package l1j.server.GameSystem.Papoo;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.ADVANCE_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.ARMOR_BREAK;
import static l1j.server.server.model.skill.L1SkillId.BLESSED_ARMOR;
import static l1j.server.server.model.skill.L1SkillId.BOUNCE_ATTACK;
import static l1j.server.server.model.skill.L1SkillId.COOKING_BEGIN;
import static l1j.server.server.model.skill.L1SkillId.COOKING_END;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.DRESS_EVASION;
import static l1j.server.server.model.skill.L1SkillId.ENCHANT_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.IllUSION_AVATAR;
import static l1j.server.server.model.skill.L1SkillId.IllUSION_DIAMONDGOLEM;
import static l1j.server.server.model.skill.L1SkillId.IllUSION_LICH;
import static l1j.server.server.model.skill.L1SkillId.IllUSION_OGRE;
import static l1j.server.server.model.skill.L1SkillId.REDUCTION_ARMOR;
import static l1j.server.server.model.skill.L1SkillId.SCALES_EARTH_DRAGON;
import static l1j.server.server.model.skill.L1SkillId.SCALES_FIRE_DRAGON;
import static l1j.server.server.model.skill.L1SkillId.SCALES_WATER_DRAGON;
import static l1j.server.server.model.skill.L1SkillId.SHADOW_ARMOR;
import static l1j.server.server.model.skill.L1SkillId.SHADOW_FANG;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_STUN;
import static l1j.server.server.model.skill.L1SkillId.SKILLS_BEGIN;
import static l1j.server.server.model.skill.L1SkillId.SKILLS_END;
import static l1j.server.server.model.skill.L1SkillId.SOLID_CARRIAGE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_BEGIN;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CANCLEEND;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_BARLOG;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_YAHEE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_DRAGONPERL;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FREEZE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_UNDERWATER_BREATH;
import static l1j.server.server.model.skill.L1SkillId.UNCANNY_DODGE;
import static l1j.server.server.model.skill.L1SkillId.�������;
import static l1j.server.server.model.skill.L1SkillId.�Ŀ��׸�;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import l1j.server.GameSystem.Papoo.PaPooRaidSystem.FafulionMsgTimer;
import l1j.server.GameSystem.Papoo.PaPooRaidSystem.papoomsg;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_Invis;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillIconBlessOfEva;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Sound;
import l1j.server.server.utils.L1SpawnUtil;

public class PaPooRaid {
	/*
	 * private L1Party Party1 = null; private L1Party Party2 = null; private
	 * L1Party Party3 = null; private L1Party Party4 = null;
	 */
	private final Map<Integer, Boolean> MiniBoss1 = new ConcurrentHashMap<Integer, Boolean>();
	private final Map<Integer, Boolean> MiniBoss2 = new ConcurrentHashMap<Integer, Boolean>();
	private final Map<Integer, Boolean> MiniBoss3 = new ConcurrentHashMap<Integer, Boolean>();

	public boolean MiniRoom1 = false;
	private boolean MiniRoom2 = false;
	private boolean MiniRoom3 = false;
	private boolean MiniRoom4 = false;

	private final ArrayList<L1PcInstance> _PaPoolist = new ArrayList<L1PcInstance>();

	private int _id;
	private long _endtime;
	private boolean _isPaPoo = false;
	private L1NpcInstance PaPoo = null;
	private L1NpcInstance _�翤 = null;
	private L1NpcInstance _�翤2 = null;
	private Random random = new Random(System.nanoTime());

	public PaPooRaid(int id) {
		_id = id;
		_endtime = System.currentTimeMillis() + 7200000;// 2�ð� ��
		MiniBoss1.put(1, false);
		MiniBoss1.put(2, false);
		MiniBoss1.put(3, false);
		MiniBoss1.put(4, false);
		MiniBoss2.put(1, false);
		MiniBoss2.put(2, false);
		MiniBoss2.put(3, false);
		MiniBoss2.put(4, false);
		MiniBoss3.put(1, false);
		MiniBoss3.put(2, false);
		MiniBoss3.put(3, false);
		MiniBoss3.put(4, false);
		GeneralThreadPool.getInstance().execute(new UserCheckThread());
	}

	public void removesael(L1NpcInstance npc) {
		npc.deleteMe();
	}

