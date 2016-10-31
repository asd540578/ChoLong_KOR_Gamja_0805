package l1j.server.GameSystem.Papoo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import l1j.server.Config;
import l1j.server.GameSystem.Astar.World;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.L1SpawnUtil;

public class PaPooRaidSystem {

	private static PaPooRaidSystem _instance;
	private final Map<Integer, PaPooRaid> _list = new ConcurrentHashMap<Integer, PaPooRaid>();
	private int[] _mapid2 = { 0, 0, 0, 0, 0, 0 };
	private static Random random = new Random(System.nanoTime());

	public static PaPooRaidSystem getInstance() {
		if (_instance == null) {
			_instance = new PaPooRaidSystem();
		}
		return _instance;
	}

	static class papoomsg implements Runnable {
		private int _mapid = 0;
		private int _type = 0;

		public papoomsg(int mapid, int type) {
			_mapid = mapid;
			_type = type;
		}

		@Override
		public void run() {
			try {

				switch (_type) {
				case 0:
					/*
					 * ���������� ��Ʈ ��Ǫ���� : ���� �����̴� ���̴�. ������� ���� �� ���ָ� ���� �� ����! ���� �翤
					 * : ���� ���� �� �� �ִ� ������ ������ ���Ե��� ��ȯ�ϰڽ��ϴ�. ���� ��¿ �� �����ϴ�.
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
								"��Ǫ���� : ���� �����̴� ���̴�. ������� ���� �� ���ָ� ���� �� ����!");
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
						S_SystemMessage sm2 = new S_SystemMessage(
								"���� �翤 : ���� ���� �� �� �ִ� ������ ������ ���Ե��� ��ȯ�ϰڽ��ϴ�. ���� ��¿ �� �����ϴ�.");
						S_SystemMessage sm3 = new S_SystemMessage(
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
							pc.sendPackets(sm2);
							pc.sendPackets(sm3);
						}
						list = null;
						sm2 = null;
						sm3 = null;
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
						PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
								_mapid);
						removeanta(_mapid);
						ar.setPapoo(false);
						ar.clLairUser();
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {
					}
					break;
				case 1:// ��Ǫ �����
					try {
						for (L1Object npc : L1World.getInstance().getObject()) {
							if (npc instanceof L1MonsterInstance) {
								if (npc.getMapId() == _mapid) {
									L1MonsterInstance _npc = (L1MonsterInstance) npc;
									if (!_npc.isDead()
											&& (_npc.getNpcId() == 4039000
													|| _npc.getNpcId() == 4039006 || _npc
													.getNpcId() == 4039007)) {
										return;
									}
								}
							}
						}
						PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(
								_mapid);
						ar.setPapoo(true);
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						/*
						 * ����ø�Ʈ ���� ���� ������ �����ٴϡ� ��Ⱑ ���� �ϱ���..
						 * 
						 * �� ���� ��Ǫ����! ���� ���� ���� �񰡸� ġ��� �� ���̴�! (���� �翤)
						 * 
						 * ������ Ǯ �� �װ� ū ������ �Ǿ�����.. ������ �� ���� �ں�� ����.
						 * 
						 * �׶��� �� �༮�� �� �� �ӱ��� ���ָ� ��������.. ������ �ٸ���! (���� �翤) 1����Ÿ ����
						 */
						S_SystemMessage sm = new S_SystemMessage(
								"��Ǫ���� : ���� ���� ������ �����ٴ�...��Ⱑ �����ϱ���..");
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
						S_SystemMessage sm2 = new S_SystemMessage(
								"���� �翤 : �� ���� ��Ǫ����! ���� ���� ���� �񰡸� ġ��� �� ���̴�!");
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
						Thread.sleep(5000);
						S_SystemMessage sm3 = new S_SystemMessage(
								"��Ǫ���� : ������ Ǯ �� �װ� ū ������ �Ǿ�����..������ �� ���� �ں�� ����..");
						for (L1PcInstance pc : antapc) {
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
						sm3 = null;
						Thread.sleep(5000);
						S_SystemMessage sm4 = new S_SystemMessage(
								"���� �翤 : �׶��� �� �༮�� �� �� �ӱ��� ���ָ� ��������..������ �ٸ���!");
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
							pc.sendPackets(sm4);
						}
						list = null;
						sm4 = null;
						PapooRaidSpawn.getInstance().fillSpawnTable(_mapid, 1);
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {
					}
					break;

				case 2:
					/*
					 * 1�� ���� ��Ʈ ��Ǫ���� : ���ҷӱ���! ������ �ʿ� �Բ� �̽��� ������ �� ���� �������� ���̳�!
					 * ���� �翤 : �����̿�! �� ����� ��Ǫ������ ����ġ�� ���� �ձ��� ������ ���� ���ָ� �ε� Ǯ��
					 * �ּҼ�! ��Ǫ���� : ���հ����δ� ����ϱ���! ������...
					 */
					try {
						for (L1Object npc : L1World.getInstance().getObject()) {
							if (npc instanceof L1MonsterInstance) {
								if (npc.getMapId() == _mapid) {
									L1MonsterInstance _npc = (L1MonsterInstance) npc;
									if (!_npc.isDead()
											&& (_npc.getNpcId() == 4039000
													|| _npc.getNpcId() == 4039006 || _npc
													.getNpcId() == 4039007)) {
										return;
									}
								}
							}
						}
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}

						S_SystemMessage sm = new S_SystemMessage(
								"��Ǫ���� : ���ҷӱ���! ������ �ʿ� �Բ� �̽��� ������ �� ���� �������� ���̳�!");
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
						S_SystemMessage sm2 = new S_SystemMessage(
								"���� �翤 : �����̿�! �� ����� ��Ǫ������ ����ġ�� ���� �ձ��� ������ ���� ���ָ� �ε� Ǯ�� �ּҼ�!");
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
						Thread.sleep(4000);
						S_SystemMessage sm3 = new S_SystemMessage(
								"��Ǫ���� : ���հ����δ� ����ϱ���! ������...");
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
						S_SystemMessage sm4 = new S_SystemMessage(
								"��Ǫ���� : �� �ӱ��� �İ��� �η����� �������� �� ���� �˰� ���ָ�!");
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
							pc.sendPackets(sm4);
						}
						list = null;
						sm4 = null;
						Thread.sleep(10000);

						PapooRaidSpawn.getInstance().fillSpawnTable(_mapid, 2);
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {

					}
					break;
				case 3:
					/*
					 * 2�� ���� ��Ʈ
					 * 
					 * ���� �翤 : ���� ��Ǫ������ ���� ���� �������� �����ϴ�! �����̾� ���� �� ���� ���ּҼ�!
					 * ��Ǫ���� : �� ����� ����̶� �θ��� ����, ���� ��� �����̾����� �˰� ���ָ�! ��Ǫ���� : �翤��
					 * �Բ� �Ѱ��� ��ȸ�ϰ� �� ���̴�! ����� ������̿�...
					 */
					try {
						for (L1Object npc : L1World.getInstance().getObject()) {
							if (npc instanceof L1MonsterInstance) {
								if (npc.getMapId() == _mapid) {
									L1MonsterInstance _npc = (L1MonsterInstance) npc;
									if (!_npc.isDead()
											&& (_npc.getNpcId() == 4039000
													|| _npc.getNpcId() == 4039006 || _npc
													.getNpcId() == 4039007)) {
										return;
									}
								}
							}
						}
						ArrayList<L1PcInstance> antapc = null;
						antapc = new ArrayList<L1PcInstance>();
						for (L1PcInstance pc : L1World.getInstance()
								.getAllPlayers()) {
							if (pc.getMapId() == _mapid) {
								antapc.add(pc);
							}
						}
						S_SystemMessage sm = new S_SystemMessage(
								"���� �翤 : ���� ��Ǫ������ ���� ���� �������� �����ϴ�! �����̾� ���� �� ���� ���ּҼ�!");
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
						S_SystemMessage sm2 = new S_SystemMessage(
								"��Ǫ���� : �� ����� ����̶� �θ��� ����, ���� ��� �����̾����� �˰� ���ָ�!");
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
								"��Ǫ���� : �翤�� �Բ� �Ѱ��� ��ȸ�ϰ� �� ���̴�! ����� ������̿�...");
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
						PapooRaidSpawn.getInstance().fillSpawnTable(_mapid, 3);
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {
					}
					break;
				case 4:

					/*
					 * 3�� ���� ��Ʈ ��Ǫ���� : �翤..�� �༮��..���...���� ��Ӵ�..�Ƿ��̽ÿ� ����
					 * ����..�ŵμҼ�... ���� �翤 : �����մϴ�..��ŵ��� ���� �Ƶ� �ְ��� �����Դϴ�. ����..����
					 * �ձ��� ���� ���ְ� Ǯ�� �� ���� �� �����ϴ�.
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
							if (pc.getMapId() != _mapid) {
								antapc.remove(pc);
								continue;
							}
							if (L1World.getInstance().getPlayer(pc.getName()) == null
									|| pc.getNetConnection() == null) {
								antapc.remove(pc);
								continue;
							}
							pc.sendPackets(new S_SkillSound(pc.getId(), 7783),
									true);
							Broadcaster.broadcastPacket(pc,
									new S_SkillSound(pc.getId(), 7783), true);
							pc.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.DRAGONRAID_BUFF,
									(86400 * 2) * 1000);
							Timestamp deleteTime = new Timestamp(
									System.currentTimeMillis() + (86400000 * Config.���̵�ð�));// 3��
							pc.sendPackets(new S_PacketBox(
									S_PacketBox.�巡�ﷹ�̵����, 86400 * 2), true);
							pc.getNetConnection().getAccount()
									.setDragonRaid(deleteTime);
							pc.getNetConnection().getAccount()
									.updateDragonRaidBuff();
							// pc.sendPackets(new
							// S_SystemMessage("�巡�� ���̵� �������� ���� "+C_SelectCharacter.ss.format(pc.getNetConnection().getAccount().getDragonRaid())+" ���Ŀ� �巡�� ��Ż ������ �����մϴ�."),
							// true);

							/*
							 * l1skilluse = new L1SkillUse();
							 * l1skilluse.handleCommands(pc,
							 * L1SkillId.DRAGONBLOOD_P, pc.getId(), pc.getX(),
							 * pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
							 * S_PacketBox pb = new
							 * S_PacketBox(S_PacketBox.DRAGONBLOOD, 85, 4320);
							 * pc.sendPackets(pb);pb = null; Timestamp
							 * deleteTime = null; deleteTime = new
							 * Timestamp(System.currentTimeMillis() +
							 * 259200000);// 3�� pc.setpaTime(deleteTime);
							 */
						}
						list = null;
						// ���� ���� ����
						Thread.sleep(3000);
						S_SystemMessage sm = new S_SystemMessage(
								"��Ǫ���� : �翤..�� �༮��..���...���� ��Ӵ�..�Ƿ��̽ÿ� ���� ����..�ŵμҼ�...");
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
						S_SystemMessage sm2 = new S_SystemMessage(
								"���� �翤 : �����մϴ�..��ŵ��� ���� �Ƶ� �ְ��� �����Դϴ�. ����..���� �ձ��� ���� ���ְ� Ǯ�� �� ���� �� �����ϴ�.");
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
						Thread.sleep(2000);
						S_SystemMessage sm3 = new S_SystemMessage(
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
							pc.sendPackets(sm3);
						}
						list = null;
						sm3 = null;
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
						if (antapc.size() > 0)
							antapc.clear();
						antapc = null;
					} catch (Exception exception) {
					}
					break;

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void exitAR(int id) {
			for (L1FieldObjectInstance npc : L1World.getInstance()
					.getAllField()) {
				if (npc.moveMapId == id) {
					npc.deleteMe();
				}
			}
			PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(id);
			ar.clLairUser();
			ar.setPapoo(false);
			ar.setPaPoo(null);
			ar.threadOn = false;
		}

