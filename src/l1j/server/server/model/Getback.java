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
package l1j.server.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.utils.SQLUtil;

public class Getback {

	private static Logger _log = Logger.getLogger(Getback.class.getName());

	private static Random _random = new Random(System.nanoTime());

	private static HashMap<Integer, ArrayList<Getback>> _getback = new HashMap<Integer, ArrayList<Getback>>();

	private int _areaX1;
	private int _areaY1;
	private int _areaX2;
	private int _areaY2;
	private int _areaMapId;
	private int _getbackX1;
	private int _getbackY1;
	private int _getbackX2;
	private int _getbackY2;
	private int _getbackX3;
	private int _getbackY3;
	private int _getbackMapId;
	private int _getbackTownId;
	private int _getbackTownIdForElf;
	private int _getbackTownIdForDarkelf;

	private Getback() {
	}

	private boolean isSpecifyArea() {
		return (_areaX1 != 0 && _areaY1 != 0 && _areaX2 != 0 && _areaY2 != 0);
	}

	public static void loadGetBack() {
		_getback.clear();
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			// �������� ������ ������ �������� ȥ���ϰ� ������(��), ������ ������ ���� �о���̱� ���� area_x1 DESC
			String sSQL = "SELECT * FROM getback ORDER BY area_mapid,area_x1 DESC ";
			pstm = con.prepareStatement(sSQL);
			rs = pstm.executeQuery();
			Getback getback = null;
			while (rs.next()) {
				getback = new Getback();
				getback._areaX1 = rs.getInt("area_x1");
				getback._areaY1 = rs.getInt("area_y1");
				getback._areaX2 = rs.getInt("area_x2");
				getback._areaY2 = rs.getInt("area_y2");
				getback._areaMapId = rs.getInt("area_mapid");
				getback._getbackX1 = rs.getInt("getback_x1");
				getback._getbackY1 = rs.getInt("getback_y1");
				getback._getbackX2 = rs.getInt("getback_x2");
				getback._getbackY2 = rs.getInt("getback_y2");
				getback._getbackX3 = rs.getInt("getback_x3");
				getback._getbackY3 = rs.getInt("getback_y3");
				getback._getbackMapId = rs.getInt("getback_mapid");
				getback._getbackTownId = rs.getInt("getback_townid");
				getback._getbackTownIdForElf = rs.getInt("getback_townid_elf");
				getback._getbackTownIdForDarkelf = rs
						.getInt("getback_townid_darkelf");
				rs.getBoolean("scrollescape");
				ArrayList<Getback> getbackList = _getback
						.get(getback._areaMapId);
				if (getbackList == null) {
					getbackList = new ArrayList<Getback>();
					_getback.put(getback._areaMapId, getbackList);
				}
				getbackList.add(getback);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, "could not Get Getback data", e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * pc�� �������κ��� ��ȯ ����Ʈ�� ����Ѵ�.
	 * 
	 * @param pc
	 * @param bScroll_Escape
	 *            (�̻��)
	 * @return locx, locy, mapid�� ������ �ݳ��ǰ� �ִ� �迭
	 */
	public static int[] GetBack_Location(L1PcInstance pc, boolean bScroll_Escape) {

		int[] loc = new int[3];
		try {
			int nPosition = _random.nextInt(3);

			int pcLocX = pc.getX();
			int pcLocY = pc.getY();
			int pcMapId = pc.getMapId();
			ArrayList<Getback> getbackList = _getback.get(pcMapId);

			if (pc.isInParty()) {
				if (pc.isDead()) {
					pc.getParty().refresh(pc);
				}
			}
			if (getbackList != null) {
				Getback getback = null;
				for (Getback gb : getbackList) {
					if (gb.isSpecifyArea()) {
						if (gb._areaX1 <= pcLocX && pcLocX <= gb._areaX2
								&& gb._areaY1 <= pcLocY && pcLocY <= gb._areaY2) {
							getback = gb;
							break;
						}
					} else {
						getback = gb;
						break;
					}
				}

				loc = ReadGetbackInfo(getback, nPosition);

				// town_id�� �����ǰ� �ִ� ���� �ű⿡ ��ȯ��Ų��
				if (pc.isElf() && getback._getbackTownIdForElf > 0) {
					loc = L1TownLocation
							.getGetBackLoc(getback._getbackTownIdForElf);
				} else if (pc.isDarkelf()
						&& getback._getbackTownIdForDarkelf > 0) {
					loc = L1TownLocation
							.getGetBackLoc(getback._getbackTownIdForDarkelf);
				} else if (getback._getbackTownId > 0) {
					loc = L1TownLocation.getGetBackLoc(getback._getbackTownId);
				}
				if (L1WorldMap.getInstance().getMap((short) loc[2])
						.isCloseZone(loc[0], loc[1])) {
					loc[0] = 33441;
					loc[1] = 32800;
					loc[2] = 4;
				}
			}
			// getback ���̺� �����Ͱ� ���� ���, SKT�� ��ȯ
			else {
				if (pc.getMapId() >= 1936 && pc.getMapId() <= 1940) {
					loc[0] = 33438;
					loc[1] = 32799;
					loc[2] = 4;
				} else if ((pc.getMapId() >= 9103 && pc.getMapId() <= 9200)) {
					loc[0] = 32573;
					loc[1] = 32940;
					loc[2] = 0;
				} else if ((pc.getMapId() >= 271 && pc.getMapId() <= 278)
						|| (pc.getMapId() >= 2102 && pc.getMapId() <= 2151)) {
					loc[0] = 34060;
					loc[1] = 32282;
					loc[2] = 4;
				} else if ((pc.getMapId() >= 1005 && pc.getMapId() <= 1010)
						|| (pc.getMapId() >= 1011 && pc.getMapId() <= 1016)
						|| (pc.getMapId() >= 10000 && pc.getMapId() <= 10005)) {
					loc[0] = 33719;
					loc[1] = 32506;
					loc[2] = 4;
				} else {
					loc[0] = 33441;
					loc[1] = 32800;
					loc[2] = 4;
				}
			}
		} catch (Exception e) {
			loc[0] = 33441;
			loc[1] = 32800;
			loc[2] = 4;
		}
		return loc;
	}

	public static int[] GetBack_Restart(L1PcInstance pc) {
		int[] loc = new int[3];

		try {
			loc = GetBack_Location(pc, true);

			if (loc[2] != 70) { // �ؼ��� �ƴҶ���
				if (pc.getClanid() != 0) { // ũ�� �Ҽ�
					int castle_id = 0;
					int house_id = 0;
					L1Clan clan = L1World.getInstance().getClan(
							pc.getClanname());
					if (clan != null) {
						castle_id = clan.getCastleId();
						house_id = clan.getHouseId();
					}
					if (castle_id != 0) { // ���� ũ����
						loc = L1CastleLocation.getCastleLoc(castle_id);
					} else if (house_id != 0) { // ����Ʈ ���� ũ����
						loc = L1HouseLocation.getHouseLoc(house_id);
					}
				}
			}
			return loc;
		} catch (Exception e) {
			/** 2011.07.31 ������ ���� ���� ���� */
			loc[0] = 33437;
			loc[1] = 32812;
			loc[2] = 4;
			return loc;
		}
	}

	private static int[] ReadGetbackInfo(Getback getback, int nPosition) {
		int[] loc = new int[3];
		switch (nPosition) {
		case 0:
			loc[0] = getback._getbackX1;
			loc[1] = getback._getbackY1;
			break;
		case 1:
			loc[0] = getback._getbackX2;
			loc[1] = getback._getbackY2;
			break;
		case 2:
			loc[0] = getback._getbackX3;
			loc[1] = getback._getbackY3;
			break;
		}
		loc[2] = getback._getbackMapId;

		return loc;
	}
}
