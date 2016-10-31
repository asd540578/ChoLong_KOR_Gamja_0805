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
package l1j.server.GameSystem;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;

public class PetMatch {
	public static final int STATUS_NONE = 0;
	public static final int STATUS_READY1 = 1;
	public static final int STATUS_READY2 = 2;
	public static final int STATUS_PLAYING = 3;

	public static final int MAX_PET_MATCH = 1;
	private static final short[] PET_MATCH_MAPID = { 5125, 5131, 5132, 5133,
			5134 };

	private String[] _pc1Name = new String[MAX_PET_MATCH];
	private String[] _pc2Name = new String[MAX_PET_MATCH];
	private L1PetInstance[] _pet1 = new L1PetInstance[MAX_PET_MATCH];
	private L1PetInstance[] _pet2 = new L1PetInstance[MAX_PET_MATCH];

	private static PetMatch _instance;

	private Random _random = new Random(System.nanoTime());

	public static PetMatch getInstance() {
		if (_instance == null) {
			synchronized (PetMatch.class) {
				if (_instance == null) {
					_instance = new PetMatch();
				}
			}
		}
		return _instance;
	}

	public int setPetMatchPc(int petMatchNo, L1PcInstance pc, L1PetInstance pet) {
		int status = getPetMatchStatus(petMatchNo);

		switch (status) {
		case STATUS_NONE:
			_pc1Name[petMatchNo] = pc.getName();
			_pet1[petMatchNo] = pet;
			return STATUS_READY1;
		case STATUS_READY1:
			_pc2Name[petMatchNo] = pc.getName();
			_pet2[petMatchNo] = pet;
			return STATUS_PLAYING;
		case STATUS_READY2:
			_pc1Name[petMatchNo] = pc.getName();
			_pet1[petMatchNo] = pet;
			return STATUS_PLAYING;
		default:
			return STATUS_NONE;
		}
	}

	private synchronized int getPetMatchStatus(int petMatchNo) {
		L1PcInstance pc1 = null;
		if (_pc1Name[petMatchNo] != null)
			pc1 = L1World.getInstance().getPlayer(_pc1Name[petMatchNo]);

		L1PcInstance pc2 = null;
		if (_pc2Name[petMatchNo] != null)
			pc2 = L1World.getInstance().getPlayer(_pc2Name[petMatchNo]);

		if (pc1 == null && pc2 == null)
			return STATUS_NONE;

		if (pc1 == null && pc2 != null) {
			if (pc2.getMapId() == PET_MATCH_MAPID[petMatchNo]) {
				return STATUS_READY2;
			} else {
				_pc2Name[petMatchNo] = null;
				_pet2[petMatchNo] = null;
				return STATUS_NONE;
			}
		}

		if (pc1 != null && pc2 == null) {
			if (pc1.getMapId() == PET_MATCH_MAPID[petMatchNo]) {
				return STATUS_READY1;
			} else {
				_pc1Name[petMatchNo] = null;
				_pet1[petMatchNo] = null;
				return STATUS_NONE;
			}
		}

		// PC가 시합장에 2명 있는 경우
		if (pc1.getMapId() == PET_MATCH_MAPID[petMatchNo]
				&& pc2.getMapId() == PET_MATCH_MAPID[petMatchNo]) {
			return STATUS_PLAYING;
		}

		// PC가 시합장에 1명 있는 경우
		if (pc1.getMapId() == PET_MATCH_MAPID[petMatchNo]) {
			_pc2Name[petMatchNo] = null;
			_pet2[petMatchNo] = null;
			return STATUS_READY1;
		}
		if (pc2.getMapId() == PET_MATCH_MAPID[petMatchNo]) {
			_pc1Name[petMatchNo] = null;
			_pet1[petMatchNo] = null;
			return STATUS_READY2;
		}
		return STATUS_NONE;
	}

	public synchronized boolean enterPetMatch(L1PcInstance pc, int amuletId) {
		return false;
	}


