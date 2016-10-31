package l1j.server.GameSystem.Robot;

import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Astar.AStar;
import l1j.server.GameSystem.Astar.Node;
import l1j.server.GameSystem.Astar.World;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.Opcodes;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1BugBearRace;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1EffectSpawn;
import l1j.server.server.model.L1GroundInventory;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.skill.L1SkillDelay;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_Fishing;
import l1j.server.server.serverpackets.S_MoveCharPacket;
import l1j.server.server.serverpackets.S_OtherCharPacks;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.utils.SQLUtil;


public class L1RobotInstance extends L1PcInstance {
	private static final long serialVersionUID = 1L;
	private static final int MOVE_SPEED = 0;
	private static final int ATTACK_SPEED = 1;
	private static final int MAGIC_SPEED = 2;
	public static final int DMG_MOTION_SPEED = 3;

	private static final int MOVE = 0;
	private static final int ATTACK = 1;
	private static Random _random = new Random(System.nanoTime());

	public Robot_Location_bean loc = null;
	public L1Character _target;
	public L1PcInstance _target2;
	public L1ItemInstance _targetItem;
	
	public int ���溿_����_lawful = 0;
	public byte ������_������ġ = -1;
	public boolean ������ = false;
	public boolean ���Ա��� = false;
	public boolean ���ú� = false;
	public boolean ��ɺ� = false;
	public boolean ���溿 = false;
	public boolean �������� = false;
	private byte ������_�̵� = 0;
	public boolean �ڻ�� = false;
	private int actionStatus = 0;
	private boolean FirstSkill = false;
	public boolean ���_���� = false;
	public boolean Ÿ�ݱ�ȯ���� = true;

	public String ��ɺ�_��ġ;
	public int ��ɺ�_Ÿ�� = 0;
	//private short ������ = 900;
	//private short ��ȫ�� = 10;
	private short ������ = 1000;
	private short ���빰�� = 10;

	private AStar aStar; // ��ã�� ����
	private int[][] iPath; // ��ã�� ����
	private Node tail; // ��ã�� ����
	private int iCurrentPath; // ��ã�� ����
	//private L1RobotInstance _instance = null;
	
	/*private boolean _Rsaid = false;
	
	private boolean Rsaid() {
		return _Rsaid;
	}

	protected void setRsaid(boolean flag) {
		_Rsaid = flag;
	}*/
	
	private boolean _Townsaid = false;
	
	private boolean Townsaid() {
		return _Townsaid;
	}

	protected void setTownsaid(boolean flag) {
		_Townsaid = flag;
	}
	
	/*private boolean _Dissaid = false;
	
	private boolean Dissaid() {
		return _Dissaid;
	}

	protected void setDissaid(boolean flag) {
		_Dissaid = flag;
	}*/
	
	private boolean _GLsaid = false;
	
	private boolean Glsaid() {
		return _GLsaid;
	}

	protected void setGlsaid(boolean flag) {
		_GLsaid = flag;
	}
	
	private int _shockStunDuration;
	private static final int[] stunTimeArray = {2000, 3000, 4000, 5000};
	

	/*private String _himent;
	private static final String[] himentArray = { "����", "5���ش� �����ض�", "����������" , "����?" , "��?", "��������", "�ο�°� ÷����?"};*/

	private String _townment;
	private static final String[] townmentArray = { "�� ������", "���� ƨ��??", "�����", "��������","�Ḹ ������","����","����������","��������",
			"������","����?","�� ��","�Ѥ�","?","����������","��","����?","�ڵ�������;;"};
	
	/*private String _disment;
	private static final String[] dismentArray = { "��  ", "�� �Ḹ��", "��?" , "�� �Ḹ������" , " �� ��ø�~", " ��"};*/
	
	private String _glment;
	private static final String[] glmentArray = {"���Ľ� ��� �߳�������?", "��� 1~4�� ���Ľ� �߳���", "9���߻�ϴ�.","�Ⱘ ��ġ�� ���� �ؽճ�",
			"��� ������ �׸��ض� ����","�����Ǻ� �ٻ�ϴ�","�ʵ� �Ͻ� ���� �����մϴ�. ��� ���� �����դ���","����� ������","�����Ǻ� ������","����� �˴ϴ�.","���̾��� �ݳ� �ޱ��غ��ϴ�.","�����ϴ� ���� ������ ������� ������ ����",
			"�Ⱘ1�� ���Ϸ� ������","�ؼ� ��Ƽ��� �ϽǺ� ���ؿ�","�ؼ� �����?","���� ��� �ϳ���?","�ؼ� ��� �����ؿ�"
			,"�����Ǻ� ��ΰ� ��ϴ�", "�������� ����� ������", "����� ��������","9�̻� ���� �ٻ�","9��ü����������"
			,"9�Ź�Ȱ �ΰ� �˴ϴ�.","9��յ� �Ⱦƿ�","9�̻� ���߰˻纽","9��ǳ���� �ּ���"};
	
	private static final int[] ������BuffSkill4 = {
			L1SkillId.PHYSICAL_ENCHANT_STR, L1SkillId.PHYSICAL_ENCHANT_DEX,
			L1SkillId.BLESS_WEAPON, L1SkillId.REMOVE_CURSE
			};
	
	public L1RobotInstance() {
		
		iPath = new int[300][2];
		aStar = new AStar();
	//	_instance = this;
	}

	public void startAI() {
		new BrainThread().start();
	}

	
	private boolean _aiRunning = false;
	private boolean _actived = false;
	private int _sleep_time;
	public String _userTitle;

	protected void setAiRunning(boolean aiRunning) {
		_aiRunning = aiRunning;
	}

	protected boolean isAiRunning() {
		return _aiRunning;
	}

	protected void setActived(boolean actived) {
		_actived = actived;
	}

	protected boolean isActived() {
		return _actived;
	}

	protected void setSleepTime(int sleep_time) {
		_sleep_time = sleep_time;
	}

	protected int getSleepTime() {
		return _sleep_time;
	}
	
		

	public boolean _���������� = false;

	class BrainThread implements Runnable {

		public void start() {
			setAiRunning(true);
			GeneralThreadPool.getInstance().execute(BrainThread.this);
			if (��ɺ�)
				GeneralThreadPool.getInstance().execute(new PotionThread());
		}

		public void run() {
			try {
				if (_����������) {
					setAiRunning(false);
					return;
				}
				if (isParalyzed() || isSleeped()) {
					GeneralThreadPool.getInstance().schedule(this, 200);
					return;
				}

				if (������ != 0) {
					GeneralThreadPool.getInstance().schedule(this, ������);
					������ = 0;
					return;
				}
				if (actionStatus == MOVE && �̵������� != 0) {
					GeneralThreadPool.getInstance().schedule(this, �̵�������);
					�̵������� = 0;
					return;
				}
				if (AI()) {
					setAiRunning(false);
					return;
				}
				if (getSleepTime() == 0)
					setSleepTime(300);
			} catch (Exception e) {
				e.printStackTrace();
			}
			GeneralThreadPool.getInstance().schedule(this, getSleepTime());

		}
	}

	class PotionThread implements Runnable {
		public void start() {
			setAiRunning(true);
			GeneralThreadPool.getInstance().execute(PotionThread.this);
		}

