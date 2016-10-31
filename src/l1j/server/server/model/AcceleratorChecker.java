/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package l1j.server.server.model;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import l1j.server.Config;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;

/**
 * ���ӱ��� ����� üũ�ϴ� Ŭ����.
 */
public class AcceleratorChecker {

	private final L1PcInstance _pc;

	private int _injusticeCount;

	private int _justiceCount;

	// private static final int INJUSTICE_COUNT_LIMIT = Config.INJUSTICE_COUNT;

	// private static final int JUSTICE_COUNT_LIMIT = Config.JUSTICE_COUNT;

	// �����δ� �̵��� ������ ��Ŷ ������ spr�� �̷�ġ����5%��ŭ �ʴ�.
	// �װ��� ����ء�5�� �ϰ� �ִ�.
	private static final double CHECK_STRICTNESS = (Config.CHECK_STRICTNESS - 5) / 100D;

	private static final double HASTE_RATE = 0.745;

	private static final double WAFFLE_RATE = 0.874;// 874;

	private static final double THIRDSPEED_RATE = 0.874;// by���

	private final EnumMap<ACT_TYPE, Long> _actTimers = new EnumMap<ACT_TYPE, Long>(
			ACT_TYPE.class);

	private final EnumMap<ACT_TYPE, Long> _checkTimers = new EnumMap<ACT_TYPE, Long>(
			ACT_TYPE.class);

	public static enum ACT_TYPE {
		MOVE, ATTACK, SPELL_DIR, SPELL_NODIR
	}

	// üũ�� ���
	public static final int R_OK = 0;

	public static final int R_DETECTED = 1;

	public static final int R_DISCONNECTED = 2;

	private static final double Level_Rate_0 = 1.392;
	private static final double Level_Rate_15 = 1.321;
	private static final double Level_Rate_30 = 1.25;
	private static final double Level_Rate_45 = 1.178;
	private static final double Level_Rate_50 = 1.107;
	private static final double Level_Rate_52 = 1.035;
	private static final double Level_Rate_55 = 0.964;
	private static final double Level_Rate_75 = 0.892;
	private static final double Level_Rate_80 = 0.821;

	private static final double Level_Rate_82 = 0.812;
	private static final double Level_Rate_85 = 0.794;

	private static final double Move_Level_Rate_0 = 1.023;
	private static final double Move_Level_Rate_15 = 0.992;
	private static final double Move_Level_Rate_45 = 0.960;
	private static final double Move_Level_Rate_50 = 0.929;
	private static final double Move_Level_Rate_52 = 0.898;
	private static final double Move_Level_Rate_55 = 0.867;
	private static final double Move_Level_Rate_60 = 0.835;
	private static final double Move_Level_Rate_65 = 0.804;
	private static final double Move_Level_Rate_70 = 0.773;
	private static final double Move_Level_Rate_75 = 0.773;
	private static final double Move_Level_Rate_80 = 0.773;

	// cache
	private int _attack_gfxid = -1;
	private int _attack_weapon = -1;
	private int _attack_interval = -1;
	private int _move_gfxid = -1;
	private int _move_weapon = -1;
	private int _move_interval = -1;
	private int _spelldir_gfxid = -1;
	private int _spelldir_interval = -1;
	private int _spellnodir_gfxid = -1;
	private int _spellnodir_interval = -1;

	public AcceleratorChecker(L1PcInstance pc) {
		_pc = pc;
		_injusticeCount = 0;
		_justiceCount = 0;
		long now = System.currentTimeMillis();
		for (ACT_TYPE each : ACT_TYPE.values()) {
			_actTimers.put(each, now);
			_checkTimers.put(each, now);
		}
	}

