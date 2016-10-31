package l1j.server.server.serverpackets;

import l1j.server.Config;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_CharMapTime extends ServerBasePacket {

	private static final String S_MAPTIMER = "[S] S_MapTimer";
	private byte[] _byte = null;
	private static final int RESTART_MAPUI = 803;
	private static int[] 고정값 = new int[] { 1, 2, 15, 41, 45, 100, 500 };
	private static int[] totaltime = new int[] { Config.기란감옥던전시간, 
			Config.상아탑발록진영시간, Config.상아탑야히진영시간, Config.얼음수정동굴시간, 
			Config.수상한천상의계곡시간, Config.말섬던전시간, Config.PC정무시간 };

	public S_CharMapTime(L1PcInstance pc) {
		int[] usetime = new int[] { Config.기란감옥던전시간-pc.getgirantime(),
				Config.상아탑발록진영시간-pc.getivorytime(), 
				Config.상아탑야히진영시간-pc.getivoryyaheetime(),
				Config.얼음수정동굴시간-0,
				Config.수상한천상의계곡시간-pc.get수상한천상계곡time(), 
				Config.말섬던전시간-pc.get수상한천상계곡time(), 
				Config.PC정무시간-pc.getpc고무time()
		};
		String[] name = new String[] { "$12125", "$6081", "$13527", "$20926", "$15586", "$23478", "$19375" };
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(RESTART_MAPUI);
		for (int i = 0; i < 고정값.length; i++) {
			writeC(0x0a);
			int size = 4 + writeLenght(고정값[i]) + writeLenght(name[i].getBytes().length) + name[i].getBytes().length +
					writeLenght(usetime[i]) + writeLenght(totaltime[i]);
			writeBit(size);
			writeC(0x08);
			writeBit(고정값[i]);
			writeC(0x12);
			writeC(name[i].getBytes().length); 
			writeByte(name[i].getBytes());
			writeC(0x18); // 남은이용시간
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