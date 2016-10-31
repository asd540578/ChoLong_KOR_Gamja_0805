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
	private static final String[] textFilter = { "세호", "행복", "폭스", "칸즈", "철웅", "현민", "ㅍㅅ", "ㅎㅂ", "ㅋㅈ", "퐁스" };

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
				int totallen = readH();// 전체길이
				패킷위치변경((byte) 0x10);// 위치이동
				int chattype = readC();// 채팅타입
				패킷위치변경((byte) 0x1a);// 위치이동
				int chatlen = readC();// 채팅길이
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

				패킷위치변경((byte) 0x2a);// 위치이동
				int namelen = readC();// 이름길이
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
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.유저랭킹, cutlist, classId, 2, 1));
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.유저랭킹, cutlist2, classId, 2, 2));
				} else {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.유저랭킹, list, classId, 1, 1));
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

					if (excludeType == 1) {// 추가
						L1ExcludingList exList = pc.getExcludingList();
						L1ExcludingLetterList exletterList = pc.getExcludingLetterList();
						switch (subType) {// 일반 0 편지 1
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
									S_SystemMessage sm = new S_SystemMessage("차단된 사용자가 너무 많습니다.");
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
									S_SystemMessage sm = new S_SystemMessage("차단된 사용자가 너무 많습니다.");
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
					} else if (excludeType == 2) {// 삭제
						L1ExcludingList exList = pc.getExcludingList();
						L1ExcludingLetterList exletterList = pc.getExcludingLetterList();
						switch (subType) {// 일반 0 편지 1
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
				// if (client.제작템패킷전송중)
				// return;
				// readD();
				// int data = readH();
				// // System.out.println(data);
				// if (data != 0 && !client.제작템패킷전송) {
				// client.제작템패킷전송 = true;
				// if(data != 7582)
				// client.제작템패킷전송 = false;
				// }
				// if (client.제작템패킷전송) {
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.WINDOW), true);
				// } else {
				// client.제작템패킷전송중 = true;
				// client.제작템패킷전송 = true;
				// GeneralThreadPool.getInstance().schedule(
				// new Send_createitemList(client), 1);
				// }

			} else if (type == 0x38) {// 리스트
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
			} else if (type == 0x5c) {// 대성공 가능 아이템들
				if (pc.getInventory().calcWeightpercent() >= 90) {
					pc.sendPackets(new S_SystemMessage("제작 실패: 무게 게이지 90% 이상 제작 불가."));
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
			} else if (type == 0x3A) {// 구매
				if (pc.getInventory().calcWeightpercent() >= 90) {
					pc.sendPackets(new S_SystemMessage("제작 실패: 무게 게이지 90% 이상 제작 불가."));
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
				int 인첸재료템 = 0;
				try {
					readC();
					readD();
					인첸재료템 = readC();
					int 추가byte = readC();
					if (추가byte != 0x22) {
						인첸재료템 |= 추가byte / 2 << 8 & 0xff00;
					}
					인첸재료템 = 인첸템변환(인첸재료템);
				} catch (Exception e) {
				}
				if (count <= 0 || count >= 100)
					return;

				// System.out.println(itemcode);
				/** 제작리스트 코드를 디비 id로 **/
				int itemid = S_NewCreateItem.createItemByCode(itemcode);

				L1Object obj = L1World.getInstance().findObject(pc.createItemNpcObjid);
				if (obj == null)
					return;

				String s = "08 00 12 29 08 d3 7a 10 01 18 ff ff ff "
						+ "ff ff ff ff ff ff 01 20 00 28 01 30 00 38 01 42 " + "06 24 31 30 39 34 37 48 00 50 00 58 b0 "
						+ "2d 62 00 " + "9d 80";

				String ss = "08 01 1a 04 08 00 10 00 85 32"; // 실패
				// 실패할수있는 아이템 코드들
				if (itemcode >= 3555 && itemcode <= 3570) {
					if (CommonUtil.random(100) < 60) {// 실패
						pc.getInventory().consumeItem(3000051, 1);
						pc.sendPackets(new S_NewCreateItem(0X3b, ss));
						// pc.sendPackets(new S_SkillSound(pc.getId(), 2029));
						return;
					}
				}

				/** itemcode가 액션값으로 처리할지 **/
				String actions = codeByAction(itemcode);

				/** 소스로 처리 **/
				if (Action_ItemCode(pc, itemcode, count)) {
					pc.sendPackets(new S_NewCreateItem(0X3b, s));// 1479
					// pc.sendPackets(new S_ServerMessage(3556), true);
					pc.sendPackets(new S_SkillSound(pc.getId(), 2029));
				} else if (Action(pc, itemid, 인첸재료템, count)) {
					// pc.sendPackets(new S_ServerMessage(3556), true);
					pc.sendPackets(new S_NewCreateItem(0X3b, s));// 1479
					pc.sendPackets(new S_SkillSound(pc.getId(), 2029));
					/** 액션 값으로 인한 처리 **/
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
					/** NPCID와 생성ItemId로 인한 처리 **/
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
			} else if (type == 0x013D) { // 세금 관련 맵 표시
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
			} else if (type == 0x013F) {// 소셜액션
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
					// 1켄트 2기란 4오크요새
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

					if (!player.isCrown()) { // 군주 이외
						S_ServerMessage sm = new S_ServerMessage(478);
						player.sendPackets(sm, true);
						return;
					}
					if (clanId == 0) { // 크란미소속
						S_ServerMessage sm = new S_ServerMessage(272);
						player.sendPackets(sm, true);
						return;
					}
					L1Clan clan = L1World.getInstance().getClan(clanName);
					if (clan == null) { // 자크란이 발견되지 않는다
						S_SystemMessage sm = new S_SystemMessage("대상 혈맹이 발견되지 않았습니다.");
						player.sendPackets(sm, true);
						return;
					}
					if (player.getId() != clan.getLeaderId()) { // 혈맹주
						S_ServerMessage sm = new S_ServerMessage(478);
						player.sendPackets(sm, true);
						return;
					}
					if (clanName.toLowerCase().equals(s.toLowerCase())) { // 자크란을
																			// 지정
						S_SystemMessage sm = new S_SystemMessage("자신의 혈에 공성 선포는 불가능합니다.");
						player.sendPackets(sm, true);
						return;
					}
					L1Clan enemyClan = null;
					String enemyClanName = null;
					for (L1Clan checkClan : L1World.getInstance().getAllClans()) { // 크란명을
																					// 체크
						if (checkClan.getClanName().toLowerCase().equals(s.toLowerCase())) {
							enemyClan = checkClan;
							enemyClanName = checkClan.getClanName();
							break;
						}
					}
					if (enemyClan == null) { // 상대 크란이 발견되지 않았다
						S_SystemMessage sm = new S_SystemMessage("대상 혈맹이 발견되지 않았습니다.");
						player.sendPackets(sm, true);
						return;
					}
					if (clan.getAlliance(enemyClan.getClanId()) == enemyClan) {
						S_ServerMessage sm = new S_ServerMessage(1205);
						player.sendPackets(sm, true);
						return;
					}
					List<L1War> warList = L1World.getInstance().getWarList(); // 전쟁
																				// 리스트를
																				// 취득
					if (clan.getCastleId() != 0) { // 자크란이 성주
						S_ServerMessage sm = new S_ServerMessage(474);
						player.sendPackets(sm, true);
						return;
					}
					if (enemyClan.getCastleId() != 0 && // 상대 크란이 성주로, 자캐릭터가
														// Lv25 미만
							player.getLevel() < 25) {
						S_ServerMessage sm = new S_ServerMessage(475);
						player.sendPackets(sm, true); // 공성전을 선언하려면 레벨 25에 이르지
														// 않으면 안됩니다.
						return;
					}

					int onLineMemberSize = 0;
					for (L1PcInstance onlineMember : clan.getOnlineClanMember()) {
						if (onlineMember.isPrivateShop())
							continue;
						onLineMemberSize++;
					}

					if (onLineMemberSize < 15) {
						player.sendPackets(new S_SystemMessage("접속중인 혈맹 구성원이 [15]명 이상 되어야 선포가 가능합니다."), true);
						return;
					}

					if (clan.getHouseId() > 0) {
						S_SystemMessage sm = new S_SystemMessage("아지트가 있는 상태에서는 선전 포고를 할 수 없습니다.");
						player.sendPackets(sm, true);
						return;
					}
					if (enemyClan.getCastleId() != 0) { // 상대 크란이 성주
						int castle_id = enemyClan.getCastleId();
						if (WarTimeController.getInstance().isNowWar(castle_id)) { // 전쟁
																					// 시간내
							L1PcInstance clanMember[] = clan.getOnlineClanMember();
							for (int k = 0; k < clanMember.length; k++) {
								if (L1CastleLocation.checkInWarArea(castle_id, clanMember[k])) {
									// S_ServerMessage sm = new
									// S_ServerMessage(477);
									// player.sendPackets(sm, true); // 당신을 포함한
									// 모든 혈맹원이 성의 밖에 나오지 않으면 공성전은 선언할 수 없습니다.
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
								if (war.CheckClanInWar(enemyClanName)) { // 상대
																			// 크란이
																			// 이미
																			// 전쟁중
									war.DeclareWar(clanName, enemyClanName);
									war.AddAttackClan(clanName);
									enemyInWar = true;
									break;
								}
							}
							if (!enemyInWar) { // 상대 크란이 전쟁중 이외로, 선전포고
								L1War war = new L1War();
								war.handleCommands(1, clanName, enemyClanName); // 공성전
																				// 개시
							}
						} else { // 전쟁 시간외
							S_ServerMessage sm = new S_ServerMessage(476);
							player.sendPackets(sm, true); // 아직 공성전의 시간이 아닙니다.
						}
					} else { // 상대 크란이 성주는 아니다
						return;
					}
				} catch (Exception e) {
				}
			} else if (type == 0x0142) {// /혈맹가입
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
				L1PcInstance crown = clan.getonline간부();// L1World.getInstance().getPlayer(clan.getLeaderName());
				if (crown == null) {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 11), true);
					return;
				}

				if (clan.getJoinSetting() == 0) {
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 8), true);
					return;

				} else if (clan.getJoinType() == 0) {
					C_Attr.혈맹가입(crown, pc, clan);
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 0), true);
					return;
				} else {
					crown.setTempID(pc.getId()); // 상대의 오브젝트 ID를 보존해 둔다
					S_Message_YN myn = new S_Message_YN(97, pc.getName());
					crown.sendPackets(myn, true);
					pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_MESSAGE, 1), true);
				}

			} else if (type == 0x0146) { // 혈맹 가입신청 받기 설정
				if (pc.getClanid() == 0 || (!pc.isCrown() && pc.getClanRank() != L1Clan.CLAN_RANK_GUARDIAN))
					return;
				readC();
				readH();
				int setting = readC();
				readC();
				int setting2 = readC();
				if (setting2 == 2) {
					pc.sendPackets(new S_SystemMessage("현재 암호 가입 유형으로 설정할 수 없습니다."), true);
					setting2 = 1;
				}

				pc.getClan().setJoinSetting(setting);
				pc.getClan().setJoinType(setting2);
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_SETTING, setting, setting2), true);
				ClanTable.getInstance().updateClan(pc.getClan());
				pc.sendPackets(new S_ServerMessage(3980), true);
			} else if (type == 0x014C) { // 혈맹 모집 셋팅
				if (pc.getClanid() == 0)
					return;
				pc.sendPackets(new S_NewCreateItem(S_NewCreateItem.CLAN_JOIN_SETTING, pc.getClan().getJoinSetting(),
						pc.getClan().getJoinType()), true);
			} else if (type == 0x0152) { // 표식설정
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

					L1PcInstance 표식pc = null;

					// System.out.println(s);

					for (L1PcInstance player : pc.getParty().getMembers()) {
						// System.out.println(player.encobjid);
						if (s.equals(player.encobjid)) {
							player.표식 = subtype[0];
							표식pc = player;
						}
					}

					if (표식pc != null) {
						for (L1PcInstance player : pc.getParty().getMembers()) {
							player.sendPackets(new S_NewUI(0x53, 표식pc));
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (type == 0x01e0) { // 탐 예약취소
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
					pc.sendPackets(new S_SystemMessage("해당 케릭터만 취소를 할 수 있습니다."));
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
			} else if (type == 0x01cc) { // 혈맹 모집 셋팅
				pc.sendPackets(new S_NewCreateItem(pc.getAccountName(), S_NewCreateItem.TamPage));
			} else if (type == 0x84) { // 수상한 하늘정원

				if (!pc.PC방_버프) {
					pc.sendPackets(new S_SystemMessage("PC방 이용권을 사용중에만 사용 가능한 행동입니다."), true);
					return;
				}
				if (pc.getMapId() == 99 || pc.getMapId() == 6202) {
					pc.sendPackets(new S_SystemMessage("주위의 마력에의해 순간이동을 사용할 수 없습니다."), true);
					return;
				}

				if (!pc.getMap().isTeleportable()) {
					pc.sendPackets(new S_SystemMessage("주위의 마력에의해 순간이동을 사용할 수 없습니다."), true);
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
				int totallen = readH();// 전체길이
				패킷위치변경((byte) 0x10);// 위치이동
				int chattype = readC();// 채팅타입
				패킷위치변경((byte) 0x1a);// 위치이동
				int chatlen = readC();// 채팅길이
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

				패킷위치변경((byte) 0x2a);// 위치이동
				int namelen = readC();// 이름길이
				if (namelen != 0) {
					name = readS(namelen);
				}
				ChatWhisper(pc, chattype, totallen, chat, chat2, name);
			} else if (type == 0x7a) { // 인형합성창
				if (pc.getInventory().calcWeightpercent() >= 90) {
					pc.sendPackets(new S_SystemMessage("무게 게이지가 가득차서 합성을 진행할 수 없습니다."));
					return;
				}
				if (client.인형합성패킷전송중)
					return;

				readH();// length
				readC();
				readC();
				int unknown = readD();

				pc.sendPackets(new S_NewCreateItem(0x80, "00 00"));
				if (unknown != 0 && !client.인형합성패킷전송) {
					client.인형합성패킷전송 = true;
					if (unknown != -1528525972)
						client.인형합성패킷전송 = false;
				}
				if (client.인형합성패킷전송) {
					pc.sendPackets(new S_NewCreateItem(0x7b, "08 03 00 00"));
				} else {
					client.인형합성패킷전송중 = true;
					client.인형합성패킷전송 = true;
					GeneralThreadPool.getInstance().schedule(new Send_DollAlchemyInfo(client), 1);
				}
			} else if (type == 0x7c) { // 합성완료창
				// pc.sendPackets(new S_SystemMessage("업데이트 준비중 입니다."));
				int bytelen = readH();// 길이임
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
						pc.sendPackets(new S_SystemMessage("정상적인 방법으로만 이용해 주세요."));
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
					int suc = 1;// 실패

					boolean 월드메세지 = false;

					// 서로 같은 단계 아닐때 버그.
					// 실제로 인벤에 없을때 버그.

					Collections.shuffle(_usedoll);

					int itemid = _usedoll.get(0).getItemId();
					int dollid = itemid;
					L1ItemInstance sucitem = null;

					if (item_doll_code(itemid) == 1) {
						if (_usedoll.size() == 2) {
							if (rnd < 10) {// 성공
								suc = 0;
							}
						} else if (_usedoll.size() == 3) {
							if (rnd < 20) {// 성공
								suc = 0;
							}
						} else if (_usedoll.size() == 4) {
							if (rnd < 40) {// 성공
								suc = 0;
							}
						}
						if (suc == 0) {
							dollid = lv2doll[_Random.nextInt(lv2doll.length)];
							sucitem = ItemTable.getInstance().createItem(dollid);
						}
					} else if (item_doll_code(itemid) == 2) {
						if (_usedoll.size() == 2) {
							if (rnd < 10) {// 성공
								suc = 0;
							}
						} else if (_usedoll.size() == 3) {
							if (rnd < 20) {// 성공
								suc = 0;
							}
						} else if (_usedoll.size() == 4) {
							if (rnd < 50) {// 성공
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
							if (rnd < 6) {// 성공
								suc = 0;
							}
						} else if (_usedoll.size() == 3) {
							if (rnd < 14) {// 성공
								suc = 0;
							}
						} else if (_usedoll.size() == 4) {
							if (rnd < 25) {// 성공
								suc = 0;
							}
						}
						if (suc == 0) {
							dollid = lv4doll[_Random.nextInt(lv4doll.length)];
							sucitem = ItemTable.getInstance().createItem(dollid);
							월드메세지 = true;
						}
					} else if (item_doll_code(itemid) == 4) {
						if (_usedoll.size() == 2) {
							if (rnd < 3) {// 성공
								suc = 0;
							}
						} else if (_usedoll.size() == 3) {
							if (rnd < 7) {// 성공
								suc = 0;
							}
						} else if (_usedoll.size() == 4) {
							if (rnd < 15) {// 성공
								suc = 0;
							}
						}
						if (suc == 0) {
							dollid = lv5doll[_Random.nextInt(lv5doll.length)];
							sucitem = ItemTable.getInstance().createItem(dollid);
							월드메세지 = true;
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

						if (월드메세지) {
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
			} else if (type == 0x01e4) { // 캐릭터 생성
				try {
					int length = readH();// 길이
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
							break;// 모름
						case 0x10:
							classtype = b[1] & 0xff;
							break;// 클래스 타입
						case 0x18:
							status = b[1] & 0xff;
							break;// 초기상태 = 1 / 스탯변경상태 = 8

						case 0x20:
							unknown2 = b[1] & 0xff;
							break;// 모름
						case 0x28:
							unknown3 = b[1] & 0xff;
							break;// 모름

						case 0x30:
							str = b[1] & 0xff;
							break;// 힘
						case 0x38:
							inte = b[1] & 0xff;
							break;// 인트
						case 0x40:
							wis = b[1] & 0xff;
							break;// 위즈
						case 0x48:
							dex = b[1] & 0xff;
							break;// 덱
						case 0x50:
							con = b[1] & 0xff;
							break;// 콘
						case 0x58:
							cha = b[1] & 0xff;
							break;// 카리

						default:
							int i = 0;
							try {
								i = b[0] & 0xff;
							} catch (Exception e) {
							}
							System.out.println("[스탯관련 정의되지 않은 패킷] op : " + i);
							break;
						}
					}

					if (str != 0 && unknown3 != 1) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, str, con, "힘", classtype, null));
					}
					if (dex != 0) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, dex, 0, "덱", classtype, null));
					}
					if (con != 0 && unknown3 != 16) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, con, str, "콘", classtype, null));
					}
					if (inte != 0) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, inte, 0, "인트", classtype, null));
					}
					if (wis != 0) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, wis, 0, "위즈", classtype, null));
					}
					if (cha != 0) {
						client.sendPacket(new S_NewCreateItem(0x01e3, status, cha, 0, "카리", classtype, null));
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
		 * 41248 버그베어 41250 늑대인간 430000 돌골렘 430002 크러스트시안 430004 에티
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
			break;// 목각
		/*
		 * 430001 장로 41249 서큐 430500 코카 500108 인어 500109 눈사람
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
			break;// 라바골렘
		/*
		 * 500205 서큐퀸 500204 흑장로 500203 자이언트 60324 드레이크
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
			break;// 다이아골렘
		/*
		 * 500202 싸이클롭스 5000035 리치
		 */
		case 500202:
			doll = 4;
			break;
		case 5000035:
			doll = 4;
			break;
		case 600244:
			doll = 4;
			break;// 시어
		case 600245:
			doll = 4;
			break;// 나발
		case 142920:
			doll = 4;
			break;// 아이리스
		case 142921:
			doll = 4;
			break;// 뱀파
		case 751:
			doll = 4;
			break;// 머미로드
		case 600246:
			doll = 5;
			break;// 데몬
		case 600247:
			doll = 5;
			break;// 데스
		case 142922:
			doll = 5;
			break;// 바란카
		case 752:
			doll = 5;
			break;// 타락
		default:
			break;
		}
		return doll;
	}

	public int doll_item_code(int doll) {
		int item = 0;
		switch (doll) {
		/*
		 * 150 버그베어 151 늑대인간 152 돌골렘 153 크러스트시안 154 에티
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
		 * 155 장로 156 서큐 157 코카 158 인어 159 눈사람
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
		 * 160 서큐퀸 161 흑장로 162 자이언트 163 드레이크
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
		 * 164 싸이클롭스 165 리치
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
			if (pc.캐릭명변경) {
				try {
					String chaName = chatText;
					if (pc.getClanid() > 0) {
						pc.sendPackets(new S_SystemMessage("혈맹탈퇴후 캐릭명을 변경할수 있습니다."));
						pc.캐릭명변경 = false;
						return;
					}
					if (!pc.getInventory().checkItem(467009, 1)) { // 있나 체크
						pc.sendPackets(new S_SystemMessage("케릭명 변경 비법서를 소지하셔야 가능합니다."));
						pc.캐릭명변경 = false;
						return;
					}
					for (int i = 0; i < chaName.length(); i++) {
						if (chaName.charAt(i) == 'ㄱ' || chaName.charAt(i) == 'ㄲ' || chaName.charAt(i) == 'ㄴ'
								|| chaName.charAt(i) == 'ㄷ' || // 한문자(char)단위로
																// 비교.
								chaName.charAt(i) == 'ㄸ' || chaName.charAt(i) == 'ㄹ' || chaName.charAt(i) == 'ㅁ'
								|| chaName.charAt(i) == 'ㅂ' || // 한문자(char)단위로
																// 비교
								chaName.charAt(i) == 'ㅃ' || chaName.charAt(i) == 'ㅅ' || chaName.charAt(i) == 'ㅆ'
								|| chaName.charAt(i) == 'ㅇ' || // 한문자(char)단위로
																// 비교
								chaName.charAt(i) == 'ㅈ' || chaName.charAt(i) == 'ㅉ' || chaName.charAt(i) == 'ㅊ'
								|| chaName.charAt(i) == 'ㅋ' || // 한문자(char)단위로
																// 비교.
								chaName.charAt(i) == 'ㅌ' || chaName.charAt(i) == 'ㅍ' || chaName.charAt(i) == 'ㅎ'
								|| chaName.charAt(i) == 'ㅛ' || // 한문자(char)단위로
																// 비교.
								chaName.charAt(i) == 'ㅕ' || chaName.charAt(i) == 'ㅑ' || chaName.charAt(i) == 'ㅐ'
								|| chaName.charAt(i) == 'ㅔ' || // 한문자(char)단위로
																// 비교.
								chaName.charAt(i) == 'ㅗ' || chaName.charAt(i) == 'ㅓ' || chaName.charAt(i) == 'ㅏ'
								|| chaName.charAt(i) == 'ㅣ' || // 한문자(char)단위로
																// 비교.
								chaName.charAt(i) == 'ㅠ' || chaName.charAt(i) == 'ㅜ' || chaName.charAt(i) == 'ㅡ'
								|| chaName.charAt(i) == 'ㅒ' || // 한문자(char)단위로
																// 비교.
								chaName.charAt(i) == 'ㅖ' || chaName.charAt(i) == 'ㅢ' || chaName.charAt(i) == 'ㅟ'
								|| chaName.charAt(i) == 'ㅝ' || // 한문자(char)단위로
																// 비교.
								chaName.charAt(i) == 'ㅞ' || chaName.charAt(i) == 'ㅙ' || chaName.charAt(i) == 'ㅚ'
								|| chaName.charAt(i) == 'ㅘ' || // 한문자(char)단위로
																// 비교.
								chaName.charAt(i) == '씹' || chaName.charAt(i) == '좃' || chaName.charAt(i) == '좆'
								|| chaName.charAt(i) == 'ㅤ') {
							pc.sendPackets(new S_SystemMessage("사용할수없는 케릭명입니다."));
							pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
							pc.캐릭명변경 = false;
							return;
						}
					}
					if (chaName.getBytes().length > 12) {
						pc.sendPackets(new S_SystemMessage("이름이 너무 깁니다."));
						pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
						pc.캐릭명변경 = false;
						return;
					}
					if (chaName.length() == 0) {
						pc.sendPackets(new S_SystemMessage("변경할 케릭명을 입력하세요."));
						pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
						pc.캐릭명변경 = false;
						return;
					}
					if (BadNamesList.getInstance().isBadName(chaName)) {
						pc.sendPackets(new S_SystemMessage("사용할 수 없는 케릭명입니다."));
						pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
						pc.캐릭명변경 = false;
						return;
					}
					if (isInvalidName(chaName)) {
						pc.sendPackets(new S_SystemMessage("사용할 수 없는 케릭명입니다."));
						pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
						pc.캐릭명변경 = false;
						return;
					}
					if (CharacterTable.doesCharNameExist(chaName)) {
						pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
						pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
						pc.캐릭명변경 = false;
						return;
					}
					if (CharacterTable.RobotNameExist(chaName)) {
						pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
						pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
						pc.캐릭명변경 = false;
						return;
					}
					if (CharacterTable.RobotCrownNameExist(chaName)) {
						pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
						pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
						pc.캐릭명변경 = false;
						return;
					}
					if (NpcShopSpawnTable.getInstance().getNpc(chaName) || npcshopNameCk(chaName)) {
						pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
						pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
						pc.캐릭명변경 = false;
						return;
					}
					if (CharacterTable.somakname(chaName)) {
						pc.sendPackets(new S_SystemMessage("동일한 케릭명이 존재합니다."));
						pc.sendPackets(new S_SystemMessage("캐릭명 변경 비법서를 다시 클릭후 이용해 주세요."));
						pc.캐릭명변경 = false;
						return;
					}

					pc.getInventory().consumeItem(467009, 1); // 소모

					String oldname = pc.getName();

					chaname(chaName, oldname);

					long sysTime = System.currentTimeMillis();
					logchangename(chaName, oldname, new Timestamp(sysTime));

					pc.sendPackets(new S_SystemMessage(chaName + " 아이디로 변경 하셨습니다."));
					pc.sendPackets(new S_SystemMessage("원할한  이용을 위해 클라이언트가 강제로 종료 됩니다."));

					Thread.sleep(1000);
					clientthread.kick();
				} catch (Exception e) {
				}
				return;
			}
			if (clientthread.AutoCheck) {
				if (chatText.equalsIgnoreCase(clientthread.AutoAnswer)) {
					pc.sendPackets(new S_SystemMessage("자동 방지 답을 성공적으로 입력하였습니다."), true);
					while (pc.isTeleport() || pc.텔대기()) {
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
						pc.sendPackets(new S_SystemMessage("자동 방지 답을 잘못 입력하였습니다."), true);
						while (pc.isTeleport() || pc.텔대기()) {
							Thread.sleep(100);
						}

						if (!GMCommands.autocheck_Tellist.contains(clientthread.getAccountName())) {
							GMCommands.autocheck_Tellist.add(clientthread.getAccountName());
						}

						L1Teleport.teleport(pc, 32928, 32864, (short) 6202, 5, true);

					} else {
						pc.sendPackets(new S_SystemMessage("자동 방지 답을 잘못 입력하였습니다. 기회는 총3번입니다."), true);
						// pc.sendPackets(new
						// S_PacketBox(S_PacketBox.GREEN_MESSAGE,
						// "자동 방지 : [ "+pc.getNetConnection().AutoQuiz+" ] 답을
						// 채팅창에 입력해주세요."),
						// true);
						// pc.sendPackets(new
						// S_SystemMessage("자동 방지 : [
						// "+pc.getNetConnection().AutoQuiz+" ] 답을 채팅창에
						// 입력해주세요."),
						// true);
						pc.sendPackets(
								new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "자동 방지 : " + pc.getNetConnection().AutoQuiz),
								true);
						pc.sendPackets(new S_SystemMessage("자동 방지 : " + pc.getNetConnection().AutoQuiz), true);
						return;
					}
					/*
					 * if(clientthread.AutoCheckCount >= 2){
					 * clientthread.kick(); return; } pc.sendPackets(new
					 * S_SystemMessage("오토 방지 코드를 잘못 입력하셨습니다."), true);
					 * clientthread.AutoCheckCount++; Random _rnd = new
					 * Random(System.nanoTime()); int x = _rnd.nextInt(30); int
					 * y = _rnd.nextInt(30); clientthread.AutoAnswer = ""+(x+y);
					 * pc.sendPackets(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
					 * "오토 방지 코드 [ "+x+" + "+y+" = ? ] 답을 입력해주세요."), true);
					 * pc.sendPackets(new S_SystemMessage("오토 방지 코드 [ "+x+" + "
					 * +y +" = ? ] 답을 입력해주세요."), true);
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
			if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) { // 채팅
																								// 금지중
				S_ServerMessage sm = new S_ServerMessage(242);
				pc.sendPackets(sm); // 현재 채팅 금지중입니다.
				sm = null;
				return;
			}

			if (pc.isDeathMatch() && !pc.isGhost() && !pc.isGm()) {
				S_ServerMessage sm = new S_ServerMessage(912);
				pc.sendPackets(sm); // 채팅을 할 수 없습니다.
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
				if (chatText.startsWith(".시각")) {
					StringBuilder sb = null;
					sb = new StringBuilder();
					TimeZone kst = TimeZone.getTimeZone("GMT+9");
					Calendar cal = Calendar.getInstance(kst);
					sb.append("[Server Time]" + cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월 "
							+ cal.get(Calendar.DATE) + "일 " + cal.get(Calendar.HOUR_OF_DAY) + ":"
							+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
					S_SystemMessage sm = new S_SystemMessage(sb.toString());
					pc.sendPackets(sm, true);
					sb = null;
					return;
				}
				// GM커멘드
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
				if (chatText.startsWith(".")) { // 유저코멘트
					String cmd = chatText.substring(1);
					if (cmd == null) {
						return;
					}
					UserCommands.getInstance().handleCommands(pc, cmd);
					return;
				}

				if (chatText.startsWith("$")) { // 월드채팅
					if (pc.isGm())
						chatWorld(pc, chatdata, chatType, chatcount, chatText);
					else
						chatWorld(pc, chatdata, 12, chatcount, chatText);
					if (!pc.isGm()) {
						pc.checkChatInterval();
					}
					return;
				}

				/** 텔렉 풀기 **/
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
				// 돕펠 처리
				L1MonsterInstance mob = null;
				for (L1Object obj : pc.getNearObjects().getKnownObjects()) {
					if (obj instanceof L1MonsterInstance) {
						mob = (L1MonsterInstance) obj;
						if (mob.getNpcTemplate().is_doppel() && mob.getName().equals(pc.getName())) {
							Broadcaster.broadcastPacket(mob, new S_NpcChatPacket(mob, chatText, 0), true);
						}
					}
				}
				eva.LogChatNormalAppend("[일반]", pc.getName(), chatText);
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
				eva.LogChatNormalAppend("[일반]", pc.getName(), chatText);
				// 돕펠 처리
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
				if (pc.getClanid() != 0) { // 크란 소속중
					L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
					S_NewCreateItem chat4 = new S_NewCreateItem(pc, 4, chatType, chatText, "");
					if (Config.혈맹채팅모니터() > 0) {
						for (L1PcInstance gm : Config.toArray혈맹채팅모니터()) {
							if (gm.getNetConnection() == null) {
								Config.remove혈맹(gm);
								continue;
							}
							if (gm == pc) {
								continue;
							}
						}
					}
					eva.LogChatClanAppend("[혈맹]", pc.getName(), pc.getClanname(), chatText);
					for (L1PcInstance listner : clan.getOnlineClanMember()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(chat4);
						}
					}
				}
			}
				break;
			case 11: {
				if (pc.isInParty()) { // 파티중
					// S_NewCreateItem s_chatpacket = new
					// S_NewCreateItem(chatType, chatdata, chatcount, pc);
					S_NewCreateItem s_chatpacket = new S_NewCreateItem(pc, 4, chatType, chatText, "");
					for (L1PcInstance listner : pc.getParty().getMembers()) {
						if (!listner.getExcludingList().contains(pc.getName())) {
							listner.sendPackets(s_chatpacket);
						}
					}
				}
				if (Config.파티채팅모니터() > 0) {
					for (L1PcInstance gm : Config.toArray파티채팅모니터()) {
						if (gm.getNetConnection() == null) {
							Config.remove파티(gm);
							continue;
						}
						if (gm == pc) {
							continue;
						}
					}
				}
				eva.PartyChatAppend("[파티]", pc.getName(), chatText);
			}
				break;
			case 12:
				if (pc.isGm())
					chatWorld(pc, chatdata, chatType, chatcount, chatText);
				else
					chatWorld(pc, chatdata, 3, chatcount, chatText);
				break;
			case 13: { // 수호기사 채팅
				if (pc.getClanid() != 0) { // 혈맹 소속중
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
				if (Config.혈맹채팅모니터() > 0) {
					for (L1PcInstance gm : Config.toArray혈맹채팅모니터()) {
						if (gm.getNetConnection() == null) {
							Config.remove혈맹(gm);
							continue;
						}
						if (gm == pc) {
							continue;
						}
					}
				}
				eva.PartyChatAppend("[연합]", pc.getName(), chatText);
			}
				break;
			case 14: { // 채팅 파티
				if (pc.isInChatParty()) { // 채팅 파티중
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
				if (Config.파티채팅모니터() > 0) {
					for (L1PcInstance gm : Config.toArray파티채팅모니터()) {
						if (gm.getNetConnection() == null) {
							Config.remove파티(gm);
							continue;
						}
						if (gm == pc) {
							continue;
						}
					}
				}
			}
				break;
			case 15: { // 동맹채팅
				if (pc.getClanid() != 0) { // 혈맹 소속중
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
							} // 자기혈맹 전송용

							for (int j = 0; j < allianceids.length; j++) {
								TargegClan = clan.getAlliance(allianceids[j]);
								if (TargegClan != null) {
									TargetClanName = TargegClan.getClanName();
									if (TargetClanName != null) {
										for (L1PcInstance alliancelistner : TargegClan.getOnlineClanMember()) {
											alliancelistner.sendPackets(s_chatpacket);
										} // 동맹혈맹 전송용
									}
								}

							}
						}

					}
				}
				break;
			}
			case 17:
				if (pc.getClanid() != 0) { // 혈맹 소속중
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
				if (pc.get_food() >= 12) { // 5%겟지?
					S_PacketBox pb = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
					pc.sendPackets(pb, true);
					if (chatType == 3) {
						S_PacketBox pb2 = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
						pc.sendPackets(pb2, true);
						if (pc.isGm()) {
							L1World.getInstance().broadcastPacketToAll(new S_NewCreateItem(pc, 4, chatType, text, ""));
							L1World.getInstance().broadcastPacketToAll(
									new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[******] " + text));
							eva.WorldChatAppend("[전체]", pc.getName(), text);
							return;
						}
						eva.WorldChatAppend("[전체]", pc.getName(), text);
						
					} else if (chatType == 12) {
						S_PacketBox pb3 = new S_PacketBox(S_PacketBox.FOOD, pc.get_food());
						pc.sendPackets(pb3, true);
						if (pc.isGm()) {
							L1World.getInstance().broadcastPacketToAll(
									new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "[******] " + text));
							eva.WorldChatAppend("[전체]", pc.getName(), text);
							return;
						}
						eva.LogChatTradeAppend("[장사]", pc.getName(), text);
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
			// 채팅 금지중의 경우
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

			if (!whisperFrom.isGm() && (targetName.compareTo("메티스") == 0)) {
				S_SystemMessage sm = new S_SystemMessage("운영자님께는 귓속말을 할 수 없습니다.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (targetName.equalsIgnoreCase("***")) {
				S_SystemMessage sm = new S_SystemMessage("-> (***) " + text);
				whisperFrom.sendPackets(sm, true);
				return;
			}

			L1PcInstance whisperTo = L1World.getInstance().getPlayer(targetName);

			// 월드에 없는 경우
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
			// 자기 자신에 대한 wis의 경우
			if (whisperTo.equals(whisperFrom)) {
				return;
			}

			if (whisperTo.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CHAT_PROHIBITED)) {
				S_SystemMessage sm = new S_SystemMessage("채팅금지중인 유저에게 귓속말은 할수 없습니다.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (text.length() > 26) {
				S_SystemMessage sm = new S_SystemMessage("귓말로 보낼 수 있는 글자수를 초과하였습니다.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			// 차단되고 있는 경우
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

			if (Config.귓말채팅모니터() > 0) {
				S_SystemMessage sm = new S_SystemMessage(
						whisperFrom.getName() + " -> (" + whisperTo.getName() + ") " + text);
				for (L1PcInstance gm : Config.toArray귓말채팅모니터()) {
					if (gm.getNetConnection() == null) {
						Config.remove귓말(gm);
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
			pstm = con.prepareStatement("SELECT day FROM `tam` WHERE encobjid = ? order by id asc limit 1"); // 케릭터
																												// 테이블에서
																												// 군주만
																												// 골라와서
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
			pstm = con.prepareStatement("SELECT objid FROM `tam` WHERE encobjid = ? order by id asc limit 1"); // 케릭터
																												// 테이블에서
																												// 군주만
																												// 골라와서
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
		
		if (pc.Gamble_Somak) { // 소막
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

	/** ItemClient 코드를 디비 id로 변경 **/
	private int 인첸템변환(int 인첸재료템) {
		
		switch (인첸재료템) {
		case 15053: // 환상의양손검
			return 284;
		case 15054: // 환상의지팡이
			return 285;
		case 15055: // 환상의한손검
			return 286;
		case 15056: // 환상의활
			return 287;
		case 15057: // 환상의이도류
			return 288;
		case 15058: // 환상의체인소드
			return 289;
		case 15059: // 환상의키링크
			return 290;
		case 231: // 마법사의지팡이
			return 129;
		case 232: // 마력의지팡이
			return 125;
		case 1206:// 라스타바드단검
			return 6;
		case 402:// 레이피어
			return 42;
		case 397:// 크로스보우
			return 180;
		case 233:// 힘의지팡이
			return 131;
		case 235:// 다마스커스검
			return 37;
		case 4102:
		case 6:// 양손검
			return 52;
		case 4177:
		case 81:// 장궁
			return 181;
		case 4098:
		case 2:// 일본도
			return 41;
		case 4135:
		case 39:// 요단
			return 5;
		case 4140:
		case 44:// 포챠드
			return 104;
		case 4166:
		case 70:// 요정족창
			return 99;
		case 138:// 대도
			return 148;
		case 141:// 그라디우스
			return 32;
		case 144:// 광도
			return 145;
		case 269:// 크로
			return 180;
		case 274:// 레이
			return 42;
		case 465:// 대검
			return 64;
		case 3217:// 사키
			return 410003;
		case 3218:// 흑키
			return 410004;
		case 3223:// 소체
			return 410000;
		case 3224:// 파체
			return 410001;
		case 4012:// 지룡마안
			return 430106;
		case 4013:// 수룡마안
			return 430104;
		case 4015:// 풍룡마안
			return 430105;
		case 4014:// 화룡마안
			return 430107;

		case 4032:// 봉인 지룡 마안
			return 430102;
		case 4033:// 봉인 수룡 마안
			return 430100;
		case 4034:// 봉인 화룡 마안
			return 430103;
		case 4035:// 봉인 풍룡 마안
			return 430101;
		case 10664:// 지룡의표식
			return 5000064;
		case 10665:// 수룡의표식
			return 5000066;
		case 10666:// 풍룡의표식
			return 5000065;
		case 10667:// 화룡의표식
			return 5000063;

		case 2257:// 결정체
			return 41246;
		case 14820:// 용의호박석
			return 60423;
		case 14024: // 단단한호박갑옷
			return 21124;
		case 14025: // 호박장갑
			return 427306;
		case 14026: // 호박검
			return 256;
		case 14027: // 호박양손검
			return 4500027;
		case 14028: // 지식의호박지팡이
			return 263;
		case 14029: // 호박 각궁
			return 4500026;
		case 14030: // 호박 체인소드
			return 265;
		case 14031: // 호박 키링크
			return 264;
		case 13044: // 자음 블록(ㄱ)
			return 60427;
		case 13045: // 자음 블록(ㄷ)
			return 60428;
		case 13046: // 자음 블록(ㄹ)
			return 60429;
		case 13047: // 자음 블록(ㅁ)
			return 60430;
		case 13048: // 자음 블록(ㅂ)
			return 60431;
		case 13049: // 자음 블록(ㅅ)
			return 60432;
		case 13050: // 자음 블록(ㅇ)
			return 60433;
		case 13051: // 자음 블록(ㅈ)
			return 60434;
		case 13052: // 자음 블록(ㅊ)
			return 60435;
		case 13053: // 자음 블록(ㅋ)
			return 60436;
		case 13054: // 자음 블록(ㅌ)
			return 60437;
		case 13055: // 자음 블록(ㅍ)
			return 60438;
		case 1177: // 핑거오브데스
			return 13;
		case 981: // 흑빛의 활
			return 177;
		case 980: // 흑빛의크로우
			return 162;
		case 979: // 흑빛의이도류
			return 81;
		case 943: // 진건틀렛
			return 194;
		default:
			return 인첸재료템;
		}
	}

	private boolean Action_ItemCode(L1PcInstance pc, int item_code, int count) {
		boolean ck = false;
		for (; count > 0; count--) {

			if ((item_code >= 1952 && item_code <= 1969) || (item_code >= 3203 && item_code <= 3208)) {
				룸티스(item_code, pc);
				ck = true;
				continue;
			}
			if (item_code >= 1970 && item_code <= 2011) {
				스냅퍼(item_code, pc);
				ck = true;
				continue;
			}
			switch (item_code) {
			case 4350:
			case 4351:
				문장테이블(item_code, pc);
				ck = true;
				break;
			case 3559:// 이번호는 원래 아까 그번호를 써줘도 대는부분인데 1736? 그거요 근데 감자팩은 또 다르케분석해서
						// 제가 감자팩에마춰서 코드 뺀거에요넵
			case 3560:
			case 3561:
			case 3562:
			case 3555:
			case 3556:
			case 3557:
			case 3558:
				레옹(item_code, pc);
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
				럭키(item_code, pc);
				ck = true;
				break;
			case 2739:
				환생보석(item_code, pc);
				ck = true;
				break;
			case 655:
				안톤(pc, "A");
				ck = true;
				break;
			case 656:
				안톤(pc, "E");
				ck = true;
				break;
			case 657:
				안톤(pc, "I");
				ck = true;
				break;
			case 658:
				안톤(pc, "M");
				ck = true;
				break;
			case 659:
				안톤(pc, "B");
				ck = true;
				break;
			case 660:
				안톤(pc, "F");
				ck = true;
				break;
			case 661:
				안톤(pc, "J");
				ck = true;
				break;
			case 662:
				안톤(pc, "N");
				ck = true;
				break;
			case 663:
				안톤(pc, "C");
				ck = true;
				break;
			case 664:
				안톤(pc, "G");
				ck = true;
				break;
			case 665:
				안톤(pc, "K");
				ck = true;
				break;
			case 666:
				안톤(pc, "O");
				ck = true;
				break;
			case 667:
				안톤(pc, "D");
				ck = true;
				break;
			case 668:
				안톤(pc, "H");
				ck = true;
				break;
			case 669:
				안톤(pc, "L");
				ck = true;
				break;
			case 670:
				안톤(pc, "P");
				ck = true;
				break;
			case 935:
			case 936:
			case 937:
				조우의불골렘_제로스의지팡이(pc, item_code);
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
				스탯부츠(pc, item_code);
				ck = true;
				break;

			case 1490:
			case 1491:
			case 1492:
			case 1493:
				신성한마투세끼6(item_code, pc);
				ck = true;
				break;
			case 1153:
			case 1154:
				질풍의도끼(item_code, pc);
				ck = true;
				break;
			case 1155:
			case 1156:
			case 1157:
				마물의도끼(item_code, pc);
				ck = true;
				break;
				

			default:
				return false;
			}

		}
		return ck;
	}

	private void 스탯부츠(L1PcInstance pc, int code) {
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

	private void 조우의불골렘_제로스의지팡이(L1PcInstance pc, int item_code) {
		
		if (pc.getInventory().checkItem(41246, 100000) && pc.getInventory().checkItem(60493, 1)) {
			L1ItemInstance 데지 = pc.getInventory().findItemsIdNotEquipped_Enchant(119, 5);
			if (데지 != null) {
				L1ItemInstance 얼지 = pc.getInventory().findItemsIdNotEquipped_Enchant(121, item_code - 926);
				if (얼지 != null) {
					pc.getInventory().consumeItem(41246, 100000);
					pc.getInventory().consumeItem(60493, 1);
					pc.getInventory().removeItem(데지);
					pc.getInventory().removeItem(얼지);

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

	private void 안톤(L1PcInstance pc, String s) {
		
		int itemid = 0;
		int 마사인첸 = 7;
		int 고대아이템 = 0;
		if (s.equalsIgnoreCase("A")) {// 0멸마판금
			itemid = 21169;
			고대아이템 = 20095;
		} else if (s.equalsIgnoreCase("B")) {// 1멸마판금
			itemid = 21169;
			마사인첸 = 8;
			고대아이템 = 20095;
		} else if (s.equalsIgnoreCase("C")) {// 2멸마판금
			itemid = 21169;
			마사인첸 = 9;
			고대아이템 = 20095;
		} else if (s.equalsIgnoreCase("D")) {// 3멸마판금
			itemid = 21169;
			마사인첸 = 10;
			고대아이템 = 20095;
		} else if (s.equalsIgnoreCase("E")) {// 0멸마비늘
			itemid = 21170;
			고대아이템 = 20094;
		} else if (s.equalsIgnoreCase("F")) {// 1멸마비늘
			itemid = 21170;
			마사인첸 = 8;
			고대아이템 = 20094;
		} else if (s.equalsIgnoreCase("G")) {// 2멸마비늘
			itemid = 21170;
			마사인첸 = 9;
			고대아이템 = 20094;
		} else if (s.equalsIgnoreCase("H")) {// 3멸마비늘
			itemid = 21170;
			마사인첸 = 10;
			고대아이템 = 20094;
		} else if (s.equalsIgnoreCase("I")) {// 0멸마가죽
			itemid = 21171;
			고대아이템 = 20092;
		} else if (s.equalsIgnoreCase("J")) {// 1멸마가죽
			itemid = 21171;
			마사인첸 = 8;
			고대아이템 = 20092;
		} else if (s.equalsIgnoreCase("K")) {// 2멸마가죽
			itemid = 21171;
			마사인첸 = 9;
			고대아이템 = 20092;
		} else if (s.equalsIgnoreCase("L")) {// 3멸마가죽
			itemid = 21171;
			마사인첸 = 10;
			고대아이템 = 20092;
		} else if (s.equalsIgnoreCase("M")) {// 0멸마로브
			itemid = 21172;
			고대아이템 = 20093;
		} else if (s.equalsIgnoreCase("N")) {// 1멸마로브
			itemid = 21172;
			마사인첸 = 8;
			고대아이템 = 20093;
		} else if (s.equalsIgnoreCase("O")) {// 2멸마로브
			itemid = 21172;
			마사인첸 = 9;
			고대아이템 = 20093;
		} else if (s.equalsIgnoreCase("P")) {// 3멸마로브
			itemid = 21172;
			마사인첸 = 10;
			고대아이템 = 20093;
		}

		boolean ck = false;

		if (고대아이템 != 0) {
			if (pc.getInventory().checkItem(41246, 100000)) {
				L1ItemInstance[] list1 = pc.getInventory().findItemsIdNotEquipped(고대아이템);
				if (list1.length > 0) {
					L1ItemInstance 마사 = null;
					L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(20110);
					for (L1ItemInstance item : list2) {
						if (item.getEnchantLevel() == 마사인첸) {
							마사 = item;
							break;
						}
					}
					if (마사 == null) {
						list2 = pc.getInventory().findItemsIdNotEquipped(1020110);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == 마사인첸) {
								마사 = item;
								break;
							}
						}
					}
					if (마사 != null) {
						pc.getInventory().removeItem(마사);
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
			L1ItemInstance item = pc.getInventory().storeItem(itemid, 1, 마사인첸 - 7);
			pc.sendPackets(new S_ServerMessage(403, item.getName()));
		}
	}

	/** XML이 아닌 소스로 처리할때(재료번호로 처리) **/
	private boolean Action(L1PcInstance pc, int itemid, int 재료번호, int count) {
		boolean ck = false;
		for (; count > 0; count--) {
			switch (itemid) {
			case 8020:  //-- 환생의 보석
			case 8022:  //-- 환생의 보석 주머니
			case 40048:  //-- 고급다이아
			case 40051:  //-- 고급에메랄드
			case 40049:  //-- 고급루비
			case 40050:  //-- 고급사파
			case 40052:  //-- 최고급다이아
			case 40055:  //-- 최고급에메랄드
			case 40053:  //-- 최고급루비
			case 40054:  //-- 최고급사파
			case 6010:  //-- 극한티아라원석
			case 6012:  //-- 극한의샌달원석
			case 437010:  //-- 드래곤의다이아몬드
			case 437009:  //-- 드래곤의보물상자
			{
				디오(pc, itemid);
				ck = true;
			}
			break;
			case 60474: {
				if (재료번호 >= 284 && 재료번호 <= 290) {
					if (pc.getInventory().consumeItem(재료번호, 1)) {
						L1ItemInstance item = pc.getInventory().storeItem(60474, 1, 0);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
			}
				break;
			case 60443: {
				if (재료번호 >= 60427 && 재료번호 <= 60438) {
					if (pc.getInventory().consumeItem(40308, 15) && pc.getInventory().consumeItem(재료번호, 1)) {
						L1ItemInstance item = pc.getInventory().storeItem(60443, 1, 0);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
			}
				break;
			case 60423: {// 용의 호박석
				if (41246 != 재료번호)
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
				빛나는마력의장갑(itemid, pc, 재료번호);
				ck = true;
				break;
			case 7225:// 산적의도끼
				산적의도끼(itemid, pc, 재료번호);
				ck = true;
				break;
			case 7227:// 오우거의도끼
				오우거의도끼(itemid, pc, 재료번호);
				ck = true;
				break;
			case 212551:// 용의호박갑옷
			case 212552:
			case 212553:
			case 212554:
			case 212555:
			case 212556:
			case 212557:
				용의호박갑옷(itemid, pc, 재료번호);
				ck = true;
				break;
			case 60387:// 7환체
				for (int i = 410000; i <= 410001; i++) {
					if (i != 재료번호)
						continue;
					L1ItemInstance 재료 = null;
					if (pc.getInventory().checkItem(40308, 5000000)) {
						L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(i);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == 8) {
								재료 = item;
								break;
							}
						}
					}
					if (재료 != null) {
						pc.getInventory().removeItem(재료);
						pc.getInventory().consumeItem(40308, 5000000);
						L1ItemInstance item = pc.getInventory().storeItem(275, 1, 7);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
				break;
			case 60388:// 8환체
				for (int i = 410000; i <= 410001; i++) {
					if (i != 재료번호)
						continue;
					L1ItemInstance 재료 = null;
					if (pc.getInventory().checkItem(40308, 10000000)) {
						L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(i);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == 9) {
								재료 = item;
								break;
							}
						}
					}
					if (재료 != null) {
						pc.getInventory().removeItem(재료);
						pc.getInventory().consumeItem(40308, 10000000);
						L1ItemInstance item = pc.getInventory().storeItem(275, 1, 8);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
				break;
			case 60389:// 7공키
				for (int i = 410003; i <= 410004; i++) {
					if (i != 재료번호)
						continue;
					L1ItemInstance 재료 = null;
					if (pc.getInventory().checkItem(40308, 5000000)) {
						L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(i);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == 8) {
								재료 = item;
								break;
							}
						}
					}
					if (재료 != null) {
						pc.getInventory().removeItem(재료);
						pc.getInventory().consumeItem(40308, 5000000);
						L1ItemInstance item = pc.getInventory().storeItem(266, 1, 7);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
				break;
			case 60390:// 8공키
				for (int i = 410003; i <= 410004; i++) {
					if (i != 재료번호)
						continue;
					L1ItemInstance 재료 = null;
					if (pc.getInventory().checkItem(40308, 10000000)) {
						L1ItemInstance[] list2 = pc.getInventory().findItemsIdNotEquipped(i);
						for (L1ItemInstance item : list2) {
							if (item.getEnchantLevel() == 9) {
								재료 = item;
								break;
							}
						}
					}
					if (재료 != null) {
						pc.getInventory().removeItem(재료);
						pc.getInventory().consumeItem(40308, 10000000);
						L1ItemInstance item = pc.getInventory().storeItem(266, 1, 8);
						pc.sendPackets(new S_ServerMessage(403, item.getName()));
						break;
					}
				}
				ck = true;
				break;
			case 60041:// 7마단
			case 60048:// 8마단
				/*
				 * 리뉴얼로인해 제작안함 case 60042: case 60043: case 60044: case 60045:
				 * case 60046: case 60047: case 60049: case 60050: case 60051:
				 * case 60052: case 60053: case 60054:
				 */
				조우불골렘(itemid, pc, 재료번호);
				ck = true;
				break;
			case 430106:
			case 430104:
			case 430107:
			case 430105:
			case 430108:
			case 430109:
			case 430110:
				마안(itemid, pc, 재료번호);
				ck = true;
				break;
			case 60126:
			case 60128:
			case 60127:
			case 60129:
				파이파크(itemid, pc, 재료번호);
				ck = true;
				break;
			case 40722:
				금호박(pc, 재료번호);
				ck = true;
				break;
			default:
				return false;
			}
		}
		return ck;
	}

	private void 문장테이블(int code, L1PcInstance pc) {
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
			break; // 성장
		case 4351:
			craftitemid = 490022;
			craftitemen = 3;
			craftmt = 490032;
			craftmtcount = 6;
			break; // 회복
		}
		if (pc.getInventory().consumeItem(craftmt, craftmtcount)) {
			L1ItemInstance t = pc.getInventory().storeItem(craftitemid, 1, craftitemen);
			pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를 //
																		// 넣었습니다.
			commit("문장테이블 : " + t.getName(), "", 1);
		}
	}

	private void 레옹(int code, L1PcInstance pc) {
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
			pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를 //
																		// 넣었습니다.
			commit("레옹 : " + t.getName(), "", 1);
		}
	}

	private void 럭키(int code, L1PcInstance pc) {
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
			pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를 //
																		// 넣었습니다.
			commit("럭키 : " + t.getName(), "", 1);
		}
	}

	private boolean 인첸트지급(L1PcInstance pc, int item_id, int count, int EnchantLevel) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(true);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else {
				pc.sendPackets(new S_ServerMessage(82));// 무게 게이지가 부족하거나 인벤토리가 꽉차서 더 들 수 없습니다.
				return false;
			}
			pc.sendPackets(new S_SystemMessage("아이템 제작에 성공했습니다."));
			pc.sendPackets(new S_ServerMessage(143, item.getLogName())); // %0를 손에 넣었습니다.
			pc.sendPackets(new S_SkillSound(pc.getId(), 7976));
			pc.broadcastPacket(new S_SkillSound(pc.getId(), 7976));
			return true;
		} else {
			return false;
		}
	}
	/** 환생의보석**/ 
	private void 환생보석(int code, L1PcInstance pc) {
		if (pc.getInventory().checkItem(40052, 1) && pc.getInventory().checkItem(40055, 1)
				&&pc.getInventory().checkItem(40053, 1) && pc.getInventory().checkItem(40054, 1)	
				&&pc.getInventory().checkItem(410061, 1)|| pc.getInventory().checkItem(500020, 1)
				){
			인첸트지급(pc, 31096, 1, 0);	
			pc.getInventory().consumeItem(40052, 1);
			pc.getInventory().consumeItem(40055, 1);
			pc.getInventory().consumeItem(40053, 1);
			pc.getInventory().consumeItem(40054, 1);
			pc.getInventory().consumeItem(410061, 1);
			pc.getInventory().consumeItem(500020, 1);
		}
	}
	/** itemcode 를 XML action값으로 처리할때 **/
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

	private void 산적의도끼(int itemid, L1PcInstance pc, int 재료아이템) {
		int enc = 9;
		// 9질풍 봉인된산적의도끼

		// System.out.println(재료아이템);
		if (pc.getInventory().checkItem(7336)// 봉인된산적의도끼
				|| pc.getInventory().checkItem(7228)// 질풍의도끼
		) {
			for (L1ItemInstance item : pc.getInventory().findItemsId(7228)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					pc.getInventory().consumeItem(7336, 1);// 봉인된산적의도끼
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																				// 손에
																				// 넣었습니다.
					return;
				}
			}
		}
	}

	private void 오우거의도끼(int itemid, L1PcInstance pc, int 재료아이템) {
		int enc = 9;
		// 9산적 봉인된오우거의도끼 오피5 아덴100만
		int val = 1000000;

		// System.out.println(재료아이템);
		if (pc.getInventory().checkItem(7335)// 봉인된오우거의도끼
				|| pc.getInventory().checkItem(7225)// 산적
				|| pc.getInventory().checkItem(40513, 5) // 오우거의눈물
				|| pc.getInventory().checkItem(40308, val) // 아데나100만
		) {
			for (L1ItemInstance item : pc.getInventory().findItemsId(7225)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					pc.getInventory().consumeItem(7335, 1);// 봉인된산적의도끼
					pc.getInventory().consumeItem(40513, 5);// 오우거의눈물5
					pc.getInventory().consumeItem(40308, val);// 아덴100만
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																				// 손에
																				// 넣었습니다.
					return;
				}
			}
		}
	}

	Random _Random = new Random(System.nanoTime());

	private void 질풍의도끼(int code, L1PcInstance pc) {
		int 재료인챈 = 0;
		int 생성인챈 = 0;
		if (code == 1153) {
			재료인챈 = 8;
			생성인챈 = 0;
		} else if (code == 1154) {
			재료인챈 = 9;
			생성인챈 = 8;
		}
		if (pc.getInventory().checkItem(412005)// 광풍의도끼
				|| pc.getInventory().checkItem(41246, 100000)) { // 결정체10만
			for (L1ItemInstance item : pc.getInventory().findItemsId(412005)) {
				if (item.getEnchantLevel() == 재료인챈 && !item.isEquipped()) {
					pc.getInventory().consumeItem(41246, 100000);// 아덴100만
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(7228, 1, 생성인챈);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																				// 손에
																				// 넣었습니다.
					commit("조우의 불골렘 : " + t.getName(), "", 1);
					return;
				}
			}
		}
	}

	private void 스냅퍼(int code, L1PcInstance pc) {
		int 인챈 = 0;
		int 재료 = 0;
		if (code >= 1970 && code <= 1975) {// 체력
			재료 = 21248;
			인챈 = code - 1967;
		} else if (code >= 1976 && code <= 1981) {// 마법저항
			재료 = 21247;
			인챈 = code - 1973;
			/*
			 * }else if(code >= 1982 && code <= 1987){//집중 재료 = 525110; 인챈 =
			 * code - 1979; }else if(code >= 1988 && code <= 1993){//마나 재료 =
			 * 525112; 인챈 = code - 1985; }else if(code >= 1994 && code <=
			 * 1999){//회복 재료 = 525109; 인챈 = code - 1991;
			 */
		} else if (code >= 2000 && code <= 2005) {// 지혜
			재료 = 21246;
			인챈 = code - 1997;
		} else if (code >= 2006 && code <= 2011) {// 용사
			재료 = 21249;
			인챈 = code - 2003;
		}
		L1ItemInstance 반지1 = null;
		L1ItemInstance 반지2 = null;
		int count = 0;
		if (pc.getInventory().checkItem(재료, 2)) { // 재료 체크
			for (L1ItemInstance item : pc.getInventory().findItemsId(재료)) {
				if (item.getEnchantLevel() == 인챈 && !item.isEquipped()) {
					count++;
					if (count == 1) {
						반지1 = item;
					}
					if (count == 2) {
						반지2 = item;
						break;
					}
				}
			}
			if (반지1 != null && 반지2 != null) {
				pc.getInventory().deleteItem(반지1);
				pc.getInventory().deleteItem(반지2);
				L1ItemInstance t = pc.getInventory().storeItem(재료 + 4, 1, 인챈);
				pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																			// 손에
																			// 넣었습니다.
				commit("룸티스의 수정구 (스냅퍼): " + t.getName(), "", 1);
			}
		}
	}

	private void 룸티스(int code, L1PcInstance pc) {
		int 인챈 = 0;
		int 재료 = 0;
		if (code >= 1952 && code <= 1957) {
			재료 = 500007;
			인챈 = code - 1949;
		} else if (code >= 1958 && code <= 1963) {
			재료 = 500008;
			인챈 = code - 1955;
		} else if (code >= 1964 && code <= 1969) {
			재료 = 500009;
			인챈 = code - 1961;
		} else if (code >= 3203 && code <= 3208) {
			재료 = 500010;
			인챈 = code - 3200;
		}

		L1ItemInstance 귀걸이1 = null;
		L1ItemInstance 귀걸이2 = null;
		int count = 0;
		if (pc.getInventory().checkItem(재료, 2)) { // 재료 체크
			for (L1ItemInstance item : pc.getInventory().findItemsId(재료)) {
				if (item.getEnchantLevel() == 인챈 && !item.isEquipped()) {
					count++;
					if (count == 1) {
						귀걸이1 = item;
					}
					if (count == 2) {
						귀걸이2 = item;
						break;
					}
				}
			}
			if (귀걸이1 != null && 귀걸이2 != null) {
				pc.getInventory().deleteItem(귀걸이1);
				pc.getInventory().deleteItem(귀걸이2);
				L1ItemInstance t = pc.getInventory().storeItem(재료 + 2000, 1, 인챈);
				pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																			// 손에
																			// 넣었습니다.
				commit("룸티스의 수정구 : " + t.getName(), "", 1);
			}
		}
	}

	private void 마물의도끼(int code, L1PcInstance pc) {
		int 재료인챈 = 0;
		int 생성인챈 = 0;
		if (code == 1155) {
			재료인챈 = 0;
			생성인챈 = 0;
		} else if (code == 1156) {
			재료인챈 = 3;
			생성인챈 = 1;
		} else if (code == 1157) {
			재료인챈 = 5;
			생성인챈 = 3;
		}
		if (pc.getInventory().checkItem(151)// 데몬엑스
				|| pc.getInventory().checkItem(41246, 200000)) { // 결정체20만
			for (L1ItemInstance item : pc.getInventory().findItemsId(151)) {
				if (item.getEnchantLevel() == 재료인챈 && !item.isEquipped()) {
					pc.getInventory().consumeItem(41246, 200000);
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(7226, 1, 생성인챈);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																				// 손에
																				// 넣었습니다.
					commit("조우의 불골렘 : " + t.getName(), "", 1);
					return;
				}
			}
		}
	}

	

	private void 신성한마투세끼6(int code, L1PcInstance pc) {
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
		L1ItemInstance 마투1 = null;
		L1ItemInstance 마투2 = null;
		if (pc.getInventory().checkItem(20011) || pc.getInventory().checkItem(120011)) {
			for (L1ItemInstance item : pc.getInventory().findItemsIds(20011, 120011)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					count++;
					if (count == 1) {
						마투1 = item;
					}
					if (count == 2) {
						마투2 = item;
						break;
					}
				}
			}
			if (마투1 != null && 마투2 != null) {
				pc.getInventory().deleteItem(마투1);
				pc.getInventory().deleteItem(마투2);
				int ran = _Random.nextInt(100);
				if (ran < 5) {// 대성공 확률
					int hh = enc - 1;

					L1ItemInstance t = pc.getInventory().storeItem(220011, 1, enc - 1);
					t.setBless(0);
					pc.getInventory().updateItem(t, L1PcInventory.COL_BLESS);
					pc.getInventory().saveItem(t, L1PcInventory.COL_BLESS);
					L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
							pc.getName() + "님이 축복받은 +" + hh + " " + t.getName() + " 제작에 성공하였습니다."));
					pc.sendPackets(new S_ServerMessage(143, "$10954", "$10947"), true);
					commit("대성공 제작 테이블^무기/방어구(금속) : " + t.getName(), "", 1);
				} else {
					L1ItemInstance t = pc.getInventory().storeItem(220011, 1, enc - 1);
					pc.sendPackets(new S_ServerMessage(143, "$10954", "$10947"), true);
					commit("제작 테이블^무기/방어구(금속) : " + t.getName(), "", 1);
				}

			}
		}
	}

	private void 빛나는마력의장갑(int itemid, L1PcInstance pc, int 재료아이템) {
		int enc = 7;

		// System.out.println(재료아이템);
		if (pc.getInventory().checkItem(7245) || pc.getInventory().checkItem(40395)
				|| pc.getInventory().checkItem(149027, 10) || pc.getInventory().checkItem(7244, 300)
				|| pc.getInventory().checkItem(7248)) {
			for (L1ItemInstance item : pc.getInventory().findItemsId(7245)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					pc.getInventory().consumeItem(7248, 1);// 마력의핵
					pc.getInventory().consumeItem(7244, 300);// 마력의실타래
					pc.getInventory().consumeItem(149027, 10);// 마물의기운
					pc.getInventory().consumeItem(40395, 1);// 수룡비늘

					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																				// 손에
																				// 넣었습니다.
					return;
				}
			}
		}
	}

	private static int[] 금호박재료 = new int[] { 90085, 90086, 90087, 90088, 90089, 90090, 90091, 90092 };

	private void 금호박(L1PcInstance pc, int 재료아이템) {
		int enc = 10;
		switch (재료아이템) {
		case 15776:
			재료아이템 = 90085;
			break;// 양손검
		case 15777:
			재료아이템 = 90086;
			break;// 지팡이
		case 15778:
			재료아이템 = 90087;
			break;// 한손검
		case 15779:
			재료아이템 = 90088;
			break;// 활
		case 15780:
			재료아이템 = 90089;
			break;// 이도류
		case 15781:
			재료아이템 = 90090;
			break;// 체인소드
		case 15782:
			재료아이템 = 90091;
			break;// 키링크
		case 15783:
			재료아이템 = 90092;
			break;// 도끼
		default:
			break;
		}
		// System.out.println(재료아이템);
		for (int f : 금호박재료) {
			if (f != 재료아이템)
				continue;
			for (L1ItemInstance item : pc.getInventory().findItemsId(f)) {
				if (item.getEnchantLevel() == enc && !item.isEquipped()) {
					pc.getInventory().deleteItem(item);
					L1ItemInstance t = pc.getInventory().storeItem(40722, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																				// 손에
																				// 넣었습니다.
					return;
				}
			}
		}
	}

	


	private static int[] meterielWeapon2 = new int[] { 81, 162, 177, 194, 13 };

	private void 파이파크(int itemid, L1PcInstance pc, int 재료아이템) {
		int enc = 8;
		int value = 5000000;
		if (itemid == 60127 || itemid == 60129) {
			enc = 9;
			value = 10000000;
		}
		// System.out.println(재료아이템);
		if (pc.getInventory().checkItem(40308, value)) {
			for (int f : meterielWeapon2) {
				if (f != 재료아이템)
					continue;
				for (L1ItemInstance item : pc.getInventory().findItemsId(f)) {
					if (item.getEnchantLevel() == enc && !item.isEquipped()) {
						pc.getInventory().consumeItem(40308, value);
						pc.getInventory().deleteItem(item);
						L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
						pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																					// 손에
																					// 넣었습니다.
						return;
					}
				}
			}
		}
	}

	private static int[] 용호갑재료 = new int[] { 21124, 427306, 256, 4500027, 263, 4500026, 265, 264, 60423 };

	private void 용의호박갑옷(int itemid, L1PcInstance pc, int 재료번호) {
		
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
			if (재료번호 != 256 && 재료번호 != 4500027 && 재료번호 != 263 && 재료번호 != 4500026 && 재료번호 != 265 && 재료번호 != 264)
				return;
		} else if (itemid == 212553 || itemid == 212555 || itemid == 212557) {
			if (재료번호 != 21124 && 재료번호 != 427306)
				return;
		}
		if (재료번호 == 21124 || 재료번호 == 427306 || 재료번호 == 60423)
			ck = true;

		if (ck || pc.getInventory().checkItem(40308, 150000)) {
			for (int f : 용호갑재료) {
				if (f != 재료번호)
					continue;
				for (L1ItemInstance item : pc.getInventory().findItemsId(f)) {
					if (item.getEnchantLevel() == enc && !item.isEquipped()) {
						if (!ck)
							pc.getInventory().consumeItem(40308, 150000);
						if (재료번호 == 60423) {
							if (!pc.getInventory().consumeItem(60423, 300))
								return;
						} else
							pc.getInventory().deleteItem(item);
						L1ItemInstance t = pc.getInventory().storeItem(21255, 1, store_enc);
						pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																					// 손에
																					// 넣었습니다.
						return;
					}
				}
			}
		}
	}

	private static int[] 담금질아이템 = new int[] { 5, 6, 32, 37, 42, 41, 52, 64, 99, 104, 125, 129, 145, 148, 180, 181,
			131 };

	private void 조우불골렘(int itemid, L1PcInstance pc, int 재료아이템) {
		int enc = 8;
		int value = 5000000;
		if (itemid >= 60048 && itemid <= 60054) {
			enc = 9;
			value = 10000000;
		}
		// System.out.println(재료아이템);
		if (pc.getInventory().checkItem(40308, value)) {
			for (int f : 담금질아이템) {
				if (f != 재료아이템)
					continue;

				for (L1ItemInstance item : pc.getInventory().findItemsId(f)) {
					if (item.getEnchantLevel() == enc && !item.isEquipped()) {
						pc.getInventory().consumeItem(40308, value);
						pc.getInventory().deleteItem(item);
						L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
						pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																					// 손에
																					// 넣었습니다.
						return;
					}
				}
			}
		}
	}

	public static int[] stat = new int[] { 0, 0, 0, 0, 0 };

	private void 마안(int itemid, L1PcInstance pc, int 재료아이템) {
		int adena = 100000;
		int count = 1;
		int 재료아이템2 = 0;
		if (itemid >= 430108 && itemid <= 430110) {
			adena = 200000;
		}
		if (재료아이템 >= 5000063 && 재료아이템 <= 5000066) {
			count = 32;
		}
		if (itemid == 430106 && 재료아이템 != 430102 && 재료아이템 != 5000064) {
			return;
		} else if (itemid == 430104 && 재료아이템 != 430100 && 재료아이템 != 5000066) {
			return;
		} else if (itemid == 430105 && 재료아이템 != 430101 && 재료아이템 != 5000065) {
			return;
		} else if (itemid == 430107 && 재료아이템 != 430103 && 재료아이템 != 5000063) {
			return;
		} else if (itemid == 430108) {
			재료아이템 = 430106;
			재료아이템2 = 430104;
		} else if (itemid == 430109) {
			재료아이템 = 430108;
			재료아이템2 = 430105;
		} else if (itemid == 430110) {
			재료아이템 = 430109;
			재료아이템2 = 430107;
		}

		if (pc.getInventory().checkItem(40308, adena)) {
			if (재료아이템2 == 0 || pc.getInventory().checkItem(재료아이템2, 1)) {
				if (pc.getInventory().consumeItem(재료아이템, count)) {
					pc.getInventory().consumeItem(40308, adena);
					if (재료아이템2 != 0)
						pc.getInventory().consumeItem(재료아이템2, 1);
					L1ItemInstance t = pc.getInventory().storeItem(itemid, 1);
					pc.sendPackets(new S_ServerMessage(403, t.getLogName())); // %0를
																				// 손에
																				// 넣었습니다.
				}
			}
		}
	}
	private void 디오(L1PcInstance pc, int itemid) {
		if (itemid == 8020) { // -- 환생의 보석
			pc.getInventory().consumeItem(40052, 1);
			pc.getInventory().consumeItem(40055, 1);
			pc.getInventory().consumeItem(40053, 1);
			pc.getInventory().consumeItem(40054, 1);
			pc.getInventory().consumeItem(149027, 1);

		} else if (itemid == 8022) { // -- 환생의 보석 주머니
			pc.getInventory().consumeItem(8020, 1);

		} else if (itemid == 40048) { // -- 고급다이아
			pc.getInventory().consumeItem(40044, 3);
			pc.getInventory().consumeItem(40308, 250);

		} else if (itemid == 40051) { // -- 고급에메랄드
			pc.getInventory().consumeItem(40047, 3);
			pc.getInventory().consumeItem(40308, 250);

		} else if (itemid == 40049) { // -- 고급루비
			pc.getInventory().consumeItem(40045, 3);
			pc.getInventory().consumeItem(40308, 250);

		} else if (itemid == 40050) { // -- 고급사파
			pc.getInventory().consumeItem(40045, 3);
			pc.getInventory().consumeItem(40308, 250);

		} else if (itemid == 40052) { // -- 최고급다이아
			pc.getInventory().consumeItem(40048, 3);
			pc.getInventory().consumeItem(40308, 2500);

		} else if (itemid == 40055) { // -- 최고급에메랄드
			pc.getInventory().consumeItem(40051, 3);
			pc.getInventory().consumeItem(40308, 2500);

		} else if (itemid == 40053) { // -- 최고급루비
			pc.getInventory().consumeItem(40049, 3);
			pc.getInventory().consumeItem(40308, 2500);

		} else if (itemid == 40050) { // -- 최고급사파
			pc.getInventory().consumeItem(40049, 3);
			pc.getInventory().consumeItem(40308, 2500);

		} else if (itemid == 6010) { // -- 극한티아라원석
			pc.getInventory().consumeItem(6002, 1);

		} else if (itemid == 6012) { // -- 극한샌달원석
			pc.getInventory().consumeItem(6006, 1);

		} else if (itemid == 437010) { // -- 드래곤의다이아몬드
			pc.getInventory().consumeItem(5000067, 1);

		} else if (itemid == 437009) { // -- 드래곤의보물상자
			pc.getInventory().consumeItem(5000068, 1);
		}

		L1ItemInstance t = pc.getInventory().storeItem(itemid, 1,0);
		pc.sendPackets(new S_ServerMessage(403, t.getLogName()));
	}
	/** 변경 가능한지 검사한다 시작 **/
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

				client.sendPacket(new S_NewCreateItem(S_NewCreateItem_list.제작패킷(i)), true);
				i++;
				if (i > 596) {
					// if(i > 347){
					client.제작템패킷전송중 = false;
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

			String data = S_DollAlchemyInfo.제작패킷(i++);

			if (data == null) {
				client.인형합성패킷전송중 = false;
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
