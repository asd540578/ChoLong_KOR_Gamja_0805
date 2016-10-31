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
import static l1j.server.server.model.skill.L1SkillId.AM_BREAK;
import static l1j.server.server.model.skill.L1SkillId.AREA_OF_SILENCE;
import static l1j.server.server.model.skill.L1SkillId.ARMOR_BREAK;
import static l1j.server.server.model.skill.L1SkillId.BIRTH_MAAN;
import static l1j.server.server.model.skill.L1SkillId.CANCELLATION;
import static l1j.server.server.model.skill.L1SkillId.CONFUSION;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_MIRROR;
import static l1j.server.server.model.skill.L1SkillId.CURSE_BLIND;
import static l1j.server.server.model.skill.L1SkillId.CURSE_PARALYZE;
import static l1j.server.server.model.skill.L1SkillId.CURSE_POISON;
import static l1j.server.server.model.skill.L1SkillId.DARKNESS;
import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import static l1j.server.server.model.skill.L1SkillId.DISEASE;
import static l1j.server.server.model.skill.L1SkillId.DISINTEGRATE;
import static l1j.server.server.model.skill.L1SkillId.DRAGON_SKIN;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.ELEMENTAL_FALL_DOWN;
import static l1j.server.server.model.skill.L1SkillId.ENTANGLE;
import static l1j.server.server.model.skill.L1SkillId.ERASE_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.FAFU_MAAN;
import static l1j.server.server.model.skill.L1SkillId.FEAR;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_A;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_B;
import static l1j.server.server.model.skill.L1SkillId.FINAL_BURN;
import static l1j.server.server.model.skill.L1SkillId.FIRE_WALL;
import static l1j.server.server.model.skill.L1SkillId.FOG_OF_SLEEPING;
import static l1j.server.server.model.skill.L1SkillId.FOU_SLAYER;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.GUARD_BREAK;
import static l1j.server.server.model.skill.L1SkillId.HORROR_OF_DEATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.IMMUNE_TO_HARM;
import static l1j.server.server.model.skill.L1SkillId.IllUSION_AVATAR;
import static l1j.server.server.model.skill.L1SkillId.JOY_OF_PAIN;
import static l1j.server.server.model.skill.L1SkillId.LIFE_MAAN;
import static l1j.server.server.model.skill.L1SkillId.LIND_MAAN;
import static l1j.server.server.model.skill.L1SkillId.MANA_DRAIN;
import static l1j.server.server.model.skill.L1SkillId.MASS_SLOW;
import static l1j.server.server.model.skill.L1SkillId.MIND_BREAK;
import static l1j.server.server.model.skill.L1SkillId.MOB_BASILL;
import static l1j.server.server.model.skill.L1SkillId.MOB_COCA;
import static l1j.server.server.model.skill.L1SkillId.MORTAL_BODY;
import static l1j.server.server.model.skill.L1SkillId.NATURES_BLESSING;
import static l1j.server.server.model.skill.L1SkillId.PANIC;
import static l1j.server.server.model.skill.L1SkillId.PATIENCE;
import static l1j.server.server.model.skill.L1SkillId.PHANTASM;
import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import static l1j.server.server.model.skill.L1SkillId.REDUCTION_ARMOR;
import static l1j.server.server.model.skill.L1SkillId.RETURN_TO_NATURE;
import static l1j.server.server.model.skill.L1SkillId.SHAPE_CHANGE;
import static l1j.server.server.model.skill.L1SkillId.SHAPE_MAAN;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_STUN;
import static l1j.server.server.model.skill.L1SkillId.SILENCE;
import static l1j.server.server.model.skill.L1SkillId.SLOW;
import static l1j.server.server.model.skill.L1SkillId.SMASH;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_BARLOG;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CURSE_YAHEE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_MITHRIL_POWDER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER_OF_EVA;
import static l1j.server.server.model.skill.L1SkillId.STRIKER_GALE;
import static l1j.server.server.model.skill.L1SkillId.TAMING_MONSTER;
import static l1j.server.server.model.skill.L1SkillId.THUNDER_GRAB;
import static l1j.server.server.model.skill.L1SkillId.TRIPLE_ARROW;
import static l1j.server.server.model.skill.L1SkillId.TURN_UNDEAD;
import static l1j.server.server.model.skill.L1SkillId.WEAKNESS;
import static l1j.server.server.model.skill.L1SkillId.WEAPON_BREAK;
import static l1j.server.server.model.skill.L1SkillId.WIND_SHACKLE;
import static l1j.server.server.model.skill.L1SkillId.데스페라도;
import static l1j.server.server.model.skill.L1SkillId.임팩트;
import static l1j.server.server.model.skill.L1SkillId.파워그립;

import java.util.Random;

import l1j.server.Config;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.ActionCodes;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.utils.CalcStat;

public class L1Magic {

	private int _calcType;

	private final int PC_PC = 1;

	private final int PC_NPC = 2;

	private final int NPC_PC = 3;

	private final int NPC_NPC = 4;

	private L1PcInstance _pc = null;

	private L1PcInstance _targetPc = null;

	private L1NpcInstance _npc = null;

	private L1NpcInstance _targetNpc = null;

	private int _leverage = 13;

	private L1Skills _skill;

	private static Random _random = new Random(System.nanoTime());

	public void setLeverage(int i) {
		_leverage = i;
	}

	private int getLeverage() {
		return _leverage;
	}

	public L1Magic(L1Character attacker, L1Character target) {
		if (attacker instanceof L1PcInstance) {
			if (target instanceof L1PcInstance) {
				_calcType = PC_PC;
				_pc = (L1PcInstance) attacker;
				_targetPc = (L1PcInstance) target;
			} else {
				_calcType = PC_NPC;
				_pc = (L1PcInstance) attacker;
				_targetNpc = (L1NpcInstance) target;
			}
		} else {
			if (target instanceof L1PcInstance) {
				_calcType = NPC_PC;
				_npc = (L1NpcInstance) attacker;
				_targetPc = (L1PcInstance) target;
			} else {
				_calcType = NPC_NPC;
				_npc = (L1NpcInstance) attacker;
				_targetNpc = (L1NpcInstance) target;
			}
		}
	}

