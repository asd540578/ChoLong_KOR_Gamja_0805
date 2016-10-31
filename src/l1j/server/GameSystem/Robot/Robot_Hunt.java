package l1j.server.GameSystem.Robot;

import static l1j.server.server.model.skill.L1SkillId.HASTE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.GMCommands;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;

public class Robot_Hunt {

	public static boolean �Ⱘ = false;
	private static Random _random = new Random(System.nanoTime());
	private static Queue<L1RobotInstance> _queue;
	private static Robot_Hunt _instance;

	public static Robot_Hunt getInstance() {
		if (_instance == null) {
			_instance = new Robot_Hunt();
		}
		return _instance;
	}

	public Robot_Hunt() {
		_queue = new ConcurrentLinkedQueue<L1RobotInstance>();
		loadbot();
		ArrayList<L1RobotInstance> list = new ArrayList<L1RobotInstance>();
		while (_queue.size() > 0) {
			L1RobotInstance ro = _queue.poll();
			list.add(ro);
		}
		Collections.shuffle(list);
		for (L1RobotInstance ro : list) {
			_queue.offer(ro);
		}
	}

	public void put(L1RobotInstance bot) {
		synchronized (_queue) {
			_queue.offer(bot);
		}
	}

	// ��踮��������� ���� 15����
	private static final String[] mapName = { "����", "���̱�1", "���̱�2", "���̱�3",
			"���̱�4", "���̱�5", "���̱�6", "����1��", "����2��", "����3��", "����4��", "����ħ����1��",
			"����ħ����2��", "����ħ����3��", "���ڽ���", "�ؼ�",
			/*
			 * "���̾�Ʈ��", "ȭ��", "���̳����",
			 */
			"���1��", "���2��", "���3��", "���4��", "���5��", "���6��", "���7��", 
			"����1��", "����2��", "����3��", "����4��", "����5��", "����6��", "����7��", 
			"�Ⱘ1��", "�Ⱘ2��" , "�Ⱘ3��" , "�Ⱘ4��" , 
              "���������1��" , "���������2��" , "���������3��" , "���������4��" , "���������5��" , "���������6��" , "���������7��" , "���������8��" , "���������9��", "���������10��",
	/* "���ž4��", "���ž5��" */};

	private static final int[] mapCount = { 0, // ����
			0, 0, 0, 0, 0, 0,//���̱�
			0, 0, 0, 0, // ����
			0, 0, 0,// ħ����
			0,// ���ڽ���
			20, // �ؼ�
			0, 0, 0, 0, 0, 0, 0, //���1 -7
			0, 0, 0, 0, 0, 0, 0, //���� 
			20, 20, 20, 20,// �Ⱘ
			20, 20, 20 ,20 ,20 ,20 ,20 ,20 ,20 ,0 //���������
         	};

	public void start_spawn() {
		for (int a = 0; a < mapCount.length; a++) {
			// System.out.println(mapName[a]+" > "+mapCount[a]);
			for (int i = 0; i < mapCount[a]; i++) {
				L1RobotInstance bot = _queue.poll();
				if (bot == null)
					continue;
				GeneralThreadPool.getInstance().schedule(new botVisible(bot, mapName[a]), 6000 * (_random.nextInt(50))); //���� 120
				// GeneralThreadPool.getInstance().schedule(new botVisible(bot,
				// mapName[a]), 6000*(_random.nextInt(10)+1));
			}
		}
	}

	private void direct_spawn(String ��ġ) {
		if (!GMCommands.huntBot)
			return;
		synchronized (_queue) {
			L1RobotInstance bot = _queue.poll();
			if (bot == null)
				return;
			GeneralThreadPool.getInstance().schedule(new botVisible(bot, ��ġ),
					1 * (_random.nextInt(2) + 1));
		}
	}

	public void delay_spawn(String ��ġ, int time) {
		if (!GMCommands.huntBot)
			return;
		synchronized (_queue) {
			L1RobotInstance bot = _queue.poll();
			if (bot == null)
				return;
			GeneralThreadPool.getInstance().schedule(new botVisible(bot, ��ġ),
					time);
		}
	}

	// private static boolean spawning = false;
	class botVisible implements Runnable {
		private L1RobotInstance bot;
		private String �����ġ;

