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

import static l1j.server.server.model.Instance.L1PcInstance.REGENSTATE_ATTACK;

import l1j.server.server.ActionCodes;
import l1j.server.server.GMCommands;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.AcceleratorChecker;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1LittleBugInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_UseArrowSkill;
import server.LineageClient;

//Referenced classes of package l1j.server.server.clientpackets:
//ClientBasePacket

public class C_Attack extends ClientBasePacket {

	private int _targetX = 0;
	private int _targetY = 0;
	private static final S_SystemMessage sm = new S_SystemMessage(
			"����ǰ�� �ʹ� ���ſ��� ������ �� �� �����ϴ�.");

	public C_Attack(byte[] decrypt, LineageClient client) {
		super(decrypt);
		try {
			int targetId = readD();
			int x = readH();
			int y = readH();
			_targetX = x;
			_targetY = y;
			L1PcInstance pc = client.getActiveChar();
			L1Object target = L1World.getInstance().findObject(targetId);
			pc.testobj = targetId;
			boolean ��� = false;
			if (pc == null || pc.isGhost() || pc.isDead() || pc.isTeleport()) {
				return;
			}
			if (GMCommands.autocheck_Tellist.size() > 0) {
				if (GMCommands.autocheck_Tellist.contains(client
						.getAccountName())) {
					pc.sendPackets(new S_SystemMessage(
							"�ڵ� ���� �亯�� 3ȸ�̻� Ʋ�� ���� ������ �־�� �մϴ�."));
					return;
				}
			}

			if (pc.isGm()) {
				if (target instanceof L1NpcInstance) {
					pc._npcnum = ((L1NpcInstance) target).getNpcTemplate()
							.get_npcId();
					pc._npcname = ((L1NpcInstance) target).getNpcTemplate()
							.get_name();
					pc.sendPackets(new S_SystemMessage("npcid :" + pc._npcnum
							+ " �̸� :" + pc._npcname));
				}
				if (target instanceof L1DoorInstance) {
					L1DoorInstance fi = (L1DoorInstance) target;
					pc.sendPackets(new S_SystemMessage("doorid :"
							+ fi.getDoorId()));
				}
			}
			
			if (pc.isInvisble()) {
				if(pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BLIND_HIDING)){
					if(!pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ؽ�)){			
						return;
					}else{
						pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.��ؽ�);
						pc.��ؽ� = true;//
					}
				}else{
					return;
				}
			}
			
			if (pc.isInvisDelay()) {
				return;
			}

			if (pc.getInventory().calcWeightpercent() >= 83) {
				pc.sendPackets(sm); // \f1�������� �ʹ� ���ſ� ������ ���� �����ϴ�.
				return;
			}

			if (pc.getMapId() == 1931) {
				if (pc.getInventory().getSize() >= 180) {
					pc.sendPackets(
							new S_SystemMessage("�κ��丮�� ������ ��� �� �������ּ���."), true);
					L1Teleport.teleport(pc, 33443, 32797, (short) 4, 5, true);
					return;
				}
			}

			if (pc.isPrivateShop()) {
				pc.sendPackets(new S_SystemMessage("��� ���������� ���᤻"), true);
				// pc.getNetConnection().close();
				return;
			}

			/*
			 * if (target instanceof L1PcInstance){ if (pc.getMapId()==777
			 * ||pc.getMapId()==778 ||pc.getMapId()==779) { S_SystemMessage sm =
			 * new S_SystemMessage("�������� PK�� ���� �Դϴ�."); pc.sendPackets(sm);
			 * sm.clear(); sm = null; return; } }
			 */
			if (target != null && target instanceof L1LittleBugInstance) {
				return;
			}
			if (target != null && target instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) target;
				if (npc.getNpcTemplate().get_npcId() == 90000
						|| npc.getNpcTemplate().get_npcId() == 90002
						|| npc.getNpcTemplate().get_npcId() == 90009
						|| npc.getNpcTemplate().get_npcId() == 90013
						|| npc.getNpcTemplate().get_npcId() == 90016) {// ���
					��� = true;
				}

				int hiddenStatus = ((L1NpcInstance) target).getHiddenStatus();
				if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK
						|| hiddenStatus == L1NpcInstance.HIDDEN_STATUS_FLY) {
					return;
				}
			}

			// if (Config.CHECK_ATTACK_INTERVAL) {
			int result = pc.getAcceleratorChecker().checkInterval(
					AcceleratorChecker.ACT_TYPE.ATTACK);
			switch (result) {
			case AcceleratorChecker.R_DETECTED:
			case AcceleratorChecker.R_DISCONNECTED:
				return;
			}
			// }

			// ���� �׼��� ���� �� �ִ� ����� ó��
			if (pc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.ABSOLUTE_BARRIER)) { // �ƺ�Ҹ�Ʈ�ٸ����� ����
				pc.getSkillEffectTimerSet().killSkillEffectTimer(
						L1SkillId.ABSOLUTE_BARRIER);
				// pc.startHpRegeneration();
				// pc.startMpRegeneration();
				pc.startHpRegenerationByDoll();
				pc.startMpRegenerationByDoll();
			}
			if (pc.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_�������)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(
						L1SkillId.STATUS_�������);
			}
			if (pc.getSkillEffectTimerSet()
					.hasSkillEffect(L1SkillId.MEDITATION)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(
						L1SkillId.MEDITATION);
			}

			pc.attacking = true;
			pc.delInvis();
			pc.setRegenState(REGENSTATE_ATTACK);
			L1Character cha = null;
			if (target != null && target instanceof L1Character) {
				cha = (L1Character) target;
			}
			// pc.sendPackets(new S_RemoveObject(cha));

			if (target == null) {
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj == null)
						continue;
					if (obj instanceof L1MonsterInstance) {
						L1MonsterInstance mon = (L1MonsterInstance) obj;
						if (!mon.isDead()
								&& (mon.getNpcId() == 100106 || (mon.getNpcId() >= 100094 && mon
										.getNpcId() <= 100097))) {
							if (mon.getX() == _targetX
									&& mon.getY() == _targetY) {
								mon.onAction(pc);
								return;
							}
						}
					}
				}
			}

			pc.�÷��̾���� = pc.����_����;
			pc.���½ð� = System.currentTimeMillis() + 2000;

			if (cha != null && target != null && !cha.isDead() && !���) {
				target.onAction(pc);
			} else { // �ϴ� ����
				// TODO Ȱ�� ���鿡 �ϴ� �������� ���� ȭ���� ���� ������ �� �ȴ�
				if (���) {
					target.onAction(pc);
				}
				int weaponId = 0;
				int weaponType = 0;
				L1ItemInstance weapon = pc.getWeapon();
				L1ItemInstance arrow = null;
				L1ItemInstance sting = null;
				if (weapon != null) {
					weaponId = weapon.getItem().getItemId();
					weaponType = weapon.getItem().getType1();
					if (weaponType == 20) {
						arrow = pc.getInventory().getArrow();
					}
					if (weaponType == 62) {
						sting = pc.getInventory().getSting();
					}
				}
				pc.getMoveState().setHeading(
						CharPosUtil.targetDirection(pc, x, y)); // ���⼼Ʈ
				if (weaponType == 20
						&& (weaponId == 190 || weaponId == 100190 || weaponId == 30082 || weaponId == 31082 || weaponId == 222206
								|| (weaponId >= 11011 && weaponId <= 11013) || arrow != null)) {
					calcOrbit(pc.getX(), pc.getY(), pc.getMoveState()
							.getHeading());
					if (arrow != null) {
						if (pc.getGfxId().getTempCharGfx() == 7968
								|| pc.getGfxId().getTempCharGfx() == 7967) {
							S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0,
									7972, _targetX, _targetY, true);
							pc.sendPackets(ua);
							Broadcaster.broadcastPacket(pc, ua, true);
						} else if (pc.getGfxId().getTempCharGfx() == 8842
								|| pc.getGfxId().getTempCharGfx() == 8900) { // �����
							S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0,
									8904, _targetX, _targetY, true);
							pc.sendPackets(ua);
							Broadcaster.broadcastPacket(pc, ua, true);
						} else if (pc.getGfxId().getTempCharGfx() == 8845
								|| pc.getGfxId().getTempCharGfx() == 8913) { // ������
							S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0,
									8916, _targetX, _targetY, true);
							pc.sendPackets(ua);
							Broadcaster.broadcastPacket(pc, ua, true);
						} else {
							S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0, 66,
									_targetX, _targetY, true);
							pc.sendPackets(ua);
							Broadcaster.broadcastPacket(pc, ua, true);
						}
						pc.getInventory().removeItem(arrow, 1);
					} else if (weaponId == 190 || weaponId == 100190 || weaponId == 30082
							|| weaponId == 222206 || weaponId == 31082) {
						S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0, 2349,
								_targetX, _targetY, true);
						pc.sendPackets(ua);
						Broadcaster.broadcastPacket(pc, ua, true);
					} else if (weaponId >= 11011 && weaponId <= 11013) {
						S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0, 8771,
								_targetX, _targetY, true);
						pc.sendPackets(ua);
						Broadcaster.broadcastPacket(pc, ua, true);
					} // �߰�

				} else if (weaponType == 62 && sting != null) {
					calcOrbit(pc.getX(), pc.getY(), pc.getMoveState()
							.getHeading());
					if (pc.getGfxId().getTempCharGfx() == 7968
							|| pc.getGfxId().getTempCharGfx() == 7967) {
						S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0, 7972,
								_targetX, _targetY, true);
						pc.sendPackets(ua);
						Broadcaster.broadcastPacket(pc, ua, true);
					} else if (pc.getGfxId().getTempCharGfx() == 8842
							|| pc.getGfxId().getTempCharGfx() == 8900) { // �����
						S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0, 8904,
								_targetX, _targetY, true);
						pc.sendPackets(ua);
						Broadcaster.broadcastPacket(pc, ua, true);

					} else if (pc.getGfxId().getTempCharGfx() == 8845
							|| pc.getGfxId().getTempCharGfx() == 8913) { // ������
						S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0, 8916,
								_targetX, _targetY, true);
						pc.sendPackets(ua);
						Broadcaster.broadcastPacket(pc, ua, true);
					} else {
						S_UseArrowSkill ua = new S_UseArrowSkill(pc, 0, 2989,
								_targetX, _targetY, true);
						pc.sendPackets(ua);
						Broadcaster.broadcastPacket(pc, ua, true);
					}
					pc.getInventory().removeItem(sting, 1);
				} else {
					S_AttackPacket ap = new S_AttackPacket(pc, 0,
							ActionCodes.ACTION_Attack);
					pc.sendPackets(ap);
					Broadcaster.broadcastPacket(pc, ap, true);
				}
				if (pc.getWeapon() != null) {
					if (pc.getWeapon().getItem().getType() == 17) {
						if (pc.getWeapon().getItemId() == 410003) {
							S_SkillSound ss = new S_SkillSound(pc.getId(), 6983);
							pc.sendPackets(ss);
							Broadcaster.broadcastPacket(pc, ss, true);
						} else {
							S_SkillSound ss = new S_SkillSound(pc.getId(), 7049);
							pc.sendPackets(ss);
							Broadcaster.broadcastPacket(pc, ss, true);
						}
					}
				}
			}

			GeneralThreadPool.getInstance().schedule(new attacking(pc),
					calcSleepTime(pc) + 50);

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			clear();
		}
	}

	class attacking implements Runnable {

		private L1PcInstance pc;

		public attacking(L1PcInstance _pc) {
			pc = _pc;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			try {
				pc.attacking = false;
			} catch (Exception e) {
			}
		}

	}

	private static final byte HEADING_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
	private static final byte HEADING_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

	private void calcOrbit(int cX, int cY, int head) {
		float disX = Math.abs(cX - _targetX);
		float disY = Math.abs(cY - _targetY);
		float dis = Math.max(disX, disY);
		float avgX = 0;
		float avgY = 0;

		if (dis == 0) {
			avgX = HEADING_X[head];
			avgY = HEADING_Y[head];
		} else {
			avgX = disX / dis;
			avgY = disY / dis;
		}

		int addX = (int) Math.floor((avgX * 15) + 0.59f);
		int addY = (int) Math.floor((avgY * 15) + 0.59f);

		if (cX > _targetX) {
			addX *= -1;
		}
		if (cY > _targetY) {
			addY *= -1;
		}

		_targetX = _targetX + addX;
		_targetY = _targetY + addY;
	}

	protected int calcSleepTime(L1PcInstance pc) {
		int gfxid = pc.getGfxId().getTempCharGfx();
		int weapon = pc.getCurrentWeapon();
		if (gfxid == 3784 || gfxid == 6137 || gfxid == 6142 || gfxid == 6147
				|| gfxid == 6152 || gfxid == 6157 || gfxid == 9205
				|| gfxid == 9206 || gfxid == 13152 || gfxid == 13153
				|| gfxid == 12702 || gfxid == 12681 || gfxid == 8812
				|| gfxid == 8817 || gfxid == 6267 || gfxid == 6270
				|| gfxid == 6273 || gfxid == 6276) {
			if (weapon == 24 && pc.getWeapon() != null
					&& pc.getWeapon().getItem().getType() == 18) {
				if (gfxid == 13152 || gfxid == 13153 || gfxid == 12702 || gfxid == 15115
						|| gfxid == 12681 || gfxid == 8812 || gfxid == 8817
						|| gfxid == 6267 || gfxid == 6270 || gfxid == 6273
						|| gfxid == 6276)
					weapon = 50;
				else
					weapon = 83;
			}
		}

		int interval = SprTable.getInstance().getAttackSpeed(gfxid, weapon + 1);
		if (interval == 0) {
			if (weapon + 1 == 1) {
				return 0;
			}
			interval = 640;
		} else {
			if ((gfxid >= 11328 && gfxid <= 11448) || gfxid == 12237
					|| gfxid == 12702 || gfxid == 12681) {
				if (pc.getLevel() >= 15)
					interval -= 43;
				if (pc.getLevel() >= 30)
					interval -= 43;
				if (pc.getLevel() >= 45)
					interval -= 34;
				if (pc.getLevel() >= 50)
					interval -= 34;
				if (pc.getLevel() >= 52)
					interval -= 25;
				if (pc.getLevel() >= 55)
					interval -= 24;
				if (pc.getLevel() >= 60)
					interval -= 22;
				if (pc.getLevel() >= 65)
					interval -= 21;
				if (pc.getLevel() >= 70)
					interval -= 16;
				if (pc.getLevel() >= 75)
					interval -= 16;
				if (pc.getLevel() >= 80)
					interval -= 16;
			}
		}

		if (pc.isHaste()) {
			interval *= L1PcInstance.HASTE_RATE;
		}
		if (pc.isBloodLust()) { // ���巯��Ʈ
			interval *= L1PcInstance.HASTE_RATE;
		}
		if (pc.isBrave()) {
			interval *= L1PcInstance.HASTE_RATE;
		}
		if (pc.isElfBrave()) {
			interval *= L1PcInstance.WAFFLE_RATE;
		}
		if (pc.isThirdSpeed()) {
			interval *= L1PcInstance.THIRDSPEED_RATE;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WIND_SHACKLE)) {
			interval *= 2;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SLOW)) {
			interval *= 2;
		}
		return interval;
	}
}
