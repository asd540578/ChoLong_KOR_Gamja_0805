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
package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

public class S_MapID extends ServerBasePacket {

	private static final String S_MapID = "[S] S_MapID";

	public S_MapID(int mapid, boolean isUnderwater) {
		writeC(Opcodes.S_WORLD);
		if (mapid >= 1005 && mapid <= 1010)
			mapid = 1005;
		if (mapid >= 1011 && mapid <= 1016)
			mapid = 1011;
		if (mapid >= 10000 && mapid <= 10005)
			mapid = 1017;

		if (mapid >= 9001 && mapid <= 9099)
			mapid = 9000;// ÇÏµò
		if (mapid >= 9103 && mapid <= 9199)
			mapid = 9101;// ÇØ»óÀü
		if (mapid >= 2102 && mapid <= 2151)
			mapid = 2101;// ¾ó´ø
		if (mapid >= 10010 && mapid <= 10100)
			mapid = 1936;// Áß¾Ó»ç¿ø
		if (mapid >= 2601 && mapid <= 2698)
			mapid = 2600;// È­µÕ¾È½ÄÃ³
			/*
			 * if(mapid >= 5000 && mapid <= 5002) mapid = 25; if(mapid >= 5003
			 * && mapid <= 5005) mapid = 26; if(mapid >= 5006 && mapid <= 5008)
			 * mapid = 27; if(mapid >= 5009 && mapid <= 50011) mapid = 28;
			 */
		// if(mapid >= 5012 && mapid <= 5014) mapid = 2010;
		// if(mapid == 4){
		writeH(mapid);
		writeC(isUnderwater ? 1 : 0);
		writeC(isUnderwater ? 1 : 0);
		/*
		 * writeH(mapid); writeC(isUnderwater ? 1 : 0); writeC(0x10);
		 * writeC(0xb8); writeC(0xff); writeC(0xeb); writeC(0x01); writeC(0x10);
		 * writeH(0x00);
		 */
		/*
		 * }else{ writeH(mapid); writeC(isUnderwater ? 1 : 0); writeC(0x10);
		 * writeC(0xb8); writeC(0xff); writeC(0xeb); writeC(0x01); writeC(0x10);
		 * writeH(0x00);
		 * 
		 * writeD(0); writeD(0x00); }
		 */

		// 18 04 00 00
		// 10 b8 ff eb 01 10 00 00 ............

		/*
		 * writeC(0); writeC(0); writeC(0);
		 */
		/*
		 * writeC(0); writeH(0x00); writeH(0x08);
		 */
		// writeD(0);
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_MapID;
	}
}
