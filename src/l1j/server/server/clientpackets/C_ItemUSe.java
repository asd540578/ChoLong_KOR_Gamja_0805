/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
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

import static l1j.server.server.model.skill.L1SkillId.ADVANCE_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.AQUA_PROTECTER;
import static l1j.server.server.model.skill.L1SkillId.ARMOR_BREAK;
import static l1j.server.server.model.skill.L1SkillId.BLESS_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.BONE_BREAK;
import static l1j.server.server.model.skill.L1SkillId.BRAVE_AURA;
import static l1j.server.server.model.skill.L1SkillId.CONCENTRATION;
import static l1j.server.server.model.skill.L1SkillId.COUNTER_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.CURSE_PARALYZE;
import static l1j.server.server.model.skill.L1SkillId.CURSE_PARALYZE2;
import static l1j.server.server.model.skill.L1SkillId.DECAY_POTION;
import static l1j.server.server.model.skill.L1SkillId.DRAGON_PUPLE;
import static l1j.server.server.model.skill.L1SkillId.DRAGON_TOPAZ;
import static l1j.server.server.model.skill.L1SkillId.DRESS_DEXTERITY;
import static l1j.server.server.model.skill.L1SkillId.DRESS_MIGHTY;
import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;
import static l1j.server.server.model.skill.L1SkillId.EARTH_SKIN;
import static l1j.server.server.model.skill.L1SkillId.ERASE_MAGIC;
import static l1j.server.server.model.skill.L1SkillId.EXOTIC_VITALIZE;
import static l1j.server.server.model.skill.L1SkillId.EXP_POTION;
import static l1j.server.server.model.skill.L1SkillId.EXP_POTION2;
import static l1j.server.server.model.skill.L1SkillId.EXP_POTION3;
import static l1j.server.server.model.skill.L1SkillId.EXP_POTION_cash;
import static l1j.server.server.model.skill.L1SkillId.FIRE_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.FOG_OF_SLEEPING;
import static l1j.server.server.model.skill.L1SkillId.FREEZING_BREATH;
import static l1j.server.server.model.skill.L1SkillId.ICE_LANCE;
import static l1j.server.server.model.skill.L1SkillId.INSIGHT;
import static l1j.server.server.model.skill.L1SkillId.IRON_SKIN;
import static l1j.server.server.model.skill.L1SkillId.MOB_BASILL;
import static l1j.server.server.model.skill.L1SkillId.MOB_COCA;
import static l1j.server.server.model.skill.L1SkillId.MOB_RANGESTUN_18;
import static l1j.server.server.model.skill.L1SkillId.MOB_RANGESTUN_19;
import static l1j.server.server.model.skill.L1SkillId.MOB_SHOCKSTUN_30;
import static l1j.server.server.model.skill.L1SkillId.PATIENCE;
import static l1j.server.server.model.skill.L1SkillId.PHANTASM;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;
import static l1j.server.server.model.skill.L1SkillId.POLLUTE_WATER;
import static l1j.server.server.model.skill.L1SkillId.SHINING_AURA;
import static l1j.server.server.model.skill.L1SkillId.SHOCK_STUN; //��������
import static l1j.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL2;
import static l1j.server.server.model.skill.L1SkillId.STATUS_CASHSCROLL3;
import static l1j.server.server.model.skill.L1SkillId.STATUS_COMA_5;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_MITHRIL_POWDER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER;
import static l1j.server.server.model.skill.L1SkillId.STATUS_HOLY_WATER_OF_EVA;
import static l1j.server.server.model.skill.L1SkillId.STATUS_����6;
import static l1j.server.server.model.skill.L1SkillId.STATUS_����7;
import static l1j.server.server.model.skill.L1SkillId.STATUS_�ÿ��Ѿ�������;
import static l1j.server.server.model.skill.L1SkillId.STATUS_����6;
import static l1j.server.server.model.skill.L1SkillId.STATUS_����7;
import static l1j.server.server.model.skill.L1SkillId.Tam_Fruit1;
import static l1j.server.server.model.skill.L1SkillId.Tam_Fruit2;
import static l1j.server.server.model.skill.L1SkillId.Tam_Fruit3;
import static l1j.server.server.model.skill.L1SkillId.WIND_SHOT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastMap;
import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.CrockSystem;
import l1j.server.GameSystem.Antaras.AntarasRaidSystem;
import l1j.server.GameSystem.Delivery.Delivery;
import l1j.server.GameSystem.Delivery.DeliverySystem;
import l1j.server.GameSystem.Lind.LindRaid;
import l1j.server.GameSystem.Papoo.PaPooRaidSystem;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.Opcodes;
import l1j.server.server.TimeController.FishingTimeController;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.LogTable;
import l1j.server.server.datatables.MapFixKeyTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.Beginner;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1EffectSpawn;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1ItemDelay;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Quest;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1BoardInstance;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1EffectInstance;
import l1j.server.server.model.Instance.L1GuardianInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.item.function.LeafItem;
import l1j.server.server.model.item.function.TeleportScroll;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.poison.L1DamagePoison;
import l1j.server.server.model.skill.L1SkillDelay;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_AddItem;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_Board;
import l1j.server.server.serverpackets.S_ChangeName;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_CharTitle;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_ClanJoinLeaveStatus;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_DRAGONPERL;
import l1j.server.server.serverpackets.S_DeleteInventoryItem;
import l1j.server.server.serverpackets.S_Dexup;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_HPUpdate;
import l1j.server.server.serverpackets.S_ItemName;
import l1j.server.server.serverpackets.S_Liquor;
import l1j.server.server.serverpackets.S_MPUpdate;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_NewUI;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_OwnCharStatus2;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_ReturnedStat;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconAura;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Sound;
import l1j.server.server.serverpackets.S_Strup;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_UseMap;
import l1j.server.server.serverpackets.S_UserCommands1;//����pcƼ�� ����
import l1j.server.server.serverpackets.S_UserCommands2;//ĳ���ͱ�ȯ��ǥ����
import l1j.server.server.serverpackets.S_UserStatus;
import l1j.server.server.serverpackets.S_�����ֽ�;
import l1j.server.server.templates.L1BookMark;
import l1j.server.server.templates.L1EtcItem;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Pet;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.utils.L1SpawnUtil;
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;
import server.manager.eva;

public class C_ItemUSe extends ClientBasePacket {

	private static final String C_ITEM_USE = "[C] C_ItemUSe";
	private static Logger _log = Logger.getLogger(C_ItemUSe.class.getName());

	private static Random _random = new Random(System.nanoTime());

	Calendar currentDate = Calendar.getInstance();

	private static final int[] MALE_LIST = new int[] { 0, 61, 138, 734, 2786, 6658, 6671, 12490 };
	private static final int[] FEMALE_LIST = new int[] { 1, 48, 37, 1186, 2796, 6661, 6650, 12494 };

	public C_ItemUSe(byte abyte0[], LineageClient client) throws Exception {
		super(abyte0);
		try {
			int itemObjid = readD();
			L1PcInstance pc = client.getActiveChar();

			if (pc == null || pc.isGhost()) {
				return;
			}
			L1ItemInstance useItem = pc.getInventory().getItem(itemObjid);
			if (itemObjid == 0 || useItem == null || pc.isDead() == true) {
				return;
			}

			if (useItem.getItemId() == 20383 && useItem.getRemainingTime() <= 0) {// �⸶��
																					// ����
				pc.sendPackets(new S_ServerMessage(1195), true); // ��� �ð��� ��
																	// �Ǿ����ϴ�.
				return;
			}
			if (pc.getMapId() == 5166) {
				return;
			}
			if (pc.isPrivateShop())
				return;

			if (useItem.getItem().getUseType() == -1) { // none:����� �� ���� ������
				pc.sendPackets(new S_ServerMessage(74, useItem.getLogName()), true); // \f1%0��
																						// �����
																						// ��
																						// �����ϴ�.
				return;
			}
			/** ���ε� ��, ���� ��� �Ұ����ϰ� by ���� **/
			if (useItem.getItemId() >= 427123 && useItem.getItemId() <= 427140) {
				pc.sendPackets(new S_ServerMessage(74, useItem.getLogName()), true); // \f1%0��
																						// �����
																						// ��
																						// �����ϴ�.
				return;
			}
			if (pc.isTeleport()) { // �ڷ���Ʈ ó����
				if (useItem instanceof TeleportScroll) {
					pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
				}
				return;
			}
			if (useItem.getItem().getType2() == 0) {
				if (useItem.getItem().getType() != 6 && useItem.getItem().getType() != 7
						&& useItem.getItem().getType() != 8 && useItem.getItem().getItemId() != 41159) {
					if (pc.getInventory().calcWeightpercent() >= 90) {
						pc.sendPackets(new S_SystemMessage("������ ��� ���� : ���� ������ 90% �̻� ��� �Ұ�."));
						// pc.sendPackets(new S_ServerMessage(3561)); // ���� ��������
						// ����á���ϴ�.
						return;
					}
					if (pc.getInventory().getSize() >= 175) {
						pc.sendPackets(new S_SystemMessage("������ ��� ���� : �κ��丮�� ������ ������ ������ּ���."));
						return;
					}

				}
			}

			// ������� ���� �߰�
			L1PcInstance jonje = L1World.getInstance().getPlayer(pc.getName());
			if (jonje == null && pc.getAccessLevel() != 200) {
				pc.sendPackets(new S_SystemMessage("������� ��������! �������ϼ���"), true);
				client.kick();
				return;
			}

			/** ���� ��ų �߿� ���Ƿ� ������ ��� ���ϰ� **/
			if (pc.getSkillEffectTimerSet().hasSkillEffect(SHOCK_STUN)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(MOB_SHOCKSTUN_30)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(MOB_RANGESTUN_19)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(MOB_RANGESTUN_18)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(BONE_BREAK)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(PHANTASM)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(FOG_OF_SLEEPING)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(CURSE_PARALYZE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(CURSE_PARALYZE2)) {
				return;
			}

			if (!pc.getMap().isUsableItem() && !pc.isGm()) {
				pc.sendPackets(new S_SystemMessage("���⿡���� ����� �� �����ϴ�."), true); // \f1
																				// ���⿡����
																				// �����
																				// ��
																				// �����ϴ�.
				if (useItem instanceof TeleportScroll) {
					pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
				}
				return;
			}
			int itemId;
			try {
				itemId = useItem.getItem().getItemId();
			} catch (Exception e) {
				return;
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_�������)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.STATUS_�������);
			}
			if (useItem.isWorking()) {
				if (pc.getCurrentHp() > 0) {
					if (useItem.getItem().getType2() == 0) { // �������� ���� ������
						int item_minlvl = ((L1EtcItem) useItem.getItem()).getMinLevel();
						int item_maxlvl = ((L1EtcItem) useItem.getItem()).getMaxLevel();

						if (item_minlvl != 0 && item_minlvl > pc.getLevel() && !pc.isGm()) {
							// pc.sendPackets(new S_ServerMessage(318,
							// String.valueOf(item_minlvl)), true);
							pc.sendPackets(new S_SystemMessage("�� �������� " + item_minlvl + "���� �̻��� �Ǿ�� ����� �� �ֽ��ϴ�."),
									true);
							// �� ��������%0���� �̻��� ���� ������ ����� �� �����ϴ�.
							if (useItem instanceof TeleportScroll) {
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
							}
							return;
						} else if (item_maxlvl != 0 && item_maxlvl < pc.getLevel() && !pc.isGm()) {
							pc.sendPackets(new S_SystemMessage("�� �������� " + item_maxlvl + "���� ������ ���� ����� �� �ֽ��ϴ�."),
									true);
							// pc.sendPackets(new S_ServerMessage(673,
							// /*String.valueOf(item_maxlvl)*/), true);
							// �� ��������%d���� �̻� ����� �� �ֽ��ϴ�.
							if (useItem instanceof TeleportScroll) {
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
							}
							return;
						}
					}
					useItem.clickItem(pc, this);
				}
				return;
			}

			int l = 0;
			int spellsc_objid = 0;
			int use_objid = 0;
			int spellsc_x = 0;
			int spellsc_y = 0;

			int use_type = useItem.getItem().getUseType();

			if (itemId >= 40859 && itemId <= 40898) {
				switch (itemId) {
				case 40859:
				case 40866:
				case 40867:
				case 40877:
				case 40884:
				case 40893:
					/*
					 * case 60004: case 60006: case 60003:
					 */
				case 40895:// ����
				case 40870:
				case 40879:
					spellsc_objid = readD();
					break;
				case 40871:
				case 40872:
				case 40890:
				case 40863:
				case 40889:
				case 40861:
					/*
					 * case 60008: case 60007: case 60005:
					 */
				case 40860:// ��ý���
					break;
				case 40896:
				case 40894:
				case 40891:
				case 40892:
				case 40864:
				case 40898:
				case 40888:
				case 40887:
				case 40865:
				case 40869:
				case 40873:
				case 40874:
				case 40875:
				case 40876:
				case 40878:
				case 40880:
				case 40881:
				case 40883:
				case 40885:
				case 40862:
				case 40897:
				case 40868:
				case 40886:// ����
					spellsc_objid = readD();
					spellsc_x = readH();
					spellsc_y = readH();
				default:
					break;
				}
			} else if (itemId == 41029 // ��ȯ���� ����

					|| itemId == 600233 || itemId == 40317 || itemId == 60155 || itemId == 41036
					|| itemId == L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_DOWN || itemId == 7322 || itemId == 7240
					|| itemId == L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_DOWN
					|| itemId == L1ItemId.LOWER_TIKAL_PRESENT_PIECE_DOWN
					|| itemId == L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_DOWN || itemId == L1ItemId.TIMECRACK_CORE

					|| itemId == 141917 || itemId == 3000051 || itemId == 3000100 || itemId == 40964 || itemId == 41030 || itemId == 40925
					|| itemId == 40926 || itemId == 40927 // ��ȭ���ź����� �Ϻ�
					|| itemId == 40928 || itemId == 40929 || itemId == 500231 || itemId == 60025 || itemId == 60026
					|| itemId == 60104 || itemId == 60197 || (itemId >= 60218 && itemId <= 60232)
					|| (itemId >= 60235 && itemId <= 60246) || itemId == 60247 || (itemId >= 60273 && itemId <= 60283)
					|| itemId == 60333 || itemId == 600228 || itemId == 60476 || itemId == 60477 || itemId == 5557
					|| itemId == 60383 || itemId == 9095) {
				l = readD();
			} else if (use_type == 30 || itemId == 40870 || itemId == 40879) { // spell_buff
				spellsc_objid = readD();
			} else if (itemId == 5559 || itemId == 5560) {
				use_objid = readD();

			} else if (use_type == 5 || use_type == 17) { // spell_long
															// spell_short
				spellsc_objid = readD();
				spellsc_x = readH();
				spellsc_y = readH();
			} else {
				l = readC();
			}

			if (pc.getCurrentHp() > 0) {
				int delay_id = 0;
				if (useItem.getItem().getType2() == 0) { // �������� ���� ������
					delay_id = ((L1EtcItem) useItem.getItem()).get_delayid();
				}
				if (delay_id != 0) { // ���� ���� �־�
					if (pc.hasItemDelay(delay_id) == true) {
						// System.out.println("������");
						return;
					}
				}

				L1ItemInstance l1iteminstance1 = pc.getInventory().getItem(l);
				_log.finest("request item use (obj) = " + itemObjid + " action = " + l);
				if (useItem.getItem().getType2() == 0) { // �������� ���� ������
					int item_minlvl = ((L1EtcItem) useItem.getItem()).getMinLevel();
					int item_maxlvl = ((L1EtcItem) useItem.getItem()).getMaxLevel();

					if (item_minlvl != 0 && item_minlvl > pc.getLevel() && !pc.isGm()) {
						pc.sendPackets(new S_SystemMessage("�� �������� " + item_minlvl + "���� �̻��� �Ǿ�� ����� �� �ֽ��ϴ�."), true);
						// pc.sendPackets(new S_ServerMessage(318,
						// String.valueOf(item_minlvl)), true);
						// �� ��������%0���� �̻��� ���� ������ ����� �� �����ϴ�.
						return;
					} else if (item_maxlvl != 0 && item_maxlvl < pc.getLevel() && !pc.isGm()) {
						pc.sendPackets(new S_SystemMessage("�� �������� " + item_maxlvl + "���� ������ ���� ����� �� �ֽ��ϴ�."), true);
						// pc.sendPackets(new S_ServerMessage(673,
						// String.valueOf(item_maxlvl)), true);
						// �� ��������%d���� �̻� ����� �� �ֽ��ϴ�.
						return;
					}
					if ((itemId == 40576 && !pc.isElf()) || (itemId == 40577 && !pc.isWizard()) // ��ȥ��
																								// ������
																								// ����(��)
							|| (itemId == 40578 && !pc.isKnight())) { // ��ȥ�� ������
																		// ����(����)
						pc.sendPackets(new S_SystemMessage("����� Ŭ������ �� �������� ����� �� �����ϴ�."), true); // \f1�����
																									// Ŭ����������
																									// ��
																									// ��������
																									// �����
																									// ��
																									// �����ϴ�.
						return;
					}

					if (itemId == 60517) { // ���� ����
						pc.getInventory().removeItem(useItem, 1);
						���ɻ���(pc);

					} else if (itemId == 60519) { // ������ ���� ����
						pc.getInventory().removeItem(useItem, 1);
						�������ɻ���(pc);
					} else if (itemId == 9057) { // �׷����Ǽ�������
						pc.getInventory().removeItem(useItem, 1);
						�׷����Ǽ�������(pc);
					} else if (itemId == 9096) { // �����ָԻ���
						pc.getInventory().removeItem(useItem, 1);
						�������ָԻ���(pc);
					} else if (itemId == 40722) { // ��ȣ��
						pc.getInventory().removeItem(useItem, 1);
						��ȣ��(pc);

					} else if (itemId == 140722) { // �ٷ��� ���� ����
						pc.getInventory().removeItem(useItem, 1);
						�ٷ��Ǽ�������(pc);

					} else if (itemId == 9096) { // �����ָԻ���
						pc.getInventory().removeItem(useItem, 1);
						�������ָԻ���(pc);
					} else if (itemId == 60518) { // ���� ���� 1ȸ ����
						pc.getInventory().removeItem(useItem, 1);
						������������(pc);
					} else if (itemId == 60516) { // Ž ����Ʈ ����
						pc.getInventory().removeItem(useItem, 1);
						Ž����Ʈ����(pc);
					} else if (itemId == 60514) { // �߾� ��� Ȳ�� ����
						�߾ӻ��Ȳ�ݻ���(pc);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60512 || itemId == 60513) {// �������� ����,
																	// ��ȭ
						��������_����_��ȭ(pc, itemId, useItem);
					} else if (itemId == 60502 || itemId == 60503 || itemId == 60504 || itemId == 60505) {// ǳ����
																											// ����:3��
						��������(pc, itemId, useItem);
					} else if (itemId == 60493) {// ���ν��� ��ȥ��
						if (pc.getX() >= 33321 && pc.getX() <= 33348 && pc.getY() >= 32421 && pc.getY() <= 32458) {
							L1SpawnUtil.spawn(pc, 100719, 3, 2 * 3600000, false);
							L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
									pc.getName() + " ���� ���� ��� ��Ÿ��� ���ν��� ��ȯ�Ͽ����ϴ�."), true);
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�̰������� ��� �� �� �����ϴ�."), true);
						}
					} else if (itemId == 60498) {// �巹��ũ�� ��
						if (pc.getX() >= 33321 && pc.getX() <= 33348 && pc.getY() >= 32421 && pc.getY() <= 32458) {
							L1SpawnUtil.spawn(pc, 100718, 3, 2 * 3600000, false);
							L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
									pc.getName() + " ���� ���� ��� ��Ÿ��� �巹��ũ�� ��ȯ�Ͽ����ϴ�."), true);
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�̰������� ��� �� �� �����ϴ�."), true);
						}
					} else if (itemId == 60486) {// ���뽺 ���� 20��
						L1ItemInstance item = pc.getInventory().storeItem(L1ItemId.ADENA, 200000);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (200000)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60487) {// ���뽺 ���� 75��
						L1ItemInstance item = pc.getInventory().storeItem(L1ItemId.ADENA, 750000);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (750000)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60488) {// ���뽺 ���� 300��
						L1ItemInstance item = pc.getInventory().storeItem(L1ItemId.ADENA, 3000000);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (3000000)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60489) {// ���뽺 ���� 1000��
						L1ItemInstance item = pc.getInventory().storeItem(L1ItemId.ADENA, 10000000);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10000000)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60491) {// ��ȭ�ָӴ�
						��ȭ�ָӴ�(pc);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 160491) {// ��ȭ�ָӴ�
						Ȳ���ָӴ�(pc);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60484 || itemId == 60485) {// 52���� ����Ʈ
																	// ������ ����
						����52��������Ʈ�����ۻ���(pc, itemId);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60482) {// ���� �ݺ� �����Ƴ�
						L1ItemInstance item = pc.getInventory().storeItem(40308, 5000000);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (5000000)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60483) {// ���� ���� �����Ƴ�
						L1ItemInstance item = pc.getInventory().storeItem(40308, 500000);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (500000)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60480) {// ū �ݺ� �����Ƴ�
						ū�ݺ������Ƴ�(pc);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60481) {// ū ���� �����Ƴ�
						ū���������Ƴ�(pc);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60475) { // �⺻ ��� ����
						�⺻������(pc);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 7258) { // Ŭ���� ��ų��
						Ŭ������ų��(pc);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId >= 7325 && itemId <= 7334) { // �� ����
						����������(pc, itemId);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60471) { // ��Ƽ���� �������� ���� ����
						��Ƽ������������������(pc);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60472) { // ũ�������� ��Ű �ָӴ�
						L1ItemInstance item = pc.getInventory().storeItem(60443, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						item = pc.getInventory().storeItem(60427 + _random.nextInt(12), 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60446) { // ũ�������� �ַ� ��Ƽ �ʴ���
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "15th_invite"), true);
					} else if (itemId == 60424) { // ���� ���� ���� (���)
						L1SkillUse su = new L1SkillUse();
						su.handleCommands(pc, L1SkillId.ADVANCE_SPIRIT, pc.getId(), pc.getX(), pc.getY(), null, 0,
								L1SkillUse.TYPE_GMBUFF);
						su = null;
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60425) { // ���� ���� ���� (�巡�� ��Ų)
						L1SkillUse su = new L1SkillUse();
						su.handleCommands(pc, L1SkillId.DRAGON_SKIN, pc.getId(), pc.getX(), pc.getY(), null, 0,
								L1SkillUse.TYPE_GMBUFF);
						su = null;
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60426) { // ���� ���� ���� (���̾� ��Ų)
						L1SkillUse su = new L1SkillUse();
						su.handleCommands(pc, L1SkillId.IRON_SKIN, pc.getId(), pc.getX(), pc.getY(), null, 0,
								L1SkillUse.TYPE_GMBUFF);
						su = null;
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60422) { // �巡�� Ű ������
						pc.getInventory().removeItem(useItem, 1);
						L1ItemInstance item = pc.getInventory().storeItem(40308, 10000000);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10000000)"), true);
					} else if (itemId == 60411) { // ������ ����
						����������(pc, useItem);
					} else if (itemId == 60391) { // ���� ������ ���ɼ� �ָӴ�
						�������������ɼ��ָӴ�(pc, useItem);
					} else if (itemId == 60392) {// ���� ������ ���ɼ�
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ev_siege"), true);
					} else if (itemId == 60383) { // �� ���� ������
						�鸶��������(pc, useItem, l1iteminstance1);
					} else if (itemId == 9095) { // ���� ����
						��������(pc, useItem, l1iteminstance1);
					} else if (itemId == 60380) { // �⺻ ���� �����
						pc.getInventory().removeItem(useItem, 1);
						Beginner.getInstance().GiveItem�����(pc);
					} else if (itemId == 60378) { // ��� Ȯ�� ����
						if (���Ȯ�屸��(pc))
							pc.getInventory().removeItem(useItem, 1);
					} else if (itemId >= 60373 && itemId <= 60377) {
						�ű��ѹ���4������(pc, itemId);
					} else if ((itemId >= 60413 && itemId <= 60416) || (itemId >= 60418 && itemId <= 60421)) {
						�������ǹ���(pc, itemId, useItem);
					} else if (itemId >= 60361 && itemId <= 60372) {
						����Ƽ����(pc, itemId, useItem);
					} else if (itemId == 60353) { // �ÿ��� �������� ����
						L1ItemInstance item = pc.getInventory().storeItem(60354, 15);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (15)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60354) { // �ÿ��� ���� ����
						useCashScroll(pc, 60354, false);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60350) {
						if (AntarasRaidSystem.getInstance().startRaid(pc)) {
							L1World.getInstance().broadcastServerMessage(
									"��ö ��� ������: ��...�巡���� ���¢���� ������� �鸮��. �ʽ� ������ �巡�� ��Ż�� �� ���� Ȯ���Ͽ�! �غ�� �巡�� �����̾�� ������ �ູ��!");
							pc.getInventory().removeItem(useItem, 1);
						}
					} else if (itemId == 60351) {
						if (PaPooRaidSystem.getInstance().startRaid(pc)) {
							L1World.getInstance().broadcastServerMessage(
									"��ö ��� ������: ��...�巡���� ���¢���� ������� �鸮��. �ʽ� ������ �巡�� ��Ż�� �� ���� Ȯ���Ͽ�! �غ�� �巡�� �����̾�� ������ �ູ��!");
							pc.getInventory().removeItem(useItem, 1);
						}
					} else if (itemId == 60352) {
						if (LindRaid.get().start(pc)) {
							pc.getInventory().removeItem(useItem, 1);
						}
					} else if (itemId == 49013) {// ������ �ֹ���
						try {
							pc.setCurrentHp(0);
							pc.death(null);
							L1ItemInstance item = pc.getInventory().storeItem(49014, 1);
							pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						} catch (Exception e) {
						}
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 40312 || itemId == 49312) {// ���� ����
						����Ű(pc, useItem);
					} else if (itemId == 60333 || itemId == 60476 || itemId == 60477) {// ����
																						// ���
																						// ��
						������Ӹ�(pc, useItem, l1iteminstance1);
					} else if (itemId == 600228) {// ���� ��� ��
						�����Ǹ�(pc, useItem, l1iteminstance1);
					} else if (itemId == 60317) {// ����ǹ�����������
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (60 * 60000 * 1)) {
							L1ItemInstance item = pc.getInventory().storeItem(60029, 200);
							pc.sendPackets(new S_ServerMessage(403, item.getName() + " (200)"), true);
							item = pc.getInventory().storeItem(60313, 2);
							pc.sendPackets(new S_ServerMessage(403, item.getName() + " (2)"), true);
							useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
							pc.getInventory().removeItem(useItem, 1);
						} else {
							long i = (lastUsed.getTime() + (60 * 60000 * 1)) - currentDate.getTimeInMillis();
							Calendar cal = (Calendar) currentDate.clone();
							cal.setTimeInMillis(cal.getTimeInMillis() + i);
							pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����(" + cal.getTime().getHours() + ":"
									+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
						}
					} else

					if (itemId == 60311 || itemId == 60312) { // �⸣Ÿ���� ���
						�⸣Ÿ���ǻ��TOTAL(pc, itemId);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60306) { // ���׽��� ���� ����
						L1ItemInstance item = pc.getInventory().storeItem(60307, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						int i = ���׽�����(pc);
						if (i != 0) {
							int count = 1;
							if (i >= 60068 && i <= 60071)
								count = 3 + _random.nextInt(7);
							item = pc.getInventory().storeItem(i, count);
							pc.sendPackets(new S_ServerMessage(403, item.getName()));
						}
						pc.getInventory().removeItem(useItem, 1);
						
					} else if (itemId == 438012) { // �ű����Ͱ����ֹ���
						if (pc.getLevel() >= Config.AUTO_REMOVECLAN) {
							pc.sendPackets(new S_SystemMessage(Config.AUTO_REMOVECLAN + "���� �̻��� �ű� ���� ���Ϳ� ������ �� �����ϴ�."));
							return;
						}
					/*	if (pc.isCrown()) {
							pc.sendPackets(new S_SystemMessage("�����ɸ��ʹ� �����Ҽ������ϴ�."));
							return;
						}*/
						if (pc.getClanid() == 0) {
							L1Clan clan = L1World.getInstance().getClan("�űԹ���");
							L1PcInstance clanMember[] = clan.getOnlineClanMember();
							for (int cnt = 0; cnt < clanMember.length; cnt++) {
								clanMember[cnt].sendPackets(new S_ServerMessage(94, pc.getName()));
							}
							pc.setClanid(Config.PROTECT_CLAN_ID);
							pc.setClanname("�űԹ���");
							pc.setTitle(" ");
							pc.setClanRank(L1Clan.CLAN_RANK_PUBLIC);
							pc.sendPackets(new S_CharTitle(pc.getId(), ""));
							Broadcaster.broadcastPacket(pc, new S_CharTitle(pc.getId(), ""));
							pc.setClanJoinDate(new Timestamp(System.currentTimeMillis()));
							pc.sendPackets(new S_CharTitle(pc.getId(), ""));
							clan.addClanMember(pc.getName(), pc.getClanRank(), pc.getLevel(), pc.getType(), pc.getMemo(),pc.getOnlineStatus(), pc);
							try {
								pc.save();
							} catch (Exception e) { }
							pc.sendPackets(new S_PacketBox(S_PacketBox.MSG_RANK_CHANGED, 0x07, pc.getName()), true);
							pc.sendPackets(new S_PacketBox(S_PacketBox.WORLDMAP_UNKNOWN1), true);

							pc.sendPackets(new S_ClanJoinLeaveStatus(pc), true);
							Broadcaster.broadcastPacket(pc, new S_ClanJoinLeaveStatus(pc));
							pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.CLAN_JOIN_LEAVE), true);
							Broadcaster.broadcastPacket(pc, new S_ReturnedStat(pc, S_ReturnedStat.CLAN_JOIN_LEAVE));
							pc.sendPackets(new S_ClanWindow(S_ClanWindow.����ũ����, pc.getClan().getmarkon()), true);
							pc.sendPackets(new S_�����ֽ�(pc.getClan(), 2), true);
							ClanTable.getInstance().updateClan(pc.getClan());
							if (pc != null) {
								pc.sendPackets(new S_PacketBox(pc, S_PacketBox.PLEDGE_REFRESH_PLUS));
								pc.sendPackets(new S_ServerMessage(95, clan.getClanName())); 
								// \f1%0���Ϳ������߽��ϴ�.
							}

							pc.sendPackets(new S_SystemMessage("\\fW�ű� ���� ���Ϳ� ���ԿϷ�!"));
							pc.sendPackets(new S_SystemMessage("\\fY�ű� ���� ������ �ڵ� Ż�� ������ 80���� �Դϴ�."));
							pc.sendPackets(new S_SystemMessage("\\fW����ġ ������ �޾� ���� �������� �Ͻñ� �ٶ��ϴ�."));
							L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(), false);
						} else {
							pc.sendPackets(new S_SystemMessage("����� �̹� ���Ϳ� �����Ͽ����ϴ�."));
						}
					} else if (itemId == 600218) { // ����������
						��Ƽ������(pc);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 600232) { // ��Ƽ����
						��Ƽ������(pc);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 500211) { // ����������
						��Ÿ�縻(pc);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 500208) { // ��������������
						L1ItemInstance item = pc.getInventory().storeItem(500206, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						���ۻ���(pc);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 600221) { // ���� ���� ����
						L1ItemInstance item = pc.getInventory().storeItem(500206, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						���ۼ�������(pc);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60307) { // ���׽��� ���� �ָӴ�
						if (pc.getInventory().checkItem(21157)) {
							pc.sendPackets(new S_SystemMessage("Ư�� �������� �κ��丮�� �̹� �־� �� ���� �� �����ϴ�."));
							return;
						}
						L1ItemInstance item = pc.getInventory().storeItem(21157, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60272) {// ������ ť�� ����
						L1ItemInstance item = pc.getInventory().storeItem(60267, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60271) {// ������ �������� ����
						L1ItemInstance item = pc.getInventory().storeItem(21138, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60268) {// ������Ÿ�� ����
						L1ItemInstance item = pc.getInventory().storeItem(60261, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						item = pc.getInventory().storeItem(60259, 10);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10)"), true);
						item = pc.getInventory().storeItem(60260, 10);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60269) {// �� ����
						L1ItemInstance item = pc.getInventory().storeItem(60262, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						item = pc.getInventory().storeItem(60259, 10);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10)"), true);
						item = pc.getInventory().storeItem(60260, 10);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60270) {// è�Ǿ� ����
						L1ItemInstance item = pc.getInventory().storeItem(60263, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						item = pc.getInventory().storeItem(60259, 10);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10)"), true);
						item = pc.getInventory().storeItem(60260, 10);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60267) {// ������ ť��
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (60 * 60000 * 3)) {
							int i = ����ť��(pc);
							int count = 1;
							if (i == 40014)
								count = 6;
							else if (i == 40031)
								count = 2;
							else if (i == 40068)
								count = 4;
							else if (i == 40440)
								count = _random.nextInt(2) + 1;
							else if (i == 40467)
								count = _random.nextInt(2) + 1;
							L1ItemInstance item = pc.getInventory().storeItem(i, count);
							pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
							useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
							useItem.setChargeCount(useItem.getChargeCount() - 1);
							pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
							if (useItem.getChargeCount() <= 0)
								pc.getInventory().removeItem(useItem, 1);
						} else {
							long i = (lastUsed.getTime() + (60 * 60000 * 3)) - currentDate.getTimeInMillis();
							Calendar cal = (Calendar) currentDate.clone();
							cal.setTimeInMillis(cal.getTimeInMillis() + i);
							pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����(" + cal.getTime().getHours() + ":"
									+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
						}
					} else if (itemId == 60257) {// �巡���� �ڼ��� ����
						L1ItemInstance item = pc.getInventory().storeItem(60256, 3);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60256) {// �巡���� �ڼ��� ����
						if (pc.getInventory().checkItem(60255)) {
							pc.sendPackets(new S_ServerMessage(2887), true);
							return;
						}
						L1ItemInstance item = pc.getInventory().storeItem(60255, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId >= 60273 && itemId <= 60282) {
						����������and��(pc, useItem, l1iteminstance1);
					} else if (itemId == 60247) {// õ�� ��(�ǵ��� ���Ƽ)
						�ǵ������and����(pc, useItem, l1iteminstance1);
					} else if (itemId == 60254) {// �׽�Ʈ �����
						pc.getInventory().removeItem(useItem, 1);
						�׽�Ʈ�����(pc);
					} else if (itemId == 9094) {// ��� ���� ����
						pc.getInventory().removeItem(useItem, 1);
						��ǳ����������(pc);
					} else if (itemId >= 60235 && itemId <= 60246 || itemId == 60283) {
						Ƽ��ȯ��(pc, useItem, l1iteminstance1);
					} else if (itemId >= 60218 && itemId <= 60232) {
						�ǵ������and����(pc, useItem, l1iteminstance1);
					} else if (itemId == 60208) { // �Ϸ��� ü������
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�Ϸº���))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.�Ϸº���);
						else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ø����))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ø����);
						else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���ĺ���))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.���ĺ���);

						pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.�Ϸº���, 900 * 1000);
						pc.addHitup(5);
						pc.addDmgup(3);
						pc.getAbility().addAddedStr((byte) 1);
						pc.sendPackets(new S_OwnCharStatus2(pc), true);
						pc.sendPackets(new S_SkillSound(pc.getId(), 7954), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7954), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60209) { // ��ø�� ��������
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�Ϸº���))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.�Ϸº���);
						else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ø����))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ø����);
						else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���ĺ���))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.���ĺ���);

						pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ø����, 900 * 1000);
						pc.addBowHitup(5);
						pc.addBowDmgup(3);
						pc.getAbility().addAddedDex((byte) 1);
						pc.sendPackets(new S_OwnCharStatus2(pc), true);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
						pc.sendPackets(new S_SkillSound(pc.getId(), 7952), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7952), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60210) { // ������ ���Ϻ���
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�Ϸº���))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.�Ϸº���);
						else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ø����))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ø����);
						else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.���ĺ���))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.���ĺ���);

						pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.���ĺ���, 900 * 1000);
						pc.addMaxMp(50);
						pc.getAbility().addAddedInt((byte) 1);
						pc.sendPackets(new S_OwnCharStatus2(pc), true);
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
						pc.sendPackets(new S_SkillSound(pc.getId(), 7956), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7956), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60211) { // ���� ���� �����
						int rnd = (new Random(System.nanoTime())).nextInt(3) + 1;
						L1ItemInstance item = pc.getInventory().storeItem(60212, rnd);
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (" + rnd + ")"), true);
						// pc.sendPackets(new
						// S_SystemMessage(item.getName()+" ("+rnd+")�� ������ϴ�."),
						// true);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 60201) { // ���ε� ������ ž 1�� �̵� ����
						L1ItemInstance item = pc.getInventory().storeItem(60202, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						// pc.sendPackets(new
						// S_SystemMessage(item.getName()+"�� ������ϴ�."), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60197) { // ȣ�� ���� ��ȣ �ֹ���
						if (l1iteminstance1 != null
								&& ((l1iteminstance1.getItemId() >= 263 && l1iteminstance1.getItemId() <= 265)
										|| l1iteminstance1.getItemId() == 256 || l1iteminstance1.getItemId() == 4500027
										|| l1iteminstance1.getItemId() == 4500026)) {
							if (l1iteminstance1.getEnchantLevel() >= 0 && l1iteminstance1.getEnchantLevel() <= 12) {
								l1iteminstance1.setDemonBongin(true);
								pc.getInventory().removeItem(useItem, 1);
								pc.sendPackets(new S_SystemMessage(l1iteminstance1.getName() + "�� �ҷ����� ��ȣ�� �������ϴ�."),
										true);
							} else
								pc.sendPackets(new S_SystemMessage("��ȭ ��ġ�� 0�̻� 12������ �ҷ��� ���⿡�� ��� �����մϴ�."), true);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
							return;
						}
					} else if (itemId == 60190 || itemId == 60191) {// �ҷ��� ȣ��
																	// ü�μҵ�, Ű��ũ
																	// ����
						int i = �ҷ���ü��Ű��ũ����(pc, itemId);
						L1ItemInstance item = pc.getInventory().storeItem(i, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
						// pc.sendPackets(new
						// S_SystemMessage(item.getName()+"�� ������ϴ�."), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60185) { // �巡���� ť��
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed == null
								|| currentDate.getTimeInMillis() > lastUsed.getTime() + (60 * 60000 * 24)) {
							int i = �巡��ť��(pc);
							L1ItemInstance item = pc.getInventory().storeItem(i, 1);
							pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
							// pc.sendPackets(new
							// S_SystemMessage(item.getName()+"�� ������ϴ�."),
							// true);
							useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
							useItem.setChargeCount(useItem.getChargeCount() - 1);
							pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
							if (useItem.getChargeCount() <= 0)
								pc.getInventory().removeItem(useItem, 1);
						} else {
							/*
							 * SimpleDateFormat dateFormat = new
							 * SimpleDateFormat("HH�ð� mm��"); String time =
							 * dateFormat.format(new
							 * Timestamp(((lastUsed.getTime() + (60 * 60000 *
							 * 24)) - currentDate.getTimeInMillis()) + (60 *
							 * 1000 * 60 * 15))); pc.sendPackets(new
							 * S_SystemMessage(time+" �Ŀ� ��� �Ҽ� �ֽ��ϴ�."), true);
							 * dateFormat = null;
							 */

							long i = (lastUsed.getTime() + (60 * 60000 * 24)) - currentDate.getTimeInMillis();
							Calendar cal = (Calendar) currentDate.clone();
							cal.setTimeInMillis(cal.getTimeInMillis() + i);
							pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����(" + cal.getTime().getHours() + ":"
									+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
						}
					} else if (itemId == 60167) { // �Ƚ��� ���� ����
						pc.�Ƚþ����ۻ��id = useItem.getId();
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "pixies"), true);
					} else if (itemId == 60308) { // ���� ���� �ֹ���
						pc.���������ۻ��id = useItem.getId();
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "dionsevent"), true);
					} else if (itemId == 60325) { // ���� ���� �ֹ��� (80������)
						pc.����80���ž����ۻ��id = useItem.getId();
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "hero80"), true);
					} else if (itemId == 60252) { // ������� �� �ָӴ�
						int itemi = ������ָӴ�(pc);
						int itemcount = 1;

						if (itemi >= 40044 && itemi <= 40055)
							itemcount = 30;

						L1ItemInstance temptem2 = pc.getInventory().storeItem(itemi, itemcount);
						if (temptem2.isStackable())
							pc.sendPackets(new S_ServerMessage(403, temptem2.getName() + " (" + itemcount + ")"), true);
						else
							pc.sendPackets(new S_ServerMessage(403, temptem2.getLogName()), true);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 8000) {// 82�����
						L1PolyMorph.doPoly(pc, 13153, 1800, L1PolyMorph.MORPH_BY_ITEMMAGIC);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 8001) {// 85�����
						L1PolyMorph.doPoly(pc, 13152, 1800, L1PolyMorph.MORPH_BY_ITEMMAGIC);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 50757) { // �����ǻ�
						int itemi = �����ǻ�(pc);
						int itemcount = 1;
						L1ItemInstance temptem2 = pc.getInventory().storeItem(itemi, itemcount);
						if (temptem2.isStackable())
							pc.sendPackets(new S_ServerMessage(403, temptem2.getName() + " (" + itemcount + ")"), true);
						else
							pc.sendPackets(new S_ServerMessage(403, temptem2.getLogName()), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60184) { // �����ں��� ��
						int itemi = �����ں���(pc);
						int itemcount = 1;

						if (itemi >= 40044 && itemi <= 40055)
							itemcount = 30;

						L1ItemInstance temptem2 = pc.getInventory().storeItem(itemi, itemcount);
						if (temptem2.isStackable())
							pc.sendPackets(new S_ServerMessage(403, temptem2.getName() + " (" + itemcount + ")"), true);
						else
							pc.sendPackets(new S_ServerMessage(403, temptem2.getLogName()), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60171) { // �Ƚó����� ȯ������
						int count = _random.nextInt(29) + 1;
						pc.getInventory().storeItem(41159, count); // �ź��� ��������
						pc.sendPackets(new S_SystemMessage("�ź��� �������� (" + count + ")�� �����Ǿ����ϴ�."), true);
						if (_random.nextInt(100) + 1 < 20) {
							pc.getInventory().storeItem(60167, 5); // �Ƚ� ���Ÿ���
							pc.sendPackets(new S_SystemMessage("�Ƚ� ���Ÿ��� (5)�� �����Ǿ����ϴ�."), true);
						}
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 40304) { // ������ ����
						int count = _random.nextInt(6) + 5;
						pc.getInventory().storeItem(40318, count); // ����
						pc.sendPackets(new S_SystemMessage("������ �� (" + count + ")�� �����Ǿ����ϴ�."), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 40305) { // �ľƱ׸����� ����
						int count = _random.nextInt(6) + 5;
						pc.getInventory().storeItem(40320, count); // �渶��
						pc.sendPackets(new S_SystemMessage("�渶�� (" + count + ")�� �����Ǿ����ϴ�."), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 40306) { // ������ ����
						int count = _random.nextInt(6) + 5;
						pc.getInventory().storeItem(40319, count); // ���ɿ�
						pc.sendPackets(new S_SystemMessage("���ɿ� (" + count + ")�� �����Ǿ����ϴ�."), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 40307) { // �������� ����
						int count = _random.nextInt(20) + 1;
						pc.getInventory().storeItem(40318, count); // ����
						pc.sendPackets(new S_SystemMessage("������ �� (" + count + ")�� �����Ǿ����ϴ�."), true);
						count = _random.nextInt(30) + 1;
						pc.getInventory().storeItem(40319, count); // ���ɿ�
						pc.sendPackets(new S_SystemMessage("���ɿ� (" + count + ")�� �����Ǿ����ϴ�."), true);
						count = _random.nextInt(20) + 1;
						pc.getInventory().storeItem(40320, count); // �渶��
						pc.sendPackets(new S_SystemMessage("�渶�� (" + count + ")�� �����Ǿ����ϴ�."), true);
						count = _random.nextInt(5) + 1;
						pc.getInventory().storeItem(40031, count); // �Ǹ�����
						pc.sendPackets(new S_SystemMessage("�Ǹ��� �� (" + count + ")�� �����Ǿ����ϴ�."), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60149) { // ������ �ָӴ�
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (20 * 60000)) {
							pc.getInventory().storeItem(60150, 1);
							pc.sendPackets(new S_SystemMessage("������ ������ �����Ǿ����ϴ�."), true);
							useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
							pc.getInventory().removeItem(useItem, 1);
						} else {
							/*
							 * SimpleDateFormat dateFormat = new
							 * SimpleDateFormat("HH�ð� mm��"); String time =
							 * dateFormat.format(new
							 * Timestamp(((lastUsed.getTime() + (20 * 60000)) -
							 * currentDate.getTimeInMillis()) + (60 * 1000 * 60
							 * * 15))); pc.sendPackets(new S_SystemMessage(time+
							 * " �Ŀ� ��� �Ҽ� �ֽ��ϴ�."), true); dateFormat = null;
							 */
							long i = (lastUsed.getTime() + (20 * 60000)) - currentDate.getTimeInMillis();
							Calendar cal = (Calendar) currentDate.clone();
							cal.setTimeInMillis(cal.getTimeInMillis() + i);
							pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����(" + cal.getTime().getHours() + ":"
									+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
						}
					} else if (itemId == 60159) { // �巡��� �������� �ָӴ�
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (60 * 60000 * 1)) {
							pc.getInventory().storeItem(60160, 1);
							pc.sendPackets(new S_SystemMessage("������ ������ �����Ǿ����ϴ�."), true);
							useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
							pc.getInventory().removeItem(useItem, 1);
						} else {
							long i = (lastUsed.getTime() + (60 * 60000 * 1)) - currentDate.getTimeInMillis();
							Calendar cal = (Calendar) currentDate.clone();
							cal.setTimeInMillis(cal.getTimeInMillis() + i);
							pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����(" + cal.getTime().getHours() + ":"
									+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
						}
					} else if (itemId == 60156) { // ������ ��� ����
						�������(useItem, pc);
					} else if (itemId == 60126) {// ����7��ũ
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 259, 1, 7);
					} else if (itemId == 60127) {// ����8��ũ
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 259, 1, 8);
					} else if (itemId == 60128) {// ����7����
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 260, 1, 7);
					} else if (itemId == 60129) {// ����8����
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 260, 1, 8);
					} else if (itemId == 60124) { // �Ƹ� �극��ũ
						�ƸӺ극��ũ(pc, useItem, spellsc_objid);
					} else if (itemId == 60105) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p220"), true);
					} else if (itemId == 60106) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p221"), true);
					} else if (itemId == 60107) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p222"), true);
					} else if (itemId == 60108) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p223"), true);
					} else if (itemId == 60109) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p224"), true);
					} else if (itemId == 60110) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p225"), true);
					} else if (itemId == 60111) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p226"), true);
					} else if (itemId == 60112) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p227"), true);
					} else if (itemId == 60113) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p228"), true);
					} else if (itemId == 60114) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p229"), true);
					} else if (itemId == 60115) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p230"), true);
					} else if (itemId == 60116) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p231"), true);
					} else if (itemId == 60117) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p232"), true);
					} else if (itemId == 60118) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p233"), true);
					} else if (itemId == 60119) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p234"), true);
					} else if (itemId == 60120) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p235"), true);
					} else if (itemId == 60121) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p236"), true);
					} else if (itemId == 60122) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p237"), true);
					} else if (itemId == 60123) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "j_ep0p200"), true);
					} else if (itemId == 60104) { // �������⺸ȣ�ֹ���
						if (l1iteminstance1 != null && l1iteminstance1.getItemId() >= 450008
								&& l1iteminstance1.getItemId() <= 450013 || l1iteminstance1.getItemId() == 7233) {
							if (l1iteminstance1.getEnchantLevel() >= 0 && l1iteminstance1.getEnchantLevel() <= 12) {
								l1iteminstance1.setDemonBongin(true);
								pc.getInventory().removeItem(useItem, 1);
								pc.sendPackets(new S_SystemMessage(l1iteminstance1.getName() + "�� ������ ��ȣ�� �������ϴ�."),
										true);
							} else
								pc.sendPackets(new S_SystemMessage("��ȭ ��ġ�� 0�̻� 12������ ���� ���⿡�� ��� �����մϴ�."), true);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
							return;
						}
					} else if (itemId >= 60088 && itemId <= 60103) { // 1~16�ܰ�
																		// ���� ����
						// �ػ����������(pc, itemId);
						// pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 600231) { // ���� ��������
						���(pc, itemId);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 600226) { // ���� ��������
						������������(pc, itemId);
						pc.getInventory().removeItem(useItem, 1);

					} else

					/*
					 * if (itemId == 550080 || itemId == 550081 || itemId ==
					 * 550082 || itemId == 550083 || itemId == 550084 || itemId
					 * == 550085 || itemId == 550086){ //������������ if
					 * (pc.getInventory(). checkItem(550080, 1)
					 * &&pc.getInventory(). checkItem(550081, 1) &&
					 * pc.getInventory(). checkItem(550082, 1) &&//����üũ�κ�
					 * pc.getInventory(). checkItem(550083, 1)
					 * &&pc.getInventory(). checkItem(550084, 1) &&
					 * pc.getInventory(). checkItem(550085, 1) && //����üũ�κ�
					 * pc.getInventory(). checkItem(550086, 1) ){ //����üũ�κ�
					 * pc.getInventory().consumeItem(550080, 1);
					 * pc.getInventory().consumeItem(550081, 1);
					 * pc.getInventory().consumeItem(550082, 1);
					 * pc.getInventory().consumeItem(550083, 1);
					 * pc.getInventory().consumeItem(550084, 1);
					 * pc.getInventory().consumeItem(550085, 1);
					 * pc.getInventory().consumeItem(550086, 1);
					 * 
					 * pc.getInventory().storeItem(40308, 1000000);
					 * 
					 * } } else
					 */

					if (itemId == 600227) { // �����ϻ�弱��
						�����ϻ�弱��(pc, itemId);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60080) { // ����� ����� ����
						int size = pc.getBookMarkSize();
						if (size + 43 > pc.getBookmarkMax()) {
							pc.sendPackets(new S_ServerMessage(2961, "" + (size - pc.getBookmarkMax() + 43)), true);
							return;
						}
						pc.���������� = useItem.getId();
						pc.sendPackets(new S_Message_YN(2936, ""), true);
					} else if (itemId == 60081) { // ����� ���� ����
						L1ItemInstance temptem3 = pc.getInventory().storeItem(60082, 1);
						pc.sendPackets(new S_ServerMessage(403, temptem3.getName() + " (" + 1 + ")"), true);
						temptem3 = pc.getInventory().storeItem(60083, 1);
						pc.sendPackets(new S_ServerMessage(403, temptem3.getName() + " (" + 1 + ")"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60082) { // �ź��� ����� ����
						int size = pc.getBookMarkSize();
						if (size + 8 > pc.getBookmarkMax()) {
							pc.sendPackets(new S_ServerMessage(2961, "" + (size - pc.getBookmarkMax() + 8)), true);
							return;
						}
						pc.���������� = useItem.getId();
						pc.sendPackets(new S_Message_YN(3016, ""), true);
					} else if (itemId == 60083) { // ���ø����� ��� ���� ����
						int size = pc.getBookMarkSize();
						if (size <= 0) {
							pc.sendPackets(new S_ServerMessage(2963, ""), true);
							return;
						}
						// pc.���������� = useItem.getId();
						pc.sendPackets(new S_Message_YN(2935, ""), true);
					} else if (itemId == 60084) { // ����� ����
						int size = pc.getBookMarkSize();
						int itemsize = L1BookMark.ItemBookmarkChehck(useItem.getId());
						if (size + itemsize > pc.getBookmarkMax()) {
							pc.sendPackets(new S_ServerMessage(2961, "" + (size - pc.getBookmarkMax() + itemsize)),
									true);
							return;
						}
						pc.���������� = useItem.getId();
						pc.sendPackets(new S_Message_YN(2936, ""), true);

					} else if (itemId == 60076) { // �Ϸ��� ���ö� �ٱ���
						int rnd = _random.nextInt(100) + 1;
						int count = 0;
						if (rnd <= 80)
							count = 1;
						else
							count = 2;
						L1ItemInstance temptem3 = pc.getInventory().storeItem(60072, count);
						pc.sendPackets(new S_ServerMessage(403, temptem3.getName() + " (" + count + ")"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60077) { // ��ø�� ���ö� �ٱ���
						int rnd = _random.nextInt(100) + 1;
						int count = 0;
						if (rnd <= 80)
							count = 1;
						else
							count = 2;
						L1ItemInstance temptem3 = pc.getInventory().storeItem(60073, count);
						pc.sendPackets(new S_ServerMessage(403, temptem3.getName() + " (" + count + ")"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60078) { // ������ ���ö� �ٱ���
						int rnd = _random.nextInt(100) + 1;
						int count = 0;
						if (rnd <= 80)
							count = 1;
						else
							count = 2;
						L1ItemInstance temptem3 = pc.getInventory().storeItem(60074, count);
						pc.sendPackets(new S_ServerMessage(403, temptem3.getName() + " (" + count + ")"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60079) { // ������ ���ö� �ٱ���
						int rnd = _random.nextInt(100) + 1;
						int count = 0;
						if (rnd <= 80)
							count = 1;
						else
							count = 2;
						L1ItemInstance temptem3 = pc.getInventory().storeItem(60075, count);
						pc.sendPackets(new S_ServerMessage(403, temptem3.getName() + " (" + count + ")"), true);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 500210) { // ������ ������ �ֹ�
						if (pc.getSkillEffectTimerSet().hasSkillEffect(DRESS_MIGHTY))
							pc.getSkillEffectTimerSet().removeSkillEffect(DRESS_MIGHTY);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(PHYSICAL_ENCHANT_STR))
							pc.getSkillEffectTimerSet().removeSkillEffect(PHYSICAL_ENCHANT_STR);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(DRESS_MIGHTY))
							pc.getSkillEffectTimerSet().removeSkillEffect(DRESS_MIGHTY);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(PHYSICAL_ENCHANT_STR))
							pc.getSkillEffectTimerSet().removeSkillEffect(PHYSICAL_ENCHANT_STR);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����7))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_����7);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����6))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_����6);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����6))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_����6);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����7))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_����7);

						if (pc.getLevel() >= 65) {
							pc.getAbility().addAddedStr((byte) 6);
							pc.getSkillEffectTimerSet().setSkillEffect(STATUS_����6, 1000 * 1800);
							pc.sendPackets(new S_Strup(pc, 6, 1800), true);
							pc.getSkillEffectTimerSet().setSkillEffect(STATUS_����6, 1000 * 1800);
							pc.getAbility().addAddedDex((byte) 6);
							pc.sendPackets(new S_Dexup(pc, 6, 1800), true);
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
						} else {
							pc.getAbility().addAddedStr((byte) 7);
							pc.getSkillEffectTimerSet().setSkillEffect(STATUS_����7, 1000 * 1800);
							pc.sendPackets(new S_Strup(pc, 7, 1800), true);
							pc.getSkillEffectTimerSet().setSkillEffect(STATUS_����7, 1000 * 1800);
							pc.getAbility().addAddedDex((byte) 7);
							pc.sendPackets(new S_Dexup(pc, 7, 1800), true);
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
						}
						pc.sendPackets(new S_SkillSound(pc.getId(), 9736), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 9736), true);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 60068) { // ������ ����� �ֹ�
						if (pc.getSkillEffectTimerSet().hasSkillEffect(DRESS_MIGHTY))
							pc.getSkillEffectTimerSet().removeSkillEffect(DRESS_MIGHTY);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(PHYSICAL_ENCHANT_STR))
							pc.getSkillEffectTimerSet().removeSkillEffect(PHYSICAL_ENCHANT_STR);
						if (pc.getLevel() >= 65) {
							if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����7))
								pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_����7);
							if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����6)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_����6);
							} else
								pc.getAbility().addAddedStr((byte) 6);
							pc.getSkillEffectTimerSet().setSkillEffect(STATUS_����6, 1000 * 1200);
							pc.sendPackets(new S_Strup(pc, 6, 1200), true);
						} else {
							if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����6))
								pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_����6);
							if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����7)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_����7);
							} else
								pc.getAbility().addAddedStr((byte) 7);
							pc.getSkillEffectTimerSet().setSkillEffect(STATUS_����7, 1000 * 1200);

							pc.sendPackets(new S_Strup(pc, 7, 1200), true);
						}
						pc.sendPackets(new S_SkillSound(pc.getId(), 191), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 191), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60069) { // ������ �ü��� �ֹ�
						if (pc.getSkillEffectTimerSet().hasSkillEffect(DRESS_DEXTERITY))
							pc.getSkillEffectTimerSet().removeSkillEffect(DRESS_DEXTERITY);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(PHYSICAL_ENCHANT_DEX))
							pc.getSkillEffectTimerSet().removeSkillEffect(PHYSICAL_ENCHANT_DEX);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����6))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_����6);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_����7))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_����7);
						if (pc.getLevel() >= 65) {
							pc.getSkillEffectTimerSet().setSkillEffect(STATUS_����6, 1000 * 1200);
							pc.getAbility().addAddedDex((byte) 6);
							pc.sendPackets(new S_Dexup(pc, 6, 1200), true);
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
						} else {
							pc.getSkillEffectTimerSet().setSkillEffect(STATUS_����7, 1000 * 1200);
							pc.getAbility().addAddedDex((byte) 7);
							pc.sendPackets(new S_Dexup(pc, 7, 1200), true);
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
						}
						pc.sendPackets(new S_SkillSound(pc.getId(), 191), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 191), true);
						pc.sendPackets(new S_ServerMessage(294), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60070) { // ������ ������ �ֹ�
						if (pc.getSkillEffectTimerSet().hasSkillEffect(DECAY_POTION)) { // ����������
																						// ����
							pc.sendPackets(new S_ServerMessage(698), true); // \f1���¿�
																			// ����
																			// �ƹ��͵�
																			// ����
																			// ����
																			// �����ϴ�.
							return;
						}
						// �ƺ�Ҹ�Ʈ�ٸ����� ����
						pc.cancelAbsoluteBarrier();
						pc.setCurrentMp(pc.getCurrentMp() + (_random.nextInt(5) + 8));
						pc.sendPackets(new S_SkillSound(pc.getId(), 190), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 190), true);
						pc.sendPackets(new S_ServerMessage(77), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60066) { // �ǵ����� ���� ����
						L1ItemInstance temptem = pc.getInventory().storeItem(60067, 1);
						pc.sendPackets(new S_ServerMessage(403, temptem.getName() + " (" + 1 + ")"), true);
						int itemi = �ǵ�����������(pc);

						int[] magiitem = { 60068, 60069, 60070, 60071 };
						int count = 0;
						int itemid = magiitem[_random.nextInt(magiitem.length)];
						if (itemid == 60068 || itemid == 60069) {
							int[] cc = { 5, 10, 15 };
							count = cc[_random.nextInt(3)];
							cc = null;
						} else if (itemid == 60070) {
							int[] cc = { 3, 6, 9 };
							count = cc[_random.nextInt(3)];
							cc = null;
						} else if (itemid == 60071) {
							int[] cc = { 20, 40, 60 };
							count = cc[_random.nextInt(3)];
							cc = null;
						}
						L1ItemInstance temptem3 = pc.getInventory().storeItem(itemid, count);
						pc.sendPackets(new S_ServerMessage(403, temptem3.getName() + " (" + count + ")"), true);
						magiitem = null;
						if (itemi != 0) {
							L1ItemInstance temptem2 = pc.getInventory().storeItem(itemi, 1);
							if (temptem2.isStackable())
								pc.sendPackets(new S_ServerMessage(403, temptem2.getName() + " (" + 1 + ")"), true);
							else
								pc.sendPackets(new S_ServerMessage(403, temptem2.getLogName()), true);
						} else {
							pc.sendPackets(new S_SystemMessage("�ǵ����� ���� ���ڿ��� �������� ���� ���Ͽ����ϴ�."), true);
						}
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60067) { // ������ ���� ���� �ָӴ�
						if (pc.getInventory().checkItem(21094)) {
							pc.sendPackets(new S_SystemMessage("Ư�� �������� �κ��丮�� �̹� �־� �� ���� �� �����ϴ�."));
						} else {
							L1ItemInstance temptem = pc.getInventory().storeItem(21094, 1);
							pc.sendPackets(new S_ServerMessage(403, temptem.getLogName()), true);
							pc.getInventory().removeItem(useItem, 1);
						}
					} else if (itemId == 60065) { // ����
						S_SystemMessage smm = new S_SystemMessage(
								"====================== �� �� �� =======================");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".����                [65�� ���ϱ��� �⺻���� ���� ����]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".����                              [���� �ɸ����� ����]");
						pc.sendPackets(smm);
						// smm = new
						// S_SystemMessage(".���� [���� ����� ��Ÿ+1,����+1,SP+1 ����+3
						// ����]");
						// pc.sendPackets(smm);
						smm = new S_SystemMessage(".���                [�� �������� ����ϴ� ��� ����]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".�����                 [���Ͱ� ����ϴ� ������ �˻�]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage("..               [�ڷ��� �ɸ��� �ڷ��� Ǯ���ִ� ��ɾ�]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".��ȣ����                  [���� ���� ��ȣ ����� ���]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".����                    [���� ä��â ���� ������ ���]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".��������                          [���� �����Ҷ� ���]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".�����Ʈ                   [������� �Ǵ� ��Ʈ ON/OFF]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".�����                          [���ΰ��� ���� ����]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".����������û                     [���������ֹ��� ��û]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".�����ð�                         [��� ���� �ð� Ȯ��]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".������û     [������ ���� ������ ���� �߼۵˴ϴ�.]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage("/����                      [�����ϰ� �����ϸ� ���λ���]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".������û  [���� Ŭ���� ���� ��ɾ�� ��ũ �ٿ�� ���]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".��������  [���� Ŭ���� ���� ��ɾ�� ��ũ ������ ���]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".��ŷ��ȸ                         [�����ɸ��� ��ŷ�˻�]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".��ŷ�˻�                         [Ÿ �ɸ��� ��ŷ �˻�]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage(".������¥   [������� ��Ʈ���� ���� ��¥�� �˷��ݴϴ�.]");
						pc.sendPackets(smm);
						smm = new S_SystemMessage("=======================================================");
						pc.sendPackets(smm, true);
					} else if (itemId == 60062) {
						S_SystemMessage smm = new S_SystemMessage("���� ���� ��ȣ���� 10000 ��� �Ͽ����ϴ�.");
						pc.sendPackets(smm, true);
						pc.addKarma((int) (-10000 * Config.RATE_KARMA));
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60063) {
						S_SystemMessage smm = new S_SystemMessage("�߷� ���� ��ȣ���� 10000 ��� �Ͽ����ϴ�.");
						pc.sendPackets(smm, true);
						pc.addKarma((int) (10000 * Config.RATE_KARMA));
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60064) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_���ึ��ȸ����)) {
							int time = pc.getSkillEffectTimerSet().getSkillEffectTimeSec(L1SkillId.STATUS_���ึ��ȸ����);
							S_SystemMessage smm = new S_SystemMessage(time + "�� �Ŀ� ��� �Ҽ� �ֽ��ϴ�.");
							pc.sendPackets(smm, true);
							return;
						}
						pc.setCurrentMp(pc.getCurrentMp() + 10000);
						S_SystemMessage smm = new S_SystemMessage("����� ���������ϴ�.");
						pc.sendPackets(smm, true);
						pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_���ึ��ȸ����, 60000 * 30);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60060) { // ��Ƽ �ʴ� ������
						L1Object temp = L1World.getInstance().findObject(spellsc_objid);
						if (temp == null) {
							S_SystemMessage smm = new S_SystemMessage("����� �����ϴ�.");
							pc.sendPackets(smm, true);
							return;
						}
						if (temp instanceof L1PcInstance) {
							L1PcInstance targetPc = (L1PcInstance) temp;
							// if (pc.getId() == targetPc.getId()) return;
							if (targetPc.isInParty()) {
								// ���� �ٸ� ��Ƽ�� �Ҽ��� �ֱ� (����)������ �ʴ��� �� �����ϴ�
								S_ServerMessage sm = new S_ServerMessage(415);
								pc.sendPackets(sm, true);
								return;
							}
							if (pc.isInParty()) {
								if (pc.getParty().isLeader(pc)) {
									targetPc.setPartyID(pc.getId());
									// \f2%0\f>%s�κ��� \fU��Ƽ \f> �� �ʴ�Ǿ����ϴ�. ���մϱ�?
									// (Y/N)
									S_Message_YN myn = new S_Message_YN(953, pc.getName());
									targetPc.sendPackets(myn, true);
								} else {
									// ��Ƽ�� �������� �ʴ��� �� �ֽ��ϴ�.
									S_ServerMessage sm = new S_ServerMessage(416);
									pc.sendPackets(sm, true);
								}
							} else {
								pc.setPartyType(0);
								targetPc.setPartyID(pc.getId());
								// \f2%0\f>%s�κ��� \fU��Ƽ \f> �� �ʴ�Ǿ����ϴ�. ���մϱ�?
								// (Y/N)
								S_Message_YN myn = new S_Message_YN(953, pc.getName());
								targetPc.sendPackets(myn, true);
							}
						} else {
							S_SystemMessage smm = new S_SystemMessage("�÷��̾�Ը� ��� �� �� �ֽ��ϴ�.");
							pc.sendPackets(smm, true);
							return;
						}
						
					} else if (itemId == 60059) { // �����ǿ�ħ ������
						Random random = new Random();
						try {
							int NewHp = 0;
							if (pc.get_food() >= 225) {
								int Enchantlvl = 0;
								try {
									Enchantlvl = pc.getWeapon().getEnchantLevel();
								} catch (Exception e) {
									// pc.sendPackets(new
									// S_SystemMessage("\\fY���⸦ �����ؾ� ������ ��ħ�� �����
									// �� �ֽ��ϴ�."));
									S_ServerMessage sm = new S_ServerMessage(1973);
									pc.sendPackets(sm, true);
									return;
								}

								if (1800000L < System.currentTimeMillis() - pc.getSurvivalCry()) {
									if (pc.getWeapon().getItemId() == 61 || pc.getWeapon().getItemId() == 86) {
										int[] probability = { 50, 60 };
										int percent = probability[random.nextInt(probability.length)];

										NewHp = pc.getCurrentHp() + pc.getMaxHp() / 100 * percent;

										if (NewHp > pc.getMaxHp()) {
											NewHp = pc.getMaxHp();
										}

										pc.setCurrentHp(NewHp);
										S_SystemMessage sm = new S_SystemMessage(
												"\\fY������ ��ħ�� ����Ͽ� �ִ�HP " + percent + "%�� ȸ���Ͽ����ϴ�.");
										pc.sendPackets(sm, true);
										S_SkillSound ss = new S_SkillSound(pc.getId(), 8773);
										pc.sendPackets(ss);
										Broadcaster.broadcastPacket(pc, ss, true);
										pc.set_food(0);
										S_PacketBox pb2 = new S_PacketBox(11, pc.get_food());
										pc.sendPackets(pb2, true);
										pc.setSurvivalCry(System.currentTimeMillis());
										probability = null;
									} else if (Enchantlvl <= 6) {
										int[] probability = { 20, 30, 40 };
										int percent = probability[random.nextInt(probability.length)];
										NewHp = pc.getCurrentHp() + pc.getMaxHp() / 100 * percent;

										if (NewHp > pc.getMaxHp()) {
											NewHp = pc.getMaxHp();
										}

										pc.setCurrentHp(NewHp);
										S_SystemMessage sm = new S_SystemMessage(
												"\\fY������ ��ħ�� ����Ͽ� �ִ�HP " + percent + "%�� ȸ���Ͽ����ϴ�.");
										pc.sendPackets(sm, true);
										S_SkillSound ss = new S_SkillSound(pc.getId(), 8684);
										pc.sendPackets(ss);
										Broadcaster.broadcastPacket(pc, ss, true);
										pc.set_food(0);
										S_PacketBox pb2 = new S_PacketBox(11, pc.get_food());
										pc.sendPackets(pb2, true);
										pc.setSurvivalCry(System.currentTimeMillis());
										probability = null;
									} else if ((Enchantlvl >= 7) && (Enchantlvl <= 8)) {
										int[] probability = { 30, 40, 50 };
										int percent = probability[random.nextInt(probability.length)];

										NewHp = pc.getCurrentHp() + pc.getMaxHp() / 100 * percent;

										if (NewHp > pc.getMaxHp()) {
											NewHp = pc.getMaxHp();
										}

										pc.setCurrentHp(NewHp);
										S_SystemMessage sm = new S_SystemMessage(
												"\\fY������ ��ħ�� ����Ͽ� �ִ�HP " + percent + "%�� ȸ���Ͽ����ϴ�.");
										pc.sendPackets(sm, true);
										S_SkillSound ss = new S_SkillSound(pc.getId(), 8685);
										pc.sendPackets(ss);
										Broadcaster.broadcastPacket(pc, ss, true);
										pc.set_food(0);
										S_PacketBox pb2 = new S_PacketBox(11, pc.get_food());
										pc.sendPackets(pb2, true);
										pc.setSurvivalCry(System.currentTimeMillis());
									} else if ((Enchantlvl >= 9) && (Enchantlvl <= 10)) {
										int[] probability = { 50, 60 };
										int percent = probability[random.nextInt(probability.length)];

										NewHp = pc.getCurrentHp() + pc.getMaxHp() / 100 * percent;

										if (NewHp > pc.getMaxHp()) {
											NewHp = pc.getMaxHp();
										}

										pc.setCurrentHp(NewHp);
										S_SystemMessage sm = new S_SystemMessage(
												"\\fY������ ��ħ�� ����Ͽ� �ִ�HP " + percent + "%�� ȸ���Ͽ����ϴ�.");
										pc.sendPackets(sm, true);
										S_SkillSound ss = new S_SkillSound(pc.getId(), 8773);
										pc.sendPackets(ss);
										Broadcaster.broadcastPacket(pc, ss, true);
										pc.set_food(0);
										S_PacketBox pb2 = new S_PacketBox(11, pc.get_food());
										pc.sendPackets(pb2, true);
										pc.setSurvivalCry(System.currentTimeMillis());
										probability = null;
									} else if (Enchantlvl >= 11) {
										NewHp = pc.getCurrentHp() + pc.getMaxHp() / 100 * 70;

										if (NewHp > pc.getMaxHp()) {
											NewHp = pc.getMaxHp();
										}

										pc.setCurrentHp(NewHp);
										S_SystemMessage sm = new S_SystemMessage("\\fY������ ��ħ�� ����Ͽ� �ִ�HP 70%�� ȸ���Ͽ����ϴ�.");
										pc.sendPackets(sm, true);
										S_SkillSound ss = new S_SkillSound(pc.getId(), 8686);
										pc.sendPackets(ss);
										Broadcaster.broadcastPacket(pc, ss, true);
										pc.set_food(0);
										S_PacketBox pb2 = new S_PacketBox(11, pc.get_food());
										pc.sendPackets(pb2, true);
										pc.setSurvivalCry(System.currentTimeMillis());
									}
								} else {
									long time = 1800L - (System.currentTimeMillis() - pc.getSurvivalCry()) / 1000L;

									long minute = time / 60L;
									long second = time % 60L;

									if (minute >= 29L) {
										S_SystemMessage sm = new S_SystemMessage(
												"\\fY������ ��ħ�� " + minute + "�� " + second + "�� �Ŀ� ���� �����մϴ�.");
										pc.sendPackets(sm, true);
										return;
									}

									NewHp = pc.getCurrentHp() + pc.getMaxHp() / 100 * (30 - (int) minute);

									if (NewHp > pc.getMaxHp()) {
										NewHp = pc.getMaxHp();
									}

									pc.setCurrentHp(NewHp);
									S_SystemMessage sm = new S_SystemMessage(
											"\\fY������ ��ħ�� ����Ͽ� �ִ�HP " + (30 - (int) minute) + "%�� ȸ���Ͽ����ϴ�.");
									pc.sendPackets(sm, true);
									S_SkillSound ss = new S_SkillSound(pc.getId(), 8683);
									pc.sendPackets(ss);
									Broadcaster.broadcastPacket(pc, ss, true);
									pc.set_food(0);
									S_PacketBox pb2 = new S_PacketBox(11, pc.get_food());
									pc.sendPackets(pb2, true);
									pc.setSurvivalCry(System.currentTimeMillis());
								}
								// ������ ����
								useItem.set_tempGfx(0);
								S_DeleteInventoryItem di = new S_DeleteInventoryItem(useItem);
								pc.sendPackets(di, true);
								S_AddItem ai = new S_AddItem(useItem);
								pc.sendPackets(ai, true);
							} else {
								// S_SystemMessage sm = new
								// S_SystemMessage("\\fY������ ��ħ�� ����İ����� 100% ä��
								// ��������,");
								// pc.sendPackets(sm); sm.clear(); sm = null;
								// pc.sendPackets(new
								// S_SystemMessage("\\fY30�еڿ� ��밡���մϴ�."));
								S_ServerMessage sm2 = new S_ServerMessage(1974);
								pc.sendPackets(sm2, true);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							random = null;
						}
					} else if (itemId == 60381 || itemId == 60384 || itemId == 7257) {
						��(pc, useItem);
					} else if (itemId == 888813) {
						���ָӴ�(pc, useItem);
					} else if (itemId == 60057) { // �� ���ϴ¼��� ������ �ָӴ�
						L1ItemInstance item1 = pc.getInventory().storeItem(60055, 3);
						L1ItemInstance item2 = pc.getInventory().storeItem(60056, 2);
						pc.sendPackets(new S_ServerMessage(403, item1.getName() + " (" + 3 + ")"), true);
						pc.sendPackets(new S_ServerMessage(403, item2.getName() + " (" + 2 + ")"), true);
						int tempid = �����Ѿ�����(pc);
						if (tempid != 0) {
							L1ItemInstance temptem = pc.getInventory().storeItem(tempid, 1);
							pc.sendPackets(new S_ServerMessage(403, temptem.getLogName()), true);
						} else {
							pc.sendPackets(new S_SystemMessage("������ �������� ���� ���Ͽ����ϴ�."), true);
						}
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60058) { // �� ���ϴ¼��� �ָӴ�
						L1ItemInstance item1 = pc.getInventory().storeItem(60055, 2);
						L1ItemInstance item2 = pc.getInventory().storeItem(60056, 1);
						pc.sendPackets(new S_ServerMessage(403, item1.getName() + " (" + 2 + ")"), true);
						pc.sendPackets(new S_ServerMessage(403, item2.getName() + " (" + 1 + ")"), true);
						int tempid = �����Ѿ�����(pc);
						if (tempid != 0) {
							L1ItemInstance temptem = pc.getInventory().storeItem(tempid, 1);
							pc.sendPackets(new S_ServerMessage(403, temptem.getLogName()), true);
						} else {
							pc.sendPackets(new S_SystemMessage("������ �������� ���� ���Ͽ����ϴ�."), true);
						}
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60056 || itemId == 60212 || itemId == 60398) { // ������
																						// �ķ�,
																						// ������
																						// ����,
																						// ���������ǹ���
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_�����ѽķ�)) {
							pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.STATUS_�����ѽķ�);
						}
						int time = 1800 * 1000;
						pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_�����ѽķ�, time);
						pc.getInventory().removeItem(useItem, 1);

						pc.sendPackets(new S_SkillSound(pc.getId(), 7541), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7541), true);
						// pc.sendPackets(new S_ServerMessage(1542));
						/** ���� ���� ���� **/
					} else if (itemId == 60041) { // ����7����
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412002, 1, 7);
					} else if (itemId == 60042) { // ����7��ǳ
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412005, 1, 7);
					} else if (itemId == 60043) { // ����7�Ĵ�
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412001, 1, 7);
					} else if (itemId == 60044) { // ����7õ��
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412003, 1, 7);
					} else if (itemId == 60045) { // ����7Ȥ��
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412004, 1, 7);
					} else if (itemId == 60046) { // ����7���Ű�
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412000, 1, 7);
					} else if (itemId == 60047) { // ����7��õ
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 191, 1, 7);
					} else if (itemId == 60048) { // ����8����
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412002, 1, 8);
					} else if (itemId == 60049) { // ����8��ǳ
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412005, 1, 8);
					} else if (itemId == 60050) { // ����8�Ĵ�
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412001, 1, 8);
					} else if (itemId == 60051) { // ����8õ��
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412003, 1, 8);
					} else if (itemId == 60052) { // ����8Ȥ��
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412004, 1, 8);
					} else if (itemId == 60053) { // ����8���Ű�
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 412000, 1, 8);
					} else if (itemId == 60054) { // ����8��õ
						pc.getInventory().removeItem(useItem, 1);
						createNewItem2(pc, 191, 1, 8);
						/** ��� �̹� �Ǹ� **/
					} else if (itemId == 60017) {
						pc.getInventory().storeItem(60027, 1);
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SkillSound(pc.getId(), 8473), true);
					} else if (itemId == 60018) {
						pc.getInventory().storeItem(60028, 1);
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SkillSound(pc.getId(), 8473), true);
					} else if (itemId == 60035) {
						pc.getInventory().storeItem(60029, 100);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60036) {
						pc.getInventory().storeItem(60029, 200);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60037) {
						pc.getInventory().storeItem(60029, 300);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60038) {
						pc.getInventory().storeItem(60030, 100);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60039) {
						pc.getInventory().storeItem(60030, 200);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60040) {
						pc.getInventory().storeItem(60030, 300);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60019) {
						pc.getInventory().storeItem(60035, 1);
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SkillSound(pc.getId(), 8473), true);
					} else if (itemId == 60020) {
						pc.getInventory().storeItem(60036, 1);
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SkillSound(pc.getId(), 8473), true);
					} else if (itemId == 60021) {
						pc.getInventory().storeItem(60037, 1);
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SkillSound(pc.getId(), 8473), true);
					} else if (itemId == 60022) {
						pc.getInventory().storeItem(60038, 1);
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SkillSound(pc.getId(), 8473), true);
					} else if (itemId == 60023) {
						pc.getInventory().storeItem(60039, 1);
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SkillSound(pc.getId(), 8473), true);
					} else if (itemId == 60024) {
						pc.getInventory().storeItem(60040, 1);
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SkillSound(pc.getId(), 8473), true);
					} else if (itemId == 60027) {
						L1SpawnUtil.spawn(pc, 45711, 0, 0, false); // �Ʊ� ������
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60028) {
						L1SpawnUtil.spawn(pc, 45313, 0, 0, false); // ȣ����
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 60025 || itemId == 60026) {// �����ѽð�
																	// 2�ð�, 4�ð�
						try {
							if (l1iteminstance1 != null) {
								if (l1iteminstance1.getItemId() >= 60011 && l1iteminstance1.getItemId() <= 60016) {
									Delivery del = DeliverySystem.getInstance().get(l1iteminstance1.getId());
									if (del != null) {
										if (del.getClockCount() <= 0) {
											pc.sendPackets(new S_SystemMessage("�� �̻� ����� �� �����ϴ�."), true);
											return;
										}
										del.setClockCount(del.getClockCount() - 1);
										Timestamp ts = del.getTime();
										if (itemId == 60026)
											ts.setTime(ts.getTime() - (3600000 * 4));
										else
											ts.setTime(ts.getTime() - (3600000 * 2));
										del.setTime(ts);
										l1iteminstance1.setEndTime(del.getTime());
										pc.getInventory().updateItem(l1iteminstance1, L1PcInventory.COL_ENDTIME);
										pc.getInventory().saveItem(l1iteminstance1, L1PcInventory.COL_ENDTIME);
										DeliverySystem.UpdateTime(del.getItemObjId(), del.getTime(),
												del.getClockCount());
									}
								} else {
									pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																									// �ƹ��͵�
																									// �Ͼ��
																									// �ʾҽ��ϴ�.
									return;
								}
							}
							pc.getInventory().removeItem(useItem, 1);
						} catch (Exception e) {
							e.printStackTrace();
						}
						/** ���ž�� ���� �ָӴ� **/

					} else if (itemId >= 600212 && itemId <= 600217) {
						��ȭ����(pc, itemId, useItem);
					} else if (itemId == 600223) {
						�Ǿ�������(pc, itemId, useItem, 7);
					} else if (itemId == 600225) {
						�Ǿ�������(pc, itemId, useItem, 30);
					} else if (itemId == 60001) {
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (60 * 60000 * 1)) {
							pc.getInventory().storeItem(60002, 1);
							pc.sendPackets(new S_SystemMessage("���ž�� ����ǰ ���� �ֹ��� �� �����Ǿ����ϴ�."), true);
							useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
						} else {
							/*
							 * SimpleDateFormat dateFormat = new
							 * SimpleDateFormat("HH�ð� mm��"); String time =
							 * dateFormat.format(new
							 * Timestamp(((lastUsed.getTime() + (60 * 60000 *
							 * 1)) - currentDate.getTimeInMillis()) + (60 * 1000
							 * * 60 * 15))); pc.sendPackets(new
							 * S_SystemMessage(time+" �Ŀ� ��� �Ҽ� �ֽ��ϴ�."), true);
							 * dateFormat = null;
							 */
							long i = (lastUsed.getTime() + (60 * 60000 * 1)) - currentDate.getTimeInMillis();
							Calendar cal = (Calendar) currentDate.clone();
							cal.setTimeInMillis(cal.getTimeInMillis() + i);
							pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����(" + cal.getTime().getHours() + ":"
									+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
						}
						/** ���ž�� ����ǰ ���� **/
					} else if (itemId == 60002) {
						pc.getInventory().storeItem(40029, 100);
						pc.getInventory().storeItem(40030, 6);
						pc.getInventory().storeItem(40095, 2);
						pc.getInventory().storeItem(40096, 2);
						pc.getInventory().removeItem(useItem, 1);
						/** ���� ť�� **/
					} else if (itemId == 90099) {
						L1PolyMorph.doPoly(pc, 5346, 30, L1PolyMorph.MORPH_BY_GM);
						pc.sendPackets(new S_SkillSound(pc.getId(), 2059), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 2059), true);
						String S[] = { "���޴�~~", "�����Ԥ�_��", "���޴�!!", "�޴��޴�~", "���˺� ���ִ�~^^*" + "���׻���!", "����~" };
						int ran = _random.nextInt(6);
						pc.sendPackets(new S_ChatPacket(pc, S[ran], Opcodes.S_SAY, 0), true);
						Broadcaster.broadcastPacket(pc, new S_ChatPacket(pc, S[ran], Opcodes.S_SAY, 0), true);
						pc.getInventory().removeItem(useItem, 1);
						S = null;
						/** ���� ť�� **/
					} else if (itemId == 6020) {
						/*
						 * int itemnum[] = {
						 * 40238,40021,40024,40088,40018,40068,40031,6022,40308
						 * }; int itemcount[] = { 1,5,5,5,3,3,2,1,3000 };
						 */
						int rnd = _random.nextInt(100) + 1;
						int itemid = 0;
						int count = 0;
						if (rnd <= 12) {
							itemid = 40021;
							count = 5;
						} else if (rnd <= 24) {
							itemid = 40024;
							count = 5;
						} else if (rnd <= 36) {
							itemid = 40088;
							count = 5;
						} else if (rnd <= 48) {
							itemid = 40018;
							count = 3;
						} else if (rnd <= 60) {
							itemid = 40068;
							count = 3;
						} else if (rnd <= 72) {
							itemid = 40031;
							count = 2;
						} else if (rnd <= 84) {
							itemid = 40308;
							count = 3000;
						} else if (rnd <= 92) {
							itemid = 40238;
							count = 1;
						} else {
							itemid = 6022;
							count = 1;
						}

						L1ItemInstance ������ = pc.getInventory().storeItem(itemid, count);// ��Ÿ������
						pc.sendPackets(new S_ServerMessage(403, ������.getName() + "(" + count + ")"), true);
						pc.getInventory().removeItem(useItem, 1);

						/** ��¦�̴� ���� ť�� **/
					} else if (itemId == 6021) {
						L1ItemInstance ������ = pc.getInventory().storeItem(6022, 2);// ȭ����
																					// ���
																					// 2
						pc.sendPackets(new S_ServerMessage(403, ������.getName() + "(2)"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 7242) {
						�����ָӴ�(pc, useItem);
						/** ������ �ָӴ� **/

					} else if (itemId == 50751) {
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed == null
								|| currentDate.getTimeInMillis() > lastUsed.getTime() + (1000 * 60 * 60 * 1)) {// 22�ð�
							if (useItem.getChargeCount() >= 1) {
								pc.getInventory().storeItem(50752, 200);
								pc.sendPackets(new S_ServerMessage(403, "������ ü�� ȸ���� (200)"), true);
							}
							useItem.setChargeCount(useItem.getChargeCount() - 1);
							pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
							if (useItem.getChargeCount() == 0) {
								pc.getInventory().removeItem(useItem);
							}
							useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
						} else {
							long i = (lastUsed.getTime() + (60 * 60000 * 1)) - currentDate.getTimeInMillis();
							Calendar cal = (Calendar) currentDate.clone();
							cal.setTimeInMillis(cal.getTimeInMillis() + i);
							StringBuffer sb = new StringBuffer();
							sb.append(i / 60000).append("�� ��(");
							if (cal.getTime().getHours() < 10) {
								sb.append("0").append(cal.getTime().getHours()).append(":");
							} else {
								sb.append(cal.getTime().getHours()).append(":");
							}

							if (cal.getTime().getMinutes() < 10) {
								sb.append("0").append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
							} else {
								sb.append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
							}

							pc.sendPackets(new S_SystemMessage(sb.toString()), true);

						}
					} else if (itemId == 600251) {// �ð��� ���� ť��
						�ð��Ǳ���ť��(pc, useItem);
					} else if (itemId == 600252) {// ���� ť��
						����ť��(pc, useItem);
					} else if (itemId == 600253) {// �巡�� ������ ť��
						������ť��(pc, useItem);
					} else if (itemId == 600254) {// ������ ť��
						������ť��(pc, useItem);
					} else if (itemId == 600255) {// ������ ���� ����
						�����Ǻ�������(pc, useItem);
					} else if (itemId == 6015) {
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed == null
								|| currentDate.getTimeInMillis() > lastUsed.getTime() + (1000 * 60 * 60 * 12)) {// 22�ð�
							if (!get�����ָӴ�_����Ƚ��(pc.getAccountName())) {
								pc.sendPackets(new S_SystemMessage("��� ������ ������ �ָӴ� �̿�Ƚ���� �ʰ��߽��ϴ�. (���� �� 12�� �ʱ�ȭ)"),
										true);
								// pc.sendPackets(new S_ServerMessage(3693,
								// "������ �ָӴ�"), true);
								return;
							}
							if (useItem.getChargeCount() == 1) {
								pc.getInventory().storeItem(6017, 1);
								pc.sendPackets(new S_ServerMessage(403, "�ҿ����� ���� ����"), true);
								// pc.sendPackets(new
								// S_SystemMessage("�ҿ����� ���� ���� 1���� ������ϴ�."),
								// true);
							} else {
								pc.getInventory().storeItem(6016, 1);
								pc.sendPackets(new S_ServerMessage(403, "�ҿ����� ���� ���� ����"), true);
								// pc.sendPackets(new
								// S_SystemMessage("�ҿ����� ���� ���� ���� 1���� ������ϴ�."),
								// true);
							}
							useItem.setChargeCount(useItem.getChargeCount() - 1);
							pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
							if (useItem.getChargeCount() == 0) {
								pc.getInventory().removeItem(useItem);
							}
							useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
						} else {
							/*
							 * SimpleDateFormat dateFormat = new
							 * SimpleDateFormat("HH�ð� mm��"); String time =
							 * dateFormat.format(new
							 * Timestamp(((lastUsed.getTime() + (1000*60*60*10))
							 * - currentDate.getTimeInMillis()) + (60 * 1000 *
							 * 60 * 15))); pc.sendPackets(new
							 * S_SystemMessage(time+" �Ŀ� ��� �Ҽ� �ֽ��ϴ�."), true);
							 * dateFormat = null;
							 */
							long i = (lastUsed.getTime() + (60 * 60000 * 12)) - currentDate.getTimeInMillis();
							Calendar cal = (Calendar) currentDate.clone();
							cal.setTimeInMillis(cal.getTimeInMillis() + i);
							StringBuffer sb = new StringBuffer();
							sb.append(i / 60000).append("�� ��(");
							if (cal.getTime().getHours() < 10) {
								sb.append("0").append(cal.getTime().getHours()).append(":");
							} else {
								sb.append(cal.getTime().getHours()).append(":");
							}

							if (cal.getTime().getMinutes() < 10) {
								sb.append("0").append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
							} else {
								sb.append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
							}

							pc.sendPackets(new S_SystemMessage(sb.toString()), true);

						}
						/** �ð��� �׾Ƹ� **/
					} else if (itemId == 500016) {
						Timestamp lastUsed = useItem.getLastUsed();
						if (lastUsed == null
								|| currentDate.getTimeInMillis() > lastUsed.getTime() + (60 * 60000 * 22)) {
							/*
							 * if(!get�ð��׾Ƹ�_����Ƚ��(pc.getAccountName())){
							 * pc.sendPackets(new S_SystemMessage(
							 * "��� ������ �ð��� �׾Ƹ� �̿�Ƚ���� �ʰ��߽��ϴ�. (���� �� 12�� �ʱ�ȭ)"),
							 * true); //pc.sendPackets(new S_ServerMessage(3693,
							 * "�ð��� �׾Ƹ�"), true); return; }
							 */
							if (pc.getInventory().checkItem(500017, 2)) {
								pc.sendPackets(new S_SystemMessage("�ð��� ������ 2�� �̻� ������ �� �����ϴ�."), true);
								// pc.sendPackets(new S_ServerMessage(3693,
								// "�ð��� �׾Ƹ�"), true);
								return;
							}
							pc.getInventory().storeItem(500017, 1);
							pc.sendPackets(new S_SystemMessage("�ð��� ���� (1) �� �����Ǿ����ϴ�."), true);
							/*
							 * pc.getInventory().storeItem(60164, 1);
							 * pc.sendPackets(new S_SystemMessage(
							 * "������ �ð� ���� (1) �� �����Ǿ����ϴ�."), true);
							 */
							useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
						} else {
							/*
							 * SimpleDateFormat dateFormat = new
							 * SimpleDateFormat("HH�ð� mm��"); String time =
							 * dateFormat.format(new
							 * Timestamp(((lastUsed.getTime() + (60 * 60000 *
							 * 5)) - currentDate.getTimeInMillis()) + (60 * 1000
							 * * 60 * 15))); pc.sendPackets(new
							 * S_SystemMessage(time+" �Ŀ� ��� �Ҽ� �ֽ��ϴ�."), true);
							 * dateFormat = null;
							 */
							long i = (lastUsed.getTime() + (60 * 60000 * 22)) - currentDate.getTimeInMillis();
							Calendar cal = (Calendar) currentDate.clone();
							cal.setTimeInMillis(cal.getTimeInMillis() + i);
							StringBuffer sb = new StringBuffer();
							sb.append(i / 60000).append("�� ��(");
							if (cal.getTime().getHours() < 10) {
								sb.append("0").append(cal.getTime().getHours()).append(":");
							} else {
								sb.append(cal.getTime().getHours()).append(":");
							}
							if (cal.getTime().getMinutes() < 10) {
								sb.append("0").append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
							} else {
								sb.append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
							}
							pc.sendPackets(new S_SystemMessage(sb.toString()), true);
						}
					} else if (itemId == L1ItemId.INCRESE_HP_SCROLL || itemId == L1ItemId.INCRESE_MP_SCROLL
							|| itemId == L1ItemId.INCRESE_ATTACK_SCROLL || itemId == L1ItemId.CHUNSANG_HP_SCROLL
							|| itemId == L1ItemId.CHUNSANG_MP_SCROLL || itemId == L1ItemId.CHUNSANG_ATTACK_SCROLL) {
						useCashScroll(pc, itemId, false);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 40858) { // liquor(��)
						pc.setDrink(true);
						pc.sendPackets(new S_Liquor(pc.getId()), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == L1ItemId.EXP_POTION || itemId == L1ItemId.EXP_POTION2
							|| itemId == L1ItemId.EXP_POTION_fairly || itemId == L1ItemId.EXP_POTION_cash) { // õ���ǹ���
						UseExpPotion(pc, itemId);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == L1ItemId.EXP_POTION3

					) { // �Է�����
						UseExpPotion2(pc, itemId);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == L1ItemId.EXP_POTION4

					) { // �Է����
						UseExpPotion3(pc, itemId);
						pc.getInventory().removeItem(useItem, 1);

						// } else if (itemId == L1ItemId.DRAGON_KEY){//�巡�� Ű
						// pc.sendPackets(new
						// S_PacketBox(S_PacketBox.DRAGONMENU, useItem));

						// // ���� 7�� �߰� ////
					} else

					if (itemId >= 4301041 && itemId <= 4301101) { // ���� 7��
						pc.cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����
						pc.getInventory().consumeItem(4301101, 1);
						int skillid = itemId - 40858;

						/*
						 * if (pc.getMaanDelay() != null) { Calendar cal =
						 * Calendar.getInstance(); if (600 * 1000 >
						 * cal.getTimeInMillis() - pc.getMaanDelay().getTime())
						 * { long time = 600 - ((cal.getTimeInMillis() - pc
						 * .getMaanDelay().getTime()) / 1000);
						 * 
						 * long minute = time / 60; long second = time % 60;
						 * 
						 * pc.sendPackets(new S_SystemMessage("\\fT" +
						 * useItem.getItem().getName() + "�� " + minute + "�� " +
						 * second + "�� �Ŀ� ���� �����մϴ�.")); return; } }
						 */
						if (!CheckEffects(pc)) {
							return;
						}
						if (itemId == L1ItemId.DRAGONMAAN_EARTH1) { // ������ ����
							if (CheckEffects(pc)) {
								skillid = 7671;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ���ݿ� ���� ȸ�ǰ� �ö󰡰�,����
								// ȿ��+3, ��ȭ ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_WATER1) { // ������
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7672;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� �޴� ���� ������� 50%
								// �����ϰ�,���� ȿ��+3 ���� ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_FIRE1) { // ȭ����
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7673;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� �߰� Ÿ��+2, ���� ȿ��+3
								// ���� ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_WIND1) { // ǳ����
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7674;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ġ��Ÿ+1,���� ȿ��+3 ����
								// ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_BIRTH1) { // ź����
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7675;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ȸ�� ���, �޴� ���� �����
								// 50% ����,���� ȿ��+5 ���� ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_SHAPE1) { // ������
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7676;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ȸ�� ���, �޴� ���� �����
								// 50% ����, ���� ġ��Ÿ+1, ���� ȿ��+5, Ȧ�� ����+3�� ȿ����
								// ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_LIFE1) { // ������
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7677;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ȸ�� ���, �޴� ���� �����
								// 50% ����, ���� ġ��Ÿ+2, ���� �߰� Ÿ��+2, ���� ȿ�� +7 �� ������
								// ȿ�� Ȧ�峻��+10, ���ϳ���+10 �� ����ȴ�."));
							}
						}

						L1SkillUse l1skilluse = new L1SkillUse();
						l1skilluse.handleCommands(client.getActiveChar(), skillid, spellsc_objid, spellsc_x, spellsc_y,
								null, 0, L1SkillUse.TYPE_SPELLSC);
						l1skilluse = null;
						Timestamp ts = new Timestamp(System.currentTimeMillis());
						pc.setMaanDelay(ts);
						try {
							pc.save();
						} catch (Exception e) {
						}
					} else

					if (itemId >= 430104 && itemId <= 430110) { // ���� 7��
						pc.cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����
						int skillid = itemId - 40858;

						/*
						 * if (pc.getMaanDelay() != null) { Calendar cal =
						 * Calendar.getInstance(); if (600 * 1000 >
						 * cal.getTimeInMillis() - pc.getMaanDelay().getTime())
						 * { long time = 600 - ((cal.getTimeInMillis() - pc
						 * .getMaanDelay().getTime()) / 1000);
						 * 
						 * long minute = time / 60; long second = time % 60;
						 * 
						 * pc.sendPackets(new S_SystemMessage("\\fT" +
						 * useItem.getItem().getName() + "�� " + minute + "�� " +
						 * second + "�� �Ŀ� ���� �����մϴ�.")); return; } }
						 */
						if (!CheckEffects(pc)) {
							return;
						}
						if (itemId == L1ItemId.DRAGONMAAN_EARTH) { // ������ ����
							if (CheckEffects(pc)) {
								skillid = 7671;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ���ݿ� ���� ȸ�ǰ� �ö󰡰�,����
								// ȿ��+3, ��ȭ ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_WATER) { // ������
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7672;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� �޴� ���� ������� 50%
								// �����ϰ�,���� ȿ��+3 ���� ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_FIRE) { // ȭ����
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7673;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� �߰� Ÿ��+2, ���� ȿ��+3
								// ���� ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_WIND) { // ǳ����
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7674;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ġ��Ÿ+1,���� ȿ��+3 ����
								// ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_BIRTH) { // ź����
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7675;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ȸ�� ���, �޴� ���� �����
								// 50% ����,���� ȿ��+5 ���� ����+3�� ȿ���� ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_SHAPE) { // ������
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7676;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ȸ�� ���, �޴� ���� �����
								// 50% ����, ���� ġ��Ÿ+1, ���� ȿ��+5, Ȧ�� ����+3�� ȿ����
								// ����ȴ�."));
							}
						} else if (itemId == L1ItemId.DRAGONMAAN_LIFE) { // ������
																			// ����
							if (CheckEffects(pc)) {
								skillid = 7677;
								// pc.sendPackets(new
								// S_SystemMessage("���� Ȯ���� ���� ȸ�� ���, �޴� ���� �����
								// 50% ����, ���� ġ��Ÿ+2, ���� �߰� Ÿ��+2, ���� ȿ�� +7 �� ������
								// ȿ�� Ȧ�峻��+10, ���ϳ���+10 �� ����ȴ�."));
							}
						}

						L1SkillUse l1skilluse = new L1SkillUse();
						l1skilluse.handleCommands(client.getActiveChar(), skillid, spellsc_objid, spellsc_x, spellsc_y,
								null, 0, L1SkillUse.TYPE_SPELLSC);
						l1skilluse = null;
						Timestamp ts = new Timestamp(System.currentTimeMillis());
						pc.setMaanDelay(ts);
						try {
							pc.save();
						} catch (Exception e) {
						}
					} else

					if (itemId == L1ItemId.POTION_OF_CURE_POISON || itemId == 40507 || itemId == 60153) { // �ü�
																											// �Ϻ�,
																											// ��Ʈ��
																											// ����
						if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																						// ����
							pc.sendPackets(new S_ServerMessage(698), true); // ���¿�
																			// ����
																			// �ƹ��͵�
																			// ����
																			// ����
																			// �����ϴ�.
						} else {
							pc.cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����
							pc.sendPackets(new S_SkillSound(pc.getId(), 192), true);
							Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 192), true);
							if (itemId == L1ItemId.POTION_OF_CURE_POISON || itemId == 60153) {
								pc.getInventory().removeItem(useItem, 1);
							} else if (itemId == 40507) {
								pc.getInventory().removeItem(useItem, 1);
							}
							pc.curePoison();
						}

					} else if (itemId == 40066 || itemId == 41413) { // ��, ����
						pc.sendPackets(new S_ServerMessage(338, "$1084"), true); // �����%0��
																					// ȸ����
																					// ��
																					// ���Դϴ�.
						pc.setCurrentMp(pc.getCurrentMp() + (7 + _random.nextInt(6))); // 7~12
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 40067 || itemId == 41414) { // ����, ����
						pc.sendPackets(new S_ServerMessage(338, "$1084"), true); // �����%0��
																					// ȸ����
																					// ��
																					// ���Դϴ�.
						pc.setCurrentMp(pc.getCurrentMp() + (15 + _random.nextInt(16))); // 15~30
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 410002) { // ������ ������
						pc.sendPackets(new S_ServerMessage(338, "$1084"), true); // �����%0��
																					// ȸ����
																					// ��
																					// ���Դϴ�.
						pc.setCurrentMp(pc.getCurrentMp() + 44);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 40735) { // ����� ����
						pc.sendPackets(new S_ServerMessage(338, "$1084"), true); // �����%0��
																					// ȸ����
																					// ��
																					// ���Դϴ�.
						pc.setCurrentMp(pc.getCurrentMp() + 60);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 40042) { // ���Ǹ� �Ϻ�
						pc.sendPackets(new S_ServerMessage(338, "$1084"), true); // �����%0��
																					// ȸ����
																					// ��
																					// ���Դϴ�.
						pc.setCurrentMp(pc.getCurrentMp() + 50);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 41404) { // ����ũ�� ����
						pc.sendPackets(new S_ServerMessage(338, "$1084"), true); // �����%0��
																					// ȸ����
																					// ��
																					// ���Դϴ�.
						pc.setCurrentMp(pc.getCurrentMp() + (80 + _random.nextInt(21))); // 80~100
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 41412) { // ���� ����
						pc.sendPackets(new S_ServerMessage(338, "$1084"), true); // �����%0��
																					// ȸ����
																					// ��
																					// ���Դϴ�.
						pc.setCurrentMp(pc.getCurrentMp() + (5 + _random.nextInt(16))); // 5~20
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 141917) {// �̺�Ʈ ���� ���� ���ø����� ��ǥ
						int dollid = l1iteminstance1.getItemId();
						if (dollid == 430000 || dollid == 41248 || dollid == 430002 || dollid == 141918
								|| dollid == 41249 || dollid == 430001 || dollid == 430004 || dollid == 430500
								|| dollid == 141919 || dollid == 141920 || dollid == 141922 || dollid == 141921
								|| dollid == 500203 || dollid == 5000035 || dollid == 500202 || dollid == 41250) {
							���ø�������(pc, useItem, l1iteminstance1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."));
						}
					} else if (itemId == 500231) {
						int i = l1iteminstance1.getItem().getItemId();
						boolean isAppear = true;
						L1DollInstance doll = null;
						for (Object dollObject : pc.getDollList()) {
							doll = (L1DollInstance) dollObject;
							if (doll.getItemObjId() == itemId) {
								isAppear = false;
								break;
							}
						}
						if (isAppear) {
							if (pc.getDollListSize() >= 1) {
								pc.sendPackets(new S_SystemMessage("��� ���� ������ ���� �� �� �����ϴ�."), true);
								return;
							}
							if ((i == 41248) || (i == 41249) || (i == 41250) || (i == 430000) || (i == 430001)
									|| (i == 430002) || (i == 430003) || (i == 430004) || (i == 430500) || (i == 430505)
									|| (i == 430506)) {
								Random _random = new Random();
								int i50 = _random.nextInt(100);
								// if (i50 <= 6){
								// pc.sendPackets(new
								// S_SystemMessage("������ ���ߵǾ� ������ϴ�.��_��"));
								// }
								if ((i50 >= 7) && (i50 <= 16)) {
									pc.getInventory().storeItem(41249, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : ��ť����"), true);
								}
								if ((i50 >= 17) && (i50 <= 34)) {
									pc.getInventory().storeItem(41250, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : �����ΰ�"), true);
								}
								if ((i50 >= 35) && (i50 <= 36)) {
									pc.getInventory().storeItem(430000, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : ����"), true);
								}
								if ((i50 >= 37) && (i50 <= 45)) {
									pc.getInventory().storeItem(430001, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : ���"), true);
								}
								if ((i50 >= 46) && (i50 <= 55)) {
									pc.getInventory().storeItem(430002, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : ũ����Ʈ�þ�"), true);
								}
								if ((i50 >= 56) && (i50 <= 65)) {
									pc.getInventory().storeItem(430003, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : �ô�"), true);
								}
								if ((i50 >= 66) && (i50 <= 86)) {
									pc.getInventory().storeItem(430004, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : ��Ƽ"), true);
								}
								/*
								 * if ((i50 >= 87) && (i50 <= 89)){
								 * pc.getInventory().storeItem(5000034, 1);
								 * pc.sendPackets(new S_SystemMessage(
								 * "�������� : ��ƾ�� ������ϴ�.")); }
								 */
								if ((i50 >= 90) && (i50 <= 92)) {
									pc.getInventory().storeItem(430500, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : ��īƮ����"), true);
								}
								if ((i50 >= 93) && (i50 <= 95)) {
									pc.getInventory().storeItem(430505, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : ��̾�"), true);
								}
								if ((i50 >= 96) && (i50 <= 97)) {
									pc.getInventory().storeItem(41915, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : ����ƺ�"), true);
								}
								if ((i50 >= 98) && (i50 <= 99)) {
									pc.getInventory().storeItem(430506, 1);
									pc.sendPackets(new S_ServerMessage(403, "�������� : ��������"), true);
								}
								pc.getInventory().removeItem(useItem, 1);
								pc.getInventory().removeItem(l1iteminstance1, 1);
								_random = null;
							}
						}
					} else if (itemId == 467009) { // ĳ�������ֹ���
						pc.ĳ������ = true;
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �Է��ϼ���"), true);
					} else if (itemId == 437008) { // ������ ���� ����
						pc.sendPackets(new S_SystemMessage("�̾������� �ų� ���������� �������� ���Ǯ�� 3õ�� ������ �ֽ��ϴ�.."), true);
					} else

					if (itemId >= 500034 && itemId <= 500038) {// ��������
						Timestamp nowday = new Timestamp(System.currentTimeMillis());
						String chat = "";
						if (itemId == 500034) {// �Ⱘ
							pc.setgirantime(1);
							pc.setgiranday(nowday);
							chat = "�������, ������� �̿�ð��� �ʱ�ȭ�Ǿ����ϴ�.";
						} else if (itemId == 500035) {// ���
							pc.set���time(1);
							pc.setpc���time(1);
							pc.set���day(nowday);
							pc.setpc���day(nowday);
							chat = "���ǵ��� �̿�ð��� �ʱ�ȭ�Ǿ����ϴ�.";

						} else if (itemId == 500038) {// �Ⱘ
							pc.setravatime(1);
							pc.setravaday(nowday);
							chat = "��Ÿ�ٵ� ���� �̿�ð��� �ʱ�ȭ�Ǿ����ϴ�.";

						} else if (itemId == 500036) {// ���ž
							pc.setivorytime(1);
							pc.setivoryday(nowday);
							chat = "���ž:�߷����� �̿�ð��� �ʱ�ȭ�Ǿ����ϴ�.";
						} else if (itemId == 500037) {// ��
							pc.set��time(1);
							pc.setpc��time(1);
							pc.set��day(nowday);
							pc.setpc��day(nowday);
							chat = "�������ǹ���(����) �̿�ð��� �ʱ�ȸ�Ǿ����ϴ�.";
						}
						pc.getNetConnection().getAccount().updateDGTime();
						pc.sendPackets(new S_SystemMessage(chat));
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 5000030) { // �͵�������~!
						pc.getInventory().removeItem(useItem, 1);
						// int polyId = pc.getGfxId().getTempCharGfx();
						L1PolyMorph.doPoly(pc, 5641, 600, L1PolyMorph.MORPH_BY_ITEMMAGIC);

					} else if (itemId == 400074) { // 1�� �Ƶ��� ����
						if (pc.getInventory().checkItem(40308, 100000000)) {
							pc.getInventory().consumeItem(40308, 100000000);
							pc.getInventory().storeItem(400075, 1);
							pc.sendPackets(new S_SystemMessage("1�� �Ƶ����� 1�� ��ǥ�� ��ȯ�Ǿ����ϴ�."), true);
						} else {
							pc.sendPackets(new S_SystemMessage("1�� �Ƶ����� �־�� �����մϴ�."), true);
						}
					} else if (itemId == 400075) { // 1�� �Ƶ��� ��ǥ
						if (pc.getInventory().checkItem(40308, 1900000000)) {
							pc.sendPackets(new S_SystemMessage("�κ��� �����ϰ� ��� �Ƶ��� ���� 19�� ���Ϸ� ������ּ���."), true);
						} else {
							pc.getInventory().storeItem(40308, 100000000);
							pc.getInventory().consumeItem(400075, 1);
							pc.sendPackets(new S_SystemMessage("1�� �Ƶ����� ��ȯ�Ǿ����ϴ�."), true);
						}
					} else if (itemId == 42098) { // ���Ǯ ����
						pc.setLawful(32000);
						pc.save();
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SystemMessage("\\fS����ġ�� ���Ǯ�� ��ȭ �˴ϴ�."), true);

					} else if (itemId == 42099) { // ī��ƽ����
						pc.setLawful(-32000);
						pc.save();
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SystemMessage("\\fY����ġ�� ī��ƽ���� ��ȭ �˴ϴ�."), true);

					} else if (itemId == 4443) { // ���Ǯ ����
						pc.setLawful(2000);
						pc.save();
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SystemMessage("\\fS����ġ�� ���Ǯ�� ��ȭ �˴ϴ�."), true);

					} else if (itemId == 4444) { // ī��ƽ����
						pc.setLawful(-2000);
						pc.save();
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SystemMessage("\\fY����ġ�� ī��ƽ���� ��ȭ �˴ϴ�."), true);

					} else if (itemId == 100903 || itemId == 100904) { // ĳ���ͱ�ȯ��ǥ
																		// ����
						pc.sendPackets(new S_UserCommands2(1), true);

					} else if (itemId == 999999) { // ����PC����������
						pc.sendPackets(new S_UserCommands1(1), true);

					} else

					if (itemId == 9999) { // ������ ��ȣ
						for (L1Object obj : L1World.getInstance().getObject()) {
							if (obj instanceof L1BoardInstance) {
								L1NpcInstance board = (L1NpcInstance) obj;
								if (board.getNpcTemplate().get_npcId() == 4500309) {// �Խ���
																					// ��ȣ
									pc.sendPackets(new S_Board(board, 0));
									// break;
								}
							}
						}
						/** ���������Խ��� ������ �ҷ����� **/
					} else

					if (itemId == 42198) { // ų�� �ʱ�ȭ �ֹ���
						pc.setKills(0);
						pc.setDeaths(0);
						pc.sendPackets(new S_SystemMessage(pc.getName() + "���� ų,���� ��ġ�� �ʱ�ȭ�Ǿ����ϴ�."), true);
						pc.getInventory().removeItem(useItem, 1);

						// ////////����ġ���� ---------------------------------
					} else if (itemId == 550009) { // ����ġ����
						if (pc.getLevel() < 51) {
							pc.addExp((ExpTable.getExpByLevel(51) - 1) - pc.getExp()
									+ ((ExpTable.getExpByLevel(51) - 1) / 100));
						} else {
							pc.addExp((ExpTable.getExpByLevel(pc.getLevel() + 1) - 1) - pc.getExp() + 100);
							pc.getInventory().removeItem(useItem, 1);
						}
					}
					// /////////����ġ����
					// ----------------------------------------

					// �ڱ� ���� ���� ���Ϳ� �����ϱ�
					else if (itemId == 42085) {
						if (pc.getInventory().checkItem(42085, 1)) { // �κ��� �ֳ�
																		// üũ
							pc.getInventory().consumeItem(42085, 1); // �Ҹ�
							if (pc.isCrown()) { // ���ֶ��
								if (pc.get_sex() == 0) { // ���ڶ��
									pc.sendPackets(new S_SystemMessage("�ٸ� ���Ϳ� �����ϴٴϿ�! ����� �����Դϴ�!"), true); // �����
																											// �����Դϴ�
								} else { // ���ֶ��
									pc.sendPackets(new S_SystemMessage("�ٸ� ���Ϳ� �����ϴٴϿ�! ����� �����Դϴ�!"), true); // �����
																											// �����Դϴ�
								}
								return;
							}
							if (pc.getClanid() != 0) { // ������ �ִٸ�
								pc.sendPackets(new S_SystemMessage("�̹� ���Ϳ� �����߽��ϴ�."), true); // �̹�
																								// ������
																								// �ֽ��ϴ�
								return;
							}
							Connection con = null;
							Statement pstm2 = null;
							ResultSet rs2 = null;
							try {
								con = L1DatabaseFactory.getInstance().getConnection();
								pstm2 = con.createStatement();
								rs2 = pstm2.executeQuery(
										"SELECT `account_name`, `char_name`, `ClanID`, `Clanname` FROM `characters` WHERE Type = 0"); // �ɸ���
																																		// ���̺���
																																		// ���ָ�
																																		// ���ͼ�
								while (rs2.next()) {
									if (pc.getNetConnection().getAccountName()
											.equalsIgnoreCase(rs2.getString("account_name"))) { // ����
																								// ������
																								// ������
																								// ������
																								// ���ؼ�
																								// �����ϸ�
										if (rs2.getInt("ClanID") != 0) { // ������
																			// ������
																			// �ִٸ�
											L1Clan clan = L1World.getInstance().getClan(rs2.getString("Clanname")); // ������
																													// ��������
																													// ����
											L1PcInstance clanMember[] = clan.getOnlineClanMember();
											for (int cnt = 0; cnt < clanMember.length; cnt++) { // ������
																								// ���Ϳ�����
																								// �޼���
																								// �Ѹ���
												clanMember[cnt].sendPackets(new S_ServerMessage(94, pc.getName()),
														true); // \f1%0�� ������
																// �Ͽ����μ�
																// �޾Ƶ鿩�����ϴ�.
											}
											pc.setClanid(rs2.getInt("ClanID"));
											pc.setClanRank(2);
											pc.setClanname(rs2.getString("Clanname"));
											pc.save(); // DB�� ĳ���� ������ �����Ѵ�
											clan.addClanMember(pc.getName(), pc.getClanRank(), pc.getLevel(),
													pc.getType(), pc.getMemo(), pc.getOnlineStatus(), pc);
											pc.sendPackets(new S_ServerMessage(95, rs2.getString("Clanname")), true); // \f1%0
																														// ���Ϳ�
																														// �����߽��ϴ�.
																														// //
																														// �޼���
																														// ������
											pc.getInventory().removeItem(useItem, 1);
											clanMember = null;
											break;
										}
									}
								}
							} catch (Exception e) {

							} finally {
								rs2.first(); // ������ ó������ �ǵ�����
								SQLUtil.close(rs2, pstm2, con);
							}
							if (pc.getClanid() == 0) { // ������ �ִٸ�
								pc.sendPackets(new S_SystemMessage("\\fY�������� ���ְ� ���ų� ������ â������ �ʾҽ��ϴ�."), true); // �޼���
																												// ������
							}
						}
						/**
						 * ��� 8�Ĵ�,8���� 7�� - +8 �ĸ��Ǵ��(412001).+8 �����Ϸ��ܴܰ�(9),+7
						 * �����������(20011),+7 ��������(120056),+7 Ƽ����(20085),+7
						 * ���������Ǻ���(20200),+7 �ϻ챺�����尩(20178),+7 �߶�ī���Ǹ�����(20119)
						 * +0 ������簡��(420003) �뿹�ǱͰ���(21027),������
						 * ����Ǹ����(20422),Ÿ��ź�Ǻ�Ʈ(20320)
						 */
						/*
						 * } else if (itemId == 500094){ //����������
						 * pc.getInventory().consumeItem(500094, 1); // �����Ǵ�
						 * �����۰� ���� ��Ȯ�λ�����(pc, 500007, 1, 0); ��Ȯ�λ�����(pc, 5000500,
						 * 8, 0); //��Ƽ����ȭ�ܼ�
						 */} else if (itemId == 600220) { // ����������
						pc.getInventory().consumeItem(600220, 1); // �����Ǵ� �����۰�
																	// ����
						��Ȯ�λ�����(pc, 500007, 1, 0);
						��Ȯ�λ�����(pc, 5000500, 8, 0); // ��Ƽ����ȭ�ܼ�
					} else if (itemId == 600219) { // �������
						pc.getInventory().consumeItem(600219, 1); // �����Ǵ� �����۰�
																	// ����
						��Ȯ�λ�����(pc, 500009, 1, 0);
						��Ȯ�λ�����(pc, 5000500, 8, 0); // ��Ƽ����ȭ�ܼ�
					} else if (itemId == 500095) { // Ǫ��������
						pc.getInventory().consumeItem(500095, 1); // �����Ǵ� �����۰�
																	// ����
						��Ȯ�λ�����(pc, 500008, 1, 0);
						��Ȯ�λ�����(pc, 5000500, 8, 0); // ��Ƽ����ȭ�ܼ�
					} else if (itemId == 500100) { // ��������
						pc.getInventory().consumeItem(500100, 1); // �����Ǵ� �����۰�
																	// ����
						��Ȯ�λ�����(pc, 500010, 1, 0);
						��Ȯ�λ�����(pc, 5000500, 8, 0); // ��Ƽ����ȭ�ܼ�

						/*
						 * } else if (itemId == 500096){ //����������
						 * pc.getInventory().consumeItem(500096, 1); // �����Ǵ�
						 * �����۰� ���� ��Ȯ�λ�����(pc, 500009, 1, 0); ��Ȯ�λ�����(pc, 5000500,
						 * 8, 0); //��Ƽ����ȭ�ܼ�
						 *//**
							 * 525109 �ű��� ȸ�� ���� 525110 �ű��� ���� ���� 525111 �ű��� ü��
							 * ���� 525112 �ű��� ���� ���� 525113 �ű��� �������� ����
							 * 
							 * 625109 �ű��� Ư�� ȸ�� ���� 625110 �ű��� Ư�� ���� ���� 625111
							 * �ű��� Ư�� ü�� ���� 625112 �ű��� Ư�� ���� ���� 625113 �ű��� Ư��
							 * �������� ����
							 **/

					} else if (itemId == 600094) { // ��������
						pc.getInventory().consumeItem(600094, 1); // �����Ǵ� �����۰�
																	// ����
						int ran = _random.nextInt(100) + 1;
						if (ran <= 5) {
							createNewItem2(pc, 625113, 1, 0); // ��Ƽ����ȭ�ܼ�
						} else {
							createNewItem2(pc, 525113, 1, 0); // ��Ƽ����ȭ�ܼ�
						}
						createNewItem2(pc, 530040, 8, 0); // ��Ƽ����ȭ�ܼ�
					} else if (itemId == 600095) { // ����
						pc.getInventory().consumeItem(600095, 1); // �����Ǵ� �����۰�
																	// ����
						int ran = _random.nextInt(100) + 1;
						if (ran <= 5) {
							createNewItem2(pc, 625112, 1, 0); // ��Ƽ����ȭ�ܼ�
						} else {
							createNewItem2(pc, 525112, 1, 0); // ��Ƽ����ȭ�ܼ�
						}
						createNewItem2(pc, 530040, 8, 0); // ��Ƽ����ȭ�ܼ�
					} else if (itemId == 600096) { // ü��
						pc.getInventory().consumeItem(600096, 1); // �����Ǵ� �����۰�
																	// ����
						int ran = _random.nextInt(100) + 1;
						if (ran <= 5) {
							createNewItem2(pc, 625111, 1, 0); // ��Ƽ����ȭ�ܼ�
						} else {
							createNewItem2(pc, 525111, 1, 0); // ��Ƽ����ȭ�ܼ�
						}
						createNewItem2(pc, 530040, 8, 0); // ��Ƽ����ȭ�ܼ�
					} else if (itemId == 600097) { // ȸ��
						pc.getInventory().consumeItem(600097, 1); // �����Ǵ� �����۰�
																	// ����
						int ran = _random.nextInt(100) + 1;
						if (ran <= 5) {
							createNewItem2(pc, 625109, 1, 0); // ��Ƽ����ȭ�ܼ�
						} else {
							createNewItem2(pc, 525109, 1, 0); // ��Ƽ����ȭ�ܼ�
						}
						createNewItem2(pc, 530040, 8, 0); // ��Ƽ����ȭ�ܼ�
					} else if (itemId == 600098) { // ����
						pc.getInventory().consumeItem(600098, 1); // �����Ǵ� �����۰�
																	// ����
						int ran = _random.nextInt(100) + 1;
						if (ran <= 5) {
							createNewItem2(pc, 625110, 1, 0); // ��Ƽ����ȭ�ܼ�
						} else {
							createNewItem2(pc, 525110, 1, 0); // ��Ƽ����ȭ�ܼ�
						}
						createNewItem2(pc, 530040, 8, 0); // ��Ƽ����ȭ�ܼ�

					} else if (itemId == 500097) { // ��������Ű��
						��Ȯ�λ�����(pc, 500094, 4, 0);
						pc.getInventory().consumeItem(500097, 1); // �����Ǵ� �����۰�
																	// ����

					} else if (itemId == 500098) { // Ǫ������Ű��
						��Ȯ�λ�����(pc, 500095, 4, 0);
						pc.getInventory().consumeItem(500098, 1); // �����Ǵ� �����۰�
																	// ����

					} else if (itemId == 500099) { // ��������Ű��
						��Ȯ�λ�����(pc, 500096, 4, 0);
						pc.getInventory().consumeItem(500099, 1); // �����Ǵ� �����۰�
																	// ����
					} else if (itemId == 500101) { // ��������Ű��
						��Ȯ�λ�����(pc, 500100, 4, 0);
						pc.getInventory().consumeItem(500101, 1); // �����Ǵ� �����۰�
																	// ����
						/*
						 * ���� 1�� 9��ö���� 2�� 7����������� 7�����Ǹ��� 7�����Ǻ��� 7�Ŀ��۷κ� ���� 7Ƽ����
						 * �����ǱͰ��� �Ϸ��Ǹ���� ������Ǻ�Ʈ �긶�ǹ��� 2��
						 */
					} else if (itemId == 555558) { // ����1��
													// ����
						createNewItem2(pc, 7229, 1, 9, 3, 0); // ��ö����
						createNewItem2(pc, 7229, 1, 9, 3, 0); // ��ö����

						createNewItem2(pc, 7247, 1, 7); // �����
						createNewItem2(pc, 20058, 1, 7); // ��������
						createNewItem2(pc, 20085, 1, 7); // Ƽ����
						createNewItem2(pc, 20201, 1, 7); // ����
						createNewItem2(pc, 20187, 1, 7); // �ı�
						createNewItem2(pc, 21169, 1, 0); // ����

						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20264, 1, 0); // �Ϸ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶
						// ��� 1�� ��Ű�� 9�Ӵ븦 9�Ĵ�� ���� ���縦 �������� ���� ��ö������ �����Ǻ����� ����

						// + 9 ������ ��հ� + 8 ���� ��� ���� + 8 Ƽ���� + 8 ���� ����
						// + 8 ������ ���� , + 8 �Ŀ��۷κ� +2 �긶�� �Ǳ� ����
						// +1 ��� ������ ���� ������ �Ͱ��� �Ϸ��� ����� ������� ��Ʈ �긶�� ���� 2¦

						// + 8 ������ ���� 2�� + 8 ������� ���� + 8 Ƽ���� + 8 �����Ǹ���
						// + 8 ������ ���� , + 7 ���尩 +2 �긶�� �Ǳ� ����
						// ������ �Ͱ��� �Ϸ��� ����� ������� ��Ʈ �긶�� ���� 2¦

					} else if (itemId == 666668) { // ����1��
						pc.getInventory().consumeItem(666668, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 7225, 1, 8, 3, 0); // ��������
						createNewItem2(pc, 7225, 1, 8, 3, 0); // ��������

						createNewItem2(pc, 7247, 1, 8); // �����
						createNewItem2(pc, 20058, 1, 8); // ��������
						createNewItem2(pc, 20085, 1, 8); // Ƽ����
						createNewItem2(pc, 20201, 1, 8); // ����
						createNewItem2(pc, 30219, 1, 7); // ����
						createNewItem2(pc, 21169, 1, 2); // ����

						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20264, 1, 0); // �Ϸ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

					} else if (itemId == 555551) { // ���1��
						���1������(pc);
					} else if (itemId == 666661) { // ���2��
						pc.getInventory().consumeItem(666661, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 62, 1, 9, 3, 0); // ����
						createNewItem2(pc, 120011, 1, 8); // ����
						createNewItem2(pc, 120056, 1, 8); // ����
						createNewItem2(pc, 20085, 1, 8); // Ƽ����
						createNewItem2(pc, 20201, 1, 8); // ����
						createNewItem2(pc, 20187, 1, 8); // �ı�
						createNewItem2(pc, 21169, 1, 2); // ����
						createNewItem2(pc, 420003, 1, 1); // ������

						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20264, 1, 0); // �Ϸ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

					} else if (itemId == 555552) { // ����1��
						pc.getInventory().consumeItem(555552, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 191, 1, 9, 9, 0); // ��õ

						createNewItem2(pc, 120011, 1, 7); // ����
						createNewItem2(pc, 120056, 1, 7); // ����
						createNewItem2(pc, 20085, 1, 7); // Ƽ
						createNewItem2(pc, 20208, 1, 7); // �ź�
						createNewItem2(pc, 21171, 1, 0); // �긶����
						createNewItem2(pc, 20191, 1, 7); // ��
						createNewItem2(pc, 420000, 1, 0); // ����
						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20256, 1, 0); // �θ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

						// + 9 �������� Ȱ + 8 ���� ��� ���� + 8 Ƽ���� + 8 ���� ����
						// + 8 �Ű��� ���� , + 8 Ȱ�� +2 �긶�� ���� ����
						// +1 ��� ����� ���� ������ �Ͱ��� ��ø�� ����� ������� ��Ʈ �긶�� ���� 2¦

					} else if (itemId == 666662) { // ����2��
						pc.getInventory().consumeItem(666662, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 190, 1, 9, 9, 0); // ��Ȱ

						createNewItem2(pc, 120011, 1, 8); // ����
						createNewItem2(pc, 120056, 1, 8); // ����
						createNewItem2(pc, 20085, 1, 8); // Ƽ
						createNewItem2(pc, 20208, 1, 8); // �ź�
						createNewItem2(pc, 21171, 1, 2); // �긶����
						createNewItem2(pc, 20191, 1, 8); // ��
						createNewItem2(pc, 420000, 1, 1); // ����
						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20256, 1, 0); // �θ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

					} else if (itemId == 555553) { // ����1��
						pc.getInventory().consumeItem(555553, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 119, 1, 0, 0, 0); // ����������
						createNewItem2(pc, 121, 1, 7, 3, 0); // ����
						createNewItem2(pc, 120011, 1, 7); // ����
						createNewItem2(pc, 120056, 1, 7); // ����
						createNewItem2(pc, 20085, 1, 7); // Ƽ����
						createNewItem2(pc, 21172, 1, 0); // ���
						createNewItem2(pc, 21097, 1, 3); // �������ǰ���
						createNewItem2(pc, 20208, 1, 7); // �ź�
						createNewItem2(pc, 20187, 1, 7); // �ı�
						createNewItem2(pc, 20266, 1, 0); // ����
						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

						/*
						 * + 9 ���������� ������ �������������� + 8 ���� ��� ���� + 8 Ƽ���� + 8 ���� ����
						 * + 8 �Ű��� ���� , + 7 ������ �尩 +2 �긶�� �κ� + 3 �������� ���� ������ �Ͱ���
						 * ������ ����� ������� ��Ʈ �긶�� ���� 2¦
						 */

					} else if (itemId == 666663) { // ����2��
						pc.getInventory().consumeItem(666663, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 119, 1, 0, 0, 0); // ����������
						createNewItem2(pc, 121, 1, 9, 3, 0); // ����
						createNewItem2(pc, 120011, 1, 8); // ����
						createNewItem2(pc, 120056, 1, 8); // ����
						createNewItem2(pc, 20085, 1, 8); // Ƽ����
						createNewItem2(pc, 21172, 1, 2); // ���
						createNewItem2(pc, 21097, 1, 3); // �������ǰ���

						createNewItem2(pc, 20208, 1, 8); // �ź�
						createNewItem2(pc, 7245, 1, 7); // �������尩

						createNewItem2(pc, 20266, 1, 0); // ����
						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

					} else if (itemId == 555554) { // �ٿ�1��
						pc.getInventory().consumeItem(555554, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 260, 1, 9, 3, 0); // ����

						createNewItem2(pc, 120011, 1, 7); // ����
						createNewItem2(pc, 120056, 1, 7); // ����
						createNewItem2(pc, 20085, 1, 7); // Ƽ
						createNewItem2(pc, 20201, 1, 7); // ����
						createNewItem2(pc, 21171, 1, 0); // �갡��
						createNewItem2(pc, 20187, 1, 7); // �ı�
						createNewItem2(pc, 420003, 1, 0); // ����

						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20264, 1, 0); // �Ϸ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

						// + 8 ��յ� + 8 ���� ��� ���� + 8 Ƽ���� + 8 ���� ����
						// + 8 ������ ���� , + 7 ���尩 +2 �긶�� ���� ����
						// +1 ��� ������ ���� ������ �Ͱ��� �Ϸ��� ����� ������� ��Ʈ �긶�� ���� 2¦
					} else if (itemId == 666664) { // �ٿ�2��
						pc.getInventory().consumeItem(666664, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 84, 1, 8, 3, 0); // �յ�

						createNewItem2(pc, 120011, 1, 8); // ����
						createNewItem2(pc, 120056, 1, 8); // ����
						createNewItem2(pc, 20085, 1, 8); // Ƽ
						createNewItem2(pc, 20201, 1, 8); // ����
						createNewItem2(pc, 21171, 1, 2); // �갡��
						createNewItem2(pc, 30219, 1, 7); // ���尩
						createNewItem2(pc, 420003, 1, 1); // ����

						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20264, 1, 0); // �Ϸ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

					} else if (itemId == 555555) { // ȯ��1��
						pc.getInventory().consumeItem(555555, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 266, 1, 9, 3, 0); // 9��Ű

						createNewItem2(pc, 120011, 1, 7); // ����
						createNewItem2(pc, 120056, 1, 7); // ����
						createNewItem2(pc, 20085, 1, 7); // Ƽ����
						createNewItem2(pc, 420006, 1, 7); // ȯ��
						createNewItem2(pc, 20208, 1, 7); // �ź�
						createNewItem2(pc, 20187, 1, 7); // �ı�
						createNewItem2(pc, 21172, 1, 0); // ���
						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20266, 1, 0); // ����
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

						// + 10 ������ Ű��ũ + 8 ���� ��� ���� + 8 Ƽ���� + 8 ���� ����
						// + 8 �Ű��� ���� , + 7 ������ �尩 +2 �긶�� �κ�
						// + 8 ȯ������ ������ ������ �Ͱ��� ������ ����� ������� ��Ʈ �긶�� ���� 2¦

					} else if (itemId == 666665) { // ȯ��2��
						pc.getInventory().consumeItem(666665, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 266, 1, 10, 3, 0); // 9��Ű

						createNewItem2(pc, 120011, 1, 8); // ����
						createNewItem2(pc, 120056, 1, 8); // ����
						createNewItem2(pc, 20085, 1, 8); // Ƽ����
						createNewItem2(pc, 420006, 1, 8); // ȯ��
						createNewItem2(pc, 20208, 1, 8); // �ź�
						createNewItem2(pc, 7245, 1, 7); // �ı�
						createNewItem2(pc, 21172, 1, 2); // ���
						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20266, 1, 0); // ����
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

					} else if (itemId == 555556) { // ����1��
						pc.getInventory().consumeItem(555556, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 275, 1, 9, 3, 0); // ȯü

						createNewItem2(pc, 120011, 1, 7); // ����
						createNewItem2(pc, 120056, 1, 7); // ����
						createNewItem2(pc, 20085, 1, 7); // Ƽ����
						createNewItem2(pc, 20201, 1, 7); // ����
						createNewItem2(pc, 20187, 1, 7); // �ı�
						createNewItem2(pc, 21170, 1, 0); // ����
						createNewItem2(pc, 420003, 1, 0); // ������簡��

						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20264, 1, 0); // �Ϸ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

						// + 9 ���� ��Ŀ + 8 ���� ��� ���� + 8 Ƽ���� + 8 ���� ����
						// + 8 ������ ���� , + 7 ���尩 +2 �긶�� ��ð���
						// +1 ��� ������ ���� ������ �Ͱ��� �Ϸ��� ����� ������� ��Ʈ �긶�� ���� 2¦
						//

					} else if (itemId == 666666) { // ����2��
						pc.getInventory().consumeItem(666666, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 262, 1, 9, 3, 0); // ���

						createNewItem2(pc, 120011, 1, 8); // ����
						createNewItem2(pc, 120056, 1, 8); // ����
						createNewItem2(pc, 20085, 1, 8); // Ƽ����
						createNewItem2(pc, 20201, 1, 8); // ����
						createNewItem2(pc, 30219, 1, 7); // ����
						createNewItem2(pc, 21170, 1, 2); // ����
						createNewItem2(pc, 420003, 1, 1); // ������簡��

						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20264, 1, 0); // �Ϸ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

					} else if (itemId == 555557) { // ����1��
						pc.getInventory().consumeItem(555557, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 412000, 1, 9, 3, 0); // ����

						createNewItem2(pc, 120011, 1, 7); // ����
						createNewItem2(pc, 120056, 1, 7); // ����
						createNewItem2(pc, 20085, 1, 7); // Ƽ����
						createNewItem2(pc, 20194, 1, 7); // ����
						createNewItem2(pc, 20187, 1, 7); // �ı�
						createNewItem2(pc, 20110, 1, 7); // ����
						createNewItem2(pc, 20235, 1, 5); // ����

						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20264, 1, 0); // �Ϸ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

						// + 10 ���Ű� + 8���� ��� ���� + 8 Ƽ���� + 8���� ����
						// + 8 ������ ���� , + 7 ���尩 + 2 �긶�� ��ð���
						// + 7 ������ ���� ������ �Ͱ��� �Ϸ��� ����� ������� ��Ʈ �긶�� ���� 2¦

					} else if (itemId == 666667) { // ����2��
						pc.getInventory().consumeItem(666667, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 412000, 1, 10, 3, 0); // ����

						createNewItem2(pc, 120011, 1, 8); // ����
						createNewItem2(pc, 120056, 1, 8); // ����
						createNewItem2(pc, 20085, 1, 8); // Ƽ����
						createNewItem2(pc, 20201, 1, 8); // ����
						createNewItem2(pc, 30219, 1, 7); // ����
						createNewItem2(pc, 21170, 1, 2); // ����
						createNewItem2(pc, 20235, 1, 7); // ����

						createNewItem2(pc, 21022, 1, 0); // ������
						createNewItem2(pc, 20264, 1, 0); // �Ϸ�
						createNewItem2(pc, 20317, 1, 0); // ����
						createNewItem2(pc, 20280, 1, 0); // �긶
						createNewItem2(pc, 20280, 1, 0); // �긶

					} else if (itemId == 447011) { // �ʺ� ��������
						// ���, ���� �Ѽհ�
						// ���� ���ž�� ����, ���ž�� ȭ�� 1000��
						// ���� ���ž ������
						// �ٿ� ���ž�� ũ�ο�
						// ����, ȯ���� ���ž�� ����
						// ������ ��� ���� ��ȯ ����
						// ���ž ����
						// ���۸��� �̵�
						if (pc.getInventory().checkItem(447011, 1)) { // üũ �Ǵ�
																		// �����۰�
																		// ����
							pc.getInventory().consumeItem(447011, 1); // �����Ǵ�
																		// �����۰�
																		// ����
							createNewItem2(pc, 40096, 20, 0); // ���ž �����ֹ���
							createNewItem2(pc, 40098, 20, 0); // ���ž Ȯ���ֹ���
							createNewItem2(pc, 40099, 100, 0); // ���ž �����̵��ֹ���
							createNewItem2(pc, 40081, 30, 0); // ���������ȯ�ֹ���
							createNewItem2(pc, 99115, 30, 0); // Ŭ���� �ֹ���
							// createNewItem2(pc, 20028, 1, 4); // ���ž����
							// createNewItem2(pc, 20082, 1, 4); // ���žƼ����
							// createNewItem2(pc, 20126, 1, 4); // ���ž����
							// createNewItem2(pc, 20173, 1, 4); // ���ž�尩
							// createNewItem2(pc, 20206, 1, 4); // ���ž����
							// createNewItem2(pc, 110113, 1, 0); //�ʺ��ڹ���
							// createNewItem2(pc, 110113, 1, 0); //�ʺ��ڹ���
							// createNewItem2(pc, 110115, 1, 0); //�ʺ��ڱͰ���
							// createNewItem2(pc, 110112, 1, 0); //�ʺ��ڸ����
							// createNewItem2(pc, 110114, 1, 0); //�ʺ��ں�Ʈ
							createNewItem2(pc, 600224, 2, 0); // õ���ǹ���
							createNewItem2(pc, 40029, 100, 0); // ���ž ü��ȸ����
							createNewItem2(pc, 40030, 5, 0); // ���ž �ӵ���󹰾�
							createNewItem2(pc, 40017, 5, 0); // �ص���
							// createNewItem2(pc, 41246, 1000, 0); //����ü
							// createNewItem2(pc, 41159, 50, 0); //�ź��� ���� ����

							// createNewItem2(pc, 762676, 1, 0); // ���۸����̵���

							// createNewItem2(pc, 430504, 1, 0); //������ ����
							// createNewItem2(pc, 430503, 1, 0); //������ ����
							createNewItem2(pc, 40308, 30000, 0); // �Ƶ���

							if (pc.isKnight()) {
								// createNewItem2(pc, 35, 1, 7); //���ž�� �Ѽհ�
								// createNewItem2(pc, 48, 1, 7); //���ž�� ��հ�
								createNewItem2(pc, 450002, 1, 0); // ���ž�� â
								createNewItem2(pc, 450000, 1, 0); // ���ž�� �ܰ�
								createNewItem2(pc, 40014, 10, 0); // ����ǹ���
								pc.sendPackets(new S_SystemMessage("������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isDragonknight()) {
								// createNewItem2(pc, 48, 1, 7); //���ž�� ��հ�
								createNewItem2(pc, 450001, 1, 0); // ���ž�� ����
								createNewItem2(pc, 450004, 1, 0); // ���ž�� �Ѽհ�
								createNewItem2(pc, 430006, 10, 0); // ���׵�󿭸�
								pc.sendPackets(new S_SystemMessage("�������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isCrown()) {
								// createNewItem2(pc, 35, 1, 7); //���ž�� �Ѽհ�
								// createNewItem2(pc, 48, 1, 7); //���ž�� ��հ�
								createNewItem2(pc, 450001, 1, 0); // ���ž�� �ܰ�
								createNewItem2(pc, 40031, 10, 0); // �Ǹ��� ��
								pc.sendPackets(new S_SystemMessage("�������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isWizard()) {
								createNewItem2(pc, 450003, 1, 0); // ���ž�� ������
								// createNewItem2(pc, 7, 1, 7); //���ž�� �ܰ�
								pc.sendPackets(new S_SystemMessage("���������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isIllusionist()) {
								// createNewItem2(pc, 147, 1, 7); //���ž�� ����
								createNewItem2(pc, 450006, 1, 0); // ���ž�� ������
								createNewItem2(pc, 430006, 10, 0); // ���׵�󿭸�
								pc.sendPackets(new S_SystemMessage("ȯ�������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isElf()) {

								// createNewItem2(pc, 35, 1, 7); //���ž�� �Ѽհ�
								// createNewItem2(pc, 174, 1, 7); //���ž�� ����
								// createNewItem2(pc, 175, 1, 7); //���ž�� Ȱ
								createNewItem2(pc, 450007, 1, 0); // ���ž�� �ܰ�
								createNewItem2(pc, 40744, 1000, 0); // ��ȭ��
								createNewItem2(pc, 40068, 10, 0); // �������
								createNewItem2(pc, 40114, 10, 0); // �佣 ��ȯ�ֹ���
								pc.sendPackets(new S_SystemMessage("�������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isDarkelf()) {
								// //createNewItem2(pc, 7, 1, 7); //���ž�� �ܰ�
								// createNewItem2(pc, 73, 1, 7); //���ž�� �̵���
								createNewItem2(pc, 450005, 1, 0); // ���ž�� ũ�ο�
								pc.sendPackets(new S_SystemMessage("��ũ�������� �������� ���� �Ǿ����ϴ�."), true);

							}
						}
					} else if (itemId == 747012) { // �ʺ� ��������
						�ʺ�����(pc);
					} else if (itemId == 747011) { // �ʺ� ��������
						if (pc.getInventory().checkItem(747011, 1)) { // üũ �Ǵ�
																		// �����۰�
																		// ����
							pc.getInventory().consumeItem(747011, 1); // �����Ǵ�
																		// �����۰�
																		// ����

							if (pc.isKnight()) {
								createNewItem2(pc, 762662, 1, 8); // ���ž�� �Ѽհ�
								createNewItem2(pc, 76269, 1, 8); // ���ž�� ��հ�
								createNewItem2(pc, 720011, 1, 6); // ����
								createNewItem2(pc, 720187, 1, 6); // �ı�
								createNewItem2(pc, 720085, 1, 6); // Ƽ
								createNewItem2(pc, 721060, 1, 6); // �ҷ���ȣ�ڰ���
								createNewItem2(pc, 720056, 1, 6); // ����
								createNewItem2(pc, 720194, 1, 6); // ����

								createNewItem2(pc, 40014, 10, 0); // ����ǹ���
								pc.sendPackets(new S_SystemMessage("������� �������� ���� �Ǿ����ϴ�."), true);
							}

							if (pc.isDragonknight()) {
								createNewItem2(pc, 762649, 1, 8); // ���ž�� �Ѽհ�
								createNewItem2(pc, 720011, 1, 6); // ����
								createNewItem2(pc, 720187, 1, 6); // �ı�
								createNewItem2(pc, 720236, 1, 6); // ���
								createNewItem2(pc, 720085, 1, 6); // Ƽ
								createNewItem2(pc, 721060, 1, 6); // �ҷ���ȣ�ڰ���
								createNewItem2(pc, 720056, 1, 6); // ����
								createNewItem2(pc, 720194, 1, 6); // ����
								pc.sendPackets(new S_SystemMessage("�������� �������� ���� �Ǿ����ϴ�."), true);
							}

							if (pc.isCrown()) {
								createNewItem2(pc, 762649, 1, 8); // ���ž�� �Ѽհ�
								createNewItem2(pc, 76269, 1, 8); // ���ž�� ��հ�
								createNewItem2(pc, 720011, 1, 6); // ����
								createNewItem2(pc, 720187, 1, 6); // �ı�
								createNewItem2(pc, 720229, 1, 6); // �ݻ�
								createNewItem2(pc, 720085, 1, 6); // Ƽ
								createNewItem2(pc, 721060, 1, 6); // �ҷ���ȣ�ڰ���
								createNewItem2(pc, 720056, 1, 6); // ����
								createNewItem2(pc, 720194, 1, 6); // ����

								createNewItem2(pc, 40031, 10, 0); // �Ǹ��� ��
								pc.sendPackets(new S_SystemMessage("�������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isWizard()) {
								createNewItem2(pc, 7626123, 1, 0); // ���ž�� ��հ�
								createNewItem2(pc, 720011, 1, 6); // ����
								createNewItem2(pc, 720187, 1, 6); // �ı�
								createNewItem2(pc, 720085, 1, 6); // Ƽ
								createNewItem2(pc, 720093, 1, 0); // ���
								createNewItem2(pc, 720056, 1, 6); // ����
								createNewItem2(pc, 720194, 1, 6); // ����
								pc.sendPackets(new S_SystemMessage("���������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isIllusionist()) {
								createNewItem2(pc, 7410004, 1, 8); // ���ž�� ��հ�
								createNewItem2(pc, 720011, 1, 6); // ����
								createNewItem2(pc, 720187, 1, 6); // �ı�
								createNewItem2(pc, 720085, 1, 6); // Ƽ
								createNewItem2(pc, 720093, 1, 0); // ���
								createNewItem2(pc, 720056, 1, 6); // ����
								createNewItem2(pc, 720194, 1, 6); // ����

								createNewItem2(pc, 430006, 10, 0); // ���׵�󿭸�
								pc.sendPackets(new S_SystemMessage("ȯ�������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isElf()) {
								createNewItem2(pc, 7626188, 1, 8); // ���ž�� �Ѽհ�
								createNewItem2(pc, 720033, 1, 6); // ����
								createNewItem2(pc, 720191, 1, 6); // ��
								createNewItem2(pc, 720085, 1, 6); // Ƽ
								createNewItem2(pc, 721060, 1, 6); // �ҷ���ȣ�ڰ���
								createNewItem2(pc, 720056, 1, 6); // ����
								createNewItem2(pc, 720194, 1, 6); // ����

								createNewItem2(pc, 40744, 1000, 0); // ��ȭ��
								createNewItem2(pc, 40068, 10, 0); // �������
								createNewItem2(pc, 40114, 10, 0); // �佣 ��ȯ�ֹ���
								pc.sendPackets(new S_SystemMessage("�������� �������� ���� �Ǿ����ϴ�."), true);
							}
							if (pc.isDarkelf()) {
								createNewItem2(pc, 762681, 1, 8); // ���ž�� �Ѽհ�
								createNewItem2(pc, 720011, 1, 6); // ����
								createNewItem2(pc, 720187, 1, 6); // �ı�
								createNewItem2(pc, 720085, 1, 6); // Ƽ
								createNewItem2(pc, 721060, 1, 6); // �ҷ���ȣ�ڰ���
								createNewItem2(pc, 720056, 1, 6); // ����
								createNewItem2(pc, 720194, 1, 6); // ����
								pc.sendPackets(new S_SystemMessage("��ũ�������� �������� ���� �Ǿ����ϴ�."), true);

							}
							// createNewItem2(pc, 110113, 1, 0); //�ʺ��ڹ���
							// createNewItem2(pc, 110113, 1, 0); //�ʺ��ڹ���
							// createNewItem2(pc, 110115, 1, 0); //�ʺ��ڱͰ���
							// createNewItem2(pc, 110112, 1, 0); //�ʺ��ڸ����
							// createNewItem2(pc, 110114, 1, 0); //�ʺ��ں�Ʈ

							createNewItem2(pc, 40096, 20, 0); // ���ž �����ֹ���
							createNewItem2(pc, 40098, 20, 0); // ���ž Ȯ���ֹ���
							createNewItem2(pc, 40099, 100, 0); // ���ž �����̵��ֹ���
							createNewItem2(pc, 40081, 30, 0); // ���������ȯ�ֹ���
							createNewItem2(pc, 40095, 30, 0); // ���ž ��ȯ�ֹ���

							createNewItem2(pc, 762676, 1, 0); // ���۸����̵���

							createNewItem2(pc, 435006, 3, 0); // õ���ǹ���
							createNewItem2(pc, 40029, 100, 0); // ���ž ü��ȸ����
							createNewItem2(pc, 40030, 5, 0); // ���ž �ӵ���󹰾�
							createNewItem2(pc, 40017, 5, 0); // �ص���
							createNewItem2(pc, 41246, 1000, 0); // ����ü
							createNewItem2(pc, 41159, 50, 0); // �ź��� ���� ����
							// createNewItem2(pc, 430504, 1, 0); //������ ����
							// createNewItem2(pc, 430503, 1, 0); //������ ����
							createNewItem2(pc, 40308, 50000, 0); // �Ƶ���

						}
						/** ������ ���� (����) **/
					} else if (itemId == 7001) {
						if (pc.getInventory().checkItem(7001, 1)) {
							pc.getInventory().consumeItem(7001, 1);
							if (pc.isKnight() || pc.isDragonknight()) {
								createNewItem2(pc, 61, 1, 4); // ����Ȳ�� �����
							}
							if (pc.isCrown()) {
								createNewItem2(pc, 114, 1, 8); // �������� ��
							}
							if (pc.isWizard()) {
								createNewItem2(pc, 134, 1, 4); // ���� ����ü ������
							}
							if (pc.isElf()) {
								createNewItem2(pc, 205, 1, 8); // ��õ�� Ȱ
							}
							if (pc.isDarkelf()) {
								createNewItem2(pc, 86, 1, 4); // ���� �׸����� �̵���
							}
							if (pc.isIllusionist()) {
								createNewItem2(pc, 450013, 1, 8); // è�Ǿ� Ű��ũ
							}
						}
						/** ������ ���� (��) **/
					} else if (itemId == 7002) {
						if (pc.getInventory().checkItem(7002, 1)) {
							pc.getInventory().consumeItem(7002, 1);
							if (pc.isKnight()) {
								createNewItem2(pc, 120011, 1, 7); // ���� ��� ����(��)
								createNewItem2(pc, 20048, 1, 5); // ȥ���� ����
								createNewItem2(pc, 20078, 1, 7); // ȥ���� ����
								createNewItem2(pc, 21028, 1, 7); // ���� Ƽ����
								createNewItem2(pc, 425106, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 425108, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 20200, 1, 7); // �������� ����
								createNewItem2(pc, 20183, 1, 7); // �ϻ챺���� �尩
								createNewItem2(pc, 420002, 1, 5); // ũ����Ż ����
								createNewItem2(pc, 420104, 1, 7); // ��Ÿ���� �Ϸ�
							}
							if (pc.isCrown()) {
								createNewItem2(pc, 120011, 1, 7); // ���� ��� ����(��)
								createNewItem2(pc, 20048, 1, 5); // ȥ���� ����
								createNewItem2(pc, 20078, 1, 7); // ȥ���� ����
								createNewItem2(pc, 21028, 1, 7); // ���� Ƽ����
								createNewItem2(pc, 425106, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 425108, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 20178, 1, 7); // �������� ����
								createNewItem2(pc, 20183, 1, 7); // �ϻ챺���� �尩
								createNewItem2(pc, 420002, 1, 5); // ũ����Ż ����
								createNewItem2(pc, 420105, 1, 7); // ��Ÿ���� �Ϸ�
							}
							if (pc.isWizard()) {
								createNewItem2(pc, 120011, 1, 7); // ���� ��� ����(��)
								createNewItem2(pc, 20048, 1, 5); // ȥ���� ����
								createNewItem2(pc, 20078, 1, 7); // ȥ���� ����
								createNewItem2(pc, 21028, 1, 7); // ���� Ƽ����
								createNewItem2(pc, 425106, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 425108, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 20218, 1, 7); // �������� ����
								createNewItem2(pc, 20183, 1, 7); // �ϻ챺���� �尩
								createNewItem2(pc, 420002, 1, 5); // ũ����Ż ����
								createNewItem2(pc, 420107, 1, 7); // ��Ÿ���� �Ϸ�
							}
							if (pc.isElf()) {
								createNewItem2(pc, 120011, 1, 7); // ���� ��� ����(��)
								createNewItem2(pc, 20048, 1, 5); // ȥ���� ����
								createNewItem2(pc, 20078, 1, 7); // ȥ���� ����
								createNewItem2(pc, 21028, 1, 7); // ���� Ƽ����
								createNewItem2(pc, 425106, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 425108, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 20216, 1, 7); // �������� ����
								createNewItem2(pc, 20190, 1, 7); // �ϻ챺���� �尩
								createNewItem2(pc, 420002, 1, 5); // ũ����Ż ����
								createNewItem2(pc, 420106, 1, 7); // ��Ÿ���� �Ϸ�
							}
							if (pc.isDarkelf()) {
								createNewItem2(pc, 120011, 1, 7); // ���� ��� ����(��)
								createNewItem2(pc, 20048, 1, 5); // ȥ���� ����
								createNewItem2(pc, 20078, 1, 7); // ȥ���� ����
								createNewItem2(pc, 21028, 1, 7); // ���� Ƽ����
								createNewItem2(pc, 425106, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 425108, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 20200, 1, 7); // �������� ����
								createNewItem2(pc, 20183, 1, 7); // �ϻ챺���� �尩
								createNewItem2(pc, 420002, 1, 5); // ũ����Ż ����
								createNewItem2(pc, 420106, 1, 7); // ��Ÿ���� �Ϸ�
							}
							if (pc.isDragonknight()) {
								createNewItem2(pc, 120011, 1, 7); // ���� ��� ����(��)
								createNewItem2(pc, 20048, 1, 5); // ȥ���� ����
								createNewItem2(pc, 20078, 1, 7); // ȥ���� ����
								createNewItem2(pc, 21028, 1, 7); // ���� Ƽ����
								createNewItem2(pc, 425106, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 425108, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 20200, 1, 7); // �������� ����
								createNewItem2(pc, 20183, 1, 7); // �׺�
								createNewItem2(pc, 420002, 1, 5); // ũ����Ż ����
								createNewItem2(pc, 420105, 1, 7); // ��Ÿ���� �Ϸ�
							}
							if (pc.isIllusionist()) {
								createNewItem2(pc, 120011, 1, 7); // ���� ��� ����(��)
								createNewItem2(pc, 20048, 1, 5); // ȥ���� ����
								createNewItem2(pc, 20078, 1, 7); // ȥ���� ����
								createNewItem2(pc, 21028, 1, 7); // ���� Ƽ����
								createNewItem2(pc, 425106, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 425108, 1, 7); // ���� ������ Ƽ����
								createNewItem2(pc, 20200, 1, 7); // �������� ����
								createNewItem2(pc, 20183, 1, 7); // �ϻ챺���� �尩
								createNewItem2(pc, 420002, 1, 5); // ũ����Ż ����
								createNewItem2(pc, 420107, 1, 7); // ��Ÿ���� �Ϸ�
							}
						}

						/** ������ ���� (�Ǽ�) **/
					} else if (itemId == 7003) {
						if (pc.getInventory().checkItem(7003, 1)) {
							pc.getInventory().consumeItem(7003, 1);
							if (pc.isKnight()) {
								createNewItem2(pc, 101010, 1, 0); // 2�������
								createNewItem2(pc, 202020, 1, 0); // 2������
								createNewItem2(pc, 303030, 1, 0); // 2������
								createNewItem2(pc, 404040, 1, 0); // 2����Ʈ
								createNewItem2(pc, 202020, 1, 0); // 2���Ͱ���

							}
							if (pc.isCrown()) {
								createNewItem2(pc, 101010, 1, 0); // 2�������
								createNewItem2(pc, 202020, 1, 0); // 2������
								createNewItem2(pc, 303030, 1, 0); // 2������
								createNewItem2(pc, 404040, 1, 0); // 2����Ʈ
								createNewItem2(pc, 202020, 1, 0); // 2���Ͱ���
							}
							if (pc.isWizard()) {
								createNewItem2(pc, 101010, 1, 0); // 2�������
								createNewItem2(pc, 202020, 1, 0); // 2������
								createNewItem2(pc, 303030, 1, 0); // 2������
								createNewItem2(pc, 404040, 1, 0); // 2����Ʈ
								createNewItem2(pc, 202020, 1, 0); // 2���Ͱ���
							}
							if (pc.isElf()) {
								createNewItem2(pc, 101010, 1, 0); // 2�������
								createNewItem2(pc, 202020, 1, 0); // 2������
								createNewItem2(pc, 303030, 1, 0); // 2������
								createNewItem2(pc, 404040, 1, 0); // 2����Ʈ
								createNewItem2(pc, 202020, 1, 0); // 2���Ͱ���
							}
							if (pc.isDarkelf()) {
								createNewItem2(pc, 101010, 1, 0); // 2�������
								createNewItem2(pc, 202020, 1, 0); // 2������
								createNewItem2(pc, 303030, 1, 0); // 2������
								createNewItem2(pc, 404040, 1, 0); // 2����Ʈ
								createNewItem2(pc, 202020, 1, 0); // 2���Ͱ���
							}
							if (pc.isDragonknight()) {
								createNewItem2(pc, 101010, 1, 0); // 2�������
								createNewItem2(pc, 202020, 1, 0); // 2������
								createNewItem2(pc, 303030, 1, 0); // 2������
								createNewItem2(pc, 404040, 1, 0); // 2����Ʈ
								createNewItem2(pc, 202020, 1, 0); // 2���Ͱ���
							}
							if (pc.isIllusionist()) {
								createNewItem2(pc, 101010, 1, 0); // 2�������
								createNewItem2(pc, 202020, 1, 0); // 2������
								createNewItem2(pc, 303030, 1, 0); // 2������
								createNewItem2(pc, 404040, 1, 0); // 2����Ʈ
								createNewItem2(pc, 202020, 1, 0); // 2���Ͱ���
							}
						}

						/** �����̵�å **/
						// by���

					} else if (itemId == 5000137) {
						if (pc.getInventory().checkItem(5000137, 1)) {
							int ran = _random.nextInt(100) + 1;
							pc.getInventory().consumeItem(5000137, 1);
							if (ran == 77) {
								createNewItem2(pc, 240074, 1, 0); // 100��Ÿ
							} else {
								int[] �۹�ȣ = { 40308, 41159, 140074, 140087, 40074, 40087, 240087 };
								int k3 = _random.nextInt(7);
								int k = _random.nextInt(9);
								int k2 = _random.nextInt(5);
								switch (k3) {
								case 0:
									int[] ���� = { 1000000, 1500000, 2000000, 2500000, 3000000, 3500000, 4000000, 4500000,
											5000000 };
									createNewItem2(pc, �۹�ȣ[k3], ����[k], 0);
									���� = null;
									break;
								case 1:
									int[] ����1 = { 100, 150, 200, 250, 300, 350, 400, 450, 500 };
									createNewItem2(pc, �۹�ȣ[k3], ����1[k], 0);
									����1 = null;
									break;
								case 2:
								case 3:
								case 4:
								case 5:
								case 6:
									int[] ����2 = { 1, 2, 3, 4, 5 };
									createNewItem2(pc, �۹�ȣ[k3], ����2[k2], 0);
									����2 = null;
									break;
								}
								�۹�ȣ = null;
							}
						}
					} else if (itemId == 40079) { // �Ϲ� ��ȯ ��ũ��

						if (pc.getMap().isEscapable() || pc.isGm()) {
							if (pc.Sabutelok()) {
								pc.setTelType(5);
								pc.sendPackets(new S_SabuTell(pc), true);
								pc.getInventory().removeItem(useItem, 1);
							}
						} else {
							pc.sendPackets(new S_ServerMessage(647), true);
						}
					} else if (itemId == 40095) { // ���ž ��ȯ ��ũ��
						if (pc.getMap().isEscapable() || pc.isGm()) {
							if (pc.Sabutelok()) {
								pc.setTelType(5);
								pc.sendPackets(new S_SabuTell(pc), true);
								pc.getInventory().removeItem(useItem, 1);
							}
						} else {
							pc.sendPackets(new S_ServerMessage(647), true);
						}
					} else if (itemId == 140056) {// ����
						pc.sendPackets(new S_UserStatus(pc, 1), true);
					} else if (itemId == 40081) { // ��� ��ȯ ��ũ��
						if (pc.getMap().isEscapable() || pc.isGm()) {
							if (pc.Sabutelok()) {
								pc.setTelType(11);
								pc.sendPackets(new S_SabuTell(pc), true);
								pc.getInventory().removeItem(useItem, 1);
							}
						} else {
							pc.sendPackets(new S_ServerMessage(647), true);
						}
					} else if (itemId == 41159) { // ����
						if (pc.getMap().isEscapable() || pc.isGm()) {
							if (pc.Sabutelok()) {
								pc.setTelType(77);
								pc.sendPackets(new S_SabuTell(pc), true);
								pc.getInventory().removeItem(useItem, 1);
							}
						} else {
							pc.sendPackets(new S_ServerMessage(647), true);
						}
						
					} else if (itemId == 40124) { // ���� ��ȯ ��ũ��
						if (pc.getMap().isEscapable() || pc.isGm()) {
							int castle_id = 0;
							int house_id = 0;
							if (pc.getClanid() != 0) { // ũ�� �Ҽ�
								L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
								if (clan != null) {
									castle_id = clan.getCastleId();
									house_id = clan.getHouseId();
								}
							}
							if (castle_id != 0) { // ���� ũ����
								if (pc.getMap().isEscapable() || pc.isGm()) {
									if (pc.Sabutelok()) {
										pc.setTelType(2);
										pc.sendPackets(new S_SabuTell(pc), true);
										pc.getInventory().removeItem(useItem, 1);
									}
								} else {
									pc.sendPackets(new S_ServerMessage(647), true);
								}
							} else if (house_id != 0) { // ����Ʈ ���� ũ����
								if (pc.getMap().isEscapable() || pc.isGm()) {
									if (pc.Sabutelok()) {
										pc.setTelType(3);
										pc.sendPackets(new S_SabuTell(pc), true);
										pc.getInventory().removeItem(useItem, 1);
									}
								} else {
									pc.sendPackets(new S_ServerMessage(647), true);
								}
							} else {
								if (pc.Sabutelok()) {
									pc.setTelType(6);
									pc.sendPackets(new S_SabuTell(pc), true);
									pc.getInventory().removeItem(useItem, 1);
								}
							}
						} else {
							pc.sendPackets(new S_ServerMessage(647), true);
						}

						/** ������ ���� (����) **/
					} else if (itemId == 7011) {
						if (pc.getInventory().checkItem(7011, 1)) {
							pc.getInventory().consumeItem(7011, 1);
							if (pc.isKnight() || pc.isDragonknight()) {
								createNewItem2(pc, 61, 1, 7); // ����Ȳ�� �����
							}
							if (pc.isCrown()) {
								createNewItem2(pc, 114, 1, 10); // �������� ��
							}
							if (pc.isWizard()) {
								createNewItem2(pc, 134, 1, 7); // ���� ����ü ������
							}
							if (pc.isElf()) {
								createNewItem2(pc, 205, 1, 9); // ��õ�� Ȱ
							}
							if (pc.isDarkelf()) {
								createNewItem2(pc, 86, 1, 7); // ���� �׸����� �̵���
							}
							if (pc.isIllusionist()) {
								createNewItem2(pc, 450013, 1, 9); // è�Ǿ� Ű��ũ
							}
						}
						
						/** ������ ���� (��) **/
					} else if (itemId == 500206) {
						if (pc.getInventory().checkItem(121216)) {
							pc.sendPackets(new S_ServerMessage(2887));
							return;
						}
						pc.getInventory().consumeItem(500206, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 121216, 1, 0); // ����ü�¹���

					} else if (itemId == 500207) {
						if (pc.getInventory().checkItem(221216)) {
							pc.sendPackets(new S_ServerMessage(2887));
							return;
						}
						pc.getInventory().consumeItem(500207, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 221216, 1, 0); // ����ü�¹���

					} else if (itemId == 7022) {
						����(pc);
						// *******************��������(3�ܰ�)************************//

					} else if (itemId == 447093) {// ��������
						if (pc.getInventory().checkItem(447093, 1)) { // üũ �Ǵ�
																		// �����۰�
																		// ����
							pc.getInventory().consumeItem(447093, 1); // �����Ǵ�
																		// �����۰�
																		// ����
							if (pc.isKnight()) {
								createNewItem2(pc, 425111, 1, 4); // ����ü�¹���
								createNewItem2(pc, 425111, 1, 4); // ����ü�¹���
								createNewItem2(pc, 20320, 1, 0); // Ÿ��
								createNewItem2(pc, 20011, 1, 8); // ����
								createNewItem2(pc, 20056, 1, 8); // ����
								createNewItem2(pc, 20194, 1, 8); // ����
								createNewItem2(pc, 20133, 1, 3); // ����
								createNewItem2(pc, 412001, 1, 9); // ����
								createNewItem2(pc, 20187, 1, 8); // �ı�
								createNewItem2(pc, 425106, 1, 8); // ��Ƽ
								createNewItem2(pc, 20422, 1, 0); // �����
								L1World.getInstance().broadcastPacketToAll(
										new S_SystemMessage("\\fU1:1 ���� �� ��ȭ �Ǵ� ��ȯ�� ���� �ص帮�� �ʽ��ϴ�."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
								pc.sendPackets(new S_SystemMessage("3�� ���������� ���� �Ϸ� ������ Ȱ����Ź�����^^ ."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
							}
							if (pc.isDragonknight()) {
								createNewItem2(pc, 425111, 1, 4); // ����ü�¹���
								createNewItem2(pc, 425111, 1, 4); // ����ü�¹���
								createNewItem2(pc, 20320, 1, 0); // ����ü�¹���
								createNewItem2(pc, 20011, 1, 8); // ����
								createNewItem2(pc, 20056, 1, 8); // ����
								createNewItem2(pc, 20194, 1, 8); // ����
								createNewItem2(pc, 21060, 1, 9); // ����
								createNewItem2(pc, 58, 1, 9); // ����
								createNewItem2(pc, 20187, 1, 8); // �ı�
								createNewItem2(pc, 425106, 1, 8); // ��Ƽ
								createNewItem2(pc, 420013, 1, 0); // �����
								L1World.getInstance().broadcastPacketToAll(
										new S_SystemMessage("\\fU���� �̿ܿ� ���� �Ҹ����� ������ �亯�帮�� �ʽ��ϴ�."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
								pc.sendPackets(new S_SystemMessage("3�� ���������� ���� �Ϸ� ������ Ȱ����Ź�����^^ ."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
							}
							if (pc.isCrown()) {
								createNewItem2(pc, 420105, 1, 8); // ��Ǫ�Ϸ�
								createNewItem2(pc, 20049, 1, 8); // �ݳ�
								createNewItem2(pc, 20200, 1, 9); // ����
								createNewItem2(pc, 420013, 1, 5); // �۰���
								createNewItem2(pc, 294, 1, 10); // ����
								L1World.getInstance().broadcastPacketToAll(
										new S_SystemMessage("\\fU�������� �Խ����� ���а� ���ϴ� �����Ϳ� ���ؼ��� ���� å�� �����ʽ��ϴ�."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
								pc.sendPackets(new S_SystemMessage("3�� ���������� ���� �Ϸ� ������ Ȱ����Ź�����^^ ."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
							}
							if (pc.isWizard()) {
								createNewItem2(pc, 420008, 1, 0); // ����ü�¹���
								createNewItem2(pc, 420008, 1, 0); // ����ü�¹���
								createNewItem2(pc, 20317, 1, 0); // ����ü�¹���
								createNewItem2(pc, 20018, 1, 7); // �޸���
								createNewItem2(pc, 20025, 1, 7); // ���͸�
								createNewItem2(pc, 20218, 1, 7); // ���
								createNewItem2(pc, 20187, 1, 7); // �ı�
								createNewItem2(pc, 20093, 1, 0); // ����
								createNewItem2(pc, 20257, 1, 0); // ���
								createNewItem2(pc, 121, 1, 8); // ����
								createNewItem2(pc, 119, 1, 0); // ����
								createNewItem2(pc, 21031, 1, 7); // �ı�
								createNewItem2(pc, 20253, 1, 0); // �����
								L1World.getInstance().broadcastPacketToAll(
										new S_SystemMessage("\\fU���� �����۰����� �������� �Խ����� �ʵ����ּ���."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
								pc.sendPackets(new S_SystemMessage("3�� ���������� ���� �Ϸ� ������ Ȱ����Ź�����^^ ."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
							}
							if (pc.isIllusionist()) {
								createNewItem2(pc, 420008, 1, 0); // ����ü�¹���
								createNewItem2(pc, 420008, 1, 0); // ����ü�¹���
								createNewItem2(pc, 20317, 1, 0); // ����ü�¹���
								createNewItem2(pc, 20018, 1, 7); // �޸���
								createNewItem2(pc, 20025, 1, 7); // ���͸�
								createNewItem2(pc, 20218, 1, 7); // ���
								createNewItem2(pc, 20187, 1, 7); // �ı�
								createNewItem2(pc, 20093, 1, 0); // ����
								createNewItem2(pc, 20257, 1, 0); // ���
								createNewItem2(pc, 121, 1, 8); // ����
								createNewItem2(pc, 119, 1, 0); // ����
								createNewItem2(pc, 21031, 1, 7); // �ı�
								createNewItem2(pc, 20253, 1, 0); // �����
								L1World.getInstance().broadcastPacketToAll(
										new S_SystemMessage("\\fU������ ���ʽ����� 2�ϸ��� 1�ð��� ���� �˴ϴ�."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
								pc.sendPackets(new S_SystemMessage("3�� ���������� ���� �Ϸ� ������ Ȱ����Ź�����^^ ."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
							}
							if (pc.isElf()) {
								createNewItem2(pc, 425111, 1, 4); // ����ü�¹���
								createNewItem2(pc, 425111, 1, 4); // ����ü�¹���
								createNewItem2(pc, 20317, 1, 0); // ����ü�¹���
								createNewItem2(pc, 20011, 1, 8); // ����
								createNewItem2(pc, 20056, 1, 8); // ����
								createNewItem2(pc, 20194, 1, 8); // ����
								createNewItem2(pc, 21060, 1, 9); // ����
								createNewItem2(pc, 205, 1, 9); // ����
								createNewItem2(pc, 20191, 1, 8); // �ı�
								createNewItem2(pc, 425106, 1, 8); // ��Ƽ
								createNewItem2(pc, 420013, 1, 0); // �����
								L1World.getInstance().broadcastPacketToAll(
										new S_SystemMessage("\\fU��� ���, ���� ���, ���ٵ��� ���α׷� ���� ������ �����Դϴ�."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
								pc.sendPackets(new S_SystemMessage("3�� ���������� ���� �Ϸ� ������ Ȱ����Ź�����^^ ."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
							}
							if (pc.isDarkelf()) {
								createNewItem2(pc, 425111, 1, 4); // ����ü�¹���
								createNewItem2(pc, 425111, 1, 4); // ����ü�¹���
								createNewItem2(pc, 20317, 1, 0); // ����ü�¹���
								createNewItem2(pc, 20011, 1, 8); // ����
								createNewItem2(pc, 20056, 1, 8); // ����
								createNewItem2(pc, 20195, 1, 8); // ����
								createNewItem2(pc, 21060, 1, 9); // ����
								createNewItem2(pc, 84, 1, 8); // ����
								createNewItem2(pc, 20187, 1, 8); // �ı�
								createNewItem2(pc, 425106, 1, 8); // ��Ƽ
								createNewItem2(pc, 20422, 1, 0); // �����
								L1World.getInstance().broadcastPacketToAll(
										new S_SystemMessage("\\fU<< ���� ���� �Խ��� >>�� �� Ȯ���ϼ���."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
								pc.sendPackets(new S_SystemMessage("3�� ���������� ���� �Ϸ� ������ Ȱ����Ź�����^^ ."), true);
								pc.sendPackets(new S_SystemMessage(
										"\\fW#####################################################"), true);
							}
						}
					} else if (itemId == 600233) { // ����
						��ȭ�̺�Ʈ�ֹ���(pc, useItem, l1iteminstance1);
					} else if (itemId == 600240) { // ����
						/*
						 * if(l1iteminstance1.getEnchantLevel() == 0){
						 * ��ȭ����0(pc); }else if(l1iteminstance1.getEnchantLevel()
						 * == 1){ ��ȭ����1(pc); }else
						 * if(l1iteminstance1.getEnchantLevel() == 2){
						 * ��ȭ����2(pc); }else if(l1iteminstance1.getEnchantLevel()
						 * == 3){ ��ȭ����3(pc); }
						 */
					} else if (itemId == 40317 || itemId == 60155) { // ����
						// ���⳪ ���� �ⱸ�� ��츸
						if (l1iteminstance1.getItem().getType2() != 0 && l1iteminstance1.get_durability() > 0) {
							String msg0;
							pc.getInventory().recoveryDamageArmor(pc, l1iteminstance1);// ������
							pc.getInventory().recoveryDamage(l1iteminstance1);
							msg0 = l1iteminstance1.getLogName();
							if (l1iteminstance1.get_durability() == 0) {
								pc.sendPackets(new S_ServerMessage(464, msg0), true); // %0%s��
																						// ��ǰ
																						// ����
																						// ���°�
																						// �Ǿ����ϴ�.
							} else {
								pc.sendPackets(new S_ServerMessage(463, msg0), true); // %0
																						// ���°�
																						// ���������ϴ�.
							}
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 7322 || itemId == 7240) { // �巡���� ���ݼ�
																	// ���
						���(pc, useItem, l1iteminstance1);
					} else if (itemId == L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_DOWN
							|| itemId == L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_DOWN) { // ���ø�����
																						// ����
																						// (��)
						int itemId2 = l1iteminstance1.getItem().getItemId();
						if (itemId == L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_DOWN
								&& itemId2 == L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_UP) {
							if (pc.getInventory().checkItem(L1ItemId.LOWER_OSIRIS_PRESENT_PIECE_UP)) {
								pc.getInventory().removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
								pc.getInventory().storeItem(L1ItemId.CLOSE_LOWER_OSIRIS_PRESENT, 1);
							}
						} else if (itemId == L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_DOWN
								&& itemId2 == L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_UP) {
							if (pc.getInventory().checkItem(L1ItemId.HIGHER_OSIRIS_PRESENT_PIECE_UP)) {
								pc.getInventory().removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
								pc.getInventory().storeItem(L1ItemId.CLOSE_HIGHER_OSIRIS_PRESENT, 1);
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == L1ItemId.LOWER_TIKAL_PRESENT_PIECE_DOWN
							|| itemId == L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_DOWN) { // ���ø�����
																						// ����
																						// (��)
						int itemId2 = l1iteminstance1.getItem().getItemId();
						if (itemId == L1ItemId.LOWER_TIKAL_PRESENT_PIECE_DOWN
								&& itemId2 == L1ItemId.LOWER_TIKAL_PRESENT_PIECE_UP) {
							if (pc.getInventory().checkItem(L1ItemId.LOWER_TIKAL_PRESENT_PIECE_UP)) {
								pc.getInventory().removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
								pc.getInventory().storeItem(L1ItemId.CLOSE_LOWER_TIKAL_PRESENT, 1);
							}
						} else if (itemId == L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_DOWN
								&& itemId2 == L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_UP) {
							if (pc.getInventory().checkItem(L1ItemId.HIGHER_TIKAL_PRESENT_PIECE_UP)) {
								pc.getInventory().removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
								pc.getInventory().storeItem(L1ItemId.CLOSE_HIGHER_TIKAL_PRESENT, 1);
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else

					// �����Ǻ�
					if (itemId == 3000051) {
						int ��ȥ��������� = l1iteminstance1.getItem().getItemId();
						if (��ȥ��������� >= 30070 && 30078 >= ��ȥ���������) {
							if ((_random.nextInt(99) + 1) <= Config.�����Ǻ�) {
								createNewItem(pc, ��ȥ��������� + 10, 1);
								L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,"��� �Ƶ� ��簡 ��ȥ�� ���� ���⿡ ��ȥ�� �Ҿ�ִµ� �����Ͽ� ���� ���⸦ ȹ�� �ϼ̽��ϴ�."));
							} else {
								pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
							}
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_ServerMessage(79));
						}
						
						// ������ ������
						} else
							if (itemId == 3000100) {
							int ���������ī���͹踮�� = l1iteminstance1.getItem().getItemId();
							if (���������ī���͹踮�� == 31000 ) {
								if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
									createNewItem(pc, 41148, 1);
								} else {
									pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
								}
								pc.getInventory().removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
							} else {
							}
								int �������������Ʈ�׷���Ʈ = l1iteminstance1.getItem().getItemId();
								if (�������������Ʈ�׷���Ʈ == 31001 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40222, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �����������Ƽ�Ʈ����ũ = l1iteminstance1.getItem().getItemId();
								if (�����������Ƽ�Ʈ����ũ == 31002 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40219, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int ����������ݿ����ǹ��� = l1iteminstance1.getItem().getItemId();
								if (����������ݿ����ǹ��� == 31003 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 21093, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������10�� = l1iteminstance1.getItem().getItemId();
								if (�������������10�� == 31004 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40288, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������1�� = l1iteminstance1.getItem().getItemId();
								if (�������������1�� == 31005 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 60201, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������2�� = l1iteminstance1.getItem().getItemId();
								if (�������������2�� == 31006 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40280, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������3�� = l1iteminstance1.getItem().getItemId();
								if (�������������3�� == 31007 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40281, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������4�� = l1iteminstance1.getItem().getItemId();
								if (�������������4�� == 31008 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40282, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������5�� = l1iteminstance1.getItem().getItemId();
								if (�������������5�� == 31009 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40283, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������6�� = l1iteminstance1.getItem().getItemId();
								if (�������������6�� == 31010 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40284, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������7�� = l1iteminstance1.getItem().getItemId();
								if (�������������7�� == 31011 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40285, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������8�� = l1iteminstance1.getItem().getItemId();
								if (�������������8�� == 31012 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40286, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int �������������9�� = l1iteminstance1.getItem().getItemId();
								if (�������������9�� == 31013 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 40287, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int ���������������� = l1iteminstance1.getItem().getItemId();
								if (���������������� == 31014 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 7310, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}
								int ����������ҿ���������� = l1iteminstance1.getItem().getItemId();
								if (����������ҿ���������� == 31015 ) {
									if ((_random.nextInt(99) + 1) <= Config.�����ǳ�����) {
										createNewItem(pc, 41149, 1);
									} else {
										pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()));
									}
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
								}

					} else

					if (itemId == L1ItemId.ANCIENT_ROYALSEAL) { // �°��� ����
						if (client.getAccount().getCharSlot() < 8) {
							client.getAccount().setCharSlot(client, client.getAccount().getCharSlot() + 1);
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == L1ItemId.TIMECRACK_CORE) { // �տ��� ��
						int itemId2 = l1iteminstance1.getItem().getItemId();
						if (itemId2 == L1ItemId.CLOSE_LOWER_OSIRIS_PRESENT) {
							if (pc.getInventory().checkItem(L1ItemId.CLOSE_LOWER_OSIRIS_PRESENT)) {
								pc.getInventory().removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
								pc.getInventory().storeItem(L1ItemId.OPEN_LOWER_OSIRIS_PRESENT, 1);
							}
						} else if (itemId2 == L1ItemId.CLOSE_HIGHER_OSIRIS_PRESENT) {
							if (pc.getInventory().checkItem(L1ItemId.CLOSE_HIGHER_OSIRIS_PRESENT)) {
								pc.getInventory().removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
								pc.getInventory().storeItem(L1ItemId.OPEN_HIGHER_OSIRIS_PRESENT, 1);
							}
						} else if (itemId2 == L1ItemId.CLOSE_LOWER_TIKAL_PRESENT) {
							if (pc.getInventory().checkItem(L1ItemId.CLOSE_LOWER_TIKAL_PRESENT)) {
								pc.getInventory().removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
								pc.getInventory().storeItem(L1ItemId.OPEN_LOWER_TIKAL_PRESENT, 1);
							}
						} else if (itemId2 == L1ItemId.CLOSE_HIGHER_TIKAL_PRESENT) {
							if (pc.getInventory().checkItem(L1ItemId.CLOSE_HIGHER_TIKAL_PRESENT)) {
								pc.getInventory().removeItem(l1iteminstance1, 1);
								pc.getInventory().removeItem(useItem, 1);
								pc.getInventory().storeItem(L1ItemId.OPEN_HIGHER_TIKAL_PRESENT, 1);
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 40097 || itemId == 40119 || itemId == 140119 || itemId == 140329) { // ���ֽ�ũ��,
																												// ���ֹ���
																												// ����
						L1Item template = null;
						for (L1ItemInstance eachItem : pc.getInventory().getItems()) {
							if (eachItem.getItem().getBless() != 2) {
								continue;
							}
							if (!eachItem.isEquipped() && (itemId == 40119 || itemId == 40097)) {
								// n���ִ� ��� �ϰ� �ִ� �� �ۿ� ���� ���� �ʴ´�
								continue;
							}
							int id_normal = eachItem.getItemId() - 200000;
							template = ItemTable.getInstance().getTemplate(id_normal);
							if (template == null) {
								continue;
							}
							if (pc.getInventory().checkItem(id_normal) && template.isStackable()) {
								pc.getInventory().storeItem(id_normal, eachItem.getCount());
								pc.getInventory().removeItem(eachItem, eachItem.getCount());
							} else {
								eachItem.setItem(template);
								pc.getInventory().updateItem(eachItem, L1PcInventory.COL_ITEMID);
								pc.getInventory().saveItem(eachItem, L1PcInventory.COL_ITEMID);
								eachItem.setBless(eachItem.getBless() - 1);
								pc.getInventory().updateItem(eachItem, L1PcInventory.COL_BLESS);
								pc.getInventory().saveItem(eachItem, L1PcInventory.COL_BLESS);
							}
						}
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SystemMessage("�������� �����ִ� �Ͱ����ϴ�."), true); // \f1��������
																						// ����
																						// ��
																						// ��
																						// �����ϴ�.
					} else if (itemId == 41036) { // Ǯ
						int diaryId = l1iteminstance1.getItem().getItemId();
						if (diaryId >= 41038 && 41047 >= diaryId) {
							if ((_random.nextInt(99) + 1) <= Config.CREATE_CHANCE_DIARY) {
								createNewItem(pc, diaryId + 10, 1);
							} else {
								pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()), true); // \f1%0��
																											// �����ϰ�
																											// ����
																											// �ʰ�
																											// �Ǿ����ϴ�.
							}
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 40964) { // �渶�� ����
						int historybookId = l1iteminstance1.getItem().getItemId();
						if (historybookId >= 41011 && 41018 >= historybookId) {
							if ((_random.nextInt(99) + 1) <= Config.CREATE_CHANCE_HISTORY_BOOK) {
								createNewItem(pc, historybookId + 8, 1);
							} else {
								pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()), true);
							}
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 40925) { // ��ȭ�� �Ϻ�
						int earingId = l1iteminstance1.getItem().getItemId();
						if (earingId >= 40987 && 40989 >= earingId) { // �������� ��
																		// �� ��
							if (_random.nextInt(100) < Config.CREATE_CHANCE_RECOLLECTION) {
								createNewItem(pc, earingId + 186, 1);
							} else {
								pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()), true); // \f1%0��
																											// �����ϰ�
																											// ����
																											// �ʰ�
																											// �Ǿ����ϴ�.
							}
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId >= 40926 && 40929 >= itemId) {
						// �ź����� �Ϻ�(1~4 �ܰ�)
						int earing2Id = l1iteminstance1.getItem().getItemId();
						int potion1 = 0;
						int potion2 = 0;
						if (earing2Id >= 41173 && 41184 >= earing2Id) {
							// �� ����
							if (itemId == 40926) {
								potion1 = 247;
								potion2 = 249;
							} else if (itemId == 40927) {
								potion1 = 249;
								potion2 = 251;
							} else if (itemId == 40928) {
								potion1 = 251;
								potion2 = 253;
							} else if (itemId == 40929) {
								potion1 = 253;
								potion2 = 255;
							}
							if (earing2Id >= (itemId + potion1) && (itemId + potion2) >= earing2Id) {
								if ((_random.nextInt(99) + 1) < Config.CREATE_CHANCE_MYSTERIOUS) {
									createNewItem(pc, (earing2Id - 12), 1);
									pc.getInventory().removeItem(l1iteminstance1, 1);
									pc.getInventory().removeItem(useItem, 1);
								} else {
									pc.sendPackets(new S_ServerMessage(160, l1iteminstance1.getName()), true);
									// \f1%0��%2 �����ϰ�%1 �������ϴٸ�, ������ �����ϰ� ��ҽ��ϴ�.
									pc.getInventory().removeItem(useItem, 1);
								}
							} else {
								pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																								// �ƹ��͵�
																								// �Ͼ��
																								// �ʾҽ��ϴ�.
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 46161) {
						for (L1Object obj : L1World.getInstance().getVisibleObjects(pc, 10)) {
							if (obj instanceof L1MonsterInstance) {
								L1NpcInstance npc = (L1NpcInstance) obj;
								npc.receiveDamage(pc, 50000);
							}
						}
					} else if (itemId == 5000677) { /// ��ڼ�Ʈ
						if (pc.getInventory().getSize() > 100) {
							pc.sendPackets(new S_SystemMessage("����â�� �����۰����� 100���� �Ѿ ����Ҽ� �����ϴ�."));
							return;
						}

						if (pc.getInventory().checkItem(5000677, 1)) {
							pc.getInventory().storeItem(5000682, 1);
							pc.getInventory().storeItem(46160, 1);
							pc.getInventory().storeItem(46161, 1);
							pc.getInventory().storeItem(5000683, 1);
							pc.getInventory().storeItem(5000684, 1);
							pc.getInventory().storeItem(5000685, 1);
							pc.getInventory().storeItem(5000686, 1);
							pc.sendPackets(new S_SystemMessage("\\fW��� �������� ���޵Ǿ����ϴ�."));
						}
					} else if (itemId == 5000682) { // 9��20�ϸ�Ƽ���� ��ȣ
						int objid = pc.getId();
						pc.sendPackets(new S_SkillSound(objid, 4856)); // 3944
						Broadcaster.broadcastPacket(pc, new S_SkillSound(objid, 4856));
						for (L1PcInstance tg : L1World.getInstance().getVisiblePlayer(pc)) {
							if (tg.getCurrentHp() == 0 && tg.isDead()) {
								tg.sendPackets(new S_SystemMessage("GM�� ��Ȱ�� ���־����ϴ�. "));
								Broadcaster.broadcastPacket(tg, new S_SkillSound(tg.getId(), 3944));
								tg.sendPackets(new S_SkillSound(tg.getId(), 3944));
								// �ູ�� ��Ȱ ��ũ�Ѱ� ���� ȿ��
								tg.setTempID(objid);
								tg.sendPackets(new S_Message_YN(322, "")); // ��
																			// ��Ȱ�ϰ�
																			// �ͽ��ϱ�?
																			// (Y/N)
							} else {
								tg.sendPackets(new S_SystemMessage("GM�� HP,MP�� ȸ�����־����ϴ�."));
								Broadcaster.broadcastPacket(tg, new S_SkillSound(tg.getId(), 832));
								tg.sendPackets(new S_SkillSound(tg.getId(), 832));
								tg.setCurrentHp(tg.getMaxHp());
								tg.setCurrentMp(tg.getMaxMp());
							}
						}
					} else if (itemId == 41029) { // ��ȯ�� ����
						int dantesId = l1iteminstance1.getItem().getItemId();
						if (dantesId >= 41030 && 41034 >= dantesId) { // ��ȯ����
																		// �ھ ��
																		// �ܰ�
							if ((_random.nextInt(99) + 1) < Config.CREATE_CHANCE_DANTES) {
								createNewItem(pc, dantesId + 1, 1);
							} else {
								pc.sendPackets(new S_ServerMessage(158, l1iteminstance1.getName()), true); // \f1%0��
																											// �����ϰ�
																											// ����
																											// �ʰ�
																											// �Ǿ����ϴ�.
							}
							pc.getInventory().removeItem(l1iteminstance1, 1);
							pc.getInventory().removeItem(useItem, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
						// ���� ��ũ��
					} else

					if (((itemId >= 40859 && itemId <= 40898) && itemId != 40863)
							|| (itemId >= 60003 && itemId <= 60008)) { // 40863��
																		// �ڷ���Ʈ
																		// ��ũ�ѷμ�
																		// ó���ȴ�
						// System.out.println("������ :");
						/*
						 * if(pc.getMapId()==777 ||pc.getMapId()==778
						 * ||pc.getMapId()==779 ){ pc.sendPackets(new
						 * S_SystemMessage("�������� �����ֹ��� ����� �����Դϴ�."), true);
						 * return; }
						 */

						/*
						 * if (pc.isSkillDelay()) { //
						 * System.out.println("������"); return; }
						 */
						if (pc.getInventory().calcWeightpercent() >= 90) {
							pc.sendPackets(new S_SystemMessage("������ ��� ���� : ���� ������ 90% �̻� ��� �Ұ�."));
							return;
						}
						if (pc.isTeleport() || pc.isDead()) {
							pc.sendPackets(new S_ServerMessage(281), true);
							// \f1�����̹�ȿ���Ǿ����ϴ�.
							return;
						}

						pc.getInventory().removeItem(useItem, 1);

						/*
						 * if (spellsc_objid == 0 &&
						 * useItem.getItem().getUseType() != 0 &&
						 * useItem.getItem().getUseType() != 26 &&
						 * useItem.getItem().getUseType() != 27) { return; }
						 */

						pc.cancelAbsoluteBarrier(); // �ƺ�Ҹ�Ʈ�ٸ����� ����
						int skillid = itemId - 40858;
						if (itemId == 60003)
							skillid = 42;
						else if (itemId == 60004)
							skillid = 48;
						else if (itemId == 60005)
							skillid = 52;
						else if (itemId == 60006)
							skillid = 57;
						else if (itemId == 60007)
							skillid = 54;
						else if (itemId == 60008)
							skillid = 49;

						L1Skills _skill = SkillsTable.getInstance().getTemplate(skillid);

						double delay_rate = 0.900;// 874;
						int delay = _skill.getScrollReuseDelay();
						// System.out.println("������ : "+delay);

						if (pc.isHaste()) {
							delay *= delay_rate;
						}

						if (pc.isBrave()) {
							delay *= delay_rate;
						}

						if (delay > 0) {
							pc.setSkillDelay(true);
							GeneralThreadPool.getInstance().schedule(new L1SkillDelay(pc, delay), delay);
						}

						pc.�÷��̾���� = pc.����_����;
						pc.���½ð� = System.currentTimeMillis() + 2000;
						// System.out.println("����2");
						L1SkillUse l1skilluse = new L1SkillUse();
						l1skilluse.handleCommands(client.getActiveChar(), skillid, spellsc_objid, spellsc_x, spellsc_y,
								null, 0, L1SkillUse.TYPE_SPELLSC);
						l1skilluse = null;
					}

					else

					if (itemId >= 40373 && itemId <= 40384 // ���� ����
							|| itemId >= 40385 && itemId <= 40390) {
						pc.sendPackets(new S_UseMap(pc, useItem.getId(), useItem.getItem().getItemId()), true);
					} else if (itemId == 40314 || itemId == 40316) { // ���� �ƹ·�Ʈ
						if (pc.getInventory().checkItem(41160)) { // ��ȯ�� �Ǹ�
							if (withdrawPet(pc, itemObjid)) {
								pc.getInventory().consumeItem(41160, 1);
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 40315) { // ���� �Ǹ�
						pc.sendPackets(new S_Sound(437), true);
						Broadcaster.broadcastPacket(pc, new S_Sound(437), true);
						for (Object petObject : pc.getPetList()) {
							if (petObject instanceof L1PetInstance) { // ��
								L1PetInstance pet = (L1PetInstance) petObject;
								pet.call();
							}
						}
					} else if (itemId == 40493) { // ���� �÷�
						pc.sendPackets(new S_Sound(165));
						Broadcaster.broadcastPacket(pc, new S_Sound(165), true);
						L1GuardianInstance guardian = null;
						for (L1Object visible : pc.getNearObjects().getKnownObjects()) {
							if (visible instanceof L1GuardianInstance) {
								guardian = (L1GuardianInstance) visible;
								if (guardian.getNpcTemplate().get_npcId() == 70850) { // ��
									if (createNewItem(pc, 88, 1)) {
										pc.getInventory().removeItem(useItem, 1);
									}
								}
							}
						}
					} else if (itemId == 40325) {
						if (pc.getInventory().checkItem(40318, 1)) {
							int gfxid = 3237 + _random.nextInt(2);
							pc.sendPackets(new S_SkillSound(pc.getId(), gfxid), true);
							Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfxid), true);
							pc.getInventory().consumeItem(40318, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�.")); // \f1
																						// �ƹ��͵�
																						// �Ͼ��
																						// �ʾҽ��ϴ�.
						}
					} else if (itemId == 40326) {
						if (pc.getInventory().checkItem(40318, 1)) {
							int gfxid = 3229 + _random.nextInt(3);
							pc.sendPackets(new S_SkillSound(pc.getId(), gfxid), true);
							Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfxid), true);
							pc.getInventory().consumeItem(40318, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 40327) {
						if (pc.getInventory().checkItem(40318, 1)) {
							int gfxid = 3241 + _random.nextInt(4);
							pc.sendPackets(new S_SkillSound(pc.getId(), gfxid), true);
							Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfxid), true);
							pc.getInventory().consumeItem(40318, 1);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 40328) {
						if (pc.getInventory().checkItem(40318, 1)) {
							int gfxid = 3204 + _random.nextInt(6);
							pc.sendPackets(new S_SkillSound(pc.getId(), gfxid), true);
							Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfxid), true);
							pc.getInventory().consumeItem(40318, 1);
						} else {
							// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}

					} else if (itemId == 5000085) {
						EventT(pc, useItem);
						int special = _random.nextInt(20);
						switch (special) {
						case 0:
							pc.getInventory().storeItem(490009, 1);
							break;
						default:
							pc.getInventory().storeItem(490000, 1);
							break;
						}

					} else if (itemId == 600250) {
						�Ƹ�ī������������(pc, useItem);

					} else if (itemId == 600257) {
						����Ȳ�ǻ���(pc, useItem);

					} else if (itemId == 600248) {
						�Ƹ�ī���������ָӴ�(pc, useItem);
					} else if (itemId == 5000086) {
						EventT(pc, useItem);
						int special = _random.nextInt(20);
						switch (special) {
						case 0:
							pc.getInventory().storeItem(490010, 1);
							break;
						default:
							pc.getInventory().storeItem(490001, 1);
							break;
						}
					} else if (itemId == 5000087) {
						EventT(pc, useItem);
						int special = _random.nextInt(20);
						switch (special) {
						case 0:
							pc.getInventory().storeItem(490011, 1);
							break;
						default:
							pc.getInventory().storeItem(490002, 1);
							break;
						}
					} else if (itemId == 5000088) {
						EventT(pc, useItem);
						int special = _random.nextInt(20);
						switch (special) {
						case 0:
							pc.getInventory().storeItem(490012, 1);
							break;
						default:
							pc.getInventory().storeItem(490003, 1);
							break;
						}
					} else if (itemId == 5000089) {
						EventT(pc, useItem);
						int special = _random.nextInt(20);
						switch (special) {
						case 0:
							pc.getInventory().storeItem(490013, 1);
							break;
						default:
							pc.getInventory().storeItem(490004, 1);
							break;
						}
					} else if (itemId == 5000090) {
						EventT(pc, useItem);
						int special = _random.nextInt(20);
						switch (special) {
						case 0:
							pc.getInventory().storeItem(490014, 1);
							break;
						default:
							pc.getInventory().storeItem(490005, 1);
							break;
						}
					} else if (itemId == 5000091) {
						EventT(pc, useItem);
						int special = _random.nextInt(20);
						switch (special) {
						case 0:
							pc.getInventory().storeItem(490015, 1);
							break;
						default:
							pc.getInventory().storeItem(490006, 1);
							break;
						}
					} else if (itemId == 5000092) {
						EventT(pc, useItem);
						int special = _random.nextInt(20);
						switch (special) {
						case 0:
							pc.getInventory().storeItem(490016, 1);
							break;
						default:
							pc.getInventory().storeItem(490007, 1);
							break;
						}
					} else if (itemId == 5000093) {
						EventT(pc, useItem);
						int special = _random.nextInt(20);
						switch (special) {
						case 0:
							pc.getInventory().storeItem(490017, 1);
							break;
						default:
							pc.getInventory().storeItem(490008, 1);
							break;
						}
					} else if (itemId == 5000094) {
						pc.getInventory().removeItem(useItem, 1);
						pc.getInventory().storeItem(5000098, 5);

					} else if (itemId >= 5001120 && itemId <= 5001129) {
						useToiTeleportAmulets(pc, itemId, useItem);
					} else if (itemId >= 5000100 && itemId <= 5000109) {
						pc.getInventory().removeItem(useItem, 1);
						int chance = _random.nextInt(100);
						if (chance <= 50) {
							L1ItemInstance item = pc.getInventory().storeItem(itemId + 1020, 1);
							if (item != null) {
								pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true);
							}
						} else {
							L1ItemInstance item = null;
							if (itemId == 5000100) {
								item = pc.getInventory().storeItem(60202, 1);
							} else {
								item = pc.getInventory().storeItem(itemId - 4959812, 1);
							}
							if (item != null) {
								pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true);
							}
						}
					} else if (itemId >= 5000110 && itemId <= 5000119) {
						ȥ������(pc, useItem, itemId);
					} else if (itemId == L1ItemId.CHARACTER_REPAIR_SCROLL) {
						Connection connection = null;
						PreparedStatement preparedstatement = null;
						Connection connection2 = null;
						PreparedStatement preparedstatement2 = null;
						ResultSet rs = null;
						try {
							connection2 = L1DatabaseFactory.getInstance().getConnection();
							preparedstatement2 = connection2
									.prepareStatement("SELECT * FROM characters WHERE account_name = ?");
							preparedstatement2.setString(1, client.getAccountName());
							rs = preparedstatement2.executeQuery();
							while (rs.next()) {
								int objid = rs.getInt("objid");
								int mapid = rs.getInt("MapID");
								if (mapid != 99 && mapid != 6202) {
									try {
										connection = L1DatabaseFactory.getInstance().getConnection();
										preparedstatement = connection.prepareStatement(
												"UPDATE characters SET LocX=33087, LocY=33399, MapID=4 WHERE objid = ?");
										preparedstatement.setInt(1, objid);
										preparedstatement.executeUpdate();
									} catch (Exception e) {
										e.printStackTrace();
									} finally {
										SQLUtil.close(preparedstatement);
										SQLUtil.close(connection);
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							SQLUtil.close(rs);
							SQLUtil.close(preparedstatement2);
							SQLUtil.close(connection2);
						}
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SystemMessage("��� �ɸ����� ��ǥ�� ���������� ���� �Ǿ����ϴ�."), true);
					} else if (itemId == 40903 || itemId == 40904 || itemId == 40905 || itemId == 40906
							|| itemId == 40907 || itemId == 40908) { // ���� ��ȥ ����
						��ȥ��(pc, useItem);
					} else if (itemId == 787880) {// �巡�� ��
						if (pc.isDead()) {
							return;
						}
						if (pc.isParalyzed()) {
							return;
						}
						if (!((pc.getMapId() >= 1005 && pc.getMapId() <= 1016)
								|| (pc.getMapId() >= 10000 && pc.getMapId() <= 10005))) {
							pc.sendPackets(new S_SystemMessage("�巡���� ������ ��� �������� ����� �� �ֽ��ϴ�."), true);
							return;
						}
						pc.getInventory().consumeItem(787880, 1); // �����Ǵ� �����۰�
																	// ����
						useCashScroll(pc, L1ItemId.INCRESE_ATTACK_SCROLL, true);

					} else if (itemId == 787879) {// �巡�� ����
						if (pc.isDead()) {
							return;
						}
						if (pc.isParalyzed()) {
							return;
						}
						if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																						// ����
							pc.sendPackets(new S_ServerMessage(698), true); // ���¿�
																			// ����
																			// �ƹ��͵�
																			// ����
																			// ����
																			// �����ϴ�.
							return;
						}
						if (!((pc.getMapId() >= 1005 && pc.getMapId() <= 1016)
								|| (pc.getMapId() >= 10000 && pc.getMapId() <= 10005))) {
							pc.sendPackets(new S_SystemMessage("�巡���� ������ ��� �������� ����� �� �ֽ��ϴ�."), true);
							return;
						}
						pc.getInventory().consumeItem(787879, 1); // �����Ǵ� �����۰�
																	// ����
						pc.sendPackets(new S_SkillSound(pc.getId(), 189));
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 189), true);
						int healHP = _random.nextInt(400) + 200;
						pc.setCurrentHp(pc.getCurrentHp() + healHP);

					} else if (itemId == 787878) {// �޾Ƴ� �巡���� ����
						pc.getInventory().consumeItem(787878, 1); // �����Ǵ� �����۰�
																	// ����
						createNewItem2(pc, 787879, 10, 0); // ���� 10��
						createNewItem2(pc, 787880, 1, 0); // ���� 1��

					} else if (itemId == 430005) {
						if (pc.getMapId() != 6202) {
							if (pc.getLevel() > 50) {
								if (pc.getInventory().checkItem(L1ItemId.REMINISCING_CANDLE)) {
									pc.getInventory().consumeItem(L1ItemId.REMINISCING_CANDLE, 1);
									L1Teleport.teleport(pc, 32723 + _random.nextInt(10), 32851 + _random.nextInt(10),
											(short) 5166, 5, true);
									StatInitialize(pc);
									eva.LogBugAppend("���:����", pc, 5);
								} else {
									pc.sendPackets(new S_ServerMessage(1290));
								}
							} else {
								pc.sendPackets(new S_SystemMessage("�����ʱ�ȭ�� 51���� �̿��ϽǼ� �ֽ��ϴ�."));
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�������� ��� �Ұ��� ������ �Դϴ�."));
						}
					} else if (itemId == 40555) { // ����� ���� Ű
						// ���� ��
						if (pc.isKnight() && (pc.getX() >= 32806 && pc.getX() <= 32814)
								&& (pc.getY() >= 32798 && pc.getY() <= 32807) && pc.getMapId() == 13) {
							if (pc.Sabutelok()) {
								pc.dx = 32815;
								pc.dy = 32810;
								pc.dm = (short) 13;
								pc.dh = pc.getMoveState().getHeading();
								pc.setTelType(7);
								pc.sendPackets(new S_SabuTell(pc), true);
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 40417) { // �����ǰ���
						if (pc.getMapId() == 440 && pc.getX() > 32667 && pc.getX() < 32673 && pc.getY() > 32977
								&& pc.getY() < 32984) {
							if (pc.Sabutelok()) {
								pc.dx = 32734;
								pc.dy = 32806;
								pc.dm = (short) 444;
								pc.dh = pc.getMoveState().getHeading();
								pc.setTelType(7);
								pc.sendPackets(new S_SabuTell(pc), true);
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 600222) {
						if (pc.isPinkName()) {
							pc.sendPackets(new S_SystemMessage("�������̶� ����� �� �����ϴ�."));
							return;
						}
						if (pc.isFishing() || pc.isFishingReady() || pc.isPrivateShop()) {
							return;
						}
						long curtime = System.currentTimeMillis() / 1000;
						if (pc.getQuizTime() + 20 > curtime) {
							pc.sendPackets(new S_SystemMessage("20�ʰ��� �����ð��� �ʿ��մϴ�."));
							return;
						}
						L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(), pc.getMoveState().getHeading(),
								false);
						pc.sendPackets(new S_SystemMessage("�ֺ� ������Ʈ�� ��ε� �Ͽ����ϴ�."));
						pc.setQuizTime(curtime);
						pc.getInventory().consumeItem(600222, 1); // �����Ǵ� �����۰�
																	// ����
					} else if (itemId == 40566) { // �ź����� ��
						// ����� ž�� ������ ���ʿ� �ִ� ���� �������� ��ǥ
						if (pc.isElf() && (pc.getX() >= 33971 && pc.getX() <= 33975)
								&& (pc.getY() >= 32324 && pc.getY() <= 32328) && pc.getMapId() == 4
								&& !pc.getInventory().checkItem(40548)) { // ������
																			// ����
							boolean found = false;
							L1MonsterInstance mob = null;
							for (L1Object obj : L1World.getInstance().getVisibleObjects(4).values()) {
								if (obj instanceof L1MonsterInstance) {
									mob = (L1MonsterInstance) obj;
									if (mob != null) {
										if (mob.getNpcTemplate().get_npcId() == 45300) {
											found = true;
											break;
										}
									}
								}
							}
							if (found) {
								pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																								// �ƹ��͵�
																								// �Ͼ��
																								// �ʾҽ��ϴ�.
							} else {
								L1SpawnUtil.spawn(pc, 45300, 0, 0, false); // �������
																			// ����
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
						}
					} else if (itemId == 40557) {
						if (pc.getX() == 32620 && pc.getY() == 32641 && pc.getMapId() == 4) {
							L1NpcInstance object = null;
							for (L1Object obj : L1World.getInstance().getObject()) {
								if (obj instanceof L1NpcInstance) {
									object = (L1NpcInstance) obj;
									if (object.getNpcTemplate().get_npcId() == 45883) {
										pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
										return;
									}
								}

							}
							L1SpawnUtil.spawn(pc, 45883, 0, 300000, false);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 40563) {
						if (pc.getX() == 32730 && pc.getY() == 32426 && pc.getMapId() == 4) {
							L1NpcInstance object = null;
							for (L1Object obj : L1World.getInstance().getObject()) {
								if (obj instanceof L1NpcInstance) {
									object = (L1NpcInstance) obj;
									if (object.getNpcTemplate().get_npcId() == 45884) {
										pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
										return;
									}
								}

							}
							L1SpawnUtil.spawn(pc, 45884, 0, 300000, false);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 40561) {
						if (pc.getX() == 33046 && pc.getY() == 32806 && pc.getMapId() == 4) {
							L1NpcInstance object = null;
							for (L1Object obj : L1World.getInstance().getObject()) {
								if (obj instanceof L1NpcInstance) {
									object = (L1NpcInstance) obj;
									if (object.getNpcTemplate().get_npcId() == 45885) {
										pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
										return;
									}
								}

							}
							L1SpawnUtil.spawn(pc, 45885, 0, 300000, false);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}

					} else if (itemId == 40560) {
						if (pc.getX() == 32580 && pc.getY() == 33260 && pc.getMapId() == 4) {
							L1NpcInstance object = null;
							for (L1Object obj : L1World.getInstance().getObject()) {
								if (obj instanceof L1NpcInstance) {
									object = (L1NpcInstance) obj;
									if (object.getNpcTemplate().get_npcId() == 45886) {
										pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
										return;
									}
								}

							}
							L1SpawnUtil.spawn(pc, 45886, 0, 300000, false);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 40562) {
						if (pc.getX() == 33447 && pc.getY() == 33476 && pc.getMapId() == 4) {
							L1NpcInstance object = null;
							for (L1Object obj : L1World.getInstance().getObject()) {
								if (obj instanceof L1NpcInstance) {
									object = (L1NpcInstance) obj;
									if (object.getNpcTemplate().get_npcId() == 45887) {
										pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
										return;
									}
								}

							}
							L1SpawnUtil.spawn(pc, 45887, 0, 300000, false);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 40559) {
						if (pc.getX() == 34215 && pc.getY() == 33195 && pc.getMapId() == 4) {
							L1NpcInstance object = null;
							for (L1Object obj : L1World.getInstance().getObject()) {
								if (obj instanceof L1NpcInstance) {
									object = (L1NpcInstance) obj;
									if (object.getNpcTemplate().get_npcId() == 45888) {
										pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
										return;
									}
								}

							}
							L1SpawnUtil.spawn(pc, 45888, 0, 300000, false);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 40558) {
						if (pc.getX() == 33513 && pc.getY() == 32890 && pc.getMapId() == 4) {
							L1NpcInstance object = null;
							for (L1Object obj : L1World.getInstance().getObject()) {
								if (obj instanceof L1NpcInstance) {
									object = (L1NpcInstance) obj;
									if (object.getNpcTemplate().get_npcId() == 45889) {
										pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
										return;
									}
								}

							}
							L1SpawnUtil.spawn(pc, 45889, 0, 300000, false);
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 40572) {
						if (pc.getX() == 32778 && pc.getY() == 32738 && pc.getMapId() == 21) {
							if (pc.Sabutelok()) {
								pc.dx = 32781;
								pc.dy = 32728;
								pc.dm = (short) 21;
								pc.dh = pc.getMoveState().getHeading();
								pc.setTelType(7);
								pc.sendPackets(new S_SabuTell(pc), true);
							}
						} else if (pc.getX() == 32781 && pc.getY() == 32728 && pc.getMapId() == 21) {
							if (pc.Sabutelok()) {
								pc.dx = 32778;
								pc.dy = 32738;
								pc.dm = (short) 21;
								pc.dh = pc.getMoveState().getHeading();
								pc.setTelType(7);
								pc.sendPackets(new S_SabuTell(pc), true);
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 40009) {// �߹渷��
						/*
						 * int chargeCount = useItem.getChargeCount(); if
						 * (chargeCount <= 0) { pc.sendPackets(new
						 * S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);// \f1
						 * �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�. return; }
						 */
						L1Object target = L1World.getInstance().findObject(spellsc_objid);
						if (target != null && target instanceof L1Character) {
							L1Character cha2 = (L1Character) target;
							if (pc != target) {
								int heding = CharPosUtil.targetDirection(pc, spellsc_x, spellsc_y);
								pc.getMoveState().setHeading(heding);
							}
							pc.sendPackets(new S_AttackPacket(pc, 0, ActionCodes.ACTION_Wand), true);
							Broadcaster.broadcastPacket(pc, new S_AttackPacket(pc, 0, ActionCodes.ACTION_Wand), true);

							if (cha2.getSkillEffectTimerSet().hasSkillEffect(COUNTER_MAGIC)) {
								cha2.getSkillEffectTimerSet().removeSkillEffect(COUNTER_MAGIC);
								int castgfx = SkillsTable.getInstance().getTemplate(COUNTER_MAGIC).getCastGfx();
								Broadcaster.broadcastPacket(cha2, new S_SkillSound(cha2.getId(), castgfx), true);
								if (cha2 instanceof L1PcInstance) {
									L1PcInstance pc2 = (L1PcInstance) cha2;
									pc2.sendPackets(new S_SkillSound(pc2.getId(), castgfx), true);
								}
								return;
							}

							if (target instanceof L1PcInstance) {
								L1PcInstance cha = (L1PcInstance) target;
								if (pc != cha) {
									if (pc.getLevel() > cha.getLevel() && 30 < _random.nextInt(100)) {
										if (!L1CastleLocation.checkInAllWarArea(cha.getX(), cha.getY(), cha.getMapId())
												&& (CharPosUtil.getZoneType(cha) == 0
														|| CharPosUtil.getZoneType(cha) == -1)) {
											byte HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
											byte HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };
											int heading = _random.nextInt(8);
											heading = checkObject(cha.getX(), cha.getY(), cha.getMapId(), heading);
											if (heading != -1) {
												int tempx = HEADING_TABLE_X[heading];
												int tempy = HEADING_TABLE_Y[heading];
												if (cha.Sabutelok()) {
													cha.dx = cha.getX() + tempx;
													cha.dy = cha.getY() + tempy;
													cha.dm = (short) cha.getLocation().getMapId();
													cha.dh = cha.getMoveState().getHeading();
													cha.setTelType(10);
													cha.sendPackets(new S_SabuTell(cha), true);
												}
											}
											HEADING_TABLE_X = null;
											HEADING_TABLE_Y = null;
										}
									}
								}
								if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC)) {
									cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
								}
							}
						}

						// useItem.setChargeCount(useItem.getChargeCount() - 1);
						// pc.getInventory().updateItem(useItem,
						// L1PcInventory.COL_CHARGE_COUNT);
						// if (useItem.getChargeCount() == 0){
						pc.getInventory().removeItem(useItem, 1);
						// }

						if (useItem.isIdentified()) {
							useItem.setIdentified(true);
							pc.sendPackets(new S_ItemName(useItem), true);
						}

					} else if (itemId == L1ItemId.ICECAVE_KEY) {
						L1Object t = L1World.getInstance().findObject(spellsc_objid);
						L1DoorInstance door = (L1DoorInstance) t;
						if (pc.getLocation().getTileLineDistance(door.getLocation()) > 3) {
							return;
						}
						if (door.getDoorId() >= 5000 && door.getDoorId() <= 5009) {
							if (door != null && door.getOpenStatus() == ActionCodes.ACTION_Close) {
								door.open();
								pc.getInventory().removeItem(useItem, 1);
							}
						}
					} else if (itemId == 60202 || itemId >= 40289 && itemId <= 40297) { // ������
																						// ž11~91��
																						// ����
						useToiTeleportAmulet(pc, itemId, useItem);
					} else if (itemId >= 40280 && itemId <= 40288) {
						// ���ε� ������ ž 11~91�� ����
						pc.getInventory().removeItem(useItem, 1);
						L1ItemInstance item = pc.getInventory().storeItem(itemId + 9, 1);
						if (item != null) {
							pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true);
						}
						/*
						 * } else if (itemId == 40070) { pc.sendPackets(new
						 * S_ServerMessage(76, useItem.getLogName()));
						 * pc.getInventory().removeItem(useItem, 1);
						 */
					} else if (itemId == 41301) { // ���̴׷����ͽ�
						int chance = _random.nextInt(10);
						if (chance >= 0 && chance < 5) {
							UseHeallingPotion(pc, 15, 189);
						} else if (chance >= 5 && chance < 9) {
							createNewItem(pc, 40019, 1);
						} else if (chance >= 9) {
							int gemChance = _random.nextInt(3);
							if (gemChance == 0) {
								createNewItem(pc, 40045, 1);
							} else if (gemChance == 1) {
								createNewItem(pc, 40049, 1);
							} else if (gemChance == 2) {
								createNewItem(pc, 40053, 1);
							}
						}
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 41302) { // ���̴ױ׸��ͽ�
						int chance = _random.nextInt(3);
						if (chance >= 0 && chance < 5) {
							UseHeallingPotion(pc, 15, 189);
						} else if (chance >= 5 && chance < 9) {
							createNewItem(pc, 40018, 1);
						} else if (chance >= 9) {
							int gemChance = _random.nextInt(3);
							if (gemChance == 0) {
								createNewItem(pc, 40047, 1);
							} else if (gemChance == 1) {
								createNewItem(pc, 40051, 1);
							} else if (gemChance == 2) {
								createNewItem(pc, 40055, 1);
							}
						}
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 41303) { // ���̴׺긣�ͽ�
						int chance = _random.nextInt(3);
						if (chance >= 0 && chance < 5) {
							UseHeallingPotion(pc, 15, 189);
						} else if (chance >= 5 && chance < 9) {
							createNewItem(pc, 40015, 1);
						} else if (chance >= 9) {
							int gemChance = _random.nextInt(3);
							if (gemChance == 0) {
								createNewItem(pc, 40046, 1);
							} else if (gemChance == 1) {
								createNewItem(pc, 40050, 1);
							} else if (gemChance == 2) {
								createNewItem(pc, 40054, 1);
							}
						}
						pc.getInventory().removeItem(useItem, 1);
					} else

					/*
					 * if (itemId == 60200 || itemId >= 40104 && itemId <= 40113
					 * ){ //�ؼ� �ںҰ�
					 * 
					 * if (pc.getMapId() == 4 || pc.getMapId() == 101){
					 * 
					 * pc.getInventory().removeItem(useItem, 1); } else {
					 * pc.sendPackets(new S_SystemMessage("�������� ����� �����մϴ�.")); }
					 * 
					 * } else
					 */

					if (itemId == 41304) { // ���̴�ȭ��Ʈ�ͽ�
						int chance = _random.nextInt(3);
						if (chance >= 0 && chance < 5) {
							UseHeallingPotion(pc, 15, 189);
						} else if (chance >= 5 && chance < 9) {
							createNewItem(pc, 40021, 1);
						} else if (chance >= 9) {
							int gemChance = _random.nextInt(3);
							if (gemChance == 0) {
								createNewItem(pc, 40044, 1);
							} else if (gemChance == 1) {
								createNewItem(pc, 40048, 1);
							} else if (gemChance == 2) {
								createNewItem(pc, 40052, 1);
							}
						}
						pc.getInventory().removeItem(useItem, 1);

					} else if ((itemId >= 40104 && itemId <= 40112) || (itemId >= 42029 && itemId <= 42039)
							|| (itemId >= 5000163 && itemId <= 5000171) || itemId == 60200) { // ����...����
																								// ����

						if (((L1EtcItem) useItem.getItem()).get_mapid() == pc.getMapId()) {
							// �� ������
							L1Location loc = L1Location.saburan(pc.getMap());
							if (pc.Sabutelok()) {
								pc.dx = loc.getX();
								pc.dy = loc.getY();
								pc.dm = ((L1EtcItem) useItem.getItem()).get_mapid();
								pc.dh = pc.getMoveState().getHeading();
								pc.setTelType(7);
								pc.sendPackets(new S_SabuTell(pc), true);
							}
						} else {
							if (pc.getMapId() != 1700 && pc.getMapId() != 5001) {
								if (pc.Sabutelok()) {
									pc.dx = ((L1EtcItem) useItem.getItem()).get_locx();
									pc.dy = ((L1EtcItem) useItem.getItem()).get_locy();
									pc.dm = ((L1EtcItem) useItem.getItem()).get_mapid();
									pc.dh = pc.getMoveState().getHeading();
									pc.setTelType(7);
									pc.sendPackets(new S_SabuTell(pc), true);
								}
							} else {
								pc.sendPackets(new S_SystemMessage("�� �������� �ڷ���Ʈ�� �� �� �����ϴ�."));
								return;
							}
						}

						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 40615) { // �׸����� ���� 2���� ����
						if (pc.getMapId() == 522 && (pc.getX() >= 32702 && pc.getX() <= 32707 && pc.getY() >= 32893
								&& pc.getY() <= 32898)) {
							if (pc.getMap().isEscapable()) { // ��ȯ���������ΰ��� �˻��Ѵ�
								if (pc.Sabutelok()) {
									pc.dx = ((L1EtcItem) useItem.getItem()).get_locx();
									pc.dy = ((L1EtcItem) useItem.getItem()).get_locy();
									pc.dm = ((L1EtcItem) useItem.getItem()).get_mapid();
									pc.dh = pc.getMoveState().getHeading();
									pc.setTelType(7);
									pc.sendPackets(new S_SabuTell(pc), true);
								}
							} else {
								// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
								pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
							}
						} else {
							// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}

					} else if (itemId == 437011 || itemId == 60207) { // ����, �ÿ�
																		// ������
																		// ����
						/*
						 * if (pc.getLevel() < 80) { pc.sendPackets(new
						 * S_SystemMessage("\\fY80���ϴ� �巡���� ���ָ� ���� �Ͻ� �� �����ϴ�."));
						 * return; }
						 */
						pc.sendPackets(new S_ServerMessage(1065), true); // ����
																			// ��Ʈ
						�������ǻ��(pc);
						// pc.getInventory().consumeItem(437011, 1);//�ش������ ����
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == 60055 || itemId == 60250) { // ������ ����,
																		// ������
																		// �ֹ���
						pc.sendPackets(new S_ServerMessage(1065), true); // ����
																			// ��Ʈ
						�������ǻ��(pc, 7976);// 7929
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 40616 || itemId == 40782 || itemId == 40783) { // �׸�����
																						// ����
																						// 3����
																						// ����

						if (pc.getMapId() == 523 && (pc.getX() >= 32698 && pc.getX() <= 32704 && pc.getY() >= 32892
								&& pc.getY() <= 32898)) {
							if (pc.getMap().isEscapable()) { // ��ȯ���������ΰ��� �˻��Ѵ�
								if (pc.Sabutelok()) {
									pc.dx = ((L1EtcItem) useItem.getItem()).get_locx();
									pc.dy = ((L1EtcItem) useItem.getItem()).get_locy();
									pc.dm = ((L1EtcItem) useItem.getItem()).get_mapid();
									pc.dh = pc.getMoveState().getHeading();
									pc.setTelType(7);
									pc.sendPackets(new S_SabuTell(pc), true);
								}
							} else {
								// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
								pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
							}
						} else {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}

					} else if (itemId == 40692) { // �ϼ��� ������ ����
						if (pc.getInventory().checkItem(40621)) {
							// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						} else if ((pc.getX() >= 32856 && pc.getX() <= 32858)
								&& (pc.getY() >= 32857 && pc.getY() <= 32858) && pc.getMapId() == 443) { // ��������
																											// ����
																											// ����
																											// 3��
							if (pc.Sabutelok()) {
								pc.dx = ((L1EtcItem) useItem.getItem()).get_locx();
								pc.dy = ((L1EtcItem) useItem.getItem()).get_locy();
								pc.dm = ((L1EtcItem) useItem.getItem()).get_mapid();
								pc.dh = pc.getMoveState().getHeading();
								pc.setTelType(7);
								pc.sendPackets(new S_SabuTell(pc), true);
							}
						} else {
							// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 41146) { // ��θ���� �ʴ���
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei001"), true);
					} else if (itemId == 41209) { // ���Ƿ����� �Ƿڼ�
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei002"), true);
					} else if (itemId == 41210) { // ������
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei003"), true);
					} else if (itemId == 41211) { // ���
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei004"), true);
					} else if (itemId == 41212) { // Ư�� ĵ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei005"), true);
					} else if (itemId == 41213) { // Ƽ���� �ٽ���
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei006"), true);
					} else if (itemId == 41214) { // ���� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei012"), true);
					} else if (itemId == 41215) { // ���� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei010"), true);
					} else if (itemId == 41216) { // ���� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei011"), true);
					} else if (itemId == 41222) { // ������
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei008"), true);
					} else if (itemId == 41223) { // ������ ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei007"), true);
					} else if (itemId == 41224) { // ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei009"), true);
					} else if (itemId == 41225) { // �ɽ�Ų�� ���ּ�
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei013"), true);
					} else if (itemId == 41226) { // �İ��� ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei014"), true);
					} else if (itemId == 41227) { // �˷����� �Ұ���
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei033"), true);
					} else if (itemId == 41228) { // �����ڻ��� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei034"), true);
					} else if (itemId == 41229) { // ���̸����� �Ӹ�
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei025"), true);
					} else if (itemId == 41230) { // �������� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei020"), true);
					} else if (itemId == 41231) { // ��Ƽ���� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei021"), true);
					} else if (itemId == 41233) { // �����̿��� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei019"), true);
					} else if (itemId == 41234) { // ���� ���� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei023"), true);
					} else if (itemId == 41235) { // ���ǥ
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei024"), true);
					} else if (itemId == 41236) { // ����í�� ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei026"), true);
					} else if (itemId == 41237) { // ���̸��� ������ũ�� ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei027"), true);
					} else if (itemId == 41239) { // ��Ʈ���� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei018"), true);
					} else if (itemId == 41240) { // ��ٿ��� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "ei022"), true);
					} else if (itemId == 41060) { // �볪���� ��õ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "nonames"), true);
					} else if (itemId == 41061) { // ������� ������������ ���� �δٸ���ī��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "kames"), true);
					} else if (itemId == 41062) { // ������� �������ΰ� ���� �׸�����ũ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "bakumos"), true);
					} else if (itemId == 41063) { // ������� ���������� ���� �δٸ����ī
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "bukas"), true);
					} else if (itemId == 41064) { // ������� ��������ũ ���� �׸����Ŀ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "huwoomos"), true);
					} else if (itemId == 41065) { // ������� ������������� ��Ʈ�ٳ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "noas"), true);
					} else if (itemId == 41356) { // �ķ��� �ڿ� ����Ʈ
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "rparum3"), true);
					} else if (itemId == 40701) { // ���� ������ ����
						if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 1) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "firsttmap"), true);
						} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 2) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "secondtmapa"), true);
						} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 3) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "secondtmapb"), true);
						} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 4) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "secondtmapc"), true);
						} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 5) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmapd"), true);
						} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 6) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmape"), true);
						} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 7) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmapf"), true);
						} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 8) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmapg"), true);
						} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 9) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmaph"), true);
						} else if (pc.getQuest().get_step(L1Quest.QUEST_LUKEIN1) == 10) {
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "thirdtmapi"), true);
						}
					} else if (itemId == 40663) { // �Ƶ��� ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "sonsletter"), true);
					} else if (itemId == 40630) { // �𿡰��� ���� �ϱ�
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "diegodiary"), true);
					} else if (itemId == 41340) { // �뺴���� Ƽ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tion"), true);
					} else if (itemId == 41317) { // ������ ��õ��
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "rarson"), true);
					} else if (itemId == 41318) { // ���� �޸�
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "kuen"), true);
					} else if (itemId == 41329) { // ������ ���� �Ƿڼ�
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "anirequest"), true);
					} else if (itemId == 41346) { // �κ��ʵ��� �޸� 1
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "robinscroll"), true);
					} else if (itemId == 41347) { // �κ��ʵ��� �޸� 2
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "robinscroll2"), true);
					} else if (itemId == 41348) { // �κ��ʵ��� �Ұ���
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "robinhood"), true);
					} else if (itemId == 41007) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "erisscroll"), true);
					} else if (itemId == 41009) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "erisscroll2"), true);
					} else if (itemId == 41019) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory1"), true);
					} else if (itemId == 41020) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory2"), true);
					} else if (itemId == 41021) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory3"), true);
					} else if (itemId == 41022) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory4"), true);
					} else if (itemId == 41023) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory5"), true);
					} else if (itemId == 41024) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory6"), true);
					} else if (itemId == 41025) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory7"), true);
					} else if (itemId == 41026) {
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "lashistory8"), true);
					} else if (itemId == 210087) { // �������� ù ��° ���ɼ�
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "first_p"), true);
					} else if (itemId == 210093) { // �Ƿ����� ù ��° ����
						pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "silrein1lt"), true);
					} else if (itemId == L1ItemId.TIKAL_CALENDAR) {
						if (CrockSystem.getInstance().isOpen())
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tcalendaro"), true);
						else
							pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "tcalendarc"), true);
					} else if (itemId == 41208) { // �� ���� ��ȥ
						if ((pc.getX() >= 32844 && pc.getX() <= 32845) && (pc.getY() >= 32693 && pc.getY() <= 32694)
								&& pc.getMapId() == 550) { // ���� ����:������
							if (pc.Sabutelok()) {
								pc.dx = ((L1EtcItem) useItem.getItem()).get_locx();
								pc.dy = ((L1EtcItem) useItem.getItem()).get_locy();
								pc.dm = ((L1EtcItem) useItem.getItem()).get_mapid();
								pc.dh = pc.getMoveState().getHeading();
								pc.setTelType(7);
								pc.sendPackets(new S_SabuTell(pc), true);
							}
						} else {
							// \f1 �ƹ��͵� �Ͼ�� �ʾҽ��ϴ�.
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						}
					} else if (itemId == 40700) { // �ǹ� �÷�
						pc.sendPackets(new S_Sound(10), true);
						Broadcaster.broadcastPacket(pc, new S_Sound(10), true);
						if ((pc.getX() >= 32619 && pc.getX() <= 32623) && (pc.getY() >= 33120 && pc.getY() <= 33124)
								&& pc.getMapId() == 440) { // ���� �ø������ݸ��� ������ ��ǥ
							boolean found = false;
							L1MonsterInstance mon = null;
							for (L1Object obj : L1World.getInstance().getObject()) {
								if (obj instanceof L1MonsterInstance) {
									mon = (L1MonsterInstance) obj;
									if (mon != null) {
										if (mon.getNpcTemplate().get_npcId() == 45875) {
											found = true;
											break;
										}
									}
								}
							}
							if (found) {
							} else {
								L1SpawnUtil.spawn(pc, 45875, 0, 0, false);
							}
						}
						
					} else if (itemId == 41121) {
						if (pc.getQuest().get_step(L1Quest.QUEST_SHADOWS) == L1Quest.QUEST_END
								|| pc.getInventory().checkItem(41122, 1)) {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						} else {
							createNewItem(pc, 41122, 1);
						}
					} else if (itemId == 41130) {
						if (pc.getQuest().get_step(L1Quest.QUEST_DESIRE) == L1Quest.QUEST_END
								|| pc.getInventory().checkItem(41131, 1)) {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
						} else {
							createNewItem(pc, 41131, 1);
						}

					} else if (itemId == 762676) { // ���� ��ũ
						if (pc.getMap().isEscapable() || pc.isGm()) {
							if (pc.Sabutelok()) {
								pc.dx = 32799;
								pc.dy = 32801;
								pc.dm = 6202;
								pc.dh = pc.getMoveState().getHeading();
								pc.setTelType(7);
								pc.sendPackets(new S_SabuTell(pc), true);
							}
						} else {
							pc.sendPackets(new S_ServerMessage(647), true);
						}
					} else if (itemId == 42501) { // ���� ��ũ
						if (pc.Sabutelok()) {
							pc.dx = spellsc_x;
							pc.dy = spellsc_y;
							pc.dm = pc.getMapId();
							pc.dh = pc.getMoveState().getHeading();

							pc.setTelType(12);
							pc.sendPackets(new S_SabuTell(pc), true);
						}
					} else if (itemId == 50101) { // ��ġ����
						IdentMapWand(pc, spellsc_x, spellsc_y);
					} else if (itemId == 50102) { // ��ġ���渷��
						MapFixKeyWand(pc, spellsc_x, spellsc_y);
					} else if (itemId == L1ItemId.CHANGING_PETNAME_SCROLL) {
						if (l1iteminstance1.getItem().getItemId() == 40314
								|| l1iteminstance1.getItem().getItemId() == 40316) {
							L1Pet petTemplate = PetTable.getInstance().getTemplate(l1iteminstance1.getId());
							if (petTemplate == null) {
								throw new NullPointerException();
							}
							L1Npc l1npc = NpcTable.getInstance().getTemplate(petTemplate.get_npcid());
							petTemplate.set_name(l1npc.get_name());
							PetTable.getInstance().storePet(petTemplate);
							L1ItemInstance item = pc.getInventory().getItem(l1iteminstance1.getId());
							pc.getInventory().updateItem(item);
							pc.getInventory().removeItem(useItem, 1);
							pc.sendPackets(new S_ServerMessage(1322, l1npc.get_name()), true);
							pc.sendPackets(new S_ChangeName(petTemplate.get_objid(), l1npc.get_name()), true);
							Broadcaster.broadcastPacket(pc, new S_ChangeName(petTemplate.get_objid(), l1npc.get_name()),
									true);
						} else {
							pc.sendPackets(new S_ServerMessage(1164), true);
						}
					} else if (itemId == 41260) { // ��
						for (L1Object object : L1World.getInstance().getVisibleObjects(pc, 3)) {
							if (object instanceof L1EffectInstance) {
								if (((L1NpcInstance) object).getNpcTemplate().get_npcId() == 81170) {
									pc.sendPackets(new S_ServerMessage(1162), true); // ����
																						// ������
																						// ��ں���
																						// �ֽ��ϴ�.
									return;
								}
							}
						}
						int[] loc = new int[2];
						loc = CharPosUtil.getFrontLoc(pc.getX(), pc.getY(), pc.getMoveState().getHeading());
						L1EffectSpawn.getInstance().spawnEffect(81170, 600000, loc[0], loc[1], pc.getMapId());
						pc.getInventory().removeItem(useItem, 1);
						loc = null;
					} else if (itemId == 41345) { // �꼺�� ����
						L1DamagePoison.doInfection(pc, pc, 3000, 5);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 41315) { // ����
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER_OF_EVA)) {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
							return;
						}
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_MITHRIL_POWDER)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_HOLY_MITHRIL_POWDER);
						}
						pc.getSkillEffectTimerSet().setSkillEffect(STATUS_HOLY_WATER, 900 * 1000);
						pc.sendPackets(new S_SkillSound(pc.getId(), 190), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 190), true);
						pc.sendPackets(new S_ServerMessage(1141), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 41316) { // �ż��� �̽������Ŀ��
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER_OF_EVA)) {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
							return;
						}
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_HOLY_WATER);
						}
						pc.getSkillEffectTimerSet().setSkillEffect(STATUS_HOLY_MITHRIL_POWDER, 900 * 1000);
						pc.sendPackets(new S_SkillSound(pc.getId(), 190), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 190), true);
						pc.sendPackets(new S_ServerMessage(1142), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == 41354) { // �ż��� ������ ��
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_WATER)
								|| pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HOLY_MITHRIL_POWDER)) {
							pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																							// �ƹ��͵�
																							// �Ͼ��
																							// �ʾҽ��ϴ�.
							return;
						}
						pc.getSkillEffectTimerSet().setSkillEffect(STATUS_HOLY_WATER_OF_EVA, 900 * 1000);
						pc.sendPackets(new S_SkillSound(pc.getId(), 190), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 190), true);
						pc.sendPackets(new S_ServerMessage(1140), true);
						pc.getInventory().removeItem(useItem, 1);

					} else

					if (itemId == 60233) { // ������
						// L1SkillId.����Ǳ��
						int[] allBuffSkill = { 247 };
						L1SkillUse l1skilluse = new L1SkillUse();
						for (int i = 0; i < allBuffSkill.length; i++) {
							l1skilluse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
						pc.getInventory().removeItem(useItem, 1);

					} else

					if (itemId == 500076) { // �ù��� ���� ��Ŭ����
						pc.setBuffnoch(1);
						int[] allBuffSkill = { 26, 42, 54, 48, 79, 148, // 88��
																		// ����
																		// �߰����־���
								151, 158 };
						L1SkillUse l1skilluse = new L1SkillUse();
						for (int i = 0; i < allBuffSkill.length; i++) {
							l1skilluse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
						l1skilluse = null;
						allBuffSkill = null;
						pc.getInventory().removeItem(useItem, 1);
						pc.sendPackets(new S_SystemMessage("\\fV�� �ӿ��� �߰ſ� ����� �ڱ�Ĩ�ϴ�."), true);
						pc.setBuffnoch(0);

					} else if (itemId == 500077) { // �ù��� ���� ��������ȯ����
						pc.setBuffnoch(1);
						int[] allBuffSkill = { 26, 42, 54, 48, 79, 149, // 88��
																		// ����
																		// �߰����־���
								151, 158 };
						L1SkillUse l1skilluse = new L1SkillUse();
						for (int i = 0; i < allBuffSkill.length; i++) {
							l1skilluse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
						}
						l1skilluse = null;
						allBuffSkill = null;
						pc.getInventory().removeItem(useItem, 1);
						// 1Teleport.teleport(pc, pc.getX(), pc.getY(),
						// pc.getMapId(), pc.getHeading(), false);
						pc.sendPackets(new S_SystemMessage("\\fV�� �ӿ��� �˼����� ����� �ڱ�Ĩ�ϴ�."), true);
						pc.setBuffnoch(0);
					} else if (itemId == L1ItemId.CHANGING_SEX_POTION) { // ����ȯ
																			// ����
						if (pc.get_sex() == 0) {
							pc.set_sex(1);
							pc.setClassId(FEMALE_LIST[pc.getType()]);
						} else {
							pc.set_sex(0);
							pc.setClassId(MALE_LIST[pc.getType()]);
						}
						pc.getGfxId().setTempCharGfx(pc.getClassId());
						pc.save();
						pc.sendPackets(new S_ChangeShape(pc.getId(), pc.getClassId()), true);
						Broadcaster.broadcastPacket(pc, new S_ChangeShape(pc.getId(), pc.getClassId()), true);
						pc.sendPackets(new S_CharVisualUpdate(pc), true);
						Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc), true);
						pc.getInventory().removeItem(useItem, 1);
						/**************************
						 * ��Ÿ�� ������ New System
						 *****************************/
					} else if (itemId == L1ItemId.DRAGON_KEY) {
						int pc_castleId = L1CastleLocation.getCastleIdByArea(pc);
						if (pc.getMapId() == 53 || pc.getMapId() == 54 || pc.getMapId() == 56 || pc.getMapId() == 55
								|| pc_castleId == 1 || pc_castleId == 2 || pc_castleId == 3 || pc_castleId == 4
								|| pc_castleId == 5 || pc_castleId == 6 || pc_castleId == 7 || pc_castleId == 8)
							if (useItem.getEndTime().getTime() < System.currentTimeMillis()) {
								pc.getInventory().removeItem(useItem);
								pc.sendPackets(new S_SystemMessage("��� �ð��� ���� ���� �մϴ�."), true);// ������
																								// ���׸�
																								// ���
							} else {
								pc.sendPackets(new S_SystemMessage("���⿡���� ����Ҽ� �����ϴ�."), true);

								return;
							}

						pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONMENU, useItem), true);
						// AntarasRaidSystem.getInstance().startRaid(pc);

					} else if (itemId == L1ItemId.DRAGON_EMEBOX) {
						int[] DRAGONSCALE = new int[] { 40393, 40394, 40395, 40396 };
						int bonus = _random.nextInt(100) + 1;
						int rullet = _random.nextInt(100) + 1;
						L1ItemInstance bonusitem = null;
						pc.getInventory().storeItem(L1ItemId.DRAGON_EME, 1);
						pc.sendPackets(new S_ServerMessage(403, "$11518"), true);
						pc.getInventory().removeItem(useItem, 1);
						if (bonus <= 3) {
							bonusitem = pc.getInventory().storeItem(DRAGONSCALE[rullet % DRAGONSCALE.length], 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else if (bonus >= 4 && bonus <= 7) {
							bonusitem = pc.getInventory().storeItem(L1ItemId.DRAGON_PEARL, 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else if (bonus >= 8 && bonus <= 14) {
							bonusitem = pc.getInventory().storeItem(L1ItemId.DRAGON_SAPHIRE, 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else if (bonus >= 15 && bonus <= 24) {
							bonusitem = pc.getInventory().storeItem(L1ItemId.DRAGON_RUBY, 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else {
						}
						DRAGONSCALE = null;
					} else if (itemId == 437009) {
						int[] DRAGONSCALE = new int[] { 40393, 40394, 40395, 40396 };
						int bonus = _random.nextInt(100) + 1;
						int rullet = _random.nextInt(100) + 1;
						L1ItemInstance bonusitem = null;
						pc.getInventory().storeItem(437010, 1);
						pc.sendPackets(new S_ServerMessage(403, "$7969"), true);
						pc.getInventory().removeItem(useItem, 1);
						if (bonus <= 1) {
							bonusitem = pc.getInventory().storeItem(DRAGONSCALE[rullet % DRAGONSCALE.length], 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else if (bonus >= 4 && bonus <= 7) {
							bonusitem = pc.getInventory().storeItem(L1ItemId.DRAGON_PEARL, 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else if (bonus >= 8 && bonus <= 14) {
							bonusitem = pc.getInventory().storeItem(L1ItemId.DRAGON_SAPHIRE, 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else if (bonus >= 15 && bonus <= 24) {
							bonusitem = pc.getInventory().storeItem(L1ItemId.DRAGON_RUBY, 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else {
						}
						DRAGONSCALE = null;
					} else if (itemId == 7249) {
						��������(pc, useItem);
					} else if (itemId == 7250) {
						��������5(pc, useItem);
					} else if (itemId == 1437009) {
						int[] DRAGONSCALE = new int[] { 40393, 40394, 40395, 40396 };
						int bonus = _random.nextInt(100) + 1;
						int rullet = _random.nextInt(100) + 1;
						L1ItemInstance bonusitem = null;
						pc.getInventory().storeItem(1437010, 1);
						pc.sendPackets(new S_ServerMessage(403, "�ູ���� $7969"), true);
						pc.getInventory().removeItem(useItem, 1);
						if (bonus <= 3) {
							bonusitem = pc.getInventory().storeItem(DRAGONSCALE[rullet % DRAGONSCALE.length], 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else if (bonus >= 4 && bonus <= 7) {
							bonusitem = pc.getInventory().storeItem(L1ItemId.DRAGON_PEARL, 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else if (bonus >= 8 && bonus <= 14) {
							bonusitem = pc.getInventory().storeItem(L1ItemId.DRAGON_SAPHIRE, 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else if (bonus >= 15 && bonus <= 24) {
							bonusitem = pc.getInventory().storeItem(L1ItemId.DRAGON_RUBY, 1);
							pc.sendPackets(new S_ServerMessage(403, bonusitem.getItem().getNameId()), true);
						} else {
						}
						DRAGONSCALE = null;

					} else if (itemId == 1437010 || itemId == 437010 || itemId == 437013 || itemId == 437012
							|| itemId == 5000067 || itemId == 60291) {
						�巡�ﺸ��(pc, useItem);
					} else if (itemId == 60255 || itemId == 60293) {// �ڼ���
						�ڼ���(pc, useItem);

					} else if (itemId == 5559 || itemId == 5560) {// Ž����
						// System.out.println("objid "+use_objid);
						if (use_objid == 0) {
							return;
						}
						int day = 0;
						if (itemId == 5559)
							day = 7;
						if (itemId == 5560)
							day = 30;
						Ž����(pc, use_objid, useItem, day);
					} else if (itemId == 7241) {// ������
						������(pc, useItem);
					} else if (itemId == L1ItemId.DRAGON_DIAMOND) {
						int temphasad = pc.getAinHasad() + 1000000;
						if (temphasad > 7010000) {
							pc.sendPackets(new S_SystemMessage("�����ϻ���� �ູ : �����ִ� �ູ������ ���� ��� �� �� �����ϴ�."));
							return;
						}
						pc.calAinHasad(1000000);
						pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc), true);
						pc.getInventory().removeItem(useItem, 1);
					} else if (itemId == L1ItemId.DRAGON_SAPHIRE) {
						int temphasad = pc.getAinHasad() + 500000;
						if (temphasad > 7010000) {
							pc.sendPackets(new S_SystemMessage("�����ϻ���� �ູ : �����ִ� �ູ������ ���� ��� �� �� �����ϴ�."));
							return;
						}

						pc.calAinHasad(500000);
						pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc), true);
						pc.getInventory().removeItem(useItem, 1);

					} else if (itemId == L1ItemId.DRAGON_RUBY) {
						int temphasad = pc.getAinHasad() + 300000;
						if (temphasad > 7010000) {
							pc.sendPackets(new S_SystemMessage("�����ϻ���� �ູ : �����ִ� �ູ������ ���� ��� �� �� �����ϴ�."));
							return;
						}

						pc.calAinHasad(300000);
						pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc), true);
						pc.getInventory().removeItem(useItem, 1);
						/** ���� �ý��� by�̷����� **/
						// �߸� �ý��� �� �̷�����ΰ͸�...
					} else if (itemId == 6000034) { // Ǯ����������
						Ǯ������(pc, useItem);
					} else if (itemId == 5000121) {
						if (pc.getInventory().checkItem(5000121, 1)) {
							pc.getInventory().consumeItem(5000121, 1);
							int[] mobArray = { 450001798 };
							int rnd = _random.nextInt(mobArray.length);
							L1SpawnUtil.spawn(pc, mobArray[rnd], 0, 300000, true);
							mobArray = null;
						}
					} else if (itemId == L1ItemId.METIS_ONE) {
						int bonus = _random.nextInt(100) + 1;
						pc.getInventory().storeItem(L1ItemId.METIS_THREE, 1);
						pc.sendPackets(new S_ServerMessage(403, "��Ƽ���� ����ũ ����"), true);
						pc.getInventory().removeItem(useItem, 1);
						if (bonus <= 3) {
							pc.getInventory().storeItem(L1ItemId.weapon_0, 1);
							pc.sendPackets(new S_ServerMessage(403, "���� ���� �ֹ���"), true);
						} else if (bonus >= 4 && bonus <= 7) {
							pc.getInventory().storeItem(L1ItemId.weapon_1, 1);
							pc.sendPackets(new S_ServerMessage(403, "���� ���� �ֹ���"), true);
						} else if (bonus >= 8 && bonus <= 12) {
							pc.getInventory().storeItem(L1ItemId.armor_0, 1);
							pc.sendPackets(new S_ServerMessage(403, "���� ���� �ֹ���"), true);
						} else if (bonus >= 13 && bonus <= 17) {
							pc.getInventory().storeItem(L1ItemId.armor_1, 1);
							pc.sendPackets(new S_ServerMessage(403, "���� ���� �ֹ���"), true);
						} else if (bonus >= 18 && bonus <= 22) {
							pc.getInventory().storeItem(L1ItemId.potion_0, 5);
							pc.sendPackets(new S_ServerMessage(403, "���׵�� ����(5)"), true);
						} else if (bonus >= 23 && bonus <= 27) {
							pc.getInventory().storeItem(L1ItemId.potion_1, 5);
							pc.sendPackets(new S_ServerMessage(403, "����� ����(5)"), true);
						} else if (bonus >= 28 && bonus <= 32) {
							pc.getInventory().storeItem(L1ItemId.potion_2, 5);
							pc.sendPackets(new S_ServerMessage(403, "���� ����(5)"), true);
						} else if (bonus >= 33 && bonus <= 37) {
							pc.getInventory().storeItem(L1ItemId.potion_3, 5);
							pc.sendPackets(new S_ServerMessage(403, "�Ǹ�����(5)"), true);
						}
						/** ���� �ý��� by�̷����� **/
						// �߸� �ý��� �� �̷�����ΰ͸�...
					} else if (itemId == L1ItemId.FORTUNE_COOKIE || (itemId >= 437027 && itemId <= 437034)) {
						int count = _random.nextInt(8) + 1;
						pc.getInventory().storeItem(41159, count);
						pc.sendPackets(new S_ServerMessage(403, "�Ƚ��� ���� (" + count + ")"), true);
						pc.getInventory().removeItem(useItem, 1);
					} else {
						int locX = ((L1EtcItem) useItem.getItem()).get_locx();
						int locY = ((L1EtcItem) useItem.getItem()).get_locy();
						short mapId = ((L1EtcItem) useItem.getItem()).get_mapid();
						if (locX != 0 && locY != 0) {
							if (pc.getMap().isEscapable() || pc.isGm()) {
								if (pc.Sabutelok()) {
									pc.dx = locX;
									pc.dy = locY;
									pc.dm = mapId;
									pc.dh = pc.getMoveState().getHeading();
									pc.setTelType(7);
									pc.sendPackets(new S_SabuTell(pc), true);
									pc.getInventory().removeItem(useItem, 1);
								}
							} else {
								pc.sendPackets(new S_ServerMessage(647), true);
							}

							pc.cancelAbsoluteBarrier();
						} else {
							if (useItem.getCount() < 1) {
								pc.sendPackets(new S_ServerMessage(329, useItem.getLogName()), true);
							} else {
								pc.sendPackets(new S_ServerMessage(74, useItem.getLogName()), true);
							}
						}
					}
				}

				L1ItemDelay.onItemUse(pc, useItem); // ������ ���� ����
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	private boolean ����(L1PcInstance pc) {
		if (pc.getMapId() == 4
				&& ((pc.getX() >= 33331 && pc.getX() <= 33341 && pc.getY() >= 32430 && pc.getY() <= 32441)
						|| (pc.getX() >= 33258 && pc.getX() <= 33267 && pc.getY() >= 32396 && pc.getY() <= 32407)
						|| (pc.getX() >= 33388 && pc.getX() <= 33397 && pc.getY() >= 32339 && pc.getY() <= 32350)
						|| (pc.getX() >= 33464 && pc.getX() <= 33531 && pc.getY() >= 33168 && pc.getY() <= 33248)
						|| (pc.getX() >= 33443 && pc.getX() <= 33483 && pc.getY() >= 32315 && pc.getY() <= 32357))) {
			return false;
		}

		if (pc.getMapId() == 4
				&& ((pc.getX() >= 33328 && pc.getX() <= 33344 && pc.getY() >= 32427 && pc.getY() <= 32444)
						|| (pc.getX() >= 33255 && pc.getX() <= 33272 && pc.getY() >= 32393 && pc.getY() <= 32412) ||

						(pc.getX() >= 34194 && pc.getX() <= 34305 && pc.getY() >= 33324 && pc.getY() <= 33535) || // Ȳȥ�ǻ��
						(pc.getX() >= 33450 && pc.getX() <= 33470 && pc.getY() >= 32328 && pc.getY() <= 32344) || // �Ƶ����ѱ���

						(pc.getX() >= 33385 && pc.getX() <= 33400 && pc.getY() >= 32336 && pc.getY() <= 32353)
						|| (pc.getX() >= 33461 && pc.getX() <= 33534 && pc.getY() >= 33165 && pc.getY() <= 33253))) {
			return false;
		}

		return true;
	}

	private void Ǯ������(L1PcInstance pc, L1ItemInstance useitem) {
		// ������ں��� ����
		pc.setBuffnoch(1);
		int[] allBuffSkill = { CONCENTRATION, PATIENCE, INSIGHT, BLESS_WEAPON, PHYSICAL_ENCHANT_DEX,
				PHYSICAL_ENCHANT_STR, ADVANCE_SPIRIT, EARTH_SKIN, WIND_SHOT, FIRE_WEAPON, SHINING_AURA, BRAVE_AURA,
				IRON_SKIN, AQUA_PROTECTER, EXOTIC_VITALIZE };

		L1SkillUse l1skilluse = new L1SkillUse();

		for (int i = 0; i < allBuffSkill.length; i++) {
			l1skilluse.handleCommands(pc, allBuffSkill[i], pc.getId(), pc.getX(), pc.getY(), null, 0,
					L1SkillUse.TYPE_GMBUFF); // ����
												// ���
												// ���ֱ�
		}
		l1skilluse = null;
		allBuffSkill = null;
		useCashScroll(pc, L1ItemId.INCRESE_ATTACK_SCROLL, false);
		L1SkillUse su = new L1SkillUse();
		su.handleCommands(pc, STATUS_COMA_5, pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
		su = null;
		L1SkillUse su2 = new L1SkillUse();
		su2.handleCommands(pc, L1SkillId.FEATHER_BUFF_A, pc.getId(), pc.getX(), pc.getY(), null, 0,
				L1SkillUse.TYPE_SPELLSC);
		su2 = null;
		pc.getInventory().consumeItem(6000034, 1);
		pc.sendPackets(new S_SkillSound(pc.getId(), 4856), true);
		pc.sendPackets(new S_SystemMessage(useitem.getName() + "�� ���� �Ͽ����ϴ�!"), true);
		pc.setBuffnoch(0);

	}

	private static final int[] �������ɻ��ڸ���Ʈ = { 60217, 437017, 437002, 437003, 437004, 560025, 560027 };

	private static final int[] �׷����������ڸ���Ʈ = { 437002, 437003, 437004, 560025, 560027 };

	private static final int[] ����ø��� = { 60187, 60188, 60189 };
	private static final int[] �����ֽø��� = { 20108, 20119, 20130, 20153 };

	private static final int[] �������ø��� = { 40033, 40034, 40035, 40036, 40037, 40038 };

	private void �������ɻ���(L1PcInstance pc) {

		pc.getInventory().storeItem(60518, 1);
		pc.sendPackets(new S_ServerMessage(403, "���� ���� 1ȸ ����"), true);

		L1ItemInstance item = null;
		int count = 1;
		int rnd = _random.nextInt(1000);

		if (rnd == 0) {
			item = pc.getInventory().storeItem(40394 + _random.nextInt(3), 1); // ����
		} else if (rnd < 20) {
			item = pc.getInventory().storeItem(60217, 1); // �ڸ�
		} else if (rnd < 120) {
			int itemid = �������ɻ��ڸ���Ʈ[_random.nextInt(�������ɻ��ڸ���Ʈ.length)];
			item = pc.getInventory().storeItem(itemid, count);
		}
		if (item != null) {
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
		}
	}

	private void �ʺ�����(L1PcInstance pc) {
		if (pc.getInventory().checkItem(747012, 1)) { // üũ �Ǵ� �����۰� ����
			pc.getInventory().consumeItem(747012, 1); // �����Ǵ� �����۰� ����
			if (pc.isKnight()) {
				createNewItem2(pc, 412001, 1, 9); // ���ž �����ֹ���
				createNewItem2(pc, 9, 1, 9); // ���ž �����ֹ���

				createNewItem2(pc, 20011, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 120056, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20085, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20200, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20178, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20119, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 420003, 1, 0); // ���ž �����ֹ���

				createNewItem2(pc, 21027, 1, 0); // ���ž �����ֹ���
				createNewItem2(pc, 20422, 1, 0); // ���ž �����ֹ���
				createNewItem2(pc, 20320, 1, 0); // ���ž �����ֹ���

				createNewItem2(pc, 40014, 10, 0); // ����ǹ���
				createNewItem2(pc, 525111, 1, 5); // ����
				createNewItem2(pc, 525111, 1, 5); // ����
				pc.sendPackets(new S_SystemMessage("������� �������� ���� �Ǿ����ϴ�."), true);
			}
			if (pc.isDragonknight()) {
				createNewItem2(pc, 57, 1, 9); // ���ž �����ֹ���

				createNewItem2(pc, 20011, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 120056, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20085, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20200, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20178, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 21060, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 420012, 1, 7); // ���ž �����ֹ���

				createNewItem2(pc, 21027, 1, 0); // ���ž �����ֹ���
				createNewItem2(pc, 20422, 1, 0); // ���ž �����ֹ���
				createNewItem2(pc, 20317, 1, 0); // ���ž �����ֹ���

				createNewItem2(pc, 525111, 1, 5); // ����
				createNewItem2(pc, 525111, 1, 5); // ����
				pc.sendPackets(new S_SystemMessage("�������� �������� ���� �Ǿ����ϴ�."), true);
			}
			if (pc.isCrown()) {
				createNewItem2(pc, 51, 1, 9); // ���ž �����ֹ���

				createNewItem2(pc, 20011, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 120056, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20085, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20200, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 20178, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 21060, 1, 7); // ���ž �����ֹ���
				createNewItem2(pc, 420012, 1, 7); // ���ž �����ֹ���

				createNewItem2(pc, 21027, 1, 0); // ���ž �����ֹ���
				createNewItem2(pc, 20422, 1, 0); // ���ž �����ֹ���
				createNewItem2(pc, 20317, 1, 0); // ���ž �����ֹ���

				createNewItem2(pc, 40031, 10, 0); // �Ǹ��� ��

				createNewItem2(pc, 525111, 1, 5); // ����
				createNewItem2(pc, 525111, 1, 5); // ����
				pc.sendPackets(new S_SystemMessage("�������� �������� ���� �Ǿ����ϴ�."), true);
			}
			if (pc.isWizard()) {
				createNewItem2(pc, 119, 1, 0); // ���ž �����ֹ���

				createNewItem2(pc, 20018, 1, 7); // ����
				createNewItem2(pc, 20025, 1, 7); // ����
				createNewItem2(pc, 20107, 1, 7); // Ƽ
				createNewItem2(pc, 20218, 1, 7); // ����
				createNewItem2(pc, 20085, 1, 7); // �ҷ�������
				createNewItem2(pc, 120187, 1, 7); // ȥ��
				createNewItem2(pc, 22009, 1, 7); // ����
				createNewItem2(pc, 20055, 1, 7); // ����

				createNewItem2(pc, 21027, 1, 0); // �뿹
				createNewItem2(pc, 20257, 1, 0); // �����
				createNewItem2(pc, 20317, 1, 0); // ����
				createNewItem2(pc, 20253, 1, 0); // ����
				createNewItem2(pc, 40219, 1, 0); // ����

				createNewItem2(pc, 420008, 1, 0); // ����123
				createNewItem2(pc, 420008, 1, 0); // ����123
				pc.sendPackets(new S_SystemMessage("���������� �������� ���� �Ǿ����ϴ�."), true);
			}
			if (pc.isIllusionist()) {
				createNewItem2(pc, 410003, 1, 9); // ���ž �����ֹ���

				createNewItem2(pc, 20011, 1, 7); // ����
				createNewItem2(pc, 120056, 1, 7); // ����
				createNewItem2(pc, 20085, 1, 7); // Ƽ
				createNewItem2(pc, 20200, 1, 7); // ����
				createNewItem2(pc, 20107, 1, 7); // �ҷ�������
				createNewItem2(pc, 22009, 1, 7); // ����
				createNewItem2(pc, 120187, 1, 7); // ����

				createNewItem2(pc, 21027, 1, 0); // �뿹
				createNewItem2(pc, 20257, 1, 0); // �����
				createNewItem2(pc, 20317, 1, 0); // ����
				createNewItem2(pc, 20253, 1, 0); // ����

				createNewItem2(pc, 430006, 10, 0); // ���׵�󿭸�

				createNewItem2(pc, 420008, 1, 0); // ����123
				createNewItem2(pc, 420008, 1, 0); // ����123
				pc.sendPackets(new S_SystemMessage("ȯ�������� �������� ���� �Ǿ����ϴ�."), true);
			}
			if (pc.isElf()) {
				createNewItem2(pc, 190, 1, 9); // ���ž �����ֹ���

				createNewItem2(pc, 20011, 1, 7); // ����
				createNewItem2(pc, 120056, 1, 7); // ����
				createNewItem2(pc, 20085, 1, 7); // Ƽ
				createNewItem2(pc, 20200, 1, 7); // ����
				createNewItem2(pc, 21060, 1, 7); // �ҷ�������
				createNewItem2(pc, 20190, 1, 0); // ȥ��
				createNewItem2(pc, 420000, 1, 0); // ����

				createNewItem2(pc, 21027, 1, 0); // �뿹
				createNewItem2(pc, 20422, 1, 0); // �����
				createNewItem2(pc, 20317, 1, 0); // ����

				createNewItem2(pc, 40744, 1000, 0); // ��ȭ��
				createNewItem2(pc, 40068, 10, 0); // �������
				createNewItem2(pc, 40114, 10, 0); // �佣 ��ȯ�ֹ���

				createNewItem2(pc, 525111, 1, 5); // ����
				createNewItem2(pc, 525111, 1, 5); // ����
				pc.sendPackets(new S_SystemMessage("�������� �������� ���� �Ǿ����ϴ�."), true);
			}
			if (pc.isDarkelf()) {
				createNewItem2(pc, 84, 1, 8); // ���ž �����ֹ���

				createNewItem2(pc, 20011, 1, 7); // ����
				createNewItem2(pc, 120056, 1, 7); // ����
				createNewItem2(pc, 20085, 1, 7); // Ƽ
				createNewItem2(pc, 20200, 1, 7); // ����
				createNewItem2(pc, 21060, 1, 7); // �ҷ�������
				createNewItem2(pc, 20178, 1, 7); // ȥ��
				createNewItem2(pc, 420003, 1, 0); // ����

				createNewItem2(pc, 21027, 1, 0); // �뿹
				createNewItem2(pc, 20422, 1, 0); // �����
				createNewItem2(pc, 20317, 1, 0); // ����

				createNewItem2(pc, 525111, 1, 5); // ����
				createNewItem2(pc, 525111, 1, 5); // ����
				pc.sendPackets(new S_SystemMessage("��ũ�������� �������� ���� �Ǿ����ϴ�."), true);

			}

			createNewItem2(pc, 40096, 20, 0); // ���ž �����ֹ���
			createNewItem2(pc, 40098, 20, 0); // ���ž Ȯ���ֹ���
			createNewItem2(pc, 40099, 100, 0); // ���ž �����̵��ֹ���
			createNewItem2(pc, 40081, 30, 0); // ���������ȯ�ֹ���
			createNewItem2(pc, 40095, 30, 0); // ���ž ��ȯ�ֹ���

			createNewItem2(pc, 762676, 1, 0); // ���۸����̵���

			createNewItem2(pc, 435006, 3, 0); // õ���ǹ���
			createNewItem2(pc, 40029, 100, 0); // ���ž ü��ȸ����
			createNewItem2(pc, 40030, 5, 0); // ���ž �ӵ���󹰾�
			createNewItem2(pc, 40017, 5, 0); // �ص���
			createNewItem2(pc, 41246, 1000, 0); // ����ü
			createNewItem2(pc, 41159, 50, 0); // �ź��� ���� ����
			createNewItem2(pc, 41249, 1, 0); // ����
			createNewItem2(pc, 40308, 1000000000, 0); // �Ƶ���

		}
	}

	private void �������ָԻ���(L1PcInstance pc) {
		L1ItemInstance item = null;
		int count = 1;
		int rnd = _random.nextInt(100000);

		if (rnd < 1) {// 0.001 9�����¸������尩
			createNewItem2(pc, 7246, count, 9);
		} else if (rnd < 2) {// 0.001 8�����¸������尩
			createNewItem2(pc, 7246, count, 8);
		} else if (rnd < 3) {// 0.001 7�����¸������尩
			createNewItem2(pc, 7246, count, 7);
		} else if (rnd < 13) {// 0.01 6�����¸������尩
			createNewItem2(pc, 7246, count, 6);
		} else if (rnd < 23) {// 0.01 5�����¸������尩
			createNewItem2(pc, 7246, count, 5);

		} else if (rnd < 24) {// 0.001 9�ݺ�����
			createNewItem2(pc, 20049, count, 9);
		} else if (rnd < 25) {// 0.001 8�ݺ�����
			createNewItem2(pc, 20049, count, 8);
		} else if (rnd < 26) {// 0.001 7�ݺ�����
			createNewItem2(pc, 20049, count, 7);
		} else if (rnd < 36) {// 0.001 6�ݺ�����
			createNewItem2(pc, 20049, count, 6);
		} else if (rnd < 46) {// 20049 5�ݺ�����
			createNewItem2(pc, 20049, count, 5);

		} else if (rnd < 47) {// 0.001 9��������
			createNewItem2(pc, 20050, count, 9);
		} else if (rnd < 48) {// 0.001 8��������
			createNewItem2(pc, 20050, count, 8);
		} else if (rnd < 49) {// 0.001 7��������
			createNewItem2(pc, 20050, count, 7);
		} else if (rnd < 59) {// 0.01 6��������
			createNewItem2(pc, 20050, count, 6);
		} else if (rnd < 69) {// 0.015��������
			createNewItem2(pc, 20050, count, 5);

		} else if (rnd < 70) {// 0.001 9Ÿ�󽺺���
			createNewItem2(pc, 120194, count, 9);
		} else if (rnd < 71) {// 0.001 8Ÿ�󽺺���
			createNewItem2(pc, 120194, count, 8);
		} else if (rnd < 72) {// 0.001 7Ÿ�󽺺���
			createNewItem2(pc, 120194, count, 7);
		} else if (rnd < 82) {// 0.01 6Ÿ�󽺺���
			createNewItem2(pc, 120194, count, 6);
		} else if (rnd < 92) {// 0.01 5Ÿ�󽺺���
			createNewItem2(pc, 120194, count, 5);

		} else if (rnd < 93) {// 0.001 9Ÿ���尩
			createNewItem2(pc, 120187, count, 9);
		} else if (rnd < 94) {// 0.001 8Ÿ���尩
			createNewItem2(pc, 120187, count, 8);
		} else if (rnd < 95) {// 0.001 7Ÿ���尩
			createNewItem2(pc, 120187, count, 7);
		} else if (rnd < 105) {// 0.01 6Ÿ���尩
			createNewItem2(pc, 120187, count, 6);
		} else if (rnd < 115) {// 0.01 5Ÿ���尩
			createNewItem2(pc, 120187, count, 5);

		} else if (rnd < 116) {// 0.001 9Ÿ���Ǻ���
			createNewItem2(pc, 20216, count, 9);
		} else if (rnd < 117) {// 0.001 8Ÿ���Ǻ���
			createNewItem2(pc, 20216, count, 8);
		} else if (rnd < 118) {// 0.001 7Ÿ���Ǻ���
			createNewItem2(pc, 20216, count, 7);
		} else if (rnd < 128) {// 0.01 6Ÿ���Ǻ���
			createNewItem2(pc, 20216, count, 6);
		} else if (rnd < 138) {// 0.01 5Ÿ���Ǻ���
			createNewItem2(pc, 20216, count, 5);

		} else if (rnd < 139) {// 0.001 9Ÿ�����尩
			createNewItem2(pc, 20186, count, 9);
		} else if (rnd < 140) {// 0.001 8Ÿ�����尩
			createNewItem2(pc, 20186, count, 8);
		} else if (rnd < 141) {// 0.001 7Ÿ�����尩
			createNewItem2(pc, 20186, count, 7);
		} else if (rnd < 151) {// 0.01 6Ÿ�����尩
			createNewItem2(pc, 20186, count, 6);
		} else if (rnd < 161) {// 0.01 5Ÿ�����尩
			createNewItem2(pc, 20186, count, 5);

		} else if (rnd < 162) {// 0.001 5��ȣ�ǰ���
			createNewItem2(pc, 21096, count, 5);
		} else if (rnd < 163) {// 0.001 4��ȣ�ǰ���
			createNewItem2(pc, 21096, count, 4);
		} else if (rnd < 164) {// 0.001 3��ȣ�ǰ���
			createNewItem2(pc, 21096, count, 3);
		} else if (rnd < 174) {// 0.01 2��ȣ�ǰ���
			createNewItem2(pc, 21096, count, 2);
		} else if (rnd < 184) {// 0.01 1��ȣ�ǰ���
			createNewItem2(pc, 21096, count, 1);

		} else if (rnd < 185) {// 0.001 5������� ����
			createNewItem2(pc, 420000, count, 5);
		} else if (rnd < 186) {// 0.001 4��� ����� ����
			createNewItem2(pc, 420000, count, 4);
		} else if (rnd < 187) {// 0.001 3��� ����� ����
			createNewItem2(pc, 420000, count, 3);
		} else if (rnd < 197) {// 0.01 2��� ����� ����
			createNewItem2(pc, 420000, count, 2);
		} else if (rnd < 207) {// 0.01 1��� ����� ����
			createNewItem2(pc, 420000, count, 1);

		} else if (rnd < 208) {// 0.001 5��� ������ ����
			createNewItem2(pc, 420003, count, 5);
		} else if (rnd < 209) {// 0.001 4��� ������ ����
			createNewItem2(pc, 420003, count, 4);
		} else if (rnd < 210) {// 0.001 3��� ������ ����
			createNewItem2(pc, 420003, count, 3);
		} else if (rnd < 220) {// 0.01 2��� ������ ����
			createNewItem2(pc, 420003, count, 2);
		} else if (rnd < 230) {// 0.01 1��� ������ ����
			createNewItem2(pc, 420003, count, 1);

		} else if (rnd < 231) {// 0.001 5��� ������ ����
			createNewItem2(pc, 20190, count, 5);
		} else if (rnd < 232) {// 0.001 4��� ������ ����
			createNewItem2(pc, 20190, count, 4);
		} else if (rnd < 233) {// 0.001 3��� ������ ����
			createNewItem2(pc, 20190, count, 3);
		} else if (rnd < 243) {// 0.01 2��� ������ ����
			createNewItem2(pc, 20190, count, 2);
		} else if (rnd < 253) {// 0.01 1��� ������ ����
			createNewItem2(pc, 20190, count, 1);

		} else if (rnd < 303) {// 0.05 �����Ǳ��
			item = pc.getInventory().storeItem(7337, 1);
		} else if (rnd < 353) {// 0.05 ȭ���Ǳ��
			item = pc.getInventory().storeItem(6022, 1);
		} else if (rnd < 403) {// 0.05 ������ ���� ���� �ֹ���
			item = pc.getInventory().storeItem(60510, 1);
		} else if (rnd < 453) {// 0.05 ������ ���� ����
			item = pc.getInventory().storeItem(60499, 1);
		} else if (rnd < 503) {// 0.05 ������ �ø���
			int itemid = �������ø���[_random.nextInt(�������ø���.length)];
			item = pc.getInventory().storeItem(itemid, count);
		} else if (rnd < 10503) {// 10% ���
			item = pc.getInventory().storeItem(437010, 1);
		} else if (rnd < 20503) {// 10% ����
			item = pc.getInventory().storeItem(437011, 1);
		} else {
			item = pc.getInventory().storeItem(60325, 1);
			// int itemid = �����ָӴ�[_random.nextInt(�����ָӴ�.length)];
			// item = pc.getInventory().storeItem(itemid, count);
		}

		if (item != null)
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
	}

	private void �ٷ��Ǽ�������(L1PcInstance pc) {
		L1ItemInstance item = null;
		int rnd = _random.nextInt(10000);
		if (rnd < 10) {// 0.1% ��Ÿ���Ǽ���
			item = pc.getInventory().storeItem(40346, 1);

		} else if (rnd < 11) {// 0.01%��Ǫ������ ����
			item = pc.getInventory().storeItem(40362, 1);

		} else if (rnd < 12) {// 0.01%���������Ǽ���
			item = pc.getInventory().storeItem(40370, 1);

		} else if (rnd < 2212) {// 22%ȭ���Ǳ��
			item = pc.getInventory().storeItem(6022, 1);

		} else if (rnd < 4412) {// 22%�����Ǳ��
			item = pc.getInventory().storeItem(7337, 1);

		} else if (rnd < 6512) {// 21%����Ž����
			item = pc.getInventory().storeItem(60499, 1);

		} else if (rnd < 8512) {// 20%����
			item = pc.getInventory().storeItem(1437010, 1);

		} else if (rnd < 8612) {// 1% ���εȺ���1��
			item = pc.getInventory().storeItem(60201, 1);

		} else if (rnd < 8712) {// 1% ���εȺ���2��
			item = pc.getInventory().storeItem(40280, 1);

		} else if (rnd < 8812) {// 1% ���εȺ���3��
			item = pc.getInventory().storeItem(40281, 1);

		} else if (rnd < 8912) {// 1% ���εȺ���4��
			item = pc.getInventory().storeItem(40282, 1);

		} else if (rnd < 9012) {// 1% ���εȺ���5��
			item = pc.getInventory().storeItem(40283, 1);

		} else if (rnd < 9112) {// 1% ���εȺ���6��
			item = pc.getInventory().storeItem(40284, 1);

		} else if (rnd < 9212) {// 1% ���εȺ���7��
			item = pc.getInventory().storeItem(40285, 1);

		} else if (rnd < 9312) {// 1% ���εȺ���8��
			item = pc.getInventory().storeItem(40286, 1);

		} else if (rnd < 9412) {// 1% ���εȺ���9��
			item = pc.getInventory().storeItem(40287, 1);

		} else if (rnd < 9512) {// 1% ���εȺ���10��
			item = pc.getInventory().storeItem(40288, 1);

		} else {// 5% �ຯ�� �ֹ���
			item = pc.getInventory().storeItem(50022, 1); // ����
		}

		if (item != null)
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
	}

	private void ��ȣ��(L1PcInstance pc) {
		L1ItemInstance item = null;
		int rnd = _random.nextInt(1020);
		if (rnd < 10) {// 1% Ÿ��ź��
			item = pc.getInventory().storeItem(7304, 1);

		} else if (rnd < 20) {// 1%Ÿ��ź��
			item = pc.getInventory().storeItem(7306, 1);

		} else if (rnd < 91) {// 7%�Ⱓƽ
			item = pc.getInventory().storeItem(7309, 1);

			/*
			 * }else if(rnd < 91){//0.1%������� item =
			 * pc.getInventory().storeItem(7310, 1);
			 */
		} else if (rnd < 94) {// 0.3%���ν�������
			item = pc.getInventory().storeItem(291, 1);

		} else if (rnd < 95) {// 0.1%Ŀ��
			item = pc.getInventory().storeItem(54, 1);

		} else if (rnd < 100) {// 0.5 �Ǹ��� ���
			item = pc.getInventory().storeItem(293, 1); // �Ǹ������

		} else if (rnd < 103) {// 0.3 ����Ʈ�ߵ��Ǿ�հ�
			item = pc.getInventory().storeItem(59, 1); // ����

		} else if (rnd < 108) {// 0.5 ����ŵ���
			item = pc.getInventory().storeItem(7227, 1);

		} else if (rnd < 178) {// 7%��������
			item = pc.getInventory().storeItem(7225, 1);

		} else if (rnd < 255) {// 7.7 ����������
			item = pc.getInventory().storeItem(119, 1);

		} else if (rnd < 305) {// 5 ��Ϸº���
			item = pc.getInventory().storeItem(21259, 1, 0, 0); // ����

		} else if (rnd < 355) {// 5 �����ĺ���
			item = pc.getInventory().storeItem(21266, 1, 0, 0); // ����

		} else if (rnd < 405) {// 5 ���ø����
			item = pc.getInventory().storeItem(21265, 1, 0, 0); // ����

		} else if (rnd < 455) {// 5 Ÿ�󽺺���
			item = pc.getInventory().storeItem(120194, 1); // ����

		} else if (rnd < 505) {// 5 �� �尩
			item = pc.getInventory().storeItem(30219, 1); // ����

		} else if (rnd < 555) {// 5 Ÿ�� �尩
			item = pc.getInventory().storeItem(120187, 1); // ����

		} else if (rnd < 585) {// 3 ������ ���� �尩
			item = pc.getInventory().storeItem(7246, 1); // ����

		} else if (rnd < 615) {// 3 ȥ���� �ձ�
			item = pc.getInventory().storeItem(20190, 1); // ����

		} else if (rnd < 650) {// 3 ��������
			item = pc.getInventory().storeItem(40033, 1); // ����

		} else if (rnd < 685) {// 3 ��������
			item = pc.getInventory().storeItem(40035, 1); // ����

		} else if (rnd < 720) {// 3 ��������Ʈ
			item = pc.getInventory().storeItem(40036, 1); // ����

		} else if (rnd < 755) {// 3 ����������
			item = pc.getInventory().storeItem(40037, 1); // ����

		} else if (rnd < 845) {// 9% ���ε� ������ǵ���
			item = pc.getInventory().storeItem(7335, 1); // ����

		} else if (rnd < 915) {// 7% ������ ����
			item = pc.getInventory().storeItem(20074, 1); // ����

		} else if (rnd < 925) {// 1% �ݳ�
			item = pc.getInventory().storeItem(20049, 1); // ����

		} else if (rnd < 935) {// 1% ����
			item = pc.getInventory().storeItem(20050, 1); // ����

		} else if (rnd < 945) {// 1% �������� �����
			item = pc.getInventory().storeItem(21268, 1); // ����

		} else if (rnd < 955) {// 1% ������ �����
			item = pc.getInventory().storeItem(21260, 1); // ����

		} else if (rnd < 965) {// 1% ���뽺 ������ �����
			item = pc.getInventory().storeItem(21258, 1); // ����

		} else if (rnd < 975) {// 1% ������ �������� ����
			item = pc.getInventory().storeItem(21267, 1); // ����

		} else if (rnd < 985) {// 1% �Ϸ��� ��Ʈ
			item = pc.getInventory().storeItem(21261, 1); // ����

		} else if (rnd < 995) {// 1% ������ ��Ʈ
			item = pc.getInventory().storeItem(21262, 1); // ����

		} else if (rnd < 1005) {// 1% ��ø�� ��Ʈ
			item = pc.getInventory().storeItem(21263, 1); // ����

		} else if (rnd < 1015) {// 1% ������ ��Ʈ
			item = pc.getInventory().storeItem(21264, 1); // ����
		} else {// 0.5% ���ְ��� ����
			item = pc.getInventory().storeItem(21122, 1); // ����
		}

		if (item != null)
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
	}

	private void �׷����Ǽ�������(L1PcInstance pc) {
		L1ItemInstance item = null;
		int count = 1;
		int rnd = _random.nextInt(100000);
		if (rnd < 1) {// 0.001 ����
			int itemid = ����ø���[_random.nextInt(����ø���.length)];
			item = pc.getInventory().storeItem(itemid, count);
		} else if (rnd < 2) {// 0.001 Ŀ��
			item = pc.getInventory().storeItem(54, 1); // Ŀ��
		} else if (rnd < 3) {// 0.001 ����
			item = pc.getInventory().storeItem(58, 1); // ����
		} else if (rnd < 4) {// 0.001 ���ν�������
			item = pc.getInventory().storeItem(291, 1); // ���ν�
		} else if (rnd < 5) {// 0.001 ������ũ�ο�
			item = pc.getInventory().storeItem(292, 1); // ������ũ�ο�
		} else if (rnd < 6) {// 0.001 ������ǵ���
			item = pc.getInventory().storeItem(7227, 1); // ������ǵ���
		} else if (rnd < 7) {// 0.001 ������Ű��ũ
			item = pc.getInventory().storeItem(6001, 1); // ������
		} else if (rnd < 8) {// 0.001 �Ǹ��� ���
			item = pc.getInventory().storeItem(293, 1); // �Ǹ������
		} else if (rnd < 9) {// 0.001 ����Ʈ�ߵ��Ǿ�հ�
			item = pc.getInventory().storeItem(59, 1); // ����
		} else if (rnd < 10) {// 0.001 ������ü�μҵ�
			item = pc.getInventory().storeItem(6000, 1); // ������ü�μҵ�
		} else if (rnd < 11) {// 0.001 �Ǹ��� Į
			item = pc.getInventory().storeItem(63, 1); // �Ǹ���Į
		} else if (rnd < 21) {// 0.01 ������
			int itemid = �����ֽø���[_random.nextInt(�����ֽø���.length)];
			item = pc.getInventory().storeItem(itemid, count);
		} else if (rnd < 31) {// 0.01 ����
			item = pc.getInventory().storeItem(40394 + _random.nextInt(3), 1); // ����
		} else if (rnd < 10031) {// 10% ���/�����ǹ���/�ڸ�
			int rndd = _random.nextInt(100);
			if (rndd < 33) {
				item = pc.getInventory().storeItem(437010, 1); // ���
			} else if (rndd < 66) {
				item = pc.getInventory().storeItem(60217, 1); // �ڸ�����
			} else {
				item = pc.getInventory().storeItem(437017, 1); // �����ǹ���
			}
		} else {
			int itemid = �׷����������ڸ���Ʈ[_random.nextInt(�׷����������ڸ���Ʈ.length)];
			item = pc.getInventory().storeItem(itemid, count);
		}

		/*
		 * int rnd1 = _random.nextInt(16)+2011; int rnd2 =
		 * _random.nextInt(20)+2028; pc.sendPackets(new S_SkillSound(pc.getId(),
		 * rnd1)); Broadcaster.broadcastPacket(pc,new S_SkillSound(pc.getId(),
		 * rnd1)); pc.sendPackets(new S_SkillSound(pc.getId(), rnd2));
		 * Broadcaster.broadcastPacket(pc,new S_SkillSound(pc.getId(), rnd2));
		 * 
		 * pc.sendPackets(new S_SkillSound(pc.getId(), 6412));
		 * Broadcaster.broadcastPacket(pc,new S_SkillSound(pc.getId(), 6412));
		 */

		if (item != null)
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
	}

	private static final int[] �������θ���Ʈ = { 3, 14, 26, 42, 68, 79, 115, 117, 158, 160, 181, 201, 206, 211, 168, 216, 88,
			89 };

	private void ������������(L1PcInstance pc) {

		new L1SkillUse().handleCommands(pc, �������θ���Ʈ[_random.nextInt(�������θ���Ʈ.length)], pc.getId(), pc.getX(), pc.getY(),
				null, 0, L1SkillUse.TYPE_GMBUFF);
	}

	private void ���ɻ���(L1PcInstance pc) {

		pc.getInventory().storeItem(60518, 1);
		pc.sendPackets(new S_ServerMessage(403, "���� ���� 1ȸ ����"), true);

		L1ItemInstance item = null;
		int count = 1;
		int rnd = _random.nextInt(100000);
		if (rnd < 1) {// 0.001 ����
			int itemid = ����ø���[_random.nextInt(����ø���.length)];
			item = pc.getInventory().storeItem(itemid, count);
		} else if (rnd < 2) {// 0.001 Ŀ��
			item = pc.getInventory().storeItem(54, 1); // Ŀ��
		} else if (rnd < 3) {// 0.001 ����
			item = pc.getInventory().storeItem(58, 1); // ����
		} else if (rnd < 13) {// 0.01 ������
			int itemid = �����ֽø���[_random.nextInt(�����ֽø���.length)];
			item = pc.getInventory().storeItem(itemid, count);
		} else if (rnd < 23) {// 0.01 ����
			item = pc.getInventory().storeItem(40394 + _random.nextInt(3), 1); // ����
		} else if (rnd < 2023) {// 2 ���
			item = pc.getInventory().storeItem(437010, 1); // ����
		} else if (rnd < 3023) {// 1 ���̵𺯰�� �� ���� ��ȯ ����
			int rndd = _random.nextInt(100);
			if (rndd < 50) {
				item = pc.getInventory().storeItem(437001, 1); // ���� ��ȯ
			} else {
				item = pc.getInventory().storeItem(467009, 1); // �̸� ����
			}
		} else if (rnd < 13023) {// 10%
			int itemid = �������ɻ��ڸ���Ʈ[_random.nextInt(�������ɻ��ڸ���Ʈ.length)];
			item = pc.getInventory().storeItem(itemid, count);
		}
		int rnd1 = _random.nextInt(16) + 2011;
		int rnd2 = _random.nextInt(20) + 2028;
		pc.sendPackets(new S_SkillSound(pc.getId(), rnd1));
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), rnd1));
		pc.sendPackets(new S_SkillSound(pc.getId(), rnd2));
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), rnd2));

		pc.sendPackets(new S_SkillSound(pc.getId(), 6412));
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 6412));

		if (item != null)
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
	}

	private void Ž����Ʈ����(L1PcInstance pc) {

		// Ž ����Ʈ ������ �����ϸ� Ž�� ������
		pc.getNetConnection().getAccount().tam_point += 2500;
		try {
			pc.getNetConnection().getAccount().updateTam();
			pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.TAM_POINT, pc.getNetConnection()), true);
		} catch (Exception e) {
		}
	}

	int[] adencount = { 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150 };

	private void Ȳ���ָӴ�(L1PcInstance pc) {

		int rnd = adencount[_random.nextInt(adencount.length)] * 10000;
		L1ItemInstance item = pc.getInventory().storeItem(L1ItemId.ADENA, rnd);
		pc.sendPackets(new S_ServerMessage(403, item.getName() + " (" + rnd + ")"), true);
		LogTable.��ɾƵ�(pc, rnd);
	}

	private void ��ȭ�ָӴ�(L1PcInstance pc) {

		int rnd = _random.nextInt(1000);
		if (rnd < 950) {// 95%
			int count = 2500 + _random.nextInt(2000);// �ּ� 2500~4500 �Ƶ���
			L1ItemInstance item = pc.getInventory().storeItem(L1ItemId.ADENA, count);
			pc.sendPackets(new S_ServerMessage(403, item.getName() + " (" + count + ")"), true);
			LogTable.��ɾƵ�(pc, count);
		} else if (rnd < 990) {// 4%
			int count = 77 + _random.nextInt(20000);// �ּ� 77~20077 �Ƶ���
			L1ItemInstance item = pc.getInventory().storeItem(L1ItemId.ADENA, count);
			pc.sendPackets(new S_ServerMessage(403, item.getName() + " (" + count + ")"), true);
			LogTable.��ɾƵ�(pc, count);
		} else if (rnd < 991) {// 0.1%
			int count = 77 + _random.nextInt(777700);// �ּ� 77~77777 �Ƶ��� 777,777��
			L1ItemInstance item = pc.getInventory().storeItem(L1ItemId.ADENA, count);
			pc.sendPackets(new S_ServerMessage(403, item.getName() + " (" + count + ")"), true);
			LogTable.��ɾƵ�(pc, count);
		} else {// 0.9%
			int count = 77 + _random.nextInt(77700);// �ּ� 77~77777 �Ƶ��� 777,777��
			L1ItemInstance item = pc.getInventory().storeItem(L1ItemId.ADENA, count);
			pc.sendPackets(new S_ServerMessage(403, item.getName() + " (" + count + ")"), true);
			LogTable.��ɾƵ�(pc, count);
		}
	}

	private void �Ǿ�������(L1PcInstance pc, int itemId, L1ItemInstance useItem, int day) {
		long sysTime = System.currentTimeMillis();
		Timestamp deleteTime = null;
		deleteTime = new Timestamp(sysTime + (86400000 * (long) day) + 10000);// 7��
		try {
			if (pc.PC��_����) {
				pc.sendPackets(new S_SystemMessage("�̹� PC�� ���� ��ǰ�� �������Դϴ�."));
				return;
			}

			// pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.�Ǿ���_����,
			// (int)86400000*day);

			pc.sendPackets(new S_PacketBox(S_PacketBox.PC�����, 1), true);
			pc.PC��_���� = true;
			String s = "08 01 f1 d5";// �Ǿ���..
			// pc.sendPackets(new S_NewCreateItem(s ));
			pc.sendPackets(new S_NewCreateItem(126, s));
			if (day == 7) {
				pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[PC�� �̿� �ð�] 7�� ���� PC�� ������ ���� �˴ϴ�."));
			} else {
				pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[PC�� �̿� �ð�] 30�� ���� PC�� ������ ���� �˴ϴ�."));
			}
			pc.getNetConnection().getAccount().setBuff_PC��(deleteTime);
			pc.getNetConnection().getAccount().update�Ǿ���();
			pc.getInventory().removeItem(useItem, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void ��ȭ����(L1PcInstance pc, int itemId, L1ItemInstance useItem) {
		String n = "";
		long sysTime = System.currentTimeMillis();
		Timestamp deleteTime = null;
		deleteTime = new Timestamp(sysTime + (86400000 * (long) 1) + 10000);// 7��
		try {
			if (itemId == 600212) {
				n = "Ȱ��";
				if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ȭ����_Ȱ��)) {
					pc.sendPackets(new S_SystemMessage("�̹� " + n + " ���� ��ǰ�� �������Դϴ�."));
					return;
					/*
					 * pc.addMaxHp(-50); pc.addMaxMp(-50);
					 * pc.addWeightReduction(-3); pc.sendPackets(new
					 * S_HPUpdate(pc)); pc.sendPackets(new
					 * S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					 */
				}
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_Ȱ��, (int) 86400000 * 1);
				pc.getNetConnection().getAccount().setBuff_HPMP(deleteTime);
				pc.sendPackets(new S_NewUI(n, (long) 86400000 * 1), true);
				pc.addMaxHp(50);
				pc.addMaxMp(50);
				pc.addWeightReduction(3);
				pc.sendPackets(new S_HPUpdate(pc));
				pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));

			} else if (itemId == 600213) {
				n = "����";
				if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ȭ����_����)) {
					pc.sendPackets(new S_SystemMessage("�̹� " + n + " ���� ��ǰ�� �������Դϴ�."));
					return;
					/*
					 * pc.addDmgup(-1); pc.addBowDmgup(-1);
					 */
				}
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_����);
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_����, (int) 86400000 * 1);
				pc.getNetConnection().getAccount().setBuff_DMG(deleteTime);
				pc.sendPackets(new S_NewUI(n, (long) 86400000 * 1), true);
				pc.addDmgup(1);
				pc.addBowDmgup(1);
			} else if (itemId == 600214) {
				n = "���";
				if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ȭ����_���)) {
					pc.sendPackets(new S_SystemMessage("�̹� " + n + " ���� ��ǰ�� �������Դϴ�."));
					return;
					// pc.addDamageReductionByArmor(-1);
				}
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_���);
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_���, (int) 86400000 * 1);
				pc.getNetConnection().getAccount().setBuff_REDUC(deleteTime);
				pc.sendPackets(new S_NewUI(n, (long) 86400000 * 1), true);
				pc.addDamageReductionByArmor(1);
			} else if (itemId == 600215) {
				n = "����";
				if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ȭ����_����)) {
					pc.sendPackets(new S_SystemMessage("�̹� " + n + " ���� ��ǰ�� �������Դϴ�."));
					return;
					// pc.getAbility().addSp(-1);
					// pc.sendPackets(new S_SPMR(pc));
				}
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_����);
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_����, (int) 86400000 * 1);
				pc.getNetConnection().getAccount().setBuff_MAGIC(deleteTime);
				pc.sendPackets(new S_NewUI(n, (long) 86400000 * 1), true);
				pc.getAbility().addSp(1);
				pc.sendPackets(new S_SPMR(pc));
			} else if (itemId == 600216) {
				n = "����";
				if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ȭ����_����)) {
					pc.sendPackets(new S_SystemMessage("�̹� " + n + " ���� ��ǰ�� �������Դϴ�."));
					return;
					// pc.getResistance().addStun(-2);
				}
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_����);
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_����, (int) 86400000 * 1);
				pc.getNetConnection().getAccount().setBuff_STUN(deleteTime);
				pc.sendPackets(new S_NewUI(n, (long) 86400000 * 1), true);
				pc.getResistance().addStun(2);
			} else if (itemId == 600217) {
				n = "Ȧ��";
				if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.��ȭ����_Ȧ��)) {
					pc.sendPackets(new S_SystemMessage("�̹� " + n + " ���� ��ǰ�� �������Դϴ�."));
					return;
					// pc.getResistance().addHold(-2);
				}
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.��ȭ����_Ȧ��);
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.��ȭ����_Ȧ��, (int) 86400000 * 1);
				pc.getNetConnection().getAccount().setBuff_HOLD(deleteTime);
				pc.sendPackets(new S_NewUI(n, (long) 86400000 * 1), true);
				pc.getResistance().addHold(2);
			}
			pc.getNetConnection().getAccount().updateBUFF();
			pc.getInventory().removeItem(useItem, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ��������(L1PcInstance pc, int itemId, L1ItemInstance useItem) {

		if (itemId == 60502) {// ǳ���� ����:3��
			pc.getInventory().storeItem(40308, 30000);
			pc.sendPackets(new S_ServerMessage(403, "�Ƶ��� (30000)"), true);
			if (_random.nextInt(1000) == 1) {
				pc.getInventory().storeItem(60510, 1);
				pc.sendPackets(new S_ServerMessage(403, "������ ���� ���� �ֹ���"), true);
			}
			pc.getInventory().removeItem(useItem, 1);
		} else if (itemId == 60503) {// ������ ����:35��
			pc.getInventory().storeItem(40308, 30000);
			pc.sendPackets(new S_ServerMessage(403, "�Ƶ��� (350000)"), true);
			if (_random.nextInt(1000) == 1) {
				pc.getInventory().storeItem(60510, 1);
				pc.sendPackets(new S_ServerMessage(403, "������ ���� ���� �ֹ���"), true);
			}
			pc.getInventory().removeItem(useItem, 1);
		} else if (itemId == 60504) {// ������ ����:120��
			pc.getInventory().storeItem(40308, 30000);
			pc.sendPackets(new S_ServerMessage(403, "�Ƶ��� (1200000)"), true);
			if (_random.nextInt(1000) == 1) {
				pc.getInventory().storeItem(60510, 1);
				pc.sendPackets(new S_ServerMessage(403, "������ ���� ���� �ֹ���"), true);
			}
			pc.getInventory().removeItem(useItem, 1);
		} else if (itemId == 60505) {// ȭ���� ����:500��
			pc.getInventory().storeItem(40308, 30000);
			pc.sendPackets(new S_ServerMessage(403, "�Ƶ��� (5000000)"), true);
			if (_random.nextInt(1000) == 1) {
				pc.getInventory().storeItem(60510, 1);
				pc.sendPackets(new S_ServerMessage(403, "������ ���� ���� �ֹ���"), true);
			}
			pc.getInventory().removeItem(useItem, 1);
		}
	}

	// private static final int[] �߾ӻ�����ڸ���Ʈ = {412000, 412001, 412005, 412004,
	// 412003, 191, 259, 260, 292, 293, 21261, 21262, 21263, 21264};
	private void �߾ӻ��Ȳ�ݻ���(L1PcInstance pc) {
		int rnd = _random.nextInt(1000) + 1;
		int itemid = 0;
		int itemcount = 1;
		if (rnd <= 5) {
			itemid = 60510; // ���ι��⸶���ܼ�
		} else if (rnd <= 10) {
			itemid = 21264; // �����Ǻ�Ʈ
		} else if (rnd <= 15) {
			itemid = 21263; // ��ø�Ǻ�Ʈ
		} else if (rnd <= 20) {
			itemid = 21262; // �����Ǻ�Ʈ
		} else if (rnd <= 25) {
			itemid = 21261; // �Ϸ��Ǻ�Ʈ

		} else if (rnd <= 575) {
			itemid = 41352; // �ż��� �������ǻ�

		} else if (rnd <= 975) {
			itemid = 40308; // �Ƶ���
			itemcount = 50000;
		} else if (rnd <= 980) {
			itemid = 40308; // �Ƶ���
			itemcount = 100000;
		} else if (rnd <= 985) {
			itemid = 40308; // �Ƶ���
			itemcount = 200000;
		} else if (rnd <= 990) {
			itemid = 40308; // �Ƶ���
			itemcount = 250000;
		} else if (rnd <= 995) {
			itemid = 40308; // �Ƶ���
			itemcount = 300000;
		} else if (rnd <= 1000) {
			itemid = 40308; // �Ƶ���
			itemcount = 1000000;
		}

		// L1ItemInstance item = pc.getInventory().storeItem(41352, 1);
		// pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
		L1ItemInstance item = pc.getInventory().storeItem(itemid, itemcount);
		pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
	}

	private void ��������_����_��ȭ(L1PcInstance pc, int itemId, L1ItemInstance useItem) {

		pc.getInventory().removeItem(useItem, 1);
		if (itemId == 60512) {// �������� ����
			pc.sendPackets(new S_SkillSound(pc.getId(), 1819), true);
			pc.sendPackets(new S_DoActionGFX(pc.getId(), 18), true);
			for (L1Object obj : L1World.getInstance().getVisibleObjects(pc, 3)) {
				if (obj instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) obj;
					if (mon == null || mon._destroyed || mon.isDead())
						continue;
					mon.receiveDamage(pc, 200 + _random.nextInt(71));
					pc.sendPackets(new S_DoActionGFX(mon.getId(), ActionCodes.ACTION_Damage), true);
				}
			}
		} else if (itemId == 60513) {// �������� ��ȭ
			pc.sendPackets(new S_SkillSound(pc.getId(), 3934), true);
			pc.sendPackets(new S_DoActionGFX(pc.getId(), 18), true);
			for (L1Object obj : L1World.getInstance().getVisibleObjects(pc, 12)) {
				if (obj instanceof L1MonsterInstance) {
					L1MonsterInstance mon = (L1MonsterInstance) obj;
					if (mon == null || mon._destroyed || mon.isDead())
						continue;
					mon.receiveDamage(pc, 700);
					pc.sendPackets(new S_DoActionGFX(mon.getId(), ActionCodes.ACTION_Damage), true);
				}
			}
		}
	}

	private void ����52��������Ʈ�����ۻ���(L1PcInstance pc, int itemId) {

		int itemid = 0;
		if (pc.isCrown()) {
			itemid = 51;
		} else if (pc.isKnight())
			itemid = 56;
		else if (pc.isElf())
			itemid = 184;
		else if (pc.isDarkelf())
			itemid = 13;
		else if (pc.isDragonknight())
			itemid = 410000;
		else if (pc.isIllusionist())
			itemid = 410003;
		else if (pc.isWizard())
			itemid = 20225;
		if (itemid != 0) {
			if (itemId == 60485)
				itemid += 100000;
			L1ItemInstance item = pc.getInventory().storeItem(itemid, 1);
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
		}
	}

	private void ū���������Ƴ�(L1PcInstance pc) {

		L1ItemInstance item = pc.getInventory().storeItem(40308, 500000);
		pc.sendPackets(new S_ServerMessage(403, item.getName() + " (500000)"), true);
		item = null;
		int rnd = _random.nextInt(10000);
		if (rnd < 20) {// ���� 0.2%
			item = pc.getInventory().storeItem(420000, 1);
		} else if (rnd < 40) {// ������ 0.2%
			item = pc.getInventory().storeItem(420003, 1);
		} else if (rnd < 90) {// ���� �㸮�� 0.5%
			item = pc.getInventory().storeItem(20315, 1);
		} else if (rnd < 140) {// ���� ����� 0.5%
			item = pc.getInventory().storeItem(20262, 1);
		} else if (rnd < 190) {// ���� ���� 0.5%
			item = pc.getInventory().storeItem(20291, 1);
		} else if (rnd < 290) {// ����� 1%
			item = pc.getInventory().storeItem(20422, 1);
		} else if (rnd < 590) {// ���� ���� 3%
			item = pc.getInventory().storeItem(41248, 1);
		} else if (rnd < 890) {// ��Ƽ ���� 3%
			item = pc.getInventory().storeItem(430004, 1);
		} else if (rnd < 1190) {// ��ī ���� 3%
			item = pc.getInventory().storeItem(430500, 1);
		} else if (rnd < 1690) {// �� 15%
			item = pc.getInventory().storeItem(40074, 1);
		} else if (rnd < 3190) {// ���� 15%
			item = pc.getInventory().storeItem(40087, 1);
		} else if (rnd < 4690) {// 52���� ����Ʈ�����ۻ��� 15%
			item = pc.getInventory().storeItem(60484, 1);
		}
		if (item == null)
			pc.sendPackets(new S_SystemMessage("���ʽ� ������ ȹ�濡 �����Ͽ����ϴ�."), true);
		else
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
	}

	private void ū�ݺ������Ƴ�(L1PcInstance pc) {

		L1ItemInstance item = pc.getInventory().storeItem(40308, 5000000);
		pc.sendPackets(new S_ServerMessage(403, item.getName() + " (5000000)"), true);
		item = null;
		int rnd = _random.nextInt(10000);
		if (rnd < 10) {// ������(CHA) 0.1%
			item = pc.getInventory().storeItem(40308, 1);
		} else if (rnd < 60) {// �Ӵ� 0.5%
			item = pc.getInventory().storeItem(276, 1);
		} else if (rnd < 110) {// ��Ű 0.5%
			item = pc.getInventory().storeItem(410004, 1);
		} else if (rnd < 160) {// ���� 0.5%
			item = pc.getInventory().storeItem(205, 1);
		} else if (rnd < 260) {// �����尩 1%
			item = pc.getInventory().storeItem(20165, 1);
		} else if (rnd < 360) {// ������� 1%
			item = pc.getInventory().storeItem(20197, 1);
		} else if (rnd < 460) {// ����κ� 1%
			item = pc.getInventory().storeItem(20160, 1);
		} else if (rnd < 560) {// ������� 1%
			item = pc.getInventory().storeItem(20218, 1);
		} else if (rnd < 660) {// ���Ͻ����� 1%
			item = pc.getInventory().storeItem(20298, 1);
		} else if (rnd < 960) {// �������� - ���� 3%
			item = pc.getInventory().storeItem(41250, 1);
		} else if (rnd < 1260) {// �������� - ���� 3%
			item = pc.getInventory().storeItem(430000, 1);
		} else if (rnd < 1560) {// �������� - ��ť 3%
			item = pc.getInventory().storeItem(41249, 1);
		} else if (rnd < 2260) {// �൥�� 7%
			item = pc.getInventory().storeItem(140087, 1);
		} else if (rnd < 2960) {// ���� 7%
			item = pc.getInventory().storeItem(140074, 1);
		} else if (rnd < 3960) {// 52���� ����Ʈ�����ۻ��� 10%
			item = pc.getInventory().storeItem(60485, 1);
		}
		if (item == null)
			pc.sendPackets(new S_SystemMessage("���ʽ� ������ ȹ�濡 �����Ͽ����ϴ�."), true);
		else
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
	}

	private void �⺻������(L1PcInstance pc) {

		createNewItem2(pc, 20028, 1, 4, 0, 0);
		createNewItem2(pc, 21098, 1, 4, 0, 0);
		createNewItem2(pc, 20126, 1, 4, 0, 0);
		createNewItem2(pc, 20173, 1, 4, 0, 0);
		createNewItem2(pc, 20206, 1, 4, 0, 0);
		createNewItem2(pc, 20232, 1, 4, 0, 0);
		createNewItem2(pc, 20082, 1, 4, 0, 0);

		if (pc.isKnight()) {
			createNewItem2(pc, 35, 1, 6, 0, 0); // ���ž�Ѽհ�
			createNewItem2(pc, 48, 1, 6, 0, 0); // ���ž��հ�
		} else if (pc.isElf()) {
			createNewItem2(pc, 175, 1, 6, 0, 0); // ���žȰ
			createNewItem2(pc, 174, 1, 6, 0, 0); // ���ž����
		} else if (pc.isWizard()) {
			createNewItem2(pc, 120, 1, 6, 0, 0); // ���ž������
		} else if (pc.isDarkelf()) {
			createNewItem2(pc, 73, 1, 6, 0, 0); // ���ž�̵���
			createNewItem2(pc, 156, 1, 6, 0, 0); // ���žũ�ο�
		} else if (pc.isIllusionist()) {
			createNewItem2(pc, 147, 1, 6, 0, 0); // ���ž����
			createNewItem2(pc, 120, 1, 6, 0, 0); // ���ž������
		} else if (pc.isDragonknight()) {
			createNewItem2(pc, 48, 1, 6, 0, 0); // ���ž��հ�
			createNewItem2(pc, 147, 1, 6, 0, 0); // ���ž����
		} else if (pc.isCrown()) {
			createNewItem2(pc, 35, 1, 6, 0, 0); // ���ž�Ѽհ�
			createNewItem2(pc, 48, 1, 6, 0, 0); // ���ž��հ�
		}
	}

	private void Ŭ������ų��(L1PcInstance pc) {
		if (pc.isCrown()) {
			if (pc.getInventory().getSize() < 173) {
				for (int i = 40226; i <= 40231; i++) {
					createNewItem2(pc, i, 1, 0); // ���� ��ų
				}
				createNewItem2(pc, 60348, 1, 0); // �ƹ�Ÿ
			} else {
				pc.sendPackets(new S_SystemMessage("�κ��丮�� 7���� ������� �ʿ��մϴ�."));
			}
		}

		if (pc.isKnight()) {
			if (pc.getInventory().getSize() < 175) {
				for (int i = 40164; i <= 40166; i++) {
					createNewItem2(pc, i, 1, 0); // ��� ��ų
				}
				createNewItem2(pc, 41147, 1, 0); // ��� ��ų
				// createNewItem2(pc, 41148, 1, 0); //��� ��ųī��
			} else {
				pc.sendPackets(new S_SystemMessage("�κ��丮�� 4���� ������� �ʿ��մϴ�."));
				// pc.sendPackets(new S_SystemMessage("�κ��丮�� 5���� ������� �ʿ��մϴ�."));
			}
		}

		if (pc.isElf()) {
			if (pc.getInventory().getSize() < 142) {
				for (int i = 40232; i <= 40264; i++) {// 38
					createNewItem2(pc, i, 1, 0); // ������ų
				}
				for (int i = 41150; i <= 41153; i++) {
					createNewItem2(pc, i, 1, 0); // ������ų
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�κ��丮�� 38���� ������� �ʿ��մϴ�."));
			}
		}
		if (pc.isWizard()) {
			if (pc.getInventory().getSize() < 124) {
				for (int i = 40170; i <= 40225; i++) {
					if (i == 40222 || i == 40223 || i == 40212)
						continue;// ��
					createNewItem2(pc, i, 1, 0); // ���� ��ų
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�κ��丮�� 55���� ������� �ʿ��մϴ�."));
			}
		}

		if (pc.isDarkelf()) {
			if (pc.getInventory().getSize() < 164) {
				for (int i = 40265; i <= 40279; i++) {
					createNewItem2(pc, i, 1, 0); // �ٿ� ��ų
				}
				// createNewItem2(pc, 60199, 1, 0); //�ٿ� ��ų
			} else {
				pc.sendPackets(new S_SystemMessage("�κ��丮�� 15���� ������� �ʿ��մϴ�."));
			}
		}

		if (pc.isDragonknight()) {
			if (pc.getInventory().getSize() < 164) {
				for (int i = 439100; i <= 439114; i++) {
					createNewItem2(pc, i, 1, 0); // ���� ��ų
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�κ��丮�� 15���� ������� �ʿ��մϴ�."));
			}
		}
		if (pc.isIllusionist()) {
			if (pc.getInventory().getSize() < 160) {
				for (int i = 439000; i <= 439019; i++) {
					createNewItem2(pc, i, 1, 0); // ���� ��ų
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�κ��丮�� 20���� ������� �ʿ��մϴ�."));
			}
		}
		if (pc.isWarrior()) {
			if (pc.getInventory().getSize() < 168) {
				for (int i = 7300; i <= 7311; i++) {
					if (i == 7310 || i == 7304 || i == 7305 || i == 7306)
						continue;// �������
					createNewItem2(pc, i, 1, 0); // ���罺ų
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�κ��丮�� 11���� ������� �ʿ��մϴ�."));
			}
		}

	}

	private void ����(L1PcInstance pc) {
		if (pc.getInventory().checkItem(7022, 1)) {
			pc.getInventory().consumeItem(7022, 1);
			if (pc.isKnight()) {
				createNewItem2(pc, 120011, 1, 9); // ���� ��� ����(��)
				createNewItem2(pc, 20048, 1, 7); // ȥ���� ����
				createNewItem2(pc, 20078, 1, 9); // ȥ���� ����
				createNewItem2(pc, 21028, 1, 9); // ���� Ƽ����
				createNewItem2(pc, 425106, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 425108, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 20200, 1, 9); // �������� ����
				createNewItem2(pc, 20183, 1, 9); // �ϻ챺���� �尩
				createNewItem2(pc, 420002, 1, 7); // ũ����Ż ����
				createNewItem2(pc, 420104, 1, 9); // ��Ÿ���� �Ϸ�
			}
			if (pc.isCrown()) {
				createNewItem2(pc, 120011, 1, 9); // ���� ��� ����(��)
				createNewItem2(pc, 20048, 1, 7); // ȥ���� ����
				createNewItem2(pc, 20078, 1, 9); // ȥ���� ����
				createNewItem2(pc, 21028, 1, 9); // ���� Ƽ����
				createNewItem2(pc, 425106, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 425108, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 20178, 1, 9); // �������� ����
				createNewItem2(pc, 20183, 1, 9); // �ϻ챺���� �尩
				createNewItem2(pc, 420002, 1, 7); // ũ����Ż ����
				createNewItem2(pc, 420105, 1, 9); // ��Ÿ���� �Ϸ�
			}
			if (pc.isWizard()) {
				createNewItem2(pc, 120011, 1, 9); // ���� ��� ����(��)
				createNewItem2(pc, 20048, 1, 7); // ȥ���� ����
				createNewItem2(pc, 20078, 1, 9); // ȥ���� ����
				createNewItem2(pc, 21028, 1, 9); // ���� Ƽ����
				createNewItem2(pc, 425106, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 425108, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 20218, 1, 9); // �������� ����
				createNewItem2(pc, 20183, 1, 9); // �ϻ챺���� �尩
				createNewItem2(pc, 420002, 1, 7); // ũ����Ż ����
				createNewItem2(pc, 420107, 1, 9); // ��Ÿ���� �Ϸ�
			}
			if (pc.isElf()) {
				createNewItem2(pc, 120011, 1, 9); // ���� ��� ����(��)
				createNewItem2(pc, 20048, 1, 7); // ȥ���� ����
				createNewItem2(pc, 20078, 1, 9); // ȥ���� ����
				createNewItem2(pc, 21028, 1, 9); // ���� Ƽ����
				createNewItem2(pc, 425106, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 425108, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 20216, 1, 9); // �������� ����
				createNewItem2(pc, 20190, 1, 9); // �ϻ챺���� �尩
				createNewItem2(pc, 420002, 1, 7); // ũ����Ż ����
				createNewItem2(pc, 420106, 1, 9); // ��Ÿ���� �Ϸ�
			}
			if (pc.isDarkelf()) {
				createNewItem2(pc, 120011, 1, 9); // ���� ��� ����(��)
				createNewItem2(pc, 20048, 1, 7); // ȥ���� ����
				createNewItem2(pc, 20078, 1, 9); // ȥ���� ����
				createNewItem2(pc, 21028, 1, 9); // ���� Ƽ����
				createNewItem2(pc, 425106, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 425108, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 20200, 1, 9); // �������� ����
				createNewItem2(pc, 20183, 1, 9); // �ϻ챺���� �尩
				createNewItem2(pc, 420002, 1, 7); // ũ����Ż ����
				createNewItem2(pc, 420106, 1, 9); // ��Ÿ���� �Ϸ�
			}
			if (pc.isDragonknight()) {
				createNewItem2(pc, 120011, 1, 9); // ���� ��� ����(��)
				createNewItem2(pc, 20048, 1, 7); // ȥ���� ����
				createNewItem2(pc, 20078, 1, 9); // ȥ���� ����
				createNewItem2(pc, 21028, 1, 9); // ���� Ƽ����
				createNewItem2(pc, 425106, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 425108, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 20200, 1, 9); // �������� ����
				createNewItem2(pc, 20183, 1, 9); // �׺�
				createNewItem2(pc, 420002, 1, 7); // ũ����Ż ����
				createNewItem2(pc, 420105, 1, 9); // ��Ÿ���� �Ϸ�
			}
			if (pc.isIllusionist()) {
				createNewItem2(pc, 120011, 1, 9); // ���� ��� ����(��)
				createNewItem2(pc, 20048, 1, 7); // ȥ���� ����
				createNewItem2(pc, 20078, 1, 9); // ȥ���� ����
				createNewItem2(pc, 21028, 1, 9); // ���� Ƽ����
				createNewItem2(pc, 425106, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 425108, 1, 9); // ���� ������ Ƽ����
				createNewItem2(pc, 20200, 1, 9); // �������� ����
				createNewItem2(pc, 20183, 1, 9); // �ϻ챺���� �尩
				createNewItem2(pc, 420002, 1, 7); // ũ����Ż ����
				createNewItem2(pc, 420107, 1, 9); // ��Ÿ���� �Ϸ�
			}
		}
		// *******************��������(1�ܰ�)************************//
	}

	private void ����������(L1PcInstance pc, int itemid) {
		if (itemid != 7334) {

			if (pc.isCrown()) {
				createNewItem2(pc, 60133, 10, 0); // �Ǹ�����
			}

			if (pc.isWarrior() || pc.isKnight()) {
				createNewItem2(pc, 60134, 10, 0); // ���
			}

			if (pc.isElf()) {
				createNewItem2(pc, 60135, 10, 0); // ����
			}

			if (pc.isWizard()) {
				createNewItem2(pc, 240016, 10, 0); // ����
			}

			if (pc.isDarkelf()) {
				createNewItem2(pc, 60139, 10, 0); // ��伮
			}

			if (pc.isDragonknight()) {
				createNewItem2(pc, 60140, 10, 0); // ������
			}

			if (pc.isIllusionist()) {
				createNewItem2(pc, 60136, 10, 0); // ���׵�󹰾�
			}

		}

		if (itemid == 7325) {// 10����
			createNewItem2(pc, 40117, 1, 0); // ������ȯ
			createNewItem2(pc, 60142, 1, 0); // ������ ���⸶��
			createNewItem2(pc, 60141, 4, 0); // ������ ���ʸ���
			if (pc.isIllusionist()) {
				createNewItem2(pc, 439004, 1, 0); // �̱״ϼ�
				createNewItem2(pc, 439000, 1, 0); // �̷� �̹���
				createNewItem2(pc, 439001, 1, 0); // ��ǻ��
			}
			if (pc.isElf()) {
				createNewItem2(pc, 40233, 1, 0); // �ٵ� �� ���ε�
				createNewItem2(pc, 40234, 1, 0); // �ٵ� �� ���ε�
			}
		} else if (itemid == 7326) {// 15����
			createNewItem2(pc, 21099, 1, 0); // �������Ǻ�Ʈ
			createNewItem2(pc, 60142, 1, 0); // ������ ���⸶��
			createNewItem2(pc, 60141, 4, 0); // ������ ���ʸ���
			if (pc.isCrown()) {
				createNewItem2(pc, 40226, 1, 0); // Ʈ��Ÿ��
			}
			if (pc.isDarkelf()) {
				createNewItem2(pc, 40268, 1, 0); // �긵����
			}
			if (pc.isDragonknight()) {
				createNewItem2(pc, 439100, 1, 0); // �巡�ｺŲ
				createNewItem2(pc, 439101, 1, 0); // ���׽����̾�
			}
			if (pc.isWarrior()) {
				createNewItem2(pc, 7232, 1, 0); // �������� ��������
				createNewItem2(pc, 7302, 1, 0); // �����̾�
			}

		} else if (itemid == 7327) {// 20����
			createNewItem2(pc, 21100, 1, 0); // �������ǹ���
			createNewItem2(pc, 60142, 1, 0); // ������ ���⸶��
			createNewItem2(pc, 60141, 4, 0); // ������ ���ʸ���
			if (pc.isWizard()) {
				createNewItem2(pc, 40170, 1, 0); // �ĺ�
			}
		} else if (itemid == 7328) {// 25����
			createNewItem2(pc, 21100, 1, 0); // �������ǹ���
			createNewItem2(pc, 60142, 1, 0); // ������ ���⸶��
			createNewItem2(pc, 60141, 4, 0); // ������ ���ʸ���
			if (pc.isWizard()) {
				createNewItem2(pc, 40188, 1, 0); // ���̽�Ʈ
			}
		} else if (itemid == 7329) {// 30����
			createNewItem2(pc, 60142, 1, 0); // ������ ���⸶��
			createNewItem2(pc, 60141, 4, 0); // ������ ���ʸ���
			if (pc.isCrown()) {
				createNewItem2(pc, 40228, 1, 0); // ��Ŭ��
			}
			if (pc.isDarkelf()) {
				createNewItem2(pc, 40270, 1, 0); // ���� �Ǽ�
			}
			if (pc.isDragonknight()) {
				createNewItem2(pc, 439106, 1, 0); // ����
				createNewItem2(pc, 439105, 1, 0); // ���巯��Ʈ
			}
			if (pc.isIllusionist()) {
				createNewItem2(pc, 439014, 1, 0); // ť���ũ
			}
			if (pc.isWarrior()) {
				createNewItem2(pc, 7307, 1, 0); // �Ͽ�
			}
		} else if (itemid == 7330) {// 35����
			createNewItem2(pc, 60142, 1, 0); // ������ ���⸶��
			createNewItem2(pc, 60141, 4, 0); // ������ ���ʸ���
			createNewItem2(pc, 21101, 1, 0); // �������� �����
		} else if (itemid == 7331) {// 40����
			createNewItem2(pc, 437004, 2, 0); // ����
			if (pc.isElf()) {
				createNewItem2(pc, 40243, 1, 0); // ���շ��� ������Ż
			}
		} else if (itemid == 7332) {// 45����
			createNewItem2(pc, 420010, 1, 0); // �������� �Ͱ���
			if (pc.isDarkelf()) {
				createNewItem2(pc, 40276, 1, 0); // ���ɴϴ���
			}
			if (pc.isWarrior()) {
				createNewItem2(pc, 7300, 1, 0); // ũ����
			}
		} else if (itemid == 7333) {// 50����
			createNewItem2(pc, 60061, 1, 0); // ��������
		} else if (itemid == 7334) {// 52����
			createNewItem2(pc, 60359, 10, 0); // ������ ���� �ֹ���
			createNewItem2(pc, 140100, 10, 0); // ���
			createNewItem2(pc, 60381, 1, 0); // ������
			createNewItem2(pc, 140032, 5, 0); // �����ǰ�ȣ

			createNewItem2(pc, 50751, 1, 0); // ������ȯ������

			if (pc.isCrown()) {
				createNewItem2(pc, 51, 1, 0); // Ȳ�� ���ֺ�
				createNewItem2(pc, 20051, 1, 0); // ������ ����
			}
			if (pc.isKnight()) {
				createNewItem2(pc, 56, 1, 0); // ���� ���̵�
				createNewItem2(pc, 20318, 1, 0); // ����� ��Ʈ
			}
			if (pc.isElf()) {
				createNewItem2(pc, 184, 1, 0); // ȭ���� Ȱ
				createNewItem2(pc, 50, 1, 0); // ȭ���� ��
			}
			if (pc.isWizard()) {
				createNewItem2(pc, 20055, 1, 0); // ��������
				createNewItem2(pc, 20225, 1, 0); // ���� ������
			}
			if (pc.isDarkelf()) {
				createNewItem2(pc, 20195, 1, 0); // �׺�
				createNewItem2(pc, 13, 1, 0); // �ΰ� ���� ����
			}
			if (pc.isDragonknight()) {
				createNewItem2(pc, 410000, 1, 0); // �Ҹ����� ü�μҵ�
				createNewItem2(pc, 420001, 1, 0); // ���� ����
			}
			if (pc.isIllusionist()) {
				createNewItem2(pc, 420006, 1, 0); // ȯ������ ������
				createNewItem2(pc, 410003, 1, 0); // �����̾� Ű��ũ
			}
			if (pc.isWarrior()) {
				createNewItem2(pc, 7231, 1, 0); // ���������ǵ���
				createNewItem2(pc, 7247, 1, 0); // ������� ����
			}

		}

	}

	private void ��ǳ����������(L1PcInstance pc) {

		// pc.getInventory().storeItem(L1ItemId.ADENA, 50000000);
		// createNewItem2(pc, 7258, 1, 0, 0, 0); // ��ų��7258
		if (pc.isKnight()) {
			createNewItem2(pc, 450028, 1, 6); // ������ ��հ�
			createNewItem2(pc, 550001, 1, 0); // ���� ����� ����

			createNewItem2(pc, 110111, 1, 7); // ������ ����
			createNewItem2(pc, 21103, 1, 7); // �������� �߰��� �尩
			createNewItem2(pc, 21104, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21105, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21106, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21107, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 500215, 1, 7); // ������ ����
			createNewItem2(pc, 21099, 1, 5); // �������� �߰��� ��Ʈ
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21101, 1, 5); // �������� �߰��� �����
			createNewItem2(pc, 21032, 1, 7); // Ƽ����
			createNewItem2(pc, 21005, 1, 5); // ������ �Ͱ���

		} else if (pc.isWarrior()) {
			createNewItem2(pc, 450036, 1, 6); // ������ ����
			createNewItem2(pc, 450036, 1, 6); // ������ ����
			createNewItem2(pc, 550001, 1, 0); // ���� ����� ����
			createNewItem2(pc, 41246, 100, 0); // ����ü

			createNewItem2(pc, 21103, 1, 7); // �������� �߰��� �尩
			createNewItem2(pc, 21104, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21105, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21106, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21107, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 500215, 1, 7); // ������ ����
			createNewItem2(pc, 21099, 1, 5); // �������� �߰��� ��Ʈ
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21101, 1, 5); // �������� �߰��� �����
			createNewItem2(pc, 21032, 1, 7); // Ƽ����
			createNewItem2(pc, 21005, 1, 5); // ������ �Ͱ���

		} else if (pc.isElf()) {
			createNewItem2(pc, 450029, 1, 6); // ������ Ȱ
			createNewItem2(pc, 450032, 1, 6); // ������ �ܰ�
			createNewItem2(pc, 40744, 3000, 0); // ��ȭ��
			createNewItem2(pc, 40068, 2, 0); // ���� ����
			createNewItem2(pc, 40319, 100, 0); // ���ɿ�

			createNewItem2(pc, 110111, 1, 7); // ������ ����
			createNewItem2(pc, 21108, 1, 7); // �������� �ż��� �尩
			createNewItem2(pc, 21109, 1, 7); // �������� �ż��� ����
			createNewItem2(pc, 21110, 1, 7); // �������� �ż��� ����
			createNewItem2(pc, 21111, 1, 7); // �������� �ż��� �κ�
			createNewItem2(pc, 21112, 1, 7); // �������� �ż��� ����
			createNewItem2(pc, 500216, 1, 7); // ������ ����
			createNewItem2(pc, 21099, 1, 5); // �������� �߰��� ��Ʈ
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21101, 1, 5); // �������� �߰��� �����
			createNewItem2(pc, 21032, 1, 7); // Ƽ����
			createNewItem2(pc, 21005, 1, 5); // ������ �Ͱ���
		} else if (pc.isWizard()) {
			createNewItem2(pc, 450030, 1, 6); // ������ ������
			createNewItem2(pc, 40318, 100, 0); // ������ ��

			createNewItem2(pc, 110111, 1, 7); // ������ ����
			createNewItem2(pc, 21108, 1, 7); // �������� �ż��� �尩
			createNewItem2(pc, 21109, 1, 7); // �������� �ż��� ����
			createNewItem2(pc, 21110, 1, 7); // �������� �ż��� ����
			createNewItem2(pc, 21111, 1, 7); // �������� �ż��� �κ�
			createNewItem2(pc, 21112, 1, 7); // �������� �ż��� ����
			createNewItem2(pc, 500216, 1, 7); // ������ ����
			createNewItem2(pc, 21099, 1, 5); // �������� �߰��� ��Ʈ
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21101, 1, 5); // �������� �߰��� �����
			createNewItem2(pc, 21032, 1, 7); // Ƽ����
			createNewItem2(pc, 21005, 1, 5); // ������ �Ͱ���
		} else if (pc.isDarkelf()) {
			createNewItem2(pc, 450033, 1, 6); // ������ �̵���
			createNewItem2(pc, 40321, 100, 0); // ��伮

			createNewItem2(pc, 110111, 1, 7); // ������ ����
			createNewItem2(pc, 21103, 1, 7); // �������� �߰��� �尩
			createNewItem2(pc, 21104, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21105, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21106, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21107, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 500215, 1, 7); // ������ ����
			createNewItem2(pc, 21099, 1, 5); // �������� �߰��� ��Ʈ
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21101, 1, 5); // �������� �߰��� �����
			createNewItem2(pc, 21032, 1, 7); // Ƽ����
			createNewItem2(pc, 21005, 1, 5); // ������ �Ͱ���
		} else if (pc.isIllusionist()) {
			createNewItem2(pc, 450035, 1, 6); // ������ Ű��ũ
			createNewItem2(pc, 430006, 2, 0); // ���׵�� ����
			createNewItem2(pc, 430008, 100, 0); // �Ӽ���

			createNewItem2(pc, 110111, 1, 7); // ������ ����
			createNewItem2(pc, 21108, 1, 7); // �������� �ż��� �尩
			createNewItem2(pc, 21109, 1, 7); // �������� �ż��� ����
			createNewItem2(pc, 21110, 1, 7); // �������� �ż��� ����
			createNewItem2(pc, 21111, 1, 7); // �������� �ż��� �κ�
			createNewItem2(pc, 21112, 1, 7); // �������� �ż��� ����
			createNewItem2(pc, 500216, 1, 7); // ������ ����
			createNewItem2(pc, 21099, 1, 5); // �������� �߰��� ��Ʈ
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21101, 1, 5); // �������� �߰��� �����
			createNewItem2(pc, 21032, 1, 7); // Ƽ����
			createNewItem2(pc, 21005, 1, 5); // ������ �Ͱ���
		} else if (pc.isDragonknight()) {
			createNewItem2(pc, 450034, 1, 7); // ������ ü�μҵ�
			createNewItem2(pc, 430007, 100, 0); // ������ ������

			createNewItem2(pc, 110111, 1, 6); // ������ ����
			createNewItem2(pc, 21103, 1, 7); // �������� �߰��� �尩
			createNewItem2(pc, 21104, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21105, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21106, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21107, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 500215, 1, 7); // ������ ����
			createNewItem2(pc, 21099, 1, 5); // �������� �߰��� ��Ʈ
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21101, 1, 5); // �������� �߰��� �����
			createNewItem2(pc, 21032, 1, 7); // Ƽ����
			createNewItem2(pc, 21005, 1, 5); // ������ �Ͱ���
		} else if (pc.isCrown()) {
			createNewItem2(pc, 450031, 1, 6); // ������ �Ѽհ�
			createNewItem2(pc, 70039, 1, 0); // ���� �Ǹ��� ����

			createNewItem2(pc, 21102, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21103, 1, 7); // �������� �߰��� �尩
			createNewItem2(pc, 21104, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21105, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21106, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 21107, 1, 7); // �������� �߰��� ����
			createNewItem2(pc, 500215, 1, 7); // ������ ����
			createNewItem2(pc, 21099, 1, 5); // �������� �߰��� ��Ʈ
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21100, 1, 5); // �������� �߰��� ����
			createNewItem2(pc, 21101, 1, 5); // �������� �߰��� �����
			createNewItem2(pc, 21032, 1, 7); // Ƽ����
			createNewItem2(pc, 21005, 1, 5); // ������ �Ͱ���
		}
	}

	private void �׽�Ʈ�����(L1PcInstance pc) {

		// pc.getInventory().storeItem(L1ItemId.ADENA, 50000000);
		createNewItem2(pc, 7258, 1, 0, 0, 0); // �Ĵ�
		if (pc.isKnight()) {
			createNewItem2(pc, 276, 1, 8); // �Ĵ�
			createNewItem2(pc, 9, 1, 8); // ����
			createNewItem2(pc, 120011, 1, 7); // ����
			createNewItem2(pc, 120056, 1, 7); // ����
			createNewItem2(pc, 20085, 1, 7); // Ƽ����
			createNewItem2(pc, 20194, 1, 7); // ����
			createNewItem2(pc, 20187, 1, 7); // �ı�
			createNewItem2(pc, 20110, 1, 7); // ����
			createNewItem2(pc, 420003, 1, 0); // ������

			createNewItem2(pc, 21022, 1, 0); // ������
			createNewItem2(pc, 20264, 1, 0); // �Ϸ�
			createNewItem2(pc, 20321, 1, 0); // Ʈ��
			createNewItem2(pc, 20280, 1, 0); // �긶
			createNewItem2(pc, 20280, 1, 0); // �긶
		} else if (pc.isWarrior()) {
			createNewItem2(pc, 7228, 1, 8); // ��ǳ�� ����
			createNewItem2(pc, 7228, 1, 8); // ��ǳ�� ����
			createNewItem2(pc, 7247, 1, 7); // ������� ����
			createNewItem2(pc, 20074, 1, 5); // ����
			createNewItem2(pc, 20085, 1, 7); // Ƽ����
			createNewItem2(pc, 20194, 1, 7); // ����
			createNewItem2(pc, 20187, 1, 7); // �ı�
			createNewItem2(pc, 20110, 1, 7); // ����
			createNewItem2(pc, 420003, 1, 0); // ������

			createNewItem2(pc, 21022, 1, 0); // ������
			createNewItem2(pc, 20264, 1, 0); // �Ϸ�
			createNewItem2(pc, 20321, 1, 0); // Ʈ��
			createNewItem2(pc, 20280, 1, 0); // �긶
			createNewItem2(pc, 20280, 1, 0); // �긶
		} else if (pc.isElf()) {
			createNewItem2(pc, 191, 1, 8); // ��õ

			createNewItem2(pc, 120011, 1, 7); // ����
			createNewItem2(pc, 120056, 1, 7); // ����
			createNewItem2(pc, 20085, 1, 7); // Ƽ
			createNewItem2(pc, 20194, 1, 7); // ����
			createNewItem2(pc, 20110, 1, 7); // ����
			createNewItem2(pc, 20191, 1, 7); // ��
			createNewItem2(pc, 420000, 1, 0); // ����
			createNewItem2(pc, 21022, 1, 0); // ������
			createNewItem2(pc, 20256, 1, 0); // �θ�
			createNewItem2(pc, 20321, 1, 0); // Ʈ��
			createNewItem2(pc, 20280, 1, 0); // �긶
			createNewItem2(pc, 20280, 1, 0); // �긶
		} else if (pc.isWizard()) {
			createNewItem2(pc, 119, 1, 0, 0, 0); // ����������
			createNewItem2(pc, 120011, 1, 7); // ����
			createNewItem2(pc, 120056, 1, 7); // ����
			createNewItem2(pc, 20085, 1, 7); // Ƽ����
			createNewItem2(pc, 20093, 1, 0); // ���
			createNewItem2(pc, 21097, 1, 3); // �������ǰ���
			createNewItem2(pc, 20194, 1, 7); // ����
			createNewItem2(pc, 20187, 1, 7); // �ı�
			createNewItem2(pc, 20266, 1, 0); // ����
			createNewItem2(pc, 21022, 1, 0); // ������
			createNewItem2(pc, 20319, 1, 0); // �����Ǻ�Ʈ
			createNewItem2(pc, 20280, 1, 0); // �긶
			createNewItem2(pc, 20280, 1, 0); // �긶
		} else if (pc.isDarkelf()) {
			createNewItem2(pc, 260, 1, 8); // ����

			createNewItem2(pc, 120011, 1, 7); // ����
			createNewItem2(pc, 120056, 1, 7); // ����
			createNewItem2(pc, 20085, 1, 7); // Ƽ
			createNewItem2(pc, 20194, 1, 7); // ����
			createNewItem2(pc, 20110, 1, 7); // ����
			createNewItem2(pc, 20187, 1, 7); // �ı�
			createNewItem2(pc, 420003, 1, 0); // ����

			createNewItem2(pc, 21022, 1, 0); // ������
			createNewItem2(pc, 20264, 1, 0); // �Ϸ�
			createNewItem2(pc, 20321, 1, 0); // Ʈ��
			createNewItem2(pc, 20280, 1, 0); // �긶
			createNewItem2(pc, 20280, 1, 0); // �긶
		} else if (pc.isIllusionist()) {
			createNewItem2(pc, 266, 1, 8); // ��Ű

			createNewItem2(pc, 120011, 1, 7); // ����
			createNewItem2(pc, 120056, 1, 7); // ����
			createNewItem2(pc, 20085, 1, 7); // Ƽ����
			createNewItem2(pc, 420006, 1, 7); // ȯ��
			createNewItem2(pc, 20194, 1, 7); // ����
			createNewItem2(pc, 20187, 1, 7); // �ı�
			createNewItem2(pc, 20093, 1, 0); // ���
			createNewItem2(pc, 21022, 1, 0); // ������
			createNewItem2(pc, 20266, 1, 0); // ����
			createNewItem2(pc, 20321, 1, 0); // Ʈ��
			createNewItem2(pc, 20280, 1, 0); // �긶
			createNewItem2(pc, 20280, 1, 0); // �긶
		} else if (pc.isDragonknight()) {
			createNewItem2(pc, 262, 1, 8); // ��

			createNewItem2(pc, 120011, 1, 7); // ����
			createNewItem2(pc, 120056, 1, 7); // ����
			createNewItem2(pc, 20085, 1, 7); // Ƽ����
			createNewItem2(pc, 20194, 1, 7); // ����
			createNewItem2(pc, 20187, 1, 7); // �ı�
			createNewItem2(pc, 20110, 1, 7); // ����
			createNewItem2(pc, 420003, 1, 0); // ������簡��

			createNewItem2(pc, 21022, 1, 0); // ������
			createNewItem2(pc, 20264, 1, 0); // �Ϸ�
			createNewItem2(pc, 20321, 1, 0); // Ʈ��
			createNewItem2(pc, 20280, 1, 0); // �긶
			createNewItem2(pc, 20280, 1, 0); // �긶
		} else if (pc.isCrown()) {
			createNewItem2(pc, 412000, 1, 8); // ����

			createNewItem2(pc, 120011, 1, 7); // ����
			createNewItem2(pc, 120056, 1, 7); // ����
			createNewItem2(pc, 20085, 1, 7); // Ƽ����
			createNewItem2(pc, 20194, 1, 7); // ����
			createNewItem2(pc, 20187, 1, 7); // �ı�
			createNewItem2(pc, 20110, 1, 7); // ����
			createNewItem2(pc, 20235, 1, 5); // ����

			createNewItem2(pc, 21022, 1, 0); // ������
			createNewItem2(pc, 20264, 1, 0); // �Ϸ�
			createNewItem2(pc, 20321, 1, 0); // Ʈ��
			createNewItem2(pc, 20280, 1, 0); // �긶
			createNewItem2(pc, 20280, 1, 0); // �긶
		}
	}

	private void �⸣Ÿ���ǻ��TOTAL(L1PcInstance pc, int itemId) {

		int i = 0;
		if (itemId == 60311)
			i = �⸣Ÿ�����(pc);
		else
			i = �����ѱ⸣Ÿ�����(pc);
		if (i != 0) {
			int count = 1;
			if (i == 40087 || i == 40074 || i == 140087 || i == 240087 || i == 140074 || i == 240074)
				count = 10;
			else if (i >= 40048 && i <= 40055)
				count = 30;
			else if (i == 430024)
				count = 3;
			else if (i >= 40304 && i <= 40307)
				count = 50;
			else if (i == 40498 || i == 40491)
				count = 5;
			L1ItemInstance item = pc.getInventory().storeItem(i, count);
			pc.sendPackets(new S_ServerMessage(403, item.getName()));
		}
	}

	private void ��Ƽ������������������(L1PcInstance pc) {

		L1ItemInstance item = null;
		if (pc.isCrown()) {
			if (pc.get_sex() == 0)
				item = pc.getInventory().storeItem(60454, 1);
			else
				item = pc.getInventory().storeItem(60447, 1);
		} else if (pc.isKnight()) {
			if (pc.get_sex() == 0)
				item = pc.getInventory().storeItem(60455, 1);
			else
				item = pc.getInventory().storeItem(60448, 1);
		} else if (pc.isWizard()) {
			if (pc.get_sex() == 0)
				item = pc.getInventory().storeItem(60456, 1);
			else
				item = pc.getInventory().storeItem(60449, 1);
		} else if (pc.isElf()) {
			if (pc.get_sex() == 0)
				item = pc.getInventory().storeItem(60457, 1);
			else
				item = pc.getInventory().storeItem(60450, 1);
		} else if (pc.isDarkelf()) {
			if (pc.get_sex() == 0)
				item = pc.getInventory().storeItem(60458, 1);
			else
				item = pc.getInventory().storeItem(60451, 1);
		} else if (pc.isDragonknight()) {
			if (pc.get_sex() == 0)
				item = pc.getInventory().storeItem(60459, 1);
			else
				item = pc.getInventory().storeItem(60452, 1);
		} else if (pc.isIllusionist()) {
			if (pc.get_sex() == 0)
				item = pc.getInventory().storeItem(60460, 1);
			else
				item = pc.getInventory().storeItem(60453, 1);
		}
		if (item != null)
			pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
	}

	private void �ű��ѹ���4������(L1PcInstance pc, int itemId) {

		if (itemId == 60373) { // �������� 4������
			pc.getInventory().consumeItem(60373, 1); // �����Ǵ� �����۰� ����
			for (int i = 0; i < 4; i++) {
				int ran = _random.nextInt(100) + 1;
				if (ran <= 5) {
					createNewItem2(pc, 625113, 1, 0); // ��Ƽ����ȭ�ܼ�
				} else
					createNewItem2(pc, 525113, 1, 0); // ��Ƽ����ȭ�ܼ�
			}
			createNewItem2(pc, 530040, 32, 0); // ��Ƽ����ȭ�ܼ�
		} else if (itemId == 60374) { // ���� 4������
			pc.getInventory().consumeItem(60374, 1); // �����Ǵ� �����۰� ����
			for (int i = 0; i < 4; i++) {
				int ran = _random.nextInt(100) + 1;
				if (ran <= 5) {
					createNewItem2(pc, 625112, 1, 0); // ��Ƽ����ȭ�ܼ�
				} else
					createNewItem2(pc, 525112, 1, 0); // ��Ƽ����ȭ�ܼ�
			}
			createNewItem2(pc, 530040, 32, 0); // ��Ƽ����ȭ�ܼ�
		} else if (itemId == 60375) { // ü�� 4������
			pc.getInventory().consumeItem(60375, 1); // �����Ǵ� �����۰� ����
			for (int i = 0; i < 4; i++) {
				int ran = _random.nextInt(100) + 1;
				if (ran <= 5) {
					createNewItem2(pc, 625111, 1, 0); // ��Ƽ����ȭ�ܼ�
				} else
					createNewItem2(pc, 525111, 1, 0); // ��Ƽ����ȭ�ܼ�
			}
			createNewItem2(pc, 530040, 32, 0); // ��Ƽ����ȭ�ܼ�
		} else if (itemId == 60376) { // ȸ�� 4������
			pc.getInventory().consumeItem(60376, 1); // �����Ǵ� �����۰� ����
			for (int i = 0; i < 4; i++) {
				int ran = _random.nextInt(100) + 1;
				if (ran <= 5) {
					createNewItem2(pc, 625109, 1, 0); // ��Ƽ����ȭ�ܼ�
				} else
					createNewItem2(pc, 525109, 1, 0); // ��Ƽ����ȭ�ܼ�
			}
			createNewItem2(pc, 530040, 32, 0); // ��Ƽ����ȭ�ܼ�
		} else if (itemId == 60377) { // ���� 4������
			pc.getInventory().consumeItem(60377, 1); // �����Ǵ� �����۰� ����
			for (int i = 0; i < 4; i++) {
				int ran = _random.nextInt(100) + 1;
				if (ran <= 5) {
					createNewItem2(pc, 625110, 1, 0); // ��Ƽ����ȭ�ܼ�
				} else
					createNewItem2(pc, 525110, 1, 0); // ��Ƽ����ȭ�ܼ�
			}
			createNewItem2(pc, 530040, 32, 0); // ��Ƽ����ȭ�ܼ�
		}
	}

	private void �������ǹ���(L1PcInstance pc, int itemId, L1ItemInstance useItem) {

		if (itemId == 60413) { // ������ ���� 4������
			pc.getInventory().removeItem(useItem, 1);
			for (int i = 0; i < 4; i++) {
				int ran = _random.nextInt(100) + 1;
				if (ran <= 5) {
					createNewItem2(pc, 21250, 1, 0); // �ູ
				} else
					createNewItem2(pc, 21246, 1, 0);
			}
			createNewItem2(pc, 60417, 32, 0); // ������ ���� ��ȭ�ܼ�
		} else if (itemId == 60414) { // ������ ���� ���� 4������
			pc.getInventory().removeItem(useItem, 1);
			for (int i = 0; i < 4; i++) {
				int ran = _random.nextInt(100) + 1;
				if (ran <= 5) {
					createNewItem2(pc, 21251, 1, 0); // �ູ
				} else
					createNewItem2(pc, 21247, 1, 0);
			}
			createNewItem2(pc, 60417, 32, 0); // ������ ���� ��ȭ�ܼ�
		} else if (itemId == 60415) { // ������ ü�� 4������
			pc.getInventory().removeItem(useItem, 1);
			for (int i = 0; i < 4; i++) {
				int ran = _random.nextInt(100) + 1;
				if (ran <= 5) {
					createNewItem2(pc, 21252, 1, 0); // �ູ
				} else
					createNewItem2(pc, 21248, 1, 0);
			}
			createNewItem2(pc, 60417, 32, 0); // ������ ���� ��ȭ�ܼ�
		} else if (itemId == 60416) { // ������ ��� 4������
			pc.getInventory().removeItem(useItem, 1);
			for (int i = 0; i < 4; i++) {
				int ran = _random.nextInt(100) + 1;
				if (ran <= 5) {
					createNewItem2(pc, 21253, 1, 0); // �ູ
				} else
					createNewItem2(pc, 21249, 1, 0);
			}
			createNewItem2(pc, 60417, 32, 0); // ������ ���� ��ȭ�ܼ�
		} else if (itemId == 60418) { // ������ ����
			pc.getInventory().removeItem(useItem, 1);
			int ran = _random.nextInt(100) + 1;
			if (ran <= 5) {
				createNewItem2(pc, 21250, 1, 0); // �ູ
			} else
				createNewItem2(pc, 21246, 1, 0);
			createNewItem2(pc, 60417, 8, 0); // ������ ���� ��ȭ�ܼ�
		} else if (itemId == 60419) { // ������ ���� ����
			pc.getInventory().removeItem(useItem, 1);
			int ran = _random.nextInt(100) + 1;
			if (ran <= 5) {
				createNewItem2(pc, 21251, 1, 0); // �ູ
			} else
				createNewItem2(pc, 21247, 1, 0);
			createNewItem2(pc, 60417, 8, 0); // ������ ���� ��ȭ�ܼ�
		} else if (itemId == 60420) { // ������ ü��
			pc.getInventory().removeItem(useItem, 1);
			int ran = _random.nextInt(100) + 1;
			if (ran <= 5) {
				createNewItem2(pc, 21252, 1, 0); // �ູ
			} else
				createNewItem2(pc, 21248, 1, 0);
			createNewItem2(pc, 60417, 8, 0); // ������ ���� ��ȭ�ܼ�
		} else if (itemId == 60421) { // ������ ���
			pc.getInventory().removeItem(useItem, 1);
			int ran = _random.nextInt(100) + 1;
			if (ran <= 5) {
				createNewItem2(pc, 21253, 1, 0); // �ູ
			} else
				createNewItem2(pc, 21249, 1, 0);
			createNewItem2(pc, 60417, 8, 0); // ������ ���� ��ȭ�ܼ�
		}
	}

	private void ����������(L1PcInstance pc, L1ItemInstance useItem) {

		Timestamp lastUsed = useItem.getLastUsed();
		if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (60 * 60000 * 2)) {
			useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
			// int count = 50 + _random.nextInt(51);
			L1ItemInstance item = pc.getInventory().storeItem(400075, 3);
			pc.sendPackets(new S_ServerMessage(403, item.getName() + " (" + 3 + ")"), true);

			pc.getInventory().removeItem(useItem, 1);
		} else {
			long i = (lastUsed.getTime() + (60 * 60000 * 2)) - currentDate.getTimeInMillis();
			Calendar cal = (Calendar) currentDate.clone();
			cal.setTimeInMillis(cal.getTimeInMillis() + i);
			pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����(" + cal.getTime().getHours() + ":"
					+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
		}
	}

	private void �������������ɼ��ָӴ�(L1PcInstance pc, L1ItemInstance useItem) {

		Timestamp lastUsed = useItem.getLastUsed();
		if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (1000 * 60 * 60 * 22)) {// 22�ð�
			if (pc.getInventory().checkItem(60392)) {
				pc.sendPackets(new S_SystemMessage("Ư�� �������� �κ��丮�� �̹� �־� �� ���� �� �����ϴ�."));
				return;
			}
			pc.getInventory().storeItem(60392, 1);
			pc.sendPackets(new S_ServerMessage(403, "���� ������ ���ɼ�"), true);
			pc.getInventory().consumeItem(60391, 1);
			useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
		} else {
			long i = (lastUsed.getTime() + (60 * 60000 * 22)) - currentDate.getTimeInMillis();
			Calendar cal = (Calendar) currentDate.clone();
			cal.setTimeInMillis(cal.getTimeInMillis() + i);
			pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����(" + cal.getTime().getHours() + ":"
					+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
		}
	}

	private void �鸶��������(L1PcInstance pc, L1ItemInstance useItem, L1ItemInstance targetItem) {
		if (targetItem == null)
			return;
		if ((targetItem.getItemId() >= 21207 && targetItem.getItemId() <= 21241)
				|| (targetItem.getItemId() >= 9000 && targetItem.getItemId() <= 9004)
				|| (targetItem.getItemId() >= 9010 && targetItem.getItemId() <= 9014)
				|| (targetItem.getItemId() >= 9020 && targetItem.getItemId() <= 9024)
				|| (targetItem.getItemId() >= 9030 && targetItem.getItemId() <= 9034)
				|| (targetItem.getItemId() >= 9040 && targetItem.getItemId() <= 9044)
				|| (targetItem.getItemId() >= 9050 && targetItem.getItemId() <= 9054)
				|| (targetItem.getItemId() >= 9060 && targetItem.getItemId() <= 9064)
				|| (targetItem.getItemId() >= 9070 && targetItem.getItemId() <= 9074)) {
			pc.getInventory().removeItem(targetItem, 1);
			pc.getInventory().storeItem(60385, 1, true);
			pc.getInventory().removeItem(useItem, 1);
		} else
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
	}

	private void ��������(L1PcInstance pc, L1ItemInstance useItem, L1ItemInstance targetItem) {
		if (targetItem == null)
			return;
		if (targetItem.getItemId() >= 9075 && targetItem.getItemId() <= 9093) {
			int storeid = 0;
			switch (targetItem.getItemId()) {
			case 9075:
				storeid = 7228;
				break;
			case 9076:
				storeid = 275;
				break;
			case 9077:
				storeid = 266;
				break;
			case 9078:
				storeid = 276;
				break;
			case 9079:
				storeid = 412000;
				break;
			case 9080:
				storeid = 191;
				break;
			case 9081:
				storeid = 259;
				break;
			case 9082:
				storeid = 119;
				break;

			case 9083:
				storeid = 7247;
				break;
			case 9084:
				storeid = 20074;
				break;
			case 9085:
				storeid = 20093;
				break;
			case 9086:
				storeid = 20095;
				break;
			case 9087:
				storeid = 20187;
				break;
			case 9088:
				storeid = 20194;
				break;
			case 9089:
				storeid = 20085;
				break;
			case 9090:
				storeid = 20092;
				break;
			case 9091:
				storeid = 120011;
				break;
			case 9092:
				storeid = 120056;
				break;
			case 9093:
				storeid = 20094;
				break;
			}
			if (storeid != 0) {
				pc.getInventory().removeItem(targetItem, 1);
				createNewItem2(pc, storeid, 1, targetItem.getEnchantLevel()); // ����
																				// ����..
				pc.getInventory().removeItem(useItem, 1);
			}
		} else
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
	}

	private boolean ���Ȯ�屸��(L1PcInstance pc) {

		if (pc.getBookmarkMax() >= 100) {
			pc.sendPackets(new S_ServerMessage(2962), true);
			return false;
		}
		pc.setBookmarkMax(pc.getBookmarkMax() + 10);
		pc.sendPackets(new S_PacketBox(S_PacketBox.���â_Ȯ��, pc.getBookmarkMax()), true);
		return true;
	}

	private void ����Ű(L1PcInstance pc, L1ItemInstance useItem) {

		if (pc.getMap().isEscapable()) {
			int keymap = 0;
			if (useItem.getEndTime().getTime() > System.currentTimeMillis()) {
				keymap = (short) useItem.getKey();
			}
			if (keymap == 0) {
				pc.sendPackets(new S_SystemMessage("�Ⱓ�� ���� ���� ���� �Դϴ�."));
				return;
			}
			if (keymap >= 16384 && keymap <= 16684) {
				L1Teleport.teleport(pc, 32746, 32803, (short) keymap, 5, true);// ����
																				// ��
			} else if (keymap >= 16896 && keymap <= 17196) {
				L1Teleport.teleport(pc, 32744, 32808, (short) keymap, 5, true);// ����
																				// Ȧ
			} else if (keymap >= 17408 && keymap <= 17708) {
				L1Teleport.teleport(pc, 32744, 32803, (short) keymap, 5, true); // �۸�
																				// ��
			} else if (keymap >= 17920 && keymap <= 18220) {
				L1Teleport.teleport(pc, 32745, 32807, (short) keymap, 5, true);// �۸�
																				// Ȧ
			} else if (keymap >= 18432 && keymap <= 18732) {
				L1Teleport.teleport(pc, 32745, 32803, (short) keymap, 5, true);// ���
																				// ��
			} else if (keymap >= 18944 && keymap <= 19244) {
				L1Teleport.teleport(pc, 32745, 32807, (short) keymap, 5, true);// ���
																				// Ȧ
			} else if (keymap >= 19456 && keymap <= 19756) {
				L1Teleport.teleport(pc, 32745, 32803, (short) keymap, 5, true);// �Ƶ�
																				// ��
			} else if (keymap >= 19968 && keymap <= 20268) {
				L1Teleport.teleport(pc, 32745, 32807, (short) keymap, 5, true);// �Ƶ�
																				// Ȧ
			} else if (keymap >= 23552 && keymap <= 23852) {
				L1Teleport.teleport(pc, 32745, 32803, (short) keymap, 5, true);// ����
																				// ��
			} else if (keymap >= 24064 && keymap <= 24364) {
				L1Teleport.teleport(pc, 32745, 32807, (short) keymap, 5, true);// ����
																				// Ȧ
			} else if (keymap >= 20480 && keymap <= 20780) {
				L1Teleport.teleport(pc, 32745, 32803, (short) keymap, 5, true); // ����
																				// ��
			} else if (keymap >= 20992 && keymap <= 21292) {
				L1Teleport.teleport(pc, 32745, 32807, (short) keymap, 5, true);// ����
																				// Ȧ
			} else if (keymap >= 21504 && keymap <= 21804) {
				L1Teleport.teleport(pc, 32745, 32803, (short) keymap, 5, true);// �����
																				// ��
			} else if (keymap >= 22016 && keymap <= 22316) {
				L1Teleport.teleport(pc, 32745, 32807, (short) keymap, 5, true);// �����
																				// Ȧ
			} else if (keymap >= 22528 && keymap <= 22828) {
				L1Teleport.teleport(pc, 32745, 32803, (short) keymap, 5, true);// ���̳�
																				// ��
			} else if (keymap >= 23040 && keymap <= 23340) {
				L1Teleport.teleport(pc, 32745, 32807, (short) keymap, 5, true);// ���̳�
																				// Ȧ
			} else if (keymap >= 24576 && keymap <= 24876) {
				L1Teleport.teleport(pc, 32745, 32803, (short) keymap, 5, true);// ������
																				// ��
			} else if (keymap >= 25088 && keymap <= 25388) {
				L1Teleport.teleport(pc, 32745, 32807, (short) keymap, 5, true);// ������
																				// Ȧ
			}
		}
	}

	private void ����Ƽ����(L1PcInstance pc, int itemId, L1ItemInstance useItem) {

		int itemid = ((itemId - 60361) * 2) + 21183;
		if (_random.nextInt(100) < 20)
			itemid += 1;
		L1ItemInstance item = pc.getInventory().storeItem(itemid, 1);
		pc.sendPackets(new S_ServerMessage(403, item.getName()), true);
		item = pc.getInventory().storeItem(430041, 10);
		pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10)"), true);
		item = pc.getInventory().storeItem(1430041, 10);
		pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10)"), true);
		item = pc.getInventory().storeItem(2430041, 10);
		pc.sendPackets(new S_ServerMessage(403, item.getName() + " (10)"), true);
		pc.getInventory().removeItem(useItem, 1);
	}

	private void �����Ǹ�(L1PcInstance pc, L1ItemInstance useItem, L1ItemInstance targetItem) {

		if (targetItem == null)
			return;
		int targetItemId = targetItem.getItemId();
		int useItemId = useItem.getItemId();
		if ((targetItemId == 600229 && useItemId == 600228)) {
			/*
			 * if(targetItem.getChargeCount() > 400){ pc.sendPackets(new
			 * S_ServerMessage(3457), true); return; }
			 */
			targetItem.setChargeCount(targetItem.getChargeCount() + 100);
			pc.getInventory().updateItem(targetItem, L1PcInventory.COL_CHARGE_COUNT);
			pc.getInventory().removeItem(useItem, 1);
			return;
		}
		if (targetItemId != 60326)
			return;
		if (pc.isFishing()) {
			pc.setFishingTime(0);
			pc.setFishingReady(false);
			pc.setFishing(false);
			pc.setFishingItem(null);
			pc.sendPackets(new S_CharVisualUpdate(pc), true);
			Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc), true);
			FishingTimeController.getInstance().removeMember(pc);
		}
		pc.getInventory().removeItem(targetItem, 1);
		pc.getInventory().storeItem(600229, 1, true);
		pc.getInventory().removeItem(useItem, 1);
	}

	private void ������Ӹ�(L1PcInstance pc, L1ItemInstance useItem, L1ItemInstance targetItem) {

		if (targetItem == null)
			return;
		int targetItemId = targetItem.getItemId();
		int useItemId = useItem.getItemId();
		if ((targetItemId == 60334 && useItemId == 60333) || (targetItemId == 60478 && useItemId == 60476)
				|| (targetItemId == 60479 && useItemId == 60477)) {
			if (targetItem.getChargeCount() > 400) {
				pc.sendPackets(new S_ServerMessage(3457), true);
				return;
			}
			if (targetItemId == 60478 || targetItemId == 60479)
				targetItem.setChargeCount(targetItem.getChargeCount() + 50);
			else
				targetItem.setChargeCount(targetItem.getChargeCount() + 100);
			pc.getInventory().updateItem(targetItem, L1PcInventory.COL_CHARGE_COUNT);
			pc.getInventory().removeItem(useItem, 1);
			return;
		}
		if (targetItemId != 60326)
			return;
		if (pc.isFishing()) {
			pc.setFishingTime(0);
			pc.setFishingReady(false);
			pc.setFishing(false);
			pc.setFishingItem(null);
			pc.sendPackets(new S_CharVisualUpdate(pc), true);
			Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc), true);
			FishingTimeController.getInstance().removeMember(pc);
		}
		pc.getInventory().removeItem(targetItem, 1);
		if (useItemId == 60476)
			pc.getInventory().storeItem(60478, 1, true);
		else if (useItemId == 60477)
			pc.getInventory().storeItem(60479, 1, true);
		else
			pc.getInventory().storeItem(60334, 1, true);
		pc.getInventory().removeItem(useItem, 1);
	}

	private int �����ѱ⸣Ÿ�����(L1PcInstance pc) {

		int rnd = _random.nextInt(100) + 1;
		int itemid = 0;
		if (rnd <= 1) // ��
			itemid = 40222;
		else if (rnd <= 2) // ī��
			itemid = 41148;
		else if (rnd <= 10)
			itemid = ������[_random.nextInt(������.length)];
		else if (rnd <= 40)
			itemid = ������[_random.nextInt(������.length)];
		else
			itemid = ��������[_random.nextInt(��������.length)];
		return itemid;
	}

	private static final int[] ������ = { 40087, 140087, 240087, 40074, 140074, 240074 };
	private static final int[] ������ = { 30229, 20422, 20071, 20059, 20061, 20054, 20077, 30219, 20187, 30218, 266, 261,
			262, 42, 190 };
	private static final int[] �������� = { 430024, 40304, 40305, 40306, 40307, 40498, 40491, 40048, 40049, 40050, 40051,
			40052, 40053, 40054, 40055 };

	private int �⸣Ÿ�����(L1PcInstance pc) {

		int rnd = _random.nextInt(1000) + 1;
		int itemid = 0;
		if (rnd <= 1) // ��
			itemid = 40222;
		else if (rnd <= 2) // ī��
			itemid = 41148;
		else if (rnd <= 100)
			itemid = ������[_random.nextInt(������.length)];
		else if (rnd <= 400)
			itemid = ������[_random.nextInt(������.length)];
		else
			itemid = ��������[_random.nextInt(��������.length)];
		return itemid;
	}

	private int ��Ƽ������(L1PcInstance pc) {

		int rnd = _random.nextInt(100) + 1;
		int itemid = 0;
		if (rnd <= 10) {// ������������
			itemid = 500208;
		} else if (rnd <= 55) {
			itemid = 600219;
		} else {
			itemid = 600220;
		}
		if (itemid != 0) {
			L1ItemInstance item = pc.getInventory().storeItem(itemid, 1);
			pc.sendPackets(new S_ServerMessage(403, item.getName()));
		}
		return itemid;
	}

	private int ��Ƽ������(L1PcInstance pc) {

		int rnd = _random.nextInt(100) + 1;
		int itemid = 0;
		int count = 1;
		if (rnd <= 25) {// �����ָӴ�
			itemid = 500206;
		} else if (rnd <= 35) {// ���
			itemid = 437010;
		} else if (rnd <= 45) {// ������2
			itemid = 600224;
			count = 2;
		} else if (rnd <= 55) {// ����2
			itemid = 437004;
			count = 2;
		} else if (rnd <= 65) {// �������5
			itemid = 560027;
			count = 5;
		} else if (rnd <= 75) {// �����̵�
			itemid = 60360;
			count = 5;
		} else if (rnd <= 80) {// ����
			itemid = 430000;
		} else if (rnd <= 85) {// �����ΰ�
			itemid = 41250;
		} else if (rnd <= 90) {// ���׺���
			itemid = 41248;
		} else if (rnd <= 95) {// ũ����Ʈ�þ�
			itemid = 430002;
		} else if (rnd <= 100) {// ũ����Ʈ�þ�
			itemid = 430004;
		}
		if (itemid != 0) {
			L1ItemInstance item = pc.getInventory().storeItem(itemid, count);
			pc.sendPackets(new S_ServerMessage(403, item.getName() + " (" + count + ")��"));
		}
		return itemid;
	}

	private int ��Ÿ�縻(L1PcInstance pc) {

		int rnd = _random.nextInt(1000000) + 1;
		int itemid = 0;
		if (rnd <= 103000) {// ������������
			itemid = 500208;
		} else if (rnd <= 178000) {// ���̵� �����
			itemid = 467009;
		} else if (rnd <= 253000) {// ���� ��ȯ ����
			itemid = 437001;
		} else if (rnd <= 613000) {// ���ø����� ��ǥ
			itemid = 141917;
			/*
			 * ȭ���� ��� 13% ������ ���̵� : 6022
			 * 
			 * ������ ��� 13% ������ ���̵� : 7337
			 * 
			 * ��ȯ �׸������� �����ָӴ� 13.2
			 */

		} else if (rnd <= 743000) {// ȭ��
			itemid = 6022;
		} else if (rnd <= 873000) {// ����
			itemid = 7337;
		} else if (rnd <= 995000) {// ���� �ָӴ�
			itemid = 500207;

		} else if (rnd <= 999993) {// ǻ��
			itemid = 500209;
		} else if (rnd <= 999994) {// Ŀ��
			itemid = 54;
		} else if (rnd <= 999995) {// ����
			itemid = 58;
		} else if (rnd <= 999996) {// ����
			itemid = 293;
		} else if (rnd <= 999997) {// ����
			itemid = 292;
		} else if (rnd <= 999998) {// ��Ű
			itemid = 6001;
		} else if (rnd <= 999999) {// ��ü
			itemid = 6000;
		} else if (rnd <= 1000000) {// ���ν�
			itemid = 291;
		}
		if (itemid != 0) {
			L1ItemInstance item = pc.getInventory().storeItem(itemid, 1);
			pc.sendPackets(new S_ServerMessage(403, item.getName()));
		}
		return itemid;
	}

	private void �ƸӺ극��ũ(L1PcInstance pc, L1ItemInstance useItem, int spellsc_objid) {
		Timestamp lastUsed = useItem.getLastUsed();
		if (!pc.isSkillDelay()
				&& (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (1000 * 5))) {
			L1Object temp = L1World.getInstance().findObject(spellsc_objid);
			if (temp != null && temp != pc && (pc.isDarkelf() || pc.isGm())) {
				if (temp instanceof L1Character) {
					L1Character tempCha = (L1Character) temp;
					if (pc.getCurrentHp() < 30) {
						pc.sendPackets(new S_ServerMessage(279), true);
					} else if (pc.getCurrentMp() < 40) {
						pc.sendPackets(new S_ServerMessage(278), true);
					} else if (!pc.getInventory().checkItem(40321, 2) && !pc.isGm()) {
						pc.sendPackets(new S_ServerMessage(299), true);
					} else {
						pc.setCurrentHp(pc.getCurrentHp() - 30);
						pc.setCurrentMp(pc.getCurrentMp() - 40);
						pc.getInventory().consumeItem(40321, 2);
						int level_dif = pc.getLevel() - tempCha.getLevel();
						int chance = 0;
						if (level_dif <= -10)
							chance = 1;
						else if (level_dif == -9)
							chance = 2;
						else if (level_dif == -8)
							chance = 3;
						else if (level_dif == -7)
							chance = 5;
						else if (level_dif == -6)
							chance = 10;
						else if (level_dif == -5)
							chance = 15;
						else if (level_dif == -4)
							chance = 20;
						else if (level_dif == -3)
							chance = 25;
						else if (level_dif == -2)
							chance = 30;
						else if (level_dif == -1)
							chance = 35;
						else if (level_dif == 0)
							chance = 40;
						else if (level_dif == 1)
							chance = 45;
						else if (level_dif == 2)
							chance = 50;
						else if (level_dif == 3)
							chance = 55;
						else if (level_dif == 4)
							chance = 60;
						else if (level_dif == 5)
							chance = 65;
						else if (level_dif == 6)
							chance = 70;
						else if (level_dif == 7)
							chance = 75;
						else if (level_dif == 8)
							chance = 80;
						else if (level_dif == 9)
							chance = 85;
						else if (level_dif >= 10)
							chance = 90;
						if (chance >= _random.nextInt(100) + 1) {
							if (tempCha.getSkillEffectTimerSet().hasSkillEffect(ARMOR_BREAK)) {
								tempCha.getSkillEffectTimerSet().killSkillEffectTimer(ARMOR_BREAK);
							}
							tempCha.getSkillEffectTimerSet().setSkillEffect(ARMOR_BREAK, 8 * 1000);
							if (tempCha instanceof L1PcInstance) {
								((L1PcInstance) tempCha).sendPackets(new S_SkillSound(tempCha.getId(), 8977), true);
								((L1PcInstance) tempCha).sendPackets(new S_SkillIconAura(119, 8), true);
							}
							Broadcaster.broadcastPacket(tempCha, new S_SkillSound(tempCha.getId(), 8977), true);
						} else
							pc.sendPackets(new S_SystemMessage("������ �����Ͽ����ϴ�."), true);
					}
				}
				pc.setSkillDelay(true);
				GeneralThreadPool.getInstance().schedule(new L1SkillDelay(pc, 5000), 5000);
				useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
			}
		} else

		if (pc.isSkillDelay()) {
			if (pc.skilldelayTime > System.currentTimeMillis()) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("ss��");
				String time = dateFormat.format(
						new Timestamp((pc.skilldelayTime - currentDate.getTimeInMillis()) + (60 * 1000 * 60 * 15)));
				pc.sendPackets(new S_SystemMessage(time + " �Ŀ� ��� �Ҽ� �ֽ��ϴ�."), true);
				dateFormat = null;
			}
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("ss��");
			String time = dateFormat.format(new Timestamp(
					((lastUsed.getTime() + (1000 * 8)) - currentDate.getTimeInMillis()) + (60 * 1000 * 60 * 15)));
			pc.sendPackets(new S_SystemMessage(time + " �Ŀ� ��� �Ҽ� �ֽ��ϴ�."), true);
			dateFormat = null;
		}
	}

	private static final int[] ���۷��� = { 20107, 90084, 90083, 59, 130220, 20298, 22009, 20079, 20017, 20260, 7228 };
	private static final int[] �������� = { 60200, 40104, 40105, 40106, 40107, 40108, 40109, 401010, 401011 };

	private int ���ۼ�������(L1PcInstance pc) {

		int rnd = _random.nextInt(10000) + 1;
		int itemid = 0;
		int count = 1;
		if (rnd <= 1) {// �����۵�
			itemid = ���۷���[_random.nextInt(���۷���.length)];
		} else if (rnd <= 1910) {// ��������
			itemid = ��������[_random.nextInt(��������.length)];
		}
		if (itemid != 0) {
			L1ItemInstance item = pc.getInventory().storeItem(itemid, count);
			pc.sendPackets(new S_ServerMessage(403, item.getName()));
		}
		return itemid;
	}

	private int ���ۻ���(L1PcInstance pc) {

		int rnd = _random.nextInt(1000000) + 1;
		int itemid = 0;
		int count = 1;
		if (rnd <= 50000) {// �������������ֹ�
			itemid = 500210;
			count = 1;
		} else if (rnd <= 80000) {// �������������ֹ�
			itemid = 500210;
			count = 3;
		} else if (rnd <= 90000) {// �������������ֹ�
			itemid = 500210;
			count = 5;
		} else if (rnd <= 140000) {// ������ ġ�����ֹ�
			itemid = 60071;
			count = 1;
		} else if (rnd <= 170000) {// ������ ġ�����ֹ�
			itemid = 60071;
			count = 3;
		} else if (rnd <= 180000) {// ������ ġ�����ֹ�
			itemid = 60071;
			count = 4;
		} else if (rnd <= 181000) {// ǻ��
			itemid = 500209;
		} else if (rnd <= 328769) {// ��������
			itemid = 60200;
		} else if (rnd <= 468769) {// ��������
			itemid = 40104;
		} else if (rnd <= 608769) {// ��������
			itemid = 40105;
		} else if (rnd <= 678769) {// ��������
			itemid = 40106;
		} else if (rnd <= 748769) {// ��������
			itemid = 40107;
		} else if (rnd <= 818769) {// ��������
			itemid = 40108;
		} else if (rnd <= 868769) {// ��������
			itemid = 40109;
		} else if (rnd <= 918769) {// ��������
			itemid = 40110;
		} else if (rnd <= 968769) {// ��������
			itemid = 40111;
		} else if (rnd <= 988769) {// ��������
			itemid = 40112;

		} else if (rnd <= 988779) {// ������
			itemid = 90084;

		} else if (rnd <= 988789) {// ����
			itemid = 69;

		} else if (rnd <= 988799) {// ǥȿ
			itemid = 90083;

		} else if (rnd <= 988809) {// �ݺ�
			itemid = 130220;

		} else if (rnd <= 988819) {// ����
			itemid = 20107;

		} else if (rnd <= 988829) {// ����
			itemid = 20298;

		} else if (rnd <= 988839) {// �þ�ɾ�
			itemid = 22009;

		} else if (rnd <= 988849) {// ���
			itemid = 20079;

		} else if (rnd <= 988859) {// �ӹ̿հ�
			itemid = 20017;

		} else if (rnd <= 988869) {// ���̸�����
			itemid = 20260;

		} else if (rnd <= 988870) {// �������
			itemid = 7310;

		} else if (rnd <= 988880) {// ��
			itemid = 7305;

		} else if (rnd <= 988890) {// ����
			itemid = 7306;

		} else if (rnd <= 988900) {// ��
			itemid = 7304;

		} else if (rnd <= 989000) {// �����ο���ŵ���
			itemid = 7335;
		} else if (rnd <= 990000) {// ���λ�������
			itemid = 7336;
		} else if (rnd <= 995000) {// ��ǳ����
			itemid = 7228;
		} else {// ��ö����
			itemid = 7229;
		}
		if (itemid != 0) {
			L1ItemInstance item = pc.getInventory().storeItem(itemid, count);
			pc.sendPackets(new S_ServerMessage(403, item.getName()));
		}
		return itemid;
	}

	private static final int[] ������ = { 120020, 120058, 120113, 120168, 120201, 120228 };
	private static final int[] �Ű��� = { 120233, 120030, 120067, 120129, 120176, 120208 };

	private int ���׽�����(L1PcInstance pc) {

		int rnd = _random.nextInt(134902) + 1;
		int itemid = 0;
		if (rnd <= 1)
			itemid = 40222;// ��
		else if (rnd <= 2)
			itemid = 41148;// ī��
		else if (rnd <= 12)
			itemid = 84;// �յ�
		else if (rnd <= 22)
			itemid = 164;// �վ�
		else if (rnd <= 32)
			itemid = 189;// �ձ�
		else if (rnd <= 52)
			itemid = 60199;// �Ƹ�
		else if (rnd <= 102)
			itemid = 41149;// ����
		else if (rnd <= 202)
			itemid = 100259; // ����ũ
		else if (rnd <= 302)
			itemid = 100260; // ������
		else if (rnd <= 802)
			itemid = 100074; // ������
		else if (rnd <= 1302)
			itemid = 100157; // ����ũ
		else if (rnd <= 2302)
			itemid = 81; // ����
		else if (rnd <= 3302)
			itemid = 162; // ��ũ
		else if (rnd <= 4802)
			itemid = 194; // ����Ʋ��
		else if (rnd <= 9602)
			itemid = ������[_random.nextInt(������.length)];
		else if (rnd <= 14402)
			itemid = �Ű���[_random.nextInt(�Ű���.length)];
		else if (rnd <= 15402)
			itemid = 140087;// �൥��
		else if (rnd <= 16402)
			itemid = 140074;// ����
		else if (rnd <= 134902)
			itemid = 60068 + _random.nextInt(4);

		if (itemid == 60070)
			itemid = 60068 + _random.nextInt(4);

		return itemid;
	}

	private void ����������and��(L1PcInstance pc, L1ItemInstance useItem, L1ItemInstance targetItem) {

		if (targetItem == null)
			return;
		if (targetItem.getBless() >= 128) {
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
			return;
		}
		if (useItem.getItemId() == 60282) { // õ�� �� : ������ Ƽ
			if (targetItem.getItemId() >= 21139 && targetItem.getItemId() <= 21156) {
				if (targetItem.getBless() == 0 && targetItem.getItemId() < 21131) {
					createNewItem2(pc, 120085, 1, targetItem.getEnchantLevel());
				} else
					createNewItem2(pc, targetItem.getItemId() >= 21148 ? 20084 : 20085, 1,
							targetItem.getEnchantLevel());
				pc.getInventory().removeItem(targetItem, 1);
			} else {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
		} else if (useItem.getItemId() >= 60273 && useItem.getItemId() <= 60281) {// ����
			if (targetItem.getItemId() == 20084 || targetItem.getItemId() == 20085
					|| targetItem.getItemId() == 120085) {
				int itemid = 0;
				if (targetItem.getItemId() == 20084)
					itemid = useItem.getItemId() - 60273 + 21148;
				else if (targetItem.getItemId() == 20085 || targetItem.getItemId() == 120085)
					itemid = useItem.getItemId() - 60273 + 21139;

				if (itemid != 0) {// ���⿩��
					if (targetItem.getItemId() == 120085) {
						L1ItemInstance citem = createNewItem2(pc, itemid, 1, targetItem.getEnchantLevel(), 0, 0, 0);
						citem.setBless(0);
						pc.getInventory().updateItem(citem, L1PcInventory.COL_BLESS);
						pc.getInventory().saveItem(citem, L1PcInventory.COL_BLESS);
					} else
						createNewItem2(pc, itemid, 1, targetItem.getEnchantLevel());
					pc.getInventory().removeItem(targetItem, 1);
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
		}
		pc.getInventory().removeItem(useItem, 1);
	}

	private static int ���������Ʈ[] = { 261, 9, 8, 20074, 20187, 20194, 40074, 120056, 20280, 120300, 140074, 40087, 140087,
			40044, 40045, 40046, 40047, 40048, 40049, 40050, 40051, 40052, 40053, 40054, 40055 };

	private int ������ָӴ�(L1PcInstance pc) {

		int rnd = _random.nextInt(1000) + 1;
		int itemid = 0;

		if (rnd <= 1) {
			itemid = 84; // ��յ�
		} else if (rnd <= 2) {
			itemid = 60199; // �ƸӺ극��ũ
		} else if (rnd <= 3) {
			itemid = 20422; // �����
		} else if (rnd <= 4) {// ������ ��
			itemid = 40034;
		} else if (rnd <= 5) {// ����
			itemid = 20074;
		} else if (rnd <= 6) {// �����Ϸ���
			itemid = 9;
		} else {
			itemid = ���������Ʈ[_random.nextInt(���������Ʈ.length)];
		}
		return itemid;
	}

	private void Ƽ��ȯ��(L1PcInstance pc, L1ItemInstance useItem, L1ItemInstance targetItem) {

		if (targetItem == null)
			return;
		if (targetItem.getBless() >= 128) {
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
			return;
		}
		// �γ��帱
		if (targetItem.getItemId() >= 490001 && targetItem.getItemId() <= 490017) {
			if (useItem.getItemId() == 60283) {
				createNewItem2(pc, 20085, 1, targetItem.getEnchantLevel());
				pc.getInventory().removeItem(targetItem, 1);
				pc.getInventory().removeItem(useItem, 1);
			} else if (useItem.getItemId() - 60235 == targetItem.getEnchantLevel()) {
				createNewItem2(pc, 20085, 1, targetItem.getEnchantLevel());
				pc.getInventory().removeItem(targetItem, 1);
				pc.getInventory().removeItem(useItem, 1);
			} else
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
		} else
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
	}

	private void �ǵ������and����(L1PcInstance pc, L1ItemInstance useItem, L1ItemInstance targetItem) {

		if (targetItem == null)
			return;
		if (targetItem.getBless() >= 128) {
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
			return;
		}
		if (useItem.getItemId() == 60247) { // õ�� ��
			if (targetItem.getItemId() >= 21125 && targetItem.getItemId() <= 21136) {
				createNewItem2(pc, targetItem.getItemId() >= 21131 ? 20084 : 20085, 1, targetItem.getEnchantLevel());
				pc.getInventory().removeItem(targetItem, 1);
			} else {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
		} else if (useItem.getItemId() >= 60218 && useItem.getItemId() <= 60223) {// ���
			if (targetItem.getItemId() == 20084 || targetItem.getItemId() == 20085) {
				int itemid = 0;
				if (targetItem.getItemId() == 20084)
					itemid = useItem.getItemId() - 60218 + 21131;
				else if (targetItem.getItemId() == 20085)
					itemid = useItem.getItemId() - 60218 + 21125;

				if (itemid != 0) {
					createNewItem2(pc, itemid, 1, targetItem.getEnchantLevel());
					pc.getInventory().removeItem(targetItem, 1);
				}
			} else {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
		} else if (useItem.getItemId() >= 60224 && useItem.getItemId() <= 60232) {// ����
			if (targetItem.getItemId() >= 21125 && targetItem.getItemId() <= 21136) {
				boolean bEquipped = false;
				if (targetItem.isEquipped()) {
					pc.getInventory().setEquipped(targetItem, false);
					bEquipped = true;
				}
				switch (useItem.getItemId()) {
				case 60224:
					targetItem.setRegistLevel(10);
					break;
				case 60225:
					targetItem.setRegistLevel(11);
					break;
				case 60226:
					targetItem.setRegistLevel(12);
					break;
				case 60227:
					targetItem.setRegistLevel(13);
					break;
				case 60228:
					targetItem.setRegistLevel(14);
					break;
				case 60229:
					targetItem.setRegistLevel(15);
					break;
				case 60230:
					targetItem.setRegistLevel(16);
					break;
				case 60231:
					targetItem.setRegistLevel(17);
					break;
				case 60232:
					targetItem.setRegistLevel(18);
					break;
				default:
					break;
				}
				if (bEquipped) {
					pc.getInventory().setEquipped(targetItem, true);
				}
				pc.getInventory().updateItem(targetItem, L1PcInventory.COL_regist);
				pc.getInventory().saveItem(targetItem, L1PcInventory.COL_regist);
				pc.saveInventory();
			} else {
				pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
				return;
			}
		}
		pc.getInventory().removeItem(useItem, 1);
	}

	private int �ҷ���ü��Ű��ũ����(L1PcInstance pc, int itemId) {

		int rnd = _random.nextInt(1500) + 1;
		int itemid = 0;
		if (rnd <= 50) {
			if (itemId == 60190)// ü�μҵ�
				itemid = 265;
			else
				// Ű��ũ
				itemid = 264;
		} else if (rnd <= 150)
			itemid = 60197; // ȣ�� ���� ��ȣ �ֹ���
		else if (rnd <= 270)
			itemid = 60192; // �ҷ��� ���� ĵ��
		else if (rnd <= 390)
			itemid = 60193; // �ҷ��� ��ũ ĵ��
		else if (rnd <= 510)
			itemid = 60194; // �ҷ��� �ٳ��� ĵ��
		else if (rnd <= 630)
			itemid = 60195; // �ҷ��� ���ݸ� ĵ��
		else if (rnd <= 750)
			itemid = 60196; // �ҷ��� ȣ�� ĵ��
		else if (rnd <= 1500)
			itemid = 435000; // �ҷ��� ȣ�� ����

		return itemid;
	}

	private void ���ø�������(L1PcInstance pc, L1ItemInstance useItem, L1ItemInstance targetItem) {
		boolean isAppear = true;
		L1DollInstance doll = null;
		boolean ck = true;
		for (Object dollObject : pc.getDollList()) {
			doll = (L1DollInstance) dollObject;
			if (doll.getItemObjId() == targetItem.getId()) {
				isAppear = false;
				break;
			}
		}
		if (isAppear) {
			Random _random = new Random();
			int ���� = _random.nextInt(1000);
			int ���� = _random.nextInt(100);
			if (���� < 40) {
				if (���� < 70) {
					pc.getInventory().storeItem(430000, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ����"), true);
				} else if (���� < 140) {
					pc.getInventory().storeItem(41248, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ���׺���"), true);
				} else if (���� < 210) {
					pc.getInventory().storeItem(41249, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ��ť����"), true);
				} else if (���� < 280) {
					pc.getInventory().storeItem(430001, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ���"), true);
				} else if (���� < 350) {
					pc.getInventory().storeItem(430002, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ũ����Ʈ �þ�"), true);
				} else if (���� < 420) {
					pc.getInventory().storeItem(141918, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : �ô�"), true);
				} else if (���� < 490) {
					pc.getInventory().storeItem(430004, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ��Ƽ"), true);
				} else if (���� < 560) {
					pc.getInventory().storeItem(430500, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ��īƮ����"), true);
				} else if (���� < 630) {
					pc.getInventory().storeItem(141919, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ��̾�"), true);
				} else if (���� < 700) {
					pc.getInventory().storeItem(141920, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ��������"), true);
				} else if (���� < 770) {
					pc.getInventory().storeItem(141922, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ��ƾ"), true);
				} else if (���� < 841) {
					pc.getInventory().storeItem(141921, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ����ƺ�"), true);

				} else if (���� < 842) {
					pc.getInventory().storeItem(500204, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : �����"), true);

				} else if (���� < 843) {
					pc.getInventory().storeItem(500205, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ��ť���� ��"), true);

				} else if (���� < 844) {
					pc.getInventory().storeItem(500203, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ���̾�Ʈ"), true);

				} else if (���� < 846) {
					pc.getInventory().storeItem(5000035, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ��ġ"), true);
				} else if (���� < 849) {
					pc.getInventory().storeItem(500202, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : ����Ŭ�ӽ�"), true);
				} else {
					pc.getInventory().storeItem(41250, 1);
					pc.sendPackets(new S_ServerMessage(403, "�������� : �����ΰ�"), true);
				}

			} else {
				ck = false;
				pc.sendPackets(new S_SystemMessage("���ø����� �ູ�� ������� ���Ͽ����ϴ�."));
				/*
				 * pc.getInventory().storeItem(41250, 1); pc.sendPackets(new
				 * S_ServerMessage(403, "�������� : �����ΰ�"), true);
				 */
			}
			if (ck) {
				pc.getInventory().removeItem(useItem, 1);
				pc.getInventory().removeItem(targetItem, 1);
			}
			pc.sendPackets(new S_SkillSound(pc.getId(), 6130));
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 6130));
			/*
			 * if(���ž��̵�!=0){ L1PolyMorph.doPoly(pc, ���ž��̵�, 600,
			 * L1PolyMorph.MORPH_BY_LOGIN); }
			 */

		} else {
			pc.sendPackets(new S_SystemMessage("��� ���� ������ ���� �� �� �����ϴ�."), true);
		}

	}

	private int �巡��ť��(L1PcInstance pc) {

		int rnd = _random.nextInt(12000) + 1;
		int itemid = 0;
		if (rnd <= 10)
			itemid = 60186; // ���� 0.1
		else if (rnd <= 11)
			itemid = 60187; // �� ��Ÿ���� 0.01
		else if (rnd <= 12)
			itemid = 60188; // �� ��Ǫ����
		else if (rnd <= 13)
			itemid = 60189; // �� �������
		else if (rnd <= 113)
			itemid = 40341; // ��Ÿ ��� 1
		else if (rnd <= 213)
			itemid = 40349; // �߶� ���
		else if (rnd <= 313)
			itemid = 40357; // ��Ǫ ���
		else if (rnd <= 413)
			itemid = 40365; // ���� ���
		else if (rnd <= 12000)
			itemid = 437010; // ���

		return itemid;
	}

	private static final int[] ����ť�긮��Ʈ = { 40491, 40498, 40651, 40643, 40645, 40618, 40074, 140074, 40087, 140087,
			40488, 40467, 40440, 40068, 40031, 40014 };

	private int ����ť��(L1PcInstance pc) {

		int itemid = 0;
		int rnd = _random.nextInt(100000) + 1;
		if (rnd >= 100 && rnd <= 150) // �ɰ�
			itemid = 60187;
		else if (rnd >= 150 && rnd <= 200)
			itemid = 60188;
		else if (rnd <= 22)
			itemid = 40513;
		else if (rnd <= 32)
			itemid = 40393;
		else if (rnd <= 42)
			itemid = 40394;
		else if (rnd <= 52)
			itemid = 40395;
		else if (rnd <= 62)
			itemid = 40396;
		else
			itemid = ����ť�긮��Ʈ[_random.nextInt(����ť�긮��Ʈ.length)];

		return itemid;
	}

	/*
	 * �巡���� �ڼ��� ���� 95& �巡���� ������ 1% �ҿ����� ���� ���� 1% ������ ���� ���� : ��ȯ�� �� 1% ������ ��� 1%
	 * ȭ���� ��� 1% 82 ���� �ֹ��� 1% 85 ���� �ֹ��� 1%
	 */
	private int �����ǻ�(L1PcInstance pc) {

		int rnd = _random.nextInt(1000) + 1;
		int itemid = 0;
		if (rnd <= 20) {
			itemid = 7241; // ������
		} else if (rnd <= 40) {// �ҿ����ѱ�������
			itemid = 6016;
		} else if (rnd <= 60) {// �����Ǳ���������ȯ�Ǽ�
			itemid = 60499;
		} else if (rnd <= 80) {// �����Ǳ��
			itemid = 7337;
		} else if (rnd <= 100) {// ȭ���Ǳ��
			itemid = 6022;
		} else if (rnd <= 120) {// 82���� �ֹ���
			itemid = 8000;
		} else if (rnd <= 140) {// 85���� �ֹ���
			itemid = 8001;
		} else {
			itemid = 60257;
		}
		return itemid;
	}

	private static int �����ں�����Ʈ[] = { 9, 8, 20074, 20187, 20194, 40074, 120056, 20280, 120300, 140074, 40087, 140087,
			40044, 40045, 40046, 40047, 40048, 40049, 40050, 40051, 40052, 40053, 40054, 40055 };

	private int �����ں���(L1PcInstance pc) {

		int rnd = _random.nextInt(1000) + 1;
		int itemid = 0;
		if (rnd <= 1) {
			itemid = 205; // ����
		} else if (rnd <= 2) {// �ݳ�
			itemid = 20049;
		} else if (rnd <= 3) {// ����
			itemid = 20050;
		} else if (rnd <= 4) {// �����
			itemid = 20422;
		} else if (rnd <= 5) {// ������ ��
			itemid = 40034;
		} else if (rnd <= 6) {// ����
			itemid = 20074;
		} else {
			itemid = �����ں�����Ʈ[_random.nextInt(�����ں�����Ʈ.length)];
		}
		return itemid;
	}

	private int _�����ϻ�弱��2[] = { 121, 119, 124, 123, 21261, 21263, 21262, 500214 };

	private void �����ϻ�弱��(L1PcInstance pc, int itemId) {

		int itemid = 500209;
		int count = 1;
		L1ItemInstance gosu = pc.getInventory().storeItem(itemid, count);
		pc.sendPackets(new S_ServerMessage(403, gosu.getItem().getName() + " (" + count + ")"), true);

		gosu = pc.getInventory().storeItem(_�����ϻ�弱��2[_random.nextInt(_�����ϻ�弱��2.length)], count);
		pc.sendPackets(new S_ServerMessage(403, gosu.getItem().getName() + " (" + count + ")"), true);

	}

	private int _���[] = { 41248, 41250, 430000, 430002, 430004 };

	private void ���(L1PcInstance pc, int itemId) {

		int rnd = _random.nextInt(100) + 1;
		int itemid = 0;
		if (rnd <= 1) {// ȣ�ڸ����
			itemid = 21269;
		} else if (rnd <= 2) {// 10�ֳ� �Ͱ���
			itemid = 423009;
		} else if (rnd <= 3) {// 10�ֳ� �����
			itemid = 423010;
		} else if (rnd <= 4) {// 10�ֳ� ������
			itemid = 423011;
		} else if (rnd <= 29) {// 1�ܰ�����
			itemid = _���[_random.nextInt(_���.length)];
		} else {// ���� ����
			itemid = 60518;
		}
		int count = 1;
		/*
		 * if(itemid == 40308){ count = _random.nextInt(45000)+5000; }
		 */
		L1ItemInstance gosu = pc.getInventory().storeItem(itemid, count);
		pc.sendPackets(new S_ServerMessage(403, gosu.getItem().getName() + " (" + count + ")"), true);
	}

	private int _������������[] = { 60107, 60108, 60109, 60110, 60111, 60112, 60113, 60114, 60115, 60116, 60117, 60118, 60119,
			60120, 60121, 60122, 40308 };

	private void ������������(L1PcInstance pc, int itemId) {

		int itemid = _������������[_random.nextInt(_������������.length)];
		int count = 1;
		if (itemid == 40308) {
			count = _random.nextInt(45000) + 5000;
		}
		L1ItemInstance gosu = pc.getInventory().storeItem(itemid, count);
		pc.sendPackets(new S_ServerMessage(403, gosu.getItem().getName() + " (" + count + ")"), true);
	}

	private void �������(L1ItemInstance useItem, L1PcInstance pc) {
		int locX = ((L1EtcItem) useItem.getItem()).get_locx();
		int locY = ((L1EtcItem) useItem.getItem()).get_locy();
		short mapId = ((L1EtcItem) useItem.getItem()).get_mapid();
		if (locX != 0 && locY != 0) {

			if (pc.getMap().isEscapable() || pc.isGm()) {
				if (pc.Sabutelok()) {
					pc.dx = locX;
					pc.dy = locY;
					pc.dm = mapId;
					pc.dh = pc.getMoveState().getHeading();
					pc.setTelType(7);
					pc.sendPackets(new S_SabuTell(pc), true);
				}
			} else {
				pc.sendPackets(new S_ServerMessage(647), true);
			}
		}
		pc.cancelAbsoluteBarrier();
	}

	private int �����Ѿ�����(L1PcInstance pc) {

		int rnd = _random.nextInt(10000) + 1;
		int itemid = 0;
		if (rnd <= 1) {
			itemid = 30220;// �ı����
		} else if (rnd <= 2) {
			itemid = 20320;// Ÿ��
		} else if (rnd <= 3) {
			itemid = 30218;// ��������
		} else if (rnd <= 4) {
			itemid = 30219;// ���尩
		} else if (rnd <= 5) {
			itemid = 40223; // �ۼ�
		} else if (rnd <= 6) {
			itemid = 157;// ��ũ
		} else if (rnd <= 7) {
			itemid = 20459;// �ӿ���
		} else if (rnd <= 8) {
			itemid = 20187;// �ı�
		} else if (rnd <= 9) {
			itemid = 140074; // ����
		} else if (rnd <= 10) {
			itemid = 140087; // �൥��
		} else if (rnd <= 60) {
			itemid = 439017;// ���̼���
		} else if (rnd <= 110) {
			itemid = 439018; // �ƹ�Ÿ����
		} else if (rnd <= 160) {
			itemid = 439016; // �дм���
		} else {
			// ��
		}
		/**
		 * �� 95���� �ۼ�,��ũ,����,�ı�,����,�൥��,���̼���,�ƹ�Ÿ����,�дм��� 0.5���ξ� Ÿ��,��������,���尩 0.1����
		 * �ı������ 0.01����
		 */
		return itemid;
	}

	private void ��(L1PcInstance pc, L1ItemInstance useItem) {
		switch (useItem.getItem().getItemId()) {
		case 60381:
			pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "questbook1"));
			break;
		case 60384:
			pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "questbook2"));
			break;
		case 7257:
			pc.sendPackets(new S_NPCTalkReturn(pc.getId(), "questbook3"));
			break;
		default:
			break;
		}
	}

	private void ���1������(L1PcInstance pc) {
		pc.getInventory().consumeItem(555551, 1); // �����Ǵ� �����۰� ����
		createNewItem2(pc, 412001, 1, 9, 3, 0); // �Ĵ�
		createNewItem2(pc, 120011, 1, 7); // ����
		createNewItem2(pc, 120056, 1, 7); // ����
		createNewItem2(pc, 20085, 1, 7); // Ƽ����
		createNewItem2(pc, 20201, 1, 7); // ����
		createNewItem2(pc, 20187, 1, 7); // �ı�
		createNewItem2(pc, 21169, 1, 0); // ����
		createNewItem2(pc, 420003, 1, 0); // ������

		createNewItem2(pc, 21022, 1, 0); // ������
		createNewItem2(pc, 20264, 1, 0); // �Ϸ�
		createNewItem2(pc, 20317, 1, 0); // ����
		createNewItem2(pc, 20280, 1, 0); // �긶
		createNewItem2(pc, 20280, 1, 0); // �긶
	}

	private void ���ָӴ�(L1PcInstance pc, L1ItemInstance useItem) {
		pc.getInventory().consumeItem(888813, 1);
		int roonid = 0;
		if (pc.isCrown()) {
			createNewItem2(pc, 9000, 1, 0, 0, 0);
			createNewItem2(pc, 9001, 1, 0, 0, 0);
			createNewItem2(pc, 9002, 1, 0, 0, 0);
			createNewItem2(pc, 9003, 1, 0, 0, 0);
			createNewItem2(pc, 9004, 1, 0, 0, 0);

		} else if (pc.isKnight()) {
			createNewItem2(pc, 9010, 1, 0, 0, 0);
			createNewItem2(pc, 9011, 1, 0, 0, 0);
			createNewItem2(pc, 9012, 1, 0, 0, 0);
			createNewItem2(pc, 9013, 1, 0, 0, 0);
			createNewItem2(pc, 9014, 1, 0, 0, 0);

		} else if (pc.isElf()) {
			createNewItem2(pc, 9020, 1, 0, 0, 0);
			createNewItem2(pc, 9021, 1, 0, 0, 0);
			createNewItem2(pc, 9022, 1, 0, 0, 0);
			createNewItem2(pc, 9023, 1, 0, 0, 0);
			createNewItem2(pc, 9024, 1, 0, 0, 0);

		} else if (pc.isWizard()) {
			createNewItem2(pc, 9030, 1, 0, 0, 0);
			createNewItem2(pc, 9031, 1, 0, 0, 0);
			createNewItem2(pc, 9032, 1, 0, 0, 0);
			createNewItem2(pc, 9033, 1, 0, 0, 0);
			createNewItem2(pc, 9034, 1, 0, 0, 0);

		} else if (pc.isDarkelf()) {
			createNewItem2(pc, 9040, 1, 0, 0, 0);
			createNewItem2(pc, 9041, 1, 0, 0, 0);
			createNewItem2(pc, 9042, 1, 0, 0, 0);
			createNewItem2(pc, 9043, 1, 0, 0, 0);
			createNewItem2(pc, 9044, 1, 0, 0, 0);

		} else if (pc.isDragonknight()) {
			createNewItem2(pc, 9050, 1, 0, 0, 0);
			createNewItem2(pc, 9051, 1, 0, 0, 0);
			createNewItem2(pc, 9052, 1, 0, 0, 0);
			createNewItem2(pc, 9053, 1, 0, 0, 0);
			createNewItem2(pc, 9054, 1, 0, 0, 0);

		} else if (pc.isIllusionist()) {
			createNewItem2(pc, 9060, 1, 0, 0, 0);
			createNewItem2(pc, 9061, 1, 0, 0, 0);
			createNewItem2(pc, 9062, 1, 0, 0, 0);
			createNewItem2(pc, 9063, 1, 0, 0, 0);
			createNewItem2(pc, 9064, 1, 0, 0, 0);

		} else if (pc.isWarrior()) {
			createNewItem2(pc, 9070, 1, 0, 0, 0);
			createNewItem2(pc, 9071, 1, 0, 0, 0);
			createNewItem2(pc, 9072, 1, 0, 0, 0);
			createNewItem2(pc, 9073, 1, 0, 0, 0);
			createNewItem2(pc, 9074, 1, 0, 0, 0);
		}

		switch (useItem.getItem().getItemId()) {
		case 7252:
			roonid += 9000;
			break;
		case 7253:
			roonid += 9001;
			break;
		case 7254:
			roonid += 9002;
			break;
		case 7255:
			roonid += 9003;
			break;
		case 7256:
			roonid += 9004;
			break;
		default:
			break;
		}
		L1Quest q = pc.getQuest();
		if (roonid >= 9000) {
			try {
				q.get_step(L1Quest.QUEST_55_Roon);
				q.set_step(L1Quest.QUEST_55_Roon, 1);
				q.get_step(L1Quest.QUEST_70_Roon);
				q.set_step(L1Quest.QUEST_70_Roon, 1);
				L1ItemInstance roon = pc.getInventory().storeItem(roonid, 1);
				L1ItemInstance gosu = pc.getInventory().storeItem(7257, 1);
				pc.sendPackets(new S_ServerMessage(403, roon.getItem().getName()), true);
				pc.sendPackets(new S_ServerMessage(403, gosu.getItem().getName()), true);
				pc.getInventory().removeItem(useItem, 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void ��������5(L1PcInstance pc, L1ItemInstance useItem) {
		pc.getInventory().storeItem(7249, 5);
		pc.sendPackets(new S_ServerMessage(403, "������ ��ű� ���� �ֹ��� ���� (5)"), true);
		pc.getInventory().removeItem(useItem, 1);
	}

	private void ��������(L1PcInstance pc, L1ItemInstance useItem) {
		int chan = _random.nextInt(100) + 1;
		if (chan < 10) {
			pc.getInventory().storeItem(7324, 1);
		} else {
			pc.getInventory().storeItem(7323, 1);
		}
		pc.sendPackets(new S_ServerMessage(403, "������ ��ű� ���� �ֹ���"), true);
		pc.getInventory().removeItem(useItem, 1);
	}

	private void ȥ������(L1PcInstance pc, L1ItemInstance useItem, int itemId) {
		pc.getInventory().removeItem(useItem, 1);
		int chance = _random.nextInt(100);
		if (chance <= 50) {
			L1ItemInstance item = pc.getInventory().storeItem(itemId + 1010, 1);
			if (item != null) {
				pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true);
			}
		} else {
			L1ItemInstance item = null;
			if (itemId == 5000110) {
				item = pc.getInventory().storeItem(60202, 1);
			} else {
				item = pc.getInventory().storeItem(itemId - 4959822, 1);
			}
			if (item != null) {
				pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true);
			}
		}
	}

	private void ��ȭ�̺�Ʈ�ֹ���(L1PcInstance pc, L1ItemInstance ��ȭ��, L1ItemInstance ����) {
		int rnd = _random.nextInt(100) + 1;
		if (����.getItem().getItemId() == 600240) {
			if (����.getEnchantLevel() >= 3) {
				pc.sendPackets(new S_SystemMessage("���̻� ��æƮ�� �Ұ��� �մϴ�."));
				return;
			}
			if (rnd < 10) {// ����
				int newEnchantLvl = ����.getEnchantLevel() + 1;
				����.setEnchantLevel(newEnchantLvl);

				pc.getInventory().updateItem(����, L1PcInventory.COL_ENCHANTLVL);
				pc.sendPackets(new S_PacketBox(����, S_PacketBox.��æ����));
				pc.saveInventory();
				pc.sendPackets(new S_SystemMessage("���� ��þƮ : ��þƮ ����."));
			} else if (rnd < 64) {// ������
				pc.sendPackets(new S_SystemMessage("���� ��þƮ : ��þƮ ����(��ȭ��ġ ����)"));
			} else {// �϶�
				if (����.getEnchantLevel() == 0) {
					pc.sendPackets(new S_SystemMessage("���� ��þƮ : ��þƮ ����(��ȭ��ġ ����)"));
					// pc.sendPackets(new
					// S_SystemMessage("�϶��ؾ� ������ ���̻� �϶��Ҽ����� ���ϵ�."));
					pc.getInventory().removeItem(��ȭ��, 1);
					return;
				}

				int newEnchantLvl = ����.getEnchantLevel() - 1;
				����.setEnchantLevel(newEnchantLvl);

				pc.getInventory().updateItem(����, L1PcInventory.COL_ENCHANTLVL);
				pc.sendPackets(new S_PacketBox(����, S_PacketBox.��æ����));
				pc.saveInventory();
				pc.sendPackets(new S_SystemMessage("���� ��þƮ : ��þƮ ����(��ȭ��ġ �϶�)"));
			}

			pc.getInventory().removeItem(��ȭ��, 1);
		} else {
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); 
		}
	}

	private int �ǵ�����������(L1PcInstance pc) {

		int rnd = _random.nextInt(10430) + 1;
		int itemid = 0;
		if (rnd <= 1) {
			itemid = 40249; // ���
		} else if (rnd <= 10) {
			itemid = 41149;// ���� ����
		} else if (rnd <= 20) {
			itemid = 40223;// �ۼ�
		} else if (rnd <= 30) {
			itemid = 40166;// �ٿ
		} else if (rnd <= 40) {
			itemid = 40238; // �������ҿ�
		} else if (rnd <= 50) {
			itemid = 20010; // ���� ����
		} else if (rnd <= 60) {
			itemid = 20100;// ���� ����
		} else if (rnd <= 70) {
			itemid = 20166; // ���� �尩
		} else if (rnd <= 80) {
			itemid = 20198; // ���� ����
		} else if (rnd <= 81) {
			if (_random.nextInt(10) + 1 == 1)
				itemid = 40222; // ��
		} else if (rnd <= 82) {
			if (_random.nextInt(10) + 1 == 1)
				itemid = 41148; // ī��
		} else if (rnd <= 83) {
			itemid = 40466;// ���
		} else if (rnd <= 490) {
			// itemid = 60071 ; //������ ġ���� �ֹ�
		} else if (rnd <= 890) {
			// itemid = 60068 ; //������ ����� �ֹ�
		} else if (rnd <= 1290) {
			// itemid = 60069 ; //������ �ü��� �ֹ�
		} else if (rnd <= 1690) {
			// itemid = 60070 ; //������ ������ �ֹ�
		} else if (rnd <= 10430) {
			// ��
		}
		return itemid;
	}

	private void UseHeallingPotion(L1PcInstance pc, int healHp, int gfxid) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																		// ����
			pc.sendPackets(new S_ServerMessage(698), true); // ���¿� ���� �ƹ��͵� ���� ����
															// �����ϴ�.
			return;
		}

		// �ۼַ�Ʈ�������� ����
		pc.cancelAbsoluteBarrier();

		pc.sendPackets(new S_SkillSound(pc.getId(), gfxid), true);
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfxid), true);
		// pc.sendPackets(new S_ServerMessage(77)); // \f1����� ���������ϴ�.
		healHp *= (_random.nextGaussian() / 5.0D) + 1.0D;

		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�������)) {
			int atklv = pc.������󵵰����ڷ���;
			int dflv = pc.getLevel();
			double ���Ϸ� = 0.65;
			if (atklv > dflv) {
				���Ϸ� += (atklv - dflv) * 0.05;
			}
			if (���Ϸ� > 0.9) {
				���Ϸ� = 0.9;
			}
			healHp -= (int) ((double) healHp * ���Ϸ�);
		} else if (pc.getSkillEffectTimerSet().hasSkillEffect(POLLUTE_WATER)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(10517)) { // ����Ʈ��Ÿ����
																		// ȸ����1/2��
			healHp /= 2;
		}

		pc.setCurrentHp(pc.getCurrentHp() + healHp);
	}

	private void �������ǻ��(L1PcInstance pc) {
		�������ǻ��(pc, 197);
	}

	private void �������ǻ��(L1PcInstance pc, int gfx) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																		// ����
			pc.sendPackets(new S_ServerMessage(698), true);
			return;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_DRAGONPERL)) {
			pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.STATUS_DRAGONPERL);
			pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONPERL, 0, 0), true);
			Broadcaster.broadcastPacket(pc, new S_DRAGONPERL(pc.getId(), 0), true); // ����ȭ�����
			pc.sendPackets(new S_DRAGONPERL(pc.getId(), 0), true);//
			pc.set���ּӵ�(0);
		}
		pc.cancelAbsoluteBarrier();// �ۼ�����(�ѿ� �� �޼ҵ������ ����)
		int time = 600 * 1000;
		int stime = (int) (((time / 1000) / 1));
		pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_DRAGONPERL, time);
		pc.sendPackets(new S_SkillSound(pc.getId(), gfx), true);// ������ ����Ʈ...
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfx), true);

		pc.sendPackets(new S_DRAGONPERL(pc.getId(), 8), true);//
		Broadcaster.broadcastPacket(pc, new S_DRAGONPERL(pc.getId(), 8), true);//
		pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONPERL, 8, stime), true);//
		pc.set���ּӵ�(1);

	}

	private void ���(L1PcInstance pc, L1ItemInstance useItem, L1ItemInstance ddItem) {
		int itemId2 = ddItem.getItem().getItemId();
		if (itemId2 == L1ItemId.DRAGON_DIAMOND || itemId2 == L1ItemId.DRAGON_EME) {
			pc.getInventory().removeItem(ddItem, 1);
			pc.getInventory().removeItem(useItem, 1);
			pc.getInventory().storeItem(7241, 2);
			pc.sendPackets(new S_ServerMessage(403, "�巡���� ������" + "(2)"), true);
		} else {
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true);
		}

	}

	/*
	 * private void �����ָӴ�(L1PcInstance pc, L1ItemInstance useItem) { Timestamp
	 * lastUsed = useItem.getLastUsed(); if(lastUsed == null ||
	 * System.currentTimeMillis() > lastUsed.getTime() + (1000*60*60*22)){//22�ð�
	 * pc.getInventory().storeItem(7337, 1); pc.sendPackets(new
	 * S_ServerMessage(403, "������ ���"), true);
	 * useItem.setChargeCount(useItem.getChargeCount()-1);
	 * pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT); if
	 * (useItem.getChargeCount() == 0){ pc.getInventory().removeItem(useItem); }
	 * useItem.setLastUsed(new Timestamp(System.currentTimeMillis() + (60 *
	 * 60000 * 22))); }else{ long i = lastUsed.getTime() + (60 * 60000 * 22)) -
	 * System.currentTimeMillis(); TimeZone seoul = TimeZone.getTimeZone("UTC");
	 * Calendar cal = Calendar.getInstance(seoul); cal.setTimeInMillis(i);
	 * pc.sendPackets(new S_SystemMessage(i/60000+"�� ����("
	 * +cal.getTime().getHours( )+":"+cal.getTime().getMinutes()+
	 * " ����)�� ����� �� �����ϴ�."), true); }
	 *//** �ð��� �׾Ƹ� **/
	/*
	 * }
	 */
	private void �����ָӴ�(L1PcInstance pc, L1ItemInstance useItem) {
		Timestamp lastUsed = useItem.getLastUsed();
		if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (1000 * 60 * 60 * 22)) {// 22�ð�
			pc.getInventory().storeItem(7337, 1);
			pc.sendPackets(new S_ServerMessage(403, "������ ���"), true);
			useItem.setChargeCount(useItem.getChargeCount() - 1);
			pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
			if (useItem.getChargeCount() == 0) {
				pc.getInventory().removeItem(useItem);
			}
			useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
		} else {
			long i = (lastUsed.getTime() + (1000 * 60 * 60 * 22)) - currentDate.getTimeInMillis();
			Calendar cal = (Calendar) currentDate.clone();
			cal.setTimeInMillis(cal.getTimeInMillis() + i);
			pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����(" + cal.getTime().getHours() + ":"
					+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
		}
		/** �ð��� �׾Ƹ� **/
	}

	private void �ڼ���(L1PcInstance pc, L1ItemInstance item) {
		if (pc.getAinHasad() > 10000) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) == true) {
				pc.sendPackets(new S_ServerMessage(2147), true);
				return;
			}
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_TOPAZ)) {
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_TOPAZ);
			}
			long sysTime = System.currentTimeMillis();

			Timestamp deleteTime = null;
			deleteTime = new Timestamp(sysTime + 1800000);
			pc.setPUPLETime(deleteTime);
			if (pc.getTOPAZTime() != null) {
				pc.setTOPAZTime(null);
			}

			/*
			 * if(!pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.
			 * DRAGON_PUPLE )){ pc.calAinHasad(1000000); pc.sendPackets(new
			 * S_PacketBox(S_PacketBox.AINHASAD, pc), true); }
			 */
			pc.getSkillEffectTimerSet().setSkillEffect(DRAGON_PUPLE, 1800 * 1000);
			pc.sendPackets(new S_PacketBox(1800, 1, true, true), true);
			pc.sendPackets(new S_SkillSound(pc.getId(), 197), true);
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 197), true);

			try {
				pc.save();
			} catch (Exception e) {

				e.printStackTrace();
			}
			pc.getInventory().removeItem(item, 1);
		} else {
			pc.sendPackets(new S_SystemMessage("�ູ������ �־�� ����ϽǼ� �ֽ��ϴ�."), true);
		}
	}

	public void tamupdate(int objectId, Timestamp date) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET TamEndTime=? WHERE objid=?");
			pstm.setTimestamp(1, date);
			pstm.setInt(2, objectId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void tamadd(String _name, int objectId, int _day, String _encobjid) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO Tam SET objid=?, Name=?, Day=? , encobjid=?");
			pstm.setInt(1, objectId);
			pstm.setString(2, _name);
			pstm.setInt(3, _day);
			pstm.setString(4, _encobjid);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void Ž����(L1PcInstance pc, int _objid, L1ItemInstance item, int day) {
		try {
			Timestamp tamtime = null;
			long time = 0;
			long sysTime = System.currentTimeMillis();
			String _Name = null;
			int tamcount = pc.tamcount();

			Connection con = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;
			try {
				con = L1DatabaseFactory.getInstance().getConnection();
				pstm = con.prepareStatement("SELECT TamEndTime, char_name FROM characters WHERE objid=?");
				pstm.setInt(1, _objid);
				rs = pstm.executeQuery();
				while (rs.next()) {
					_Name = rs.getString("char_name");
					tamtime = rs.getTimestamp("TamEndTime");
					if (tamtime != null) {
						if (sysTime < tamtime.getTime()) {
							time = tamtime.getTime() - sysTime;
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				SQLUtil.close(rs);
				SQLUtil.close(pstm);
				SQLUtil.close(con);
			}
			if (_Name == null) {
				pc.sendPackets(new S_SystemMessage("������ �����Դϴ�. �ٽ� �õ����ּ���."), true);
				return;
			}
			if (time != 0) {
				tamadd(_Name, _objid, day, byteWrite(_objid));
				pc.sendPackets(new S_NewCreateItem(pc.getAccountName(), 0xcd));
				pc.sendPackets(new S_SystemMessage("[" + _Name + "] �� �̹� �̿����� ��ǰ�� �־� ���� �Ǿ����ϴ�."), true);
				pc.getInventory().removeItem(item, 1);
				return;
			} else if (tamcount >= 3) {// ���⿡�� ������ 3���Ծ����� üũ�ϸ�ɵ�
				pc.sendPackets(new S_SystemMessage("������ ���� 3���� ĳ���Ϳ��� ��� �����մϴ�."), true);
				return;
			}
			Timestamp deleteTime = null;
			deleteTime = new Timestamp(sysTime + (86400000 * (long) day) + 10000);// 7��

			// deleteTime = new Timestamp(sysTime + 1000*60);//7��

			if (pc.getId() == _objid) {
				pc.setTamTime(deleteTime);
				try {
					pc.save();
				} catch (Exception e) {

					e.printStackTrace();
				}
			} else {
				tamupdate(_objid, deleteTime);
			}

			pc.sendPackets(new S_NewCreateItem(pc.getAccountName(), 0xcd));
			int aftertamcount = pc.tamcount();
			int aftertamtime = (int) pc.TamTime();
			// System.out.println(aftertamtime);
			// long aaa = pc.TamTime();
			// System.out.println((int)aaa);

			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.Tam_Fruit1)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.Tam_Fruit1);
				pc.getAC().addAc(1);
			} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.Tam_Fruit2)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.Tam_Fruit2);
				pc.getAC().addAc(2);
			} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.Tam_Fruit3)) {
				pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.Tam_Fruit3);
				pc.getAC().addAc(3);
			} else {
			}

			if (aftertamtime < 0) {
				aftertamtime = 0;
			}

			if (aftertamcount == 1) {
				pc.getAC().addAc(-1);
				pc.getSkillEffectTimerSet().setSkillEffect(Tam_Fruit1, aftertamtime);
			} else if (aftertamcount == 2) {
				pc.getAC().addAc(-2);
				pc.getSkillEffectTimerSet().setSkillEffect(Tam_Fruit2, aftertamtime);
			} else if (aftertamcount == 3) {
				pc.getAC().addAc(-3);
				pc.getSkillEffectTimerSet().setSkillEffect(Tam_Fruit3, aftertamtime);
			}

			// pc.getAC().addAc(-1*pc.tamcount());
			pc.sendPackets(new S_OwnCharStatus(pc));
			pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.����â, pc.TamTime(), aftertamcount, true), true);
			pc.sendPackets(new S_ServerMessage(3916));
			pc.sendPackets(new S_SkillSound(pc.getId(), 2028), true);
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 2028), true);

			//

			pc.getInventory().removeItem(item, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c,
			0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e,
			0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0,
			0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2,
			0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4,
			0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6,
			0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
			0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff };

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

	private void ������(L1PcInstance pc, L1ItemInstance item) {
		if (pc.getAinHasad() > 10000) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) == true) {
				pc.sendPackets(new S_ServerMessage(2147), true);
				return;
			}
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_PUPLE)) {
				pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGON_PUPLE);
			}
			long sysTime = System.currentTimeMillis();
			Timestamp deleteTime = null;
			deleteTime = new Timestamp(sysTime + 1800000);
			pc.setTOPAZTime(deleteTime);

			if (pc.getPUPLETime() != null) {
				pc.setPUPLETime(null);
			}
			/*
			 * if(!pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.
			 * DRAGON_TOPAZ )){ pc.calAinHasad(1000000); pc.sendPackets(new
			 * S_PacketBox(S_PacketBox.AINHASAD, pc), true); }
			 */

			pc.getSkillEffectTimerSet().setSkillEffect(DRAGON_TOPAZ, 1800 * 1000);
			pc.sendPackets(new S_PacketBox(1800, 2, true, true), true);
			pc.sendPackets(new S_SkillSound(pc.getId(), 197), true);
			Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 197), true);

			try {
				pc.save();
			} catch (Exception e) {

				e.printStackTrace();
			}
			pc.getInventory().removeItem(item, 1);
		} else {
			pc.sendPackets(new S_SystemMessage("�ູ������ �־�� ����ϽǼ� �ֽ��ϴ�."), true);
		}
	}

	private void �巡�ﺸ��(L1PcInstance pc, L1ItemInstance item) {
		int hasad = 0;
		int skill = 0;
		int packet = 0;
		int msg = 0;
		int itemId = item.getItem().getItemId();
		int effect = 197;
		if (itemId == 1437010) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) == true) {
				pc.sendPackets(new S_ServerMessage(2146), true);
				return;
			}
			hasad = 5000000;
			int temphasad = pc.getAinHasad() + hasad;
			if (temphasad > 7010000) {
				pc.sendPackets(new S_SystemMessage("�����ϻ���� �ູ : �����ִ� �ູ������ ���� ��� �� �� �����ϴ�."));
				return;
			}

			Timestamp deleteTime = null;
			deleteTime = new Timestamp(System.currentTimeMillis() + 3600000 * 3);// 86400000);//
																					// 3��
			pc.setDETime2(deleteTime);

			skill = 7785;
			packet = 0x01;
			msg = 2142;
			effect = 198;
		} else if (itemId == L1ItemId.DRAGON_DIAMOND || itemId == 60291) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) == true) {
				pc.sendPackets(new S_ServerMessage(2146), true);
				return;
			}

			hasad = 1000000;
			int temphasad = pc.getAinHasad() + hasad;
			if (temphasad > 7010000) {
				pc.sendPackets(new S_SystemMessage("�����ϻ���� �ູ : �����ִ� �ູ������ ���� ��� �� �� �����ϴ�."));
				return;
			}

			Timestamp deleteTime = null;
			deleteTime = new Timestamp(System.currentTimeMillis() + 3600000 * 3);// 86400000);//
																					// 3��
			pc.setDETime2(deleteTime);
			skill = 7785;
			packet = 0x01;
			msg = 2142;
		} else if (itemId == L1ItemId.DRAGON_SAPHIRE) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) == true) {
				pc.sendPackets(new S_ServerMessage(2146), true);
				return;
			}

			hasad = 500000;
			int temphasad = pc.getAinHasad() + hasad;
			if (temphasad > 7010000) {
				pc.sendPackets(new S_SystemMessage("�����ϻ���� �ູ : �����ִ� �ູ������ ���� ��� �� �� �����ϴ�."));
				return;
			}

			Timestamp deleteTime = null;
			deleteTime = new Timestamp(System.currentTimeMillis() + 3600000 * 3);// 86400000);//
																					// 3��
			pc.setDETime2(deleteTime);
			skill = 7785;
			packet = 0x01;
			msg = 2142;
		} else if (itemId == L1ItemId.DRAGON_RUBY) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) == true) {
				pc.sendPackets(new S_ServerMessage(2146), true);
				return;
			}

			hasad = 300000;
			int temphasad = pc.getAinHasad() + hasad;
			if (temphasad > 7010000) {
				pc.sendPackets(new S_SystemMessage("�����ϻ���� �ູ : �����ִ� �ູ������ ���� ��� �� �� �����ϴ�."));
				return;
			}

			Timestamp deleteTime = null;
			deleteTime = new Timestamp(System.currentTimeMillis() + 3600000 * 3);// 86400000);//
																					// 3��
			pc.setDETime2(deleteTime);
			skill = 7785;
			packet = 0x01;
			msg = 2142;
		} else if (itemId == L1ItemId.DRAGON_EME || itemId == 60292) {
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_1) == true) {
				pc.sendPackets(new S_ServerMessage(2145), true);
				return;
			} else if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2) == true
					|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_PUPLE) == true
					|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_TOPAZ) == true) {
				pc.sendPackets(new S_ServerMessage(2147), true);
				return;
			}
			hasad = 1000000;
			int temphasad = pc.getAinHasad() + hasad;
			if (temphasad > 7010000) {
				pc.sendPackets(new S_SystemMessage("�����ϻ���� �ູ : �����ִ� �ູ������ ���� ��� �� �� �����ϴ�."));
				return;
			}

			Timestamp deleteTime = null;
			deleteTime = new Timestamp(System.currentTimeMillis() + 3600000 * 3);// 86400000);//
																					// 3��
			pc.setDETime(deleteTime);
			skill = 7786;
			packet = 0x02;
			msg = 2140;
		}

		pc.calAinHasad(hasad);
		pc.getSkillEffectTimerSet().setSkillEffect(skill, 10800 * 1000);
		pc.sendPackets(new S_PacketBox(S_PacketBox.AINHASAD, pc), true);
		pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME, packet, 10800), true);
		pc.sendPackets(new S_ServerMessage(msg), true);

		pc.sendPackets(new S_SkillSound(pc.getId(), effect), true);
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), effect), true);
		try {
			pc.save();
		} catch (Exception e) {
		}
		pc.getInventory().removeItem(item, 1);
	}

	private void useToiTeleportAmulets(L1PcInstance pc, int itemId, L1ItemInstance item) {
		boolean isTeleport = false;
		if (itemId >= 5001120 && itemId <= 5001129) { // 11,51Famulet
			if (pc.getX() >= 33923 && pc.getX() <= 33934 && pc.getY() >= 33340 && pc.getY() <= 33356
					&& pc.getMapId() == 4) {
				isTeleport = true;
			}
		}

		if (isTeleport) {
			pc.dx = item.getItem().get_locx();
			pc.dy = item.getItem().get_locy();
			pc.dm = item.getItem().get_mapid();
			pc.dh = pc.getMoveState().getHeading();
			pc.setTelType(7);
			pc.sendPackets(new S_SabuTell(pc), true);
		} else {
			pc.sendPackets(new S_SystemMessage("�ƹ��ϵ� �Ͼ�� �ʾҽ��ϴ�."), true); // \f1
																			// �ƹ��͵�
																			// �Ͼ��
																			// �ʾҽ��ϴ�.
		}
	}

	// õ���� ����
	private void UseExpPotion(L1PcInstance pc, int item_id) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																		// ����
			pc.sendPackets(new S_ServerMessage(698, ""), true); // ���¿� ���� �ƹ��͵�
																// ���� ���� �����ϴ�.
			return;
		}
		pc.cancelAbsoluteBarrier();

		int time = 0;
		int gfx = 0;
		if (item_id == L1ItemId.EXP_POTION || item_id == L1ItemId.EXP_POTION2) { // ����ġ
																					// ���
																					// ����
			time = 3600; // 60��
			gfx = 13249;
		} else if (item_id == L1ItemId.EXP_POTION_fairly) {
			time = 1800; // 30��
			gfx = 13249;
		} else if (item_id == L1ItemId.EXP_POTION_cash) {
			time = 1800; // 30��
			gfx = 13249;
		}

		pc.getSkillEffectTimerSet().removeSkillEffect(EXP_POTION);
		pc.getSkillEffectTimerSet().removeSkillEffect(EXP_POTION_cash);

		if (item_id == L1ItemId.EXP_POTION_cash) {
			pc.getSkillEffectTimerSet().setSkillEffect(EXP_POTION_cash, time * 1000);
		} else {
			pc.getSkillEffectTimerSet().setSkillEffect(EXP_POTION, time * 1000);
		}
		pc.sendPackets(new S_SkillSound(pc.getId(), gfx), true);
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), gfx), true);
		pc.sendPackets(new S_ServerMessage(1313), true);
		if (pc.getMap().isSafetyZone(pc.getX(), pc.getY())) {
			pc.sendPackets(new S_PacketBox(1, time, true, true, true));
			pc.sendPackets(new S_PacketBox(2, time, true, true, true));
		} else {
			pc.sendPackets(new S_PacketBox(1, time, true, true, true));
		}
	}

	private void UseExpPotion2(L1PcInstance pc, int item_id) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																		// ����
			pc.sendPackets(new S_ServerMessage(698, ""), true); // ���¿� ���� �ƹ��͵�
																// ���� ���� �����ϴ�.
			return;
		}
		pc.cancelAbsoluteBarrier();

		int time = 0;
		if (item_id == L1ItemId.EXP_POTION3 // �Է��� ��������
		) { // ����ġ ��� ����
			time = 3600; // 60��
		}

		pc.getSkillEffectTimerSet().setSkillEffect(EXP_POTION3, time * 1000);
		pc.sendPackets(new S_SkillSound(pc.getId(), 7013), true);
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7013), true);
		pc.sendPackets(new S_ServerMessage(1313), true);
	}

	private void EventT(L1PcInstance pc, L1ItemInstance useItem) {
		pc.getInventory().removeItem(useItem, 1);
		pc.getInventory().storeItem(L1ItemId.Inadril_T_ScrollA, 10);
		pc.getInventory().storeItem(L1ItemId.Inadril_T_ScrollB, 10);
		pc.getInventory().storeItem(L1ItemId.Inadril_T_ScrollC, 10);
		pc.getInventory().storeItem(L1ItemId.Inadril_T_ScrollD, 5);
	}

	private void ������ť��(L1PcInstance pc, L1ItemInstance useItem) {
		Timestamp lastUsed = useItem.getLastUsed();
		if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (1000 * 60 * 60 * 22)) {// 22�ð�
			if (pc.getInventory().checkItem(6016)) {
				pc.sendPackets(new S_SystemMessage("�κ� �丮�� �ҿ����� ���� ���� ������ ���� �ֽ��ϴ�."));
				return;
			}

			pc.getInventory().storeItem(6016, 1);
			pc.sendPackets(new S_ServerMessage(403, "�ҿ����� ���� ���� ����"), true);
			useItem.setChargeCount(useItem.getChargeCount() - 1);
			pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
			if (useItem.getChargeCount() == 0) {
				pc.getInventory().removeItem(useItem);
			}
			useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
		} else {
			long i = (lastUsed.getTime() + (60 * 60000 * 22)) - currentDate.getTimeInMillis();
			Calendar cal = (Calendar) currentDate.clone();
			cal.setTimeInMillis(cal.getTimeInMillis() + i);
			StringBuffer sb = new StringBuffer();
			sb.append(i / 60000).append("�� ��(");
			if (cal.getTime().getHours() < 10) {
				sb.append("0").append(cal.getTime().getHours()).append(":");
			} else {
				sb.append(cal.getTime().getHours()).append(":");
			}

			if (cal.getTime().getMinutes() < 10) {
				sb.append("0").append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
			} else {
				sb.append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
			}

			pc.sendPackets(new S_SystemMessage(sb.toString()), true);

		}
	}

	private void ������ť��(L1PcInstance pc, L1ItemInstance useItem) {
		Timestamp lastUsed = useItem.getLastUsed();
		if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (1000 * 60 * 30)) {// 22�ð�
			pc.getInventory().storeItem(7241, 1);
			pc.sendPackets(new S_ServerMessage(403, "�巡���� ������"), true);
			useItem.setChargeCount(useItem.getChargeCount() - 1);
			pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
			if (useItem.getChargeCount() == 0) {
				pc.getInventory().removeItem(useItem);
			}
			useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
		} else {
			long i = (lastUsed.getTime() + (30 * 60000)) - currentDate.getTimeInMillis();
			Calendar cal = (Calendar) currentDate.clone();
			cal.setTimeInMillis(cal.getTimeInMillis() + i);
			StringBuffer sb = new StringBuffer();
			sb.append(i / 60000).append("�� ��(");
			if (cal.getTime().getHours() < 10) {
				sb.append("0").append(cal.getTime().getHours()).append(":");
			} else {
				sb.append(cal.getTime().getHours()).append(":");
			}

			if (cal.getTime().getMinutes() < 10) {
				sb.append("0").append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
			} else {
				sb.append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
			}

			pc.sendPackets(new S_SystemMessage(sb.toString()), true);

		}
	}

	private void ����ť��(L1PcInstance pc, L1ItemInstance useItem) {
		pc.getInventory().storeItem(1437010, 1);
		pc.sendPackets(new S_ServerMessage(403, "�ູ���� �巡���� ���̾Ƹ��"), true);
		useItem.setChargeCount(useItem.getChargeCount() - 1);
		pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
		if (useItem.getChargeCount() == 0) {
			pc.getInventory().removeItem(useItem);
		}
	}

	private void �ð��Ǳ���ť��(L1PcInstance pc, L1ItemInstance useItem) {
		Timestamp lastUsed = useItem.getLastUsed();
		if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime() + (1000 * 60 * 10)) {// 22�ð�
			pc.getInventory().storeItem(500017, 1);
			pc.sendPackets(new S_ServerMessage(403, "�ð��� ����"), true);
			useItem.setChargeCount(useItem.getChargeCount() - 1);
			pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
			if (useItem.getChargeCount() == 0) {
				pc.getInventory().removeItem(useItem);
			}
			useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
		} else {
			long i = (lastUsed.getTime() + (10 * 60000)) - currentDate.getTimeInMillis();
			Calendar cal = (Calendar) currentDate.clone();
			cal.setTimeInMillis(cal.getTimeInMillis() + i);
			StringBuffer sb = new StringBuffer();
			sb.append(i / 60000).append("�� ��(");
			if (cal.getTime().getHours() < 10) {
				sb.append("0").append(cal.getTime().getHours()).append(":");
			} else {
				sb.append(cal.getTime().getHours()).append(":");
			}

			if (cal.getTime().getMinutes() < 10) {
				sb.append("0").append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
			} else {
				sb.append(cal.getTime().getMinutes()).append(")�� ����� �� �ֽ��ϴ�.");
			}

			pc.sendPackets(new S_SystemMessage(sb.toString()), true);

		}
	}

	private void �Ƹ�ī������������(L1PcInstance pc, L1ItemInstance useItem) {
		pc.getInventory().removeItem(useItem, 1);
		L1ItemInstance item = null;
		item = pc.getInventory().storeItem(600248, 5);
		pc.sendPackets(new S_ServerMessage(403, item.getName() + " (5)"));

		// item = pc.getInventory().storeItem(600249, 3);
		// pc.sendPackets(new S_ServerMessage(403, item.getName() + " (3)"));
	}

	private static final int ������ž�ֹ���[] = { 60200, 40104, 40105, 40106, 40107, 40108, 40109, 40110, 40111, 40112 };
	private static final int ������ž�̵�����[] = { 40284, 40285, 40286, 40287, 40288, 40283, 40282, 40281, 40280 };

	private void �����Ǻ�������(L1PcInstance pc, L1ItemInstance useItem) {
		pc.getInventory().removeItem(useItem, 1);
		int rnd = _random.nextInt(100000) + 1;
		L1ItemInstance sucitem = null;
		try {

			if (rnd == 1) {// 0.001%
				sucitem = ItemTable.getInstance().createItem(90084);// ��������ü�μҵ�
			} else if (rnd == 2) {// 0.001%
				sucitem = ItemTable.getInstance().createItem(59);// ���� ��հ�
			} else if (rnd <= 12) {
				sucitem = ItemTable.getInstance().createItem(20298);// ���Ͻ��ǹ���
			} else if (rnd <= 22) {
				sucitem = ItemTable.getInstance().createItem(20079);// �����̾��Ǹ���
			} else if (rnd <= 32) {
				sucitem = ItemTable.getInstance().createItem(20017);// �ӹ̷ε��ǿհ�
			} else if (rnd <= 42) {
				sucitem = ItemTable.getInstance().createItem(20107);// ��ġ�κ�
			} else if (rnd <= 52) {
				sucitem = ItemTable.getInstance().createItem(130220);// �ݺ����尩
			} else if (rnd <= 62) {
				sucitem = ItemTable.getInstance().createItem(22009);// �þ��ǽɾ�
			} else if (rnd <= 72) {
				sucitem = ItemTable.getInstance().createItem(20260);// ���̸����Ǹ����
			} else if (rnd <= 172) {
				sucitem = ItemTable.getInstance().createItem(20074);// �����Ǹ���
			} else if (rnd <= 5172) {
				sucitem = ItemTable.getInstance().createItem(������ž�̵�����[_random.nextInt(������ž�̵�����.length)]);
			} else {
				sucitem = ItemTable.getInstance().createItem(������ž�ֹ���[_random.nextInt(������ž�ֹ���.length)]);
			}

			if (sucitem != null) {
				pc.getInventory().storeItem(sucitem, true);
				pc.sendPackets(new S_ServerMessage(403, sucitem.getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * ��Ÿ�ٵ� ���� ���� ����� 1�� 5% �� �̽��� �Ǳ� 10�� 16% ����� �ֱ� 50�� 16% �׶�ī���� ���� 10�� 16%
	 * ��ũ���� ��ȥ�� ����ü 300�� 16% ������ ���� 100�� 15% �渶�� ���� 50�� 16%
	 */
	private void ����Ȳ�ǻ���(L1PcInstance pc, L1ItemInstance useItem) {
		pc.getInventory().removeItem(useItem, 1);
		int rnd = _random.nextInt(10000) + 1;
		L1ItemInstance sucitem = null;
		int count = 1;
		try {
			if (rnd <= 500) {
				sucitem = ItemTable.getInstance().createItem(40965);
			} else if (rnd <= 2100) {
				sucitem = ItemTable.getInstance().createItem(40445);
				count = 10;
			} else if (rnd <= 3700) {
				sucitem = ItemTable.getInstance().createItem(40677);
				count = 50;
			} else if (rnd <= 5400) {
				sucitem = ItemTable.getInstance().createItem(40525);
				count = 10;
			} else if (rnd <= 7000) {
				sucitem = ItemTable.getInstance().createItem(40969);
				count = 300;
			} else if (rnd <= 8500) {
				sucitem = ItemTable.getInstance().createItem(40967);
				count = 100;
			} else {
				sucitem = ItemTable.getInstance().createItem(40964);
				count = 50;
			}

			if (sucitem != null) {
				if (count != 1) {
					sucitem.setCount(count);
				}
				pc.getInventory().storeItem(sucitem, true);
				pc.sendPackets(new S_ServerMessage(403, sucitem.getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final int lv1doll[] = { 41248, 41250, 430000, 430002, 430004, 600241 };
	private static final int lv2doll[] = { 430001, 41249, 430500, 500108, 500109, 600242 };
	private static final int lv3doll[] = { 500205, 500204, 500203, 60324, 500110, 600243 };
	private static final int lv4doll[] = { 500202, 5000035, 600244, 60024, 1429215, 142920, 751 };
	private static final int lv5doll[] = { 600246, 600247, 142922, 752 };

	private void �Ƹ�ī���������ָӴ�(L1PcInstance pc, L1ItemInstance useItem) {
		pc.getInventory().removeItem(useItem, 1);
		int rnd = _random.nextInt(10000) + 1;
		int rnd2 = _random.nextInt(100) + 1;
		L1ItemInstance sucitem = null;
		try {
			if (rnd <= 8000) {// 80%1�ܰ�
				sucitem = ItemTable.getInstance().createItem(lv1doll[_random.nextInt(lv1doll.length)]);
			} else if (rnd <= 9500) {// 15%2�ܰ�
				sucitem = ItemTable.getInstance().createItem(lv2doll[_random.nextInt(lv2doll.length)]);
			} else if (rnd <= 9999) {// 0.99%3�ܰ�
				sucitem = ItemTable.getInstance().createItem(lv3doll[_random.nextInt(lv3doll.length)]);
			} else {// 4,5�ܰ� 0.01%
				if (rnd2 <= 80) {
					sucitem = ItemTable.getInstance().createItem(lv4doll[_random.nextInt(lv4doll.length)]);
				} else {
					sucitem = ItemTable.getInstance().createItem(lv5doll[_random.nextInt(lv5doll.length)]);
				}
			}

			if (sucitem != null) {
				pc.getInventory().storeItem(sucitem, true);
				pc.sendPackets(new S_ServerMessage(403, sucitem.getName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void UseExpPotion3(L1PcInstance pc, int item_id) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(71) == true) { // ����������
																		// ����
			pc.sendPackets(new S_ServerMessage(698, ""), true); // ���¿� ���� �ƹ��͵�
																// ���� ���� �����ϴ�.
			return;
		}
		pc.cancelAbsoluteBarrier();

		int time = 0;
		if (item_id == L1ItemId.EXP_POTION4 // �Է��� ����
		) { // ����ġ ��� ����
			time = 3600; // 60��
		}

		pc.getSkillEffectTimerSet().setSkillEffect(EXP_POTION2, time * 1000);
		pc.sendPackets(new S_SkillSound(pc.getId(), 7013), true);
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 7013), true);
		pc.sendPackets(new S_ServerMessage(1313), true);
	}

	private void ��ȥ��(L1PcInstance pc, L1ItemInstance useItem) {
		L1PcInstance partner = null;
		boolean partner_stat = false;
		if (pc.getPartnerId() != 0) { // ��ȥ��
			partner = (L1PcInstance) L1World.getInstance().findObject(pc.getPartnerId());
			if (partner != null && partner.getPartnerId() != 0 && pc.getPartnerId() == partner.getId()
					&& partner.getPartnerId() == pc.getId()) {
				partner_stat = true;
			}
		} else {
			pc.sendPackets(new S_SystemMessage("����� ���� ��ȥ�� ���°� �ƴմϴ�."), true); // \f1�����
			// ��ȥ����
			// �ʾҽ��ϴ�.
			return;
		}

		if (useItem.getChargeCount() <= 0) {
			return;
		}
		if (pc.getMapId() == 666) {
			return;
		}
		if (!����(partner)) {
			pc.sendPackets(new S_SystemMessage("����� ��Ʈ�ʴ� ���� ����� �� �� ���� ������ �÷��̸� �ϰ� �ֽ��ϴ�."), true); // \f1�����
			return;
		}

		if (partner_stat) {
			boolean castle_area = L1CastleLocation.checkInAllWarArea(partner.getX(), partner.getY(),
					partner.getMapId());

			if ((partner.getMapId() == 0 || partner.getMapId() == 4 || partner.getMapId() == 304)

					&& castle_area == false) {

				if (pc.getMapId() == 4 && ((pc.getX() >= 33331 && pc.getX() <= 33341 && pc.getY() >= 32430
						&& pc.getY() <= 32441)
						|| (pc.getX() >= 33258 && pc.getX() <= 33267 && pc.getY() >= 32396 && pc.getY() <= 32407) ||

						(pc.getX() >= 34197 && pc.getX() <= 34302 && pc.getY() >= 33104 && pc.getY() <= 33533
								&& pc.getMap().isNormalZone(pc.getX(), pc.getY()))
						|| // Ȳȥ�ǻ��

						(pc.getX() >= 33453 && pc.getX() <= 33468 && pc.getY() >= 32331 && pc.getY() <= 32341) || // �Ƶ����ѱ���

						(pc.getX() >= 33388 && pc.getX() <= 33397 && pc.getY() >= 32339 && pc.getY() <= 32350)
						|| (pc.getX() >= 33464 && pc.getX() <= 33531 && pc.getY() >= 33168 && pc.getY() <= 33248) // ||
				)) {
					pc.sendPackets(new S_SystemMessage("����� ��Ʈ�ʴ� ���� ����� �� �� ���� ������ �÷��̸� �ϰ� �ֽ��ϴ�."), true); // \f1�����
					// ��Ʈ�ʴ�
					// ����
					// ����� ��
					// �� ����
					// ������
					// �÷������Դϴ�.
				} else {
					if (pc.Sabutelok()) {
						useItem.setChargeCount(useItem.getChargeCount() - 1);
						pc.getInventory().updateItem(useItem, L1PcInventory.COL_CHARGE_COUNT);
						pc.dx = partner.getX();
						pc.dy = partner.getY();
						pc.dm = (short) partner.getMapId();
						pc.dh = pc.getMoveState().getHeading();
						pc.setTelType(7);
						pc.sendPackets(new S_SabuTell(pc), true);
					}
				}
			} else {
				pc.sendPackets(new S_SystemMessage("����� ��Ʈ�ʴ� ���� ����� �� �� ���� ������ �÷��̸� �ϰ� �ֽ��ϴ�."), true); // \f1�����
				// ��Ʈ�ʴ�
				// ����
				// ����� ��
				// �� ����
				// ������
				// �÷������Դϴ�.
			}
		} else {
			pc.sendPackets(new S_SystemMessage("����� ��Ʈ�ʴ� ���� ������ �÷����ϰ� ���� �ʽ��ϴ�."), true); // \f1�����
			// ��Ʈ�ʴ� ����
			// �÷��̸� �ϰ�
			// ���� �ʽ��ϴ�.
		}
	}

	private void useCashScroll(L1PcInstance pc, int item_id, boolean ok) {
		int time = 0;
		if (ok) {
			time = 3600;
		} else {
			time = 1800;
		}

		int scroll = 0;

		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CASHSCROLL)) {
			pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_CASHSCROLL);
			pc.addHpr(-4);
			pc.addMaxHp(-50);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
			if (pc.isInParty()) {
				pc.getParty().updateMiniHP(pc);
			}
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CASHSCROLL2)) {
			pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_CASHSCROLL2);
			pc.addMpr(-4);
			pc.addMaxMp(-40);
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_CASHSCROLL3)) {
			pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_CASHSCROLL3);
			pc.addDmgup(-3);
			pc.addHitup(-3);
			pc.getAbility().addSp(-3);
			pc.sendPackets(new S_SPMR(pc), true);
		}

		if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_�ÿ��Ѿ�������)) {
			pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_�ÿ��Ѿ�������);
		}

		if (item_id == L1ItemId.INCRESE_HP_SCROLL || item_id == L1ItemId.CHUNSANG_HP_SCROLL) {
			scroll = 7893;
			pc.addHpr(4);
			pc.addMaxHp(50);
			pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
			if (pc.isInParty()) {
				pc.getParty().updateMiniHP(pc);
			}
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);

		} else if (item_id == L1ItemId.INCRESE_MP_SCROLL || item_id == L1ItemId.CHUNSANG_MP_SCROLL) {
			scroll = 7894;
			pc.addMpr(4);
			pc.addMaxMp(40);
			pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
		} else if (item_id == L1ItemId.INCRESE_ATTACK_SCROLL || item_id == L1ItemId.CHUNSANG_ATTACK_SCROLL) {
			scroll = 7895;
			pc.addDmgup(3);
			pc.addHitup(3);
			pc.getAbility().addSp(3);
			pc.sendPackets(new S_SPMR(pc), true);
		} else if (item_id == 60354) {// �ÿ��Ѿ�������
			scroll = 8485;
			pc.addDmgup(2);
			pc.getAbility().addSp(2);
			pc.addHpr(1);
			pc.addMpr(1);
			pc.sendPackets(new S_SPMR(pc), true);
		}

		pc.sendPackets(new S_SkillSound(pc.getId(), scroll), true);
		Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), scroll), true);

		pc.getSkillEffectTimerSet().setSkillEffect(scroll, time * 1000);
	}

	private boolean createNewItem(L1PcInstance pc, int item_id, int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // ���� �� ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
			}
			pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true); // %0��
																				// �տ�
																				// �־����ϴ�.
			return true;
		} else {
			return false;
		}
	}

	private boolean ��Ȯ�λ�����(L1PcInstance pc, int item_id, int count, int EnchantLevel) {
		return ��Ȯ�λ�����2(pc, item_id, count, EnchantLevel, 0, 0);
	}

	private boolean createNewItem2(L1PcInstance pc, int item_id, int count, int EnchantLevel) {
		return createNewItem2(pc, item_id, count, EnchantLevel, 0, 0);
	}

	private boolean ��Ȯ�λ�����2(L1PcInstance pc, int item_id, int count, int EnchantLevel, int attrEnc, int SpiritIn) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);

		if (item != null) {
			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(false);
			item.setAttrEnchantLevel(attrEnc);
			item.setRegistLevel(SpiritIn);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // ���� �� ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
			}
			pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true); // %0��
																				// �տ�
																				// �־����ϴ�.
			return true;
		} else {
			return false;
		}
	}

	private boolean createNewItem2(L1PcInstance pc, int item_id, int count, int EnchantLevel, int attrEnc,
			int SpiritIn) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);

		if (item != null) {

			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(true);
			item.setAttrEnchantLevel(attrEnc);
			item.setRegistLevel(SpiritIn);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // ���� �� ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
			}
			pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true); // %0��
																				// �տ�
																				// �־����ϴ�.
			return true;
		} else {
			return false;
		}
	}

	private L1ItemInstance createNewItem2(L1PcInstance pc, int item_id, int count, int EnchantLevel, int attrEnc,
			int SpiritIn, int bless) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);

		if (item != null) {

			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(true);
			item.setAttrEnchantLevel(attrEnc);
			item.setRegistLevel(SpiritIn);
			item.setBless(bless);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // ���� �� ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance().getInventory(pc.getX(), pc.getY(), pc.getMapId()).storeItem(item);
			}
			pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true); // %0��
																				// �տ�
																				// �־����ϴ�.
			return item;
		} else {
			return null;
		}
	}

	private void useToiTeleportAmulet(L1PcInstance pc, int itemId, L1ItemInstance item) {
		boolean isTeleport = false;

		if (itemId == 60202 || itemId >= 40289 && itemId <= 40297) { // ��������
																		// 11~91������
			if (pc.getMap().isEscapable()) { // ��ȯ���������ΰ��� �˻��Ѵ�
				if (pc.getX() >= 33923 && pc.getX() <= 33934 && pc.getY() >= 33340 && pc.getY() <= 33356
						&& pc.getMapId() == 4) {
					isTeleport = true;
				}
			}
		}

		if (isTeleport) {
			pc.dx = item.getItem().get_locx();
			pc.dy = item.getItem().get_locy();
			pc.dm = item.getItem().get_mapid();
			pc.dh = pc.getMoveState().getHeading();
			pc.setTelType(7);
			pc.sendPackets(new S_SabuTell(pc), true);
		} else {
			pc.sendPackets(new S_ServerMessage(3236), true); // \f1 �ƹ��͵� �Ͼ��
																// �ʾҽ��ϴ�.
		}
	}

	private boolean withdrawPet(L1PcInstance pc, int itemObjectId) {
		if (!pc.getMap().isTakePets()) {
			pc.sendPackets(new S_ServerMessage(563)); // \f1 ���⿡���� ����� �� �����ϴ�.
			return false;
		}

		int petCost = 0;
		for (Object pet : pc.getPetList()) {
			if (pet instanceof L1PetInstance) {
				if (((L1PetInstance) pet).getItemObjId() == itemObjectId) { // �̹�
																			// ������
																			// �ִ�
																			// �ֿϵ���
					return false;
				}
			}
			petCost += ((L1NpcInstance) pet).getPetcost();
		}
		int charisma = pc.getAbility().getTotalCha();
		if (pc.isCrown()) { // CROWN
			charisma += 6;
		} else if (pc.isElf()) { // ELF
			charisma += 12;
		} else if (pc.isWizard()) { // WIZ
			charisma += 6;
		} else if (pc.isDarkelf()) { // DE
			charisma += 6;
		} else if (pc.isDragonknight()) { // ����
			charisma += 6;
		} else if (pc.isIllusionist()) { // ȯ����
			charisma += 6;
		}

		charisma -= petCost;
		int petCount = charisma / 6;
		if (petCount <= 0) {
			pc.sendPackets(new S_ServerMessage(489), true); // ���������� �ϴ� �ֿϵ����� �ʹ�
															// �����ϴ�.
			return false;
		}

		L1Pet l1pet = PetTable.getInstance().getTemplate(itemObjectId);
		if (l1pet != null) {
			L1Npc npcTemp = NpcTable.getInstance().getTemplate(l1pet.get_npcid());
			L1PetInstance pet = new L1PetInstance(npcTemp, pc, l1pet);
			if (l1pet.get_npcid() == 45313 || l1pet.get_npcid() == 45710 || l1pet.get_npcid() == 45711
					|| l1pet.get_npcid() == 45712)
				pet.setPetcost(12);
			else
				pet.setPetcost(6);
			pet.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_PET_FOOD, pet.getFoodTime() * 1000);
		}
		return true;
	}

	private void IdentMapWand(L1PcInstance pc, int locX, int locY) {
		pc.sendPackets(new S_SystemMessage("Gab :" + pc.getMap().getOriginalTile(locX, locY) + ",x :" + locX + ",y :"
				+ locY + ", mapId :" + pc.getMapId()), true);
		if (pc.getMap().isCloseZone(locX, locY)) {
			pc.sendPackets(new S_EffectLocation(locX, locY, (short) 10), true);
			Broadcaster.broadcastPacket(pc, new S_EffectLocation(locX, locY, (short) 10), true);
			pc.sendPackets(new S_SystemMessage("������ �ν���"), true);
		}
	}

	private void MapFixKeyWand(L1PcInstance pc, int locX, int locY) {
		String key = new StringBuilder().append(pc.getMapId()).append(locX).append(locY).toString();
		if (!pc.getMap().isCloseZone(locX, locY)) {
			if (!MapFixKeyTable.getInstance().isLockey(key)) {
				MapFixKeyTable.getInstance().storeLocFix(locX, locY, pc.getMapId());
				pc.sendPackets(new S_EffectLocation(locX, locY, (short) 1815), true);
				Broadcaster.broadcastPacket(pc, new S_EffectLocation(locX, locY, (short) 1815), true);
				pc.sendPackets(new S_SystemMessage("key�߰� ,x :" + locX + ",y :" + locY + ", mapId :" + pc.getMapId()),
						true);
			}
		} else {
			pc.sendPackets(new S_SystemMessage("������ǥ�� ���� �ƴմϴ�."), true);

			if (MapFixKeyTable.getInstance().isLockey(key)) {
				MapFixKeyTable.getInstance().deleteLocFix(locX, locY, pc.getMapId());
				pc.sendPackets(new S_EffectLocation(locX, locY, (short) 10), true);
				Broadcaster.broadcastPacket(pc, new S_EffectLocation(locX, locY, (short) 10), true);
				pc.sendPackets(new S_SystemMessage("key���� ,x :" + locX + ",y :" + locY + ", mapId :" + pc.getMapId()),
						true);
			}
		}
	}

	private void StatInitialize(L1PcInstance pc) {
		L1SkillUse l1skilluse = new L1SkillUse();
		l1skilluse.handleCommands(pc, L1SkillId.CANCELLATION, pc.getId(), pc.getX(), pc.getY(), null, 0,
				L1SkillUse.TYPE_LOGIN);
		l1skilluse = null;

		if (pc.getInventory().getTypeEquipped(2, 14) != 0) {// �� ����
			L1ItemInstance item = pc.getInventory().getItemEquipped(2, 14);
			if (item != null) {
				pc.getInventory().setEquipped(item, false, false, false);
			}
		}

		pc.getInventory().takeoffEquip(945);
		pc.sendPackets(new S_CharVisualUpdate(pc), true);
		pc.setReturnStat(pc.getExp());
		pc.setReturnStatus(1);
		pc.sendPackets(new S_SPMR(pc), true);
		pc.sendPackets(new S_OwnCharAttrDef(pc), true);
		pc.sendPackets(new S_OwnCharStatus2(pc), true);
		pc.sendPackets(new S_ReturnedStat(pc, S_ReturnedStat.START), true);
		try {
			pc.save();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	private boolean CheckEffects(L1PcInstance pc) {
		if (pc.getSkillEffectTimerSet().hasSkillEffect(76711) || pc.getSkillEffectTimerSet().hasSkillEffect(76721)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(76731)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(76741)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(76751)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(76761)
				|| pc.getSkillEffectTimerSet().hasSkillEffect(76771)) {
			pc.sendPackets(new S_ServerMessage(1594));
			return false;
		}
		return true;
	}

	public int checkObject(int x, int y, short m, int d) {
		L1Map map = L1WorldMap.getInstance().getMap(m);
		switch (d) {
		case 1:
			if (map.isPassable(x, y, 1)) {
				return 1;
			} else if (map.isPassable(x, y, 0)) {
				return 0;
			} else if (map.isPassable(x, y, 2)) {
				return 2;
			}
			break;
		case 2:
			if (map.isPassable(x, y, 2)) {
				return 2;
			} else if (map.isPassable(x, y, 1)) {
				return 1;
			} else if (map.isPassable(x, y, 3)) {
				return 3;
			}
			break;
		case 3:
			if (map.isPassable(x, y, 3)) {
				return 3;
			} else if (map.isPassable(x, y, 2)) {
				return 2;
			} else if (map.isPassable(x, y, 4)) {
				return 4;
			}
			break;
		case 4:
			if (map.isPassable(x, y, 4)) {
				return 4;
			} else if (map.isPassable(x, y, 3)) {
				return 3;
			} else if (map.isPassable(x, y, 5)) {
				return 5;
			}
			break;
		case 5:
			if (map.isPassable(x, y, 5)) {
				return 5;
			} else if (map.isPassable(x, y, 4)) {
				return 4;
			} else if (map.isPassable(x, y, 6)) {
				return 6;
			}
			break;
		case 6:
			if (map.isPassable(x, y, 6)) {
				return 6;
			} else if (map.isPassable(x, y, 5)) {
				return 5;
			} else if (map.isPassable(x, y, 7)) {
				return 7;
			}
			break;
		case 7:
			if (map.isPassable(x, y, 7)) {
				return 7;
			} else if (map.isPassable(x, y, 6)) {
				return 6;
			} else if (map.isPassable(x, y, 0)) {
				return 0;
			}
			break;
		case 0:
			if (map.isPassable(x, y, 0)) {
				return 0;
			} else if (map.isPassable(x, y, 7)) {
				return 7;
			} else if (map.isPassable(x, y, 1)) {
				return 1;
			}
			break;
		default:
			break;
		}
		return -1;
	}

	private static FastMap<String, Long> �ð����׾Ƹ�_����Ƚ�� = new FastMap<String, Long>();

	public static boolean get�ð��׾Ƹ�_����Ƚ��(String account, long time) {
		synchronized (�ð����׾Ƹ�_����Ƚ��) {
			try {
				if (�ð����׾Ƹ�_����Ƚ�� != null) {
					if (�ð����׾Ƹ�_����Ƚ��.containsKey(account)) {
						return false;
					} else {
						�ð����׾Ƹ�_����Ƚ��.put(account, time);
					}
				}
			} catch (Exception e) {
			}

			return true;
		}
	}

	public static void reset�ð����׾Ƹ�Ƚ��() {
		synchronized (�ð����׾Ƹ�_����Ƚ��) {
			�ð����׾Ƹ�_����Ƚ��.clear();
		}
	}

	private static FastMap<String, Integer> �����ָӴ�_����Ƚ�� = new FastMap<String, Integer>();

	public static boolean get�����ָӴ�_����Ƚ��(String account) {
		synchronized (�����ָӴ�_����Ƚ��) {
			int time = 0;
			try {
				time = �����ָӴ�_����Ƚ��.get(account);
			} catch (Exception e) {
			}
			if (time >= 3)
				return false;
			�����ָӴ�_����Ƚ��.put(account, ++time);
			return true;
		}
	}

	public static void reset�����ָӴ�_����Ƚ��() {
		synchronized (�����ָӴ�_����Ƚ��) {
			�����ָӴ�_����Ƚ��.clear();
		}
	}

	@Override
	public String getType() {
		return C_ITEM_USE;
	}
}