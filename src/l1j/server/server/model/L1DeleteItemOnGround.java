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
package l1j.server.server.model;

import java.util.List;
import java.util.logging.Logger;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.datatables.UBTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

public class L1DeleteItemOnGround {
	private DeleteTimer _deleteTimer;

	private static final Logger _log = Logger
			.getLogger(L1DeleteItemOnGround.class.getName());

	public L1DeleteItemOnGround() {
	}

	private class DeleteTimer extends Thread {
		public DeleteTimer() {
		}

		@Override
		public void run() {
			try {
				int time = (1000 * 60) * 19;
				while (true) {
					try {
						Thread.sleep(time);
					} catch (Exception exception) {
						_log.warning("L1DeleteItemOnGround error: " + exception);
						break;
					}
					// L1World.getInstance().broadcastServerMessage("����� û�� : ���� �ʻ��� �������� 1�� �Ŀ� ���� �˴ϴ�.");
					try {
						Thread.sleep((1000 * 60) - 5000);
					} catch (Exception exception) {
						_log.warning("L1DeleteItemOnGround error: " + exception);
						break;
					}
					// L1World.getInstance().broadcastServerMessage("����� û�� : ���� �ʻ��� �������� 5�� �Ŀ� ���� �˴ϴ�.");
					try {
						Thread.sleep(5000);
					} catch (Exception exception) {
						_log.warning("L1DeleteItemOnGround error: " + exception);
						break;
					}
					deleteItem();
					// #################### ���� ���� �߰� #################
					// L1World.getInstance().broadcastServerMessage("����� û�� : ���� �ʻ��� �������� �ڵ� û�� �Ǿ����ϴ�.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void initialize() {
		_deleteTimer = new DeleteTimer();
		_deleteTimer.start();
		// GeneralThreadPool.getInstance().execute(_deleteTimer); // Ÿ�̸� ����
	}

	private void deleteItem() {
		for (L1Object obj : L1World.getInstance().getAllItem()) {
			if (!(obj instanceof L1ItemInstance)) {
				continue;
			}
			L1ItemInstance item = (L1ItemInstance) obj;
			if (item.getItemOwner() == null
					|| !(item.getItemOwner() instanceof L1RobotInstance)) {
				if (item.getX() == 0 && item.getY() == 0) { // ������� �������� �ƴϰ�,
															// �������� ������
					continue;
				}
			}
			if (item.getItem().getItemId() == 40515) { // ������ ��
				continue;
			}
			if (L1HouseLocation.isInHouse(item.getX(), item.getY(),
					item.getMapId())) { // ����Ʈ��
				continue;
			}
			// ���Ѵ����� ������� ������ �Ȼ������ by �ƽ�����
			boolean ck = false;
			if (item.getMapId() >= 88 && item.getMapId() <= 98) {
				for (L1UltimateBattle ub : UBTable.getInstance().getAllUb()) {
					if (item.getMapId() == ub.getMapId() && ub.isNowUb()) {
						ck = true;
						break;
					}
				}
			}
			if (ck)
				continue;
			List<L1PcInstance> players = null;
			players = L1World.getInstance().getVisiblePlayer(item, 3);
			if (players.size() < 1) { // ���� �������� �÷��̾ ������ ����
				L1Inventory groundInventory = L1World
						.getInstance()
						.getInventory(item.getX(), item.getY(), item.getMapId());
				groundInventory.removeItem(item);
			}
		}
	}
}
