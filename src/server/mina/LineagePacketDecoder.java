package server.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import server.LineageClient;

//import net.Logger;

public class LineagePacketDecoder implements ProtocolDecoder {

	@Override
	public synchronized void decode(IoSession session, IoBuffer buffer,
			ProtocolDecoderOutput out) {
		try {
			// ����ȭ �ؾ���
			LineageClient client = (LineageClient) session
					.getAttribute(LineageClient.CLIENT_KEY);
			// if(client!=null) client.encryptD(buffer);
			if (client != null) {
				int size = buffer.limit();
				if (size < 4096) {
					if (size > 4096)
						System.out.println("---------- ��Ŷ ������ 4096�̻� >> "
								+ client.getIp());
					byte[] data = buffer.array();
					client.pck.requestWork(data);
				} else {
					// ������ ���ٷ� �����ع���.
					System.out.println("������ ������ ���� :" + size);
					client.close();
				}
			}
		} catch (Exception e) {
			// Logger.getInstance().error(getClass().toString()+" decode(IoSession session, IoBuffer buffer, ProtocolDecoderOutput out)\r\n"+e.toString(),
			// Config.LOG.error);
		}
	}

	@Override
	public void dispose(IoSession client) throws Exception {

	}

	@Override
	public void finishDecode(IoSession client, ProtocolDecoderOutput output)
			throws Exception {

	}
}