	/**
	 * �׼��� ������ �������� ������ üũ��, ���� ó���� �ǽ��Ѵ�.
	 * 
	 * @param type
	 *            - üũ�ϴ� �׼��� Ÿ��
	 * @return ������ ������ ���� 0, ������ ���� 1, ���� ������ ���� ȸ���� �̸����� ������ �÷��̾ ���� ���� ����
	 *         2�� �����ش�.
	 */
	public int checkInterval(ACT_TYPE type) {
		int result = R_OK;
		long now = System.currentTimeMillis();
		long interval = now - _actTimers.get(type);
		int rightInterval = getRightInterval(type);

		if (type == ACT_TYPE.MOVE) {
			_pc.speed_time_temp = rightInterval + now;
		}

		interval *= CHECK_STRICTNESS;
		interval *= 1.1;
		if (_pc.isGm() || _pc.isSGm()) {
			return R_OK;
		}

		if (_pc.getGfxId().getTempCharGfx() == 6284) { // ��������ȣ��
			_injusticeCount = 0;
			_justiceCount = 0;
			return R_OK;
		}
		if (0 < interval && interval < rightInterval) {
			_injusticeCount++;
			_justiceCount = 0;
			result = R_DETECTED;
			if (_injusticeCount >= Config.INJUSTICE_COUNT) {
				_injusticeCount = 0;
				result = R_DISCONNECTED;
			}
		} else if (interval >= rightInterval) {
			_justiceCount++;
			if (_justiceCount >= Config.JUSTICE_COUNT) {
				_injusticeCount = 0;
				_justiceCount = 0;
			}
		}

		// ������
		/*
		 * double rate = (double) interval / rightInterval;
		 * System.out.println(String.format("%s: %d / %d = %.2f (o-%d x-%d)",
		 * type.toString(), interval, rightInterval, rate, _justiceCount,
		 * _injusticeCount));
		 */
		_actTimers.put(type, now);
		return result;
	}

