package l1j.server.server.model.item.function;

import java.util.Random;

import l1j.server.Config;
import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.ItemEnchantList;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;

public class EnchantWeapon extends Enchant {
	private static final long serialVersionUID = 1L;
	private static Random _random = new Random(System.nanoTime());

	public EnchantWeapon(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			int itemId = this.getItemId();
			L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(packet.readD());

			if (l1iteminstance1 == null) {
				// System.out.println("무기 인챈 버그 의심"+pc.getName());
				pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																				// 아무것도
																				// 일어나지
																				// 않았습니다.
				return;
			}

			if (pc.getLastEnchantItemid() == l1iteminstance1.getId()) {
				pc.setLastEnchantItemid(l1iteminstance1.getId(), l1iteminstance1);
				return;
			}
			if (l1iteminstance1 == null || l1iteminstance1.getItem().getType2() != 1) {
				pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																				// 아무것도
																				// 일어나지
																				// 않았습니다.
				return;
			}
			if (l1iteminstance1.getBless() >= 128) { // 봉인템
				pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																				// 아무것도
																				// 일어나지
																				// 않았습니다.
				return;
			}
			int safe_enchant = l1iteminstance1.getItem().get_safeenchant();
			if (safe_enchant < 0) { // 강화 불가
				pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																				// 아무것도
																				// 일어나지
																				// 않았습니다.
				return;
			}
			int weaponId = l1iteminstance1.getItem().getItemId();
			if (weaponId >= 246 && weaponId <= 249) { // 강화 불가
				if (itemId == L1ItemId.SCROLL_OF_ENCHANT_QUEST_WEAPON) {// 시련의
																		// 스크롤
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																					// 아무것도
																					// 일어나지
																					// 않았습니다.
					return;
				}
			}
			if (itemId == L1ItemId.SCROLL_OF_ENCHANT_QUEST_WEAPON) {
				// 시련의 스크롤
				if (weaponId >= 246 && weaponId <= 249) { // 강화 불가
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																					// 아무것도
																					// 일어나지
																					// 않았습니다.
					return;
				}
			}
			/** 아놀드 무기 마법 주문서 **/
			if (weaponId >= 307 && weaponId <= 314) {
				if (itemId == 30146) {
				} else {
					pc.sendPackets(new S_ServerMessage(79)); // \f1 아무것도 일어나지
																// 않았습니다.
					return;
				}
			}
			if (itemId == 30146) {
				if (weaponId >= 307 && weaponId <= 314) {
				} else {
					pc.sendPackets(new S_ServerMessage(79)); // \f1 아무것도 일어나지
																// 않았습니다.
					return;
				}
			}
			/** 환상의 무기 마법 주문서 **/
			if (weaponId >= 413000 && weaponId <= 413007) { // 이외에 강화 불가
				if (itemId == L1ItemId.SCROLL_OF_ENCHANT_FANTASY_WEAPON) {// 환상의무기마법주문서
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																					// 아무것도
																					// 일어나지
																					// 않았습니다.
					return;
				}
			}
			if (itemId == L1ItemId.SCROLL_OF_ENCHANT_FANTASY_WEAPON) {// 환상의무기마법주문서
				if (weaponId >= 413000 && weaponId <= 413007) { // 이외에 강화 불가
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																					// 아무것도
																					// 일어나지
																					// 않았습니다.
					return;
				}
			}

			/** 환상의 무기 마법 주문서 **/
			if (weaponId >= 284 && weaponId <= 290) { // 이외에 강화 불가
				if (itemId == 60473 && l1iteminstance1.getEnchantLevel() < 10) {// 환상의무기마법주문서
																				// (10검)
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																					// 아무것도
																					// 일어나지
																					// 않았습니다.
					return;
				}
			}
			if (itemId == 60473) {// 환상의무기마법주문서 (10검)
				if (weaponId >= 284 && weaponId <= 290) { // 이외에 강화 불가
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																					// 아무것도
																					// 일어나지
																					// 않았습니다.
					return;
				}
			}

			/** 환상의 무기 마법 주문서 **/
			if (weaponId >= 90085 && weaponId <= 90092) { // 이외에 강화 불가
				if (itemId == 160510) {// 환상의무기마법주문서 (10검)
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																					// 아무것도
																					// 일어나지
																					// 않았습니다.
					return;
				}
			}
			if (itemId == 160510) {// 환상의무기마법주문서 (10검)
				if (weaponId >= 90085 && weaponId <= 90092) { // 이외에 강화 불가
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // \f1
																					// 아무것도
																					// 일어나지
																					// 않았습니다.
					return;
				}
			}

			if (weaponId == 7 || weaponId == 35 || weaponId == 48 || weaponId == 73 || weaponId == 105
					|| weaponId == 120 || weaponId == 147 || weaponId == 156 || weaponId == 174 || weaponId == 175
					|| weaponId == 224 || weaponId == 7232) {
				if (itemId == L1ItemId.여행무기주문서 || itemId == 60142) {
					int enchant_level = l1iteminstance1.getEnchantLevel();
					if (enchant_level >= 6) {
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
						return;
					}
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
					return;
				}
			}
			if (itemId == L1ItemId.여행무기주문서 || itemId == 60142) {
				if (weaponId == 7 || weaponId == 35 || weaponId == 48 || weaponId == 73 || weaponId == 105
						|| weaponId == 120 || weaponId == 147 || weaponId == 156 || weaponId == 174 || weaponId == 175
						|| weaponId == 7232 || weaponId == 224) {
					int enchant_level = l1iteminstance1.getEnchantLevel();
					if (enchant_level >= 6) {
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
						return;
					}
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
					return;
				}
			}
			// 아놀드인첸//
			if (weaponId == 90093 || weaponId == 90093 || weaponId == 90093 || weaponId == 90094 || weaponId == 90095
					|| weaponId == 90096 || weaponId == 90097 || weaponId == 90098 || weaponId == 90099
					|| weaponId == 90100) {
				if (itemId == L1ItemId.아놀드무기주문서 || itemId == 500103) {
					int enchant_level = l1iteminstance1.getEnchantLevel();
					if (enchant_level >= 10) {
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
						return;
					}
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
					return;
				}
			}
			/** 창천 무기 마법 주문서 **/
			if (weaponId >= 411000 && weaponId <= 411035) {
				if (itemId == L1ItemId.CHANGCHUN_ENCHANT_WEAPON_SCROLL) {
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
					return;
				}
			}
			if (itemId == L1ItemId.CHANGCHUN_ENCHANT_WEAPON_SCROLL) {
				if (weaponId >= 411000 && weaponId <= 411035) {
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
					return;
				}
			}
			/** 창천 무기 마법 주문서 **/

			int enchant_level = l1iteminstance1.getEnchantLevel();

			/*
			 * if (Config.GAME_SERVER_TYPE == 0 && enchant_level >=
			 * safe_enchant+3 && (itemId != L1ItemId.WIND_ENCHANT_WEAPON_SCROLL
			 * || itemId != L1ItemId.EARTH_ENCHANT_WEAPON_SCROLL || itemId !=
			 * L1ItemId.WATER_ENCHANT_WEAPON_SCROLL || itemId !=
			 * L1ItemId.FIRE_ENCHANT_WEAPON_SCROLL)){ pc.sendPackets(new
			 * S_SystemMessage("테스트서버에서는 안전인챈+3 이상은 인챈하실수 없습니다.")); return; }
			 */

			if (itemId == L1ItemId.ANTIQUITY_ENCHANT_WEAPON_SCROLL) { // 고대의 서
				if (l1iteminstance1 != null && l1iteminstance1.getItem().getType2() == 1
						|| l1iteminstance1.getItem().getType2() == 2) {
					if (safe_enchant == 0 && enchant_level >= 6 || enchant_level == 11) { // 강화불가 수치
						pc.sendPackets(new S_SystemMessage("\\fW인첸트 수치 제한으로 더이상 인첸트 할수 없습니다."));
						return;
					}
					Random random = new Random();
					int k3 = random.nextInt(100);
					if (k3 <= 5) { // -1 될 확율 5%
						SuccessEnchant(pc, l1iteminstance1, -1);
						pc.sendPackets(new S_SystemMessage("\\fY인첸트에 실패하여 아이템의 인첸수치가 -1 내려갔습니다."));
						pc.getInventory().removeItem(useItem, 1);
					}
					if (k3 >= 6 && k3 <= 30) { // +1 될확율 30%
						SuccessEnchant(pc, l1iteminstance1, RandomELevel(l1iteminstance1, itemId));
						pc.sendPackets(new S_SystemMessage("\\fW축하합니다!인첸트에 성공하여 아이템의 인첸수치가 +1 올라갔습니다."));
						pc.getInventory().removeItem(useItem, 1);
					}
					if (k3 >= 31 && k3 <= 100) { // 확률은 알아서
						pc.sendPackets(new S_SystemMessage("\\fW인첸트에 실패하였으나 인첸트 수치가 보존 됩니다."));
						pc.getInventory().removeItem(useItem, 1);
					}

				} else {
					pc.sendPackets(new S_ServerMessage(79)); // \f1 아무것도 일어나지
																// 않았습니다.
				}
			} else

			if (itemId == 500301) { // 고대의 서 100%
				if (l1iteminstance1 != null && l1iteminstance1.getItem().getType2() == 1
						|| l1iteminstance1.getItem().getType2() == 2) {
					if (enchant_level >= 9) { // 강화불가 수치 알아서...
						pc.sendPackets(new S_SystemMessage("\\fW인첸트 제한으로 더이상 인첸트 할수 없습니다"));
						return;
					}

					SuccessEnchant(pc, l1iteminstance1, RandomELevel(l1iteminstance1, itemId));
					pc.sendPackets(new S_SystemMessage("\\fW아이템의 인첸수치가 +1 올라갔습니다."));
					pc.getInventory().removeItem(useItem, 1);

				}

			} else
			/*
			 * if (enchant_level >= Config.MAX_WEAPON1) if (weaponId >= 450008
			 * && weaponId <= 450013) { pc.sendPackets(new S_SystemMessage(
			 * "\\fW마족 무기는 현재 +" + Config.MAX_WEAPON1 + "이상은 인챈할수 없습니다."),
			 * true); return; }
			 */

			if (itemId == 60510) { // 장인의 무기 마법 주문서
				if (safe_enchant == 0 && enchant_level >=5 || enchant_level == 10) {
					pc.sendPackets(new S_SystemMessage("해당 아이템은 최고 인첸입니다. 더이상 인첸할수 없습니다."), true);
					return;
				}
				if (safe_enchant == 0 && enchant_level >= 0 || enchant_level >= 7) {
					if (_random.nextInt(100) < 5) {
						SuccessEnchant(pc, l1iteminstance1, 1);
					} else
						pc.sendPackets(new S_ServerMessage(160, l1iteminstance1.getLogName(), "$245", "$248"), true);
					pc.getInventory().removeItem(useItem, 1);
				}
				
			} else if (itemId == L1ItemId.C_SCROLL_OF_ENCHANT_WEAPON) { // c-dai
				pc.getInventory().removeItem(useItem, 1);
				if (enchant_level < -6) {
					// -7이상은 할 수 없다.
					FailureEnchant(pc, l1iteminstance1);
				} else {
					SuccessEnchant(pc, l1iteminstance1, -1);
				}
			} else if (itemId == L1ItemId.WIND_ENCHANT_WEAPON_SCROLL_100
					|| itemId == L1ItemId.EARTH_ENCHANT_WEAPON_SCROLL_100
					|| itemId == L1ItemId.WATER_ENCHANT_WEAPON_SCROLL_100
					|| itemId == L1ItemId.FIRE_ENCHANT_WEAPON_SCROLL_100
					|| itemId == L1ItemId.WIND_ENCHANT_WEAPON_SCROLL
					|| itemId == L1ItemId.EARTH_ENCHANT_WEAPON_SCROLL
					|| itemId == L1ItemId.WATER_ENCHANT_WEAPON_SCROLL
					|| itemId == L1ItemId.FIRE_ENCHANT_WEAPON_SCROLL) {
				AttrEnchant(pc, l1iteminstance1, itemId);
			} else if (itemId >= 60355 && itemId <= 60358) {
				AttrChange(pc, l1iteminstance1, itemId);
			} else if (itemId == L1ItemId.Add_ENCHANT_WEAPON_SCROLL
					|| itemId == L1ItemId.Add_ENCHANT_WEAPON_SCROLL_100) {
				StepEnchant(pc, l1iteminstance1, itemId);
			} else if (enchant_level < safe_enchant && safe_enchant > 0) {
				pc.getInventory().removeItem(useItem, 1);
				SuccessEnchant(pc, l1iteminstance1, RandomELevel(l1iteminstance1, itemId));
			} else {
				pc.getInventory().removeItem(useItem, 1);
				if (enchant_level >= Config.MAX_WEAPON)
					if (!(itemId == L1ItemId.WIND_ENCHANT_WEAPON_SCROLL
							&& itemId == L1ItemId.EARTH_ENCHANT_WEAPON_SCROLL
							&& itemId != L1ItemId.Add_ENCHANT_WEAPON_SCROLL// 추가강화줌서.
							|| itemId == L1ItemId.WATER_ENCHANT_WEAPON_SCROLL
									&& itemId == L1ItemId.FIRE_ENCHANT_WEAPON_SCROLL)) {
						pc.sendPackets(new S_SystemMessage("\\fW모든 무기는 현재 +" + Config.MAX_WEAPON + "이상은 인챈할수 없습니다."),
								true);
						return;
					}
				if (safe_enchant == 0) {
					if (enchant_level >= Config.MAX_WEAPON1) {
						if (!(itemId == L1ItemId.WIND_ENCHANT_WEAPON_SCROLL
								&& itemId == L1ItemId.EARTH_ENCHANT_WEAPON_SCROLL
								&& itemId != L1ItemId.Add_ENCHANT_WEAPON_SCROLL// 추가강화줌서.
								|| itemId == L1ItemId.WATER_ENCHANT_WEAPON_SCROLL
								
										&& itemId == L1ItemId.FIRE_ENCHANT_WEAPON_SCROLL))
							pc.sendPackets(
									new S_SystemMessage("\\fW안전인챈 0 무기는 현재 +" + Config.MAX_WEAPON1 + "이상은 인챈할수 없습니다."),
									true);
						return;
					}
				}

				int rnd = _random.nextInt(100) + 1;
				int enchant_chance_wepon;
				int chance = 0;
				try {
					chance = ItemEnchantList.getInstance().getItemEnchant(l1iteminstance1.getItemId());
				} catch (Exception e) {
					System.out.println("WeaponEnchantList chance Error");
				}

				if (enchant_level >= 8) {
					enchant_chance_wepon = 90 / ((enchant_level - safe_enchant + 1) * 2)
							/ (enchant_level / 9 != 0 ? 1 * 2 : 1) + Config.ENCHANT_CHANCE_WEAPON + chance;
				} else {
					if (l1iteminstance1.getItem().get_safeenchant() == 0) {
						enchant_chance_wepon = 90 / ((enchant_level - safe_enchant + 1) * 2)
								/ (enchant_level / 9 != 0 ? 1 * 2 : 1) + Config.ENCHANT_CHANCE_WEAPON + chance;
					} else {
						enchant_chance_wepon = 90 / ((enchant_level - safe_enchant + 1) * 2)
								/ (enchant_level / 9 != 0 ? 1 * 2 : 1) + Config.ENCHANT_CHANCE_WEAPON + chance;
						;
					}
				}
				if (pc.isGm()) {
					pc.sendPackets(new S_SystemMessage("\\fY확률 : [ " + enchant_chance_wepon + " ]"));
					pc.sendPackets(new S_SystemMessage("\\fY추가 : [ " + chance + " ]"));
					pc.sendPackets(new S_SystemMessage("\\fY찬스 : [ " + rnd + " ]"));
				}

				if (itemId == L1ItemId.TEST_ENCHANT_WEAPON) { // 빨간부분 추가
					enchant_chance_wepon = 100;
				}

				if (rnd < enchant_chance_wepon) {
					int randomEnchantLevel = RandomELevel(l1iteminstance1, itemId);
					SuccessEnchant(pc, l1iteminstance1, randomEnchantLevel);

					/*
					 * int rnd = _random.nextInt(10000); int
					 * enchant_chance_wepon1 = 0;
					 * 
					 * if (safe_enchant == 0) { } else { if (itemId == 160510) {
					 * if (enchant_level <= 6) enchant_chance_wepon1 = 30; else
					 * if (enchant_level == 7) enchant_chance_wepon1 = 30; else
					 * if (enchant_level == 8) enchant_chance_wepon1 = 20; else
					 * if (enchant_level == 9) enchant_chance_wepon1 = 7; else
					 * if (enchant_level == 10) enchant_chance_wepon1 = 2; else
					 * if (enchant_level >= 11) enchant_chance_wepon1 = 1; }
					 * else { if (enchant_level <= 6) enchant_chance_wepon1 =
					 * 30; else if (enchant_level == 7) enchant_chance_wepon1 =
					 * 30; else if (enchant_level == 8) enchant_chance_wepon1 =
					 * 20; else if (enchant_level == 9 || enchant_level == 10)
					 * enchant_chance_wepon1 = 2; else if (enchant_level >= 11)
					 * enchant_chance_wepon1 = 1; } } enchant_chance_wepon1 *=
					 * 100; enchant_chance_wepon1 *=
					 * (Config.ENCHANT_CHANCE_WEAPON == 0 ? 1 :
					 * Config.ENCHANT_CHANCE_WEAPON); // 100%인첸주문서 // if (rnd <
					 * enchant_chance_wepon1) { int randomEnchantLevel =
					 * RandomELevel(l1iteminstance1, itemId); SuccessEnchant(pc,
					 * l1iteminstance1, randomEnchantLevel);
					 */

					// 100%인첸주문서 //

				} else if (enchant_level >= 9 && rnd < (enchant_chance_wepon * 2)) {
					// \f1%0이%2과 강렬하게%1 빛났습니다만, 다행히 무사하게 살았습니다.
					pc.sendPackets(new S_ServerMessage(160, l1iteminstance1.getLogName(), "$245", "$248"), true);
				} else {
					FailureEnchant(pc, l1iteminstance1);
				}
			}
		}
	}

	private void AttrChange(L1PcInstance pc, L1ItemInstance item, int itemId) {
		// TODO 자동 생성된 메소드 스텁
		int attr_level = item.getAttrEnchantLevel();
		if (attr_level == 0) {
			pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
			return;
		}
		if (itemId == 60355) {// 풍령 속성 변환
			if (attr_level == 1 || attr_level == 4 || attr_level == 10)
				item.setAttrEnchantLevel(7);
			else if (attr_level == 2 || attr_level == 5 || attr_level == 11)
				item.setAttrEnchantLevel(8);
			else if (attr_level == 3 || attr_level == 6 || attr_level == 12)
				item.setAttrEnchantLevel(9);
			else if (attr_level == 33 || attr_level == 35 || attr_level == 39)
				item.setAttrEnchantLevel(37);
			else if (attr_level == 34 || attr_level == 36 || attr_level == 40)
				item.setAttrEnchantLevel(38);
		} else if (itemId == 60356) {// 지령 속성 변환
			if (attr_level == 1 || attr_level == 4 || attr_level == 7)
				item.setAttrEnchantLevel(10);
			else if (attr_level == 2 || attr_level == 5 || attr_level == 8)
				item.setAttrEnchantLevel(11);
			else if (attr_level == 3 || attr_level == 6 || attr_level == 9)
				item.setAttrEnchantLevel(12);
			else if (attr_level == 33 || attr_level == 35 || attr_level == 37)
				item.setAttrEnchantLevel(39);
			else if (attr_level == 34 || attr_level == 36 || attr_level == 38)
				item.setAttrEnchantLevel(40);
		} else if (itemId == 60357) {// 수령 속성 변환
			if (attr_level == 1 || attr_level == 10 || attr_level == 7)
				item.setAttrEnchantLevel(4);

			else if (attr_level == 2 || attr_level == 11 || attr_level == 8)
				item.setAttrEnchantLevel(5);

			else if (attr_level == 3 || attr_level == 12 || attr_level == 9)
				item.setAttrEnchantLevel(6);

			else if (attr_level == 33 || attr_level == 39 || attr_level == 37)
				item.setAttrEnchantLevel(35);

			else if (attr_level == 34 || attr_level == 40 || attr_level == 38)
				item.setAttrEnchantLevel(36);

		} else if (itemId == 60358) {// 화령 속성 변환
			if (attr_level == 4 || attr_level == 10 || attr_level == 7)
				item.setAttrEnchantLevel(1);
			else if (attr_level == 5 || attr_level == 11 || attr_level == 8)
				item.setAttrEnchantLevel(2);
			else if (attr_level == 6 || attr_level == 12 || attr_level == 9)
				item.setAttrEnchantLevel(3);
			else if (attr_level == 35 || attr_level == 39 || attr_level == 37)
				item.setAttrEnchantLevel(33);
			else if (attr_level == 36 || attr_level == 40 || attr_level == 38)
				item.setAttrEnchantLevel(34);
		}
		pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
		pc.getInventory().consumeItem(itemId, 1);
		pc.getInventory().updateItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
		pc.getInventory().saveItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
		pc.saveInventory();
	}
}
