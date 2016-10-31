package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

public class S_GMCommands extends ServerBasePacket {

	private static final String S_GMCommands = "[S] S_GMCommands";

	// private static Logger _log =
	// Logger.getLogger(S_GMCommands.class.getName());

	private byte[] _byte = null;

	public S_GMCommands(int number) {
		buildPacket(number);
	}

	private void buildPacket(int number) {
		writeC(Opcodes.S_BOARD_READ);
		writeD(number);// �ѹ�
		writeS("");// �۾���?
		writeS("��ڸ�ɾ�");// ��¥?
		writeS("");// ����?
		writeS(".���� .���� .û�� .�ӵ� .����\n" + ".��ȯ .��� .�̵� .��ġ .��Ȱ\n"
				+ ".���� .��ȯ .���� .�߹� .ų\n" + ".���� .���� .ä�� .���� .����\n"
				+ ".���� .�˻� .�˻� .���� .�ǹ�\n" + ".���� .�׼� .����.��ġ.����\n"
				+ ".�Ƶ���.��Ȱ .�ù���.��ü����\n" + ".���� .ä�� .�������� .���ε�\n"
				+ ".���� .���� .��ü���� .�̹���\n" + ".ȨŸ�� .ä��Ǯ�� .���� .��ų\n"
				+ ".������ .�� .����� .��Ƽ��ȯ\n\n" + ".��ų������.�����߹� .�ڵ� \n"
				+ ".������� .����.������� \n" + ".��ų������.���� .����Ʈ��\n"
				+ ".���λ���.�����з�.�κ��̹���\n" + ".�����߹�.���ڻ���\n" + ".���ε�Ʈ��.��������.��������\n"
				+ ".�ؼ�����.�ڵ�����.�ڸ�����");
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_GMCommands;
	}
}
