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

import static l1j.server.server.model.skill.L1SkillId.COOKING_1_0_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_0_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_10_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_10_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_11_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_11_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_12_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_12_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_13_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_13_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_14_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_14_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_15_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_15_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_16_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_16_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_17_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_17_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_18_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_18_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_19_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_19_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_1_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_1_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_20_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_20_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_21_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_21_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_22_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_22_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_23_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_23_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_2_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_2_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_3_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_3_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_4_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_4_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_5_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_5_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_6_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_6_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_7_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_7_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_8_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_8_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_9_N;
import static l1j.server.server.model.skill.L1SkillId.COOKING_1_9_S;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�߰��;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_����;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_ĥ����;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�ѿ�;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING;
import static l1j.server.server.model.skill.L1SkillId.SPECIAL_COOKING2;
import static l1j.server.server.model.skill.L1SkillId.��Ƽ����������;
import static l1j.server.server.model.skill.L1SkillId.��Ƽ�������丮;
import static l1j.server.server.model.skill.L1SkillId.���̸����Ѷ��;
import static l1j.server.server.model.skill.L1SkillId.���̽ÿ�������;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_HPUpdate;
import l1j.server.server.serverpackets.S_MPUpdate;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_OwnCharStatus2;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_ServerMessage;

// Referenced classes of package l1j.server.server.model:
// L1Cooking

public class L1Cooking {

	private L1Cooking() {
	}

	public static void useNewCookingItem(L1PcInstance pc, L1ItemInstance item) {
		int itemId = item.getItem().getItemId();
		int cookingId = 0;
		switch (itemId) {
		case 60075:// �������� // �߰�� ����
			cookingId = COOKING_NEW_�߰��;
			break;
		case 60073:// Ű���зԱ��� // ���� ���� ��
			cookingId = COOKING_NEW_����;
			break;
		case 60074:// �Ź̴ٸ���ġ���� // ������ ĥ���� ����
			cookingId = COOKING_NEW_ĥ����;
			break;
		case 60072:// ��ī������ũ // �ѿ� ������ũ
			cookingId = COOKING_NEW_�ѿ�;
			break;
		case 60259:
			cookingId = ���̸����Ѷ��;
			break;
		case 60260:
			cookingId = ���̽ÿ�������;
			break;
		/*
		 * case 11111://��Ƽ���� �������� �丮 cookingId = ��Ƽ�������丮; break; case
		 * 111112://��Ƽ���� �������� �丮 cookingId = ��Ƽ����������; break;
		 */
		default:
			return;
		}
		if (cookingId == ��Ƽ�������丮 || cookingId == ���̸����Ѷ��
				|| cookingId == ���̽ÿ�������)
			newEatCooking(pc, cookingId, 920);
		else if (cookingId == ��Ƽ����������)
			newEatCooking(pc, cookingId, 900);
		else
			newEatCooking(pc, cookingId, 1800);
		if (cookingId != ���̸����Ѷ�� && cookingId != ���̽ÿ�������)
			pc.sendPackets(new S_ServerMessage(76, item.getNumberedName(1)));
		pc.getInventory().removeItem(item, 1);
		pc.sendPackets(new S_EffectLocation(pc.getX(), pc.getY(), 6392));
	}

