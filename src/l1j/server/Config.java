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
 * http://www.gnu.org/copyleft/gpl.html1
 */
package l1j.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.datatables.ExpTable;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.utils.IntRange;
import server.GameServer;

public final class Config {
	public static IND_Q _IND_Q = null;
	public static quit_Q _quit_Q = null;
	public static INN_Q _INN_Q = null;
	private static final Logger _log = Logger.getLogger(Config.class.getName());
	public static int craft = 1005;
	public static boolean mainenc = false;
	public static boolean STANDBY_SERVER = false;
	public static int test222 = 3;
	public static boolean 새로운패킷구조 = false;
	public static boolean 서버패킷출력 = false;
	public static boolean 클라패킷출력 = false;
	private static final String servername = "테스트";

	public static final byte[] FIRST_PACKET = { (byte) 0x0b, (byte) 0x00, // size

			(byte) 0x4F, (byte) 0x3E, (byte) 0x62, (byte) 0x79, (byte) 0x74, (byte) 0xC4, (byte) 0xFA, (byte) 0x19,
			(byte) 0x35 };

	public static long SeedVal = 0x7479623E; // 1020

	public static int 이름확인(char[] _values) {
		for (char value : _values) {
			if ((value >= 'A' && value <= 'z') || (value >= 'a' && value <= 'z')) {
				// System.out.println("영어 : "+value);
			} else if (value >= '0' && value <= '9') {
				// System.out.println("숫자 : "+value);
			} else if (value >= '\uAC00' && value <= '\uD7A3') {
				// System.out.println("한글 : "+value);
			} else {
				return -1;
			}
		}
		return 0;
		// .... 이하 생략
	}

	public static ArrayList<Integer> spractionerr = new ArrayList<Integer>();

	public static int teste = 8700;
	public static int test = 0;
	public static int aaaaa = 0;

	public synchronized static void addaa() {
		aaaaa++;
	}

	public static int iitest = 0;

	public static String getserver() {
		return servername;
	}

	/** Debug/release mode */
	public static final boolean DEBUG = false;
	// public static boolean 패킷로그 = true;//로그

	public static final int 정무시간 = 60 * 90;
	public static final int PC정무시간 = 60 * 30;

	public static final int 계정_용의둥지_시간 = 60 * 60 * 3;
	public static final int PC_용의둥지_시간 = 60 * 60 * 3;
	
	public static int 기란감옥던전시간 = 3600;
	public static int 상아탑발록진영시간 = 3600;
	public static int 상아탑야히진영시간 = 3600;
	public static int 얼음수정동굴시간 = 3600;
	public static int 수상한천상의계곡시간 = 3600;
	public static int 말섬던전시간 = 3600;
	public static int 몽환의섬시간 = 3600;
	public static int 라스타바드던전시간 = 3600;

	protected static ArrayList<L1PcInstance> 혈맹채팅모니터 = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> 파티채팅모니터 = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> 귓말채팅모니터 = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> 인첸채팅모니터 = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> 삭제채팅모니터 = new ArrayList<L1PcInstance>();
	protected static ArrayList<L1PcInstance> 접속채팅모니터 = new ArrayList<L1PcInstance>();

	protected static ArrayList<L1PcInstance> 버그채팅모니터 = new ArrayList<L1PcInstance>();

	public static int 혈맹채팅모니터() {
		return 혈맹채팅모니터.size();
	}

	public static int 파티채팅모니터() {
		return 파티채팅모니터.size();
	}

	public static int 귓말채팅모니터() {
		return 귓말채팅모니터.size();
	}

	public static int 인첸채팅모니터() {
		return 인첸채팅모니터.size();
	}

	public static int 삭제채팅모니터() {
		return 삭제채팅모니터.size();
	}

	public static int 접속채팅모니터() {
		return 접속채팅모니터.size();
	}

	public static int 버그채팅모니터() {
		return 버그채팅모니터.size();
	}

	public static L1PcInstance[] toArray혈맹채팅모니터() {
		return 혈맹채팅모니터.toArray(new L1PcInstance[혈맹채팅모니터.size()]);
	}

	public static L1PcInstance[] toArray파티채팅모니터() {
		return 파티채팅모니터.toArray(new L1PcInstance[파티채팅모니터.size()]);
	}

	public static L1PcInstance[] toArray귓말채팅모니터() {
		return 귓말채팅모니터.toArray(new L1PcInstance[귓말채팅모니터.size()]);
	}

	public static L1PcInstance[] toArray인첸채팅모니터() {
		return 인첸채팅모니터.toArray(new L1PcInstance[인첸채팅모니터.size()]);
	}

	public static L1PcInstance[] toArray삭제채팅모니터() {
		return 삭제채팅모니터.toArray(new L1PcInstance[삭제채팅모니터.size()]);
	}

	public static L1PcInstance[] toArray접속채팅모니터() {
		return 접속채팅모니터.toArray(new L1PcInstance[접속채팅모니터.size()]);
	}

	public static L1PcInstance[] toArray버그채팅모니터() {
		return 버그채팅모니터.toArray(new L1PcInstance[버그채팅모니터.size()]);
	}

	public static void add전체(L1PcInstance pc) {
		if (!혈맹채팅모니터.contains(pc)) {
			혈맹채팅모니터.add(pc);
			;
		}
		if (!파티채팅모니터.contains(pc)) {
			파티채팅모니터.add(pc);
			;
		}
		if (!귓말채팅모니터.contains(pc)) {
			귓말채팅모니터.add(pc);
			;
		}
		if (!인첸채팅모니터.contains(pc)) {
			인첸채팅모니터.add(pc);
			;
		}
		if (!삭제채팅모니터.contains(pc)) {
			삭제채팅모니터.add(pc);
			;
		}
		if (!접속채팅모니터.contains(pc)) {
			접속채팅모니터.add(pc);
			;
		}
		if (!버그채팅모니터.contains(pc)) {
			버그채팅모니터.add(pc);
			;
		}
	}

	public static void remove전체(L1PcInstance pc) {
		if (혈맹채팅모니터.contains(pc)) {
			혈맹채팅모니터.remove(pc);
			;
		}
		if (파티채팅모니터.contains(pc)) {
			파티채팅모니터.remove(pc);
			;
		}
		if (귓말채팅모니터.contains(pc)) {
			귓말채팅모니터.remove(pc);
			;
		}
		if (인첸채팅모니터.contains(pc)) {
			인첸채팅모니터.remove(pc);
			;
		}
		if (삭제채팅모니터.contains(pc)) {
			삭제채팅모니터.remove(pc);
			;
		}
		if (접속채팅모니터.contains(pc)) {
			접속채팅모니터.remove(pc);
			;
		}
		if (버그채팅모니터.contains(pc)) {
			버그채팅모니터.remove(pc);
			;
		}
	}

	public static void add혈맹(L1PcInstance pc) {
		if (혈맹채팅모니터.contains(pc)) {
			return;
		}
		혈맹채팅모니터.add(pc);
	}

	public static void remove혈맹(L1PcInstance pc) {
		if (!혈맹채팅모니터.contains(pc)) {
			return;
		}
		혈맹채팅모니터.remove(pc);
	}

	public static void add파티(L1PcInstance pc) {
		if (파티채팅모니터.contains(pc)) {
			return;
		}
		파티채팅모니터.add(pc);
	}

	public static void remove파티(L1PcInstance pc) {
		if (!파티채팅모니터.contains(pc)) {
			return;
		}
		파티채팅모니터.remove(pc);
	}

	public static void add귓말(L1PcInstance pc) {
		if (귓말채팅모니터.contains(pc)) {
			return;
		}
		귓말채팅모니터.add(pc);
	}

	public static void remove귓말(L1PcInstance pc) {
		if (!귓말채팅모니터.contains(pc)) {
			return;
		}
		귓말채팅모니터.remove(pc);
	}

	public static void add인첸(L1PcInstance pc) {
		if (인첸채팅모니터.contains(pc)) {
			return;
		}
		인첸채팅모니터.add(pc);
	}

	public static void remove인첸(L1PcInstance pc) {
		if (!인첸채팅모니터.contains(pc)) {
			return;
		}
		인첸채팅모니터.remove(pc);
	}

	public static void add삭제(L1PcInstance pc) {
		if (삭제채팅모니터.contains(pc)) {
			return;
		}
		삭제채팅모니터.add(pc);
	}

	public static void remove삭제(L1PcInstance pc) {
		if (!삭제채팅모니터.contains(pc)) {
			return;
		}
		삭제채팅모니터.remove(pc);
	}

	public static void add접속(L1PcInstance pc) {
		if (접속채팅모니터.contains(pc)) {
			return;
		}
		접속채팅모니터.add(pc);
	}

	public static void remove접속(L1PcInstance pc) {
		if (!접속채팅모니터.contains(pc)) {
			return;
		}
		접속채팅모니터.remove(pc);
	}

	public static void add버그(L1PcInstance pc) {
		if (버그채팅모니터.contains(pc)) {
			return;
		}
		버그채팅모니터.add(pc);
	}

