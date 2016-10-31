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

import java.util.Calendar;
import java.util.TimeZone;

import l1j.server.Config;
import l1j.server.server.datatables.BoardTable;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1BoardInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_BoardWrite extends ClientBasePacket {

	private static final String C_BOARD_WRITE = "[C] C_BoardWrite";

	// private static Logger _log =
	// Logger.getLogger(C_BoardWrite.class.getName());

	public C_BoardWrite(byte decrypt[], LineageClient client) {
		super(decrypt);
		try {
			int id = readD();
			String date = currentTime();
			String title = readS();
			String content = readS();

			L1Object tg = L1World.getInstance().findObject(id);
			if (tg != null && tg instanceof L1BoardInstance) {
				L1BoardInstance board = (L1BoardInstance) tg;
				if (board.getNpcId() == 100212) { // �ػ��� �Խ���
					return;
				}
				L1PcInstance pc = client.getActiveChar();
				if (board.getNpcId() == 4500309
						|| board.getNpcId() == 4200012
						|| board.getNpcId() == 4200016
						|| board.getNpcId() == 4200013
						|| board.getNpcId() == 4200014
						|| (board.getNpcId() >= 100283 && board.getNpcId() <= 100285)) {
					if (!pc.isGm()) {
						S_SystemMessage ssm = new S_SystemMessage(
								"�ش� �Խ����� ��ڸ� ��밡���մϴ�.");
						pc.sendPackets(ssm);
						ssm = null;
						return;
					}
				}
				// if (pc.getLevel() < 50) { //�Խ����ۼ� ��������
				if (board.getNpcId() == 4212014) {
					if (checkdragonkey(pc)) {
						L1ItemInstance dragonkey = pc.getInventory()
								.findItemId(L1ItemId.DRAGON_KEY);
						BoardTable.getInstance().writeDragonKey(pc, dragonkey,
								date, board.getNpcId());
						S_ServerMessage sm = new S_ServerMessage(1567);
						pc.sendPackets(sm);// ��ϵǾ���
						sm = null;
					} else {
						return;
					}
				} else {
					if (board.getNpcId() == 4200020) {
						pc.getInventory().consumeItem(L1ItemId.ADENA, 300);
						BoardTable.getInstance().writeTopicUser(pc, date,
								title, content);
					} else if (board.getNpcId() == 4500302) {
						pc.getInventory().consumeItem(L1ItemId.ADENA, 300);
						BoardTable.getInstance().writeTopicfree(pc, date,
								title, content, board.getNpcId());
					} else {
						pc.getInventory().consumeItem(L1ItemId.ADENA, 300);
						BoardTable.getInstance().writeTopic(pc, date, title,
								content, board.getNpcId());
					}

				}

				// } else {
				// pc.sendPackets(new
				// S_SystemMessage("50���� ���ϴ� �Խ��� �ۼ��� �Ҽ� �����ϴ�."));
				// }
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	private static String currentTime() {
		TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		Calendar cal = Calendar.getInstance(tz);
		int year = cal.get(Calendar.YEAR) - 2000;
		String year2;
		if (year < 10) {
			year2 = "0" + year;
		} else {
			year2 = Integer.toString(year);
		}
		int Month = cal.get(Calendar.MONTH) + 1;
		String Month2 = null;
		if (Month < 10) {
			Month2 = "0" + Month;
		} else {
			Month2 = Integer.toString(Month);
		}
		int date = cal.get(Calendar.DATE);
		String date2 = null;
		if (date < 10) {
			date2 = "0" + date;
		} else {
			date2 = Integer.toString(date);
		}
		return year2 + "/" + Month2 + "/" + date2;
	}

	/**
	 * �巡��Ű�� ����� ������ �Ǵ��Ѵ�.
	 * 
	 * @param pc
	 * @return
	 */
	private boolean checkdragonkey(L1PcInstance pc) {
		if (pc.getInventory().checkItem(L1ItemId.DRAGON_KEY)) {
			if (BoardTable.getInstance().checkExistName(pc.getName(), 4212014)) {
				S_ServerMessage sm = new S_ServerMessage(1568);
				pc.sendPackets(sm);// �̹� ��ϵǾ� �־�
				sm = null;
				return false;
			} else {
				return true;
			}
		} else {
			S_ServerMessage sm = new S_ServerMessage(1566);
			pc.sendPackets(sm);// �巡�� Ű �־�� ��
			sm = null;
			return false;
		}
	}

	@Override
	public String getType() {
		return C_BOARD_WRITE;
	}

}
