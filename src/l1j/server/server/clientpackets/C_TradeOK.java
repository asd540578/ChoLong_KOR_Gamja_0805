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

package l1j.server.server.clientpackets;

import l1j.server.GameSystem.Gamble.GambleInstance;
import l1j.server.GameSystem.NpcTradeShop.NpcTradeShop;
import l1j.server.GameSystem.NpcTradeShop.ShopItem;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Trade;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1BuffNpcInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_TradeStatus;
import l1j.server.server.types.Point;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_TradeOK extends ClientBasePacket {

	private static final String C_TRADE_CANCEL = "[C] C_TradeOK";

	public C_TradeOK(byte abyte0[], LineageClient clientthread)
			throws Exception {
		super(abyte0);
		try {
			L1PcInstance player = clientthread.getActiveChar();
			if (!player.getTradeReady())
				return;

			L1Object trading_partner = L1World.getInstance().findObject(
					player.getTradeID());

			if (trading_partner != null) {
				if (trading_partner instanceof L1PcInstance) {
					L1PcInstance target = (L1PcInstance) trading_partner;
					if (!target.getTradeReady())
						return;
					player.setTradeOk(true);
					if (target.isChaTradeSlot()) {
						S_SystemMessage sm_noslot = new S_SystemMessage(
								"�ŷ� ��󿡰� �� ĳ���� ������ �����ϴ�.");
						S_SystemMessage sm_noslot2 = new S_SystemMessage(
								"�� ĳ���� ������ �����ϴ�. ĳ���� ������ Ȯ���ϰ� �ٽ� �õ����ֽñ� �ٶ��ϴ�.");
						player.sendPackets(sm_noslot, true);
						target.sendPackets(sm_noslot2, true);

						L1Trade trade = new L1Trade();

						trade.TradeCancel(player);
						target.setChaTradeSlot(false);
						trade = null;
						return;
					} else if (player.isChaTradeSlot()) {
						S_SystemMessage sm_noslot = new S_SystemMessage(
								"�ŷ� ��󿡰� �� ĳ���� ������ �����ϴ�.");
						S_SystemMessage sm_noslot2 = new S_SystemMessage(
								"�� ĳ���� ������ �����ϴ�. ĳ���� ������ Ȯ���ϰ� �ٽ� �õ����ֽñ� �ٶ��ϴ�.");
						target.sendPackets(sm_noslot, true);
						player.sendPackets(sm_noslot2, true);

						L1Trade trade = new L1Trade();

						trade.TradeCancel(player);
						player.setChaTradeSlot(false);
						trade = null;
						return;
					}
					if (!target.getTradeOk()) {
						target.sendPackets(new S_TradeStatus(2), true);
					}
					if (player.getTradeOk() && target.getTradeOk()) // ��� OK��
																	// ������
					{
						if (player.getLocation().getTileLineDistance(
								new Point(target.getX(), target.getY())) > 20) {
							player.sendPackets(new S_SystemMessage(
									"���� �Ÿ��� �ʹ� �־� �ŷ��� �� �� �����ϴ�."));
							target.sendPackets(new S_SystemMessage(
									"���� �Ÿ��� �ʹ� �־� �ŷ��� �� �� �����ϴ�."));
							L1Trade trade = new L1Trade();
							trade.TradeCancel(player);
							trade = null;
						} else {
							// (180 - 16) ���̸��̶�� Ʈ���̵� ����.
							// ������ ��ġ�� ������(�Ƶ����� )�� �̹� ������ �ִ� ��츦 ������� �ʴ� ���� ��
							// �ȴ�.
							if (player.getInventory().getSize() < (180 - 16)
									&& target.getInventory().getSize() < (180 - 16)) {// ������
																						// ��������
																						// ��뿡��
																						// �ǳ��ش�
								L1Trade trade = new L1Trade();
								trade.TradeOK(player);
								trade = null;
							} else {// ������ �������� ���߿� �ǵ�����
								S_ServerMessage sm = new S_ServerMessage(263);
								player.sendPackets(sm); // \f1�ѻ���� ĳ���Ͱ� ������ ���� ��
														// �ִ� �������� �ִ� 180�������Դϴ�.
								target.sendPackets(sm, true); // \f1�ѻ���� ĳ���Ͱ�
																// ������ ���� �� �ִ�
																// �������� �ִ�
																// 180�������Դϴ�.
								L1Trade trade = new L1Trade();
								trade.TradeCancel(player);
								trade = null;
							}
						}
					}
				} else if (trading_partner instanceof L1BuffNpcInstance) {
					L1BuffNpcInstance target = (L1BuffNpcInstance) trading_partner;
					player.setTradeOk(true);
					if (player.getTradeOk()) // ��� OK�� ������
					{
						if (player.getTradeWindowInventory().findItemId(40308)
								.getCount() != 100000
								&& target.getNpcTemplate().get_npcId() == 7000067) { // ����(��������)
							L1Trade trade = new L1Trade();
							trade.TradeCancel(player);
							Broadcaster.broadcastPacket(target,
									new S_NpcChatPacket(target, "10����������", 0),
									true);
							trade = null;
							return;
						}

						if (player.getTradeWindowInventory().findItemId(40308)
								.getCount() != 200000
								&& target.getNpcTemplate().get_npcId() == 7000070) { // ����(����ȯ����)
							L1Trade trade = new L1Trade();
							trade.TradeCancel(player);
							Broadcaster.broadcastPacket(target,
									new S_NpcChatPacket(target, "20����������", 0),
									true);
							trade = null;
							return;
						}
						// (180 - 16) ���̸��̶�� Ʈ���̵� ����.
						// ������ ��ġ�� ������(�Ƶ����� )�� �̹� ������ �ִ� ��츦 ������� �ʴ� ���� �� �ȴ�.
						if (player.getInventory().getSize() < (180 - 16)
								&& target.getInventory().getSize() < (180 - 16)) {// ������
																					// ��������
																					// ��뿡��
																					// �ǳ��ش�
							L1Trade trade = new L1Trade();
							trade.TradeOK(player);
							trade = null;
						} else {// ������ �������� ���߿� �ǵ�����
							player.sendPackets(new S_ServerMessage(263), true); // \f1�ѻ����
																				// ĳ���Ͱ�
																				// ������
																				// ����
																				// ��
																				// �ִ�
																				// ��������
																				// �ִ�
																				// 180�������Դϴ�.
							L1Trade trade = new L1Trade();
							trade.TradeCancel(player);
							trade = null;
						}
					}
				} else if (trading_partner instanceof L1NpcShopInstance) {
					// L1NpcShopInstance tradenpc =
					// (L1NpcShopInstance)trading_partner;
					player.setTradeOk(true);
					if (player.getTradeOk()) // ��� OK�� ������
					{
						// (180 - 16) ���̸��̶�� Ʈ���̵� ����.
						// ������ ��ġ�� ������(�Ƶ����� )�� �̹� ������ �ִ� ��츦 ������� �ʴ� ���� �� �ȴ�.
						// if (player.getInventory().getSize() < (180 - 16)) {//
						// ������ �������� ��뿡�� �ǳ��ش�
						// System.out.println("444444444444");
						L1Trade trade = new L1Trade();
						ShopItem[] list = NpcTradeShop.getInstance()
								.getShopList();
						boolean ck = false;
						if (list != null) {
							for (ShopItem shop : list) {
								if (shop.getNpcId() == ((L1NpcShopInstance) trading_partner)
										.getNpcId()) {
									ck = true;
									break;
								}
							}
						}
						if (ck) {
							// System.out.println("1111111111");
							trade.TradeOK_NpcTradeShop(player);
						} else {
							// System.out.println("2222222222222");
							trade.TradeOK_NpcShop(player);
						}
						trade = null;
						/*
						 * } else {// ������ �������� ���߿� �ǵ����� S_ServerMessage sm = new
						 * S_ServerMessage(263); player.sendPackets(sm, true);
						 * // \f1�ѻ���� ĳ���Ͱ� ������ ���� �� �ִ� �������� �ִ� 180�������Դϴ�.
						 * L1Trade trade = new L1Trade();
						 * trade.TradeCancel(player); trade = null; }
						 */
					}
				} else if (trading_partner instanceof GambleInstance) {
					// GambleInstance tradenpc =
					// (GambleInstance)trading_partner;
					player.setTradeOk(true);
					if (player.getTradeOk()) // ��� OK�� ������
					{
						// (180 - 16) ���̸��̶�� Ʈ���̵� ����.
						// ������ ��ġ�� ������(�Ƶ����� )�� �̹� ������ �ִ� ��츦 ������� �ʴ� ���� �� �ȴ�.
						if (player.getInventory().getSize() < (180 - 16)) {// ������
																			// ��������
																			// ��뿡��
																			// �ǳ��ش�
							L1Trade trade = new L1Trade();
							trade.TradeOK(player);
							trade = null;
						} else {// ������ �������� ���߿� �ǵ�����
							S_ServerMessage sm = new S_ServerMessage(263);
							player.sendPackets(sm, true); // \f1�ѻ���� ĳ���Ͱ� ������ ����
															// �� �ִ� �������� �ִ�
															// 180�������Դϴ�.
							L1Trade trade = new L1Trade();
							trade.TradeCancel(player);
							trade = null;
						}
					}
				}
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_TRADE_CANCEL;
	}

}
