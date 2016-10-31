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

public class ÀØ¼¶Controller extends Thread {

	private static ÀØ¼¶Controller _instance;

	private boolean _ÀØ¼¶Start;

	public boolean getÀØ¼¶Start() {
		return _ÀØ¼¶Start;
	}

	public void setÀØ¼¶Start(boolean ÀØ¼¶) {
		_ÀØ¼¶Start = ÀØ¼¶;
	}

	private static long sTime = 0;

	public boolean isGmOpen = false; // Ãß°¡

	private String NowTime = "";

	private static int LOOP = 3;    //6  

	 private static final SimpleDateFormat s = new SimpleDateFormat("HH",
	   Locale.KOREA);

	 private static final SimpleDateFormat ss = new SimpleDateFormat(
	   "MM-dd HH:mm", Locale.KOREA);

	 public static ÀØ¼¶Controller getInstance() {
	  if (_instance == null) {
	   _instance = new ÀØ¼¶Controller();
	  }
	  return _instance;
	 }
	 
	 public ÀØ¼¶Controller(){
	  LOOP = 3;   //  6           
	 }

	 @Override
	 public void run() {
	  try {
	   while (true) {
	    Thread.sleep(1000);
	    /** ¿ÀÇÂ * */
	    if (!isOpen() && !isGmOpen)
	     continue;
	    if (L1World.getInstance().getAllPlayers().size() <= 0)
	     continue;
	                /**ÀØ¼¶½Ã°£¶§¸¸¿£ÇÇ¾¾»ý¼º**/
	    L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "ÀØÇôÁø¼¶ÀÌ ¿­·È½À´Ï´Ù")); 
	    L1SpawnUtil.spawn2(33437, 32811, (short) 4, 777018, 0, 3800000, 0);//ÅÚ·¹Æ÷ÅÍ   >> ÀØ¼¶ ÅÚ·¹Æ÷ÅÍ°¡ »ý±â´Â À§Ä¡
	    /**ÀØ¼¶½Ã°£¶§¸¸¿£ÇÇ¾¾»ý¼º**/
	    isGmOpen = false;
	    /** ¿ÀÇÂ ¸Þ¼¼Áö * */
	    L1World.getInstance().broadcastServerMessage("\\aH ÀØÇôÁø ¼¶À¸·Î°¡´Â ¹®ÀÌ ¿­·È½À´Ï´Ù.");
	    L1World.getInstance().broadcastServerMessage("\\aH ÀØÇôÁø ¼¶ : 1½Ã°£ »ç³É°¡´É.");
	    /** ÀØ¼¶ ½ÃÀÛ* */
	    setÀØ¼¶Start(true);

	    /** ½ÇÇà 1½Ã°£ ½ÃÀÛ* */

	    Thread.sleep(3800000L); // 3800000L 1½Ã°£ 10ºÐÁ¤µµ  >1½Ã°£ 10ºÐµÚ ¸ðµÎ ¸¶À»·Î ÆÃ

	    /** 1½Ã°£ ÈÄ ÀÚµ¿ ÅÚ·¹Æ÷Æ®* */
	    TelePort();
	    close(); //Ãß°¡
	    Thread.sleep(5000L);
	    TelePort2();

	    /** Á¾·á * */
	    End();
	   }

	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }

	 /**
	  * ¿ÀÇÂ ½Ã°¢À» °¡Á®¿Â´Ù
	  * 
	  * @return (Strind) ¿ÀÇÂ ½Ã°¢(MM-dd HH:mm)
	  */
	 public String OpenTime() {
	  Calendar c = Calendar.getInstance();
	  c.setTimeInMillis(sTime);
	  return ss.format(c.getTime());
	 }

	 /**
	  * ¿µÅä°¡ ¿­·ÁÀÖ´ÂÁö È®ÀÎ
	  * 
	  * @return (boolean) ¿­·ÁÀÖ´Ù¸é true ´ÝÇôÀÖ´Ù¸é false
	  */
	 private boolean isOpen() {
	  NowTime = getTime();
	  if ((Integer.parseInt(NowTime) % LOOP) == 0)
	   return true;
	  return false;
	 }
	 
	 /**
	  * ½ÇÁ¦ ÇöÀç½Ã°¢À» °¡Á®¿Â´Ù
	  * 
	  * @return (String) ÇöÀç ½Ã°¢(HH:mm)
	  */
	 private String getTime() {
	  return s.format(Calendar.getInstance().getTime());
	 }

	 /** ¾Æµ§¸¶À»·Î ÆÃ±â°Ô* */
	 private void TelePort() {
	  for (L1PcInstance c : L1World.getInstance().getAllPlayers()) {
	   switch (c.getMap().getId()) {
	   case 1700:
	    c.stopHpRegenerationByDoll();
	    c.stopMpRegenerationByDoll();
	    L1Teleport.teleport(c, 33430, 32797, (short) 4, 4, true);
	    c.sendPackets(new S_SystemMessage("ÀØÇôÁø¼¶ÀÌ ´ÝÇû½À´Ï´Ù"));
	    break;
	   default:
	    break;
	   }
	  }
	 }
	 /**Ä³¸¯ÅÍ°¡ Á×¾ú´Ù¸é Á¾·á½ÃÅ°±â**/
	  private void close() {
	   for(L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
	    if (pc.getMap().getId() == 1700 && pc.isDead()) {
	     pc.stopHpRegenerationByDoll();
	     pc.stopMpRegenerationByDoll();
	     pc.sendPackets(new S_Disconnect());
	    }
	   }
	  }

	 /** ¾Æµ§¸¶À»·Î ÆÃ±â°Ô* */
	 private void TelePort2() {
	  for (L1PcInstance c : L1World.getInstance().getAllPlayers()) {
	   switch (c.getMap().getId()) {
	   case 1700:
	    c.stopHpRegenerationByDoll();
	    c.stopMpRegenerationByDoll();
	    L1Teleport.teleport(c, 33430, 32797, (short) 4, 4, true);
	    c.sendPackets(new S_SystemMessage("ÀØÇôÁø¼¶ÀÌ ´ÝÇû½À´Ï´Ù"));
	    break;
	   default:
	    break;
	   }
	  }
	 }

	 /** Á¾·á * */
	 private void End() {
	  L1World.getInstance().broadcastServerMessage("ÀØÇôÁø¼¶ÀÌ »ç¶óÁ³½À´Ï´Ù.3½Ã°£¸¶´Ù ¿­¸³´Ï´Ù.");
	  setÀØ¼¶Start(false);
	 }
	}
