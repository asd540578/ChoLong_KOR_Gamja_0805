package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

public class S_UserCommands2 extends ServerBasePacket {

	private static final String S_UserCommands2 = "[C] S_UserCommands2";

	// private static Logger _log =
	// Logger.getLogger(S_UserCommands2.class.getName());

	private byte[] _byte = null;

	public S_UserCommands2(int number) {
		buildPacket(number);
	}

	private void buildPacket(int number) {
		writeC(Opcodes.S_BOARD_READ);
		writeD(number);// �ѹ�
		writeS(" ��� ");// �۾���?
		writeS(" ĳ���ͱ�ȯ ���� ");// ��¥?
		writeS("");// ����?
		writeS("\n === �ɸ��ͱ�ȯ ���� ===\n" + "\n" + " ĳ���ͱ�ȯǥ�� ĳ����\n"
				+ " �Ǹ��Ϸ��� ������ ǥ��\n" + " �����ϼž��մϴ�.\n" + " �ڱⷾ�� 70�̻��̶��.\n"
				+ " ���ĳ����ȯǥ�� ������\n" + " �����Ϸ��� �������� ��ȯâ\n" + " ���� �ø��ø�˴ϴ�.\n"
				+ " �����Ϸ��� ������ �ɸ��� \n" + " �����ϱ����� �ø���������\n"
				+ " �Ǹ����� â��� ����˴ϴ�.\n\n" + " �ŷ��������Ǹ� �����ñ�鼭\n"
				+ " ��ǥ���ø��ɸ��� ��������\n" + " �������� �Ѿ�� �˴ϴ�.\n" + " (���� �ɸ� �±�ȯ�� ����)");
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_UserCommands2;
	}
}
