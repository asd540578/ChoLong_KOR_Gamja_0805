package l1j.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import l1j.server.GameSystem.GhostHouse;
import l1j.server.GameSystem.PetRacing;
import l1j.server.GameSystem.MiniGame.DeathMatch;
import l1j.server.Warehouse.ClanWarehouse;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.datatables.CharBuffTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Trade;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1FollowerInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;

public class quit_Q implements Runnable {
	private long time = 0;
	private final Queue<L1PcInstance> _queue;

	public quit_Q() {
		_queue = new ConcurrentLinkedQueue<L1PcInstance>();
		GeneralThreadPool.getInstance().execute(this);
	}

	public void requestWork(L1PcInstance name) {
		_queue.offer(name);
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(10L);
				synchronized (this) // Ÿ�̹� ������ 2�� ���� �����ϴ�. �ſ� ���� Ȯ���̱� �ϴٸ�.
									// �̷л�.-_-
				{
					L1PcInstance pc = _queue.peek();

					if (_queue.size() > 100) {
						if (System.currentTimeMillis() > time) {
							System.out.println("���� ť 100�� �̻� : "
									+ _queue.size());
							time = System.currentTimeMillis() + (1000 * 10);
						}
					}

					if (pc == null) {
						continue;
					}

					// System.out.println("123 :" +pc.getName());

					try {
						if (pc.isPrivateShop()) {
							pc.���������ۻ���(pc.getId());
						}

						if (Config.����ä�ø����() > 0) {
							for (L1PcInstance gm : Config.toArray����ä�ø����()) {
								if (gm.getNetConnection() == null) {
									Config.remove����(gm);
									continue;
								}
								if (gm == pc)
									continue;
								gm.sendPackets(new S_SystemMessage("\\fY["
										+ pc.getName() + "] (����) / ����:"
										+ pc.getAccountName()));
							}
						}

						if (pc.isGm()) {
							Config.remove��ü(pc);
						}
						pc.getNetConnection().getAccount().updateDGTime();
						pc.getNetConnection().getAccount().updateTam();
						pc.getNetConnection().getAccount().updateNcoin();

						pc.set_delete(true); // ������� �Ƚ�
						pc.setadFeature(1);
						pc.setDeathMatch(false);
						pc.setHaunted(false);
						pc.setPetRacing(false);

						// ����ϰ� ������(��) �Ÿ��� �ǵ���, ���� ���·� �Ѵ�
						if (pc.isDead()) {
							int[] loc = Getback.GetBack_Location(pc, true);
							pc.setX(loc[0]);
							pc.setY(loc[1]);
							pc.setMap((short) loc[2]);
							pc.setCurrentHp(pc.getLevel());
							pc.set_food(39); // 10%

							loc = null;
						}
						// �ڽ��� �� ��ó���� ����� �������� ��ġ ����
						if (pc.getClan() != null
								&& pc.getClan().getCastleId() > 0) {
							if (L1CastleLocation.checkInWarArea(pc.getClan()
									.getCastleId(), pc)) {
								int[] loc = L1CastleLocation.getCastleLoc(pc
										.getClan().getCastleId());
								pc.setX(loc[0]);
								pc.setY(loc[1]);
								pc.setMap((short) loc[2]);
								loc = null;
							}
						}

						if (pc.isGhost()) {
							pc.setX(pc._ghostSaveLocX);
							pc.setY(pc._ghostSaveLocY);
							pc.setMap((short) pc._ghostSaveMapId);
							pc.getMoveState().setHeading(pc._ghostSaveHeading);
						}

						// ����â�� ��뵵�� �ñ�ų� �����Ұ�� ����â�� ����� ����(���)
						ClanWarehouse clanWarehouse = null;
						L1Clan clan = L1World.getInstance().getClan(
								pc.getClanname());
						if (clan != null) {
							clanWarehouse = WarehouseManager.getInstance()
									.getClanWarehouse(clan.getClanName());
							clan.removeOnlineClanMember(pc.getName());
						}
						if (clanWarehouse != null)
							clanWarehouse.unlock(pc.getId());

						// Ʈ���̵带 �����Ѵ�
						if (pc.getTradeID() != 0) { // Ʈ���̵���
							L1Trade trade = new L1Trade();
							trade.TradeCancel(pc);
						}

						// ������
						if (pc.getFightId() != 0) {
							pc.setFightId(0);
							L1PcInstance fightPc = (L1PcInstance) L1World
									.getInstance().findObject(pc.getFightId());
							if (fightPc != null) {
								fightPc.setFightId(0);
								fightPc.sendPackets(new S_PacketBox(
										S_PacketBox.MSG_DUEL, 0, 0));
							}
						}

						// ��Ƽ�� ������
						if (pc.isInParty()) { // ��Ƽ��
							pc.getParty().leaveMember(pc);
						}

						// ä����Ƽ�� ������
						if (pc.isInChatParty()) { // ä����Ƽ��
							pc.getChatParty().leaveMember(pc);
						}

						if (DeathMatch.getInstance().isEnterMember(pc)) {
							DeathMatch.getInstance().removeEnterMember(pc);
						}
						if (GhostHouse.getInstance().isEnterMember(pc)) {
							GhostHouse.getInstance().removeEnterMember(pc);
						}
						if (PetRacing.getInstance().isEnterMember(pc)) {
							PetRacing.getInstance().removeEnterMember(pc);
						}

						// �ֿϵ����� ���� MAP�����κ��� �����
						if (pc.getPetList() != null && pc.getPetListSize() > 0) {
							for (Object petObject : pc.getPetList()) {
								if (petObject == null)
									continue;
								if (petObject instanceof L1PetInstance) {
									L1PetInstance pet = (L1PetInstance) petObject;
									pet.collect();
									int time = pet.getSkillEffectTimerSet()
											.getSkillEffectTimeSec(
													L1SkillId.STATUS_PET_FOOD);
									PetTable.getInstance().storePetFoodTime(
											pet.getId(), pet.getFood(), time);
									pet.getSkillEffectTimerSet()
											.clearSkillEffectTimer();
									pc.removePet((L1NpcInstance) pet);
									pet.deleteMe();
								} else if (petObject instanceof L1SummonInstance) {
									L1SummonInstance sunm = (L1SummonInstance) petObject;
									sunm.dropItem();
									pc.removePet((L1NpcInstance) sunm);
									sunm.deleteMe();
								}
							}
						}
						// ���� ������ ���� �ʻ����κ��� �����
						if (pc.getDollList() != null
								&& pc.getDollListSize() > 0) {
							for (L1DollInstance doll : pc.getDollList()) {
								if (doll != null)
									doll.deleteDoll();
							}
						}

						if (pc.getFollowerList() != null
								&& pc.getFollowerList().size() > 0) {
							L1FollowerInstance follower = null;
							for (Object followerObject : pc.getFollowerList()
									.values()) {
								if (followerObject == null)
									continue;
								follower = (L1FollowerInstance) followerObject;
								follower.setParalyzed(true);
								follower.spawn(follower.getNpcTemplate()
										.get_npcId(), follower.getX(), follower
										.getY(), follower.getMoveState()
										.getHeading(), follower.getMapId());
								follower.deleteMe();
							}
						}

						CharBuffTable.DeleteBuff(pc);
						CharBuffTable.SaveBuff(pc);
						pc.setNetConnection(null);
						pc.getSkillEffectTimerSet()
								.clearRemoveSkillEffectTimer();

						for (L1ItemInstance item : pc.getInventory().getItems()) {
							if (item != null && item.getCount() <= 0) {
								pc.getInventory().deleteItem(item);
							}
						}
						pc.setLogOutTime();
						pc.setOnlineStatus(0);
						pc.save();
						pc.saveInventory();
					} catch (Exception e) {
						e.printStackTrace();
					}

					_queue.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}