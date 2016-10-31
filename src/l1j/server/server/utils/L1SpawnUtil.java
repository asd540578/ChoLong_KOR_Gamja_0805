/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastTable;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.BossSpawnThread;
import l1j.server.GameSystem.GameList;
import l1j.server.GameSystem.Antaras.AntarasRaid;
import l1j.server.GameSystem.Antaras.AntarasRaidSystem;
import l1j.server.GameSystem.IceQeen.IceQeen;
import l1j.server.GameSystem.Papoo.PaPooRaid;
import l1j.server.GameSystem.Papoo.PaPooRaidSystem;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1NpcDeleteTimer;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ArrowInstance;
import l1j.server.server.model.Instance.L1CastleGuardInstance;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.templates.L1Npc;

public class L1SpawnUtil {
	private static Logger _log = Logger.getLogger(L1SpawnUtil.class.getName());

	public static void spawn(L1PcInstance pc, int npcId, int randomRange,
			int timeMillisToDelete, boolean isUsePainwand) {
		try {

			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(npcId);
			npc.setId(ObjectIdFactory.getInstance().nextId());
			npc.setMap(pc.getMapId());
			/*
			 * if (randomRange == 0) { randomRange = 3; }
			 */
			if (randomRange == 0) {
				npc.getLocation().set(pc.getLocation());
				npc.getLocation().forward(pc.getMoveState().getHeading());
				if (npc.getMap().isInMap(npc.getLocation())
						&& npc.getMap().isPassable(npc.getLocation())) {
				} else {
					npc.getLocation().set(pc.getLocation());
				}
			} else {
				int tryCount = 0;
				do {
					tryCount++;
					npc.setX(pc.getX() + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					npc.setY(pc.getY() + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					if ((npc.getX() >= 32936 && npc.getY() <= 32945)
							&& (npc.getY() >= 32861 && npc.getY() <= 32870)
							&& npc.getMapId() == 410)
						continue;
					if (npc.getMap().isInMap(npc.getLocation())
							&& npc.getMap().isPassable(npc.getLocation())) {
						break;
					}
					Thread.sleep(1);
				} while (tryCount < 50);
				if (tryCount >= 50) {
					npc.getLocation().set(pc.getLocation());
					npc.getLocation().forward(pc.getMoveState().getHeading());
					if (npc.getMap().isInMap(npc.getLocation())
							&& npc.getMap().isPassable(npc.getLocation())) {
					} else {
						npc.getLocation().set(pc.getLocation());
					}
				}
			}
			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			npc.getMoveState().setHeading(pc.getMoveState().getHeading());
			if (npc instanceof L1CastleGuardInstance) {
				((L1CastleGuardInstance) npc).default_heading = pc
						.getMoveState().getHeading();
			}
			if (isUsePainwand) {
				if (npc instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) npc;
					mon.set_storeDroped((byte) 2);
				}
			}

			/*
			 * if(npc instanceof L1DoorInstance){ L1DoorInstance fi =
			 * (L1DoorInstance) npc; System.out.println("123123123");
			 * fi.setDirection(1); fi.setLeftEdgeLocation(fi.getY()-3);
			 * fi.setRightEdgeLocation(fi.getY()+3);
			 * 
			 * fi.setOpenStatus( ActionCodes.ACTION_Close);
			 * fi.isPassibleDoor(false); fi.setPassable(1); }
			 */

			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc);
			if (npcId == 45000172) {
				npc.broadcastPacket(new S_NPCPack(npc), true);
				npc.broadcastPacket(new S_DoActionGFX(npc.getId(), 11), true);
			}

			if (npcId == 90000 || npcId == 90002 || npcId == 90009
					|| npcId == 90013 || npcId == 90016) {
				npc.broadcastPacket(new S_NPCPack(npc), true);
				npc.broadcastPacket(new S_DoActionGFX(npc.getId(), 4), true);
			}

			npc.getLight().turnOnOffLight();
			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // 채팅 개시
			if (0 < timeMillisToDelete) {

				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc,
						timeMillisToDelete);
				timer.begin();
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	/**
	 * 화살을 스폰한다
	 */

	public static FastTable<L1ArrowInstance> ArrowSpawn() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		L1Npc l1npc = null;
		L1ArrowInstance field = null;
		FastTable<L1ArrowInstance> list = null;
		list = new FastTable<L1ArrowInstance>();
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM spawnlist_Arrow");
			rs = pstm.executeQuery();
			while (rs.next()) {
				l1npc = NpcTable.getInstance().getTemplate(rs.getInt("npc_id"));
				if (l1npc != null) {
					try {
						field = (L1ArrowInstance) NpcTable.getInstance()
								.newNpcInstance(rs.getInt("npc_id"));
						field.setId(ObjectIdFactory.getInstance().nextId());
						field.setX(rs.getInt("locx"));
						field.setY(rs.getInt("locy"));
						field.setTarX(rs.getInt("tarx"));
						field.setTarY(rs.getInt("tary"));
						field.setMap((short) rs.getInt("mapid"));
						field.setLightSize(l1npc.getLightSize());
						field.getLight().turnOnOffLight();

						L1World.getInstance().storeObject(field);
						L1World.getInstance().addVisibleObject(field);
						int delay = rs.getInt("start_delay");
						if (delay == 0)
							field.setAction(true);
						list.add(field);
					} catch (Exception e) {
						_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				}
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (SecurityException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} catch (IllegalArgumentException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

		return list;
	}

	public static L1NpcInstance spawn2(int x, int y, short map, int npcId,
			int randomRange, int timeMillisToDelete, int movemap) {
		int heading = 5;
		if (npcId == 7000044 || npcId == 100325 || npcId == 100326
				|| (npcId == 100213 && x == 33094 && y == 33401)
				|| npcId == 100563 || npcId == 100646 || npcId == 100692)
			heading = 6;
		else if (npcId == 100430 || npcId == 100709 || npcId == 100710)
			heading = 4;
		return spawn4(x, y, map, heading, npcId, randomRange,
				timeMillisToDelete, movemap, false);
	}

	public static L1NpcInstance spawn2(int x, int y, short map, int npcId,
			int randomRange, int timeMillisToDelete, int movemap, boolean level) {
		int heading = 5;
		if (npcId == 7000044 || npcId == 100325 || npcId == 100326
				|| (npcId == 100213 && x == 33094 && y == 33401)
				|| npcId == 100563 || npcId == 100646 || npcId == 100692)
			heading = 6;
		else if (npcId == 100430 || npcId == 100709 || npcId == 100710)
			heading = 4;
		return spawn4(x, y, map, heading, npcId, randomRange,
				timeMillisToDelete, movemap, level);
	}

	public static L1NpcInstance spawn4(int x, int y, short map, int heading,
			int npcId, int randomRange, int timeMillisToDelete, int movemap) {
		return spawn4(x, y, map, heading, npcId, randomRange,
				timeMillisToDelete, movemap, false);
	}

	public static L1NpcInstance spawn4(int x, int y, short map, int heading,
			int npcId, int randomRange, int timeMillisToDelete, int movemap,
			boolean level) {
		L1NpcInstance npc = null;
		try {
			if (level) {
				npc = NpcTable.getInstance().newNpcInstance(npcId + 1000000);
			} else {
				npc = NpcTable.getInstance().newNpcInstance(npcId);
			}

			npc.setId(ObjectIdFactory.getInstance().nextId());
			npc.setMap(map);
			if (randomRange == 0) {
				npc.getLocation().set(x, y, map);
				/**
				 * 용땅도포함~~~ 하딘 관련 NPC가 아닐 경우에만 적용 일단 주석 처리 해봄.
				 **/
				/*
				 * if(npcId != 4212013 && !(npcId >= 5000038 && npcId <=
				 * 5000093)) npc.getLocation().forward(5);
				 */
			} else {
				int tryCount = 0;
				do {
					tryCount++;
					npc.setX(x + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					npc.setY(y + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					if (npc.getMap().isInMap(npc.getLocation())
							&& npc.getMap().isPassable(npc.getLocation())) {
						break;
					}
					Thread.sleep(1);
				} while (tryCount < 50);
				if (tryCount >= 50) {
					npc.getLocation().set(x, y, map);
					// npc.getLocation().forward(5);
				}
			}

			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			npc.getMoveState().setHeading(heading);
			if (npcId == 4500101 || npcId == 4500102 || npcId == 4500103
					|| npcId == 4212015 || npcId == 4212016 || npcId == 4500107
					|| npcId == 100011) {
				L1FieldObjectInstance fobj = (L1FieldObjectInstance) npc;
				fobj.setMoveMapId(movemap);
			}
			if (npc.getNpcId() == 5000091) {
				L1DoorInstance door = (L1DoorInstance) npc;
				door.setDoorId(npc.getNpcTemplate().get_npcId());
				door.setDirection(0);
				door.setLeftEdgeLocation(door.getX());
				door.setRightEdgeLocation(door.getX());

				door.setOpenStatus(ActionCodes.ACTION_Close);
				door.isPassibleDoor(false);
				door.setPassable(1);
				// door.setOpenStatus(ActionCodes.ACTION_Close);
				// door.isPassibleDoor(false);
				// door.setPassable(L1DoorInstance.PASS);
			}

			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc);
			// 안타라스의 경우 튀어 나오게끔..
			if (npcId == 4212015 || npcId == 4212016 || npcId == 4038000
					|| npcId == 4200010 || npcId == 4200011 || npcId == 4039000
					|| npcId == 4039006 || npcId == 4039007 || npcId == 100011) { // 안타
																					// 파푸
				// npc.broadcastPacket(new S_DoActionGFX(npc.getId(), 11));
				// npc.setActionStatus(11);
				npc.broadcastPacket(new S_NPCPack(npc), true);
				npc.broadcastPacket(new S_DoActionGFX(npc.getId(), 11), true);
				// npc.setActionStatus(3);
				// npc.broadcastPacket(new S_NPCPack(npc));
				// npc.broadcastPacket(new S_NPCPack(npc));
			} else if (npcId == 100586 || npcId == 100587) {
				npc.broadcastPacket(new S_NPCPack(npc), true);
				npc.broadcastPacket(new S_DoActionGFX(npc.getId(), 4), true);
			}
			if (npc.getNpcId() == 4038000 || npc.getNpcId() == 4200010
					|| npc.getNpcId() == 4200011) {
				AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(map);
				ar.setanta(npc);
			}
			if (npc.getNpcId() == 4039000 || npc.getNpcId() == 4039006
					|| npc.getNpcId() == 4039007) {
				PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(map);
				ar.setPaPoo(npc);
			}

			if (map >= 2101 && map <= 2151) {
				synchronized (GameList.IQList) {
					IceQeen IQ = GameList.getIQ(map);
					if (IQ != null)
						IQ.AddMon(npc);
				}
			}

			npc.getLight().turnOnOffLight();
			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // 채팅 개시
			if (0 < timeMillisToDelete) {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc,
						timeMillisToDelete);
				timer.begin();
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		return npc;
	}

	public static L1NpcInstance spawn5(int x, int y, short map, int heading,
			int npcId, int randomRange, int timeMillisToDelete, L1Clan clan) {
		L1NpcInstance npc = null;
		try {
			npc = NpcTable.getInstance().newNpcInstance(npcId);
			npc.setId(ObjectIdFactory.getInstance().nextId());
			npc.setMap(map);
			if (randomRange == 0) {
				npc.getLocation().set(x, y, map);
			} else {
				int tryCount = 0;
				do {
					tryCount++;
					npc.setX(x + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					npc.setY(y + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					if (npc.getMap().isInMap(npc.getLocation())
							&& npc.getMap().isPassable(npc.getLocation())) {
						break;
					}
					Thread.sleep(1);
				} while (tryCount < 50);
				if (tryCount >= 50) {
					npc.getLocation().set(x, y, map);
				}
			}

			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			npc.getMoveState().setHeading(heading);
			if (npc instanceof L1MerchantInstance) {
				L1MerchantInstance mer = (L1MerchantInstance) npc;
				mer.setNameId(clan.getClanName());
				mer.setClanid(clan.getClanId());
				mer.setClanname(clan.getClanName());
			}

			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc);

			npc.getLight().turnOnOffLight();
			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // 채팅 개시
			if (0 < timeMillisToDelete) {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc,
						timeMillisToDelete);
				timer.begin();
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		return npc;
	}

	/**
	 * 보스를 스폰한다
	 * 
	 * @param x
	 * @param y
	 * @param map
	 * @param npcId
	 * @param randomRange
	 * @param timeMillisToDelete
	 * @param movemap
	 *            (이동시킬 맵을 설정한다 - 안타레이드)
	 */
	public static void capaspawn(int x, int y, short map, int random) {
		try {
			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(45464); // 세마
			npc.setId(ObjectIdFactory.getInstance().nextId());
			GeneralThreadPool.getInstance().execute(
					new BossSpawnThread(npc, x, y, map, 1000 * 60 * 50, random,
							0));
			L1NpcInstance npc2 = NpcTable.getInstance().newNpcInstance(45473); // 발터자르
			npc2.setId(ObjectIdFactory.getInstance().nextId());
			GeneralThreadPool.getInstance().execute(
					new BossSpawnThread(npc2, x, y, map, 1000 * 60 * 50,
							random, 0));
			L1NpcInstance npc3 = NpcTable.getInstance().newNpcInstance(45497); // 메르키오르
			npc3.setId(ObjectIdFactory.getInstance().nextId());
			GeneralThreadPool.getInstance().execute(
					new BossSpawnThread(npc3, x, y, map, 1000 * 60 * 50,
							random, 0));
		} catch (Exception e) {
		}
	}

	public static void baphospawn(int x, int y, int x2, int y2, short map,
			int random) {
		try {
			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(100085);
			npc.setId(ObjectIdFactory.getInstance().nextId());
			GeneralThreadPool.getInstance().execute(
					new BossSpawnThread(npc, x, y, map, 1000 * 60 * 50, random,
							0));
			L1NpcInstance npc2 = NpcTable.getInstance().newNpcInstance(100085);
			npc2.setId(ObjectIdFactory.getInstance().nextId());
			GeneralThreadPool.getInstance().execute(
					new BossSpawnThread(npc2, x2, y2, map, 1000 * 60 * 50,
							random, 0));
		} catch (Exception e) {
		}
	}

	public static void logofbossTime(L1NpcInstance npc) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO log_Boss_Time SET Name=?, Npc_ID=?, x=?, y=?, map=?, Spawn_Time=?");
			pstm.setString(1, npc.getName());
			pstm.setInt(2, npc.getNpcTemplate().get_npcId());
			pstm.setInt(3, npc.getX());
			pstm.setInt(4, npc.getY());
			pstm.setInt(5, npc.getMapId());
			Calendar cal = Calendar.getInstance();
			pstm.setTimestamp(6, new Timestamp(cal.getTimeInMillis()));
			pstm.executeUpdate();
		} catch (SQLException e) {
		} catch (Exception e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void bossspawn(int npcId, int x, int y, short map,
			int timeMillisToDelete, int type) {
		Random rnd = new Random(System.nanoTime());
		int chance = rnd.nextInt(100) + 1;
		int randomRange = 0;
		// 보스들 80% 확률로 뜨게하기
		if (type != 99 && type != 7 && type != 0 && type != 10 && type != 7626
				&& rnd.nextInt(100) + 1 > 80)
			return;
		// type 0 = 타락 12시간주기
		// type 1 = 12시기준 4시간주기
		try {
			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(npcId);
			npc.setId(ObjectIdFactory.getInstance().nextId());
			int tempx = x;
			int tempy = y;
			short tempm = map;
			int random = rnd.nextInt(10 * 60000);

			if (type == 7626)
				random = 0;

			if (type == 10)
				random = rnd.nextInt(20 * 60000);

			if (type == 99)
				random = 3;

			if (type == 0)// 3시간랜덤
				random = rnd.nextInt(3 * (60 * 60000));

			if (type == 6)// 6시간랜덤
				random = rnd.nextInt(6 * (60 * 60000));

			if (type == 7)
				random = rnd.nextInt((3 * (60 * 60000)) + (20 * 60000));

			if (npcId == 45488) {// 카파팸~~
				if (chance <= 50) {
					tempx = 32739;
					tempy = 32740;
					tempm = 810;
				}
				capaspawn(tempx, tempy, tempm, random);
			}

			if (npcId == 7280193 || npcId == 7200185) {// 데몬, 하딘의 분신
				if (chance <= 30) {
					tempx = 32715;
					tempy = 32827;
					// tempx=32730 + rnd.nextInt(78);
					// tempy=32789 + rnd.nextInt(88);
					tempm = 284;
				} else if (chance <= 60) {
					tempx = 32789;
					tempy = 32816;
					// tempx=32670 + rnd.nextInt(81);
					// tempy=32790 + rnd.nextInt(82);
					tempm = 283;
				} else {
					tempx = 32772;
					tempy = 32832;
					// tempx = 32729 + rnd.nextInt(82);
					// tempy = 32790 + rnd.nextInt(77);
					tempm = 282;
				}
				// randomRange = 10;
				randomRange = 2;
			}
			if (npcId == 45685) {// 상아탑 타락
				if (chance <= 30) {
					tempx = 32715;
					tempy = 32827;
					tempm = 289;
				} else if (chance <= 60) {
					tempx = 32789;
					tempy = 32816;
					tempm = 288;
				} else {
					tempx = 32772;
					tempy = 32832;
					tempm = 287;
				}
				randomRange = 2;
			}
			if (npcId == 7300168) { // 상아탑 미믹
				if (chance <= 20) {
					// tempx=32730 + rnd.nextInt(78);
					// tempy=32789 + rnd.nextInt(88);
					tempx = 32715;
					tempy = 32825;
					tempm = 284;
				} else if (chance <= 40) {
					// tempx=32670 + rnd.nextInt(81);
					// tempy=32790 + rnd.nextInt(82);
					tempx = 32787;
					tempy = 32819;
					tempm = 283;
				} else if (chance <= 60) {
					// tempx=32872 + rnd.nextInt(73);
					// tempy=32738 + rnd.nextInt(73);
					tempx = 32900;
					tempy = 32769;
					tempm = 280;
				} else if (chance <= 80) {
					// tempx=32731 + rnd.nextInt(86);
					// tempy=32789 + rnd.nextInt(86);
					tempx = 32773;
					tempy = 32833;
					tempm = 281;
				} else {
					// tempx = 32729 + rnd.nextInt(82);
					// tempy = 32790 + rnd.nextInt(77);
					tempx = 32774;
					tempy = 32834;
					tempm = 282;
				}
				randomRange = 40;
			}
			if (npcId == 45601) {// 데스나이트
				if (chance <= 30) {
					tempx = 32750;
					tempy = 32786;
					tempm = 812;
				} else if (chance <= 60) {
					tempx = 32774;
					tempy = 32785;
					tempm = 813;
				}
			}

			/*
			 * if(npcId==45529 || npcId == 100717){//드레이크 if(chance <= 25){
			 * tempx=33355; tempy=32353; }else if(chance <= 50){ tempx=33389;
			 * tempy=32336; }else if(chance <= 75){ tempx=33365; tempy=32384; }
			 * }
			 */
			if (npcId == 45516) {// 이프
				if (chance <= 25) {
					tempx = 33675;
					tempy = 32311;
				} else if (chance <= 50) {
					tempx = 33719;
					tempy = 32260;
				} else if (chance <= 75) {
					tempx = 33737;
					tempy = 32281;
				}
			}
			if (npcId == 45545) { // 흑장로
				if (chance <= 50) {
					tempx = 33271;
					tempy = 32394;
				}
			}
			if (npcId == 45573) { // 바포메트
				tempx = 32707;
				tempy = 32846;
				baphospawn(32795, 32798, 32759, 32886, (short) 2, random);
				/*
				 * if(chance <= 33){ tempx=32795; tempy=32798; baphospawn(32707,
				 * 32846, 32759, 32886, (short)2, random); }else if(chance <=
				 * 66){ tempx=32707; tempy=32846; baphospawn(32795, 32798,
				 * 32759, 32886, (short)2, random); }else{ tempx=32759;
				 * tempy=32886; baphospawn(32707, 32846, 32759, 32798, (short)2,
				 * random); }
				 */
			}
			if (npcId == 81175) {
				randomRange = 40;
			}
			if (npcId == 100338 || npcId == 100420)
				random = 0;
			// else if(npcId >= 45955 && npcId <= 45962)
			// random = rnd.nextInt(60000 * 220);
			// else if(npcId == 45752 || npcId == 45675 || npcId == 45625 ||
			// npcId == 45674 || npcId == 45685)
			// random = rnd.nextInt(60000 * 210);
			//

			// if(npcId == 45752 || npcId == 45675 || npcId == 45625 || npcId ==
			// 45674 || npcId == 45685)
			// random = 30;
			if (npcId == 45683 || npcId == 100814) {
				L1Location nl = new L1Location();
				nl.setMap(814);
				nl = L1Location.saburan(nl.getMap());
				tempx = nl.getX();
				tempy = nl.getY();
			}
			if (npcId == 100717) {
				int 드레이크랜덤 = rnd.nextInt(100);
				if (드레이크랜덤 <= 10) {
					GeneralThreadPool.getInstance().execute(
							new BossSpawnThread(npc, tempx, tempy, tempm,
									timeMillisToDelete, random, randomRange));
				}
			}
			GeneralThreadPool.getInstance().execute(
					new BossSpawnThread(npc, tempx, tempy, tempm,
							timeMillisToDelete, random, randomRange));

		} catch (Exception e) {
			e.printStackTrace();
		}
		rnd = null;
	}

	public static void spawn3(L1NpcInstance pc, int npcId, int randomRange,
			int timeMillisToDelete, boolean isUsePainwand) {
		try {
			L1NpcInstance npc = NpcTable.getInstance().newNpcInstance(npcId);
			npc.setId(ObjectIdFactory.getInstance().nextId());
			npc.setMap(pc.getMapId());

			if (randomRange == 0) {
				npc.getLocation().set(pc.getLocation());
				// npc.getLocation().forward(pc.getMoveState().getHeading());
			} else {
				int tryCount = 0;
				do {
					tryCount++;
					npc.setX(pc.getX() + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					npc.setY(pc.getY() + (int) (Math.random() * randomRange)
							- (int) (Math.random() * randomRange));
					if ((npc.getX() >= 32936 && npc.getY() <= 32945)
							&& (npc.getY() >= 32861 && npc.getY() <= 32870)
							&& npc.getMapId() == 410)
						continue;
					if (npc.getMap().isInMap(npc.getLocation())
							&& npc.getMap().isPassable(npc.getLocation())) {
						break;
					}
					Thread.sleep(1);
				} while (tryCount < 50);

				if (tryCount >= 50) {
					npc.getLocation().set(pc.getLocation());
					// npc.getLocation().forward(pc.getMoveState().getHeading());
				}
			}
			npc.setHomeX(npc.getX());
			npc.setHomeY(npc.getY());
			npc.getMoveState().setHeading(pc.getMoveState().getHeading());
			if (isUsePainwand) {
				if (npc instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) npc;
					mon.set_storeDroped((byte) 2);
				}
			}
			if (npcId == 4039004) {
				PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
						pc.getMapId());
				ar.셋사엘(npc);
			}
			if (npcId == 4039005) {
				PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
						pc.getMapId());
				ar.셋사엘2(npc);
			}
			L1World.getInstance().storeObject(npc);
			L1World.getInstance().addVisibleObject(npc);

			npc.getLight().turnOnOffLight();
			npc.startChat(L1NpcInstance.CHAT_TIMING_APPEARANCE); // 채팅 개시
			if (0 < timeMillisToDelete) {
				L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc,
						timeMillisToDelete);
				timer.begin();
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}
}
