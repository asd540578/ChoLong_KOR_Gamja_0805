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
package l1j.server.server.model.Instance;

import java.util.Timer;
import java.util.TimerTask;

import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.NPCTalkDataTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1NpcTalkData;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.templates.L1Npc;

public class L1PeopleInstance extends L1NpcInstance {
	private static final long serialVersionUID = 1L;

	/**
	 * @param template
	 */
	public L1PeopleInstance(L1Npc template) {
		super(template);
	}

	@Override
	public void onAction(L1PcInstance pc, int adddmg) {
		if (getCurrentHp() > 0 && !isDead()) {
			L1Attack attack = new L1Attack(pc, this);
			if (attack.calcHit()) {
				attack.calcDamage(adddmg);
				attack.addPcPoisonAttack(pc, this);
			}
			attack.action();
			attack.commit();
			attack = null;
		}
	}

	@Override
	public void onAction(L1PcInstance pc) {
		if (getCurrentHp() > 0 && !isDead()) {
			L1Attack attack = new L1Attack(pc, this);
			if (attack.calcHit()) {
				attack.calcDamage();
				attack.addPcPoisonAttack(pc, this);
			}
			attack.action();
			attack.commit();
			attack = null;
		}
	}

	@Override
	public void onNpcAI() {
		if (isAiRunning()) {
			return;
		}
		setActived(false);
		startAI();
	}

	@Override
	public void onTalkAction(L1PcInstance pc) {
		int objid = getId();
		L1NpcTalkData talking = NPCTalkDataTable.getInstance().getTemplate(
				getNpcTemplate().get_npcId());
		int npcid = getNpcTemplate().get_npcId();
		String htmlid = null;

		int pcX = pc.getX();
		int pcY = pc.getY();
		int npcX = getX();
		int npcY = getY();

		if (getNpcTemplate().getChangeHead()) {
			int heading = 0;

			if (pcX == npcX && pcY < npcY)
				heading = 0;
			else if (pcX > npcX && pcY < npcY)
				heading = 1;
			else if (pcX > npcX && pcY == npcY)
				heading = 2;
			else if (pcX > npcX && pcY > npcY)
				heading = 3;
			else if (pcX == npcX && pcY > npcY)
				heading = 4;
			else if (pcX < npcX && pcY > npcY)
				heading = 5;
			else if (pcX < npcX && pcY == npcY)
				heading = 6;
			else if (pcX < npcX && pcY < npcY)
				heading = 7;

			getMoveState().setHeading(heading);
			S_ChangeHeading ch = new S_ChangeHeading(this);
			Broadcaster.broadcastPacket(this, ch, true);

			synchronized (this) {
				if (_monitor != null) {
					_monitor.cancel();
				}
				setRest(true);
				_monitor = new RestMonitor();
				_restTimer.schedule(_monitor, REST_MILLISEC);
			}
		}

		if (talking != null) {
			switch (npcid) {
			case 70839: // ����Ʈ
				if (pc.isCrown() || pc.isKnight() || pc.isWizard()) {
					htmlid = "doettM1";
				} else if (pc.isDarkelf()) {
					htmlid = "doettM2";
				} else if (pc.isDragonknight()) {
					htmlid = "doettM3";
				} else if (pc.isIllusionist()) {
					htmlid = "doettM4";
				}
				break;
			case 70854: // �ĸ��޷�
				if (pc.isCrown() || pc.isKnight() || pc.isWizard()) {
					htmlid = "hurinM1";
				} else if (pc.isDarkelf()) {
					htmlid = "hurinE3";
				} else if (pc.isDragonknight()) {
					htmlid = "hurinE4";
				} else if (pc.isIllusionist()) {
					htmlid = "hurinE5";
				}
				break;
			case 70843: // �𸮿�
				if (pc.isCrown() || pc.isKnight() || pc.isWizard()) {
					htmlid = "morienM1";
				} else if (pc.isDarkelf()) {
					htmlid = "morienM2";
				} else if (pc.isDragonknight()) {
					htmlid = "morienM3";
				} else if (pc.isIllusionist()) {
					htmlid = "morienM4";
				}
				break;
			case 70849: // �׿�����
				if (pc.isCrown() || pc.isKnight() || pc.isWizard()) {
					htmlid = "theodorM1";
				} else if (pc.isDarkelf()) {
					htmlid = "theodorM2";
				} else if (pc.isDragonknight()) {
					htmlid = "theodorM3";
				} else if (pc.isIllusionist()) {
					htmlid = "theodorM4";
				}
				break;
			default:
				break;
			}
			// html ǥ�� ��Ŷ �۽�
			if (htmlid != null) { // htmlid�� �����ǰ� �ִ� ���
				S_NPCTalkReturn nr = new S_NPCTalkReturn(objid, htmlid);
				pc.sendPackets(nr, true);
			} else {
				if (pc.getLawful() < -1000) { // �÷��̾ ī��ƽ
					S_NPCTalkReturn nr = new S_NPCTalkReturn(talking, objid, 2);
					pc.sendPackets(nr, true);
				} else {
					S_NPCTalkReturn nr = new S_NPCTalkReturn(talking, objid, 1);
					pc.sendPackets(nr, true);
				}
			}
		}
		htmlid = null;
	}

