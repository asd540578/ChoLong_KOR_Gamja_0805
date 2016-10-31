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

package l1j.server.server.clientpackets;

import static l1j.server.server.model.skill.L1SkillId.ABSOLUTE_BARRIER;
import static l1j.server.server.model.skill.L1SkillId.BONE_BREAK;
import static l1j.server.server.model.skill.L1SkillId.CALL_CLAN;
import static l1j.server.server.model.skill.L1SkillId.CUBE_BALANCE;
import static l1j.server.server.model.skill.L1SkillId.CUBE_IGNITION;
import static l1j.server.server.model.skill.L1SkillId.CUBE_QUAKE;
import static l1j.server.server.model.skill.L1SkillId.CUBE_SHOCK;
import static l1j.server.server.model.skill.L1SkillId.CURSE_PARALYZE;
import static l1j.server.server.model.skill.L1SkillId.CURSE_PARALYZE2;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.FIRE_WALL;
import static l1j.server.server.model.skill.L1SkillId.FOG_OF_SLEEPING;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.INVISIBILITY;
import static l1j.server.server.model.skill.L1SkillId.LIFE_STREAM;
import static l1j.server.server.model.skill.L1SkillId.MASS_TELEPORT;
import static l1j.server.server.model.skill.L1SkillId.MEDITATION;
import static l1j.server.server.model.skill.L1SkillId.MOB_BASILL;
import static l1j.server.server.model.skill.L1SkillId.MOB_COCA;
import static l1j.server.server.model.skill.L1SkillId.MOB_RANGESTUN_18;
import static l1j.server.server.model.skill.L1SkillId.MOB_RANGESTUN_19;
import static l1j.server.server.model.skill.L1SkillId.MOB_SHOCKSTUN_30;
import static l1j.server.server.model.skill.L1SkillId.PHANTASM;
import static l1j.server.server.model.skill.L1SkillId.RUN_CLAN;
import static l1j.server.server.model.skill.L1SkillId.SCALES_EARTH_DRAGON;
import static l1j.server.server.model.skill.L1SkillId.SCALES_FIRE_DRAGON;
import static l1j.server.server.model.skill.L1SkillId.SCALES_WATER_DRAGON;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_STUN;
import static l1j.server.server.model.skill.L1SkillId.TELEPORT;
import static l1j.server.server.model.skill.L1SkillId.TRUE_TARGET;

import l1j.server.server.GMCommands;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.AcceleratorChecker;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import server.LineageClient;

//Referenced classes of package l1j.server.server.clientpackets:
//ClientBasePacket

public class C_UseSkill extends ClientBasePacket {

