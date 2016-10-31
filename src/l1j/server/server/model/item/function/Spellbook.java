package l1j.server.server.model.item.function;

import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_AddSkill;
import l1j.server.server.serverpackets.S_CreateItem;
import l1j.server.server.serverpackets.S_NewUI;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1EtcItem;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.types.Point;

public class Spellbook extends L1ItemInstance {
	private static final long serialVersionUID = 1L;

	public Spellbook(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		try {
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
				int itemId = useItem.getItemId();
				int delay_id = 0;
				if (useItem.getItem().getType2() == 0) { // 종별：그 외의 아이템
					delay_id = ((L1EtcItem) useItem.getItem()).get_delayid();
				}
				if (delay_id != 0) { // 지연 설정 있어
					if (pc.hasItemDelay(delay_id) == true) {
						return;
					}

				}
				if (itemId == 40218 || itemId == 55593 || itemId == 55594 || itemId == 210133 || itemId == 402281
						|| itemId == 411488 || itemId == 411533 || itemId == 2100199 || itemId == 2100345) {
					newSkills(pc, useItem, itemId);
					return;
				}
				if (itemId > 40169 && itemId < 40226 || itemId >= 45000 && itemId <= 45022
						|| (itemId == 140186 || itemId == 140196 || itemId == 140198 || itemId == 140204
								|| itemId == 140205 || itemId == 140210 || itemId == 140219)) { // 마법서
					useSpellBook(pc, useItem, itemId);
				} else if ((itemId > 40225 && itemId < 40232) || itemId == 60348) {
					if (pc.isCrown() || pc.isGm()) {
						if (itemId == 40226) {
							if (pc.getLevel() >= 15)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "15"), true);
						} else if (itemId == 40228) {
							if (pc.getLevel() >= 30)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "30"), true);
						} else if (itemId == 40227) {
							if (pc.getLevel() >= 40)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "40"), true);
						} else if (itemId == 40231 || itemId == 40232) {
							if (pc.getLevel() >= 45)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "45"), true);
						} else if (itemId == 40230) {
							if (pc.getLevel() >= 50)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "50"), true);
						} else if (itemId == 40229) {
							if (pc.getLevel() >= 55)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "55"), true);
						} else if (itemId == 60348) {
							if (pc.getLevel() >= 60)
								SpellBook4(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "60"), true);
						} else {
							pc.sendPackets(new S_ServerMessage(312), true); // LV가
																			// 낮아서
						}
					} else {
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
					}
				} else if (itemId >= 40232 && itemId <= 40264 // 정령의 수정
						|| itemId >= 41149 && itemId <= 41153) {
					useElfSpellBook(pc, useItem, itemId);
				} else if (itemId > 40264 && itemId < 40280 || itemId == 60199) {
					if (pc.isDarkelf() || pc.isGm()) {
						if (itemId >= 40265 && itemId <= 40269) { // 어둠 정령의 수정
							if (pc.getLevel() >= 15)
								SpellBook1(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "15"), true);
						} else if (itemId >= 40270 && itemId <= 40274) { // / 어둠
																			// 정령의
																			// 수정
							if (pc.getLevel() >= 30)
								SpellBook1(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "30"), true);
						} else if (itemId >= 40275 && itemId <= 40279) {
							if (pc.getLevel() >= 45)
								SpellBook1(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "45"), true);
						} else if (itemId == 60199) {
							if (pc.getLevel() >= 60) { // 아머브레이크
								SpellBook1(pc,
										useItem);/*
													 * if(!pc.getInventory().
													 * checkItem(60124)){
													 * S_SkillSound s_skillSound
													 * = new S_SkillSound
													 * (pc.getId(), 224);//231
													 * pc.sendPackets
													 * (s_skillSound);
													 * Broadcaster
													 * .broadcastPacket(pc,
													 * s_skillSound, true); pc
													 * .getInventory().storeItem
													 * (60124, 1);
													 * pc.getInventory
													 * ().removeItem (useItem,
													 * 1); }else
													 * pc.sendPackets(new
													 * S_SystemMessage (
													 * "이미 배운 마법입니다."), true);
													 */
							} else
								pc.sendPackets(new S_ServerMessage(3321, "60"), true);

						} else {
							pc.sendPackets(new S_ServerMessage(312), true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true); // (원문:어둠
																						// 정령의
																						// 수정은
																						// 다크
																						// 에르프만을
																						// 습득할
																						// 수
																						// 있습니다.
																						// )
					}
				} else if (itemId >= 7300 && itemId <= 7311) {
					if (pc.isWarrior() || pc.isGm()) {
						if (itemId == 7302 && pc.getLevel() >= 15) {// 슬레이어 패시브
							전사의인장패시브(pc, useItem);
						} else if (itemId == 7307 && pc.getLevel() >= 30) {// 하울
																			// 스킬
							전사의인장(pc, useItem);
						} else if (itemId == 7308 && pc.getLevel() >= 45) {// 토마호크
																			// 스킬
							전사의인장(pc, useItem);
						} else if (itemId == 7300 && pc.getLevel() >= 45) {// 크래쉬
																			// 패시브
							전사의인장패시브(pc, useItem);
						} else if (itemId == 7303 && pc.getLevel() >= 50) {// 아머가드
																			// 패시브
							전사의인장패시브(pc, useItem);
						} else if (itemId == 7309 && pc.getLevel() >= 55) {// 기간틱
																			// 스킬
							전사의인장(pc, useItem);
						} else if (itemId == 7301 && pc.getLevel() >= 60) {// 퓨리
																			// 패시브
							전사의인장패시브(pc, useItem);
						} else if (itemId == 7304 && pc.getLevel() >= 60) {// 타이탄락
																			// 패시브
							전사의인장패시브(pc, useItem);
						} else if (itemId == 7310 && pc.getLevel() >= 60) {// 데스페라도
																			// 스킬
							전사의인장(pc, useItem);
						} else if (itemId == 7311 && pc.getLevel() >= 70) {// 파워그립
																			// 스킬
							전사의인장(pc, useItem);
						} else if (itemId == 7306 && pc.getLevel() >= 75) {// 타이탄매직
																			// 스킬
							전사의인장패시브(pc, useItem);
						} else if (itemId == 7305 && pc.getLevel() >= 82) {// 타이탄블릿
																			// 스킬
							전사의인장패시브(pc, useItem);
						} else if (itemId == 73101 && pc.getLevel() >= 82) {// 타이탄라이징
							// 스킬
							전사의인장패시브(pc, useItem);
						} else {
							pc.sendPackets(new S_ServerMessage(312));
						}
					} else {
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."));
					}

				} else if (itemId >= 40164 && itemId <= 40166 // 기술서
						|| itemId >= 41147 && itemId <= 41148) {
					if (pc.isKnight() || pc.isGm()) {
						if (itemId >= 40164 && itemId <= 40165) { // 스탠, 축소 아모
							if (pc.getLevel() >= 50)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "50"), true);
						} else if (itemId >= 41147 && itemId <= 41148) { // 솔리드
																			// 왕복대,
																			// 카운터
																			// 바리어
							if (pc.getLevel() >= 50)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "50"), true);
						} else if (itemId == 40166) { // 바운스아탁크
							if (pc.getLevel() >= 60)
								SpellBook3(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "60"), true);
						} else {
							pc.sendPackets(new S_ServerMessage(312), true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
					}
				} else if (itemId >= L1ItemId.DRAGONKNIGHT_SPELLSTART && itemId <= L1ItemId.DRAGONKNIGHT_SPELLEND) {
					if (pc.isDragonknight() || pc.isGm()) {
						if (itemId >= L1ItemId.DRAGONKNIGHTTABLET_DRAGONSKIN
								&& itemId <= L1ItemId.DRAGONKNIGHTTABLET_AWAKE_ANTHARAS) {
							if (pc.getLevel() >= 15)
								SpellBook5(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "15"), true);
						} else if (itemId >= L1ItemId.DRAGONKNIGHTTABLET_BLOODLUST
								&& itemId <= L1ItemId.DRAGONKNIGHTTABLET_AWAKE_PAPURION) {
							if (pc.getLevel() >= 30)
								SpellBook5(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "30"), true);
						} else if (itemId >= L1ItemId.DRAGONKNIGHTTABLET_MOTALBODY
								&& itemId <= L1ItemId.DRAGONKNIGHTTABLET_AWAKE_BALAKAS) {
							if (pc.getLevel() >= 45)
								SpellBook5(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "45"), true);
						} else {
							pc.sendPackets(new S_ServerMessage(312), true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
					}
				} else if (itemId >= L1ItemId.ILLUSIONIST_SPELLSTART && itemId <= L1ItemId.ILLUSIONIST_SPELLEND) {
					if (pc.isIllusionist() || pc.isGm()) {
						if (itemId >= L1ItemId.MEMORIALCRYSTAL_MIRRORIMAGE
								&& itemId <= L1ItemId.MEMORIALCRYSTAL_CUBE_IGNITION) {
							if (pc.getLevel() >= 10)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "10"), true);
						} else if (itemId >= L1ItemId.MEMORIALCRYSTAL_CONSENTRATION
								&& itemId <= L1ItemId.MEMORIALCRYSTAL_CUBE_QUAKE) {
							if (pc.getLevel() >= 20)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "20"), true);
						} else if (itemId >= L1ItemId.MEMORIALCRYSTAL_PATIENCE
								&& itemId <= L1ItemId.MEMORIALCRYSTAL_CUBE_SHOCK) {
							if (pc.getLevel() >= 30)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "30"), true);
						} else if (itemId >= L1ItemId.MEMORIALCRYSTAL_INSITE
								&& itemId <= L1ItemId.MEMORIALCRYSTAL_CUBE_BALANCE) {
							if (pc.getLevel() >= 40)
								SpellBook6(pc, useItem);
							else
								pc.sendPackets(new S_ServerMessage(3321, "40"), true);
						} else {
							pc.sendPackets(new S_ServerMessage(312), true);
						}
					} else {
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
					}
				}
				L1ItemDelay.onItemUse(pc, useItem); // 아이템 지연 개시
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void 전사의인장(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int dk3 = 0;
		int bw1 = 0;
		int bw2 = 0;
		int bw3 = 0;

		int warrior1 = 0;
		int warrior2 = 0;
		int warrior3 = 0;
		int warrior4 = 0;
		L1Skills l1skills = null;
		for (int j6 = 225; j6 < 231; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "전사의 인장(" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				if (pc.isSkillMastery(i)) {
					pc.sendPackets(new S_SystemMessage("이미 습득한 스킬 입니다."), true);
					return;
				}
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
				case 1:
					j = i7;
					break;
				case 2:
					k = i7;
					break;
				case 3:
					l = i7;
					break;
				case 4:
					i1 = i7;
					break;
				case 5:
					j1 = i7;
					break;
				case 6:
					k1 = i7;
					break;
				case 7:
					l1 = i7;
					break;
				case 8:
					i2 = i7;
					break;
				case 9:
					j2 = i7;
					break;
				case 10:
					k2 = i7;
					break;
				case 11:
					l2 = i7;
					break;
				case 12:
					i3 = i7;
					break;
				case 13:
					j3 = i7;
					break;
				case 14:
					k3 = i7;
					break;
				case 15:
					l3 = i7;
					break;
				case 16:
					i4 = i7;
					break;
				case 17:
					j4 = i7;
					break;
				case 18:
					k4 = i7;
					break;
				case 19:
					l4 = i7;
					break;
				case 20:
					i5 = i7;
					break;
				case 21:
					j5 = i7;
					break;
				case 22:
					k5 = i7;
					break;
				case 23:
					l5 = i7;
					break;
				case 24:
					i6 = i7;
					break;
				case 25:
					dk3 = i7;
					break;
				case 26:
					bw1 = i7;
					break;
				case 27:
					bw2 = i7;
					break;
				case 28:
					bw3 = i7;
					break;

				case 29:
					warrior1 = i7;
					break;
				case 30:
					warrior2 = i7;
					break;
				case 31:
					warrior3 = i7;
					break;
				case 32:
					warrior4 = i7;
					break;

				}
			}
		}

		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5,
				k5, l5, i6, dk3, bw1, bw2, bw3, warrior1, warrior2, warrior3, warrior4, pc.getElfAttr()));
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void 전사의인장패시브(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		L1Skills l1skills = null;
		for (int i = 300; i < 307; i++) {
			l1skills = SkillsTable.getInstance().getTemplate(i);
			String s1 = "전사의 인장(" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int id = l1skills.getId();
				if (pc.isSkillMastery(i)) {
					pc.sendPackets(new S_SystemMessage("이미 습득한 패시브 입니다."), true);
					return;
				}
				switch (id) {
				case 1:
					pc.isCrash = true;
					break;
				case 2:
					pc.isPurry = true;
					break;
				case 3:
					pc.isSlayer = true;
					break;
				case 5:
					pc.isAmorGaurd = true;
					break;
				case 6:
					pc.isTaitanR = true;
					break;
				case 7:
					pc.isTaitanB = true;
					break;
				case 8:
					pc.isTaitanM = true;
				}
				pc.sendPackets(new S_NewUI(S_NewUI.패시브추가, id));
				S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 224);
				pc.sendPackets(s_skillSound);
				Broadcaster.broadcastPacket(pc, s_skillSound);
				SkillsTable.getInstance().spellMastery(pc.getId(), i, l1skills.getName(), 0, 0);
				pc.getInventory().removeItem(l1iteminstance, 1);
			}
		}
	}

	private void useSpellBook(L1PcInstance pc, L1ItemInstance item, int itemId) {
		boolean isLawful = true;
		int pcX = pc.getX();
		int pcY = pc.getY();
		int mapId = pc.getMapId();
		int level = pc.getLevel();
		if (pcX > 33116 && pcX < 33128 && pcY > 32930 && pcY < 32942 && mapId == 4
				|| pcX > 33135 && pcX < 33147 && pcY > 32235 && pcY < 32247 && mapId == 4
				|| pcX >= 32783 && pcX <= 32803 && pcY >= 32831 && pcY <= 32851 && mapId == 77
				|| pcX >= 33189 && pcX <= 33198 && pcY >= 33446 && pcY <= 33456 && mapId == 4) {
			isLawful = true;
		}
		if (pcX > 32880 && pcX < 32892 && pcY > 32646 && pcY < 32658 && mapId == 4
				|| pcX > 32662 && pcX < 32674 && pcY > 32297 && pcY < 32309 && mapId == 4
				|| pcX >= 33075 && pcX <= 33082 && pcY >= 33212 && pcY <= 33220 && mapId == 4) {
			isLawful = false;
		}
		if (pc.isGm()) {
			SpellBook(pc, item, isLawful);
		} else {
			if (pc.isKnight()) {
				if (itemId >= 45000 && itemId <= 45007) {
					if (level >= 50)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "50"), true);
				} else if (itemId >= 45000 && itemId <= 45007) {
					pc.sendPackets(new S_ServerMessage(312), true);
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
				}
			} else if (pc.isCrown() || pc.isDarkelf()) {
				if (itemId >= 45000 && itemId <= 45007) {
					if (level >= 10)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "10"), true);
				} else if (itemId >= 45008 && itemId <= 45015) {
					if (level >= 20)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "20"), true);
				} else if (itemId >= 45008 && itemId <= 45015 || itemId >= 45000 && itemId <= 45007) {
					pc.sendPackets(new S_ServerMessage(312), true);
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
				}
			} else if (pc.isElf()) {
				if (itemId >= 45000 && itemId <= 45007) {
					if (level >= 8)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "8"), true);
				} else if (itemId >= 45008 && itemId <= 45015) {
					if (level >= 16)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "16"), true);
				} else if (itemId >= 45016 && itemId <= 45022) {
					if (level >= 24)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "24"), true);
				} else if (itemId >= 40170 && itemId <= 40177) {
					if (level >= 32)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "32"), true);
				} else if (itemId >= 40178 && itemId <= 40185) {
					if (level >= 40)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "40"), true);
				} else if (((itemId >= 40186 && itemId <= 40193) || itemId == 140186)) {
					if (level >= 48)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "48"), true);
				} else if (itemId >= 45000 && itemId <= 45022 || itemId >= 40170 && itemId <= 40193) {
					pc.sendPackets(new S_ServerMessage(312), true);
				} else {
					pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
				}
			} else if (pc.isWizard()) {
				if (itemId >= 45000 && itemId <= 45007) {
					if (level >= 4)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "4"), true);
				} else if (itemId >= 45008 && itemId <= 45015) {
					if (level >= 8)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "8"), true);
				} else if (itemId >= 45016 && itemId <= 45022) {
					if (level >= 12)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "12"), true);
				} else if (itemId >= 40170 && itemId <= 40177) {
					if (level >= 16)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "16"), true);
				} else if (itemId >= 40178 && itemId <= 40185) {
					if (level >= 20)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "20"), true);
				} else if (((itemId >= 40186 && itemId <= 40193) || itemId == 140186)) {
					if (level >= 24)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "24"), true);
				} else if (((itemId >= 40194 && itemId <= 40201) || itemId == 140196 || itemId == 140198)) {
					if (level >= 28)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "28"), true);
				} else if (((itemId >= 40202 && itemId <= 40209) || itemId == 140204 || itemId == 140205)) {
					if (level >= 32)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "32"), true);
				} else if (((itemId >= 40210 && itemId <= 40217) || itemId == 140210)) {
					if (level >= 36)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "36"), true);
				} else if (((itemId >= 40219 && itemId <= 40225) || itemId == 140219)) {
					if (level >= 40)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "40"), true);
				} else if (itemId == 40218) {
					if (level >= 8)
						SpellBook(pc, item, isLawful);
					else
						pc.sendPackets(new S_ServerMessage(3321, "80"), true);
				} else {
					pc.sendPackets(new S_ServerMessage(312), true);
				}
			}
		}
		/*
		 * } else if (itemAttr != locAttr && itemAttr != 0 && locAttr != 0) {
		 * pc.sendPackets(new S_SystemMessage("이곳에서는 배울 수 없는 마법입니다."), true);
		 * S_SkillSound effect = new S_SkillSound(pc.getId(), 10);
		 * pc.sendPackets(effect); Broadcaster.broadcastPacket(pc, effect,
		 * true); pc.setCurrentHp(Math.max(pc.getCurrentHp() - 45, 0)); if
		 * (pc.getCurrentHp() <= 0) { pc.death(null); }
		 * pc.getInventory().removeItem(item, 1); } else { pc.sendPackets(new
		 * S_SystemMessage("특정 장소에서만 사용할 수 있습니다."), true); }
		 */
	}

	private void useElfSpellBook(L1PcInstance pc, L1ItemInstance item, int itemId) {
		int level = pc.getLevel();
		if ((pc.isElf() || pc.isGm()) /* && isLearnElfMagic(pc) */) {
			if (itemId >= 40232 && itemId <= 40234) {
				if (level >= 10)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "10"), true);
			} else if (itemId >= 40235 && itemId <= 40236) {
				if (level >= 20)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "15"), true);
			} else if (itemId >= 40237 && itemId <= 40240) {
				if (level >= 30)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "30"), true);
			} else if (itemId >= 40241 && itemId <= 40243) {
				if (level >= 40)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "40"), true);
			} else if (itemId >= 40244 && itemId <= 40246) {
				if (level >= 50)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "50"), true);
			} else if (itemId >= 40247 && itemId <= 40248) {
				if (level >= 30)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "30"), true);
			} else if (itemId >= 40249 && itemId <= 40250) {
				if (level >= 40)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "45"), true);
			} else if (itemId >= 40251 && itemId <= 40252) {
				if (level >= 50)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "50"), true);
			} else if (itemId == 40253) {
				if (level >= 30)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "30"), true);
			} else if (itemId == 40254) {
				if (level >= 40)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "40"), true);
			} else if (itemId == 40255) {
				if (level >= 50)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "50"), true);
			} else if (itemId == 40256) {
				if (level >= 30)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "30"), true);
			} else if (itemId == 40257) {
				if (level >= 40)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "40"), true);
			} else if (itemId >= 40258 && itemId <= 40259) {
				if (level >= 50)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "50"), true);
			} else if (itemId >= 40260 && itemId <= 40261) {
				if (level >= 30)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "30"), true);
			} else if (itemId == 40262) {
				if (level >= 40)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "40"), true);
			} else if (itemId >= 40263 && itemId <= 40264) {
				if (level >= 50)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "50"), true);
			} else if (itemId >= 41149 && itemId <= 41150) {
				if (level >= 50)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "50"), true);
			} else if (itemId == 41151) {
				if (level >= 40)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "40"), true);
			} else if (itemId >= 41152 && itemId <= 41153) {
				if (level >= 50)
					SpellBook2(pc, item);
				else
					pc.sendPackets(new S_ServerMessage(3321, "50"), true);
			}
		} else {
			pc.sendPackets(new S_ServerMessage(312), true);
			// pc.sendPackets(new S_SystemMessage("특정 장소에서만 사용할 수 있습니다."),
			// true);
		}
	}

	// 엄마 나무인지 체크
	public boolean isLearnElfMagic(L1PcInstance pc) {
		int pcX = pc.getX();
		int pcY = pc.getY();
		int pcMapId = pc.getMapId();
		if (pcX >= 32786 && pcX <= 32797 && pcY >= 32842 && pcY <= 32859 && pcMapId == 75
				|| pc.getLocation().isInScreen(new Point(33055, 32336)) && pcMapId == 4) {
			return true;
		}
		return false;
	}

	private void SpellBook(L1PcInstance pc, L1ItemInstance item, boolean isLawful) {
		String s = "";
		int i = 0;
		int level1 = 0;
		int level2 = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int dk3 = 0;
		int bw1 = 0;
		int bw2 = 0;
		int bw3 = 0;
		L1Skills l1skills = null;
		for (int skillId = 1; skillId < 81; skillId++) {
			l1skills = SkillsTable.getInstance().getTemplate(skillId);
			String s1 = "마법서 (" + l1skills.getName() + ")";

			if (item.getItem().getName().equalsIgnoreCase(s1)) {
				// System.out.println(" 여기");
				int skillLevel = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (skillLevel) {
				case 1:
					level1 = i7;
					break;
				case 2:
					level2 = i7;
					break;
				case 3:
					l = i7;
					break;
				case 4:
					i1 = i7;
					break;
				case 5:
					j1 = i7;
					break;
				case 6:
					k1 = i7;
					break;
				case 7:
					l1 = i7;
					break;
				case 8:
					i2 = i7;
					break;
				case 9:
					j2 = i7;
					break;
				case 10:
					k2 = i7;
					break;
				case 11:
					l2 = i7;
					break;
				case 12:
					i3 = i7;
					break;
				case 13:
					j3 = i7;
					break;
				case 14:
					k3 = i7;
					break;
				case 15:
					l3 = i7;
					break;
				case 16:
					i4 = i7;
					break;
				case 17:
					j4 = i7;
					break;
				case 18:
					k4 = i7;
					break;
				case 19:
					l4 = i7;
					break;
				case 20:
					i5 = i7;
					break;
				case 21:
					j5 = i7;
					break;
				case 22:
					k5 = i7;
					break;
				case 23:
					l5 = i7;
					break;
				case 24:
					i6 = i7;
					break;
				case 25:
					dk3 = i7;
					break;
				case 26:
					bw1 = i7;
					break;
				case 27:
					bw2 = i7;
					break;
				case 28:
					bw3 = i7;
					break;
				}
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("이미 배운 마법입니다."), true);
			return;
		}
		int objid = pc.getId();
		pc.sendPackets(new S_AddSkill(level1, level2, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4,
				i5, j5, k5, l5, i6, dk3, bw1, bw2, bw3, 0, 0, 0, 0, pc.getElfAttr()), true);
		S_SkillSound s_skillSound = new S_SkillSound(objid, isLawful ? 224 : 231);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(objid, i, s, 0, 0);
		pc.getInventory().removeItem(item, 1);
	}

	private void SpellBook1(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int dk3 = 0;
		int bw1 = 0;
		int bw2 = 0;
		int bw3 = 0;
		L1Skills l1skills = null;
		for (int j6 = 97; j6 < 113; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "흑정령의 수정 (" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
				case 1:
					j = i7;
					break;
				case 2:
					k = i7;
					break;
				case 3:
					l = i7;
					break;
				case 4:
					i1 = i7;
					break;
				case 5:
					j1 = i7;
					break;
				case 6:
					k1 = i7;
					break;
				case 7:
					l1 = i7;
					break;
				case 8:
					i2 = i7;
					break;
				case 9:
					j2 = i7;
					break;
				case 10:
					k2 = i7;
					break;
				case 11:
					l2 = i7;
					break;
				case 12:
					i3 = i7;
					break;
				case 13:
					j3 = i7;
					break;
				case 14:
					k3 = i7;
					break;
				case 15:
					l3 = i7;
					break;
				case 16:
					i4 = i7;
					break;
				case 17:
					j4 = i7;
					break;
				case 18:
					k4 = i7;
					break;
				case 19:
					l4 = i7;
					break;
				case 20:
					i5 = i7;
					break;
				case 21:
					j5 = i7;
					break;
				case 22:
					k5 = i7;
					break;
				case 23:
					l5 = i7;
					break;
				case 24:
					i6 = i7;
					break;
				case 25:
					dk3 = i7;
					break;
				case 26:
					bw1 = i7;
					break;
				case 27:
					bw2 = i7;
					break;
				case 28:
					bw3 = i7;
					break;
				}
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("이미 배운 마법입니다."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5,
				k5, l5, i6, dk3, bw1, bw2, bw3, 0, 0, 0, 0, pc.getElfAttr()), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);// 231
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook2(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int dk3 = 0;
		int bw1 = 0;
		int bw2 = 0;
		int bw3 = 0;
		L1Skills l1skills = null;
		for (int j6 = 129; j6 <= 176; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "정령의 수정 (" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				if (!pc.isGm() && l1skills.getAttr() != 0 && pc.getElfAttr() != l1skills.getAttr()) {
					if (pc.getElfAttr() == 0 || pc.getElfAttr() == 1 || pc.getElfAttr() == 2 || pc.getElfAttr() == 4
							|| pc.getElfAttr() == 8) { // 속성치가 이상한 경우는 전속성을 기억할
														// 수 있도록(듯이) 해 둔다
						pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."));
						return;
					}
				}
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
				case 1:
					j = i7;
					break;
				case 2:
					k = i7;
					break;
				case 3:
					l = i7;
					break;
				case 4:
					i1 = i7;
					break;
				case 5:
					j1 = i7;
					break;
				case 6:
					k1 = i7;
					break;
				case 7:
					l1 = i7;
					break;
				case 8:
					i2 = i7;
					break;
				case 9:
					j2 = i7;
					break;
				case 10:
					k2 = i7;
					break;
				case 11:
					l2 = i7;
					break;
				case 12:
					i3 = i7;
					break;
				case 13:
					j3 = i7;
					break;
				case 14:
					k3 = i7;
					break;
				case 15:
					l3 = i7;
					break;
				case 16:
					i4 = i7;
					break;
				case 17:
					j4 = i7;
					break;
				case 18:
					k4 = i7;
					break;
				case 19:
					l4 = i7;
					break;
				case 20:
					i5 = i7;
					break;
				case 21:
					j5 = i7;
					break;
				case 22:
					k5 = i7;
					break;
				case 23:
					l5 = i7;
					break;
				case 24:
					i6 = i7;
					break;
				case 25:
					dk3 = i7;
					break;
				case 26:
					bw1 = i7;
					break;
				case 27:
					bw2 = i7;
					break;
				case 28:
					bw3 = i7;
					break;

				}
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("이미 배운 마법입니다."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5,
				k5, l5, i6, dk3, bw1, bw2, bw3, 0, 0, 0, 0, pc.getElfAttr()), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook3(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int dk3 = 0;
		int bw1 = 0;
		int bw2 = 0;
		int bw3 = 0;
		L1Skills l1skills = null;
		for (int j6 = 87; j6 <= 93; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);

			String s1 = (new StringBuilder()).append("기술서 (").append(l1skills.getName()).append(")").toString();
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();

				i = l1skills.getSkillId();
				switch (l6) {
				case 1:
					j = i7;
					break;
				case 2:
					k = i7;
					break;
				case 3:
					l = i7;
					break;
				case 4:
					i1 = i7;
					break;
				case 5:
					j1 = i7;
					break;
				case 6:
					k1 = i7;
					break;
				case 7:
					l1 = i7;
					break;
				case 8:
					i2 = i7;
					break;
				case 9:
					j2 = i7;
					break;
				case 10:
					k2 = i7;
					break;
				case 11:
					l2 = i7;
					break;
				case 12:
					i3 = i7;
					break;
				case 13:
					j3 = i7;
					break;
				case 14:
					k3 = i7;
					break;
				case 15:
					l3 = i7;
					break;
				case 16:
					i4 = i7;
					break;
				case 17:
					j4 = i7;
					break;
				case 18:
					k4 = i7;
					break;
				case 19:
					l4 = i7;
					break;
				case 20:
					i5 = i7;
					break;
				case 21:
					j5 = i7;
					break;
				case 22:
					k5 = i7;
					break;
				case 23:
					l5 = i7;
					break;
				case 24:
					i6 = i7;
					break;
				case 25:
					dk3 = i7;
					break;
				case 26:
					bw1 = i7;
					break;
				case 27:
					bw2 = i7;
					break;
				case 28:
					bw3 = i7;
					break;

				}
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("이미 배운 마법입니다."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5,
				k5, l5, i6, dk3, bw1, bw2, bw3, 0, 0, 0, 0, pc.getElfAttr()), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook4(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int dk3 = 0;
		int bw1 = 0;
		int bw2 = 0;
		int bw3 = 0;
		L1Skills l1skills = null;
		for (int j6 = 113; j6 < 123; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "마법서 (" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
				case 1:
					j = i7;
					break;
				case 2:
					k = i7;
					break;
				case 3:
					l = i7;
					break;
				case 4:
					i1 = i7;
					break;
				case 5:
					j1 = i7;
					break;
				case 6:
					k1 = i7;
					break;
				case 7:
					l1 = i7;
					break;
				case 8:
					i2 = i7;
					break;
				case 9:
					j2 = i7;
					break;
				case 10:
					k2 = i7;
					break;
				case 11:
					l2 = i7;
					break;
				case 12:
					i3 = i7;
					break;
				case 13:
					j3 = i7;
					break;
				case 14:
					k3 = i7;
					break;
				case 15:
					l3 = i7;
					break;
				case 16:
					i4 = i7;
					break;
				case 17:
					j4 = i7;
					break;
				case 18:
					k4 = i7;
					break;
				case 19:
					l4 = i7;
					break;
				case 20:
					i5 = i7;
					break;
				case 21:
					j5 = i7;
					break;
				case 22:
					k5 = i7;
					break;
				case 23:
					l5 = i7;
					break;
				case 24:
					i6 = i7;
					break;
				case 25:
					dk3 = i7;
					break;
				case 26:
					bw1 = i7;
					break;
				case 27:
					bw2 = i7;
					break;
				case 28:
					bw3 = i7;
					break;

				}
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("이미 배운 마법입니다."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5,
				k5, l5, i6, dk3, bw1, bw2, bw3, 0, 0, 0, 0, pc.getElfAttr()), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook5(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int dk3 = 0;
		int bw1 = 0;
		int bw2 = 0;
		int bw3 = 0;
		L1Skills l1skills = null;
		for (int j6 = 181; j6 < 200; j6++) {
			l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "용기사의 서판(" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
				case 1:
					j = i7;
					break;
				case 2:
					k = i7;
					break;
				case 3:
					l = i7;
					break;
				case 4:
					i1 = i7;
					break;
				case 5:
					j1 = i7;
					break;
				case 6:
					k1 = i7;
					break;
				case 7:
					l1 = i7;
					break;
				case 8:
					i2 = i7;
					break;
				case 9:
					j2 = i7;
					break;
				case 10:
					k2 = i7;
					break;
				case 11:
					l2 = i7;
					break;
				case 12:
					i3 = i7;
					break;
				case 13:
					j3 = i7;
					break;
				case 14:
					k3 = i7;
					break;
				case 15:
					l3 = i7;
					break;
				case 16:
					i4 = i7;
					break;
				case 17:
					j4 = i7;
					break;
				case 18:
					k4 = i7;
					break;
				case 19:
					l4 = i7;
					break;
				case 20:
					i5 = i7;
					break;
				case 21:
					j5 = i7;
					break;
				case 22:
					k5 = i7;
					break;
				case 23:
					l5 = i7;
					break;
				case 24:
					i6 = i7;
					break;
				case 25:
					dk3 = i7;
					break;
				case 26:
					bw1 = i7;
					break;
				case 27:
					bw2 = i7;
					break;
				case 28:
					bw3 = i7;
					break;

				}
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("이미 배운 마법입니다."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5,
				k5, l5, i6, dk3, bw1, bw2, bw3, 0, 0, 0, 0, pc.getElfAttr()), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void SpellBook6(L1PcInstance pc, L1ItemInstance l1iteminstance) {
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		int j2 = 0;
		int k2 = 0;
		int l2 = 0;
		int i3 = 0;
		int j3 = 0;
		int k3 = 0;
		int l3 = 0;
		int i4 = 0;
		int j4 = 0;
		int k4 = 0;
		int l4 = 0;
		int i5 = 0;
		int j5 = 0;
		int k5 = 0;
		int l5 = 0;
		int i6 = 0;
		int dk3 = 0;
		int bw1 = 0;
		int bw2 = 0;
		int bw3 = 0;
		for (int j6 = 201; j6 < 224; j6++) {
			L1Skills l1skills = SkillsTable.getInstance().getTemplate(j6);
			String s1 = "기억의 수정(" + l1skills.getName() + ")";
			if (l1iteminstance.getItem().getName().equalsIgnoreCase(s1)) {
				int l6 = l1skills.getSkillLevel();
				int i7 = l1skills.getId();
				s = l1skills.getName();
				i = l1skills.getSkillId();
				switch (l6) {
				case 1:
					j = i7;
					break;
				case 2:
					k = i7;
					break;
				case 3:
					l = i7;
					break;
				case 4:
					i1 = i7;
					break;
				case 5:
					j1 = i7;
					break;
				case 6:
					k1 = i7;
					break;
				case 7:
					l1 = i7;
					break;
				case 8:
					i2 = i7;
					break;
				case 9:
					j2 = i7;
					break;
				case 10:
					k2 = i7;
					break;
				case 11:
					l2 = i7;
					break;
				case 12:
					i3 = i7;
					break;
				case 13:
					j3 = i7;
					break;
				case 14:
					k3 = i7;
					break;
				case 15:
					l3 = i7;
					break;
				case 16:
					i4 = i7;
					break;
				case 17:
					j4 = i7;
					break;
				case 18:
					k4 = i7;
					break;
				case 19:
					l4 = i7;
					break;
				case 20:
					i5 = i7;
					break;
				case 21:
					j5 = i7;
					break;
				case 22:
					k5 = i7;
					break;
				case 23:
					l5 = i7;
					break;
				case 24:
					i6 = i7;
					break;
				case 25:
					dk3 = i7;
					break;
				case 26:
					bw1 = i7;
					break;
				case 27:
					bw2 = i7;
					break;
				case 28:
					bw3 = i7;
					break;

				}
			}
		}
		if (pc.isSkillMastery(i)) {
			pc.sendPackets(new S_SystemMessage("이미 배운 마법입니다."), true);
			return;
		}
		int k6 = pc.getId();
		pc.sendPackets(new S_AddSkill(j, k, l, i1, j1, k1, l1, i2, j2, k2, l2, i3, j3, k3, l3, i4, j4, k4, l4, i5, j5,
				k5, l5, i6, dk3, bw1, bw2, bw3, 0, 0, 0, 0, pc.getElfAttr()), true);
		S_SkillSound s_skillSound = new S_SkillSound(k6, 224);
		pc.sendPackets(s_skillSound);
		Broadcaster.broadcastPacket(pc, s_skillSound, true);
		SkillsTable.getInstance().spellMastery(k6, i, s, 0, 0);
		pc.getInventory().removeItem(l1iteminstance, 1);
	}

	private void newSkills(L1PcInstance pc, L1ItemInstance item, int itemId) {
		if (pc.getLevel() < 80) {
			pc.sendPackets(new S_ServerMessage(3321, "80"), true);
			return;
		}
		// 40218 마법서 (데스힐)
		// 55593 흑정령의 수정 (어쌔신)
		// 55594 흑정령의 수정 (블레이징 스피릿츠)
		// 210133 전사의 인장(타이탄 라이징)
		// 402281 마법서 (그레이스 아바타)
		// 411488 기술서 (앱솔루트 블레이드)
		// 411533 정령의 수정 (소울 배리어)
		// 2100199 기억의 수정(임팩트)
		// 2100345 용기사의 서판(디스트로이)
		switch (itemId) {
		case 40218:
			if (pc.isWizard()) {
				SpellBook(pc, item, true);
			}
			break;
		case 55593:
			if (pc.isDarkelf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(55593);
				if (skill != null) {
					int skillLevel = skill.getSkillLevel();
					int id = skill.getId();
					int[] arr = new int[30];
					arr[skillLevel - 1] = id;
					int skillId = skill.getSkillId();
					int objid = pc.getId();
					pc.sendPackets(new S_AddSkill(arr));
					S_SkillSound s_skillSound = new S_SkillSound(objid, 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(objid, skillId, skill.getName(), 0, 0);
				}
			}
			break;
		case 55594:
			if (pc.isDarkelf()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(55594);
				if (skill != null) {
					int skillId = skill.getSkillId();
					pc.sendPackets(new S_CreateItem(146, 9, 0), true);
					S_SkillSound s_skillSound = new S_SkillSound(pc.getId(), 231);
					pc.sendPackets(s_skillSound);
					Broadcaster.broadcastPacket(pc, s_skillSound);
					SkillsTable.getInstance().spellMastery(pc.getId(), skillId, skill.getName(), 0, 0);
					pc.블레이징 = true;
				}
			}
			break;
		case 210133:
			if (pc.isWarrior()) {
				L1Skills skill = SkillsTable.getInstance().getTemplateByItem(210133);
				int skillLevel = skill.getSkillLevel();
				int id = skill.getId();
				int[] arr = new int[29];
				arr[skillLevel - 1] = id;
				int skillId = skill.getSkillId();
				int objid = pc.getId();
				pc.sendPackets(new S_AddSkill(arr));
				S_SkillSound s_skillSound = new S_SkillSound(objid, 224);
				pc.sendPackets(s_skillSound);
				Broadcaster.broadcastPacket(pc, s_skillSound);
				SkillsTable.getInstance().spellMastery(objid, skillId, skill.getName(), 0, 0);
			}
			break;
		case 402281:
			if (pc.isCrown()) {
				SpellBook4(pc, item);
			}
			break;
		case 411488:
			if (pc.isKnight()) {
				SpellBook3(pc, item);
			}
			break;
		case 411533:
			if (pc.isElf()) {
				SpellBook2(pc, item);
			}
			break;
		case 2100199:
			if (pc.isIllusionist()) {
				SpellBook6(pc, item);
			}
			break;
		case 2100345:
			if (pc.isDragonknight()) {
				SpellBook5(pc, item);
			}
			break;
		}
		pc.getInventory().removeItem(item, 1);
	}
}
