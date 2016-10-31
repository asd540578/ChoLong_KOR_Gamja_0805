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
 * Author: ChrisLiu.2007.06.30
 */
package l1j.server.server.clientpackets;

import java.util.List;

import l1j.server.server.model.L1Clan.ClanMember;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_ServerMessage;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_Pledge extends ClientBasePacket {

	private static final String C_PLEDGE = "[C] C_Pledge";

	public C_Pledge(byte abyte0[], LineageClient clientthread) {
		super(abyte0);
		try {
			L1PcInstance pc = clientthread.getActiveChar();

			if (pc.getClanid() > 0) {
				pc.sendPackets(new S_ClanWindow(pc, S_ClanWindow.혈맹공지및정보), true);
				int size = pc.getClan().getClanMemberList().size();
				int i = size / 90;
				for (int a = 0; a <= i; a++) {
					List<ClanMember> list = pc.getClan().getClanMemberList().subList(a * 90,
							a == i ? size : (a + 1) * 90);
					pc.sendPackets(new S_ClanWindow(list, i + 1, a));
				}
				pc.sendPackets(new S_ClanWindow(pc, S_ClanWindow.접속유저), true);
			} else {
				S_ServerMessage sm = new S_ServerMessage(1064);
				pc.sendPackets(sm);
				sm = null;
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_PLEDGE;
	}

}
