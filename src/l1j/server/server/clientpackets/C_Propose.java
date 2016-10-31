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

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.utils.FaceToFace;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_Propose extends ClientBasePacket {

	private static final String C_PROPOSE = "[C] C_Propose";

	public C_Propose(byte abyte0[], LineageClient clientthread) {
		super(abyte0);
		int c = readC();
		try {
			L1PcInstance pc = clientthread.getActiveChar();
			if (c == 0) { // /propose(/��������)
				if (pc.isGhost()) {
					return;
				}
				L1PcInstance target = FaceToFace.faceToFace(pc);

				if (target != null) {
					if (pc.getPartnerId() > 0) {
						S_ServerMessage sm = new S_ServerMessage(657);
						pc.sendPackets(sm, true);// \f1����� ���� ��ȥ�߽��ϴ�.
						return;
					}
					if (target.getPartnerId() > 0) {
						S_ServerMessage sm = new S_ServerMessage(658);
						pc.sendPackets(sm, true); // \f1 �� ���� ���� ��ȥ�߽��ϴ�.
						return;
					}
					if (pc.get_sex() == target.get_sex()) {
						S_ServerMessage sm = new S_ServerMessage(661);
						pc.sendPackets(sm, true); // \f1��ȥ���� �̼��� �ƴϸ� �ȵ˴ϴ�.
						return;
					}
					if (!pc.getInventory().checkItem(40901)
							&& !pc.getInventory().checkItem(40902)
							&& !pc.getInventory().checkItem(40903)
							&& !pc.getInventory().checkItem(40904)
							&& !pc.getInventory().checkItem(40905)
							&& !pc.getInventory().checkItem(40906)
							&& !pc.getInventory().checkItem(40907)
							&& !pc.getInventory().checkItem(40908)) {
						S_ServerMessage sm = new S_ServerMessage(659);
						pc.sendPackets(sm, true); // \f1����� ��ȥ������ ������ ���� �ʽ��ϴ�.
						return;
					}
					if (!target.getInventory().checkItem(40903)
							&& !target.getInventory().checkItem(40901)
							&& !target.getInventory().checkItem(40902)
							&& !target.getInventory().checkItem(40904)
							&& !target.getInventory().checkItem(40905)
							&& !target.getInventory().checkItem(40906)
							&& !target.getInventory().checkItem(40907)
							&& !target.getInventory().checkItem(40908)) {
						S_ServerMessage sm = new S_ServerMessage(660);
						pc.sendPackets(sm, true); // \f1����� ûȥ�� ����� ��ȥ������ ������ ����
													// �ʽ��ϴ�.
						return;
					}
					/*
					 * if (pc.getX() >= 33974 && pc.getX() <= 33976 && pc.getY()
					 * >= 33362 && pc.getY() <= 33365 && pc.getMapId() == 4 &&
					 * target.getX() >= 33974 && target.getX() <= 33976 &&
					 * target.getY() >= 33362 && target.getY() <= 33365 &&
					 * target.getMapId() == 4) {
					 */
					target.setTempID(pc.getId()); // ����� ������Ʈ ID�� ������ �д�
					S_Message_YN yn = new S_Message_YN(654, pc.getName());
					target.sendPackets(yn, true); // %0%s��Ű� ��ȥ �ϰ� �;��ϰ� �ֽ��ϴ�.
													// %0�� ��ȥ�մϱ�? (Y/N)
					// }
				}
			} else if (c == 1) { // /divorce(/��ȥ)
				if (pc.getPartnerId() == 0) {
					S_ServerMessage sm = new S_ServerMessage(662);
					pc.sendPackets(sm, true); // \f1����� ��ȥ���� �ʾҽ��ϴ�.
					return;
				}
				S_Message_YN yn = new S_Message_YN(653, "");
				pc.sendPackets(yn, true); // ��ȥ�� �ϸ�(��) ���� ����� �����ϴ�. ��ȥ�� �ٶ��ϱ�?
											// (Y/N)

			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_PROPOSE;
	}
}
