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
import l1j.server.server.model.L1GroundInventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import server.LineageClient;
import server.manager.eva;

public class C_DropItem extends ClientBasePacket {

	private static final String C_DROP_ITEM = "[C] C_DropItem";

	public C_DropItem(byte[] decrypt, LineageClient client) throws Exception {
		super(decrypt);
		try {
			/*
			 * 01 00 00 00 95 82 2f 80 6c b4 17 1b 01 00 00 00 2f ea 03
			 */
			int size = readD();
			int x = 0;
			int y = 0;
			int objectId = 0;
			int count = -1;

			L1PcInstance pc = client.getActiveChar();
			if (pc == null) {
				return;
			}
			if (pc.getOnlineStatus() != 1) {
				pc.sendPackets(new S_Disconnect(), true);
				return;
			}
			if (pc.isGhost())
				return;
			if (isTwoLogin(pc))
				return;
			L1ItemInstance item = null;
			if (size < 1)
				return;
			if (size > 255)
				return;
			for (int i = 0; i < size; i++) {
				x = readH();
				y = readH();
				objectId = readD();
				count = readD();
				if (x == 0 || y == 0 || objectId == 0 || count == -1) {
					return;
				}
				item = pc.getInventory().getItem(objectId);
				if (item != null) {
					if (!item.getItem().isTradable() && !pc.isGm()) {
						// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
						pc.sendPackets(new S_ServerMessage(210, item.getItem()
								.getName()));
						return;
					}
					if (!item.isStackable() && count != 1 && !pc.isGm()) {
						return;
					}

					if (item.getCount() <= 0 && !pc.isGm()) {
						return;
					}

					if (count <= 0 || count > 2000000000 && !pc.isGm()) {
						return;
					}

					if (count > item.getCount()) {
						count = item.getCount();
					}

					if (Math.max(Math.abs(x - pc.getX()),
							Math.abs(y - pc.getY())) > 2) {
						return;
					}

					if (pc.getMapId() == 340 || pc.getMapId() == 350
							|| pc.getMapId() == 360 || pc.getMapId() == 370
							&& !pc.isGm()) {
						S_SystemMessage sm = new S_SystemMessage(
								"����ȿ����� �ٴڿ� �������� ���� �� �����ϴ�.");
						pc.sendPackets(sm, true);
						return;
					}

					if (!pc.isGm() && pc.getLevel() < Config.�������) { 
						pc.sendPackets(new S_SystemMessage("���� " + Config.������� + "���� ���� �� �ֽ��ϴ�."));
						return;
					}
					if (!pc.isGm()
							&& (item.getItemId() == 40308 || (item.getItemId() >= 40033 && item
									.getItemId() <= 40038))) {
						pc.sendPackets(new S_SystemMessage(
								"�ش� �������� �ٴڿ� ���� �� �����ϴ�."));
						return;
					}

					if (item.getItemId() == 49312 || item.getItemId() == 40312) {
						pc.sendPackets(new S_SystemMessage(
								"��������� /��ȯ�� �̿��ϰų� â�� �̿����ּ���."));
						return;
					}

					/*
					 * if (item.getEnchantLevel() >= 1){ // ��æƮ�� �������� �ٴڿ� ��Ӹ���Ű��
					 * pc.sendPackets(new
					 * S_SystemMessage("��þƮ�� �������� �ٴڿ� ���� �� �����ϴ�.")); return; }
					 * 
					 * if (!pc.isGm() && item.getItem().getItemId() == 40074 ||
					 * item.getItem().getItemId() == 140074 ||
					 * item.getItem().getItemId() == 40308 ||
					 * item.getItem().getItemId() == 240074 ||
					 * item.getItem().getItemId() == 40087 ||
					 * item.getItem().getItemId() == 41159 ||
					 * item.getItem().getItemId() == 140087 ||
					 * item.getItem().getItemId() == 240087) {
					 * pc.sendPackets(new
					 * S_SystemMessage("�ش� �������� �ٴڿ� ���� �� �����ϴ�.")); return; }
					 */

					if (item.getBless() >= 128 && !pc.isGm()) {
						S_ServerMessage sm = new S_ServerMessage(210, item
								.getItem().getName());
						pc.sendPackets(sm, true);
						return;
					}

					if (pc.getPetListSize() > 0) {
						L1PetInstance pet = null;
						S_ServerMessage sm1 = new S_ServerMessage(210, item
								.getItem().getName());
						for (Object petObject : pc.getPetList()) {
							if (petObject instanceof L1PetInstance) {
								pet = (L1PetInstance) petObject;
								if (item.getId() == pet.getItemObjId()) {
									// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
									pc.sendPackets(sm1, true);
									return;
								}
							}
						}
					}
					if (pc.getDollListSize() > 0) {
						L1DollInstance ���� = null;
						S_SystemMessage sm = new S_SystemMessage(
								"��ȯ���� ������ ������ �����ϴ�.");
						for (Object ����������Ʈ : pc.getDollList()) {
							if (����������Ʈ instanceof L1DollInstance) {
								���� = (L1DollInstance) ����������Ʈ;
								if (item.getId() == ����.getItemObjId()) {
									// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
									pc.sendPackets(sm, true);
									return;
								}
							}
						}
					}
					if (item.isEquipped()) {
						// \f1������ �� ���� �������̳� ��� �ϰ� �ִ� �������� ���� �� �����ϴ�.
						S_SystemMessage sm2 = new S_SystemMessage(
								"���� �Ұ��� �������̳� �����ϰ� �ִ� �������� ���� �� �����ϴ�.");
						pc.sendPackets(sm2, true);
						return;
					}
					eva.DropLogAppend("�۵�� -" + "["+ pc.getName()+ "] "+ item.getName() +" / " + item.getCount());
					L1GroundInventory gi = L1World.getInstance().getInventory(
							x, y, pc.getMapId());
					if (gi.getSize() > 5) {
						pc.sendPackets(new S_SystemMessage(
								"�� ������ ���̻� �������� �������� �� �����ϴ�."), true);
						return;
					}

					int size1 = 0;
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(pc)) {
						if (obj instanceof L1GroundInventory) {
							L1GroundInventory ginv = (L1GroundInventory) obj;
							size1 += ginv.getSize();
							if (size1 > 150)
								break;
						}
					}

					if (size1 > 150) {
						pc.sendPackets(new S_SystemMessage(
								"�� ȭ�鿡�� ���̻� �������� �������� �� �����ϴ�."), true);
						return;
					}
					/*
					 * int desc = item.getItem().getItemDescId(); int newdesc =
					 * desc + 1; pc.sendPackets(new
					 * S_SystemMessage(desc+" -> "+newdesc));
					 * item.getItem().setItemDescId(newdesc);
					 */
					pc.getInventory().tradeItem(item, count, gi);
				}

			}
		} catch (Exception e) {

		} finally {
			clear();
		}

	}

	@Override
	public String getType() {
		return C_DROP_ITEM;
	}

	private boolean isTwoLogin(L1PcInstance c) {
		boolean bool = false;
		for (L1PcInstance target : L1World.getInstance().getAllPlayers()) {
			// ����PC �� ����
			if (target == null)
				continue;
			if (target.noPlayerCK)
				continue;
			if (target.�����)
				continue;
			//
			if (c.getId() != target.getId() && !target.isPrivateShop()) {
				if (c.getNetConnection()
						.getAccountName()
						.equalsIgnoreCase(
								target.getNetConnection().getAccountName())) {
					bool = true;
					break;
				}
			}
		}
		return bool;
	}
}
