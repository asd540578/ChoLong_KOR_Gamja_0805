/* Eva Pack -http://eva.gg.gg
 * ���� ������� ��Ÿ�� ���̵� �ý���
 */

package l1j.server.GameSystem.Antaras;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import l1j.server.Config;
import l1j.server.GameSystem.Astar.World;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.TrapTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1TrapInstance;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.trap.L1Trap;
import l1j.server.server.model.trap.L1WorldTraps;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.types.Point;
import l1j.server.server.utils.L1SpawnUtil;

public class AntarasRaidSystem {
	// private static Logger _log =
	// Logger.getLogger(AntarasRaidSystem.class.getName());

	private static AntarasRaidSystem _instance;
	private final Map<Integer, AntarasRaid> _list = new ConcurrentHashMap<Integer, AntarasRaid>();
	private int[] _mapid2 = { 0, 0, 0, 0, 0, 0 };
	private static Random random = new Random(System.nanoTime());

	public static AntarasRaidSystem getInstance() {
		if (_instance == null) {
			_instance = new AntarasRaidSystem();
		}
		return _instance;
	}

	static class antamsg implements Runnable {
		private int _mapid = 0;
		private int _type = 0;

		public antamsg(int mapid, int type) {
			_mapid = mapid;
			_type = type;
		}

		public void run() {
			try {
				switch (_type) {
				case 0:// �뷹�� ó�� �����
					try {
						AntaTrapSpawn();
						AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(
								_mapid);
						ar.setAntaras(true);
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						/*
						 * ����ø�Ʈ ��Ÿ�� : ���� ���� �������! �����ΰ�? 5�� ũ���� : ��Ÿ��! �ʸ� �Ѿ�
						 * �̰� ĥ���� ��ұ��� �Դ�! ��Ÿ�� : ���ҷӱ�. �ٽ� �ѹ� �׿��ָ�, ũ����! 1����Ÿ ����
						 */
						S_SystemMessage sm = new S_SystemMessage(
								"��Ÿ�� : ���� ���� �������! �����ΰ�?");
						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(5000);
						S_SystemMessage sm1 = new S_SystemMessage(
								"ũ���� : ��Ÿ��! �ʸ� �Ѿ� �̰� ĥ���� ��ұ��� �Դ�!");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm1);
						}
						list = null;
						sm1 = null;
						Thread.sleep(5000);

						S_SystemMessage sm2 = new S_SystemMessage(
								"��Ÿ�� : ���ҷӱ�. �ٽ� �ѹ� �׿��ָ�, ũ����!");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm2);
						}
						list = null;
						sm2 = null;
						AntarasRaidSpawn.getInstance()
								.fillSpawnTable(_mapid, 5);

						antapc.clear();
					} catch (Exception exception) {
					}
					break;
				case 1:
					/*
					 * ���������� ��Ʈ ��Ÿ�� : �� �༮�� �����Ե� ���������..! �� ������ ������ �����϶�! ũ���� :
					 * �� �̻� ������ ������ ���� ���� ����. ������ ���� ������ ���� �״���� ��ȯ�ϰڼ�.
					 */
					try {
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						S_SystemMessage sm = new S_SystemMessage(
								"��Ÿ�� : �� �༮�� �����Ե� ���������..! �� ������ ������ �����϶�!");
						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(5000);
						S_SystemMessage sm1 = new S_SystemMessage(
								"ũ���� : �� �̻� ������ ������ ���� ���� ����. ������ ���� ������ ���� �״���� ��ȯ�ϰڼ�.");
						S_SystemMessage sm2 = new S_SystemMessage(
								"20���� �ʰ��Ǿ� ���̵� ����! 5���� ��������� �̵� �˴ϴ�.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm1);
							pc.sendPackets(sm2);
						}
						list = null;
						sm1 = null;
						sm2 = null;
						Thread.sleep(5000);

						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							L1Teleport.teleport(pc, 33432, 32796, (short) 4, 5,
									true);
						}
						list = null;
						AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(
								_mapid);
						removeanta(_mapid);
						ar.setAntaras(false);
						ar.clLairUser();
						antapc.clear();

