package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_Letter extends ServerBasePacket {

	private static final String S_LETTER = "[S] S_Letter";
	private byte[] _byte = null;

	public S_Letter(L1PcInstance pc, int type, int iden, int objid, String 제목) {
		buildPacket(pc, type, iden, objid, 제목);
	}

	private void buildPacket(L1PcInstance pc, int type, int iden, int objid,
			String 제목) {
		writeC(Opcodes.S_MAIL_INFO);
		writeC(type); // 0:메일함 1:혈맹메일함 2:보관함
		writeH(1);
		writeD(objid);
		writeC(iden); // 확인여부 0:미확인 1:확인
		writeD((int) (System.currentTimeMillis() / 1000));
		writeC(0x00);
		writeS(pc.getName());
		writeSS(제목);
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
		return S_LETTER;
	}
}
