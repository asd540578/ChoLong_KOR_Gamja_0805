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

import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import server.LineageClient;

public class C_WarTimeList extends ClientBasePacket {

	private static final String C_WAR_TIME_LIST = "[C] C_WarTimeList";

	public C_WarTimeList(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);
		try {
			L1PcInstance pc = clientthread.getActiveChar();
			L1Clan clan = L1World.getInstance().getClan(pc.getClanname());

			if (clan != null) {
				int castle_id = clan.getCastleId();
				if (castle_id != 0) { // 성주 클랜 아이디
					S_ServerMessage sm = new S_ServerMessage(305);
					pc.sendPackets(sm, true);// \f1지금은 전쟁 시간을 변경할 수 없습니다.
					return;
				}
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_WAR_TIME_LIST;
	}

}