	public C_UseSkill(byte abyte0[], LineageClient client) throws Exception {
		super(abyte0);
		try {
			int row = readC();
			int column = readC();
			int skillId = (row * 8) + column + 1;
			String charName = null;
			String message = null;
			int targetId = 0;
			int targetX = 0;
			int targetY = 0;
			L1PcInstance pc = client.getActiveChar();
			if (GMCommands.�����ӵ�üũ && pc.isGm()) {
				long time = System.currentTimeMillis();
				long eve = 0;
				if (pc._skilltime != 0) {
					eve = time - pc._skilltime;
					if (eve != 0) {
						pc.sendPackets(new S_SystemMessage("Ŭ�� ���� ��� �ӵ� : "
								+ eve));
					}
				}
				pc._skilltime = time;
			}

			if (pc.isTeleport() || pc.isDead() || pc.isGhost()) {
				return;
			}
			if (!pc.isSkillMastery(skillId)) {
				return;
			}
			if (!pc.getMap().isUsableSkill()) {
				S_ServerMessage sm = new S_ServerMessage(563);
				pc.sendPackets(sm, true);
				return;
			}

			/** ���� ��ų �߿� ���Ƿ� ������ ��� ���ϰ� **/
			if (pc.getSkillEffectTimerSet().hasSkillEffect(SHOCK_STUN)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(
							MOB_SHOCKSTUN_30)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(
							MOB_RANGESTUN_19)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(
							MOB_RANGESTUN_18)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(
							FREEZING_BREATH)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(BONE_BREAK)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(PHANTASM)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(
							FOG_OF_SLEEPING)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(
							CURSE_PARALYZE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(
							CURSE_PARALYZE2)) {
				return;
			}

			if (GMCommands.Ʈ�������콺��
					&& (skillId == L1SkillId.TRIPLE_ARROW || skillId == L1SkillId.FOU_SLAYER)) {
			} else {
				int result;
				result = pc.getAcceleratorChecker().checkSkillInterval(skillId);
				if (result != AcceleratorChecker.R_OK) {
					/*
					 * pc.add_hc(); if(skillId == L1SkillId.BLOODY_SOUL ||
					 * skillId == L1SkillId.TRIPLE_ARROW || skillId ==
					 * L1SkillId.FOU_SLAYER){ }else{ S_SystemMessage sm = new
					 * S_SystemMessage("�����ϼ��� �ҹ� ���α׷� ����� ���� ����� �˴ϴ�.");
					 * pc.sendPackets(sm, true); L1Teleport.teleport(pc,
					 * pc.getX(), pc.getY(), pc.getMapId(),
					 * pc.getMoveState().getHeading()); }
					 */
					/*
					 * if(pc.get_hc()>4){ pc.getNetConnection().kick();
					 * pc.getNetConnection().close(); }
					 */
					return;
				} else {
					// pc.radd_hc();
				}
			}

			// �䱸 ������ üũ�Ѵ�
			/*
			 * if (Config.CHECK_SPELL_INTERVAL && !(skillId == FOU_SLAYER)) {
			 * int result; // FIXME ��� ��ų�� dir/no dir�ϱ��� �Ǵ��� ���� if
			 * (SkillsTable.getInstance(). getTemplate(skillId). getActionId()
			 * == ActionCodes.ACTION_SkillAttack) { result =
			 * pc.getAcceleratorChecker(). checkInterval(
			 * AcceleratorChecker.ACT_TYPE.SPELL_DIR); } else { result =
			 * pc.getAcceleratorChecker(). checkInterval(
			 * AcceleratorChecker.ACT_TYPE.SPELL_NODIR); } if (result ==
			 * AcceleratorChecker.R_DISCONNECTED) { return; } }
			 */

			if (pc.getMapId() == 1931) {
				if (pc.getInventory().getSize() >= 180) {
					pc.sendPackets(
							new S_SystemMessage("�κ��丮�� ������ ��� �� �������ּ���."), true);
					L1Teleport.teleport(pc, 33443, 32797, (short) 4, 5, true);
					return;
				}
			}

			if (skillId == L1SkillId.CANCELLATION) {
				if (pc.isInvisble()) {
					pc.delInvis();
				}
			}
			if (skillId == SCALES_EARTH_DRAGON
					|| skillId == SCALES_WATER_DRAGON
					|| skillId == SCALES_FIRE_DRAGON) {
				if (pc.getSkillEffectTimerSet().hasSkillEffect(
						SCALES_EARTH_DRAGON)) {
					pc.getSkillEffectTimerSet().removeSkillEffect(
							SCALES_EARTH_DRAGON);
					// return;
				} else if (pc.getSkillEffectTimerSet().hasSkillEffect(
						SCALES_WATER_DRAGON)) {
					pc.getSkillEffectTimerSet().removeSkillEffect(
							SCALES_WATER_DRAGON);
					// return;
				} else if (pc.getSkillEffectTimerSet().hasSkillEffect(
						SCALES_FIRE_DRAGON)) {
					pc.getSkillEffectTimerSet().removeSkillEffect(
							SCALES_FIRE_DRAGON);
					// return;
				}
			}

			if (skillId == CUBE_IGNITION) {
				if (pc.getSkillEffectTimerSet().hasSkillEffect(CUBE_IGNITION)) {
					S_ServerMessage sm1412 = new S_ServerMessage(1412);
					pc.sendPackets(sm1412, true);
					return;
				}
			}
			if (skillId == CUBE_QUAKE) {
				if (pc.getSkillEffectTimerSet().hasSkillEffect(CUBE_QUAKE)) {
					S_ServerMessage sm1412 = new S_ServerMessage(1412);
					pc.sendPackets(sm1412, true);
					return;
				}
			}
			if (skillId == CUBE_SHOCK) {
				if (pc.getSkillEffectTimerSet().hasSkillEffect(CUBE_SHOCK)) {
					S_ServerMessage sm1412 = new S_ServerMessage(1412);
					pc.sendPackets(sm1412, true);
					return;
				}
			}
			if (skillId == CUBE_BALANCE) {
				if (pc.getSkillEffectTimerSet().hasSkillEffect(CUBE_BALANCE)) {
					S_ServerMessage sm1412 = new S_ServerMessage(1412);
					pc.sendPackets(sm1412, true);
					return;
				}
			}

			if (abyte0.length > 4) {
				try {
					switch (skillId) {
					case CALL_CLAN:
					case RUN_CLAN:
						charName = readS();
						break;
					case TRUE_TARGET:
						targetId = readD();
						targetX = readH();
						targetY = readH();
						// message = readS();
						break;
					case TELEPORT:
					case MASS_TELEPORT:
						targetId = readH(); // MapID
						// targetId = readD(); // Bookmark ID
						targetX = readH();
						targetY = readH();
						break;
					case FIRE_WALL:
					case LIFE_STREAM:
						targetX = readH();
						targetY = readH();
						break;
					case L1SkillId.SUMMON_MONSTER:
						targetId = readH();
						break;
					default: {
						targetId = readD();
						targetX = readH();
						targetY = readH();
					}
						break;
					}
				} catch (Exception e) {
					// _log.log(Level.SEVERE, "", e);
				}
			}

			if (skillId != TELEPORT
					&& pc.getSkillEffectTimerSet().hasSkillEffect(
							ABSOLUTE_BARRIER)) { // �ƺ�Ҹ�Ʈ�ٸ����� ����
				pc.getSkillEffectTimerSet().killSkillEffectTimer(
						ABSOLUTE_BARRIER);
				pc.startHpRegenerationByDoll();
				pc.startMpRegenerationByDoll();
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_�������)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(
						L1SkillId.STATUS_�������);
			}

			pc.getSkillEffectTimerSet().killSkillEffectTimer(MEDITATION);

			try {
				L1Object target2 = L1World.getInstance().findObject(targetId);
				if (target2 != null
						&& SkillsTable.getInstance().getTemplate(skillId)
								.getTarget().equalsIgnoreCase("attack")) {
					if (skillId != L1SkillId.FIRE_WALL) {
						int calcx = (int) pc.getX()
								- target2.getLocation().getX();
						int calcy = (int) pc.getY()
								- target2.getLocation().getY();
						int range = SkillsTable.getInstance()
								.getTemplate(skillId).getRanged();
						if (range != -1) {
							if (Math.abs(calcx) > range
									|| Math.abs(calcy) > range) {
								return;
							}
						}
					}
				}

				if (!pc.isGm() && target2 != null
						&& target2 instanceof L1PcInstance
						&& skillId == L1SkillId.CANCELLATION) {
					L1PcInstance tpc = (L1PcInstance) target2;
					if (tpc.getMapId() == 2005)
						return;
				}

				/*
				 * if (target2 != null && target2 instanceof L1PcInstance){
				 * if(SkillsTable.getInstance(). getTemplate(skillId).
				 * getActionId() == ActionCodes.ACTION_SkillAttack){ if
				 * (pc.getMapId()==777 ||pc.getMapId()==778
				 * ||pc.getMapId()==779) { pc.sendPackets(new
				 * S_SystemMessage("�������� PK�� ���� �Դϴ�."), true); return; }
				 * 
				 * }else{ if(skillId == L1SkillId.CANCELLATION ||skillId ==
				 * L1SkillId.SLOW ||skillId == L1SkillId.DECAY_POTION ||skillId
				 * == L1SkillId.MANA_DRAIN ||skillId == L1SkillId.SILENCE){ if
				 * (pc.getMapId()==777 ||pc.getMapId()==778
				 * ||pc.getMapId()==779) { pc.sendPackets(new
				 * S_SystemMessage("�������� ��뿡�� �طο� ������ ���� �Դϴ�."), true); return;
				 * } } } }
				 */
				if (skillId == CALL_CLAN || skillId == RUN_CLAN) {
					if (charName.isEmpty()) {
						return;
					}

					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < charName.length(); i++) {
						if (charName.charAt(i) == '[') {
							break;
						}
						sb.append(charName.charAt(i));
					}

					L1PcInstance target = L1World.getInstance().getPlayer(
							sb.toString());
					sb = null;

					if (target == null) {
						pc.sendPackets(new S_ServerMessage(73, charName), true);
						return;
					}

					if (pc.isPinkName() || target.isPinkName()) {
						pc.sendPackets(new S_SystemMessage(
								"������ �Ǵ� ����� ���� ���Դϴ�."), true);
						return;
					}

					if (pc.isGhost()) {
						pc.sendPackets(new S_SystemMessage(
								"���� ���� �� �� ���� ����Դϴ�."), true);
						return;
					}

					if (pc.getClan() == null || pc.getClanid() == 0) {
						pc.sendPackets(new S_SystemMessage(
								"������ â���ؾ� ��� ���� �մϴ�."), true);
						return;
					}

					if (pc.getClanid() != target.getClanid()) {
						pc.sendPackets(new S_ServerMessage(414), true);
						return;
					}

					targetId = target.getId();
					if (skillId == CALL_CLAN) {
						int callClanId = pc.getCallClanId();
						if (callClanId == 0 || callClanId != targetId) {
							pc.setCallClanId(targetId);
							pc.setCallClanHeading(pc.getMoveState()
									.getHeading());
						}
					}
				}
				if (skillId == INVISIBILITY) {
					if (pc.getMapId() == 5153) {
						pc.sendPackets(new S_ServerMessage(563), true); // \f1
																		// ���⿡����
																		// ����� ��
																		// �����ϴ�.
						return;
					}

				}
				/*
				 * switch(skillId){ case 4: //������ ��Ʈ case 6: //���̽� ��� case 7:
				 * //���� Ŀ�� case 10: //ĥ ��ġ case 15: //���̾� �ַο� case 16: //��Ż��
				 * case 28: //�����̾ ��ġ case 30: //� ���� case 34: //�� ����Ʈ�� case
				 * 38: //�� ���� �ݵ� case 45: //�̷��� case 46: //�� ����Ʈ case 77:
				 * //����Ƽ�׷���Ʈ case 108: //���̳� �� case 132: //Ʈ���� �ַο� case 187:
				 * //���� �����̾� case 203: //���Ž� new AttackSkill(pc, skillId,
				 * targetId, targetX, targetY); return; }
				 */
				// if(skillId == L1SkillId.AREA_OF_SILENCE)
				// GeneralThreadPool.getInstance().execute(new areaSilence(pc));
				pc.�÷��̾���� = pc.����_����;
				pc.���½ð� = System.currentTimeMillis() + 2000;
				L1SkillUse l1skilluse = new L1SkillUse();
				l1skilluse.handleCommands(pc, skillId, targetId, targetX,
						targetY, message, 0, L1SkillUse.TYPE_NORMAL);
				l1skilluse = null;

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	class areaSilence implements Runnable {

		private L1PcInstance pc;

		public areaSilence(L1PcInstance _pc) {
			pc = _pc;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			int i = 10;
			try {
				while (i > 0) {
					if (pc == null || pc.getNetConnection() == null)
						return;
					if (i <= 3) {
						pc.sendPackets(new S_SystemMessage(i
								+ "�� - ����� ���� ���Ϸ��� ��Ÿ��"), true);
					}
					i--;
					Thread.sleep(1000);
				}
				pc.sendPackets(new S_SystemMessage("����� ���� ���Ϸ��� ��Ÿ�� ����"), true);
			} catch (Exception e) {
			}
		}

	}
}
