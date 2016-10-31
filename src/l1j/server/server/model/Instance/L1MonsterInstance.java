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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;
import l1j.server.Config;
import l1j.server.GameSystem.GameList;
import l1j.server.GameSystem.Antaras.AntarasRaid;
import l1j.server.GameSystem.Antaras.AntarasRaidSystem;
import l1j.server.GameSystem.Antaras.AntarasRaidTimer;
import l1j.server.GameSystem.FireDragon.FireDragon;
import l1j.server.GameSystem.FireDragon.FireDragonSpawn;
import l1j.server.GameSystem.Lind.Lind;
import l1j.server.GameSystem.Lind.LindThread;
import l1j.server.GameSystem.NavalWarfare.NavalWarfare;
import l1j.server.GameSystem.NavalWarfare.NavalWarfareController;
import l1j.server.GameSystem.Papoo.PaPooRaid;
import l1j.server.GameSystem.Papoo.PaPooRaidSystem;
import l1j.server.GameSystem.Papoo.PaPooTimer;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.DoorSpawnTable;
import l1j.server.server.datatables.DragonRaidItemTable;
import l1j.server.server.datatables.DropTable;
import l1j.server.server.datatables.NPCTalkDataTable;
import l1j.server.server.datatables.UBTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.Dead;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1HateList;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1NpcDeleteTimer;
import l1j.server.server.model.L1NpcTalkData;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PinkName;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1UltimateBattle;
import l1j.server.server.model.L1World;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_MPUpdate;
import l1j.server.server.serverpackets.S_MonsterBookUI;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.utils.CalcExp;
import l1j.server.server.utils.L1SpawnUtil;

public class L1MonsterInstance extends L1NpcInstance {
	private static final long serialVersionUID = 1L;
	public static int[][] _classGfxId = { { 0, 1 }, { 48, 61 }, { 37, 138 },
			{ 734, 1186 }, { 2786, 2796 }, { 6658, 6661 }, { 6671, 6650 } };
	private static Random _random = new Random(System.nanoTime());
	private byte _storeDroped;
	private boolean seeon = false;
	private Dead dead = new Dead(this, null);

	public boolean lind_fly = false;
	public boolean lind_fly2 = false;
	private boolean �����_���� = false;

	public boolean lind_level2_cloud = false;
	public byte �ػ�������̹ʹܰ� = 1;
	public boolean kir_absolute = false;
	public boolean kir_counter_barrier = false;
	public boolean kir_counter_magic = false;
	public boolean kir_poison_barrier = false;

	public static �����ڸ��۽ð�üũ ���۽ð�üũ;

	private ArrayList<L1PcInstance> �����κ긮��Ʈ = new ArrayList<L1PcInstance>();

	private boolean shellHydra = false;
	public boolean shellManClose = false;

	@Override
	public void onItemUse() {
		if (!isActived() && _target != null) {
			// /////////////////��Ǫ
			if (getNpcTemplate().is_doppel() && _target instanceof L1PcInstance) {
				L1PcInstance targetPc = (L1PcInstance) _target;
				setName(_target.getName());
				setNameId(_target.getName());
				setTitle(_target.getTitle());
				setTempLawful(_target.getLawful());
				getGfxId().setTempCharGfx(targetPc.getClassId());
				getGfxId().setGfxId(targetPc.getClassId());
				setPassispeed(640);
				setAtkspeed(900);
				for (L1PcInstance pc : L1World.getInstance()
						.getRecognizePlayer(this)) {
					pc.sendPackets(new S_RemoveObject(this), true);
					pc.getNearObjects().removeKnownObject(this);
					pc.updateObject();
				}
			}
		}
		if (_target != null) {
			// if (getLevel() <= 45) {
			useItem(USEITEM_HASTE, 40);
			// }
		}
		if (getCurrentHp() * 100 / getMaxHp() < 40) {
			useItem(USEITEM_HEAL, 50);
		}
	}