	public static void newEatCooking(L1PcInstance pc, int cookingId, int time) {
		int cookingType = 0;
		switch (cookingId) {
		case ���̸����Ѷ��:
			cookingType = 161;
			pc.addHitup(2);
			pc.addDmgup(2);
			pc.addBowHitup(2);
			pc.addBowDmgup(2);
			pc.addHpr(3);
			pc.addMpr(4);
			pc.getAbility().addSp(2);
			pc.getResistance().addMr(15);
			pc.sendPackets(new S_SPMR(pc));
			pc.getResistance().addAllNaturalResistance(10);
			pc.sendPackets(new S_OwnCharAttrDef(pc));
			break;
		case ���̽ÿ�������:
			cookingType = 162;
			break;
		case COOKING_NEW_�߰��:
			cookingType = 160;
			
			break;
		case ��Ƽ����������:
			cookingType = 162;
			break;
		case COOKING_NEW_����:// Ű���зԱ��� // ���� ���� ��
			cookingType = 158;
			pc.addBowHitup(1);
			pc.addBowDmgup(2);
			pc.addHpr(2);
			pc.addMpr(2);
			pc.getResistance().addMr(10);
			pc.sendPackets(new S_SPMR(pc));
			pc.getResistance().addAllNaturalResistance(10);
			pc.sendPackets(new S_OwnCharAttrDef(pc));
			
			break;
		case COOKING_NEW_ĥ����:
			cookingType = 159;
			pc.addHpr(2);
			pc.addMpr(3);
			pc.getAbility().addSp(2);
			pc.getResistance().addMr(10);
			pc.sendPackets(new S_SPMR(pc));
			pc.getResistance().addAllNaturalResistance(10);
			pc.sendPackets(new S_OwnCharAttrDef(pc));
			
			break;
		case COOKING_NEW_�ѿ�:
			cookingType = 157;
			pc.addHitup(1);
			pc.addDmgup(2);
			pc.addHpr(2);
			pc.addMpr(2);
			pc.getResistance().addMr(10);
			pc.sendPackets(new S_SPMR(pc));
			pc.getResistance().addAllNaturalResistance(10);
			pc.sendPackets(new S_OwnCharAttrDef(pc));
			
			break;
		case ��Ƽ�������丮:
			cookingType = 151;
			pc.addHitup(2);
			pc.addDmgup(2);
			pc.addBowHitup(2);
			pc.addBowDmgup(2);
			pc.getAbility().addSp(2);
			pc.addHpr(3);
			pc.addMpr(4);
			pc.getResistance().addMr(15);
			pc.sendPackets(new S_SPMR(pc));
			pc.getResistance().addAllNaturalResistance(10);
			pc.sendPackets(new S_OwnCharAttrDef(pc));
			break;
		default:
			return;
		}

		int cookingId2 = 0;
		if (cookingId == COOKING_NEW_�߰�� || cookingId == ��Ƽ����������
				|| cookingId == ���̽ÿ�������)
			cookingId2 = pc.getDessertId();
		else
			cookingId2 = pc.getCookingId();
		if (cookingId2 != 0) {
			pc.getSkillEffectTimerSet().removeSkillEffect(cookingId2);
		}

		pc.sendPackets(new S_PacketBox(53, cookingType, time));
		pc.getSkillEffectTimerSet().setSkillEffect(cookingId, time * 1000);
		if (cookingId == COOKING_NEW_�߰�� || cookingId == ��Ƽ����������
				|| cookingId == ���̽ÿ�������)
			pc.setDessertId(cookingId);
		else
			pc.setCookingId(cookingId);

		if (pc.get_food() < 225) {
			pc.set_food(pc.get_food() + 12);// �丮������ ����İ����� �ø��κ�..
			if (pc.get_food() > 225)
				pc.set_food(225);
			pc.sendPackets(new S_PacketBox(S_PacketBox.FOOD, pc.get_food()));
		}
		pc.sendPackets(new S_OwnCharStatus(pc));
	}

