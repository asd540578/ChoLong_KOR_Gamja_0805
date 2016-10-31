package l1j.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.GameSystem.GameList;
import l1j.server.GameSystem.DreamsTemple.DreamsTemple;
import l1j.server.GameSystem.FireDragon.FireDragon;
import l1j.server.GameSystem.Hadin.Hadin;
import l1j.server.GameSystem.IceQeen.IceQeen;
import l1j.server.GameSystem.ORIM.ORIM;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;

public class IND_Q implements Runnable {

	private final Queue<IND> _queue;

	public IND_Q() {
		_queue = new ConcurrentLinkedQueue<IND>();
		GeneralThreadPool.getInstance().execute(this);
	}

	public void requestWork(IND _ind) {
		_queue.offer(_ind);
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(30L);
				synchronized (this) // Ÿ�̹� ������ 2�� ���� �����ϴ�. �ſ� ���� Ȯ���̱� �ϴٸ�.
									// �̷л�.-_-
				{
					IND _ind = _queue.peek();
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

					if (_ind._type == 0) {// �ϵ�
						if (player.getMapId() != 9100) {
							_queue.remove();
							continue;
						}
						Hadin.get().start(player);

					} else if (_ind._type == 1 || _ind._type == 2) {// ���a
						if (player.getMapId() != 2100) {
							_queue.remove();
							continue;
						}
						IceQeen _IQ = new IceQeen();
						int IQmapId = _IQ.Start(player, _ind._type);
						if (IQmapId != 0) {
							if (player.getMapId() == 2100) {
								player.getInventory().consumeItem(6022, 1);
								player.dx = 32728;
								player.dy = 32819;
								player.dm = (short) IQmapId;
								player.dh = 5;
								player.setTelType(7);
								player.sendPackets(new S_SabuTell(player), true);
							}
						}
					} else if (_ind._type == 3) {// ���� ��
						if (player.getMapId() != 9202) {
							_queue.remove();
							continue;
						}
						ORIM _OR = new ORIM();
						int ORmapid = _OR.Start(player, _ind._type);
						if (ORmapid != 0) {
							if (player.getMapId() == 9202) {
								_OR.systemgo(player, ORmapid, false);
							}
						}
					} else if (_ind._type == 4) {// ���� ��
						if (player.getMapId() != 9202) {
							_queue.remove();
							continue;
						}
						ORIM _OR = new ORIM();
						int ORmapid = _OR.Start(player, _ind._type);
						if (ORmapid != 0) {
							if (player.getMapId() == 9202) {
								_OR.systemgo(player, ORmapid, true);
							}
						}
					} else if (_ind._type == 5) {// ȭ���δ�
						if (player.getMapId() != 2699) {
							_queue.remove();
							continue;
						}
						FireDragon _FD = new FireDragon();
						int FDmapId = _FD.Start(player);
						if (FDmapId != 0) {
							if (player.getMapId() == 2699) {
								player.getInventory().consumeItem(7337, 1);
								L1ItemInstance item = player.getInventory()
										.storeItem(7236, 1);
								player.sendPackets(new S_ServerMessage(403,
										item.getLogName()), true);

								player.dx = 32624;
								player.dy = 33057;
								player.dm = (short) FDmapId;
								player.dh = 6;
								player.setTelType(7);
								player.sendPackets(new S_SabuTell(player), true);

								L1NpcInstance ������ = GameList.getwdeath();
								if (������ != null) {
									������.Wchat_start(player);
								}
								player.�δ�������();
							}
						}
					} else if (_ind._type == 6) {// ����
						if (player.getMapId() != 1935) {
							_queue.remove();
							continue;
						}
						DreamsTemple _DT = new DreamsTemple();
						int DTmapid = _DT.start(player);
						if (DTmapid != 0) {
							if (player.getMapId() == 1935) {
								_DT.systemgo(player, DTmapid);
							}
						}
					}

					_queue.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}