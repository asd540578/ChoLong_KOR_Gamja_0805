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
import static l1j.server.server.model.skill.L1SkillId.BERSERKERS;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.ERASE_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.GREATER_HASTE;
import static l1j.server.server.model.skill.L1SkillId.HASTE;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_STUN;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FREEZE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HASTE;
import static l1j.server.server.model.skill.L1SkillId.STATUS_SPOT1;
import static l1j.server.server.model.skill.L1SkillId.STATUS_SPOT2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_SPOT3;
import static l1j.server.server.model.skill.L1SkillId.WIND_SHACKLE;

import java.util.ArrayList;
import java.util.Random;

import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.poison.L1DamagePoison;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_RangeSkill;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillIconWindShackle;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1Skills;

//Referenced classes of package l1j.server.server.model:
//L1PcInstance

public class WeaponSkill {

	// private static final int SLOW = 0;
	private static Random _random = new Random(System.nanoTime());

	public static double getIceSpearDamage(L1PcInstance pc, L1Character cha) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (20 >= chance) {

			L1Magic magic = new L1Magic(pc, cha);

			dmg = magic.calcMagicDamage(L1SkillId.FROZEN_CLOUD);
			dmg = magic.calcMrDefense((int) dmg);
			if (dmg <= 0) {
				dmg = 0;
			}
			pc.sendPackets(new S_SkillSound(cha.getId(), 1804));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 1804));
			L1PcInstance targetPc = null;
			L1NpcInstance targetNpc = null;
			for (L1Object object : L1World.getInstance().getVisibleObjects(cha, 2)) {
				if (object == null) {
					continue;
				}
				if (!(object instanceof L1Character)) {
					continue;
				}
				if (object.getId() == pc.getId() || object.getId() == cha.getId()) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					if (CharPosUtil.getZoneType(targetPc) == 1) {
						continue;
					}
				}
				if (cha instanceof L1MonsterInstance) {
					if (!(object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				if (cha instanceof L1PcInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
					if (!(object instanceof L1PcInstance || object instanceof L1SummonInstance
							|| object instanceof L1PetInstance || object instanceof L1MonsterInstance)) {
						continue;
					}
				}

				if (dmg <= 0) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					targetPc.sendPackets(new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					Broadcaster.broadcastPacket(targetPc,
							new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					targetPc.receiveDamage(pc, (int) dmg, false);
				} else if (object instanceof L1SummonInstance || object instanceof L1PetInstance
						|| object instanceof L1MonsterInstance) {
					targetNpc = (L1NpcInstance) object;
					Broadcaster.broadcastPacket(targetNpc,
							new S_DoActionGFX(targetNpc.getId(), ActionCodes.ACTION_Damage));
					targetNpc.receiveDamage(pc, (int) dmg);
				}
			}
			magic = null;
		}
		return dmg;
	}

	public static double geTornadoAxeDamage(L1PcInstance pc, L1Character cha) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (10 >= chance) {

			L1Magic magic = new L1Magic(pc, cha);

			dmg = magic.calcMagicDamage(L1SkillId.TORNADO);
			// dmg = magic.calcMrDefense((int)dmg);
			if (dmg <= 0) {
				dmg = 0;
			}

			pc.sendPackets(new S_SkillSound(cha.getId(), 758));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 758));
			L1PcInstance targetPc = null;
			L1NpcInstance targetNpc = null;
			for (L1Object object : L1World.getInstance().getVisibleObjects(cha, 2)) {
				if (object == null) {
					continue;
				}
				if (!(object instanceof L1Character)) {
					continue;
				}
				if (object.getId() == pc.getId() || object.getId() == cha.getId()) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					if (CharPosUtil.getZoneType(targetPc) == 1) {
						continue;
					}
				}
				if (cha instanceof L1MonsterInstance) {
					if (!(object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				if (cha instanceof L1PcInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
					if (!(object instanceof L1PcInstance || object instanceof L1SummonInstance
							|| object instanceof L1PetInstance || object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				// dmg = calcDamageReduction((L1Character) object, dmg,
				// L1Skills.ATTR_WIND);
				if (dmg <= 0) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					targetPc.sendPackets(new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					Broadcaster.broadcastPacket(targetPc,
							new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					targetPc.receiveDamage(pc, (int) dmg, false);
				} else if (object instanceof L1SummonInstance || object instanceof L1PetInstance
						|| object instanceof L1MonsterInstance) {
					targetNpc = (L1NpcInstance) object;
					Broadcaster.broadcastPacket(targetNpc,
							new S_DoActionGFX(targetNpc.getId(), ActionCodes.ACTION_Damage));
					targetNpc.receiveDamage(pc, (int) dmg);
				}
			}
			magic = null;
		}
		return dmg;
	}

	public static double getBarlogSwordDamage(L1PcInstance pc, L1Character cha) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (7 >= chance) {

			L1Magic magic = new L1Magic(pc, cha);

			dmg = magic.calcMagicDamage(L1SkillId.METEOR_STRIKE);
			dmg = magic.calcMrDefense((int) dmg);
			if (dmg <= 0) {
				dmg = 0;
			}
			pc.sendPackets(new S_SkillSound(cha.getId(), 762));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 762));
			L1PcInstance targetPc = null;
			L1NpcInstance targetNpc = null;
			for (L1Object object : L1World.getInstance().getVisibleObjects(cha, 2)) {
				if (object == null) {
					continue;
				}
				if (!(object instanceof L1Character)) {
					continue;
				}
				if (object.getId() == pc.getId() || object.getId() == cha.getId()) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					if (CharPosUtil.getZoneType(targetPc) == 1) {
						continue;
					}
				}
				if (cha instanceof L1MonsterInstance) {
					if (!(object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				if (cha instanceof L1PcInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
					if (!(object instanceof L1PcInstance || object instanceof L1SummonInstance
							|| object instanceof L1PetInstance || object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				// dmg = calcDamageReduction((L1Character) object, dmg,
				// L1Skills.ATTR_FIRE);
				if (dmg <= 0) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					targetPc.sendPackets(new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					Broadcaster.broadcastPacket(targetPc,
							new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					targetPc.receiveDamage(pc, (int) dmg, false);
					if (targetPc.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
						targetPc.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

				} else if (object instanceof L1SummonInstance || object instanceof L1PetInstance
						|| object instanceof L1MonsterInstance) {
					targetNpc = (L1NpcInstance) object;
					Broadcaster.broadcastPacket(targetNpc,
							new S_DoActionGFX(targetNpc.getId(), ActionCodes.ACTION_Damage));
					targetNpc.receiveDamage(pc, (int) dmg);
				}
			}
			magic = null;
		}
		return dmg;
	}

	public static void AkdukSword(L1PcInstance pc, L1Character cha, int weaponid) {
		int[] stunTimeArray = { 1000, 1200, 1300, 1400, 1500, 2000, 2500, 3000, 3500 };
		int rnd = _random.nextInt(stunTimeArray.length);
		int _shockStunDuration = stunTimeArray[rnd];
		int chance = _random.nextInt(100) + 1;
		if (isFreeze(cha)) {
			return;
		}
		if (Config.검스턴확률 >= chance) {
			L1EffectSpawn.getInstance().spawnEffect(81162, _shockStunDuration, cha.getX(), cha.getY(), cha.getMapId());
			if (cha instanceof L1PcInstance) {
				L1PcInstance targetPc = (L1PcInstance) cha;
				targetPc.getSkillEffectTimerSet().setSkillEffect(SHOCK_STUN, _shockStunDuration);
				targetPc.sendPackets(new S_SkillSound(targetPc.getId(), 87));
				Broadcaster.broadcastPacket(targetPc, new S_SkillSound(targetPc.getId(), 87));
				targetPc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_STUN, true));
			} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
					|| cha instanceof L1PetInstance) {
				L1NpcInstance npc = (L1NpcInstance) cha;
				npc.getSkillEffectTimerSet().setSkillEffect(SHOCK_STUN, _shockStunDuration);
				Broadcaster.broadcastPacket(npc, new S_SkillSound(npc.getId(), 87));
				npc.setParalyzed(true);
			}
		}
	}

	public static double getBarlogSwordDamage1(L1PcInstance pc, L1Character cha) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (7 >= chance) {

			L1Magic magic = new L1Magic(pc, cha);

			dmg = magic.calcMagicDamage(L1SkillId.METEOR_STRIKE);
			dmg = magic.calcMrDefense((int) dmg);
			if (dmg <= 0) {
				dmg = 0;
			}
			pc.sendPackets(new S_SkillSound(cha.getId(), 12248));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 12248));
			L1PcInstance targetPc = null;
			L1NpcInstance targetNpc = null;
			for (L1Object object : L1World.getInstance().getVisibleObjects(cha, 2)) {
				if (object == null) {
					continue;
				}
				if (!(object instanceof L1Character)) {
					continue;
				}
				if (object.getId() == pc.getId() || object.getId() == cha.getId()) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					if (CharPosUtil.getZoneType(targetPc) == 1) {
						continue;
					}
				}
				if (cha instanceof L1MonsterInstance) {
					if (!(object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				if (cha instanceof L1PcInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
					if (!(object instanceof L1PcInstance || object instanceof L1SummonInstance
							|| object instanceof L1PetInstance || object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				// dmg = calcDamageReduction((L1Character) object, dmg,
				// L1Skills.ATTR_FIRE);
				if (dmg <= 0) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					targetPc.sendPackets(new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					Broadcaster.broadcastPacket(targetPc,
							new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					targetPc.receiveDamage(pc, (int) dmg, false);
					if (targetPc.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
						targetPc.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

				} else if (object instanceof L1SummonInstance || object instanceof L1PetInstance
						|| object instanceof L1MonsterInstance) {
					targetNpc = (L1NpcInstance) object;
					Broadcaster.broadcastPacket(targetNpc,
							new S_DoActionGFX(targetNpc.getId(), ActionCodes.ACTION_Damage));
					targetNpc.receiveDamage(pc, (int) dmg);
				}
			}
			magic = null;
		}
		return dmg;
	}

	public static double getBarlogSwordDamage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;

		if (7 + enchant >= chance) {

			L1Magic magic = new L1Magic(pc, cha);

			dmg = magic.calcMagicDamage(L1SkillId.METEOR_STRIKE);
			dmg = magic.calcMrDefense((int) dmg);
			if (dmg <= 0) {
				dmg = 0;
			}
			// 카매방어
			if (isCounterMagic(cha)) {
				dmg = 0;
			} else {
				pc.sendPackets(new S_SkillSound(cha.getId(), 762));
				Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 762));
			}
			// 카매방어
			pc.sendPackets(new S_SkillSound(cha.getId(), 762));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 762));
			L1PcInstance targetPc = null;
			L1NpcInstance targetNpc = null;
			for (L1Object object : L1World.getInstance().getVisibleObjects(cha, 2)) {
				if (object == null) {
					continue;
				}
				if (!(object instanceof L1Character)) {
					continue;
				}
				if (object.getId() == pc.getId() || object.getId() == cha.getId()) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					if (CharPosUtil.getZoneType(targetPc) == 1) {
						continue;
					}
				}
				if (cha instanceof L1MonsterInstance) {
					if (!(object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				if (cha instanceof L1PcInstance || cha instanceof L1SummonInstance || cha instanceof L1PetInstance) {
					if (!(object instanceof L1PcInstance || object instanceof L1SummonInstance
							|| object instanceof L1PetInstance || object instanceof L1MonsterInstance)) {
						continue;
					}
				}
				// dmg = calcDamageReduction((L1Character) object, dmg,
				// L1Skills.ATTR_FIRE);
				if (dmg <= 0) {
					continue;
				}
				if (object instanceof L1PcInstance) {
					targetPc = (L1PcInstance) object;
					targetPc.sendPackets(new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					Broadcaster.broadcastPacket(targetPc,
							new S_DoActionGFX(targetPc.getId(), ActionCodes.ACTION_Damage));
					targetPc.receiveDamage(pc, (int) dmg, false);
				} else if (object instanceof L1SummonInstance || object instanceof L1PetInstance
						|| object instanceof L1MonsterInstance) {
					targetNpc = (L1NpcInstance) object;
					Broadcaster.broadcastPacket(targetNpc,
							new S_DoActionGFX(targetNpc.getId(), ActionCodes.ACTION_Damage));
					targetNpc.receiveDamage(pc, (int) dmg);
				}
			}
			magic = null;
		}

		return dmg;
	}

	public static void getDeathKnightjin(L1PcInstance pc, L1Character cha) {

		int chance = _random.nextInt(100) + 1;
		int val = 10;
		if (val >= chance) {

			/*
			 * L1SkillUse l1skilluse = new L1SkillUse(); 123
			 * l1skilluse.handleCommands(pc, 4064, cha.getId(), cha.getX(),
			 * cha.getY(),null, 0, L1SkillUse.TYPE_NORMAL); l1skilluse = null;
			 * 
			 * 123
			 */

			ArrayList<L1Character> arrcha = new ArrayList<L1Character>();

			int rand = _random.nextInt(70) + 30;
			for (L1Object chars : L1World.getInstance().getVisibleObjects(pc, 6)) {
				if (chars instanceof L1MonsterInstance) {
					arrcha.add((L1Character) chars);
					((L1NpcInstance) chars).receiveDamage(pc, rand);
				}
			}

			L1Character[] chars = new L1Character[arrcha.size()];

			int i = 0;

			for (L1Character ts : arrcha) {
				chars[i] = ts;
				i++;
			}

			pc.sendPackets(new S_RangeSkill(pc, chars, 11660, 1, 0));
			Broadcaster.broadcastPacket(pc, new S_RangeSkill(pc, chars, 11660, 1, 0));

		}
	}

	public static double 블레이즈쇼크(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else
			val += 1;

		if (val >= chance) {
			int randmg = _random.nextInt(50) + 20;
			dmg = randmg;

			dmg -= dmg * calcAttrResistance(cha, 2);

			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);

			if (dmg < 20)
				dmg = 20;

			pc.sendPackets(new S_SkillSound(cha.getId(), 3939));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 3939));
		}
		return dmg;
	}

	// 잊혀진섬아이템리뉴얼
	public static double 히페리온의절망(L1PcInstance pc, L1Character cha, int effect, int enchant) {
		double dmg = 0.0D;
		int locx = cha.getX();
		int locy = cha.getY();
		int intel = pc.getAbility().getTotalInt();
		int chance = _random.nextInt(100) + 1;
		if (2 + enchant >= chance) {
			dmg = _random.nextInt(intel * 2) + intel * 4; // dmg =
															// _random.nextInt(intel
															// / 3) + intel * 6;
			if (cha.getCurrentMp() >= 10) {
				cha.setCurrentMp(cha.getCurrentMp() - 4);
				if (dmg <= 0.0D) {
					dmg = 0.0D;
				}
			}
			S_EffectLocation packet = new S_EffectLocation(locx, locy, effect);
			pc.sendPackets(packet);
			pc.broadcastPacket(packet);
		}
		return calcDamageReduction(cha, dmg, 4);
	}

	public static void getSilenceSword(L1PcInstance pc, L1Character cha, int enchant) {
		int chance = _random.nextInt(100) + 1;
		int val = enchant;
		if (cha instanceof L1MonsterInstance) {
			if (cha.getLevel() > 69) {
				return;
			}
		}
		if (cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EARTH_BIND))
			return;
		if (val <= 0) {
			val = 1;
		}
		if (enchant > 8) {
			val += 1;
		}
		if (enchant < 9) {
			val += 1;
		}

		if (val >= chance) {
			if (!cha.getSkillEffectTimerSet().hasSkillEffect(64)) {
				cha.getSkillEffectTimerSet().setSkillEffect(64, 16 * 1000);
				pc.sendPackets(new S_SkillSound(cha.getId(), 2177));
				Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 2177));
			}
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

		}
	}

	public static void getPoisonSword(L1PcInstance pc, L1Character cha) {
		int chance = _random.nextInt(100) + 1;
		if (10 >= chance) {
			L1DamagePoison.doInfection(pc, cha, 3000, 10);
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
	}

	public static void 이블트릭(L1PcInstance pc, L1Character target, int enchanlvl) {
		int probability = (enchanlvl * 1) + 5;
		if (probability >= _random.nextInt(100) + 1) {
			// System.out.println("트릭이팩 터짐!!");
			L1이블트릭 트릭 = new L1이블트릭(pc, target);
			트릭.begin();
			if (target.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				target.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
	}

	public static void 이블트릭(L1PcInstance pc, L1Character target, int enchanlvl, int gfx) {
		int probability = (enchanlvl * 1) + 5;
		if (probability >= _random.nextInt(100) + 1) {
			// System.out.println("트릭이팩 터짐!!");
			L1이블트릭 트릭 = new L1이블트릭(pc, target, gfx);
			트릭.begin();
		}
	}

	public static void 이블리버스(L1PcInstance pc, L1Character target, int enchanlvl) {
		int probability = (enchanlvl * 2) + 8;

		/*
		 * if(pc.getWeapon().getItemId() == 450008){ //마족검{ probability =
		 * enchanlvl; if(pc.getWeapon().getEnchantLevel() >= 8) probability +=
		 * 1; if(pc.getWeapon().getEnchantLevel() >= 9) probability += 2; }
		 */
		if (pc.getWeapon() != null && (pc.getWeapon().getItemId() >= 277 && pc.getWeapon().getItemId() <= 283))
			probability *= 0.5;

		if (probability >= _random.nextInt(100) + 1) {
			int pcInt = pc.getAbility().getTotalInt();
			int randmg = _random.nextInt(pcInt + enchanlvl) + enchanlvl;

			randmg = calcMrDefense(target.getResistance().getEffectedMrBySkill(), (int) randmg);

			// System.out.println("리버스이팩터짐!");
			L1이블리버스 리버스 = new L1이블리버스(pc, target, randmg);
			리버스.begin();
			if (target.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				target.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
	}

	public static void 이블리버스(L1PcInstance pc, L1Character target, int enchanlvl, int gfx) {
		int probability = (enchanlvl * 2) + 8;
		if (probability >= _random.nextInt(100) + 1) {
			int pcInt = pc.getAbility().getTotalInt();
			int randmg = _random.nextInt(pcInt + enchanlvl) + enchanlvl;
			// System.out.println("리버스이팩터짐!");
			L1이블리버스 리버스 = new L1이블리버스(pc, target, randmg, gfx);
			리버스.begin();
		}
	}

	public static void 체이서(L1PcInstance pc, L1Character target, int enchanlvl, int cc) {
		int dmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (chance <= 1 + enchanlvl) {
			int pcInt = pc.getAbility().getTotalInt();
			int targetMr = target.getResistance().getEffectedMrBySkill();
			int randmg = _random.nextInt((pcInt)) + enchanlvl;
			dmg = pcInt + randmg;
			dmg = calcMrDefense(targetMr, (int) dmg);

			if (dmg < 30)
				dmg = 30;
			dmg /= 3;

			L1Chaser 체이소 = new L1Chaser(pc, target, dmg, cc);
			체이소.begin();
			if (target.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				target.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
	}

	public static void getDiseaseWeapon(L1PcInstance pc, L1Character cha, int weaponid) {
		int chance = _random.nextInt(100) + 1;
		int skilltime = weaponid == 412003 ? 64 : 20;
		if (7 >= chance) {
			if (!cha.getSkillEffectTimerSet().hasSkillEffect(56)) {
				cha.addDmgup(-6);
				cha.getAC().addAc(12);
				if (cha instanceof L1PcInstance) {
					L1PcInstance target = (L1PcInstance) cha;
					target.sendPackets(new S_OwnCharAttrDef(target));
				}
			}
			cha.getSkillEffectTimerSet().setSkillEffect(56, skilltime * 1000);
			pc.sendPackets(new S_SkillSound(cha.getId(), 2230));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 2230));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
	}

	public static double getRondeDamage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else
			val += 1;
		if (val >= chance) {
			int locx = cha.getX();
			int locy = cha.getY();

			int randmg = _random.nextInt(20) + 80;

			dmg = randmg;

			if (dmg < 30)
				dmg = 30;
			S_EffectLocation packet = new S_EffectLocation(locx, locy, (short) 123);
			pc.sendPackets(packet);
			Broadcaster.broadcastPacket(pc, packet);
		}
		return dmg;
	}

	public static void getGunjuSword(L1PcInstance pc, L1Character cha, int enchant) {
		int chance = _random.nextInt(100) + 1;
		if (18 + enchant >= chance) {
			pc.sendPackets(new S_SkillSound(cha.getId(), 2568));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 2568));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

		}

	}

	public static double getKurtSwordDamage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else
			val += 1;
		if (val >= chance) {
			int pcInt = pc.getAbility().getTotalInt();
			int sp = pc.getAbility().getSp();
			int randmg = _random.nextInt((pcInt * 5)) + enchant;
			dmg = sp * 6 + randmg;

			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);

			dmg -= dmg * calcAttrResistance(cha, 8);
			if (dmg < 30)
				dmg = 30;
			pc.sendPackets(new S_SkillSound(cha.getId(), 10405));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 10405));
		}
		return dmg;
	}

	// 7.10 조우불골렘 관련 리뉴얼
	public static double getEffectDamage(L1PcInstance pc, L1Character cha, int enchant, int effect) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else
			val += 1;

		if (val >= chance) {
			int pcInt = pc.getAbility().getTotalInt();
			int randmg = _random.nextInt((pcInt * 2)) + enchant;
			if (cha instanceof L1PcInstance)
				dmg = pcInt * 2 + randmg;
			else
				dmg = pcInt * 2 + randmg;
			if (effect == 11760) {// 크리선버 (제로스지팡이)
				dmg = pcInt * 3 + randmg;
			}
			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);

			dmg -= dmg * calcAttrResistance(cha, 8);
			if (dmg < 30)
				dmg = 30;
			pc.sendPackets(new S_SkillSound(cha.getId(), effect));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), effect));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

		}
		return dmg;
	}

	public static double getEffectSwordDamage(L1PcInstance pc, L1Character cha, int effectid) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (10 >= chance) {
			L1Magic magic = new L1Magic(pc, cha);

			dmg = magic.calcMagicDamage(L1SkillId.CALL_LIGHTNING);
			dmg = magic.calcMrDefense((int) dmg);

			if (dmg <= 0) {
				dmg = 0;
			}
			pc.sendPackets(new S_SkillSound(cha.getId(), effectid));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), effectid));
			magic = null;
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
		return dmg;
	}

	public static int PhantomShock(L1PcInstance pc, L1Character cha, int enchant) {
		int chance = _random.nextInt(100) + 1;
		int dmg = 0;
		int val = enchant + 1;
		if (val >= chance) {

			int pcInt = pc.getAbility().getTotalInt();
			int randmg = (pc.getAbility().getSp() * 2) + pcInt;

			dmg = randmg;

			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);

			dmg -= dmg * calcAttrResistance(cha, 8);
			if (dmg < 30)
				dmg = 30;
			pc.sendPackets(new S_SkillSound(cha.getId(), 5201));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 5201));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
		return dmg;
	}

	public static double Icekiring(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else {
			val += 1;
		}

		if (val >= chance) {
			int pcInt = pc.getAbility().getTotalInt();
			int randmg = pc.getAbility().getSp() * 3 + pcInt;
			dmg = randmg;
			if (dmg < 30)
				dmg = 30;

			pc.sendPackets(new S_SkillSound(cha.getId(), 6553));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 6553));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

			if (cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EARTH_BIND)
					|| cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ICE_LANCE)) {
				return dmg;
			}

			if (cha.getCurrentMp() >= 5) {
				cha.setCurrentMp(cha.getCurrentMp() - 5);
			}

		}
		return dmg;
	}

	public static double Icekiring11(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else {
			val += 1;
		}

		if (val >= chance) {
			int pcInt = pc.getAbility().getTotalInt();
			int randmg = pc.getAbility().getSp() * 3 + pcInt;
			dmg = randmg;
			if (dmg < 40)
				dmg = 40;

			pc.sendPackets(new S_SkillSound(cha.getId(), 12248));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 12248));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

			if (cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EARTH_BIND)
					|| cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ICE_LANCE)) {
				return dmg;
			}

		}
		return dmg;
	}

	public static double IceChainSword(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else
			val += 1;
		if (val >= chance) {
			int pcInt = pc.getAbility().getTotalInt();
			int randmg = _random.nextInt((pcInt * 3)) + enchant;
			dmg = pcInt * 5 + randmg;

			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);

			dmg -= dmg * calcAttrResistance(cha, 4);

			if (dmg < 30)
				dmg = 30;

			S_EffectLocation packet = new S_EffectLocation(cha.getX(), cha.getY(), (short) 3687);
			pc.sendPackets(packet);
			Broadcaster.broadcastPacket(pc, packet);
			if (cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EARTH_BIND)
					|| cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ICE_LANCE)) {
				return dmg;
			}
			pc.setCurrentHp(pc.getCurrentHp() + _random.nextInt(pc.getAbility().getTotalInt() * 4) + 5);
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

		}
		return dmg;
	}

	public static double getIceQueenStaffDamage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 2 + 2;
		if (val >= chance) {
			int pcInt = pc.getAbility().getTotalInt();
			int targetMr = cha.getResistance().getEffectedMrBySkill();
			int randmg = _random.nextInt(pcInt * 3) + _random.nextInt(pc.getAbility().getSp() * 2);
			dmg = pcInt * 3 + randmg;
			dmg = calcMrDefense(targetMr, (int) dmg);

			dmg -= dmg * calcAttrResistance(cha, 4);

			if (dmg < 30)
				dmg = 30;

			pc.sendPackets(new S_SkillSound(cha.getId(), 1810));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 1810));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
		return dmg;
	}

	public static double getMoonBowDamage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant - 1;
		if (val <= 0) {
			val = 1;
		} else {
			val += 1;
		}
		if (val >= chance) {
			int randmg = _random.nextInt(10) + 15;

			dmg = randmg;

			if (dmg < 20)
				dmg = 20;

			S_UseAttackSkill packet = new S_UseAttackSkill(pc, cha.getId(), 6288, cha.getX(), cha.getY(),
					ActionCodes.ACTION_Attack, false);
			pc.sendPackets(packet);
			Broadcaster.broadcastPacket(pc, packet);
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
		return dmg;
	}

	// 할로윈
	public static double halloweenCus(L1PcInstance pc, L1Character cha) {
		if (isFreeze(cha)) {
			return 0;
		}
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (8 >= chance) {// 조정
			// int sp = pc.getAbility().getSp();
			int str = pc.getAbility().getTotalStr();
			int con = pc.getAbility().getTotalCon();
			double bsk = 0;
			if (pc.getSkillEffectTimerSet().hasSkillEffect(BERSERKERS)) {
				bsk = 0.2;
			}
			dmg = (str + con) * (2 + bsk) + _random.nextInt(str + con) * 3;
			cha.getSkillEffectTimerSet().setSkillEffect(29, 4 * 1000);
			pc.sendPackets(new S_SkillSound(cha.getId(), 752));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 752));
			pc.sendPackets(new S_SkillHaste(cha.getId(), 2, 4 * 1000));
			Broadcaster.broadcastPacket(pc, new S_SkillHaste(cha.getId(), 2, 4 * 1000));
			if (cha.getMoveState().getMoveSpeed() == 0) {
				// if (cha instanceof L1PcInstance) {
				// L1PcInstance pc2 = (L1PcInstance) cha;
				// pc2.sendPackets(new S_SkillHaste(pc2.getId(), 2, 64*1000));
				// }
				// cha.sendPackets(new S_SkillHaste(cha.getId(), 2, 64*1000));
				Broadcaster.broadcastPacket(pc, new S_SkillHaste(cha.getId(), 2, 4 * 1000));
				cha.getMoveState().setMoveSpeed(2);
			} else if (cha.getMoveState().getMoveSpeed() == 1) {
				int skillNum = 0;
				if (cha.getSkillEffectTimerSet().hasSkillEffect(HASTE)) {
					skillNum = HASTE;
				} else if (cha.getSkillEffectTimerSet().hasSkillEffect(GREATER_HASTE)) {
					skillNum = GREATER_HASTE;
				} else if (cha.getSkillEffectTimerSet().hasSkillEffect(STATUS_HASTE)) {
					skillNum = STATUS_HASTE;
				}
				if (skillNum != 0) {
					cha.getSkillEffectTimerSet().removeSkillEffect(skillNum);
					// cha.getSkillEffectTimerSet().removeSkillEffect(_skillId);
					cha.getMoveState().setMoveSpeed(0);
					// continue;
				}
			}

		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_RAY);
	}

	//
	public static double halloweenCus2(L1PcInstance pc, L1Character cha) {
		double dmg = 0;
		if (pc.getWeapon() == null)
			return 0;
		int chance = 1 + (pc.getWeapon().getEnchantLevel() / 2);
		if (isFreeze(cha)) {
			return 0;
		}
		if (_random.nextInt(100) + 1 <= chance) {// 조정
			int sp = pc.getAbility().getSp();
			int intel = pc.getAbility().getTotalInt();
			double bsk = 0;
			if (pc.getSkillEffectTimerSet().hasSkillEffect(BERSERKERS)) {
				bsk = 0.2;
			}
			dmg = (intel + sp) * (2 + bsk) + _random.nextInt(intel + sp) * 0.3;
			if (cha instanceof L1PcInstance) {
				L1PcInstance targetPc = (L1PcInstance) cha;
				if (targetPc.getSkillEffectTimerSet().hasSkillEffect(WIND_SHACKLE))
					targetPc.getSkillEffectTimerSet().removeSkillEffect(WIND_SHACKLE);
				targetPc.getSkillEffectTimerSet().setSkillEffect(WIND_SHACKLE, 4 * 1000);
				targetPc.sendPackets(new S_SkillIconWindShackle(targetPc.getId(), 4));
				Broadcaster.broadcastPacket(targetPc, new S_SkillIconWindShackle(targetPc.getId(), 4));
				targetPc.sendPackets(new S_SkillSound(targetPc.getId(), 7849));
				Broadcaster.broadcastPacket(targetPc, new S_SkillSound(targetPc.getId(), 7849));
				if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
					cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
			} else if (cha instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) cha;
				Broadcaster.broadcastPacket(npc, new S_SkillSound(npc.getId(), 7849));
				if (npc.getSkillEffectTimerSet().hasSkillEffect(WIND_SHACKLE))
					npc.getSkillEffectTimerSet().removeSkillEffect(WIND_SHACKLE);
				npc.getSkillEffectTimerSet().setSkillEffect(WIND_SHACKLE, 4 * 1000);
				/*
				 * npc.set템프(npc.getAtkspeed());
				 * npc.setAtkspeed((int)(npc.getAtkspeed()*1.5));
				 * npc.set펌프(16000);
				 */
			}
			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);
			// cha.getSkillEffectTimerSet().setSkillEffect(STATUS_FREEZE, 3000);
			// cha.sendPackets(new S_Paralysis(S_Paralysis.TYPE_BIND, true));
		}
		return calcDamageReduction(cha, dmg, L1Skills.ATTR_RAY);
	}

	//

	public static void giveSalCheonEffect(L1PcInstance pc, L1Character cha) { // 살천의활
																				// (썬더그랩홀드효과:
																				// 진홍빛크로스보우와
																				// 동일)
		int fettersTime = 3000;
		if (isFreeze(cha)) {
			return;
		}
		if ((_random.nextInt(35) + 1) <= 2) {
			L1EffectSpawn.getInstance().spawnEffect(81182, fettersTime, cha.getX(), cha.getY(), cha.getMapId());
			if (cha instanceof L1PcInstance) {
				L1PcInstance targetPc = (L1PcInstance) cha;
				targetPc.getSkillEffectTimerSet().removeSkillEffect(WIND_SHACKLE);
				targetPc.getSkillEffectTimerSet().setSkillEffect(WIND_SHACKLE, 16 * 1000);
				targetPc.sendPackets(new S_SkillIconWindShackle(targetPc.getId(), 16));
				Broadcaster.broadcastPacket(targetPc, new S_SkillIconWindShackle(targetPc.getId(), 16));
				targetPc.sendPackets(new S_SkillSound(targetPc.getId(), 7849));
				Broadcaster.broadcastPacket(targetPc, new S_SkillSound(targetPc.getId(), 7849));
			}
			if (cha instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) cha;
				Broadcaster.broadcastPacket(npc, new S_SkillSound(npc.getId(), 7849));
				if (npc.getSkillEffectTimerSet().hasSkillEffect(WIND_SHACKLE))
					npc.getSkillEffectTimerSet().removeSkillEffect(WIND_SHACKLE);
				npc.getSkillEffectTimerSet().setSkillEffect(WIND_SHACKLE, 16 * 1000);
				// npc.set템프(npc.getAtkspeed());
				// npc.setAtkspeed((int)(npc.getAtkspeed()*1.5));
				// npc.set펌프(16000);
			}
		}
	}

	public static void 섬멸자의체인소드( L1PcInstance pc ) { //체인소드 대미지.
		if(_random.nextInt(100) < 18){
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_SPOT1)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.STATUS_SPOT1);
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_SPOT2,15 * 1000);
				pc.sendPackets(new S_PacketBox(S_PacketBox.SPOT, 2));
			} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_SPOT2)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.STATUS_SPOT2);
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_SPOT3,15 * 1000);
				pc.sendPackets(new S_PacketBox(S_PacketBox.SPOT, 3));
			} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_SPOT3)) {
			} else {
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_SPOT1,15 * 1000);
				pc.sendPackets(new S_PacketBox(S_PacketBox.SPOT, 1));
			}
		}
	}
	
	public static double getZerosDamage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else
			val += 1;

		if (val >= chance) {
			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);

			dmg -= dmg * calcAttrResistance(cha, 2);

			if (dmg < 30)
				dmg = 30;
			pc.sendPackets(new S_SkillSound(cha.getId(), 11760));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 11760));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
		return dmg;
	}

	public static double getDeathKnightSwordDamage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant * 1;
		if (val <= 0) {
			val = 1;
		} else
			val += 1;

		if (val >= chance) {

			int pcInt = pc.getAbility().getTotalInt();
			int sp = pc.getAbility().getSp();
			int targetMr = cha.getResistance().getEffectedMrBySkill();
			int randmg = _random.nextInt((pcInt * 5)) + enchant;
			dmg = sp * 6 + randmg;
			dmg = calcMrDefense(targetMr, (int) dmg);
			dmg -= dmg * calcAttrResistance(cha, 2);

			if (dmg < 30)
				dmg = 30;
			pc.sendPackets(new S_SkillSound(cha.getId(), 11660));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 11660));
		}
		return dmg;
	}
	
	public static double getBaphometStaffDamage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant + 7;

		if (val >= chance) {
			int locx = cha.getX();
			int locy = cha.getY();
			int pcInt = pc.getAbility().getTotalInt();
			int randmg = _random.nextInt((pcInt * 4)) + _random.nextInt(pc.getAbility().getSp() * 2);
			dmg = pcInt * 4 + randmg;

			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);

			dmg -= dmg * calcAttrResistance(cha, 1);
			if (dmg < 30)
				dmg = 30;
			S_EffectLocation packet = new S_EffectLocation(locx, locy, (short) 129);
			pc.sendPackets(packet);
			Broadcaster.broadcastPacket(pc, packet);
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

		}
		return dmg;
	}

	public static double get수결지Damage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant + 5;
		if (val >= chance) {
			int pcInt = pc.getAbility().getTotalInt();
			int randmg = _random.nextInt(pcInt * 5) + _random.nextInt(pc.getAbility().getSp() * 3);
			dmg = pcInt * 5 + randmg;
			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);

			if (dmg < 30)
				dmg = 30;

			pc.sendPackets(new S_SkillSound(cha.getId(), 10405));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 10405));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

		}
		return dmg;
	}

	public static double get절망Damage(L1PcInstance pc, L1Character cha, int enchant) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int val = enchant + 5;
		if (val >= chance) {
			int pcInt = pc.getAbility().getTotalInt();
			int randmg = _random.nextInt(pcInt * 5) + _random.nextInt(pc.getAbility().getSp() * 3);
			dmg = pcInt * 5 + randmg;
			dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), (int) dmg);

			if (dmg < 30)
				dmg = 30;

			pc.sendPackets(new S_SkillSound(cha.getId(), 12248));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(cha.getId(), 12248));
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

		}
		return dmg;
	}

	public static double getDiceDaggerDamage(L1PcInstance pc, L1PcInstance targetPc, L1ItemInstance weapon) {
		double dmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (3 >= chance) {
			dmg = targetPc.getCurrentHp() * 2 / 3;
			if (targetPc.getCurrentHp() - dmg < 0) {
				dmg = 0;
			}
			String msg = weapon.getLogName();
			pc.sendPackets(new S_ServerMessage(158, msg));
			pc.getInventory().removeItem(weapon, 1);
			if (targetPc.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				targetPc.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
		}
		return dmg;
	}

	public static void giveFettersEffect(L1PcInstance pc, L1Character cha) {
		int fettersTime = 4000;
		if (isFreeze(cha)) {
			return;
		}
		if ((_random.nextInt(100) + 1) <= 5) {
			L1EffectSpawn.getInstance().spawnEffect(81182, fettersTime, cha.getX(), cha.getY(), cha.getMapId());
			if (cha instanceof L1PcInstance) {
				L1PcInstance targetPc = (L1PcInstance) cha;
				targetPc.getSkillEffectTimerSet().setSkillEffect(STATUS_FREEZE, fettersTime);
				targetPc.sendPackets(new S_SkillSound(targetPc.getId(), 4184));
				Broadcaster.broadcastPacket(targetPc, new S_SkillSound(targetPc.getId(), 4184));
				targetPc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_BIND, true));
				if (targetPc.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
					targetPc.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
			} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
					|| cha instanceof L1PetInstance) {
				L1NpcInstance npc = (L1NpcInstance) cha;
				npc.getSkillEffectTimerSet().setSkillEffect(STATUS_FREEZE, fettersTime);
				Broadcaster.broadcastPacket(npc, new S_SkillSound(npc.getId(), 4184));
				npc.setParalyzed(true);
			}
		}
	}

	public static int getKiringkuDamage(L1PcInstance pc, L1Character cha) {
		int dmg = 0;
		dmg = calcMrDefense(cha.getResistance().getEffectedMrBySkill(), dmg);

		return dmg;
	}

	public static int calcMrDefense(int MagicResistance, int dmg) {
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

	public static int getChaserDamage(L1PcInstance pc, L1Character target, int effect) {
		int dmg = 0;
		double plusdmg = 0;
		int chance = _random.nextInt(100) + 1;
		if (chance <= 7) {
			int pcInt = pc.getAbility().getTotalInt();
			int targetMr = target.getResistance().getEffectedMrBySkill();
			int randmg = _random.nextInt((pcInt * 2));

			dmg = pcInt * 5 + randmg;

			int ran = Math.abs(targetMr - 60);

			if (ran == 0) {
				return dmg;
			} else if (targetMr < 60) {
				plusdmg = _random.nextInt(ran) / 2;
				dmg += plusdmg;
				// pc.sendPackets(new
				// S_SystemMessage("마방에의한 데미지 증가 = "+plusdmg+" 토탈데미지 :"+dmg));
			} else {
				plusdmg = _random.nextInt(ran) / 2 * 1.5;
				dmg -= plusdmg;
				// pc.sendPackets(new
				// S_SystemMessage("마방에의한 데미지 감소 = "+plusdmg+" 토탈데미지 :"+dmg));
			}
			dmg /= 2;
			if (dmg < 0)
				dmg = 0;

			pc.sendPackets(new S_SkillSound(target.getId(), effect));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(target.getId(), effect));
		}
		return dmg;
	}

	public static int getChainSwordDamage(L1PcInstance pc, L1Character cha, int itemid) {
		int dmg = 0;
		int chance = _random.nextInt(100) + 1;
		int chanceweapon = 8;

		if (itemid == 90084) {
			chanceweapon = 13;
		}

		if (pc.ChainSwordObjid != cha.getId()) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_SPOT1)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_SPOT1);
				S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 0);
				pc.sendPackets(pb, true);
			} else if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_SPOT2)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_SPOT2);
				S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 0);
				pc.sendPackets(pb, true);
			} else if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_SPOT3)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_SPOT3);
				S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 0);
				pc.sendPackets(pb, true);
			}
			pc.ChainSwordObjid = cha.getId();
		}
		if (chanceweapon >= chance) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_SPOT1)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_SPOT1);
				pc.getSkillEffectTimerSet().setSkillEffect(STATUS_SPOT2, 16 * 1000);
				S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 2);
				pc.sendPackets(pb, true);
			} else if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_SPOT2)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_SPOT2);
				pc.getSkillEffectTimerSet().setSkillEffect(STATUS_SPOT3, 16 * 1000);
				S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 3);
				pc.sendPackets(pb, true);
			} else if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_SPOT3)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_SPOT3);
				pc.getSkillEffectTimerSet().setSkillEffect(STATUS_SPOT3, 16 * 1000);
				S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 3);
				pc.sendPackets(pb, true);
			} else {
				pc.getSkillEffectTimerSet().setSkillEffect(STATUS_SPOT1, 16 * 1000);
				S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 1);
				pc.sendPackets(pb, true);
			}
		}
		return dmg;
	}

	private static double calcDamageReduction(L1Character cha, double dmg, int attr) {
		if (isFreeze(cha)) {
			return 0;
		}
		int MagicResistance = 0; // 마법저항
		int RealMagicResistance = 0; // 적용되는 마법저항값
		double calMr = 0.00D; // 마방계산
		double baseMr = 0.00D;
		if (cha instanceof L1PcInstance) {
			baseMr = (_random.nextInt(1000) + 98000) / 100000D;

			if (MagicResistance <= 100) {
				calMr = baseMr - (MagicResistance * 470) / 100000D;
			} else if (MagicResistance > 100) {
				calMr = baseMr - (MagicResistance * 470) / 100000D + ((MagicResistance - 100) * 0.004);
			}
		} else {
			calMr = (200 - RealMagicResistance) / 250.00D;
		}

		dmg *= calMr;

		int resist = 0;
		if (attr == L1Skills.ATTR_EARTH) {
			resist = cha.getResistance().getEarth();
		} else if (attr == L1Skills.ATTR_FIRE) {
			resist = cha.getResistance().getFire();
		} else if (attr == L1Skills.ATTR_WATER) {
			resist = cha.getResistance().getWater();
		} else if (attr == L1Skills.ATTR_WIND) {
			resist = cha.getResistance().getWind();
		}
		int resistFloor = (int) (0.32 * Math.abs(resist));
		if (resist >= 0) {
			resistFloor *= 1;
		} else {
			resistFloor *= -1;
		}
		double attrDeffence = resistFloor / 32.0;
		dmg = (1.0 - attrDeffence) * dmg;

		if (dmg <= 0) {
			dmg = 0;
		} else {
			if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC)) {
				cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
			}
		}
		return dmg;
	}

	// 마법검 카매로방어
	private static boolean isCounterMagic(L1Character cha) {
		if (cha.getSkillEffectTimerSet().hasSkillEffect(COUNTER_MAGIC)) {
			cha.getSkillEffectTimerSet().removeSkillEffect(COUNTER_MAGIC);
			int castgfx = SkillsTable.getInstance().getTemplate(COUNTER_MAGIC).getCastGfx();
			Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), castgfx));
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_SkillSound(pc.getId(), castgfx));
			}
			return true;
		}
		return false;
	}

	private static boolean isFreeze(L1Character cha) {
		if (cha.getSkillEffectTimerSet().hasSkillEffect(STATUS_FREEZE)) {
			return true;
		}
		if (cha.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
				|| cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_안전모드)) {
			return true;
		}
		if (cha.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)) {
			return true;
		}
		if (cha.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
			return true;
		}
		if (cha.isParalyzed()) {
			return true;
		}
		if (cha.getSkillEffectTimerSet().hasSkillEffect(COUNTER_MAGIC)) {
			cha.getSkillEffectTimerSet().removeSkillEffect(COUNTER_MAGIC);
			int castgfx = SkillsTable.getInstance().getTemplate(COUNTER_MAGIC).getCastGfx();
			Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), castgfx));
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_SkillSound(pc.getId(), castgfx));
			}
			return true;
		}
		return false;
	}

	private static double calcAttrResistance(L1Character cha, int attr) {
		int resist = 0;
		if (cha instanceof L1PcInstance) {
			switch (attr) {
			case L1Skills.ATTR_EARTH:
				resist = cha.getResistance().getEarth();
				break;
			case L1Skills.ATTR_FIRE:
				resist = cha.getResistance().getFire();
				break;
			case L1Skills.ATTR_WATER:
				resist = cha.getResistance().getWater();
				break;
			case L1Skills.ATTR_WIND:
				resist = cha.getResistance().getWind();
				break;
			}
		}
		double attrDeffence = resist / 4 * 0.01;

		return attrDeffence;
	}
}
