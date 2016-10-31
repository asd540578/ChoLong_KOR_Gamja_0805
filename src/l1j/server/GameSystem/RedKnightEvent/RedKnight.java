package l1j.server.GameSystem.RedKnightEvent;

import java.util.Random;

import javolution.util.FastTable;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.utils.L1SpawnUtil;

public class RedKnight implements Runnable {

	private static Random rnd = new Random(System.currentTimeMillis());
	private int _mapnum = 0;
	private int step = 0;
	private int READY_TIME = 4;
	private int ROUND_1_STEP = 8;
	private int ROUND_2_STEP = 2;
	private int ROUND_3_STEP = 2;
	private int END_TIME = 13;
	private static final int SPAWN = 0;
	private static final int READY = 1;
	private static final int MEMBER_CHECK = 2;
	private static final int ROUND_1 = 3;
	private static final int ROUND_2 = 4;
	private static final int ROUND_3 = 5;
	private static final int END = 6;
	private static final int TIME_OVER = 7;

	private FastTable<L1NpcInstance> �ٸ�1 = null;
	private FastTable<L1NpcInstance> �ٸ�2 = null;
	private FastTable<L1NpcInstance> �ٸ�3 = null;

	private FastTable<L1NpcInstance> ��� = null;
	private L1NpcInstance ���� = null;

	private L1NpcInstance �������� = null;
	private L1NpcInstance ��������1 = null;
	private L1NpcInstance ��������2 = null;

	private boolean on = true;

