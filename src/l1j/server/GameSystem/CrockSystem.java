package l1j.server.GameSystem;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import l1j.server.server.datatables.EvaSystemTable;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1EvaSystem;
import l1j.server.server.utils.L1SpawnUtil;

public class CrockSystem extends Thread {

	private static CrockSystem _instance;

	public static CrockSystem getInstance() {
		if (_instance == null) {
			_instance = new CrockSystem();
		}
		return _instance;
	}

	L1EvaSystem eva = EvaSystemTable.getInstance().getSystem(1);
	/**
	 * �տ� �ð� ����
	 */

	/** �տ��� ���ȴ��� �ƴ��� */
	private boolean isOpen = false;

	/** ���� �ð��� ���� �Ǿ����� */
	private boolean isBossTime = false;

	/** �ð��� �տ� �׺� ���� Ƚ�� */
	private static int dieCount = 0;

	/** ������ �ο� ī��Ʈ (���� 20�� �� ���� ��ȯ �ص� ���� �ȵ�) **/
	public int in_count = 0;

	/** �տ� ��ǥ */
	private static final int[][] loc = { { 32852, 32709, 4 },
			{ 32729, 32702, 4 }, { 32906, 33174, 4 }, { 32959, 33254, 4 },
			{ 34254, 33206, 4 }, { 34223, 33316, 4 }, { 32912, 33429, 4 },
			{ 34266, 33367, 4 }, { 32832, 32650, 4 } };
	// 33258 32742 783
	/** ������ ������ 20���� ��� ���� ����Ʈ */
	private static ArrayList<L1PcInstance> sList = new ArrayList<L1PcInstance>();

	public void reload() {
		EvaSystemTable.getInstance().reload(eva);
	}

	private CrockSystem() {
		super("l1j.server.GameSystem.CrockSystem.CrockSystem");
		if (eva.getOpenContinuation() == 1) {
			isOpen = true;
			ready();
		}
		start();
	}

	private int ckck = 5;

