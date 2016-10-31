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

import static l1j.server.server.model.skill.L1SkillId.EARTH_BIND;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Gamble.GambleInstance;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.server.BadNamesList;
import l1j.server.server.GMCommands;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.UserCommands;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.ExcludeLetterTable;
import l1j.server.server.datatables.ExcludeTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.MonsterBookTeleportTable;
import l1j.server.server.datatables.NpcActionTable;
import l1j.server.server.datatables.NpcShopSpawnTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1ExcludingLetterList;
import l1j.server.server.model.L1ExcludingList;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.npc.L1NpcHtml;
import l1j.server.server.model.npc.action.L1NpcAction;
import l1j.server.server.model.npc.action.L1NpcMakeItemAction;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_BitPacket;
import l1j.server.server.serverpackets.S_CharMapTime;
import l1j.server.server.serverpackets.S_CreateItem;
import l1j.server.server.serverpackets.S_DollAlchemyInfo;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_NewCreateItem_list;
import l1j.server.server.serverpackets.S_NewUI;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1UserRanking;
import l1j.server.server.utils.BinaryOutputStream;
import l1j.server.server.utils.CommonUtil;
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;
import server.controller.InvSwapController;
import server.manager.eva;

public class C_NewCreateItem extends ClientBasePacket {
	private static final String C_NEW_CREATEITEM = "[C] C_NewCreateItem";
	private static final String[] textFilter = { "��ȣ", "�ູ", "����", "ĭ��", "ö��", "����", "����", "����", "����", "����" };

	private static final int RESTART_UI = 802;
	private static final int MONSTER_BOOKS_TELEPORT = 565;
	//private static final int MONSTER_BOOKS_TELEPORT = 53;
	private static final int CHAT = 514;
	
