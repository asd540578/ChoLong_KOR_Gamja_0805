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

public class �ؼ�Controller extends Thread {

	private static �ؼ�Controller _instance;

	private boolean _�ؼ�Start;

	public boolean get�ؼ�Start() {
		return _�ؼ�Start;
	}

	public void set�ؼ�Start(boolean �ؼ�) {
		_�ؼ�Start = �ؼ�;
	}

	private static long sTime = 0;

	public boolean isGmOpen = false; // �߰�

	private String NowTime = "";

	private static int LOOP = 3;    //6  

	 private static final SimpleDateFormat s = new SimpleDateFormat("HH",
	   Locale.KOREA);

	 private static final SimpleDateFormat ss = new SimpleDateFormat(
	   "MM-dd HH:mm", Locale.KOREA);

	 public static �ؼ�Controller getInstance() {
	  if (_instance == null) {
	   _instance = new �ؼ�Controller();
	  }
	  return _instance;
	 }
	 
	 public �ؼ�Controller(){
	  LOOP = 3;   //  6           
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
	                /**�ؼ��ð��������Ǿ�����**/
	    L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "���������� ���Ƚ��ϴ�")); 
	    L1SpawnUtil.spawn2(33437, 32811, (short) 4, 777018, 0, 3800000, 0);//�ڷ�����   >> �ؼ� �ڷ����Ͱ� ����� ��ġ
	    /**�ؼ��ð��������Ǿ�����**/
	    isGmOpen = false;
	    /** ���� �޼��� * */
	    L1World.getInstance().broadcastServerMessage("\\aH ������ �����ΰ��� ���� ���Ƚ��ϴ�.");
	    L1World.getInstance().broadcastServerMessage("\\aH ������ �� : 1�ð� ��ɰ���.");
	    /** �ؼ� ����* */
	    set�ؼ�Start(true);

	    /** ���� 1�ð� ����* */

	    Thread.sleep(3800000L); // 3800000L 1�ð� 10������  >1�ð� 10�е� ��� ������ ��

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
	   case 1700:
	    c.stopHpRegenerationByDoll();
	    c.stopMpRegenerationByDoll();
	    L1Teleport.teleport(c, 33430, 32797, (short) 4, 4, true);
	    c.sendPackets(new S_SystemMessage("���������� �������ϴ�"));
	    break;
	   default:
	    break;
	   }
	  }
	 }
	 /**ĳ���Ͱ� �׾��ٸ� �����Ű��**/
	  private void close() {
	   for(L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
	    if (pc.getMap().getId() == 1700 && pc.isDead()) {
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
	   case 1700:
	    c.stopHpRegenerationByDoll();
	    c.stopMpRegenerationByDoll();
	    L1Teleport.teleport(c, 33430, 32797, (short) 4, 4, true);
	    c.sendPackets(new S_SystemMessage("���������� �������ϴ�"));
	    break;
	   default:
	    break;
	   }
	  }
	 }

	 /** ���� * */
	 private void End() {
	  L1World.getInstance().broadcastServerMessage("���������� ��������ϴ�.3�ð����� �����ϴ�.");
	  set�ؼ�Start(false);
	 }
	}
