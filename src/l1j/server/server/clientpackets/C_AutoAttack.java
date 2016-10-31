package l1j.server.server.clientpackets;

import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import server.LineageClient;

public class C_AutoAttack extends ClientBasePacket {
	public C_AutoAttack(byte[] decrypt, LineageClient client) {
		super(decrypt);
		if (client.getActiveChar() == null)
			return;
		try {
			int targetId = readD();

			if (targetId == 0 || targetId == 0xFFFFFFFF) {
				client.getActiveChar().run = false;
			} else {
				L1Object target = L1World.getInstance().findObject(targetId);
				if (target == null)
					return;
				client.getActiveChar().target = target;
				if (client.getActiveChar().run)
					return;

				client.getActiveChar().run = true;
				client.getActiveChar().startatat();
			}
		} catch (Exception e) {
			client.getActiveChar().run = false;
		}
	}
}
