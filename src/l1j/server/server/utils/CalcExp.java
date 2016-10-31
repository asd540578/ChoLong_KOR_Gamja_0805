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

package l1j.server.server.utils;

import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_닭고기;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_연어;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_칠면조;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_한우;

import java.util.ArrayList;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.server.Account;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
//import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_PetPack;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1Pet;
import server.GameServer;

// Referenced classes of package l1j.server.server.utils:
// CalcStat

public class CalcExp {

	// private static final long serialVersionUID = 1L;

	private static Logger _log = Logger.getLogger(CalcExp.class.getName());

	public static final int MAX_EXP = ExpTable.getExpByLevel(100) - 1;

	
	private static L1NpcInstance npc = null;

	private CalcExp() {
	}

	public static void calcExp(L1PcInstance l1pcinstance, int targetid,
			ArrayList<?> acquisitorList, ArrayList<?> hateList, int exp) {
		try {
			int i = 0;
			double party_level = 0;
			double dist = 0;
			int member_exp = 0;
			int member_lawful = 0;
			L1Object l1object = L1World.getInstance().findObject(targetid);
			npc = (L1NpcInstance) l1object;

			// 헤이트의 합계를 취득
			L1Character acquisitor;
			int hate = 0;
			int acquire_exp = 0;
			int acquire_lawful = 0;
			int party_exp = 0;
			int party_lawful = 0;
			int totalHateExp = 0;
			int totalHateLawful = 0;
			int partyHateExp = 0;
			int partyHateLawful = 0;
			int ownHateExp = 0;

			if (acquisitorList.size() != hateList.size()) {
				return;
			}
			for (i = hateList.size() - 1; i >= 0; i--) {
				acquisitor = (L1Character) acquisitorList.get(i);
				hate = (Integer) hateList.get(i);
				if (acquisitor != null && !acquisitor.isDead()) {
					totalHateExp += hate;
					if (acquisitor instanceof L1PcInstance) {
						totalHateLawful += hate;
					}
				} else { // null였거나 죽어 있으면(자) 배제
					acquisitorList.remove(i);
					hateList.remove(i);
				}
			}
			if (totalHateExp == 0) { // 취득자가 없는 경우
				return;
			}

			if (l1object != null && !(npc instanceof L1PetInstance)
					&& !(npc instanceof L1SummonInstance)) {
				// int exp = npc.get_exp();
				if (!L1World.getInstance().isProcessingContributionTotal()
						&& l1pcinstance.getHomeTownId() > 0) {
					int contribution = npc.getLevel() / 10;
					l1pcinstance.addContribution(contribution);
				}
				int lawful = npc.getLawful();

				if (l1pcinstance.isInParty()) { // 파티중

					// 파티의 헤이트의 합계를 산출
					// 파티 멤버 이외에는 그대로 배분
					partyHateExp = 0;
					partyHateLawful = 0;
					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = (L1Character) acquisitorList.get(i);
						hate = (Integer) hateList.get(i);
						if (acquisitor instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) acquisitor;
							if (pc == l1pcinstance) {
								partyHateExp += hate;
								partyHateLawful += hate;
							} else if (l1pcinstance.getParty().isMember(pc)) {
								partyHateExp += hate;
								partyHateLawful += hate;
							} else {
								if (totalHateExp > 0) {
									acquire_exp = (exp * hate / totalHateExp);
								}
								if (totalHateLawful > 0) {
									acquire_lawful = (lawful * hate / totalHateLawful);
								}
								AddExp(pc, acquire_exp, acquire_lawful);
							}
						} else if (acquisitor instanceof L1PetInstance) {
							L1PetInstance pet = (L1PetInstance) acquisitor;
							L1PcInstance master = (L1PcInstance) pet
									.getMaster();
							if (master == l1pcinstance) {
								partyHateExp += hate;
							} else if (l1pcinstance.getParty().isMember(master)) {
								partyHateExp += hate;
							} else {
								if (totalHateExp > 0) {
									acquire_exp = (exp * hate / totalHateExp);
								}
								AddExpPet(pet, acquire_exp);
							}
						} else if (acquisitor instanceof L1SummonInstance) {
							L1SummonInstance summon = (L1SummonInstance) acquisitor;
							L1PcInstance master = (L1PcInstance) summon
									.getMaster();
							if (master == l1pcinstance) {
								partyHateExp += hate;
							} else if (l1pcinstance.getParty().isMember(master)) {
								partyHateExp += hate;
							} else {
							}
						}
					}
					if (totalHateExp > 0) {
						party_exp = (exp * partyHateExp / totalHateExp);
					}
					if (totalHateLawful > 0) {
						party_lawful = (lawful * partyHateLawful / totalHateLawful);
					}

					// EXP, 로우훌 배분

					// 프리보나스
					double pri_bonus = 0;
					L1PcInstance leader = l1pcinstance.getParty().getLeader();
					if (leader.isCrown()
							&& (l1pcinstance.getNearObjects().knownsObject(
									leader) || l1pcinstance.equals(leader))) {
						pri_bonus = 0.059;
					}

					// PT경험치의 계산
					L1PcInstance[] ptMembers = l1pcinstance.getParty()
							.getMembers();
					double pt_bonus = 0;
					for (L1PcInstance each : l1pcinstance.getParty()
							.getMembers()) {
						if (l1pcinstance.getNearObjects().knownsObject(each)
								|| l1pcinstance.equals(each)) {
							party_level += each.getLevel() * each.getLevel();
						}
						if (l1pcinstance.getNearObjects().knownsObject(each)) {
							pt_bonus += 0.04;
						}
					}

					party_exp = (int) (party_exp * (1 + pt_bonus + pri_bonus));

					// 자캐릭터와 그 애완동물·사몬의 헤이트의 합계를 산출
					if (party_level > 0) {
						dist = ((l1pcinstance.getLevel() * l1pcinstance
								.getLevel()) / party_level);
					}
					member_exp = (int) (party_exp * dist);
					member_lawful = (int) (party_lawful * dist);

					ownHateExp = 0;
					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = (L1Character) acquisitorList.get(i);
						hate = (Integer) hateList.get(i);
						if (acquisitor instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) acquisitor;
							if (pc == l1pcinstance) {
								ownHateExp += hate;
							}
						} else if (acquisitor instanceof L1PetInstance) {
							L1PetInstance pet = (L1PetInstance) acquisitor;
							L1PcInstance master = (L1PcInstance) pet
									.getMaster();
							if (master == l1pcinstance) {
								ownHateExp += hate;
							}
						} else if (acquisitor instanceof L1SummonInstance) {
							L1SummonInstance summon = (L1SummonInstance) acquisitor;
							L1PcInstance master = (L1PcInstance) summon
									.getMaster();
							if (master == l1pcinstance) {
								ownHateExp += hate;
							}
						}
					}
					// 자캐릭터와 그 애완동물·사몬에 분배
					if (ownHateExp != 0) { // 공격에 참가하고 있었다
						for (i = hateList.size() - 1; i >= 0; i--) {
							acquisitor = (L1Character) acquisitorList.get(i);
							hate = (Integer) hateList.get(i);
							if (acquisitor instanceof L1PcInstance) {
								L1PcInstance pc = (L1PcInstance) acquisitor;
								if (pc == l1pcinstance) {
									if (ownHateExp > 0) {
										acquire_exp = (member_exp * hate / ownHateExp);
									}
									AddExp(pc, acquire_exp, member_lawful);
								}
							} else if (acquisitor instanceof L1PetInstance) {
								L1PetInstance pet = (L1PetInstance) acquisitor;
								L1PcInstance master = (L1PcInstance) pet
										.getMaster();
								if (master == l1pcinstance) {
									if (ownHateExp > 0) {
										acquire_exp = (member_exp * hate / ownHateExp);
									}
									AddExpPet(pet, acquire_exp);
								}
							} else if (acquisitor instanceof L1SummonInstance) {
							}
						}
					} else { // 공격에 참가하고 있지 않았다
						// 자캐릭터에만 분배
						AddExp(l1pcinstance, member_exp, member_lawful);
					}

