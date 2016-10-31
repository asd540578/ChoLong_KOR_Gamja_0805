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

import java.util.List;
import java.util.Random;

import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_War extends ClientBasePacket {

	private static final String C_WAR = "[C] C_War";

	public C_War(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);
		try {
			int type = readC();
			// System.out.println(type);
			String s = readS();
			// System.out.println(s);
			L1PcInstance player = clientthread.getActiveChar();
			String playerName = player.getName();
			String clanName = player.getClanname();
			int clanId = player.getClanid();

			if (!player.isCrown()) { // ���� �̿�
				S_ServerMessage sm = new S_ServerMessage(478);
				player.sendPackets(sm, true);
				return;
			}
			if (clanId == 0) { // ũ���̼Ҽ�
				S_ServerMessage sm = new S_ServerMessage(272);
				player.sendPackets(sm, true);
				return;
			}
			L1Clan clan = L1World.getInstance().getClan(clanName);
			if (clan == null) { // ��ũ���� �߰ߵ��� �ʴ´�
				S_SystemMessage sm = new S_SystemMessage("��� ������ �߰ߵ��� �ʾҽ��ϴ�.");
				player.sendPackets(sm, true);
				return;
			}
			if (player.getId() != clan.getLeaderId()
					|| (type == 2 && player.getClanRank() != L1Clan.CLAN_RANK_PRINCE)) { // ������
				S_ServerMessage sm = new S_ServerMessage(478);
				player.sendPackets(sm, true);
				return;
			}

			if (clanName.toLowerCase().equals(s.toLowerCase())) { // ��ũ���� ����
				S_SystemMessage sm = new S_SystemMessage(
						"�ڽ��� ���� ���� ������ �Ұ����մϴ�.");
				player.sendPackets(sm, true);
				return;
			}

			L1Clan enemyClan = null;
			String enemyClanName = null;

			for (L1Clan checkClan : L1World.getInstance().getAllClans()) { // ũ������
																			// üũ
				if (checkClan.getClanName().toLowerCase()
						.equals(s.toLowerCase())) {
					enemyClan = checkClan;
					enemyClanName = checkClan.getClanName();
					break;
				}
			}
			if (enemyClan == null) { // ��� ũ���� �߰ߵ��� �ʾҴ�
				S_SystemMessage sm = new S_SystemMessage("��� ������ �߰ߵ��� �ʾҽ��ϴ�.");
				player.sendPackets(sm, true);
				return;
			}
			if (clan.getAlliance(enemyClan.getClanId()) == enemyClan) {
				S_ServerMessage sm = new S_ServerMessage(1205);
				player.sendPackets(sm, true);
				return;
			}
			boolean inWar = false;
			List<L1War> warList = L1World.getInstance().getWarList(); // ���� ����Ʈ��
																		// ���
			for (L1War war : warList) {
				if (war.CheckClanInWar(clanName)) { // ��ũ���� �̹� ������
					/*
					 * if (type == 0) { // �������� S_ServerMessage sm = new
					 * S_ServerMessage(234); player.sendPackets(sm); // \f1�����
					 * ������ ���� �������Դϴ�. sm.clear(); sm = null; return; }
					 */
					inWar = true;
					break;
				}
			}
			if (!inWar && (type == 2 || type == 3)) { // ��ũ���� ������ �ܷ̿�, �׺� �Ǵ� ����
				S_SystemMessage sm = new S_SystemMessage(
						"�������� �ƴϹǷ� �������̶� �׺� �� �Ҽ� �����ϴ�.");
				player.sendPackets(sm, true);
				return;
			}

			if (clan.getCastleId() != 0) { // ��ũ���� ����
				if (type == 0) { // ��������
					S_ServerMessage sm = new S_ServerMessage(474);
					player.sendPackets(sm, true);
					return;
				} else if (type == 2 || type == 3) { // �׺�, ����
					S_SystemMessage sm = new S_SystemMessage("�׺��� �� �� �����ϴ�.");
					player.sendPackets(sm, true);
					return;
				}
			}

			if (enemyClan.getCastleId() == 0 && player.getLevel() <= 15) {
				player.sendPackets(new S_ServerMessage(232), true);
				return;
			}
			if (enemyClan.getCastleId() != 0 && player.getLevel() < 25) {
				player.sendPackets(new S_ServerMessage(475), true); // ��������
																	// �����Ϸ��� ����
																	// 25�� �̸���
																	// ������ �ȵ˴ϴ�.
				return;
			}

			if (enemyClan.getCastleId() != 0) { // ��� ũ���� ����
				int castle_id = enemyClan.getCastleId();
				if (WarTimeController.getInstance().isNowWar(castle_id)) { // ����
																			// �ð���
					L1PcInstance clanMember[] = clan.getOnlineClanMember();
					for (int k = 0; k < clanMember.length; k++) {
						if (L1CastleLocation.checkInWarArea(castle_id,
								clanMember[k])) {
							// S_ServerMessage sm = new S_ServerMessage(477);
							// player.sendPackets(sm, true); // ����� ������ ��� ���Ϳ���
							// ���� �ۿ� ������ ������ �������� ������ �� �����ϴ�.
							int[] loc = new int[3];
							Random _rnd = new Random(System.nanoTime());
							loc = L1CastleLocation.getGetBackLoc(castle_id);
							int locx = loc[0] + (_rnd.nextInt(4) - 2);
							int locy = loc[1] + (_rnd.nextInt(4) - 2);
							short mapid = (short) loc[2];
							L1Teleport.teleport(clanMember[k], locx, locy,
									mapid, clanMember[k].getMoveState()
											.getHeading(), true);
						}
					}
					boolean enemyInWar = false;
					for (L1War war : warList) {
						if (war.CheckClanInWar(enemyClanName)) { // ��� ũ���� �̹�������
							if (type == 0) { // ��������
								war.DeclareWar(clanName, enemyClanName);
								war.AddAttackClan(clanName);
							} else if (type == 2 || type == 3) {
								if (!war.CheckClanInSameWar(clanName,enemyClanName)) { // ��ũ���� ��� ũ���� �ٸ� ����
									S_SystemMessage sm = new S_SystemMessage("�ٸ� ���� �������Դϴ�.");
									player.sendPackets(sm, true);
									return;
								}
								if (type == 2) { // �׺�
									war.SurrenderWar(clanName, enemyClanName);
								} else if (type == 3) { // ����
									war.CeaseWar(clanName, enemyClanName);
								}
							}
							enemyInWar = true;
							break;
						}
					}
					if (!enemyInWar && type == 0) { // ��� ũ���� ������ �ܷ̿�, ��������
						L1War war = new L1War();
						war.handleCommands(1, clanName, enemyClanName); // ����������
					}
				} else { // ���� �ð���
					if (type == 0) { // ��������
						S_ServerMessage sm = new S_ServerMessage(476);
						player.sendPackets(sm, true); // ���� �������� �ð��� �ƴմϴ�.
					}
				}
			} else { // ��� ũ���� ���ִ� �ƴϴ�
				for (int i = 1; i < 9; i++) {
					if (WarTimeController.getInstance().isNowWar(i)) {
						player.sendPackets(new S_SystemMessage(
								"���� �߿��� �����ܿ��� ��û�� �� �� �����ϴ�."));
						return;
					}
				}
				boolean enemyInWar = false;
				for (L1War war : warList) {
					if (war.CheckClanInWar(enemyClanName)) { // ��� ũ���� �̹� ������
						if (type == 0) { // ��������
							S_ServerMessage sm = new S_ServerMessage(236,
									enemyClanName);
							player.sendPackets(sm, true);
							return;
						} else if (type == 2 || type == 3) { // �׺� �Ǵ� ����
							if (!war.CheckClanInSameWar(clanName, enemyClanName)) { // ��ũ����
																					// ���
																					// ũ����
																					// �ٸ�
																					// ����
								S_SystemMessage sm = new S_SystemMessage(
										"�̹� ���� ���Դϴ�.");
								player.sendPackets(sm, true);
								return;
							}
						}
						enemyInWar = true;
						break;
					}
				}
				if (!enemyInWar && (type == 2 || type == 3)) { // ��� ũ���� ������
																// �ܷ̿�, �׺� �Ǵ� ����
					S_SystemMessage sm = new S_SystemMessage("������ �ƴϹǷ� �׺� �Ұ���");
					player.sendPackets(sm, true);
					return;
				}

				// �������� �ƴ� ���, ����� �������� ������ �ʿ�
				L1PcInstance enemyLeader = L1World.getInstance().getPlayer(
						enemyClan.getLeaderName());

				if (enemyLeader == null) { // ����� �����ְ� �߰ߵ��� �ʾҴ�
					S_ServerMessage sm = new S_ServerMessage(218, enemyClanName);
					player.sendPackets(sm, true); // \f1%0 ������ ���ִ� ���� ���忡 �����ϴ�.
					return;
				}

				if (type == 0) { // ��������
					enemyLeader.setTempID(player.getId()); // ����� ������Ʈ ID�� ������
															// �д�
					S_Message_YN myn = new S_Message_YN(217, clanName,
							playerName);
					enemyLeader.sendPackets(myn, true); // %0������%1�� ����� ���Ͱ��� ������
														// �ٶ�� �ֽ��ϴ�. ���￡ ���մϱ�?
														// (Y/N)
				} else if (type == 2) { // �׺�
					enemyLeader.setTempID(player.getId()); // ����� ������Ʈ ID�� ������
															// �д�
					S_Message_YN myn = new S_Message_YN(221, clanName);
					enemyLeader.sendPackets(myn, true); // %0������ �׺��� �ٶ�� �ֽ��ϴ�.
														// �޾Ƶ��Դϱ�? (Y/N)
				} else if (type == 3) { // ����
					enemyLeader.setTempID(player.getId()); // ����� ������Ʈ ID�� ������
															// �д�
					S_Message_YN myn = new S_Message_YN(222, clanName);
					enemyLeader.sendPackets(myn, true); // %0������ ������ ������ �ٶ��
														// �ֽ��ϴ�. �����մϱ�? (Y/N)
				}
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_WAR;
	}

}
