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

import javolution.util.FastMap;
import l1j.server.Config;
import l1j.server.server.ActionCodes;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_DoActionShop;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1PrivateShopBuyList;
import l1j.server.server.templates.L1PrivateShopSellList;
import server.LineageClient;
import server.message.ServerMessage;
//import java.util.logging.Logger;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_Shop extends ClientBasePacket {
	/*
	 * C -> S 0000: 02 00 01 00 db 8c 4d 24 0d b6 56 07 01 00 00 00
	 * ......M$..V..... 0010: 00 00 31 32 33 31 32 33 ff 31 32 33 31 32 33 32
	 * ..123123.1231232 0020: 31 33 00 74 72 61 64 65 7a 6f 6e 65 31 00 00 00
	 * 13.tradezone1...
	 */
	private static final String C_SHOP = "[C] C_Shop";

	public C_Shop(byte abyte0[], LineageClient clientthread) {
		super(abyte0);
		try {
			L1PcInstance pc = clientthread.getActiveChar();
			if (pc == null) {
				return;
			}
			// if (pc.getAccessLevel() == Config.GMCODE){return;}
			if (pc.isGhost() || pc.isDead()) {
				return;
			}
			if (pc.isInvisble()) {
				pc.sendPackets(new S_ServerMessage(755), true);
				return;
			}
			int mapId = pc.getMapId();
			if (mapId != 340 && mapId != 350 && mapId != 360 && mapId != 370
					&& mapId != 800) {
				pc.sendPackets(new S_ServerMessage(876), true);
				return;
			}
			/*
			 * if (pc.getGfxId().getTempCharGfx() != pc.getClassId() &&
			 * pc.getSkillEffectTimerSet
			 * ().getSkillEffectTimeSec(L1SkillId.SHAPE_CHANGE) <=0){
			 * pc.sendPackets(new S_SystemMessage("변신 아이템을 해제 해 주시기 바랍니다."),
			 * true); return; }
			 */
			/*
			 * if (pc.getLevel() < 1 || pc.getLevel() > 99) { pc.sendPackets(new
			 * S_SystemMessage("무인상점은 52레벨이하 만 가능합니다."), true); return; }
			 */

			if (Config.ALT_PRIVATE_SHOP_LEVEL != 0
					&& pc.getLevel() < Config.ALT_PRIVATE_SHOP_LEVEL) {
				pc.sendPackets(new S_SystemMessage("무인상점은 "
						+ Config.ALT_PRIVATE_SHOP_LEVEL + " 레벨이상 만 가능합니다."),
						true);
				return;
			}

			if (pc.getInventory().checkEquipped(427200)
					|| pc.getInventory().checkEquipped(427201)
					|| pc.getInventory().checkEquipped(427202)
					|| pc.getInventory().checkEquipped(427203)
					|| pc.getInventory().checkEquipped(427204)
					|| pc.getInventory().checkEquipped(427205)
					|| pc.getInventory().checkEquipped(427206)
					|| pc.getInventory().checkEquipped(427207)
					|| pc.getInventory().checkEquipped(427113)
					|| pc.getInventory().checkEquipped(427114)
					|| pc.getInventory().checkEquipped(427115)
					|| pc.getInventory().checkEquipped(427116)
					|| pc.getInventory().checkEquipped(427117)
					|| pc.getInventory().checkEquipped(427118)
					|| pc.getInventory().checkEquipped(427119)
					|| pc.getInventory().checkEquipped(427120)
					|| pc.getInventory().checkEquipped(427121)
					|| pc.getInventory().checkEquipped(427122)) {
				pc.sendPackets(new S_SystemMessage(
						"무인상점 개설은 룬을 해제해야 사용이 가능합니다."), true);
				return;
			}

			/*
			 * try { // 엔챤트를 DB의 character_buff에 보존한다
			 * CharBuffTable.DeleteBuff(pc); CharBuffTable.SaveBuff(pc);
			 * pc.getSkillEffectTimerSet().clearSkillEffectTimer(); pc.save(); }
			 * catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			ArrayList<L1PrivateShopSellList> sellList = pc.getSellList();
			ArrayList<L1PrivateShopBuyList> buyList = pc.getBuyList();
			L1ItemInstance checkItem;
			boolean tradable = true;

			int type = readC();
			if (type == 0) { // 개시
				int sellTotalCount = readH();
				int sellObjectId;
				int sellPrice;
				int sellCount;
				L1ItemInstance sellitem = null;
				for (int i = 0; i < sellTotalCount; i++) {
					sellObjectId = readD();
					sellPrice = readD();
					sellCount = readD();

					/** 개인상점 오류 수정 */
					if (sellTotalCount > 7) {
						pc.sendPackets(
								new S_SystemMessage("물품등록은 7개까지만 가능합니다."), true);
						return;
					}

					// 거래 가능한 아이템이나 체크
					checkItem = pc.getInventory().getItem(sellObjectId);

					if (checkItem == null) {
						continue;
					}

					if (sellObjectId != checkItem.getId()) {
						tradable = false;
						pc.sendPackets(new S_SystemMessage(
								"비정상 아이템 입니다. 다시 시도해주세요."), true);
						/*
						 * pc.sendPackets(new S_Disconnect(), true); return;
						 */
					}
					if (!checkItem.isStackable() && sellCount != 1) {
						tradable = false;
						pc.sendPackets(new S_SystemMessage(
								"비정상 아이템 입니다. 다시 시도해주세요."), true);
						/*
						 * pc.sendPackets(new S_Disconnect(), true); return;
						 */
					}
					if (sellCount > checkItem.getCount()) {
						sellCount = checkItem.getCount();
					}
					if (checkItem.getCount() < sellCount
							|| checkItem.getCount() <= 0 || sellCount <= 0) {
						tradable = false;
						pc.sendPackets(new S_SystemMessage(
								"비정상 아이템 입니다. 다시 시도해주세요."), true);
						/*
						 * sellList.clear(); buyList.clear(); return;
						 */
					}
					/*
					 * if (checkItem.getItem().getItemId() == 423012 ||
					 * checkItem.getItem().getItemId() == 423013){ // 10주년티
					 * pc.sendPackets(new S_ServerMessage(210,
					 * checkItem.getItem().getName())); // \f1%0은 버리거나 또는 타인에게
					 * 양일을 할 수 없습니다. return; }
					 */
					if (checkItem.getBless() >= 128) {
						tradable = false;
						pc.sendPackets(new S_ServerMessage(
								ServerMessage.CANNOT_DROP_OR_TRADE, checkItem
										.getItem().getName()));
						// return;
					}
					L1DollInstance 인형 = null;
					for (Object 인형오브젝트 : pc.getDollList()) {
						if (인형오브젝트 instanceof L1DollInstance) {
							인형 = (L1DollInstance) 인형오브젝트;
							if (checkItem.getId() == 인형.getItemObjId()) {
								// \f1%0은 버리거나 또는 타인에게 양일을 할 수 없습니다.
								tradable = false;
								pc.sendPackets(new S_SystemMessage(
										"소환중인 인형은 상점에 올릴 수 없습니다."), true);
								// return;
							}
						}
					}
					if (!checkItem.getItem().isTradable()) {
						tradable = false;
						pc.sendPackets(new S_ServerMessage(166, checkItem
								.getItem().getName(), "거래 불가능합니다. "), true);
					}

					if ((checkItem.getItem().getType2() == 1 || checkItem
							.getItem().getType2() == 2)
							&& pc.getMapId() != 340
							&& pc.getMapId() != 800) {
						tradable = false;
						pc.sendPackets(new S_SystemMessage(
								"장비류는 글루딘 시장에서만 가능합니다."));
					}

					if (checkItem.getItem().getType2() == 0
							&& pc.getMapId() != 350 && pc.getMapId() != 800) {
						tradable = false;
						pc.sendPackets(new S_SystemMessage(
								"잡템류는 기란 시장에서만 가능합니다."));
					}

					for (Object petObject : pc.getPetList()) {
						if (petObject instanceof L1PetInstance) {
							L1PetInstance pet = (L1PetInstance) petObject;
							if (checkItem.getId() == pet.getItemObjId()) {
								tradable = false;
								pc.sendPackets(new S_ServerMessage(166,
										checkItem.getItem().getName(),
										"거래 불가능합니다. "), true);
								break;
							}
						}
					}
					L1PrivateShopSellList pssl = new L1PrivateShopSellList();
					pssl.setItemObjectId(sellObjectId);
					pssl.setSellPrice(sellPrice);
					pssl.setSellTotalCount(sellCount);
					sellList.add(pssl);
					// try{
					// sellitem = pc.getInventory().getItem(sellObjectId);
					/*
					 * Config.SaveShop(pc, sellitem, sellPrice, 1); } catch
					 * (Exception e) {e.printStackTrace(); }
					 */
				}
				int buyTotalCount = readH();
				if (buyTotalCount > 0) {
					try {
						pc.상점아이템삭제(pc.getId());
					} catch (Exception e) {
						e.printStackTrace();
						// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
					}
					pc.sendPackets(new S_SystemMessage(
							"일시적으로 구입 물품을 등록할 수 없습니다."), true);
					sellList.clear();
					buyList.clear();
					pc.setPrivateShop(false);
					pc.sendPackets(new S_DoActionGFX(pc.getId(),
							ActionCodes.ACTION_Idle), true);
					Broadcaster.broadcastPacket(pc,
							new S_DoActionGFX(pc.getId(),
									ActionCodes.ACTION_Idle), true);
					return;
				}
				int buyObjectId;
				int buyPrice;
				int buyCount;
				L1ItemInstance buyitem = null;
				for (int i = 0; i < buyTotalCount; i++) {
					buyObjectId = readD();
					buyPrice = readD();
					buyCount = readD();
					if (buyTotalCount > 7) {
						pc.sendPackets(
								new S_SystemMessage("물품등록은 7개까지만 가능합니다."), true);
						return;
					}

					checkItem = pc.getInventory().getItem(buyObjectId);

					if (buyObjectId != checkItem.getId()) {
						tradable = false;
						pc.sendPackets(new S_SystemMessage(
								"비정상 아이템 입니다. 다시 시도해주세요."), true);
						/*
						 * pc.sendPackets(new S_Disconnect(), true); return;
						 */
					}
					if (!checkItem.isStackable() && buyCount != 1) {
						tradable = false;
						pc.sendPackets(new S_SystemMessage(
								"비수량성 아이템은 1개씩만 등록할 수 있습니다."), true);
						// pc.sendPackets(new S_Disconnect(), true);
						// return;
					}
					if (buyCount <= 0 || checkItem.getCount() <= 0) {
						tradable = false;
						pc.sendPackets(new S_SystemMessage(
								"비정상 아이템 입니다. 다시 시도해주세요."), true);
						/*
						 * pc.sendPackets(new S_Disconnect(), true); return;
						 */
					}
					/*
					 * if (buyCount > checkItem.getCount()) { buyCount =
					 * checkItem.getCount(); }
					 */

					if (!checkItem.getItem().isTradable()) {
						tradable = false;
						pc.sendPackets(new S_ServerMessage(166, checkItem
								.getItem().getName(), "거래 불가능합니다. "), true);
					}

					/*
					 * if ((checkItem.getItem().getType2() == 1 ||
					 * checkItem.getItem().getType2() == 2) && pc.getMapId() !=
					 * 340 && pc.getMapId() != 800) { tradable = false;
					 * pc.sendPackets(new
					 * S_SystemMessage("장비류는 글루딘 시장에서만 가능합니다.")); }
					 */
					/*
					 * if (checkItem.getItem().getType2() == 0 && pc.getMapId()
					 * != 350 && pc.getMapId() != 800) { tradable = false;
					 * pc.sendPackets(new
					 * S_SystemMessage("잡템류는 기란 시장에서만 가능합니다.")); }
					 */

					for (Object petObject : pc.getPetList()) {
						if (petObject instanceof L1PetInstance) {
							L1PetInstance pet = (L1PetInstance) petObject;
							if (checkItem.getId() == pet.getItemObjId()) {
								tradable = false;
								pc.sendPackets(new S_ServerMessage(166,
										checkItem.getItem().getName(),
										"거래 불가능합니다. "), true);
								break;
							}
						}
					}
					L1PrivateShopBuyList psbl = new L1PrivateShopBuyList();
					psbl.setItemObjectId(buyObjectId);
					psbl.setBuyPrice(buyPrice);
					psbl.setBuyTotalCount(buyCount);
					buyList.add(psbl);
					// try{
					// buyitem = pc.getInventory().getItem(buyObjectId);
					/*
					 * Config.SaveShop(pc, buyitem, buyPrice, 0); } catch
					 * (Exception e) { e.printStackTrace();
					 * //_log.log(Level.SEVERE, e.getLocalizedMessage(), e); }
					 */
				}

				if (sellTotalCount == 0 && buyTotalCount == 0) {
					pc.sendPackets(new S_ServerMessage(908), true);
					pc.setPrivateShop(false);
					pc.sendPackets(new S_DoActionGFX(pc.getId(),
							ActionCodes.ACTION_Idle), true);
					Broadcaster.broadcastPacket(pc,
							new S_DoActionGFX(pc.getId(),
									ActionCodes.ACTION_Idle), true);
					return;
				}

				if (!tradable) { // 거래 불가능한 아이템이 포함되어 있는 경우, 개인 상점 종료
					sellList.clear();
					buyList.clear();
					pc.setPrivateShop(false);
					pc.sendPackets(new S_DoActionGFX(pc.getId(),
							ActionCodes.ACTION_Idle), true);
					Broadcaster.broadcastPacket(pc,
							new S_DoActionGFX(pc.getId(),
									ActionCodes.ACTION_Idle), true);
					return;
				}

				/*
				 * if(!get상점개설계정횟수(pc.getAccountName())){ sellList.clear();
				 * buyList.clear(); pc.setPrivateShop(false); pc.sendPackets(new
				 * S_DoActionGFX(pc.getId(), ActionCodes.ACTION_Idle), true);
				 * Broadcaster.broadcastPacket(pc, new
				 * S_DoActionGFX(pc.getId(),ActionCodes.ACTION_Idle), true);
				 * pc.sendPackets(new S_ServerMessage(3695), true); return; }
				 */

				int shopOpenCount = pc.getNetConnection().getAccount().Shop_open_count;

				if (shopOpenCount >= 40) {
					int OpenAdena = 20000 + ((shopOpenCount - 40) * 1000);
					if (!pc.getInventory().consumeItem(40308, OpenAdena)) {
						sellList.clear();
						buyList.clear();
						pc.setPrivateShop(false);
						pc.sendPackets(new S_DoActionGFX(pc.getId(),
								ActionCodes.ACTION_Idle), true);
						Broadcaster.broadcastPacket(pc,
								new S_DoActionGFX(pc.getId(),
										ActionCodes.ACTION_Idle), true);
						pc.sendPackets(new S_ServerMessage(189), true);
						return;
					}
				}

				pc.getNetConnection().getAccount().updateShopOpenCount();
				pc.sendPackets(new S_PacketBox(S_PacketBox.상점개설횟수, pc
						.getNetConnection().getAccount().Shop_open_count), true);

				byte[] chat = readSByte();
				pc.setShopChat(chat);
				pc.setPrivateShop(true);

				pc.sendPackets(new S_DoActionShop(pc.getId(),
						ActionCodes.ACTION_Shop, chat), true);
				Broadcaster.broadcastPacket(pc, new S_DoActionShop(pc.getId(),
						ActionCodes.ACTION_Shop, chat), true);

				try {
					for (L1PrivateShopSellList pss : pc.getSellList()) {
						int sellp = pss.getSellPrice();
						int sellc = pss.getSellTotalCount();
						sellitem = pc.getInventory().getItem(
								pss.getItemObjectId());
						if (sellitem == null)
							continue;
						pc.SaveShop(pc, sellitem, sellp, sellc, 1);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					for (L1PrivateShopBuyList psb : pc.getBuyList()) {
						int buyp = psb.getBuyPrice();
						int buyc = psb.getBuyTotalCount();
						buyitem = pc.getInventory().getItem(
								psb.getItemObjectId());
						if (buyitem == null)
							continue;
						pc.SaveShop(pc, buyitem, buyp, buyc, 0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					String polyName = readS();
					int polyId = 0;
					if (polyName.equalsIgnoreCase("tradezone1"))
						polyId = 11326;
					else if (polyName.equalsIgnoreCase("tradezone2"))
						polyId = 11427;
					else if (polyName.equalsIgnoreCase("tradezone3"))
						polyId = 10047;
					else if (polyName.equalsIgnoreCase("tradezone4"))
						polyId = 9688;
					else if (polyName.equalsIgnoreCase("tradezone5"))
						polyId = 11322;
					else if (polyName.equalsIgnoreCase("tradezone6"))
						polyId = 10069;
					else if (polyName.equalsIgnoreCase("tradezone7"))
						polyId = 10034;
					else if (polyName.equalsIgnoreCase("tradezone8"))
						polyId = 10032;

					if (polyId != 0) {
						pc.getSkillEffectTimerSet().killSkillEffectTimer(
								L1SkillId.SHAPE_CHANGE);
						L1PolyMorph.undoPoly(pc);
						L1ItemInstance weapon = pc.getWeapon();
						if (weapon != null)
							pc.getInventory().setEquipped(weapon, false, false,
									false);
						pc.getGfxId().setTempCharGfx(polyId);
						pc.sendPackets(new S_ChangeShape(pc.getId(), polyId, pc
								.getCurrentWeapon()));
						if (!pc.isGmInvis() && !pc.isInvisble()) {
							Broadcaster.broadcastPacket(pc, new S_ChangeShape(
									pc.getId(), polyId));
						}
						S_CharVisualUpdate charVisual = new S_CharVisualUpdate(
								pc, 0x46);
						pc.sendPackets(charVisual);
						Broadcaster.broadcastPacket(pc, charVisual);
					}

				} catch (Exception e) {
					pc.상점아이템삭제(pc.getId());
					sellList.clear();
					buyList.clear();
					pc.setPrivateShop(false);
					pc.sendPackets(new S_DoActionGFX(pc.getId(),
							ActionCodes.ACTION_Idle), true);
					Broadcaster.broadcastPacket(pc,
							new S_DoActionGFX(pc.getId(),
									ActionCodes.ACTION_Idle), true);
					return;
				}
				// pc.sendPackets(new S_ServerMessage(902), true);
			} else if (type == 1) { // 종료
				if (isTwoLogin(pc)) {
					pc.sendPackets(new S_Disconnect());
				}
				sellList.clear();
				buyList.clear();
				pc.setPrivateShop(false);

				int classId = pc.getClassId();
				pc.getGfxId().setTempCharGfx(classId);
				pc.sendPackets(new S_ChangeShape(pc.getId(), classId));
				Broadcaster.broadcastPacket(pc, new S_ChangeShape(pc.getId(),
						classId));
				L1ItemInstance weapon = pc.getWeapon();
				if (weapon != null)
					pc.getInventory().setEquipped(weapon, false, false, false);
				S_CharVisualUpdate charVisual = new S_CharVisualUpdate(pc);
				pc.sendPackets(charVisual);
				Broadcaster.broadcastPacket(pc, charVisual);
				// pc.sendPackets(new S_ServerMessage(903), true);
				// pc.sendPackets(new S_DoActionGFX(pc.getId(),
				// ActionCodes.ACTION_Idle));
				// Broadcaster.broadcastPacket(pc, new S_DoActionGFX(pc.getId(),
				// ActionCodes.ACTION_Idle));
				try {
					pc.상점아이템삭제(pc.getId());
				} catch (Exception e) {
					e.printStackTrace();
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
		L1PcInstance[] list = L1World.getInstance().getAllPlayersToArray();
		for (L1PcInstance target : list) {
			// 무인PC 는 제외
			if (target == null)
				continue;
			if (target.noPlayerCK)
				continue;
			if (target.샌드백)
				continue;
			//
			if (c.getId() != target.getId() && !target.isPrivateShop()) {
				if (c.getNetConnection() != null
						&& target.getNetConnection() != null) {
					if (c.getNetConnection()
							.getAccountName()
							.equalsIgnoreCase(
									target.getNetConnection().getAccountName())) {
						bool = true;
						break;
					}
				}
			}
		}
		list = null;
		return bool;
	}

	private static FastMap<String, Integer> 상점개설_계정횟수 = new FastMap<String, Integer>();

	public static boolean get상점개설계정횟수(String account) {
		synchronized (상점개설_계정횟수) {
			int time = 0;
			try {
				time = 상점개설_계정횟수.get(account);
			} catch (Exception e) {
			}
			if (time >= 50)
				return false;
			상점개설_계정횟수.put(account, time++);
			return true;
		}
	}

	public static void reset상점개설계정횟수() {
		synchronized (상점개설_계정횟수) {
			상점개설_계정횟수.clear();
		}
	}

	@Override
	public String getType() {
		return C_SHOP;
	}
}
