package l1j.server.server.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import l1j.server.Config;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_DropItem;
import l1j.server.server.serverpackets.S_RemoveObject;

public class L1GroundInventory extends L1Inventory {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private static final Timer _timer = new Timer();

	private Map<Integer, ScheduledFuture<?>> _reservedTimers = new HashMap<Integer, ScheduledFuture<?>>();

	private class DeletionTimer implements Runnable {
		private final L1ItemInstance _item;

		public DeletionTimer(L1ItemInstance item) {
			_item = item;
		}

		@Override
		public void run() {
			try {
				synchronized (L1GroundInventory.this) {
					if (!_items.contains(_item)) {// �ֿ��� Ÿ�ֿ̹� ���󼭴� �� ������ ä�� �� �ִ�
						return; // �̹� �ֿ����� �ִ�
					}
					removeItem(_item);
				}
			} catch (Throwable t) {
				// _log.log(Level.SEVERE, t.getLocalizedMessage(), t);
				t.printStackTrace();
			}
		}
	}

	private void setTimer(L1ItemInstance item) {
		try {
			if (!Config.ALT_ITEM_DELETION_TYPE.equalsIgnoreCase("std")) {
				return;
			}
			if (item.getItemId() == 40515) { // ������ ��
				return;
			}

			DeletionTimer dt = new DeletionTimer(item);
			// _reservedTimers.put(item.getId(), dt);
			_reservedTimers.put(item.getId(), GeneralThreadPool.getInstance()
					.schedule(dt, Config.ALT_ITEM_DELETION_TIME * 60 * 1000));
			// _timer.schedule(new
			// DeletionTimer(item),Config.ALT_ITEM_DELETION_TIME * 60 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cancelTimer(L1ItemInstance item) {
		ScheduledFuture<?> timer = _reservedTimers.get(item.getId());
		if (timer == null) {
			return;
		}
		timer.cancel(true);
		/*
		 * DeletionTimer timer = _reservedTimers.get(item.getId()); if (timer ==
		 * null) { return; } timer.cancel();
		 */
	}

	public L1GroundInventory(int objectId, int x, int y, short map) {
		setId(objectId);
		setX(x);
		setY(y);
		setMap(map);
		L1World.getInstance().addVisibleObject(this);
	}

	@Override
	public void onPerceive(L1PcInstance perceivedFrom) {
		for (L1ItemInstance item : getItems()) {
			if (!perceivedFrom.getNearObjects().knownsObject(item)) {
				perceivedFrom.getNearObjects().addKnownObject(item);
				perceivedFrom.sendPackets(new S_DropItem(item), true);
			}
		}
	}

	// �ν� �������� �ִ� �÷��̾ ������Ʈ �۽�
	@Override
	public void insertItem(L1ItemInstance item) {
		setTimer(item);

		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(item)) {
			if (!pc.getNearObjects().knownsObject(item)) {
				pc.getNearObjects().addKnownObject(item);
				pc.sendPackets(new S_DropItem(item), true);
			}
		}
	}

	// ���̴� �������� �ִ� �÷��̾��� ������Ʈ ����
	@Override
	public void updateItem(L1ItemInstance item) {
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(item)) {
			pc.sendPackets(new S_DropItem(item), true);
		}
	}

	// �ϴ� ��� �ı� �� ���̴� �������� �ִ� �÷��̾��� ������Ʈ ����
	@Override
	public void deleteItem(L1ItemInstance item) {
		cancelTimer(item);
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(item)) {
			if (pc.getNearObjects().knownsObject(item)) {
				pc.sendPackets(new S_RemoveObject(item), true);
				pc.getNearObjects().removeKnownObject(item);
			}
		}
		_items.remove(item);
		if (_items.size() == 0) {
			L1World.getInstance().removeVisibleObject(this);
		}
	}

	// private static Logger _log = Logger.
	// getLogger(L1PcInventory.class.getName());
}
