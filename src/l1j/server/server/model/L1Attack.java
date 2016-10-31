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

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.ARMOR_BREAK;
import static l1j.server.server.model.skill.L1SkillId.BERSERKERS;
import static l1j.server.server.model.skill.L1SkillId.BRAVE_AURA;
import static l1j.server.server.model.skill.L1SkillId.BURNING_SLASH;
import static l1j.server.server.model.skill.L1SkillId.BURNING_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.BURNING_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�߰��;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_����;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_ĥ����;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�ѿ�;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.DOUBLE_BRAKE;
import static l1j.server.server.model.skill.L1SkillId.DRAGON_SKIN;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.ELEMENTAL_FIRE;
import static l1j.server.server.model.skill.L1SkillId.ENCHANT_VENOM;
import static l1j.server.server.model.skill.L1SkillId.FEAR;
import static l1j.server.server.model.skill.L1SkillId.HEUKSA;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_A;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_B;
import static l1j.server.server.model.skill.L1SkillId.FIRE_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.IMMUNE_TO_HARM;
import static l1j.server.server.model.skill.L1SkillId.IllUSION_AVATAR;
import static l1j.server.server.model.skill.L1SkillId.LIFE_MAAN;
import static l1j.server.server.model.skill.L1SkillId.MIRROR_IMAGE;
import static l1j.server.server.model.skill.L1SkillId.MOB_BASILL;
import static l1j.server.server.model.skill.L1SkillId.MOB_COCA;
import static l1j.server.server.model.skill.L1SkillId.PATIENCE;
import static l1j.server.server.model.skill.L1SkillId.REDUCTION_ARMOR;
import static l1j.server.server.model.skill.L1SkillId.SOUL_OF_FLAME;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_BARLOG;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_YAHEE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_MITHRIL_POWDER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER_OF_EVA;
import static l1j.server.server.model.skill.L1SkillId.UNCANNY_DODGE;
import static l1j.server.server.model.skill.L1SkillId.VALA_MAAN;
import static l1j.server.server.model.skill.L1SkillId.��Ƽ����������;
import static l1j.server.server.model.skill.L1SkillId.��Ƽ�������丮;
import static l1j.server.server.model.skill.L1SkillId.��Ƽ���ູ�ֹ���;
import static l1j.server.server.model.skill.L1SkillId.���̸����Ѷ��;
import static l1j.server.server.model.skill.L1SkillId.���̽ÿ�������;

import java.util.Random;

import l1j.server.Config;
import l1j.server.GameSystem.Astar.World;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.ActionCodes;
import l1j.server.server.Opcodes;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.WeaponAddDamage;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.model.poison.L1DamagePoison;
import l1j.server.server.model.poison.L1ParalysisPoison;
import l1j.server.server.model.poison.L1SilencePoison;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_AttackMissPacket;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_AttackPacketForNpc;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_CreateItem;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_UseArrowSkill;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.types.Point;
import l1j.server.server.utils.CalcStat;
import l1j.server.server.utils.CommonUtil;

public class L1Attack {

	private L1PcInstance _pc = null;

	private L1Character _target = null;

	private L1PcInstance _targetPc = null;

	private L1NpcInstance _npc = null;

	private L1NpcInstance _targetNpc = null;

	private final int _targetId;

	private int _targetX;

	private int _targetY;

	private int _statusDamage = 0;

	private static final Random _random = new Random(System.nanoTime());

	private int _hitRate = 0;

	private int _calcType;

	private static final int PC_PC = 1;

	private static final int PC_NPC = 2;

	private static final int NPC_PC = 3;

	private static final int NPC_NPC = 4;

	private boolean _isHit = false;

	private int _damage = 0;

	private int _drainMana = 0;

	/** ������ ���� **/

	private int _drainHp = 0;

	/** ������ ���� **/

	private int _attckGrfxId = 0;

	private int _attckActId = 0;
	
	private static final int[] strDmg = new int[128];

	static {
		// STR ������ ����
		for (int str = 0; str <= 8; str++) {
			// 1~8�� -2
			strDmg[str] = -2;
		}
		for (int str = 9; str <= 10; str++) {
			// 9~10�� -1
			strDmg[str] = -1;
		}
		strDmg[11] = 0;     strDmg[12] = 0;     strDmg[13] = 1;    strDmg[14] = 1;
		strDmg[15] = 2;		strDmg[16] = 2;		strDmg[17] = 3;	   strDmg[18] = 3;
		strDmg[19] = 4;     strDmg[20] = 4;     strDmg[21] = 5;    strDmg[22] = 5;
		strDmg[23] = 6;     strDmg[24] = 6;     strDmg[25] = 6;    strDmg[26] = 7;
		strDmg[27] = 7;     strDmg[28] = 7;     strDmg[29] = 8;    strDmg[30] = 8;
		strDmg[31] = 9;     strDmg[32] = 9;     strDmg[33] = 10;   strDmg[34] = 11;
		strDmg[35] = 12;    strDmg[36] = 12;    strDmg[37] = 12;   strDmg[38] = 12;
		strDmg[39] = 13;    strDmg[40] = 13;    strDmg[41] = 13;   strDmg[42] = 13;
		strDmg[43] = 14;    strDmg[44] = 14;    strDmg[45] = 14;   strDmg[46] = 14;
		strDmg[47] = 15;    strDmg[48] = 15;    strDmg[49] = 16;   strDmg[50] = 17;
		int dmg = 18; 
		for (int str = 51; str <= 127; str++) { // 51~127�� 4���٣�1
			if (str % 4 == 1) {
				dmg++;
			}
			strDmg[str] = dmg;
		}
	}

		private static final int[] dexDmg = new int[128];

		static {
			// DEX ������ ����
			for (int dex = 0; dex <= 11; dex++) {
				// 0~11�� 0
				dexDmg[dex] = 0;
			}
			dexDmg[12] = 1;     dexDmg[13] = 1;     dexDmg[14] = 1;     dexDmg[15] = 1;
			dexDmg[16] = 2;     dexDmg[17] = 2;     dexDmg[18] = 2;     dexDmg[19] = 2;		
			dexDmg[20] = 3;		dexDmg[21] = 3;		dexDmg[22] = 3;     dexDmg[23] = 3;		
			dexDmg[24] = 4;		dexDmg[25] = 4;		dexDmg[26] = 4;     dexDmg[27] = 4;		
			dexDmg[28] = 5;		dexDmg[29] = 5;		dexDmg[30] = 5;     dexDmg[31] = 5;		
			dexDmg[32] = 6;		dexDmg[33] = 6;		dexDmg[34] = 6;     dexDmg[35] = 6;     
			
			int dmg = 7;
			for (int dex = 36; dex <= 127; dex++) { // 48~127�� 4���٣�1 //#
				if (dex % 4 == 1) {
					dmg++;
				}
				dexDmg[dex] = dmg;
			}
		}

		private static final int[] IntDmg = new int[128]; // Ű��ũ ��Ʈ

		 static {
				for (int Int = 0; Int <= 8; Int++) {
					// 1~8�� -2
					IntDmg[Int] = -2;
				}
				for (int Int = 9; Int <= 14; Int++) {
					// 9~10�� -1
					IntDmg[Int] = -1;
				}
				IntDmg[15] = 0;
				IntDmg[16] = 0;
				IntDmg[17] = 1;
				IntDmg[18] = 1;
				IntDmg[19] = 2;
				IntDmg[20] = 2;
				IntDmg[21] = 2;
				IntDmg[22] = 2;
				IntDmg[23] = 3;
				IntDmg[24] = 3;
				IntDmg[25] = 3;
				IntDmg[26] = 4;
				IntDmg[27] = 4;
				IntDmg[28] = 4;
				IntDmg[29] = 5;
				IntDmg[30] = 5;
				IntDmg[31] = 5;
				IntDmg[32] = 6;
				IntDmg[33] = 6;
				IntDmg[34] = 6;
				int dmg = 7; 
				for (int Int = 35; Int <= 127; Int++) { // 35~127�� 4���٣�1
					if (Int % 4 == 1) {
						dmg++;
					}
					IntDmg[Int] = dmg;
				}
			}

	// �����ڰ� �÷��̾��� ����� ���� ����
	private L1ItemInstance Sweapon = null;// ��������
	private int _SweaponId = 0;
	private int _SweaponType = 0;
	private int _SweaponAddDmg = 0;
	private int _SweaponSmall = 0;
	private int _SweaponLarge = 0;
	private int _SweaponBless = 1;
	private int _SweaponEnchant = 0;
	private int _SweaponMaterial = 0;
	private int _SweaponAttrEnchantLevel = 0;

	private L1ItemInstance weapon = null;

	private int _weaponId = 0;

	private int _weaponType = 0;

	private int _weaponType1 = 0;

	private int _weaponAddHit = 0;

	private int _weaponAddDmg = 0;

	private int _weaponSmall = 0;

	private int _weaponLarge = 0;

	private int _weaponRange = 1;

	private int _weaponBless = 1;

	private int _weaponEnchant = 0;

	private int _weaponMaterial = 0;

	private int _weaponAttr = 0;

	private int _weaponAttrEnchantLevel = 0;

	private int _weaponDoubleDmgChance = 0;

	private int _attackType = 0;

	private boolean _ũ��Ƽ�� = false;
	private L1ItemInstance _arrow = null;

	private L1ItemInstance _sting = null;

	private int _leverage = 10; // 1/10��� ǥ���Ѵ�.

	public void setLeverage(int i) {
		_leverage = i;
	}

	private int getLeverage() {
		return _leverage;
	}

	// �����ڰ� �÷��̾��� ����� �������ͽ��� ���� ����
	public void setActId(int actId) {
		_attckActId = actId;
	}

	public void setGfxId(int gfxId) {
		_attckGrfxId = gfxId;
	}

	public int getActId() {
		return _attckActId;
	}

	public int getGfxId() {
		return _attckGrfxId;
	}

	public L1Attack(L1Character attacker, L1Character target) {
		if (attacker instanceof L1PcInstance) {
			_pc = (L1PcInstance) attacker;
			if (target instanceof L1PcInstance) {
				_targetPc = (L1PcInstance) target;
				_calcType = PC_PC;
			} else if (target instanceof L1NpcInstance) {
				_targetNpc = (L1NpcInstance) target;
				_calcType = PC_NPC;
			}
			// ���� ������ ���
			weapon = _pc.getWeapon();
			Sweapon = _pc.getSecondWeapon();
			if (Sweapon != null) {
				_SweaponId = Sweapon.getItem().getItemId();
				_SweaponType = Sweapon.getItem().getType1();
				_SweaponAddDmg = Sweapon.getItem().getDmgModifier()
						+ Sweapon.getDmgByMagic();
					_SweaponSmall = Sweapon.getItem().getDmgSmall();
				_SweaponLarge = Sweapon.getItem().getDmgLarge();

				_SweaponBless = Sweapon.getItem().getBless();
				_SweaponEnchant = Sweapon.getEnchantLevel()
						- Sweapon.get_durability(); // �ջ�� ���̳ʽ�
				_SweaponMaterial = Sweapon.getItem().getMaterial();
				_SweaponAttrEnchantLevel = Sweapon.getAttrEnchantLevel();
			}
			if (weapon != null) {
				_weaponId = weapon.getItem().getItemId();
				_weaponType = weapon.getItem().getType1();
				_weaponAddHit = weapon.getItem().getHitModifier()
						+ weapon.getHitByMagic();
				_weaponAddDmg = weapon.getItem().getDmgModifier()
						+ weapon.getDmgByMagic()+ weapon.getItem().getaddDmg();
				_weaponType1 = weapon.getItem().getType();
				_weaponSmall = weapon.getItem().getDmgSmall();
				_weaponLarge = weapon.getItem().getDmgLarge();
				_weaponRange = weapon.getItem().getRange();
				_weaponBless = weapon.getItem().getBless();
				if (_weaponType != 20 && _weaponType != 62) {
					_weaponEnchant = weapon.getEnchantLevel()
							- weapon.get_durability(); // �ջ�� ���̳ʽ�
				} else {
					_weaponEnchant = weapon.getEnchantLevel();
				}
				_weaponMaterial = weapon.getItem().getMaterial();
				if (_weaponType == 20) { // ȭ���� ���
					_arrow = _pc.getInventory().getArrow();
					if (_arrow != null) {
						if (_arrow.getItemId() == 50747)// ����
							_weaponAttr = 1;
						if (_arrow.getItemId() == 50748)// ǳ��
							_weaponAttr = 2;
						if (_arrow.getItemId() == 50749)// ����
							_weaponAttr = 3;
						if (_arrow.getItemId() == 50750)// ȭ��
							_weaponAttr = 4;

						_weaponBless = _arrow.getItem().getBless();
						_weaponMaterial = _arrow.getItem().getMaterial();
					}
				} else if (_weaponType == 62) { // ������ ���
					_sting = _pc.getInventory().getSting();
					if (_sting != null) {
						_weaponBless = _sting.getItem().getBless();
						_weaponMaterial = _sting.getItem().getMaterial();
					}
				}
				_weaponDoubleDmgChance = weapon.getItem().getDoubleDmgChance();
				_weaponAttrEnchantLevel = weapon.getAttrEnchantLevel();
			}
			// �������ͽ��� ���� �߰� ������ ����
			if (_weaponType == 20 || _weaponType == 62) { // Ȱ�� ���� DEXġ ����
				_statusDamage = dexDmg[_pc.getAbility().getTotalDex()] + CalcStat.���Ÿ������(_pc.getAbility().getTotalDex());
			} else {
				_statusDamage = strDmg[_pc.getAbility().getTotalStr()] + CalcStat.�ٰŸ������(_pc.getAbility().getTotalStr());
			}
		}
		
		
		else if (_weaponId == 410003 || _weaponId == 410004
				|| _weaponId == 411030 || _weaponId == 450006
				|| _weaponId == 450013) { // Ű��ũ�� ��� INTġ ����
			_statusDamage = IntDmg[_pc.getAbility().getTotalInt()] + CalcStat.���������(_pc.getAbility().getTotalInt());
		} else if (attacker instanceof L1NpcInstance) {
			_npc = (L1NpcInstance) attacker;
			if (target instanceof L1PcInstance) {
				_targetPc = (L1PcInstance) target;
				_calcType = NPC_PC;
			} else if (target instanceof L1NpcInstance) {
				_targetNpc = (L1NpcInstance) target;
				_calcType = NPC_NPC;
			}
		}
		if (target != null) {
			_target = target;
			_targetId = target.getId();
			_targetX = target.getX();
			_targetY = target.getY();
		} else {
			_targetId = 0;
		}
	}

	/* ����������������� ���� ���� ����������������� */
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

	public int calcheading(L1Character o, int x, int y) {
		return calcheading(o.getX(), o.getY(), x, y);
	}

