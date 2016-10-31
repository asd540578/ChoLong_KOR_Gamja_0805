/*
\ * This program is free software; you can redistribute it and/or modify
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
package l1j.server.server.serverpackets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;

import l1j.server.Config;
import l1j.server.server.Account;
import l1j.server.server.Opcodes;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Clan.ClanMember;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * ��ų �������̳� ���� ����Ʈ�� ǥ�� �� ������ �뵵�� ���Ǵ� ��Ŷ�� Ŭ����
 */
public class S_PacketBox extends ServerBasePacket {
	private static final String S_PACKETBOX = "[S] S_PacketBox";

	private byte[] _byte = null;

	// *** S_107 sub code list ***
	/** ���� �ý��� by��� */
	public static final int BAPO = 114;
	// 1:Kent 2:Orc 3:WW 4:Giran 5:Heine 6:Dwarf 7:Aden 8:Diad 9:���� 9 ...

	public static final int DRAGONMENU = 102; // �巡�� �޴�
	public static final int DRAGON_EME = 86;

	/** C(id) H(?): %s�� �������� ���۵Ǿ����ϴ�. */
	public static final int MSG_WAR_BEGIN = 0;

	/** C(id) H(?): %s�� �������� �����߽��ϴ�. */
	public static final int MSG_WAR_END = 1;

	/** C(id) H(?): %s�� �������� �������Դϴ�. */
	public static final int MSG_WAR_GOING = 2;

	/** -: ���� �ֵ����� ��ҽ��ϴ�. (������ �ٲ��) */
	public static final int MSG_WAR_INITIATIVE = 3;

	/** -: ���� �����߽��ϴ�. */
	public static final int MSG_WAR_OCCUPY = 4;

	/** ?: ������ �������ϴ�. (������ �ٲ��) */
	public static final int MSG_DUEL = 5;

	/** C(count): SMS�� �۽ſ� �����߽��ϴ�. / ����%d�Ǽ۽ŵǾ����ϴ�. */
	public static final int MSG_SMS_SENT = 6;

	/** -: �ູ��, 2���� �κημ� ����Ǿ����ϴ�. (������ �ٲ��) */
	public static final int MSG_MARRIED = 9;

	/** C(weight): �߷�(30 �ܰ�) */
	public static final int WEIGHT = 10;

	/** C(food): ������(30 �ܰ�) */
	public static final int FOOD = 11;

	/** C(0) C(level): �� ��������%d���� ���ϸ� ����� �� �ֽ��ϴ�. (0~49�ܴ̿� ǥ�õ��� �ʴ´�) */
	public static final int MSG_LEVEL_OVER = 12;

	/** UB���� HTML */
	public static final int HTML_UB = 14;

	/**
	 * C(id)<br>
	 * 1:���� ����� �ִ� ������ ���� ����ȿ� ��� ���� ���� �������ϴ�.<br>
	 * 2:���� ���������� ȭ�� ���ɷ��� ������ �ɴϴ�.<br>
	 * 3:���� ���������� ���� ���ɷ��� ������ �ɴϴ�.<br>
	 * 4:���� ���������� �ٶ��� ���ɷ��� ������ �ɴϴ�.<br>
	 * 5:���� ���������� ���� ���ɷ��� ������ �ɴϴ�.<br>
	 */

	public static final int MSG_ELF = 15;

	/** C(count) S(name)...: ���� ����Ʈ ���� �߰� */
	public static final int ADD_EXCLUDE2 = 17;

	/** S(name): ���� ����Ʈ �߰� */
	public static final int ADD_EXCLUDE = 18;

	/** S(name): ���� ���� */
	public static final int REM_EXCLUDE = 19;

	/** ��ų ������ */
	public static final int ICONS1 = 20;

	/** ��ų ������ */
	public static final int ICONS2 = 21;

	/** �ƿ����� ��ų ������ �� �̷�������� ������ ���� */
	public static final int ICON_AURA = 22;

	/** S(name): Ÿ�� ��������%s�� ���õǾ����ϴ�. */
	public static final int MSG_TOWN_LEADER = 23;

	/**
	 * D(���Ϳ���) (S(�����̸�) C(�������)) ���Ϳ� ������ �� ���¿����� /����.
	 */
	public static final int PLEDGE_TWO = 24;

	/**
	 * D(���Ϳ��̸�) C(��ũ) ���Ϳ� �߰��� �ο��� ������ �����ִ� ��Ŷ
	 */
	public static final int PLEDGE_REFRESH_PLUS = 25;

	/**
	 * D(���Ϳ��̸�) C(��ũ) ���Ϳ� ������ �ο��� ������ �����ִ� ��Ŷ
	 */
	public static final int PLEDGE_REFRESH_MINUS = 26;

	/**
	 * C(id): ����� ��ũ��%s�� ����Ǿ����ϴ�. (1-�߽� 2-�Ϲ� 3-��ȣ���)
	 */
	public static final int MSG_RANK_CHANGED = 27;

	/**
	 * D(���Ϳ���) (S(�����̸�) C(�������)) ���Ϳ� ������ �ȵ� ���¿����� /����.
	 */
	//public static final int PLEDGE_ONE = 119;