	@Override
	public void onFinalAction(L1PcInstance player, String action) {
	}

	public void doFinalAction(L1PcInstance player) {
	}

	private static final long REST_MILLISEC = 10000;

	private static final Timer _restTimer = new Timer(true);

	private RestMonitor _monitor;

	public class RestMonitor extends TimerTask {
		@Override
		public void run() {
			setRest(false);
		}
	}

	@Override
	public void receiveDamage(L1Character attacker, int damage) {
		if (getCurrentHp() > 0 && !isDead()) {
			if (damage > 0) {
				if (getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.FOG_OF_SLEEPING)) {
					getSkillEffectTimerSet().removeSkillEffect(
							L1SkillId.FOG_OF_SLEEPING);
				} else if (getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.PHANTASM)) {
					getSkillEffectTimerSet().removeSkillEffect(
							L1SkillId.PHANTASM);
				} else if (getSkillEffectTimerSet().hasSkillEffect(
						L1SkillId.DARK_BLIND)) {
					getSkillEffectTimerSet().removeSkillEffect(
							L1SkillId.DARK_BLIND);
				}
			}

			// onNpcAI();

			if (attacker instanceof L1PcInstance && damage > 0) {
				L1PcInstance pc = (L1PcInstance) attacker;
				pc.setPetTarget(this);
			}

			int newHp = getCurrentHp() - damage;
			if (newHp <= 0 && !isDead()) {
				setCurrentHp(0);
				setDead(true);
				setActionStatus(ActionCodes.ACTION_Die);
				Death death = new Death(attacker);
				GeneralThreadPool.getInstance().execute(death);
			}
			if (newHp > 0) {
				setCurrentHp(newHp);
			}
		} else if (getCurrentHp() == 0 && !isDead()) {
		} else if (!isDead()) {
			setDead(true);
			setActionStatus(ActionCodes.ACTION_Die);
			Death death = new Death(attacker);
			GeneralThreadPool.getInstance().execute(death);
		}
	}

	class Death implements Runnable {
		L1Character _lastAttacker;

		public Death(L1Character lastAttacker) {
			_lastAttacker = lastAttacker;
		}

		@Override
		public void run() {
			try {
				setDeathProcessing(true);
				setCurrentHp(0);
				setDead(true);
				setActionStatus(ActionCodes.ACTION_Die);
				getMap().setPassable(getLocation(), true);
				Broadcaster.broadcastPacket(L1PeopleInstance.this,
						new S_DoActionGFX(getId(), ActionCodes.ACTION_Die),
						true);

				startChat(CHAT_TIMING_DEAD);

				int lawful = 5000;// L1PeopleInstance.this.getNpcTemplate().get_lawful();
				if (lawful > 0) {
					_lastAttacker.addLawful(-lawful);
				}

				setDeathProcessing(false);

				allTargetClear();

				startDeleteTimer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
