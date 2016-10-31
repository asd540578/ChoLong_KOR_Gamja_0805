/*
n * This program is free software; you can redistribute it and/or modify
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

package l1j.server.server.model.item.function;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import l1j.server.Config;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;
import server.manager.eva;

public class Enchant extends L1ItemInstance {
	private static final long serialVersionUID = 1L;

	private static Random _random = new Random(System.nanoTime());

	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd h:mm:ss a");
	String time = dateFormat.format(currentDate.getTime());

	public Enchant(L1Item item) {
		super(item);
	}

	public void SuccessEnchant(L1PcInstance pc, L1ItemInstance item, int i) {
		String s = "";
		String sa = "";
		String sb = "";
		String s1 = item.getName();
		
		if (item.getItem().getType2() == 1) {
			if (!item.isIdentified() || item.getEnchantLevel() == 0) {
				switch (i) {
				case -1:
					s = item.getLogName();
					sa = "$246";
					sb = "$247";
					break;

				case 1: // '\001'
					s = item.getLogName();
					sa = "$245";
					sb = "$247";
					break;

				case 2: // '\002'
					s = item.getLogName();
					sa = "$245";
					sb = "$248";
					break;

				case 3: // '\003'
					s = item.getLogName();
					sa = "$245";
					sb = "$248";
					break;
				}
			} else {
				switch (i) {
				case -1:
					/*
					 * s = (new StringBuilder()).append( pm +
					 * item.getEnchantLevel()).append(" ").append(s1)
					 * .toString(); // \f1%0��%2%1 �����ϴ�.
					 */
					s = item.getLogName();
					sa = "$246";
					sb = "$247";
					break;

				case 1: // '\001'
					/*
					 * s = (new StringBuilder()).append( pm +
					 * item.getEnchantLevel()).append(" ").append(s1)
					 * .toString(); // \f1%0��%2%1 �����ϴ�.
					 */
					s = item.getLogName();
					sa = "$245";
					sb = "$247";
					break;

				case 2: // '\002'
					/*
					 * s = (new StringBuilder()).append( pm +
					 * item.getEnchantLevel()).append(" ").append(s1)
					 * .toString(); // \f1%0��%2%1 �����ϴ�.
					 */
					s = item.getLogName();
					sa = "$245";
					sb = "$248";
					break;

				case 3: // '\003'
					/*
					 * s = (new StringBuilder()).append( pm +
					 * item.getEnchantLevel()).append(" ").append(s1)
					 * .toString(); // \f1%0��%2%1 �����ϴ�.
					 */
					s = item.getLogName();
					sa = "$245";
					sb = "$248";
					break;
				}
			}
		} else if (item.getItem().getType2() == 2) {
			if (!item.isIdentified() || item.getEnchantLevel() == 0) {
				switch (i) {
				case -1:
					s = s1;
					sa = "$246";
					sb = "$247";
					break;

				case 1: // '\001'
					if (item.getItem().getGrade() < 0) {
						s = s1;
						sa = "$252";
						sb = "$247";
					} else {
						s = s1;
						sa = "$245";
						sb = "$248";
					}
					break;
				case 2: // '\002'
					s = s1;
					sa = "$252";
					sb = "$248";
					break;

				case 3: // '\003'
					s = s1;
					sa = "$252";
					sb = "$248";
					break;
				}
			} else {
				switch (i) {
				case -1:
					s = item.getLogName();
					// s = (new StringBuilder()).append(
					// pm + item.getEnchantLevel()).append(" ").append(s1)
					// .toString(); // \f1%0��%2%1 �����ϴ�.
					sa = "$246";
					sb = "$247";
					break;

				case 1: // '\001'
					s = item.getLogName();
					// s = (new StringBuilder()).append(
					// pm + item.getEnchantLevel()).append(" ").append(s1)
					// .toString(); // \f1%0��%2%1 �����ϴ�.
					if (item.getItem().getGrade() < 0) {
						sa = "$252";
						sb = "$247";
					} else {
						sa = "$245";
						sb = "$248";
					}
					break;

				case 2: // '\002'
					s = item.getLogName();
					// s = (new StringBuilder()).append(
					// pm + item.getEnchantLevel()).append(" ").append(s1)
					// .toString(); // \f1%0��%2%1 �����ϴ�.
					sa = "$252";
					sb = "$248";
					break;

				case 3: // '\003'
					s = item.getLogName();
					// s = (new StringBuilder()).append(
					// pm + item.getEnchantLevel()).append(" ").append(s1)
					// .toString(); // \f1%0��%2%1 �����ϴ�.
					sa = "$252";
					sb = "$248";
					break;
				}
			}
		}

		pc.setLastEnchantItemid(0, null);
		pc.sendPackets(new S_ServerMessage(161, s, sa, sb), true);
		int oldEnchantLvl = item.getEnchantLevel();
		int newEnchantLvl = item.getEnchantLevel() + i;
		int attr_level = item.getAttrEnchantLevel();
		int safe_enchant = item.getItem().get_safeenchant();

		if (item.getItem().getType2() == 1 && newEnchantLvl >= 10) {
			L1World.getInstance().broadcastPacketToAll(new S_ServerMessage(4446, item.getLogName()));
		}

		if (item.getItem().getType2() == 2) {
			if (item.getItem().getType() >= 8 && item.getItem().getType() <= 12) {
				if (newEnchantLvl >= 8) {
					L1World.getInstance().broadcastPacketToAll(new S_ServerMessage(4445, item.getLogName()));
				}
			} else if (newEnchantLvl >= 9) {
				L1World.getInstance().broadcastPacketToAll(new S_ServerMessage(4445, item.getLogName()));
			}
		}

		item.setEnchantLevel(newEnchantLvl);

		if (oldEnchantLvl >= 10 && newEnchantLvl < 10
				&& (attr_level == 33 || attr_level == 35 || attr_level == 37 || attr_level == 39)) {
			if (attr_level == 33)
				item.setAttrEnchantLevel(3);
			else if (attr_level == 35)
				item.setAttrEnchantLevel(6);
			else if (attr_level == 37)
				item.setAttrEnchantLevel(9);
			else if (attr_level == 39)
				item.setAttrEnchantLevel(12);
			pc.getInventory().updateItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
			pc.getInventory().saveItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
			pc.sendPackets(new S_ServerMessage(3296, item.getViewName()), true);
		} else if (oldEnchantLvl >= 11 && newEnchantLvl < 11
				&& (attr_level == 34 || attr_level == 36 || attr_level == 38 || attr_level == 40)) {
			if (attr_level == 34)
				item.setAttrEnchantLevel(33);
			else if (attr_level == 36)
				item.setAttrEnchantLevel(35);
			else if (attr_level == 38)
				item.setAttrEnchantLevel(37);
			else if (attr_level == 40)
				item.setAttrEnchantLevel(39);
			pc.getInventory().updateItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
			pc.getInventory().saveItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
			pc.sendPackets(new S_ServerMessage(3296, item.getViewName()), true);
		}
		pc.getInventory().updateItem(item, L1PcInventory.COL_ENCHANTLVL);
		pc.sendPackets(new S_PacketBox(item, S_PacketBox.��æ����));
		pc.saveInventory();
		if (newEnchantLvl > safe_enchant) {
			pc.getInventory().saveItem(item, L1PcInventory.COL_ENCHANTLVL);
		}

		if (item.getItem().getType2() == 1 && Config.LOGGING_WEAPON_ENCHANT != 0) {
			if (safe_enchant == 0 || newEnchantLvl >= Config.LOGGING_WEAPON_ENCHANT) {
			}
		}
		if (item.getItem().getType2() == 2 && Config.LOGGING_ARMOR_ENCHANT != 0) {
			if (safe_enchant == 0 || newEnchantLvl >= Config.LOGGING_ARMOR_ENCHANT) {
			}
		}

		if (newEnchantLvl > safe_enchant) {
			if (Config.��þä�ø����() > 0) {
				for (L1PcInstance gm : Config.toArray��þä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove��þ(gm);
						continue;
					}
					if (gm == pc) {
						continue;
					}
					gm.sendPackets(new S_SystemMessage("\\fV[" + pc.getName() + "] " + item.getName() + " : "
							+ oldEnchantLvl + "->" + newEnchantLvl + " [��þ ����]"), true);
				}
			}
			eva.LogEnchantAppend("����:W", pc.getName(), oldEnchantLvl + "->" + newEnchantLvl, item.getName(),
					item.getId());
		}
		if (item.getItem().getType2() == 2) {
			if (newEnchantLvl > safe_enchant) {
				eva.LogEnchantAppend("����:A", pc.getName(), oldEnchantLvl + "->" + newEnchantLvl, item.getName(),
						item.getId());
			}
		}

		if (item.getItem().getType2() == 2) {
			if (item.isEquipped()) {
				if (item.getItem().getType() >= 8 && item.getItem().getType() <= 12) {
				} else {
					pc.getAC().addAc(-i);
				}
				int i2 = item.getItem().getItemId();
				if (i2 == 500214 || i2 == 20011 || i2 == 20110 || i2 == 120011 || i2 == 9091 || i2 == 20117
						|| i2 == 420108 || i2 == 420109 || i2 == 420110 || i2 == 420111 || i2 == 425108 || i2 == 490008
						|| i2 == 490017 || i2 == 120194 || i2 == 1020110 || (i2 >= 21169 && i2 <= 21172)) { // ����
					// ���, ����
					// ü�� ����
					pc.getResistance().addMr(i);
					pc.sendPackets(new S_SPMR(pc), true);
				}
				if (i2 == 20056 || i2 == 120056 || i2 == 9092 || i2 == 220056 || i2 == 425108 || i2 == 9084
						|| i2 == 20074 || i2 == 120074) { // ���� Ŭ��ũ
					pc.getResistance().addMr(i * 2);
					pc.sendPackets(new S_SPMR(pc), true);
				}
				if (i2 == 20078 || i2 == 20079 || i2 == 120079 || i2 == 21137) {
					pc.getResistance().addMr(i * 3);
					pc.sendPackets(new S_SPMR(pc), true);
				}
				if (i2 == 220011 || i2 == 20049 || i2 == 20050) {
					pc.getResistance().addMr(i * 2);
					pc.sendPackets(new S_SPMR(pc), true);
				}
				if (i2 == 21166) {
					pc.setMaxMp(pc.getMaxMp() + (i * 10));
				}
			}

			pc.sendPackets(new S_OwnCharStatus(pc), true);
			pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
		}
	}

	public void FailureEnchant(L1PcInstance pc, L1ItemInstance item) {
		FailureEnchant(pc, item, 0);
	}

	public void FailureEnchant(L1PcInstance pc, L1ItemInstance item, int scrollid) {
		String s = "";
		String sa = "";
		int itemType = item.getItem().getType2();
		int itemId = item.getItem().getItemId();
		String nameId = item.getName();
		if (itemType == 1) { // ����
			if (!item.isIdentified() || item.getEnchantLevel() == 0) {
				s = nameId; // \f1%0%s �����ϰ�%1 �������� ���ߵǾ� ������ϴ�.
				sa = "$245";
			} else {
				s = item.getLogName();
				sa = "$245";
			}
		} else if (itemType == 2) { // ���� �ⱸ
			if (!item.isIdentified() || item.getEnchantLevel() == 0) {
				s = nameId; // \f1%0%s �����ϰ�%1 �������� ���ߵǾ� ������ϴ�.
				if (item.getItem().getGrade() < 0)
					sa = "$252";
				else
					sa = "$245";
			} else {
				s = item.getLogName();
				if (item.getItem().getGrade() < 0)
					sa = "$252";
				else
					sa = "$245";
			}
		}

		if (scrollid == L1ItemId.ORIM_ACCESSORY_ENCHANT_SCROLL) {
			String item_name_id = item.getName();
			int enchant_level = item.getEnchantLevel();
			String pm1 = "";
			String msg = "";
			if (enchant_level > 0) {
				pm1 = "+";
			}
			// ��æƮ: %0%s �Ҹ��� ������ �غ� �Ͽ����ϴ�.
			msg = (new StringBuilder()).append(pm1 + enchant_level).append(" ").append(item_name_id).toString();
			pc.sendPackets(new S_ServerMessage(161, msg, "$246", "$247"), true);
			int newEnchantLvl = item.getEnchantLevel() - 1;
			item.setEnchantLevel(newEnchantLvl);
			pc.getInventory().updateItem(item, L1PcInventory.COL_ENCHANTLVL);
			pc.saveInventory();

			if (Config.��þä�ø����() > 0) {
				for (L1PcInstance gm : Config.toArray��þä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove��þ(gm);
						continue;
					}
					if (gm == pc) {
						continue;
					}
					gm.sendPackets(new S_SystemMessage("\\fV[" + pc.getName() + "] +" + item.getEnchantLevel() + " "
							+ item.getName() + " [������þ ����]"));
				}
			}
		} else if ((itemId >= 450022 && itemId <= 450025) || (itemId >= 427110 && itemId <= 427112)) {
			pc.sendPackets(new S_ServerMessage(1310), true); // �����ϰ� �������� ��� ����
																// ������ �ʾҽ��ϴ�.
			pc.getInventory().setEquipped(item, false); // �߰����ּ���.
			item.setEnchantLevel(0);
			pc.getInventory().updateItem(item, L1PcInventory.COL_ENCHANTLVL);
			pc.saveInventory();
			if (Config.��þä�ø����() > 0) {
				for (L1PcInstance gm : Config.toArray��þä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove��þ(gm);
						continue;
					}
					if (gm == pc) {
						continue;
					}
					gm.sendPackets(new S_SystemMessage("\\fV[" + pc.getName() + "] +" + item.getEnchantLevel() + " "
							+ item.getName() + " [��þ ����]"), true);
				}
			}
			if (itemType == 1) {
				eva.LogEnchantAppend("����:W", pc.getName(), Integer.toString(item.getEnchantLevel()), item.getName(),
						item.getId());
			} else if (itemType == 2) {
				eva.LogEnchantAppend("����:A", pc.getName(), Integer.toString(item.getEnchantLevel()), item.getName(),
						item.getId());
			}
		} else if (item.isDemonBongin()) {
			if ((item.getItemId() >= 263 && item.getItemId() <= 265) || item.getItemId() == 256
					|| item.getItemId() == 4500027 || item.getItemId() == 4500026)
				pc.sendPackets(new S_SystemMessage("�����ϰ� �������� �ҷ����� ��ȣ�� �����־� ���� ������ �ʾҽ��ϴ�."), true);
			else
				pc.sendPackets(new S_SystemMessage("�����ϰ� �������� ������ ��ȣ�� �����־� ���� ������ �ʾҽ��ϴ�."), true);
			// pc.sendPackets(new S_ServerMessage(1310), true); // �����ϰ� �������� ���
			// ���� ������ �ʾҽ��ϴ�.
			pc.getInventory().setEquipped(item, false); // �߰����ּ���.

			item.setEnchantLevel(item.getEnchantLevel() - 1);
			item.setDemonBongin(false);
			pc.getInventory().updateItem(item, L1PcInventory.COL_ENCHANTLVL + L1PcInventory.COL_DEMONBONGIN);
			pc.getInventory().saveItem(item, L1PcInventory.COL_ENCHANTLVL + L1PcInventory.COL_DEMONBONGIN);
			pc.saveInventory();
			if (Config.��þä�ø����() > 0) {
				for (L1PcInstance gm : Config.toArray��þä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove��þ(gm);
						continue;
					}
					if (gm == pc) {
						continue;
					}
					gm.sendPackets(new S_SystemMessage("\\fV[" + pc.getName() + "] +" + item.getEnchantLevel() + " "
							+ item.getName() + " [��þ ����-> No ����]"), true);
				}
			}
			if (itemType == 1) {
				eva.LogEnchantAppend("����:W", pc.getName(), Integer.toString(item.getEnchantLevel()), item.getName(),
						item.getId());
			} else if (itemType == 2) {
				eva.LogEnchantAppend("����:A", pc.getName(), Integer.toString(item.getEnchantLevel()), item.getName(),
						item.getId());
			}
		} else {
			if (pc.getSecondWeapon() != null) {
				if (pc.getSecondWeapon().getId() == item.getId()) {
					pc.getInventory().setEquipped(pc.getSecondWeapon(), false, false, false, true);
				}
			}
			if (pc.getWeapon() != null) {
				if (pc.getWeapon().getId() == item.getId()) {
					pc.getInventory().setEquipped(pc.getWeapon(), false, false, false, false);
				}
			}

			pc.setLastEnchantItemid(item.getId(), item);
			pc.sendPackets(new S_ServerMessage(164, s, sa), true);
			pc.getInventory().removeItem(item, item.getCount());
			pc.saveInventory();
			if (Config.��þä�ø����() > 0) {
				for (L1PcInstance gm : Config.toArray��þä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove��þ(gm);
						continue;
					}
					if (gm == pc) {
						continue;
					}
					gm.sendPackets(new S_SystemMessage("\\fV[" + pc.getName() + "] +" + item.getEnchantLevel() + " "
							+ item.getName() + " [��þ ����]"), true);
				}
			}
			if (itemType == 1) {
				eva.LogEnchantAppend("����:W", pc.getName(), Integer.toString(item.getEnchantLevel()), item.getName(),
						item.getId());
			} else if (itemType == 2) {
				eva.LogEnchantAppend("����:A", pc.getName(), Integer.toString(item.getEnchantLevel()), item.getName(),
						item.getId());
			}
		}
	}

	public void RegistEnchant(L1PcInstance pc, L1ItemInstance item, int item_id) {
		int level = item.getRegistLevel();
		int chance = _random.nextInt(20) + 1;
		boolean bEquipped = false;

		if (item.isEquipped()) {
			pc.getInventory().setEquipped(item, false);
			bEquipped = true;
		}
		if (item_id == L1ItemId.Inadril_T_ScrollD) {
			switch (level) {
			case 0:
				if (chance <= 5) {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ���������ϴ�."), true);
					item.setRegistLevel(1);
				} else {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ������� ���Ͽ����ϴ�."), true);
				}
				break;
			case 1:
				if (chance <= 4) {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ���������ϴ�."), true);
					item.setRegistLevel(2);
				} else {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ������� ���Ͽ����ϴ�."), true);
				}
				break;
			case 2:
				if (chance <= 3) {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ���������ϴ�."), true);
					item.setRegistLevel(3);
				} else {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ������� ���Ͽ����ϴ�."), true);
				}
				break;
			case 3:
				if (chance <= 2) {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ���������ϴ�."), true);
					item.setRegistLevel(4);
				} else {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ������� ���Ͽ����ϴ�."), true);
				}
				break;
			case 4:
				if (chance <= 1) {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ���������ϴ�."), true);
					item.setRegistLevel(5);
				} else {
					pc.sendPackets(new S_SystemMessage("" + item.getLogName() + "�� �Ӽ� ���׷��� ������� ���Ͽ����ϴ�."), true);
				}
				break;
			default:
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				break;
			}

			if (bEquipped) {
				pc.getInventory().setEquipped(item, true);
			}
			pc.getInventory().updateItem(item, L1PcInventory.COL_regist);
			pc.getInventory().saveItem(item, L1PcInventory.COL_regist);
			pc.saveInventory();
		}
	}

	public int RandomELevel(L1ItemInstance item, int itemId) {

		if (item.getItem().getType2() == 2 && item.getItem().get_safeenchant() == 0) {
			if (item.getName().indexOf("����") > 0)
				return 1;
		}

		int j = _random.nextInt(100) + 1;
		if (itemId == L1ItemId.B_SCROLL_OF_ENCHANT_ARMOR || itemId == L1ItemId.B_SCROLL_OF_ENCHANT_WEAPON
				|| itemId == 1430041 || itemId == L1ItemId.Inadril_T_ScrollB) {
			if (item.getEnchantLevel() < 0) {
				if (j < 30) {
					return 2;
				} else {
					return 1;
				}
			} else if (item.getEnchantLevel() <= 2) {
				if (j < 32) {
					return 1;
				} else if (j >= 33 && j <= 76) {
					return 2;
				} else if (j >= 77 && j <= 100) {
					return 3;
				}
			} else if (item.getEnchantLevel() >= 3 && item.getEnchantLevel() <= 5) {
				if (j < 40) { // ���ڴ� 50
					return 2;
				} else {
					return 1;
				}
			}
			return 1;
		} else if (itemId == 140129 || itemId == 140130) {
			if (item.getEnchantLevel() < 0) {
				if (j < 30) {
					return 2;
				} else {
					return 1;
				}
			} else if (item.getEnchantLevel() <= 2) {
				if (j < 32) {
					return 1;
				} else if (j >= 33 && j <= 60) {
					return 2;
				} else if (j >= 61 && j <= 100) {
					return 3;
				}
			} else if (item.getEnchantLevel() >= 3 && item.getEnchantLevel() <= 5) {
				if (j < 40) {
					return 2;
				} else {
					return 1;
				}
			}
			return 1;
		}
		return 1;
	}

	public void AttrEnchant(L1PcInstance pc, L1ItemInstance item, int item_id) { // �Ӽ�4��5��
		int attr_level = item.getAttrEnchantLevel();
		int chance = _random.nextInt(1000) + 1;
		if (item.getEnchantLevel() < 9 && (attr_level == 3 || attr_level == 6 || attr_level == 9 || attr_level == 12)) {
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																			// �ƹ��͵�
																			// �Ͼ��
																			// �ʾҽ��ϴ�.
			return;
		} else if (item.getEnchantLevel() < 10
				&& (attr_level == 33 || attr_level == 35 || attr_level == 37 || attr_level == 39)) {
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																			// �ƹ��͵�
																			// �Ͼ��
																			// �ʾҽ��ϴ�.
			return;
		}
		if (item_id == L1ItemId.FIRE_ENCHANT_WEAPON_SCROLL) { // ���� ���� ��ȭ �ֹ���
			if (attr_level == 0 || (attr_level >= 4 && attr_level != 33 && attr_level != 34)) {
				if (attr_level >= 33 && attr_level <= 40)
					pc.sendPackets(new S_ServerMessage(3297, item.getLogName()), true);
				else if (chance < 300) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(1);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 1) {
				if (chance < 150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(2);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 2) {
				if (chance < 80) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(3);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 3) {
				if (chance < 50) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(33);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 33) {
				if (chance < 30) {
					// pc.sendPackets(new S_ServerMessage(1410,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(3296, item.getLogName()), true);
					item.setAttrEnchantLevel(34);
				} else if (chance < 100) {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
					item.setAttrEnchantLevel(3);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 34) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																				// �ƹ��͵�
																				// �Ͼ��
																				// �ʾҽ��ϴ�.
				return;
			}
		} else if (item_id == L1ItemId.WATER_ENCHANT_WEAPON_SCROLL) { // ���� ����
																		// ��ȭ
																		// �ֹ���
			if ((attr_level >= 0 && attr_level <= 3) || (attr_level >= 7 && attr_level != 35 && attr_level != 36)) {
				if (attr_level >= 33 && attr_level <= 40)
					pc.sendPackets(new S_ServerMessage(3297, item.getLogName()), true);
				else if (chance < 300) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(4);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 4) {
				if (chance < 150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(5);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 5) {
				if (chance < 80) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(6);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 6) {
				if (chance < 50) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(35);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 35) {
				if (chance < 30) {
					// pc.sendPackets(new S_ServerMessage(1410,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(3296, item.getLogName()), true);
					item.setAttrEnchantLevel(36);
				} else if (chance < 100) {
					// pc.sendPackets(new S_ServerMessage(3296,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
					item.setAttrEnchantLevel(6);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 36) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																				// �ƹ��͵�
																				// �Ͼ��
																				// �ʾҽ��ϴ�.
				return;
			}
		} else if (item_id == L1ItemId.WIND_ENCHANT_WEAPON_SCROLL) { // �ٶ��� ����
																		// ��ȭ
																		// �ֹ���
			if ((attr_level >= 0 && attr_level <= 6) || (attr_level >= 10 && attr_level != 37 && attr_level != 38)) {
				if (attr_level >= 33 && attr_level <= 40)
					pc.sendPackets(new S_ServerMessage(3297, item.getLogName()), true);
				else if (chance < 300) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(7);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 7) {
				if (chance < 150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(8);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 8) {
				if (chance < 80) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(9);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 9) {
				if (chance < 50) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(37);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 37) {
				if (chance < 30) {
					// pc.sendPackets(new S_ServerMessage(1410,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(3296, item.getLogName()), true);
					item.setAttrEnchantLevel(38);
				} else if (chance < 100) {
					// pc.sendPackets(new S_ServerMessage(3296,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
					item.setAttrEnchantLevel(9);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 38) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																				// �ƹ��͵�
																				// �Ͼ��
																				// �ʾҽ��ϴ�.
				return;
			}
		} else if (item_id == L1ItemId.EARTH_ENCHANT_WEAPON_SCROLL) { // ���� ����
																		// ��ȭ
																		// �ֹ���
			if (attr_level >= 0 && (attr_level <= 9 && attr_level != 39 && attr_level != 40)) {
				if (attr_level >= 33 && attr_level <= 40)
					pc.sendPackets(new S_ServerMessage(3297, item.getLogName()), true);
				else if (chance < 300) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(10);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 10) {
				if (chance < 150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(11);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 11) {
				if (chance < 80) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(12);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 12) {
				if (chance < 50) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(39);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 39) {
				if (chance < 30) {
					// pc.sendPackets(new S_ServerMessage(1410,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(3296, item.getLogName()), true);
					item.setAttrEnchantLevel(40);
				} else if (chance < 100) {
					// pc.sendPackets(new S_ServerMessage(3296,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
					item.setAttrEnchantLevel(12);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 40) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																				// �ƹ��͵�
																				// �Ͼ��
																				// �ʾҽ��ϴ�.
				return;
			}

		} else if (item_id == L1ItemId.FIRE_ENCHANT_WEAPON_SCROLL_100) { // ����
																			// ����
																			// ��ȭ
																			// �ֹ���
			if (attr_level == 0 || (attr_level >= 4 && attr_level != 33 && attr_level != 34)) {
				if (attr_level >= 33 && attr_level <= 40)
					pc.sendPackets(new S_ServerMessage(3297, item.getLogName()), true);
				else if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(1);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 1) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(2);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 2) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(3);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 3) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(33);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 33) {
				if (chance < 1150) {
					// pc.sendPackets(new S_ServerMessage(1410,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(3296, item.getLogName()), true);
					item.setAttrEnchantLevel(34);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 34) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																				// �ƹ��͵�
																				// �Ͼ��
																				// �ʾҽ��ϴ�.
				return;
			}
		} else if (item_id == L1ItemId.WATER_ENCHANT_WEAPON_SCROLL_100) { // ����
																			// ����
																			// ��ȭ
																			// �ֹ���
			if ((attr_level >= 0 && attr_level <= 3) || (attr_level >= 7 && attr_level != 35 && attr_level != 36)) {
				if (attr_level >= 33 && attr_level <= 40)
					pc.sendPackets(new S_ServerMessage(3297, item.getLogName()), true);
				else if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(4);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 4) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(5);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 5) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(6);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 6) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(35);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 35) {
				if (chance < 1150) {
					// pc.sendPackets(new S_ServerMessage(1410,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(3296, item.getLogName()), true);
					item.setAttrEnchantLevel(36);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 36) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																				// �ƹ��͵�
																				// �Ͼ��
																				// �ʾҽ��ϴ�.
				return;
			}
		} else if (item_id == L1ItemId.WIND_ENCHANT_WEAPON_SCROLL_100) { // �ٶ���
																			// ����
																			// ��ȭ
																			// �ֹ���
			if ((attr_level >= 0 && attr_level <= 6) || (attr_level >= 10 && attr_level != 37 && attr_level != 38)) {
				if (attr_level >= 33 && attr_level <= 40)
					pc.sendPackets(new S_ServerMessage(3297, item.getLogName()), true);
				else if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(7);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 7) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(8);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 8) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(9);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 9) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(37);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 37) {
				if (chance < 1150) {
					// pc.sendPackets(new S_ServerMessage(1410,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(3296, item.getLogName()), true);
					item.setAttrEnchantLevel(38);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 38) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																				// �ƹ��͵�
																				// �Ͼ��
																				// �ʾҽ��ϴ�.
				return;
			}
		} else if (item_id == L1ItemId.EARTH_ENCHANT_WEAPON_SCROLL_100) { // ����
																			// ����
																			// ��ȭ
																			// �ֹ���
			if (attr_level >= 0 && (attr_level <= 9 && attr_level != 39 && attr_level != 40)) {
				if (attr_level >= 33 && attr_level <= 40)
					pc.sendPackets(new S_ServerMessage(3297, item.getLogName()), true);
				else if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(10);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 10) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(11);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 11) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(12);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 12) {
				if (chance < 1150) {
					pc.sendPackets(new S_ServerMessage(1410, item.getLogName()), true);
					item.setAttrEnchantLevel(39);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 39) {
				if (chance < 1150) {
					// pc.sendPackets(new S_ServerMessage(1410,
					// item.getLogName()), true);
					pc.sendPackets(new S_ServerMessage(3296, item.getLogName()), true);
					item.setAttrEnchantLevel(40);
				} else {
					pc.sendPackets(new S_ServerMessage(1411, item.getLogName()), true);
				}
			} else if (attr_level == 40) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																				// �ƹ��͵�
																				// �Ͼ��
																				// �ʾҽ��ϴ�.
				return;
			}
		}

		pc.getInventory().consumeItem(item_id, 1);
		pc.getInventory().updateItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
		pc.getInventory().saveItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
		pc.saveInventory();

	}

	public void StepEnchant(L1PcInstance pc, L1ItemInstance item, int item_id) { // <<�˻�
		int step_level = item.getStepEnchantLevel();
		int chance = _random.nextInt(100) + 1;
		boolean bEquipped = false;

		if (item.isEquipped()) {
			pc.getInventory().setEquipped(item, false);
			bEquipped = true;
		}
		if (item_id == L1ItemId.Add_ENCHANT_WEAPON_SCROLL) {
			{
				if (step_level == 0) {
					if (chance < 20) {
						item.setStepEnchantLevel(1);
						pc.sendPackets(new S_SystemMessage("\\fW���� 1�ܰ� ��ȭ  ���׷��̵� ����!"), true);
					} else {
						pc.sendPackets(new S_SystemMessage("\\fY���� 1�ܰ� ��ȭ  ���׷��̵� ����!"), true);
					}
				} else if (step_level == 1) {
					if (chance < 10) {
						item.setStepEnchantLevel(2);
						pc.sendPackets(new S_SystemMessage("\\fW���� 2�ܰ� ��ȭ  ���׷��̵� ����!"), true);
					} else {
						item.setStepEnchantLevel(1);
						pc.sendPackets(new S_SystemMessage("\\fY���� 2�ܰ� ��ȭ  ���׷��̵� ����!"), true);
					}
				} else if (step_level == 2) {
					if (chance < 5) {
						item.setStepEnchantLevel(3);
						pc.sendPackets(new S_SystemMessage("\\fW���� 3�ܰ� ��ȭ  ���׷��̵� ����!"), true);
					} else {
						item.setStepEnchantLevel(2);
						pc.sendPackets(new S_SystemMessage("\\fY���� 3�ܰ� ��ȭ  ���׷��̵� ����!"), true);
					}
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
		}

		if (item_id == L1ItemId.Add_ENCHANT_WEAPON_SCROLL_100) {// Ưȭ ��ȭ�ܼ�100%
			{
				if (step_level == 0) {
					// if (chance < 100) {
					item.setStepEnchantLevel(1);
					pc.sendPackets(new S_SystemMessage("\\fW���� 1�ܰ� ��ȭ  ���׷��̵� ����!"), true);
					// }
				} else if (step_level == 1) {
					// if (chance < 100) {
					item.setStepEnchantLevel(2);
					pc.sendPackets(new S_SystemMessage("\\fW���� 2�ܰ� ��ȭ  ���׷��̵� ����!"), true);
					// }
				} else if (step_level == 2) {
					// if (chance < 100) {
					item.setStepEnchantLevel(3);
					pc.sendPackets(new S_SystemMessage("\\fW���� 3�ܰ� ��ȭ  ���׷��̵� ����!"), true);
					// }
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}

			}
		}

		if (bEquipped) {
			pc.getInventory().setEquipped(item, true);
		}
		pc.getInventory().consumeItem(item_id, 1);
		pc.getInventory().updateItem(item, L1PcInventory.COL_STEPENCHANTLVL);
		pc.getInventory().saveItem(item, L1PcInventory.COL_STEPENCHANTLVL);
		pc.saveInventory();

	}

	public void DragonArmorEnchant(L1PcInstance pc, L1ItemInstance item, int item_id) {
		int attr_level = item.getAttrEnchantLevel();
		// int safe_enchant = item.getItem().get_safeenchant();
		int chance = _random.nextInt(100) + 1;
		boolean bEquipped = false;

		if (item.isEquipped()) {
			pc.getInventory().setEquipped(item, false);
			bEquipped = true;
		}

		if (item_id == 5000138) {
			if (attr_level == 0) {
				if (chance < 20) {
					item.setAttrEnchantLevel(41);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 41) {
				if (chance < 20) {
					item.setAttrEnchantLevel(42);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 41) {
				if (chance < 20) {
					item.setAttrEnchantLevel(42);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 42) {
				if (chance < 20) {
					item.setAttrEnchantLevel(43);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 43) {
				if (chance < 20) {
					item.setAttrEnchantLevel(44);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 44) {
				if (chance < 20) {
					item.setAttrEnchantLevel(45);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 45) {
				if (chance < 20) {
					item.setAttrEnchantLevel(46);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 46) {
				if (chance < 20) {
					item.setAttrEnchantLevel(47);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 47) {
				if (chance < 20) {
					item.setAttrEnchantLevel(48);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 48) {
				if (chance < 20) {
					item.setAttrEnchantLevel(49);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 49) {
				if (chance < 20) {
					item.setAttrEnchantLevel(50);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 50) {
				if (chance < 20) {
					item.setAttrEnchantLevel(51);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 51) {
				if (chance < 20) {
					item.setAttrEnchantLevel(52);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 52) {
				if (chance < 20) {
					item.setAttrEnchantLevel(53);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 53) {
				if (chance < 20) {
					item.setAttrEnchantLevel(54);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 54) {
				if (chance < 20) {
					item.setAttrEnchantLevel(55);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 55) {
				if (chance < 20) {
					item.setAttrEnchantLevel(56);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 56) {
				if (chance < 20) {
					item.setAttrEnchantLevel(57);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 57) {
				if (chance < 20) {
					item.setAttrEnchantLevel(58);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 58) {
				if (chance < 20) {
					item.setAttrEnchantLevel(59);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 59) {
				if (chance < 20) {
					item.setAttrEnchantLevel(60);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 60) {
				if (chance < 20) {
					item.setAttrEnchantLevel(61);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 61) {
				if (chance < 20) {
					item.setAttrEnchantLevel(62);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 62) {
				if (chance < 20) {
					item.setAttrEnchantLevel(63);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 63) {
				if (chance < 20) {
					item.setAttrEnchantLevel(64);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 64) {
				if (chance < 20) {
					item.setAttrEnchantLevel(65);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 65) {
				if (chance < 20) {
					item.setAttrEnchantLevel(66);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 66) {
				if (chance < 20) {
					item.setAttrEnchantLevel(67);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 67) {
				if (chance < 20) {
					item.setAttrEnchantLevel(68);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 68) {
				if (chance < 20) {
					item.setAttrEnchantLevel(69);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else if (attr_level == 69) {
				if (chance < 20) {
					item.setAttrEnchantLevel(70);
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("������ ������ ���׷��̵� ����!!"), true);
				}
			} else

			{
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
		}

		if (item_id == 5000139) {
			if (attr_level == 0) {
				if (chance < 20) {
					item.setAttrEnchantLevel(24);
					pc.sendPackets(new S_SystemMessage("\\fW������ ���� ��ȭ  ���׷��̵� ����!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("\\fY������ ���� ��ȭ  ���׷��̵� ����!"), true);
				}
			} else if (attr_level == 24) {
				if (chance < 10) {
					item.setAttrEnchantLevel(25);
					pc.sendPackets(new S_SystemMessage("\\fW������ ���� ��ȭ  ���׷��̵� ����!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("\\fY������ ���� ��ȭ  ���׷��̵� ����!"), true);
				}
			} else if (attr_level == 25) {
				if (chance < 5) {
					item.setAttrEnchantLevel(26);
					pc.sendPackets(new S_SystemMessage("\\fW������ ���� ��ȭ  ���׷��̵� ����!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("\\fY������ ���� ��ȭ  ���׷��̵� ����!"), true);
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
		}

		if (item_id == 5000140) {
			if (attr_level == 0) {
				if (chance < 20) {
					item.setAttrEnchantLevel(27);
					pc.sendPackets(new S_SystemMessage("\\fWǳ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("\\fYǳ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				}
			} else if (attr_level == 27) {
				if (chance < 10) {
					item.setAttrEnchantLevel(28);
					pc.sendPackets(new S_SystemMessage("\\fWǳ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("\\fYǳ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				}
			} else if (attr_level == 28) {
				if (chance < 5) {
					item.setAttrEnchantLevel(29);
					pc.sendPackets(new S_SystemMessage("\\fWǳ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("\\fYǳ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
		}

		if (item_id == 5000141) {
			if (attr_level == 0) {
				if (chance < 20) {
					item.setAttrEnchantLevel(30);
					pc.sendPackets(new S_SystemMessage("\\fWȭ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("\\fYȭ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				}
			} else if (attr_level == 30) {
				if (chance < 10) {
					item.setAttrEnchantLevel(31);
					pc.sendPackets(new S_SystemMessage("\\fWȭ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("\\fYȭ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				}
			} else if (attr_level == 31) {
				if (chance < 5) {
					item.setAttrEnchantLevel(32);
					pc.sendPackets(new S_SystemMessage("\\fWȭ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				} else {
					pc.sendPackets(new S_SystemMessage("\\fYȭ���� ���� ��ȭ  ���׷��̵� ����!"), true);
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
		}

		if (bEquipped) {
			pc.getInventory().setEquipped(item, true);
		}
		pc.getInventory().consumeItem(item_id, 1);
		pc.getInventory().updateItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
		pc.getInventory().updateItem(item, L1PcInventory.COL_IS_ID);
		pc.getInventory().saveItem(item, L1PcInventory.COL_ATTRENCHANTLVL);
		pc.getInventory().saveItem(item, L1PcInventory.COL_IS_ID);
		pc.saveInventory();
	}
}
