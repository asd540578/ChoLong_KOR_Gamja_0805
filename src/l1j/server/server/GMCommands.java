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

package l1j.server.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastTable;
import l1j.server.Base64;
import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.SpecialEventHandler;
import l1j.server.Database.DB;
import l1j.server.GameSystem.CrockSystem;
import l1j.server.GameSystem.NpcShopSystem;
import l1j.server.GameSystem.Advertisement.BadukiChatController;
import l1j.server.GameSystem.Advertisement.ItemBayChatController;
import l1j.server.GameSystem.Advertisement.NurtureChatController;
import l1j.server.GameSystem.EventShop.돌아온아놀드이벤트;
import l1j.server.GameSystem.Gamble.Gamble;
import l1j.server.GameSystem.NpcBuyShop.NpcBuyShop;
import l1j.server.GameSystem.NpcTradeShop.NpcTradeShop;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.Robot.Robot;
import l1j.server.GameSystem.Robot.Robot_Bugbear;
import l1j.server.GameSystem.Robot.Robot_ConnectAndRestart;
import l1j.server.GameSystem.Robot.Robot_Crown;
import l1j.server.GameSystem.Robot.Robot_Fish;
import l1j.server.GameSystem.Robot.Robot_Hunt;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.IndunSystem.MiniGame.BattleZone;
import l1j.server.Warehouse.ClanWarehouse;
import l1j.server.Warehouse.PrivateWarehouse;
import l1j.server.Warehouse.SupplementaryService;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.TimeController.DevilController;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.TimeController.잊섬Controller;
import l1j.server.server.clientpackets.C_AuthLogin;
import l1j.server.server.clientpackets.C_SelectCharacter;
import l1j.server.server.command.L1Commands;
import l1j.server.server.command.executor.L1CommandExecutor;
import l1j.server.server.datatables.CastleTable;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.DropTable;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.HouseTable;
import l1j.server.server.datatables.IpPhoneCertificationTable;
import l1j.server.server.datatables.IpTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.LogTable;
import l1j.server.server.datatables.ModelSpawnTable;
import l1j.server.server.datatables.NpcShopSpawnTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.datatables.UBTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1BugBearRace;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1EffectSpawn;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1UltimateBattle;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_Ability;
import l1j.server.server.serverpackets.S_Chainfo;
import l1j.server.server.serverpackets.S_CharTitle;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_DRAGONPERL;
import l1j.server.server.serverpackets.S_DeleteInventoryItem;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_Emblem;
import l1j.server.server.serverpackets.S_GMCommands;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_TempInv;
import l1j.server.server.serverpackets.S_문장주시;
import l1j.server.server.storage.mysql.MySqlCharacterStorage;
import l1j.server.server.templates.L1Command;
import l1j.server.server.templates.L1House;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.utils.DeadLockDetector;
import l1j.server.server.utils.L1SpawnUtil;
import l1j.server.server.utils.SQLUtil;
import server.GameServer;
import server.manager.eva;
import server.system.autoshop.AutoShopManager;

public class GMCommands {

	private static Logger _log = Logger.getLogger(GMCommands.class.getName());

	public static boolean sellShopNotice = false;
	public static boolean restartBot = false;
	public static boolean bugbearBot = false;
	public static boolean clanBot = false;
	public static boolean fishBot = false;
	public static boolean huntBot = false;
	// public static boolean 매입상점 = false;
	public static boolean 자동생성방지 = false;
	public static boolean 주시아이피체크 = false;
	public static boolean 무인상점구매체크 = true;
	public static boolean 케릭인증영자방 = false;
	public static boolean 트리플포우스핵 = false;
	public static boolean 용해로그 = false;
	public static boolean 접속이름체크 = false;
	public static boolean 아덴교환체크 = false;
	public static boolean 엔진체크 = true;

	public static boolean 범위로그 = false;
	public static boolean 마법속도체크 = false;
	private static GMCommands _instance;

	private GMCommands() {
	}

	public static GMCommands getInstance() {
		if (_instance == null) {
			_instance = new GMCommands();
		}
		return _instance;
	}

	private String complementClassName(String className) {
		if (className.contains(".")) {
			return className;
		}
		return "l1j.server.server.command.executor." + className;
	}

