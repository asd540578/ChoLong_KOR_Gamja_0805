/* This program is free software; you can redistribute it and/or modify
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

import static l1j.server.server.model.skill.L1SkillId.ADDITIONAL_FIRE;
import static l1j.server.server.model.skill.L1SkillId.ANTA_MAAN;
import static l1j.server.server.model.skill.L1SkillId.BERSERKERS;
import static l1j.server.server.model.skill.L1SkillId.BIRTH_MAAN;
import static l1j.server.server.model.skill.L1SkillId.CLEAR_MIND;
import static l1j.server.server.model.skill.L1SkillId.CONCENTRATION;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_0_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_0_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_14_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_14_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_16_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_16_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_22_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_22_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_6_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_6_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_8_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_8_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�߰��;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�ѿ�;
import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import static l1j.server.server.model.skill.L1SkillId.DECREASE_WEIGHT;
import static l1j.server.server.model.skill.L1SkillId.DISEASE;
import static l1j.server.server.model.skill.L1SkillId.DRAGON_SKIN;
import static l1j.server.server.model.skill.L1SkillId.DRESS_EVASION;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.ELEMENTAL_FALL_DOWN;
import static l1j.server.server.model.skill.L1SkillId.ELEMENTAL_FIRE;
import static l1j.server.server.model.skill.L1SkillId.ELEMENTAL_PROTECTION;
import static l1j.server.server.model.skill.L1SkillId.ERASE_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.EXP_POTION;
import static l1j.server.server.model.skill.L1SkillId.EXP_POTION2;
import static l1j.server.server.model.skill.L1SkillId.EXP_POTION3;
import static l1j.server.server.model.skill.L1SkillId.FAFU_MAAN;
import static l1j.server.server.model.skill.L1SkillId.FEAR;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_A;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_B;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_C;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_D;
import static l1j.server.server.model.skill.L1SkillId.GUARD_BREAK;
import static l1j.server.server.model.skill.L1SkillId.HEUKSA;
import static l1j.server.server.model.skill.L1SkillId.HORROR_OF_DEATH;
import static l1j.server.server.model.skill.L1SkillId.INSIGHT;
import static l1j.server.server.model.skill.L1SkillId.LIFE_MAAN;
import static l1j.server.server.model.skill.L1SkillId.LIND_MAAN;
import static l1j.server.server.model.skill.L1SkillId.MORTAL_BODY;
import static l1j.server.server.model.skill.L1SkillId.NATURES_TOUCH;
import static l1j.server.server.model.skill.L1SkillId.PANIC;
import static l1j.server.server.model.skill.L1SkillId.PATIENCE;
import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import static l1j.server.server.model.skill.L1SkillId.RESIST_ELEMENTAL;
import static l1j.server.server.model.skill.L1SkillId.RESIST_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.SHAPE_CHANGE;
import static l1j.server.server.model.skill.L1SkillId.SHAPE_MAAN;
import static l1j.server.server.model.skill.L1SkillId.SILENCE;
import static l1j.server.server.model.skill.L1SkillId.SOUL_OF_FLAME;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BLUE_POTION3;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL3;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CHAT_PROHIBITED;
import static l1j.server.server.model.skill.L1SkillId.STATUS_COMA_3;
import static l1j.server.server.model.skill.L1SkillId.STATUS_COMA_5;
import static l1j.server.server.model.skill.L1SkillId.STATUS_ELFBRAVE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FRUIT;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HASTE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_TIKAL_BOSSDIE;
import static l1j.server.server.model.skill.L1SkillId.STRIKER_GALE;
import static l1j.server.server.model.skill.L1SkillId.Tam_Fruit1;
import static l1j.server.server.model.skill.L1SkillId.Tam_Fruit2;
import static l1j.server.server.model.skill.L1SkillId.Tam_Fruit3;
import static l1j.server.server.model.skill.L1SkillId.VALA_MAAN;
import static l1j.server.server.model.skill.L1SkillId.VENOM_RESIST;
import static l1j.server.server.model.skill.L1SkillId.WEAKNESS;
import static l1j.server.server.model.skill.L1SkillId.WIND_SHACKLE;
import static l1j.server.server.model.skill.L1SkillId.���̸����Ѷ��;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random; //�߰�
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.SpecialEventHandler;
import l1j.server.GameSystem.CrockSystem;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.IndunSystem.MiniGame.BattleZone;
import l1j.server.server.ActionCodes;
import l1j.server.server.GMCommands;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.EvaSystemTable;
import l1j.server.server.datatables.ExcludeLetterTable;
import l1j.server.server.datatables.ExcludeTable;
import l1j.server.server.datatables.GetBackRestartTable;
import l1j.server.server.datatables.IpCheckTable;
import l1j.server.server.datatables.LetterTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Cooking;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_AddSkill;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_CharacterConfig;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_CreateItem;
import l1j.server.server.serverpackets.S_DRAGONPERL;
import l1j.server.server.serverpackets.S_Dexup;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_ElfIcon;
import l1j.server.server.serverpackets.S_Elixir;
import l1j.server.server.serverpackets.S_EventNotice;
import l1j.server.server.serverpackets.S_FairlyConfig;
import l1j.server.server.serverpackets.S_HPUpdate;
import l1j.server.server.serverpackets.S_MPUpdate;
import l1j.server.server.serverpackets.S_MapID;
import l1j.server.server.serverpackets.S_MonsterBookUI;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_NewUI;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharPack;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_OwnCharStatus2;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_Poison;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillIconBlessOfEva;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Strup;
import l1j.server.server.serverpackets.S_SummonPack;
import l1j.server.server.serverpackets.S_SurvivalCry;
import l1j.server.server.serverpackets.S_SystemMessage; //�߰�
import l1j.server.server.serverpackets.S_UnityIcon;
import l1j.server.server.serverpackets.S_Unknown1;
import l1j.server.server.serverpackets.S_War;
import l1j.server.server.serverpackets.S_Weather;
import l1j.server.server.serverpackets.S_bonusstats;
import l1j.server.server.serverpackets.S_�����ֽ�;
import l1j.server.server.templates.L1BookMark;
import l1j.server.server.templates.L1EvaSystem;
import l1j.server.server.templates.L1GetBackRestart;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.utils.CheckInitStat;
import l1j.server.server.utils.SQLUtil;
import server.GameServer;
import server.LineageClient;
import server.controller.InvSwapController;
import server.manager.eva;
import server.system.autoshop.AutoShop;
import server.system.autoshop.AutoShopManager;

//Referenced classes of package l1j.server.server.clientpackets:
//ClientBasePacket

public class C_SelectCharacter extends ClientBasePacket {
	private static final String C_LOGIN_TO_SERVER = "[C] C_LoginToServer";
	private static Logger _log = Logger.getLogger(C_SelectCharacter.class.getName());

	private static final int[] omanLocX = { 32781, 32818, 32818, 32818 };
	private static final int[] omanLocY = { 32781, 32781, 32818, 32781 };

	private static final Random ran = new Random();
	public static final SimpleDateFormat ss = new SimpleDateFormat("MM-dd HH:mm", Locale.KOREA);
	public static FastTable<String> nameList = new FastTable<String>();
	public static FastTable<String> nameINOUTList = new FastTable<String>();

	public C_SelectCharacter(byte abyte0[], LineageClient client) throws FileNotFoundException, Exception {
		super(abyte0);

		String charname = readS();

		try {
			GeneralThreadPool.getInstance().schedule(new login(charname, client), ran.nextInt(500));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String HexToDex(int data, int digits) {
		String number = Integer.toHexString(data);
		for (int i = number.length(); i < digits; i++)
			number = "0" + number;
		return number;
	}

	public static String DataToPacket(byte[] data, int len) {
		StringBuffer result = new StringBuffer();
		int counter = 0;
		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				result.append(HexToDex(i, 4) + ": ");
			}
			result.append(HexToDex(data[i] & 0xff, 2) + " ");
			counter++;
			if (counter == 16) {
				result.append("   ");
				int charpoint = i - 15;
				for (int a = 0; a < 16; a++) {
					int t1 = data[charpoint++];
					if (t1 > 0x1f && t1 < 0x80) {
						result.append((char) t1);
					} else {
						result.append('.');
					}
				}
				result.append("\n");
				counter = 0;
			}
		}
		int rest = data.length % 16;
		if (rest > 0) {
			for (int i = 0; i < 17 - rest; i++) {
				result.append("   ");
			}
			int charpoint = data.length - rest;
			for (int a = 0; a < rest; a++) {
				int t1 = data[charpoint++];
				if (t1 > 0x1f && t1 < 0x80) {
					result.append((char) t1);
				} else {
					result.append('.');
				}
			}
			result.append("\n");
		}
		return result.toString();
	}

	/*
	 * public C_SelectCharacter(byte abyte0[]){ super(abyte0); } public void
	 * clogin(String charName, LineageClient client){
	 * GeneralThreadPool.getInstance().schedule(new login(charName, client),
	 * ran.nextInt(500)); }
	 */

	class login implements Runnable {
		String name = "";
		LineageClient client = null;

		public login(String _name, LineageClient _client) {
			name = _name;
			client = _client;
		}

		@Override
		public void run() {
			try {
				if (name.equalsIgnoreCase("")
						|| client == null /*
											 * || client.close
											 */)
					return;
				login_server(name, client);
			} catch (Exception e) {
			}
		}
	}

	private void login_server(String charName, LineageClient client) {
		try {
			if (client.getAccount() == null) {
				System.out.println("���� Null ���� �õ� " + charName);
				client.kick();
				client.close();
				return;
			}

			if (client.getActiveChar() != null) { // restart ���°� �ƴ϶� �Ҹ���?
				System.out.println("1���� 2�ɸ� ���� �õ� : " + charName + " �����ڵ� 1");
				client.kick();
				client.close();
				return;
			}

			if (GMCommands.�����̸�üũ) {
				if (nameList.contains(charName)) {
					if (L1World.getInstance().getPlayer(charName) == null) {
						GeneralThreadPool.getInstance().schedule(new Runnable() {
							@Override
							public void run() {
								GMCommands.connectCharNameReset();
							}
						}, 3000);
					} else
						GameServer.DuplicationLoginCheck(charName, "2�ʳ� ���� ����");
					client.kick();
					client.close();
					return;
				}
				nameList.add(charName);
				GeneralThreadPool.getInstance().schedule(new charNameDelete(charName), 2000);
			}

			AutoShopManager shopManager = AutoShopManager.getInstance();
			AutoShop shopPlayer = shopManager.getShopPlayer(charName);
			if (shopPlayer != null) {
				L1PcInstance pc = L1World.getInstance().getPlayer(charName);
				shopPlayer.logout();
				shopManager.remove(shopPlayer);
				shopPlayer = null;
				pc.zombie = false;
			}
			if (GMCommands.�����̸�üũ) {
				if (nameINOUTList.contains(charName)) {
					if (L1World.getInstance().getPlayer(charName) == null) {
						GeneralThreadPool.getInstance().schedule(new Runnable() {
							@Override
							public void run() {
								GMCommands.connectCharNameReset();
							}
						}, 3000);
					} else
						GameServer.DuplicationLoginCheck(charName, "���� ����");
					client.kick();
					client.close();
					return;
				}
				nameINOUTList.add(charName);
			}

			L1PcInstance pc = L1PcInstance.load(charName);
			String accountName = client.getAccountName();

			if (pc == null || !accountName.equals(pc.getAccountName())) {
				System.out.println("���� ������ ���� �ɸ� ���ӽõ� : " + charName + " ���� : " + client.getAccountName());
				client.kick();
				client.close();
				return;
			}

			// ///////////////// ���ݹ��׹����ҽ� /////////////////////
			L1PcInstance ckpc = L1World.getInstance().getPlayer(charName);
			if (ckpc != null && !ckpc.zombie) {
				System.out.println("1���� 2�ɸ� ���� �õ� : " + charName + " �����ڵ� 2");
				client.kick();
				client.close();
				ckpc.logout();
				if (ckpc.getNetConnection() != null)
					ckpc.getNetConnection().kick();
				return;
			}
			for (L1PcInstance _client2 : L1World.getInstance().getAllPlayers()) {
				if (_client2 == null || _client2.zombie || _client2.�����) {
					continue;
				}
				if (client.getAccountName().equalsIgnoreCase(_client2.getAccountName())) {
					System.out.println("1���� 2�ɸ� ���� �õ� : " + charName + " �����ڵ� 3");
					client.kick();
					client.close();
					if (_client2.getNetConnection() != null) {
						_client2.getNetConnection().kick();
						_client2.getNetConnection().close();
					}
					return;
				}
			}

			if (pc.isGm()) {
				Config.add����(pc);
				Config.add����(pc);
			}

			if (pc.getLevel() > pc.getHighLevel()) {
				System.out.println("���� �������� �������� ���� : " + charName);
				client.kick();
				client.close();
				return;
			}
			if (pc.getAbility().getCon() > 65 || pc.getAbility().getStr() > 65 || pc.getAbility().getDex() > 65
					|| pc.getAbility().getCha() > 65 || pc.getAbility().getInt() > 65
					|| pc.getAbility().getWis() > 65) {
				System.out.println("�Ѱ��� ���� 65�̻� ���� : " + charName);
				client.kick();
				client.close();
				return;
			}

			// ///////////////// ���ݹ��׹����ҽ� /////////////////////

			String ip = client.getIp();
			try {
				_log.info("ĳ���� �α���: char=" + charName + " account=" + accountName + " host=" + client.getHostname());
			} catch (Exception e) {
				_log.info("ĳ���� �α���: char=" + charName + " account=" + accountName + " host= ȣ��Ʈ ����");
			}
			pc.setPacketOutput(client);

			// pc.setPacketOutput(client);

			eva.LogServerAppend("����", pc, client.getIp(), 1);
			// ChatMonitorChannel.getInstance().sendMsg(ChatMonitorChannel.CHAT_MONITOR_GM,
			// time +" [����] "+pc.getName()+" - "+ client.getIp(), null);

			int currentHpAtLoad = pc.getCurrentHp();
			int currentMpAtLoad = pc.getCurrentMp();

			pc.clearSkillMastery();
			pc.setOnlineStatus(1);
			pc.sendPackets(new S_MonsterBookUI());
			CharacterTable.updateOnlineStatus(pc);
			L1World.getInstance().storeObject(pc);

			pc.set_delete(false); // ��������Ƚ�
			pc.setNetConnection(client);

			client.setActiveChar(pc);

			if (GameServer.getInstance().checkip(ip)) {
				client.��Ŷ�α� = true;
			}

			// if(pc.isGm())ChatMonitorChannel.getInstance().join(pc);

			/*
			 * ############### Point System ############### int at =
			 * client.getAccount().getAccountTime() * 60000; if(at > 0) { long
			 * la = client.getAccount().getLastActive().getTime(); long sum = la
			 * + at; pc.setLimitPointTime(sum/1000); pc.setPointUser(true); }
			 * ############### Point System ###############
			 */

			pc.sendPackets(new S_Unknown1(), true);

			// pc.sendPackets(new S_CharacterConfig());
			if (Config.CHARACTER_CONFIG_IN_SERVER_SIDE) {
				if (pc.getLevel() == 1) {
					pc.sendPackets(new S_CharacterConfig(pc.getId(), 1), true);
				} else {
					pc.sendPackets(new S_CharacterConfig(pc.getId()), true);
				}

			}

			pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.getbase_Er()), true);
			// 0000: 76 84 03 v..

			pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_Er()), true);
			// 0000: 76 84 0b

			items(pc);

			// pc.sendPackets(new S_ReturnedStat(pc,
			// S_ReturnedStat.Unknown_LOGIN, 1));
			// pc.sendPackets(new S_ReturnedStat(pc,
			// S_ReturnedStat.Unknown_LOGIN, 2));
			// pc.sendPackets(new S_ReturnedStat(pc,
			// S_ReturnedStat.Unknown_LOGIN2, 2));
			pc.sendPackets(new S_ReturnedStat(S_ReturnedStat.RING_RUNE_SLOT, S_ReturnedStat.SUBTYPE_RING,
					pc.getRingSlotLevel()));
			if (pc.getEarringSlotLevel() > 0)
				pc.sendPackets(new S_ReturnedStat(S_ReturnedStat.RING_RUNE_SLOT, S_ReturnedStat.SUBTYPE_RING, 16));

			pc.sendPackets(new S_ReturnedStat(S_ReturnedStat.RING_RUNE_SLOT, S_ReturnedStat.SUBTYPE_RUNE, 1));

			pc.sendPackets(new S_ReturnedStat(S_ReturnedStat.Unknown_LOGIN2, 0, 0), true);
			pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.LOGIN_EQUIP));

			// ���� �� ��񿡼� �ҷ�����
			pc.setCurrentHp(pc.get_loadHp());
			pc.setCurrentMp(pc.get_loadMp());

			pc.sendPackets(new S_PacketBox(S_PacketBox.WORLDMAP_UNKNOWN1), true);

			bookmarks(pc);
			pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.BOOKMARK), true);

			pc.sendPackets(new S_MonsterBookUI());
			skills(pc);

			// restartó�� getback_restart ���̺�� �����ǰ� ������(��) �̵���Ų��
			GetBackRestartTable gbrTable = GetBackRestartTable.getInstance();
			L1GetBackRestart[] gbrList = gbrTable.getGetBackRestartTableList();
			if (gbrList != null) {
				for (L1GetBackRestart gbr : gbrList) {
					if (pc.getMapId() == gbr.getArea()) {
						if (pc.getMapId() == 248 || pc.getMapId() == 249 || pc.getMapId() == 250
								|| pc.getMapId() == 251) {
							L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
							if (clan != null) {
								if (clan.getCastleId() == 4) {
									break;
								}
							}
							pc.setX(33429);
							pc.setY(32814);
							pc.setMap((short) 4);
						}
						if (pc.getMapId() == 240 || pc.getMapId() == 241 || pc.getMapId() == 242
								|| pc.getMapId() == 243) {
							L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
							if (clan != null) {
								if (clan.getCastleId() == 1) {
									break;
								}
							}
							pc.setX(33429);
							pc.setY(32814);
							pc.setMap((short) 4);
						}
						if (pc.getMapId() == 15) {
							L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
							if (clan != null) {
								if (clan.getCastleId() == 1) {
									break;
								}
							}
						} else if (pc.getMapId() == 29) {
							L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
							if (clan != null) {
								if (clan.getCastleId() == 3) {
									break;
								}
							}
						} else if (pc.getMapId() == 260) {
							L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
							if (clan != null) {
								if (clan.getCastleId() == 2) {
									break;
								}
							}
						} else if (pc.getMapId() == 52) {
							L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
							if (clan != null) {
								if (clan.getCastleId() == 4) {
									break;
								}
							}
						} else if (pc.getMapId() == 64) {
							L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
							if (clan != null) {
								if (clan.getCastleId() == 5) {
									break;
								}
							}
						} else if (pc.getMapId() == 300) {
							L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
							if (clan != null) {
								if (clan.getCastleId() == 7) {
									break;
								}
							}
						}
						pc.setX(gbr.getLocX());
						pc.setY(gbr.getLocY());
						pc.setMap(gbr.getMapId());
						break;
					}
				}
			}

			gbrList = null;

			if ((pc.getMapId() >= 271 && pc.getMapId() <= 278) || (pc.getMapId() >= 2100 && pc.getMapId() <= 2151)) {
				pc.setX(34060);
				pc.setY(32282);
				pc.setMap((short) 4);
			}

			if (pc.getMapId() == 4 && ((pc.getX() >= 33332 && pc.getX() <= 33338 && pc.getY() >= 32433
					&& pc.getY() <= 32439)
					|| (pc.getX() >= 33259 && pc.getX() <= 33265 && pc.getY() >= 32399 && pc.getY() <= 32405)
					|| (pc.getX() >= 33389 && pc.getX() <= 33395 && pc.getY() >= 32341 && pc.getY() <= 32347))) {
				L1Location newLocation = pc.getLocation().randomLocation(30, true);
				pc.setX(newLocation.getX());
				pc.setY(newLocation.getY());
			}

			if (pc.getMapId() == 814) {
				pc.setX(33616);
				pc.setY(33244);
				pc.setMap((short) 4);
			}

			if (pc.getMapId() == 2010 || (pc.getMapId() >= 2221 && pc.getMapId() <= 2235)) {
				pc.setX(33085);
				pc.setY(33391);
				pc.setMap((short) 4);
			}
			if (pc.getMapId() >= 2600 && pc.getMapId() <= 2699) {
				pc.setX(33702);
				pc.setY(32502);
				pc.setMap((short) 4);
			}
			if (pc.getMapId() >= 1400 && pc.getMapId() <= 1499) {
				pc.setX(33489);
				pc.setY(32764);
				pc.setMap((short) 4);
			}
			if (pc.getMapId() >= 1911 && pc.getMapId() <= 1913) {
				pc.setX(32768);
				pc.setY(32835);
				pc.setMap((short) 622);
			}
			if (pc.getMapId() >= 2301 && pc.getMapId() <= 2350) {
				pc.setX(33438);
				pc.setY(32799);
				pc.setMap((short) 4);
			}
			if (pc.getMapId() >= 1936 && pc.getMapId() <= 1940) {
				pc.setX(33438);
				pc.setY(32799);
				pc.setMap((short) 4);
			}

			L1Map map = L1WorldMap.getInstance().getMap(pc.getMapId());
			// altsettings.properties�� GetBack�� true��� �Ÿ��� �̵���Ų��
			int tile = map.getTile(pc.getX(), pc.getY());
			if (Config.GET_BACK || !map.isInMap(pc.getX(), pc.getY()) || tile == 0 || tile == 4 || tile == 12) {
				int[] loc = Getback.GetBack_Location(pc, true);
				try {
					pc.setX(loc[0]);
					pc.setY(loc[1]);
					pc.setMap((short) loc[2]);
				} catch (Exception e) {
					System.out.println("ĳ���� Get_Back Error: char=" + charName + " X: " + pc.getX() + " Y: " + pc.getY()
							+ " Tile: " + tile);
					_log.info("ĳ���� Get_Back Error: char=" + charName + " X: " + pc.getX() + " Y: " + pc.getY()
							+ " Tile: " + tile);
					e.printStackTrace();
				}
			}

			if (pc.getMapId() == 101) {// ������ ž ��ȯ �����ϰ�� 1������ ���õǾ�����
				int rnd = ran.nextInt(omanLocX.length);
				pc.setX(omanLocX[rnd]);
				pc.setY(omanLocY[rnd]);
				pc.setMap((short) 101);
			}

			if (pc.getMapId() == 410) {
				pc.setX(32929);
				pc.setY(32995);
				pc.setMap((short) 410);
			}
			if (pc.getMapId() == 5153 || pc.getMapId() == 5125 || pc.getMapId() == 5140 || pc.getMapId() == 5143) {
				pc.setX(33429);
				pc.setY(32814);
				pc.setMap((short) 4);
			}
			if ((pc.getMapId() >= 1005 && pc.getMapId() <= 1010) || (pc.getMapId() >= 1011 && pc.getMapId() <= 1016)
					|| (pc.getMapId() >= 10000 && pc.getMapId() <= 10005)) {
				pc.setX(33719);
				pc.setY(32506);
				pc.setMap((short) 4);
			}
			if (pc.getMapId() >= 9101 && pc.getMapId() <= 9199) {
				pc.setX(33442);
				pc.setY(32799);
				pc.setMap((short) 4);
			}
			if (pc.getMapId() >= 653 && pc.getMapId() <= 656) { // �����Ѱ��� -> ������
																// �ϴ�����
				pc.setX(32764);
				pc.setY(32836);
				pc.setMap((short) 622);
			}
			if (pc.getMapId() == 785 || pc.getMapId() == 788 || pc.getMapId() == 789) {
				pc.setX(33442);
				pc.setY(32799);
				pc.setMap((short) 4);
			}
			if (pc.getReturnStat() == 0 && pc.getMapId() == 5166) {
				pc.setX(32612);
				pc.setY(32734);
				pc.setMap((short) 4);
			}
			if (!pc.isGm() && (pc.getMapId() == 610 || pc.getMapId() == 612 || pc.getMapId() == 5554)) {
				pc.setX(33442);
				pc.setY(32799);
				pc.setMap((short) 4);
			}

			if ((pc.getMapId() == 420)) {
				pc.setX(32693);
				pc.setY(32800);
				pc.setMap((short) 450);
			}

			if (!pc.isGm() && (pc.getMapId() == 430)) {
				pc.setX(33442);
				pc.setY(32799);
				pc.setMap((short) 4);
			}
			if ((pc.getMapId() >= 9103 && pc.getMapId() <= 9200)) {
				pc.setX(32573);
				pc.setY(32940);
				pc.setMap((short) 0);
			}

			if (GMCommands.autocheck_Tellist.size() > 0) {
				if (GMCommands.autocheck_Tellist.contains(client.getAccountName()) && pc.getMapId() != 6202) {
					pc.setX(32928);
					pc.setY(32864);
					pc.setMap((short) 6202);
				}
			}

			if (!pc.isGm() && Config.LEVEL_DOWN_RANGE != 0) {
				if (pc.getHighLevel() - pc.getLevel() >= Config.LEVEL_DOWN_RANGE) {
					S_ServerMessage sm = new S_ServerMessage(64);
					pc.sendPackets(sm, true);
					S_Disconnect dis = new S_Disconnect();
					pc.sendPackets(dis, true);
					_log.info(String.format("[%s]: ���� ������ �Ѿ��� ������ ���� ���� �߽��ϴ�.", pc.getName()));
				}
			}

			// �������� �⳻�� �־��� ���, ���� ������ �ƴ� ���� ��ȯ��Ų��.
			int castle_id = L1CastleLocation.getCastleIdByArea(pc);
			if (pc.getMapId() == 66) {
				castle_id = 6;
			}
			if (0 < castle_id) {
				if (WarTimeController.getInstance().isNowWar(castle_id)) {
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					if (clan != null && clan.getCastleId() != castle_id) {
						int[] loc = new int[3];
						loc = L1CastleLocation.getGetBackLoc(castle_id);
						pc.setX(loc[0]);
						pc.setY(loc[1]);
						pc.setMap((short) loc[2]);
						loc = null;
					} else if (pc.getMapId() == 4) {
						int[] loc = new int[3];
						loc = L1CastleLocation.getGetBackLoc(castle_id);
						pc.setX(loc[0]);
						pc.setY(loc[1]);
						pc.setMap((short) loc[2]);
						loc = null;
					}
				}
			}

			L1World.getInstance().addVisibleObject(pc);
			// S_ActiveSpells s_activespells = new S_ActiveSpells(pc);
			// pc.sendPackets(s_activespells);

			pc.beginGameTimeCarrier();

			pc.sendPackets(new S_OwnCharStatus(pc), true);
			pc.sendPackets(new S_MapID(pc.getMapId(), pc.getMap().isUnderwater()), true);
			pc.sendPackets(new S_OwnCharPack(pc), true);

			buff(client, pc);

			pc.sendPackets(new S_Weather(L1World.getInstance().getWeather()), true);

			// ������ �� �Ʒ� ��Ŷ ���̿� S_OPCODE_CASTLEMASTER ��Ŷ�� �´� (�� 9��)
			pc.sendCastleMaster();

			pc.sendPackets(new S_OwnCharStatus2(pc), true);
			pc.sendPackets(new S_SPMR(pc), true);

			pc.sendPackets(new S_ReturnedStat(pc, 4), true);

			// XXX Ÿ��Ʋ ������ S_OwnCharPack�� ���ԵǹǷ� �Ƹ� �ҿ�
			// S_CharTitle s_charTitle = new S_CharTitle(pc.getId(),
			// pc.getTitle());
			// pc.sendPackets(s_charTitle);
			// Broadcaster.broadcastPacket(pc, s_charTitle);

			pc.sendVisualEffectAtLogin(); // ��, ���� ���� �ð� ȿ���� ǥ��

			pc.sendPackets(new S_PacketBox(pc, S_PacketBox.KARMA), true);
			pc.sendPackets(new S_PacketBox(S_PacketBox.INIT_DG, 0x00));
			pc.sendPackets(new S_PacketBox(S_PacketBox.UPDATE_DG, pc.getDg()));

			pc.sendPackets(new S_SurvivalCry(6000), true);

			pc.getLight().turnOnOffLight();

			// ������� ���� �߰�
			L1PcInstance jonje = L1World.getInstance().getPlayer(pc.getName());
			if (jonje == null) {
				S_SystemMessage sm = new S_SystemMessage("������� ��������! �������ϼ���");
				pc.sendPackets(sm, true);
				client.kick();
				return;
			}

			if (pc.getGfxId().getTempCharGfx() == 11326 || pc.getGfxId().getTempCharGfx() == 11427
					|| pc.getGfxId().getTempCharGfx() == 10047 || pc.getGfxId().getTempCharGfx() == 9688
					|| pc.getGfxId().getTempCharGfx() == 11322 || pc.getGfxId().getTempCharGfx() == 10069
					|| pc.getGfxId().getTempCharGfx() == 10034 || pc.getGfxId().getTempCharGfx() == 10032) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.SHAPE_CHANGE);
				L1PolyMorph.undoPoly(pc);
			}

			if (pc.getCurrentHp() > 0) {
				pc.setDead(false);
				pc.setActionStatus(0);
			} else {
				pc.setDead(true);
				pc.setActionStatus(ActionCodes.ACTION_Die);
			}

			if (pc.getLevel() >= 51 && pc.getLevel() - 50 > pc.getAbility().getBonusAbility()
					&& pc.getAbility().getAmount() < 150) {
				int temp = (pc.getLevel() - 50) - pc.getAbility().getBonusAbility();
				pc.sendPackets(new S_bonusstats(pc.getId(), temp), true);
			}

			if (pc.getReturnStat() != 0) {
				SpecialEventHandler.getInstance().ReturnStats(pc);
			}

			// commit(pc);//���� �Ǻ�

			// pc.sendPackets(new S_Test(pc, 0));
			// pc.sendPackets(new S_Test(pc, 1));

			pc.sendPackets(new S_PacketBox(S_PacketBox.LOGIN_UNKNOWN3));

			pc.sendPackets(new S_FairlyConfig(pc));
			pc.sendPackets(new S_EventNotice()); // �̺�Ʈ�˶�
			pc.sendPackets(new S_MonsterBookUI(S_MonsterBookUI.MONSTER_BOOK));
			pc.sendPackets(new S_MonsterBookUI(S_MonsterBookUI.MONSTER_ACTION));

			pc.sendPackets(new S_PacketBox(86, 0), true);// ��1
			pc.sendPackets(new S_PacketBox(86, 1), true);// ��2

			pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_Er()), true);

			hasadbuff(pc);

			pc.����г�Ƽ(true);

			pc.sendPackets(new S_NewCreateItem(0x01e3, 1, pc.getAbility().getTotalStr(), pc.getAbility().getTotalCon(),
					"��", pc.getType(), pc));
			pc.sendPackets(new S_NewCreateItem(0x01e3, 1, pc.getAbility().getTotalDex(), 0, "��", pc.getType(), pc));
			pc.sendPackets(new S_NewCreateItem(0x01e3, 1, pc.getAbility().getTotalCon(), pc.getAbility().getTotalStr(),
					"��", pc.getType(), pc));
			pc.sendPackets(new S_NewCreateItem(0x01e3, 1, pc.getAbility().getTotalInt(), 0, "��Ʈ", pc.getType(), pc));
			pc.sendPackets(new S_NewCreateItem(0x01e3, 1, pc.getAbility().getTotalWis(), 0, "����", pc.getType(), pc));
			pc.sendPackets(new S_NewCreateItem(0x01e3, 1, pc.getAbility().getTotalCha(), 0, "ī��", pc.getType(), pc));
			pc.sendPackets(new S_NewCreateItem("����", pc));

			pc.sendPackets(new S_NewCreateItem(0x01e7, "����1", pc));
			pc.sendPackets(new S_NewCreateItem(0x01e7, "����2", pc));
			pc.sendPackets(new S_NewCreateItem(0x01e7, "����3", pc));

			pc.sendPackets(new S_NewCreateItem(0x01ea, "��������", pc));

			pc.sendPackets(new S_NewCreateItem(0x01e9, "08 00 c9 c9"));// �����̺�Ʈ?

			pc.sendPackets(new S_NewCreateItem(0x7E, "08 00 10 01 63 d7"));// �����̺�Ʈ?

			pc.sendPackets(new S_PacketBox(S_PacketBox.ACTION_GUIDE_2));

			UserRankingController.getInstance().setBuffSetting(pc);

			serchSummon(pc);

			WarTimeController.getInstance().checkCastleWar(pc);

			if (pc.getClanid() != 0) { // ũ�� �Ҽ���

				L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
				if (clan != null) {
					if (pc.getClanid() == clan.getClanId() && 
							pc.getClanname().toLowerCase().equals(clan.getClanName().toLowerCase())) {
						clan.addOnlineClanMember(pc.getName(), pc);
						S_ServerMessage sm = new S_ServerMessage(843, pc.getName());
						for (L1PcInstance clanMember : clan.getOnlineClanMember()) {
							if (clanMember.getId() != pc.getId()) {
								clanMember.sendPackets(sm);
							}
						}
						sm = null;
						// ������ ����Ʈ�� ���
						for (L1War war : L1World.getInstance().getWarList()) {
							boolean ret = war.CheckClanInWar(pc.getClanname());
							if (ret) { // ���￡ ������
								String enemy_clan_name = war.GetEnemyClanName(pc.getClanname());
								if (enemy_clan_name != null) {
									// ����� ������ ����_���Ͱ� �������Դϴ�.
									pc.sendPackets(new S_War(8, pc.getClanname(), enemy_clan_name), true);
								}
								break;
							}
						}
					} else {
						pc.setClanid(0);
						pc.setClanname("");
						pc.setClanRank(0);
						pc.save(); // DB�� ĳ���� ������ �����Ѵ�
					}
				}
			} /*
				 * else{ if(pc.isCrown()) pc.sendPackets(new
				 * S_ServerMessage(3247), true);//������ â���ϰ� ���� �˸�����! else
				 * pc.sendPackets(new S_ServerMessage(3245), true);//������ �θ�: ���Ϳ�
				 * �����ϼ���! }
				 */

			// pc.sendPackets(new S_PacketBox(173), true);
			/*
			 * try{ pc.sendPackets(new S_PacketBox(S_PacketBox.����_����,
			 * pc.getNetConnection().getAccount().berry)); }catch(Exception e){}
			 */

			if (pc.getPartnerId() != 0) { // ��ȥ��
				L1PcInstance partner = (L1PcInstance) L1World.getInstance().findObject(pc.getPartnerId());
				if (partner != null && partner.getPartnerId() != 0) {
					if (pc.getPartnerId() == partner.getId() && partner.getPartnerId() == pc.getId()) {
						pc.sendPackets(new S_ServerMessage(548), true); // �����
																		// ��Ʈ�ʴ�
																		// ����
						// �������Դϴ�.
						partner.sendPackets(new S_ServerMessage(549), true); // �����
																				// ��Ʈ�ʴ�
						// ���
						// �α����߽��ϴ�.
					}
				}
			}
			if (currentHpAtLoad > pc.getCurrentHp()) {
				pc.setCurrentHp(currentHpAtLoad);
			}
			if (currentMpAtLoad > pc.getCurrentMp()) {
				pc.setCurrentMp(currentMpAtLoad);
			}

			pc.startObjectAutoUpdate();
			client.CharReStart(false);
			pc.beginExpMonitor();

			pc.tempx = pc.getX();
			pc.tempy = pc.getY();
			pc.tempm = pc.getMapId();
			pc.temph = pc.getMoveState().getHeading();
			long sysTime = System.currentTimeMillis();

			// System.out.println(pc.getDETime2());
			if (pc.getDETime2() != null) {
				if (sysTime <= pc.getDETime2().getTime()) {
					long DETIME = pc.getDETime2().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_EME_1);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGON_EME_1, (int) DETIME);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME, 0x01, (int) DETIME / 1000), true);
				}
			}
			// System.out.println(pc.getDETime());
			if (pc.getDETime() != null) {
				if (sysTime <= pc.getDETime().getTime()) {
					long DETIME = pc.getDETime().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_EME_2);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGON_EME_2, (int) DETIME);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME, 0x02, (int) DETIME / 1000), true);
				}
			}
			if (pc.getPUPLETime() != null) {
				if (sysTime <= pc.getPUPLETime().getTime()) {
					long DETIME = pc.getPUPLETime().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_PUPLE);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGON_PUPLE, (int) DETIME);
					pc.sendPackets(new S_PacketBox((int) DETIME / 1000, 1, true, true), true);
				}
			}

			if (pc.getTOPAZTime() != null) {
				if (sysTime <= pc.getTOPAZTime().getTime()) {
					long DETIME = pc.getTOPAZTime().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_TOPAZ);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGON_TOPAZ, (int) DETIME);
					pc.sendPackets(new S_PacketBox((int) DETIME / 1000, 2, true, true), true);
				}
			}

			int tamcount = pc.tamcount();
			if (tamcount > 0) {
				long tamtime = pc.TamTime();

				int aftertamtime = (int) tamtime;

				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.����â, tamtime, tamcount, true), true);

				if (aftertamtime < 0) {
					aftertamtime = 0;
				}

				if (tamcount == 1) {
					pc.getSkillEffectTimerSet().setSkillEffect(Tam_Fruit1, aftertamtime);
					pc.getAC().addAc(-1);
				} else if (tamcount == 2) {
					pc.getSkillEffectTimerSet().setSkillEffect(Tam_Fruit2, aftertamtime);
					pc.getAC().addAc(-2);
				} else if (tamcount == 3) {
					pc.getSkillEffectTimerSet().setSkillEffect(Tam_Fruit3, aftertamtime);
					pc.getAC().addAc(-3);
				}

				pc.sendPackets(new S_OwnCharStatus(pc));
			}

			if (pc.getNetConnection().getAccount().getBuff_HPMP() != null) {
				if (sysTime <= pc.getNetConnection().getAccount().getBuff_HPMP().getTime()) {
					long bufftime = pc.getNetConnection().getAccount().getBuff_HPMP().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_Ȱ��);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_Ȱ��, (int) bufftime);
					pc.sendPackets(new S_NewUI("Ȱ��", (long) bufftime), true);
					pc.addMaxHp(50);
					pc.addMaxMp(50);
					pc.addWeightReduction(3);
					pc.sendPackets(new S_HPUpdate(pc));
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
				}
			}

			if (pc.getNetConnection().getAccount().getBuff_DMG() != null) {
				if (sysTime <= pc.getNetConnection().getAccount().getBuff_DMG().getTime()) {
					long bufftime = pc.getNetConnection().getAccount().getBuff_DMG().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_����);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_����, (int) bufftime);
					pc.sendPackets(new S_NewUI("����", (long) bufftime), true);
					pc.addDmgup(1);
					pc.addBowDmgup(1);
				}
			}

			if (pc.getNetConnection().getAccount().getBuff_REDUC() != null) {
				if (sysTime <= pc.getNetConnection().getAccount().getBuff_REDUC().getTime()) {
					long bufftime = pc.getNetConnection().getAccount().getBuff_REDUC().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_���);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_���, (int) bufftime);
					pc.sendPackets(new S_NewUI("���", (long) bufftime), true);
					pc.addDamageReductionByArmor(1);
				}
			}

			if (pc.getNetConnection().getAccount().getBuff_MAGIC() != null) {
				if (sysTime <= pc.getNetConnection().getAccount().getBuff_MAGIC().getTime()) {
					long bufftime = pc.getNetConnection().getAccount().getBuff_MAGIC().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_����);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_����, (int) bufftime);
					pc.sendPackets(new S_NewUI("����", (long) bufftime), true);
					pc.getAbility().addSp(1);
					pc.sendPackets(new S_SPMR(pc));
				}
			}

			if (pc.getNetConnection().getAccount().getBuff_STUN() != null) {
				if (sysTime <= pc.getNetConnection().getAccount().getBuff_STUN().getTime()) {
					long bufftime = pc.getNetConnection().getAccount().getBuff_STUN().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_����);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_����, (int) bufftime);
					pc.sendPackets(new S_NewUI("����", (long) bufftime), true);
					pc.getResistance().addStun(2);
				}
			}

			if (pc.getNetConnection().getAccount().getBuff_HOLD() != null) {
				if (sysTime <= pc.getNetConnection().getAccount().getBuff_HOLD().getTime()) {
					long bufftime = pc.getNetConnection().getAccount().getBuff_HOLD().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_Ȧ��);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_Ȧ��, (int) bufftime);
					pc.sendPackets(new S_NewUI("Ȧ��", (long) bufftime), true);
					pc.getResistance().addHold(2);
				}
			}

			if (pc.getNetConnection().getAccount().getBuff_PC��() != null) {
				if (sysTime <= pc.getNetConnection().getAccount().getBuff_PC��().getTime()) {
					long �Ǿ�Ÿ�� = pc.getNetConnection().getAccount().getBuff_PC��().getTime() - sysTime;
					
					TimeZone seoul = TimeZone.getTimeZone("UTC");
					Calendar calendar = Calendar.getInstance(seoul);
					calendar.setTimeInMillis(�Ǿ�Ÿ��);
					int d = calendar.get(Calendar.DATE) - 1;
					int h = calendar.get(Calendar.HOUR_OF_DAY);
					int m = calendar.get(Calendar.MINUTE);
					int sc = calendar.get(Calendar.SECOND);

					if (d > 0) {
						pc.sendPackets(new S_SystemMessage(
								"[PC�� �̿� �ð�] " + d + "�� " + h + "�ð� " + m + "�� " + sc + "�� ���ҽ��ϴ�."));
					} else if (h > 0) {
						pc.sendPackets(new S_SystemMessage("[PC�� �̿� �ð�] " + h + "�ð� " + m + "�� " + sc + "�� ���ҽ��ϴ�."));
					} else if (m > 0) {
						pc.sendPackets(new S_SystemMessage("[PC�� �̿� �ð�] " + m + "�� " + sc + "�� ���ҽ��ϴ�."));
					} else {
						pc.sendPackets(new S_SystemMessage("[PC�� �̿� �ð�] " + sc + "�� ���ҽ��ϴ�."));
					}

					PC����(pc);
				}
			}

			if (pc.PC��_����) {
				String s = "08 01 10 01 f1 d5";// �Ǿ���..
				// pc.sendPackets(new S_NewCreateItem(s ));
				pc.sendPackets(new S_NewCreateItem(126, s));
			} else {
				String s = "08 00 10 01 e7 6d";// �Ǿ���..
				// pc.sendPackets(new S_NewCreateItem(s ));
				pc.sendPackets(new S_NewCreateItem(126, s));
			}

			if (pc.getNetConnection().getAccount().getDragonRaid() != null) {
				if (sysTime <= pc.getNetConnection().getAccount().getDragonRaid().getTime()) {
					long BloodTime = pc.getNetConnection().getAccount().getDragonRaid().getTime() - sysTime;
					pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONRAID_BUFF);
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGONRAID_BUFF, (int) BloodTime);
					pc.sendPackets(new S_PacketBox(S_PacketBox.�巡�ﷹ�̵����, (int) BloodTime / 1000), true);
				}
			}

			try {
				pc.save(); // DB�� ĳ���� ������ �����Ѵ�
			} catch (Exception e) {
				e.printStackTrace();
			}

			S_SystemMessage smpc = new S_SystemMessage(
					"\\fY" + pc.getName() + " ���� ����. \\fVIP:" + ip + " ����:" + client.getAccountName());
			if (Config.����ä�ø����() > 0) {
				for (L1PcInstance gm : Config.toArray����ä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove����(gm);
						continue;
					}
					if (gm == pc) {
						continue;
					}
					gm.sendPackets(smpc);
					if (GMCommands.�ֽþ�����üũ) {
						FastTable<String> iplist = IpCheckTable.getInstance().list();
						if (iplist != null && iplist.size() > 0) {
							for (String ipl : iplist) {
								if (ip.startsWith(ipl)) {
									pc.sendPackets(new S_SystemMessage(
											"\\fY >> " + pc.getName() + " = " + ipl + " << �ֽ� ������ �ɸ��� üũ�ٶ�"));
								}
							}
						}
					}
				}
			}

			if (pc.getMoveState().getHeading() < 0 || pc.getMoveState().getHeading() > 7) {
				pc.getMoveState().setHeading(0);
			}

			pc.sendPackets(new S_Elixir(S_Elixir.Elixir, pc.getAbility().getElixirCount()));
			if (pc.getEarringSlotLevel() != 0) {
				pc.sendPackets(new S_ReturnedStat(S_ReturnedStat.RING_RUNE_SLOT, S_ReturnedStat.SUBTYPE_RING, 16));
			}
			pc.sendPackets(new S_ReturnedStat(S_ReturnedStat.RING_RUNE_SLOT, S_ReturnedStat.SUBTYPE_RING,
					pc.getRingSlotLevel()));

			huntoption(pc); // ����ȿ��
			bapo(pc);

			if (pc.getHellTime() > 0) {
				pc.beginHell(false);
			}

			if (pc.getHuntCount() == 1) {
				pc.initBeWanted();
				int[] beWanted = { 1, 1, 1, 1, 1, 1 };
				pc.setBeWanted(beWanted);
				pc.addBeWanted();
			}
			if (pc.getHuntCount() == 2) {
				pc.initBeWanted();
				int[] beWanted = { 2, 2, 2, 2, 2, 2 };
				pc.setBeWanted(beWanted);
				pc.addBeWanted();
			}
			if (pc.getHuntCount() == 3) {
				pc.initBeWanted();
				int[] beWanted = { 3, 3, 3, 3, 3, 3 };
				pc.setBeWanted(beWanted);
				pc.addBeWanted();
			}
			���ȹ���(pc);
			checkBattleZone(pc);
			
			pc.sendPackets(new S_OwnCharStatus(pc), true);
			if (pc.getClanid() != 0) { // ũ�� �Ҽ���
				pc.sendPackets(new S_ClanWindow(S_ClanWindow.����ũ����, pc.getClan().getmarkon()), true);
				pc.sendPackets(new S_�����ֽ�(pc.getClan(), 2), true);
				pc.sendPackets(new S_NewUI(0x19, pc.getClan().getClanName(), pc), true);

			}

			/** ��Ʋ�� **/
			if (pc.getMapId() == 5153) {
				if (!BattleZone.getInstance().getDuelOpen()) {
					if (pc.get_DuelLine() != 0) {
						pc.set_DuelLine(0);
					}
					L1Teleport.teleport(pc, 33090, 33402, (short) 4, 0, true);
				} else {
					if (pc.get_DuelLine() == 0) {
						L1Teleport.teleport(pc, 33090, 33402, (short) 4, 0, true);
					}
				}
			} else {
				if (pc.get_DuelLine() != 0) {
					pc.set_DuelLine(0);
				}
			}

			// pc.sendPackets(new S_NewUI(0x13));

			if (pc.getReturnStat() != 0) {
				L1SkillUse l1skilluse = new L1SkillUse();
				l1skilluse.handleCommands(pc, L1SkillId.CANCELLATION, pc.getId(), pc.getX(), pc.getY(), null, 0,
						L1SkillUse.TYPE_LOGIN);
				if (pc.getSecondWeapon() != null) {
					pc.getInventory().setEquipped(pc.getSecondWeapon(), false, false, false, true);
				}
				if (pc.getWeapon() != null) {
					pc.getInventory().setEquipped(pc.getWeapon(), false, false, false, false);
				}

				pc.sendPackets(new S_CharVisualUpdate(pc));
				// pc.sendPackets(new S_OwnCharStatus2(pc));
				pc.sendPackets(new S_OwnCharStatus(pc));

				for (L1ItemInstance armor : pc.getInventory().getItems()) {
					for (int type = 0; type <= 12; type++) {
						if (armor != null) {
							pc.getInventory().setEquipped(armor, false, false, false);
						}
					}
				}
				pc.sendPackets(new S_SPMR(pc));
				pc.sendPackets(new S_OwnCharAttrDef(pc));
				// pc.sendPackets(new S_OwnCharStatus2(pc));
				pc.sendPackets(new S_OwnCharStatus(pc));
				pc.setReturnStatus(1);
				pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.START));
			} else if (!pc.LoadCheckStatus()) {
				if (!CheckInitStat.CheckPcStat(pc)) {
					pc.setReturnStat(pc.getExp());
					pc.setReturnStatus(1);
					pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.START));
					return;
				}
			}
			if (pc.getGfxId().getTempCharGfx() == 11120 || pc.getGfxId().getTempCharGfx() == 11121
					|| pc.getGfxId().getTempCharGfx() == 11122 || pc.getGfxId().getTempCharGfx() == 11123
					|| pc.getGfxId().getTempCharGfx() == 11124 || pc.getGfxId().getTempCharGfx() == 11125
					|| pc.getGfxId().getTempCharGfx() == 11126 || pc.getGfxId().getTempCharGfx() == 11127) {
				pc.getInventory().takeoffEquip(pc.getGfxId().getTempCharGfx());
			}

			/*
			 * try {
			 * 
			 * if(pc.getLevel() >= 65){ L1ItemInstance item = null; for (int i =
			 * 0; i < ������.length; i++) { item =
			 * pc.getInventory().checkEquippedItem(������[i]); if(item!=null){
			 * if(item.getItem().getType2()==1){
			 * if(pc.getSecondWeapon()!=null){//�ΰ������� if(item.getId() ==
			 * pc.getSecondWeapon().getId()){//�������� �����ڰ� �������϶�
			 * pc.getInventory().setEquipped(item, false, false, false, true);
			 * }else{//�����ڰ� �����϶� pc.getInventory().setEquipped(item, false,
			 * false, false); } if(pc.getWeapon() !=null){
			 * if(pc.getWeapon().getItem().getItemId() == 7232){
			 * pc.getInventory().setEquipped(pc.getWeapon(), false, false,
			 * false); } } }else{//�ΰ�����������������.
			 * pc.getInventory().setEquipped(item, false, false, false); }
			 * }else{ pc.getInventory().setEquipped(item, false,false,false); }
			 * } } }
			 * 
			 * 
			 * } catch (Exception e) { e.printStackTrace(); }
			 */

			/*
			 * if(pc.getQuest().get_step(L1Quest.QUEST_��ǳ����������) != 1){ L1Quest
			 * qq = pc.getQuest(); qq.get_step(L1Quest.QUEST_��ǳ����������);
			 * qq.set_step(L1Quest.QUEST_��ǳ����������, 1);
			 * if(!pc.getInventory().checkItem(9094)){
			 * pc.getInventory().storeItem(9094,1); pc.sendPackets(new
			 * S_SystemMessage("��ǳ�� �������ڸ� ���� �����̽��ϴ�.")); } }
			 */

			if (LetterTable.getInstance().CheckNoReadLetter(pc.getName())) {
				S_SkillSound ss = new S_SkillSound(pc.getId(), 1091);
				pc.sendPackets(ss, true);
				S_ServerMessage sm = new S_ServerMessage(428);
				pc.sendPackets(sm, true);
			}
			// �׺��� �����ִ���
			if (CrockSystem.getInstance().isOpen()) {
				// ���� ��������
				if (CrockSystem.getInstance().isCrockIng()) {
					pc.sendPackets(new S_SystemMessage("�ð��� �տ��� ���Ƚ��ϴ�. �տ��� ���� �������� ������ �ð��� ����Ǿ� �ִ� �����Դϴ�."), true);
				} else {
					pc.sendPackets(new S_SystemMessage("�ð��� �տ��� ���Ƚ��ϴ�."), true);
				}
			}

			ExcludeTable.getInstance().load(pc);
			ExcludeLetterTable.getInstance().load(pc);

			if (pc.isGm()) {
				L1EvaSystem eva = EvaSystemTable.getInstance().getSystem(1);
				Timestamp ts = new Timestamp(eva.getEvaTime().getTimeInMillis());
				Date day = new Date(System.currentTimeMillis());
				if (ts.getMonth() == day.getMonth() && ts.getDate() == day.getDate()) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm");
					String time = dateFormat.format(ts);
					String time2 = dateFormat.format(new Timestamp(eva.bossTime));
					String time3 = dateFormat.format(new Timestamp(eva.bosscheck));
					pc.sendPackets(new S_SystemMessage("�տ�: " + time + " ����: " + time2 + " ����: " + time3), true);
					dateFormat = null;
				}
				ts = null;
				day = null;
			}
			pc.setSurvivalCry(sysTime);

			pc.sendPackets(new S_SPMR(pc), true);

			// 12_09
			for (L1Object obj : L1World.getInstance().getObject()) {
				if (obj == null)
					continue;
				if (obj instanceof L1DollInstance) {
					L1DollInstance doll = (L1DollInstance) obj;
					L1Character cha = doll.getMaster();
					if (cha == null || ((L1PcInstance) cha).getNetConnection() == null || cha.isDead())
						doll.deleteDoll();
					else if (doll.getMaster().getName().equalsIgnoreCase(charName))
						doll.deleteDoll();
				}
			}

			pc.encobjid = byteWrite(pc.getId());// ǥ�ļ���������

			InvSwapController.getInstance().toWorldJoin(pc);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clear();
		}

	}

	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c,
			0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e,
			0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0,
			0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2,
			0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4,
			0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6,
			0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
			0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff };

	private String byteWrite(long value) {
		long temp = value / 128;
		StringBuffer sb = new StringBuffer();
		if (temp > 0) {
			sb.append((byte) hextable[(int) value % 128]);
			while (temp >= 128) {
				sb.append((byte) hextable[(int) temp % 128]);
				temp = temp / 128;
			}
			if (temp > 0)
				sb.append((int) temp);
		} else {
			if (value == 0) {
				sb.append(0);
			} else {
				sb.append((byte) hextable[(int) value]);
				sb.append(0);
			}
		}
		return sb.toString();
	}

	private void PC����(L1PcInstance pc) {
		// TODO �ڵ� ������ �޼ҵ� ����
		pc.sendPackets(new S_PacketBox(S_PacketBox.PC�����, 1), true);
		pc.PC��_���� = true;
	}

	class earthCheck implements Runnable {

		private L1PcInstance pc = null;

		public earthCheck(L1PcInstance _pc) {
			pc = _pc;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			try {
				if (pc == null || pc.getNetConnection() == null)
					return;
				if (pc.getMapId() != 6202) {
					GeneralThreadPool.getInstance().schedule(this, 1000);
					return;
				}
				if (!pc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
					pc.getSkillEffectTimerSet().setSkillEffect(EARTH_BIND, 9999 * 1000);
					pc.sendPackets(new S_Poison(pc.getId(), 2), true);
					Broadcaster.broadcastPacket(pc, new S_Poison(pc.getId(), 2), true);
					pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, true), true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			GeneralThreadPool.getInstance().schedule(this, 1000);
		}

	}

	private void ���ȹ���(L1PcInstance pc) {
		// TODO �ڵ� ������ �޼ҵ� ����
		pc.sendPackets(new S_PacketBox(S_PacketBox.���ȹ���), true);
		pc.getAC().addAc(-1);
		pc.sendPackets(new S_OwnCharAttrDef(pc), true);
	}

	private void bapo(L1PcInstance pc) {
		int level = pc.getLevel();
		if (level <= 50) {
			pc.sendPackets(new S_PacketBox(S_PacketBox.BAPO, 6, true));
		}
		pc.setNBapoLevell(7);
	}

	/** ��Ʋ�� **/
	private void checkBattleZone(L1PcInstance pc) {
		if (pc.getMapId() == 5153) {
			L1Teleport.teleport(pc, 33437, 32800, (short) 4, 4, false);
		}
	}

	/** ��Ʋ�� **/
	private void hasadbuff(L1PcInstance pc) {
		try {
			if (pc.getAinHasad() >= 7000000) {
				pc.setAinHasad(7000000);
				pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc), true);
				return;
			}

			if (pc.getAinHasad() >= 2000000) {
				pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc), true);
				return;
			}
			int temp = 0;

			if (pc.getLogOutTime() != null) {
				temp = (int) ((System.currentTimeMillis() - pc.getLogOutTime().getTime()) / 900000);
			}

			int sum = pc.getAinHasad() + (temp * 10000);
			if (sum >= 2000000)
				pc.setAinHasad(2000000);
			else
				pc.setAinHasad(sum);

			pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc), true);
		} catch (Exception e) {
		}
	}

	private void items(L1PcInstance pc) {
		// DB�κ��� ĳ���Ϳ� â���� �������� �о���δ�
		CharacterTable.getInstance().restoreInventory(pc);

	}

	/*
	 * private int CheckMail(L1PcInstance pc){ int count = 0; Connection con =
	 * null; PreparedStatement pstm1 = null; ResultSet rs = null; try { con =
	 * L1DatabaseFactory.getInstance().getConnection(); pstm1 =
	 * con.prepareStatement (
	 * " SELECT count(*) as cnt FROM letter where receiver = ? AND isCheck = 0"
	 * ); pstm1.setString(1, pc.getName()); rs = pstm1.executeQuery();
	 * 
	 * if (rs.next()) { count = rs.getInt("cnt"); } } catch (SQLException e) {
	 * _log.log(Level.SEVERE, e.getLocalizedMessage(), e); } finally {
	 * SQLUtil.close(rs); SQLUtil.close(pstm1); SQLUtil.close(con); } return
	 * count; }
	 */

	private void bookmarks(L1PcInstance pc) {

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			// pstm =
			// con.prepareStatement("SELECT * FROM character_teleport WHERE
			// char_id=? ORDER BY name ASC");
			pstm = con.prepareStatement("SELECT * FROM character_teleport WHERE char_id=? ORDER BY id ASC");
			pstm.setInt(1, pc.getId());
			rs = pstm.executeQuery();

			L1BookMark bookmark = null;
			while (rs.next()) {
				bookmark = new L1BookMark();
				bookmark.setId(rs.getInt("id"));
				bookmark.setCharId(rs.getInt("char_id"));
				bookmark.setName(rs.getString("name"));
				bookmark.setLocX(rs.getInt("locx"));
				bookmark.setLocY(rs.getInt("locy"));
				bookmark.setMapId(rs.getShort("mapid"));
				bookmark.setRandomX(rs.getShort("randomX"));
				bookmark.setRandomY(rs.getShort("randomY"));
				bookmark.set_fast(rs.getShort("fast"));
				// S_Bookmarks s_bookmarks = new S_Bookmarks(bookmark.getName(),
				// bookmark.getLocX(), bookmark.getLocY(), bookmark.getMapId(),
				// bookmark.getId());
				pc.addBookMark(bookmark);
				// pc.sendPackets(s_bookmarks);
				// s_bookmarks.clear(); s_bookmarks = null;
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void skills(L1PcInstance pc) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM character_skills WHERE char_obj_id=?");
			pstm.setInt(1, pc.getId());
			rs = pstm.executeQuery();

			int i = 0;
			int lv1 = 0;
			int lv2 = 0;
			int lv3 = 0;
			int lv4 = 0;
			int lv5 = 0;
			int lv6 = 0;
			int lv7 = 0;
			int lv8 = 0;
			int lv9 = 0;
			int lv10 = 0;
			int lv11 = 0;
			int lv12 = 0;
			int lv13 = 0;
			int lv14 = 0;
			int lv15 = 0;
			int lv16 = 0;
			int lv17 = 0;
			int lv18 = 0;
			int lv19 = 0;
			int lv20 = 0;
			int lv21 = 0;
			int lv22 = 0;
			int lv23 = 0;
			int lv24 = 0;
			int lv25 = 0;
			int lv26 = 0;
			int lv27 = 0;
			int lv28 = 0;
			int lv29 = 0;
			int lv30 = 0;
			int lv31 = 0;
			int lv32 = 0;
			boolean passive = false;
			boolean ũ���� = false;
			boolean ǻ�� = false;
			boolean �����̾� = false;
			boolean �ƸӰ��� = false;
			boolean Ÿ��ź�� = false;
			boolean Ÿ��ź�� = false;
			boolean Ÿ��ź���� = false;
			boolean Ÿ��ź����¡ = false;
			L1Skills l1skills = null;
			while (rs.next()) {

				int skillId = rs.getInt("skill_id");
				try {
					l1skills = SkillsTable.getInstance().getTemplate(skillId);
					if (l1skills.getSkillLevel() == 1) {
						lv1 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 2) {
						lv2 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 3) {
						lv3 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 4) {
						lv4 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 5) {
						lv5 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 6) {
						lv6 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 7) {
						lv7 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 8) {
						lv8 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 9) {
						lv9 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 10) {
						lv10 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 11) {
						lv11 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 12) {
						lv12 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 13) {
						lv13 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 14) {
						lv14 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 15) {
						lv15 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 16) {
						lv16 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 17) {
						lv17 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 18) {
						lv18 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 19) {
						lv19 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 20) {
						lv20 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 21) {
						lv21 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 22) {
						lv22 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 23) {
						lv23 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 24) {
						lv24 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 25) {
						lv25 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 26) {
						lv26 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 27) {
						lv27 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 28) {
						lv28 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 29) {
						lv29 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 30) {
						lv30 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 31) {
						lv31 |= l1skills.getId();
					}
					if (l1skills.getSkillLevel() == 32) {
						lv32 |= l1skills.getId();
					}

					if (l1skills.getSkillLevel() == 99) {
						switch (l1skills.getId()) {
						case 1:
							ũ���� = true;
							pc.isCrash = true;
							break;
						case 2:
							ǻ�� = true;
							pc.isPurry = true;
							break;
						case 3:
							�����̾� = true;
							pc.isSlayer = true;
							break;
						case 5:
							�ƸӰ��� = true;
							pc.isAmorGaurd = true;
							break;
						case 6:
							Ÿ��ź�� = true;
							pc.isTaitanR = true;
							break;
						case 7:
							Ÿ��ź�� = true;
							pc.isTaitanB = true;
							break;
						case 8:
							Ÿ��ź���� = true;
							pc.isTaitanM = true;
							break;

						}
						passive = true;
					}

					i = lv1 + lv2 + lv3 + lv4 + lv5 + lv6 + lv7 + lv8 + lv9 + lv10 + lv11 + lv12 + lv13 + lv14 + lv15
							+ lv16 + lv17 + lv18 + lv19 + lv20 + lv21 + lv22 + lv23 + lv24 + lv25 + lv26 + lv27 + lv28
							+ lv29 + lv30 + lv31 + lv32;
					if (l1skills != null && pc.isDarkelf() && l1skills.getSkillLevel() == 51) {// �ٿ�
																								// ����
																								// �нú�
						pc.sendPackets(new S_CreateItem(145, 9, 0));
						pc.����¡ = true;
					}

					pc.setSkillMastery(skillId);
				} catch (Exception e) {
					// System.out.println(skillId);
				}
			}

			if (passive) {
				S_NewUI sn1 = new S_NewUI(ũ����, ǻ��, �����̾�, �ƸӰ���, Ÿ��ź��, Ÿ��ź��, Ÿ��ź����);
				pc.sendPackets(sn1);
				sn1 = null;
			}

			if (i > 0) {
				S_AddSkill as = new S_AddSkill(lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8, lv9, lv10, lv11, lv12, lv13,
						lv14, lv15, lv16, lv17, lv18, lv19, lv20, lv21, lv22, lv23, lv24, lv25, lv26, lv27, lv28, lv29,
						lv30, lv31, lv32, pc.getElfAttr());
				pc.sendPackets(as, true);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void huntoption(L1PcInstance pc) { // ����ɼ�
		if (pc.getHuntCount() == 1) {
			if (pc.isWizard() || pc.isIllusionist()) {
				if (pc.getHuntPrice() == 1000000) {
					pc.getAbility().addSp(1);
					pc.sendPackets(new S_SPMR(pc));
				}
			} else if (pc.isCrown() || pc.isKnight() || pc.isDarkelf() || pc.isDragonknight() || pc.isElf()
					|| pc.isWarrior()) {
				if (pc.getHuntPrice() == 1000000) {
					pc.addDmgup(1);
					pc.addBowDmgup(1);
					pc.addDamageReductionByArmor(1);
				}
			}
		}

		if (pc.getHuntCount() == 2) {
			if (pc.isWizard() || pc.isIllusionist()) {
				if (pc.getHuntPrice() == 5000000) {
					pc.getAbility().addSp(2);
					pc.sendPackets(new S_SPMR(pc));
				}
			} else if (pc.isCrown() || pc.isKnight() || pc.isDarkelf() || pc.isDragonknight() || pc.isElf()
					|| pc.isWarrior()) {
				if (pc.getHuntPrice() == 5000000) {
					pc.addDmgup(2);
					pc.addBowDmgup(2);
					pc.addDamageReductionByArmor(2);
				}
			}
		}
		if (pc.getHuntCount() == 3) {
			if (pc.isWizard() || pc.isIllusionist()) {
				if (pc.getHuntPrice() == 10000000) {
					pc.getAbility().addSp(3);
					pc.sendPackets(new S_SPMR(pc));
				}
			} else if (pc.isCrown() || pc.isKnight() || pc.isDarkelf() || pc.isDragonknight() || pc.isElf()
					|| pc.isWarrior()) {
				if (pc.getHuntPrice() == 10000000) {
					pc.addDmgup(3);
					pc.addBowDmgup(3);
					pc.addDamageReductionByArmor(3);
				}
			}
		}

	}

	private void serchSummon(L1PcInstance pc) {
		for (L1SummonInstance summon : L1World.getInstance().getAllSummons()) {
			if (summon.getMaster().getId() == pc.getId()) {
				summon.setMaster(pc);
				pc.addPet(summon);
				for (L1PcInstance visiblePc : L1World.getInstance().getVisiblePlayer(summon)) {
					S_SummonPack sp = new S_SummonPack(summon, visiblePc);
					visiblePc.sendPackets(sp, true);
				}
			}
		}
	}

	private void buff(LineageClient clientthread, L1PcInstance pc) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {

			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM character_buff WHERE char_obj_id=?");
			pstm.setInt(1, pc.getId());
			rs = pstm.executeQuery();
			int icon[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0 };

			while (rs.next()) {
				int skillid = rs.getInt("skill_id");
				int remaining_time = rs.getInt("remaining_time");
				if (skillid >= COOKING_1_0_N && skillid <= COOKING_1_6_N
						|| skillid >= COOKING_1_8_N && skillid <= COOKING_1_14_N
						|| skillid >= COOKING_1_16_N && skillid <= COOKING_1_22_N
						|| skillid >= COOKING_1_0_S && skillid <= COOKING_1_6_S
						|| skillid >= COOKING_1_8_S && skillid <= COOKING_1_14_S
						|| skillid >= COOKING_1_16_S && skillid <= COOKING_1_22_S) { // �丮(����Ʈ��
																						// �����ϴ�)
					L1Cooking.eatCooking(pc, skillid, remaining_time);
					continue;
				}
				if (skillid >= COOKING_NEW_�ѿ� && skillid <= COOKING_NEW_�߰��) {
					L1Cooking.newEatCooking(pc, skillid, remaining_time);
					continue;
				}
				if (skillid >= ���̸����Ѷ�� && skillid <= L1SkillId.���̽ÿ�������) {
					L1Cooking.newEatCooking(pc, skillid, remaining_time);
					continue;
				}
				switch (skillid) {
				case LIND_MAAN: {
					if (pc.getSkillEffectTimerSet().hasSkillEffect(LIND_MAAN)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(LIND_MAAN);
					}
					pc.getResistance().addSleep(15); // ���鳻��3
					icon[35] = (remaining_time + 16) / 32;
					icon[36] = 48;
				}
					break;
				case FAFU_MAAN: {
					if (pc.getSkillEffectTimerSet().hasSkillEffect(FAFU_MAAN)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(FAFU_MAAN);
					}
					pc.getResistance().addFreeze(15); // ��������3
					icon[35] = (remaining_time + 16) / 32;
					icon[36] = 47;
				}
					break;
				case ANTA_MAAN: {
					if (pc.getSkillEffectTimerSet().hasSkillEffect(ANTA_MAAN)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(ANTA_MAAN);
					}
					pc.getResistance().addHold(15);
					icon[35] = (remaining_time + 16) / 32;
					icon[36] = 46;
					// pc.addDamageReductionByArmor(3);
					// pc.getResistance().addPetrifaction(3); //��ȭ����3
				}
					break;
				case VALA_MAAN: {
					if (pc.getSkillEffectTimerSet().hasSkillEffect(VALA_MAAN)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(VALA_MAAN);
					}
					icon[35] = (remaining_time + 16) / 32;
					icon[36] = 49;
					// pc.addDamageReductionByArmor(3);
					pc.getResistance().addStun(15); // ���ϳ���3
				}
					break;
				case BIRTH_MAAN: {
					if (pc.getSkillEffectTimerSet().hasSkillEffect(BIRTH_MAAN)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(BIRTH_MAAN);
					}
					icon[35] = (remaining_time + 16) / 32;
					icon[36] = 50;
					// pc.addDamageReductionByArmor(5);
					pc.getResistance().addHold(15); // Ȧ��
					pc.getResistance().addFreeze(15); // ��������3
				}
					break;
				case SHAPE_MAAN: {
					if (pc.getSkillEffectTimerSet().hasSkillEffect(SHAPE_MAAN)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(SHAPE_MAAN);
					}
					icon[35] = (remaining_time + 16) / 32;
					icon[36] = 51;
					pc.getResistance().addHold(15); // Ȧ��
					pc.getResistance().addFreeze(15); // ��������3
					pc.getResistance().addSleep(15); // ���鳻��3
				}
					break;
				case LIFE_MAAN: {
					if (pc.getSkillEffectTimerSet().hasSkillEffect(LIFE_MAAN)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(LIFE_MAAN);
					}
					icon[35] = (remaining_time + 16) / 32;
					icon[36] = 52;
					pc.getResistance().addHold(15); // Ȧ��
					pc.getResistance().addFreeze(15); // ��������3
					pc.getResistance().addSleep(15); // ���鳻��3
					pc.getResistance().addStun(15); // ���ϳ���3
				}

				case HEUKSA:
					pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HEUKSA);
					pc.sendPackets(new S_PacketBox(S_PacketBox.UNLIMITED_ICON, 4914, remaining_time));
					break;
				case 999:// ���� ��ų���̵�
					int stime = (remaining_time / 4) - 2;
					pc.sendPackets(new S_DRAGONPERL(pc.getId(), 8), true);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONPERL, 8, stime), true);
					pc.set���ּӵ�(1);
					break;
				/*
				 * case DRAGON_EME_1: if(pc.getDETime2() != null){
				 * if(System.currentTimeMillis() > pc.getDETime2().getTime()){
				 * pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME, 0x01,
				 * 0)); pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.
				 * DRAGON_EME_1); }else{ long DETIME =
				 * pc.getDETime2().getTime()-System.currentTimeMillis() ;
				 * pc.getSkillEffectTimerSet
				 * ().removeSkillEffect(L1SkillId.DRAGON_EME_1);
				 * pc.getSkillEffectTimerSet
				 * ().setSkillEffect(L1SkillId.DRAGON_EME_1, (int)DETIME);
				 * pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME, 0x01,
				 * (int)DETIME/1000)); try { pc.save(); } catch (Exception e) {
				 * // TODO Auto-generated catch block e.printStackTrace(); } } }
				 * break; case DRAGON_EME_2: if(pc.getDETime() != null){
				 * if(System.currentTimeMillis() > pc.getDETime().getTime()){
				 * pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME, 0x02,
				 * 0)); pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.
				 * DRAGON_EME_2); }else{ long DETIME =
				 * pc.getDETime().getTime()-System.currentTimeMillis() ;
				 * pc.getSkillEffectTimerSet
				 * ().removeSkillEffect(L1SkillId.DRAGON_EME_2);
				 * pc.getSkillEffectTimerSet
				 * ().setSkillEffect(L1SkillId.DRAGON_EME_2, (int)DETIME);
				 * pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME, 0x02,
				 * (int)DETIME/1000)); try { pc.save(); } catch (Exception e) {
				 * // TODO Auto-generated catch block e.printStackTrace(); } } }
				 * break;
				 */

				case L1SkillId.STATUS_WISDOM_POTION:
					icon[38] = (remaining_time + 8) / 16;
					break;
				case DECREASE_WEIGHT:
					icon[0] = remaining_time / 16;
					break;
				case WEAKNESS:// ��ũ�Ͻ� //
					icon[4] = remaining_time / 4;
					pc.addDmgup(-5);
					pc.addHitup(-1);
					break;
				case BERSERKERS:// ����Ŀ�� //
					icon[7] = remaining_time / 4;
					pc.getAC().addAc(10);
					pc.addDmgup(2);
					pc.addHitup(8);
					break;
				case DISEASE:// ������ //
					icon[5] = remaining_time / 4;
					pc.addDmgup(-6);
					pc.getAC().addAc(12);
					break;
				case SILENCE:
					icon[2] = remaining_time / 4;
					break;
				case SHAPE_CHANGE:
					int poly_id = rs.getInt("poly_id");
					// pc.getGfxId().setTempCharGfx(poly_id);
					L1PolyMorph.doPoly(pc, poly_id, remaining_time, L1PolyMorph.MORPH_BY_ITEMMAGIC);
					continue;
				case DECAY_POTION:
					icon[1] = remaining_time / 4;
					break;
				case VENOM_RESIST:// ���� ������Ʈ //
					icon[3] = remaining_time / 4;
					break;
				case DRESS_EVASION:// �巹�� �̺����� //
					icon[6] = remaining_time / 4;
					break;
				case RESIST_MAGIC:// ������Ʈ ����
					pc.getResistance().addMr(10);
					pc.sendPackets(new S_ElfIcon(remaining_time / 16, 0, 0, 0), true);
					break;
				case ELEMENTAL_FALL_DOWN:
					icon[12] = remaining_time / 4;
					int playerAttr = pc.getElfAttr();
					int i = -50;
					switch (playerAttr) {
					case 0:
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						break;
					case 1:
						pc.getResistance().addEarth(i);
						pc.setAddAttrKind(1);
						break;
					case 2:
						pc.getResistance().addFire(i);
						pc.setAddAttrKind(2);
						break;
					case 4:
						pc.getResistance().addWater(i);
						pc.setAddAttrKind(4);
						break;
					case 8:
						pc.getResistance().addWind(i);
						pc.setAddAttrKind(8);
						break;
					default:
						break;
					}
					break;
				case CLEAR_MIND:// Ŭ���� ���ε�
					pc.getAbility().addAddedWis((byte) 3);
					pc.resetBaseMr();
					pc.sendPackets(new S_ElfIcon(0, remaining_time / 16, 0, 0), true);
					break;
				case RESIST_ELEMENTAL:// ������Ʈ ������Ż
					pc.getResistance().addAllNaturalResistance(10);
					pc.sendPackets(new S_ElfIcon(0, 0, remaining_time / 16, 0), true);
					break;
				case ELEMENTAL_PROTECTION:// �����ؼ� ���� ������Ż
					int attr = pc.getElfAttr();
					if (attr == 1) {
						pc.getResistance().addEarth(50);
					} else if (attr == 2) {
						pc.getResistance().addFire(50);
					} else if (attr == 4) {
						pc.getResistance().addWater(50);
					} else if (attr == 8) {
						pc.getResistance().addWind(50);
					}
					pc.sendPackets(new S_ElfIcon(0, 0, 0, remaining_time / 16), true);
					break;
				case ERASE_MAGIC:
					icon[10] = remaining_time / 4;
					break;
				case NATURES_TOUCH:// �����Ľ� ��ġ //
					icon[8] = remaining_time / 4;
					break;
				case WIND_SHACKLE:
					icon[9] = remaining_time / 4;
					break;
				case ELEMENTAL_FIRE:
					icon[13] = remaining_time / 4;
					break;
				case POLLUTE_WATER:// ����Ʈ ���� //
					icon[16] = remaining_time / 4;
					break;
				case STRIKER_GALE:// ��Ʈ����Ŀ ���� //
					icon[14] = remaining_time / 4;
					break;
				case SOUL_OF_FLAME:// �ҿ� ���� ������ //
					icon[15] = remaining_time / 4;
					break;
				case ADDITIONAL_FIRE:
					icon[11] = remaining_time / 16;
					break;
				case DRAGON_SKIN:// �巡�� ��Ų //
					icon[29] = remaining_time / 16;
					break;
				case GUARD_BREAK:// ���� �극��ũ //
					icon[28] = remaining_time / 4;
					pc.getAC().addAc(10);
					break;
				case FEAR:// �Ǿ� //
					icon[26] = remaining_time / 4;
					break;
				case MORTAL_BODY:// ��Ż�ٵ� //
					icon[24] = remaining_time / 4;
					break;
				case HORROR_OF_DEATH:// ȣ�� ���� ���� //
					icon[25] = remaining_time / 4;
					pc.getAbility().addAddedStr((byte) -3);
					pc.getAbility().addAddedInt((byte) -3);
					break;
				case CONCENTRATION:
					icon[21] = remaining_time / 16;
					break;
				case PATIENCE:// ���̼ǽ� //
					icon[27] = remaining_time / 4;
					break;
				case INSIGHT:
					icon[22] = remaining_time / 16;
					pc.getAbility().addAddedStr((byte) 1);
					pc.getAbility().addAddedDex((byte) 1);
					pc.getAbility().addAddedCon((byte) 1);
					pc.getAbility().addAddedInt((byte) 1);
					pc.getAbility().addAddedWis((byte) 1);
					pc.getAbility().addAddedCha((byte) 1);
					pc.resetBaseMr();
					break;
				case PANIC:
					icon[23] = remaining_time / 16;
					pc.getAbility().addAddedStr((byte) -1);
					pc.getAbility().addAddedDex((byte) -1);
					pc.getAbility().addAddedCon((byte) -1);
					pc.getAbility().addAddedInt((byte) -1);
					pc.getAbility().addAddedWis((byte) -1);
					pc.getAbility().addAddedCha((byte) -1);
					pc.resetBaseMr();
					break;
				case L1SkillId.BLOOD_LUST:
					pc.sendPackets(new S_SkillBrave(pc.getId(), 1, remaining_time), true);
					Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 1, 0), true);
					pc.getMoveState().setBraveSpeed(1);
					break;
				case STATUS_BRAVE:
					pc.sendPackets(new S_SkillBrave(pc.getId(), 1, remaining_time), true);
					Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 1, 0), true);
					pc.getMoveState().setBraveSpeed(1);
					break;
				case STATUS_HASTE:
					pc.sendPackets(new S_SkillHaste(pc.getId(), 1, remaining_time), true);
					Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 1, 0), true);
					pc.getMoveState().setMoveSpeed(1);
					break;
				case STATUS_BLUE_POTION:
				case STATUS_BLUE_POTION2:
				case STATUS_BLUE_POTION3:
					pc.sendPackets(new S_SkillIconGFX(34, remaining_time), true);
					break;
				case STATUS_ELFBRAVE:
					pc.sendPackets(new S_SkillBrave(pc.getId(), 3, remaining_time), true);
					Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 3, 0), true);
					pc.getMoveState().setBraveSpeed(1);
					break;
				case STATUS_CHAT_PROHIBITED:
					pc.sendPackets(new S_SkillIconGFX(36, remaining_time), true);
					break;
				case STATUS_TIKAL_BOSSDIE:
					icon[20] = (remaining_time + 8) / 16;
					L1SkillUse sk1 = new L1SkillUse();
					sk1.handleCommands(clientthread.getActiveChar(), skillid, pc.getId(), pc.getX(), pc.getY(), null,
							remaining_time, L1SkillUse.TYPE_LOGIN);
					sk1 = null;
					break;
				case STATUS_COMA_3:// �ڸ� 3
					icon[31] = (remaining_time + 16) / 32;
					icon[32] = 40;
					L1SkillUse sk2 = new L1SkillUse();
					sk2.handleCommands(clientthread.getActiveChar(), skillid, pc.getId(), pc.getX(), pc.getY(), null,
							remaining_time, L1SkillUse.TYPE_LOGIN);
					sk2 = null;
					break;
				case STATUS_COMA_5:// �ڸ� 5
					icon[31] = (remaining_time + 16) / 32;
					icon[32] = 41;
					L1SkillUse sk3 = new L1SkillUse();
					sk3.handleCommands(clientthread.getActiveChar(), skillid, pc.getId(), pc.getX(), pc.getY(), null,
							remaining_time, L1SkillUse.TYPE_LOGIN);
					sk3 = null;
					break;
				case SPECIAL_COOKING:
					if (pc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)) {
						if (pc.getSkillEffectTimerSet().getSkillEffectTimeSec(SPECIAL_COOKING) < remaining_time) {
							pc.getSkillEffectTimerSet().setSkillEffect(SPECIAL_COOKING, remaining_time * 1000);
						}
					}
					continue;
				case L1SkillId.STATUS_�ÿ��Ѿ�������:
					icon[18] = (remaining_time + 8) / 16;
					icon[19] = 0x4C;
					pc.addDmgup(2);
					pc.getAbility().addSp(2);
					pc.addHpr(1);
					pc.addMpr(1);
					pc.sendPackets(new S_SPMR(pc), true);
					break;
				case STATUS_CASHSCROLL:// ü�������ֹ��� //
					icon[18] = (remaining_time + 8) / 16;
					pc.addHpr(4);
					pc.addMaxHp(50);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
					if (pc.isInParty()) {
						pc.getParty().updateMiniHP(pc);
					}
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
					break;
				case STATUS_CASHSCROLL2:// ���������ֹ��� //
					icon[18] = (remaining_time + 8) / 16;
					icon[19] = 1;
					pc.addMpr(4);
					pc.addMaxMp(40);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
					break;
				case STATUS_CASHSCROLL3:
					icon[18] = (remaining_time + 8) / 16;
					icon[19] = 2;
					pc.addDmgup(3);
					pc.addHitup(3);
					pc.getAbility().addSp(3);
					pc.sendPackets(new S_SPMR(pc), true);
					break;
				case STATUS_FRUIT:// ���׵�� //
					if (pc.isDragonknight()) {
						pc.sendPackets(new S_SkillBrave(pc.getId(), 1, remaining_time), true);
						Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 1, 0), true);
						pc.getMoveState().setBraveSpeed(1);
					} else if (pc.isIllusionist()) {
						icon[30] = remaining_time / 4;
						// pc.sendPackets(new S_SkillBrave(pc.getId(), 1,
						// remaining_time), true);
						Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 1, 0), true);

						// pc.sendPackets(new S_SkillBrave(pc.getId(), 4,
						// remaining_time), true);
						// Broadcaster.broadcastPacket(pc, new
						// S_SkillBrave(pc.getId(), 4, 0), true);
					}
					break;
				case EXP_POTION:
				case L1SkillId.EXP_POTION_cash:
					if (pc.getMap().isSafetyZone(pc.getX(), pc.getY())) {
						pc.sendPackets(new S_PacketBox(1, remaining_time, true, true, true));
						pc.sendPackets(new S_PacketBox(2, remaining_time, true, true, true));
					} else {
						pc.sendPackets(new S_PacketBox(1, remaining_time, true, true, true));
					}
					// icon[38] = remaining_time;
					break;

				/*
				 * icon[17] = remaining_time/16; break;
				 */
				case EXP_POTION2:
					icon[17] = remaining_time / 16;
					break;
				case EXP_POTION3:
					icon[17] = remaining_time / 16;
					break;
				case FEATHER_BUFF_A:// ��� ���� ���� ���� // �ſ�����
					icon[33] = remaining_time / 16;
					icon[34] = 70;
					pc.addDmgup(2);
					pc.addHitup(2);
					pc.getAbility().addSp(2);
					pc.sendPackets(new S_SPMR(pc), true);
					pc.addHpr(3);
					pc.addMaxHp(50);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
					if (pc.isInParty()) {
						pc.getParty().updateMiniHP(pc);
					}
					pc.addMpr(3);
					pc.addMaxMp(30);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
					break;
				case FEATHER_BUFF_B:// ��� ���� ���� ���� // ����
					icon[33] = remaining_time / 16;
					icon[34] = 71;
					pc.addHitup(2);
					pc.getAbility().addSp(1);
					pc.sendPackets(new S_SPMR(pc), true);
					pc.addMaxHp(50);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
					if (pc.isInParty()) {
						pc.getParty().updateMiniHP(pc);
					}
					pc.addMaxMp(30);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
					break;
				case FEATHER_BUFF_C:// ��� ���� ���� ���� // ����
					icon[33] = remaining_time / 16;
					icon[34] = 72;
					pc.addMaxHp(50);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
					if (pc.isInParty()) {
						pc.getParty().updateMiniHP(pc);
					}
					pc.addMaxMp(30);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
					pc.getAC().addAc(-2);
					break;
				case L1SkillId.STATUS_UNDERWATER_BREATH:
					pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), remaining_time), true);
					break;
				case FEATHER_BUFF_D:// ��� ���� ���� ���� // ����
					icon[33] = remaining_time / 16;
					icon[34] = 73;
					pc.getAC().addAc(-1);
					break;
				case L1SkillId.���������ʽ�://
					pc.sendPackets(new S_PacketBox(remaining_time, true, true), true);
					break;
				case L1SkillId.PHYSICAL_ENCHANT_STR:
					pc.getAbility().addAddedStr((byte) 5);
					pc.sendPackets(new S_Strup(pc, 5, remaining_time), true);
					break;
				case L1SkillId.PHYSICAL_ENCHANT_DEX:
					pc.getAbility().addAddedDex((byte) 5);
					pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
					pc.sendPackets(new S_Dexup(pc, 5, remaining_time), true);
					break;
				case L1SkillId.����Ǳ��:
					pc.getAC().addAc(-2);
					pc.addMaxHp(20);
					pc.addMaxMp(13);
					pc.getResistance().addBlind(10);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					pc.sendPackets(new S_OwnCharStatus(pc));
					icon[37] = remaining_time / 16;
					pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.����Ǳ��, remaining_time * 1000);
					break;
				default:
					L1SkillUse sk111 = new L1SkillUse();
					sk111.handleCommands(clientthread.getActiveChar(), skillid, pc.getId(), pc.getX(), pc.getY(), null,
							remaining_time, L1SkillUse.TYPE_LOGIN);
					sk111 = null;
					continue;
				}
				pc.getSkillEffectTimerSet().setSkillEffect(skillid, remaining_time * 1000);
			}
			S_UnityIcon uni = new S_UnityIcon(icon[0], icon[1], icon[2], icon[3], icon[4], icon[5], icon[6], icon[7],
					icon[8], icon[9], icon[10], icon[11], icon[12], icon[13], icon[14], icon[15], icon[16], icon[17],
					icon[18], icon[19], icon[20], icon[21], icon[22], icon[23], icon[24], icon[25], icon[26], icon[27],
					icon[28], icon[29], icon[30], icon[31], icon[32], icon[33], icon[34], icon[35], icon[36], icon[37],
					icon[38]);
			pc.sendPackets(uni, true);
			icon = null;
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

		/*
		 * { Random random = new Random(); int chance = random.nextInt(3); {
		 * switch (chance) { case 0: { pc.sendPackets(new
		 * S_PacketBox(S_PacketBox.GREEN_MESSAGE,"WOLF ������ ���Ű� ȯ���մϴ�."));
		 * 
		 * }
		 * 
		 * break; case 1: { pc.sendPackets(new
		 * S_PacketBox(S_PacketBox.GREEN_MESSAGE,"WOLF ������ ���Ű� ȯ���մϴ�.")); }
		 * break; case 2: { pc.sendPackets(new
		 * S_PacketBox(S_PacketBox.GREEN_MESSAGE,"WOLF ������ ���Ű� ȯ���մϴ�.")); }
		 * break; } } }
		 */
	}

	class charNameDelete implements Runnable {
		private String name = null;

		public charNameDelete(String _name) {
			name = _name;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			try {
				if (nameList.contains(name))
					nameList.remove(name);
			} catch (Exception e) {
				System.out.println("���� �̸� ����Ʈ���� ���� ���� : " + name);
			}
		}

	}

	@Override
	public String getType() {
		return C_LOGIN_TO_SERVER;
	}
}