		public void removeanta(int id) {
			PaPooRaid ar = PaPooRaidSystem.getInstance().getAR(id);
			L1NpcInstance npc = ar.PaPoo();
			if (npc != null && !npc.isDead()) {
				npc.deleteMe();
			}
		}
	}

	static class FafulionMsgTimer implements Runnable {
		private int _mapid = 0;
		private int _type = 0;

		public FafulionMsgTimer(int mapid, int type) {
			_mapid = mapid;
			_type = type;
		}

		@Override
		public void run() {
			try {
				int idlist[] = { �˷��۽��ް�, �˷��۽���� };

				int x = 0, y = 0, x1 = 0, y1 = 0, x2 = 0, y2 = 0, x3 = 0, y3 = 0, x4 = 0, y4 = 0;

				int ranid = 0;
				int swich = 0;
				int ranx = 0;
				int rany = 0;

				switch (_type) {
				case 1:
					x = 32743;
					y = 32862; // 1����
					x1 = 32752;
					y1 = 32809; // 2����

					x2 = 32809;
					y2 = 32813;
					x3 = 32811;
					y3 = 32854;
					x4 = 32860;
					y4 = 32858;// 3����
					break;
				case 2:
					x = 32743;
					y = 32733;
					x1 = 32752;
					y1 = 32680; // 2����

					x2 = 32809;
					y2 = 32685;
					x3 = 32811;
					y3 = 32724;
					x4 = 32860;
					y4 = 32728;// 3����
					break;
				case 3:
					x = 32743;
					y = 32605;
					x1 = 32752;
					y1 = 32552; // 2����

					x2 = 32809;
					y2 = 32557;
					x3 = 32811;
					y3 = 32596;
					x4 = 32860;
					y4 = 32598;// 3����
					break;
				case 4:
					x = 32935;
					y = 32605;
					x1 = 32943;
					y1 = 32552; // 2����

					x2 = 32999;
					y2 = 32557;
					x3 = 33000;
					y3 = 32596;
					x4 = 33050;
					y4 = 32598;// 3����
					break;
				}
				/*
				 * 1679 ���ƻ繫�� ��ħ : �� �˷��۽����� ������ ���Դϴ�. ������ �غ� �ϼ���. ���ƻ繫�� ��ħ :
				 * �༮���� ���� ���ɰ� ������ ���� ������ ������, �˼��� ���� �༮�� Ư�� �����ϼ���. ���ƻ繫�� ��ħ :
				 * �˷��۽����� ��Ǫ������ �Ƚ��� ��Ű�� ���� ���Ľ��� �İ��� ���ϵ� �Դϴ�. ī�ӻ繫�� ��ħ : ��Ǫ������ ����
				 * ������ ���߰� �� ������ ź�� �Ͽ����ϴ�.!!
				 */
				PaPooRaid PT = PaPooRaidSystem.getInstance().getAR(_mapid);
				// L1Party PT =
				// PaPooRaidSystem.getInstance().getAR(_mapid).getParty(_type);
				S_ServerMessage sm = new S_ServerMessage(1679);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(sm);
				}
				Thread.sleep(2000);
				sm = new S_ServerMessage(1680);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(sm);
				}