	public void timeOverRun(int type) {
		switch (type) {
		case -4:// 4��° �� 2�� ���� �� ��ȯ
			FafulionMsgTimer room4 = new FafulionMsgTimer(_id, 4);
			GeneralThreadPool.getInstance().execute(room4);
			break;
		case -3:
			FafulionMsgTimer room3 = new FafulionMsgTimer(_id, 3);
			GeneralThreadPool.getInstance().execute(room3);
			break;
		case -2:
			FafulionMsgTimer room2 = new FafulionMsgTimer(_id, 2);
			GeneralThreadPool.getInstance().execute(room2);
			break;
		case -1:
			FafulionMsgTimer room1 = new FafulionMsgTimer(_id, 1);
			GeneralThreadPool.getInstance().execute(room1);
			break;

		case 0:// ��Ÿ�濡 �ִ� ��� ��� �ڽ�Ų��
			if (isPaPoo() == true) {
				papoomsg anta1 = new papoomsg(_id, 0);
				GeneralThreadPool.getInstance().execute(anta1);
			}
			break;
		case 1:// 1����Ÿ ������ ��Ʈ ����
				// S_SystemMessage sm = new
				// S_SystemMessage("��Ǫ���� �� �޾Ƴ� �巡���� ������ �־����ϴ�.");
			/*
			 * for (L1PcInstance player: L1World.getInstance().getAllPlayers()){
			 * if(player.getMapId()== PaPoo().getMapId()){ createNewItem(player,
			 * 787878, 1); //���� 1 //player.sendPackets(sm); for (L1Object temp :
			 * L1World
			 * .getInstance().getVisibleObjects(player.getMapId()).values()){
			 * if(temp instanceof L1PcInstance) ((L1PcInstance)
			 * temp).sendPackets(new S_ServerMessage(813, "��Ǫ����", "�޾Ƴ� �巡���� ����",
			 * player.getName())); } } }
			 */
			// sm = null;
			papoomsg anta1 = new papoomsg(_id, 2);
			GeneralThreadPool.getInstance().execute(anta1);
			break;
		case 2:// 2����Ÿ ������ ��Ʈ ����
				// S_SystemMessage sm2 = new
				// S_SystemMessage("��Ǫ���� �� �޾Ƴ� �巡���� ������ �־����ϴ�.");
			/*
			 * for (L1PcInstance player: L1World.getInstance().getAllPlayers()){
			 * if(player.getMapId()== PaPoo().getMapId()){ createNewItem(player,
			 * 787878, 1); //���� 1 for (L1Object temp :
			 * L1World.getInstance().getVisibleObjects
			 * (player.getMapId()).values()){ if(temp instanceof L1PcInstance)
			 * ((L1PcInstance) temp).sendPackets(new S_ServerMessage(813,
			 * "��Ǫ����", "�޾Ƴ� �巡���� ����", player.getName())); }
			 * //player.sendPackets(sm2); } }
			 */
			// sm2 = null;
			papoomsg anta2 = new papoomsg(_id, 3);
			GeneralThreadPool.getInstance().execute(anta2);
			break;
		case 3:// 3����Ÿ ������ ��Ʈ ����
				// S_SystemMessage sm3 = new
				// S_SystemMessage("��Ǫ������ �����鼭 ��ǥ�� ������ϴ�.");

			L1World.getInstance().broadcastServerMessage(
					"�������� ��ħ : ��Ǫ������ ���� ������ ���߰� �� ������ ź�� �Ͽ����ϴ�.!!");
			GeneralThreadPool.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					// TODO �ڵ� ������ �޼ҵ� ����
					for (L1PcInstance player : L1World.getInstance()
							.getAllPlayers()) {
						if (player.getMapId() == PaPoo().getMapId()) {
							createNewItem(player, 5000066, 1); // ���� 11
							for (L1Object temp : L1World.getInstance()
									.getVisibleObjects(player.getMapId())
									.values()) {
								if (temp instanceof L1PcInstance)
									((L1PcInstance) temp).sendPackets(new S_ServerMessage(
											813, "��Ǫ����", "������ ǥ��", player
													.getName()));
							}
							// player.sendPackets(sm3);
						}
					}
				}
			}, 10000);
			// sm3 = null;
			papoomsg anta3 = new papoomsg(_id, 4);
			GeneralThreadPool.getInstance().execute(anta3);
			break;

		case 4:// ������
			papoomsg anta4 = new papoomsg(_id, 1);
			GeneralThreadPool.getInstance().execute(anta4);
			break;

		case 5: {// ��Ÿ 1�� ���
			L1SpawnUtil.spawn3(PaPoo(), 145943, 10, 0, false);
			S_SkillSound ss = new S_SkillSound(PaPoo().getId(), 761);
			Broadcaster.broadcastPacket(PaPoo(), ss);
			ss = null;
		}
			break;

		case 6: {// ��Ǫ �������� ~
			try {
				ArrayList<L1PcInstance> list = new ArrayList<L1PcInstance>();
				for (L1Object obj : L1World.getInstance().getVisibleObjects(
						PaPoo(), 13)) {
					if (obj instanceof L1NpcInstance) {
						L1NpcInstance pc = (L1NpcInstance) obj;
						if (pc.getNpcId() == 4039004) {
							pc.deleteMe();
						}
						if (pc.getNpcId() == 4039005) {
							pc.deleteMe();
						}
					} else if (obj instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) obj;
						list.add(pc);
					}
				}
				if (list.size() > 0) {
					Collections.shuffle(list);
					try {
						for (int i = 0; i < 4; i++) {
							L1PcInstance pc = list.get(i);
							if (pc == null)
								break;
							int oj = 0;
							if (i >= 2)
								oj = 1;
							pc.getSkillEffectTimerSet().removeSkillEffect(
									10500 + oj);
							pc.getSkillEffectTimerSet().setSkillEffect(
									10501 - oj, 60000);
							L1SkillUse l1skilluse = null;
							l1skilluse = new L1SkillUse();
							l1skilluse.handleCommands(pc, 10501 - oj,
									pc.getId(), pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
					} catch (Exception e) {
					}
				}
				// L1SpawnUtil.spawn3(PaPoo(), 4039004, 10, 120*1000, false);
				// L1SpawnUtil.spawn3(PaPoo(), 4039005, 10, 120*1000, false);
				L1SpawnUtil.spawn3(PaPoo(), 4039001, 10, 0, false);
				PaPoo().marble.add("��������");
				L1SpawnUtil.spawn3(PaPoo(), 4039002, 10, 0, false);
				PaPoo().marble2.add("�ź��ѿ�������");
				if (PaPoo().getNpcId() == 4039006) {
					L1SpawnUtil.spawn3(PaPoo(), 4039001, 10, 0, false);
					PaPoo().marble.add("��������");
				} else if (PaPoo().getNpcId() == 4039007) {
					L1SpawnUtil.spawn3(PaPoo(), 4039001, 10, 0, false);
					PaPoo().marble.add("��������");
					L1SpawnUtil.spawn3(PaPoo(), 4039001, 10, 0, false);
					PaPoo().marble.add("��������");
				}
				S_SkillSound ss = new S_SkillSound(PaPoo().getId(), 761);
				Broadcaster.broadcastPacket(PaPoo(), ss);
				ss = null;
			} catch (Exception e) {
			}
		}
			break;

		case 7: {// �� ����
			S_DoActionGFX gfx3 = new S_DoActionGFX(PaPoo().getId(), 5);
			Broadcaster.broadcastPacket(PaPoo(), gfx3);

			for (L1Object obj : L1World.getInstance().getVisibleObjects(
					PaPoo(), 3)) {
				if (obj instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) obj;
					if (pc.isDead()) {
						continue;
					}
					PaPoo().getMoveState().setHeading(
							calcheading(PaPoo().getX(), PaPoo().getY(),
									pc.getX(), pc.getY()));
					S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
					Broadcaster.broadcastPacket(PaPoo(), ch);
					ch = null;
					int ran = random.nextInt(100) + 300;
					pc.receiveDamage(PaPoo(), ran, true);

					if (pc.getResistance().getMr() < 150) {
						S_SkillSound ss = new S_SkillSound(pc.getId(), 870);
						Broadcaster.broadcastPacket(pc, ss);
						pc.sendPackets(ss);
						ss = null;
						can(pc);
					}
					if (pc.isDead()) {
						break;
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Broadcaster.broadcastPacket(PaPoo(), gfx3);
					int ran1 = random.nextInt(100) + 300;
					pc.receiveDamage(PaPoo(), ran1, true);
				}
				break;
			}
			gfx3 = null;
		}
			break;

		case 8: {// ���� ����
			try {
				synchronized (PaPoo().synchObject) {
					PaPoo().setParalyzed(true);
					S_DoActionGFX gfx3 = new S_DoActionGFX(PaPoo().getId(), 12);
					Broadcaster.broadcastPacket(PaPoo(), gfx3);
					int time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 12);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 3)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							PaPoo().getMoveState().setHeading(
									calcheading(PaPoo().getX(), PaPoo().getY(),
											pc.getX(), pc.getY()));
							S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
							Broadcaster.broadcastPacket(PaPoo(), ch);
							ch = null;
							int ran = random.nextInt(100) + 300;
							pc.receiveDamage(PaPoo(), ran, true);
							if (pc.isDead()) {
								break;
							}
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Broadcaster.broadcastPacket(PaPoo(), gfx3);
							int ran1 = random.nextInt(100) + 300;
							pc.receiveDamage(PaPoo(), ran1, true);
						}
						break;
					}
					time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 12);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));

					/*
					 * //////////////////////////////////////////�����극��ũ
					 * //////////////////////////// for (L1Object obj :
					 * L1World.getInstance().getVisibleObjects(PaPoo(),3)){ if
					 * (obj instanceof L1PcInstance) { L1PcInstance pc =
					 * (L1PcInstance) obj; if(pc.isDead()){ continue; }
					 * 
					 * L1ItemInstance weapon = pc.getWeapon(); if (weapon !=
					 * null) { S_SkillSound ss = new S_SkillSound(pc.getId(),
					 * 172); Broadcaster.broadcastPacket(pc, ss);
					 * pc.sendPackets(ss); ss = null; S_ServerMessage sm4 = new
					 * S_ServerMessage(268, weapon.getLogName());
					 * pc.sendPackets(sm4); sm4 = null;
					 * pc.getInventory().receiveDamage(weapon, 5); } } }
					 */
					gfx3 = null;
					PaPoo().setParalyzed(false);
				}
			} catch (Exception e) {
			}
		}
			break;

		case 9: {// �׳� �е�
			try {
				synchronized (PaPoo().synchObject) {
					PaPoo().setParalyzed(true);
					S_DoActionGFX gfx3 = new S_DoActionGFX(PaPoo().getId(), 25);
					S_DoActionGFX gfx4 = new S_DoActionGFX(PaPoo().getId(), 41);// �̷�

					int time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 25);
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 3)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							PaPoo().getMoveState().setHeading(
									calcheading(PaPoo().getX(), PaPoo().getY(),
											pc.getX(), pc.getY()));
							S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
							Broadcaster.broadcastPacket(PaPoo(), ch);
							ch = null;
							Broadcaster.broadcastPacket(PaPoo(), gfx3);
							int ran = random.nextInt(100) + 300;
							pc.receiveDamage(PaPoo(), ran, true);
							if (pc.isDead()) {
								break;
							}

							if (time > 0)
								Thread.sleep(PaPoo().calcSleepTime(time, 2));

							Broadcaster.broadcastPacket(PaPoo(), gfx3);
							int ran1 = random.nextInt(100) + 300;
							pc.receiveDamage(PaPoo(), ran1, true);

							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						break;
					}
					gfx3 = null;

					time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 25);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));

					Broadcaster.broadcastPacket(PaPoo(), gfx4);
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}

							S_EffectLocation packet = new S_EffectLocation(
									pc.getX(), pc.getY(), (short) 3685);
							pc.sendPackets(packet);
							Broadcaster.broadcastPacket(pc, packet);
							packet = null;
							if (pc.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.COUNTER_MAGIC)) {
								pc.getSkillEffectTimerSet().removeSkillEffect(
										COUNTER_MAGIC);
								int castgfx = SkillsTable.getInstance()
										.getTemplate(COUNTER_MAGIC)
										.getCastGfx();
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										castgfx);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								ss = null;
								continue;
							}
							int ran2 = random.nextInt(300) + 300;
							pc.receiveDamage(PaPoo(), ran2, true);
						}
					}
					gfx4 = null;
					time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 41);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					PaPoo().setParalyzed(false);
				}
			} catch (Exception e) {
			}
		}
			break;

		case 10: {// �׳� �е�
			try {
				synchronized (PaPoo().synchObject) {
					PaPoo().setParalyzed(true);
					S_DoActionGFX gfx3 = new S_DoActionGFX(PaPoo().getId(), 1);
					S_DoActionGFX gfx4 = new S_DoActionGFX(PaPoo().getId(), 41);// �̷�
					int time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 1);
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 3)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							PaPoo().getMoveState().setHeading(
									calcheading(PaPoo().getX(), PaPoo().getY(),
											pc.getX(), pc.getY()));
							S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
							Broadcaster.broadcastPacket(PaPoo(), ch);
							ch = null;
							Broadcaster.broadcastPacket(PaPoo(), gfx3);
							int ran = random.nextInt(100) + 300;
							pc.receiveDamage(PaPoo(), ran, true);
							if (pc.isDead()) {
								break;
							}
							if (time > 0)
								Thread.sleep(PaPoo().calcSleepTime(time, 2));
							Broadcaster.broadcastPacket(PaPoo(), gfx3);
							int ran1 = random.nextInt(100) + 300;
							pc.receiveDamage(PaPoo(), ran1, true);
						}
						break;
					}

					time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 1);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));

					Broadcaster.broadcastPacket(PaPoo(), gfx4);
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}

							S_EffectLocation packet = new S_EffectLocation(
									pc.getX(), pc.getY(), (short) 3685);
							pc.sendPackets(packet);
							Broadcaster.broadcastPacket(pc, packet);
							packet = null;
							if (pc.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.COUNTER_MAGIC)) {
								pc.getSkillEffectTimerSet().removeSkillEffect(
										COUNTER_MAGIC);
								int castgfx = SkillsTable.getInstance()
										.getTemplate(COUNTER_MAGIC)
										.getCastGfx();
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										castgfx);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								ss = null;
								continue;
							}
							int ran2 = random.nextInt(300) + 300;
							pc.receiveDamage(PaPoo(), ran2, true);
						}
					}
					gfx3 = null;
					gfx4 = null;
					time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 41);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					PaPoo().setParalyzed(false);
				}
			} catch (Exception e) {
			}
		}
			break;

		case 11: {// ����Ÿ! ������ ���ζ�ũ
			// ����ĵ��+������+���̽���Ƽ��+���̽��̷�
			/*
			 * 1�Ӹ� 5������ 12 �޼� 18 �극�� 19 ���� 20 ���̽����� 25 ���� 30 ���� 41 ��������
			 */
			try {
				synchronized (PaPoo().synchObject) {
					PaPoo().setParalyzed(true);
					S_DoActionGFX ���� = new S_DoActionGFX(PaPoo().getId(), 41);// ��������
																				// ����
					S_DoActionGFX ���� = new S_DoActionGFX(PaPoo().getId(), 20);// ����
					S_DoActionGFX �̷� = new S_DoActionGFX(PaPoo().getId(), 18);// ����

					Broadcaster.broadcastPacket(PaPoo(), ����);
					���� = null;
					L1SkillUse l1skilluse = null;
					l1skilluse = new L1SkillUse();
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							if (pc.getResistance().getMr() < 150) {
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										870);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								can(pc);
							}
							l1skilluse.handleCommands(pc, 10518, pc.getId(),
									pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
					}
					l1skilluse = null;
					int time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 41);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					Broadcaster.broadcastPacket(PaPoo(), ����);
					���� = null;
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 8)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							if (pc.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.COUNTER_MAGIC)) {
								pc.getSkillEffectTimerSet().removeSkillEffect(
										COUNTER_MAGIC);
								int castgfx = SkillsTable.getInstance()
										.getTemplate(COUNTER_MAGIC)
										.getCastGfx();
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										castgfx);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								continue;
							}
							int ran = random.nextInt(400) + 400;
							pc.receiveDamage(PaPoo(), ran, true);
						}
					}
					time = SprTable.getInstance().getMoveSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 20);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					Broadcaster.broadcastPacket(PaPoo(), �̷�);
					�̷� = null;
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							if (pc.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.COUNTER_MAGIC)) {
								pc.getSkillEffectTimerSet().removeSkillEffect(
										COUNTER_MAGIC);
								int castgfx = SkillsTable.getInstance()
										.getTemplate(COUNTER_MAGIC)
										.getCastGfx();
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										castgfx);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								ss = null;
								break;
							}
							int ran = random.nextInt(200) + 300;
							pc.receiveDamage(PaPoo(), ran, true);
						}
						break;
					}
					time = SprTable.getInstance().getDirSpellSpeed(
							PaPoo().getNpcTemplate().get_gfxid());
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					PaPoo().setParalyzed(false);
				}
			} catch (Exception e) {
			}
		}
			break;

		case 12: {// ����Ÿ! ������ ���� ���ζ�ũ
			// ����ĵ��+������+���̽���Ƽ��+���� ��
			/*
			 * 1�Ӹ� 5������ 12 �޼� 18 �극�� 19 ���� 20 ���̽����� 25 ���� 30 ���� 41 ��������
			 */
			try {
				synchronized (PaPoo().synchObject) {
					PaPoo().setParalyzed(true);
					S_DoActionGFX ���� = new S_DoActionGFX(PaPoo().getId(), 41);// ��������
																				// ����
					S_DoActionGFX ���� = new S_DoActionGFX(PaPoo().getId(), 20);// ����
					S_DoActionGFX ���� = new S_DoActionGFX(PaPoo().getId(), 30);// ����

					Broadcaster.broadcastPacket(PaPoo(), ����);
					���� = null;
					L1SkillUse l1skilluse = null;
					l1skilluse = new L1SkillUse();
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							if (pc.getResistance().getMr() < 150) {
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										870);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								ss = null;
								can(pc);
							}
							l1skilluse.handleCommands(pc, 10518, pc.getId(),
									pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
					}
					l1skilluse = null;
					int time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 41);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					Broadcaster.broadcastPacket(PaPoo(), ����);
					���� = null;
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 8)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							if (pc.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.COUNTER_MAGIC)) {
								pc.getSkillEffectTimerSet().removeSkillEffect(
										COUNTER_MAGIC);
								int castgfx = SkillsTable.getInstance()
										.getTemplate(COUNTER_MAGIC)
										.getCastGfx();
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										castgfx);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								ss = null;
								continue;
							}
							int ran = random.nextInt(400) + 400;
							pc.receiveDamage(PaPoo(), ran, true);
						}
					}

					time = SprTable.getInstance().getMoveSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 20);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));

					Broadcaster.broadcastPacket(PaPoo(), ����);
					���� = null;
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 10)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							if (pc.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.COUNTER_MAGIC)) {
								pc.getSkillEffectTimerSet().removeSkillEffect(
										COUNTER_MAGIC);
								int castgfx = SkillsTable.getInstance()
										.getTemplate(COUNTER_MAGIC)
										.getCastGfx();
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										castgfx);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								ss = null;
								continue;
							}
							int ran = random.nextInt(500) + 700;
							pc.receiveDamage(PaPoo(), ran, true);
						}
					}

					time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 30);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					PaPoo().setParalyzed(false);
				}
			} catch (Exception e) {
			}

		}
			break;

		case 13: {// ����Ÿ! �׳� ���ζ�ũ ������+����+���̽� �극��
			// // ����Ÿ! �׳� ���ζ�ũ ������+����+���̽� �극��
			/*
			 * 1�Ӹ� 5������ 12 �޼� 18 �극�� 19 ���� 20 ���̽����� 25 ���� 30 ���� 41 ��������
			 */
			try {
				synchronized (PaPoo().synchObject) {
					PaPoo().setParalyzed(true);
					S_DoActionGFX ���� = new S_DoActionGFX(PaPoo().getId(), 25);// ��������
																				// ����
					S_DoActionGFX �극 = new S_DoActionGFX(PaPoo().getId(), 18);// ����

					L1SkillUse l1skilluse = null;
					l1skilluse = new L1SkillUse();
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							l1skilluse.handleCommands(pc, 10518, pc.getId(),
									pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
					}

					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 3)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							PaPoo().getMoveState().setHeading(
									calcheading(PaPoo().getX(), PaPoo().getY(),
											pc.getX(), pc.getY()));
							S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
							Broadcaster.broadcastPacket(PaPoo(), ch);
							ch = null;
							Broadcaster.broadcastPacket(PaPoo(), ����);
							for (L1Object obj2 : L1World.getInstance()
									.getVisibleObjects(pc, 2)) {
								if (obj2 instanceof L1PcInstance) {
									L1PcInstance pc2 = (L1PcInstance) obj2;
									int ran = random.nextInt(200) + 200;
									pc2.receiveDamage(PaPoo(), ran, true);
								}
							}
						}
						break;
					}
					���� = null;
					int time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 25);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));

					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 3)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							PaPoo().getMoveState().setHeading(
									calcheading(PaPoo().getX(), PaPoo().getY(),
											pc.getX(), pc.getY()));
							S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
							Broadcaster.broadcastPacket(PaPoo(), ch);
							ch = null;
							Broadcaster.broadcastPacket(PaPoo(), �극);
							for (L1Object obj2 : L1World.getInstance()
									.getVisibleObjects(pc, 2)) {
								if (obj2 instanceof L1PcInstance) {
									L1PcInstance pc2 = (L1PcInstance) obj2;
									if (pc2.getSkillEffectTimerSet()
											.hasSkillEffect(
													L1SkillId.COUNTER_MAGIC)) {
										pc2.getSkillEffectTimerSet()
												.removeSkillEffect(
														COUNTER_MAGIC);
										int castgfx = SkillsTable.getInstance()
												.getTemplate(COUNTER_MAGIC)
												.getCastGfx();
										S_SkillSound ss = new S_SkillSound(
												pc2.getId(), castgfx);
										Broadcaster.broadcastPacket(pc2, ss);
										pc2.sendPackets(ss);
										ss = null;
										continue;
									}
									int ran = random.nextInt(300) + 300;
									pc2.receiveDamage(PaPoo(), ran, true);
								}
							}
						}
						break;
					}
					�극 = null;
					time = SprTable.getInstance().getDirSpellSpeed(
							PaPoo().getNpcTemplate().get_gfxid());
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					PaPoo().setParalyzed(false);
				}
			} catch (Exception e) {
			}
		}
			break;

		case 14: {// ����Ÿ! ���� �촩�� ���ེ��+�Ӹ�����+���̽��극��
			// // ���� �촩�� ���ེ��+�Ӹ�����+���̽��극��
			/*
			 * 1�Ӹ� 5������ 12 �޼� 18 �극�� 19 ���� 20 ���̽����� 25 ���� 30 ���� 41 ��������
			 */
			try {
				synchronized (PaPoo().synchObject) {
					PaPoo().setParalyzed(true);
					S_DoActionGFX �Ӹ� = new S_DoActionGFX(PaPoo().getId(), 1);// ��������
																				// ����
					S_DoActionGFX �극 = new S_DoActionGFX(PaPoo().getId(), 18);// ����

					L1SkillUse l1skilluse = null;
					l1skilluse = new L1SkillUse();
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							l1skilluse.handleCommands(pc, 10517, pc.getId(),
									pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
					}
					l1skilluse = null;
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 3)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							PaPoo().getMoveState().setHeading(
									calcheading(PaPoo().getX(), PaPoo().getY(),
											pc.getX(), pc.getY()));
							S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
							Broadcaster.broadcastPacket(PaPoo(), ch);
							ch = null;
							Broadcaster.broadcastPacket(PaPoo(), �Ӹ�);
							for (L1Object obj2 : L1World.getInstance()
									.getVisibleObjects(pc, 1)) {
								if (obj2 instanceof L1PcInstance) {
									L1PcInstance pc2 = (L1PcInstance) obj2;
									int ran = random.nextInt(200) + 200;
									pc2.receiveDamage(PaPoo(), ran, true);
								}
							}
						}
						break;
					}
					�Ӹ� = null;

					int time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 1);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));

					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 3)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							PaPoo().getMoveState().setHeading(
									calcheading(PaPoo().getX(), PaPoo().getY(),
											pc.getX(), pc.getY()));
							S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
							Broadcaster.broadcastPacket(PaPoo(), ch);
							ch = null;
							Broadcaster.broadcastPacket(PaPoo(), �극);
							for (L1Object obj2 : L1World.getInstance()
									.getVisibleObjects(pc, 2)) {
								if (obj2 instanceof L1PcInstance) {
									L1PcInstance pc2 = (L1PcInstance) obj2;
									if (pc2.getSkillEffectTimerSet()
											.hasSkillEffect(
													L1SkillId.COUNTER_MAGIC)) {
										pc2.getSkillEffectTimerSet()
												.removeSkillEffect(
														COUNTER_MAGIC);
										int castgfx = SkillsTable.getInstance()
												.getTemplate(COUNTER_MAGIC)
												.getCastGfx();
										S_SkillSound ss = new S_SkillSound(
												pc2.getId(), castgfx);
										Broadcaster.broadcastPacket(pc2, ss);
										pc2.sendPackets(ss);
										ss = null;
										continue;
									}
									int ran = random.nextInt(300) + 300;
									pc2.receiveDamage(PaPoo(), ran, true);
								}
							}
						}
						break;
					}
					�극 = null;
					time = SprTable.getInstance().getDirSpellSpeed(
							PaPoo().getNpcTemplate().get_gfxid());
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					PaPoo().setParalyzed(false);
				}
			} catch (Exception e) {
			}
		}
			break;

		case 15: {// ����Ÿ! �� ���̹� �������� + ������ + ���̽��̷�
			// // ����Ÿ! �� ���̹� �������� + ������ + ���̽��̷�
			/*
			 * 1�Ӹ� 5������ 12 �޼� 18 �극�� 19 ���� 20 ���̽����� 25 ���� 30 ���� 41 ��������
			 */
			try {
				synchronized (PaPoo().synchObject) {
					PaPoo().setParalyzed(true);
					S_DoActionGFX ���� = new S_DoActionGFX(PaPoo().getId(), 5);// ������
					S_DoActionGFX �̷� = new S_DoActionGFX(PaPoo().getId(), 41);// ���̽��̷�

					L1SkillUse l1skilluse = null;
					l1skilluse = new L1SkillUse();
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							l1skilluse.handleCommands(pc, 10513, pc.getId(),
									pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
					}
					l1skilluse = null;
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 3)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							PaPoo().getMoveState().setHeading(
									calcheading(PaPoo().getX(), PaPoo().getY(),
											pc.getX(), pc.getY()));
							S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
							Broadcaster.broadcastPacket(PaPoo(), ch);
							ch = null;
							Broadcaster.broadcastPacket(PaPoo(), ����);
							for (L1Object obj2 : L1World.getInstance()
									.getVisibleObjects(pc, 1)) {
								if (obj2 instanceof L1PcInstance) {
									L1PcInstance pc2 = (L1PcInstance) obj2;
									int ran = random.nextInt(200) + 200;
									pc2.receiveDamage(PaPoo(), ran, true);
								}
							}
						}
						break;
					}
					���� = null;
					int time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 5);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));

					Broadcaster.broadcastPacket(PaPoo(), �̷�);
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							S_EffectLocation packet = new S_EffectLocation(
									pc.getX(), pc.getY(), (short) 3685);
							pc.sendPackets(packet);
							Broadcaster.broadcastPacket(pc, packet);
							packet = null;
							if (pc.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.COUNTER_MAGIC)) {
								pc.getSkillEffectTimerSet().removeSkillEffect(
										COUNTER_MAGIC);
								int castgfx = SkillsTable.getInstance()
										.getTemplate(COUNTER_MAGIC)
										.getCastGfx();
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										castgfx);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								ss = null;
								continue;
							}
							int ran2 = random.nextInt(300) + 300;
							pc.receiveDamage(PaPoo(), ran2, true);
						}
					}
					�̷� = null;
					time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 41);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					PaPoo().setParalyzed(false);
				}
			} catch (Exception e) {
			}
		}
			break;

		case 16: {// ����Ÿ! ���� ���̹� ��������+������+����
			// // ����Ÿ! ���� ���̹� ��������+������+����
			/*
			 * 1�Ӹ� 5������ 12 �޼� 18 �극�� 19 ���� 20 ���̽����� 25 ���� 30 ���� 41 ��������
			 */
			try {
				synchronized (PaPoo().synchObject) {
					PaPoo().setParalyzed(true);
					S_DoActionGFX ���� = new S_DoActionGFX(PaPoo().getId(), 5);// ������
					S_DoActionGFX ���� = new S_DoActionGFX(PaPoo().getId(), 19);// ���̽��̷�

					L1SkillUse l1skilluse = null;
					l1skilluse = new L1SkillUse();
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 5)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							l1skilluse.handleCommands(pc, 10513, pc.getId(),
									pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
					}
					l1skilluse = null;
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 3)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							PaPoo().getMoveState().setHeading(
									calcheading(PaPoo().getX(), PaPoo().getY(),
											pc.getX(), pc.getY()));
							S_ChangeHeading ch = new S_ChangeHeading(PaPoo());
							Broadcaster.broadcastPacket(PaPoo(), ch);
							ch = null;
							Broadcaster.broadcastPacket(PaPoo(), ����);
							for (L1Object obj2 : L1World.getInstance()
									.getVisibleObjects(pc, 1)) {
								if (obj2 instanceof L1PcInstance) {
									L1PcInstance pc2 = (L1PcInstance) obj2;
									int ran = random.nextInt(200) + 200;
									pc2.receiveDamage(PaPoo(), ran, true);
								}
							}
						}
						break;
					}
					���� = null;
					int time = SprTable.getInstance().getAttackSpeed(
							PaPoo().getNpcTemplate().get_gfxid(), 5);
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					Broadcaster.broadcastPacket(PaPoo(), ����);
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(PaPoo(), 8)) {
						if (obj instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) obj;
							if (pc.isDead()) {
								continue;
							}
							if (pc.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.COUNTER_MAGIC)) {
								pc.getSkillEffectTimerSet().removeSkillEffect(
										COUNTER_MAGIC);
								int castgfx = SkillsTable.getInstance()
										.getTemplate(COUNTER_MAGIC)
										.getCastGfx();
								S_SkillSound ss = new S_SkillSound(pc.getId(),
										castgfx);
								Broadcaster.broadcastPacket(pc, ss);
								pc.sendPackets(ss);
								ss = null;
								continue;
							}
							int ran = random.nextInt(400) + 400;
							pc.receiveDamage(PaPoo(), ran, true);
						}
					}
					���� = null;
					time = SprTable.getInstance().getNodirSpellSpeed(
							PaPoo().getNpcTemplate().get_gfxid());
					if (time > 0)
						Thread.sleep(PaPoo().calcSleepTime(time, 2));
					PaPoo().setParalyzed(false);
				}
			} catch (Exception e) {
			}
		}
			break;

		}

	}

	private boolean createNewItem(L1PcInstance pc, int item_id, int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // ���� �� ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance()
						.getInventory(pc.getX(), pc.getY(), pc.getMapId())
						.storeItem(item);
			}
			// S_ServerMessage ss = new S_ServerMessage(403, item.getLogName());
			// pc.sendPackets(ss); // %0�� �տ� �־����ϴ�.
			// ss = null;
			return true;
		} else {
			return false;
		}
	}

	private boolean isNotCancelable(int skillNum) {
		return skillNum == ENCHANT_WEAPON || skillNum == BLESSED_ARMOR
				|| skillNum == ABSOLUTE_BARRIER || skillNum == ADVANCE_SPIRIT
				|| skillNum == SHOCK_STUN || skillNum == �Ŀ��׸�
				|| skillNum == ������� || skillNum == STATUS_UNDERWATER_BREATH
				|| skillNum == SHADOW_FANG || skillNum == REDUCTION_ARMOR
				|| skillNum == SOLID_CARRIAGE || skillNum == COUNTER_BARRIER
				|| skillNum == SHADOW_ARMOR || skillNum == ARMOR_BREAK
				|| skillNum == DRESS_EVASION || skillNum == UNCANNY_DODGE
				|| skillNum == SCALES_EARTH_DRAGON
				|| skillNum == SCALES_WATER_DRAGON
				|| skillNum == SCALES_FIRE_DRAGON || skillNum == BOUNCE_ATTACK
				|| skillNum == IllUSION_OGRE || skillNum == IllUSION_LICH
				|| skillNum == IllUSION_DIAMONDGOLEM
				|| skillNum == IllUSION_AVATAR;
	}

	int ĵ����������[] = { STATUS_CURSE_BARLOG, STATUS_CURSE_YAHEE, STATUS_DRAGONPERL };

	public void can(L1PcInstance pc) {
		if (pc.getResistance().getMr() >= 150) {
			return;
		}

		for (int skillNum = SKILLS_BEGIN; skillNum <= SKILLS_END; skillNum++) {
			if (isNotCancelable(skillNum) && !pc.isDead()) {
				continue;
			}
			pc.getSkillEffectTimerSet().removeSkillEffect(skillNum);
		}
		for (int i = 0; i < ĵ����������.length; i++) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(ĵ����������[i]))
				pc.getSkillEffectTimerSet().removeSkillEffect(ĵ����������[i]);
		}
		for (int skillNum = STATUS_BEGIN; skillNum <= STATUS_CANCLEEND; skillNum++) {
			if (isNotCancelable(skillNum) && !pc.isDead()) {
				continue;
			}
			pc.getSkillEffectTimerSet().removeSkillEffect(skillNum);
		}

		pc.curePoison();
		pc.cureParalaysis();

		for (int skillNum = COOKING_BEGIN; skillNum <= COOKING_END; skillNum++) {
			if (isNotCancelable(skillNum) && !pc.isDead()) {
				continue;
			}
			pc.getSkillEffectTimerSet().removeSkillEffect(skillNum);
		}

		L1PolyMorph.undoPoly(pc);
		S_CharVisualUpdate cvu = new S_CharVisualUpdate(pc);
		pc.sendPackets(cvu);
		Broadcaster.broadcastPacket(pc, cvu);
		cvu = null;

		if (pc.getHasteItemEquipped() > 0) {
			pc.getMoveState().setMoveSpeed(0);
			S_SkillHaste sh = new S_SkillHaste(pc.getId(), 0, 0);
			pc.sendPackets(sh);
			Broadcaster.broadcastPacket(pc, sh);
			sh = null;
		}
		if (pc != null && pc.isInvisble()) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.INVISIBILITY)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(
						L1SkillId.INVISIBILITY);
				S_Invis iv = new S_Invis(pc.getId(), 0);
				pc.sendPackets(iv);
				Broadcaster.broadcastPacket(pc, iv);
				iv = null;
				S_Sound ss = new S_Sound(147);
				pc.sendPackets(ss);
				ss = null;

				S_RemoveObject iv2 = new S_RemoveObject(pc.getId());
				pc.sendPackets(iv2);
				Broadcaster.broadcastPacket(pc, iv2);
			}
			if (pc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.BLIND_HIDING)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(
						L1SkillId.BLIND_HIDING);
				S_Invis iv = new S_Invis(pc.getId(), 0);
				pc.sendPackets(iv);
				Broadcaster.broadcastPacket(pc, iv);
				iv = null;

				S_RemoveObject iv2 = new S_RemoveObject(pc.getId());
				pc.sendPackets(iv2);
				Broadcaster.broadcastPacket(pc, iv2);
			}
		}
		if (pc.getSkillEffectTimerSet()
				.hasSkillEffect(STATUS_UNDERWATER_BREATH)) {
			int timeSec = pc.getSkillEffectTimerSet().getSkillEffectTimeSec(
					STATUS_UNDERWATER_BREATH);
			pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), timeSec), true);
		}
		pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_FREEZE);
		if (pc.is��ƾ����()) {
			pc.getMoveState().setMoveSpeed(1);
			S_SkillHaste sh = new S_SkillHaste(pc.getId(), 1, -1);
			pc.sendPackets(sh);
			sh = null;
			S_SkillHaste sh2 = new S_SkillHaste(pc.getId(), 1, 0);
			Broadcaster.broadcastPacket(pc, sh2);
			sh2 = null;
		}
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

	/** �翤1 ������Ʈ�� �����ش� */
	public L1NpcInstance �翤() {
		return _�翤;
	}

	/** �翤1 ������Ʈ�� ���� �Ѵ�. */
	public void �»翤(L1NpcInstance npc) {
		_�翤 = npc;
	}

	/** �翤2 ������Ʈ�� �����ش� */
	public L1NpcInstance �翤2() {
		return _�翤2;
	}

	/** �翤2 ������Ʈ�� ���� �Ѵ�. */
	public void �»翤2(L1NpcInstance npc) {
		_�翤2 = npc;
	}

	public void ����������() {
		for (L1Object obj : L1World.getInstance()
				.getVisibleObjects(PaPoo(), 15)) {
			if (obj instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) obj;
				if (obj instanceof L1DollInstance) {
					continue;
				}
				if (obj instanceof L1SummonInstance) {
					continue;
				}
				if (npc.getNpcId() != 145943) {
					npc.setCurrentHp(0);
					npc.setDead(true);
					npc.getMap().setPassable(npc.getX(), npc.getY(), true);
					npc.setExp(0);
					npc.setKarma(0);
					npc.allTargetClear();
					npc.setActionStatus(ActionCodes.ACTION_Die);
					S_DoActionGFX da = new S_DoActionGFX(npc.getId(),
							ActionCodes.ACTION_Die);
					Broadcaster.broadcastPacket(npc, da);
					da = null;
					npc.deleteMe();
				}
			}
		}
	}

	/** ��Ǫ���·�� ������ ���� ���� �����´� */
	public int countLairUser() {
		return _PaPoolist.size();
	}

	/** ��Ǫ���·�� ������ ������ �ִ´� */
	public void addLairUser(L1PcInstance pc) {
		if (_PaPoolist.contains(pc)) {
			return;
		}
		_PaPoolist.add(pc);
	}

	/** ��Ǫ ��� ������ ������ �ʱ�ȭ */
	public void clLairUser() {
		_PaPoolist.clear();
	}

	/** ��Ǫ ������Ʈ�� �����ش� */
	public L1NpcInstance PaPoo() {
		return PaPoo;
	}

	/** ��Ǫ ������Ʈ�� ���� �Ѵ�. */
	public void setPaPoo(L1NpcInstance npc) {
		PaPoo = npc;
	}

	/** ��Ǫ ������ �˷��ش� */
	public boolean isPaPoo() {
		return _isPaPoo;
	}

	/** ��Ǫ�� ������ �����Ѵ� */
	public void setPapoo(boolean flag) {
		_isPaPoo = flag;
	}

	public int getAntaId() {
		return _id;
	}

	public long getEndTime() {
		return _endtime;
	}

	private ArrayList<L1NpcInstance> MonList = new ArrayList<L1NpcInstance>();

	private int MonsterCount1 = 200;
	private int MonsterCount2 = 200;
	private int MonsterCount3 = 200;
	private int MonsterCount4 = 200;

	public synchronized void MonsterCount(int type) {
		switch (type) {
		case 1:
			MonsterCount1--;
			if (MonsterCount1 < 1) {
				if (MiniBoss3.get(1) == false) {
					BossSpawn(3, 1);
					MiniBoss3.put(1, true);
				}
			} else if (MonsterCount1 < 121) {
				if (MiniBoss2.get(1) == false) {
					BossSpawn(2, 1);
					MiniBoss2.put(1, true);
				}
			} else if (MonsterCount1 < 161) {
				if (MiniBoss1.get(1) == false) {
					BossSpawn(1, 1);
					MiniBoss1.put(1, true);
				}
			}
			break;
		case 2:
			MonsterCount2--;
			if (MonsterCount2 < 1) {
				if (MiniBoss3.get(2) == false) {
					BossSpawn(3, 2);
					MiniBoss3.put(2, true);
				}
			} else if (MonsterCount2 < 121) {
				if (MiniBoss2.get(2) == false) {
					BossSpawn(2, 2);
					MiniBoss2.put(2, true);
				}
			} else if (MonsterCount2 < 161) {
				if (MiniBoss1.get(2) == false) {
					BossSpawn(1, 2);
					MiniBoss1.put(2, true);
				}
			}
			break;
		case 3:
			MonsterCount3--;
			if (MonsterCount3 < 1) {
				if (MiniBoss3.get(3) == false) {
					BossSpawn(3, 3);
					MiniBoss3.put(3, true);
				}
			} else if (MonsterCount3 < 121) {
				if (MiniBoss2.get(3) == false) {
					BossSpawn(2, 3);
					MiniBoss2.put(3, true);
				}
			} else if (MonsterCount3 < 161) {
				if (MiniBoss1.get(3) == false) {
					BossSpawn(1, 3);
					MiniBoss1.put(3, true);
				}
			}
			break;
		case 4:
			MonsterCount4--;
			if (MonsterCount4 < 1) {
				if (MiniBoss3.get(4) == false) {
					BossSpawn(3, 4);
					MiniBoss3.put(4, true);
				}
			} else if (MonsterCount4 < 121) {
				if (MiniBoss2.get(4) == false) {
					BossSpawn(2, 4);
					MiniBoss2.put(4, true);
				}
			} else if (MonsterCount4 < 161) {
				if (MiniBoss1.get(4) == false) {
					BossSpawn(1, 4);
					MiniBoss1.put(4, true);
				}
			}
			break;
		}
	}

	private int �׷�a = 4039014;
	private int �׷�b = 4039015;
	private int �׷�c = 4039016;

	private void BossSpawn(int step, int type) {
		switch (type) {
		case 1:
			switch (step) {
			case 1:
				L1SpawnUtil.spawn2(32740, 32842, (short) _id, �׷�a, 0, 0, 0);
				break;
			case 2:
				L1SpawnUtil.spawn2(32777, 32810, (short) _id, �׷�b, 0, 0, 0);
				break;
			case 3:
				L1SpawnUtil.spawn2(32860, 32840, (short) _id, �׷�c, 0, 0, 0);
				break;
			}
			break;
		case 2:
			switch (step) {
			case 1:
				L1SpawnUtil.spawn2(32741, 32715, (short) _id, �׷�a, 0, 0, 0);
				break;
			case 2:
				L1SpawnUtil.spawn2(32777, 32682, (short) _id, �׷�b, 0, 0, 0);
				break;
			case 3:
				L1SpawnUtil.spawn2(32861, 32711, (short) _id, �׷�c, 0, 0, 0);
				break;
			}
			break;
		case 3:
			switch (step) {
			case 1:
				L1SpawnUtil.spawn2(32740, 32587, (short) _id, �׷�a, 0, 0, 0);
				break;
			case 2:
				L1SpawnUtil.spawn2(32777, 32554, (short) _id, �׷�b, 0, 0, 0);
				break;
			case 3:
				L1SpawnUtil.spawn2(32861, 32583, (short) _id, �׷�c, 0, 0, 0);
				break;
			}
			break;
		case 4:
			switch (step) {
			case 1:
				L1SpawnUtil.spawn2(32932, 32587, (short) _id, �׷�a, 0, 0, 0);
				break;
			case 2:
				L1SpawnUtil.spawn2(32969, 32554, (short) _id, �׷�b, 0, 0, 0);
				break;
			case 3:
				L1SpawnUtil.spawn2(33052, 32584, (short) _id, �׷�c, 0, 0, 0);
				break;
			}
			break;
		}
	}

	public void AddMon(L1NpcInstance npc) {
		MonList.add(npc);
	}

	public void MiniBossReset() {
		for (L1NpcInstance mon : MonList) {
			if (mon.isDead()) {
				continue;
			}
			mon.deleteMe();
		}
		MonList.clear();
	}

	/** ��Ƽ ���� */
	/*
	 * public L1Party getParty(int type){ switch (type) { case 1:return
	 * Party1;case 2:return Party2; case 3:return Party3;case 4:return Party4; }
	 * return null; }
	 */

	public boolean Check(int type) {
		switch (type) {
		case 1:
			return MiniRoom1;
		case 2:
			return MiniRoom2;
		case 3:
			return MiniRoom3;
		case 4:
			return MiniRoom4;
		}
		return false;
	}

	/*
	 * public void setParty(L1Party party, int type){ switch (type) { case
	 * 1:Party1 = party; MiniRoom1 = true; break; case 2:Party2 = party;
	 * MiniRoom2 = true; break; case 3:Party3 = party; MiniRoom3 = true; break;
	 * case 4:Party4 = party; MiniRoom4 = true; break; } }
	 */

	private ArrayList<L1PcInstance> _list = new ArrayList<L1PcInstance>();

	public void addMember(L1PcInstance pc) {
		synchronized (_list) {
			if (!_list.contains(pc)) {
				// System.out.println("�߰� > "+pc.getName());
				_list.add(pc);
			}
		}
	}

	public void removeMember(L1PcInstance pc) {
		synchronized (_list) {
			if (_list.contains(pc)) {
				// System.out.println("���� > "+pc.getName());
				_list.remove(pc);
			}
		}
	}

	public ArrayList<L1PcInstance> getMembers() {
		return _list;
	}

	private byte MembermapckCount = 0;
	public boolean threadOn = true;

	class UserCheckThread implements Runnable {

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			int size = 0;
			while (threadOn) {
				try {
					L1PcInstance[] list = null;
					synchronized (_list) {
						if ((size = _list.size()) > 0) {
							list = _list.toArray(new L1PcInstance[size]);
						}
					}
					if (list != null && size > 0) {
						boolean ck2 = false;
						for (L1PcInstance pc : list) {
							if (pc == null
									|| pc.getMapId() != _id
									|| pc.getNetConnection() == null
									|| !((pc.getX() >= 32721 && pc.getX() <= 32879) && (pc
											.getY() >= 32777 && pc.getY() <= 32880))) {
								ck2 = true;
							}
						}
						if (ck2) {
							if (MembermapckCount++ > 3) {
								for (L1PcInstance pc : list) {
									if (pc == null
											|| pc.getMapId() != _id
											|| pc.getNetConnection() == null
											|| !((pc.getX() >= 32721 && pc
													.getX() <= 32879) && (pc
													.getY() >= 32777 && pc
													.getY() <= 32880)))
										removeMember(pc);
								}
								MembermapckCount = 0;
							}
						}
					}
					list = null;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO �ڵ� ������ catch ���
						e.printStackTrace();
					}
				}
			}
		}

	}

}
