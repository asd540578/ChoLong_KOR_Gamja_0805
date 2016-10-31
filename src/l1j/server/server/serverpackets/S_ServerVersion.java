package l1j.server.server.serverpackets;

import java.util.StringTokenizer;

import server.LineageClient;

public class S_ServerVersion extends ServerBasePacket {
	private static final String S_SERVER_VERSION = "[S] ServerVersion";
	public S_ServerVersion(LineageClient client) {

		// 160901 by feel.
		String s = "93 00 05 " + "EE 27 97 09 " + "EE 27 97 09 " + "7D D6 1B 78 " + "EE 27 97 09 " + "32 50 CF 57 "
				+ "00 00 00 " + "8B FD FF 34 ";

		StringTokenizer st = new StringTokenizer(s);

		while (st.hasMoreTokens()) {
			writeC(Integer.parseInt(st.nextToken(), 16));
		}
		int time = (int) (System.currentTimeMillis() / 1000);
		writeD(time);

		// 160901 by feel.
		String s2 = "FC CB 01 09 " + "DD C5 F5 08 " + "2C 52 91 09 " + "45 4E 91 09 " + "F4 84 91 09 ";
		st = new StringTokenizer(s2);
		while (st.hasMoreTokens()) {
			writeC(Integer.parseInt(st.nextToken(), 16));
		}

	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}

	@Override
	public String getType() {
		return S_SERVER_VERSION;
	}
}