	public void startPetMatch(int petMatchNo) {
		try {
			Thread.sleep(3000);
			int a = 3204 + _random.nextInt(6);
			int b = 3204 + _random.nextInt(6);
			S_SkillSound ss1 = new S_SkillSound(_pet1[petMatchNo].getId(), a);
			S_SkillSound ss2 = new S_SkillSound(_pet2[petMatchNo].getId(), b);
			Broadcaster.broadcastPacket(_pet1[petMatchNo], ss1, true);
			Broadcaster.broadcastPacket(_pet2[petMatchNo], ss2, true);
			Thread.sleep(4000);
			if (a > b) {
				_pet1[petMatchNo].useHastePotion(500);
			} else if (b > a) {
				_pet2[petMatchNo].useHastePotion(500);
			}
			Thread.sleep(4000);
		} catch (Throwable e) {
		}
		L1PcInstance pc1 = L1World.getInstance()
				.getPlayer(_pc1Name[petMatchNo]);
		L1PcInstance pc2 = L1World.getInstance()
				.getPlayer(_pc2Name[petMatchNo]);

		_pet1[petMatchNo].setCurrentPetStatus(1);
		_pet1[petMatchNo].setTarget(_pet2[petMatchNo]);

		_pet2[petMatchNo].setCurrentPetStatus(1);
		_pet2[petMatchNo].setTarget(_pet1[petMatchNo]);

		S_PacketBox pb_time = new S_PacketBox(S_PacketBox.MINIGAME_TIME, 300);
		pc1.sendPackets(pb_time);
		pc2.sendPackets(pb_time, true);

		PetMatchTimer timer = new PetMatchTimer(_pet1[petMatchNo],
				_pet2[petMatchNo], petMatchNo);
		timer.begin();
	}

	public void endPetMatch(int petMatchNo, int winNo) {
		try {
			Thread.sleep(4000);
		} catch (Throwable e) {
		}

		L1PcInstance pc1 = L1World.getInstance()
				.getPlayer(_pc1Name[petMatchNo]);
		L1PcInstance pc2 = L1World.getInstance()
				.getPlayer(_pc2Name[petMatchNo]);
		if (pc1 != null && pc2 != null) {
			if (winNo == 1) {
				_pet1[petMatchNo].setCurrentPetStatus(3);
				giveMedal(pc1, petMatchNo, true);
				giveMedal(pc2, petMatchNo, false);
			} else if (winNo == 2) {
				_pet2[petMatchNo].setCurrentPetStatus(3);
				giveMedal(pc1, petMatchNo, false);
				giveMedal(pc2, petMatchNo, true);
			} else if (winNo == 3) { // 무승부
				_pet1[petMatchNo].setCurrentPetStatus(3);
				_pet2[petMatchNo].setCurrentPetStatus(3);
				giveMedal(pc1, petMatchNo, false);
				giveMedal(pc2, petMatchNo, false);
			}

			S_PacketBox pb_clear = new S_PacketBox(
					S_PacketBox.MINIGAME_TIME_CLEAR);
			pc1.sendPackets(pb_clear);
			pc2.sendPackets(pb_clear, true);
		}
		qiutPetMatch(petMatchNo);
	}

