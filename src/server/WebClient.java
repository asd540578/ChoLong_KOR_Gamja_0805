package server;

import java.sql.Timestamp;
import java.util.Random;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.clientpackets.C_Attr;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_CharTitle;
import l1j.server.server.serverpackets.S_ClanJoinLeaveStatus;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_�����ֽ�;

public class WebClient {
	private int _off = 1;
	private int _off2 = 2;

	public String printData(byte[] data, int len) {
		StringBuffer result = new StringBuffer();
		int counter = 0;
		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				result.append(fillHex(i, 4) + ": ");
			}
			result.append(fillHex(data[i] & 0xff, 2) + " ");
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

	private String fillHex(int data, int digits) {
		String number = Integer.toHexString(data);

		for (int i = number.length(); i < digits; i++) {
			number = "0" + number;
		}
		return number;
	}

	public WebClient(byte[] abyte0) {
		int op = 0;
		op = abyte0[0] & 0xff;
		String name1;
		String name2;
		String c_pcname;
		String c_clanname;

		try {
			switch (op) {
			/*
			 * �ü� â���� ��ư Ŭ���� 1byte(opcode)0x00 ���� �ü��˻��� ��Ÿ������ ��ư Ŭ�� (data1) Ŭ����
			 * �����̸�(string) ���� 0x00���� (data2) Ŭ���� ��ư�� �Ǹ��� �̸�(string) ���� 0x00����
			 */
			case ����ã���ư:
				name1 = readS(abyte0);
				name2 = readS(abyte0);

				// System.out.println("[��Ŷ1]\r\n"+printData(name1.getBytes(),
				// name1.getBytes().length));
				// System.out.println("[��Ŷ2]\r\n"+printData(name2.getBytes(),
				// name2.getBytes().length));
				// System.out.println(name1.getBytes() + " 123 :"+name2);
				L1PcInstance pc = L1World.getInstance().getPlayer(name1);
				L1PcInstance targetpc = L1World.getInstance().getPlayer(name2);
				L1NpcShopInstance targetNpc = L1World.getInstance().getNpcShop(name2);
				if (pc == null)
					return;
				if (pc.isPrivateShop() || pc.isTeleport())
					return;
				if (pc.getMapId() != 800)
					return;
				if (targetpc == null && targetNpc == null) {
					pc.sendPackets(new S_SystemMessage("���λ����� ã���� �����ϴ�. ����� �ٽ� �̿� �ٶ��ϴ�."));
					return;
				}
				Random rnd = new Random(System.nanoTime());
				if (targetpc != null) {
					if (targetpc.getMapId() != 800)
						return;
					pc.dx = targetpc.getX() + rnd.nextInt(3) - 1;
					pc.dy = targetpc.getY() + rnd.nextInt(3) - 1;
					pc.dm = (short) targetpc.getMapId();
					pc.dh = calcheading(pc.dx, pc.dy, targetpc.getX(), targetpc.getY());
					pc.����ã��Objid = targetpc.getId();
					pc.setTelType(7);
					pc.sendPackets(new S_SabuTell(pc), true);
				} else if (targetNpc != null) {
					pc.dx = targetNpc.getX() + rnd.nextInt(3) - 1;
					pc.dy = targetNpc.getY() + rnd.nextInt(3) - 1;
					pc.dm = (short) targetNpc.getMapId();
					pc.dh = calcheading(pc.dx, pc.dy, targetNpc.getX(), targetNpc.getY());
					pc.����ã��Objid = targetNpc.getId();
					pc.setTelType(7);
					pc.sendPackets(new S_SabuTell(pc), true);
				}
				break;
			case ���Ͱ��Խ�û��ư:
				int type = 0;
				type = abyte0[1] & 0xff;
				c_pcname = readSS(abyte0);
				c_clanname = readSS(abyte0);

				L1PcInstance _pc = L1World.getInstance().getPlayer(c_pcname);
				if (_pc == null)
					return;// 123
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���Խ�û������)) {
					int time = _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(L1SkillId.���Խ�û������);
					_pc.sendPackets(new S_SystemMessage("(" + time + ") ���� �ٽ� Ŭ�����ּ���."));
					return;
				}

				L1Clan clan = L1World.getInstance().getClan(c_clanname);
				
				if (clan == null) {
					_pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 4), true);
					return;
				}

				L1PcInstance _target = clan.getonline����();
				
				if (_target == null) {
					_pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 11), true);
					return;
				}

				if (type == 0) {// ���Խ�û
					������(_pc, _target, clan);
				} else if (type == 1) {// ��û���

				}
				break;
			default:
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ������(L1PcInstance player, L1PcInstance target, L1Clan clan) {
		if (player.getClanid() != 0) { // �̹� ũ���� ������ ���� ����
			if (player.isCrown()) {
				player.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 13), true);
				return;
			} else {
				player.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 9), true);
				return;
			}
		}

		if (player.isCrown()) {
			player.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 13), true);
			return;
		}

		if (clan.getJoinSetting() == 0) {
			player.sendPackets(new S_SystemMessage("[" + clan.getClanName() + "] ������\n���� ������ �� �����ϴ�."), true);
			// player.sendPackets(new
			// S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 8),true);
			return;
		} else if (clan.getJoinType() == 0) {// ��ð���
			C_Attr.���Ͱ���(target, player, clan);
			player.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 0), true);
			return;
		} else {// ���ΰ���
			target.setTempID(player.getId()); // ����� ������Ʈ ID�� ������ �д�
			player.getSkillEffectTimerSet().setSkillEffect(L1SkillId.���Խ�û������, 30000);
			// player.sendPackets(new
			// S_SystemMessage("["+clan.getClanName()+"] ���Ϳ� ���Խ�û�� �Ͽ����ϴ�."));
			player.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 1), true);
			if (target instanceof L1RobotInstance) {
				Random _rnd = new Random(System.nanoTime());
				GeneralThreadPool.getInstance().schedule(new join(target, player), 1000 + _rnd.nextInt(2000));
			} else {
				S_Message_YN myn = new S_Message_YN(97, player.getName());
				target.sendPackets(myn, true);
			}
		}

	}

	class join implements Runnable {

		private L1PcInstance crown;
		private L1PcInstance joinchar;

		public join(L1PcInstance _crown, L1PcInstance _joinchar) {
			crown = _crown;
			joinchar = _joinchar;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			try {
				if (crown == null || joinchar == null || L1World.getInstance().getPlayer(crown.getName()) == null
						|| L1World.getInstance().getPlayer(joinchar.getName()) == null) {
					return;
				}
				clanJoin(crown, joinchar);
				if (((L1RobotInstance) crown)._userTitle == null
						|| ((L1RobotInstance) crown)._userTitle.equalsIgnoreCase(""))
					return;
				Random _rnd = new Random(System.nanoTime());
				Thread.sleep(3000 + _rnd.nextInt(2000));

				if (L1World.getInstance().getPlayer(crown.getName()) == null
						|| L1World.getInstance().getPlayer(joinchar.getName()) == null)
					return;

				joinchar.setTitle(((L1RobotInstance) crown)._userTitle);
				S_CharTitle ct = new S_CharTitle(joinchar.getId(), joinchar.getTitle());
				joinchar.sendPackets(ct);
				Broadcaster.broadcastPacket(joinchar, ct, true);
				try {
					if (!(joinchar instanceof L1RobotInstance))
						joinchar.save(); // DB�� ĳ���� ������ �� ��
				} catch (Exception e) {
				}

				L1Clan clan = L1World.getInstance().getClan(crown.getClanname());
				if (clan != null) {
					for (L1PcInstance clanPc : clan.getOnlineClanMember()) {
						// \f1%0��%1�� ��%2��� �ϴ� ȣĪ�� �־����ϴ�.
						S_ServerMessage sm = new S_ServerMessage(203, crown.getName(), joinchar.getName(),
								joinchar.getTitle());
						clanPc.sendPackets(sm, true);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void clanJoin(L1PcInstance pc, L1PcInstance joinPc) {
		try {
			int clan_id = pc.getClanid();
			String clanName = pc.getClanname();
			L1Clan clan = L1World.getInstance().getClan(clanName);
			if (clan != null) {
				int maxMember = 300;

				if (joinPc.getClanid() == 0) { // ũ���̰���
					if (maxMember <= clan.getClanMemberList().size()) {// clanMembersName.length)
																		// { //
																		// �� ����
																		// ����
						joinPc.sendPackets(new S_ServerMessage(188, pc.getName())); // %0��
																					// �����
																					// ���Ϳ����μ�
																					// �޾Ƶ���
																					// ����
																					// �����ϴ�.
						return;
					}
					for (L1PcInstance clanMembers : clan.getOnlineClanMember()) {
						clanMembers.sendPackets(new S_ServerMessage(94, joinPc.getName())); // \f1%0��
																							// ������
																							// �Ͽ����μ�
																							// �޾Ƶ鿩�����ϴ�.
					}
					joinPc.setClanid(clan_id);
					joinPc.setClanname(clanName);
					joinPc.setClanRank(L1Clan.CLAN_RANK_PROBATION);
					joinPc.setTitle("");
					joinPc.setClanJoinDate(new Timestamp(System.currentTimeMillis()));
					joinPc.sendPackets(new S_CharTitle(joinPc.getId(), ""));
					Broadcaster.broadcastPacket(joinPc, new S_CharTitle(joinPc.getId(), ""));
					joinPc.save(); // DB�� ĳ���� ������ �����Ѵ�
					clan.addClanMember(joinPc.getName(), joinPc.getClanRank(), joinPc.getLevel(), joinPc.getType(),
							joinPc.getMemo(), joinPc.getOnlineStatus(), joinPc);
					joinPc.sendPackets(new S_PacketBox(S_PacketBox.MSG_RANK_CHANGED, 0x07, joinPc.getName()), true);
					joinPc.sendPackets(new S_ClanJoinLeaveStatus(joinPc), true);
					Broadcaster.broadcastPacket(joinPc, new S_ClanJoinLeaveStatus(joinPc));
					joinPc.sendPackets(new S_ReturnedStat(joinPc, S_ReturnedStat.CLAN_JOIN_LEAVE), true);
					Broadcaster.broadcastPacket(joinPc, new S_ReturnedStat(joinPc, S_ReturnedStat.CLAN_JOIN_LEAVE));
					joinPc.sendPackets(new S_ClanWindow(S_ClanWindow.����ũ����, joinPc.getClan().getmarkon()), true);
					joinPc.sendPackets(new S_�����ֽ�(joinPc.getClan(), 2), true);
					pc.sendPackets(new S_PacketBox(pc, S_PacketBox.PLEDGE_REFRESH_PLUS));
					joinPc.sendPackets(new S_ServerMessage(95, clanName)); // \f1%0
																			// ���Ϳ�
																			// �����߽��ϴ�.
				} else { // ũ�� ������ ���� ����(ũ�� ����)
					joinPc.sendPackets(new S_SystemMessage("�̹� ���Ϳ� �����߽��ϴ�.")); // \f1�����
																				// ����
																				// ���Ϳ�
																				// �����ϰ�
																				// �ֽ��ϴ�.
				}
			}
		} catch (Exception e) {
		}
	}

	public String readSS(byte[] data) {
		String s = null;
		try {
			s = new String(data, _off2, data.length - _off2, "utf-8");
			s = s.substring(0, s.indexOf('\0'));
			_off2 += s.getBytes("utf-8").length + 1;
		} catch (Exception e) {
			// _log.log(Level.SEVERE, "OpCode=" + (_decrypt[0] & 0xff), e);
		}
		return s;
	}

	public String readS(byte[] data) {
		String s = null;
		try {
			s = new String(data, _off, data.length - _off, "utf-8");
			s = s.substring(0, s.indexOf('\0'));
			_off += s.getBytes("utf-8").length + 1;
		} catch (Exception e) {
			// _log.log(Level.SEVERE, "OpCode=" + (_decrypt[0] & 0xff), e);
		}
		return s;
	}

	public static final String CLIENT_KEY = "WebClient";

	private static final int ����ã���ư = 0x01;
	private static final int ���Ͱ��Խ�û��ư = 0x02;

	private int calcheading(int myx, int myy, int tx, int ty) {
		if (tx > myx && ty > myy) {
			return 3;
		} else if (tx < myx && ty < myy) {
			return 7;
		} else if (tx > myx && ty == myy) {
			return 2;
		} else if (tx < myx && ty == myy) {
			return 6;
		} else if (tx == myx && ty < myy) {
			return 0;
		} else if (tx == myx && ty > myy) {
			return 4;
		} else if (tx < myx && ty > myy) {
			return 5;
		} else {
			return 1;
		}
	}
	/*
	 * ���� â���� ���� ��û ��ư Ŭ���� 1byte(opcode)0x01 ������ ���� ��ư�� Ŭ�� 1byte(type) 0x00
	 * (data1) Ŭ���� �����̸�(string) ���� 0x00���� (data2) Ŭ���� ��ư�� ���� �̸�(string) ����
	 * 0x00����
	 * 
	 * 
	 * ���� ��û â���� ��û��� ��ư Ŭ���� 1byte(opcode)0x01 ��û ��Ҹ� ���� ��ư�� Ŭ�� 1byte(type) 0x01
	 * (data1) Ŭ���� �����̸�(string) ���� 0x00���� (data2) Ŭ���� ��ư�� ���� �̸�(string) ����
	 * 0x00����
	 */

}