	/** D(?) S(name) S(clanname): %s������%s�� ��Ÿ�ٵ屺�� ġ�����ϴ�. */
	public static final int MSG_WIN_LASTAVARD = 30;

	/** -: \f1����� ���������ϴ�. */
	public static final int MSG_FEEL_GOOD = 31;

	/** �Ҹ�.C_30 ��Ŷ�� ���� */
	public static final int SOMETHING1 = 33;

	/** H(time): ��� �Ϻ��� �������� ǥ�õȴ�. */
	public static final int ICON_BLUEPOTION = 34;

	/** H(time): ������ �������� ǥ�õȴ�. */
	public static final int ICON_POLYMORPH = 35;

	/** H(time): ä�� ������ �������� ǥ�õȴ�. */
	public static final int ICON_CHATBAN = 36;

	/** �� ������ ���� ��Ŷ */
	public static final int PET_ITEM = 37;

	/** ���� ������ HTML�� ǥ�õȴ� */
	public static final int HTML_CLAN1 = 38;

	/** H(time): �̹��� �������� ǥ�õȴ� */
	public static final int ICON_I2H = 40;

	/** ĳ������ ���� �ɼ�, ��Ʈ �� �������� ������ */
	public static final int CHARACTER_CONFIG = 41;

	/** ĳ���� ���� ȭ������ ���ư��� */
	public static final int LOGOUT = 42;

	/** �����߿� ��� ������ �� �����ϴ�. */
	public static final int MSG_CANT_LOGOUT = 43;

	/**
	 * C(count) D(time) S(name) S(info):<br>
	 * [CALL] ��ư�� ���� �����찡 ǥ�õȴ�. �̸��� ���� Ŭ�� �ϸ�(��) C_RequestWho�� ����, Ŭ���̾�Ʈ�� ������
	 * bot_list.txt�� �����ȴ�.�̸��� ������+Ű�� ������(��) ���ο� �����찡 ������.
	 */
	public static final int CALL_SOMETHING = 45;

	/**
	 * C(id): ��Ʋ �ݷԼ���, ī���� �����̡�<br>
	 * id - 1:�����մϴ� 2:�����Ǿ��� 3:�����մϴ�
	 */
	public static final int MSG_COLOSSEUM = 49;

	/** ���� ������ HTML */
	public static final int HTML_CLAN2 = 51;

	/** �丮 �����츦 ���� */
	public static final int COOK_WINDOW = 52;

	/** C(type) H(time): �丮 �������� ǥ�õȴ� */
	public static final int ICON_COOKING = 53;

	/** ������� ��鸲���� */
	public static final int FISHING = 55;

	/** ������ ���� */
	public static final int DEL_ICON = 59;

	public static final int DRAGONPERL = 60; // ����

	public static final int EXP_POTION3 = 9278;

	public static final int EXP_POTION2 = 9279;

	/** ���� �ð� type:������� time:�ð� */
	public static final int ACCOUNT_TIME = 61;

	/** ���� ��� */
	public static final int ALLIANCE_LIST = 62;

	/** �̴ϰ��� : 5,4,3,2,1 ī��Ʈ */
	public static final int MINIGAME_START_COUNT = 64;

	/** �̴ϰ��� : Ÿ��(0:00����) */
	public static final int MINIGAME_START_TIME = 65;

	/** �̴ϰ��� : ������ ����Ʈ */
	public static final int MINIGAME_LIST = 66;

	/** �̴ϰ��� : ��� �� ������ �̵��˴ϴ�(10�� ��) **/
	public static final int MINIGAME_10SECOND_COUNT = 69;

	/** �̴ϰ��� : ���� */
	public static final int MINIGAME_END = 70;

	/** �̴ϰ��� : Ÿ�� */
	public static final int MINIGAME_TIME = 71;

	/** �̴ϰ��� : Ÿ�ӻ��� */
	public static final int MINIGAME_TIME_CLEAR = 72;

	// 73 value 100������ ���� �Ҹ� �鸲

	/** ���� ���� ���� �ɷ��� �϶��մϴ�. */
	public static final int DAMAGE_DOWN = 74;

	/** ���� : ���� ���� */
	public static final int SPOT = 75;

	/**
	 * �������� ���۵Ǿ����ϴ�. 78 ���� �����ϰ� �ִ� ������ ������ �����ϴ�.
	 */

	/**
	 * �������� ����Ǿ����ϴ�. 79 ���� �����ϰ� �ִ� ������ ������ �����ϴ�.
	 */

	/**
	 * �������� �������Դϴ�. 80 ���� �����ϰ� �ִ� ������ ������ �����ϴ�.
	 */

	// public static final int HADIN_DISPLAY = 83;

	public static final int HADIN_DISPLAY = 83;
	public static final int GREEN_MESSAGE = 84;
	public static final int YELLOW_MESSAGE = 00000; // �δ� é��2 ���

	/** �����ϻ�� ���� */
	public static final int AINHASAD = 82;

	/**
	 * ��ȣ�� UI ǥ�� + ����� ���� - �׸��� ����
	 */
	public static final int KARMA = 87;

