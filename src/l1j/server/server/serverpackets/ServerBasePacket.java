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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class ServerBasePacket {

	private int OpKey; // opcode Key

	private boolean isKey = true;
	private static Logger _log = Logger.getLogger(ServerBasePacket.class.getName());

	ByteArrayOutputStream _bao = new ByteArrayOutputStream();

	protected ServerBasePacket() {
	}

	public void clear() {
		try {
			_bao.reset();
			_bao.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_bao = null;
	}

	// Key
	private void setKey(int i) {
		OpKey = i;
	}

	public int bitlengh(int obj) {
		int length = 0;
		if (obj < 0) {
			BigInteger b = new BigInteger("18446744073709551615");
			while (BigInteger.valueOf(obj).and(b).shiftRight((length + 1) * 7).longValue() > 0) {
				length++;
			}
			length++;
		} else {
			if (obj <= 127) {
				length = 1;
			} else if (obj <= 16383) {
				length = 2;
			} else if (obj <= 2097151) {
				length = 3;
			} else if (obj <= 268435455) {
				length = 4;
			} else if ((long) obj <= 34359738367L) {
				length = 5;
			}
		}
		return length;
	}

	private int getKey() {
		return OpKey;
	}

	protected void writeD(int value) {
		_bao.write(value & 0xff);
		_bao.write(value >> 8 & 0xff);
		_bao.write(value >> 16 & 0xff);
		_bao.write(value >> 24 & 0xff);
	}

	protected void writeCH(int value) {
		_bao.write(value & 0xff);
		_bao.write(value >> 8 & 0xff);
		_bao.write(value >> 16 & 0xff);
	}

	protected void writeH(int value) {
		_bao.write(value & 0xff);
		_bao.write(value >> 8 & 0xff);
	}

	protected void writeC(int value) {

		if (isKey) {
			setKey(value);
			isKey = false;
		}
		_bao.write(value & 0xff);
		// ���ڵ� wirteC ù��° ȣ�⸸ ����...
	}

	protected void writeP(int value) {
		_bao.write(value);
	}

	protected void writeL(long value) {
		_bao.write((int) (value & 0xff));
	}

	protected void write4bit(int value) {
		if (value <= 127) {
			_bao.write(value & 0x7f);
		} else if (value <= 16383) {
			_bao.write(value & 0x7f | 0x80);
			_bao.write(value >> 7 & 0x7f);
		} else if (value <= 2097151) {
			_bao.write(value & 0x7f | 0x80);
			_bao.write(value >> 7 & 0x7f | 0x80);
			_bao.write(value >> 14 & 0x7f);
		} else if (value <= 268435455) {
			_bao.write(value & 0x7f | 0x80);
			_bao.write(value >> 7 & 0x7f | 0x80);
			_bao.write(value >> 14 & 0x7f | 0x80);
			_bao.write(value >> 21 & 0x7f);
		} else if ((long) value <= 34359738367L) {
			_bao.write(value & 0x7f | 0x80);
			_bao.write(value >> 7 & 0x7f | 0x80);
			_bao.write(value >> 14 & 0x7f | 0x80);
			_bao.write(value >> 21 & 0x7f | 0x80);
			_bao.write(value >> 28 & 0x7f);
		}
	}

	protected void write7B(long value) {
		int i = 0;
		BigInteger b = new BigInteger("18446744073709551615");

		while (BigInteger.valueOf(value).and(b).shiftRight((i + 1) * 7).longValue() > 0) {
			_bao.write(BigInteger.valueOf(value).and(b).shiftRight(7 * i++).remainder(BigInteger.valueOf(0x80))
					.or(BigInteger.valueOf(0x80)).intValue());
		}
		_bao.write(BigInteger.valueOf(value).and(b).shiftRight(7 * i++).remainder(BigInteger.valueOf(0x80)).intValue());
	}

	public int size7B(int obj) {
		int length = 0;
		if (obj < 0) {
			BigInteger b = new BigInteger("18446744073709551615");
			while (BigInteger.valueOf(obj).and(b).shiftRight((length + 1) * 7).longValue() > 0) {
				length++;
			}
			length++;
		} else {
			if (obj <= 127) {
				length = 1;
			} else if (obj <= 16383) {
				length = 2;
			} else if (obj <= 2097151) {
				length = 3;
			} else if (obj <= 268435455) {
				length = 4;
			} else if ((long) obj <= 34359738367L) {
				length = 5;
			}
		}
		return length;
	}

	protected void writeF(double org) {
		long value = Double.doubleToRawLongBits(org);
		_bao.write((int) (value & 0xff));
		_bao.write((int) (value >> 8 & 0xff));
		_bao.write((int) (value >> 16 & 0xff));
		_bao.write((int) (value >> 24 & 0xff));
		_bao.write((int) (value >> 32 & 0xff));
		_bao.write((int) (value >> 40 & 0xff));
		_bao.write((int) (value >> 48 & 0xff));
		_bao.write((int) (value >> 56 & 0xff));
	}

	protected void writeS2(String text) {
		try {
			if (text != null) {
				_bao.write(text.getBytes("EUC-KR"));
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	protected void writeS3(String text) {
		try {
			if (text != null && !text.isEmpty()) {
				byte[] name = text.getBytes("EUC-KR");
				_bao.write(name.length & 0xFF);
				if (name.length > 0) {
					_bao.write(name);
				}
			} else {
				_bao.write(0 & 0xff);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	protected void writeS(String text) {
		try {
			if (text != null) {
				_bao.write(text.getBytes("EUC-KR"));
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}

		_bao.write(0);
	}

	// ���ͺ�
	protected void writeBit(long value) {
		if (value < 0L) {
			String str = Integer.toBinaryString((int) value);
			value = Long.valueOf(str, 2).longValue();
		}
		int i = 0;
		while (value >> 7 * (i + 1) > 0L)
			this._bao.write((int) ((value >> 7 * i++) % 128L | 0x80));
		this._bao.write((int) ((value >> 7 * i) % 128L));
	}

	protected void writeBit(int value1, int value2) {
		String str1 = Integer.toBinaryString(value1);
		String str2 = Integer.toBinaryString(value2);
		if (value1 <= 32767)
			str1 = "0" + str1;
		String str3 = str2 + str1;
		writeBit(Long.valueOf(str3, 2).longValue());
	}

	public int writeLenght(long value) {
		if (value < 0L) {
			String stringValue = Integer.toBinaryString((int) value);
			value = Long.valueOf(stringValue, 2).longValue();
		}
		int size = 0;

		if (value <= 127L)
			size = 1;
		else if (value <= 16383L)
			size = 2;
		else if (value <= 2097151L)
			size = 3;
		else if (value <= 268435455L)
			size = 4;
		else if (value <= 34359738367L) {
			size = 5;
		}

		return size;
	}

	protected void writeSU16(String text) {
		try {
			if (text != null) {
				_bao.write(text.getBytes("UTF-16LE"));
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}

		_bao.write(0);
		_bao.write(0);
	}

	protected void writeSS(String text) {
		try {
			if (text != null) {
				byte[] test = text.getBytes("EUC-KR");
				for (int i = 0; i < test.length;) {
					if ((test[i] & 0xff) >= 0x7F) {
						/** �ѱ� **/
						_bao.write(test[i + 1]);
						_bao.write(test[i]);
						i += 2;
					} else {
						/** ����&���� **/
						_bao.write(test[i]);
						_bao.write(0);
						i += 1;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		_bao.write(0);
		_bao.write(0);
	}

	protected void writeK(int value) {
		int valueK = (int) (value / 128);
		if (valueK == 0) {
			_bao.write(value);
		} else if (valueK <= 127) {
			_bao.write((value & 0x7F) + 128);
			_bao.write(valueK);
		} else if (valueK <= 16383) {
			_bao.write((value & 0x7F) + 128);
			_bao.write((valueK & 0x7F) + 128);
			_bao.write(valueK / 128);
		} else if (valueK <= 2097151) {
			_bao.write((value & 0x7F) + 128);
			_bao.write((valueK & 0x7F) + 128);
			_bao.write(((valueK / 128) & 0x7F) + 128);
			_bao.write(valueK / 16384);
		} else {
			_bao.write((value & 0x7F) + 128);
			_bao.write((valueK & 0x7F) + 128);
			_bao.write(((valueK / 128) & 0x7F) + 128);
			_bao.write(((valueK / 16384) & 0x7F) + 128);
			_bao.write(valueK / 2097152);
		}
	}

	protected void writeByte(byte[] text) {
		try {
			if (text != null) {
				_bao.write(text);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	protected void writeB(byte[] data) {
		if (data != null) {
			_bao.write(data, 0, data.length);
		}
	}

	public int getLength() {
		return _bao.size() + 2;
	}

	public byte[] getBytes() {
		return _bao.toByteArray();
	}

	public abstract byte[] getContent() throws IOException;

	/**
	 * ���� ��Ŷ�� ������ ��Ÿ���� ĳ���� ������ �����ش�. ("[S] S_WhoAmount" �� )
	 */
	public String getType() {
		return "";
	}

	public String toString() {
		// getType() �� ������ "" �̶�� �� �ƴϸ� ��Ŷ�̸� + �ڵ尪 ���
		// [���ڵ�] ��Ŷ��
		String sTemp = getType().equals("") ? "" : "[" + getKey() + "] " + getType();
		return sTemp;
	}
}
