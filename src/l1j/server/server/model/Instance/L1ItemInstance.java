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

package l1j.server.server.model.Instance;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;

import l1j.server.GameSystem.CrockSystem;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.ArmorSetTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1EquipmentTimer;
import l1j.server.server.model.L1ItemOwnerTimer;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.templates.L1Armor;
import l1j.server.server.templates.L1ArmorSets;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Pet;
import l1j.server.server.utils.BinaryOutputStream;

//Referenced classes of package l1j.server.server.model:
//L1Object, L1PcInstance

public class L1ItemInstance extends L1Object {

	private static final long serialVersionUID = 1L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.KOREA);

	/** 패키지상점 **/
	private boolean _isPackage = false;

	public boolean isPackage() {
		return _isPackage;
	}

	public void setPackage(boolean _isPackage) {
		this._isPackage = _isPackage;
	}

	private int _count;
	private int _class = -1;

	public boolean _isSecond = false;
	private int _itemId;
	private L1Item _item;
	private boolean _isEquipped = false;
	private int _enchantLevel;
	private int _attrenchantLevel;
	private int _stepenchantLevel; // 단계강화주문서
	private boolean _isIdentified = false;
	private int _durability;
	private int _chargeCount;
	private int _remainingTime;
	private Timestamp _lastUsed = null;

	private String _encobjid = null;

	private int bless;
	private int _lastWeight;
	private final LastStatus _lastStatus = new LastStatus();
	private L1PcInstance _pc;
	public boolean _isRunning = false;
	private EnchantTimer _timer;
	private Timestamp _buyTime = null;
	private Timestamp _endTime = null;
	private boolean _demon_bongin = false;
	private int RingSlotNum = 13;

	private int _Key = 0;

	public L1ItemInstance() {
		_count = 1;
		_enchantLevel = 0;
	}

	public L1ItemInstance(L1Item item, int count) {
		this();
		setItem(item);
		setCount(count);
	}

	public L1ItemInstance(L1Item item) {
		this(item, 1);
	}

	public void clickItem(L1Character cha, ClientBasePacket packet) {
	}

	public int getRSN() {
		return RingSlotNum;
	}

	public void setRSN(int num) {
		RingSlotNum = num;
	}

	public String getEncobjid() {
		return _encobjid;
	}

	public void setEncobjid(String s) {
		_encobjid = s;
	}

	public void setEnchantWA(L1PcInstance p) {
		_pc = p;
	}

	public boolean isIdentified() {
		return _isIdentified;
	}

	public void setIdentified(boolean identified) {
		_isIdentified = identified;
	}

	public String getName() {
		return _item.getName();
	}

	public int getCount() {
		return _count;
	}

	public void setCount(int count) {
		_count = count;
	}

	public int getClassType() {
		return _class;
	}

	public void setClassType(int count) {
		_class = count;
	}

	public boolean isEquipped() {
		return _isEquipped;
	}

	public void setEquipped(boolean equipped) {
		_isEquipped = equipped;
	}

	public boolean isDemonBongin() {
		return _demon_bongin;
	}

	public void setDemonBongin(boolean ck) {
		_demon_bongin = ck;
	}

	public L1Item getItem() {
		return _item;
	}

	public void setItem(L1Item item) {
		_item = item;
		_itemId = item.getItemId();
	}

	public int getItemId() {
		return _itemId;
	}

	public void setItemId(int itemId) {
		_itemId = itemId;
	}

	public boolean isStackable() {
		return _item.isStackable();
	}

	@Override
	public void onAction(L1PcInstance player) {
	}

	public int getEnchantLevel() {
		return _enchantLevel;
	}

	public void setEnchantLevel(int enchantLevel) {
		_enchantLevel = enchantLevel;
	}

	public int getAttrEnchantLevel() {
		return _attrenchantLevel;
	}

	public void setAttrEnchantLevel(int attrenchantLevel) {
		_attrenchantLevel = attrenchantLevel;
	}

	public int getStepEnchantLevel() {
		return _stepenchantLevel;
	}

	public void setStepEnchantLevel(int stepenchantLevel) {
		_stepenchantLevel = stepenchantLevel;
	}

	private int temp_gfx = 0;

	public int get_gfxid() {
		return temp_gfx == 0 ? _item.getGfxId() : temp_gfx;
	}

	public void set_tempGfx(int i) {
		temp_gfx = i;
	}

	public int get_tempGfx() {
		return temp_gfx;
	}

	public int get_durability() {
		return _durability;
	}

	public int getChargeCount() {
		return _chargeCount;
	}

	public void setChargeCount(int i) {
		_chargeCount = i;
	}

	public int getRemainingTime() {
		return _remainingTime;
	}

	public void setRemainingTime(int i) {
		_remainingTime = i;
	}

	public void setLastUsed(Timestamp t) {
		_lastUsed = t;
	}

	public Timestamp getLastUsed() {
		return _lastUsed;
	}

	public int getBless() {
		return bless;
	}

	public void setBless(int i) {
		bless = i;
	}

	public int getLastWeight() {
		return _lastWeight;
	}

	public void setLastWeight(int weight) {
		_lastWeight = weight;
	}

	public Timestamp getBuyTime() {
		return _buyTime;
	}

	public void setBuyTime(Timestamp t) {
		_buyTime = t;
	}

	public int getKey() {
		return _Key;
	}

	public void setKey(int t) {
		_Key = t;
	}

	private String _CreaterName;

	public String getCreaterName() {
		return _CreaterName;
	}

	public void setCreaterName(String name) {
		_CreaterName = name;
	}

	public Timestamp getEndTime() {
		return _endTime;
	}

	public void setEndTime(Timestamp t) {
		_endTime = t;
	}

	private int _registlevel;

	public int getRegistLevel() {
		return _registlevel;
	}

	public void setRegistLevel(int level) {
		_registlevel = level;
	}

	/** 룸티스 푸른빛귀걸이 물약효율표시 **/
	private String RoomtisHealingPotion11() {
		int lvl = getEnchantLevel();
		String in = "";
		switch (lvl) {
		case 0:
			in = "공포 회복감소 효과 상쇄 +2%";
			break;
		case 1:
			in = "공포 회복감소 효과 상쇄 +6%";
			break;
		case 2:
			in = "공포 회복감소 효과 상쇄 +8%";
			break;
		case 3:
			in = "공포 회복감소 효과 상쇄 +10%";
			break;
		case 4:
			in = "공포 회복감소 효과 상쇄 +12%";
			break;
		case 5:
			in = "공포 회복감소 효과 상쇄 +14%";
			break;
		case 6:
			in = "공포 회복감소 효과 상쇄 +16%";
			break;
		case 7:
			in = "공포 회복감소 효과 상쇄 +18%";
			break;
		case 8:
			in = "공포 회복감소 효과 상쇄 +20%";
			break;
		default:
			break;
		}
		return in;
	}

	/** 룸티스 푸른빛귀걸이 물약효율표시 **/
	private String RoomtisHealingPotion12() {
		int lvl = getEnchantLevel();
		String in = "";
		switch (lvl) {
		case 0:
			in = "공포 회복감소 효과 상쇄 +2%";
			break;
		case 1:
			in = "공포 회복감소 효과 상쇄 +6%";
			break;
		case 2:
			in = "공포 회복감소 효과 상쇄 +8%";
			break;
		case 3:
			in = "공포 회복감소 효과 상쇄 +12%";
			break;
		case 4:
			in = "공포 회복감소 효과 상쇄 +14%";
			break;
		case 5:
			in = "공포 회복감소 효과 상쇄 +16%";
			break;
		case 6:
			in = "공포 회복감소 효과 상쇄 +18%";
			break;
		case 7:
			in = "공포 회복감소 효과 상쇄 +20%";
			break;
		case 8:
			in = "공포 회복감소 효과 상쇄 +22%";
			break;
		default:
			break;
		}
		return in;
	}
	
	/** 일반귀걸이,목걸이 공포 회복 감소 효과 상쇄표시 **/
	private String RoomtisHealingPotion14() {
		int lvl = getEnchantLevel();
		String in = "";
		switch (lvl) {
		case 5:
			in = "공포 회복감소 효과 상쇄 +2%";
			break;
		case 6:
			in = "공포 회복감소 효과 상쇄 +4%";
			break;
		case 7:
			in = "공포 회복감소 효과 상쇄 +6%";
			break;
		case 8:
			in = "공포 회복감소 효과 상쇄 +8%";
			break;
		case 9:
			in = "공포 회복감소 효과 상쇄 +9%";
			break;			
		default:
			break;
		}
		return in;
	}

	public int getsp() {
		int sp = _item.get_addsp();
		int itemid = getItemId();
		/*
		 * if (itemid == 134) { //수결지 코드번호 sp += getEnchantLevel() * 2; }else
		 */

		if (itemid == 20107) { // 리롭
			if (getEnchantLevel() >= 3)
				sp += getEnchantLevel() - 2;
		} else if (itemid == 120107) { // 축 리롭
			if (getEnchantLevel() >= 3)
				sp += getEnchantLevel() - 2;
		}
		int itemtype = _item.getType();
		int itemgrade = _item.getGrade();
		if (itemgrade != 3) {
			if (itemtype == 9 || itemtype == 11) {// 반지
				if (getEnchantLevel() == 7) {
					sp += 1;
				}
				if (getEnchantLevel() == 8) {
					sp += 2;
				}
			}
		}
		if (sp < 0)
			sp = 0;
		return sp;
	}

	public int getMr() {
		int mr = _item.get_mdef();
		int itemid = getItemId();
		if (itemid == 20011 || itemid == 20110 || itemid == 120011 || itemid == 9091 || itemid == 900019
		// || itemid == 420108 || itemid == 420109 || itemid == 420110
		// || itemid == 420111
				|| itemid == 490008 || itemid == 490017 || itemid == 120194 || itemid == 1020110
				|| (itemid >= 21169 && itemid <= 21172)) {
			mr += getEnchantLevel();
		}

		if (itemid == 500214 || itemid == 7247 || itemid == 9083 || itemid == 20117) {
			mr += getEnchantLevel();
		}

		if (itemid == 20056 || itemid == 120056 || itemid == 9092 || itemid == 220056 || itemid == 425108
				|| itemid == 20074 || itemid == 9084 || itemid == 120074) {
			mr += getEnchantLevel() * 2;
		}
		if (itemid == 20078 || itemid == 20079 || itemid == 120079 || itemid == 21137) {
			mr += getEnchantLevel() * 3;
		}
		if (itemid == 220011 || itemid == 20049 || itemid == 20050)
			mr += getEnchantLevel() * 2;
		// **룸티스의 보랏빛 귀걸이 마방**//
		if (itemid == 500009) {
			// mr += (getEnchantLevel() * 1);
			if (getEnchantLevel() == 7)
				mr += 10;
			else if (getEnchantLevel() == 8)
				mr += 13;
			else if (getEnchantLevel() > 0)
				mr += 2 + getEnchantLevel();
		}

		if (itemid == 502009) {
			if (getEnchantLevel() == 7)
				mr += 13;
			else if (getEnchantLevel() == 8)
				mr += 18;
			else if (getEnchantLevel() > 0)
				mr += 3 + getEnchantLevel();
		}

		if (itemid == 21251) {// 스냅퍼마법저항반지 축복
			if (getEnchantLevel() >= 6) {
				mr += getEnchantLevel() - 5;
			}
		}
		// **룸티스의 보랏빛 귀걸이 마방**//

		/*
		 * if(getItem().getGrade() == 2 ){//장신구 추가 mr = mr + getEnchantLevel() *
		 * 1; } if(getItem().getGrade() == 3 ){//장신구 추가 mr = mr +
		 * getEnchantLevel() * 2; }
		 */
		if (mr < 0)
			mr = 0; // << -마방버그 픽스
		return mr;
	}

	public void set_durability(int i) {
		if (i < 0) {
			i = 0;
		}

		if (i > 127) {
			i = 127;
		}
		_durability = i;
	}

	public int getWeight() {
		if (getItem().getWeight() == 0) {
			return 0;
		} else {
			return Math.max(getCount() * getItem().getWeight() / 1000, 1);
		}
	}

	public class LastStatus {
		public int registLevel;
		public int count;
		public int itemId;
		public boolean isEquipped = false;
		public int enchantLevel;
		public boolean isIdentified = true;
		public int durability;
		public int chargeCount;
		public int remainingTime;
		public Timestamp lastUsed = null;
		public int bless;
		public int attrenchantLevel;
		public int stepenchantLevel;
		public Timestamp endTime = null;
		public boolean demon_bongin;

		public void updateAll() {
			count = getCount();
			itemId = getItemId();
			isEquipped = isEquipped();
			isIdentified = isIdentified();
			enchantLevel = getEnchantLevel();
			durability = get_durability();
			chargeCount = getChargeCount();
			remainingTime = getRemainingTime();
			lastUsed = getLastUsed();
			bless = getBless();
			attrenchantLevel = getAttrEnchantLevel();
			stepenchantLevel = getStepEnchantLevel();
			registLevel = getRegistLevel();
			endTime = getEndTime();
			demon_bongin = isDemonBongin();

		}

		public void updateCount() {
			count = getCount();
		}

		public void updateItemId() {
			itemId = getItemId();
		}

		public void updateEquipped() {
			isEquipped = isEquipped();
		}

		public void updateIdentified() {
			isIdentified = isIdentified();
		}

		public void updateEnchantLevel() {
			enchantLevel = getEnchantLevel();
		}

		public void updateDuraility() {
			durability = get_durability();
		}

		public void updateChargeCount() {
			chargeCount = getChargeCount();
		}

		public void updateRemainingTime() {
			remainingTime = getRemainingTime();
		}

		public void updateLastUsed() {
			lastUsed = getLastUsed();
		}

		public void updateBless() {
			bless = getBless();
		}

		public void updateAttrEnchantLevel() {
			attrenchantLevel = getAttrEnchantLevel();
		}

		public void updateRegistLevel() {
			registLevel = getRegistLevel();
		}

		public void updateEndTIme() {
			endTime = getEndTime();
		}

		public void updateDemonBongin() {
			demon_bongin = isDemonBongin();
		}

		public void updateStepEnchantLevel() {
			stepenchantLevel = getStepEnchantLevel();
		}
	}

	public LastStatus getLastStatus() {
		return _lastStatus;
	}

	public int getRecordingColumns() {
		int column = 0;

		if (getCount() != _lastStatus.count) {
			column += L1PcInventory.COL_COUNT;
		}
		if (getItemId() != _lastStatus.itemId) {
			column += L1PcInventory.COL_ITEMID;
		}
		if (isEquipped() != _lastStatus.isEquipped) {
			column += L1PcInventory.COL_EQUIPPED;
		}
		if (getEnchantLevel() != _lastStatus.enchantLevel) {
			column += L1PcInventory.COL_ENCHANTLVL;
		}
		if (get_durability() != _lastStatus.durability) {
			column += L1PcInventory.COL_DURABILITY;
		}
		if (getChargeCount() != _lastStatus.chargeCount) {
			column += L1PcInventory.COL_CHARGE_COUNT;
		}
		if (getLastUsed() != _lastStatus.lastUsed) {
			column += L1PcInventory.COL_DELAY_EFFECT;
		}
		if (isIdentified() != _lastStatus.isIdentified) {
			column += L1PcInventory.COL_IS_ID;
		}
		if (getRemainingTime() != _lastStatus.remainingTime) {
			column += L1PcInventory.COL_REMAINING_TIME;
		}
		if (getBless() != _lastStatus.bless) {
			column += L1PcInventory.COL_BLESS;
		}
		if (getAttrEnchantLevel() != _lastStatus.attrenchantLevel) {
			column += L1PcInventory.COL_ATTRENCHANTLVL;
		}
		if (getRegistLevel() != _lastStatus.registLevel) {
			column += L1PcInventory.COL_regist;
		}
		if (getEndTime() != _lastStatus.endTime) {
			column += L1PcInventory.COL_ENDTIME;
		}
		if (isDemonBongin() != _lastStatus.demon_bongin) {
			column += L1PcInventory.COL_DEMONBONGIN;
		}
		return column;
	}

	public String getNumberedViewName(int count) {
		return getNumberedViewName(count, false);
	}

	public String getNumberedViewName(int count, boolean privateShop) {
		StringBuilder name = new StringBuilder(getNumberedName(count, privateShop));
		int itemType2 = getItem().getType2();
		int itemId = getItem().getItemId();

		if (itemId == 40314 || itemId == 40316) {
			L1Pet pet = PetTable.getInstance().getTemplate(getId());
			if (pet != null) {
				if (privateShop) {
					L1Npc npc = NpcTable.getInstance().getTemplate(pet.get_npcid());
					name.append("[Lv." + pet.get_level() + " " + npc.get_nameid() + "]HP" + pet.get_hp() + " "
							+ npc.get_nameid());
				} else
					name.append("[Lv." + pet.get_level() + " " + pet.get_name() + "]");
			}
		}

		if (getItem().getType2() == 0 && getItem().getType() == 2) { // light
			if (isNowLighting()) {
				name.append(" ($10)");
			}
			if (itemId == 40001 || itemId == 40002 || itemId == 60154) {
				if (getRemainingTime() <= 0) {
					name.append(" ($11)");
				}
			}
		}

		if (getItem().getItemId() == L1ItemId.TEBEOSIRIS_KEY || getItem().getItemId() == L1ItemId.TIKAL_KEY) {
			name.append(" [" + CrockSystem.getInstance().OpenTime() + "]");
		}

		if (getItem().getItemId() == L1ItemId.DRAGON_KEY || (getItemId() >= 60350 && getItemId() <= 60352)) {// 드래곤
																												// 키
			name.append(" [" + sdf.format(getEndTime().getTime()) + "]");
		}

		if (getItem().getItemId() == 60285) {// 훈련소 열쇠
			if (getEndTime() != null) {
				int mapid = getKey() - 1399;
				String date = " (" + (mapid > 9 ? mapid : "0" + mapid) + ") [" + sdf.format(getEndTime().getTime())
						+ "]";
				date = date.replace("[0", "[");
				name.append(date);
			}
		}

		if (getItem().getItemId() == 40312 || getItem().getItemId() == 49312) {// 여관열쇠
			if (getEndTime() != null)
				name.append(" [" + sdf.format(getEndTime().getTime()) + "] CHECK : " + getKey());
		}

		if (getItem().getItemId() == 20344 || getItem().getItemId() == 21092 || itemId == 60061 || // 토끼투구,
																									// 요리모자,
																									// 버프코인
				(getItem().getItemId() >= 60009 && getItem().getItemId() <= 60016) || // 계약서
				(itemId >= 425000 && itemId <= 425002) || // 엘모어 방어구
				(itemId >= 450000 && itemId <= 450007) || // 엘모어 무기
				itemId == 21094 || // 위대한 자의 유물
				itemId == 21157 || itemId == 121216 || itemId == 221216 || (itemId >= 60173 && itemId <= 60176)
				|| (itemId >= 21113 && itemId <= 21120) || itemId == 430003 || itemId == 430505 || itemId == 430506
				|| itemId == 41915 || itemId == 5000034// 블레그 마법 인형
				|| (itemId >= 21125 && itemId <= 21136) || (itemId >= 9075 && itemId <= 9093)
				|| (itemId >= 21139 && itemId <= 21156) || (itemId >= 427113 && itemId <= 427207)
				|| (itemId >= 427120 && itemId <= 427122) || (itemId >= 21158 && itemId <= 21165)
				|| (itemId >= 267 && itemId <= 274) || itemId == 600234 || itemId == 450028 // 영웅의
																							// 무기
				|| itemId == 450029 || itemId == 450030 || itemId == 450031 || itemId == 450032 || itemId == 450033
				|| itemId == 450034 || itemId == 450035 || itemId == 450036 || itemId == 21005 || itemId == 500215
				|| itemId == 21032 || itemId == 110111 || itemId == 500216
				// 상아탑템들

				/*
				 * || itemId == 7 || itemId == 35 || itemId == 48 || itemId ==
				 * 73 || itemId == 105 || itemId == 120 || itemId == 147 ||
				 * itemId == 156 || itemId == 174 || itemId == 175 || itemId ==
				 * 224 || itemId == 20028 || itemId == 20082 || itemId == 20126
				 * || itemId == 20173 || itemId == 20206 || itemId == 20232 ||
				 * itemId == 20282 || itemId == 201261 || itemId == 21098 ||
				 * (itemId == 21102 && itemId <= 21112) || itemId == 21254
				 */

				|| (itemId >= 21099 && itemId <= 21112) || itemId == 21254 || itemId == 20082 || itemId == 7
				|| itemId == 35 || itemId == 48 || itemId == 73 || itemId == 105 || itemId == 120 || itemId == 147
				|| itemId == 7232 || itemId == 156 || itemId == 174 || itemId == 175 || itemId == 224 || itemId == 9056// 그렘린
				|| itemId == 141915 || itemId == 141916 || itemId == 1419161 || itemId == 60319) {
			if (getEndTime() != null) {
				String date = " [" + sdf.format(getEndTime().getTime()) + "]";
				date = date.replace("[0", "[");
				name.append(date);
			}
		}

		if (getItem().getItemId() == 40309) {
			String cname = getCreaterName();
			name.append(" " + getRoundId() + "-" + (getTicketId() + 1) + " "
					+ (cname == null || cname.equalsIgnoreCase("null") ? "" : cname));
		}

		if (isEquipped()) {
			if (itemType2 == 1) {
				name.append(" ($9)");
			} else if (itemType2 == 2 && !getItem().isUseHighPet()) {
				name.append(" ($117)");
			}
		}
		return name.toString();
	}

	public String getViewName() {
		return getNumberedViewName(_count);
	}

	public String getLogName() {
		return getNumberedName(_count);
	}

	public String getNumberedName(int count) {
		return getNumberedName(count, false);
	}

	public String getNumberedName(int count, boolean privateShop) {
		StringBuilder name = new StringBuilder();
		if (getItemId() == 600240) {
			if (getEnchantLevel() >= 0) {
				name.append("+" + getEnchantLevel() + " ");
			} else if (getEnchantLevel() < 0) {
				name.append(String.valueOf(getEnchantLevel()) + " ");
			}
		}
		if (isIdentified()) {
			if (getItem().getType2() == 1 || getItem().getType2() == 2) {
				switch (getAttrEnchantLevel()) {
				case 1:
					name.append("화령:1단 ");
					break;
				case 2:
					name.append("화령:2단 ");
					break;
				case 3:
					name.append("화령:3단 ");
					break;
				case 4:
					name.append("수령:1단 ");
					break;
				case 5:
					name.append("수령:2단 ");
					break;
				case 6:
					name.append("수령:3단 ");
					break;
				case 7:
					name.append("풍령:1단 ");
					break;
				case 8:
					name.append("풍령:2단 ");
					break;
				case 9:
					name.append("풍령:3단 ");
					break;
				case 10:
					name.append("지령:1단 ");
					break;
				case 11:
					name.append("지령:2단 ");
					break;
				case 12:
					name.append("지령:3단 ");
					break;
				/*
				 * case 1: name.append("$6115"); break; case 2:
				 * name.append("$6116"); break; case 3: name.append("$6117");
				 * break; case 4: name.append("$6118"); break; case 5:
				 * name.append("$6119"); break; case 6: name.append("$6120");
				 * break; case 7: name.append("$6121"); break; case 8:
				 * name.append("$6122"); break; case 9: name.append("$6123");
				 * break; case 10: name.append("$6124"); break; case 11:
				 * name.append("$6125"); break; case 12: name.append("$6126");
				 * break;
				 */
				case 33:
					name.append("화령:4단 ");
					break;
				case 34:
					name.append("화령:5단 ");
					break;
				case 35:
					name.append("수령:4단 ");
					break;
				case 36:
					name.append("수령:5단 ");
					break;
				case 37:
					name.append("풍령:4단 ");
					break;
				case 38:
					name.append("풍령:5단 ");
					break;
				case 39:
					name.append("지령:4단 ");
					break;
				case 40:
					name.append("지령:5단 ");
					break;

				case 41:
					name.append("[리덕션 +1]");
					break;
				case 42:
					name.append("[리덕션 +2]");
					break;
				case 43:
					name.append("[리덕션 +3]");
					break;
				case 44:
					name.append("[리덕션 +4]");
					break;
				case 45:
					name.append("[리덕션 +5]");
					break;
				case 46:
					name.append("[리덕션 +6]");
					break;
				case 47:
					name.append("[리덕션 +7]");
					break;
				case 48:
					name.append("[리덕션 +8]");
					break;
				case 49:
					name.append("[리덕션 +9]");
					break;
				case 50:
					name.append("[리덕션 +10]");
					break;
				case 51:
					name.append("[리덕션 +11]");
					break;
				case 52:
					name.append("[리덕션 +12]");
					break;
				case 53:
					name.append("[리덕션 +13]");
					break;
				case 54:
					name.append("[리덕션 +14]");
					break;
				case 55:
					name.append("[리덕션 +15]");
					break;
				case 56:
					name.append("[리덕션 +16]");
					break;
				case 57:
					name.append("[리덕션 +17]");
					break;
				case 58:
					name.append("[리덕션 +18]");
					break;
				case 59:
					name.append("[리덕션 +19]");
					break;
				case 60:
					name.append("[리덕션 +20]");
					break;
				case 61:
					name.append("[리덕션 +21]");
					break;
				case 62:
					name.append("[리덕션 +22]");
					break;
				case 63:
					name.append("[리덕션 +23]");
					break;
				case 64:
					name.append("[리덕션 +24]");
					break;
				case 65:
					name.append("[리덕션 +25]");
					break;
				case 66:
					name.append("[리덕션 +26]");
					break;
				case 67:
					name.append("[리덕션 +27]");
					break;
				case 68:
					name.append("[리덕션 +28]");
					break;
				case 69:
					name.append("[리덕션 +29]");
					break;
				case 70:
					name.append("[리덕션 +30]");
					break;
				default:
					break;
				}
				if (getEnchantLevel() >= 0) {
					name.append("+" + getEnchantLevel() + " ");
				} else if (getEnchantLevel() < 0) {
					name.append(String.valueOf(getEnchantLevel()) + " ");
				}
			}
		}
		name.append(_item.getNameId());

		if (isIdentified()) {
			if (getItem().getType2() == 1 || getItem().getType2() == 2) {
				if (getStepEnchantLevel() > 0) {
					name.append(" [" + getStepEnchantLevel() + "단]");
				}
			}
		}

		if (getItem().getItemId() >= 600251 && getItem().getItemId() <= 600254) {
			if (getItem().getMaxChargeCount() > 0) {
				name.append(" (" + getChargeCount() + ")");
			}
		}
		if (isIdentified()) {
			if (getItem().getItemId() < 600251 || getItem().getItemId() > 600254) {
				if (getItem().getMaxChargeCount() > 0) {
					name.append(" (" + getChargeCount() + ")");
				}
			}
			if (getItem().getMaxUseTime() > 0 && getItem().getType2() != 0) {
				name.append(" [" + getRemainingTime() + "]");
			} else if (getItemId() >= 60173 && getItemId() <= 60176 && getRemainingTime() > 0)
				name.append(" [" + getRemainingTime() + "]");
		}

		if (!privateShop && count > 1) {
			name.append(" (" + count + ")");
		}

		return name.toString();
	}

	public byte[] getStatusBytes() {
		int itemType2 = getItem().getType2();
		int itemId = getItemId();
		BinaryOutputStream os = new BinaryOutputStream();

		if (itemType2 == 0) { // etcitem
			switch (getItem().getType()) {
			case 2: // light
				os.writeC(22);
				os.writeH(getItem().getLightRange());
				break;
			case 7: // food
				os.writeC(21);
				os.writeH(getItem().getFoodVolume());
				break;
			case 0: // arrow
			case 15: // sting
				os.writeC(1);
				os.writeC(getItem().getDmgSmall());
				os.writeC(getItem().getDmgLarge());
				break;
			default:
				if (itemId == 40314 || itemId == 40316) {
					L1Pet pet = PetTable.getInstance().getTemplate(getId());
					if (pet != null) {
						L1Npc npc = NpcTable.getInstance().getTemplate(pet.get_npcid());
						String nid = npc.get_nameid();
						try {
							if (nid.charAt(0) == '$')
								nid = nid.substring(1);
							else
								nid = "1194";
							int i = Integer.parseInt(nid);
							os.writeC(25);
							os.writeH(i);
						} catch (Exception e) {
							os.writeC(25);
							os.writeH(1194);
						}
						os.writeC(26);
						os.writeH(pet.get_level());
						os.writeC(31);
						os.writeH(pet.get_hp());
					}
				}
				os.writeC(23);
				break;
			}

			os.writeC(getItem().getMaterial());
			os.writeD(getWeight());

			/*
			 * 0e 17 05 07 00 00 00 4d 03 07 0c 4b 01 4c 19 //덱스 단계4 라우풀
			 * 
			 * 0e 17 05 07 00 00 00 4d 03 07 0c 4b 00 4c 1f //메디 단계4 뉴트럴 0e 17
			 * 05 07 00 00 00 4d 04 07 0c 4b 01 4c 22 //그힐 단계5 라우풀 0e 17 05 07
			 * 00 00 00 4d 04 07 0c 4b 01 4c 24//리무브 단계5 라우풀 0e 17 05 07 00 00
			 * 00 4d 05 07 0c 4b 00 4c 29//힘 단계6 뉴트럴 0e 17 05 07 00 00 00 4d 05
			 * 07 0c 4b 00 4c 2a//헤이 단계6 뉴트럴 0e 17 05 07 00 00 00 4d 06 07 08 4b
			 * 01 4c 30//힐올 단계7 라우풀 0c 17 05 07 00 00 00 4f 3c 07 01 4c
			 * 78//브레이브아바타 레벨60
			 */
			/*
			 * if ((itemId >= 90085 && itemId <= 90092) || itemId == 160423 ||
			 * itemId == 435000 || itemId == 160510 || itemId == 160511 ||
			 * itemId == 21123) { os.writeC(61); os.writeD(3442346400L); } if
			 * (itemId == 21269 && getEnchantLevel() < 6) { os.writeC(61);
			 * os.writeD(3442346400L); }
			 * 
			 * if (itemId == 500206 || itemId == 500207 || itemId == 121216 ||
			 * itemId == 221216 || itemId == 500208) { os.writeC(61);
			 * os.writeD(3501426400L); }
			 */

			if (getItem().getType() == 10) {
				if ((getItem().getItemId() >= 40170 && getItem().getItemId() <= 40225)
						|| (getItem().getItemId() >= 45000 && getItem().getItemId() <= 45022)
						|| getItem().getItemId() == 140186 || getItem().getItemId() == 140196
						|| getItem().getItemId() == 140198 || getItem().getItemId() == 140204
						|| getItem().getItemId() == 140205 || getItem().getItemId() == 140210
						|| getItem().getItemId() == 140219) {
					if (getItem().getskilllv() != 0) {// 법사 단계
						os.writeC(77);// 4D
						os.writeC(getItem().getskilllv() - 1);
					}
					if (getItem().getskilllv() >= 4 && getItem().getskilllv() <= 6) {
						os.writeC(7);
						os.writeC(0x0c);
					} else {
						os.writeC(7);
						os.writeC(0x08);
					}

				} else {
					if ((getItem().getItemId() >= 40232 && getItem().getItemId() <= 40264)
							|| (getItem().getItemId() >= 41149 && getItem().getItemId() <= 41153)) {
						if (getItem().getskilllv() != 0) {// 법사 단계
							os.writeC(77);// 4D
							os.writeC(getItem().getskilllv() - 1);
						}
						os.writeC(7);
						os.writeC(0x014);

					} else {
						if (getItem().getskilllv() != 0) {// 사용레벨
							os.writeC(79);
							os.writeC(getItem().getskilllv());
						}
					}

					if ((getItem().getItemId() >= 40226 && getItem().getItemId() <= 40231)
							|| getItem().getItemId() == 60348) {// 군주
						os.writeC(7);
						os.writeC(0x01);
					}

					if ((getItem().getItemId() >= 40164 && getItem().getItemId() <= 40166)
							|| getItem().getItemId() == 41147 || getItem().getItemId() == 41148) {// 기사
						os.writeC(7);
						os.writeC(0x02);
					}

					if ((getItem().getItemId() >= 40265 && getItem().getItemId() <= 40279)
							|| getItem().getItemId() == 60199) {// 다엘
						os.writeC(7);
						os.writeC(16);
					}
					if ((getItem().getItemId() >= 439100 && getItem().getItemId() <= 439114)) {// 용기사
						os.writeC(7);
						os.writeC(32);
					}
					if ((getItem().getItemId() >= 439000 && getItem().getItemId() <= 439019)) {// 환술
						os.writeC(7);
						os.writeC(64);
					}
					if ((getItem().getItemId() >= 7300 && getItem().getItemId() <= 7311)) {// 전사
						os.writeC(7);
						os.writeC(128);
					}

					/*
					 * int bit = 0; bit |= getItem().isUseRoyal() ? 1 : 0; bit
					 * |= getItem().isUseKnight() ? 2 : 0; bit |=
					 * getItem().isUseElf() ? 4 : 0; bit |=
					 * getItem().isUseMage() ? 8 : 0;
					 * 
					 * 
					 * bit |= getItem().isUseDarkelf() ? 16 : 0; bit |=
					 * getItem().isUseDragonKnight() ? 32 : 0; bit |=
					 * getItem().isUseBlackwizard() ? 64 : 0; bit |=
					 * getItem().isUseWarrior() ? 128 : 0; //bit |=
					 * getItem().isUseHighPet() ? 128 : 0; if(itemType2 != 2 ||
					 * getItem().getType() != 12 || bit != 127){ os.writeC(7);
					 * os.writeC(bit); }
					 */
				}

				if ((getItem().getItemId() >= 40232 && getItem().getItemId() <= 40264)
						|| (getItem().getItemId() >= 41149 && getItem().getItemId() <= 41153)) {
					if (getItem().getskillattr() != 0) {// 속성
						os.writeC(78);// 4b
						os.writeC(getItem().getskillattr() - 1);
					}
				} else {
					if (getItem().getskillattr() != 0) {// 속성
						os.writeC(75);// 4b
						os.writeC(getItem().getskillattr() - 1);
					}
				}

				if (getItem().getskillnum() != 0) {// 스킬번호
					os.writeC(76);// 4c
					os.writeC(getItem().getskillnum() - 1);
				}

			}

			if (itemId == 60354) { // 시원한 얼음 조각
				os.writeC(17);
				os.writeC(2);
				os.writeC(47);
				os.writeC(2);
				os.writeC(35);
				os.writeC(2);
				os.writeC(37);
				os.writeC(1);
				os.writeC(38);
				os.writeC(1);
			} else if (itemId == 60080 || itemId == 60082 || itemId == 60084) {
				if (getCreaterName() != null) {
					os.writeC(39);
					os.writeS("제작자:" + getCreaterName());
				}
			}
		} else if (itemType2 == 1 || itemType2 == 2) { // weapon | armor
			/** 아이템 안전인챈 표시 추가 **/
			int SafeEnchant = getItem().get_safeenchant();
			os.writeC(39);
			if (SafeEnchant < 0) {
				SafeEnchant = 0;
			}
			os.writeS("\\fY[안전인챈 : +" + SafeEnchant + "]");
			/** **/
			if (itemType2 == 1) {
				if (getItem().getBless() == 0) {// 축복추타
					os.writeC(39);
					os.writeS("\\f2(축복)대미지 +1");
				}
			}
			if (itemType2 == 1) { // weapon
				os.writeC(1);
				os.writeC(getItem().getDmgSmall());
				os.writeC(getItem().getDmgLarge());
				os.writeC(getItem().getMaterial());
				os.writeD(getWeight());

			} else if (itemType2 == 2) { // armor

				// AC
				os.writeC(19);
				int ac = ((L1Armor) getItem()).get_ac();
				if (getRegistLevel() == 14)// 판도라 강철문양
					ac -= 1;
				if (ac < 0) {
					ac = ac - ac - ac;
				} else {
					ac = ac - ac - ac;
				}
				os.writeC(ac);
				os.writeC(getItem().getMaterial());
				if (getItem().getType() == 8 || getItem().getType() == 12) {
					os.writeC(0x2b);// 근성
				} else if (getItem().getType() == 9 || getItem().getType() == 11) {
					os.writeC(0x2c);// 열정
				} else if (getItem().getType() == 10) {
					os.writeC(0x2d);// 의지
				} else {
					os.writeC(-1);
				}
				/*
				 * if(itemType2 != 2 || getItem().getType() != 12)
				 * os.writeC(getItem().getGrade());// else
				 */
				os.writeD(getWeight());
			}
			if (getItemId() == 500010 || getItemId() == 502010) {
				int ac = getEnchantLevel();

				if (getBless() == 0 && getEnchantLevel() >= 3) {
					ac += 1;
				}

				if (ac > 0) {
					os.writeC(2);
					os.writeC(ac);
				}

			} else if (itemId == 500008) { // 룸티스
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(2);
					os.writeC(1);
					break;
				case 6:
				case 7:
					os.writeC(2);
					os.writeC(2);
					break;
				case 8:
					os.writeC(2);
					os.writeC(3);
					break;
				}
			} else if (itemId == 502008) { // 룸티스
				switch (getEnchantLevel()) {
				case 4:
					os.writeC(2);
					os.writeC(1);
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(2);
					break;
				case 7:
					os.writeC(2);
					os.writeC(3);
					break;
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}
			} else if ((getItem().getItemId() >= 425109 && getItem().getItemId() <= 425113)
					|| (getItem().getItemId() >= 525109 && getItem().getItemId() <= 525113)
					|| (getItem().getItemId() >= 625109 && getItem().getItemId() <= 625113)) {
				switch (getEnchantLevel()) {
				case 0:
				case 1:
					break;
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel() - 1);
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}

			} else if ((getItem().getItemId() == 525115)) { // 용사
				switch (getEnchantLevel()) {
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel());
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}
			} else if ((getItem().getItemId() == 525109 // 회복
					|| getItem().getItemId() == 525110 // 집중
					|| getItem().getItemId() == 525111 // 체력
					|| getItem().getItemId() == 525112 // 마나
					|| getItem().getItemId() == 525113 // 마저
					|| getItem().getItemId() == 525114)// 지혜
			) {
				switch (getEnchantLevel()) {
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel() - 1);
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(3);
					break;
				}
			} else if ((getItem().getItemId() == 625115)) { // 용사
				switch (getEnchantLevel()) {
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel());
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(4);
					break;
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(5);
					break;
				}
			} else if ((getItem().getItemId() == 625109 || getItem().getItemId() == 625110
					|| getItem().getItemId() == 625111 || getItem().getItemId() == 625112
					|| getItem().getItemId() == 625113 || getItem().getItemId() == 625114)) {
				switch (getEnchantLevel()) {
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel() - 1);
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(3);
					break;
				case 7:
				case 8:
					if (getItem().getItemId() == 625111 || getItem().getItemId() == 625113
							|| getItem().getItemId() == 625114) {
						os.writeC(2);
						os.writeC(4);
						break;
					} else {
						os.writeC(2);
						os.writeC(3);
						break;
					}
				}

			} else if ((getItemId() >= 21246 && getItemId() <= 21248)
					|| (getItemId() >= 21270 && getItemId() <= 21272)) { // 스냅퍼반지
				switch (getEnchantLevel()) {
				case 0:
				case 1:
					break;
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel() - 1);
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(3);
					break;
				}
			} else if ((getItemId() >= 21250 && getItemId() <= 21252)
					|| (getItemId() >= 21273 && getItemId() <= 21275)) { // 스냅퍼
																			// 축복반지
				switch (getEnchantLevel()) {
				case 0:
				case 1:
					break;
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel() - 1);
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(3);
					break;
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}
			} else if (getItemId() == 21249) { // 스냅퍼 용사 반지
				switch (getEnchantLevel()) {
				case 0:
					break;
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel());
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(4);
					break;
				}
			} else

			if (getItemId() == 21253) { // 스냅퍼 용사 축복 반지
				switch (getEnchantLevel()) {
				case 0:
					break;
				case 1:
				case 2:
				case 3:
				case 4:
					os.writeC(2);
					os.writeC(getEnchantLevel());
					break;
				case 5:
				case 6:
					os.writeC(2);
					os.writeC(4);
					break;
				case 7:
				case 8:
					os.writeC(2);
					os.writeC(5);
					break;
				}
			} else if (getEnchantLevel() != 0
					&& !(itemType2 == 2 && getItem().getType() >= 8 && getItem().getType() <= 12)) {
				os.writeC(2);
				os.writeC(getEnchantLevel());
			}

			if (get_durability() != 0) {
				os.writeC(3);
				os.writeC(get_durability());
			}
			if (getItem().isTwohandedWeapon()) {
				os.writeC(4);
			}

			if (getItem().getItemId() == 21269) {
				os.writeC(0x28);
				os.writeC(3);
			}

			// 공격 성공
			if (getItemId() == 525115) { // 스냅퍼 용사 반지
				switch (getEnchantLevel()) {
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(5);
					os.writeC(getEnchantLevel() - 4);
					break;
				}
			} else if (getItemId() == 625115) { // 스냅퍼 용사 반지
				switch (getEnchantLevel()) {
				case 4:
				case 5:
				case 6:
				case 7:
					os.writeC(5);
					os.writeC(getEnchantLevel() - 4);
					break;
				case 8:
					os.writeC(5);
					os.writeC(5);
					break;
				}
			} else if (getItem().getHitModifier() != 0) {
				if (getItem().getType2() == 1 && (getItem().getType1() == 20 || getItem().getType1() == 62)) {
					os.writeC(24);
					os.writeC(getItem().getHitModifier());
				} else {
					os.writeC(5);
					os.writeC(getItem().getHitModifier());
				}
			}
			// 추타
			if (getItem().getType2() == 1 && getItem().getType() != 7 && getItem().getType() != 17
					&& getStepEnchantLevel() != 0) {// 검추타주문서이용한...시발
				os.writeC(6);
				os.writeC(getItem().getDmgModifier() + (getStepEnchantLevel() * 2));
			} else if (getItem().getGrade() != 3 && itemType2 == 2
					&& (getItem().getType() == 9 || getItem().getType() == 11)) { // 반지~
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(6);
					os.writeC(getItem().getDmgModifier() + 1);
					break;
				case 6:
					os.writeC(6);
					os.writeC(getItem().getDmgModifier() + 2);
					break;
				case 7:
					os.writeC(6);
					os.writeC(getItem().getDmgModifier() + 3);
					break;
				case 8:
					os.writeC(6);
					os.writeC(getItem().getDmgModifier() + 4);
					break;
				default:
					break;
				}
			} else if (getItemId() == 500010 || getItemId() == 502010) { // 룸티스검은
				if (getEnchantLevel() >= 3) {
					os.writeC(6);
					int dm = getEnchantLevel() - 2;
					if (getBless() != 0 && getEnchantLevel() >= 4)
						dm -= 1;
					os.writeC(getItem().getDmgModifier() + dm);
				}
			} else if ((getItemId() >= 525109 && getItemId() <= 525115) && getItemId() != 525114) { // 스냅퍼
																									// 용사
																									// 반지
				switch (getEnchantLevel()) {
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(6);
					os.writeC(getEnchantLevel() - 4);
					break;
				}
			} else if ((getItemId() >= 625109 && getItemId() <= 625115) && getItemId() != 625114) { // 스냅퍼
																									// 용사
																									// 반지
				switch (getEnchantLevel()) {
				case 4:
				case 5:
				case 6:
				case 7:
					os.writeC(6);
					os.writeC(getEnchantLevel() - 3);
					break;
				case 8:
					os.writeC(6);
					os.writeC(5);
					break;
				}

			} else if (getItem().getGrade() == 3) { // 순백 반지
				if ((getItem().getItemId() >= 425109 && getItem().getItemId() <= 425113)
						// ||(getItem().getItemId() >=
						// 525109&&getItem().getItemId() <= 525113)
						// ||(getItem().getItemId() >=
						// 625109&&getItem().getItemId() <= 625113)
						|| (getItemId() >= 21247 && getItemId() <= 21249)
						|| (getItemId() >= 21270 && getItemId() <= 21272)) {
					switch (getEnchantLevel()) {
					case 5:
						os.writeC(6);
						os.writeC(getItem().getDmgModifier() + 1);
						break;
					case 6:
						os.writeC(6);
						os.writeC(getItem().getDmgModifier() + 2);
						break;
					case 7:
						os.writeC(6);
						os.writeC(getItem().getDmgModifier() + 3);
						break;
					case 8:
						os.writeC(6);
						os.writeC(getItem().getDmgModifier() + 4);
						break;
					default:
						break;
					}
				} else if ((getItemId() >= 21251 && getItemId() <= 21253)
						|| (getItemId() >= 21273 && getItemId() <= 21275)) {
					switch (getEnchantLevel()) {
					case 4:
						os.writeC(6);
						os.writeC(getItem().getDmgModifier() + 1);
						break;
					case 5:
						os.writeC(6);
						os.writeC(getItem().getDmgModifier() + 2);
						break;
					case 6:
						os.writeC(6);
						os.writeC(getItem().getDmgModifier() + 3);
						break;
					case 7:
						os.writeC(6);
						os.writeC(getItem().getDmgModifier() + 4);
						break;
					case 8:
						os.writeC(6);
						os.writeC(getItem().getDmgModifier() + 5);
						break;
					default:
						break;
					}
				}

			} else if (getItem().getDmgModifier() != 0) {
				os.writeC(6);
				os.writeC(getItem().getDmgModifier());
			}

			if (getItemId() == 21249 || getItemId() == 21253) { // 스냅퍼 용사 반지
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
					break;
				case 4:
					if (getItemId() == 21253) { // 여기처럼
						os.writeC(5);
						os.writeC(1);
					}
					break;
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(5);
					os.writeC((getEnchantLevel() - 4) + (getItemId() == 21253 ? 1 : 0));
					break;
				}

			} else if (getItemId() == 130220 && getEnchantLevel() >= 5) { // 격분의
																			// 장갑
				os.writeC(5);
				os.writeC(getEnchantLevel() - 4);
			} else if (getItem().getHitup() != 0) {
				os.writeC(5);
				os.writeC(getItem().getHitup());
			}

			if (getItem().getDmgup() != 0) {
				os.writeC(6);
				os.writeC(getItem().getDmgup());
			}

			if (getItemId() == 21249) { // 스냅퍼 용사 반지
				switch (getEnchantLevel()) {
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(24);
					os.writeC(getEnchantLevel() - 4);
					break;
				}
			} else if (getItemId() == 21253) { // 스냅퍼 용사 반지
				switch (getEnchantLevel()) {
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(24);
					os.writeC(getEnchantLevel() - 3);
					break;
				}
			} else if (getItem().getBowHitup() != 0) {
				os.writeC(24);
				os.writeC(getItem().getBowHitup());
			}

			// 추타111
			if (getItem().getType1() == 20 && getItem().getType1() == 62 && getStepEnchantLevel() != 0) {// 검추타주문서이용한...시발
				os.writeC(35);
				os.writeC(getItem().getBowDmgup() + (getStepEnchantLevel() * 2));
			} else if (getItem().getGrade() != 3 && itemType2 == 2
					&& (getItem().getType() == 9 || getItem().getType() == 11)) { // 반지~
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 1);
					break;
				case 6:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 2);
					break;
				case 7:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 3);
					break;
				case 8:
					os.writeC(35);
					os.writeC(getItem().getBowDmgup() + 4);
					break;
				default:
					break;
				}
			} else if (getItemId() == 500010 || getItemId() == 502010) { // 룸티스검은
				if (getEnchantLevel() >= 3) {
					os.writeC(35);
					int dm = getEnchantLevel() - 2;
					if (getBless() != 0 && getEnchantLevel() >= 4)
						dm -= 1;
					os.writeC(dm);
				}
			} else if ((getItemId() >= 21247 && getItemId() <= 21249)
					|| (getItemId() >= 21270 && getItemId() <= 21272)) { // 스냅퍼
																			// 용사
																			// 반지
				switch (getEnchantLevel()) {
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(35);
					os.writeC(getEnchantLevel() - 4);
					break;
				}
			} else if ((getItemId() >= 21251 && getItemId() <= 21253)
					|| (getItemId() >= 21273 && getItemId() <= 21275)) { // 스냅퍼
																			// 용사
																			// 반지
				switch (getEnchantLevel()) {
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
					os.writeC(35);
					os.writeC(getEnchantLevel() - 3);
					break;
				}
			} else if (getItem().getGrade() == 3) { // 순백 반지
				if ((getItem().getItemId() >= 425109 && getItem().getItemId() <= 425113)
				// ||(getItem().getItemId() >= 525109&&getItem().getItemId() <=
				// 525113)
				// ||(getItem().getItemId() >= 625109&&getItem().getItemId() <=
				// 625113)
				) {
					switch (getEnchantLevel()) {
					case 5:
						os.writeC(35);
						os.writeC(getItem().getBowDmgup() + 1);
						break;
					case 6:
						os.writeC(35);
						os.writeC(getItem().getBowDmgup() + 2);
						break;
					case 7:
						os.writeC(35);
						os.writeC(getItem().getBowDmgup() + 3);
						break;
					case 8:
						os.writeC(35);
						os.writeC(getItem().getBowDmgup() + 4);
						break;
					default:
						break;
					}
				}
			} else if (getItem().getBowDmgup() != 0) {
				os.writeC(35);
				os.writeC(getItem().getBowDmgup());
			}

			if (itemId == 126 || itemId == 127 || itemId == 450012 || itemId == 100035 || itemId == 412002
					|| itemId == 450011 || itemId == 450023 || itemId == 450025 || itemId == 450013
					|| itemId == 413103) {
				os.writeC(16);
			}
			if (itemId == 412001 || itemId == 450009 || itemId == 450008 || itemId == 450010 || itemId == 262
					|| itemId == 12 || itemId == 30080 || itemId == 31080 || itemId == 1412001 || itemId == 100032
					|| itemId == 450032 || itemId == 30088 || itemId == 222202 || itemId == 222205) {
				os.writeC(34);
			}

			int bit = 0;
			bit |= getItem().isUseRoyal() ? 1 : 0;
			bit |= getItem().isUseKnight() ? 2 : 0;
			bit |= getItem().isUseElf() ? 4 : 0;
			bit |= getItem().isUseMage() ? 8 : 0;
			bit |= getItem().isUseDarkelf() ? 16 : 0;
			bit |= getItem().isUseDragonKnight() ? 32 : 0;
			bit |= getItem().isUseBlackwizard() ? 64 : 0;
			bit |= getItem().isUseWarrior() ? 128 : 0;
			// bit |= getItem().isUseHighPet() ? 128 : 0;
			if (itemType2 != 2 || getItem().getType() != 12 || bit != 127) {
				os.writeC(7);
				os.writeC(bit);
			}

			// STR~CHA
			if (getItem().get_addstr() != 0) {
				os.writeC(8);
				os.writeC(getItem().get_addstr());
			}
			if (getItem().get_adddex() != 0) {
				os.writeC(9);
				os.writeC(getItem().get_adddex());
			}
			if (getItem().get_addcon() != 0) {
				os.writeC(10);
				os.writeC(getItem().get_addcon());
			}
			if (getItem().get_addwis() != 0) {
				os.writeC(11);
				os.writeC(getItem().get_addwis());
			}
			if (getStepEnchantLevel() != 0 && getItem().getType2() == 1 && getItem().getType() == 17) {
				os.writeC(12);
				os.writeC(getItem().get_addint() + getStepEnchantLevel());
			} else if (getItem().get_addint() != 0) {
				os.writeC(12);
				os.writeC(getItem().get_addint());
			}
			if (getItem().get_addcha() != 0) {
				os.writeC(13);
				os.writeC(getItem().get_addcha());
			}

			if (itemId == 189 || itemId == 30220 || itemId == 293 || itemId == 413105) { // 관통
				os.writeC(39);
				os.writeS("관통 효과");

			}

			if (itemId == 427205) { // 관통
				os.writeC(39);
				os.writeS("마법적중 +2");

			}

			if (itemId == 30083 || itemId == 31083 || itemId == 222208) { // 공포
																			// 적중
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(39);
					os.writeS("공포 적중+5");
					break;
				case 1:
					os.writeC(39);
					os.writeS("공포 적중+6");
					break;
				case 2:
					os.writeC(39);
					os.writeS("공포 적중+7");
					break;
				case 3:
					os.writeC(39);
					os.writeS("공포 적중+8");
					break;
				case 4:
					os.writeC(39);
					os.writeS("공포 적중+9");
					break;
				case 5:
					os.writeC(39);
					os.writeS("공포 적중+10");
					break;
				case 6:
					os.writeC(39);
					os.writeS("공포 적중+10");
					break;
				case 7:
					os.writeC(39);
					os.writeS("공포 적중+10");
					break;
				case 8:
					os.writeC(39);
					os.writeS("공포 적중+10");
					break;
				case 9:
					os.writeC(39);
					os.writeS("공포 적중+10");
					break;
				case 10:
					os.writeC(39);
					os.writeS("공포 적중+10");
					break;

				}
			}
			if (itemId == 7227) { // 공포 적중
				switch (getEnchantLevel()) {
				case 8:
					os.writeC(39);
					os.writeS("공포 적중+1");
					break;
				case 9:
					os.writeC(39);
					os.writeS("공포 적중+2");
					break;
				case 10:
					os.writeC(39);
					os.writeS("공포 적중+3");
					break;

				}
			}

			if (itemId == 501214) { // 유니콘 각반
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(39);
					os.writeS("최대 HP +10");
					break;
				case 1:
					os.writeC(39);
					os.writeS("최대 HP +15");
					break;
				case 2:
					os.writeC(39);
					os.writeS("최대 HP +20");
					break;
				case 3:
					os.writeC(39);
					os.writeS("최대 HP +25.");
					break;
				case 4:
					os.writeC(39);
					os.writeS("최대 HP +30");
					break;
				case 5:
					os.writeC(39);
					os.writeS("최대 HP +35");
					break;
				case 6:
					os.writeC(39);
					os.writeS("최대 HP +40");
					break;
				case 7:
					os.writeC(39);
					os.writeS("최대 HP +45");
					break;
				case 8:
					os.writeC(39);
					os.writeS("최대 HP +50");
					break;
				case 9:
					os.writeC(39);
					os.writeS("최대 HP +55");
					break;
				case 10:
					os.writeC(39);
					os.writeS("최대 HP +60");
					break;

				}
			}

			if (itemId == 501211) { // 유니콘 각반
				switch (getEnchantLevel()) {
				case 9:
					os.writeC(39);
					os.writeS("SP +1");
					break;
				case 10:
					os.writeC(39);
					os.writeS("SP +2");
					break;

				}
			}
			if (itemId == 501212) { // 유니콘 각반
				switch (getEnchantLevel()) {
				case 9:
					os.writeC(39);
					os.writeS("근거리 대미지 +1");
					break;
				case 10:
					os.writeC(39);
					os.writeS("근거리 대미지 +2");
					break;

				}
			}
			if (itemId == 501213) { // 유니콘 각반
				switch (getEnchantLevel()) {
				case 9:
					os.writeC(39);
					os.writeS("원거리 대미지 +1");
					break;
				case 10:
					os.writeC(39);
					os.writeS("원거리 대미지 +2");
					break;

				}
			}

			if (itemId == 420000) { // 고대 명궁의 가더
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(39);
					os.writeS("원거리 대미지 +1");
					break;
				case 1:
					os.writeC(39);
					os.writeS("원거리 대미지 +1");
					break;
				case 2:
					os.writeC(39);
					os.writeS("원거리 대미지 +1");
					break;
				case 3:
					os.writeC(39);
					os.writeS("원거리 대미지 +1");
					break;
				case 4:
					os.writeC(39);
					os.writeS("원거리 대미지 +1");
					break;
				case 5:
					os.writeC(39);
					os.writeS("원거리 대미지 +2");
					break;
				case 6:
					os.writeC(39);
					os.writeS("원거리 대미지 +2");
					break;
				case 7:
					os.writeC(39);
					os.writeS("원거리 대미지 +3");
					break;
				case 8:
					os.writeC(39);
					os.writeS("원거리 대미지 +3");
					break;
				case 9:
					os.writeC(39);
					os.writeS("원거리 대미지 +4");
					break;
				case 10:
					os.writeC(39);
					os.writeS("원거리 대미지 +4");
					break;
				}
			}

			if (itemId == 420003) { // 고대 투사의 가더
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(39);
					os.writeS("근거리 대미지 +1");
					break;
				case 1:
					os.writeC(39);
					os.writeS("근거리 대미지 +1");
					break;
				case 2:
					os.writeC(39);
					os.writeS("근거리 대미지 +1");
					break;
				case 3:
					os.writeC(39);
					os.writeS("근거리 대미지 +1");
					break;
				case 4:
					os.writeC(39);
					os.writeS("근거리 대미지 +1");
					break;
				case 5:
					os.writeC(39);
					os.writeS("근거리 대미지 +2");
					break;
				case 6:
					os.writeC(39);
					os.writeS("근거리 대미지 +2");
					break;
				case 7:
					os.writeC(39);
					os.writeS("근거리 대미지 +3");
					break;
				case 8:
					os.writeC(39);
					os.writeS("근거리 대미지 +3");
					break;
				case 9:
					os.writeC(39);
					os.writeS("근거리 대미지 +4");
					break;
				case 10:
					os.writeC(39);
					os.writeS("근거리 대미지 +4");
					break;
				}
			}

			if (itemId == 61 || itemId == 30085 || itemId == 222201) { // 스턴 레벨
																		// 상승
				os.writeC(39);
				os.writeS("스턴 적중+10");

			}
			/*
			 * if(itemId == 20010 || itemId == 20100 || itemId == 20166 ||
			 * itemId == 20198) { //데스셋 os.writeC(39); os.writeS(
			 * "세트 착용 보너스:AC-10,STR+2,근거리 대미지+2,변신 :검은데스나이트");
			 * 
			 * }
			 * 
			 * if(itemId == 20041 || itemId == 20150 || itemId == 20184 ||
			 * itemId == 20214) { //커츠셋 os.writeC(39); os.writeS(
			 * "세트 착용 보너스:AC-10,CON+2,최대 HP+100,대미지 감소+2,변신 : 커츠");
			 * 
			 * }
			 */
			if (itemId == 59 || itemId == 100059) { // 스턴 레벨 상승
				os.writeC(39);
				os.writeS("스턴 적중+5");

			}
			if (itemId == 100033) { // 스턴 레벨 상승
				os.writeC(39);
				os.writeS("근거리 치명타 +10%");

			}

			if (itemId == 30082) { // 가이아표기부분
				os.writeC(39);
				os.writeS("관통 효과");
				os.writeC(39);
				os.writeS("대미지 감소+2");
				os.writeC(39);
				os.writeS("대미지 리덕션 무시+12");
			}

			if (itemId == 31082) { // 가이아표기부분
				os.writeC(39);
				os.writeS("관통 효과");
				os.writeC(39);
				os.writeS("대미지 감소+2");
				os.writeC(39);
				os.writeS("대미지 리덕션 무시+12");
			}

			if (itemId == 222206) { // 가이아표기부분
				os.writeC(39);
				os.writeS("관통 효과");
				os.writeC(39);
				os.writeS("대미지 감소+2");
				os.writeC(39);
				os.writeS("대미지 리덕션 무시+12");
			}
			/** 룸티스의 푸른빛 귀걸이 물약효율 표시 **/
			if (itemType2 == 2 && itemId == 500008 && getEnchantLevel() >= 0) {

				os.writeC(39);
				os.writeS(RoomtisHealingPotion11());
			}

			/** 축복받은 룸티스의 푸른빛 귀걸이 물약효율 표시 **/
			if (itemType2 == 2 && itemId == 502008 && getEnchantLevel() >= 0) {

				os.writeC(39);
				os.writeS(RoomtisHealingPotion12());
			}
			/** 룸티스 물약회복표시 **/
			/*
			 * if (itemId == 500008 && getEnchantLevel() >= 0){ os.writeC(39);
			 * os.writeS(RootisHealingPotion()); }
			 */
			// exp 공포상쇄 패킷번호추출전이라 일단 ..
			if (itemId == 490020) {
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
					os.writeC(39);
					os.writeS("EXP+ " + (getEnchantLevel() + 1) + "%");
					break;
				case 7:
					os.writeC(39);
					os.writeS("EXP+ 9%");
					break;
				case 8:
					os.writeC(39);
					os.writeS("EXP+ 11%");
					break;
				case 9:
					os.writeC(39);
					os.writeS("EXP+ 13%");
					break;
				case 10:
					os.writeC(39);
					os.writeS("EXP+ 15%");
					break;
				}
			} else if (itemId == 490021 || itemId == 490022) { // 회복문장
				int c = (getEnchantLevel() < 1 ? 2 : (getEnchantLevel() + 1) * 2);
				os.writeC(39);
				os.writeS("공포 회복감소 효과 상쇄 +" + c + "%");
				os.writeC(39);
				os.writeS("물약 회복량 " + c + "% +" + c);
			}
			/** 일반 목걸이 귀걸이 공표 회복 감소  물약효율표시 **/
		if (itemId == 21027 || itemId == 21258 || itemId == 21260
				 || itemId == 222346 || itemId == 222347 || itemId == 222348
				 || itemId == 222349) {
			os.writeC(39);
			os.writeS(RoomtisHealingPotion14());
		//	os.writeC(39);
		//	os.writeS(RoomtisHealingPotion15());
			
		}
			if (getItem().getGrade() != 3 && getItem().getType2() == 2
					&& (getItem().getType() == 8 || getItem().getType() == 12) && getEnchantLevel() > 0) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(65);
					os.writeC(2);
					os.writeC(2);
					break;
				case 6:
					os.writeC(65);
					os.writeC(4);
					os.writeC(4);
					break;
				case 7:
					os.writeC(65);
					os.writeC(6);
					os.writeC(6);
					break;
				case 8:
					os.writeC(65);
					os.writeC(8);
					os.writeC(8);
					break;
				case 9:
					os.writeC(65);
					os.writeC(9);
					os.writeC(9);
					break;
				default:
					break;
				}
			} else if (itemId == 500008) {
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(65);
					os.writeC(2);
					os.writeC(2);
					break;
				case 1:
					os.writeC(65);
					os.writeC(6);
					os.writeC(6);
					break;
				case 2:
					os.writeC(65);
					os.writeC(8);
					os.writeC(8);
					break;
				case 3:
					os.writeC(65);
					os.writeC(10);
					os.writeC(10);
					break;
				case 4:
					os.writeC(65);
					os.writeC(12);
					os.writeC(12);
					break;
				case 5:
					os.writeC(65);
					os.writeC(14);
					os.writeC(14);
					break;
				case 6:
					os.writeC(65);
					os.writeC(16);
					os.writeC(16);
					break;
				case 7:
					os.writeC(65);
					os.writeC(18);
					os.writeC(18);
					break;
				case 8:
					os.writeC(65);
					os.writeC(20);
					os.writeC(20);
					break;
				default:
					break;
				}
			} else if (itemId == 502008) {
				switch (getEnchantLevel()) {
				case 3:
					os.writeC(65);
					os.writeC(12);
					os.writeC(12);
					break;
				case 4:
					os.writeC(65);
					os.writeC(14);
					os.writeC(14);
					break;
				case 5:
					os.writeC(65);
					os.writeC(16);
					os.writeC(16);
					break;
				case 6:
					os.writeC(65);
					os.writeC(18);
					os.writeC(18);
					break;
				case 7:
					os.writeC(65);
					os.writeC(20);
					os.writeC(20);
					break;
				case 8:
					os.writeC(65);
					os.writeC(22);
					os.writeC(22);
					break;
				default:
					break;
				}
			}

			/** 룸티스 물약회복표시 **/

			// 피틱엠틱 이자리에서 아래로 이동

			/** 룸티스 데미지감소표시 **/
			if (itemId == 500007 && getEnchantLevel() >= 3) {
				os.writeC(63);
				os.writeC(붉귀리덕());
			}

			if (itemId == 502007 && getEnchantLevel() >= 3) {
				os.writeC(63);
				os.writeC(축붉귀리덕());
			}

			if (getItem().get_regist_freeze() != 0) {
				os.writeC(33);
				os.writeC(1);
				os.writeC(getItem().get_regist_freeze());
			}
			if (getItem().get_regist_stone() != 0) {
				os.writeC(33);
				os.writeC(2);
				os.writeC(getItem().get_regist_stone());
			}
			if (getItem().get_regist_sleep() != 0) {
				os.writeC(33);
				os.writeC(3);
				os.writeC(getItem().get_regist_sleep());
			}
			if (getItem().get_regist_blind() != 0) {
				os.writeC(33);
				os.writeC(4);
				os.writeC(getItem().get_regist_blind());
			}

			//
			if ((getItem().get_regist_horror() != 0) && (getMr() == 0)) {
				os.writeC(39);
				os.writeS(new StringBuilder().append("공포 내성 +").append(getItem().get_regist_horror()).toString());
			}

			if (((getItem().getItemId() >= 21246 && getItem().getItemId() <= 21253)
					|| (getItem().getItemId() >= 21270 && getItem().getItemId() <= 21275)) && getEnchantLevel() > 5) {
				switch (getEnchantLevel()) {
				case 6:
					os.writeC(33);
					os.writeC(5);
					os.writeC(5);
					break;
				case 7:
					os.writeC(33);
					os.writeC(5);
					os.writeC(7);
					break;
				case 8:
					os.writeC(33);
					os.writeC(5);
					os.writeC(9);
					break;
				default:
					break;
				}

			} else if (getItem().get_regist_stun() != 0) {
				os.writeC(33);
				os.writeC(5);
				os.writeC(getItem().get_regist_stun());
			}
			if (getItem().get_regist_sustain() != 0) {
				os.writeC(33);
				os.writeC(6);
				os.writeC(getItem().get_regist_sustain());
			} else

			if (itemId == 90101) {// 라이아반지 공포내성
				switch (getEnchantLevel()) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:

					os.writeC(39);
					os.writeS("언데드 추가 대미지");

					break;
				}
			}

			if (itemId == 900019) {// 실프 티 스턴내성
				switch (getEnchantLevel()) {
				case 0:
					os.writeC(39);
					os.writeS("스턴 내성 +7");
					break;
				case 1:
					os.writeC(39);
					os.writeS("스턴 내성 +8");
					break;
				case 2:
					os.writeC(39);
					os.writeS("스턴 내성 +9");
					break;
				case 3:
					os.writeC(39);
					os.writeS("스턴 내성 +10");
					break;
				case 4:
					os.writeC(39);
					os.writeS("스턴 내성 +11");
					break;
				case 5:
					os.writeC(39);
					os.writeS("스턴 내성 +12");
					break;
				case 6:
					os.writeC(39);
					os.writeS("스턴 내성 +13");
					break;
				case 7:
					os.writeC(39);
					os.writeS("스턴 내성 +14");
					break;
				case 8:
					os.writeC(39);
					os.writeS("스턴 내성 +15");
					break;
				case 9:
					os.writeC(39);
					os.writeS("스턴 내성 +16");
					break;
				case 10:
					os.writeC(39);
					os.writeS("스턴 내성 +17");

					break;
				}
			}

			if (itemId == 200851) {// 지룡의 티셔츠
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(39);
					os.writeS("MR +4");
					break;
				case 6:
					os.writeC(39);
					os.writeS("MR +5");
					break;
				case 7:
					os.writeC(39);
					os.writeS("MR +6");
					break;
				case 8:
					os.writeC(39);
					os.writeS("MR +8");
					break;
				case 9:
					os.writeC(39);
					os.writeS("MR +11");
					break;
				case 10:
					os.writeC(39);
					os.writeS("MR +14");

					break;
				}
			}

			if (itemId == 200852) {// 화룡의 티셔츠
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(39);
					os.writeS("스턴 내성 +8");
					break;
				case 6:
					os.writeC(39);
					os.writeS("스턴 내성 +9");
					break;
				case 7:
					os.writeC(39);
					os.writeS("스턴 내성 +10");
					break;
				case 8:
					os.writeC(39);
					os.writeS("스턴 내성 +12");
					break;
				case 9:
					os.writeC(39);
					os.writeS("스턴 내성 +15");
					os.writeC(39);
					os.writeS("근거리 추가 대미지+1");
					break;
				case 10:
					os.writeC(39);
					os.writeS("스턴 내성 +18");
					os.writeC(39);
					os.writeS("근거리 추가 대미지+2");

					break;
				}
			}
			if (itemId == 200853) {// 풍룡의 티셔츠
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(39);
					os.writeS("스턴 내성 +8");
					break;
				case 6:
					os.writeC(39);
					os.writeS("스턴 내성 +9");
					break;
				case 7:
					os.writeC(39);
					os.writeS("스턴 내성 +10");
					break;
				case 8:
					os.writeC(39);
					os.writeS("스턴 내성 +12");
					break;
				case 9:
					os.writeC(39);
					os.writeS("스턴 내성 +15");
					os.writeC(39);
					os.writeS("원거리 추가 대미지+1");
					break;
				case 10:
					os.writeC(39);
					os.writeS("스턴 내성 +18");
					os.writeC(39);
					os.writeS("원거리 추가 대미지+2");

					break;
				}
			}
			if (itemId == 200854) {// 수룡의 티셔츠
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(39);
					os.writeS("스턴 내성 +8");
					break;
				case 6:
					os.writeC(39);
					os.writeS("스턴 내성 +9");
					break;
				case 7:
					os.writeC(39);
					os.writeS("스턴 내성 +10");
					break;
				case 8:
					os.writeC(39);
					os.writeS("스턴 내성 +12");
					break;
				case 9:
					os.writeC(39);
					os.writeS("스턴 내성 +15");
					os.writeC(39);
					os.writeS("추가 Sp+1");
					break;
				case 10:
					os.writeC(39);
					os.writeS("스턴 내성 +18");
					os.writeC(39);
					os.writeS("추가 Sp+2");

					break;
				}
			}

			/** 룸티스의 푸른빛 귀걸이 **/
			// 무기에 표시부분

			if (itemId == 21097) {// 마법사의 가더
				switch (getEnchantLevel()) {
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(1);
					break;
				case 7:
				case 8:
					os.writeC(17);
					os.writeC(2);
					break;
				default:
					if (getEnchantLevel() >= 9) {
						os.writeC(17);
						os.writeC(3);
					}
					break;
				}
			} else if (getsp() != 0) {
				os.writeC(17);
				os.writeC(getsp());
			}
			// 스펠파워 지팡이

			// 디락.장신구 업 포함 SP
			// 추타111

			if (getItem().getType2() == 1 && (getItem().getType() == 7 || getItem().getType1() == 17)
					&& getStepEnchantLevel() == 1) {// 검추타주문서이용한...시발
				os.writeC(39);
				os.writeS("추가 SP +1");
			} else if (getItem().getType2() == 1 && (getItem().getType() == 7 || getItem().getType1() == 17)
					&& getStepEnchantLevel() == 2) {// 검추타주문서이용한...시발
				os.writeC(39);
				os.writeS("추가 SP +2");
			} else if (getItem().getType2() == 1 && (getItem().getType() == 7 || getItem().getType1() == 17)
					&& getStepEnchantLevel() == 3) {// 검추타주문서이용한...시발
				os.writeC(39);
				os.writeS("추가 SP +3");
			} else

			if (getItemId() == 21246) { // 스냅퍼 지혜 반지
				switch (getEnchantLevel()) {
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(getEnchantLevel() - 4);
					break;
				case 7:
				case 8:
					os.writeC(17);
					os.writeC(getEnchantLevel() - 5);
					break;
				default:
					break;
				}
			} else

			if (getItemId() == 21250) { // 스냅퍼 지혜 반지
				switch (getEnchantLevel()) {
				case 4:
					os.writeC(17);
					os.writeC(1);
					break;
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(2);
					break;
				case 7:
				case 8:
					os.writeC(17);
					os.writeC(getEnchantLevel() - 4);
					break;
				default:
					break;
				}
				/*
				 * }else if (getItem().getGrade() == 2) {//강도 하
				 * if(getEnchantLevel() < 6){ if (getItem().get_addsp() != 0) {
				 * os.writeC(17); os.writeC(getItem().get_addsp()); } }else
				 * if(getEnchantLevel() >= 6){ os.writeC(17);
				 * os.writeC(getItem().get_addsp() + (getEnchantLevel()-5)); }
				 */
			} else if (itemId == 500009) { // 룸티스 보랏빛
				switch (getEnchantLevel()) {
				case 3:
				case 4:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 1);
					break;
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 2);
					break;
				case 7:
				case 8:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					break;
				}
			} else if (itemId == 502009) { // 룸티스 보랏빛
				switch (getEnchantLevel()) {
				case 3:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 1);
					break;
				case 4:
				case 5:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 2);
					break;
				case 6:
				case 7:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 3);
					break;
				case 8:
					os.writeC(17);
					os.writeC(getItem().get_addsp() + 4);
					break;
				}
			} else if (itemId == 525114) { // 룸티스 보랏빛
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(17);
					os.writeC(1);
					break;
				case 6:
				case 7:
					os.writeC(17);
					os.writeC(2);
					break;
				case 8:
					os.writeC(17);
					os.writeC(3);
					break;
				}
			} else if (itemId == 625114) { // 룸티스 보랏빛
				switch (getEnchantLevel()) {
				case 4:
					os.writeC(17);
					os.writeC(1);
					break;
				case 5:
				case 6:
					os.writeC(17);
					os.writeC(2);
					break;
				case 7:
					os.writeC(17);
					os.writeC(3);
					break;
				case 8:
					os.writeC(17);
					os.writeC(4);
					break;
				}
			}
			/*
			 * else if (getItem().get_addsp() != 0) { os.writeC(17);
			 * os.writeC(getItem().get_addsp()); }
			 */

			if (getItem().isHasteItem()) {
				os.writeC(18);
			}

			// 클래스

			// ////////////////////////////////////////////////////////////////////////////////////////////////
			// 디락.장신구업 포함 HP
			if (itemId == 21095) { // 체력의 가더
				os.writeC(14);
				switch (getEnchantLevel()) {
				case 5:
				case 6:
					os.writeH(getItem().get_addhp() + 25);
					break;
				case 7:
				case 8:
					os.writeH(getItem().get_addhp() + 50);
					break;
				default:
					if (getEnchantLevel() >= 9) {
						os.writeH(getItem().get_addhp() + 75);
					} else {
						os.writeH(getItem().get_addhp());
					}
					break;
				}
			} else if (getItem().getGrade() != 3 && itemType2 == 2 && (getItem().getType() == 9
					|| getItem().getType() == 11 || getItem().getType() == 8 || getItem().getType() == 12)) { // 반지~
				switch (getEnchantLevel()) {
				case 0:
					if (getItem().get_addhp() != 0) {
						os.writeC(14);
						os.writeH(getItem().get_addhp());
					}
					break;
				case 1:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 5);
					break;
				case 2:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 10);
					break;
				case 3:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 20);
					break;
				case 4:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 30);
					break;
				case 5:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 40);
					break;
				case 6:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 40);
					break;
				case 7:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 50);
					break;
				case 8:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 50);
					break;
				case 9:
					os.writeC(14);
					os.writeH(getItem().get_addhp() + 60);
					break;
				default:
					break;
				}
			} else if (getItem().getGrade() == 3) {// 순백반지
				if (itemId == 500007) { // 룸티스 붉은빛
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;
					case 1:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 20);
						break;
					case 2:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 30);
						break;
					case 3:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 40);
						break;
					case 4:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 50);
						break;
					case 5:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 60);
						break;
					case 6:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 70);
						break;
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 80);
						break;
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 90);
						break;
					}
				} else if (itemId == 502007) { // 룸티스 붉은빛
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;
					case 3:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 50);
						break;
					case 4:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 60);
						break;
					case 5:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 70);
						break;
					case 6:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 80);
						break;
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 90);
						break;
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 140);
						break;
					}

				} else if ((getItem().getItemId() == 525109 || getItem().getItemId() == 525110
						|| getItem().getItemId() == 525111 || getItem().getItemId() == 525112
						|| getItem().getItemId() == 525113 || getItem().getItemId() == 625109
						|| getItem().getItemId() == 625110 || getItem().getItemId() == 625111
						|| getItem().getItemId() == 625112 || getItem().getItemId() == 625113)) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) + 10);
						break;
					case 6:
					case 7:
					case 8:
						if (getItem().getItemId() == 625111) {
							os.writeC(14);
							os.writeH((getItem().get_addhp() + (getEnchantLevel() * 5) + 10)
									+ ((getEnchantLevel() - 5) * 5));
							break;
						} else {
							os.writeC(14);
							os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) + 10);
							break;
						}
					}

				} else if (getItem().getItemId() == 525115) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) - 10);
						break;
					}
				} else if (getItem().getItemId() == 625115) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) - 5);
						break;
					case 8:
						os.writeC(14);
						os.writeH(30);
						break;
					}
				} else if (getItem().getItemId() == 525114) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5));
						break;
					}
				} else if (getItem().getItemId() == 625114) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 3:
						os.writeC(14);
						os.writeH(20);
						break;
					case 1:
					case 2:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5));
						break;
					case 4:
					case 5:
					case 6:
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) + 5);
						break;
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + (getEnchantLevel() * 5) + 10);
						break;
					}
				} else if ((getItem().getItemId() >= 425109 && getItem().getItemId() <= 425113)
						|| (getItem().getItemId() >= 525109 && getItem().getItemId() <= 525113)
						|| (getItem().getItemId() >= 625109 && getItem().getItemId() <= 625113) || getItemId() == 21247
						|| getItemId() == 21248 || getItemId() == 21251 || getItemId() == 21252) {
					switch (getEnchantLevel()) {
					case 0:
						if (getItem().get_addhp() != 0) {
							os.writeC(14);
							os.writeH(getItem().get_addhp());
						}
						break;//
					case 1:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 15);
						break;
					case 2:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 20);
						break;
					case 3:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 25);
						break;
					case 4:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 30);
						break;
					case 5:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 35);
						break;
					case 6:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 40 + (getItemId() == 21252 ? 5 : 0));
						break;
					case 7:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 45 + (getItemId() == 21252 ? 10 : 0));
						break;
					case 8:
						os.writeC(14);
						os.writeH(getItem().get_addhp() + 50 + (getItemId() == 21252 ? 15 : 0));
						break;
					}
				} else if (getItemId() == 21246 || getItemId() == 21250) { // 스냅퍼
																			// 지혜
					if (getEnchantLevel() > 0) {
						os.writeC(14);
						os.writeH(getEnchantLevel() * 5);
					}
				} else if (getItemId() == 21249 || getItemId() == 21253) { // 스냅퍼용사
					if (getEnchantLevel() >= 3) {
						os.writeC(14);
						os.writeH((getEnchantLevel() - 2) * 5);
					}
				}
				/**/
			} else if (getItem().get_addhp() != 0) {
				os.writeC(14);
				os.writeH(getItem().get_addhp());
			}
			// ////////////////////////////////////////////////////////////////////////////////////////////////

			// 피틱
			/*
			 * if (getItem().getGrade() == 0){// 강도 : 상
			 * if(getItem().get_addhpr() != 0){ os.writeC(37);
			 * os.writeC(getItem().get_addhpr()); }else{
			 * switch(getEnchantLevel()){ case 6: os.writeC(37);
			 * os.writeC(getItem().get_addhpr() + 1); break; case 7:
			 * os.writeC(37); os.writeC(getItem().get_addhpr() + 2); break; case
			 * 8: os.writeC(37); os.writeC(getItem().get_addhpr() + 3); break; }
			 * } } else
			 */if (getItem().get_addhpr() != 0) {
				os.writeC(37);
				os.writeC(getItem().get_addhpr());
			}

			// 디락.장신구업 포함 MP
			if (getItem().getGrade() != 3 && itemType2 == 2 && (getItem().getType() == 10)) { // 벨트
																								// ~
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 5);
					break;
				case 2:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 10);
					break;
				case 3:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 20);
					break;
				case 4:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 30);
					break;
				case 5:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 40);
					break;
				case 6:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 40);
					break;
				case 7:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 8:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				default:
					break;
				}
			} else if (getItem().getItemId() == 500009) {// 보라빛
				switch (getEnchantLevel()) {
				case 0:
					if (getItem().get_addmp() != 0) {
						os.writeC(32);
						os.writeH(getItem().get_addmp());
					}
					break;
				case 1:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 10);
					break;
				case 2:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 15);
					break;
				case 3:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 30);
					break;
				case 4:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 35);
					break;
				case 5:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 6:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 55);
					break;
				case 7:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 70);
					break;
				case 8:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 95);
					break;
				}
			} else if (getItem().getItemId() == 502009) {// 보라빛
				switch (getEnchantLevel()) {
				case 1:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 10);
					break;
				case 2:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 15);
					break;
				case 3:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 35);
					break;
				case 4:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 50);
					break;
				case 5:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 55);
					break;
				case 6:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 70);
					break;
				case 7:
					os.writeC(32);
					os.writeH(getItem().get_addmp() + 95);
					break;
				case 8:
					os.writeC(39);
					os.writeS("최대 MP +130");
					break;
				default:
					break;
				}
			} else if (getItemId() == 625114) {// 스냅퍼의
												// 지혜 축복
				os.writeC(32);
				if (getEnchantLevel() >= 7) {
					if (getEnchantLevel() == 7) {
						os.writeC(30);
					} else {
						os.writeC(35);
					}
				} else {
					os.writeC(getItem().get_addmp());
				}
			} else if (getItem().get_addmp() != 0) {
				os.writeC(32);
				if (getItemId() == 21166)
					os.writeH(getItem().get_addmp() + (getEnchantLevel() * 10));
				else
					os.writeH(getItem().get_addmp());
			}

			// 엠틱
			/*
			 * if (getItem().getGrade() == 0){//강도 : 상 if(getItem().get_addmpr()
			 * != 0){ os.writeC(38); os.writeC(getItem().get_addmpr()); }else{
			 * switch(getEnchantLevel()){ case 6: os.writeC(38);
			 * os.writeC(getItem().get_addmpr() + 1); break; case 7:
			 * os.writeC(38); os.writeC(getItem().get_addmpr() + 2); break; case
			 * 8: os.writeC(38); os.writeC(getItem().get_addmpr() + 3); break; }
			 * } } else
			 */if (getItem().get_addmpr() != 0) {
				os.writeC(38);
				int mprr = getItem().get_addmpr();
				if (getItem().getItemId() == 261)
					mprr += getEnchantLevel();
				os.writeC(mprr);
			}

			// 수정

			// PVP 데미지 리덕션
			if (getItemId() >= 21242 && getItemId() <= 21245) {
				os.writeC(60);
				os.writeC(getEnchantLevel() + 1);
			}
			/** 잊섬아이템리뉴얼 by.soju **/
			if (getItem().get_regist_calcPcDefense() != 0) {
				os.writeC(39);
				os.writeS(new StringBuilder().append("\\f2PVP 대미지 감소+ ").append(getItem().get_regist_calcPcDefense())
						.toString());
			}

			// MR
			/*
			 * if (getItem().getGrade() == 1){//강도: 중 if(getEnchantLevel() < 6){
			 * if (getMr() != 0) { os.writeC(15); os.writeH(getMr()); } }else
			 * if(getEnchantLevel()>=6){ os.writeC(15);
			 * os.writeH(getMr()+(getEnchantLevel()-5)); }
			 *//**
				 * } else if(getItem().getItemId() == 500009){//보라빛
				 * os.writeC(15); os.writeH(getMr());
				 **/
			/*
			 * } else
			 */if (getMr() != 0) {
				os.writeC(15);
				os.writeH(getMr());
			}

			// 디락.장신구 포함 속방표시
			if (getItem().get_defense_fire() != 0) {
				os.writeC(27);
				os.writeC(getItem().get_defense_fire());
			}
			if (getItem().get_defense_water() != 0) {
				os.writeC(28);
				os.writeC(getItem().get_defense_water());
			}
			if (getItem().get_defense_wind() != 0) {
				os.writeC(29);
				os.writeC(getItem().get_defense_wind());
			}
			if (getItem().get_defense_earth() != 0) {
				os.writeC(30);
				os.writeC(getItem().get_defense_earth());
			}

			/**
			 * getMr() 추가.디락. 마방과 중복표시 되지 않음 아이템 표시부분 오류 때문에
			 */

			if (getRegistLevel() != 0 && itemId >= 490000 && itemId <= 490017) {
				os.writeC(39);
				os.writeS(spirit());
			}

			if (getRegistLevel() == 10) {// 판도라 정령문양
				os.writeC(27);
				os.writeC(10);
				os.writeC(28);
				os.writeC(10);
				os.writeC(29);
				os.writeC(10);
				os.writeC(30);
				os.writeC(10);
			} else if (getRegistLevel() == 11) {// 판도라 마나문양
				os.writeC(32);
				os.writeC(30);
			} else if (getRegistLevel() == 12) {// 판도라 체력문양
				os.writeC(14);
				os.writeH(30);
			} else if (getRegistLevel() == 13) {// 판도라 멸마문양
				os.writeC(15);
				os.writeH(10);
			} else if (getRegistLevel() == 15) {// 판도라 회복문양
				os.writeC(37);
				os.writeC(1);
				os.writeC(38);
				os.writeC(1);
			} else if (getRegistLevel() == 16) {// 판도라 석화문양
				os.writeC(33);
				os.writeC(2);
				os.writeC(10);
			} else if (getRegistLevel() == 17) {// 판도라 홀드문양
				os.writeC(33);
				os.writeC(6);
				os.writeC(10);
			} else if (getRegistLevel() == 18) {// 판도라 스턴문양
				os.writeC(33);
				os.writeC(5);
				os.writeC(10);
			}
			/*
			 * 마법 명중 os.writeC(40); os.writeC(value);
			 */
			if (getItem().getMinLevel() > 0) {
				os.writeC(42);
				os.writeC(getItem().getMinLevel());
			}
			// 45/45 순결한 체력꽃향 요정족 티

			// PVP 추가 데미지

			if ((getItemId() >= 277 && getItemId() <= 283) || (getItemId() >= 90085 && getItemId() <= 90092)) {
				os.writeC(59);
				os.writeC(getEnchantLevel());

			}
			/** 잊섬아이템리뉴얼 by.soju **/
			if (getItem().get_regist_PVPweaponTotalDamage() != 0) {
				os.writeC(39);
				os.writeS(new StringBuilder().append("\\f2PVP 추가 대미지+ ")
						.append(getItem().get_regist_PVPweaponTotalDamage()).toString());
			}

			else

			if ((getItemId() >= 21246 && getItemId() <= 21253) || (getItemId() >= 21270 && getItemId() <= 21275)) {

				int dmg = 0;
				if (getEnchantLevel() == 7)
					dmg = 1;
				else if (getEnchantLevel() == 8)
					dmg = 2;
				if (dmg != 0) {
					os.writeC(59);
					os.writeC(dmg);
				}
			} else

			if ((getItemId() >= 90093 && getItemId() <= 90100)) {

				int dmg = 0;
				if (getEnchantLevel() == 7)
					dmg = 3;
				else if (getEnchantLevel() == 8)
					dmg = 5;
				else if (getEnchantLevel() == 9)
					dmg = 7;
				else if (getEnchantLevel() == 10)
					dmg = 10;
				if (dmg != 0) {
					os.writeC(59);
					os.writeC(dmg);
				}
			} else

			if ((getItemId() >= 110051 && getItemId() <= 110058)) // 썸타는 무기류
			{

				int dmg = 0;
				if (getEnchantLevel() == 7)
					dmg = 3;
				else if (getEnchantLevel() == 8)
					dmg = 5;
				else if (getEnchantLevel() == 9)
					dmg = 7;
				else if (getEnchantLevel() == 10)
					dmg = 10;
				if (dmg != 0) {
					os.writeC(59);
					os.writeC(dmg);
				}
			} else

			if ((getItemId() == 121216)
			/* || (getItemId() >= 625109 && getItemId() <= 625115) */) {
				os.writeC(59);
				os.writeC(1);
			} else if ((getItemId() == 221216)
			/* || (getItemId() >= 625109 && getItemId() <= 625115) */) {
				os.writeC(59);
				os.writeC(3);
			} else if (getItemId() >= 284 && getItemId() <= 290) {
				os.writeC(59);
				int dmg = 0;
				if (getEnchantLevel() == 7)
					dmg = 3;
				else if (getEnchantLevel() == 8)
					dmg = 5;
				else if (getEnchantLevel() == 9)
					dmg = 7;
				else if (getEnchantLevel() == 10)
					dmg = 10;
				os.writeC(dmg);
			} else if (getItemId() >= 900015 && getItemId() <= 900018) {
				os.writeC(59);
				int dmg = 0;
				if (getEnchantLevel() == 0)
					dmg = 2;
				else if (getEnchantLevel() == 1)
					dmg = 2;
				else if (getEnchantLevel() == 2)
					dmg = 2;
				else if (getEnchantLevel() == 3)
					dmg = 2;
				else if (getEnchantLevel() == 4)
					dmg = 2;
				else if (getEnchantLevel() == 5)
					dmg = 2;
				else if (getEnchantLevel() == 6)
					dmg = 2;
				else if (getEnchantLevel() == 7)
					dmg = 2;
				else if (getEnchantLevel() == 8)
					dmg = 2;
				else if (getEnchantLevel() == 9)
					dmg = 2;
				else if (getEnchantLevel() == 10)
					dmg = 2;

				os.writeC(dmg);
			} else

			/*
			 * if ((itemId >= 90085 && itemId <= 90092) || itemId == 160423 ||
			 * itemId == 435000 || itemId == 160510 || itemId == 160511 ||
			 * itemId == 21123) { os.writeC(61); os.writeD(3442346400L); } if
			 * (itemId == 21269 && getEnchantLevel() < 6) { os.writeC(61);
			 * os.writeD(3442346400L); }
			 * 
			 * if (itemId == 500206 || itemId == 500207 || itemId == 121216 ||
			 * itemId == 221216 || itemId == 500208) { os.writeC(61);
			 * os.writeD(3501426400L); }
			 */
			/*
			 * if (itemId >= 9075 && itemId <= 9093) { os.writeC(61); long dd =
			 * 3374546400L; Calendar cal = (Calendar)
			 * Calendar.getInstance().clone(); cal.setTimeInMillis(dd); //
			 * cal.add(Calendar.YEAR, -1997); // cal.add(Calendar.HOUR_OF_DAY,
			 * 7); // cal.add(Calendar.DAY_OF_MONTH, 7); /* long ddd =
			 * calendar.getTimeInMillis();
			 * 
			 * calendar = Calendar.getInstance();
			 * calendar.add(Calendar.DAY_OF_MONTH, 7);
			 */
			// long dddd = cal.getTimeInMillis()/1000/60/60;

			/*
			 * // 1970년1월1일 long time = (((System.currentTimeMillis()/1000)/60
			 * )/60);//(((((System.currentTimeMillis
			 * ()+851533200000)/1000)/60)/60) + (24*7)) + 236537; long temp =
			 * time / 128; if(temp > 0){ os.writeC(hextable[(int)time%128]);
			 * while (temp > 128) { os.writeC(hextable[(int)temp%128]); temp =
			 * temp / 128; } os.writeC((int)temp); }else{ if(time==0){
			 * os.writeC(0); }else{ os.writeC(hextable[(int)time]);
			 * os.writeC(0); } }
			 */
			// os.writeD(dddd*24*1000);
			// os.writeD(Config.test + 3374546400L);
			// }

			if (itemId == 21096) {// 수호의 가더
				if (수호의가더DamageDown() > 0) {
					os.writeC(63);

					os.writeC(수호의가더DamageDown());
				}
			} else if (getItem().getGrade() != 3 && itemType2 == 2 && (getItem().getType() == 10)) { // 벨트
																										// ~
				if (벨트데미지감소() > 0) {
					os.writeC(63);

					os.writeC(벨트데미지감소());
				}
			} else if (getItem().getDamageReduction() != 0) {
				int reduc = getItem().getDamageReduction();
				if (itemId >= 420100 && itemId <= 420103) {
					if (getEnchantLevel() >= 7) {
						reduc++;
					}
					if (getEnchantLevel() >= 8) {
						reduc++;
					}
					if (getEnchantLevel() >= 9) {
						reduc++;
					}
				}
				os.writeC(63);
				os.writeC(reduc);
			}

			if (itemId == 502007) {
				switch (getEnchantLevel()) {
				case 4:
					os.writeC(64);
					os.writeC(2);
					os.writeC(20);
					break;
				case 5:
					os.writeC(64);
					os.writeC(3);
					os.writeC(20);
					break;
				case 6:
					os.writeC(64);
					os.writeC(4);
					os.writeC(20);
					break;
				case 7:
					os.writeC(64);
					os.writeC(5);
					os.writeC(20);
					break;
				case 8:
					os.writeC(64);
					os.writeC(6);
					os.writeC(20);
					break;
				}
			} else if (itemId == 500007) {
				switch (getEnchantLevel()) {
				case 5:
					os.writeC(64);
					os.writeC(2);
					os.writeC(20);
					break;
				case 6:
					os.writeC(64);
					os.writeC(3);
					os.writeC(20);
					break;
				case 7:
					os.writeC(64);
					os.writeC(4);
					os.writeC(20);
					break;
				case 8:
					os.writeC(64);
					os.writeC(5);
					os.writeC(20);
					break;
				}
			}

			if (getItem().getWeightReduction() != 0) {
				// os.writeC(68);
				int reduc = getItem().getWeightReduction();
				if (itemId == 7246) {
					if (getEnchantLevel() > 5) {
						int en = getEnchantLevel() - 5;
						reduc += en * 60;
					}
				}

				os.writeC(0x5a);
				os.writeH(reduc);
			}

			if (getItem().getItemId() == 20298 // 제니스의반지
					|| getItem().getItemId() == 120298 // 축제니스반지
					|| getItem().getItemId() == 20117 // 바포메트의갑옷
					|| getItem().getItemId() == 420100 || getItem().getItemId() == 420101
					|| getItem().getItemId() == 420102 || getItem().getItemId() == 420103) {
				os.writeC(70);
				os.writeC(2);// 독내성
			}

			if (getItem().getMagicName() != null) {
				os.writeC(74);
				os.writeS(getItem().getMagicName());
			}

			if (getItemId() == 500010 || getItemId() == 502010) {
				int chance = 0;
				if (getBless() == 0 && getEnchantLevel() >= 4) {
					chance = 2 + getEnchantLevel() - 4;
				} else if (getEnchantLevel() >= 5) {
					chance = 2 + getEnchantLevel() - 5;
				}

				if (chance > 0) {
					os.writeC(67);
					os.writeH(0x5F3D);
					os.writeC(chance);
					os.writeC(20);
				}
			}
		}
		try {
			os.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
		return os.getBytes();
	}



	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c,
			0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e,
			0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0,
			0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2,
			0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4,
			0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6,
			0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
			0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff };

	private int 벨트데미지감소() {
		// TODO 자동 생성된 메소드 스텁
		int lvl = getEnchantLevel();
		int reduc = 0;
		switch (lvl) {
		case 5:
			reduc = getItem().getDamageReduction() + 1;
			break;
		case 6:
			reduc = getItem().getDamageReduction() + 2;
			break;
		case 7:
			reduc = getItem().getDamageReduction() + 3;
			break;
		case 8:
			reduc = getItem().getDamageReduction() + 4;
			break;
		default:
			if (lvl >= 9) {
				reduc = getItem().getDamageReduction() + 4;
			} else {
				reduc = getItem().getDamageReduction() + 0;
			}
			break;
		}
		return reduc;
	}

	private int 수호의가더DamageDown() {
		// TODO 자동 생성된 메소드 스텁
		int lvl = getEnchantLevel();
		int reduc = 0;
		switch (lvl) {
		case 5:
		case 6:
			reduc = 2;
			break;
		case 7:
		case 8:
			reduc = 3;
			break;
		default:
			if (lvl >= 9) {
				reduc = 4;
			} else {
				reduc = 1;
			}
			break;
		}
		return reduc;
	}

	private static final int _hit = 0x05;
	private static final int _dmg = 0x06;
	private static final int _bowhit = 0x18;
	private static final int _bowdmg = 0x23;
	private static final int _str = 0x08;
	private static final int _dex = 0x09;
	private static final int _con = 0x0a;
	private static final int _wis = 0x0b;
	private static final int _int = 0x0c;
	private static final int _cha = 0x0d;

	private static final int _mr = 0x0f;
	private static final int _sp = 0x11;

	private static final int _fire = 0x1B;
	private static final int _water = 0x1C;
	private static final int _wind = 0x1D;
	private static final int _earth = 0x1E;

	private static final int _maxhp = 0x0e;
	private static final int _maxmp = 0x20;
	private static final int _hpr = 0x25;
	private static final int _mpr = 0x26;
	private static final int _add_ac = 0x38;
	private static final int _poly = 0x47;

	public byte[] getStatusBytes(L1PcInstance pc, boolean check) {
		byte[] data = getStatusBytes();
		@SuppressWarnings("resource")
		BinaryOutputStream os = new BinaryOutputStream();
		try {
			os.write(data);

			os.writeC(0x45);

			if (check) {
				os.writeC(1);
			} else {
				os.writeC(2);
			}
			L1ArmorSets set = ArmorSetTable.getInstance().getArmorSets(getItem().getSetId());

			if (set.getAc() != 0) {
				os.writeC(_add_ac);
				os.writeC(set.getAc());
			}

			if (getItem().getItemId() == 20099) {
				os.writeC(_poly);
				os.writeH(1175);// 데몬
			} else if (getItem().getItemId() == 20100) {
				os.writeC(_poly);
				os.writeH(18692);// 진데스
			} else if (getItem().getItemId() == 20151) {
				os.writeC(_poly);
				os.writeH(2118);// 케레니스
			} else if (getItem().getItemId() == 20118) {
				os.writeC(_poly);
				os.writeH(2117);// 켄라우헬
			}

			if (set.getShortHitup() != 0) {
				os.writeC(_hit);
				os.writeC(set.getShortHitup());
			}
			if (set.getShortDmgup() != 0) {
				os.writeC(_dmg);
				os.writeC(set.getShortDmgup());
			}

			if (set.getLongHitup() != 0) {
				os.writeC(_bowhit);
				os.writeC(set.getLongHitup());
			}
			if (set.getLongDmgup() != 0) {
				os.writeC(_bowdmg);
				os.writeC(set.getLongDmgup());
			}

			if (set.getHpr() != 0) {
				os.writeC(_hpr);
				os.writeC(set.getHpr());
			}
			if (set.getMpr() != 0) {
				os.writeC(_mpr);
				os.writeC(set.getMpr());
			}

			if (set.getHp() != 0) {
				os.writeC(_maxhp);
				os.writeH(set.getHp());
			}
			if (set.getMp() != 0) {
				os.writeC(_maxmp);
				os.writeC(set.getMp());
			}

			if (set.getMr() != 0) {
				os.writeC(_mr);
				os.writeH(set.getMr());
			}

			if (set.getSp() != 0) {
				os.writeC(_sp);
				os.writeC(set.getSp());
			}

			if (set.getfire() != 0) {
				os.writeC(_fire);
				os.writeC(set.getfire());
			}
			if (set.getwater() != 0) {
				os.writeC(_water);
				os.writeC(set.getwater());
			}
			if (set.getwind() != 0) {
				os.writeC(_wind);
				os.writeC(set.getwind());
			}
			if (set.getearth() != 0) {
				os.writeC(_earth);
				os.writeC(set.getearth());
			}

			if (set.getStr() != 0) {
				os.writeC(_str);
				os.writeC(set.getStr());
			}
			if (set.getDex() != 0) {
				os.writeC(_dex);
				os.writeC(set.getDex());
			}
			if (set.getCon() != 0) {
				os.writeC(_con);
				os.writeC(set.getCon());
			}
			if (set.getWis() != 0) {
				os.writeC(_wis);
				os.writeC(set.getWis());
			}
			if (set.getIntl() != 0) {
				os.writeC(_int);
				os.writeC(set.getIntl());
			}
			if (set.getCha() != 0) {
				os.writeC(_cha);
				os.writeC(set.getCha());
			}
			os.writeC(0x45);
			os.writeC(0);

			if (getItem().getType2() == 2) {
				if (getItem().getType() == 8 || getItem().getType() == 12) {
					os.writeC(0x43);
					os.writeC(0x2b);// 근성
				} else if (getItem().getType() == 9 || getItem().getType() == 11) {
					os.writeC(0x43);
					os.writeC(0x2c);// 열정
				} else if (getItem().getType() == 10) {
					os.writeC(0x43);
					os.writeC(0x2d);// 의지
				} else {
					os.writeC(0);
					os.writeC(-1);
				}
			} else {
				os.writeC(0);
				os.writeC(0);
			}

		} catch (Exception e) {
		}
		return os.getBytes();
	}

	public byte[] getStatusBytes(L1PcInstance pc) {
		byte[] data = getStatusBytes();
		@SuppressWarnings("resource")
		BinaryOutputStream os = new BinaryOutputStream();
		try {
			os.write(data);
			/*
			 * if(pc != null && isEquipped()){ if(getItem().getItemId() ==
			 * 20423){ if(pc.getInventory().checkEquipped(21019)){ os.writeC(8);
			 * os.writeC(2); os.writeC(10); os.writeC(-2); } }else
			 * if(getItem().getItemId() == 20424){
			 * if(pc.getInventory().checkEquipped(21019)){ os.writeC(12);
			 * os.writeC(2); os.writeC(11); os.writeC(-2); } }else
			 * if(getItem().getItemId() == 20425){
			 * if(pc.getInventory().checkEquipped(21019)){ os.writeC(9);
			 * os.writeC(2); os.writeC(13); os.writeC(-2); } }else
			 * if(getItem().getItemId() == 423021){
			 * if(pc.getInventory().checkEquipped(423020)){ os.writeC(14);
			 * os.writeH(55); os.writeC(37); os.writeC(5); } }else
			 * if(getItem().getItemId() == 423022){
			 * if(pc.getInventory().checkEquipped(423020)){ os.writeC(32);
			 * os.writeC(33); os.writeC(38); os.writeC(2); os.writeC(17);
			 * os.writeH(1); } }else if(getItem().getItemId() == 423023){
			 * if(pc.getInventory().checkEquipped(423020)){ os.writeC(24);
			 * os.writeC(2); os.writeC(5); os.writeC(2); os.writeC(6);
			 * os.writeC(2); os.writeC(35); os.writeC(2); } }else
			 * if(getItem().getItemId() == 21173){
			 * if(pc.getInventory().checkEquipped(21176)){ os.writeC(14);
			 * os.writeH(55); os.writeC(37); os.writeC(8); os.writeC(38);
			 * os.writeC(1); } }else if(getItem().getItemId() == 21174){
			 * if(pc.getInventory().checkEquipped(21176)){ os.writeC(32);
			 * os.writeC(33); os.writeC(37); os.writeC(3); os.writeC(38);
			 * os.writeC(3); os.writeC(17); os.writeH(1); } }else
			 * if(getItem().getItemId() == 21175){
			 * if(pc.getInventory().checkEquipped(21176)){ os.writeC(37);
			 * os.writeC(3); os.writeC(38); os.writeC(1); os.writeC(24);
			 * os.writeC(2); os.writeC(5); os.writeC(2); os.writeC(6);
			 * os.writeC(2); os.writeC(35); os.writeC(2); } } }
			 */

			L1ArmorSets set = ArmorSetTable.getInstance().getArmorSets(getItem().getSetId());

			if (set != null && getItem().getMainId() == getItem().getItemId()) {
				os.writeC(0x45);
				os.writeC(2);
				if (set.getAc() != 0) {
					os.writeC(_add_ac);
					os.writeC(set.getAc());
				}
				if (getItem().getItemId() == 20099) {
					os.writeC(_poly);
					os.writeH(1175);// 데몬
				} else if (getItem().getItemId() == 20100) {
					os.writeC(_poly);
					os.writeH(18692);// 진데스
				} else if (getItem().getItemId() == 20151) {
					os.writeC(_poly);
					os.writeH(2118);// 케레니스
				} else if (getItem().getItemId() == 20118) {
					os.writeC(_poly);
					os.writeH(2117);// 켄라우헬
				}

				if (set.getShortHitup() != 0) {
					os.writeC(_hit);
					os.writeC(set.getShortHitup());
				}
				if (set.getShortDmgup() != 0) {
					os.writeC(_dmg);
					os.writeC(set.getShortDmgup());
				}

				if (set.getLongHitup() != 0) {
					os.writeC(_bowhit);
					os.writeC(set.getLongHitup());
				}
				if (set.getLongDmgup() != 0) {
					os.writeC(_bowdmg);
					os.writeC(set.getLongDmgup());
				}

				if (set.getHpr() != 0) {
					os.writeC(_hpr);
					os.writeC(set.getHpr());
				}
				if (set.getMpr() != 0) {
					os.writeC(_mpr);
					os.writeC(set.getMpr());
				}

				if (set.getHp() != 0) {
					os.writeC(_maxhp);
					os.writeH(set.getHp());
				}
				if (set.getMp() != 0) {
					os.writeC(_maxmp);
					os.writeC(set.getMp());
				}

				if (set.getMr() != 0) {
					os.writeC(_mr);
					os.writeH(set.getMr());
				}

				if (set.getSp() != 0) {
					os.writeC(_sp);
					os.writeC(set.getSp());
				}

				if (set.getfire() != 0) {
					os.writeC(_fire);
					os.writeC(set.getfire());
				}
				if (set.getwater() != 0) {
					os.writeC(_water);
					os.writeC(set.getwater());
				}
				if (set.getwind() != 0) {
					os.writeC(_wind);
					os.writeC(set.getwind());
				}
				if (set.getearth() != 0) {
					os.writeC(_earth);
					os.writeC(set.getearth());
				}

				if (set.getStr() != 0) {
					os.writeC(_str);
					os.writeC(set.getStr());
				}
				if (set.getDex() != 0) {
					os.writeC(_dex);
					os.writeC(set.getDex());
				}
				if (set.getCon() != 0) {
					os.writeC(_con);
					os.writeC(set.getCon());
				}
				if (set.getWis() != 0) {
					os.writeC(_wis);
					os.writeC(set.getWis());
				}
				if (set.getIntl() != 0) {
					os.writeC(_int);
					os.writeC(set.getIntl());
				}
				if (set.getCha() != 0) {
					os.writeC(_cha);
					os.writeC(set.getCha());
				}
				os.writeC(0x45);
				os.writeC(0);
			}

			if (getItem().getType2() == 2) {
				if (getItem().getType() == 8 || getItem().getType() == 12) {
					os.writeC(0x43);
					os.writeC(0x2b);// 근성
				} else if (getItem().getType() == 9 || getItem().getType() == 11) {
					os.writeC(0x43);
					os.writeC(0x2c);// 열정
				} else if (getItem().getType() == 10) {
					os.writeC(0x43);
					os.writeC(0x2d);// 의지
				} else {
					os.writeC(0);
					os.writeC(-1);
				}
			} else {
				os.writeC(0);
				os.writeC(0);
			}

		} catch (Exception e) {
		}
		return os.getBytes();
	}

	private String spirit() {
		int lvl = getRegistLevel();
		String in = "";
		switch (lvl) {
		case 1:
			in = "정령의 인(I)";
			break;
		case 2:
			in = "정령의 인(II)";
			break;
		case 3:
			in = "정령의 인(III)";
			break;
		case 4:
			in = "정령의 인(IV)";
			break;
		case 5:
			in = "정령의 인(V)";
			break;
		default:
			break;
		}
		return in;
	}

	public EnchantTimer getSkill() {
		return _timer;
	}

	public void getSkillExit() {
		_timer.cancel();
		_isRunning = false;
		_timer = null;
		setAcByMagic(0);
		setDmgByMagic(0);
		setHolyDmgByMagic(0);
		setHitByMagic(0);
	}

	public class EnchantTimer implements Runnable {

		private int skillId = 0;
		private int time = 0;
		private boolean cancel = false;

		public EnchantTimer() {
		}

		public EnchantTimer(int skillid, int _time) {
			skillId = skillid;
			time = _time;
		}

		@Override
		public void run() {
			try {
				if (cancel)
					return;
				time--;
				if (time > 0) {
					GeneralThreadPool.getInstance().schedule(this, 1000);
					return;
				}

				int type = getItem().getType();
				int type2 = getItem().getType2();
				int itemId = getItem().getItemId();
				if (_pc != null && _pc.getInventory().checkItem(itemId)) {
					if (type == 2 && type2 == 2 && isEquipped()) {
						_pc.getAC().addAc(3);
						_pc.sendPackets(new S_OwnCharStatus(_pc), true);
						switch (skillId) {
						case L1SkillId.BLESSED_ARMOR:
							if (_pc != null && _pc.getInventory().checkItem(getItemId()) && isEquipped()) {
								_pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 748, 0, false, false));
							}
							break;
						default:
							break;
						}
					}
				}
				// _pc.sendPackets(new S_ServerMessage(308, getLogName()));
				switch (skillId) {
				case L1SkillId.ENCHANT_WEAPON:
					if (_pc != null && _pc.getInventory().checkItem(itemId) && isEquipped()) {
						_pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0, _isSecond, false));
					}
					break;
				case L1SkillId.SHADOW_FANG:
					if (_pc != null && _pc.getInventory().checkItem(itemId) && isEquipped()) {
						_pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2951, 0, false, false));
					}
					break;
				default:
					break;
				}
				setAcByMagic(0);
				setDmgByMagic(0);
				setHolyDmgByMagic(0);
				setHitByMagic(0);
				_isRunning = false;
				_timer = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public int getSkillId() {
			return skillId;
		}

		public int getTime() {
			return time;
		}

		public void cancel() {
			cancel = true;
		}

	}

	private int _acByMagic = 0;
	private int _hitByMagic = 0;
	private int _holyDmgByMagic = 0;
	private int _dmgByMagic = 0;

	public int getAcByMagic() {
		return _acByMagic;
	}

	public void setAcByMagic(int i) {
		_acByMagic = i;
	}

	public int getDmgByMagic() {
		return _dmgByMagic;
	}

	public void setDmgByMagic(int i) {
		_dmgByMagic = i;
	}

	public int getHolyDmgByMagic() {
		return _holyDmgByMagic;
	}

	public void setHolyDmgByMagic(int i) {
		_holyDmgByMagic = i;
	}

	public int getHitByMagic() {
		return _hitByMagic;
	}

	public void setHitByMagic(int i) {
		_hitByMagic = i;
	}

	public void setSkillArmorEnchant(L1PcInstance pc, int skillId, int skillTime) {
		int type = getItem().getType();
		int type2 = getItem().getType2();
		if (_isRunning) {
			_timer.cancel();
			int itemId = getItem().getItemId();
			if (pc != null && pc.getInventory().checkItem(itemId)) {
				if (type == 2 && type2 == 2 && isEquipped()) {
					pc.getAC().addAc(3);
					pc.sendPackets(new S_OwnCharStatus(pc), true);
				}
			}
			setAcByMagic(0);
			_isRunning = false;
			_timer = null;
			switch (skillId) {
			case L1SkillId.BLESSED_ARMOR:
				if (pc != null && pc.getInventory().checkItem(getItemId()) && isEquipped()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 748, 0, false, false));
				}
				break;
			default:
				break;
			}
		}
		if (type == 2 && type2 == 2 && isEquipped()) {
			pc.getAC().addAc(-3);
			pc.sendPackets(new S_OwnCharStatus(pc));
			switch (skillId) {
			case L1SkillId.BLESSED_ARMOR:
				if (pc != null && pc.getInventory().checkItem(getItem().getItemId()) && isEquipped()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 748, skillTime / 1000, false, false));
				}
				break;
			default:
				break;
			}
		}
		setAcByMagic(3);
		_pc = pc;
		_timer = new EnchantTimer(skillId, skillTime / 1000);
		GeneralThreadPool.getInstance().schedule(_timer, 1000);
		// (new Timer()).schedule(_timer, skillTime);
		_isRunning = true;
	}

	public void setSkillWeaponEnchant(L1PcInstance pc, int skillId, int skillTime) {
		if (getItem().getType2() != 1) {
			return;
		}
		if (_isRunning) {
			_timer.cancel();
			setDmgByMagic(0);
			setHolyDmgByMagic(0);
			setHitByMagic(0);
			_isRunning = false;
			_timer = null;
			switch (skillId) {
			case L1SkillId.ENCHANT_WEAPON:
				if (pc != null && pc.getInventory().checkItem(getItemId()) && isEquipped()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, 0, _isSecond, false));
				}
				break;
			case L1SkillId.BLESS_WEAPON:
				if (pc != null && pc.getInventory().checkItem(getItemId()) && isEquipped()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2176, 0, _isSecond, false));
				}
				break;
			case L1SkillId.SHADOW_FANG:
				if (pc != null && pc.getInventory().checkItem(getItemId()) && isEquipped()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2951, 0, false, false));
				}
				break;
			default:
				break;
			}
		}

		switch (skillId) {
		case L1SkillId.HOLY_WEAPON:
			setHolyDmgByMagic(1);
			setHitByMagic(1);
			break;

		case L1SkillId.ENCHANT_WEAPON:
			if (pc != null && pc.getInventory().checkItem(getItem().getItemId()) && isEquipped()) {
				pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 747, skillTime / 1000, _isSecond, false));
			}
			setDmgByMagic(2);
			break;

		case L1SkillId.BLESS_WEAPON:
			if (pc != null && pc.getInventory().checkItem(getItem().getItemId()) && isEquipped()) {
				pc.sendPackets(
						new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2176, skillTime / 1000, _isSecond, false));
			}
			setDmgByMagic(2);
			setHitByMagic(2);
			break;

		case L1SkillId.SHADOW_FANG:
			if (pc != null && pc.getInventory().checkItem(getItem().getItemId()) && isEquipped()) {
				pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2951, skillTime / 1000, false, false));
			}
			setDmgByMagic(5);
			break;

		default:
			break;
		}

		_pc = pc;
		_timer = new EnchantTimer(skillId, skillTime / 1000);
		GeneralThreadPool.getInstance().schedule(_timer, 1000);
		// (new Timer()).schedule(_timer, skillTime);
		_isRunning = true;
	}

	private int 붉귀리덕() {
		int lvl = getEnchantLevel();
		int i = 0;
		switch (lvl) {
		case 3:
			i = 1;
			break;
		case 4:
			i = 1;
			break;
		case 5:
			i = 2;
			break;
		case 6:
			i = 3;
			break;
		case 7:
			i = 4;
			break;
		case 8:
			i = 5;
			break;
		default:
			break;
		}
		return i;
	}

	private int 축붉귀리덕() {
		int lvl = getEnchantLevel();
		int i = 0;
		switch (lvl) {
		case 3:
			i = 1;
			break;
		case 4:
			i = 2;
			break;
		case 5:
			i = 3;
			break;
		case 6:
			i = 4;
			break;
		case 7:
			i = 5;
			break;
		case 8:
			i = 6;
			break;
		default:
			break;
		}
		return i;
	}

	// ** 룸티스 악세 정보 추가부분 **/
	public void startItemOwnerTimer(L1PcInstance pc) {
		setItemOwner(pc);
		L1ItemOwnerTimer timer = new L1ItemOwnerTimer(this, 10000);
		timer.begin();
	}

	private L1EquipmentTimer _equipmentTimer;

	public void startEquipmentTimer(L1PcInstance pc) {
		if (getRemainingTime() > 0) {
			_equipmentTimer = new L1EquipmentTimer(pc, this);
			Timer timer = new Timer(true);
			timer.scheduleAtFixedRate(_equipmentTimer, 1000, 1000);
		}
	}

	public void stopEquipmentTimer() {
		try {
			if (getRemainingTime() > 0 && _equipmentTimer != null) {
				_equipmentTimer.cancel();
				_equipmentTimer = null;
			}
		} catch (Exception e) {
		}
	}

	private L1PcInstance _itemOwner;

	public L1PcInstance getItemOwner() {
		return _itemOwner;
	}

	public void setItemOwner(L1PcInstance pc) {
		_itemOwner = pc;
	}

	private boolean _isNowLighting = false;

	public boolean isNowLighting() {
		return _isNowLighting;
	}

	public void setNowLighting(boolean flag) {
		_isNowLighting = flag;
	}

	private int _secondId;

	public int getSecondId() {
		return _secondId;
	}

	public void setSecondId(int i) {
		_secondId = i;
	}

	private int _roundId;

	public int getRoundId() {
		return _roundId;
	}

	public void setRoundId(int i) {
		_roundId = i;
	}

	private int _ticketId = -1; // 티겟 번호

	public int getTicketId() {
		return _ticketId;
	}

	public void setTicketId(int i) {
		_ticketId = i;
	}

	private int _DropMobId = 0;

	public int isDropMobId() {
		return _DropMobId;
	}

	public void setDropMobId(int i) {
		_DropMobId = i;
	}

	private boolean _isWorking = false;

	public boolean isWorking() {
		return _isWorking;
	}

	public void setWorking(boolean flag) {
		_isWorking = flag;
	}

	// 아이템을 분당체크해서 삭제하기 위해서 추가!!
	private int _deleteItemTime = 0;

	public int get_DeleteItemTime() {
		return _deleteItemTime;
	}

	public void add_DeleteItemTime() {
		_deleteItemTime++;
	}

	public void init_DeleteItemTime() {
		_deleteItemTime = 0;
	}
}
