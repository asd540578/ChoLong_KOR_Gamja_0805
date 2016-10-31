package l1j.server.server.model.item.function;

import l1j.server.server.ActionCodes;
import l1j.server.server.TimeController.FishingTimeController;
import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.serverpackets.S_Fishing;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;

public class Fishing extends L1ItemInstance {
	private static final long serialVersionUID = 1L;

	public Fishing(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			int itemId = this.getItemId();
			if (pc.isFishing()) {
				pc.sendPackets(new S_SystemMessage("����: ������"), true);
			} else
				startFishing(pc, itemId, packet.readH(), packet.readH());
		}
	}

	private void startFishing(L1PcInstance pc, int itemId, int fishX, int fishY) {
		if (pc.getMapId() != 5302 && pc.getMapId() != 5490) {
			// ���⿡ ���˴븦 ���� �� �����ϴ�.
			pc.sendPackets(new S_ServerMessage(1138), true);
			return;
		}
		int gab = 0;
		int heading = pc.getMoveState().getHeading(); // �� ����: (0.�»�)(1.��)(
														// 2.���)(3.������)(4.����)(5.��)(6.����)(7.��)
		switch (heading) {
		case 0: // ����
			gab = L1WorldMap.getInstance().getMap((short) 5302).getOriginalTile(pc.getX(), pc.getY() - 5);
			break;
		case 1: // ��
			gab = L1WorldMap.getInstance().getMap((short) 5302).getOriginalTile(pc.getX() + 5, pc.getY() - 5);
			break;
		case 2: // ���
			gab = L1WorldMap.getInstance().getMap((short) 5302).getOriginalTile(pc.getX() + 5, pc.getY() - 5);
			break;
		case 3: // ������
			gab = L1WorldMap.getInstance().getMap((short) 5302).getOriginalTile(pc.getX() + 5, pc.getY() + 5);
			break;
		case 4: // ����
			gab = L1WorldMap.getInstance().getMap((short) 5302).getOriginalTile(pc.getX(), pc.getY() + 5);
			break;
		case 5: // ��
			gab = L1WorldMap.getInstance().getMap((short) 5302).getOriginalTile(pc.getX() - 5, pc.getY() + 5);
			break;
		case 6: // ����
			gab = L1WorldMap.getInstance().getMap((short) 5302).getOriginalTile(pc.getX() - 5, pc.getY());
			break;
		case 7: // ��
			gab = L1WorldMap.getInstance().getMap((short) 5302).getOriginalTile(pc.getX() - 5, pc.getY() - 5);
			break;
		}
		int fishGab = pc.getMap().getOriginalTile(fishX, fishY);
		if (gab == 28 && fishGab == 28) {
			if (itemId == 600229 || pc.getInventory().consumeItem(60327, 1)) { // ����
				// if (pc.getInventory().consumeItem(41295, 1)) { // ����
				pc.sendPackets(new S_Fishing(pc.getId(), ActionCodes.ACTION_Fishing, fishX, fishY), true);
				Broadcaster.broadcastPacket(pc, new S_Fishing(pc.getId(), ActionCodes.ACTION_Fishing, fishX, fishY),
						true);
				pc.setFishing(true);
				pc.setFishingItem(this);
				pc.fishX = fishX;
				pc.fishY = fishY;
				// long time = System.currentTimeMillis() + 10000 +
				// _random.nextInt(5) * 1000;
				boolean ck = false;
				int is���� = 0;
				long time = System.currentTimeMillis() + 240000;
				if (itemId == 60334 || itemId == 60478 || itemId == 60479) {// ��
																			// ����
																			// ��ź��
																			// ���˴�
					time = System.currentTimeMillis() + 80000;
					ck = true;
				}
				if (itemId == 600229) {// �� ���� ��ź�� ���˴�
					time = System.currentTimeMillis() + 40000;
					ck = true;
					is���� = 1;
				}
				pc.setFishingTime(time);
				FishingTimeController.getInstance().addMember(pc);
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.FISH_WINDOW, 1, ck, is����), true);
			} else {
				// ���ø� �ϱ� ���ؼ��� ���̰� �ʿ��մϴ�.
				pc.sendPackets(new S_ServerMessage(1137), true);
			}
		} else {
			// ���⿡ ���˴븦 ���� �� �����ϴ�.
			pc.sendPackets(new S_ServerMessage(1138), true);
		}
	}
}