				Thread.sleep(2000);
				sm = new S_ServerMessage(1681);
				for (L1PcInstance pc : PT.getMembers()) {
					if (pc.getMapId() != _mapid) {
						continue;
					}
					pc.sendPackets(sm);
				}
				sm = null;
				Thread.sleep(2000);

				for (int i = 0; i < 40; i++) {
					ranid = random.nextInt(2);
					ranx = random.nextInt(15);
					rany = random.nextInt(15);
					swich = random.nextInt(2);
					if (swich == 0) {
						ranx *= -1;
						rany *= -1;
					}
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

	private static int �˷��۽��ް� = 4039012;
	private static int �˷��۽���� = 4039013;

	public boolean startRaid(L1PcInstance pc) {
		checkAR();
		if (_list.size() >= 15) {
			S_SystemMessage sm = new S_SystemMessage(
					"�Ƶ����� �� ���̻� ��Ǫ ��Ż�� ��ȯ�� �� �����ϴ�.");
			pc.sendPackets(sm);
			sm = null;
			return false;
		}

		ArrayList<L1Object> list = L1World.getInstance().getVisibleObjects(pc,
				0);
		if (list.size() > 0) {
			pc.sendPackets(new S_SystemMessage("�� ��ġ�� ��Ǫ ��Ż�� ��ȯ�� �� �����ϴ�."),
					true);
			return false;
		}
		// pc.getInventory().consumeItem(430116, 1);
		int id = blankMapId();

		PaPooRaid ar = new PaPooRaid(id);

		L1WorldMap.getInstance().cloneMap(1011, id);
		World.cloneMap(1011, id);

		PapooRaidSpawn.getInstance().fillSpawnTable(id, 0);

		L1NpcInstance npc = L1SpawnUtil.spawn2(pc.getX(), pc.getY(),
				pc.getMapId(), 4212016, 0, 7200 * 1000, id);
		L1FieldObjectInstance foi = (L1FieldObjectInstance) npc;
		foi.Potal_Open_pcid = pc.getId();

		L1SpawnUtil.spawn2(32921, 32727, (short) id, 4500103, 0, 0, id);

		L1SpawnUtil.spawn2(32942, 32671, (short) id, 4500107, 0, 0, id);
		_mapid2[id - 1011] = id;
		_list.put(id, ar);
		return true;
	}

	public void checkAR() {
		PaPooRaid ar = null;
		for (int i = 1011; i <= 1016; i++) {
			if (_list.containsKey(i)) {
				ar = _list.get(i);
				if (ar.getEndTime() <= System.currentTimeMillis()
						|| !ar.threadOn) {
					_list.remove(i);
					_mapid2[i - 1011] = 0;
				}
			}
		}
	}

	public PaPooRaid getAR(int id) {
		return _list.get(id);
	}

	/**
	 * �� �� ���̵� �����´�
	 * 
	 * @return
	 */
	public int blankMapId() {
		int mapid = 1011;
		int a0 = 1011;
		int a1 = 1012;
		int a2 = 1013;
		int a3 = 1014;
		int a4 = 1015;
		int a5 = 1016;
		if (_list.size() >= 1) {
			for (int id : _mapid2) {
				if (id == 1011) {
					a0 = 0;
				}
				if (id == 1012) {
					a1 = 0;
				}
				if (id == 1013) {
					a2 = 0;
				}
				if (id == 1014) {
					a3 = 0;
				}
				if (id == 1015) {
					a4 = 0;
				}
				if (id == 1016) {
					a5 = 0;
				}
			}
		}
		if (a0 != 0) {
			System.out.println("1011");
			return a0;
		}
		if (a1 != 0) {
			System.out.println("1012");
			return a1;
		}
		if (a2 != 0) {
			System.out.println("1013");
			return a2;
		}
		if (a3 != 0) {
			System.out.println("1014");
			return a3;
		}
		if (a4 != 0) {
			System.out.println("1015");
			return a4;
		}
		if (a5 != 0) {
			System.out.println("1016");
			return a5;
		}
		return mapid;
	}

	public int countRaidPotal() {
		return _list.size();
	}
}
