package l1j.server.server.TimeController;

import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.L1SpawnUtil;

public class DevilController extends Thread {

	private static DevilController _instance;

	private boolean _DevilStart;

	public boolean getDevilStart() {
		return _DevilStart;
	}

	public void setDevilStart(boolean Devil) {
		_DevilStart = Devil;
	}

	private static long sTime = 0;

	public boolean isGmOpen = false; // �߰�

	private String NowTime = "";

	// �ð� ����
	private static final int LOOP = 3;

	private static final SimpleDateFormat s = new SimpleDateFormat("HH",
			Locale.KOREA);

	private static final SimpleDateFormat ss = new SimpleDateFormat(
			"MM-dd HH:mm", Locale.KOREA);

	public static DevilController getInstance() {
		if (_instance == null) {
			_instance = new DevilController();
		}
		return _instance;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000);
				/** ���� * */
				if (!isOpen() && !isGmOpen)
					continue;
				if (L1World.getInstance().getAllPlayers().size() <= 0)
					continue;
                /**�ǿ��ð��������Ǿ�����**/
				L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "�Ǹ����� ���䰡 ���Ƚ��ϴ�!")); 
				//L1SpawnUtil.spawn2(33430, 32797, (short) 4, 4100039, 0, 3800000, 0);//����
				L1SpawnUtil.spawn2(33441, 32805, (short) 4, 777017, 0, 3800000, 0);//�ڷ����� 
				/**�ǿ��ð��������Ǿ�����**/
				isGmOpen = false;
				/** ���� �޼��� * */
			//	L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,"\\f3�Ǹ����� ���䰡 ���Ƚ��ϴ�."));
				/** �Ǹ��տ��� ����* */
				setDevilStart(true);

				/** ���� 1�ð� ����* */

				Thread.sleep(3800000L); // 3800000L 1�ð� 10������

				/** 1�ð� �� �ڵ� �ڷ���Ʈ* */
				TelePort();
				close(); //�߰�
				Thread.sleep(5000L);
				TelePort2();

				/** ���� * */
				End();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���� �ð��� �����´�
	 * 
	 * @return (Strind) ���� �ð�(MM-dd HH:mm)
	 */
	public String OpenTime() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(sTime);
		return ss.format(c.getTime());
	}

	/**
	 * ���䰡 �����ִ��� Ȯ��
	 * 
	 * @return (boolean) �����ִٸ� true �����ִٸ� false
	 */
	private boolean isOpen() {
		NowTime = getTime();
		if ((Integer.parseInt(NowTime) % LOOP) == 0)
			return true;
		return false;
	}
	
	

	/**
	 * ���� ����ð��� �����´�
	 * 
	 * @return (String) ���� �ð�(HH:mm)
	 */
	private String getTime() {
		return s.format(Calendar.getInstance().getTime());
	}

	/** �Ƶ������� �ñ��* */
	private void TelePort() {
		for (L1PcInstance c : L1World.getInstance().getAllPlayers()) {
			switch (c.getMap().getId()) {
			case 5167:
				c.stopHpRegenerationByDoll();
				c.stopMpRegenerationByDoll();
				L1Teleport.teleport(c, 33430, 32797, (short) 4, 4, true);
				c.sendPackets(new S_SystemMessage("�Ǹ����� ���䰡 �������ϴ�."));
				break;
			default:
				break;
			}
		}
	}
	/**ĳ���Ͱ� �׾��ٸ� �����Ű��**/
	 private void close() {
	  for(L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
	   if (pc.getMap().getId() == 5167 && pc.isDead()) {
	    pc.stopHpRegenerationByDoll();
	    pc.stopMpRegenerationByDoll();
	    pc.sendPackets(new S_Disconnect());
	   }
	  }
	 }

	/** �Ƶ������� �ñ��* */
	private void TelePort2() {
		for (L1PcInstance c : L1World.getInstance().getAllPlayers()) {
			switch (c.getMap().getId()) {
			case 5167:
				c.stopHpRegenerationByDoll();
				c.stopMpRegenerationByDoll();
				L1Teleport.teleport(c, 33430, 32797, (short) 4, 4, true);
				c.sendPackets(new S_SystemMessage("�Ǹ����� ���䰡 �������ϴ�."));
				break;
			default:
				break;
			}
		}
	}

	/** ���� * */
	private void End() {
		L1World.getInstance().broadcastServerMessage("�Ǹ����� ���䰡 ��������ϴ�.3�ð����� �����ϴ�.");
		setDevilStart(false);
	}
}
