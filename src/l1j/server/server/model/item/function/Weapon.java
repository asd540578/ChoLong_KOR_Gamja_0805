package l1j.server.server.model.item.function;

import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Sound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;

public class Weapon extends L1ItemInstance {
	private static final long serialVersionUID = 1L;

	public Weapon(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			if (useItem.getItem().getType2() == 1) {
				if (pc.isGm()) {
					UseWeapon(pc, useItem);
				} else if (pc.isCrown() && useItem.getItem().isUseRoyal()
						|| pc.isKnight() && useItem.getItem().isUseKnight()
						|| pc.isElf() && useItem.getItem().isUseElf() || pc.isWizard() && useItem.getItem().isUseMage()
						|| pc.isWarrior() && useItem.getItem().isUseWarrior()
						|| pc.isDarkelf() && useItem.getItem().isUseDarkelf()
						|| pc.isDragonknight() && useItem.getItem().isUseDragonKnight()
						|| pc.isIllusionist() && useItem.getItem().isUseBlackwizard()) {
					UseWeapon(pc, useItem);
				} else {
					// \f1����� Ŭ���������� �� �������� ����� �� �����ϴ�.
					pc.sendPackets(new S_ServerMessage(264), true);
				}
				// }
			}
		}
	}

	private boolean ���߳�(L1ItemInstance weapon, L1PcInstance pc) {
		int min = weapon.getItem().getMinLevel();
		int max = weapon.getItem().getMaxLevel();
		if (min != 0 && min > pc.getLevel()) {
			pc.sendPackets(new S_ServerMessage(318, String.valueOf(min)), true);
			return false;
		} else if (max != 0 && max < pc.getLevel()) {
			if (max < 50) {
				pc.sendPackets(new S_PacketBox(S_PacketBox.MSG_LEVEL_OVER, max), true);
			} else {
				pc.sendPackets(new S_SystemMessage("�� ��������" + max + "���� ���ϸ� ����� �� �ֽ��ϴ�. "), true);
			}
			return false;
		}
		return true;
	}

	private boolean useweaponpoly(L1PcInstance pc, L1ItemInstance weapon, int polyid) {
		if (!L1PolyMorph.isEquipableWeapon(polyid, 19)) { // �� ���ſ����� ��� �Ұ�
			pc.sendPackets(new S_SystemMessage("���� ���� ���¿����� �ּ� ������ ������ �� �����ϴ�."), true);
			return false;
		}
		return true;
	}

	private void UseWeapon(L1PcInstance activeChar, L1ItemInstance weapon) {
		L1PcInventory pcInventory = activeChar.getInventory();
		if (activeChar.getWeapon() == null || !activeChar.getWeapon().equals(weapon)) {
			int weapon_type = weapon.getItem().getType();
			int polyid = activeChar.getGfxId().getTempCharGfx();

			if (weapon.getItem().getItemId() != 7236) {
				if (!L1PolyMorph.isEquipableWeapon(polyid, weapon_type)) {
					String n = ("+" + weapon.getEnchantLevel() + " " + weapon.getName());
					activeChar.sendPackets(new S_SystemMessage("���� ���� ���¿����� " + n + "�� ���� �� �� �����ϴ�."), true);
					return;
				}
			}

			if (weapon.getItem().isTwohandedWeapon() && pcInventory.getGarderEquipped(2, 7, 13) >= 1) {
				activeChar.sendPackets(new S_ServerMessage(128), true);
				return;
			}

		}

		activeChar.cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����
		boolean isdoubleweapon = false;
		if (activeChar.isWarrior()) {
			if (activeChar.isSlayer) {
				if (activeChar.getWeapon() != null && activeChar.getSecondWeapon() != null) {

					isdoubleweapon = true;
				}
				if (isdoubleweapon) {
					if (activeChar.getWeapon().equals(weapon)) {
						if (activeChar.getWeapon().getItem().getBless() == 2) {
							activeChar.sendPackets(new S_ServerMessage(150));
							return;
						}
						// ��� ��ȯ�� �ƴϰ� ������ ��
						pcInventory.setEquipped(activeChar.getWeapon(), false, false, false, false);
						return;
					}
					if (activeChar.getSecondWeapon().equals(weapon)) {
						// ��� ��ȯ�� �ƴϰ� ������ ��
						pcInventory.setEquipped(activeChar.getSecondWeapon(), false, false, false, true);
						return;
					}
					if (weapon.getItem().getType() == 6) {
						if (!���߳�(weapon, activeChar))
							return;

						pcInventory.setEquipped(activeChar.getSecondWeapon(), false, false, true, true);
						pcInventory.setEquipped(weapon, true, false, false, true);
						return;

					} else {
						if (!���߳�(weapon, activeChar))
							return;
						pcInventory.setEquipped(activeChar.getSecondWeapon(), false, false, false, true);
						pcInventory.setEquipped(activeChar.getWeapon(), false, false, false, false);
						pcInventory.setEquipped(weapon, true, false, false, false);
						return;
					}
				} else {
					if (activeChar.getWeapon() != null) {
						if (activeChar.getWeapon().equals(weapon)) {
							if (activeChar.getWeapon().getItem().getBless() == 2) {
								activeChar.sendPackets(new S_ServerMessage(150));
								return;
							}
							// ��� ��ȯ�� �ƴϰ� ������ ��
							pcInventory.setEquipped(activeChar.getWeapon(), false, false, false, false);
							return;
						}
						if (activeChar.getWeapon().getItem().getType() == 6 && weapon.getItem().getType() == 6) {
							if (pcInventory.getGarderEquipped(2, 7, 13) >= 1) {
								activeChar.sendPackets(new S_ServerMessage(128), true);
								return;
							}
							if (pcInventory.getGarderEquipped(2, 13, 13) >= 1) {
								activeChar.sendPackets(new S_ServerMessage(128), true);
								return;
							}
							if (!���߳�(weapon, activeChar)) {
								return;
							}
							int polyid = activeChar.getGfxId().getTempCharGfx();
							if (!useweaponpoly(activeChar, weapon, polyid)) {
								return;
							}
							activeChar.sendPackets(new S_SkillSound(activeChar.getId(), 12534)); // �����̾�
																									// ����Ʈ
							Broadcaster.broadcastPacket(activeChar, new S_SkillSound(activeChar.getId(), 12534)); // �����̾�
																													// ����Ʈ
							pcInventory.setEquipped(weapon, true, false, false, true);
							return;
						}
					}

				}
			}
		}
		if (activeChar.getWeapon() != null) { // �̹� �����ΰ��� ��� �ϰ� �ִ� ���, ���� ��� ����
			if (activeChar.getWeapon().getItem().getBless() == 2) { // ���������� �־���
																	// ���
				activeChar.sendPackets(new S_ServerMessage(150), true);
				return;
			}
			if (activeChar.getWeapon().equals(weapon)) {
				// ��� ��ȯ�� �ƴϰ� ������ ��
				pcInventory.setEquipped(activeChar.getWeapon(), false, false, false);
				if (weapon.getItemId() == 262)// ���弭Ŀ ���� ����
					activeChar.sendPackets(new S_Sound(2828), true);
				return;
			} else {
				if (!���߳�(weapon, activeChar))
					return;
				pcInventory.setEquipped(activeChar.getWeapon(), false, false, true);
			}
		}

		if (weapon.getItemId() == 200002) { // �������� ���̽��ٰ�
			activeChar.sendPackets(new S_ServerMessage(149, weapon.getLogName()), true); // \f1%0��
																							// �տ�
																							// �鷯�پ����ϴ�.
		}
		if (!���߳�(weapon, activeChar))
			return;
		pcInventory.setEquipped(weapon, true, false, false);
		if (weapon.getItemId() == 7236) {// ������ ���߸�Ʈ
			activeChar.sendPackets(new S_ServerMessage(149, weapon.getLogName()), true); // \f1%0��
																							// �տ�
																							// �鷯�پ����ϴ�.
		}
		if (weapon.getItemId() == 262)// ���弭Ŀ ���� ����
			activeChar.sendPackets(new S_Sound(2828), true);

	}
}