	public boolean calcHit() {
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			if (_target.getX() == _pc.getX() && _target.getY() == _pc.getY()
					&& _target.getMapId() == _target.getMapId()) {
				_isHit = true;
				return true;
			}
			if (_target instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) _target;
				if (mon.isreodie) {
					return false;
				}
			}
			boolean door = World.���̵�(_pc.getX(), _pc.getY(), _pc.getMapId(),
					calcheading(_pc, _target.getX(), _target.getY()));
			boolean tail = World.isThroughAttack(_pc.getX(), _pc.getY(),
					_pc.getMapId(),
					calcheading(_pc, _target.getX(), _target.getY()));
			if (_pc.getX() == _target.getX() && _pc.getY() == _target.getY()
					&& _pc.getMapId() == _target.getMapId())
				tail = true;
			if (door || !tail) {
				if (!(_target instanceof L1DoorInstance)) {
					_isHit = false;
					return _isHit;
				}
			}
			if (_pc instanceof L1RobotInstance && _pc.isElf()) {
				if (!_pc.getLocation().isInScreen(_target.getLocation())) {
					_isHit = false;
					return _isHit;
				}
			} else if (_weaponRange != -1) {
				if (_target instanceof L1MonsterInstance) {
					if (((L1MonsterInstance) _target).getNpcId() == 100584
							|| ((L1MonsterInstance) _target).getNpcId() == 100588
							|| ((L1MonsterInstance) _target).getNpcId() == 100589)
						_weaponRange++;
				}
				if (_pc.getLocation()
						.getTileLineDistance(_target.getLocation()) > _weaponRange + 1) {
					_isHit = false;
					return _isHit;
				}
				if (_weaponType1 == 17) {
					_isHit = true;
					if (_target instanceof L1NpcInstance) {
						int npcId = ((L1NpcInstance) _target).getNpcTemplate()
								.get_npcId();
						if (!_pc.getSkillEffectTimerSet().hasSkillEffect(
								STATUS_CURSE_BARLOG)
								&& (npcId == 45752 || npcId == 45753
								|| npcId == 7000026 || npcId == 7000027
								|| npcId == 7000012 || npcId == 7000019
								|| npcId == 7000006 || npcId == 7000013
								|| npcId == 7000020 || npcId == 7000007
								|| npcId == 7000014 || npcId == 7000021
								|| npcId == 7000030 || npcId == 7000031
								|| npcId == 7000032 || npcId == 7000033
								|| npcId == 7000034 || npcId == 7000035
								|| npcId == 7000036 || npcId == 7000037
								|| npcId == 7000038 || npcId == 7000039
								|| npcId == 7000040 || npcId == 7000041
								|| npcId == 7000008 || npcId == 7000015
								|| npcId == 7000022 || npcId == 7000009
								|| npcId == 7000016 || npcId == 7000023
								|| npcId == 7000010 || npcId == 7000017
								|| npcId == 7000024 || npcId == 7000028
								|| npcId == 7000029 || npcId == 7000030
								|| npcId == 7000011 || npcId == 7000018
								|| npcId == 7000025 || npcId == 7000042)) {// �߷�
							_isHit = false;
						} else if (!_pc.getSkillEffectTimerSet()
								.hasSkillEffect(STATUS_CURSE_YAHEE)
								&& (npcId == 45675 || npcId == 81082
								|| npcId == 45625 || npcId == 45674 || npcId == 100570)) {
							_isHit = false;
						}
					} else if (_target instanceof L1PcInstance) {
						if (_target.getSkillEffectTimerSet().hasSkillEffect(
								COUNTER_MAGIC)) {
							_target.getSkillEffectTimerSet().removeSkillEffect(
									COUNTER_MAGIC);
							int castgfx = 10702;
							Broadcaster.broadcastPacket(_target,
									new S_SkillSound(_target.getId(), castgfx),
									true);
							((L1PcInstance) _target).sendPackets(
									new S_SkillSound(_target.getId(), castgfx),
									true);
							_isHit = false;
						}
					}
					return _isHit;
				}
			} else {
				if (!_pc.getLocation().isInScreen(_target.getLocation())) {
					_isHit = false;
					return _isHit;
				}
			}
			if (!(_pc instanceof L1RobotInstance) && _weaponType == 20
					&& _weaponId != 190 && _weaponId != 100190 && _weaponId != 30082
					&& _weaponId != 11011 && _weaponId != 11012 && _weaponId != 31082
					&& _weaponId != 11013 && _weaponId != 7201 && _weaponId != 222206
					&& _arrow == null) {
				_isHit = false; // ȭ���� ���� ���� �̽�
			} else if (_weaponType == 62 && _sting == null) {
				_isHit = false; // ������ ���� ���� �̽�
			} else if (!(_target instanceof L1DoorInstance)
					&& (!CharPosUtil.isAreaAttack(_pc, _targetX, _targetY,
							_target.getMapId()) || !CharPosUtil.isAreaAttack(
									_target, _pc.getX(), _pc.getY(), _pc.getMapId()))) {
				_isHit = false; // �����ڰ� �÷��̾��� ���� ��ֹ� ����
			} else if (_weaponId == 247 || _weaponId == 248 || _weaponId == 249) {
				_isHit = false; // �÷��� ��B~C������ ��ȿ
			} else if (_calcType == PC_PC) {
				if (CharPosUtil.getZoneType(_pc) == 1 || CharPosUtil.getZoneType(_targetPc) == 1) {
			//		_pc.sendPackets(new S_SkillSound(_target.getId(), 13418));// ����Ʈ
			//		_targetPc.sendPackets(new S_SkillSound(_target.getId(), 13418));// ����Ʈ
					_isHit = false;
				}
				if(_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�ۼַ�Ʈ���̵�) && _targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER) ){
					int c = _pc.getLevel()-80;
					if(c >8) //�ִ�
						c=8;
					if(c <3)
						c=3; //�ּ�
					if(CommonUtil.random(100) <= c){
						_targetPc.getSkillEffectTimerSet().removeSkillEffect(ABSOLUTE_BARRIER);
						_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 14539));
						_pc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 14539));		
					}			
				}
				_isHit = calcPcPcHit();
				if (_isHit == false) {
			//		_pc.sendPackets(new S_SkillSound(_target.getId(), 13418));// ����Ʈ
				//	_targetPc.sendPackets(new S_SkillSound(_target.getId(), 13418));// ����Ʈ
				}
			} else if (_calcType == PC_NPC) {
				_isHit = calcPcNpcHit();
				if (_isHit == false) {
					_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 13418));// ����Ʈ
				}
			}
		} else if (_calcType == NPC_PC) {
			_isHit = calcNpcPcHit();
			if (_isHit == false) {
				_targetPc.sendPackets(new S_SkillSound(_target.getId(), 13418));// ����Ʈ
			}
		} else 
			
			if (_calcType == NPC_NPC) {
			_isHit = calcNpcNpcHit();
			if (_isHit == false) {
		//	_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 13418));// ����Ʈ
			}
		}
		return _isHit;
	}

	// �ܡܡܡ� �÷��̾�κ��� �÷��̾�� ���� ���� �ܡܡܡ�
	/*
	 * PC���� ������ =(PC�� Lv��Ŭ���� ������STR ������DEX ���������� ������DAI�� �ż�/2������ ����)��0.68��10
	 * �̰����� ����� ��ġ�� �ڽ��� �ִ� ����(95%)�� �ִ� ���� �� �� �ִ� ����� PC�� AC �ű�κ��� ����� PC�� AC��
	 * 1������ ������ �ڸ������κ��� 1��� ���� �ּ� ������5% �ִ� ������95%
	 */
	private boolean calcPcPcHit() {

		_hitRate = _pc.getLevel();
	     /** ��Ʋ�� **/
        if (_calcType == PC_PC) {
            if (_pc.getMapId() == 5153) {
                if (_pc.get_DuelLine() == _targetPc.get_DuelLine()) {
                    return false;
                }
            }
        }
		if (_weaponType != 20 && _weaponType != 62) {
			/*
			 * if (_pc.getAbility().getTotalStr() > 59) _hitRate +=
			 * (strHit[58]); else _hitRate +=
			 * (strHit[_pc.getAbility().getTotalStr()-1]);
			 */
			_hitRate += CalcStat.�ٰŸ�����(_pc.getAbility().getTotalStr()) * 2;
			_hitRate += (_weaponAddHit * 3) + _pc.getHitup()
					+ (_pc.getHitupByArmor() * 2) + (_weaponEnchant / 2);

		} else {
			/*
			 * if (_pc.getAbility().getTotalDex() > 60) _hitRate +=
			 * (dexHit[59]); else _hitRate +=
			 * (dexHit[_pc.getAbility().getTotalDex()-1]);
			 */
			_hitRate += CalcStat.���Ÿ�����(_pc.getAbility().getTotalDex()) * 2;
			_hitRate += (_weaponAddHit * 3) + _pc.getBowHitup()
					+ (_pc.getBowHitupByArmor() * 2) + _pc.getBowHitupByDoll()
					+ (_weaponEnchant / 2);
		}

		if (_pc.isWarrior()) {
			_hitRate += 10;
		}

		// if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MIRROR_IMAGE))
		// _hitRate -= 8;

		int attackerDice = _random.nextInt(20) + 1 + _hitRate - 10;

		int defenderDice = 0;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEAR)) {
			attackerDice += 5;
		}

		int defenderValue = (int) (_targetPc.getAC().getAc() * 1.5) * -1;
		int levelDmg = (int) ((_targetPc.getLevel() - _pc.getLevel()) * 2.0);
		if (levelDmg <= 0)
			levelDmg = 0;

		defenderValue += levelDmg;
		

		
	
		if (_targetPc.getAC().getAc() >= 0)
			defenderDice = 10 - _targetPc.getAC().getAc();
		else if (_targetPc.getAC().getAc() < 0) {
			defenderDice = defenderValue;
			// defenderDice = 10 + _random.nextInt(defenderValue) + 1;
			int ac = _targetPc.getAC().getAc();
			if (ac <= -170)
				defenderDice += Config.AC_170;
			else if (ac <= -160)
				defenderDice += Config.AC_160;
			else if (ac <= -150)
				defenderDice += Config.AC_150;
			else if (ac <= -140)
				defenderDice += Config.AC_140;
			else if (ac <= -130)
				defenderDice += Config.AC_130;
			else if (ac <= -120)
				defenderDice += Config.AC_120;
			else if (ac <= -110)
				defenderDice += Config.AC_110;
			else if (ac <= -100)
				defenderDice += Config.AC_100;
			else if (ac <= -90)
				defenderDice += Config.AC_90;
			else if (ac <= -80)
				defenderDice += Config.AC_80;
			else if (ac <= -70)
				defenderDice += Config.AC_70;
			else if (ac <= -60)
				defenderDice += Config.AC_60;
			else if (ac <= -50)
				defenderDice += Config.AC_50;
			else if (ac <= -40)
				defenderDice += Config.AC_40;
			else if (ac <= -30)
				defenderDice += Config.AC_30;
			else if (ac <= -20)
				defenderDice += Config.AC_20;
			else if (ac <= -10)
				defenderDice += Config.AC_10;
		}

		if (_weaponType == 20 || _weaponType == 62) {
			defenderDice += (_targetPc.get_PlusEr() * 3);
		}

		// _pc.sendPackets(new
		// S_SystemMessage("pc_pc ���� ����Ʈ: "+attackerDice+" �������Ʈ : "+defenderDice));

		double ����̽�Ȯ�� = 2;

		double �������ݼ���Ȯ�� = 0;

		/*
		 * if (attackerDice > defenderDice){
		 * 
		 * 
		 * 
		 * }else
		 */if (attackerDice <= defenderDice) {
			 double temp = ((defenderDice - attackerDice) * 0.1);

			 if (temp < 1) {
				 temp = 1;
			 }

			 ����̽�Ȯ�� += temp;
			 // _pc.sendPackets(new
			 // S_SystemMessage("[�������Ʈ ����] �������Ʈ�̽�Ȯ�� : "+temp+"%"));
			 /*
			  * if(_random.nextInt(100) >= temp){ _pc.sendPackets(new
			  * S_SystemMessage("[�������Ʈ ����] ���� ���� Ȯ�� : "+(100-temp)+"% (������)"));
			  * �������ݼ���Ȯ�� = 100-����̽�Ȯ��; }else{ _pc.sendPackets(new
			  * S_SystemMessage("[�������Ʈ ����] ���� �̽� Ȯ�� : "+temp+"% (�̽���)"));
			  * _hitRate = 0; }
			  */
		 }
		 if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MIRROR_IMAGE)) {
			 if (_targetPc.getAC().getAc() >= -69) {
				 ����̽�Ȯ�� += 5; // 3
			 } else if (_targetPc.getAC().getAc() >= -79) {
				 ����̽�Ȯ�� += 7; // 5
			 } else if (_targetPc.getAC().getAc() >= -89) {
				 ����̽�Ȯ�� += 9; //7
			 } else if (_targetPc.getAC().getAc() >= -99) {
				 ����̽�Ȯ�� += 12; // 10
			 } else if (_targetPc.getAC().getAc() <= -100) {
				 ����̽�Ȯ�� += 17; // 15
			 }
		 }
		 if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(UNCANNY_DODGE)) {
			 if (_targetPc.getAC().getAc() >= -69) {
				 ����̽�Ȯ�� += 2; //3
			 } else if (_targetPc.getAC().getAc() >= -79) {
				 ����̽�Ȯ�� += 4; //5
			 } else if (_targetPc.getAC().getAc() >= -89) {
				 ����̽�Ȯ�� += 6; // 7
			 } else if (_targetPc.getAC().getAc() >= -99) {
				 ����̽�Ȯ�� += 9; //10
			 } else if (_targetPc.getAC().getAc() <= -100) {
				 ����̽�Ȯ�� += 14; //15
			 }
		 }

		 if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
				 L1SkillId.ANTA_MAAN) // ������ ���� - ���� ����Ȯ�� ȸ��
				 || _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						 L1SkillId.BIRTH_MAAN) // ź���� ���� - ���� ����Ȯ�� ȸ��
						 || _targetPc.getSkillEffectTimerSet().hasSkillEffect(
								 L1SkillId.SHAPE_MAAN) // ������ ���� - ���� ����Ȯ�� ȸ��
								 || _targetPc.getSkillEffectTimerSet().hasSkillEffect(
										 L1SkillId.LIFE_MAAN)) { // ������ ���� - ���� ����Ȯ�� ȸ��
			 int MaanHitRnd = _random.nextInt(100) + 1;
			 if (MaanHitRnd <= 10) { // Ȯ��
				 ����̽�Ȯ�� += 10;
			 }
		 }

		 �������ݼ���Ȯ�� = 100 - ����̽�Ȯ��;

		 // _pc.sendPackets(new S_SystemMessage("���� ���� ���� Ȯ�� : "+�������ݼ���Ȯ��+"%"));

		 if (_random.nextInt(100) + 1 < ����̽�Ȯ��) {
			 �������ݼ���Ȯ�� = 0;
			 // _pc.sendPackets(new
			 // S_SystemMessage("[���� ����] ����+����̽�Ȯ��+�������Ʈ�̽�Ȯ�� : "+����̽�Ȯ��+"% (�̽���)"));
		 }

		 if (�������ݼ���Ȯ�� > 0) {
			 if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
					 ABSOLUTE_BARRIER)
					 || _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							 ICE_LANCE)
							 || _targetPc.getSkillEffectTimerSet().hasSkillEffect(
									 FREEZING_BREATH)
									 || _targetPc.getSkillEffectTimerSet().hasSkillEffect(
											 L1SkillId.STATUS_�������)) {
				 �������ݼ���Ȯ�� = 0;
			 }
		 }

		 int rnd = _random.nextInt(100) + 1;

		 /** ���Ÿ����� ��� */

		 int _jX = _pc.getX() - _targetPc.getX();
		 int _jY = _pc.getY() - _targetPc.getY();
		 if (!(_pc instanceof L1RobotInstance)) {
			 if (_weaponType == 24) { // â�϶�
				 if ((_jX > 3 || _jX < -3) && (_jY > 3 || _jY < -3)) {
					 �������ݼ���Ȯ�� = 0;
				 }
			 } else if (_weaponType == 20 || _weaponType == 62) {// Ȱ�϶�
				 if ((_jX > 15 || _jX < -15) && (_jY > 15 || _jY < -15)) {
					 �������ݼ���Ȯ�� = 0;
				 }
			 } else {
				 if ((_jX > 2 || _jX < -2) && (_jY > 2 || _jY < -2)) {
					 �������ݼ���Ȯ�� = 0;
				 }
			 }
		 }
		 /** ���Ÿ����� ��� */

		 return �������ݼ���Ȯ�� >= rnd;
	}

	// �ܡܡܡ� �÷��̾�κ��� NPC ���� ���� ���� �ܡܡܡ�
	private boolean calcPcNpcHit() {
		try {

			// NPC���� ������
			// =(PC�� Lv��Ŭ���� ������STR ������DEX ���������� ������DAI�� �ż�/2������ ����)��5��{NPC��
			// AC��(-5)}
			_hitRate = _pc.getLevel();

			/*
			 * if (_weaponType != 20 && _weaponType != 62) { _hitRate +=
			 * CalcStat.�ٰŸ�����(_pc.getAbility().getTotalStr())*2; _hitRate +=
			 * (_weaponAddHit*3) + _pc.getHitup() + (_pc.getHitupByArmor()*2) +
			 * (_weaponEnchant/2);
			 * 
			 * } else { _hitRate +=
			 * CalcStat.���Ÿ�����(_pc.getAbility().getTotalDex())*2; _hitRate +=
			 * (_weaponAddHit*3) + _pc.getBowHitup() +
			 * (_pc.getBowHitupByArmor()*2) + _pc.getBowHitupByDoll() +
			 * (_weaponEnchant/2); }
			 * 
			 * if(_pc.isWarrior()){ _hitRate += 10; }
			 */

			if (_weaponType != 20 && _weaponType != 62) {
				_hitRate += CalcStat.�ٰŸ�����(_pc.getAbility().getTotalStr()) * 2;
				_hitRate += (_weaponAddHit * 3) + _pc.getHitup()
						+ (_pc.getHitupByArmor() * 2) + (_weaponEnchant / 2);
			} else {
				_hitRate += CalcStat.���Ÿ�����(_pc.getAbility().getTotalDex()) * 2;
				_hitRate += (_weaponAddHit * 3) + _pc.getBowHitup()
						+ (_pc.getBowHitupByArmor() * 2)
						+ _pc.getBowHitupByDoll() + (_weaponEnchant / 2);
			}

			if (_pc.isWarrior()) {
				_hitRate += 10;
			}

			int attackerDice = _random.nextInt(20) + 1 + _hitRate - 10;

			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(FEAR))
				attackerDice += 5;

			int defenderDice = 0;

			int defenderValue = (int) (_targetNpc.getAC().getAc() * 3.2) * -1;

			if (_targetNpc.getAC().getAc() >= 0)
				defenderDice = 10 - _targetNpc.getAC().getAc();
			else if (_targetNpc.getAC().getAc() < 0) {
				defenderDice = defenderValue;
				// defenderDice = 10 + _random.nextInt(defenderValue) + 1;
				int ac = _targetNpc.getAC().getAc();
				if (ac <= -170)
					defenderDice += Config.AC_170;
				else if (ac <= -160)
					defenderDice += Config.AC_160;
				else if (ac <= -150)
					defenderDice += Config.AC_150;
				else if (ac <= -140)
					defenderDice += Config.AC_140;
				else if (ac <= -130)
					defenderDice += Config.AC_130;
				else if (ac <= -120)
					defenderDice += Config.AC_120;
				else if (ac <= -110)
					defenderDice += Config.AC_110;
				else if (ac <= -100)
					defenderDice += Config.AC_100;
				else if (ac <= -90)
					defenderDice += Config.AC_90;
				else if (ac <= -80)
					defenderDice += Config.AC_80;
				else if (ac <= -70)
					defenderDice += Config.AC_70;
				else if (ac <= -60)
					defenderDice += Config.AC_60;
				else if (ac <= -50)
					defenderDice += Config.AC_50;
				else if (ac <= -40)
					defenderDice += Config.AC_40;
				else if (ac <= -30)
					defenderDice += Config.AC_30;
				else if (ac <= -20)
					defenderDice += Config.AC_20;
				else if (ac <= -10)
					defenderDice += Config.AC_10;
			}

			// _pc.sendPackets(new
			// S_SystemMessage("pc_npc ���� ����Ʈ: "+attackerDice+" �������Ʈ : "+defenderDice));

			double ����̽�Ȯ�� = 2;
			if (_targetNpc.getSkillEffectTimerSet()
					.hasSkillEffect(MIRROR_IMAGE)) {
				if (_targetNpc.getAC().getAc() >= -69) {
					����̽�Ȯ�� += 3;
				} else if (_targetNpc.getAC().getAc() >= -79) {
					����̽�Ȯ�� += 5;
				} else if (_targetNpc.getAC().getAc() >= -89) {
					����̽�Ȯ�� += 7;
				} else if (_targetNpc.getAC().getAc() >= -99) {
					����̽�Ȯ�� += 10;
				} else if (_targetNpc.getAC().getAc() <= -100) {
					����̽�Ȯ�� += 15;
				}
			}
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(
					UNCANNY_DODGE)) {
				if (_targetNpc.getAC().getAc() >= -69) {
					����̽�Ȯ�� += 2; //3
				} else if (_targetNpc.getAC().getAc() >= -79) {
					����̽�Ȯ�� += 4; //5
				} else if (_targetNpc.getAC().getAc() >= -89) {
					����̽�Ȯ�� += 6; //6
				} else if (_targetNpc.getAC().getAc() >= -99) {
					����̽�Ȯ�� += 9; //10
				} else if (_targetNpc.getAC().getAc() <= -100) {
					����̽�Ȯ�� += 14; //15
				}
			}

			double �������ݼ���Ȯ�� = 0;

			/*
			 * if (attackerDice > defenderDice){
			 * 
			 * 
			 * 
			 * }else
			 */if (attackerDice <= defenderDice) {
				 double temp = ((defenderDice - attackerDice) * 0.1);

				 if (temp < 1) {
					 temp = 1;
				 }

				 ����̽�Ȯ�� += temp;
				 // _pc.sendPackets(new
				 // S_SystemMessage("[�������Ʈ ����] �������Ʈ�̽�Ȯ�� : "+temp+"%"));
				 /*
				  * if(_random.nextInt(100) >= temp){ _pc.sendPackets(new
				  * S_SystemMessage
				  * ("[�������Ʈ ����] ���� ���� Ȯ�� : "+(100-temp)+"% (������)")); �������ݼ���Ȯ�� =
				  * 100-����̽�Ȯ��; }else{ _pc.sendPackets(new
				  * S_SystemMessage("[�������Ʈ ����] ���� �̽� Ȯ�� : "+temp+"% (�̽���)"));
				  * _hitRate = 0; }
				  */
			 }

			 �������ݼ���Ȯ�� = 100 - ����̽�Ȯ��;

			 // _pc.sendPackets(new
			 // S_SystemMessage("���� ���� ���� Ȯ�� : "+�������ݼ���Ȯ��+"%"));

			 if (_random.nextInt(100) + 1 < ����̽�Ȯ��) {
				 �������ݼ���Ȯ�� = 0;
				 // _pc.sendPackets(new
				 // S_SystemMessage("[���� ����] ����+�ּҹ̽�Ȯ��+�������Ʈ�̽�Ȯ�� : "+����̽�Ȯ��+"% (�̽���)"));
			 }

			 /*
			  * int fumble = _hitRate - 9; int critical = _hitRate + 10;
			  * 
			  * 
			  * if (attackerDice <= fumble) { _pc.sendPackets(new
			  * S_SystemMessage("���� Ȯ�� �� ���� 100% �̽�!")); _hitRate = 0; } else if
			  * (attackerDice >= critical) { _pc.sendPackets(new
			  * S_SystemMessage("���� Ȯ�� �� ���� 100% ����!")); _hitRate = 100; } else {
			  * if (attackerDice > defenderDice){ _pc.sendPackets(new
			  * S_SystemMessage("���� ��갪 > ����갪 = 100% ���ݼ���")); _hitRate = 100;
			  * }else if (attackerDice <= defenderDice){ _pc.sendPackets(new
			  * S_SystemMessage("���� ��갪 < ����갪 = 100% ���ݹ̽�")); _hitRate = 0; } }
			  */

			 if (�������ݼ���Ȯ�� > 0) {
				 int npcId = _targetNpc.getNpcTemplate().get_npcId();
				 if (npcId >= 45912
						 && npcId <= 45915
						 && !_pc.getSkillEffectTimerSet().hasSkillEffect(
								 STATUS_HOLY_WATER)) {
					 �������ݼ���Ȯ�� = 0;
				 } else if (npcId == 45916
						 && !_pc.getSkillEffectTimerSet().hasSkillEffect(
								 STATUS_HOLY_MITHRIL_POWDER)) {
					 �������ݼ���Ȯ�� = 0;
				 } else if (npcId == 45941
						 && !_pc.getSkillEffectTimerSet().hasSkillEffect(
								 STATUS_HOLY_WATER_OF_EVA)) {
					 �������ݼ���Ȯ�� = 0;
				 } else if (!_pc.getSkillEffectTimerSet().hasSkillEffect(
						 STATUS_CURSE_BARLOG)
						 && (npcId == 45752 || npcId == 45753
						 || npcId == 7000026 || npcId == 7000027
						 || npcId == 7000012 || npcId == 7000019
						 || npcId == 7000006 || npcId == 7000013
						 || npcId == 7000020 || npcId == 7000007
						 || npcId == 7000014 || npcId == 7000021
						 || npcId == 7000030 || npcId == 7000031
						 || npcId == 7000032 || npcId == 7000033
						 || npcId == 7000034 || npcId == 7000035
						 || npcId == 7000036 || npcId == 7000037
						 || npcId == 7000038 || npcId == 7000039
						 || npcId == 7000040 || npcId == 7000041
						 || npcId == 7000008 || npcId == 7000015
						 || npcId == 7000022 || npcId == 7000009
						 || npcId == 7000016 || npcId == 7000023
						 || npcId == 7000010 || npcId == 7000017
						 || npcId == 7000024 || npcId == 7000028
						 || npcId == 7000029 || npcId == 7000030
						 || npcId == 7000011 || npcId == 7000018
						 || npcId == 7000025 || npcId == 7000042)) {// �߷�
					 �������ݼ���Ȯ�� = 0;
				 } else if (!_pc.getSkillEffectTimerSet().hasSkillEffect(
						 STATUS_CURSE_YAHEE)
						 && (npcId == 45675 || npcId == 81082 || npcId == 45625
						 || npcId == 45674 || npcId == 100570)) {
					 �������ݼ���Ȯ�� = 0;
				 } else if (npcId >= 46068 && npcId <= 46091
						 && _pc.getGfxId().getTempCharGfx() == 6035) {
					 �������ݼ���Ȯ�� = 0;
				 } else if (npcId >= 46092 && npcId <= 46106
						 && _pc.getGfxId().getTempCharGfx() == 6034) {
					 �������ݼ���Ȯ�� = 0;
				 }

				 /** ���Ÿ����� ��� */
				 int _jX = _pc.getX() - _targetNpc.getX();
				 int _jY = _pc.getY() - _targetNpc.getY();
				 if (!(_pc instanceof L1RobotInstance)) {
					 if (_weaponType == 24) { // â�϶�
						 if ((_jX > 3 || _jX < -3) && (_jY > 3 || _jY < -3)) {
							 �������ݼ���Ȯ�� = 0;
						 }
					 } else if (_weaponType == 20 || _weaponType == 62) { // Ȱ�϶�
						 if ((_jX > 20 || _jX < -20) && (_jY > 20 || _jY < -20)) {
							 �������ݼ���Ȯ�� = 0;
						 }
					 } else {
						 if ((_jX > 2 || _jX < -2) && (_jY > 2 || _jY < -2)) {
							 �������ݼ���Ȯ�� = 0;
						 }
					 }
				 }
			 }
			 /** ���Ÿ����� ��� */
			 int rnd = _random.nextInt(100) + 1;

			 return �������ݼ���Ȯ�� >= rnd;
		} catch (Exception e) {
			System.out.println("���ݼ��� ����� ���� >> " + _pc.getName());
			e.printStackTrace();
		}
		return false;
	}

	// �ܡܡܡ� NPC �κ��� �÷��̾�� ���� ���� �ܡܡܡ�
	private boolean calcNpcPcHit() {
		_hitRate += _npc.getLevel();

		if (_npc instanceof L1PetInstance) {
			_hitRate += ((L1PetInstance) _npc).getHitByWeapon();
		}

		_hitRate += _npc.getHitup();

		int attackerDice = _random.nextInt(20) + 1 + _hitRate - 1;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(UNCANNY_DODGE)) {
			if (_targetPc.getAC().getAc() >= -89) {
				attackerDice -= 5;
			} else if (_targetPc.getAC().getAc() >= -99) {
				attackerDice -= 6;
			} else if (_targetPc.getAC().getAc() < -99) {
				attackerDice -= 7;
			}
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.ANTA_MAAN) // ������ ���� - ���� ����Ȯ�� ȸ��
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.BIRTH_MAAN) // ź���� ���� - ���� ����Ȯ�� ȸ��
						|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.SHAPE_MAAN) // ������ ���� - ���� ����Ȯ�� ȸ��
								|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
										L1SkillId.LIFE_MAAN)) { // ������ ���� - ���� ����Ȯ�� ȸ��
			int MaanHitRnd = _random.nextInt(100) + 1;
			if (MaanHitRnd <= 3) { // Ȯ��
				_hitRate = 0;
			}
		}
		if (_pc != null) {
			if (_pc.getInventory().checkEquipped(420112)
					|| _pc.getInventory().checkEquipped(420113)
					|| _pc.getInventory().checkEquipped(420114)
					|| _pc.getInventory().checkEquipped(420115)) { // �߶�ī���� 3��
				// ������
				int chance = _random.nextInt(100);
				if (chance <= 5) {
					// weaponDamage = 10;
					// _pc.sendPackets(new S_SkillSound(_pc.getId(), 2185));
					// _pc.broadcastPacket(new S_SkillSound(_pc.getId(), 2185));
				}
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEAR)) {
			attackerDice += 5;
		}
		int defenderDice = 0;

		int defenderValue = (_targetPc.getAC().getAc()) * -1;

		if (_targetPc.getAC().getAc() >= 0) {
			defenderDice = 10 - _targetPc.getAC().getAc();
		} else if (_targetPc.getAC().getAc() < 0) {
			defenderDice = 10 + _random.nextInt(defenderValue) + 1;
		}
		int fumble = _hitRate;
		int critical = _hitRate + 19;

		if (attackerDice <= fumble) {
			_hitRate = 0;
		} else if (attackerDice >= critical) {
			_hitRate = 100;
		} else {
			if (attackerDice > defenderDice) {
				_hitRate = 100;
			} else if (attackerDice <= defenderDice) {
				_hitRate = 0;
			}
		}
		
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.STATUS_�������)) {
			_hitRate = 0;
		} else if (_npc.getNpcId() >= 100584 && _npc.getNpcId() <= 100589) {
			_hitRate = 100;
			return true;
		}

		int rnd = _random.nextInt(100) + 1;
		// NPC�� ���� �������� 10�̻��� ����, 2�̻� ������ �ִ� ���Ȱ�������� �����Ѵ�
		if (_npc.getNpcTemplate().get_ranged() >= 10
				&& _hitRate > rnd
				&& _npc.getLocation().getTileLineDistance(
						new Point(_targetX, _targetY)) >= 3) {
			return calcErEvasion();
		}
		if (_hitRate <= 100) {
			if (_npc.getMapId() >= 550 && _npc.getMapId() <= 558) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 90 ? 100 : 0;
			} else if (_npc.getNpcId() == 100081) {
				_hitRate *= 0.75;
			} else if (_npc.getNpcId() >= 100055 && _npc.getNpcId() <= 100058) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getNpcId() >= 100059 && _npc.getNpcId() <= 100060) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getNpcId() == 45295 || _npc.getNpcId() == 45318
					|| _npc.getNpcId() == 45337 || _npc.getNpcId() == 45351) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 90 ? 100 : 0;
			} else if (_npc.getMapId() >= 307 && _npc.getMapId() <= 309) { // ħ����
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 90 ? 100 : 0;
			} else if (_npc.getMapId() >= 59 && _npc.getMapId() <= 63) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getMapId() == 440) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getMapId() == 4
					&& ((_npc.getX() >= 33472 && _npc.getX() <= 33856
					&& _npc.getY() >= 32191 && _npc.getY() <= 32511) || (_npc
							.getX() >= 32511
							&& _npc.getX() <= 32960
							&& _npc.getY() >= 32191 && _npc.getY() <= 32537))) {
				if (_hitRate == 0)
					_hitRate = _random.nextInt(100) > 85 ? 100 : 0;
			} else if (_npc.getMapId() >= 30 && _npc.getMapId() <= 36) {
				if (_npc.getNpcTemplate().get_undead() == 1)
					_hitRate *= 0.55;
				else
					_hitRate *= 0.70;
			} else if (_npc.getMapId() >= 1 && _npc.getMapId() <= 2) {
				if (_npc.getNpcTemplate().get_undead() == 1)
					_hitRate *= 0.80;
			} else {
				_hitRate *= 0.80;
			}
		}
		return _hitRate >= rnd;
	}

	// �ܡܡܡ� NPC �κ��� NPC ���� ���� ���� �ܡܡܡ�
	private boolean calcNpcNpcHit() {
		int target_ac = 10 - _targetNpc.getAC().getAc();
		int attacker_lvl = _npc.getNpcTemplate().get_level();

		if (target_ac != 0) {
			_hitRate = (100 / target_ac * attacker_lvl); // �ǰ����� AC = ������ Lv
			// �� �� ������ 100%
		} else {
			_hitRate = 100 / 1 * attacker_lvl;
		}

		if (_npc instanceof L1PetInstance) { // ���� LV1���� �߰� ����+2
			_hitRate += _npc.getLevel() * 2;
			_hitRate += ((L1PetInstance) _npc).getHitByWeapon();
		}

		if (_hitRate < attacker_lvl) {
			_hitRate = attacker_lvl; // ���� ������=L����
		}
		if (_hitRate > 95) {
			_hitRate = 95; // �ְ� �������� 95%
		}
		if (_hitRate < 5) {
			_hitRate = 5; // ������ Lv�� 5 �̸����� ������ 5%
		}

		int rnd = _random.nextInt(100) + 1;
		return _hitRate >= rnd;
	}

	// �ܡܡܡ� ER�� ���� ȸ�� ���� �ܡܡܡ�
	private boolean calcErEvasion() {
		int er = _targetPc.get_PlusEr();

		int rnd = _random.nextInt(130) + 1;
		return er < rnd;
	}

	/* ���������������� ������ ���� ���������������� */
	public int calcDamage(int adddmg) {
		switch (_calcType) {
		case PC_PC:
			_damage = calcPcPcDamage(adddmg);
			break;
		case PC_NPC:
			_damage = calcPcNpcDamage(adddmg);
			break;
		case NPC_PC:
			_damage = calcNpcPcDamage();
			break;
		case NPC_NPC:
			_damage = calcNpcNpcDamage();
			break;
		default:
			break;
		}
		return _damage;
	}

	public int calcDamage() {
		switch (_calcType) {
		case PC_PC:
			_damage = calcPcPcDamage(0);
			break;
		case PC_NPC:
			_damage = calcPcNpcDamage(0);
			break;
		case NPC_PC:
			_damage = calcNpcPcDamage();
			break;
		case NPC_NPC:
			_damage = calcNpcNpcDamage();
			break;
		default:
			break;
		}
		return _damage;
	}

	boolean burning = false;
	boolean double_burning = false;

	// �ܡܡܡ� �÷��̾�κ��� �÷��̾�� ������ ���� �ܡܡܡ�
	public int calcPcPcDamage(int adddmg) {
		if (_pc instanceof L1RobotInstance) {
			 if(!_targetPc.isRobot()){
				if (_pc.getCurrentWeapon() == 20){ //Ȱ
					return _random.nextInt(70) + 40;
				} else {
					return _random.nextInt(80) + 30;
				}
			  } else {
				  return 50;
			  }
			
			}
		int weaponMaxDamage = 0;

		weaponMaxDamage = _weaponSmall + _weaponAddDmg;
		boolean secondw = false;
		if (_pc.isWarrior() && _pc.isSlayer && _pc.getSecondWeapon() != null) {
			int ran = _random.nextInt(100);
			if (ran < 50) {
				secondw = true;
				weaponMaxDamage = _SweaponSmall + _SweaponAddDmg;
			}
		}
		/*
		 * double warr = (_weaponSmall + _weaponAddDmg + _SweaponSmall +
		 * _SweaponAddDmg) * 0.7; weaponMaxDamage = (int)warr;
		 */
		// }else{

		// }

		int weaponDamage = 0;

		int doubleChance = _random.nextInt(120) + 1;

		if (_target != null) {
			int chance1 = _random.nextInt(100);
			for (L1ItemInstance item : _targetPc.getInventory().getItems()) {
				if (item.isEquipped()) {
					if (item.getItemId() >= 420104
							&& item.getItemId() <= 420107) {
						int addchan = 5;/* item.getEnchantLevel()-5; */
						if (addchan < 0)
							addchan = 0;
						if (chance1 < /* 6+addchan */5) {
							// 123456 �϶� 80~100
							// ��Ǫ ��ȣ 7�϶� 120~140 / 8�϶� 140~160 9�϶� 160~180
							int addhp = _random.nextInt(20) + 1;
							int basehp = 80;
							if (item.getEnchantLevel() == 7)
								basehp = 120;
							if (item.getEnchantLevel() == 8)
								basehp = 140;
							if (item.getEnchantLevel() == 9)
								basehp = 160;
							_targetPc.setCurrentHp(_targetPc.getCurrentHp()
									+ basehp + addhp);
							_targetPc.sendPackets(
									new S_SkillSound(_targetPc.getId(), 2187),
									true);
							Broadcaster.broadcastPacket(_targetPc,
									new S_SkillSound(_targetPc.getId(), 2187),
									true);
						}
						break;
					} else if (item.getItemId() >= 420108
							&& item.getItemId() <= 420111) {
						int addchan = item.getEnchantLevel() - 6;
						if (addchan < 0)
							addchan = 0;
						if (chance1 < 3 + addchan) {
							if (_targetPc.getSkillEffectTimerSet()
									.hasSkillEffect(L1SkillId.���尡ȣ������)) {
								break;
							}
							_targetPc.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.���尡ȣ������, 4000);

							if (item.getItemId() == 420108)// �Ϸ�
								_targetPc.setCurrentMp(_targetPc.getCurrentMp()
										+ 6 + _random.nextInt(5));
							else if (item.getItemId() == 420109)// ����
								_targetPc.setCurrentMp(_targetPc.getCurrentMp()
										+ 7 + _random.nextInt(6));
							else if (item.getItemId() == 420110)// �γ�
								_targetPc.setCurrentMp(_targetPc.getCurrentMp()
										+ 8 + _random.nextInt(7));
							else if (item.getItemId() == 420111)// ����
								_targetPc.setCurrentMp(_targetPc.getCurrentMp()
										+ 10 + _random.nextInt(8));

							_targetPc.sendPackets(
									new S_SkillSound(_targetPc.getId(), 2188),
									true);
							Broadcaster.broadcastPacket(_targetPc,
									new S_SkillSound(_targetPc.getId(), 2188),
									true);
						}
						break;
					} else if (item.getItemId() == 21255) {
						if (chance1 < 4) {
							_targetPc
							.setCurrentHp(_targetPc.getCurrentHp() + 31);
							_targetPc.sendPackets(
									new S_SkillSound(_targetPc.getId(), 2183),
									true);
							Broadcaster.broadcastPacket(_targetPc,
									new S_SkillSound(_targetPc.getId(), 2183),
									true);
						}
						break;
					}
				}
				// �ż��� �������Ǳݰ���
				int chance66 = _random.nextInt(100) + 1;
				if (_target != null) {
					int dmg2 = 0;
					int plus = 0;
					if (_targetPc.getInventory().checkEquipped(222351)) {
						if (chance66 <= 6) { // ���� 5��
							if (_targetPc.getSkillEffectTimerSet()
									.hasSkillEffect(L1SkillId.POLLUTE_WATER)) {
								dmg2 += (25 + _random.nextInt(15) + (plus * 10)) / 2; //
							}
							if (_targetPc.getSkillEffectTimerSet()
									.hasSkillEffect(L1SkillId.WATER_LIFE)) {
								dmg2 += (25 + _random.nextInt(15) + (plus * 10)) * 2; //
							}
							dmg2 += 25 + _random.nextInt(15) + (plus * 10); //
							_targetPc.setCurrentHp(_targetPc.getCurrentHp() + dmg2);
							_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 15355));
							_targetPc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 15355));
						}
					}
				}
				/*
				 * int chance1 = _random.nextInt(100) + 1; if (chance1 <4 &&
				 * (_targetPc.getInventory().checkEquipped(420104) ||
				 * _targetPc.getInventory().checkEquipped(420105) ||
				 * _targetPc.getInventory().checkEquipped(420106) ||
				 * _targetPc.getInventory().checkEquipped(420107))) { // int
				 * chance2 = _random.nextInt(20) + 1;
				 * _targetPc.setCurrentHp(_targetPc.getCurrentHp() +
				 * 50+chance2); _targetPc.sendPackets(new
				 * S_SkillSound(_targetPc.getId() , 2187), true);
				 * Broadcaster.broadcastPacket(_targetPc, new
				 * S_SkillSound(_targetPc.getId(), 2187), true); }
				 */
			}
		}
		
		// ��Ǫ��ȣ

		int weaponTotalDamage = 0;
		double dmg = 0;

		if (_weaponId == 90083) { // ��ȿ���̵���
			_weaponDoubleDmgChance += _weaponEnchant + 1;
		}

		if (_weaponType == 0) { // �Ǽ�
			weaponDamage = 0;
		} else { // �Ǽ��� �ƴҶ�
			if (_weaponType == 58 && doubleChance <= _weaponDoubleDmgChance) { // ũ�ο�
				// ����
				weaponDamage = weaponMaxDamage;
				weaponDamage += weaponDamage * 0.2;
				_attackType = 2;
			} else {
				weaponDamage = _random.nextInt(weaponMaxDamage + 2) + 1;
			}

			if (weaponDamage > weaponMaxDamage) {
				_ũ��Ƽ�� = true;
			}
			// //

			int ġ�� = _random.nextInt(100) + 1;
			int ġ��Ȯ�� = 0;
			if (_weaponType == 20 || _weaponType == 62) {
				ġ��Ȯ�� = CalcStat.���Ÿ�ġ��Ÿ(_pc.getAbility().getTotalDex());
			} else {
				ġ��Ȯ�� = CalcStat.�ٰŸ�ġ��Ÿ(_pc.getAbility().getTotalStr());
			}

			if (ġ�� <= ġ��Ȯ��) {
				_ũ��Ƽ�� = true;
				weaponDamage = weaponMaxDamage;
			}

			if (_pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME)) {
				if (_weaponType != 20 && _weaponType != 62) {
					weaponDamage = weaponMaxDamage /* + 20 */;
					_ũ��Ƽ�� = false;
				}
			}
	        if (_pc.getInventory().checkEquipped(203025) || _pc.getInventory().checkEquipped(203026)) { //�ο�ȥ (���ο�)
				    if (_weaponEnchant >= 10 && _weaponEnchant < 12) { 
				    int chance = _random.nextInt(100);
				    if (chance <= 10) {
				      _damage += 15;
				      _pc.sendPackets(new S_SkillSound(_targetPc.getId(),8032), true);
				      Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 8032), true);
				    }
				   }
				  }
				   
				   if (_pc.getInventory().checkEquipped(7227)) { //���� (��ǳ�ǵ���)
				    if (_weaponEnchant >= 10 && _weaponEnchant < 12) { 
				    int chance = _random.nextInt(100);
				    if (chance <= 10) {
				      _damage += 9;
				      _pc.sendPackets(new S_SkillSound(_targetPc.getId(),7977), true);
				      Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 7977), true);
				    }
				   }
				  }
				   if (_pc.getInventory().checkEquipped(293)) { //�Ǹ� (�Ǹ������)
					    if (_weaponEnchant >= 10 && _weaponEnchant < 12) { 
					    int chance = _random.nextInt(100);
					    if (chance <= 10) {
					      _damage += 2;
					      _pc.sendPackets(new S_SkillSound(_targetPc.getId(),13541), true);
					      Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 13541), true);
					    }
					   }
					  }
					   
					   if (_pc.getInventory().checkEquipped(90084)) { //���� (������ ü�μҵ�)
					    if (_weaponEnchant >= 10 && _weaponEnchant < 12) { 
					    int chance = _random.nextInt(100);
					    if (chance <= 10) {
					      _damage += 17;
					      _pc.sendPackets(new S_SkillSound(_targetPc.getId(),13987), true);
					      Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetPc.getId(), 13987), true);
					    }
					   }
					  }
					   
			if (secondw) {
				weaponTotalDamage = weaponDamage + _SweaponEnchant;
			} else {
				weaponTotalDamage = weaponDamage + _weaponEnchant;
			}

			if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.����¡���Ǹ���)) {
				if (_weaponType == 54 ) { // �̵���
					// ����
					weaponTotalDamage *= 2.4;
					if (_weaponId != 415011) {
						weaponTotalDamage *= 1.1;
					}
					_attackType = 4;
					_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 14547) ,true);
					_pc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 14547) ,true);
				}
			}else{
				if (_weaponType == 54 && doubleChance <= _weaponDoubleDmgChance) { // �̵���
					// ����
					weaponTotalDamage *= 2.4;
					if (_weaponId != 415011) {
						weaponTotalDamage *= 1.1;
					}
					_attackType = 4;
					if (_pc.��ؽ� && _pc.����¡) {
							int timeb = 3;
							if(_pc.getLevel() == 86)
								timeb +=1;
							if(_pc.getLevel() >= 87)
								timeb +=1;
							_pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.����¡���Ǹ���, timeb * 1000);
							_pc.sendPackets(new S_CreateItem(L1SkillId.����¡���Ǹ���, timeb));	
							_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 14547));
							_pc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 14547));
					}
					_pc.��ؽ� = false;
					_pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ؽ�);		
					
				}
			}
			if (_pc.isCrash) {
				int rnd = _random.nextInt(100);
				if (rnd < 25) {
					int crashdmg = _pc.getLevel() / 2;
					double purrydmg = crashdmg * 2;
					int plusdmg = crashdmg;
					int gfx = 12487;
					if (_pc.isPurry) {
						int rnd2 = _random.nextInt(100);
						if (rnd2 < 15) {
							gfx = 12489;
							plusdmg = (int) purrydmg;
						}
					}
					weaponTotalDamage += plusdmg;
					_pc.sendPackets(new S_SkillSound(_target.getId(), gfx));// 12489
					Broadcaster.broadcastPacket(_target, new S_SkillSound(
							_target.getId(), gfx));
				}
			}
			/*
			 * double enchatDmgRate = 1; try { if (_weaponEnchant < 7){ }else if
			 * (_weaponEnchant == 7 && (_random.nextInt(100) + 1) <=
			 * Config.RATE_7_DMG_PER) { // ��þƮ 7�϶� �������� 5% enchatDmgRate =
			 * Config.RATE_7_DMG_RATE; }else if (_weaponEnchant == 8 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_8_DMG_PER) { // ��þƮ 8�϶�
			 * �������� 10% enchatDmgRate = Config.RATE_8_DMG_RATE; }else if
			 * (_weaponEnchant == 9 && (_random.nextInt(100) + 1) <=
			 * Config.RATE_9_DMG_PER) { // ��þƮ 9�϶� enchatDmgRate =
			 * Config.RATE_9_DMG_RATE; }else if (_weaponEnchant == 10 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_10_DMG_PER) { // ��þƮ
			 * 10�϶� enchatDmgRate = Config.RATE_10_DMG_RATE; }else if
			 * (_weaponEnchant == 11 && (_random.nextInt(100) + 1) <=
			 * Config.RATE_11_DMG_PER) { // ��þƮ 11�϶� enchatDmgRate =
			 * Config.RATE_11_DMG_RATE; }else if (_weaponEnchant == 12 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_12_DMG_PER) { // ��þƮ
			 * 12�϶� enchatDmgRate = Config.RATE_12_DMG_RATE; }else if
			 * (_weaponEnchant == 13 && (_random.nextInt(100) + 1) <=
			 * Config.RATE_13_DMG_PER) { // ��þƮ 13�϶� enchatDmgRate =
			 * Config.RATE_13_DMG_RATE; }else if (_weaponEnchant == 14 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_14_DMG_PER) { // ��þƮ
			 * 14�϶� enchatDmgRate = Config.RATE_14_DMG_RATE; }else if
			 * (_weaponEnchant == 15 && (_random.nextInt(100) + 1) <=
			 * Config.RATE_15_DMG_PER) { // ��þƮ 15�϶� enchatDmgRate =
			 * Config.RATE_15_DMG_RATE; }else if (_weaponEnchant == 16 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_16_DMG_PER) { // ��þƮ
			 * 15�϶� enchatDmgRate = Config.RATE_16_DMG_RATE; }else if
			 * (_weaponEnchant == 17 && (_random.nextInt(100) + 1) <=
			 * Config.RATE_17_DMG_PER) { // ��þƮ 17�϶� enchatDmgRate =
			 * Config.RATE_17_DMG_RATE; }else if (_weaponEnchant == 18 &&
			 * (_random.nextInt(100) + 1) <= Config.RATE_18_DMG_PER) { // ��þƮ
			 * 18�϶� enchatDmgRate = Config.RATE_18_DMG_RATE; } } catch
			 * (Exception e) { }
			 */
			if (secondw) {
				weaponTotalDamage += calcSAttrEnchantDmg();
			} else {
				weaponTotalDamage += calcAttrEnchantDmg();
			}

			/*
			 * if (_weaponId == 61){ weaponTotalDamage += _weaponEnchant*2;
			 * }else
			 */if (_pc.getSkillEffectTimerSet().hasSkillEffect(DOUBLE_BRAKE)
					 && (_weaponType == 54 || _weaponType == 58)) {
				 int rnd = 20;
				 if (_pc.getLevel() >= 90) {
					 rnd += 9;
				 } else if (_pc.getLevel() >= 85) {
					 rnd += 8;
				 } else if (_pc.getLevel() >= 80) {
					 rnd += 7;
				 } else if (_pc.getLevel() >= 75) {
					 rnd += 6;
				 } else if (_pc.getLevel() >= 70) {
					 rnd += 5;
				 } else if (_pc.getLevel() >= 65) {
					 rnd += 4;
				 } else if (_pc.getLevel() >= 60) {
					 rnd += 3;
				 } else if (_pc.getLevel() >= 55) {
					 rnd += 2;
				 } else if (_pc.getLevel() >= 50) {
					 rnd += 1;
				 } else if (_pc.getLevel() >= 45) {
					 rnd += 0;
				 }
				 if ((_random.nextInt(100) + 1) <= rnd) {
					 weaponTotalDamage *= 2;

					 double_burning = true;
				 }
			 }

			 dmg = weaponTotalDamage + _statusDamage;

			 if (_weaponType != 20 && _weaponType != 62) {
				 dmg += _pc.getDmgup() + _pc.getDmgupByArmor();
			 } else {
				 dmg += _pc.getBowDmgup() + _pc.getBowDmgupByArmor()
						 + _pc.getBowDmgupByDoll();
			 }

			 if (_weaponType == 20) { // Ȱ
				 if (_arrow != null) {
					 int add_dmg = _arrow.getItem().getDmgSmall();
					 if (add_dmg == 0) {
						 add_dmg = 1;
					 }
					 dmg += _random.nextInt(add_dmg) + 5;
				 } else if (_weaponId == 190 || _weaponId == 100190 || _weaponId == 450029 || _weaponId == 30082
						 || (_weaponId >= 11011 && _weaponId <= 11013)) { // ��������Ȱ
					 dmg += _random.nextInt(2) + 1;
				 } else if (_weaponId == 7201){
					 dmg += 10;
				 }
			 } else if (_weaponType == 62) { // �� ���� ��
				 int add_dmg = _sting.getItem().getDmgSmall();
				 if (add_dmg == 0) {
					 add_dmg = 1;
				 }
				 dmg = dmg + _random.nextInt(add_dmg) + 1;
			 }

			 dmg = calcBuffDamage(dmg);

			 /*
			  * if(_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SPIRIT)){
			  * _pc.sendPackets(new S_SkillSound(_targetPc.getId(), 7065), true);
			  * Broadcaster.broadcastPacket(_pc, new
			  * S_SkillSound(_targetPc.getId(), 7065), true); }
			  */
		}

		try {
			  dmg += WeaponAddDamage.getInstance().getWeaponAddDamage(_weaponId);
	

		} catch (Exception e) {
			System.out.println("Weapon Add Damege Error");
		} 
		
		if (_weaponType == 0) { // �Ǽ�
			dmg = (_random.nextInt(5) + 4) / 4;
		} else if (_weaponType == 46) {
			dmg += 2;
		} else if (_weaponType == 50) {
			dmg += 5;
		}
		if (_weaponType != 0) { // �Ǽ��� �ƴҶ�
			if (_weaponType1 == 17) {
				int Ű��ũ�������Ʈ = _pc.getAbility().getTotalInt();
				int Ű��ũ��������� = _pc.getAbility().getSp();
				int Ű��ũ�ִ� = Ű��ũ�������Ʈ + Ű��ũ���������;
				int Ű��ũ���� = (_random.nextInt(Ű��ũ�������Ʈ) + 1)
						+ (_random.nextInt(Ű��ũ���������) + 1);
				/*
				 * if(_target.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.
				 * ERASE_MAGIC)){
				 * _target.getSkillEffectTimerSet().removeSkillEffect
				 * (L1SkillId.ERASE_MAGIC); }
				 */
				dmg += Ű��ũ����;

				dmg = calcMrDefense(_target.getResistance()
						.getEffectedMrBySkill(), dmg);

				if (Ű��ũ���� >= Ű��ũ�ִ�) {
					_ũ��Ƽ�� = true;
				}

			}
			
			
			
			if (_weaponType1 == 18) {
				dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId);
			}
			
		
			
			
			if (secondw) {
				switch (_SweaponId) {
				case 90085:
				case 90086:
				case 90087:
				case 90088:
				case 90089:
				case 90090:
				case 90091:
				case 90093:
				case 90094:
				case 90095:
				case 90096:
				case 90097:
				case 90098:
				case 90099:
				case 90100:
                case 110051:
                case 110052:
                case 110053:
                case 110054:
                case 110055:
                case 110056:
                case 110057:
                case 110058:
					
					WeaponSkill.�������ũ(_pc, _targetPc, _weaponEnchant);
					
					break;
				case 7236:
				case 7237:
				case 30084:
					WeaponSkill.getDeathKnightjin(_pc, _targetPc);
					break;
				case 2:
				case 200002:
					dmg = WeaponSkill.getDiceDaggerDamage(_pc, _targetPc,
							weapon);
					return (int) dmg; // break;
				case 13:
				case 44:
					WeaponSkill.getPoisonSword(_pc, _targetPc);
					break;
				case 100047:
				case 47:
				case 450031:
					
					WeaponSkill.getSilenceSword(_pc, _targetPc, _weaponEnchant);
					break;
				case 134:
				case 30086:
				case 222204:
					dmg += WeaponSkill.get������Damage(_pc, _target,
							_weaponEnchant);
					break;
				case 294: // ���������ǰ� 123551
					WeaponSkill.AkdukSword(_pc, _target, 60);
					//   dmg += getEbHP(_pc, _target, 8981, _weaponEnchant);
					break;
				   case 30081:
				   case 31081:
				   case 222207:
				        dmg += WeaponSkill.���丮��������(this._pc, this._target, 12248, this._weaponEnchant);
				        break;
				case 54:
					dmg += WeaponSkill.getKurtSwordDamage(_pc, _targetPc,
							_weaponEnchant);
					break;
				case 58:
					dmg += WeaponSkill.getDeathKnightSwordDamage(_pc,
							_targetPc, _weaponEnchant);
					break;
					// case 59: dmg += WeaponSkill.getBarlogSwordDamage(_pc,
					// _target, _weaponEnchant); break;
				case 76:
					dmg += WeaponSkill.getRondeDamage(_pc, _targetPc,
							_weaponEnchant);
					break;
				case 121:
					dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 124:
				case 100124:
					dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 9080:
				case 191:// ��õ��Ȱ
					// WeaponSkill.giveSalCheonEffect(_pc, _target);
					// DrainofEvil(); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 9361);
					break;
					// case 114: WeaponSkill.getGunjuSword(_pc,_target
					// ,_weaponEnchant); break; //�߰�
				case 203:
					dmg += WeaponSkill.getBarlogSwordDamage(_pc, _target);
					break;
				case 86: // ����
				case 204: // ��ȫ�� ũ�ν�����
				case 100204:
				case 30087:
				case 222203:
					WeaponSkill.giveFettersEffect(_pc, _targetPc);
					break;

				case 205:
				case 100205:
					dmg += WeaponSkill.getMoonBowDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 264:
				case 265:
				case 256:
				case 4500027:
					dmg += WeaponSkill
					.getEffectSwordDamage(_pc, _target, 11107);
					break;
				case 9079:
				case 412000: // dmg += WeaponSkill.getEffectSwordDamage(_pc,
					// _target, 10); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3940);
					break;
					/*
					 * case 410000: case 410001: case 450009: case 450014: case
					 * 450004: dmg += WeaponSkill.getChainSwordDamage(_pc, _target);
					 * break;
					 */
				case 412004: // dmg += WeaponSkill.getIceSpearDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3704);
					break;
				case 7228:
				case 9075:
				case 412005: // dmg += WeaponSkill.geTornadoAxeDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 5524);
					break;
					// case 412003: WeaponSkill.getDiseaseWeapon(_pc, _target,
					// 412003); break;
				case 413101:
				case 413102:
				case 413104:
				case 413105:
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 413103:
					calcStaffOfMana();
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 415010:
				case 415011:
				case 415012:
				case 415013:
					WeaponSkill.ü�̼�(_pc, _target, _weaponEnchant, 0);
					break;

				case 415015:
				case 415016:
					WeaponSkill.ü�̼�(_pc, _target, _weaponEnchant, 1);
					break;
				case 6000:
					dmg += WeaponSkill.IceChainSword(_pc, _target,
							_weaponEnchant);
					break;
				case 6001:
					dmg += WeaponSkill.Icekiring(_pc, _target, _weaponEnchant);
					break;

				case 450008:
				case 450022:
				case 450024:
				case 450010:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
					break;

				case 450011:
				case 450012:
				case 450013:
				case 450023:
				case 450025:
				case 30082://���̾�
				case 31082://���̾�
				case 222206://���̾�
					WeaponSkill.�̺�Ʈ��(_pc, _target, _weaponEnchant);
					break;
			
			
			    case 30080: 
			    case 31080:
			    case 222205:
			    	dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId);
			    	break;
				
				case 263:
				case 4500028: // dmg += WeaponSkill.halloweenCus(_pc, _target);
					// break;
				case 4500026:
					dmg += WeaponSkill.halloweenCus2(_pc, _target);
					break;// ����
					// case 263:
					// case 4500028: dmg += WeaponSkill.halloweenCus(_pc, _target);
					// break;
				case 100259:
				case 100260:
				case 9081:
				case 259:
				case 260:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 9359);
					break; // �ı��� ũ�ο�, �̵���
				case 9077:
				case 266:
				case 100266:
					dmg += WeaponSkill.PhantomShock(_pc, _target,
							_weaponEnchant);
					break; // ������ Ű��ũ
				case 9076:
				case 275:
				case 100275:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 7398);
					break; // ȯ���� ü�μҵ�
				case 277:
				case 278:
				case 279:
				case 280:
				case 281:
				case 282:
				case 283:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
					break; // ���� ���� ����
				case 284:
				case 285:
				case 286:
				case 287:
				case 288:
				case 289:
				case 290:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3939);
					break; // ȯ���� ���� (10�� �̺�Ʈ)
				case 291: // ���ν���������
					dmg += WeaponSkill.getZerosDamage(_pc, _target,
							_weaponEnchant);
					break;
		        case 90084:
		            WeaponSkill.��������ü�μҵ�(_pc);
				default:
					break;
				}
			} else {
				switch (_weaponId) {
				case 90085:
				case 90086:
				case 90087:
				case 90088:
				case 90089:
				case 90090:
				case 90091:
				case 90093:
				case 90094:
				case 90095:
				case 90096:
				case 90097:
				case 90098:
				case 90099:
				case 90100:
		        case 110051:
                case 110052:
                case 110053:
                case 110054:
                case 110055:
                case 110056:
                case 110057:
                case 110058:
					WeaponSkill.�������ũ(_pc, _targetPc, _weaponEnchant);
					break;
				case 7236:
				case 7237:
				case 30084:
					WeaponSkill.getDeathKnightjin(_pc, _targetPc);
					break;
					
				case 2:
				case 200002:
					dmg = WeaponSkill.getDiceDaggerDamage(_pc, _targetPc,
							weapon);
					return (int) dmg; // break;
				case 13:
				case 44:
					WeaponSkill.getPoisonSword(_pc, _targetPc);
					break;
				case 100047:
				case 47:
				case 450031:
					WeaponSkill.getSilenceSword(_pc, _targetPc, _weaponEnchant);
					break;
				case 134:
				case 30086:
				case 222204:
					dmg += WeaponSkill.get������Damage(_pc, _target,
							_weaponEnchant);

					break;
				case 294: // ���������ǰ� 123551
					WeaponSkill.AkdukSword(_pc, _target, 60);
					//   dmg += getEbHP(_pc, _target, 8981, _weaponEnchant);
					break;
				   case 30081:
				   case 31081:
				   case 222207:
				        dmg += WeaponSkill.���丮��������(this._pc, this._target, 12248, this._weaponEnchant);
				        break;
				case 54:
					dmg += WeaponSkill.getKurtSwordDamage(_pc, _targetPc,
							_weaponEnchant);
					break;
				case 58:
					dmg += WeaponSkill.getDeathKnightSwordDamage(_pc,
							_targetPc, _weaponEnchant);
					break;
					// case 59: dmg += WeaponSkill.getBarlogSwordDamage(_pc,
					// _target, _weaponEnchant); break;
				case 76:
					dmg += WeaponSkill.getRondeDamage(_pc, _targetPc,
							_weaponEnchant);
					break;
				case 121:
					dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 124:
				case 100124:
					dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 9080:
				case 191:// ��õ��Ȱ
					// WeaponSkill.giveSalCheonEffect(_pc, _target);
					// DrainofEvil(); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 9361);
					break;
					// case 114: WeaponSkill.getGunjuSword(_pc,_target
					// ,_weaponEnchant); break; //�߰�
				case 203:
					dmg += WeaponSkill.getBarlogSwordDamage(_pc, _target);
					break;
				case 86: // ����
				case 204: // ��ȫ�� ũ�ν�����
				case 100204:
				case 30087:
				case 222203:
					WeaponSkill.giveFettersEffect(_pc, _targetPc);
					break;
				case 205:
				case 100205:
					dmg += WeaponSkill.getMoonBowDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 264:
				case 265:
				case 256:
				case 4500027:
					dmg += WeaponSkill
					.getEffectSwordDamage(_pc, _target, 11107);
					break;
				case 9079:
				case 412000: // dmg += WeaponSkill.getEffectSwordDamage(_pc,
					// _target, 10); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3940);
					break;
					/*
					 * case 410000: case 410001: case 450009: case 450014: case
					 * 450004: dmg += WeaponSkill.getChainSwordDamage(_pc, _target);
					 * break;
					 */
				case 412004: // dmg += WeaponSkill.getIceSpearDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3704);
					break;
				case 7228:
				case 9075:
				case 412005: // dmg += WeaponSkill.geTornadoAxeDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 5524);
					break;
					// case 412003: WeaponSkill.getDiseaseWeapon(_pc, _target,
					// 412003); break;
				case 413101:
				case 413102:
				case 413104:
				case 413105:
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 413103:
					calcStaffOfMana();
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 415010:
				case 415011:
				case 415012:
				case 415013:
					WeaponSkill.ü�̼�(_pc, _target, _weaponEnchant, 0);
					break;
				case 415015:
				case 415016:
					WeaponSkill.ü�̼�(_pc, _target, _weaponEnchant, 1);
					break;
				case 6000:
					dmg += WeaponSkill.IceChainSword(_pc, _target,
							_weaponEnchant);
					break;
				case 6001:
					dmg += WeaponSkill.Icekiring(_pc, _target, _weaponEnchant);
					break;

				case 450008:
				case 450022:
				case 450024:
				case 450010:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
					break;

				case 450011:
				case 450012:
				case 450013:
				case 450023:
				case 450025:
				case 30082: //���̾�
				case 31082://���̾�
				case 222206://���̾�
					WeaponSkill.�̺�Ʈ��(_pc, _target, _weaponEnchant);
					break;
					  //ũ�γ뽺�ǰ���
			    case 30080: 
			    case 31080:
			    case 222205:
			    	dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId);
			    	break;
				   case 3000081:
					   dmg += WeaponSkill.Icekiring11(_pc, _target, _weaponEnchant);
				        break;
				case 263:
				case 4500028: // dmg += WeaponSkill.halloweenCus(_pc, _target);
					// break;
				case 4500026:
					dmg += WeaponSkill.halloweenCus2(_pc, _target);
					break;// ����
					// case 263:
					// case 4500028: dmg += WeaponSkill.halloweenCus(_pc, _target);
					// break;
				case 100259:
				case 100260:
				case 9081:
				case 259:
				case 260:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 9359);
					break; // �ı��� ũ�ο�, �̵���
				case 9077:
				case 266:
				case 100266:
					dmg += WeaponSkill.PhantomShock(_pc, _target,
							_weaponEnchant);
					break; // ������ Ű��ũ
				case 9076:
				case 275:
				case 100275:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 7398);
					break; // ȯ���� ü�μҵ�
				case 277:
				case 278:
				case 279:
				case 280:
				case 281:
				case 282:
				case 283:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
					break; // ���� ���� ����
				case 284:
				case 285:
				case 286:
				case 287:
				case 288:
				case 289:
				case 290:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3939);
					break; // ȯ���� ���� (10�� �̺�Ʈ)
				case 291: // ���ν���������
					dmg += WeaponSkill.getZerosDamage(_pc, _target,
							_weaponEnchant);
					break;
				default:
					break;
				}
			}
			if (_weaponId == 450009) {
				dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId);
				WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
			}
			if (secondw) {
				if (_SweaponId == 7233) {
					WeaponSkill.�̺�����(_pc, _target, _SweaponEnchant);
				}
			} else {
				if (_weaponId == 7233) {
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
				}
			}
		}

		// �๫�� ������ �߰�
		
		  if(weapon != null){ if(weapon.getBless() == 0){ dmg += 1; } }
		
		/*
		 * if(_weaponId >= 90085 && _weaponId <= 90092){ dmg += _weaponEnchant;
		 * }
		 */
		// ���� ������ ���� (PVP ������)
		if (_weaponId >= 277 && _weaponId <= 283) {
			dmg += _weaponEnchant;
		}
		// ȯ���� ���� (10�� �̺�Ʈ)
		/*
		 * if(_weaponId >= 284 && _weaponId <= 290){ if(_weaponEnchant == 7) dmg
		 * += 3; else if(_weaponEnchant == 8) dmg += 5; else if(_weaponEnchant
		 * == 9) dmg += 7; else if(_weaponEnchant == 10) dmg += 10;
		 * 
		 * dmg *= 0.92; }
		 */
		// ���� ������ ���� (PVP ����������)
		for (int i = 21242; i <= 21245; i++) {
			L1ItemInstance PVP�� = _targetPc.getInventory().checkEquippedItem(
					i);
			if (PVP�� != null) {
				dmg -= PVP��.getEnchantLevel() + 1;
				break;
			}
		}

		if (_calcType == PC_PC) {
			if( _pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_3) ||
					_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_4)) {
				dmg += 2;
			}
			if(_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_2) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_3) ||
					_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_4)) {
				dmg -= 2;
			}
			if(_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�����ǰ�ȣ))
				dmg -= 8;
		}
		/*
		 * if(_weaponId >= 90085 && _weaponId <= 90092){ dmg += _weaponEnchant;
		 * }
		 */
		if (_weaponId >= 277 && _weaponId <= 283) {
			dmg += _weaponEnchant;
		}

		if (_weaponId == 9078 || _weaponId == 276 || _weaponId == 100276 || // ��������Ǵ��
				_weaponId == 292 || _weaponId == 293) {// ������ũ�ο�, �Ǹ������
			if (_pc.getLawful() <= -20000)
				dmg += 5;
			else if (_pc.getLawful() <= -10000)
				dmg += 3;
			else if (_pc.getLawful() <= 0)
				dmg += 1;
			/*
			 * double lawful = 32767 - _pc.getLawful(); lawful *= 0.0000037;//��ī
			 * 24%���� ���� 0% System.out.println(_pc.getLawful()); dmg += dmg *
			 * lawful;
			 */
		}
		if (_pc.getDollList().size() > 0) {
			for (L1DollInstance doll : _pc.getDollList()) {
				if (_weaponType != 20 && _weaponType != 62) {
					int d = doll.getDamageByDoll(_targetPc);
					if (d > 0
							&& doll.getDollType() == L1DollInstance.DOLLTYPE_�׷���)
						_pc.setCurrentHp(_pc.getCurrentHp()
								+ _random.nextInt(5) + 1);
					dmg += d;
				}

				if (doll.getDollType() == L1DollInstance.DOLLTYPE_�����
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_��������Ʈ) {
					dmg += doll.getMagicDamageByDoll(_targetPc);
				}

				doll.attackPoisonDamage(_pc, _targetPc);
			}
		}

		if (adddmg != 0)
			dmg += adddmg;

		/** �������� ���� **/
		if (_targetPc.getDollList().size() > 0 && _weaponType != 20
				&& _weaponType != 62) {
			for (L1DollInstance doll : _targetPc.getDollList()) {
				if (doll.getDollType() == L1DollInstance.DOLLTYPE_STONEGOLEM
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_�巹��ũ)
					dmg -= doll.getDamageReductionByDoll();
			}
		}
		/** �������� ���� **/
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SLASH)) {
			dmg += 50;
			_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 6591), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(
					_targetPc.getId(), 6591), true);
			_pc.getSkillEffectTimerSet().killSkillEffectTimer(BURNING_SLASH);
		}

		if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�ֱ��ǹ���)) {
			if (_pc.getClanRank() >= L1Clan.CLAN_RANK_GUARDIAN)
				dmg += 30;
		}
		
		dmg += ��Ƽ���˱��߰�������();

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg -= (dmg*Config.�õ�����);
			
		
		}

		if (_targetPc.isAmorGaurd) { // �ƸӰ��忡���� ����������
			int d = _targetPc.getAC().getAc() / 10;

			if (d < 0) {
				dmg += d;
			} else {
				dmg -= d;
			}
		}

		// if(_weaponType1 != 17) // Ű��ũ �ƴҶ���
		dmg -= _targetPc.getDamageReductionByArmor(); // ���� �ⱸ�� ���� ������ �氨

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)) { // ����ȿ丮��
			// ����
			// ������
			// �氨
			dmg -= 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(HEUKSA)) {
			dmg -= 3;
		}
		
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING2)) {
			dmg -= 5;
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.EARTH_BLESS))
			dmg -= 2;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_�߰��)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						COOKING_NEW_����)
						|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
								COOKING_NEW_ĥ����)
								|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
										COOKING_NEW_�ѿ�)) {
			dmg -= 2;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(��Ƽ����������)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(���̽ÿ�������))
			dmg -= 5;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(��Ƽ�������丮)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(���̸����Ѷ��))
			dmg -= 5;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(��Ƽ���ູ�ֹ���)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.����Ǳ��))
			dmg -= 3;

		dmg -= ��Ƽ���ӱ͵���������();

		// Ű��ũ �ƴҶ��� �߰�
		if (_weaponType1 != 17
				&& _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			int dmg2 = (targetPcLvl - 40) / 3 - 3;
			dmg -= dmg2 > 0 ? dmg2 : 0;// +1
		}

		/*
		 * //���ž �尩�� ����� if (_targetPc.getInventory().checkEquipped(20173) ||
		 * _targetPc.getInventory().checkEquipped(21103) ||
		 * _targetPc.getInventory().checkEquipped(21108)) dmg *= 1.05;
		 */
		if (_target != _targetNpc) {
			L1ItemInstance �ݿ����ǹ��� = _targetPc.getInventory().checkEquippedItem(
					21093);
			if (�ݿ����ǹ��� != null) {
				int chance = 5 + (�ݿ����ǹ���.getEnchantLevel() * 2);
				if (_random.nextInt(100) <= chance) {
					dmg -= 70;
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(),
							6320));
					Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(
							_targetPc.getId(), 6320));
				}
			}

			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ARMOR_BREAK)){//�ƸӺ극��ũ
				if (_weaponType != 20 || _weaponType != 62) {
					dmg *= 1.45;
				}
			}
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(DRAGON_SKIN)) {
				dmg -= 2;
			}
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IllUSION_AVATAR)) {
			dmg += dmg / 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(DRAGON_SKIN)) {
			dmg -= 3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(PATIENCE)) {
			dmg -= 4;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_A)) {
			dmg -= 3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_B)) {
			dmg -= 2;
		}

		if (_pc.getSkillEffectTimerSet().hasSkillEffect(VALA_MAAN) // ȭ���� ���� -
				// ����Ȯ����
				// �����߰�Ÿ��+2
				|| _pc.getSkillEffectTimerSet().hasSkillEffect(LIFE_MAAN)) { // ������
			// ���� -
			// ����Ȯ����
			// �����߰�Ÿ��+2
			int MaanAttDmg = _random.nextInt(100) + 1;
			if (MaanAttDmg <= 30) { // Ȯ��
				dmg += 2;
			}
		}
		if (_pc != null) {
			int chance = _random.nextInt(100);
			if (chance <= 5) {
				if (_pc.getInventory().checkEquipped(420112)
						|| _pc.getInventory().checkEquipped(420113)
						|| _pc.getInventory().checkEquipped(420114)
						|| _pc.getInventory().checkEquipped(420115)) { // �߶�ī����
					// 3��
					// ������
					weaponDamage += 10;
					// _pc.sendPackets(new S_SkillSound(_pc.getId(), 2185));
					// _pc.broadcastPacket(new S_SkillSound(_pc.getId(), 2185));
				}
			}
		}

		if (_weaponId == 22) { // ���Ϻ극��Ŀ
			dmg -= dmg * 0.30;
		}

		if (_weaponId == 450009) { // ü�μҵ�
			dmg -= dmg * 0.25;
		}

		if (_weaponId == 7227 || _weaponId == 7225) { // ü�μҵ�
			dmg += dmg * 0.20;
		}

		if (_weaponId == 410000 || _weaponId == 510000) {// �Ҹ����� ü�μҵ�
			dmg -= dmg * 0.30;
		}
		if (_weaponId == 7229) {// ��ö����
			dmg -= dmg * 0.10;
		}
		if (_targetPc != null) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.TRUE_TARGET)) {
				if (_pc != null) {
					if (_targetPc.tt_clanid == _pc.getClanid()
							|| _targetPc.tt_partyid == _pc.getPartyID()) {
						if (_targetPc.tt_level >= 90) {
							dmg += dmg * 0.06;
						} else if (_targetPc.tt_level >= 75) {
							dmg += dmg * 0.05;
						} else if (_targetPc.tt_level >= 60) {
							dmg += dmg * 0.04;
						} else if (_targetPc.tt_level >= 45) {
							dmg += dmg * 0.03;
						} else if (_targetPc.tt_level >= 30) {
							dmg += dmg * 0.02;
						} else if (_targetPc.tt_level >= 15) {
							dmg += dmg * 0.01;
						}
					}
				}
			}
		}

		if (_targetPc.getInventory().checkEquipped(21104)
				|| _targetPc.getInventory().checkEquipped(21111)) {
			// dmg += dmg*0.2;
			if (Config.�����ڰ��ʹ뷱����ġ != 0) {
				double balance = Config.�����ڰ��ʹ뷱����ġ * 0.01;
				dmg += dmg * balance;// ������ ���� 35%����
			}
		}

		if (_weaponId == 7 || _weaponId == 35 || _weaponId == 48
				|| _weaponId == 73 || _weaponId == 105 || _weaponId == 120
				|| _weaponId == 147 || _weaponId == 156 || _weaponId == 174
				|| _weaponId == 175 || _weaponId == 224 || _weaponId == 7232) {
			if (Config.�����ڹ���뷱����ġ != 0) {
				double balance = Config.�����ڹ���뷱����ġ * 0.01;
				dmg -= dmg * balance;// ������ ���� 35%����
			}
		}

		// dmg -= dmg*0.05;///Ŭ������ü 10%����


		if(_pc.isKnight()){ dmg += dmg*0.1; }

		if (dmg <= 0) {
			_isHit = false;
			_drainHp = 0;
		}
		// ����� ���� �ٲٱ����� �ű�.
		if (_weaponId == 262) { // ���弭Ŀ
			if (dmg >= 30 && _random.nextInt(100) <= 90) {
				// _pc.sendPackets(new
				// S_SystemMessage("���� :"+_drainHp+" ������ :"+dmg));
				int _dhp = (int) ((dmg - 10) / 10);
				_drainHp += _dhp;// +_random.nextInt(3)+1;
			}
		}
		
		if(dmg > 0 && _pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��Ʈ����) ){
			int c = _pc.getLevel()-80;
			if(c >5) //�ִ�
				c=5;
			if(c <3)
				c=3; //�ּ�
			if(CommonUtil.random(100) <= c){
				L1ItemInstance item = _targetPc.getEquipSlot().gettypeArmor();
				int maxc = item.get_durability();int ac = item.getItem().get_ac();
				if(ac < 0) ac = Math.abs(ac);
				if(maxc < ac){
					if (item != null) {
							_targetPc.sendPackets(new S_ServerMessage(268, item.getLogName()));
							_targetPc.getInventory().receiveDamageArmor(_targetPc ,item);			
							_pc.sendPackets(new S_SkillSound(_targetPc.getId(), 14549));
							_pc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 14549));
						}
				}
			}
		}

		return (int) dmg;
	}

	private double ��Ƽ���˱��߰�������() {
		int dmg = 0;
		if(_calcType == PC_PC || _calcType == PC_NPC) {
			L1ItemInstance blackRumti = _pc.getInventory().checkEquippedItem(500010);
			if(blackRumti == null)
				blackRumti = _pc.getInventory().checkEquippedItem(502010);
			if(blackRumti != null) {
				int chance = 0;
				if(blackRumti.getBless() == 0 && blackRumti.getEnchantLevel() >= 4) {
					chance = 2 + blackRumti.getEnchantLevel() - 4;
				}
				else if(blackRumti.getEnchantLevel() >= 5) {
					chance = 2 + blackRumti.getEnchantLevel() - 5;
				}
				if(chance != 0) {
					if(_random.nextInt(100) < chance) {
						dmg += 20;
						_pc.sendPackets(new S_SkillSound(_pc.getId(), 13931));
						_pc.broadcastPacket(new S_SkillSound(_pc.getId(), 13931));
					}
				}
			}
		}
		return dmg;
	}

	private int calcSAttrEnchantDmg() {
		if (_SweaponAttrEnchantLevel <= 0)
			return 0;
		int dmg = 0;
		/** �Ӽ���æƮ �߰� Ÿ��ġ */
		switch (_SweaponAttrEnchantLevel) {
		case 1:
		case 4:
		case 7:
		case 10:
			dmg = 1;
			break;
		case 2:
		case 5:
		case 8:
		case 11:
			dmg = 3;
			break;
		case 3:
		case 6:
		case 9:
		case 12:
			dmg = 5;
			break;
		case 16:
		case 18:
		case 20:
		case 22:
			dmg = 7;
			break;
		case 17:
		case 19:
		case 21:
		case 23:
			dmg = 9;
			break;

		case 33:
		case 35:
		case 37:
		case 39:
			dmg = 7;
			break;
		case 34:
		case 36:
		case 38:
		case 40:
			dmg = 9;
			break;

		case 13:// Ưȭ ��ȭ�ֹ���
			dmg = 6;// ��Ÿ6
			_hitRate += 2;// ����2
			
			break;// 1�ܰ�
		case 14:
			dmg = 8;
			_hitRate += 2;//
			break;
		case 15:
			dmg = 10;
			_hitRate += 2;// 3�ܰ�
			break;
		default:
			dmg = 0;
			break;
		}
		int attr = 0;
		switch (_SweaponAttrEnchantLevel) {
		case 1:
		case 2:
		case 3:
		case 13:
		case 14:
		case 33:
		case 34:
			attr = 2;
			break;
		case 4:
		case 5:
		case 6:
		case 15:
		case 16:
		case 35:
		case 36:
			attr = 4;
			break;
		case 7:
		case 8:
		case 9:
		case 17:
		case 18:
		case 37:
		case 38:
			attr = 8;
			break;
		case 10:
		case 11:
		case 12:
		case 19:
		case 20:
		case 39:
		case 40:
			attr = 1;
			break;
		default:
			break;
		}
		dmg -= dmg * calcAttrResistance(attr);
		return dmg;

	}

	private int calcAttrEnchantDmg() {
		if (_weaponAttrEnchantLevel <= 0)
			return 0;
		int dmg = 0;
		/** �Ӽ���æƮ �߰� Ÿ��ġ */

		switch (_weaponAttrEnchantLevel) {
		case 1:
		case 4:
		case 7:
		case 10:
			dmg = 1;
			break;
		case 2:
		case 5:
		case 8:
		case 11:
			dmg = 3;
			break;
		case 3:
		case 6:
		case 9:
		case 12:
			dmg = 5;
			break;
		case 16:
		case 18:
		case 20:
		case 22:
			dmg = 7;
			break;
		case 17:
		case 19:
		case 21:
		case 23:
			dmg = 9;
			break;

		case 33:
		case 35:
		case 37:
		case 39:
			dmg = 7;
			break;
		case 34:
		case 36:
		case 38:
		case 40:
			dmg = 9;
			break;

		case 13:// Ưȭ ��ȭ�ֹ���
			dmg = 6;// ��Ÿ6
			_hitRate += 2;// ����2
			break;// 1�ܰ�
		case 14:
			dmg = 8;
			_hitRate += 2;//
			break;
		case 15:
			dmg = 10;
			_hitRate += 2;// 3�ܰ�
			break;
		default:
			dmg = 0;
			break;
		}

		int attr = 0;
		switch (_weaponAttrEnchantLevel) {
		case 1:
		case 2:
		case 3:
		case 13:
		case 14:
		case 33:
		case 34:
			attr = 2;
			break;
		case 4:
		case 5:
		case 6:
		case 15:
		case 16:
		case 35:
		case 36:
			attr = 4;
			break;
		case 7:
		case 8:
		case 9:
		case 17:
		case 18:
		case 37:
		case 38:
			attr = 8;
			break;
		case 10:
		case 11:
		case 12:
		case 19:
		case 20:
		case 39:
		case 40:
			attr = 1;
			break;
		default:
			break;
		}

		dmg -= dmg * calcAttrResistance(attr);
		return dmg;

	}

	private int addattrdmg(int attr) {
		int adddmg = 0;
		int orgattr = 0;
		int npc_att = _targetNpc.getNpcTemplate().get_weakAttr();
		if (npc_att == 0)
			return 0;
		switch (attr) {
		case 1:
			orgattr = 4;
			break;// ��
		case 2:
			orgattr = 8;
			break;// ǳ
		case 3:
			orgattr = 1;
			break;// ��
		case 4:
			orgattr = 2;
			break;// ȭ
		default:
			break;
		}

		if (orgattr == npc_att) {
			adddmg = 3;
		}

		return adddmg;
	}

	private double calcAttrResistance(int attr) {
		int resist = 0;
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			switch (attr) {
			case L1Skills.ATTR_EARTH:
				resist = _targetPc.getResistance().getEarth();
				break;
			case L1Skills.ATTR_FIRE:
				resist = _targetPc.getResistance().getFire();
				break;
			case L1Skills.ATTR_WATER:
				resist = _targetPc.getResistance().getWater();
				break;
			case L1Skills.ATTR_WIND:
				resist = _targetPc.getResistance().getWind();
				break;
			default:
				break;
			}
		} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
			// ���Ӽ� �̿� �ϰ�� �Ӽ������� �ȵ���
			int npc_att = _targetNpc.getNpcTemplate().get_weakAttr();
			if (npc_att == 0)
				return 0;
			if (npc_att >= 8) {
				npc_att -= 8;
				if (attr == 8)
					return 0;
			}
			if (npc_att >= 4) {
				npc_att -= 4;
				if (attr == 4)
					return 0;
			}
			if (npc_att >= 2) {
				npc_att -= 2;
				if (attr == 2)
					return 0;
			}
			if (npc_att >= 1) {
				npc_att -= 1;
				if (attr == 1)
					return 0;
			}
			return 1;
		}

		/*
		 * int resistFloor = (int) (0.32 * Math.abs(resist)); if (resist >= 0) {
		 * resistFloor *= 1; } else { resistFloor *= -1; }
		 * 
		 * double attrDeffence = resistFloor / 32.0;
		 */

		double attrDeffence = resist / 5 * 0.01;

		return attrDeffence;
	}

	// �ܡܡܡ� �÷��̾�κ��� NPC ���� ������ ���� �ܡܡܡ�
	private int calcPcNpcDamage(int adddmg) {
		if (_pc instanceof L1RobotInstance) {
			if (((L1RobotInstance) _pc).��ɺ�_��ġ.equalsIgnoreCase("����")
					|| ((L1RobotInstance)_pc).��ɺ�_��ġ.startsWith("�ؼ�")
					|| ((L1RobotInstance) _pc).��ɺ�_��ġ.equalsIgnoreCase("���ڼ���")
					|| ((L1RobotInstance) _pc).��ɺ�_��ġ.equalsIgnoreCase("���ž4��")
					|| ((L1RobotInstance) _pc).��ɺ�_��ġ.equalsIgnoreCase("���ž5��")) {
				if (_pc.getCurrentWeapon() == 46 // �ܰ�
						|| _pc.getCurrentWeapon() == 20
						|| _pc.getCurrentWeapon() == 24) {// Ȱ
					return _random.nextInt(50) + 100;
				} else {
					return _random.nextInt(50) + 50;
				}
			} else {
				if (_pc.getCurrentWeapon() == 46 // �ܰ�
						|| _pc.getCurrentWeapon() == 20
						|| _pc.getCurrentWeapon() == 24) {// Ȱ
					return _random.nextInt(30) + 70;
				} else {
					return _random.nextInt(40) + 40;
				}
			}
		}
		int weaponMaxDamage = 0;
		boolean secondw = false;

		int doubleChance = _random.nextInt(100) + 1;

		if (_targetNpc.getNpcTemplate().get_size().equalsIgnoreCase("small")
				&& _weaponSmall > 0) {
			weaponMaxDamage = _weaponSmall + _weaponAddDmg;
		} else if (_targetNpc.getNpcTemplate().get_size()
				.equalsIgnoreCase("large")
				&& _weaponLarge > 0) {
			weaponMaxDamage = _weaponLarge + _weaponAddDmg;
		} else {
			weaponMaxDamage = _weaponSmall + _weaponAddDmg;
		}

		if (_pc.isWarrior() && _pc.isSlayer && _pc.getSecondWeapon() != null) {
			int ran = _random.nextInt(100);
			if (ran < 50) {
				secondw = true;
				if (_targetNpc.getNpcTemplate().get_size()
						.equalsIgnoreCase("small")
						&& _SweaponSmall > 0) {
					weaponMaxDamage = _SweaponSmall + _SweaponAddDmg;
				} else if (_targetNpc.getNpcTemplate().get_size()
						.equalsIgnoreCase("large")
						&& _SweaponLarge > 0) {
					weaponMaxDamage = _SweaponLarge + _SweaponAddDmg;
				} else {
					weaponMaxDamage = _SweaponSmall + _SweaponAddDmg;
				}
			}
		}

		if (_weaponId == 90083) { // ��ȿ���̵���
			_weaponDoubleDmgChance += _weaponEnchant + 1;
		}

		int weaponDamage = 0;
		if (_weaponId == 154 || _weaponId == 84 || _weaponId == 100084) {
			if (_weaponType == 58
					&& doubleChance <= _weaponDoubleDmgChance + _weaponEnchant) { // ����
				// ��Ʈ
				weaponDamage = weaponMaxDamage;
				_attackType = 2;
				// _pc.sendPackets(new S_SkillSound(_pc.getId(), 3671));
				// Broadcaster.broadcastPacket(_pc, new
				// S_SkillSound(_pc.getId(), 3671));
			}
		} else {
			if (_weaponType == 58 && doubleChance <= _weaponDoubleDmgChance) { // ����
				// ��Ʈ
				weaponDamage = weaponMaxDamage;
				_attackType = 2;
				// _pc.sendPackets(new S_SkillSound(_pc.getId(), 3671));
				// Broadcaster.broadcastPacket(_pc, new
				// S_SkillSound(_pc.getId(), 3671));
			} else if (_weaponType == 0) { // �Ǽ�
				weaponDamage = 0;
			} else {
				weaponDamage = _random.nextInt(weaponMaxDamage + 1) + 1;
			}
		}
		
		if (_weaponId == 189 || _weaponId == 30220 || _weaponId ==293 || _weaponId ==30082 || _weaponId ==413105
			|| _weaponId ==31082 || _weaponId ==222206 ){ // ����ȿ��
			if(_targetNpc.getMaxHp() >= 10000){
				// ������ �ٸ��� ���� �ִ뵥������ �ǽ��� ������ŭ ������ �����Ͽ� ������ �߰�
				weaponDamage = weaponMaxDamage + _random.nextInt(_pc.getLevel() / 3 );
			}
		}



		if (weaponDamage > weaponMaxDamage) {
			_ũ��Ƽ�� = true;
		}

		// //
		int ġ�� = _random.nextInt(100) + 1;
		int ġ��Ȯ�� = 0;
		if (_weaponType == 20 || _weaponType == 62) {
			ġ��Ȯ�� = CalcStat.���Ÿ�ġ��Ÿ(_pc.getAbility().getTotalDex());
		} else {
			ġ��Ȯ�� = CalcStat.�ٰŸ�ġ��Ÿ(_pc.getAbility().getTotalStr());
		}

		if (ġ�� <= ġ��Ȯ��) {
			_ũ��Ƽ�� = true;
			// System.out.println("333333");
			weaponDamage = weaponMaxDamage;
		}

		if (_weaponType != 20 && _weaponType != 62) {
			if (_pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME)) {
				weaponDamage = weaponMaxDamage;
				_ũ��Ƽ�� = false;
			}
		}

		int weaponTotalDamage = 0;

		if (secondw) {
			weaponTotalDamage = weaponDamage + _SweaponEnchant;
			weaponTotalDamage += calcSMaterialBlessDmg();
		} else {
			weaponTotalDamage = weaponDamage + _weaponEnchant;
			weaponTotalDamage += calcMaterialBlessDmg();
		}

		// ���ູ ������ ���ʽ�

		if (_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.����¡���Ǹ���)) {
			if (_weaponType == 54 ) { // �̵���
				// ����
				weaponTotalDamage *= 2.4;
				if (_weaponId != 415011) {
					weaponTotalDamage *= 1.1;
				}
				_attackType = 4;
				_pc.sendPackets(new S_SkillSound(_target.getId(), 14547) ,true);
				_pc.broadcastPacket(new S_SkillSound(_target.getId(), 14547) ,true);
			}
		}else{
			if (_weaponType == 54 && doubleChance <= _weaponDoubleDmgChance) { // �̵���
				// ����
				weaponTotalDamage *= 2.4;
				if (_weaponId != 415011) {
					weaponTotalDamage *= 1.1;
				}
				_attackType = 4;
				if (_pc.��ؽ� && _pc.����¡) {
						int timeb = 3;
						if(_pc.getLevel() == 86)
							timeb +=1;
						if(_pc.getLevel() >= 87)
							timeb +=1;
						_pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.����¡���Ǹ���, timeb * 1000);
						_pc.sendPackets(new S_CreateItem(1, L1SkillId.����¡���Ǹ���, timeb));	
						_pc.sendPackets(new S_SkillSound(_target.getId(), 14547));
						_pc.broadcastPacket(new S_SkillSound(_target.getId(), 14547));
				}
				_pc.��ؽ� = false;
				_pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ؽ�);		
				
			}
		}
		if (_pc.isCrash) {
			int rnd = _random.nextInt(100);
			if (rnd < 25) {
				int crashdmg = _pc.getLevel() / 2;
				double purrydmg = crashdmg * 2;
				int plusdmg = crashdmg;
				int gfx = 12487;
				if (_pc.isPurry) {
					int rnd2 = _random.nextInt(100);
					if (rnd2 < 15) {
						gfx = 12489;
						plusdmg = (int) purrydmg;
					}
				}
				weaponTotalDamage += plusdmg;
				_pc.sendPackets(new S_SkillSound(_target.getId(), gfx));// 12489
				Broadcaster.broadcastPacket(_target,
						new S_SkillSound(_target.getId(), gfx));
			}
		}
		
		if (_weaponId == 203 || _weaponId == 160 || _weaponId == 134
				|| _weaponId == 124 || _weaponId == 100124 || _weaponId == 61
				|| _weaponId == 12 || _weaponId == 86) {
			if (_weaponEnchant >= 1) // 1�϶� ��Ÿ�̷���
				weaponTotalDamage += 5;
			if (_weaponEnchant >= 2) // 2�϶� ��Ÿ�̷���
				weaponTotalDamage += 8;
			if (_weaponEnchant >= 3) // 3�϶� ��Ÿ
				weaponTotalDamage += 13;
			if (_weaponEnchant >= 4) // 4�϶� ��Ÿ
				weaponTotalDamage += 18;
			if (_weaponEnchant >= 6) // 6�϶� ��Ÿ
				weaponTotalDamage += 21;
		}
		if (secondw) {
			weaponTotalDamage += calcSAttrEnchantDmg();
		} else {
			weaponTotalDamage += calcAttrEnchantDmg();
		}

		if ((_weaponType == 54 || _weaponType == 58)
				&& _pc.getSkillEffectTimerSet().hasSkillEffect(DOUBLE_BRAKE)) {
			if ((_random.nextInt(100) + 1) <= 33) {
				weaponTotalDamage *= 2;
				double_burning = true;
			}
		}

		double dmg = weaponTotalDamage + _statusDamage;

		if (_weaponType != 20 && _weaponType != 62) {
			dmg += _pc.getDmgup() + _pc.getDmgupByArmor() + _pc.get_regist_PVPweaponTotalDamage()+2;
		} else {
			dmg += _pc.getBowDmgup() + _pc.getBowDmgupByArmor()
					+ _pc.getBowDmgupByDoll() + _pc.get_regist_PVPweaponTotalDamage()+2;
		}
		

		if (_weaponType == 20) { // Ȱ
			if (_arrow != null) {
				int add_dmg = 0;
				if (_targetNpc.getNpcTemplate().get_size()
						.equalsIgnoreCase("large")) {
					add_dmg = _arrow.getItem().getDmgLarge();
				} else {
					add_dmg = _arrow.getItem().getDmgSmall();
				}
				if (add_dmg == 0) {
					add_dmg = 1;
				}
				if (_targetNpc.getNpcTemplate().is_hard()) {
					add_dmg /= 2;
				}
				dmg = dmg + _random.nextInt(add_dmg) + 1;
			} else if (_weaponId == 190 || _weaponId == 100190 || _weaponId == 450029  || _weaponId == 30082
					|| (_weaponId >= 11011 && _weaponId <= 11013)) { // �������� Ȱ
				int add_dmg = 0;
				add_dmg = (int) (dmg + (_random.nextInt(3) + 1));
				if (_targetNpc.getNpcTemplate().is_hard()) {
					add_dmg /= 2;
				}
				dmg = add_dmg;
			} else if ( _weaponId == 7201){
				int add_dmg = 0;
				add_dmg = (int) (dmg + 10);
				dmg = add_dmg;
			}
		} else if (_weaponType == 62) { // �� ���� ��
			int add_dmg = 0;
			if (_targetNpc.getNpcTemplate().get_size()
					.equalsIgnoreCase("large")) {
				add_dmg = _sting.getItem().getDmgLarge();
			} else {
				add_dmg = _sting.getItem().getDmgSmall();
			}
			if (add_dmg == 0) {
				add_dmg = 1;
			}
			dmg = dmg + _random.nextInt(add_dmg) + 1;
		}

		dmg = calcBuffDamage(dmg);

		if (_weaponType == 0) { // �Ǽ�
			dmg = (_random.nextInt(5) + 4) / 4;
		} else {
			if (_weaponType1 == 17) { // Ű��ũ
				int Ű��ũ�������Ʈ = _pc.getAbility().getTotalInt();
				int Ű��ũ��������� = _pc.getAbility().getSp();
				int Ű��ũ�ִ� = Ű��ũ�������Ʈ + Ű��ũ���������;
				int Ű��ũ���� = (_random.nextInt(Ű��ũ�������Ʈ) + 1)
						+ (_random.nextInt(Ű��ũ���������) + 1);
				/*
				 * if(_target.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.
				 * ERASE_MAGIC)){
				 * _target.getSkillEffectTimerSet().removeSkillEffect
				 * (L1SkillId.ERASE_MAGIC); }
				 */
				dmg += Ű��ũ����;

				dmg = calcMrDefense(_target.getResistance()
						.getEffectedMrBySkill(), dmg);

				if (Ű��ũ���� >= Ű��ũ�ִ�) {
					_ũ��Ƽ�� = true;
				}
			} else if (_weaponType1 == 18) {
				dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId);
			}

			if (secondw) {
				switch (_SweaponId) {
				case 90085:
				case 90086:
				case 90087:
				case 90088:
				case 90089:
				case 90090:
				case 90091:
				case 90093:
				case 90094:
				case 90095:
				case 90096:
				case 90097:
				case 90098:
				case 90099:
				case 90100:
		        case 110051:
                case 110052:
                case 110053:
                case 110054:
                case 110055:
                case 110056:
                case 110057:
                case 110058:
					WeaponSkill.�������ũ(_pc, _target, _weaponEnchant);
					break;
				case 7236:
				case 7237:
				case 30084:
					WeaponSkill.getDeathKnightjin(_pc, _target);
					break;
				case 13:
				case 44:
					WeaponSkill.getPoisonSword(_pc, _target);
					break;
				case 100047:
				case 47:
				case 450031:
					WeaponSkill.getSilenceSword(_pc, _target, _weaponEnchant);
					break;
				case 134:
				case 30086:
				case 222204:
					dmg += WeaponSkill.get������Damage(_pc, _target,
							_weaponEnchant);
					break;
				case 294: // ���������ǰ� 123551
					WeaponSkill.AkdukSword(_pc, _target, 60);
					//   dmg += getEbHP(_pc, _target, 8981, _weaponEnchant);
					break;
				   case 30081:
                   case 31081:   
                   case 222207:
				        dmg += WeaponSkill.���丮��������(this._pc, this._target, 12248, this._weaponEnchant);
				        break;
				case 3000081:
					
					 dmg += WeaponSkill.Icekiring11(_pc, _target, _weaponEnchant);
				   case 30080: 
				   case 31080:
				   case 222205:
				    	dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId);
				    	break;
				case 30082://���̾�
				case 31082://���̾�
				case 222206://���̾�
					WeaponSkill.�̺�Ʈ��(_pc, _target, _weaponEnchant);
					break;
				
				case 54:
					dmg += WeaponSkill.getKurtSwordDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 58:
					dmg += WeaponSkill.getDeathKnightSwordDamage(_pc, _target,
							_weaponEnchant);
					break;
					// case 59: dmg += WeaponSkill.getBarlogSwordDamage(_pc,
					// _target, _weaponEnchant); break;
				case 76:
					dmg += WeaponSkill.getRondeDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 121:
					dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 124:
				case 100124:
					dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 9080:
				case 191:// ��õ��Ȱ
					// WeaponSkill.giveSalCheonEffect(_pc, _target);
					// DrainofEvil(); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 9361);
					break;
					// case 114: WeaponSkill.getGunjuSword(_pc,
					// _target,_weaponEnchant); break;
				case 203:
					dmg += WeaponSkill.getBarlogSwordDamage(_pc, _target);
					break;
				case 86: // ����
				case 204:
				case 100204:
				case 30087:
				case 222203:
					WeaponSkill.giveFettersEffect(_pc, _target);
					break;
				case 205:
				case 100205:
					dmg += WeaponSkill.getMoonBowDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 264:
				case 265:
				case 256:
				case 4500027:
					dmg += WeaponSkill
					.getEffectSwordDamage(_pc, _target, 11107);
					break;
				case 9079:
				case 412000: // dmg += WeaponSkill.getEffectSwordDamage(_pc,
					// _target, 10); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3940);
					break;
					/*
					 * case 410000: case 410001: case 450009: case 450014: case
					 * 450004: dmg += WeaponSkill.getChainSwordDamage(_pc, _target);
					 * break;
					 */
				case 412004: // dmg += WeaponSkill.getIceSpearDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3704);
					break;
				case 7228:
				case 9075:
				case 412005: // dmg += WeaponSkill.geTornadoAxeDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 5524);
					break;
					// case 412003: WeaponSkill.getDiseaseWeapon(_pc, _target,
					// 412003); break;
				case 413101:
				case 413102:
				case 413104:
				case 413105:
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 413103:
					calcStaffOfMana();
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 263:
				case 4500028:
				case 4500026:
					dmg += WeaponSkill.halloweenCus2(_pc, _target);
					break;// ����
					// case 263:
					// case 4500028: dmg += WeaponSkill.halloweenCus(_pc, _target);
					// break;//ȣ��
				case 415010:
				case 415011:
				case 415012:
				case 415013:
					WeaponSkill.ü�̼�(_pc, _target, _weaponEnchant, 0);
					break;
				case 415015:
				case 415016:
					WeaponSkill.ü�̼�(_pc, _target, _weaponEnchant, 1);
					break;
				case 6000:
					dmg += WeaponSkill.IceChainSword(_pc, _target,
							_weaponEnchant);
					break;
				case 6001:
					dmg += WeaponSkill.Icekiring(_pc, _target, _weaponEnchant);
					break;
				case 450008:
				case 450010:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
					break;
				case 450011:
				case 450012:
				case 450013:
					WeaponSkill.�̺�Ʈ��(_pc, _target, _weaponEnchant);
					break;
				case 450022:
				case 450024:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant, 8981);
					break;
				case 450023:
				case 450025:
					WeaponSkill.�̺�Ʈ��(_pc, _target, _weaponEnchant, 8981);
					break;
				case 100259:
				case 100260:
				case 9081:
				case 259:
				case 260:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 9359);
					break; // �ı��� ũ�ο�, �̵���
				case 9077:
				case 266:
				case 100266:
					dmg += WeaponSkill.PhantomShock(_pc, _target,
							_weaponEnchant);
					break; // ������ Ű��ũ
				case 9076:
				case 275:
				case 100275:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 7398);
					break; // ȯ���� ü�μҵ�
				case 277:
				case 278:
				case 279:
				case 280:
				case 281:
				case 282:
				case 283:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
					break; // ���� ���� ����
				case 284:
				case 285:
				case 286:
				case 287:
				case 288:
				case 289:
				case 290:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3939);
					break; // ȯ���� ���� (10�� �̺�Ʈ)
				case 291: // ���ν���������
					dmg += WeaponSkill.getZerosDamage(_pc, _target,
							_weaponEnchant);
					break;
				default:
					break;
				}
			} else {
				switch (_weaponId) {

				case 90085:
				case 90086:
				case 90087:
				case 90088:
				case 90089:
				case 90090:
				case 90091:
				case 90093:
				case 90094:
				case 90095:
				case 90096:
				case 90097:
				case 90098:
				case 90099:
				case 90100:
		        case 110051:
                case 110052:
                case 110053:
                case 110054:
                case 110055:
                case 110056:
                case 110057:
                case 110058:
					WeaponSkill.�������ũ(_pc, _target, _weaponEnchant);
					break;

				case 7236:
				case 7237:
				case 30084:
					WeaponSkill.getDeathKnightjin(_pc, _target);
					break;
				case 13:
				case 44:
					WeaponSkill.getPoisonSword(_pc, _target);
					break;
				case 100047:
				case 47:
				case 450031:
					WeaponSkill.getSilenceSword(_pc, _target, _weaponEnchant);
					break;
				case 134:
				case 30086:
				case 222204:
					dmg += WeaponSkill.get������Damage(_pc, _target,
							_weaponEnchant);
					break;
				case 294: // ���������ǰ� 123551
					WeaponSkill.AkdukSword(_pc, _target, 60);
					//   dmg += getEbHP(_pc, _target, 8981, _weaponEnchant);
					break;
				   case 30081:
				   case 31081:
				   case 222207:
				        dmg += WeaponSkill.���丮��������(this._pc, this._target, 12248, this._weaponEnchant);
				        break;
				case 3000081:
					 dmg += WeaponSkill.Icekiring11(_pc, _target, _weaponEnchant);
				   case 30080: 
				   case 31080:
				   case 222205:
				    	dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId);
				    	break;
				
				   case 30082://���̾�
				   case 31082://���̾�
					case 222206://���̾�
					WeaponSkill.�̺�Ʈ��(_pc, _target, _weaponEnchant);
					break;
			
				   case 54:
					dmg += WeaponSkill.getKurtSwordDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 58:
					dmg += WeaponSkill.getDeathKnightSwordDamage(_pc, _target,
							_weaponEnchant);
					break;
					// case 59: dmg += WeaponSkill.getBarlogSwordDamage(_pc,
					// _target, _weaponEnchant); break;
				case 76:
					dmg += WeaponSkill.getRondeDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 121:
					dmg += WeaponSkill.getIceQueenStaffDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 124:
				case 100124:
					dmg += WeaponSkill.getBaphometStaffDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 9080:
				case 191:// ��õ��Ȱ
					// WeaponSkill.giveSalCheonEffect(_pc, _target);
					// DrainofEvil(); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 9361);
					break;
					// case 114: WeaponSkill.getGunjuSword(_pc,
					// _target,_weaponEnchant); break;
				case 203:
					dmg += WeaponSkill.getBarlogSwordDamage(_pc, _target);
					break;
				case 86: // ����
				case 204:
				case 100204:
				case 30087:
				case 222203:
					WeaponSkill.giveFettersEffect(_pc, _target);
					break;
				case 205:
				case 100205:
					dmg += WeaponSkill.getMoonBowDamage(_pc, _target,
							_weaponEnchant);
					break;
				case 264:
				case 265:
				case 256:
				case 4500027:
					dmg += WeaponSkill
					.getEffectSwordDamage(_pc, _target, 11107);
					break;
				case 9079:
				case 412000: // dmg += WeaponSkill.getEffectSwordDamage(_pc,
					// _target, 10); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3940);
					break;
					/*
					 * case 410000: case 410001: case 450009: case 450014: case
					 * 450004: dmg += WeaponSkill.getChainSwordDamage(_pc, _target);
					 * break;
					 */
				case 412004: // dmg += WeaponSkill.getIceSpearDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3704);
					break;
				case 7228:
				case 9075:
				case 412005: // dmg += WeaponSkill.geTornadoAxeDamage(_pc,
					// _target); break;
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 5524);
					break;
					// case 412003: WeaponSkill.getDiseaseWeapon(_pc, _target,
					// 412003); break;
				case 413101:
				case 413102:
				case 413104:
				case 413105:
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 413103:
					calcStaffOfMana();
					WeaponSkill.getDiseaseWeapon(_pc, _target, 413101);
					break;
				case 263:
				case 4500028:
				case 4500026:
					dmg += WeaponSkill.halloweenCus2(_pc, _target);
					break;// ����
					// case 263:
					// case 4500028: dmg += WeaponSkill.halloweenCus(_pc, _target);
					// break;//ȣ��
				case 415010:
				case 415011:
				case 415012:
				case 415013:
					WeaponSkill.ü�̼�(_pc, _target, _weaponEnchant, 0);
					break;
				case 415015:
				case 415016:
					WeaponSkill.ü�̼�(_pc, _target, _weaponEnchant, 1);
					break;
				case 6000:
					dmg += WeaponSkill.IceChainSword(_pc, _target,
							_weaponEnchant);
					break;
				case 6001:
					dmg += WeaponSkill.Icekiring(_pc, _target, _weaponEnchant);
					break;
				case 450008:
				case 450010:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
					break;
				case 450011:
				case 450012:
				case 450013:
					WeaponSkill.�̺�Ʈ��(_pc, _target, _weaponEnchant);
					break;
				case 450022:
				case 450024:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant, 8981);
					break;
				case 450023:
				case 450025:
					WeaponSkill.�̺�Ʈ��(_pc, _target, _weaponEnchant, 8981);
					break;
				case 100259:
				case 100260:
				case 9081:
				case 259:
				case 260:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 9359);
					break; // �ı��� ũ�ο�, �̵���
				case 9077:
				case 266:
				case 100266:
					dmg += WeaponSkill.PhantomShock(_pc, _target,
							_weaponEnchant);
					break; // ������ Ű��ũ
				case 9076:
				case 275:
				case 100275:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 7398);
					break; // ȯ���� ü�μҵ�
				case 277:
				case 278:
				case 279:
				case 280:
				case 281:
				case 282:
				case 283:
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
					break; // ���� ���� ����
				case 284:
				case 285:
				case 286:
				case 287:
				case 288:
				case 289:
				case 290:
					dmg += WeaponSkill.getEffectDamage(_pc, _target,
							_weaponEnchant, 3939);
					break; // ȯ���� ���� (10�� �̺�Ʈ)
				case 291: // ���ν���������
					dmg += WeaponSkill.getZerosDamage(_pc, _target,
							_weaponEnchant);
					break;
		        case 90084:
		            WeaponSkill.��������ü�μҵ�(_pc);      	
				default:
					break;
				}
			}
			if (_weaponId == 450009) {
				dmg += WeaponSkill.getChainSwordDamage(_pc, _target, _weaponId);
				WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
			}
			if (secondw) {
				if (_SweaponId == 7233) {
					WeaponSkill.�̺�����(_pc, _target, _SweaponEnchant);
				}
			} else {
				if (_weaponId == 7233) {
					WeaponSkill.�̺�����(_pc, _target, _weaponEnchant);
				}
			}
		}
		
		try {
		
			  dmg += WeaponAddDamage.getInstance().getWeaponAddDamage(_weaponId);
		} catch (Exception e) {
			System.out.println("Weapon Add Damege Error");
		}

		if (_weaponId == 9078 || _weaponId == 276 || _weaponId == 100276 || // ��������Ǵ��
				_weaponId == 292 || _weaponId == 293) {// ������ũ�ο�, �Ǹ������
			if (_pc.getLawful() <= -20000)
				dmg += 5;
			else if (_pc.getLawful() <= -10000)
				dmg += 3;
			else if (_pc.getLawful() <= 0)
				dmg += 1;
			/*
			 * double lawful = 32767 - _pc.getLawful(); lawful *= 0.000005; dmg
			 * += dmg * lawful;
			 */
		}

		if (_weaponId >= 90085 && _weaponId <= 90092) {
			dmg += _weaponEnchant;
		}

		if (_pc.getDollList().size() > 0) {
			for (L1DollInstance doll : _pc.getDollList()) {
				if (_weaponType != 20 && _weaponType != 62) {
					int d = doll.getDamageByDoll(_targetNpc);
					if (d > 0
							&& doll.getDollType() == L1DollInstance.DOLLTYPE_�׷���)
						_pc.setCurrentHp(_pc.getCurrentHp()
								+ _random.nextInt(5) + 1);
					dmg += d;
				}

				if (doll.getDollType() == L1DollInstance.DOLLTYPE_�����
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_��������Ʈ) {
					dmg += doll.getMagicDamageByDoll(_targetNpc);
				}

				doll.attackPoisonDamage(_pc, _targetNpc);
			}
		}

		if (adddmg != 0)
			dmg += adddmg;

		// �๫�� ������ �߰�
		
		  if(weapon != null){ if(weapon.getBless() == 0){ dmg += 1; } }
		 
		// �๫�� ������ �߰�
		/*
		 * if(Sweapon != null){ if(Sweapon.getBless() == 0){ dmg += 1; } }
		 */
		if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ARMOR_BREAK)){//�ƸӺ극��ũ
			if (_weaponType != 20 || _weaponType != 62) {
				dmg *= 1.45;
			}
		}

	
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SLASH)) {
			dmg += 50;
			_pc.sendPackets(new S_SkillSound(_targetNpc.getId(), 6591), true);
			Broadcaster.broadcastPacket(_pc,
					new S_SkillSound(_targetNpc.getId(), 6591), true);
			_pc.getSkillEffectTimerSet().killSkillEffectTimer(BURNING_SLASH);
		}
		

		dmg += ��Ƽ���˱��߰�������();

		/*
		 * if(_weaponType1 != 17)//Ű��ũ�� �ƴҶ�
		 */
		dmg -= calcNpcDamageReduction();

		if (_targetNpc.getNpcId() == 45640) {
			dmg /= 2;
		}
		// �÷��̾�κ��� �ֿϵ���, ��� ����
		boolean isNowWar = false;
		int castleId = L1CastleLocation.getCastleIdByArea(_targetNpc);
		if (castleId > 0) {
			isNowWar = WarTimeController.getInstance().isNowWar(castleId);
		}
		if (!isNowWar) {
			if (_targetNpc instanceof L1PetInstance) {
				dmg /= 8;
			}
			/*
			 * if (_targetNpc instanceof L1SummonInstance) { L1SummonInstance
			 * summon = (L1SummonInstance) _targetNpc; if
			 * (summon.isExsistMaster()) { dmg /= 8; } }
			 */
		}

		/*
		 * if(_weaponType == 50){ //��� dmg += dmg*0.2; }else if (_weaponType ==
		 * 20) { // Ȱ dmg += dmg*0.3; }else if (_weaponType == 4) { // �Ѽ� dmg +=
		 * dmg*0.2; }else if (_weaponType == 54) { // �̵��� dmg += dmg*0.2; }else
		 * if (_weaponType == 58) { // Ű��ũ dmg += dmg*0.20; }else if
		 * (_weaponType == 46) { // �ܰ� dmg += dmg*0.30; }else if(_weaponType ==
		 * 24){ // ü�μҵ� dmg += dmg*0.15; }else if(_weaponType == 11){ // ���� dmg
		 * += dmg*0.20; }
		 */
		// dmg += dmg*0.10; // ��繫�� ����

		// ���������//�๫��
		/*
		 * if(_weaponId == 49||_weaponId == 100049){ dmg += dmg*0.1; } //����� Į��
		 * else if(_weaponId == 55){ dmg += dmg*0.1; } //�ο�ƺ����//��ο� else
		 * if(_weaponId == 57 ||_weaponId == 100057){ dmg += dmg*0.16; } //�׾�
		 * else if( _weaponId ==415010){ dmg += dmg*0.07; } //��â else
		 * if(_weaponId ==415016){ dmg += dmg*0.10; } //������ ��Ʋ� else
		 * if(_weaponId >= 450008 && _weaponId <= 450021 ){ dmg += dmg*0.07; }
		 * //�Ź��� else if(_weaponId == 450022){ dmg += dmg*0.03; } else
		 * if(_weaponId == 450023){ dmg += dmg*0.03; } else if(_weaponId ==
		 * 450024){ dmg += dmg*0.03; } else if(_weaponId == 450025){ dmg +=
		 * dmg*0.03; } //�Ǹ�����Ȱ else if(_weaponId == 413105){ dmg += dmg*0.05; }
		 * // �⸣�� else if(_weaponId == 217){ dmg += dmg*0.09; } //���߾�հ� else
		 * if(_weaponId == 59){ dmg += dmg*0.07; }
		 * 
		 * //������ ��հ� �๫�� else if(_weaponId == 62 ||_weaponId == 100062){ dmg +=
		 * dmg*0.05; }
		 * 
		 * //����� else if(_weaponId == 188 ){ dmg += dmg*0.05; } //����Ű�� else
		 * if(_weaponId == 410003 ){ dmg += dmg*0.1; } //���� else if(_weaponId ==
		 * 9 || _weaponId == 100009 || _weaponId == 10000000 || _weaponId ==
		 * 76269){ dmg += dmg*0.05; } //���� else if(_weaponId == 11 ){ dmg +=
		 * dmg*0.07; }else if (_weaponId == 7230){// �����ǵ��� dmg += dmg*0.20;
		 * }else if (_weaponId == 7227){// ������ǵ��� dmg += dmg*0.15; }else if
		 * (_weaponId == 7225){// �����ǵ��� dmg += dmg*0.10; }else if (_weaponId ==
		 * 7229){// ��ö���� dmg += dmg*0.05; }else if (_weaponId == 7233){// ��������
		 * dmg += dmg*0.07; }else if (_weaponId == 7228){// ��ǳ�� ���� dmg +=
		 * dmg*0.08; }else if (_weaponId == 412005){// ��ǳ�� ���� dmg += dmg*0.07;
		 * }else if (_weaponId == 7231){// ���������� ���� dmg += dmg*0.05; }
		 */

		// �𵥵� �� ������ ����
		if (isUndeadDamage(_targetNpc)) {
			dmg -= dmg * 0.15;
		}

		/*
		 * if(_weaponId == 412001 || _weaponId == 1412001){//�Ĵ� dmg += dmg*0.05;
		 * }
		 */
		/*
		 * if(_weaponId == 6001){//��Ű dmg -= dmg*0.20; }
		 */

		/*
		 * if(_weaponId == 450013 || _weaponId == 100266 || _weaponId == 266 ||
		 * _weaponId == 10000006 || _weaponId == 7410004 || _weaponId == 410004
		 * ){//����, ����, ��伮 dmg -= dmg*0.25; }
		 */

		/*
		 * if(_weaponId == 410003 || _weaponId == 510003){ // �����̾� dmg -=
		 * dmg*0.50; }
		 */

		/*
		 * if(_weaponId == 100057){//��ο� dmg += dmg*0.30; }
		 */
		/*
		 * if(_weaponId == 59 || _weaponId == 100059){//���� dmg += dmg*0.20; }
		 */
		/*
		 * if(_weaponId == 62 || _weaponId == 100062|| _weaponId == 10000001||
		 * _weaponId == 762662){ //���� dmg += dmg*0.10; }
		 */
		/*
		 * if(_weaponId == 262){ //���� ��Ŀ dmg += dmg*0.15; }
		 */

		if (_weaponId == 22) { // ���Ϻ극��Ŀ
			dmg -= dmg * 0.30;
		}

		if (_weaponId == 450009) { // ü�μҵ�
			dmg -= dmg * 0.25;
		}

		if (_weaponId == 410000 || _weaponId == 510000) {// �Ҹ����� ü�μҵ�
			dmg -= dmg * 0.30;
		}
		if (_weaponId == 7229) {// ��ö����
			dmg -= dmg * 0.10;
		}

		if (_weaponId == 7 || _weaponId == 35 || _weaponId == 48
				|| _weaponId == 73 || _weaponId == 105 || _weaponId == 120
				|| _weaponId == 147 || _weaponId == 156 || _weaponId == 174
				|| _weaponId == 175 || _weaponId == 224 || _weaponId == 7232) {
			if (Config.�����ڹ���뷱����ġ != 0) {
				double balance = Config.�����ڹ���뷱����ġ * 0.01;
				dmg -= dmg * balance;// ������ ���� 35%����
			}
		}

		if (_weaponAttr != 0)
			dmg += addattrdmg(_weaponAttr);

		if (dmg <= 0) {
			_isHit = false;
			_drainHp = 0;
		}
		// ����� ���� �ٲٱ����� �ű�.
		if (_weaponId == 262) { // ���弭Ŀ
			if (dmg >= 30 && _random.nextInt(100) <= 90) {
				// _pc.sendPackets(new
				// S_SystemMessage("���� :"+_drainHp+" ������ :"+dmg));
				int _dhp = (int) ((dmg - 10) / 10);
				_drainHp += _dhp;// +_random.nextInt(3)+1;
			}
		}

		if (dmg > 0 && _targetNpc instanceof L1SummonInstance
				|| _targetNpc instanceof L1PetInstance)
			dmg = 1;
		return (int) dmg;
	}

	// �ܡܡܡ� NPC �κ��� �÷��̾�� ������ ���� �ܡܡܡ�
	private int calcNpcPcDamage() {
		int lvl = _npc.getLevel();
		double dmg = 1D;//0
		if (_targetPc instanceof L1RobotInstance) {
			 dmg = 20;
			
			} //�κ�����

		if (_npc instanceof L1PetInstance) { // ������ ������ str 10�� �������� ������.
			if (_npc.getAbility().getStr() < 10) { // �������� 8���� 1��Ÿ��.
				dmg = (lvl / 8)
						+ _random.nextInt(_npc.getAbility().getTotalStr()) * 2;
				dmg += ((L1PetInstance) _npc).getDamageByWeapon();
			} else if (_npc.getAbility().getStr() >= 10) { // �������� 4���� 1��Ÿ��.
				dmg = (lvl / 4)
						+ _random.nextInt(_npc.getAbility().getTotalStr()) * 2;
				dmg += ((L1PetInstance) _npc).getDamageByWeapon();
			}
		} else {
			if (lvl < 10) // ������ 10�̸�
				dmg = _random.nextInt(lvl) + 10D
				+ _npc.getAbility().getTotalStr() + 2;
			else if (lvl >= 10 && lvl < 20) // ������ 10 ~ 49
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
				+ 2;
			else if (lvl >= 20 && lvl < 30) // ������ 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
				+ 4;
			else if (lvl >= 30 && lvl < 40) // ������ 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
				+ 8;
			else if (lvl >= 40 && lvl < 50) // ������ 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
				+ 12;
			else if (lvl >= 50 && lvl < 60) // ������ 70 ~ 79
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
				+ 16;
			else if (lvl >= 60 && lvl < 70) // ������ 80 ~ 86
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
				+ 20;
			else if (lvl >= 70 && lvl < 80) // ������ 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
				+ 30;
			else if (lvl >= 80 && lvl < 87) // ������ 50 ~ 69
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
				+ 40;
			else if (lvl >= 87) // ������ 87 �̻�
				dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
				* 2 + 100;
		}

		if (_target != null) {
			int chance1 = _random.nextInt(100);
			for (L1ItemInstance item : _targetPc.getInventory().getItems()) {
				if (item.isEquipped()) {
					if (item.getItemId() >= 420104
							&& item.getItemId() <= 420107) {
						int addchan = 5;/* item.getEnchantLevel()-5; */
						if (addchan < 0)
							addchan = 0;
						if (chance1 < /* 6+addchan */5) {
							// 123456 �϶� 80~100
							// ��Ǫ ��ȣ 7�϶� 120~140 / 8�϶� 140~160 9�϶� 160~180
							int addhp = _random.nextInt(20) + 1;
							int basehp = 80;
							if (item.getEnchantLevel() == 7)
								basehp = 120;
							if (item.getEnchantLevel() == 8)
								basehp = 140;
							if (item.getEnchantLevel() == 9)
								basehp = 160;
							_targetPc.setCurrentHp(_targetPc.getCurrentHp()
									+ basehp + addhp);
							_targetPc.sendPackets(
									new S_SkillSound(_targetPc.getId(), 2187),
									true);
							Broadcaster.broadcastPacket(_targetPc,
									new S_SkillSound(_targetPc.getId(), 2187),
									true);
						}
						break;
					} else if (item.getItemId() >= 420108
							&& item.getItemId() <= 420111) {
						int addchan = item.getEnchantLevel() - 5;
						if (addchan < 0)
							addchan = 0;
						if (chance1 < 6 + addchan) {
							if (_targetPc.getSkillEffectTimerSet()
									.hasSkillEffect(L1SkillId.���尡ȣ������)) {
								break;
							}
							if (item.getItemId() == 420108)// �Ϸ�
								_targetPc.setCurrentMp(_targetPc.getCurrentMp()
										+ 8 + _random.nextInt(7));
							else if (item.getItemId() == 420109)// ����
								_targetPc.setCurrentMp(_targetPc.getCurrentMp()
										+ 9 + _random.nextInt(7));
							else if (item.getItemId() == 420110)// �γ�
								_targetPc.setCurrentMp(_targetPc.getCurrentMp()
										+ 16 + _random.nextInt(9));
							else if (item.getItemId() == 420111)// ����
								_targetPc.setCurrentMp(_targetPc.getCurrentMp()
										+ 20 + _random.nextInt(11));

							_targetPc.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.���尡ȣ������, 4000);

							_targetPc.sendPackets(
									new S_SkillSound(_targetPc.getId(), 2188),
									true);
							Broadcaster.broadcastPacket(_targetPc,
									new S_SkillSound(_targetPc.getId(), 2188),
									true);
						}
						break;
					} else if (item.getItemId() == 21255) {
						if (chance1 < 4) {
							_targetPc
							.setCurrentHp(_targetPc.getCurrentHp() + 31);
							_targetPc.sendPackets(
									new S_SkillSound(_targetPc.getId(), 2183),
									true);
							Broadcaster.broadcastPacket(_targetPc,
									new S_SkillSound(_targetPc.getId(), 2183),
									true);
						}
						break;
						// �ż��� �������Ǳݰ���
					} else if (item.getItemId() == 222351) {
						if (chance1 < 5) {
							_targetPc
							.setCurrentHp(_targetPc.getCurrentHp() + 21);
							_targetPc.sendPackets(
									new S_SkillSound(_targetPc.getId(), 15355),
									true);
							Broadcaster.broadcastPacket(_targetPc,
									new S_SkillSound(_targetPc.getId(), 15355),
									true);
						}
						break;
					}
				}

			}
		}
		int chance66 = _random.nextInt(100) + 1;
		if (_target != null) {
			int dmg2 = 0;
			int plus = 0;
			if (_targetPc.getInventory().checkEquipped(222351)) {
				if (chance66 <= 6) { // ���� 5��
					if (_targetPc.getSkillEffectTimerSet()
							.hasSkillEffect(L1SkillId.POLLUTE_WATER)) {
						dmg2 += (25 + _random.nextInt(15) + (plus * 10)) / 2; //
					}
					if (_targetPc.getSkillEffectTimerSet()
							.hasSkillEffect(L1SkillId.WATER_LIFE)) {
						dmg2 += (25 + _random.nextInt(15) + (plus * 10)) * 2; //
					}
					dmg2 += 25 + _random.nextInt(15) + (plus * 10); //
					_targetPc.setCurrentHp(_targetPc.getCurrentHp() + dmg2);
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 15355));
					_targetPc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 15355));
				}
			}
		}
		
		dmg += _npc.getDmgup();

		if (isUndeadDamage()) {
			dmg *= 1.15;
		}

		dmg = dmg * getLeverage() / 10;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg -= (dmg*0.5);
			
		}

		if (_npc.isWeaponBreaked()) { // NPC�� �����극��ũ��.
			dmg /= 2;
		}

		dmg -= _targetPc.getDamageReductionByArmor(); // ���� �ⱸ�� ���� ������ �氨

		/** �������� ���� **/
		if (_npc.getNpcTemplate().getBowActId() == 0
				&& _targetPc.getDollList().size() > 0) {
			for (L1DollInstance doll : _targetPc.getDollList()) {
				if (doll.getDollType() == L1DollInstance.DOLLTYPE_STONEGOLEM
						|| doll.getDollType() == L1DollInstance.DOLLTYPE_�巹��ũ)
					dmg -= doll.getDamageReductionByDoll();
			}
		}
		/** �������� ���� **/

		L1ItemInstance �ݿ����ǹ��� = _targetPc.getInventory().checkEquippedItem(
				21093);
		if (�ݿ����ǹ��� != null) {
			int chance = 5 + (�ݿ����ǹ���.getEnchantLevel() * 2);
			if (_random.nextInt(100) <= chance) {
				dmg -= 70;
				_targetPc
				.sendPackets(new S_SkillSound(_targetPc.getId(), 6320));
				Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(
						_targetPc.getId(), 6320));
			}
		}
		L1ItemInstance ���������� = _targetPc.getInventory().checkEquippedItem(222355);
		if (���������� != null) {
			int chance = 3 + (����������.getEnchantLevel() * 2);
			if (_random.nextInt(100) <= chance) {
				dmg -= 50;
				_targetPc
				.sendPackets(new S_SkillSound(_targetPc.getId(), 14543));
				Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(_targetPc.getId(), 14543));
			}
		}
		if (_targetPc.isAmorGaurd) { // ����ȿ丮�� ���� ������ �氨
			int d = _targetPc.getAC().getAc() / 10;
			if (d < 0) {
				dmg += d;
			} else {
				dmg -= d;
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)) { // ����ȿ丮��
			// ����
			// ������
			// �氨
			dmg -= 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING2)) {
			dmg -= 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_�߰��)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						COOKING_NEW_����)
						|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
								COOKING_NEW_ĥ����)
								|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
										COOKING_NEW_�ѿ�)) {
			dmg -= 2;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(��Ƽ����������)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(���̽ÿ�������))
			dmg -= 5;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(��Ƽ�������丮)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(���̸����Ѷ��))
			dmg -= 5;

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(��Ƽ���ູ�ֹ���)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.����Ǳ��))
			dmg -= 3;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.EARTH_BLESS))
			dmg -= 2;
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(DRAGON_SKIN)) {
			dmg -= 2;
		}

		dmg -= ��Ƽ���ӱ͵���������();

		// �ֿϵ���, ������κ��� �÷��̾ ����
		boolean isNowWar = false;
		int castleId = L1CastleLocation.getCastleIdByArea(_targetPc);
		if (castleId > 0) {
			isNowWar = WarTimeController.getInstance().isNowWar(castleId);
		}
		if (!isNowWar) {
			if (_npc instanceof L1PetInstance) {
				dmg /= 8;
			} else if (_npc instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) _npc;
				if (summon.isExsistMaster()) {
					if (summon.getNpcTemplate().get_npcId() == 81104
							|| summon.getNpcTemplate().get_npcId() == 81240) {
						dmg /= 5;
					} else {
						dmg /= 2;
					}
				}
			}

		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IllUSION_AVATAR)) {
			dmg += dmg / 5;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(PATIENCE)) {
			dmg -= 4;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(DRAGON_SKIN)) {
			dmg -= 3;
		}

		/*
		 * //���ž �尩�� ����� if (_targetPc.getInventory().checkEquipped(20173) ||
		 * _targetPc.getInventory().checkEquipped(21103) ||
		 * _targetPc.getInventory().checkEquipped(21108)) dmg *= 1.05;
		 */

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_A)) {
			dmg -= 3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_B)) {
			dmg -= 2;
		}
		/*if (_weaponType != 62 || _weaponType != 20 && _targetPc.getSkillEffectTimerSet().hasSkillEffect(ARMOR_BREAK)) { // �ƸӺ극��ũ������
			dmg *= 1.48;
		}*/
		addNpcPoisonAttack(_npc, _targetPc);

		if (_npc instanceof L1PetInstance || _npc instanceof L1SummonInstance) {
			if (CharPosUtil.getZoneType(_targetPc) == 1) {
				_isHit = false;
			}
		}
		/*
		 * if(_npc.getNpcId()== 45338 ||_npc.getNpcId()== 45456
		 * ||_npc.getNpcId()== 45458 ||_npc.getNpcId()== 45488
		 * ||_npc.getNpcId()== 45534 ||_npc.getNpcId()== 45516
		 * ||_npc.getNpcId()== 45529 ||_npc.getNpcId()== 45529
		 * ||_npc.getNpcId()== 45535 ||_npc.getNpcId()== 45545
		 * 
		 * ||_npc.getNpcId()== 45546 ||_npc.getNpcId()== 45573
		 * ||_npc.getNpcId()== 45583 ||_npc.getNpcId()== 45584
		 * ||_npc.getNpcId()== 45600 ||_npc.getNpcId()== 45601
		 * ||_npc.getNpcId()== 45609 ||_npc.getNpcId()== 45610
		 * ||_npc.getNpcId()== 45614 ||_npc.getNpcId()== 45617
		 * ||_npc.getNpcId()== 45625 ||_npc.getNpcId()== 45640
		 * ||_npc.getNpcId()== 45640 ||_npc.getNpcId()== 45640
		 * ||_npc.getNpcId()== 45642 ||_npc.getNpcId()== 45643
		 * ||_npc.getNpcId()== 45644 ||_npc.getNpcId()== 45645
		 * ||_npc.getNpcId()== 45642 ||_npc.getNpcId()== 45643
		 * ||_npc.getNpcId()== 45644 ||_npc.getNpcId()== 45645
		 * ||_npc.getNpcId()== 45642 ||_npc.getNpcId()== 45643
		 * ||_npc.getNpcId()== 45644 ||_npc.getNpcId()== 45645
		 * ||_npc.getNpcId()== 45646 ||_npc.getNpcId()== 45649
		 * ||_npc.getNpcId()== 45651 ||_npc.getNpcId()== 45671
		 * ||_npc.getNpcId()== 45674 ||_npc.getNpcId()== 45675
		 * ||_npc.getNpcId()== 45680 ||_npc.getNpcId()== 45681
		 * ||_npc.getNpcId()== 45684 ||_npc.getNpcId()== 45685
		 * ||_npc.getNpcId()== 45734 ||_npc.getNpcId()== 45735
		 * ||_npc.getNpcId()== 45752 ||_npc.getNpcId()== 45772
		 * ||_npc.getNpcId()== 45795 ||_npc.getNpcId()== 45801
		 * ||_npc.getNpcId()== 45802 ||_npc.getNpcId()== 45829
		 * ||_npc.getNpcId()== 45548 ||_npc.getNpcId()== 46024
		 * ||_npc.getNpcId()== 46025 ||_npc.getNpcId()== 46026
		 * ||_npc.getNpcId()== 46037 ||_npc.getNpcId()== 45935
		 * ||_npc.getNpcId()== 45942 ||_npc.getNpcId()== 45941
		 * ||_npc.getNpcId()== 45931 ||_npc.getNpcId()== 45943
		 * ||_npc.getNpcId()== 45944 ||_npc.getNpcId()== 45492
		 * ||_npc.getNpcId()== 46141 ||_npc.getNpcId()== 46142
		 * ||_npc.getNpcId()== 4037000 ||_npc.getNpcId()== 4037000
		 * ||_npc.getNpcId()== 81163 ||_npc.getNpcId()== 45513
		 * ||_npc.getNpcId()== 45547 ||_npc.getNpcId()== 45606
		 * ||_npc.getNpcId()== 45650 ||_npc.getNpcId()== 45652
		 * ||_npc.getNpcId()== 45653 ||_npc.getNpcId()== 45654
		 * ||_npc.getNpcId()== 45618 ||_npc.getNpcId()== 45672
		 * ||_npc.getNpcId()== 45673 ||_npc.getNpcId() == 100570){ }else{
		 * if(_npc.getMapId() >= 151 && _npc.getMapId() <= 200) dmg -= dmg*0.40;
		 * else if(_npc.getMapId() == 400) dmg -= dmg*0.70; else
		 * if(_npc.getMapId() >= 1 && _npc.getMapId() <= 2){
		 * if(_npc.getNpcTemplate().get_undead() == 1){ if(_npc.getNpcId() ==
		 * 100081) dmg -= dmg*0.70; else dmg -= dmg*0.50; } }else
		 * if(_npc.getMapId() >= 30 && _npc.getMapId() <= 36) dmg -= dmg*0.70;
		 * else dmg -= dmg*0.45; }
		 */

		dmg -= dmg * 0.45;// 15��2��1�� ����.

		if (_targetPc.getInventory().checkEquipped(21104)
				|| _targetPc.getInventory().checkEquipped(21111)) {
			// dmg += dmg*0.2;
			if (Config.�����ڰ��ʹ뷱����ġ != 0) {
				double balance = Config.�����ڰ��ʹ뷱����ġ * 0.01;
				dmg += dmg * balance;// ������ ���� 35%����
			}
		}

		// ������ ���� ������ ������Ű��.

		/*
		 * //��� ���� 10% ���� if(_npc.getMaxHp() >= 10000){ dmg += dmg*0.10; }
		 */

		// ������ ������
		if (_npc.getNpcId() == 90008) {
			dmg -= dmg * 0.30;
		}

		if (dmg <= 0) {
			_isHit = false;
		}

		if (dmg > 0 && _npc instanceof L1SummonInstance
				|| _npc instanceof L1PetInstance)
			dmg = 1;

		return (int) dmg;
	}

	// �ܡܡܡ� NPC �κ��� NPC ���� ������ ���� �ܡܡܡ�
	private int calcNpcNpcDamage() {
		int lvl = _npc.getLevel();
		double dmg = 0;
		if (_npc instanceof L1PetInstance) { // ������ ������ str 10�� �������� ������.
			if (_npc.getAbility().getStr() < 10) { // �������� 8���� 1��Ÿ��.
				dmg = (lvl / 8)
						+ _random.nextInt(_npc.getAbility().getTotalStr()) * 2;
				dmg += ((L1PetInstance) _npc).getDamageByWeapon();
			} else if (_npc.getAbility().getStr() >= 10) { // �������� 4���� 1��Ÿ��.
				dmg = (lvl / 4)
						+ _random.nextInt(_npc.getAbility().getTotalStr()) * 2;
				dmg += ((L1PetInstance) _npc).getDamageByWeapon();
			}
		} else {
			dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr()
					+ (lvl * lvl / 100);
		}/*
		 * if (_npc instanceof L1PetInstance) { dmg =
		 * _random.nextInt(_npc.getNpcTemplate().get_level()) +
		 * _npc.getAbility().getTotalStr() / 2 + 1; dmg += (lvl / 16); // ����
		 * LV16���� �߰� Ÿ�� dmg += ((L1PetInstance) _npc).getDamageByWeapon(); }
		 * else { dmg = _random.nextInt(lvl) + _npc.getAbility().getTotalStr() /
		 * 2 + 1; }
		 */

		if (isUndeadDamage()) {
			dmg *= 1.15;
		}

		dmg = dmg * getLeverage() / 10;

		dmg -= calcNpcDamageReduction();

		if (_npc.isWeaponBreaked()) { // NPC�� �����극��ũ��.
			dmg /= 2;
		}
		if (_targetNpc.getNpcId() == 45640) {
			dmg /= 2;
		}
		if (_npc instanceof L1SummonInstance) {
			if (_npc.getNpcId() != 81104)
				dmg *= 0.7;
		}
		if (_targetNpc instanceof L1SummonInstance) {
			if (_targetNpc.getNpcId() != 81104)
				dmg *= 1.5;
		} //ARMOR_BREAK
		if (_targetNpc instanceof L1PetInstance) {
			dmg *= 2;
		}
	/*	if (_weaponType != 62 || _weaponType != 20 && _targetPc.getSkillEffectTimerSet().hasSkillEffect(ARMOR_BREAK)) { // �ƸӺ극��ũ������
			dmg *= 1.48;
		}*/
		addNpcPoisonAttack(_npc, _targetNpc);

		// �𵥵� �� ������ ����
		if (isUndeadDamage(_targetNpc)) {
			dmg -= dmg * 0.15;
		}

		if (dmg <= 0) {
			_isHit = false;
		}

		return (int) dmg;
	}

	// �ܡܡܡ� �÷��̾��� ������ ��ȭ ���� �ܡܡܡ�
	private double calcBuffDamage(double dmg) {
		if (_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SPIRIT)
				|| _pc.getSkillEffectTimerSet().hasSkillEffect(BRAVE_AURA)
				|| (_pc.getSkillEffectTimerSet().hasSkillEffect(ELEMENTAL_FIRE)
						&& _weaponType != 20 && _weaponType != 62 && _weaponType1 != 17)) {
			if ((_random.nextInt(100) + 1) <= 33) {

				double tempDmg = dmg;

				if (_pc.getSkillEffectTimerSet().hasSkillEffect(FIRE_WEAPON)) {
					tempDmg -= 4;
				}
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_WEAPON)) {
					tempDmg -= 6;
				}
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(BERSERKERS)) {
					tempDmg -= 2;
				}

				double diffDmg = dmg - tempDmg;
				dmg = tempDmg * 1.5 + diffDmg;

				burning = true;

				if (_pc.getSkillEffectTimerSet().hasSkillEffect(BURNING_SPIRIT)) {
					if (double_burning) {
						if (_calcType == PC_PC) {
							_pc.sendPackets(new S_SkillSound(_targetPc.getId(),
									6532), true);
							Broadcaster.broadcastPacket(_pc, new S_SkillSound(
									_targetPc.getId(), 6532), true);
						} else if (_calcType == PC_NPC) {
							_pc.sendPackets(new S_SkillSound(
									_targetNpc.getId(), 6532), true);
							Broadcaster.broadcastPacket(_pc, new S_SkillSound(
									_targetNpc.getId(), 6532), true);
						}
						double_burning = false;
					}
				}
			}
		}
		return dmg;
	}

	// �ܡܡܡ� �÷��̾��� AC�� ���� ������ �氨 �ܡܡܡ�
	/*
	 * private int calcPcDefense() { int ac = Math.max(0, 10 -
	 * _targetPc.getAC().getAc()); int acDefMax =
	 * _targetPc.getClassFeature().getAcDefenseMax(ac); return
	 * _random.nextInt(acDefMax + 1); }
	 */

	// �ܡܡܡ� NPC�� ������ ��ҿ� ���� �氨 �ܡܡܡ�
	private int calcNpcDamageReduction() {
		return _targetNpc.getNpcTemplate().get_damagereduction();
	}

	// �ܡܡܡ� ������ ������ �ູ�� ���� �߰� ������ ���� �ܡܡܡ�
	private int calcMaterialBlessDmg() {
		int damage = 0;
		int undead = _targetNpc.getNpcTemplate().get_undead();
		if ((_weaponMaterial == 14 || _weaponMaterial == 17 || _weaponMaterial == 22)
				&& (undead == 1 || undead == 3 || undead == 5)) { // �����̽����������ϸ���,
			// ����, ��
			// ����衤�� �����
			// ����
			damage += _random.nextInt(10) + 20;
		}
		if ((_weaponMaterial == 17 || _weaponMaterial == 22) && undead == 2) {
			damage += _random.nextInt(3) + 1;
		}
		if ((_weaponBless == 0 || _weaponBless == 128)
				&& (undead == 1 || undead == 2 || undead == 3)) { // �ູ ����, ����,
			// ��
			// ����衤�Ǹ��衤��
			// ����� ����
			damage += _random.nextInt(4) + 1;
		}
		if (_pc != null && _pc.getWeapon() != null && _weaponType != 20
				&& _weaponType != 62 && weapon.getHolyDmgByMagic() != 0
				&& (undead == 1 || undead == 3)) {
			damage += weapon.getHolyDmgByMagic();
		}
		return damage;
	}

	private int calcSMaterialBlessDmg() {
		int damage = 0;
		/*
		 * 0:none 1:��ü 2:web 3:�Ĺ��� 4:������ 5:�� 6:�� 7:�� 8:�� 9:�� 10:���� �� 11:ö 12:��ö
		 * 13:�� 14:�� 15:�� 16:�ö�Ƽ�� 17:�̽��� 18:���ũ�̽��� 19:���� 20:���� 21:���� 22:�����ϸ���
		 */
		int undead = _targetNpc.getNpcTemplate().get_undead();
		if ((_SweaponMaterial == 14 || _SweaponMaterial == 17 || _SweaponMaterial == 22)
				&& (undead == 1 || undead == 3 || undead == 5)) { // �����̽����������ϸ���,
			// ����, ��
			// ����衤�� �����
			// ����
			damage += _random.nextInt(10) + 20;
		}
		if ((_SweaponMaterial == 17 || _SweaponMaterial == 22) && undead == 2) {
			damage += _random.nextInt(3) + 1;
		}
		if ((_SweaponBless == 0 || _SweaponBless == 128)
				&& (undead == 1 || undead == 2 || undead == 3)) { // �ູ ����, ����,
			// ��
			// ����衤�Ǹ��衤��
			// ����� ����
			damage += _random.nextInt(4) + 1;
		}
		if (_pc.getSecondWeapon() != null && _SweaponType != 20
				&& _SweaponType != 62 && Sweapon.getHolyDmgByMagic() != 0
				&& (undead == 1 || undead == 3)) {
			damage += Sweapon.getHolyDmgByMagic();
		}
		return damage;
	}

	// �ܡܡܡ� NPC�� �� ������ �߰� ���ݷ��� ��ȭ �ܡܡܡ�
	private boolean isUndeadDamage() {
		return isUndeadDamage(_npc);
	}

	private boolean isUndeadDamage(L1NpcInstance npc) {
		boolean flag = false;
		int undead = npc.getNpcTemplate().get_undead();
		boolean isNight = GameTimeClock.getInstance().getGameTime().isNight();
		if (isNight && (undead == 1 || undead == 3 || undead == 4)) { // 18~6��,
			// ����, ��
			// ����衤��
			// �����
			// ����
			flag = true;
		}
		return flag;
	}

	// �ܡܡܡ� PC�� �������� �ΰ� �ܡܡܡ�
	public void addPcPoisonAttack(L1Character attacker, L1Character target) {
		int chance = _random.nextInt(100) + 1;
		if (chance <= 10
				&& (_weaponId == 13 || _weaponId == 44 || (_weaponId != 0 && _pc
				.getSkillEffectTimerSet().hasSkillEffect(ENCHANT_VENOM)))) {
			L1DamagePoison.doInfection(attacker, target, 2000, 5, 1);
		}
	}

	// �ܡܡܡ� NPC�� �������� �ΰ� �ܡܡܡ�
	private void addNpcPoisonAttack(L1Character attacker, L1Character target) {
		if (_npc.getNpcTemplate().get_poisonatk() != 0) { // ������ �־�
			if (15 >= _random.nextInt(100) + 1) { // 15%�� Ȯ���� ������
				if (_npc.getNpcTemplate().get_poisonatk() == 1) { // ���
					// 3�� �ֱ⿡ ������ 5
					L1DamagePoison.doInfection(attacker, target, 3000, 5);
				} else if (_npc.getNpcTemplate().get_poisonatk() == 2) { // ħ����
					L1SilencePoison.doInfection(target);
				} else if (_npc.getNpcTemplate().get_poisonatk() == 4) { // ����
					// 20�� �Ŀ� 45�ʰ� ����
					L1ParalysisPoison.doInfection(target, 20000, 3000);
				}
			}
		}/*
		 * else if (_npc.getNpcTemplate().get_paralysisatk() != 0) { /// ���� ����
		 * �־� }
		 */
	}

	// ����� ���� ���ǰ� ��ö�� ���� ������ MP����� ���� �����
	public void calcStaffOfMana() {
		// SOM �Ǵ� ��ö�� SOM
		if (_weaponId == 1126 || _weaponId == 126 || _weaponId == 127
				|| _weaponId == 413103 || _weaponId == 100035) {
			int som_lvl = _weaponEnchant + 3; // �ִ� MP������� ����

			if (som_lvl < 1) {
				som_lvl = 1;
			}
			// MP������� �� �� ���
			_drainMana = _random.nextInt(som_lvl) + 1;
			// �ִ� MP������� 9�� ����
			if (_drainMana > Config.MANA_DRAIN_LIMIT_PER_SOM_ATTACK) {
				_drainMana = Config.MANA_DRAIN_LIMIT_PER_SOM_ATTACK;
			}
		}

		if (_weaponId == 412002) { // ������ �ܰ�
			/*
			 * ������ 1�� ���� �Ǵ´� ���� �� �ʿ� ���ٰ� ���� by.���� int som_lvl = _weaponEnchant;
			 * // �ִ� MP������� ����
			 * 
			 * if (som_lvl < 1) { som_lvl = 1; } // MP������� �� �� ��� _drainMana =
			 * _random.nextInt(som_lvl); // �ִ� MP������� 9�� ���� if (_drainMana > 1)
			 * { _drainMana = 1; }
			 */
			_drainMana = 1;
			try {
				if (_calcType == PC_PC
						&& _target.getResistance().getEffectedMrBySkill() >= 100)
					_drainMana = 0;
			} catch (Exception e) {
			}
		}
	}

	// ����� �ĸ��� ��� HP����� ���� �����
	public void calcDrainOfHp() {
		if (_weaponId == 412001 || _weaponId == 1412001 || _weaponId == 12 || _weaponId == 100032 
			|| _weaponId == 450032 || _weaponId == 30088 || _weaponId == 30080 || _weaponId == 31080
			|| _weaponId == 222202 || _weaponId == 222205) { // �Ĵ�,
			
			if (_damage > 0 && _random.nextInt(100) <= 40) {
				_drainHp = _damage / 10;
				if (_drainHp < 1)
					_drainHp = 1;
				else if (_drainHp >= 40)
					_drainHp = 40;
			}
		}
	}

	/* ��������������� ���� ��� �۽� ��������������� */
	public void action() {
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			actionPc();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			actionNpc();
		}
	}

	// �ܡܡܡ� �÷��̾��� ���� ��� �۽� �ܡܡܡ�
	private void actionPc() {
		_pc.getMoveState().setHeading(
				CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // ���⼼Ʈ
		if (_pc.getCurrentWeapon() == 20 || _weaponType == 20) {
			if (_pc instanceof L1RobotInstance || _arrow != null) {
				_pc.getInventory().removeItem(_arrow, 1);
				if (_pc.getGfxId().getTempCharGfx() == 7968
						|| _pc.getGfxId().getTempCharGfx() == 7967) {
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 7972,
							_targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
							_targetId, 7972, _targetX, _targetY, _isHit), true);
				} else if (_pc.getGfxId().getTempCharGfx() == 8842
						|| _pc.getGfxId().getTempCharGfx() == 8900) { // �����
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8904,
							_targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
							_targetId, 8904, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _pc, true);
				} else if (_pc.getGfxId().getTempCharGfx() == 8845
						|| _pc.getGfxId().getTempCharGfx() == 8913) { // ������
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8916,
							_targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
							_targetId, 8916, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _pc, true);
				} else if (_pc.getGfxId().getTempCharGfx() == 13631){
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 13656,
							_targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
							_targetId, 13656, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _pc, true);
				} else if (_pc.getGfxId().getTempCharGfx() == 13346){ //70���ٿ�
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 66,
							_targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
							_targetId, 66, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _pc, true);
				} else if (_pc.getGfxId().getTempCharGfx() == 13635){
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 13658,
							_targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
							_targetId, 13656, _targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _pc, true);
				} else {
					_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 66,
							_targetX, _targetY, _isHit), true);
					Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
							_targetId, 66, _targetX, _targetY, _isHit), true);
				}
				if (_isHit) {
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _pc, true);
				}
			} else if (_weaponId == 190 || _weaponId == 100190 || _weaponId == 7201 || _weaponId == 450029 || _weaponId == 30082
					|| _weaponId == 31082 || _weaponId == 222206) {
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 2349,_targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,_targetId, 2349, _targetX, _targetY, _isHit), true);
				if (_isHit) {
					Broadcaster.broadcastPacketExceptTargetSight(_target,new S_DoActionGFX(_targetId,ActionCodes.ACTION_Damage), _pc, true);
				}
			} else if (_weaponId >= 11011 && _weaponId <= 11013) {
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8771,
						_targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
						_targetId, 8771, _targetX, _targetY, _isHit), true);
				if (_isHit) {
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _pc, true);
				} // �߰�
			} else if (_arrow == null) {
				_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId), true);
				Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc,
						_targetId), true);
			}
		} else if (_weaponType == 62 && _sting != null) {
			_pc.getInventory().removeItem(_sting, 1);
			if (_pc.getGfxId().getTempCharGfx() == 7968
					|| _pc.getGfxId().getTempCharGfx() == 7967) {
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 7972,
						_targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
						_targetId, 7972, _targetX, _targetY, _isHit), true);
			} else if (_pc.getGfxId().getTempCharGfx() == 8842
					|| _pc.getGfxId().getTempCharGfx() == 8900) { // �����
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8904,
						_targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
						_targetId, 8904, _targetX, _targetY, _isHit));
				Broadcaster
				.broadcastPacketExceptTargetSight(_target,
						new S_DoActionGFX(_targetId,
								ActionCodes.ACTION_Damage), _pc, true);
			} else if (_pc.getGfxId().getTempCharGfx() == 8845
					|| _pc.getGfxId().getTempCharGfx() == 8913) { // ������
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 8916,
						_targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
						_targetId, 8916, _targetX, _targetY, _isHit));
				Broadcaster
				.broadcastPacketExceptTargetSight(_target,
						new S_DoActionGFX(_targetId,
								ActionCodes.ACTION_Damage), _pc, true);
			} else {
				_pc.sendPackets(new S_UseArrowSkill(_pc, _targetId, 2989,
						_targetX, _targetY, _isHit), true);
				Broadcaster.broadcastPacket(_pc, new S_UseArrowSkill(_pc,
						_targetId, 2989, _targetX, _targetY, _isHit), true);
			}
			if (_isHit) {
				Broadcaster
				.broadcastPacketExceptTargetSight(_target,
						new S_DoActionGFX(_targetId,
								ActionCodes.ACTION_Damage), _pc, true);
			}
		} else {
			if (_target.getSkillEffectTimerSet().hasSkillEffect(
					ABSOLUTE_BARRIER)
					|| _target.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.STATUS_�������)) {
				_isHit = false;
			}
			byte motion = ActionCodes.ACTION_Attack;
			if (_pc.getGfxId().getTempCharGfx() == 12237
					&& _random.nextInt(100) < 20)
				motion = 30;
			if (_isHit) {
				if (_ũ��Ƽ��) {
					int crigfx = 0;
					/**
					 * 1:�Ѽհ�, 2:�ܰ�, 3:��հ�, 4:Ȱ, 5:â, 6:����, 7:�б�,
					 * 8:throwingknife, 9:arrow, 10:�ǵ鷿, 11:ũ�ο�, 12:�̵���, 13:�Ѽ�Ȱ,
					 * 14:�Ѽ�â, 15:��յ���, 16:��յб�, 17:Ű��ũ 18ü�μҵ�
					 */
					if (_weaponType1 == 1) {// �Ѽհ�
						crigfx = 13411;
					} else if (_weaponType1 == 2) {// �ܰ�
						crigfx = 13412;
					} else if (_weaponType1 == 3) {// ��հ�
						crigfx = 13410;
					} else if (_weaponType1 == 5 || _weaponType1 == 14) {// â
						crigfx = 13402;
					} else if (_weaponType1 == 6 || _weaponType1 == 15) {// ����
						if (Sweapon != null) {
							crigfx = 13415;
						} else {
							crigfx = 13414;
						}
					} else if (_weaponType1 == 7 || _weaponType1 == 16) {// �б�
						crigfx = 13413;
					} else if (_weaponType1 == 11) {// ũ�ο�
						crigfx = 13416;
					} else if (_weaponType1 == 12) {// �̵���
						crigfx = 13417;
					} else if (_weaponType1 == 17) {// Ű��ũ
						crigfx = 13396;
					} else if (_weaponType1 == 18) {// ü�μҵ�
						crigfx = 13409;
					}
					if (_pc.getWeapon() != null) {
						_attackType = 2;
					} else {
						_attackType = 0;
					}
					if (burning) {
						_attackType = 132;
						burning = false;
					}
					_ũ��Ƽ�� = false;
					_pc.sendPackets(new S_AttackPacket(_pc, _targetId, motion,
							_attackType, crigfx), true);
					Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc,
							_targetId, motion, _attackType, crigfx), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _pc, true);
				} else {
					/*
					 * int crigfx = 0; if(_weaponType1 == 17){//Ű��ũ
					 * if(_pc.getWeapon().getItemId() == 4110003){ crigfx =
					 * 6983; }else{ crigfx = 7049; } } if(crigfx != 0){
					 * _pc.sendPackets(new S_AttackPacket(_pc, _targetId,
					 * motion, _attackType, crigfx), true);
					 * Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc,
					 * _targetId, motion, _attackType, crigfx), true); }else{
					 */

					if (_pc.getWeapon() != null) {
						if (_pc.getWeapon().getItem().getType() == 17) {
							if (_pc.getWeapon().getItemId() == 410003) {
								S_SkillSound ss = new S_SkillSound(_pc.getId(),
										6983);
								_pc.sendPackets(ss);
								Broadcaster.broadcastPacket(_pc, ss, true);
							} else {
								S_SkillSound ss = new S_SkillSound(_pc.getId(),
										7049);
								_pc.sendPackets(ss);
								Broadcaster.broadcastPacket(_pc, ss, true);
							}
						}
					}
					if (burning) {
						_attackType = 131;
						burning = false;
					}
					// System.out.println("5555555555555555");
					_pc.sendPackets(new S_AttackPacket(_pc, _targetId, motion,
							_attackType), true);
					Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc,
							_targetId, motion, _attackType), true);
					// }
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _pc, true);
				}
			} else {
				burning = false;
				if (_targetId > 0) {
					if (_pc.getWeapon() != null) {
						if (_pc.getWeapon().getItem().getType() == 17) {
							if (_pc.getWeapon().getItemId() == 410003) {
								S_SkillSound ss = new S_SkillSound(_pc.getId(),
										6983);
								_pc.sendPackets(ss);
								Broadcaster.broadcastPacket(_pc, ss, true);
							} else {
								S_SkillSound ss = new S_SkillSound(_pc.getId(),
										7049);
								_pc.sendPackets(ss);
								Broadcaster.broadcastPacket(_pc, ss, true);
							}
							_pc.sendPackets(new S_AttackMissPacket(_pc,
									_targetId, motion), true);
							Broadcaster.broadcastPacket(_pc,
									new S_AttackMissPacket(_pc, _targetId,
											motion), true);
						} else {
							_pc.sendPackets(new S_AttackMissPacket(_pc,
									_targetId, motion), true);
							Broadcaster.broadcastPacket(_pc,
									new S_AttackMissPacket(_pc, _targetId,
											motion), true);
						}
					} else {
						_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId,
								motion), true);
						Broadcaster.broadcastPacket(_pc,
								new S_AttackMissPacket(_pc, _targetId, motion),
								true);
					}
				} else {
					_pc.sendPackets(new S_AttackPacket(_pc, 0, motion), true);
					Broadcaster.broadcastPacket(_pc, new S_AttackPacket(_pc, 0,
							motion), true);
				}
			}
		}

		if (_isHit) {
			if (_target instanceof L1PcInstance) {
				L1PcInstance target = (L1PcInstance) _target;
				if (target.�����) {
					target.Ÿ��++;
					target.����++;
				}
			}
		} else {
			if (_target instanceof L1PcInstance) {
				L1PcInstance target = (L1PcInstance) _target;
				if (target.�����) {
					target.�̽�++;
					target.����++;
					S_ChatPacket s_chatpacket = new S_ChatPacket(target, "[H:"
							+ target.Ÿ�� + " M:" + target.�̽� + " T:" + target.����
							+ "] TotalDMG : " + target.��Ż������,
							Opcodes.S_SAY, 0);
					Broadcaster.broadcastPacket(target, s_chatpacket, true);
					int heading = target.getMoveState().getHeading();
					if (heading < 7) {
						heading++;
					} else {
						heading = 0;
					}
					target.getMoveState().setHeading(heading);
					Broadcaster.broadcastPacket(target, new S_ChangeHeading(
							target), true);
					// S_DoActionGFX ������ = new
					// S_DoActionGFX(target.getId(),ActionCodes.ACTION_Damage );
					// Broadcaster.broadcastPacket(target, ������);
				}
			}
		}
	}

	// �ܡܡܡ� NPC�� ���� ��� �۽� �ܡܡܡ�
	private void actionNpc() {
		int _npcObjectId = _npc.getId();
		int bowActId = 0;
		int actId = 0;

		_npc.getMoveState().setHeading(
				CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // ���⼼Ʈ

		// Ÿ�ٰ��� �Ÿ��� 2�̻� ������ ���Ÿ� ����
		boolean isLongRange = (_npc.getLocation().getTileLineDistance(
				new Point(_targetX, _targetY)) > 1);
		bowActId = _npc.getNpcTemplate().getBowActId();

		if (_npc.getNpcTemplate().get_gfxid() == 10050) {
			isLongRange = true;
		}

		if (getActId() > 0) {
			actId = getActId();
		} else if (bowActId > 0) {
			actId = 18;

		} else {
			if (_npc.getNpcTemplate().get_gfxid() == 1780) // ȥ�̺�
				actId = 30;
			else
				actId = ActionCodes.ACTION_Attack;
		}

		if (!isLongRange) {
			if (_npc.getNpcTemplate().get_gfxid() == 3412
					|| _npc.getNpcTemplate().get_npcId() == 100055) {
				actId = 30;
			}
		}

		if (isLongRange && bowActId > 0) {
			Broadcaster.broadcastPacket(_npc, new S_UseArrowSkill(_npc,
					_targetId, bowActId, _targetX, _targetY, _isHit), true);

			// Broadcaster.broadcastPacket(_npc, new
			// S_NpcChatPacket(_npc,"1 �� �׼ǰ��� "+actId+"�ε�?"), true);
		} else {
			// Broadcaster.broadcastPacket(_npc, new
			// S_NpcChatPacket(_npc,"2 �� �׼ǰ��� "+actId+"�ε�?"), true);
			if (_isHit) {
				if (getGfxId() > 0) {
					Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(
							_target, _npcObjectId, getGfxId(), _targetX,
							_targetY, actId), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _npc, true);
				} else {
					Broadcaster.broadcastPacket(_npc, new S_AttackPacketForNpc(
							_target, _npcObjectId, actId), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(_targetId,
									ActionCodes.ACTION_Damage), _npc, true);
				}
			} else {
				if (getGfxId() > 0) {
					Broadcaster.broadcastPacket(_npc, new S_UseAttackSkill(
							_target, _npcObjectId, getGfxId(), _targetX,
							_targetY, actId, 0), true);
				} else {
					Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(
							_npc, _targetId, actId), true);
				}
			}
			/*
			 * if(_npc.getNpcTemplate().get_npcId() == 45618) // ���� ��Ÿ�� �˱� �߰�
			 * Broadcaster.broadcastPacket(_npc, new S_SkillSound(_npc.getId(),
			 * 5680), true); else
			 */if (_npc.getNpcTemplate().get_npcId() == 45654) // ���̸��� ��Ÿ ����Ʈ �߰�
				 Broadcaster.broadcastPacket(_npc, new S_SkillSound(
						 _npc.getId(), 7337), true);

		}
	}

	/* ���������������� ��� ��� �ݿ� ���������������� */

	public void commit() {
		if (_isHit) {
			if (_calcType == PC_PC || _calcType == NPC_PC) {
				commitPc();
			} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
				commitNpc();
			}
		}

		// ������ġ �� ������ Ȯ�ο� �޼���
		if (!Config.ALT_ATKMSG) {
			return;
		}
		/*
		 * if (Config.ALT_ATKMSG) { if ((_calcType == PC_PC || _calcType ==
		 * PC_NPC) && !_pc.isGm()) { return; } if ((_calcType == PC_PC ||
		 * _calcType == NPC_PC) && !_targetPc.isGm()) { return; } }
		 */

		if (_target == null)
			return;
		if (_pc != null && _pc.isGm()) {
			StringBuffer sb = new StringBuffer();
			sb.append("\\fT[").append(_pc.getName()).append("] ==> [")
			.append(_target.getName()).append("][== ").append(_damage)
			.append(" DMG ==][HP ").append(_target.getCurrentHp())
			.append("]");
			_pc.sendPackets(new S_SystemMessage(sb.toString()), true);
			sb = null;
		}
		if (_targetPc != null && _targetPc.isGm()) {
			StringBuffer sb = new StringBuffer();
			sb.append("\\fY[").append(_target.getName()).append("] <== [")
			.append((_pc == null ? _npc.getName() : _pc.getName()))
			.append("][== ").append(_damage).append(" DMG ==][HP ")
			.append(_target.getCurrentHp()).append("]");
			_targetPc.sendPackets(new S_SystemMessage(sb.toString()), true);
			sb = null;
		}
		/*
		 * String msg0 = ""; String msg1 = "���� "; String msg2 = ""; String msg3
		 * = ""; String msg4 = ""; if (_calcType == PC_NPC) { // ����Ŀ�� PC�� ���
		 * msg0 = _pc.getName(); } if (_calcType == PC_NPC) { // Ÿ���� NPC�� ���
		 * msg4 = _targetNpc.getName(); msg2 = "HP: " +
		 * _targetNpc.getCurrentHp(); } msg3 = _isHit ? _damage +
		 * "�� �������� �־����ϴ�." : "�̽� �߽��ϴ�."; if (_calcType == PC_NPC) { // ����Ŀ�� PC��
		 * ��� _pc.sendPackets(new S_ServerMessage(166, msg0, msg1, msg2, msg3,
		 * msg4)); // \f1%0%4%1%3 %2 }
		 */
	}

	// �ܡܡܡ� �÷��̾ ��� ����� �ݿ� �ܡܡܡ�
	private void commitPc() {
		if (_calcType == PC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							ABSOLUTE_BARRIER)
							|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
									FREEZING_BREATH)
									|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
											EARTH_BIND)
											|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
													MOB_BASILL) // �ٽǾ󸮱ⵥ����0
													|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
															MOB_COCA)
															|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
																	L1SkillId.STATUS_�������)) { // ��ī�󸮱ⵥ����0
				_damage = 0;
				_drainMana = 0;
				_drainHp = 0;
			} else {
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.VALA_MAAN) // ȭ���� ���� - ����Ȯ���� �����߰�Ÿ��+2
						|| _pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.LIFE_MAAN)) { // ������ ���� - ����Ȯ����
					// �����߰�Ÿ��+2
					int MaanAttDmg = _random.nextInt(100) + 1;
					if (MaanAttDmg <= 30) { // Ȯ��
						_damage += 2;
					}
				}
				if (_drainMana > 0 && _targetPc.getCurrentMp() > 0) {
					if (_drainMana > _targetPc.getCurrentMp()) {
						_drainMana = _targetPc.getCurrentMp();
					}
					short newMp = (short) (_targetPc.getCurrentMp() - _drainMana);
					_targetPc.setCurrentMp(newMp);
					newMp = (short) (_pc.getCurrentMp() + _drainMana);
					_pc.setCurrentMp(newMp);
				}
				/** ������ ���� **/
				if (_drainHp > 0 && _targetPc.getCurrentHp() > 0) {
					if (_drainHp > _targetPc.getCurrentHp()) {
						_drainHp = _targetPc.getCurrentHp();
					}
					short newHp = (short) (_targetPc.getCurrentHp() - _drainHp);
					_targetPc.setCurrentHp(newHp);
					newHp = (short) (_pc.getCurrentHp() + _drainHp);
					_pc.setCurrentHp(newHp);
				}
				/** ������ ���� **/
			}
			// �ٿ���� ������� ��ջ� damagePcWeaponDurability(); // ���⸦ �ջ��Ų��.

			_targetPc.receiveDamage(_pc, _damage, false);
		} else if (_calcType == NPC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							ABSOLUTE_BARRIER)
							|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
									FREEZING_BREATH)
									|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
											EARTH_BIND)
											|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
													MOB_BASILL) // �ٽǾ󸮱ⵥ����0
													|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
															MOB_COCA)
															|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
																	L1SkillId.STATUS_�������)) { // ��ī�󸮱ⵥ����0
				_damage = 0;
			}
			_targetPc.receiveDamage(_npc, _damage, false);
		}
	}

	// �ܡܡܡ� NPC�� ��� ����� �ݿ� �ܡܡܡ�
	private void commitNpc() {
		if (_calcType == PC_NPC) {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
							FREEZING_BREATH)
							|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
									EARTH_BIND)
									|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
											MOB_BASILL) // �ٽǾ󸮱ⵥ����0
											|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
													MOB_COCA)) { // ��ī�󸮱ⵥ����0
				_damage = 0;
				_drainMana = 0;
				_drainHp = 0;
			} else {
				if (_targetNpc instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) _targetNpc;
					if (mon.kir_counter_barrier) {
						commitCounterBarrier();
						return;
					} else if (mon.kir_poison_barrier) {
						if (15 >= _random.nextInt(100) + 1) { // 15%�� Ȯ���� ������
							L1DamagePoison.doInfection(_targetNpc, _pc, 3000,
									100 + _random.nextInt(50));
						}
					} else if (mon.kir_absolute) {
						_damage = 0;
						_drainMana = 0;
						_drainHp = 0;
					}
				}
				if (_drainMana > 0) {
					int drainValue = _targetNpc.drainMana(_drainMana);
					int newMp = _pc.getCurrentMp() + drainValue;
					_pc.setCurrentMp(newMp);

					if (drainValue > 0) {
						int newMp2 = _targetNpc.getCurrentMp() - drainValue;
						_targetNpc.setCurrentMp(newMp2);
					}
				}

				/** ������ ���� **/

				if (_drainHp > 0) {
					int newHp = _pc.getCurrentHp() + _drainHp;
					_pc.setCurrentHp(newHp);
				}
				/** ������ ���� **/
			}
			damageNpcWeaponDurability(); // ���⸦ �ջ��Ų��.
			if (_damage > 0)
				_damage = (int) (_damage * 1.20);
			_targetNpc.receiveDamage(_pc, _damage);
		} else if (_calcType == NPC_NPC) {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
							FREEZING_BREATH)
							|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
									EARTH_BIND)
									|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
											MOB_BASILL) // �ٽǾ󸮱ⵥ����0
											|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
													MOB_COCA)) { // //��ī�󸮱ⵥ����0
				_damage = 0;
			}
			_targetNpc.receiveDamage(_npc, _damage);
		}
	}

	public static double calcMrDefense(int MagicResistance, double dmg) {
		double cc = 0;
		if (MagicResistance <= 19) {
			cc = 0.05;
		} else if (MagicResistance <= 29) {
			cc = 0.07;
		} else if (MagicResistance <= 39) {
			cc = 0.1;
		} else if (MagicResistance <= 49) {
			cc = 0.12;
		} else if (MagicResistance <= 59) {
			cc = 0.17;
		} else if (MagicResistance <= 69) {
			cc = 0.20;
		} else if (MagicResistance <= 79) {
			cc = 0.22;
		} else if (MagicResistance <= 89) {
			cc = 0.25;
		} else if (MagicResistance <= 99) {
			cc = 0.27;
		} else if (MagicResistance <= 110) {
			cc = 0.31;
		} else if (MagicResistance <= 120) {
			cc = 0.32;
		} else if (MagicResistance <= 130) {
			cc = 0.34;
		} else if (MagicResistance <= 140) {
			cc = 0.36;
		} else if (MagicResistance <= 150) {
			cc = 0.38;
		} else if (MagicResistance <= 160) {
			cc = 0.40;
		} else if (MagicResistance <= 170) {
			cc = 0.42;
		} else if (MagicResistance <= 180) {
			cc = 0.44;
		} else if (MagicResistance <= 190) {
			cc = 0.46;
		} else if (MagicResistance <= 200) {
			cc = 0.48;
		} else if (MagicResistance <= 220) {
			cc = 0.49;
		} else {
			cc = 0.51;
		}

		dmg -= dmg * cc;

		if (dmg < 0) {
			dmg = 0;
		}

		return dmg;
	}

	// / * �̺����� ȿ���� ����.

	public void EveilReverse() { // �������� �Ǻ��¹����
		int chance = _random.nextInt(100) + 1;
		if (5 + _weaponEnchant >= chance) { // Ȯ���� 5����
			_drainHp = 30; // �Ǹ� 30���ٴ¾��
			_pc.sendPackets(new S_SkillSound(_target.getId(), 8150), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_target.getId(),
					8150), true);
		}

	}

	public void DrainofEvil() { // ���� Ȱ
		int chance = _random.nextInt(100) + 1;
		if (5 + _weaponEnchant >= chance) { // Ȯ���� 5����
			_drainMana = _random.nextInt(5) + 6; // ���Ǹ� 3���ٴ¾ֱ�
			_pc.sendPackets(new S_SkillSound(_target.getId(), 8152), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_target.getId(),
					8152), true);
		}
	}

	public void DrainofEvil1() { // ���� ������ Ű��ũ
		int chance = _random.nextInt(100) + 1;
		if (12 >= chance) {// Ȯ���� 12����
			_drainMana = _random.nextInt(5) + 6; // ���Ǹ� 6���ٴ¾ֱ�
			_pc.sendPackets(new S_SkillSound(_target.getId(), 8152), true);
			Broadcaster.broadcastPacket(_pc, new S_SkillSound(_target.getId(),
					8152), true);
		}
	}

	public void actionTaitan(int type) {
		int gfx = 0;
		switch (type) {//
		case 0:// ��
			gfx = 12555;
			break;
		case 1:// ����
			gfx = 12559;
			break;
		case 2:// ��
			gfx = 12557;
			break;
		}
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			_pc.getMoveState().setHeading(
					CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // ���⼼Ʈ
			_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId));
			Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc,
					_targetId));
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(),
					ActionCodes.ACTION_Damage));
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(),
					ActionCodes.ACTION_Damage));
			_targetPc.sendPackets(new S_SkillSound(_target.getId(), gfx));
			// Broadcaster.broadcastPacket(_target, new
			// S_SkillSound(_target.getId(), gfx));
		} else if (_calcType == NPC_PC) {
			int actId = 0;
			_npc.getMoveState().setHeading(
					CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // ���⼼Ʈ
			if (getActId() > 0) {
				actId = getActId();
			} else {
				actId = ActionCodes.ACTION_Attack;
			}

			if (getGfxId() > 0) {
				Broadcaster.broadcastPacket(_npc,
						new S_UseAttackSkill(_target, _npc.getId(), getGfxId(),
								_targetX, _targetY, actId, 0), true);
			} else {
				Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc,
						_targetId, actId), true);
			}

			Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(),
					ActionCodes.ACTION_Damage), true);
			// Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId,
			// gfx), true);
			_targetPc.sendPackets(new S_SkillSound(_target.getId(), gfx));
		}
	}

	/* ���������������� ī���� �ٸ��� ���������������� */

	// ����� ī���� �ٸ������ ���� ��� �۽� �����
	public void actionCounterBarrier() {
		if (_calcType == PC_PC || _calcType == PC_NPC) {

			_pc.getMoveState().setHeading(
					CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // ���⼼Ʈ
			_pc.sendPackets(new S_AttackMissPacket(_pc, _targetId), true);
			Broadcaster.broadcastPacket(_pc, new S_AttackMissPacket(_pc,
					_targetId), true);
			_pc.sendPackets(new S_DoActionGFX(_pc.getId(),
					ActionCodes.ACTION_Damage), true);
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(),
					ActionCodes.ACTION_Damage), true);
			_pc.sendPackets(new S_SkillSound(_targetId, 10710), true);
			Broadcaster.broadcastPacket(_pc,
					new S_SkillSound(_targetId, 10710), true);
		} else if (_calcType == NPC_PC) {
			int actId = 0;
			_npc.getMoveState().setHeading(
					CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // ���⼼Ʈ
			if (getActId() > 0) {
				actId = getActId();
			} else {
				actId = ActionCodes.ACTION_Attack;
			}
			if (getGfxId() > 0) {
				Broadcaster.broadcastPacket(_npc,
						new S_UseAttackSkill(_target, _npc.getId(), getGfxId(),
								_targetX, _targetY, actId, 0), true);
			} else {
				Broadcaster.broadcastPacket(_npc, new S_AttackMissPacket(_npc,
						_targetId, actId), true);
			}
			Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(),
					ActionCodes.ACTION_Damage), true);
			Broadcaster.broadcastPacket(_npc,
					new S_SkillSound(_targetId, 10710), true);
		}

	}

	// ����� ��Ż�ٵ� �ߵ����� ���� ��� �۽� �����
	public void actionMortalBody() {
		if (_calcType == PC_PC) {
			_pc.getMoveState().setHeading(
					CharPosUtil.targetDirection(_pc, _targetX, _targetY)); // ���⼼Ʈ

			_pc.sendPackets(new S_DoActionGFX(_pc.getId(),
					ActionCodes.ACTION_Damage));
			Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(),
					ActionCodes.ACTION_Damage));
			_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(), 6519));
			Broadcaster.broadcastPacket(_targetPc,
					new S_SkillSound(_targetPc.getId(), 6519));

			/*
			 * S_UseAttackSkill packet = new S_UseAttackSkill(_pc, _pc.getId(),
			 * 6519, _targetX, _targetY, ActionCodes.ACTION_Attack, false);
			 * _pc.sendPackets(packet); Broadcaster.broadcastPacket(_pc, packet,
			 * true); _pc.sendPackets(new S_DoActionGFX(_pc.getId(),
			 * ActionCodes.ACTION_Damage), true);
			 * Broadcaster.broadcastPacket(_pc, new S_DoActionGFX(_pc.getId(),
			 * ActionCodes.ACTION_Damage), true);
			 */
		} else if (_calcType == NPC_PC) {
			_npc.getMoveState().setHeading(
					CharPosUtil.targetDirection(_npc, _targetX, _targetY)); // ���⼼Ʈ
			Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(_npc.getId(),
					ActionCodes.ACTION_Damage));
			Broadcaster.broadcastPacket(_targetPc,
					new S_SkillSound(_targetPc.getId(), 6519));
		}
	}

	// ����� ���帶���� �ߵ����� ���� ��� �۽� �����
	/*
	 * public void actionLindArmor() { if (_calcType == PC_PC || _calcType ==
	 * PC_NPC) { _pc.getMoveState().setHeading( CharPosUtil.targetDirection(_pc,
	 * _targetX, _targetY)); // ���⼼Ʈ _pc.sendPackets(new S_AttackMissPacket(_pc,
	 * _targetId), true); Broadcaster.broadcastPacket(_pc, new
	 * S_AttackMissPacket(_pc,_targetId), true); _pc.sendPackets(new
	 * S_DoActionGFX(_pc.getId(),ActionCodes.ACTION_Damage), true);
	 * Broadcaster.broadcastPacket(_pc, new
	 * S_DoActionGFX(_pc.getId(),ActionCodes.ACTION_Damage), true);
	 * //Broadcaster.broadcastPacket(_pc, new S_SkillSound(_targetId, 8120));
	 * S_EffectLocation s = new S_EffectLocation(_targetX+1, _targetY, (short)
	 * 8120); _pc.sendPackets(s); Broadcaster.broadcastPacket(_pc, s, true); }
	 * else if (_calcType == NPC_PC) { int actId = 0;
	 * _npc.getMoveState().setHeading(CharPosUtil.targetDirection(_npc,
	 * _targetX, _targetY)); // ���⼼Ʈ if (getActId() > 0) { actId = getActId(); }
	 * else { actId = ActionCodes.ACTION_Attack; } if (getGfxId() > 0) {
	 * Broadcaster.broadcastPacket(_npc, new
	 * S_UseAttackSkill(_target,_npc.getId(), getGfxId(), _targetX,
	 * _targetY,actId, 0), true); } else { Broadcaster.broadcastPacket(_npc, new
	 * S_AttackMissPacket(_npc,_targetId, actId), true); }
	 * Broadcaster.broadcastPacket(_npc, new
	 * S_DoActionGFX(_npc.getId(),ActionCodes.ACTION_Damage), true);
	 * //Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, 4395));
	 * //Broadcaster.broadcastPacket(_npc, new S_SkillSound(_targetId, 8120));
	 * Broadcaster.broadcastPacket(_npc, new S_EffectLocation(_targetX+1,
	 * _targetY, (short) 8120), true); } }
	 */

	// ����� ����� ���ݿ� ���ؼ� ī���� �ٸ�� ��ȿ�Ѱ��� �Ǻ� �����
	public boolean isShortDistance() {
		boolean isShortDistance = true;
		if (_calcType == PC_PC) {
			if (_weaponType == 20 || _weaponType == 62) { // Ȱ�̳� ��Ʈ��Ʈ
				isShortDistance = false;
			}
		} else if (_calcType == NPC_PC) {
			boolean isLongRange = (_npc.getLocation().getTileLineDistance(
					new Point(_targetX, _targetY)) > 1);
			int bowActId = _npc.getNpcTemplate().getBowActId();
			// �Ÿ��� 2�̻�, �������� Ȱ�� �׼� ID�� �ִ� ���� ������
			if (isLongRange && bowActId > 0) {
				isShortDistance = false;
			}
		}
		return isShortDistance;
	}

	// ����� Ÿ��ź �������� �ݿ� �����
	public void commitTaitan(int type) {
		int damage = calcTaitanDamage(type);
		// _pc.sendPackets(new S_SystemMessage("Ÿ��ź �ݻ絥���� :"+damage));
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == PC_NPC) {
			_pc.receiveDamage(_targetNpc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ����� ī���� �ٸ����� �������� �ݿ� �����
	public void commitCounterBarrier() {
		int damage = calcCounterBarrierDamage();
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == PC_NPC) {
			_pc.receiveDamage(_targetNpc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ����� ��Ż�ٵ��� �������� �ݿ� �����
	public void commitMortalBody() {
		int damage = 40;
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// ����� ���� �������� �������� �ݿ� �����
	public void commitLindArmor() {
		int damage = calcLindArmorDamage();
		if (damage == 0) {
			return;
		}
		if (_calcType == PC_PC) {
			_pc.receiveDamage(_targetPc, damage, false);
		} else if (_calcType == PC_NPC) {
			_pc.receiveDamage(_targetNpc, damage, false);
		} else if (_calcType == NPC_PC) {
			_npc.receiveDamage(_targetPc, damage);
		}
	}

	// �ܡܡܡ� ���� �������� �������� ���� �ܡܡܡ�
	private int calcLindArmorDamage() {
		int damage = 0;
		L1ItemInstance weapon = null;

		if (_calcType == PC_PC) {
			weapon = _pc.getWeapon();
		} else if (_calcType == NPC_PC) {
			weapon = _targetPc.getWeapon();
		} else
			damage = 60 + _random.nextInt(41);
		if (weapon != null) {
			if (weapon.getItem().getType1() == 20
					|| weapon.getItem().getType1() == 62) {
				// (ū ���� Ÿ��ġ + �߰� Ÿ�� �ɼ�+ ��æƮ ��ġ ) x 2
				damage = (weapon.getItem().getDmgLarge()
						+ weapon.getEnchantLevel() + weapon.getItem()
						.getDmgModifier()) * 2;
			}
		}
		return damage;
	}

	// �ܡܡܡ� ī���� �ٸ����� �������� ���� �ܡܡܡ�
	private int calcTaitanDamage(int type) {
		int damage = 0;
		L1ItemInstance weapon = null;
		weapon = _targetPc.getWeapon();
		if (weapon != null) {
			// (ū ���� Ÿ��ġ + �߰� Ÿ�� �ɼ�+ ��æƮ ��ġ ) x 2
			damage = (weapon.getItem().getDmgLarge() + weapon.getEnchantLevel() + weapon
					.getItem().getDmgModifier()) * 2;
		}
		return damage;
	}

	// �ܡܡܡ� ī���� �ٸ����� �������� ���� �ܡܡܡ�
	private int calcCounterBarrierDamage() {
		int damage = 0;
		L1ItemInstance weapon = null;

		if (_calcType == NPC_PC || _calcType == PC_PC) {
			weapon = _targetPc.getWeapon();

			if (weapon != null) {
				if (weapon.getItem().getType() == 3) {
					// (ū ���� Ÿ��ġ + �߰� Ÿ�� �ɼ�+ ��æƮ ��ġ ) x 2
					damage = (weapon.getItem().getDmgLarge()
							+ weapon.getEnchantLevel() + weapon.getItem()
							.getDmgModifier()) * 2;
				}
			}
		} else
			damage = 60 + _random.nextInt(41);
		return damage;
	}

	/*
	 * ���⸦ �ջ��Ų��. ��NPC�� ���, �ջ� Ȯ����10%�� �Ѵ�. �ູ �����3%�� �Ѵ�.
	 */

	
	private void damageNpcWeaponDurability() {
		int chance = 10;
		int bchance = 5;

		/*
		 * �ջ����� �ʴ� NPC, �Ǽ�, �ջ����� �ʴ� ���� ���, SOF���� ��� �ƹ��͵� ���� �ʴ´�.
		 */
		if (_calcType != PC_NPC
				|| _targetNpc.getNpcTemplate().is_hard() == false
				|| _weaponType == 0 || weapon.getItem().get_canbedmg() == 0
				|| _pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME)) {
			return;
		}
		// ��ջ�Ȱ��� ����
		/*
		 * if(weapon.getBless() == 0||weapon.getBless() == 128){ return; }
		 */
		// ����� ���⡤�������� ����
		if ((_weaponBless == 1 || _weaponBless == 2)
				&& ((_random.nextInt(100) + 1) < chance)) {
			// \f1�����%0�� �ջ��߽��ϴ�.
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()), true);
			_pc.getInventory().receiveDamage(weapon);
			_pc.sendPackets(new S_SkillSound(_pc.getId(), 10712), true);
		}
		// �ູ�� ����
		if ((_weaponBless == 0 || _weaponBless == 128)
				&& ((_random.nextInt(100) + 1) < bchance)) {
			// \f1�����%0�� �ջ��߽��ϴ�.
			_pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()));
			_pc.getInventory().receiveDamage(weapon);
			_pc.sendPackets(new S_SkillSound(_pc.getId(), 10712), true);
		}
	}

	/*
	 * �ٿ��Źũ�� ���� ���⸦ �ջ��Ų��. �ٿ��Źũ�� �ջ� Ȯ����10%
	 */
	/*
	 * private void damagePcWeaponDurability() { int chance = 5; //int bchance =
	 * 2;
	 * 
	 * // PvP �̿�, �Ǽ�, Ȱ, �� ���� ��, ��밡 �ٿ��Źũ�̻��, SOF���� ��� �ƹ��͵� ���� �ʴ´� if
	 * (_weaponBless == 0 || _weaponBless == 128 ||_calcType != PC_PC ||
	 * _weaponType == 0 || _weaponType == 20 || _weaponType == 62 ||
	 * _targetPc.getSkillEffectTimerSet().hasSkillEffect(BOUNCE_ATTACK) == false
	 * || _pc.getSkillEffectTimerSet().hasSkillEffect(SOUL_OF_FLAME) ||
	 * _targetPc.isParalyzed()) { return; }
	 * 
	 * if ((_weaponBless == 1 || _weaponBless == 2) && ((_random.nextInt(100) +
	 * 1) <= chance)) { // \f1�����%0�� �ջ��߽��ϴ�. _pc.sendPackets(new
	 * S_ServerMessage(268, weapon.getLogName()), true);
	 * _pc.getInventory().receiveDamage(weapon); } }
	 */
	
	
	private int ��Ƽ���ӱ͵���������() {
		int damage = 0;
		if (_calcType == NPC_PC || _calcType == PC_PC) {
			L1ItemInstance item = _targetPc.getInventory().checkEquippedItem(
					500007);
			if (item != null && item.getEnchantLevel() >= 5) {
				if (_random.nextInt(100) < 2 + item.getEnchantLevel() - 5) {
					damage = 20;
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(),
							12118), true);
				}
			}

			L1ItemInstance item2 = _targetPc.getInventory().checkEquippedItem(
					502007);
			if (item2 != null && item2.getEnchantLevel() >= 5) {
				if (_random.nextInt(100) < 2 + item2.getEnchantLevel() - 5) {
					damage = 20;
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(),
							12118), true);
				}
			}
		}
		return damage;
	}
}