	/**
	 * ��ȣ�� ������ ���� �Һи� ��Ŷ 2�� ��
	 */
	public static final int INIT_DG = 88;
	public static final int DRAGONBLOOD = 100;
	public static final int UPDATE_DG = 101;

	public static final int DG_TIME_RESTART = 159;// 9F
	public static final int TIME_COUNT = 0x99;
	public static final int ���ȹ��� = 125;
	public static final int PC����� = 127;
	public static final int LOGIN_UNKNOWN3 = 32;
	public static final int char_ER = 132;
	public static final int ����ջ󸶿콺 = 138;
	public static final int ���â_Ȯ�� = 141;

	public static final int �����ð����� = 86;

	/*
	 * public static final int ���� = 142; [������Ŷ] [2014-07-09 ���� 10:49:27:667]
	 * [OPCODE = 118]
	 * 
	 * 0000: 76 8e 00 ef 4e 0d 00 24 31 33 37 31 39 00 08 5c v...N..$13719..\
	 * 0010: 66 56 5b bd c5 ba f1 5d 20 5c 66 58 be f3 c0 bd fV[....] \fX....
	 * 0020: 20 c8 a3 bc f6 20 c1 df be d3 00 04 00 00 00 d2 .... ..........
	 * 0030: 84 49 7e 5c 66 56 5b bd c5 ba f1 5d 20 5c 66 58 .I~\fV[....] \fX
	 * 0040: be f3 c0 bd 20 c8 a3 bc f6 20 bf dc b0 a2 00 04 .... .... ......
	 * 0050: 00 00 00 ca 84 4c 7e 5c 66 56 5b bd c5 ba f1 5d .....L~\fV[....]
	 * 0060: 20 5c 66 58 be f3 c0 bd 20 c0 fd ba ae 00 04 00 \fX.... .......
	 * 0070: 00 00 a4 85 57 7e 5c 66 56 5b bd c5 ba f1 5d 20 ....W~\fV[....]
	 * 0080: 5c 66 58 be c6 b5 a7 20 c0 db c0 ba 20 b9 e8 00 \fX.... .... ...
	 * 0090: 04 00 00 00 92 85 6f 81 5c 66 56 5b bd c5 ba f1 ......o.\fV[....
	 * 00a0: 5d 20 5c 66 58 c7 cf c0 cc b3 d7 20 b0 e6 b0 e8 ] \fX...... ....
	 * 00b0: c5 be 00 04 00 00 00 df 82 e1 82 5c 66 56 5b bd ...........\fV[.
	 * 00c0: c5 ba f1 5d 20 5c 66 58 bf eb c0 c7 20 b0 e8 b0 ...] \fX.... ...
	 * 00d0: ee 20 c0 fd ba ae 00 04 00 00 00 ba 82 38 7e 5c . ...........8~\
	 * 00e0: 66 56 5b bd c5 ba f1 5d 20 5c 66 58 bc fb b0 dc fV[....] \fX....
	 * 00f0: c1 f8 20 b0 c5 ba cf c0 cc 20 bc b6 00 04 00 00 .. ...... ......
	 * 0100: 00 3f 82 8b 81 5c 66 56 5b bd c5 ba f1 5d 20 5c .?...\fV[....] \
	 * 0110: 66 58 c1 a4 bd c5 b0 fa 20 bd c3 b0 a3 c0 c7 20 fX...... ......
	 * 0120: bc b6 00 04 00 00 00 2b 7f d3 80 .......+..
	 */

	public static final int SKILL_WEAPON_ICON = 154;
	public static final int ROUND_SHOW = 156;
	public static final int ROUND_DEL = 158;
	public static final int WEAPON_RANGE = 160;
	public static final int Ŀ������ = 161;// A1
	public static final int ���͹��� = 165;// 173
	/*
	 * [OPCODE = 239] 0000: ef ac e3 27 ab 11 17 00 00 00 00 00 01 d7 a5 e6
	 * ...'............ 0010: 19 00 00 00 00 00 00 00 00 07 00 00 00 00 2e 3f
	 * ...............?
	 */
	public static final int ��æ���� = 172;
	public static final int ������ġ���� = 176;
	public static final int �巡�ﷹ�̵���� = 179;
	public static final int ����_���� = 182;
	public static final int ��Ʋ�� = 181;
	public static final int NONE_TIME_ICON = 180;
	public static final int WORLDMAP_UNKNOWN1 = 184;
	public static final int ACTION_GUIDE_1 = 188;
	public static final int ACTION_GUIDE_2 = 189;
	public static final int ��������Ƚ�� = 198;
	public static final int ���������� = 193;
	public static final int �̹������� = 194;//

	/**�߰���Ŷ 10.20opcodes**/
	  public static final int MINI_MAP_SEND = 111;
	  public static final int SCORE_MARK = 4;
	  public static final int aaaa1 = 78;
	  public static final int bbbb2 = 79;
	  public static final int cccc3 = 80;
	  public static final int HTML_PLEDGE_REALEASE_ANNOUNCE = 168;
	  public static final int HTML_PLEDGE_ONLINE_MEMBERS = 171;
	  public static final int UNLIMITED_ICON = 147;
	  public static final int ITEM_ = 149;
	  public static final int ICON_COMBO_BUFF = 204;

