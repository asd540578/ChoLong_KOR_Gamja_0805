package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

public class S_EventNotice extends ServerBasePacket {
	private static final String S_EventNotice = "[S] S_EventNotice";

	private byte[] _byte = null;

	public S_EventNotice() {

		String event = "http://g.lineage.power.plaync.com/wiki/";
		String event2 = "알람미사용.";

		int length = event.getBytes().length;
		int length2 = event2.getBytes().length;
		int time = (int) System.currentTimeMillis();
		if (time < 0){
			time = -(time);
		}
		int time2 = (int) System.currentTimeMillis();
		if (time2 < 0){
			time2 = -(time2);
		}
		int total = writeLenght(11) + writeLenght(length) + length + writeLenght(length2) + length2 + writeLenght(time)
				+ writeLenght(time2) + 5;

		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(141);
		writeC(8);
		writeC(1);
		writeC(16);
		writeC(1);

		writeC(26); // 0x1a
		writeBit(total);
		writeC(8);
		writeBit(11);
		writeC(26); // 0x1a
		writeBit(length);
		writeByte(event.getBytes());
		writeC(34); // 0x22
		writeBit(length2);
		writeByte(event2.getBytes());
		writeC(40); // 0x28
		writeBit(time);
		writeC(48); // 0x30
		writeBit(time2);
		writeC(0x3a);
		writeC(0x09);
		writeC(0x0a);
		writeC(0x04);
		writeC(0x34);
		writeC(0x36);
		writeC(0x35);
		writeC(0x34);
		writeC(0x10);
		writeC(0xe8);
		writeC(0x07);
		writeH(0x00);
	}
	
	public static int safeLongToInt(long l) {
	    int i = (int)l;
	    if ((long)i != l) {
	        throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
	    }
	    return i;
	}


	public byte[] getContent() {
		if (this._byte == null) {
			this._byte = this._bao.toByteArray();
		}
		return this._byte;
	}

	public String getType() {
		return S_EventNotice;
	}
}