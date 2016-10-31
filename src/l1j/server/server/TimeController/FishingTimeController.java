package l1j.server.server.TimeController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

public class FishingTimeController implements Runnable {
	private static FishingTimeController _instance;
	private final List<L1PcInstance> _fishingList = new ArrayList<L1PcInstance>();
	private static Logger _log = Logger.getLogger(FishingTimeController.class.getName());
	private static Random _random = new Random(System.nanoTime());

	public static FishingTimeController getInstance() {
		if (_instance == null) {
			_instance = new FishingTimeController();
		}
		return _instance;
	}

	@Override
	public void run() {
		try {
			fishing();
			GeneralThreadPool.getInstance().schedule(this, 300);
		} catch (Exception e1) {
			_log.log(Level.SEVERE, e1.getLocalizedMessage(), e1);
		}
	}

	public void addMember(L1PcInstance pc) {
		if (pc == null || _fishingList.contains(pc)) {
			return;
		}
		_fishingList.add(pc);
	}

	public void removeMember(L1PcInstance pc) {
		if (pc == null || !_fishingList.contains(pc)) {
			return;
		}
		_fishingList.remove(pc);
	}

	private void fishing() {
		try {
			if (_fishingList.size() > 0) {
				long currentTime = System.currentTimeMillis();
				L1PcInstance[] list = _fishingList.toArray(new L1PcInstance[_fishingList.size()]);
				for (L1PcInstance pc : list) {
					if (pc == null) {
						removeMember(pc);
						continue;
					}

					if (L1World.getInstance().getPlayer(pc.getName()) == null) {
						removeMember(pc);
						continue;
					}

					if (pc.isFishing()) {
						if (pc.getFishingItem() == null) {
							fishingExit(pc);
							removeMember(pc);
							continue;
						}
						long time = pc.getFishingTime();
						if (currentTime <= (time + 1000) && currentTime >= (time - 1000)) {
							pc.setFishingReady(true);
							pc.sendPackets(new S_PacketBox(S_PacketBox.FISHING), true);
						} else if (currentTime > (time + 100)) {
							int chance = _random.nextInt(100000);
							if (pc.getFishingItem().getItemId() == 600229) {
								if (chance <= 300) {// 0.3%
									successFishing(pc, 60330, "앵무 베리아나");
								} else if (chance <= 1300) {// 1.0%
									successFishing(pc, 600250, "아르카의 인형 상자");
								} else if (chance <= 3300) {// 2.0%
									successFishing(pc, 437010, "드래곤의 다이아몬드 상자");
								} else if (chance <= 32800) {// 30%
									successFishing(pc, 60329, "블루 베리아나");
								} else /* if(chance < 90100) */ {
									successFishing(pc, 60328, "베리아나");
								}
							} else {
								if (chance < 5) {
									if (pc.getFishingItem().getItemId() == 60334) {// 고탄력낚시대
										if (_random.nextInt(1000) > 500)
											successFishing(pc, 60482, "작은 금빛 베리아나");
										else
											successFishing(pc, 60483, "작은 은빛 베리아나");
									} else if (pc.getFishingItem().getItemId() == 60478) {// 금빛낚시대
										String ss = "누군가가 큰 금빛 베리아나를 낚아 올렸습니다!";
										if (_random.nextInt(1000) > 500)
											successFishing(pc, 60480, "큰 금빛 베리아나");
										else {
											successFishing(pc, 60482, "작은 금빛 베리아나");
											ss = "누군가가 작은 금빛 베리아나를 낚아 올렸습니다!";
										}
										for (L1Object temp : L1World.getInstance().getVisibleObjects(pc.getMapId())
												.values()) {
											if (temp instanceof L1PcInstance) {
												L1PcInstance tp = (L1PcInstance) temp;
												tp.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, ss), true);
											}
										}
									} else if (pc.getFishingItem().getItemId() == 60479) {// 은빛낚시대
										if (_random.nextInt(1000) > 500)
											successFishing(pc, 60481, "큰 은빛 베리아나");
										else
											successFishing(pc, 60483, "작은 은빛 베리아나");
									}

								} else if (chance < 705 && pc.getFishingItem().getItemId() == 60334) {// 0.7%
									successFishing(pc, 60331, "황금 상자");
								} else if (chance < 2205) {// 1.5%
									successFishing(pc, 60330, "앵무 베리아나");
								} else if (chance < 4205) {// 2.0%
									successFishing(pc, 437011, "드래곤의 진주");
								} else if (chance < 29205) {// 25%
									successFishing(pc, 60329, "블루 베리아나");
								} else if (chance < 79205) {// 60%
									successFishing(pc, 60328, "베리아나");
								} else {// 실패
									pc.sendPackets(new S_ServerMessage(1517, ""), true); // 낚시해에
																							// 실패했습니다.
									pc.getInventory().consumeItem(60327, 1); // 먹이
									if (pc.getFishingItem().getItemId() == 60334
											|| pc.getFishingItem().getItemId() == 60478
											|| pc.getFishingItem().getItemId() == 60479) {// 릴
																							// 장착
																							// 고탄력
																							// 낚싯대
										pc.getFishingItem().setChargeCount(pc.getFishingItem().getChargeCount() - 1);
										pc.getInventory().updateItem(pc.getFishingItem(),
												L1PcInventory.COL_CHARGE_COUNT);
										if (pc.getFishingItem().getChargeCount() <= 0) {
											pc.getInventory().removeItem(pc.getFishingItem(), 1);
											pc.getInventory().storeItem(60326, 1);
											fishingExit(pc);
										}
									}
									if (pc.isFishing() && !pc.getInventory().checkItem(60327, 1)) { // 먹이
										fishingExit(pc);
										pc.sendPackets(new S_ServerMessage(1137)); // 낚시를하기
																					// 위해선
																					// 먹이가
																					// 필요합니다.
										removeMember(pc);
									}
								}
							}

							pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.FISH_WINDOW, 2, false, 0), true);
							if (pc.isFishing()) {
								long time2 = System.currentTimeMillis() + 240000;
								boolean ck = false;
								int is성장 = 0;
								if (pc.getFishingItem().getItemId() == 60334 || pc.getFishingItem().getItemId() == 60478
										|| pc.getFishingItem().getItemId() == 60479) {// 릴
																						// 장착
																						// 고탄력
																						// 낚싯대
									time2 = System.currentTimeMillis() + 80000;
									ck = true;
								}
								if (pc.getFishingItem().getItemId() == 600229) {// 릴
																				// 장착
																				// 고탄력
																				// 낚싯대
									time2 = System.currentTimeMillis() + 40000;
									ck = true;
									is성장 = 1;
								}
								pc.setFishingTime(time2);
								pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.FISH_WINDOW, 1, ck, is성장), true);
							}
						}
					} else {
						removeMember(pc);
						continue;
					}

				}

				list = null;

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void fishingExit(L1PcInstance pc) {
		pc.setFishingTime(0);
		pc.setFishingReady(false);
		pc.setFishing(false);
		pc.setFishingItem(null);
		pc.sendPackets(new S_CharVisualUpdate(pc));
		Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc));
	}

	private void successFishing(L1PcInstance pc, int itemId, String message) {
		try {
			boolean ck = false;

			L1ItemInstance item = ItemTable.getInstance().createItem(itemId);

			if (pc.getInventory().checkAddItem(item, 1) != L1Inventory.OK) {
				fishingExit(pc);
				pc.sendPackets(new S_SystemMessage("인벤토리가 무거워 더이상 낚시를 할 수 없습니다."));
				removeMember(pc);
				return;
			}

			pc.getInventory().storeItem(item);
			if (!ck) {
				pc.getInventory().consumeItem(60327, 1); // 먹이
			}

			if (pc.getFishingItem().getItemId() == 60334 || pc.getFishingItem().getItemId() == 60478
					|| pc.getFishingItem().getItemId() == 60479) {// 릴 장착 고탄력
																	// 낚싯대
				pc.getFishingItem().setChargeCount(pc.getFishingItem().getChargeCount() - 1);
				pc.getInventory().updateItem(pc.getFishingItem(), L1PcInventory.COL_CHARGE_COUNT);
				if (pc.getFishingItem().getChargeCount() <= 0) {
					pc.getInventory().removeItem(pc.getFishingItem(), 1);
					pc.getInventory().storeItem(60326, 1);
					fishingExit(pc);
				}
			} else if (ck) {
				int exp = 15000;

				if (pc.getLevel() >= Config.RATE_XP1) {
					exp = 15000;
				}

				/*
				 * if(Config.룸티스_Event){ if(Config.룸티스드랍진행중){ if(pc.getLevel() <
				 * 80){ exp = 30000; } } }
				 */

				double dragon = 1;
				int settingEXP = (int) Config.RATE_XP;
				if (pc.getAinHasad() > 10000) {
					pc.calAinHasad(-exp);
					if (pc.getAinHasad() > 2000000) {
						dragon = 2.3;
					} else {
						dragon = 2;
					}
					if (pc.PC방_버프) {
						dragon += 0.20;
					}

					pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc));
				}
				if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) && pc.getAinHasad() > 10000) {
					dragon += 0.8;
					pc.calAinHasad(-exp);
					pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc));
				} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_PUPLE)
						&& pc.getAinHasad() > 10000) {
					if (pc.getLevel() >= 49 && pc.getLevel() <= 54)
						dragon += 0.53;
					else if (pc.getLevel() >= 55 && pc.getLevel() <= 59)
						dragon += 0.43;
					else if (pc.getLevel() >= 60 && pc.getLevel() <= 64)
						dragon += 0.33;
					else if (pc.getLevel() >= 65)
						dragon += 0.23;
					pc.calAinHasad(-exp);
					pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc));
					if (pc.getAinHasad() <= 10000) {
						pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_PUPLE);
					}
				} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_TOPAZ)
						&& pc.getAinHasad() > 10000) {
					dragon += 0.8;
					pc.calAinHasad(-exp);
					pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc));
					if (pc.getAinHasad() <= 10000) {
						pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_TOPAZ);
					}
				}

				/*
				 * if(Config.룸티스_Event){ if(Config.룸티스드랍진행중){ if(pc.getLevel() <
				 * 80){ if(dragon != 1){ exp = 15000; dragon *= 2; } } } }
				 */

				double exppenalty = ExpTable.getPenaltyRate(pc.getLevel());

				int add_exp = (int) (exp * settingEXP * dragon * exppenalty);

				pc.addExp(add_exp);

				pc.getFishingItem().setChargeCount(pc.getFishingItem().getChargeCount() - 1);
				pc.getInventory().updateItem(pc.getFishingItem(), L1PcInventory.COL_CHARGE_COUNT);
				if (pc.getFishingItem().getChargeCount() <= 0) {
					pc.getInventory().removeItem(pc.getFishingItem(), 1);
					pc.getInventory().storeItem(60326, 1);
					fishingExit(pc);
				}

				pc.save();
			}

			pc.sendPackets(new S_ServerMessage(1185, message));// 낚시에 성공해 를
																// 낚시했습니다.
			if (!ck && !pc.getInventory().checkItem(60327, 1)) { // 먹이
				fishingExit(pc);
				pc.sendPackets(new S_ServerMessage(1137)); // 낚시를하기 위해선 먹이가
															// 필요합니다.
				removeMember(pc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

}
