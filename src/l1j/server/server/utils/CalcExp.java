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

import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�߰��;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_����;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_ĥ����;
import static l1j.server.server.model.skill.L1SkillId.COOKING_NEW_�ѿ�;

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

			// ����Ʈ�� �հ踦 ���
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
				} else { // null���ų� �׾� ������(��) ����
					acquisitorList.remove(i);
					hateList.remove(i);
				}
			}
			if (totalHateExp == 0) { // ����ڰ� ���� ���
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

				if (l1pcinstance.isInParty()) { // ��Ƽ��

					// ��Ƽ�� ����Ʈ�� �հ踦 ����
					// ��Ƽ ��� �̿ܿ��� �״�� ���
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

					// EXP, �ο��� ���

					// ����������
					double pri_bonus = 0;
					L1PcInstance leader = l1pcinstance.getParty().getLeader();
					if (leader.isCrown()
							&& (l1pcinstance.getNearObjects().knownsObject(
									leader) || l1pcinstance.equals(leader))) {
						pri_bonus = 0.059;
					}

					// PT����ġ�� ���
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

					// ��ĳ���Ϳ� �� �ֿϵ���������� ����Ʈ�� �հ踦 ����
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
					// ��ĳ���Ϳ� �� �ֿϵ�������� �й�
					if (ownHateExp != 0) { // ���ݿ� �����ϰ� �־���
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
					} else { // ���ݿ� �����ϰ� ���� �ʾҴ�
						// ��ĳ���Ϳ��� �й�
						AddExp(l1pcinstance, member_exp, member_lawful);
					}

					// ��Ƽ ����� �� �ֿϵ���������� ����Ʈ�� �հ踦 ����
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
							// ��Ƽ ����� �� �ֿϵ�������� �й�
							if (ownHateExp != 0) { // ���ݿ� �����ϰ� �־���
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
							} else { // ���ݿ� �����ϰ� ���� �ʾҴ�
								// ��Ƽ ������� �й�
								AddExp(ptMembers[cnt], member_exp,
										member_lawful);
							}
						}
					}
				} else { // ��Ƽ�� ¥�� �ʾҴ�
					// EXP, �ο����� �й�
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
				 * //System.out.println("��Ƽ�� ����ġ: "+pa_exp); if(npc instanceof
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
				 * pc.getPartyID()){ // System.out.println("�н�"); continue; }
				 * //System.out.println("���� ����ġ: "+acquire_exp); AddExp(pc,
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
		/** ���� ���� ��� */
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
	    double comboBonus = 1; //�޺�
		double etcBonus = 1;
		double ������ = 1;
		double clan20Bonus = 1;
		double levelupBonus = 1;
		double �ű����� = 1;
		double �������ġ = 1;
		if(pc.get���巹��() > 0)
			�������ġ += pc.get���巹��() * 0.01;

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
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_����)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_ĥ����)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_�ѿ�)) {
			foodBonus = 1.02;
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(COOKING_NEW_�߰��)) {
			foodBonus = 1.04;
		} else if (pc.getSkillEffectTimerSet()
				.hasSkillEffect(L1SkillId.��Ƽ����������)
				|| pc.getSkillEffectTimerSet()
						.hasSkillEffect(L1SkillId.���̽ÿ�������)) {
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
				if (pc.PC��_����) {
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

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_�����ѽķ�)) {
			������ = 1.25;
		}

		if (GameServer.�ű�����_����ġ���޴� && pc.getLevel() <= 70)
			�ű����� = 1.50;

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���������ʽ�))
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

			if (pc.PC��_����) {
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

		} // �߰�

		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
		if (clan != null) {
			if (pc.���͹���) {
				double l = 0.5;
				clan20Bonus = 1.08;
				if (pc.getLevel() > 45) {
					clan20Bonus += l * (pc.getLevel() - 45);
				}
			}
		}
		int newchar = 1;

		int settingEXP = (int) Config.RATE_XP;
    	/* ���� ����*/
		if (pc.getLevel() >= 1) {
		  if ((settingEXP + pc.getExp()) > ExpTable.getExpByLevel((pc.getLevel()+1))) {
			  settingEXP =  ExpTable.getExpByLevel((pc.getLevel()+1)) - pc.getExp();
			}
		}

		int add_exp = (int) (exp * settingEXP * foodBonus * expposion
				* levelBonus * exppenalty * newchar * clanBonus * castleBonus
				* dollBonus * gereng * dragoneme* comboBonus * etcBonus * ������ * clan20Bonus
				* levelupBonus * �ű����� *�������ġ);
		
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

		if (gap != 0) { // �������ϸ�(��) DB�� �����Ѵ�
			pc.sendPackets(new S_SkillSound(pet.getId(), 6353));// /�̰� �ڱ��ѵ� ���̰�
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pet.getId(), 6353));// �̰Ŵ� �ٸ� ����� ����...
			L1Pet petTemplate = PetTable.getInstance().getTemplate(petItemObjId);
			if (petTemplate == null) { // PetTable�� ����
				_log.warning("L1Pet == null");
				return;
			}
			petTemplate.set_exp(pet.getExp());
			petTemplate.set_level(pet.getLevel());
			petTemplate.set_hp(pet.getMaxHp());
			petTemplate.set_mp(pet.getMaxMp());
			PetTable.getInstance().storePet(petTemplate); // DB�� ������
			pc.sendPackets(new S_ServerMessage(320, pet.getName())); // \f1%0�� ������ �ö����ϴ�.
		}
	}

	private static void CheckQuize(L1PcInstance pc) {
		if(pc.noPlayerCK)
			return;
		Account account = Account.load(pc.getAccountName());
		if (pc.getLevel() >= 99)//50���Ͷߵ���
			if (account.getquize() == null || account.getquize() == "") {
		//		pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,""+Config.SERVER_NAME+"������ ������� �ϼž� �մϴ�. [.����� OOOO]"));
		//	    pc.sendPackets(new S_ChatPacket(pc, "\\aG��� ���������ʾ� ������������� -1 �����մϴ�."));
			    pc.sendPackets(new S_SkillSound(pc.getId(), 6251));	//
				if (!pc.getExcludingList().contains(pc.getName())) {
				}
			}
	}
} 
