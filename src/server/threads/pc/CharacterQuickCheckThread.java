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
		//System.out.println(CharacterQuickCheckThread.class.getName() + " ����");
		while (true) {
			try {
				for (L1PcInstance _client : L1World.getInstance()
						.getAllPlayers()) {
					if (_client instanceof L1RobotInstance) {
						continue;
					}
					if (_client.isPrivateShop() || _client.noPlayerCK
							|| _client.�����
							|| _client.getNetConnection() == null) {
						continue;
					} else {
						try {
							if (_client.getNetConnection().isClosed()) {
								System.out.println(_client.getName()
										+ " << ����ȵ� : �̸�Ʈ�� ���ÿ� �������� �ߴ��� Ȯ��.");
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
					// TODO �ڵ� ������ catch ���
					e.printStackTrace();
				}
			}
		}

	}

}
