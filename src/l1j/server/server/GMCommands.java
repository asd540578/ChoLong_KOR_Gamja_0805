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
import l1j.server.GameSystem.EventShop.���ƿ¾Ƴ���̺�Ʈ;
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
import l1j.server.server.TimeController.�ؼ�Controller;
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
import l1j.server.server.serverpackets.S_�����ֽ�;
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
	// public static boolean ���Ի��� = false;
	public static boolean �ڵ��������� = false;
	public static boolean �ֽþ�����üũ = false;
	public static boolean ���λ�������üũ = true;
	public static boolean �ɸ��������ڹ� = false;
	public static boolean Ʈ�������콺�� = false;
	public static boolean ���طα� = false;
	public static boolean �����̸�üũ = false;
	public static boolean �Ƶ���ȯüũ = false;
	public static boolean ����üũ = true;

	public static boolean �����α� = false;
	public static boolean �����ӵ�üũ = false;
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
				pc.sendPackets(new S_ServerMessage(74, "Ŀ��� " + name), true); // \f1%0��
																				// �����
																				// ��
																				// �����ϴ�.
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

	private static int �ҷ�������Ʈ[] = { 90085, 90086, 90087, 90088, 90089, 90090, 90091, 90092, 160423, 435000, 160510,
			160511, 21123, 21269 };
	private static int �ҷ�������Ʈ2[] = { 90085, 90086, 90087, 90088, 90089, 90090, 90091, 90092, 160423, 435000, 160510,
			160511, 21123 };

	public synchronized static void EventItemDelete() {
		try {
			for (L1PcInstance tempPc : L1World.getInstance().getAllPlayers()) {
				if (tempPc == null)
					continue;
				for (int i = 0; i < �ҷ�������Ʈ.length; i++) {
					L1ItemInstance[] item = tempPc.getInventory().findItemsId(�ҷ�������Ʈ[i]);

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
								tempPc.getInventory().setEquipped(item[o], false); // �߰����ּ���.
							}
							tempPc.getInventory().removeItem(item[o]);
						}
					}
					try {
						PrivateWarehouse pw = WarehouseManager.getInstance()
								.getPrivateWarehouse(tempPc.getAccountName());
						L1ItemInstance[] item2 = pw.findItemsId(�ҷ�������Ʈ[i]);
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
							L1ItemInstance[] item3 = cw.findItemsId(�ҷ�������Ʈ[i]);
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
								L1ItemInstance[] pitem = npc.getInventory().findItemsId(�ҷ�������Ʈ[i]);
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
					for (int ii = 0; ii < �ҷ�������Ʈ.length; ii++) {
						if (�ҷ�������Ʈ[ii] == temp_item.getItemId()) {
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
			for (int i = 0; i < �ҷ�������Ʈ2.length; i++) {
				sb.append(+�ҷ�������Ʈ2[i]);
				if (i < �ҷ�������Ʈ2.length - 1) {
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
		// ������ ��������� Ŀ�ǵ�, �� ���Ĵ� ������ �ܶ����� �� �Ķ���ͷμ� ����Ѵ�
		if (!token.hasMoreTokens()) {
			return;
		}
		String cmd = token.nextToken();
		String param = "";
		while (token.hasMoreTokens()) {
			param = new StringBuilder(param).append(token.nextToken()).append(' ').toString();
		}
		param = param.trim();

		// ����Ÿ���̽�ȭ �� Ŀ���
		if (executeDatabaseCommand(gm, cmd, param)) {
			if (!cmd.equalsIgnoreCase("�����")) {
				_lastCommands.put(gm.getId(), cmdLine);
			}
			return;
		}

		if (gm.getAccessLevel() < 200) {
			gm.sendPackets(new S_ServerMessage(74, "Ŀ�ǵ� " + cmd), true);
			return;
		}

		if (gm.isSGm()
				/*
				 * && !cmd.equalsIgnoreCase("������") &&
				 * !cmd.equalsIgnoreCase("������Ÿ��") &&
				 * !cmd.equalsIgnoreCase("�������Ϸ�") &&
				 * !cmd.equalsIgnoreCase("�������ʱ�ȭ")
				 */

				&& !cmd.equalsIgnoreCase("�з�����") && !cmd.equalsIgnoreCase("����") && !cmd.equalsIgnoreCase("����")
				&& !cmd.equalsIgnoreCase("�ʴ���")) {
			gm.sendPackets(new S_ServerMessage(74, "Ŀ�ǵ� " + cmd), true);
			return;
		}

		// GM�� �����ϴ� Ŀ�ǵ�� ���⿡ ����
		if (cmd.equalsIgnoreCase("����")) {
			showHelp(gm);
		} else if (cmd.equalsIgnoreCase("��ŷ����")) {
			UserRankingController.isRenewal = true;
			gm.sendPackets(new S_SystemMessage("��ŷ�� ���� �Ǿ����ϴ�."));
		} else if (cmd.equalsIgnoreCase("ų��Ʈ")) {
			killment(gm, param); // by ��� ų ��Ʈ
		} else if (cmd.equalsIgnoreCase("�ڵ��з�")) {
			�ڵ��з�(gm, param);

		} else if (cmd.equalsIgnoreCase("�ǸŻ���")) {
			sellshop(gm);
		} else if (cmd.equalsIgnoreCase("�ǿ�����")) {
			DevilController.getInstance().isGmOpen = true;
			gm.sendPackets(new S_SystemMessage("�Ǹ����� ���並 �����մϴ�."));
		} else if (cmd.equalsIgnoreCase("�ǿ�����")) {
			DevilController.getInstance().isGmOpen = false;
			gm.sendPackets(new S_SystemMessage("�Ǹ����� ���並 �ݽ��ϴ�."));

		} else if (cmd.equalsIgnoreCase("�ؼ�����")) {
			�ؼ�Controller.getInstance().isGmOpen = true;
			gm.sendPackets(new S_SystemMessage("������ ���� �����մϴ�."));
		} else if (cmd.equalsIgnoreCase("�ؼ�����")) {
			�ؼ�Controller.getInstance().isGmOpen = false;
			gm.sendPackets(new S_SystemMessage("������ ���� �ݽ��ϴ�."));
		} else if (cmd.equalsIgnoreCase("���Ի���")) {
			buyshop(gm);

		} else if (cmd.equalsIgnoreCase("�������Ի���")) {
			buyshop_user(gm);
		} else if (cmd.equalsIgnoreCase("��ü����")) {
			��ü����(gm);
		} else if (cmd.equalsIgnoreCase("����û��")) {
			����û��(gm);
		} else if (cmd.equalsIgnoreCase("�޸𸮹�ȯ")) {
			�޸𸮹�ȯ(gm);
		} else if (cmd.equalsIgnoreCase("����")) {
			����(gm, param);
		} else if (cmd.equalsIgnoreCase("��Ʈ")) {
			��Ʈ(gm, param);

		} else if (cmd.equalsIgnoreCase("�޼���")) {
			�޼���(gm, param);

		} else if (cmd.equalsIgnoreCase("����2")) {
			����2(gm, param);

		} else if (cmd.equalsIgnoreCase("�����ʱ�ȭ")) {
			�����ʱ�ȭ(gm, param);

		} else if (cmd.equalsIgnoreCase("��������")) {
			��������(gm, param);
		} else if (cmd.equalsIgnoreCase("html")) {
			html(gm, param);

		} else if (cmd.equalsIgnoreCase("�Ⱘ�ʱ�ȭ")) {
			�Ⱘ(gm);
		} else if (cmd.equalsIgnoreCase("�α�")) {
			standBy8(gm, param);
		} else if (cmd.equalsIgnoreCase("�׼�")) {
			�ù�(gm, param);

		} else if (cmd.equalsIgnoreCase("�Ƴ�����")) {
			�Ƴ�����(gm, param);

			// }else if (cmd.equalsIgnoreCase("������")) {
			// ����(gm);
		} else if (cmd.equalsIgnoreCase("ä�÷α�")) {
			ä��(gm, param);
		} else if (cmd.equalsIgnoreCase("��")) {
			���ڵ�(gm, param);

		} else if (cmd.equalsIgnoreCase("����")) {
			����(gm, param);
		} else if (cmd.equalsIgnoreCase("�����ӵ�üũ")) {
			�����ӵ�(gm);
		} else if (cmd.equalsIgnoreCase("����ð�")) {
			checktime(gm);
		} else if (cmd.equalsIgnoreCase("�����׽�Ʈ")) {
			��Ʈ(gm);
		} else if (cmd.equalsIgnoreCase("��Ŷ�ڽ�")) {
			packetbox(gm, param);
		} else if (cmd.equalsIgnoreCase("�����߰�")) {
			addaccount(gm, param);
			// } else if (cmd.equalsIgnoreCase("����")){
			// SpecialEventHandler.getInstance().doBugRace();
		} else if (cmd.equalsIgnoreCase("ȭ�����")) {
			SpecialEventHandler.getInstance().doScreenBuf(gm);
		} else if (cmd.equalsIgnoreCase("ȭ�����2")) {
			SpecialEventHandler.getInstance().doscreenbuftest(gm);
		} else if (cmd.equalsIgnoreCase("��ü����")) {
			SpecialEventHandler.getInstance().doAllBuf();
		} else if (cmd.equalsIgnoreCase("�ڸ�����")) {
			SpecialEventHandler.getInstance().doAllComaBuf();

		} else if (cmd.equalsIgnoreCase("�ڸ�")) {
			SpecialEventHandler.getInstance().doAllComa();

		} else if (cmd.equalsIgnoreCase("ȭ���ڸ�")) {
			SpecialEventHandler.getInstance().doScreenComaBuf(gm);

		} else if (cmd.equalsIgnoreCase("���λ���")) {
			autoshop(gm, param);
		} else if (cmd.equalsIgnoreCase("�������")) {
			changepassword(gm, param);
		} else if (cmd.equalsIgnoreCase("��")) {
			CodeTest(gm, param);
		} else if (cmd.equalsIgnoreCase("����")) {
			Clear(gm);
		} else if (cmd.equalsIgnoreCase("��")) {
			spawnmodel(gm, param);
		} else if (cmd.equalsIgnoreCase("��������")) {
			serversave(gm);// ��� �߰�
		} else if (cmd.equalsIgnoreCase("����")) {
			nocall(gm, param);
		} else if (cmd.equalsIgnoreCase("����")) {
			hellcall(gm, param);
		} else if (cmd.startsWith("�з�����")) {
			accountdel(gm, param);
		} else if (cmd.startsWith("�з�")) {
			kick(gm, param);
		} else if (cmd.startsWith("��")) {
			ban(gm, param);
		} else if (cmd.startsWith("����")) {
			standBy7(gm, param);
		} else if (cmd.startsWith("�ɸ�����")) {
			standBy77(gm, param);
		} else if (cmd.startsWith("�̺�����")) {
			standBy79(gm, param);
		} else if (cmd.startsWith("ȯ��")) {
			ȯ��(gm, param);
		} else if (cmd.startsWith("���Ǳ׷ε�")) {
			standBy80(gm);
		} else if (cmd.startsWith("�н�")) {
			standBy81(gm, param);
		} else if (cmd.startsWith("ȭ��н�")) {
			standBy82(gm, param);
		} else if (cmd.startsWith("���������")) {
			standBy82(gm);

		} else if (cmd.equalsIgnoreCase("ä��Ǯ��")) {
			chatx(gm, param);
		} else if (cmd.equalsIgnoreCase("��ü����")) {
			allpresent(gm, param);
		} else if (cmd.equalsIgnoreCase("��������")) { // / �߰�
			SetRates(gm, param);
		} else if (cmd.equalsIgnoreCase("��æ����")) {
			SetEnchantRates(gm, param);
		} else if (cmd.equalsIgnoreCase("������ȸ")) {
			CheckAllRates(gm);
		} else if (cmd.equalsIgnoreCase("����")) { // #### �ɸ��˻�
			chainfo(gm, param);
		} else if (cmd.equalsIgnoreCase("�κ�")) { // #### �ɸ��˻�
			�κ�(gm, param);
		} else if (cmd.equalsIgnoreCase("�Ǹ�üũ")) {
			�Ǹ�üũ(gm);
		} else if (cmd.equals("��������")) {
			castleWarStart(gm, param);
		} else if (cmd.equals("��������")) {
			castleWarExit(gm, param);
		} else if (cmd.startsWith("��ġ����")) {
			returnEXP(gm, param);
		} else if (cmd.equalsIgnoreCase("����ä��")) { // by�ǵ��� ����ä��
			if (Config.isGmchat) {
				Config.isGmchat = false;
				gm.sendPackets(new S_SystemMessage("����ä�� OFF"), true);
			} else {
				Config.isGmchat = true;
				gm.sendPackets(new S_SystemMessage("����ä�� ON"), true);
			}
		} else if (cmd.equalsIgnoreCase("��������")) {
			StartWar(gm, param);
		} else if (cmd.equalsIgnoreCase("��������")) {
			StopWar(gm, param);
		} else if (cmd.equalsIgnoreCase("�˻�")) { // ########## �˻� �߰� ##########
			searchDatabase(gm, param);
		} else if (cmd.equalsIgnoreCase("�׺�")) {
			gamble(gm);
		} else if (cmd.equalsIgnoreCase("�����")) {
			GeneralThreadPool.getInstance().execute(new DeadLockDetector(gm));
		} else if (cmd.equalsIgnoreCase("��Ǯ")) {
			gm.sendPackets(new S_SystemMessage("DBCP Active Connection Count >> " + DB.active_count_check), true);
		} else if (cmd.equalsIgnoreCase("�տ���ǥ")) {
			if (CrockSystem.getInstance().isOpen()) {
				// L1EvaSystem eva = EvaSystemTable.getInstance().getSystem(1);
				int[] loc = CrockSystem.getInstance().loc();
				gm.sendPackets(new S_SystemMessage("�տ� ��ǥ > x:" + loc[0] + " y:" + loc[1]), true);
			}
		} else if (cmd.equalsIgnoreCase("����")) {
			maphack(gm, param);
		} else if (cmd.equalsIgnoreCase("����")) {
			UserGiranTel(gm, param);
		} else if (cmd.equalsIgnoreCase("�ʹ���")) {
			mapBuff(gm);
			// } else if (cmd.equalsIgnoreCase("�ű�����")){
			// �ű�����(gm, param);
		} else if (cmd.equalsIgnoreCase("��Ʈ����")) {
			��Ʈ����(gm, param);
		} else if (cmd.equalsIgnoreCase("�����")) {
			if (!_lastCommands.containsKey(gm.getId())) {
				gm.sendPackets(new S_ServerMessage(74, "Ŀ�ǵ� " + cmd), true); // \f1%0��
																				// �����
																				// ��
																				// �����ϴ�.
				return;
			}
			redo(gm, param);
			return;
		} else if (cmd.equalsIgnoreCase("ȭ�齺��")) {
			ȭ�齺��(gm, param);
		} else if (cmd.equalsIgnoreCase("�׼Ǽӵ�")) {
			SprSpeed(gm, param);
		} else if (cmd.equalsIgnoreCase("���͸�ũ")) {
			clanMark(gm, param);
		} else if (cmd.equalsIgnoreCase("�αױ��")) {
			logSwitch(gm, param);
		} else if (cmd.equalsIgnoreCase("�ڵ�����")) {
			autobot(gm, param);
		} else if (cmd.equalsIgnoreCase("��Ʋ��")) {
			if (BattleZone.getInstance().getDuelStart()) {
				gm.sendPackets(new S_SystemMessage("��Ʋ���� ���� �� �Դϴ�."));
			} else {
				BattleZone.getInstance().setGmStart(true);
				gm.sendPackets(new S_SystemMessage("��Ʋ���� ���� �Ǿ����ϴ�."));
			}
		} else if (cmd.equalsIgnoreCase("�Ϸ��������")) {
			L1MerchantInstance.resetOneDayBuy();
			gm.sendPackets(new S_SystemMessage("�Ϸ���� ����"), true);
		} else if (cmd.equalsIgnoreCase("����üũ")) {
			����üũ = !����üũ;
			gm.sendPackets(new S_SystemMessage("����üũ : " + ����üũ), true);
		} else if (cmd.equalsIgnoreCase("����üũ")) {
			����üũ = !����üũ;
			gm.sendPackets(new S_SystemMessage("����üũ : " + ����üũ), true);
		} else if (cmd.equalsIgnoreCase("�ʴ���")) {
			mapwho(gm, param);
		} else if (cmd.equalsIgnoreCase("�ɸ�����") || cmd.equalsIgnoreCase("ĳ������")) {
			charname(gm, param);

		} else if (cmd.equalsIgnoreCase("����")) {
			iconserch2(gm, param);
		} else if (cmd.equalsIgnoreCase("�ڵ�������")) {
			autoIpcheck(gm, param);
		} else if (cmd.equalsIgnoreCase("��")) {
			��(gm, param);
		} else if (cmd.equalsIgnoreCase("����üũ")) {
			����üũ = !����üũ;
			gm.sendPackets(new S_SystemMessage("����üũ : " + ����üũ), true);
		} else if (cmd.equalsIgnoreCase("���λ�������üũ")) {
			���λ�������üũ = !���λ�������üũ;
			gm.sendPackets(new S_SystemMessage("���λ�������üũ : " + ���λ�������üũ), true);
		} else if (cmd.equalsIgnoreCase("������üũ")) {
			�ֽþ�����üũ = !�ֽþ�����üũ;
			gm.sendPackets(new S_SystemMessage("������üũ : " + �ֽþ�����üũ), true);
		} else if (cmd.equalsIgnoreCase("��������")) {
			�ɸ��������ڹ� = !�ɸ��������ڹ�;
			gm.sendPackets(new S_SystemMessage("�������� : " + �ɸ��������ڹ�), true);
		} else if (cmd.equalsIgnoreCase("Ʈ���ý���")) {
			Ʈ�������콺�� = !Ʈ�������콺��;
			gm.sendPackets(new S_SystemMessage("Ʈ���ý��� : " + Ʈ�������콺��), true);
		} else if (cmd.equalsIgnoreCase("�ڵ���������")) {
			�ڵ��������� = !�ڵ���������;
			gm.sendPackets(new S_SystemMessage("�ڵ��������� : " + �ڵ���������), true);
		} else if (cmd.equalsIgnoreCase("���طα�")) {
			���طα� = !���طα�;
			gm.sendPackets(new S_SystemMessage("���طα� : " + ���طα�), true);
		} else if (cmd.equalsIgnoreCase("����")) {
			ipphone_certification(gm, param);
		} else if (cmd.equalsIgnoreCase("��ȭâ")) {
			try {
				StringTokenizer st = new StringTokenizer(param);
				String s = st.nextToken();
				gm.sendPackets(new S_NPCTalkReturn(gm.getId(), s), true);
			} catch (Exception e) {
			}
		} else if (cmd.equalsIgnoreCase("�����̸�üũ")) {
			�����̸�üũ = !�����̸�üũ;
			gm.sendPackets(new S_SystemMessage("�����̸�üũ : " + �����̸�üũ), true);
		} else if (cmd.equalsIgnoreCase("�����̸�üũ����")) {
			connectCharNameReset();
		} else if (cmd.equalsIgnoreCase("�Ƶ���ȯüũ")) {
			�Ƶ���ȯüũ = !�Ƶ���ȯüũ;
			gm.sendPackets(new S_SystemMessage("�Ƶ���ȯüũ : " + �Ƶ���ȯüũ), true);
		} else if (cmd.equalsIgnoreCase("�κ�����")) {
			try {
				List<L1ItemInstance> list = gm.getInventory().getItems();
				L1ItemInstance[] ll = list.toArray(new L1ItemInstance[list.size()]);
				for (L1ItemInstance item : ll) {
					gm.getInventory().removeItem(item);
				}
			} catch (Exception e) {
			}
		} else if (cmd.equalsIgnoreCase("������")) {
			������(gm, param);

		} else if (cmd.equals("����Ʈ����")) {
			GiveHouse(gm, param);
		} else if (cmd.equalsIgnoreCase("�巡�����")) {
			if (NpcShopSystem._dragon_power) {
				gm.sendPackets(new S_SystemMessage("�̹� �������Դϴ�."), true);
				return;
			}
			NpcShopSystem.getInstance().npcDragonShopStart();
			// } else if (cmd.equalsIgnoreCase("��üũ")){
			// �̹���üũ(gm);
		} else if (cmd.equalsIgnoreCase("����")) {
			����(gm, param);
		} else if (cmd.equalsIgnoreCase("����")) {
			����(gm, param);
		} else if (cmd.equalsIgnoreCase("�ٵ���")) {
			�ٵ���(gm, param);
		} else if (cmd.equalsIgnoreCase("Ž�ֱ�")) {
			Ž�ֱ�(gm, param);
		} else if (cmd.equalsIgnoreCase("��ɾƵ��α�")) {
			��ɾƵ�(gm, param);
		} else if (cmd.equalsIgnoreCase("�����Ƶ��α�")) {
			�����Ƶ�(gm, param);
		} else if (cmd.equalsIgnoreCase("ȭ��α�")) {
			if (fifi == null) {
				fifi = GeneralThreadPool.getInstance().schedule(new sdf(gm), 1000);
				gm.sendPackets(new S_SystemMessage("----- ȭ�� �α� ���� -----"), true);
			}
		} else if (cmd.equalsIgnoreCase("ȭ��α�����")) {
			if (fifi != null) {
				fifi.cancel(true);
				fifi = null;
				for (L1PcInstance pc : L1World.getInstance().getAllPlayersToArray()) {
					if (pc.getNetConnection() == null)
						continue;
					if (pc.getNetConnection().��Ŷ�α� || GameServer.getInstance().checkip(pc.getNetConnection().getIp())) {
						pc.getNetConnection().�ൿ�α�����(true);
						pc.getNetConnection().��Ŷ�α� = false;
						GameServer.getInstance().removeip(pc.getNetConnection().getIp());
					}
				}
				gm.sendPackets(new S_SystemMessage("----- ȭ�� �α� ���� -----"), true);
			}
		} else if (cmd.equalsIgnoreCase("�����з�")) {
			�����з�(gm, param);
		} else if (cmd.equalsIgnoreCase("��")) {
			����߰�(gm, param);
		} else if (cmd.equalsIgnoreCase("�Ƴ��")) {
			���ƿ¾Ƴ���̺�Ʈ(gm, param);
		} else if (cmd.equalsIgnoreCase("��")) {
			��Ʈ(gm, param);
		} else {
			gm.sendPackets(new S_SystemMessage("Ŀ��� " + cmd + " �� �������� �ʽ��ϴ�. "), true);
		}
		eva.LogCommandAppend("[��ڸ��]" + gm.getName(), cmd, param);
		token = null;
	}

	private void ��Ʈ(L1PcInstance gm, String param) {
		StringTokenizer st = new StringTokenizer(param);
		String _��Ʈ = st.nextToken();
		gm._note = _��Ʈ;
		gm.sendPackets(new S_SystemMessage("��Ʈ����� :" + _��Ʈ));
	}

	public void killment(L1PcInstance pc, String param) { // by��� ��Ʈ
		if (param.equalsIgnoreCase("��")) {
			pc.ų��Ʈ = false;
			pc.sendPackets(new S_SystemMessage("ų��Ʈ �� ǥ������ �ʽ��ϴ�."));
		} else if (param.equalsIgnoreCase("��")) {
			pc.ų��Ʈ = true;
			pc.sendPackets(new S_SystemMessage("ų��Ʈ �� ǥ�� �մϴ�."));

		} else {
			pc.sendPackets(new S_SystemMessage(".ų��Ʈ [��/��] ���� �Է��� �ּ���. "));
		}
	}

	private void charname(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String chaName = tok.nextToken();
			if (pc.getClanid() > 0) {
				pc.sendPackets(new S_SystemMessage("\\fU����Ż���� ĳ������ �����Ҽ� �ֽ��ϴ�."));
				return;
			}
			if (!pc.getInventory().checkItem(467009, 1)) { // �ֳ� üũ
				pc.sendPackets(new S_SystemMessage("\\fU�ɸ��� ���� ������� �����ϼž� �����մϴ�."));
				return;
			}
			for (int i = 0; i < chaName.length(); i++) {
				if (chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��' || // �ѹ���(char)������ ��.
						chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
						|| chaName.charAt(i) == '��') {
					pc.sendPackets(new S_SystemMessage("����Ҽ����� �ɸ����Դϴ�."));
					return;
				}
			}
			if (chaName.getBytes().length > 12) {
				pc.sendPackets(new S_SystemMessage("�̸��� �ʹ� ��ϴ�."));
				return;
			}
			if (chaName.length() == 0) {
				pc.sendPackets(new S_SystemMessage("������ �ɸ����� �Է��ϼ���."));
				return;
			}
			if (BadNamesList.getInstance().isBadName(chaName)) {
				pc.sendPackets(new S_SystemMessage("����� �� ���� �ɸ����Դϴ�."));
				return;
			}
			if (isInvalidName(chaName)) {
				pc.sendPackets(new S_SystemMessage("����� �� ���� �ɸ����Դϴ�."));
				return;
			}
			if (CharacterTable.doesCharNameExist(chaName)) {
				pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
				return;
			}

			if (CharacterTable.RobotNameExist(chaName)) {
				pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
				return;
			}
			if (CharacterTable.RobotCrownNameExist(chaName)) {
				pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
				return;
			}
			if (NpcShopSpawnTable.getInstance().getNpc(chaName) || npcshopNameCk(chaName)) {
				pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
				return;
			}
			if (CharacterTable.somakname(chaName)) {
				pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
				return;
			}
			String oldname = pc.getName();

			chaname(chaName, oldname);

			long sysTime = System.currentTimeMillis();
			logchangename(chaName, oldname, new Timestamp(sysTime));

			pc.sendPackets(new S_SystemMessage(chaName + " ���̵�� ���� �ϼ̽��ϴ�."));
			pc.sendPackets(new S_SystemMessage("\\fU���� �Է�â���� �̵��� �ٽ� �����Ͻø� ����˴ϴ�."));
			pc.getInventory().consumeItem(467009, 1); // �Ҹ�
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".�ɸ����� [�ٲٽǾ��̵�] []�� �����ϰ� �Է����ּ���."));
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

	/** ���� �������� �˻��Ѵ� ���� **/
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

	private void ����߰�(L1PcInstance gm, String param) {
		StringTokenizer st = new StringTokenizer(param);
		int itemid = Integer.parseInt(st.nextToken(), 10);
		// int max = Integer.parseInt(st.nextToken(), 10);
		L1Item temp = ItemTable.getInstance().getTemplate(itemid);
		if (temp == null) {
			gm.sendPackets(new S_SystemMessage("�������� ã���� �����ϴ�."));
			return;
		}
		L1Object npc = NpcTable.getInstance().getTemplate(gm._npcnum);
		if (npc == null) {
			gm.sendPackets(new S_SystemMessage("���Ǿ��� ã���� �����ϴ�."));
			return;
		}
		@SuppressWarnings("static-access")
		boolean ck = DropTable.getInstance().SabuDrop(gm._npcnum, itemid, 1, 1000000, gm._npcname, temp.getName(),
				gm._note);
		if (ck) {
			gm.sendPackets(
					new S_SystemMessage("����߰� : npc-" + gm._npcnum + " / item(" + itemid + ") / max " + 1 + "��"));
		} else {
			gm.sendPackets(new S_SystemMessage("����߰� : �̹� ��ϵ� ��� ����Ʈ"));
		}
	}

	private void �����з�(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String �̸� = st.nextToken();
			L1PcInstance pc = L1World.getInstance().getPlayer(�̸�);
			if (pc == null) {
				gm.sendPackets(new S_SystemMessage(�̸� + " �̶� �ɸ��ʹ� �� ���� ���Դϴ�."), true);
				return;
			}
			if (pc.getNetConnection() == null || pc.getNetConnection().getIp() == null) {
				gm.sendPackets(new S_SystemMessage(�̸� + " �̶� �ɸ��ʹ� ���λ����̰ų� �κ� �ɸ����Դϴ�."), true);
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
					IpTable.getInstance().banIp(ip + i); // BAN ����Ʈ�� IP�� �߰��Ѵ�.
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
			gm.sendPackets(new S_SystemMessage(�̸� + " �ɸ��� ���� [" + ip + " �뿪] IP ���ܰ� �ش� ������ �з���ŵ�ϴ�."), true);

		} catch (Exception e) {
		}
	}

	private void ��ɾƵ�(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String �̸� = st.nextToken();
			if (�̸�.equalsIgnoreCase("��")) {
				if (LogTable.��ɾƵ�) {
					gm.sendPackets(new S_SystemMessage("��ɾƵ� �αװ� �̹� �������Դϴ�."), true);
					return;
				}
				LogTable.��ɾƵ�����();
				gm.sendPackets(new S_SystemMessage("��ɾƵ� �αװ� ���� �Ǿ����ϴ�."), true);
			} else if (�̸�.equalsIgnoreCase("��")) {
				if (!LogTable.��ɾƵ�) {
					gm.sendPackets(new S_SystemMessage("��ɾƵ� �αװ� �̹� �������Դϴ�."), true);
					return;
				}
				LogTable.��ɾƵ�����();
				gm.sendPackets(new S_SystemMessage("��ɾƵ� �αװ� ���� �Ǿ����ϴ�."), true);
			}
		} catch (Exception e) {
		}
	}

	private void �����Ƶ�(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String �̸� = st.nextToken();
			if (�̸�.equalsIgnoreCase("��")) {
				if (LogTable.�����Ƶ�) {
					gm.sendPackets(new S_SystemMessage("�����Ƶ� �αװ� �̹� �������Դϴ�."), true);
					return;
				}
				LogTable.�����Ƶ�����();
				gm.sendPackets(new S_SystemMessage("�����Ƶ� �αװ� ���� �Ǿ����ϴ�."), true);
			} else if (�̸�.equalsIgnoreCase("��")) {
				if (!LogTable.�����Ƶ�) {
					gm.sendPackets(new S_SystemMessage("�����Ƶ� �αװ� �̹� �������Դϴ�."), true);
					return;
				}
				LogTable.�����Ƶ�����();
				gm.sendPackets(new S_SystemMessage("�����Ƶ� �αװ� ���� �Ǿ����ϴ�."), true);
			}
		} catch (Exception e) {
		}
	}

	private void ����(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String �̸� = st.nextToken();
			if (�̸�.equalsIgnoreCase("��")) {
				ItemBayChatController.getInstance().OnOff(gm, true);
			} else if (�̸�.equalsIgnoreCase("��")) {
				ItemBayChatController.getInstance().OnOff(gm, false);
			}
		} catch (Exception e) {
		}
	}

	private void �ٵ���(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String �̸� = st.nextToken();
			if (�̸�.equalsIgnoreCase("��")) {
				BadukiChatController.getInstance().OnOff(gm, true);
			} else if (�̸�.equalsIgnoreCase("��")) {
				BadukiChatController.getInstance().OnOff(gm, false);
			}
		} catch (Exception e) {
		}
	}

	private void ����(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String �̸� = st.nextToken();
			if (�̸�.equalsIgnoreCase("��")) {
				NurtureChatController.getInstance().OnOff(gm, true);
			} else if (�̸�.equalsIgnoreCase("��")) {
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
					if (pl.getNetConnection() == null || pl.getNetConnection().��Ŷ�α�
							|| GameServer.getInstance().checkip(pl.getNetConnection().getIp()))
						continue;
					pl.getNetConnection().��Ŷ�α� = true;
					pc.sendPackets(new S_SystemMessage(pl.getName() + "���� �α׸� ����մϴ�."), true);
					pc.sendPackets(new S_SystemMessage(pl.getNetConnection().getIp() + "�� �����ϴ� ��� �ɸ����� �ڵ� ����˴ϴ�."),
							true);
					GameServer.getInstance().addipl(pl.getNetConnection().getIp());
				}
			} catch (Exception e) {
			}
			fifi = GeneralThreadPool.getInstance().schedule(this, 1000);
		}
	}

	private void Ž�ֱ�(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String �̸� = st.nextToken();
			int id = Integer.parseInt(st.nextToken());
			L1PcInstance ���� = L1World.getInstance().getPlayer(�̸�);
			if (���� != null) {
				����.getNetConnection().getAccount().tam_point += id;
				����.getNetConnection().getAccount().updateTam();
				try {
					����.sendPackets(new S_NewCreateItem(S_NewCreateItem.TAM_POINT, ����.getNetConnection()), true);
				} catch (Exception e) {
				}
				gm.sendPackets(new S_SystemMessage(����.getName() + "���� Ž " + id + "���� �־����ϴ�."), true);
			} else
				gm.sendPackets(new S_SystemMessage("�������� �ʴ� ���� �Դϴ�."), true);
		} catch (Exception e) {
		}
	}

	private void �ڵ��з�(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String �̸� = st.nextToken();
			L1PcInstance �κ� = L1World.getInstance().getPlayer(�̸�);
			if (�κ� != null) {
				if (�κ� instanceof L1RobotInstance) {
					L1World world = L1World.getInstance();
					if (((L1RobotInstance) �κ�).��ɺ�) {
						if (((L1RobotInstance) �κ�).���_����) {
							gm.sendPackets(new S_SystemMessage("���� ������� �κ��Դϴ�."), true);
							return;
						} else {
							Robot_Hunt.getInstance().delay_spawn(((L1RobotInstance) �κ�).��ɺ�_��ġ, 60000);
							((L1RobotInstance) �κ�).����(1);
							gm.sendPackets(new S_SystemMessage(�̸� + " �κ��� ���� ��ŵ�ϴ�."), true);
							return;
						}
					}
					((L1RobotInstance) �κ�)._���������� = true;
					if (!((L1RobotInstance) �κ�).������ && !((L1RobotInstance) �κ�).���ú�) {
						for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(�κ�)) {
							pc.sendPackets(new S_RemoveObject(�κ�), true);
							pc.getNearObjects().removeKnownObject(�κ�);
						}
						world.removeVisibleObject(�κ�);
						world.removeObject(�κ�);
						�κ�.getNearObjects().removeAllKnownObjects();
					}
					�κ�.stopHalloweenRegeneration();
					�κ�.stopPapuBlessing();
					�κ�.stopLindBlessing();
					�κ�.stopHalloweenArmorBlessing();
					�κ�.stopAHRegeneration();
					�κ�.stopHpRegenerationByDoll();
					�κ�.stopMpRegenerationByDoll();
					�κ�.stopSHRegeneration();
					�κ�.stopMpDecreaseByScales();
					�κ�.stopEtcMonitor();
					((L1RobotInstance) �κ�).���溿_Ÿ�� = 0;
					((L1RobotInstance) �κ�).loc = null;
					if (�κ�.getClanid() != 0) {
						�κ�.getClan().removeOnlineClanMember(�κ�.getName());
					}
					Robot.Doll_Delete((L1RobotInstance) �κ�);
					gm.sendPackets(new S_SystemMessage(�̸� + " �κ��� ���� ��ŵ�ϴ�."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(�̸� + " �ɸ��ʹ� �Ϲ� ���� �Դϴ�."), true);
				}
			} else {
				gm.sendPackets(new S_SystemMessage(�̸� + " �ɸ��ʹ� �������� �ƴմϴ�."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�ڵ��з� [�̸�]"), true);
		}
	}

	private void ������(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String charname = st.nextToken();
			int id = Integer.parseInt(st.nextToken());
			L1PcInstance target = L1World.getInstance().getPlayer(charname);
			if (target == null) {
				gm.sendPackets(new S_SystemMessage("�� ���� �ɸ����Դϴ�."), true);
				return;
			}
			if (target.getClanid() == 0) {
				gm.sendPackets(new S_SystemMessage("������ ���� �ɸ����Դϴ�."), true);
				return;
			}
			if (L1World.getInstance().getClan(id) != null) {
				gm.sendPackets(new S_SystemMessage("�����ϴ� ��ID �Դϴ�."), true);
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
			gm.sendPackets(new S_SystemMessage(".������ [�ɸ����̸�][�űԹ����ȣ]"), true);
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
				gm.sendPackets(new S_SystemMessage("�� ���� �ɸ����Դϴ�."), true);
				return;
			}
			if (IpPhoneCertificationTable.getInstance().list().contains(target.getNetConnection().getIp())) {
				gm.sendPackets(new S_SystemMessage("�̹� �����ϴ� ������ �Դϴ�. IP: " + target.getNetConnection().getIp()), true);
				return;
			}
			IpPhoneCertificationTable.getInstance().add(target.getNetConnection().getIp());
			gm.sendPackets(new S_SystemMessage(charname + " << ���� �Ͽ����ϴ�. IP: " + target.getNetConnection().getIp()),
					true);
			target.setTelType(77);
			target.sendPackets(new S_SabuTell(target), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".���� [�ɸ���]"), true);
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
					gm.sendPackets(new S_SystemMessage("������ ���������� : " + ia.namelist.size() + " �ɸ� - ������: " + ia.ip),
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
						gmList.append("�̸� : " + eachpc.getName() + " / ���� : " + eachpc.getLevel() + "\n");
						countGM++;
						continue;
					}
					if (!eachpc.isPrivateShop()) {
						if (eachpc.noPlayerCK) {
							noplayList.append(eachpc.getName() + ", ");
							nocountPlayer++;
							continue;
						} else {
							playList.append("�̸� : " + eachpc.getName() + " / ���� : " + eachpc.getLevel() + "\n");
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
				gm.sendPackets(new S_SystemMessage("-- ��� (" + countGM + "��)"));
				gm.sendPackets(new S_SystemMessage(gmList.toString()));
			}
			if (noplayList.length() > 0) {
				gm.sendPackets(new S_SystemMessage("-- ��� (" + nocountPlayer + "��)"));
				gm.sendPackets(new S_SystemMessage(noplayList.toString()));
			}
			if (playList.length() > 0) {
				gm.sendPackets(new S_SystemMessage("-- �÷��̾� (" + countPlayer + "��)"));
				gm.sendPackets(new S_SystemMessage(playList.toString()));
			}

			if (shopList.length() > 0) {
				gm.sendPackets(new S_SystemMessage("-- ���λ��� (" + countShop + "��)"));
				gm.sendPackets(new S_SystemMessage(shopList.toString()));
			}
			/*
			 * StringBuilder sb = new StringBuilder(); int count = 0;
			 * for(L1Object obj :
			 * L1World.getInstance().getVisibleObjects(i).values()){ if(obj
			 * instanceof L1PcInstance){ L1PcInstance pc = (L1PcInstance)obj;
			 * sb.append("�ɸ���:"+pc.getName()+" ����:"+pc.getLevel()+"\n");
			 * count++; } } gm.sendPackets(new S_Ranking(i, count,
			 * sb.toString()), true);
			 */
		} catch (Exception e) {
		}
	}

	public static boolean ����üũ = false;
	public static boolean ����üũ = false;

	private void autobot(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String ��� = st.nextToken();
			if (���.equalsIgnoreCase("���")) {
				if (huntBot) {
					gm.sendPackets(new S_SystemMessage("��� ���� ���� �������Դϴ�."), true);
					return;
				}
				huntBot = true;
				Robot_Hunt.getInstance().start_spawn();
				gm.sendPackets(new S_SystemMessage("��ɺ� ������ �����մϴ�."), true);
				return;
			} else if (���.equalsIgnoreCase("����")) {
				if (restartBot) {
					gm.sendPackets(new S_SystemMessage("���� ���� ���� �������Դϴ�."), true);
					return;
				}
				restartBot = true;
				Robot_ConnectAndRestart.getInstance().start_spawn();
				gm.sendPackets(new S_SystemMessage("������ ������ �����մϴ�."), true);
				return;
			} else if (���.equalsIgnoreCase("����")) {
				if (bugbearBot) {
					gm.sendPackets(new S_SystemMessage("���� ���� ���� �������Դϴ�."), true);
					return;
				}
				bugbearBot = true;
				Robot_Bugbear.getInstance().start_spawn();
				gm.sendPackets(new S_SystemMessage("���溿 ������ �����մϴ�."), true);
				return;
			} else if (���.equalsIgnoreCase("����")) {
				if (clanBot) {
					gm.sendPackets(new S_SystemMessage("���� ���� ���� �������Դϴ�."), true);
					return;
				}
				clanBot = true;
				Robot_Crown.getInstance().loadbot();
				gm.sendPackets(new S_SystemMessage("���ֺ� ������ �����մϴ�."), true);
				return;
			} else if (���.equalsIgnoreCase("����")) {
				if (fishBot) {
					gm.sendPackets(new S_SystemMessage("���� ���� ���� �������Դϴ�."), true);
					return;
				}
				fishBot = true;
				Robot_Fish.getInstance().start_spawn();
				gm.sendPackets(new S_SystemMessage("���ú� ������ �����մϴ�."), true);
				return;
			} else if (���.equalsIgnoreCase("����")) {
				Robot.���� = !Robot.����;
				gm.sendPackets(new S_SystemMessage("�κ� ���� ���: " + Robot.����), true);
			} else {
				gm.sendPackets(new S_SystemMessage("����� [��� / ���� / ���� / ���� / ���� / ����(�����������)] �� �����մϴ�."), true);
				return;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�ڵ����� [��� / ���� / ���� / ���� / ���� / ����(�����������)]"), true);
		}
	}

	public static boolean �α�_��� = false;
	public static boolean �α�_�Ⱦ� = false;
	public static boolean �α�_���� = true;
	public static boolean �α�_���� = true;
	public static boolean �α�_���λ��� = true;
	public static boolean �α�_��ȯ = true;
	public static boolean �α�_��þ = true;

	private void ���ƿ¾Ƴ���̺�Ʈ(L1PcInstance gm, String param) {
		if (param.equalsIgnoreCase("����")) {
			if (!Config.�Ƴ���̺�Ʈ) {
				���ƿ¾Ƴ���̺�Ʈ.getInstance().isGmOpen4 = true;
				���ƿ¾Ƴ���̺�Ʈ.getInstance().start();
			} else {
				gm.sendPackets(new S_SystemMessage("���� �Ƴ�� �̺�Ʈ�� ������ �Դϴ�."));
			}
		} else if (param.equalsIgnoreCase("����")) {
			if (Config.�Ƴ���̺�Ʈ) {
				���ƿ¾Ƴ���̺�Ʈ.getInstance().isGmOpen4 = false;
				���ƿ¾Ƴ���̺�Ʈ.getInstance().set���ƿ¾Ƴ���̺�Ʈ(false);
				���ƿ¾Ƴ���̺�Ʈ.getInstance().End();
				gm.sendPackets(new S_SystemMessage("�Ƴ�� �̺�Ʈ�� ���� ���� �Ǿ����ϴ�."));
			} else {
				gm.sendPackets(new S_SystemMessage("�Ƴ�� �̺�Ʈ �������� �ƴմϴ�."));
			}
		} else {
			gm.sendPackets(new S_SystemMessage(".�Ƴ�� [���� or ����] �Է��ϼ���"));
		}
	}

	private void logSwitch(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String type = st.nextToken();
			if (type.equalsIgnoreCase("���")) {
				�α�_��� = !�α�_���;
				gm.sendPackets(new S_SystemMessage("�α� ��� : " + �α�_���), true);
			} else if (type.equalsIgnoreCase("�Ⱦ�")) {
				�α�_�Ⱦ� = !�α�_�Ⱦ�;
				gm.sendPackets(new S_SystemMessage("�α� �Ⱦ� : " + �α�_�Ⱦ�), true);
			} else if (type.equalsIgnoreCase("����")) {
				�α�_���� = !�α�_����;
				gm.sendPackets(new S_SystemMessage("�α� ���� : " + �α�_����), true);
			} else if (type.equalsIgnoreCase("����")) {
				�α�_���� = !�α�_����;
				gm.sendPackets(new S_SystemMessage("�α� ���� : " + �α�_����), true);
			} else if (type.equalsIgnoreCase("���λ���")) {
				�α�_���λ��� = !�α�_���λ���;
				gm.sendPackets(new S_SystemMessage("�α� ���λ��� : " + �α�_���λ���), true);
			} else if (type.equalsIgnoreCase("��ȯ")) {
				�α�_��ȯ = !�α�_��ȯ;
				gm.sendPackets(new S_SystemMessage("�α� ��ȯ : " + �α�_��ȯ), true);
			} else if (type.equalsIgnoreCase("��þ")) {
				�α�_��þ = !�α�_��þ;
				gm.sendPackets(new S_SystemMessage("�α� ��þ : " + �α�_��þ), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�αױ�� [���, �Ⱦ�, ����, ����, ���λ���, ��ȯ, ��þ]"), true);
			// e.printStackTrace();
		}
	}

	public static int ȯ���� = 95;

	private void ȯ��(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			int i = Integer.parseInt(st.nextToken(), 10);
			ȯ���� = i;
			gm.sendPackets(new S_SystemMessage("ȯ���� 0." + ȯ���� + "�� ���� �Ǿ����ϴ�."));
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".ȯ�� [����]"));
		}
	}

	private void ��Ʈ����(L1PcInstance gm, String param) {

		try {
			/*
			 * StringTokenizer st = new StringTokenizer(param); int port =
			 * Integer.parseInt(st.nextToken(), 10); boolean check =
			 * xnetwork.Acceptor.ChangePort(port); if(check) gm.sendPackets(new
			 * S_SystemMessage(port+" ��ȣ�� ��Ʈ�� ���� �Ǿ����ϴ�.")); else
			 */
			gm.sendPackets(new S_SystemMessage("��Ʈ ������ �����Ͽ����ϴ�."));
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".��Ʈ���� [Port]"));
		}
	}

	private void SprSpeed(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			int spr = Integer.parseInt(st.nextToken(), 10);
			String type = st.nextToken();
			int speed = 0;
			if (type.equalsIgnoreCase("����"))
				speed = SprTable.getInstance().getAttackSpeed(spr, 1);
			else if (type.equalsIgnoreCase("�̵�"))
				speed = SprTable.getInstance().getMoveSpeed(spr, 0);
			else if (type.equalsIgnoreCase("����"))
				speed = SprTable.getInstance().getDirSpellSpeed(spr);
			else if (type.equalsIgnoreCase("��������"))
				speed = SprTable.getInstance().getNodirSpellSpeed(spr);
			gm.sendPackets(new S_SystemMessage("Spr��ȣ: " + spr + " " + type + ": " + speed));
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�׼Ǽӵ� [Spr��ȣ] [�̵�, ����, ����, ��������]"));
		}
	}

	// private static L1NpcInstance ����ġ���޴� = null;
	/*
	 * private void �ű�����(L1PcInstance gm, String param) { try{ StringTokenizer
	 * st = new StringTokenizer(param); String onoff = st.nextToken();
	 * if(onoff.equalsIgnoreCase("��")){ if(!GameServer.�ű�����_����ġ���޴�){// && ����ġ���޴�
	 * == null){ L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(
	 * "�Ƶ����忡 �ű������� ���۵Ǿ����ϴ�."), true);
	 * L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(
	 * "70�������� ����ġ �г��� �����Ǿ� ���� ���� �������� �����մϴ�."), true); //����ġ���޴� =
	 * L1SpawnUtil.spawn4(33435, 32795, (short)4, 4, 100395, 0, 0, 0);
	 * 
	 * GameServer.�ű�����_����ġ���޴� = true; }else{ gm.sendPackets(new S_SystemMessage(
	 * "���� �ű������� �������Դϴ�."), true); } }else if(onoff.equalsIgnoreCase("��")){
	 * GameServer.�ű�����_����ġ���޴� = false; //if(����ġ���޴� != null) //
	 * ����ġ���޴�.deleteMe(); //����ġ���޴� = null; gm.sendPackets(new S_SystemMessage(
	 * "�ű������� ���� �Ͽ����ϴ�."), true); }else{ gm.sendPackets(new S_SystemMessage(
	 * ".�ű����� [�� / ��]")); return; } }catch(Exception e){ gm.sendPackets(new
	 * S_SystemMessage(".�ű����� [�� / ��]")); } }
	 */

	private void clanMark(L1PcInstance gm, String param) {

		try {
			StringTokenizer st = new StringTokenizer(param);
			String onoff = st.nextToken();
			if (onoff.equalsIgnoreCase("��")) {
				gm.sendPackets(new S_�����ֽ�(gm, 2, true), true);
				gm.sendPackets(new S_�����ֽ�(gm, 0, true), true);
			} else if (onoff.equalsIgnoreCase("��")) {
				gm.sendPackets(new S_�����ֽ�(gm, 2, false), true);
				gm.sendPackets(new S_�����ֽ�(gm, 1, false), true);
			} else {
				gm.sendPackets(new S_SystemMessage(".���͸�ũ [�� / ��]"));
				return;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".���͸�ũ [�� / ��]"));
		}
	}

	private void mapBuff(L1PcInstance pc) {

		try {
			SpecialEventHandler.getInstance().doMapBuf(pc);
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".�ʹ���"));
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
					L1Teleport.�κ���(rob, loc[0], loc[1], (short) loc[2], true);
				} else {
					user.���� = false;
					L1Teleport.teleport(user, 33437, 32812, (short) 4, 5, true); // /
																					// ���Ե�
																					// ����
																					// (������������������)
					// user.setTelType(77);
					// user.sendPackets(new S_SabuTell(user), true);
				}
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".���� [�ɸ�]"));
		}
	}

	private void maphack(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			String on = st.nextToken();
			if (on.equalsIgnoreCase("��")) {
				pc.sendPackets(new S_Ability(3, true));
				pc.sendPackets(new S_SystemMessage("���� : [��]"));
			} else if (on.equals("��")) {
				pc.sendPackets(new S_Ability(3, false));
				pc.sendPackets(new S_SystemMessage("���� : [��]"));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".����  [��, ��]"));
		}
	}

	private void �Ǹ�üũ(L1PcInstance gm) {
		try {

			if (sellShopNotice) {
				sellShopNotice = false;
			} else
				sellShopNotice = true;
			gm.sendPackets(new S_SystemMessage("���� �Ǹ� üũ : " + sellShopNotice), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buyshop_user(L1PcInstance gm) {

		try {
			boolean ck = NpcBuyShop.getInstance().BuyShop_Show_or_Delete(false);
			gm.sendPackets(new S_SystemMessage("���� ���� ���� NPC >> " + (ck ? "[��]" : "[��]")), true);
			// ���Ի��� = !���Ի���;
			// NpcBuyShop.getInstance().BuyShop_Show_or_Delete(���Ի���);
			// gm.sendPackets(new S_SystemMessage("���� ���� NPC >> "+(���Ի��� ?
			// "[��]":"[��]")), true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buyshop(L1PcInstance gm) {

		try {
			boolean ck = NpcBuyShop.getInstance().BuyShop_Show_or_Delete(true);
			gm.sendPackets(new S_SystemMessage("���� ���� NPC >> " + (ck ? "[��]" : "[��]")), true);
			// ���Ի��� = !���Ի���;
			// NpcBuyShop.getInstance().BuyShop_Show_or_Delete(���Ի���);
			// gm.sendPackets(new S_SystemMessage("���� ���� NPC >> "+(���Ի��� ?
			// "[��]":"[��]")), true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sellshop(L1PcInstance gm) {

		try {
			boolean ck = NpcTradeShop.getInstance().BuyShop_Show_or_Delete();
			gm.sendPackets(new S_SystemMessage("�Ǹ� ���� NPC >> " + (ck ? "[��]" : "[��]")), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void gamble(L1PcInstance gm) {

		try {
			boolean ck = Gamble.get().Gamble_Show_or_Delete();
			gm.sendPackets(new S_SystemMessage("�׺� NPC >> " + (ck ? "[��]" : "[��]")), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void spawnmodel(L1PcInstance gm, String param) {
		StringTokenizer st = new StringTokenizer(param);
		int type = Integer.parseInt(st.nextToken(), 10);
		ModelSpawnTable.getInstance().insertmodel(gm, type);
		gm.sendPackets(new S_SystemMessage("�� �־���"), true);
	}

	private void showHelp(L1PcInstance gm) {
		gm.sendPackets(new S_GMCommands(1), true);
	}

	private void �޸𸮹�ȯ(L1PcInstance gm) {
		System.out.println("������ ������ ó���� ���� �մϴ�.");
		System.gc();
		System.out.println("�޸� ������ �Ϸ� �Ǿ����ϴ�.");
	}

	private void ����û��(L1PcInstance gm) {
		int count = 0;
		int ccount = 0;
		for (Object obj : L1World.getInstance().getObject()) {
			if (obj instanceof L1DollInstance) {
				L1DollInstance ���� = (L1DollInstance) obj;
				if (����.getMaster() == null) {
					count++;
					����.deleteMe();
				} else if (((L1PcInstance) ����.getMaster()).getNetConnection() == null) {
					ccount++;
					����.deleteMe();
				}
			}
		}
		gm.sendPackets(new S_SystemMessage("����û�� ���� - ����X: " + count + "  ��������: " + ccount), true);
	}

	private void �����ʱ�ȭ(L1PcInstance gm, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			String pcName = st.nextToken();
			String dun = st.nextToken();
			L1PcInstance player = L1World.getInstance().getPlayer(pcName);
			if (player == null) {
				gm.sendPackets(new S_SystemMessage(pcName + "�� �������� ĳ���� �ƴմϴ�."), true);
				return;
			}
			Timestamp nowday = new Timestamp(System.currentTimeMillis());

			if (dun.equalsIgnoreCase("�����Ѱ���")) {
				player.set�����Ѱ���time(1);
				player.set�����Ѱ���day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("������õ����")) {
				player.set������õ����time(1);
				player.set������õ����day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("���ž�߷�")) {
				player.setivorytime(1);
				player.setivoryday(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("���ž����")) {
				player.setivoryyaheetime(1);
				player.setivoryyaheeday(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("���") || dun.equalsIgnoreCase("����")) {
				player.setgirantime(1);
				player.setgiranday(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("���") || dun.equalsIgnoreCase("����")) {
				player.set���time(1);
				player.set���day(nowday);
				player.setpc���time(1);
				player.setpc���day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else

			if (dun.equalsIgnoreCase("����")) {
				player.set����time(1);
				player.set����day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("��") || dun.equalsIgnoreCase("����")) {
				player.set��time(1);
				player.set��day(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			} else if (dun.equalsIgnoreCase("���")) {
				player.setravatime(1);
				player.setravaday(nowday);
				player.getNetConnection().getAccount().updateDGTime();
			}

			player.sendPackets(new S_SystemMessage("GM �� ���� " + dun + " �ð��� �ʱ�ȭ �Ǿ����ϴ�."));
			gm.sendPackets(new S_SystemMessage(player.getName() + "�� " + dun + "�ð��� �ʱ�ȭ ���׽��ϴ�."));
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�����ʱ�ȭ [�̸�] [����] ���� �Է�."));
			gm.sendPackets(new S_SystemMessage("���ž�߷� / ���ž���� / ���,���� / ���,���� / ���� / ��,���� / ���"));
		}
	}

	private void ��ü����(L1PcInstance gm) {
		int cnt = 0;
		for (L1Object obj : L1World.getInstance().getObject()) {
			if (obj instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) obj;
				mon.die(gm);
				cnt++;
			}
		}
		gm.sendPackets(new S_SystemMessage("���� " + cnt + "������ �׿����ϴ�."), true);
	}

	private void ban(L1PcInstance gm, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int type = Integer.parseInt(st.nextToken(), 10);
			String account = st.nextToken();
			IpTable iptable = IpTable.getInstance();
			if (account.equalsIgnoreCase("")) {
				gm.sendPackets(new S_SystemMessage(".�� [1~4] [IP or ����]"), true);
				gm.sendPackets(new S_SystemMessage("1 = ������ / 3 = ������ ����"), true);
				gm.sendPackets(new S_SystemMessage("2 = IP�� / 4 = IP�� ����"), true);
				gm.sendPackets(new S_SystemMessage("5 = ����IP�� / 6 = ����IP�� ����"), true);
				gm.sendPackets(new S_SystemMessage("7 = ����IP��+���� / 8 = ����IP��+���� ����"), true);
			}
			switch (type) {
			case 1: {// ������
				Account.ban(account);
				gm.sendPackets(new S_SystemMessage(account + " ������ �з����׽��ϴ�."), true);
			}
				break;
			case 2: {// �����Ǻ�
				iptable.banIp(account); // BAN ����Ʈ�� IP�� �߰��Ѵ�.
				gm.sendPackets(new S_SystemMessage(account + " IP�� ���� ��ŵ�ϴ�."), true);
				// iptable.reload();
			}
				break;
			case 3: {// ������Ǯ��
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
				gm.sendPackets(new S_SystemMessage(account + " ������ ���� ���� ��ŵ�ϴ�."), true);
			}
				break;
			case 4: {// �����Ǻ�Ǯ��
				iptable.liftBanIp(account);
				// iptable.reload();
			}
				break;
			case 5: {// ���������Ǻ�
				if (account.lastIndexOf(".") + 1 != account.length()) {
					gm.sendPackets(new S_SystemMessage("123.456.789.  <-- �������� �Է��Ͽ��ּ���"), true);
					return;
				}
				for (int i = 1; i <= 255; i++) {
					iptable.banIp(account + i); // BAN ����Ʈ�� IP�� �߰��Ѵ�.
				}
				gm.sendPackets(new S_SystemMessage(account + "1~255 IP�� ���� ��ŵ�ϴ�."), true);
			}
				break;
			case 6: {// ���������Ǻ� Ǯ��
				if (account.lastIndexOf(".") + 1 != account.length()) {
					gm.sendPackets(new S_SystemMessage("123.456.789.  <-- �������� �Է��Ͽ��ּ���"), true);
					return;
				}
				for (int i = 1; i <= 255; i++) {
					iptable.liftBanIp(account + i); // BAN ����Ʈ�� IP�� �����Ѵ�.
				}
				gm.sendPackets(new S_SystemMessage(account + "1~255 IP�� ���� ��ŵ�ϴ�."), true);
			}
				break;
			case 7: {// ����������+���� ��
				if (account.lastIndexOf(".") + 1 != account.length()) {
					gm.sendPackets(new S_SystemMessage("123.456.789.  <-- �������� �Է��Ͽ��ּ���"), true);
					return;
				}
				Connection con = null;
				PreparedStatement pstm = null;
				try {
					con = L1DatabaseFactory.getInstance().getConnection();
					for (int i = 1; i <= 255; i++) {
						iptable.banIp(account + i); // BAN ����Ʈ�� IP�� �߰��Ѵ�.
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
				gm.sendPackets(new S_SystemMessage(account + "1~255 IP ���ܰ� �ش� ������ �з���ŵ�ϴ�."), true);
			}
				break;
			case 8: {// ����������+���� ��Ǯ��
				if (account.lastIndexOf(".") + 1 != account.length()) {
					gm.sendPackets(new S_SystemMessage("123.456.789.  <-- �������� �Է��Ͽ��ּ���"), true);
					return;
				}
				Connection con = null;
				PreparedStatement pstm = null;
				try {
					con = L1DatabaseFactory.getInstance().getConnection();
					for (int i = 1; i <= 255; i++) {
						iptable.liftBanIp(account + i); // BAN ����Ʈ�� IP�� �����Ѵ�.
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
				gm.sendPackets(new S_SystemMessage(account + "1~255 IP�� ������ ���� ��ŵ�ϴ�."), true);
			}
				break;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�� [1~4] [IP or ����]"), true);
			gm.sendPackets(new S_SystemMessage("1 = ������ / 3 = ������ ����"), true);
			gm.sendPackets(new S_SystemMessage("2 = IP�� / 4 = IP�� ����"), true);
			gm.sendPackets(new S_SystemMessage("5 = ����IP�� / 6 = ����IP�� ����"), true);
			gm.sendPackets(new S_SystemMessage("7 = ����IP��+���� / 8 = ����IP��+���� ����"), true);
		}
	}

	private void ��Ʈ(L1PcInstance gm) {
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
							target.getClanname() + " ���Ϳ��� " + pobyhouse.getHouseName() + "���� �����Ͽ����ϴ�."));
					for (L1PcInstance tc : TargetClan.getOnlineClanMember()) {
						tc.sendPackets(new S_SystemMessage("���Ӹ����ͷκ��� " + pobyhouse.getHouseName() + "���� ���� �޾ҽ��ϴ�."));
					}
				} else {
					pc.sendPackets(new S_SystemMessage(target.getName() + "���� ���Ϳ� ���� ���� �ʽ��ϴ�."));
				}
			} else {
				pc.sendPackets(new S_ServerMessage(73, pobyname));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".����Ʈ���� <���������Ϳ�> <����Ʈ��ȣ>"));
		}
	}

	private void �����ӵ�(L1PcInstance gm) {
		if (�����ӵ�üũ) {
			�����ӵ�üũ = false;
			gm.sendPackets(new S_SystemMessage("�����ӵ� üũ off"));
		} else {
			�����ӵ�üũ = true;
			gm.sendPackets(new S_SystemMessage("�����ӵ� üũ on"));
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
				gm.sendPackets(new S_SystemMessage(".�з� [�̸�] [1,2,3,4]"), true);
				gm.sendPackets(new S_SystemMessage("1 or ���� = �ܼ� ���� ����"), true);
				gm.sendPackets(new S_SystemMessage("2 = �ɸ��� ��"), true);
				gm.sendPackets(new S_SystemMessage("3 = �� ����"), true);
				gm.sendPackets(new S_SystemMessage("4 = �ɸ�,����,������ ��� �з�"), true);
				return;
			}
			L1PcInstance target = L1World.getInstance().getPlayer(pcName);
			if (target == null) {
				gm.sendPackets(new S_SystemMessage("�׷��� �̸��� ĳ���ʹ� ���峻���� �������� �ʽ��ϴ�."), true);
				return;
			}
			switch (type) {
			case 1:// �Ϲ�ƨ���
				target.sendPackets(new S_Disconnect(), true);
				target.getNetConnection().close();
				gm.sendPackets(new S_SystemMessage(pcName + "������ �������� ������ ���� ���׽��ϴ�."), true);
				break;
			case 2:// �ɸ��� ��Ű��
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
				gm.sendPackets(new S_SystemMessage(pcName + "������ �ɸ��͸� �� ���׽��ϴ�."), true);
				break;
			case 3:// �ɸ��� Ǯ��
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
				gm.sendPackets(new S_SystemMessage(pcName + "������ �� ���¸� ���� ���׽��ϴ�."), true);
				break;
			case 4:// ������ �з�
				Account.ban(target.getAccountName());
				gm.sendPackets(new S_SystemMessage(target.getAccountName() + " ������ �з����׽��ϴ�."), true);
				iptable.banIp(target.getNetConnection().getIp()); // BAN ����Ʈ��
																	// IP�� �߰��Ѵ�.
				// iptable.reload();
				gm.sendPackets(new S_SystemMessage(target.getNetConnection().getIp() + " IP�� ���� ��ŵ�ϴ�."), true);
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
				gm.sendPackets(new S_SystemMessage(pcName + "������ �ɸ��͸� �� ���׽��ϴ�."), true);
				break;
			default:
				target.sendPackets(new S_Disconnect(), true);
				target.getNetConnection().close();
				gm.sendPackets(new S_SystemMessage(pcName + "������ �������� ������ ���� ���׽��ϴ�."), true);
				break;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�߹� [�̸�] [1,2,3]"), true);
			gm.sendPackets(new S_SystemMessage("1 or ���� = �ܼ� ���� ����"), true);
			gm.sendPackets(new S_SystemMessage("2 = �ɸ��� ��"), true);
			gm.sendPackets(new S_SystemMessage("3 = �� ����"), true);
			gm.sendPackets(new S_SystemMessage("4 = �ɸ�,����,������ ��� �з�"), true);
		}
	}

	private static Random _random = new Random(System.nanoTime());
	// ���
	// ���
	// ȭ��
	private static final String ��Ʈ[] = { "��", "��!", "��~", "��!!", "��~!", "��", "��~", "��~~", "��" };

	class �κ���ó�ڼ� implements Runnable {
		public �κ���ó�ڼ�() {
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
						listner.sendPackets(new S_ChatPacket(rob, ��Ʈ[_random.nextInt(��Ʈ.length)], Opcodes.S_MESSAGE, 3),
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

	private void ����(L1PcInstance gm, String param) {
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

	private void ����2(L1PcInstance gm, String param) {
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
			gm.sendPackets(new S_SystemMessage(".�������� ����, ���, �ð� [��]  ��� �Է��� �ּ���. "));
		}
	}

	private void ��������(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String type = tokenizer.nextToken();
			if (type.equalsIgnoreCase("����")) {
				GameServer.getInstance().shutdown();
				GameServer.getInstance().shutdownWithCountdown(5);
			} else if (type.equalsIgnoreCase("���")) {
				GameServer.getInstance().abortShutdown();
			} else if (type.equalsIgnoreCase("�ð�")) {
				int secc = Integer.parseInt(tokenizer.nextToken(), 10);
				GameServer.getInstance().abortShutdown();
				GameServer.getInstance().shutdownWithCountdown(secc);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�������� ����, ���, �ð� [��]  ��� �Է��� �ּ���. "));
		}
	}

	private void ����(L1PcInstance gm, String param) {
		StringTokenizer tokenizer = new StringTokenizer(param);
		int oder = Integer.parseInt(tokenizer.nextToken(), 10);
		int cnt = Integer.parseInt(tokenizer.nextToken(), 10);
		try {
			L1BugBearRace.getInstance().addBetting(oder, cnt);
			gm.sendPackets(new S_SystemMessage(oder + "��° Ƽ�� " + cnt + "�� ����!! "), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".���� [Ƽ�Ϲ�ȣ(������ȣ)0~5] [����]"), true);
		}
	}

	private void �ù�(L1PcInstance gm, String param) {
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
				giranh = (girantemp - girantime) / 60 / 60;// �ð�
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
				ivoryh = (ivorytemp - ivorytime) / 60 / 60;// �ð�
			} else {
				ivoryh = 1;
				ivorym = 0;
				ivorys = 0;
			}
			pc.sendPackets(new S_SystemMessage("������� : " + giranh + "�ð� " + giranm + "�� " + girans + "��"), true);
			pc.sendPackets(new S_SystemMessage("���ž : " + ivoryh + "�ð� " + ivorym + "�� " + ivorys + "��"), true);
		} catch (Exception e) {
		}
	}

	private void �Ⱘ(L1PcInstance gm) {
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
				gm.sendPackets(new S_SystemMessage("��� ������ �Ⱘ�ð��� �ʱ�ȭ �߽��ϴ�."), true);
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
				pc.sendPackets(new S_SystemMessage("Ŀ�ǵ� " + lastCmd + " ��(��) ������մϴ�."), true);
				handleCommands(pc, lastCmd);
			} else {
				StringTokenizer token = new StringTokenizer(lastCmd);
				String cmd = token.nextToken() + " " + arg;
				pc.sendPackets(new S_SystemMessage("Ŀ�ǵ� " + cmd + " ��(��) ������մϴ�."), true);
				handleCommands(pc, cmd);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			pc.sendPackets(new S_SystemMessage(".����� Ŀ�ǵ忡��"), true);
		}
	}

	private void standBy7(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			String �� = tokenizer.nextToken();
			String �� = tokenizer.nextToken();
			L1PcInstance target = L1World.getInstance().getPlayer(pcName);
			if (target != null) {
				if (��.equalsIgnoreCase("�Ƶ�")) {
					if (��.equalsIgnoreCase("��")) {
						gm.sendPackets(new S_SystemMessage(target.getName() + "���� �Ƶ� ���ø� �մϴ�."), true);
						GameServer.getInstance().addbug(target.getName());
					} else if (��.equalsIgnoreCase("��")) {
						gm.sendPackets(new S_SystemMessage(target.getName() + "���� �Ƶ� ���ø� ���� �մϴ�."), true);
						GameServer.getInstance().removebug(target.getName());
					}
				} else if (��.equalsIgnoreCase("���")) {
					if (��.equalsIgnoreCase("��")) {
						gm.sendPackets(new S_SystemMessage(target.getName() + "���� �� ���ø� �մϴ�."), true);
						GameServer.getInstance().addbug(target.getName());
					} else if (��.equalsIgnoreCase("��")) {
						gm.sendPackets(new S_SystemMessage(target.getName() + "���� �� ���ø� ���� �մϴ�."), true);
						GameServer.getInstance().removebug(target.getName());
					}
				} else {
					gm.sendPackets(new S_SystemMessage(".���� [�ɸ���] [�Ƶ�/���] [��/��]"), true);
				}
			} else {
				gm.sendPackets(new S_SystemMessage("���峻�� �׷��ɸ��ʹ� �����ϴ�."), true);
			}
		} catch (Exception eee) {
			gm.sendPackets(new S_SystemMessage(".���� [�ɸ���] [�Ƶ�/���] [��/��]"), true);
		}
	}

	private void standBy8(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			L1PcInstance target = L1World.getInstance().getPlayer(pcName);
			if (target != null) {
				target.getNetConnection().��Ŷ�α� = true;
				gm.sendPackets(new S_SystemMessage(target.getName() + "���� �α׸� ����մϴ�."), true);
				gm.sendPackets(new S_SystemMessage(target.getNetConnection().getIp() + "�� �����ϴ� ��� �ɸ����� �ڵ� ����˴ϴ�."),
						true);
				GameServer.getInstance().addipl(target.getNetConnection().getIp());
			} else {
				gm.sendPackets(new S_SystemMessage("���峻�� �׷��ɸ��ʹ� �����ϴ�."), true);
			}
		} catch (Exception eee) {
			gm.sendPackets(new S_SystemMessage(".�α� [�ɸ���]"), true);
		}
	}

	// 1�� ���� ���
	/*
	 * private void standBy6(L1PcInstance gm, String param){ try{
	 * StringTokenizer tokenizer = new StringTokenizer(param); int id =
	 * Integer.parseInt(tokenizer.nextToken(), 10);
	 * GameServer.getInstance().�Ƶ�������=id; gm.sendPackets(new S_SystemMessage(
	 * "�Ƶ��˻� �������� "+id+" �� �����մϴ�."), true); }catch (Exception eee){
	 * gm.sendPackets(new S_SystemMessage(".�Ƶ������� [0~2000000000]"), true); } }
	 */
	// private void standBy9(L1PcInstance gm){
	// try{
	// gm.sendPackets(new S_SystemMessage("��Ŷ ��ȭ ����."));
	// Config.��Ŷ�α� = false;
	// }catch (Exception eee){
	// gm.sendPackets(new S_SystemMessage("��ɾ �� �Է� �� �ּ���."));
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
				gm.sendPackets(new S_SystemMessage("�׷��� �̸��� ĳ���ʹ� ���峻���� �������� �ʽ��ϴ�."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".��ġ���� [ĳ���͸�]�� �Է� ���ּ���."), true);
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
			gm.sendPackets(new S_SystemMessage("���Ǳ׸� ���ε� �Ͽ����ϴ�."), true);
			Config.load();
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".���Ǳ׷ε� ����"), true);
		}
	}

	private void standBy82(L1PcInstance gm) {
		try {
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.�����) {
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
			gm.sendPackets(new S_SystemMessage("������� ��� ������� �����մϴ�."), true);
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
				gm.sendPackets(new S_SystemMessage("�ش� NPC�� �߰ߵ��� �ʽ��ϴ�."), true);
				return;
			}
			gm.sendPackets(new S_SystemMessage(npc.get_name() + " ��(��) " + h + "�ð� " + m + "�� ���� �����մϴ�."), true);
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
			gm.sendPackets(new S_SystemMessage(".�̺����� [���ǽù�ȣor�̸�] [�ð�] [��]"), true);
		}
	}

	private void standBy82(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String name = tokenizer.nextToken();
			L1PcInstance ck = L1World.getInstance().getPlayer(name);
			if (ck != null) {
				gm.sendPackets(new S_SystemMessage("�������� ������ ���� �̸��� �Ұ���."), true);
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

				pc.����� = true;

				L1World.getInstance().storeObject(pc);
				L1World.getInstance().addVisibleObject(pc);
			}
			gm.sendPackets(new S_SystemMessage("����� ����!"), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�н� [�̸�]"), true);
		}
	}

	private void standBy81(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String name = tokenizer.nextToken();
			L1PcInstance ck = L1World.getInstance().getPlayer(name);
			if (ck != null) {
				gm.sendPackets(new S_SystemMessage("�������� ������ ���� �̸��� �Ұ���."), true);
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

			pc.����� = true;

			L1World.getInstance().storeObject(pc);
			L1World.getInstance().addVisibleObject(pc);

			gm.sendPackets(new S_SystemMessage("����� ����!"), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�н� [�̸�]"), true);
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
					gm.sendPackets(new S_SystemMessage("��� �ش� ������ �̸��� �������� �ʽ��ϴ�."), true);

				} else {
					gm.sendPackets(new S_SystemMessage(acname + "���� " + pcName + "���� �ɸ��͸� ���� �մϴ�."), true);
					CharacterTable.getInstance().deleteCharacter(acname, pcName);
					gm.sendPackets(new S_SystemMessage("�ش������� ���������� ���� �Ͽ����ϴ�."), true);
				}
			} catch (SQLException e) {

			} finally {
				SQLUtil.close(rs);
				SQLUtil.close(pstm);
				SQLUtil.close(con);
			}

		} catch (Exception eee) {
			gm.sendPackets(new S_SystemMessage(".�ɸ����� [�ɸ���]"), true);
		}
	}

	private void iconserch2(L1PcInstance pc, String s) {
		try {
			StringTokenizer st = new StringTokenizer(s);
			int id = Integer.parseInt(st.nextToken());
			int count = Integer.parseInt(st.nextToken());
			for (int i = id; i < count; i++) {
				pc.sendPackets(new S_SystemMessage("��ȣ: " + i));
				pc.sendPackets(new S_PacketBox(S_PacketBox.BUFFICON, i, 1));
			}
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage(".���� [����] [��]"));
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
					gm.sendPackets(new S_SystemMessage("DB�� " + pcName + " �ɸ����� ���� ���� �ʽ��ϴ�"), true);
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

					gm.sendPackets(new S_SystemMessage(pcName + " �� �����з��� ���� �Ͽ����ϴ�"), true);
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
			gm.sendPackets(new S_SystemMessage(".�з����� �ɸ������� �Է����ּ���."), true);
		}
	}

	private void packetbox(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int id = Integer.parseInt(st.nextToken(), 10);
			pc.sendPackets(new S_PacketBox(83, id), true);
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage(".��Ŷ�ڽ� [id] �Է�"), true);
		}
	}

	private void ä��(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();
			String pcName2 = tokenizer.nextToken();

			if (pcName.equalsIgnoreCase("����")) {
				if (pcName2.equalsIgnoreCase("��")) {
					Config.add����(gm);
					gm.sendPackets(new S_SystemMessage(".���� ä���� ����� �մϴ�."), true);
				} else if (pcName2.equalsIgnoreCase("��")) {
					Config.remove����(gm);
					gm.sendPackets(new S_SystemMessage(".���� ä�� ����͸� �ߴ� �մϴ�."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("��Ƽ")) {
				if (pcName2.equalsIgnoreCase("��")) {
					Config.add��Ƽ(gm);
					gm.sendPackets(new S_SystemMessage(".��Ƽ ä���� ����� �մϴ�."), true);
				} else if (pcName2.equalsIgnoreCase("��")) {
					Config.remove��Ƽ(gm);
					gm.sendPackets(new S_SystemMessage(".��Ƽ ä�� ����͸� �ߴ� �մϴ�."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("�Ӹ�")) {
				if (pcName2.equalsIgnoreCase("��")) {
					Config.add�Ӹ�(gm);
					gm.sendPackets(new S_SystemMessage(".�Ӹ� ä���� ����� �մϴ�."), true);
				} else if (pcName2.equalsIgnoreCase("��")) {
					Config.remove�Ӹ�(gm);
					gm.sendPackets(new S_SystemMessage(".�Ӹ� ä�� ����͸� �ߴ� �մϴ�."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("��þ")) {
				if (pcName2.equalsIgnoreCase("��")) {
					Config.add��þ(gm);
					gm.sendPackets(new S_SystemMessage(".��þ �α׸� ����� �մϴ�."), true);
				} else if (pcName2.equalsIgnoreCase("��")) {
					Config.remove��þ(gm);
					gm.sendPackets(new S_SystemMessage(".��þ �α� ����͸� �ߴ� �մϴ�."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("����")) {
				if (pcName2.equalsIgnoreCase("��")) {
					Config.add����(gm);
					gm.sendPackets(new S_SystemMessage(".���� �α׸� ����� �մϴ�."), true);
				} else if (pcName2.equalsIgnoreCase("��")) {
					Config.remove����(gm);
					gm.sendPackets(new S_SystemMessage(".���� �α� ����͸� �ߴ� �մϴ�."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("����")) {
				if (pcName2.equalsIgnoreCase("��")) {
					Config.add����(gm);
					gm.sendPackets(new S_SystemMessage(".���� �α׸� ����� �մϴ�."), true);
				} else if (pcName2.equalsIgnoreCase("��")) {
					Config.remove����(gm);
					gm.sendPackets(new S_SystemMessage(".���� �α� ����͸� �ߴ� �մϴ�."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("����")) {
				if (pcName2.equalsIgnoreCase("��")) {
					Config.add����(gm);
					gm.sendPackets(new S_SystemMessage(".���� �α׸� ����� �մϴ�."), true);
				} else if (pcName2.equalsIgnoreCase("��")) {
					Config.remove����(gm);
					gm.sendPackets(new S_SystemMessage(".���� �α� ����͸� �ߴ� �մϴ�."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
					return;
				}
			} else if (pcName.equalsIgnoreCase("��ü")) {
				if (pcName2.equalsIgnoreCase("��")) {
					Config.add��ü(gm);
					gm.sendPackets(new S_SystemMessage(".��� ä���� ����� �մϴ�."), true);
				} else if (pcName2.equalsIgnoreCase("��")) {
					Config.remove��ü(gm);
					gm.sendPackets(new S_SystemMessage(".��� ä�� ����͸� �ߴ� �մϴ�."), true);
				} else {
					gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
					return;
				}
			} else {
				gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
				return;
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".ä�� [����/��Ƽ/�Ӹ�/��þ/����/����/����/��ü] [��/��]"), true);
		}
	}

	private void autoshop(L1PcInstance gm, String param) {
		if (param.equalsIgnoreCase("��")) {
			AutoShopManager.getInstance().isAutoShop(true);
			gm.sendPackets(new S_SystemMessage("���λ��� ��"), true);
		} else if (param.equalsIgnoreCase("��")) {
			AutoShopManager.getInstance().isAutoShop(false);
			gm.sendPackets(new S_SystemMessage("���λ��� ��"), true);
		} else {
			gm.sendPackets(new S_SystemMessage(".���λ��� [�� or ��] �Է�"), true);
		}
	}

	private void �Ƴ�����(L1PcInstance gm, String param) {
		if (param.equalsIgnoreCase("��")) {
			if (Config.�Ƴ����� == null) {
				L1NpcInstance npc = L1SpawnUtil.spawn4(33435, 32804, (short) 4, 0, 100880, 0, 0, 0);
				if (npc != null) {
					Config.�Ƴ����� = npc;
				}

				gm.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "�Ƴ�� ������ ���� �մϴ�."), true);
			} else {
				gm.sendPackets(new S_SystemMessage("�Ƴ�� ������ �̹� ������ �Դϴ�."), true);
			}
		} else if (param.equalsIgnoreCase("��")) {
			if (Config.�Ƴ����� != null) {
				Config.�Ƴ�����.deleteMe();
				Config.�Ƴ����� = null;
				gm.sendPackets(new S_SystemMessage("�Ƴ�� ������ ���� �մϴ�."), true);
			} else {
				gm.sendPackets(new S_SystemMessage("�Ƴ�� ������ ã�� �� �����ϴ�."), true);
			}
		} else {
			gm.sendPackets(new S_SystemMessage(".�Ƴ����� [�� or ��] �Է�"), true);
		}
	}

	private void nocall(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();

			L1PcInstance target = null; // q
			target = L1World.getInstance().getPlayer(pcName);
			if (target != null) { // Ÿ��

				L1Teleport.teleport(target, 33437, 32812, (short) 4, 5, true); // /
																				// ���Ե�
																				// ����
																				// (������������������)
			} else {
				gm.sendPackets(new S_SystemMessage("���������� �ʴ� ���� ID �Դϴ�."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".���� (�����ɸ��͸�) ���� �Է��� �ּ���."), true);
		}
	}

	private void hellcall(L1PcInstance gm, String param) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(param);
			String pcName = tokenizer.nextToken();

			L1PcInstance target = null; // q
			target = L1World.getInstance().getPlayer(pcName);
			if (target != null) { // Ÿ��

				target.���� = true;

				L1Teleport.teleport(target, 32928, 32864, (short) 6202, 5, true); // /
																					// ���Ե�
																					// ����
																					// (������������������)

			} else {
				gm.sendPackets(new S_SystemMessage("���������� �ʴ� ���� ID �Դϴ�."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".���� (�����ɸ��͸�) ���� �Է��� �ּ���."), true);
		}
	}

	private void allpresent(L1PcInstance gm, String param) {
		try {
			StringTokenizer kwang = new StringTokenizer(param);
			String item = kwang.nextToken();
			if (item.equalsIgnoreCase("����")) {
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (!pc.isPrivateShop() && pc.getNetConnection() != null) {
						L1Item tempItem = ItemTable.getInstance().getTemplate(41159);
						if (tempItem != null) {
							PresentGive(tempItem, 41159, 300, 0, pc);
						}
					}
				}
				return;
			} else if (item.equalsIgnoreCase("�ڸ�")) {
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
						 * "��ڴԲ��� ��ü�������� ������[ "+ item.getName()
						 * +(item.getCount() > 0 ? (" ("+item.getCount()+")") :
						 * "")+" ]�� �־����ϴ�."), true);
						 */
					}
				}
			}
		} catch (Exception exception) {
			gm.sendPackets(new S_SystemMessage(".��ü���� ������ID ��þƮ�� �����ۼ��� �Է��� �ּ���."), true);
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
				// target.sendPackets(new S_ServerMessage(403, // %0�� �տ� �־����ϴ�.
				// item.getLogName()));
				target.sendPackets(new S_SystemMessage("��� ���� : ��ſ��� " + item.getName()
						+ (item.getCount() > 0 ? (" (" + item.getCount() + ")") : "") + " �� �־����ϴ�."), true);
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
					// target.sendPackets(new S_ServerMessage(403, // %0�� �տ�
					// �־����ϴ�.
					// item.getLogName()));
					target.sendPackets(new S_SystemMessage("��� ���� : ��ſ��� " + item.getName()
							+ (item.getCount() > 0 ? (" (" + item.getCount() + ")") : "") + " �� �־����ϴ�."), true);
				}
			}
		}
	}

	// ���� ����ġ ������ �Ƶ��� ����
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
				gm.sendPackets(new S_SystemMessage("[����ġ = " + exp + " ��]"), true);
				gm.sendPackets(new S_SystemMessage("[������ = " + item + " ��]"), true);
				gm.sendPackets(new S_SystemMessage("[�Ƶ��� = " + adena + " ��]�� ���� �Ǿ����ϴ�"), true);
			} else {// �Է��� ���� ���ڰ� �ƴҰ��
				gm.sendPackets(new S_SystemMessage("�߸��� ���� �Է��ϼ̽��ϴ�. ��� ���ڷ� �Է����ּ���"), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�������� [����ġ][������][�Ƶ���]�� �Է� ���ּ���."), true);
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
				gm.sendPackets(new S_SystemMessage("[���� ��æ�� = " + weapon + " ��]"), true);
				gm.sendPackets(new S_SystemMessage("[�� ��æ�� = " + armor + " ��]"), true);
			} else {// �Է��� ���� ���ڰ� �ƴҰ��
				gm.sendPackets(new S_SystemMessage("�߸��� ���� �Է��ϼ̽��ϴ�. ��� ���ڷ� �Է����ּ���"), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".��æ���� [������æ��][����æ��][��ű���æ��]�� �Է� ���ּ���."), true);
		}
	}

	// �Է°��� �������� üũ
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

	// �Է¹��� ���� ����Ÿ������ ��ȯ
	private double ConvertToDouble(String param) {
		Float Temp = Float.parseFloat(param);
		double doubleValue = (double) Temp;
		return doubleValue;
	}

	private void CheckAllRates(L1PcInstance gm) {
		gm.sendPackets(new S_SystemMessage("***** ���� ���� ���� *****"), true);
		gm.sendPackets(new S_SystemMessage("[����ġ]: " + Config.RATE_XP + "��"), true);
		gm.sendPackets(new S_SystemMessage("[������]: " + Config.RATE_DROP_ITEMS + "��"), true);
		gm.sendPackets(new S_SystemMessage("[�Ƶ���]: " + Config.RATE_DROP_ADENA + "��"), true);
		gm.sendPackets(new S_SystemMessage("[������æ��]: " + Config.ENCHANT_CHANCE_WEAPON + "��"), true);
		gm.sendPackets(new S_SystemMessage("[����æ��]: " + Config.ENCHANT_CHANCE_ARMOR + "��"), true);
	}

	private void �κ�(L1PcInstance gm, String param) {
		try {
			StringTokenizer stringtokenizer = new StringTokenizer(param);
			String s = stringtokenizer.nextToken();

			L1PcInstance pc = L1World.getInstance().getPlayer(s);
			if (pc == null) {
				gm.sendPackets(new S_SystemMessage("���� ���� �ɸ��� �ƴմϴ�."), true);
				return;
			}

			List<L1ItemInstance> items = gm.getInventory().getItems();
			for (L1ItemInstance item : items) {
				gm.sendPackets(new S_DeleteInventoryItem(item), true);
			}
			List<L1ItemInstance> tempitem = pc.getInventory().getItems();
			gm.sendPackets(new S_TempInv(tempitem), true);
		} catch (Exception exception21) {
			gm.sendPackets(new S_SystemMessage(".�κ� ĳ���͸�"), true);
		}
	}

	private void chainfo(L1PcInstance gm, String param) {
		try {
			StringTokenizer stringtokenizer = new StringTokenizer(param);
			String s = stringtokenizer.nextToken();
			L1PcInstance pc = L1World.getInstance().getPlayer(s);
			if (pc == null) {
				gm.sendPackets(new S_SystemMessage("���� ���� �ɸ��� �ƴմϴ�."), true);
			}
			gm.sendPackets(new S_Chainfo(pc), true);
		} catch (Exception exception21) {
			gm.sendPackets(new S_SystemMessage(".���� ĳ���͸�"), true);
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
				gm.sendPackets(new S_SystemMessage("�ش�ĳ���� ä���� ���� �߽��ϴ�."), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".ä��Ǯ�� ĳ���͸� �̶�� �Է��� �ּ���."), true);
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
				gm.sendPackets(new S_SystemMessage(clan_name1 + "������ �������� �ʽ��ϴ�."), true);
				return;
			}

			if (clan2 == null) {
				gm.sendPackets(new S_SystemMessage(clan_name2 + "������ �������� �ʽ��ϴ�."), true);
				return;
			}

			for (L1War war : L1World.getInstance().getWarList()) {
				if (war.CheckClanInSameWar(clan_name1, clan_name2) == true) {
					gm.sendPackets(new S_SystemMessage("[" + clan_name1 + "]���Ͱ� [" + clan_name2 + "]������ ���� ���� �� �Դϴ�."),
							true);
					return;
				}
			}

			L1War war = new L1War();
			war.handleCommands(2, clan_name1, clan_name2); // ������ ����
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				pc.sendPackets(new S_SystemMessage("[" + clan_name1 + "]���Ͱ� [" + clan_name2 + "]������ ������ ���� �Ǿ����ϴ�."),
						true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�������� �����̸� �����̸�"), true);
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
				gm.sendPackets(new S_SystemMessage(clan_name1 + "������ �������� �ʽ��ϴ�."), true);
				return;
			}

			if (clan2 == null) {
				gm.sendPackets(new S_SystemMessage(clan_name2 + "������ �������� �ʽ��ϴ�."), true);
				return;
			}

			for (L1War war : L1World.getInstance().getWarList()) {
				if (war.CheckClanInSameWar(clan_name1, clan_name2) == true) {
					war.CeaseWar(clan_name1, clan_name2);
					for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
						pc.sendPackets(
								new S_SystemMessage("[" + clan_name1 + "]���Ͱ� [" + clan_name2 + "]������ ������ ���� �Ǿ����ϴ�."),
								true);
					}
					return;
				}
			}
			gm.sendPackets(new S_SystemMessage("[" + clan_name1 + "]���Ͱ� [" + clan_name2 + "]������ ���� ���������� �ʽ��ϴ�."), true);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�������� �����̸� �����̸�"), true);
		}
	}

	private void searchDatabase(L1PcInstance gm, String param) { // �˻�����߰�

		try {
			StringTokenizer tok = new StringTokenizer(param);
			int type = Integer.parseInt(tok.nextToken());
			String name = tok.nextToken();

			searchObject(gm, type, "%" + name + "%");

		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�˻� [0~5] [�̸�]�� �Է� ���ּ���."), true);
			gm.sendPackets(new S_SystemMessage("0=����, 1=����, 2=����, 3=npc"), true);
		}
	}

	private void serversave(L1PcInstance pc) {// �˻��� �߰�
		Saveserver();// �������̺� �޼ҵ� ����
		pc.sendPackets(new S_SystemMessage("���������� �Ϸ�Ǿ����ϴ�."), true);// �������� �˷��ְ�
	}

	/** �������� **/
	private void Saveserver() {
		/** ��ü�÷��̾ ȣ�� **/
		for (L1PcInstance player : L1World.getInstance().getAllPlayers()) {
			try {
				/** �Ǿ��������ְ� **/
				player.save();
				/** �κ��� �����ϰ� **/
				player.saveInventory();
			} catch (Exception ex) {
				/** ���� �κ����� **/
				player.saveInventory();
			}
		}
	}

	/** �������� **/

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
					gm.sendPackets(new S_SystemMessage("�̹� ������ �ֽ��ϴ�."), true);
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
					gm.sendPackets(new S_SystemMessage("���� �߰��� �Ϸ�Ǿ����ϴ�."), true);
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

				gm.sendPackets(new S_SystemMessage("�� [" + count + "]���� �����Ͱ� �˻��Ǿ����ϴ�."), true);
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
			if (!Character.isDigit(str.charAt(i)) // ���ڰ� �ƴ϶��
					&& Character.isLetterOrDigit(str.charAt(i)) // Ư�����ڶ��
					&& !Character.isUpperCase(str.charAt(i)) // �빮�ڰ� �ƴ϶��
					&& !Character.isLowerCase(str.charAt(i))) { // �ҹ��ڰ� �ƴ϶��
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
				gm.sendPackets(new S_SystemMessage("�Է��Ͻ� �������� �ڸ����� �ʹ� ª���ϴ�."), true);
				gm.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."), true);
				return;
			}
			if (passwd.length() < 4) {
				gm.sendPackets(new S_SystemMessage("�Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ª���ϴ�."), true);
				gm.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."), true);
				return;
			}

			if (passwd.length() > 12) {
				gm.sendPackets(new S_SystemMessage("�Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ��ϴ�."), true);
				gm.sendPackets(new S_SystemMessage("�ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."), true);
				return;
			}

			if (isDisitAlpha(passwd) == false) {
				gm.sendPackets(new S_SystemMessage("��ȣ�� ������ �ʴ� ���ڰ� ���� �Ǿ� �ֽ��ϴ�."), true);
				return;
			}
			AddAccount(gm, user, passwd, "127.0.0.1", "127.0.0.1");
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�����߰� [������] [��ȣ] �Է�"), true);
		}
	}

	private void changepassword(L1PcInstance gm, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String user = tok.nextToken();
			String oldpasswd = tok.nextToken();
			String newpasswd = tok.nextToken();

			if (user.length() < 4) {
				gm.sendPackets(new S_SystemMessage("�Է��Ͻ� �������� �ڸ����� �ʹ� ª���ϴ�."), true);
				gm.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."), true);
				return;
			}
			if (newpasswd.length() < 4) {
				gm.sendPackets(new S_SystemMessage("�Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ª���ϴ�."), true);
				gm.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."), true);
				return;
			}
			if (newpasswd.length() > 12) {
				gm.sendPackets(new S_SystemMessage("�Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ��ϴ�."), true);
				gm.sendPackets(new S_SystemMessage("�ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."), true);
				return;
			}

			if (isDisitAlpha(newpasswd) == false) {
				gm.sendPackets(new S_SystemMessage("��ȣ�� ������ �ʴ� ���ڰ� ���� �Ǿ� �ֽ��ϴ�."), true);
				return;
			}
			chkpassword(gm, user, oldpasswd, newpasswd);
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".������� [����] [������] [�ٲܺ��] �Է�"), true);
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
					gm.sendPackets(new S_SystemMessage("�Է��Ͻ� ������ �������� ���� ���� �ʽ��ϴ�."), true);
					return;
				}

				if (!isPasswordTrue(password, oldpassword)) {
					// System.out.println("���� ��� : " +
					// oldpassword+" - üũ ��� : "+password);
					gm.sendPackets(new S_SystemMessage("���� �������� ��й�ȣ�� ��ġ���� �ʽ��ϴ�. "), true);
					gm.sendPackets(new S_SystemMessage("�ٽ� Ȯ���Ͻð� ������ �ּ���."), true);
					return;
				} else {
					String sqlstr = "UPDATE accounts SET password=password(?) WHERE login=?";
					pstm = con.prepareStatement(sqlstr);
					pstm.setString(1, newpassword);
					pstm.setString(2, account);
					pstm.executeUpdate();
					gm.sendPackets(new S_SystemMessage("������ : " + account + " / �ٲ��й�ȣ : " + newpassword), true);
					gm.sendPackets(new S_SystemMessage("��й�ȣ ������ ���������� �Ϸ�Ǿ����ϴ�."), true);
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

	// �н����� �´��� ���� ����
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
			if (_rtnPwd.equals(Password)) { // �����ϴٸ�
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

	private void ȭ�齺��(L1PcInstance pc, String param) {
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
			pc.sendPackets(new S_SystemMessage(".�� [����] �Է�"), true);
		}
	}

	private void ��Ʈ(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int id = Integer.parseInt(st.nextToken(), 10);

			pc.sendPackets(new S_ServerMessage(id, null), true);
		} catch (Exception exception) {
			exception.printStackTrace();
			pc.sendPackets(new S_SystemMessage(".�� [����] �Է�"), true);
		}
	}

	private void �޼���(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int type = Integer.parseInt(st.nextToken(), 10);
			String msg = st.nextToken();

			pc.sendPackets(new S_ServerMessage(type, msg), true);
		} catch (Exception exception) {
			exception.printStackTrace();
			pc.sendPackets(new S_SystemMessage(".�� [����] �Է�"), true);
		}
	}

	private void ���ڵ�(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int type = Integer.parseInt(st.nextToken(), 10);
			Config.test222 = type;
		} catch (Exception exception) {
			exception.printStackTrace();
			pc.sendPackets(new S_SystemMessage(".�� [����] �Է�"), true);
		}
	}

	public static int �ڿ� = 0;

	private void CodeTest(L1PcInstance pc, String param) {
		try {
			StringTokenizer st = new StringTokenizer(param);
			int codetest = Integer.parseInt(st.nextToken(), 10);
			�ڿ� = codetest;
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage(".�ڵ� [����] �Է�"), true);
		}
	}

	private void Clear(L1PcInstance gm) {
		for (L1Object obj : L1World.getInstance().getVisibleObjects(gm, 15)) { // 10
																				// ����
																				// ����
																				// ������Ʈ��
																				// ã�Ƽ�
			if (obj instanceof L1MonsterInstance) { // ���Ͷ��
				L1MonsterInstance npc = (L1MonsterInstance) obj;
				// npc.testonNpcAI(gm);
				npc.receiveDamage(gm, 50000); // ������

				/*
				 * npc.die(gm); npc.die(gm); npc.die(gm); npc.die(gm);
				 * npc.die(gm);
				 */

				gm.sendPackets(new S_SkillSound(obj.getId(), 1815), true);
				Broadcaster.broadcastPacket(gm, new S_SkillSound(obj.getId(), 1815), true);
			} else if (obj instanceof L1PcInstance) { // pc���
				L1PcInstance player = (L1PcInstance) obj;
				if (!(obj instanceof L1RobotInstance)) {
					player.receiveDamage(player, 0, false); // ������
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
			gm.sendPackets(new S_SystemMessage(".�������� [���̸��α���]"), true);
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
					new S_SystemMessage(String.format(".�����ð��� %s ���� ���� �Ǿ����ϴ�.", formatter.format(cal.getTime()))),
					true);
			gm.sendPackets(new S_SystemMessage(param + "�� �� ������ �����մϴ�."), true);
			formatter = null;
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�������� [���̸��α���] [��]"), true);
		}
	}

	private void ��(L1PcInstance gm, String param) {
		StringTokenizer tokenizer = new StringTokenizer(param);
		try {
			String type = tokenizer.nextToken();
			if (type.equalsIgnoreCase("����")) {
				try {
					String �̸� = tokenizer.nextToken();
					if (�̸�.equalsIgnoreCase("��ü")) {
						int _cnt = 0;
						restartBot = false;
						clanBot = false;
						fishBot = false;
						bugbearBot = false;
						huntBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							_cnt++;
							L1World world = L1World.getInstance();
							if (bot.��ɺ�) {
								if (bot.���_����)
									continue;
								else {
									bot.����();
									continue;
								}
							}
							bot._���������� = true;
							if (!bot.������ && !bot.���ú�) {
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
							// if(!bot.������ && !bot.���ú�)
							// bot.setDead(true);
							if (bot.getClanid() != 0) {
								bot.getClan().removeOnlineClanMember(bot.getName());
							}
							Robot.Doll_Delete(bot);
						}
						gm.sendPackets(new S_SystemMessage(_cnt + "������ �κ��� ���� ���׽��ϴ�."), true);
					} else if (�̸�.equalsIgnoreCase("���")) {
						int _cnt = 0;
						huntBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.��ɺ� || bot.���_����)
								continue;
							_cnt++;
							bot.����();
						}
						gm.sendPackets(new S_SystemMessage(_cnt + "������ �κ��� ���� ���׽��ϴ�."), true);
					} else if (�̸�.equalsIgnoreCase("����")) {
						int _cnt = 0;
						restartBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.������)
								continue;
							_cnt++;
							bot._���������� = true;
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
						gm.sendPackets(new S_SystemMessage(_cnt + "������ �κ��� ���� ���׽��ϴ�."), true);
					} else if (�̸�.equalsIgnoreCase("����")) {
						int _cnt = 0;
						bugbearBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.���溿)
								continue;
							_cnt++;
							bot._���������� = true;
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
							bot.���溿_Ÿ�� = 0;
							Robot.Doll_Delete(bot);
						}
						gm.sendPackets(new S_SystemMessage(_cnt + "������ �κ��� ���� ���׽��ϴ�."), true);
					} else if (�̸�.equalsIgnoreCase("����")) {
						int _cnt = 0;
						fishBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.���ú�)
								continue;
							_cnt++;
							bot._���������� = true;
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
						gm.sendPackets(new S_SystemMessage(_cnt + "������ �κ��� ���� ���׽��ϴ�."), true);
					} else if (�̸�.equalsIgnoreCase("����")) {
						int _cnt = 0;
						clanBot = false;
						for (L1RobotInstance bot : L1World.getInstance().getAllRobot()) {
							if (!bot.���Ա���)
								continue;
							_cnt++;
							L1World world = L1World.getInstance();
							bot._���������� = true;
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
						gm.sendPackets(new S_SystemMessage(_cnt + "������ �κ��� ���� ���׽��ϴ�."), true);
					} else {
						L1PcInstance �κ� = L1World.getInstance().getPlayer(�̸�);
						if (�κ� != null) {
							if (�κ� instanceof L1RobotInstance) {
								L1World world = L1World.getInstance();
								if (((L1RobotInstance) �κ�).��ɺ�) {
									if (((L1RobotInstance) �κ�).���_����)
										return;
									else {
										Robot_Hunt.getInstance().delay_spawn(((L1RobotInstance) �κ�).��ɺ�_��ġ, 60000);
										((L1RobotInstance) �κ�).����();
										return;
									}
								}
								((L1RobotInstance) �κ�)._���������� = true;
								if (!((L1RobotInstance) �κ�).������ && !((L1RobotInstance) �κ�).���ú�) {
									for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(�κ�)) {
										pc.sendPackets(new S_RemoveObject(�κ�), true);
										pc.getNearObjects().removeKnownObject(�κ�);
									}
									world.removeVisibleObject(�κ�);
									world.removeObject(�κ�);
									�κ�.getNearObjects().removeAllKnownObjects();
								}
								�κ�.stopHalloweenRegeneration();
								�κ�.stopPapuBlessing();
								�κ�.stopLindBlessing();
								�κ�.stopHalloweenArmorBlessing();
								�κ�.stopAHRegeneration();
								�κ�.stopHpRegenerationByDoll();
								�κ�.stopMpRegenerationByDoll();
								�κ�.stopSHRegeneration();
								�κ�.stopMpDecreaseByScales();
								�κ�.stopEtcMonitor();
								((L1RobotInstance) �κ�).���溿_Ÿ�� = 0;
								((L1RobotInstance) �κ�).loc = null;
								if (�κ�.getClanid() != 0) {
									�κ�.getClan().removeOnlineClanMember(�κ�.getName());
								}
								Robot.Doll_Delete((L1RobotInstance) �κ�);
								gm.sendPackets(new S_SystemMessage(�̸� + "�κ��� ���� ��ŵ�ϴ�."), true);
							} else {
								gm.sendPackets(new S_SystemMessage("�κ��� �ƴմϴ�."), true);
							}
						} else {
							gm.sendPackets(new S_SystemMessage("�������� �κ��� �̸��� �ƴմϴ�."), true);
						}

					}
				} catch (Exception e) {
					gm.sendPackets(new S_SystemMessage("�� ���� �̸�/����/��ü/����"), true);
				}
			} else if (type.equalsIgnoreCase("����")) {
				try {
					String ���̸� = tokenizer.nextToken();
					String �̸� = tokenizer.nextToken();
					String ȣĪ = tokenizer.nextToken();
					L1Clan ���� = L1World.getInstance().getClan(���̸�);
					if (���� == null) {
						gm.sendPackets(new S_SystemMessage(���̸� + "������ �������� �ʽ��ϴ�."), true);
						return;
					}
					L1PcInstance �κ� = L1World.getInstance().getPlayer(�̸�);
					if (�κ� != null) {
						if (�κ� instanceof L1RobotInstance) {
							L1RobotInstance rob = (L1RobotInstance) �κ�;
							rob.updateclan(���̸�, ����.getClanId(), ȣĪ, true);
							rob.setClanid(����.getClanId());
							rob.setClanname(���̸�);
							rob.setClanRank(2);
							rob.setTitle(ȣĪ);
							����.addClanMember(rob.getName(), rob.getClanRank(), rob.getLevel(), rob.getType(),
									rob.getMemo(), rob.getOnlineStatus(), rob);
							Broadcaster.broadcastPacket(rob, new S_CharTitle(rob.getId(), ȣĪ), true);
							Broadcaster.broadcastPacket(rob, new S_Emblem(rob.getClanid()), true);
						} else {
							gm.sendPackets(new S_SystemMessage("�κ��� �ƴմϴ�."), true);
							return;
						}
					} else {
						gm.sendPackets(new S_SystemMessage("�������� �κ��� �̸��� �ƴմϴ�."), true);
						return;
					}
				} catch (Exception e) {
					gm.sendPackets(new S_SystemMessage(".�� ���� [���̸�] [�̸�] [ȣĪ]"), true);
				}
			} else if (type.equalsIgnoreCase("�߹�")) {
				try {
					String �̸� = tokenizer.nextToken();
					L1PcInstance �κ� = L1World.getInstance().getPlayer(�̸�);
					if (�κ� != null) {
						if (�κ� instanceof L1RobotInstance) {
							L1RobotInstance rob = (L1RobotInstance) �κ�;
							rob.updateclan("", 0, "", false);
							rob.setClanid(0);
							rob.setClanname("");
							rob.setTitle("");
							rob.setClanRank(0);
							Broadcaster.broadcastPacket(rob, new S_CharTitle(rob.getId(), ""), true);
							Broadcaster.broadcastPacket(rob, new S_Emblem(0), true);
						} else {
							gm.sendPackets(new S_SystemMessage("�κ��� �ƴմϴ�."), true);
							return;
						}
					} else {
						gm.sendPackets(new S_SystemMessage("�������� �κ��� �̸��� �ƴմϴ�."), true);
						return;
					}
				} catch (Exception e) {
					gm.sendPackets(new S_SystemMessage(".�� �߹� [�̸�]"), true);
				}
			} else if (type.equalsIgnoreCase("�з�")) {
				String �̸� = tokenizer.nextToken();
				L1PcInstance �κ� = L1World.getInstance().getPlayer(�̸�);
				if (�κ� != null) {
					if (�κ� instanceof L1RobotInstance) {
						L1World world = L1World.getInstance();
						((L1RobotInstance) �κ�)._���������� = true;
						for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(�κ�)) {
							pc.sendPackets(new S_RemoveObject(�κ�), true);
							pc.getNearObjects().removeKnownObject(�κ�);
						}
						world.removeVisibleObject(�κ�);
						world.removeObject(�κ�);
						�κ�.getNearObjects().removeAllKnownObjects();
						�κ�.stopHalloweenRegeneration();
						�κ�.stopPapuBlessing();
						�κ�.stopLindBlessing();
						�κ�.stopHalloweenArmorBlessing();
						�κ�.stopAHRegeneration();
						�κ�.stopHpRegenerationByDoll();
						�κ�.stopMpRegenerationByDoll();
						�κ�.stopSHRegeneration();
						�κ�.stopMpDecreaseByScales();
						�κ�.stopEtcMonitor();
						�κ�.setDead(true);
						gm.sendPackets(new S_SystemMessage(�̸� + "�κ��� �з� ��ŵ�ϴ�."), true);
						((L1RobotInstance) �κ�).updateban(true);
					} else {
						gm.sendPackets(new S_SystemMessage("�κ��� �ƴմϴ�."), true);
					}
				} else {
					gm.sendPackets(new S_SystemMessage("�������� �κ��� �̸��� �ƴմϴ�."), true);
				}
			} else if (type.equalsIgnoreCase("��")) {
				�κ���ó�ڼ� bot = new �κ���ó�ڼ�();
				GeneralThreadPool.getInstance().execute(bot);
			} else {
				gm.sendPackets(new S_SystemMessage(".�� ���� / �з�"), true);
			}
		} catch (Exception e) {
			gm.sendPackets(new S_SystemMessage(".�� ���� / �з�"), true);
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