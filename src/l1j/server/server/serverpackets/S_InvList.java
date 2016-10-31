package l1j.server.server.serverpackets;

import java.sql.Timestamp;
import java.util.List;

import l1j.server.server.Opcodes;
import l1j.server.server.model.ItemClientCode;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;

public class S_InvList extends ServerBasePacket {

	private static final String S_INV_LIST = "[S] S_InvList";

	/**
	 * 목록에 아이템을 복수개 정리해 추가한다.
	 */

	public S_InvList(L1PcInstance pc) {
		if (pc.getInventory().checkItem(6013)) {
			pc.getInventory().consumeItem(6013, pc.getInventory().countItems(6013));
		}
		if (pc.getInventory().checkItem(6014)) {
			pc.getInventory().consumeItem(6014, pc.getInventory().countItems(6014));
		}
		if (pc.getInventory().checkItem(60512)) {
			pc.getInventory().consumeItem(60512, pc.getInventory().countItems(60512));
		}
		if (pc.getInventory().checkItem(60513)) {
			pc.getInventory().consumeItem(60513, pc.getInventory().countItems(60513));
		}
		if (pc.getInventory().checkItem(40005)) { // 7236
			L1ItemInstance item = pc.getInventory().checkEquippedItem(40005); // 7236
			if (item != null) {
				pc.getInventory().setEquipped(item, false, false, false);
			}
			pc.getInventory().consumeItem(40005, // 7236
					pc.getInventory().countItems(40005)); // 7236
		}

		List<L1ItemInstance> items = pc.getInventory().getItems();
		for (L1ItemInstance item : items) {
			if (item.getItemId() == 20082) {
				if (item.getEndTime() == null) {
					Timestamp deleteTime = null;
					deleteTime = new Timestamp(System.currentTimeMillis() + (3600000 * 24 * 7));// 3일
					item.setEndTime(deleteTime);
				}
			}

			if (item.getItemId() == L1ItemId.DRAGON_KEY) {
				if (System.currentTimeMillis() > item.getEndTime().getTime()) {
					pc.getInventory().deleteItem(item);
				}
			}
		}

		writeC(Opcodes.S_ADD_INVENTORY_BATCH);
		writeC(items.size());
		byte[] status = null;
		for (L1ItemInstance item : items) {
			writeD(item.getId());
			writeH(ItemClientCode.code(item.getItemId()));
			if (item.getItemId() == 40008) {
				writeH(0x0827);
			} else if (item.getItemId() == 5559 || item.getItemId() == 5560) {
				writeH(0x0044);
			} else {
				writeC(item.getItem().getUseType());
				if (item.getItemId() >= 40859 && item.getItemId() <= 40898) {
					writeC(item.getItemId() - 40859);
				} else {
					writeC(0);
				}
			}

			writeH(item.get_gfxid());
			writeC(item.getBless());
			writeD(item.getCount());
			// if(item.isIdentified()){
			if (item.getBless() >= 128 && item.getBless() <= 131) {
				writeC(32);
			} else {
				int type = 0;
				if (item.getItem().get_safeenchant() == -1)
					type += 8;
				if (item.getItem().isCantDelete())
					type += 4;
				if (!item.getItem().isTradable()) {
					type += 2;
					if ((item.getItemId() >= 437010 && item.getItemId() <= 437013) || item.getItemId() == 5000067
							|| item.getItemId() == 60104 || (item.getItemId() >= 21125 && item.getItemId() <= 21136)
							|| (item.getItemId() >= 20452 && item.getItemId() <= 20455)
							|| (item.getItemId() >= 421000 && item.getItemId() <= 421020) || item.getItemId() == 560025
							|| item.getItemId() == 560027 || item.getItemId() == 560028

							|| (item.getItemId() >= 21139 && item.getItemId() <= 21156)
							|| (item.getItemId() >= 60286 && item.getItemId() <= 60289)
							|| (item.getItemId() >= 60261 && item.getItemId() <= 60263) || item.getItemId() == 60354
							|| item.getItemId() == 60396 || item.getItemId() == 60398
							|| (item.getItemId() >= 60427 && item.getItemId() <= 60444)
							|| (item.getItemId() >= 60447 && item.getItemId() <= 60472) || item.getItemId() == 21256
							|| item.getItemId() == 21257 || item.getItemId() == 60492) {
						type += 16;
					}
				}

				if (type == 0) {
					type = 128;
				}

				writeC(type);
			}

			writeS(item.getViewName());
			if (!item.isIdentified()) {
				// 미감정의 경우 스테이터스를 보낼 필요는 없다
				writeC(0);
			} else {
				status = item.getStatusBytes(pc);
				writeC(status.length);
				for (byte b : status) {
					writeC(b);
				}
			}
			writeD(0x17);
			writeH(0x00);
			writeC(item.getEnchantLevel());
			writeD(item.getId());
			writeD(0);
			writeD(0);
			if (!((item.getItemId() >= 437010 && item.getItemId() <= 437013) || item.getItemId() == 5000067
					|| item.getItemId() == 60104 || (item.getItemId() >= 21125 && item.getItemId() <= 21136)
					|| (item.getItemId() >= 20452 && item.getItemId() <= 20455)
					|| (item.getItemId() >= 421000 && item.getItemId() <= 421020) || item.getItemId() == 560025
					|| item.getItemId() == 560027 || item.getItemId() == 560028
					|| (item.getItemId() >= 21139 && item.getItemId() <= 21156)
					|| (item.getItemId() >= 60286 && item.getItemId() <= 60289)
					|| (item.getItemId() >= 60261 && item.getItemId() <= 60263) || item.getItemId() == 60354
					|| item.getItemId() == 60396 || item.getItemId() == 60398
					|| (item.getItemId() >= 60427 && item.getItemId() <= 60444)
					|| (item.getItemId() >= 60447 && item.getItemId() <= 60472) || item.getItemId() == 21256
					|| item.getItemId() == 21257 || item.getItemId() == 60492) && !item.getItem().isTradable()) {
				writeC(0x00);
			} else {
				writeC(7);// 창고 보관 가능
			}

			writeD(0x00);
		}
		writeH(0x00);
	}

	@Override
	public byte[] getContent() {
		return _bao.toByteArray();
	}

	@Override
	public String getType() {
		return S_INV_LIST;
	}
}
