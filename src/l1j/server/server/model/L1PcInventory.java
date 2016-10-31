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
package l1j.server.server.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_AddItem;
import l1j.server.server.serverpackets.S_AddItemDoll;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_DeleteInventoryItem;
import l1j.server.server.serverpackets.S_InvList;
import l1j.server.server.serverpackets.S_ItemColor;
import l1j.server.server.serverpackets.S_ItemName;
import l1j.server.server.serverpackets.S_ItemStatus;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SabuBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.storage.CharactersItemStorage;
import l1j.server.server.templates.L1Armor;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Weapon;

public class L1PcInventory extends L1Inventory {

	/** ��¥ �� �ð� ��� **/
	Calendar rightNow = Calendar.getInstance();
	int day = rightNow.get(Calendar.DATE);
	int hour = rightNow.get(Calendar.HOUR);
	int min = rightNow.get(Calendar.MINUTE);
	int sec = rightNow.get(Calendar.SECOND);
	int year = rightNow.get(Calendar.YEAR);
	int month = rightNow.get(Calendar.MONTH) + 1;
	String totime = "[" + year + ":" + month + ":" + day + "]";
	String totime1 = "[" + hour + ":" + min + ":" + sec + "]";
	String date = +year + "_" + month + "_" + day;
	private static final long serialVersionUID = 1L;

	private static Logger _log = Logger
			.getLogger(L1PcInventory.class.getName());

	private static final int MAX_SIZE = 180;

	private final L1PcInstance _owner;

	private int _arrowId;

	private int _stingId;

	private long timeVisible = 0;
	private long timeVisibleDelay = 3000;

	public L1PcInventory(L1PcInstance owner) {
		_owner = owner;
		_arrowId = 0;
		_stingId = 0;
	}

	public L1PcInstance getOwner() {
		return _owner;
	}

	/*
	 * // 240�ܰ��� ���� ���� public int getWeight240() { if(calcWeightpercent()>=83){
	 * return 216; } if(calcWeightpercent()>=50){ return 120; }else{ return 0; }
	 * }
	 */
	public int calcWeightpercent() {
		if (Config.RATE_WEIGHT_LIMIT != 0) {
			int WeightRatio = 0;
			int maxWeight = _owner.getMaxWeight();
			WeightRatio = 100 * getWeight() / maxWeight;
			if (getSize() > 175) {
				return 100;
			}
			return WeightRatio;
		} else { // ����Ʈ ����Ʈ�� 0�̶�� �߷� �׻� 0
			return 0;
		}
	}

	/*
	 * public boolean calcWeight50(int weight){ if(getSize() >= MAX_SIZE){
	 * return false; }
	 * 
	 * if (Config.RATE_WEIGHT_LIMIT != 0) { int maxWeight =
	 * _owner.getMaxWeight(); if (weight > maxWeight) { return false; } else {
	 * int temp = (maxWeight/100) * 50; if(weight >= temp){ return false; }else{
	 * return true; } double wpTemp = (weight * 100 / maxWeight) * 240.00 /
	 * 100.00; DecimalFormat df = new DecimalFormat("00.##"); df.format(wpTemp);
	 * wpTemp = Math.round(wpTemp); } } else { // ����Ʈ ����Ʈ�� 0�̶�� �߷� �׻� 0 return
	 * true; } } public boolean calcWeight83(int weight){ if(getSize() >=
	 * MAX_SIZE){ return false; }
	 * 
	 * if (Config.RATE_WEIGHT_LIMIT != 0) { int maxWeight =
	 * _owner.getMaxWeight(); if (weight > maxWeight) { return false; } else {
	 * int temp = (maxWeight/100) * 83; if(weight >= temp){ return false; }else{
	 * return true; } double wpTemp = (weight * 100 / maxWeight) * 240.00 /
	 * 100.00; DecimalFormat df = new DecimalFormat("00.##"); df.format(wpTemp);
	 * wpTemp = Math.round(wpTemp); } } else { // ����Ʈ ����Ʈ�� 0�̶�� �߷� �׻� 0 return
	 * true; } }
	 * 
	 * // 240�ܰ��� ���Ը� ����Ѵ� public int calcWeight240(int weight) { int weight6000
	 * = 0; if(getSize() >= MAX_SIZE){ return 6000; } if
	 * (Config.RATE_WEIGHT_LIMIT != 0) { int maxWeight = _owner.getMaxWeight();
	 * if (weight > maxWeight) { weight6000 = 6000; } else { double wpTemp =
	 * (weight * 100 / maxWeight) * 240.00 / 100.00; DecimalFormat df = new
	 * DecimalFormat("00.##"); df.format(wpTemp); wpTemp = Math.round(wpTemp);
	 * weight6000 = maxWeight; } } else { // ����Ʈ ����Ʈ�� 0�̶�� �߷� �׻� 0 weight6000 =
	 * 0; } return weight6000; }
	 */

	@Override
	public int checkAddItem(L1ItemInstance item, int count) {
		return checkAddItem(item, count, true);
	}

	public int checkAddItem(L1ItemInstance item, int count, boolean message) {
		if (item == null) {
			return -1;
		}
		if (getSize() > MAX_SIZE
				|| (getSize() == MAX_SIZE && (!item.isStackable() || !checkItem(item
						.getItem().getItemId())))) {
			if (message) {
				sendOverMessage(263);
			}
			return SIZE_OVER;
		}

		int weight = getWeight() + item.getItem().getWeight() * count;
		if (weight < 0 || (item.getItem().getWeight() * count) < 0) {
			if (message) {
				sendOverMessage(82); // �������� �ʹ� ���ſ�, �� �̻� ���� �� �����ϴ�.
			}
			return WEIGHT_OVER;
		}
		if (calcWeightpercent() >= 100) {
			if (message) {
				sendOverMessage(82); // �������� �ʹ� ���ſ�, �� �̻� ���� �� �����ϴ�.
			}
			return WEIGHT_OVER;
		}

		L1ItemInstance itemExist = findItemId(item.getItemId());
		if (itemExist != null && (itemExist.getCount() + count) > MAX_AMOUNT) {
			if (message) {

				getOwner().sendPackets(
						new S_SystemMessage("�����ϰ� �ִ� �Ƶ����� 20���� �ʰ��ϰ� �˴ϴ�.")); // \f1%0��%4%1%3%2
			}
			return AMOUNT_OVER;
		}

		return OK;
	}