	private static final int PLEDGE_ONE = 0;

	public static final int BUFFICON = 0;
	  
	  /** 10.20 �߰���Ŷ **/

	  public S_PacketBox(int subCode, L1ItemInstance item, int type)
	  {
	    writeC(108);
	    writeC(subCode);
	    switch (subCode) {
	    case ITEM_:
	      writeD(item.getId());
	      writeH(type);
	    }
	  }
	  public S_PacketBox(int subCode, Object[] names)
	  {
	    writeC(108);
	    writeC(subCode);

	    switch (subCode) {
	    case 171:
	      writeH(names.length);
	      for (Object name : names)
	        if (name != null) {
	          L1PcInstance pc = (L1PcInstance)name;
	          writeS(pc.getName());
	          writeC(0);
	        }
	      break;
	    }
	  }
	  public S_PacketBox(int subCode, String name, int mapid, int x, int y, int Mid)
	  {
	    writeC(108);
	    writeC(subCode);
	    switch (subCode) {
	    case 111:
	      writeS(name);
	      writeH(mapid);
	      writeH(x);
	      writeH(y);
	      writeD(Mid);
	      break;
	    }
	  }
	// 183 ���� �ð� : �Ϲ� XX(��) / PC�� �� XX(��)�� ���ҽ��ϴ�.
	public S_PacketBox(L1ItemInstance item, int subcode) {
		writeC(Opcodes.S_EVENT);
		writeC(subcode);

		writeD(item.getId());
		writeD(0x17);
		writeH(0x00);
		writeC(item.getEnchantLevel());
		writeD(item.getId());
		writeD(0);
		writeD(0);
		if (!((item.getItemId() >= 437010 && item.getItemId() <= 437013)
				|| item.getItemId() == 5000067 || item.getItemId() == 60104
				|| (item.getItemId() >= 21125 && item.getItemId() <= 21136)
				|| (item.getItemId() >= 20452 && item.getItemId() <= 20455)
				|| (item.getItemId() >= 421000 && item.getItemId() <= 421020)
				|| item.getItemId() == 560025 || item.getItemId() == 560027
				|| item.getItemId() == 560028 || item.getItemId() == 41159
				|| (item.getItemId() >= 21139 && item.getItemId() <= 21156)
				|| (item.getItemId() >= 60286 && item.getItemId() <= 60289)
				|| (item.getItemId() >= 60261 && item.getItemId() <= 60263)
				|| item.getItemId() == 60354 || item.getItemId() == 60396
				|| item.getItemId() == 60398
				|| (item.getItemId() >= 60427 && item.getItemId() <= 60444)
				|| (item.getItemId() >= 60447 && item.getItemId() <= 60472)
				|| item.getItemId() == 21256 || item.getItemId() == 21257 || item
				.getItemId() == 60492) && !item.getItem().isTradable()) {
			writeC(0x00);
		} else {
			writeC(7);// â�� ���� ����
			/*
			 * if(item.getItem().getType2()==2){ writeC(3); }else{
			 * writeC(7);//â�� ���� ���� }
			 */
		}

		writeD(0);
		writeH(0);
	}

	public S_PacketBox(int subCode) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
	    case 188:
	        writeD(0);
	        writeD(0);
	        break;
	    case 189:
	        writeD(0);
	        break;
		case MSG_WAR_INITIATIVE: //3
		case MSG_WAR_OCCUPY: //4
		case MSG_MARRIED: //9
		case MSG_FEEL_GOOD: //31
		case MSG_CANT_LOGOUT: //43
		case LOGOUT: //42
		case ROUND_DEL: //158
		case 125: //���ȹ���
			break;
		case FISHING:
		case MINIGAME_START_TIME:
		case MINIGAME_TIME_CLEAR:
			break;
		case CALL_SOMETHING:
			callSomething();
			break;
		case WORLDMAP_UNKNOWN1:
		case DEL_ICON:
			writeH(0);
			break;
		case ICON_AURA:
			writeC(0x98);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			break;
		case MINIGAME_10SECOND_COUNT:
			writeC(10);
			writeC(109);
			writeC(85);
			writeC(208);
			writeC(2);
			writeC(220);
			break;
		case MINIGAME_END:
			writeC(147);
			writeC(92);
			writeC(151);
			writeC(220);
			writeC(42);
			writeC(74);
			break;
		case MINIGAME_START_COUNT:
			writeC(5);
			writeC(129);
			writeC(252);
			writeC(125);
			writeC(110);
			writeC(17);
			break;
		case LOGIN_UNKNOWN3:
			/*
			 * 10 17 fc 3f 00 00 00 00 00 00 00 00 00 00 00 00 00
			 */
			writeC(0x10);// writeC(0x17);writeC(0xfc);writeC(0x3f);
			writeD(0);
			writeD(2);
			writeD(0);
			writeD(0x00200000);
			break;
		/*
		 * case ACTION_GUIDE_1: //writeD(0x0002F070);//�� ���� ���� �����׾����� ��� ����޾Ҵ���
		 * �����µ� //writeD(0x0006F1F8);
		 * 
		 * writeD(0xFFFFFFFF); writeD(0x00); break;
		 */

