package l1j.server.server.serverpackets;

import l1j.server.Config;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_CharMapTime extends ServerBasePacket {

	private static final String S_MAPTIMER = "[S] S_MapTimer";
	private byte[] _byte = null;
	private static final int RESTART_MAPUI = 803;
	private static int[] ������ = new int[] { 1, 2, 15, 41, 45, 100, 500 };
	private static int[] totaltime = new int[] { Config.������������ð�, 
			Config.���ž�߷������ð�, Config.���ž���������ð�, Config.�������������ð�, 
			Config.������õ���ǰ��ð�, Config.���������ð�, Config.PC�����ð� };

	public S_CharMapTime(L1PcInstance pc) {
		int[] usetime = new int[] { Config.������������ð�-pc.getgirantime(),
				Config.���ž�߷������ð�-pc.getivorytime(), 
				Config.���ž���������ð�-pc.getivoryyaheetime(),
				Config.�������������ð�-0,
				Config.������õ���ǰ��ð�-pc.get������õ����time(), 
				Config.���������ð�-pc.get������õ����time(), 
				Config.PC�����ð�-pc.getpc��time()
		};
		String[] name = new String[] { "$12125", "$6081", "$13527", "$20926", "$15586", "$23478", "$19375" };
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(RESTART_MAPUI);
		for (int i = 0; i < ������.length; i++) {
			writeC(0x0a);
			int size = 4 + writeLenght(������[i]) + writeLenght(name[i].getBytes().length) + name[i].getBytes().length +
					writeLenght(usetime[i]) + writeLenght(totaltime[i]);
			writeBit(size);
			writeC(0x08);
			writeBit(������[i]);
			writeC(0x12);
			writeC(name[i].getBytes().length); 
			writeByte(name[i].getBytes());
			writeC(0x18); // �����̿�ð�
			writeBit(usetime[i]);
			writeC(0x20);
			writeBit(totaltime[i]);
			
		}
		writeH(0);
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
		return S_MAPTIMER;
	}
}