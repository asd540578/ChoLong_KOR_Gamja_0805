/**
 * ���� ������� �������� ���̵�
 * ���� �� �� ���� ���� Ŭ����
 * by. ����
 */
package l1j.server.GameSystem.Lind;

import java.util.ArrayList;

import javolution.util.FastMap;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.L1SpawnUtil;

public class LindRaid {

	private final FastMap<L1Map, Boolean> mapList;
	private static LindRaid _instance;

	public static LindRaid get() {
		if (_instance == null)
			_instance = new LindRaid();
		return _instance;
	}

	private LindRaid() {
		mapList = new FastMap<L1Map, Boolean>().shared();
		for (byte i = 0; i < 6; i++) {
			short mapid = (short) (10000 + i);
			L1WorldMap.getInstance().cloneMap(1017, mapid);
			// World.cloneMap(1017, mapid);
			mapList.put(L1WorldMap.getInstance().getMap(mapid), false);
		}
		LindThread.get();
	//	System.out.println(":: LindRaid Lodading Compleate");
	}
	
	public synchronized boolean start(L1PcInstance pc) {
		L1Map map = mapCheck();
		if (map == null) {
			S_SystemMessage sm = new S_SystemMessage(
					"�Ƶ����� �� ���̻� ���� ��Ż�� ��ȯ�� �� �����ϴ�.");
			pc.sendPackets(sm, true);
			return false;
		}
		ArrayList<L1Object> list = L1World.getInstance().getVisibleObjects(pc,
				0);
		if (list.size() > 0) {
			pc.sendPackets(new S_SystemMessage("�� ��ġ�� ���� ��Ż�� ��ȯ�� �� �����ϴ�."),
					true);
			return false;
		}
		L1World.getInstance()
				.broadcastServerMessage(
						"��ö ��� ������: ��...�巡���� ���¢���� ������� �鸮��. �ʽ� ������ �巡�� ��Ż�� �� ���� Ȯ���Ͽ�! �غ�� �巡�� �����̾�� ������ �ູ��!");
		// pc.getInventory().consumeItem(430116, 1);
		mapList.put(map, true);

		Lind lind = new Lind(map);
		lind.portal = L1SpawnUtil.spawn2(pc.getX(), pc.getY(), pc.getMapId(),
				100011, 0, 7200 * 1000, map.getId());
		L1FieldObjectInstance foi = (L1FieldObjectInstance) lind.portal;
		foi.Potal_Open_pcid = pc.getId();

		LindThread.get().add(lind);
		return true;
	}

	private L1Map mapCheck() {
		for (FastMap.Entry<L1Map, Boolean> e = mapList.head(), mapEnd = mapList
				.tail(); (e = e.getNext()) != mapEnd;) {
			if (!e.getValue()) {
				return e.getKey();
			}
		}
		return null;
	}

	public void quit(L1Map map) {
		mapList.put(map, false);
	}

	public void in(L1PcInstance pc) {
		Lind lind = LindThread.get().getLind(pc.dragonmapid);
		if (lind == null)
			return;
		pc.dx = 32735;
		pc.dy = 32850;
		pc.dm = (short) pc.dragonmapid;
		pc.dh = 4;
		pc.setTelType(7);
		pc.sendPackets(new S_SabuTell(pc));
		// L1Teleport.teleport(pc, 32674, 32926, (short) pc.dragonmapid, 4,
		// true);
		lind.addMember(pc);
	}
}
