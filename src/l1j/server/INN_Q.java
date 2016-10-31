package l1j.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.GameSystem.INN.INN;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;

public class INN_Q implements Runnable {
	private final Queue<INN_IND> _queue;

	public INN_Q() {
		_queue = new ConcurrentLinkedQueue<INN_IND>();
		GeneralThreadPool.getInstance().execute(this);
	}

	public void requestWork(INN_IND _ind) {
		_queue.offer(_ind);
	}

	@SuppressWarnings("static-access")
	public void run() {
		while (true) {
			try {
				Thread.sleep(30L);
				synchronized (this) // Ÿ�̹� ������ 2�� ���� �����ϴ�. �ſ� ���� Ȯ���̱� �ϴٸ�.
									// �̷л�.-_-
				{
					INN_IND _ind = _queue.peek();
					if (_ind == null) {
						continue;
					}
					final L1PcInstance player = L1World.getInstance()
							.getPlayer(_ind._pcname);
					if (player == null) {
						_queue.remove();
						continue;
					}

					if (player.�δ�������) {
						_queue.remove();
						continue;
					}

					player.�δ������� = true;
					GeneralThreadPool.getInstance().schedule(new Runnable() {
						public void run() {
							player.�δ������� = false;
							;
						}
					}, 3000);
					INN.getInstance().INNStart(player, _ind._objid,
							_ind._npcid, _ind._type, _ind._count);
					/*
					 * if(_ind._type == 0 ){//��뿩
					 * 
					 * }else if(_ind._type == 1 || _ind._type == 2){//���a }
					 */

					_queue.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}