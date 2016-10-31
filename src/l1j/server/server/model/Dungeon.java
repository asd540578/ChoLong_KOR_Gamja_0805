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
package l1j.server.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.GameList;
import l1j.server.GameSystem.NewTimeController;
import l1j.server.GameSystem.FireDragon.FireDragon;
import l1j.server.GameSystem.FireDragon.FireDragonSpawn;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;

// Referenced classes of package l1j.server.server.model:
// L1Teleport, L1PcInstance

public class Dungeon {

	private static Logger _log = Logger.getLogger(Dungeon.class.getName());
	private static Random _random = new Random(System.nanoTime());
	private static Dungeon _instance = null;

	private static Map<String, NewDungeon> _dungeonMap = new HashMap<String, NewDungeon>();

	private static Map<String, DungeonMap> _SrcDungeonMap = new HashMap<String, DungeonMap>();

	private enum DungeonType {
		NONE, SHIP_FOR_FI, SHIP_FOR_HEINE, SHIP_FOR_PI, SHIP_FOR_HIDDENDOCK, SHIP_FOR_GLUDIN, SHIP_FOR_TI
	};

	public static Dungeon getInstance() {
		if (_instance == null) {
			_instance = new Dungeon();
		}
		return _instance;
	}

	private Dungeon() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();

			pstm = con.prepareStatement("SELECT * FROM dungeon");
			rs = pstm.executeQuery();
			NewDungeon newDungeon = null;
			DungeonMap DM = null;
			while (rs.next()) {
				int srcMapId = rs.getInt("src_mapid");
				int srcX = rs.getInt("src_x");
				int srcY = rs.getInt("src_y");

				String key = new StringBuilder().append(srcMapId).append(srcX)
						.append(srcY).toString();

				int newX = rs.getInt("new_x");
				int newY = rs.getInt("new_y");
				int newMapId = rs.getInt("new_mapid");
				int heading = rs.getInt("new_heading");

				String key2 = new StringBuilder().append(srcMapId)
						.append(newMapId).toString();

				DungeonType dungeonType = DungeonType.NONE;
				if ((srcX >= 33423 && srcX <= 33426)
						&& srcY == 33502
						&& srcMapId == 4 // Heine������->FI���� ��
						|| (srcX >= 32733 && srcX <= 32736) && srcY == 32794
						&& srcMapId == 83) { // FI���� ��->Heine������
					dungeonType = DungeonType.SHIP_FOR_FI;
				} else if ((srcX >= 32935 && srcX <= 32937)
						&& srcY == 33058
						&& srcMapId == 1700 // FI������->Heine���� ��
						|| (srcX >= 32732 && srcX <= 32735) && srcY == 32796
						&& srcMapId == 84) { // Heine���� ��->FI������
					dungeonType = DungeonType.SHIP_FOR_HEINE;
				} else if ((srcX >= 32750 && srcX <= 32752)
						&& srcY == 32874
						&& srcMapId == 445 // ������ ������->���������� ��
						|| (srcX >= 32731 && srcX <= 32733) && srcY == 32796
						&& srcMapId == 447) { // ���������� ��->������ ������
					// System.out.println("�������� �ε�Ϸ�");
					dungeonType = DungeonType.SHIP_FOR_PI;
				} else if ((srcX >= 32296 && srcX <= 32298)
						&& srcY == 33087
						&& srcMapId == 440 // ������������->������ ���������� ��
						|| (srcX >= 32735 && srcX <= 32737) && srcY == 32794
						&& srcMapId == 446) { // ������ ���������� ��->������������
					dungeonType = DungeonType.SHIP_FOR_HIDDENDOCK;
				} else if ((srcX >= 32630 && srcX <= 32632)
						&& srcY == 32983
						&& srcMapId == 0 // TalkingIsland->TalkingIslandShiptoAdenMainland
						|| (srcX >= 32733 && srcX <= 32735) && srcY == 32796
						&& srcMapId == 5) { // TalkingIslandShiptoAdenMainland->TalkingIsland
					dungeonType = DungeonType.SHIP_FOR_GLUDIN;
				} else if ((srcX >= 32540 && srcX <= 32545)
						&& srcY == 32728
						&& srcMapId == 4 // AdenMainland->AdenMainlandShiptoTalkingIsland
						|| (srcX >= 32734 && srcX <= 32737) && srcY == 32794
						&& srcMapId == 6) { // AdenMainlandShiptoTalkingIsland->AdenMainland
					dungeonType = DungeonType.SHIP_FOR_TI;
				}
				newDungeon = new NewDungeon(newX, newY, (short) newMapId,
						heading, dungeonType);

				DM = new DungeonMap(srcX, srcY, (short) srcMapId);

				if (_dungeonMap.containsKey(key)) {
					_log.log(Level.WARNING, "���� Ű�� dungeon �����Ͱ� �ֽ��ϴ�. key="
							+ key);
				}
				_dungeonMap.put(key, newDungeon);
				_SrcDungeonMap.put(key2, DM);
			}
			// } catch (Exception e) {
			// e.printStackTrace();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static class DungeonMap {
		int _sx;
		int _sy;
		short _sm;