		case ������ġ����:
			writeC(0x01);
			writeH(0x00);
			break;
		case 73:
			writeS("voice_kirtas_12");
			break;
	
		default:
			// writeH(0xFFFF);
			break;
		}
	}

	public S_PacketBox(int subCode, int objid, int value, boolean show) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case ICON_AURA:
			writeC(value);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0);
			break;
		case �̹�������:

			/*
			 * 12750 = ȭ��Ƚ�ó ��ų
			 */
			writeD(objid); // ������Ʈ���̵�
			writeD(value); // �̹��� ��ȣ
			writeD(show ? 0x01 : 0x00); // ���� �߰�
			writeH(0);
			break;
		}
	}

	public S_PacketBox(int subCode, int value, boolean show) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case DRAGON_EME:
			writeC(0x4C);
			writeC(show ? 0x01 : 0x00);
			writeC(value);// 0x2d(45)ũ���� 0x4a(74)����
			break;
		  case 147:
		      writeC(show ? 1 : 0);
		      writeC(value);
		      break;
		  case 180:
		      writeC(show ? 1 : 0);
		      writeD(value);
		      writeD(0);
		      writeH(0);
		      break;
		case BAPO:
			writeD(value); // 1~7 ���
			writeD(show ? 0x01 : 0x00); // On Off
		      break;
		
		default:
			break;
		}
	}

	public S_PacketBox(int subCode, int value, String svalue) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);

		switch (subCode) {
		
		case MSG_RANK_CHANGED:
			writeC(value);
			writeS(svalue);
			break;
	 
		default:
			break;
		}
	}

	public S_PacketBox(int subCode, int value) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case PC�����:
			if (value == 1) {
				writeC(0x18);
			} else {
				writeC(0);
			}
			break;
		case DRAGON_EME:
			if (value == 0) {
				writeH(0x90);
				writeC(0);
			} else {
				writeH(0xb7);
				writeC(0);
			}
			break;
		case ICON_AURA:
			writeC(0x5a);
			writeH(value);
			break;
		case ICON_BLUEPOTION:
		case ICON_CHATBAN:
		case ICON_I2H:
		case ICON_POLYMORPH:
		case MINIGAME_TIME:
		case INIT_DG:
			writeH(value); // time
			break;
		case MSG_WAR_BEGIN:
		case MSG_WAR_END:
		case MSG_WAR_GOING:
			writeC(value); // castle id
			writeH(0); // ?
			break;
		case MSG_SMS_SENT:
		case WEIGHT:
		case FOOD:
		case UPDATE_DG:
		case MSG_ELF:
		case MSG_COLOSSEUM:
		case SPOT:
		case ���â_Ȯ��:
			writeC(value); // msg id
			break;
		   case 204: //�޺�
			      writeH(value);
			      break;
		case MSG_LEVEL_OVER:
			writeC(0); // ?
			writeC(value); // 0-49�ܴ̿� ǥ�õ��� �ʴ´�
			break;
		case COOK_WINDOW:
			writeC(0xdb); // ?
			writeC(0x31);
			writeC(0xdf);
			writeC(0x02);
			writeC(0x01);
			writeC(value); // level
			break;
		case HADIN_DISPLAY:
			writeD(value);
			writeH(0);
			break;
		case TIME_COUNT:
			writeH(value);
			writeH(0);
			break;
		case 129:
			writeC(value);
			writeC(value);
			break;
		case ���͹���:
		      writeC(1);
		      if (value == 0)
		        writeC(0);
		      else if (value == 1) {
		        writeC(1);
		      }
		      writeD(0);
		      break;
		case ROUND_SHOW:
		      writeD(value);
		      writeD(12);
		      break;
		case ����ջ󸶿콺:
			writeC(value);
			break;
		case ����_����:
			writeD(value);
			writeH(0x00);
			break;
		case ��Ʋ��:
			writeD(value);
			break;
		case �巡�ﷹ�̵����:
			writeC(0x01);
			writeC(0x27);
			writeC(0x0E);
			writeD(value);// ������
			writeH(0x63EF);
			break;
		case char_ER:
			writeC(value);
			break;
		case ��������Ƚ��:
			writeD(value);
			writeD(0x28);
			writeD(0x00);
			break;
		default:
			break;
		}
	}

	/** �ڼ��� **/
	// public S_PacketBox(int time, int val, boolean ck, boolean ck2) {
	public S_PacketBox(int time, int val, boolean ck, boolean ck2) {
		writeC(Opcodes.S_EVENT);
		writeC(DRAGON_EME);
		writeC(0x81);
		writeC(0x01);
		writeC(val);
		writeH(time);
	}// 7e 56 81 01 02 08 07

	public S_PacketBox(int i, int time, boolean ck, boolean ck2, boolean ck3) {
		writeC(Opcodes.S_EVENT);
		writeC(DRAGON_EME);
		writeC(0x3e);
		writeC(i);
		writeH(time);
		writeC(0x14);
		writeC(0x86);
	}// 0f 56 3e 01 08 07 14 86

	public S_PacketBox(L1PcInstance pc, int time, boolean ck, boolean ck2,
			boolean ck3) {
		writeC(Opcodes.S_EVENT);
		writeC(DRAGON_EME);
		writeC(0x3e);
		if (pc.getMap().isSafetyZone(pc.getX(), pc.getY())) {
			writeC(0x02);
		} else {
			writeC(0x01);
		}
		writeH(time);
		writeC(0x14);
		writeC(0x86);
	}// 0f 56 3e 01 08 07 14 86

	/** ������ ���� **/
	public S_PacketBox(int time, boolean ck, boolean ck2) {
		writeC(Opcodes.S_EVENT);
		writeC(0x56);
		writeC(0xAA);
		writeC(0x01);
		writeH(time / 16);
		writeH(0x00);
	}

	public S_PacketBox(int subCode, int type, int time, boolean second,
			boolean temp) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case SKILL_WEAPON_ICON:
			writeH(time);
			writeH(type);
			writeH(0);
			writeH(second ? 0x01 : 0x00); // ���� �߰�
			break;
		}// b0 04 80 08 00 00 00 00
	}

	
	public S_PacketBox(int subCode, int type, int time) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);

		switch (subCode) {
		case DRAGONBLOOD:
			writeC(type);
			writeD(time);
			break;
		case DRAGON_EME:
			writeC(0x70);
			writeC(0x01);
			writeC(type);
			writeD(time);
			break;
		case DRAGONPERL:
			writeC((int)((time + 2) / 4));
			writeC(type);
			break;
		case EXP_POTION2:
			writeC(time);
			writeC(type);
			break;
		case EXP_POTION3:
			writeC(time);
			writeC(type);
			break;
		// 0f
		// 3d e3 8b 24 00 00 �Ǿ���
		case ACCOUNT_TIME:
			writeD(0);
			writeH(type);
			break;
		case ICON_COOKING:
			writeC(0x13);
			writeC(0x0b);
			writeC(0x0a);
			writeC(0x12);
			writeH(0x0909);
			writeC(0xD0);
			writeC(0x07);
			writeC(type);
			writeC(0x26);
			writeH(time);
			writeC(0x61);
			/*
			 * if (type != 7) { writeC(0x0c); writeC(0x0c); writeC(0x0c);
			 * writeC(0x12); writeC(0x0c); writeC(0x09); writeC(0x00);
			 * writeC(0x00); writeC(type); writeC(0x24); writeH(time);
			 * writeH(0x00);
			 * 
			 * } else { writeC(0x0c); writeC(0x0c); writeC(0x0c); writeC(0x12);
			 * writeC(0x0c); writeC(0x09); writeC(0xc8); writeC(0x00);
			 * writeC(type); writeC(0x26); writeH(time); writeC(0x3e);
			 * writeC(0x87); }
			 */
			break;
		case ICON_AURA:
			writeC(0xdd);
			writeH(time);
			writeC(type);
			break;

		case MSG_DUEL:
			writeD(type);
			writeD(time);
			break;
		case Ŀ������:// 161
			writeC(type); // 1��, 2�з�, 6����
			if (type == 2) {
				writeH(0x00); // ������ �ð�, ����
				writeH(time); // �з� �ð�
			} else {
				writeH(time); // ������ �ð�, ����
				writeH(0x00); // �з� �ð�
			}
			break;

		case NONE_TIME_ICON:
			writeC(type);// on/off
			writeD(time);// 166 exp30% 228 �ÿ��Ѿ�������286 exp40% 343 �⸣Ÿ����������г�Ƽ
							// 409�ƸӺ극��ũ 497�����������ǥ �̺�Ʈ������ //477~479
			writeD(3431);
			writeH(0);
			break;
		default:
			break;
		}
	}

	public S_PacketBox(int subCode, int type, int petid, int ac) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);

		switch (subCode) {

		case PET_ITEM:
			writeC(type);
			writeD(petid); // pet objid
			writeH(ac);
			break;
		default:
			break;
		}
	}

	public S_PacketBox(int subCode, L1ItemInstance item) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case DRAGONMENU:
			writeD(item.getId());
			writeC(1);
			writeC(1);
			writeC(1);
			writeC(0);
		//      writeD(item.getId());
		 //     writeC(item.getItemId() == 490012 ? 1 : 0);
		  //    writeC(item.getItemId() == 490013 ? 1 : 0);
		   //   writeC(item.getItemId() == 490014 ? 1 : 0);
		    //  writeC(0);
			break;
		  case 172: //��þ����
		      writeD(item.getId());
		      writeC(23);
		      writeC(0);
		      writeH(0);
		      writeH(0);
		      writeC(item.getEnchantLevel());
		      writeD(item.getId());
		      writeD(0);
		      writeD(0);
		      writeD(item.getItem().isTradable() ? 7 : item.getBless() >= 128 ? 3 : 2);
		      writeD(0);
		      break;
		    }
		  }

	public S_PacketBox(int subCode, String name) {

		writeC(Opcodes.S_EVENT);
		writeC(subCode);

		switch (subCode) {
		case MSG_TOWN_LEADER:
			// case PLEDGE_UNION:
		case 168:
			writeS(name);
			break;
		case GREEN_MESSAGE:
			writeC(2);
			writeS(name);
			break;
		case YELLOW_MESSAGE:
			break;
		default:
			break;
		}
	}

	public S_PacketBox(int subCode, String name, int type) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case ADD_EXCLUDE:
		case REM_EXCLUDE:
			writeS(name);
			writeC(type);// Ÿ�� 0:���ܸ���Ʈ 1:���� ���ܸ���Ʈ
			break;
		default:
			break;
		}
	}

	public S_PacketBox(int subCode, Object[] names, int type) {
		writeC(Opcodes.S_EVENT);
		writeH(subCode);

		switch (subCode) {
		case ADD_EXCLUDE2:
			writeC(type);// Ÿ�� 0: ���ܸ���Ʈ, 1: ��������
			writeC(names.length);
			for (Object name : names) {
				writeS(name.toString());
			}
			break;
		default:
			break;
		}
	}

	public S_PacketBox(int subCode, int id, String name, String clanName) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);

		switch (subCode) {
		case MSG_WIN_LASTAVARD:
			writeD(id); // ũ�� ID�ΰ� �����ΰ�?
			writeS(name);
			writeS(clanName);
			break;
		default:
			break;
		}
	}

	public S_PacketBox(L1PcInstance pc, int subCode) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);

		switch (subCode) {
		case PLEDGE_ONE:
		case PLEDGE_TWO:
			String clanName = pc.getClanname();
			L1Clan clan = L1World.getInstance().getClan(clanName);
			writeD(clan.getClanMemberList().size());

			ClanMember member;
			ArrayList<ClanMember> clanMemberList = clan.getClanMemberList(); // ���
																				// ���Ϳ���
																				// �̸���
																				// ���
			for (int i = 0; i < clanMemberList.size(); i++) {
				member = clanMemberList.get(i);
				writeS(member.name);
				if (member.rank == 5) {
					writeC(3);
				} else {
					writeC(member.rank);
				}
			}

			/*
			 * if (offlineMemberCount > 0) { for(int i = 0 ; i <
			 * offlineMemberCount ; i++) { // �������� String name =
			 * clan.getOfflineMemberName(i); int rank =
			 * clan.getOfflineMemberRank(name); if(rank < 0) continue;
			 * writeS(name); writeC(rank); } }
			 */

			writeD(clan.getOnlineMemberCount());
			for (L1PcInstance targetPc : clan.getOnlineClanMember()) { // �¶���
				writeS(targetPc.getName());
			}
			break;
		case PLEDGE_REFRESH_PLUS:
		case PLEDGE_REFRESH_MINUS:
			writeS(pc.getName());
			writeC(pc.getClanRank());
			writeH(0);
			break;
		case KARMA:
			writeD(pc.getKarma());
			break;
		case ALLIANCE_LIST:
			StringBuffer sb = new StringBuffer();
			for (int i : pc.getClan().Alliance()) {
				if (i == 0)
					continue;
				L1Clan c = L1World.getInstance().getClan(i);
				if (c == null)
					continue;
				sb.append(c.getClanName() + " ");
			}
			writeS(sb.toString());
			break;
		
		
		
		
		case DG_TIME_RESTART:
			writeD(7);
			writeD(1);
			writeS("$12125");// �Ⱘ
			int time = pc.�����ð�üũ("�Ⱘ");
			writeD(time > 0 ? time / 60 : 0);
			writeD(15);

			writeS("$6081");
			time = pc.�����ð�üũ("���ž");
			writeD(time > 0 ? time / 60 : 0);
			writeD(19);

			writeS("$12126");
			time = pc.�����ð�üũ("��");
			writeD(time > 0 ? time / 60 : 0);
			writeD(41);
			
		//	writeS("$14250");
		//	time = pc.�����ð�üũ("��");
		//	writeD(time > 0 ? time / 60 : 0);
		//	writeD(500);

			writeS("$19375");
			time = pc.�����ð�üũ("��");
			writeD(time > 0 ? time / 60 : 0);
			writeD(49200);

			time = 7200 - pc.getravatime();
			writeD(time > 0 ? time / 60 : 0);
			break;
		default:
			break;
		}
	}

	public S_PacketBox(String name, int mapid, int x, int y, int Mid) {

		writeC(Opcodes.S_EVENT);
		writeC(0x6F);
		writeS(name);
		writeH(mapid);
		writeH(x);
		writeH(y);
		writeH(Mid);
		writeH(0);
	}

	/**
	 * ���� ��� SCORE
	 * 
	 * @param on
	 *            TRUE : ǥ�� FALSE : ��ǥ��
	 * @param score
	 *            ��� ����
	 */
	public S_PacketBox(boolean on, int score) {
		writeC(Opcodes.S_EVENT);
		writeC(0x54);
		if (on) {
			writeC(4);
			writeS("" + score);
		} else {
			writeC(3);
			writeS("" + score);
		}
	}

	public S_PacketBox(int subCode, L1PcInstance pc, L1ItemInstance item,
			boolean bool) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case WEAPON_RANGE: {
			int polyId = pc.getGfxId().getTempCharGfx();
			int ���� = 1;
			int ���� = 0;
			int ������� = 0;
		
			
			if (polyId == 3784 || polyId == 13152 || polyId == 13153
					|| polyId == 12702 || polyId == 12681 || polyId == 6137
					|| polyId == 6142 || polyId == 6147 || polyId == 6152
					|| polyId == 6157 || polyId == 9205 || polyId == 9206) {
				���� = 1;
			} else {
				if (bool) {
					if (pc.getWeapon().getItem().getType() == 4) {
						���� = 17;
					} else if (pc.getWeapon().getItem().getType() == 13
							|| pc.getWeapon().getItem().getType() == 10) {
						���� = 15;
					} else if (pc.getWeapon().getItem().getType() == 5
							|| pc.getWeapon().getItem().getType() == 14
							|| pc.getWeapon().getItem().getType() == 18) {
						���� = 2;
					} else
						���� = 1;

					if (���� == 2
							&& (polyId == 7967 || polyId == 10874
									|| polyId == 7846 || polyId == 7848 || polyId == 8719)) {
						���� = 1;
					}
					if (���� == 2 && polyId >= 11328 && polyId <= 11448
							&& !(polyId >= 11408 && polyId <= 11421)) {
						if (polyId == 11330 || polyId == 11344
								|| polyId == 11351 || polyId == 11368
								|| polyId == 11376 || polyId == 11447)
							���� = 2;
						else
							���� = 1;
					}
				} else {
					���� = 1;
				}
			}
			if (���� == 17 || ���� == 15) {
				���� =  3;
				������� = 1;
				if(���� == 15){
					������� = 0;
				}
			} else {
				if (bool) {
					���� = 1;
					if (pc.getWeapon().getItem().getType() == 3) {
						������� = 1;
					}

					if (pc.getWeapon().getItem().getType() == 18) {
						���� = 10;
						������� = 1;
					}
				}
			}
			if(pc.getWeapon() != null && pc.getWeapon().getItem().getType1() == 54){
				writeC(0x01);
				writeC(0x04);
				writeC(0x01);
				writeC(0x02);
				writeC(0xe2);
				writeC(0x0a);
			}else
			writeC(����);
			writeC(����);
			writeC(�������);
		}
			break;

		}
	}
	public S_PacketBox(int subCode, L1PcInstance pc) {
		// TODO �ڵ� ������ ������ ����
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case AINHASAD:
			int value = pc.getAinHasad();
			value /= 10000;
			writeD(value);// % ��ġ 1~200
			writeD(10000);
			writeD(0x00);
			break;
		case ����������:
			writeH(pc.getX());
			writeH(pc.getY());
			break;

		}
	}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public S_PacketBox(int subCode, String[] names, int type)
{
  writeC(108);
  writeC(subCode);
  writeC(0);
  switch (subCode) {
  case 17: //���ܸ���Ʈ
    writeC(type);
    writeC(names.length);
    for (String name : names) {
      writeS(name);
    }
    writeH(0);
  }
}