	public static void remove버그(L1PcInstance pc) {
		if (!버그채팅모니터.contains(pc)) {
			return;
		}
		버그채팅모니터.remove(pc);
	}
	public static int PROTECT_CLAN_ID;
	public static int AUTO_REMOVECLAN;
	public static boolean 신규혈맹보호처리;

	public static boolean 뚫어방어사용;
	public static boolean 유체이탈방어사용;

	/** Thread pools size */
	public static int THREAD_P_EFFECTS;
	public static int THREAD_P_GENERAL;
	public static int AI_MAX_THREAD;
	public static int THREAD_P_TYPE_GENERAL;
	public static int THREAD_P_SIZE_GENERAL;

	/** Server control */
	public static int GAME_SERVER_TYPE;
	public static String GAME_SERVER_HOST_NAME;
	public static int GAME_SERVER_PORT;
	public static String DB_DRIVER;
	public static String DB_URL;
	public static String DB_LOGIN;
	public static String DB_PASSWORD;
	public static String TIME_ZONE;
	public static int CLIENT_LANGUAGE;

	public static boolean HOSTNAME_LOOKUPS;
	public static int AUTOMATIC_KICK;
	public static boolean AUTO_CREATE_ACCOUNTS;
	public static short MAX_ONLINE_USERS;
	public static boolean CACHE_MAP_FILES;
	public static boolean LOAD_V2_MAP_FILES;
	public static boolean CHECK_MOVE_INTERVAL;
	public static boolean CHECK_ATTACK_INTERVAL;
	public static boolean CHECK_SPELL_INTERVAL;
	public static short INJUSTICE_COUNT;
	public static int JUSTICE_COUNT;
	public static int CHECK_STRICTNESS;
	public static byte LOGGING_WEAPON_ENCHANT;
	public static byte LOGGING_ARMOR_ENCHANT;
	public static int LOGGING_TIME;
	public static boolean LOGGING_CHAT_NORMAL;
	public static boolean LOGGING_CHAT_WHISPER;
	public static boolean LOGGING_CHAT_SHOUT;
	public static boolean LOGGING_CHAT_WORLD;
	public static boolean LOGGING_CHAT_CLAN;
	public static boolean LOGGING_CHAT_PARTY;
	public static boolean LOGGING_CHAT_COMBINED;
	public static boolean LOGGING_CHAT_CHAT_PARTY;
	public static int ENCHANT_CHANCE_ACCESSORY;

	public static boolean 배틀존작동유무;
	public static int 배틀존입장레벨;
	public static String 배틀존아이템;
	public static String 배틀존아이템갯수;

	public static boolean Tam_Ok;
	public static int Tam_Time;
	public static int Tam_Count;

	public static int D_Reset_Time;

	public static boolean Event_Box;

	public static int AUTOSAVE_INTERVAL;
	public static int AUTOSAVE_INTERVAL_INVENTORY;
	public static int SKILLTIMER_IMPLTYPE;
	public static int NPCAI_IMPLTYPE;
	public static boolean TELNET_SERVER;
	public static int TELNET_SERVER_PORT;
	public static int PC_RECOGNIZE_RANGE;
	public static boolean CHARACTER_CONFIG_IN_SERVER_SIDE;
	public static boolean ALLOW_2PC;
	public static int LEVEL_DOWN_RANGE;
	public static boolean SEND_PACKET_BEFORE_TELEPORT;
	public static boolean DETECT_DB_RESOURCE_LEAKS;
	public static boolean AUTO_CHECK;

	public static boolean WAR_TIME_AUTO_SETTING;

	/** Rate control */
	public static int 스냅퍼최대인챈;
	public static int 룸티스최대인챈;
	public static int 장신구최대인챈;
	public static double RATE_XP;
	public static double RATE_XP1;
	public static double RATE_LAWFUL;
	public static double RATE_KARMA;
	public static double RATE_DROP_ADENA;
	public static double RATE_DROP_ITEMS;
	public static int RATE_ROBOT_TIME; // 무인PC(쿠우)
	public static int ENCHANT_CHANCE_WEAPON;
	public static int ENCHANT_CHANCE_ARMOR;
	public static double RATE_WEIGHT_LIMIT;
	public static double RATE_WEIGHT_LIMIT_PET;
	public static double RATE_SHOP_SELLING_PRICE;
	public static double RATE_SHOP_PURCHASING_PRICE;
	public static int RATE_DREAM; // 만월의정기 이벤트드랍
	public static int CREATE_CHANCE_DIARY;
	public static int CREATE_CHANCE_RECOLLECTION;
	public static int CREATE_CHANCE_MYSTERIOUS;
	public static int CREATE_CHANCE_PROCESSING;
	public static int CREATE_CHANCE_PROCESSING_DIAMOND;
	public static int CREATE_CHANCE_DANTES;
	public static int CREATE_CHANCE_ANCIENT_AMULET;
	public static int CREATE_CHANCE_HISTORY_BOOK;
	public static int MAX_WEAPON;
	public static int MAX_ARMOR;
	public static int MAX_WEAPON1; // 마족무기 인첸제한
	public static int MAX_LEVEL; // 신규보호렙제
	public static int FEATHER_TIME;
	public static int FEATHER_NUMBER;
	public static int CLAN_NUMBER;
	public static int CASTLE_NUMBER;
	public static double RATE_CLAN_XP;
	public static double RATE_CASTLE_XP;
	public static double RATE_7_DMG_RATE;// 인첸추타 외부화
	public static int RATE_7_DMG_PER;
	public static double RATE_8_DMG_RATE;
	public static int RATE_8_DMG_PER;
	public static double RATE_9_DMG_RATE;
	public static int RATE_9_DMG_PER;
	public static double RATE_10_DMG_RATE;
	public static int RATE_10_DMG_PER;
	public static double RATE_11_DMG_RATE;
	public static int RATE_11_DMG_PER;
	public static double RATE_12_DMG_RATE;
	public static int RATE_12_DMG_PER;
	public static double RATE_13_DMG_RATE;
	public static int RATE_13_DMG_PER;
	public static double RATE_14_DMG_RATE;
	public static int RATE_14_DMG_PER;
	public static double RATE_15_DMG_RATE;
	public static int RATE_15_DMG_PER;
	public static double RATE_16_DMG_RATE;
	public static int RATE_16_DMG_PER;
	public static double RATE_17_DMG_RATE;
	public static int RATE_17_DMG_PER;
	public static double RATE_18_DMG_RATE;
	public static int RATE_18_DMG_PER;
	public static int AC_170;
	public static int AC_160;
	public static int AC_150;
	public static int AC_140;
	public static int AC_130;
	public static int AC_120;
	public static int AC_110;
	public static int AC_100;
	public static int AC_90;
	public static int AC_80;
	public static int AC_70;
	public static int AC_60;
	public static int AC_50;
	public static int AC_40;
	public static int AC_30;
	public static int AC_20;
	public static int AC_10;

	/** AltSettings control */
	public static short GLOBAL_CHAT_LEVEL;
	public static short WHISPER_CHAT_LEVEL;
	public static byte AUTO_LOOT;
	public static int LOOTING_RANGE;
	public static boolean ALT_NONPVP;
	public static boolean ALT_ATKMSG;
	public static boolean CHANGE_TITLE_BY_ONESELF;
	public static int MAX_CLAN_MEMBER;
	public static boolean CLAN_ALLIANCE;
	public static int MAX_PT;
	public static int MAX_CHAT_PT;
	public static boolean SIM_WAR_PENALTY;
	public static boolean GET_BACK;
	public static String ALT_ITEM_DELETION_TYPE;
	public static int ALT_ITEM_DELETION_TIME;
	public static int ALT_ITEM_DELETION_RANGE;
	public static int ALT_PRIVATE_WAREHOUSE_LEVEL;
	public static int ALT_PRIVATE_SHOP_LEVEL;

	public static int 수련자무기밸런스수치;
	public static int 수련자갑옷밸런스수치;

	public static L1NpcInstance 아놀드상점 = null;

	public static boolean 아놀드이벤트;
	
	public static int 영생의빛;
	public static int 생명의나뭇잎;
	public static int 무혈상점;
	public static int 이레매직확률;
	public static int 데페확률;
	public static int 그립확률;
	public static int 스턴확률;
	public static int 어바확률;
	public static int 검스턴확률;
	public static double 뮨데미지;
	public static int 드랍레벨;

	/****** 이벤트 ******/
	public static boolean ALT_HALLOWEENEVENT; // 할로윈
	public static boolean ALT_HALLOWEENEVENT2009; // 할로윈(2009년)
	public static boolean ALT_FANTASYEVENT; // 환상
	public static boolean ALT_CHUSEOKEVENT; // 추석(09.09.24)
	public static boolean ALT_FEATURE;

