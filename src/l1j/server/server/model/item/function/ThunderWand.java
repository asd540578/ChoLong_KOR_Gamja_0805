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

package l1j.server.server.model.item.function;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�߰��;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_����;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_ĥ����;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�ѿ�;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.DRAGON_SKIN;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.ERASE_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_A;
import static l1j.server.server.model.skill.L1SkillId.FEATHER_BUFF_B;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.MOB_BASILL;
import static l1j.server.server.model.skill.L1SkillId.PATIENCE;
import static l1j.server.server.model.skill.L1SkillId.REDUCTION_ARMOR;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_FREEZE;
import static l1j.server.server.model.skill.L1SkillId.��Ƽ����������;
import static l1j.server.server.model.skill.L1SkillId.��Ƽ�������丮;
import static l1j.server.server.model.skill.L1SkillId.��Ƽ���ູ�ֹ���;
import static l1j.server.server.model.skill.L1SkillId.���̸����Ѷ��;
import static l1j.server.server.model.skill.L1SkillId.���̽ÿ�������;

import java.util.Random;

import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1PinkName;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ItemName;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1EtcItem;
import l1j.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class ThunderWand extends L1ItemInstance {

	private static Random _random = new Random(System.nanoTime());

	public ThunderWand(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			if (pc.attacking)
				return;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			int spellsc_objid = 0;
			int spellsc_x = 0;
			int spellsc_y = 0;
			spellsc_objid = packet.readD();
			spellsc_x = packet.readH();
			spellsc_y = packet.readH();

			int delay_id = 0;
			delay_id = ((L1EtcItem) useItem.getItem()).get_delayid();
			if (delay_id != 0) { // ���� ���� �־�
				if (pc.hasItemDelay(delay_id) == true) {
					return;
				}
			}

			if (pc.isInvisble()) {
				pc.sendPackets(new S_ServerMessage(1003), true);
				return;
			}
			L1Object target = L1World.getInstance().findObject(spellsc_objid);
			pc.cancelAbsoluteBarrier();
			int heding = CharPosUtil.targetDirection(pc, spellsc_x, spellsc_y);
			pc.getMoveState().setHeading(heding);
			if (target != null) {
				doWandAction(pc, target);
			} else {
				pc.sendPackets(new S_UseAttackSkill(pc, 0, 10, spellsc_x,
						spellsc_y, 17), true);
				Broadcaster.broadcastPacket(pc, new S_UseAttackSkill(pc, 0, 10,
						spellsc_x, spellsc_y, 17), true);
			}
			pc.�÷��̾���� = pc.����_����;
			pc.���½ð� = System.currentTimeMillis() + 2000;
			pc.getInventory().updateItem(useItem, L1PcInventory.COL_COUNT);
			L1ItemDelay.onItemUse(pc, useItem); // ������ ���� ����
			pc.getInventory().removeItem(useItem, 1);

			/** 2011.07.01 ������ ������ ������ ��Ȯ������ �Ǵ� ���� */
			if (useItem.isIdentified()) {
				useItem.setIdentified(true);
				pc.sendPackets(new S_ItemName(useItem), true);
			}
		}
	}

	private void doWandAction(L1PcInstance user, L1Object target) {

		if (!(target instanceof L1Character)) {
			return;
		}

		if (CharPosUtil.isAreaAttack(user, target.getX(), target.getY(),
				target.getMapId()) == false) {
			return; // ������ ��ֹ��� �ִ�
		}

		if (CharPosUtil.isAreaAttack((L1Character) target, user.getX(),
				user.getY(), user.getMapId()) == false) {
			return; // ������ ��ֹ��� �ִ�
		}

		// XXX ������ ������ ���, ���� ����
		int dmg = ((_random.nextInt(5) + 1) + user.getAbility().getTotalStr() / 4);
		dmg = Math.max(17, dmg);

		if (target instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) target;
			/*
			 * if (user.getId() == target.getId()) { return; // �ڱ� �ڽſ��� ������ }
			 */
			if (CharPosUtil.getZoneType(pc) == 1
					|| user.checkNonPvP(user, pc)
					|| CharPosUtil.getZoneType(user) == 1
					|| (pc.getSkillEffectTimerSet().hasSkillEffect(
							SPECIAL_COOKING) && pc.getSkillEffectTimerSet()
							.hasSkillEffect(SPECIAL_COOKING2))) { // ����ȿ丮�� ����
																	// ������ �氨) {
				if (!pc.isGmInvis() && pc.isInvisble()) {
					pc.delInvis();
				}
				// user.sendPackets(new S_AttackPacket(user, pc.getId(),
				// ActionCodes.ACTION_Wand));
				// Broadcaster.broadcastPacket(user, new S_AttackPacket(user,
				// pc.getId(), ActionCodes.ACTION_Wand));

				user.sendPackets(
						new S_UseAttackSkill(user, pc.getId(), 10, pc.getX(),
								pc.getY(), 17, 0), true);
				Broadcaster.broadcastPacket(user,
						new S_UseAttackSkill(user, pc.getId(), 10, pc.getX(),
								pc.getY(), 17, 0), true);

				L1PinkName.onAction(pc, user);
				return;
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.FOG_OF_SLEEPING))
				pc.getSkillEffectTimerSet().removeSkillEffect(
						L1SkillId.FOG_OF_SLEEPING);
			if (isFreeze(pc) || isUseCounterMagic(pc)) {
				// user.sendPackets(new S_AttackPacket(user, pc.getId(),
				// ActionCodes.ACTION_Wand));
				// Broadcaster.broadcastPacket(user, new S_AttackPacket(user,
				// pc.getId(), ActionCodes.ACTION_Wand));

				user.sendPackets(
						new S_UseAttackSkill(user, pc.getId(), 10, pc.getX(),
								pc.getY(), 17, 0), true);
				Broadcaster.broadcastPacket(user,
						new S_UseAttackSkill(user, pc.getId(), 10, pc.getX(),
								pc.getY(), 17, 0), true);
				L1PinkName.onAction(pc, user);
				return;
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC))
				pc.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);

			L1PinkName.onAction(pc, user);

			int ���� = ����(pc);
			if (���� > 100) {
				user.sendPackets(
						new S_UseAttackSkill(user, pc.getId(), 10, pc.getX(),
								pc.getY(), 17, 0), true);
				Broadcaster.broadcastPacket(user,
						new S_UseAttackSkill(user, pc.getId(), 10, pc.getX(),
								pc.getY(), 17, 0), true);
				return;
			}

			user.sendPackets(
					new S_UseAttackSkill(user, pc.getId(), 10, pc.getX(), pc
							.getY(), 17), true);
			Broadcaster.broadcastPacket(
					user,
					new S_UseAttackSkill(user, pc.getId(), 10, pc.getX(), pc
							.getY(), 17), true);
			int newHp = pc.getCurrentHp() - dmg;
			if (newHp > 0) {
				if (!pc.isGmInvis() && pc.isInvisble()) {
					pc.delInvis();
				}
				// pc.sendPackets(new S_AttackPacket(pc, 0,
				// ActionCodes.ACTION_Damage));
				// Broadcaster.broadcastPacket(pc, new S_AttackPacket(pc, 0,
				// ActionCodes.ACTION_Damage));
				pc.setCurrentHp(newHp);
			} else if (newHp <= 0 && pc.isGm()) {
				pc.setCurrentHp(pc.getMaxHp());
			} else if (newHp <= 0 && !pc.isGm()) {
				pc.death(user);
			}
		} else if (target instanceof L1MonsterInstance) {
			L1MonsterInstance mob = (L1MonsterInstance) target;
			user.sendPackets(
					new S_UseAttackSkill(user, mob.getId(), 10, mob.getX(), mob
							.getY(), 17), true);
			Broadcaster.broadcastPacket(
					user,
					new S_UseAttackSkill(user, mob.getId(), 10, mob.getX(), mob
							.getY(), 17), true);
			// Broadcaster.broadcastPacket(mob, new S_AttackPacketForNpc(user,
			// mob.getId(), 2));
			mob.receiveDamage(user, dmg);
		} else if (target instanceof L1NpcInstance) {
			L1NpcInstance npc = (L1NpcInstance) target;
			if (npc instanceof L1NpcShopInstance) {
				L1NpcShopInstance pc = (L1NpcShopInstance) npc;
				if (CharPosUtil.getZoneType(pc) == 1
						|| user.checkNonPvP(user, pc)
						|| CharPosUtil.getZoneType(user) == 1) {
					user.sendPackets(new S_UseAttackSkill(user, pc.getId(), 10,
							pc.getX(), pc.getY(), 17, 0), true);
					Broadcaster.broadcastPacket(user, new S_UseAttackSkill(
							user, pc.getId(), 10, pc.getX(), pc.getY(), 17, 0),
							true);
					return;
				}
			}
			user.sendPackets(
					new S_UseAttackSkill(user, npc.getId(), 10, npc.getX(), npc
							.getY(), 17), true);
			Broadcaster.broadcastPacket(
					user,
					new S_UseAttackSkill(user, npc.getId(), 10, npc.getX(), npc
							.getY(), 17), true);
			// Broadcaster.broadcastPacket(npc, new S_DoActionGFX(npc.getId(),
			// ActionCodes.ACTION_Damage));
		}
	}

	private boolean isUseCounterMagic(L1Character cha) {
		if (cha.getSkillEffectTimerSet().hasSkillEffect(COUNTER_MAGIC)) {
			cha.getSkillEffectTimerSet().removeSkillEffect(COUNTER_MAGIC);
			Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(),
					10702), true);
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_SkillSound(pc.getId(), 10702), true);
			}
			return true;
		}
		return false;
	}

	public boolean isFreeze(L1PcInstance pc) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_FREEZE)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.STATUS_�������)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)) {
			return true;
		}
		return false;
	}

	private int ����(L1PcInstance pc) {
		// TODO �ڵ� ������ �޼ҵ� ����
		int ���� = 0;
		/** �������� ���� **/
		if (pc.getDollList().size() > 0) {
			for (L1DollInstance doll : pc.getDollList()) {
				���� += doll.getDamageReductionByDoll();
			}
		}
		���� += pc.getDamageReductionByArmor(); // ���� �ⱸ�� ���� ������ �氨

		if (pc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)) { // ����ȿ丮��
																			// ����
																			// ������
																			// �氨
			���� += 5;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING2)) {
			���� += 5;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_�߰��)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_����)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_ĥ����)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_�ѿ�)) {
			���� += 2;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(��Ƽ����������)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(���̽ÿ�������))
			���� += 5;
		if (pc.getSkillEffectTimerSet().hasSkillEffect(��Ƽ�������丮)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(���̸����Ѷ��))
			���� += 5;
		if (pc.getSkillEffectTimerSet().hasSkillEffect(��Ƽ���ູ�ֹ���)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.����Ǳ��))
			���� += 3;

		// Ű��ũ �ƴҶ��� �߰�
		if (pc.getSkillEffectTimerSet().hasSkillEffect(REDUCTION_ARMOR)) {
			int targetPcLvl = pc.getLevel();
			if (targetPcLvl < 50) {
				targetPcLvl = 50;
			}
			int dmg2 = (targetPcLvl - 40) / 3 - 3;
			���� += dmg2 > 0 ? dmg2 : 0;// +1
		}
		L1ItemInstance �ݿ����ǹ��� = pc.getInventory().checkEquippedItem(21093);
		if (�ݿ����ǹ��� != null) {
			int chance = �ݿ����ǹ���.getEnchantLevel() * 2;
			if (_random.nextInt(100) <= chance) {
				���� += 50;
				pc.sendPackets(new S_SkillSound(pc.getId(), 6320));
				Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(),
						6320));
			}
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(DRAGON_SKIN)) {
			���� += 5;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(PATIENCE)) {
			���� += 2;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_A)) {
			���� += 3;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(FEATHER_BUFF_B)) {
			���� += 2;
		}
		if (pc.isAmorGaurd) { // �ƸӰ��忡���� ����������
			int d = pc.getAC().getAc() / 10;
			if (d < 0) {
				���� -= d;
			} else {
				���� += d;
			}
		}

		return ����;
	}
}
