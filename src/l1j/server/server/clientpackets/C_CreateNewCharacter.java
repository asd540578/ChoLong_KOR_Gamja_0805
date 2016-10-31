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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.BadNamesList;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.MonsterBookTeleportTable;
import l1j.server.server.datatables.NpcShopSpawnTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Beginner;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_AddSkill;
import l1j.server.server.serverpackets.S_CharCreateStatus;
import l1j.server.server.serverpackets.S_NewCharPacket;
import l1j.server.server.templates.L1Skills;
import server.GameServer;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_CreateNewCharacter extends ClientBasePacket {
	private static Logger _log = Logger.getLogger(C_CreateNewCharacter.class
			.getName());
	private static final String C_OPCODE_CREATE_NEW_CHARACTER = "[C] C_CreateNewCharacter";

	public C_CreateNewCharacter(byte[] abyte0, LineageClient client)
			throws Exception {
		super(abyte0);

		try {
			String name = readS();
						
			L1PcInstance pc = new L1PcInstance();
			byte str, dex, con, intel, wis, cha;
			int total;

			for (int i = 0; i < name.length(); i++) {
				if (name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��.
						name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��
						name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��
						name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��.
						name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��.
						name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��.
						name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��.
						name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��.
						name.charAt(i) == '��' || name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��.
						name.charAt(i) == '��' || name.charAt(i) == '��'
						|| name.charAt(i) == '��' || name.charAt(i) == '��'
						|| // �ѹ���(char)������ ��.
						name.charAt(i) == '��' || name.charAt(i) == '��'
						|| name.charAt(i) == '��' || name.charAt(i) == '��') {
					S_CharCreateStatus s_charcreatestatus = new S_CharCreateStatus(
							S_CharCreateStatus.REASON_INVALID_NAME);
					client.sendPacket(s_charcreatestatus, true);
					return;
				}
			}

			if (name.length() == 0) {
				S_CharCreateStatus s_charcreatestatus = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_INVALID_NAME);
				client.sendPacket(s_charcreatestatus, true);
				return;
			}

			if (BadNamesList.getInstance().isBadName(name)) {
				S_CharCreateStatus s_charcreatestatus = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_INVALID_NAME);
				_log.info("���� ������ ĳ���� �̸�, ��������");
				client.sendPacket(s_charcreatestatus, true);
				return;
			}

			if (isInvalidName(name, client)) {
				S_CharCreateStatus s_charcreatestatus = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_INVALID_NAME);
				client.sendPacket(s_charcreatestatus, true);
				return;
			}

			if (CharacterTable.doesCharNameExist(name)) {
				_log.fine("charname: " + pc.getName()
						+ " already exists. creation failed.");
				S_CharCreateStatus s_charcreatestatus1 = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_ALREADY_EXSISTS);
				client.sendPacket(s_charcreatestatus1, true);
				return;
			}

			if (CharacterTable.RobotNameExist(name)) {
				S_CharCreateStatus s_charcreatestatus1 = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_ALREADY_EXSISTS);
				client.sendPacket(s_charcreatestatus1, true);
				return;
			}

			if (CharacterTable.RobotCrownNameExist(name)) {
				S_CharCreateStatus s_charcreatestatus1 = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_ALREADY_EXSISTS);
				client.sendPacket(s_charcreatestatus1, true);
				return;
			}

			if (NpcShopSpawnTable.getInstance().getNpc(name)
					|| npcshopNameCk(name)) {
				S_CharCreateStatus s_charcreatestatus1 = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_ALREADY_EXSISTS);
				client.sendPacket(s_charcreatestatus1, true);
				return;
			}

			if (CharacterTable.somakname(name)) {
				S_CharCreateStatus s_charcreatestatus1 = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_ALREADY_EXSISTS);
				client.sendPacket(s_charcreatestatus1, true);
				return;
			}

			if (client.getAccount().countCharacters() >= 8) {
				_log.fine("account: " + client.getAccountName()
						+ " 8�� �Ѵ� ĳ���� �ۼ� �䱸. ");
				S_CharCreateStatus s_charcreatestatus1 = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_WRONG_AMOUNT);
				client.sendPacket(s_charcreatestatus1, true);
				return;
			}

			pc.setName(name);
			pc.setType(readC());
			pc.set_sex(readC());

			if (pc.get_sex() == 0)
				pc.setClassId(MALE_LIST[pc.getType()]);
			else
				pc.setClassId(FEMALE_LIST[pc.getType()]);

			pc.setHighLevel(1);
			str = (byte) readC();
			dex = (byte) readC();
			con = (byte) readC();
			wis = (byte) readC();
			cha = (byte) readC();
			intel = (byte) readC();
			total = str + dex + con + wis + cha + intel;

			pc.getAbility().setBaseStr(str);
			pc.getAbility().setBaseDex(dex);
			pc.getAbility().setBaseCon(con);
			pc.getAbility().setBaseWis(wis);
			pc.getAbility().setBaseCha(cha);
			pc.getAbility().setBaseInt(intel);

			if (!pc.getAbility().isNormalAbility(pc.getClassId(),
					pc.getLevel(), pc.getHighLevel(), total)) {
				_log.finest("Character have wrong value");
				S_CharCreateStatus s_charcreatestatus3 = new S_CharCreateStatus(
						S_CharCreateStatus.REASON_WRONG_AMOUNT);
				client.sendPacket(s_charcreatestatus3, true);
				return;
			}

			_log.fine("charname: " + pc.getName() + " classId: "
					+ pc.getClassId());
			S_CharCreateStatus s_charcreatestatus2 = new S_CharCreateStatus(
					S_CharCreateStatus.REASON_OK);
			client.sendPacket(s_charcreatestatus2, true);
			initNewChar(client, pc);
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	/*
	 * private static final int[] MALE_LIST = new int[] { 0, 61, 138, 734, 2786,
	 * 6658, 6671 }; private static final int[] FEMALE_LIST = new int[] { 1, 48,
	 * 37, 1186, 2796, 6661, 6650 }; private static final int[][] START_LOC_X =
	 * new int [][] {{ 32707, 32715, 32714, 32704, 32708 }, // ���� X { 32785,
	 * 32780, 32774, 32775, 32780 }};// �뼶 X private static final int[][]
	 * START_LOC_Y = new int [][] {{ 32880, 32870, 32877, 32876, 32870 }, // ����Y
	 * { 32783, 32790, 32789, 32783, 32781 }};// �뼶Y private static final
	 * short[] MAPID_LIST = new short[] { 68, 69, 69, 68, 69, 68, 69 };
	 */
	public static final int[] MALE_LIST = new int[] { 0, 61, 138, 734, 2786,
			6658, 6671, 12490 };
	public static final int[] FEMALE_LIST = new int[] { 1, 48, 37, 1186, 2796,
			6661, 6650, 12494 };

	// public static final int[][] START_LOC_X = new int [][] {{ 32683, 32686,
	// 32684, 32683, 32684 }, // ���� X
	// { 32683, 32686, 32684, 32683, 32684 }};// �뼶 X
	// public static final int[][] START_LOC_Y = new int [][] {{ 32853, 32856,
	// 32851, 32856, 32850 }, // ����Y
	// { 32853, 32856, 32851, 32856, 32850 }};// �뼶Y

	public static final short[] MAPID_LIST = new short[] { 7783, 7783, 7783,
		7783, 7783, 7783, 7783, 7783 };

	private static void initNewChar(LineageClient client, L1PcInstance pc)
			throws IOException, Exception {
		short init_hp = 0, init_mp = 0;
		Random random = new Random();

		pc.setId(ObjectIdFactory.getInstance().nextId());

		if (pc.isCrown()) { // CROWN
			init_hp = 14;
			switch (pc.getAbility().getBaseWis()) {
			case 11:
				init_mp = 2;
				break;
			case 12:
			case 13:
			case 14:
			case 15:
				init_mp = 3;
				break;
			case 16:
			case 17:
			case 18:
				init_mp = 4;
				break;
			default:
				init_mp = 2;
				break;
			}
		} else if (pc.isKnight()) { // KNIGHT
			init_hp = 16;
			switch (pc.getAbility().getBaseWis()) {
			case 9:
			case 10:
			case 11:
				init_mp = 1;
				break;
			case 12:
			case 13:
				init_mp = 2;
				break;
			default:
				init_mp = 1;
				break;
			}
		} else if (pc.isElf()) { // ELF
			init_hp = 15;
			switch (pc.getAbility().getBaseWis()) {
			case 12:
			case 13:
			case 14:
			case 15:
				init_mp = 4;
				break;
			case 16:
			case 17:
			case 18:
				init_mp = 6;
				break;
			default:
				init_mp = 4;
				break;
			}
		} else if (pc.isWizard()) { // WIZ
			init_hp = 12;
			switch (pc.getAbility().getBaseWis()) {
			case 12:
			case 13:
			case 14:
			case 15:
				init_mp = 6;
				break;
			case 16:
			case 17:
			case 18:
				init_mp = 8;
				break;
			default:
				init_mp = 6;
				break;
			}
		} else if (pc.isDarkelf()) { // DE
			init_hp = 12;
			switch (pc.getAbility().getBaseWis()) {
			case 10:
			case 11:
				init_mp = 3;
				break;
			case 12:
			case 13:
			case 14:
			case 15:
				init_mp = 4;
				break;
			case 16:
			case 17:
			case 18:
				init_mp = 6;
				break;
			default:
				init_mp = 3;
				break;
			}
		} else if (pc.isDragonknight()) { // ����
			init_hp = 16;
			init_mp = 2;
		} else if (pc.isIllusionist()) { // ȯ����
			init_hp = 14;
			switch (pc.getAbility().getBaseWis()) {
			case 12:
			case 13:
			case 14:
			case 15:
				init_mp = 5;
				break;
			case 16:
			case 17:
			case 18:
				init_mp = 6;
				break;
			default:
				init_mp = 5;
				break;
			}
		} else if (pc.isWarrior()) { // ����
			init_hp = 16;
			if (pc.getAbility().getBaseCon() >= 17) {
				init_hp += 1;
			}
			if (pc.getAbility().getBaseCon() >= 19) {
				init_hp += 2;
			}
			init_mp = 1;
		}
		int x = random.nextInt(3) + 32781;
		int y = random.nextInt(3) + 32822;
		int x1 = random.nextInt(3) + 32781;
		int y1 = random.nextInt(3) + 32822;
		int rnd = random.nextInt(2);
		if (rnd == 0) {
			pc.setX(x);
			pc.setY(y);
		} else {
			pc.setX(x1);
			pc.setY(y1);
		}

		pc.setMap(MAPID_LIST[pc.getType()]);

		pc.getMoveState().setHeading(0);
		pc.setLawful(0);
		pc.addBaseMaxHp(init_hp);
		pc.setCurrentHp(init_hp);
		pc.addBaseMaxMp(init_mp);
		pc.setCurrentMp(init_mp);
		pc.resetBaseAc();
		pc.setTitle("");
		pc.setClanid(0);
		pc.setClanRank(0);
		pc.set_food(39); // 17%
		pc.setAccessLevel((short) 0);
		pc.setGm(false);
		pc.setMonitor(false);
		pc.setGmInvis(false);
		pc.setExp(0);
		pc.setActionStatus(0);
		pc.setClanname("");
		pc.getAbility().setBonusAbility(0);
		pc.resetBaseMr();
		pc.setElfAttr(0);
		pc.set_PKcount(0);
		pc.setExpRes(0);
		pc.setPartnerId(0);
		pc.setOnlineStatus(0);
		pc.setHomeTownId(0);
		pc.setContribution(0);
		pc.setBanned(false);
		pc.setKarma(0);
		pc.setReturnStat(0);
		pc.calAinHasad(10001);
		/******* ���� ****************/
		Calendar local_c = Calendar.getInstance();
		SimpleDateFormat local_sdf = new SimpleDateFormat("yyyyMMdd");
		local_c.setTimeInMillis(System.currentTimeMillis());
		pc.setBirthDay(Integer.parseInt(local_sdf.format(local_c.getTime())));
		local_sdf = null;
		/**************************************************/
		// pc.setGdungeonTime(0);
		pc.setravatime(0);
		pc.setravaday(null);
		pc.setgirantime(0);
		pc.setgiranday(null);
		pc.setivorytime(0);
		pc.setivoryday(null);
		pc.setAnTime(null);
		pc.setpaTime(null);
		pc.setDETime(null);
		pc.setDETime2(null);

		if (pc.isWizard()) { // WIZ
			S_AddSkill as = new S_AddSkill(3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
					0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			pc.sendPackets(as, true);
			int object_id = pc.getId();
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(4); // EB
			String skill_name = l1skills.getName();
			int skill_id = l1skills.getSkillId();
			SkillsTable.getInstance().spellMastery(object_id, skill_id,
					skill_name, 0, 0); // DB�� ���
		}
		/*
		 * if(GameServer.�ű�����_����ġ���޴�){
		 * Beginner.getInstance().�ű԰���ġ���޴�GiveItem(pc); }else{
		 * Beginner.getInstance().GiveItem(pc); }
		 */
		if (GameServer.�ű�����_����ġ���޴�) {
			L1ItemInstance item = ItemTable.getInstance().createItem(60291);
			item.setCount(30);
			Beginner.getInstance().GiveItem(pc, item, false);
			item = ItemTable.getInstance().createItem(60293);
			item.setCount(15);
			Beginner.getInstance().GiveItem(pc, item, false);
			item = ItemTable.getInstance().createItem(40018);
			item.setCount(5);
			Beginner.getInstance().GiveItem(pc, item, false);
			int tt = 0;
			if (pc.isKnight() || pc.isWarrior())
				tt = 60134;
			else if (pc.isCrown())
				tt = 60133;
			else if (pc.isElf())
				tt = 60135;
			else if (pc.isIllusionist())
				tt = 60136;
			else if (pc.isDragonknight())
				tt = 60140;
			else if (pc.isDarkelf())
				tt = 60139;
			if (tt != 0) {
				item = ItemTable.getInstance().createItem(tt);
				item.setCount(10);
				Beginner.getInstance().GiveItem(pc, item, false);
			}
		}

		// L1ItemInstance box = ItemTable.getInstance().createItem(60380);
		// Beginner.getInstance().GiveItem(pc, box, false);

		if (pc.isCrown()) {
			int list[] = { 40391 };
			for (int i : list) {
				L1ItemInstance item = ItemTable.getInstance().createItem(i);
				Beginner.getInstance().GiveItem(pc, item, true);
			}
		} else if (pc.isKnight()) {
			int list[] = { 40391 };
			for (int i : list) {
				L1ItemInstance item = ItemTable.getInstance().createItem(i);
				Beginner.getInstance().GiveItem(pc, item, true);
			}
		} else if (pc.isWarrior()) {
			int list[] = { 40391 };
			for (int i : list) {
				L1ItemInstance item = ItemTable.getInstance().createItem(i);
				Beginner.getInstance().GiveItem(pc, item, true);
			}
		} else if (pc.isDarkelf()) {
			int list[] = { 40391 };
			for (int i : list) {
				L1ItemInstance item = ItemTable.getInstance().createItem(i);
				Beginner.getInstance().GiveItem(pc, item, true);
			}
		} else if (pc.isDragonknight()) {
			int list[] = { 40391 };
			for (int i : list) {
				L1ItemInstance item = ItemTable.getInstance().createItem(i);
				Beginner.getInstance().GiveItem(pc, item, true);
			}
		} else if (pc.isIllusionist()) {
			int list[] = { 40391 };
			for (int i : list) {
				L1ItemInstance item = ItemTable.getInstance().createItem(i);
				Beginner.getInstance().GiveItem(pc, item, true);
			}
		} else if (pc.isElf()) {
			int list[] = { 40391 };
			for (int i : list) {
				L1ItemInstance item = ItemTable.getInstance().createItem(i);
				Beginner.getInstance().GiveItem(pc, item, true);
			}

		} else if (pc.isWizard()) {
			int list[] = { 40391 };
			for (int i : list) {
				L1ItemInstance item = ItemTable.getInstance().createItem(i);
				Beginner.getInstance().GiveItem(pc, item, true);
			}

		}

		Beginner.getInstance().GiveItem(pc);

		// Beginner.getInstance().GiveItem(pc);

		if (Config.GAME_SERVER_TYPE == 0) {
			Beginner.getInstance().writeBookmark(pc);
		}
		pc.setAccountName(client.getAccountName());
		CharacterTable.getInstance().storeNewCharacter(pc);
		S_NewCharPacket s_newcharpacket = new S_NewCharPacket(pc);

		client.sendPacket(s_newcharpacket, true);
		pc.refresh();
		local_sdf = null;
	}

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
		ac = null;
		return flag;
	}

	private static boolean isInvalidName(String name, LineageClient client) {
		int numOfNameBytes = 0;
		try {
			numOfNameBytes = name.getBytes("EUC-KR").length;
		} catch (UnsupportedEncodingException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return false;
		}
		int i = Config.�̸�Ȯ��(name.toCharArray());

		if (i < 0) {
			return true;
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

	private boolean npcshopNameCk(String name) {
		return NpcTable.getInstance().findNpcShopName(name);
	}

	@Override
	public String getType() {
		return C_OPCODE_CREATE_NEW_CHARACTER;
	}
}
