package l1j.server.GameSystem.INN;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import l1j.server.GameSystem.Astar.World;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_SystemMessage;

public class INN {

	private static INN _instance;

	public static INN getInstance() {
		if (_instance == null) {
			_instance = new INN();
		}
		return _instance;
	}

	private static Map<Integer, Boolean> INNList = new ConcurrentHashMap<Integer, Boolean>(
			0);

	private static Map<Integer, InnTimer> InnTimerList = new ConcurrentHashMap<Integer, InnTimer>(
			0);

	public static InnTimer getInnTimer(int id) {
		synchronized (InnTimerList) {
			if (InnTimerList.containsKey(id)) {
				return InnTimerList.get(id);
			} else {
				return null;
			}
		}
	}

	public static void setInnTimer(int id, InnTimer IT) {
		synchronized (InnTimerList) {
			if (IT == null) {
				if (InnTimerList.containsKey(id)) {
					InnTimerList.remove(id);
				}
			} else {
				InnTimerList.put(id, IT);
			}
		}
	}

	public static boolean getINN(int id) {
		synchronized (INNList) {
			if (INNList.containsKey(id)) {
				return INNList.get(id);
			} else {
				return false;
			}
		}
	}

	public static void setINN(int id, boolean flag) {
		synchronized (INNList) {
			INNList.put(id, flag);
		}
	}

	public static synchronized void INNStart(L1PcInstance pc, int objid,
			int npcid, int type, int count) {
		synchronized (INNList) {
			int minmap = 0;
			switch (npcid) {
			case 70012:// ����
				if (type == 0) {// ����
					minmap = 16384;
				} else {// Ȧ
					minmap = 16896;
				}
				break;
			case 70019:// �۸�
				if (type == 0) {// ����
					minmap = 17408;
				} else {
					minmap = 17920;
				}
				break;
			case 70031:// ���
				if (type == 0) {// ����
					minmap = 18432;
				} else {
					minmap = 18944;
				}
				break;
			case 70054:// �Ƶ�
				if (type == 0) {// ����
					minmap = 19456;
				} else {
					minmap = 19968;
				}
				break;
			case 70065:// ����
				if (type == 0) {// ����
					minmap = 23552;
				} else {
					minmap = 24064;
				}
				break;
			case 70070:// ���ٿ��
				if (type == 0) {// ����
					minmap = 20480;
				} else {
					minmap = 20992;
				}
				break;
			case 70075:// �����
				if (type == 0) {// ����
					minmap = 21504;
				} else {
					minmap = 22016;
				}
				break;
			case 70084:// ���̳�
				if (type == 0) {// ����
					minmap = 22528;
				} else {
					minmap = 23040;
				}
				break;
			case 70096:// ������
				if (type == 0) {// ����
					minmap = 24576;
				} else {
					minmap = 25088;
				}
				break;
			default:
				break;
			}
			boolean checkmap = false;
			int cnt = 0;
			if (minmap != 0) {
				for (int i = 1; i <= 100; i++) {
					if (INNList.containsKey(minmap + i)) {
						checkmap = INNList.get(minmap + i);
					} else {
						checkmap = false; // getINN(minmap+i);
					}

					if (checkmap == false) {// �������
						cnt = i;
						break;
					}
					// cnt++;
				}
				if (checkmap == true || cnt == 0) {// ��ü�����
					if (type == 0) {
						pc.sendPackets(new S_NPCTalkReturn(objid, "inn6"), true);
					} else {
						pc.sendPackets(new S_NPCTalkReturn(objid, "inn16"),
								true);
					}
					// System.out.println("����� ����.");
					// ����̾���
					return;
				} else {// �������

					int price = 0;
					if (type == 0) {
						price = count * 300;
						if (pc.getInventory().countItems(40308) < price) {
							S_SystemMessage sm = new S_SystemMessage(
									"�Ƶ��� ���� �մϴ�.");
							pc.sendPackets(sm, true);
							return;
						}
					} else {
						price = count * 600;
						if (pc.getInventory().countItems(40308) < price) {
							S_SystemMessage sm = new S_SystemMessage(
									"�Ƶ��� ���� �մϴ�.");
							pc.sendPackets(sm, true);
							return;
						}
					}
					pc.getInventory().consumeItem(40308, price);
					INNList.put(minmap + cnt, true);
					InnTimer IT = new InnTimer(minmap + cnt, count,
							(3600000 * 4));// 4�ð���10000);//
					IT.begin();
					setInnTimer(minmap + cnt, IT);

					if (!World.get_map(minmap + cnt)) {
						L1WorldMap.getInstance().cloneMap(minmap, minmap + cnt);
					}

					L1ItemInstance item = null;
					if (type == 0) {
						item = ItemTable.getInstance().createItem(40312);
					} else {
						item = ItemTable.getInstance().createItem(49312);
					}
					item.setCount(count);
					item.setKey(minmap + cnt);
					item.setIdentified(true);
					pc.getInventory().storeItem(item);

//					pc.dx = 32745;
//					pc.dy = 32803;
//					pc.dm = (short) (minmap + cnt);
//					pc.sendPackets(new S_SabuTell(pc));

				}
			}
		}
	}

}