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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.TimeZone;

import l1j.server.Config;
import l1j.server.INN_IND;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.datatables.AuctionBoardTable;
import l1j.server.server.datatables.FurnitureSpawnTable;
import l1j.server.server.datatables.HouseTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.NpcActionTable;
import l1j.server.server.model.L1HouseLocation;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FurnitureInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.npc.L1NpcHtml;
import l1j.server.server.model.npc.action.L1NpcAction;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.storage.CharactersItemStorage;
import l1j.server.server.templates.L1AuctionBoard;
import l1j.server.server.templates.L1House;
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket, C_Amount

public class C_Amount extends ClientBasePacket {

	private static final String C_AMOUNT = "[C] C_Amount";

	public C_Amount(byte[] decrypt, LineageClient client) throws Exception {
		super(decrypt);
		try {
			int objectId = readD();
			int amount = readD();

			// ����Ʈ ��� �Խ��� ���� ����
			long _amount = amount;
			if (_amount < 0) {
				return;
			}

			@SuppressWarnings("unused")
			int c = readC();
			String s = readS();

			L1PcInstance pc = client.getActiveChar();
			L1NpcInstance npc = (L1NpcInstance) L1World.getInstance().findObject(objectId);
			String s1 = "";
			String s2 = "";

			// ����Ʈ ��� �Խ��� ���� ����
			if (!pc.getInventory().checkItem(40308)) {
				return;
			} // ������Ʈ �ͼ��� �ذ�

			if (npc == null) {
				return;
			}

			try {
				StringTokenizer stringtokenizer = new StringTokenizer(s);
				s1 = stringtokenizer.nextToken();
				s2 = stringtokenizer.nextToken();
				stringtokenizer = null;
			} catch (NoSuchElementException e) {
				s1 = "";
				s2 = "";
			}
			if (!s1.equalsIgnoreCase("agsell") && pc.getInventory().findItemId(40308).getCount() < _amount) {
				return;
			}

			if (s.equalsIgnoreCase("inn2")) { // �� �뿩
				Config._INN_Q.requestWork(new INN_IND(pc.getName(), objectId, npc.getNpcId(), 0, amount));
				pc.sendPackets(new S_SystemMessage("�� ���� Ž�����Դϴ� ��ø� ��ٷ��ּ���."), true);

			} else if (s.equalsIgnoreCase("inn12")) { // Ȧ �뿩
				Config._INN_Q.requestWork(new INN_IND(pc.getName(), objectId, npc.getNpcId(), 1, amount));
				pc.sendPackets(new S_SystemMessage("�� Ȧ�� Ž�����Դϴ� ��ø� ��ٷ��ּ���."), true);
			} else if (s1.equalsIgnoreCase("agapply")) { // ��ſ� �������� ���
				String pcName = pc.getName();
				AuctionBoardTable boardTable = new AuctionBoardTable();
				for (L1AuctionBoard board : boardTable.getAuctionBoardTableList()) {
					if (pcName.equalsIgnoreCase(board.getBidder())) {
						pc.sendPackets(new S_SystemMessage("�̹� �ٸ� �� ��ſ� �����ϼ̽��ϴ�."), true);
						return;
					}
				}

				int houseId = Integer.valueOf(s2);
				L1AuctionBoard board = boardTable.getAuctionBoardTable(houseId);
				if (board != null) {
					int nowPrice = board.getPrice();
					long _nowPrice = nowPrice;

					if (_nowPrice <= 0) {
						return;
					}
					if (pc.getInventory().findItemId(40308).getCount() < _nowPrice) {
						return;
					}

					int nowBidderId = board.getBidderId();
					if (pc.getInventory().consumeItem(L1ItemId.ADENA, amount)) {
						// ��� �Խ����� ����
						board.setPrice(amount);
						board.setBidder(pcName);
						board.setBidderId(pc.getId());
						boardTable.updateAuctionBoard(board);
						if (nowBidderId != 0) {
							// �����ڿ��� �Ƶ����� ȯ��
							L1PcInstance bidPc = (L1PcInstance) L1World.getInstance().findObject(nowBidderId);
							if (bidPc != null) { // �¶�����
								bidPc.getInventory().storeItem(L1ItemId.ADENA, nowPrice);
								// ����� ���õ� �ݾ׺��� �� �� ��� �ݾ��� ������ (��)���� ��Ÿ���� ������,
								// ������������ ������ �����߽��ϴ�. %n
								// ����� ��ſ� �ñ�%0�Ƶ����� ����մϴ�. %n �����մϴ�. %n%n
								bidPc.sendPackets(new S_ServerMessage(525, String.valueOf(nowPrice)), true);
							} else { // ���� ������
								L1PcInstance temppc = loadCharacter(nowBidderId);
								if (temppc == null)
									return;
								L1ItemInstance item = ItemTable.getInstance().createItem(L1ItemId.ADENA);
								item.setCount(nowPrice);
								CharactersItemStorage storage = CharactersItemStorage.create();
								storage.storeItem(temppc, item);
							}
						}
					} else {
						pc.sendPackets(new S_SystemMessage("�Ƶ����� ���ġ �ʽ��ϴ�."), true); // \f1�Ƶ�����
																						// �����մϴ�.
					}
				}
			} else if (s1.equalsIgnoreCase("agsell")) { // ���� �Ⱦ��� ���
				int houseId = Integer.valueOf(s2);
				AuctionBoardTable boardTable = new AuctionBoardTable();
				L1AuctionBoard board = new L1AuctionBoard();
				if (board != null) {
					// ��� �Խ��ǿ� �ű� ����
					board.setHouseId(houseId);
					L1House house = HouseTable.getInstance().getHouseTable(houseId);
					board.setHouseName(house.getHouseName());
					board.setHouseArea(house.getHouseArea());
					TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
					Calendar cal = Calendar.getInstance(tz);
					cal.add(Calendar.DATE, 5); // 5�� ��
					cal.set(Calendar.MINUTE, 0); // �� , �ʴ� �߶� ����
					cal.set(Calendar.SECOND, 0);
					board.setDeadline(cal);
					board.setPrice(amount);
					board.setLocation(house.getLocation());
					board.setOldOwner(pc.getName());
					board.setOldOwnerId(pc.getId());
					board.setBidder("");
					board.setBidderId(0);
					boardTable.insertAuctionBoard(board);

					int keeperId = house.getKeeperId();
					if (npc.getNpcTemplate().get_npcId() == keeperId) {
						for (L1FurnitureInstance furn : L1World.getInstance().getAllFurniture()) {
							if (L1HouseLocation.isInHouseLoc(houseId, furn.getX(), furn.getY(), furn.getMapId())) {
								furn.deleteMe();
								FurnitureSpawnTable.getInstance().deleteFurniture(furn);
							}
						}
					}

					house.setOnSale(true); // ��������� ����
					house.setPurchaseBasement(true); // ���� ����Ʈ�̱������� ����
					HouseTable.getInstance().updateHouse(house); // DB�� ������
				}
			} else {
				L1NpcAction action = NpcActionTable.getInstance().get(s, pc, npc);
				if (action != null) {
					L1NpcHtml result = action.executeWithAmount(s, pc, npc, amount);
					if (result != null) {
						pc.sendPackets(new S_NPCTalkReturn(npc.getId(), result), true);
					}
					return;
				}
			}
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	public L1PcInstance loadCharacter(int id) {
		L1PcInstance pc = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM characters WHERE objid=?");
			pstm.setInt(1, id);

			rs = pstm.executeQuery();

			if (!rs.next()) {
				// SELECT�� ����� �������� �ʾҴ�.
				return null;
			}
			pc = new L1PcInstance();
			pc.setAccountName(rs.getString("account_name"));
			pc.setId(id);
			pc.setName(rs.getString("char_name"));

		} catch (SQLException e) {
			return null;
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return pc;
	}

	@Override
	public String getType() {
		return C_AMOUNT;
	}
}