	@SuppressWarnings("unused")
	private int getSpellPower() {
		int spellPower = 0;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			spellPower = _pc.getAbility().getSp();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			spellPower = _npc.getAbility().getSp();
		}
		return spellPower;
	}

	private int getMagicLevel() {
		int magicLevel = 0;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			magicLevel = _pc.getAbility().getMagicLevel();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			magicLevel = _npc.getAbility().getMagicLevel();
		}
		return magicLevel;
	}

	private int getMagicBonus() {
		int magicBonus = 0;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			magicBonus = _pc.getAbility().getMagicBonus();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			magicBonus = _npc.getAbility().getMagicBonus();
		}
		return magicBonus;
	}

	private int getLawful() {
		int lawful = 0;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			lawful = _pc.getLawful();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			lawful = _npc.getLawful();
		}
		return lawful;
	}

	private int getTargetMr() {
		int mr = 0;
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			mr = _targetPc.getResistance().getEffectedMrBySkill();
		} else {
			mr = _targetNpc.getResistance().getEffectedMrBySkill();
		}
		return mr;
	}

	/* ■■■■■■■■■■■■■■ 성공 판정 ■■■■■■■■■■■■■ */
	// ●●●● 확률계 마법의 성공 판정 ●●●●
	// 계산방법
	// 공격측 포인트：LV + ((MagicBonus * 3) * 마법 고유 계수)
	// 방어측 포인트：((LV / 2) + (MR * 3)) / 2
	// 공격 성공율：공격측 포인트 - 방어측 포인트
	public boolean calcProbabilityMagic(int skillId) {
		int probability = 0;
		boolean isSuccess = false;

		if (_pc != null && _pc.isGm()) {
			return true;
		}

		if (_calcType == PC_NPC && _targetNpc != null) {
			int npcId = _targetNpc.getNpcTemplate().get_npcId();
			if (npcId >= 45912
					&& npcId <= 45915
					&& !_pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_WATER)) {
				return false;
			}
			if (npcId == 45916
					&& !_pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_MITHRIL_POWDER)) {
				return false;
			}
			if (npcId == 45941
					&& !_pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_WATER_OF_EVA)) {
				return false;
			}
			if (!_pc.getSkillEffectTimerSet().hasSkillEffect(
					STATUS_CURSE_BARLOG)
					&& (npcId == 45752 || npcId == 45753)) {
				return false;
			}
			if (!_pc.getSkillEffectTimerSet()
					.hasSkillEffect(STATUS_CURSE_YAHEE)
					&& (npcId == 45675 || npcId == 81082 || npcId == 45625
							|| npcId == 45674 || npcId == 45685)) {
				return false;
			}
			if (npcId >= 46068 && npcId <= 46091
					&& _pc.getGfxId().getTempCharGfx() == 6035) {
				return false;
			}
			if (npcId >= 46092 && npcId <= 46106
					&& _pc.getGfxId().getTempCharGfx() == 6034) {
				return false;
			}
		}

		if (!checkZone(skillId)) {
			return false;
		}

		if (skillId == CANCELLATION) {
			if (_calcType == PC_PC && _pc != null && _targetPc != null) {
				if (_pc.getId() == _targetPc.getId()) {
					return true;
				}
			
				/*
				 * if (_targetPc.isInvisble()){ _targetPc.delInvis(); return
				 * true; }
				 */

				if (_pc.isInParty()) {
					if (_pc.getParty().isMember(_targetPc)) {
						return true;
					}
				}

				if (CharPosUtil.getZoneType(_pc) == 1
						|| CharPosUtil.getZoneType(_targetPc) == 1) {
					return false;
				}
			}
			if (_calcType == PC_NPC || _calcType == NPC_PC
					|| _calcType == NPC_NPC) {
				return true;
			}
		}

		if (_calcType == PC_NPC
				&& _targetNpc.getNpcTemplate().isCantResurrect()

		) { // 50렙 이상 npc 에게 아래 마법 안걸림:즉 보스몬스터에게 사용불가
			if (skillId == WEAPON_BREAK || skillId == CURSE_PARALYZE
					|| skillId == MANA_DRAIN || skillId == WEAKNESS
					|| skillId == SILENCE || skillId == DISEASE
					|| skillId == DECAY_POTION || skillId == MASS_SLOW
					|| skillId == ERASE_MAGIC || skillId == AREA_OF_SILENCE
					|| skillId == WIND_SHACKLE

					|| skillId == FOG_OF_SLEEPING || skillId == ICE_LANCE
					|| skillId == POLLUTE_WATER || skillId == RETURN_TO_NATURE
					|| skillId == THUNDER_GRAB || skillId == 파워그립) {
				return false;
			}
		}

		/*
		 * if(_calcType == PC_PC){ if(_targetPc.getLevel() <= Config.MAX_LEVEL
		 * || _pc.getLevel() <= Config.MAX_LEVEL){ //레벨65까지 if(_pc.getClanid()
		 * == 0 || _targetPc.getClanid() == 0){///무혈일경우 _skill =
		 * SkillsTable.getInstance().getTemplate(skillId); if (skillId !=
		 * L1Skills.TYPE_CHANGE) { // 버프계 _pc.sendPackets(new
		 * S_SystemMessage("\\fW혈맹이 없거나 레벨"+Config.MAX_LEVEL+"이하라 마법을 실패합니다."));
		 * _targetPc.sendPackets(new
		 * S_SystemMessage("\\fW상대방이 당신에게 마법을 실패하였습니다.")); return false; } } } }
		 */

		if (_calcType == PC_PC) { // 디스중첩 안되게
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(DISINTEGRATE)) {
				if (skillId == DISINTEGRATE) {
					return false;
				}
			}
		}
		// 아스바인드중은 WB, 왈가닥 세레이션 이외 무효
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
				_skill = SkillsTable.getInstance().getTemplate(skillId);
				if (skillId != WEAPON_BREAK && skillId != CANCELLATION // 확률계
						&& _skill.getType() != L1Skills.TYPE_HEAL // 힐 계
						&& _skill.getType() != L1Skills.TYPE_CHANGE) { // 버프계
					return false;
				}
			}
		} else {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
				if (skillId != WEAPON_BREAK && skillId != CANCELLATION) {
					return false;
				}
			}
		}

		// 100% 확률을 가지는 스킬
		if (skillId == SMASH || skillId == MIND_BREAK || skillId == AM_BREAK
				|| skillId == IllUSION_AVATAR /* || skillId == BONE_BREAK */
				|| skillId == RETURN_TO_NATURE || skillId == AREA_OF_SILENCE) {
			return true;
		}
		probability = calcProbability(skillId);
		int rnd = 0;

		if ((skillId == EARTH_BIND || skillId == DARKNESS
				|| skillId == CURSE_BLIND || skillId == CURSE_PARALYZE)) {
			if (_calcType == PC_NPC) {
				if (_targetNpc.getLevel() >= 70) {
					return false;
				}
			}
		}

		switch (skillId) {
		case CANCELLATION:
		case SILENCE:
		case L1SkillId.데스힐:
			if (_calcType == PC_PC) {
				if (_targetPc instanceof L1RobotInstance) {
					if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.ERASE_MAGIC)) {
						probability = 100;
						rnd = 100;
					} else {
						probability = 20;
						rnd = _random.nextInt(_targetPc.getResistance()
								.getEffectedMrBySkill() + 1) + 1;
					}
				}else{
					rnd = _random.nextInt(100) + 1;
				}
					
					
					/* else {
				}
					if ((skillId == SILENCE && _targetPc.getResistance()
							.getEffectedMrBySkill() >= 170)
							|| (skillId == DECAY_POTION && _targetPc
									.getResistance().getEffectedMrBySkill() >= 175)
							|| (skillId == CANCELLATION && _targetPc
									.getResistance().getEffectedMrBySkill() >= 150)
							|| _targetPc.getResistance().getEffectedMrBySkill() >= 160) {
						probability = 0;
						rnd = 100;
					} else {
						rnd = _random.nextInt(_targetPc.getResistance()
								.getEffectedMrBySkill() + 1) + 1;
					}
				}*/

				if (_targetPc.isInvisble()) {
					probability = 0;
				}

			} else if (_calcType == PC_NPC) {
				rnd = 40;
			} else {
				rnd = _random.nextInt(100) + 1;
			}
			break;
		case DECAY_POTION:
		case CURSE_PARALYZE:
		case SLOW:
		case DARKNESS:
		case WEAKNESS:
		case CURSE_POISON:
		case CURSE_BLIND:
		case WEAPON_BREAK:
		case MANA_DRAIN:
			if (_calcType == PC_PC) {
				if (_targetPc instanceof L1RobotInstance) {
					if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.ERASE_MAGIC)) {
						probability = 100;
						rnd = 100;
					} else {
						probability = 20;
						rnd = _random.nextInt(_targetPc.getResistance()
								.getEffectedMrBySkill() + 1) + 1;
					}
				}else{
					rnd = _random.nextInt(100) + 1;
				}
					/* else {
				}
					if ((skillId == SILENCE && _targetPc.getResistance()
							.getEffectedMrBySkill() >= 170)
							|| (skillId == DECAY_POTION && _targetPc
									.getResistance().getEffectedMrBySkill() >= 175)
							|| (skillId == CANCELLATION && _targetPc
									.getResistance().getEffectedMrBySkill() >= 150)
							|| _targetPc.getResistance().getEffectedMrBySkill() >= 160) {
						probability = 0;
						rnd = 100;
					} else {
						rnd = _random.nextInt(_targetPc.getResistance()
								.getEffectedMrBySkill() + 1) + 1;
					}
				}*/
			} else if (_calcType == PC_NPC) {
				rnd = 40;
			} else {
			}
			break;
		default:
			rnd = _random.nextInt(100) + 1;
			if (probability > 80)
				probability = 80;
			/** 아랜 -> 얼녀,아이스데몬 마방120이상 대상에게 안들어가게 **/
			if (skillId == ICE_LANCE && _calcType == NPC_PC
					&& (_npc.getNpcId() == 46141 || _npc.getNpcId() == 46142)
					&& _targetPc.getResistance().getEffectedMrBySkill() >= 100)
				probability = 0;
			else if (skillId == ICE_LANCE && _calcType == NPC_PC
					&& _npc.getNpcId() == 100367)
				probability = 20;
			else if (skillId == ICE_LANCE && _calcType == NPC_PC
					&& (_npc.getNpcId() == 46141 || _npc.getNpcId() == 46142)) {
				probability *= 0.5;
			} else if (skillId == TURN_UNDEAD && _calcType == PC_NPC
					&& _pc != null && !_pc.isWizard())
				probability *= 0.5;

			break;
		}

	/*	if (_calcType == PC_PC || _calcType == PC_NPC) {
			int addprob = CalcStat.마법명중(_pc.getAbility().getTotalInt()) / 2;
			if (probability != 0) {
				probability += addprob;
			}
		}
*/
		if (probability >= rnd) {
			isSuccess = true;
		} else {
			isSuccess = false;
			if (skillId == TURN_UNDEAD && _calcType == PC_NPC
					&& _targetNpc != null) {
				if (_random.nextInt(100) + 1 < 20) {
					if (!_targetNpc.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.HASTE)) {
						// new L1SkillUse().handleCommands(_pc, L1SkillId.HASTE,
						// _targetNpc.getId(), _targetNpc.getX(),
						// _targetNpc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
						_targetNpc.getSkillEffectTimerSet().setSkillEffect(
								L1SkillId.HASTE, 9999 * 1000);
						Broadcaster.broadcastPacket(_targetNpc,
								new S_SkillHaste(_targetNpc.getId(), 1, 0),
								true);
						_targetNpc.getMoveState().setMoveSpeed(1);
						Broadcaster.broadcastPacket(_targetNpc,
								new S_EffectLocation(_targetNpc.getX(),
										_targetNpc.getY(), (short) 8987), true);
					}
				}
			}
		}

		if (!Config.ALT_ATKMSG) {
			return isSuccess;
		}
		if (_targetPc == null && _targetNpc == null)
			return isSuccess;

		String msg2 = "확률:" + probability + "%";
		String msg3 = "";
		if (isSuccess == true) {
			msg3 = "성공";
		} else {
			msg3 = "실패";
		}

		if (_pc != null && _pc.isGm()) {
			_pc.sendPackets(new S_SystemMessage("\\fT["
					+ _pc.getName()
					+ "] ==> ["
					+ (_targetPc == null ? _targetNpc.getName() : _targetPc
							.getName()) + "][== " + msg2 + " ==][" + msg3 + "]"));
		}
		if (_targetPc != null && _targetPc.isGm()) {
			_targetPc.sendPackets(new S_SystemMessage("\\fY["
					+ _targetPc.getName() + "] <== ["
					+ (_pc == null ? _npc.getName() : _pc.getName()) + "][== "
					+ msg2 + " ==][" + msg3 + "]"));
		}
		/*
		 * if (Config.ALT_ATKMSG) { if ((_calcType == PC_PC || _calcType ==
		 * PC_NPC) && !_pc.isGm()) { return isSuccess; } if ((_calcType == PC_PC
		 * || _calcType == NPC_PC) && !_targetPc.isGm()) { return isSuccess; } }
		 * 
		 * String msg0 = ""; String msg1 = "왜"; String msg2 = ""; String msg3 =
		 * ""; String msg4 = "";
		 * 
		 * if (_calcType == PC_PC || _calcType == PC_NPC) { msg0 =
		 * _pc.getName(); } else if (_calcType == NPC_PC) { msg0 =
		 * _npc.getName(); }
		 * 
		 * msg2 = "probability:" + probability + "%"; if (_calcType == NPC_PC ||
		 * _calcType == PC_PC) { msg4 = _targetPc.getName(); } else if
		 * (_calcType == PC_NPC) { msg4 = _targetNpc.getName(); } if (isSuccess
		 * == true) { msg3 = "성공"; } else { msg3 = "실패"; }
		 * 
		 * if (_calcType == PC_PC || _calcType == PC_NPC) { _pc.sendPackets(new
		 * S_ServerMessage(166, msg0, msg1, msg2, msg3, msg4)); } if (_calcType
		 * == NPC_PC || _calcType == PC_PC) { _targetPc.sendPackets(new
		 * S_ServerMessage(166, msg0, msg1, msg2, msg3, msg4)); }
		 */
		return isSuccess;
	}

	private boolean checkZone(int skillId) {
		if (_pc != null && _targetPc != null) {
			if (CharPosUtil.getZoneType(_pc) == 1
					|| CharPosUtil.getZoneType(_targetPc) == 1) {
				if (skillId == WEAPON_BREAK || skillId == SLOW
						|| skillId == CURSE_PARALYZE || skillId == MANA_DRAIN
						|| skillId == DARKNESS || skillId == WEAKNESS
						|| skillId == DISEASE || skillId == DECAY_POTION
						|| skillId == MASS_SLOW || skillId == ENTANGLE
						|| skillId == ERASE_MAGIC || skillId == EARTH_BIND
						|| skillId == AREA_OF_SILENCE
						|| skillId == WIND_SHACKLE || skillId == STRIKER_GALE
						|| skillId == SHOCK_STUN || skillId == 파워그립
						|| skillId == 데스페라도 || skillId == FOG_OF_SLEEPING
						|| skillId == ICE_LANCE || skillId == HORROR_OF_DEATH
						|| skillId == POLLUTE_WATER || skillId == FEAR
						|| skillId == ELEMENTAL_FALL_DOWN
						|| skillId == GUARD_BREAK
						|| skillId == RETURN_TO_NATURE || skillId == PHANTASM
						|| skillId == CONFUSION || skillId == SILENCE
						|| skillId == L1SkillId.데스힐) {
					return false;
				}
			}
		}
		return true;
	}

	private int calcProbability(int skillId) {
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
		int attackLevel = 0;
		int defenseLevel = 0;
		int probability = 0;
		int attackInt = 0;
		int defenseMr = 0;

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			attackLevel = _pc.getLevel();
			attackInt = _pc.getAbility().getTotalInt();
		} else {
			attackLevel = _npc.getLevel();
			attackInt = _npc.getAbility().getTotalInt();
		}

		if (_calcType == PC_PC || _calcType == NPC_PC) {
			defenseLevel = _targetPc.getLevel();
			defenseMr = _targetPc.getResistance().getEffectedMrBySkill();
		} else {
			defenseLevel = _targetNpc.getLevel();
			defenseMr = _targetNpc.getResistance().getEffectedMrBySkill();
			if (skillId == RETURN_TO_NATURE) {
				if (_targetNpc instanceof L1SummonInstance) {
					L1SummonInstance summon = (L1SummonInstance) _targetNpc;
					defenseLevel = summon.getMaster().getLevel();
				}
			}
		}

		switch (skillId) {
		case ERASE_MAGIC:
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 3)  + Config.이레매직확률;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 4) + Config.이레매직확률;
			}
			if (probability > 60) {
				probability = 60;
			}
			if (probability < 30) {
				probability = 30;
			}
			break;

		case STRIKER_GALE:
		case POLLUTE_WATER:
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 3) + 25;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 4) + 25;
			}
			if (probability > 60) {
				probability = 60;
			}
			if (probability < 30) {
				probability = 30;
			}
			break;

		case 데스페라도:
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 5) + Config.데페확률;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 5) + Config.데페확률;
			}
			
			

			if (probability > 90) {
				probability = 90;
			}

			if (probability < 30)
				probability = 30;

			if (_calcType == PC_PC && skillId != AREA_OF_SILENCE) {
				if(_pc.getSkillEffectTimerSet().hasSkillEffect(임팩트) && _pc.임팩트 > 0)
					probability += _pc.임팩트;
				if (_pc instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) _pc;
					L1ItemInstance weapon = _pc.getWeapon();
					int itemId = weapon.getItem().getItemId();
					int Enchant = weapon.getEnchantLevel();
					
				
	
				
					if (pc.get어택레벨() != 0) {
						attackLevel += pc.get어택레벨();
					}
					if (itemId == 7227 && Enchant ==8) {
						probability += 1;
					} else
					if (itemId == 7227 && Enchant ==9) {
						probability += 2;
					} else
					if (itemId == 7227 && Enchant ==10) {
						probability += 3;
					} else
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==0) {
						probability += 5;
					} else
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==1) {
						probability += 6;
					} else
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==2) {
						probability += 7;
					} else 
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==3) {
						probability += 8;
					} else
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==4) {
						probability += 9;
					} else
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==5) {
						probability += 10;
					} 
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==6) {
						probability += 10;
					} 
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==7) {
						probability += 10;
					} 
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==8) {
						probability += 10;
					} 
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==9) {
						probability += 10;
					} 
					if (itemId == 30083 || itemId == 31083 || itemId == 222208 && Enchant ==10) {
						probability += 10;
					} 
			
			
				}
				
				int stunregi = _targetPc.getResistance().gethorror();
				probability -= stunregi;
			}
			break;
		
		
		case 파워그립:
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 5) + Config.그립확률;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 5) + Config.그립확률;
			}

			if (probability > 90) {
				probability = 90;
			}

			if (probability < 40)
				probability = 40;

			if (_calcType == PC_PC && skillId != AREA_OF_SILENCE) {
				
				
				int stunregi = _targetPc.getResistance().getHold();
				probability -= stunregi;
			}

			break;
		case SHOCK_STUN:
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 3) + Config.스턴확률;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 3) + Config.스턴확률;
			}

			if (_calcType == PC_PC || _calcType == PC_NPC) {
				if (_pc instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) _pc;
					L1ItemInstance weapon = _pc.getWeapon();
					int itemId = weapon.getItem().getItemId();
					if (pc.get어택레벨() != 0) {
						attackLevel += pc.get어택레벨();
					}
					if (itemId == 61) {
						probability += 10;
					}
					if (itemId == 30085) {
						probability += 10;
					}
					if (itemId == 222201) {
						probability += 10;
					}
					if (itemId == 59) {
						probability += 5;
					}
					if (itemId == 100059) {
						probability += 5;
					}
				}
			
		
				
				for (L1DollInstance doll : _pc.getDollList()) {
					probability += doll.getStunLevelAdd();
					
				}
				if(_pc.getSkillEffectTimerSet().hasSkillEffect(임팩트) && _pc.임팩트 > 0)
					probability += _pc.임팩트;
			}

			if (probability > 90) {
				probability = 90;
			}
			if (_calcType == PC_PC || _calcType == NPC_PC) {
				int stunregi = _targetPc.getResistance().getStun();
				probability -= stunregi;
			}
			break;
			
			
		case EARTH_BIND:
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 5) + Config.어바확률;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 6) + Config.어바확률;
			}
			if (probability > 90) {
				probability = 90;
			}
			
			if (_calcType == PC_PC && skillId != AREA_OF_SILENCE) {
		
				
				int stunregi = _targetPc.getResistance().getHold();
				probability -= stunregi;
			}
			break;
			
			
		case THUNDER_GRAB:
	
		case AREA_OF_SILENCE:
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 5) + 45;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 5) + 45;
			}
			if (probability > 90) {
				probability = 90;
			}
			if (probability < 40) {
				probability = 40;
			}
			if (_calcType == PC_PC && skillId != AREA_OF_SILENCE) {
				int stunregi = _targetPc.getResistance().getHold();
				probability -= stunregi;
			}
			break;
			
		case ELEMENTAL_FALL_DOWN:
		case RETURN_TO_NATURE:
		case ENTANGLE:
		case WIND_SHACKLE:
			probability = (int) (30 + (attackLevel - defenseLevel) * 2);
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += 2 * _pc.getBaseMagicHitUp();
			}
			break;
		case COUNTER_BARRIER:
			probability = 18; //22
			break;
		case MORTAL_BODY:
			probability = 18;
			break;
		case PANIC:
			probability = (int) (((l1skills.getProbabilityDice()) / 10D) * (attackLevel - defenseLevel))
					+ l1skills.getProbabilityValue();
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += 2 * _pc.getBaseMagicHitUp();
			}
			break;
		case DARKNESS:
		case WEAKNESS:
		case CURSE_PARALYZE:
		case CURSE_POISON:
		case CURSE_BLIND:
		case WEAPON_BREAK:
		case CANCELLATION: {
			if(attackInt > 50)attackInt=50;
			if (attackLevel >= defenseLevel) {
				defenseMr -= (int) ((attackLevel - defenseLevel) * 3); //5
			} else if (attackLevel < defenseLevel) {
				defenseMr -= (int) ((attackLevel - defenseLevel) * 3); //5
			}

			if (_calcType == PC_PC || _calcType == PC_NPC) {
				int 마법명중 = CalcStat.마법명중(_pc.getAbility().getTotalInt());
				if (마법명중 > 14) {
					int temp = 마법명중 - 14;
					defenseMr -= (int) (temp * 1.5); //4
				}
	
				L1ItemInstance run = _pc.getInventory().getItemEquipped(2, 14);
				if (run != null) {
					if (run.getItemId() == 427115) { // 마법 명중 룬
						defenseMr -= 5;
					} else if (run.getItemId() == 427205) { // 장로의 부러진 지팡이
						defenseMr -= 10;
					}
				}
				if (_pc.getInventory().checkEquipped(21269)) {
					defenseMr -= 15;
				}
			}
			//_pc.sendPackets(new S_SystemMessage("최종상대마방 : "+defenseMr));
			if (defenseMr > 150)
				return 0;

			probability = (int) ((attackInt*2) - (defenseMr / 1.1)); //1.3
			
			//_pc.sendPackets(new S_SystemMessage("확률 : "+probability));
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				if (_pc.isElf()) {
					if (_calcType == PC_PC) {
						if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.ERASE_MAGIC)) {
							probability *= 2;
						}
					}
					if (_calcType == PC_NPC) {
						if (_targetNpc.getSkillEffectTimerSet()
								.hasSkillEffect(L1SkillId.ERASE_MAGIC)) {
							probability *= 2;
						}
					}
				}
			}
			//_pc.sendPackets(new S_SystemMessage("확률 : "+probability));
			if (_calcType == PC_NPC) {
				if (_targetNpc.getLevel() >= 70) {
					probability = 0;
				}
			}
			if (skillId == SHAPE_CHANGE) {
				if (_calcType == PC_PC) {
					if (_pc.getId() == _targetPc.getId()) {
						// System.out.println("123");
						probability = 100;
					}
				}
			}
			if (probability < 0)
				probability = 0;
		}
			break;
		case DECAY_POTION:
		case SHAPE_CHANGE:
		case SLOW: {
			if(attackInt > 50)attackInt=50;
			if (attackLevel >= defenseLevel) {
				defenseMr -= (int) ((attackLevel - defenseLevel) * 2); //5
			} else if (attackLevel < defenseLevel) {
				defenseMr -= (int) ((attackLevel - defenseLevel) * 2); //5
			}

			if (_calcType == PC_PC || _calcType == PC_NPC) {
				int 마법명중 = CalcStat.마법명중(_pc.getAbility().getTotalInt());
				if (마법명중 > 14) {
					int temp = 마법명중 - 14;
					defenseMr -= (int) (temp * 1.2); //4
				}
	
				L1ItemInstance run = _pc.getInventory().getItemEquipped(2, 14);
				if (run != null) {
					if (run.getItemId() == 427115) { // 마법 명중 룬
						defenseMr -= 5;
					} else if (run.getItemId() == 427205) { // 장로의 부러진 지팡이
						defenseMr -= 10;
					}
				}
				if (_pc.getInventory().checkEquipped(21269)) {
					defenseMr -= 15;
				}
			}
			//_pc.sendPackets(new S_SystemMessage("최종상대마방 : "+defenseMr));
			if (defenseMr > 160)
				return 0;
			
			probability = (int) ((attackInt*2) - (defenseMr / 1.4)); //1.4
			//
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				if (_pc.isElf()) {
					if (_calcType == PC_PC) {
						if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.ERASE_MAGIC)) {
							probability *= 2;
						}
					}
					if (_calcType == PC_NPC) {
						if (_targetNpc.getSkillEffectTimerSet()
								.hasSkillEffect(L1SkillId.ERASE_MAGIC)) {
							probability *= 2;
						}
					}
				}
			}
			//_pc.sendPackets(new S_SystemMessage("확률 : "+probability));
			if (_calcType == PC_NPC) {
				if (_targetNpc.getLevel() >= 70) {
					probability = 0;
				}
			}
			if (skillId == SHAPE_CHANGE) {
				if (_calcType == PC_PC) {
					if (_pc.getId() == _targetPc.getId()) {
						// System.out.println("123");
						probability = 100;
					}
				}
			}
			if (probability < 0)
				probability = 0;
		}
			break;
		case SILENCE:
		case FOG_OF_SLEEPING:
		case L1SkillId.데스힐:{
			if(attackInt > 50)attackInt=50;
			if (attackLevel >= defenseLevel) {
				defenseMr -= (int) ((attackLevel - defenseLevel) * 3); //5
			} else if (attackLevel < defenseLevel) {
				defenseMr -= (int) ((attackLevel - defenseLevel) * 3); //5
			}

			if (_calcType == PC_PC || _calcType == PC_NPC) {
				int 마법명중 = CalcStat.마법명중(_pc.getAbility().getTotalInt());
				if (마법명중 > 14) {
					int temp = 마법명중 - 14;
					defenseMr -= (int) (temp * 2);
				}
	
				L1ItemInstance run = _pc.getInventory().getItemEquipped(2, 14);
				if (run != null) {
					if (run.getItemId() == 427115) { // 마법 명중 룬
						defenseMr -= 5;
					} else if (run.getItemId() == 427205) { // 장로의 부러진 지팡이
						defenseMr -= 10;
					}
				}
				if (_pc.getInventory().checkEquipped(21269)) {
					defenseMr -= 15;
				}
			}
			
			//_pc.sendPackets(new S_SystemMessage("최종상대마방 : "+defenseMr));
			if (defenseMr > 150)
				return 0;

			probability = (int) ((attackInt*2) - (defenseMr / 1.4));
			//
			// 인트 - mr/5.95 * 9;
			// 인트18 마방 150 인경우
			// 1.2 * 9%
			// 10프로 정도
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				if (_pc.isElf()) {
					if (_calcType == PC_PC) {
						if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.ERASE_MAGIC)) {
							probability *= 2;
						}
					}
					if (_calcType == PC_NPC) {
						if (_targetNpc.getSkillEffectTimerSet()
								.hasSkillEffect(L1SkillId.ERASE_MAGIC)) {
							probability *= 2;
						}
					}
				}
			}
			//_pc.sendPackets(new S_SystemMessage("확률 : "+probability));
			if (_calcType == PC_NPC) {
				if (_targetNpc.getLevel() >= 70) {
					probability = 0;
				}
			}
			if (skillId == SHAPE_CHANGE) {
				if (_calcType == PC_PC) {
					if (_pc.getId() == _targetPc.getId()) {
						// System.out.println("123");
						probability = 100;
					}
				}
			}
			if (probability < 0)
				probability = 0;
		}
			break;
		case MANA_DRAIN: {
			if(attackInt > 50)attackInt=50;
			// if (attackInt > 25)
			// attackInt = 25;

			if (attackLevel >= defenseLevel) {
				defenseMr -= (int) ((attackLevel - defenseLevel) * 5);
			} else if (attackLevel < defenseLevel) {
				defenseMr -= (int) ((attackLevel - defenseLevel) * 5);
			}

			int 마법명중 = CalcStat.마법명중(_pc.getAbility().getTotalInt());
			if (마법명중 > 14) {
				int temp = 마법명중 - 14;
				defenseMr -= (int) (temp * 4);
			}

			L1ItemInstance run1 = _pc.getInventory().getItemEquipped(2, 14);
			if (run1 != null) {
				if (run1.getItemId() == 427115) { // 마법 명중 룬
					defenseMr -= 5;
				} else if (run1.getItemId() == 427205) { // 장로의 부러진 지팡이
					defenseMr -= 10;
				}
			}
			if (_pc.getInventory().checkEquipped(21269)) {
				defenseMr -= 15;
			}
			//_pc.sendPackets(new S_SystemMessage("최종상대마방 : "+defenseMr));
			if (defenseMr > 170)
				return 0;
			probability = (int) ((attackInt*2) - (defenseMr / 1.7));
			//_pc.sendPackets(new S_SystemMessage("확률 : "+probability));
			if (probability < 0)
				probability = 3;
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability += _pc.getBaseMagicHitUp();
			}
		}
			break;
		case GUARD_BREAK:
		case FEAR:
		case HORROR_OF_DEATH:
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 3) + 50;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 4) + 50;
			}
			if (probability > 60) {
				probability = 60;
			}
			if (probability < 30) {
				probability = 30;
			}
			break;

		/*
		 * if (attackLevel >= defenseLevel) { defenseMr -= (int) ((attackLevel -
		 * defenseLevel) * 5); } else if (attackLevel < defenseLevel) {
		 * defenseMr += (int) ((attackLevel - defenseLevel) * 5); }
		 * 
		 * int 마법명중11 = CalcStat.마법명중(_pc.getAbility().getTotalInt()); if(마법명중11
		 * != 0){ defenseMr -= (int) (마법명중11 * 5); }
		 * 
		 * L1ItemInstance run11 = _pc.getInventory().getItemEquipped(2, 14); if
		 * (run11 != null) { if (run11.getItemId() == 427115) { // 마법 명중 룬
		 * defenseMr -= 5; } else if (run11.getItemId() == 427205) { // 장로의 부러진
		 * 지팡이 defenseMr -= 10; } } if (_pc.getInventory().checkEquipped(21269))
		 * { defenseMr -= 15; }
		 * 
		 * probability = 32; if (_calcType == PC_PC) { if
		 * (_targetPc.getResistance().getEffectedMrBySkill() >= 145) probability
		 * = 0; } break;
		 */
		case MASS_SLOW:
			probability = 32;
			break;
		
		/* case BONE_BREAK: 
		// probability = 30; probability +=
		// _random.nextInt(21);
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 5) + 30;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 5) + 30;
			}
			if (probability > 90) {
				probability = 90;
			}
			if (probability < 30) {
				probability = 30;
			}
			break; */
		 
		case PHANTASM:
			probability = 35;
			break;
		case CONFUSION:
			if (attackLevel >= defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 3) + 35;
			} else if (attackLevel < defenseLevel) {
				probability = (int) ((attackLevel - defenseLevel) * 4) + 35;
			}
			if (probability > 60) {
				probability = 60;
			}
			if (probability < 30) {
				probability = 30;
			}
			break;
		case JOY_OF_PAIN:
			probability = 70;
			break;
		case ARMOR_BREAK: //아머
			int level_dif = attackLevel - defenseLevel;
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				for (L1DollInstance doll : _pc.getDollList()) {
					probability += doll.getamorLevelAdd();
					
				}
				if(_pc.getSkillEffectTimerSet().hasSkillEffect(임팩트) && _pc.임팩트 > 0)
					probability += _pc.임팩트;
			}
			
			if (level_dif <= -10)
				probability = 1;
			else if (level_dif == -9)
				probability = 5;
			else if (level_dif == -8)
				probability = 10;
			else if (level_dif == -7)
				probability = 15;
			else if (level_dif == -6)
				probability = 20;
			else if (level_dif == -5)
				probability = 25;
			else if (level_dif == -4)
				probability = 30;
			else if (level_dif == -3)
				probability = 35;
			else if (level_dif == -2)
				probability = 40;
			else if (level_dif == -1)
				probability = 45;
			else if (level_dif == 0)
				probability = 50;  // 원래 40
			else if (level_dif == 1)
				probability = 54;
			else if (level_dif == 2)
				probability = 58;
			else if (level_dif == 3)
				probability = 62;
			else if (level_dif == 4)
				probability = 66;
			else if (level_dif == 5)
				probability = 70;
			else if (level_dif == 6)
				probability = 74;
			else if (level_dif == 7)
				probability = 78;
			else if (level_dif == 8)
				probability = 82;
			else if (level_dif == 9)
				probability = 86;
			else if (level_dif >= 10)
				probability = 90;
			break;
		case TURN_UNDEAD:
			// if (attackInt >25) attackInt = 25;
			// if (attackLevel > 52) attackLevel = 52;
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				probability = (int) ((attackInt * 2 + (attackLevel * 2.5) + _pc
						.getBaseMagicHitUp())
						- (defenseMr + (defenseLevel / 2)) - 84);
				if (!_pc.isWizard()) {
					probability -= 30;
				}
			} else
				probability = (int) ((attackInt * 2 + (attackLevel * 2.5))
						- (defenseMr + (defenseLevel / 2)) - 84);

			if (attackInt >= 35) {
				int addpro = 0;
				if (attackInt == 35)
					addpro += 4;
				if (attackInt == 40)
					addpro += 4;
				if (attackInt == 45)
					addpro += 4;
				if (attackInt == 50)
					addpro += 4;
				if (attackInt == 55)
					addpro += 4;
				if (attackInt == 60)
					addpro += 4;
				probability += addpro;
			}

			/*
			 * if(_pc.getLevel() >= 75){ int addpro = (_pc.getLevel() - 74) * 2;
			 * if(addpro > 16)addpro = 16; probability += addpro; }
			 */

			if (probability > 0) {
				L1ItemInstance run = _pc.getInventory().getItemEquipped(2, 14);
				if (run != null) {
					if (run.getItemId() == 427115) { // 마법 명중 룬
						probability += 5;
					} else if (run.getItemId() == 427205) { // 장로의 부러진 지팡이
						probability += 10;
					}
				}
				if (_pc.getInventory().checkEquipped(21269)) {
					probability += 15;
				}
			}

			break;
		default: {
			int dice1 = l1skills.getProbabilityDice();
			int diceCount1 = 0;
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				if (_pc.isWizard()) {
					diceCount1 = getMagicBonus() + getMagicLevel() + 1;
				} else if (_pc.isElf()) {
					diceCount1 = getMagicBonus() + getMagicLevel() - 1;
				} else if (_pc.isDragonknight()) {
					diceCount1 = getMagicBonus() + getMagicLevel();
				} else {
					diceCount1 = getMagicBonus() + getMagicLevel() - 1;
				}
			} else {
				diceCount1 = getMagicBonus() + getMagicLevel();
			}
			if (diceCount1 < 1) {
				diceCount1 = 1;
			}
			for (int i = 0; i < diceCount1; i++) {
				probability += (_random.nextInt(dice1) + 1);
			}
			probability = probability * getLeverage() / 10;
			probability -= getTargetMr();

			if (skillId == TAMING_MONSTER) {
				double probabilityRevision = 1;
				if ((_targetNpc.getMaxHp() * 1 / 4) > _targetNpc.getCurrentHp()) {
					probabilityRevision = 1.3;
				} else if ((_targetNpc.getMaxHp() * 2 / 4) > _targetNpc
						.getCurrentHp()) {
					probabilityRevision = 1.2;
				} else if ((_targetNpc.getMaxHp() * 3 / 4) > _targetNpc
						.getCurrentHp()) {
					probabilityRevision = 1.1;
				}
				probability *= probabilityRevision;
			}
		}

			break;
		}
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			if (probability > 0) {
				if (skillId != L1SkillId.SHOCK_STUN
						&& skillId != L1SkillId.데스페라도
						&& skillId != L1SkillId.파워그립) {
					L1ItemInstance run = _pc.getInventory().getItemEquipped(2,
							14);
					if (run != null) {
						if (run.getItemId() == 427115) { // 마법 명중 룬
							probability += 1;
						} else if (run.getItemId() == 427205) { // 장로의 부러진 지팡이
							probability += 2;
						}
					}
					if (_pc.getInventory().checkEquipped(21269)) {
						probability += 3;
					}
				}
			}
		}
		return probability;
	}

	public int calcMagicDamage(int skillId) {
		int damage = 0;
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			damage = calcPcMagicDamage(skillId);
			if (_calcType == PC_PC) {
				if (_pc.getClanid() > 0
						&& (_pc.getClanid() == _targetPc.getClanid())) {
					if (skillId == 17 || skillId == 22 || skillId == 25
							|| skillId == 53 || skillId == 53 || skillId == 59
							|| skillId == 62 || skillId == 65 || skillId == 70
							|| skillId == 74) { // 미티어 포함한 범위마법들..
						damage = 0;
					}
				}
			}
		} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
			damage = calcNpcMagicDamage(skillId);
			// ////////혈원에게는 범위마법 데미지 0 디플추가////////////
			// ////////혈원에게는 범위마법 데미지 0 디플추가끝///////////
		}

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			if (skillId == L1SkillId.ENERGY_BOLT
					&& _pc.getInventory().checkEquipped(120)) {
				damage += 10;
			}

			int tempsp = _pc.getAbility().getSp();

			if (tempsp >= 33) {
				int temp2sp = tempsp / 3;
				damage += damage * (temp2sp * 0.01);
			}

			damage += CalcStat.마법대미지(_pc.getAbility().getTotalInt());

			double balance = CalcStat.마법보너스(_pc.getAbility().getTotalInt()) * 0.01;

			damage += damage * balance;
		}

		// 파푸가호
		if (_targetPc != null) {
			// Random random = new Random();
			int chance1 = _random.nextInt(100) + 1;
			if ((_targetPc.getInventory().checkEquipped(420104)
					|| _targetPc.getInventory().checkEquipped(420105)
					|| _targetPc.getInventory().checkEquipped(420106) || _targetPc
					.getInventory().checkEquipped(420107)) && chance1 < 5) {
				// 123456 일때 80~100
				// 파푸 가호 7일때 120~140 / 8일때 140~160 9일때 160~180
				int addhp = _random.nextInt(20) + 1;
				int basehp = 80;

				L1ItemInstance item = null;
				if (_targetPc.getInventory().checkEquipped(420104)) {
					item = _targetPc.getInventory().checkEquippedItem(420104);
				}
				if (_targetPc.getInventory().checkEquipped(420105)) {
					item = _targetPc.getInventory().checkEquippedItem(420105);
				}
				if (_targetPc.getInventory().checkEquipped(420106)) {
					item = _targetPc.getInventory().checkEquippedItem(420106);
				}
				if (_targetPc.getInventory().checkEquipped(420107)) {
					item = _targetPc.getInventory().checkEquippedItem(420107);
				}

				if (item.getEnchantLevel() == 7)
					basehp = 120;
				if (item.getEnchantLevel() == 8)
					basehp = 140;
				if (item.getEnchantLevel() == 9)
					basehp = 160;

				_targetPc.setCurrentHp(_targetPc.getCurrentHp() + basehp
						+ addhp);
				_targetPc
						.sendPackets(new S_SkillSound(_targetPc.getId(), 2187));
				Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(
						_targetPc.getId(), 2187));
			} else if (_targetPc.getInventory().checkEquipped(21255)
					&& chance1 < 4) {
				_targetPc.setCurrentHp(_targetPc.getCurrentHp() + 31);
				_targetPc
						.sendPackets(new S_SkillSound(_targetPc.getId(), 2183));
				Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(
						_targetPc.getId(), 2183));
			}
		}
		// 파푸가호

		/** 파번은 마방 공식 제외 (임시) */
		if (skillId != FINAL_BURN) {
			damage = calcMrDefense(damage);
		} else if (skillId == FINAL_BURN && _targetPc != null) { // final burn's
																	// temporary
																	// damage
			damage = _pc.getCurrentMp() / 2;

			/*
			 * if(_targetPc.getResistance().getEffectedMrBySkill() <= 50) damage
			 * = _pc.getCurrentMp() + _random.nextInt(_pc.getCurrentMp()/2+1);
			 * else if(_targetPc.getResistance().getEffectedMrBySkill() > 50 &&
			 * _targetPc.getResistance().getEffectedMrBySkill() < 100) damage =
			 * _pc.getCurrentMp() - _random.nextInt(_pc.getCurrentMp()/2+1);
			 * else if(_targetPc.getResistance().getEffectedMrBySkill() > 100)
			 * damage = _random.nextInt(_pc.getCurrentMp()/2+1);
			 */
		}

		// 피가 1에서 안달던 부분 수정.
		/*
		 * if (_calcType == PC_PC || _calcType == NPC_PC) { if (damage >
		 * _targetPc.getCurrentHp()) { damage = _targetPc.getCurrentHp(); } }
		 * else { if (damage > _targetNpc.getCurrentHp()) { damage =
		 * _targetNpc.getCurrentHp(); } }
		 */
		return damage;
	}

	private int calcTaitanDamage(int type) {
		int damage = 0;
		L1ItemInstance weapon = null;
		weapon = _targetPc.getWeapon();
		if (weapon != null) {
			// (큰 몬스터 타격치 + 추가 타격 옵션+ 인챈트 수치 ) x 2
			damage = (weapon.getItem().getDmgLarge() + weapon.getEnchantLevel() + weapon
					.getItem().getDmgModifier()) * 2;
		}
		return damage;
	}

	public int calcPcFireWallDamage() {
		int dmg = 0;
		double attrDeffence = calcAttrResistance(L1Skills.ATTR_FIRE);
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(FIRE_WALL);
		dmg = (int) ((1.0 - attrDeffence) * l1skills.getDamageValue());

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						FREEZING_BREATH)
				|| _targetPc.getSkillEffectTimerSet()
						.hasSkillEffect(EARTH_BIND)
				|| _targetPc.getSkillEffectTimerSet()
						.hasSkillEffect(MOB_BASILL)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.STATUS_안전모드)) {
			dmg = 0;
		}

		if (dmg < 0) {
			dmg = 0;
		}

		return dmg;
	}

	public int calcNpcFireWallDamage() {
		int dmg = 0;
		double attrDeffence = calcAttrResistance(L1Skills.ATTR_FIRE);
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(FIRE_WALL);
		dmg = (int) ((1.0 - attrDeffence) * l1skills.getDamageValue());

		if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
						FREEZING_BREATH)
				|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
						EARTH_BIND)
				|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
						MOB_BASILL)
				|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) {
			dmg = 0;
		}

		if (dmg < 0) {
			dmg = 0;
		}

		return dmg;
	}

	private int calcPcMagicDamage(int skillId) {
		int dmg = 0;
		if (skillId == FINAL_BURN) {
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				dmg = _pc.getCurrentMp() / 2;
			} else {
				dmg = _pc.getCurrentMp() / 2;
			}
		} else {
			// if (_calcType == PC_PC) {
			dmg = calcMagicDiceDamage(skillId);
			dmg = (dmg * getLeverage()) / 10;
			// }else if (_calcType == NPC_PC) {
			// dmg = calcMagicDiceDamage(skillId);
			// dmg = (dmg * getLeverage()) / 10;
			// }
		}
		dmg -= _targetPc.getDamageReductionByArmor();

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)) { // 스페셜요리에
																					// 의한
																					// 데미지
																					// 경감
			dmg -= 5;
		}
		if (_targetPc.isAmorGaurd) { // 아머가드에의한 데미지감소
			int d = _targetPc.getAC().getAc() / 10;
			if (d < 0) {
				dmg += d;
			} else {
				dmg -= d;
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = _targetPc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			dmg -= (targetPcLvl - 50) / 5 + 1;
		}

		if (_calcType == NPC_PC) {
			boolean isNowWar = false;
			int castleId = L1CastleLocation.getCastleIdByArea(_targetPc);
			if (castleId > 0) {
				isNowWar = WarTimeController.getInstance().isNowWar(castleId);
			}
			if (!isNowWar) {
				if (_npc instanceof L1PetInstance) {
					dmg /= 8;
				}
				if (_npc instanceof L1SummonInstance) {
					L1SummonInstance summon = (L1SummonInstance) _npc;
					if (summon.isExsistMaster()) {
						dmg /= 8;
					}
				}
			}
			// Object[] dollList = _targetPc.getDollList().values().toArray();
			// // 마법 인형에 의한 추가 방어
			// L1DollInstance doll = null;
			for (L1DollInstance doll : _targetPc.getDollList()) {
				// doll = (L1DollInstance) dollObject;
				dmg -= doll.getDamageReductionByDoll();
			}

			// dmg -= dmg*0.05;
			// 몹 스킬 데미지 관련
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
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(IMMUNE_TO_HARM)) {
			dmg -= (dmg*Config.뮨데미지);
		}

		/*
		 * if (_calcType == PC_NPC) {//pc -> npc 데미지 하향 dmg -= dmg*0.3; }
		 */

		/*
		 * if (_targetPc != null) { int chance = _random.nextInt(100); if
		 * (_targetPc.getInventory().checkEquipped(420108) ||
		 * _targetPc.getInventory().checkEquipped(420109) ||
		 * _targetPc.getInventory().checkEquipped(420110) ||
		 * _targetPc.getInventory().checkEquipped(420111)) { // 린드비오르 3차 마갑주 if
		 * (chance <= 15){ // 20% 확률 dmg /= 2; _targetPc.sendPackets(new
		 * S_SystemMessage("린드비오르의 가호를 받았습니다.")); // _targetPc.sendPackets(new
		 * S_SkillSound(_targetPc.getId() , 2188));//임팩트 //
		 * _targetPc.broadcastPacket(new S_SkillSound(_targetPc.getId(), 2188));
		 * } } }
		 */

		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
				|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)) {
			dmg = 0;
		}
		
		
		if (_calcType == PC_PC) {
			if (_pc.getMapId() == 5153) {
				if (_pc.get_DuelLine() == _targetPc.get_DuelLine() || _pc.get_DuelLine() == 0) {
					dmg = 0;
				}
			}
		}
		/*
		 * if(_calcType == PC_PC){ if(_targetPc.getLevel() <= Config.MAX_LEVEL
		 * || _pc.getLevel() <= Config.MAX_LEVEL){ //레벨65까지 if(_pc.getClanid()
		 * == 0 || _targetPc.getClanid() == 0){///무혈일경우 dmg = 0;
		 * _pc.sendPackets(new
		 * S_SystemMessage("\\fW혈맹이 없거나 레벨"+Config.MAX_LEVEL+
		 * "이하라 공격마법을 실패합니다.")); _targetPc.sendPackets(new
		 * S_SystemMessage("\\fW상대방의 마법공격을 보호받고 있습니다.")); } } }
		 */

		if (_targetPc.isTaitanM) {
			int hpRatio = 100;
			if (0 < _targetPc.getMaxHp()) {
				hpRatio = 100 * _targetPc.getCurrentHp() / _targetPc.getMaxHp();
			}
			int rock=0;
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.타이탄라이징)){ //타이탄라이징
				rock = 5;
				if(_targetPc.getLevel() > 80)
					rock += _targetPc.getLevel()-80;
				if(rock > 10)
					rock = 10;														
			}
			if (hpRatio < 41 + rock) {
				int chan = _random.nextInt(100) + 1;
				boolean isProbability = false;
				if (_targetPc.getInventory().checkItem(41246, 10)) {
					if (25 > chan) { //원래 30
						isProbability = true;
						_targetPc.getInventory().consumeItem(41246, 10);
					}
				}
				if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.SHOCK_STUN)
						|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.EARTH_BIND)) {
					isProbability = false;
				}

				if (skillId == SHOCK_STUN || skillId == FOU_SLAYER
						|| skillId == TRIPLE_ARROW) {
					isProbability = false;
				}

				if (isProbability) {
					if (_calcType == PC_PC) {
						_pc.sendPackets(new S_DoActionGFX(_pc.getId(),
								ActionCodes.ACTION_Damage));
						Broadcaster.broadcastPacket(_pc,
								new S_DoActionGFX(_pc.getId(),
										ActionCodes.ACTION_Damage));
						_targetPc.sendPackets(new S_SkillSound(_targetPc
								.getId(), 12559));
						// Broadcaster.broadcastPacket(_targetPc, new
						// S_SkillSound(_targetPc.getId(), 12559));
						_pc.skillismiss = true;
						_pc.receiveDamage(_targetPc, calcTaitanDamage(0), false);
						dmg = 0;
					} else if (_calcType == NPC_PC) {
						int npcId = _npc.getNpcTemplate().get_npcId();
						if (npcId == 45681 || npcId == 45682 || npcId == 45683
								|| npcId == 45684) {
						} else if (!_npc.getNpcTemplate().get_IsErase()) {
						} else {
							Broadcaster.broadcastPacket(_npc,
									new S_DoActionGFX(_npc.getId(),
											ActionCodes.ACTION_Damage));
							_targetPc.sendPackets(new S_SkillSound(_targetPc
									.getId(), 12559));
							// Broadcaster.broadcastPacket(_targetPc, new
							// S_SkillSound(_targetPc.getId(), 12559));
							_npc.receiveDamage(_targetPc, calcTaitanDamage(0));
							dmg = 0;
						}
					}
				}
			}
		} else if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
				MORTAL_BODY)) {
			int chan = _random.nextInt(100) + 1;
			boolean isProbability = false;
			if (18 > chan) {
				isProbability = true;
			}
			if (isProbability) {
				if (_calcType == PC_PC) {
					_pc.sendPackets(new S_DoActionGFX(_pc.getId(),
							ActionCodes.ACTION_Damage));
					Broadcaster.broadcastPacket(_pc,
							new S_DoActionGFX(_pc.getId(),
									ActionCodes.ACTION_Damage));
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(),
							6519));
					Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(
							_targetPc.getId(), 6519));
					_pc.receiveDamage(_targetPc, 40, false);
					// dmg = 0;
				} else if (_calcType == NPC_PC) {
					int npcId = _npc.getNpcTemplate().get_npcId();
					if (npcId == 45681 || npcId == 45682 || npcId == 45683
							|| npcId == 45684) {
					} else if (!_npc.getNpcTemplate().get_IsErase()) {
					} else {
						_npc.sendPackets(new S_DoActionGFX(_npc.getId(),
								ActionCodes.ACTION_Damage));
						Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(
								_npc.getId(), ActionCodes.ACTION_Damage));
						_targetPc.sendPackets(new S_SkillSound(_targetPc
								.getId(), 6519));
						Broadcaster.broadcastPacket(_targetPc,
								new S_SkillSound(_targetPc.getId(), 6519));
						_npc.receiveDamage(_targetPc, 40);
						// dmg = 0;
					}
				}
			}
		} else if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
				COUNTER_MIRROR)) {
			if (_calcType == PC_PC) {
				if (_targetPc.getAbility().getTotalWis() >= _random
						.nextInt(100)) {
					_pc.sendPackets(new S_DoActionGFX(_pc.getId(),
							ActionCodes.ACTION_Damage));
					Broadcaster.broadcastPacket(_pc,
							new S_DoActionGFX(_pc.getId(),
									ActionCodes.ACTION_Damage));
					_targetPc.sendPackets(new S_SkillSound(_targetPc.getId(),
							4395));
					Broadcaster.broadcastPacket(_targetPc, new S_SkillSound(
							_targetPc.getId(), 4395));
					dmg = dmg / 2;
					_pc.receiveDamage(_targetPc, dmg, false);
					dmg = 0;
					_targetPc.getSkillEffectTimerSet().killSkillEffectTimer(
							COUNTER_MIRROR);
				}
			} else if (_calcType == NPC_PC) {
				int npcId = _npc.getNpcTemplate().get_npcId();
				if (npcId == 45681 || npcId == 45682 || npcId == 45683
						|| npcId == 45684) {
				} else if (!_npc.getNpcTemplate().get_IsErase()) {
				} else {
					if (_targetPc.getAbility().getTotalWis() >= _random
							.nextInt(100)) {
						Broadcaster.broadcastPacket(_npc, new S_DoActionGFX(
								_npc.getId(), ActionCodes.ACTION_Damage));
						_targetPc.sendPackets(new S_SkillSound(_targetPc
								.getId(), 4395));
						Broadcaster.broadcastPacket(_targetPc,
								new S_SkillSound(_targetPc.getId(), 4395));
						_npc.receiveDamage(_targetPc, dmg);
						dmg = 0;
						_targetPc.getSkillEffectTimerSet()
								.killSkillEffectTimer(COUNTER_MIRROR);
					}
				}
			}
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_A)) {
			dmg -= 3;
		}
		if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_B)) {
			dmg -= 2;
		}
		
		if (_calcType == PC_PC) {
			if(_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_2) || _targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_3) ||
					_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_4)) {
				dmg -= 2;
			}
			if(_targetPc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.정상의가호))
				dmg -= 8;
		}
		
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FAFU_MAAN) // 수룡의
																				// 마안
																				// -
																				// 마법데미지
																				// 50%감소
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							LIFE_MAAN) // 생명의 마안 - 마법데미지 50%감소
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							SHAPE_MAAN) // 형상의 마안 - 마법데미지 50%감소
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							BIRTH_MAAN)) { // 탄생의 마안 - 마법데미지 50%감소
				int MaanMagicCri = _random.nextInt(100) + 1;
				if (MaanMagicCri <= 35) { // 확률
					dmg /= 2;
				}
			}
		}

		if (dmg < 0) {
			dmg = 0;
		}
		return dmg;
	}

	private int calcNpcMagicDamage(int skillId) {
		int dmg = 0;
		if (skillId == FINAL_BURN) {
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				dmg = _pc.getCurrentMp() / 2;
			} else {
				dmg = _pc.getCurrentMp() / 2;
			}
		} else {
			dmg = calcMagicDiceDamage(skillId);
			dmg = (dmg * getLeverage()) / 10;
		}
		if (_targetNpc.getNpcId() == 45640) {
			dmg /= 2;
		}
		if (_calcType == PC_NPC) {
			boolean isNowWar = false;
			int castleId = L1CastleLocation.getCastleIdByArea(_targetNpc);
			if (castleId > 0) {
				isNowWar = WarTimeController.getInstance().isNowWar(castleId);
			}
			if (!isNowWar) {
				if (_targetNpc instanceof L1PetInstance) {
					dmg /= 8;
				}
				if (_targetNpc instanceof L1SummonInstance) {
					L1SummonInstance summon = (L1SummonInstance) _targetNpc;
					if (summon.isExsistMaster()) {
						dmg /= 8;
					}
				}
			}
		}

		if (_calcType == PC_PC || _calcType == NPC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(FAFU_MAAN) // 수룡의
																				// 마안
																				// -
																				// 마법데미지
																				// 50%감소
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							LIFE_MAAN) // 생명의 마안 - 마법데미지 50%감소
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							SHAPE_MAAN) // 형상의 마안 - 마법데미지 50%감소
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							BIRTH_MAAN)) { // 탄생의 마안 - 마법데미지 50%감소
				int MaanMagicCri = _random.nextInt(100) + 1;
				if (MaanMagicCri <= 35) { // 확률
					dmg /= 2;
				}
			}
		}

		if (_calcType == PC_NPC && _targetNpc != null) {
			int npcId = _targetNpc.getNpcTemplate().get_npcId();
			if (npcId >= 45912
					&& npcId <= 45915
					&& !_pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_WATER)) {
				dmg = 0;
			}
			if (npcId == 45916
					&& !_pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_MITHRIL_POWDER)) {
				dmg = 0;
			}
			if (npcId == 45941
					&& !_pc.getSkillEffectTimerSet().hasSkillEffect(
							STATUS_HOLY_WATER_OF_EVA)) {
				dmg = 0;
			}
			if (!_pc.getSkillEffectTimerSet().hasSkillEffect(
					STATUS_CURSE_BARLOG)
					&& (npcId == 45752 || npcId == 45753)) {
				dmg = 0;
			}
			if (!_pc.getSkillEffectTimerSet()
					.hasSkillEffect(STATUS_CURSE_YAHEE)
					&& (npcId == 45675 || npcId == 81082 || npcId == 45625
							|| npcId == 45674 || npcId == 45685)) {
				dmg = 0;
			}
			if (npcId >= 46068 && npcId <= 46091
					&& _pc.getGfxId().getTempCharGfx() == 6035) {
				dmg = 0;
			}
			if (npcId >= 46092 && npcId <= 46106
					&& _pc.getGfxId().getTempCharGfx() == 6034) {
				dmg = 0;
			}

			if (dmg != 0 && _targetNpc.getNpcTemplate().is__MagicBarrier()) {
				int mbrnd = _random.nextInt(100);
				if (mbrnd == 1) {
					_pc.receiveDamage(_targetNpc, dmg, 0);
				}
			}
			// System.out.println("pc -> npc 정상 데미지 : "+dmg);
			dmg -= dmg * 0.3;
			// System.out.println("pc -> npc 30%하향후   데미지 : "+dmg);
		}

		return dmg;
	}

	private int calcMagicDiceDamage(int skillId) {
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
		int dice = l1skills.getDamageDice();
		int diceCount = l1skills.getDamageDiceCount();
		int value = l1skills.getDamageValue();
		int magicDamage = 0;
		int charaIntelligence = 0;
		Random random = new Random();

		for (int i = 0; i < diceCount; i++) {
			magicDamage += (_random.nextInt(dice) + 1);
		}
		magicDamage += value;

		// 크리 50% 증가 10%확률
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			if (skillId == L1SkillId.DISINTEGRATE
					|| skillId == L1SkillId.SUNBURST
					|| skillId == L1SkillId.ERUPTION
					|| skillId == L1SkillId.CONE_OF_COLD
					|| skillId == L1SkillId.CALL_LIGHTNING) {
				int crirnd = 10;
				crirnd += CalcStat.마법치명타(_pc.getAbility().getTotalInt());
				if (_random.nextInt(100) < crirnd) {
					_pc.skillCritical = true;
					magicDamage *= 1.4; // 1.4배
				}
			}
		}

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			int weaponAddDmg = 0;
			L1ItemInstance weapon = _pc.getWeapon();
			if (weapon != null) {
				weaponAddDmg = weapon.getItem().getMagicDmgModifier();
			}
			magicDamage += weaponAddDmg;
			magicDamage += random.nextInt(_pc.ability.getInt()) * 0.5;
		}

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			// int spByItem = _pc.getAbility().getSp() -
			// _pc.getAbility().getTrueSp();
			charaIntelligence = _pc.getAbility().getSp();
		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			int spByItem = _npc.getAbility().getSp()
					- _npc.getAbility().getTrueSp();
			charaIntelligence = _npc.getAbility().getTotalInt() + spByItem - 12;
		}
		if (charaIntelligence < 1) {
			charaIntelligence = 1;
		}

		double attrDeffence = calcAttrResistance(l1skills.getAttr());

		/*
		 * double coefficient = (1.0 - attrDeffence + charaIntelligence * 3.2 /
		 * 32.0); if (coefficient < 0) { coefficient = 0; }
		 */
		double coefficient = (charaIntelligence * 3.2 / 32.0);
		if (coefficient < 0) {
			coefficient = 0;
		}

		magicDamage *= coefficient;

		magicDamage -= magicDamage * attrDeffence;

		/** 치명타 발생 부분 추가 - By 시니 - */

		double criticalCoefficient = 1.5;
		int rnd = random.nextInt(100) + 1;
		if (_calcType == PC_PC || _calcType == PC_NPC) {
			int propCritical = CalcStat.마법치명타(_pc.ability.getTotalInt());
			if (criticalOccur(propCritical)) {
				magicDamage *= 1.5;
			}
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				if (_pc.getSkillEffectTimerSet().hasSkillEffect(LIND_MAAN) // 풍룡의
																			// 마안
																			// -
																			// 일정확률로
																			// 마법치명타+1
						|| _pc.getSkillEffectTimerSet().hasSkillEffect(
								SHAPE_MAAN) // 형상의 마안 - 일정확률로 마법치명타+1
						|| _pc.getSkillEffectTimerSet().hasSkillEffect(
								LIFE_MAAN)) { // 생명의 마안 - 일정확률로 마법치명타+1
					int MaanMagicCri = _random.nextInt(100) + 1;
					if (MaanMagicCri <= 20) { // 확률
						magicDamage *= 1.5;
					}
				}
			}
			/** 마법인형 장로 **/
			/*
			 * for (L1DollInstance doll : _pc.getDollList().values()) { // 피씨
			 * 자신이 인형을 가지고 있다면 magicDamage += doll.getMagicDamageByDoll(); // 인형
			 * 대미지를 줌..<이전페이지>가지고 있었을경우만 해당 // 15정도 대미지를 위에 += 플러스 시킴 }
			 */
			/** 마법인형 장로 **/

		} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
			if (rnd <= 15) {
				magicDamage *= criticalCoefficient;
			}
		}

		if (_calcType == PC_PC || _calcType == PC_NPC) {
			magicDamage += _pc.getBaseMagicDmg();
		}
		

		if (_calcType == PC_PC) {
			if( _pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_3) ||
					_pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_4)) {
				magicDamage += 2;
			}
		}
		return magicDamage;
	}

	public int calcHealing(int skillId) {
		L1Skills l1skills = SkillsTable.getInstance().getTemplate(skillId);
		int dice = l1skills.getDamageDice();
		int value = l1skills.getDamageValue();
		int magicDamage = 0;

		if (skillId != NATURES_BLESSING) {
			int magicBonus = getMagicBonus();
			if (magicBonus > 10) {
				magicBonus = 10;
			}

			int diceCount = value + magicBonus;
			for (int i = 0; i < diceCount; i++) {
				magicDamage += (_random.nextInt(dice) + 1);
			}
		} else {
			int Int = 0;
			if (_calcType == PC_PC || _calcType == PC_NPC) {
				Int = _pc.getAbility().getTotalInt();
			} else if (_calcType == NPC_PC || _calcType == NPC_NPC) {
				Int = _npc.getAbility().getTotalInt();
			}
			if (Int < 12)
				Int = 12;
			for (int i = 12; i <= Int; i++) {
				if (i == 12)
					magicDamage += (100 + _random.nextInt(80));
				else if (i >= 13 && i <= 17)
					magicDamage += (3 + _random.nextInt(2));
				else if (i >= 18 && i <= 25)
					magicDamage += (10 + _random.nextInt(6));
				else if (i >= 26)
					magicDamage += (1 + _random.nextInt(2));
			}
			magicDamage /= 2.2;
		}

		double alignmentRevision = 1.0;
		if (getLawful() > 0) {
			alignmentRevision += (getLawful() / 32768.0);
		}

		magicDamage *= alignmentRevision;

		if (skillId != NATURES_BLESSING)
			magicDamage = (magicDamage * getLeverage()) / 10;

		return magicDamage;
	}

	/**
	 * MR에 의한 마법 데미지 감소를 처리 한다 수정일자 : 2009.04.15 수정자 : 손영신
	 * 
	 * @param dmg
	 * @return dmg
	 */

	public int calcMrDefense(int dmg) {

		int MagicResistance = 0; // 마법저항
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			MagicResistance = _targetPc.getResistance().getEffectedMrBySkill();
			if (MagicResistance > 221) {
				MagicResistance = 221;
			}
		} else {
			MagicResistance = _targetNpc.getResistance().getEffectedMrBySkill();
			if (MagicResistance > 221) {
				MagicResistance = 221;
			}
		}

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

		// System.out.println("적용데미지 :"+dmg);
		if (dmg < 0) {
			dmg = 0;
		}

		return dmg;
	}

	private boolean criticalOccur(int prop) {
		boolean ok = false;
		int num = _random.nextInt(100) + 1;

		if (prop == 0) {
			return false;
		}
		if (num <= prop) {
			ok = true;
		}
		return ok;
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
			}
		} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
			// 취약속성 데미지 10% 증가
			int npc_att = _targetNpc.getNpcTemplate().get_weakAttr();
			if (npc_att == 0)
				return 0;
			if (npc_att >= 8) {
				npc_att -= 8;
				if (attr == 8)
					return -0.2;
			}
			if (npc_att >= 4) {
				npc_att -= 4;
				if (attr == 4)
					return -0.2;
			}
			if (npc_att >= 2) {
				npc_att -= 2;
				if (attr == 2)
					return -0.2;
			}
			if (npc_att >= 1) {
				npc_att -= 1;
				if (attr == 1)
					return -0.2;
			}
			return 0;
		}

		/*
		 * int resistFloor = (int) (0.32 * Math.abs(resist)); if (resist >= 0) {
		 * resistFloor *= 1; } else { resistFloor *= -1; }
		 * 
		 * double attrDeffence = resistFloor / 32.0;
		 */

		// double attrDeffence = resist / 4 * 0.01;
		// double attrDeffence = resist * 0.3 * 0.01;
		double attrDeffence = 0;
		if (resist < 10) {
			attrDeffence = 0.01;
		} else if (resist < 20) {
			attrDeffence = 0.02;
		} else if (resist < 30) {
			attrDeffence = 0.03;
		} else if (resist < 40) {
			attrDeffence = 0.04;
		} else if (resist < 50) {
			attrDeffence = 0.05;
		} else if (resist < 60) {
			attrDeffence = 0.06;
		} else if (resist < 70) {
			attrDeffence = 0.1;
		} else if (resist < 80) {
			attrDeffence = 0.15;
		} else if (resist < 90) {
			attrDeffence = 0.20;
		} else if (resist < 100) {
			attrDeffence = 0.25;
		} else {
			attrDeffence = 0.30;
		}
		return attrDeffence;
	}

	public void commit(int damage, int drainMana, int skillid) {
		if (_calcType == PC_PC || _calcType == NPC_PC) {
			if (skillid == L1SkillId.MANA_DRAIN) {
				commitPc(damage, drainMana, true);
			} else {
				commitPc(damage, drainMana, false);
			}
		} else if (_calcType == PC_NPC || _calcType == NPC_NPC) {
			if (_calcType == PC_NPC) {
				if (damage > 0)
					damage = (int) (damage * 1.40);
			}
			if (skillid == L1SkillId.MANA_DRAIN) {
				commitNpc(damage, drainMana, true);
			} else {
				commitNpc(damage, drainMana, false);
			}

		}

		if (!Config.ALT_ATKMSG) {
			return;
		}

		if (_targetPc == null && _targetNpc == null)
			return;
		if (_pc != null && _pc.isGm()) {
			_pc.sendPackets(new S_SystemMessage("\\fT["
					+ _pc.getName()
					+ "] ==> ["
					+ (_targetPc == null ? _targetNpc.getName() : _targetPc
							.getName())
					+ "][== "
					+ damage
					+ " MAG ==][HP "
					+ (_targetPc == null ? _targetNpc.getCurrentHp()
							: _targetPc.getCurrentHp()) + "]"));
		}
		if (_targetPc != null && _targetPc.isGm()) {
			_targetPc
					.sendPackets(new S_SystemMessage("\\fY["
							+ _targetPc.getName() + "] <== ["
							+ (_pc == null ? _npc.getName() : _pc.getName())
							+ "][== " + damage + " MAG ==][HP "
							+ _targetPc.getCurrentHp() + "]"));
		}
		/*
		 * if (Config.ALT_ATKMSG) { if ((_calcType == PC_PC || _calcType ==
		 * PC_NPC) && !_pc.isGm()) { return; } if ((_calcType == PC_PC ||
		 * _calcType == NPC_PC) && !_targetPc.isGm()) { return; } }
		 * 
		 * String msg0 = ""; String msg1 = "왜"; String msg2 = ""; String msg3 =
		 * ""; String msg4 = "";
		 * 
		 * if (_calcType == PC_PC || _calcType == PC_NPC) { msg0 =
		 * _pc.getName(); } else if (_calcType == NPC_PC) { msg0 =
		 * _npc.getName(); }
		 * 
		 * if (_calcType == NPC_PC || _calcType == PC_PC) { msg4 =
		 * _targetPc.getName(); msg2 = "THP" + _targetPc.getCurrentHp(); } else
		 * if (_calcType == PC_NPC) { msg4 = _targetNpc.getName(); msg2 = "THp"
		 * + _targetNpc.getCurrentHp(); }
		 * 
		 * msg3 = damage + "주었다";
		 * 
		 * if (_calcType == PC_PC || _calcType == PC_NPC) { _pc.sendPackets(new
		 * S_ServerMessage(166, msg0, msg1, msg2, msg3, msg4)); } if (_calcType
		 * == NPC_PC || _calcType == PC_PC) { _targetPc.sendPackets(new
		 * S_ServerMessage(166, msg0, msg1, msg2, msg3, msg4)); }
		 */
	}

	private void commitPc(int damage, int drainMana, boolean ismanadrain) {
		if (_calcType == PC_PC) {
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(
					ABSOLUTE_BARRIER)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							ICE_LANCE)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							FREEZING_BREATH)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							MOB_BASILL)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							MOB_COCA)
					|| _targetPc.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.STATUS_안전모드)) {
				damage = 0;
				drainMana = 0;
			}
			if (_targetPc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					&& damage >= 0) {
				damage = 0;
				drainMana = 0;
			}
			if (drainMana > 0 && _targetPc.getCurrentMp() > 0) {
				int newMp = 0;

				if (drainMana > _targetPc.getCurrentMp()) {
					drainMana = _targetPc.getCurrentMp();
				}

				if (ismanadrain) {
					newMp = _pc.getCurrentMp() + (drainMana / 2);
				} else {
					newMp = _pc.getCurrentMp() + drainMana;
				}

				_pc.setCurrentMp(newMp);
			}
			_targetPc.receiveManaDamage(_pc, drainMana);
			_targetPc.receiveDamage(_pc, damage, true);
		} else if (_calcType == NPC_PC) {
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
			 * ||_npc.getNpcId()== 45673){ }else{ //damage -= damage*0.15;
			 * damage -= damage*0.20; }
			 */
			damage -= damage * 0.50;
			_targetPc.receiveDamage(_npc, damage, true);
		}
	}

	private void commitNpc(int damage, int drainMana, boolean ismanadrain) {
		if (_calcType == PC_NPC) {
			if (_targetNpc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
							FREEZING_BREATH)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
							EARTH_BIND)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
							MOB_BASILL)
					|| _targetNpc.getSkillEffectTimerSet().hasSkillEffect(
							MOB_COCA)) {
				damage = 0;
				drainMana = 0;
			}

			if (_targetNpc instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) _targetNpc;
				if (mon.kir_counter_magic) {
					_pc.receiveDamage(_targetNpc, damage * 2, true);
					drainMana = 0;
					damage = 0;
				} else if (mon.kir_absolute) {
					damage = 0;
					drainMana = 0;
				}
			}
			if (drainMana > 0) {
				int drainValue = _targetNpc.drainMana(drainMana);

				if (ismanadrain) {
					drainValue /= 2;
				}

				int newMp = _pc.getCurrentMp() + drainValue;
				_pc.setCurrentMp(newMp);
			}
			_targetNpc.ReceiveManaDamage(_pc, drainMana);
			_targetNpc.receiveDamage(_pc, damage);
		} else if (_calcType == NPC_NPC) {
			_targetNpc.receiveDamage(_npc, damage);
		}
	}
}