	public C_NewCreateItem(byte[] decrypt, LineageClient client) {
		super(decrypt);
		try {
			int type = readH();

			L1PcInstance pc = client.getActiveChar();
			if (type != 0x01e4) {
				if (pc == null)
					return;
			}
			
			if (type == MONSTER_BOOKS_TELEPORT) {
				readH();
				readC();
				int a = read4(read_size());
				int b = (a / 3) + 1;
				MonsterBookTeleportTable mbt = MonsterBookTeleportTable.getInstance();
				MonsterBookTeleportTable.BooksTeleportLoc bl = mbt.getTeleportLoc(b);
				if (bl == null)
					return;
				L1Location baseloc = new L1Location(bl._getX, bl._getY, bl._getMapId);
				int random = bl._random;
				L1Location newloc = L1Location.randomLocation(baseloc, 0, random, true);
				int itemId = bl._itemId;
				if (pc.getInventory().checkItem(itemId, 1)) {
					pc.getInventory().consumeItem(itemId, 1);
					L1Teleport.teleport(pc, newloc, 5, true);
				} else {
					pc.sendPackets(new S_BitPacket(S_BitPacket.TELEPORT_FAIL));
				}
				
				

				
			} else if (type == RESTART_UI) {

				pc.sendPackets(new S_CharMapTime(pc));

			} else if (type == CHAT) {
				int totallen = readH();// ��ü����
				��Ŷ��ġ����((byte) 0x10);// ��ġ�̵�
				int chattype = readC();// ä��Ÿ��
				��Ŷ��ġ����((byte) 0x1a);// ��ġ�̵�
				int chatlen = readC();// ä�ñ���
				BinaryOutputStream os = new BinaryOutputStream();
				for (int i = 0; i < chatlen; i++) {
					os.writeC(readC());
				}
				byte[] chat = os.getBytes();
				String chat2 = new String(chat, "EUC-KR");
				os.close();
				String name = "";

				if (chattype != 1) {
					Chat(pc, chattype, totallen, chat, chat2, client);
					return;
				}

				��Ŷ��ġ����((byte) 0x2a);// ��ġ�̵�
				int namelen = readC();// �̸�����
				if (namelen != 0) {
					name = readS(namelen);
				}
				ChatWhisper(pc, chattype, totallen, chat, chat2, name);
			} else if (type == 801) {
				readH();

				// readC();
				int index = readC();
				int code = readC();
				if (index == 0x08) {
					InvSwapController.getInstance().toSaveSet(pc, code);
				} else if (index == 0x10) {
					InvSwapController.getInstance().toChangeSet(pc, code);
				}
			} else if (type == 0x87) {
				readH();
				readC();
				int classId = readC();
				ArrayList<L1UserRanking> list = UserRankingController.getInstance().getList(classId);

				if (list.size() > 100) {
					List<L1UserRanking> cutlist = list.subList(0, 100);
					List<L1UserRanking> cutlist2 = list.subList(100, list.size());
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.������ŷ, cutlist, classId, 2, 1));
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.������ŷ, cutlist2, classId, 2, 2));
				} else {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.������ŷ, list, classId, 1, 1));
				}
			} else if (type == 0x021F) {
				readH();
				readC();
				int excludeType = readC();
				readC();
				int subType = readC();
				int nameFlag = readC();
				String name = "";
				if (nameFlag == 0x1A) {
					int nameLength = readC();
					name = readS(nameLength);
				}

				try {
					if (name.isEmpty()) {
						pc.sendPackets(new S_PacketBox(S_PacketBox.ADD_EXCLUDE2, pc.getExcludingList().getList(), 0),
								true);
						pc.sendPackets(
								new S_PacketBox(S_PacketBox.ADD_EXCLUDE2, pc.getExcludingLetterList().getList(), 1),
								true);
						return;
					}

					if (excludeType == 1) {// �߰�
						L1ExcludingList exList = pc.getExcludingList();
						L1ExcludingLetterList exletterList = pc.getExcludingLetterList();
						switch (subType) {// �Ϲ� 0 ���� 1
						case 0:
							if (exList.contains(name)) {
								/*
								 * String temp = exList.remove(name);
								 * S_PacketBox pb = new
								 * S_PacketBox(S_PacketBox.REM_EXCLUDE, temp,
								 * type); pc.sendPackets(pb, true);
								 * ExcludeTable.getInstance().delete(pc.getName(
								 * ), name);
								 */
							} else {
								if (exList.isFull()) {
									S_SystemMessage sm = new S_SystemMessage("���ܵ� ����ڰ� �ʹ� �����ϴ�.");
									pc.sendPackets(sm, true);
									return;
								}
								exList.add(name);
								S_PacketBox pb = new S_PacketBox(S_PacketBox.ADD_EXCLUDE, name, 0);
								pc.sendPackets(pb, true);
								ExcludeTable.getInstance().add(pc.getName(), name);
							}
							break;
						case 1:
							if (exletterList.contains(name)) {
								/*
								 * String temp = exList.remove(name);
								 * S_PacketBox pb = new
								 * S_PacketBox(S_PacketBox.REM_EXCLUDE, temp,
								 * type); pc.sendPackets(pb, true);
								 * ExcludeLetterTable
								 * .getInstance().delete(pc.getName(), name);
								 */
							} else {
								if (exletterList.isFull()) {
									S_SystemMessage sm = new S_SystemMessage("���ܵ� ����ڰ� �ʹ� �����ϴ�.");
									pc.sendPackets(sm, true);
									return;
								}
								exletterList.add(name);
								S_PacketBox pb = new S_PacketBox(S_PacketBox.ADD_EXCLUDE, name, 1);
								pc.sendPackets(pb, true);
								ExcludeLetterTable.getInstance().add(pc.getName(), name);
							}
							break;
						default:
							break;
						}
					} else if (excludeType == 2) {// ����
						L1ExcludingList exList = pc.getExcludingList();
						L1ExcludingLetterList exletterList = pc.getExcludingLetterList();
						switch (subType) {// �Ϲ� 0 ���� 1
						case 0:
							if (exList.contains(name)) {
								String temp = exList.remove(name);
								S_PacketBox pb = new S_PacketBox(S_PacketBox.REM_EXCLUDE, temp, 0);
								pc.sendPackets(pb, true);
								ExcludeTable.getInstance().delete(pc.getName(), name);
							}
							break;
						case 1:
							if (exletterList.contains(name)) {
								String temp = exletterList.remove(name);
								S_PacketBox pb = new S_PacketBox(S_PacketBox.REM_EXCLUDE, temp, 1);
								pc.sendPackets(pb, true);
								ExcludeLetterTable.getInstance().delete(pc.getName(), name);
							}
							break;
						default:
							break;
						}
					}
				} catch (Exception e) {
				}
			} else

			if (type == 0x44) {

				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_WAIT, true), true);

			} else if (type == 0x36) {
				// if (client.��������Ŷ������)
				// return;
				// readD();
				// int data = readH();
				// // System.out.println(data);
				// if (data != 0 && !client.��������Ŷ����) {
				// client.��������Ŷ���� = true;
				// if(data != 7582)
				// client.��������Ŷ���� = false;
				// }
				// if (client.��������Ŷ����) {
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.WINDOW), true);
				// } else {
				// client.��������Ŷ������ = true;
				// client.��������Ŷ���� = true;
				// GeneralThreadPool.getInstance().schedule(
				// new Send_createitemList(client), 1);
				// }

			} else if (type == 0x38) {// ����Ʈ
				if (pc.talkingNpcObjid == 0)
					return;
				L1Object obj = L1World.getInstance().findObject(pc.talkingNpcObjid);
				if (obj == null)
					return;

				if (obj instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					if (npc.getNpcTemplate().get_npcId() == 301028 || npc.getNpcTemplate().get_npcId() == 11887
							|| npc.getNpcTemplate().get_npcId() == 7310086 || npc.getNpcTemplate().get_npcId() == 70662
							|| npc.getNpcTemplate().get_npcId() == 70027|| npc.getNpcTemplate().get_npcId() == 7210071) {
						pc.sendPackets(new S_CreateItem(npc), true);
						pc.createItemNpcObjid = pc.talkingNpcObjid;
						pc.talkingNpcObjid = 0;
						return;
					}
				}
				pc.sendPackets(new S_NewCreateItem(pc, obj), true);
				pc.createItemNpcObjid = pc.talkingNpcObjid;
				pc.talkingNpcObjid = 0;
			} else if (type == 0x5c) {// �뼺�� ���� �����۵�
				if (pc.getInventory().calcWeightpercent() >= 90) {
					pc.sendPackets(new S_SystemMessage("���� ����: ���� ������ 90% �̻� ���� �Ұ�."));
					return;
				}
				int len = readC() - 3;
				for (int i = 0; i < len; i++) {
					readC();
				}
				readH();
				readC();
				readC();
				pc.sendPackets(new S_NewCreateItem(0X5D, "08 00 10 e3 03 56 ce"));// 1479
			} else if (type == 0x3A) {// ����
				if (pc.getInventory().calcWeightpercent() >= 90) {
					pc.sendPackets(new S_SystemMessage("���� ����: ���� ������ 90% �̻� ���� �Ұ�."));
					return;
				}
				readH();
				readC();
				readH();
				readD();
				int itemcode = readC();
				int subcode = readC();
				if (subcode != 0x18) {
					readC();
					itemcode |= subcode << 8 & 0xff00;
				}
				int count = readC();
				int ��þ����� = 0;
				try {
					readC();
					readD();
					��þ����� = readC();
					int �߰�byte = readC();
					if (�߰�byte != 0x22) {
						��þ����� |= �߰�byte / 2 << 8 & 0xff00;
					}
					��þ����� = ��þ�ۺ�ȯ(��þ�����);
				} catch (Exception e) {
				}
				if (count <= 0 || count >= 100)
					return;

				// System.out.println(itemcode);
				/** ���۸���Ʈ �ڵ带 ��� id�� **/
				int itemid = S_NewCreateItem.createItemByCode(itemcode);

				L1Object obj = L1World.getInstance().findObject(pc.createItemNpcObjid);
				if (obj == null)
					return;

				String s = "08 00 12 29 08 d3 7a 10 01 18 ff ff ff "
						+ "ff ff ff ff ff ff 01 20 00 28 01 30 00 38 01 42 " + "06 24 31 30 39 34 37 48 00 50 00 58 b0 "
						+ "2d 62 00 " + "9d 80";

				String ss = "08 01 1a 04 08 00 10 00 85 32"; // ����
				// �����Ҽ��ִ� ������ �ڵ��
				if (itemcode >= 3555 && itemcode <= 3570) {
					if (CommonUtil.random(100) < 60) {// ����
						pc.getInventory().consumeItem(3000051, 1);
						pc.sendPackets(new S_NewCreateItem(0X3b, ss));
						// pc.sendPackets(new S_SkillSound(pc.getId(), 2029));
						return;
					}
				}

				/** itemcode�� �׼ǰ����� ó������ **/
				String actions = codeByAction(itemcode);

				/** �ҽ��� ó�� **/
				if (Action_ItemCode(pc, itemcode, count)) {
					pc.sendPackets(new S_NewCreateItem(0X3b, s));// 1479
					// pc.sendPackets(new S_ServerMessage(3556), true);
					pc.sendPackets(new S_SkillSound(pc.getId(), 2029));
				} else if (Action(pc, itemid, ��þ�����, count)) {
					// pc.sendPackets(new S_ServerMessage(3556), true);
					pc.sendPackets(new S_NewCreateItem(0X3b, s));// 1479
					pc.sendPackets(new S_SkillSound(pc.getId(), 2029));
					/** �׼� ������ ���� ó�� **/
				} else if (!actions.equalsIgnoreCase("")) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					L1NpcAction action = NpcActionTable.getInstance().get(actions, pc, npc);
					if (action != null) {
						L1NpcHtml result = action.executeWithAmount(actions, pc, npc, count);
						// pc.sendPackets(new S_ServerMessage(3556), true);
						pc.sendPackets(new S_NewCreateItem(0X3b, s));// 1479
						pc.sendPackets(new S_SkillSound(pc.getId(), 2029));
						if (result != null) {
							pc.sendPackets(new S_NPCTalkReturn(npc.getId(), result), true);
						}
					}
					/** NPCID�� ����ItemId�� ���� ó�� **/
				} else {

					FastTable<L1NpcAction> list = NpcActionTable.getInstance().getlist(pc, obj);
					if (list.size() <= 0)
						return;
					L1NpcMakeItemAction nma = null;
					for (int i = 0; i < list.size(); i++) {
						L1NpcMakeItemAction na = (L1NpcMakeItemAction) list.get(i);
						if (na.getItemId(obj) == itemid) {
							nma = na;
							break;
						}
					}
					// System.out.println("123123123");
					if (nma == null)
						return;
					// pc.sendPackets(new S_NewCreateItem(0X3b,s));//1479
					// 123
					nma.executeWithAmount("newCreateItem", pc, obj, count);

					// pc.sendPackets(new S_SkillSound(pc.getId(), 2029));
					// pc.sendPackets(new S_ServerMessage(3556), true);
				}

				// System.out.println(itemcode+" > "+count);
			} else if (type == 0x013D) { // ���� ���� �� ǥ��
				/*
				 * String s = "3e 01 0a 29 08 01 12 08 52 65 61 6c 42 6f 53 "+
				 * "73 1a 08 52 65 61 6c 42 6f 73 73 20 14 28 9a fb "+
				 * "92 91 0c 30 ac eb c9 d2 01 38 e4 f5 a4 69 0a 1f "+
				 * "08 02 12 06 24 31 36 35 34 38 1a 00 20 00 28 aa "+
				 * "81 92 91 0c 30 dc ac e4 91 01 38 bc 96 f2 48 0a "+
				 * "18 08 03 12 06 24 31 36 35 34 39 1a 00 20 00 28 "+
				 * "80 97 ad e9 0b 30 00 38 00 0a 2a 08 04 12 08 b6 "+
				 * "ca b3 a2 c0 fc c5 f5 1a 08 bc bc b9 d9 b6 ca b3 "+
				 * "a2 20 13 28 ac fb 92 91 0c 30 da d9 c8 a3 02 38 "+
				 * "fe ac e4 91 01 0a 18 08 05 12 06 24 31 36 35 35 "+
				 * "31 1a 00 20 00 28 80 97 ad e9 0b 30 00 38 00 0a "+
				 * "18 08 06 12 06 24 31 36 35 35 32 1a 00 20 00 28 "+
				 * "80 97 ad e9 0b 30 00 38 00 0a 18 08 07 12 06 24 "+
				 * "31 36 35 36 30 1a 00 20 00 28 80 97 ad e9 0b 30 "+
				 * "00 38 00 00 00"; pc.sendPackets(new S_NewCreateItem(s),
				 * true);
				 */
			} else if (type == 0x013F) {// �ҼȾ׼�
				readD();
				readC();
				int action = readC();
				if (action >= 1 && action <= 11) {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.EMOTICON, action, pc.getId()), true);
					Broadcaster.broadcastPacket(pc, new S_NewCreateItem(S_NewCreateItem.EMOTICON, action, pc.getId()),
							true);
				}

			} else if (type == 0x45) {
				try {
					readH();
					readC();
					int castleType = readC();
					// 1��Ʈ 2��� 4��ũ���
					String s = "";
					for (L1Clan cc : L1World.getInstance().getAllClans()) {
						if (castleType == cc.getCastleId()) {
							s = cc.getClanName();
							break;
						}
					}

					if (s.equalsIgnoreCase("")) {
						return;
					}

					L1PcInstance player = pc;
					String clanName = player.getClanname();
					int clanId = player.getClanid();

					if (!player.isCrown()) { // ���� �̿�
						S_ServerMessage sm = new S_ServerMessage(478);
						player.sendPackets(sm, true);
						return;
					}
					if (clanId == 0) { // ũ���̼Ҽ�
						S_ServerMessage sm = new S_ServerMessage(272);
						player.sendPackets(sm, true);
						return;
					}
					L1Clan clan = L1World.getInstance().getClan(clanName);
					if (clan == null) { // ��ũ���� �߰ߵ��� �ʴ´�
						S_SystemMessage sm = new S_SystemMessage("��� ������ �߰ߵ��� �ʾҽ��ϴ�.");
						player.sendPackets(sm, true);
						return;
					}
					if (player.getId() != clan.getLeaderId()) { // ������
						S_ServerMessage sm = new S_ServerMessage(478);
						player.sendPackets(sm, true);
						return;
					}
					if (clanName.toLowerCase().equals(s.toLowerCase())) { // ��ũ����
																			// ����
						S_SystemMessage sm = new S_SystemMessage("�ڽ��� ���� ���� ������ �Ұ����մϴ�.");
						player.sendPackets(sm, true);
						return;
					}
					L1Clan enemyClan = null;
					String enemyClanName = null;
					for (L1Clan checkClan : L1World.getInstance().getAllClans()) { // ũ������
																					// üũ
						if (checkClan.getClanName().toLowerCase().equals(s.toLowerCase())) {
							enemyClan = checkClan;
							enemyClanName = checkClan.getClanName();
							break;
						}
					}
					if (enemyClan == null) { // ��� ũ���� �߰ߵ��� �ʾҴ�
						S_SystemMessage sm = new S_SystemMessage("��� ������ �߰ߵ��� �ʾҽ��ϴ�.");
						player.sendPackets(sm, true);
						return;
					}
					if (clan.getAlliance(enemyClan.getClanId()) == enemyClan) {
						S_ServerMessage sm = new S_ServerMessage(1205);
						player.sendPackets(sm, true);
						return;
					}
					List<L1War> warList = L1World.getInstance().getWarList(); // ����
																				// ����Ʈ��
																				// ���
					if (clan.getCastleId() != 0) { // ��ũ���� ����
						S_ServerMessage sm = new S_ServerMessage(474);
						player.sendPackets(sm, true);
						return;
					}
					if (enemyClan.getCastleId() != 0 && // ��� ũ���� ���ַ�, ��ĳ���Ͱ�
														// Lv25 �̸�
							player.getLevel() < 25) {
						S_ServerMessage sm = new S_ServerMessage(475);
						player.sendPackets(sm, true); // �������� �����Ϸ��� ���� 25�� �̸���
														// ������ �ȵ˴ϴ�.
						return;
					}

					int onLineMemberSize = 0;
					for (L1PcInstance onlineMember : clan.getOnlineClanMember()) {
						if (onlineMember.isPrivateShop())
							continue;
						onLineMemberSize++;
					}

					if (onLineMemberSize < 15) {
						player.sendPackets(new S_SystemMessage("�������� ���� �������� [15]�� �̻� �Ǿ�� ������ �����մϴ�."), true);
						return;
					}

					if (clan.getHouseId() > 0) {
						S_SystemMessage sm = new S_SystemMessage("����Ʈ�� �ִ� ���¿����� ���� ���� �� �� �����ϴ�.");
						player.sendPackets(sm, true);
						return;
					}
					if (enemyClan.getCastleId() != 0) { // ��� ũ���� ����
						int castle_id = enemyClan.getCastleId();
						if (WarTimeController.getInstance().isNowWar(castle_id)) { // ����
																					// �ð���
							L1PcInstance clanMember[] = clan.getOnlineClanMember();
							for (int k = 0; k < clanMember.length; k++) {
								if (L1CastleLocation.checkInWarArea(castle_id, clanMember[k])) {
									// S_ServerMessage sm = new
									// S_ServerMessage(477);
									// player.sendPackets(sm, true); // ����� ������
									// ��� ���Ϳ��� ���� �ۿ� ������ ������ �������� ������ �� �����ϴ�.
									int[] loc = new int[3];
									Random _rnd = new Random(System.nanoTime());
									loc = L1CastleLocation.getGetBackLoc(castle_id);
									int locx = loc[0] + (_rnd.nextInt(4) - 2);
									int locy = loc[1] + (_rnd.nextInt(4) - 2);
									short mapid = (short) loc[2];
									L1Teleport.teleport(clanMember[k], locx, locy, mapid,
											clanMember[k].getMoveState().getHeading(), true);
								}
							}
							boolean enemyInWar = false;
							for (L1War war : warList) {
								if (war.CheckClanInWar(enemyClanName)) { // ���
																			// ũ����
																			// �̹�
																			// ������
									war.DeclareWar(clanName, enemyClanName);
									war.AddAttackClan(clanName);
									enemyInWar = true;
									break;
								}
							}
							if (!enemyInWar) { // ��� ũ���� ������ �ܷ̿�, ��������
								L1War war = new L1War();
								war.handleCommands(1, clanName, enemyClanName); // ������
																				// ����
							}
						} else { // ���� �ð���
							S_ServerMessage sm = new S_ServerMessage(476);
							player.sendPackets(sm, true); // ���� �������� �ð��� �ƴմϴ�.
						}
					} else { // ��� ũ���� ���ִ� �ƴϴ�
						return;
					}
				} catch (Exception e) {
				}
			} else if (type == 0x0142) {// /���Ͱ���
				readC();
				readH();
				int length = readC();
				byte[] BYTE = readByte();

				if (pc.isCrown()) {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 13), true);
					return;
				}

				if (pc.getClanid() != 0) {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 9), true);
					return;
				}

				String clanname = new String(BYTE, 0, length, "EUC-KR");
				L1Clan clan = L1World.getInstance().getClan(clanname);
				if (clan == null) {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 4), true);
					return;
				}
				L1PcInstance crown = clan.getonline����();// L1World.getInstance().getPlayer(clan.getLeaderName());
				if (crown == null) {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 11), true);
					return;
				}

				if (clan.getJoinSetting() == 0) {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 8), true);
					return;

				} else if (clan.getJoinType() == 0) {
					C_Attr.���Ͱ���(crown, pc, clan);
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 0), true);
					return;
				} else {
					crown.setTempID(pc.getId()); // ����� ������Ʈ ID�� ������ �д�
					S_Message_YN myn = new S_Message_YN(97, pc.getName());
					crown.sendPackets(myn, true);
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 1), true);
				}

			} else if (type == 0x0146) { // ���� ���Խ�û �ޱ� ����
				if (pc.getClanid() == 0 || (!pc.isCrown() && pc.getClanRank() != L1Clan.CLAN_RANK_GUARDIAN))
					return;
				readC();
				readH();
				int setting = readC();
				readC();
				int setting2 = readC();
				if (setting2 == 2) {
					pc.sendPackets(new S_SystemMessage("���� ��ȣ ���� �������� ������ �� �����ϴ�."), true);
					setting2 = 1;
				}

				pc.getClan().setJoinSetting(setting);
				pc.getClan().setJoinType(setting2);
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_SETTING, setting, setting2), true);
				ClanTable.getInstance().updateClan(pc.getClan());
				pc.sendPackets(new S_ServerMessage(3980), true);
			} else if (type == 0x014C) { // ���� ���� ����
				if (pc.getClanid() == 0)
					return;
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_SETTING, pc.getClan().getJoinSetting(),
						pc.getClan().getJoinType()), true);
			} else if (type == 0x0152) { // ǥ�ļ���
				try {

					int length = readH();

					byte[] BYTE = readByte();
					byte[] objid = new byte[length - 3];
					byte[] subtype = new byte[1];

					if (pc.getParty() == null)
						return;
					if (!pc.getParty().isLeader(pc))
						return;

					System.arraycopy(BYTE, 1, objid, 0, objid.length);
					System.arraycopy(BYTE, length - 1, subtype, 0, 1);

					StringBuffer sb = new StringBuffer();

					for (byte zzz : objid) {
						sb.append(String.valueOf(zzz));
					}

					String s = sb.toString();

					L1PcInstance ǥ��pc = null;

					// System.out.println(s);

					for (L1PcInstance player : pc.getParty().getMembers()) {
						// System.out.println(player.encobjid);
						if (s.equals(player.encobjid)) {
							player.ǥ�� = subtype[0];
							ǥ��pc = player;
						}
					}

					if (ǥ��pc != null) {
						for (L1PcInstance player : pc.getParty().getMembers()) {
							player.sendPackets(new S_NewUI(0x53, ǥ��pc));
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (type == 0x01e0) { // Ž �������
				readC();
				readH();
				byte[] BYTE = readByte();
				byte[] temp = new byte[BYTE.length - 1];
				for (int i = 0; i < temp.length; i++) {
					temp[i] = BYTE[i];
				}
				StringBuffer sb = new StringBuffer();
				for (byte zzz : temp) {
					sb.append(String.valueOf(zzz));
				}
				int day = Nexttam(sb.toString());
				int charobjid = TamCharid(sb.toString());
				if (charobjid != pc.getId()) {
					pc.sendPackets(new S_SystemMessage("�ش� �ɸ��͸� ��Ҹ� �� �� �ֽ��ϴ�."));
					return;
				}
				int itemid = 0;
				if (day != 0) {
					if (day == 7) {
						itemid = 5559;
					} else if (day == 30) {
						itemid = 5560;
					}
					L1ItemInstance item = pc.getInventory().storeItem(itemid, 1);
					if (item != null) {
						pc.sendPackets(new S_ServerMessage(403, item.getName() + " (1)"), true);
						tamcancle(sb.toString());
						pc.sendPackets(new S_NewCreateItem(pc.getAccountName(), 0xcd));
					}
				}
			} else if (type == 0x01cc) { // ���� ���� ����
				pc.sendPackets(new S_NewCreateItem(pc.getAccountName(), S_NewCreateItem.TamPage));
			} else if (type == 0x84) { // ������ �ϴ�����

				if (!pc.PC��_����) {
					pc.sendPackets(new S_SystemMessage("PC�� �̿���� ����߿��� ��� ������ �ൿ�Դϴ�."), true);
					return;
				}
				if (pc.getMapId() == 99 || pc.getMapId() == 6202) {
					pc.sendPackets(new S_SystemMessage("������ ���¿����� �����̵��� ����� �� �����ϴ�."), true);
					return;
				}

				if (!pc.getMap().isTeleportable()) {
					pc.sendPackets(new S_SystemMessage("������ ���¿����� �����̵��� ����� �� �����ϴ�."), true);
					return;
				}

				int ran = _Random.nextInt(4);

				if (ran == 0) {
					L1Teleport.teleport(pc, 32779, 32825, (short) 622, pc.getMoveState().getHeading(), true);
				} else if (ran == 1) {
					L1Teleport.teleport(pc, 32761, 32819, (short) 622, pc.getMoveState().getHeading(), true);
				} else if (ran == 2) {
					L1Teleport.teleport(pc, 32756, 32837, (short) 622, pc.getMoveState().getHeading(), true);
				} else {
					L1Teleport.teleport(pc, 32770, 32839, (short) 622, pc.getMoveState().getHeading(), true);
				}

			} else if (type == 0x0202) {
				int totallen = readH();// ��ü����
				��Ŷ��ġ����((byte) 0x10);// ��ġ�̵�
				int chattype = readC();// ä��Ÿ��
				��Ŷ��ġ����((byte) 0x1a);// ��ġ�̵�
				int chatlen = readC();// ä�ñ���
				BinaryOutputStream os = new BinaryOutputStream();
				for (int i = 0; i < chatlen; i++) {
					os.writeC(readC());
				}
				byte[] chat = os.getBytes();
				String chat2 = new String(chat, "EUC-KR");
				os.close();
				String name = "";
				if (chattype != 1) {
					Chat(pc, chattype, totallen, chat, chat2, client);
					return;
				}

				��Ŷ��ġ����((byte) 0x2a);// ��ġ�̵�
				int namelen = readC();// �̸�����
				if (namelen != 0) {
					name = readS(namelen);
				}
				ChatWhisper(pc, chattype, totallen, chat, chat2, name);
			} else if (type == 0x7a) { // �����ռ�â
				if (pc.getInventory().calcWeightpercent() >= 90) {
					pc.sendPackets(new S_SystemMessage("���� �������� �������� �ռ��� ������ �� �����ϴ�."));
					return;
				}
				if (client.�����ռ���Ŷ������)
					return;

				readH();// length
				readC();
				readC();
				int unknown = readD();

				pc.sendPackets(new S_NewCreateItem(0x80, "00 00"));
				if (unknown != 0 && !client.�����ռ���Ŷ����) {
					client.�����ռ���Ŷ���� = true;
					if (unknown != -1528525972)
						client.�����ռ���Ŷ���� = false;
				}
				if (client.�����ռ���Ŷ����) {
					pc.sendPackets(new S_NewCreateItem(0x7b, "08 03 00 00"));
				} else {
					client.�����ռ���Ŷ������ = true;
					client.�����ռ���Ŷ���� = true;
					GeneralThreadPool.getInstance().schedule(new Send_DollAlchemyInfo(client), 1);
				}
			} else if (type == 0x7c) { // �ռ��Ϸ�â
				// pc.sendPackets(new S_SystemMessage("������Ʈ �غ��� �Դϴ�."));
				int bytelen = readH();// ������
				readH();

				byte[] BYTE = readByte();

				int a_len = 0;
				int _off = 0;
				a_len = BYTE[_off + 1];
				/*
				 * for (int i = 0; i < BYTE.length; i++) {
				 * System.out.println(BYTE[i] & 0xff); }
				 */
				// System.out.println("--------------------------------");
				// System.out.println("--------------------------------");
				StringBuffer sb = null;
				StringBuffer sb2 = null;
				byte[] temp = null;
				ArrayList<L1ItemInstance> _usedoll = new ArrayList<L1ItemInstance>();
				L1ItemInstance item = null;
				while (bytelen > 7) {
					// System.out.println("a_len = "+a_len);
					// System.out.println("len = "+ bytelen);
					temp = new byte[a_len - 6];
					System.arraycopy(BYTE, _off + 8, temp, 0, a_len - 6);
					bytelen -= temp.length + 8;
					_off += temp.length + 8;

					sb = new StringBuffer();
					sb2 = new StringBuffer();

					for (byte zzz : temp) {
						sb.append(HexToDex(zzz & 0xff, 2) + " ");
						sb2.append(String.valueOf(zzz));
					}

					item = pc.getInventory().findEncobj(sb2.toString());

					if (item == null) {
						pc.sendPackets(new S_SystemMessage("�������� ������θ� �̿��� �ּ���."));
						return;
					}

					_usedoll.add(item);

					// System.out.println(sb.toString());
					/*
					 * for (int i = 0; i < temp.length; i++) {
					 * System.out.println(temp[i] & 0xff); }
					 */
					// System.out.println("--------------------------------");
				}

				try {
					String lll = "08";
					if (temp.length == 5) {
						lll = "09";
					} else if (temp.length == 4) {
						lll = "08";
					} else if (temp.length == 3) {
						lll = "07";
					} else if (temp.length == 2) {
						lll = "06";
					} else if (temp.length == 1) {
						lll = "05";
					}
					int rnd = _Random.nextInt(100) + 1;
					int suc = 1;// ����

					boolean ����޼��� = false;

					// ���� ���� �ܰ� �ƴҶ� ����.
					// ������ �κ��� ������ ����.

					Collections.shuffle(_usedoll);

					int itemid = _usedoll.get(0).getItemId();
					int dollid = itemid;
					L1ItemInstance sucitem = null;

					if (item_doll_code(itemid) == 1) {
						if (_usedoll.size() == 2) {
							if (rnd < 10) {// ����
								suc = 0;
							}
						} else if (_usedoll.size() == 3) {
							if (rnd < 20) {// ����
								suc = 0;
							}
						} else if (_usedoll.size() == 4) {
							if (rnd < 40) {// ����
								suc = 0;
							}
						}
						if (suc == 0) {
							dollid = lv2doll[_Random.nextInt(lv2doll.length)];
							sucitem = ItemTable.getInstance().createItem(dollid);
						}
					} else if (item_doll_code(itemid) == 2) {
						if (_usedoll.size() == 2) {
							if (rnd < 10) {// ����
								suc = 0;
							}
						} else if (_usedoll.size() == 3) {
							if (rnd < 20) {// ����
								suc = 0;
							}
						} else if (_usedoll.size() == 4) {
							if (rnd < 50) {// ����
								suc = 0;
							}
						}
						if (suc == 0) {
							dollid = lv3doll[_Random.nextInt(lv3doll.length)];
							sucitem = ItemTable.getInstance().createItem(dollid);
							// dollid = 176;
						}
					} else if (item_doll_code(itemid) == 3) {
						if (_usedoll.size() == 2) {
							if (rnd < 6) {// ����
								suc = 0;
							}
						} else if (_usedoll.size() == 3) {
							if (rnd < 14) {// ����
								suc = 0;
							}
						} else if (_usedoll.size() == 4) {
							if (rnd < 25) {// ����
								suc = 0;
							}
						}
						if (suc == 0) {
							dollid = lv4doll[_Random.nextInt(lv4doll.length)];
							sucitem = ItemTable.getInstance().createItem(dollid);
							����޼��� = true;
						}
					} else if (item_doll_code(itemid) == 4) {
						if (_usedoll.size() == 2) {
							if (rnd < 3) {// ����
								suc = 0;
							}
						} else if (_usedoll.size() == 3) {
							if (rnd < 7) {// ����
								suc = 0;
							}
						} else if (_usedoll.size() == 4) {
							if (rnd < 15) {// ����
								suc = 0;
							}
						}
						if (suc == 0) {
							dollid = lv5doll[_Random.nextInt(lv5doll.length)];
							sucitem = ItemTable.getInstance().createItem(dollid);
							����޼��� = true;
						}
					}

					for (L1ItemInstance zzz : _usedoll) {
						pc.getInventory().removeItem(zzz);
					}

					if (sucitem == null) {
						sucitem = ItemTable.getInstance().createItem(dollid);
					}

					String ss = HexToDex(suc & 0xff, 2);

					sucitem.setCount(1);
					// 0000: 6d e5 01 08 23 10 ad 12 18 dc 33 d9 ee
					// m...#.....3..

					pc.sendPackets(new S_NewCreateItem(0x7D, "08 " + ss + " 12 " + lll + " 08", sucitem.getId(), "10 ",
							sucitem.get_gfxid()));

					// pc.sendPackets(new
					// S_NewCreateItem(0x01E5,"08 23 10 ad 12 18 dc 33 00 00"));

					if (suc == 0) {
						pc.getInventory().storeItem(sucitem, true);

						// pc.sendPackets(new
						// S_NewCreateItem(0x01E5,"08 23 10 ad 12 18 dc 33 00
						// 00"));

						if (����޼���) {
							L1World.getInstance().broadcastPacketToAll(
									new S_ServerMessage(4433, sucitem.getItem().getNameId(), pc.getName(), true));
						}

					} else {
						pc.getInventory().storeItem(sucitem);
					}

					/*
					 * if(suc==1){ L1ItemInstance faildoll = _usedoll.get(0);
					 * _usedoll.remove(0); for (L1ItemInstance zzz : _usedoll) {
					 * pc.getInventory().removeItem(zzz); }
					 * 
					 * //String sss = HexToDex(Config.test & 0xff, 2); String ss
					 * = HexToDex(suc & 0xff, 2);
					 * 
					 * pc.sendPackets(new S_NewCreateItem(0x7D,"08 "+ss+" 12 "
					 * +lll+" 08", faildoll.getId(), "10",
					 * faildoll.get_gfxid()));
					 * 
					 * }else{ for (L1ItemInstance zzz : _usedoll) {
					 * pc.getInventory().removeItem(zzz); }
					 * 
					 * //String sss = HexToDex(Config.test & 0xff, 2); String ss
					 * = HexToDex(suc & 0xff, 2);
					 * 
					 * L1ItemInstance sucitem = ItemTable.getInstance().
					 * createItem(dollid);
					 * 
					 * sucitem.setCount(1);
					 * 
					 * pc.sendPackets(new S_NewCreateItem(0x7D,"08 "+ss+" 12 "
					 * +lll+" 08", sucitem.getId() , "10 ",
					 * sucitem.get_gfxid()));
					 * 
					 * pc.getInventory().storeItem(sucitem); }
					 */

				} catch (Exception e) {
					e.printStackTrace();
				}

				/*
				 * 0e 00 08 01 10 00 18 08 20 01 1 28 10 16 30 0e 14 50 0b 11
				 * 
				 * 00
				 */
			} else if (type == 0x01e4) { // ĳ���� ����
				try {
					int length = readH();// ����
					ArrayList<byte[]> arrb = new ArrayList<byte[]>();
					for (int i = 0; i < length / 2; i++) {
						arrb.add(readByte(2));
					}
					int addstat, level, classtype = 0, status = 0, unknown2, unknown3 = 0, str = 0, cha = 0, inte = 0,
							dex = 0, con = 0, wis = 0;
					for (byte[] b : arrb) {
						switch (b[0]) {
						case 0x08:
							level = b[1] & 0xff;
							break;// ��
						case 0x10:
							classtype = b[1] & 0xff;
							break;// Ŭ���� Ÿ��
						case 0x18:
							status = b[1] & 0xff;
							break;// �ʱ���� = 1 / ���Ⱥ������ = 8

						case 0x20:
							unknown2 = b[1] & 0xff;
							break;// ��
						case 0x28:
							unknown3 = b[1] & 0xff;
							break;// ��

						case 0x30:
							str = b[1] & 0xff;
							break;// ��
						case 0x38:
							inte = b[1] & 0xff;
							break;// ��Ʈ
						case 0x40:
							wis = b[1] & 0xff;
							break;// ����
						case 0x48:
							dex = b[1] & 0xff;
							break;// ��
						case 0x50:
							con = b[1] & 0xff;
							break;// ��
						case 0x58:
							cha = b[1] & 0xff;
							break;// ī��

						default:
							int i = 0;
							try {
								i = b[0] & 0xff;
							} catch (Exception e) {
							}
							System.out.println("[���Ȱ��� ���ǵ��� ���� ��Ŷ] op : " + i);
							break;
						}
					}

					if (str != 0 && unknown3 != 1) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, str, con, "��", classtype, null));
					}
					if (dex != 0) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, dex, 0, "��", classtype, null));
					}
					if (con != 0 && unknown3 != 16) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, con, str, "��", classtype, null));
					}
					if (inte != 0) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, inte, 0, "��Ʈ", classtype, null));
					}
					if (wis != 0) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, wis, 0, "����", classtype, null));
					}
					if (cha != 0) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, cha, 0, "ī��", classtype, null));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {

		} finally {
			clear();
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

	private static final int lv2doll[] = { 430001, 41249, 430500, 500108, 500109, 600242 };
	private static final int lv3doll[] = { 500205, 500204, 500203, 60324, 500110, 600243 };
	private static final int lv4doll[] = { 500202, 5000035, 600244, 600245, 142920, 142921, 751 };
	private static final int lv5doll[] = { 600246, 600247, 142922, 752 };

	public int item_doll_code(int item) {
		int doll = 0;
		switch (item) {
		/*
		 * 41248 ���׺��� 41250 �����ΰ� 430000 ���� 430002 ũ����Ʈ�þ� 430004 ��Ƽ
		 */
		case 41248:
			doll = 1;
			break;
		case 41250:
			doll = 1;
			break;
		case 430000:
			doll = 1;
			break;
		case 430002:
			doll = 1;
			break;
		case 430004:
			doll = 1;
			break;
		case 600241:
			doll = 1;
			break;// ��
		/*
		 * 430001 ��� 41249 ��ť 430500 ��ī 500108 �ξ� 500109 �����
		 */
		case 430001:
			doll = 2;
			break;
		case 41249:
			doll = 2;
			break;
		case 430500:
			doll = 2;
			break;
		case 500108:
			doll = 2;
			break;
		case 500109:
			doll = 2;
			break;
		case 600242:
			doll = 2;
			break;// ��ٰ�
		/*
		 * 500205 ��ť�� 500204 ����� 500203 ���̾�Ʈ 60324 �巹��ũ
		 */
		case 500205:
			doll = 3;
			break;
		case 500204:
			doll = 3;
			break;
		case 500203:
			doll = 3;
			break;
		case 60324:
			doll = 3;
			break;
		case 500110:
			doll = 3;
			break;
		case 600243:
			doll = 3;
			break;// ���̾ư�
		/*
		 * 500202 ����Ŭ�ӽ� 5000035 ��ġ
		 */
		case 500202:
			doll = 4;
			break;
		case 5000035:
			doll = 4;
			break;
		case 600244:
			doll = 4;
			break;// �þ�
		case 600245:
			doll = 4;
			break;// ����
		case 142920:
			doll = 4;
			break;// ���̸���
		case 142921:
			doll = 4;
			break;// ����
		case 751:
			doll = 4;
			break;// �ӹ̷ε�
		case 600246:
			doll = 5;
			break;// ����
		case 600247:
			doll = 5;
			break;// ����
		case 142922:
			doll = 5;
			break;// �ٶ�ī
		case 752:
			doll = 5;
			break;// Ÿ��
		default:
			break;
		}
		return doll;
	}

	public int doll_item_code(int doll) {
		int item = 0;
		switch (doll) {
		/*
		 * 150 ���׺��� 151 �����ΰ� 152 ���� 153 ũ����Ʈ�þ� 154 ��Ƽ
		 */
		case 150:
			item = 41248;
			break;
		case 151:
			item = 41250;
			break;
		case 152:
			item = 430000;
			break;
		case 153:
			item = 430002;
			break;
		case 154:
			item = 430004;
			break;

		/*
		 * 155 ��� 156 ��ť 157 ��ī 158 �ξ� 159 �����
		 */
		case 155:
			item = 430001;
			break;
		case 156:
			item = 41249;
			break;
		case 157:
			item = 430500;
			break;
		case 158:
			item = 500108;
			break;
		case 159:
			item = 500109;
			break;

		/*
		 * 160 ��ť�� 161 ����� 162 ���̾�Ʈ 163 �巹��ũ
		 */
		case 160:
			item = 500205;
			break;
		case 161:
			item = 500204;
			break;
		case 162:
			item = 500203;
			break;
		case 163:
			item = 60324;
			break;
		case 176:
			item = 500110;
			break;
		/*
		 * 164 ����Ŭ�ӽ� 165 ��ġ
		 */
		case 164:
			item = 500202;
			break;
		case 165:
			item = 5000035;
			break;
		default:
			break;
		}
		return item;
	}

	static final int div1 = 128 * 128 * 128 * 128;
	static final int div2 = 128 * 128 * 128;
	static final int div3 = 128 * 128;
	static final int div4 = 128;

	private static String HexToDex(int data, int digits) {
		String number = Integer.toHexString(data);
		for (int i = number.length(); i < digits; i++)
			number = "0" + number;
		return number;
	}

	private void Chat(L1PcInstance pc, int chatType, int chatcount, byte[] chatdata, String chatText,
			LineageClient clientthread) {
		try {
			if (pc.ĳ������) {
				try {
					String chaName = chatText;
					if (pc.getClanid() > 0) {
						pc.sendPackets(new S_SystemMessage("����Ż���� ĳ������ �����Ҽ� �ֽ��ϴ�."));
						pc.ĳ������ = false;
						return;
					}
					if (!pc.getInventory().checkItem(467009, 1)) { // �ֳ� üũ
						pc.sendPackets(new S_SystemMessage("�ɸ��� ���� ������� �����ϼž� �����մϴ�."));
						pc.ĳ������ = false;
						return;
					}
					for (int i = 0; i < chaName.length(); i++) {
						if (chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��.
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��.
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��.
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��.
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��.
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��.
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��.
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��' || // �ѹ���(char)������
																// ��.
								chaName.charAt(i) == '��' || chaName.charAt(i) == '��' || chaName.charAt(i) == '��'
								|| chaName.charAt(i) == '��') {
							pc.sendPackets(new S_SystemMessage("����Ҽ����� �ɸ����Դϴ�."));
							pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
							pc.ĳ������ = false;
							return;
						}
					}
					if (chaName.getBytes().length > 12) {
						pc.sendPackets(new S_SystemMessage("�̸��� �ʹ� ��ϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}
					if (chaName.length() == 0) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �Է��ϼ���."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}
					if (BadNamesList.getInstance().isBadName(chaName)) {
						pc.sendPackets(new S_SystemMessage("����� �� ���� �ɸ����Դϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}
					if (isInvalidName(chaName)) {
						pc.sendPackets(new S_SystemMessage("����� �� ���� �ɸ����Դϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}
					if (CharacterTable.doesCharNameExist(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}
					if (CharacterTable.RobotNameExist(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}
					if (CharacterTable.RobotCrownNameExist(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}
					if (NpcShopSpawnTable.getInstance().getNpc(chaName) || npcshopNameCk(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}
					if (CharacterTable.somakname(chaName)) {
						pc.sendPackets(new S_SystemMessage("������ �ɸ����� �����մϴ�."));
						pc.sendPackets(new S_SystemMessage("ĳ���� ���� ������� �ٽ� Ŭ���� �̿��� �ּ���."));
						pc.ĳ������ = false;
						return;
					}

					pc.getInventory().consumeItem(467009, 1); // �Ҹ�

					String oldname = pc.getName();

					chaname(chaName, oldname);

					long sysTime = System.currentTimeMillis();
					logchangename(chaName, oldname, new Timestamp(sysTime));

					pc.sendPackets(new S_SystemMessage(chaName + " ���̵�� ���� �ϼ̽��ϴ�."));
					pc.sendPackets(new S_SystemMessage("������  �̿��� ���� Ŭ���̾�Ʈ�� ������ ���� �˴ϴ�."));

					Thread.sleep(1000);
					clientthread.kick();
				} catch (Exception e) {
				}
				return;
			}
			if (clientthread.AutoCheck) {
				if (chatText.equalsIgnoreCase(clientthread.AutoAnswer)) {
					pc.sendPackets(new S_SystemMessage("�ڵ� ���� ���� ���������� �Է��Ͽ����ϴ�."), true);
					while (pc.isTeleport() || pc.�ڴ��()) {
						Thread.sleep(100);
					}
					if (pc.getMapId() == 6202 || pc.getMapId() == 2005) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(EARTH_BIND);
						}
					}
					if (pc.getMapId() == 6202) {
						// L1Teleport.teleport(pc, 32778, 32832, (short) 622, 5,
						// true);
						L1Teleport.teleport(pc, 33442, 32797, (short) 4, 5, true);
					}
					if (GMCommands.autocheck_iplist.contains(clientthread.getIp())) {
						GMCommands.autocheck_iplist.remove(clientthread.getIp());
					}
					if (GMCommands.autocheck_accountlist.contains(clientthread.getAccountName())) {
						GMCommands.autocheck_accountlist.remove(clientthread.getAccountName());
					}
				} else {
					if (clientthread.AutoCheckCount++ >= 2) {
						pc.sendPackets(new S_SystemMessage("�ڵ� ���� ���� �߸� �Է��Ͽ����ϴ�."), true);
						while (pc.isTeleport() || pc.�ڴ��()) {
							Thread.sleep(100);
						}

						if (!GMCommands.autocheck_Tellist.contains(clientthread.getAccountName())) {
							GMCommands.autocheck_Tellist.add(clientthread.getAccountName());
						}

						L1Teleport.teleport(pc, 32928, 32864, (short) 6202, 5, true);

					} else {
						pc.sendPackets(new S_SystemMessage("�ڵ� ���� ���� �߸� �Է��Ͽ����ϴ�. ��ȸ�� ��3���Դϴ�."), true);
						// pc.sendPackets(new
						// S_PacketBox(S_PacketBox.GREEN_MESSAGE,
						// "�ڵ� ���� : [ "+pc.getNetConnection().AutoQuiz+" ] ����
						// ä��â�� �Է����ּ���."),
						// true);
						// pc.sendPackets(new
						// S_SystemMessage("�ڵ� ���� : [
						// "+pc.getNetConnection().AutoQuiz+" ] ���� ä��â��
						// �Է����ּ���."),
						// true);
						pc.sendPackets(
								new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "�ڵ� ���� : " + pc.getNetConnection().AutoQuiz),
								true);
						pc.sendPackets(new S_SystemMessage("�ڵ� ���� : " + pc.getNetConnection().AutoQuiz), true);
						return;
					}
					/*
					 * if(clientthread.AutoCheckCount >= 2){
					 * clientthread.kick(); return; } pc.sendPackets(new
					 * S_SystemMessage("���� ���� �ڵ带 �߸� �Է��ϼ̽��ϴ�."), true);
					 * clientthread.AutoCheckCount++; Random _rnd = new
					 * Random(System.nanoTime()); int x = _rnd.nextInt(30); int
					 * y = _rnd.nextInt(30); clientthread.AutoAnswer = ""+(x+y);
					 * pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
					 * "���� ���� �ڵ� [ "+x+" + "+y+" = ? ] ���� �Է����ּ���."), true);
					 * pc.sendPackets(new S_SystemMessage("���� ���� �ڵ� [ "+x+" + "
					 * +y +" = ? ] ���� �Է����ּ���."), true);
					 */
				}
				clientthread.AutoCheck = false;
				clientthread.AutoCheckCount = 0;
				clientthread.AutoQuiz = "";
				clientthread.AutoAnswer = "";
				return;
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SILENCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.AREA_OF_SILENCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_POISON_SILENCE)) {
				return;
			}
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) { // ä��
																								// ������
				S_ServerMessage sm = new S_ServerMessage(242);
				pc.sendPackets(sm); // ���� ä�� �������Դϴ�.
				sm = null;
				return;
			}

			if (pc.isDeathMatch() && !pc.isGhost() && !pc.isGm()) {
				S_ServerMessage sm = new S_ServerMessage(912);
				pc.sendPackets(sm); // ä���� �� �� �����ϴ�.
				sm = null;
				return;
			}

			if (!pc.isGm()) {
				for (String tt : textFilter) {
					int indexof = chatText.indexOf(tt);
					if (indexof != -1) {
						int count = 100;
						while ((indexof = chatText.indexOf(tt)) != -1) {
							if (count-- <= 0)
								break;
							char[] dd = chatText.toCharArray();
							chatText = "";
							for (int i = 0; i < dd.length; i++) {
								if (i >= indexof && i <= (indexof + tt.length() - 1)) {
									chatText = chatText + "  ";
								} else
									chatText = chatText + dd[i];
							}
						}
					}
				}
			}
			switch (chatType) {
			case 0: {
				if (pc.isGhost() && !(pc.isGm() || pc.isMonitor())) {
					return;
				}
				if (chatText.startsWith(".�ð�")) {
					StringBuilder sb = null;
					sb = new StringBuilder();
					TimeZone kst = TimeZone.getTimeZone("GMT+9");
					Calendar cal = Calendar.getInstance(kst);
					sb.append("[Server Time]" + cal.get(Calendar.YEAR) + "�� " + (cal.get(Calendar.MONTH) + 1) + "�� "
							+ cal.get(Calendar.DATE) + "�� " + cal.get(Calendar.HOUR_OF_DAY) + ":"
							+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
					S_SystemMessage sm = new S_SystemMessage(sb.toString());
					pc.sendPackets(sm, true);
					sb = null;
					return;
				}
				// GMĿ���
				if (chatText.startsWith(".") && (pc.getAccessLevel() == Config.GMCODE || pc.getAccessLevel() == 7777)) {
					String cmd = chatText.substring(1);
					GMCommands.getInstance().handleCommands(pc, cmd);
					return;
				}

				if (chatText.startsWith("$")) {
					if (pc.isGm())
						chatWorld(pc, chatdata, chatType, chatcount, chatText);
					else
						chatWorld(pc, chatdata, 12, chatcount, chatText);
					if (!pc.isGm()) {
						pc.checkChatInterval();
					}
					return;
				}

				Gamble(pc, chatText);
				if (chatText.startsWith(".")) { // �����ڸ�Ʈ
					String cmd = chatText.substring(1);
					if (cmd == null) {
						return;
					}
					UserCommands.getInstance().handleCommands(pc, cmd);
					return;
				}

				if (chatText.startsWith("$")) { // ����ä��
					if (pc.isGm())
						chatWorld(pc, chatdata, chatType, chatcount, chatText);
					else
						chatWorld(pc, chatdata, 12, chatcount, chatText);
					if (!pc.isGm()) {
						pc.checkChatInterval();
					}
					return;
				}

				/** �ڷ� Ǯ�� **/
				/*
				 * if (chatText.startsWith("119")) { try {
				 * L1Teleport.teleport(pc, pc.getX(), pc.getY(), pc.getMapId(),
				 * pc.getMoveState().getHeading(), false); } catch (Exception
				 * exception35) {} }
				 */
				// S_ChatPacket s_chatpacket = new S_ChatPacket(pc, chatText,
				// Opcodes.S_SAY, 0);

				// pc.sendPackets(new S_NewCreateItem(chatType, chatdata,
				// chatcount, "",pc));
				// L1PcInstance pc, int type, int chat_type, String chat_text,
				// String target_name
				pc.sendPackets(new S_NewCreateItem(pc, 3, chatType, chatText, ""));
				// pc.sendPackets(new S_NewCreateItem(pc, 4, chatType, chatText,
				// ""));
				S_NewCreateItem s_chatpacket = new S_NewCreateItem(pc, 4, chatType, chatText, "");
				// new S_NewCreateItem(chatType, chatdata, chatcount, pc);
				if (!pc.getExcludingList().contains(pc.getName())) {
					if (pc.getMapId() != 2699) {
						pc.sendPackets(s_chatpacket);
					}
				}
				for (L1PcInstance listner : L1World.getInstance().getRecognizePlayer(pc)) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						if (listner.getMapId() == 2699) {
							continue;
						}
						listner.sendPackets(s_chatpacket);
					}
				}
				// ���� ó��
				L1MonsterInstance mob = null;
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj instanceof L1MonsterInstance) {
						mob = (L1MonsterInstance) obj;
						if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
							Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, chatText, 0), true);
						}
					}
				}
				eva.LogChatNormalAppend("[�Ϲ�]", pc.getName(), chatText);
			}
				break;
			case 2: {
				if (pc.isGhost()) {
					return;
				}
				// S_ChatPacket s_chatpacket = new S_ChatPacket(pc,
				// chatText,Opcodes.S_SAY, 2);
				// S_NewCreateItem chat5 = new S_NewCreateItem(chatType,
				// chatdata, chatcount, pc);
				S_NewCreateItem chat5 = new S_NewCreateItem(pc, 4, chatType, chatText, "");
				if (!pc.getExcludingList().contains(pc.getName())) {
					pc.sendPackets(chat5);
				}
				for (L1PcInstance listner : L1World.getInstance().getVisiblePlayer(pc, 50)) {
					if (!listner.getExcludingList().contains(pc.getName())) {
						listner.sendPackets(chat5);
					}
				}
				eva.LogChatNormalAppend("[�Ϲ�]", pc.getName(), chatText);
				// ���� ó��
				L1MonsterInstance mob = null;
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj instanceof L1MonsterInstance) {
						mob = (L1MonsterInstance) obj;
						if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
							for (L1PcInstance listner : L1World.getInstance().getVisiblePlayer(mob, 30)) {
								listner.sendPackets(new S_NpcChatPacket(mob, chatText, 2), true);
							}
						}
					}
				}
			}
				break;
			case 3:
				chatWorld(pc, chatdata, chatType, chatcount, chatText);
				break;
			case 4: {
				if (pc.getClanid() != 0) { // ũ�� �Ҽ���
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					S_NewCreateItem chat4 = new S_NewCreateItem(pc, 4, chatType, chatText, "");
					if (Config.����ä�ø����() > 0) {
						for (L1PcInstance gm : Config.toArray����ä�ø����()) {
							if (gm.getNetConnection() == null) {
								Config.remove����(gm);
								continue;
							}
							if (gm == pc) {
								continue;
							}
						}
					}
					eva.LogChatClanAppend("[����]", pc.getName(), pc.getClanname(), chatText);
					for (L1PcInstance listner : clan.getOnlineClanMember()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(chat4);
						}
					}
				}
			}
				break;
			case 11: {
				if (pc.isInParty()) { // ��Ƽ��
					// S_NewCreateItem s_chatpacket = new
					// S_NewCreateItem(chatType, chatdata, chatcount, pc);
					S_NewCreateItem s_chatpacket = new S_NewCreateItem(pc, 4, chatType, chatText, "");
					for (L1PcInstance listner : pc.getParty().getMembers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
				if (Config.��Ƽä�ø����() > 0) {
					for (L1PcInstance gm : Config.toArray��Ƽä�ø����()) {
						if (gm.getNetConnection() == null) {
							Config.remove��Ƽ(gm);
							continue;
						}
						if (gm == pc) {
							continue;
						}
					}
				}
				eva.PartyChatAppend("[��Ƽ]", pc.getName(), chatText);
			}
				break;
			case 12:
				if (pc.isGm())
					chatWorld(pc, chatdata, chatType, chatcount, chatText);
				else
					chatWorld(pc, chatdata, 3, chatcount, chatText);
				break;
			case 13: { // ��ȣ��� ä��
				if (pc.getClanid() != 0) { // ���� �Ҽ���
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					int rank = pc.getClanRank();
					if (clan != null && (rank == L1Clan.CLAN_RANK_GUARDIAN || rank == L1Clan.CLAN_RANK_SUBPRINCE
							|| rank == L1Clan.CLAN_RANK_PRINCE)) {
						// S_NewCreateItem chat1 = new S_NewCreateItem(chatType,
						// chatdata, chatcount, pc);
						S_NewCreateItem chat1 = new S_NewCreateItem(pc, 4, chatType, chatText, "");
						for (L1PcInstance listner : clan.getOnlineClanMember()) {
							int listnerRank = listner.getClanRank();
							if (!listner.getExcludingList().contains(pc.getName())
									&& (listnerRank == L1Clan.CLAN_RANK_GUARDIAN || rank == L1Clan.CLAN_RANK_SUBPRINCE
											|| listnerRank == L1Clan.CLAN_RANK_PRINCE)) {
								listner.sendPackets(chat1);
							}
						}
					}
				}
				if (Config.����ä�ø����() > 0) {
					for (L1PcInstance gm : Config.toArray����ä�ø����()) {
						if (gm.getNetConnection() == null) {
							Config.remove����(gm);
							continue;
						}
						if (gm == pc) {
							continue;
						}
					}
				}
				eva.PartyChatAppend("[����]", pc.getName(), chatText);
			}
				break;
			case 14: { // ä�� ��Ƽ
				if (pc.isInChatParty()) { // ä�� ��Ƽ��
					// S_ChatPacket s_chatpacket = new S_ChatPacket(pc,
					// chatText,Opcodes.S_SAY, 14);
					// S_NewCreateItem s_chatpacket = new
					// S_NewCreateItem(chatType, chatdata, chatcount, pc);
					S_NewCreateItem s_chatpacket = new S_NewCreateItem(pc, 4, chatType, chatText, "");
					for (L1PcInstance listner : pc.getChatParty().getMembers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
				if (Config.��Ƽä�ø����() > 0) {
					for (L1PcInstance gm : Config.toArray��Ƽä�ø����()) {
						if (gm.getNetConnection() == null) {
							Config.remove��Ƽ(gm);
							continue;
						}
						if (gm == pc) {
							continue;
						}
					}
				}
			}
				break;
			case 15: { // ����ä��
				if (pc.getClanid() != 0) { // ���� �Ҽ���
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());

					if (clan != null) {
						Integer allianceids[] = clan.Alliance();
						if (allianceids.length > 0) {
							String TargetClanName = null;
							L1Clan TargegClan = null;

							// S_NewCreateItem s_chatpacket = new
							// S_NewCreateItem(chatType, chatdata, chatcount,
							// pc);
							S_NewCreateItem s_chatpacket = new S_NewCreateItem(pc, 4, chatType, chatText, "");
							for (L1PcInstance listner : clan.getOnlineClanMember()) {
								int AllianceClan = listner.getClanid();
								if (pc.getClanid() == AllianceClan) {
									listner.sendPackets(s_chatpacket);
								}
							} // �ڱ����� ���ۿ�

							for (int j = 0; j < allianceids.length; j++) {
								TargegClan = clan.getAlliance(allianceids[j]);
								if (TargegClan != null) {
									TargetClanName = TargegClan.getClanName();
									if (TargetClanName != null) {
										for (L1PcInstance alliancelistner : TargegClan.getOnlineClanMember()) {
											alliancelistner.sendPackets(s_chatpacket);
										} // �������� ���ۿ�
									}
								}

							}
						}

					}
				}
				break;
			}
			case 17:
				if (pc.getClanid() != 0) { // ���� �Ҽ���
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					if (clan != null && (pc.isCrown() && pc.getId() == clan.getLeaderId())) {
						// S_ChatPacket s_chatpacket = new
						// S_ChatPacket(pc,chatText, Opcodes.S_MESSAGE, 17);
						// S_NewCreateItem s_chatpacket5 = new
						// S_NewCreateItem(chatType, chatdata, chatcount, pc);
						S_NewCreateItem s_chatpacket5 = new S_NewCreateItem(pc, 4, chatType, chatText, "");
						for (L1PcInstance listner : clan.getOnlineClanMember()) {
							if (!listner.getExcludingList().contains(pc.getName())) {
								listner.sendPackets(s_chatpacket5);
							}
						}
					}
				}
				break;

			}
			if (!pc.isGm()) {
				pc.checkChatInterval();
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	private void chatWorld(L1PcInstance pc, byte[] chatdata, int chatType, int chatcount, String text) {
		if (pc.getLevel() >= Config.GLOBAL_CHAT_LEVEL) {
			if (pc.isGm() || L1World.getInstance().isWorldChatElabled()) {
				if (pc.get_food() >= 12) { // 5%����?
					S_PacketBox pb = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
					pc.sendPackets(pb, true);
					if (chatType == 3) {
						S_PacketBox pb2 = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
						pc.sendPackets(pb2, true);
						if (pc.isGm()) {
							L1World.getInstance().broadcastPacketToAll(new S_NewCreateItem(pc, 4, chatType, text, ""));
							L1World.getInstance().broadcastPacketToAll(
									new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[******] " + text));
							eva.WorldChatAppend("[��ü]", pc.getName(), text);
							return;
						}
						eva.WorldChatAppend("[��ü]", pc.getName(), text);
						
					} else if (chatType == 12) {
						S_PacketBox pb3 = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
						pc.sendPackets(pb3, true);
						if (pc.isGm()) {
							L1World.getInstance().broadcastPacketToAll(
									new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[******] " + text));
							eva.WorldChatAppend("[��ü]", pc.getName(), text);
							return;
						}
						eva.LogChatTradeAppend("[���]", pc.getName(), text);
					}
					for (L1PcInstance listner : L1World.getInstance().getAllPlayers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							if (listner.isShowTradeChat() && chatType == 12) {
								listner.sendPackets(new S_NewCreateItem(chatType, chatdata, chatcount, pc));
								listner.sendPackets(new S_NewCreateItem(pc, 4, chatType, text, ""));
							} else if (listner.isShowWorldChat() && chatType == 3) {
								listner.sendPackets(new S_NewCreateItem(pc, 4, chatType, text, ""));
							}
						}
					}

				} else {
					S_ServerMessage sm = new S_ServerMessage(462);
					pc.sendPackets(sm, true);
				}
			} else {
				S_ServerMessage sm = new S_ServerMessage(510);
				pc.sendPackets(sm, true);
			}
		} else {
			S_ServerMessage sm = new S_ServerMessage(195, String.valueOf(Config.GLOBAL_CHAT_LEVEL));
			pc.sendPackets(sm, true);
		}
	}

	private void ChatWhisper(L1PcInstance whisperFrom, int chatType, int chatcount, byte[] chatdata, String text,
			String targetName) {
		try {
			// ä�� �������� ���
			if (whisperFrom.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) {
				S_ServerMessage sm = new S_ServerMessage(242);
				whisperFrom.sendPackets(sm, true);
				return;
			}
			if (whisperFrom.getLevel() < Config.WHISPER_CHAT_LEVEL) {
				S_ServerMessage sm = new S_ServerMessage(404, String.valueOf(Config.WHISPER_CHAT_LEVEL));
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (!whisperFrom.isGm() && (targetName.compareTo("��Ƽ��") == 0)) {
				S_SystemMessage sm = new S_SystemMessage("��ڴԲ��� �ӼӸ��� �� �� �����ϴ�.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (targetName.equalsIgnoreCase("***")) {
				S_SystemMessage sm = new S_SystemMessage("-> (***) " + text);
				whisperFrom.sendPackets(sm, true);
				return;
			}

			L1PcInstance whisperTo = L1World.getInstance().getPlayer(targetName);

			// ���忡 ���� ���
			if (whisperTo == null) {
				L1NpcShopInstance npc = null;
				npc = L1World.getInstance().getNpcShop(targetName);
				if (npc != null) {
					// S_ChatPacket scp = new S_ChatPacket(npc,
					// text,Opcodes.S_MESSAGE, 9);
					S_NewCreateItem scp = new S_NewCreateItem(chatType, chatdata, chatcount, whisperFrom);
					whisperFrom.sendPackets(scp, true);
					// S_SystemMessage sm = new
					// S_SystemMessage("-> ("+targetName+") "+text);
					// whisperFrom.sendPackets(sm); sm.clear(); sm = null;
					return;
				}
				S_ServerMessage sm = new S_ServerMessage(73, targetName);
				whisperFrom.sendPackets(sm, true);
				return;
			}
			// �ڱ� �ڽſ� ���� wis�� ���
			if (whisperTo.equals(whisperFrom)) {
				return;
			}

			if (whisperTo.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) {
				S_SystemMessage sm = new S_SystemMessage("ä�ñ������� �������� �ӼӸ��� �Ҽ� �����ϴ�.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (text.length() > 26) {
				S_SystemMessage sm = new S_SystemMessage("�Ӹ��� ���� �� �ִ� ���ڼ��� �ʰ��Ͽ����ϴ�.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			// ���ܵǰ� �ִ� ���
			if (whisperTo.getExcludingList().contains(whisperFrom.getName())) {
				S_ServerMessage sm = new S_ServerMessage(117, whisperTo.getName());
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (!whisperTo.isCanWhisper()) {
				S_ServerMessage sm = new S_ServerMessage(205, whisperTo.getName());
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (whisperTo instanceof L1RobotInstance) {
				// S_ChatPacket scp = new S_ChatPacket(whisperTo,
				// text,Opcodes.S_MESSAGE, 9);
				whisperFrom.sendPackets(
						new S_NewCreateItem(chatType, chatdata, chatcount, whisperTo.getName(), whisperFrom));
				return;
			}
			// nt type, byte[] chat, int count, String targetNam1, L1PcInstance
			// pc
			// L1PcInstance pc, int type, int chat_type, String chat_text,
			// String target_name
			whisperFrom.sendPackets(new S_NewCreateItem(whisperFrom, 3, chatType, text, whisperTo.getName()));
			// whisperFrom.sendPackets(new S_NewCreateItem(whisperTo, 4,
			// chatType, text, whisperFrom.getName()));
			// whisperTo.sendPackets(new S_NewCreateItem(chatType, chatdata,
			// chatcount, whisperFrom));
			whisperTo.sendPackets(new S_NewCreateItem(whisperFrom, 4, chatType, text, whisperTo.getName()));
			// whisperTo.sendPackets(new S_NewCreateItem(whisperFrom, 4,
			// chatType, text, whisperTo.getName()));

			if (Config.�Ӹ�ä�ø����() > 0) {
				S_SystemMessage sm = new S_SystemMessage(
						whisperFrom.getName() + " -> (" + whisperTo.getName() + ") " + text);
				for (L1PcInstance gm : Config.toArray�Ӹ�ä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove�Ӹ�(gm);
						continue;
					}
					if (gm == whisperFrom || gm == whisperTo) {
						continue;
					}

					gm.sendPackets(sm);
				}
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	public int Nexttam(String encobj) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int day = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT day FROM `tam` WHERE encobjid = ? order by id asc limit 1"); // �ɸ���
																												// ���̺���
																												// ���ָ�
																												// ���ͼ�
			pstm.setString(1, encobj);
			rs = pstm.executeQuery();
			while (rs.next()) {
				day = rs.getInt("Day");
			}
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return day;
	}

	public int TamCharid(String encobj) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int objid = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT objid FROM `tam` WHERE encobjid = ? order by id asc limit 1"); // �ɸ���
																												// ���̺���
																												// ���ָ�
																												// ���ͼ�
			pstm.setString(1, encobj);
			rs = pstm.executeQuery();
			while (rs.next()) {
				objid = rs.getInt("objid");
			}
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return objid;
	}

	public void tamcancle(String objectId) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("delete from tam where encobjid = ? order by id asc limit 1");
			pstm.setString(1, objectId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void commit(String com, String name, int count) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM request_log WHERE command=?");
			pstm.setString(1, com);
			rs = pstm.executeQuery();
			Connection con2 = null;
			PreparedStatement pstm2 = null;
			try {
				con2 = L1DatabaseFactory.getInstance().getConnection();
				if (rs.next()) {
					int amount = rs.getInt("count");
					pstm2 = con2.prepareStatement("UPDATE request_log SET count=? WHERE command=?");
					pstm2.setInt(1, amount + count);
					pstm2.setString(2, com);
				} else {
					pstm2 = con2.prepareStatement("INSERT INTO request_log SET command=?, count=?");
					pstm2.setString(1, com);
					pstm2.setInt(2, count);
				}
				pstm2.executeUpdate();
			} catch (SQLException e) {
			} finally {
				SQLUtil.close(pstm2);
				SQLUtil.close(con2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void Gamble(L1PcInstance pc, String chatText) {
		
		if (pc.Gamble_Somak) { // �Ҹ�
			for (int i : GambleInstance.mobArray) {
				L1Npc npck = NpcTable.getInstance().getTemplate(i);
				String name = npck.get_name().replace(" ", "");
				if (name.equalsIgnoreCase(chatText) || npck.get_name().equalsIgnoreCase(chatText)
				/*
				 * || chatText.startsWith(npck.get_name())||
				 * chatText.startsWith(name)
				 */) {
					pc.Gamble_Text = npck.get_name();
				}
			}
		}
	}

	/** ItemClient �ڵ带 ��� id�� ���� **/
	private int ��þ�ۺ�ȯ(int ��þ�����) {
		
		switch (��þ�����) {
		case 15053: // ȯ���Ǿ�հ�
			return 284;
		case 15054: // ȯ����������
			return 285;
		case 15055: // ȯ�����Ѽհ�
			return 286;
		case 15056: // ȯ����Ȱ
			return 287;
		case 15057: // ȯ�����̵���
			return 288;
		case 15058: // ȯ����ü�μҵ�
			return 289;
		case 15059: // ȯ����Ű��ũ
			return 290;
		case 231: // ��������������
			return 129;
		case 232: // ������������
			return 125;
		case 1206:// ��Ÿ�ٵ�ܰ�
			return 6;
		case 402:// �����Ǿ�
			return 42;
		case 397:// ũ�ν�����
			return 180;
		case 233:// ����������
			return 131;
		case 235:// �ٸ���Ŀ����
			return 37;
		case 4102:
		case 6:// ��հ�
			return 52;
		case 4177:
		case 81:// ���
			return 181;
		case 4098:
		case 2:// �Ϻ���
			return 41;
		case 4135:
		case 39:// ���
			return 5;
		case 4140:
		case 44:// ��í��
			return 104;
		case 4166:
		case 70:// ������â
			return 99;
		case 138:// �뵵
			return 148;
		case 141:// �׶��콺
			return 32;
		case 144:// ����
			return 145;
		case 269:// ũ��
			return 180;
		case 274:// ����
			return 42;
		case 465:// ���
			return 64;
		case 3217:// ��Ű
			return 410003;
		case 3218:// ��Ű
			return 410004;
		case 3223:// ��ü
			return 410000;
		case 3224:// ��ü
			return 410001;
		case 4012:// ���渶��
			return 430106;
		case 4013:// ���渶��
			return 430104;
		case 4015:// ǳ�渶��
			return 430105;
		case 4014:// ȭ�渶��
			return 430107;

		case 4032:// ���� ���� ����
			return 430102;
		case 4033:// ���� ���� ����
			return 430100;
		case 4034:// ���� ȭ�� ����
			return 430103;
		case 4035:// ���� ǳ�� ����
			return 430101;
		case 10664:// ������ǥ��
			return 5000064;
		case 10665:// ������ǥ��
			return 5000066;
		case 10666:// ǳ����ǥ��
			return 5000065;
		case 10667:// ȭ����ǥ��
			return 5000063;

		case 2257:// ����ü
			return 41246;
		case 14820:// ����ȣ�ڼ�
			return 60423;
		case 14024: // �ܴ���ȣ�ڰ���
			return 21124;
		case 14025: // ȣ���尩
			return 427306;
		case 14026: // ȣ�ڰ�
			return 256;
		case 14027: // ȣ�ھ�հ�
			return 4500027;
		case 14028: // ������ȣ��������
			return 263;
		case 14029: // ȣ�� ����
			return 4500026;
		case 14030: // ȣ�� ü�μҵ�
			return 265;
		case 14031: // ȣ�� Ű��ũ
			return 264;
		case 13044: // ���� ���(��)
			return 60427;
		case 13045: // ���� ���(��)
			return 60428;
		case 13046: // ���� ���(��)
			return 60429;
		case 13047: // ���� ���(��)
			return 60430;
		case 13048: // ���� ���(��)
			return 60431;
		case 13049: // ���� ���(��)
			return 60432;
		case 13050: // ���� ���(��)
			return 60433;
		case 13051: // ���� ���(��)
			return 60434;
		case 13052: // ���� ���(��)
			return 60435;
		case 13053: // ���� ���(��)
			return 60436;
		case 13054: // ���� ���(��)
			return 60437;
		case 13055: // ���� ���(��)
			return 60438;
		case 1177: // �ΰſ��굥��
			return 13;
		case 981: // ����� Ȱ
			return 177;
		case 980: // �����ũ�ο�
			return 162;
		case 979: // ������̵���
			return 81;
		case 943: // ����Ʋ��
			return 194;
		default:
			return ��þ�����;
		}
	}

	private boolean Action_ItemCode(L1PcInstance pc, int item_code, int count) {
		boolean ck = false;
		for (; count > 0; count--) {

			if ((item_code >= 1952 && item_code <= 1969) || (item_code >= 3203 && item_code <= 3208)) {
				��Ƽ��(item_code, pc);
				ck = true;
				continue;
			}
			if (item_code >= 1970 && item_code <= 2011) {
				������(item_code, pc);
				ck = true;
				continue;
			}
			switch (item_code) {
			case 4350:
			case 4351:
				�������̺�(item_code, pc);
				ck = true;
				break;
			case 3559:// �̹�ȣ�� ���� �Ʊ� �׹�ȣ�� ���൵ ��ºκ��ε� 1736? �װſ� �ٵ� �������� �� �ٸ��ɺм��ؼ�
						// ���� �����ѿ����缭 �ڵ� ���ſ����
			case 3560:
			case 3561:
			case 3562:
			case 3555:
			case 3556:
			case 3557:
			case 3558:
				����(item_code, pc);
				ck = true;
				break;
			case 3563:
			case 3564:
			case 3565:
			case 3566:
			case 3567:
			case 3568:
			case 3569:
			case 3570:
				��Ű(item_code, pc);
				ck = true;
				break;
			case 2739:
				ȯ������(item_code, pc);
				ck = true;
				break;
			case 655:
				����(pc, "A");
				ck = true;
				break;
			case 656:
				����(pc, "E");
				ck = true;
				break;
			case 657:
				����(pc, "I");
				ck = true;
				break;
			case 658:
				����(pc, "M");
				ck = true;
				break;
			case 659:
				����(pc, "B");
				ck = true;
				break;
			case 660:
				����(pc, "F");
				ck = true;
				break;
			case 661:
				����(pc, "J");
				ck = true;
				break;
			case 662:
				����(pc, "N");
				ck = true;
				break;
			case 663:
				����(pc, "C");
				ck = true;
				break;
			case 664:
				����(pc, "G");
				ck = true;
				break;
			case 665:
				����(pc, "K");
				ck = true;
				break;
			case 666:
				����(pc, "O");
				ck = true;
				break;
			case 667:
				����(pc, "D");
				ck = true;
				break;
			case 668:
				����(pc, "H");
				ck = true;
				break;
			case 669:
				����(pc, "L");
				ck = true;
				break;
			case 670:
				����(pc, "P");
				ck = true;
				break;
			case 935:
			case 936:
			case 937:
				�����ǺҰ�_���ν���������(pc, item_code);
				ck = true;
				break;
			case 993:
			case 994:
			case 995:
			case 996:
			case 997:
			case 998:
			case 999:
			case 1000:
			case 1001:
				���Ⱥ���(pc, item_code);
				ck = true;
				break;

			case 1490:
			case 1491:
			case 1492:
			case 1493:
				�ż��Ѹ�������6(item_code, pc);
				ck = true;
				break;
			case 1153:
			case 1154:
				��ǳ�ǵ���(item_code, pc);
				ck = true;
				break;
			case 1155:
			case 1156:
			case 1157:
				�����ǵ���(item_code, pc);
				ck = true;
				break;
				

			default:
				return false;
			}

		}
		return ck;
	}

	private void ���Ⱥ���(L1PcInstance pc, int code) {
		int createitem = 0;
		int mateitem = 0;
		int mateitem2 = 0;
		if (code >= 993 && code <= 996) {
			if (code == 993) {
				createitem = 21259;
				mateitem = 40053;
				mateitem2 = 40393;
			} else if (code == 994) {
				createitem = 30218;
				mateitem = 40052;
				mateitem2 = 40396;
			} else if (code == 995) {
				createitem = 21265;
				mateitem = 40055;
				mateitem2 = 40394;
			} else if (code == 996) {
				createitem = 21266;
				mateitem = 40054;
				mateitem2 = 40395;
			}
			if (pc.getInventory().checkItem(149027, 50)) {
				if (pc.getInventory().checkItem(mateitem, 10) && pc.getInventory().checkItem(mateitem2, 5)) {
					pc.getInventory().consumeItem(149027, 50);
					pc.getInventory().consumeItem(mateitem, 10);
					pc.getInventory().consumeItem(mateitem2, 5);
					L1ItemInstance item = pc.getInventory().storeItem(createitem, 1);
					pc.sendPackets(new S_ServerMessage(403, item.getName()));
				}
			}
		} else if (code >= 997 && code <= 1001) {
			if (code == 997) {
				createitem = 21259;
				mateitem = 60358;
			} else if (code == 998) {
				createitem = 30218;
				mateitem = 60356;
			} else if (code == 999) {
				createitem = 21265;
				mateitem = 60355;
			} else if (code == 1000) {
				createitem = 21266;
				mateitem = 60357;
			} else if (code == 1001) {
				createitem = 21267;
				mateitem = 60358;

				return;
			}
			try {

				if (pc.getInventory().checkItem(149027, 50)) {
					if (pc.getInventory().consumeItem(mateitem, 1)) {
						pc.getInventory().consumeItem(149027, 50);
						L1ItemInstance item = pc.getInventory().storeItem(createitem, 1);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean npcshopNameCk(String name) {
		return NpcTable.getInstance().findNpcShopName(name);
	}

	private void �����ǺҰ�_���ν���������(L1PcInstance pc, int item_code) {
		
		if (pc.getInventory().checkItem(41246, 100000) && pc.getInventory().checkItem(60493, 1)) {
			L1ItemInstance ���� = pc.getInventory().findItemsIdNotEquipped_Enchant(119, 5);
			if (���� != null) {
				L1ItemInstance ���� = pc.getInventory().findItemsIdNotEquipped_Enchant(121, item_code - 926);
				if (���� != null) {
					pc.getInventory().consumeItem(41246, 100000);
					pc.getInventory().consumeItem(60493, 1);
					pc.getInventory().removeItem(����);
					pc.getInventory().removeItem(����);

					int enchant = 0;
					if (item_code == 938)
						enchant = 8;
					else if (item_code == 939)
						enchant = 9;

					L1Item tempL1Item = ItemTable.getInstance().getTemplate(291);
					L1ItemInstance tempitem = ItemTable.getInstance().FunctionItem(tempL1Item);
					tempitem.setIdentified(true);
					tempitem.setEnchantLevel(enchant);
					tempitem.setCount((int) 1);
					tempitem.setId(ObjectIdFactory.getInstance().nextId());
					pc.getInventory().storeItem(tempitem);
					if (item_code == 937) {
						tempitem.setBless(0);
						pc.getInventory().updateItem(tempitem, L1PcInventory.COL_BLESS);
						pc.getInventory().saveItem(tempitem, L1PcInventory.COL_BLESS);
					}
				}
			}
		}
	}

	private void logchangename(String chaName, String oldname, Timestamp datetime) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "INSERT INTO Log_Change_name SET Old_Name=?,New_Name=?, Time=?";
			pstm = con.prepareStatement(sqlstr);
			pstm.setString(1, oldname);
			pstm.setString(2, chaName);
			pstm.setTimestamp(3, datetime);
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private void ����(L1PcInstance pc, String s) {
		
		int itemid = 0;
		int ������þ = 7;
		int �������� = 0;
		if (s.equalsIgnoreCase("A")) {// 0�긶�Ǳ�
			itemid = 21169;
			�������� = 20095;
		} else if (s.equalsIgnoreCase("B")) {// 1�긶�Ǳ�
			itemid = 21169;
			������þ = 8;
			�������� = 20095;
		} else if (s.equalsIgnoreCase("C")) {// 2�긶�Ǳ�
			itemid = 21169;
			������þ = 9;
			�������� = 20095;
		} else if (s.equalsIgnoreCase("D")) {// 3�긶�Ǳ�
			itemid = 21169;
			������þ = 10;
			�������� = 20095;
		} else if (s.equalsIgnoreCase("E")) {// 0�긶���
			itemid = 21170;
			�������� = 20094;
		} else if (s.equalsIgnoreCase("F")) {// 1�긶���
			itemid = 21170;
			������þ = 8;
			�������� = 20094;
		} else if (s.equalsIgnoreCase("G")) {// 2�긶���
			itemid = 21170;
			������þ = 9;
			�������� = 20094;
		} else if (s.equalsIgnoreCase("H")) {// 3�긶���
			itemid = 21170;
			������þ = 10;
			�������� = 20094;
		} else if (s.equalsIgnoreCase("I")) {// 0�긶����
			itemid = 21171;
			�������� = 20092;
		} else if (s.equalsIgnoreCase("J")) {// 1�긶����
			itemid = 21171;
			������þ = 8;
			�������� = 20092;
		} else if (s.equalsIgnoreCase("K")) {// 2�긶����
			itemid = 21171;
			������þ = 9;
			�������� = 20092;
		} else if (s.equalsIgnoreCase("L")) {// 3�긶����
			itemid = 21171;
			������þ = 10;
			�������� = 20092;
		} else if (s.equalsIgnoreCase("M")) {// 0�긶�κ�
			itemid = 21172;
			�������� = 20093;
		} else if (s.equalsIgnoreCase("N")) {// 1�긶�κ�
			itemid = 21172;
			������þ = 8;
			�������� = 20093;
		} else if (s.equalsIgnoreCase("O")) {// 2�긶�κ�
			itemid = 21172;
			������þ = 9;
			�������� = 20093;
		} else if (s.equalsIgnoreCase("P")) {// 3�긶�κ�
			itemid = 21172;
			������þ = 10;
			�������� = 20093;
		}

		boolean ck = false;

		if (�������� != 0) {
			if (pc.getInventory().checkItem(41246, 100000)) {
				L1ItemInstance[] list1 = pc.getInventory().findItemsIdNotEquipped(��������);
				if (list1.length > 0) {
					L1ItemInstance ���� = null;
					L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(20110);
					for (L1ItemInstance item : list2) {
						if (item.getEnchantLevel() == ������þ) {
							���� = item;
							break;
						}
					}
					if (���� == null) {
						list2 = pc.getInventory().findItemsIdNotEquipped(1020110);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == ������þ) {
								���� = item;
								break;
							}
						}
					}
					if (���� != null) {
						pc.getInventory().removeItem(����);
						for (L1ItemInstance item2 : list1) {
							pc.getInventory().removeItem(item2);
							pc.getInventory().consumeItem(41246, 100000);
							ck = true;
							break;
						}
					}
				}
			}
		}
		if (ck && itemid != 0) {
			L1ItemInstance item = pc.getInventory().storeItem(itemid, 1, ������þ - 7);
			pc.sendPackets(new S_ServerMessage(403, item.getName()));
		}
	}

	/** XML�� �ƴ� �ҽ��� ó���Ҷ�(����ȣ�� ó��) **/
	private boolean Action(L1PcInstance pc, int itemid, int ����ȣ, int count) {
		boolean ck = false;
		for (; count > 0; count--) {
			switch (itemid) {
			case 8020:  //-- ȯ���� ����
			case 8022:  //-- ȯ���� ���� �ָӴ�
			case 40048:  //-- ��޴��̾�
			case 40051:  //-- ��޿��޶���
			case 40049:  //-- ��޷��
			case 40050:  //-- ��޻���
			case 40052:  //-- �ְ�޴��̾�
			case 40055:  //-- �ְ�޿��޶���
			case 40053:  //-- �ְ�޷��
			case 40054:  //-- �ְ�޻���
			case 6010:  //-- ����Ƽ�ƶ����
			case 6012:  //-- �����ǻ��޿���
			case 437010:  //-- �巡���Ǵ��̾Ƹ��
			case 437009:  //-- �巡���Ǻ�������
			{
				���(pc, itemid);
				ck = true;
			}
			break;
			case 60474: {
				if (����ȣ >= 284 && ����ȣ <= 290) {
					if (pc.getInventory().consumeItem(����ȣ, 1)) {
						L1ItemInstance item = pc.getInventory().storeItem(60474, 1, 0);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
			}
				break;
			case 60443: {
				if (����ȣ >= 60427 && ����ȣ <= 60438) {
					if (pc.getInventory().consumeItem(40308, 15) && pc.getInventory().consumeItem(����ȣ, 1)) {
						L1ItemInstance item = pc.getInventory().storeItem(60443, 1, 0);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
			}
				break;
			case 60423: {// ���� ȣ�ڼ�
				if (41246 != ����ȣ)
					continue;
				if (pc.getInventory().consumeItem(41246, 3000)) {
					L1ItemInstance item = pc.getInventory().storeItem(60423, 1, 0);
					pc.sendPackets(new S_ServerMessage(403, item.getName()));
					break;
				}
				ck = true;
			}
				break;
			case 7246:
				�����¸������尩(itemid, pc, ����ȣ);
				ck = true;
				break;
			case 7225:// �����ǵ���
				�����ǵ���(itemid, pc, ����ȣ);
				ck = true;
				break;
			case 7227:// ������ǵ���
				������ǵ���(itemid, pc, ����ȣ);
				ck = true;
				break;
			case 212551:// ����ȣ�ڰ���
			case 212552:
			case 212553:
			case 212554:
			case 212555:
			case 212556:
			case 212557:
				����ȣ�ڰ���(itemid, pc, ����ȣ);
				ck = true;
				break;
			case 60387:// 7ȯü
				for (int i = 410000; i <= 410001; i++) {
					if (i != ����ȣ)
						continue;
					L1ItemInstance ��� = null;
					if (pc.getInventory().checkItem(40308, 5000000)) {
						L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(i);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == 8) {
								��� = item;
								break;
							}
						}
					}
					if (��� != null) {
						pc.getInventory().removeItem(���);
						pc.getInventory().consumeItem(40308, 5000000);
						L1ItemInstance item = pc.getInventory().storeItem(275, 1, 7);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
				break;
			case 60388:// 8ȯü
				for (int i = 410000; i <= 410001; i++) {
					if (i != ����ȣ)
						continue;
					L1ItemInstance ��� = null;
					if (pc.getInventory().checkItem(40308, 10000000)) {
						L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(i);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == 9) {
								��� = item;
								break;
							}
						}
					}
					if (��� != null) {
						pc.getInventory().removeItem(���);
						pc.getInventory().consumeItem(40308, 10000000);
						L1ItemInstance item = pc.getInventory().storeItem(275, 1, 8);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
				break;
			case 60389:// 7��Ű
				for (int i = 410003; i <= 410004; i++) {
					if (i != ����ȣ)
						continue;
					L1ItemInstance ��� = null;
					if (pc.getInventory().checkItem(40308, 5000000)) {
						L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(i);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == 8) {
								��� = item;
								break;
							}
						}
					}
					if (��� != null) {
						pc.getInventory().removeItem(���);
						pc.getInventory().consumeItem(40308, 5000000);
						L1ItemInstance item = pc.getInventory().storeItem(266, 1, 7);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
				break;
			case 60390:// 8��Ű
				for (int i = 410003; i <= 410004; i++) {
					if (i != ����ȣ)
						continue;
					L1ItemInstance ��� = null;
					if (pc.getInventory().checkItem(40308, 10000000)) {
						L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(i);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == 9) {
								��� = item;
								break;
							}
						}
					}
					if (��� != null) {
						pc.getInventory().removeItem(���);
						pc.getInventory().consumeItem(40308, 10000000);
						L1ItemInstance item = pc.getInventory().storeItem(266, 1, 8);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
				break;
			case 60041:// 7����
			case 60048:// 8����
				/*
				 * ����������� ���۾��� case 60042: case 60043: case 60044: case 60045:
				 * case 60046: case 60047: case 60049: case 60050: case 60051:
				 * case 60052: case 60053: case 60054:
				 */
				����Ұ�(itemid, pc, ����ȣ);
				ck = true;
				break;
			case 430106:
			case 430104:
			case 430107:
			case 430105:
			case 430108:
			case 430109:
			case 430110:
				����(itemid, pc, ����ȣ);
				ck = true;
				break;
			case 60126:
			case 60128:
			case 60127:
			case 60129:
				������ũ(itemid, pc, ����ȣ);
				ck = true;
				break;
			case 40722:
				��ȣ��(pc, ����ȣ);
				ck = true;
				break;
			default:
				return false;
			}
		}
		return ck;
	}

	private void �������̺�(int code, L1PcInstance pc) {
		int craftitemid = 0;
		int craftitemen = 0;
		int craftmt = 0;
		int craftmtcount = 0;
		switch (code) {
		case 4350:
			craftitemid = 490020;
			craftitemen = 3;
			craftmt = 490030;
			craftmtcount = 6;
			break; // ����
		case 4351:
			craftitemid = 490022;
			craftitemen = 3;
			craftmt = 490032;
			craftmtcount = 6;
			break; // ȸ��
		}
		if (pc.getInventory().consumeItem(craftmt, craftmtcount)) {
			L1ItemInstance t = pc.getInventory().storeItem(craftitemid, 1, craftitemen);
			pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0�� //
																		// �־����ϴ�.
			commit("�������̺� : " + t.getName(), "", 1);
		}
	}

	private void ����(int code, L1PcInstance pc) {
		// System.out.println(code);
		int craftitemid = 0;
		int craftmt = 0;
		switch (code) {
		case 3559:
			craftitemid = 900015;
			craftmt = 3000075;
			break;
		case 3560:
			craftitemid = 900018;
			craftmt = 3000075;
			break;
		case 3561:
			craftitemid = 900017;
			craftmt = 3000075;
			break;
		case 3562:
			craftitemid = 900016;
			craftmt = 3000075;
			break;
		case 3555:
			craftitemid = 900011;
			craftmt = 3100053;
			break;
		case 3556:
			craftitemid = 900012;
			craftmt = 3100053;
			break;
		case 3557:
			craftitemid = 900013;
			craftmt = 3100053;
			break;
		case 3558:
			craftitemid = 900014;
			craftmt = 3100053;
			break;
		}
		// System.out.println(craftmt);
		if (pc.getInventory().consumeItem(craftmt, 1) && pc.getInventory().consumeItem(3000051, 1)) {
			// System.out.println(22222);
			L1ItemInstance t = pc.getInventory().storeItem(craftitemid, 1);
			pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0�� //
																		// �־����ϴ�.
			commit("���� : " + t.getName(), "", 1);
		}
	}

	private void ��Ű(int code, L1PcInstance pc) {
		int craftitemid = 0;
		int craftmt = 3000051;
		switch (code) {
		case 3563:
			craftitemid = 3000110;
			break;
		case 3564:
			craftitemid = 3000113;
			break;
		case 3565:
			craftitemid = 3000112;
			break;
		case 3566:
			craftitemid = 3000116;
			break;
		case 3567:
			craftitemid = 3000117;
			break;
		case 3568:
			craftitemid = 3000115;
			break;
		case 3569:
			craftitemid = 3000111;
			break;
		case 3570:
			craftitemid = 3000114;
			break;
		}

		if (pc.getInventory().consumeItem(craftmt, 1)) {
			L1ItemInstance t = pc.getInventory().storeItem(craftitemid, 1);
			pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0�� //
																		// �־����ϴ�.
			commit("��Ű : " + t.getName(), "", 1);
		}
	}

	private boolean ��þƮ����(L1PcInstance pc, int item_id, int count, int EnchantLevel) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(true);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else {
				pc.sendPackets(new S_ServerMessage(82));// ���� �������� �����ϰų� �κ��丮�� ������ �� �� �� �����ϴ�.
				return false;
			}
			pc.sendPackets(new S_SystemMessage("������ ���ۿ� �����߽��ϴ�."));
			pc.sendPackets(new S_ServerMessage(143, item.getLogName())); // %0�� �տ� �־����ϴ�.
			pc.sendPackets(new S_SkillSound(pc.getId(), 7976));
			pc.broadcastPacket(new S_SkillSound(pc.getId(), 7976));
			return true;
		} else {
			return false;
		}
	}
	/** ȯ���Ǻ���**/ 
	private void ȯ������(int code, L1PcInstance pc) {
		if (pc.getInventory().checkItem(40052, 1) && pc.getInventory().checkItem(40055, 1)
				&&pc.getInventory().checkItem(40053, 1) && pc.getInventory().checkItem(40054, 1)	
				&&pc.getInventory().checkItem(410061, 1)|| pc.getInventory().checkItem(500020, 1)
				){
			��þƮ����(pc, 31096, 1, 0);	
			pc.getInventory().consumeItem(40052, 1);
			pc.getInventory().consumeItem(40055, 1);
			pc.getInventory().consumeItem(40053, 1);
			pc.getInventory().consumeItem(40054, 1);
			pc.getInventory().consumeItem(410061, 1);
			pc.getInventory().consumeItem(500020, 1);
		}
	}
	/** itemcode �� XML action������ ó���Ҷ� **/
	private static String codeByAction(int code) {
		switch (code) {
		case 10:
			return "request druga treasure1 a";
		case 11:
			return "request druga treasure1 f";
		case 12:
			return "request druga treasure1 l";
		case 13:
			return "request druga treasure1 v";
		case 14:
			return "request druga treasure2 a";
		case 15:
			return "request druga treasure2 f";
		case 16:
			return "request druga treasure2 l";
		case 17:
			return "request druga treasure2 v";
		case 18:
			return "request druga treasure3 a";
		case 19:
			return "request druga treasure3 f";
		case 20:
			return "request druga treasure3 l";
		case 21:
			return "request druga treasure3 v";
		case 22:
			return "request druga treasure4 a";
		case 23:
			return "request druga treasure4 f";
		case 24:
			return "request druga treasure4 l";
		case 25:
			return "request druga treasure4 v";
		case 26:
			return "request druga treasure5 a";
		case 27:
			return "request druga treasure5 f";
		case 28:
			return "request druga treasure5 l";
		case 29:
			return "request druga treasure5 v";
		case 30:
			return "request druga treasure6 a";
		case 31:
			return "request druga treasure6 f";
		case 32:
			return "request druga treasure6 l";
		case 33:
			return "request druga treasure6 v";
		default:
			return "";
		}
	}

	private void �����ǵ���(int itemid, L1PcInstance pc, int ��������) {
		int enc = 9;
		// 9��ǳ ���εȻ����ǵ���

		// System.out.println(��������);
		if (pc.getInventory().checkItem(7336)// ���εȻ����ǵ���
				|| pc.getInventory().checkItem(7228)// ��ǳ�ǵ���
		) {
			for (L1ItemInstance item : pc.getInventory().findItemsId(7228)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					pc.getInventory().consumeItem(7336, 1);// ���εȻ����ǵ���
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																				// �տ�
																				// �־����ϴ�.
					return;
				}
			}
		}
	}

	private void ������ǵ���(int itemid, L1PcInstance pc, int ��������) {
		int enc = 9;
		// 9���� ���εȿ�����ǵ��� ����5 �Ƶ�100��
		int val = 1000000;

		// System.out.println(��������);
		if (pc.getInventory().checkItem(7335)// ���εȿ�����ǵ���
				|| pc.getInventory().checkItem(7225)// ����
				|| pc.getInventory().checkItem(40513, 5) // ������Ǵ���
				|| pc.getInventory().checkItem(40308, val) // �Ƶ���100��
		) {
			for (L1ItemInstance item : pc.getInventory().findItemsId(7225)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					pc.getInventory().consumeItem(7335, 1);// ���εȻ����ǵ���
					pc.getInventory().consumeItem(40513, 5);// ������Ǵ���5
					pc.getInventory().consumeItem(40308, val);// �Ƶ�100��
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																				// �տ�
																				// �־����ϴ�.
					return;
				}
			}
		}
	}

	Random _Random = new Random(System.nanoTime());

	private void ��ǳ�ǵ���(int code, L1PcInstance pc) {
		int �����æ = 0;
		int ������æ = 0;
		if (code == 1153) {
			�����æ = 8;
			������æ = 0;
		} else if (code == 1154) {
			�����æ = 9;
			������æ = 8;
		}
		if (pc.getInventory().checkItem(412005)// ��ǳ�ǵ���
				|| pc.getInventory().checkItem(41246, 100000)) { // ����ü10��
			for (L1ItemInstance item : pc.getInventory().findItemsId(412005)) {
				if (item.getEnchantLevel() == �����æ && !item.isEquipped()) {
					pc.getInventory().consumeItem(41246, 100000);// �Ƶ�100��
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(7228, 1, ������æ);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																				// �տ�
																				// �־����ϴ�.
					commit("������ �Ұ� : " + t.getName(), "", 1);
					return;
				}
			}
		}
	}

	private void ������(int code, L1PcInstance pc) {
		int ��æ = 0;
		int ��� = 0;
		if (code >= 1970 && code <= 1975) {// ü��
			��� = 21248;
			��æ = code - 1967;
		} else if (code >= 1976 && code <= 1981) {// ��������
			��� = 21247;
			��æ = code - 1973;
			/*
			 * }else if(code >= 1982 && code <= 1987){//���� ��� = 525110; ��æ =
			 * code - 1979; }else if(code >= 1988 && code <= 1993){//���� ��� =
			 * 525112; ��æ = code - 1985; }else if(code >= 1994 && code <=
			 * 1999){//ȸ�� ��� = 525109; ��æ = code - 1991;
			 */
		} else if (code >= 2000 && code <= 2005) {// ����
			��� = 21246;
			��æ = code - 1997;
		} else if (code >= 2006 && code <= 2011) {// ���
			��� = 21249;
			��æ = code - 2003;
		}
		L1ItemInstance ����1 = null;
		L1ItemInstance ����2 = null;
		int count = 0;
		if (pc.getInventory().checkItem(���, 2)) { // ��� üũ
			for (L1ItemInstance item : pc.getInventory().findItemsId(���)) {
				if (item.getEnchantLevel() == ��æ && !item.isEquipped()) {
					count++;
					if (count == 1) {
						����1 = item;
					}
					if (count == 2) {
						����2 = item;
						break;
					}
				}
			}
			if (����1 != null && ����2 != null) {
				pc.getInventory().deleteItem(����1);
				pc.getInventory().deleteItem(����2);
				L1ItemInstance t = pc.getInventory().storeItem(��� + 4, 1, ��æ);
				pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																			// �տ�
																			// �־����ϴ�.
				commit("��Ƽ���� ������ (������): " + t.getName(), "", 1);
			}
		}
	}

	private void ��Ƽ��(int code, L1PcInstance pc) {
		int ��æ = 0;
		int ��� = 0;
		if (code >= 1952 && code <= 1957) {
			��� = 500007;
			��æ = code - 1949;
		} else if (code >= 1958 && code <= 1963) {
			��� = 500008;
			��æ = code - 1955;
		} else if (code >= 1964 && code <= 1969) {
			��� = 500009;
			��æ = code - 1961;
		} else if (code >= 3203 && code <= 3208) {
			��� = 500010;
			��æ = code - 3200;
		}

		L1ItemInstance �Ͱ���1 = null;
		L1ItemInstance �Ͱ���2 = null;
		int count = 0;
		if (pc.getInventory().checkItem(���, 2)) { // ��� üũ
			for (L1ItemInstance item : pc.getInventory().findItemsId(���)) {
				if (item.getEnchantLevel() == ��æ && !item.isEquipped()) {
					count++;
					if (count == 1) {
						�Ͱ���1 = item;
					}
					if (count == 2) {
						�Ͱ���2 = item;
						break;
					}
				}
			}
			if (�Ͱ���1 != null && �Ͱ���2 != null) {
				pc.getInventory().deleteItem(�Ͱ���1);
				pc.getInventory().deleteItem(�Ͱ���2);
				L1ItemInstance t = pc.getInventory().storeItem(��� + 2000, 1, ��æ);
				pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																			// �տ�
																			// �־����ϴ�.
				commit("��Ƽ���� ������ : " + t.getName(), "", 1);
			}
		}
	}

	private void �����ǵ���(int code, L1PcInstance pc) {
		int �����æ = 0;
		int ������æ = 0;
		if (code == 1155) {
			�����æ = 0;
			������æ = 0;
		} else if (code == 1156) {
			�����æ = 3;
			������æ = 1;
		} else if (code == 1157) {
			�����æ = 5;
			������æ = 3;
		}
		if (pc.getInventory().checkItem(151)// ���󿢽�
				|| pc.getInventory().checkItem(41246, 200000)) { // ����ü20��
			for (L1ItemInstance item : pc.getInventory().findItemsId(151)) {
				if (item.getEnchantLevel() == �����æ && !item.isEquipped()) {
					pc.getInventory().consumeItem(41246, 200000);
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(7226, 1, ������æ);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																				// �տ�
																				// �־����ϴ�.
					commit("������ �Ұ� : " + t.getName(), "", 1);
					return;
				}
			}
		}
	}

	

	private void �ż��Ѹ�������6(int code, L1PcInstance pc) {
		int enc = 0;
		if (code == 1490)
			enc = 7;
		if (code == 1491)
			enc = 8;
		if (code == 1492)
			enc = 9;
		if (code == 1493)
			enc = 10;
		int count = 0;
		L1ItemInstance ����1 = null;
		L1ItemInstance ����2 = null;
		if (pc.getInventory().checkItem(20011) || pc.getInventory().checkItem(120011)) {
			for (L1ItemInstance item : pc.getInventory().findItemsIds(20011, 120011)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					count++;
					if (count == 1) {
						����1 = item;
					}
					if (count == 2) {
						����2 = item;
						break;
					}
				}
			}
			if (����1 != null && ����2 != null) {
				pc.getInventory().deleteItem(����1);
				pc.getInventory().deleteItem(����2);
				int ran = _Random.nextInt(100);
				if (ran < 5) {// �뼺�� Ȯ��
					int hh = enc - 1;

					L1ItemInstance t = pc.getInventory().storeItem(220011, 1, enc - 1);
					t.setBless(0);
					pc.getInventory().updateItem(t, L1PcInventory.COL_BLESS);
					pc.getInventory().saveItem(t, L1PcInventory.COL_BLESS);
					L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
							pc.getName() + "���� �ູ���� +" + hh + " " + t.getName() + " ���ۿ� �����Ͽ����ϴ�."));
					pc.sendPackets(new S_ServerMessage(143, "$10954", "$10947"), true);
					commit("�뼺�� ���� ���̺�^����/��(�ݼ�) : " + t.getName(), "", 1);
				} else {
					L1ItemInstance t = pc.getInventory().storeItem(220011, 1, enc - 1);
					pc.sendPackets(new S_ServerMessage(143, "$10954", "$10947"), true);
					commit("���� ���̺�^����/��(�ݼ�) : " + t.getName(), "", 1);
				}

			}
		}
	}

	private void �����¸������尩(int itemid, L1PcInstance pc, int ��������) {
		int enc = 7;

		// System.out.println(��������);
		if (pc.getInventory().checkItem(7245) || pc.getInventory().checkItem(40395)
				|| pc.getInventory().checkItem(149027, 10) || pc.getInventory().checkItem(7244, 300)
				|| pc.getInventory().checkItem(7248)) {
			for (L1ItemInstance item : pc.getInventory().findItemsId(7245)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					pc.getInventory().consumeItem(7248, 1);// ��������
					pc.getInventory().consumeItem(7244, 300);// �����ǽ�Ÿ��
					pc.getInventory().consumeItem(149027, 10);// �����Ǳ��
					pc.getInventory().consumeItem(40395, 1);// ������

					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																				// �տ�
																				// �־����ϴ�.
					return;
				}
			}
		}
	}

	private static int[] ��ȣ����� = new int[] { 90085, 90086, 90087, 90088, 90089, 90090, 90091, 90092 };

	private void ��ȣ��(L1PcInstance pc, int ��������) {
		int enc = 10;
		switch (��������) {
		case 15776:
			�������� = 90085;
			break;// ��հ�
		case 15777:
			�������� = 90086;
			break;// ������
		case 15778:
			�������� = 90087;
			break;// �Ѽհ�
		case 15779:
			�������� = 90088;
			break;// Ȱ
		case 15780:
			�������� = 90089;
			break;// �̵���
		case 15781:
			�������� = 90090;
			break;// ü�μҵ�
		case 15782:
			�������� = 90091;
			break;// Ű��ũ
		case 15783:
			�������� = 90092;
			break;// ����
		default:
			break;
		}
		// System.out.println(��������);
		for (int f : ��ȣ�����) {
			if (f != ��������)
				continue;
			for (L1ItemInstance item : pc.getInventory().findItemsId(f)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(40722, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																				// �տ�
																				// �־����ϴ�.
					return;
				}
			}
		}
	}

	


	private static int[] meterielWeapon2 = new int[] { 81, 162, 177, 194, 13 };

	private void ������ũ(int itemid, L1PcInstance pc, int ��������) {
		int enc = 8;
		int value = 5000000;
		if (itemid == 60127 || itemid == 60129) {
			enc = 9;
			value = 10000000;
		}
		// System.out.println(��������);
		if (pc.getInventory().checkItem(40308, value)) {
			for (int f : meterielWeapon2) {
				if (f != ��������)
					continue;
				for (L1ItemInstance item : pc.getInventory().findItemsId(f)) {
					if (item.getEnchantLevel() == enc && !item.isEquipped()) {
						pc.getInventory().consumeItem(40308, value);
						pc.getInventory().deleteItem(item);
						L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
						pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																					// �տ�
																					// �־����ϴ�.
						return;
					}
				}
			}
		}
	}

	private static int[] ��ȣ����� = new int[] { 21124, 427306, 256, 4500027, 263, 4500026, 265, 264, 60423 };

	private void ����ȣ�ڰ���(int itemid, L1PcInstance pc, int ����ȣ) {
		
		boolean ck = false;
		int enc = 0;
		int store_enc = 0;
		if (itemid == 212552 || itemid == 212553) {
			enc = 8;
		} else if (itemid == 212554 || itemid == 212555) {
			enc = 9;
			store_enc = 5;
		} else if (itemid == 212556 || itemid == 212557) {
			enc = 10;
			store_enc = 7;
		}

		if (itemid == 212552 || itemid == 212554 || itemid == 212556) {
			if (����ȣ != 256 && ����ȣ != 4500027 && ����ȣ != 263 && ����ȣ != 4500026 && ����ȣ != 265 && ����ȣ != 264)
				return;
		} else if (itemid == 212553 || itemid == 212555 || itemid == 212557) {
			if (����ȣ != 21124 && ����ȣ != 427306)
				return;
		}
		if (����ȣ == 21124 || ����ȣ == 427306 || ����ȣ == 60423)
			ck = true;

		if (ck || pc.getInventory().checkItem(40308, 150000)) {
			for (int f : ��ȣ�����) {
				if (f != ����ȣ)
					continue;
				for (L1ItemInstance item : pc.getInventory().findItemsId(f)) {
					if (item.getEnchantLevel() == enc && !item.isEquipped()) {
						if (!ck)
							pc.getInventory().consumeItem(40308, 150000);
						if (����ȣ == 60423) {
							if (!pc.getInventory().consumeItem(60423, 300))
								return;
						} else
							pc.getInventory().deleteItem(item);
						L1ItemInstance t = pc.getInventory().storeItem(21255, 1, store_enc);
						pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																					// �տ�
																					// �־����ϴ�.
						return;
					}
				}
			}
		}
	}

	private static int[] ����������� = new int[] { 5, 6, 32, 37, 42, 41, 52, 64, 99, 104, 125, 129, 145, 148, 180, 181,
			131 };

	private void ����Ұ�(int itemid, L1PcInstance pc, int ��������) {
		int enc = 8;
		int value = 5000000;
		if (itemid >= 60048 && itemid <= 60054) {
			enc = 9;
			value = 10000000;
		}
		// System.out.println(��������);
		if (pc.getInventory().checkItem(40308, value)) {
			for (int f : �����������) {
				if (f != ��������)
					continue;

				for (L1ItemInstance item : pc.getInventory().findItemsId(f)) {
					if (item.getEnchantLevel() == enc && !item.isEquipped()) {
						pc.getInventory().consumeItem(40308, value);
						pc.getInventory().deleteItem(item);
						L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
						pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																					// �տ�
																					// �־����ϴ�.
						return;
					}
				}
			}
		}
	}

	public static int[] stat = new int[] { 0, 0, 0, 0, 0 };

	private void ����(int itemid, L1PcInstance pc, int ��������) {
		int adena = 100000;
		int count = 1;
		int ��������2 = 0;
		if (itemid >= 430108 && itemid <= 430110) {
			adena = 200000;
		}
		if (�������� >= 5000063 && �������� <= 5000066) {
			count = 32;
		}
		if (itemid == 430106 && �������� != 430102 && �������� != 5000064) {
			return;
		} else if (itemid == 430104 && �������� != 430100 && �������� != 5000066) {
			return;
		} else if (itemid == 430105 && �������� != 430101 && �������� != 5000065) {
			return;
		} else if (itemid == 430107 && �������� != 430103 && �������� != 5000063) {
			return;
		} else if (itemid == 430108) {
			�������� = 430106;
			��������2 = 430104;
		} else if (itemid == 430109) {
			�������� = 430108;
			��������2 = 430105;
		} else if (itemid == 430110) {
			�������� = 430109;
			��������2 = 430107;
		}

		if (pc.getInventory().checkItem(40308, adena)) {
			if (��������2 == 0 || pc.getInventory().checkItem(��������2, 1)) {
				if (pc.getInventory().consumeItem(��������, count)) {
					pc.getInventory().consumeItem(40308, adena);
					if (��������2 != 0)
						pc.getInventory().consumeItem(��������2, 1);
					L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0��
																				// �տ�
																				// �־����ϴ�.
				}
			}
		}
	}
	private void ���(L1PcInstance pc, int itemid) {
		if (itemid == 8020) { // -- ȯ���� ����
			pc.getInventory().consumeItem(40052, 1);
			pc.getInventory().consumeItem(40055, 1);
			pc.getInventory().consumeItem(40053, 1);
			pc.getInventory().consumeItem(40054, 1);
			pc.getInventory().consumeItem(149027, 1);

		} else if (itemid == 8022) { // -- ȯ���� ���� �ָӴ�
			pc.getInventory().consumeItem(8020, 1);

		} else if (itemid == 40048) { // -- ��޴��̾�
			pc.getInventory().consumeItem(40044, 3);
			pc.getInventory().consumeItem(40308, 250);

		} else if (itemid == 40051) { // -- ��޿��޶���
			pc.getInventory().consumeItem(40047, 3);
			pc.getInventory().consumeItem(40308, 250);

		} else if (itemid == 40049) { // -- ��޷��
			pc.getInventory().consumeItem(40045, 3);
			pc.getInventory().consumeItem(40308, 250);

		} else if (itemid == 40050) { // -- ��޻���
			pc.getInventory().consumeItem(40045, 3);
			pc.getInventory().consumeItem(40308, 250);

		} else if (itemid == 40052) { // -- �ְ�޴��̾�
			pc.getInventory().consumeItem(40048, 3);
			pc.getInventory().consumeItem(40308, 2500);

		} else if (itemid == 40055) { // -- �ְ�޿��޶���
			pc.getInventory().consumeItem(40051, 3);
			pc.getInventory().consumeItem(40308, 2500);

		} else if (itemid == 40053) { // -- �ְ�޷��
			pc.getInventory().consumeItem(40049, 3);
			pc.getInventory().consumeItem(40308, 2500);

		} else if (itemid == 40050) { // -- �ְ�޻���
			pc.getInventory().consumeItem(40049, 3);
			pc.getInventory().consumeItem(40308, 2500);

		} else if (itemid == 6010) { // -- ����Ƽ�ƶ����
			pc.getInventory().consumeItem(6002, 1);

		} else if (itemid == 6012) { // -- ���ѻ��޿���
			pc.getInventory().consumeItem(6006, 1);

		} else if (itemid == 437010) { // -- �巡���Ǵ��̾Ƹ��
			pc.getInventory().consumeItem(5000067, 1);

		} else if (itemid == 437009) { // -- �巡���Ǻ�������
			pc.getInventory().consumeItem(5000068, 1);
		}

		L1ItemInstance t = pc.getInventory().storeItem(itemid, 1,0);
		pc.sendPackets(new S_ServerMessage(403, t.getLogName()));
	}
	/** ���� �������� �˻��Ѵ� ���� **/
	private void chaname(String chaName, String oldname) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("UPDATE characters SET char_name=? WHERE char_name=?");
			pstm.setString(1, chaName);
			pstm.setString(2, oldname);
			pstm.executeUpdate();
		} catch (Exception e) {

		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	class Send_createitemList implements Runnable {
		private LineageClient client = null;
		short i = 0;

		public Send_createitemList(LineageClient _client) {
			client = _client;
		}

		@Override
		public void run() {
			try {

				
				if (client == null /* || client.close */)
					return;

				client.sendPacket(new S_NewCreateItem(S_NewCreateItem_list.������Ŷ(i)), true);
				i++;
				if (i > 596) {
					// if(i > 347){
					client.��������Ŷ������ = false;
					return;
				}
				GeneralThreadPool.getInstance().schedule(this, 2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class Send_DollAlchemyInfo implements Runnable {
		private LineageClient client = null;
		short i = 0;

		public Send_DollAlchemyInfo(LineageClient _client) {
			client = _client;
		}

		@Override
		public void run() {
			if (client == null)
				return;

			String data = S_DollAlchemyInfo.������Ŷ(i++);

			if (data == null) {
				client.�����ռ���Ŷ������ = false;
				return;
			}

			client.sendPacket(new S_NewCreateItem(data), true);
			GeneralThreadPool.getInstance().schedule(this, 2);
		}
	}

	private static boolean isAlphaNumeric(String s) {
		boolean flag = true;
		char ac[] = s.toCharArray();
		int i = 0;
		do {
			if (i >= ac.length) {
				break;
			}
			if (!Character.isLetterOrDigit(ac[i])) {
				flag = false;
				break;
			}
			i++;
		} while (true);
		return flag;
	}

	private static boolean isInvalidName(String name) {
		int numOfNameBytes = 0;
		try {
			numOfNameBytes = name.getBytes("EUC-KR").length;
		} catch (UnsupportedEncodingException e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return false;
		}

		if (isAlphaNumeric(name)) {
			return false;
		}
		if (5 < (numOfNameBytes - name.length()) || 12 < numOfNameBytes) {
			return false;
		}

		if (BadNamesList.getInstance().isBadName(name)) {
			return false;
		}
		return true;
	}

	@Override
	public String getType() {
		return C_NEW_CREATEITEM;
	}
}
