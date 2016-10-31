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
import java.util.Random;

import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Magic;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.poison.L1DamagePoison;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.templates.L1Npc;

public class L1EffectInstance extends L1NpcInstance {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int FW_DAMAGE_INTERVAL = 1000;
	private int CubeTime; // 큐브시간
	private L1PcInstance CubePc; // 큐브사용자
	private L1MonsterInstance PoisonCludeMon; // 독구름사용몬스터
	/** true = normal, false = hard **/
	public boolean PoisonDamageStrength = false;
	private int Cube = 20;

	public L1EffectInstance(L1Npc template) {
		super(template);

		if (getNpcTemplate().get_npcId() == 81157) { // FW
			GeneralThreadPool.getInstance()
					.schedule(new FwDamageTimer(this), 0);
		} else if (getNpcId() == 100394) { // 독구름
			GeneralThreadPool.getInstance().schedule(new PoisonClude(this), 0);
		}
	}

	/** 큐브다 */
	public void setCubeTime(int CubeTime) {
		this.CubeTime = CubeTime;
	}

	public boolean isCube() {
		return CubeTime-- <= 0;
	}

	public void setCubePc(L1PcInstance CubePc) {
		this.CubePc = CubePc;
	}

	public L1PcInstance CubePc() {
		return CubePc;
	}

	public void setPoisonCludeMon(L1MonsterInstance pcm) {
		this.PoisonCludeMon = pcm;
	}

	public L1MonsterInstance getPoisonCludeMon() {
		return PoisonCludeMon;
	}

	public boolean Cube() {
		return Cube-- <= 0;
	}

	@Override
	public void onAction(L1PcInstance pc) {
	}

	@Override
	public void deleteMe() {
		try {
			_destroyed = true;
			if (getInventory() != null) {
				getInventory().clearItems();
			}
			allTargetClear();
			_master = null;
			this.setCubePc(null);

			if (aStar != null) {
				aStar.clear();
			}
			aStar = null;

			L1World.getInstance().removeVisibleObject(this);
			L1World.getInstance().removeObject(this);

			Broadcaster.broadcastPacket(this, new S_RemoveObject(this));
			// _isdelete = true;
			/*
			 * List<L1PcInstance> players =
			 * L1World.getInstance().getRecognizePlayer(this);
			 * 
			 * if (players.size() > 0) { S_RemoveObject s_deleteNewObject = new
			 * S_RemoveObject(this); for (L1PcInstance pc : players) {
			 * 
			 * if (pc != null) { //pc.getNearObjects().removeKnownObject(this);
			 * pc.sendPackets(s_deleteNewObject); //Config.addaa(); //
			 * if(!L1Character.distancepc(user, this)) } } }
			 */
			getNearObjects().removeAllKnownObjects();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class FwDamageTimer implements Runnable {
		private L1EffectInstance _effect;

		public FwDamageTimer(L1EffectInstance effect) {
			_effect = effect;
		}

		@Override
		public void run() {

			L1Magic magic = null;
			L1MonsterInstance mob = null;
			ArrayList<L1Object> list = L1World.getInstance().getVisibleObjects(
					_effect);
			while (!_destroyed) {
				try {
					for (L1Object objects : list) {
						if (objects.getX() != getX()
								|| objects.getY() != getY())
							continue;
						if (objects instanceof L1PcInstance) {
							continue;
							
						} else if (objects instanceof L1MonsterInstance) {
							mob = (L1MonsterInstance) objects;
							if (mob.isDead()) {
								continue;
							}
							magic = new L1Magic(_effect, mob);
							int damage = magic.calcNpcFireWallDamage();
							if (damage == 0) {
								continue;
							}
							Broadcaster.broadcastPacket(mob, new S_DoActionGFX(
									mob.getId(), ActionCodes.ACTION_Damage),
									true);
							try {
								mob.receiveDamage(
										_effect.CubePc() == null ? _effect
												: _effect.CubePc(), damage);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					Thread.sleep(FW_DAMAGE_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class PoisonClude implements Runnable {
		private L1EffectInstance _effect;
		private L1PcInstance[] list = null;

		public PoisonClude(L1EffectInstance effect) {
			_effect = effect;
			list = L1World.getInstance().getAllPlayersToArray();
		}

		@Override
		public void run() {

			// L1PcInstance pc = null;
			// L1MonsterInstance mob = null;
			try {
				if (_destroyed)
					return;

				Random _rnd = new Random(System.nanoTime());
				for (L1PcInstance pc : list) {
					// if (objects instanceof L1PcInstance) {
					// pc = (L1PcInstance) objects;
					if (pc == null)
						continue;
					if (getMapId() != pc.getMapId() || getX() != pc.getX()
							|| getY() != pc.getY())
						continue;
					if (pc.isGhost() || pc.isDead() || pc.isGm()) {
						continue;
					}
					// pc.sendPackets(new S_DoActionGFX(pc.getId(),
					// ActionCodes.ACTION_Damage));
					// Broadcaster.broadcastPacket(pc, new
					// S_DoActionGFX(pc.getId(),ActionCodes.ACTION_Damage),
					// true);
					if (10 < _rnd.nextInt(100)) {
						int damage = 100 + _rnd.nextInt(50);
						if (PoisonDamageStrength)
							damage = 20 + _rnd.nextInt(10);
						L1DamagePoison.doInfection(
								_effect.getPoisonCludeMon() == null ? _effect
										: _effect.getPoisonCludeMon(), pc,
								3000, damage);
					}
					/*
					 * try{ pc.receiveDamage(_effect.getPoisonCludeMon() == null
					 * ? _effect : _effect.getPoisonCludeMon(), damage, false);
					 * }catch(Exception e){ e.printStackTrace(); }
					 */
					// }
				}
				GeneralThreadPool.getInstance().schedule(this,
						FW_DAMAGE_INTERVAL);
				// Thread.sleep(FW_DAMAGE_INTERVAL);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
