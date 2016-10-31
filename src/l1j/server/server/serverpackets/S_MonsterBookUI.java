package l1j.server.server.serverpackets;

import java.util.StringTokenizer;

import l1j.server.server.Opcodes;

public class S_MonsterBookUI extends ServerBasePacket {
	private byte[] _byte = null;
	public static final int MONSTER_BOOK = 0x022f;
	public static final int MONSTER_ACTION = 0x0230;
	
	
	public S_MonsterBookUI() { // 패킷 길이는 무조건 16입니다!!
		StringTokenizer dicst = new StringTokenizer(Dic);
		while (dicst.hasMoreTokens()) {
			writeC(Integer.parseInt(dicst.nextToken(), 16));
		}
	}
	
	private static final String Dic = "df 2a 03 0a e1 01 0a 55 12 10 08 01 10 9a 83 2c "
			+ "18 00 22 06 08 d8 87 01 10 01 12 11 08 02 10 81 " + "88 6e 18 c6 20 22 06 08 d8 87 01 10 02 12 12 08 "
			+ "03 10 84 a0 b8 03 18 9f 78 22 06 08 d8 87 01 10 " + "05 1a 0b 08 01 10 84 a0 b8 03 18 c1 87 01 1a 0b "
			+ "08 02 10 b5 bf f0 06 18 c0 87 01 20 37 12 2b 08 " + "00 18 01 22 0b 08 00 10 28 18 cb 02 20 6f 28 00 "
			+ "22 0b 08 01 10 28 18 d1 02 20 71 28 00 22 0b 08 " + "02 10 28 18 ce 02 20 70 28 00 12 2b 08 01 18 01 "
			+ "22 0b 08 00 10 28 18 e3 02 20 77 28 00 22 0b 08 " + "01 10 28 18 e6 02 20 78 28 00 22 0b 08 02 10 28 "
			+ "18 e9 02 20 79 28 00 12 2e 08 02 18 01 22 0c 08 " + "00 10 28 18 db 06 20 9f 02 28 00 22 0c 08 01 10 "
			+ "28 18 e1 06 20 a1 02 28 00 22 0c 08 02 10 28 18 " + "d8 06 20 9e 02 28 00 00 00 ";

	public S_MonsterBookUI(int value) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(value);
		switch (value) {
		case MONSTER_BOOK:
			writeC(8);
			writeC(0);
			writeC(16);
			writeC(0);
			writeH(0);
			break;
		case MONSTER_ACTION:
			writeC(8);
			writeC(0);
			writeC(16);
			writeC(0);
			for (int i = 1; i < 584; i++) {
				writeC(26);
				int j = writeLenght(i) + writeLenght(1) + 2;
				writeBit(j);
				writeC(8);
				writeBit(i);
				writeC(16);
				writeBit(1);
			}
			writeH(0);
			break;
		}
	}

	public S_MonsterBookUI(int[] array1, int[] array2) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(MONSTER_ACTION);
		writeC(8);
		writeC(0);
		writeC(16);
		writeC(0);
		for (int i = 0; i < array1.length; i++) {
			writeC(26);
			writeC(8);
			writeBit(array1[i]);
			writeC(16);
			writeBit(array2[i]);
		}
		writeH(0);
	}

	public S_MonsterBookUI(int value1, int value2) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(567);
		writeC(8);
		writeBit(value1);
		writeC(16);
		writeBit(value2);
		writeH(0);
	}

	public S_MonsterBookUI(boolean is1, int value1, boolean is2, int value2) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(1000);
		writeC(8);
		writeBit(value1);
		writeH(0);
	}

	public byte[] getContent() {
		if (this._byte == null)
			this._byte = getBytes();
		return this._byte;
	}

	public String getType() {
		return "[S] S_MonsterBookUI";
	}
}