						exitAR(_mapid);
					} catch (Exception exception) {
					}
					break;
				case 2:
					/*
					 * 1�� ���� ��Ʈ ��Ÿ�� : ����� �ڿ�! ���� �г븦 �ڱ��ϴ� ����. ũ���� : �����̿� �״����
					 * Į�� �Ƶ��� ����� �ɷ��ִ�. ��Ÿ���� ���� ������ ���߰� �� �ڴ� �״�� ���̴�! ��Ÿ�� : �̷�
					 * ���������� ���� �̱� �� ���� �� ������! ũ������..
					 */
					try {
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						S_SystemMessage sm = new S_SystemMessage(
								"��Ÿ�� : ����� �ڿ�! ���� �г븦 �ڱ��ϴ� ����.");
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(4000);
						S_SystemMessage sm1 = new S_SystemMessage(
								"ũ���� : �����̿� �״���� Į�� �Ƶ��� ����� �ɷ��ִ�. ��Ÿ���� ���� ������ ���߰� �� �ڴ� �״�� ���̴�!");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm1);
						}
						list = null;
						sm1 = null;
						Thread.sleep(4000);
						S_SystemMessage sm2 = new S_SystemMessage(
								"��Ÿ�� : �̷� ���������� ���� �̱� �� ���� �� ������! ũ������..");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm2);
						}
						list = null;
						sm2 = null;
						Thread.sleep(10000);

						S_SystemMessage sm3 = new S_SystemMessage(
								"��Ÿ�� : ���� ���ִ� �Ļ縦 �غ���? ���� �ǳ����� ���� ��ġ�� �ϴ±���.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm3);
						}
						list = null;
						sm3 = null;
						Thread.sleep(10000);
						AntarasRaidSpawn.getInstance()
								.fillSpawnTable(_mapid, 6);
						antapc.clear();
					} catch (Exception exception) {
					}
					break;
				case 3:
					/*
					 * 2�� ���� ��Ʈ ũ���� : ���������! �Ǹ��� ��ȥ���� ��ħ�� �鸮�� �ʴ°�! �׾��! ��Ÿ�� :
					 * ���� ���� ����Ϸ� �ϴٴ�..�׷��� ���� ��� �ٶ����?
					 */
					try {
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						S_SystemMessage sm = new S_SystemMessage(
								"ũ���� : ���������! �Ǹ��� ��ȥ���� ��ħ�� �鸮�� �ʴ°�! �׾��!");
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(4000);
						S_SystemMessage sm1 = new S_SystemMessage(
								"��Ÿ�� : ���� ���� ����Ϸ� �ϴٴ�..�׷��� ���� ��� �ٶ����?");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm1);
						}
						list = null;
						sm1 = null;
						Thread.sleep(10000);

						S_SystemMessage sm2 = new S_SystemMessage(
								"��Ÿ�� : ���� �г밡 �ϴÿ� ��Ҵ�. ���� �� ���� �ƹ����� ���� ���̴�.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm2);
						}
						list = null;
						sm2 = null;
						Thread.sleep(10000);
						AntarasRaidSpawn.getInstance()
								.fillSpawnTable(_mapid, 7);
						antapc.clear();
					} catch (Exception exception) {
					}
					break;
				case 4:

					/*
					 * 2�� ���� ��Ʈ ��Ÿ�� : Ȳȥ�� ���ְ� �״�鿡�� ���� �����! �Ƿ��̿�, ���� ��ӴϿ�, ����
					 * ����.. �ŵμҼ�... ũ���� : ����.. �ְ��� ������� ������ �ְ��� ��翩! ��û�� �÷���
					 * �̰ܳ��� ����� �տ� ��Ÿ���� �Ǹ� �����°�! ���� �� ������ Ǯ�ڱ���. ����������!! ����. ��
					 * ���� ���� ���� �����̿�! �������� ��ħ : ���� ������ ������ ����� ������ ���� ���� ���Ƚ��ϴ�.
					 */
					try {
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getNetConnection() != null) {
								if (pc.getMapId() == _mapid) {
									antapc.add(pc);
								}
							}
						}

						L1PcInstance[] list = antapc
								.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(new S_SkillSound(pc.getId(), 7783),
									true);
							Broadcaster.broadcastPacket(pc,
									new S_SkillSound(pc.getId(), 7783), true);
							pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGONRAID_BUFF,(86400 * 2) * 1000);
							Timestamp deleteTime = new Timestamp(System.currentTimeMillis() + (86400000 * Config.���̵�ð�));// 3��
							pc.sendPackets(new S_PacketBox(S_PacketBox.�巡�ﷹ�̵����, 86400 * 2), true);
							pc.getNetConnection().getAccount().setDragonRaid(deleteTime);
							pc.getNetConnection().getAccount().updateDragonRaidBuff();
					}
						list = null;
						// ���� ���� ����
						Thread.sleep(3000);
						S_SystemMessage sm = new S_SystemMessage(
								"��Ÿ�� : Ȳȥ�� ���ְ� �״�鿡�� ���� �����! �Ƿ��̿�, ���� ��ӴϿ�, ���� ����.. �ŵμҼ�...");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm);
						}
						list = null;
						sm = null;
						Thread.sleep(3000);
						S_SystemMessage sm1 = new S_SystemMessage(
								"ũ���� : ����.. �ְ��� ������� ������ �ְ��� ��翩! ��û�� �÷��� �̰ܳ��� ����� �տ� ��Ÿ���� �Ǹ� �����°�! ���� �� ������ Ǯ�ڱ���. ����������!! ����. �� ���� ���� ���� �����̿�!");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm1);
						}
						list = null;
						sm1 = null;
						Thread.sleep(2000);
						S_SystemMessage sm2 = new S_SystemMessage(
								"�������� ��ħ : � �� ���� ��������. �� ���� ���� ���Դϴ�.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm2);
						}
						list = null;
						sm2 = null;
						/*
						 * if(GameList.get�붥() == false){
						 * L1World.getInstance().broadcastServerMessage
						 * ("�������� ��ħ : ���� ������ ������ ����� ������ ���� ���� ���Ƚ��ϴ�.");
						 * L1SpawnUtil.spawn2( 33726, 32506, (short)4 , 4212013,
						 * 0, 1000*60*60*12 , 0); GameList.set�붥(true); }else{
						 * L1World.getInstance().broadcastServerMessage(
						 * "�������� ��ħ : ������ ����� ������ ���� ���� �̹� ���� ������ ���� �ֽ��ϴ�."); }
						 */
						Thread.sleep(2000);
						// ������ �й�
						Thread.sleep(10000);
						S_SystemMessage sm6 = new S_SystemMessage(
								"�ý��� �޽��� : 10�� �Ŀ� �ڷ���Ʈ �մϴ�.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm6);
						}
						list = null;
						sm6 = null;
						Thread.sleep(5000);
						S_SystemMessage sm7 = new S_SystemMessage(
								"�ý��� �޽��� : 5�� �Ŀ� �ڷ���Ʈ �մϴ�.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm7);
						}
						list = null;
						sm7 = null;
						Thread.sleep(1000);
						S_SystemMessage sm8 = new S_SystemMessage(
								"�ý��� �޽��� : 4�� �Ŀ� �ڷ���Ʈ �մϴ�.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm8);
						}
						list = null;
						sm8 = null;
						Thread.sleep(1000);
						S_SystemMessage sm9 = new S_SystemMessage(
								"�ý��� �޽��� : 3�� �Ŀ� �ڷ���Ʈ �մϴ�.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm9);
						}
						list = null;
						sm9 = null;
						Thread.sleep(1000);
						S_SystemMessage sm10 = new S_SystemMessage(
								"�ý��� �޽��� : 2�� �Ŀ� �ڷ���Ʈ �մϴ�.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm10);
						}
						list = null;
						sm10 = null;
						Thread.sleep(1000);
						S_SystemMessage sm11 = new S_SystemMessage(
								"�ý��� �޽��� : 1�� �Ŀ� �ڷ���Ʈ �մϴ�.");
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(sm11);
						}
						list = null;
						sm11 = null;
						Thread.sleep(1000);
						list = antapc.toArray(new L1PcInstance[antapc.size()]);
						for (L1PcInstance pc : list) {
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							L1Teleport.teleport(pc, 33718, 32506, (short) 4, 5,
									true);
						}
						list = null;
						exitAR(_mapid);
						antapc.clear();
					} catch (Exception exception) {
					}
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		private static final int[] traplist = { 3, 98, 99, 100 };

		private void AntaTrapSpawn() {
			// TODO �ڵ� ������ �޼ҵ� ����
			L1TrapInstance trap = null;
			L1TrapInstance base = null;
			L1Trap trapTemp = null;
			L1Location loc = null;
			Point rndPt = null;
			for (int trapId : traplist) {
				trapTemp = TrapTable.getInstance().getTemplate(trapId);
				loc = new L1Location();
				loc.setMap(_mapid);
				loc.setX(32784);
				loc.setY(32691);
				rndPt = new Point();
				rndPt.setX(20);
				rndPt.setY(20);
				int count = 18;
				int span = 0;
				int trapDoorId = 0;
				for (int i = 0; i < count; i++) {
					trap = new L1TrapInstance(ObjectIdFactory.getInstance()
							.nextId(), trapTemp, loc, rndPt, span, trapDoorId);
					trap.setRespawn(false);
					L1World.getInstance().addVisibleObject(trap);
					L1WorldTraps.getInstance().addTrap(trap);
					// System.out.println(trap.getX()
					// +" > "+trap.getY()+" > "+trap.getMapId());
				}
				base = new L1TrapInstance(ObjectIdFactory.getInstance()
						.nextId(), loc);
				base.setRespawn(false);
				L1World.getInstance().addVisibleObject(base);
				L1WorldTraps.getInstance().addBase(base);
			}
		}

		public void exitAR(int id) {
			for (L1FieldObjectInstance npc : L1World.getInstance()
					.getAllField()) {
				if (npc.moveMapId == id) {
					npc.deleteMe();
				}
			}
			AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(id);
			ar.clLairUser();
			ar.setAntaras(false);
			ar.setanta(null);
			ar.MiniBossReset();
			ar.threadOn = false;
		}

		public void removeanta(int id) {
			AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(id);
			L1NpcInstance npc = ar.anta();
			if (npc != null && !npc.isDead()) {
				npc.deleteMe();
			}
		}
	}

	static class AntarasMsgTimer implements Runnable {
		private int _mapid = 0;
		private int _type = 0;

		public AntarasMsgTimer(int mapid, int type) {
			_mapid = mapid;
			_type = type;
		}

		public void run() {
			try {
				int idlist[] = { �ڸ���, �ڸ��� };

				int x = 0, y = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 0, x4 = 0, y4 = 0;

				int ranid = 0;
				int ranx = 0;
				int rany = 0;

				switch (_type) {
				case 1:
					x = 32663;
					y = 32792;
					x1 = 32613;
					y1 = 32815;
					x2 = 32631;
					y2 = 32845;
					x3 = 32671;
					y3 = 32865;
					x4 = 32623;
					y4 = 32916;
					break;
				case 2:
					x = 32919;
					y = 32600;
					x1 = 32870;
					y1 = 32624;
					x2 = 32886;
					y2 = 32655;
					x3 = 32925;
					y3 = 32679;
					x4 = 32881;
					y4 = 32713;
					break;
				case 3:
					x = 32919;
					y = 32792;
					x1 = 32868;
					y1 = 32819;
					x2 = 32886;
					y2 = 32846;
					x3 = 32924;
					y3 = 32871;
					x4 = 32881;
					y4 = 32907;
					break;
				case 4:
					x = 32791;
					y = 32792;
					x1 = 32759;
					y1 = 32844;
					x2 = 32740;
					y2 = 32817;
					x3 = 32798;
					y3 = 32869;
					x4 = 32747;
					y4 = 32903;
					break;
				}

				AntarasRaidSpawn.getInstance().fillSpawnTable(_mapid, _type); // ��
																				// ����
				AntarasRaid PT = AntarasRaidSystem.getInstance().getAR(_mapid);
				// L1Party PT =
				// AntarasRaidSystem.getInstance().getAR(_mapid).getParty(_type);
				S_ServerMessage smm = new S_ServerMessage(1588);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(smm);
				}
				smm = null;
				Thread.sleep(2000);
				S_ServerMessage smm1 = new S_ServerMessage(1589);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(smm1);
				}
				smm1 = null;
				Thread.sleep(2000);
				S_ServerMessage smm2 = new S_ServerMessage(1590);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(smm2);
				}
				smm2 = null;
				Thread.sleep(2000);
				S_ServerMessage smm3 = new S_ServerMessage(1591);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(smm3);
				}
				smm3 = null;
				for (int i = 0; i < 40; i++) {
					ranid = random.nextInt(2);
					ranx = random.nextInt(15);
					rany = random.nextInt(15);
					// 1���� ����
					L1SpawnUtil.spawn2(x + ranx, y + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					// 2���� ����
					L1SpawnUtil.spawn2(x1 + ranx, y1 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					L1SpawnUtil.spawn2(x2 + ranx, y2 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					// 3���� ����
					L1SpawnUtil.spawn2(x3 + ranx, y3 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
					L1SpawnUtil.spawn2(x4 + ranx, y4 + rany, (short) _mapid,
							idlist[ranid], 5, 0, 0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static int �ڸ��� = 4038001;
	private static int �ڸ��� = 4038002;

	public boolean startRaid(L1PcInstance pc) {
		checkAR();
		if (_list.size() >= 5) {
			pc.sendPackets(
					new S_SystemMessage("�Ƶ����� �� ���̻� ��Ÿ ��Ż�� ��ȯ�� �� �����ϴ�."), true);
			return false;
		}

		ArrayList<L1Object> list = L1World.getInstance().getVisibleObjects(pc,
				0);
		if (list.size() > 0) {
			pc.sendPackets(new S_SystemMessage("�� ��ġ�� ��Ÿ ��Ż�� ��ȯ�� �� �����ϴ�."),
					true);
			return false;
		}

		// pc.getInventory().consumeItem(430116, 1);
		int id = blankMapId();

		AntarasRaid ar = new AntarasRaid(id);

		L1WorldMap.getInstance().cloneMap(1005, id);
		World.cloneMap(1005, id);
		AntarasRaidSpawn.getInstance().fillSpawnTable(id, 0);

		L1NpcInstance npc = L1SpawnUtil.spawn2(pc.getX(), pc.getY(),
				pc.getMapId(), 4212015, 0, 7200 * 1000, id);
		L1FieldObjectInstance foi = (L1FieldObjectInstance) npc;
		foi.Potal_Open_pcid = pc.getId();

		L1SpawnUtil.spawn2(32680, 32744, (short) id, 4500101, 0, 0, id);

		L1SpawnUtil.spawn2(32703, 32670, (short) id, 4500102, 0, 0, id);

		_mapid2[id - 1005] = id;
		_list.put(id, ar);
		return true;
	}

	public void checkAR() {
		AntarasRaid ar = null;
		for (int i = 1005; i <= 1010; i++) {
			if (_list.containsKey(i)) {
				ar = _list.get(i);
				if (ar.getEndTime() <= System.currentTimeMillis()
						|| !ar.threadOn) {
					_list.remove(i);
					_mapid2[i - 1005] = 0;
				}
			}
		}
	}

	public AntarasRaid getAR(int id) {
		return _list.get(id);
	}

	/**
	 * �� �� ���̵� �����´�
	 * 
	 * @return
	 */
	public int blankMapId() {
		int mapid = 1005;
		int a0 = 1005;
		int a1 = 1006;
		int a2 = 1007;
		int a3 = 1008;
		int a4 = 1009;
		int a5 = 1010;
		if (_list.size() >= 1) {
			for (int id : _mapid2) {
				if (id == 1005) {
					a0 = 0;
				}
				if (id == 1006) {
					a1 = 0;
				}
				if (id == 1007) {
					a2 = 0;
				}
				if (id == 1008) {
					a3 = 0;
				}
				if (id == 1009) {
					a4 = 0;
				}
				if (id == 1010) {
					a5 = 0;
				}
			}
		}
		if (a0 != 0) {
			System.out.println("1005");
			return a0;
		}
		if (a1 != 0) {
			System.out.println("1006");
			return a1;
		}
		if (a2 != 0) {
			System.out.println("1007");
			return a2;
		}
		if (a3 != 0) {
			System.out.println("1008");
			return a3;
		}
		if (a4 != 0) {
			System.out.println("1009");
			return a4;
		}
		if (a5 != 0) {
			System.out.println("1010");
			return a5;
		}
		return mapid;
	}

	public int countRaidPotal() {
		return _list.size();
	}
}
