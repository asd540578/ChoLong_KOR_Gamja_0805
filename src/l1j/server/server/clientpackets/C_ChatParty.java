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

import l1j.server.server.model.L1ChatParty;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Party;
import l1j.server.server.serverpackets.S_ServerMessage;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_ChatParty extends ClientBasePacket {

	private static final String C_CHAT_PARTY = "[C] C_ChatParty";

	public C_ChatParty(byte abyte0[], LineageClient clientthread) {
		super(abyte0);
		try {
			L1PcInstance pc = clientthread.getActiveChar();

			if (pc.isGhost())
				return;

			int type = readC();
			if (type == 0) { // /chatbanish Ŀ���
				String name = readS();
				if (!pc.isInChatParty()) {
					// ��Ƽ�� �����ϰ� ���� �ʽ��ϴ�.
					S_ServerMessage sm = new S_ServerMessage(425);
					pc.sendPackets(sm);
					sm = null;
					return;
				}
				if (!pc.getChatParty().isLeader(pc)) {
					// ��Ƽ�� �������� �߹��� �� �ֽ��ϴ�.
					S_ServerMessage sm = new S_ServerMessage(427);
					pc.sendPackets(sm);
					sm = null;
					return;
				}
				L1PcInstance targetPc = L1World.getInstance().getPlayer(name);
				if (targetPc == null) {
					// %0��� �̸��� ����� �����ϴ�.
					S_ServerMessage sm = new S_ServerMessage(109);
					pc.sendPackets(sm);
					sm = null;
					return;
				}
				if (pc.getId() == targetPc.getId()) {
					return;
				}

				for (L1PcInstance member : pc.getChatParty().getMembers()) {
					if (member.getName().toLowerCase()
							.equals(name.toLowerCase())) {
						pc.getChatParty().kickMember(member);
						return;
					}
				}
				// �߰ߵ��� �ʾҴ�
				// %0�� ��Ƽ ����� �ƴմϴ�.
				S_ServerMessage sm = new S_ServerMessage(426, name);
				pc.sendPackets(sm);
				sm = null;
			} else if (type == 1) { // /chatoutparty Ŀ���
				if (pc.isInChatParty()) {
					pc.getChatParty().leaveMember(pc);
				}
			} else if (type == 2) { // /chatparty Ŀ���
				L1ChatParty chatParty = pc.getChatParty();
				if (pc.isInChatParty()) {
					S_Party p = new S_Party("party", pc.getId(), chatParty
							.getLeader().getName(),
							chatParty.getMembersNameList());
					pc.sendPackets(p);
					p = null;
				} else {
					S_ServerMessage sm = new S_ServerMessage(425);
					pc.sendPackets(sm);
					sm = null;
					// pc.sendPackets(new S_Party("party", pc.getId()));
				}
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_CHAT_PARTY;
	}

}
