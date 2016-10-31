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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

import l1j.server.Config;
import l1j.server.GameSystem.NpcBuyShop.NpcBuyShop;
import l1j.server.Warehouse.ClanWarehouse;
import l1j.server.Warehouse.ElfWarehouse;
import l1j.server.Warehouse.PackageWarehouse;
import l1j.server.Warehouse.PrivateWarehouse;
import l1j.server.Warehouse.SupplementaryService;
import l1j.server.Warehouse.Warehouse;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.GMCommands;
import l1j.server.server.datatables.ClanHistoryTable;
import l1j.server.server.datatables.LogTable;
import l1j.server.server.datatables.NpcShopTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.model.L1BugBearRace;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.shop.L1Shop;
import l1j.server.server.model.shop.L1ShopBuyOrderList;
import l1j.server.server.model.shop.L1ShopSellOrderList;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_ItemStatus;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Pet;
import l1j.server.server.templates.L1PrivateShopBuyList;
import l1j.server.server.templates.L1PrivateShopSellList;
import server.LineageClient;
import server.manager.eva;

public class C_ShopAndWarehouse extends ClientBasePacket {
	private final int TYPE_BUY_SHP = 0; // ���� or ���� ���� ���
	private final int TYPE_SEL_SHP = 1; // ���� or ���� ���� �ȱ�
	private final int TYPE_PUT_PWH = 2; // ���� â�� �ñ��
	private final int TYPE_GET_PWH = 3; // ���� â�� ã��
	private final int TYPE_PUT_CWH = 4; // ���� â�� �ñ��
	private final int TYPE_GET_CWH = 5; // ���� â�� ã��
	private final int TYPE_PUT_EWH = 8; // ���� â�� �ñ��
	private final int TYPE_GET_EWH = 9; // ���� â�� ã��
	private final int TYPE_GET_MWH = 10; // ��Ű�� â�� ã��
	private final int TYPE_GET_PET = 12; // �� ã��
	private final int TYPE_GET_SSH = 20; // �ΰ����� â��

