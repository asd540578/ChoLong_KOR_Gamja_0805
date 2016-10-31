package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;

public class S_PetWindow extends ServerBasePacket {

	private static final String S_PetWindow = "[S] S_PetWindow";
	private byte[] _byte = null;

	public S_PetWindow(int cnt, L1NpcInstance pet, boolean show) {
		buildPacket(cnt, pet, show);
	}

	/*
	 * 3c 0c 03 00 00 00 00 00 cf 5b 90 0f 04 00 00 00 95 82 50 80 01 24 32 36
	 * 39 32 00
	 */

	private void buildPacket(int cnt, L1NpcInstance pet, boolean show) {
		writeC(Opcodes.S_VOICE_CHAT);
		writeC(0x0c);
		if (show) {
			writeH(cnt);// ¼ø¼­
			writeD(0);
			writeD(pet.getId());// Æêid
			writeD(pet.getMapId());// ??
			writeH(pet.getX());// x
			writeH(pet.getY());// y
			if (pet instanceof L1PetInstance)
				writeC(1);
			else if (pet instanceof L1SummonInstance)
				writeC(0);
			writeS(pet.getName());// name
		} else {
			writeH(cnt);// ¼ø¼­
			writeD(1);
			writeD(pet.getId());// id
		}
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
		return S_PetWindow;
	}
}
