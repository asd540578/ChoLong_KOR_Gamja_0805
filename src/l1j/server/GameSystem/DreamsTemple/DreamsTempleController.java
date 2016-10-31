package l1j.server.GameSystem.DreamsTemple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javolution.util.FastTable;
import l1j.server.GameSystem.GameList;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1GroundInventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Sound;
import l1j.server.server.utils.L1SpawnUtil;

public class DreamsTempleController implements Runnable {

	private static Random rnd = new Random(System.currentTimeMillis());
	private boolean on = true;
	private L1PcInstance pc = null;
	private int _mapnum = 0;
	private int step = 0;
	private int sub_step = 0;
	private int sub_step2 = 0;
	private int round = 0;
	private L1NpcInstance ������;
	private FastTable<L1NpcInstance> monster;

	public DreamsTempleController(L1PcInstance _pc, int mapid) {
		pc = _pc;
		_mapnum = mapid;

		L1Teleport.teleport(pc, 32798, 32867, (short) mapid, 5, true);

		DreamsTempleSpawn.getInstance().fillSpawnTable(mapid, 0);
		monster = DreamsTempleSpawn.getInstance().fillSpawnTable(mapid, 1);
		������ = L1SpawnUtil.spawn4(32801, 32862, (short) mapid, 4, 100749, 0, 0,
				0);

		GeneralThreadPool.getInstance().schedule(new timer(), 1000);

		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				for (L1NpcInstance npc : monster) {
					if (npc.getNpcId() == 100747)
						SHOUT_MSG(npc, "������ ��� ����?");
				}
			}
		}, 2000);
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				for (L1NpcInstance npc : monster) {
					if (npc.getNpcId() == 100748) {
						SHOUT_MSG(npc, "�׺в��� �� ���� ���� ���� ���̴�.");
						break;
					}
				}
			}
		}, 3000);
	}

	class timer implements Runnable {
		int time = 3600 * 60;
		boolean ck = false;

		@Override
		public void run() {
			try {
				if (!on)
					return;
				if (������ == null || ������._destroyed || ������.isDead()) {
					step = 11;
					return;
				}
				if (!ck) {
					int percent = (int) Math.round((double) ������.getCurrentHp()
							/ (double) ������.getMaxHp() * 100);
					if (percent < 30) {
						GREEN_MSG("�������� �ʹ� ���� ���ظ� �Ծ����ϴ�.");
					}
					ck = true;
				}
				if (time-- < 0) {
					quit();
					return;
				}
				GeneralThreadPool.getInstance().schedule(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final byte HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
	private static final byte HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	class ������ implements Runnable {
		private boolean check = false;
		private L1MonsterInstance ���� = null;

		@Override
		public void run() {
			try {

				// TODO �ڵ� ������ �޼ҵ� ����
				if (!on)
					return;
				if (!check) {
					switch (rnd.nextInt(4)) {
					case 0:
						GREEN_MSG("���� �������� ��Ÿ�����ϴ�.");
						���� = (L1MonsterInstance) L1SpawnUtil.spawn2(32806,
								32862, (short) _mapnum, 100758, 3, 0, 0);
						check = true;
						break;
					case 1:
						GREEN_MSG("�ٶ��� �������� ��Ÿ�����ϴ�.");
						���� = (L1MonsterInstance) L1SpawnUtil.spawn2(32801,
								32870, (short) _mapnum, 100759, 3, 0, 0);
						check = true;
						break;
					case 2:
						GREEN_MSG("���� �������� ��Ÿ�����ϴ�.");
						���� = (L1MonsterInstance) L1SpawnUtil.spawn2(32793,
								32861, (short) _mapnum, 100760, 3, 0, 0);
						check = true;
						break;
					case 3:
						GREEN_MSG("���� �������� ��Ÿ�����ϴ�.");
						���� = (L1MonsterInstance) L1SpawnUtil.spawn2(32799,
								32854, (short) _mapnum, 100761, 3, 0, 0);
						check = true;
						break;
					default:
						break;
					}

				} else {
					if (����._destroyed || ����.isDead()) {
						GREEN_MSG("�������� ���븦 ����߷Ƚ��ϴ�. ���븦 �̿��ϸ� ������ ū ������ �� ���Դϴ�.");
						List<Integer> dirList = new ArrayList<Integer>();
						for (int j = 0; j < 8; j++) {
							dirList.add(j);
						}
						for (int i = 0; i < 2; i++) {
							int x = 0;
							int y = 0;
							int dir = 0;
							do {
								if (dirList.size() == 0) {
									x = 0;
									y = 0;
									break;
								}
								int randomInt = rnd.nextInt(dirList.size());
								dir = dirList.get(randomInt);
								dirList.remove(randomInt);
								x = HEADING_TABLE_X[dir];
								y = HEADING_TABLE_Y[dir];
							} while (!����.getMap().isPassable(����.getX() + x,
									����.getY() + y));
							L1GroundInventory targetInventory = L1World
									.getInstance().getInventory(����.getX() + x,
											����.getY() + y, ����.getMapId());
							targetInventory.storeItem(60513, 1);
						}
						return;
					}
				}
				GeneralThreadPool.getInstance().schedule(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class ������ implements Runnable {
		private int ������step = 0;
		private L1MonsterInstance mon = null;

		@Override
		public void run() {
			try {
				// TODO �ڵ� ������ �޼ҵ� ����
				if (!on)
					return;
				switch (������step) {
				case 0:
					GREEN_MSG("��ȯ�� ������ : �������� ���Ѿư�����? �׷��� ���� �� ����!!");
					������step++;
					break;
				case 1:
					mon = (L1MonsterInstance) L1SpawnUtil.spawn2(32794, 32861,
							(short) _mapnum, 100762, 3, 0, 0);
					������step++;
					break;
				case 2:// �׾����� üũ
					if (mon == null || mon._destroyed || mon.isDead()) {
						step = 5;
						GREEN_MSG("��ȯ�� �����ڰ� ��ġ �Ǿ����ϴ�.");
						������.setCurrentHp(������.getMaxHp());
						return;
					}
					break;
				}
				GeneralThreadPool.getInstance().schedule(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static Random _random = new Random(System.nanoTime());

	@Override
	public void run() {
		// TODO �ڵ� ������ �޼ҵ� ����
		try {
			if (!on)
				return;
			if (pc == null) {
				quit();
				return;
			}
			L1PcInstance _pc = L1World.getInstance().getPlayer(pc.getName());
			if (_pc == null || _pc.getMapId() != _mapnum) {
				quit();
				return;
			}
			switch (step) {
			case 0:
				int count = 0;
				L1NpcInstance delnpc = null;
				for (L1NpcInstance npc : monster) {
					if (npc == null || npc._destroyed || npc.isDead()) {
						count++;
						delnpc = npc;
					}
				}
				if (count >= monster.size()) {
					L1GroundInventory targetInventory = L1World.getInstance()
							.getInventory(delnpc.getX(), delnpc.getY(),
									delnpc.getMapId());
					targetInventory.storeItem(60512, 200);
					step++;
					monster = null;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else {
					monster.remove(delnpc);
				}
				break;
			case 1:
				if (sub_step == 0)
					MSG(������, "�����ַ� �� �ּż� �����մϴ�.");
				else if (sub_step == 1)
					MSG(������, "�̰��� ���簡 �� ���ƿð̴ϴ�.");
				else if (sub_step == 2)
					MSG(������, "������ ���� ������ Ǯ �� �ֵ��� �ð��� �����ּ���.");
				else if (sub_step == 3) {
					MSG(������, "���� ���븦 �̿��� ���� óġ���ּ���.");
					GREEN_MSG("���� ���븦 �̿��� ���� óġ���ּ���.");

				}

				if (sub_step++ >= 3) {
					step++;
				} else {
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				}
			case 2:
				if (round == 0)
					GREEN_MSG("������ �������� �ֽ��ϴ�.");
				else
					GREEN_MSG("������ �� �����ɴϴ�. �غ��� �ּ���");
				Effect_PEQ();
				Round();
				round++;
				step++;
				sub_step = 0;
				sub_step2 = 0;
				if (round == 3)
					sub_step2 = 1;
				else if (round == 4)
					sub_step2 = 2;
				GeneralThreadPool.getInstance().schedule(this, 5000);
				return;
			case 3:
				if (sub_step++ < 4) {
					DreamsTempleSpawn.getInstance().fillSpawnTable(_mapnum,
							1 + sub_step, ������, round != 1);
					if (round == 3 || round == 4)
						GeneralThreadPool.getInstance().schedule(this, 8000);
					else
						GeneralThreadPool.getInstance().schedule(this, 12000);
					return;
				}

				if (sub_step2 > 0) {
					sub_step2--;
					sub_step = 0;
					if (round == 4 && sub_step2 == 0) {
						GeneralThreadPool.getInstance().schedule(new ������(),
								7000);
					} else if (round == 4 && sub_step2 == 1) {
						MSG(������, "�� ���� �ʾҽ��ϴ�. ���ݸ� �� �����ּ���");
					}
					GeneralThreadPool.getInstance().schedule(this, 2000);
					return;
				}

				if (round >= 4)
					step++;
				else {
					step = 2;
					GeneralThreadPool.getInstance().schedule(new ������(), 1);
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				}
				break;
			case 4:// ��� ��ȯ�������� �׾�����
				break;
			case 5:
				for (L1Object ob : L1World.getInstance()
						.getVisibleObjects(_mapnum).values()) {
					if (ob instanceof L1MonsterInstance) {
						L1MonsterInstance npc = (L1MonsterInstance) ob;
						if (npc == null || npc._destroyed || npc.isDead())
							continue;
						npc.receiveDamage(pc, 10000);
					}
				}
				step++;
				sub_step = 0;
				GeneralThreadPool.getInstance().schedule(this, 5000);
				return;
			case 6:
				if (sub_step == 0) {
					������.setTempCharGfx(12493);
					pc.sendPackets(new S_ChangeShape(������.getId(), 12493), true);
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 1000);
					return;
				} else if (sub_step == 1) {
					MSG(������, "�����մϴ�");
					GREEN_MSG("�����մϴ�!");
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else if (sub_step == 2) {
					MSG(������, "��а� �װ��� ���ƿ� �� ���� ���Դϴ�.");
					GREEN_MSG("��а� �װ��� ���ƿ� �� ���� ���Դϴ�.");
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else if (sub_step == 3) {
					MSG(������, "� ��ȯ�� ������ ���ư� ���߰ڱ���.");
					GREEN_MSG("� ��ȯ�� ������ ���ư� ���߰ڱ���.");
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else if (sub_step == 4) {
					MSG(������, "������ �帮�� �ͱ���. ������ ������� ���ڳ׿�.");
					GREEN_MSG("������ �帮�� �ͱ���. ������ ������� ���ڳ׿�.");
					sub_step++;
					GeneralThreadPool.getInstance().schedule(this, 5000);
					return;
				} else if (sub_step == 5) {
					int rnd = _random.nextInt(1000) + 1;
					int itemid = 0;

					if (rnd == 1) {
						itemid = 293;// �Ǹ�
					} else if (rnd == 2) {
						itemid = 292;// ������ũ�ο�
					} else if (rnd <= 7) {
						itemid = 412000;// ���Ű�
					} else if (rnd <= 12) {
						itemid = 412001;// �ĸ��Ǵ��
					} else if (rnd <= 17) {
						itemid = 412005;// ��ǳ�ǵ���
					} else if (rnd <= 22) {
						itemid = 412004;// Ȥ����â
					} else if (rnd <= 27) {
						itemid = 412003;// õ����������
					} else if (rnd <= 32) {
						itemid = 191;// ��õ�� Ȱ
					} else if (rnd <= 37) {
						itemid = 259;// �ı��� ũ�ο�
					} else if (rnd <= 42) {
						itemid = 260;// �ı����̵���
					} else if (rnd <= 321) {
						itemid = 40074;// ���ʸ����ֹ���
					} else if (rnd <= 600) {
						itemid = 40087;// ���⸶���ֹ���
					}

					L1GroundInventory targetInventory = L1World.getInstance()
							.getInventory(������.getX(), ������.getY(),
									������.getMapId());
					targetInventory.storeItem(60514, 1);
					if (itemid > 0)
						targetInventory.storeItem(itemid, 1);

					pc.sendPackets(new S_SkillSound(������.getId(), 169));
					pc.sendPackets(new S_RemoveObject(������));
					pc.getNearObjects().removeKnownObject(������);
					������.teleport(32771, 32835, 6);
					step++;
					GeneralThreadPool.getInstance().schedule(this, 20000);
					return;
				}
				break;
			case 7:
				GREEN_MSG("30�ʵ� ������ �̵��մϴ�.");
				step++;
				GeneralThreadPool.getInstance().schedule(this, 10000);
				return;
			case 8:
				GREEN_MSG("20�ʵ� ������ �̵��մϴ�.");
				step++;
				GeneralThreadPool.getInstance().schedule(this, 10000);
				return;
			case 9:
				GREEN_MSG("10�ʵ� ������ �̵��մϴ�.");
				step++;
				GeneralThreadPool.getInstance().schedule(this, 10000);
				return;
			case 10:// ���� ����
				quit();
				break;
			case 11:// ������ ���
				GREEN_MSG("�������� ������ϴ�.");
				for (L1Object ob : L1World.getInstance()
						.getVisibleObjects(_mapnum).values()) {
					if (ob instanceof L1MonsterInstance) {
						L1MonsterInstance npc = (L1MonsterInstance) ob;
						if (npc == null || npc._destroyed || npc.isDead())
							continue;
						npc.deleteMe();
					}
				}
				step = 7;
				GeneralThreadPool.getInstance().schedule(this, 5000);
				return;
			default:
				break;
			}
		} catch (Exception e) {
		}
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

	public int getMapId() {
		return _mapnum;
	}

	private void quit() {
		on = false;
		HOME_TELEPORT();
		DreamsTemple DT = GameList.getDT(getMapId());
		if (DT != null) {
			DT.Reset();
		}
	}

	private void GREEN_MSG(String msg) {
		pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, msg));
	}

	private void Effect_PEQ() {
		pc.sendPackets(new S_Sound(184));
		pc.sendPackets(new S_PacketBox(83, 1));
		pc.sendPackets(new S_PacketBox(83, 2));
	}

	private void Round() {
		pc.sendPackets(new S_PacketBox(S_PacketBox.ROUND_SHOW, round + 1, 4),
				true);
	}

	private void MSG(L1NpcInstance npc, String msg) {
		Broadcaster
				.broadcastPacket(npc, new S_NpcChatPacket(npc, msg, 0), true);
	}

	private void SHOUT_MSG(L1NpcInstance npc, String msg) {
		Broadcaster
				.broadcastPacket(npc, new S_NpcChatPacket(npc, msg, 2), true);
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
}