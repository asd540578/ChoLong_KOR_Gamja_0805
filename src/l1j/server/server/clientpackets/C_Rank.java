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

import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound; // ����
import l1j.server.server.serverpackets.S_SystemMessage; // ����
import l1j.server.server.utils.FaceToFace;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_Rank extends ClientBasePacket {

	private static final String C_RANK = "[C] C_Rank";
	private static Logger _log = Logger.getLogger(C_Rank.class.getName());

	public C_Rank(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);

		try {
			int type = readC(); // ?
			int rank = 0;

			try {
				rank = readC();
			} catch (Exception e) {
			}

			L1PcInstance pc = clientthread.getActiveChar();
			if (pc == null) {
				return;
			}

			L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
			String clanname = pc.getClanname();

			if ((clan == null) && (type < 5)) {
				return;
			}

			int Enchantlvl = 0; // ����

			switch (type) {
			case 0: // ���Ϳ� ���� �ο����� ������ �־��� ���
				// pc.sendPackets(new S_PacketBox(pc, S_PacketBox.PLEDGE_ONE));
				S_PacketBox pb = new S_PacketBox(pc, S_PacketBox.PLEDGE_TWO);
				pc.sendPackets(pb, true);
				break;
			case 1:// ���
				String name = readS();
				L1PcInstance targetPc = L1World.getInstance().getPlayer(name);
				if (rank != L1Clan.CLAN_RANK_PROBATION && rank != L1Clan.CLAN_RANK_PUBLIC
						&& rank != L1Clan.CLAN_RANK_GUARDIAN && rank != L1Clan.CLAN_RANK_����) {
					// ��ũ�� �����ϴ� ����� �̸��� ��ũ�� �Է��� �ּ���. [��ũ=�����, �Ϲ�, �߽�]
					S_ServerMessage sm = new S_ServerMessage(781);
					pc.sendPackets(sm, true);
					return;
				}

				if (pc.isCrown()) { // ����
					String d = pc.getClan().getClanSubPrince();
					if (d == null || !d.equalsIgnoreCase(pc.getName())) {
						if (pc.getId() != clan.getLeaderId()) { // ������
							S_ServerMessage sm = new S_ServerMessage(785);
							pc.sendPackets(sm, true);
							return;
						}
					}
				} else {
					if (pc.getClanRank() != L1Clan.CLAN_RANK_GUARDIAN) {
						S_ServerMessage sm = new S_ServerMessage(518);
						pc.sendPackets(sm, true);
						return;
					}
				}
				if (pc.getName().equalsIgnoreCase(name)) {
					S_SystemMessage sm = new S_SystemMessage("�ڽſ��� ����� �ο� �� �� �����ϴ�.");
					pc.sendPackets(sm, true);
					return;
				}

				if (targetPc != null) { // �¶�����
					if (pc.getClanid() == targetPc.getClanid()) { // ���� ũ��
						try {
							if (rank == L1Clan.CLAN_RANK_GUARDIAN && targetPc.getLevel() < 40) {
								S_SystemMessage sm = new S_SystemMessage("��ȣ���� ���� 40�̻� ���Ϳ����Ը� �ο� �����մϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							if (rank == L1Clan.CLAN_RANK_GUARDIAN && pc.getClanRank() == L1Clan.CLAN_RANK_GUARDIAN) {
								S_SystemMessage sm = new S_SystemMessage("����� ��� �ο��� ������� ������  �� �� �ֽ��ϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							if (targetPc.getClanRank() == L1Clan.CLAN_RANK_PRINCE) {
								S_SystemMessage sm = new S_SystemMessage("������ ����� ���� �� �� �����ϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							int gc = 5;
							if (pc.getClan().getClanSubPrince() != null) {
								gc = 12;
							}
							if (pc.getClan().getGuardianCount() > gc && rank == L1Clan.CLAN_RANK_GUARDIAN) {
								S_SystemMessage sm = new S_SystemMessage("�Ϲ� ���� ��ȣ���� " + gc + "�� �̻��� �ɼ� �����ϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							if (rank == L1Clan.CLAN_RANK_���� && pc.getClan().get����Count() > 19) {
								S_SystemMessage sm = new S_SystemMessage("�������� " + gc + "�� �̻��� �ɼ� �����ϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							targetPc.setClanRank(rank);
							clan.setClanRankMember(targetPc.getName(), rank);
							targetPc.save(); // DB�� ĳ���� ������ �����Ѵ�
							String rankString = "�Ϲ�";
							if (rank == L1Clan.CLAN_RANK_PROBATION) {
								rankString = "����";
							} else if (rank == L1Clan.CLAN_RANK_PUBLIC) {
								rankString = "�Ϲ�";
							} else if (rank == L1Clan.CLAN_RANK_GUARDIAN) {
								rankString = "��ȣ���";
							} else if (rank == L1Clan.CLAN_RANK_����) {
								rankString = "�������";
							}
							// targetPc.sendPackets(new S_ServerMessage(784,
							// rankString)); // ����� ��ũ��%s�� ����Ǿ����ϴ�.
							S_SystemMessage sm = new S_SystemMessage("����� ����� " + rankString + "(��)�� ����Ǿ����ϴ�.");
							targetPc.sendPackets(sm, true);
							S_SystemMessage sm2 = new S_SystemMessage(
									targetPc.getName() + "�� ����� " + rankString + "(��)�� ����Ǿ����ϴ�.");
							pc.sendPackets(sm2, true);
						} catch (Exception e) {
							_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
						}
					} else {
						S_ServerMessage sm = new S_ServerMessage(414);
						pc.sendPackets(sm, true);
						return;
					}
				} else { // ���� ������
					L1PcInstance restorePc = CharacterTable.getInstance().restoreCharacter(name);
					if (restorePc != null && restorePc.getClanid() == pc.getClanid()) { // ����
																						// ũ��
						try {
							if (rank == L1Clan.CLAN_RANK_GUARDIAN && restorePc.getLevel() < 40) {
								S_SystemMessage sm = new S_SystemMessage("��ȣ���� ���� 40�̻� ���Ϳ����Ը� �ο� �����մϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							if (rank == L1Clan.CLAN_RANK_GUARDIAN && pc.getClanRank() == L1Clan.CLAN_RANK_GUARDIAN) {
								S_SystemMessage sm = new S_SystemMessage("����� ��� �ο��� ������� ������  �� �� �ֽ��ϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							if (restorePc.getClanRank() == L1Clan.CLAN_RANK_PRINCE) {
								S_SystemMessage sm = new S_SystemMessage("������ ����� ���� �� �� �����ϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
							restorePc.setClanRank(rank);
							clan.setClanRankMember(name, rank);
							restorePc.save(); // DB�� ĳ���� ������ �����Ѵ�
						} catch (Exception e) {
							_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
						}
					} else {
						S_ServerMessage sm = new S_ServerMessage(109, name);
						pc.sendPackets(sm, true);
						return;
					}
				}
				break;

			case 2:// ���
					// 5c 3e c7 c1 b8 ae bf a1 b9 d9 20 00 3f 53 ac 73
					// 5c 3e 45 76 61 73 74 6f 72 79 20 00 00 3d ea 03 // 10��21��
					// Ȯ��
					// �� /���� /����Ŭ������

				if (clan.AllianceSize() > 0) {
					S_PacketBox pb2 = new S_PacketBox(pc, S_PacketBox.ALLIANCE_LIST);
					pc.sendPackets(pb2, true);
					// pc.sendPackets(new S_PacketBox(S_PacketBox.PLEDGE_UNION,
					// clan.getAlliance()));
				} /*
					 * else { S_ServerMessage sm = new S_ServerMessage(1233);
					 * pc.sendPackets(sm, true); return; }
					 */
				break;
			case 3:// ����
				L1PcInstance allianceLeader = FaceToFace.faceToFace(pc);
				if (pc.getLevel() < 25 || !pc.isCrown()) {
					S_ServerMessage sm = new S_ServerMessage(1206);
					pc.sendPackets(sm, true);
					return;
				} /*
					 * if (pc.getClan().getAlliance() != 0) { pc.sendPackets(new
					 * S_ServerMessage(1202));// �̹� ���Ϳ� ���Ե� �����Դϴ�. return; }
					 */
				for (L1War war : L1World.getInstance().getWarList()) {
					if (war.CheckClanInWar(clanname)) {
						S_ServerMessage sm = new S_ServerMessage(1234);
						pc.sendPackets(sm, true);
						return;
					}
				}
				// ���� �� ����(4������) �߰��ؾ��� // 1201 // ���Ϳ� ������ �� �����ϴ�.
				if (clan.AllianceSize() > 4) {
					S_SystemMessage sm = new S_SystemMessage("������ 4�� ���� ������ �����մϴ�.");
					pc.sendPackets(sm, true);
					return;
				}
				if (allianceLeader != null) {
					if (allianceLeader.getLevel() > 24 && allianceLeader.isCrown()) {
						allianceLeader.setTempID(pc.getId());
						S_Message_YN yn = new S_Message_YN(223, pc.getName());
						allianceLeader.sendPackets(yn, true);
					} else {
						S_ServerMessage sm = new S_ServerMessage(1201);
						pc.sendPackets(sm, true);
					}
				}
				break;
			case 4:// Ż��
				for (L1War war : L1World.getInstance().getWarList()) {
					if (war.CheckClanInWar(clanname)) {
						S_ServerMessage sm = new S_ServerMessage(1203);
						pc.sendPackets(sm, true);
						return;
					}
				}
				if (clan.AllianceSize() > 0) {
					S_Message_YN yn = new S_Message_YN(1210, "");
					pc.sendPackets(yn, true);
				} else {
					S_ServerMessage sm = new S_ServerMessage(1233);
					pc.sendPackets(sm, true);
				}
				break;

			// /////////////////������ ��ħ
			case 5:
				try {
					int NewHp = 0;

					if (pc.get_food() >= 225) {
						try {
							Enchantlvl = pc.getWeapon().getEnchantLevel();
						} catch (Exception e) {
							S_ServerMessage sm = new S_ServerMessage(1973);
							pc.sendPackets(sm, true);
							return;
						}

						if ((60000L * 100) < System.currentTimeMillis() - pc.getSurvivalCry()) {

							NewHp = pc.getCurrentHp() + (Enchantlvl * 100);

							if (NewHp > pc.getMaxHp()) {
								NewHp = pc.getMaxHp();
							}

							pc.setCurrentHp(NewHp);
							S_SkillSound ss = null;
							if (pc.getWeapon().getItemId() == 61 || pc.getWeapon().getItemId() == 86)
								ss = new S_SkillSound(pc.getId(), 8773);
							else if (Enchantlvl <= 6)
								ss = new S_SkillSound(pc.getId(), 8684);
							else if ((Enchantlvl >= 7) && (Enchantlvl <= 8))
								ss = new S_SkillSound(pc.getId(), 8685);
							else if ((Enchantlvl >= 9) && (Enchantlvl <= 10))
								ss = new S_SkillSound(pc.getId(), 8773);
							else if (Enchantlvl >= 11)
								ss = new S_SkillSound(pc.getId(), 8686);
							pc.sendPackets(ss);
							Broadcaster.broadcastPacket(pc, ss, true);
							pc.set_food(0);
							S_PacketBox pb2 = new S_PacketBox(11, pc.get_food());
							pc.sendPackets(pb2, true);
							pc.setSurvivalCry(System.currentTimeMillis());
						} else {
							long time = 6000L - (System.currentTimeMillis() - pc.getSurvivalCry()) / 1000L;

							long minute = time / 60L;
							long second = time % 60L;

							if (minute < 100L) {
								S_SystemMessage sm = new S_SystemMessage(
										"\\fY������ ��ħ�� " + minute + "�� " + second + "�� �Ŀ� ���� �����մϴ�.");
								pc.sendPackets(sm, true);
								return;
							}
						}
					} else {
						S_ServerMessage sm2 = new S_ServerMessage(1974);
						pc.sendPackets(sm2, true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					clear();
				}
				break;
			case 6:
				try {
					if (pc.get_food() >= 225) {
						try {
							Enchantlvl = pc.getWeapon().getEnchantLevel();
						} catch (Exception e) {
							S_SystemMessage sm = new S_SystemMessage("\\fY���⸦ �����ؾ� ������ ��ħ�� ����� �� �ֽ��ϴ�.");
							pc.sendPackets(sm, true);
							return;
						}

						// ����, ���� ��þ9~10 ȿ�� ����
						if (pc.getWeapon().getItemId() == 61 || pc.getWeapon().getItemId() == 86) {
							S_SkillSound ss = new S_SkillSound(pc.getId(), 8773);
							pc.sendPackets(ss);
							Broadcaster.broadcastPacket(pc, ss, true);
						} else if (Enchantlvl <= 6) {
							S_SkillSound ss = new S_SkillSound(pc.getId(), 8684);
							pc.sendPackets(ss);
							Broadcaster.broadcastPacket(pc, ss, true);
						} else if ((Enchantlvl >= 7) && (Enchantlvl <= 8)) {
							S_SkillSound ss = new S_SkillSound(pc.getId(), 8685);
							pc.sendPackets(ss);
							Broadcaster.broadcastPacket(pc, ss, true);
						} else if ((Enchantlvl >= 9) && (Enchantlvl <= 10)) {
							S_SkillSound ss = new S_SkillSound(pc.getId(), 8773);
							pc.sendPackets(ss);
							Broadcaster.broadcastPacket(pc, ss, true);
						} else if (Enchantlvl >= 11) {
							S_SkillSound ss = new S_SkillSound(pc.getId(), 8686);
							pc.sendPackets(ss);
							Broadcaster.broadcastPacket(pc, ss, true);
						}
					} else {
						S_SystemMessage ss = new S_SystemMessage("\\fY������ ��ħ�� ����İ����� 100% ä���,");
						pc.sendPackets(ss, true);
						S_SystemMessage ss2 = new S_SystemMessage("\\fY����Ͻñ� �ٶ��ϴ�.");
						pc.sendPackets(ss2, true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 8:
				int time = (60 * 60 * 3) - pc.getgirantime();
				time = time > 0 ? time / 60 : 0;
				pc.sendPackets(new S_ServerMessage(2535, "���/�۷�������", time + ""), true);
				time = 3600 - pc.getivorytime();
				time = time > 0 ? time / 60 : 0;
				pc.sendPackets(new S_ServerMessage(2535, "���ž:�߷� ����", time + ""), true);
				time = 10800 - pc.getpc���time();
				time = time > 0 ? time / 60 : 0;
				pc.sendPackets(new S_ServerMessage(2535, "���� ����", time + ""), true);
				time = 3600 - pc.get������õ����time();
				time = time > 0 ? time / 60 : 0;
				pc.sendPackets(new S_ServerMessage(2535, "������ õ���� ���", time + ""), true);
				time = 3600 - pc.getivoryyaheetime();
				time = time > 0 ? time / 60 : 0;
				pc.sendPackets(new S_ServerMessage(2535, "���ž:���� ����(PC)", time + ""), true);
				time = 7200 - pc.getravatime();
				time = time > 0 ? time / 60 : 0;
				pc.sendPackets(new S_ServerMessage(2535, "��Ÿ�ٵ� ����", time + ""), true);
				break;
			case 9:
				pc.sendPackets(new S_PacketBox(pc, S_PacketBox.DG_TIME_RESTART), true);
				break;
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_RANK;
	}
}
