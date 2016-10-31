package server.threads.pc;

import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;

public class CharacterQuickCheckThread extends Thread {

	private static CharacterQuickCheckThread _instance;

	// private static Logger _log =
	// Logger.getLogger(CharacterQuickCheckThread.class.getName());

	public static CharacterQuickCheckThread getInstance() {
		if (_instance == null) {
			_instance = new CharacterQuickCheckThread();
			_instance.start();
		}
		return _instance;
	}

	public CharacterQuickCheckThread() {
		super("server.threads.pc.CharacterQuickCheckThread");
	}

	public void run() {
		//System.out.println(CharacterQuickCheckThread.class.getName() + " 시작");
		while (true) {
			try {
				for (L1PcInstance _client : L1World.getInstance()
						.getAllPlayers()) {
					if (_client instanceof L1RobotInstance) {
						continue;
					}
					if (_client.isPrivateShop() || _client.noPlayerCK
							|| _client.샌드백
							|| _client.getNetConnection() == null) {
						continue;
					} else {
						try {
							if (_client.getNetConnection().isClosed()) {
								System.out.println(_client.getName()
										+ " << 종료안됨 : 이멘트가 동시에 여러명이 뜨는지 확인.");
								_client.logout();
								_client.getNetConnection().close();
							}

						} catch (Exception e) {
							e.printStackTrace();
							// _log.warning("Quit Character failure.");
							// _log.log(Level.SEVERE, e.getLocalizedMessage(),
							// e);
							// throw e;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} finally {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO 자동 생성된 catch 블록
					e.printStackTrace();
				}
			}
		}

	}

}
