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

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1World;
import l1j.server.server.model.poison.L1DamagePoison;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_DollPack;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_HPUpdate;
import l1j.server.server.serverpackets.S_MPUpdate;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.types.Point;

public class L1DollInstance extends L1NpcInstance {
	private static final long serialVersionUID = 1L;

	public static final int DOLLTYPE_BUGBEAR = 0;
	public static final int DOLLTYPE_SUCCUBUS = 1;
	public static final int DOLLTYPE_WAREWOLF = 2;
	public static final int DOLLTYPE_STONEGOLEM = 3;
	public static final int DOLLTYPE_ELDER = 4;
	public static final int DOLLTYPE_CRUSTACEA = 5;
	public static final int DOLLTYPE_SEADANCER = 6;
	public static final int DOLLTYPE_SNOWMAN = 7;
	public static final int DOLLTYPE_COCATRIS = 8;
	public static final int DOLLTYPE_DRAGON_M = 9;
	public static final int DOLLTYPE_DRAGON_W = 10;
	public static final int DOLLTYPE_HIGH_DRAGON_M = 11;
	public static final int DOLLTYPE_HIGH_DRAGON_W = 12;
	public static final int DOLLTYPE_LAMIA = 13;
	public static final int DOLLTYPE_ETIN = 17;
	public static final int DOLLTYPE_HELPER = 20;
	public static final int DOLLTYPE_SPATOI = 21;
	public static final int DOLLTYPE_HUSUABI = 22;
	public static final int DOLLTYPE_SNOWMAN_A = 23; // �����(A)
	public static final int DOLLTYPE_SNOWMAN_B = 24; // �����(B)
	public static final int DOLLTYPE_SNOWMAN_C = 25; // �����(C)
	public static final int DOLLTYPE_��ƾ = 26; // ��ƾ
	public static final int DOLLTYPE_RICH = 27; // ��ġ
	public static final int DOLLTYPE_���� = 28;
	public static final int DOLLTYPE_������ = 29;
	public static final int DOLLTYPE_������ = 30;
	public static final int DOLLTYPE_�׷��� = 31;
	public static final int DOLLTYPE_���� = 56;
	public static final int DOLLTYPE_�ܵ� = 57;
	public static final int DOLLTYPE_�긮 = 58;
	public static final int DOLLTYPE_�巹��ũ = 59;
	public static final int DOLLTYPE_����_���� = 60;
	public static final int DOLLTYPE_�׷��� = 61;
	public static final int DOLLTYPE_HW_HUSUABI = 62;
	public static final int DOLLTYPE_ưư�ѱ�� = 63;
	public static final int DOLLTYPE_����Ǳ�� = 79;
	public static final int DOLLTYPE_���̸��� = 80;
	public static final int DOLLTYPE_�����̾� = 81;
	public static final int DOLLTYPE_�ٶ�ī = 82;

	public static final int DOLLTYPE_����Ŭ�ӽ� = 64;
	public static final int DOLLTYPE_���̾�Ʈ = 65;
	public static final int DOLLTYPE_����� = 66;
	public static final int DOLLTYPE_��ť�� = 67;

	public static final int DOLLTYPE_�ξ� = 68;
	public static final int DOLLTYPE_ŷ_���׺��� = 69;
	public static final int DOLLTYPE_����� = 70;
	public static final int DOLLTYPE_�̺�Ʈ���� = 71;

	public static final int DOLLTYPE_�� = 72;
	public static final int DOLLTYPE_��ٰ� = 73;
	public static final int DOLLTYPE_���̾Ƹ��� = 74;
	public static final int DOLLTYPE_�þ� = 75;
	public static final int DOLLTYPE_����Ʈ�ߵ� = 76;
	public static final int DOLLTYPE_���� = 77;
	public static final int DOLLTYPE_��������Ʈ = 78;
	public static final int DOLLTYPE_�ӹ̷ε� = 3000089;
	public static final int DOLLTYPE_Ÿ�� = 3000090;

	// public static final int DOLL_TIME = 1800000;

	private static Random _random = new Random(System.nanoTime());
	private int _dollType;
	private int _itemObjId;
	private ScheduledFuture<?> _future = null;

	private static int Buff[] = { 26, 42, 43, 79 }; // ����, ��, ����, �

	// Ÿ���� ���� ����� ó��
	@Override
	public boolean noTarget() {
		if (_master != null
				&& (_master.isDead() || (!(_master instanceof L1RobotInstance) && ((L1PcInstance) _master)
						.getNetConnection() == null))) {
			deleteDoll();
			return true;
		} else if (_master != null && _master.getMapId() == getMapId()) {
			if (getLocation().getTileLineDistance(_master.getLocation()) > 2) {
				int dir = moveDirection(_master.getMapId(), _master.getX(),
						_master.getY());
				if (dir == -1) {
					teleport(_master.getX(), _master.getY(), getMoveState()
							.getHeading());
				} else {
					setDirectionMove(dir);
					setSleepTime(calcSleepTime(getPassispeed(), MOVE_SPEED));
				}
			}
			return false;
		}
		if (_master == null) {
			deleteDoll();
			return true;
		}

		if (_master.getMapId() != getMapId()) {
			teleport(_master.getX(), _master.getY(), getMoveState()
					.getHeading(), _master.getMapId());
			return false;
		}

		return false;
	}

