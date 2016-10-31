package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

public class S_UserCommands3 extends ServerBasePacket {

	private static final String S_UserCommands3 = "[C] S_UserCommands3";

	// private static Logger _log =
	// Logger.getLogger(S_UserCommands3.class.getName());

	private byte[] _byte = null;

	public S_UserCommands3(int number) {
		buildPacket(number);
	}

	private void buildPacket(int number) {
		writeC(Opcodes.S_BOARD_READ);
		writeD(number);// �ѹ�
		writeS(" ��� ");// �۾���?
		writeS(" ���� �ɼ� ���� ");// ��¥?
		writeS("");// ����?
		writeS("\n========[���� ����]==========\n" + "\n"
				+ "[���׺���:����10% ����           ]\n" + "[��Ƽ:Ac-2 �������+7       ]\n"
				+ "[������:��ƽ+4 ����5%      ]\n" + "[��̾�:��ƽ+4         ������   ]\n"
				+ "[��ť:�ߵ��ÿ�ƽ+15 ���:������Ÿ��ƽ:18]\n" + "[����,�þ�:�ߵ��� ��Ÿ +15  ]\n"
				+ "[��īƮ����:����+1  Ȱ��Ÿ+1  ]\n" + "[����:Ȯ���� ����15       ]\n"
				+ "[�ô�:�ߵ��� ��ȸ�� 70 ����]\n" + "[��������:�ٰŸ� ��Ÿ+2 ���ϳ���+10]\n"
				+ "[����ƺ�:��+50 ��+30 ����/Ȱ����+3]\n" + "\n\n"
				+ "=============================");
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_UserCommands3;
	}
}
