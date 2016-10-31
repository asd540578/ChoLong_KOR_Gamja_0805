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

import java.sql.Timestamp;
import java.util.Random;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_CharTitle;
import l1j.server.server.serverpackets.S_ClanJoinLeaveStatus;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_�����ֽ�;
import l1j.server.server.utils.FaceToFace;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_JoinClan extends ClientBasePacket {

	private static final String C_JOIN_CLAN = "[C] C_JoinClan";

	public C_JoinClan(byte abyte0[], LineageClient clientthread)
			throws Exception {
		super(abyte0);
		try {
			L1PcInstance pc = clientthread.getActiveChar();
			if (pc == null || pc.isGhost()) {
				return;
			}

			L1PcInstance target = FaceToFace.faceToFace(pc);
			if (target != null) {
				JoinClan(pc, target);
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	private void JoinClan(L1PcInstance player, L1PcInstance target) {
		if (target.getClanRank() < L1Clan.CLAN_RANK_GUARDIAN) { // ��밡 ������ �Ǵ�
																// �������� �̿�
			// player.sendPackets(new S_ServerMessage(92, target.getName())); //
			// \f1%0�� �������� ���������� �ƴմϴ�.
			S_SystemMessage sm = new S_SystemMessage(target.getName()
					+ "�� ���� �Ǵ� ��ȣ��簡 �ƴմϴ�.");
			player.sendPackets(sm, true);
			return;
		}

		int clan_id = target.getClanid();
		String clan_name = target.getClanname();
		if (clan_id == 0) { // ��� ũ���� ����
			S_ServerMessage sm = new S_ServerMessage(90, target.getName());
			player.sendPackets(sm, true);
			return;
		}

		L1Clan clan = L1World.getInstance().getClan(clan_name);
		if (clan == null)
			return;
		/*
		 * if (target.getId() != clan.getLeaderId()) { // ��밡 ������ �̿�
		 * player.sendPackets(new S_ServerMessage(92, target.getName())); //
		 * \f1%0�� �������� ���������� �ƴմϴ�. return; }
		 */
		if (player.getClanid() != 0) { // �̹� ũ���� ������ ���� ����
			if (player.isCrown()) { // �ڽ��� ����
				String player_clan_name = player.getClanname();
				L1Clan player_clan = L1World.getInstance().getClan(
						player_clan_name);
				if (player_clan == null) {
					return;
				}

				if (player.getId() != player_clan.getLeaderId()) { // �ڽ��� ������ �̿�
					S_ServerMessage sm = new S_ServerMessage(89);
					player.sendPackets(sm, true);
					return;
				}

				if (player_clan.getCastleId() != 0
						|| player_clan.getHouseId() != 0) {
					S_ServerMessage sm = new S_ServerMessage(665);
					player.sendPackets(sm, true);
					return;
				}
			} else {
				S_ServerMessage sm = new S_ServerMessage(89);
				player.sendPackets(sm, true);
				return;
			}
		}

		/*
		 * if(target.getClan().getJoinSetting() == 0){ player.sendPackets(new
		 * S_SystemMessage("["+target.getClanname()+"] ������\n���� ������ �� �����ϴ�."),
		 * true); //player.sendPackets(new
		 * S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 11),true); return;
		 * }else if(target.getClan().getJoinType() == 0){ C_Attr.���Ͱ���(target,
		 * player, target.getClan()); return; }
		 */

		target.setTempID(player.getId()); // ����� ������Ʈ ID�� ������ �д�
		if (target instanceof L1RobotInstance) {
			if (300 <= clan.getClanMemberList().size()) {// clanMembersName.length)
															// { // �� ���� ����
				player.sendPackets(new S_ServerMessage(188, target.getName())); // %0��
																				// �����
																				// ���Ϳ����μ�
																				// �޾Ƶ���
																				// ����
																				// �����ϴ�.
				return;
			}
			Random _rnd = new Random(System.nanoTime());
			GeneralThreadPool.getInstance().schedule(new join(target, player),
					1000 + _rnd.nextInt(2000));
		} else {
			S_Message_YN myn = new S_Message_YN(97, player.getName());
			target.sendPackets(myn, true);
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
				if (crown == null
						|| joinchar == null
						|| L1World.getInstance().getPlayer(crown.getName()) == null
						|| L1World.getInstance().getPlayer(joinchar.getName()) == null) {
					return;
				}
				clanJoin(crown, joinchar);
				if (((L1RobotInstance) crown)._userTitle == null
						|| ((L1RobotInstance) crown)._userTitle
								.equalsIgnoreCase(""))
					return;
				Random _rnd = new Random(System.nanoTime());
				Thread.sleep(3000 + _rnd.nextInt(2000));

				if (L1World.getInstance().getPlayer(crown.getName()) == null
						|| L1World.getInstance().getPlayer(joinchar.getName()) == null)
					return;

				joinchar.setTitle(((L1RobotInstance) crown)._userTitle);
				S_CharTitle ct = new S_CharTitle(joinchar.getId(),
						joinchar.getTitle());
				joinchar.sendPackets(ct);
				Broadcaster.broadcastPacket(joinchar, ct, true);
				try {
					if (!(joinchar instanceof L1RobotInstance))
						joinchar.save(); // DB�� ĳ���� ������ �� ��
				} catch (Exception e) {
				}

				L1Clan clan = L1World.getInstance()
						.getClan(crown.getClanname());
				if (clan != null) {
					for (L1PcInstance clanPc : clan.getOnlineClanMember()) {
						// \f1%0��%1�� ��%2��� �ϴ� ȣĪ�� �־����ϴ�.
						S_ServerMessage sm = new S_ServerMessage(203,
								crown.getName(), joinchar.getName(),
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
						joinPc.sendPackets(new S_ServerMessage(188, pc
								.getName())); // %0�� ����� ���Ϳ����μ� �޾Ƶ��� ���� �����ϴ�.
						return;
					}
					for (L1PcInstance clanMembers : clan.getOnlineClanMember()) {
						clanMembers.sendPackets(new S_ServerMessage(94, joinPc
								.getName())); // \f1%0�� ������ �Ͽ����μ� �޾Ƶ鿩�����ϴ�.
					}
					joinPc.setClanid(clan_id);
					joinPc.setClanname(clanName);
					joinPc.setClanRank(L1Clan.CLAN_RANK_PROBATION);
					joinPc.setTitle("");
					joinPc.setClanJoinDate(new Timestamp(System
							.currentTimeMillis()));
					joinPc.sendPackets(new S_CharTitle(joinPc.getId(), ""));
					Broadcaster.broadcastPacket(joinPc,
							new S_CharTitle(joinPc.getId(), ""));
					joinPc.save(); // DB�� ĳ���� ������ �����Ѵ�
					clan.addClanMember(joinPc.getName(), joinPc.getClanRank(),
							joinPc.getLevel(), joinPc.getType(),
							joinPc.getMemo(), joinPc.getOnlineStatus(), joinPc);
					joinPc.sendPackets(
							new S_PacketBox(S_PacketBox.MSG_RANK_CHANGED, 0x07,
									joinPc.getName()), true);
					joinPc.sendPackets(new S_ClanJoinLeaveStatus(joinPc), true);
					Broadcaster.broadcastPacket(joinPc,
							new S_ClanJoinLeaveStatus(joinPc));
					joinPc.sendPackets(new S_ReturnedStat(joinPc,
							S_ReturnedStat.CLAN_JOIN_LEAVE), true);
					Broadcaster.broadcastPacket(joinPc, new S_ReturnedStat(
							joinPc, S_ReturnedStat.CLAN_JOIN_LEAVE));
					joinPc.sendPackets(new S_ClanWindow(S_ClanWindow.����ũ����,
							joinPc.getClan().getmarkon()), true);
					joinPc.sendPackets(new S_�����ֽ�(joinPc.getClan(), 2), true);
					pc.sendPackets(new S_PacketBox(pc,
							S_PacketBox.PLEDGE_REFRESH_PLUS));
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

	@Override
	public String getType() {
		return C_JOIN_CLAN;
	}
}
