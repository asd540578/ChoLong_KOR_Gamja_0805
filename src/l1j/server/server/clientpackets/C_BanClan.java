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

import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import server.LineageClient;

public class C_BanClan extends ClientBasePacket {

	private static final String C_BAN_CLAN = "[C] C_BanClan";
	private static Logger _log = Logger.getLogger(C_BanClan.class.getName());

	public C_BanClan(byte abyte0[], LineageClient clientthread)
			throws Exception {
		super(abyte0);
		try {
			String s = readS();
			if (s == null || s.equals(""))
				return;

			L1PcInstance pc = clientthread.getActiveChar();
			L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
			if (clan != null) {
				if (pc.isCrown() && pc.getId() == clan.getLeaderId()) { // ����,
																		// ����,
																		// ������
					if (pc.getName().toLowerCase().equals(s.toLowerCase())) { // ����
																				// �ڽ�
						return;
					}
					int castle_id = clan.getCastleId();
					if (castle_id != 0
							&& WarTimeController.getInstance().isNowWar(
									castle_id)) {
						S_ServerMessage sm = new S_ServerMessage(439);
						pc.sendPackets(sm);
						sm = null;
						return;
					}
					L1PcInstance tempPc = L1World.getInstance().getPlayer(s);
					if (tempPc != null) { // �¶�����
						if (tempPc.getClanid() == pc.getClanid()) { // ���� ũ��
							tempPc.ClearPlayerClanData(clan);
							clan.removeOnlineClanMember(tempPc.getName());
							clan.removeClanMember(tempPc.getName());
							S_PacketBox pb = new S_PacketBox(pc,
									S_PacketBox.PLEDGE_REFRESH_MINUS);
							pc.sendPackets(pb);
							pb = null;
							S_ServerMessage sm = new S_ServerMessage(238,
									pc.getClanname());
							tempPc.sendPackets(sm); // �����%0�������κ��� �߹�Ǿ����ϴ�.
							sm = null;
							S_ServerMessage sm2 = new S_ServerMessage(240,
									tempPc.getName());
							pc.sendPackets(sm2); // %0�� ����� �������κ��� �߹�Ǿ����ϴ�.
							sm2 = null;
						} else {
							S_ServerMessage sm = new S_ServerMessage(109, s);
							pc.sendPackets(sm); // %0��� �̸��� ����� �����ϴ�.
							sm = null;
						}
					} else { // ���� ������
						try {
							L1PcInstance restorePc = CharacterTable
									.getInstance().restoreCharacter(s);
							if (restorePc != null
									&& restorePc.getClanid() == pc.getClanid()) { // ����
																					// ����
								restorePc.ClearPlayerClanData(clan);
								clan.removeClanMember(restorePc.getName());
								S_ServerMessage sm = new S_ServerMessage(240,
										restorePc.getName());
								pc.sendPackets(sm); // %0�� ����� �������κ��� �߹�Ǿ����ϴ�.
								sm = null;
							} else {
								S_ServerMessage sm = new S_ServerMessage(109, s);
								pc.sendPackets(sm); // %0��� �̸��� ����� �����ϴ�.
								sm = null;
							}
						} catch (Exception e) {
							_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
						}
					}
				} else {
					S_ServerMessage sm = new S_ServerMessage(518);
					pc.sendPackets(sm); // �� ����� ������ ���ָ� �̿��� �� �ֽ��ϴ�.
					sm = null;
				}
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_BAN_CLAN;
	}
}
