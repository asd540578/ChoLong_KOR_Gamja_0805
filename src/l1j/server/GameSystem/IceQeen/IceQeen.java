package l1j.server.GameSystem.IceQeen;

import java.util.ArrayList;
import java.util.Random;

import l1j.server.GameSystem.GameList;
import l1j.server.GameSystem.Astar.World;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.map.L1V1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.serverpackets.S_Door;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.L1SpawnUtil;

public class IceQeen {

	public L1PcInstance _pc;
	private int _mapnum;

	private int ����â = 90023;
	private int ����Ȱ = 90022;
	private int �ó� = 90026;
	private int ���̽��� = 90021;
	private int ���̽��� = 90024;
	private int ��Ƽ = 90027;
	private int �����ǿ� = 90025;

	// private int MonsterCount = 200;
	private Random random = new Random(System.nanoTime());
	private ArrayList<L1DoorInstance> DoorList = new ArrayList<L1DoorInstance>();
	private ArrayList<L1NpcInstance> MonList = new ArrayList<L1NpcInstance>();
	private MonsterCheckThread mct;

	public IceQeen() {
	};

	public int Start(L1PcInstance pc, int type) {
		synchronized (GameList.IQList) {
			try {
				int mapid = GameList.getIceQueenMapId();

				if (mapid == 0) {
					S_SystemMessage sm = new S_SystemMessage("��� ���� ������Դϴ� ����� �ٽ� �̿����ּ���.");
					pc.sendPackets(sm, true);
					// System.out.println("0");
					return 0;
				}

				boolean ok = GameList.addIceQeen(mapid, this);

				if (!ok) {
					S_SystemMessage sm = new S_SystemMessage("�ʼ����� �߸� �Ǿ����ϴ�. �ٽ� ���� ��û �ٶ��ϴ�.");
					pc.sendPackets(sm, true);
					// System.out.println("1");
					return 0;
				}

				if (!World.get_map(mapid)) {
					L1WorldMap.getInstance().cloneMap(2101, mapid);
				}

				// if(_pc != null){
				// S_SystemMessage sm = new
				// S_SystemMessage("�ʼ����� �߸� �Ǿ����ϴ�. �ٽ� ���� ��û �ٶ��ϴ�.");
				// pc.sendPackets(sm, true);
				// return 0;
				// }

				_pc = pc;
				_mapnum = mapid;

				GeneralThreadPool.getInstance().execute(new start_spawn(mapid, type));

				return mapid;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}

	class start_spawn implements Runnable {

		private int mapid;
		private int type;

		public start_spawn(int m, int t) {
			mapid = m;
			type = t;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);

				IceQeenSpawn.getInstance().Spawn(mapid, 0);
				IceQeenSpawn.getInstance().Spawn(mapid, type);

				int idlist[] = new int[6];
				if (type == 1) {
					idlist[0] = ���̽���;
					idlist[1] = ����â;
					idlist[2] = ����Ȱ;
					idlist[3] = ��Ƽ;
					idlist[4] = �����ǿ�;
					idlist[5] = ���̽���;
				} else {
					idlist[0] = ���̽���;
					idlist[1] = ����â;
					idlist[2] = �ó�;
					idlist[3] = ��Ƽ;
					idlist[4] = �����ǿ�;
					idlist[5] = ����Ȱ;
				}
				/*
				 * �� ��° ���������� ù ��° ������ �����ߴ� ���͵�� �����ǿ�, ���� ������ ������(Ȱ)�� �߰��� �����Ѵ�.
				 * �� �߿��� �����ǿ��� ���� �ð� ���� HP�� ���ҽ�Ű�� ���� ����ϱ� ������ ��Ʈ�� �ٱ⳪ �ص����� �غ��صδ�
				 * ���� ����.
				 * 
				 * ���� - 1������ = �ó�,��,����â,��Ƽ 2���������� = �����ǿ�,����Ȱ �߰� ��� - 1������ =
				 * ��,����â,��Ƽ,���̽���,����Ȱ 2���������� = ���� �߰�
				 */
				// type 1 : ��������(��������) / type 2 : ��ȣ����(���̽�����)
				if (type == 1) {

				} else {

				}
				int ranid1 = 0;
				int ranid = 0;
				int ranx = 0;
				int rany = 0;
				for (int i = 0; i < 16; i++) {
					try {
						ranid1 = random.nextInt(4);
						ranid = random.nextInt(6);
						ranx = random.nextInt(10);
						rany = random.nextInt(10);
						// 1���� ����
						L1SpawnUtil.spawn2(32760 + ranx, 32813 + rany, (short) mapid, idlist[ranid1], 5, 0, 0);
						// 2���� ����
						L1SpawnUtil.spawn2(32832 + ranx, 32801 + rany, (short) mapid, idlist[ranid], 5, 0, 0);
						// 3���� ����
						L1SpawnUtil.spawn2(32843 + ranx, 32847 + rany, (short) mapid, idlist[ranid], 5, 0, 0);
						// 4���� ����
						L1SpawnUtil.spawn2(32763 + ranx, 32882 + rany, (short) mapid, idlist[ranid], 5, 0, 0);
						// 5���� ����
						L1SpawnUtil.spawn2(32820 + ranx, 32916 + rany, (short) mapid, idlist[ranid], 5, 0, 0);
					} catch (Exception e) {
						// ���⼭ null �� ���� �ߴ°� ������ NPC�� ���°� ����.üũ�ٶ�
						e.printStackTrace();
					}
				}
				idlist = null;

				mct = new MonsterCheckThread();
				GeneralThreadPool.getInstance().schedule(mct, 1000);
			} catch (InterruptedException e1) {
				// TODO �ڵ� ������ catch ���
				e1.printStackTrace();
			}
			// iqt = new IceQueenTimeCheck();
			// GeneralThreadPool.getInstance().schedule(iqt, 1000);
		}
	}