		public botVisible(L1RobotInstance bot, String _�����ġ) {
			this.bot = bot;
			this.�����ġ = _�����ġ;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			try {
				L1PcInstance rob = L1World.getInstance().getPlayer(
						bot.getName());
				if (rob != null || bot.isCrown()) {
					put(bot);
					direct_spawn(�����ġ);
					return;
				}
				if (!GMCommands.huntBot) {
					put(bot);
					return;
				}
				if (bot.isWizard() && �����ġ.equalsIgnoreCase("���")) {
					put(bot);
					return;
				}
				int map = _random.nextInt(2);
				while (true) {
					// ��ǥ ����
					switch (map) {
					case 0:// ���
						bot.setX(33436 + _random.nextInt(5));
						bot.setY(32799 + _random.nextInt(5));
						break;
					case 1:// ����
						bot.setX(34055 + _random.nextInt(6));
						bot.setY(32278 + _random.nextInt(8));
						break;
					default:
						break;
					}
					bot.setMap((short) 4);
					boolean ck = false;
					for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(bot, 0)) {
						ck = true;
						break;
					}
					if (ck)
						continue;
					if (bot.getMap().isInMap(bot.getX(), bot.getY())
							&& bot.getMap().isPassable(bot.getX(), bot.getY()))
						break;
					Thread.sleep(100);
				}

				if (�����ġ.equalsIgnoreCase("����")
						|| �����ġ.equalsIgnoreCase("���ڽ���")
						|| �����ġ.equalsIgnoreCase("���ž4��")
						|| �����ġ.equalsIgnoreCase("���ž5��")) {
					bot.getAC().setAc(-80);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(50);
						bot.addDmgup(100);
					} else {
						bot.addHitup(10);
						bot.addDmgup(30);
					}
					bot.addDamageReductionByArmor(30);
				} else if(�����ġ.startsWith("�ؼ�")){
					bot.getAC().setAc(-100);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(50);
					} else {
						bot.addHitup(30);
						bot.addDmgup(50);
					}
					bot.addDamageReductionByArmor(50);
				} else if (�����ġ.startsWith("����")) {
					bot.getAC().setAc(-80);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(50);
					} else {
						bot.addHitup(10);
						bot.addDmgup(30);
					}
					bot.addDamageReductionByArmor(30);
				} else if (�����ġ.startsWith("�Ⱘ")) {
					bot.getAC().setAc(-70);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(10);
						bot.addDmgup(20);
					} else {
						bot.addHitup(10);
						bot.addDmgup(20);
					}
					bot.addDamageReductionByArmor(30);
				} else {
					bot.getAC().setAc(-90);
					if(bot.getCurrentWeapon() == 20){
						bot.addHitup(30);
						bot.addDmgup(50);
					} else {
						bot.addHitup(10);
						bot.addDmgup(30);
					}
					bot.addDamageReductionByArmor(30);
				}
				/*
				 * }else{ bot.getAC().setAc(-60); bot.addHitup(20);
				 * bot.addBowHitup(20); bot.addDamageReductionByArmor(5); }
				 */
				bot.��ɺ� = true;
				bot.��ɺ�_��ġ = �����ġ;
				bot._���������� = false;
				bot.getMoveState().setHeading(_random.nextInt(8));
				bot.getMoveState().setMoveSpeed(1); 
				bot.getSkillEffectTimerSet().setSkillEffect(HASTE,
						(_random.nextInt(400) + 1700) * 1000);
				if (bot.isKnight() || bot.isCrown()) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.STATUS_BRAVE,
							(_random.nextInt(600) + 400) * 1000);
					bot.getMoveState().setBraveSpeed(1);
				} else if (bot.isElf()) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.STATUS_ELFBRAVE,
							(_random.nextInt(600) + 400) * 1000);
					bot.getMoveState().setBraveSpeed(1);
				} else if (bot.isDragonknight()) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.BLOOD_LUST,
							(_random.nextInt(300) + 200) * 1000);
					bot.getMoveState().setBraveSpeed(1);
				} else if (bot.isDarkelf()) {
					bot.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.MOVING_ACCELERATION,
							(_random.nextInt(600) + 400) * 1000);
					bot.getMoveState().setBraveSpeed(4);
				}
				L1Clan clan = L1World.getInstance().getClan(bot.getClanname());
				if (clan != null) {
					if (bot.getClanid() == clan.getClanId() && // ũ���� �ػ���, ����,
																// ������ ũ���� â���Ǿ���
																// ���� ��å
							bot.getClanname().toLowerCase()
									.equals(clan.getClanName().toLowerCase())) {
						clan.addOnlineClanMember(bot.getName(), bot);
						S_ServerMessage sm = new S_ServerMessage(843,bot.getName());
						for (L1PcInstance clanMember : clan
								.getOnlineClanMember()) {
							if (clanMember.getId() != bot.getId()) {
								clanMember.sendPackets(sm);
							}
						}
					}
				}
				Robot.poly(bot);
				bot.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.SHAPE_CHANGE, 1800 * 1000);
				L1World.getInstance().storeObject(bot);
				L1World.getInstance().addVisibleObject(bot);
				Robot.clan_join(bot);
				Robot.Doll_Spawn(bot);
				bot.updateconnect(true);
				bot.������(3000 + _random.nextInt(15000));
				//if ((_random.nextInt(100)+1) >= 60) //Ÿ�ݱ�ȯ ���� false
				bot.Ÿ�ݱ�ȯ���� = true;
				bot.Hunt_Exit_Time = System.currentTimeMillis()
						+ (60000 * (60 + _random.nextInt(40)));
				bot.startAI();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void loadbot() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM robots");
			rs = pstm.executeQuery();
			while (rs.next()) {
				L1RobotInstance newPc = new L1RobotInstance();
				newPc.setId(ObjectIdFactory.getInstance().nextId());
				newPc.setAccountName("");
				newPc.setName(rs.getString("name"));
				// rs.getInt("step");
				int level = _random.nextInt(10) + 70; //��� �κ�����
				newPc.setHighLevel(level);
				newPc.setLevel(level);
				newPc.setExp(ExpTable.getExpByLevel(level)
						+ _random.nextInt(ExpTable.getNeedExpNextLevel(level)));
				newPc.getAC().setAc(-80);
				newPc.addHitup(20);
				newPc.addBowHitup(20);
				newPc.addDamageReductionByArmor(20);

				if (_random.nextInt(100) > 85)
					newPc.setLawful(0);
				else
					newPc.setLawful(32767);
				newPc.setDead(false);
				newPc.getResistance().addMr(100 + _random.nextInt(30));
				newPc.setTitle(rs.getString("title"));
				newPc.set_sex(rs.getInt("sex"));

				newPc.setClassId(rs.getInt("class"));
				newPc.getGfxId().setTempCharGfx(rs.getInt("class"));
				newPc.getGfxId().setGfxId(rs.getInt("class"));

				int ran = _random.nextInt(100) + 1;
				if (newPc.isKnight()) {
					newPc.setCurrentWeapon(50);
					newPc.setType(1);
				} else if (newPc.isElf()) {
					/*if (newPc.getGfxId().getTempCharGfx() != 6160
							&& newPc.getGfxId().getTempCharGfx() != 11498
							&& ran < 20)
						newPc.setCurrentWeapon(4);
					else*/
						newPc.setCurrentWeapon(20);
					newPc.setType(2);
				} else if (newPc.isDarkelf()) {
					if (ran < 50)
						newPc.setCurrentWeapon(58);
					else
						newPc.setCurrentWeapon(54);
					newPc.setType(4);
				} else if (newPc.isDragonknight()) {
					//if (ran < 10)
					//	newPc.setCurrentWeapon(4); //�ܰ�
					//else
						newPc.setCurrentWeapon(50); //���
						//newPc.setCurrentWeapon(24);
					newPc.setType(5);
				} else if (newPc.isIllusionist()) {
					//if (ran < 10)
					//	newPc.setCurrentWeapon(40); //������
					//else
						newPc.setCurrentWeapon(58); //Ű��ũ
					newPc.setType(6);
				} else if (newPc.isCrown()) {
					newPc.setCurrentWeapon(0);
					newPc.setType(0);
				} else if (newPc.isWizard()) {
					newPc.setCurrentWeapon(40);
					newPc.setType(3);
				}
				StatSetting(newPc);
				HpMpUp(newPc);

				newPc.getMoveState().setMoveSpeed(0);
				newPc.getMoveState().setBraveSpeed(0);
				newPc.getMoveState().setHeading(0);

				newPc.set_food(225);
				newPc.setClanid(rs.getInt("clanid"));
				newPc.setClanname(rs.getString("clanname"));
				newPc.setClanRank(0);
				newPc.setElfAttr(0);
				newPc.set_PKcount(0);
				newPc.setExpRes(0);
				newPc.setPartnerId(0);
				newPc.setAccessLevel((short) 0);
				newPc.setGm(false);
				newPc.setMonitor(false);
				newPc.setHomeTownId(0);
				newPc.setContribution(0);
				newPc.setHellTime(0);
				newPc.setBanned(false);
				newPc.setKarma(0);
				newPc.setReturnStat(0);
				newPc.setGmInvis(false);
				newPc.noPlayerCK = true;
				newPc.setActionStatus(0);
				newPc.setRobot(true);
				newPc.getLight().turnOnOffLight();
						
				if (_random.nextInt(1000) > 200) {
					newPc.setKills(0);
					newPc.setDeaths(0);
				} else {
					newPc.setKills(0);
					newPc.setDeaths(_random.nextInt(10));
				}
				newPc.setNetConnection(null);
				put(newPc);
			}
		} catch (SQLException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static void StatSetting(L1RobotInstance bot) {
		if (bot.isKnight()) {
			bot.getAbility().setBaseStr(20);
			bot.getAbility().setStr(bot.getLevel());
			bot.getAbility().setBaseCon(14);
			bot.getAbility().setCon(14);
			bot.getAbility().setBaseDex(12);
			bot.getAbility().setDex(12);
			bot.getAbility().setBaseCha(12);
			bot.getAbility().setCha(12);
			bot.getAbility().setBaseInt(8);
			bot.getAbility().setInt(8);
			bot.getAbility().setBaseWis(9);
			bot.getAbility().setWis(9);
		} else if (bot.isElf()) {
			if (bot.getCurrentWeapon() != 20) {
				bot.getAbility().setBaseStr(18);
				bot.getAbility().setStr(bot.getLevel());
				bot.getAbility().setBaseCon(12);
				bot.getAbility().setCon(12);
				bot.getAbility().setBaseDex(12);
				bot.getAbility().setDex(12);
			} else {
				bot.getAbility().setBaseStr(11);
				bot.getAbility().setStr(11);
				bot.getAbility().setBaseCon(13);
				bot.getAbility().setCon(13);
				bot.getAbility().setBaseDex(18);
				bot.getAbility().setDex(bot.getLevel());
			}
			bot.getAbility().setBaseCha(9);
			bot.getAbility().setCha(9);
			bot.getAbility().setBaseInt(12);
			bot.getAbility().setInt(12);
			bot.getAbility().setBaseWis(12);
			bot.getAbility().setWis(12);
		} else if (bot.isWizard()) {
			bot.getAbility().setBaseStr(8);
			bot.getAbility().setStr(8);
			bot.getAbility().setBaseCon(16);
			bot.getAbility().setCon(16);
			bot.getAbility().setBaseDex(7);
			bot.getAbility().setDex(7);
			bot.getAbility().setBaseCha(8);
			bot.getAbility().setCha(8);
			bot.getAbility().setBaseInt(18);
			bot.getAbility().setInt(bot.getLevel());
			bot.getAbility().setBaseWis(18);
			bot.getAbility().setWis(18);
		} else if (bot.isDarkelf()) {
			bot.getAbility().setBaseStr(18);
			bot.getAbility().setStr(bot.getLevel());
			bot.getAbility().setBaseCon(12);
			bot.getAbility().setCon(12);
			bot.getAbility().setBaseDex(15);
			bot.getAbility().setDex(15);
			bot.getAbility().setBaseCha(9);
			bot.getAbility().setCha(9);
			bot.getAbility().setBaseInt(11);
			bot.getAbility().setInt(11);
			bot.getAbility().setBaseWis(10);
			bot.getAbility().setWis(10);
		} else if (bot.isDragonknight()) {
			bot.getAbility().setBaseStr(18);
			bot.getAbility().setStr(bot.getLevel());
			bot.getAbility().setBaseCon(15);
			bot.getAbility().setCon(15);
			bot.getAbility().setBaseDex(11);
			bot.getAbility().setDex(11);
			bot.getAbility().setBaseCha(8);
			bot.getAbility().setCha(8);
			bot.getAbility().setBaseInt(11);
			bot.getAbility().setInt(11);
			bot.getAbility().setBaseWis(12);
			bot.getAbility().setWis(12);
		} else if (bot.isIllusionist()) {
			bot.getAbility().setBaseStr(11);
			bot.getAbility().setStr(11);
			bot.getAbility().setBaseCon(17);
			bot.getAbility().setCon(17);
			bot.getAbility().setBaseDex(10);
			bot.getAbility().setDex(10);
			bot.getAbility().setBaseCha(8);
			bot.getAbility().setCha(8);
			bot.getAbility().setBaseInt(17);
			bot.getAbility().setInt(17);
			bot.getAbility().setBaseWis(12);
			bot.getAbility().setWis(12);
		}
	}

	private static void HpMpUp(L1RobotInstance bot) {
		bot.addBaseMaxHp((short) (25 + _random.nextInt(5)));
		bot.setCurrentHp(bot.getBaseMaxHp());
		bot.addBaseMaxMp((short) (10 + _random.nextInt(7)));
		bot.setCurrentMp(bot.getBaseMaxMp());
		for (int i = 0; i < bot.getLevel(); i++) {
			int randomHp = 20;// CalcStat.calcStatHp(bot.getType(),
								// bot.getBaseMaxHp(),
								// bot.getAbility().getCon());
			int randomMp = 8;

			bot.addBaseMaxHp((short) randomHp);
			bot.addBaseMaxMp((short) randomMp);
			bot.setCurrentHp(bot.getBaseMaxHp());
			bot.setCurrentMp(bot.getBaseMaxMp());
		}

	}
}
