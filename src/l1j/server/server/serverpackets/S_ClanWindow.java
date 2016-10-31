package l1j.server.server.serverpackets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import l1j.server.server.Opcodes;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Clan.ClanMember;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;

/**
 * ���� ���� â�� ������ �ڽ�.
 */
public class S_ClanWindow extends ServerBasePacket {
	private static final String S_ClanWindow = "[S] S_ClanWindow";
	private byte[] _byte = null;

	public static final int ���Ͱ��������� = 167;
	public static final int ���Ͱ������� = 168;
	public static final int ���Ϳ��޸𺯰� = 169;
	public static final int ���Ϳ���� = 170;
	public static final int �������� = 171;

	public static final int ����ũ���� = 173;

	public S_ClanWindow(int subCode, int val) {
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case ����ũ����:
			writeC(0x01);
			writeC(val);
			writeH(0);
			break;
		default:
			break;
		}
	}

	public S_ClanWindow(L1PcInstance pc, int subCode) {
		String clanName = pc.getClanname();
		L1Clan clan = L1World.getInstance().getClan(clanName);
		writeC(Opcodes.S_EVENT);
		writeC(subCode);
		switch (subCode) {
		case ���Ͱ���������:
			writeS(clan.getClanName());
			writeS(clan.getLeaderName());
			writeD(clan.getClanId());

			writeC(0);
			writeC(0);
			writeC(0);
			writeC(0xf0);
			writeC(0);
			Calendar s = (Calendar) Calendar.getInstance().clone();
			String[] str = clan.getCreateDate().split("/");
			// System.out.println(str[0]+" > "+str[1]+" > "+str[2]);
			s.set(Integer.parseInt(str[0]), Integer.parseInt(str[1]) - 1,
					Integer.parseInt(str[2]) + 1);
			writeH((int) (s.getTimeInMillis() / 1000 / (65537.347303365361402153445029212)));
			// writeD(0x3e43c9f0);//f0 c9 43 3e
			if (clan.getNotice() != null) {
				writeS(clan.getNotice());
			}
			// int cla = 502 - getBytes().length;
			int cla = 496 - getBytes().length;
			for (int i = 0; i < cla; i++) {
				writeC(0x00);
			}
			break;

		case ���Ͱ�������:
			if (clan.getNotice() != null) {
				writeS(clan.getNotice());
			}
			int aaa = 502 - getBytes().length;
			for (int i = 0; i < aaa; i++) {
				writeC(0x00);
			}
			break;
		case ���Ϳ��޸𺯰�: {
			writeS(pc.getName());
			if (pc.getMemo() != null) {
				writeS(pc.getMemo());
				// }
				int bbb = 61 - pc.getMemo().getBytes().length;
				for (int j = 0; j < bbb; j++) {
					writeC(0x00);
				}
			} else {
				for (int j = 0; j < 62; j++) {
					writeC(0x00);
				}
			}
		}
			break;
		case ���Ϳ����:
			writeC(0x01);// ������������
			writeC(0x00);// ������������ȣ
			ArrayList<ClanMember> clanMemberList = clan.getClanMemberList(); // ���
																				// ���Ϳ���
																				// �̸���
																				// ���
			writeC(clanMemberList.size());// ���������ο�
			ClanMember member;
			for (int i = 0; i < clanMemberList.size(); i++) {
				member = clanMemberList.get(i);
				writeS(member.name);
				if (member.rank == L1Clan.CLAN_RANK_PROBATION) {
					writeC(L1Clan.CLAN_RANK_PROBATION);
				} else if (member.rank == L1Clan.CLAN_RANK_GUARDIAN) {
					writeC(L1Clan.CLAN_RANK_GUARDIAN);
				} else if (member.rank == L1Clan.CLAN_RANK_PRINCE) {
					writeC(L1Clan.CLAN_RANK_PRINCE);
				} else if (member.rank == L1Clan.CLAN_RANK_PUBLIC) {
					writeC(L1Clan.CLAN_RANK_PUBLIC);
				} else if (member.rank == L1Clan.CLAN_RANK_����) {
					writeC(L1Clan.CLAN_RANK_����);
				} else {
					writeC(L1Clan.CLAN_RANK_PROBATION);
				}
				writeC(member.level);// ����
				if (member.memo != null) {
					writeS(member.memo);
					// }
					// pc.sendPackets(new S_SystemMessage(member.memo));
					int bbb = 61 - member.memo.getBytes().length;
					/*
					 * pc.sendPackets(new S_SystemMessage(
					 * member.memo.getBytes().length + "�޸���� / ���Ȱ� : " + bbb));
					 */
					for (int j = 0; j < bbb; j++) {
						writeC(0x00);
					}
				} else {
					for (int j = 0; j < 62; j++) {
						writeC(0x00);
					}
				}
				// int bbb = 73 - getBytes().length;
				// for(int j=0; j < bbb; j++){
				// writeC(0x00);
				// }
				writeD(0x00000000);
				writeC(member.type);// Ŭ����
			}
			writeH(0x8ECD);
			break;
		case ��������:
			writeH(clan.getOnlineMemberCount());
			for (L1PcInstance targetPc : clan.getOnlineClanMember()) { // �¶���
				writeS(targetPc.getName());
				writeC(0x00);
			}
			writeH(0x0000);
			break;
		}
	}

	public S_ClanWindow(List<ClanMember> clanMemberList, int total, int curpage) {
		writeC(Opcodes.S_EVENT);
		writeC(���Ϳ����);
		writeC(total);// ������������
		writeC(curpage);// ������������ȣ
		// 2D e4 1e 52 - 13�� 8�� 29��
		writeC(clanMemberList.size());// ���������ο�
		for (ClanMember member : clanMemberList) {
			writeS(member.name);
			if (member.rank == L1Clan.CLAN_RANK_PROBATION) {
				writeC(L1Clan.CLAN_RANK_PROBATION);
			} else if (member.rank == L1Clan.CLAN_RANK_GUARDIAN) {
				writeC(L1Clan.CLAN_RANK_GUARDIAN);
			} else if (member.rank == L1Clan.CLAN_RANK_PRINCE) {
				writeC(L1Clan.CLAN_RANK_PRINCE);
			} else if (member.rank == L1Clan.CLAN_RANK_PUBLIC) {
				writeC(L1Clan.CLAN_RANK_PUBLIC);
			} else if (member.rank == L1Clan.CLAN_RANK_����) {
				writeC(L1Clan.CLAN_RANK_����);
			} else {
				writeC(L1Clan.CLAN_RANK_PROBATION);
			}
			writeC(member.level);// ����

			if (member.memo != null) {
				writeS(member.memo);
				int bbb = 61 - member.memo.getBytes().length;
				for (int j = 0; j < bbb; j++) {
					writeC(0x00);
				}
			} else {
				for (int j = 0; j < 62; j++) {
					writeC(0x00);
				}
			}
			writeD(0x001270a6);// a6 70 12 00
			writeC(member.type);// Ŭ����

			if (member.player == null
					|| member.player.getClanJoinDate() == null) {
				writeD(0x00);// ������
			} else {
				writeD((int) (member.player.getClanJoinDate().getTime() / 1000));
			}
		}
		writeH(0x00);
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
		return S_ClanWindow;
	}
}
