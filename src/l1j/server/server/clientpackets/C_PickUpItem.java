/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.server.clientpackets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import l1j.server.server.ActionCodes;
import l1j.server.server.GMCommands;
import l1j.server.server.datatables.LogTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Npc;
import server.LineageClient;

public class C_PickUpItem extends ClientBasePacket {

	private static final String C_PICK_UP_ITEM = "[C] C_PickUpItem";
	private Random _random = new Random();

	public C_PickUpItem(byte decrypt[], LineageClient client) throws Exception {
		super(decrypt);
		try {
			int x = readH();
			int y = readH();
			int objectId = readD();
			int pickupCount = readD();
			L1PcInstance pc = client.getActiveChar();

			if (pc == null)
				return;
			if (pc.getOnlineStatus() != 1) {
				S_Disconnect dis = new S_Disconnect();
				pc.sendPackets(dis, true);
				return;
			}
			if (isTwoLogin(pc)) {
				return;
			}
			if (pc.isGhost() || pc.isDead()) {
				return;
			}
			if (pc.isInvisble()) {
				return;
			} // �κ��� ����
			if (pc.isInvisDelay()) {
				return;
			} // �κ������� ����
				// if
				// (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.ABSOLUTE_BARRIER))
				// { return; }
			if (pc.getInventory().calcWeightpercent() >= 100) {
				pc.sendPackets(new S_ServerMessage(82)); // ���� �������� ����á���ϴ�.
				return;
			}
			L1Inventory groundInventory = L1World.getInstance().getInventory(x, y, pc.getMapId());
			L1Object object = groundInventory.getItem(objectId);

			// if(!(object instanceof L1ItemInstance))
			// return;

			if (object != null) {
				L1ItemInstance item = (L1ItemInstance) object;
				// ��۽ÿ� ������ �����ð��� �ʱ�ȭ �Ѵ�.
				item.init_DeleteItemTime();

				if (item.getItemOwner() != null) {
					if (item.getItemOwner().isInParty()) {
						if (!item.getItemOwner().getParty().isMember(pc)) {
							S_ServerMessage sm = new S_ServerMessage(623);
							pc.sendPackets(sm, true);
							return;
						}
					} else {
						if (item.getItemOwner().getId() != pc.getId()) {
							S_ServerMessage sm = new S_ServerMessage(623);
							pc.sendPackets(sm, true);
							return;
						}
					}
				}
				if (objectId != item.getId()) {
					return;
				}
				if (!item.isStackable() && pickupCount != 1) {
					return;
				}
				if (pickupCount <= 0 || item.getCount() <= 0) {
					groundInventory.deleteItem(item);
					return;
				}
				if (pickupCount > item.getCount()) {
					pickupCount = item.getCount();
				}

				if (pc.getLocation().getTileLineDistance(item.getLocation()) > 1) {
					return;
				}

				if (item.getItem().getItemId() == L1ItemId.ADENA) {
					L1ItemInstance inventoryItem = pc.getInventory().findItemId(L1ItemId.ADENA);
					int inventoryItemCount = 0;
					if (inventoryItem != null) {
						inventoryItemCount = inventoryItem.getCount();
					}
					// �ֿ� �Ŀ� 2 G�� �ʰ����� �ʰ� üũ
					if ((long) inventoryItemCount + (long) pickupCount > 20000000000L) {
						S_SystemMessage sm = new S_SystemMessage("�����ϰ� �ִ� �Ƶ����� 20���� �ʰ��ϰ� �˴ϴ�.");
						pc.sendPackets(sm, true);
						return;
					}
					LogTable.��ɾƵ�(pc, pickupCount);
				}

				pc.�÷��̾���� = pc.����_����;
				pc.���½ð� = System.currentTimeMillis() + 2000;
				// �뷮 �߷� Ȯ�� �� �޼��� �۽�
				if (pc.getInventory().checkAddItem(item, pickupCount) == L1Inventory.OK) {
					if (item.getX() != 0 && item.getY() != 0) {
						if (pc.isInParty()) { // ��Ƽ�� ���
							if (pc.getLocation().getTileLineDistance(pc.getLocation()) < 14) {
								// �ڵ��й� Ÿ���ΰ�?
								if (pc.getParty().getLeader().getPartyType() == 1 && item.isDropMobId() != 0) {
									List<L1PcInstance> _membersList = new ArrayList<L1PcInstance>();
									_membersList.add(pc);
									for (L1PcInstance realUser : L1World.getInstance().getVisiblePlayer(pc, -1)) {
										if (pc.getParty().isMember(realUser) && pc.getId() != realUser.getId()) {
											_membersList.add(realUser);
										}
									}
									// �������� ���� ���� ������
									int luckuyNum = _random.nextInt(_membersList.size());
									L1PcInstance luckyUser = _membersList.get(luckuyNum);
									// �Ƶ��� �ΰ�? �й�
									if (item.getItemId() == L1ItemId.ADENA || item.getItemId() == L1ItemId.HALPAS) {
										int divAden = pickupCount / _membersList.size();
										if (_membersList.size() > 1) {
											int modNum = pickupCount % _membersList.size();
											if (modNum == 0) {
												for (int row = 0; row < _membersList.size(); row++) {
													groundInventory.tradeItem(item, divAden,
															_membersList.get(row).getInventory());
													if (item.isDropMobId() != 0) {
														L1Npc npc = NpcTable.getInstance()
																.getTemplate(item.isDropMobId());
														for (L1PcInstance partymember : pc.getParty().getMembers()) {
															if (partymember.RootMent) {
																partymember.sendPackets(new S_ServerMessage(813,
																		npc.get_name(), item.getLogName(),
																		partymember.getName()));
															}
															item.setDropMobId(0);
														}
													}
												}
											} else {
												if (pickupCount < _membersList.size()) {
													groundInventory.tradeItem(item, pickupCount, pc.getInventory());
												} else {
													for (int row = 0; row < _membersList.size(); row++) {
														if (pc.getId() == _membersList.get(row).getId()) {
															groundInventory.tradeItem(item, divAden + modNum,
																	pc.getInventory());
														} else {
															groundInventory.tradeItem(item, divAden,
																	_membersList.get(row).getInventory());
															// �� ���ִ°� ��Ʈ�� �����ִ�??
														}
													}
												}
											}
										} else {
											groundInventory.tradeItem(item, pickupCount, pc.getInventory());
										}
									} else {// �ƴϸ� �ٸ� �������ΰ�?
										groundInventory.tradeItem(item, pickupCount, luckyUser.getInventory());
										if (item.isDropMobId() != 0) {
											L1Npc npc = NpcTable.getInstance().getTemplate(item.isDropMobId());
											for (L1PcInstance partymember : pc.getParty().getMembers()) {
												if (partymember.RootMent) {
													partymember.sendPackets(new S_ServerMessage(813, npc.get_name(),
															item.getLogName(), luckyUser.getName()));
												}
												item.setDropMobId(0);
											}
										}
									}
								} else { // �ƴϸ� �׳��ΰ�?
									groundInventory.tradeItem(item, pickupCount, pc.getInventory());
									if (item.isDropMobId() != 0) {
										L1Npc npc = NpcTable.getInstance().getTemplate(item.isDropMobId());
										for (L1PcInstance partymember : pc.getParty().getMembers()) {
											if (partymember.RootMent) {
												partymember.sendPackets(new S_ServerMessage(813, npc.get_name(),
														item.getLogName(), pc.getName()));
											}
											item.setDropMobId(0);
										}
									}
								}
							}
							pc.getLight().turnOnOffLight();
							// �����������Ŵ
							pc.saveInventory();
							// �����������Ŵ

						} else { 
							groundInventory.tradeItem(item, pickupCount, pc.getInventory());
							pc.getLight().turnOnOffLight();
						}
						S_AttackPacket sap = new S_AttackPacket(pc, objectId, ActionCodes.ACTION_Pickup);
						pc.sendPackets(sap, true);
						if (!pc.isGmInvis()) {
							S_AttackPacket sap2 = new S_AttackPacket(pc, objectId, ActionCodes.ACTION_Pickup);
							Broadcaster.broadcastPacket(pc, sap2, true);
						}
					}
				}
				// item null
			} else {
				if (GMCommands.����üũ) {
					boolean ck = false;
					for (L1PcInstance temp : L1World.getInstance().getAllPlayers()) {
						if (temp.getInventory().getItem(objectId) != null) {
							System.out.println("���� üũ > �ٴ� ��� - ������ ������ : " + temp.getName());
							ck = true;
							break;
						}
					}
					if (!ck) {
						for (L1Object obj : L1World.getInstance().getAllItem()) {
							if (!(obj instanceof L1ItemInstance)) {
								continue;
							}
							L1ItemInstance item = (L1ItemInstance) obj;
							if (item.getId() == objectId) {
								System.out.println("���� üũ > �ٴ� ��� - ������ �ٴ� : x>" + item.getX() + " y>" + item.getY()
										+ " map>" + item.getMapId());
								return;
							}
						}
						System.out.println("���� üũ > �ٴ� ��� - �������� �ʴ� ������ objectId :" + objectId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clear();
		}
	}

	private boolean isTwoLogin(L1PcInstance c) {
		boolean bool = false;
		try {
			for (L1PcInstance target : L1World.getInstance().getAllPlayers()) {
				// ����PC �� ����
				if (target == null)
					continue;
				if (target.noPlayerCK)
					continue;
				if (target.�����)
					continue;
				//
				if (c.getId() != target.getId() && !target.isPrivateShop()) {
					if (c.getNetConnection().getAccountName()
							.equalsIgnoreCase(target.getNetConnection().getAccountName())) {
						bool = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			return true;
		}
		return bool;
	}

	@Override
	public String getType() {
		return C_PICK_UP_ITEM;
	}
}
