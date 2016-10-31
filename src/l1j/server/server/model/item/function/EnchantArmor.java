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

package l1j.server.server.model.item.function;

import java.util.Random;

import l1j.server.Config;
import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.datatables.ItemEnchantList;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Armor;
import l1j.server.server.templates.L1Item;

public class EnchantArmor extends Enchant {
	private static final long serialVersionUID = 1L;
	private static Random _random = new Random(System.nanoTime());

	public EnchantArmor(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			int itemId = this.getItemId();
			L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(packet.readD());

			if (l1iteminstance1 == null) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
			int safe_enchant = 0;
			if (l1iteminstance1.getItem().getType2() != 2) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}

			if (pc.getLastEnchantItemid() == l1iteminstance1.getId()) {
				pc.setLastEnchantItemid(l1iteminstance1.getId(), l1iteminstance1);
				return;
			}
			if (l1iteminstance1.getBless() >= 128) { // ������
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); 
				return;
			}

			safe_enchant = ((L1Armor) l1iteminstance1.getItem()).get_safeenchant();

			if (safe_enchant < 0) { // ��ȭ �Ұ�
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); 
				return;
			}

			int armorId = l1iteminstance1.getItem().getItemId();
			int armortype = l1iteminstance1.getItem().getType();
			/** ȯ���� ���� ���� �ֹ��� **/

			if (armorId >= 423000 && armorId <= 423008) {
				if (itemId == L1ItemId.SCROLL_OF_ENCHANT_FANTASY_ARMOR) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			if (itemId == L1ItemId.SCROLL_OF_ENCHANT_FANTASY_ARMOR) {
				if (armorId >= 423000 && armorId <= 423008) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			/** ȯ���� ���� ���� �ֹ��� **/

			/** �Ƴ�� ���� ���� �ֹ��� **/
			if (armorId == 21276) {
				if (itemId == 30147) {
				} else {
					pc.sendPackets(new S_ServerMessage(79));
					return;
				}
			}
			if (itemId == 30147) {
				if (armorId == 21276) {
				} else {
					pc.sendPackets(new S_ServerMessage(79));
					return;
				}
			}

			if (armorId != 21137 && itemId == 60251) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			} else if (armorId == 21137 && itemId != 60251) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}

			if (armorId != 21269 && itemId == 160511) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			} else if (armorId == 21269 && itemId != 160511) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}

			if (armorId == 20028 || armorId == 20283 || armorId == 20126 || armorId == 20173 || armorId == 20206
					|| armorId == 20232
					// ||armorId == 20059
					|| armorId == 421030 || armorId == 421031 || armorId == 21051 || armorId == 21052
					|| armorId == 21053 || armorId == 21054 || armorId == 21055 || armorId == 21056 || armorId == 21098

					|| armorId == 20081 || armorId == 21102 || armorId == 21103 || armorId == 21104 || armorId == 21105
					|| armorId == 21106 || armorId == 21107 || armorId == 21108 || armorId == 21109 || armorId == 21110
					|| armorId == 21111 || armorId == 21112 || armorId == 21254 || armorId == 20082) {
				if (itemId == L1ItemId.�������ֹ��� || itemId == 60141) {
					if (armorId == 20028 || armorId == 20126 || armorId == 20283 || armorId == 20173 || armorId == 20206
							|| armorId == 20232 || armorId == 21098 || armorId == 20081 || armorId == 20082) {
						int enchant_level = l1iteminstance1.getEnchantLevel();
						if (enchant_level >= 3) {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
							return;
						}
					} else if (

					armorId == 21102 || armorId == 21103 || armorId == 21104 || armorId == 21105 || armorId == 21106
							|| armorId == 21107 || armorId == 21108 || armorId == 21109 || armorId == 21110
							|| armorId == 21111 || armorId == 21112 || armorId == 21254) {
						int enchant_level = l1iteminstance1.getEnchantLevel();
						if (enchant_level >= 6) {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
							return;
						}

					}
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}

			if (itemId == L1ItemId.�������ֹ��� || itemId == 60141) {
				if (armorId == 20028 || armorId == 20283 || armorId == 20126 || armorId == 20173 || armorId == 20206
						|| armorId == 20232 || armorId == 21098

						// ||armorId == 20059
						|| armorId == 421030 || armorId == 421031 || armorId == 21051 || armorId == 21052
						|| armorId == 21053 || armorId == 21054 || armorId == 21055 || armorId == 21056
						|| armorId == 20081 || armorId == 20082) {
					if (armorId == 20028 || armorId == 20126 || armorId == 20283 || armorId == 20173 || armorId == 20206
							|| armorId == 20232 || armorId == 21098 || armorId == 20081 || armorId == 20082) {
						int enchant_level = l1iteminstance1.getEnchantLevel();
						if (enchant_level >= 3) {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
							return;
						}
					}
				} else {
					if (((armorId >= 21102 && armorId <= 21112) || armorId == 21102 || armorId == 21103
							|| armorId == 21104 || armorId == 21105 || armorId == 21106 || armorId == 21107
							|| armorId == 21108 || armorId == 21109 || armorId == 21110 || armorId == 21111
							|| armorId == 21112 || armorId == 21254) && l1iteminstance1.getEnchantLevel() < 6) {
					} else {
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						return;
					}
				}
			} else if (((armorId >= 21102 && armorId <= 21112) || armorId == 21254)) {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}

			/** âõ�� ���� ���� �ֹ��� **/
			if (armorId >= 422000 && armorId <= 422020) {
				if (itemId == L1ItemId.CHANGCHUN_ENCHANT_ARMOR_SCROLL) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			if (armorId >= 422000 && armorId <= 422020) {
				if (armorId >= 22041 && armorId <= 22061) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			/** âõ�� ���� ���� �ֹ��� **/

			if (itemId >= L1ItemId.Inadril_T_ScrollA && itemId <= L1ItemId.Inadril_T_ScrollD) {
				if (!(armorId >= 490000 && armorId <= 490017)) {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			if (armorId >= 490000 && armorId <= 490017) {
				if (!(itemId >= L1ItemId.Inadril_T_ScrollA && itemId <= L1ItemId.Inadril_T_ScrollD)) {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}

			/** ���� ��ȭ�� **/
			if (itemId == 4900211) {
				if (armortype != 16) {
					pc.sendPackets(new S_ServerMessage(79));
					return;
				}
			} else {
				if (armortype == 16) {
					pc.sendPackets(new S_ServerMessage(79));
					return;
				}
			}

			// �Ƴ����þ//
			if (armorId == 120192) {
				if (itemId == L1ItemId.�Ƴ�����ֹ��� || itemId == 500102) {
					int enchant_level = l1iteminstance1.getEnchantLevel();
					if (enchant_level >= 10) {
						pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						return;
					}
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			/** ��ű� ��ȭ �ֹ��� */
			if (itemId == 160511 || itemId == L1ItemId.ORIM_ACCESSORY_ENCHANT_SCROLL
					|| itemId == L1ItemId.ORIM_ACCESSORY_ENCHANT_SCROLL_B || itemId == L1ItemId.ACCESSORY_ENCHANT_SCROLL
					|| itemId == 430040 || itemId == 5000500 || itemId == 5000550 || itemId == 530040 || itemId == 60417) {
				if (armortype >= 8 && armortype <= 12) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			if (armortype >= 8 && armortype <= 12) {
				if (itemId == 160511 || itemId == L1ItemId.ORIM_ACCESSORY_ENCHANT_SCROLL
						|| itemId == L1ItemId.ORIM_ACCESSORY_ENCHANT_SCROLL_B
						|| itemId == 5000550|| itemId == L1ItemId.ACCESSORY_ENCHANT_SCROLL || itemId == 430040 || itemId == 5000500
						|| itemId == 530040 || itemId == 60417) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			/** ��ű� ��ȭ �ֹ��� */
			/** ��Ƽ���� ��ȭ �ֹ��� **/
			int enchant_level = l1iteminstance1.getEnchantLevel();
			if (itemId == 5000500 || itemId == 5000550) {
				if ((armorId >= 500007 && armorId <= 500010) || (armorId >= 502007 && armorId <= 502010)) {
					if (enchant_level >= Config.��Ƽ���ִ���æ) {
						pc.sendPackets(new S_SystemMessage("\\fW��Ƽ�� �Ͱ��̴� " + Config.��Ƽ���ִ���æ + "�̻��� ��æƮ �� �� �����ϴ�."),
								true);
						return;
					}
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}

			}
			if ((armorId >= 500007 && armorId <= 500010) || (armorId >= 502007 && armorId <= 502010)) {
				if (itemId == 5000500 || itemId == 5000550) {
					if (enchant_level >= Config.��Ƽ���ִ���æ) {
						pc.sendPackets(new S_SystemMessage("\\fW��Ƽ�� �Ͱ��̴� " + Config.��Ƽ���ִ���æ + "�̻��� ��æƮ �� �� �����ϴ�."),
								true);
						return;
					}
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}

			}
			/** ��Ƽ���� ��ȭ �ֹ��� **/
			/** �������� ���� ��ȭ �ֹ��� */
			if (itemId == 60417) {
				if (armorId >= 21246 && armorId <= 21253) {
					if (enchant_level >= Config.�������ִ���æ) {
						pc.sendPackets(new S_SystemMessage("\\fW�����۹����� " + Config.�������ִ���æ + "�̻��� ��æƮ �� �� �����ϴ�."), true);
						return;
					}
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			if (armorId >= 21246 && armorId <= 21253) {
				if (itemId == 60417) {
					if (enchant_level >= Config.�������ִ���æ) {
						pc.sendPackets(new S_SystemMessage("\\fW�����۹����� " + Config.�������ִ���æ + "�̻��� ��æƮ �� �� �����ϴ�."), true);
						return;
					}
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}

			}
			/** �������� ���� ��ȭ �ֹ��� */
			/** �ű��� ���� ��ȭ �ֹ��� */
			if (itemId == 530040) {
				if ((armorId >= 525109 && armorId <= 525113) || (armorId >= 625109 && armorId <= 625113)) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			if ((armorId >= 525109 && armorId <= 525113) || (armorId >= 625109 && armorId <= 625113)) {
				if (itemId == 530040) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			/** �ű��� ���� ��ȭ �ֹ��� */

			/** ������ ���� ��ȭ �ֹ��� */
			if (itemId == 430040) {
				if (armorId >= 425109 && armorId <= 425113) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			if (armorId >= 425109 && armorId <= 425113) {
				if (itemId == 430040) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			/** ������ ���� ��ȭ �ֹ��� */
			/** Ƽ���� ���� ��ȭ �ֹ��� */
			if (itemId == 430041 || itemId == 1430041 || itemId == 2430041) {
				if (armorId == 21028 || armorId == 21029 || armorId == 21030 || armorId == 21031 || armorId == 21032
						|| armorId == 21033 || armorId == 425106 || armorId == 425107 || armorId == 425108
						|| (armorId >= 21183 && armorId <= 21206)
				// armorId == 423013 || armorId == 423012
				) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			if (armorId == 21028 || armorId == 21029 || armorId == 21030 || armorId == 21031 || armorId == 21032
					|| armorId == 21033 || armorId == 425106 || armorId == 425107 || armorId == 425108
					|| (armorId >= 21183 && armorId <= 21206)
			// armorId == 423013 || armorId == 423012
			) {
				if (itemId == 430041 || itemId == 1430041 || itemId == 2430041) {
				} else {
					pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
					return;
				}
			}
			/** Ƽ���� ���� ��ȭ �ֹ��� */
			if (enchant_level >= Config.��ű��ִ���æ) {
				if (armortype >= 8 && armortype <= 12) { // ���� : Ư
					pc.sendPackets(new S_SystemMessage("\\fW��ű��� " + Config.��ű��ִ���æ + "�̻��� ��æƮ �� �� �����ϴ�."), true);
					return;
				}
			}
				
			if (enchant_level >= Config.MAX_ARMOR) {
				pc.sendPackets(new S_SystemMessage("\\fW��� ���� ���� +" + Config.MAX_ARMOR + "�̻��� ��æ�� �� �����ϴ�."), true);
				return;
			}

			if (Config.GAME_SERVER_TYPE == 1 && enchant_level >= safe_enchant + 3) {
				pc.sendPackets(new S_SystemMessage("�׽�Ʈ���������� ������æ+3 �̻��� ��æ�ϽǼ� �����ϴ�."), true);
				return;
			}

			if (itemId == 500302) { // ����� ��
				if (l1iteminstance1 != null && l1iteminstance1.getItem().getType2() == 1
						|| l1iteminstance1.getItem().getType2() == 2) {
					if (enchant_level >= Config.MAX_ARMOR) { // ��ȭ�Ұ� ��ġ �˾Ƽ�...
						pc.sendPackets(new S_SystemMessage("\\fW��� ���� ���� +" + Config.MAX_ARMOR + "�̻��� ��æ�� �� �����ϴ�."));
						return;
					}
					Random random = new Random();
					int k3 = random.nextInt(100);
					if (k3 <= 5) { // -1 �� Ȯ�� 4%
						SuccessEnchant(pc, l1iteminstance1, -1);
						pc.sendPackets(new S_SystemMessage("\\fY��þƮ�� �����Ͽ� �������� ��þ��ġ�� -1 ���������ϴ�."));
						pc.getInventory().removeItem(useItem, 1);
					}
					if (k3 >= 6 && k3 <= 30) { // +1 ��Ȯ�� 17%
						SuccessEnchant(pc, l1iteminstance1, RandomELevel(l1iteminstance1, itemId));
						pc.sendPackets(new S_SystemMessage("\\fW�����մϴ�!��þƮ�� �����Ͽ� �������� ��þ��ġ�� +1 �ö󰬽��ϴ�."));
						pc.getInventory().removeItem(useItem, 1);
					}
					if (k3 >= 31 && k3 <= 100) { // Ȯ���� �˾Ƽ�
						pc.sendPackets(new S_SystemMessage("\\fW��þƮ�� �����Ͽ����� ��þƮ ��ġ�� ���� �˴ϴ�."));
						pc.getInventory().removeItem(useItem, 1);
					}

				} else {
					pc.sendPackets(new S_ServerMessage(79)); // \f1 �ƹ��͵� �Ͼ��
																// �ʾҽ��ϴ�.
				}
			} else if (itemId == 500303) { // ����� �� 100%
				if (l1iteminstance1 != null && l1iteminstance1.getItem().getType2() == 1
						|| l1iteminstance1.getItem().getType2() == 2) {
					if (enchant_level >= 10) { // ��ȭ�Ұ� ��ġ �˾Ƽ�...
						pc.sendPackets(new S_SystemMessage("\\fW��þƮ �������� ���̻� ��þƮ �Ҽ� �����ϴ�"));
						return;
					}

					SuccessEnchant(pc, l1iteminstance1, RandomELevel(l1iteminstance1, itemId));
					pc.sendPackets(new S_SystemMessage("\\fW�������� ��þ��ġ�� +1 �ö󰬽��ϴ�."));
					pc.getInventory().removeItem(useItem, 1);

				}
			} else if (itemId == L1ItemId.C_SCROLL_OF_ENCHANT_ARMOR || itemId == 2430041
					|| itemId == L1ItemId.Inadril_T_ScrollC) { // c-zel
				pc.getInventory().removeItem(useItem, 1);
				int rnd = _random.nextInt(100) + 1;
				if (safe_enchant == 0 && rnd <= 30) {
					FailureEnchant(pc, l1iteminstance1);
					return;
				}
				if (enchant_level < -6) {
					// -6�̻��� �� �� ����.
					FailureEnchant(pc, l1iteminstance1);
				} else {
					SuccessEnchant(pc, l1iteminstance1, -1);
				}
			} else if (itemId >= 5000138 && itemId <= 5000141) {
				DragonArmorEnchant(pc, l1iteminstance1, itemId);
				// pc.getInventory().removeItem(useItem, 1);
			} else if (itemId == L1ItemId.Inadril_T_ScrollD) {
				pc.getInventory().removeItem(useItem, 1);
				RegistEnchant(pc, l1iteminstance1, itemId);

			} else if (enchant_level < safe_enchant) {
				pc.getInventory().removeItem(useItem, 1);
				SuccessEnchant(pc, l1iteminstance1, RandomELevel(l1iteminstance1, itemId));
			} else {
				pc.getInventory().removeItem(useItem, 1);

				// int rnd = _random.nextInt(10000);
				int rnd = _random.nextInt(100) + 1;
				int enchant_chance_armor;
				int enchant_level_tmp;
				if (safe_enchant == 0) {
					enchant_level_tmp = 2;
				} else {
					enchant_level_tmp = 1;
				}
				if (armortype >= 8 && armortype <= 12) {
					int chance = 0;
					try {
						chance = ItemEnchantList.getInstance().getItemEnchant(l1iteminstance1.getItemId());
					} catch (Exception e) {
						System.out.println("ItemEnchantList chance Error");
					}
					if (enchant_level >= 9) {
						enchant_chance_armor = (70 + enchant_level_tmp * Config.ENCHANT_CHANCE_ACCESSORY)
								/ (enchant_level_tmp * (enchant_level - 1)) + chance;
					} else if (enchant_level >= 5) {
						enchant_chance_armor = (80 + enchant_level_tmp * Config.ENCHANT_CHANCE_ACCESSORY)
								/ (enchant_level_tmp * 4) + chance;
					} else {
						enchant_chance_armor = (90 + enchant_level_tmp * Config.ENCHANT_CHANCE_ACCESSORY)
								/ enchant_level_tmp + chance;
						;
					}
				} else {
					int chance = 0;
					try {
						chance = ItemEnchantList.getInstance().getItemEnchant(l1iteminstance1.getItemId());
					} catch (Exception e) {
						System.out.println("ItemEnchantList chance Error");
					}

					if (enchant_level >= 6) {
						if (l1iteminstance1.getMr() > 0) {
							enchant_chance_armor = 80 / ((enchant_level - safe_enchant + 1) * 2)
									/ (enchant_level / 7 != 0 ? 1 * 2 : 1) / (enchant_level_tmp)
									+ Config.ENCHANT_CHANCE_ARMOR + chance;
						} else {
							enchant_chance_armor = 90 / ((enchant_level - safe_enchant + 1) * 2)
									/ (enchant_level / 7 != 0 ? 1 * 2 : 1) / (enchant_level_tmp)
									+ Config.ENCHANT_CHANCE_ARMOR + chance;
						}
					} else {
						if (l1iteminstance1.getItem().get_safeenchant() == 0) {
							if (l1iteminstance1.getMr() > 0) {
								enchant_chance_armor = 80 / ((enchant_level - safe_enchant + 1) * 2)
										/ (enchant_level / 7 != 0 ? 1 * 2 : 1) / (enchant_level_tmp)
										+ Config.ENCHANT_CHANCE_ARMOR + chance;
							} else {
								enchant_chance_armor = 90 / ((enchant_level - safe_enchant + 1) * 2)
										/ (enchant_level / 7 != 0 ? 1 * 2 : 1) / (enchant_level_tmp)
										+ Config.ENCHANT_CHANCE_ARMOR + chance;
							}
						} else {
							if (l1iteminstance1.getMr() > 0) {
								enchant_chance_armor = 80 / ((enchant_level - safe_enchant + 1) * 2)
										/ (enchant_level / 7 != 0 ? 1 * 2 : 1) / (enchant_level_tmp)
										+ Config.ENCHANT_CHANCE_ARMOR + chance;
								;
							} else {
								enchant_chance_armor = 90 / ((enchant_level - safe_enchant + 1) * 2)
										/ (enchant_level / 7 != 0 ? 1 * 2 : 1) / (enchant_level_tmp)
										+ Config.ENCHANT_CHANCE_ARMOR + chance;
								;
							}
						}
					}

					if (pc.isGm()) {
						pc.sendPackets(new S_SystemMessage("\\fYȮ�� : [ " + enchant_chance_armor + " ]"));
						pc.sendPackets(new S_SystemMessage("\\fY�߰� : [ " + chance + " ]"));
						pc.sendPackets(new S_SystemMessage("\\fY���� : [ " + rnd + " ]"));
					}
				}
				/*
				 * if (safe_enchant == 0) { if (armortype >= 8 && armortype <=
				 * 12) { if (itemId == 5000500 || itemId == 60417) { if
				 * (enchant_level >= 0 && enchant_level <= 3) { if
				 * (enchant_level == 3) enchant_chance_armor = 35; else
				 * enchant_chance_armor = 50; } else if (enchant_level == 4)
				 * enchant_chance_armor = 20; else if (enchant_level == 5)
				 * enchant_chance_armor = 10; else if (enchant_level == 6)
				 * enchant_chance_armor = 8; else if (enchant_level == 7)
				 * enchant_chance_armor = 5; } else if (itemId == 160511) {//
				 * �ҷ�����ű������ֹ��� if (enchant_level >= 0 && enchant_level <= 3) {
				 * if (enchant_level == 3) enchant_chance_armor = 40; else
				 * enchant_chance_armor = 55; } else if (enchant_level == 4)
				 * enchant_chance_armor = 18; else if (enchant_level >= 5)
				 * enchant_chance_armor = 8; } else { if (enchant_level >= 0 &&
				 * enchant_level <= 3) { if (enchant_level == 3)
				 * enchant_chance_armor = 40; else enchant_chance_armor = 60; //
				 * } } else if (enchant_level == 4) enchant_chance_armor = 18;
				 * else if (enchant_level == 5) enchant_chance_armor = 18; else
				 * if (enchant_level >= 6) enchant_chance_armor = 18; } } else {
				 * if (enchant_level >= 0 && enchant_level <= 3) {
				 * enchant_chance_armor = 20; } else if (enchant_level >= 4 &&
				 * enchant_level <= 5) enchant_chance_armor = 15; else if
				 * (enchant_level >= 6) enchant_chance_armor = 5; } } else if
				 * (safe_enchant == 4) { if (enchant_level <= 4)
				 * enchant_chance_armor = 35; else if (enchant_level == 5)
				 * enchant_chance_armor = 25; else if (enchant_level == 6)
				 * enchant_chance_armor = 25; else if (enchant_level == 7)
				 * enchant_chance_armor = 25; else if (enchant_level == 8)
				 * enchant_chance_armor = 20; else if (enchant_level >= 9)
				 * enchant_chance_armor = 5; } else { if (enchant_level <= 6)
				 * enchant_chance_armor = 33; else if (enchant_level == 7)
				 * enchant_chance_armor = 15; else if (enchant_level == 8)
				 * enchant_chance_armor = 10; else if (enchant_level >= 9)
				 * enchant_chance_armor = 1; } // Ưȭ�� ������æ 6�������� if (armorId >=
				 * 427303 && armorId <= 427305 && enchant_level <= 5) {
				 * enchant_chance_armor = 200; }
				 * 
				 * // ��þ ������ ������ ���� ���� enchant_chance_armor *= 100;
				 * enchant_chance_armor *= (Config.ENCHANT_CHANCE_ARMOR == 0 ? 1
				 * : Config.ENCHANT_CHANCE_ARMOR);
				 * 
				 * if (itemId == L1ItemId.ORIM_ACCESSORY_ENCHANT_SCROLL ||
				 * itemId == L1ItemId.ORIM_ACCESSORY_ENCHANT_SCROLL_B) { rnd =
				 * _random.nextInt(100); enchant_chance_armor = 20; if
				 * (enchant_level == 6) { enchant_chance_armor = 12; } if
				 * (enchant_level == 7) { enchant_chance_armor = 5; }
				 * 
				 * }
				 */

				if (rnd < enchant_chance_armor) {
					int randomEnchantLevel = RandomELevel(l1iteminstance1, itemId);
					SuccessEnchant(pc, l1iteminstance1, randomEnchantLevel);
				} else if (enchant_level >= 9 && rnd < (enchant_chance_armor * 2)) {
					String item_name_id = l1iteminstance1.getName();
					String pm = "";
					String msg = "";
					if (enchant_level > 0) {
						pm = "+";
					}
					msg = (new StringBuilder()).append(pm + enchant_level).append(" ").append(item_name_id).toString();
					pc.sendPackets(new S_ServerMessage(160, msg, "$252", "$248"), true);
				} else {
					if (itemId == L1ItemId.ORIM_ACCESSORY_ENCHANT_SCROLL) {
						int rnddd = _random.nextInt(100);
						if (rnddd < 80) {
							String item_name_id = l1iteminstance1.getName();
							String pm = "";
							String msg = "";
							if (enchant_level > 0) {
								pm = "+";
							}
							msg = (new StringBuilder()).append(pm + enchant_level).append(" ").append(item_name_id)
									.toString();

							pc.sendPackets(new S_ServerMessage(4056, msg));
							return;
						}
					} else if (itemId == L1ItemId.ORIM_ACCESSORY_ENCHANT_SCROLL_B) {
						String item_name_id = l1iteminstance1.getName();
						String pm = "";
						String msg = "";
						if (enchant_level > 0) {
							pm = "+";
						}
						msg = (new StringBuilder()).append(pm + enchant_level).append(" ").append(item_name_id)
								.toString();
						pc.sendPackets(new S_ServerMessage(4056, msg));
						return;
					}
				 else if (itemId == 5000550) {
					String item_name_id = l1iteminstance1.getName();
					String pm = "";
					String msg = "";
					if (enchant_level > 0) {
						pm = "+";
					}
					msg = (new StringBuilder()).append(pm + enchant_level).append(" ").append(item_name_id)
							.toString();
					pc.sendPackets(new S_ServerMessage(4056, msg));
					return;
				}

					FailureEnchant(pc, l1iteminstance1, itemId);
				}
			}
		}
	}
}
