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

import static l1j.server.server.model.skill.L1SkillId.SHAPE_CHANGE;

import java.util.Random;

import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.server.ActionCodes;
import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_ItemName;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_ShowPolyList;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Skills;

public class PolyWand extends L1ItemInstance {
	private static final long serialVersionUID = 1L;
	private static Random _random = new Random(System.nanoTime());

	public PolyWand(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			// int itemId = this.getItemId();
			int spellsc_objid = 0;
			int spellsc_x = 0;
			int spellsc_y = 0;
			String polyName = null;
			spellsc_objid = packet.readD();
			if (pc.getInventory().checkEquipped(20281)
					|| pc.getInventory().checkEquipped(120281)) {
				polyName = packet.readS();
				spellsc_x = pc.getX();
				spellsc_y = pc.getY();
			} else {
				spellsc_x = packet.readH();
				spellsc_y = packet.readH();
			}

			if (pc.getMapId() == 63 || pc.getMapId() == 552
					|| pc.getMapId() == 555 || pc.getMapId() == 557
					|| pc.getMapId() == 558 || pc.getMapId() == 779) { // HC4f������
																		// ����
																		// ���߿�����
																		// ��� �Ұ�
				pc.sendPackets(new S_ServerMessage(563));
			} else {
				int heding = CharPosUtil.targetDirection(pc, spellsc_x,
						spellsc_y);
				pc.getMoveState().setHeading(heding);
				pc.sendPackets(new S_AttackPacket(pc, 0,
						ActionCodes.ACTION_Wand), true);
				Broadcaster.broadcastPacket(pc, new S_AttackPacket(pc, 0,
						ActionCodes.ACTION_Wand), true);
				// int chargeCount = useItem.getChargeCount();
				if (/*
					 * chargeCount <= 0 && itemId != 40410 ||
					 */pc.getGfxId().getTempCharGfx() == 6034
						|| pc.getGfxId().getTempCharGfx() == 6035) {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."),
							true);
					return;
				}

				if (polyName != null) {
					if (pc.isParalyzed()) {
						return;
					}
					

					if(polyName.equalsIgnoreCase("ranking class polymorph"))
					{
						if(pc.isCrown()) {
							if(pc.get_sex() == 0)	polyName = "rangking prince male";
							else					polyName = "rangking prince female";
						} else if(pc.isKnight()) {
							if(pc.get_sex() == 0)	polyName = "rangking knight male";
							else					polyName = "rangking knight female";
						} else if(pc.isElf()) {
							if(pc.get_sex() == 0)	polyName = "rangking elf male";
							else					polyName = "rangking elf female";
						} else if(pc.isWizard()) {
							if(pc.get_sex() == 0)	polyName = "rangking wizard male";
							else					polyName = "rangking wizard female";
						} else if(pc.isDarkelf()) {
							if(pc.get_sex() == 0)	polyName = "rangking darkelf male";
							else					polyName = "rangking darkelf female";
						} else if(pc.isDragonknight()) {
							if(pc.get_sex() == 0)	polyName = "rangking dragonknight male";
							else					polyName = "rangking dragonknight female";
						} else if(pc.isIllusionist()) {
							if(pc.get_sex() == 0)	polyName = "rangking illusionist male";
							else					polyName = "rangking illusionist female";
						} else if(pc.isWarrior()) {
							if(pc.get_sex() == 0)	polyName = "rangking warrior male";
							else					polyName = "rangking warrior female";
						}
					}
					
					if(polyName.startsWith("rangking "))
					{
						int star = UserRankingController.getInstance().getStarCount(pc.getName());
						if(star != 3 && star != 4)
							return;
					}

					// ��ǳ����� 11328�̻� ���� �̿��� �����Ұ�� ���׷� ����
					L1PolyMorph poly = PolyTable.getInstance().getTemplate(
							polyName);
					if ((poly.getPolyId() != 6698 && poly.getPolyId() != 6697
							&& poly.getPolyId() != 10870
							&& poly.getPolyId() != 8719
							&& poly.getPolyId() != 7846 && poly.getPolyId() != 7967)
							&& (poly.getPolyId() < 11328
									|| poly.getPolyId() == 11487 || poly
									.getPolyId() == 11498)) {
						return;
					}
					L1PolyMorph.handleCommands(pc, polyName);
					pc.cancelAbsoluteBarrier();
					pc.getInventory().removeItem(useItem, 1);
					/** 2011.07.01 ������ ������ ������ ��Ȯ������ �Ǵ� ���� */
					if (useItem.isIdentified()) {
						useItem.setIdentified(true);
						pc.sendPackets(new S_ItemName(useItem), true);
					}
					return;
				}
				L1Object target = L1World.getInstance().findObject(
						spellsc_objid);
//useItem.getItemId()
				if (target != null) {
					L1Character character = (L1Character) target;
				
					if (useItem.getItemId() == 60167) // �Ƚ� ���Ÿ���
						polyPixieAction(pc, character);
					else
						polyAction(pc, character);
					pc.cancelAbsoluteBarrier();
					
					  if (useItem.getItemId() == 40008 || useItem.getItemId() == 140008) {
					//  useItem.setChargeCount(useItem.getChargeCount() - 1);
							pc.getInventory().removeItem(useItem, 1);  
					  
					  pc.getInventory().updateItem(useItem,L1PcInventory.COL_CHARGE_COUNT); 
					  } else {
					 
					pc.getInventory().removeItem(useItem, 1);
					 }
					  

					/*if (useItem.getChargeCount() == 0) {
					 pc.getInventory().removeItem(useItem, 1);
					 }*/

					/** 2011.07.01 ������ ������ ������ ��Ȯ������ �Ǵ� ���� */
					if (useItem.isIdentified()) {
						useItem.setIdentified(true);
						pc.sendPackets(new S_ItemName(useItem), true);
					}
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."),
							true);
				}
			}
		}
	}

	private static final int[] polyPixie = { 7968, 8126, 7407, 7848 };

	private void polyPixieAction(L1PcInstance attacker, L1Character cha) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			L1PolyMorph.doPoly(pc,
					polyPixie[_random.nextInt(polyPixie.length)], 1200,
					L1PolyMorph.MORPH_BY_ITEMMAGIC);
			if (attacker.getId() != pc.getId()) {
				pc.sendPackets(new S_ServerMessage(241, attacker.getName()),
						true); // %0�� ����� ���Ž��׽��ϴ�.
			}
		}
	}

	private void polyAction(L1PcInstance attacker, L1Character cha) {
		boolean isSameClan = false;
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			if (pc.getClanid() != 0 && attacker.getClanid() == pc.getClanid()) {
				isSameClan = true;
			}
		}
		if (cha instanceof L1MonsterInstance) {
			return;
		}
		if (attacker.getId() != cha.getId() && !isSameClan) {
			int probability = 3 * (attacker.getLevel() - cha.getLevel())
					- cha.getResistance().getEffectedMrBySkill();
			int rnd = _random.nextInt(100) + 1;
			if (rnd > probability) {
				return;
			}
		}
		/*
		 * int pid = _random.nextInt(polyArray.length); int polyId =
		 * polyArray[pid];
		 */

		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			/*
			 * if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.
			 * SCALES_EARTH_DRAGON) ||
			 * pc.getSkillEffectTimerSet().hasSkillEffect
			 * (L1SkillId.SCALES_FIRE_DRAGON) ||
			 * pc.getSkillEffectTimerSet().hasSkillEffect
			 * (L1SkillId.SCALES_WATER_DRAGON)){ pc.sendPackets(new
			 * S_ServerMessage(1384), true); return; }
			 */
			
			if (pc.getInventory().checkEquipped(20281)) { 
				pc.sendPackets(new S_ShowPolyList(pc.getId()), true); 
				if (!pc.isShapeChange()) {
					pc.setShapeChange(true); 
				} //
				pc.sendPackets(new S_ServerMessage(966), true); 
				// string-j.tbl:968��°
				// ������ ���� ����  ��ȣ�˴ϴ�. 
				// ���Ŷ��� �޼�����, Ÿ���� �ڽ��� ���Ž����� ���� ������ �޼����� ������ ������ ���� ������ �޼����ܴ̿� �����ϴ�.
			} 
			 
			int randomValue = 11;

			if (pc.getLevel() >= 15)
				randomValue += 9;
			if (pc.getLevel() >= 30)
				randomValue += 18;
			int polyId = 11328 + _random.nextInt(randomValue);
			if (polyId >= 11358 && polyId <= 11361) {
				polyId = 11371 + _random.nextInt(4);
			} else if (polyId >= 11362 && polyId <= 11365) {
				polyId = 11396 + _random.nextInt(4);
			}
			/*
			 * if(pc.getLevel() >= 15) randomValue += 9; if(pc.getLevel() >= 30)
			 * randomValue += 11; if(pc.getLevel() >= 45) randomValue += 6;
			 * if(pc.getLevel() >= 50) randomValue += 6; if(pc.getLevel() >= 52)
			 * randomValue += 9; if(pc.getLevel() >= 55) randomValue += 4;
			 * if(pc.getLevel() >= 60) randomValue += 4; if(pc.getLevel() >= 65)
			 * randomValue += 4; if(pc.getLevel() >= 70) randomValue += 8;
			 * if(pc.getLevel() >= 75) randomValue += 4; if(pc.getLevel() >= 80)
			 * randomValue += 4; int polyId =
			 * 11328+_random.nextInt(randomValue);
			 */
			L1Skills skillTemp = SkillsTable.getInstance().getTemplate(
					SHAPE_CHANGE);
			L1PolyMorph.doPoly(pc, polyId, skillTemp.getBuffDuration(),
					L1PolyMorph.MORPH_BY_ITEMMAGIC);
			if (attacker.getId() != pc.getId()) {
				pc.sendPackets(new S_ServerMessage(241, attacker.getName()),
						true); // %0�� ����� ���Ž��׽��ϴ�.
			}
			// }
		} else if (cha instanceof L1MonsterInstance) {
			L1MonsterInstance mob = (L1MonsterInstance) cha;
			if (mob.getLevel() < 50) {
				int polyId = 11328 + _random.nextInt(20);
				int npcId = mob.getNpcTemplate().get_npcId();
				if (npcId != 45338 && npcId != 45370 && npcId != 45456
						&& npcId != 45464 && npcId != 45473 && npcId != 45488
						&& npcId != 45497 && npcId != 45516 && npcId != 45529
						&& npcId != 45458) {
					L1Skills skillTemp = SkillsTable.getInstance().getTemplate(
							SHAPE_CHANGE);
					L1PolyMorph.doPoly(mob, polyId,
							skillTemp.getBuffDuration(),
							L1PolyMorph.MORPH_BY_ITEMMAGIC);
				}
			}
		}
	}
}