		private DungeonMap(int sx, int sy, short sm) {
			_sx = sx;
			_sy = sy;
			_sm = sm;
		}
	}

	private static class NewDungeon {
		int _newX;
		int _newY;
		short _newMapId;
		int _heading;
		DungeonType _dungeonType;

		private NewDungeon(int newX, int newY, short newMapId, int heading,
				DungeonType dungeonType) {
			_newX = newX;
			_newY = newY;
			_newMapId = newMapId;
			_heading = heading;
			_dungeonType = dungeonType;

		}
	}

	public L1Location dgloc(int smap, int fmap) {
		L1Location loc = null;
		loc = new L1Location();
		String key = new StringBuilder().append(smap).append(fmap).toString();
		if (_SrcDungeonMap.containsKey(key)) {
			DungeonMap Dungeon = _SrcDungeonMap.get(key);
			loc.setX(Dungeon._sx);
			loc.setY(Dungeon._sy);
			loc.setMap(Dungeon._sm);
		} else {
			return null;
		}
		key = null;
		return loc;
	}

	private String isAccount���尡�ɿ���(Timestamp accountday, int outtime,
			int usetime) {
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		String end = "�Ұ���";
		String ok = "���尡��";
		String start = "�ʱ�ȭ";
		if (accountday != null) {
			long clac = nowday.getTime() - accountday.getTime();
			int hours = nowday.getHours();
			int lasthours = accountday.getHours();

			if (accountday.getDate() != nowday.getDate()) {
				if (clac > 86400000 || hours >= Config.D_Reset_Time
						|| lasthours < Config.D_Reset_Time) {// 24�ð��� �����ų�
																// ����9�����Ķ��
					return start;
				}
			} else {
				if (lasthours < Config.D_Reset_Time
						&& hours >= Config.D_Reset_Time) {// ������ 9�������� ����üũ
					return start;
				}
			}
			if (outtime <= usetime) {
				return end;// ��λ��
			} else {
				return ok;
			}
		} else {
			return start;
		}
	}

	private String isPC���尡�ɿ���(Timestamp accountday, int outtime, int usetime) {
		Timestamp nowday = new Timestamp(System.currentTimeMillis());
		String end = "�Ұ���";
		String ok = "���尡��";
		String start = "�ʱ�ȭ";
		if (accountday != null) {
			long clac = nowday.getTime() - accountday.getTime();

			int hours = nowday.getHours();
			int lasthours = accountday.getHours();

			if (accountday.getDate() != nowday.getDate()) {
				// System.out.println(nowday.getHours());
				if (clac > 86400000 || hours >= Config.D_Reset_Time
						|| lasthours < Config.D_Reset_Time) {// 24�ð��� �����ų�
																// ����9�����Ķ��
					return start;
				}
			} else {
				if (lasthours < Config.D_Reset_Time
						&& hours >= Config.D_Reset_Time) {// ������ 9�������� ����üũ
					return start;
				}
			}
			if (outtime <= usetime) {
				return end;// ��λ��
			} else {
				return ok;
			}
		} else {
			return start;
		}
	}

	public boolean dg(int locX, int locY, int mapId, L1PcInstance pc) {
		try {

			long servertime = GameTimeClock.getInstance().getGameTime()
					.getSeconds();
			// servertime += 240;
			long nowtime = servertime % 86400;

			/*
			 * if(nowtime < 0){ nowtime=nowtime-nowtime-nowtime; }
			 */

			String key = new StringBuilder().append(mapId).append(locX)
					.append(locY).toString();
			if (_dungeonMap.containsKey(key)) {
				int xdis = Math.abs(pc.getX() - locX);
				int ydis = Math.abs(pc.getY() - locY);
				if (ydis > 3 || xdis > 3) {
					pc.sendPackets(new S_SystemMessage("�߸��� ���� �Դϴ�."));
					return false;
				}

				NewDungeon newDungeon = _dungeonMap.get(key);
				short newMap = newDungeon._newMapId;
				int newX = newDungeon._newX;
				int newY = newDungeon._newY;
				int heading = newDungeon._heading;
				DungeonType dungeonType = newDungeon._dungeonType;
				boolean teleportable = false;
				key = null;
				if (dungeonType == DungeonType.NONE) {
					teleportable = true;
				} else {
					// System.out.println("nowtime : "+nowtime);
					if (nowtime >= 15 * 360
							&& nowtime < 25 * 360 // 1.30~2.30
							|| nowtime >= 45 * 360
							&& nowtime < 55 * 360 // 4.30~5.30
							|| nowtime >= 75 * 360
							&& nowtime < 85 * 360 // 7.30~8.30
							|| nowtime >= 105 * 360
							&& nowtime < 115 * 360 // 10.30~11.30
							|| nowtime >= 135 * 360
							&& nowtime < 145 * 360
							|| nowtime >= 165 * 360
							&& nowtime < 175 * 360
							|| nowtime >= 195 * 360
							&& nowtime < 205 * 360
							|| nowtime >= 225 * 360
							&& nowtime < 235 * 360

							|| nowtime <= -15 * 360
							&& nowtime > -25 * 360 // 1.30~2.30
							|| nowtime <= -45 * 360
							&& nowtime > -55 * 360 // 4.30~5.30
							|| nowtime <= -75 * 360
							&& nowtime > -85 * 360 // 7.30~8.30
							|| nowtime <= -105 * 360
							&& nowtime > -115 * 360 // 10.30~11.30
							|| nowtime <= -135 * 360 && nowtime > -145 * 360
							|| nowtime <= -165 * 360 && nowtime > -175 * 360
							|| nowtime <= -195 * 360 && nowtime > -205 * 360
							|| nowtime <= -225 * 360 && nowtime > -235 * 360) {
						if ((pc.getInventory().checkItem(40299, 1) && dungeonType == DungeonType.SHIP_FOR_GLUDIN) // TalkingIslandShiptoAdenMainland
								|| (pc.getInventory().checkItem(40301, 1) && dungeonType == DungeonType.SHIP_FOR_HEINE) // AdenMainlandShiptoForgottenIsland
								|| (pc.getInventory().checkItem(40302, 1) && dungeonType == DungeonType.SHIP_FOR_PI)) { // ShipPirateislandtoHiddendock
							teleportable = true;
						}
					} else if (nowtime >= 0
							&& nowtime < 10 * 360// 00:00~01:00
							|| nowtime >= 30 * 360 && nowtime < 40 * 360
							|| nowtime >= 60 * 360 && nowtime < 70 * 360
							|| nowtime >= 90 * 360 && nowtime < 100 * 360
							|| nowtime >= 120 * 360
							&& nowtime < 130 * 360
							|| nowtime >= 150 * 360
							&& nowtime < 160 * 360
							|| nowtime >= 180 * 360
							&& nowtime < 190 * 360
							|| nowtime >= 210 * 360
							&& nowtime < 220 * 360

							|| nowtime <= -0
							&& nowtime > -10 * 360// 00:00~01:00
							|| nowtime <= -30 * 360 && nowtime > -40 * 360
							|| nowtime <= -60 * 360 && nowtime > -70 * 360
							|| nowtime <= -90 * 360 && nowtime > -100 * 360
							|| nowtime <= -120 * 360 && nowtime > -130 * 360
							|| nowtime <= -150 * 360 && nowtime > -160 * 360
							|| nowtime <= -180 * 360 && nowtime > -190 * 360
							|| nowtime <= -210 * 360 && nowtime > -220 * 360

					) {
						if ((pc.getInventory().checkItem(40298, 1) && dungeonType == DungeonType.SHIP_FOR_TI) // AdenMainlandShiptoTalkingIsland
								|| (pc.getInventory().checkItem(40300, 1) && dungeonType == DungeonType.SHIP_FOR_FI) // ForgottenIslandShiptoAdenMainland
								|| (pc.getInventory().checkItem(40303, 1) && dungeonType == DungeonType.SHIP_FOR_HIDDENDOCK)) { // ShipHiddendocktoPirateisland
							teleportable = true;
						}
					}
				}

				if (teleportable) {
					if (pc instanceof L1RobotInstance) {
						pc.dx = newX;
						pc.dy = newY;
						pc.dm = newMap;
						pc.dh = heading;
						return true;
					}
					/*
					 * if(mapId > newMap && mapId != 494 && mapId != 496 &&
					 * mapId != 478 && mapId != 477 && mapId != 495 && mapId !=
					 * 493 && mapId != 476){ // ��� �ڷ� ������ if((mapId >= 451 &&
					 * mapId <= 478) || (mapId >= 490 && mapId <= 496) || (mapId
					 * >= 530 && mapId <= 535)) return false; }
					 */
					/*
					 * if(newMap == 531){ NewLadun.getInstance().�̵�üũ(1); }
					 * if(newMap == 533){ NewLadun.getInstance().�̵�üũ(2); }
					 */
					if (newMap == 451) {
						Random random = new Random();
						int gn1 = random.nextInt(3);
						int time = 0;
						int outtime = 7200;
						if (pc.getravaday() == null) {
							pc.setravatime(1);
							// pc.setravaday(nowday);
							pc.setravaday((Timestamp) NewTimeController.get().lb_time
									.clone());
							try {
								pc.save();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pc.sendPackets(new S_ServerMessage(1526, "2"));// 3
																			// �ð�
																			// ���Ҵ�.
							pc.dx = 32730 + gn1;
							pc.dy = 32846 + gn1;
							pc.dm = 451;
							pc.dh = 5;
						} else {
							time = pc.getravatime();
							/*
							 * if (pc.getravaday().getDate() !=
							 * nowday.getDate()){ pc.setravatime(1); try {
							 * pc.save(); } catch (Exception e) { // TODO
							 * Auto-generated catch block e.printStackTrace(); }
							 * pc.sendPackets(new S_ServerMessage(1526, "5"));//
							 * 3�ð� ���Ҵ�. pc.dx=32730 + gn1; pc.dy=32846 + gn1;
							 * pc.dm=451; pc.dh=5; }else
							 */if (outtime > time) {
								int h = (outtime - time) / 60 / 60;
								if (h < 0) {
									h = 0;
								}
								int m = (outtime - time) / 60 % 60;
								if (m < 0) {
									m = 0;
								}

								if (h > 0) {
									pc.sendPackets(new S_ServerMessage(1525, h
											+ "", m + ""));// %�ð� %�г��ҽ��ϴ�.
								} else {
									pc.sendPackets(new S_ServerMessage(1527, ""
											+ m + ""));// �� ���Ҵ�.
								}
								pc.dx = 32730 + gn1;
								pc.dy = 32846 + gn1;
								pc.dm = 451;
								pc.dh = 5;
							} else {
								pc.sendPackets(new S_ServerMessage(1522, "2"));// 5�ð�
																				// ���
																				// ����ߴ�.
								return false;
							}
						}
					} else 
						
						
			
						
						if (newMap >= 53 && newMap <= 56)
						 {// �������
						int outtime = 60 * 60 * 3;
						int usetime = pc.getgirantime();
						Timestamp nowday = new Timestamp(
								System.currentTimeMillis());
						if (pc.getgiranday() == null) {
							try {
								pc.setgirantime(1);
								pc.setgiranday(nowday);
								pc.save();
								pc.sendPackets(new S_ServerMessage(1526, "3"));// 3
																				// �ð�
																				// ���Ҵ�.
							} catch (Exception e) {
							}
						} else {
							try {
								String s1 = isPC���尡�ɿ���(pc.getgiranday(),
										outtime, usetime);
								if (s1.equals("���尡��")) {// ���尡��
									int h = (outtime - usetime) / 60 / 60;
									if (h < 0) {
										h = 0;
									}
									int m = (outtime - usetime) / 60 % 60;
									if (m < 0) {
										m = 0;
									}
									if (h > 0) {
										pc.sendPackets(new S_ServerMessage(
												1525, h + "", m + ""));// %�ð�
																		// %�г��ҽ��ϴ�.
									} else {
										pc.sendPackets(new S_ServerMessage(
												1527, "" + m + ""));// �� ���Ҵ�
									}
								} else if (s1.equals("�Ұ���")) {// ����Ұ���
									pc.sendPackets(new S_ServerMessage(1522,
											"3"));// 5�ð� ��� ����ߴ�.
								} else if (s1.equals("�ʱ�ȭ")) {// �ʱ�ȭ
									pc.setgirantime(1);
									pc.setgiranday(nowday);
									pc.sendPackets(new S_ServerMessage(1526,
											"3"));// �ð� ���Ҵ�.
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else 
						
						
						if 
							 (newMap >= 777 && newMap <= 779) {
						Timestamp nowday = new Timestamp(
								System.currentTimeMillis());
						try {
							int outtime = Config.����_���ǵ���_�ð�;
							int usetime = pc.get���time();

							String s2 = isAccount���尡�ɿ���(pc.get���day(), outtime,
									usetime);
							if (s2.equals("���尡��")) {// ���尡��
								int h = (outtime - usetime) / 60 / 60;
								if (h < 0) {
									h = 0;
								}
								int m = (outtime - usetime) / 60 % 60;
								if (m < 0) {
									m = 0;
								}
								if (h > 0) {
									pc.sendPackets(new S_SystemMessage(
											"���� �ð� : ���� ���� �ð� " + h + "�ð� " + m
													+ "�� ����"), true);
								} else {
									pc.sendPackets(new S_SystemMessage(
											"���� �ð� : ���� ���� �ð� " + m + "�� ����"),
											true);
								}
							} else if (s2.equals("�Ұ���")) {// ����Ұ���
								pc.sendPackets(new S_SystemMessage(
										"���� �ð� : ���� ���� �ð� 6�ð� ��� ���"), true);
							} else if (s2.equals("�ʱ�ȭ")) {// �ʱ�ȭ
								pc.set���time(1);
								pc.set���day(nowday);
								pc.sendPackets(new S_SystemMessage(
										"���� �ð� : ���� ���� �ð� 6�ð� ����"), true);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						int outtime = Config.PC_���ǵ���_�ð�;
						int usetime = pc.getpc���time();

						if (pc.getpc���day() == null) {
							try {
								pc.setpc���time(1);
								pc.setpc���day(nowday);
								pc.save();
								pc.sendPackets(new S_ServerMessage(1526, "3"));// 3
																				// �ð�
																				// ���Ҵ�.
							} catch (Exception e) {
							}
						} else {
							try {
								String s1 = isPC���尡�ɿ���(pc.getpc���day(),
										outtime, usetime);
								if (s1.equals("���尡��")) {// ���尡��
									int h = (outtime - usetime) / 60 / 60;
									if (h < 0) {
										h = 0;
									}
									int m = (outtime - usetime) / 60 % 60;
									if (m < 0) {
										m = 0;
									}
									if (h > 0) {
										pc.sendPackets(new S_ServerMessage(
												1525, h + "", m + ""));// %�ð�
																		// %�г��ҽ��ϴ�.
									} else {
										pc.sendPackets(new S_ServerMessage(
												1527, "" + m + ""));// �� ���Ҵ�
									}
								} else if (s1.equals("�Ұ���")) {// ����Ұ���
									pc.sendPackets(new S_ServerMessage(1522,
											"3"));// 5�ð� ��� ����ߴ�.
								} else if (s1.equals("�ʱ�ȭ")) {// �ʱ�ȭ
									pc.setpc���time(1);
									pc.setpc���day(nowday);
									pc.sendPackets(new S_ServerMessage(1526,
											"3"));// �ð� ���Ҵ�.
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
					if (teleportable) {
						boolean castleck = false;
						if (newMap == 15 || newMap == 29 || newMap == 260
								|| newMap == 52 || newMap == 64
								|| newMap == 300 || newMap == 330) {
							if (!pc.isGm() && !pc.isGhost()) {
								if (pc.getClanid() > 0) {
									L1Clan clan = pc.getClan();
									int castle_id = clan.getCastleId();
									if (castle_id > 0) {
										if (L1CastleLocation
												.getCastleIdByInMap(castle_id) == newMap) {
											castleck = true;
										}
									}
								}
								if (!castleck) {
									for (L1Clan clan : L1World.getInstance()
											.getAllClans()) {
										if (clan == null
												|| clan.getCastleId() == 0)
											continue;
										if (L1CastleLocation
												.getCastleIdByInMap(clan
														.getCastleId()) == newMap) {
											S_ChatPacket s_chatpacket = new S_ChatPacket(
													"������", "������ ������ ħ���Ͽ����ϴ�!",
													17, true);
											for (L1PcInstance cpc : clan
													.getOnlineClanMember()) {
												if (cpc != null)
													cpc.sendPackets(s_chatpacket);
											}
											s_chatpacket = null;
											break;
										}
									}
								}
							}
						}
						pc.dx = newX;
						pc.dy = newY;
						pc.dm = newMap;
						pc.dh = heading;
						return true;
					}

				}
			} else {
				/*
				 * if(mapId == 492){ if(((locX >= 32852 && locX <= 32853) &&
				 * (locY >= 32860 && locY <= 32861)) || ((locX >= 32857 && locX
				 * <= 32858) && (locY >= 32860 && locY <= 32861)) || ((locX >=
				 * 32857 && locX <= 32858) && (locY >= 32865 && locY <= 32866))
				 * || ((locX >= 32852 && locX <= 32853) && (locY >= 32865 &&
				 * locY <= 32866))){ pc.dx=32665; pc.dy=32853; pc.dm=457;
				 * pc.dh=4; return true; } }else{
				 */
				int xdis = Math.abs(pc.getX() - locX);
				int ydis = Math.abs(pc.getY() - locY);
				if (ydis > 3 || xdis > 3) {
					pc.sendPackets(new S_SystemMessage("�߸��� ���� �Դϴ�."));
					return false;
				}
				/*
				 * if(mapId == 410){ if((locX >= 32930 && locX <= 32931) &&
				 * (locY >= 32994 && locY <= 32995)){ pc.dx=32825; pc.dy=33001;
				 * pc.dm=400; pc.dh=6; return true; } }else
				 */if (mapId == 400) {
					if ((locX >= 32826 && locX <= 32827)
							&& (locY >= 33000 && locY <= 33001)) {
						pc.dx = 32929;
						pc.dy = 32995;
						pc.dm = 410;
						pc.dh = 6;
						return true;
					}
				} else if (mapId >= 2600 && mapId <= 2698) {
					FireDragon fd = null;
					synchronized (GameList.FDList) {
						fd = GameList.getFD(mapId);
					}
					if (fd == null) {
						return false;
					}
					if (locX == 32753 && (locY >= 32985 && locY <= 32986)) {
						pc.dx = 32833;
						pc.dy = 32757;
						pc.dm = (short) mapId;
						pc.dh = 3;

						int rnd = _random.nextInt(100);

						int tt = 4;// �Ǵн�
						if (rnd < 20) {
							tt = 6;// �߶�ī��
						} else if (rnd < 60) {
							tt = 5;// ������Ʈ
						} else {
							tt = 4;// �Ǵн�
						}
						// ������Ʈ 40 �Ǵн� 40 �߶�ī�� 20
						FireDragonSpawn.getInstance().Spawn(pc, mapId, tt);
						return true;
					}
				}
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}
}
