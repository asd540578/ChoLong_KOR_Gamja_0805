package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_Letter extends ServerBasePacket {

	private static final String S_LETTER = "[S] S_Letter";
	private byte[] _byte = null;

	public S_Letter(L1PcInstance pc, int type, int iden, int objid, String ����) {
		buildPacket(pc, type, iden, objid, ����);
	}

	private void buildPacket(L1PcInstance pc, int type, int iden, int objid,
			String ����) {
		writeC(Opcodes.S_MAIL_INFO);
		writeC(type); // 0:������ 1:���͸����� 2:������
		writeH(1);
		writeD(objid);
		writeC(iden); // Ȯ�ο��� 0:��Ȯ�� 1:Ȯ��
		writeD((int) (System.currentTimeMillis() / 1000));
		writeC(0x00);
		writeS(pc.getName());
		writeSS(����);
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
