/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.Warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.templates.L1Item;
import l1j.server.server.utils.SQLUtil;

public class ClanWarehouse extends Warehouse {
	private static final long serialVersionUID = 1L;
	private static Logger _log = Logger
			.getLogger(ClanWarehouse.class.getName());
	private boolean key = false;
	private int pcIdUsingClanWarehouse = -1;

	public ClanWarehouse(String clan) {
		super(clan);
	}

	public synchronized boolean lock(int id) {
		if (!key || pcIdUsingClanWarehouse == id) {
			key = true;
			pcIdUsingClanWarehouse = id;
			return key;
		} else
			return false;
	}

	public synchronized void unlock(int id) {
		if (id == pcIdUsingClanWarehouse)
			key = false;
	}

	@Override
	protected int getMax() {
		return Config.MAX_CLAN_WAREHOUSE_ITEM;
	}

	@Override
	public synchronized boolean checkitem(int itemid, int count) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT count FROM clan_warehouse WHERE clan_name = ? AND item_id = ?");
			pstm.setString(1, getName());
			pstm.setInt(2, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				if (count <= rs.getInt("count")) {
					return true;
				}
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	@Override
	public synchronized void loadItems() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM clan_warehouse WHERE clan_name = ?");
			pstm.setString(1, getName());
			rs = pstm.executeQuery();
			L1ItemInstance item = null;
			L1Item itemTemplate = null;
			while (rs.next()) {
				item = new L1ItemInstance();
				int objectId = rs.getInt("id");
				item.setId(objectId);
				int itemId = rs.getInt("item_id");
				itemTemplate = ItemTable.getInstance().getTemplate(itemId);
				if (itemTemplate == null) {
					throw new NullPointerException("item_id=" + itemId
							+ " not found");
				}
				item.setItem(itemTemplate);
				item.setCount(rs.getInt("count"));
				item.setEquipped(false);
				item.setEnchantLevel(rs.getInt("enchantlvl"));
				item.setIdentified(rs.getInt("is_id") != 0 ? true : false);
				item.set_durability(rs.getInt("durability"));
				item.setChargeCount(rs.getInt("charge_count"));
				item.setRemainingTime(rs.getInt("remaining_time"));
				item.setLastUsed(rs.getTimestamp("last_used"));
				item.setBless(item.getItem().getBless());
				item.setAttrEnchantLevel(rs.getInt("attr_enchantlvl"));
				item.setEndTime(rs.getTimestamp("end_time"));
				item.setCreaterName(rs.getString("CreaterName"));
				item.setDemonBongin(rs.getInt("demon_bongin") == 0 ? false
						: true);
				_items.add(item);
				L1World.getInstance().storeObject(item);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	@Override
	public synchronized void insertItem(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM clan_warehouse WHERE id = ?");
			pstm.setInt(1, item.getId());
			rs = pstm.executeQuery();
			if (!rs.next()) {
				pstm2 = con.prepareStatement("INSERT INTO clan_warehouse SET id = ?, clan_name = ?, item_id = ?, item_name = ?, count = ?, is_equipped=0, enchantlvl = ?, is_id= ?, durability = ?, charge_count = ?, remaining_time = ?, last_used = ?, attr_enchantlvl = ?, end_time=?, CreaterName=?, demon_bongin=?");
				pstm2.setInt(1, item.getId());
				pstm2.setString(2, getName());
				pstm2.setInt(3, item.getItemId());
				pstm2.setString(4, item.getName());
				pstm2.setInt(5, item.getCount());
				pstm2.setInt(6, item.getEnchantLevel());
				pstm2.setInt(7, item.isIdentified() ? 1 : 0);
				pstm2.setInt(8, item.get_durability());
				pstm2.setInt(9, item.getChargeCount());
				pstm2.setInt(10, item.getRemainingTime());
				pstm2.setTimestamp(11, item.getLastUsed());
				pstm2.setInt(12, item.getAttrEnchantLevel());
				pstm2.setTimestamp(13, item.getEndTime());
				pstm2.setString(14, item.getCreaterName());
				pstm2.setInt(15, item.isDemonBongin() ? 1 : 0);
				pstm2.executeUpdate();
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(pstm2);
			SQLUtil.close(con);
		}
	}

	@Override
	public synchronized void updateItem(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE clan_warehouse SET count = ? WHERE id = ?");
			pstm.setInt(1, item.getCount());
			pstm.setInt(2, item.getId());
			pstm.executeUpdate();

		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	@Override
	public synchronized void deleteItem(L1ItemInstance item) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM clan_warehouse WHERE id = ?");
			pstm.setInt(1, item.getId());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		_items.remove(_items.indexOf(item));
	}

	public synchronized void deleteAllItems() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM clan_warehouse WHERE clan_name = ?");
			pstm.setString(1, getName());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
}
