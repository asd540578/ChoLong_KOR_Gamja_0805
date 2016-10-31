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

import l1j.server.server.datatables.CastleTable;
import l1j.server.server.datatables.SoldierTable;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Castle;
import l1j.server.server.templates.L1Soldier;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_SoldierBuy extends ClientBasePacket {

	private static final String C_SOLDIER_BUY = "[C] C_SoldierBuy";

	public class SelectedSoldierInfo {
		int number;
		int count;
		int price;
	}

	public C_SoldierBuy(byte abyte0[], LineageClient clientthread) {
		super(abyte0);
		try {
			@SuppressWarnings("unused")
			int npcid = readD(); // �� �� npc id
			int count = readH(); // ��Ͽ��� � ����?
			SelectedSoldierInfo[] soldierInfor = new SelectedSoldierInfo[count];

			int totalCount = 0, totalPrice = 0;
			for (int i = 0; i < soldierInfor.length; i++) {
				soldierInfor[i] = new SelectedSoldierInfo();
				soldierInfor[i].number = readH(); // ��Ͽ��� ���° �뺴�ΰ�
				soldierInfor[i].count = readH(); // �뺴 ������
				soldierInfor[i].price = readH(); // ����

				totalCount += soldierInfor[i].count;
				totalPrice += soldierInfor[i].price;
			}

			L1PcInstance pc = clientthread.getActiveChar();

			int castle_id = pc.getClan().getCastleId();

			L1Soldier soldier = SoldierTable.getInstance().getSoldierTable(
					castle_id);
			L1Castle l1catle = CastleTable.getInstance().getCastleTable(
					castle_id);

			int totalsoldier = soldier.getSoldier1() + soldier.getSoldier2()
					+ soldier.getSoldier3() + soldier.getSoldier4();
			int clanBossChar = pc.getAbility().getTotalCha();

			if (totalsoldier + totalCount > clanBossChar) {
				// ī�� �������� ����
				// �޼��� ������?
				S_SystemMessage sm = new S_SystemMessage(
						"ī������ �������� �� �̻� ��� �� �� �����ϴ�.");
				pc.sendPackets(sm, true);
				S_SystemMessage sm2 = new S_SystemMessage("��� �Ǿ��ִ� ��: "
						+ totalsoldier + " �Ϸ��� ��: " + totalCount);
				pc.sendPackets(sm2, true);
				return;
			}

			// ��� �˻� �����ϱ�, �뺴 ���� ó��

			for (int i = 0; i < count; i++) {
				switch (soldierInfor[i].number) {
				case 0:
					int sum1 = soldier.getSoldier1() + soldierInfor[i].count;
					soldier.setSoldier1(sum1);
					S_SystemMessage sm = new S_SystemMessage(
							soldier.getSoldier1Name() + "�� "
									+ soldierInfor[i].count + " �� ��� �Ͽ����ϴ�.");
					pc.sendPackets(sm, true);
					break;
				case 1:
					int sum2 = soldier.getSoldier2() + soldierInfor[i].count;
					soldier.setSoldier2(sum2);
					S_SystemMessage sm2 = new S_SystemMessage(
							soldier.getSoldier2Name() + "�� "
									+ soldierInfor[i].count + " �� ��� �Ͽ����ϴ�.");
					pc.sendPackets(sm2, true);
					break;
				case 2:
					int sum3 = soldier.getSoldier3() + soldierInfor[i].count;
					soldier.setSoldier3(sum3);
					S_SystemMessage sm3 = new S_SystemMessage(
							soldier.getSoldier3Name() + "�� "
									+ soldierInfor[i].count + " �� ��� �Ͽ����ϴ�.");
					pc.sendPackets(sm3, true);
					break;
				case 3:
					int sum4 = soldier.getSoldier4() + soldierInfor[i].count;
					soldier.setSoldier4(sum4);
					S_SystemMessage sm4 = new S_SystemMessage(
							soldier.getSoldier4Name() + "�� "
									+ soldierInfor[i].count + " �� ��� �Ͽ����ϴ�.");
					pc.sendPackets(sm4, true);
					break;
				default:
					// do nothing;
				}
			}

			int castleMoney = l1catle.getPublicMoney() - totalPrice;
			l1catle.setPublicMoney(castleMoney);
			CastleTable.getInstance().updateCastle(l1catle);
			SoldierTable.getInstance().updateSoldier(soldier);

		} catch (Exception e) {

		} finally {
			// soldierInfor = null;
			clear();
		}
	}

	@Override
	public String getType() {
		return C_SOLDIER_BUY;
	}
}
