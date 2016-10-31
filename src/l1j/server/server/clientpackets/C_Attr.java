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

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;
import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.GhostHouse;
import l1j.server.GameSystem.PetRacing;
import l1j.server.GameSystem.Antaras.AntarasRaid;
import l1j.server.GameSystem.Antaras.AntarasRaidSystem;
import l1j.server.GameSystem.Antaras.AntarasRaidTimer;
import l1j.server.GameSystem.Gamble.GambleInstance;
import l1j.server.GameSystem.Lind.LindRaid;
import l1j.server.GameSystem.MiniGame.DeathMatch;
import l1j.server.GameSystem.Papoo.PaPooRaid;
import l1j.server.GameSystem.Papoo.PaPooRaidSystem;
import l1j.server.GameSystem.Papoo.PaPooTimer;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.BoardTable;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.HouseTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1ChatParty;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Party;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Question;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1BuffNpcInstance;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ChangeName;
import l1j.server.server.serverpackets.S_CharTitle;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ClanJoinLeaveStatus;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_DRAGONPERL;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_OwnCharStatus2;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_Resurrection;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_Trade;
import l1j.server.server.serverpackets.S_bonusstats;
import l1j.server.server.serverpackets.S_�����ֽ�;
import l1j.server.server.templates.L1BookMark;
import l1j.server.server.templates.L1House;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Pet;
import l1j.server.server.types.Point;
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;
import server.message.ServerMessage;

public class C_Attr extends ClientBasePacket {

	private static final Logger _log = Logger.getLogger(C_Attr.class.getName());
	private static final String C_ATTR = "[C] C_Attr";

	private static final int HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
	private static final int HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	public C_Attr(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);
		try {
			int i = readH();
			if (i != 479) {
				readD();
				i = readH();
			}
			int c;
			String name;

			L1PcInstance pc = clientthread.getActiveChar();

			if (pc.system != -1)
				i = 622;
			switch (i) {
			case 180:// ������ü������ �����ϱ�
				c = readC();
				String polys = readS();
				// L1PolyMorph poly =
				// PolyTable.getInstance().getTemplate(polys);
				
				if(polys.equalsIgnoreCase("ranking class polymorph"))
				{
						if(pc.isCrown()) {
							if(pc.get_sex() == 0)	polys = "rangking prince male";
							else					polys = "rangking prince female";
						} else if(pc.isKnight()) {
							if(pc.get_sex() == 0)	polys = "rangking knight male";
							else					polys = "rangking knight female";
						} else if(pc.isElf()) {
							if(pc.get_sex() == 0)	polys = "rangking elf male";
							else					polys = "rangking elf female";
						} else if(pc.isWizard()) {
							if(pc.get_sex() == 0)	polys = "rangking wizard male";
							else					polys = "rangking wizard female";
						} else if(pc.isDarkelf()) {
							if(pc.get_sex() == 0)	polys = "rangking darkelf male";
							else					polys = "rangking darkelf female";
						} else if(pc.isDragonknight()) {
							if(pc.get_sex() == 0)	polys = "rangking dragonknight male";
							else					polys = "rangking dragonknight female";
						} else if(pc.isIllusionist()) {
							if(pc.get_sex() == 0)	polys = "rangking illusionist male";
							else					polys = "rangking illusionist female";
						} else if(pc.isWarrior()) {
							if(pc.get_sex() == 0)	polys = "rangking warrior male";
							else					polys = "rangking warrior female";
						}
				}

				if(polys.startsWith("rangking "))
				{
					int star = UserRankingController.getInstance().getStarCount(pc.getName());
					if(star != 3 && star != 4)
						return;
				}
				
				L1PolyMorph.handleCommands(pc, polys);
				pc.cancelAbsoluteBarrier();
				break;
			case 3348:// ����� ��ﱸ��
				L1Clan target_clan = L1World.getInstance().getClan(
						pc.getClanid());
				L1Clan use_clan = L1World.getInstance().getClan(pc.get�ֽþ��̵�());
				L1PcInstance target_clanMaster = L1World.getInstance()
						.getPlayer(target_clan.getLeaderName());
				L1PcInstance use_clanMaster = L1World.getInstance().getPlayer(
						use_clan.getLeaderName());
				c = readC();
				if (c == 0) {// ����
					target_clanMaster.sendPackets(new S_SystemMessage(
							"���� �ֽ� : ���� �ֽ� ��û�� ������."));
					use_clanMaster.sendPackets(new S_SystemMessage(
							"���� �ֽ� : ���� �ֽ� ��û�� ������."));
					use_clanMaster.set�ֽþ��̵�(0);
					target_clanMaster.set�ֽþ��̵�(0);
				} else if (c == 1) {
					target_clan.addMarkSeeList(use_clan.getClanName());
					use_clan.addMarkSeeList(target_clan.getClanName());
					ClanTable.getInstance().insertObserverClan(
							target_clan.getClanId(), use_clan.getClanName());
					ClanTable.getInstance().insertObserverClan(
							use_clan.getClanId(), target_clan.getClanName());
					for (L1PcInstance tp : target_clan.getOnlineClanMember()) {
						tp.sendPackets(new S_�����ֽ�(pc.getClan(), 2), true);
						tp.sendPackets(new S_�����ֽ�(pc.getClan(), 0), true);
						tp.sendPackets(
								new S_ServerMessage(3360, use_clan
										.getClanName()), true);
					}
					for (L1PcInstance tp : use_clan.getOnlineClanMember()) {
						tp.sendPackets(new S_�����ֽ�(pc.getClan(), 2), true);
						tp.sendPackets(new S_�����ֽ�(pc.getClan(), 0), true);
						tp.sendPackets(
								new S_ServerMessage(3360, target_clan
										.getClanName()), true);
					}
					use_clanMaster.set�ֽþ��̵�(0);
					target_clanMaster.set�ֽþ��̵�(0);
				}

			case 2936:// ����� ��ﱸ��
				c = readC();
				if (c == 0) {
				} else if (c == 1) {
					int size = pc.getBookMarkSize();
					L1ItemInstance item = pc.getInventory().getItem(pc.����������);
					if (item.getItemId() == 60084) {
						int itemsize = L1BookMark.ItemBookmarkChehck(pc.����������);
						if (size + itemsize > pc.getBookmarkMax()) {
							pc.sendPackets(new S_ServerMessage(2961, ""
									+ (size - pc.getBookmarkMax() + itemsize)),
									true);
							pc.���������� = 0;
							return;
						}
						if (L1BookMark.ItemBookmarkLoad(pc, pc.����������))
							pc.getInventory().removeItem(item, 1);
					} else {
						if (size + 42 > pc.getBookmarkMax()) {
							pc.sendPackets(new S_ServerMessage(2961, ""
									+ (size - pc.getBookmarkMax() + 43)), true);
							pc.���������� = 0;
							return;
						}
						if (L1BookMark.�����۱��(pc, 42))
							pc.getInventory().removeItem(item, 1);
					}
				}
				pc.���������� = 0;
				break;
			case 3016: // �ź��� ��ﱸ��
				c = readC();
				if (c == 0) {
				} else if (c == 1) {
					int size2 = pc.getBookMarkSize();
					if (size2 + 8 > pc.getBookmarkMax()) {
						pc.sendPackets(new S_ServerMessage(2961, ""
								+ (size2 - pc.getBookmarkMax() + 8)), true);
						return;
					}
					L1ItemInstance item2 = pc.getInventory().getItem(pc.����������);
					if (L1BookMark.�����۱��(pc, 8))
						pc.getInventory().removeItem(item2, 1);
				}
				pc.���������� = 0;
				break;
			/*
			 * case 2935: //���ø��� ������屸�� c = readC(); if(c == 0){ }else if(c ==
			 * 1){ int size3 = pc.getBookMarkSize(); if (size3 <= 0) {
			 * pc.sendPackets(new S_ServerMessage(2963, ""), true); return; }
			 * L1ItemInstance item3 = pc.getInventory().getItem(pc.����������);
			 * pc.getInventory().removeItem(item3, 1);
			 * 
			 * L1ItemInstance item4 = ItemTable.getInstance().createItem(60084);
			 * item4.setCount(1); item4.setIdentified(true);
			 * item4.setCreaterName(pc.getName());
			 * pc.getInventory().storeItem(item4); //L1ItemInstance item4 =
			 * pc.getInventory().storeItem(60084, 1);
			 * L1BookMark.ItemaddBookmark(pc, item4.getId()); } pc.���������� = 0;
			 * break;
			 */
			// case 3055: // ã�� ���� �̸�
			// pc.����ã�� = false;
			// break;
			case 1565: // ��Ű �������
				c = readC();
				if (c == 0) {
				} else if (c == 1) {
					if (checkdragonkey(pc)) {
						L1ItemInstance dragonkey = pc.getInventory().getItem(
								pc.��Ű���üũid);
						// L1ItemInstance dragonkey =
						// pc.getInventory().findItemId(L1ItemId.DRAGON_KEY);
						BoardTable.getInstance().writeDragonKey(pc, dragonkey,
								currentTime(), 4212014);
						pc.sendPackets(new S_ServerMessage(1567), true);
						L1World.getInstance()
								.broadcastServerMessage(
										"��ö ��� ������: ������ �Է��Բ��� ��� �Ƶ� ����� �巡�� Ű�� ��Ÿ���ٰ� �Ͻʴϴ�. ���� ���� �巡�� �����̾�� ������ �ູ��!");
					}
				}
				pc.��Ű���üũid = 0;
				break;
			case 1906: // ���� ���� �� Ż�� ������
				c = readC();
				if (pc.getClanid() > 0) {
					if (c == 0) { // ���� Ż��
					} else if (c == 1) { // ���� ��û

						pc.sendPackets(new S_SystemMessage(
								"���ֿ��� ���� Ż�� ���� ������ ��û ���Դϴ�. ��� ��ٷ� �ּ���."));// ��û���Դϴ�
																			// ���
																			// ��ٷ���
						L1Clan clan = L1World.getInstance().getClan(
								pc.getClanname());
						L1PcInstance cl = L1World.getInstance().getPlayer(
								clan.getLeaderName());
						if (cl != null) {
							cl.sendPackets(new S_Message_YN(1908, pc.getName()));// Ż��
																					// ��û�Ͽ����ϴ�.
																					// ��������
																					// ������
							cl.setTempID(pc.getId());
						} else {
							boolean ck = false;
							String sm = pc.getClan().getClanSubPrince();
							if (sm != null) {
								L1PcInstance subM = L1World.getInstance()
										.getPlayer(sm);
								if (subM != null) {
									subM.sendPackets(new S_Message_YN(1908, pc
											.getName()));// Ż�� ��û�Ͽ����ϴ�. �������� ������
									subM.setTempID(pc.getId());
									ck = true;
								}
							}
							if (!ck)
								pc.sendPackets(new S_Message_YN(1914, null)); // ����/�α���
																				// ������
																				// ����
																				// Ż��
																				// �ϰڳ�?
						}
					}
				}
				break;
			case 1908: // ���� Ż�� ��û ó���ϱ�
				c = readC();
				L1PcInstance tpc = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getTempID());
				pc.setTempID(0);
				if (tpc != null && pc.getClanid() > 0) {
					if (c == 0) { // �ź�
						pc.sendPackets(new S_ServerMessage(1910, tpc.getName())); // %0��
																					// ����
																					// Ż��
																					// �ź�
																					// �Ͽ����ϴ�.
						tpc.sendPackets(new S_Message_YN(1901, null)); // �ź��ߴ�
																		// ���� Ż��
																		// �ҷ�?
					} else if (c == 1) { // ����
						leaveClan(tpc);
						pc.sendPackets(new S_ServerMessage(1909, tpc.getName())); // %0��
																					// ����
																					// Ż��
																					// �̷�������ϴ�.
						tpc.sendPackets(new S_ServerMessage(1900, null)); // ���ְ�
																			// ����
																			// Ż����
																			// ������
																			// ����
																			// �Ͽ����ϴ�.
					}
				}
			case 1914: // ����/�α��� ������ Ż�� �ϰڳ�?
				c = readC();
				if (pc.getClanid() > 0) {
					if (c == 1) {
						pc.sendPackets(new S_Message_YN(1915, null)); // ��¥ Ż��
																		// �Ұ�?
					}
				}
				break;
			case 1901: // ���� �ź����� ���� Ż�� �ҷ�?
			case 1915: // ������ ���� Ż�� �ϰڳ�?
				c = readC();
				if (pc.getClanid() > 0) {
					if (c == 1) {
						pc.sendPackets(new S_SystemMessage(pc.getClanname()
								+ " ���Ϳ��� ���� Ż�� �Ǿ����ϴ�. 3�ð��� ���� ���� ������ �ɸ��� �˴ϴ�."));
						leaveClan(pc);
						if (leaveNameCK == null) {
							leaveNameCK = new ClanLeave_joinNameCK();
							GeneralThreadPool.getInstance()
									.execute(leaveNameCK);
						}
						leaveNameCK.add(pc.getName(),
								System.currentTimeMillis() + (60000 * 60 * 1));
					}
				}
				break;
			case 97: // %0�� ���Ϳ� ������������ �ֽ��ϴ�. �³��մϱ�? (Y/N)
				c = readC();
				L1PcInstance joinPc = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getTempID());
				pc.setTempID(0);
				if (joinPc != null) {
					if (c == 0) { // No
						joinPc.sendPackets(new S_ServerMessage(96, pc.getName())); // \f1%0��
																					// �����
																					// ��û��
																					// �����߽��ϴ�.
					} else if (c == 1) { // Yes
						String clanName = pc.getClanname();
						L1Clan clan = L1World.getInstance().getClan(clanName);
						if (clan != null) {
							���Ͱ���(pc, joinPc, clan);
						}
					}
				}
				break;

