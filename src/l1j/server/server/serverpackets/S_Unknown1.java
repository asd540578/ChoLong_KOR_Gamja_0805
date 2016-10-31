package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

public class S_Unknown1 extends ServerBasePacket {

	private static final String S_Unknown1 = "[S] S_Unknown1";

	public S_Unknown1() {
		writeC(Opcodes.S_ENTER_WORLD_CHECK);
		writeC(0x03);
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_Unknown1;
	}
}
