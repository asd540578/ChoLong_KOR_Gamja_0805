package l1j.server.server.serverpackets;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import l1j.server.GameSystem.NpcBuyShop.NpcBuyShop;
import l1j.server.server.Opcodes;
import l1j.server.server.datatables.NpcShopTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.shop.L1Shop;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1ShopItem;

public class S_PrivateShopforNpc extends ServerBasePacket {
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"MM-dd HH:mm", Locale.KOREA);

	public S_PrivateShopforNpc(L1PcInstance pc, int objId, int type) {
		L1NpcInstance npc = (L1NpcInstance) L1World.getInstance().findObject(
				objId);

		if (npc == null) {
			return;
		}

		int npcId = npc.getNpcTemplate().get_npcId();
		L1Shop shop = null;
		if ((shop = NpcBuyShop.getInstance().get(npcId)) == null)
			shop = NpcShopTable.getInstance().get(npcId);
		if (shop == null)
			return;

		/*
		 * if(type == 0){ List<L1ShopItem> shopItems = shop.getSellingItems();
		 * int size = shopItems.size(); if(size <= 0) return; }else
		 */if (type == 1) {
			List<L1ShopItem> list = shop.getPurchasingItems();
			int size = list.size();
			if (size <= 0)
				return;
		}

		writeC(Opcodes.S_PERSONAL_SHOP_LIST);
		writeC(type);

		writeD(objId);
		if (type == 0) {
			List<L1ShopItem> shopItems = shop.getSellingItems();

			int size = shopItems.size();
			pc.setPartnersPrivateShopItemCount(size);
			writeH(size);
			L1ItemInstance dummy = new L1ItemInstance();
			for (int i = 0; i < size; i++) {
				L1ShopItem shopItem = shopItems.get(i);
				L1Item item = shopItem.getItem();
				if (item == null) {
					System.out.println("엔피시 상점 오류 :" + npc.getNpcId() + " x :"
							+ npc.getX() + " y :" + npc.getY());
				}
				if (dummy != null) {
					dummy.setItem(item);
					writeC(i);
					writeD(shopItem.getCount());
					writeD(shopItem.getPrice());
					writeH(dummy.getItem().getGfxId());
					writeC(shopItem.getEnchant());
					boolean identify = false;
					if (shopItem.getEnchant() != 0
							|| dummy.getItem().getBless() != 1
							|| (shopItem.getItemId() == 40074
									|| shopItem.getItemId() == 140074 || shopItem
									.getItemId() == 240074)
							|| (shopItem.getItemId() == 40087
									|| shopItem.getItemId() == 140087 || shopItem
									.getItemId() == 240087)) {
						writeC(0x01);
						identify = true;
					} else
						writeC(0x00);
					writeC(dummy.getItem().getBless());
					String atrname = "";
					switch (shopItem.getAttr()) {
					case 1:
						atrname = "화령:1단 ";
						break;
					case 2:
						atrname = "화령:2단 ";
						break;
					case 3:
						atrname = "화령:3단 ";
						break;
					case 4:
						atrname = "수령:1단 ";
						break;
					case 5:
						atrname = "수령:2단 ";
						break;
					case 6:
						atrname = "수령:3단 ";
						break;
					case 7:
						atrname = "풍령:1단 ";
						break;
					case 8:
						atrname = "풍령:2단 ";
						break;
					case 9:
						atrname = "풍령:3단 ";
						break;
					case 10:
						atrname = "지령:1단 ";
						break;
					case 11:
						atrname = "지령:2단 ";
						break;
					case 12:
						atrname = "지령:3단 ";
						break;
					case 33:
						atrname = "화령:4단 ";
						break;
					case 34:
						atrname = "화령:5단 ";
						break;
					case 35:
						atrname = "수령:4단 ";
						break;
					case 36:
						atrname = "수령:5단 ";
						break;
					case 37:
						atrname = "풍령:4단 ";
						break;
					case 38:
						atrname = "풍령:5단 ";
						break;
					case 39:
						atrname = "지령:4단 ";
						break;
					case 40:
						atrname = "지령:5단 ";
						break;
					default:
						break;
					}
					if (shopItem.getEnchant() > 0) {
						writeS(atrname
								+ "+"
								+ shopItem.getEnchant()
								+ " "
								+ dummy.getName()
								+ (shopItem.getDeleteTime() != null ? (" ["
										+ sdf.format(shopItem.getDeleteTime()
												.getTime()) + "]") : ""));
					} else {
						writeS(atrname
								+ ""
								+ dummy.getName()
								+ (shopItem.getDeleteTime() != null ? (" ["
										+ sdf.format(shopItem.getDeleteTime()
												.getTime()) + "]") : ""));
					}
					if (identify) {
						dummy.setEnchantLevel(shopItem.getEnchant());
						byte[] status = dummy.getStatusBytes();
						writeC(status.length);
						for (byte b : status) {
							writeC(b);
						}
					} else
						writeC(0);
				}
			}
			writeH(0x00);
		} else if (type == 1) {
			List<L1ShopItem> list = shop.getPurchasingItems();
			int size = list.size();

			boolean ck = false;
			for (int i = 0; i < size; i++) {
				L1ShopItem shopItem = list.get(i);
				L1Item item = shopItem.getItem();
				int enchant = shopItem.getEnchant();
				for (L1ItemInstance pcItem : pc.getInventory().getItems()) {
					try {
						if (item.getItemId() == pcItem.getItemId()
								&& enchant == pcItem.getEnchantLevel()) {
							ck = true;
							break;
						}
					} catch (Exception e) {
					}
				}
				if (ck)
					break;
			}
			if (!ck)
				size = 0;

			writeH(size);
			for (int i = 0; i < size; i++) {
				L1ShopItem shopItem = list.get(i);
				L1Item item = shopItem.getItem();
				int count = shopItem.getCount();
				int price = shopItem.getPrice();
				int enchant = shopItem.getEnchant();
				for (L1ItemInstance pcItem : pc.getInventory().getItems()) {
					try {
						if (item.getItemId() == pcItem.getItemId()
								&& enchant == pcItem.getEnchantLevel()) {
							writeC(i);
							writeD(count);
							writeD(price);
							writeD(pcItem.getId());
							writeC(0x00);
							/*
							 * writeD(pcItem.getId()); writeD(count);
							 * writeD(price);
							 */
						}
					} catch (Exception e) {
					}
				}
			}
			writeH(0x00);
			/*
			 * S -> C 0000: 0b 01 82 31 f9 24 03 00 01 28 e9 65 25 05 00 00
			 * ...1.$...(.e%... 0010: 00 cf 04 00 00 02 27 e9 65 25 01 00 00 00
			 * cf 04 ......'.e%...... 0020: 00 00
			 */
		}
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}
}