	public C_ShopAndWarehouse(byte abyte0[], LineageClient clientthread)
			throws Exception {
		super(abyte0);
		try {
			/*
			 * 49 33 e4 69 1b 00 ����ƮŸ�� 01 ������ 00 ���� 00 00 00 00 01 00 00 00
			 * I3.i............ 1b 69 f2 35 00 00 00 00 .i.5....
			 */
			int npcObjectId = readD();
			int resultType = readC();
			int size = readC();
			@SuppressWarnings("unused")
			int unknown = readC();
			if (size < 0)
				return;

			L1PcInstance pc = clientthread.getActiveChar();
			if (pc.getOnlineStatus() == 0 || isTwoLogin(pc)) {
				clientthread.kick();
				clientthread.close();
				return;
			}

			int npcId = 0;
			String npcImpl = "";
			boolean isPrivateShop = false;
			L1Object findObject = L1World.getInstance().findObject(npcObjectId);
			if (findObject != null) { // 15��
				int diffLocX = Math.abs(pc.getX() - findObject.getX());
				int diffLocY = Math.abs(pc.getY() - findObject.getY());
				if (diffLocX > 15 || diffLocY > 15) {
					return;
				}

				if (findObject instanceof L1NpcInstance) {
					L1NpcInstance targetNpc = (L1NpcInstance) findObject;
					npcId = targetNpc.getNpcTemplate().get_npcId();
					npcImpl = targetNpc.getNpcTemplate().getImpl();
				} else if (findObject instanceof L1PcInstance) {
					isPrivateShop = true;
				}
			}
			if (resultType == TYPE_BUY_SHP || resultType == TYPE_GET_PWH
					|| resultType == TYPE_GET_CWH || resultType == TYPE_GET_EWH
					|| resultType == TYPE_GET_MWH) {
				if (pc.getInventory().calcWeightpercent() >= 99) {
					pc.sendPackets(new S_ServerMessage(81)); // ���� �������� ����á���ϴ�.
					return;
				}
			}

			if (npcObjectId == 7626) {
				npcId = 7626;
				npcImpl = "L1Merchant";
			}

			// System.out.println("123");
			switch (resultType) {
			case TYPE_BUY_SHP: // ���� or ���� ���� ���
				// System.out.println("npcid = "+npcId+"size :"+size);
				if (npcId == 70035 || npcId == 70041 || npcId == 70042) {
					int status = L1BugBearRace.getInstance().getBugRaceStatus();
					boolean chk = L1BugBearRace.getInstance().buyTickets;
					if (status != L1BugBearRace.STATUS_READY || chk == false) {
						return;
					}
				}
				
				if (pc.getClan() == null && pc.getLevel() >= Config.��������) {
					pc.sendPackets(new S_SystemMessage(Config.�������� + "���� �̻��� ������ ������ ������ �̿��� �� �����ϴ�."));
					if (pc.isGm()) {
					//	pc.sendPackets(new S_SystemMessage("������ ��ڴ� �ʤ���"));
					} else {
						return;
					}
				}
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Merchant"))
					buyItemFromShop(pc, npcId, size);
				if (size != 0 && npcImpl.equalsIgnoreCase("L1NpcShop")) {
					buyItemFromNpcShop(pc, npcId, size);
					break;
				}

				if (size != 0 && isPrivateShop)
					buyItemFromPrivateShop(pc, findObject, size);
				break;

			case TYPE_SEL_SHP: // ���� or ���� ���� �ȱ�

				if (size != 0 && npcImpl.equalsIgnoreCase("L1Merchant"))
					sellItemToShop(pc, npcId, size);
				if (size != 0 && npcImpl.equalsIgnoreCase("L1NpcShop")) {
					sellItemToNpcShop(pc, npcId, size);
					break;
				}
				if (size != 0 && isPrivateShop)
					sellItemToPrivateShop(pc, findObject, size);
				break;
			case TYPE_PUT_PWH: // ���� â�� �ñ��
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					putItemToPrivateWarehouse(pc, size);
				break;
			case TYPE_GET_PWH: // ���� â�� ã��
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					getItemToPrivateWarehouse(pc, size);
				break;
			case TYPE_PUT_CWH: // ���� â�� �ñ��
				if (npcImpl.equalsIgnoreCase("L1Dwarf"))
					putItemToClanWarehouse(pc, size);
				break;
			case TYPE_GET_CWH: // ���� â�� ã��
				if (npcImpl.equalsIgnoreCase("L1Dwarf"))
					getItemToClanWarehouse(pc, size);
				break;
			case TYPE_PUT_EWH: // ���� â�� �ñ��
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					putItemToElfWarehouse(pc, size);
				break;
			case TYPE_GET_EWH: // ���� â�� ã��
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					getItemToElfWarehouse(pc, size);
				break;
			case TYPE_GET_MWH: // ��Ű�� â�� ã��
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					getItemToPackageWarehouse(pc, size);
				break;
			case TYPE_GET_PET: // �� ã��
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Merchant"))
					getPet(pc, size);
				break;
			case TYPE_GET_SSH: // �ΰ� ���� â��
				if (size != 0 && pc == findObject)
					getItemToSupplementaryService(pc, size);
				break;
			default:
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	private void getItemToSupplementaryService(L1PcInstance pc, int size) {
		L1ItemInstance item = null;
		SupplementaryService warehouse = WarehouseManager.getInstance()
				.getSupplementaryService(pc.getAccountName());
		if (warehouse == null)
			return;
		if (size > 100)
			return;
		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();
			item = warehouse.getItem(objectId, count);
			if (!isAvailableTrade(pc, objectId, item, count))
				break;
			if (count > item.getCount())
				count = item.getCount();
			if (!isAvailablePcWeight(pc, item, count))
				break;
			// if(!hasAdena(pc)) break;
			ArrayList<String> name = new ArrayList<String>();
			name = L1PcInstance.getPCs(pc.getAccountName());
			for (L1PcInstance _pc : L1World.getInstance().getAllPlayers()) {
				if (_pc.getName() == pc.getName()) {
					continue;
				}
				if (name.contains(_pc.getName())) {
					if (_pc.getNetConnection() != null) {
						_pc.getNetConnection().close();
					}
				}
			}
			warehouse.tradeItem(item, count, pc.getInventory());
			int chargeCount = item.getItem().getMaxChargeCount();
			int itemId = item.getItemId();
			switch (itemId) {
			case 5000121:
				Random random = new Random(System.nanoTime());
				chargeCount -= random.nextInt(5);
				break;
			case 40903:
			case 40904:
			case 40905:
				chargeCount = itemId - 40902;
				break;
			case 40906:
				chargeCount = 5;
				break;
			case 40907:
			case 40908:
				chargeCount = 20;
				break;
			}
			item.setChargeCount(chargeCount);
			if (item.getItem().getType2() == 0 && item.getItem().getType() == 2) { // light
				item.setRemainingTime(item.getItem().getLightFuel());
			} else if (itemId >= 60173 && itemId <= 60176) {
				item.setRemainingTime(3600 * 5);
			} else {
				item.setRemainingTime(item.getItem().getMaxUseTime());
			}
			if (itemId == L1ItemId.DRAGON_KEY) {// �巡�� Ű
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(
						System.currentTimeMillis() + 259200000);// 3��
				item.setEndTime(deleteTime);
			}
			if (itemId == 40312 || itemId == 49312) {// ��������
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 4));// 4�ð�
				item.setEndTime(deleteTime);
			}
			if (itemId == 20344 || (itemId >= 21113 && itemId <= 21120)
					|| (itemId >= 60350 && itemId <= 60352)) { // �䳢����, ���ɾǼ�(����)
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 3));// 3�ð�
				item.setEndTime(deleteTime);
			} else if (itemId == 600234
					|| itemId == 60009
					|| itemId == 60010
					|| itemId == 21092
					|| itemId == 60061
					|| // �޸�, ų�� ��༭, �丮�����, ��������
					(itemId >= 425000 && itemId <= 425002)
					|| // ����� ��
					(itemId >= 450000 && itemId <= 450007) // ����� ����
					|| itemId == 430003 || itemId == 430505 || itemId == 430506
					|| itemId == 41915 || itemId == 5000034 || itemId == 60233
					|| (itemId >= 21125 && itemId <= 21136)
					|| (itemId >= 21139 && itemId <= 21156)) { // �ô�, ��̾�,
																// ��������, ����ƺ�,
																// ��ƾ
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 24 * 7));// 7��
				item.setEndTime(deleteTime);
			} else if (itemId == 60011 || itemId == 60014) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 9));// 9�ð�
				item.setEndTime(deleteTime);
			} else if (itemId == 60012 || itemId == 60015) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 17));// 17�ð�
				item.setEndTime(deleteTime);
			} else if (itemId == 60013 || itemId == 60016) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 24));// 24�ð�
				item.setEndTime(deleteTime);
			} else if (itemId == 21094) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis() + 3600000);// 1�ð�
				item.setEndTime(deleteTime);
			} else if (itemId == 21157) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis() + 1800000);// 30��
				item.setEndTime(deleteTime);
			} else if ((itemId >= 267 && itemId <= 274)
					|| (itemId >= 21158 && itemId <= 21165)) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 24 * 3));// 3��
				item.setEndTime(deleteTime);
				// ����۵�
				/*
				 * }else if(itemId == 7 || itemId == 35 || itemId == 48 ||
				 * itemId == 73 || itemId == 105 || itemId == 120 || itemId ==
				 * 147 || itemId == 156 || itemId == 174 || itemId == 175 ||
				 * itemId == 224 || itemId == 20028 || itemId == 20082 || itemId
				 * == 20126 || itemId == 20173 || itemId == 20206 || itemId ==
				 * 20232 || itemId == 20282 || itemId == 201261 || itemId ==
				 * 21098 || (itemId >= 21102 && itemId <= 21112) || itemId ==
				 * 21254){ Timestamp deleteTime = null; deleteTime = new
				 * Timestamp(System.currentTimeMillis() + (3600000*24*7));// 3��
				 * item.setEndTime(deleteTime);
				 */
			} else if ((itemId >= 21099 && itemId <= 21112) || itemId == 21254
					|| itemId == 20082 || itemId == 7 || itemId == 35
					|| itemId == 48 || itemId == 73 || itemId == 105
					|| itemId == 120 || itemId == 147 || itemId == 7232
					|| itemId == 156 || itemId == 174 || itemId == 175
					|| itemId == 224) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 24 * 7));// 3��
				item.setEndTime(deleteTime);
			} else if (itemId == 60319) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 22));// 22�ð�
				item.setEndTime(deleteTime);
			} else if (itemId == 9056 || itemId == 141915 || itemId == 141916) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (86400000 * 7));// 22�ð�
				item.setEndTime(deleteTime);
			}  else if (itemId == 1419161) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 3));// 3�ð�
				item.setEndTime(deleteTime);
			}
			if (itemId == 60080 || itemId == 60082) {
				if (item.getCreaterName() == null)
					item.setCreaterName("*���ø���*");
			}
			/** ���� �ý��� by�̷����� **/
			// �߸� �ý��� �� �̷�����ΰ͸�...
			if (itemId == L1ItemId.HAPPY_BIRTHDAY_ELF) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(
						System.currentTimeMillis() + 86400000);
				item.setEndTime(deleteTime);
			}
			if (itemId == L1ItemId.HAPPY_BIRTHDAY_METIS) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis() + 3600000);
				item.setEndTime(deleteTime);
			}
			pc.sendPackets(new S_ItemStatus(item, pc), true);
			pc.sendPackets(new S_ServerMessage(403, item.getNumberedName(count)));
		}
	}
	private void getPet(L1PcInstance pc, int size) {
		int itemObjectId;
		if (size > 10 || size < 0)
			return;
		for (int i = 0; i < size; i++) {
			itemObjectId = readD();
			readD();

			if (!petGetitemCheck(pc, itemObjectId)) {
				break;
			} else if (!hasAdena(pc, 115))
				break;

			int petCost = 0;
			for (Object pet : pc.getPetList()) {
				petCost += ((L1NpcInstance) pet).getPetcost();
			}
			int charisma = pc.getAbility().getTotalCha();
			if (pc.isCrown())
				charisma += 6;
			else if (pc.isElf())
				charisma += 12;
			else if (pc.isWizard())
				charisma += 6;
			else if (pc.isDarkelf())
				charisma += 6;
			else if (pc.isDragonknight())
				charisma += 6;
			else if (pc.isIllusionist())
				charisma += 6;

			// 21 -
			int petCount = (charisma - petCost) / 6;
			if (petCount <= 0) {
				S_ServerMessage sm = new S_ServerMessage(489);
				pc.sendPackets(sm, true);
				break;
			}

			L1Pet l1pet = PetTable.getInstance().getTemplate(itemObjectId);
			if (l1pet != null) {
				L1Npc npcTemp = NpcTable.getInstance().getTemplate(
						l1pet.get_npcid());
				L1PetInstance pet = new L1PetInstance(npcTemp, pc, l1pet);
				if (l1pet.get_npcid() == 45313 || l1pet.get_npcid() == 45710
						|| l1pet.get_npcid() == 45711
						|| l1pet.get_npcid() == 45712)
					pet.setPetcost(12);
				else
					pet.setPetcost(6);
				pet.getSkillEffectTimerSet().setSkillEffect(
						L1SkillId.STATUS_PET_FOOD, pet.getFoodTime() * 1000);
			}
		}
	}

	private boolean petGetitemCheck(L1PcInstance pc, int itemObjectId) {
		// TODO �ڵ� ������ �޼ҵ� ����
		L1ItemInstance item = pc.getInventory().getItem(itemObjectId);
		if (item == null
				|| (item.getItem().getItemId() != 40314 && item.getItem()
						.getItemId() != 40316))
			return false;
		if (isWithdraw(pc, itemObjectId))
			return false;
		return true;
	}

	private boolean isWithdraw(L1PcInstance pc, int itemid) {
		L1PetInstance pet = null;
		for (Object petObject : pc.getPetList()) {
			if (petObject instanceof L1PetInstance) {
				pet = (L1PetInstance) petObject;
				if (itemid == pet.getItemObjId()) {
					return true;
				}
			}
		}
		return false;
	}

	private void doNothingClanWarehouse(L1PcInstance pc) {
		if (pc == null)
			return;

		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
		if (clan == null)
			return;

		ClanWarehouse clanWarehouse = WarehouseManager.getInstance()
				.getClanWarehouse(clan.getClanName());
		if (clanWarehouse == null)
			return;

		clanWarehouse.unlock(pc.getId());
	}

	private boolean okok(int id) {
		/*
		 * if(id >=40044 && id<=40055){ return true; } if(id ==41246){ return
		 * true; } if(id ==40308){ return true; }
		 */
		return false;
	}

	private void getItemToPackageWarehouse(L1PcInstance pc, int size) {
		int objectId, count;
		L1ItemInstance item = null;
		PackageWarehouse w = WarehouseManager.getInstance()
				.getPackageWarehouse(pc.getAccountName());
		if (w == null)
			return;

		for (int i = 0; i < size; i++) {
			objectId = readD();
			count = readD();
			item = w.getItem(objectId, count);
			/*
			 * if(item ==null){ if(Config.����ä�ø����()>0){ for(L1PcInstance gm :
			 * Config.toArray����ä�ø����()){ if(gm.getNetConnection()==null){
			 * Config.remove����(gm); continue; } gm.sendPackets(new
			 * S_SystemMessage("���� ���� �ǽ� : "+pc.getName())); } }
			 * pc.sendPackets(new S_SystemMessage("�߸��� ���� �ٽ� �õ����ּ���.")); break;
			 * }
			 */
			if (okok(item.getItemId())) {
				pc.sendPackets(
						new S_SystemMessage("�ش� �������� ��а� â���̿��� �Ұ����մϴ�."), true);
				break;
			}
			if (!isAvailableTrade(pc, objectId, item, count))
				break;
			if (count > item.getCount())
				count = item.getCount();
			if (!isAvailablePcWeight(pc, item, count))
				break;

			ArrayList<String> name = new ArrayList<String>();
			name = L1PcInstance.getPCs(pc.getAccountName());
			for (L1PcInstance _pc : L1World.getInstance().getAllPlayers()) {
				if (_pc.getName() == pc.getName()) {
					continue;
				}
				if (name.contains(_pc.getName())) {
					if (_pc.getNetConnection() != null) {
						_pc.getNetConnection().close();
					}
				}
			}

			w.tradeItem(item, count, pc.getInventory());
			eva.LogWareHouseAppend("��Ű��:", pc.getName(), "", item, count, objectId);
		}
	}

	private void getItemToElfWarehouse(L1PcInstance pc, int size) {
		if (pc.getLevel() < 5 || !pc.isElf())
			return;

		L1ItemInstance item;
		ElfWarehouse elfwarehouse = WarehouseManager.getInstance()
				.getElfWarehouse(pc.getAccountName());
		if (elfwarehouse == null)
			return;

		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();
			item = elfwarehouse.getItem(objectId, count);
			if (okok(item.getItemId())) {
				pc.sendPackets(
						new S_SystemMessage("�ش� �������� ��а� â���̿��� �Ұ����մϴ�."), true);
				break;
			}
			/*
			 * if(item ==null){ if(Config.����ä�ø����()>0){ for(L1PcInstance gm :
			 * Config.toArray����ä�ø����()){ if(gm.getNetConnection()==null){
			 * Config.remove����(gm); continue; } gm.sendPackets(new
			 * S_SystemMessage("���� ���� �ǽ� : "+pc.getName())); } }
			 * pc.sendPackets(new S_SystemMessage("�߸��� ���� �ٽ� �õ����ּ���.")); break;
			 * }
			 */
			if (!isAvailableTrade(pc, objectId, item, count))
				break;
			if (count > item.getCount())
				count = item.getCount();
			if (!isAvailablePcWeight(pc, item, count))
				break;
			ArrayList<String> name = new ArrayList<String>();
			name = L1PcInstance.getPCs(pc.getAccountName());
			for (L1PcInstance _pc : L1World.getInstance().getAllPlayers()) {
				if (_pc.getName() == pc.getName()) {
					continue;
				}
				if (name.contains(_pc.getName())) {
					if (_pc.getNetConnection() != null) {
						_pc.getNetConnection().close();
					}
				}
			}
			if (pc.getInventory().consumeItem(40494, 2)) {
				elfwarehouse.tradeItem(item, count, pc.getInventory());
				eva.LogWareHouseAppend("����:ã", pc.getName(), "", item, count, objectId);
			} else {
				pc.sendPackets(new S_ServerMessage(337, "$767"), true);
				break;
			}
		}
	}

	private void putItemToElfWarehouse(L1PcInstance pc, int size) {
		if (pc.getLevel() < 5 || !pc.isElf())
			return;

		L1Object object = null;
		L1ItemInstance item = null;
		ElfWarehouse elfwarehouse = WarehouseManager.getInstance()
				.getElfWarehouse(pc.getAccountName());
		if (elfwarehouse == null)
			return;

		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();

			object = pc.getInventory().getItem(objectId);
			item = (L1ItemInstance) object;

			// ��� â�� ��� ����
			// if (pc.getAccessLevel() == Config.GMCODE) break;
			// if (item.getId() == 41246) break; // ����ü â�� ��� ����
			if (!isAvailableTrade(pc, objectId, item, count))
				break;
			if (count > item.getCount())
				count = item.getCount();

			if (!item.getItem().isTradable()) {
				pc.sendPackets(new S_ServerMessage(210, item.getItem()
						.getName()), true); // \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
				break;
			}

			if (!checkPetList(pc, item))
				break;
			if (!isAvailableWhCount(elfwarehouse, pc, item, count))
				break;

			
			if (item.getItem().getItemId() == 430116) {
				pc.sendPackets(new S_SystemMessage("�ش� �������� â�� �̿��� �� �� �����ϴ�."),
						true);
				break;
			}
			if (item.getItem().getItemId() == 49312
					|| item.getItem().getItemId() == 40312 || item.getItem().getItemId() == 437011  ) {
				pc.sendPackets(new S_SystemMessage("�ش� �������� â�� �̿��� �� �� �����ϴ�."),
						true);
				break;
			}

			ArrayList<String> name = new ArrayList<String>();
			name = L1PcInstance.getPCs(pc.getAccountName());
			for (L1PcInstance _pc : L1World.getInstance().getAllPlayers()) {
				if (_pc.getName() == pc.getName()) {
					continue;
				}
				if (name.contains(_pc.getName())) {
					if (_pc.getNetConnection() != null) {
						_pc.getNetConnection().close();
					}
				}
			}

			pc.getInventory().tradeItem(objectId, count, elfwarehouse);
			eva.LogWareHouseAppend("����:��", pc.getName(), "", item, count, objectId);
		}
	}

	private void getItemToClanWarehouse(L1PcInstance pc, int size) {
		if (pc.getLevel() < 5)
			return;

		if (pc.getClanRank() == L1Clan.CLAN_RANK_PROBATION)
			return;

		if (size == 0) {
			doNothingClanWarehouse(pc);
			return;
		}

		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());

		if (!isAvailableClan(pc, clan))
			return;

		L1ItemInstance item;
		ClanWarehouse clanWarehouse = WarehouseManager.getInstance()
				.getClanWarehouse(clan.getClanName());
		if (clanWarehouse == null)
			return;

		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();
			item = clanWarehouse.getItem(objectId, count);
			if (okok(item.getItemId())) {
				pc.sendPackets(
						new S_SystemMessage("�ش� �������� ��а� â���̿��� �Ұ����մϴ�."), true);
				break;
			}
			/*
			 * if(item ==null){ if(Config.����ä�ø����()>0){ for(L1PcInstance gm :
			 * Config.toArray����ä�ø����()){ if(gm.getNetConnection()==null){
			 * Config.remove����(gm); continue; } gm.sendPackets(new
			 * S_SystemMessage("���� ���� �ǽ� : "+pc.getName())); } }
			 * pc.sendPackets(new S_SystemMessage("�߸��� ���� �ٽ� �õ����ּ���.")); break;
			 * }
			 */
			if (!isAvailableTrade(pc, objectId, item, count))
				break;
			if (!hasAdena(pc))
				break;
			if (count >= item.getCount())
				count = item.getCount();
			if (!isAvailablePcWeight(pc, item, count))
				break;
			ArrayList<String> name = new ArrayList<String>();
			name = L1PcInstance.getPCs(pc.getAccountName());
			for (L1PcInstance _pc : L1World.getInstance().getAllPlayers()) {
				if (_pc.getName() == pc.getName()) {
					continue;
				}
				if (name.contains(_pc.getName())) {
					if (_pc.getNetConnection() != null) {
						_pc.getNetConnection().close();
					}
				}
			}

			L1ItemInstance itemExist = pc.getInventory().findItemId(
					item.getItemId());
			if (itemExist != null
					&& ((itemExist.getCount() + count) > 2000000000 || (itemExist
							.getCount() + count) < 0)) {
				pc.sendPackets(new S_SystemMessage(
						"�ش� �������� ���� ������ 20���� �ʰ��ϰ� �˴ϴ�.")); // \f1%0��%4%1%3%2
				return;
			}

			clanWarehouse.tradeItem(item, count, pc.getInventory());
			pc.sendPackets(new S_SystemMessage(ItemLogName(item, count, 1)),
					true);// ã������Ʈ
			ClanHistoryTable.getInstance().add(pc.getClan(), 1, pc.getName(),
					item.getName(), count);
			LogTable.logcwarehouse(pc, item, count, 1);
		}
		clanWarehouse.unlock(pc.getId());

	}

	private void putItemToClanWarehouse(L1PcInstance pc, int size) {
		if (size == 0) {
			doNothingClanWarehouse(pc);
			return;
		}

		L1Clan clan = L1World.getInstance().getClan(pc.getClanname());

		if (!isAvailableClan(pc, clan))
			return;

		L1Object object = null;
		L1ItemInstance item = null;
		ClanWarehouse clanWarehouse = WarehouseManager.getInstance()
				.getClanWarehouse(clan.getClanName());
		if (clanWarehouse == null)
			return;

		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();

			object = pc.getInventory().getItem(objectId);
			item = (L1ItemInstance) object;

			L1DollInstance ���� = null;
			for (Object ����������Ʈ : pc.getDollList()) {
				if (����������Ʈ instanceof L1DollInstance) {
					���� = (L1DollInstance) ����������Ʈ;
					if (item.getId() == ����.getItemObjId()) {
						// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
						pc.sendPackets(new S_SystemMessage(
								"��ȯ���� ������ â�� �̿��� �� �� �����ϴ�."), true);
						return;
					}
				}
			}
			if (item == null)
				break;
			if (count > item.getCount())
				count = item.getCount();
			// if(pc.getAccessLevel() == Config.GMCODE) break; // ��� â�� ��� ����
			// if (item.getId() == 41246) break; // ����ü â�� ��� ����
			if (!isAvailableTrade(pc, objectId, item, count))
				break;

			if (item.getItem().getItemId() == 411591
					|| item.getItemId() == 40308) { // ����,�Ƶ� â�� ��� ����
				pc.sendPackets(new S_ServerMessage(210, item.getItem()
						.getName()), true); // \f1%0�� �����ų� �Ǵ� Ÿ�ο��� ������ �� �� �����ϴ�.
				return;
			}
			if (item.getItem().getItemId() == 49312
					|| item.getItem().getItemId() == 40312) {
				pc.sendPackets(new S_SystemMessage("�ش� �������� â�� �̿��� �� �� �����ϴ�."),
						true);
				break;
			}
			if (item.getBless() >= 128 || !item.getItem().isTradable()) {
				// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� �絵 �� �� �����ϴ�.
				pc.sendPackets(new S_ServerMessage(210, item.getItem()
						.getName()), true);
				break;
			}
			if (item.getItem().getItemId() == 430116
					|| item.getItemId() == 21255 || item.getItemId() == 437011) { // 21255 ����ȣ�ڰ���
				pc.sendPackets(new S_SystemMessage(
						"�ش� �������� ���� â�� �̿��� �� �� �����ϴ�."), true);
				break;
			}
			if (!checkPetList(pc, item))
				break;
			if (!isAvailableWhCount(clanWarehouse, pc, item, count))
				break;
			ArrayList<String> name = new ArrayList<String>();
			name = L1PcInstance.getPCs(pc.getAccountName());
			for (L1PcInstance _pc : L1World.getInstance().getAllPlayers()) {
				if (_pc.getName() == pc.getName()) {
					continue;
				}
				if (name.contains(_pc.getName())) {
					if (_pc.getNetConnection() != null) {
						_pc.getNetConnection().close();
					}
				}
			}
			L1ItemInstance itemExist = clanWarehouse.findItemId(item
					.getItemId());
			if (itemExist != null
					&& ((itemExist.getCount() + count) > 2000000000 || (itemExist
							.getCount() + count) < 0)) {
				pc.sendPackets(new S_SystemMessage(
						"�ش� �������� ���� ������ 20���� �ʰ��ϰ� �˴ϴ�.")); // \f1%0��%4%1%3%2
				return;
			}
			pc.getInventory().tradeItem(objectId, count, clanWarehouse);

			ClanHistoryTable.getInstance().add(pc.getClan(), 0, pc.getName(),
					item.getName(), count);
			eva.LogWareHouseAppend("����:��", pc.getName(), pc.getClanname(), item, count, objectId);
			LogTable.logcwarehouse(pc, item, count, 0);
		}
		clanWarehouse.unlock(pc.getId());

	}

	private void getItemToPrivateWarehouse(L1PcInstance pc, int size) {
		L1ItemInstance item = null;
		PrivateWarehouse warehouse = WarehouseManager.getInstance()
				.getPrivateWarehouse(pc.getAccountName());
		if (warehouse == null)
			return;

		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();
			item = warehouse.getItem(objectId, count);
			/*
			 * if(item ==null){ if(Config.����ä�ø����()>0){ for(L1PcInstance gm :
			 * Config.toArray����ä�ø����()){ if(gm.getNetConnection()==null){
			 * Config.remove����(gm); continue; } gm.sendPackets(new
			 * S_SystemMessage("���� ���� �ǽ� : "+pc.getName())); } }
			 * pc.sendPackets(new S_SystemMessage("�߸��� ���� �ٽ� �õ����ּ���.")); break;
			 * }
			 */
			if (okok(item.getItemId())) {
				pc.sendPackets(
						new S_SystemMessage("�ش� �������� ��а� â���̿��� �Ұ����մϴ�."), true);
				break;
			}
			if (!isAvailableTrade(pc, objectId, item, count))
				break;
			if (count > item.getCount())
				count = item.getCount();
			if (!isAvailablePcWeight(pc, item, count))
				break;
			if (!hasAdena(pc))
				break;
			ArrayList<String> name = new ArrayList<String>();
			name = L1PcInstance.getPCs(pc.getAccountName());
			for (L1PcInstance _pc : L1World.getInstance().getAllPlayers()) {
				if (_pc.getName() == pc.getName()) {
					continue;
				}
				if (name.contains(_pc.getName())) {
					if (_pc.getNetConnection() != null) {
						_pc.getNetConnection().close();
					}
				}
			}

			L1ItemInstance itemExist = pc.getInventory().findItemId(
					item.getItemId());
			// int iii = itemExist.getCount() + count;
			// System.out.println(iii);
			if (itemExist != null
					&& ((itemExist.getCount() + count) > 2000000000 || (itemExist
							.getCount() + count) < 0)) {
				pc.sendPackets(new S_SystemMessage(
						"�ش� �������� ���� ������ 20���� �ʰ��ϰ� �˴ϴ�.")); // \f1%0��%4%1%3%2
				return;
			}

			warehouse.tradeItem(item, count, pc.getInventory());
			pc.sendPackets(new S_SystemMessage(ItemLogName(item, count, 1)),
					true);// ã������Ʈ
			LogTable.logwarehouse(pc, item, count, 1);
			eva.LogWareHouseAppend("����â��:ã", pc.getName(), "", item, count, objectId);
		}
	}

	private String ItemLogName(L1ItemInstance item, int count, int type) {
		int attr = item.getAttrEnchantLevel();
		int rlv = item.getRegistLevel();
		int elv = item.getEnchantLevel();
		boolean iden = item.isIdentified();

		StringBuilder SB = new StringBuilder();
		// 0:�ູ 1:��� 2:���� 3:�̰���
		if (iden) {
			if (item.getBless() == 0) {
				SB.append("[�ູ] ");
			} else if (item.getBless() == 1) {
				SB.append("[����] ");
			} else if (item.getBless() == 2) {
				SB.append("[����] ");
			}

			if (attr > 0) {
				switch (attr) {
				case 1:
					SB.append("ȭ��:1�� ");
					break;
				case 2:
					SB.append("ȭ��:2�� ");
					break;
				case 3:
					SB.append("ȭ��:3�� ");
					break;
				case 4:
					SB.append("����:1�� ");
					break;
				case 5:
					SB.append("����:2�� ");
					break;
				case 6:
					SB.append("����:3�� ");
					break;
				case 7:
					SB.append("ǳ��:1�� ");
					break;
				case 8:
					SB.append("ǳ��:2�� ");
					break;
				case 9:
					SB.append("ǳ��:3�� ");
					break;
				case 10:
					SB.append("����:1�� ");
					break;
				case 11:
					SB.append("����:2�� ");
					break;
				case 12:
					SB.append("����:3�� ");
					break;
				case 33:
					SB.append("ȭ��:4�� ");
					break;
				case 34:
					SB.append("ȭ��:5�� ");
					break;
				case 35:
					SB.append("����:4�� ");
					break;
				case 36:
					SB.append("����:5�� ");
					break;
				case 37:
					SB.append("ǳ��:4�� ");
					break;
				case 38:
					SB.append("ǳ��:5�� ");
					break;
				case 39:
					SB.append("����:4�� ");
					break;
				case 40:
					SB.append("����:5�� ");
					break;
				}
			}

			if (rlv > 0) {
				switch (rlv) {
				case 1:
					SB.append("[������ ��(I)] ");
					break;
				case 2:
					SB.append("[������ ��(II)] ");
					break;
				case 3:
					SB.append("[������ ��(III)] ");
					break;
				case 4:
					SB.append("[������ ��(IV)] ");
					break;
				case 5:
					SB.append("[������ ��(V)] ");
					break;
				}
			}

			if (elv > 0) {
				SB.append("+");
				SB.append(elv);
				SB.append(" ");
			}
		} else {
			SB.append("[��Ȯ��] ");
		}
		SB.append(item.getName());
		SB.append(" ");

		if (count > 1) {
			SB.append("(");
			SB.append(count);
			SB.append(")");
			SB.append("�� ");
		}
		if (type == 1) {
			SB.append("��(��) ã�ҽ��ϴ�.");
		} else {
			SB.append("��(��) �ð���ϴ�.");
		}

		return SB.toString();
	}

	private void putItemToPrivateWarehouse(L1PcInstance pc, int size) {
		L1Object object = null;
		L1ItemInstance item = null;
		PrivateWarehouse warehouse = WarehouseManager.getInstance()
				.getPrivateWarehouse(pc.getAccountName());
		if (warehouse == null)
			return;

		for (int i = 0, objectId, count; i < size; i++) {
			objectId = readD();
			count = readD();

			object = pc.getInventory().getItem(objectId);
			item = (L1ItemInstance) object;
			if (pc.getLevel() < Config.ALT_PRIVATE_WAREHOUSE_LEVEL) {
				pc.sendPackets(new S_SystemMessage(
						Config.ALT_PRIVATE_WAREHOUSE_LEVEL
								+ "�����ϴ� â�� �̿��� �� �� �����ϴ�."), true);
				break;
			}

			if (item == null)
				break;
			/** ���,�忡��,������,����,�����, �������⺸ȣ�ֹ���, �ǵ������Ƽ â�� �������ְ� **/
			if (!((item.getItemId() >= 437010 && item.getItemId() <= 437013)
					|| item.getItemId() == 5000067
					|| item.getItemId() == 60104
					|| (item.getItemId() >= 21125 && item.getItemId() <= 21136)
					|| (item.getItemId() >= 20452 && item.getItemId() <= 20455)
					|| (item.getItemId() >= 421000 && item.getItemId() <= 421020)
					|| item.getItemId() == 560025 || item.getItemId() == 560027
					|| item.getItemId() == 560028 || item.getItemId() == 41159
					|| (item.getItemId() >= 60286 && item.getItemId() <= 60289)
					|| item.getItemId() == 60354
					|| (item.getItemId() >= 40033 && item.getItemId() <= 40038)
					|| (item.getItemId() >= 60261 && item.getItemId() <= 60263)
					|| item.getItemId() == 60396 || item.getItemId() == 60398
					|| (item.getItemId() >= 60423 && item.getItemId() <= 60426)
					|| (item.getItemId() >= 60427 && item.getItemId() <= 60444)
					|| (item.getItemId() >= 60447 && item.getItemId() <= 60472)
					|| item.getItemId() == 21256 || item.getItemId() == 21257)
					|| item.getItemId() == 60492) {
				if (!item.getItem().isTradable()) {
					// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� �絵 �� �� �����ϴ�.
					pc.sendPackets(new S_ServerMessage(210, item.getItem()
							.getName()), true);
					break;
				}
			}
			if (okok(item.getItemId())) {
				pc.sendPackets(
						new S_SystemMessage("�ش� �������� ��а� â���̿��� �Ұ����մϴ�."), true);
				break;
			}
			// if(pc.getAccessLevel() == Config.GMCODE) return; // ��� â�� ��� ����
			// if (item.getId() == 41246) break; // ����ü â�� ��� ����
			if (item.getItem().getItemId() == 430116
					|| item.getItem().getItemId() == 49312 || item.getItem().getItemId() == 437011
					|| item.getItem().getItemId() == 40312) {
				pc.sendPackets(new S_SystemMessage("�ش� �������� â�� �̿��� �� �� �����ϴ�."),
						true);
				break;
			}
			if (!isAvailableTrade(pc, objectId, item, count))
				break;
			if (!checkPetList(pc, item))
				break;
			if (!isAvailableWhCount(warehouse, pc, item, count))
				break;
			if (count > item.getCount())
				count = item.getCount();

			ArrayList<String> name = new ArrayList<String>();
			name = L1PcInstance.getPCs(pc.getAccountName());
			for (L1PcInstance _pc : L1World.getInstance().getAllPlayers()) {
				if (_pc.getName() == pc.getName()) {
					continue;
				}
				if (name.contains(_pc.getName())) {
					if (_pc.getNetConnection() != null) {
						_pc.getNetConnection().close();
					}
				}
			}

			L1ItemInstance itemExist = warehouse.findItemId(item.getItemId());
			if (itemExist != null
					&& ((itemExist.getCount() + count) > 2000000000 || (itemExist
							.getCount() + count) < 0)) {
				pc.sendPackets(new S_SystemMessage(
						"�ش� �������� ���� ������ 20���� �ʰ��ϰ� �˴ϴ�.")); // \f1%0��%4%1%3%2
				return;
			}

			pc.getInventory().tradeItem(objectId, count, warehouse);
			LogTable.logwarehouse(pc, item, count, 0);
			eva.LogWareHouseAppend("����â��:��", pc.getName(), "", item, count, objectId);
		}
	}

	private void sellItemToPrivateShop(L1PcInstance pc, L1Object findObject,
			int size) {
		if (findObject == null)
			return;

		if (findObject instanceof L1PcInstance) {
			L1PcInstance targetPc = (L1PcInstance) findObject;

			if (targetPc.isTradingInPrivateShop())
				return;

			targetPc.setTradingInPrivateShop(true);

			L1PrivateShopBuyList psbl;
			L1ItemInstance item = null;
			L1ItemInstance sellitem = null;
			boolean[] isRemoveFromList = new boolean[8];
			ArrayList<L1PrivateShopBuyList> buyList = targetPc.getBuyList();

			synchronized (buyList) {
				int order, itemObjectId, count, buyPrice, buyTotalCount, buyCount;
				for (int i = 0; i < size; i++) {
					itemObjectId = readD();
					count = readCH();
					order = readC();

					item = pc.getInventory().getItem(itemObjectId);

					if (!isAvailableTrade(pc, itemObjectId, item, count))
						break;
					if (item.getBless() >= 128) {
						// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� �絵 �� �� �����ϴ�.
						pc.sendPackets(new S_ServerMessage(210, item.getItem()
								.getName()), true);
						break;
					}

					psbl = (L1PrivateShopBuyList) buyList.get(order);
					buyPrice = psbl.getBuyPrice();
					buyTotalCount = psbl.getBuyTotalCount(); // �� ������ ����
					buyCount = psbl.getBuyCount(); // �� ����

					sellitem = targetPc.getInventory().getItem(
							psbl.getItemObjectId());

					if (sellitem == null || item == null)
						break;

					if (item.getItemId() != sellitem.getItemId())
						break;
					if (item.getEnchantLevel() != sellitem.getEnchantLevel())
						break;
					if (item.getAttrEnchantLevel() != sellitem
							.getAttrEnchantLevel())
						break;
					if (item.getBless() != sellitem.getBless())
						break;

					if (buyTotalCount - buyCount == 0)
						break;

					// if( buyTotalCount == buyCount)break;

					if (count > buyTotalCount - buyCount)
						count = buyTotalCount - buyCount;

					if (item.isEquipped()) {
						pc.sendPackets(new S_ServerMessage(905)); // ��� �ϰ� �ִ�
																	// �������� �Ǹ���
																	// �� �����ϴ�.
						break;
					}

					if (!isAvailablePcWeight(pc, item, count))
						break;
					if (isOverMaxAdena(targetPc, buyPrice, count))
						return;

					if (count >= item.getCount())
						count = item.getCount();

					if (!targetPc.getInventory().checkItem(L1ItemId.ADENA,
							count * buyPrice)) {
						targetPc.sendPackets(new S_ServerMessage(189)); // \f1�Ƶ�����
																		// �����մϴ�.
						break;
					}

					L1ItemInstance adena = targetPc.getInventory().findItemId(
							L1ItemId.ADENA);
					if (adena == null)
						break;

					String message = item.getItem().getName()
							+ (count > 1 ? " (" + String.valueOf(count) + ")"
									: "");
					pc.sendPackets(new S_ServerMessage(877, targetPc.getName(),
							message));
					targetPc.getInventory().tradeItem(adena, count * buyPrice,
							pc.getInventory());
					pc.getInventory().tradeItem(item, count,
							targetPc.getInventory());
					LogTable.logshop(pc, targetPc, item, buyPrice, count, 1);
					psbl.setBuyCount(count + buyCount);
					buyList.set(order, psbl);

					if (psbl.getBuyCount() == psbl.getBuyTotalCount()) { // ��
																			// ������
																			// ������
																			// ���
						isRemoveFromList[order] = true;
						try {

							pc.���������۸��Ի���(targetPc.getId(), item.getItemId(), 0);
						} catch (Exception e) {
							e.printStackTrace();
							// _log.log(Level.SEVERE, e.getLocalizedMessage(),
							// e);
						}
					} else {
						try {
							pc.���������۸��Ծ�����Ʈ(
									targetPc.getId(),
									item.getItemId(),
									0,
									psbl.getBuyTotalCount()
											- psbl.getBuyCount());
						} catch (Exception e) {
							e.printStackTrace();
							// _log.log(Level.SEVERE, e.getLocalizedMessage(),
							// e);
						}
					}
					try {
						pc.saveInventory();
						targetPc.saveInventory();
					} catch (Exception e) {
						e.printStackTrace();
						// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
				}
				// ������ �������� ����Ʈ�� ���̷κ��� ����
				for (int i = 7; i >= 0; i--) {
					if (isRemoveFromList[i]) {
						buyList.remove(i);
					}
				}
				targetPc.setTradingInPrivateShop(false);
			}
		}
	}

	private void sellItemToShop(L1PcInstance pc, int npcId, int size) {
		L1Shop shop = ShopTable.getInstance().get(npcId);
		L1ShopSellOrderList orderList = shop.newSellOrderList(pc);
		int itemNumber;
		long itemcount;

		for (int i = 0; i < size; i++) {
			itemNumber = readD();
			itemcount = readD();
			if (itemcount <= 0) {
				return;
			}
			orderList.add(itemNumber, (int) itemcount, pc);
		}
		int bugok = orderList.BugOk();
		if (bugok == 0) {
			if (npcId == 100027) {// �ÿ�
				shop.buyItemsFoodHub(orderList);
			} else if (npcId == 100028) {// �̿�
				shop.buyItemsFoodSauce(orderList);
			} else if (npcId == 100564) {// ȯ��
				shop.buyItemsȯ��(orderList);
			} else if (npcId == 100605) {// ����
				shop.buyItems����(orderList);
			} else  if (npcId >= 7001411 && npcId <=7001413) {// Ưȭ���� 
				shop.buyItemsƯȭ����(orderList);
			} else  if (npcId >= 7001414 && npcId <=7001416) {
				shop.buyItems��������(orderList);
			} else  if (npcId >= 7001417 && npcId <=7001419) {
				shop.buyItems�������ι�(orderList);
			}else
				shop.buyItems(orderList);
		}
	}

	private void sellItemToNpcShop(L1PcInstance pc, int npcId, int size) {
		L1Shop shop = null;
		if ((shop = NpcBuyShop.getInstance().get(npcId)) == null)
			shop = NpcShopTable.getInstance().get(npcId);
		if (shop == null)
			return;
		L1ShopSellOrderList orderList = shop.newSellOrderList(pc);

		int itemNumber;
		long itemcount;

		for (int i = 0; i < size; i++) {
			itemNumber = readD();
			itemcount = readD();
			if (itemcount <= 0) {
				return;
			}
			orderList.add(itemNumber, (int) itemcount, pc);
		}
		int bugok = orderList.BugOk();
		if (bugok == 0) {
			shop.buyItems(orderList);
		}
	}

	/** ���� ���� ���Ǿ� ������ ���� */
	private void buyItemFromNpcShop(L1PcInstance pc, int npcId, int size) {
		L1Shop shop = null;
		if ((shop = NpcBuyShop.getInstance().get(npcId)) == null)
			shop = NpcShopTable.getInstance().get(npcId);
		if (shop == null)
			return;
		L1ShopBuyOrderList orderList = shop.newBuyOrderList();
		int itemNumber;
		long itemcount;

		for (int i = 0; i < size; i++) {
			itemNumber = readD();
			itemcount = readD();
			if (itemcount <= 0 || itemcount >= 10000) {
				return;
			}
			/*
			 * if(size >= 2){ //���ÿ� �ٸ������� ������� 2���� ���õȴٸ�, pc.sendPackets(new
			 * S_SystemMessage("\\fY�ѹ��� ���� �ٸ��������� �����Ҽ������ϴ�.")); return; }
			 * if(pc.getMapId() == 360){//���������� ���۸� �����ϰ��߱⶧����, �������忡�� 15���� ���� �̻�
			 * �Ȼ����� if(itemcount > 15) { pc.sendPackets(new
			 * S_SystemMessage("\\fY�ִ뱸�ż��� : ���۷�(15����) / ����(1����)")); return; }
			 * }
			 */

			orderList.add(itemNumber, (int) itemcount, pc);
		}
		int bugok = orderList.BugOk();
		if (bugok == 0) {
			shop.sellItems(pc, orderList);
			// �鼷���� ���� ���������׹���
			pc.saveInventory();
			// �鼷���� ���� ���������׹���
		}
	}

	private void buyItemFromPrivateShop(L1PcInstance pc, L1Object findObject,
			int size) {
		if (findObject == null)
			return;
		if (findObject instanceof L1PcInstance) {
			L1PcInstance targetPc = (L1PcInstance) findObject;

			ArrayList<L1PrivateShopSellList> sellList = targetPc.getSellList();

			synchronized (sellList) {
				// ǰ���� �߻���, �������� �����ۼ��� ����Ʈ���� �ٸ���

				if (pc.getPartnersPrivateShopItemCount() != sellList.size())
					return;
				if (pc.getPartnersPrivateShopItemCount() < sellList.size())
					return;

				if (targetPc.isTradingInPrivateShop()) {
					pc.sendPackets(new S_SystemMessage(
							"�̹� �ٸ������ �ŷ��� �Դϴ�. �ٽ� �̿����ּ���."), true);
					return;
				}

				targetPc.setTradingInPrivateShop(true);

				L1ItemInstance item;
				L1PrivateShopSellList pssl;
				boolean[] isRemoveFromList = new boolean[8];
				int order, count, price, sellCount, sellPrice, itemObjectId, sellTotalCount;
				for (int i = 0; i < size; i++) { // ���� ������ ��ǰ
					order = readD();
					count = readD();

					pssl = (L1PrivateShopSellList) sellList.get(order);
					itemObjectId = pssl.getItemObjectId();
					sellPrice = pssl.getSellPrice();
					sellTotalCount = pssl.getSellTotalCount(); // �� ������ ����
					sellCount = pssl.getSellCount(); // �� ����
					item = targetPc.getInventory().getItem(itemObjectId);

					if (item == null)
						break;
					if (item.isEquipped()) {
						pc.sendPackets(new S_ServerMessage(905, "")); // ��� �ϰ�
																		// �ִ�
																		// ������
																		// ����
																		// ���ϰ�.
						break;
					}

					if (count > sellTotalCount - sellCount)
						count = sellTotalCount - sellCount;

					if (count == 0)
						break;

					if (!isAvailablePcWeight(pc, item, count))
						break;
					if (isOverMaxAdena(pc, sellPrice, count))
						break;

					price = count * sellPrice;
					if (price <= 0 || price > 2000000000)
						break;

					if (!isAvailableTrade(pc, targetPc, itemObjectId, item,
							count))
						break;

					if (count >= item.getCount())
						count = item.getCount();
					if (count > 9999)
						break;

					if (!pc.getInventory().checkItem(L1ItemId.ADENA, price)) {

						pc.sendPackets(new S_SystemMessage("�Ƶ����� ���ġ �ʽ��ϴ�."),
								true); // \f1�Ƶ����� �����մϴ�.
						break;
					}

					L1ItemInstance adena = pc.getInventory().findItemId(
							L1ItemId.ADENA);

					if (targetPc == null || adena == null)
						break;
					if (targetPc.getInventory().tradeItem(item, count,
							pc.getInventory()) == null)
						break;

					pc.getInventory().tradeItem(adena, price,
							targetPc.getInventory());
					// %1%o %0�� �Ǹ��߽��ϴ�.
					String message = item.getItem().getName() + " ("
							+ String.valueOf(count) + ")";
					targetPc.sendPackets(new S_ServerMessage(877, pc.getName(),
							message));

					pssl.setSellCount(count + sellCount);
					sellList.set(order, pssl);

					// writeLogbuyPrivateShop(pc, targetPc, item, count, price);

					if (pssl.getSellCount() == pssl.getSellTotalCount()) { // �ش�
																			// ����
																			// ��
																			// �ȾҴ�
						isRemoveFromList[order] = true;
						try {
							pc.���������ۻ���(targetPc.getId(), item.getId(), 1);
						} catch (Exception e) {
							e.printStackTrace();
							// _log.log(Level.SEVERE, e.getLocalizedMessage(),
							// e);
						}
					} else {
						try {
							pc.���������۾�����Ʈ(
									targetPc.getId(),
									item.getId(),
									1,
									pssl.getSellTotalCount()
											- pssl.getSellCount());
						} catch (Exception e) {
							e.printStackTrace();
							// _log.log(Level.SEVERE, e.getLocalizedMessage(),
							// e);
						}
					}
					try {
						pc.saveInventory();
						targetPc.saveInventory();

					} catch (Exception e) {
						e.printStackTrace();
					}

					LogTable.logshop(pc, targetPc, item, sellPrice, count, 0);

					if (GMCommands.���λ�������üũ) {
						for (L1PcInstance temppc : L1World.getInstance()
								.getAllPlayers()) {
							if (temppc == null
									|| temppc.getNetConnection() == null)
								continue;
							if (temppc.isGm()) {
								temppc.sendPackets(new S_SystemMessage(
										"\\fW���� > ����:" + pc.getName() + " �Ǹ�:"
												+ targetPc.getName() + " ��:"
												+ item.getNumberedName(0, true)
												+ " ���簡��:"
												+ (sellPrice * count) + " ����:"
												+ count), true);

							}
						}
					}
				}

				// ǰ���� �������� ����Ʈ�� ���̷κ��� ����
				for (int i = 7; i >= 0; i--) {
					if (isRemoveFromList[i]) {
						sellList.remove(i);
					}
				}
				targetPc.setTradingInPrivateShop(false);
			}
		}
	}

	private void buyItemFromShop(L1PcInstance pc, int npcId, int size) {
		if (size > 1000) {
			return;
		}
		if (npcId == 100800) {
			npcId = pc.getType();
		}
		/*
		 * 0000: 49 33 e4 69 1b 00 01 00 00 00 00 00 01 00 00 00
		 * I3.i............ 0010: 1b 69 f2 35 00 00 00 00 .i.5....
		 */

		/*
		 * 49 33 e4 69 1b 00 ����ƮŸ�� 01 ������ 00 ���� 00 00 00 00 01 00 00 00
		 * I3.i............ 1b 69 f2 35 00 00 00 00 .i.5....
		 */
		/*
		 * 49 12 2f 6b 1b 00 01 00 95 3a ff 00
		 * 
		 * 01 00 00 00 I./k.....:...... fa b3 f3 35 00 00 00 00 ...5....
		 */
		L1Shop shop = ShopTable.getInstance().get(npcId);
		L1ShopBuyOrderList orderList = shop.newBuyOrderList();
		int itemNumber;
		long itemcount;
		int un;
		for (int i = 0; i < size; i++) {

			if (npcId == 100725) {
				itemNumber = readH();
				un = readH();
			} else {
				itemNumber = readD();
			}

			if (itemNumber == 14997) {
				itemNumber = 0;
			} else if (itemNumber == 14998) {
				itemNumber = 1;
			} else if (itemNumber == 14825) {
				itemNumber = 2;
			} else if (itemNumber == 15212) {
				itemNumber = 3;
			} else if (itemNumber == 15002) {
				itemNumber = 4;
			} 

			itemcount = readD();
			if (itemcount <= 0 || itemcount >= 10000) {
				return;
			}

			if (npcId == 70017 && itemcount > 1)
				itemcount = 1;
			if (npcId == 80080 && itemcount > 1)
				itemcount = 1;
			orderList.add(itemNumber, (int) itemcount, pc);
		}
		int bugok = orderList.BugOk();
		if (bugok == 0) {
			shop.sellItems(pc, orderList);
		}
	}

	/**
	 * ����� �ִ� ��� ĳ���� ������ ���� ���� ������ �ִٸ� true ���ٸ� false
	 * 
	 * @param c
	 *            L1PcInstance
	 * @return �ִٸ� true
	 */
	private boolean isTwoLogin(L1PcInstance c) {
		boolean bool = false;
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
				if (c.getNetConnection()
						.getAccountName()
						.equalsIgnoreCase(
								target.getNetConnection().getAccountName())) {
					bool = true;
					break;
				}
			}
		}
		return bool;
	}

	private boolean isOverMaxAdena(L1PcInstance pc, int sellPrice, int count) {
		if (sellPrice * count > 2000000000 || sellPrice * count < 0) {
			pc.sendPackets(new S_ServerMessage(904, "2000000000"), true);
			return true;
		}
		if (count < 0) {
			return true;
		}
		if (sellPrice < 0) {
			return true;
		}
		return false;
	}

	private boolean checkPetList(L1PcInstance pc, L1ItemInstance item) {
		L1DollInstance doll = null;
		for (Object dollObject : pc.getDollList()) {
			doll = (L1DollInstance) dollObject;
			if (item.getId() == doll.getItemObjId()) {
				pc.sendPackets(new S_ServerMessage(1181), true); //
				return false;
			}
		}
		for (Object petObject : pc.getPetList()) {
			if (petObject instanceof L1PetInstance) {
				L1PetInstance pet = (L1PetInstance) petObject;
				if (item.getId() == pet.getItemObjId()) {
					// \f1%0�� �����ų� �Ǵ� Ÿ�ο��� �絵 �� �� �����ϴ�.
					pc.sendPackets(new S_ServerMessage(210, item.getItem()
							.getName()), true);
					return false;
				}
			}
		}
		return true;
	}

	private boolean isAvailableWhCount(Warehouse warehouse, L1PcInstance pc,
			L1ItemInstance item, int count) {
		if (warehouse.checkAddItemToWarehouse(item, count) == L1Inventory.SIZE_OVER) {
			// \f1��밡 ������ �ʹ� ������ �־� �ŷ��� �� �����ϴ�.
			pc.sendPackets(new S_ServerMessage(75), true);
			return false;
		}
		return true;
	}

	private boolean isAvailableClan(L1PcInstance pc, L1Clan clan) {
		if (pc.getClanid() == 0 || clan == null) {
			// \f1���� â�� ����Ϸ��� ���Ϳ� �������� ������ �ȵ˴ϴ�.
			pc.sendPackets(new S_ServerMessage(208), true);
			return false;
		}
		return true;
	}

	private boolean isAvailablePcWeight(L1PcInstance pc, L1ItemInstance item,
			int count) {
		if (pc.getInventory().checkAddItem(item, count) != L1Inventory.OK) {
			// \f1 ������ �ִ� ���� ���ſ��� �ŷ��� �� �����ϴ�.
			pc.sendPackets(new S_ServerMessage(270), true);
			return false;
		}
		return true;
	}

	private boolean hasAdena(L1PcInstance pc) {
		if (!pc.getInventory().consumeItem(L1ItemId.ADENA, 30)) {
			// \f1�Ƶ����� �����մϴ�.
			pc.sendPackets(new S_ServerMessage(189), true);
			return false;
		}
		return true;
	}

	private boolean hasAdena(L1PcInstance pc, int count) {
		if (!pc.getInventory().consumeItem(L1ItemId.ADENA, count)) {
			// \f1�Ƶ����� �����մϴ�.
			pc.sendPackets(new S_ServerMessage(189), true);
			return false;
		}
		return true;
	}

	private boolean isAvailableTrade(L1PcInstance pc, int objectId,
			L1ItemInstance item, int count) {
		boolean result = true;

		if (item == null)
			result = false;
		if (objectId != item.getId())
			result = false;
		if (!item.isStackable() && count != 1)
			result = false;
		if (item.getCount() <= 0 || item.getCount() > 2000000000)
			result = false;
		if (count <= 0 || count > 2000000000)
			result = false;

		if (!result) {
			pc.sendPackets(new S_Disconnect(), true);
		}

		return result;
	}

	private boolean isAvailableTrade(L1PcInstance pc, L1PcInstance targetPc,
			int itemObjectId, L1ItemInstance item, int count) {
		boolean result = true;

		if (itemObjectId != item.getId() || !item.isStackable() && count != 1)
			result = false;
		if (count <= 0 || item.getCount() <= 0 || item.getCount() < count)
			result = false;

		if (!result) {
			pc.sendPackets(new S_Disconnect(), true);
			targetPc.sendPackets(new S_Disconnect(), true);
		}

		return result;
	}

	@Override
	public String getType() {
		return "[C] C_Result";
	}
}