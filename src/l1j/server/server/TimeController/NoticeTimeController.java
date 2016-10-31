package l1j.server.server.TimeController;

import l1j.server.server.datatables.NoticeTable;
import l1j.server.server.model.L1World;
import l1j.server.server.serverpackets.S_SystemMessage;

public class NoticeTimeController extends Thread {

	// private String NowHourTime = "";
	// private String NowMiniuteTime = "";

	private static int noticeCount = 0;

	// private static final SimpleDateFormat s = new SimpleDateFormat("HH",
	// Locale.KOREA);
	// private static final SimpleDateFormat ss = new SimpleDateFormat("mm",
	// Locale.KOREA);

	private static NoticeTimeController _instance;

	public static NoticeTimeController getInstance() {
		if (_instance == null) {
			_instance = new NoticeTimeController();
		}
		return _instance;
	}

	public NoticeTimeController() {
		super("NoticeTimeController");
		start();
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000 * 60 * 1); // 1��

				// 5�� �ֱ�
				if (noticeCount == 3) {
					// GeneralThreadPool.getInstance().execute(new LottoTime());
					// GeneralThreadPool.getInstance().execute(new Notice());
					for (Object obj : NoticeTable.getInstance().getNoticeList()
							.clone()) {
						if (obj == null)
							continue;
						String message = (String) obj;
						L1World.getInstance().broadcastPacketToAll(
								new S_SystemMessage(/* "�������� : "+ */message),
								true);

						try {
							Thread.sleep(120000);
						} catch (Exception e) {
						}
					}
					noticeCount = 0;
				}

				noticeCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���� ����ð��� �����´�
	 * 
	 * @return (String) ���� �ð�(HH:mm)
	 */
	/*
	 * private String getTime() { return
	 * s.format(Calendar.getInstance().getTime()); }
	 * 
	 * private String getMiniuteTime() { return
	 * ss.format(Calendar.getInstance().getTime()); }
	 * 
	 * private boolean isNoticeTime(int miniute) { NowHourTime = getTime();
	 * NowMiniuteTime = getMiniuteTime();
	 * 
	 * if ((Integer.parseInt(NowMiniuteTime) % miniute) == 0) { return true; }
	 * return false; }
	 * 
	 * private boolean isLottoNoticeTime() { NowHourTime = getTime();
	 * NowMiniuteTime = getMiniuteTime();
	 * 
	 * // ���� 21�ú��� 23�û��� 5�и��� �ζ� 2�ð����̶�� �˸�. if ((Integer.parseInt(NowHourTime)
	 * >= 21 && Integer.parseInt(NowHourTime) < 23) &&
	 * (Integer.parseInt(NowMiniuteTime) % 5) == 0) { return true; } return
	 * false; }
	 * 
	 * private boolean isLottoTime() { NowHourTime = getTime(); NowMiniuteTime =
	 * getMiniuteTime();
	 * 
	 * // ���� 23�� �ζ� ��÷ if (Integer.parseInt(NowHourTime) == 23 &&
	 * Integer.parseInt(NowMiniuteTime) == 0) { return true; } return false; }
	 * 
	 * private class Notice implements Runnable { public void run() { try { for
	 * (Object obj : NoticeTable.getInstance().getNoticeList()) { if(obj ==
	 * null)continue; String message = (String)obj;
	 * L1World.getInstance().broadcastPacketToAll(new S_SystemMessage(message));
	 * 
	 * try { Thread.sleep(120000); } catch (Exception e) { } } noticeCount = 0;
	 * } catch (Exception e) { e.printStackTrace(); } } }
	 */
}
