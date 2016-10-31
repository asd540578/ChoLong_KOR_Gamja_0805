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
	private final int TYPE_BUY_SHP = 0; // 상점 or 개인 상점 사기
	private final int TYPE_SEL_SHP = 1; // 상점 or 개인 상점 팔기
	private final int TYPE_PUT_PWH = 2; // 개인 창고 맡기기
	private final int TYPE_GET_PWH = 3; // 개인 창고 찾기
	private final int TYPE_PUT_CWH = 4; // 혈맹 창고 맡기기
	private final int TYPE_GET_CWH = 5; // 혈맹 창고 찾기
	private final int TYPE_PUT_EWH = 8; // 엘프 창고 맡기기
	private final int TYPE_GET_EWH = 9; // 엘프 창고 찾기
	private final int TYPE_GET_MWH = 10; // 패키지 창고 찾기
	private final int TYPE_GET_PET = 12; // 펫 찾기
	private final int TYPE_GET_SSH = 20; // 부가서비스 창고

	public C_ShopAndWarehouse(byte abyte0[], LineageClient clientthread)
			throws Exception {
		super(abyte0);
		try {
			/*
			 * 49 33 e4 69 1b 00 리절트타입 01 사이즈 00 언노운 00 00 00 00 01 00 00 00
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
			if (findObject != null) { // 15셀
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
					pc.sendPackets(new S_ServerMessage(81)); // 무게 게이지가 가득찼습니다.
					return;
				}
			}

			if (npcObjectId == 7626) {
				npcId = 7626;
				npcImpl = "L1Merchant";
			}

			// System.out.println("123");
			switch (resultType) {
			case TYPE_BUY_SHP: // 상점 or 개인 상점 사기
				// System.out.println("npcid = "+npcId+"size :"+size);
				if (npcId == 70035 || npcId == 70041 || npcId == 70042) {
					int status = L1BugBearRace.getInstance().getBugRaceStatus();
					boolean chk = L1BugBearRace.getInstance().buyTickets;
					if (status != L1BugBearRace.STATUS_READY || chk == false) {
						return;
					}
				}
				
				if (pc.getClan() == null && pc.getLevel() >= Config.무혈상점) {
					pc.sendPackets(new S_SystemMessage(Config.무혈상점 + "레벨 이상은 혈맹이 없으면 상점을 이용할 수 없습니다."));
					if (pc.isGm()) {
					//	pc.sendPackets(new S_SystemMessage("하지만 운영자는 됨ㅋㅋ"));
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

			case TYPE_SEL_SHP: // 상점 or 개인 상점 팔기

				if (size != 0 && npcImpl.equalsIgnoreCase("L1Merchant"))
					sellItemToShop(pc, npcId, size);
				if (size != 0 && npcImpl.equalsIgnoreCase("L1NpcShop")) {
					sellItemToNpcShop(pc, npcId, size);
					break;
				}
				if (size != 0 && isPrivateShop)
					sellItemToPrivateShop(pc, findObject, size);
				break;
			case TYPE_PUT_PWH: // 개인 창고 맡기기
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					putItemToPrivateWarehouse(pc, size);
				break;
			case TYPE_GET_PWH: // 개인 창고 찾기
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					getItemToPrivateWarehouse(pc, size);
				break;
			case TYPE_PUT_CWH: // 혈맹 창고 맡기기
				if (npcImpl.equalsIgnoreCase("L1Dwarf"))
					putItemToClanWarehouse(pc, size);
				break;
			case TYPE_GET_CWH: // 혈맹 창고 찾기
				if (npcImpl.equalsIgnoreCase("L1Dwarf"))
					getItemToClanWarehouse(pc, size);
				break;
			case TYPE_PUT_EWH: // 엘프 창고 맡기기
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					putItemToElfWarehouse(pc, size);
				break;
			case TYPE_GET_EWH: // 엘프 창고 찾기
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					getItemToElfWarehouse(pc, size);
				break;
			case TYPE_GET_MWH: // 패키지 창고 찾기
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Dwarf"))
					getItemToPackageWarehouse(pc, size);
				break;
			case TYPE_GET_PET: // 펫 찾기
				if (size != 0 && npcImpl.equalsIgnoreCase("L1Merchant"))
					getPet(pc, size);
				break;
			case TYPE_GET_SSH: // 부가 서비스 창고
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
			if (itemId == L1ItemId.DRAGON_KEY) {// 드래곤 키
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(
						System.currentTimeMillis() + 259200000);// 3일
				item.setEndTime(deleteTime);
			}
			if (itemId == 40312 || itemId == 49312) {// 여관열쇠
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 4));// 4시간
				item.setEndTime(deleteTime);
			}
			if (itemId == 20344 || (itemId >= 21113 && itemId <= 21120)
					|| (itemId >= 60350 && itemId <= 60352)) { // 토끼투구, 정령악세(깃털)
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 3));// 3시간
				item.setEndTime(deleteTime);
			} else if (itemId == 600234
					|| itemId == 60009
					|| itemId == 60010
					|| itemId == 21092
					|| itemId == 60061
					|| // 메린, 킬톤 계약서, 요리사모자, 버프코인
					(itemId >= 425000 && itemId <= 425002)
					|| // 엘모어 방어구
					(itemId >= 450000 && itemId <= 450007) // 엘모어 무기
					|| itemId == 430003 || itemId == 430505 || itemId == 430506
					|| itemId == 41915 || itemId == 5000034 || itemId == 60233
					|| (itemId >= 21125 && itemId <= 21136)
					|| (itemId >= 21139 && itemId <= 21156)) { // 시댄서, 라미아,
																// 스파토이, 허수아비,
																// 에틴
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 24 * 7));// 7일
				item.setEndTime(deleteTime);
			} else if (itemId == 60011 || itemId == 60014) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 9));// 9시간
				item.setEndTime(deleteTime);
			} else if (itemId == 60012 || itemId == 60015) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 17));// 17시간
				item.setEndTime(deleteTime);
			} else if (itemId == 60013 || itemId == 60016) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 24));// 24시간
				item.setEndTime(deleteTime);
			} else if (itemId == 21094) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis() + 3600000);// 1시간
				item.setEndTime(deleteTime);
			} else if (itemId == 21157) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis() + 1800000);// 30분
				item.setEndTime(deleteTime);
			} else if ((itemId >= 267 && itemId <= 274)
					|| (itemId >= 21158 && itemId <= 21165)) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 24 * 3));// 3일
				item.setEndTime(deleteTime);
				// 상아템들
				/*
				 * }else if(itemId == 7 || itemId == 35 || itemId == 48 ||
				 * itemId == 73 || itemId == 105 || itemId == 120 || itemId ==
				 * 147 || itemId == 156 || itemId == 174 || itemId == 175 ||
				 * itemId == 224 || itemId == 20028 || itemId == 20082 || itemId
				 * == 20126 || itemId == 20173 || itemId == 20206 || itemId ==
				 * 20232 || itemId == 20282 || itemId == 201261 || itemId ==
				 * 21098 || (itemId >= 21102 && itemId <= 21112) || itemId ==
				 * 21254){ Timestamp deleteTime = null; deleteTime = new
				 * Timestamp(System.currentTimeMillis() + (3600000*24*7));// 3일
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
						+ (3600000 * 24 * 7));// 3일
				item.setEndTime(deleteTime);
			} else if (itemId == 60319) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 22));// 22시간
				item.setEndTime(deleteTime);
			} else if (itemId == 9056 || itemId == 141915 || itemId == 141916) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (86400000 * 7));// 22시간
				item.setEndTime(deleteTime);
			}  else if (itemId == 1419161) {
				Timestamp deleteTime = null;
				deleteTime = new Timestamp(System.currentTimeMillis()
						+ (3600000 * 3));// 3시간
				item.setEndTime(deleteTime);
			}
			if (itemId == 60080 || itemId == 60082) {
				if (item.getCreaterName() == null)
					item.setCreaterName("*케플리샤*");
			}
			/** 생일 시스템 by이러버엉 **/
			// 야메 시스템 ㅋ 이런방식인것만...
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
		// TODO 자동 생성된 메소드 스텁
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
			 * if(item ==null){ if(Config.버그채팅모니터()>0){ for(L1PcInstance gm :
			 * Config.toArray버그채팅모니터()){ if(gm.getNetConnection()==null){
			 * Config.remove버그(gm); continue; } gm.sendPackets(new
			 * S_SystemMessage("복사 버그 의심 : "+pc.getName())); } }
			 * pc.sendPackets(new S_SystemMessage("잘못된 접근 다시 시도해주세요.")); break;
			 * }
			 */
			if (okok(item.getItemId())) {
				pc.sendPackets(
						new S_SystemMessage("해당 아이템은 당분간 창고이용이 불가능합니다."), true);
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
			eva.LogWareHouseAppend("패키지:", pc.getName(), "", item, count, objectId);
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
						new S_SystemMessage("해당 아이템은 당분간 창고이용이 불가능합니다."), true);
				break;
			}
			/*
			 * if(item ==null){ if(Config.버그채팅모니터()>0){ for(L1PcInstance gm :
			 * Config.toArray버그채팅모니터()){ if(gm.getNetConnection()==null){
			 * Config.remove버그(gm); continue; } gm.sendPackets(new
			 * S_SystemMessage("복사 버그 의심 : "+pc.getName())); } }
			 * pc.sendPackets(new S_SystemMessage("잘못된 접근 다시 시도해주세요.")); break;
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
				eva.LogWareHouseAppend("요정:찾", pc.getName(), "", item, count, objectId);
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

			// 운영자 창고 사용 금지
			// if (pc.getAccessLevel() == Config.GMCODE) break;
			// if (item.getId() == 41246) break; // 결정체 창고 사용 금지
			if (!isAvailableTrade(pc, objectId, item, count))
				break;
			if (count > item.getCount())
				count = item.getCount();

			if (!item.getItem().isTradable()) {
				pc.sendPackets(new S_ServerMessage(210, item.getItem()
						.getName()), true); // \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
				break;
			}

			if (!checkPetList(pc, item))
				break;
			if (!isAvailableWhCount(elfwarehouse, pc, item, count))
				break;

			
			if (item.getItem().getItemId() == 430116) {
				pc.sendPackets(new S_SystemMessage("해당 아이템은 창고 이용을 할 수 없습니다."),
						true);
				break;
			}
			if (item.getItem().getItemId() == 49312
					|| item.getItem().getItemId() == 40312 || item.getItem().getItemId() == 437011  ) {
				pc.sendPackets(new S_SystemMessage("해당 아이템은 창고 이용을 할 수 없습니다."),
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
			eva.LogWareHouseAppend("요정:맡", pc.getName(), "", item, count, objectId);
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
						new S_SystemMessage("해당 아이템은 당분간 창고이용이 불가능합니다."), true);
				break;
			}
			/*
			 * if(item ==null){ if(Config.버그채팅모니터()>0){ for(L1PcInstance gm :
			 * Config.toArray버그채팅모니터()){ if(gm.getNetConnection()==null){
			 * Config.remove버그(gm); continue; } gm.sendPackets(new
			 * S_SystemMessage("복사 버그 의심 : "+pc.getName())); } }
			 * pc.sendPackets(new S_SystemMessage("잘못된 접근 다시 시도해주세요.")); break;
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
						"해당 아이템의 소지 갯수가 20억을 초과하게 됩니다.")); // \f1%0이%4%1%3%2
				return;
			}

			clanWarehouse.tradeItem(item, count, pc.getInventory());
			pc.sendPackets(new S_SystemMessage(ItemLogName(item, count, 1)),
					true);// 찾을때멘트
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

			L1DollInstance 인형 = null;
			for (Object 인형오브젝트 : pc.getDollList()) {
				if (인형오브젝트 instanceof L1DollInstance) {
					인형 = (L1DollInstance) 인형오브젝트;
					if (item.getId() == 인형.getItemObjId()) {
						// \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
						pc.sendPackets(new S_SystemMessage(
								"소환중인 인형은 창고 이용을 할 수 없습니다."), true);
						return;
					}
				}
			}
			if (item == null)
				break;
			if (count > item.getCount())
				count = item.getCount();
			// if(pc.getAccessLevel() == Config.GMCODE) break; // 운영자 창고 사용 금지
			// if (item.getId() == 41246) break; // 결정체 창고 사용 금지
			if (!isAvailableTrade(pc, objectId, item, count))
				break;

			if (item.getItem().getItemId() == 411591
					|| item.getItemId() == 40308) { // 깃털,아덴 창고 사용 금지
				pc.sendPackets(new S_ServerMessage(210, item.getItem()
						.getName()), true); // \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
				return;
			}
			if (item.getItem().getItemId() == 49312
					|| item.getItem().getItemId() == 40312) {
				pc.sendPackets(new S_SystemMessage("해당 아이템은 창고 이용을 할 수 없습니다."),
						true);
				break;
			}
			if (item.getBless() >= 128 || !item.getItem().isTradable()) {
				// \f1%0은 버리거나 또는 타인에게 양도 할 수 없습니다.
				pc.sendPackets(new S_ServerMessage(210, item.getItem()
						.getName()), true);
				break;
			}
			if (item.getItem().getItemId() == 430116
					|| item.getItemId() == 21255 || item.getItemId() == 437011) { // 21255 용의호박갑옷
				pc.sendPackets(new S_SystemMessage(
						"해당 아이템은 혈맹 창고 이용을 할 수 없습니다."), true);
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
						"해당 아이템의 소지 갯수가 20억을 초과하게 됩니다.")); // \f1%0이%4%1%3%2
				return;
			}
			pc.getInventory().tradeItem(objectId, count, clanWarehouse);

			ClanHistoryTable.getInstance().add(pc.getClan(), 0, pc.getName(),
					item.getName(), count);
			eva.LogWareHouseAppend("혈맹:맡", pc.getName(), pc.getClanname(), item, count, objectId);
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
			 * if(item ==null){ if(Config.버그채팅모니터()>0){ for(L1PcInstance gm :
			 * Config.toArray버그채팅모니터()){ if(gm.getNetConnection()==null){
			 * Config.remove버그(gm); continue; } gm.sendPackets(new
			 * S_SystemMessage("복사 버그 의심 : "+pc.getName())); } }
			 * pc.sendPackets(new S_SystemMessage("잘못된 접근 다시 시도해주세요.")); break;
			 * }
			 */
			if (okok(item.getItemId())) {
				pc.sendPackets(
						new S_SystemMessage("해당 아이템은 당분간 창고이용이 불가능합니다."), true);
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
						"해당 아이템의 소지 갯수가 20억을 초과하게 됩니다.")); // \f1%0이%4%1%3%2
				return;
			}

			warehouse.tradeItem(item, count, pc.getInventory());
			pc.sendPackets(new S_SystemMessage(ItemLogName(item, count, 1)),
					true);// 찾을때멘트
			LogTable.logwarehouse(pc, item, count, 1);
			eva.LogWareHouseAppend("개인창고:찾", pc.getName(), "", item, count, objectId);
		}
	}

	private String ItemLogName(L1ItemInstance item, int count, int type) {
		int attr = item.getAttrEnchantLevel();
		int rlv = item.getRegistLevel();
		int elv = item.getEnchantLevel();
		boolean iden = item.isIdentified();

		StringBuilder SB = new StringBuilder();
		// 0:축복 1:통상 2:저주 3:미감정
		if (iden) {
			if (item.getBless() == 0) {
				SB.append("[축복] ");
			} else if (item.getBless() == 1) {
				SB.append("[보통] ");
			} else if (item.getBless() == 2) {
				SB.append("[저주] ");
			}

			if (attr > 0) {
				switch (attr) {
				case 1:
					SB.append("화령:1단 ");
					break;
				case 2:
					SB.append("화령:2단 ");
					break;
				case 3:
					SB.append("화령:3단 ");
					break;
				case 4:
					SB.append("수령:1단 ");
					break;
				case 5:
					SB.append("수령:2단 ");
					break;
				case 6:
					SB.append("수령:3단 ");
					break;
				case 7:
					SB.append("풍령:1단 ");
					break;
				case 8:
					SB.append("풍령:2단 ");
					break;
				case 9:
					SB.append("풍령:3단 ");
					break;
				case 10:
					SB.append("지령:1단 ");
					break;
				case 11:
					SB.append("지령:2단 ");
					break;
				case 12:
					SB.append("지령:3단 ");
					break;
				case 33:
					SB.append("화령:4단 ");
					break;
				case 34:
					SB.append("화령:5단 ");
					break;
				case 35:
					SB.append("수령:4단 ");
					break;
				case 36:
					SB.append("수령:5단 ");
					break;
				case 37:
					SB.append("풍령:4단 ");
					break;
				case 38:
					SB.append("풍령:5단 ");
					break;
				case 39:
					SB.append("지령:4단 ");
					break;
				case 40:
					SB.append("지령:5단 ");
					break;
				}
			}

			if (rlv > 0) {
				switch (rlv) {
				case 1:
					SB.append("[정령의 인(I)] ");
					break;
				case 2:
					SB.append("[정령의 인(II)] ");
					break;
				case 3:
					SB.append("[정령의 인(III)] ");
					break;
				case 4:
					SB.append("[정령의 인(IV)] ");
					break;
				case 5:
					SB.append("[정령의 인(V)] ");
					break;
				}
			}

			if (elv > 0) {
				SB.append("+");
				SB.append(elv);
				SB.append(" ");
			}
		} else {
			SB.append("[미확인] ");
		}
		SB.append(item.getName());
		SB.append(" ");

		if (count > 1) {
			SB.append("(");
			SB.append(count);
			SB.append(")");
			SB.append("개 ");
		}
		if (type == 1) {
			SB.append("을(를) 찾았습니다.");
		} else {
			SB.append("을(를) 맡겼습니다.");
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
								+ "렙이하는 창고를 이용을 할 수 없습니다."), true);
				break;
			}

			if (item == null)
				break;
			/** 드다,드에메,드진주,드루비,드사파, 마족무기보호주문서, 판도라꽃향티 창고 넣을수있게 **/
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
					// \f1%0은 버리거나 또는 타인에게 양도 할 수 없습니다.
					pc.sendPackets(new S_ServerMessage(210, item.getItem()
							.getName()), true);
					break;
				}
			}
			if (okok(item.getItemId())) {
				pc.sendPackets(
						new S_SystemMessage("해당 아이템은 당분간 창고이용이 불가능합니다."), true);
				break;
			}
			// if(pc.getAccessLevel() == Config.GMCODE) return; // 운영자 창고 사용 금지
			// if (item.getId() == 41246) break; // 결정체 창고 사용 금지
			if (item.getItem().getItemId() == 430116
					|| item.getItem().getItemId() == 49312 || item.getItem().getItemId() == 437011
					|| item.getItem().getItemId() == 40312) {
				pc.sendPackets(new S_SystemMessage("해당 아이템은 창고 이용을 할 수 없습니다."),
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
						"해당 아이템의 소지 갯수가 20억을 초과하게 됩니다.")); // \f1%0이%4%1%3%2
				return;
			}

			pc.getInventory().tradeItem(objectId, count, warehouse);
			LogTable.logwarehouse(pc, item, count, 0);
			eva.LogWareHouseAppend("개인창고:맡", pc.getName(), "", item, count, objectId);
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
						// \f1%0은 버리거나 또는 타인에게 양도 할 수 없습니다.
						pc.sendPackets(new S_ServerMessage(210, item.getItem()
								.getName()), true);
						break;
					}

					psbl = (L1PrivateShopBuyList) buyList.get(order);
					buyPrice = psbl.getBuyPrice();
					buyTotalCount = psbl.getBuyTotalCount(); // 살 예정의 개수
					buyCount = psbl.getBuyCount(); // 산 누계

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
						pc.sendPackets(new S_ServerMessage(905)); // 장비 하고 있는
																	// 아이템은 판매할
																	// 수 없습니다.
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
						targetPc.sendPackets(new S_ServerMessage(189)); // \f1아데나가
																		// 부족합니다.
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

					if (psbl.getBuyCount() == psbl.getBuyTotalCount()) { // 살
																			// 예정의
																			// 개수를
																			// 샀다
						isRemoveFromList[order] = true;
						try {

							pc.상점아이템매입삭제(targetPc.getId(), item.getItemId(), 0);
						} catch (Exception e) {
							e.printStackTrace();
							// _log.log(Level.SEVERE, e.getLocalizedMessage(),
							// e);
						}
					} else {
						try {
							pc.상점아이템매입업데이트(
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
				// 매점한 아이템을 리스트의 말미로부터 삭제
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
			if (npcId == 100027) {// 시올
				shop.buyItemsFoodHub(orderList);
			} else if (npcId == 100028) {// 미엘
				shop.buyItemsFoodSauce(orderList);
			} else if (npcId == 100564) {// 환전
				shop.buyItems환전(orderList);
			} else if (npcId == 100605) {// 베리
				shop.buyItems베리(orderList);
			} else  if (npcId >= 7001411 && npcId <=7001413) {// 특화코인 
				shop.buyItems특화코인(orderList);
			} else  if (npcId >= 7001414 && npcId <=7001416) {
				shop.buyItems영웅코인(orderList);
			} else  if (npcId >= 7001417 && npcId <=7001419) {
				shop.buyItems영웅코인방어구(orderList);
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

	/** 영자 상점 엔피씨 샵에서 구입 */
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
			 * if(size >= 2){ //동시에 다른물건을 살수없게 2개가 선택된다면, pc.sendPackets(new
			 * S_SystemMessage("\\fY한번에 서로 다른아이템을 구입할수없습니다.")); return; }
			 * if(pc.getMapId() == 360){//오렌시장을 잡템만 가능하게했기때문에, 오렌시장에선 15개씩 수량 이상
			 * 안사지게 if(itemcount > 15) { pc.sendPackets(new
			 * S_SystemMessage("\\fY최대구매수량 : 잡템류(15개씩) / 장비류(1개씩)")); return; }
			 * }
			 */

			orderList.add(itemNumber, (int) itemcount, pc);
		}
		int bugok = orderList.BugOk();
		if (bugok == 0) {
			shop.sellItems(pc, orderList);
			// 백섭복사 방지 수량성버그방지
			pc.saveInventory();
			// 백섭복사 방지 수량성버그방지
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
				// 품절이 발생해, 열람중의 아이템수와 리스트수가 다르다

				if (pc.getPartnersPrivateShopItemCount() != sellList.size())
					return;
				if (pc.getPartnersPrivateShopItemCount() < sellList.size())
					return;

				if (targetPc.isTradingInPrivateShop()) {
					pc.sendPackets(new S_SystemMessage(
							"이미 다른사람과 거래중 입니다. 다시 이용해주세요."), true);
					return;
				}

				targetPc.setTradingInPrivateShop(true);

				L1ItemInstance item;
				L1PrivateShopSellList pssl;
				boolean[] isRemoveFromList = new boolean[8];
				int order, count, price, sellCount, sellPrice, itemObjectId, sellTotalCount;
				for (int i = 0; i < size; i++) { // 구입 예정의 상품
					order = readD();
					count = readD();

					pssl = (L1PrivateShopSellList) sellList.get(order);
					itemObjectId = pssl.getItemObjectId();
					sellPrice = pssl.getSellPrice();
					sellTotalCount = pssl.getSellTotalCount(); // 팔 예정의 개수
					sellCount = pssl.getSellCount(); // 판 누계
					item = targetPc.getInventory().getItem(itemObjectId);

					if (item == null)
						break;
					if (item.isEquipped()) {
						pc.sendPackets(new S_ServerMessage(905, "")); // 장비 하고
																		// 있는
																		// 아이템
																		// 구매
																		// 못하게.
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

						pc.sendPackets(new S_SystemMessage("아데나가 충분치 않습니다."),
								true); // \f1아데나가 부족합니다.
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
					// %1%o %0에 판매했습니다.
					String message = item.getItem().getName() + " ("
							+ String.valueOf(count) + ")";
					targetPc.sendPackets(new S_ServerMessage(877, pc.getName(),
							message));

					pssl.setSellCount(count + sellCount);
					sellList.set(order, pssl);

					// writeLogbuyPrivateShop(pc, targetPc, item, count, price);

					if (pssl.getSellCount() == pssl.getSellTotalCount()) { // 해당
																			// 템을
																			// 다
																			// 팔았다
						isRemoveFromList[order] = true;
						try {
							pc.상점아이템삭제(targetPc.getId(), item.getId(), 1);
						} catch (Exception e) {
							e.printStackTrace();
							// _log.log(Level.SEVERE, e.getLocalizedMessage(),
							// e);
						}
					} else {
						try {
							pc.상점아이템업데이트(
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

					if (GMCommands.무인상점구매체크) {
						for (L1PcInstance temppc : L1World.getInstance()
								.getAllPlayers()) {
							if (temppc == null
									|| temppc.getNetConnection() == null)
								continue;
							if (temppc.isGm()) {
								temppc.sendPackets(new S_SystemMessage(
										"\\fW개상 > 구매:" + pc.getName() + " 판매:"
												+ targetPc.getName() + " 템:"
												+ item.getNumberedName(0, true)
												+ " 개당가격:"
												+ (sellPrice * count) + " 갯수:"
												+ count), true);

							}
						}
					}
				}

				// 품절된 아이템을 리스트의 말미로부터 삭제
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
		 * 49 33 e4 69 1b 00 리절트타입 01 사이즈 00 언노운 00 00 00 00 01 00 00 00
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
	 * 월드상에 있는 모든 캐릭의 계정을 비교해 같은 계정이 있다면 true 없다면 false
	 * 
	 * @param c
	 *            L1PcInstance
	 * @return 있다면 true
	 */
	private boolean isTwoLogin(L1PcInstance c) {
		boolean bool = false;
		for (L1PcInstance target : L1World.getInstance().getAllPlayers()) {
			// 무인PC 는 제외
			if (target == null)
				continue;
			if (target.noPlayerCK)
				continue;
			if (target.샌드백)
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
					// \f1%0은 버리거나 또는 타인에게 양도 할 수 없습니다.
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
			// \f1상대가 물건을 너무 가지고 있어 거래할 수 없습니다.
			pc.sendPackets(new S_ServerMessage(75), true);
			return false;
		}
		return true;
	}

	private boolean isAvailableClan(L1PcInstance pc, L1Clan clan) {
		if (pc.getClanid() == 0 || clan == null) {
			// \f1혈맹 창고를 사용하려면 혈맹에 가입하지 않으면 안됩니다.
			pc.sendPackets(new S_ServerMessage(208), true);
			return false;
		}
		return true;
	}

	private boolean isAvailablePcWeight(L1PcInstance pc, L1ItemInstance item,
			int count) {
		if (pc.getInventory().checkAddItem(item, count) != L1Inventory.OK) {
			// \f1 가지고 있는 것이 무거워서 거래할 수 없습니다.
			pc.sendPackets(new S_ServerMessage(270), true);
			return false;
		}
		return true;
	}

	private boolean hasAdena(L1PcInstance pc) {
		if (!pc.getInventory().consumeItem(L1ItemId.ADENA, 30)) {
			// \f1아데나가 부족합니다.
			pc.sendPackets(new S_ServerMessage(189), true);
			return false;
		}
		return true;
	}

	private boolean hasAdena(L1PcInstance pc, int count) {
		if (!pc.getInventory().consumeItem(L1ItemId.ADENA, count)) {
			// \f1아데나가 부족합니다.
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