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
package l1j.server.server.serverpackets;

import java.io.IOException;

import l1j.server.Warehouse.SupplementaryService;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_RetrieveSupplementaryService extends ServerBasePacket {

	private static final String _S_RetrieveSupplementaryService = "[S] S_RetrieveSupplementaryService";

	public S_RetrieveSupplementaryService(int objid, L1PcInstance pc) {
		if (pc.getInventory().getSize() < 180) {
			SupplementaryService warehouse = WarehouseManager.getInstance()
					.getSupplementaryService(pc.getAccountName());
			int size = warehouse.getSize();
			if (size > 0) {
				writeC(Opcodes.S_RETRIEVE_LIST);
				writeD(objid);
				writeH(size);
				writeC(20); // ���� â��
				L1ItemInstance item = null;
				byte[] status = null;
				for (Object itemObject : warehouse.getItems()) {
					item = (L1ItemInstance) itemObject;
					writeD(item.getId());
					writeC(0);
					writeH(item.get_gfxid());
					writeC(item.getBless());
					writeD(item.getCount());
					writeC(item.isIdentified() ? 1 : 0);
					writeS(item.getViewName());
					if (!item.isIdentified()) {
						// �̰����� ��� �������ͽ��� ���� �ʿ�� ����
						writeC(0);
					} else {
						status = /* pc == null ? item.getStatusBytes() : */item
								.getStatusBytes(pc);
						// status = item.getStatusBytes();
						writeC(status.length);
						for (byte b : status) {
							writeC(b);
						}
					}
				}
			}
			writeH(0x00);
			writeD(0x00);
			writeH(0x00);
			writeH(0x08);
		} else {
			pc.sendPackets(new S_ServerMessage(263)); // \f1�ѻ���� ĳ���Ͱ� ������ ���� ��
														// �ִ� �������� �ִ� 180�������Դϴ�.
		}
	}

	@Override
	public byte[] getContent() throws IOException {
		return getBytes();
	}

	@Override
	public String getType() {
		return _S_RetrieveSupplementaryService;
	}
}
