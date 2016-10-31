package l1j.server.GameSystem.Lind;

import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Random;

import javolution.util.FastMap;
import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NPCPack;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_UseAttackSkill;

public class LindThread extends Thread {

	private FastTable<Lind> _list;
	private Random _rnd = new Random(System.nanoTime());

	private static LindThread _instance;

	public static LindThread get() {
		if (_instance == null)
			_instance = new LindThread();
		return _instance;
	}

	public LindThread() {
		super("l1j.server.GameSystem.LindThread");
		_list = new FastTable<Lind>();
		start();
	}

	public void run() {
		int size = 0;
		while (true) {
			try {
				Lind[] list = null;
				synchronized (_list) {
					if ((size = _list.size()) > 0) {
						list = _list.toArray(new Lind[size]);
					}
				}
				if (size > 0) {
					for (Lind lind : list) {
						if (lind == null) {
							_list.remove(lind);
							continue;
						}
						if (lind.getEndTIme() < System.currentTimeMillis())
							quit(lind);

						/** ���� �� üũ **/
						mapUserCheck(lind);

						if (lind.Sleep > System.currentTimeMillis())
							continue;
						Sleep_Setting(lind);
						switch (lind.Step) {
						case 1: // ���� ����
							if (lind.Sub_Step == 1)
								SystemChat(lind, "�������� : ���� ���� ������ ���� �ϴ°�?");
							else if (lind.Sub_Step == 2)
								SystemChat(lind, "�������� : �ɷ��Ͻ� �� ���� ȭ���� �Ϸ��� �ǰ�?");
							else if (lind.Sub_Step == 3)
								SystemChat(lind, "�������� : ����� �ΰ��� �̱�...");
							else if (lind.Sub_Step == 4)
								SystemChat(lind, "�������� : �� ���������� ȭ���� �� �밡�� ġ�� ���̴�.");
							else if (lind.Sub_Step == 5)
								lind.dragon_lind = LindSpawn.getInstance().fillSpawnTable(lind.getMap().getId(), 1); // ����
																														// ����
							lind.Sub_Step++;
							if (lind.Sub_Step == 6) {
								lind.Step = 2;
								lind.Sub_Step = 0;
							}
							break;
						case 2: // ���� ����
							if (lind.dragon_lind == null || lind.dragon_lind.isDead() || lind.dragon_lind._destroyed) {
								lind.Step = 3;
								// giveItem(lind, 787878);
								// SystemChat(lind,
								// "�������� �� �޾Ƴ� �巡���� ������ �־����ϴ�.");
							}
							break;
						case 3: // ���� 2�� ����
							if (lind.Sub_Step == 0)
								SystemChat(lind, "�������� : ���ҷӱ���! ������� ������� ���� ���� ��ȸ�ϰ� ������ְڴ�!");
							else if (lind.Sub_Step == 1)
								SystemChat(lind, "�������� : �׷��� �����̱���! ������ �������� ��ƿ �� ������?");
							else if (lind.Sub_Step == 2)
								SystemChat(lind, "�������� : �������� ���� Ǯ�� ���ߴµ�, ��ƺ����� ����!!");
							else if (lind.Sub_Step == 3)
								Lind_level2_setting(lind);
							else if (lind.Sub_Step == 5)
								cloud_npcChat(lind, "���������� : ������������ ���ҿ� ħ���� �� �����ΰ�?");
							else if (lind.Sub_Step == 6)
								cloud_npcChat(lind, "���������� : ���..���� ������������ �ɱ⸦ �����ϰ� �ϴٴ�!");
							else if (lind.Sub_Step == 7)
								cloud_npcChat(lind, "���������� : ����! �������� �ʰڴ�!");
							else if (lind.Sub_Step == 8)
								cloud_npcChat(lind, "���������� : ������ �����̿� ������ ����!");

							if (lind.Sub_Step != 4 && lind.Sub_Step != 9)
								lind.Sub_Step++;
							if (lind.Sub_Step >= 4) {
								if (lind.dragon_lind != null
										&& (lind.dragon_lind.getMaxHp() / 2) > lind.dragon_lind.getCurrentHp()
										&& !((L1MonsterInstance) lind.dragon_lind).lind_level2_cloud) {
									((L1MonsterInstance) lind.dragon_lind).lind_level2_cloud = true;
									Broadcaster.broadcastPacket(lind.dragon_lind,
											new S_NpcChatPacket(lind.dragon_lind, "�� ���̸�! ������ ��..", 0), true);
									lind.cloud_list = LindSpawn.getInstance().fillSpawnTable(lind.getMap().getId(), 4,
											true); // ���� ������
									lind.Sub_Step++;
								}
								if (lind.dragon_lind == null || lind.dragon_lind.isDead()
										|| lind.dragon_lind._destroyed) {
									Lind_level2_die(lind);
									lind.Step = 4;
									lind.Sub_Step = 0;
									// giveItem(lind, 787878);
									// SystemChat(lind,
									// "�������� �� �޾Ƴ� �巡���� ������ �־����ϴ�.");
								}
								continue;
							}
							break;
						case 4: // ���� 3��
							if (lind.Sub_Step == 0)
								SystemChat(lind, "�������� : �̷� ������... ����� �͵��̿�~~ ���� ���� �����ϴ� �ǰ�?");
							else if (lind.Sub_Step == 1)
								SystemChat(lind, "�������� : �� �׷� ���� �߹����� ���ƶ�~~");
							else if (lind.Sub_Step == 2)
								SystemChat(lind, "�������� : �� �༮���� ������� ſ�ϰ� �� ���̴�.");
							else if (lind.Sub_Step == 3)
								SystemChat(lind, "�������� : �׳���� ���̻� ������ ���� ���̴�. �ٽ� ��������!");
							else if (lind.Sub_Step == 4)
								lind.dragon_lind = LindSpawn.getInstance().fillSpawnTable(lind.getMap().getId(), 3); // ����
																														// ����
							else if (lind.Sub_Step == 5) {
								if (lind.dragon_lind == null || lind.dragon_lind.isDead()
										|| lind.dragon_lind._destroyed) {
									lind.Step = 6;
									lind.Sub_Step = 0;
									continue;
								}
								if (((L1MonsterInstance) lind.dragon_lind).lind_fly) {
									lind.Step = 5;
									lind.Sub_Step = 0;
								}
								continue;
							}
							lind.Sub_Step++;
							break;
						case 5: // ���� ����
							if (lind.dragon_lind == null || lind.dragon_lind.isDead() || lind.dragon_lind._destroyed) {
								lind.Step = 6;
								lind.Sub_Step = 0;
								continue;
							}
							if (lind.Sub_Step == 0) {
							} else if (lind.Sub_Step < 11) {
								int x = 32838 + _rnd.nextInt(22);
								int y = 32866 + _rnd.nextInt(22);
								for (L1PcInstance pc : lind.getMember()) {
									S_UseAttackSkill uas = new S_UseAttackSkill(pc, 0, 10, x, y, 13, false);
									S_UseAttackSkill uas2 = new S_UseAttackSkill(pc, 0, 8118, x, y, 13, false);
									pc.sendPackets(uas);
									uas = null;
									pc.sendPackets(uas2);
									uas2 = null;
									S_PacketBox pb = new S_PacketBox(83, 2);
									pc.sendPackets(pb);
									pb = null;
									if (pc.getX() == x && pc.getY() == y) {
										if (!pc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
												&& !pc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
												&& !pc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH))
											pc.receiveDamage(lind.dragon_lind, 5000, true);
									}
								}
							} else if (lind.Sub_Step == 11) {
								if (lind.dragon_lind.getHiddenStatus() == L1NpcInstance.HIDDEN_STATUS_FLY) {
									lind.dragon_lind.setHiddenStatus(L1NpcInstance.HIDDEN_STATUS_NONE);
									Broadcaster.broadcastPacket(lind.dragon_lind,
											new S_DoActionGFX(lind.dragon_lind.getId(), 11), true);
									lind.dragon_lind.setActionStatus(11);
									Broadcaster.broadcastPacket(lind.dragon_lind, new S_NPCPack(lind.dragon_lind),
											true);
								}
							} else {
								continue;
							}
							lind.Sub_Step++;
							break;
						case 6: // ���� ����
							if (lind.Sub_Step == 0)
								SystemChat(lind, "�������� : �̷�����!! ũ�ƾ�..������� �躻 ���� �ʹ����� ���������... ");
							else if (lind.Sub_Step == 1)
								SystemChat(lind, "�������� : �ƾ�~!! ���� ��Ӵ� �Ƿ��̿� ���� ����� �ּҼ�...");
							else if (lind.Sub_Step == 2) {
								// ����
								health(lind);
								giveItem(lind, 5000065);
								// SystemChat(lind, "���������� �����鼭 ��ǥ�� ������ϴ�.");
							} else if (lind.Sub_Step == 3)
								SystemChat(lind, "�ý��� �޽��� : 10�� �Ŀ� �ڷ���Ʈ �մϴ�.");
							else if (lind.Sub_Step == 4)
								SystemChat(lind, "�ý��� �޽��� : 5�� �Ŀ� �ڷ���Ʈ �մϴ�.");
							else if (lind.Sub_Step == 5)
								SystemChat(lind, "�ý��� �޽��� : 4�� �Ŀ� �ڷ���Ʈ �մϴ�.");
							else if (lind.Sub_Step == 6)
								SystemChat(lind, "�ý��� �޽��� : 3�� �Ŀ� �ڷ���Ʈ �մϴ�.");
							else if (lind.Sub_Step == 7)
								SystemChat(lind, "�ý��� �޽��� : 2�� �Ŀ� �ڷ���Ʈ �մϴ�.");
							else if (lind.Sub_Step == 8)
								SystemChat(lind, "�ý��� �޽��� : 1�� �Ŀ� �ڷ���Ʈ �մϴ�.");
							else {
								lind.Step = 7;
								lind.Sub_Step = 0;
								continue;
							}
							lind.Sub_Step++;
							break;
						case 7: // ����
							home(lind);
							quit(lind);

							break;
						default:
							break;
						}
						// System.out.println("���� >> "+lind.Step);
					}
				}
				list = null;
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** ���� & ������ **/
	private void health(Lind lind) {
		for (L1PcInstance pc : lind.getMember()) {
			pc.sendPackets(new S_SkillSound(pc.getId(), 7783), true);
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7783), true);
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.DRAGONRAID_BUFF, (86400 * 2) * 1000);
			Timestamp deleteTime = new Timestamp(System.currentTimeMillis() + (86400000 * Config.���̵�ð�));// 7��
			pc.getNetConnection().getAccount().setDragonRaid(deleteTime);
			pc.getNetConnection().getAccount().updateDragonRaidBuff();
			pc.sendPackets(new S_PacketBox(S_PacketBox.�巡�ﷹ�̵����, 86400 * 2), true);
		}
		L1World.getInstance().broadcastServerMessage("�������� ��ħ : ���������� ������ ���� ������ ź�� �Ͽ����ϴ�.!!");
	}

	private void giveItem(Lind lind, int id) {
		for (L1PcInstance pc : lind.getMember()) {
			if (pc == null)
				continue;
			createNewItem(lind, pc, id, 1);
		}
	}

	private boolean createNewItem(Lind lind, L1PcInstance pc, int item_id, int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // ���� �� ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
			}
			for (L1PcInstance temp : lind.getMember()) {
				temp.sendPackets(new S_ServerMessage(813, "��������", item.getName(), temp.getName()));
			}
			// S_ServerMessage sm = new S_ServerMessage(403, item.getLogName());
			// pc.sendPackets(sm); // %0�� �տ� �־����ϴ�.
			// sm = null;
			return true;
		} else {
			return false;
		}
	}

	private void home(Lind lind) {
		for (L1PcInstance pc : lind.getMember()) {
			if (pc == null)
				continue;
			pc.dx = 33718;
			pc.dy = 32506;
			pc.dm = (short) 4;
			pc.dh = 4;
			pc.setTelType(7);
			S_SabuTell st = new S_SabuTell(pc);
			pc.sendPackets(st);
			st = null;
		}
	}

	/** ���� �� ���� ó�� **/
	public void quit(Lind lind) {
		for (L1FieldObjectInstance npc : L1World.getInstance().getAllField()) {
			if (npc.moveMapId == lind.getMap().getId()) {
				npc.deleteMe();
			}
		}
		remove(lind);
		LindRaid.get().quit(lind.getMap());
		Object_Delete(lind);
		lind.clear();
	}

	private void Object_Delete(Lind p) {
		for (L1Object ob : L1World.getInstance().getVisibleObjects(p.getMap().getId()).values()) {
			if (ob == null || ob instanceof L1DollInstance || ob instanceof L1SummonInstance
					|| ob instanceof L1PetInstance)
				continue;
			if (ob instanceof L1ItemInstance) {
				L1ItemInstance obj = (L1ItemInstance) ob;
				L1Inventory groundInventory = L1World.getInstance().getInventory(obj.getX(), obj.getY(),
						obj.getMapId());
				groundInventory.removeItem(obj);
			} else if (ob instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) ob;
				npc.deleteMe();
			}
		}
	}

	public void remove(Lind lind) {
		_list.remove(lind);
		// System.out.println("���� > "+lind.getMap().getId());
	}

	public void add(Lind lind) {
		_list.add(lind);
		// System.out.println("���� > "+lind.getMap().getId());
	}

	public Lind getLind(int mapid) {
		for (Lind lind : _list) {
			if (lind == null)
				continue;
			if (lind.getMap().getId() == mapid)
				return lind;
		}
		return null;
	}

	private void cloud_npcChat(Lind lind, String chat) {
		if (lind.cloud_list == null || lind.cloud_list.size() == 0)
			return;
		for (FastMap.Entry<String, L1NpcInstance> e = lind.cloud_list.head(), mapEnd = lind.cloud_list
				.tail(); (e = e.getNext()) != mapEnd;) {
			L1NpcInstance npc = e.getValue();
			if (npc == null || npc._destroyed || npc.isDead())
				continue;
			Broadcaster.broadcastPacket(npc, new S_NpcChatPacket(npc, chat, 0), true);
		}
	}

	/** �ش� �� ���� üũ ��ȯ or ���̵� ������� ����Ʈ���� ���� **/
	public void mapUserCheck(Lind lind) {
		boolean ck = false;
		boolean ck2 = false;
		for (L1PcInstance pc : lind.getMember()) {
			if (pc == null || pc.getMapId() != lind.getMap().getId() || pc.getNetConnection() == null) {
				ck2 = true;
			} else if ((pc.getX() >= 32825 && pc.getX() <= 32867) && (pc.getY() >= 32857 && pc.getY() <= 32899)) {
				ck = true;
				lind.mapckCount = 0;
				// System.out.println("�ȿ� ���� ����");
			}
		}
		if ((lind.Step > 0 && lind.Step < 6) && !ck) {
			if (lind.mapckCount++ > 3) {
				Collection<L1Object> list = L1World.getInstance().getVisibleObjects(lind.getMap().getId()).values();
				for (L1Object obj : list) {
					if (obj == null || !(obj instanceof L1MonsterInstance))
						continue;
					L1MonsterInstance mon = (L1MonsterInstance) obj;
					mon.deleteMe();
				}
				lind.Step = 0;
				lind.mapckCount = 0;
				lind.Sleep = System.currentTimeMillis();
				// System.out.println("�ϵ� ��� ����");
			}
		}
		if (ck2) {
			if (lind.MembermapckCount++ > 3) {
				for (L1PcInstance pc : lind.getMember()) {
					if (pc == null || pc.getMapId() != lind.getMap().getId() || pc.getNetConnection() == null)
						lind.removeMember(pc);
				}
				lind.MembermapckCount = 0;
			}
		}

	}

	private void Sleep_Setting(Lind lind) {
		long time = 0;
		switch (lind.Step) {
		case 1:
			if (lind.Sub_Step == 0)
				time = 90000;
			else if (lind.Sub_Step == 4)
				time = 5000;
			else
				time = 2000;
			break;
		case 3:
			if (lind.Sub_Step >= 0 && lind.Sub_Step <= 2)
				time = 5000;
			else if (lind.Sub_Step >= 5 && lind.Sub_Step <= 7)
				time = 2000;
			break;
		case 4:
			if (lind.Sub_Step >= 0 && lind.Sub_Step <= 3)
				time = 5000;
			break;
		case 5:
			if (lind.Sub_Step >= 0 && lind.Sub_Step <= 10)
				time = 2000;
			break;
		case 6:
			if (lind.Sub_Step >= 0 && lind.Sub_Step <= 1)
				time = 5000;
			else if (lind.Sub_Step == 2)
				time = 10000;
			else if (lind.Sub_Step == 3)
				time = 5000;
			break;
		default:
			break;
		}
		lind.Sleep = System.currentTimeMillis() + time;
	}

	/*
	 * private void ServerChat(Lind p, int msg){ S_ServerMessage sm = new
	 * S_ServerMessage(msg); for(L1PcInstance pc : p.getMember()){ if(pc !=
	 * null){ pc.sendPackets(sm); } } sm = null; }
	 */

	private void SystemChat(Lind p, String msg) {
		S_SystemMessage sm = new S_SystemMessage(msg);
		for (L1PcInstance pc : p.getMember()) {
			if (pc != null) {
				pc.sendPackets(sm);
			}
		}
		sm = null;
	}

	private void Lind_level2_setting(Lind lind) {
		lind.lind_level2 = LindSpawn.getInstance().fillSpawnTable(lind.getMap().getId(), 2, true); // �⺻
																									// NPC
																									// ����
		int c = _rnd.nextInt(lind.lind_level2.size());
		int c2 = 0;
		for (FastMap.Entry<String, L1NpcInstance> e = lind.lind_level2.head(), mapEnd = lind.lind_level2
				.tail(); (e = e.getNext()) != mapEnd;) {
			if (c2 == c) {
				lind.dragon_lind = e.getValue();
			}
			c2++;
		}
	}

	private void Lind_level2_die(Lind lind) {
		for (FastMap.Entry<String, L1NpcInstance> e = lind.lind_level2.head(), mapEnd = lind.lind_level2
				.tail(); (e = e.getNext()) != mapEnd;) {
			if (e.getValue() == null || e.getValue().isDead() || e.getValue()._destroyed)
				continue;
			e.getValue().deleteMe();
		}
	}
}