	public void sendOverMessage(int message_id) {

		_owner.sendPackets(new S_ServerMessage(message_id), true);
	}

	// DB�� character_items�� ����
	@Override
	public void loadItems() {
		try {
			CharactersItemStorage storage = CharactersItemStorage.create();
			int weapon = 0;

			for (L1ItemInstance item : storage.loadItems(_owner.getId())) {
				_items.add(item);
				if (item.isEquipped()) {
					item.setEquipped(false);
					if (item.getItem().getType2() == 1) {
						weapon++;
					}
					if (weapon == 2) {
						setEquipped(item, true, true, false, true);
					} else {
						setEquipped(item, true, true, false);
					}
				}
				L1World.getInstance().storeObject(item);
			}
			_owner.sendPackets(new S_InvList(_owner), true);

			/*
			 * for (L1ItemInstance item : storage.loadItems(_owner.getId())) {
			 * _items.add(item);
			 * 
			 * }
			 * 
			 * 
			 * 
			 * for (L1ItemInstance item : _items) { if (item.isEquipped()) {
			 * 
			 * if(item.getItem().getType2()==1){ weapon++; }
			 * 
			 * item.setEquipped(false); if(weapon == 2){ setEquipped(item, true,
			 * true, false, true); }else{ setEquipped(item, true, true, false);
			 * }
			 * 
			 * } L1World.getInstance().storeObject(item); }
			 * _owner.sendPackets(new S_InvList(_owner), true);
			 */
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85,
			0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c, 0x8d, 0x8e, 0x8f, 0x90,
			0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b,
			0x9c, 0x9d, 0x9e, 0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6,
			0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0, 0xb1,
			0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc,
			0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2, 0xc3, 0xc4, 0xc5, 0xc6, 0xc7,
			0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2,
			0xd3, 0xd4, 0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd,
			0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6, 0xe7, 0xe8,
			0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xf0, 0xf1, 0xf2, 0xf3,
			0xf4, 0xf5, 0xf6, 0xf7, 0xf8, 0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe,
			0xff };

	private String byteWrite(long value) {
		long temp = value / 128;
		StringBuffer sb = new StringBuffer();
		if (temp > 0) {
			sb.append((byte) hextable[(int) value % 128]);
			while (temp >= 128) {
				sb.append((byte) hextable[(int) temp % 128]);
				temp = temp / 128;
			}
			if (temp > 0)
				sb.append((int) temp);
		} else {
			if (value == 0) {
				sb.append(0);
			} else {
				sb.append((byte) hextable[(int) value]);
				sb.append(0);
			}
		}
		return sb.toString();
	}

	// DB�� character_items�� ���
	@Override
	public void insertItem(L1ItemInstance item) {
		if (item == null)
			return;

		if (_owner instanceof L1RobotInstance) {
			L1World.getInstance().removeObject(item);
			_items.remove(item);
			return;
		}

		item.setEncobjid(byteWrite(item.getId()));

		if (item.getItem().getItemId() == 6018) {
			int tempcount = item.getCount();
			if (tempcount > 99) {
				_owner.sendPackets(new S_PacketBox(84,
						("���� ������ ���簡 ��������, ���󿡰� �����ʽÿ�.")), true);
			}
		} else if (item.getItemId() == 60501) {
			int tempcount = item.getCount();
			int ���� = countItems(60500);
			int ���� = countItems(60499);

			// int maxcount = Math.max(����, ����)*5;
			int maxcount = (���� + ����) * 5;

			if (maxcount > 50) {
				maxcount = 50;
			}

			int msgcount = maxcount - 5;

			int step = 0;
			if (msgcount == 0) {
				step = tempcount;
			} else {
				step = tempcount % 5;
			}

			if (msgcount < tempcount) {
				if (step == 1) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ����� �������ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				} else if (step == 2) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ����� �� �ȳ��ҽ��ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				} else if (step == 3) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ���� �Ұ� �ֽ��ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				} else if (step == 4) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����) 1���� �Ƿ��� ���� 5���� ��ȣ�� �� �ֽ��ϴ�. ("
									+ tempcount + "/" + maxcount + ")")));
				} else {
					_owner.sendPackets(new S_PacketBox(84,
							("�Ƿ��� ������ �ʹ� ���� ������! ��Ų���� ��������. (" + tempcount
									+ "/" + maxcount + ")")));
					_owner.sendPackets(new S_SystemMessage(
							"10�� �� ������ ���� �̵� �˴ϴ�."));
				}
			} else {
				if (step == 0) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ��ȣ�� �޽��ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				}
			}

			/*
			 * $4294������ ����(����)�� ��ȣ�� �޽��ϴ�. (%d/%d) $4295������ ����(����)�� ����� ��
			 * �ȳ��ҽ��ϴ�. (%d/%d) $4296������ ����(����)�� ���� �Ұ� �ֽ��ϴ�. (%d/%d) $4297������
			 * ����(����) 1���� �Ƿ��� ���� 5���� ��ȣ�� �� �ֽ��ϴ�. (%d/%d) $4298�Ƿ��� ������ �ʹ� ����
			 * ������! ��Ų���� ��������. (%d/%d)
			 * 
			 * $4303������ ����(����)�� ����� �������ϴ�. (%d/%d)
			 */
		}

		_owner.sendPackets(new S_AddItem(item));
		if (item.getItem().getWeight() != 0) {
			_owner.sendPackets(new S_NewCreateItem("����", _owner));
			// _owner.sendPackets(new S_PacketBox(S_PacketBox.WEIGHT,
			// getWeight240()), true);
		}
		try {
			CharactersItemStorage storage = CharactersItemStorage.create();
			storage.storeItem(_owner, item);

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void insertItem(L1ItemInstance item, boolean ok) {
		if (item == null)
			return;

		if (_owner instanceof L1RobotInstance) {
			L1World.getInstance().removeObject(item);
			_items.remove(item);
			return;
		}

		item.setEncobjid(byteWrite(item.getId()));

		if (item.getItem().getItemId() == 6018) {
			int tempcount = item.getCount();
			if (tempcount > 99) {
				_owner.sendPackets(new S_PacketBox(84,
						("���� ������ ���簡 ��������, ���󿡰� �����ʽÿ�.")), true);
			}
		} else if (item.getItemId() == 60501) {
			int tempcount = item.getCount();
			int ���� = countItems(60500);
			int ���� = countItems(60499);

			// int maxcount = Math.max(����, ����)*5;
			int maxcount = (���� + ����) * 5;

			if (maxcount > 50) {
				maxcount = 50;
			}

			int msgcount = maxcount - 5;

			int step = 0;
			if (msgcount == 0) {
				step = tempcount;
			} else {
				step = tempcount % 5;
			}

			if (msgcount < tempcount) {
				if (step == 1) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ����� �������ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				} else if (step == 2) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ����� �� �ȳ��ҽ��ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				} else if (step == 3) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ���� �Ұ� �ֽ��ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				} else if (step == 4) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����) 1���� �Ƿ��� ���� 5���� ��ȣ�� �� �ֽ��ϴ�. ("
									+ tempcount + "/" + maxcount + ")")));
				} else {
					_owner.sendPackets(new S_PacketBox(84,
							("�Ƿ��� ������ �ʹ� ���� ������! ��Ų���� ��������. (" + tempcount
									+ "/" + maxcount + ")")));
					_owner.sendPackets(new S_SystemMessage(
							"10�� �� ������ ���� �̵� �˴ϴ�."));
				}
			} else {
				if (step == 0) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ��ȣ�� �޽��ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				}
			}

			/*
			 * $4294������ ����(����)�� ��ȣ�� �޽��ϴ�. (%d/%d) $4295������ ����(����)�� ����� ��
			 * �ȳ��ҽ��ϴ�. (%d/%d) $4296������ ����(����)�� ���� �Ұ� �ֽ��ϴ�. (%d/%d) $4297������
			 * ����(����) 1���� �Ƿ��� ���� 5���� ��ȣ�� �� �ֽ��ϴ�. (%d/%d) $4298�Ƿ��� ������ �ʹ� ����
			 * ������! ��Ų���� ��������. (%d/%d)
			 * 
			 * $4303������ ����(����)�� ����� �������ϴ�. (%d/%d)
			 */
		}

		_owner.sendPackets(new S_AddItemDoll(item));
		if (item.getItem().getWeight() != 0) {
			_owner.sendPackets(new S_NewCreateItem("����", _owner));
			// _owner.sendPackets(new S_PacketBox(S_PacketBox.WEIGHT,
			// getWeight240()), true);
		}
		try {
			CharactersItemStorage storage = CharactersItemStorage.create();
			storage.storeItem(_owner, item);

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	public static final int COL_DEMONBONGIN = 16384;

	public static final int COL_STEPENCHANTLVL = 2048;

	public static final int COL_ATTRENCHANTLVL = 1024;
	
	public static final int COL_ATTRENCHANTLVL1 = 10241;

	public static final int COL_BLESS = 512;

	public static final int COL_REMAINING_TIME = 256;

	public static final int COL_CHARGE_COUNT = 128;

	public static final int COL_ITEMID = 64;

	public static final int COL_DELAY_EFFECT = 32;

	public static final int COL_COUNT = 16;

	public static final int COL_EQUIPPED = 8;

	public static final int COL_ENCHANTLVL = 4;

	public static final int COL_IS_ID = 2;

	public static final int COL_DURABILITY = 1;

	public static final int COL_regist = 4096;

	public static final int COL_ENDTIME = 8192;

	@Override
	public void updateItem(L1ItemInstance item) {
		if (item.getItem().getItemId() == 6018) {
			int tempcount = item.getCount();
			if (tempcount > 99) {
				_owner.sendPackets(new S_PacketBox(84,
						("���� ������ ���簡 ��������, ���󿡰� �����ʽÿ�.")), true);
			}
		} else if (item.getItemId() == 60501) {
			int tempcount = item.getCount();
			int ���� = countItems(60500);
			int ���� = countItems(60499);

			// int maxcount = Math.max(����, ����)*5;
			int maxcount = (���� + ����) * 5;

			if (maxcount > 50) {
				maxcount = 50;
			}

			int msgcount = maxcount - 5;
			int step = 0;
			if (msgcount == 0) {
				step = tempcount;
			} else {
				step = tempcount % 5;
			}
			if (msgcount < tempcount) {
				if (step == 1) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ����� �������ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				} else if (step == 2) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ����� �� �ȳ��ҽ��ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				} else if (step == 3) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ���� �Ұ� �ֽ��ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				} else if (step == 4) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����) 1���� �Ƿ��� ���� 5���� ��ȣ�� �� �ֽ��ϴ�. ("
									+ tempcount + "/" + maxcount + ")")));
				} else {
					_owner.sendPackets(new S_PacketBox(84,
							("�Ƿ��� ������ �ʹ� ���� ������! ��Ų���� ��������. (" + tempcount
									+ "/" + maxcount + ")")));
					_owner.sendPackets(new S_SystemMessage(
							"10�� �� ������ ���� �̵� �˴ϴ�."));
				}
			} else {
				if (step == 0) {
					_owner.sendPackets(new S_PacketBox(84,
							("������ ����(����)�� ��ȣ�� �޽��ϴ�. (" + tempcount + "/"
									+ maxcount + ")")));
				}
			}

		}

		updateItem(item, COL_COUNT);
		if (item.getItem().isToBeSavedAtOnce()) {
			saveItem(item, COL_COUNT);
		}
	}

	/**
	 * ��ϳ��� ������ ���¸� �����Ѵ�.
	 * 
	 * @param item
	 *            - ���� ����� ������
	 * @param column
	 *            - �����ϴ� �������ͽ��� ����
	 */
	@Override
	public void updateItem(L1ItemInstance item, int column) {
		if (column >= COL_DEMONBONGIN) {
			column -= COL_DEMONBONGIN;
		}
		if (column >= COL_ENDTIME) {
			_owner.sendPackets(new S_ItemName(item), true);
			column -= COL_ENDTIME;
		}
		if (column >= COL_regist) {
			_owner.sendPackets(new S_ItemStatus(item), true);
			column -= COL_regist;
		}
		if (column >= COL_ATTRENCHANTLVL) {
			_owner.sendPackets(new S_ItemName(item), true);
			column -= COL_ATTRENCHANTLVL;
		}
		if (column >= COL_ATTRENCHANTLVL1) {
			_owner.sendPackets(new S_ItemName(item), true);
			column -= COL_ATTRENCHANTLVL1;
		}
		if (column >= COL_BLESS) {
			_owner.sendPackets(new S_ItemColor(item), true);
			column -= COL_BLESS;
		}
		if (column >= COL_REMAINING_TIME) { // ��� ������ ���� �ð�
			_owner.sendPackets(new S_ItemName(item), true);
			column -= COL_REMAINING_TIME;
		}
		if (column >= COL_CHARGE_COUNT) { // ��� ������ Ƚ��
			_owner.sendPackets(new S_ItemName(item), true);
			// _owner.sendPackets(new S_ItemAmount(item));
			column -= COL_CHARGE_COUNT;
		}
		if (column >= COL_ITEMID) { // �ٸ� �������� �Ǵ� ���(�������� �������� �� ��)
			_owner.sendPackets(new S_ItemStatus(item), true);
			_owner.sendPackets(new S_ItemColor(item), true);
			_owner.sendPackets(new S_NewCreateItem("����", _owner));
			/*
			 * _owner.sendPackets(new S_PacketBox( S_PacketBox.WEIGHT,
			 * getWeight240()), true);
			 */
			column -= COL_ITEMID;
		}
		if (column >= COL_DELAY_EFFECT) {
			column -= COL_DELAY_EFFECT;
		}
		if (column >= COL_COUNT) {// ī��Ʈ
			int weight = item.getWeight();
			if (weight != item.getLastWeight()) {
				item.setLastWeight(weight);
				// _owner.sendPackets(new S_ItemStatus(item));
			} else {
				_owner.sendPackets(new S_ItemName(item), true);
			}
			_owner.sendPackets(new S_ItemStatus(item), true);
			if (item.getItem().getWeight() != 0) {
				_owner.sendPackets(new S_NewCreateItem("����", _owner));
				// _owner.sendPackets(new
				// S_NewCreateItem(0x01e5,20,0,0,"����",0,_owner));
				// ���԰� ������ �ʾ��� ��� �׳� ������ �ȴ�.
			}
			column -= COL_COUNT;
		}
		if (column >= COL_EQUIPPED) {
			_owner.sendPackets(new S_ItemName(item), true);
			column -= COL_EQUIPPED;
		}
		if (column >= COL_ENCHANTLVL) {
			_owner.sendPackets(new S_ItemStatus(item), true);
			column -= COL_ENCHANTLVL;
		}
		if (column >= COL_IS_ID) {
			_owner.sendPackets(new S_ItemStatus(item), true);
			_owner.sendPackets(new S_ItemColor(item), true);
			column -= COL_IS_ID;
		}
		if (column >= COL_DURABILITY) {
			_owner.sendPackets(new S_ItemStatus(item), true);
			if (_owner.getWeapon() != null && _owner.getWeapon() == item)
				_owner.sendPackets(
						new S_PacketBox(S_PacketBox.����ջ󸶿콺, item
								.get_durability()), true);
			column -= COL_DURABILITY;
		}

	}

	/**
	 * ��ϳ��� ������ ���¸� DB�� �����Ѵ�.
	 * 
	 * @param item
	 *            - ���� ����� ������
	 * @param column
	 *            - �����ϴ� �������ͽ��� ����
	 */
	public void saveItem(L1ItemInstance item, int column) {
		if (column == 0) {
			return;
		}

		try {
			CharactersItemStorage storage = CharactersItemStorage.create();
			// System.out.println(column);
			if (column >= COL_DEMONBONGIN) {
				storage.updateItemDemonBongin(item);
				column -= COL_DEMONBONGIN;
			}
			if (column >= COL_ENDTIME) {
				storage.updateItemEndTime(item);
				column -= COL_ENDTIME;
			}
			if (column >= COL_regist) {
				// System.out.println("����");
				storage.updateItemRegistLevel(item);
				column -= COL_regist;
			}
			if (column >= COL_STEPENCHANTLVL) {
				storage.updateItemStepEnchantLevel(item);
				column -= COL_STEPENCHANTLVL;
			}
			if (column >= COL_ATTRENCHANTLVL) {
				storage.updateItemAttrEnchantLevel(item);
				column -= COL_ATTRENCHANTLVL;
			}
			if (column >= COL_ATTRENCHANTLVL1) {
				storage.updateItemAttrEnchantLevel(item);
				column -= COL_ATTRENCHANTLVL1;
			}
			if (column >= COL_BLESS) {
				storage.updateItemBless(item);
				column -= COL_BLESS;
			}
			if (column >= COL_REMAINING_TIME) {
				storage.updateItemRemainingTime(item);
				column -= COL_REMAINING_TIME;
			}
			if (column >= COL_CHARGE_COUNT) {
				storage.updateItemChargeCount(item);
				column -= COL_CHARGE_COUNT;
			}
			if (column >= COL_ITEMID) {
				storage.updateItemId(item);
				column -= COL_ITEMID;
			}
			if (column >= COL_DELAY_EFFECT) {
				storage.updateItemDelayEffect(item);
				column -= COL_DELAY_EFFECT;
			}
			if (column >= COL_COUNT) {
				storage.updateItemCount(item);
				column -= COL_COUNT;
			}
			if (column >= COL_EQUIPPED) {
				storage.updateItemEquipped(item);
				column -= COL_EQUIPPED;
			}
			if (column >= COL_ENCHANTLVL) {
				storage.updateItemEnchantLevel(item);
				column -= COL_ENCHANTLVL;
			}
			if (column >= COL_IS_ID) {
				storage.updateItemIdentified(item);
				column -= COL_IS_ID;
			}
			if (column >= COL_DURABILITY) {
				storage.updateItemDurability(item);
				column -= COL_DURABILITY;
			}

		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	// DB�� character_items�κ��� ����
	@Override
	public void deleteItem(L1ItemInstance item) {
		try {
			CharactersItemStorage storage = CharactersItemStorage.create();

			storage.deleteItem(item);
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
		if (item.isEquipped()) {
			setEquipped(item, false);
		}
		if (_owner.getDollList().size() > 0) {
			for (L1DollInstance doll : _owner.getDollList()) {
				if (doll.getItemObjId() == item.getId())
					doll.deleteDoll();
			}
		}
		_owner.sendPackets(new S_DeleteInventoryItem(item), true);
		_items.remove(item);
		if (item.getItem().getWeight() != 0) {
			_owner.sendPackets(new S_NewCreateItem("����", _owner));
			// _owner.sendPackets(new S_PacketBox(S_PacketBox.WEIGHT,
			// getWeight240()), true);
		}
	}

	public void checkEndTime() {
		L1ItemInstance item = null;
		L1ItemInstance[] li = _items.toArray(new L1ItemInstance[_items.size()]);
		for (Object itemObject : li) {
			item = (L1ItemInstance) itemObject;
			if (item.getEndTime() == null) {
				if (item.getItemId() == 60319) {
					removeItem(item);
				} else if ((item.getItemId() >= 21125 && item.getItemId() <= 21136)
						|| item.getItemId() == 430003
						|| item.getItemId() == 430505
						|| item.getItemId() == 430506
						|| item.getItemId() == 41915
						|| item.getItemId() == 5000034) {
					Timestamp deleteTime = null;
					deleteTime = new Timestamp(System.currentTimeMillis()
							+ (3600000 * 24 * 7));// 7��
					item.setEndTime(deleteTime);
				}
				continue;
			}
			if (item.getEndTime().getTime() < System.currentTimeMillis()) {
				if (item.isEquipped()) {
					setEquipped(item, false, false, false);
				}
				if (item.getItemId() >= 21125 && item.getItemId() <= 21136) {
					if (item.getBless() == 0 && item.getItemId() < 21131) {
						createNewItem(120085, 1, item.getEnchantLevel());
					} else
						createNewItem(
								item.getItemId() >= 21131 ? 20084 : 20085, 1,
								item.getEnchantLevel());
				} else if (item.getItemId() >= 21139
						&& item.getItemId() <= 21156) {
					if (item.getBless() == 0 && item.getItemId() < 21148) {
						createNewItem(120085, 1, item.getEnchantLevel());
					} else
						createNewItem(
								item.getItemId() >= 21148 ? 20084 : 20085, 1,
								item.getEnchantLevel());
				}

				if (_owner.getDollListSize() > 0) {
					L1DollInstance ���� = null;
					for (Object ����������Ʈ : _owner.getDollList()) {
						if (����������Ʈ instanceof L1DollInstance) {
							���� = (L1DollInstance) ����������Ʈ;
							if (item.getId() == ����.getItemObjId()) {
								����.deleteDoll();
								_owner.sendPackets(new S_SkillIconGFX(56, 0),
										true);
								_owner.sendPackets(new S_OwnCharStatus(_owner),
										true);
								_owner.sendPackets(
										new S_PacketBox(S_PacketBox.char_ER,
												_owner.get_PlusEr()), true);
							}
						}
					}
				}

				removeItem(item);
			}
		}
		li = null;
	}

	private boolean createNewItem(int item_id, int count, int EnchantLevel) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {

			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(true);
			if (checkAddItem(item, count) == L1Inventory.OK) {
				storeItem(item);
			} else { // ���� �� ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance()
						.getInventory(_owner.getX(), _owner.getY(),
								_owner.getMapId()).storeItem(item);
			}
			_owner.sendPackets(new S_ServerMessage(403, item.getLogName()),
					true); // %0�� �տ� �־����ϴ�.
			return true;
		} else {
			return false;
		}
	}

	public static int changeval(int type, int cnt, int val, int val2) {
		int temp = 0;

		if (type == 2) {
			switch (val) {
			case 1:
				temp = 1;
				break;// ����
			case 2:
				temp = 2;
				break;// ����
			case 3:
				temp = 3;
				break;// Ƽ
			case 4:
				temp = 4;
				break;// ����

			case 15:
				temp = 5;
				break;// ����~
			case 6:
				temp = 6;
				break;// ����
			case 5:
				temp = 7;
				break;// �尩
			case 7:
			case 13:
				temp = 8;
				break;// ����
			case 8:
				temp = 11;
				break;

			case 14:
				temp = 23;
				break;

			case 9:// ����
				if (cnt == 0) {
					temp = 19;
				} else if (cnt == 1) {
					temp = 20;
				} else if (cnt == 2) {
					temp = 21;
				} else if (cnt == 3) {
					temp = 22;
				}
				break;

			case 10:
				temp = 12;
				break;

			case 12:
				if (cnt == 0) {
					temp = 13;
				} else if (cnt == 1) {
					temp = 26;
				}
				break;
			case 16:
				temp = 28;
				break;
			default:
				break;
			}
		} else if (type == 1) {
			temp = 9; // ����
		}
		return temp;
	}

	static int[] earring = { 13, 26 };

	// �������� ���� Ż����Ų��(L1ItemInstance�� ����, ����ġ�� ����, character_items�� ����, ��Ŷ �۽ű���
	// ����)
	public void setEquipped(L1ItemInstance item, boolean equipped) {
		setEquipped(item, equipped, false, false);
	}

	public void setEquipped(L1ItemInstance item, boolean equipped,
			boolean loaded, boolean changeWeapon) {
		setEquipped(item, equipped, loaded, changeWeapon, false);
	}

	public void setEquipped(L1ItemInstance item, boolean equipped,
			boolean loaded, boolean changeWeapon, boolean doubleweapon) {
		if (item.isEquipped() != equipped) {
			L1Item temp = item.getItem();
			if (equipped) {
				if (temp.getItemId() == 20077 || temp.getItemId() == 20062
						|| temp.getItemId() == 120077) {
					if (System.currentTimeMillis() - timeVisible < timeVisibleDelay) {
						return;
					}
				}

				int min = 0;
				int max = 0;
				if (item.getItem().getType2() == 1) {
					min = ((L1Weapon) item.getItem()).getMinLevel();
					max = ((L1Weapon) item.getItem()).getMaxLevel();
				} else if (item.getItem().getType2() == 2) {
					min = ((L1Armor) item.getItem()).getMinLevel();
					max = ((L1Armor) item.getItem()).getMaxLevel();
				}
				if ((min != 0 && min > _owner.getLevel())) {
					if (!loaded)
						_owner.sendPackets(
								new S_ServerMessage(318, String.valueOf(min)),
								true);
					return;
				} else if (max != 0 && max < _owner.getLevel()) {
					if (!loaded) {
						if (max < 50) {
							_owner.sendPackets(new S_PacketBox(
									S_PacketBox.MSG_LEVEL_OVER, max), true);
						} else {
							_owner.sendPackets(new S_SystemMessage("�� �������� "
									+ max + "���� ���ϸ� ����� �� �ֽ��ϴ�. "), true);
						}
					}
					return;
				}

				int cnt = 0;
				if (item.getItem().getType2() == 2
						&& item.getItem().getType() == 9) {
					cnt = getTypeEquipped(2, 9);
					for (int i = 19; i <= 22; i++) {
						boolean ck = false;
						for (L1ItemInstance ring : getRingEquipped()) {
							if (ring == null)
								continue;
							if (i == ring.getRSN()) {
								ck = true;
								break;
							}
						}
						if (!ck) {
							item.setRSN(i);
							break;
						}
					}
					if (!loaded) {
						_owner.sendPackets(new S_SabuBox(S_SabuBox.�������������԰���,
								item.getId(), item.getRSN(), true));
					}
				} else if (item.getItem().getType2() == 2
						&& item.getItem().getType() == 12) {
					for (int i : earring) {
						boolean ck = false;
						for (L1ItemInstance earring : getEarringEquipped()) {
							if (earring == null)
								continue;
							if (i == earring.getRSN()) {
								ck = true;
								break;
							}
						}
						if (!ck) {
							item.setRSN(i);
							break;
						}
					}
					if (!loaded) {
						int tt = item.getRSN();
						if (doubleweapon) {
							tt = 8;
						}
						_owner.sendPackets(new S_SabuBox(S_SabuBox.�������������԰���,
								item.getId(), tt, true));

					}
				} else {
					if (!loaded) {
						int tt = changeval(item.getItem().getType2(), cnt, item
								.getItem().getType(), item.getItem().getType1());
						if (doubleweapon) {
							tt = 8;
						}
						_owner.sendPackets(new S_SabuBox(S_SabuBox.�������������԰���,
								item.getId(), tt, true));
					}
				}
				item.setEquipped(true);
				item.setEnchantWA(_owner);
				_owner.getEquipSlot().set(item, doubleweapon, loaded);
			} else {
				if (!loaded) {
					if (temp.getItemId() == 20077 || temp.getItemId() == 20062
							|| temp.getItemId() == 120077) {
						if (_owner.isInvisble()) {
							_owner.delInvis();
							return;
						}
						timeVisible = System.currentTimeMillis();
					}
					int cnt = 0;
					if (item.getItem().getType2() == 2
							&& (item.getItem().getType() == 9 || item.getItem()
									.getType() == 12)) {
						_owner.sendPackets(new S_SabuBox(S_SabuBox.�������������԰���,
								item.getId(), item.getRSN(), false));
						item.setRSN(0);
					} else {
						int tt = changeval(item.getItem().getType2(), cnt, item
								.getItem().getType(), item.getItem().getType1());
						if (doubleweapon) {
							tt = 8;
						}
						_owner.sendPackets(new S_SabuBox(S_SabuBox.�������������԰���,
								item.getId(), tt, false));
					}
				}
				item.setEquipped(false);
				item.setEnchantWA(null);
				_owner.getEquipSlot().remove(item, doubleweapon);
			}
			// System.out.println("123");
			if (!loaded) {
				// System.out.println("222222222222");
				_owner.setCurrentHp(_owner.getCurrentHp());
				_owner.setCurrentMp(_owner.getCurrentMp());
				updateItem(item, COL_EQUIPPED);
				_owner.sendPackets(new S_OwnCharStatus(_owner), true);
				if (temp.getType2() == 1) {
					if (changeWeapon == false) {
						_owner.sendPackets(new S_CharVisualUpdate(_owner), true);
						Broadcaster.broadcastPacket(_owner,
								new S_CharVisualUpdate(_owner), true);
					}
					// System.out.println("44444444444444");
					_owner.sendPackets(new S_PacketBox(
							S_PacketBox.WEAPON_RANGE, _owner, item, equipped),
							true);
				}
			} else if (temp.getType2() == 1) {
				// .out.println("3333333333");
				_owner.sendPackets(new S_PacketBox(S_PacketBox.WEAPON_RANGE,
						_owner, item, equipped), true);
			}

			/*
			 * if(temp.getItemId() == 21019){ for (Object itemObject : _items) {
			 * L1ItemInstance tt = (L1ItemInstance) itemObject; if
			 * (tt.getItem().getItemId() == 20423 || tt.getItem().getItemId() ==
			 * 20424 || tt.getItem().getItemId() == 20425) {
			 * _owner.sendPackets(new S_ItemStatus(tt, _owner), true); } } }else
			 * if(temp.getItemId() >= 20424 && temp.getItemId() <= 20425)
			 * _owner.sendPackets(new S_ItemStatus(item, _owner), true); else
			 * if(temp.getItemId() == 423020){ for (Object itemObject : _items)
			 * { L1ItemInstance tt = (L1ItemInstance) itemObject; if
			 * (tt.getItem().getItemId() == 423021 || tt.getItem().getItemId()
			 * == 423022 || tt.getItem().getItemId() == 423023) {
			 * _owner.sendPackets(new S_ItemStatus(tt, _owner), true); } } }else
			 * if(temp.getItemId() >= 423021 && temp.getItemId() <= 423023)
			 * _owner.sendPackets(new S_ItemStatus(item, _owner), true); else
			 * if(temp.getItemId() == 21176){ for (Object itemObject : _items) {
			 * L1ItemInstance tt = (L1ItemInstance) itemObject; if
			 * (tt.getItem().getItemId() == 21173 || tt.getItem().getItemId() ==
			 * 21174 || tt.getItem().getItemId() == 21175) {
			 * _owner.sendPackets(new S_ItemStatus(tt, _owner), true); } } }else
			 * if(temp.getItemId() >= 21173 && temp.getItemId() <= 21175)
			 * _owner.sendPackets(new S_ItemStatus(item, _owner), true);
			 */
		}
	}

	public boolean checkEquippedEnchant(int id, int enc) {
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getItemId() == id && item.isEquipped()) {
				if (item.getEnchantLevel() >= enc) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkEquipped(int id) {
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getItemId() == id && item.isEquipped()) {
				return true;
			}
		}
		return false;
	}

	public boolean checksetitem(L1ItemInstance[] check) {
		boolean no = false;
		L1ItemInstance item = null;
		for (Object itemObject : check) {
			item = (L1ItemInstance) itemObject;
			if (!item.isEquipped()) {
				return no;
			}
		}
		return true;
	}

	public L1ItemInstance checkEquippedItem(int id) {
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getItemId() == id && item.isEquipped()) {
				return item;
			}
		}
		return null;
	}

	public boolean checkEquipped(int[] ids) {
		for (int id : ids) {
			if (!checkEquipped(id)) {
				return false;
			}
		}
		return true;
	}

	public int getTypeEquipped(int type2, int type) {
		int equipeCount = 0;
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getType2() == type2
					&& item.getItem().getType() == type && item.isEquipped()) {
				equipeCount++;
			}
		}
		return equipeCount;
	}

	/**
	 * @return type2=1weapon 2armor enchant
	 */

	public int getItemEnchantCount(int type2, int enchant) {
		int cnt = 0;
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getType2() == type2
					&& item.getEnchantLevel() >= enchant) {
				cnt++;
			}
		}
		return cnt;
	}

	public int getGarderEquipped(int type2, int type, int gd) {
		int equipeCount = 0;
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getType2() == type2
					&& item.getItem().getType() == type
					&& item.getItem().getUseType() != gd && item.isEquipped()) {
				equipeCount++;
			}
		}
		return equipeCount;
	}

	public L1ItemInstance getItemEquipped(int type2, int type) {
		L1ItemInstance equipeitem = null;
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getType2() == type2
					&& item.getItem().getType() == type && item.isEquipped()) {
				equipeitem = item;
				break;
			}
		}
		return equipeitem;
	}

	public L1ItemInstance[] getRingEquipped() {
		L1ItemInstance equipeItem[] = new L1ItemInstance[4];
		int equipeCount = 0;
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getType2() == 2 && item.getItem().getType() == 9
					&& item.isEquipped()) {
				equipeItem[equipeCount] = item;
				equipeCount++;
				if (equipeCount == 4) {
					break;
				}
			}
		}
		return equipeItem;
	}

	public L1ItemInstance[] getEarringEquipped() {
		L1ItemInstance equipeItem[] = new L1ItemInstance[2];
		int equipeCount = 0;
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getType2() == 2
					&& item.getItem().getType() == 12 && item.isEquipped()) {
				equipeItem[equipeCount] = item;
				equipeCount++;
				if (equipeCount == 2) {
					break;
				}
			}
		}
		return equipeItem;
	}

	public void takeoffEquip(int polyid) {
		takeoffWeapon(polyid);
		takeoffArmor(polyid);
	}

	private void takeoffWeapon(int polyid) {
		if (_owner.getWeapon() == null) {
			return;
		}

		boolean takeoff = false;
		int weapon_type = _owner.getWeapon().getItem().getType();
		if (_owner.getSecondWeapon() != null) {
			weapon_type = 19;
		}
		takeoff = !L1PolyMorph.isEquipableWeapon(polyid, weapon_type);

		if (takeoff) {
			if (_owner.getSecondWeapon() != null) {
				setEquipped(_owner.getSecondWeapon(), false, false, false, true);
			}
			setEquipped(_owner.getWeapon(), false, false, false);
		}
	}

	private void takeoffArmor(int polyid) {
		L1ItemInstance armor = null;

		for (int type = 0; type <= 13; type++) {
			if (getTypeEquipped(2, type) != 0
					&& !L1PolyMorph.isEquipableArmor(polyid, type)) {
				if (type == 9) {
					armor = getItemEquipped(2, type);
					if (armor != null) {
						setEquipped(armor, false, false, false);
					}
					armor = getItemEquipped(2, type);
					if (armor != null) {
						setEquipped(armor, false, false, false);
					}
				} else {
					armor = getItemEquipped(2, type);
					if (armor != null) {
						setEquipped(armor, false, false, false);
					}
				}
			}
		}
	}

	public L1ItemInstance getArrow() {
		return getBullet(0);
	}

	public L1ItemInstance getSting() {
		return getBullet(15);
	}

	private L1ItemInstance getBullet(int type) {
		L1ItemInstance bullet;
		int priorityId = 0;
		if (type == 0) {
			priorityId = _arrowId;
		}
		if (type == 15) {
			priorityId = _stingId;
		}
		if (priorityId > 0) {
			bullet = findItemId(priorityId);
			if (bullet != null) {
				return bullet;
			} else {
				if (type == 0) {
					_arrowId = 0;
				}
				if (type == 15) {
					_stingId = 0;
				}
			}
		}

		for (Object itemObject : _items) {
			bullet = (L1ItemInstance) itemObject;
			if (bullet.getItem().getType() == type
					&& bullet.getItem().getType2() == 0) {
				if (type == 0) {
					_arrowId = bullet.getItem().getItemId();
				}
				if (type == 15) {
					_stingId = bullet.getItem().getItemId();
				}
				return bullet;
			}
		}
		return null;
	}

	public void setArrow(int id) {
		_arrowId = id;
	}

	public void setSting(int id) {
		_stingId = id;
	}

	public int hpRegenPerTick() {
		int hpr = 0;
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.isEquipped()) {
				hpr += item.getItem().get_addhpr();
			}
		}
		return hpr;
	}

	public int mpRegenPerTick() {
		int mpr = 0;
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			item = (L1ItemInstance) itemObject;
			if (item.isEquipped()) {
				mpr += item.getItem().get_addmpr();
			}
		}
		return mpr;
	}

	public L1ItemInstance CaoPenalty() {
		Random random = new Random(System.nanoTime());
		if (_items.size() <= 0) {
			return null;
		}
		int ck = 0;
		int rnd = 0;
		L1ItemInstance penaltyItem = null;
		boolean ok = false;
		while (!ok) {
			rnd = random.nextInt(_items.size());
			penaltyItem = _items.get(rnd);
			if (ck > 100) {
				ok = true;
				return null;
			}
			ck++;
			if (penaltyItem.getItem().getItemId() == L1ItemId.ADENA
					|| !penaltyItem.getItem().isTradable()) {
				continue;
			}
			if (penaltyItem.getItem().getItemId() == 40312
					|| penaltyItem.getItem().getItemId() == 49312) {
				continue;
			}

			/** 10��þ�̻� �� �ȶ����� **/
			if (penaltyItem.getItem().getType2() == 2
					&& penaltyItem.getEnchantLevel() >= 10) {
				continue;
			}

			/** �����, ���� ��� �ȵǰ� **/
			if (penaltyItem.getItemId() == 61 || penaltyItem.getItemId() == 86) {
				continue;
			}

			// ���ž�۵� ��� �ȵǰ�
			if (penaltyItem.getName().equalsIgnoreCase("���ž�� ")) {
				continue;
			}

			L1PetInstance pet = null;
			for (Object petObject : _owner.getPetList()) {
				if (petObject instanceof L1PetInstance) {
					pet = (L1PetInstance) petObject;
					if (penaltyItem.getId() == pet.getItemObjId()) {
						continue;
					}
				}
			}

			setEquipped(penaltyItem, false);
			ok = true;
			return penaltyItem;
		}
		return null;

	}

	/**
	 * ������ ���� (��æƮ ������ ����)
	 * 
	 * @param itemid
	 *            - ���ý� �ʿ��� �����ȣ
	 * 
	 * @param enchantLevel
	 *            - ���ý� �ʿ��� ������ ��æƮ����
	 */
	public boolean MakeDeleteEnchant(int itemid, int enchantLevel) {
		L1ItemInstance item = findItemId(itemid);
		if (item != null && item.getEnchantLevel() == enchantLevel) {
			removeItem(item, 1);
			return true;
		}
		return false;
	}

	/**
	 * ������ ���� (��æƮ ������ �˻�)
	 * 
	 * @param id
	 *            - ���ý� �ʿ��� �����ȣ
	 * 
	 * @param enchantLevel
	 *            - ���ý� �ʿ��� ������ ��æƮ ����
	 * 
	 */
	public boolean MakeCheckEnchant(int id, int enchantLevel) {
		L1ItemInstance item = findItemId(id);
		if (item != null && item.getEnchantLevel() == enchantLevel
				&& item.getCount() == 1) {
			return true;
		}
		return false;
	}

	public boolean CheckSellPrivateShopItem(int id, int enchantLevel, int attr,
			int bless) {
		L1ItemInstance item = findItemId(id);
		if (item != null && !item.isEquipped()
				&& item.getEnchantLevel() == enchantLevel
				&& item.getAttrEnchantLevel() == attr
				&& item.getBless() == bless) {
			return true;
		}
		return false;
	}

	public boolean CheckEncobj(String enc) {
		L1ItemInstance item = findEncobj(enc);
		if (item != null) {
			return true;
		}
		return false;
	}

	public boolean getEnchantEquipped(int itemid, int enchant) {
		L1ItemInstance item = null;
		for (Object itemObject : _items) {
			if (itemObject == null)
				continue;
			item = (L1ItemInstance) itemObject;
			if (item.getItem().getItemId() == itemid && item.isEquipped()
					&& item.getEnchantLevel() == enchant) {
				return true;
			}
		}
		return false;
	}
	
	public void receiveDamageArmor(L1PcInstance pc, L1ItemInstance item) {
		if (item == null) return;	
		int itemType = item.getItem().getType2();
		if(itemType != 2)return;		
		int currentDurability = item.get_durability();
		int maxDurability = item.getItem().get_ac();
		if(maxDurability < 0) maxDurability = -maxDurability;
		if(currentDurability >= maxDurability)return;
		item.set_durability(currentDurability + 1);
		updateItem(item, L1PcInventory.COL_DURABILITY);
		if(item.isEquipped() && itemType ==2){
			pc.getAC().addAc(1);
			pc.sendPackets(new S_OwnCharStatus(pc));
		}
	}
	
	public void recoveryDamageArmor(L1PcInstance pc, L1ItemInstance item) {
		if (item == null) {
			return;
		}
		int itemType = item.getItem().getType2();	
		if(item.isEquipped() && itemType ==2){
			pc.getAC().addAc(-1);
			pc.sendPackets(new S_OwnCharStatus(pc));
		}
	}
	
	public int getEnchantCount(int id){//��þ ����
		int cnt = 0;
		L1ItemInstance item = null;  
		for(Object itemObject : _items){
			item = (L1ItemInstance) itemObject;
			if(item.getItemId() == id){
				cnt = item.getEnchantLevel();
			}
		}
		return cnt;
	}
	
	public L1ItemInstance findEquippedItemId(int id) {
		for (L1ItemInstance item : _items) {
			if (item == null)
				continue;
			if ((item.getItem().getItemId() == id) && item.isEquipped()) {
				return item;
			}
		}
		return null;
	}

	public int checkEquippedcount(int id) {//����
		int equipeCount = 0;
		for (L1ItemInstance item : _items) {
			if (item.getItem().getItemId() == id && item.isEquipped()) {
				equipeCount++;
			}
			if(equipeCount > 1) break;
		}
		return equipeCount;
	}
	
	public L1ItemInstance findItemObjId(int id)
	{
		for (L1ItemInstance item : this._items)
		{
			if (item == null)
				continue;
			if (item.getId() == id)
			{
				return item;
			}
		}
		return null;
	}

    public boolean DeleteEnchant(int id, int enchant) {
        L1ItemInstance item = null;
        for (Object itemObject : _items) {
            item = (L1ItemInstance) itemObject;
            if (item.getItem().getItemId() == id && item.getEnchantLevel() == enchant) {
                removeItem(item, 1);
                return true;
            }
        }
        return false;
    }
	
	
}