	public static void useCookingItem(L1PcInstance pc, L1ItemInstance item) {
		int itemId = item.getItem().getItemId();
		/*
		 * if (itemId == 41284 //���� ���� || itemId == 49056 //ũ���� ���� || itemId ==
		 * 49064 //ȯ���� ũ���� ���� || itemId == 41292 // ȯ���� �������� || itemId ==
		 * L1ItemId.COOKFOOD_BASILIST_EGG_SOUP //�ٽǸ���ũ �� ���� || itemId ==
		 * L1ItemId.SCOOKFOOD_BASILIST_EGG_SOUP) { //ȯ���� �ٽǸ���ũ �� ���� if
		 * (pc.get_food() != 225) { // 100% pc.sendPackets(new
		 * S_SystemMessage("�ش� �丮�� ������ 100%������ ������ �ֽ��ϴ�.")); //
		 * pc.sendPackets(new S_ServerMessage(74, item.getNumberedName(1)));
		 * return; }
		 * 
		 * }
		 */

		if (itemId >= 41277
				&& itemId <= 41283 // 1�� �丮
				|| itemId >= 49049
				&& itemId <= 49056// 2�� �丮
				|| itemId >= L1ItemId.NORMAL_COOKFOOD_3RD_START
				&& itemId <= L1ItemId.COOKFOOD_DEEP_SEA_FISH_STEW// 3�� �丮
				|| itemId >= 41285
				&& itemId <= 41291// 1�� ȯ���� �丮
				|| itemId >= 49057
				&& itemId <= 49064// 2�� ȯ���� �丮
				|| itemId >= L1ItemId.SPECIAL_COOKFOOD_3RD_START
				&& itemId <= L1ItemId.SCOOKFOOD_DEEP_SEA_FISH_STEW) {// 3�� ȯ����
																		// �丮
			int cookingId = pc.getCookingId();
			if (cookingId != 0) {
				pc.getSkillEffectTimerSet().removeSkillEffect(cookingId);
			}
		}

		if (itemId == 41284 // ���� ����
				|| itemId == 49056// ũ���� ����
				|| itemId == 49064// ȯ���� ũ���� ����
				|| itemId == 41292// ȯ���� ���� ����
				|| itemId == L1ItemId.COOKFOOD_BASILIST_EGG_SOUP // �ٽǸ���ũ �� ����
				|| itemId == L1ItemId.SCOOKFOOD_BASILIST_EGG_SOUP) {// ȯ���� �ٽǸ���ũ
																	// �� ����
			int dessertId = pc.getDessertId();
			if (dessertId != 0) {
				pc.getSkillEffectTimerSet().removeSkillEffect(dessertId);
			}
		}

		/** 1�� �丮 ȿ�� */
		int cookingId;
		int time = 900;
		switch (itemId) {
		case 41277:
		case 41285:
			if (itemId == 41277) {
				cookingId = COOKING_1_0_N;
			} else {
				cookingId = COOKING_1_0_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 41278:
		case 41286:
			if (itemId == 41278) {
				cookingId = COOKING_1_1_N;
			} else {
				cookingId = COOKING_1_1_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 41279:
		case 41287:
			if (itemId == 41279) {
				cookingId = COOKING_1_2_N;
			} else {
				cookingId = COOKING_1_2_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 41280:
		case 41288:
			if (itemId == 41280) {
				cookingId = COOKING_1_3_N;
			} else {
				cookingId = COOKING_1_3_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 41281:
		case 41289:
			if (itemId == 41281) {
				cookingId = COOKING_1_4_N;
			} else {
				cookingId = COOKING_1_4_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 41282:
		case 41290:
			if (itemId == 41282) {
				cookingId = COOKING_1_5_N;
			} else {
				cookingId = COOKING_1_5_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 41283:
		case 41291:
			if (itemId == 41283) {
				cookingId = COOKING_1_6_N;
			} else {
				cookingId = COOKING_1_6_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 41284:
		case 41292:
			if (itemId == 41284) {
				cookingId = COOKING_1_7_N;
			} else {
				cookingId = COOKING_1_7_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 49049:
		case 49057:
			if (itemId == 49049) {
				cookingId = COOKING_1_8_N;
			} else {
				cookingId = COOKING_1_8_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 49050:
		case 49058:
			if (itemId == 49050) {
				cookingId = COOKING_1_9_N;
			} else {
				cookingId = COOKING_1_9_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 49051:
		case 49059:
			if (itemId == 49051) {
				cookingId = COOKING_1_10_N;
			} else {
				cookingId = COOKING_1_10_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 49052:
		case 49060:
			if (itemId == 49052) {
				cookingId = COOKING_1_11_N;
			} else {
				cookingId = COOKING_1_11_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 49053:
		case 49061:
			if (itemId == 49053) {
				cookingId = COOKING_1_12_N;
			} else {
				cookingId = COOKING_1_12_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 49054:
		case 49062:
			if (itemId == 49054) {
				cookingId = COOKING_1_13_N;
			} else {
				cookingId = COOKING_1_13_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 49055:
		case 49063:
			if (itemId == 49055) {
				cookingId = COOKING_1_14_N;
			} else {
				cookingId = COOKING_1_14_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case 49056:
		case 49064:
			if (itemId == 49056) {
				cookingId = COOKING_1_15_N;
			} else {
				cookingId = COOKING_1_15_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case L1ItemId.COOKFOOD_CRUSTCEA_CLAW_CHARCOAL:
		case L1ItemId.SCOOKFOOD_CRUSTCEA_CLAW_CHARCOAL:
			if (itemId == L1ItemId.COOKFOOD_CRUSTCEA_CLAW_CHARCOAL) {
				cookingId = COOKING_1_16_N;
			} else {
				cookingId = COOKING_1_16_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case L1ItemId.COOKFOOD_GRIFFON_CHARCOAL:
		case L1ItemId.SCOOKFOOD_GRIFFON_CHARCOAL:
			if (itemId == L1ItemId.COOKFOOD_GRIFFON_CHARCOAL) {
				cookingId = COOKING_1_17_N;
			} else {
				cookingId = COOKING_1_17_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case L1ItemId.COOKFOOD_COCKATRICE_STEAK:
		case L1ItemId.SCOOKFOOD_COCKATRICE_STEAK:
			if (itemId == L1ItemId.COOKFOOD_COCKATRICE_STEAK) {
				cookingId = COOKING_1_18_N;
			} else {
				cookingId = COOKING_1_18_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case L1ItemId.COOKFOOD_TURTLEKING_CHARCOAL:
		case L1ItemId.SCOOKFOOD_TURTLEKING_CHARCOAL:
			if (itemId == L1ItemId.COOKFOOD_TURTLEKING_CHARCOAL) {
				cookingId = COOKING_1_19_N;
			} else {
				cookingId = COOKING_1_19_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case L1ItemId.COOKFOOD_LESSERDRAGON_WING_SKEWER:
		case L1ItemId.SCOOKFOOD_LESSERDRAGON_WING_SKEWER:
			if (itemId == L1ItemId.COOKFOOD_LESSERDRAGON_WING_SKEWER) {
				cookingId = COOKING_1_20_N;
			} else {
				cookingId = COOKING_1_20_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case L1ItemId.COOKFOOD_DRAKE_CHARCOAL:
		case L1ItemId.SCOOKFOOD_DRAKE_CHARCOAL:
			if (itemId == L1ItemId.COOKFOOD_DRAKE_CHARCOAL) {
				cookingId = COOKING_1_21_N;
			} else {
				cookingId = COOKING_1_21_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case L1ItemId.COOKFOOD_DEEP_SEA_FISH_STEW:
		case L1ItemId.SCOOKFOOD_DEEP_SEA_FISH_STEW:
			if (itemId == L1ItemId.COOKFOOD_DEEP_SEA_FISH_STEW) {
				cookingId = COOKING_1_22_N;
			} else {
				cookingId = COOKING_1_22_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		case L1ItemId.COOKFOOD_BASILIST_EGG_SOUP:
		case L1ItemId.SCOOKFOOD_BASILIST_EGG_SOUP:
			if (itemId == L1ItemId.COOKFOOD_BASILIST_EGG_SOUP) {
				cookingId = COOKING_1_23_N;
			} else {
				cookingId = COOKING_1_23_S;
			}
			eatCooking(pc, cookingId, time);
			break;
		}

		pc.sendPackets(new S_ServerMessage(76, item.getNumberedName(1)));
		pc.getInventory().removeItem(item, 1);
	}

	/** 1���丮 ȿ�� */
	public static void eatCooking(L1PcInstance pc, int cookingId, int time) {
		int cookingType = 0;

		switch (cookingId) {
		case COOKING_1_0_N:// ������ ������ũ
		case COOKING_1_0_S:
			cookingType = 0;
			pc.getResistance().addAllNaturalResistance(10);
			pc.sendPackets(new S_OwnCharAttrDef(pc));
			break;
		case COOKING_1_1_N:// ����� ����
		case COOKING_1_1_S:
			cookingType = 1;
			pc.addMaxHp(30);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			if (pc.isInParty()) {
				pc.getParty().updateMiniHP(pc);
			}
			break;
		case COOKING_1_2_N:// ��ȣ��
		case COOKING_1_2_S:
			pc.addMpr(3);
			cookingType = 2;
			break;
		case COOKING_1_3_N:// ���̴ٸ�ġ���
		case COOKING_1_3_S:
			cookingType = 3;
			pc.getAC().addAc(-1);
			// pc.sendPackets(new S_OwnCharStatus(pc));
			break;
		case COOKING_1_4_N:// ���ϻ�����
		case COOKING_1_4_S:
			cookingType = 4;
			pc.addMaxMp(20);
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
			break;
		case COOKING_1_5_N:// ����������
		case COOKING_1_5_S:
			cookingType = 5;
			pc.addHpr(3);
			break;
		case COOKING_1_6_N:// ����� ��ġ ����
		case COOKING_1_6_S:
			cookingType = 6;
			pc.getResistance().addMr(5);
			pc.sendPackets(new S_SPMR(pc));
			break;
		case COOKING_1_7_N:// ��������
		case COOKING_1_7_S:
			cookingType = 7;
			break;
		/** 1���丮 ȿ���� */
		case COOKING_1_8_N:// ĳ���ī����
		case COOKING_1_8_S:
			cookingType = 16;
			pc.addHitup(1);
			pc.addDmgup(1);
			break;
		case COOKING_1_9_N:// �Ǿ����ũ
		case COOKING_1_9_S:
			cookingType = 17;
			pc.addMaxHp(30);
			pc.addMaxMp(30);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			if (pc.isInParty()) {
				pc.getParty().updateMiniHP(pc);
			}
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
			break;
		case COOKING_1_10_N:// ��Ʋ�巡�����
		case COOKING_1_10_S:
			cookingType = 18;
			pc.getAC().addAc(-2);
			pc.sendPackets(new S_OwnCharStatus2(pc));
			break;
		case COOKING_1_11_N:// Ű���зԱ���
		case COOKING_1_11_S:
			pc.addBowHitup(1);
			pc.addBowDmgup(1);
			cookingType = 19;
			break;
		case COOKING_1_12_N:// �����ǿ±���
		case COOKING_1_12_S:
			pc.addHpr(2);
			pc.addMpr(2);
			cookingType = 20;
			break;
		case COOKING_1_13_N:// �Ϸ�ī�ҽ�Ʃ
		case COOKING_1_13_S:
			cookingType = 21;
			pc.getResistance().addMr(10);
			pc.sendPackets(new S_SPMR(pc));
			break;
		case COOKING_1_14_N:// �Ź̴ٸ���ġ����
		case COOKING_1_14_S:
			cookingType = 22;
			pc.getAbility().addSp(1);
			pc.sendPackets(new S_SPMR(pc));
			break;
		case COOKING_1_15_N:// ũ���콺��
		case COOKING_1_15_S:
			cookingType = 23;
			break;
		/** 2���丮 ȿ���� */
		case COOKING_1_16_N:// �þ����Թ߱���
		case COOKING_1_16_S:
			cookingType = 45;
			pc.addBowHitup(2);
			pc.addBowDmgup(1);
			break;
		case COOKING_1_17_N:// �׸�������
		case COOKING_1_17_S:
			cookingType = 46;
			pc.addMaxHp(50);
			pc.addMaxMp(50);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			if (pc.isInParty()) {
				pc.getParty().updateMiniHP(pc);
			}
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
			break;
		case COOKING_1_18_N:// ��ī������ũ
		case COOKING_1_18_S:
			cookingType = 47;
			pc.addHitup(2);
			pc.addDmgup(1);
			break;
		case COOKING_1_19_N:// ��հźϱ���
		case COOKING_1_19_S:
			cookingType = 48;
			pc.getAC().addAc(-3);
			pc.sendPackets(new S_OwnCharStatus2(pc));
			break;
		case COOKING_1_20_N:// ����������ġ
		case COOKING_1_20_S:
			cookingType = 49;
			pc.getResistance().addAllNaturalResistance(10);
			pc.getResistance().addMr(15);
			pc.sendPackets(new S_SPMR(pc));
			pc.sendPackets(new S_OwnCharAttrDef(pc));
			break;
		case COOKING_1_21_N:// �巹��ũ����
		case COOKING_1_21_S:
			cookingType = 50;
			pc.addMpr(2);
			pc.getAbility().addSp(2);
			pc.sendPackets(new S_SPMR(pc));
			break;
		case COOKING_1_22_N:// ���ؾƩ
		case COOKING_1_22_S:
			cookingType = 51;
			pc.addHpr(2);
			pc.addMaxHp(30);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
			if (pc.isInParty()) {
				pc.getParty().updateMiniHP(pc);
			}
			break;
		case COOKING_1_23_N:// �ٽǽ���
		case COOKING_1_23_S:
			cookingType = 52;
			break;
		default:
			break;
		}

		pc.sendPackets(new S_PacketBox(53, cookingType, time));
		pc.getSkillEffectTimerSet().setSkillEffect(cookingId, time * 1000);

		if (cookingId == COOKING_1_7_S || cookingId == COOKING_1_15_S
				|| cookingId == COOKING_1_23_S) {
			if (!pc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING2)) {
				pc.getSkillEffectTimerSet().setSkillEffect(SPECIAL_COOKING2,
						time * 1000);
			}
		} else if (cookingId >= COOKING_1_0_S && cookingId <= COOKING_1_23_S) {
			if (!pc.getSkillEffectTimerSet().hasSkillEffect(SPECIAL_COOKING)) {
				pc.getSkillEffectTimerSet().setSkillEffect(SPECIAL_COOKING,
						time * 1000);
			}
		}

		if (cookingId == COOKING_1_7_N || cookingId == COOKING_1_7_S
				|| cookingId == COOKING_1_15_N || cookingId == COOKING_1_15_S
				|| cookingId == COOKING_1_23_N || cookingId == COOKING_1_23_S) {
			pc.setDessertId(cookingId);
		} else {
			pc.setCookingId(cookingId);
		}

		if (pc.get_food() < 225) {
			pc.set_food(pc.get_food() + 12);// �丮������ ����İ����� �ø��κ�..
			if (pc.get_food() > 225)
				pc.set_food(225);
			pc.sendPackets(new S_PacketBox(S_PacketBox.FOOD, pc.get_food()));
		}

		/*
		 * if(cookingType == 7){ pc.set_food(180);//�丮������ ����İ����� �������ºκ�..
		 * pc.sendPackets(new S_PacketBox(S_PacketBox.FOOD, pc.get_food())); }
		 */

		pc.sendPackets(new S_OwnCharStatus(pc));
	}

}