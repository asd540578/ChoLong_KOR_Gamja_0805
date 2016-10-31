package l1j.server.server.serverpackets;

import java.util.ArrayList;
import java.util.Collection;

import l1j.server.server.Opcodes;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_�����ֽ� extends ServerBasePacket {
	public static final String S_�����ֽ� = "[S] S_�����ֽ�";
	private byte[] _byte = null;

	public S_�����ֽ�(L1Clan clan, int type) {
		if (clan == null)
			return;
		buildPacket(clan, type);
	}

	private void buildPacket(L1Clan clan, int type) {
		writeC(Opcodes.S_PLEDGE_WATCH);
		writeH(type);
		if (type == 2) {
			if (clan == null) {
				writeD(0x00);
			} else {
				ArrayList<String> list = clan.getMarkSeeList();
				int size = list.size();
				writeD(size);
				if (size > 0) {
					for (int i = 0; i < size; i++) {
						writeS(list.get(i));
					}
				}
			}
		}
		writeH(0);
	}

	public S_�����ֽ�(L1PcInstance pc, int type, boolean on) {
		writeC(Opcodes.S_PLEDGE_WATCH);
		writeH(type);
		if (type == 2) {
			if (on) {
				Collection<L1Clan> list = L1World.getInstance().getAllClans();
				int size = list.size();
				writeD(size);
				if (size > 0) {
					for (L1Clan clan : list) {
						writeS(clan.getClanName());
					}
				}
			} else {
				if (pc.getClanid() > 0) {
					ArrayList<String> list = pc.getClan().getMarkSeeList();
					int size = list.size();
					writeD(size);
					if (size > 0) {
						for (int i = 0; i < size; i++) {
							writeS(list.get(i));
						}
					}
				} else {
					writeD(0x00);
				}
			}
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
		return S_�����ֽ�;
	}
}