	/*
	 * private void doDisconnect(String type) { if (!(_pc.getAccessLevel() ==
	 * Config.GMCODE)) { L1Teleport.teleport(_pc, _pc.getSpeedHackX(),
	 * _pc.getSpeedHackY(), _pc.getSpeedHackMapid(), _pc.getSpeedHackHeading(),
	 * false); //_pc.sendPackets(new S_ServerMessage(945)); // ���� ���α׷��� �߰ߵǾ����Ƿ�,
	 * �����մϴ�. //_pc.sendPackets(new S_Disconnect()); } else { // GM�� ���� ���� �ʴ´�
	 * _pc.sendPackets(new S_SystemMessage(
	 * "[���� ����] ĳ���� - "+_pc.getName()+" / ���� - "
	 * +_pc.getGfxId().getTempCharGfx()+" / Ÿ�� -"+type)); _injusticeCount = 0; }
	 * 
	 * }
	 */
	/**
	 * PC ���·κ��� ������ ������ �׼��� �ùٸ� ���͹�(ms)�� �����, �����ش�.
	 * 
	 * @param type
	 *            - �׼��� ����
	 * @param _pc
	 *            - �����ϴ� PC
	 * @return �ùٸ� ���͹�(ms)
	 */
	private int getRightInterval(ACT_TYPE type) {
		int interval;
		int gfxid = _pc.getGfxId().getTempCharGfx();
		int weapon = _pc.getCurrentWeapon();

		switch (type) {
		case ATTACK:
			if (_attack_gfxid != gfxid || _attack_weapon != weapon) {
				_attack_gfxid = gfxid;
				_attack_weapon = weapon;
				_attack_interval = SprTable.getInstance().getAttackSpeed(gfxid,
						weapon + 1);
			}

			interval = _attack_interval;

			if (gfxid == 13140) {
				interval *= Level_Rate_80;
			}

			if ((gfxid >= 11328 && gfxid <= 11448) || gfxid == 12237
					|| gfxid == 12702 || gfxid == 12681 || gfxid == 12541
					|| gfxid == 12542 || gfxid == 13152 || gfxid == 13153) {
				if (_pc.getLevel() >= 85) {
					interval *= Level_Rate_85;
				} else if (_pc.getLevel() >= 82) {
					interval *= Level_Rate_82;
				} else if (_pc.getLevel() >= 80) {
					interval *= Level_Rate_80;
				} else if (_pc.getLevel() >= 75) {
					interval *= Level_Rate_75;
				} else if (_pc.getLevel() >= 55) {
					interval *= Level_Rate_55;
				} else if (_pc.getLevel() >= 52) {
					interval *= Level_Rate_52;
				} else if (_pc.getLevel() >= 50) {
					interval *= Level_Rate_50;
				} else if (_pc.getLevel() >= 45) {
					interval *= Level_Rate_45;
				} else if (_pc.getLevel() >= 30) {
					interval *= Level_Rate_30;
				} else if (_pc.getLevel() >= 15) {
					interval *= Level_Rate_15;
				} else {
					interval *= Level_Rate_0;
				}
			}
			break;

		case MOVE:
			if (_move_gfxid != gfxid || _move_weapon != weapon) {
				_move_gfxid = gfxid;
				_move_weapon = weapon;
				_move_interval = SprTable.getInstance().getMoveSpeed(gfxid,
						weapon);
				// System.out.printf("%s: gfxid:%d, weapon:%d, interval:%d\n",
				// _pc.getName(), gfxid, weapon, _move_interval);
			}
			interval = _move_interval;
			/*
			 * if(gfxid == 11333 || gfxid == 11355 || gfxid == 11364 || gfxid ==
			 * 11379) interval *= 0.9; else if(gfxid == 11343) interval *= 0.8;
			 */
			if (gfxid == 13140) {
				interval *= Move_Level_Rate_80;
			}
			if (gfxid == 11333 || // "lv1 dwarf" ; ������
					gfxid == 11343 || // "lv15 ungoliant" ; ���񸮾�Ʈ
					gfxid == 11355 || // "lv30 cockatrice" ; ��īƮ����
					gfxid == 11364 || // "lv45 baphomet" ; ������Ʈ
					gfxid == 11379// "lv52 beleth" ; ������
			) {
				if (_pc.getLevel() >= 80) {
					interval *= Move_Level_Rate_80;
				} else if (_pc.getLevel() >= 75) {
					interval *= Move_Level_Rate_75;
				} else if (_pc.getLevel() >= 70) {
					interval *= Move_Level_Rate_70;
				} else if (_pc.getLevel() >= 65) {
					interval *= Move_Level_Rate_65;
				} else if (_pc.getLevel() >= 60) {
					interval *= Move_Level_Rate_60;
				} else if (_pc.getLevel() >= 55) {
					interval *= Move_Level_Rate_55;
				} else if (_pc.getLevel() >= 52) {
					interval *= Move_Level_Rate_52;
				} else if (_pc.getLevel() >= 50) {
					interval *= Move_Level_Rate_50;
				} else if (_pc.getLevel() >= 45) {
					interval *= Move_Level_Rate_45;
				} else if (_pc.getLevel() >= 15) {
					interval *= Move_Level_Rate_15;
				} else {
					interval *= Move_Level_Rate_0;
				}
			}
			break;

		case SPELL_DIR:
			if (_spelldir_gfxid != gfxid) {
				_spelldir_gfxid = gfxid;
				_spelldir_interval = SprTable.getInstance().getDirSpellSpeed(
						gfxid);
			}
			interval = _spelldir_interval;
			break;

		case SPELL_NODIR:
			if (_spellnodir_gfxid != gfxid) {
				_spellnodir_gfxid = gfxid;
				_spellnodir_interval = SprTable.getInstance()
						.getNodirSpellSpeed(gfxid);
			}
			interval = _spellnodir_interval;
			break;

		default:
			return 0;
		}
		if (_pc.isHaste()) {
			interval *= HASTE_RATE;
		}
		if (type.equals(ACT_TYPE.MOVE) && _pc.isFastMovable()) {
			interval *= HASTE_RATE;
		}
		if (type.equals(ACT_TYPE.MOVE) && _pc.isIllusionist()
				&& _pc.isUgdraFruit()) {
			interval *= HASTE_RATE;
		}
		if (_pc.isBloodLust()) { // ���巯��Ʈ
			interval *= HASTE_RATE;
		}

		if (_pc.isBrave()) {
			interval *= HASTE_RATE;
		}
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.FIRE_BLESS)) {
			interval *= HASTE_RATE;
		}
		if (type.equals(ACT_TYPE.MOVE) && _pc.isElfBrave()) {
			interval *= HASTE_RATE;
		}

		if (type.equals(ACT_TYPE.ATTACK) && _pc.isElfBrave()) {
			interval *= WAFFLE_RATE;
		}
		if (_pc.isThirdSpeed()) {
			interval *= THIRDSPEED_RATE;
		}
		if (_pc.getMapId() == 5143) {
			interval *= (HASTE_RATE / 2);
		}
		if (type.equals(ACT_TYPE.MOVE) && (gfxid == 6697 || gfxid == 6698)) {
			interval *= HASTE_RATE;
		}

		return interval;
	}

	private long _skillNextUseTime = 0;

	private static Map<Integer, Integer> _skillInterval = new HashMap<Integer, Integer>();
	static {
		// �� ��ų�� ����� �� �ִ� �ִ�ӵ�(��ũ+����� Ŭ������ 2������)�� üũ�Ѵ�.
		int e = 50; // ��������

		_skillInterval.put(187, 2100 - e); // ���� �����̾�
		// _skillInterval.put(189, 2400 - e); // ��ũ��Ų
		_skillInterval.put(192, 4400 - e); // ��� �׷�
		_skillInterval.put(87, 8400 - e); // ����
		_skillInterval.put(146, 1550 - e); // ���� �� ���
		_skillInterval.put(130, 500 - e); // �ٵ������ε� - 500
		_skillInterval.put(35, 600 - e); // �׷����� �� - 600
		_skillInterval.put(132, 250 - e); // Ʈ���� - 400
		_skillInterval.put(57, 300 - e); // Ǯ �� - 900
		_skillInterval.put(19, 500 - e); // �ͽ�Ʈ�� �� - 500
		_skillInterval.put(45, 500 - e); // �̷��� - 500
		_skillInterval.put(38, 500 - e); // �ܿ����ݵ� - 500
		_skillInterval.put(46, 800 - e); // ������Ʈ - 900
		_skillInterval.put(70, 850 - e); // ���̾�� - 850
		_skillInterval.put(65, 2300 - e); // ����Ʈ�� ���� - 2300
		_skillInterval.put(78, 12400 - e); // �ۼַ�Ʈ �踮�� - 12400
		_skillInterval.put(74, 3400 - e); // ��Ƽ�� - 3400
		_skillInterval.put(77, 4000 - e); // �� - 5350
		_skillInterval.put(44, 850 - e); // ĵ�� - 850
		_skillInterval.put(29, 850 - e); // ���� - 850
		_skillInterval.put(39, 850 - e); // �����巹�� - 850
		_skillInterval.put(27, 3500 - e); // �����극��ũ - 3500

	}

	public int checkSkillInterval(int skillId) {
		int result = R_OK;
		long now = System.currentTimeMillis();
		int rinterval;

		if (_skillNextUseTime - now > 12400) {
			_skillNextUseTime = now;
		}

		if (now < _skillNextUseTime) {
			result = R_DETECTED;
		} else {
			// ���� ��ų��� ���ɽð��� ���
			rinterval = getRightSkillInterval(skillId);
			_skillNextUseTime = now + (long) rinterval;
		}
		return result;
	}

	private int getRightSkillInterval(int skillId) {
		Integer delay = _skillInterval.get(skillId);

		if (delay == null) {
			return 350;
		}
		return delay;
	}
}