	public void run() {
		while (true) {
			try {
				if (ckck-- < 1) {
					if (size() > 0) {
						L1PcInstance[] list = toArray�տ�();
						for (L1PcInstance mem : list) {
							if (mem != null && mem.getNetConnection() != null) {
								if (mem.getMapId() != 782
										&& mem.getMapId() != 784) {
									del(mem);
								}
							}
						}
						list = null;
					}
					ckck = 5;
					// System.out.println(in_count+" >> "+size());
				}

				// System.out.println(in_count+" >> "+size());
				if (System.currentTimeMillis() >= eva.getEvaTime()
						.getTimeInMillis()) {
					openCrock(eva.getEvaTime().getTimeInMillis());
					if (eva.getOpenContinuation() == 0
							&& eva.bossTime < System.currentTimeMillis()) {
						boss();
					}
					if (eva.getOpenContinuation() == 0) {
						bosscheck();
					} else if (eva.getOpenContinuation() == 1) {
						close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					sleep(1000);
				} catch (Exception e) {
				}
			}
		}
	}

	private static final S_ServerMessage sm1469 = new S_ServerMessage(1469);

	private void openCrock(long time) {
		if (!isOpen()) {
			OpenTime = time;
			// System.out.println("�׺�����");
			setOpen(true);
			ready();
			L1World.getInstance().broadcastPacketToAll(sm1469);// ���ȴ�~
			in_count = 0;
		}
	}

	private boolean boss_room_in = false;
	private int boss_room_in_check_count = 0;

	private void boss() {
		try {
			if (!isBossTime()) {
				setBossTime(true);
				// System.out.println("���� Ÿ��!!");
			} else {
				if (!boss_room_in) {
					if (boss_room_in_check_count-- <= 0) {
						// System.out.println("���� Ÿ�� �濡�� �ƹ��� ����.");
						boss_room_in_check_count = 20;
					}
					if (sList.size() > 0) {
						boss_room_in = true;
						// System.out.println("���� Ÿ�� �濡 �ο����� ����~");
						sleep(2000);
						if (eva.getMoveLocation() == 1) { // �׺�
							sendSystemChat("���ø��� : ����� �͵�..�̰��� �����!! �ƴ���! ȣ�罺! ���͵��� ���������!!");
							sleep(3000);
							sendSystemChat("�ƴ��� : ���񿡰� ������....");
							sendSystemChat("ȣ�罺 : �ں�� ����....");
						} else if (eva.getMoveLocation() == 2) { // ƼĮ
							sendSystemChat("����ĭ : ���� �̰��� �����ٴ�!! ���� ����!! ����Ŷ�!!");
							sleep(3000);
							sendSystemChat("���� ���� : ������������....����������....");
							sendSystemChat("���� ���� : ����������....����������...");
						}
						bossStart();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bosscheck() {
		try {
			if (isOpen()) {
				if (isBossDie()) {
					// System.out.println("�������� �ð�����");
					if (eva.getMoveLocation() == 1) { // �׺�
						sendSystemChat("�׺� ���ø��� : �̷�����..!!! �츮�� ����.");
						sleep(2000);
						sendSystemChat("�׺� ���ø��� : ���� �� �ð����� �Ϸ� ���� �׺��󽺸� �����ϵ��� �ϰڴ�.");
					} else if (eva.getMoveLocation() == 2) { // ƼĮ
						sendSystemChat("����ĭ : �̷�����..!!! �츮�� ����.");
						sleep(2000);
						sendSystemChat("����ĭ : ���� �� �ð����� �Ϸ� ���� ƼĮ����� �����ϵ��� �ϰڴ�.");
					}

					eva.bosscheck += (long) ((long) 60000 * (long) 60 * (long) 19);
					CrockContinuation();
				} else if (eva.bosscheck < System.currentTimeMillis()) {
					if (isBossTime()) {
						if (eva.getMoveLocation() == 1) { // �׺�
							sendSystemChat("�׺� ���ø��� : ������� �����ߴ�!!!");
						} else if (eva.getMoveLocation() == 2) { // ƼĮ
							sendSystemChat("����ĭ : ������� ������ ���� ������� ��� �������!!!");
						}
					}
					// System.out.println("�׳�... Ŭ����");
					setOpen(false);
					setBossTime(false);
					clear();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close() {
		if (isOpen()) {
			if (System.currentTimeMillis() > eva.bosscheck) {
				// System.out.println("����ð�����");
				setOpen(false);
				setBossTime(false);
				clear();
			}
		}
	}

	private void ready() {
		if (eva.getMoveLocation() == 0) {
			eva.setOpenLocation((int) (Math.random() * 8));
			eva.setMoveLocation((int) (Math.random() * 2 + 1));
		}
		int OL = eva.getOpenLocation();
		L1SpawnUtil.spawn2(loc[OL][0], loc[OL][1], (short) loc[OL][2], 4500100,
				0, 0, 0);// ��ġ�� �����Ѵ�
	}

	private void bossStart() {
		// ������ �����ϰ� ���� Ÿ���� ���
		// System.out.println("���� ����!! "+eva.getMoveLocation());
		switch (eva.getMoveLocation()) {
		case 1:// �׺�
			L1SpawnUtil.spawn2(32794, 32825, (short) 782, 400016, 0,
					(3600 * 1000) * 3, 0);
			L1SpawnUtil.spawn2(32794, 32836, (short) 782, 400017, 0,
					(3600 * 1000) * 3, 0);
			break;
		case 2:// ƼĮ
			L1SpawnUtil.spawn2(32753, 32870, (short) 784, 4036016, 0,
					(3600 * 1000) * 3, 0);
			L1SpawnUtil.spawn2(32751, 32859, (short) 784, 4036017, 0,
					(3600 * 1000) * 3, 0);
			break;
		default:
			break;
		}
	}

	private static final S_ServerMessage sm1468 = new S_ServerMessage(1468);
	private static final S_ServerMessage sm1467 = new S_ServerMessage(1467);

	private void clear() {
		try {
			// ��� ���¸� �ʱ�ȭ �Ѵ� �׸��� ���� ������ �غ��Ѵ�
			eva.setOpenLocation(0);
			eva.setMoveLocation(0);
			eva.setOpenContinuation(0);
			boss_room_in = false;
			// long longtime = OpenTime;
			Calendar cal = (Calendar) Calendar.getInstance().clone();
			cal.setTimeInMillis(eva.getEvaTime().getTimeInMillis()
					+ (long) ((long) 60000 * (long) 60 * (long) 24 * (long) 2));
			eva.setEvaTime(cal);
			eva.bossTime = eva.getEvaTime().getTimeInMillis()
					+ (long) ((long) 60000 * (long) 60 * (long) 2)
					+ ((long) 60000 * (long) 30);
			eva.bosscheck = eva.getEvaTime().getTimeInMillis()
					+ (long) ((long) 60000 * (long) 60 * (long) 4);
			EvaSystemTable.getInstance().updateSystem(eva);

			L1World.getInstance().broadcastPacketToAll(sm1467);// �ð��� �տ��� ��
																// �����ϴ�.
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getInventory().checkItem(L1ItemId.TEBEOSIRIS_KEY, 1))
					pc.getInventory().consumeItem(L1ItemId.TEBEOSIRIS_KEY, 1);
			}
			teleportMsg();
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc == null)
					continue;
				if (pc.getInventory().checkItem(L1ItemId.TEBEOSIRIS_KEY, 1))
					pc.getInventory().consumeItem(L1ItemId.TEBEOSIRIS_KEY, 1);
				if (pc.getMap().getId() >= 780 && pc.getMap().getId() <= 784) {
					L1Teleport.teleport(pc, 33970, 33246, (short) 4, 4, true);
				}
			}
			dieCount = 0;
			if (sList.size() > 0)
				sList.clear();
			L1World.getInstance().broadcastPacketToAll(sm1468);// �ð��� �տ��� ������ϴ�
			crockDelete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void crockDelete() {
		L1FieldObjectInstance f = null;
		for (L1Object l1object : L1World.getInstance().getObject()) {
			if (l1object != null && l1object instanceof L1FieldObjectInstance) {
				f = (L1FieldObjectInstance) l1object;
				if (f.getNpcTemplate().get_npcId() == 4500100
						&& l1object != null)
					f.deleteMe();
			}
		}
	}

	private void teleportMsg() {
		try {
			sleep(2000);
			sendSystemChat("�ý��� �޽��� : 30�� �Ŀ� �ڷ���Ʈ �մϴ�.");
			sleep(10000);
			sendSystemChat("�ý��� �޽��� : 20�� �Ŀ� �ڷ���Ʈ �մϴ�.");
			sleep(10000);
			sendSystemChat("�ý��� �޽��� : 10�� �Ŀ� �ڷ���Ʈ �մϴ�.");
			sleep(5000);
			sendSystemChat("�ý��� �޽��� : 5�� �Ŀ� �ڷ���Ʈ �մϴ�.");
			sleep(1000);
			sendSystemChat("�ý��� �޽��� : 4�� �Ŀ� �ڷ���Ʈ �մϴ�.");
			sleep(1000);
			sendSystemChat("�ý��� �޽��� : 3�� �Ŀ� �ڷ���Ʈ �մϴ�.");
			sleep(1000);
			sendSystemChat("�ý��� �޽��� : 2�� �Ŀ� �ڷ���Ʈ �մϴ�.");
			sleep(1000);
			sendSystemChat("�ý��� �޽��� : 1�� �Ŀ� �ڷ���Ʈ �մϴ�.");
			sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������ �Ѵ� ������ ���� �ְ� ������� �����Ѵ�
	 */
	private static final S_ServerMessage sm1470 = new S_ServerMessage(1470);

	public void CrockContinuation() {
		try {
			if (eva.getMoveLocation() == 2)
				BossDieBuff();// ������ �ְ�
			eva.setOpenContinuation(1);// ���� ���¸� ����
			EvaSystemTable.getInstance().updateExtend(1);
			L1World.getInstance().broadcastPacketToAll(sm1470);// �ð��� �տ��� ������ϴ�
			teleportMsg();

			L1PcInstance[] list5 = toArray�տ�();
			if (list5.length > 0) {
				for (L1PcInstance pc : list5) {
					if (pc == null)
						continue;
					switch (pc.getMapId()) {
					case 782:
						L1Teleport.teleport(pc, 32628, 32906, (short) 780, 5,
								true);
						break;
					case 784:
						L1Teleport.teleport(pc, 32793, 32754, (short) 783, 2,
								false);
						break;
					default:
						break;
					}
				}
			}
			list5 = null;
			synchronized (sList) {
				if (sList.size() > 0)
					sList.clear();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * �ð��� �տ� �������� Ȯ��
	 * 
	 * @return (boolean) 2������ �׾��ٸ� ture 1���� ���� �׿��ٸ� false
	 */
	private boolean isBossDie() {
		boolean sTemp = false;
		switch (dieCount()) {
		case 2:
			sTemp = true;
			break;
		default:
			sTemp = false;
			break;
		}
		return sTemp;
	}

	/**
	 * �ð��� �տ� �׺� ���� ���� �ݳ�
	 * 
	 * @return (int) dieCount ���� ���� Ƚ��
	 */
	public int dieCount() {
		return dieCount;
	}

	public void dieCount(int dieCount) {
		CrockSystem.dieCount = dieCount;
	}

	/**
	 * �ð��� �տ� �̵� ����
	 * 
	 * @return (boolean) move �̵� ����
	 */
	public boolean isOpen() {
		return isOpen;
	}

	private void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	/**
	 * �׺��� ƼĮ�� ���� Ÿ������ ����
	 * 
	 * @return
	 */
	public boolean isBossTime() {
		return isBossTime;
	}

	private void setBossTime(boolean isBossTime) {
		this.isBossTime = isBossTime;
	}

	public boolean isContinuationTime() {
		if (eva.getOpenContinuation() == 0)
			return false;
		else
			return true;
	}

	/**
	 * ������ npcId �� ���� loc �� �ݳ�
	 * 
	 * @return (int[]) loc ��ǥ �迭
	 */
	public int[] loc() {
		return loc[eva.getOpenLocation()];
	}

	/**
	 * ������ 20�� ���
	 */
	public void add(L1PcInstance c) {
		/** ��ϵǾ� ���� �ʰ� */
		synchronized (sList) {
			if (!sList.contains(c)) {
				/** ������ 20�� ���϶�� */
				if (sList.size() < 20)
					sList.add(c);
			}
		}
	}

	public void del(L1PcInstance c) {
		synchronized (sList) {
			/** ��ϵǾ� ���� �ʰ� */
			if (sList.contains(c)) {
				/** ������ 20�� ���϶�� */
				sList.remove(c);
			}
		}
	}

	public void sendSystemChat(String msg) {
		L1PcInstance[] list = toArray�տ�();
		if (list.length > 0) {
			S_SystemMessage smMsg = new S_SystemMessage(msg);
			for (L1PcInstance pc : toArray�տ�()) {
				if (pc == null || pc.getNetConnection() == null)
					continue;
				if (pc.getMapId() != 782 && pc.getMapId() != 784)
					del(pc);
				else
					pc.sendPackets(smMsg);
			}
		}
	}

	public static L1PcInstance[] toArray�տ�() {
		L1PcInstance[] list = null;
		synchronized (sList) {
			list = sList.toArray(new L1PcInstance[sList.size()]);
		}
		return list;
	}

	/**
	 * ������ ����Ʈ ������ �ݳ�
	 * 
	 * @return (int) sList �� ������
	 */
	public int size() {
		synchronized (sList) {
			return sList.size();
		}
	}

	/**
	 * ƼĮ ������ �������� ���� �Ǿ� �������� ������ �ش�.
	 */
	public void BossDieBuff() {
		L1PcInstance[] list = toArray�տ�();
		for (L1PcInstance pc : list) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_TIKAL_BOSSJOIN))
				pc.getSkillEffectTimerSet().removeSkillEffect(
						L1SkillId.STATUS_TIKAL_BOSSJOIN);
		}
		list = null;
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			if (pc == null || pc.getNetConnection() == null
					|| pc.isPrivateShop()) {
				continue;
			}
			L1SkillUse su = new L1SkillUse();
			su.handleCommands(pc, L1SkillId.STATUS_TIKAL_BOSSDIE, pc.getId(),
					pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_SPELLSC);
			su = null;
		}
	}

	/**
	 * ������ ������ ���� �������� �����ش�
	 * 
	 * @return true : ����
	 */
	public boolean isCrockIng() {
		if (eva.getOpenContinuation() == 1)
			return true;
		else
			return false;
	}

	/** �ð� ������ ���� */
	private static final SimpleDateFormat ss = new SimpleDateFormat(
			"MM-dd HH:mm", Locale.KOREA);
	private Timestamp ts = new Timestamp(System.currentTimeMillis());
	private long OpenTime = 0;// ���½ð�

	public String OpenTime() {
		String resul = "��� �Ұ���";
		if (OpenTime == 0) {
			return resul;
		} else {
			ts.setTime(OpenTime);
			return ss.format(ts);
		}
	}
}