	public RedKnight(int mapid) {
		_mapnum = mapid;
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			private int TIMER = 90;

			@Override
			public void run() {
				// TODO �ڵ� ������ �޼ҵ� ����
				try {
					if (!on)
						return;
					if (TIMER == 5) {
						GREEN_MSG("����: ���θ�����. 5�� �Ŀ� ���� �������� ������ �� �����ϴ�. �� ���� ������ �Ϸ����� ���ϸ� ������ �����ؾ� �մϴ�.");
					} else if (TIMER == 4) {
						GREEN_MSG("����: ���θ�����. 4�� �Ŀ� ���� �������� ������ �� �����ϴ�.");
					} else if (TIMER == 3) {
						GREEN_MSG("����: ���θ�����. 3�� �Ŀ� ���� �������� ������ �� �����ϴ�.");
					} else if (TIMER == 2) {
						GREEN_MSG("����: ���θ�����. 2�� �Ŀ� ���� �������� ������ �� �����ϴ�.");
					} else if (TIMER == 1) {
						GREEN_MSG("����: ���θ�����. 1�� �Ŀ� ���� �������� ������ �� �����ϴ�.");
					} else if (TIMER == 0) {
						GREEN_MSG("����: ���� �������� �� �ձ��� �����߽��ϴ�. �� �̻� ��ü�� �� ������ ������ �����ϰڽ��ϴ�.");
						step = TIME_OVER;
						return;
					}
					TIMER--;
				} catch (Exception e) {
				}
				GeneralThreadPool.getInstance().schedule(this, 60000);
			}
		}, 60000);
	}

	@Override
	public void run() {
		// TODO �ڵ� ������ �޼ҵ� ����
		int sleep = 1;
		try {
			switch (step) {
			case SPAWN:
				�ٸ�1 = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 0);
				�ٸ�2 = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 1);
				�ٸ�3 = RedKnightSpawn.getInstance().fillSpawnTable(_mapnum, 2);
				sleep = 60;
				step++;
				break;
			case READY:
				if (READY_TIME == 4)
					GREEN_MSG("����: 4�� �� ����� �����Դϴ�.");
				else
					GREEN_MSG("����: " + READY_TIME
							+ "�� �� ����� �����Դϴ�. ���� �ο��� 10�� �̸��̸� ������ ��ҵ˴ϴ�.");
				sleep = 60;
				READY_TIME--;
				if (READY_TIME <= 0)
					step++;
				break;
			case MEMBER_CHECK:
				int count = 0;
				for (L1Object ob : L1World.getInstance()
						.getVisibleObjects(_mapnum).values()) {
					if (ob == null)
						continue;
					if (ob instanceof L1PcInstance)
						count++;
				}
				if (count < 1) {
					GREEN_MSG("����: ���� �ο� �������� �̹� ������ ��ҵǾ����ϴ�. ���� ������ ��ٷ� �ּ���.");
					Thread.sleep(3000);
					HOME_TELEPORT();
					Object_Delete();
					return;
				} else {
					GREEN_MSG("����: ������ �ռ� ����������� �����е��� �ݷ��Ϸ� ���� �����Դϴ�.");
					sleep = 5;
				}
				step++;
				break;
			case ROUND_1:
				sleep = 5;
				if (ROUND_1_STEP == 8) {
					��������1 = L1SpawnUtil.spawn4(32772, 32814, (short) _mapnum,
							4, 100660, 0, 0, 0);
					��������2 = L1SpawnUtil.spawn4(32768, 32814, (short) _mapnum,
							4, 100660, 0, 0, 0);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(��������1, 4, 5), 50);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(��������2, 4, 5), 50);
				} else if (ROUND_1_STEP == 7) {
					SHOUT_MSG(��������2, "���� ����: ������ �ֽʽÿ�!! ����������� ���ʴϴ�.");
					�������� = L1SpawnUtil.spawn4(32770, 32814, (short) _mapnum, 4,
							100659, 0, 0, 0);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(��������, 4, 7), 50);
				} else if (ROUND_1_STEP == 6) {
					SHOUT_MSG(��������,
							"��������: �̷��� �츮 ���� ������ ���췯 ���� �״���� ��� ġ���ϳ�.");
					sleep = 10;
				} else if (ROUND_1_STEP == 5) {
					SHOUT_MSG(��������,
							"��������: ���Ű� �ΰ����� ���� �̹� ���� ��������� �̹� �ӹ��� ���� �߿��ϴٳ�.");
					sleep = 10;
				} else if (ROUND_1_STEP == 4) {
					SHOUT_MSG(��������,
							"��������: ��.. ���� ���� ���� �ִ� ��å�� ����Ʈ���״� ������ ���ư��� �ܼ��� ã�ƿ���.");
					sleep = 10;
				} else if (ROUND_1_STEP == 3) {
					SHOUT_MSG(��������, "��������: �״���� �ϰ� �� ���ư��� ���� Żȯ�� �غ� �ϰڳ�.");
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(��������1, 0, 5), 2500);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(��������2, 0, 5), 2500);
					GeneralThreadPool.getInstance().schedule(
							new NpcMove(��������, 0, 7), 50);
					sleep = 10;
				} else if (ROUND_1_STEP == 2) {
					�ٸ�����(�ٸ�1);
					��� = RedKnightSpawn.getInstance()
							.fillSpawnTable(_mapnum, 3);
					GREEN_MSG("����: ù ��° ��å�� �ı��ƴ�. ��� �����϶�!!");
				} else if (ROUND_1_STEP == 1) {
					if (���üũ()) {
						���� = L1SpawnUtil.spawn2(32770, 32923, (short) _mapnum,
								100653, 3, 0, 0);// ��̾ƽ�
						GREEN_MSG("�δ���: ���⸦ ��� ã�Ƴ���? �츮 ���� ������ �ǵ帰 �˰��� ġ���� ���ָ�!!");
					} else {
						GeneralThreadPool.getInstance()
								.schedule(this, 5 * 1000);
						return;
					}
				} else if (ROUND_1_STEP == 0) {
					if (����üũ())
						step++;
				}
				if (ROUND_1_STEP != 0)
					ROUND_1_STEP--;
				break;
			case ROUND_2:
				sleep = 5;
				if (ROUND_2_STEP == 2) {
					�ٸ�����(�ٸ�2);
					��� = RedKnightSpawn.getInstance()
							.fillSpawnTable(_mapnum, 5);
					GREEN_MSG("����: �� ��° ��å�� �ı��ƴ�. ����~������~~!!");
				} else if (ROUND_2_STEP == 1) {
					if (���üũ()) {
						���� = L1SpawnUtil.spawn2(32771, 33009, (short) _mapnum,
								100654, 3, 0, 0);// �ٷε�
						GREEN_MSG("�δ���: ������� �Դٴ� ���� ���� �δ븦 ����Ʈ�ȴٴ� ���ε�... ���� �� ����� �ƴϱ�. ���� ����� �ָ�!!");
					} else {
						GeneralThreadPool.getInstance()
								.schedule(this, 5 * 1000);
						return;
					}
				} else if (ROUND_2_STEP == 0) {
					if (����üũ())
						step++;
				}
				if (ROUND_2_STEP != 0)
					ROUND_2_STEP--;
				break;
			case ROUND_3:
				sleep = 5;
				if (ROUND_3_STEP == 2) {
					�ٸ�����(�ٸ�3);
					��� = RedKnightSpawn.getInstance()
							.fillSpawnTable(_mapnum, 7);
					GREEN_MSG("����: ������ ��å�� �ı��ƴ�. ���� �� ���� ����~~!!");
				} else if (ROUND_3_STEP == 1) {
					if (���üũ()) {
						���� = L1SpawnUtil.spawn2(32769, 33093, (short) _mapnum,
								100655, 3, 0, 0);// �׸�����
						GREEN_MSG("�δ���: �þ�Ծ �ÿ����� ���!! �� ���� �������� �ʰڴ�!!");
					} else {
						GeneralThreadPool.getInstance()
								.schedule(this, 5 * 1000);
						return;
					}
				} else if (ROUND_3_STEP == 0) {
					if (����üũ())
						step++;
				}
				if (ROUND_3_STEP != 0)
					ROUND_3_STEP--;
				break;
			case END:
				if (END_TIME <= 0) {
					HOME_TELEPORT();
					Object_Delete();
					return;
				} else if (END_TIME == 13) {
					GREEN_MSG("����: �̹� ������ ���������� ����ģ �Ϳ� ���� ����������� �⻵�Ͻ� ���Դϴ�.");
					sleep = 3;
				} else if (END_TIME == 12) {
					GREEN_MSG("����: ȹ���� �ܼ� 3������ '����'���� ������ �ֽñ� �ٶ��ϴ�.");
					sleep = 3;
				} else if (END_TIME == 11) {
					GREEN_MSG("�ý��� �޽���: 1���� ������ ���� �ڷ���Ʈ �˴ϴ�.");
					sleep = 50;
				} else {
					GREEN_MSG("�ý��� �޽���: " + END_TIME + "��");
				}
				END_TIME--;
				break;
			case TIME_OVER:
				HOME_TELEPORT();
				Object_Delete();
				return;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GeneralThreadPool.getInstance().schedule(this, sleep * 1000);
	}

	private boolean ����üũ() {
		if (���� == null || ����._destroyed || ����.isDead())
			return true;
		return false;
	}

	private boolean ���üũ() {
		if (��� == null || ���.size() <= 0)
			return true;
		for (L1NpcInstance npc : ���) {
			if (npc == null || npc._destroyed || npc.isDead())
				continue;
			// System.out.println(npc.getX()+" > "+npc.getY());
			return false;
		}
		return true;
	}

	private void �ٸ�����(FastTable<L1NpcInstance> list) {
		// TODO �ڵ� ������ �޼ҵ� ����
		if (list == null || list.size() <= 0)
			return;
		for (L1NpcInstance npc : list) {
			if (npc == null || npc._destroyed)
				return;
			npc.getMap().setPassable(npc.getLocation(), true);
			npc.deleteMe();
		}
	}

	private void GREEN_MSG(String msg) {
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null)
				continue;
			if (ob instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) ob;
				pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, msg));
			}
		}
	}

	private void SHOUT_MSG(L1NpcInstance npc, String msg) {
		Broadcaster
				.broadcastPacket(npc, new S_NpcChatPacket(npc, msg, 2), true);
		/*
		 * for(L1Object ob :
		 * L1World.getInstance().getVisibleObjects(_mapnum).values()){ if(ob ==
		 * null) continue; if(ob instanceof L1PcInstance){ L1PcInstance pc =
		 * (L1PcInstance) ob; pc.sendPackets(new S_SystemMessage(msg, true)); }
		 * }
		 */
	}

	private void HOME_TELEPORT() {
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null)
				continue;
			if (ob instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) ob;
				L1Teleport.teleport(pc, 33436 + rnd.nextInt(12),
						32795 + rnd.nextInt(14), (short) 4, 5, true);
			}
		}
	}

	private void Object_Delete() {
		on = false;
		for (L1Object ob : L1World.getInstance().getVisibleObjects(_mapnum)
				.values()) {
			if (ob == null || ob instanceof L1DollInstance
					|| ob instanceof L1SummonInstance
					|| ob instanceof L1PetInstance)
				continue;
			if (ob instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) ob;
				npc.deleteMe();
			}
		}
		for (L1ItemInstance obj : L1World.getInstance().getAllItem()) {
			if (obj.getMapId() != _mapnum)
				continue;
			L1Inventory groundInventory = L1World.getInstance().getInventory(
					obj.getX(), obj.getY(), obj.getMapId());
			groundInventory.removeItem(obj);
		}
	}

	class NpcMove implements Runnable {

		private L1NpcInstance npc = null;
		private int count = 0;
		private int direct = 0;

		public NpcMove(L1NpcInstance _npc, int _direct, int _count) {
			npc = _npc;
			count = _count;
			direct = _direct;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			try {
				if (count <= 0) {
					if (direct == 0)
						npc.deleteMe();
					return;
				}
				count--;
				npc.setDirectionMove(direct);
				GeneralThreadPool.getInstance().schedule(this, 640);
			} catch (Exception e) {
			}
		}

	}
}