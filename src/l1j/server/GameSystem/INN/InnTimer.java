package l1j.server.GameSystem.INN;

import java.util.Timer;
import java.util.TimerTask;

import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SabuTell;

public class InnTimer extends TimerTask {
	private int _Ű�ʹ�ȣ;
	private int _Ű����;
	private int _timeMillis;
private boolean exit = false;
	public InnTimer(int Ű�ʹ�ȣ, int Ű����, int timeMillis) {
		_Ű�ʹ�ȣ = Ű�ʹ�ȣ;
		_Ű���� = Ű����;
		_timeMillis = timeMillis;
	}

	public synchronized void Ű����(int count) {
		try {
			int check = _Ű���� - count;
			if (check <= 0) {
				run();
			} else {
				_Ű���� = check;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void run() {
		try {
			if(exit)return;
			exit = true;
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc.getMapId() == _Ű�ʹ�ȣ) {
					pc.setTelType(5);
					S_SabuTell st = new S_SabuTell(pc);
					pc.sendPackets(st, true);
				}
			}
			//System.out.println("111111111111111111111111");
			INN.setINN(_Ű�ʹ�ȣ, false);
			INN.setInnTimer(_Ű�ʹ�ȣ, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void begin() {
		Timer timer = new Timer();
		timer.schedule(this, _timeMillis);
	}
}