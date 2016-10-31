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

import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;

import java.util.Random;

import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1EtcItem;
import l1j.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class HealingPotion extends L1ItemInstance {
	private static Random _random = new Random(System.nanoTime());

	public HealingPotion(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;

			if (cha.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| cha.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| cha.getSkillEffectTimerSet().hasSkillEffect(
							FREEZING_BREATH)
					|| cha.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.CURSE_PARALYZE)
					|| cha.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.SHOCK_STUN)) {
				return;
			}

			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			int itemId = useItem.getItemId();

			int delay_id = 0;
			delay_id = ((L1EtcItem) useItem.getItem()).get_delayid();
			if (delay_id != 0) { // ���� ���� �־�
				if (pc.hasItemDelay(delay_id) == true) {
					return;
				}
			}

			int item_minlvl = ((L1EtcItem) useItem.getItem()).getMinLevel();
			int item_maxlvl = ((L1EtcItem) useItem.getItem()).getMaxLevel();

			if (item_minlvl != 0 && item_minlvl > pc.getLevel() && !pc.isGm()) {
				pc.sendPackets(
						new S_ServerMessage(318, String.valueOf(item_minlvl)),
						true);
				// �� ��������%0���� �̻��� ���� ������ ����� �� �����ϴ�.
				return;
			} else if (item_maxlvl != 0 && item_maxlvl < pc.getLevel()
					&& !pc.isGm()) {
				pc.sendPackets(
						new S_ServerMessage(673, String.valueOf(item_maxlvl)),
						true);
				// �� ��������%d���� �̻� ����� �� �ֽ��ϴ�.
				return;
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																			// ����
				pc.sendPackets(new S_ServerMessage(698), true); // ���¿� ���� �ƹ��͵�
																// ���� ���� �����ϴ�.
				return;
			}
			switch (itemId) {
			case 60071: // ������ ġ���� �ֹ�
				if (pc.getLevel() >= 65)
					UseHeallingPotion(pc, 80 + _random.nextInt(41), 197);
				else
					UseHeallingPotion(pc, 120 + _random.nextInt(31), 197);
				break;
			case 60029: // �������ֽ�
			case 60165: // �Ƚ��� ��������
				UseHeallingPotion(pc, 23 + _random.nextInt(16), 189);
				break;
			case 60030: // ����������
				UseHeallingPotion(pc, 4 + _random.nextInt(39), 189);
				break;
			case 40010: // ������
			case 40019:
			case 40029:
			case 40022: // �ź��� ���� �ؾߵ�
			case 240010:
			case 140010:
			case 50752:

			case 60205: // �ÿ��� ü�� ����
			case 60328:
			case 60412: // �¸��� ü�� ȸ����
				// UseHeallingPotion(pc, 9+_random.nextInt(37), 189);
				UseHeallingPotion(pc, 11 + _random.nextInt(25), 189);
				break;
			// case 60205: // �ÿ��� ü�� ����
			// UseHeallingPotion(pc, 47+_random.nextInt(30), 189);
			// break;
			case 40011: // ��ȫ��
			case 40020:
			case 40023:
			case 140011:
			case 60248:
			case 60329:
				UseHeallingPotion(pc, 33 + _random.nextInt(56), 194);
				break;
			case 40012: // ������
			case 40021:
			case 40024:
			case 140012:
				UseHeallingPotion(pc, 45 + _random.nextInt(61), 197);// 81
				break;
			case 435000:
				UseHeallingPotion(pc, 33 + _random.nextInt(56), 189);// 81
				break;
			case 40026: // �ٳ��� �ֽ�
			case 40027:
			case 40028:
				UseHeallingPotion(pc, 11 + _random.nextInt(23), 189);
				break;
			case 6014: // �ź��� ȸ�� ����
				UseHeallingPotion(pc, 141 + _random.nextInt(1044), 197);
				break;
			case 40043: // �䳢��
				UseHeallingPotion(pc, 141 + _random.nextInt(1044), 189);
				break;
			case 40058: // ������ ������
				UseHeallingPotion(pc, 18 + _random.nextInt(21), 189);
				break;
			case 40071: // Ÿ�ٳ��� ������
				UseHeallingPotion(pc, 33 + _random.nextInt(51), 197);
				break;
			case 40506: // ��Ʈ�� ����
			case 140506:
				UseHeallingPotion(pc, 46 + _random.nextInt(78), 197);
				break;
			case 40734: // �ŷ��� ����
				UseHeallingPotion(pc, 50 + _random.nextInt(26), 189);
				break;
			case 40930: // �ٺ�ť
				UseHeallingPotion(pc, 79 + _random.nextInt(105), 189);
				break;
			case 41298: // � �����
				UseHeallingPotion(pc, 8 + _random.nextInt(3), 189);
				break;
			case 41299: // ����� �����
				UseHeallingPotion(pc, 7 + _random.nextInt(17), 194);
				break;
			case 41300: // ���� �����
				UseHeallingPotion(pc, 11 + _random.nextInt(55), 197);
				break;
			case 41337: // �ູ���� ������
				UseHeallingPotion(pc, 44 + _random.nextInt(64), 197);
				break;
			case 41403: // ������ �ķ�
				UseHeallingPotion(pc, 124 + _random.nextInt(477), 189);
				break;
			case 41411: // ������
				UseHeallingPotion(pc, 10 + _random.nextInt(5), 189);
				break;
			case 41417:
			case 41418:
			case 41419:
			case 41420:
			case 41421:
				UseHeallingPotion(pc, 90 + _random.nextInt(23), 197);
				break;
			case 41141:
			case L1ItemId.MYSTERY_THICK_HEALING_POTION: // �ź��� ���� ���� ����
				UseHeallingPotion(pc, 23 + _random.nextInt(34), 189);
				break;
			case 60258:// ������ �˾�
				UseHeallingPotion(pc, 55 + _random.nextInt(86), 197);
				break;
			case 60423:// ���� ȣ�ڼ�
				UseHeallingPotion(pc, 55 + _random.nextInt(81), 197);
				break;
			case 60443: // ũ�������� ��Ű
				if (pc.getLevel() >= 1 && pc.getLevel() <= 60)
					UseHeallingPotion(pc, 120 + _random.nextInt(31), 197);
				else
					UseHeallingPotion(pc, 80 + _random.nextInt(41), 197);
				break;
			}
			pc.getInventory().removeItem(useItem, 1);
			L1ItemDelay.onItemUse(pc, useItem); // ������ ���� ����

		
		}
	}

	private void UseHeallingPotion(L1PcInstance pc, int healHp, int gfxid) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																		// ����
			pc.sendPackets(new S_ServerMessage(698), true); // ���¿� ���� �ƹ��͵� ���� ����
															// �����ϴ�.
			return;
		}
		// �ۼַ�Ʈ�������� ����
		pc.cancelAbsoluteBarrier();

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid), true);
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfxid),
				true);
		// pc.sendPackets(new S_ServerMessage(77)); // \f1����� ���������ϴ�.
		// healHp *= (_random.nextGaussian() / 5.0D) + 1.0D;
		// System.out.println(healHp);
		// ** ��Ƽ�� Ǫ���� �Ͱ��� �� **//
		if (pc.getInventory().getEnchantEquipped(500008, 0)) { // ����æ
			healHp += (healHp * 0.02) + 2;
		}
		if (pc.getInventory().getEnchantEquipped(500008, 1)) { // 1��
			healHp += (healHp * 0.06) + 6;
		}
		if (pc.getInventory().getEnchantEquipped(500008, 2)) { // 2��
			healHp += (healHp * 0.08) + 8;
		}
		if (pc.getInventory().getEnchantEquipped(500008, 3)) { // 3��
			healHp += (healHp * 0.10) + 10;
		}
		if (pc.getInventory().getEnchantEquipped(500008, 4)) { // 4��
			healHp += (healHp * 0.12) + 12;
		}
		if (pc.getInventory().getEnchantEquipped(500008, 5)) { // 5��
			healHp += (healHp * 0.14) + 14;
		}
		if (pc.getInventory().getEnchantEquipped(500008, 6)) { // 6��
			healHp += (healHp * 0.16) + 16;
		}
		if (pc.getInventory().getEnchantEquipped(500008, 7)) { // 7��
			healHp += (healHp * 0.18) + 18;
		}
		if (pc.getInventory().getEnchantEquipped(500008, 8)) { // 8��
			healHp += (healHp * 0.20) + 20;
		}
		
		if (pc.getInventory().getEnchantEquipped(502008, 0)) { // ����æ
			healHp += (healHp * 0.02) + 2;
		}
		if (pc.getInventory().getEnchantEquipped(502008, 1)) { // 1��
			healHp += (healHp * 0.06) + 6;
		}
		if (pc.getInventory().getEnchantEquipped(502008, 2)) { // 2��
			healHp += (healHp * 0.08) + 8;
		}
		if (pc.getInventory().getEnchantEquipped(502008, 3)) { // 3��
			healHp += (healHp * 0.12) + 12;
		}
		if (pc.getInventory().getEnchantEquipped(502008, 4)) { // 4��
			healHp += (healHp * 0.14) + 14;
		}
		if (pc.getInventory().getEnchantEquipped(502008, 5)) { // 5��
			healHp += (healHp * 0.16) + 16;
		}
		if (pc.getInventory().getEnchantEquipped(502008, 6)) { // 6��
			healHp += (healHp * 0.18) + 18;
		}
		if (pc.getInventory().getEnchantEquipped(502008, 7)) { // 7��
			healHp += (healHp * 0.20) + 20;
		}
		if (pc.getInventory().getEnchantEquipped(502008, 8)) { // 8��
			healHp += (healHp * 0.22) + 22;
		}
		
		
		//�Ϲ� �Ͱ��� ����
		//�뿹 �Ͱ���
		if (pc.getInventory().getEnchantEquipped(21027, 5)) { // 5��
			healHp += (healHp * 0.02);
		}
		if (pc.getInventory().getEnchantEquipped(21027, 6)) { // 6��
			healHp += (healHp * 0.04);
		}
		if (pc.getInventory().getEnchantEquipped(21027, 7)) { // 7��
			healHp += (healHp * 0.06);
		}
		if (pc.getInventory().getEnchantEquipped(21027, 8)) { // 8��
			healHp += (healHp * 0.08);
		}
		if (pc.getInventory().getEnchantEquipped(21027, 9)) { // 8��
			healHp += (healHp * 0.08);
		}
		
		//�Ϲ� ����� ����
		//���뽺 ������ �����
		if (pc.getInventory().getEnchantEquipped(21258, 5)) { // 5��
			healHp += (healHp * 0.02);
		}
		if (pc.getInventory().getEnchantEquipped(21258, 6)) { // 6��
			healHp += (healHp * 0.04);
		}
		if (pc.getInventory().getEnchantEquipped(21258, 7)) { // 7��
			healHp += (healHp * 0.06);
		}
		if (pc.getInventory().getEnchantEquipped(21258, 8)) { // 8��
			healHp += (healHp * 0.08);
		}
		if (pc.getInventory().getEnchantEquipped(21258, 9)) { // 9��
			healHp += (healHp * 0.09);
		}
		//������ �����
		if (pc.getInventory().getEnchantEquipped(21260, 5)) { // 5��
			healHp += (healHp * 0.02);
		}
		if (pc.getInventory().getEnchantEquipped(21260, 6)) { // 6��
			healHp += (healHp * 0.04);
		}
		if (pc.getInventory().getEnchantEquipped(21260, 7)) { // 7��
			healHp += (healHp * 0.06);
		}
		if (pc.getInventory().getEnchantEquipped(21260, 8)) { // 8��
			healHp += (healHp * 0.08);
		}
		if (pc.getInventory().getEnchantEquipped(21260, 9)) { // 9��
			healHp += (healHp * 0.09);
		}
		//�ż��� �Ϸ��� �����
		if (pc.getInventory().getEnchantEquipped(222346, 5)) { // 5��
			healHp += (healHp * 0.02);
		}
		if (pc.getInventory().getEnchantEquipped(222346, 6)) { // 6��
			healHp += (healHp * 0.04);
		}
		if (pc.getInventory().getEnchantEquipped(222346, 7)) { // 7��
			healHp += (healHp * 0.06);
		}
		if (pc.getInventory().getEnchantEquipped(222346, 8)) { // 8��
			healHp += (healHp * 0.08);
		}
		if (pc.getInventory().getEnchantEquipped(222346, 9)) { // 9��
			healHp += (healHp * 0.09);
		}
		//�ż��� ��ø�� ����� 
		if (pc.getInventory().getEnchantEquipped(222347, 5)) { // 5��
			healHp += (healHp * 0.02);
		}
		if (pc.getInventory().getEnchantEquipped(222347, 6)) { // 6��
			healHp += (healHp * 0.04);
		}
		if (pc.getInventory().getEnchantEquipped(222347, 7)) { // 7��
			healHp += (healHp * 0.06);
		}
		if (pc.getInventory().getEnchantEquipped(222347, 8)) { // 8��
			healHp += (healHp * 0.08);
		}
		if (pc.getInventory().getEnchantEquipped(222347, 9)) { // 9��
			healHp += (healHp * 0.09);
		}		
		//�ż��� ������ �����
		if (pc.getInventory().getEnchantEquipped(222348, 5)) { // 5��
			healHp += (healHp * 0.02);
		}
		if (pc.getInventory().getEnchantEquipped(222348, 6)) { // 6��
			healHp += (healHp * 0.04);
		}
		if (pc.getInventory().getEnchantEquipped(222348, 7)) { // 7��
			healHp += (healHp * 0.06);
		}
		if (pc.getInventory().getEnchantEquipped(222348, 8)) { // 8��
			healHp += (healHp * 0.08);
		}
		if (pc.getInventory().getEnchantEquipped(222348, 9)) { // 9��
			healHp += (healHp * 0.09);
		}		
		//�ż��� ������ �����
		if (pc.getInventory().getEnchantEquipped(222349, 5)) { // 5��
			healHp += (healHp * 0.02);
		}
		if (pc.getInventory().getEnchantEquipped(222349, 6)) { // 6��
			healHp += (healHp * 0.04);
		}
		if (pc.getInventory().getEnchantEquipped(222349, 7)) { // 7��
			healHp += (healHp * 0.06);
		}
		if (pc.getInventory().getEnchantEquipped(222349, 8)) { // 8��
			healHp += (healHp * 0.08);
		}
		if (pc.getInventory().getEnchantEquipped(222349, 9)) { // 9��
			healHp += (healHp * 0.09);
		}		
		
		
		if ( pc.getInventory().checkEquipped(490022)){//ȸ������
			int enchant = pc.getInventory().getEnchantCount(490022);
			int upHp = (enchant < 1 ? 2 : (enchant + 1) * 2);
			healHp = healHp * (upHp + 100) / 100 + upHp;
		}
		if ( pc.getInventory().checkEquipped(490022)){//ȸ������
			int enchant = pc.getInventory().getEnchantCount(490022);
			int upHp = (enchant < 1 ? 2 : (enchant + 1) * 2);
			healHp = healHp * (upHp + 100) / 100 + upHp;
		}
		if (pc.getPotionPlus() > 0) {
			double per = pc.getPotionPlus() / 100.000;

			// System.out.println("per :"+per);
			int addhp = (int) ((double) healHp * per);
			// System.out.println("����ȸ�� :"+healHp);
			// System.out.println("ȸ������ :"+addhp);
			healHp += addhp + pc.getPotionPlus();
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�������)) {
			int atklv = pc.������󵵰����ڷ���;
			int dflv = pc.getLevel();
			double ���Ϸ� = 0.65;
			if (atklv > dflv) {
				���Ϸ� += (atklv - dflv) * 0.05;
			}
			if ( pc.getInventory().checkEquipped(490022)){//ȸ������
				int enchant = pc.getInventory().getEnchantCount(490022);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if ( pc.getInventory().checkEquipped(500008)){//��Ƽ��
				int enchant = pc.getInventory().getEnchantCount(500008);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if ( pc.getInventory().checkEquipped(502008)){//�� ��Ƽ��
				int enchant = pc.getInventory().getEnchantCount(502008);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if ( pc.getInventory().checkEquipped(21027)){//�뿹�� �Ͱ���
				int enchant = pc.getInventory().getEnchantCount(21027);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if ( pc.getInventory().checkEquipped(21258)){//���뽺 ������ �����
				int enchant = pc.getInventory().getEnchantCount(21027);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if ( pc.getInventory().checkEquipped(21260)){////������ �����
				int enchant = pc.getInventory().getEnchantCount(21027);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if ( pc.getInventory().checkEquipped(222346)){//�ż��� �Ϸ��� �����
				int enchant = pc.getInventory().getEnchantCount(21027);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if ( pc.getInventory().checkEquipped(222347)){//�ż��� ��ø�� �����
				int enchant = pc.getInventory().getEnchantCount(21027);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if ( pc.getInventory().checkEquipped(222348)){//�ż��� ������ �����
				int enchant = pc.getInventory().getEnchantCount(21027);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if ( pc.getInventory().checkEquipped(222349)){//�ż��� ������ �����
				int enchant = pc.getInventory().getEnchantCount(21027);
				���Ϸ� += (enchant < 1 ? 2 : (enchant + 1) * 2) / 100;
			}
			if (���Ϸ� > 0.9) {
				���Ϸ� = 0.9;
			}
		
	
			healHp -= (int) ((double) healHp * ���Ϸ�);
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(POLLUTE_WATER)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(10517)) { // ����Ʈ��Ÿ����
																		// ȸ����1/2��
			healHp /= 2;
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(10513)) {
			pc.receiveDamage(pc, healHp, true);
		} else {
			pc.setCurrentHp(pc.getCurrentHp() + healHp);
		}
	}
}