		public void run() {
			try {
				if (_����������) {
					setAiRunning(false);
					return;
				}

				if (isDead()) {
					GeneralThreadPool.getInstance().schedule(this, 500);
					return;
				}

				if (isParalyzed() || isSleeped()) {
					GeneralThreadPool.getInstance().schedule(this, 200);
					return;
				}

				if (isTeleport()) {
					GeneralThreadPool.getInstance().schedule(this, 400);
					return;
				}

				int percent = (int) Math.round((double) getCurrentHp() / (double) getMaxHp() * 100);
				if (percent < 10 && ��ɺ�_Ÿ�� == HUNT && !��ɺ�_��ġ.startsWith("�ؼ�")) {
					setCurrentHp(getCurrentHp() + 500);
					��ȯ();
					GeneralThreadPool.getInstance().schedule(this, 2000);
					return;
				} else if (percent < 30 && ��ɺ�_Ÿ�� == HUNT && !��ɺ�_��ġ.startsWith("�ؼ�")) {
					setCurrentHp(getCurrentHp() + 500);
					������();
					GeneralThreadPool.getInstance().schedule(this, 2000);
					return;
				}
				int delay = Debuff();
				if (delay > 0) {
					GeneralThreadPool.getInstance().schedule(this, delay);
					return;
				}
				if (Poison()) {
					GeneralThreadPool.getInstance().schedule(this, 300);
					return;
				}
				delay = Potion();
				if (delay > 0) {
					GeneralThreadPool.getInstance().schedule(this, delay);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			GeneralThreadPool.getInstance().schedule(this, 100);
		}
	}

	private boolean AI() {
		if (isTeleport()) {
			return false;
		}



		if (������) {
			if (������_������ġ == 1 || ������_������ġ == 0 || ������_������ġ == 3)
				return false;
			if (������_�̵� > 0)
				�������̵�();
			else if (_random.nextInt(1000) <= 1)
				������_�̵� = 1;
		} else if (���ú�) {
			���ú�();
		} else if (��ɺ�) {
			��ɺ�();
		} else if (���溿) {
			���溿();
		}
		return false;
	}

	private int Debuff() {
		// TODO �ڵ� ������ �޼ҵ� ����
		// Ŀ�� ���
		if (getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_CURSE_PARALYZING)) {
			��ȯ();
			������ = 8000;
			return 8000;
		}
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DECAY_POTION)) {
			��ȯ();
			int time = getSkillEffectTimerSet().getSkillEffectTimeSec(
					L1SkillId.DECAY_POTION) * 1000;
			return (int) (������ = time);
		}
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE)) {
			��ȯ();
			int time = getSkillEffectTimerSet().getSkillEffectTimeSec(
					L1SkillId.SILENCE) * 1000;
			return (int) (������ = time);
		}
		return 0;
	}

	public boolean Poison() {
		// TODO �ڵ� ������ �޼ҵ� ����
		if (getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ���������� ����
			return false;
		}
		if (getPoison() != null) {
			cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����
			Broadcaster.broadcastPacket(this, new S_SkillSound(getId(), 192),
					true);
			curePoison();
			���빰��--;
			if (���빰�� <= 0)
				���ฮ��();
			return true;
		}
		return false;
	}

	private boolean ����(int ����) {
		return isDistance(getX(), getY(), getMapId(), loc.getX(), loc.getY(),
				getMapId(), ����);
	}

	public int ���溿_Ÿ�� = 0;

	private void ���溿() {
		// TODO �ڵ� ������ �޼ҵ� ����
		try {
			switch (���溿_Ÿ��) {
			case 0: // ����
				���溿��ǥ����();
				���溿_Ÿ��++;
				������(1000 + _random.nextInt(1000));
				return;
			case 1: // �⺻��ǥ �����̰ų� �÷���������
				if (����(_random.nextInt(3))) {
					// System.out.println(getName() +" �� ������");
					���溿_Ÿ��++;
					return;
				} else if (L1BugBearRace.getInstance().getBugRaceStatus() == L1BugBearRace.STATUS_PLAYING) {
					���溿_Ÿ�� = 3;
					loc = new Robot_Location_bean(33534 + _random.nextInt(6),
							32853 + _random.nextInt(6), 4);
					// loc = new Robot_Location_bean(33479 + _random.nextInt(8),
					// 32849 + _random.nextInt(11), 4);
					return;
				} else {
					// System.out.println("��������ֿ���" +getName());
				}
				break;
			case 2: // ���� ���
				if (_random.nextInt(100) < 50 && Robot.�ӵ�����(this)) {
					setSleepTime(calcSleepTime(MAGIC_SPEED));
					return;
				}
				if (L1BugBearRace.getInstance().getBugRaceStatus() == L1BugBearRace.STATUS_PLAYING) {
					������(500 + _random.nextInt(3500));
					if (_random.nextInt(100) < 98) {
						���溿_Ÿ�� = 5;
						return;
					}
					loc = new Robot_Location_bean(33534 + _random.nextInt(6),
							32853 + _random.nextInt(6), 4);
					// loc = new Robot_Location_bean(33479 +
					// _random.nextInt(10), 32849 + _random.nextInt(11), 4);
					���溿_Ÿ��++;
				} else {
					if (_random.nextInt(1000) > 0)
						return;
					GeneralThreadPool.getInstance().schedule(new Runnable() {
						@Override
						public void run() {
							if (_���������� || ���溿_Ÿ�� != 2)
								return;
							int dir = L1SkillUse.checkObject(getX(), getY(),
									getMapId(), _random.nextInt(20));
							if (dir != -1) {
								setDirectionMove(dir);
							}
							if (_random.nextInt(100) < 60)
								GeneralThreadPool.getInstance().schedule(this,
										getSleepTime());
						}
					}, 1);
				}
				return;
			case 3: // ���� ���
				if (L1BugBearRace.getInstance().getBugRaceStatus() == L1BugBearRace.STATUS_PLAYING) {
					if (Robot.�ӵ�����(this)) {
						setSleepTime(calcSleepTime(MAGIC_SPEED));
						return;
					}
					if (L1BugBearRace.racing_im) {
						boolean ck = false;
						if (����(_random.nextInt(2))) {
							ck = true;
							������(100 + _random.nextInt(3500));
						} else {
							ck = L1World
									.getInstance()
									.getVisibleObjects(this,
											Config.PC_RECOGNIZE_RANGE).size() > 0;
						}
						if (ck) {
							���溿_Ÿ��++;
							loc = new Robot_Location_bean(
									33516 + _random.nextInt(11),
									32849 + _random.nextInt(7), 4);
							return;
						}
					}
				} else
					return;
				break;
			case 4: // ���� ����
				if (����(_random.nextInt(3))) {
					������(3000 + _random.nextInt(7000));
					���溿_Ÿ�� = 0;
					return;
				}
				break;
			case 5:// ����Ȯ���� ������۽� �������� �ʴ�
				if (L1BugBearRace.getInstance().getBugRaceStatus() != L1BugBearRace.STATUS_PLAYING)
					���溿_Ÿ�� = 2;
				return;
			default:
				break;
			}
			if (���溿_Ÿ�� == 3 || ���溿_Ÿ�� == 4) {
				if (getSleepTime() < 250 && _random.nextInt(100) < 3)
					������(300 + _random.nextInt(1700));
			}
			if (!isParalyzed()) {
				int dir = moveDirection(loc.getX(), loc.getY(), loc.getMapId());
				if (dir == -1) {
					cnt++;
					if (cnt > 50) {
						Robot.�κ�����(this);
						cnt = 0;
					}
				} else {
					boolean tail2 = World.isThroughObject(getX(), getY(),
							getMapId(), dir);
					boolean door = World.���̵�(getX(), getY(), getMapId(),
							calcheading(this, loc.getX(), loc.getY()));
					if (door || !tail2) {
						cnt++;
						if (cnt > 50) {
							Robot.�κ�����(this);
							cnt = 0;
						}
					}
					setDirectionMove(dir);
					setSleepTime(calcSleepTime(MOVE_SPEED));
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void ���溿��ǥ����() {
		int count = 1000;
		while (count-- > 0) {
			try {
				if (count > 5) {
					switch (_random.nextInt(4)) {
					case 0:
						// loc = new
						// Robot_Location_bean(33502+_random.nextInt(26), 32849,
						// 4);
						// loc = new
						// Robot_Location_bean(33495+_random.nextInt(33), 32849,
						// 4);
						// loc = new Robot_Location_bean(33537, 32853, 4);
						loc = new Robot_Location_bean(33537, 32841, 4);
						break;
					case 1:
						loc = new Robot_Location_bean(33528, 32848, 4);
						break;
					case 2:
						int rrr = _random.nextInt(100);
						if (rrr < 15) {
							loc = new Robot_Location_bean(
									33513 + _random.nextInt(10), 32819, 4);
						} else {
							loc = new Robot_Location_bean(33537, 32853, 4);
						}
						// loc = new Robot_Location_bean(33529,
						// 32844+_random.nextInt(4), 4);
						break;
					case 3:
						// loc = new
						// Robot_Location_bean(33502+_random.nextInt(31), 32859,
						// 4);
						loc = new Robot_Location_bean(
								33495 + _random.nextInt(38), 32859, 4);
						break;
					default:
						break;
					}
				} else {
					int rrr = _random.nextInt(100);
					if (rrr < 30) {
						loc = new Robot_Location_bean(33525, 32851, 4);
					} else if (rrr < 60) {
						loc = new Robot_Location_bean(33537, 32841, 4);
					} else {
						loc = new Robot_Location_bean(33537, 32853, 4);
					}
					/*
					 * if(_random.nextInt(2) == 0){
					 * 
					 * }else
					 */

					// loc = new Robot_Location_bean(33510+_random.nextInt(18),
					// 32850+_random.nextInt(9), 4);
				}
				boolean ck = false;
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (pc.getX() == loc.getX() && pc.getY() == loc.getY()) {
						ck = true;
						break;
					}
				}
				if (!ck) {
					for (L1RobotInstance robot : L1World.getInstance()
							.getAllRobot()) {
						if (robot.loc == null || robot == this)
							continue;
						if (robot.loc.getX() == loc.getX()
								&& robot.loc.getY() == loc.getY()) {
							ck = true;
							break;
						}
					}
				}
				if (ck)
					continue;
				break;
			} catch (Exception e) {
				break;
			}
		}
		if (count <= 0) {

		}
	}

	public static final int SETTING = 0;
	public static final int TEL_NPC_MOVE = 1;
	public static final int HUNT_MOVE = 2;
	public static final int HUNT = 3;
	public static final int DEATH = 4;
	public static final int EXIT = 10;

	public long Hunt_Exit_Time = 0;

	private boolean Ÿ_����_���̵� = false;
	private int cnt2 = 0;
	private Queue<Robot_Location_bean> location_queue = new ConcurrentLinkedQueue<Robot_Location_bean>();
	private Queue<L1ItemInstance> item_queue = new ConcurrentLinkedQueue<L1ItemInstance>();

	private void ��ɺ�() {
		try {
			if (isDead() && ��ɺ�_Ÿ�� != DEATH) {
				������(2000 + _random.nextInt(3000));
				��ɺ�_Ÿ�� = DEATH;
				return;
			}
			if (!isDead() && !isTeleport()) {
				if (Hunt_Exit_Time <= System.currentTimeMillis()) {
					������(20000);
					��ȯ(500);
					����();
					Robot_Hunt.getInstance().delay_spawn(��ɺ�_��ġ, 60000);
					return;
				}
				if (!getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.SHAPE_CHANGE)) {
					getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.SHAPE_CHANGE, 1800 * 1000);
					int time = getSkillEffectTimerSet().getSkillEffectTimeSec(
							L1SkillId.SHAPE_CHANGE);
					if (time == -1) {
						����();
						return;
					}
					Robot.poly(this);
					Broadcaster.broadcastPacket(this, new S_ChangeShape(
							getId(), getGfxId().getTempCharGfx()));
					Broadcaster.broadcastPacket(this, new S_CharVisualUpdate(
							this, getCurrentWeapon()));
					return;
				}
				if (Robot.�ӵ�����(this)) {
					setSleepTime(calcSleepTime(MAGIC_SPEED));
					return;
				}
				if (!isSkillDelay()) {
					if (Robot.Ŭ��������(this)) {
						setSleepTime(calcSleepTime(MAGIC_SPEED));
						return;
					}
					if (isElf()) {
	
						if (actionStatus == MOVE) {
							int percent = (int) Math
									.round((double) getCurrentMp()
											/ (double) getMaxMp() * 100);
							if (percent < 95) {
								new L1SkillUse()
										.handleCommands(this,
												L1SkillId.BLOODY_SOUL, getId(),
												getX(), getY(), null, 0,
												L1SkillUse.TYPE_NORMAL);
								setSleepTime(calcSleepTime(MAGIC_SPEED));
								return;
							}
						}
					}
				}

				Robot.Doll_Spawn(this);
			    
			
			}

			// Ÿ�������� üũ ����ġ�� ��
			if (loc == null) {
				location_queue.clear();
				ArrayList<Robot_Location_bean> list = Robot_Location.�����̼�(this);
				if (list != null) {
					for (Robot_Location_bean ro : list) {
			
						if (��ɺ�_Ÿ�� == SETTING)
							�߰�SETTING��ǥ(ro);
						location_queue.offer(ro);
					}
					loc = location_queue.poll();
				}
			}
			switch (��ɺ�_Ÿ��) {
			case SETTING:// ����, â��, ����
			case TEL_NPC_MOVE:// �ڳ��̵�
				  	/** �������� ä�� **/
					int townrandom = _random.nextInt(1000) +1;
					if(!Townsaid() && townrandom > 998){
					try{
					Delay(1500);
					_townment = townmentArray[_random.nextInt(townmentArray.length)];
					Broadcaster.broadcastPacket(this ,new S_ChatPacket(this, _townment, Opcodes.S_SAY, 0));
					setTownsaid(true);
					_townment = null;
					}catch(Exception e){
						return;
					}
					}
					/** �������� ä�� **/
				if (loc == null) {
					��ɺ�_Ÿ��++;
					return;
				}
				if (isDistance(getX(), getY(), getMapId(), loc.getX(),
						loc.getY(), loc.getMapId(), 1 + _random.nextInt(5))) {
					loc = location_queue.poll();
					������(5000 + _random.nextInt(15000));
					if (loc != null && Ÿ_����_���̵�) {
						getMoveState().setHeading(5);
						��(loc.getX(), loc.getY(), loc.getMapId(),
								3000 + _random.nextInt(3000));
						loc = location_queue.poll();
						Ÿ_����_���̵� = false;
					}
					if (loc == null) {
						if (��ɺ�_Ÿ�� == SETTING)
							����������();
							��ɺ�_Ÿ��++;
					}
					return;
				}
				break;
			case HUNT_MOVE: // ����ͷ� �̵�
				������(500 + _random.nextInt(1000));
				��(loc.getX(), loc.getY(), loc.getMapId());
				location_queue.offer(loc);
				loc = location_queue.poll();
				��ɺ�_Ÿ��++;
				return;
			case HUNT: // ���
				if (checkTarget() || checkTarget2()){
					return;
				}
			
				if (�ڻ��) {
					������(1000 + _random.nextInt(500));
					������(500 + _random.nextInt(1000));
					setTownsaid(false);
					setGlsaid(false);
					return;
					
				}

				if (loc == null) {
					������(3000 + _random.nextInt(6000));
					��ȯ(1000 + _random.nextInt(2000));
					return;
				}

				int range = _random.nextInt(5)+1;

				if (isDistance(getX(), getY(), getMapId(), loc.getX(), loc.getY(), getMapId(), range)) {
					location_queue.offer(loc);
					loc = location_queue.poll();
					cnt2++;
					if (cnt2 >= 3) {
						passTargetList.clear();
						passTargetList2.clear();
						cnt2 = 0;
                        return;
					}
				}
				break;
			case DEATH: // ����
				int[] loc = Getback.GetBack_Restart(this);
				Broadcaster.broadcastPacket(this, new S_RemoveObject(this),
						true);
				setCurrentHp(getLevel());
				set_food(225); // �׾����� 100%
				setDead(false);
				L1World.getInstance().moveVisibleObject(this, loc[0], loc[1],
						loc[2]);
				setX(loc[0]);
				setY(loc[1]);
				setMap((short) loc[2]);
				for (L1PcInstance pc2 : L1World.getInstance().getVisiblePlayer(
						this)) {
					pc2.sendPackets(new S_OtherCharPacks(this, pc2));
				}
				_target = null; //��������
				_targetItem = null; //��������
				_target2 = null; //��������
				������(3000 + _random.nextInt(6000));
				��ȯ(1000 + _random.nextInt(2000));
				setTownsaid(false);
				setGlsaid(false);
				return;
			case EXIT: // ����
				return;
			default:
				break;
			}
			if (!isDead() && loc != null) {
				�̵�();
			}
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}

	private void �߰�SETTING��ǥ(Robot_Location_bean ro) {
		// TODO �ڵ� ������ �޼ҵ� ����
		if (ro.getX() == 33457 && ro.getY() == 32819 && ro.getMapId() == 4) {// ���
																				// �������
			if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// ����
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// �ڳ�
																				// ��ġ
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));// ��
																				// ��
																				// ��ġ
			} else if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// �����
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));
			}
			if (location_queue.size() > 0)
				Ÿ_����_���̵� = true;
		} else if (ro.getX() == 33432 && ro.getY() == 32815
				&& ro.getMapId() == 4) {// ���2 �������
			if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// ����
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// �ڳ�
																				// ��ġ
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));// ��
																				// ��
																				// ��ġ
			} else if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// �����
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));
			}
			if (location_queue.size() > 0)
				Ÿ_����_���̵� = true;
		} else if (ro.getX() == 33428 && ro.getY() == 32806
				&& ro.getMapId() == 4) {// ���3,5 �Ƶ����
			if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// ����
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// �ڳ�
																				// ��ġ
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));// ��
																				// ��
																				// ��ġ
			} else if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// �����
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));
			}
			if (location_queue.size() > 0)
				Ÿ_����_���̵� = true;
		} else if (ro.getX() == 33437 && ro.getY() == 32803
				&& ro.getMapId() == 4) {// ���4 ������
			if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// ����
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// �ڳ�
																				// ��ġ
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));// ��
																				// ��
																				// ��ġ
			} else if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// �����
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33438, 32796, 4));
			}
			if (location_queue.size() > 0)
				Ÿ_����_���̵� = true;
		} else if (ro.getX() == 34065 && ro.getY() == 32287
				&& ro.getMapId() == 4) {// ���� �������
			if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// �����
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(34062, 32278, 4));
			} else if (getX() >= 33410 && getX() <= 33461 && getY() >= 32788
					&& getY() <= 32838 && getMapId() == 4) {// ���
				location_queue.offer(new Robot_Location_bean(33437, 32794, 4));
				location_queue.offer(new Robot_Location_bean(34062, 32278, 4));
			}
			if (location_queue.size() > 0)
				Ÿ_����_���̵� = true;
		} else if (ro.getX() == 32596 && ro.getY() == 32741
				&& ro.getMapId() == 4) {// �۸� �������
			if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// �����
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(32608, 32734, 4));
			} else if (getX() >= 33410 && getX() <= 33461 && getY() >= 32788
					&& getY() <= 32838 && getMapId() == 4) {// ���
				location_queue.offer(new Robot_Location_bean(33437, 32794, 4));
				location_queue.offer(new Robot_Location_bean(32608, 32734, 4));
			} else if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// ����
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// �ڳ�
																				// ��ġ
				location_queue.offer(new Robot_Location_bean(32608, 32734, 4));
			}
			if (location_queue.size() > 0)
				Ÿ_����_���̵� = true;
		} else if (ro.getX() == 33738 && ro.getY() == 32494
				&& ro.getMapId() == 4) {// ���� �������
			if (getX() >= 33065 && getX() <= 33093 && getY() >= 33385
					&& getY() <= 33411 && getMapId() == 4) {// �����
				location_queue.offer(new Robot_Location_bean(33080, 33384, 4));
				location_queue.offer(new Robot_Location_bean(33709, 32500, 4));
			} else if (getX() >= 33410 && getX() <= 33461 && getY() >= 32788
					&& getY() <= 32838 && getMapId() == 4) {// ���
				location_queue.offer(new Robot_Location_bean(33437, 32794, 4));
				location_queue.offer(new Robot_Location_bean(33709, 32500, 4));
			} else if (getX() >= 34047 && getX() <= 34064 && getY() >= 32273
					&& getY() <= 32297 && getMapId() == 4) {// ����
				location_queue.offer(new Robot_Location_bean(34064, 32278, 4));// �ڳ�
																				// ��ġ
				location_queue.offer(new Robot_Location_bean(33709, 32500, 4));
			}
			if (location_queue.size() > 0)
				Ÿ_����_���̵� = true;
		}
	}

	public void ����() {
		����(1000 + _random.nextInt(20000));
	}

	public void ����(int time) {
		���_���� = true;
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				// TODO �ڵ� ������ �޼ҵ� ����
				_���������� = true;
				for (L1PcInstance pc : L1World.getInstance()
						.getRecognizePlayer(L1RobotInstance.this)) {
					pc.sendPackets(new S_RemoveObject(L1RobotInstance.this), true);
					pc.getNearObjects().removeKnownObject(L1RobotInstance.this);
				}
				L1World world = L1World.getInstance();
				world.removeVisibleObject(L1RobotInstance.this);
				world.removeObject(L1RobotInstance.this);
				getNearObjects().removeAllKnownObjects();
				stopHalloweenRegeneration();
				stopPapuBlessing();
				stopHalloweenArmorBlessing();
				stopAHRegeneration();
				stopHpRegenerationByDoll();
				stopMpRegenerationByDoll();
				stopSHRegeneration();
				stopMpDecreaseByScales();
				stopEtcMonitor();
				��ɺ�_��ġ = null;
				��ɺ� = false;
				��ɺ�_Ÿ�� = 0;
				���_���� = false;
				Ÿ�ݱ�ȯ���� = true; //Ÿ�ݱ�ȯ ���� false
				loc = null;
				updateconnect(false);
				Robot.Doll_Delete(L1RobotInstance.this, true);
				Robot_Hunt.getInstance().put(L1RobotInstance.this);
			}

		}, time);
	}

	public void ������() {
		������(1000);
		������(1);
		passTargetList.clear();
		passTargetList2.clear();
		
	}

	private void ������(int time) {
		L1Location newLocation = getLocation().randomLocation(200, true);
		int newX = newLocation.getX();
		int newY = newLocation.getY();
		short mapId = (short) newLocation.getMapId();
		��(newX, newY, mapId, time);
	}

	public void ��ȯ() {
		��ȯ(1);
	}

	public void ��ȯ(int time) {
		int[] loc = new int[3];
		_random.setSeed(System.currentTimeMillis());
		switch (_random.nextInt(10)) {
		case 0:
			loc[0] = 33433;
			loc[1] = 32800;
			loc[2] = 4;
			break;
		case 1:
			loc[0] = 33418;
			loc[1] = 32815;
			loc[2] = 4;
			break;
		case 2:
			loc[0] = 33425;
			loc[1] = 32827;
			loc[2] = 4;
			break;
		case 3:
			loc[0] = 33442;
			loc[1] = 32797;
			loc[2] = 4;
			break;
		case 6:
		case 5:
		case 4:
			loc[0] = 34056;
			loc[1] = 32279;
			loc[2] = 4;
			break;
		case 7:
		case 8:
		case 9:
			loc[0] = 33080;
			loc[1] = 33392;
			loc[2] = 4;
			break;
		default:
			loc[0] = 33442;
			loc[1] = 32797;
			loc[2] = 4;
			break;
		}
		��(loc[0], loc[1], loc[2], time);
		if (��ɺ�) {
			item_queue.clear();
			passTargetList.clear();
			passTargetList2.clear();
			��ɺ�_Ÿ�� = SETTING;
			this.loc = null;
		}
	}

	private int Potion() {
		if (getSkillEffectTimerSet().hasSkillEffect(10513))
			return 1000;
		if (getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ���������� ����
			return 0;
		}
		int percent = (int) Math.round((double) getCurrentHp()
				/ (double) getMaxHp() * 100);
		int gfxid = 0;
		int healHp = 0;
		int delay = 0;
	
		if(percent < 95){
			gfxid = 197; //������ 189
			healHp = 45 + _random.nextInt(35);
			delay = 800;
			������--;
		}


		if (healHp == 0)
			return 0;
		// �ۼַ�Ʈ�������� ����
		cancelAbsoluteBarrier();
		Broadcaster.broadcastPacket(this, new S_SkillSound(getId(), gfxid),
				true);
		if (getSkillEffectTimerSet().hasSkillEffect(POLLUTE_WATER)
				|| getSkillEffectTimerSet().hasSkillEffect(10517)) { // ����Ʈ��Ÿ����
																		// ȸ����1/2��
			healHp /= 2;
		}

		setCurrentHp(getCurrentHp() + healHp);
		if (������ <= 0) {
			���ฮ��();
		}
		return delay;
	}

	private void ���ฮ��() {
		/*
		 * ������(2000+_random.nextInt(14000)); ��ȯ();
		 */
		
		������ = (short) (800 + _random.nextInt(1000));
		���빰�� = (short) (1000);
	}

	private boolean checkTarget() {
		
		if (_target == null && _targetItem == null) {
			searchTarget();
		}
		if ( _target != null && _target instanceof L1MonsterInstance) {
			if (((L1MonsterInstance) _target).getHiddenStatus() != L1NpcInstance.HIDDEN_STATUS_NONE
					|| _target.isDead()
					|| ((L1MonsterInstance) _target)._destroyed
					|| ((L1MonsterInstance) _target).getTarget() != this
					|| _target.isInvisble()) {
				_target = null;
				searchTarget();
				setSleepTime(100);
			}
		}
		if (_targetItem != null) {
			L1Inventory groundInventory = L1World.getInstance().getInventory(
					_targetItem.getX(), _targetItem.getY(),
					_targetItem.getMapId());
			if (!groundInventory.checkItem(_targetItem.getItemId())) {
				_targetItem = null;
				searchTarget(); //�ΰ�����
				setSleepTime(100);
			} else {
				onTargetItem();
				return true;
			}
		} else if (_target != null && _target2 !=null) { //�����������
			return onTarget();
		}
		return false;
	}
	
	private boolean checkTarget2() {
		// TODO �ڵ� ������ �޼ҵ� ����
 		if (_target2 == null) {
			searchTarget();
		}

		if (_target2 != null && _target2 instanceof L1PcInstance) {
			if (_target2.isDead() || _target2.isInvisble()) {
				_target2= null;
				searchTarget();
				setSleepTime(100);
			} else {
         	return onTarget2();

			}
		}		
		return false;
	}

	private void searchTarget() {
		
	
		int MaxRange = 2;
		//if (��ɺ�_��ġ.startsWith("�ؼ�"))
		//	MaxRange = 3;
	
		ArrayList<L1Object> list = L1World.getInstance().getVisibleObjects(this);
	    ArrayList<L1PcInstance> list2 = L1World.getInstance().getVisiblePlayer(this); 
	  
	    if(list2.contains(_target2)){
	    	return;
	    } 
	    //������ �ȶ�����? �ΰ�����
	    
	    
	    if (list.size() > 1)
			Collections.shuffle(list);
	    

		
		int mapid = getMapId();
		for (L1Object obj : list) {
			if (obj instanceof L1GroundInventory) {
				L1GroundInventory inv = (L1GroundInventory) obj;
				for (L1ItemInstance item : inv.getItems()) {
					//if (item.getItemOwner() != null	&& item.getItemOwner() == this) { //����۸԰�
						if (item !=null && !isDistance(getX(), getY(), mapid, item.getX(),item.getY(), mapid, 20)){
							continue;
						}
						if (item !=null && isDistance(getX(), getY(), mapid, item.getX(),item.getY(), mapid, 10)
							&& !isDistance(getX(), getY(), mapid, item.getX(),item.getY(), mapid, -1)){
						if(_serchCource(item.getX(), item.getY()) == -1) {
							continue;
						} if (item_queue.contains(item)){
								continue;
						}
						 
						//} //����۸԰�
					
						item_queue.offer(item);
					    list = null; //�������� 2015.11.26
					    obj = null; //�������� 2015.11.26
					    item = null; //��������
					
					}
		
				}
			}
		}
		if (item_queue.size() > 0) {
			_targetItem = item_queue.poll();
			return;
		}
		
		for (int i = 0; i <= MaxRange; i++) {
	
			list = L1World.getInstance().getVisibleObjects(this, i == 0 ? 1 : 4 * i);
			list2 = L1World.getInstance().getVisiblePlayer(this, i == 0 ? 1 : 4 * i);

			
		
			if (list2.size() > 1)
				Collections.shuffle(list2);	
			if (list.size() > 1)
			    Collections.shuffle(list);
			if (list.size() > 1 && list2.size() > 1)
				Collections.shuffle(list2);
		

			
			for (L1Object obj : list){
				if (obj instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) obj;
					
					if (passTargetList.contains(obj)){
						continue;
					}
				
					if (mon.getCurrentHp() <= 0 || mon.isDead()){
						continue;
					}
					if (mon.getHiddenStatus() != L1NpcInstance.HIDDEN_STATUS_NONE
							|| mon._destroyed || mon.isInvisble()){
						continue;
					}
					if (mon.getNpcId() == 100623
						|| mon.getNpcId() == 100624
						|| mon.getNpcId() == 45941
						|| (mon.getNpcId() >= 46048 && mon.getNpcId() <= 46052)){
						continue;
					}
                   	if (mon.getNpcId() >= 70981 && mon.getNpcId() <= 70984){
						continue;
					}
					if (mon.getTarget() != null && mon.getTarget() != this){
						continue;
					}
					if (obj != null && !isDistance(getX(), getY(), mapid, obj.getX(), obj.getY(), mapid, 20)){
						continue;
					}
					if (obj != null && _serchCource(obj.getX(), obj.getY()) == -1) { 
						passTargetList.add(obj);
						continue;
					}
								
					_target = mon;
					FirstSkill = false;
					list = null; //�������� 2015.11.26
					obj = null; //�������� 2015.11.26
					mon= null;
					return;
				}
				
				for (L1PcInstance obj2 : list2){
					if (obj2 instanceof L1PcInstance) {
						L1PcInstance saram = (L1PcInstance) obj2;
		
					   if (passTargetList2.contains(obj2)){
							continue;
					    }
						if (saram.getCurrentHp() <= 0 || saram.isDead()){
							continue;
						}
						if (saram.isInvisble()){
							continue;
						}
						if (saram.getMap().isSafetyZone(saram.getX(), saram.getY())){
							continue;
						}
						if (getClanid() == saram.getClanid()){
							continue;
						} 	
						
						if (obj2 != null && !isDistance(getX(), getY(), mapid, obj2.getX(), obj2.getY(), mapid, 20)){
							continue;
						}
						if (obj2 != null && isDistance(getX(), getY(), mapid, obj2.getX(), obj2.getY(), mapid, 10)
						&& !isDistance(getX(), getY(), mapid, obj2.getX(), obj2.getY(), mapid, -1)){ 
						if( _serchCource(obj2.getX(), obj2.getY()) == -1){
						passTargetList2.add(obj2);
						continue;
						}
						}
				
										
						_target2 = saram;
						FirstSkill = false;
						//setRsaid(false); //�κ�ä��
						//setDissaid(false); //�Ÿ�ä��
						list2 = null;//�������� 2015.11.26
						obj2 = null; //�������� 2015.11.26
						saram = null;
						return;
					}
				}
				
				
			}
		}
	}

	public void onTargetItem() {

		if (_targetItem == null) {
			return;
		}
		if (getLocation().getTileLineDistance(_targetItem.getLocation()) <= 1) {
			pickupTargetItem();
			setSleepTime(800 + _random.nextInt(400));
		
		} else {
			int dir = moveDirection(_targetItem.getX(), _targetItem.getY(),
					_targetItem.getMapId());
			if (dir == -1) {
				_targetItem = null;
			} else {
				boolean tail = World.isThroughObject(getX(), getY(),
						getMapId(), dir);
				int tmpx = aStar.getXY(dir, true) + getX();
				int tmpy = aStar.getXY(dir, false) + getY();
				boolean obj = World.isMapdynamic(tmpx, tmpy, getMapId());
				boolean door = World.���̵�(getX(), getY(), getMapId(), dir);
				if (tail && !obj && !door) {
					setDirectionMove(dir);
				}
				setSleepTime(calcSleepTime(MOVE_SPEED));
			}
		}
	}

	private void pickupTargetItem() {
		int chdir = calcheading(this, _targetItem.getX(), _targetItem.getY());
		if (getMoveState().getHeading() != chdir) {
			getMoveState().setHeading(chdir);
			Broadcaster.broadcastPacket(this, new S_ChangeHeading(this), true);
		}
		Broadcaster.broadcastPacket(this,
				new S_AttackPacket(this, _targetItem.getId(),
						ActionCodes.ACTION_Pickup), true);
		L1Inventory groundInventory = L1World.getInstance().getInventory(
				_targetItem.getX(), _targetItem.getY(), _targetItem.getMapId());
		groundInventory.tradeItem(_targetItem, _targetItem.getCount(), getInventory());
		_targetItem = null;
	}

	private ArrayList<L1Object> passTargetList = new ArrayList<L1Object>();
	private ArrayList<L1PcInstance> passTargetList2 = new ArrayList<L1PcInstance>();
	

	public boolean onTarget() {
		setActived(true);
		_targetItem=null;
		L1Character target = _target;
		
		int percent = (int) Math.round((double) getCurrentHp() / (double) getMaxHp() * 100);
		if(_target2 !=null && percent < 85){
			_target = null;
			return checkTarget2(); //�ΰ�����
			 
		}
	
		if (target == null) {
			return false;
		}
		
		/** ��â **/
		int glrandom = _random.nextInt(1000) +1;
		 if(!Glsaid() && glrandom > 998){
			 ��â();
		 }
		 /** ��â **/
		
		int escapeDistance = 15;
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DARKNESS)
				|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.CURSE_BLIND))
			escapeDistance = 1;
		int calcx = (int) getLocation().getX() - target.getLocation().getX();
		int calcy = (int) getLocation().getY() - target.getLocation().getY();

		if (Math.abs(calcx) > escapeDistance
				|| Math.abs(calcy) > escapeDistance) {
			_target = null;
			return false;
		}
		boolean tail = World.isThroughAttack(getX(), getY(), getMapId(), calcheading(this, target.getX(), target.getY()));
		
		if (getX() == _target.getX() && getY() == _target.getY()
				&& getMapId() == _target.getMapId())
			tail = true;
	
		boolean door = World.���̵�(getX(), getY(), getMapId(), calcheading(this, target.getX(), target.getY()));
		
		int range = 1;
		if (isElf() && getCurrentWeapon() == 20)
			range = 11;
		// ùŸ ���� �Ǵ� Ʈ���� �Ǵ� ����?
		if (!FirstSkill && !isSkillDelay() && getCurrentMp() > 30) {
			int skillId = 0;
			int skill_range = 11;
			if (isElf() && getCurrentWeapon() == 20) {
				skillId = L1SkillId.TRIPLE_ARROW;
			} else if (isDragonknight()) {
				skillId = L1SkillId.FOU_SLAYER;
				skill_range = 1;
			}
			if (skillId > 0) {
				if (CharPosUtil.isAttackPosition(this, target.getX(), target.getY(), target.getMapId(), skill_range) == true
						&& CharPosUtil.isAttackPosition(target, getX(), getY(),	getMapId(), skill_range) == true
				) {
					FirstSkill = true;
					new L1SkillUse().handleCommands(this, skillId,
							_target.getId(), _target.getX(), _target.getY(),
							null, 0, L1SkillUse.TYPE_NORMAL);
					setSleepTime(calcSleepTime(MAGIC_SPEED));
					actionStatus = ATTACK;
					return true;
				}
			}
		}
		if (CharPosUtil.isAttackPosition(this, target.getX(), target.getY(),target.getMapId(), range) == true
				&& CharPosUtil.isAttackPosition(target, getX(), getY(),	getMapId(), range) == true
			
				) {// �⺻ ���ݹ���
			if (door || !tail) {
				cnt++;
				if (cnt > 5) {
					_target = null;
					cnt = 0;
				}
				return false;
			}
			getMoveState().setHeading(CharPosUtil.targetDirection(this, target.getX(), target.getY()));
			attackTarget(target);
			actionStatus = ATTACK;
			return true;

		} else {
			int dir = moveDirection(target.getX(), target.getY(), target.getMapId());
			if (dir == -1) {
				passTargetList.add(_target);
				_target = null;
				return false;
			} else {
				boolean tail2 = World.isThroughObject(getX(), getY(), getMapId(), dir);
				if (door || !tail2) {
					cnt++;
					if (cnt > 5) {
						_target = null;
						cnt = 0;
					}
					return false;
				}
				setDirectionMove(dir);
				setSleepTime(calcSleepTime(MOVE_SPEED));
			}
 		}
		return true;
	}
	
	public <L1Pcinstance> boolean onTarget2() {
		setActived(true);
		_targetItem = null;
		_target = null;
		L1PcInstance target2 = _target2;
		
		if(target2 == null){
			try{
				Delay(1000);
				return false;
				}catch(Exception e){
			}
			
		}
							
		
		/** ����� ä�� **/
		/*int hirandom= _random.nextInt(1000)+1;
		if(hirandom > 995 && !Rsaid() && !target2.isRobot()){
		_himent = himentArray[_random.nextInt(himentArray.length)];
		try{
		Delay(1500);
		Broadcaster.broadcastPacket(this ,new S_ChatPacket(this, _himent, Opcodes.S_OPCODE_NORMALCHAT, 0));
		setRsaid(true);
		_himent = null;
		}catch(Exception e){
		}
		}*/
		/** ����� ä�� **/
		
		
		
		
		int escapeDistance = 15;
		if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DARKNESS)
				|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.CURSE_BLIND))
			escapeDistance = 1;
		int calcx = (int) getLocation().getX() - target2.getLocation().getX();
		int calcy = (int) getLocation().getY() - target2.getLocation().getY();

		if (Math.abs(calcx) > escapeDistance
			|| Math.abs(calcy) > escapeDistance) {
			_target2 = null;
			return false;
		}
		
		/** �������� ä�� **/
		/*int disrandom= _random.nextInt(100)+1;
		if(disrandom > 50 && !isElf() &&  Rsaid() && !target2.isRobot() && Math.abs(calcx) > 6 && !Dissaid() 
		|| disrandom > 50 && !isElf() &&  Rsaid() && !target2.isRobot() && Math.abs(calcy) > 6 && !Dissaid()
		){
		_disment = dismentArray[_random.nextInt(dismentArray.length)];
		String NAME = target2.getName();
		try{
        Delay(1500);
		Broadcaster.broadcastPacket(this ,new S_ChatPacket(this, NAME+_disment, Opcodes.S_OPCODE_NORMALCHAT, 0));
		cnt++;
		if (cnt > 2) {
		setDissaid(true);
		_disment = null;
		}
		}catch(Exception e){}
		}*/
		/** �������� ä�� **/
		
		boolean tail = World.isThroughAttack(getX(), getY(), getMapId(), calcheading(this, target2.getX(), target2.getY()));
		
		if (getX() == _target2.getX() && getY() == _target2.getY() && getMapId() == _target2.getMapId())
			tail = true;
	
		boolean door = World.���̵�(getX(), getY(), getMapId(), calcheading(this, target2.getX(), target2.getY()));
		
		int range = 1;
		if (isElf() && getCurrentWeapon() == 20)
			range = 11;
				
		// ùŸ ���� �Ǵ� Ʈ���� �Ǵ� ����?
		if (!FirstSkill && !isSkillDelay() && getCurrentMp() > 30) {
			int skillId = 0;
			int skill_range = 11;
			if (isElf() && getCurrentWeapon() == 20) {
				skillId = L1SkillId.TRIPLE_ARROW;
			}  else if (isWizard()) { //���线 ��
				skillId = L1SkillId.DISINTEGRATE;
			}  else if (isDragonknight()) {
				skillId = L1SkillId.FOU_SLAYER;
				skill_range = 1;
			}  else if (isKnight()) {
				skillId = L1SkillId.SHOCK_STUN;
				skill_range = 1;
			} 
			
			if (skillId > 0) {
				if (CharPosUtil.isAttackPosition(this, target2.getX(), target2.getY(), target2.getMapId(), skill_range) == true
						&& CharPosUtil.isAttackPosition(target2, getX(), getY(),	getMapId(), skill_range) == true) {
								
					FirstSkill = true;
					
				if(isKnight() && skillId == L1SkillId.SHOCK_STUN && !target2.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHOCK_STUN)){
			
						int STrnd = _random.nextInt(10) + 1;
						if(STrnd >= 7){
						_shockStunDuration = stunTimeArray[_random.nextInt(stunTimeArray.length)];
						S_SkillSound ss1 = new S_SkillSound(target2.getId(), 4434);
						target2.sendPackets(ss1);
						Broadcaster.broadcastPacket(target2, ss1);
						ss1 = null;
						target2.getSkillEffectTimerSet().setSkillEffect(
								L1SkillId.SHOCK_STUN, _shockStunDuration);
						L1EffectSpawn.getInstance().spawnEffect(81162,
								_shockStunDuration, target2.getX(), target2.getY(),
								target2.getMapId());
						S_Paralysis par = new S_Paralysis(S_Paralysis.TYPE_STUN, true);
						target2.sendPackets(par);
						par = null;
						this.setSkillDelay(true);
						GeneralThreadPool.getInstance().schedule(new L1SkillDelay(this, 8000), 8000);
								
						} else {
							S_SkillSound ss1 = new S_SkillSound(target2.getId(), 4434);
							target2.sendPackets(ss1);
							Broadcaster.broadcastPacket(target2, ss1);
							ss1 = null;
							this.setSkillDelay(true);
							GeneralThreadPool.getInstance().schedule(new L1SkillDelay(this, 8000), 8000);
						}
					} else if(!isKnight()) {
						
					new L1SkillUse().handleCommands(this, skillId,_target2.getId(), _target2.getX(), _target2.getY(),
						null, 0, L1SkillUse.TYPE_NORMAL);
					}
				    int drandom = _random.nextInt(10) + 1;
				    if(drandom > 6 && isDarkelf()){ //�ٿ��� ����
				    	Broadcaster.broadcastPacket(_target2, new S_SkillSound(_target2.getId(), 3398));
				    	_target2.sendPackets(new S_SkillSound(_target2.getId(), 3398));
				    	_target2.receiveDamage(this, 100, false);
				    }
					setSleepTime(calcSleepTime(MAGIC_SPEED));
					actionStatus = ATTACK;
					return true;
				}
			}
		}
		if (CharPosUtil.isAttackPosition(this, target2.getX(), target2.getY(),target2.getMapId(), range) == true
				&& CharPosUtil.isAttackPosition(target2, getX(), getY(), getMapId(), range) == true) {// �⺻ ���ݹ���	
			if (door || !tail) {
				cnt++;
				if (cnt > 5) {
					_target2 = null;
					cnt = 0;
				}
				return false;
			}
			
			getMoveState().setHeading(CharPosUtil.targetDirection(this, target2.getX(), target2.getY()));
			attackTarget(target2);
			actionStatus = ATTACK;
			return true;

		} else {
			int dir = moveDirection(target2.getX(), target2.getY(), target2.getMapId());
			if (dir == -1) {
				passTargetList2.add(_target2);
				_target2 = null;
				return false;
			} else {
				boolean tail2 = World.isThroughObject(getX(), getY(), getMapId(), dir);
				if (door || !tail2) {
					cnt++;
					if (cnt > 5) {
						_target2 = null;
						cnt = 0;
					}
					return false;
				}
				setDirectionMove(dir);
				setSleepTime(calcSleepTime(MOVE_SPEED));
			}
		  }
		
		return true;
	}

	public void attackTarget(L1Character target) {
		Random random = new Random();
		if (target instanceof L1PcInstance) {
			L1PcInstance player = (L1PcInstance) target;
			if (player.isTeleport())
				return;
		}

		boolean isCounterBarrier = false;
		boolean isMortalBody = false;
		boolean isLindArmor = false;
		L1Attack attack = new L1Attack(this, target);
		if (attack.calcHit()) {
			if (target.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.COUNTER_BARRIER)) {
				int chan = random.nextInt(100) + 1;
				boolean isProbability = false;
				if (20 > chan) {
					isProbability = true;
				}
				boolean isShortDistance = attack.isShortDistance();
				if (isProbability && isShortDistance) {
					isCounterBarrier = true;
				}
			} else if (target.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.MORTAL_BODY)) {
				int chan = random.nextInt(100) + 1;
				boolean isProbability = false;
				if (15 > chan) {
					isProbability = true;
				}
				// boolean isShortDistance = attack.isShortDistance();
				if (isProbability /* && isShortDistance */) {
					isMortalBody = true;
				}
			}
			if (!isCounterBarrier && !isMortalBody && !isLindArmor) {
				attack.calcDamage();
			}
		}
		if (isCounterBarrier) {
			attack.actionCounterBarrier();
			attack.commitCounterBarrier();
		} else if (isMortalBody) {
			attack.actionMortalBody();
			attack.commitMortalBody();
		} else {
			attack.action();
			attack.commit();
		}
		attack = null;
		setSleepTime(calcSleepTime(ATTACK_SPEED));

	}
	

	// ��ɺ� �̵�
	private void �̵�() {
		�̵�(loc.getX(), loc.getY());
	}

	private L1Location BackLoc_1th = null;
	private L1Location BackLoc_2th = null;
	private int cnt3 = 0;
	private boolean BackRR = false;

	private void �̵�(int x, int y) {
		int dir = moveDirection(x, y, getMapId());
		if (dir == -1) {
			cnt++;
			if (cnt > 20) {
				������(3000 + _random.nextInt(2000));
				��ȯ(1000 + _random.nextInt(2000));
				cnt = 0;
				/*
				 * if(getMapId() >= 110 && getMapId() <= 179){
				 * System.out.println(��ɺ�_��ġ);
				 * System.out.println("�������� ��ȯ -> "+getName
				 * ()+" X:"+getX()+" Y:"+
				 * getY()+" m:"+getMapId()+" �����°�>"+x+" > "+y); }
				 */
				return;
			}
			setSleepTime(1000 + _random.nextInt(1000));
		} else {
			boolean tail2 = World.isThroughObject(getX(), getY(), getMapId(),
					dir);
			boolean door = World.���̵�(getX(), getY(), getMapId(),
					calcheading(this, x, y));
			if (door || !tail2) {
				cnt++;
				if (cnt > 20) {
					������(3000 + _random.nextInt(2000));
					��ȯ(1000 + _random.nextInt(2000));
					cnt = 0;
					return;
				}
			}

			setDirectionMove(dir);
			setSleepTime(calcSleepTime(MOVE_SPEED));

			/*
			 * if(BackLoc_1th != null && BackLoc_2th != null &&
			 * getName().equalsIgnoreCase("����")){ System.out.println(cnt3);
			 * System.out.println(BackLoc_1th.getX()+" > "+BackLoc_1th.getY());
			 * System.out.println(BackLoc_2th.getX()+" > "+BackLoc_2th.getY());
			 * }
			 */

			if ((BackLoc_1th != null && getLocation().getTileDistance(
					BackLoc_1th) == 0)
					|| (BackLoc_2th != null && getLocation().getTileDistance(
							BackLoc_2th) == 0))
				cnt3++;
			else
				cnt3 = 0;

			if (!BackRR)
				BackLoc_1th = new L1Location(getLocation());
			else
				BackLoc_2th = new L1Location(getLocation());

			BackRR = !BackRR;

			if (cnt3 > 20) {
				������(3000 + _random.nextInt(2000));
				��ȯ(1000 + _random.nextInt(2000));
				cnt3 = 0;
				/*
				 * if(getMapId() >= 133 && getMapId() <= 139){
				 * System.out.println(��ɺ�_��ġ);
				 * System.out.println("���ѹݺ� ��ȯ -> "+getName
				 * ()+" X:"+getX()+" Y:"+
				 * getY()+" m:"+getMapId()+" �����°�>"+x+" > "+y); }
				 */
				return;
			}
		}
	}

	public void ��(int x, int y, int mapid) {
		��(x, y, mapid, 1, true);
	}

	public void ��(int x, int y, int mapid, int time) {
		��(x, y, mapid, time, true);
	}

	public void ��(final int x, final int y, final int mapid, int time,
			final boolean effect) {
		if (��ɺ�)
			item_queue.clear();
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				// TODO �ڵ� ������ �޼ҵ� ����
				try {
					if (L1RobotInstance.this.isDead()
							|| L1RobotInstance.this.isTeleport()
							|| L1RobotInstance.this.isParalyzed()
							|| L1RobotInstance.this.isSleeped()
							|| L1RobotInstance.this.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�������))
					return;
			
					setTeleport(true);
					S_SkillSound ss = new S_SkillSound(getId(), 169);
					S_RemoveObject ro = new S_RemoveObject(L1RobotInstance.this);
					for (L1PcInstance pc : L1World.getInstance()
							.getRecognizePlayer(L1RobotInstance.this)) {
						if (effect)
							pc.sendPackets(ss);
						pc.sendPackets(ro);
					}
					Thread.sleep(280);
					for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(L1RobotInstance.this)) {
						pc.getNearObjects().removeKnownObject(L1RobotInstance.this);
						pc.sendPackets(ro); //����� �ȳ���?
						
					}
					L1World.getInstance().moveVisibleObject(
							L1RobotInstance.this, x, y, mapid);
					setX(x);
					setY(y);
					setMap((short) mapid);
					setTeleport(false);
				} catch (Exception e) {
				}
			}
		}, time);
	}

	public L1Location ������ǥ = null;
	public L1Location �����̵���ǥ = null;
	public boolean ������ = false;
	public boolean �������� = false;
	public boolean ������ = false;
	public L1NpcInstance ���ð������Npc = null;

	private void ���ú�() {
		// TODO �ڵ� ������ �޼ҵ� ����
		if (������) {
			if (��������) {
				if (���ð������Npc == null) {
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(5490).values()) {
						if (obj == null || !(obj instanceof L1NpcInstance))
							continue;
						L1NpcInstance npc = (L1NpcInstance) obj;
						if (���ð������Npc == null)
							���ð������Npc = npc;
						else {
							if (this.getLocation().getTileLineDistance(
									npc.getLocation()) < this.getLocation()
									.getTileLineDistance(
											���ð������Npc.getLocation()))
								���ð������Npc = npc;
						}
					}
				} else {
					if (isDistance(getX(), getY(), getMapId(),
							���ð������Npc.getX(), ���ð������Npc.getY(), getMapId(),
							1 + _random.nextInt(3))) {
						������ = true;
						return;
					}
					if (!isParalyzed()) {
						int dir = moveDirection(���ð������Npc.getX(),
								���ð������Npc.getY(), ���ð������Npc.getMapId());
						if (dir == -1) {
							cnt++;
							if (cnt > 30) {
								cnt = 0;
								���ð������Npc = null;
								return;
							}
						} else {
							boolean tail2 = World.isThroughObject(getX(),
									getY(), getMapId(), dir);
							boolean door = World.���̵�(
									getX(),
									getY(),
									getMapId(),
									calcheading(this, �����̵���ǥ.getX(),
											�����̵���ǥ.getY()));
							if (door || !tail2) {
								cnt++;
								if (cnt > 30) {
									cnt = 0;
									���ð������Npc = null;
									return;
								}
							}
							setDirectionMove(dir);
							setSleepTime(calcSleepTime(MOVE_SPEED));
						}
					}
				}
			}
			return;
		}
		if (������ǥ == null) {
			L1Map map = L1WorldMap.getInstance().getMap((short) 5490);
			int wi = 50;
			while (wi-- > 0) {
				boolean ck = false;
				int x = map.getX() + _random.nextInt(map.getWidth());
				int y = map.getY() + _random.nextInt(map.getHeight());
				if (map.getOriginalTile(x, y) == 28) {
					for (int h = 0; h < 8; h++) {
						if (map.isPassable(x + HEADING_TABLE_X[h], y
								+ HEADING_TABLE_X[h])) {
							ck = true;
							break;
						}
					}
					if (ck)
						continue;
					int i = 50;
					while (i-- > 0) {
						ck = false;
						int tx = x - 3 + _random.nextInt(7);
						int ty = y - 3 + _random.nextInt(7);
						if (map.isPassable(tx, ty)) {
							for (L1Object obj : L1World.getInstance()
									.getVisibleObjects(5490).values()) {
								if (obj == null)
									continue;
								if (obj.getX() == tx && obj.getY() == ty) {
									ck = true;
									break;
								}
							}
							if ((tx >= 32767 && tx <= 32769 && ty >= 32846 && ty <= 32856)
									|| (tx >= 32743 && tx <= 32754
											&& ty >= 32828 && ty <= 32830)
									|| (tx >= 32764 && tx <= 32766
											&& ty >= 32804 && ty <= 32815)
									|| (tx >= 32782 && tx <= 32794
											&& ty >= 32829 && ty <= 32831)) {
							} else if (!ck) {
								������ǥ = new L1Location(x, y, map);
								�����̵���ǥ = new L1Location(tx, ty, map);
								return;
							}
						}
					}
				}
			}
		} else {
			if ((getX() >= 32767 && getX() <= 32769 && getY() >= 32846 && getY() <= 32856)
					|| (getX() >= 32743 && getX() <= 32754 && getY() >= 32828 && getY() <= 32830)
					|| (getX() >= 32764 && getX() <= 32766 && getY() >= 32804 && getY() <= 32815)
					|| (getX() >= 32782 && getX() <= 32794 && getY() >= 32829 && getY() <= 32831)) {
			} else if (isDistance(getX(), getY(), getMapId(), ������ǥ.getX(),
					������ǥ.getY(), getMapId(), 1 + _random.nextInt(4))) {
				������ = true;
				int chdir = calcheading(this, ������ǥ.getX(), ������ǥ.getY());
				if (getMoveState().getHeading() != chdir) {
					this.getMoveState().setHeading(chdir);
					Broadcaster.broadcastPacket(this,
							new S_ChangeHeading(this), true);
				}
				Broadcaster.broadcastPacket(this, new S_Fishing(getId(),
						ActionCodes.ACTION_Fishing, ������ǥ.getX(), ������ǥ.getY()),
						true);
				fishX = ������ǥ.getX();
				fishY = ������ǥ.getY();
				setFishing(true);
				return;
			}
			if (!isParalyzed()) {
				int dir = moveDirection(�����̵���ǥ.getX(), �����̵���ǥ.getY(),
						�����̵���ǥ.getMapId());
				if (dir == -1) {
					cnt++;
					if (cnt > 30) {
						cnt = 0;
						������ǥ = null;
						�����̵���ǥ = null;
						return;
					}
				} else {
					boolean tail2 = World.isThroughObject(getX(), getY(),
							getMapId(), dir);
					boolean door = World.���̵�(getX(), getY(), getMapId(),
							calcheading(this, �����̵���ǥ.getX(), �����̵���ǥ.getY()));
					if (door || !tail2) {
						cnt++;
						if (cnt > 30) {
							cnt = 0;
							������ǥ = null;
							�����̵���ǥ = null;
							return;
						}
					}
					setDirectionMove(dir);
					setSleepTime(calcSleepTime(MOVE_SPEED));
				}
			}
		}
	}

	private int moveDirection(int x, int y, int m) {
		int dir = 0;
		try {
			aStar.cleanTail();
			tail = aStar.searchTail(this, x, y, m, true);
		} catch (Exception e) {
			return -1;
		}
		if (tail != null) {
			iCurrentPath = -1;
			while (!_���������� && tail != null) {
				if (tail.x == getX() && tail.y == getY()) {
					// ������ġ ��� ����
					break;
				}
				if (iCurrentPath >= 299 || isDead()) {
					return -1;
				}
				iPath[++iCurrentPath][0] = tail.x;
				iPath[iCurrentPath][1] = tail.y;
				tail = tail.prev;

			}
			if (iCurrentPath != -1) {
				return aStar.calcheading(getX(), getY(),
						iPath[iCurrentPath][0], iPath[iCurrentPath][1]);
			} else {
				return -1;
			}
		} else {

			try {
				aStar.cleanTail();
				int calcx = (int) getLocation().getX() - loc.getX();
				int calcy = (int) getLocation().getY() - loc.getY();
				if ((Math.abs(calcx) <= 15 && Math.abs(calcy) <= 15)
						&& loc != null) {
					tail = aStar.������ġŸ��(this, x, y, m, false);
				} else {
					tail = aStar.������ġŸ��(this, x, y, m, true);
				}
			} catch (Exception e) {
				return -1;
			}
			if (tail != null && !(tail.x == getX() && tail.y == getY())) {
				iCurrentPath = -1;
				while (!_���������� && tail != null) {
					if (tail.x == getX() && tail.y == getY()) {
						// ������ġ ��� ����
						break;
					}
					if (iCurrentPath >= 299 || isDead()) {
						// System.out.println(getName());
						return -1;
					}
					iPath[++iCurrentPath][0] = tail.x;
					iPath[iCurrentPath][1] = tail.y;
					tail = tail.prev;
				}
				if (iCurrentPath != -1) {
					return aStar.calcheading(getX(), getY(),
							iPath[iCurrentPath][0], iPath[iCurrentPath][1]);
				} else {
					dir = -1;
				}
			} else {
				dir = -1;
				if (!��ɺ�) {
					int chdir = calcheading(this, x, y);
					if (getMoveState().getHeading() != chdir) {
						this.getMoveState().setHeading(calcheading(this, x, y));
						Broadcaster.broadcastPacket(this, new S_ChangeHeading(
								this), true);
					}
				}
			}

			return dir;
		}
	}

	private static final byte HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
	private static final byte HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	private void setDirectionMove(int dir) {
		if (dir >= 0) {
			int nx = 0;
			int ny = 0;
			if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.THUNDER_GRAB)
					|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�������)
					|| getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�Ŀ��׸�)) {
				return;
			}
			// Broadcaster.broadcastPacket(this, new S_ChatPacket(this,
			// ""+��ɸ�.getId(), Opcodes.S_OPCODE_NORMALCHAT, 0));
			int heading = 0;
			nx = HEADING_TABLE_X[dir];
			ny = HEADING_TABLE_Y[dir];
			heading = dir;
			int nnx = getX() + nx;
			int nny = getY() + ny;

			if (World.isMapdynamic(nnx, nny, getMapId())) {
				return;
			}
			getMoveState().setHeading(heading);
			L1World.getInstance().Move(this, nnx, nny);
			getMap().setPassable(getLocation(), true);

			setX(nnx);
			setY(nny);
			getMap().setPassable(nnx, nny, false);
			S_MoveCharPacket mp = new S_MoveCharPacket(this);
			Broadcaster.broadcastPacket(this, mp, true);
			actionStatus = MOVE;
		}
	}

	private static final double HASTE_RATE = 0.745;
	private static final double WAFFLE_RATE = 0.874;
	private static final double THIRDSPEED_RATE = 0.874;

	public int calcSleepTime(int type) {
		int interval = 640;
		try {
			int gfxid = this.getGfxId().getTempCharGfx();
			int weapon = this.getCurrentWeapon();
			if (gfxid == 3784 || gfxid == 6137 || gfxid == 6142
					|| gfxid == 6147 || gfxid == 6152 || gfxid == 6157
					|| gfxid == 9205 || gfxid == 9206){
						
				if (weapon == 24) weapon = 83;
			}
			switch (type) {
			case ATTACK_SPEED:
				//interval = SprTable.getInstance().getAttackSpeed(gfxid, weapon +1);
				//if(interval < 406)
				interval = 426; //680-304  396
				if(weapon == 50) interval = 446;
				if(weapon == 20) interval = 436;
				break;
			case MOVE_SPEED:
				//interval = SprTable.getInstance().getMoveSpeed(gfxid, weapon);
				interval = 515; //515
				break;
			case MAGIC_SPEED:
				interval = SprTable.getInstance().getNodirSpellSpeed(gfxid);
				if (interval <= 0) {
					interval = 120;
				}
				break;
			case DMG_MOTION_SPEED:
				interval = SprTable.getInstance().getDmgMotionSpeed(gfxid);
				if (interval <= 0) {
					interval = 120;
				}
				break;
			default:
				interval = SprTable.getInstance().getMoveSpeed(gfxid, weapon);
				break;
			}
	
			if (gfxid == 13719 || gfxid == 13725 || gfxid == 13735) {// �κ����Ӽ���
					interval += 90; //��Ŀ���� �ӵ�������
				   
				}
			
			/*if (type != MOVE_SPEED) {
				if (gfxid >= 11328 && gfxid <= 13635) {// �κ����Ӽ���
					if (getLevel() >= 15)
						interval -= 43;
					if (getLevel() >= 30)
						interval -= 43;
					if (getLevel() >= 45)
						interval -= 34;
					if (getLevel() >= 50)
						interval -= 34;
					if (getLevel() >= 52)
						interval -= 25;
					if (getLevel() >= 55)
						interval -= 24;
					if (getLevel() >= 60)
						interval -= 22;
					if (getLevel() >= 65)
						interval -= 21;
					if (getLevel() >= 70)
						interval -= 16;
					if (getLevel() >= 75)
						interval -= 16;
					if (getLevel() >= 80)
						interval -= 16;
				}
			}*/
			if (this.isHaste() || getMoveState().getMoveSpeed() == 1) {
				interval *= HASTE_RATE;
			}
			if (type == MOVE_SPEED && this.isFastMovable()) {
				interval *= HASTE_RATE;
			}
			if (type == MOVE_SPEED && this.isIllusionist()
					&& this.isUgdraFruit()) {
				interval *= HASTE_RATE;
			}
			if (this.isBloodLust()) { // ���巯��Ʈ
				interval *= HASTE_RATE;
			}
			if (this.isBrave()) {
				interval *= HASTE_RATE;
			}
			if (this.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.FIRE_BLESS)) {
				interval *= HASTE_RATE;
			}
			if (this.isElfBrave()) {
				interval *= WAFFLE_RATE;
			}
			if (this.isThirdSpeed()) {
				interval *= THIRDSPEED_RATE;
			}
			if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WIND_SHACKLE)) {
				if (type == ATTACK_SPEED) {
					interval *= 2;
				}
			}
			if (getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SLOW)) {
				interval *= 2;
			}

			if (type == ATTACK_SPEED) {
				int[] list = { 100, 110, 120 };
				interval += list[_random.nextInt(3)];
				list = null;
			} else {
				int[] list = { 30, 40, 50 };
				interval += list[_random.nextInt(3)];
				list = null;
			}

		} catch (Exception e) {
			// e.printStackTrace();
			if (interval == 0)
				return 640;
		}
		return interval;
	}

	public long ������ = 0;
	public int �̵������� = 0;

	public void ������(int i) {
		������ = i;
		// ������ = System.currentTimeMillis() + i;
	}

	private int cnt = 0;

	public void �������̵�() {
		if (������_�̵� == 1) {
			if (loc == null) {
				if (������_������ġ == 2 || ������_������ġ == 4 || ������_������ġ == 5
					|| ������_������ġ == 9 || ������_������ġ == 8) // ���
					loc = new Robot_Location_bean(33437, 32804, 4);
				else if (������_������ġ == 6 || ������_������ġ == 7) // ���̳�
					loc = new Robot_Location_bean(33613, 33248, 4);
				/*else if (������_������ġ == 8) // ��� ����
					loc = new Robot_Location_bean(32693, 32794, 450);
				else if (������_������ġ == 9) // ���
					loc = new Robot_Location_bean(32640, 33183, 4);*/
				else if (������_������ġ == 10 || ������_������ġ == 11) // �۷��
					loc = new Robot_Location_bean(32609, 32738, 4);
				else if (������_������ġ == 12) // ����
					loc = new Robot_Location_bean(32587, 32929, 0);
				else if (������_������ġ == 13) // �����
					loc = new Robot_Location_bean(33089, 33393, 4);
				else if (������_������ġ == 14) // ����
					loc = new Robot_Location_bean(34065, 32280, 4);
				else if (������_������ġ == 15) // �Ƶ�
					loc = new Robot_Location_bean(33938, 33358, 4);
			}
		} else if (������_�̵� == 2) {
			if (loc == null) {
				if (������_������ġ == 2 || ������_������ġ == 4 || ������_������ġ == 5
					|| ������_������ġ == 9 || ������_������ġ == 8) // ���
					loc = new Robot_Location_bean(33437, 32795, 4);
				else if (������_������ġ == 6 || ������_������ġ == 7) // ���̳�
					loc = new Robot_Location_bean(33613, 33257, 4);
				/*else if (������_������ġ == 8) // ��� ����
					loc = new Robot_Location_bean(32685, 32795, 450);
				else if (������_������ġ == 9) // ���
					loc = new Robot_Location_bean(32640, 33189, 4);*/
				else if (������_������ġ == 10 || ������_������ġ == 11) // �۷��
					loc = new Robot_Location_bean(32611, 32732, 4);
				else if (������_������ġ == 12) // ����
					loc = new Robot_Location_bean(32583, 32922, 0);
				else if (������_������ġ == 13) // �����
					loc = new Robot_Location_bean(33089, 33396, 4);
				else if (������_������ġ == 14) // ����
					loc = new Robot_Location_bean(34063, 32278, 4);
				else if (������_������ġ == 15) // �Ƶ�
					loc = new Robot_Location_bean(33934, 33351, 4);
			}
		}
		if (loc == null)
			return;

		if (isDistance(getX(), getY(), getMapId(), loc.getX(), loc.getY(),
				getMapId(), 1 + _random.nextInt(3))) {
			loc = null;
			if (������_�̵� == 1) {
				������_�̵� = 2;
				����������();
			} else {
				������_�̵� = 0;
				��(32750, 32809, 39, 1000 + _random.nextInt(3000));
				_���������� = true;
				stopHalloweenRegeneration();
				stopPapuBlessing();
				stopLindBlessing();
				stopHalloweenArmorBlessing();
				stopAHRegeneration();
				stopHpRegenerationByDoll();
				stopMpRegenerationByDoll();
				stopSHRegeneration();
				stopMpDecreaseByScales();
				stopEtcMonitor();
			}
			setSleepTime(4000 + _random.nextInt(2000));
			return;
		}
		if (loc == null)
			return;
		if (!isParalyzed()) {
			int dir = moveDirection(loc.getX(), loc.getY(), loc.getMapId());
			if (dir == -1) {
				cnt++;
			} else {
				boolean tail2 = World.isThroughObject(getX(), getY(),
						getMapId(), dir);
				boolean door = World.���̵�(getX(), getY(), getMapId(),
						calcheading(this, loc.getX(), loc.getY()));
				if (door || !tail2) {
					cnt++;
				}
				setDirectionMove(dir);
				setSleepTime(calcSleepTime(MOVE_SPEED));
			}
		}
	}

	/*private static final int[] ������BuffSkill4 = {
			L1SkillId.PHYSICAL_ENCHANT_STR, L1SkillId.PHYSICAL_ENCHANT_DEX,
			L1SkillId.BLESS_WEAPON, L1SkillId.REMOVE_CURSE
			};*/
	
	
	private void ��â(){
		try{
		_glment = glmentArray[_random.nextInt(glmentArray.length)];
		Delay(1500);
		for (L1PcInstance listner : L1World.getInstance().getAllPlayers()){
		S_ChatPacket cp = new S_ChatPacket(this, _glment, Opcodes.S_MESSAGE, 3);
		listner.sendPackets(cp, true);
		setGlsaid(true);
		listner = null; //��������
		cp = null;
		}
		}catch(Exception e){
		     return;
		}
		}

	

	private void ����������() {
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				// TODO �ڵ� ������ �޼ҵ� ����
				try {
					int[] skillt = ������BuffSkill4;
					if (_random.nextInt(2) == 0) {
						for (Integer i : skillt) {
							L1Skills skill = SkillsTable.getInstance()
									.getTemplate(i);
							if (i == L1SkillId.HASTE)
								new L1SkillUse().handleCommands(
										L1RobotInstance.this, i,
										L1RobotInstance.this.getId(),
										L1RobotInstance.this.getX(),
										L1RobotInstance.this.getY(), null, 0,
										L1SkillUse.TYPE_GMBUFF);
							else
								Broadcaster.broadcastPacket(
										L1RobotInstance.this, new S_SkillSound(
												L1RobotInstance.this.getId(),
												skill.getCastGfx()), true);
						}
						Thread.sleep(1000 + _random.nextInt(1000));
						// �������
						// Broadcaster.broadcastPacket(L1RobotInstance.this, new
						// S_SkillSound(L1RobotInstance.this.getId(), 4914),
						// true);
					} else {
						// �������
						// Broadcaster.broadcastPacket(L1RobotInstance.this, new
						// S_SkillSound(L1RobotInstance.this.getId(), 4914),
						// true);
						Thread.sleep(1000 + _random.nextInt(1000));
						for (Integer i : skillt) {
							L1Skills skill = SkillsTable.getInstance()
									.getTemplate(i);
							if (i == L1SkillId.HASTE)
								new L1SkillUse().handleCommands(
										L1RobotInstance.this, i,
										L1RobotInstance.this.getId(),
										L1RobotInstance.this.getX(),
										L1RobotInstance.this.getY(), null, 0,
										L1SkillUse.TYPE_GMBUFF);
							else
								Broadcaster.broadcastPacket(
										L1RobotInstance.this, new S_SkillSound(
												L1RobotInstance.this.getId(),
												skill.getCastGfx()), true);
						}
					}
				} catch (Exception e) {
				}
			}

		}, 1000 + _random.nextInt(1000));
	}

	/**
	 * �Ÿ��� ����.
	 * 
	 * @param o
	 * @param oo
	 * @return
	 */
	public int getDistance(int x, int y, int tx, int ty) {
		long dx = tx - x;
		long dy = ty - y;
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * �Ÿ��ȿ� �ִٸ� ��
	 */
	public boolean isDistance(int x, int y, int m, int tx, int ty, int tm,
			int loc) {
		int distance = getDistance(x, y, tx, ty);
		if (loc < distance)
			return false;
		if (m != tm)
			return false;
		return true;
	}

	/**
	 * �ش��ϴ� ��ǥ�� ������ ��ȯ�Ҷ� ���.
	 */
	public int calcheading(int myx, int myy, int tx, int ty) {
		if (tx > myx && ty > myy) {
			return 3;
		} else if (tx < myx && ty < myy) {
			return 7;
		} else if (tx > myx && ty == myy) {
			return 2;
		} else if (tx < myx && ty == myy) {
			return 6;
		} else if (tx == myx && ty < myy) {
			return 0;
		} else if (tx == myx && ty > myy) {
			return 4;
		} else if (tx < myx && ty > myy) {
			return 5;
		} else {
			return 1;
		}
	}
	
	

	public void updateban(boolean swich) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE robots SET ban = ? WHERE name = ?");
			if (swich) {
				pstm.setInt(1, 1);
			} else {
				pstm.setInt(1, 0);
			}
			pstm.setString(2, getName());
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public int _step = 0;

	public void updateconnect(boolean swich) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE robots SET connect = ?,step = ?,map = ? WHERE name = ?");
			if (swich) {
				pstm.setInt(1, 1);
				pstm.setInt(2, _step);
				pstm.setInt(3, 4);
			} else {
				pstm.setInt(1, 0);
				pstm.setInt(2, 0);
				pstm.setInt(3, 0);
			}
			pstm.setString(4, getName());
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void updateclan(String ���̸�, int clanid, String ȣĪ, boolean swich) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("UPDATE robots SET clanname = ?,clanid = ?,title = ? WHERE name = ?");
			if (swich) {
				pstm.setString(1, ���̸�);
				pstm.setInt(2, clanid);
				pstm.setString(3, ȣĪ);
			} else {
				pstm.setString(1, "");
				pstm.setInt(2, 0);
				pstm.setString(3, "");
			}
			pstm.setString(4, getName());
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public int calcheading(L1Object o, int x, int y) {
		return calcheading(o.getX(), o.getY(), x, y);
	}

	public synchronized int _serchCource(int x, int y) {
		int courceRange = 10;
	
		int i;
		int locCenter = courceRange + 1;
		int mapId = getMapId();
		int diff_x = x - locCenter;
		int diff_y = y - locCenter;
		int[] locBace = { getX() - diff_x, getY() - diff_y, 0, 0 };
		int[] locNext = new int[4];
		int[] locCopy;
		int[] dirFront = new int[5];
		boolean serchMap[][] = new boolean[locCenter * 2 + 1][locCenter * 2 + 1];
		LinkedList queueSerch = new LinkedList();

		for (int j = courceRange * 2 + 1; j > 0; j--) {
			for (i = courceRange - Math.abs(locCenter - j); i >= 0; i--) {
				serchMap[j][locCenter + i] = true;
				serchMap[j][locCenter - i] = true;
			}
		}
		// 32666 32820 32647 32795 19 25
		// locbase = ������ǥ - (Ÿ����ǥ-25)
		// locNext�� ����
		// locNext�� ��ĭ�̵�
		// locCenter = 26;
		int[] firstCource = { 2, 4, 6, 0, 1, 3, 5, 7 };
		for (i = 0; i < 8; i++) {
			System.arraycopy(locBace, 0, locNext, 0, 4);
			_moveLocation(locNext, firstCource[i]);
			if (locNext[0] - locCenter == 0 && locNext[1] - locCenter == 0)
				return firstCource[i];
			if (serchMap[locNext[0]][locNext[1]]) {
				if (World.isMapdynamic(locNext[0] + diff_x,
						locNext[1] + diff_y, mapId) == false) {
					locCopy = new int[4];
					System.arraycopy(locNext, 0, locCopy, 0, 4);
					locCopy[2] = firstCource[i];
					locCopy[3] = firstCource[i];
					queueSerch.add(locCopy);
				}
				serchMap[locNext[0]][locNext[1]] = false;
			}
		}
		locBace = null;
		while (queueSerch.size() > 0) {
			locBace = (int[]) queueSerch.removeFirst();
			_getFront(dirFront, locBace[2]);
			for (i = 4; i >= 0; i--) {
				System.arraycopy(locBace, 0, locNext, 0, 4);
				_moveLocation(locNext, dirFront[i]);
				if (locNext[0] - locCenter == 0 && locNext[1] - locCenter == 0)
					return locNext[3];
				if (serchMap[locNext[0]][locNext[1]]) {
					if (World.isMapdynamic(locNext[0] + diff_x, locNext[1]
							+ diff_y, mapId) == false) {
						locCopy = new int[4];
						System.arraycopy(locNext, 0, locCopy, 0, 4);
						locCopy[2] = dirFront[i];
						queueSerch.add(locCopy);
					}
					serchMap[locNext[0]][locNext[1]] = false;
				}
			}
			locBace = null;
		}
		return -1;
	}
	
	

	private void _moveLocation(int[] ary, int d) {
		ary[0] = ary[0] + HEADING_TABLE_X[d];
		ary[1] = ary[1] + HEADING_TABLE_Y[d];
		ary[2] = d;
	}

	private void _getFront(int[] ary, int d) {
		switch (d) {
		case 1:
			ary[4] = 2;
			ary[3] = 0;
			ary[2] = 1;
			ary[1] = 3;
			ary[0] = 7;
			break;
		case 2:
			ary[4] = 2;
			ary[3] = 4;
			ary[2] = 0;
			ary[1] = 1;
			ary[0] = 3;
			break;
		case 3:
			ary[4] = 2;
			ary[3] = 4;
			ary[2] = 1;
			ary[1] = 3;
			ary[0] = 5;
			break;
		case 4:
			ary[4] = 2;
			ary[3] = 4;
			ary[2] = 6;
			ary[1] = 3;
			ary[0] = 5;
			break;
		case 5:
			ary[4] = 4;
			ary[3] = 6;
			ary[2] = 3;
			ary[1] = 5;
			ary[0] = 7;
			break;
		case 6:
			ary[4] = 4;
			ary[3] = 6;
			ary[2] = 0;
			ary[1] = 5;
			ary[0] = 7;
			break;
		case 7:
			ary[4] = 6;
			ary[3] = 0;
			ary[2] = 1;
			ary[1] = 5;
			ary[0] = 7;
			break;
		case 0:
			ary[4] = 2;
			ary[3] = 6;
			ary[2] = 0;
			ary[1] = 1;
			ary[0] = 7;
			break;
		default:
			break;
		}
	}
}