	private void giveMedal(L1PcInstance pc, int petMatchNo, boolean isWin) {
		if (pc == null)
			return;
		if (pc.getMapId() != PET_MATCH_MAPID[petMatchNo])
			return;
		S_ServerMessage sm403 = null;
		if (isWin) {
			S_ServerMessage sm1166 = new S_ServerMessage(1166, pc.getName());
			pc.sendPackets(sm1166, true);
			L1ItemInstance item = ItemTable.getInstance().createItem(41309);
			int count = 10;
			if (item != null) {
				if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
					item.setCount(count);
					pc.getInventory().storeItem(item);
					// if (_random.nextInt(10) <= 2){
					pc.getInventory().storeItem(L1ItemId.PETMATCH_WINNER_PIECE,
							1);
					sm403 = new S_ServerMessage(403, "펫 매치 승리의 조각");
					pc.sendPackets(sm403);
					// }
					sm403 = new S_ServerMessage(403, item.getLogName());
					pc.sendPackets(sm403);
				}
			}
		} else {
			L1ItemInstance item = ItemTable.getInstance().createItem(41309);
			int count = 5;
			if (item != null) {
				if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
					item.setCount(count);
					pc.getInventory().storeItem(item);
					sm403 = new S_ServerMessage(403, item.getLogName());
					pc.sendPackets(sm403);
				}
			}
		}
	}

	private void qiutPetMatch(int petMatchNo) {
		L1PcInstance pc1 = L1World.getInstance()
				.getPlayer(_pc1Name[petMatchNo]);
		if (pc1 != null && pc1.getMapId() == PET_MATCH_MAPID[petMatchNo]) {
			L1PetInstance pet = null;
			for (Object object : pc1.getPetList()) {
				if (object instanceof L1PetInstance) {
					pet = (L1PetInstance) object;
					pet.dropItem();
					// pc1.getPetList().remove(pet.getId());
					pc1.removePet(pet);
					pet.deleteMe();
				}
			}
			L1Teleport.teleport(pc1, 32630, 32744, (short) 4, 4, true);
		}
		_pc1Name[petMatchNo] = null;
		_pet1[petMatchNo] = null;

		L1PcInstance pc2 = L1World.getInstance()
				.getPlayer(_pc2Name[petMatchNo]);
		if (pc2 != null && pc2.getMapId() == PET_MATCH_MAPID[petMatchNo]) {
			L1PetInstance pet = null;
			for (Object object : pc2.getPetList()) {
				if (object instanceof L1PetInstance) {
					pet = (L1PetInstance) object;
					pet.dropItem();
					pc2.removePet(pet);
					// pc2.getPetList().remove(pet.getId());
					pet.deleteMe();
				}
			}
			L1Teleport.teleport(pc2, 32630, 32744, (short) 4, 4, true);
		}
		_pc2Name[petMatchNo] = null;
		_pet2[petMatchNo] = null;
	}

	public class PetMatchReadyTimer extends TimerTask {

		private final int _petMatchNo;
		private final L1PcInstance _pc;
		private final L1PetInstance _pet;

		public PetMatchReadyTimer(int petMatchNo, L1PcInstance pc,
				L1PetInstance pet) {
			_petMatchNo = petMatchNo;
			_pc = pc;
			_pet = pet;
		}

		public void begin() {
			Timer timer = new Timer();
			timer.schedule(this, 3000);
		}

		@Override
		public void run() {
			try {
				for (;;) {
					Thread.sleep(1000);
					if (_pc == null || _pet == null) {
						this.cancel();
						return;
					}

					if (_pc.isTeleport())
						continue;

					if (PetMatch.getInstance().setPetMatchPc(_petMatchNo, _pc,
							_pet) == PetMatch.STATUS_PLAYING) {
						PetMatch.getInstance().startPetMatch(_petMatchNo);
					}
					this.cancel();
					return;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}

	public class PetMatchTimer implements Runnable {
		private final L1PetInstance _pet1;
		private final L1PetInstance _pet2;
		private final int _petMatchNo;
		private int _counter = 0;

		public PetMatchTimer(L1PetInstance pet1, L1PetInstance pet2,
				int petMatchNo) {
			_pet1 = pet1;
			_pet2 = pet2;
			_petMatchNo = petMatchNo;
		}

		public void begin() {
			GeneralThreadPool.getInstance().execute(this);
		}

		@Override
		public void run() {
			try {
				while (true) {
					Thread.sleep(300);
					_counter++;
					if (_pet1 == null || _pet2 == null)
						break;

					if (_pet1.isDead() || _pet2.isDead()) {
						int winner = 0;
						if (!_pet1.isDead() && _pet2.isDead()) {
							winner = 1;
							_pet1.setCurrentPetStatus(3);
							S_SkillSound ss = new S_SkillSound(_pet1.getId(),
									6354);
							Broadcaster.broadcastPacket(_pet1, ss, true);
						} else if (_pet1.isDead() && !_pet2.isDead()) {
							winner = 2;
							_pet2.setCurrentPetStatus(3);
							S_SkillSound ss = new S_SkillSound(_pet2.getId(),
									6354);
							Broadcaster.broadcastPacket(_pet2, ss, true);
						} else {
							winner = 3;
						}
						PetMatch.getInstance().endPetMatch(_petMatchNo, winner);
						break;
					}

					if (_counter == 1000) { // 5분 지나도 끝나지 않는 경우는 무승부
						PetMatch.getInstance().endPetMatch(_petMatchNo, 3);
						break;
					}
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
