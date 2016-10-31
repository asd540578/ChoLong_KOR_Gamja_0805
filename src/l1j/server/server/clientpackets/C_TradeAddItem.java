/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be trading_partnerful,
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

package l1j.server.server.clientpackets;

import l1j.server.GameSystem.Gamble.GambleInstance;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Trade;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1BuffNpcInstance;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
//Referenced classes of package l1j.server.server.clientpackets:
//ClientBasePacket
import l1j.server.server.serverpackets.S_SystemMessage;
import server.LineageClient;

public class C_TradeAddItem extends ClientBasePacket {
	private static final String C_TRADE_ADD_ITEM = "[C] C_TradeAddItem";

	public C_TradeAddItem(byte abyte0[], LineageClient client) throws Exception {

		super(abyte0);
		try {
			int itemid = readD();
			int itemcount = readD();
			L1PcInstance pc = client.getActiveChar();
			L1Trade trade = new L1Trade();
			L1ItemInstance item = pc.getInventory().getItem(itemid);

			if (item == null)
				return;
			if (itemid != item.getId()) {
				return;
			}
			if (!item.isStackable() && itemcount != 1) {
				return;
			}
			if (itemcount <= 0 || item.getCount() <= 0) {
				return;
			}
			if (itemcount > item.getCount()) {
				itemcount = item.getCount();
			}
			if (itemcount > 2000000000) {
				return;
			}
			/*
			 * if (item.getItem().getItemId() == 423012 ||
			 * item.getItem().getItemId() == 423013){ // 10�ֳ�Ƽ
			 * pc.sendPackets(new S_ServerMessage(210,
			 * item.getItem().getName())); // \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
			 * return; }
			 */
			if (!item.getItem().isTradable()) {
				S_ServerMessage sm = new S_ServerMessage(210, item.getItem()
						.getName());
				pc.sendPackets(sm, true);
				return;
			}
			if (item.getBless() >= 128) {
				S_ServerMessage sm = new S_ServerMessage(210, item.getItem()
						.getName());
				pc.sendPackets(sm, true);
				return;
			}
			if (item.isEquipped()) {
				S_ServerMessage sm = new S_ServerMessage(906);
				pc.sendPackets(sm, true);
				return;
			}
			L1DollInstance ���� = null;
			S_SystemMessage smdoll = new S_SystemMessage(
					"��ȯ���� ������ �ŷ� �� �� �����ϴ�.");
			for (Object ����������Ʈ : pc.getDollList()) {
				if (����������Ʈ instanceof L1DollInstance) {
					���� = (L1DollInstance) ����������Ʈ;
					if (item.getId() == ����.getItemObjId()) {
						// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
						pc.sendPackets(smdoll, true);
						return;
					}
				}
			}
			L1PetInstance pet = null;
			S_ServerMessage smdolli = new S_ServerMessage(210, item.getItem()
					.getName());
			for (Object petObject : pc.getPetList()) {
				if (petObject instanceof L1PetInstance) {
					pet = (L1PetInstance) petObject;
					if (item.getId() == pet.getItemObjId()) {
						// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
						pc.sendPackets(smdolli, true);
						return;
					}
				}
			}
			if (pc.getTradeOk()) { // �ϷḦ �������¶�� ������ ���̻� �� �ø�
				return;
			}

			L1Object tradingPartner = L1World.getInstance().findObject(
					pc.getTradeID());
			if (tradingPartner == null) {
				return;
			}
			if (tradingPartner instanceof L1PcInstance) {
				L1PcInstance target = (L1PcInstance) tradingPartner;
				if (pc.getTradeOk() || target.getTradeOk()) {
					return;
				}
				if (target.getInventory().checkAddItem(item, itemcount) != L1Inventory.OK) {
					S_ServerMessage sm = new S_ServerMessage(270);
					target.sendPackets(sm, true);
					S_ServerMessage sm1 = new S_ServerMessage(271);
					pc.sendPackets(sm1, true);
					return;
				}
			} else if (tradingPartner instanceof L1BuffNpcInstance) {
				L1BuffNpcInstance target = (L1BuffNpcInstance) tradingPartner;
				if (pc.getTradeOk() || target.getTradeOk()) {
					return;
				}
			} else if (tradingPartner instanceof L1NpcShopInstance) {
				if (pc.getTradeOk()) {
					return;
				}
			} else if (tradingPartner instanceof GambleInstance) {
				if (pc.getTradeOk()) {
					return;
				}
			}
			trade.TradeAddItem(pc, itemid, itemcount);
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_TRADE_ADD_ITEM;
	}
}
