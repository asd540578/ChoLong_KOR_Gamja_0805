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
package l1j.server.server.TimeController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.AuctionBoardTable;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.HouseTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1AuctionBoard;
import l1j.server.server.templates.L1House;
import l1j.server.server.utils.SQLUtil;

public class AuctionTimeController implements Runnable {
	// private static Logger _log =
	// Logger.getLogger(AuctionTimeController.class.getName());

	private static AuctionTimeController _instance;

	public static AuctionTimeController getInstance() {
		if (_instance == null) {
			_instance = new AuctionTimeController();
		}
		return _instance;
	}

	@Override
	public void run() {
		try {
			checkAuctionDeadline();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		GeneralThreadPool.getInstance().schedule(this, 60000);
	}

	public Calendar getRealTime() {
		TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		Calendar cal = Calendar.getInstance(tz);
		return cal;
	}

	private void checkAuctionDeadline() {
		AuctionBoardTable boardTable = new AuctionBoardTable();
		for (L1AuctionBoard board : boardTable.getAuctionBoardTableList()) {
			if (board.getDeadline().before(getRealTime())) {
				endAuction(board);
			}
		}
	}

	private void endAuction(L1AuctionBoard board) {
		int houseId = board.getHouseId();
		int price = board.getPrice();
		int oldOwnerId = board.getOldOwnerId();
		String bidder = board.getBidder();
		int bidderId = board.getBidderId();

		if (oldOwnerId != 0 && bidderId != 0) { // ������ ������ �־������ �־�
			L1PcInstance oldOwnerPc = (L1PcInstance) L1World.getInstance()
					.findObject(oldOwnerId);
			int payPrice = (int) (price * 0.9);
			if (oldOwnerPc != null) { // ������ �����ڰ� �¶�����
				oldOwnerPc.getInventory().storeItem(L1ItemId.ADENA, payPrice);
				// ����� �����ϰ� �ִ� ���� ���� ����%1�Ƶ����� �����Ǿ����ϴ�.%n
				// ������10%%�� ������ �������� �ݾ�%0�Ƶ����� �帳�ϴ�.%n �����մϴ�.%n%n
				oldOwnerPc.sendPackets(
						new S_ServerMessage(527, String.valueOf(payPrice)),
						true);
			} else { // ������ �����ڰ� ���� ������
				/*
				 * L1ItemInstance item =
				 * ItemTable.getInstance().createItem(L1ItemId.ADENA);
				 * item.setCount(payPrice); try { CharactersItemStorage storage
				 * = CharactersItemStorage.create();
				 * storage.storeItem(oldOwnerId, item); } catch (Exception e) {
				 * _log.log(Level.SEVERE, e.getLocalizedMessage(), e); }
				 */
				itemInsert(oldOwnerId, payPrice);
			}

			L1PcInstance bidderPc = (L1PcInstance) L1World.getInstance()
					.findObject(bidderId);
			if (bidderPc != null) { // �����ڰ� �¶�����
				// �����մϴ�.%n����� ������ ��Ŵ� ���� ����%0�Ƶ����� �������� �����Ǿ����ϴ�.%n
				// ����� �����Ͻ� ���� ��ٷ� �̿��Ͻ� �� �ֽ��ϴ�.%n �����մϴ�.%n%n
				bidderPc.sendPackets(
						new S_ServerMessage(524, String.valueOf(price), bidder),
						true);
			}
			deleteHouseInfo(houseId);
			setHouseInfo(houseId, bidderId);
			deleteNote(houseId);
		} else if (oldOwnerId == 0 && bidderId != 0) { // ������ ������ ������������ �־�
			L1PcInstance bidderPc = (L1PcInstance) L1World.getInstance()
					.findObject(bidderId);
			if (bidderPc != null) { // �����ڰ� �¶�����
				// �����մϴ�.%n����� ������ ��Ŵ� ���� ����%0�Ƶ����� �������� �����Ǿ����ϴ�.%n
				// ����� �����Ͻ� ���� ��ٷ� �̿��Ͻ� �� �ֽ��ϴ�.%n �����մϴ�.%n%n
				bidderPc.sendPackets(
						new S_ServerMessage(524, String.valueOf(price), bidder),
						true);
			}
			setHouseInfo(houseId, bidderId);
			deleteNote(houseId);
		} else if (oldOwnerId != 0 && bidderId == 0) { // ������ ������ �־������ ����
			L1PcInstance oldOwnerPc = (L1PcInstance) L1World.getInstance()
					.findObject(oldOwnerId);
			if (oldOwnerPc != null) { // ������ �����ڰ� �¶�����
				// ����� ��û �Ͻ� ��Ŵ�, ��� �Ⱓ���� ������ �ݾ� �̻󿡼��� ������ ǥ���ϴ� ���� ��Ÿ���� �ʾұ�
				// (����)������, �ᱹ �����Ǿ����ϴ�.%n
				// ����, �������� ��ſ��� �ǵ����� ���� �˷� �帮�ڽ��ϴ�.%n �����մϴ�.%n%n
				oldOwnerPc.sendPackets(new S_ServerMessage(528), true);
			}
			deleteNote(houseId);
		} else if (oldOwnerId == 0 && bidderId == 0) { // ������ ������ ������������ ����
			// ������ 5�� �ķ� ������ ���� ��ſ� ���δ�
			Calendar cal = getRealTime();
			cal.add(Calendar.DATE, 2);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			board.setDeadline(cal);
			AuctionBoardTable boardTable = new AuctionBoardTable();
			boardTable.updateAuctionBoard(board);
		}
	}

	/**
	 * ������ �������� ����Ʈ�� �����
	 * 
	 * @param houseId
	 * 
	 * @return
	 */
	private void deleteHouseInfo(int houseId) {
		for (L1Clan clan : L1World.getInstance().getAllClans()) {
			if (clan.getHouseId() == houseId) {
				clan.setHouseId(0);
				ClanTable.getInstance().updateClan(clan);
			}
		}
	}

	/**
	 * �������� ����Ʈ�� �����Ѵ�
	 * 
	 * @param houseId
	 *            bidderId
	 * 
	 * @return
	 */
	private void setHouseInfo(int houseId, int bidderId) {
		for (L1Clan clan : L1World.getInstance().getAllClans()) {
			if (clan.getLeaderId() == bidderId) {
				clan.setHouseId(houseId);
				ClanTable.getInstance().updateClan(clan);
				break;
			}
		}
	}

	/**
	 * ����Ʈ�� ��� ���¸� OFF�� ������, ��� �Խ������κ��� �����
	 * 
	 * @param houseId
	 * 
	 * @return
	 */
	private void deleteNote(int houseId) {
		// ����Ʈ�� ��� ���¸� OFF�� �����Ѵ�
		L1House house = HouseTable.getInstance().getHouseTable(houseId);
		house.setOnSale(false);
		Calendar cal = getRealTime();
		cal.add(Calendar.DATE, Config.HOUSE_TAX_INTERVAL);
		cal.set(Calendar.MINUTE, 0); // �� , �ʴ� �߶� ����
		cal.set(Calendar.SECOND, 0);
		house.setTaxDeadline(cal);
		HouseTable.getInstance().updateHouse(house);

		// ��� �Խ������κ��� �����
		AuctionBoardTable boardTable = new AuctionBoardTable();
		boardTable.deleteAuctionBoard(houseId);
	}

	private void itemInsert(int charid, int ac) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM character_items WHERE char_id=? AND item_id =?");
			pstm.setInt(1, charid);
			pstm.setInt(2, 40308);
			rs = pstm.executeQuery();
			if (rs.next()) {
				int item_id = rs.getInt("id");
				int count = rs.getInt("count");
				itemUpdate(item_id, count + ac);
			} else {
				itemInsert2(charid, ac);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void itemInsert2(int id, int count) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO character_items SET id = ?, item_id = ?, char_id = ?, item_name = ?, count = ?, bless=?");
			pstm.setInt(1, ObjectIdFactory.getInstance().nextId());
			pstm.setInt(2, 40308);
			pstm.setInt(3, id);
			pstm.setString(4, "�Ƶ���");
			pstm.setInt(5, count);
			pstm.setInt(6, 1);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void itemUpdate(int id, int count) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE character_items SET count=? WHERE id=?");
			pstm.setInt(1, count);
			pstm.setInt(2, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
}