	public void teleport(int nx, int ny, int dir) {
		isTeleport = true;
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			pc.sendPackets(new S_SkillSound(getId(), 169), true);
			pc.sendPackets(new S_RemoveObject(this), true);
			pc.getNearObjects().removeKnownObject(this);
		}
		L1World.getInstance().moveVisibleObject(this, nx, ny, this.getMapId());
		setX(nx);
		setY(ny);
		getMoveState().setHeading(dir);
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			try {
				pc.getNearObjects().addKnownObject(this);
				pc.sendPackets(new S_NPCPack(this), true);
			} catch (Exception e) {
			}
		}
		isTeleport = false;
	}

	@Override
	public void onPerceive(L1PcInstance perceivedFrom) {
		perceivedFrom.getNearObjects().addKnownObject(this);
		if (0 < getCurrentHp()) {
			if (getHiddenStatus() == HIDDEN_STATUS_SINK) {
				perceivedFrom.sendPackets(new S_DoActionGFX(getId(),
						ActionCodes.ACTION_Hide), true);
			} else if (getHiddenStatus() == HIDDEN_STATUS_FLY) {
				perceivedFrom.sendPackets(new S_DoActionGFX(getId(),
						ActionCodes.ACTION_Moveup), true);
			}
			// testonNpcAI();
			onNpcAI();

			/*
			 * if (getMoveState().getBraveSpeed() == 1) {
			 * perceivedFrom.sendPackets(new S_SkillBrave(getId(), 1, 600000));
			 * }
			 */
		}
		perceivedFrom.sendPackets(new S_NPCPack(this), true);
		if ((getNpcTemplate().get_npcId() == 4038000
				|| getNpcTemplate().get_npcId() == 4200010 || getNpcTemplate()
				.get_npcId() == 4200011) && !seeon) {
			Broadcaster.broadcastPacket(this, new S_DoActionGFX(getId(),
					ActionCodes.ACTION_Hide), true);
			seeon = true;
		}

		if ((getNpcTemplate().get_npcId() == 4039000
				|| getNpcTemplate().get_npcId() == 4039006 || getNpcTemplate()
				.get_npcId() == 4039007) && !seeon) {
			Broadcaster.broadcastPacket(this, new S_DoActionGFX(getId(),
					ActionCodes.ACTION_Hide), true);
			seeon = true;
		}

		if ((getMapId() >= 1005 && getMapId() <= 1010)
				|| (getMapId() >= 1011 && getMapId() <= 1016)) {
			if (getMap().getOriginalTile(getX(), getY()) == 0) {
				teleport(perceivedFrom.getX(), perceivedFrom.getY(), 0);
			}
		}

	}

	@Override
	public void searchTarget() {
		L1PcInstance targetPlayer = null;
		L1MonsterInstance targetMonster = null; // ����ƺ��б�

		if (getNpcTemplate().get_npcId() >= 100750
				&& getNpcTemplate().get_npcId() <= 100757) {
			// System.out.println("123");
			for (L1Object obj : L1World.getInstance()
					.getVisibleObjects(getMapId()).values()) {
				if (obj instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) obj;
					if (mon.isDead())
						continue;
					if (mon.getNpcTemplate().get_npcId() == 100749) {
						// System.out.println("555555555555");
						targetMonster = mon;
						break;
					}
				}
			}
		} else {
			ArrayList<L1PcInstance> list = L1World.getInstance()
					.getVisiblePlayer(this);
			if (list.size() > 1)
				Collections.shuffle(list);
			for (L1PcInstance pc : list) {
				if (pc.getCurrentHp() <= 0 || pc.isDead() || pc.isSGm()
						|| pc.isGm() || pc.isMonitor() || pc.isGhost()) {
					continue;
				}

				if (_backtarget != null) {
					if (_backtarget instanceof L1PcInstance) {
						L1PcInstance _pc = (L1PcInstance) _backtarget;
						if (pc.getName() == _pc.getName()) {
							_backtargetre++;
							if (_backtargetre > 3) {
								_backtarget = null;
								_backtargetre = 0;
							}
							continue;
						}
					}
				}

				int mapId = getMapId();
				if (mapId == 88 || mapId == 98 || mapId == 92 || mapId == 91
						|| mapId == 95) {
					if (!pc.isInvisble() || getNpcTemplate().is_agrocoi()) {
						targetPlayer = pc;
						break;
					}
				}

				if (getNpcId() == 45600) {
					if (pc.isCrown()
							|| pc.isDarkelf()
							|| pc.getGfxId().getTempCharGfx() != pc
									.getClassId()) {
						targetPlayer = pc;
						break;
					}
				}

				if ((getNpcTemplate().getKarma() < 0 && pc.getKarmaLevel() >= 1)
						|| (getNpcTemplate().getKarma() > 0 && pc
								.getKarmaLevel() <= -1)) {
					continue;
				}

				// ���� ����Ʈ�� ����, �� ������ monster�κ��� ���� ���ݹ��� �ʴ´�
				if (pc.getGfxId().getTempCharGfx() == 6034
						&& getNpcTemplate().getKarma() < 0
						|| pc.getGfxId().getTempCharGfx() == 6035
						&& getNpcTemplate().getKarma() > 0
						|| pc.getGfxId().getTempCharGfx() == 6035
						&& getNpcTemplate().get_npcId() == 46070
						|| pc.getGfxId().getTempCharGfx() == 6035
						&& getNpcTemplate().get_npcId() == 46072) {
					continue;
				}

				if (!getNpcTemplate().is_agro()
						&& !getNpcTemplate().is_agrososc()
						&& getNpcTemplate().is_agrogfxid1() < 0
						&& getNpcTemplate().is_agrogfxid2() < 0) {
					// ���� ��� ī���ν� ����
					if (getNpcId() != 4030000 && getNpcId() != 4030001
							&& pc.getLawful() < -1000) {
						targetPlayer = pc;
						break;
					}
					continue;
				}
				// agrocoi = �����ν� agrososc = �����ν� agro = �Ϲ� ��׷�
				if (!pc.isInvisble() || getNpcTemplate().is_agrocoi()) {
					if (pc.getSkillEffectTimerSet().hasSkillEffect(67)) {
						if (getNpcTemplate().is_agrososc()) {
							targetPlayer = pc;
							break;
						}
					} else if (getNpcTemplate().is_agro()) {
						targetPlayer = pc;
						break;
					}

					if (getNpcTemplate().is_agrogfxid1() >= 0
							&& getNpcTemplate().is_agrogfxid1() <= 4) {
						if (_classGfxId[getNpcTemplate().is_agrogfxid1()][0] == pc
								.getGfxId().getTempCharGfx()
								|| _classGfxId[getNpcTemplate().is_agrogfxid1()][1] == pc
										.getGfxId().getTempCharGfx()) {
							targetPlayer = pc;
							break;
						}
					} else if (pc.getGfxId().getTempCharGfx() == getNpcTemplate()
							.is_agrogfxid1()) {
						targetPlayer = pc;
						break;
					}

					if (getNpcTemplate().is_agrogfxid2() >= 0
							&& getNpcTemplate().is_agrogfxid2() <= 4) {
						if (_classGfxId[getNpcTemplate().is_agrogfxid2()][0] == pc
								.getGfxId().getTempCharGfx()
								|| _classGfxId[getNpcTemplate().is_agrogfxid2()][1] == pc
										.getGfxId().getTempCharGfx()) {
							targetPlayer = pc;
							break;
						}
					} else if (pc.getGfxId().getTempCharGfx() == getNpcTemplate()
							.is_agrogfxid2()) {
						targetPlayer = pc;
						break;
					}
				}
			} // ������ ����ƺ� �б�
		}// ������� �ù߻��
		if (getNpcTemplate().get_npcId() == 7000042
				|| getNpcTemplate().get_npcId() == 7000009
				|| getNpcTemplate().get_npcId() == 7000013
				|| getNpcTemplate().get_npcId() == 7000016
				|| getNpcTemplate().get_npcId() == 7000019
				|| getNpcTemplate().get_npcId() == 7000006
				|| getNpcTemplate().get_npcId() == 7000010
				|| getNpcTemplate().get_npcId() == 7000014
				|| getNpcTemplate().get_npcId() == 7000017
				|| getNpcTemplate().get_npcId() == 7000020
				|| getNpcTemplate().get_npcId() == 7000007
				|| getNpcTemplate().get_npcId() == 7000011
				|| getNpcTemplate().get_npcId() == 7000015
				|| getNpcTemplate().get_npcId() == 7000018
				|| getNpcTemplate().get_npcId() == 7000021
				|| getNpcTemplate().get_npcId() == 7000022
				|| getNpcTemplate().get_npcId() == 7000023
				|| getNpcTemplate().get_npcId() == 7000024
				|| getNpcTemplate().get_npcId() == 7000025
				|| getNpcTemplate().get_npcId() == 7000026
				|| getNpcTemplate().get_npcId() == 7000027
				|| getNpcTemplate().get_npcId() == 7000028
				|| getNpcTemplate().get_npcId() == 7000029
				|| getNpcTemplate().get_npcId() == 7000030
				|| getNpcTemplate().get_npcId() == 7000031
				|| getNpcTemplate().get_npcId() == 7000032
				|| getNpcTemplate().get_npcId() == 7000033
				|| getNpcTemplate().get_npcId() == 7000034
				|| getNpcTemplate().get_npcId() == 7000035
				|| getNpcTemplate().get_npcId() == 7000036
				|| getNpcTemplate().get_npcId() == 7000037
				|| getNpcTemplate().get_npcId() == 7000038
				|| getNpcTemplate().get_npcId() == 7000039
				|| getNpcTemplate().get_npcId() == 7000040
				|| getNpcTemplate().get_npcId() == 7000041
				|| getNpcTemplate().get_npcId() == 7000008
				|| getNpcTemplate().get_npcId() == 7000012
				|| getNpcTemplate().get_npcId() == 7000013
				|| getNpcTemplate().get_npcId() == 7000014
				|| getNpcTemplate().get_npcId() == 7000015) { // ���� �ν��� ����
			for (L1Object obj : L1World.getInstance().getVisibleObjects(this)) {
				if (obj instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) obj;
					if (mon.getHiddenStatus() != 0 || mon.isDead()) {
						continue;
					}
					if (mon.getNpcTemplate().get_npcId() == 45003
							|| mon.getNpcTemplate().get_npcId() == 45060
							|| mon.getNpcTemplate().get_npcId() == 45157
							|| mon.getNpcTemplate().get_npcId() == 45241
							|| mon.getNpcTemplate().get_npcId() == 45223
							|| mon.getNpcTemplate().get_npcId() == 45298) {
						targetMonster = mon;
						break;
					}
				}
			}
		} // <<�߰�
		if (targetPlayer != null) {
			_hateList.add(targetPlayer, 0);
			_target = targetPlayer;
		}
		if (targetMonster != null) {
			_hateList.add(targetMonster, 0);
			_target = targetMonster;
		} // <<����ƺ� �б�
	}

	public void setTarget(L1Character target) {
		if (target != null) {
			if (target instanceof L1PcInstance
					|| target instanceof L1MonsterInstance) {
				_hateList.add(target, 0);
				_target = target;
			}
		}
	}

	public L1Character getTarget() {
		return _target;
	}

	@Override
	public void setLink(L1Character cha) {
		if (cha != null && _hateList.isEmpty()) {
			_hateList.add(cha, 0);
			checkTarget();
		}
	}

	public L1MonsterInstance(L1Npc template) {
		super(template);
		_storeDroped = 1;
		if (getNpcId() == 100342 || getNpcId() == 100422) { // �縷 ȸ����, �����ں� ȸ����
			Random _rnd = new Random(System.nanoTime());
			int time = (_rnd.nextInt(30) + 60) * 1000;
			if (getNpcId() == 100422)
				time = (_rnd.nextInt(60) + 30) * 1000;
			L1NpcDeleteTimer timer = new L1NpcDeleteTimer(this, time);
			timer.begin();
			GeneralThreadPool.getInstance().schedule(new �����ں�ȸ����Timer(this),
					500);
		} else if (getNpcId() == 100586 || getNpcId() == 100587) {
			GeneralThreadPool.getInstance().execute(new �⸣��());
		} else if (getNpcId() == 100859) {
			GeneralThreadPool.getInstance().schedule(new ���̳�Ʈ��Timer(this), 500);
		}

	}

	// private int abcd1 = 0;
	// private int abcd2 = 0;
	@Override
	public synchronized void onNpcAI() {
		// abcd1++;
		if (isAiRunning()) {
			return;
		}
		/*
		 * abcd2++; if(abcd2 > 1){
		 * System.out.println("[�������1] t1 : "+abcd1+" / t2 : "
		 * +abcd2+" / id :"+getNpcId
		 * ()+" / x-"+getX()+" y-"+getY()+" m-"+getMapId()); }
		 */
		if (_storeDroped == 1) {
			_storeDroped = 0;
			DropTable.getInstance().setDrop(this, getInventory());
			getInventory().shuffle();
		} else if (_storeDroped == 2) {
			_storeDroped = 0;
			DropTable.getInstance().setPainwandDrop(this, getInventory());
			getInventory().shuffle();
		}

		setActived(false);
		startAI();

	}

	@Override
	public void onTalkAction(L1PcInstance pc) {
		int objid = getId();
		L1NpcTalkData talking = NPCTalkDataTable.getInstance().getTemplate(
				getNpcTemplate().get_npcId());
		if (talking == null) {
			System.out.println("�������߻���Ű�� ���ǽ� �̸� :" + getName() + " x :"
					+ getX() + " y :" + getY() + " m :" + getMapId()
					+ " Pc�̸�: " + (pc == null ? "null" : pc.getName()));
			return;
		}
		if (pc.getLawful() < -1000) { // �÷��̾ ī��ƽ
			pc.sendPackets(new S_NPCTalkReturn(talking, objid, 2), true);
		} else {

			pc.sendPackets(new S_NPCTalkReturn(talking, objid, 1), true);
		}
	}

	@Override
	public void onAction(L1PcInstance pc, int adddmg) {
		/** ��� ��Ʈ�������� ���� by��� **/
		if (getNpcTemplate().get_npcId() == 90000
				|| getNpcTemplate().get_npcId() == 90002
				|| getNpcTemplate().get_npcId() == 90009
				|| getNpcTemplate().get_npcId() == 90013
				|| getNpcTemplate().get_npcId() == 90016) {// ���
			receiveDamage(pc, 1);
		} else {
			if (shellman()) {
				L1Attack attack = new L1Attack(pc, this);
				attack.action();
				attack = null;
				return;
			}
			if (getCurrentHp() > 0 && !isDead()) {
				L1Attack attack = new L1Attack(pc, this);
				if (attack.calcHit()) {
					boolean isCounterBarrier = false;
					if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.COUNTER_BARRIER)) {

						int chan = _random.nextInt(100) + 1;
						boolean isProbability = false;
						if (18 > chan) {
							isProbability = true;
						}
						boolean isShortDistance = attack.isShortDistance();
						if (isProbability && isShortDistance) {
							isCounterBarrier = true;
						}
					}
					if (isCounterBarrier) {
						attack.actionCounterBarrier();
						attack.commitCounterBarrier();
					}
					int enc = pc.getWeapon() == null ? 0 : pc.getWeapon()
							.getEnchantLevel();
					if (enc < 0)
						enc = 0;
					if (getNpcTemplate().get_IsTU() && pc.getWeapon() != null
							&& pc.getWeapon().getItemId() == 412003
							&& _random.nextInt(100) < 5 + enc) {
						new L1SkillUse().handleCommands(pc,
								L1SkillId.TURN_UNDEAD, getId(), getX(), getY(),
								null, 0, L1SkillUse.TYPE_GMBUFF);
					} else {
						attack.calcDamage(adddmg);
						attack.calcStaffOfMana();
						attack.calcDrainOfHp();
						attack.addPcPoisonAttack(pc, this);
					}
				}
				attack.action();
				attack.commit();
				attack = null;
			}
		}
	}

	@Override
	public void onAction(L1PcInstance pc) {
		/** ��� ��Ʈ�������� ���� by��� **/
		if (getNpcTemplate().get_npcId() == 90000
				|| getNpcTemplate().get_npcId() == 90002
				|| getNpcTemplate().get_npcId() == 90009
				|| getNpcTemplate().get_npcId() == 90013
				|| getNpcTemplate().get_npcId() == 90016) {// ���
			receiveDamage(pc, 1);
		} else {
			if (shellman()) {
				L1Attack attack = new L1Attack(pc, this);
				attack.action();
				attack = null;
				return;
			}
			if (getCurrentHp() > 0 && !isDead()) {
				L1Attack attack = new L1Attack(pc, this);
				if (attack.calcHit()) {
					boolean isCounterBarrier = false;
					if (getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.COUNTER_BARRIER)) {

						int chan = _random.nextInt(100) + 1;
						boolean isProbability = false;
						if (18 > chan) {
							isProbability = true;
						}
						boolean isShortDistance = attack.isShortDistance();
						if (isProbability && isShortDistance) {
							isCounterBarrier = true;
						}
					}
					if (isCounterBarrier) {
						attack.actionCounterBarrier();
						attack.commitCounterBarrier();
					}
					int enc = pc.getWeapon() == null ? 0 : pc.getWeapon()
							.getEnchantLevel();
					if (enc < 0)
						enc = 0;
					if (getNpcTemplate().get_IsTU() && pc.getWeapon() != null
							&& pc.getWeapon().getItemId() == 412003
							&& _random.nextInt(100) < 5 + enc) {
						new L1SkillUse().handleCommands(pc,
								L1SkillId.TURN_UNDEAD, getId(), getX(), getY(),
								null, 0, L1SkillUse.TYPE_GMBUFF);
					} else {
						attack.calcDamage();
						attack.calcStaffOfMana();
						attack.calcDrainOfHp();
						attack.addPcPoisonAttack(pc, this);
					}
				}
				attack.action();
				attack.commit();
				attack = null;
			}
		}
	}

	@Override
	public void ReceiveManaDamage(L1Character attacker, int mpDamage) {
		if (mpDamage > 0 && !isDead()) {
			// int Hate = mpDamage / 10 + 10;
			// setHate(attacker, Hate);
			if (!(getNpcId() >= 100750 && getNpcId() <= 100757))
				setHate(attacker, mpDamage);

			onNpcAI();

			if (attacker instanceof L1PcInstance) {
				serchLink((L1PcInstance) attacker, getNpcTemplate()
						.get_family());
			}

			int newMp = getCurrentMp() - mpDamage;
			if (newMp < 0) {
				newMp = 0;
			}
			setCurrentMp(newMp);
		}
	}

	private static boolean �����ڸ��۽��� = false;

	class a implements Runnable {
		L1Character _cha = null;

		public a(L1Character cha) {
			_cha = cha;
		}

		public void run() {
			for (int i = 0; i < 100; i++) {
				receiveDamage(_cha, 50000);
			}
		}
	}

	class b implements Runnable {
		L1Character _cha = null;

		public b(L1Character cha) {
			_cha = cha;
		}

		public void run() {
			for (int i = 0; i < 100; i++) {
				receiveDamage(_cha, 50000);
			}
		}
	}

	public void testonNpcAI(L1Character cha) {
		a atm = new a(cha);
		b atm2 = new b(cha);
		Thread aa = new Thread(atm, "a");
		Thread bb = new Thread(atm2, "b");
		aa.start();
		bb.start();
	}

	@Override
	public void receiveDamage(L1Character attacker, int damage) {
		if (isreodie) {
			return;
		}
		if (transok) {
			return;
		}
		if (getCurrentHp() > 0 && !isDead()) {
			if (getHiddenStatus() != HIDDEN_STATUS_NONE
					|| getHiddenStatus() == HIDDEN_STATUS_FLY) {
				return;
			}
			if (attacker instanceof L1PcInstance) {
				if (getNpcTemplate().is_doppel()) {
					L1PinkName.onAction(this, attacker);
				}
			}
			if (getNpcId() == 100749 && attacker instanceof L1PcInstance)
				return;

			if (!(getNpcId() >= 100750 && getNpcId() <= 100757)) {
				if (damage >= 0) {
					if (!(attacker instanceof L1EffectInstance)) {
						setHate(attacker, damage);
					}
				}
			}
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
				if ((getNpcTemplate().get_npcId() >= 100032
						&& getNpcTemplate().get_npcId() <= 100044
						|| getNpcId() == 100338 || getNpcId() == 100157
						|| getNpcId() == 1100157 || getNpcId() == 100149
						|| getNpcId() == 1100149 || getNpcId() == 100420
						|| getNpcId() == 100397 || getNpcId() == 100584
						|| getNpcId() == 100588 || getNpcId() == 100589 || getNpcId() == 1000651
						|| getNpcId() == 60443 || getNpcId() == 100718 || getNpcId() == 100719)
						&& attacker instanceof L1PcInstance) {
					if (�����κ긮��Ʈ != null
							&& !�����κ긮��Ʈ.contains((L1PcInstance) attacker))
						�����κ긮��Ʈ.add((L1PcInstance) attacker);
				}
			}

			onNpcAI();
			if (attacker instanceof L1PcInstance) {
				serchLink((L1PcInstance) attacker, getNpcTemplate()
						.get_family());
			}
			if (attacker instanceof L1SummonInstance) {
				L1SummonInstance sum = (L1SummonInstance) attacker;
				if (getNpcTemplate().get_npcId() == 45681
						|| getNpcTemplate().get_npcId() == 45682
						|| getNpcTemplate().get_npcId() == 45683
						|| getNpcTemplate().get_npcId() == 45684) {
					sumdel(sum);
				}

			}
			if (attacker instanceof L1PcInstance && damage > 0) {
				L1PcInstance player = (L1PcInstance) attacker;
				player.setPetTarget(this);

				if (getNpcTemplate().get_npcId() == 45681
						|| getNpcTemplate().get_npcId() == 45682
						|| getNpcTemplate().get_npcId() == 45683
						|| getNpcTemplate().get_npcId() == 45684) {
					recall(player);
				}
			}

			if (getNpcTemplate().get_npcId() == 100719
					&& getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���ν�����)) {
				damage *= 0.3;
				if (damage < 0)
					damage = 0;
			}

			if (getNpcTemplate().get_npcId() == 4039001) { // ��Ǫ ��������
				if (!attacker.getSkillEffectTimerSet().hasSkillEffect(10501)) {
					damage = 0;
					return;
				} else {
					damage = 1;
				}
			}
			if (getNpcTemplate().get_npcId() == 4039002) {
				if (!attacker.getSkillEffectTimerSet().hasSkillEffect(10500)) {
					damage = 0;
					return;
				} else {
					damage = 1;
				}
			}

			if (getNpcId() == 100707 && damage > 0)
				damage = 1;

			if (getNpcTemplate().get_npcId() == 4039003) {
				damage = 0;
				return;
			}

			int newHp = getCurrentHp() - damage;
			if (!Escape) {
				if (getNpcTemplate().get_npcId() == 100045) {
					int hpRatio = 100;
					if (0 < getMaxHp()) {
						hpRatio = 100 * getCurrentHp() / getMaxHp();
					}
					if (hpRatio < 40) {
						Escape = true;
						STATUS_Escape = true;
						GeneralThreadPool.getInstance().schedule(new ����Timer(),
								500);
					}
				}
			}

			if (newHp <= 0 && !isDead()) {
				if (attacker instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) attacker;
					if (getNpcTemplate().get_npcId() == 101022) {
						pc.��ܰ���();
					}
					if (getNpcTemplate().get_npcId() == 101017) {// ����ġ��ȸ��
						new L1SkillUse().handleCommands(pc,
								L1SkillId.GREATER_HEAL, pc.getId(), pc.getX(),
								pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
					}
					if (getNpcTemplate().get_npcId() == 101018) {// ����ȸ��
						pc.setCurrentMp(pc.getCurrentMp() + 100);
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc
								.getMaxMp()));
						// new L1SkillUse().handleCommands(pc,
						// L1SkillId.BLESS_WEAPON, getId(), getX(), getY(),
						// null, 1200 ,L1SkillUse.TYPE_GMBUFF);
					}
					if (getNpcTemplate().get_npcId() == 101019) {// ��������
						new L1SkillUse().handleCommands(pc,
								L1SkillId.BLESS_WEAPON, pc.getId(), pc.getX(),
								pc.getY(), null, 1200, L1SkillUse.TYPE_GMBUFF);
					}
					if (getNpcTemplate().get_npcId() == 101020) {// ���̼ǽ�
						new L1SkillUse().handleCommands(pc, L1SkillId.PATIENCE,
								pc.getId(), pc.getX(), pc.getY(), null, 600,
								L1SkillUse.TYPE_GMBUFF);
					}
					if (getNpcTemplate().get_npcId() == 101021) {// �
						new L1SkillUse().handleCommands(pc,
								L1SkillId.ADVANCE_SPIRIT, pc.getId(),
								pc.getX(), pc.getY(), null, 1200,
								L1SkillUse.TYPE_GMBUFF);
					}
				}

				if (getMapId() >= 2600 && getMapId() <= 2698) {
					if (getNpcTemplate().get_npcId() == 100837
							|| getNpcTemplate().get_npcId() == 100838) {// ����
						isreodie = true;
						allTargetClear();
						_isReoTH = true;
						GeneralThreadPool.getInstance()
								.execute(new ����������(this));
						return;
					}
					if (getNpcTemplate().get_npcId() == 100858) {
						vchat_exit();
					}

					if (getNpcTemplate().get_npcId() == 100849
							|| getNpcTemplate().get_npcId() == 100850
							|| getNpcTemplate().get_npcId() == 100851) {
						((L1PcInstance) attacker)._��.settime(31);
					}

					if (getNpcTemplate().get_npcId() >= 100840
							&& getNpcTemplate().get_npcId() <= 100846) {
						int rnd = _random.nextInt(100);
						if (rnd < 5) {
							NPCchat("$18654", 2);
						} else if (rnd < 10) {
							NPCchat("$18655", 2);
						}
					}
				}
				// 100840~100846
				/*
				 * �߶�ī������ �� �뼭���� ���� ���̴�.18654 �߶�ī������ ���ִ� �� ��� �̽¿��� ������ �Ұ�
				 * �̴�.18655
				 */
				if (getNpcTemplate().get_npcId() == 100839) {
					NPCchat("$18878", 2);
					Broadcaster.broadcastPacket(this, new S_PacketBox(
							S_PacketBox.HADIN_DISPLAY, 2));
					for (L1Object obj : L1World.getInstance()
							.getVisibleObjects(getMapId()).values()) {
						if (obj instanceof L1DoorInstance) {
							L1DoorInstance door = (L1DoorInstance) obj;
							if (door.getNpcId() == 100835) {// 100834
								door.open();
								break;
							}
						}
					}
				}

				if (getNpcTemplate().get_npcId() == 45955) {
					L1DoorInstance door = DoorSpawnTable.getInstance().getDoor(
							4047);
					if (door != null) {
						door.setDead(false);
						door.open();
					}
				}
				if (getNpcTemplate().get_npcId() == 45585
						|| getNpcTemplate().get_npcId() == 45574
						|| getNpcTemplate().get_npcId() == 45577
						|| getNpcTemplate().get_npcId() == 45844
						|| getNpcTemplate().get_npcId() == 45588
						|| getNpcTemplate().get_npcId() == 45607
						|| getNpcTemplate().get_npcId() == 45612
						|| getNpcTemplate().get_npcId() == 45863
						|| getNpcTemplate().get_npcId() == 45608
						|| getNpcTemplate().get_npcId() == 45676
						|| getNpcTemplate().get_npcId() == 45963
						|| getNpcTemplate().get_npcId() == 45615
						|| getNpcTemplate().get_npcId() == 45648
						|| getNpcTemplate().get_npcId() == 45602) {
					if (getdoorid() != 0) {
						L1DoorInstance door = DoorSpawnTable.getInstance()
								.getDoor(getdoorid());
						if (door != null) {
							door.setDead(false);
							door.open();
						}
					}
				}
 
				if (getNpcTemplate().get_npcId() == 45959) {
					L1DoorInstance door = DoorSpawnTable.getInstance().getDoor(
							4051);
					if (door != null) {
						door.setDead(false);
						door.open();
					}
				}

				if (attacker instanceof L1PcInstance) {
					L1PcInstance _atker = (L1PcInstance) attacker;
					_atker._PlayMonKill++;
					_atker.sendPackets(new S_OwnCharStatus(_atker), true);
				}

				if (getMapId() >= 1005 && getMapId() <= 1010) {
					if (getNpcTemplate().get_npcId() == 4038001
							|| getNpcTemplate().get_npcId() == 4038002) {
						if (attacker instanceof L1PcInstance) {
							AntarasRaid ar = AntarasRaidSystem.getInstance()
									.getAR(getMapId());
							ar.MonsterCount(1);
						}
					}
				}
				if (getMapId() >= 1011 && getMapId() <= 1016) {
					if (getNpcTemplate().get_npcId() == 4039012
							|| getNpcTemplate().get_npcId() == 4039013) {
						if (attacker instanceof L1PcInstance) {
							PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
									getMapId());
							ar.MonsterCount(1);
						}
					}
				}

				if (getMapId() >= 2101 && getMapId() <= 2151) {
					if (getNpcTemplate().get_npcId() == 46142) {
						if (attacker instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) attacker;
							pc.sendPackets(new S_PacketBox(84,
									("�� �ڿ� �ִ� ������ �����ʽÿ�.")), true);
						}
					} else if (getNpcTemplate().get_npcId() == 46141) {
						if (attacker instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) attacker;
							pc.sendPackets(new S_PacketBox(84,
									("���� ������ �ó��� �ּ��κ��� �����ο������ϴ�.")), true);
						}
					}
				}

				/** ��� ��Ʈ�������� ���� by��� **/
				if (!isTrans() && getNpcTemplate().get_npcId() == 90000
						|| getNpcTemplate().get_npcId() == 90002
						|| getNpcTemplate().get_npcId() == 90009
						|| getNpcTemplate().get_npcId() == 90013
						|| getNpcTemplate().get_npcId() == 90016) {// ���
					setTrans(true);
					Broadcaster.broadcastPacket(this, new S_DoActionGFX(
							getId(), 11), true);
					transform(this.getNpcId() + 1);
					return;
				}

				Random random = new Random();
				if (attacker instanceof L1PcInstance) {
					L1PcInstance apc = (L1PcInstance) attacker;
					if (apc.getInventory().checkEquipped(20344)) {
						int chance1 = random.nextInt(100) + 1;
						if (Config.RATE_DREAM > chance1) {
							getInventory().storeItem(438005, 1); // ���������� �������
						}
					}
				}

				int npcid = getNpcTemplate().get_npcId();
				int chance2 = random.nextInt(100) + 1;
				int chance3 = random.nextInt(100) + 1;
				random = null;

				if (chance2 <= 2) {
					// ���� �ٶ�, ��, ��, �� ������ ��ȯ
					if (getNpcId() >= 100726 && getNpcId() <= 100729) {
						Broadcaster.broadcastPacket(this,
								new S_SkillSound(this.getId(), 4784), true);
						L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
								.getId(), 100730, 0, 0, 0);
					} else if (getNpcId() >= 100731 && getNpcId() <= 100734) {
						Broadcaster.broadcastPacket(this,
								new S_SkillSound(this.getId(), 4784), true);
						L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
								.getId(), 100735, 0, 0, 0);
					} else if (getNpcId() >= 100736 && getNpcId() <= 100739) {
						Broadcaster.broadcastPacket(this,
								new S_SkillSound(this.getId(), 4784), true);
						L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
								.getId(), 100740, 0, 0, 0);
					} else if (getNpcId() >= 100741 && getNpcId() <= 100744) {
						Broadcaster.broadcastPacket(this,
								new S_SkillSound(this.getId(), 4784), true);
						L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
								.getId(), 100745, 0, 0, 0);
					}
				}

				if (getMapId() >= 9103 && getMapId() <= 9199) {
					NavalWarfareController nwc = NavalWarfare.getInstance()
							.getNaval(getMapId());
					if (nwc != null) {
						if (chance2 < 5)
							nwc.score++;
						nwc.score++;
						nwc.scorePacket(nwc.score);
					}
				}
				if (chance2 <= 10) {
					if (getMapId() >= 101 && getMapId() <= 110) {
						if ((npcid == 45380 || npcid == 45409 || npcid == 45471
								|| npcid == 45455 || npcid == 45496
								|| npcid == 45524 || npcid == 45528
								|| npcid == 45540 || npcid == 45589 || npcid == 45620)
								&& !�����ڸ��۽��� /* !���۽ð�üũ.check(getMapId()) */) {
							// �޵λ�, ���̾����, �����̺�, ��������, �����巡��, ���ֿ�����, ��Ȥ����,
							// ��Һ�Ÿ������, ��������ε�, ���Ǵ�����
							�����ڸ��۽��� = true;
							GeneralThreadPool.getInstance().schedule(
									new Runnable() {
										@Override
										public void run() {
											
											L1MonsterInstance.�����ڸ��۽��� = false;
										}

									}, 3600000 * 3);
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45590, 0, 0, 0);
							// ���۽ð�üũ.add(getMapId(), getId());
						}
					}
				}
				if (npcid == 45590) {
					if (chance3 < 50) {
						if (getMapId() == 101) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45513, 0, 0, 0);
						} else if (getMapId() == 102) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45547, 0, 0, 0);
						} else if (getMapId() == 103) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45606, 0, 0, 0);
						} else if (getMapId() == 104) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45650, 0, 0, 0);
						} else if (getMapId() == 105) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45652, 0, 0, 0);
						} else if (getMapId() == 106) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45653, 0, 0, 0);
						} else if (getMapId() == 107) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45654, 0, 0, 0);
						} else if (getMapId() == 108) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45618, 0, 0, 0);
						} else if (getMapId() == 109) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 45672, 0, 0, 0);
						} else if (getMapId() == 110) {
							Broadcaster.broadcastPacket(this, new S_SkillSound(
									this.getId(), 4784), true);
							L1SpawnUtil.spawn2(getX(), getY(), (short) getMap()
									.getId(), 100002, 0, 0, 0);
						}
					}
				}

				// �׸�����
				if (npcid == 81047) {
					Broadcaster.broadcastPacket(this,
							new S_SkillSound(this.getId(), 4784), true);
				}

				if (npcid == 100719) { // ���ν�
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"���� �ᱹ �ٽ� ��Ȱ�ϰ� �� ���̴�. �ٷ� ������� �տ� ���Ͽ�! ��������..", 0),
							true);
				}

				/** ���� ���� ������ ��� **/
				if (npcid == 45673) { // ��� �׸� ����
					Broadcaster
							.broadcastPacket(
									this,
									new S_NpcChatPacket(
											this,
											"������ �ΰ����̿� ���� ������ ��! �׸�����! ������ ���� ���� �� �ٽ� ���� ���̴�. ����������",
											0), true);
				} else if (npcid == 45672) { // �Ҹ��� ��ġ
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"�ߵ�..�ߵ� �״� �����Ѱ�? ���Ͻ�..�׳��?", 0), true);
				} else if (npcid == 45618) { // ����� ����Ʈ�ߵ�
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"���� ����Ʈ�� ���� �״���ΰ�? ���� ���� ���شٿ�..��Ź����..", 0), true);
				} else if (npcid == 45654) { // ��Ȥ�� ���̸���
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"��..�ƹ���...��� ��ʴϱ�..?", 0), true);
				} else if (npcid == 45653) { // �һ��� �ӹ̷ε�
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"��..���� ������ ���..�Ǿ��°�?..", 0), true);
				} else if (npcid == 45650) { // ������ ����ε�
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"��..��...�����ո�..�־��..��..���ϴ�..", 0), true);
				} else if (npcid == 45652) { // ������ ���
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"ũ����������...", 0), true);
				} else if (npcid == 45547) { // �ҽ��� �þ�
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"�������!...", 0), true);
				} else if (npcid == 45606) { // ������ �����̾�
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"��..�̷�����..�� ������ ���� �̰ܳ��ٴ�..", 0), true);
				} else if (npcid == 45513) { // �ְ��� ���Ͻ� ��
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"������Ÿ��... ���..��� ��Ű���...", 0), true);
				}

				if (npcid == 4038000 && isantarun == false) { // ���Ǿ� ��ȣ
					isantarun = true;
					AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(
							getMapId());
					AntarasRaidTimer antaendtime = new AntarasRaidTimer(this,
							ar, 7, 2000);// 22�� üũ
					antaendtime.begin();
				}
				if (npcid == 4200010 && isantarun == false) { // ���Ǿ� ��ȣ
					isantarun = true;
					AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(
							getMapId());
					AntarasRaidTimer antaendtime = new AntarasRaidTimer(this,
							ar, 8, 2000);// 22�� üũ
					antaendtime.begin();
				}
				if (npcid == 4200011 && isantarun == false) { // ���Ǿ� ��ȣ
					isantarun = true;
					AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(
							getMapId());
					AntarasRaidTimer antaendtime = new AntarasRaidTimer(this,
							ar, 9, 2000);// 22�� üũ
					antaendtime.begin();
				}
				// /////////////////��Ǫ
				if (npcid == 4039000 && ispapoorun == false) { // ���Ǿ� ��ȣ
					ispapoorun = true;
					PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
							getMapId());
					ar.����������();
					PaPooTimer antaendtime = new PaPooTimer(this, ar, 1, 2000);// 22��
																				// üũ
					antaendtime.begin();
				}
				if (npcid == 4039006 && ispapoorun == false) { // ���Ǿ� ��ȣ
					ispapoorun = true;
					PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
							getMapId());
					ar.����������();
					PaPooTimer antaendtime = new PaPooTimer(this, ar, 2, 2000);// 22��
																				// üũ
					antaendtime.begin();
				}
				if (npcid == 4039007 && ispapoorun == false) { // ���Ǿ� ��ȣ
					ispapoorun = true;
					PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
							getMapId());
					ar.����������();
					PaPooTimer antaendtime = new PaPooTimer(this, ar, 3, 2000);// 22��
																				// üũ
					antaendtime.begin();
				}
				GeneralThreadPool.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						
						try {
							if (getNpcTemplate().get_npcId() >= 100032
									&& getNpcTemplate().get_npcId() <= 100044
									|| getNpcId() == 100338
									|| getNpcId() == 100157
									|| getNpcId() == 1100157
									|| getNpcId() == 100149
									|| getNpcId() == 1100149
									|| getNpcId() == 100397
									|| getNpcId() == 100420
									|| getNpcId() == 100584
									|| getNpcId() == 100588
									|| getNpcId() == 100589
											|| getNpcId() == 1000651
									|| getNpcId() == 100707
									|| getNpcId() == 100718
									|| getNpcId() == 100719) {
								int itemid = 0;
								if (getNpcTemplate().get_npcId() >= 100034
										&& getNpcTemplate().get_npcId() <= 100037) {
									itemid = 60057;
								} else if (getNpcId() == 100338)
									itemid = 60184;
								else if (getNpcId() == 1000651)
									itemid = 437009;
								else 
									if (getNpcId() == 100397)
									itemid = 60211;
								else if (getNpcId() == 100420)
									itemid = 60252;
								else if (getNpcId() == 100584
										|| getNpcId() == 100588
										|| getNpcId() == 100589)
									itemid = 60311;
								else if (getNpcId() == 100149
										|| getNpcId() == 1100149)// �õ� �ػ���
									itemid = 600226;

								else if (getNpcId() == 100157
										|| getNpcId() == 1100157)// �õ� �ػ���
									itemid = 500022;

								else if (getNpcId() == 100707)
									itemid = 60443;
								else if (getNpcId() == 100718)
									itemid = 60497;
								else if (getNpcId() == 100719)
									itemid = 60496;
								if (itemid != 0) {
									if (�����κ긮��Ʈ != null && �����κ긮��Ʈ.size() > 0) {
										L1PcInstance[] ������Ʈ = �����κ긮��Ʈ
												.toArray(new L1PcInstance[�����κ긮��Ʈ
														.size()]);
										for (L1PcInstance temppc : ������Ʈ) {
											int i = getLocation()
													.getTileDistance(
															temppc.getLocation());
											if (i > 15
													|| getMapId() != temppc
															.getMapId())
												continue;
											L1ItemInstance tempitem = temppc
													.getInventory().storeItem(
															itemid, 1);
											temppc.sendPackets(
													new S_ServerMessage(403,
															tempitem.getName()
																	+ " (" + 1
																	+ ")"),
													true);
											�����κ긮��Ʈ.remove(temppc);
										}
										������Ʈ = null;
									}
								}
								if (�����κ긮��Ʈ != null && �����κ긮��Ʈ.size() > 0) {
									�����κ긮��Ʈ.clear();
								}
								�����κ긮��Ʈ = null;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}, 1);

				if (getMapId() == 1931) {
					int _rnd = _random.nextInt(100);
					int rate = 13;
					if (getNpcId() == 100726 || getNpcId() == 100733
							|| getNpcId() == 100739 || getNpcId() == 100743) {
						rate = 5; // ����ȥ�� 100%���޵ǰ�
					}
					if (attacker instanceof L1PcInstance) {

						if (getNpcId() >= 100726 && getNpcId() <= 100744
								&& getNpcId() != 100730 && getNpcId() != 100735
								&& getNpcId() != 100740) {
							if (_rnd < rate) {
								if (attacker.getInventory().countItems(60501) < 50) {
									L1ItemInstance tempitem = attacker
											.getInventory().storeItem(60501, 1);
									((L1PcInstance) attacker)
											.sendPackets(new S_ServerMessage(
													403, tempitem.getName()
															+ " (" + 1 + ")"),
													true);
								}
							}
						}

					} else if (attacker instanceof L1SummonInstance) {
						L1SummonInstance sum = (L1SummonInstance) attacker;
						if (getNpcId() >= 100726 && getNpcId() <= 100744
								&& getNpcId() != 100730 && getNpcId() != 100735
								&& getNpcId() != 100740) {
							if (sum.getMaster() != null) {
								if (rate < rate) {
									if (sum.getMaster().getInventory()
											.countItems(60501) < 50) {
										L1ItemInstance tempitem = sum
												.getMaster().getInventory()
												.storeItem(60501, 1);
										((L1PcInstance) sum.getMaster())
												.sendPackets(
														new S_ServerMessage(
																403,
																tempitem.getName()
																		+ " ("
																		+ 1
																		+ ")"),
														true);
									}
								}
							}
						}
					} else if (attacker instanceof L1PetInstance) {
						L1PetInstance sum = (L1PetInstance) attacker;
						if (getNpcId() >= 100726 && getNpcId() <= 100744
								&& getNpcId() != 100730 && getNpcId() != 100735
								&& getNpcId() != 100740) {
							if (sum.getMaster() != null) {
								if (rate < rate) {
									if (sum.getMaster().getInventory()
											.countItems(60501) < 50) {
										L1ItemInstance tempitem = sum
												.getMaster().getInventory()
												.storeItem(60501, 1);
										((L1PcInstance) sum.getMaster())
												.sendPackets(
														new S_ServerMessage(
																403,
																tempitem.getName()
																		+ " ("
																		+ 1
																		+ ")"),
														true);
									}
								}
							}
						}
					}
				}

	                
				// ����� ����ƺ�
				if (getNpcId() >= 100774 && getNpcId() <= 100776) {
					GeneralThreadPool.getInstance().schedule(new Runnable() {
						@Override
						public void run() {
							resurrect(getMaxHp());
						}
					}, 4000);
				}

				int transformId = getNpcTemplate().getTransformId();
				if (transformId == -1) {
					setCurrentHp(0);
					setDead(true);
					setActionStatus(ActionCodes.ACTION_Die);
					if (isPapoo()) {
						setDeathProcessing(true);
						setCurrentHp(0);
						setDead(true);
						setDeathProcessing(false);
						setExp(0);
						setKarma(0);
						allTargetClear();
						setActionStatus(ActionCodes.ACTION_Die);
						Broadcaster.broadcastPacket(this, new S_DoActionGFX(
								getId(), ActionCodes.ACTION_Die), true);
						deleteMe();
						return;
					} else {
						if (getNpcId() == 45456 && _random.nextInt(100) < 50) {
							Broadcaster.broadcastPacket(this,
									new S_DoActionGFX(getId(),
											ActionCodes.ACTION_Die), true);
							deleteMe();
							GeneralThreadPool.getInstance().schedule(
									new Runnable() {
										@Override
										public void run() {
											
											try {
												L1NpcInstance mon = L1SpawnUtil.spawn2(
														getX(), getY(),
														(short) getMap()
																.getId(),
														100628, 0, 0, 0);
												Thread.sleep(500);
												Broadcaster.broadcastPacket(
														mon,
														new S_NpcChatPacket(
																mon, "$15700",
																0), true);
											} catch (Exception e) {
											}
										}
									}, 1000);
							return;
						}

						Death death = new Death(attacker, this);
						GeneralThreadPool.getInstance().execute(death);

						dead.setAttacker(attacker);
						GeneralThreadPool.getInstance().execute(dead);

					}
					if (ismarble()) {
						for (L1Object obj : L1World.getInstance()
								.getVisibleObjects(this, 15)) {
							if (obj instanceof L1NpcInstance) {
								L1NpcInstance npc = (L1NpcInstance) obj;
								if (npc.getNpcId() == 4039000
										|| npc.getNpcId() == 4039006
										|| npc.getNpcId() == 4039007) {
									npc.marble.remove("��������");
									if (npc.marble.size() == 0) {
										// PaPooRaid ar =
										// PaPooRaidSystem.getInstance().getAR(npc.getMapId());
										// ar.�翤().deleteMe();
									}
								}
							}
						}
						setCurrentHp(0);
						setDead(true);
						setActionStatus(ActionCodes.ACTION_Die);

						Death death = new Death(attacker, this);
						GeneralThreadPool.getInstance().execute(death);

						dead.setAttacker(attacker);
						GeneralThreadPool.getInstance().execute(dead);

					}
					if (ismarble2()) {
						setCurrentHp(0);
						setDead(true);
						for (L1Object obj : L1World.getInstance()
								.getVisibleObjects(this, 15)) {
							if (obj instanceof L1NpcInstance) {
								L1NpcInstance npc = (L1NpcInstance) obj;
								if (npc.getNpcId() == 4039000
										|| npc.getNpcId() == 4039006
										|| npc.getNpcId() == 4039007) {
									npc.marble2.remove("�ź��ѿ�������");
									if (npc.marble2.size() == 0) {
										// PaPooRaid ar =
										// PaPooRaidSystem.getInstance().getAR(npc.getMapId());
										// ar.�翤2().deleteMe();
										Broadcaster.broadcastPacket(npc,
												new S_SkillHaste(npc.getId(),
														0, 0), true);
										npc.getMoveState().setMoveSpeed(0);
									}
								}
							}
						}
						setActionStatus(ActionCodes.ACTION_Die);

						Death death = new Death(attacker, this);
						GeneralThreadPool.getInstance().execute(death);

						dead.setAttacker(attacker);
						GeneralThreadPool.getInstance().execute(dead);

					}
				} else {
					/*
					 * if(isGDMonster()){ setCurrentHp(0); setDead(true);
					 * die2(attacker); ///////�������Ÿ///// } else
					 */
					if (isAntharas()) {
						setCurrentHp(0);
						setDead(true);
						Death death = new Death(attacker, this);
						GeneralThreadPool.getInstance().execute(death);

						dieAntharas(attacker);
						GeneralThreadPool.getInstance().execute(dead);
						// ////�������Ÿ//////
					}
					if (isPapoo()) {
						setCurrentHp(0);
						setDead(true);
						diePaPoo(attacker);

						Death death = new Death(attacker, this);
						GeneralThreadPool.getInstance().execute(death);

						GeneralThreadPool.getInstance().execute(dead);
					} else {
						// distributeExpDropKarma(attacker);
						transok = true;
						transform(transformId);
					}
				}
				if (attacker instanceof L1PcInstance) {
					L1PcInstance apc = (L1PcInstance) attacker;
					if (npcid == 100623) { // ��ƺ��� ȥ��(�Ķ�)
						L1Location newLocation = apc.getLocation()
								.randomLocation(200, true);
						int newX = newLocation.getX();
						int newY = newLocation.getY();
						short mapId = (short) newLocation.getMapId();
						apc.dx = newX;
						apc.dy = newY;
						apc.dm = mapId;
						apc.dh = apc.getMoveState().getHeading();
						apc.setTelType(7);
						apc.sendPackets(new S_SabuTell(apc), true);
					} else if (npcid == 100624) { // ��ƺ��� ȥ��(���)
						if ((getMapId() >= 807 && getMapId() <= 812)
								|| getMapId() == 1) {
							L1Location newLocation = new L1Location(apc.getX(),
									apc.getY(), apc.getMapId() + 1)
									.randomLocation(200, true);
							int newX = newLocation.getX();
							int newY = newLocation.getY();
							short mapId = (short) newLocation.getMapId();
							apc.dx = newX;
							apc.dy = newY;
							apc.dm = mapId;
							apc.dh = apc.getMoveState().getHeading();
							apc.setTelType(7);
							apc.sendPackets(new S_SabuTell(apc), true);
						}// 33461 32336
					} else if (npcid >= 100711 && npcid <= 100713) { // �Ŵ� �ذ��
																		// (���)
						// if(_random.nextInt(1) == 0){
						if (_random.nextInt(1000) == 1) {
							L1Location newLocation = null;
							if (_random.nextInt(1000) < 500) {
								newLocation = new L1Location(33459, 32338, 4);
							} else {
								switch (_random.nextInt(3)) {
								case 0:
									newLocation = new L1Location(33393, 32344,
											4);
									break;
								case 1:
									newLocation = new L1Location(33263, 32402,
											4);
									break;
								case 2:
									newLocation = new L1Location(33336, 32436,
											4);
									break;
								}
							}
							if (newLocation != null) {
								int newX = newLocation.getX();
								int newY = newLocation.getY();
								short mapId = (short) newLocation.getMapId();
								apc.dx = newX;
								apc.dy = newY;
								apc.dm = mapId;
								apc.dh = apc.getMoveState().getHeading();
								apc.setTelType(7);
								apc.sendPackets(new S_SabuTell(apc), true);
							}
						}

					}
				}
			}
			if (newHp > 0) {
				if (getNpcId() == 45590) {
					if (���۴��üũ == null) {
						���۴��üũ = new boolean[4];
					}
					int hpRatio = (newHp * 100) / getMaxHp();
					if (hpRatio <= 25 && !���۴��üũ[2] && !���۴��üũ[3]) {
						if (!���۽ð�üũ.check(getMapId())) {
							���۴��üũ[3] = true;
							// ���۰� ������ ���� Ȯ��
							Broadcaster.broadcastPacket(this,
									new S_NpcChatPacket(this,
											"�� ��鿡�� Ư���� ������ ������ ������ ���ָ�!", 0),
									true);
						} else {
							���۴��üũ[2] = true;
							Broadcaster.broadcastPacket(this,
									new S_NpcChatPacket(this,
											"ũ��..�ΰ��� ġ��� �����̱���..", 0), true);
						}
						���۴��üũ[0] = true;
						���۴��üũ[1] = true;
					} else if (hpRatio <= 50 && !���۴��üũ[1]) {
						���۴��üũ[1] = true;
						���۴��üũ[0] = true;
						Broadcaster.broadcastPacket(this, new S_NpcChatPacket(
								this, "������ �ΰ� ���� �뼭���ְڴ�.������!", 0), true);
					} else if (hpRatio <= 75 && !���۴��üũ[0]) {
						���۴��üũ[0] = true;
						Broadcaster.broadcastPacket(this, new S_NpcChatPacket(
								this, "�׸����۴Կ��� �帱 �����̷α���.", 0), true);
					}
				}

				setCurrentHp(newHp);
				hide();
			}
		} else if (!isDead()) {
			if (getNpcTemplate().get_npcId() == 100837
					|| getNpcTemplate().get_npcId() == 100838) {// ����
				if (getMapId() >= 2600 && getMapId() <= 2698) {
					isreodie = true;
					allTargetClear();
					_isReoTH = true;
					GeneralThreadPool.getInstance().execute(new ����������(this));
				}
			} else {
				setDead(true);
				setActionStatus(ActionCodes.ACTION_Die);

				Death death = new Death(attacker, this);
				GeneralThreadPool.getInstance().execute(death);

				dead.setAttacker(attacker);
				GeneralThreadPool.getInstance().execute(dead);
			}
			// }
		}
	}

	public boolean isreodie = false;
	private boolean[] ���۴��üũ;

	public static class �����ڸ��۽ð�üũ implements Runnable {

		private FastMap<Integer, Integer> list = new FastMap<Integer, Integer>();

		public boolean check(int map) {
			return list.get(map) != null;
		}

		public void add(int map, int id) {
			list.put(map, id);
		}

		@Override
		public void run() {
			
			try {
				Date day = new Date(System.currentTimeMillis());
				if (day.getSeconds() == 0 && day.getMinutes() == 0) {
					list.clear();
					GeneralThreadPool.getInstance().schedule(this, 3000);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			GeneralThreadPool.getInstance().schedule(this, 1000);
		}

	}

	public void setDeath(Dead d) {
		dead = d;
	}

	private void recall(L1PcInstance pc) {
		if (getMapId() != pc.getMapId()) {
			return;
		}
		if (getLocation().getTileLineDistance(pc.getLocation()) > 4) {
			L1Location newLoc = null;
			for (int count = 0; count < 10; count++) {
				newLoc = getLocation().randomLocation(3, 4, false);
				if (CharPosUtil.isAreaAttack(this, newLoc.getX(),
						newLoc.getY(), newLoc.getMapId())) {
					if (pc instanceof L1RobotInstance) {
						L1RobotInstance rob = (L1RobotInstance) pc;
						L1Teleport.�κ���(rob, newLoc.getX(), newLoc.getY(),
								getMapId(), true);
						break;
					}
					L1Teleport.teleport(pc, newLoc.getX(), newLoc.getY(),
							getMapId(), 5);
					break;
				}
			}
		}
	}

	private void sumdel(L1SummonInstance sum) {
		if (getMapId() != sum.getMapId()) {
			return;
		}
		sum.Death(this);
	}

	@Override
	public void setCurrentHp(int i) {
		super.setCurrentHp(i);

		if (getMaxHp() > getCurrentHp()) {
			startHpRegeneration();
		}
	}

	@Override
	public void setCurrentMp(int i) {
		super.setCurrentMp(i);

		if (getMaxMp() > getCurrentMp()) {
			startMpRegeneration();
		}
	}

	class Death implements Runnable {
		L1Character _lastAttacker;
		L1MonsterInstance _Mon;

		public Death(L1Character lastAttacker, L1MonsterInstance mon) {
			_lastAttacker = lastAttacker;
			_Mon = mon;
		}

		@Override
		public void run() {
			setDeathProcessing(true);
			setCurrentHp(0);
			setDead(true);
			getMap().setPassable(getLocation(), true);
			if (getNpcId() == 100106
					|| (getNpcId() >= 100094 && getNpcId() <= 100097)) { // ������
																			// �ζ�
																			// ��,
																			// ��Ÿ��
																			// ��
				setActionStatus(ActionCodes.ACTION_Open);
				broadcastPacket(new S_DoActionGFX(getId(),
						ActionCodes.ACTION_Open), true);
				eggDie();
			} else {
				setActionStatus(ActionCodes.ACTION_Die);
				broadcastPacket(new S_DoActionGFX(getId(),
						ActionCodes.ACTION_Die), true);
			}
			startChat(CHAT_TIMING_DEAD);
			distributeExpDropKarma(_lastAttacker);
			giveUbSeal();
			setDeathProcessing(false);
			setExp(0);
			setKarma(0);
			setLawful(0);
			allTargetClear();
			if (getNpcId() == 4036016 || getNpcId() == 4036017) { // ���극��
				GeneralThreadPool.getInstance().execute(new �����Ȱ������(_Mon));
			} else
				startDeleteTimer();
		}
	}

	/*
	 * 32654 33000 32694 33052
	 */
	/*
	 * public void die(L1Character lastAttacker) { setDeathProcessing(true);
	 * setCurrentHp(0); setDead(true); getMap().setPassable(getLocation(),
	 * true); if(getNpcId() == 100106 || (getNpcId() >= 100094 && getNpcId() <=
	 * 100097)){ // ������ �ζ� ��, ��Ÿ�� �� setActionStatus(ActionCodes.ACTION_Open);
	 * Broadcaster.broadcastPacket(this, new
	 * S_DoActionGFX(this.getId(),ActionCodes.ACTION_Open), true); eggDie();
	 * }else{ setActionStatus(ActionCodes.ACTION_Die);
	 * Broadcaster.broadcastPacket(this, new S_DoActionGFX(getId(),
	 * ActionCodes.ACTION_Die), true); } startChat(CHAT_TIMING_DEAD);
	 * distributeExpDropKarma(lastAttacker); giveUbSeal();
	 * setDeathProcessing(false); setExp(0); setKarma(0); setLawful(0);
	 * allTargetClear(); if(this.getNpcId() == 4036016 || this.getNpcId() ==
	 * 4036017){ // ���극�� GeneralThreadPool.getInstance().execute(new
	 * �����Ȱ������(this)); }else startDeleteTimer(); }
	 */

	private void eggDie() {
		if (getNpcId() == 100106) {
			int rnd = _random.nextInt(100) + 1;
			if (rnd <= 50) {
				L1SpawnUtil.spawn3(this, 100104, 2, 0, false);
			} else {
				L1SpawnUtil.spawn3(this, 100105, 2, 0, false);
			}
		}
	}

	class ���������� implements Runnable {
		private L1MonsterInstance mon;
		private boolean reook = false;
		private int time = 20;
		FireDragon FD = null;

		public ����������(L1MonsterInstance _mon) {
			mon = _mon;
			NPCchat("$18875", 2);
		}

		/*
		 * �𸣴¸�Ʈ�� 18632 18634
		 */
		@Override
		public void run() {
			try {

				while (!reook) {
					time--;
					if (time <= 0) {
						if (mon.getNpcId() == 100837) {// ����1
							mon.teleport(32653, 33000, 2);
							NPCchat("$18876", 2);
							try {
								Thread.sleep(2000L);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							}

							synchronized (GameList.FDList) {
								FD = GameList.getFD(mon.getMapId());
							}

							if (FD == null) {
								System.out.println("�߸��� ������ ��ǥ : " + "x "
										+ mon.getX() + " y " + mon.getY()
										+ " map " + mon.getMapId());
								reook = true;
								return;
							}

							FireDragonSpawn.getInstance().Spawn(null,
									mon.getMapId(), 1);
							_door.open();
							reook = true;
							setDead(true);
							setActionStatus(ActionCodes.ACTION_Die);
							Death death = new Death(null, mon);
							GeneralThreadPool.getInstance().execute(death);
							dead.setAttacker(null);
							GeneralThreadPool.getInstance().execute(dead);
							return;
						} else if (mon.getNpcId() == 100838) {// ����2
							mon.teleport(32693, 33052, 2);
							NPCchat("$18877", 2);
							try {
								Thread.sleep(2000L);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							}
							synchronized (GameList.FDList) {
								FD = GameList.getFD(mon.getMapId());
							}
							if (FD == null) {
								System.out.println("�߸��� ������ ��ǥ : " + "x "
										+ mon.getX() + " y " + mon.getY()
										+ " map " + mon.getMapId());
								reook = true;
								return;
							}

							FireDragonSpawn.getInstance().Spawn(null,
									mon.getMapId(), 2);
							_door.open();
							// die(null);
							reook = true;
							setDead(true);
							setActionStatus(ActionCodes.ACTION_Die);

							Death death = new Death(null, mon);
							GeneralThreadPool.getInstance().execute(death);

							dead.setAttacker(null);
							GeneralThreadPool.getInstance().execute(dead);

							return;
						}
					} else {
						if (mon.getNpcId() == 100837 && mon.getX() == 32653
								&& mon.getY() == 33000) {// ����1
							mon.getMoveState().setHeading(2);
							Broadcaster.broadcastPacket(mon,
									new S_ChangeHeading(mon));
							NPCchat("$18876", 2);
							try {
								Thread.sleep(2000L);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							}
							_door.open();
							synchronized (GameList.FDList) {
								FD = GameList.getFD(mon.getMapId());
							}
							if (FD == null) {
								System.out.println("�߸��� ������ ��ǥ : " + "x "
										+ mon.getX() + " y " + mon.getY()
										+ " map " + mon.getMapId());
								reook = true;
								return;
							}

							FireDragonSpawn.getInstance().Spawn(null,
									mon.getMapId(), 1);
							// die(null);
							reook = true;
							setDead(true);
							setActionStatus(ActionCodes.ACTION_Die);

							Death death = new Death(null, mon);
							GeneralThreadPool.getInstance().execute(death);

							dead.setAttacker(null);
							GeneralThreadPool.getInstance().execute(dead);
							return;
						} else if (mon.getNpcId() == 100838
								&& mon.getX() == 32693 && mon.getY() == 33052) {// ����2
							mon.getMoveState().setHeading(2);
							Broadcaster.broadcastPacket(mon,
									new S_ChangeHeading(mon));
							NPCchat("$18877", 2);
							try {
								Thread.sleep(2000L);
							} catch (InterruptedException e) {
								
								e.printStackTrace();
							}
							_door.open();
							synchronized (GameList.FDList) {
								FD = GameList.getFD(mon.getMapId());
							}
							if (FD == null) {
								System.out.println("�߸��� ������ ��ǥ : " + "x "
										+ mon.getX() + " y " + mon.getY()
										+ " map " + mon.getMapId());
								reook = true;
								return;
							}
							FireDragonSpawn.getInstance().Spawn(null,
									mon.getMapId(), 2);
							// die(null);
							reook = true;
							setDead(true);
							setActionStatus(ActionCodes.ACTION_Die);

							Death death = new Death(null, mon);
							GeneralThreadPool.getInstance().execute(death);

							dead.setAttacker(null);
							GeneralThreadPool.getInstance().execute(dead);
							return;
						}

					}

					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class �����Ȱ������ implements Runnable {

		private L1MonsterInstance mon;

		public �����Ȱ������(L1MonsterInstance _mon) {
			mon = _mon;
		}

		@Override
		public void run() {
			try {
				
				for (int i = 0; i < 20; ++i) {
					if (mon.getNpcId() == 4036016 || mon.getNpcId() == 4036017) {
						int ckid = 0;
						if (mon.getNpcId() == 4036017)
							ckid = 4036016;
						else if (mon.getNpcId() == 4036016)
							ckid = 4036017;
						if (ckid != 0 && !TKmonCK(ckid)) {
							startDeleteTimer();
							return;
						}
					}
					try {
						Thread.sleep(3000L);
						// Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				mon.set�����Ȱ(true);
				mon.resurrect(mon.getMaxHp() / 2);
				if (mon.getNpcId() == 4036016)
					Broadcaster.broadcastPacket(mon, new S_SkillSound(getId(),
							7275), true);
				else if (mon.getNpcId() == 4036017)
					Broadcaster.broadcastPacket(mon, new S_SkillSound(getId(),
							7274), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * private void die2(L1Character lastAttacker) { setDeathProcessing(true);
	 * setCurrentHp(0); setDead(true); getMap().setPassable(getLocation(),
	 * true); startChat(CHAT_TIMING_DEAD); //
	 * distributeExpDropKarma(lastAttacker); setDeathProcessing(false);
	 * setExp(0); setKarma(0); setLawful(0); allTargetClear(); int
	 * transformGfxId = getNpcTemplate().getTransformGfxId(); if (transformGfxId
	 * > 0) Broadcaster.broadcastPacket(this, new S_SkillSound(getId(),
	 * transformGfxId)); deleteMe();
	 * GeneralThreadPool.getInstance().schedule(new GiranTransTimer(this),
	 * 2000); }
	 */

	// ////////////�������Ÿ///////
	private void dieAntharas(L1Character lastAttacker) {
		setDeathProcessing(true);
		setCurrentHp(0);
		setDead(true);
		getMap().setPassable(getLocation(), true);
		// startChat(CHAT_TIMING_DEAD);
		// distributeExpDropKarma(lastAttacker);
		setDeathProcessing(false);
		setExp(0);
		setKarma(0);
		allTargetClear();
		int transformGfxId = getNpcTemplate().getTransformGfxId();
		if (transformGfxId > 0)
			Broadcaster.broadcastPacket(this, new S_SkillSound(getId(),
					transformGfxId), true);
		setActionStatus(ActionCodes.ACTION_Die);
		Broadcaster.broadcastPacket(this, new S_DoActionGFX(getId(),
				ActionCodes.ACTION_Die), true);
		// deleteMe();
	}

	/*
	 * private static class AntharasTransTimer extends TimerTask { L1NpcInstance
	 * _npc; private AntharasTransTimer(L1NpcInstance some) { _npc = some; }
	 * 
	 * @Override public void run() {
	 * 
	 * } }
	 */
	// /////////�������Ÿ///////
	/*
	 * ������ ����
	 */
	private void diePaPoo(L1Character lastAttacker) {
		setDeathProcessing(true);
		setCurrentHp(0);
		setDead(true);
		getMap().setPassable(getLocation(), true);
		setDeathProcessing(false);
		setExp(0);
		setKarma(0);
		allTargetClear();
		setActionStatus(ActionCodes.ACTION_Die);
		Broadcaster.broadcastPacket(this, new S_DoActionGFX(getId(),
				ActionCodes.ACTION_Die), true);
		deleteMe();
	}

	/*
	 * private static class GiranTransTimer extends TimerTask { L1NpcInstance
	 * _npc;
	 * 
	 * private GiranTransTimer(L1NpcInstance some) { _npc = some; }
	 * 
	 * @Override public void run() { L1SpawnUtil.spawn2(_npc.getX(),
	 * _npc.getY(), (short) _npc.getMap().getId(),
	 * _npc.getNpcTemplate().getTransformId(), 0, 0, 0); } }
	 * 
	 * private boolean isGDMonster() { int id = getNpcTemplate().get_npcId();
	 * if((id >= 4037100 && id <= 4037102) || (id >= 4037200 && id <= 4037202)
	 * || (id >= 4037400 && id <= 4037403)) return true; return false; }
	 */
	// //////�������Ÿ//////
	private boolean isAntharas() {
		int id = getNpcTemplate().get_npcId();
		if (id == 4038000 || id == 4200010)
			return true;
		return false;
	}

	// //////�������Ÿ///////
	// //////��������Ǫ///////
	private boolean isPapoo() {
		int id = getNpcTemplate().get_npcId();
		if (id == 4039000 || id == 4039006)
			return true;
		return false;
	}

	private boolean ismarble() {
		int id = getNpcTemplate().get_npcId();
		if (id == 4039001)
			return true;
		return false;
	}

	private boolean ismarble2() {
		int id = getNpcTemplate().get_npcId();
		if (id == 4039002)
			return true;
		return false;
	}

	private synchronized void distributeExpDropKarma(L1Character lastAttacker) {
		if (lastAttacker == null) {
			return;
		}
		L1PcInstance pc = null;
		if (lastAttacker instanceof L1PcInstance) {
			pc = (L1PcInstance) lastAttacker;
		} else if (lastAttacker instanceof L1PetInstance) {
			pc = (L1PcInstance) ((L1PetInstance) lastAttacker).getMaster();
		} else if (lastAttacker instanceof L1SummonInstance) {
			pc = (L1PcInstance) ((L1SummonInstance) lastAttacker).getMaster();
		}

		
		if (pc != null) {
			ArrayList<L1Character> targetList = _hateList.toTargetArrayList();
			ArrayList<Integer> hateList = _hateList.toHateArrayList();
			int exp = getExp();
			CalcExp.calcExp(pc, getId(), targetList, hateList, exp);
			if (isDead()) {
				distributeDrop(pc);
				giveKarma(pc);
			}
		} else if (lastAttacker instanceof L1EffectInstance) {
			ArrayList<L1Character> targetList = _hateList.toTargetArrayList();
			ArrayList<Integer> hateList = _hateList.toHateArrayList();
			if (hateList.size() != 0) {
				int maxHate = 0;
				for (int i = hateList.size() - 1; i >= 0; i--) {
					if (maxHate < ((Integer) hateList.get(i))) {
						maxHate = (hateList.get(i));
						lastAttacker = targetList.get(i);
					}
				}
				if (lastAttacker instanceof L1PcInstance) {
					pc = (L1PcInstance) lastAttacker;
				} else if (lastAttacker instanceof L1PetInstance) {
					pc = (L1PcInstance) ((L1PetInstance) lastAttacker)
							.getMaster();
				} else if (lastAttacker instanceof L1SummonInstance) {
					pc = (L1PcInstance) ((L1SummonInstance) lastAttacker)
							.getMaster();
				}
				int exp = getExp();
				CalcExp.calcExp(pc, getId(), targetList, hateList, exp);
				if (isDead()) {
					distributeDrop(pc);
					giveKarma(pc);
				}
			}
		}
	}

	private static Logger _log = Logger.getLogger(L1MonsterInstance.class
			.getName());
	boolean rare_item_drop = false;

	private void distributeDrop(L1PcInstance pc) {
		int npcId = getNpcTemplate().get_npcId();
		try {
			if (isResurrect()) {
				return;
			}
			if (npcId == 4200011 || npcId == 4039007 || npcId == 100014
					|| npcId == 400016 || npcId == 400017 || npcId == 4036016
					|| npcId == 4036017) {
				int ckid = 0;
				if (npcId == 400016)
					ckid = 400017;
				else if (npcId == 400017)
					ckid = 400016;
				else if (npcId == 4036017)
					ckid = 4036016;
				else if (npcId == 4036016)
					ckid = 4036017;
				if (ckid != 0 && TKmonCK(ckid))
					return;
				int time = 1;
				if (npcId == 4200011 || npcId == 4039007 || npcId == 100014)
					time = 10000;
				if (rare_item_drop)
					return;
				rare_item_drop = true;
				GeneralThreadPool.getInstance().schedule(new dragondistri(pc),
						time);

			} else if (npcId == 100155) { // �ٴ� ����
				if (!isResurrect() && pc.isInParty()) {
					for (L1PcInstance partymember : pc.getParty().getMembers()) {
						if (pc.getMapId() != partymember.getMapId())
							continue;
						L1ItemInstance item = partymember.getInventory()
								.storeItem(60085, 1);
						partymember.sendPackets(new S_ServerMessage(143,
								getName(), item.getName())); // \f1%0��%1�� �־����ϴ�.
					}
				}
			} else if (npcId == 100156) { // �ٴ� �巹��ũ
				if (!isResurrect() && pc.isInParty()) {
					for (L1PcInstance partymember : pc.getParty().getMembers()) {
						if (pc.getMapId() != partymember.getMapId())
							continue;
						L1ItemInstance item = partymember.getInventory()
								.storeItem(60086, 1);
						partymember.sendPackets(new S_ServerMessage(143,
								getName(), item.getName())); // \f1%0��%1�� �־����ϴ�.
					}
				}
			} else if (npcId == 100210) { // ���� �̹�
				if (!isResurrect() && pc.isInParty()) {
					for (L1PcInstance partymember : pc.getParty().getMembers()) {
						if (pc.getMapId() != partymember.getMapId())
							continue;
						L1ItemInstance item = partymember.getInventory()
								.storeItem(60087 + �ػ�������̹ʹܰ�, 1);
						partymember.sendPackets(new S_ServerMessage(143,
								getName(), item.getName())); // \f1%0��%1�� �־����ϴ�.
					}
				}
			} else if (npcId == 100157) { // ��� ��¡��
				if (!isResurrect() && pc.isInParty()) {
					for (L1PcInstance partymember : pc.getParty().getMembers()) {
						if (pc.getMapId() != partymember.getMapId())
							continue;
						L1ItemInstance item = partymember.getInventory()
								.storeItem(500022, 1);
						partymember.sendPackets(new S_ServerMessage(143,
								getName(), item.getName())); // \f1%0��%1�� �־����ϴ�.
					}
				}
			} else if (npcId >= 100653 && npcId <= 100655) {// �������� �δ���
				if (isResurrect())
					return;
				for (L1PcInstance tempPc : L1World.getInstance()
						.getVisiblePlayer(this, -1)) {
					if (tempPc == null)
						continue;
					L1ItemInstance item = tempPc.getInventory().storeItem(
							60393 + (npcId - 100653), 1);
					tempPc.sendPackets(new S_ServerMessage(143, getName(), item
							.getName())); // \f1%0��%1�� �־����ϴ�.
				}
			} else if (npcId != 45640
					|| (npcId == 45640 && getGfxId().getTempCharGfx() == 2332)) {

				if (npcId == 100338 || npcId == 100420) { // �����ں�, �����
					L1Character[] m3list = _dropHateList.getMaxHate3Character();
					if (m3list != null) {
						L1World.getInstance().broadcastPacketToAll(
								new S_ServerMessage(3320, getName(),
										(m3list[0] != null ? m3list[0]
												.getName() : "����"),
										(m3list[1] != null ? m3list[1]
												.getName() : "����"),
										(m3list[2] != null ? m3list[2]
												.getName() : "����")));
						m3list = null;
					}
				}
				ArrayList<L1Character> dropTargetList = _dropHateList
						.toTargetArrayList();
				ArrayList<Integer> dropHateList = _dropHateList
						.toHateArrayList();
				try {
					DropTable.getInstance().dropShare(L1MonsterInstance.this,
							dropTargetList, dropHateList, pc);
				} catch (Exception e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				}
			}
		} catch (Exception e) {
		}
	}

	class dragondistri implements Runnable {

		private L1PcInstance pc;

		public dragondistri(L1PcInstance _pc) {
			pc = _pc;
		}

		@Override
		public void run() {
			
			try {
				Collection<L1Object> DragonDropTargetList = L1World
						.getInstance().getVisibleObjects(getMapId()).values();
				ArrayList<L1PcInstance> list = new ArrayList<L1PcInstance>();
				L1HateList DragonHate = new L1HateList();
				int ran = 0;
				for (L1Object DUser : DragonDropTargetList) {
					if (DUser instanceof L1PcInstance) {
						ran = _random.nextInt(1000);
						DragonHate.add((L1PcInstance) DUser, ran);
						list.add((L1PcInstance) DUser);
					}
				}
				DragonRaidItemTable.get().insertItem(L1MonsterInstance.this,
						list.size());

				ArrayList<L1Character> dropTargetList = DragonHate
						.toTargetArrayList();
				ArrayList<Integer> dropHateList = DragonHate.toHateArrayList();
				try {
					DropTable.getInstance().dropShare(L1MonsterInstance.this,
							dropTargetList, dropHateList, pc);
				} catch (Exception e) {
					_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				}

				// DropTable.getInstance().dropShare(L1MonsterInstance.this,
				// DragonHate, pc);
			} catch (Exception e) {
			}
		}

	}

	private boolean TKmonCK(int id) {
		for (L1Object obj : L1World.getInstance().getVisibleObjects(getMapId())
				.values()) {
			if (obj != null && obj instanceof L1MonsterInstance) {
				L1MonsterInstance mon = (L1MonsterInstance) obj;
				if (mon.getNpcTemplate().get_npcId() == id && !mon.isDead()
						&& !mon._destroyed)
					return true;
			}
		}
		/*
		 * for(L1Object obj : L1World.getInstance().getObject()){ if(obj!= null
		 * && obj instanceof L1MonsterInstance){ L1MonsterInstance mon =
		 * (L1MonsterInstance)obj; if(mon.getNpcTemplate().get_npcId() == id &&
		 * !mon.isDead() && !mon._destroyed) return true; } }
		 */
		return false;
	}

	private void giveKarma(L1PcInstance pc) {
		int karma = getKarma();
		if (karma != 0) {
			int karmaSign = Integer.signum(karma);
			int pcKarmaLevel = pc.getKarmaLevel();
			int pcKarmaLevelSign = Integer.signum(pcKarmaLevel);
			if (pcKarmaLevelSign != 0 && karmaSign != pcKarmaLevelSign) {
				karma *= 5;
			}
			pc.addKarma((int) (karma * Config.RATE_KARMA));
		}
	}

	private void giveUbSeal() {
		if (getUbSealCount() != 0) {
			L1UltimateBattle ub = UBTable.getInstance().getUb(getUbId());
			if (ub != null) {
				for (L1PcInstance pc : ub.getMembersArray()) {
					if (pc != null && !pc.isDead() && !pc.isGhost()) {
						if (_random.nextInt(10) <= 2) {
							pc.getInventory().storeItem(
									L1ItemId.UB_WINNER_PIECE, 1);
						}
						pc.getInventory().storeItem(41402, getUbSealCount());
						pc.sendPackets(new S_ServerMessage(403, "$5448"), true);
					}
				}
			}
		}
	}

	public byte get_storeDroped() {
		return _storeDroped;
	}

	public void set_storeDroped(byte i) {
		_storeDroped = i;
	}

	private int _ubSealCount = 0;

	public int getUbSealCount() {
		return _ubSealCount;
	}

	public void setUbSealCount(int i) {
		_ubSealCount = i;
	}

	private int _ubId = 0; // UBID

	public int getUbId() {
		return _ubId;
	}

	public void setUbId(int i) {
		_ubId = i;
	}

	private void hide() {
		int npcid = getNpcTemplate().get_npcId();
		if (npcid == 45061 || npcid == 45161 || npcid == 45181
				|| npcid == 45455) {
			if (getMaxHp() / 3 > getCurrentHp()) {
				int rnd = _random.nextInt(10);
				if (1 > rnd) {
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_SINK);
					Broadcaster.broadcastPacket(this, new S_DoActionGFX(
							getId(), ActionCodes.ACTION_Hide), true);
					setActionStatus(13);
					Broadcaster
							.broadcastPacket(this, new S_NPCPack(this), true);
				}
			}
		} else if (npcid == 45682) {
			if (getMaxHp() / 3 > getCurrentHp()) {
				int rnd = _random.nextInt(50);
				if (1 > rnd) {
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_SINK);
					Broadcaster.broadcastPacket(this, new S_DoActionGFX(
							getId(), ActionCodes.ACTION_AntharasHide), true);
					setActionStatus(20);
					Broadcaster
							.broadcastPacket(this, new S_NPCPack(this), true);
				}
			}
		} else if (npcid == 45067 || npcid == 45264 || npcid == 45452
				|| npcid == 45090 || npcid == 45321 || npcid == 45445
				|| npcid == 100352) {
			if (getMaxHp() / 3 > getCurrentHp()) {
				int rnd = _random.nextInt(10);
				if (1 > rnd) {
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_FLY);
					Broadcaster.broadcastPacket(this, new S_DoActionGFX(
							getId(), ActionCodes.ACTION_Moveup), true);
					setActionStatus(4);
					Broadcaster
							.broadcastPacket(this, new S_NPCPack(this), true);
				}
			}
		} else if (npcid == 45681) {
			if (getMaxHp() / 3 > getCurrentHp()) {
				int rnd = _random.nextInt(50);
				if (1 > rnd) {
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_FLY);
					Broadcaster.broadcastPacket(this, new S_DoActionGFX(
							getId(), ActionCodes.ACTION_Moveup), true);
					setActionStatus(11);
					Broadcaster
							.broadcastPacket(this, new S_NPCPack(this), true);
				}
			}
		} else if (npcid == 100014) {// ���� �����
			if (getMapId() >= 10000 && getMapId() <= 10005) {
				if ((getMaxHp() / 2) > getCurrentHp() && !lind_fly) {
					lind_fly = true;
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_FLY);
					Broadcaster.broadcastPacket(this, new S_DoActionGFX(
							getId(), 13), true);
					setActionStatus(13);
					Broadcaster
							.broadcastPacket(this, new S_NPCPack(this), true);
				} else if ((getMaxHp() / 4) > getCurrentHp() && !lind_fly2) {
					lind_fly2 = true;
					Lind lind = LindThread.get().getLind(getMapId());
					lind.Step = 5;
					lind.Sub_Step = 0;
					allTargetClear();
					setHiddenStatus(HIDDEN_STATUS_FLY);
					Broadcaster.broadcastPacket(this, new S_DoActionGFX(
							getId(), 13), true);
					setActionStatus(13);
					Broadcaster
							.broadcastPacket(this, new S_NPCPack(this), true);
				}
			}
		} else if (npcid == 100420) { // �����
			if ((getMaxHp() / 2) > getCurrentHp() && !�����_����) {
				�����_���� = true;
				allTargetClear();
				setHiddenStatus(HIDDEN_STATUS_SINK);
				Broadcaster.broadcastPacket(this, new S_DoActionGFX(getId(),
						ActionCodes.ACTION_Hide), true);
				setActionStatus(13);
				Broadcaster.broadcastPacket(this, new S_NPCPack(this), true);
				GeneralThreadPool.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						
						if (getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_NONE)
							return;
						randomWalk();
						GeneralThreadPool.getInstance().schedule(this,
								getSleepTime());
					}
				}, 500);
				int rnd = _random.nextInt(3) + 2;
				for (int i = 0; i < rnd; i++) {
					L1SpawnUtil.spawn3(this, 100421, 6, 600 * 1000, false);
				}
			}
		}
	}

	public void initHide() {
		int npcid = getNpcTemplate().get_npcId();
		if (npcid == 45061 || npcid == 45161 || npcid == 45181
				|| npcid == 45455 || npcid == 400000 || npcid == 400001) {
			int rnd = _random.nextInt(3);
			if (1 > rnd) {
				setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_SINK);
				setActionStatus(13);
			}
		} else if (npcid == 45045 || npcid == 45126 || npcid == 45134
				|| npcid == 45281) {
			int rnd = _random.nextInt(3);
			if (1 > rnd) {
				setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_SINK);
				setActionStatus(4);
			}
		} else if (npcid == 100072 || npcid == 100627) {
			int rnd = _random.nextInt(3);
			if (1 > rnd) {
				setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_SINK);
				setActionStatus(11);
			}
		} else if (npcid == 45067 || npcid == 45264 || npcid == 45452
				|| npcid == 45090 || npcid == 45321 || npcid == 45445
				|| npcid == 100352) {
			setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_FLY);
			setActionStatus(4);
		} else if (npcid == 45681) {
			setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_FLY);
			setActionStatus(11);
		}
	}

	public void initHideForMinion(L1NpcInstance leader) {
		int npcid = getNpcTemplate().get_npcId();
		if (leader.getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_SINK) {
			if (npcid == 45061 || npcid == 45161 || npcid == 45181
					|| npcid == 45455 || npcid == 400000 || npcid == 400001) {
				setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_SINK);
				setActionStatus(13);
			} else if (npcid == 45045 || npcid == 45126 || npcid == 45134
					|| npcid == 45281 || npcid == 100072 || npcid == 100627) {
				setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_SINK);
				setActionStatus(4);
			}
		} else if (leader.getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_FLY) {
			if (npcid == 45067 || npcid == 45264 || npcid == 45452
					|| npcid == 45090 || npcid == 45321 || npcid == 45445
					|| npcid == 100352) {
				setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_FLY);
				setActionStatus(4);
			} else if (npcid == 45681) {
				setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_FLY);
				setActionStatus(11);
			}
		}
	}

	@Override
	protected void transform(int transformId) {
		super.transform(transformId);
		getInventory().clearItems();
		DropTable.getInstance().setDrop(this, getInventory());
		getInventory().shuffle();
		transok = false;
	}

	private int shellCloseCount = 0;

	private boolean shellman() {
		boolean miss = false;
		if (getNpcTemplate().get_npcId() == 100003
				|| getNpcTemplate().get_npcId() == 100814) {
			if (shellManClose) {
				miss = true;
			} else {
				if (shellCloseCount < 2 && _random.nextInt(100) < 2) {
					shellCloseCount++;
					shellManClose = true;
					Broadcaster.broadcastPacket(this, new S_NpcChatPacket(this,
							"õ�� �ΰ� ������ ���⺸�� �걸��!", 0), true);
					Broadcaster.broadcastPacket(this, new S_DoActionGFX(
							getId(), 4), true);
					setActionStatus(4);
					Broadcaster.broadcastPacket(this, new S_CharVisualUpdate(
							this), true);

					miss = true;
				}
				int percent = (int) Math.round((double) getCurrentHp()
						/ (double) getMaxHp() * 100);
				if (!shellHydra) {
					/** ����� ��ȯ **/
					if (percent < 50) {
						shellHydra = true;
						L1SpawnUtil.spawn2(getX() - 5 + _random.nextInt(11),
								getY() - 5 + _random.nextInt(11),
								(short) getMap().getId(), 100004, 0, 0, 0);
						L1SpawnUtil.spawn2(getX() - 5 + _random.nextInt(11),
								getY() - 5 + _random.nextInt(11),
								(short) getMap().getId(), 100004, 0, 0, 0);
						L1SpawnUtil.spawn2(getX() - 5 + _random.nextInt(11),
								getY() - 5 + _random.nextInt(11),
								(short) getMap().getId(), 100004, 0, 0, 0);
					}
				} else {
					if (percent > 90) {
						shellHydra = false;
					}
				}
			}
		}
		return miss;
	}

	private static final short[] list = { 0, 4, 7, 8, 9, 10, 11, 12, 13, 25,
			26, 27, 28, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111,
			112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124,
			125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137,
			138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150,
			151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163,
			164, 165, 166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176,
			177, 178, 179, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189,
			190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 307, 308,
			309, 70, 303, 67, 535 };

	class �����ں�ȸ����Timer implements Runnable {
		private L1MonsterInstance _effect;

		public �����ں�ȸ����Timer(L1MonsterInstance effect) {
			_effect = effect;
		}

		@Override
		public void run() {
			if (_destroyed)
				return;
			try {
				for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(
						_effect, 1)) {
					if (pc.isDead() || pc.isGhost()) {
						continue;
					}
					L1Location loc = new L1Location();
					if (_effect.getNpcId() == 100422) {// �����ں� ��ȯ ȸ����
						L1Map map = L1WorldMap.getInstance().getMap(
								list[_random.nextInt(list.length)]);
						loc.set(map.getX(), map.getY(), map.getId());
						loc = L1Location.randomRangeLocation(loc,
								map.getWidth(), map.getHeight(), true);
					} else {
						loc.set(32707, 33121, 4);
						loc = L1Location.randomRangeLocation(loc, 190, 583,
								false);
					}
					L1Teleport.teleport(pc, loc.getX(), loc.getY(),
							(short) loc.getMapId(), 6, true);
				}
				GeneralThreadPool.getInstance().schedule(this, 500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class ����Timer implements Runnable {
		public ����Timer() {
		}

		@Override
		public void run() {
			try {
				Thread.sleep(3000);
				STATUS_Escape = false;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class ���̳�Ʈ��Timer implements Runnable {
		private L1MonsterInstance _effect;

		public ���̳�Ʈ��Timer(L1MonsterInstance effect) {
			_effect = effect;
		}

		@Override
		public void run() {
			try {
				if (_destroyed)
					return;
				for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(
						_effect, 1)) {
					if (pc.isTeleport() || pc.isDead() || pc.isGhost()) {
						continue;
					}
					L1Location loc = new L1Location();
					loc.set(33408, 33145, 4);
					loc = L1Location.randomRangeLocation(loc, 126, 174, false);
					L1Teleport.teleport(pc, loc.getX(), loc.getY(),
							(short) loc.getMapId(), 6, true);

					L1NpcDeleteTimer timer = new L1NpcDeleteTimer(_effect, 1000);
					timer.begin();

					loc.set(33408, 33145, 4);
					loc = L1Location.randomRangeLocation(loc, 126, 174, false);

					L1SpawnUtil.spawn2(loc.getX(), loc.getY(), (short) 4,
							100859, 1, 0, 0);
				}
				GeneralThreadPool.getInstance().schedule(this, 500);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	class �⸣�� implements Runnable {
		@Override
		public void run() {
			if (_destroyed || isDead())
				return;
			try {
				for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(
						L1MonsterInstance.this, 1)) {
					if (pc.isDead() || pc.isGhost()) {
						continue;
					}
					Broadcaster.broadcastPacket(L1MonsterInstance.this,
							new S_DoActionGFX(pc.getId(),
									ActionCodes.ACTION_Damage), true);
					try {
						pc.receiveDamage(L1MonsterInstance.this,
								_random.nextInt(50) + 10, true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				GeneralThreadPool.getInstance().schedule(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
