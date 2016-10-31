package l1j.server.server.clientpackets;

import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import server.LineageClient;

public class C_BanParty extends ClientBasePacket {

	private static final String C_BAN_PARTY = "[C] C_BanParty";

	public C_BanParty(byte decrypt[], LineageClient client) throws Exception {
		super(decrypt);
		try {
			String s = readS();
			L1PcInstance pc = client.getActiveChar();
			if (!pc.getParty().isLeader(pc)) {
				S_ServerMessage sm = new S_ServerMessage(427);
				pc.sendPackets(sm); // 파티의 리더만을 추방할 수 있습니다.
				sm = null;
				return;
			}

			for (L1PcInstance member : pc.getParty().getMembers()) {
				if (member.getName().toLowerCase().equals(s.toLowerCase())) {
					pc.getParty().leaveMember(member);
					return;
				}
			}
			// 발견되지 않았다
			S_ServerMessage sm = new S_ServerMessage(426, s);
			pc.sendPackets(sm); // %0는 파티 멤버가 아닙니다.
			sm = null;
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_BAN_PARTY;
	}

}
