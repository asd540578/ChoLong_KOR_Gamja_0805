package l1j.server.server.serverpackets;

public class KeyPacket extends ServerBasePacket {
	private byte[] _byte = null;

	public KeyPacket() {
		  byte[] _byte1 = {
					(byte) 0x4F, 
				        (byte) 0x3E, (byte) 0x62, (byte) 0x79, (byte) 0x74, 
				        (byte) 0xC4, (byte) 0xFA, (byte) 0x19, (byte) 0x35}; 
	for (int i = 0; i < _byte1.length; i++) {
				writeC(_byte1[i]);
			}
		}
	private static String HexToDex(int data, int digits) {
		String number = Integer.toHexString(data);
		for (int i = number.length(); i < digits; i++)
			number = "0" + number;
		return number;
	}

	public static String DataToPacket(byte[] data, int len) {
		StringBuffer result = new StringBuffer();
		int counter = 0;
		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				result.append(HexToDex(i, 4) + ": ");
			}
			result.append(HexToDex(data[i] & 0xff, 2) + " ");
			counter++;
			if (counter == 16) {
				result.append("   ");
				int charpoint = i - 15;
				for (int a = 0; a < 16; a++) {
					int t1 = data[charpoint++];
					if (t1 > 0x1f && t1 < 0x80) {
						result.append((char) t1);
					} else {
						result.append('.');
					}
				}
				result.append("\n");
				counter = 0;
			}
		}
		int rest = data.length % 16;
		if (rest > 0) {
			for (int i = 0; i < 17 - rest; i++) {
				result.append("   ");
			}
			int charpoint = data.length - rest;
			for (int a = 0; a < rest; a++) {
				int t1 = data[charpoint++];
				if (t1 > 0x1f && t1 < 0x80) {
					result.append((char) t1);
				} else {
					result.append('.');
				}
			}
			result.append("\n");
		}
		return result.toString();
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}
}