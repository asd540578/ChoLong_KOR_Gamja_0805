package server.threads.pc;

import java.util.Random;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.L1HouseLocation;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1EffectInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.utils.CalcStat;

public class HpMpRegenThread extends Thread {

	private static HpMpRegenThread _instance;

	// private static Logger _log =
	// Logger.getLogger(HpMpRegenThread.class.getName());

	public static HpMpRegenThread getInstance() {
		if (_instance == null) {
			_instance = new HpMpRegenThread();
			_instance.start();
		}
		return _instance;
	}

	public HpMpRegenThread() {
		super("server.threads.pc.HpMpRegenThread");
	}

	public void run() {
	//	System.out.println(HpMpRegenThread.class.getName() + " ����");
		while (true) {
			try {
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					try {
						if (pc == null
								|| (pc.getNetConnection() == null && !(pc instanceof L1RobotInstance))) {
							continue;
						} else {
							if (pc.isDead()) {
								continue;
							} else {
								// HP �ι� ���� �ĸ�
								// 6�� 12��
								// 65���� ������ ������ 3~4�� ��ƽ
								// �̵� �Ҷ� 6��
								// ���� �Ҷ� 12��
								// REGENSTATE 12 6 3 ���� ���߸� ����ȭ �� ���� ������ ����
								if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STRIKER_GALE)){
									pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER,pc.get_PlusEr()), true);
								}
								pc.updateLevel();
								pc.addHpregenPoint(pc.getHpcurPoint());
								pc.setHpcurPoint(L1PcInstance.REGENSTATE_NONE);

							
								if (pc.getHpregenMax() <= pc.getHpregenPoint()) {
									pc.setHpregenPoint(0);
									regenHp(pc);
								}

								if (pc.�÷��̾���� != pc.�޽�_����) {
									if (System.currentTimeMillis() >= pc.���½ð�) {
										pc.�÷��̾���� = pc.�޽�_����;
									}
								}

								if (pc.�÷��̾���� == pc.����_����) {
									pc.addMpregenPoint(1);
									// System.out.println("���� ����");
								} else if (pc.�÷��̾���� == pc.�̵�_����) {
									pc.addMpregenPoint(2);
									// System.out.println("�̵� ����");
								} else {
									// System.out.println("�޽� ����");
									pc.addMpregenPoint(4);
								}

								// System.out.println("123");
								// pc.setMpcurPoint(4);

								if (64 <= pc.getMpregenPoint()) {
									pc.setMpregenPoint(0);
									regenMp(pc);
								}

							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
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

	public void regenMp(L1PcInstance _pc) {
		int baseMpr = 1;
		int wis = _pc.getAbility().getTotalWis();
		/*
		 * if (wis == 15 || wis == 16) { baseMpr = 2; } else if (wis >= 17) {
		 * baseMpr = 3; }
		 */

		// ���̽� WIS ȸ�� ���ʽ�
		int baseStatMpr = CalcStat.��ȸ��ƽ(_pc.getAbility().getTotalWis());
		int PotionStatMpr = CalcStat.���ǹ���ȸ��(_pc.getAbility().getTotalWis());
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_BLUE_POTION) == true) {
			if (wis < 11) {
				wis = 11;
			}
			baseMpr += wis - 10;
			baseMpr += PotionStatMpr;
		}
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_BLUE_POTION2) == true) {
			if (wis < 11) {
				wis = 11;
			}
			baseMpr += wis - 8;
			baseMpr += PotionStatMpr;
		}
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_BLUE_POTION3) == true) {
			baseMpr += 3;
			baseMpr += PotionStatMpr;
		}
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���ĺ���) == true) {
			baseMpr += 5;
		}
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���׽�����) == true) {
			baseMpr += 2;
		}
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.MEDITATION) == true) {
			int i = 0;
			int medTime = _pc.getSkillEffectTimerSet().getSkillEffectTimeSec(
					L1SkillId.MEDITATION);
			for (int a = 0; a < 40; a++) {
				if ((medTime -= 16) <= 0)
					break;
				i++;
			}
			i = 40 - i;
			baseMpr += 4 + i; // ���� �⺻ 5������ �޵�ƽ�� ���� ���� �������� 4����
		}
		if (_pc.getSkillEffectTimerSet()
				.hasSkillEffect(L1SkillId.CONCENTRATION) == true) {
			baseMpr += 2;
		}
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_WISDOM_POTION) == true) {
			baseMpr += 2;
		} else if (_pc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_WISDOM_POTION2) == true) {
			baseMpr += 2;
		}
		boolean ck = false;
		if (L1HouseLocation.isInHouse(_pc.getX(), _pc.getY(), _pc.getMapId())) {
			baseMpr += 3;
			ck = true;
		}
		if (isInn(_pc)) {
			baseMpr += 3;
			ck = true;
		}
		if (L1HouseLocation.isRegenLoc(_pc, _pc.getX(), _pc.getY(),
				_pc.getMapId())) {
			baseMpr += 3;
		}
		if (L1HouseLocation.isInn(_pc.getMapId())) {
			baseMpr += 15;
			ck = true;
		}

		int itemMpr = _pc.getInventory().mpRegenPerTick();
		itemMpr += _pc.getMpr();

		if (!ck && (_pc.get_food() < 24 || isOverWeight(_pc))) {
			baseMpr = 0;
			baseStatMpr = 0;
			if (itemMpr > 0) {
				itemMpr = 0;
			}
		}
		int mpr = baseMpr + itemMpr + baseStatMpr;
		// System.out.println("mpr : "+mpr+" base : "+baseMpr+" item : "+itemMpr+" basestat : "+baseStatMpr);
		int newMp = _pc.getCurrentMp() + mpr;

		_pc.setCurrentMp(newMp);
	}

	public void regenHp(L1PcInstance _pc) {
		Random _random = new Random();
		if (_pc.isDead()) {
			return;
		}
		int maxBonus = 1;

		// CON ���ʽ�
		/*
		 * if (11 < _pc.getLevel() && 14 <= _pc.getAbility().getTotalCon()) {
		 * maxBonus = _pc.getAbility().getTotalCon() - 12; if (25 <
		 * _pc.getAbility().getTotalCon()) { maxBonus = 14; } }
		 */
		// ���̽� CON ���ʽ�
		int basebonus = CalcStat.��ȸ��ƽ(_pc.getAbility().getTotalCon());

		int equipHpr = _pc.getInventory().hpRegenPerTick();
		equipHpr += _pc.getHpr();
		int bonus = _random.nextInt(maxBonus) + 1;

		if (_pc.getSkillEffectTimerSet()
				.hasSkillEffect(L1SkillId.NATURES_TOUCH)) {
			bonus += 15;
		}
		if (L1HouseLocation.isInHouse(_pc.getX(), _pc.getY(), _pc.getMapId())) {
			bonus += 5;
		}
		if (isInn(_pc)) {
			bonus += 5;
		}
		if (L1HouseLocation.isRegenLoc(_pc, _pc.getX(), _pc.getY(),
				_pc.getMapId())) {
			bonus += 5;
		}
		if (L1HouseLocation.isInn(_pc.getMapId())) {
			bonus += 25;
		}

		boolean inLifeStream = false;
		if (isPlayerInLifeStream(_pc)) {
			inLifeStream = true;
			// ����� ����, ������ ���������� HPR+3�� ������?
			bonus += 3;
		}

		// ������ �߷��� üũ
		if (_pc.get_food() < 24
				|| isOverWeight(_pc)
				|| _pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.BERSERKERS)) {
			if (_pc.getAbility().getTotalCon() >= 45) {
				bonus /= 2;
				basebonus /= 2;
			} else {
				bonus = 0;
				basebonus = 0;
			}
			// ��� ���� HPR ������ ������, �߷��� ���� ����������, ������ ���� ������, �߷��� ������� ȿ���� ���´�
			if (equipHpr > 0) {
				if (_pc.getAbility().getTotalCon() >= 45) {
					equipHpr /= 2;
				} else {
					equipHpr = 0;
				}
			}
		}

		int newHp = _pc.getCurrentHp();
		newHp += bonus + equipHpr + basebonus;

		if (newHp < 1) {
			newHp = 1; // HPR ���� ��� ���� ����� ���� �ʴ´�
		}
		// ���߿����� ���� ó��
		// ������ �ó����� ���Ҹ� ���� �� ������ �Ҹ�
		if (isUnderwater(_pc)) {
			newHp -= 20;
			if (newHp < 1) {
				if (_pc.isGm()) {
					newHp = 1;
				} else {
					_pc.death(null); // HP�� 0�� �Ǿ��� ���� ����Ѵ�.
				}
			}
		}
		// Lv50 ����Ʈ�� ����� ���� 1 F2F������ ���� ó��
		if (isLv50Quest(_pc) && !inLifeStream) {
			newHp -= 10;
			if (newHp < 1) {
				if (_pc.isGm()) {
					newHp = 1;
				} else {
					_pc.death(null); // HP�� 0�� �Ǿ��� ���� ����Ѵ�.
				}
			}
		}
		// ������ ���������� ���� ó��
		if (_pc.getMapId() == 410 && !inLifeStream) {
			newHp -= 10;
			if (newHp < 1) {
				if (_pc.isGm()) {
					newHp = 1;
				} else {
					_pc.death(null); // HP�� 0�� �Ǿ��� ���� ����Ѵ�.
				}
			}
		}

		if (!_pc.isDead()) {
			_pc.setCurrentHp(Math.min(newHp, _pc.getMaxHp()));
		}
		_random = null;
	}

	private boolean isUnderwater(L1PcInstance pc) {
		// ���� ���� �����ΰ�, ������ �ູ �����̸�, ������ �ƴϸ� �����Ѵ�.
		if (pc.getInventory().checkEquipped(20207)) {
			return false;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.STATUS_UNDERWATER_BREATH)) {
			return false;
		}
		if (pc.getInventory().checkEquipped(21048)
				&& pc.getInventory().checkEquipped(21049)
				&& pc.getInventory().checkEquipped(21050)) {
			return false;
		}

		return pc.getMap().isUnderwater();
	}

	private boolean isOverWeight(L1PcInstance pc) {
		// ��Ű��ƽũ����Ÿ������ ����, �Ƶ������̾� �����ΰ�
		// ��� �� �����̸�, �߷� �������� ������ �����Ѵ�.
		if (pc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.EXOTIC_VITALIZE)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.ADDITIONAL_FIRE)
				|| (pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.SCALES_WATER_DRAGON) && pc.getInventory()
						.calcWeightpercent() < 50)) {
			return false;
		}
		
		 if (pc.getInventory().checkEquipped(20049)) { return false; }
		 
		if (isInn(pc)) {
			return false;
		}

		return (50 <= pc.getInventory().calcWeightpercent()) ? true : false;
	}

	private boolean isLv50Quest(L1PcInstance pc) {
		int mapId = pc.getMapId();
		return (mapId == 2000 || mapId == 2001) ? true : false;
	}

	/**
	 * ������ PC�� ������ �ó����� �������� �ִ��� üũ�Ѵ�
	 * 
	 * @param pc
	 *            PC
	 * @return true PC�� ������ �ó����� �������� �ִ� ���
	 */
	private static boolean isPlayerInLifeStream(L1PcInstance pc) {
		L1EffectInstance effect = null;
		for (L1Object object : pc.getNearObjects().getKnownObjects()) {
			if (object instanceof L1EffectInstance == false) {
				continue;
			}
			effect = (L1EffectInstance) object;
			if (effect.getNpcId() == 81169
					&& effect.getLocation().getTileLineDistance(
							pc.getLocation()) < 4) {
				return true;
			}
		}
		return false;
	}

	private boolean isInn(L1PcInstance pc) {
		int mapId = pc.getMapId();
		return (mapId == 16384 || mapId == 16896 || mapId == 17408
				|| mapId == 17492 || mapId == 17820 || mapId == 17920
				|| mapId == 18432 || mapId == 18944 || mapId == 19456
				|| mapId == 19968 || mapId == 20480 || mapId == 20992
				|| mapId == 21504 || mapId == 22016 || mapId == 22528
				|| mapId == 23040 || mapId == 23552 || mapId == 24064
				|| mapId == 24576 || mapId == 25088) ? true : false;
	}
}
