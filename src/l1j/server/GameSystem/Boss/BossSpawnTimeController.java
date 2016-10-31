package l1j.server.GameSystem.Boss;

import java.util.Calendar;

import l1j.server.Config;
import l1j.server.server.datatables.BossSpawnTable;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.BaseTime;
import l1j.server.server.model.gametime.RealTime;
import l1j.server.server.model.gametime.RealTimeClock;
import l1j.server.server.model.gametime.TimeListener;
import l1j.server.server.serverpackets.S_SystemMessage;

public class BossSpawnTimeController implements TimeListener {
	private static BossSpawnTimeController _instance;

	public static void start() {
		if (_instance == null) {
			_instance = new BossSpawnTimeController();
		}
		_instance.init();
		RealTimeClock.getInstance().addListener(_instance);
	}

	private void init() {
		RealTime time = RealTimeClock.getInstance().getRealTime();
		for (L1BossCycle b : L1BossCycle.getBossCycleList()) {
			if (b.getBaseDay() == 0)
				b.setBaseDay(time.get(Calendar.DAY_OF_MONTH));

			setBaseTime(b, time);
			setNextBossSpawnTime(b);
		}
	}

	private void setBaseTime(L1BossCycle b, RealTime time) {
		final int H = time.get(Calendar.HOUR_OF_DAY);
		final int M = time.get(Calendar.MINUTE);
		boolean isOverDay = false;

		for (; b.getBaseHour() < H && !isOverDay;) {
			int nM = (b.getBaseMinute() + b.getPeriodMinute());
			int nH = (b.getBaseHour() + b.getPeriodHour() + (nM / 60));

			if ((nH / 24) >= 1)
				isOverDay = true;

			b.setBaseHour(nH % 24);
			b.setBaseMinute(nM % 60);
		}

		for (; b.getBaseHour() == H && b.getBaseMinute() <= M
				&& b.getPeriodMinute() > 0;) {
			int plusM = b.getBaseMinute() + b.getPeriodMinute();
			if (plusM < 60)
				b.setBaseMinute(plusM);
			else
				b.setBaseHour(b.getBaseHour() + 1);
		}

		setNextBossSpawnTime(b);
	}

	private void setNextBossSpawnTime(L1BossCycle b) {
		// Random rnd = new Random(System.nanoTime());
		int newH = 0, newM = 0;
		newH = b.getBaseHour() + b.getStartHour();
		newM = b.getBaseMinute() + b.getStartMinute();

		// System.out.println("bh/sh/eh = "+b.getBaseHour()+"/"+b.getStartHour()+"/"+b.getEndHour());
		// System.out.println("bm/sm/em = "+b.getBaseMinute()+"/"+b.getStartMinute()+"/"+b.getEndMinute());
		// int eH = b.getEndHour();
		// int eM = b.getEndMinute();
		// int rndr = (eH*60+eM)-(b.getStartHour()*60+b.getStartMinute());
		// rndr = rndr-(rndr/4);
		// int rndM = 0;
		// �ߴ� �ð� ���� ����
		// if(eH > 0) rndM = rnd.nextInt((eH*60));
		// if(eM > 0) rndM += rnd.nextInt(eM);
		// if(rndr>0) rndM = rnd.nextInt(rndr);
		// else rndM = rnd.nextInt(10);
		// System.out.println("eH/eM/rndM = "+eH+"/"+eM+"/"+rndM);
		// newH += rndM / 60;
		// newM += rndM % 60;

		newH += newM / 60;
		newM %= 60;
		newH %= 24;
		// System.out.println("newH/newM = "+newH+"/"+newM);
		b.setKillHour(b.getNewKillHour());
		b.setKillMinute(b.getNewKillMinute());

		int kM = b.getBaseMinute() + b.getEndMinute();
		int kH = b.getBaseHour() + b.getEndHour() + (kM / 60);
		kM %= 60;
		kH %= 24;

		b.setNewKillHour(kH);
		b.setNewKillMinute(kM);

		int plusH = (b.getBaseMinute() + b.getPeriodMinute()) / 60;
		int perim = b.getPeriodMinute() % 60;
		int perih = b.getPeriodHour() + plusH % 24;
		b.setBaseMinute((b.getBaseMinute() + b.getPeriodMinute()) % 60);
		b.setBaseHour((b.getBaseHour() + b.getPeriodHour() + plusH) % 24);
		// ���ο� ���� �ð� ����
		b.setNextSpawnHour(newH);
		b.setNextSpawnMinute(newM);
		if (Config.����ä�ø����() > 0) {
			for (L1PcInstance gm : Config.toArray����ä�ø����()) {
				if (gm.getNetConnection() == null) {
					Config.remove����(gm);
					continue;
				}
				gm.sendPackets(new S_SystemMessage("[" + b.getName() + "] "
						+ newH + "�� " + newM + "�� / �ֱ� : " + perih + "�� "
						+ perim + "��"));
			}
		}
		System.out.println("Name : " + b.getName());
		System.out.println("�������� " + newH + "�� " + newM + "�� / �ֱ� : " + perih
				+ "�� " + perim + "��");

	}

	public void onDayChanged(BaseTime time) {
	}

	public void onHourChanged(BaseTime time) {
	}

	public void onMinuteChanged(BaseTime time) {
		// System.out.println(time.get(Calendar.MINUTE)+ "��, ó�� ����");
		final int H = time.get(Calendar.HOUR_OF_DAY);
		final int M = time.get(Calendar.MINUTE);
		int sH, sM;
		for (L1BossCycle b : L1BossCycle.getBossCycleList()) {
			if (H == b.getKillHour() && M == b.getKillMinute()) { //�̺�Ʈ�α׿���
		//		eva.EventLogAppend(" ["+time.get(Calendar.HOUR)+"��"+time.get(Calendar.MINUTE)+"��] "+b.getName() + " [�˸�] ���� Ÿ���� ����Ǿ����ϴ�" );
				BossSpawnTable.killBoss(b.getName());
				if (Config.����ä�ø����() > 0) {
					for (L1PcInstance gm : Config.toArray����ä�ø����()) {
						if (gm.getNetConnection() == null) {
							Config.remove����(gm);
							continue;
						}
						gm.sendPackets(new S_SystemMessage("ų����" + b.getName()));
					}
				}
			}

			sH = b.getNextSpawnHour();
			sM = b.getNextSpawnMinute();

			if (sH == H && sM == M) {
//�̺�Ʈ�α׿���
			//	eva.EventLogAppend(" ["+time.get(Calendar.HOUR)+"��"+time.get(Calendar.MINUTE)+"��] "+b.getName() +" [�˸�] ���� Ÿ���� ���۵Ǿ����ϴ�");
				BossSpawnTable.spawnBoss(b.getName()); // ����
				setNextBossSpawnTime(b); // ���� Ÿ�� �缳��
				if (Config.����ä�ø����() > 0) {
					for (L1PcInstance gm : Config.toArray����ä�ø����()) {
						if (gm.getNetConnection() == null) {
							Config.remove����(gm);
							continue;
						}
						gm.sendPackets(new S_SystemMessage("��������" + b.getName()));
					}
				}
			}
		}
		// System.out.println(time.get(Calendar.MINUTE)+ "��, ó�� ��");
	}

	public void onMonthChanged(BaseTime time) {
	}

}