	class IceQueenTimeCheck implements Runnable {

		private long time = 0;

		public IceQueenTimeCheck() {
			time = System.currentTimeMillis() + 60000 * 10;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			try {
				if (_pc == null || L1World.getInstance().getPlayer(_pc.getName()) == null) {
					return;
				}
				if (_pc.getMapId() != _mapnum) {
					return;
				}
				if (System.currentTimeMillis() >= time) {
					_pc.dx = 34058;
					_pc.dy = 32281;
					_pc.dm = (short) 4;
					_pc.dh = 5;
					_pc.setTelType(7);
					_pc.sendPackets(new S_SabuTell(_pc), true);
				} else
					GeneralThreadPool.getInstance().schedule(this, 3000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	class MonsterCheckThread implements Runnable {

		private boolean on = true;
		private boolean door1 = false, door2 = false, door3 = false, door4 = false, door5 = false;

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			if (!on)
				return;
			try {
				boolean ck1 = false, ck2 = false, ck3 = false, ck4 = false, ck5 = false;
				;
				for (L1Object obj : L1World.getInstance().getVisibleObjects(_mapnum).values()) {
					if (obj != null && obj instanceof L1MonsterInstance) {
						L1MonsterInstance mon = (L1MonsterInstance) obj;
						if (mon._destroyed || mon.isDead())
							continue;
						if (obj.getX() >= 32720 && obj.getX() <= 32784 && obj.getY() >= 32792 && obj.getY() <= 32847) {
							ck1 = true;
						} else if (obj.getX() >= 32787 && obj.getX() <= 32851 && obj.getY() >= 32788
								&& obj.getY() <= 32827) {
							ck2 = true;
						} else if ((obj.getX() >= 32853 && obj.getX() <= 32871 && obj.getY() >= 32798
								&& obj.getY() <= 32835)
								|| (obj.getX() >= 32824 && obj.getX() <= 32870 && obj.getY() >= 32834
										&& obj.getY() <= 32873)) {
							ck3 = true;
						} else if ((obj.getX() >= 32788 && obj.getX() <= 32822 && obj.getY() >= 32846
								&& obj.getY() <= 32894)
								|| (obj.getX() >= 32747 && obj.getX() <= 32790 && obj.getY() >= 32865
										&& obj.getY() <= 32915)) {
							ck4 = true;
						} else if (obj.getX() >= 32747 && obj.getX() <= 32790 && obj.getY() >= 32865
								&& obj.getY() <= 32915) {
							ck4 = true;
						} else if (obj.getX() >= 32750 && obj.getX() <= 32866 && obj.getY() >= 32898
								&& obj.getY() <= 32944) {
							ck5 = true;
						}
					}
				}
				if (!door1 && !ck1) {
					door1 = true;
					DoorOpen(4040000);
				} else if (!door2 && !ck2) {
					door2 = true;
					DoorOpen(4040001);
				} else if (!door3 && !ck3) {
					door3 = true;
					DoorOpen(4040002);
				} else if (!door4 && !ck4) {
					door4 = true;
					DoorOpen(4040003);
				} else if (!door5 && !ck5) {
					door5 = true;
					DoorOpen(4040004);
				}
				if (door5)
					return;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (!door5)
					GeneralThreadPool.getInstance().schedule(this, 3000);
			}
		}

		public void off() {
			on = false;
		}
	}

	/*
	 * public synchronized void MonsterCount(){ MonsterCount--; if(MonsterCount
	 * < 1){ DoorOpen(4040004); }else if(MonsterCount < 41){ DoorOpen(4040003);
	 * }else if(MonsterCount < 81){ DoorOpen(4040002); }else if(MonsterCount <
	 * 121){ DoorOpen(4040001); }else if(MonsterCount < 161){ DoorOpen(4040000);
	 * } }
	 */

	private void ����Ŷ(L1DoorInstance door) {
		S_Door packet = new S_Door(door);
		_pc.sendPackets(packet);
	}

	private void DoorOpen(int id) {
		try {
			for (L1DoorInstance door : DoorList) {
				if (door.getNpcId() == id) {
					door.isPassibleDoor(true);
					door.setPassable(0);
					if (_pc != null) {
						����Ŷ(door);
					}
					door.deleteMe();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AddDoor(L1DoorInstance door) {
		DoorList.add(door);
	}

	public void AddMon(L1NpcInstance npc) {
		MonList.add(npc);
	}

	public int MonSize() {
		return MonList.size();
	}

	public void Reset() {
		try {
			System.out.println("��� 1�� �δ� �� ���� : " + _mapnum);
			if (mct != null)
				mct.off();
			mct = null;
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
			m.reset((L1V1Map) L1WorldMap.getInstance().getMap((short) 2101));
			// World.resetMap(2101, _mapnum);

			GameList.removeIceQeen(_mapnum);

			_pc = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Object_Delete() {

		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum).values()) {
			if (ob == null || ob instanceof L1DollInstance || ob instanceof L1SummonInstance
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
			// groundInventory.deleteItem(obj);
			// L1World.getInstance().removeVisibleObject(obj);
			// L1World.getInstance().removeObject(obj);
		}
	}

}