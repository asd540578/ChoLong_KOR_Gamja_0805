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

import l1j.server.Config;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import server.LineageClient;

//Referenced classes of package l1j.server.server.clientpackets:
//ClientBasePacket

public class C_DeleteInventoryItem extends ClientBasePacket {

	private static final String C_DELETE_INVENTORY_ITEM = "[C] C_DeleteInventoryItem";

	public C_DeleteInventoryItem(byte[] decrypt, LineageClient client) {
		super(decrypt);
		try {
			L1PcInstance pc = client.getActiveChar();
			if (pc == null)
				return;
			int size = readD();
			int itemObjectId;
			int itemCount;
			L1ItemInstance item = null;
			if (size < 1)
				return;
			if (size > 20) {
				size = 20;
			}
			for (int i = 0; i < size; i++) {
				itemObjectId = readD();
				itemCount = readD();
				item = pc.getInventory().getItem(itemObjectId);
				if (item == null) {
					return;
				}

				if (!pc.isGm() && item.getItem().isCantDelete()) {
					S_SystemMessage sm = new S_SystemMessage(
							"���� �Ұ��� �������̳� �����ϰ� �ִ� �������� ���� �� �����ϴ�.");
					pc.sendPackets(sm, true);
					continue;
				}

				if (item.isEquipped()) {
					// \f1������ �� ���� �������̳� ��� �ϰ� �ִ� �������� ���� �� �����ϴ�.
					S_SystemMessage sm = new S_SystemMessage(
							"���� �Ұ��� �������̳� �����ϰ� �ִ� �������� ���� �� �����ϴ�.");
					pc.sendPackets(sm, true);
					continue;
				}
				if (item.getBless() >= 128) {
					S_ServerMessage sm = new S_ServerMessage(210, item
							.getItem().getName());
					pc.sendPackets(sm, true);
					continue;
				}

				L1PetInstance pet = null;
				for (Object petObject : pc.getPetList()) {
					if (petObject instanceof L1PetInstance) {
						pet = (L1PetInstance) petObject;
						if (item.getId() == pet.getItemObjId()) {
							// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
							S_ServerMessage sm = new S_ServerMessage(210, item
									.getItem().getName());
							pc.sendPackets(sm, true);
							continue;
						}
					}
				}
				if (Config.����ä�ø����() > 0) {
					S_SystemMessage sm = new S_SystemMessage("[" + pc.getName()
							+ "] " + item.getName() + "(" + item.getCount()
							+ ")�� [����]");
					;
					for (L1PcInstance gm : Config.toArray����ä�ø����()) {
						if (gm.getNetConnection() == null) {
							Config.remove����(gm);
							continue;
						}
						if (gm == pc) {
							continue;
						}
						gm.sendPackets(sm);
					}
				}

				if (itemCount > item.getCount() || itemCount == 0)
					itemCount = item.getCount();

				pc.getInventory().removeItem(item, itemCount);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_DELETE_INVENTORY_ITEM;
	}
}
