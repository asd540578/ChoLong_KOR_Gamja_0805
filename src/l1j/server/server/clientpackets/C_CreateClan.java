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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;

public class C_CreateClan extends ClientBasePacket {

	private static final String C_CREATE_CLAN = "[C] C_CreateClan";

	public C_CreateClan(byte abyte0[], LineageClient clientthread)
			throws Exception {
		super(abyte0);
		try {
			String s = readS();

			if (s == null || s.equalsIgnoreCase(""))
				return;

			L1PcInstance l1pcinstance = clientthread.getActiveChar();

			for (char ac : s.toCharArray()) {
				if (!Character.isLetterOrDigit(ac)) {
					S_SystemMessage sm = new S_SystemMessage(
							"���� â�� �Ұ� : �߸��� ���ڶǴ� ��ȣ�� ���Ե�. (" + ac + ")");
					l1pcinstance.sendPackets(sm, true);
					return;
				}
			}

			int numOfNameBytes = 0;
			numOfNameBytes = s.getBytes("EUC-KR").length;

			if (l1pcinstance.isCrown()) { // ������ �Ǵ� ��������
				if (l1pcinstance.getClanid() == 0) {

					/*
					 * int length = s.length(); char chr; for(int i = 0; i <
					 * length; i++){ chr = s.charAt(i);
					 * if(Character.UnicodeBlock.of(chr) !=
					 * Character.UnicodeBlock.BASIC_LATIN){ S_SystemMessage sm =
					 * new S_SystemMessage("���� â�� �Ұ� : �ѱ� �Ǵ� ���� ���ڸ� ����");
					 * l1pcinstance.sendPackets(sm, true); return; } }
					 */

					if (!l1pcinstance.getInventory().checkItem(40308, 30000)) {
						S_SystemMessage sm = new S_SystemMessage(
								"���� â�� �Ұ� : (30000) �Ƶ��� �ʿ�.");
						l1pcinstance.sendPackets(sm, true);
						return;
					}

					l1pcinstance.getInventory().consumeItem(L1ItemId.ADENA,
							30000);

					if (l1pcinstance.getLevel() < 5) {
						S_SystemMessage sm = new S_SystemMessage(
								"5���� �̸��� ������ â���Ҽ� �����ϴ�.");
						l1pcinstance.sendPackets(sm, true);
						return;
					}
					for (int i = 0; i < s.length(); i++) {
						if (s.charAt(i) == ' ' || s.charAt(i) == '��') {
							S_ServerMessage sm = new S_ServerMessage(53);
							l1pcinstance.sendPackets(sm, true);
							return;
						}
					}
					if (8 < (numOfNameBytes - s.length())
							|| 16 < numOfNameBytes) {

						S_SystemMessage sm = new S_SystemMessage(
								"�����̸��� �ʹ� ��ϴ�.");
						l1pcinstance.sendPackets(sm, true);
					}

					S_SystemMessage sm99 = new S_SystemMessage(
							"���� �̸��� ������ �����մϴ�.");
					if (s.equalsIgnoreCase("ruphy")) {
						l1pcinstance.sendPackets(sm99, true);
						return;
					}

					for (L1Clan clan : L1World.getInstance().getAllClans()) { // \f1
																				// ����
																				// �̸���
																				// ������
																				// �����մϴ�.
						if (clan.getClanName().toLowerCase()
								.equals(s.toLowerCase())) {
							l1pcinstance.sendPackets(sm99, true);
							return;
						}
					}

					if (gambleClanCheck(s)) {
						l1pcinstance.sendPackets(sm99, true);
						return;
					}
					L1Clan clan = ClanTable.getInstance().createClan(
							l1pcinstance, s); // ũ�� â��

					if (clan != null) {
						S_ServerMessage sm = new S_ServerMessage(84, s);
						l1pcinstance.sendPackets(sm, true);
					}

				} else {

					S_SystemMessage sm = new S_SystemMessage("�̹� ������ â���Ͽ����ϴ�");
					l1pcinstance.sendPackets(sm, true);
				}
			} else {

				S_SystemMessage sm = new S_SystemMessage(
						"���ڿ� ���ָ��� ������ â���� �� �ֽ��ϴ�.");
				l1pcinstance.sendPackets(sm, true);
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	private boolean gambleClanCheck(String s) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM npc_gamble_spawnlist WHERE clan_name=?");
			pstm.setString(1, s);
			rs = pstm.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	@Override
	public String getType() {
		return C_CREATE_CLAN;
	}

}
