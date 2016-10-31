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
import l1j.server.server.model.L1Location;
import l1j.server.server.types.Point;

public class S_EffectLocation extends ServerBasePacket {

	private byte[] _byte = null;

	/**
	 * ������ ��ġ�� ȿ���� ǥ���ϴ� ��Ŷ�� �����Ѵ�.
	 * 
	 * @param pt
	 *            - ȿ���� ǥ���ϴ� ��ġ�� �ݳ��� Point ������Ʈ
	 * @param gfxId
	 *            - ǥ���ϴ� ȿ���� ID
	 */
	public S_EffectLocation(Point pt, short gfxId) {
		this(pt.getX(), pt.getY(), gfxId);
	}

	/**
	 * ������ ��ġ�� ȿ���� ǥ���ϴ� ��Ŷ�� �����Ѵ�.
	 * 
	 * @param loc
	 *            - ȿ���� ǥ���ϴ� ��ġ�� �ݳ��� L1Location ������Ʈ
	 * @param gfxId
	 *            - ǥ���ϴ� ȿ���� ID
	 */
	public S_EffectLocation(L1Location loc, short gfxId) {
		this(loc.getX(), loc.getY(), gfxId);
	}

	/**
	 * ������ ��ġ�� ȿ���� ǥ���ϴ� ��Ŷ�� �����Ѵ�.
	 * 
	 * @param x
	 *            - ȿ���� ǥ���ϴ� ��ġ�� X��ǥ
	 * @param y
	 *            - ȿ���� ǥ���ϴ� ��ġ�� Y��ǥ
	 * @param gfxId
	 *            - ǥ���ϴ� ȿ���� ID
	 */
	public S_EffectLocation(int x, int y, short gfxId) {
		writeC(Opcodes.S_EFFECT_LOC);
		writeH(x);
		writeH(y);
		writeH(gfxId);
		writeH(0);
	}

	public S_EffectLocation(int x, int y, int gfxId) {
		writeC(Opcodes.S_EFFECT_LOC);
		writeH(x);
		writeH(y);
		writeH(gfxId);
		writeH(0);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}

		return _byte;
	}

	@Override
	public String getType() {
		return "S_EffectLocation";
	}
}
