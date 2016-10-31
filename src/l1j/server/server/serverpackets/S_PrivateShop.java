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

package l1j.server.server.serverpackets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import l1j.server.server.Opcodes;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.templates.L1PrivateShopBuyList;
import l1j.server.server.templates.L1PrivateShopSellList;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

public class S_PrivateShop extends ServerBasePacket {

	public S_PrivateShop(L1PcInstance pc, int objectId, int type) {
		try {
			L1PcInstance shopPc = (L1PcInstance) L1World.getInstance()
					.findObject(objectId);

			if (shopPc == null) {
				return;
			}

			if (!pc.isGm()
					&& pc.getAccountName().equalsIgnoreCase(
							shopPc.getAccountName())) {
				return;
			}
			/*
			 * if (type == 0) { ArrayList<?> list = shopPc.getSellList(); int
			 * size = list.size(); if(size <= 0) return; } else
			 */if (type == 1) {
				ArrayList<?> list = shopPc.getBuyList();
				int size = list.size();
				if (size <= 0)
					return;
			}
			writeC(Opcodes.S_PERSONAL_SHOP_LIST);
			writeC(type);
			writeD(objectId);

			if (type == 0) {
				ArrayList<?> list = shopPc.getSellList();
				int size = list.size();
				pc.setPartnersPrivateShopItemCount(size);
				writeH(size);
				L1PrivateShopSellList pssl = null;
				L1ItemInstance item = null;
				for (int i = 0; i < size; i++) {
					pssl = (L1PrivateShopSellList) list.get(i);
					int itemObjectId = pssl.getItemObjectId();
					int count = pssl.getSellTotalCount() - pssl.getSellCount();
					int price = pssl.getSellPrice();
					item = shopPc.getInventory().getItem(itemObjectId);
					if (item != null) {
						writeC(i);
						writeD(count);
						writeD(price);
						writeH(item.getItem().getGfxId());
						writeC(item.getEnchantLevel());
						writeC(item.isIdentified() ? 1 : 0);
						writeC(item.getBless());
						writeS(item.getNumberedViewName(count, true));
						if (item.isIdentified()) {
							int oldCount = item.getCount();
							item.setCount(1);
							byte[] status = item.getStatusBytes();
							item.setCount(oldCount);
							writeC(status.length);
							for (byte b : status) {
								writeC(b);
							}
						} else
							writeC(0);
					}
				}
				writeH(0x00);
			} else if (type == 1) {
				ArrayList<L1PrivateShopBuyList> list = shopPc.getBuyList();
				Map<Integer, L1PrivateShopBuyList> havelist = new HashMap<Integer, L1PrivateShopBuyList>();
				// ArrayList<L1ItemInstance> pchavelist = new
				// ArrayList<L1ItemInstance>();

				L1PrivateShopBuyList psbl = null;
				L1ItemInstance item = null;
				int havesize = 0;
				int havelistsize = 0;
				int tempi = 0;
				for (L1PrivateShopBuyList psb : list) {
					item = shopPc.getInventory().getItem(psb.getItemObjectId());
					for (L1ItemInstance pcItem : pc.getInventory().getItems()) {
						if (!pcItem.isEquipped()
								&& item.getItemId() == pcItem.getItemId()
								&& item.getEnchantLevel() == pcItem
										.getEnchantLevel()
								&& item.getAttrEnchantLevel() == pcItem
										.getAttrEnchantLevel()
								&& item.getBless() == pcItem.getBless()) {
							// pchavelist.add(pcItem);
							// System.out.println(havesize+" : "
							// +item.getName());
							havesize++;
						}
					}

					if (pc.getInventory().CheckSellPrivateShopItem(
							item.getItemId(), item.getEnchantLevel(),
							item.getAttrEnchantLevel(), item.getBless())) {
						havelist.put(havelistsize, psb);
						havelistsize++;
					}
				}

				// int size = havelist.size();

				writeH(havesize);
				if (havesize <= 0)
					return;

				for (int i = 0; i < havelistsize; i++) {
					psbl = havelist.get(i);
					int itemObjectId = psbl.getItemObjectId();
					int count = psbl.getBuyTotalCount();
					int price = psbl.getBuyPrice();
					item = shopPc.getInventory().getItem(itemObjectId);
					// System.out.println(havesize+" : " +item.getName());
					for (L1ItemInstance pcItem : pc.getInventory().getItems()) {
						try {
							if (!pcItem.isEquipped()
									&& item.getItemId() == pcItem.getItemId()
									&& item.getEnchantLevel() == pcItem
											.getEnchantLevel()
									&& item.getAttrEnchantLevel() == pcItem
											.getAttrEnchantLevel()
									&& item.getBless() == pcItem.getBless()) {
								writeC(i + tempi);
								writeD(count);
								writeD(price);
								writeD(pcItem.getId());
								writeC(0x00);
								tempi++;
								/*
								 * writeD(pcItem.getId()); writeD(count);
								 * writeD(price);
								 */
							}
						} catch (Exception e) {
							if (shopPc != null)
								System.out
										.println("유저 상점 오류 좌표 x :"
												+ shopPc.getX() + " y :"
												+ shopPc.getY() + " m :"
												+ shopPc.getMapId() + " 아이템 ="
												+ item != null ? item.getName()
												: "NULL");
						}
					}
				}
				writeH(0x00);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}
}