	public static boolean ALT_WHO_COMMAND;
	public static boolean ALT_REVIVAL_POTION;
	public static int ALT_WAR_TIME;
	public static int ALT_WAR_TIME_UNIT;
	public static int ALT_WAR_INTERVAL;
	public static int ALT_WAR_INTERVAL_UNIT;
	public static int ALT_RATE_OF_DUTY;
	public static boolean SPAWN_HOME_POINT;
	public static int SPAWN_HOME_POINT_RANGE;
	public static int SPAWN_HOME_POINT_COUNT;
	public static int SPAWN_HOME_POINT_DELAY;
	public static boolean INIT_BOSS_SPAWN;
	public static int ELEMENTAL_STONE_AMOUNT;
	public static int HOUSE_TAX_INTERVAL;
	public static int MAX_DOLL_COUNT;
	public static boolean RETURN_TO_NATURE;
	public static int MAX_NPC_ITEM;
	public static int MAX_PERSONAL_WAREHOUSE_ITEM;
	public static int MAX_CLAN_WAREHOUSE_ITEM;
	public static boolean DELETE_CHARACTER_AFTER_7DAYS;
	public static int GMCODE;
	public static int DELETE_DB_DAYS;
	public static boolean isGmchat = true; // by판도라 영자채팅 녹색패킷
	public static byte MAX_버모스_DUNGEON_LEVEL;

	public static byte MAX_후오스_DUNGEON_LEVEL;

	public static byte MIN_상아탑_DUNGEON_LEVEL;

	public static boolean 캐릭터비번사용여부 = true;

	public static boolean ACCOUNT_PASSWORD;// 패스워드 암호화 삭제 소스 0813 추가

	/** CharSettings control */
	public static int PRINCE_MAX_HP;

	public static int MAXLEVEL;

	public static int PRINCE_MAX_MP;
	public static int KNIGHT_MAX_HP;
	public static int KNIGHT_MAX_MP;
	public static int ELF_MAX_HP;
	public static int ELF_MAX_MP;
	public static int WIZARD_MAX_HP;
	public static int WIZARD_MAX_MP;
	public static int DARKELF_MAX_HP;
	public static int DARKELF_MAX_MP;
	public static int DRAGONKNIGHT_MAX_HP;
	public static int DRAGONKNIGHT_MAX_MP;
	public static int BLACKWIZARD_MAX_HP;
	public static int BLACKWIZARD_MAX_MP;

	public static int LV50_EXP;
	public static int LV51_EXP;
	public static int LV52_EXP;
	public static int LV53_EXP;
	public static int LV54_EXP;
	public static int LV55_EXP;
	public static int LV56_EXP;
	public static int LV57_EXP;
	public static int LV58_EXP;
	public static int LV59_EXP;
	public static int LV60_EXP;
	public static int LV61_EXP;
	public static int LV62_EXP;
	public static int LV63_EXP;
	public static int LV64_EXP;
	public static int LV65_EXP;
	public static int LV66_EXP;
	public static int LV67_EXP;
	public static int LV68_EXP;
	public static int LV69_EXP;
	public static int LV70_EXP;
	public static int LV71_EXP;
	public static int LV72_EXP;
	public static int LV73_EXP;
	public static int LV74_EXP;
	public static int LV75_EXP;
	public static int LV76_EXP;
	public static int LV77_EXP;
	public static int LV78_EXP;
	public static int LV79_EXP;
	public static int LV80_EXP;
	public static int LV81_EXP;
	public static int LV82_EXP;
	public static int LV83_EXP;
	public static int LV84_EXP;
	public static int LV85_EXP;
	public static int LV86_EXP;
	public static int LV87_EXP;
	public static int LV88_EXP;
	public static int LV89_EXP;
	public static int LV90_EXP;
	public static int LV91_EXP;
	public static int LV92_EXP;
	public static int LV93_EXP;
	public static int LV94_EXP;
	public static int LV95_EXP;
	public static int LV96_EXP;
	public static int LV97_EXP;
	public static int LV98_EXP;
	public static int LV99_EXP;
	public static int EXP_GIVE;

	public static int 레이드시간;
	public static int 아놀드이벤트시간;

	/** 데이터베이스 풀 관련 */
	public static int min;
	public static int max;
	public static boolean run;

	/** Configuration files */
	public static final String SERVER_CONFIG_FILE = "./config/server.properties";
	public static final String RATES_CONFIG_FILE = "./config/rates.properties";
	public static final String ALT_SETTINGS_FILE = "./config/altsettings.properties";
	public static final String CHAR_SETTINGS_CONFIG_FILE = "./config/charsettings.properties";
	public static boolean shutdown = false;
	// 로그 표현할것인지
	public static boolean LOGGER = true;
	// 패킷 표현 할것인지
	public static boolean PACKET = false;
	/** 그 외의 설정 */

	// NPC로부터 들이마실 수 있는 MP한계
	public static final int MANA_DRAIN_LIMIT_PER_NPC = 40;

	// 1회의 공격으로 들이마실 수 있는 MP한계(SOM, 강철 SOM)
	public static final int MANA_DRAIN_LIMIT_PER_SOM_ATTACK = 9;

	public static void load() {
		_log.info("loading gameserver config");
		// server.properties
		try {
			Properties serverSettings = new Properties();
			InputStream is = new FileInputStream(new File(SERVER_CONFIG_FILE));
			serverSettings.load(is);
			is.close();

			/** 데이터 베이스 풀 */
			min = Integer.parseInt(serverSettings.getProperty("min"));
			max = Integer.parseInt(serverSettings.getProperty("max"));
			run = Boolean.parseBoolean(serverSettings.getProperty("run"));

			캐릭터비번사용여부 = Boolean.parseBoolean(serverSettings.getProperty("Charpass"));

			GAME_SERVER_TYPE = Integer.parseInt(serverSettings.getProperty("ServerType", "0"));

			GAME_SERVER_HOST_NAME = serverSettings.getProperty("GameserverHostname", "*");

			GAME_SERVER_PORT = Integer.parseInt(serverSettings.getProperty("GameserverPort", "2000"));

			DB_DRIVER = serverSettings.getProperty("Driver", "com.mysql.jdbc.Driver");

			DB_URL = serverSettings.getProperty("URL",
					"jdbc:mysql://localhost/l1jdb?useUnicode=true&characterEncoding=euckr");

			DB_LOGIN = serverSettings.getProperty("Login", "root");

			DB_PASSWORD = serverSettings.getProperty("Password", "");

			THREAD_P_TYPE_GENERAL = Integer.parseInt(serverSettings.getProperty("GeneralThreadPoolType", "0"), 10);

			THREAD_P_SIZE_GENERAL = Integer.parseInt(serverSettings.getProperty("GeneralThreadPoolSize", "0"), 10);

			CLIENT_LANGUAGE = Integer.parseInt(serverSettings.getProperty("ClientLanguage", "4"));

			TIME_ZONE = serverSettings.getProperty("TimeZone", "KST");

			HOSTNAME_LOOKUPS = Boolean.parseBoolean(serverSettings.getProperty("HostnameLookups", "false"));

			뚫어방어사용 = Boolean.parseBoolean(serverSettings.getProperty("dduldef", "true"));
			유체이탈방어사용 = Boolean.parseBoolean(serverSettings.getProperty("ucedef", "true"));

			AUTOMATIC_KICK = Integer.parseInt(serverSettings.getProperty("AutomaticKick", "10"));

			AUTO_CREATE_ACCOUNTS = Boolean.parseBoolean(serverSettings.getProperty("AutoCreateAccounts", "true"));

			MAX_ONLINE_USERS = Short.parseShort(serverSettings.getProperty("MaximumOnlineUsers", "30"));

			CACHE_MAP_FILES = Boolean.parseBoolean(serverSettings.getProperty("CacheMapFiles", "false"));

			LOAD_V2_MAP_FILES = Boolean.parseBoolean(serverSettings.getProperty("LoadV2MapFiles", "false"));

			CHECK_MOVE_INTERVAL = Boolean.parseBoolean(serverSettings.getProperty("CheckMoveInterval", "false"));

			CHECK_ATTACK_INTERVAL = Boolean.parseBoolean(serverSettings.getProperty("CheckAttackInterval", "false"));

			CHECK_SPELL_INTERVAL = Boolean.parseBoolean(serverSettings.getProperty("CheckSpellInterval", "false"));

			INJUSTICE_COUNT = Short.parseShort(serverSettings.getProperty("InjusticeCount", "10"));

			JUSTICE_COUNT = Integer.parseInt(serverSettings.getProperty("JusticeCount", "4"));

			CHECK_STRICTNESS = Integer.parseInt(serverSettings.getProperty("CheckStrictness", "102"));

			ACCOUNT_PASSWORD = Boolean.parseBoolean(serverSettings.getProperty("AccountPassword", "false"));// 패스워드
																											// 암호화
																											// 삭제
																											// 소스
																											// 0813
																											// 추가

			LOGGING_WEAPON_ENCHANT = Byte.parseByte(serverSettings.getProperty("LoggingWeaponEnchant", "0"));

			LOGGING_ARMOR_ENCHANT = Byte.parseByte(serverSettings.getProperty("LoggingArmorEnchant", "0"));

			LOGGING_TIME = Integer.parseInt(serverSettings.getProperty("LoggingTime", "120"));

			LOGGING_CHAT_NORMAL = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatNormal", "false"));

			LOGGING_CHAT_WHISPER = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatWhisper", "false"));

			LOGGING_CHAT_SHOUT = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatShout", "false"));

			LOGGING_CHAT_WORLD = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatWorld", "false"));

			LOGGING_CHAT_CLAN = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatClan", "false"));

			LOGGING_CHAT_PARTY = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatParty", "false"));

			LOGGING_CHAT_COMBINED = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatCombined", "false"));

			LOGGING_CHAT_CHAT_PARTY = Boolean.parseBoolean(serverSettings.getProperty("LoggingChatChatParty", "false"));

			AUTOSAVE_INTERVAL = Integer.parseInt(serverSettings.getProperty("AutosaveInterval", "1200"), 10);

			AUTOSAVE_INTERVAL_INVENTORY = Integer
					.parseInt(serverSettings.getProperty("AutosaveIntervalOfInventory", "300"), 10);

			SKILLTIMER_IMPLTYPE = Integer.parseInt(serverSettings.getProperty("SkillTimerImplType", "1"));

			NPCAI_IMPLTYPE = Integer.parseInt(serverSettings.getProperty("NpcAIImplType", "1"));

			TELNET_SERVER = Boolean.parseBoolean(serverSettings.getProperty("TelnetServer", "false"));

			TELNET_SERVER_PORT = Integer.parseInt(serverSettings.getProperty("TelnetServerPort", "23"));

			PC_RECOGNIZE_RANGE = Integer.parseInt(serverSettings.getProperty("PcRecognizeRange", "20"));

			CHARACTER_CONFIG_IN_SERVER_SIDE = Boolean
					.parseBoolean(serverSettings.getProperty("CharacterConfigInServerSide", "true"));

			ALLOW_2PC = Boolean.parseBoolean(serverSettings.getProperty("Allow2PC", "true"));

			LEVEL_DOWN_RANGE = Integer.parseInt(serverSettings.getProperty("LevelDownRange", "0"));

			SEND_PACKET_BEFORE_TELEPORT = Boolean
					.parseBoolean(serverSettings.getProperty("SendPacketBeforeTeleport", "false"));
			DETECT_DB_RESOURCE_LEAKS = Boolean
					.parseBoolean(serverSettings.getProperty("EnableDatabaseResourceLeaksDetection", "false"));
			// 123123123

			AUTO_CHECK = Boolean.parseBoolean(serverSettings.getProperty("AutoCheck", "false"));

			WAR_TIME_AUTO_SETTING = Boolean.parseBoolean(serverSettings.getProperty("WarTimeAutoSetting", "false"));

			GameServer.신규지원_경험치지급단 = Boolean.parseBoolean(serverSettings.getProperty("NewvieBonus", "false"));
			// DETECT_DB_RESOURCE_LEAKS =
			// Boolean.parseBoolean(serverSettings.getProperty("EnableDatabaseResourceLeaksDetection",
			// "false"));
			// DETECT_DB_RESOURCE_LEAKS =
			// Boolean.parseBoolean(serverSettings.getProperty("EnableDatabaseResourceLeaksDetection",
			// "false"));
			// =========== IP Check[#Config] ===========
			// AUTH_CONNECT =
			// Boolean.parseBoolean(serverSettings.getProperty("AuthConnect",
			// "true"));
			// =========== IP Check[#Config] ===========
			// =========== IP Check[#Config] ===========
			// CHECK_CONNECT =
			// Boolean.parseBoolean(serverSettings.getProperty("CheckConnect",
			// "true"));
			// =========== IP Check[#Config] ===========

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + SERVER_CONFIG_FILE + " File.");
		}