					// 파티 멤버와 그 애완동물·사몬의 헤이트의 합계를 산출
					for (int cnt = 0; cnt < ptMembers.length; cnt++) {
						if (l1pcinstance.getNearObjects().knownsObject(
								ptMembers[cnt])) {
							if (party_level > 0) {
								dist = ((ptMembers[cnt].getLevel() * ptMembers[cnt]
										.getLevel()) / party_level);
							}
							member_exp = (int) (party_exp * dist);
							member_lawful = (int) (party_lawful * dist);

							ownHateExp = 0;
							for (i = hateList.size() - 1; i >= 0; i--) {
								acquisitor = (L1Character) acquisitorList
										.get(i);
								hate = (Integer) hateList.get(i);
								if (acquisitor instanceof L1PcInstance) {
									L1PcInstance pc = (L1PcInstance) acquisitor;
									if (pc == ptMembers[cnt]) {
										ownHateExp += hate;
									}
								} else if (acquisitor instanceof L1PetInstance) {
									L1PetInstance pet = (L1PetInstance) acquisitor;
									L1PcInstance master = (L1PcInstance) pet
											.getMaster();
									if (master == ptMembers[cnt]) {
										ownHateExp += hate;
									}
								} else if (acquisitor instanceof L1SummonInstance) {
									L1SummonInstance summon = (L1SummonInstance) acquisitor;
									L1PcInstance master = (L1PcInstance) summon
											.getMaster();
									if (master == ptMembers[cnt]) {
										ownHateExp += hate;
									}
								}
							}
							// 파티 멤버와 그 애완동물·사몬에 분배
							if (ownHateExp != 0) { // 공격에 참가하고 있었다
								for (i = hateList.size() - 1; i >= 0; i--) {
									acquisitor = (L1Character) acquisitorList
											.get(i);
									hate = (Integer) hateList.get(i);
									if (acquisitor instanceof L1PcInstance) {
										L1PcInstance pc = (L1PcInstance) acquisitor;
										if (pc == ptMembers[cnt]) {
											if (ownHateExp > 0) {
												acquire_exp = (member_exp
														* hate / ownHateExp);
											}
											AddExp(pc, acquire_exp,
													member_lawful);
										}
									} else if (acquisitor instanceof L1PetInstance) {
										L1PetInstance pet = (L1PetInstance) acquisitor;
										L1PcInstance master = (L1PcInstance) pet
												.getMaster();
										if (master == ptMembers[cnt]) {
											if (ownHateExp > 0) {
												acquire_exp = (member_exp
														* hate / ownHateExp);
											}
											AddExpPet(pet, acquire_exp);
										}
									} else if (acquisitor instanceof L1SummonInstance) {
									}
								}
							} else { // 공격에 참가하고 있지 않았다
								// 파티 멤버에만 분배
								AddExp(ptMembers[cnt], member_exp,
										member_lawful);
							}
						}
					}
				} else { // 파티를 짜지 않았다
					// EXP, 로우훌의 분배
					for (i = hateList.size() - 1; i >= 0; i--) {
						acquisitor = (L1Character) acquisitorList.get(i);
						hate = (Integer) hateList.get(i);
						acquire_exp = (exp * hate / totalHateExp);
						if (acquisitor instanceof L1PcInstance) {
							if (totalHateLawful > 0) {
								acquire_lawful = (lawful * hate / totalHateLawful);
							}
						}

						if (acquisitor instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) acquisitor;
							AddExp(pc, acquire_exp, acquire_lawful);
						} else if (acquisitor instanceof L1PetInstance) {
							L1PetInstance pet = (L1PetInstance) acquisitor;
							AddExpPet(pet, acquire_exp);
						} else if (acquisitor instanceof L1SummonInstance) {
						}
					}
				}
				/*
				 * if(l1pcinstance.isInParty()){ for (i = hateList.size() - 1; i
				 * >= 0; i--) { acquisitor = (L1Character)
				 * acquisitorList.get(i); hate = (Integer) hateList.get(i); if
				 * (acquisitor instanceof L1PcInstance) { L1PcInstance pc =
				 * (L1PcInstance) acquisitor; if (pc == l1pcinstance) {
				 * partyHateExp += hate; partyHateLawful += hate; } else if
				 * (l1pcinstance.getParty().isMember(pc)) { partyHateExp +=
				 * hate; partyHateLawful += hate; } } } int pa_exp = (int) ((exp
				 * * partyHateExp / totalHateExp) * 0.4); int pa_lawful = 0; if
				 * (totalHateLawful > 0) { pa_lawful = (lawful * partyHateLawful
				 * / totalHateLawful); } boolean nownCheck = false; for
				 * (L1PcInstance each : l1pcinstance.getParty().getMembers()) {
				 * if(l1pcinstance.equals(each))continue; if
				 * (l1pcinstance.getNearObjects().knownsObject(each)) {
				 * nownCheck = true; break; } } if(!nownCheck){
				 * AddExp(l1pcinstance, (exp * partyHateExp / totalHateExp),
				 * pa_lawful); }else{ for (L1PcInstance each :
				 * l1pcinstance.getParty().getMembers()) { if
				 * (l1pcinstance.getNearObjects().knownsObject(each) ||
				 * l1pcinstance.equals(each)) {
				 * //System.out.println("파티시 경험치: "+pa_exp); if(npc instanceof
				 * L1ScarecrowInstance){ if(each.getLevel() >= 5) continue; }
				 * AddExp(each, pa_exp, pa_lawful); } } } } for (i =
				 * hateList.size() - 1; i >= 0; i--) { acquisitor =
				 * (L1Character) acquisitorList.get(i); hate = (Integer)
				 * hateList.get(i); acquire_exp = (exp * hate / totalHateExp);
				 * if (acquisitor instanceof L1PcInstance) { if (totalHateLawful
				 * > 0) { acquire_lawful = (lawful * hate / totalHateLawful); }
				 * }
				 * 
				 * if (acquisitor instanceof L1PcInstance) { L1PcInstance pc =
				 * (L1PcInstance) acquisitor; if(l1pcinstance.isInParty() &&
				 * pc.isInParty() && l1pcinstance.getPartyID() ==
				 * pc.getPartyID()){ // System.out.println("패스"); continue; }
				 * //System.out.println("보통 경험치: "+acquire_exp); AddExp(pc,
				 * acquire_exp, acquire_lawful); } else if (acquisitor
				 * instanceof L1PetInstance) { L1PetInstance pet =
				 * (L1PetInstance) acquisitor; AddExpPet(pet, acquire_exp); }
				 * else if (acquisitor instanceof L1SummonInstance) { } }
				 */
			}

		} catch (Exception e) {
		}
	}

	// 100225
	// 100226
	// 100227
	// 100228
	// 100229
	// 100230
	// 100231

	// 100236
	// 100237
	// 100238
	// 100239
	// 100240
	// 100241
	private static void AddExp(L1PcInstance pc, int exp, int lawful) {
		/** 서버 오픈 대기 */
		if (Config.STANDBY_SERVER) {
			return;
		}

		if (pc.getLevel() > Config.MAXLEVEL) {
			return;
		}
		if (pc.isDead())
			return;

		int pclevel = pc.getLevel();

		if (pclevel >= 45) {
			if (npc instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) npc;
				if ((mon.getNpcId() >= 100225 && mon.getNpcId() <= 100231)
						|| (mon.getNpcId() >= 100236 && mon.getNpcId() <= 100241)) {
					return;
				}
			}
		}

		int add_lawful = (int) (lawful * Config.RATE_LAWFUL) * -1;
		pc.addLawful(add_lawful);

		if (npc instanceof L1MonsterInstance) {
			L1MonsterInstance mon = (L1MonsterInstance) npc;
			if (mon.getUbId() != 0) {
				int ubexp = (exp / 10);
				pc.setUbScore(pc.getUbScore() + ubexp);
			}
		}

		double exppenalty = ExpTable.getPenaltyRate(pclevel);
		double foodBonus = 1;
		double expposion = 1;
		double levelBonus = 1;
		// double ainhasadBonus = 1;
		double clanBonus = 1;
		double castleBonus = 1;
		double dollBonus = 1.0;
		double gereng = 1;
		double dragoneme = 1;
	    double comboBonus = 1; //콤보
		double etcBonus = 1;
		double 진귀한 = 1;
		double clan20Bonus = 1;
		double levelupBonus = 1;
		double 신규지원 = 1;
		double 문장경험치 = 1;
		if(pc.get문장레벨() > 0)
			문장경험치 += pc.get문장레벨() * 0.01;

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.COOKING_1_7_N)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.COOKING_1_7_S)) {
			foodBonus = 1.01;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.COOKING_1_15_N)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.COOKING_1_15_S)) {
			foodBonus = 1.02;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(
				L1SkillId.COOKING_1_23_N)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.COOKING_1_23_S)) {
			foodBonus = 1.03;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_연어)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_칠면조)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_한우)) {
			foodBonus = 1.02;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_닭고기)) {
			foodBonus = 1.04;
		} else if (pc.getSkillEffectTimerSet()
				.hasSkillEffect(L1SkillId.메티스정성스프)
				|| pc.getSkillEffectTimerSet()
						.hasSkillEffect(L1SkillId.싸이시원한음료)) {
			foodBonus = 1.05;
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.STATUS_COMA_5)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.EXP_POTION_cash)

				|| pc.getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.EXP_POTION)) {

			if (pc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.EXP_POTION_cash)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.EXP_POTION)) {
				if (pc.PC방_버프) {
					expposion = 1.3;
				} else {
					expposion = 1.2;
				}
			} else {
				expposion = 1.2;
			}
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION2)) {
			gereng = 1.4;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.EXP_POTION3)) {
			gereng = 1.3;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) 	&& pc.getAinHasad() > 10000) {
			dragoneme = 1.8;
			if (pc.getAinHasad() < 0){
				pc.calAinHasad(0);
			}
			pc.calAinHasad(-exp);
			  if (pc.getSkillEffectTimerSet().hasSkillEffect(80006)) {
			        if (pc.getComboCount() <= 10) {
			          comboBonus = 0.5D * pc.getComboCount();
			        } else if ((pc.getComboCount() > 10) && (pc.getComboCount() <= 15)) {
			          comboBonus = 0.5D * pc.getComboCount();
			          comboBonus += 0.2D * (pc.getComboCount() - 10);
			        } else if (pc.getComboCount() > 15) {
			          comboBonus = 3.0D;
			        }
			        if (comboBonus > 0.0D) {
			          pc.calAinHasad(-(int)(exp * comboBonus));
			        }
			      }
			      pc.sendPackets(new S_PacketBox(82, pc));
			pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc));
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_PUPLE)
				&& pc.getAinHasad() > 10000) {
			if (pc.getLevel() >= 49 && pc.getLevel() <= 54)
				dragoneme = 1.53;
			else if (pc.getLevel() >= 55 && pc.getLevel() <= 59)
				dragoneme = 1.43;
			else if (pc.getLevel() >= 60 && pc.getLevel() <= 64)
				dragoneme = 1.33;
			else if (pc.getLevel() >= 65)
				dragoneme = 1.23;
			pc.calAinHasad(-exp);
			 if (pc.getSkillEffectTimerSet().hasSkillEffect(80006)) {
			        if (pc.getComboCount() <= 10) {
			          comboBonus = 0.5D * pc.getComboCount();
			        } else if ((pc.getComboCount() > 10) && (pc.getComboCount() <= 15)) {
			          comboBonus = 0.5D * pc.getComboCount();
			          comboBonus += 0.2D * (pc.getComboCount() - 10);
			        } else if (pc.getComboCount() > 15) {
			          comboBonus = 3.0D;
			        }
			        if (comboBonus > 0.0D) {
			          pc.calAinHasad(-(int)(exp * comboBonus));
			        }
			      }
			      pc.sendPackets(new S_PacketBox(82, pc));
			pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc));
			if (pc.getAinHasad() <= 10000) {
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_PUPLE);
			}
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_TOPAZ)&& pc.getAinHasad() > 10000) {
			dragoneme = 1.8;
			pc.calAinHasad(-exp);
			 if (pc.getSkillEffectTimerSet().hasSkillEffect(80006)) {
			        if (pc.getComboCount() <= 10) {
			          comboBonus = 0.5D * pc.getComboCount();
			        } else if ((pc.getComboCount() > 10) && (pc.getComboCount() <= 15)) {
			          comboBonus = 0.5D * pc.getComboCount();
			          comboBonus += 0.2D * (pc.getComboCount() - 10);
			        } else if (pc.getComboCount() > 15) {
			          comboBonus = 3.0D;
			        }
			        if (comboBonus > 0.0D) {
			            pc.calAinHasad(-(int)(exp * comboBonus));
			        }
			      }
			pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc));
			if (pc.getAinHasad() <= 10000) {
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_TOPAZ);
			}
		}
		if (pc.getInventory().checkEquipped(427300)) {
			etcBonus += 0.15;
		}
		if (pc.getInventory().checkEquipped(427301)) {
			etcBonus += 0.15;
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_진귀한식량)) {
			진귀한 = 1.25;
		}

		if (GameServer.신규지원_경험치지급단 && pc.getLevel() <= 70)
			신규지원 = 1.50;

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.레벨업보너스))
			levelupBonus = 2.23;

		// if(pclevel >= 49) {
		/*
		 * if(pclevel <= 64) { double minus = 64 - pclevel; if(minus == 0) minus
		 * = 1; levelBonus = minus / 100; levelBonus = levelBonus + 1; }
		 */
		if (pc.getAinHasad() > 10000) {
			pc.calAinHasad(-exp);
			
			if (pc.getAinHasad() > 2000000) {
				gereng += 1.3;
			} else {
				gereng += 1;
			}

			if (pc.PC방_버프) {
				gereng += 0.10;
				pc.calAinHasad(-exp);
			}
			pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc));
		}
		// }
		for (L1DollInstance doll : pc.getDollList()) {
			int dollType = doll.getDollType();
			dollBonus = doll.getAddExpByDoll();
			if (dollType == L1DollInstance.DOLLTYPE_SNOWMAN_A
					|| dollType == L1DollInstance.DOLLTYPE_SNOWMAN_B
					|| dollType == L1DollInstance.DOLLTYPE_SNOWMAN_C) {
				dollBonus = 1.1;
			}

		} // 추가

		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
		if (clan != null) {
			if (pc.혈맹버프) {
				double l = 0.5;
				clan20Bonus = 1.08;
				if (pc.getLevel() > 45) {
					clan20Bonus += l * (pc.getLevel() - 45);
				}
			}
		}
		int newchar = 1;

		int settingEXP = (int) Config.RATE_XP;
    	/* 폭렙 방지*/
		if (pc.getLevel() >= 1) {
		  if ((settingEXP + pc.getExp()) > ExpTable.getExpByLevel((pc.getLevel()+1))) {
			  settingEXP =  ExpTable.getExpByLevel((pc.getLevel()+1)) - pc.getExp();
			}
		}

		int add_exp = (int) (exp * settingEXP * foodBonus * expposion
				* levelBonus * exppenalty * newchar * clanBonus * castleBonus
				* dollBonus * gereng * dragoneme* comboBonus * etcBonus * 진귀한 * clan20Bonus
				* levelupBonus * 신규지원 *문장경험치);
		
		  if (pclevel >=10){ if((add_exp + pc.getExp()) >
		  ExpTable.getExpByLevel((pc.getLevel()+1))){ add_exp =
		  (ExpTable.getExpByLevel((pc.getLevel()+1))-pc.getExp()); } }
		 
		if (add_exp < 0) {
			return;
		}

		if (ExpTable.getExpByLevel(Config.MAXLEVEL + 1) <= pc.getExp()+ add_exp) {
			pc.setExp(ExpTable.getExpByLevel(Config.MAXLEVEL + 1) - 1);
		} else {
			pc.addExp(add_exp);
			CheckQuize(pc);	
		}

	}

	private static void AddExpPet(L1PetInstance pet, int exp) {
		L1PcInstance pc = (L1PcInstance) pet.getMaster();

		//int petNpcId = pet.getNpcTemplate().get_npcId();
		int petItemObjId = pet.getItemObjId();

		int levelBefore = pet.getLevel();
		int totalExp = (int) (exp * 50 + pet.getExp());
		if (totalExp >= ExpTable.getExpByLevel(51)) {
			totalExp = ExpTable.getExpByLevel(51) - 1;
		}
		pet.setExp(totalExp);

		pet.setLevel(ExpTable.getLevelByExp(totalExp));

		int expPercentage = ExpTable.getExpPercentage(pet.getLevel(), totalExp);

		int gap = pet.getLevel() - levelBefore;
		for (int i = 1; i <= gap; i++) {
			IntRange hpUpRange = pet.getPetType().getHpUpRange();
			IntRange mpUpRange = pet.getPetType().getMpUpRange();
			pet.addMaxHp(hpUpRange.randomValue());
			pet.addMaxMp(mpUpRange.randomValue());
		}

		pet.setExpPercent(expPercentage);
		pc.sendPackets(new S_PetPack(pet, pc));

		if (gap != 0) { // 레벨업하면(자) DB에 기입한다
			pc.sendPackets(new S_SkillSound(pet.getId(), 6353));// /이건 자기한데 보이게
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pet.getId(), 6353));// 이거는 다른 사람도 보게...
			L1Pet petTemplate = PetTable.getInstance().getTemplate(petItemObjId);
			if (petTemplate == null) { // PetTable에 없다
				_log.warning("L1Pet == null");
				return;
			}
			petTemplate.set_exp(pet.getExp());
			petTemplate.set_level(pet.getLevel());
			petTemplate.set_hp(pet.getMaxHp());
			petTemplate.set_mp(pet.getMaxMp());
			PetTable.getInstance().storePet(petTemplate); // DB에 기입해
			pc.sendPackets(new S_ServerMessage(320, pet.getName())); // \f1%0의 레벨이 올랐습니다.
		}
	}

	private static void CheckQuize(L1PcInstance pc) {
		if(pc.noPlayerCK)
			return;
		Account account = Account.load(pc.getAccountName());
		if (pc.getLevel() >= 99)//50부터뜨도록
			if (account.getquize() == null || account.getquize() == "") {
		//		pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,""+Config.SERVER_NAME+"에서는 퀴즈설정을 하셔야 합니다. [.퀴즈설정 OOOO]"));
		//	    pc.sendPackets(new S_ChatPacket(pc, "\\aG퀴즈를 설정하지않아 대미지리덕션이 -1 감소합니다."));
			    pc.sendPackets(new S_SkillSound(pc.getId(), 6251));	//
				if (!pc.getExcludingList().contains(pc.getName())) {
				}
			}
	}
} 
