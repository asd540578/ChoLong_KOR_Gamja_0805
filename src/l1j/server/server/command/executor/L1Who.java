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

import java.util.Collection;
import java.util.logging.Logger;

import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_WhoAmount;
import server.system.autoshop.AutoShopManager;

public class L1Who implements L1CommandExecutor {
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(L1Who.class.getName());

	private L1Who() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1Who();
	}

	@Override
	public void execute(L1PcInstance pc, String cmdName, String arg) {
		try {
			int AutoShopUser = AutoShopManager.getInstance()
					.getShopPlayerCount();
			int CalcUser = L1UserCalc.getClacUser();
			Collection<L1PcInstance> players = L1World.getInstance()
					.getAllPlayers();
			String amount = String.valueOf(players.size());
			S_WhoAmount s_whoamount = new S_WhoAmount(amount);
			pc.sendPackets(s_whoamount);
			pc.sendPackets(new S_SystemMessage("���λ��� : " + AutoShopUser));
			pc.sendPackets(new S_SystemMessage("��Ƣ�� : " + CalcUser));
			int �� = 0;
			int ���� = 0;
			for (L1PcInstance each : players) {
				if (!each.isPrivateShop()) {
					if (each.noPlayerCK)
						��++;
					else
						����++;
				}
			}
			pc.sendPackets(new S_SystemMessage("���� ���� : " + ����));
			pc.sendPackets(new S_SystemMessage("�κ� : " + ��));

			// �¶����� �÷��̾� ����Ʈ�� ǥ��
			if (arg.equalsIgnoreCase("��ü")) {
				StringBuffer gmList = new StringBuffer();
				StringBuffer playList = new StringBuffer();
				StringBuffer noplayList = new StringBuffer();
				StringBuffer shopList = new StringBuffer();

				int countGM = 0, nocountPlayer = 0, countPlayer = 0, countShop = 0;

				for (L1PcInstance each : players) {
					if (each.isGm()) {
						gmList.append(each.getName() + ", ");
						countGM++;
						continue;
					}
					if (!each.isPrivateShop()) {
						if (each.noPlayerCK) {
							noplayList.append(each.getName() + ", ");
							nocountPlayer++;
							continue;
						} else {
							playList.append(each.getName() + ", ");
							countPlayer++;
							continue;
						}
					}
					if (each.isPrivateShop()) {
						shopList.append(each.getName() + ", ");
						countShop++;
					}
				}
				if (gmList.length() > 0) {
					pc.sendPackets(new S_SystemMessage("-- ��� (" + countGM
							+ "��)"));
					pc.sendPackets(new S_SystemMessage(gmList.toString()));
				}
				if (noplayList.length() > 0) {
					pc.sendPackets(new S_SystemMessage("-- ��� ("
							+ nocountPlayer + "��)"));
					pc.sendPackets(new S_SystemMessage(noplayList.toString()));
				}
				if (playList.length() > 0) {
					pc.sendPackets(new S_SystemMessage("-- �÷��̾� ("
							+ countPlayer + "��)"));
					pc.sendPackets(new S_SystemMessage(playList.toString()));
				}

				if (shopList.length() > 0) {
					pc.sendPackets(new S_SystemMessage("-- ���λ��� (" + countShop
							+ "��)"));
					pc.sendPackets(new S_SystemMessage(shopList.toString()));
				}
			} else if (arg.equalsIgnoreCase("�κ�")) {
				StringBuffer noplayList = new StringBuffer();
				for (L1PcInstance each : players) {
					if (!each.isPrivateShop()) {
						if (each.noPlayerCK) {
							noplayList.append(each.getName() + ", ");
						}
					}
				}
				if (noplayList.length() > 0) {
					pc.sendPackets(new S_SystemMessage("----------"));
					pc.sendPackets(new S_SystemMessage(noplayList.toString()));
				}
			} else if (arg.equalsIgnoreCase("������")) {
				StringBuffer playList = new StringBuffer();
				for (L1PcInstance each : players) {
					if (!each.isPrivateShop()) {
						if (!each.noPlayerCK) {
							playList.append(each.getName() + ", ");
						}
					}
				}
				if (playList.length() > 0) {
					pc.sendPackets(new S_SystemMessage("----------"));
					pc.sendPackets(new S_SystemMessage(playList.toString()));
				}
			} else {
				if (arg.equalsIgnoreCase(""))
					return;
				L1PcInstance pp = L1World.getInstance().getPlayer(arg);
				if (pp == null || pp.getNetConnection() == null) {
					pc.sendPackets(new S_SystemMessage(
							"�ش� ������ �������� �ƴϰų� �κ�, ���λ����Դϴ�."));
					return;
				}
				String ip = pp.getNetConnection().getIp();
				ip = ip.substring(0, ip.lastIndexOf(".") + 1);
				StringBuffer playList = new StringBuffer();
				for (L1PcInstance each : players) {
					if (!each.isPrivateShop()
							&& each.getNetConnection() != null) {
						if (!each.noPlayerCK) {
							if (each.getNetConnection().getIp().indexOf(ip) >= 0) {
								playList.append(each.getName() + ", ");
							}
						}
					}
				}
				if (playList.length() > 0) {
					if (ip.startsWith("183.111.9")) {// 211.233.72
						pc.sendPackets(new S_SystemMessage("���Ͻ� �����Ƿ� ������ �Դϴ�."));
					} else {
						pc.sendPackets(new S_SystemMessage("IP: " + ip
								+ " �뿪 ----------"));
						pc.sendPackets(new S_SystemMessage(playList.toString()));
					}
				} else {
					pc.sendPackets(new S_SystemMessage(
							"�ش� IP �뿪 �߰� ���� ���� ----------"));
				}
			}
			players = null;
		} catch (Exception e) {
			pc.sendPackets(new S_SystemMessage(".���� [��ü,�κ�,������] ��� �Է��� �ּ���. "));
		}
	}
}
