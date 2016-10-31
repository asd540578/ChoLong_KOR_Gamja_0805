/**
 * ���� ������� Chapter1. ����� ������ �ϵ�
 * �ϵ� �̺�Ʈ ó���� ���� �޸� Ŭ����
 * by. ����
 */
package l1j.server.GameSystem.Hadin;

import javolution.util.FastMap;
import javolution.util.FastTable;
import l1j.server.server.model.L1Party;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;

public class HadinParty {
	public HadinParty(L1Party p, short _mapid) {
		_party = p;
		mapid = _mapid;
		BasicNpcList = HadinSpawn.getInstance().fillSpawnTable(mapid, 0, true);
		Leader_BasicNpcList = HadinSpawn.getInstance().fillSpawnTable(mapid, 8,
				true);
		BossRoomDoor = HadinSpawn.getInstance().fillSpawnTable2(mapid, 22);
		Arrow = HadinSpawn.getInstance().fillSpawnTable2(mapid, 40);
		for (L1NpcInstance npc : BasicNpcList) {
			if (npc != null) {
				if (npc.getName().equalsIgnoreCase("�ϵ�")) {
					Npc_Hadin = npc;
				} else if (npc.getName().equalsIgnoreCase("�ϵ� �ٴ� ����Ʈ")) {
					Hadin_Effect = npc;
				} else if (npc.getName().equalsIgnoreCase("�ɷ��Ͻ�")) {
					Kere = npc;
				}
				if (Npc_Hadin != null && Hadin_Effect != null && Kere != null)
					break;
			}
		}
		for (L1NpcInstance npc : Leader_BasicNpcList) {
			if (npc.getSpawnLocation().equalsIgnoreCase("��ũ"))
				Npc_Orc = npc;
			else if (npc.getSpawnLocation().equalsIgnoreCase("��ũ ����"))
				Npc_OrcWarrior = npc;
			else if (npc.getSpawnLocation().equalsIgnoreCase("��ũ �ü�"))
				Npc_OrcAcher = npc;
			else if (npc.getSpawnLocation().equalsIgnoreCase("��ũ ���� �߽�"))
				Npc_Balsim = npc;
		}
	}

	public int partymembercount() {
		int count = 0;
		for (L1PcInstance player : _party.getMembers()) {
			if (player.getMapId() == mapid) {
				count++;
			}
		}
		return count;
	}

	public int partybossroomcount() {
		int count = 0;
		for (L1PcInstance player : _party.getMembers()) {
			if (player.�ϵ򺸽�������) {
				count++;
			}
		}
		return count;
	}

	public int bossroomcount = 0;
	private L1Party _party;

	public L1Party getParty() {
		return _party;
	}

	public long delayTime = 0;
	public long leader_delayTime = 0;
	public byte step = 1;
	public byte sub_step = 0;
	public byte ssub_step = 0;
	public byte leader_step = 1;
	public byte leader_sub_step = 0;
	public byte leader_bossroom_sub_step = 0;
	public short mapid = 0;
	public boolean leaderOn;
	public boolean TrapCkOn;
	public boolean ArrowTrap;
	public boolean Leader_Chapter_2;
	public byte firtCkCount = 0;
	public boolean UserCountCK = true;
	// public boolean MonCK = false;
	public boolean LEVEL_1_TRAP_CK = false;
	public boolean LEVEL_3_TRAP_CK = false;
	public boolean BossRoomCK = false;
	public boolean LAST_TRAP_CK = false;
	public int BS = 0;

	public L1NpcInstance Npc_Hadin;
	public L1NpcInstance Hadin_Effect;
	public L1NpcInstance Baphomet;
	public L1NpcInstance Kere;
	public L1NpcInstance Npc_Orc;
	public L1NpcInstance Npc_OrcWarrior;
	public L1NpcInstance Npc_OrcAcher;
	public L1NpcInstance Npc_Balsim;

	public Hadin_KerevsBapho vsThread;

	public FastTable<L1NpcInstance> BasicNpcList;
	public FastTable<L1NpcInstance> NpcList;
	public FastTable<L1NpcInstance> NpcList2;
	public FastTable<L1NpcInstance> NpcList3;
	public FastTable<L1NpcInstance> NpcList4;
	public FastTable<L1NpcInstance> Leader_BasicNpcList;
	public FastTable<L1NpcInstance> Leader_NpcList;
	public FastMap<String, L1NpcInstance> BossRoomDoor;
	public FastMap<String, L1NpcInstance> Arrow;
	public boolean Leader_Chapter_3;
	public boolean Leader_Chapter_4;

	public void reset() {
		if (BasicNpcList != null && BasicNpcList.size() > 0)
			BasicNpcList.clear();
		BasicNpcList = null;
		if (NpcList != null && NpcList.size() > 0)
			NpcList.clear();
		NpcList = null;
		if (NpcList2 != null && NpcList2.size() > 0)
			NpcList2.clear();
		NpcList2 = null;
		if (NpcList3 != null && NpcList3.size() > 0)
			NpcList3.clear();
		NpcList3 = null;
		if (NpcList4 != null && NpcList4.size() > 0)
			NpcList4.clear();
		NpcList4 = null;
		if (Leader_BasicNpcList != null && Leader_BasicNpcList.size() > 0)
			Leader_BasicNpcList.clear();
		Leader_BasicNpcList = null;
		if (Leader_NpcList != null && Leader_NpcList.size() > 0)
			Leader_NpcList.clear();
		Leader_NpcList = null;
		if (BossRoomDoor != null && BossRoomDoor.size() > 0)
			BossRoomDoor.clear();
		BossRoomDoor = null;
		if (Arrow != null && Arrow.size() > 0)
			Arrow.clear();
		Arrow = null;
	}
}
