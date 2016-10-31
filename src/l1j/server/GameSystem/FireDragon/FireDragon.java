package l1j.server.GameSystem.FireDragon;

import java.util.ArrayList;

import l1j.server.GameSystem.GameList;
import l1j.server.GameSystem.Astar.World;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.map.L1V1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_SystemMessage;

public class FireDragon {

	public L1PcInstance _pc;
	public L1NpcInstance _bdeath;
	public L1NpcInstance _wdeath;
	public L1NpcInstance _v;
	private int _mapnum;
	private ArrayList<L1NpcInstance> MonList = new ArrayList<L1NpcInstance>();
	public int _bstcon = -1;
	public int _wstcon = -1;
	public int _vstcon = -1;

	public FireDragon() {
	};

	public int Start(L1PcInstance pc) {
		synchronized (GameList.FDList) {
			try {
				int mapid = GameList.getFireDragonMapId();

				if (mapid == 0) {
					S_SystemMessage sm = new S_SystemMessage("��� ���� ������Դϴ� ����� �ٽ� �̿����ּ���.");
					pc.sendPackets(sm, true);
					return 0;
				}

				boolean ok = GameList.addFireDragon(mapid, this);

				if (!ok) {
					S_SystemMessage sm = new S_SystemMessage("�ʼ����� �߸� �Ǿ����ϴ�. �ٽ� ���� ��û �ٶ��ϴ�.");
					pc.sendPackets(sm, true);
					return 0;
				}

				if (!World.get_map(mapid)) {
					L1WorldMap.getInstance().cloneMap(2600, mapid);
				}

				_pc = pc;
				_mapnum = mapid;

				FireDragonSpawn.getInstance().Spawn(pc, mapid, 0);

				return mapid;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	public void _�߶�_start() {
		try {

			_wstcon = 1;
			GeneralThreadPool.getInstance().execute(new Start_vchat());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * �߶�ī��:���� ���� ����°�?18869 ��������Ʈ:�߶�ī��! ���� �ʸ� ������ �Ǵ±���..18870 �߶�ī��:���� ���� ����
	 * �񰡴�..���� �뿹�� �Ǿ� ��� ���� �Ǹ���..18871 ��������Ʈ:�׷� ���� ���� �й����� �� �ص� ���� �ʴ´�.18872
	 * �߶�ī��:ũũ..�ڽŰ��� ��ġ�±���..18873 �߶�ī��:�׾�� �ΰ�!18874
	 */
	class Start_vchat implements Runnable {
		public Start_vchat() {
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				if (_wstcon == -1)
					return;
				MSG(_wdeath, _wdeath.getId(), "$18869", _bdeath.getX(),
						_bdeath.getY());
				Thread.sleep(1000);
				if (_wstcon == -1)
					return;
				MSG(_wdeath, _wdeath.getId(), "$18646", _bdeath.getX(),
						_bdeath.getY());
				Thread.sleep(1000);
				if (_wstcon == -1)
					return;
				MSG(_wdeath, _wdeath.getId(), "$18647", _bdeath.getX(),
						_bdeath.getY());
				_wstcon = -1;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void MSG(L1NpcInstance npc, int id, String msg, int x, int y) {
		Broadcaster.broadcastPacket(npc, new S_NpcChatPacket(id, msg, x, y),
				true);
	}

	public void _bdeath_stc_exit() {
		_bstcon = -1;
	}

	public void _wdeath_stc_exit() {
		_wstcon = -1;
	}

	public void _wdeath_stc_start() {
		_wstcon = 1;
		GeneralThreadPool.getInstance().execute(new Start_wchat());
	}

	class Start_wchat implements Runnable {
		public Start_wchat() {
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				if (_wstcon == -1)
					return;
				MSG(_wdeath, _wdeath.getId(), "$18645", _bdeath.getX(),_bdeath.getY());
				Thread.sleep(1000);
				if (_wstcon == -1)
					return;
				MSG(_wdeath, _wdeath.getId(), "$18646", _bdeath.getX(),_bdeath.getY());
				Thread.sleep(1000);
				if (_wstcon == -1)
					return;
				MSG(_wdeath, _wdeath.getId(), "$18647", _bdeath.getX(),_bdeath.getY());
				_wstcon = -1;
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	class Start_bchat implements Runnable {
		public Start_bchat() {
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
				if (_bstcon == -1)
					return;
				MSG(_bdeath, _bdeath.getId(), "$18861", _bdeath.getX(),
						_bdeath.getY());
				Thread.sleep(1000);
				if (_bstcon == -1)
					return;
				MSG(_bdeath, _pc.getId(), "$18862", _pc.getX(), _pc.getY());
				Thread.sleep(1000);
				if (_bstcon == -1)
					return;
				MSG(_bdeath, _bdeath.getId(), "$18863", _bdeath.getX(),
						_bdeath.getY());
				Thread.sleep(1000);
				if (_bstcon == -1)
					return;
				MSG(_bdeath, _pc.getId(), "$18864", _pc.getX(), _pc.getY());
				Thread.sleep(1000);
				if (_bstcon == -1)
					return;
				MSG(_bdeath, _bdeath.getId(), "$18865", _bdeath.getX(),
						_bdeath.getY());
				Thread.sleep(1000);
				if (_bstcon == -1)
					return;
				MSG(_bdeath, _pc.getId(), "$18866", _pc.getX(), _pc.getY());
				Thread.sleep(1000);
				if (_bstcon == -1)
					return;
				MSG(_bdeath, _bdeath.getId(), "$18867", _bdeath.getX(),
						_bdeath.getY());
				Thread.sleep(1000);
				if (_bstcon == -1)
					return;
				MSG(_bdeath, _bdeath.getId(), "$18868", _bdeath.getX(),
						_bdeath.getY());
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void Reset() {
		try {
			System.out.println("ȭ����� 1�� �δ� �� ���� : " + _mapnum);
			for (L1NpcInstance mon : MonList) {
				if (mon == null || mon._destroyed || mon.isDead()) {
					continue;
				}
				mon.deleteMe();
			}
			Object_Delete();
			if (MonList.size() > 0)
				MonList.clear();

			L1V1Map m = (L1V1Map) L1WorldMap.getInstance().getMap((short) _mapnum);
			m.reset((L1V1Map) L1WorldMap.getInstance().getMap((short) 2600));
			// World.resetMap(2101, _mapnum);

			GameList.removeFireDragon(_mapnum);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AddMon(L1NpcInstance npc) {
		MonList.add(npc);
	}

	private void Object_Delete() {
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null || ob instanceof L1DollInstance
					|| ob instanceof L1SummonInstance
					|| ob instanceof L1PetInstance)
				continue;
			if (ob instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) ob;
				if (npc._destroyed || npc.isDead())
					continue;
				npc.deleteMe();
			}
		}
		for (L1ItemInstance obj : L1World.getInstance().getAllItem()) {
			if (obj.getMapId() != _mapnum)
				continue;
			L1Inventory groundInventory = L1World.getInstance().getInventory(obj.getX(), obj.getY(), obj.getMapId());
			groundInventory.removeItem(obj);
		}
	}

}