			case 217: // %0������%1�� ����� ���Ͱ��� ������ �ٶ�� �ֽ��ϴ�. ���￡ ���մϱ�? (Y/N)
			case 221: // %0������ �׺��� �ٶ�� �ֽ��ϴ�. �޾Ƶ��Դϱ�? (Y/N)
			case 222: // %0������ ������ ������ �ٶ�� �ֽ��ϴ�. �����մϱ�? (Y/N)
				c = readC();
				L1PcInstance enemyLeader = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getTempID());
				if (enemyLeader == null) {
					return;
				}
				pc.setTempID(0);
				String clanName = pc.getClanname();
				String enemyClanName = enemyLeader.getClanname();
				if (c == 0) { // No
					if (i == 217) {
						enemyLeader.sendPackets(new S_ServerMessage(236,
								clanName)); // %0������ ����� ���Ͱ��� ������ �����߽��ϴ�.
					} else if (i == 221 || i == 222) {
						enemyLeader.sendPackets(new S_ServerMessage(237,
								clanName)); // %0������ ����� ������ �����߽��ϴ�.
					}
				} else if (c == 1) { // Yes
					if (i == 217) {
						L1War war = new L1War();
						war.handleCommands(2, enemyClanName, clanName); // ������
																		// ����
					} else if (i == 221 || i == 222) {
						for (L1War war : L1World.getInstance().getWarList()) { // ����
																				// ����Ʈ��
																				// ���
							if (war.CheckClanInWar(clanName)) { // ��ũ���� ���� �ִ�
																// ������ �߰�
								if (i == 221) {
									war.SurrenderWar(enemyClanName, clanName); // �׺�
								} else if (i == 222) {
									war.CeaseWar(enemyClanName, clanName); // ����
								}
								break;
							}
						}
					}
				}
				break;

			case 223: // %0%s ������ ���մϴ�. �޾Ƶ��̽ðڽ��ϱ�? (Y/N)
				c = readC();
				L1PcInstance allianceLeader = (L1PcInstance) L1World
						.getInstance().findObject(pc.getTempID());
				if (allianceLeader == null)
					return;
				pc.setTempID(0);
				int PcClanId = pc.getClanid();
				int TargetClanId = allianceLeader.getClanid();
				String PcClanName = pc.getClanname();
				String TargetClanName = allianceLeader.getClanname();
				L1Clan PcClan = L1World.getInstance().getClan(PcClanName);
				L1Clan TargegClan = L1World.getInstance().getClan(
						TargetClanName);
				if (c == 1) { // Yes
					PcClan.addAlliance(TargetClanId);
					// PcClan.setAlliance(TargetClanId);
					// TargegClan.setAlliance(PcClanId);
					TargegClan.addAlliance(PcClanId);
					ClanTable.getInstance().updateClan(PcClan);
					ClanTable.getInstance().updateClan(TargegClan);
					pc.sendPackets(new S_ServerMessage(1200, TargetClanName));// %0
																				// ������
																				// ���Ϳ�
																				// ���ԵǾ����ϴ�.
					allianceLeader.sendPackets(new S_ServerMessage(224,
							PcClanName, TargetClanName));// %0 ���Ͱ� %1 ������ ������
															// �ξ����ϴ�.
				} else if (c == 0) { // No

					pc.sendPackets(new S_SystemMessage("��밡 ������ �����߽��ϴ�."));// ��밡
																			// ������
																			// �����߽��ϴ�.
				}

			case 1210: // ������ ������ Ż���Ͻðڽ��ϱ�? (Y/N)
				if (readC() == 1) {
					if (pc.getClan() != null && pc.getClan().AllianceSize() > 0) {
						for (int clanid : pc.getClan().Alliance()) {
							if (clanid == 0)
								continue;
							L1Clan clan = L1World.getInstance().getClan(clanid);
							if (clan == null)
								continue;
							for (L1PcInstance tempPc : clan
									.getOnlineClanMember()) {
								tempPc.sendPackets(new S_ServerMessage(1204, pc
										.getClan().getClanName()));
							}
							clan.removeAlliance(pc.getClanid());
							ClanTable.getInstance().updateClan(clan);
						}
					}
					pc.sendPackets(new S_SystemMessage(pc.getClanname()
							+ " ������ ������ Ż�� �Ͽ����ϴ�."));
					pc.getClan().AllianceDelete();
					ClanTable.getInstance().updateClan(pc.getClan());
				}
				break;
			case 252: // %0%s�� ��Ű� �������� �ŷ��� �ٶ�� �ֽ��ϴ�. �ŷ��մϱ�? (Y/N)
				c = readC();
				if (pc.getTradeID() == 0 || pc.getTradeReady())
					return;
				L1Object trading_partner = L1World.getInstance().findObject(
						pc.getTradeID());
				if (trading_partner != null) {
					if (trading_partner instanceof L1PcInstance) {
						L1PcInstance target = (L1PcInstance) trading_partner;
						if (target.getTradeReady())
							return;
						if (c == 0) { // No
							target.sendPackets(new S_ServerMessage(253, pc
									.getName())); // %0%d�� ��Ű��� �ŷ��� ������ �ʾҽ��ϴ�.
							pc.setTradeID(0);
							target.setTradeID(0);
							pc.setTradeReady(false);
							target.setTradeReady(false);
						} else if (c == 1) { // Yes
							if (pc.getLocation().getTileLineDistance(
									new Point(target.getX(), target.getY())) > 20) {
								pc.sendPackets(new S_SystemMessage(
										"���� �Ÿ��� �ʹ� �־� �ŷ��� �� �� �����ϴ�."));

								pc.setTradeID(0);
								target.setTradeID(0);
								pc.setTradeReady(false);
								target.setTradeReady(false);
								return;
							}

							pc.sendPackets(new S_Trade(target.getName()));
							target.sendPackets(new S_Trade(pc.getName()));
							pc.setTradeReady(true);
							target.setTradeReady(true);
						}
					} else if (trading_partner instanceof L1BuffNpcInstance) {
						L1BuffNpcInstance target = (L1BuffNpcInstance) trading_partner;
						if (c == 0) { // No
							pc.setTradeID(0);
							target.setTradeID(0);
							pc.setTradeReady(false);
						} else if (c == 1) { // Yes
							pc.sendPackets(new S_Trade(target.getName()));
							pc.setTradeReady(true);
							target.setTradeID(pc.getId()); // ����� ������Ʈ ID�� ������
															// �д�
						}
					} else if (trading_partner instanceof L1NpcShopInstance) {
						L1NpcShopInstance target = (L1NpcShopInstance) trading_partner;
						if (c == 0) { // No
							pc.setTradeID(0);
							pc.setTradeReady(false);
						} else if (c == 1) { // Yes
							target.Npc_trade = true;
							pc.setTradeReady(true);
							pc.sendPackets(new S_Trade(target.getName()));
						}
					} else if (trading_partner instanceof GambleInstance) {
						GambleInstance target = (GambleInstance) trading_partner;
						if (c == 0) { // No
							pc.setTradeID(0);
							pc.setTradeReady(false);
						} else if (c == 1) { // Yes
							if (target.getTileLineDistance(pc) > 1) {
								pc.setTradeID(0);
								pc.setTradeReady(false);
								pc.sendPackets(new S_SystemMessage(
										"�Ÿ��� �־� ���� �� �� �����ϴ�."));
								return;
							}
							target.Npc_trade = true;
							pc.setTradeReady(true);
							pc.sendPackets(new S_Trade(target.getName()));
						}
					}

				}
				break;

			case 321: // �� ��Ȱ�ϰ� �ͽ��ϱ�? (Y/N)
				c = readC();
				L1PcInstance resusepc1 = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getTempID());
				pc.setTempID(0);
				if (resusepc1 != null) { // ��Ȱ ��ũ��
					if (c == 0) { // No
						;
					} else if (c == 1) { // Yes
						if (pc.isInParty()) {// ��Ƽ�߰�
							if (pc.isDead()) {
								pc.getParty().refresh(pc);
							}
						}
						pc.sendPackets(new S_SkillSound(pc.getId(), '\346'));
						Broadcaster.broadcastPacket(pc,
								new S_SkillSound(pc.getId(), '\346'));
						pc.resurrect(pc.getMaxHp() / 2);
						pc.setCurrentHp(pc.getMaxHp() / 2);
						// pc.startHpRegeneration();
						// pc.startMpRegeneration();
						pc.startMpRegenerationByDoll();
						pc.sendPackets(new S_Resurrection(pc, resusepc1, 0));
						Broadcaster.broadcastPacket(pc, new S_Resurrection(pc,
								resusepc1, 0));
						pc.sendPackets(new S_CharVisualUpdate(pc));
						Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(
								pc));
					}
				}
				break;

			case 322: // �� ��Ȱ�ϰ� �ͽ��ϱ�? (Y/N)
				c = readC();
				L1PcInstance resusepc2 = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getTempID());
				pc.setTempID(0);
				if (resusepc2 != null) { // �ູ�� ��Ȱ ��ũ��, ���ڷ�ũ��, �׷���Ÿ���ڷ�ũ��
					if (c == 0) { // No
						;
					} else if (c == 1) { // Yes
						if (pc.isInParty()) {// ��Ƽ�߰�
							if (pc.isDead()) {
								pc.getParty().refresh(pc);
							}
						}
						pc.sendPackets(new S_SkillSound(pc.getId(), '\346'));
						Broadcaster.broadcastPacket(pc,
								new S_SkillSound(pc.getId(), '\346'));
						pc.resurrect(pc.getMaxHp());
						pc.setCurrentHp(pc.getMaxHp());
						// pc.startHpRegeneration();
						// pc.startMpRegeneration();
						pc.startMpRegenerationByDoll();
						pc.startMpRegenerationByDoll();
						pc.sendPackets(new S_Resurrection(pc, resusepc2, 0));
						Broadcaster.broadcastPacket(pc, new S_Resurrection(pc,
								resusepc2, 0));
						pc.sendPackets(new S_CharVisualUpdate(pc));
						Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(
								pc));
						// EXP �ν�Ʈ �ϰ� �ִ�, G-RES�� �� �� �ִ�, EXP �ν�Ʈ �� ���
						// ��θ� ä��� ��츸 EXP ����
						if (pc.getExpRes() == 1 && pc.isGres()
								&& pc.isGresValid()) {
							pc.resExp();
							pc.setExpRes(0);
							pc.setGres(false);
						}
					}
				}
				break;

			case 325: // ������ �̸��� ������ �ּ��䣺
				c = readC(); // ?
				name = readS();
				L1PetInstance pet = (L1PetInstance) L1World.getInstance()
						.findObject(pc.getTempID());
				pc.setTempID(0);
				renamePet(pet, name);
				break;

			case 512: // ���� �̸���?
				c = readC(); // ?
				name = readS();
				int houseId = pc.getTempID();
				if (houseId == 0)
					return;
				pc.setTempID(0);
				if (name.length() <= 16) {
					L1House house = HouseTable.getInstance().getHouseTable(
							houseId);
					house.setHouseName(name);
					HouseTable.getInstance().updateHouse(house); // DB�� ������
				} else {

					pc.sendPackets(new S_SystemMessage("�� �̸��� �ʹ� ��ϴ�.")); // ����
																			// �̸���
																			// �ʹ�
																			// ��ϴ�.
				}
				break;

			case 622: // ����
				c = readC();
				 switch (pc.getMsgType()) {

		         
				 //�׽�Ʈ �����׽�Ʈ
				 case L1PcInstance.SPAWN_GIRTAS:


		                pc.setMsgType(0);

		                if (c == 0) {

		                } else if (c == 1) {

		                    // �ڷ���Ʈ ó��

		                }

		                break;
				 }
				 //�׽�Ʈ
				
				switch (pc.system) {
				case 1:
					pc.system = -1;// �ʱ�ȭ
					if (c == 0) {
					} else if (c == 1) {
						int count = 0;
						int trcount = 0;
						AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(
								pc.dragonmapid);
						int count1 = ar.countLairUser();
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.DRAGONBLOOD_A)) {
							pc.sendPackets(new S_SystemMessage(
									"���� ������ �־� ���� �� �� �����ϴ�."));
							return;
						}
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.DRAGONRAID_BUFF)) {
							pc.sendPackets(new S_SystemMessage(
									"�巡�� ���̵� �������� ���� �巡�� ��Ż�� ���� �� �� �����ϴ�."));
							return;
						}
						if (count1 > 0) {
							for (L1PcInstance player : L1World.getInstance()
									.getAllPlayers()) {
								if (player.getMapId() == pc.dragonmapid) {
									trcount++;
								}
							}
							if (trcount == 0) {
								for (L1Object npc : L1World.getInstance()
										.getObject()) {
									if (npc instanceof L1MonsterInstance) {
										if (npc.getMapId() == pc.dragonmapid) {
											L1MonsterInstance _npc = (L1MonsterInstance) npc;
											_npc.deleteMe();
										}
									}
								}
								ar.clLairUser();
								ar.setAntaras(false);
								ar.setanta(null);
							}
						}
						for (L1PcInstance player : L1World.getInstance()
								.getAllPlayers()) {
							if (player.getMapId() == pc.dragonmapid) {
								count += 1;
								if (count > 31) {
									pc.sendPackets(new S_SystemMessage(
											"���� ���� �ο����� �ʰ� �Ͽ����ϴ�."));
									return;
								}
							}
						}
						L1Teleport.teleport(pc, 32668, 32675,
								(short) pc.dragonmapid, 5, true);
					}
					break;
				case 2:
					pc.system = -1;// �ʱ�ȭ
					if (c == 0) {
					} else if (c == 1) {
						int count = 0;
						int trcount = 0;
						PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
								pc.dragonmapid);
						int count1 = ar.countLairUser();
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.DRAGONBLOOD_P)) {
							pc.sendPackets(new S_SystemMessage(
									"���� ������ �־� ���� �� �� �����ϴ�."));
							return;
						}

						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.DRAGONRAID_BUFF)) {
							pc.sendPackets(new S_SystemMessage(
									"�巡�� ���̵� �������� ���� �巡�� ��Ż�� ���� �� �� �����ϴ�."));
							return;
						}
						if (count1 > 0) {
							for (L1PcInstance player : L1World.getInstance()
									.getAllPlayers()) {
								if (player.getMapId() == pc.dragonmapid) {
									trcount++;
								}
							}
							if (trcount == 0) {
								for (L1Object npc : L1World.getInstance()
										.getObject()) {
									if (npc instanceof L1MonsterInstance) {
										if (npc.getMapId() == pc.dragonmapid) {
											L1MonsterInstance _npc = (L1MonsterInstance) npc;
											_npc.deleteMe();
										}
									}
								}
								ar.clLairUser();
								ar.setPapoo(false);
								ar.setPaPoo(null);
							}
						}
						for (L1PcInstance player : L1World.getInstance()
								.getAllPlayers()) {
							if (player.getMapId() == pc.dragonmapid) {
								count += 1;
								if (count > 31) {
									pc.sendPackets(new S_SystemMessage(
											"���� ���� �ο����� �ʰ� �Ͽ����ϴ�."));
									return;
								}
							}
						}
						L1Teleport.teleport(pc, 32920, 32672,
								(short) pc.dragonmapid, 5, true);
					}
					break;
				case 3:
					pc.system = -1;// �ʱ�ȭ
					if (c == 0) {
					} else if (c == 1) {
						AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(
								pc.dragonmapid);
						/*
						 * L1Party pcpt = pc.getParty(); if(pcpt == null){
						 * pc.sendPackets(new
						 * S_SystemMessage("����� ��Ƽ ���� �ƴմϴ�.")); return; }
						 * if(pcpt.getLeader().getName() != pc.getName()){
						 * pc.sendPackets(new
						 * S_SystemMessage("��Ƽ�常 ������ �� �� �ֽ��ϴ�.")); return; }
						 */
						boolean ok = true;
						int num = 0;
						/*
						 * for (int i1 = 1; i1 < 4 ; i1++){ num --; ok =
						 * ar.Check(i1); if(ok == false){ ar.setParty(pcpt, i1);
						 * System.out.println("��Ƽ ��� "+i1+"�� ° �� ���"); break; }
						 * }
						 */

						num = -1;
						ok = ar.Check(1);
						ar.addMember(pc);
						if (ok == true) {

							// pc.sendPackets(new
							// S_SystemMessage("�̹� ���۵Ǿ����ϴ�."));
							// return;
						} else {
							AntarasRaidTimer art = new AntarasRaidTimer(null,
									ar, num, 10000);// 2�� üũ
							art.begin();
							ar.MiniRoom1 = true;
						}
						num *= -1;

						switch (num) {
						case 1:
							if (pc.getMapId() != pc.dragonmapid) {
								return;
							}
							L1Teleport.teleport(pc, 32679, 32803,
									(short) pc.dragonmapid, 5, true);
							break;
						/*
						 * case 1: for (L1PcInstance mem : pcpt.getMembers()) {
						 * if(mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32679, 32803, (short)
						 * pc.dragonmapid, 5, true); } break; case 2: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if(mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32935, 32611, (short)
						 * pc.dragonmapid, 5, true); } break; case 3: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if(mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32935, 32803, (short)
						 * pc.dragonmapid, 5, true); } break; case 4: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if(mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32807, 32803, (short)
						 * pc.dragonmapid, 5, true); } break;
						 */
						case 5:
							pc.sendPackets(new S_SystemMessage("�̹� ���۵Ǿ����ϴ�."));
							break;
						}

					}
					break;

				case 4:
					pc.system = -1;// �ʱ�ȭ
					if (c == 0) {
					} else if (c == 1) {
						PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
								pc.dragonmapid);
						// System.out.println(pc.dragonmapid);
						/*
						 * L1Party pcpt = pc.getParty(); if(pcpt == null){
						 * pc.sendPackets(new
						 * S_SystemMessage("����� ��Ƽ ���� �ƴմϴ�.")); return; }
						 * if(pcpt.getLeader().getName() != pc.getName()){
						 * pc.sendPackets(new
						 * S_SystemMessage("��Ƽ�常 ������ �� �� �ֽ��ϴ�.")); return; }
						 */
						boolean ok = true;
						int num = 0;
						/*
						 * for (int i1 = 1; i1 < 4 ; i1++){ num --; ok =
						 * ar.Check(i1); if(ok == false){ ar.setParty(pcpt, i1);
						 * System.out.println("��Ǫ �ܰ��Ʈ ��Ƽ ��� "+i1+"�� ° �� ���");
						 * break; } }
						 */
						num = -1;
						ok = ar.Check(1);
						ar.addMember(pc);
						if (ok == true) {
							// pc.sendPackets(new
							// S_SystemMessage("�̹� ���۵Ǿ����ϴ�."));
							// return;
						} else {

							PaPooTimer art = new PaPooTimer(null, ar, num,
									10000);// 2�� üũ
							art.begin();
							ar.MiniRoom1 = true;
						}
						num *= -1;
						switch (num) {
						case 1:
							if (pc.getMapId() != pc.dragonmapid) {
								return;
							}
							L1Teleport.teleport(pc, 32759, 32852,
									(short) pc.dragonmapid, 5, true);
							break;
						/*
						 * case 1:
						 * 
						 * for (L1PcInstance mem : pcpt.getMembers()) {
						 * if(mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32759, 32852, (short)
						 * pc.dragonmapid, 5, true); } break; case 2: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if(mem.getMapId() != pc.dragonmapid){ continue; }
						 * 
						 * L1Teleport.teleport(mem, 32759, 32726, (short)
						 * pc.dragonmapid, 5, true); } break; case 3: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if(mem.getMapId() != pc.dragonmapid){ continue; }
						 * 
						 * L1Teleport.teleport(mem, 32759, 32597, (short)
						 * pc.dragonmapid, 5, true); } break; case 4: for
						 * (L1PcInstance mem : pcpt.getMembers()) {
						 * if(mem.getMapId() != pc.dragonmapid){ continue; }
						 * L1Teleport.teleport(mem, 32952, 32596, (short)
						 * pc.dragonmapid, 5, true); } break;
						 */
						case 5:
							pc.sendPackets(new S_SystemMessage("�̹� ���۵Ǿ����ϴ�."));
							break;
						}

					}
					break;
				// ����
				case 5:
					pc.system = -1;// �ʱ�ȭ
					if (c == 0) {
					} else if (c == 1) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.DRAGONBLOOD_L)) {
							pc.sendPackets(new S_SystemMessage(
									"���� ������ �־� ���� �� �� �����ϴ�."));
							return;
						}
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.DRAGONRAID_BUFF)) {
							pc.sendPackets(new S_SystemMessage(
									"�巡�� ���̵� �������� ���� �巡�� ��Ż�� ���� �� �� �����ϴ�."));
							return;
						}
						int count = 0;
						for (L1PcInstance player : L1World.getInstance()
								.getAllPlayers()) {
							if (player.getMapId() == pc.dragonmapid) {
								count += 1;
								if (count > 31) {
									pc.sendPackets(new S_SystemMessage(
											"���� ���� �ο����� �ʰ� �Ͽ����ϴ�."));
									return;
								}
							}
						}
						LindRaid.get().in(pc);
					}
					break;
				default:
					pc.system = -1;// �ʱ�ȭ
					if (c == 0)
						L1Question.bad += 1;
					else if (c == 1)
						L1Question.good += 1;
					break;
				}
				break;
			case 630:
				c = readC();
				L1PcInstance fightPc = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getFightId());
				if (c == 0) {
					pc.setFightId(0);
					fightPc.setFightId(0);
					fightPc.sendPackets(new S_ServerMessage(631, pc.getName()));
				} else if (c == 1) {
					fightPc.sendPackets(new S_PacketBox(S_PacketBox.MSG_DUEL,
							fightPc.getFightId(), fightPc.getId()));
					pc.sendPackets(new S_PacketBox(S_PacketBox.MSG_DUEL, pc
							.getFightId(), pc.getId()));
				}
				break;

			case 653: // ��ȥ�� �ϸ�(��) ���� ����� �����ϴ�.��ȥ�� �ٶ��ϱ�? (Y/N)
				c = readC();
				L1PcInstance par = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getPartnerId());
				if (c == 0) { // No
					;
				} else if (c == 1) { // Yes
					// if(pc.getInventory().checkItem(40308, 10000000)){//���ڷ� BY
					// ���̳�
					// pc.getInventory().consumeItem(40308, 10000000);
					// pc.sendPackets(new
					// S_SystemMessage("\\fC��ȥ ���ڷ�� õ������ �Һ��߽��ϴ�.")); //
					if (pc.getInventory().checkItem(40901))
						pc.getInventory().consumeItem(40901,
								pc.getInventory().countItems(40901));
					if (pc.getInventory().checkItem(40902))
						pc.getInventory().consumeItem(40902,
								pc.getInventory().countItems(40902));
					if (pc.getInventory().checkItem(40903))
						pc.getInventory().consumeItem(40903,
								pc.getInventory().countItems(40903));
					if (pc.getInventory().checkItem(40904))
						pc.getInventory().consumeItem(40904,
								pc.getInventory().countItems(40904));
					if (pc.getInventory().checkItem(40905))
						pc.getInventory().consumeItem(40905,
								pc.getInventory().countItems(40905));
					if (pc.getInventory().checkItem(40906))
						pc.getInventory().consumeItem(40906,
								pc.getInventory().countItems(40906));
					if (par != null) {
						par.sendPackets(new S_SystemMessage(pc.getName()
								+ "�� ���� ��Ű� ��ȥ�� �Ͽ����ϴ�..")); //
						par.setPartnerId(0);
						par.save(); // DB�� ĳ���� ������ �����Ѵ�
					} else {
						phone(pc.getPartnerId());
					}
					pc.setPartnerId(0);
					pc.save(); // DB�� ĳ���� ������ �����Ѵ�
					// }
					// else{
					// pc.sendPackets(new
					// S_SystemMessage("\\fC��ȥ�� �Ϸ��� ���ڷ� õ������ �ʿ��մϴ�")); //

					// }
				}
				break;

			case 654: // %0%s��Ű� ��ȥ �ϰ� �;��ϰ� �ֽ��ϴ�. %0�� ��ȥ�մϱ�? (Y/N)
				c = readC();
				L1PcInstance partner = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getTempID());
				pc.setTempID(0);
				if (partner != null) {
					if (c == 0) { // No
						partner.sendPackets(new S_ServerMessage(656, pc
								.getName())); // %0%s�� ��Ű��� ��ȥ�� �����߽��ϴ�.
					} else if (c == 1) { // Yes
						pc.setPartnerId(partner.getId());
						pc.save();

						pc.sendPackets(new S_SystemMessage(
								"�� ����� ��ȥ�� ��� ����� �ູ�ӿ� �̷�������ϴ�.")); // ����� �ູ
																	// ��(��)����, ��
																	// ���� ��ȥ��
																	// �߽��ϴ�.
						pc.sendPackets(new S_ServerMessage(655, partner
								.getName())); // �����մϴ�! %0�� ��ȥ�߽��ϴ�.
						pc.sendPackets(new S_SkillSound(pc.getId(), 2059));
						Broadcaster.broadcastPacket(pc,
								new S_SkillSound(pc.getId(), 2059));
						partner.setPartnerId(pc.getId());
						partner.save();
						partner.sendPackets(new S_ServerMessage(790)); // ����� �ູ
																		// ��(��)����,
																		// �� ����
																		// ��ȥ��
																		// �߽��ϴ�.
						partner.sendPackets(new S_ServerMessage(655, pc
								.getName())); // �����մϴ�! %0�� ��ȥ�߽��ϴ�.
						partner.sendPackets(new S_SkillSound(partner.getId(),
								2059));
						Broadcaster.broadcastPacket(partner, new S_SkillSound(
								partner.getId(), 2059));
					}
				}
				break;

			// �� ũ��
			case 729: // ���Ϳ��� ����� �ڷ���Ʈ ��Ű���� �ϰ� �ֽ��ϴ�. ���մϱ�? (Y/N)
				c = readC();
				if (c == 0) {
				} else if (c == 1) { // Yes
					callClan(pc);
				}
				break;

			case 738:// ����ġ�� ȸ���Ϸ���%0�� �Ƶ����� �ʿ��մϴ�. ����ġ�� ȸ���մϱ�?
				c = readC();
				if (c == 0) {
				} else if (c == 1 && pc.getExpRes() == 1) { // Yes
					// int cost = readD();
					int cost = 0;
					int level = pc.getLevel();
					int lawful = pc.getLawful();

					if (level < 45) {
						cost = level * level * 50;
					} else {
						cost = level * level * 100;
					}

					if (lawful >= 0) {
						cost = (cost / 2);
					}

					cost *= 2;

					if (cost >= 200000000 || cost < 0)
						return;

					if (pc.getInventory().consumeItem(L1ItemId.ADENA, cost)) {
						pc.resExpToTemple();
						pc.setExpRes(0);
					} else {
						pc.sendPackets(new S_SystemMessage("�Ƶ����� ���ġ �ʽ��ϴ�."),
								true);// \f1�Ƶ����� �����մϴ�.
					}

				}
				break;
			case 2551:// ��ȣ������ �̿��� ȸ��
				c = readC();
				if (c == 0) {
				} else if (c == 1 && pc.getExpRes() == 1) { // Yes
					if (pc.getInventory().consumeItem(600256, 1)) {
						pc.resExpTo��ȣ();
						pc.setExpRes(0);
					} else {
						pc.sendPackets(new S_SystemMessage("��ȣ ������ �ʿ� �մϴ�."),
								true);// \f1�Ƶ����� �����մϴ�.
					}
				}
				break;

			case 951: // ä�� ��Ƽ �ʴ븦 �㰡�մϱ�? (Y/N)
				c = readC();
				L1PcInstance chatPc = (L1PcInstance) L1World.getInstance()
						.findObject(pc.getPartyID());
				pc.setPartyID(0);
				if (chatPc != null) {
					if (c == 0) { // No
						chatPc.sendPackets(new S_ServerMessage(423, pc
								.getName())); // %0�� �ʴ븦 �ź��߽��ϴ�.
					} else if (c == 1) { // Yes
						if (chatPc.isInChatParty()) {
							if (chatPc.getChatParty().isVacancy()
									|| chatPc.isGm()) {
								chatPc.getChatParty().addMember(pc);
							} else {
								chatPc.sendPackets(new S_ServerMessage(417)); // ��
																				// �̻�
																				// ��Ƽ
																				// �����
																				// �޾Ƶ���
																				// ��
																				// �����ϴ�.
							}
						} else {
							L1ChatParty chatParty = new L1ChatParty();
							chatParty.addMember(chatPc);
							chatParty.addMember(pc);
							chatPc.sendPackets(new S_ServerMessage(424, pc
									.getName())); // %0�� ��Ƽ�� �����ϴ�.
						}
					}
				}
				break;

			case 953: // ��Ƽ �ʴ븦 �㰡�մϱ�? (Y/N)
			case 954:
				c = readC();
				L1PcInstance target = (L1PcInstance) L1World.getInstance().findObject(pc.getPartyID());
				if (target != null) {
					if (c == 0) { // No
						target.sendPackets(new S_ServerMessage(423, pc.getName())); // %0�� �ʴ븦 �ź��߽��ϴ�.
						pc.setPartyID(0);
					} else if (c == 1) { // Yes
						/** ��Ʋ�� **/
						if (target.getMapId() == 5153 || target.getMapId() == 5001 || pc.getMapId() == 5153 || pc.getMapId() == 5001) {
							target.sendPackets(new S_ServerMessage(423, pc.getName())); // %0�� �ʴ븦 �ź��߽��ϴ�.
							return;
						}

						if (target.isInParty()) { // �ʴ��ְ� ��Ƽ��
							if (target.getParty().isVacancy() || target.isGm()) { // ��Ƽ�� �� ���� �ִ�
								target.getParty().addMember(pc);
							} else { // ��Ƽ�� �� ���� ����
								target.sendPackets(new S_ServerMessage(417)); // �� �̻� ��Ƽ ����� �޾Ƶ��� �� �����ϴ�.
							}
						} else { // �ʴ��ְ� ��Ƽ���� �ƴϴ�
							L1Party party = new L1Party();
							party.addMember(target);
							party.addMember(pc);
							target.sendPackets(new S_ServerMessage(424, pc.getName())); // %0�� ��Ƽ�� �����ϴ�.
						}
					}
				}
				break;

			case 1256: // ����忡 �����Ͻðڽ��ϱ�? (Y/N)
				c = readC();
				if (c == 0) {
					miniGameRemoveEnterMember(pc);
				} else if (c == 1) {
					if (pc.getInventory().checkItem(L1ItemId.ADENA, 1000)) {
						pc.getInventory().consumeItem(L1ItemId.ADENA, 1000);
						if (pc.isInParty())
							pc.getParty().leaveMember(pc);
						L1SkillUse l1skilluse = new L1SkillUse();
						l1skilluse.handleCommands(pc, L1SkillId.CANCELLATION,
								pc.getId(), pc.getX(), pc.getY(), null, 0,
								L1SkillUse.TYPE_LOGIN);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.STATUS_DRAGONPERL)) {
							pc.getSkillEffectTimerSet().killSkillEffectTimer(
									L1SkillId.STATUS_DRAGONPERL);
							pc.sendPackets(new S_PacketBox(
									S_PacketBox.DRAGONPERL, 0, 0));
							Broadcaster.broadcastPacket(pc,
									new S_DRAGONPERL(pc.getId(), 0));
							pc.sendPackets(new S_DRAGONPERL(pc.getId(), 0));
							pc.set���ּӵ�(0);
						}

						if (GhostHouse.getInstance().isEnterMember(pc)) {
							if (GhostHouse.getInstance().isPlayingNow()) {
								pc.sendPackets(new S_ServerMessage(1182));
								return;
							}
							for (L1DollInstance doll : pc.getDollList()) {
								doll.deleteDoll();
							}
							GhostHouse.getInstance().addPlayMember(pc);
							L1Teleport.teleport(pc, 32722, 32830, (short) 5140,
									2, true);
						} else if (PetRacing.getInstance().isEnterMember(pc)) {
							if (PetRacing.getInstance().isPlay()) {
								pc.sendPackets(new S_ServerMessage(1182));
								return;
							}
							for (L1DollInstance doll : pc.getDollList()) {
								doll.deleteDoll();
							}
							pc.setPetRacing(true);
							PetRacing.getInstance().removeEnterMember(pc);
							PetRacing.getInstance().addPlayMember(pc);
							L1Teleport.teleport(pc, 32768, 32848, (short) 5143,
									5, true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("�Ƶ����� ���ġ �ʽ��ϴ�."));// \f1�Ƶ�����
																				// �����մϴ�.
						miniGameRemoveEnterMember(pc);
					}
				}
				break;

			case 1268: // ������ġ�� �����Ͻðڽ��ϱ�? (Y/N)
				c = readC();
				if (c == 0)
					DeathMatch.getInstance().giveBackAdena(pc);
				else if (c == 1)
					DeathMatch.getInstance().addPlayMember(pc);
				break;
			case 479: // ��� �ɷ�ġ�� ����ŵ�ϱ�? (str, dex, int, con, wis, cha)
				if (readC() == 1) {
					String s = readS();
					final int BONUS_ABILITY = pc.getAbility().getBonusAbility();

					if (!(pc.getLevel() - 50 > BONUS_ABILITY))
						return;

					if (s.toLowerCase().equals("str".toLowerCase())) {
						if (pc.getAbility().getStr() < 45) {
							pc.getAbility().addStr((byte) 1); // ���� STRġ��+1
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save(); // DB�� ĳ���� ������ �����Ѵ�
						} else {
							pc.sendPackets(new S_SystemMessage(
									"�� �ɷ�ġ�� �ִ밪�� 45 �Դϴ�. �ٸ� �ɷ�ġ�� ������ �ּ���.")); // �ϳ���
																				// �ɷ�ġ��
																				// �ִ�ġ��
																				// 25�Դϴ�.
																				// �ٸ�
																				// �ɷ�ġ��
																				// ������
																				// �ּ���
						}
					} else if (s.toLowerCase().equals("dex".toLowerCase())) {
						if (pc.getAbility().getDex() < 45) {
							pc.getAbility().addDex((byte) 1);
							pc.resetBaseAc();
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER,
									pc.get_PlusEr()), true);
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage(
									"�� �ɷ�ġ�� �ִ밪�� 45 �Դϴ�. �ٸ� �ɷ�ġ�� ������ �ּ���.")); // �ϳ���
																				// �ɷ�ġ��
																				// �ִ�ġ��
																				// 25�Դϴ�.
																				// �ٸ�
																				// �ɷ�ġ��
																				// ������
																				// �ּ���
						}
					} else if (s.toLowerCase().equals("con".toLowerCase())) {
						if (pc.getAbility().getCon() < 45) {
							pc.getAbility().addCon((byte) 1);
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage(
									"�� �ɷ�ġ�� �ִ밪�� 45 �Դϴ�. �ٸ� �ɷ�ġ�� ������ �ּ���.")); // �ϳ���
																				// �ɷ�ġ��
																				// �ִ�ġ��
																				// 25�Դϴ�.
																				// �ٸ�
																				// �ɷ�ġ��
																				// ������
																				// �ּ���
						}
					} else if (s.toLowerCase().equals("int".toLowerCase())) {
						if (pc.getAbility().getInt() < 45) {
							pc.getAbility().addInt((byte) 1);
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_ServerMessage(481));
						}
					} else if (s.toLowerCase().equals("wis".toLowerCase())) {
						if (pc.getAbility().getWis() < 45) {
							pc.getAbility().addWis((byte) 1);
							pc.resetBaseMr();
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage(
									"�� �ɷ�ġ�� �ִ밪�� 45 �Դϴ�. �ٸ� �ɷ�ġ�� ������ �ּ���.")); // �ϳ���
																				// �ɷ�ġ��
																				// �ִ�ġ��
																				// 25�Դϴ�.
																				// �ٸ�
																				// �ɷ�ġ��
																				// ������
																				// �ּ���
						}
					} else if (s.toLowerCase().equals("cha".toLowerCase())) {
						if (pc.getAbility().getCha() < 45) {
							pc.getAbility().addCha((byte) 1);
							pc.getAbility().setBonusAbility(BONUS_ABILITY + 1);
							pc.sendPackets(new S_OwnCharStatus2(pc));
							pc.sendPackets(new S_CharVisualUpdate(pc));
							pc.save();
						} else {
							pc.sendPackets(new S_SystemMessage(
									"�� �ɷ�ġ�� �ִ밪�� 45 �Դϴ�. �ٸ� �ɷ�ġ�� ������ �ּ���.")); // �ϳ���
																				// �ɷ�ġ��
																				// �ִ�ġ��
																				// 25�Դϴ�.
																				// �ٸ�
																				// �ɷ�ġ��
																				// ������
																				// �ּ���
						}
					}

					pc.CheckStatus();
					if (pc.getLevel() >= 51
							&& pc.getLevel() - 50 > pc.getAbility()
									.getBonusAbility()) {
						if ((pc.getAbility().getStr()
								+ pc.getAbility().getDex()
								+ pc.getAbility().getCon()
								+ pc.getAbility().getInt()
								+ pc.getAbility().getWis() + pc.getAbility()
								.getCha()) < 150) {
							int temp = (pc.getLevel() - 50)
									- pc.getAbility().getBonusAbility();
							pc.sendPackets(new S_bonusstats(pc.getId(), temp));
						}
					}
				}
				break;

			default:
				break;
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	// joinPc = ����, pc = ����
	public static void ���Ͱ���(L1PcInstance pc, L1PcInstance joinPc, L1Clan clan) {
		// try{
		if (pc == null)
			return;
		int maxMember = 0;
		/*
		 * int maxMember = 0; int charisma = 0; if(pc == null)return;
		 * if(pc.getClanRank() == L1Clan.CLAN_RANK_PRINCE ){ charisma =
		 * pc.getAbility().getTotalCha(); if (pc.getLevel() >= 50) { // Lv50 �̻�
		 * maxMember = charisma * 9; } else if (pc.getLevel() >= 45) { // Lv45
		 * �̸� maxMember = charisma * 6; }else{ maxMember = charisma * 3; }
		 * 
		 * }else if(pc.getClanRank() >= L1Clan.CLAN_RANK_GUARDIAN){ maxMember =
		 * clan.getCrownMaxMember(); } //pc.sendPackets(new
		 * S_SystemMessage("dd "+maxMember)); if(maxMember > 300){ maxMember =
		 * 300; }
		 */
		maxMember = 300;

		if (joinPc.getClanid() == 0) { // ũ���̰���
			if (leaveNameCK != null) {
				long ck = leaveNameCK.ck(joinPc.getName());
				if (ck > 0) {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"HH�ð� mm��");
					String time = dateFormat.format(new Timestamp((ck - System
							.currentTimeMillis()) + (60 * 1000 * 60 * 15)));
					if (pc != null)
						pc.sendPackets(new S_SystemMessage(joinPc.getName()
								+ " ���� ���� ���� ������ �ɷ��ִ� ���� �Դϴ�."));
					joinPc.sendPackets(new S_SystemMessage(time
							+ " �Ŀ� �簡�� �� �� �ֽ��ϴ�."));
					return;
				}
			}
			if (maxMember <= clan.getClanMemberList().size()) {// clanMembersName.length)
																// { // �� ���� ����
				if (pc != null)
					joinPc.sendPackets(new S_ServerMessage(188, pc.getName())); // %0��
																				// �����
																				// ���Ϳ����μ�
																				// �޾Ƶ���
																				// ����
																				// �����ϴ�.
				joinPc.sendPackets(new S_SystemMessage("[" + clan.getClanName()
						+ "] ������\n���� ������ �� �����ϴ�."), true);
				return;
			}
			for (L1PcInstance clanMembers : clan.getOnlineClanMember()) {
				clanMembers.sendPackets(new S_ServerMessage(94, joinPc
						.getName())); // \f1%0�� ������ �Ͽ����μ� �޾Ƶ鿩�����ϴ�.
			}
			joinPc.setClanid(clan.getClanId());
			joinPc.setClanname(clan.getClanName());
			joinPc.setClanRank(L1Clan.CLAN_RANK_PROBATION);
			joinPc.setTitle("");
			joinPc.sendPackets(new S_CharTitle(joinPc.getId(), ""));
			Broadcaster.broadcastPacket(joinPc, new S_CharTitle(joinPc.getId(),
					""));
			joinPc.setClanJoinDate(new Timestamp(System.currentTimeMillis()));
			try {
				joinPc.save();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			clan.addClanMember(joinPc.getName(), joinPc.getClanRank(),
					joinPc.getLevel(), joinPc.getType(), joinPc.getMemo(), 
					joinPc.getOnlineStatus(), joinPc);

			joinPc.sendPackets(new S_PacketBox(S_PacketBox.MSG_RANK_CHANGED,
					0x07, joinPc.getName()), true);

			joinPc.sendPackets(new S_PacketBox(S_PacketBox.WORLDMAP_UNKNOWN1),
					true);

			joinPc.sendPackets(new S_ClanJoinLeaveStatus(joinPc), true);
			Broadcaster.broadcastPacket(joinPc, new S_ClanJoinLeaveStatus(
					joinPc));

			joinPc.sendPackets(new S_ReturnedStat(joinPc,
					S_ReturnedStat.CLAN_JOIN_LEAVE), true);
			Broadcaster.broadcastPacket(joinPc, new S_ReturnedStat(joinPc,
					S_ReturnedStat.CLAN_JOIN_LEAVE));
			joinPc.sendPackets(new S_ClanWindow(S_ClanWindow.����ũ����, joinPc
					.getClan().getmarkon()), true);
			joinPc.sendPackets(new S_�����ֽ�(joinPc.getClan(), 2), true);

			ClanTable.getInstance().updateClan(joinPc.getClan());
			if (pc != null) {
				pc.sendPackets(new S_PacketBox(pc,
						S_PacketBox.PLEDGE_REFRESH_PLUS));
				joinPc.sendPackets(new S_ServerMessage(95, clan.getClanName())); // \f1%0
																					// ���Ϳ�
																					// �����߽��ϴ�.
			}
		} else { // ũ�� ������ ���� ����(ũ�� ����)
			if (Config.CLAN_ALLIANCE) {
				if (pc != null)
					changeClan(pc.getNetConnection(), pc, joinPc, maxMember);
			} else {
				joinPc.sendPackets(new S_SystemMessage("�̹� ���Ϳ� �����߽��ϴ�.")); // \f1�����
																			// ����
																			// ���Ϳ�
																			// �����ϰ�
																			// �ֽ��ϴ�.
			}
		}
		/*
		 * }catch(Exception e){ e.printStackTrace(); }
		 */
	}

	private void leaveClan(L1PcInstance pc) {
		try {
			String player_name = pc.getName();
			String clan_name = pc.getClanname();
			L1Clan clan = L1World.getInstance().getClan(clan_name);
			L1PcInstance clanMember[] = clan.getOnlineClanMember();

			for (int f = 0; f < clanMember.length; f++) {
				clanMember[f].sendPackets(new S_ServerMessage(
						ServerMessage.LEAVE_CLAN, player_name, clan_name)); // \f1%0��
																			// %1������
																			// Ż���߽��ϴ�.
			}

			pc.ClearPlayerClanData(clan);
			clan.removeOnlineClanMember(player_name);
			clan.removeClanMember(player_name);

			ClanTable.getInstance().updateClan(clan);

			pc.sendPackets(new S_ReturnedStat(pc,
					S_ReturnedStat.CLAN_JOIN_LEAVE), true);
		} catch (Exception e) {
		}
	}

	private static void changeClan(LineageClient clientthread, L1PcInstance pc,
			L1PcInstance joinPc, int maxMember) {
		int clanId = pc.getClanid();
		String clanName = pc.getClanname();
		L1Clan clan = L1World.getInstance().getClan(clanName);
		int clanNum = clan.getClanMemberList().size();

		int oldClanId = joinPc.getClanid();
		String oldClanName = joinPc.getClanname();
		L1Clan oldClan = L1World.getInstance().getClan(oldClanName);
		int oldClanNum = oldClan.getClanMemberList().size();
		if (clan != null && oldClan != null && joinPc.isCrown()
				&& joinPc.getId() == oldClan.getLeaderId()) {
			if (maxMember < clanNum + oldClanNum) { // �� ���� ����
				joinPc.sendPackets(new S_ServerMessage(188, pc.getName())); // %0��
																			// �����
																			// ���Ϳ����μ�
																			// �޾Ƶ���
																			// ����
																			// �����ϴ�.
				return;
			}
			L1PcInstance clanMember[] = clan.getOnlineClanMember();
			for (int cnt = 0; cnt < clanMember.length; cnt++) {
				clanMember[cnt].sendPackets(new S_ServerMessage(94, joinPc
						.getName())); // \f1%0�� ������ �Ͽ����μ� �޾Ƶ鿩�����ϴ�.
			}

			for (int i = 0; i < oldClan.getClanMemberList().size(); i++) {
				L1PcInstance oldClanMember = L1World.getInstance().getPlayer(
						oldClan.getClanMemberList().get(i).name);
				if (oldClanMember != null) { // �¶������� ��ũ�� ���
					oldClanMember.setClanid(clanId);
					oldClanMember.setClanname(clanName);
					// ���� ���տ� ������ ���ִ� �����
					// ���ְ� ���� �� ���Ϳ��� ���޾�
					if (oldClanMember.getId() == joinPc.getId()) {
						// oldClanMember.setClanRank(L1Clan.CLAN_RANK_GUARDIAN);
						oldClanMember.setClanRank(L1Clan.CLAN_RANK_SUBPRINCE);
					} else {
						oldClanMember.setClanRank(L1Clan.CLAN_RANK_PROBATION);
					}
					try {
						// DB�� ĳ���� ������ �����Ѵ�
						oldClanMember.save();
					} catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
					clan.addClanMember(oldClanMember.getName(),
							oldClanMember.getClanRank(),
							oldClanMember.getLevel(), oldClanMember.getType(),
							oldClanMember.getMemo(),
							oldClanMember.getOnlineStatus(), oldClanMember);
					oldClanMember
							.sendPackets(new S_ServerMessage(95, clanName)); // \f1%0
																				// ���Ϳ�
																				// �����߽��ϴ�.
				} else { // ���� �������� ��ũ�� ���
					try {
						L1PcInstance offClanMember = CharacterTable
								.getInstance()
								.restoreCharacter(
										oldClan.getClanMemberList().get(i).name);
						offClanMember.setClanid(clanId);
						offClanMember.setClanname(clanName);
						offClanMember.setClanRank(L1Clan.CLAN_RANK_PROBATION);
						offClanMember.save();
						clan.addClanMember(offClanMember.getName(),
								offClanMember.getClanRank(),
								offClanMember.getLevel(),
								offClanMember.getType(),
								offClanMember.getMemo(),
								offClanMember.getOnlineStatus(), offClanMember);
					} catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				}
			}
			// �������� ����
			String emblem_file = String.valueOf(oldClanId);
			File file = new File("emblem/" + emblem_file);
			file.delete();
			ClanTable.getInstance().deleteClan(oldClanName);
		}
	}

	private static void renamePet(L1PetInstance pet, String name) {
		if (pet == null || name == null) {
			throw new NullPointerException();
		}

		int petItemObjId = pet.getItemObjId();
		L1Pet petTemplate = PetTable.getInstance().getTemplate(petItemObjId);
		if (petTemplate == null) {
			throw new NullPointerException();
		}

		L1PcInstance pc = (L1PcInstance) pet.getMaster();
		if (PetTable.isNameExists(name)) {
			pc.sendPackets(new S_ServerMessage(327)); // ���� �̸��� ���� �����ϰ� �ֽ��ϴ�.
			return;
		}
		L1Npc l1npc = NpcTable.getInstance().getTemplate(pet.getNpcId());
		if (!(pet.getName().equalsIgnoreCase(l1npc.get_name()))) {
			pc.sendPackets(new S_ServerMessage(326));
			return;
		}
		pet.setName(name);
		petTemplate.set_name(name);
		PetTable.getInstance().storePet(petTemplate); // DB�� ������
		L1ItemInstance item = pc.getInventory().getItem(pet.getItemObjId());
		pc.getInventory().updateItem(item);
		pc.sendPackets(new S_ChangeName(pet.getId(), name));
		Broadcaster.broadcastPacket(pc, new S_ChangeName(pet.getId(), name));
	}

	private void callClan(L1PcInstance pc) {
		L1PcInstance callClanPc = (L1PcInstance) L1World.getInstance()
				.findObject(pc.getTempID());

		if (callClanPc == null) {
			return;
		}

		boolean isInWarArea = false;
		short mapId = callClanPc.getMapId();
		int castleId = L1CastleLocation.getCastleIdByArea(callClanPc);

		pc.setTempID(0);

		if (!pc.getMap().isEscapable() && !pc.isGm()) {
			pc.sendPackets(new S_ServerMessage(647));
			L1Teleport.teleport(pc, pc.getLocation(), pc.getMoveState()
					.getHeading(), false);
			return;
		}

		if (pc.getId() != callClanPc.getCallClanId()) {
			return;
		}

		if (castleId != 0) {
			isInWarArea = true;
			if (WarTimeController.getInstance().isNowWar(castleId)) {
				isInWarArea = false;
			}
		}

		if (mapId != 0 && mapId != 4 && mapId != 304 || isInWarArea) {
			pc.sendPackets(new S_ServerMessage(547));
			return;
		}

		if (mapId == 4
				&& ((callClanPc.getX() >= 33331 && callClanPc.getX() <= 33341
						&& callClanPc.getY() >= 32430 && callClanPc.getY() <= 32441)
						|| (callClanPc.getX() >= 33258
								&& callClanPc.getX() <= 33267
								&& callClanPc.getY() >= 32396 && callClanPc
								.getY() <= 32407)
						|| (callClanPc.getX() >= 33388
								&& callClanPc.getX() <= 33397
								&& callClanPc.getY() >= 32339 && callClanPc
								.getY() <= 32350) || (callClanPc.getX() >= 33443
						&& callClanPc.getX() <= 33483
						&& callClanPc.getY() >= 32315 && callClanPc.getY() <= 32357))) {
			pc.sendPackets(new S_ServerMessage(547));
			return;
		}

		L1Map map = callClanPc.getMap();
		int locX = callClanPc.getX();
		int locY = callClanPc.getY();
		int heading = callClanPc.getCallClanHeading();
		locX += HEADING_TABLE_X[heading];
		locY += HEADING_TABLE_Y[heading];
		heading = (heading + 4) % 4;

		boolean isExsistCharacter = false;
		L1Character cha = null;
		for (L1Object object : L1World.getInstance().getVisibleObjects(
				callClanPc, 1)) {
			if (object instanceof L1Character) {
				cha = (L1Character) object;
				if (cha.getX() == locX && cha.getY() == locY
						&& cha.getMapId() == mapId) {
					isExsistCharacter = true;
					break;
				}
			}
		}
		if (mapId == 4
				&& ((locX >= 33331 && locX <= 33341 && locY >= 32430 && locY <= 32441)
						|| (locX >= 33258 && locX <= 33267 && locY >= 32396 && locY <= 32407)
						||

						(locX >= 34197 && locX <= 34302 && locY >= 33327 && locY <= 33533)
						|| // Ȳȥ�ǻ��
						(locX >= 33453 && locX <= 33468 && locY >= 32331 && locY <= 32341)
						|| // �Ƶ����ѱ���

						(locX >= 33388 && locX <= 33397 && locY >= 32339 && locY <= 32350) || (locX >= 33464
						&& locX <= 33531 && locY >= 33168 && locY <= 33248)
				/*
				 * (newX2 >= 33443 && newX2 <= 33483 && newY2 >= 32315 && newY2
				 * <= 32357)
				 */) /* && !pc.isGm() */) {
			pc.sendPackets(new S_ServerMessage(627));
			return;
		}
		if (locX == 0 && locY == 0 || !map.isPassable(locX, locY)
				|| isExsistCharacter) {
			pc.sendPackets(new S_ServerMessage(627));
			return;
		}
		L1Teleport.teleport(pc, locX, locY, mapId, heading, true,
				L1Teleport.CALL_CLAN);
	}

	private void phone(int id) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "UPDATE characters SET PartnerID=0 WHERE objid=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setInt(1, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void miniGameRemoveEnterMember(L1PcInstance pc) {
		if (GhostHouse.getInstance().isEnterMember(pc))
			GhostHouse.getInstance().removeEnterMember(pc);
		else if (PetRacing.getInstance().isEnterMember(pc))
			PetRacing.getInstance().removeEnterMember(pc);
	}

	private static ClanLeave_joinNameCK leaveNameCK = null;

	class ClanLeave_joinNameCK implements Runnable {
		private final FastMap<String, Long> list;

		public ClanLeave_joinNameCK() {
			list = new FastMap<String, Long>();
		}

		@Override
		public void run() {
			while (true) {
				try {
					if (list.size() > 0) {
						FastTable<String> sl = new FastTable<String>();
						for (FastMap.Entry<String, Long> e = list.head(), mapEnd = list
								.tail(); (e = e.getNext()) != mapEnd;) {
							if (e.getValue() < System.currentTimeMillis()) {
								sl.add(e.getKey());
							}
						}
						for (String name : sl) {
							list.remove(name);
						}
						Thread.sleep(1000);
					} else {
						Thread.sleep(5000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		public void add(String name, long time) {
			list.put(name, time);
		}

		public long ck(String name) {
			long d = 0;
			try {
				d = list.get(name);
			} catch (Exception e) {
			}
			return d;
		}
	}

	/*
	 * private boolean checkdragonkey(L1PcInstance pc){
	 * if(pc.getInventory().checkItem(L1ItemId.DRAGON_KEY)){
	 * if(BoardTable.getInstance().checkExistName(pc.getName(), 4212014)){
	 * S_ServerMessage sm = new S_ServerMessage(1568); pc.sendPackets(sm);// �̹�
	 * ��ϵǾ� �־� sm = null; return false; }else{ return true; } }else{
	 * S_ServerMessage sm = new S_ServerMessage(1566); pc.sendPackets(sm);// �巡��
	 * Ű �־�� �� sm = null; return false; } }
	 */
	private boolean checkdragonkey(L1PcInstance pc) {
		int keyid = pc.��Ű���üũid;
		if (keyid == 0)
			return false;
		L1ItemInstance item = pc.getInventory().getItem(keyid);
		if (item != null && item.getItemId() == L1ItemId.DRAGON_KEY) {
			if (BoardTable.getInstance().checkExistkey(keyid, 4212014)) {
				S_ServerMessage sm = new S_ServerMessage(1568);
				pc.sendPackets(sm);// �̹� ��ϵǾ� �־�
				sm = null;
				return false;
			} else {
				return true;
			}
		} else {
			S_ServerMessage sm = new S_ServerMessage(1566);
			pc.sendPackets(sm);// �巡�� Ű �־�� ��
			sm = null;
			return false;
		}
	}

	private static String currentTime() {
		TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		Calendar cal = Calendar.getInstance(tz);
		int year = cal.get(Calendar.YEAR) - 2000;
		String year2;
		if (year < 10) {
			year2 = "0" + year;
		} else {
			year2 = Integer.toString(year);
		}
		int Month = cal.get(Calendar.MONTH) + 1;
		String Month2 = null;
		if (Month < 10) {
			Month2 = "0" + Month;
		} else {
			Month2 = Integer.toString(Month);
		}
		int date = cal.get(Calendar.DATE);
		String date2 = null;
		if (date < 10) {
			date2 = "0" + date;
		} else {
			date2 = Integer.toString(date);
		}
		return year2 + "/" + Month2 + "/" + date2;
	}

	@Override
	public String getType() {
		return C_ATTR;
	}
}