		// rates.properties
		try {
			Properties rateSettings = new Properties();
			InputStream is = new FileInputStream(new File(RATES_CONFIG_FILE));
			rateSettings.load(is);
			is.close();

			RATE_XP = Double.parseDouble(rateSettings.getProperty("RateXp", "1.0"));

			RATE_XP1 = Double.parseDouble(rateSettings.getProperty("RateXp1", "1.0"));

			RATE_LAWFUL = Double.parseDouble(rateSettings.getProperty("RateLawful", "1.0"));

			RATE_KARMA = Double.parseDouble(rateSettings.getProperty("RateKarma", "1.0"));

			RATE_DROP_ADENA = Double.parseDouble(rateSettings.getProperty("RateDropAdena", "1.0"));

			RATE_DROP_ITEMS = Double.parseDouble(rateSettings.getProperty("RateDropItems", "1.0"));

			RATE_ROBOT_TIME = Integer.parseInt(rateSettings.getProperty("RateRobotTime", "12")); // 무인PC(쿠우)

			ENCHANT_CHANCE_WEAPON = Integer.parseInt(rateSettings.getProperty("EnchantChanceWeapon", "1"));

			ENCHANT_CHANCE_ARMOR = Integer.parseInt(rateSettings.getProperty("EnchantChanceArmor", "1"));

			ENCHANT_CHANCE_ACCESSORY = Integer.parseInt(rateSettings.getProperty("EnchantChanceAccessory", "5"));

			RATE_WEIGHT_LIMIT = Double.parseDouble(rateSettings.getProperty("RateWeightLimit", "1"));

			RATE_WEIGHT_LIMIT_PET = Double.parseDouble(rateSettings.getProperty("RateWeightLimitforPet", "1"));

			RATE_SHOP_SELLING_PRICE = Double.parseDouble(rateSettings.getProperty("RateShopSellingPrice", "1.0"));

			RATE_SHOP_PURCHASING_PRICE = Double.parseDouble(rateSettings.getProperty("RateShopPurchasingPrice", "1.0"));

			RATE_DREAM = Integer.parseInt(rateSettings.getProperty("Ratedream", "1")); // 만월의정기이벤트

			CREATE_CHANCE_DIARY = Integer.parseInt(rateSettings.getProperty("CreateChanceDiary", "33"));

			CREATE_CHANCE_RECOLLECTION = Integer.parseInt(rateSettings.getProperty("CreateChanceRecollection", "90"));

			CREATE_CHANCE_MYSTERIOUS = Integer.parseInt(rateSettings.getProperty("CreateChanceMysterious", "90"));

			CREATE_CHANCE_PROCESSING = Integer.parseInt(rateSettings.getProperty("CreateChanceProcessing", "90"));

			CREATE_CHANCE_PROCESSING_DIAMOND = Integer
					.parseInt(rateSettings.getProperty("CreateChanceProcessingDiamond", "90"));

			CREATE_CHANCE_DANTES = Integer.parseInt(rateSettings.getProperty("CreateChanceDantes", "90"));

			CREATE_CHANCE_ANCIENT_AMULET = Integer
					.parseInt(rateSettings.getProperty("CreateChanceAncientAmulet", "90"));

			CREATE_CHANCE_HISTORY_BOOK = Integer.parseInt(rateSettings.getProperty("CreateChanceHistoryBook", "50"));

			MAX_WEAPON = Integer.parseInt(rateSettings.getProperty("MaxWeapon", "12"));

			MAX_ARMOR = Integer.parseInt(rateSettings.getProperty("MaxArmor", "10"));

			MAX_WEAPON1 = Integer.parseInt(rateSettings.getProperty("MaxWeapon1", "12"));// 마족

			MAX_LEVEL = Integer.parseInt(rateSettings.getProperty("Maxlevel", "12"));// 신규보호렙제
			배틀존입장레벨 = Integer.parseInt(rateSettings.getProperty("BattleLevel", "55"));
			배틀존작동유무 = Boolean.parseBoolean(rateSettings.getProperty("BattleZone", "true"));
			배틀존아이템 = rateSettings.getProperty("BattleItem", "");
			배틀존아이템갯수 = rateSettings.getProperty("BattleCount", "");

			FEATHER_TIME = Integer.parseInt(rateSettings.getProperty("FeatherTime", "10"));

			FEATHER_NUMBER = Integer.parseInt(rateSettings.getProperty("FeatherNumber", "10"));

			CLAN_NUMBER = Integer.parseInt(rateSettings.getProperty("ClanNumber", "10"));

			CASTLE_NUMBER = Integer.parseInt(rateSettings.getProperty("CastleNumber", "50"));

			스냅퍼최대인챈 = Integer.parseInt(rateSettings.getProperty("SnapperMaxEnchant", "5"));
			룸티스최대인챈 = Integer.parseInt(rateSettings.getProperty("RoomteeceMaxEnchant", "5"));
			장신구최대인챈 = Integer.parseInt(rateSettings.getProperty("acaccessoryMaxEnchant", "5"));
			아놀드이벤트시간 = Integer.parseInt(rateSettings.getProperty("AnoldeTime", "2"));
			생명의나뭇잎 = Integer.parseInt(rateSettings.getProperty("Leafitem", "100"));
			영생의빛 = Integer.parseInt(rateSettings.getProperty("Eternalitem", "100"));
			// RATE_CLAN_XP =
			// Double.parseDouble(rateSettings.getProperty("RateClanXp",
			// "1.0"));

			// RATE_CASTLE_XP =
			// Double.parseDouble(rateSettings.getProperty("RateCastleXp",
			// "1.0"));

			RATE_7_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_7_Dmg_Rate", "1.5"));
			RATE_8_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_8_Dmg_Rate", "1.5"));
			RATE_9_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_9_Dmg_Rate", "2.0"));
			RATE_10_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_10_Dmg_Rate", "2.0"));
			RATE_11_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_11_Dmg_Rate", "2.0"));
			RATE_12_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_12_Dmg_Rate", "2.0"));
			RATE_13_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_13_Dmg_Rate", "2.0"));
			RATE_14_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_14_Dmg_Rate", "2.0"));
			RATE_15_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_15_Dmg_Rate", "2.0"));
			RATE_16_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_16_Dmg_Rate", "2.5"));
			RATE_17_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_17_Dmg_Rate", "2.5"));
			RATE_18_DMG_RATE = Double.parseDouble(rateSettings.getProperty("Rate_18_Dmg_Rate", "2.5"));

			RATE_7_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_7_Dmg_Per", "5"));
			RATE_8_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_8_Dmg_Per", "10"));
			RATE_9_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_9_Dmg_Per", "20"));
			RATE_10_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_10_Dmg_Per", "30"));
			RATE_11_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_11_Dmg_Per", "40"));
			RATE_12_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_12_Dmg_Per", "50"));
			RATE_13_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_13_Dmg_Per", "60"));
			RATE_14_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_14_Dmg_Per", "70"));
			RATE_15_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_15_Dmg_Per", "80"));
			RATE_16_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_16_Dmg_Per", "90"));
			RATE_17_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_17_Dmg_Per", "90"));
			RATE_18_DMG_PER = Integer.parseInt(rateSettings.getProperty("Rate_18_Dmg_Per", "100"));

			뮨데미지 = Double.parseDouble(rateSettings.getProperty("Immune_to", "0.5"));

			AC_170 = Integer.parseInt(rateSettings.getProperty("AC_170", "0"));
			AC_160 = Integer.parseInt(rateSettings.getProperty("AC_160", "0"));
			AC_150 = Integer.parseInt(rateSettings.getProperty("AC_150", "0"));
			AC_140 = Integer.parseInt(rateSettings.getProperty("AC_140", "0"));
			AC_130 = Integer.parseInt(rateSettings.getProperty("AC_130", "0"));
			AC_120 = Integer.parseInt(rateSettings.getProperty("AC_120", "0"));
			AC_110 = Integer.parseInt(rateSettings.getProperty("AC_110", "0"));
			AC_100 = Integer.parseInt(rateSettings.getProperty("AC_100", "0"));
			AC_90 = Integer.parseInt(rateSettings.getProperty("AC_90", "0"));
			AC_80 = Integer.parseInt(rateSettings.getProperty("AC_80", "0"));
			AC_70 = Integer.parseInt(rateSettings.getProperty("AC_70", "0"));
			AC_60 = Integer.parseInt(rateSettings.getProperty("AC_60", "0"));
			AC_50 = Integer.parseInt(rateSettings.getProperty("AC_50", "0"));
			AC_40 = Integer.parseInt(rateSettings.getProperty("AC_40", "0"));
			AC_30 = Integer.parseInt(rateSettings.getProperty("AC_30", "0"));
			AC_20 = Integer.parseInt(rateSettings.getProperty("AC_20", "0"));
			AC_10 = Integer.parseInt(rateSettings.getProperty("AC_10", "0"));

			레이드시간 = Integer.parseInt(rateSettings.getProperty("Raidtime", "1"));

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + RATES_CONFIG_FILE + " File.");
		}

		// altsettings.properties
		try {
			Properties altSettings = new Properties();
			InputStream is = new FileInputStream(new File(ALT_SETTINGS_FILE));
			altSettings.load(is);
			is.close();

			GLOBAL_CHAT_LEVEL = Short.parseShort(altSettings.getProperty("GlobalChatLevel", "30"));

			WHISPER_CHAT_LEVEL = Short.parseShort(altSettings.getProperty("WhisperChatLevel", "7"));

			AUTO_LOOT = Byte.parseByte(altSettings.getProperty("AutoLoot", "2"));

			LOOTING_RANGE = Integer.parseInt(altSettings.getProperty("LootingRange", "3"));

			ALT_NONPVP = Boolean.parseBoolean(altSettings.getProperty("NonPvP", "true"));

			ALT_ATKMSG = Boolean.parseBoolean(altSettings.getProperty("AttackMessageOn", "true"));

			CHANGE_TITLE_BY_ONESELF = Boolean.parseBoolean(altSettings.getProperty("ChangeTitleByOneself", "false"));

			MAX_CLAN_MEMBER = Integer.parseInt(altSettings.getProperty("MaxClanMember", "0"));

			CLAN_ALLIANCE = Boolean.parseBoolean(altSettings.getProperty("ClanAlliance", "true"));

			MAX_PT = Integer.parseInt(altSettings.getProperty("MaxPT", "8"));

			EXP_GIVE = Integer.parseInt(altSettings.getProperty("ExpGive", "70"));

			MAX_CHAT_PT = Integer.parseInt(altSettings.getProperty("MaxChatPT", "8"));

			SIM_WAR_PENALTY = Boolean.parseBoolean(altSettings.getProperty("SimWarPenalty", "true"));

			GET_BACK = Boolean.parseBoolean(altSettings.getProperty("GetBack", "false"));

			ALT_ITEM_DELETION_TYPE = altSettings.getProperty("ItemDeletionType", "auto");

			ALT_ITEM_DELETION_TIME = Integer.parseInt(altSettings.getProperty("ItemDeletionTime", "10"));

			ALT_ITEM_DELETION_RANGE = Integer.parseInt(altSettings.getProperty("ItemDeletionRange", "5"));

			ALT_PRIVATE_WAREHOUSE_LEVEL = Integer.parseInt(altSettings.getProperty("PrivateWarehouseLevel", "5"));

			ALT_HALLOWEENEVENT = Boolean.parseBoolean(altSettings.getProperty("HalloweenEvent", "true"));

			ALT_HALLOWEENEVENT2009 = Boolean.parseBoolean(altSettings.getProperty("HalloweenEvent2009", "true"));

			ALT_FANTASYEVENT = Boolean.parseBoolean(altSettings.getProperty("FantasyEvent", "true"));

			ALT_CHUSEOKEVENT = Boolean.parseBoolean(altSettings.getProperty("ChuSeokEvent", "true"));

			ALT_FEATURE = Boolean.parseBoolean(altSettings.getProperty("FeatureEvent", "true"));

			ALT_WHO_COMMAND = Boolean.parseBoolean(altSettings.getProperty("WhoCommand", "false"));

			ALT_REVIVAL_POTION = Boolean.parseBoolean(altSettings.getProperty("RevivalPotion", "false"));
			String strWar;
			strWar = altSettings.getProperty("WarTime", "1h");
			if (strWar.indexOf("d") >= 0) {
				ALT_WAR_TIME_UNIT = Calendar.DATE;
				strWar = strWar.replace("d", "");
			} else if (strWar.indexOf("h") >= 0) {
				ALT_WAR_TIME_UNIT = Calendar.HOUR_OF_DAY;
				strWar = strWar.replace("h", "");
			} else if (strWar.indexOf("m") >= 0) {
				ALT_WAR_TIME_UNIT = Calendar.MINUTE;
				strWar = strWar.replace("m", "");
			}
			ALT_WAR_TIME = Integer.parseInt(strWar);
			strWar = altSettings.getProperty("WarInterval", "2d");
			if (strWar.indexOf("d") >= 0) {
				ALT_WAR_INTERVAL_UNIT = Calendar.DATE;
				strWar = strWar.replace("d", "");
			} else if (strWar.indexOf("h") >= 0) {
				ALT_WAR_INTERVAL_UNIT = Calendar.HOUR_OF_DAY;
				strWar = strWar.replace("h", "");
			} else if (strWar.indexOf("m") >= 0) {
				ALT_WAR_INTERVAL_UNIT = Calendar.MINUTE;
				strWar = strWar.replace("m", "");
			}
			ALT_WAR_INTERVAL = Integer.parseInt(strWar);

			SPAWN_HOME_POINT = Boolean.parseBoolean(altSettings.getProperty("SpawnHomePoint", "true"));

			SPAWN_HOME_POINT_COUNT = Integer.parseInt(altSettings.getProperty("SpawnHomePointCount", "2"));

			SPAWN_HOME_POINT_DELAY = Integer.parseInt(altSettings.getProperty("SpawnHomePointDelay", "100"));

			SPAWN_HOME_POINT_RANGE = Integer.parseInt(altSettings.getProperty("SpawnHomePointRange", "8"));

			INIT_BOSS_SPAWN = Boolean.parseBoolean(altSettings.getProperty("InitBossSpawn", "true"));

			ELEMENTAL_STONE_AMOUNT = Integer.parseInt(altSettings.getProperty("ElementalStoneAmount", "300"));

			HOUSE_TAX_INTERVAL = Integer.parseInt(altSettings.getProperty("HouseTaxInterval", "10"));

			MAX_DOLL_COUNT = Integer.parseInt(altSettings.getProperty("MaxDollCount", "1"));

			RETURN_TO_NATURE = Boolean.parseBoolean(altSettings.getProperty("ReturnToNature", "false"));

			MAX_NPC_ITEM = Integer.parseInt(altSettings.getProperty("MaxNpcItem", "8"));

			MAX_PERSONAL_WAREHOUSE_ITEM = Integer.parseInt(altSettings.getProperty("MaxPersonalWarehouseItem", "100"));

			MAX_CLAN_WAREHOUSE_ITEM = Integer.parseInt(altSettings.getProperty("MaxClanWarehouseItem", "200"));

			DELETE_CHARACTER_AFTER_7DAYS = Boolean
					.parseBoolean(altSettings.getProperty("DeleteCharacterAfter7Days", "True"));

			GMCODE = Integer.parseInt(altSettings.getProperty("GMCODE", "9999"));

			DELETE_DB_DAYS = Integer.parseInt(altSettings.getProperty("DeleteDBDAY", "14"));

			MAX_버모스_DUNGEON_LEVEL = Byte.parseByte(altSettings.getProperty("MaxEarthDragonDungeon", "66"));

			MAX_후오스_DUNGEON_LEVEL = Byte.parseByte(altSettings.getProperty("MaxWaterDragonDungeon", "66"));

			MIN_상아탑_DUNGEON_LEVEL = Byte.parseByte(altSettings.getProperty("MinIvoryTowerDungeon", "67"));

			Event_Box = Boolean.parseBoolean(altSettings.getProperty("Event_box_ok", "false"));

			ALT_PRIVATE_SHOP_LEVEL = Integer.parseInt(altSettings.getProperty("PrivateShopLevel", "0"));

			이레매직확률 = Integer.parseInt(altSettings.getProperty("EraseMagic", "30"));

			데페확률 = Integer.parseInt(altSettings.getProperty("Desperado", "60"));

			그립확률 = Integer.parseInt(altSettings.getProperty("PowerRip", "50"));

			스턴확률 = Integer.parseInt(altSettings.getProperty("Shock_Stun", "50"));

			어바확률 = Integer.parseInt(altSettings.getProperty("Earth_Bind", "50"));

			검스턴확률 = Integer.parseInt(altSettings.getProperty("weaponShock_Stun", "5"));

			수련자무기밸런스수치 = Integer.parseInt(altSettings.getProperty("aWeaponBalance", "0"));
			수련자갑옷밸런스수치 = Integer.parseInt(altSettings.getProperty("aArmorBalance", "0"));

			Tam_Ok = Boolean.parseBoolean(altSettings.getProperty("TamOK", "false"));
			Tam_Time = Integer.parseInt(altSettings.getProperty("TamTime", "15"));
			Tam_Count = Integer.parseInt(altSettings.getProperty("TamCount", "600"));

			무혈상점 = Integer.parseInt(altSettings.getProperty("ShopLevel", "5"));
			드랍레벨 = Integer.parseInt(altSettings.getProperty("DropLevel", "5"));

			PROTECT_CLAN_ID = Integer.parseInt(altSettings.getProperty("ProtectClanId", "200000009"));
			AUTO_REMOVECLAN = Integer.parseInt(altSettings.getProperty("AutoRemoveClan", "95"));
		
			신규혈맹보호처리 = Boolean.parseBoolean(altSettings.getProperty("NewClanPvP", "true"));
			
			D_Reset_Time = Integer.parseInt(altSettings.getProperty("DungeonResetTime", "9"));

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + ALT_SETTINGS_FILE + " File.");
		}

		// charsettings.properties
		try {
			Properties charSettings = new Properties();
			InputStream is = new FileInputStream(new File(CHAR_SETTINGS_CONFIG_FILE));
			charSettings.load(is);
			is.close();

			PRINCE_MAX_HP = Integer.parseInt(charSettings.getProperty("PrinceMaxHP", "1000"));

			PRINCE_MAX_MP = Integer.parseInt(charSettings.getProperty("PrinceMaxMP", "800"));

			KNIGHT_MAX_HP = Integer.parseInt(charSettings.getProperty("KnightMaxHP", "1400"));

			KNIGHT_MAX_MP = Integer.parseInt(charSettings.getProperty("KnightMaxMP", "600"));

			ELF_MAX_HP = Integer.parseInt(charSettings.getProperty("ElfMaxHP", "1000"));

			ELF_MAX_MP = Integer.parseInt(charSettings.getProperty("ElfMaxMP", "900"));

			WIZARD_MAX_HP = Integer.parseInt(charSettings.getProperty("WizardMaxHP", "800"));

			WIZARD_MAX_MP = Integer.parseInt(charSettings.getProperty("WizardMaxMP", "1200"));

			DARKELF_MAX_HP = Integer.parseInt(charSettings.getProperty("DarkelfMaxHP", "1000"));

			DARKELF_MAX_MP = Integer.parseInt(charSettings.getProperty("DarkelfMaxMP", "900"));

			DRAGONKNIGHT_MAX_HP = Integer.parseInt(charSettings.getProperty("DragonknightMaxHP", "1000"));

			DRAGONKNIGHT_MAX_MP = Integer.parseInt(charSettings.getProperty("DragonknightMaxMP", "900"));

			BLACKWIZARD_MAX_HP = Integer.parseInt(charSettings.getProperty("BlackwizardMaxHP", "900"));

			BLACKWIZARD_MAX_MP = Integer.parseInt(charSettings.getProperty("BlackwizardMaxMP", "1100"));

			MAXLEVEL = Integer.parseInt(charSettings.getProperty("LimitLevel", "99"));

			LV50_EXP = Integer.parseInt(charSettings.getProperty("Lv50Exp", "1"));
			LV51_EXP = Integer.parseInt(charSettings.getProperty("Lv51Exp", "1"));
			LV52_EXP = Integer.parseInt(charSettings.getProperty("Lv52Exp", "1"));
			LV53_EXP = Integer.parseInt(charSettings.getProperty("Lv53Exp", "1"));
			LV54_EXP = Integer.parseInt(charSettings.getProperty("Lv54Exp", "1"));
			LV55_EXP = Integer.parseInt(charSettings.getProperty("Lv55Exp", "1"));
			LV56_EXP = Integer.parseInt(charSettings.getProperty("Lv56Exp", "1"));
			LV57_EXP = Integer.parseInt(charSettings.getProperty("Lv57Exp", "1"));
			LV58_EXP = Integer.parseInt(charSettings.getProperty("Lv58Exp", "1"));
			LV59_EXP = Integer.parseInt(charSettings.getProperty("Lv59Exp", "1"));
			LV60_EXP = Integer.parseInt(charSettings.getProperty("Lv60Exp", "1"));
			LV61_EXP = Integer.parseInt(charSettings.getProperty("Lv61Exp", "1"));
			LV62_EXP = Integer.parseInt(charSettings.getProperty("Lv62Exp", "1"));
			LV63_EXP = Integer.parseInt(charSettings.getProperty("Lv63Exp", "1"));
			LV64_EXP = Integer.parseInt(charSettings.getProperty("Lv64Exp", "1"));
			LV65_EXP = Integer.parseInt(charSettings.getProperty("Lv65Exp", "2"));
			LV66_EXP = Integer.parseInt(charSettings.getProperty("Lv66Exp", "2"));
			LV67_EXP = Integer.parseInt(charSettings.getProperty("Lv67Exp", "2"));
			LV68_EXP = Integer.parseInt(charSettings.getProperty("Lv68Exp", "2"));
			LV69_EXP = Integer.parseInt(charSettings.getProperty("Lv69Exp", "2"));
			LV70_EXP = Integer.parseInt(charSettings.getProperty("Lv70Exp", "4"));
			LV71_EXP = Integer.parseInt(charSettings.getProperty("Lv71Exp", "4"));
			LV72_EXP = Integer.parseInt(charSettings.getProperty("Lv72Exp", "4"));
			LV73_EXP = Integer.parseInt(charSettings.getProperty("Lv73Exp", "4"));
			LV74_EXP = Integer.parseInt(charSettings.getProperty("Lv74Exp", "4"));
			LV75_EXP = Integer.parseInt(charSettings.getProperty("Lv75Exp", "8"));
			LV76_EXP = Integer.parseInt(charSettings.getProperty("Lv76Exp", "8"));
			LV77_EXP = Integer.parseInt(charSettings.getProperty("Lv77Exp", "8"));
			LV78_EXP = Integer.parseInt(charSettings.getProperty("Lv78Exp", "8"));
			LV79_EXP = Integer.parseInt(charSettings.getProperty("Lv79Exp", "16"));
			LV80_EXP = Integer.parseInt(charSettings.getProperty("Lv80Exp", "32"));
			LV81_EXP = Integer.parseInt(charSettings.getProperty("Lv81Exp", "64"));
			LV82_EXP = Integer.parseInt(charSettings.getProperty("Lv82Exp", "128"));
			LV83_EXP = Integer.parseInt(charSettings.getProperty("Lv83Exp", "256"));
			LV84_EXP = Integer.parseInt(charSettings.getProperty("Lv84Exp", "512"));
			LV85_EXP = Integer.parseInt(charSettings.getProperty("Lv85Exp", "1024"));
			LV86_EXP = Integer.parseInt(charSettings.getProperty("Lv86Exp", "2048"));
			LV87_EXP = Integer.parseInt(charSettings.getProperty("Lv87Exp", "4096"));
			LV88_EXP = Integer.parseInt(charSettings.getProperty("Lv88Exp", "8192"));
			LV89_EXP = Integer.parseInt(charSettings.getProperty("Lv89Exp", "16384"));
			LV90_EXP = Integer.parseInt(charSettings.getProperty("Lv90Exp", "32768"));
			LV91_EXP = Integer.parseInt(charSettings.getProperty("Lv91Exp", "65536"));
			LV92_EXP = Integer.parseInt(charSettings.getProperty("Lv92Exp", "131072"));
			LV93_EXP = Integer.parseInt(charSettings.getProperty("Lv93Exp", "262144"));
			LV94_EXP = Integer.parseInt(charSettings.getProperty("Lv94Exp", "524288"));
			LV95_EXP = Integer.parseInt(charSettings.getProperty("Lv95Exp", "1048576"));
			LV96_EXP = Integer.parseInt(charSettings.getProperty("Lv96Exp", "2097152"));
			LV97_EXP = Integer.parseInt(charSettings.getProperty("Lv97Exp", "4194304"));
			LV98_EXP = Integer.parseInt(charSettings.getProperty("Lv98Exp", "8388608"));
			LV99_EXP = Integer.parseInt(charSettings.getProperty("Lv99Exp", "16777216"));

			ExpTable._expPenalty[0] = LV50_EXP;
			ExpTable._expPenalty[1] = LV51_EXP;
			ExpTable._expPenalty[2] = LV52_EXP;
			ExpTable._expPenalty[3] = LV53_EXP;
			ExpTable._expPenalty[4] = LV54_EXP;
			ExpTable._expPenalty[5] = LV55_EXP;
			ExpTable._expPenalty[6] = LV56_EXP;
			ExpTable._expPenalty[7] = LV57_EXP;
			ExpTable._expPenalty[8] = LV58_EXP;
			ExpTable._expPenalty[9] = LV59_EXP;
			ExpTable._expPenalty[10] = LV60_EXP;
			ExpTable._expPenalty[11] = LV61_EXP;
			ExpTable._expPenalty[12] = LV62_EXP;
			ExpTable._expPenalty[13] = LV63_EXP;
			ExpTable._expPenalty[14] = LV64_EXP;
			ExpTable._expPenalty[15] = LV65_EXP;
			ExpTable._expPenalty[16] = LV66_EXP;
			ExpTable._expPenalty[17] = LV67_EXP;
			ExpTable._expPenalty[18] = LV68_EXP;
			ExpTable._expPenalty[19] = LV69_EXP;
			ExpTable._expPenalty[20] = LV70_EXP;
			ExpTable._expPenalty[21] = LV71_EXP;
			ExpTable._expPenalty[22] = LV72_EXP;
			ExpTable._expPenalty[23] = LV73_EXP;
			ExpTable._expPenalty[24] = LV74_EXP;
			ExpTable._expPenalty[25] = LV75_EXP;
			ExpTable._expPenalty[26] = LV76_EXP;
			ExpTable._expPenalty[27] = LV77_EXP;
			ExpTable._expPenalty[28] = LV78_EXP;
			ExpTable._expPenalty[29] = LV79_EXP;
			ExpTable._expPenalty[30] = LV80_EXP;
			ExpTable._expPenalty[31] = LV81_EXP;
			ExpTable._expPenalty[32] = LV82_EXP;
			ExpTable._expPenalty[33] = LV83_EXP;
			ExpTable._expPenalty[34] = LV84_EXP;
			ExpTable._expPenalty[35] = LV85_EXP;
			ExpTable._expPenalty[36] = LV86_EXP;
			ExpTable._expPenalty[37] = LV87_EXP;
			ExpTable._expPenalty[38] = LV88_EXP;
			ExpTable._expPenalty[39] = LV89_EXP;
			ExpTable._expPenalty[40] = LV90_EXP;
			ExpTable._expPenalty[41] = LV91_EXP;
			ExpTable._expPenalty[42] = LV92_EXP;
			ExpTable._expPenalty[43] = LV93_EXP;
			ExpTable._expPenalty[44] = LV94_EXP;
			ExpTable._expPenalty[45] = LV95_EXP;
			ExpTable._expPenalty[46] = LV96_EXP;
			ExpTable._expPenalty[47] = LV97_EXP;
			ExpTable._expPenalty[48] = LV98_EXP;
			ExpTable._expPenalty[49] = LV99_EXP;
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			throw new Error("Failed to Load " + CHAR_SETTINGS_CONFIG_FILE + " File.");
		}
		validate();
	}

	public static String toKor(String src) {
		String str = null;
		try {
			byte[] b = src.getBytes("8859_1");
			str = new String(b, "EUC-KR");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	private static void validate() {
		if (!IntRange.includes(Config.ALT_ITEM_DELETION_RANGE, 0, 5)) {
			throw new IllegalStateException("ItemDeletionRange의 값이 설정 가능 범위외입니다. ");
		}

		if (!IntRange.includes(Config.ALT_ITEM_DELETION_TIME, 1, 35791)) {
			throw new IllegalStateException("ItemDeletionTime의 값이 설정 가능 범위외입니다. ");
		}
	}

	public static boolean setParameterValue(String pName, String pValue) {
		// server.properties
		if (pName.equalsIgnoreCase("ServerType")) {
			GAME_SERVER_TYPE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("GameserverHostname")) {
			GAME_SERVER_HOST_NAME = pValue;
		} else if (pName.equalsIgnoreCase("GameserverPort")) {
			GAME_SERVER_PORT = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Driver")) {
			DB_DRIVER = pValue;
		} else if (pName.equalsIgnoreCase("URL")) {
			DB_URL = pValue;
		} else if (pName.equalsIgnoreCase("Login")) {
			DB_LOGIN = pValue;
		} else if (pName.equalsIgnoreCase("Password")) {
			DB_PASSWORD = pValue;
		} else if (pName.equalsIgnoreCase("ClientLanguage")) {
			CLIENT_LANGUAGE = Integer.parseInt(pValue);

		} else if (pName.equalsIgnoreCase("TimeZone")) {
			TIME_ZONE = pValue;
		} else if (pName.equalsIgnoreCase("AutomaticKick")) {
			AUTOMATIC_KICK = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AutoCreateAccounts")) {
			AUTO_CREATE_ACCOUNTS = Boolean.parseBoolean(pValue);
		} else if (pName.equalsIgnoreCase("MaximumOnlineUsers")) {
			MAX_ONLINE_USERS = Short.parseShort(pValue);
		} else if (pName.equalsIgnoreCase("LoggingWeaponEnchant")) {
			LOGGING_WEAPON_ENCHANT = Byte.parseByte(pValue);
		} else if (pName.equalsIgnoreCase("LoggingArmorEnchant")) {
			LOGGING_ARMOR_ENCHANT = Byte.parseByte(pValue);
		} else if (pName.equalsIgnoreCase("LoggingTime")) {
			LOGGING_TIME = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("CharacterConfigInServerSide")) {
			CHARACTER_CONFIG_IN_SERVER_SIDE = Boolean.parseBoolean(pValue);
		} else if (pName.equalsIgnoreCase("Charpass")) {
			캐릭터비번사용여부 = Boolean.parseBoolean(pValue);
		} else if (pName.equalsIgnoreCase("Allow2PC")) {
			ALLOW_2PC = Boolean.parseBoolean(pValue);
		} else if (pName.equalsIgnoreCase("LevelDownRange")) {
			LEVEL_DOWN_RANGE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AccountPassword")) {
			ACCOUNT_PASSWORD = Boolean.parseBoolean(pValue);// 패스워드 암호화 삭제 소스
															// 0813 추가
		} else if (pName.equalsIgnoreCase("SendPacketBeforeTeleport")) {
			SEND_PACKET_BEFORE_TELEPORT = Boolean.parseBoolean(pValue);
		}
		// rates.properties
		else if (pName.equalsIgnoreCase("RateXp")) {
			RATE_XP = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("RateXp1")) {
			RATE_XP1 = Double.parseDouble(pValue);
		} else

		if (pName.equalsIgnoreCase("RateLawful")) {
			RATE_LAWFUL = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("RateKarma")) {
			RATE_KARMA = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("RateDropAdena")) {
			RATE_DROP_ADENA = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("RateDropItems")) {
			RATE_DROP_ITEMS = Double.parseDouble(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceWeapon")) {
			ENCHANT_CHANCE_WEAPON = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("FeatherTime")) {
			FEATHER_TIME = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("FeatherNumber")) {
			FEATHER_NUMBER = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("BattleZone")) {
			배틀존작동유무 = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("BattleLevel")) {
			배틀존입장레벨 = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("ClanNumber")) {
			CLAN_NUMBER = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("CastleNumber")) {
			CASTLE_NUMBER = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceArmor")) {
			ENCHANT_CHANCE_ARMOR = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("EnchantChanceAccessory")) {
			ENCHANT_CHANCE_ACCESSORY = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Weightrate")) {
			RATE_WEIGHT_LIMIT = Byte.parseByte(pValue);
		}

		// altsettings.properties
		else if (pName.equalsIgnoreCase("GlobalChatLevel")) {
			GLOBAL_CHAT_LEVEL = Short.parseShort(pValue);
		} else if (pName.equalsIgnoreCase("WhisperChatLevel")) {
			WHISPER_CHAT_LEVEL = Short.parseShort(pValue);
		} else if (pName.equalsIgnoreCase("AutoLoot")) {
			AUTO_LOOT = Byte.parseByte(pValue);
		} else if (pName.equalsIgnoreCase("LOOTING_RANGE")) {
			LOOTING_RANGE = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AltNonPvP")) {
			ALT_NONPVP = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("AttackMessageOn")) {
			ALT_ATKMSG = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("ChangeTitleByOneself")) {
			CHANGE_TITLE_BY_ONESELF = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxClanMember")) {
			MAX_CLAN_MEMBER = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("ClanAlliance")) {
			CLAN_ALLIANCE = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxPT")) {
			MAX_PT = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("MaxChatPT")) {
			MAX_CHAT_PT = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("SimWarPenalty")) {
			SIM_WAR_PENALTY = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("GetBack")) {
			GET_BACK = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("AutomaticItemDeletionTime")) {
			ALT_ITEM_DELETION_TIME = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AutomaticItemDeletionRange")) {
			ALT_ITEM_DELETION_RANGE = Byte.parseByte(pValue);
		} else if (pName.equalsIgnoreCase("HalloweenEvent")) {
			ALT_HALLOWEENEVENT = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("HalloweenEvent2009")) {
			ALT_HALLOWEENEVENT2009 = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("FantasyEvent")) {
			ALT_FANTASYEVENT = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("ChuSeokEvent")) {
			ALT_CHUSEOKEVENT = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("HouseTaxInterval")) {
			HOUSE_TAX_INTERVAL = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxDollCount")) {
			MAX_DOLL_COUNT = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("ReturnToNature")) {
			RETURN_TO_NATURE = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxNpcItem")) {
			MAX_NPC_ITEM = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxPersonalWarehouseItem")) {
			MAX_PERSONAL_WAREHOUSE_ITEM = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("MaxClanWarehouseItem")) {
			MAX_CLAN_WAREHOUSE_ITEM = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("DeleteCharacterAfter7Days")) {
			DELETE_CHARACTER_AFTER_7DAYS = Boolean.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("GMCODE")) {
			GMCODE = Integer.valueOf(pValue);
		} else if (pName.equalsIgnoreCase("DeleteDBDAY")) {
			DELETE_DB_DAYS = Integer.valueOf(pValue);
		}

		else if (pName.equalsIgnoreCase("ExpGive")) {
			EXP_GIVE = Integer.parseInt(pValue);
		}
		// charsettings.properties
		else if (pName.equalsIgnoreCase("PrinceMaxHP")) {
			PRINCE_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("LimitLevel")) {
			MAXLEVEL = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("PrinceMaxMP")) {
			PRINCE_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("KnightMaxHP")) {
			KNIGHT_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("KnightMaxMP")) {
			KNIGHT_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("ElfMaxHP")) {
			ELF_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("ElfMaxMP")) {
			ELF_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("WizardMaxHP")) {
			WIZARD_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("WizardMaxMP")) {
			WIZARD_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("DarkelfMaxHP")) {
			DARKELF_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("DarkelfMaxMP")) {
			DARKELF_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("DragonknightMaxHP")) {
			DRAGONKNIGHT_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("DragonknightMaxMP")) {
			DRAGONKNIGHT_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("BlackwizardMaxHP")) {
			BLACKWIZARD_MAX_HP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("BlackwizardMaxMP")) {
			BLACKWIZARD_MAX_MP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv50Exp")) {
			LV50_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv51Exp")) {
			LV51_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv52Exp")) {
			LV52_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv53Exp")) {
			LV53_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv54Exp")) {
			LV54_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv55Exp")) {
			LV55_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv56Exp")) {
			LV56_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv57Exp")) {
			LV57_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv58Exp")) {
			LV58_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv59Exp")) {
			LV59_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv60Exp")) {
			LV60_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv61Exp")) {
			LV61_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv62Exp")) {
			LV62_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv63Exp")) {
			LV63_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv64Exp")) {
			LV64_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv65Exp")) {
			LV65_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv66Exp")) {
			LV66_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv67Exp")) {
			LV67_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv68Exp")) {
			LV68_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv69Exp")) {
			LV69_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv70Exp")) {
			LV70_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv71Exp")) {
			LV71_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv72Exp")) {
			LV72_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv73Exp")) {
			LV73_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv74Exp")) {
			LV74_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv75Exp")) {
			LV75_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv76Exp")) {
			LV76_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv77Exp")) {
			LV77_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv78Exp")) {
			LV78_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv79Exp")) {
			LV79_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv80Exp")) {
			LV80_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv81Exp")) {
			LV81_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv82Exp")) {
			LV82_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv83Exp")) {
			LV83_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv84Exp")) {
			LV84_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv85Exp")) {
			LV85_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv86Exp")) {
			LV86_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv87Exp")) {
			LV87_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv88Exp")) {
			LV88_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv89Exp")) {
			LV89_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv90Exp")) {
			LV90_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv91Exp")) {
			LV91_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv92Exp")) {
			LV92_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv93Exp")) {
			LV93_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv94Exp")) {
			LV94_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv95Exp")) {
			LV95_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv96Exp")) {
			LV96_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv97Exp")) {
			LV97_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv98Exp")) {
			LV98_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Lv99Exp")) {
			LV99_EXP = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("Raidtime")) {
			레이드시간 = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("SnapperMaxEnchant")) {
			스냅퍼최대인챈 = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("RoomteeceMaxEnchant")) {
			룸티스최대인챈 = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("acaccessoryMaxEnchant")) {
			장신구최대인챈 = Integer.parseInt(pValue);
		} else if (pName.equalsIgnoreCase("AnoldeTime")) {
			아놀드이벤트시간 = Integer.parseInt(pValue);
		} else {
			return false;
		}
		return true;
	}

	private Config() {
	}

	public final static int etc_arrow = 0;
	public final static int etc_wand = 1;
	public final static int etc_light = 2;
	public final static int etc_gem = 3;
	public final static int etc_potion = 6;
	public final static int etc_firecracker = 5;
	public final static int etc_food = 7;
	public final static int etc_scroll = 8;
	public final static int etc_questitem = 9;
	public final static int etc_spellbook = 10;
	public final static int etc_other = 12;
	public final static int etc_material = 13;
	public final static int etc_sting = 15;
	public final static int etc_treasurebox = 16;

	public static enum LOG {
		chat, error, system, badplayer, enchant, inventory, time
	}

	public static synchronized String YearMonthDate2() {
		try {

			Date day = new Date(System.currentTimeMillis());
			int 년 = day.getYear() - 100;
			String 년2;
			if (년 < 10) {
				년2 = "0" + 년;
			} else {
				년2 = Integer.toString(년);
			}
			int 월 = (day.getMonth() + 1);
			String 월2 = null;
			if (월 < 10) {
				월2 = "0" + 월;
			} else {
				월2 = Integer.toString(월);
			}
			int 일 = day.getDate();
			String 일2 = null;
			if (일 < 10) {
				일2 = "0" + 일;
			} else {
				일2 = Integer.toString(일);
			}
			return 년2 + 월2 + 일2;
		} catch (Exception e) {
		}

		return "000000";
	}

	public static synchronized int Year() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.YEAR) - 2000;
	}

	public static synchronized int Month() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.MONTH) + 1;
	}

	public static synchronized int Date() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.DATE);
	}

	public static synchronized int Hour() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static synchronized int Minute() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		return cal.get(Calendar.MINUTE);
	}

	public static synchronized String Time() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("KST"));
		int h = cal.get(Calendar.HOUR);
		int m = cal.get(Calendar.MINUTE);
		StringBuffer sb = new StringBuffer();
		if (h < 10) {
			sb.append("0");
		}
		sb.append(h);
		sb.append(":");
		if (m < 10) {
			sb.append("0");
		}
		sb.append(m);
		return sb.toString();
	}
}