	private boolean executeDatabaseCommand(L1PcInstance pc, String name, String arg) {
		try {
			L1Command command = L1Commands.get(name);
			if (command == null) {
				return false;
			}
			if (pc.getAccessLevel() < command.getLevel()) {
				pc.sendPackets(new S_ServerMessage(74, "커멘드 " + name), true); // \f1%0은
																				// 사용할
																				// 수
																				// 없습니다.
				return true;
			}

			Class<?> cls = Class.forName(complementClassName(command.getExecutorClassName()));
			L1CommandExecutor exe = (L1CommandExecutor) cls.getMethod("getInstance").invoke(null);
			exe.execute(pc, name, arg);
			return true;
		} catch (Exception e) {
			// _log.log(Level.SEVERE, "error gm command", e);
		}
		return false;
	}

	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c,
			0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e,
			0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0,
			0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2,
			0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4,
			0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6,
			0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
			0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff };

	private static void Delete(String id_name) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("delete FROM character_items WHERE item_id IN (" + id_name + ")");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM character_warehouse WHERE item_id in (" + id_name + ")");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM clan_warehouse WHERE item_id in (" + id_name + ")");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM character_elf_warehouse WHERE item_id in (" + id_name + ")");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static void Delete21269() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("delete FROM character_items WHERE item_id=21269 AND enchantlvl < 6");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM character_warehouse WHERE item_id=21269 AND enchantlvl < 6");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM clan_warehouse WHERE item_id=21269 AND enchantlvl < 6");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM character_elf_warehouse WHERE item_id=21269 AND enchantlvl < 6");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static int 할로윈리스트[] = { 90085, 90086, 90087, 90088, 90089, 90090, 90091, 90092, 160423, 435000, 160510,
			160511, 21123, 21269 };
	private static int 할로윈리스트2[] = { 90085, 90086, 90087, 90088, 90089, 90090, 90091, 90092, 160423, 435000, 160510,
			160511, 21123 };

	public synchronized static void EventItemDelete() {
		try {
			for (L1PcInstance tempPc : L1World.getInstance().getAllPlayers()) {
				if (tempPc == null)
					continue;
				for (int i = 0; i < 할로윈리스트.length; i++) {
					L1ItemInstance[] item = tempPc.getInventory().findItemsId(할로윈리스트[i]);

					if (item != null && item.length > 0) {
						for (int o = 0; o < item.length; o++) {
							if (item[o].getItemId() == 21269) {
								if (item[o].getEnchantLevel() >= 6) {
									if (item[o].getBless() >= 128)
										item[o].setBless(128);
									else
										item[o].setBless(0);
									tempPc.getInventory().updateItem(item[o], L1PcInventory.COL_BLESS);
									tempPc.getInventory().saveItem(item[o], L1PcInventory.COL_BLESS);
									continue;
								}
							}
							if (item[o].isEquipped()) {
								tempPc.getInventory().setEquipped(item[o], false); // 추가해주세요.
							}
							tempPc.getInventory().removeItem(item[o]);
						}
					}
					try {
						PrivateWarehouse pw = WarehouseManager.getInstance()
								.getPrivateWarehouse(tempPc.getAccountName());
						L1ItemInstance[] item2 = pw.findItemsId(할로윈리스트[i]);
						if (item2 != null && item2.length > 0) {
							for (int o = 0; o < item2.length; o++) {
								if (item[o].getItemId() == 21269) {
									if (item[o].getEnchantLevel() >= 6) {
										if (item[o].getBless() >= 128)
											item[o].setBless(128);
										else
											item[o].setBless(0);
										tempPc.getInventory().updateItem(item[o], L1PcInventory.COL_BLESS);
										tempPc.getInventory().saveItem(item[o], L1PcInventory.COL_BLESS);
										continue;
									}
								}
								pw.removeItem(item2[o]);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (tempPc.getClanid() > 0) {
							ClanWarehouse cw = WarehouseManager.getInstance().getClanWarehouse(tempPc.getClanname());
							L1ItemInstance[] item3 = cw.findItemsId(할로윈리스트[i]);
							if (item3 != null && item3.length > 0) {
								for (int o = 0; o < item3.length; o++) {
									if (item3[o].getItemId() == 21269) {
										if (item3[o].getEnchantLevel() >= 6) {
											if (item3[o].getBless() >= 128)
												item3[o].setBless(128);
											else
												item3[o].setBless(0);
											tempPc.getInventory().updateItem(item3[o], L1PcInventory.COL_BLESS);
											tempPc.getInventory().saveItem(item3[o], L1PcInventory.COL_BLESS);
											continue;
										}
									}
									cw.removeItem(item3[o]);
								}
							}
						}
					} catch (Exception e) {
					}
					try {
						Collection<L1NpcInstance> pList = tempPc.getPetList();
						if (pList.size() > 0) {
							for (L1NpcInstance npc : pList) {
								L1ItemInstance[] pitem = npc.getInventory().findItemsId(할로윈리스트[i]);
								if (pitem != null && pitem.length > 0) {
									for (int o = 0; o < pitem.length; o++) {
										if (pitem[o].getItemId() == 21269) {
											if (pitem[o].getEnchantLevel() >= 6) {
												if (pitem[o].getBless() >= 128)
													pitem[o].setBless(128);
												else
													pitem[o].setBless(0);
												tempPc.getInventory().updateItem(pitem[o], L1PcInventory.COL_BLESS);
												tempPc.getInventory().saveItem(pitem[o], L1PcInventory.COL_BLESS);
												continue;
											}
										}
										npc.getInventory().removeItem(pitem[o]);
									}
								}
							}
						}
					} catch (Exception e) {
					}
				}
			}
			try {
				for (L1Object obj : L1World.getInstance().getAllItem()) {
					if (!(obj instanceof L1ItemInstance))
						continue;
					L1ItemInstance temp_item = (L1ItemInstance) obj;
					if (temp_item.getItemOwner() == null || !(temp_item.getItemOwner() instanceof L1RobotInstance)) {
						if (temp_item.getX() == 0 && temp_item.getY() == 0)
							continue;
					}
					for (int ii = 0; ii < 할로윈리스트.length; ii++) {
						if (할로윈리스트[ii] == temp_item.getItemId()) {
							if (temp_item.getItemId() == 21269) {
								if (temp_item.getEnchantLevel() >= 6) {
									if (temp_item.getBless() >= 128)
										temp_item.setBless(128);
									else
										temp_item.setBless(0);
									continue;
								}
							}
							L1Inventory groundInventory = L1World.getInstance().getInventory(temp_item.getX(),
									temp_item.getY(), temp_item.getMapId());
							groundInventory.removeItem(temp_item);
							break;
						}
					}

				}
			} catch (Exception e) {
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 할로윈리스트2.length; i++) {
				sb.append(+할로윈리스트2[i]);
				if (i < 할로윈리스트2.length - 1) {
					sb.append(",");
				}
			}
			Delete(sb.toString());
			Delete21269();
			BlessUpdate(21269);
		} catch (Exception e) {
		}
	}

	public static void BlessUpdate(int itemid) {
		Connection con = null;
		Connection con2 = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT id, bless, enchantlvl FROM character_items WHERE item_id=?");
			// pstm =
			// con.prepareStatement("SELECT * FROM character_items WHERE
			// item_id=?");
			pstm.setInt(1, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int bless = rs.getInt("bless");
				int ent = rs.getInt("enchantlvl");
				if (ent < 6)
					continue;
				try {
					con2 = L1DatabaseFactory.getInstance().getConnection();
					pstm2 = con2.prepareStatement("UPDATE character_items SET bless =? WHERE id=?");
					pstm2.setInt(1, bless > 128 ? 128 : 0);
					pstm2.setInt(2, id);
					pstm2.executeUpdate();
				} finally {
					SQLUtil.close(pstm2);
					SQLUtil.close(con2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void handleCommands(L1PcInstance gm, String cmdLine) {
		StringTokenizer token = new StringTokenizer(cmdLine);
		// 최초의 공백까지가 커맨드, 그 이후는 공백을 단락으로 한 파라미터로서 취급한다
		if (!token.hasMoreTokens()) {
			return;
		}
		String cmd = token.nextToken();
		String param = "";
		while (token.hasMoreTokens()) {
			param = new StringBuilder(param).append(token.nextToken()).append(' ').toString();
		}
		param = param.trim();

		// 데이타베이스화 된 커멘드
		if (executeDatabaseCommand(gm, cmd, param)) {
			if (!cmd.equalsIgnoreCase("재실행")) {
				_lastCommands.put(gm.getId(), cmdLine);
			}
			return;
		}

		if (gm.getAccessLevel() < 200) {
			gm.sendPackets(new S_ServerMessage(74, "커맨드 " + cmd), true);
			return;
		}

		if (gm.isSGm()
				/*
				 * && !cmd.equalsIgnoreCase("폰인증") &&
				 * !cmd.equalsIgnoreCase("폰인증타겟") &&
				 * !cmd.equalsIgnoreCase("폰인증완료") &&
				 * !cmd.equalsIgnoreCase("폰인증초기화")
				 */

				&& !cmd.equalsIgnoreCase("압류해제") && !cmd.equalsIgnoreCase("감옥") && !cmd.equalsIgnoreCase("마을")
				&& !cmd.equalsIgnoreCase("맵누구")) {
			gm.sendPackets(new S_ServerMessage(74, "커맨드 " + cmd), true);
			return;
		}

		// GM에 개방하는 커맨드는 여기에 쓴다
		if (cmd.equalsIgnoreCase("도움말")) {
			showHelp(gm);
		} else if (cmd.equalsIgnoreCase("랭킹갱신")) {
			UserRankingController.isRenewal = true;
			gm.sendPackets(new S_SystemMessage("랭킹이 갱신 되었습니다."));
		} else if (cmd.equalsIgnoreCase("킬멘트")) {
			killment(gm, param); // by 사부 킬 멘트
		} else if (cmd.equalsIgnoreCase("자동압류")) {
			자동압류(gm, param);

		} else if (cmd.equalsIgnoreCase("판매상점")) {
			sellshop(gm);
		} else if (cmd.equalsIgnoreCase("악영오픈")) {
			DevilController.getInstance().isGmOpen = true;
			gm.sendPackets(new S_SystemMessage("악마왕의 영토를 오픈합니다."));
		} else if (cmd.equalsIgnoreCase("악영종료")) {
			DevilController.getInstance().isGmOpen = false;
			gm.sendPackets(new S_SystemMessage("악마왕의 영토를 닫습니다."));

		} else if (cmd.equalsIgnoreCase("잊섬오픈")) {
			잊섬Controller.getInstance().isGmOpen = true;
			gm.sendPackets(new S_SystemMessage("잊혀진 섬을 오픈합니다."));
		} else if (cmd.equalsIgnoreCase("잊섬종료")) {
			잊섬Controller.getInstance().isGmOpen = false;
			gm.sendPackets(new S_SystemMessage("잊혀진 섬을 닫습니다."));
		} else if (cmd.equalsIgnoreCase("매입상점")) {
			buyshop(gm);

		} else if (cmd.equalsIgnoreCase("유저매입상점")) {
			buyshop_user(gm);
		} else if (cmd.equalsIgnoreCase("전체정리")) {
			전체정리(gm);
		} else if (cmd.equalsIgnoreCase("인형청소")) {
			인형청소(gm);
		} else if (cmd.equalsIgnoreCase("메모리반환")) {
			메모리반환(gm);
		} else if (cmd.equalsIgnoreCase("버그")) {
			버그(gm, param);
		} else if (cmd.equalsIgnoreCase("멘트")) {
			멘트(gm, param);

		} else if (cmd.equalsIgnoreCase("메세지")) {
			메세지(gm, param);

		} else if (cmd.equalsIgnoreCase("버그2")) {
			버그2(gm, param);

		} else if (cmd.equalsIgnoreCase("던전초기화")) {
			던전초기화(gm, param);

		} else if (cmd.equalsIgnoreCase("서버종료")) {
			서버종료(gm, param);
		} else if (cmd.equalsIgnoreCase("html")) {
			html(gm, param);

		} else if (cmd.equalsIgnoreCase("기감초기화")) {
			기감(gm);
		} else if (cmd.equalsIgnoreCase("로그")) {
			standBy8(gm, param);
		} else if (cmd.equalsIgnoreCase("액숀")) {
			시바(gm, param);

		} else if (cmd.equalsIgnoreCase("아놀드상점")) {
			아놀드상점(gm, param);

			// }else if (cmd.equalsIgnoreCase("엔샵샵")) {
			// 엔샵(gm);
		} else if (cmd.equalsIgnoreCase("채팅로그")) {
			채팅(gm, param);
		} else if (cmd.equalsIgnoreCase("옵")) {
			옵코드(gm, param);

		} else if (cmd.equalsIgnoreCase("버경")) {
			버경(gm, param);
		} else if (cmd.equalsIgnoreCase("마법속도체크")) {
			마법속도(gm);
		} else if (cmd.equalsIgnoreCase("입장시간")) {
			checktime(gm);
		} else if (cmd.equalsIgnoreCase("무대테스트")) {
			텟트(gm);
		} else if (cmd.equalsIgnoreCase("패킷박스")) {
			packetbox(gm, param);
		} else if (cmd.equalsIgnoreCase("계정추가")) {
			addaccount(gm, param);
			// } else if (cmd.equalsIgnoreCase("버경")){
			// SpecialEventHandler.getInstance().doBugRace();
		} else if (cmd.equalsIgnoreCase("화면버프")) {
			SpecialEventHandler.getInstance().doScreenBuf(gm);
		} else if (cmd.equalsIgnoreCase("화면버프2")) {
			SpecialEventHandler.getInstance().doscreenbuftest(gm);
		} else if (cmd.equalsIgnoreCase("전체버프")) {
			SpecialEventHandler.getInstance().doAllBuf();
		} else if (cmd.equalsIgnoreCase("코마버프")) {
			SpecialEventHandler.getInstance().doAllComaBuf();

		} else if (cmd.equalsIgnoreCase("코마")) {
			SpecialEventHandler.getInstance().doAllComa();

		} else if (cmd.equalsIgnoreCase("화면코마")) {
			SpecialEventHandler.getInstance().doScreenComaBuf(gm);

		} else if (cmd.equalsIgnoreCase("무인상점")) {
			autoshop(gm, param);
		} else if (cmd.equalsIgnoreCase("비번변경")) {
			changepassword(gm, param);
		} else if (cmd.equalsIgnoreCase("아")) {
			CodeTest(gm, param);
		} else if (cmd.equalsIgnoreCase("정리")) {
			Clear(gm);
		} else if (cmd.equalsIgnoreCase("불")) {
			spawnmodel(gm, param);
		} else if (cmd.equalsIgnoreCase("서버저장")) {
			serversave(gm);// 요고 추가
		} else if (cmd.equalsIgnoreCase("가라")) {
			nocall(gm, param);
		} else if (cmd.equalsIgnoreCase("감옥")) {
			hellcall(gm, param);
		} else if (cmd.startsWith("압류해제")) {
			accountdel(gm, param);
		} else if (cmd.startsWith("압류")) {
			kick(gm, param);
		} else if (cmd.startsWith("벤")) {
			ban(gm, param);
		} else if (cmd.startsWith("버그")) {
			standBy7(gm, param);
		} else if (cmd.startsWith("케릭삭제")) {
			standBy77(gm, param);
		} else if (cmd.startsWith("이벤상점")) {
			standBy79(gm, param);
		} else if (cmd.startsWith("환수")) {
			환수(gm, param);
		} else if (cmd.startsWith("컨피그로드")) {
			standBy80(gm);
		} else if (cmd.startsWith("분신")) {
			standBy81(gm, param);
		} else if (cmd.startsWith("화면분신")) {
			standBy82(gm, param);
		} else if (cmd.startsWith("샌드백정리")) {
			standBy82(gm);

		} else if (cmd.equalsIgnoreCase("채금풀기")) {
			chatx(gm, param);
		} else if (cmd.equalsIgnoreCase("전체선물")) {
			allpresent(gm, param);
		} else if (cmd.equalsIgnoreCase("배율설정")) { // / 추가
			SetRates(gm, param);
		} else if (cmd.equalsIgnoreCase("인챈설정")) {
			SetEnchantRates(gm, param);
		} else if (cmd.equalsIgnoreCase("배율조회")) {
			CheckAllRates(gm);
		} else if (cmd.equalsIgnoreCase("조사")) { // #### 케릭검사
			chainfo(gm, param);
		} else if (cmd.equalsIgnoreCase("인벤")) { // #### 케릭검사
			인벤(gm, param);
		} else if (cmd.equalsIgnoreCase("판매체크")) {
			판매체크(gm);
		} else if (cmd.equals("공성시작")) {
			castleWarStart(gm, param);
		} else if (cmd.equals("공성종료")) {
			castleWarExit(gm, param);
		} else if (cmd.startsWith("겸치복구")) {
			returnEXP(gm, param);
		} else if (cmd.equalsIgnoreCase("영자채팅")) { // by판도라 영자채팅
			if (Config.isGmchat) {
				Config.isGmchat = false;
				gm.sendPackets(new S_SystemMessage("영자채팅 OFF"), true);
			} else {
				Config.isGmchat = true;
				gm.sendPackets(new S_SystemMessage("영자채팅 ON"), true);
			}
		} else if (cmd.equalsIgnoreCase("혈전시작")) {
			StartWar(gm, param);
		} else if (cmd.equalsIgnoreCase("혈전종료")) {
			StopWar(gm, param);
		} else if (cmd.equalsIgnoreCase("검색")) { // ########## 검색 추가 ##########
			searchDatabase(gm, param);
		} else if (cmd.equalsIgnoreCase("겜블")) {
			gamble(gm);
		} else if (cmd.equalsIgnoreCase("데드락")) {
			GeneralThreadPool.getInstance().execute(new DeadLockDetector(gm));
		} else if (cmd.equalsIgnoreCase("디풀")) {
			gm.sendPackets(new S_SystemMessage("DBCP Active Connection Count >> " + DB.active_count_check), true);
		} else if (cmd.equalsIgnoreCase("균열좌표")) {
			if (CrockSystem.getInstance().isOpen()) {
				// L1EvaSystem eva = EvaSystemTable.getInstance().getSystem(1);
				int[] loc = CrockSystem.getInstance().loc();
				gm.sendPackets(new S_SystemMessage("균열 좌표 > x:" + loc[0] + " y:" + loc[1]), true);
			}
		} else if (cmd.equalsIgnoreCase("맵핵")) {
			maphack(gm, param);
		} else if (cmd.equalsIgnoreCase("마을")) {
			UserGiranTel(gm, param);
		} else if (cmd.equalsIgnoreCase("맵버프")) {
			mapBuff(gm);
			// } else if (cmd.equalsIgnoreCase("신규지원")){
			// 신규지원(gm, param);
		} else if (cmd.equalsIgnoreCase("포트변경")) {
			포트변경(gm, param);
		} else if (cmd.equalsIgnoreCase("재실행")) {
			if (!_lastCommands.containsKey(gm.getId())) {
				gm.sendPackets(new S_ServerMessage(74, "커맨드 " + cmd), true); // \f1%0은
																				// 사용할
																				// 수
																				// 없습니다.
				return;
			}
			redo(gm, param);
			return;
		} else if (cmd.equalsIgnoreCase("화면스폰")) {
			화면스폰(gm, param);
		} else if (cmd.equalsIgnoreCase("액션속도")) {
			SprSpeed(gm, param);
		} else if (cmd.equalsIgnoreCase("혈맹마크")) {
			clanMark(gm, param);
		} else if (cmd.equalsIgnoreCase("로그기록")) {
			logSwitch(gm, param);
		} else if (cmd.equalsIgnoreCase("자동생성")) {
			autobot(gm, param);
		} else if (cmd.equalsIgnoreCase("배틀존")) {
			if (BattleZone.getInstance().getDuelStart()) {
				gm.sendPackets(new S_SystemMessage("배틀존이 실행 중 입니다."));
			} else {
				BattleZone.getInstance().setGmStart(true);
				gm.sendPackets(new S_SystemMessage("배틀존이 실행 되었습니다."));
			}
		} else if (cmd.equalsIgnoreCase("하루상점리셋")) {
			L1MerchantInstance.resetOneDayBuy();
			gm.sendPackets(new S_SystemMessage("하루상점 리셋"), true);
		} else if (cmd.equalsIgnoreCase("길팅체크")) {
			길팅체크 = !길팅체크;
			gm.sendPackets(new S_SystemMessage("길팅체크 : " + 길팅체크), true);
		} else if (cmd.equalsIgnoreCase("루팅체크")) {
			루팅체크 = !루팅체크;
			gm.sendPackets(new S_SystemMessage("루팅체크 : " + 루팅체크), true);
		} else if (cmd.equalsIgnoreCase("맵누구")) {
			mapwho(gm, param);
		} else if (cmd.equalsIgnoreCase("케릭명변경") || cmd.equalsIgnoreCase("캐릭명변경")) {
			charname(gm, param);

		} else if (cmd.equalsIgnoreCase("아이")) {
			iconserch2(gm, param);
		} else if (cmd.equalsIgnoreCase("자동아이피")) {
			autoIpcheck(gm, param);
		} else if (cmd.equalsIgnoreCase("봇")) {
			봇(gm, param);
		} else if (cmd.equalsIgnoreCase("엔진체크")) {
			엔진체크 = !엔진체크;
			gm.sendPackets(new S_SystemMessage("엔진체크 : " + 엔진체크), true);
		} else if (cmd.equalsIgnoreCase("무인상점구매체크")) {
			무인상점구매체크 = !무인상점구매체크;
			gm.sendPackets(new S_SystemMessage("무인상점구매체크 : " + 무인상점구매체크), true);
		} else if (cmd.equalsIgnoreCase("아이피체크")) {
			주시아이피체크 = !주시아이피체크;
			gm.sendPackets(new S_SystemMessage("아이피체크 : " + 주시아이피체크), true);
		} else if (cmd.equalsIgnoreCase("영자인증")) {
			케릭인증영자방 = !케릭인증영자방;
			gm.sendPackets(new S_SystemMessage("영자인증 : " + 케릭인증영자방), true);
		} else if (cmd.equalsIgnoreCase("트리플스핵")) {
			트리플포우스핵 = !트리플포우스핵;
			gm.sendPackets(new S_SystemMessage("트리플스핵 : " + 트리플포우스핵), true);
		} else if (cmd.equalsIgnoreCase("자동생성방지")) {
			자동생성방지 = !자동생성방지;
			gm.sendPackets(new S_SystemMessage("자동생성방지 : " + 자동생성방지), true);
		} else if (cmd.equalsIgnoreCase("용해로그")) {
			용해로그 = !용해로그;
			gm.sendPackets(new S_SystemMessage("용해로그 : " + 용해로그), true);
		} else if (cmd.equalsIgnoreCase("인증")) {
			ipphone_certification(gm, param);
		} else if (cmd.equalsIgnoreCase("대화창")) {
			try {
				StringTokenizer st = new StringTokenizer(param);
				String s = st.nextToken();
				gm.sendPackets(new S_NPCTalkReturn(gm.getId(), s), true);
			} catch (Exception e) {
			}
		} else if (cmd.equalsIgnoreCase("접속이름체크")) {
			접속이름체크 = !접속이름체크;
			gm.sendPackets(new S_SystemMessage("접속이름체크 : " + 접속이름체크), true);
		} else if (cmd.equalsIgnoreCase("접속이름체크리셋")) {
			connectCharNameReset();
		} else if (cmd.equalsIgnoreCase("아덴교환체크")) {
			아덴교환체크 = !아덴교환체크;
			gm.sendPackets(new S_SystemMessage("아덴교환체크 : " + 아덴교환체크), true);
		} else if (cmd.equalsIgnoreCase("인벤삭제")) {
			try {
				List<L1ItemInstance> list = gm.getInventory().getItems();
				L1ItemInstance[] ll = list.toArray(new L1ItemInstance[list.size()]);
				for (L1ItemInstance item : ll) {
					gm.getInventory().removeItem(item);
				}
			} catch (Exception e) {
			}
		} else if (cmd.equalsIgnoreCase("혈문장")) {
			혈문장(gm, param);

		} else if (cmd.equals("아지트지급")) {
			GiveHouse(gm, param);
		} else if (cmd.equalsIgnoreCase("드래곤상점")) {
			if (NpcShopSystem._dragon_power) {
				gm.sendPackets(new S_SystemMessage("이미 실행중입니다."), true);
				return;
			}
			NpcShopSystem.getInstance().npcDragonShopStart();
			// } else if (cmd.equalsIgnoreCase("이체크")){
			// 이미지체크(gm);
		} else if (cmd.equalsIgnoreCase("베이")) {
			베이(gm, param);
		} else if (cmd.equalsIgnoreCase("육성")) {
			육성(gm, param);
		} else if (cmd.equalsIgnoreCase("바둑이")) {
			바둑이(gm, param);
		} else if (cmd.equalsIgnoreCase("탐주기")) {
			탐주기(gm, param);
		} else if (cmd.equalsIgnoreCase("사냥아덴로그")) {
			사냥아덴(gm, param);
		} else if (cmd.equalsIgnoreCase("상점아덴로그")) {
			상점아덴(gm, param);
		} else if (cmd.equalsIgnoreCase("화면로그")) {
			if (fifi == null) {
				fifi = GeneralThreadPool.getInstance().schedule(new sdf(gm), 1000);
				gm.sendPackets(new S_SystemMessage("----- 화면 로그 시작 -----"), true);
			}
		} else if (cmd.equalsIgnoreCase("화면로그종료")) {
			if (fifi != null) {
				fifi.cancel(true);
				fifi = null;
				for (L1PcInstance pc : L1World.getInstance().getAllPlayersToArray()) {
					if (pc.getNetConnection() == null)
						continue;
					if (pc.getNetConnection().패킷로그 || GameServer.getInstance().checkip(pc.getNetConnection().getIp())) {
						pc.getNetConnection().행동로그저장(true);
						pc.getNetConnection().패킷로그 = false;
						GameServer.getInstance().removeip(pc.getNetConnection().getIp());
					}
				}
				gm.sendPackets(new S_SystemMessage("----- 화면 로그 종료 -----"), true);
			}
		} else if (cmd.equalsIgnoreCase("광역압류")) {
			광역압류(gm, param);
		} else if (cmd.equalsIgnoreCase("드")) {
			드랍추가(gm, param);
		} else if (cmd.equalsIgnoreCase("아놀드")) {
			돌아온아놀드이벤트(gm, param);
		} else if (cmd.equalsIgnoreCase("노")) {
			노트(gm, param);
		} else {
			gm.sendPackets(new S_SystemMessage("커멘드 " + cmd + " 는 존재하지 않습니다. "), true);
		}
		eva.LogCommandAppend("[운영자명령]" + gm.getName(), cmd, param);
		token = null;
	}

	private void 노트(L1PcInstance gm, String param) {
		StringTokenizer st = new StringTokenizer(param);
		String _노트 = st.nextToken();
		gm._note = _노트;
		gm.sendPackets(new S_SystemMessage("노트값등록 :" + _노트));
	}

	public void killment(L1PcInstance pc, String param) { // by사부 멘트
		if (param.equalsIgnoreCase("끔")) {
			pc.킬멘트 = false;
			pc.sendPackets(new S_SystemMessage("킬멘트 를 표시하지 않습니다."));
		} else if (param.equalsIgnoreCase("켬")) {
			pc.킬멘트 = true;
			pc.sendPackets(new S_SystemMessage("킬멘트 를 표시 합니다."));

		} else {
			pc.sendPackets(new S_SystemMessage(".킬멘트 [켬/끔] 으로 입력해 주세요. "));
		}
	}

	private void charname(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String chaName = tok.nextToken();
			if (pc.getClanid() > 0) {
				pc.sendPackets(new S_SystemMessage("\\fU혈맹탈퇴후 캐릭명을 변경할수 있습니다."));
				return;
			}
			if (!pc.getInventory().checkItem(467009, 1)) { // 있나 체크
				pc.sendPackets(new S_SystemMessage("\\fU케릭명 변경 비법서를 소지하셔야 가능합니다."));
				return;
			}
			for (int i = 0; i < chaName.length(); i++) {
				if (chaName.charAt(i) == 'ㄱ' || chaName.charAt(i) == 'ㄲ' || chaName.charAt(i) == 'ㄴ'
						|| chaName.charAt(i) == 'ㄷ' || // 한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㄸ' || chaName.charAt(i) == 'ㄹ' || chaName.charAt(i) == 'ㅁ'
						|| chaName.charAt(i) == 'ㅂ' || // 한문자(char)단위로 비교
						chaName.charAt(i) == 'ㅃ' || chaName.charAt(i) == 'ㅅ' || chaName.charAt(i) == 'ㅆ'
						|| chaName.charAt(i) == 'ㅇ' || // 한문자(char)단위로 비교
						chaName.charAt(i) == 'ㅈ' || chaName.charAt(i) == 'ㅉ' || chaName.charAt(i) == 'ㅊ'
						|| chaName.charAt(i) == 'ㅋ' || // 한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅌ' || chaName.charAt(i) == 'ㅍ' || chaName.charAt(i) == 'ㅎ'
						|| chaName.charAt(i) == 'ㅛ' || // 한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅕ' || chaName.charAt(i) == 'ㅑ' || chaName.charAt(i) == 'ㅐ'
						|| chaName.charAt(i) == 'ㅔ' || // 한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅗ' || chaName.charAt(i) == 'ㅓ' || chaName.charAt(i) == 'ㅏ'
						|| chaName.charAt(i) == 'ㅣ' || // 한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅠ' || chaName.charAt(i) == 'ㅜ' || chaName.charAt(i) == 'ㅡ'
						|| chaName.charAt(i) == 'ㅒ' || // 한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅖ' || chaName.charAt(i) == 'ㅢ' || chaName.charAt(i) == 'ㅟ'
						|| chaName.charAt(i) == 'ㅝ' || // 한문자(char)단위로 비교.
						chaName.charAt(i) == 'ㅞ' || chaName.charAt(i) == 'ㅙ' || chaName.charAt(i) == 'ㅚ'
						|| chaName.charAt(i) == 'ㅘ' || // 한문자(char)단위로 비교.
						chaName.charAt(i) == '씹' || chaName.charAt(i) == '좃' || chaName.charAt(i) == '좆'
						|| chaName.charAt(i) == 'ㅤ') {
					pc.sendPackets(new S_SystemMessage("사용할수없는 케릭명입니다."));
					return;
				}
			}
			if (chaName.getBytes().length > 12) {
				pc.sendPackets(new S_SystemMessage("이름이 너무 깁니다."));
				return;
			}
			if (chaName.length() == 0) {
				pc.sendPackets(new S_SystemMessage("변경할 케릭명을 입력하세요."));
				return;
			}
			if (BadNamesList.getInstance().isBadName(chaName)) {
				pc.sendPackets(new S_SystemMessage("사용할 수 없는 케릭명입니다."));
				return;
			}
			if (isInvalidName(chaName)) {
				pc.sendPackets(new S_SystemMessage("사용할 수 없는 케릭명입니다."));
				return;
			}
			if (CharacterTable.doesCharNameExist(chaName)) {
				pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
				return;
			}

			if (CharacterTable.RobotNameExist(chaName)) {
				pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
				return;
			}
			if (CharacterTable.RobotCrownNameExist(chaName)) {
				pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
				return;
			}
			if (NpcShopSpawnTable.getInstance().getNpc(chaName) || npcshopNameCk(chaName)) {
				pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
				return;
			}
			if (CharacterTable.somakname(chaName)) {
				pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
				return;
			}
			String oldname = pc.getName();

			chaname(chaName, oldname);

			long sysTime = System.currentTimeMillis();
			logchangename(chaName, oldname, new Timestamp(sysTime));

			pc.sendPackets(new S_SystemMessage(chaName + " 아이디로 변경 하셨습니다."));
			pc.sendPackets(new S_SystemMessage("\\fU계정 입력창으로 이동후 다시 접속하시면 적용됩니다."));
			pc.getInventory().consumeItem(467009, 1); // 소모
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".케릭명변경 [바꾸실아이디] []는 제외하고 입력해주세요."));
		}
	}

	private void logchangename(String chaName, String oldname, Timestamp datetime) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "INSERT INTO Log_Change_name SET Old_Name=?,New_Name=?, Time=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, oldname);
			pstm.setString(2, chaName);
			pstm.setTimestamp(3, datetime);
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private boolean npcshopNameCk(String name) {
		return NpcTable.getInstance().findNpcShopName(name);
	}

	/** 변경 가능한지 검사한다 시작 **/
	private static boolean isAlphaNumeric(String s) {
		boolean flag = true;
		char ac[] = s.toCharArray();
		int i = 0;
		do {
			if (i >= ac.length) {
				break;
			}
			if (!Character.isLetterOrDigit(ac[i])) {
				flag = false;
				break;
			}
			i++;
		} while (true);
		return flag;
	}

	private static boolean isInvalidName(String name) {
		int numOfNameBytes = 0;
		try {
			numOfNameBytes = name.getBytes("EUC-KR").length;
		} catch (UnsupportedEncodingException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return false;
		}

		if (isAlphaNumeric(name)) {
			return false;
		}
		if (5 < (numOfNameBytes - name.length()) || 12 < numOfNameBytes) {
			return false;
		}

		if (BadNamesList.getInstance().isBadName(name)) {
			return false;
		}
		return true;
	}

	private void chaname(String chaName, String oldname) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET char_name=? WHERE char_name=?");
			pstm.setString(1, chaName);
			pstm.setString(2, oldname);
			pstm.executeUpdate();
		} catch (Exception e) {

		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void 드랍추가(L1PcInstance gm, String param) {
		StringTokenizer st = new StringTokenizer(param);
		int itemid = Integer.parseInt(st.nextToken(), 10);
		// int max = Integer.parseInt(st.nextToken(), 10);
		L1Item temp = ItemTable.getInstance().getTemplate(itemid);
		if (temp == null) {
			gm.sendPackets(new S_SystemMessage("아이템을 찾을수 없습니다."));
			return;
		}
		L1Object npc = NpcTable.getInstance().getTemplate(gm._npcnum);
		if (npc == null) {
			gm.sendPackets(new S_SystemMessage("엔피씨를 찾을수 없습니다."));
			return;
		}
		@SuppressWarnings("static-access")
		boolean ck = DropTable.getInstance().SabuDrop(gm._npcnum, itemid, 1, 1000000, gm._npcname, temp.getName(),
				gm._note);
		if (ck) {
			gm.sendPackets(
					new S_SystemMessage("드랍추가 : npc-" + gm._npcnum + " / item(" + itemid + ") / max " + 1 + "개"));
		} else {
			gm.sendPackets(new S_SystemMessage("드랍추가 : 이미 등록된 드랍 리스트"));
		}
	}

	private void 광역압류(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String 이름 = st.nextToken();
			L1PcInstance pc = L1World.getInstance().getPlayer(이름);
			if (pc == null) {
				gm.sendPackets(new S_SystemMessage(이름 + " 이란 케릭터는 미 접속 중입니다."), true);
				return;
			}
			if (pc.getNetConnection() == null || pc.getNetConnection().getIp() == null) {
				gm.sendPackets(new S_SystemMessage(이름 + " 이란 케릭터는 무인상점이거나 로봇 케릭터입니다."), true);
				return;
			}
			String ip = pc.getNetConnection().getIp();
			ip = ip.substring(0, ip.lastIndexOf(".") + 1);

			for (L1PcInstance tempPc : L1World.getInstance().getAllPlayers()) {
				if (tempPc == null || tempPc.getNetConnection() == null || tempPc.getNetConnection().getIp() == null)
					continue;
				if (tempPc.getNetConnection().getIp().indexOf(ip) >= 0) {
					tempPc.getNetConnection().kick();
					tempPc.getNetConnection().close();
				}
			}

			Connection con = null;
			PreparedStatement pstm = null;
			try {
				con = L1DatabaseFactory.getInstance().getConnection();
				for (int i = 1; i <= 255; i++) {
					IpTable.getInstance().banIp(ip + i); // BAN 리스트에 IP를 추가한다.
					String sqlstr = "UPDATE accounts SET banned=1 WHERE ip=?";
					pstm = con.prepareStatement(sqlstr);
					pstm.setString(1, ip + i);
					pstm.executeUpdate();
					SQLUtil.close(pstm);
				}
			} catch (SQLException e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} finally {
				SQLUtil.close(pstm);
				SQLUtil.close(con);
			}
			gm.sendPackets(new S_SystemMessage(이름 + " 케릭터 관련 [" + ip + " 대역] IP 차단과 해당 계정을 압류시킵니다."), true);

		} catch (Exception e) {
		}
	}

	private void 사냥아덴(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String 이름 = st.nextToken();
			if (이름.equalsIgnoreCase("켬")) {
				if (LogTable.사냥아덴) {
					gm.sendPackets(new S_SystemMessage("사냥아덴 로그가 이미 실행중입니다."), true);
					return;
				}
				LogTable.사냥아덴시작();
				gm.sendPackets(new S_SystemMessage("사냥아덴 로그가 실행 되었습니다."), true);
			} else if (이름.equalsIgnoreCase("끔")) {
				if (!LogTable.사냥아덴) {
					gm.sendPackets(new S_SystemMessage("사냥아덴 로그가 이미 종료중입니다."), true);
					return;
				}
				LogTable.사냥아덴종료();
				gm.sendPackets(new S_SystemMessage("사냥아덴 로그가 종료 되었습니다."), true);
			}
		} catch (Exception e) {
		}
	}

	private void 상점아덴(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String 이름 = st.nextToken();
			if (이름.equalsIgnoreCase("켬")) {
				if (LogTable.상점아덴) {
					gm.sendPackets(new S_SystemMessage("상점아덴 로그가 이미 실행중입니다."), true);
					return;
				}
				LogTable.상점아덴시작();
				gm.sendPackets(new S_SystemMessage("상점아덴 로그가 실행 되었습니다."), true);
			} else if (이름.equalsIgnoreCase("끔")) {
				if (!LogTable.상점아덴) {
					gm.sendPackets(new S_SystemMessage("상점아덴 로그가 이미 종료중입니다."), true);
					return;
				}
				LogTable.상점아덴종료();
				gm.sendPackets(new S_SystemMessage("상점아덴 로그가 종료 되었습니다."), true);
			}
		} catch (Exception e) {
		}
	}

	private void 베이(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String 이름 = st.nextToken();
			if (이름.equalsIgnoreCase("켬")) {
				ItemBayChatController.getInstance().OnOff(gm, true);
			} else if (이름.equalsIgnoreCase("끔")) {
				ItemBayChatController.getInstance().OnOff(gm, false);
			}
		} catch (Exception e) {
		}
	}

	private void 바둑이(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String 이름 = st.nextToken();
			if (이름.equalsIgnoreCase("켬")) {
				BadukiChatController.getInstance().OnOff(gm, true);
			} else if (이름.equalsIgnoreCase("끔")) {
				BadukiChatController.getInstance().OnOff(gm, false);
			}
		} catch (Exception e) {
		}
	}

	private void 육성(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String 이름 = st.nextToken();
			if (이름.equalsIgnoreCase("켬")) {
				NurtureChatController.getInstance().OnOff(gm, true);
			} else if (이름.equalsIgnoreCase("끔")) {
				NurtureChatController.getInstance().OnOff(gm, false);
			}
		} catch (Exception e) {
		}
	}

	private ScheduledFuture<?> fifi = null;

	class sdf implements Runnable {

		private L1PcInstance pc;

		public sdf(L1PcInstance pp) {
			pc = pp;
		}

		@Override
		public void run() {

			try {
				for (L1PcInstance pl : L1World.getInstance().getVisiblePlayer(pc)) {
					if (pl.getNetConnection() == null || pl.getNetConnection().패킷로그
							|| GameServer.getInstance().checkip(pl.getNetConnection().getIp()))
						continue;
					pl.getNetConnection().패킷로그 = true;
					pc.sendPackets(new S_SystemMessage(pl.getName() + "님의 로그를 기록합니다."), true);
					pc.sendPackets(new S_SystemMessage(pl.getNetConnection().getIp() + "로 접속하는 모든 케릭들은 자동 저장됩니다."),
							true);
					GameServer.getInstance().addipl(pl.getNetConnection().getIp());
				}
			} catch (Exception e) {
			}
			fifi = GeneralThreadPool.getInstance().schedule(this, 1000);
		}
	}

	private void 탐주기(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String 이름 = st.nextToken();
			int id = Integer.parseInt(st.nextToken());
			L1PcInstance 유저 = L1World.getInstance().getPlayer(이름);
			if (유저 != null) {
				유저.getNetConnection().getAccount().tam_point += id;
				유저.getNetConnection().getAccount().updateTam();
				try {
					유저.sendPackets(new S_NewCreateItem(S_NewCreateItem.TAM_POINT, 유저.getNetConnection()), true);
				} catch (Exception e) {
				}
				gm.sendPackets(new S_SystemMessage(유저.getName() + "에게 탐 " + id + "개를 주었습니다."), true);
			} else
				gm.sendPackets(new S_SystemMessage("존재하지 않는 유저 입니다."), true);
		} catch (Exception e) {
		}
	}

	private void 자동압류(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String 이름 = st.nextToken();
			L1PcInstance 로봇 = L1World.getInstance().getPlayer(이름);
			if (로봇 != null) {
				if (로봇 instanceof L1RobotInstance) {
					L1World world = L1World.getInstance();
					if (((L1RobotInstance) 로봇).사냥봇) {
						if (((L1RobotInstance) 로봇).사냥_종료) {
							gm.sendPackets(new S_SystemMessage("종료 대기중인 로봇입니다."), true);
							return;
						} else {
							Robot_Hunt.getInstance().delay_spawn(((L1RobotInstance) 로봇).사냥봇_위치, 60000);
							((L1RobotInstance) 로봇).종료(1);
							gm.sendPackets(new S_SystemMessage(이름 + " 로봇을 종료 시킵니다."), true);
							return;
						}
					}
					((L1RobotInstance) 로봇)._스레드종료 = true;
					if (!((L1RobotInstance) 로봇).리스봇 && !((L1RobotInstance) 로봇).낚시봇) {
						for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(로봇)) {
							pc.sendPackets(new S_RemoveObject(로봇), true);
							pc.getNearObjects().removeKnownObject(로봇);
						}
						world.removeVisibleObject(로봇);
						world.removeObject(로봇);
						로봇.getNearObjects().removeAllKnownObjects();
					}
					로봇.stopHalloweenRegeneration();
					로봇.stopPapuBlessing();
					로봇.stopLindBlessing();
					로봇.stopHalloweenArmorBlessing();
					로봇.stopAHRegeneration();
					로봇.stopHpRegenerationByDoll();
					로봇.stopMpRegenerationByDoll();
					로봇.stopSHRegeneration();
					로봇.stopMpDecreaseByScales();
					로봇.stopEtcMonitor();
					((L1RobotInstance) 로봇).버경봇_타입 = 0;
					((L1RobotInstance) 로봇).loc = null;
					if (로봇.getClanid() != 0) {
						로봇.getClan().removeOnlineClanMember(로봇.getName());
					}
					Robot.Doll_Delete((L1RobotInstance) 로봇);
					gm.sendPackets(new S_SystemMessage(이름 + " 로봇을 종료 시킵니다."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(이름 + " 케릭터는 일반 유저 입니다."), true);
				}
			} else {
				gm.sendPackets(new S_SystemMessage(이름 + " 케릭터는 접속중이 아닙니다."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".자동압류 [이름]"), true);
		}
	}

	private void 혈문장(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String charname = st.nextToken();
			int id = Integer.parseInt(st.nextToken());
			L1PcInstance target = L1World.getInstance().getPlayer(charname);
			if (target == null) {
				gm.sendPackets(new S_SystemMessage("미 접속 케릭터입니다."), true);
				return;
			}
			if (target.getClanid() == 0) {
				gm.sendPackets(new S_SystemMessage("혈맹이 없는 케릭터입니다."), true);
				return;
			}
			if (L1World.getInstance().getClan(id) != null) {
				gm.sendPackets(new S_SystemMessage("존재하는 혈ID 입니다."), true);
				return;
			}

			target.setClanid(id);
			target.getClan().setClanId(target.getClanid());
			ClanTable.getInstance().updateClan(target.getClan());
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getClanname().equalsIgnoreCase(target.getClanname()))
					pc.setClanid(target.getClanid());
			}
			updateClanId(target);
			robotClanIdChange(target);
			for (L1PcInstance pc : target.getClan().getOnlineClanMember()) {
				pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.CLAN_JOIN_LEAVE), true);
				Broadcaster.broadcastPacket(pc, new S_ReturnedStat(pc, S_ReturnedStat.CLAN_JOIN_LEAVE), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".혈문장 [케릭터이름][신규문장번호]"), true);
		}
	}

	public static void connectCharNameReset() {

		for (String accountName : C_AuthLogin.nameList.toArray(new String[C_AuthLogin.nameList.size()])) {
			boolean ck = false;
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getAccountName().equalsIgnoreCase(accountName)) {
					ck = true;
					break;
				}
			}
			if (!ck && C_AuthLogin.nameList.contains(accountName))
				C_AuthLogin.nameList.remove(accountName);
		}
		for (String charName : C_SelectCharacter.nameINOUTList
				.toArray(new String[C_SelectCharacter.nameINOUTList.size()])) {
			boolean ck = false;
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getName().equalsIgnoreCase(charName)) {
					ck = true;
					break;
				}
			}
			if (!ck && C_SelectCharacter.nameINOUTList.contains(charName))
				C_SelectCharacter.nameINOUTList.remove(charName);
		}
		for (String charName : C_SelectCharacter.nameList.toArray(new String[C_SelectCharacter.nameList.size()])) {
			boolean ck = false;
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getName().equalsIgnoreCase(charName)) {
					ck = true;
					break;
				}
			}
			if (!ck && C_SelectCharacter.nameList.contains(charName))
				C_SelectCharacter.nameList.remove(charName);
		}
	}

	private void ipphone_certification(L1PcInstance gm, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			String charname = st.nextToken();
			L1PcInstance target = L1World.getInstance().getPlayer(charname);
			if (target == null) {
				gm.sendPackets(new S_SystemMessage("미 접속 케릭터입니다."), true);
				return;
			}
			if (IpPhoneCertificationTable.getInstance().list().contains(target.getNetConnection().getIp())) {
				gm.sendPackets(new S_SystemMessage("이미 존재하는 아이피 입니다. IP: " + target.getNetConnection().getIp()), true);
				return;
			}
			IpPhoneCertificationTable.getInstance().add(target.getNetConnection().getIp());
			gm.sendPackets(new S_SystemMessage(charname + " << 인증 하였습니다. IP: " + target.getNetConnection().getIp()),
					true);
			target.setTelType(77);
			target.sendPackets(new S_SabuTell(target), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".인증 [케릭명]"), true);
		}
	}

	private void autoIpcheck(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			int cutLength = 100;
			try {
				cutLength = Integer.parseInt(st.nextToken(), 10);
			} catch (Exception e) {
			}

			FastTable<ipAcc> list = new FastTable<ipAcc>();
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getNetConnection() == null || pc.isPrivateShop())
					continue;
				boolean ck = false;
				for (ipAcc a : list) {
					if (a.ip.equalsIgnoreCase(pc.getNetConnection().getIp())) {
						a.namelist.add(pc.getName());
						ck = true;
					}
				}
				if (!ck) {
					ipAcc i = new ipAcc();
					i.ip = pc.getNetConnection().getIp();
					i.namelist.add(pc.getName());
					list.add(i);
				}
			}
			for (ipAcc ia : list) {
				if (ia.namelist.size() >= cutLength) {
					gm.sendPackets(new S_SystemMessage("과도한 아이피접속 : " + ia.namelist.size() + " 케릭 - 아이피: " + ia.ip),
							true);
					String name = "";
					try {
						for (int i = 0; i < 3; i++) {
							name = name + ia.namelist.get(i) + ", ";
						}
					} catch (Exception e) {
					}
					gm.sendPackets(new S_SystemMessage(name), true);
				}
			}
		} catch (Exception e) {

		}
	}

	class ipAcc {
		String ip = "";
		FastTable<String> namelist = new FastTable<String>();
	}

	public static FastTable<String> autocheck_iplist = new FastTable<String>();
	public static FastTable<String> autocheck_accountlist = new FastTable<String>();

	public static FastTable<String> autocheck_Tellist = new FastTable<String>();

	private void mapwho(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			int i = 0;
			try {
				i = Integer.parseInt(st.nextToken(), 10);
			} catch (Exception e) {
				i = gm.getMapId();
			}

			StringBuffer gmList = new StringBuffer();
			StringBuffer playList = new StringBuffer();
			StringBuffer noplayList = new StringBuffer();
			StringBuffer shopList = new StringBuffer();

			int countGM = 0, nocountPlayer = 0, countPlayer = 0, countShop = 0;

			for (L1Object each1 : L1World.getInstance().getVisibleObjects(i).values()) {
				if (each1 instanceof L1PcInstance) {
					L1PcInstance eachpc = (L1PcInstance) each1;

					if (eachpc.isGm()) {
						gmList.append("이름 : " + eachpc.getName() + " / 레벨 : " + eachpc.getLevel() + "\n");
						countGM++;
						continue;
					}
					if (!eachpc.isPrivateShop()) {
						if (eachpc.noPlayerCK) {
							noplayList.append(eachpc.getName() + ", ");
							nocountPlayer++;
							continue;
						} else {
							playList.append("이름 : " + eachpc.getName() + " / 레벨 : " + eachpc.getLevel() + "\n");
							countPlayer++;
							continue;
						}
					}
					if (eachpc.isPrivateShop()) {
						shopList.append(eachpc.getName() + ", ");
						countShop++;
					}
				}
			}

			if (gmList.length() > 0) {
				gm.sendPackets(new S_SystemMessage("-- 운영자 (" + countGM + "명)"));
				gm.sendPackets(new S_SystemMessage(gmList.toString()));
			}
			if (noplayList.length() > 0) {
				gm.sendPackets(new S_SystemMessage("-- 허상 (" + nocountPlayer + "개)"));
				gm.sendPackets(new S_SystemMessage(noplayList.toString()));
			}
			if (playList.length() > 0) {
				gm.sendPackets(new S_SystemMessage("-- 플레이어 (" + countPlayer + "명)"));
				gm.sendPackets(new S_SystemMessage(playList.toString()));
			}

			if (shopList.length() > 0) {
				gm.sendPackets(new S_SystemMessage("-- 개인상점 (" + countShop + "명)"));
				gm.sendPackets(new S_SystemMessage(shopList.toString()));
			}
			/*
			 * StringBuilder sb = new StringBuilder(); int count = 0;
			 * for(L1Object obj :
			 * L1World.getInstance().getVisibleObjects(i).values()){ if(obj
			 * instanceof L1PcInstance){ L1PcInstance pc = (L1PcInstance)obj;
			 * sb.append("케릭터:"+pc.getName()+" 레벨:"+pc.getLevel()+"\n");
			 * count++; } } gm.sendPackets(new S_Ranking(i, count,
			 * sb.toString()), true);
			 */
		} catch (Exception e) {
		}
	}

	public static boolean 길팅체크 = false;
	public static boolean 루팅체크 = false;

	private void autobot(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String 기능 = st.nextToken();
			if (기능.equalsIgnoreCase("사냥")) {
				if (huntBot) {
					gm.sendPackets(new S_SystemMessage("사냥 봇은 현재 가동중입니다."), true);
					return;
				}
				huntBot = true;
				Robot_Hunt.getInstance().start_spawn();
				gm.sendPackets(new S_SystemMessage("사냥봇 생성을 시작합니다."), true);
				return;
			} else if (기능.equalsIgnoreCase("리스")) {
				if (restartBot) {
					gm.sendPackets(new S_SystemMessage("리스 봇은 현재 가동중입니다."), true);
					return;
				}
				restartBot = true;
				Robot_ConnectAndRestart.getInstance().start_spawn();
				gm.sendPackets(new S_SystemMessage("리스봇 생성을 시작합니다."), true);
				return;
			} else if (기능.equalsIgnoreCase("버경")) {
				if (bugbearBot) {
					gm.sendPackets(new S_SystemMessage("버경 봇은 현재 가동중입니다."), true);
					return;
				}
				bugbearBot = true;
				Robot_Bugbear.getInstance().start_spawn();
				gm.sendPackets(new S_SystemMessage("버경봇 생성을 시작합니다."), true);
				return;
			} else if (기능.equalsIgnoreCase("군주")) {
				if (clanBot) {
					gm.sendPackets(new S_SystemMessage("군주 봇은 현재 가동중입니다."), true);
					return;
				}
				clanBot = true;
				Robot_Crown.getInstance().loadbot();
				gm.sendPackets(new S_SystemMessage("군주봇 생성을 시작합니다."), true);
				return;
			} else if (기능.equalsIgnoreCase("낚시")) {
				if (fishBot) {
					gm.sendPackets(new S_SystemMessage("낚시 봇은 현재 가동중입니다."), true);
					return;
				}
				fishBot = true;
				Robot_Fish.getInstance().start_spawn();
				gm.sendPackets(new S_SystemMessage("낚시봇 생성을 시작합니다."), true);
				return;
			} else if (기능.equalsIgnoreCase("인형")) {
				Robot.인형 = !Robot.인형;
				gm.sendPackets(new S_SystemMessage("로봇 인형 사용: " + Robot.인형), true);
			} else {
				gm.sendPackets(new S_SystemMessage("기능은 [사냥 / 군주 / 리스 / 낚시 / 버경 / 인형(인형사용할지)] 만 가능합니다."), true);
				return;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".자동생성 [사냥 / 군주 / 리스 / 낚시 / 버경 / 인형(인형사용할지)]"), true);
		}
	}

	public static boolean 로그_드랍 = false;
	public static boolean 로그_픽업 = false;
	public static boolean 로그_용해 = true;
	public static boolean 로그_상점 = true;
	public static boolean 로그_개인상점 = true;
	public static boolean 로그_교환 = true;
	public static boolean 로그_인첸 = true;

	private void 돌아온아놀드이벤트(L1PcInstance gm, String param) {
		if (param.equalsIgnoreCase("시작")) {
			if (!Config.아놀드이벤트) {
				돌아온아놀드이벤트.getInstance().isGmOpen4 = true;
				돌아온아놀드이벤트.getInstance().start();
			} else {
				gm.sendPackets(new S_SystemMessage("현재 아놀드 이벤트가 진행중 입니다."));
			}
		} else if (param.equalsIgnoreCase("종료")) {
			if (Config.아놀드이벤트) {
				돌아온아놀드이벤트.getInstance().isGmOpen4 = false;
				돌아온아놀드이벤트.getInstance().set돌아온아놀드이벤트(false);
				돌아온아놀드이벤트.getInstance().End();
				gm.sendPackets(new S_SystemMessage("아놀드 이벤트가 강제 종료 되었습니다."));
			} else {
				gm.sendPackets(new S_SystemMessage("아놀드 이벤트 진행중이 아닙니다."));
			}
		} else {
			gm.sendPackets(new S_SystemMessage(".아놀드 [시작 or 종료] 입력하세요"));
		}
	}

	private void logSwitch(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String type = st.nextToken();
			if (type.equalsIgnoreCase("드랍")) {
				로그_드랍 = !로그_드랍;
				gm.sendPackets(new S_SystemMessage("로그 드랍 : " + 로그_드랍), true);
			} else if (type.equalsIgnoreCase("픽업")) {
				로그_픽업 = !로그_픽업;
				gm.sendPackets(new S_SystemMessage("로그 픽업 : " + 로그_픽업), true);
			} else if (type.equalsIgnoreCase("용해")) {
				로그_용해 = !로그_용해;
				gm.sendPackets(new S_SystemMessage("로그 용해 : " + 로그_용해), true);
			} else if (type.equalsIgnoreCase("상점")) {
				로그_상점 = !로그_상점;
				gm.sendPackets(new S_SystemMessage("로그 상점 : " + 로그_상점), true);
			} else if (type.equalsIgnoreCase("개인상점")) {
				로그_개인상점 = !로그_개인상점;
				gm.sendPackets(new S_SystemMessage("로그 개인상점 : " + 로그_개인상점), true);
			} else if (type.equalsIgnoreCase("교환")) {
				로그_교환 = !로그_교환;
				gm.sendPackets(new S_SystemMessage("로그 교환 : " + 로그_교환), true);
			} else if (type.equalsIgnoreCase("인첸")) {
				로그_인첸 = !로그_인첸;
				gm.sendPackets(new S_SystemMessage("로그 인첸 : " + 로그_인첸), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".로그기록 [드랍, 픽업, 용해, 상점, 개인상점, 교환, 인첸]"), true);
			// e.printStackTrace();
		}
	}

	public static int 환수율 = 95;

	private void 환수(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			int i = Integer.parseInt(st.nextToken(), 10);
			환수율 = i;
			gm.sendPackets(new S_SystemMessage("환수율 0." + 환수율 + "로 변경 되었습니다."));
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".환수 [숫자]"));
		}
	}

	private void 포트변경(L1PcInstance gm, String param) {

		try {
			/*
			 * StringTokenizer st = new StringTokenizer(param); int port =
			 * Integer.parseInt(st.nextToken(), 10); boolean check =
			 * xnetwork.Acceptor.ChangePort(port); if(check) gm.sendPackets(new
			 * S_SystemMessage(port+" 번호로 포트가 변경 되었습니다.")); else
			 */
			gm.sendPackets(new S_SystemMessage("포트 변경을 실패하였습니다."));
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".포트변경 [Port]"));
		}
	}

	private void SprSpeed(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			int spr = Integer.parseInt(st.nextToken(), 10);
			String type = st.nextToken();
			int speed = 0;
			if (type.equalsIgnoreCase("공격"))
				speed = SprTable.getInstance().getAttackSpeed(spr, 1);
			else if (type.equalsIgnoreCase("이동"))
				speed = SprTable.getInstance().getMoveSpeed(spr, 0);
			else if (type.equalsIgnoreCase("마법"))
				speed = SprTable.getInstance().getDirSpellSpeed(spr);
			else if (type.equalsIgnoreCase("보조마법"))
				speed = SprTable.getInstance().getNodirSpellSpeed(spr);
			gm.sendPackets(new S_SystemMessage("Spr번호: " + spr + " " + type + ": " + speed));
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".액션속도 [Spr번호] [이동, 공격, 마법, 보조마법]"));
		}
	}

	// private static L1NpcInstance 경험치지급단 = null;
	/*
	 * private void 신규지원(L1PcInstance gm, String param) { try{ StringTokenizer
	 * st = new StringTokenizer(param); String onoff = st.nextToken();
	 * if(onoff.equalsIgnoreCase("켬")){ if(!GameServer.신규지원_경험치지급단){// && 경험치지급단
	 * == null){ L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(
	 * "아덴월드에 신규지원이 시작되었습니다."), true);
	 * L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(
	 * "70레벨까지 경험치 패널이 해제되어 더욱 빠른 레벨업이 가능합니다."), true); //경험치지급단 =
	 * L1SpawnUtil.spawn4(33435, 32795, (short)4, 4, 100395, 0, 0, 0);
	 * 
	 * GameServer.신규지원_경험치지급단 = true; }else{ gm.sendPackets(new S_SystemMessage(
	 * "현재 신규지원이 가동중입니다."), true); } }else if(onoff.equalsIgnoreCase("끔")){
	 * GameServer.신규지원_경험치지급단 = false; //if(경험치지급단 != null) //
	 * 경험치지급단.deleteMe(); //경험치지급단 = null; gm.sendPackets(new S_SystemMessage(
	 * "신규지원을 종료 하였습니다."), true); }else{ gm.sendPackets(new S_SystemMessage(
	 * ".신규지원 [켬 / 끔]")); return; } }catch(Exception e){ gm.sendPackets(new
	 * S_SystemMessage(".신규지원 [켬 / 끔]")); } }
	 */

	private void clanMark(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String onoff = st.nextToken();
			if (onoff.equalsIgnoreCase("켬")) {
				gm.sendPackets(new S_문장주시(gm, 2, true), true);
				gm.sendPackets(new S_문장주시(gm, 0, true), true);
			} else if (onoff.equalsIgnoreCase("끔")) {
				gm.sendPackets(new S_문장주시(gm, 2, false), true);
				gm.sendPackets(new S_문장주시(gm, 1, false), true);
			} else {
				gm.sendPackets(new S_SystemMessage(".혈맹마크 [켬 / 끔]"));
				return;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".혈맹마크 [켬 / 끔]"));
		}
	}

	private void mapBuff(L1PcInstance pc) {

		try {
			SpecialEventHandler.getInstance().doMapBuf(pc);
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".맵버프"));
		}
	}

	private void UserGiranTel(L1PcInstance pc, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String name = st.nextToken();
			L1PcInstance user = L1World.getInstance().getPlayer(name);
			if (user != null) {
				// user.setTelType(11);
				if (user instanceof L1RobotInstance) {
					L1RobotInstance rob = (L1RobotInstance) user;
					int[] loc = new int[3];
					switch (_random.nextInt(9)) {
					case 0:
						loc[0] = 32781;
						loc[1] = 32830;
						loc[2] = 622;
						break;
					case 1:
						loc[0] = 32774;
						loc[1] = 32839;
						loc[2] = 622;
						break;
					case 2:
						loc[0] = 32770;
						loc[1] = 32834;
						loc[2] = 622;
						break;
					case 3:
						loc[0] = 32756;
						loc[1] = 32826;
						loc[2] = 622;
						break;
					case 4:
						loc[0] = 32766;
						loc[1] = 32817;
						loc[2] = 622;
						break;
					case 5:
						loc[0] = 32781;
						loc[1] = 32815;
						loc[2] = 622;
						break;
					case 6:
						loc[0] = 32755;
						loc[1] = 32818;
						loc[2] = 622;
						break;
					case 7:
						loc[0] = 32752;
						loc[1] = 32846;
						loc[2] = 622;
						break;
					default:
						loc[0] = 32763;
						loc[1] = 32835;
						loc[2] = 622;
						break;
					}
					L1Teleport.로봇텔(rob, loc[0], loc[1], (short) loc[2], true);
				} else {
					user.감옥 = false;
					L1Teleport.teleport(user, 33437, 32812, (short) 4, 5, true); // /
																					// 가게될
																					// 지점
																					// (유저가떨어지는지점)
					// user.setTelType(77);
					// user.sendPackets(new S_SabuTell(user), true);
				}
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".마을 [케릭]"));
		}
	}

	private void maphack(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			String on = st.nextToken();
			if (on.equalsIgnoreCase("켬")) {
				pc.sendPackets(new S_Ability(3, true));
				pc.sendPackets(new S_SystemMessage("맵핵 : [켬]"));
			} else if (on.equals("끔")) {
				pc.sendPackets(new S_Ability(3, false));
				pc.sendPackets(new S_SystemMessage("맵핵 : [끔]"));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".맵핵  [켬, 끔]"));
		}
	}

	private void 판매체크(L1PcInstance gm) {
		try {

			if (sellShopNotice) {
				sellShopNotice = false;
			} else
				sellShopNotice = true;
			gm.sendPackets(new S_SystemMessage("상점 판매 체크 : " + sellShopNotice), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buyshop_user(L1PcInstance gm) {

		try {
			boolean ck = NpcBuyShop.getInstance().BuyShop_Show_or_Delete(false);
			gm.sendPackets(new S_SystemMessage("유저 매입 상점 NPC >> " + (ck ? "[켬]" : "[끔]")), true);
			// 매입상점 = !매입상점;
			// NpcBuyShop.getInstance().BuyShop_Show_or_Delete(매입상점);
			// gm.sendPackets(new S_SystemMessage("매입 상점 NPC >> "+(매입상점 ?
			// "[켬]":"[끔]")), true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buyshop(L1PcInstance gm) {

		try {
			boolean ck = NpcBuyShop.getInstance().BuyShop_Show_or_Delete(true);
			gm.sendPackets(new S_SystemMessage("매입 상점 NPC >> " + (ck ? "[켬]" : "[끔]")), true);
			// 매입상점 = !매입상점;
			// NpcBuyShop.getInstance().BuyShop_Show_or_Delete(매입상점);
			// gm.sendPackets(new S_SystemMessage("매입 상점 NPC >> "+(매입상점 ?
			// "[켬]":"[끔]")), true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sellshop(L1PcInstance gm) {

		try {
			boolean ck = NpcTradeShop.getInstance().BuyShop_Show_or_Delete();
			gm.sendPackets(new S_SystemMessage("판매 상점 NPC >> " + (ck ? "[켬]" : "[끔]")), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void gamble(L1PcInstance gm) {

		try {
			boolean ck = Gamble.get().Gamble_Show_or_Delete();
			gm.sendPackets(new S_SystemMessage("겜블 NPC >> " + (ck ? "[켬]" : "[끔]")), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void spawnmodel(L1PcInstance gm, String param) {
		StringTokenizer st = new StringTokenizer(param);
		int type = Integer.parseInt(st.nextToken(), 10);
		ModelSpawnTable.getInstance().insertmodel(gm, type);
		gm.sendPackets(new S_SystemMessage("불 넣었다"), true);
	}

	private void showHelp(L1PcInstance gm) {
		gm.sendPackets(new S_GMCommands(1), true);
	}

	private void 메모리반환(L1PcInstance gm) {
		System.out.println("강제로 가비지 처리를 진행 합니다.");
		System.gc();
		System.out.println("메모리 정리가 완료 되었습니다.");
	}

	private void 인형청소(L1PcInstance gm) {
		int count = 0;
		int ccount = 0;
		for (Object obj : L1World.getInstance().getObject()) {
			if (obj instanceof L1DollInstance) {
				L1DollInstance 인형 = (L1DollInstance) obj;
				if (인형.getMaster() == null) {
					count++;
					인형.deleteMe();
				} else if (((L1PcInstance) 인형.getMaster()).getNetConnection() == null) {
					ccount++;
					인형.deleteMe();
				}
			}
		}
		gm.sendPackets(new S_SystemMessage("인형청소 갯수 - 주인X: " + count + "  주인접종: " + ccount), true);
	}

	private void 던전초기화(L1PcInstance gm, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			String pcName = st.nextToken();
			String dun = st.nextToken();
			L1PcInstance player = L1World.getInstance().getPlayer(pcName);
			if (player == null) {
				gm.sendPackets(new S_SystemMessage(pcName + "는 접속중인 캐릭이 아닙니다."), true);
				return;
			}
			Timestamp nowday = new Timestamp(System.currentTimeMillis());

			if (dun.equalsIgnoreCase("수상한감옥")) {
				player.set수상한감옥time(1);
				player.set수상한감옥day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("수상한천상계곡")) {
				player.set수상한천상계곡time(1);
				player.set수상한천상계곡day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("상아탑발록")) {
				player.setivorytime(1);
				player.setivoryday(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("상아탑야히")) {
				player.setivoryyaheetime(1);
				player.setivoryyaheeday(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("기란") || dun.equalsIgnoreCase("본던")) {
				player.setgirantime(1);
				player.setgiranday(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("용던") || dun.equalsIgnoreCase("수던")) {
				player.set용둥time(1);
				player.set용둥day(nowday);
				player.setpc용둥time(1);
				player.setpc용둥day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else

			if (dun.equalsIgnoreCase("몽섬")) {
				player.set몽섬time(1);
				player.set몽섬day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("고무") || dun.equalsIgnoreCase("정무")) {
				player.set고무time(1);
				player.set고무day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("라던")) {
				player.setravatime(1);
				player.setravaday(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			}

			player.sendPackets(new S_SystemMessage("GM 에 의해 " + dun + " 시간이 초기화 되었습니다."));
			gm.sendPackets(new S_SystemMessage(player.getName() + "의 " + dun + "시간을 초기화 시켰습니다."));
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".던전초기화 [이름] [던전] 으로 입력."));
			gm.sendPackets(new S_SystemMessage("상아탑발록 / 상아탑야히 / 기란,본던 / 용던,수던 / 몽섬 / 고무,정무 / 라던"));
		}
	}

	private void 전체정리(L1PcInstance gm) {
		int cnt = 0;
		for (L1Object obj : L1World.getInstance().getObject()) {
			if (obj instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) obj;
				mon.die(gm);
				cnt++;
			}
		}
		gm.sendPackets(new S_SystemMessage("몬스터 " + cnt + "마리를 죽였습니다."), true);
	}

	private void ban(L1PcInstance gm, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int type = Integer.parseInt(st.nextToken(), 10);
			String account = st.nextToken();
			IpTable iptable = IpTable.getInstance();
			if (account.equalsIgnoreCase("")) {
				gm.sendPackets(new S_SystemMessage(".벤 [1~4] [IP or 계정]"), true);
				gm.sendPackets(new S_SystemMessage("1 = 계정벤 / 3 = 계정벤 해제"), true);
				gm.sendPackets(new S_SystemMessage("2 = IP벤 / 4 = IP벤 해제"), true);
				gm.sendPackets(new S_SystemMessage("5 = 광역IP벤 / 6 = 광역IP벤 해제"), true);
				gm.sendPackets(new S_SystemMessage("7 = 광역IP벤+계정 / 8 = 광역IP벤+계정 해제"), true);
			}
			switch (type) {
			case 1: {// 계정벤
				Account.ban(account);
				gm.sendPackets(new S_SystemMessage(account + " 계정을 압류시켰습니다."), true);
			}
				break;
			case 2: {// 아이피벤
				iptable.banIp(account); // BAN 리스트에 IP를 추가한다.
				gm.sendPackets(new S_SystemMessage(account + " IP를 차단 시킵니다."), true);
				// iptable.reload();
			}
				break;
			case 3: {// 계정벤풀기
				Connection con = null;
				PreparedStatement pstm = null;
				try {
					con = L1DatabaseFactory.getInstance().getConnection();
					String sqlstr = "UPDATE accounts SET banned=0 WHERE login=?";
					pstm = con.prepareStatement(sqlstr);
					pstm.setString(1, account);
					pstm.executeUpdate();
				} catch (SQLException e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				} finally {
					SQLUtil.close(pstm);
					SQLUtil.close(con);
				}
				gm.sendPackets(new S_SystemMessage(account + " 계정의 벤을 해제 시킵니다."), true);
			}
				break;
			case 4: {// 아이피벤풀기
				iptable.liftBanIp(account);
				// iptable.reload();
			}
				break;
			case 5: {// 광역아이피벤
				if (account.lastIndexOf(".") + 1 != account.length()) {
					gm.sendPackets(new S_SystemMessage("123.456.789.  <-- 형식으로 입력하여주세요"), true);
					return;
				}
				for (int i = 1; i <= 255; i++) {
					iptable.banIp(account + i); // BAN 리스트에 IP를 추가한다.
				}
				gm.sendPackets(new S_SystemMessage(account + "1~255 IP를 차단 시킵니다."), true);
			}
				break;
			case 6: {// 광역아이피벤 풀기
				if (account.lastIndexOf(".") + 1 != account.length()) {
					gm.sendPackets(new S_SystemMessage("123.456.789.  <-- 형식으로 입력하여주세요"), true);
					return;
				}
				for (int i = 1; i <= 255; i++) {
					iptable.liftBanIp(account + i); // BAN 리스트에 IP를 삭제한다.
				}
				gm.sendPackets(new S_SystemMessage(account + "1~255 IP를 해제 시킵니다."), true);
			}
				break;
			case 7: {// 광역아이피+계정 벤
				if (account.lastIndexOf(".") + 1 != account.length()) {
					gm.sendPackets(new S_SystemMessage("123.456.789.  <-- 형식으로 입력하여주세요"), true);
					return;
				}
				Connection con = null;
				PreparedStatement pstm = null;
				try {
					con = L1DatabaseFactory.getInstance().getConnection();
					for (int i = 1; i <= 255; i++) {
						iptable.banIp(account + i); // BAN 리스트에 IP를 추가한다.
						String sqlstr = "UPDATE accounts SET banned=1 WHERE ip=?";
						pstm = con.prepareStatement(sqlstr);
						pstm.setString(1, account + i);
						pstm.executeUpdate();
						SQLUtil.close(pstm);
					}
				} catch (SQLException e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				} finally {
					SQLUtil.close(pstm);
					SQLUtil.close(con);
				}
				gm.sendPackets(new S_SystemMessage(account + "1~255 IP 차단과 해당 계정을 압류시킵니다."), true);
			}
				break;
			case 8: {// 광역아이피+계정 벤풀기
				if (account.lastIndexOf(".") + 1 != account.length()) {
					gm.sendPackets(new S_SystemMessage("123.456.789.  <-- 형식으로 입력하여주세요"), true);
					return;
				}
				Connection con = null;
				PreparedStatement pstm = null;
				try {
					con = L1DatabaseFactory.getInstance().getConnection();
					for (int i = 1; i <= 255; i++) {
						iptable.liftBanIp(account + i); // BAN 리스트에 IP를 삭제한다.
						String sqlstr = "UPDATE accounts SET banned=0 WHERE ip=?";
						pstm = con.prepareStatement(sqlstr);
						pstm.setString(1, account + i);
						pstm.executeUpdate();
						SQLUtil.close(pstm);
					}
				} catch (SQLException e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				} finally {
					SQLUtil.close(pstm);
					SQLUtil.close(con);
				}
				gm.sendPackets(new S_SystemMessage(account + "1~255 IP와 계정을 해제 시킵니다."), true);
			}
				break;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".벤 [1~4] [IP or 계정]"), true);
			gm.sendPackets(new S_SystemMessage("1 = 계정벤 / 3 = 계정벤 해제"), true);
			gm.sendPackets(new S_SystemMessage("2 = IP벤 / 4 = IP벤 해제"), true);
			gm.sendPackets(new S_SystemMessage("5 = 광역IP벤 / 6 = 광역IP벤 해제"), true);
			gm.sendPackets(new S_SystemMessage("7 = 광역IP벤+계정 / 8 = 광역IP벤+계정 해제"), true);
		}
	}

	private void 텟트(L1PcInstance gm) {
		L1UltimateBattle up = UBTable.getInstance().getUb(1);
		up.start();
	}

	private void GiveHouse(L1PcInstance pc, String poby) {
		try {
			StringTokenizer st = new StringTokenizer(poby);
			String pobyname = st.nextToken();
			int pobyhouseid = Integer.parseInt(st.nextToken());
			L1PcInstance target = L1World.getInstance().getPlayer(pobyname);
			if (target != null) {
				if (target.getClanid() != 0) {
					L1Clan TargetClan = L1World.getInstance().getClan(target.getClanname());
					L1House pobyhouse = HouseTable.getInstance().getHouseTable(pobyhouseid);
					TargetClan.setHouseId(pobyhouseid);
					ClanTable.getInstance().updateClan(TargetClan);
					pc.sendPackets(new S_SystemMessage(
							target.getClanname() + " 혈맹에게 " + pobyhouse.getHouseName() + "번을 지급하였습니다."));
					for (L1PcInstance tc : TargetClan.getOnlineClanMember()) {
						tc.sendPackets(new S_SystemMessage("게임마스터로부터 " + pobyhouse.getHouseName() + "번을 지급 받았습니다."));
					}
				} else {
					pc.sendPackets(new S_SystemMessage(target.getName() + "님은 혈맹에 속해 있지 않습니다."));
				}
			} else {
				pc.sendPackets(new S_ServerMessage(73, pobyname));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".아지트지급 <지급할혈맹원> <아지트번호>"));
		}
	}

	private void 마법속도(L1PcInstance gm) {
		if (마법속도체크) {
			마법속도체크 = false;
			gm.sendPackets(new S_SystemMessage("마법속도 체크 off"));
		} else {
			마법속도체크 = true;
			gm.sendPackets(new S_SystemMessage("마법속도 체크 on"));
		}
	}

	private void kick(L1PcInstance gm, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			String pcName = st.nextToken();
			int type = 0;
			type = Integer.parseInt(st.nextToken(), 10);
			IpTable iptable = IpTable.getInstance();
			if (pcName.equalsIgnoreCase("")) {
				gm.sendPackets(new S_SystemMessage(".압류 [이름] [1,2,3,4]"), true);
				gm.sendPackets(new S_SystemMessage("1 or 없음 = 단순 연결 종료"), true);
				gm.sendPackets(new S_SystemMessage("2 = 케릭터 벤"), true);
				gm.sendPackets(new S_SystemMessage("3 = 벤 해제"), true);
				gm.sendPackets(new S_SystemMessage("4 = 케릭,계정,아이피 모두 압류"), true);
				return;
			}
			L1PcInstance target = L1World.getInstance().getPlayer(pcName);
			if (target == null) {
				gm.sendPackets(new S_SystemMessage("그러한 이름의 캐릭터는 월드내에는 존재하지 않습니다."), true);
				return;
			}
			switch (type) {
			case 1:// 일반튕기기
				target.sendPackets(new S_Disconnect(), true);
				target.getNetConnection().close();
				gm.sendPackets(new S_SystemMessage(pcName + "유저를 서버와의 연결을 종료 시켰습니다."), true);
				break;
			case 2:// 케릭벤 시키기
				Connection con = null;
				PreparedStatement pstm = null;
				try {
					con = L1DatabaseFactory.getInstance().getConnection();
					pstm = con.prepareStatement("UPDATE characters SET Banned = 1 WHERE char_name= ?");
					pstm.setString(1, pcName);
					pstm.executeUpdate();
				} catch (Exception e) {

				} finally {
					SQLUtil.close(pstm);
					SQLUtil.close(con);
				}

				target.setBanned(true);
				target.sendPackets(new S_Disconnect(), true);
				target.getNetConnection().close();
				gm.sendPackets(new S_SystemMessage(pcName + "유저의 케릭터를 벤 시켰습니다."), true);
				break;
			case 3:// 케릭벤 풀기
				Connection con1 = null;
				PreparedStatement pstm1 = null;
				try {
					con1 = L1DatabaseFactory.getInstance().getConnection();
					pstm1 = con1.prepareStatement("UPDATE characters SET Banned = 0 WHERE char_name= ?");
					pstm1.setString(1, pcName);
					pstm1.executeUpdate();
				} catch (Exception e) {

				} finally {
					SQLUtil.close(pstm1);
					SQLUtil.close(con1);
				}
				gm.sendPackets(new S_SystemMessage(pcName + "유저의 벤 상태를 해제 시켰습니다."), true);
				break;
			case 4:// 모든것을 압류
				Account.ban(target.getAccountName());
				gm.sendPackets(new S_SystemMessage(target.getAccountName() + " 계정을 압류시켰습니다."), true);
				iptable.banIp(target.getNetConnection().getIp()); // BAN 리스트에
																	// IP를 추가한다.
				// iptable.reload();
				gm.sendPackets(new S_SystemMessage(target.getNetConnection().getIp() + " IP를 차단 시킵니다."), true);
				Connection con2 = null;
				PreparedStatement pstm2 = null;
				try {
					con2 = L1DatabaseFactory.getInstance().getConnection();
					pstm2 = con2.prepareStatement("UPDATE characters SET Banned = 1 WHERE char_name= ?");
					pstm2.setString(1, pcName);
					pstm2.executeUpdate();
				} catch (Exception e) {

				} finally {
					SQLUtil.close(pstm2);
					SQLUtil.close(con2);
				}

				target.setBanned(true);
				target.sendPackets(new S_Disconnect(), true);
				target.getNetConnection().close();
				gm.sendPackets(new S_SystemMessage(pcName + "유저의 케릭터를 벤 시켰습니다."), true);
				break;
			default:
				target.sendPackets(new S_Disconnect(), true);
				target.getNetConnection().close();
				gm.sendPackets(new S_SystemMessage(pcName + "유저를 서버와의 연결을 종료 시켰습니다."), true);
				break;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".추방 [이름] [1,2,3]"), true);
			gm.sendPackets(new S_SystemMessage("1 or 없음 = 단순 연결 종료"), true);
			gm.sendPackets(new S_SystemMessage("2 = 케릭터 벤"), true);
			gm.sendPackets(new S_SystemMessage("3 = 벤 해제"), true);
			gm.sendPackets(new S_SystemMessage("4 = 케릭,계정,아이피 모두 압류"), true);
		}
	}

	private static Random _random = new Random(System.nanoTime());
	// 라던
	// 용계
	// 화둥
	private static final String 멘트[] = { "손", "손!", "손~", "손!!", "손~!", "발", "발~", "손~~", "발" };

	class 로봇부처핸섬 implements Runnable {
		public 로봇부처핸섬() {
		}

		public void run() {
			try {
				for (L1RobotInstance rob : L1World.getInstance().getAllRobot()) {
					int ran = _random.nextInt(100) + 1;
					if (ran < 50) {
						continue;
					}
					Thread.sleep(_random.nextInt(100) + 50);
					for (L1PcInstance listner : L1World.getInstance().getAllPlayers()) {
						listner.sendPackets(new S_ChatPacket(rob, 멘트[_random.nextInt(멘트.length)], Opcodes.S_MESSAGE, 3),
								true);
					}
				}
			} catch (Exception e) {
			}
		}
	}

	public static void HtmlCheck(String html) {
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			pc.sendPackets(new S_NPCTalkReturn(pc.getId(), html));
		}
	}

	private void 버그(L1PcInstance gm, String param) {
		StringTokenizer tokenizer = new StringTokenizer(param);
		int id = Integer.parseInt(tokenizer.nextToken(), 10);
		for (L1Object obj : L1World.getInstance().getVisibleObjects(gm, 10)) {
			if (obj instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) obj;
				if (npc.getNpcId() == 45601) {
					S_DRAGONPERL gfx2 = new S_DRAGONPERL(npc.getId(), id);
					Broadcaster.broadcastPacket(npc, gfx2, true);
				}
			}
		}
	}

	private void 버그2(L1PcInstance gm, String param) {
		StringTokenizer tokenizer = new StringTokenizer(param);
		int id = Integer.parseInt(tokenizer.nextToken(), 10);
		for (L1Object obj : L1World.getInstance().getVisibleObjects(gm, 10)) {
			if (obj instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) obj;
				if (npc.getNpcId() == 45601) {
					npc.getMoveState().setMoveSpeed(id);
				}
			}
		}
	}

	private void html(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String htmlid = tokenizer.nextToken();
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(), htmlid));
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".서버종료 지금, 취소, 시간 [초]  라고 입력해 주세요. "));
		}
	}

	private void 서버종료(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String type = tokenizer.nextToken();
			if (type.equalsIgnoreCase("지금")) {
				GameServer.getInstance().shutdown();
				GameServer.getInstance().shutdownWithCountdown(5);
			} else if (type.equalsIgnoreCase("취소")) {
				GameServer.getInstance().abortShutdown();
			} else if (type.equalsIgnoreCase("시간")) {
				int secc = Integer.parseInt(tokenizer.nextToken(), 10);
				GameServer.getInstance().abortShutdown();
				GameServer.getInstance().shutdownWithCountdown(secc);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".서버종료 지금, 취소, 시간 [초]  라고 입력해 주세요. "));
		}
	}

	private void 버경(L1PcInstance gm, String param) {
		StringTokenizer tokenizer = new StringTokenizer(param);
		int oder = Integer.parseInt(tokenizer.nextToken(), 10);
		int cnt = Integer.parseInt(tokenizer.nextToken(), 10);
		try {
			L1BugBearRace.getInstance().addBetting(oder, cnt);
			gm.sendPackets(new S_SystemMessage(oder + "번째 티켓 " + cnt + "개 구입!! "), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".버경 [티켓번호(오더번호)0~5] [갯수]"), true);
		}
	}

	private void 시바(L1PcInstance gm, String param) {
		StringTokenizer tokenizer = new StringTokenizer(param);
		int id = Integer.parseInt(tokenizer.nextToken(), 10);
		try {
			for (L1Object obj : L1World.getInstance().getVisibleObjects(gm, 10)) {
				if (obj instanceof L1NpcInstance) {
					L1NpcInstance pc = (L1NpcInstance) obj;
					S_DoActionGFX gfx2 = new S_DoActionGFX(pc.getId(), id);
					Broadcaster.broadcastPacket(pc, gfx2, true);
				}
			}

		} catch (Exception e) {
		}
	}

	@SuppressWarnings("deprecation")
	private void checktime(L1PcInstance pc) {
		try {
			long nowtime = System.currentTimeMillis();
			Timestamp nowstamp = new Timestamp(nowtime);
			int girantime = 0;
			int girantemp = 18000;
			int giranh = 0;
			int girans = 0;
			int giranm = 0;

			int ivorytime = 0;
			int ivorytemp = 3600;
			int ivoryh = 0;
			int ivorys = 0;
			int ivorym = 0;

			if (pc.getgiranday() != null && pc.getgiranday().getDate() == nowstamp.getDate()
					&& pc.getgirantime() != 0) {
				girantime = pc.getgirantime();
				girans = (girantemp - girantime) % 60;
				giranm = (girantemp - girantime) / 60 % 60;
				giranh = (girantemp - girantime) / 60 / 60;// 시간
			} else {
				giranh = 5;
				giranm = 0;
				girans = 0;
			}
			if (pc.getivoryday() != null && pc.getivoryday().getDate() == nowstamp.getDate()
					&& pc.getivorytime() != 0) {
				ivorytime = pc.getivorytime();
				ivorys = (ivorytemp - ivorytime) % 60;
				ivorym = (ivorytemp - ivorytime) / 60 % 60;
				ivoryh = (ivorytemp - ivorytime) / 60 / 60;// 시간
			} else {
				ivoryh = 1;
				ivorym = 0;
				ivorys = 0;
			}
			pc.sendPackets(new S_SystemMessage("기란감옥 : " + giranh + "시간 " + giranm + "분 " + girans + "초"), true);
			pc.sendPackets(new S_SystemMessage("상아탑 : " + ivoryh + "시간 " + ivorym + "분 " + ivorys + "초"), true);
		} catch (Exception e) {
		}
	}

	private void 기감(L1PcInstance gm) {
		try {
			for (L1PcInstance allpc : L1World.getInstance().getAllPlayers()) {
				allpc.setgirantime(0);
			}
			Connection con2 = null;
			PreparedStatement pstm2 = null;
			try {
				con2 = L1DatabaseFactory.getInstance().getConnection();
				pstm2 = con2.prepareStatement("UPDATE characters SET GDGTime = null");
				pstm2.executeUpdate();
				gm.sendPackets(new S_SystemMessage("모든 유저의 기감시간을 초기화 했습니다."), true);
			} catch (Exception e) {

			} finally {
				SQLUtil.close(pstm2);
				SQLUtil.close(con2);
			}
		} catch (Exception e) {
		}
	}

	private static Map<Integer, String> _lastCommands = new HashMap<Integer, String>();

	private void redo(L1PcInstance pc, String arg) {
		try {
			String lastCmd = _lastCommands.get(pc.getId());
			if (arg.isEmpty()) {
				pc.sendPackets(new S_SystemMessage("커맨드 " + lastCmd + " 을(를) 재실행합니다."), true);
				handleCommands(pc, lastCmd);
			} else {
				StringTokenizer token = new StringTokenizer(lastCmd);
				String cmd = token.nextToken() + " " + arg;
				pc.sendPackets(new S_SystemMessage("커맨드 " + cmd + " 을(를) 재실행합니다."), true);
				handleCommands(pc, cmd);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			pc.sendPackets(new S_SystemMessage(".재실행 커맨드에러"), true);
		}
	}

	private void standBy7(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			String 앙 = tokenizer.nextToken();
			String 응 = tokenizer.nextToken();
			L1PcInstance target = L1World.getInstance().getPlayer(pcName);
			if (target != null) {
				if (앙.equalsIgnoreCase("아덴")) {
					if (응.equalsIgnoreCase("켬")) {
						gm.sendPackets(new S_SystemMessage(target.getName() + "님의 아덴 감시를 합니다."), true);
						GameServer.getInstance().addbug(target.getName());
					} else if (응.equalsIgnoreCase("끔")) {
						gm.sendPackets(new S_SystemMessage(target.getName() + "님의 아덴 감시를 해제 합니다."), true);
						GameServer.getInstance().removebug(target.getName());
					}
				} else if (앙.equalsIgnoreCase("방어")) {
					if (응.equalsIgnoreCase("켬")) {
						gm.sendPackets(new S_SystemMessage(target.getName() + "님의 방어구 감시를 합니다."), true);
						GameServer.getInstance().addbug(target.getName());
					} else if (응.equalsIgnoreCase("끔")) {
						gm.sendPackets(new S_SystemMessage(target.getName() + "님의 방어구 감시를 해제 합니다."), true);
						GameServer.getInstance().removebug(target.getName());
					}
				} else {
					gm.sendPackets(new S_SystemMessage(".버그 [케릭명] [아덴/방어] [켬/끔]"), true);
				}
			} else {
				gm.sendPackets(new S_SystemMessage("월드내에 그런케릭터는 없습니다."), true);
			}
		} catch (Exception eee) {
			gm.sendPackets(new S_SystemMessage(".버그 [케릭명] [아덴/방어] [켬/끔]"), true);
		}
	}

	private void standBy8(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			L1PcInstance target = L1World.getInstance().getPlayer(pcName);
			if (target != null) {
				target.getNetConnection().패킷로그 = true;
				gm.sendPackets(new S_SystemMessage(target.getName() + "님의 로그를 기록합니다."), true);
				gm.sendPackets(new S_SystemMessage(target.getNetConnection().getIp() + "로 접속하는 모든 케릭들은 자동 저장됩니다."),
						true);
				GameServer.getInstance().addipl(target.getNetConnection().getIp());
			} else {
				gm.sendPackets(new S_SystemMessage("월드내에 그런케릭터는 없습니다."), true);
			}
		} catch (Exception eee) {
			gm.sendPackets(new S_SystemMessage(".로그 [케릭명]"), true);
		}
	}

	// 1원 많이 깔기
	/*
	 * private void standBy6(L1PcInstance gm, String param){ try{
	 * StringTokenizer tokenizer = new StringTokenizer(param); int id =
	 * Integer.parseInt(tokenizer.nextToken(), 10);
	 * GameServer.getInstance().아덴최저값=id; gm.sendPackets(new S_SystemMessage(
	 * "아덴검사 최저값을 "+id+" 로 변경합니다."), true); }catch (Exception eee){
	 * gm.sendPackets(new S_SystemMessage(".아덴값변경 [0~2000000000]"), true); } }
	 */
	// private void standBy9(L1PcInstance gm){
	// try{
	// gm.sendPackets(new S_SystemMessage("패킷 녹화 시작."));
	// Config.패킷로그 = false;
	// }catch (Exception eee){
	// gm.sendPackets(new S_SystemMessage("명령어를 재 입력 해 주세요."));
	// }
	// }
	private void returnEXP(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			L1PcInstance target = L1World.getInstance().getPlayer(pcName);
			if (target != null) {
				int oldLevel = target.getLevel();
				int needExp = ExpTable.getNeedExpNextLevel(oldLevel);
				int exp = 0;
				if (oldLevel >= 1 && oldLevel < 11) {
					exp = 0;
				} else if (oldLevel >= 11 && oldLevel < 45) {
					exp = (int) (needExp * 0.1);
				} else if (oldLevel == 45) {
					exp = (int) (needExp * 0.09);
				} else if (oldLevel == 46) {
					exp = (int) (needExp * 0.08);
				} else if (oldLevel == 47) {
					exp = (int) (needExp * 0.07);
				} else if (oldLevel == 48) {
					exp = (int) (needExp * 0.06);
				} else if (oldLevel >= 49) {
					exp = (int) (needExp * 0.05);
				}
				target.addExp(+exp);
				target.save();
				target.saveInventory();
			} else {
				gm.sendPackets(new S_SystemMessage("그러한 이름의 캐릭터는 월드내에는 존재하지 않습니다."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".겸치복구 [캐릭터명]을 입력 해주세요."), true);
		}
	}

	private int parseNpcId(String nameId) {
		int npcid = 0;
		try {
			npcid = Integer.parseInt(nameId);
		} catch (NumberFormatException e) {
			npcid = NpcTable.getInstance().findNpcIdByNameWithoutSpace(nameId);
		}
		return npcid;
	}

	private void standBy80(L1PcInstance gm) {
		try {
			gm.sendPackets(new S_SystemMessage("컨피그를 리로드 하였습니다."), true);
			Config.load();
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".컨피그로드 오류"), true);
		}
	}

	private void standBy82(L1PcInstance gm) {
		try {
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.샌드백) {
					L1World.getInstance().removeObject(pc);
					L1World.getInstance().removeVisibleObject(pc);
					List<L1PcInstance> players = L1World.getInstance().getRecognizePlayer(pc);
					if (players.size() > 0) {
						S_RemoveObject s_deleteNewObject = new S_RemoveObject(pc);
						for (L1PcInstance sendbag : players) {
							if (sendbag != null) {
								sendbag.getNearObjects().removeKnownObject(pc);
								// if(!L1Character.distancepc(user, this))
								sendbag.sendPackets(s_deleteNewObject);
							}
						}
					}
				}
			}
			gm.sendPackets(new S_SystemMessage("월드맵의 모든 샌드백을 삭제합니다."), true);
		} catch (Exception e) {
		}
	}

	private void standBy79(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String nameId = tokenizer.nextToken();
			int h = Integer.parseInt(tokenizer.nextToken());
			int m = Integer.parseInt(tokenizer.nextToken());
			int npcid = parseNpcId(nameId);
			L1Npc npc = NpcTable.getInstance().getTemplate(npcid);
			if (npc == null) {
				gm.sendPackets(new S_SystemMessage("해당 NPC가 발견되지 않습니다."), true);
				return;
			}
			gm.sendPackets(new S_SystemMessage(npc.get_name() + " 를(을) " + h + "시간 " + m + "분 동안 스폰합니다."), true);
			if (h != 0) {
				h = ((h * 60) * 60) * 1000;
			}
			if (m != 0) {
				m = (m * 60) * 1000;
			}
			int time = 0;
			time = h + m;
			L1SpawnUtil.spawn(gm, npcid, 0, time, false);

		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".이벤상점 [엔피시번호or이름] [시간] [분]"), true);
		}
	}

	private void standBy82(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String name = tokenizer.nextToken();
			L1PcInstance ck = L1World.getInstance().getPlayer(name);
			if (ck != null) {
				gm.sendPackets(new S_SystemMessage("접속중인 유저와 동일 이름은 불가능."), true);
				return;
			}
			for (int i = 0; i < 100; i++) {
				L1PcInstance pc = new MySqlCharacterStorage().loadCharacter(gm.getName());
				pc.setId(ObjectIdFactory.getInstance().nextId());
				pc.setX(gm.getX());
				pc.setY(gm.getY());
				pc.setMap(gm.getMap());
				pc.setLawful(0);

				pc.getAC().setAc(gm.getAC().getAc());

				pc.setName(name + i);

				pc.setLevel(gm.getLevel());

				pc.getResistance().setBaseMr(gm.getResistance().getMr());
				pc.setTitle("L" + gm.getLevel() + " M" + gm.getResistance().getMr() + " A" + gm.getAC().getAc());
				pc.addBaseMaxHp((short) 32767);
				pc.setCurrentHp(32767);

				pc.샌드백 = true;

				L1World.getInstance().storeObject(pc);
				L1World.getInstance().addVisibleObject(pc);
			}
			gm.sendPackets(new S_SystemMessage("샌드백 스폰!"), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".분신 [이름]"), true);
		}
	}

	private void standBy81(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String name = tokenizer.nextToken();
			L1PcInstance ck = L1World.getInstance().getPlayer(name);
			if (ck != null) {
				gm.sendPackets(new S_SystemMessage("접속중인 유저와 동일 이름은 불가능."), true);
				return;
			}

			L1PcInstance pc = new MySqlCharacterStorage().loadCharacter(gm.getName());
			pc.setId(ObjectIdFactory.getInstance().nextId());
			pc.setX(gm.getX());
			pc.setY(gm.getY());
			pc.setMap(gm.getMap());
			pc.setLawful(0);

			pc.getAC().setAc(gm.getAC().getAc());

			pc.setName(name);

			pc.setLevel(gm.getLevel());

			pc.getResistance().setBaseMr(gm.getResistance().getMr());
			pc.setTitle("L" + gm.getLevel() + " M" + gm.getResistance().getMr() + " A" + gm.getAC().getAc());
			pc.addBaseMaxHp((short) 32767);
			pc.setCurrentHp(32767);

			pc.샌드백 = true;

			L1World.getInstance().storeObject(pc);
			L1World.getInstance().addVisibleObject(pc);

			gm.sendPackets(new S_SystemMessage("샌드백 스폰!"), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".분신 [이름]"), true);
		}
	}

	private void standBy77(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			L1PcInstance target = L1World.getInstance().getPlayer(pcName);
			int objid = 0;
			String acname = null;
			if (target != null) {
				target.sendPackets(new S_Disconnect());
			}
			Connection con = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			try {
				con = L1DatabaseFactory.getInstance().getConnection();
				pstm = con.prepareStatement("SELECT objid, account_name FROM characters WHERE char_name=?");
				pstm.setString(1, pcName);
				rs = pstm.executeQuery();
				while (rs.next()) {
					objid = rs.getInt(1);
					acname = rs.getString(2);
				}
				if (objid == 0) {
					gm.sendPackets(new S_SystemMessage("디비에 해당 유저의 이름이 존재하지 않습니다."), true);

				} else {
					gm.sendPackets(new S_SystemMessage(acname + "계정 " + pcName + "님의 케릭터를 삭제 합니다."), true);
					CharacterTable.getInstance().deleteCharacter(acname, pcName);
					gm.sendPackets(new S_SystemMessage("해당유저를 정상적으로 삭제 하였습니다."), true);
				}
			} catch (SQLException e) {

			} finally {
				SQLUtil.close(rs);
				SQLUtil.close(pstm);
				SQLUtil.close(con);
			}

		} catch (Exception eee) {
			gm.sendPackets(new S_SystemMessage(".케릭삭제 [케릭명]"), true);
		}
	}

	private void iconserch2(L1PcInstance pc, String s) {
		try {
			StringTokenizer st = new StringTokenizer(s);
			int id = Integer.parseInt(st.nextToken());
			int count = Integer.parseInt(st.nextToken());
			for (int i = id; i < count; i++) {
				pc.sendPackets(new S_SystemMessage("번호: " + i));
				pc.sendPackets(new S_PacketBox(S_PacketBox.BUFFICON, i, 1));
			}
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage(".아이 [시작] [끝]"));
		}
	}

	private void accountdel(L1PcInstance gm, String param) {

		try {

			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();

			Connection con = null;
			Connection con2 = null;
			PreparedStatement pstm = null;
			PreparedStatement pstm2 = null;
			Connection con3 = null;
			PreparedStatement pstm3 = null;
			ResultSet find2 = null;
			ResultSet find = null;
			String findcha = null;

			try {
				con = L1DatabaseFactory.getInstance().getConnection();
				pstm = con.prepareStatement("SELECT * FROM characters WHERE char_name=?");
				pstm.setString(1, pcName);
				find = pstm.executeQuery();

				while (find.next()) {
					findcha = find.getString(1);
				}

				if (findcha == null) {
					gm.sendPackets(new S_SystemMessage("DB에 " + pcName + " 케릭명이 존재 하지 않습니다"), true);
				} else {
					con2 = L1DatabaseFactory.getInstance().getConnection();
					pstm2 = con2.prepareStatement("UPDATE accounts SET banned = 0 WHERE login= ?");
					pstm2.setString(1, findcha);
					pstm2.executeUpdate();

					con3 = L1DatabaseFactory.getInstance().getConnection();
					pstm3 = con3.prepareStatement("SELECT * FROM accounts WHERE login=?");
					pstm3.setString(1, findcha);
					find2 = pstm3.executeQuery();

					if (find2.next()) {
						IpTable.getInstance().liftBanIp(find2.getString(5));
					}

					gm.sendPackets(new S_SystemMessage(pcName + " 의 계정압류를 해제 하였습니다"), true);
				}
			} catch (Exception e) {

			} finally {
				SQLUtil.close(find2);
				SQLUtil.close(find);
				SQLUtil.close(pstm3);
				SQLUtil.close(con3);
				SQLUtil.close(pstm2);
				SQLUtil.close(pstm);
				SQLUtil.close(con2);
				SQLUtil.close(con);
			}
		} catch (Exception exception) {
			gm.sendPackets(new S_SystemMessage(".압류해제 케릭명으로 입력해주세요."), true);
		}
	}

	private void packetbox(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int id = Integer.parseInt(st.nextToken(), 10);
			pc.sendPackets(new S_PacketBox(83, id), true);
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage(".패킷박스 [id] 입력"), true);
		}
	}

	private void 채팅(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			String pcName2 = tokenizer.nextToken();

			if (pcName.equalsIgnoreCase("혈맹")) {
				if (pcName2.equalsIgnoreCase("켬")) {
					Config.add혈맹(gm);
					gm.sendPackets(new S_SystemMessage(".혈맹 채팅을 모니터 합니다."), true);
				} else if (pcName2.equalsIgnoreCase("끔")) {
					Config.remove혈맹(gm);
					gm.sendPackets(new S_SystemMessage(".혈맹 채팅 모니터를 중단 합니다."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("파티")) {
				if (pcName2.equalsIgnoreCase("켬")) {
					Config.add파티(gm);
					gm.sendPackets(new S_SystemMessage(".파티 채팅을 모니터 합니다."), true);
				} else if (pcName2.equalsIgnoreCase("끔")) {
					Config.remove파티(gm);
					gm.sendPackets(new S_SystemMessage(".파티 채팅 모니터를 중단 합니다."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("귓말")) {
				if (pcName2.equalsIgnoreCase("켬")) {
					Config.add귓말(gm);
					gm.sendPackets(new S_SystemMessage(".귓말 채팅을 모니터 합니다."), true);
				} else if (pcName2.equalsIgnoreCase("끔")) {
					Config.remove귓말(gm);
					gm.sendPackets(new S_SystemMessage(".귓말 채팅 모니터를 중단 합니다."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("인첸")) {
				if (pcName2.equalsIgnoreCase("켬")) {
					Config.add인첸(gm);
					gm.sendPackets(new S_SystemMessage(".인첸 로그를 모니터 합니다."), true);
				} else if (pcName2.equalsIgnoreCase("끔")) {
					Config.remove인첸(gm);
					gm.sendPackets(new S_SystemMessage(".인첸 로그 모니터를 중단 합니다."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("삭제")) {
				if (pcName2.equalsIgnoreCase("켬")) {
					Config.add삭제(gm);
					gm.sendPackets(new S_SystemMessage(".삭제 로그를 모니터 합니다."), true);
				} else if (pcName2.equalsIgnoreCase("끔")) {
					Config.remove삭제(gm);
					gm.sendPackets(new S_SystemMessage(".삭제 로그 모니터를 중단 합니다."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("접속")) {
				if (pcName2.equalsIgnoreCase("켬")) {
					Config.add접속(gm);
					gm.sendPackets(new S_SystemMessage(".접속 로그를 모니터 합니다."), true);
				} else if (pcName2.equalsIgnoreCase("끔")) {
					Config.remove접속(gm);
					gm.sendPackets(new S_SystemMessage(".접속 로그 모니터를 중단 합니다."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("버그")) {
				if (pcName2.equalsIgnoreCase("켬")) {
					Config.add버그(gm);
					gm.sendPackets(new S_SystemMessage(".버그 로그를 모니터 합니다."), true);
				} else if (pcName2.equalsIgnoreCase("끔")) {
					Config.remove버그(gm);
					gm.sendPackets(new S_SystemMessage(".버그 로그 모니터를 중단 합니다."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("전체")) {
				if (pcName2.equalsIgnoreCase("켬")) {
					Config.add전체(gm);
					gm.sendPackets(new S_SystemMessage(".모든 채팅을 모니터 합니다."), true);
				} else if (pcName2.equalsIgnoreCase("끔")) {
					Config.remove전체(gm);
					gm.sendPackets(new S_SystemMessage(".모든 채팅 모니터를 중단 합니다."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
					return;
				}
			} else {
				gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
				return;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".채팅 [혈맹/파티/귓말/인첸/삭제/접속/버그/전체] [켬/끔]"), true);
		}
	}

	private void autoshop(L1PcInstance gm, String param) {
		if (param.equalsIgnoreCase("켬")) {
			AutoShopManager.getInstance().isAutoShop(true);
			gm.sendPackets(new S_SystemMessage("무인상점 켬"), true);
		} else if (param.equalsIgnoreCase("끔")) {
			AutoShopManager.getInstance().isAutoShop(false);
			gm.sendPackets(new S_SystemMessage("무인상점 끔"), true);
		} else {
			gm.sendPackets(new S_SystemMessage(".무인상점 [켬 or 끔] 입력"), true);
		}
	}

	private void 아놀드상점(L1PcInstance gm, String param) {
		if (param.equalsIgnoreCase("켬")) {
			if (Config.아놀드상점 == null) {
				L1NpcInstance npc = L1SpawnUtil.spawn4(33435, 32804, (short) 4, 0, 100880, 0, 0, 0);
				if (npc != null) {
					Config.아놀드상점 = npc;
				}

				gm.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "아놀드 상점을 스폰 합니다."), true);
			} else {
				gm.sendPackets(new S_SystemMessage("아놀드 상점이 이미 스폰중 입니다."), true);
			}
		} else if (param.equalsIgnoreCase("끔")) {
			if (Config.아놀드상점 != null) {
				Config.아놀드상점.deleteMe();
				Config.아놀드상점 = null;
				gm.sendPackets(new S_SystemMessage("아놀드 상점을 삭제 합니다."), true);
			} else {
				gm.sendPackets(new S_SystemMessage("아놀드 상점을 찾을 수 없습니다."), true);
			}
		} else {
			gm.sendPackets(new S_SystemMessage(".아놀드상점 [켬 or 끔] 입력"), true);
		}
	}

	private void nocall(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();

			L1PcInstance target = null; // q
			target = L1World.getInstance().getPlayer(pcName);
			if (target != null) { // 타겟

				L1Teleport.teleport(target, 33437, 32812, (short) 4, 5, true); // /
																				// 가게될
																				// 지점
																				// (유저가떨어지는지점)
			} else {
				gm.sendPackets(new S_SystemMessage("접속중이지 않는 유저 ID 입니다."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".가라 (보낼케릭터명) 으로 입력해 주세요."), true);
		}
	}

	private void hellcall(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();

			L1PcInstance target = null; // q
			target = L1World.getInstance().getPlayer(pcName);
			if (target != null) { // 타겟

				target.감옥 = true;

				L1Teleport.teleport(target, 32928, 32864, (short) 6202, 5, true); // /
																					// 가게될
																					// 지점
																					// (유저가떨어지는지점)

			} else {
				gm.sendPackets(new S_SystemMessage("접속중이지 않는 유저 ID 입니다."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".감옥 (보낼케릭터명) 으로 입력해 주세요."), true);
		}
	}

	private void allpresent(L1PcInstance gm, String param) {
		try {
			StringTokenizer kwang = new StringTokenizer(param);
			String item = kwang.nextToken();
			if (item.equalsIgnoreCase("깃털")) {
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (!pc.isPrivateShop() && pc.getNetConnection() != null) {
						L1Item tempItem = ItemTable.getInstance().getTemplate(41159);
						if (tempItem != null) {
							PresentGive(tempItem, 41159, 300, 0, pc);
						}
					}
				}
				return;
			} else if (item.equalsIgnoreCase("코마")) {
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (!pc.isPrivateShop() && pc.getNetConnection() != null) {
						L1Item tempItem = ItemTable.getInstance().getTemplate(60217);
						if (tempItem != null) {
							PresentGive(tempItem, 60217, 1, 0, pc);
						}
					}
				}
				return;
			}

			int itemid = Integer.parseInt(item, 10);
			int enchant = Integer.parseInt(kwang.nextToken(), 10);
			int count = Integer.parseInt(kwang.nextToken(), 10);
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (!pc.isPrivateShop() && pc.getNetConnection() != null) {
					L1Item tempItem = ItemTable.getInstance().getTemplate(itemid);
					if (tempItem != null) {
						PresentGive(tempItem, itemid, count, enchant, pc);
						/*
						 * if (pc.isGhost() == false) { L1ItemInstance item =
						 * ItemTable.getInstance().createItem(itemid);
						 * item.setCount(count); item.setEnchantLevel(enchant);
						 * if (item != null) { if
						 * (pc.getInventory().checkAddItem(item, count) ==
						 * L1Inventory.OK) { pc.getInventory().storeItem(item);
						 * } }
						 * 
						 * pc.sendPackets(new S_SystemMessage(
						 * "운영자님께서 전체유저에게 선물로[ "+ item.getName()
						 * +(item.getCount() > 0 ? (" ("+item.getCount()+")") :
						 * "")+" ]를 주었습니다."), true);
						 */
					}
				}
			}
		} catch (Exception exception) {
			gm.sendPackets(new S_SystemMessage(".전체선물 아이템ID 인첸트수 아이템수로 입력해 주세요."), true);
		}
	}

	private void PresentGive(L1Item temp, int itemid, int count, int enchant, L1PcInstance target) {
		SupplementaryService pwh = WarehouseManager.getInstance().getSupplementaryService(target.getAccountName());
		if (temp.isStackable()) {
			L1ItemInstance item = ItemTable.getInstance().createItem(itemid);
			item.setIdentified(true);
			item.setEnchantLevel(0);
			item.setCount(count);

			pwh.storeTradeItem(item);
			if (target != null) {
				// target.sendPackets(new S_ServerMessage(403, // %0를 손에 넣었습니다.
				// item.getLogName()));
				target.sendPackets(new S_SystemMessage("운영자 선물 : 당신에게 " + item.getName()
						+ (item.getCount() > 0 ? (" (" + item.getCount() + ")") : "") + " 를 주었습니다."), true);
			}
		} else {
			L1ItemInstance item = null;
			int createCount;
			for (createCount = 0; createCount < count; createCount++) {
				item = ItemTable.getInstance().createItem(itemid);
				item.setIdentified(true);
				item.setEnchantLevel(enchant);
				pwh.storeTradeItem(item);
			}
			if (createCount > 0) {
				if (target != null) {
					// target.sendPackets(new S_ServerMessage(403, // %0를 손에
					// 넣었습니다.
					// item.getLogName()));
					target.sendPackets(new S_SystemMessage("운영자 선물 : 당신에게 " + item.getName()
							+ (item.getCount() > 0 ? (" (" + item.getCount() + ")") : "") + " 를 주었습니다."), true);
				}
			}
		}
	}

	// 셋팅 경험치 아이템 아데나 배율
	private void SetRates(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String exp = tok.nextToken();
			String item = tok.nextToken();
			String adena = tok.nextToken();
			if (NumberChk(exp) && NumberChk(item) && NumberChk(adena)) {
				Config.RATE_XP = ConvertToDouble(exp);
				Config.RATE_DROP_ITEMS = ConvertToDouble(item);
				Config.RATE_DROP_ADENA = ConvertToDouble(adena);
				gm.sendPackets(new S_SystemMessage("[경험치 = " + exp + " 배]"), true);
				gm.sendPackets(new S_SystemMessage("[아이템 = " + item + " 배]"), true);
				gm.sendPackets(new S_SystemMessage("[아데나 = " + adena + " 배]로 셋팅 되었습니다"), true);
			} else {// 입력한 값이 숫자가 아닐경우
				gm.sendPackets(new S_SystemMessage("잘못된 값을 입력하셨습니다. 모두 숫자로 입력해주세요"), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".배율설정 [경험치][아이템][아데나]를 입력 해주세요."), true);
		}
	}

	private void SetEnchantRates(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String weapon = tok.nextToken();
			String armor = tok.nextToken();
			String accessory = tok.nextToken();
			if (NumberChk(weapon) && NumberChk(armor) && NumberChk(accessory)) {
				Config.ENCHANT_CHANCE_WEAPON = Integer.parseInt(weapon);
				Config.ENCHANT_CHANCE_ARMOR = Integer.parseInt(armor);
				gm.sendPackets(new S_SystemMessage("[무기 인챈율 = " + weapon + " 배]"), true);
				gm.sendPackets(new S_SystemMessage("[방어구 인챈율 = " + armor + " 배]"), true);
			} else {// 입력한 값이 숫자가 아닐경우
				gm.sendPackets(new S_SystemMessage("잘못된 값을 입력하셨습니다. 모두 숫자로 입력해주세요"), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".인챈설정 [무기인챈율][방어구인챈율][장신구인챈율]를 입력 해주세요."), true);
		}
	}

	// 입력값이 숫자인지 체크
	private boolean NumberChk(String str) {
		char ch;

		if (str.equals(""))
			return false;

		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch < 48 || ch > 59) {
				return false;
			}
		}
		return true;
	}

	// 입력받은 값을 더블타입으로 변환
	private double ConvertToDouble(String param) {
		Float Temp = Float.parseFloat(param);
		double doubleValue = (double) Temp;
		return doubleValue;
	}

	private void CheckAllRates(L1PcInstance gm) {
		gm.sendPackets(new S_SystemMessage("***** 현재 설정 배율 *****"), true);
		gm.sendPackets(new S_SystemMessage("[경험치]: " + Config.RATE_XP + "배"), true);
		gm.sendPackets(new S_SystemMessage("[아이템]: " + Config.RATE_DROP_ITEMS + "배"), true);
		gm.sendPackets(new S_SystemMessage("[아데나]: " + Config.RATE_DROP_ADENA + "배"), true);
		gm.sendPackets(new S_SystemMessage("[무기인챈율]: " + Config.ENCHANT_CHANCE_WEAPON + "배"), true);
		gm.sendPackets(new S_SystemMessage("[방어구인챈율]: " + Config.ENCHANT_CHANCE_ARMOR + "배"), true);
	}

	private void 인벤(L1PcInstance gm, String param) {
		try {
			StringTokenizer stringtokenizer = new StringTokenizer(param);
			String s = stringtokenizer.nextToken();

			L1PcInstance pc = L1World.getInstance().getPlayer(s);
			if (pc == null) {
				gm.sendPackets(new S_SystemMessage("접속 중인 케릭이 아닙니다."), true);
				return;
			}

			List<L1ItemInstance> items = gm.getInventory().getItems();
			for (L1ItemInstance item : items) {
				gm.sendPackets(new S_DeleteInventoryItem(item), true);
			}
			List<L1ItemInstance> tempitem = pc.getInventory().getItems();
			gm.sendPackets(new S_TempInv(tempitem), true);
		} catch (Exception exception21) {
			gm.sendPackets(new S_SystemMessage(".인벤 캐릭터명"), true);
		}
	}

	private void chainfo(L1PcInstance gm, String param) {
		try {
			StringTokenizer stringtokenizer = new StringTokenizer(param);
			String s = stringtokenizer.nextToken();
			L1PcInstance pc = L1World.getInstance().getPlayer(s);
			if (pc == null) {
				gm.sendPackets(new S_SystemMessage("접속 중인 케릭이 아닙니다."), true);
			}
			gm.sendPackets(new S_Chainfo(pc), true);
		} catch (Exception exception21) {
			gm.sendPackets(new S_SystemMessage(".조사 캐릭터명"), true);
		}
	}

	private void chatx(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(pcName);

			if (target != null) {
				target.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.STATUS_CHAT_PROHIBITED);
				target.sendPackets(new S_SkillIconGFX(36, 0), true);
				target.sendPackets(new S_ServerMessage(288), true);
				gm.sendPackets(new S_SystemMessage("해당캐릭의 채금을 해제 했습니다."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".채금풀기 캐릭터명 이라고 입력해 주세요."), true);
		}
	}

	private void StartWar(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String clan_name1 = tok.nextToken();
			String clan_name2 = tok.nextToken();

			L1Clan clan1 = L1World.getInstance().getClan(clan_name1);
			L1Clan clan2 = L1World.getInstance().getClan(clan_name2);

			if (clan1 == null) {
				gm.sendPackets(new S_SystemMessage(clan_name1 + "혈맹이 존재하지 않습니다."), true);
				return;
			}

			if (clan2 == null) {
				gm.sendPackets(new S_SystemMessage(clan_name2 + "혈맹이 존재하지 않습니다."), true);
				return;
			}

			for (L1War war : L1World.getInstance().getWarList()) {
				if (war.CheckClanInSameWar(clan_name1, clan_name2) == true) {
					gm.sendPackets(new S_SystemMessage("[" + clan_name1 + "]혈맹과 [" + clan_name2 + "]혈맹은 현재 전쟁 중 입니다."),
							true);
					return;
				}
			}

			L1War war = new L1War();
			war.handleCommands(2, clan_name1, clan_name2); // 모의전 개시
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				pc.sendPackets(new S_SystemMessage("[" + clan_name1 + "]혈맹과 [" + clan_name2 + "]혈맹의 전쟁이 시작 되었습니다."),
						true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".혈전시작 혈맹이름 혈맹이름"), true);
		}
	}

	private void StopWar(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String clan_name1 = tok.nextToken();
			String clan_name2 = tok.nextToken();

			L1Clan clan1 = L1World.getInstance().getClan(clan_name1);
			L1Clan clan2 = L1World.getInstance().getClan(clan_name2);

			if (clan1 == null) {
				gm.sendPackets(new S_SystemMessage(clan_name1 + "혈맹이 존재하지 않습니다."), true);
				return;
			}

			if (clan2 == null) {
				gm.sendPackets(new S_SystemMessage(clan_name2 + "혈맹이 존재하지 않습니다."), true);
				return;
			}

			for (L1War war : L1World.getInstance().getWarList()) {
				if (war.CheckClanInSameWar(clan_name1, clan_name2) == true) {
					war.CeaseWar(clan_name1, clan_name2);
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						pc.sendPackets(
								new S_SystemMessage("[" + clan_name1 + "]혈맹과 [" + clan_name2 + "]혈맹의 전쟁이 종료 되었습니다."),
								true);
					}
					return;
				}
			}
			gm.sendPackets(new S_SystemMessage("[" + clan_name1 + "]혈맹과 [" + clan_name2 + "]혈맹은 현재 전쟁중이지 않습니다."), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".혈전종료 혈맹이름 혈맹이름"), true);
		}
	}

	private void searchDatabase(L1PcInstance gm, String param) { // 검색기능추가

		try {
			StringTokenizer tok = new StringTokenizer(param);
			int type = Integer.parseInt(tok.nextToken());
			String name = tok.nextToken();

			searchObject(gm, type, "%" + name + "%");

		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".검색 [0~5] [이름]을 입력 해주세요."), true);
			gm.sendPackets(new S_SystemMessage("0=잡템, 1=무기, 2=갑옷, 3=npc"), true);
		}
	}

	private void serversave(L1PcInstance pc) {// 검색후 추가
		Saveserver();// 서버세이브 메소드 선언
		pc.sendPackets(new S_SystemMessage("서버저장이 완료되었습니다."), true);// 지엠에게 알려주고
	}

	/** 서버저장 **/
	private void Saveserver() {
		/** 전체플레이어를 호출 **/
		for (L1PcInstance player : L1World.getInstance().getAllPlayers()) {
			try {
				/** 피씨저장해주고 **/
				player.save();
				/** 인벤도 저장하고 **/
				player.saveInventory();
			} catch (Exception ex) {
				/** 예외 인벤저장 **/
				player.saveInventory();
			}
		}
	}

	/** 서버저장 **/

	private static String encodePassword(String rawPassword)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte buf[] = rawPassword.getBytes("UTF-8");
		buf = MessageDigest.getInstance("SHA").digest(buf);

		return Base64.encodeBytes(buf);
	}

	private void AddAccount(L1PcInstance gm, String account, String passwd, String Ip, String Host) {
		try {
			String login = null;
			String password = null;
			java.sql.Connection con = null;
			PreparedStatement statement = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;

			try {

				con = L1DatabaseFactory.getInstance().getConnection();
				password = encodePassword(passwd);

				statement = con.prepareStatement("select * from accounts where login Like '" + account + "'");
				rs = statement.executeQuery();

				if (rs.next())
					login = rs.getString(1);
				if (login != null) {
					gm.sendPackets(new S_SystemMessage("이미 계정이 있습니다."), true);
					return;
				} else {

					String sqlstr = "INSERT INTO accounts SET login=?,password=?,lastactive=?,access_level=?,ip=?,host=?,banned=?,charslot=?,gamepassword=?,notice=?";
					pstm = con.prepareStatement(sqlstr);
					pstm.setString(1, account);
					pstm.setString(2, password);
					pstm.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
					pstm.setInt(4, 0);
					pstm.setString(5, Ip);
					pstm.setString(6, Host);
					pstm.setInt(7, 0);
					pstm.setInt(8, 6);
					pstm.setInt(9, 0);
					pstm.setInt(10, 0);
					pstm.executeUpdate();
					gm.sendPackets(new S_SystemMessage("계정 추가가 완료되었습니다."), true);
				}

			} catch (Exception e) {

			} finally {
				SQLUtil.close(rs);
				SQLUtil.close(pstm);
				SQLUtil.close(statement);
				SQLUtil.close(con);
			}
		} catch (Exception e) {
		}
	}

	private void searchObject(L1PcInstance gm, int type, String name) {
		try {
			String str1 = null;
			String str2 = null;
			int count = 0;
			java.sql.Connection con = null;
			con = L1DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = null;
			ResultSet rs = null;
			try {
				switch (type) {
				case 0: // etcitem
					statement = con
							.prepareStatement("select item_id, name from etcitem where name Like '" + name + "'");
					break;
				case 1: // weapon
					statement = con.prepareStatement("select item_id, name from weapon where name Like '" + name + "'");
					break;
				case 2: // armor
					statement = con.prepareStatement("select item_id, name from armor where name Like '" + name + "'");
					break;
				case 3: // npc
					statement = con.prepareStatement("select npcid, name from npc where name Like '" + name + "'");
					break;
				case 4: // polymorphs
					statement = con
							.prepareStatement("select polyid, name from polymorphs where name Like '" + name + "'");
					break;
				default:
					break;
				}
				rs = statement.executeQuery();
				while (rs.next()) {
					str1 = rs.getString(1);
					str2 = rs.getString(2);
					gm.sendPackets(new S_SystemMessage("id : [" + str1 + "], name : [" + str2 + "]"), true);
					count++;
				}

				gm.sendPackets(new S_SystemMessage("총 [" + count + "]개의 데이터가 검색되었습니다."), true);
			} catch (Exception e) {

			} finally {
				SQLUtil.close(rs);
				SQLUtil.close(statement);
				SQLUtil.close(con);
			}
		} catch (Exception e) {
		}
	}

	private static boolean isDisitAlpha(String str) {
		boolean check = true;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)) // 숫자가 아니라면
					&& Character.isLetterOrDigit(str.charAt(i)) // 특수문자라면
					&& !Character.isUpperCase(str.charAt(i)) // 대문자가 아니라면
					&& !Character.isLowerCase(str.charAt(i))) { // 소문자가 아니라면
				check = false;
				break;
			}
		}
		return check;
	}

	private void addaccount(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String user = tok.nextToken();
			String passwd = tok.nextToken();

			if (user.length() < 4) {
				gm.sendPackets(new S_SystemMessage("입력하신 계정명의 자릿수가 너무 짧습니다."), true);
				gm.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."), true);
				return;
			}
			if (passwd.length() < 4) {
				gm.sendPackets(new S_SystemMessage("입력하신 암호의 자릿수가 너무 짧습니다."), true);
				gm.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."), true);
				return;
			}

			if (passwd.length() > 12) {
				gm.sendPackets(new S_SystemMessage("입력하신 암호의 자릿수가 너무 깁니다."), true);
				gm.sendPackets(new S_SystemMessage("최대 12자 이하로 입력해 주십시오."), true);
				return;
			}

			if (isDisitAlpha(passwd) == false) {
				gm.sendPackets(new S_SystemMessage("암호에 허용되지 않는 문자가 포함 되어 있습니다."), true);
				return;
			}
			AddAccount(gm, user, passwd, "127.0.0.1", "127.0.0.1");
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".계정추가 [계정명] [암호] 입력"), true);
		}
	}

	private void changepassword(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String user = tok.nextToken();
			String oldpasswd = tok.nextToken();
			String newpasswd = tok.nextToken();

			if (user.length() < 4) {
				gm.sendPackets(new S_SystemMessage("입력하신 계정명의 자릿수가 너무 짧습니다."), true);
				gm.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."), true);
				return;
			}
			if (newpasswd.length() < 4) {
				gm.sendPackets(new S_SystemMessage("입력하신 암호의 자릿수가 너무 짧습니다."), true);
				gm.sendPackets(new S_SystemMessage("최소 4자 이상 입력해 주십시오."), true);
				return;
			}
			if (newpasswd.length() > 12) {
				gm.sendPackets(new S_SystemMessage("입력하신 암호의 자릿수가 너무 깁니다."), true);
				gm.sendPackets(new S_SystemMessage("최대 12자 이하로 입력해 주십시오."), true);
				return;
			}

			if (isDisitAlpha(newpasswd) == false) {
				gm.sendPackets(new S_SystemMessage("암호에 허용되지 않는 문자가 포함 되어 있습니다."), true);
				return;
			}
			chkpassword(gm, user, oldpasswd, newpasswd);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".비번변경 [계정] [현재비번] [바꿀비번] 입력"), true);
		}
	}

	private void chkpassword(L1PcInstance gm, String account, String oldpassword, String newpassword) {
		try {
			String password = null;
			java.sql.Connection con = null;
			con = L1DatabaseFactory.getInstance().getConnection();
			PreparedStatement statement = null;
			PreparedStatement pstm = null;

			statement = con.prepareStatement("select password from accounts where login='" + account + "'");
			ResultSet rs = statement.executeQuery();

			try {

				if (rs.next())
					password = rs.getString(1);
				if (password == null) {
					gm.sendPackets(new S_SystemMessage("입력하신 계정은 서버내에 존재 하지 않습니다."), true);
					return;
				}

				if (!isPasswordTrue(password, oldpassword)) {
					// System.out.println("현재 비번 : " +
					// oldpassword+" - 체크 비번 : "+password);
					gm.sendPackets(new S_SystemMessage("기존 계정명의 비밀번호가 일치하지 않습니다. "), true);
					gm.sendPackets(new S_SystemMessage("다시 확인하시고 실행해 주세요."), true);
					return;
				} else {
					String sqlstr = "UPDATE accounts SET password=password(?) WHERE login=?";
					pstm = con.prepareStatement(sqlstr);
					pstm.setString(1, newpassword);
					pstm.setString(2, account);
					pstm.executeUpdate();
					gm.sendPackets(new S_SystemMessage("계정명 : " + account + " / 바뀐비밀번호 : " + newpassword), true);
					gm.sendPackets(new S_SystemMessage("비밀번호 변경이 정상적으로 완료되었습니다."), true);
				}
			} catch (Exception e) {

			} finally {
				SQLUtil.close(rs);
				SQLUtil.close(pstm);
				SQLUtil.close(statement);
				SQLUtil.close(con);
			}
		} catch (Exception e) {
		}
	}

	// 패스워드 맞는지 여부 리턴
	public static boolean isPasswordTrue(String Password, String oldPassword) {
		String _rtnPwd = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT password(?) as pwd ");

			pstm.setString(1, oldPassword);
			rs = pstm.executeQuery();
			if (rs.next()) {
				_rtnPwd = rs.getString("pwd");
			}
			if (_rtnPwd.equals(Password)) { // 동일하다면
				return true;
			} else
				return false;
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	String tampage = "cd 01 0a 1c 08 0e 10 b8 f9 1a 18 00 20 00 2a "
			+ "0a c6 c4 c7 c1 b8 ae bf c2 c5 b3 30 30 38 03 40 " + "01 0a 1d 08 0e 10 ba f9 1a 18 00 20 00 2a 0b 4c "
			+ "69 76 65 6f 72 44 65 61 74 68 30 2f 38 02 40 01 " + "0a 1c 08 0e 10 c9 a5 ec 06 18 00 20 00 2a 09 44 "
			+ "64 64 64 64 64 64 64 33 30 10 38 07 40 00 0a 1e " + "08 0e 10 bf f9 1a 18 00 20 00 2a 0c b8 b6 c0 bd "
			+ "b8 b8 ba f1 b4 dc b0 e1 30 0d 38 01 40 00 0a 1a " + "08 0e 10 bb f9 1a 18 00 20 00 2a 08 bb f3 b4 eb "
			+ "c0 fb c0 ce 30 0d 38 02 40 00 0a 19 08 0e 10 a6 " + "e1 49 18 00 20 00 2a 07 59 6f 75 72 6c 69 70 30 "
			+ "0b 38 00 40 00 0a 1f 08 0e 10 ad 87 81 07 18 00 " + "20 00 2a 0c 41 61 77 65 66 69 6f 70 61 77 65 6a "
			+ "30 01 38 07 40 00 10 03 18 00 20 00 00 00";

	private void 화면스폰(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int id = Integer.parseInt(st.nextToken(), 10);

			int rnd = 0;
			int rnd2 = 0;
			for (int i = 0; i < id; i++) {
				rnd = _random.nextInt(30);
				rnd2 = _random.nextInt(30);
				L1EffectSpawn.getInstance().spawnEffect(91164, 2000, pc.getX() - 15 + rnd, pc.getY() - 15 + rnd2,
						(short) pc.getMapId());
			}
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage(".옵 [숫자] 입력"), true);
		}
	}

	private void 멘트(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int id = Integer.parseInt(st.nextToken(), 10);

			pc.sendPackets(new S_ServerMessage(id, null), true);
		} catch (Exception exception) {
			exception.printStackTrace();
			pc.sendPackets(new S_SystemMessage(".옵 [숫자] 입력"), true);
		}
	}

	private void 메세지(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int type = Integer.parseInt(st.nextToken(), 10);
			String msg = st.nextToken();

			pc.sendPackets(new S_ServerMessage(type, msg), true);
		} catch (Exception exception) {
			exception.printStackTrace();
			pc.sendPackets(new S_SystemMessage(".옵 [숫자] 입력"), true);
		}
	}

	private void 옵코드(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int type = Integer.parseInt(st.nextToken(), 10);
			Config.test222 = type;
		} catch (Exception exception) {
			exception.printStackTrace();
			pc.sendPackets(new S_SystemMessage(".옵 [숫자] 입력"), true);
		}
	}

	public static int 텔옵 = 0;

	private void CodeTest(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int codetest = Integer.parseInt(st.nextToken(), 10);
			텔옵 = codetest;
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage(".코드 [숫자] 입력"), true);
		}
	}

	private void Clear(L1PcInstance gm) {
		for (L1Object obj : L1World.getInstance().getVisibleObjects(gm, 15)) { // 10
																				// 범위
																				// 내에
																				// 오브젝트를
																				// 찾아서
			if (obj instanceof L1MonsterInstance) { // 몬스터라면
				L1MonsterInstance npc = (L1MonsterInstance) obj;
				// npc.testonNpcAI(gm);
				npc.receiveDamage(gm, 50000); // 데미지

				/*
				 * npc.die(gm); npc.die(gm); npc.die(gm); npc.die(gm);
				 * npc.die(gm);
				 */

				gm.sendPackets(new S_SkillSound(obj.getId(), 1815), true);
				Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId(), 1815), true);
			} else if (obj instanceof L1PcInstance) { // pc라면
				L1PcInstance player = (L1PcInstance) obj;
				if (!(obj instanceof L1RobotInstance)) {
					player.receiveDamage(player, 0, false); // 데미지
				}
				gm.sendPackets(new S_SkillSound(obj.getId(), 1815), true);
				Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId(), 1815), true);
			}
		}
	}

	private void castleWarExit(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String name = tok.nextToken();
			WarTimeController.getInstance().setWarExitTime(gm, name);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".공성종료 [성이름두글자]"), true);
		}
	}

	private void castleWarStart(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String name = tok.nextToken();
			int minute = Integer.parseInt(tok.nextToken());

			Calendar cal = (Calendar) Calendar.getInstance().clone();
			if (minute != 0)
				cal.add(Calendar.MINUTE, minute);

			CastleTable.getInstance().updateWarTime(name, cal);
			WarTimeController.getInstance().setWarStartTime(name, cal);

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			gm.sendPackets(
					new S_SystemMessage(String.format(".공성시간이 %s 으로 변경 되었습니다.", formatter.format(cal.getTime()))),
					true);
			gm.sendPackets(new S_SystemMessage(param + "분 뒤 공성이 시작합니다."), true);
			formatter = null;
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".공성시작 [성이름두글자] [분]"), true);
		}
	}

	private void 봇(L1PcInstance gm, String param) {
		StringTokenizer tokenizer = new StringTokenizer(param);
		try {
			String type = tokenizer.nextToken();
			if (type.equalsIgnoreCase("종료")) {
				try {
					String 이름 = tokenizer.nextToken();
					if (이름.equalsIgnoreCase("전체")) {
						int _cnt = 0;
						restartBot = false;
						clanBot = false;
						fishBot = false;
						bugbearBot = false;
						huntBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							_cnt++;
							L1World world = L1World.getInstance();
							if (bot.사냥봇) {
								if (bot.사냥_종료)
									continue;
								else {
									bot.종료();
									continue;
								}
							}
							bot._스레드종료 = true;
							if (!bot.리스봇 && !bot.낚시봇) {
								for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(bot)) {
									pc.sendPackets(new S_RemoveObject(bot), true);
									pc.getNearObjects().removeKnownObject(bot);
								}
								world.removeVisibleObject(bot);
								world.removeObject(bot);
								bot.getNearObjects().removeAllKnownObjects();
							}
							bot.stopHalloweenRegeneration();
							bot.stopPapuBlessing();
							bot.stopLindBlessing();
							bot.stopHalloweenArmorBlessing();
							bot.stopAHRegeneration();
							bot.stopHpRegenerationByDoll();
							bot.stopMpRegenerationByDoll();
							bot.stopSHRegeneration();
							bot.stopMpDecreaseByScales();
							bot.stopEtcMonitor();
							// if(!bot.리스봇 && !bot.낚시봇)
							// bot.setDead(true);
							if (bot.getClanid() != 0) {
								bot.getClan().removeOnlineClanMember(bot.getName());
							}
							Robot.Doll_Delete(bot);
						}
						gm.sendPackets(new S_SystemMessage(_cnt + "마리의 로봇을 종료 시켰습니다."), true);
					} else if (이름.equalsIgnoreCase("사냥")) {
						int _cnt = 0;
						huntBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.사냥봇 || bot.사냥_종료)
								continue;
							_cnt++;
							bot.종료();
						}
						gm.sendPackets(new S_SystemMessage(_cnt + "마리의 로봇을 종료 시켰습니다."), true);
					} else if (이름.equalsIgnoreCase("리스")) {
						int _cnt = 0;
						restartBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.리스봇)
								continue;
							_cnt++;
							bot._스레드종료 = true;
							bot.getNearObjects().removeAllKnownObjects();
							bot.stopHalloweenRegeneration();
							bot.stopPapuBlessing();
							bot.stopLindBlessing();
							bot.stopHalloweenArmorBlessing();
							bot.stopAHRegeneration();
							bot.stopHpRegenerationByDoll();
							bot.stopMpRegenerationByDoll();
							bot.stopSHRegeneration();
							bot.stopMpDecreaseByScales();
							bot.stopEtcMonitor();
							Robot.Doll_Delete(bot);
						}
						gm.sendPackets(new S_SystemMessage(_cnt + "마리의 로봇을 종료 시켰습니다."), true);
					} else if (이름.equalsIgnoreCase("버경")) {
						int _cnt = 0;
						bugbearBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.버경봇)
								continue;
							_cnt++;
							bot._스레드종료 = true;
							bot.getNearObjects().removeAllKnownObjects();
							bot.stopHalloweenRegeneration();
							bot.stopPapuBlessing();
							bot.stopLindBlessing();
							bot.stopHalloweenArmorBlessing();
							bot.stopAHRegeneration();
							bot.stopHpRegenerationByDoll();
							bot.stopMpRegenerationByDoll();
							bot.stopSHRegeneration();
							bot.stopMpDecreaseByScales();
							bot.stopEtcMonitor();
							bot.버경봇_타입 = 0;
							Robot.Doll_Delete(bot);
						}
						gm.sendPackets(new S_SystemMessage(_cnt + "마리의 로봇을 종료 시켰습니다."), true);
					} else if (이름.equalsIgnoreCase("낚시")) {
						int _cnt = 0;
						fishBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.낚시봇)
								continue;
							_cnt++;
							bot._스레드종료 = true;
							bot.getNearObjects().removeAllKnownObjects();
							bot.stopHalloweenRegeneration();
							bot.stopPapuBlessing();
							bot.stopHalloweenArmorBlessing();
							bot.stopLindBlessing();
							bot.stopAHRegeneration();
							bot.stopHpRegenerationByDoll();
							bot.stopMpRegenerationByDoll();
							bot.stopSHRegeneration();
							bot.stopMpDecreaseByScales();
							bot.stopEtcMonitor();
							Robot.Doll_Delete(bot);
						}
						gm.sendPackets(new S_SystemMessage(_cnt + "마리의 로봇을 종료 시켰습니다."), true);
					} else if (이름.equalsIgnoreCase("군주")) {
						int _cnt = 0;
						clanBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.가입군주)
								continue;
							_cnt++;
							L1World world = L1World.getInstance();
							bot._스레드종료 = true;
							for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(bot)) {
								pc.sendPackets(new S_RemoveObject(bot), true);
								pc.getNearObjects().removeKnownObject(bot);
							}
							world.removeVisibleObject(bot);
							world.removeObject(bot);
							bot.stopHalloweenRegeneration();
							bot.stopPapuBlessing();
							bot.stopLindBlessing();
							bot.stopHalloweenArmorBlessing();
							bot.stopAHRegeneration();
							bot.stopHpRegenerationByDoll();
							bot.stopMpRegenerationByDoll();
							bot.stopSHRegeneration();
							bot.stopMpDecreaseByScales();
							bot.stopEtcMonitor();
							if (bot.getClanid() != 0) {
								bot.getClan().removeOnlineClanMember(bot.getName());
							}
							Robot.Doll_Delete(bot);
						}
						gm.sendPackets(new S_SystemMessage(_cnt + "마리의 로봇을 종료 시켰습니다."), true);
					} else {
						L1PcInstance 로봇 = L1World.getInstance().getPlayer(이름);
						if (로봇 != null) {
							if (로봇 instanceof L1RobotInstance) {
								L1World world = L1World.getInstance();
								if (((L1RobotInstance) 로봇).사냥봇) {
									if (((L1RobotInstance) 로봇).사냥_종료)
										return;
									else {
										Robot_Hunt.getInstance().delay_spawn(((L1RobotInstance) 로봇).사냥봇_위치, 60000);
										((L1RobotInstance) 로봇).종료();
										return;
									}
								}
								((L1RobotInstance) 로봇)._스레드종료 = true;
								if (!((L1RobotInstance) 로봇).리스봇 && !((L1RobotInstance) 로봇).낚시봇) {
									for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(로봇)) {
										pc.sendPackets(new S_RemoveObject(로봇), true);
										pc.getNearObjects().removeKnownObject(로봇);
									}
									world.removeVisibleObject(로봇);
									world.removeObject(로봇);
									로봇.getNearObjects().removeAllKnownObjects();
								}
								로봇.stopHalloweenRegeneration();
								로봇.stopPapuBlessing();
								로봇.stopLindBlessing();
								로봇.stopHalloweenArmorBlessing();
								로봇.stopAHRegeneration();
								로봇.stopHpRegenerationByDoll();
								로봇.stopMpRegenerationByDoll();
								로봇.stopSHRegeneration();
								로봇.stopMpDecreaseByScales();
								로봇.stopEtcMonitor();
								((L1RobotInstance) 로봇).버경봇_타입 = 0;
								((L1RobotInstance) 로봇).loc = null;
								if (로봇.getClanid() != 0) {
									로봇.getClan().removeOnlineClanMember(로봇.getName());
								}
								Robot.Doll_Delete((L1RobotInstance) 로봇);
								gm.sendPackets(new S_SystemMessage(이름 + "로봇을 종료 시킵니다."), true);
							} else {
								gm.sendPackets(new S_SystemMessage("로봇이 아닙니다."), true);
							}
						} else {
							gm.sendPackets(new S_SystemMessage("접속중인 로봇의 이름이 아닙니다."), true);
						}

					}
				} catch (Exception e) {
					gm.sendPackets(new S_SystemMessage("봇 종료 이름/리스/전체/군주"), true);
				}
			} else if (type.equalsIgnoreCase("가입")) {
				try {
					String 혈이름 = tokenizer.nextToken();
					String 이름 = tokenizer.nextToken();
					String 호칭 = tokenizer.nextToken();
					L1Clan 혈맹 = L1World.getInstance().getClan(혈이름);
					if (혈맹 == null) {
						gm.sendPackets(new S_SystemMessage(혈이름 + "혈맹이 존재하지 않습니다."), true);
						return;
					}
					L1PcInstance 로봇 = L1World.getInstance().getPlayer(이름);
					if (로봇 != null) {
						if (로봇 instanceof L1RobotInstance) {
							L1RobotInstance rob = (L1RobotInstance) 로봇;
							rob.updateclan(혈이름, 혈맹.getClanId(), 호칭, true);
							rob.setClanid(혈맹.getClanId());
							rob.setClanname(혈이름);
							rob.setClanRank(2);
							rob.setTitle(호칭);
							혈맹.addClanMember(rob.getName(), rob.getClanRank(), rob.getLevel(), rob.getType(),
									rob.getMemo(), rob.getOnlineStatus(), rob);
							Broadcaster.broadcastPacket(rob, new S_CharTitle(rob.getId(), 호칭), true);
							Broadcaster.broadcastPacket(rob, new S_Emblem(rob.getClanid()), true);
						} else {
							gm.sendPackets(new S_SystemMessage("로봇이 아닙니다."), true);
							return;
						}
					} else {
						gm.sendPackets(new S_SystemMessage("접속중인 로봇의 이름이 아닙니다."), true);
						return;
					}
				} catch (Exception e) {
					gm.sendPackets(new S_SystemMessage(".봇 가입 [혈이름] [이름] [호칭]"), true);
				}
			} else if (type.equalsIgnoreCase("추방")) {
				try {
					String 이름 = tokenizer.nextToken();
					L1PcInstance 로봇 = L1World.getInstance().getPlayer(이름);
					if (로봇 != null) {
						if (로봇 instanceof L1RobotInstance) {
							L1RobotInstance rob = (L1RobotInstance) 로봇;
							rob.updateclan("", 0, "", false);
							rob.setClanid(0);
							rob.setClanname("");
							rob.setTitle("");
							rob.setClanRank(0);
							Broadcaster.broadcastPacket(rob, new S_CharTitle(rob.getId(), ""), true);
							Broadcaster.broadcastPacket(rob, new S_Emblem(0), true);
						} else {
							gm.sendPackets(new S_SystemMessage("로봇이 아닙니다."), true);
							return;
						}
					} else {
						gm.sendPackets(new S_SystemMessage("접속중인 로봇의 이름이 아닙니다."), true);
						return;
					}
				} catch (Exception e) {
					gm.sendPackets(new S_SystemMessage(".봇 추방 [이름]"), true);
				}
			} else if (type.equalsIgnoreCase("압류")) {
				String 이름 = tokenizer.nextToken();
				L1PcInstance 로봇 = L1World.getInstance().getPlayer(이름);
				if (로봇 != null) {
					if (로봇 instanceof L1RobotInstance) {
						L1World world = L1World.getInstance();
						((L1RobotInstance) 로봇)._스레드종료 = true;
						for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(로봇)) {
							pc.sendPackets(new S_RemoveObject(로봇), true);
							pc.getNearObjects().removeKnownObject(로봇);
						}
						world.removeVisibleObject(로봇);
						world.removeObject(로봇);
						로봇.getNearObjects().removeAllKnownObjects();
						로봇.stopHalloweenRegeneration();
						로봇.stopPapuBlessing();
						로봇.stopLindBlessing();
						로봇.stopHalloweenArmorBlessing();
						로봇.stopAHRegeneration();
						로봇.stopHpRegenerationByDoll();
						로봇.stopMpRegenerationByDoll();
						로봇.stopSHRegeneration();
						로봇.stopMpDecreaseByScales();
						로봇.stopEtcMonitor();
						로봇.setDead(true);
						gm.sendPackets(new S_SystemMessage(이름 + "로봇을 압류 시킵니다."), true);
						((L1RobotInstance) 로봇).updateban(true);
					} else {
						gm.sendPackets(new S_SystemMessage("로봇이 아닙니다."), true);
					}
				} else {
					gm.sendPackets(new S_SystemMessage("접속중인 로봇의 이름이 아닙니다."), true);
				}
			} else if (type.equalsIgnoreCase("손")) {
				로봇부처핸섬 bot = new 로봇부처핸섬();
				GeneralThreadPool.getInstance().execute(bot);
			} else {
				gm.sendPackets(new S_SystemMessage(".봇 종료 / 압류"), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".봇 종료 / 압류"), true);
		}
	}

	private void updateClanId(L1PcInstance player) {

		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET ClanID=? WHERE Clanname=?");
			pstm.setInt(1, player.getClanid());
			pstm.setString(2, player.getClanname());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void robotClanIdChange(L1PcInstance player) {

		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE robots_crown SET clanid=? WHERE clanname=?");
			pstm.setInt(1, player.getClanid());
			pstm.setString(2, player.getClanname());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE robots SET clanid=? WHERE clanname=?");
			pstm.setInt(1, player.getClanid());
			pstm.setString(2, player.getClanname());
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
}