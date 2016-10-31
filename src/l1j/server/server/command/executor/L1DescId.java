/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.server.command.executor;

import java.util.StringTokenizer;
import java.util.logging.Logger;

import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_AddItemDoll;
import l1j.server.server.serverpackets.S_SystemMessage;

public class L1DescId implements L1CommandExecutor {
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(L1DescId.class.getName());

	private L1DescId() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1DescId();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			StringTokenizer st = new StringTokenizer(arg);
			// int gfxid = Integer.parseInt(st.nextToken(), 10);
			int descid = Integer.parseInt(st.nextToken(), 10);
			int count = 1;
			if (st.hasMoreTokens()) {
				count = Integer.parseInt(st.nextToken(), 10);
			}

			L1ItemInstance item = null;
			int cc = 0;
			for (int i = 0; i < count; i++) {
				item = ItemTable.getInstance().createItem(40005);
				item.setId(cc);
				item.getItem().setItemDescId(descid + i);
				item.getItem().setGfxId(1945);
				item.setCount(1);
				// item.setCount(1);
				item.getItem().setName(String.valueOf(descid + i));
				item.getItem().setNameId(String.valueOf(descid + i));
				item.setIdentified(true);
				S_AddItemDoll ai = new S_AddItemDoll(item);
				pc.sendPackets(ai);
				cc++;
			}
			int iii = descid + cc;
			pc.sendPackets(new S_SystemMessage("" + iii));
			System.out.println(descid + cc);
			/*
			 * for (int i1 = 0; i1 < 255; i1++) { S_DeleteInventoryItem ai2 =
			 * new S_DeleteInventoryItem(i1); pc.sendPackets(ai2); }
			 */
		} catch (Exception exception) {
			pc.sendPackets(new S_SystemMessage(cmdName
					+ " [id] [출현시키는 수]로 입력해 주세요. "), true);
		}
	}
}
