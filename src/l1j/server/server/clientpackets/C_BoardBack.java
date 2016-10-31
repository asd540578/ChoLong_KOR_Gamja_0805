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

import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1BoardInstance;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket, C_BoardPage

public class C_BoardBack extends ClientBasePacket {

	private static final String C_BOARD_BACK = "[C] C_BoardBack";

	public C_BoardBack(byte abyte0[], LineageClient client) {
		super(abyte0);
		try {
			int objId = readD();
			int topicNumber = readD();
			L1Object obj = L1World.getInstance().findObject(objId);
			if (obj == null || client.getActiveChar() == null)
				return;
			L1BoardInstance board = (L1BoardInstance) obj;
			// System.out.println("topic1 "+topicNumber);
			board.onAction(client.getActiveChar(), topicNumber);
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_BOARD_BACK;
	}

}