public S_PacketBox(int subCode, L1PcInstance pc, int value1, int value2)
{
  writeC(108);
  writeC(subCode);
  switch (subCode) {
  case 161: //Ŀ������
    writeC(value1);
    writeD(value2);
    break;
  }
}

public S_PacketBox(int subCode, boolean show)
{
  writeC(108);
  writeC(subCode);
  switch (subCode) {
  case 165: //���͹���
    writeC(show ? 1 : 0);
  }
}

	
	
	
	private void callSomething() {

		Iterator<L1PcInstance> itr = L1World.getInstance().getAllPlayers()
				.iterator();

		writeC(L1World.getInstance().getAllPlayers().size());
		L1PcInstance pc = null;
		Account acc = null;
		Calendar cal = null;
		while (itr.hasNext()) {
			pc = itr.next();
			acc = Account.load(pc.getAccountName());

			// �ð� ���� �켱 �α��� �ð��� �־� ����655

			if (acc == null) {
				writeD(0);
			} else {
				cal = Calendar.getInstance(TimeZone
						.getTimeZone(Config.TIME_ZONE));
				long lastactive = acc.getLastActive().getTime();
				cal.setTimeInMillis(lastactive);
				cal.set(Calendar.YEAR, 1970);
				int time = (int) (cal.getTimeInMillis() / 1000);
				writeD(time); // JST 1970 1/1 09:00 �� ����
			}

			// ĳ���� ����
			writeS(pc.getName()); // �ݰ� 12�ڱ���
			writeS(pc.getClanname()); // []���� ǥ�õǴ� ĳ���� ����.�ݰ� 12�ڱ���
		}
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}

		return _byte;
	}

	@Override
	public String getType() {
		return S_PACKETBOX;
	}
	/*
	 * �̷��̹��� ��Ŷ 0e <- PacektBox 15 00 00 00 00 <- �� 1000�� �ϰ�� 1000 / 16 + 1
	 */
}
