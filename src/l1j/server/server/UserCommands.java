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

import static l1j.server.server.model.skill.L1SkillId.ADVANCE_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.BLESS_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;


import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.ItemTable;
//import l1j.server.GameSystem.Boss.BossSpawnTimeController;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_Ability;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_Message_YN;//������Ƽ
import l1j.server.server.serverpackets.S_NewUI;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_War;
import l1j.server.server.serverpackets.S_�����ֽ�;
import l1j.server.server.serverpackets.S_HPMeter;
import l1j.server.server.utils.SQLUtil;
import server.manager.eva;
import l1j.server.server.model.Instance.L1MonsterInstance; 
import l1j.server.server.model.Instance.L1PcInstance; 
import l1j.server.server.model.Instance.L1PetInstance; 
import l1j.server.server.model.Instance.L1SummonInstance;

public class UserCommands {

	private static UserCommands _instance;

	private UserCommands() {
	}

	public static UserCommands getInstance() {
		if (_instance == null) {
			_instance = new UserCommands();
		}
		return _instance;
	}

	public void handleCommands(L1PcInstance pc, String cmdLine) {
		StringTokenizer token = new StringTokenizer(cmdLine);
		if (!token.hasMoreTokens()) {
			return;
		}
		// ������ ��������� Ŀ�ǵ�, �� ���Ĵ� ������ �ܶ����� �� �Ķ���ͷμ� ����Ѵ�
		String cmd = token.nextToken();
		String param = "";
		while (token.hasMoreTokens()) {
			param = new StringBuilder(param).append(token.nextToken()).append(' ').toString();
		}
		param = param.trim();

		if (cmd.equalsIgnoreCase("����")) {
			help(pc);
		} else if (cmd.equalsIgnoreCase(".��")) {
			tell(pc);
		} else if (cmd.equalsIgnoreCase("�������") || cmd.equalsIgnoreCase("��ȣ����")) {
			changePassword(pc, param);
		} else if (cmd.equalsIgnoreCase("Ű����")) {
			quize(pc, param);
		} else if (cmd.equalsIgnoreCase("Ű�������")) {
			quize2(pc, param);
		} else if (cmd.equalsIgnoreCase("������û")) {
			phone(pc, param);
		
		 } else if (cmd.equalsIgnoreCase("�ǹ�")) { 
	            execute(pc, param, param); 

		} else if (cmd.equalsIgnoreCase("����")) {
			age(pc, param);
		} else if (cmd.equalsIgnoreCase(".")) {
			telrek(pc);
		} else if (cmd.equalsIgnoreCase("��ũ") || cmd.equalsIgnoreCase("����ũ")) {
			����ũ(pc, param);
		} else if (cmd.equalsIgnoreCase("�����Ʈ") || cmd.equalsIgnoreCase("��Ʈ")) {
			Ment(pc, param);
		} else if (cmd.equalsIgnoreCase("������Ƽ")) {
			ClanParty(pc);

		} else if (cmd.equalsIgnoreCase("����������û")) {
			Sealedoff(pc, param);
		} else if (cmd.equalsIgnoreCase("�����ֹ�����û")) {
			Sealedoff1(pc, param);
		} else if (cmd.equalsIgnoreCase("�������")) {
			autoroot(pc, cmd, param);
		} else if (cmd.equalsIgnoreCase("�����Ʈ")) {
			ment(pc, cmd, param);
		} else if (cmd.equalsIgnoreCase("����")) {
			maphack(pc, param);
		} else if (cmd.equalsIgnoreCase("����")) {
			buff(pc);
		} else if (cmd.equalsIgnoreCase("����1��")) {
			Hunt(pc, param);
		} else if (cmd.equalsIgnoreCase("����2��")) {
			Hunt2(pc, param);
		} else if (cmd.equalsIgnoreCase("����3��")) {
			Hunt3(pc, param);
		} else {
			S_ChatPacket s_chatpacket = new S_ChatPacket(pc, cmdLine, Opcodes.S_SAY, 0);
			if (!pc.getExcludingList().contains(pc.getName())) {
				pc.sendPackets(s_chatpacket);
			}
			for (L1PcInstance listner : L1World.getInstance().getRecognizePlayer(pc)) {
				if (!listner.getExcludingList().contains(pc.getName())) {
					listner.sendPackets(s_chatpacket);
				}
			}
			// ���� ó��
			L1MonsterInstance mob = null;
			for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
				if (obj instanceof L1MonsterInstance) {
					mob = (L1MonsterInstance) obj;
					if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
						Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, cmdLine, 0), true);
					}
				}
			}
			eva.LogCommandAppend("[����Ŀ�Ǵ�]", pc.getName(), cmdLine);
		}
	}

	private void help(L1PcInstance pc) {
		// TODO �ڵ� ������ �޼ҵ� ����
		pc.sendPackets(new S_SystemMessage("[����],[����],[�������],[������û],[����ũ]"), true);
		pc.sendPackets(new S_SystemMessage("[������Ƽ],[�������],[�����Ʈ],[����],[..(�ڷ�Ǯ��)"), true);
		pc.sendPackets(new S_SystemMessage("[[Ű����],[Ű�������],[����������û],[�����ֹ�����û]"), true);
		pc.sendPackets(new S_SystemMessage("[����1��],[����2��],[����3��]"), true);
	}

	/** ���� ��Ƽ ��û ��ɾ� **/
	public void ClanParty(L1PcInstance pc) {
		int ClanId = pc.getClanid();
		if (ClanId != 0 && (pc.getClanRank() == L1Clan.CLAN_RANK_GUARDIAN || pc.isCrown())) { // Clan[O]
																								// [����,��ȣ���]
			for (L1PcInstance SearchBlood : L1World.getInstance().getAllPlayers()) {
				if (SearchBlood.getClanid() != ClanId || SearchBlood.isPrivateShop() || SearchBlood.isInParty()) {
					continue;
				} else if (SearchBlood.getName() != pc.getName()) {
					pc.setPartyType(1); // ��ƼŸ�� ����
					SearchBlood.setPartyID(pc.getId()); // ��Ƽ���̵� ����
					SearchBlood.sendPackets(new S_Message_YN(954, pc.getName())); // ������Ƽ
																					// ��û
					pc.sendPackets(new S_SystemMessage("����� [" + SearchBlood.getName() + "]���� ��Ƽ�� ��û�߽��ϴ�."));
				}
			}
		} else { // Ŭ���� ���ų� ���� �Ǵ� ��ȣ��� [X]
			pc.sendPackets(new S_SystemMessage("������ ����, ��ȣ��縸 ����Ҽ� �ֽ��ϴ�."));
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

	private void tell(L1PcInstance pc) {
		try {
			if (pc.isPinkName()) {
				pc.sendPackets(new S_SystemMessage("�������̶� ����� �� �����ϴ�."));
				return;
			}
			if (pc.isFishing() || pc.isFishingReady() || pc.isPrivateShop()) {
				return;
			}
			long curtime = System.currentTimeMillis() / 1000;
			if (pc.getQuizTime() + 20 > curtime) {
				pc.sendPackets(new S_SystemMessage("20�ʰ��� �����ð��� �ʿ��մϴ�."));
				return;
			}
			L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
			pc.sendPackets(new S_SystemMessage("�ֺ� ������Ʈ�� ��ε� �Ͽ����ϴ�."));
			pc.setQuizTime(curtime);
		} catch (Exception exception35) {
		}
	}

	private void Ment(L1PcInstance pc, String param) {
		if (param.equalsIgnoreCase("��")) {
			pc.sendPackets(new S_SystemMessage(pc, "������ ȹ�� ��Ʈ - OFF -"));
			pc.RootMent = false;
		} else if (param.equalsIgnoreCase("��")) {
			pc.sendPackets(new S_SystemMessage(pc, "������ ȹ�� ��Ʈ - ON -"));
			pc.RootMent = true;
		} else {
			pc.sendPackets(new S_SystemMessage(pc, ".�����Ʈ [��/��]�� �Է� (������ ȹ�� ��Ʈ ����)"));
		}
	}

	public void autoroot(L1PcInstance pc, String cmd, String param) {
		if (param.equalsIgnoreCase("��")) { // by��� ������� �Բ� ��ɾ�
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_AUTOROOT, 0);
			pc.sendPackets(new S_SystemMessage("\\fY��������� �����մϴ�. "));

		} else if (param.equalsIgnoreCase("��")) { // by��� ������� �Բ� ��ɾ�
			pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_AUTOROOT);
			pc.sendPackets(new S_SystemMessage("\\fY��������� Ȱ��ȭ�մϴ�. "));

		} else { // by��� ������� �Բ� ��ɾ�
			pc.sendPackets(new S_SystemMessage(cmd + " [��,��] ��� �Է��� �ּ���. "));
		}
	}

	public void ment(L1PcInstance pc, String cmd, String param) { // by��� ��Ʈ
		if (param.equalsIgnoreCase("��")) {
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_MENT, 0);
			pc.sendPackets(new S_SystemMessage("\\fY������� ��Ʈ�� ���ϴ�."));
		} else if (param.equalsIgnoreCase("��")) {
			pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_MENT);
			pc.sendPackets(new S_SystemMessage("\\fY������� ��Ʈ�� �մϴ�."));

		} else {
			pc.sendPackets(new S_SystemMessage(cmd + " [��,��] ��� �Է��� �ּ���. "));
		}
	}

	private void ����ũ(L1PcInstance pc, String param) {
		long curtime = System.currentTimeMillis() / 1000;
		if (pc.getQuizTime() + 10 > curtime) {
			long time = (pc.getQuizTime() + 10) - curtime;
			pc.sendPackets(new S_SystemMessage("" + time + " �� �� ����� �� �ֽ��ϴ�."));

			return;
		}
		if (pc.isDead()) {
			pc.sendPackets(new S_SystemMessage("���� ���¿��� ����� �� �����ϴ�."));
			return;
		}
		int i = 1;
		if (pc.�����ֽ�) {
			i = 3;
			pc.�����ֽ� = false;
			pc.sendPackets(new S_�����ֽ�(pc.getClan(), i), false);
			pc.sendPackets(new S_ClanWindow(S_ClanWindow.����ũ����, pc.getClan().getmarkon()), false);
			pc.sendPackets(new S_NewUI(0x19, pc.getClan().getClanName(), pc), false);
		} else
			pc.�����ֽ� = true;
		for (L1Clan clan : L1World.getInstance().getAllClans()) {
			if (clan != null) {
				pc.sendPackets(new S_War(i, pc.getClanname(), clan.getClanName()));
				pc.sendPackets(new S_�����ֽ�(pc.getClan(), i), true);
				pc.sendPackets(new S_ClanWindow(S_ClanWindow.����ũ����, pc.getClan().getmarkon()), true);
				pc.sendPackets(new S_NewUI(0x19, pc.getClan().getClanName(), pc), true);
			}
		}
		pc.setQuizTime(curtime);
	}

	public void killment(L1PcInstance pc, String cmd, String param) { // by��� ��Ʈ
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

	public static String checkPassword(String accountName) {
		String _inputPwd = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			// statement =
			// con.prepareStatement("select account_name from characters where
			// char_name Like '"
			// + pc.getName() + "'");
			pstm = con.prepareStatement("select password from accounts where login = ?");
			pstm.setString(1, accountName);
			rs = pstm.executeQuery();
			if (rs.next()) {
				_inputPwd = rs.getString("password");
			}
			return _inputPwd;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return _inputPwd;
	}

	public static boolean checkPassword(String accountName, String _pwd, String rawPassword) {
		String _inputPwd = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT password(?) as pwd ");

			pstm.setString(1, rawPassword);
			rs = pstm.executeQuery();
			if (rs.next()) {
				_inputPwd = rs.getString("pwd");
			}
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
			if (_pwd.equals(_inputPwd)) { // �����ϴٸ�
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	private void to_Change_Passwd(L1PcInstance pc, String passwd) {
		PreparedStatement statement = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		java.sql.Connection con = null;
		try {
			String login = null;
			String password = null;
			con = L1DatabaseFactory.getInstance().getConnection();
			password = passwd;
			statement = con.prepareStatement(
					"select account_name from characters where char_name Like '" + pc.getName() + "'");
			rs = statement.executeQuery();

			while (rs.next()) {
				login = rs.getString(1);
				pstm = con.prepareStatement("UPDATE accounts SET password=? WHERE login Like '" + login + "'");
				pstm.setString(1, password);
				pstm.execute();
				pc.sendPackets(new S_ChatPacket(pc, "����������� :(" + passwd + ")�� ������ �Ϸ�Ǿ����ϴ�.", Opcodes.S_SAY, 2));
				pc.sendPackets(new S_SystemMessage("��� ������ ���������� �Ϸ�Ǿ����ϴ�."));
			}
			login = null;
			password = null;
		} catch (Exception e) {
			System.out.println("to_Change_Passwd() Error : " + e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(statement);
			SQLUtil.close(con);
		}
	}

	private void changePassword(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			String passwd = tok.nextToken();
			Account account = Account.load(pc.getAccountName()); // �߰�
			if (account.getquize() != null) {
				pc.sendPackets(new S_SystemMessage("Ű���带 �������� ������ ������ �� �����ϴ�."));
				pc.sendPackets(new S_SystemMessage("��) .Ű�������"));
				return;
			} // ��ȣ����� ��� �����Ǿ� ���� �ʴٸ� �ٲ� �� ������.
			if (passwd.length() < 4) {
				pc.sendPackets(new S_SystemMessage("�Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ª���ϴ�."));
				pc.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}

			if (passwd.length() > 12) {
				pc.sendPackets(new S_SystemMessage("�Է��Ͻ� ��ȣ�� �ڸ����� �ʹ� ��ϴ�."));
				pc.sendPackets(new S_SystemMessage("�ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."));
				return;
			}

			if (isDisitAlpha(passwd) == false) {
				pc.sendPackets(new S_SystemMessage("����� ������ �ʴ� ���ڰ� ���� �Ǿ� �ֽ��ϴ�."));
				return;
			}

			to_Change_Passwd(pc, passwd);
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".������� [������ ��ȣ]�� �Է� ���ּ���."));
		}
	}
	
	  public void execute(L1PcInstance pc, String cmdName, String arg) { 
		  if (arg.equalsIgnoreCase("��")) { 
		  pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.GMSTATUS_HPBAR, 0); 
		  } else if (arg.equalsIgnoreCase("��")) { 
		  pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.GMSTATUS_HPBAR); 

		  for (L1Object obj : pc.getNearObjects().getKnownObjects()) { 
		  if (isHpBarTarget(obj)) { 
		  pc.sendPackets(new S_HPMeter(obj.getId(), 0xFF, 0)); 
		  } 
		  } 
		  } else { 
		  pc.sendPackets(new S_SystemMessage(cmdName + " [��,��] ��� �Է��� �ּ���. ")); 
		  } 
		  } 
		      
		      public static boolean isHpBarTarget(L1Object obj) { 
		  if (obj instanceof L1MonsterInstance) { 
		  return true; 
		  } 
		  if (obj instanceof L1PcInstance) { 
		  return true; 
		  } 
		  if (obj instanceof L1SummonInstance) { 
		  return true; 
		  } 
		  if (obj instanceof L1PetInstance) { 
		  return true; 
		  } 
		  return false; 
		  } 

	private void phone(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			// String user = tok.nextToken();
			String name = tok.nextToken();
			String phone = tok.nextToken();

			if (name.length() > 12 || phone.length() > 12) {
				pc.sendPackets(new S_SystemMessage("�߸��� ���� �Դϴ�."));
				return;
			}

			phonenumber(name, phone, pc.getName());
			pc.sendPackets(new S_SystemMessage("\\fT(" + name + ") �� " + phone + " ��ȣ�� ���� ��û �Ǽ̽��ϴ�. �����մϴ�."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".������û [���� ����ȣ] �� �Է����ּ���.[]���ڴ� ����"));
			pc.sendPackets(new S_SystemMessage("EX).������û ȫ�浿 0000000000 (��ȣ�� �����̽��� ���� �ȵ˴ϴ�.)"));
		}
	}
	
	

	private void telrek(L1PcInstance pc) {
		try {
			int castle_id = L1CastleLocation.getCastleIdByArea(pc);
			// /////////////////////// Ÿ��/////////////////////////////////
			long curtime = System.currentTimeMillis() / 1000;
			if (pc.getQuizTime() + 20 > curtime) {
				pc.sendPackets(new S_SystemMessage("20�ʰ��� �����ð��� �ʿ��մϴ�."));
				return;
			}
			// /////////////////////// Ÿ��/////////////////////////////////
			if (pc.getMapId() == 5302 || pc.getMapId() == 5490) {
				pc.sendPackets(new S_SystemMessage("�̰������� ����� �� �����ϴ�."));
				return;
			}
			if (CharPosUtil.getZoneType(pc) == 0 && castle_id != 0) {
				pc.sendPackets(new S_SystemMessage("���ֺ������� ��� �� �� �����ϴ�."));
				return;
			}
			if (pc.getMapId() == 350 || pc.getMapId() == 5153) {
				pc.sendPackets(new S_SystemMessage("�̰������� ����� �� �����ϴ�."));
				return;
			}
			if (pc.isPinkName() || pc.isParalyzed() || pc.isSleeped()) {
				pc.sendPackets(new S_SystemMessage("������ ������ ����߿��� ����� �� �����ϴ�."));
				return;
			}
			if (pc.isDead()) {
				pc.sendPackets(new S_SystemMessage("���� ���¿��� ����� �� �����ϴ�."));
				return;
			}
			if (!(pc.getInventory().checkItem(40308, 1000))) {
				pc.sendPackets(new S_SystemMessage("1000 �Ƶ����� �����մϴ�."));
				return;
			}
			pc.getInventory().consumeItem(40308, 1000);

			L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
			pc.sendPackets(new S_SystemMessage("1000 �Ƶ����� �Ҹ� �Ǿ����ϴ�."));
			// pc.sendPackets(new S_SystemMessage("�ֺ� ������Ʈ�� ��ε� �Ͽ����ϴ�."));
			pc.setQuizTime(curtime);
			// } catch (Exception e) {
		} catch (Exception exception35) {
		}
	}

	private void age(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String AGE = tok.nextToken();
			int AGEint = Integer.parseInt(AGE);
			if (AGEint > 60 || AGEint < 12) {
				pc.sendPackets(new S_SystemMessage(pc, "�ڽ��� ���� ���̷� �����ϼ���."));
				return;
			}
			pc.setAge(AGEint);
			pc.save();
			pc.sendPackets(new S_SystemMessage(pc, "���̰� " + AGEint + "���� �����Ǿ����ϴ�. ���� ä�ý� ��Ÿ���ϴ�."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(pc, ".���� ���� �������� �Է�.(���� ä�� �� ǥ�õ�)"));
		}
	}

	private void Hunt(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer st = new StringTokenizer(cmd);
			String char_name = st.nextToken();
			// int price = Integer.parseInt(st.nextToken());
			String story = st.nextToken();

			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(char_name);
			if (target != null) {
				if (target.isGm()) {
					return;
				}
				// if (char_name.equals(pc.getName())) {
				// pc.sendPackets(new S_SystemMessage("�ڽſ��� ������� �ɼ� �����ϴ�."));
				// return;
				// }
				if (target.getHuntCount() == 1) {
					pc.sendPackets(new S_SystemMessage("�̹� ���� �Ǿ��ֽ��ϴ�"));
					return;
				}
				if (target.getHuntCount() == 2) {
					pc.sendPackets(new S_SystemMessage("�̹� ���� �Ǿ��ֽ��ϴ�"));
					return;
				}
				if (target.getHuntCount() == 3) {
					pc.sendPackets(new S_SystemMessage("�̹� ���� �Ǿ��ֽ��ϴ�"));
					return;
				}

				if (!(pc.getInventory().checkItem(40308, 1000000))) {
					pc.sendPackets(new S_SystemMessage("�Ƶ����� �����մϴ�.[�ʿ����:100�� �Ƶ���]"));
					return;
				}
				if (story.length() > 20) {
					pc.sendPackets(new S_SystemMessage("������ ª�� 20���ڷ� �Է��ϼ���"));
					return;
				}

				target.setHuntCount(1);
				target.setHuntPrice(1000000);
				target.setReasonToHunt(story);
				target.save();
				L1World.getInstance().broadcastServerMessage("\\aD[" + target.getName() + "]�� �� ������� �ɷȽ��ϴ�.");
				L1World.getInstance()
						.broadcastPacketToAll(new S_SystemMessage("\\aD[ ������ ]:  " + target.getName() + "  ]"));
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\aD[ ���� ]: " + story + "  "));
				pc.getInventory().consumeItem(40308, 1000000);
				// huntoption(pc);
				L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
				pc.initBeWanted();
				int[] beWanted = { 1, 1, 1, 1, 1, 1 };
				pc.setBeWanted(beWanted);
				pc.addBeWanted();
			} else {
				pc.sendPackets(new S_SystemMessage("���������� �ʽ��ϴ�."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".����1�� [ĳ���͸�] [����]"));
			pc.sendPackets(new S_SystemMessage("====== ���� ȿ�� ���� ======"));
			pc.sendPackets(new S_SystemMessage("=��Ÿ+1 / ����+1 / SP+1="));

		}
	}

	private void Hunt2(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer st = new StringTokenizer(cmd);
			String char_name = st.nextToken();
			// int price = Integer.parseInt(st.nextToken());
			String story = st.nextToken();

			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(char_name);
			if (target != null) {
				if (target.isGm()) {
					return;
				}
				// if (char_name.equals(pc.getName())) {
				// pc.sendPackets(new S_SystemMessage("�ڽſ��� ������� �ɼ� �����ϴ�."));
				// return;
				// }

				if (target.getHuntCount() == 2) {
					pc.sendPackets(new S_SystemMessage("�̹� ���� �Ǿ��ֽ��ϴ�"));
					return;
				}
				if (target.getHuntCount() == 3) {
					pc.sendPackets(new S_SystemMessage("�̹� ���� �Ǿ��ֽ��ϴ�"));
					return;
				}

				if (!(pc.getInventory().checkItem(40308, 5000000))) {
					pc.sendPackets(new S_SystemMessage("�Ƶ����� �����մϴ�.[�ʿ����:500�� �Ƶ���]"));
					return;
				}
				if (story.length() > 20) {
					pc.sendPackets(new S_SystemMessage("������ ª�� 20���ڷ� �Է��ϼ���"));
					return;
				}

				target.setHuntCount(2);
				target.setHuntPrice(5000000);
				target.setReasonToHunt(story);
				target.save();
				L1World.getInstance().broadcastServerMessage("\\aD[" + target.getName() + "]�� �� ������� �ɷȽ��ϴ�.");
				L1World.getInstance()
						.broadcastPacketToAll(new S_SystemMessage("\\aD[ ������ ]:  " + target.getName() + "  ]"));
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\aD[ ���� ]: " + story + "  "));
				pc.getInventory().consumeItem(40308, 5000000);
				// huntoption(pc);
				L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
				pc.initBeWanted();
				int[] beWanted = { 2, 2, 2, 2, 2, 2 };
				pc.setBeWanted(beWanted);
				pc.addBeWanted();
			} else {
				pc.sendPackets(new S_SystemMessage("���������� �ʽ��ϴ�."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".����2�� [ĳ���͸�] [����]"));
			pc.sendPackets(new S_SystemMessage("====== ���� ȿ�� ���� ======"));
			pc.sendPackets(new S_SystemMessage("=��Ÿ+2 / ����+2 / SP+2="));

		}
	}

	private void Hunt3(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer st = new StringTokenizer(cmd);
			String char_name = st.nextToken();
			// int price = Integer.parseInt(st.nextToken());
			String story = st.nextToken();

			L1PcInstance target = null;
			target = L1World.getInstance().getPlayer(char_name);
			if (target != null) {
				if (target.isGm()) {
					return;
				}
				// if (char_name.equals(pc.getName())) {
				// pc.sendPackets(new S_SystemMessage("�ڽſ��� ������� �ɼ� �����ϴ�."));
				// return;
				// }

				if (target.getHuntCount() == 3) {
					pc.sendPackets(new S_SystemMessage("�̹� ���� �Ǿ��ֽ��ϴ�"));
					return;
				}

				if (!(pc.getInventory().checkItem(40308, 10000000))) {
					pc.sendPackets(new S_SystemMessage("�Ƶ����� �����մϴ�.[�ʿ����:1000�� �Ƶ���]"));
					return;
				}
				if (story.length() > 20) {
					pc.sendPackets(new S_SystemMessage("������ ª�� 20���ڷ� �Է��ϼ���"));
					return;
				}

				target.setHuntCount(3);
				target.setHuntPrice(10000000);
				target.setReasonToHunt(story);
				target.save();
				L1World.getInstance().broadcastServerMessage("\\aD[" + target.getName() + "]�� �� ������� �ɷȽ��ϴ�.");
				L1World.getInstance()
						.broadcastPacketToAll(new S_SystemMessage("\\aD[ ������ ]:  " + target.getName() + "  ]"));
				L1World.getInstance().broadcastPacketToAll(new S_SystemMessage("\\aD[ ���� ]: " + story + "  "));
				pc.getInventory().consumeItem(40308, 10000000);
				// huntoption(pc);
				L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
				pc.initBeWanted();
				int[] beWanted = { 3, 3, 3, 3, 3, 3 };
				pc.setBeWanted(beWanted);
				pc.addBeWanted();
			} else {
				pc.sendPackets(new S_SystemMessage("���������� �ʽ��ϴ�."));
			}
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".����3�� [ĳ���͸�] [����]"));
			pc.sendPackets(new S_SystemMessage("====== ���� ȿ�� ���� ======"));
			pc.sendPackets(new S_SystemMessage("=��Ÿ+3 / ����+3 / SP+3="));

		}
	}

	private void quize(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			// String user = tok.nextToken();
			String quize = tok.nextToken();
			Account account = Account.load(pc.getAccountName());

			if (quize.length() < 6) {
				pc.sendPackets(new S_SystemMessage("�ּ� 6�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}

			if (quize.length() > 12) {
				pc.sendPackets(new S_SystemMessage("�ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."));
				return;
			}
			if (isDisitAlpha(quize) == false) {
				pc.sendPackets(new S_SystemMessage("Ű���忡 ������ �ʴ� ���ڰ� ���ԵǾ����ϴ�."));
				return;
			}

			if (account.getquize() != null) {
				pc.sendPackets(new S_SystemMessage("�̹� Ű���尡 �����Ǿ� �ֽ��ϴ�."));
				return;
			}
			account.setquize(quize);
			Account.updateQuize(account);
			pc.sendPackets(new S_SystemMessage("Ű���尡 [" + quize + "]���� �ԷµǾ����ϴ�. Ű����� �ٽ� Ȯ�ΰ� ������ �Ұ����ϴ� �����Ͻñ� �ٶ��ϴ�."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".Ű���� �����Ͻ�Ű���带 �Է����ּ���.\n�ּ� 6�� �̻� �Է��Ͻñ� �ٶ��ϴ�."));
		}
	}

	private void quize2(L1PcInstance pc, String cmd) {
		try {
			StringTokenizer tok = new StringTokenizer(cmd);
			String quize2 = tok.nextToken();
			Account account = Account.load(pc.getAccountName());

			if (quize2.length() < 4) {
				pc.sendPackets(new S_SystemMessage("�Է��Ͻ� Ű������ �ڸ����� �ʹ� ª���ϴ�."));
				pc.sendPackets(new S_SystemMessage("�ּ� 4�� �̻� �Է��� �ֽʽÿ�."));
				return;
			}

			if (quize2.length() > 12) {
				pc.sendPackets(new S_SystemMessage("�Է��Ͻ� Ű������ �ڸ����� �ʹ� ��ϴ�."));
				pc.sendPackets(new S_SystemMessage("�ִ� 12�� ���Ϸ� �Է��� �ֽʽÿ�."));
				return;
			}

			if (account.getquize() == null || account.getquize() == "") {
				pc.sendPackets(new S_SystemMessage("Ű���尡 �����Ǿ� ���� �ʽ��ϴ�."));
				return;
			}

			if (!quize2.equals(account.getquize())) {
				pc.sendPackets(new S_SystemMessage("������ Ű����� ��ġ���� �ʽ��ϴ�."));
				return;
			}

			if (isDisitAlpha(quize2) == false) {
				pc.sendPackets(new S_SystemMessage("Ű���忡 ������ �ʴ� ���ڰ� ���ԵǾ����ϴ�."));
				return;
			}
			account.setquize(null);
			Account.updateQuize(account);
			pc.sendPackets(new S_SystemMessage("Ű���尡 �����Ǿ����ϴ�."));
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage("��� ��).Ű������� ��ȣ(Ű����)"));
		}
	}

	private void Sealedoff(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			int sealCount = Integer.parseInt(tok.nextToken(), 10);
			Account account = Account.load(pc.getAccountName());
			if (account.getquize() != null) {
				pc.sendPackets(new S_SystemMessage(".Ű������� �� �ϽŴ��� ��û�Ͻñ�ٶ��ϴ�."));
				return;
			} // ���������� ��� �����Ǿ� ���� �ʴٸ� ��û�Ұ��� �ϵ���
			if (sealCount > 15) {
				pc.sendPackets(new S_SystemMessage("���������ֹ��� 15�� �̻� ��û �� �� �����ϴ�."));
				return;
			}
			if (pc.getInventory().checkItem(50021, 1)) {// �����ֹ���
				pc.sendPackets(new S_SystemMessage("���������ֹ����� ��δ� ����� ��û�ϼ���."));
				return;
			}
			createNewItem(pc, 50021, sealCount);// �����ֹ���
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".����������û [��û�� ���] �� �Է��ϼ���."));
		}
	}

	private void Sealedoff1(L1PcInstance pc, String param) {
		try {
			StringTokenizer tok = new StringTokenizer(param);
			int sealCount = Integer.parseInt(tok.nextToken(), 10);
			Account account = Account.load(pc.getAccountName());
			if (account.getquize() != null) {
				pc.sendPackets(new S_SystemMessage(".Ű������� �� �ϽŴ��� ��û�Ͻñ�ٶ��ϴ�."));
				return;
			} // ���������� ��� �����Ǿ� ���� �ʴٸ� ��û�Ұ��� �ϵ���
			if (sealCount > 15) {
				pc.sendPackets(new S_SystemMessage("�����ֹ����� 15�� �̻� ��û �� �� �����ϴ�."));
				return;
			}
			if (pc.getInventory().checkItem(50020, 1)) {// �����ֹ���
				pc.sendPackets(new S_SystemMessage("�����ֹ����� ��δ� ����� ��û�ϼ���."));
				return;
			}
			createNewItem(pc, 50020, sealCount);// �����ֹ���
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".�����ֹ�����û [��û�� ���] �� �Է��ϼ���."));
		}
	}

	private boolean createNewItem(L1PcInstance pc, int item_id, int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item == null) {
			return false;
		}
		item.setCount(count);

		if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
			pc.getInventory().storeItem(item);
		} else { // ���� �� ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
			L1World.getInstance().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
		}
		pc.sendPackets(new S_ServerMessage(403, item.getLogName())); 
		return true;

	}

	public void phonenumber(String name, String phone, String chaname) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO UserPhone SET name=?,pnumber=?, chaname=?");
			pstm.setString(1, name);
			pstm.setString(2, phone);
			pstm.setString(3, chaname);
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static final int[] allBuffSkill = { L1SkillId.EARTH_SKIN, PHYSICAL_ENCHANT_DEX, PHYSICAL_ENCHANT_STR,
			BLESS_WEAPON, ADVANCE_SPIRIT };

	private void buff(L1PcInstance pc) {
		if (pc.isDead())
			return;
		long curtime = System.currentTimeMillis() / 1000;
		if (pc.getQuizTime() + 20 > curtime) {
			pc.sendPackets(new S_SystemMessage("20�ʰ��� �����ð��� �ʿ��մϴ�."));
			return;
		}
		if (pc.getLevel() <= 59) {
			try {
				L1SkillUse l1skilluse = new L1SkillUse();
				for (int i = 0; i < allBuffSkill.length; i++) {
					l1skilluse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), null, 0,
							L1SkillUse.TYPE_GMBUFF);
					pc.setQuizTime(curtime);
				}
			} catch (Exception e) {
			}
		} else {
			pc.sendPackets(new S_SystemMessage("59���� ���Ĵ� ������ ������ �����ϴ�."));
		}
	}
}
