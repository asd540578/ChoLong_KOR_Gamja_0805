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

import java.io.File;
import java.util.logging.Logger;

import l1j.server.server.datatables.ClanTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Emblem;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_Clan extends ClientBasePacket {

	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(C_Clan.class.getName());
	private static final String C_CLAN = "[C] C_Clan";

	public C_Clan(byte abyte0[], LineageClient client) {
		super(abyte0);
		try {
			int clanId = readD();

			L1PcInstance pc = client.getActiveChar();

			L1Clan clan = ClanTable.getInstance().getTemplate(clanId);

			if (clan == null) {
				String emblem_file = String.valueOf(clanId);
				File file = new File("emblem/" + emblem_file);
				if (file.exists()) {
					S_Emblem em = new S_Emblem(clanId);
					pc.sendPackets(em, true);
				}
				/*
				 * boolean em = false; String emblem_file =
				 * String.valueOf(clanId); File file = new File("emblem/" +
				 * emblem_file); if (file.exists())em = true; if(em){
				 * pc.sendPackets(new S_Emblem(clanId)); }else{
				 * System.out.println
				 * ("마크 오류 혈맹번호 :"+clanId+"요청 케릭 :"+pc.getName());}
				 */
				return;
			}
			String emblem_file = String.valueOf(clanId);
			File file = new File("emblem/" + emblem_file);
			if (file.exists()) {
				S_Emblem em = new S_Emblem(clan.getClanId());
				pc.sendPackets(em, true);
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_CLAN;
	}

}