	// �ð� ������
	class DollTimer implements Runnable {
		@Override
		public void run() {
			try {

				if (_destroyed) { // �̹� �ı�Ǿ� ���� ������ üũ
					return;
				}
				deleteDoll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class HelpTimer implements Runnable {
		@Override
		public void run() {
			try {

				if (_destroyed) { // �̹� �ı�Ǿ� ���� ������ üũ
					return;
				}
				getHelperAction();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class RemaningTimer implements Runnable {
		@Override
		public void run() {
			try {
				if (_destroyed || getMaster() == null) { // �̹� �ı�Ǿ� ���� ������ üũ
					return;
				}
				L1ItemInstance _item = getMaster().getInventory().getItem(
						getItemObjId());
				if (_item == null) {
					deleteDoll();
					return;
				}
				L1PcInstance pc = (L1PcInstance) getMaster();
				_item.getLastStatus().updateRemainingTime();
				if ((_item.getRemainingTime() - 1) > 0) {
					if (pc.getOnlineStatus() == 0) {
						return;
					}
					_item.setRemainingTime(_item.getRemainingTime() - 1);
				} else {
					deleteDoll();
					pc.getInventory().removeItem(_item, 1);
				}
				GeneralThreadPool.getInstance().schedule(this, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public L1DollInstance(L1Npc template, L1PcInstance master, int dollType,
			int itemObjId, int dollTime) {
		super(template);
		setId(ObjectIdFactory.getInstance().nextId());

		setDollType(dollType);
		setItemObjId(itemObjId);
		GeneralThreadPool.getInstance().schedule(new DollTimer(), dollTime);

		setMaster(master);
		setX(master.getX() + _random.nextInt(5) - 2);
		setY(master.getY() + _random.nextInt(5) - 2);
		setMap(master.getMapId());
		getMoveState().setHeading(5);
		setLightSize(template.getLightSize());

		L1World.getInstance().storeObject(this);
		L1World.getInstance().addVisibleObject(this);
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			onPerceive(pc);
		}
		master.addDoll(this);
		if (!isAiRunning()) {
			startAI();
		}
		if (isMpRegeneration()) {
			master.startMpRegenerationByDoll();
		}
		if (isHpRegeneration()) {
			master.startHpRegenerationByDoll();
		}

		int type = getDollType();
		if (type == DOLLTYPE_SNOWMAN) {
			master.getAC().addAc(-3);
			_master.getResistance().addFreeze(7);
		}
		if (type == DOLLTYPE_ETIN) {
			_master.getAC().addAc(-2);
			_master.getResistance().addHold(10);
			_master.removeHasteSkillEffect();
			if (_master.getMoveState().getMoveSpeed() != 1) {
				_master.getMoveState().setMoveSpeed(1);
				S_SkillHaste sh = new S_SkillHaste(_master.getId(), 1, -1);
				_master.sendPackets(sh, true);
				S_SkillHaste sh2 = new S_SkillHaste(_master.getId(), 1, 0);
				Broadcaster.broadcastPacket(_master, sh2, true);
			}
			_master.set��ƾ(true);
		}

		if (type == DOLLTYPE_SNOWMAN_A) {
			_master.addBowHitupByDoll(5);
		}
		if (type == DOLLTYPE_COCATRIS) {
			_master.addBowHitupByDoll(1);
			_master.addBowDmgupByDoll(1);
			// _master.addMpr(10);
		}
		if (type == DOLLTYPE_DRAGON_M || type == DOLLTYPE_DRAGON_W
				|| type == DOLLTYPE_HIGH_DRAGON_M
				|| type == DOLLTYPE_HIGH_DRAGON_W) {
			_master.addMpr(4);
		}
		if (type == DOLLTYPE_HUSUABI) {
			_master.addBowHitupByDoll(2);
			_master.addHitup(2);
			_master.addMaxHp(50);
			_master.addMaxMp(30);
		}
		

		/*
		 * ���ϳ��� + 10 Ȧ�峻�� + 10 ��ƽ ��ƽ + 10 �����Ŀ� + 1 �߰������� + 1 ���� + 1
		 */
		if (type == DOLLTYPE_HW_HUSUABI) {
			_master.getResistance().addStun(10);
			_master.getResistance().addHold(10);
			_master.getAbility().addSp(1);
			_master.addHpr(10);
			_master.addMpr(10);
			_master.addDmgup(1);
			_master.addHitup(1);
			_master.sendPackets(new S_SPMR(_master));
		}
		// ���� 1 ��Ÿ 1 ���� 1 �� 50 �� 30 ���ϳ��� + 10
		if (type == DOLLTYPE_ưư�ѱ��) {
			_master.addDmgup(1);
			_master.addHitup(1);
			_master.addBowDmgup(1);
			_master.addBowHitup(1);
			_master.getAbility().addSp(1);
			_master.addMaxHp(50);
			_master.addMaxMp(30);
			_master.getResistance().addStun(10);
			_master.sendPackets(new S_SPMR(_master));
		}

		if (type == DOLLTYPE_LAMIA) {
			_master.addMpr(4);
			// _master.getResistance().addStun(5);

		}

		if (type == DOLLTYPE_BUGBEAR) {
			_master.sendPackets(new S_NewCreateItem("����", _master));
		}

		if (type == DOLLTYPE_SPATOI) {
			_master.getResistance().addStun(10);
			_master.addDmgup(2);
		}
		// if (type == DOLLTYPE_ELDER) {
		// _master.addMpr(18);
		// }
		if (type == DOLLTYPE_��ƾ) {
			_master.getAC().addAc(-2);
			_master.getResistance().addHold(10);
			_master.removeHasteSkillEffect();
			if (_master.getMoveState().getMoveSpeed() != 1) {
				_master.getMoveState().setMoveSpeed(1);
				S_SkillHaste sh = new S_SkillHaste(_master.getId(), 1, -1);
				_master.sendPackets(sh, true);
				S_SkillHaste sh2 = new S_SkillHaste(_master.getId(), 1, 0);
				Broadcaster.broadcastPacket(_master, sh2, true);
			}
			_master.set��ƾ(true);
		}

		if (type == DOLLTYPE_RICH) {
			_master.getAbility().addSp(2);
			_master.addMaxHp(80);

			_master.getResistance().addStun(5);

			_master.sendPackets(new S_SPMR(_master), true);
		}

		if (type == DOLLTYPE_�׷���) {
			_master.addDmgup(1);
			_master.addHitup(1);
			_master.addBowDmgup(1);
			_master.addBowHitup(1);
			_master.getAbility().addSp(1);
			_master.addMaxHp(30);

			_master.sendPackets(new S_SPMR(_master), true);
			_master.sendPackets(new S_HPUpdate(_master), true);
		}
		
		if (type == DOLLTYPE_��������Ʈ) {
			_master.addBowHitupByDoll(3);
			_master.addBowDmgupByDoll(3);
			_master.addMaxMp(100);
		}

		if (type >= DOLLTYPE_���� && type <= DOLLTYPE_�׷���) {
			_master.getAbility().addSp(1);
			_master.addDmgup(2);
			_master.sendPackets(new S_SPMR(_master), true);
		}

		if (type == DOLLTYPE_����) {
			if (getNpcId() == 100431) { // ���� ��Ÿ��
				_master.getAbility().addSp(1);
				_master.addMaxHp(30);
				_master.sendPackets(new S_SPMR(_master), true);
			} else if (getNpcId() == 100432) { // ��
				_master.addBowDmgupByDoll(2);
				_master.addMaxHp(30);
			} else if (getNpcId() == 100433) { // è�Ǿ�
				_master.addDmgup(2);
				_master.addMaxHp(30);
			}
		}
		if (type == DOLLTYPE_�ܵ� || type == DOLLTYPE_�긮) {
			_master.addMaxHp(50);
			_master.addMaxMp(30);
		}
		if (type == DOLLTYPE_�巹��ũ) {
			_master.addBowDmgupByDoll(2);
		}
		if (type == DOLLTYPE_����_����) {
			_master.addMaxHp(100);
		}
		if (type == DOLLTYPE_����Ŭ�ӽ�) {
			_master.addDmgup(2);
			_master.addHitup(2);
			_master.getResistance().addStun(12);
		}
		if (type == DOLLTYPE_�����) {
			_master.addDmgup(1);
			_master.addHitup(1);
		}

		if (type == DOLLTYPE_�̺�Ʈ����) {
			_master.addDmgup(1);
			_master.addHitup(1);
			_master.addBowHitupByDoll(1);
			_master.addBowDmgupByDoll(1);
			_master.getAbility().addSp(1);
			_master.addMaxHp(100);
			_master.addMaxMp(50);
			_master.sendPackets(new S_SPMR(_master));
			_master.sendPackets(new S_HPUpdate(_master), true);
			_master.sendPackets(
					new S_MPUpdate(_master.getCurrentMp(), _master.getMaxMp()),
					true);
		}
		if (type == DOLLTYPE_��ť��) {
			_master.getAbility().addSp(1);
			_master.sendPackets(new S_SPMR(_master));
		}
		if (type == DOLLTYPE_ŷ_���׺���) {// ŷ����
			_master.getResistance().addStun(8);
		}

		if (type == DOLLTYPE_��) {
			_master.addMaxHp(50);
			_master.sendPackets(new S_HPUpdate(_master), true);
		}
		if (type == DOLLTYPE_��ٰ�) {
			_master.addDmgup(1);
		}
		if (type == DOLLTYPE_����Ʈ�ߵ�) {
			_master.addDmgup(2);
			_master.addHitup(2);
		}
		if (type == DOLLTYPE_�����̾�) {
			_master.addDmgup(2);
			_master.addHitup(2);
		}
		if (type == DOLLTYPE_�þ�) {
			_master.addBowDmgup(5);
		}
		if (type == DOLLTYPE_����) {
			_master.getResistance().addStun(12);
			_master.addMaxHp(300);
			_master.addDmgup(4);
		}
		if (type == DOLLTYPE_�ٶ�ī) {
			_master.getResistance().addStun(12);
			_master.addMaxHp(100);
			_master.addDmgup(5);
			_master.addHitup(5);
		}
		if (type == DOLLTYPE_Ÿ��) {
			_master.getResistance().addStun(10);
			_master.addHitup(5);
			_master.getAbility().addSp(3);
			_master.sendPackets(new S_SPMR(_master));
		}
		
		startHelpTimer();
		L1ItemInstance _item = getMaster().getInventory().getItem(
				getItemObjId());
		if (_item != null && _item.getRemainingTime() > 0)
			GeneralThreadPool.getInstance().schedule(new RemaningTimer(), 1000);
	}

	public void deleteDoll() {
		try {
			if (isMpRegeneration()) {
				((L1PcInstance) _master).stopMpRegenerationByDoll();
			} else if (isHpRegeneration()) {
				((L1PcInstance) _master).stopHpRegenerationByDoll();
			}
			int type = getDollType();
			if (type == DOLLTYPE_SNOWMAN) {
				_master.getAC().addAc(3);
				_master.getResistance().addFreeze(-7);
			}

			if (type == DOLLTYPE_ETIN) {
				_master.getAC().addAc(2);
				_master.getResistance().addHold(-10);
				_master.removeHasteSkillEffect();
				_master.getMoveState().setMoveSpeed(0);
				S_SkillHaste sh = new S_SkillHaste(_master.getId(), 0, 0);
				_master.sendPackets(sh);
				Broadcaster.broadcastPacket(_master, sh, true);
				_master.set��ƾ(false);
			}

			if (type == DOLLTYPE_SNOWMAN_A) {
				_master.addBowHitupByDoll(-5);
			}

			if (type == DOLLTYPE_COCATRIS) {
				_master.addBowHitupByDoll(-1);
				_master.addBowDmgupByDoll(-1);
				// _master.addMpr(-10);
			}
			if (type == DOLLTYPE_HUSUABI) {
				_master.addBowHitupByDoll(-2);
				_master.addHitup(-2);
				_master.addMaxHp(-50);
				_master.addMaxMp(-30);

			}
			
			if (type == DOLLTYPE_��������Ʈ) {
				_master.addMaxMp(-100);
				_master.addBowHitupByDoll(-3);
				_master.addBowDmgupByDoll(-3);
			}
		

			if (type == DOLLTYPE_BUGBEAR) {
				_master.sendPackets(new S_NewCreateItem("����", _master));
			}

			if (type == DOLLTYPE_HW_HUSUABI) {
				_master.getResistance().addStun(-10);
				_master.getResistance().addHold(-10);
				_master.getAbility().addSp(-1);
				_master.addHpr(-10);
				_master.addMpr(-10);
				_master.addDmgup(-1);
				_master.addHitup(-1);
				_master.sendPackets(new S_SPMR(_master));
			}
			if (type == DOLLTYPE_ưư�ѱ��) {
				_master.addDmgup(-1);
				_master.addHitup(-1);
				_master.addBowDmgup(-1);
				_master.addBowHitup(-1);
				_master.getAbility().addSp(-1);
				_master.addMaxHp(-50);
				_master.addMaxMp(-30);
				_master.getResistance().addStun(-10);
				_master.sendPackets(new S_SPMR(_master));
			}
			if (type == DOLLTYPE_DRAGON_M || type == DOLLTYPE_DRAGON_W
					|| type == DOLLTYPE_HIGH_DRAGON_M
					|| type == DOLLTYPE_HIGH_DRAGON_W) {
				_master.addMpr(-4);
			}
			if (type == DOLLTYPE_LAMIA) {
				_master.addMpr(-4);
				// _master.getResistance().addStun(-5);
			}
			if (type == DOLLTYPE_SPATOI) {
				_master.getResistance().addStun(-10);
				_master.addDmgup(-2);
			}
			if (type == DOLLTYPE_ELDER) {
				_master.addMpr(-18);
			}
			if (type == DOLLTYPE_��ƾ) {
				_master.getAC().addAc(2);
				_master.getResistance().addHold(-10);
				_master.removeHasteSkillEffect();
				_master.getMoveState().setMoveSpeed(0);
				S_SkillHaste sh = new S_SkillHaste(_master.getId(), 0, 0);
				_master.sendPackets(sh);
				Broadcaster.broadcastPacket(_master, sh, true);
				_master.set��ƾ(false);
			}
			if (type == DOLLTYPE_RICH) {
				_master.getAbility().addSp(-2);
				_master.addMaxHp(-80);

				_master.getResistance().addStun(-5);
				_master.sendPackets(new S_SPMR(_master), true);
			}

			if (type == DOLLTYPE_�̺�Ʈ����) {
				_master.addDmgup(-1);
				_master.addHitup(-1);
				_master.addBowHitupByDoll(-1);
				_master.addBowDmgupByDoll(-1);
				_master.getAbility().addSp(-1);
				_master.addMaxHp(-100);
				_master.addMaxMp(-50);
				_master.sendPackets(new S_SPMR(_master));
				_master.sendPackets(new S_HPUpdate(_master), true);
				_master.sendPackets(new S_MPUpdate(_master.getCurrentMp(),
						_master.getMaxMp()), true);
			}

			// 1800�� ���� �ٰŸ� ����� + 1 ���Ÿ� ����� + 1 ���� + 1 sp + 1 ���� ���� +1 �ִ� HP +
			// 30 64�ʸ��� MP 10ȸ��
			if (type == DOLLTYPE_�׷���) {
				_master.addDmgup(-1);
				_master.addHitup(-1);

				_master.addBowDmgup(-1);
				_master.addBowHitup(-1);

				_master.getAbility().addSp(-1);
				_master.addMaxHp(-30);
				_master.sendPackets(new S_HPUpdate(_master), true);
				_master.sendPackets(new S_SPMR(_master), true);
			}

			if (type >= DOLLTYPE_���� && type <= DOLLTYPE_�׷���) {
				_master.getAbility().addSp(-1);
				_master.addDmgup(-2);
				_master.sendPackets(new S_SPMR(_master), true);
			}
			if (type == DOLLTYPE_����) {
				if (getNpcId() == 100431) { // ���� ��Ÿ��
					_master.getAbility().addSp(-1);
					_master.addMaxHp(-30);
					_master.sendPackets(new S_SPMR(_master), true);
				} else if (getNpcId() == 100432) { // ��
					_master.addBowDmgupByDoll(-2);
					_master.addMaxHp(-30);
				} else if (getNpcId() == 100433) { // è�Ǿ�
					_master.addDmgup(-2);
					_master.addMaxHp(-30);
				}
			}
			if (type == DOLLTYPE_�ܵ� || type == DOLLTYPE_�긮) {
				_master.addMaxHp(-50);
				_master.addMaxMp(-30);
			}
			if (type == DOLLTYPE_�巹��ũ) {
				_master.addBowDmgupByDoll(-2);
			}
			if (type == DOLLTYPE_����_����) {
				_master.addMaxHp(-100);
			}

			if (type == DOLLTYPE_�����) {
				_master.addDmgup(-1);
				_master.addHitup(-1);
			}

			if (type == DOLLTYPE_����Ŭ�ӽ�) {
				_master.addDmgup(-2);
				_master.addHitup(-2);
				_master.getResistance().addStun(-12);
			}

			if (type == DOLLTYPE_��ť��) {
				_master.getAbility().addSp(-1);
				_master.sendPackets(new S_SPMR(_master));
			}
			if (type == DOLLTYPE_ŷ_���׺���) {// ŷ����
				_master.getResistance().addStun(-8);
			}
			if (type == DOLLTYPE_��) {
				_master.addMaxHp(-50);
				_master.sendPackets(new S_HPUpdate(_master), true);
			}
			if (type == DOLLTYPE_��ٰ�) {
				_master.addDmgup(-1);
			}
			if (type == DOLLTYPE_����Ʈ�ߵ�) {
				_master.addDmgup(-2);
				_master.addHitup(-2);
			}
			if (type == DOLLTYPE_�����̾�) {
				_master.addDmgup(-2);
				_master.addHitup(-2);
			}
			if (type == DOLLTYPE_�þ�) {
				_master.addBowDmgup(-5);
			}
			if (type == DOLLTYPE_����) {
				_master.getResistance().addStun(-12);
				_master.addMaxHp(-300);
				_master.addDmgup(-4);
			}
			if (type == DOLLTYPE_�ٶ�ī) {
				_master.getResistance().addStun(-12);
				_master.addMaxHp(-100);
				_master.addDmgup(-5);
				_master.addHitup(-5);
			}
			if (type == DOLLTYPE_Ÿ��) {
				_master.getResistance().addStun(-10);
				_master.addHitup(-5);
				_master.getAbility().addSp(-3);
				_master.sendPackets(new S_SPMR(_master));
			}
			stopHelpTimer();
			if (_master.isDead() || !(_master instanceof L1RobotInstance)) {
				S_SkillSound sh = new S_SkillSound(getId(), 5936);
				_master.sendPackets(sh);
				Broadcaster.broadcastPacket(_master, sh, true);
			}
			if (_master instanceof L1RobotInstance) {
				L1RobotInstance bot = (L1RobotInstance) _master;
				bot.�������� = false;
			}
		} catch (Exception e) {
		}
		try {
			if (_master.getDollList() != null) {
				_master.removeDoll(this);
			}
		} catch (Exception e) {
		}
		try {
			deleteMe();
		} catch (Exception e) {
		}
	}

	@Override
	public void onPerceive(L1PcInstance perceivedFrom) {
		perceivedFrom.getNearObjects().addKnownObject(this);
		S_DollPack dp = new S_DollPack(this, perceivedFrom);
		perceivedFrom.sendPackets(dp, true);
	}

	@Override
	public void onItemUse() {
		if (!isActived()) {
			// 100%�� Ȯ���� ���� �ľ� �Ϻ� ���
			useItem(USEITEM_HASTE, 100);
		}
	}

	@Override
	public void onGetItem(L1ItemInstance item) {
		if (getNpcTemplate().get_digestitem() > 0) {
			setDigestItem(item);
		}
		if (Arrays.binarySearch(haestPotions, item.getItem().getItemId()) >= 0) {
			useItem(USEITEM_HASTE, 100);
		}
	}

	public int getDollType() {
		return _dollType;
	}

	public void setDollType(int i) {
		_dollType = i;
	}

	public int getItemObjId() {
		return _itemObjId;
	}

	public void setItemObjId(int i) {
		_itemObjId = i;
	}

	public int getDamageByDoll(L1Character target) {
		int damage = 0;
		int type = getDollType();
		int chance = _random.nextInt(100) + 1;
		if (chance <= 7) {
			if (type == DOLLTYPE_WAREWOLF) {
				damage = 15;
			} else if (type == DOLLTYPE_CRUSTACEA) {
				damage = 16;
			} else if (type >= DOLLTYPE_���� && type <= DOLLTYPE_�׷���) {
				if (target instanceof L1PcInstance)
					return 0;
				if (getLocation().getTileLineDistance(
						new Point(target.getX(), target.getY())) > 10)
					return 0;
				int gfx = 0;
				if (type == DOLLTYPE_�׷���)
					gfx = 4022;
				else if (type == DOLLTYPE_����)
					gfx = 1809;
				else if (type == DOLLTYPE_������)
					gfx = 1583;
				else if (type == DOLLTYPE_������)
					gfx = 7331;
				Broadcaster.broadcastPacket(this, new S_UseAttackSkill(this,
						target.getId(), gfx, target.getX(), target.getY(), 1),
						true);
				Broadcaster.broadcastPacketExceptTargetSight(target,
						new S_DoActionGFX(target.getId(),
								ActionCodes.ACTION_Damage), this, true);
				/*
				 * if (_master instanceof L1PcInstance) { L1PcInstance pc =
				 * (L1PcInstance) _master; pc.sendPackets(new
				 * S_SkillSound(_master.getId(), 7624), true); }
				 * Broadcaster.broadcastPacket(_master, new
				 * S_SkillSound(_master.getId(), 7624), true);
				 */
				return 15;
			} else
				return 0;
			S_SkillSound ss = new S_SkillSound(_master.getId(), 6319);
			if (_master instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) _master;
				pc.sendPackets(ss);
			}
			Broadcaster.broadcastPacket(_master, ss, true);
		}

		return damage;
	}

	public double getAddExpByDoll() {
		double addexp = 1;

		if (getDollType() == DOLLTYPE_�̺�Ʈ����) {
			if (_master.getLevel() < 81) {
				addexp = 2;
			}
		} else if (getDollType() == DOLLTYPE_���̾�Ʈ) {
			addexp = 1.1;
		} else if (getDollType() == DOLLTYPE_����Ǳ��) {
			addexp = 1.3;
		} else if (getDollType() == DOLLTYPE_��������Ʈ) {
			addexp = 1.2;
		} else if (getDollType() == DOLLTYPE_����) {
			addexp = 1.1;
		} else if (getDollType() == DOLLTYPE_�ٶ�ī) {
			addexp = 1.1;
		} else if (getDollType() == DOLLTYPE_�ӹ̷ε�) {
			addexp = 1.1;
		} else if (getDollType() == DOLLTYPE_�ξ�) {
			addexp = 1.03;
		}
		return addexp;
	}

	public static int calcMrDefense(int MagicResistance, int dmg) {
		double cc = 0;
		if (MagicResistance <= 19) {
			cc = 0.05;
		} else if (MagicResistance <= 29) {
			cc = 0.07;
		} else if (MagicResistance <= 39) {
			cc = 0.1;
		} else if (MagicResistance <= 49) {
			cc = 0.12;
		} else if (MagicResistance <= 59) {
			cc = 0.17;
		} else if (MagicResistance <= 69) {
			cc = 0.20;
		} else if (MagicResistance <= 79) {
			cc = 0.22;
		} else if (MagicResistance <= 89) {
			cc = 0.25;
		} else if (MagicResistance <= 99) {
			cc = 0.27;
		} else if (MagicResistance <= 110) {
			cc = 0.31;
		} else if (MagicResistance <= 120) {
			cc = 0.32;
		} else if (MagicResistance <= 130) {
			cc = 0.34;
		} else if (MagicResistance <= 140) {
			cc = 0.36;
		} else if (MagicResistance <= 150) {
			cc = 0.38;
		} else if (MagicResistance <= 160) {
			cc = 0.40;
		} else if (MagicResistance <= 170) {
			cc = 0.42;
		} else if (MagicResistance <= 180) {
			cc = 0.44;
		} else if (MagicResistance <= 190) {
			cc = 0.46;
		} else if (MagicResistance <= 200) {
			cc = 0.48;
		} else if (MagicResistance <= 220) {
			cc = 0.49;
		} else {
			cc = 0.51;
		}

		dmg -= dmg * cc;

		if (dmg < 0) {
			dmg = 0;
		}

		return dmg;
	}

	public int getMagicDamageByDoll(L1Character _target) {// ��� ���� �߰� ������� �޼���
		int magicDamage = 0;
		int type = getDollType();
		int chance = _random.nextInt(100) + 1;

		int pcInt = _master.getAbility().getTotalInt();
		int sp = _master.getAbility().getSp();

		if (chance <= 10) {// �ߵ� Ȯ��
			if (type == DOLLTYPE_�����) {
				magicDamage = (pcInt + sp) / 2; // �߰�������� �� ����� ��ġ(����)
				S_SkillSound ss = new S_SkillSound(_target.getId(), 11736);
				if (_target instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) _target;
					pc.sendPackets(ss);
				}
				Broadcaster.broadcastPacket(_target, ss);
				ss.clear();
				ss = null;
				if (magicDamage < 10)
					magicDamage = 10;
				if (magicDamage > 50)
					magicDamage = 50;
			} else if (type == DOLLTYPE_��������Ʈ) {
				/*
				 * int targetMr =
				 * _target.getResistance().getEffectedMrBySkill(); int randmg =
				 * _random.nextInt((pcInt*5)); magicDamage = sp*6 + randmg;
				 * magicDamage = calcMrDefense(targetMr, (int)magicDamage);
				 * magicDamage -= magicDamage * calcAttrResistance(_target, 2);
				 */

				magicDamage = _random.nextInt(70) + 50;/* pcInt+sp */// �߰��������
																		// �� �����
																		// ��ġ(����)

				S_EffectLocation ss = new S_EffectLocation(this.getX(),
						this.getY(), 11660);
				/*
				 * if (_target instanceof L1PcInstance) { L1PcInstance pc =
				 * (L1PcInstance) _target; pc.sendPackets(ss); }
				 */
				// System.out.println("111111111111111111111111");
				Broadcaster.broadcastPacket(this, ss);
				// if(magicDamage < 40)magicDamage=40;
				// if(magicDamage > 80)magicDamage=80;
			}
		}

		return magicDamage;
	}

	public void attackPoisonDamage(L1PcInstance pc, L1Character cha) {
		int type = getDollType();
		if (type == DOLLTYPE_LAMIA) {
			int chance = _random.nextInt(100) + 1;
			if (10 >= chance) {
				L1DamagePoison.doInfection(pc, cha, 3000, 10);
			}
		}
	}

	public int getStunLevelAdd() {
		int addStun = 0;
		if (getDollType() == DOLLTYPE_����Ʈ�ߵ�) {
			addStun = 5; // 3
		} else if (getDollType() == DOLLTYPE_����) {
			addStun = 10; // 6
		}
		return addStun;
	}
	
	public int getamorLevelAdd() { //�Ƹ�
		int addamor = 0;
		 if (getDollType() == DOLLTYPE_�ٶ�ī) {
			addamor = 10; //6
		}
		return addamor;
	}

	public int getDamageReductionByDoll() {
		int DamageReduction = 0;
		if (getDollType() == DOLLTYPE_��ٰ�
				|| getDollType() == DOLLTYPE_STONEGOLEM
				|| getDollType() == DOLLTYPE_���̾�Ʈ) {
			DamageReduction = 1;
			/*
			 * int chance = _random.nextInt(100) + 1; if (chance <= 2) {
			 * DamageReduction = 15; S_SkillSound ss = new
			 * S_SkillSound(_master.getId(), 6320); if (_master instanceof
			 * L1PcInstance) { L1PcInstance pc = (L1PcInstance) _master;
			 * pc.sendPackets(ss); } Broadcaster.broadcastPacket(_master, ss,
			 * true); }
			 */
		} else if (getDollType() == DOLLTYPE_���̾Ƹ���) {
			DamageReduction = 2;
		} else if (getDollType() == DOLLTYPE_��������Ʈ) {
			DamageReduction = 5;
		} else if (getDollType() == DOLLTYPE_���̸���) {
			DamageReduction = 3;
		} else if (getDollType() == DOLLTYPE_�ӹ̷ε�) {
			DamageReduction = 2;
		} 

		return DamageReduction;
	}

	public boolean isMpRegeneration() {
		boolean isMpRegeneration = false;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_SUCCUBUS:
		case DOLLTYPE_ELDER:
		case DOLLTYPE_SNOWMAN_B:
		case DOLLTYPE_����:
		case DOLLTYPE_�ܵ�:
		case DOLLTYPE_����_����:
		case DOLLTYPE_�׷���:
		case DOLLTYPE_�����:
		case DOLLTYPE_��ť��:
		case DOLLTYPE_�̺�Ʈ����:
		case DOLLTYPE_ŷ_���׺���:
		case DOLLTYPE_�巹��ũ:
			isMpRegeneration = true;
			break;
		}
		return isMpRegeneration;
	}

	public boolean isHpRegeneration() {
		boolean isHpRegeneration = false;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_SEADANCER:
		case DOLLTYPE_SNOWMAN_C:
		case DOLLTYPE_�긮:
		case DOLLTYPE_�þ�:
			isHpRegeneration = true;
			break;
		}
		return isHpRegeneration;
	}
	public int fou_DamageUp() { //���콽���̾� ��������
		int fou = 0;
		switch (getDollType()) {
		case DOLLTYPE_���̸���:
			fou = 10;
			break;
		}
		return fou;
	}
	
	public int getWeightReductionByDoll() {
		int weightReduction = 0;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_HIGH_DRAGON_M:
		case DOLLTYPE_HIGH_DRAGON_W:
			weightReduction = 5;
			break;
		case DOLLTYPE_BUGBEAR:
			weightReduction = 500;
			break;
		case DOLLTYPE_����_����:
			weightReduction = 15;
			break;
		}
		return weightReduction;
	}

	public int getMpRegenerationValues() {
		int regenMp = 0;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_�巹��ũ:
			regenMp = 6;
			break;
		case DOLLTYPE_�׷���:
		case DOLLTYPE_ŷ_���׺���:
			regenMp = 10;
			break;
		case DOLLTYPE_�̺�Ʈ����:
		case DOLLTYPE_SUCCUBUS:
		case DOLLTYPE_ELDER:
		case DOLLTYPE_����:
		case DOLLTYPE_�ܵ�:
		case DOLLTYPE_����_����:
		case DOLLTYPE_�����:
		case DOLLTYPE_��ť��:
			regenMp = 15;
			break;
		case DOLLTYPE_�ӹ̷ε�:
			regenMp = 15;
			break;
		case DOLLTYPE_SNOWMAN_B:
			regenMp = 20;
			break;

		}
		return regenMp;
	}

	public int getHpRegenerationValues() {
		int regenHp = 0;
		int type = getDollType();
		switch (type) {
		case DOLLTYPE_SEADANCER:
		case DOLLTYPE_SNOWMAN_C:
		case DOLLTYPE_�긮:
			regenHp = 25;
			break;
		case DOLLTYPE_�þ�:
			regenHp = 30;
			break;
		}
		return regenHp;
	}

	private void getHelperAction() {
		if (_master.getCurrentHp() < _master.getMaxHp() / 2) {
			L1SkillUse su = new L1SkillUse();
			su.handleCommands(null, 35, _master.getId(), _master.getX(),
					_master.getY(), null, 0, L1SkillUse.TYPE_NORMAL, this);
			su = null;
			return;
		}
		for (int i = 0; i < Buff.length; i++) {
			if (!_master.getSkillEffectTimerSet().hasSkillEffect(Buff[i])) {
				L1SkillUse su = new L1SkillUse();
				su.handleCommands(null, Buff[i], _master.getId(),
						_master.getX(), _master.getY(), null, 0,
						L1SkillUse.TYPE_NORMAL, this);
				su = null;
				break;
			}
		}
	}

	public void startHelpTimer() {
		if (getDollType() != DOLLTYPE_HELPER)
			return;
		_future = GeneralThreadPool.getInstance().scheduleAtFixedRate(
				new HelpTimer(), 4000, 4000);
	}

	public void stopHelpTimer() {
		if (getDollType() != DOLLTYPE_HELPER)
			return;
		if (_future != null) {
			_future.cancel(false);
		}
	}
}
