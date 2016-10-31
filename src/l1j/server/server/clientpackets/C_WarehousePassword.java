package l1j.server.server.clientpackets;

import java.util.logging.Logger;

import l1j.server.server.Account;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_RetrieveList;
import l1j.server.server.serverpackets.S_ServerMessage;
import server.LineageClient;

public class C_WarehousePassword extends ClientBasePacket {

	private static final String C_WhPw = "[C] C_WhPw";

	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(C_WarehousePassword.class
			.getName());

	/**
	 * (0e) (00) (0e 64 03) 00 (0e 64 03) 00 00 00 �� Ÿ�� ���� ����
	 */

	public C_WarehousePassword(byte[] data, LineageClient client) {
		super(data);
		try {
			L1PcInstance pc = client.getActiveChar();
			int gamepassword = client.getAccount().getGamePassword();
			int type = readC();
			if (type == 0) {
				/** ���� */
				int oldpass = readCH();
				readC(); // dummy
				int newpass = readCH();
				if (gamepassword == 0 || gamepassword == oldpass) {
					Account.setGamePassword(client, newpass);
				} else {
					S_ServerMessage sm = new S_ServerMessage(835);
					pc.sendPackets(sm, true);
				}
			} else if (type == 1) {
				/** â�� ã�� */
				int chkpass = readCH();
				readC(); // dummy
				int objId = readD();
				if (gamepassword == 0 || gamepassword == chkpass) {
					if (pc.getLevel() >= 5) {
						S_RetrieveList rl = new S_RetrieveList(objId, pc);
						pc.sendPackets(rl, true);
					}
				} else {
					S_ServerMessage sm = new S_ServerMessage(835);
					pc.sendPackets(sm, true);
				}
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	public String getType() {
		return C_WhPw;
	}
}