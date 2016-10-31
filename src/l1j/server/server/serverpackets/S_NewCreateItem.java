package l1j.server.server.serverpackets;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javolution.util.FastTable;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.server.Opcodes;
import l1j.server.server.datatables.NpcActionTable;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.npc.action.L1NpcAction;
import l1j.server.server.model.npc.action.L1NpcMakeItemAction;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.templates.L1UserRanking;
import l1j.server.server.utils.CalcStat;
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;

public class S_NewCreateItem extends ServerBasePacket {

	private static final String S_NEWCREATEITEM = "[S] S_NewCreateItem";
	private byte[] _byte = null;

	public static final int WINDOW = 0x37;
	public static final int LIST = 0x39;
	public static final int FISH_WINDOW = 0x3F;
	public static final int EMOTICON = 0x40;

	public static final int unk1 = 0x41;

	public static final int CLAN_JOIN_MESSAGE = 0x43;
	public static final int CASTLE_WAR_TIME_END = 0x44;// ���� �̻��
	public static final int CLAN_JOIN_WAIT = 0x45;
	public static final int CASTLE_WAR_TIME = 0x4C;
	public static final int TAM_POINT = 0x01C2;
	public static final int CLAN_JOIN_SETTING = 0x4D;

	public static final int �뼺������ = 0x5D;

	public static final int unknown1 = 0x4E;
	public static final int TamPage = 0xCD;
	public static final int ����г�Ƽ = 0xCF;
	public static final int ����â = 0x6e;
	public static final int ������ŷ = 0x88;
	public static final int ��������Ʈ��ŷ = 0x89;
	public static final int �丶ȣũ_��Ʈ = 0x93;
	public static final int �ű���Ŷ13 = 0x07;

	public static final int ����Ʈ��ȭ = 11;
	public static final int ����Ʈ = 13;
	public static final int ����Ʈ2 = 9;
	public static final int ����Ʈ3 = 62;
	public static final int ����Ʈ4 = 6;

	/** �α�� ���� ��Ŷ�� **/

	public static final int �ű���Ŷ2 = 32;

	public static final int �ű���Ŷ3 = 227;
	public static final int �ű���Ŷ4 = 229;
	public static final int �ű���Ŷ5 = 231;
	public static final int �ű���Ŷ6 = 233;
	public static final int �ű���Ŷ7 = 234;
	public static final int �ű���Ŷ8 = 47;
	public static final int ���� = 0x30;
	public static final int �ű���Ŷ10 = 126;

	public static final int �ű���Ŷ11 = 118;
	public static final int �ű���Ŷ12 = 119;

	public S_NewCreateItem(int type) {
		buildPacket(type);
	}

	public S_NewCreateItem(int type, boolean ck) {
		buildPacket(type, ck);
	}

	private void buildPacket(int type, boolean ck) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(type);
		switch (type) {

		case �丶ȣũ_��Ʈ:
			// 0000: 06 93 01 08 84 c7 bf 60 27 61 .......`'a
			writeC(1);
			writeC(8);
			writeC(0x84);
			writeC(0xc7);
			writeC(0xbf);
			writeC(0x60);
			writeH(0);
			break;
		case ����â:
			// 0000: 06 6e 00 08 01 10 ba 04 38 b9 .n......8.
			// 0000: 06 6e 00 08 03 10 ba 04 7a 31 .n......z1
			writeC(0);
			writeC(8);
			if (ck) {
				writeC(1);
			} else {
				writeC(3);
			}
			writeC(0x10);
			writeC(0xba);
			writeC(0x04);
			writeH(0);
			break;
		case CLAN_JOIN_WAIT:
			writeC(1);
			writeC(8);
			writeC(2);
			writeH(0);
			break;
		case ����г�Ƽ://
			if (ck) {
				writeC(1);
				writeC(8);
				writeC(0x80);
				writeC(1);
				writeC(0x10);
				writeC(00);
				writeC(0x18);
				writeC(0);
				writeH(0);
			} else {
				writeC(1);
				writeC(8);
				writeC(0);
				writeC(0x10);
				writeC(0);
				writeC(0x18);
				writeC(0);
				writeH(0);
			}
			break;
		}
	}

	private void buildPacket(int type) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(type);

		switch (type) {
		case 0x91:// ��
			writeC(01);
			writeC(0x88);
			writeC(0xd4);
			break;
		case unk1:
			writeC(1);
			writeC(8);
			writeC(0x80);
			writeC(0xe1);
			writeC(1);
			writeC(0x10);
			writeC(0xe5);
			writeC(0xe0);
			writeC(1);
			writeC(0x4a);
			writeC(0);
			break;
		case unknown1:
			writeC(1);
			writeC(8);
			writeC(3);
			writeC(0x10);
			writeC(0);
			writeC(0x18);
			writeC(0);
			writeH(0);
			break;

		case WINDOW:
			writeC(0x00);
			writeH(0x0308);
			// writeH(0x4356);
			writeH(0x9D29);
			break;
		case CLAN_JOIN_WAIT:
			writeC(0x01);
			writeH(0x0208);
			int size = 1;

			if (size > 0) {
				Collection<L1PcInstance> list = L1World.getInstance().getAllPlayers();
				int i = 0;
				for (L1PcInstance pc : list) {
					writeC(0x12);
					if (i == 0)
						writeC(39);
					else if (i == 1)
						writeC(38);
					else if (i == 2)
						writeC(40);
					else
						writeC(39);
					/*
					 * else if(i == 3) writeC(39); else if(i == 4) writeC(38);
					 * else if(i == 5) writeC(41);
					 */
					writeC(0x08);
					writeD(0x1203A9A2);
					writeD(0xC5C3B208);
					writeD(0xB7ACC5EB);
					writeH(0x18B4);

					// byteWrite(pc.getId());
					// if(i == 0 || i == 3 || i == 4 || i >= 6){
					if (i == 0 || i >= 3) {
						writeD(0x02D8D1BE);
					} else {
						writeC(0xC3);
						writeH(0x5C8A);
					}
					writeC(0x22);
					byte[] name = pc.getName().getBytes();
					writeC(name.length);
					writeByte(name);
					writeC(0x2A);
					writeC("1".getBytes().length);// name.length);
					writeByte("1".getBytes());
					// byte[] memo = pc.getTitle().getBytes();//�ӽ÷� ȣĪ
					// writeC(memo.length);
					// writeByte(memo);
					writeC(0x30);
					writeC(L1World.getInstance().getPlayer(pc.getName()) != null ? 1 : 0);// ������
					writeC(0x38);
					writeC(pc.getType());// Ŭ����
					i++;
					if (i == 2)
						break;
				}
			}
			writeH(0x00);
			break;
		}
	}

	public S_NewCreateItem(int type, int subtype) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(type);
		switch (type) {
		case CLAN_JOIN_MESSAGE:
			writeH(0x0801);
			writeC(subtype);
			writeH(0x00);
			break;
		default:
			break;
		}
	}

	public S_NewCreateItem(int type, L1UserRanking rank) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case ��������Ʈ��ŷ:
			int star = UserRankingController.getInstance().getStarCount(rank.getName());
			byte[] name = rank.getName().getBytes();
			int length = 11 + byteWriteCount(rank.getCurRank()) + byteWriteCount(rank.getOldRank())
					+ byteWriteCount(rank.getClassId()) + name.length;
			writeC(0x0A);
			writeC(length);
			writeC(0x08);
			writeC(star);
			writeC(0x10);
			byteWrite(rank.getCurRank());
			writeC(0x18);
			byteWrite(rank.getOldRank());
			writeC(0x20);
			byteWrite(rank.getClassId());
			writeC(0x2A);
			writeC(name.length);
			writeByte(name);
			writeC(0x30);
			writeC(0x00);
			writeC(0x38);
			writeC(0x00);

			L1UserRanking classRank = UserRankingController.getInstance().getClassRank(rank.getClassId(),
					rank.getName());

			length = 7 + byteWriteCount(classRank.getCurRank()) + byteWriteCount(classRank.getOldRank())
					+ byteWriteCount(rank.getClassId()) + name.length;
			writeC(0x12);
			writeC(length);
			writeC(0x08);
			writeC(star);
			writeC(0x10);
			byteWrite(classRank.getCurRank());
			writeC(0x18);
			byteWrite(classRank.getOldRank());
			writeC(0x20);
			byteWrite(rank.getClassId());
			writeC(0x2A);
			writeC(name.length);
			writeByte(name);

			if (rank.getCurRank() != 1) {
				writeC(0x18);
				writeC(0x01);
			}
			writeC(0x20);
			writeC(0x01);
			writeC(0x30);
			writeC(0x01);
			writeH(0x00);
			break;
		}
	}

	public S_NewCreateItem(int type, List<L1UserRanking> list, int classId, int totalPage, int curPage) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case ������ŷ:
			writeH(0x08);
			writeC(0x10);
			byteWrite(System.currentTimeMillis() / 2000);
			writeC(0x18);
			writeC(classId);
			writeC(0x20);
			writeC(totalPage);
			writeC(0x28);
			writeC(curPage);

			for (L1UserRanking user : list) {
				byte[] name = user.getName().getBytes();
				int rank = user.getCurRank();
				int oldRank = user.getOldRank();

				int buffCount = 0;

				if (classId == 8) {
					if (rank >= 1 && rank <= 10)
						buffCount = 4;
					else if (rank >= 11 && rank <= 30)
						buffCount = 3;
					else if (rank >= 31 && rank <= 60)
						buffCount = 2;
					else if (rank >= 61 && rank <= 100)
						buffCount = 1;
				} else {
					buffCount = UserRankingController.getInstance().getStarCount(user.getName());
				}

				int length = 8 + name.length + byteWriteCount(rank) + byteWriteCount(oldRank);

				writeC(0x32);
				byteWrite(length);

				writeC(0x08);
				writeC(buffCount);
				writeC(0x10);
				byteWrite(rank);
				writeC(0x18);
				byteWrite(oldRank);
				writeC(0x20);
				writeC(user.getClassId());
				writeC(0x2A);
				writeC(name.length);
				writeByte(name);
			}
			writeH(0x00);
			break;
		}
	}

	String TamP = "";
	String tampage = "cd " + "01 0a 1c 08 0e 10 b8 f9 1a 18 00 "// ������
			+ "20 00 2a " + "0a "// �̸�����
			+ "c6 c4 c7 c1 b8 ae bf c2 c5 b3 "// �̸�
			+ "30 " + "30 "// ����
			+ "38 " + "03 "// Ŭ����
			+ "40 " +

			"01 0a 1d 08 0e 10 ba f9 1a 18 00 "// ������
			+ "20 00 2a " + "0b " + "4c 69 76 65 6f 72 44 65 61 74 68 " + "30 " + "2f " + "38 " + "02 " + "40 "

			+ "01 0a 1c 08 0e 10 c9 a5 ec 06 18 00 "// ������
			+ "20 00 2a " + "09 " + "44 64 64 64 64 64 64 64 33 " + "30 " + "10 " + "38 " + "07 " + "40 "

			+ "00 0a 1e 08 0e 10 bf f9 1a 18 00 "// �����
			+ "20 00 2a " + "0c " + "b8 b6 c0 bd b8 b8 ba f1 b4 dc b0 e1 " + "30 " + "0d " + "38 " + "01 " + "40 "

			+ "00 0a 1a 08 0e 10 bb f9 1a 18 00 "// ������
			+ "20 00 2a " + "08 " + "bb f3 b4 eb c0 fb c0 ce " + "30 " + "0d " + "38 " + "02 " + "40 "

			+ "00 0a 19 08 0e 10 a6 e1 49 18 00 "// ������
			+ "20 00 2a " + "07 " + "59 6f 75 72 6c 69 70 " + "30 " + "0b " + "38 " + "00 " + "40 "

			+ "00 0a 1f 08 0e 10 ad 87 81 07 18 00 " + // ������
			"20 00 2a " + "0c " + "41 61 77 65 66 69 6f 70 61 77 65 6a " + "30 " + "01 " + "38 " + "07 " + "40 "

			+ "00 10 03 18 00 20 00 00 00";

	public S_NewCreateItem(int type, long �����ð�, int ���, boolean ck) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(type);
		switch (type) {
		case ����â:// 255 / 65025 / 16581375 / 4228250625
			String s = "00 08 02 10 e0 11 18";
			// /00 08 02 10 e0 11 18

			StringTokenizer st = new StringTokenizer(s);
			while (st.hasMoreTokens()) {
				writeC(Integer.parseInt(st.nextToken(), 16));
			}
			byteWrite(�����ð� / 1000);
			// writeC(�����ð�);//�ð���
			// writeD(100);//�ð���
			/*
			 * �����ð� /= 1000; if(�����ð� <= 255){ writeC(�����ð�);//�ð��� }else{ if(�����ð�
			 * <= 65025){ writeH(�����ð�);//�ð��� }else{ if(�����ð� <= 16581375){
			 * writeCH(�����ð�);//�ð��� }else{ writeD(�����ð�);//�ð��� }
			 */
			// System.out.println(�����ð�);
			// 8a 9a 9e 01

			if (��� == 1) {
				s = "20 08 28 d4 2f 30 00 38 03 40";
			} else if (��� == 2) {
				s = "20 08 28 93 33 30 00 38 03 40";
			} else if (��� == 3) {
				s = "20 08 28 92 33 30 00 38 03 40";
			} else {
				s = "20 08 28 d4 2f 30 00 38 03 40";
			}
			// 20 08 28 d4 2f 30 00 38 03 40 1�ܰ�
			// 20 08 28 93 33 30 00 38 03 40 2�ܰ�
			// 20 08 28 92 33 30 00 38 03 40 3�ܰ�
			st = new StringTokenizer(s);
			while (st.hasMoreTokens()) {
				writeC(Integer.parseInt(st.nextToken(), 16));
			}

			if (��� == 1) {
				writeH(0x1ec9);// ac d2 20=2 c9 1e=1
				s = "48 d5 20 50 00 58 01";
			} else if (��� == 2) {
				writeH(0x20d2);// ac d2 20=2 c9 1e=1
				s = "48 d6 20 50 00 58 01";
			} else if (��� == 3) {
				writeH(0x20d3);// ac d2 20=2 c9 1e=1
				s = "48 d7 20 50 00 58 01";
			} else {
				writeH(0x1ec9);// ac d2 20=2 c9 1e=1
				s = "48 d5 20 50 00 58 01";
			}
			// 48 00 50 00 58 01
			// 48 d5 20 50 00 58 01
			st = new StringTokenizer(s);
			while (st.hasMoreTokens()) {
				writeC(Integer.parseInt(st.nextToken(), 16));
			}
			writeH(0x0060);
			writeH(0x0068);
			writeH(0x0070);

			writeH(0);// �ð���46 e7
			// 00 00
		default:
			break;
		}
	}

	private int tamwaitcount(int obj) {
		int count = 0;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM tam WHERE objid = ?"); // �ɸ���
			// ���̺���
			// ���ָ�
			// ���ͼ�
			pstm.setInt(1, obj);
			rs = pstm.executeQuery();
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return count;
	}

	public S_NewCreateItem(String account, int type) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(type);
		switch (type) {
		case TamPage:
			writeC(1);// _sex);//���ڿ���?
			Connection con = null;
			PreparedStatement pstm = null;
			ResultSet rs = null;

			int _level = 0;
			int _class = 0;
			int _sex = 0;
			String _Name = null;
			Timestamp tamtime = null;
			int _objid = 0;
			int objidcount;
			long time = 0;
			long sysTime = System.currentTimeMillis();
			int tamcount;
			try {
				con = L1DatabaseFactory.getInstance().getConnection();
				pstm = con.prepareStatement(
						"SELECT objid, TamEndTime, level, char_name, Type,Sex FROM characters WHERE account_name=? ORDER BY `TamEndTime` DESC, `EXP` DESC");
				pstm.setString(1, account);
				rs = pstm.executeQuery();
				while (rs.next()) {

					tamcount = 0;
					objidcount = 0;
					time = 0;
					tamtime = null;

					_objid = rs.getInt("objid");
					_level = rs.getInt("level");
					_class = rs.getInt("Type");
					_sex = rs.getInt("Sex");
					_Name = rs.getString("char_name");
					tamtime = rs.getTimestamp("TamEndTime");

					if (tamtime != null) {
						if (sysTime < tamtime.getTime()) {
							time = tamtime.getTime() - sysTime;
						}
					}

					// System.out.println(tamwaitcount(_objid));
					/*
					 * ������"00 0a 19 08 00 10 a6 e1 49 18 "//������ ������
					 * "00 0a 19 08 00 10 f3 ad 8e 01 18 "//������ �����
					 * "00 0a 1e 08 00 10 bf f9 1a 18 "//����� �����
					 * "01 0a 1d 08 00 10 e0 ed 81 07 18 " ������
					 * "00 0a 1a 08 00 10 bb f9 1a 18 "//������ ������
					 * "01 0a 1d 08 00 10 ba f9 1a 18 "//������ ������
					 * "01 0a 1f 08 00 10 b3 fb 8f 01 18 "//������ ������
					 * "01 0a 1c 08 00 10 b8 f9 1a 18 "// ������ ���ٿ�
					 * "01 0a 1a 08 00 10 e1 ed 81 07 18 " ���ٿ�
					 * "00 0a 1b 08 00 10 e2 ed 81 07 18 " ������
					 * "00 0a 1d 08 00 10 e6 ed 81 07 18 " ������
					 * "00 0a 1d 08 00 10 e7 ed 81 07 18 " ��ȯ����
					 * "00 0a 1f 08 00 10 9e dd 8f 01 18 "//��ȯ���� ��ȯ����
					 * "00 0a 1e 08 00 10 df ed 81 07 18 " ������
					 * "01 0a 1c 08 00 10 c9 a5 ec 06 18 "//������ ������
					 * "01 0a 1d 08 00 10 e3 ed 81 07 18 "
					 */
					/*
					 * if(_class == 0){//���� if(_sex == 0){temp2 = "a6 e1 49";
					 * addlen=3; }else{temp2 = "f3 ad 8e 01"; addlen=4;} }else
					 * if(_class == 1){//��� if(_sex == 0){temp2 = "bf f9 1a"
					 * ;addlen=3; }else{temp2 = "e0 ed 81 07";addlen=4;} }else
					 * if(_class == 2){//���� if(_sex == 0){temp2 = "bb f9 1a"
					 * ;addlen=3; }else{temp2 = "ba f9 1a";addlen=3;} }else
					 * if(_class == 3){//���� if(_sex == 0){temp2 = "b3 fb 8f 01"
					 * ;addlen=4; }else{temp2 = "b8 f9 1a"; addlen=3;} }else
					 * if(_class == 4){//��ũ���� if(_sex == 0){temp2 =
					 * "e1 ed 81 07";addlen=4; }else{temp2 = "e2 ed 81 07"
					 * ;addlen=4;} }else if(_class == 5){//���� if(_sex ==
					 * 0){temp2 = "e6 ed 81 07";addlen=4; }else{temp2 =
					 * "e7 ed 81 07";addlen=4;} }else if(_class == 6){//ȯ��
					 * if(_sex == 0){temp2 = "9e dd 8f 01";addlen=4; }else{temp2
					 * = "df ed 81 07";addlen=4;} }else if(_class == 7){//����
					 * if(_sex == 0){temp2 = "c9 a5 ec 06";addlen=4; }else{temp2
					 * = "e3 ed 81 07";addlen=4;} }
					 */

					if (time == 0) {
						tamcount = 1;
					} else {
						tamcount = byteWriteCount(time / 1000);
					}
					objidcount = byteWriteCount(_objid);

					writeC(0x0a);//
					// System.out.println(objidcount);
					writeC(_Name.getBytes().length + 14 + objidcount/* addlen */
							+ tamcount);// ��Ŷ��ü����
					writeC(0x08);//
					writeC(0x00);// ��ȭ (���� �ٸ� ����)
					writeC(0x10);//
					byteWrite(_objid);// Ž ������ �ö�.
					/*
					 * StringTokenizer st = new StringTokenizer(temp2);
					 * while(st.hasMoreTokens()){
					 * writeC(Integer.parseInt(st.nextToken(), 16)); }
					 */
					writeC(0x18);
					if (time == 0) {
						writeC(0);// Ž
					} else {
						byteWrite(time / 1000);// Ž ������ �ö�.
					}
					writeC(0x20);
					writeC(tamwaitcount(_objid));
					writeC(0x2a);//
					writeC(_Name.getBytes().length);// �̸� ����
					writeByte(_Name.getBytes());// �̸� �ڿ� 0 �����̸�.
					writeC(0x30);//
					writeC(_level);// ����
					writeC(0x38);//
					writeC(_class);// Ŭ������ȣ
					writeC(0x40);//
					writeC(_sex);// _sex);//���ڿ���?
				}
				// ���� ������ �������� ����� ��Ŷ
				// writeC(0x00);
				writeC(0x10);
				writeC(0x03);
				writeC(0x18);
				writeC(0x00);
				writeC(0x20);
				writeC(0x00);
				writeH(0);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				SQLUtil.close(rs);
				SQLUtil.close(pstm);
				SQLUtil.close(con);
			}
			break;
		default:
			break;
		}
	}

	public S_NewCreateItem(int type, int subtype, int objid) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(type);
		switch (type) {
		case EMOTICON:
			writeC(0x01);
			writeC(0x08);
			int temp = objid / 128;
			if (temp > 0) {
				writeC(hextable[objid % 128]);
				while (temp > 128) {
					writeC(hextable[temp % 128]);
					temp = temp / 128;
				}
				writeC(temp);
			} else {
				if (objid == 0) {
					writeC(0);
				} else {
					writeC(hextable[objid]);
					writeC(0);
				}
			}
			// byteWrite(value);
			writeC(0x10);
			writeC(0x02);
			writeC(0x18);
			writeC(subtype);
			writeH(0);
			break;

		case CLAN_JOIN_SETTING:
			writeD(0x10010801);
			writeC(subtype);// ���� ����
			writeC(0x18);
			writeC(objid);// ���� ����
			writeD(0x00001422);
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
			break;
		default:
			break;
		}
	}

	public S_NewCreateItem(int type, LineageClient client) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case TAM_POINT:
			writeC(0x08);
			int value = client.getAccount().tam_point;
			if (value <= 0)
				writeC(0x00);

			byteWrite(value);
			writeH(0x00);
			break;
		}
	}

	public S_NewCreateItem(int id, int id2, boolean bool, boolean bool2) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(0x01C2);
		writeC(0x08);
		writeC(id);
		writeH(id2);
	}

	public static final int[] hextable = { 0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87, 0x88, 0x89, 0x8a, 0x8b, 0x8c,
			0x8d, 0x8e, 0x8f, 0x90, 0x91, 0x92, 0x93, 0x94, 0x95, 0x96, 0x97, 0x98, 0x99, 0x9a, 0x9b, 0x9c, 0x9d, 0x9e,
			0x9f, 0xa0, 0xa1, 0xa2, 0xa3, 0xa4, 0xa5, 0xa6, 0xa7, 0xa8, 0xa9, 0xaa, 0xab, 0xac, 0xad, 0xae, 0xaf, 0xb0,
			0xb1, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7, 0xb8, 0xb9, 0xba, 0xbb, 0xbc, 0xbd, 0xbe, 0xbf, 0xc0, 0xc1, 0xc2,
			0xc3, 0xc4, 0xc5, 0xc6, 0xc7, 0xc8, 0xc9, 0xca, 0xcb, 0xcc, 0xcd, 0xce, 0xcf, 0xd0, 0xd1, 0xd2, 0xd3, 0xd4,
			0xd5, 0xd6, 0xd7, 0xd8, 0xd9, 0xda, 0xdb, 0xdc, 0xdd, 0xde, 0xdf, 0xe0, 0xe1, 0xe2, 0xe3, 0xe4, 0xe5, 0xe6,
			0xe7, 0xe8, 0xe9, 0xea, 0xeb, 0xec, 0xed, 0xee, 0xef, 0xf0, 0xf1, 0xf2, 0xf3, 0xf4, 0xf5, 0xf6, 0xf7, 0xf8,
			0xf9, 0xfa, 0xfb, 0xfc, 0xfd, 0xfe, 0xff };

	static final int div1 = 128 * 128 * 128 * 128;
	static final int div2 = 128 * 128 * 128;
	static final int div3 = 128 * 128;
	static final int div4 = 128;

	private void byteWrite(long value) {
		long temp = value / 128;
		if (temp > 0) {
			writeC(hextable[(int) value % 128]);
			while (temp >= 128) {
				writeC(hextable[(int) temp % 128]);
				temp = temp / 128;
			}
			if (temp > 0)
				writeC((int) temp);
		} else {
			if (value == 0) {
				writeC(0);
			} else {
				writeC(hextable[(int) value]);
				writeC(0);
			}
		}
	}

	private int byteWriteCount(long value) {
		long temp = value / 128;
		int count = 0;
		if (temp > 0) {
			count++;
			while (temp >= 128) {
				count++;
				temp = temp / 128;
			}
			if (temp > 0)
				count++;
		} else {
			if (value == 0) {
				count++;
			} else {
				count += 2;
			}
		}
		return count;
	}

	/*
	 * 52 3f 00
	 * 
	 * 08
	 * 
	 * 01
	 * 
	 * 10
	 * 
	 * 28
	 * 
	 * 18 02 3f bf
	 */

	public S_NewCreateItem(int type, int subType, boolean ck, int i) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		switch (type) {
		case FISH_WINDOW:
			writeC(0x08);
			writeC(subType);
			if (subType == 1) { // ���۽�
				writeC(0x10);
				if (ck) {// �� ����
					if (i == 1) {
						writeC(0x28);// �����ǳ��˴�
					} else {
						writeC(0x50);// �ð� (�ʴ���)
					}
				} else {// �� ������
					writeH(0x01F0);// �ð� (�ʴ���)
				}
				writeC(0x18);
				writeC(ck ? 0x02 : 0x01);// 1: �� ������ 2:: ������
				writeH(0x00);
			} else if (subType == 2) { // ���� �ų� ��������
				writeH(0x00);
			}
			break;
		}
	}

	public S_NewCreateItem(int warType, int second, String castle) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(CASTLE_WAR_TIME);
		writeC(0x08);
		writeC(warType);// 1���� 2����
		writeC(0x10);
		if (second > 0) {
			int total = second *= 2;
			boolean ck = false;
			while (true) {
				if ((second -= 126) <= 0)
					break;
				if (!ck) {
					total += 256;
					ck = true;
				} else
					total += 128;
			}
			if (total <= 126)
				writeC(total);
			else
				writeH(total);
			writeC(0x1A);
			writeC(castle.getBytes().length);
			writeS(castle);
			writeC(0x14);
		} else {
			writeC(0x00);
			writeH(0x00);
		}
	}

	/*
	 * ���� �������� ��Ʈ writeC(Opcodes.S_EXTENDED_PROTOBUF);
	 * writeH(CASTLE_WAR_TIME_END); writeD(0x0D120408); writeC(0x08);
	 * writeC(0x91);//�� Ÿ�� writeH(0x1207); writeC(0x08); writeS("�����̸�");
	 * writeC(0x00);
	 */

	public S_NewCreateItem(L1PcInstance pc, L1Object obj) {
		try {
			if (obj instanceof L1NpcInstance) {
				if (((L1NpcInstance) obj).getNpcId() == 100709) { // ����(10��)
					writeC(Opcodes.S_EXTENDED_PROTOBUF);
					writeH(LIST);
					writeH(0x08);
					writeC(0x12);
					writeC(0x07);
					writeC(0x08);
					writeH(0x02F7);
					writeH(0x10);
					writeH(0x18);
					writeH(0x18);
					writeH(0x00);
					return;
				} else if (((L1NpcInstance) obj).getNpcId() == 81121) { // �����
					// ����
					writeC(Opcodes.S_EXTENDED_PROTOBUF);
					writeH(LIST);
					writeH(0x08);
					writeC(0x12);
					writeC(0x06);
					writeC(0x08);
					writeC(0x22);
					writeH(0x10);
					writeH(0x18);
					writeH(0x00);
					return;
				} else if (((L1NpcInstance) obj).getNpcId() == 100692) {
					writeC(Opcodes.S_EXTENDED_PROTOBUF);
					writeH(LIST);
					writeH(0x08);
					writeC(0x12);
					writeC(0x07);
					writeC(0x08);
					writeH(0x02E7);
					writeH(0x10);
					writeH(0x18);
					writeH(0x0712);
					writeC(0x08);
					writeH(0x02E8);
					writeH(0x10);
					writeH(0x18);
					writeH(0x0712);
					writeC(0x08);
					writeH(0x02E9);
					writeH(0x10);
					writeH(0x18);
					writeH(0x0712);
					writeC(0x08);
					writeH(0x02EA);
					writeH(0x10);
					writeH(0x18);
					writeH(0x0712);
					writeC(0x08);
					writeH(0x02EB);
					writeH(0x10);
					writeH(0x18);
					writeH(0xEA30);
					return;
				} else if (((L1NpcInstance) obj).getNpcId() == 100671) {
					writeC(Opcodes.S_EXTENDED_PROTOBUF);
					writeH(LIST);
					writeH(0x08);
					writeC(0x12);
					writeC(0x07);
					writeC(0x08);
					writeH(741);
					writeH(0x10);
					writeH(0x18);
					writeC(0x12);
					writeC(0x07);
					writeC(0x08);
					writeH(742);
					writeH(0x10);
					writeH(0x18);
					writeC(0x12);
					writeC(0x07);
					for (int i = 730; i <= 736; i++) {
						writeC(0x08);
						writeH(i);
						writeH(0x10);
						writeH(0x18);
						if (i == 736)
							writeH(0x00);
						else {
							writeC(0x12);
							writeC(0x07);
						}
					}
					return;

				} else if (((L1NpcInstance) obj).getNpcId() == 101001) {
					String s = "39 00 08 00 12 07 08 ce 05 10 00 18 00 12 07 "
							+ "08 cf 05 10 00 18 00 12 07 08 d0 05 10 00 18 00 "
							+ "12 07 08 d1 05 10 00 18 00 12 07 08 d2 05 10 00 "
							+ "18 00 12 07 08 d3 05 10 00 18 00 12 07 08 d4 05 "
							+ "10 00 18 00 12 07 08 d5 05 10 00 18 00 18 00 35 32";
					StringTokenizer st = new StringTokenizer(s);
					writeC(Opcodes.S_EXTENDED_PROTOBUF);
					while (st.hasMoreTokens()) {
						writeC(Integer.parseInt(st.nextToken(), 16));
					}
					return;
				} else if (((L1NpcInstance) obj).getNpcId() == 101027) {
					String s = "39 00 08 00 " + "12 07 08 a0 07 10 00 18 00 " + "12 07 08 a1 07 10 00 18 00 "
							+ "12 07 08 a2 07 10 00 18 00 " + "12 07 08 a3 07 10 00 18 00 "
							+ "12 07 08 a4 07 10 00 18 00 " + "12 07 08 a5 07 10 00 18 00 "

							+ "12 07 08 a6 07 10 00 18 00 " + "12 07 08 a7 07 10 00 18 00 "
							+ "12 07 08 a8 07 10 00 18 00 " + "12 07 08 a9 07 10 00 18 00 "
							+ "12 07 08 aa 07 10 00 18 00 " + "12 07 08 ab 07 10 00 18 00 "

							+ "12 07 08 ac 07 10 00 18 00 " + "12 07 08 ad 07 10 00 18 00 "
							+ "12 07 08 ae 07 10 00 18 00 " + "12 07 08 af 07 10 00 18 00 "
							+ "12 07 08 b0 07 10 00 18 00 " + "12 07 08 b1 07 10 00 18 00 "

							+ "12 07 08 83 0C 10 00 18 00 " + "12 07 08 84 0C 10 00 18 00 "
							+ "12 07 08 85 0C 10 00 18 00 " + "12 07 08 86 0C 10 00 18 00 "
							+ "12 07 08 87 0C 10 00 18 00 " + "12 07 08 88 0C 10 00 18 00 " + "18 00 44 d9";
					writeC(Opcodes.S_EXTENDED_PROTOBUF);
					StringTokenizer st = new StringTokenizer(s);
					while (st.hasMoreTokens()) {
						writeC(Integer.parseInt(st.nextToken(), 16));
					}
					return;

				} else if (((L1NpcInstance) obj).getNpcId() == 101028) {
					String s = "39 00 08 00 " + "12 07 08 b2 07 10 00 18 00 "// 1970
							+ "12 07 08 b3 07 10 00 18 00 " + "12 07 08 b4 07 10 00 18 00 "
							+ "12 07 08 b5 07 10 00 18 00 " + "12 07 08 b6 07 10 00 18 00 "
							+ "12 07 08 b7 07 10 00 18 00 "

							+ "12 07 08 b8 07 10 00 18 00 " + "12 07 08 b9 07 10 00 18 00 "
							+ "12 07 08 ba 07 10 00 18 00 " + "12 07 08 bb 07 10 00 18 00 "
							+ "12 07 08 bc 07 10 00 18 00 " + "12 07 08 bd 07 10 00 18 00 "

							+ "12 07 08 be 07 10 00 18 00 " + "12 07 08 bf 07 10 00 18 00 "
							+ "12 07 08 c0 07 10 00 18 00 " + "12 07 08 c1 07 10 00 18 00 "
							+ "12 07 08 c2 07 10 00 18 00 " + "12 07 08 c3 07 10 00 18 00 "

							+ "12 07 08 c4 07 10 00 18 00 " + "12 07 08 c5 07 10 00 18 00 "
							+ "12 07 08 c6 07 10 00 18 00 " + "12 07 08 c7 07 10 00 18 00 "
							+ "12 07 08 c8 07 10 00 18 00 " + "12 07 08 c9 07 10 00 18 00 "

							+ "12 07 08 ca 07 10 00 18 00 " + "12 07 08 cb 07 10 00 18 00 "
							+ "12 07 08 cc 07 10 00 18 00 " + "12 07 08 cd 07 10 00 18 00 "
							+ "12 07 08 ce 07 10 00 18 00 " + "12 07 08 cf 07 10 00 18 00 "

							+ "12 07 08 d0 07 10 00 18 00 " + "12 07 08 d1 07 10 00 18 00 "

							+ "12 07 08 d2 07 10 00 18 00 " + "12 07 08 d3 07 10 00 18 00 "
							+ "12 07 08 d4 07 10 00 18 00 " + "12 07 08 d5 07 10 00 18 00 "

							+ "12 07 08 d6 07 10 00 18 00 " + "12 07 08 d7 07 10 00 18 00 "
							+ "12 07 08 d8 07 10 00 18 00 " + "12 07 08 d9 07 10 00 18 00 "
							+ "12 07 08 da 07 10 00 18 00 " + "12 07 08 db 07 10 00 18 00 "// 2011
							+ "18 00 00 00";
					StringTokenizer st = new StringTokenizer(s);
					writeC(Opcodes.S_EXTENDED_PROTOBUF);
					while (st.hasMoreTokens()) {
						writeC(Integer.parseInt(st.nextToken(), 16));
					}
					return;

					/*
					 * }else if(((L1NpcInstance)obj).getNpcId() == 101002){
					 * writeC(Opcodes.S_EXTENDED_PROTOBUF); writeH(LIST);
					 * writeH(0x08);
					 * 
					 * writeC(0x12);writeC(0x07);writeC(0x08); writeH(1474);
					 * writeH(0x10);writeH(0x18);
					 * 
					 * writeC(0x12);writeC(0x07);writeC(0x08); writeH(1475);
					 * writeH(0x10);writeH(0x18); //1476 = Ÿ���κ�//1477 = Ÿ������
					 * writeC(0x12);writeC(0x07);writeC(0x08); writeH(1478);
					 * writeH(0x10);writeH(0x18);
					 * 
					 * writeC(0x12);writeC(0x07);writeC(0x08); writeH(1479);
					 * writeH(0x10);writeH(0x18); writeC(0x18);writeC(0x00);
					 * writeC(0x26);writeC(0x70);
					 * 
					 * return; }else if(((L1NpcInstance)obj).getNpcId() ==
					 * 101003){ String s =
					 * "19 39 00 08 00 12 07 08 be 05 10 00 18 00 12 07 "+
					 * "08 bf 05 10 00 18 00 12 07 08 c0 05 10 00 18 00 "+
					 * "12 07 08 c1 05 10 00 18 00 12 07 08 c4 05 10 00 "+
					 * "18 00 12 07 08 c5 05 10 00 18 00 12 07 08 ca 05 "+
					 * "10 00 18 00 12 07 08 cb 05 10 00 18 00 12 07 08 "+
					 * "cc 05 10 00 18 00 12 07 08 cd 05 10 00 18 00 18 00 75 d0"
					 * ; StringTokenizer st = new StringTokenizer(s);
					 * while(st.hasMoreTokens()){
					 * writeC(Integer.parseInt(st.nextToken(), 16)); } return;
					 */
				}
			}
			FastTable<L1NpcAction> list = NpcActionTable.getInstance().getlist(pc, obj);
			if (list.size() <= 0) {
				if (obj instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					if (npc.getNpcId() == 70614) {// ����
						writeC(Opcodes.S_EXTENDED_PROTOBUF);
						writeH(LIST);
						writeH(0x08);
						writeC(0x12);
						writeC(0x07);
						for (int i = 655; i <= 670; i++) {
							writeC(0x08);
							writeH(i);
							writeH(0x10);
							writeH(0x18);
							if (i == 670)
								writeH(0x00);
							else {
								writeC(0x12);
								writeC(0x07);
							}
						}
					}
				}
				return;
			}

			writeC(Opcodes.S_EXTENDED_PROTOBUF);
			writeH(LIST);
			writeH(0x08);
			writeC(0x12);
			writeC(0x07);
			FastTable<Integer> ck = new FastTable<Integer>();
			if (obj instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) obj;
				if (npc.getNpcId() == 100029) {// �����ǺҰ� ���ν�, �긶����
					writeC(0x08);
					writeH(1153);
					writeH(0x10);
					writeH(0x18);
					for (int a = 1154; a <= 1157; a++) {
						writeC(0x12);
						writeC(0x07);
						writeC(0x08);
						writeH(a);
						writeH(0x10);
						writeH(0x18);
					}
				}
			}
			for (int i = 0; i < list.size(); i++) {
				L1NpcMakeItemAction na = (L1NpcMakeItemAction) list.get(i);
				int itemid = na.getItemId(obj);
				if (itemid == 0)
					continue;
				int code = createCode(itemid);
				if (code == 0)
					continue;
				if (ck.contains(code))
					continue;
				else
					ck.add(code);

				if (code >= 463 && code <= 466) {
					if (obj instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) obj;
						if (npc.getNpcId() == 70904)
							code += 8;
					}
				}
				if (obj instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) obj;
					if (npc.getNpcId() == 100029) {
						writeC(0x12);
						writeC(0x07);
					}
				}

				if (code == 10 || code == 14 || code == 18 || code == 22 || code == 26 || code == 30) {
					for (int j = 0; j < 4; j++) {
						writeC(0x08);
						writeH(code + 128 + j);
						writeH(0x10);
						writeH(0x18);
						writeC(0x12);
						writeC(0x07);
					}
				} else {
					if (code <= 127)
						code += 128;
					writeC(0x08);
					writeH(code);
					writeH(0x10);
					writeH(0x18);
					if (i == (list.size() - 1)) {

						if (obj instanceof L1NpcInstance) {
							L1NpcInstance npc = (L1NpcInstance) obj;
							if (npc.getNpcId() == 100029) {// �����ǺҰ� ���ν�, �긶����
								for (int a = 655; a <= 670; a++) {
									writeC(0x12);
									writeC(0x07);
									writeC(0x08);
									writeH(a);
									writeH(0x10);
									writeH(0x18);
								}
								for (int a = 0; a < 3; a++) {
									writeC(0x12);
									writeC(0x07);
									writeC(0x08);
									writeH(935 + a);
									writeH(0x10);
									writeH(0x18);
								}
							} else

							/*
							 * if (npc.getNpcId() == 11887) {// ���� // ���� ���� for
							 * (int a = 1763; a <= 1766; a++) { writeC(0x12);
							 * writeC(0x07); writeC(0x08);//1763 writeH(1763 +
							 * a);// 1001+a);// writeH(0x10); writeH(0x18); }
							 * 
							 * } else
							 */

							if (npc.getNpcId() == 70690) {// �ٹ�Ʈ
								// (�Ϸ�~���ĺ���)
								for (int a = 0; a < 8; a++) {
									writeC(0x12);
									writeC(0x07);
									writeC(0x08);// 993
									writeH(993 + a);// 1001+a);//
									writeH(0x10);
									writeH(0x18);
								}

							}
						}

						writeH(0x00);
					} else

					{
						if (obj instanceof L1NpcInstance) {
							L1NpcInstance npc = (L1NpcInstance) obj;
							if (npc.getNpcId() != 100029) {
								writeC(0x12);
								writeC(0x07);
							}
						}
						// writeC(0x12);
						// writeC(0x07);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String check = // 13 02
			"08 90 1c 10 d0 81 87 a5 05 18 80 a3 05 " + "20 01 28 03 32 43 08 17 12 3f 08 02 10 d3 61 18 "
					+ "03 22 14 67 72 6f 77 74 68 20 63 72 79 73 74 61 "
					+ "6c 20 70 69 65 63 65 28 00 30 af 10 38 01 42 06 "
					+ "24 31 32 38 32 39 4a 06 17 14 00 00 00 00 50 87 "
					+ "ff ff ff ff ff ff ff ff 01 32 44 08 05 12 40 08 "
					+ "02 10 f1 6d 18 03 22 0e 70 73 79 20 73 6f 66 74 "
					+ "20 64 72 69 6e 6b 28 00 30 bc 26 38 01 42 06 24 "
					+ "31 30 39 33 37 4a 0d 15 78 00 03 02 00 00 00 3d "
					+ "e0 33 4d cb 50 97 ff ff ff ff ff ff ff ff 01 32 "
					+ "4b 08 1c 12 47 08 02 10 95 75 18 01 22 15 66 61 "
					+ "6e 74 61 73 79 20 63 72 79 73 74 61 6c 20 70 69 "
					+ "65 63 65 28 00 30 aa 10 38 01 42 0d 24 31 37 35 "
					+ "33 31 20 24 31 37 38 30 31 4a 06 17 14 00 00 00 "
					+ "00 50 87 ff ff ff ff ff ff ff ff 01 32 42 08 0a "
					+ "12 3e 08 02 10 c8 1f 18 64 22 0e 67 6d 20 70 6f "
					+ "74 69 6f 6e 20 31 34 74 68 28 33 30 c1 26 38 01 "
					+ "42 06 24 31 30 39 33 35 4a 0b 17 13 00 00 00 00 "
					+ "3d e0 33 4d cb 50 97 ff ff ff ff ff ff ff ff 01 "
					+ "32 3f 08 21 12 3b 08 02 10 ea 6b 18 01 22 10 62 "
					+ "61 67 20 6f 66 20 73 61 6e 64 20 77 6f 72 6d 28 "
					+ "00 30 95 09 38 01 42 06 24 31 34 32 39 30 4a 06 "
					+ "17 04 00 00 00 00 50 87 ff ff ff ff ff ff ff ff "
					+ "01 32 3f 08 0f 12 3b 08 02 10 80 7a 18 14 22 0b "
					+ "64 72 75 77 61 20 63 61 6e 64 79 28 33 30 8e 1a "
					+ "38 01 42 06 24 31 30 39 34 36 4a 0b 17 03 00 00 "
					+ "00 00 3d e0 33 4d cb 50 97 ff ff ff ff ff ff ff "
					+ "ff 01 32 43 08 26 12 3f 08 02 10 b8 7b 18 01 22 "
					+ "0f 65 76 20 69 76 6f 72 79 20 63 68 61 72 67 65 "
					+ "28 00 30 e0 0f 38 01 42 06 24 32 30 34 35 35 4a "
					+ "0b 17 07 00 00 00 00 3d e0 33 4d cb 50 97 ff ff "
					+ "ff ff ff ff ff ff 01 32 41 08 14 12 3d 08 02 10 "
					+ "c8 20 18 01 22 13 72 75 62 79 20 6f 66 20 64 72 "
					+ "61 67 6f 6e 20 32 30 30 39 28 33 30 8d 1d 38 01 "
					+ "42 05 24 37 39 37 31 4a 06 17 14 00 00 00 00 50 "
					+ "97 ff ff ff ff ff ff ff ff 01 32 44 08 02 12 40 "
					+ "08 02 10 f1 6d 18 03 22 0e 70 73 79 20 73 6f 66 "
					+ "74 20 64 72 69 6e 6b 28 00 30 bc 26 38 01 42 06 "
					+ "24 31 30 39 33 37 4a 0d 15 78 00 03 02 00 00 00 "
					+ "3d e0 33 4d cb 50 97 ff ff ff ff ff ff ff ff 01 "
					+ "32 40 08 19 12 3c 08 02 10 c6 7b 18 01 22 0c 69 "
					+ "63 65 20 74 65 61 72 20 62 61 67 28 00 30 be 07 "
					+ "38 01 42 06 24 32 30 34 37 39 4a 0b 17 07 00 00 "
					+ "00 00 3d e0 33 4d cb 50 97 ff ff ff ff ff ff ff "
					+ "ff 01 32 3f 08 07 12 3b 08 02 10 80 7a 18 05 22 "
					+ "0b 64 72 75 77 61 20 63 61 6e 64 79 28 33 30 8e "
					+ "1a 38 01 42 06 24 31 30 39 34 36 4a 0b 17 03 00 "
					+ "00 00 00 3d e0 33 4d cb 50 97 ff ff ff ff ff ff "
					+ "ff ff 01 32 4b 08 1e 12 47 08 02 10 95 75 18 01 "
					+ "22 15 66 61 6e 74 61 73 79 20 63 72 79 73 74 61 "
					+ "6c 20 70 69 65 63 65 28 00 30 aa 10 38 01 42 0d "
					+ "24 31 37 35 33 31 20 24 31 37 38 30 31 4a 06 17 "
					+ "14 00 00 00 00 50 87 ff ff ff ff ff ff ff ff 01 "
					+ "32 45 08 0c 12 41 08 02 10 ee 6d 18 0a 22 0f 70 "
					+ "73 79 20 73 70 69 63 79 20 72 61 6d 65 6e 28 00 "
					+ "30 be 26 38 01 42 06 24 31 30 39 33 36 4a 0d 15 "
					+ "78 00 03 02 00 00 00 3d e0 33 4d cb 50 97 ff ff "
					+ "ff ff ff ff ff ff 01 32 3e 08 23 12 3a 08 02 10 "
					+ "bd 6f 18 01 22 0f 6b 69 72 74 61 73 20 73 69 6e "
					+ "69 73 74 65 72 28 00 30 93 2c 38 01 42 06 24 31 "
					+ "35 33 38 34 4a 06 17 0e 00 00 00 00 50 87 ff ff "
					+ "ff ff ff ff ff ff 01 32 48 08 11 12 44 08 02 10 "
					+ "84 1a 18 03 22 10 62 6d 20 6d 61 67 69 63 20 73 "
					+ "63 72 6f 6c 6c 33 28 33 30 ae 2f 38 01 42 05 24 "
					+ "35 38 32 35 4a 10 17 05 00 00 00 00 11 03 18 03 "
					+ "05 03 06 03 23 03 50 97 ff ff ff ff ff ff ff ff "
					+ "01 32 3b 08 28 12 37 08 02 10 b9 77 18 01 22 0c "
					+ "66 69 72 65 20 63 72 79 73 74 61 6c 28 00 30 cc "
					+ "19 38 01 42 06 24 31 38 36 31 37 4a 06 17 15 00 "
					+ "00 00 00 50 83 ff ff ff ff ff ff ff ff 01 32 44 "
					+ "08 16 12 40 08 02 10 c6 20 18 01 22 16 64 69 61 "
					+ "6d 6f 6e 64 20 6f 66 20 64 72 61 67 6f 6e 20 32 "
					+ "30 30 39 28 33 30 89 1d 38 01 42 05 24 37 39 36 "
					+ "39 4a 06 17 14 00 00 00 00 50 97 ff ff ff ff ff "
					+ "ff ff ff 01 32 45 08 04 12 41 08 02 10 ee 6d 18 "
					+ "03 22 0f 70 73 79 20 73 70 69 63 79 20 72 61 6d "
					+ "65 6e 28 00 30 be 26 38 01 42 06 24 31 30 39 33 "
					+ "36 4a 0d 15 78 00 03 02 00 00 00 3d e0 33 4d cb "
					+ "50 97 ff ff ff ff ff ff ff ff 01 32 36 08 1b 12 "
					+ "32 08 02 10 d8 61 18 01 22 07 69 63 71 20 6b 65 "
					+ "79 28 00 30 d7 17 38 01 42 06 24 31 32 38 34 38 "
					+ "4a 06 17 0c 00 00 00 00 50 83 ff ff ff ff ff ff "
					+ "ff ff 01 32 44 08 09 12 40 08 02 10 f1 6d 18 05 "
					+ "22 0e 70 73 79 20 73 6f 66 74 20 64 72 69 6e 6b "
					+ "28 00 30 bc 26 38 01 42 06 24 31 30 39 33 37 4a "
					+ "0d 15 78 00 03 02 00 00 00 3d e0 33 4d cb 50 97 "
					+ "ff ff ff ff ff ff ff ff 01 32 3f 08 20 12 3b 08 "
					+ "02 10 eb 6b 18 01 22 10 62 61 67 20 6f 66 20 61 "
					+ "6e 74 20 71 75 65 65 6e 28 00 30 bb 0e 38 01 42 "
					+ "06 24 31 34 32 38 39 4a 06 17 04 00 00 00 00 50 "
					+ "87 ff ff ff ff ff ff ff ff 01 32 43 08 0e 12 3f "
					+ "08 02 10 c8 1f 18 c8 01 22 0e 67 6d 20 70 6f 74 "
					+ "69 6f 6e 20 31 34 74 68 28 33 30 c1 26 38 01 42 "
					+ "06 24 31 30 39 33 35 4a 0b 17 13 00 00 00 00 3d "
					+ "e0 33 4d cb 50 97 ff ff ff ff ff ff ff ff 01 32 "
					+ "46 08 25 12 42 08 02 10 b7 7b 18 01 22 12 65 76 "
					+ "20 73 6f 75 6c 74 6f 6d 62 20 63 68 61 72 67 65 "
					+ "28 00 30 e0 0f 38 01 42 06 24 32 30 34 35 34 4a "
					+ "0b 17 07 00 00 00 00 3d e0 33 4d cb 50 97 ff ff "
					+ "ff ff ff ff ff ff 01 32 42 08 13 12 3e 08 02 10 "
					+ "b5 7b 18 0a 22 0f 65 76 20 6f 6d 61 6e 20 74 65 "
					+ "6c 62 6f 6f 6b 28 00 30 e3 1e 38 01 42 05 24 39 "
					+ "33 38 31 4a 0b 17 05 00 00 00 00 3d e0 33 4d cb "
					+ "50 97 ff ff ff ff ff ff ff ff 01 32 3e 08 2a 12 "
					+ "3a 08 02 10 8c 6e 18 01 22 0f 70 63 20 69 76 6f "
					+ "72 79 20 65 6c 69 78 69 72 28 00 30 8e 20 38 01 "
					+ "42 06 24 32 30 34 36 32 4a 06 17 13 00 00 00 00 "
					+ "50 93 ff ff ff ff ff ff ff ff 01 32 45 08 01 12 "
					+ "41 08 02 10 ee 6d 18 03 22 0f 70 73 79 20 73 70 "
					+ "69 63 79 20 72 61 6d 65 6e 28 00 30 be 26 38 01 "
					+ "42 06 24 31 30 39 33 36 4a 0d 15 78 00 03 02 00 "
					+ "00 00 3d e0 33 4d cb 50 97 ff ff ff ff ff ff ff "
					+ "ff 01 32 40 08 18 12 3c 08 02 10 c6 7b 18 01 22 "
					+ "0c 69 63 65 20 74 65 61 72 20 62 61 67 28 00 30 "
					+ "be 07 38 01 42 06 24 32 30 34 37 39 4a 0b 17 07 "
					+ "00 00 00 00 3d e0 33 4d cb 50 97 ff ff ff ff ff "
					+ "ff ff ff 01 32 42 08 06 12 3e 08 02 10 c8 1f 18 "
					+ "32 22 0e 67 6d 20 70 6f 74 69 6f 6e 20 31 34 74 "
					+ "68 28 33 30 c1 26 38 01 42 06 24 31 30 39 33 35 "
					+ "4a 0b 17 13 00 00 00 00 3d e0 33 4d cb 50 97 ff "
					+ "ff ff ff ff ff ff ff 01 32 4b 08 1d 12 47 08 02 "
					+ "10 95 75 18 01 22 15 66 61 6e 74 61 73 79 20 63 "
					+ "72 79 73 74 61 6c 20 70 69 65 63 65 28 00 30 aa "
					+ "10 38 01 42 0d 24 31 37 35 33 31 20 24 31 37 38 "
					+ "30 31 4a 06 17 14 00 00 00 00 50 87 ff ff ff ff "
					+ "ff ff ff ff 01 32 3f 08 0b 12 3b 08 02 10 80 7a "
					+ "18 0a 22 0b 64 72 75 77 61 20 63 61 6e 64 79 28 "
					+ "33 30 8e 1a 38 01 42 06 24 31 30 39 34 36 4a 0b "
					+ "17 03 00 00 00 00 3d e0 33 4d cb 50 97 ff ff ff "
					+ "ff ff ff ff ff 01 32 3d 08 22 12 39 08 02 10 f7 "
					+ "75 18 01 22 0e 62 61 67 20 6f 66 20 61 73 74 61 "
					+ "72 6f 74 28 00 30 a9 10 38 01 42 06 24 31 37 36 "
					+ "35 38 4a 06 17 07 00 00 00 00 50 83 ff ff ff ff "
					+ "ff ff ff ff 01 32 3e 08 10 12 3a 08 02 10 96 1a "
					+ "18 03 22 10 62 6d 20 6c 61 77 66 75 6c 20 74 69 "
					+ "63 6b 65 74 28 00 30 c2 18 38 01 42 05 24 35 38 "
					+ "34 30 4a 06 17 05 00 00 00 00 50 97 ff ff ff ff "
					+ "ff ff ff ff 01 32 46 08 27 12 42 08 02 10 b9 7b "
					+ "18 01 22 0d 65 76 20 67 69 61 6e 74 20 64 6f 6c "
					+ "6c 28 00 30 d8 33 38 01 42 10 24 32 30 34 36 36 "
					+ "20 5b 32 35 39 32 30 30 30 5d 4a 0f 17 08 01 00 "
					+ "00 00 3d e0 33 4d cb 3f 01 24 0a 50 17 32 45 08 "
					+ "15 12 41 08 02 10 c7 20 18 01 22 17 73 61 70 70 "
					+ "68 69 72 65 20 6f 66 20 64 72 61 67 6f 6e 20 32 "
					+ "30 30 39 28 33 30 8f 1d 38 01 42 05 24 37 39 37 "
					+ "30 4a 06 17 14 00 00 00 00 50 97 ff ff ff ff ff "
					+ "ff ff ff 01 32 3f 08 03 12 3b 08 02 10 80 7a 18 "
					+ "03 22 0b 64 72 75 77 61 20 63 61 6e 64 79 28 33 "
					+ "30 8e 1a 38 01 42 06 24 31 30 39 34 36 4a 0b 17 "
					+ "03 00 00 00 00 3d e0 33 4d cb 50 97 ff ff ff ff "
					+ "ff ff ff ff 01 32 40 08 1a 12 3c 08 02 10 c6 7b "
					+ "18 01 22 0c 69 63 65 20 74 65 61 72 20 62 61 67 "
					+ "28 00 30 be 07 38 01 42 06 24 32 30 34 37 39 4a "
					+ "0b 17 07 00 00 00 00 3d e0 33 4d cb 50 97 ff ff "
					+ "ff ff ff ff ff ff 01 32 45 08 08 12 41 08 02 10 "
					+ "ee 6d 18 05 22 0f 70 73 79 20 73 70 69 63 79 20 "
					+ "72 61 6d 65 6e 28 00 30 be 26 38 01 42 06 24 31 "
					+ "30 39 33 36 4a 0d 15 78 00 03 02 00 00 00 3d e0 "
					+ "33 4d cb 50 97 ff ff ff ff ff ff ff ff 01 32 32 "
					+ "08 1f 12 2e 08 02 10 91 78 18 01 22 0c 6f 74 68 "
					+ "65 72 20 73 65 6c 66 20 33 28 44 30 b0 30 38 01 "
					+ "42 06 24 31 38 37 32 34 4a 06 17 03 00 00 00 00 "
					+ "50 17 32 44 08 0d 12 40 08 02 10 f1 6d 18 0a 22 "
					+ "0e 70 73 79 20 73 6f 66 74 20 64 72 69 6e 6b 28 "
					+ "00 30 bc 26 38 01 42 06 24 31 30 39 33 37 4a 0d "
					+ "15 78 00 03 02 00 00 00 3d e0 33 4d cb 50 97 ff "
					+ "ff ff ff ff ff ff ff 01 32 43 08 24 12 3f 08 02 "
					+ "10 b6 7b 18 01 22 0f 65 76 20 67 69 72 61 6e 20 "
					+ "63 68 61 72 67 65 28 00 30 e0 0f 38 01 42 06 24 "
					+ "32 30 34 35 33 4a 0b 17 07 00 00 00 00 3d e0 33 "
					+ "4d cb 50 97 ff ff ff ff ff ff ff ff 01 32 44 08 "
					+ "12 12 40 08 02 10 b4 7b 18 0a 22 10 65 76 20 6a "
					+ "6f 77 6f 6f 20 74 65 6c 62 6f 6f 6b 28 00 30 ff "
					+ "17 38 01 42 06 24 31 35 39 39 34 4a 0b 17 05 00 "
					+ "00 00 00 3d e0 33 4d cb 50 97 ff ff ff ff ff ff "
					+ "ff ff 01 32 44 08 29 12 40 08 02 10 f0 73 18 01 "
					+ "22 10 31 35 74 68 20 72 65 73 63 75 65 20 63 6f "
					+ "69 6e 28 00 30 e2 0f 38 01 42 06 24 31 30 39 33 "
					+ "38 4a 0b 17 05 00 00 00 00 3d e0 33 4d cb 50 97 " + "ff ff ff ff ff ff ff ff 01 00 00";



	String stat1 = "0a 06 08 19 10 01 18 01 12 06 08 19 10 " + "01 18 01 1a 08 08 19 10 01 18 01 38 32 22 06 08 "
			+ "19 10 01 18 01 2a 06 08 19 10 01 30 32 32 0b 08 " + "ff ff ff ff ff ff ff ff ff 01 23 ca";

	String stat2 = "0a 06 08 23 10 01 18 01 12 06 08 23 10 " + "01 18 01 1a 08 08 23 10 01 18 01 38 64 22 06 08 "
			+ "23 10 01 18 01 2a 08 08 23 10 01 18 01 30 64 32 " + "0b 08 ff ff ff ff ff ff ff ff ff 01 68 92";

	String stat3 = "0a 08 08 2d 10 03 18 03 20 01 12 08 08 " + "2d 10 03 18 03 20 01 1a 09 08 2d 10 03 18 03 38 "
			+ "96 01 22 08 08 2d 10 03 18 03 20 01 2a 09 08 2d " + "10 03 18 02 30 96 01 32 0b 08 ff ff ff ff ff ff "
			+ "ff ff ff 01 eb 88";

	public S_NewCreateItem(int type, String s) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		StringTokenizer st = new StringTokenizer(s);
		if (type == 532) {
			st = new StringTokenizer(check);
		}
		while (st.hasMoreTokens()) {
			writeC(Integer.parseInt(st.nextToken(), 16));
		}
	}

	/*
	 * 43 e3 01 08 10 12 09 ���� 08 03 �ٴ� 10 07 �ٸ� 18 00 ��ġ 20 b4 10 ���� 79 27
	 */

	public S_NewCreateItem(String name, L1PcInstance pc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(0x01e5);
		writeC(0x08);
		byteWrite(pc.getInventory().calcWeightpercent());
		// writeC();
		if (name.equalsIgnoreCase("����")) {
			int �ִ�������� = pc.getMaxWeight();
			// CalcStat.�ִ��������(pc.getAbility().getTotalStr(),
			// pc.getAbility().getTotalCon());
			writeC(0x10);
			byteWrite(pc.getInventory().getWeight());

			writeC(0x18);
			byteWrite(�ִ��������);

			writeH(0);
		}
	}

	/*
	 * ea 01
	 * 
	 * 08 14 �� 10 0a ��Ʈ 18 0a ���� 20 0b ���� 28 10 �� 30 08 ī��
	 * 
	 * d8 44 D
	 */

	public S_NewCreateItem(int type, String name, L1PcInstance pc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);// ea01
		if (name.equalsIgnoreCase("��������")) {
			int str, inte, wis, dex, con, cha = 0;
			str = pc.getAbility().getStr();
			inte = pc.getAbility().getInt();
			wis = pc.getAbility().getWis();
			dex = pc.getAbility().getDex();
			con = pc.getAbility().getCon();
			cha = pc.getAbility().getCha();
			writeC(0x08);
			writeC(str);

			writeC(0x10);
			writeC(inte);

			writeC(0x18);
			writeC(wis);

			writeC(0x20);
			writeC(dex);

			writeC(0x28);
			writeC(con);

			writeC(0x30);
			writeC(cha);

			writeH(0);
		} else if (name.equalsIgnoreCase("����1")) {
			StringTokenizer st = new StringTokenizer(stat1);
			while (st.hasMoreTokens()) {
				writeC(Integer.parseInt(st.nextToken(), 16));
			}
		} else if (name.equalsIgnoreCase("����2")) {
			StringTokenizer st = new StringTokenizer(stat2);
			while (st.hasMoreTokens()) {
				writeC(Integer.parseInt(st.nextToken(), 16));
			}
		} else if (name.equalsIgnoreCase("����3")) {
			StringTokenizer st = new StringTokenizer(stat3);
			while (st.hasMoreTokens()) {
				writeC(Integer.parseInt(st.nextToken(), 16));
			}
		}
	}

	public S_NewCreateItem(int type, int ����, int stat, int stat2, String name, int classtype, L1PcInstance pc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		writeC(0x08);
		writeC(���� * 2);
		if (name.equalsIgnoreCase("��")) {
			int len = 0;
			byte[] minus = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
					(byte) 0xff, (byte) 0x01 };

			int �ٰŸ������ = CalcStat.�ٰŸ������(stat);
			int �ٰŸ����� = CalcStat.�ٰŸ�����(stat);
			int �ٰŸ�ġ��Ÿ = CalcStat.�ٰŸ�ġ��Ÿ(stat);
			int �ִ�������� = CalcStat.�ִ��������(stat, stat2);

			len += 4 + 4 + 1;
			if (�ٰŸ������ < 0) {
				len += 9;
			}
			if (�ٰŸ����� < 0) {
				len += 9;
			}
			if (�ٰŸ�ġ��Ÿ < 0) {
				len += 9;
			}
			if (�ִ�������� < 0) {
				len += 9;
			}

			writeC(0x12);
			writeC(len);

			writeC(0x08);
			writeC(�ٰŸ������);
			if (�ٰŸ������ < 0)
				writeByte(minus);
			writeC(0x10);
			writeC(�ٰŸ�����);
			if (�ٰŸ����� < 0)
				writeByte(minus);
			writeC(0x18);
			writeC(�ٰŸ�ġ��Ÿ);
			if (�ٰŸ�ġ��Ÿ < 0)
				writeByte(minus);
			writeC(0x20);
			byteWrite(�ִ��������);

			writeH(0);
		} else if (name.equalsIgnoreCase("��")) {
			/*
			 * 43 e3 01 08 10 2a 13 08 04 ���Ÿ������ 10 03 ���Ÿ����� 18 00 ���Ÿ�ġ�� 20 fc
			 * ff ff ff ff ff ff ff ff 01 ���� 28 06 ���Ÿ�ȸ�� 3d 69
			 */
			int len = 0;
			byte[] minus = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
					(byte) 0xff, (byte) 0x01 };
			int ���Ÿ������ = CalcStat.���Ÿ������(stat);
			int ���Ÿ����� = CalcStat.���Ÿ�����(stat);
			int ���Ÿ�ġ��Ÿ = CalcStat.���Ÿ�ġ��Ÿ(stat);
			int �������� = CalcStat.��������(stat);
			int ���Ÿ�ȸ�� = CalcStat.���Ÿ�ȸ��(stat);

			len += 5 + 5;

			if (���Ÿ������ < 0) {
				len += 9;
			}
			if (���Ÿ����� < 0) {
				len += 9;
			}
			if (���Ÿ�ġ��Ÿ < 0) {
				len += 9;
			}
			if (�������� < 0) {
				len += 9;
			}
			if (���Ÿ�ȸ�� < 0) {
				len += 9;
			}

			writeC(0x2a);
			writeC(len);

			writeC(0x08);
			writeC(���Ÿ������);
			if (���Ÿ������ < 0)
				writeByte(minus);

			writeC(0x10);
			writeC(���Ÿ�����);
			if (���Ÿ����� < 0)
				writeByte(minus);

			writeC(0x18);
			writeC(���Ÿ�ġ��Ÿ);
			if (���Ÿ�ġ��Ÿ < 0)
				writeByte(minus);

			writeC(0x20);
			writeC(��������);
			if (�������� < 0)
				writeByte(minus);

			writeC(0x28);
			writeC(���Ÿ�ȸ��);
			if (���Ÿ�ȸ�� < 0)
				writeByte(minus);

			writeH(0);
		} else if (name.equalsIgnoreCase("��")) {
			/*
			 * 32 0b 08 06 ȸ��ƽ 10 00 ����ȸ������ 18 98 11 �ִ�������� 20 0b ��������hp���� 28 00
			 * ? 93 e5
			 */

			int len = 0;
			byte[] minus = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
					(byte) 0xff, (byte) 0x01 };
			int ��ȸ��ƽ = CalcStat.��ȸ��ƽ(stat);
			int ����ȸ������ = CalcStat.����ȸ������(stat);
			int �ִ�������� = CalcStat.�ִ��������(stat, stat2);
			int �������������� = CalcStat.��������������(classtype, stat);

			len += 5 + 5 + 1;

			if (��ȸ��ƽ < 0) {
				len += 9;
			}
			if (����ȸ������ < 0) {
				len += 9;
			}
			if (�������������� < 0) {
				len += 9;
			}

			writeC(0x32);
			writeC(len);

			writeC(0x08);
			writeC(��ȸ��ƽ);
			if (��ȸ��ƽ < 0)
				writeByte(minus);

			writeC(0x10);
			writeC(����ȸ������);
			if (����ȸ������ < 0)
				writeByte(minus);

			writeC(0x18);
			byteWrite(�ִ��������);

			writeC(0x20);
			writeC(��������������);
			if (�������������� < 0)
				writeByte(minus);

			writeC(0x28);
			writeC(0);

			writeH(0);
		} else if (name.equalsIgnoreCase("��Ʈ")) {
			/*
			 * 43 e3 01 08 10 1a 13 08 00 ��������� 10 fe ff ff ff ff ff ff ff ff 01
			 * �������� 18 00 ���� ġ��Ÿ 20 03 ���� ���ʽ� 28 08 ���Ҹ� ���� 3d 69
			 */
			/*
			 * 0000: 43 e3 01 08 10 1a 13 08 00 10 fd ff ff ff ff ff
			 * C............... 0010: ff ff ff 01 18 00 20 02 28 06 25 22 ......
			 * .(.%"
			 */

			int len = 0;
			byte[] minus = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
					(byte) 0xff, (byte) 0x01 };
			int ��������� = CalcStat.���������(stat);
			int �������� = CalcStat.��������(stat);
			int ����ġ��Ÿ = CalcStat.����ġ��Ÿ(stat);
			int �������ʽ� = CalcStat.�������ʽ�(stat);
			int ���Ҹ𰨼� = CalcStat.���Ҹ𰨼�(stat);

			len += 5 + 5;

			if (��������� < 0) {
				len += 9;
			}
			if (�������� < 0) {
				len += 9;
			}
			if (����ġ��Ÿ < 0) {
				len += 9;
			}
			if (�������ʽ� < 0) {
				len += 9;
			}
			if (���Ҹ𰨼� < 0) {
				len += 9;
			}

			writeC(0x1a);
			writeC(len);

			writeC(0x08);
			writeC(���������);
			if (��������� < 0)
				writeByte(minus);

			writeC(0x10);
			writeC(��������);
			if (�������� < 0)
				writeByte(minus);

			writeC(0x18);
			writeC(����ġ��Ÿ);
			if (����ġ��Ÿ < 0)
				writeByte(minus);

			writeC(0x20);
			writeC(�������ʽ�);
			if (�������ʽ� < 0)
				writeByte(minus);

			writeC(0x28);
			writeC(���Ҹ𰨼�);
			if (���Ҹ𰨼� < 0)
				writeByte(minus);

			writeH(0);
		} else if (name.equalsIgnoreCase("����")) {
			/*
			 * 08 10 22 0c 08 02 ��ȸ��ƽ 10 02 18 25
			 * 
			 * 20 04 28 07 30 00 fb 2c
			 */
			int len = 0;
			byte[] minus = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
					(byte) 0xff, (byte) 0x01 };
			int ��ȸ��ƽ = CalcStat.��ȸ��ƽ(stat);
			int ���ǹ���ȸ�� = CalcStat.���ǹ���ȸ��(stat);
			int ������� = CalcStat.�������(classtype, stat);
			int[] ���������������� = CalcStat.����������������(classtype, stat);
			int hp = 0;
			if (stat >= 45) {
				hp = 300;
			} else if (stat >= 35) {
				hp = 150;
			} else if (stat >= 25) {
				hp = 50;
			}
			len += 6 + 4 + byteWriteCount(�������) + byteWriteCount(hp);

			if (stat >= 45) {
				len++;
			} else if (stat >= 35) {
				len++;
			}

			if (��ȸ��ƽ < 0) {
				len += 9;
			}
			if (���ǹ���ȸ�� < 0) {
				len += 9;
			}

			writeC(0x22);
			writeC(len);

			writeC(0x08);
			writeC(��ȸ��ƽ);
			if (��ȸ��ƽ < 0)
				writeByte(minus);

			writeC(0x10);
			writeC(���ǹ���ȸ��);
			if (���ǹ���ȸ�� < 0)
				writeByte(minus);

			writeC(0x18);
			byteWrite(�������);

			writeC(0x20);
			writeC(����������������[0]);

			writeC(0x28);
			writeC(����������������[1]);

			writeC(0x30);
			byteWrite(hp);
			/*
			 * if(stat >= 45){ writeC(0x96); writeC(2); }else if(stat >= 35){
			 * writeC(0x96); writeC(1); }else if(stat >= 25){ writeC(0x32);
			 * }else{ writeC(0); }
			 */

			writeH(0);

		} else if (name.equalsIgnoreCase("ī��")) {
			writeC(0x3a);
			writeC(0x02);

			writeC(0x08);
			writeC(0x01);

			writeH(0);
		}
	}

	public S_NewCreateItem(int type, String s1, int objid, String s2, int gfxid) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(type);
		StringTokenizer st = new StringTokenizer(s1);

		while (st.hasMoreTokens()) {
			writeC(Integer.parseInt(st.nextToken(), 16));
		}

		byteWrite(objid);

		st = new StringTokenizer(s2);

		while (st.hasMoreTokens()) {
			writeC(Integer.parseInt(st.nextToken(), 16));
		}
		byteWrite(gfxid);
		writeH(0);
	}

	public S_NewCreateItem(String s) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		StringTokenizer st = new StringTokenizer(s);
		while (st.hasMoreTokens()) {
			writeC(Integer.parseInt(st.nextToken(), 16));
		}
	}

	/*
	 * 1470=���� 1471=�ݳ� 1472=����� 1473=���ɷκ� 1474=���� 1475=���� 1476=Ÿ�κ� 1477=Ÿ������
	 * 1478=Ÿ�� 1479=Ÿ�� 1480=���� 1481=���� 1482=ī�ĸ��� 1483=�޸����� 1484=���͸��� 1485=��������
	 * 1486=���ɺ� 1487=�� 1488=ǳ 1489=ȭ 1490=6���� 1491=7���� 1492=8���� 1493=9����
	 */

	public S_NewCreateItem(int skillId, boolean on, int classId, long time) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(0x6E);
		writeC(0x08);
		writeC(on ? 2 : 3);
		writeC(0x10);
		byteWrite(skillId);
		writeC(0x18);
		if (time < 0) {
			byte[] minus = { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
					(byte) 0xff, (byte) 0x01 };
			writeByte(minus);
		} else
			byteWrite(time);
		writeC(0x20);
		writeC(0x08);
		writeC(0x28);
		if (skillId == L1SkillId.RANKING_BUFF_4) {
			byteWrite(7096);
		} else if (skillId == L1SkillId.RANKING_BUFF_3) {
			byteWrite(7095);
		} else if (skillId == L1SkillId.RANKING_BUFF_2) {
			byteWrite(7094);
		} else if (skillId == L1SkillId.RANKING_BUFF_1) {
			byteWrite(7093);
		}
		writeH(0x0030);
		writeC(0x38);
		writeC(0x03);
		writeC(0x40);

		int msgNum = 0;

		if (skillId == L1SkillId.RANKING_BUFF_4)
			msgNum = 4572;
		else if (skillId == L1SkillId.RANKING_BUFF_3)
			msgNum = 4571;
		else if (skillId == L1SkillId.RANKING_BUFF_2)
			msgNum = 4570;
		else if (skillId == L1SkillId.RANKING_BUFF_1)
			msgNum = 4569;

		if (classId == 2) {
			msgNum += 4;
		} else if (classId == 3 || classId == 6) {
			msgNum += 8;
		}

		byteWrite(msgNum);

		writeC(0x48);
		writeC(0x00);
		writeH(0x0050);
		writeC(0x58);
		writeC(0x01);
		writeC(0x60);
		writeC(0x00);
		writeC(0x68);
		writeC(0x00);
		writeC(0x70);
		writeC(0x00);
		writeH(0x00);
	}

	/** ���۸���Ʈ �ڵ� **/
	private int createCode(int itemid) {
		switch (itemid) {
		/*
		 * 1719 2�� �ֹ��� 1720 3�� 1721 4 1722 5 1723 6 1724 7 1725 8 1726 9 1727 10
		 * 1728 ����
		 */
		case 40033:
			return 2195;
		case 40034:
			return 2196;
		case 40035:
			return 2197;
		case 40036:
			return 2198;
		case 40037:
			return 2199;
		case 40038:
			return 2200;

		case 500207:
			return 934;
		case 40104:
			return 1719;// 2���ֹ���
		case 40105:
			return 1720;// 3���ֹ���
		case 40106:
			return 1721;// 4���ֹ���
		case 40107:
			return 1722;// 5���ֹ���
		case 40108:
			return 1723;// 6���ֹ���
		case 40109:
			return 1724;// 7���ֹ���
		case 40110:
			return 1725;// 8���ֹ���
		case 40111:
			return 1726;// 9���ֹ���
		case 40112:
			return 1727;// 10���ֹ���
		case 40113:
			return 1728;// ���� �ֹ���

		/*
		 * 1729 ���� 1�� ���� 1730 2�� 1731 3 1732 4 1733 5 1734 6 1735 7 1736 8 1737
		 * 9 1738 10
		 */

		case 5000100:
			return 1729;// 1
		case 5000101:
			return 1730;// 2
		case 5000102:
			return 1731;// 3
		case 5000103:
			return 1732;// 4
		case 5000104:
			return 1733;// 5
		case 5000105:
			return 1734;// 6
		case 5000106:
			return 1735;// 7
		case 5000107:
			return 1736;// 8
		case 5000108:
			return 1737;// 9
		case 5000109:
			return 1738;// 10
		/*
		 * 1739 ȥ�� 1�� ���� 1740 2�� 1741 3 1742 4 1743 5 1744 6 1745 7 1746 8 1747
		 * 9 1748 10
		 */
		case 5000110:
			return 1739;// 1
		case 5000111:
			return 1740;// 2
		case 5000112:
			return 1741;// 3
		case 5000113:
			return 1742;// 4
		case 5000114:
			return 1743;// 5
		case 5000115:
			return 1744;// 6
		case 5000116:
			return 1745;// 7
		case 5000117:
			return 1746;// 8
		case 5000118:
			return 1747;// 9
		case 5000119:
			return 1748;// 10

		case 20050:
			return 1470;// ����
		case 20049:
			return 1471;// �ݳ�
		case 20057:
			return 1472;// ���
		case 20109:
			return 1473;// ����
		case 20200:
			return 1474;// ����
		case 20178:
			return 1475;// ����
		case 20152:
			return 1476;// Ÿ��
		case 20076:
			return 1477;// Ÿ��
		case 20186:
			return 1478;// Ÿ��
		case 20216:
			return 1479;// Ÿ��
		case 21167:
			return 1480;// ����
		case 21168:
			return 1481;// ����

		case 20040:
			return 1482;// ī�ĸ�
		case 20018:
			return 1483;// �޸���
		case 20025:
			return 1484;// ���͸�
		case 20029:
			return 1485;// ������

		case 50749:
			return 1486;// ��
		case 50747:
			return 1487;// ��
		case 50748:
			return 1488;// ǳ
		case 50750:
			return 1489;// ȭ

		/*
		 * case 50750: return 1490;//6�Ÿ��� case 50750: return 1491;//7�Ÿ��� case
		 * 50750: return 1492;//8�Ÿ��� case 50750: return 1493;//9�Ÿ���
		 */
		case 130220:
			return 1716;// �ݺ��� �尩
		case 90084:
			return 1717;// �������� ü�μҵ�
		case 90083:
			return 1715;// ��ȿ�� �̵���

		case 7227:
			return 1158;// ������ǵ���
		case 7225:
			return 1159;// �����ǵ���
		case 7245:
			return 1210;// ������ �尩
		case 7246:
			return 1211;// ������ �������尩

		case 7252:
			return 1217;
		case 7253:
			return 1218;
		case 7254:
			return 1219;
		case 7255:
			return 1220;
		case 7256:
			return 1221;

		case 60502:
			return 938;// ǳ���� ����:3��
		case 60503:
			return 939;// ������ ����:35��
		case 60504:
			return 940;// ������ ����:120��
		case 60505:
			return 941;// ȭ���� ����:500��
		case 20301:
			return 2;// ü��������
		case 20265:
			return 3;// ��ȭ������
		case 20297:
			return 4;// ���ŷ�������
		case 40391:
			return 5;// ����
		case 40921:
			return 6;// ������������
		case 218:
			return 7;// �Ӱ�����ö��
		case 20396:
			return 8;// �ù����ǹ���
		case 210:
			return 9;// �ù�����â
		case 40623:
			return 10;// 1���밡��
		case 40624:
			return 14;// 2���밡��
		case 40625:
			return 18;// 3���밡��
		case 40626:
			return 22;// 4���밡��
		case 40627:
			return 26;// 5���밡��
		case 40628:
			return 30;// 6���밡��
		case 40417:
			return 34;// �����ǰ���
		case 185:
			return 35;// �Ǹ���ũ�ν�����
		case 63:
			return 36;// �Ǹ���Į
		case 85:
			return 37;// �Ǹ����̵���
		case 165:
			return 38;// �Ǹ���ũ�ο�
		case 20062:
			return 39;// �߷����ͺ�����
		case 40416:
			return 40;// ���ֹ�����
		case 40615:
			return 41;// �׽�2������
		case 40616:
			return 42;// �׽�3������
		case 40853:
			return 43;// ��Ÿ�ٵ��̵��ֹ���(����)
		case 40084:
			return 44;// ��Ƶ��̵��ֹ���
		case 40118:
		case 40120:
			return 45;// ��Ÿ�ٵ��̵��ֹ���(����)
		case 61:
			return 46;// ����Ȳ�������
		case 12:
			return 47;// ��Į
		case 86:
			return 48;// ����
		case 134:
			return 49;// ������
		case 20410:
			return 50;// ����Ȳ�� �尩
		case 20395:
			return 51;// ����Ȳ�� ����
		case 20408:
			return 52;// ����Ȳ�� ����
		case 20390:
			return 53;// ����Ȳ�� ����
		case 20402:
			return 54;// ����Ȳ�� ����
		case 40677:
			return 55;// ������ֱ�
		case 41027:
			return 56;// �ϼ� ��ٿ��缭
		case 164:
			return 57;// ��վ�
		case 84:
			return 58;// ��յ�
		case 189:
			return 59;// ��ձ�
		case 20235:
			return 60;// �����ǹ���
		case 410000:
			return 61;// ��ü
		case 410001:
			return 62;// ��ü
		case 6000:
			return 65;// ��ü
		case 410003:
			return 66;// �����̾�Ű��ũ
		case 410004:
			return 67;// ��伮Ű��ũ
		case 6001:
			return 68;// ������Ű��ũ
		case 40526:
			return 69;// �����Ǳ�
		case 40779:
			return 70;// ��ö��
		case 40443:
			return 71;// ���̽���
		case 40445:
			return 72;// ���̽����Ǳ�
		case 40467:
			return 73;// ����
		case 40469:
			return 74;// ���Ǳ�
		case 40488:
			return 75;// Ȳ�ݱ�
		case 40487:
			return 76;// Ȳ���Ǳ�
		case 40440:
			return 77;// ��ݱ�
		case 40439:
			return 78;// ����Ǳ�
		case 420100:
			return 79;// ��Ÿ�Ϸ�
		case 420112:
			return 80;// �߶�Ϸ�
		case 420104:
			return 81;// ��Ǫ�Ϸ�
		case 420108:
			return 82;// ����Ϸ�
		case 420103:
			return 83;// ��Ÿ����
		case 420115:
			return 84;// �߶󸶷�
		case 420107:
			return 85;// ��Ǫ����
		case 420111:
			return 86;// ���帶��
		case 420102:
			return 87;// ��Ÿ�γ�
		case 420114:
			return 88;// �߶��γ�
		case 420106:
			return 89;// ��Ǫ�γ�
		case 420110:
			return 90;// �����γ�
		case 420101:
			return 91;// ��Ÿ����
		case 420113:
			return 92;// �߶���
		case 420105:
			return 93;// ��Ǫ����
		case 420109:
			return 94;// ���忹��
		case 60386:
			return 95;// �˸������ָӴ�
		case 40318:
			return 96;// ����
		case 40319:
			return 97;// ���ɿ�
		case 40320:
			return 98;// �渶��
		case 40031:
			return 99;// �Ǹ�����
		case 21095:
			return 100;// ü���ǰ���
		case 21096:
			return 101;// �����ǰ���
		case 21097:
			return 102;// �����簡��
		case 430106:
			return 103;// ���渶��
		case 430104:
			return 104;// ���渶��
		case 430107:
			return 105;// ȭ�渶��
		case 430105:
			return 106;// ǳ�渶��
		case 430108:
			return 107;// ź������
		case 430109:
			return 108;// ���󸶾�
		case 430110:
			return 109;// ������

		case 41916:
			return 110;// ������
		case 41917:
			return 111;// ����Ȱ
		case 41918:
			return 112;// ����������
		case 41920:
			return 113;// ����ũ�ο�
		case 41921:
			return 114;// ����ü�μҵ�
		case 41919:
			return 115;// ����Ű��ũ
		case 60041:
			return 116;// 7����
		case 60042:
			return 117;// 7��ǳ
		case 60043:
			return 118;// 7�Ĵ�
		case 60044:
			return 119;// 7õ��
		case 60045:
			return 120;// 7Ȥ����â
		case 60046:
			return 121;// 7����
		case 60047:
			return 122;// 7��õ
		case 60048:
			return 123;// 8����
		case 60049:
			return 124;// 8��ǳ
		case 60050:
			return 125;// 8�Ĵ�
		case 60051:
			return 126;// 8õ��
		case 60052:
			return 127;// 8Ȥ����â
		case 60053:
			return 384;// 8����
		case 60054:
			return 385;// 8��õ
		case 49072:
			return 392;// ���󵿻�
		case 49073:
			return 393;// �巹��ũ����
		case 49074:
			return 394;// ��ŵ���
		case 49075:
			return 395;// ���̸�������
		case 49076:
			return 396;// �׹̳뵿��
		case 20083:
			return 397;// ��������
		case 20131:
			return 398;// ���� ����
		case 20069:
			return 399;// ������ ����
		case 20179:
			return 400;// �������尩
		case 20209:
			return 401;// �����Ǻ���
		case 20290:
			return 402;// �����ǹ���
		case 20261:
			return 403;// �����Ǹ����
		case 20031:
			return 404;// ����������
		case 196:
			return 405;// �ٸ���1
		case 197:
			return 406;// �ٸ���2
		case 198:
			return 407;// �ٸ���3
		case 199:
			return 408;// �ٸ���4
		case 200:
			return 409;// �ٸ���5
		case 201:
			return 410;// �ٸ���6
		case 202:
			return 411;// �ٸ���7
		case 203:
			return 412;// �ٸ���8
		case 420000:
			return 413;// ����
		case 420003:
			return 414;// ���簡��
		case 60387:
			return 415;// 7ȯü
		case 60388:
			return 416;// 8ȯü
		case 60389:
			return 417;// 7��Ű
		case 60390:
			return 418;// 8��Ű
		case 40502:
			return 419;// ��
		case 40504:
			return 420;// �ƶ�ũ���ǰŹ���
		case 40495:
			return 421;// �̽�����
		case 40497:
			return 422;// �̽����Ǳ�
		case 40509:
			return 423;// �����Ϸ����Ǳ�
		case 88:
			return 424;// ���ǻ�
		case 40521:
			return 425;// ���ǳ���
		case 40494:
			return 426;// �̽���
		case 40508:
			return 427;// �����Ϸ���
		case 180:
			return 428;// ũ�ν�����
		case 181:
			return 429;// ���
		case 22:
			return 430;// ���Ϻ극��Ŀ
		case 42:
			return 431;// �����Ǿ�
		case 20033:
			return 432;// �������ູ
		case 20137:
			return 433;// �������罽����
		case 20138:
			return 434;// �������Ǳݰ���
		case 20073:
			return 435;// ����������
		case 20191:
			return 436;// Ȱ��
		case 20187:
			return 437;// �Ŀ��۷κ�
		case 20236:
			return 438;// ����������
		case 40520:
			return 439;// ������Ʈ
		case 40746:
			return 440;// �̽���ȭ��
		case 40748:
			return 441;// �����Ϸ���ȭ��
		case 40493:
			return 442;// �����÷�
		case 40068:
			return 443;// �������
		case 40506:
			return 444;// ��Ʈ�ǿ���
		case 20003:
			return 445;// ��ö�鰩
		case 20091:
			return 446;// ��ö�Ǳݰ���
		case 20163:
			return 447;// ��ö�尩
		case 20194:
			return 448;// ��ö����
		case 20220:
			return 449;// ��ö����
		case 6003:
			return 450;// ��������
		case 6004:
			return 451;// ���Ѱ���
		case 6005:
			return 452;// ���Ѻ���
		case 49015:
			return 453;// ���̽������
		case 121:
			return 454;// ����
		case 20085:
			return 455;// Ƽ����
		case 20056:
			return 456;// ��������
		case 20063:
			return 457;// ��ȣ����
		case 57:
			return 458;// �ο�
		case 20146:
			return 459;// �����ð���
		case 20127:
			return 460;// �����ð���
		case 20156:
			return 461;// ǳ���ð���
		case 20159:
			return 462;// ȭ���ð���
		case 20130:
			return 463;// ��Ÿ������(����)
		case 20153:
			return 464;// ��Ǫ������
		case 20108:
			return 465;// ���帶����
		case 20119:
			return 466;// �߶󸶰���
		case 190:
			return 467;// ��������Ȱ
		case 120187:
			return 468;// Ÿ�����尩
		case 120194:
			return 469;// Ÿ���Ǻ���
		case 21166:
			return 470;// �븶�����Ǹ���
		case 158:
			return 475;// �����ũ�ο�
		case 75:
			return 476;// ������̵���
		case 168:
			return 477;// �����Ȱ
		case 162:
			return 478;// ��ũ
		case 81:
			return 479;// ����
		case 177:
			return 480;// ��Ȱ
		case 157:
			return 481;// ������ũ�ο�
		case 74:
			return 482;// ����
		case 40747:
			return 483;// ���ȭ��
		case 40162:
			return 484;// ���Ǽ���
		case 40413:
			return 485;// ������
		case 40169:
			return 486;// �巹��ũ����
		case 40409:
			return 487;// �һ��Ǽ���
		case 20309:
			return 488;// �����½ź�
		case 20311:
			return 489;// ����������
		case 20310:
			return 490;// �����¿���
		case 40018:
			return 491;// ��ȭ�ͱ�
		case 40428:
			return 492;// �޺��Ǵ���
		case 40525:
			return 493;// �׶�ī���Ǵ���
		case 40227:
			return 494;// �۷��׿��� ����
		case 40229:
			return 495;// ���̴׽ǵ� ����
		case 40230:
			return 496;// �극�̺��Ż ����
		case 40231:
			return 497;// ��Ŭ�� ����
		case 40208:
			return 498;// ��������Ʈ�� ����
		case 119:
			return 499;// ������������
		case 124:
			return 500;// ����������
		case 123:
			return 501;// ��������������
		case 427104:
			return 502;// ��ɰ��̻�
		case 427109:
			return 503;// �¸����̻�
		case 427103:
			return 504;// Ȳ�����̻�
		case 427107:
			return 505;// �Ÿ����̻�
		case 427101:
			return 506;// �ĸ����̻�
		case 427003:
			return 507;// ������Ƹ�
		case 427006:
			return 508;// ũ�ν���Ƹ�
		case 427007:
			return 509;// ü����Ƹ�
		case 427005:
			return 510;// �̽�����Ƹ�
		case 40406:
			return 511;// �������

		case 40404:
			return 640;// ����������(����)
		case 40402:
			return 641;// ����������(����)
		case 40403:
			return 642;// ����������(�ٶ�)
		case 40401:
			return 643;// ����������(��ȭ)
		case 20054:
			return 644;// �����Ǹ���
		case 20059:
			return 645;// �����Ǹ���
		case 20061:
			return 646;// �ٶ��Ǹ���
		case 20071:
			return 647;// ��ȭ�Ǹ���
		case 550001:
			return 648;// �������ǹ���
		case 550002:
			return 649;// ���������ǹ���
		case 550003:
			return 650;// ���������ǹ���
		case 550004:
			return 651;// ���ึ���ǹ���
		case 550000:
			return 652;// ����ӵ��ǹ���
		case 550005:
			return 653;// ����ȣ���ǹ���
		case 550006:
			return 654;// ���ຯ���ǹ���
		case 21169:
			return 655;// +0�긶���Ǳݰ���
		case 21170:
			return 656;// +0�긶�Ǻ�ð���
		case 21171:
			return 657;// +0�긶�ǰ��װ���
		case 21172:
			return 658;// +0�긶�Ƿκ갩��
		/*
		 * 659 +1�긶���Ǳݰ��� 660 +1�긶�Ǻ�ð��� 661 +1�긶�ǰ��װ��� 662 +1�긶�Ƿκ갩�� 663 +2�긶���Ǳݰ���
		 * ~ 670 +3�긶�Ƿκ갩��
		 */
		case 60126:
			return 671;// 7�ı���ũ�ο�
		case 60127:
			return 672;// 8�ı���ũ�ο�
		case 60128:
			return 673;// 7�ı����̵���
		case 60129:
			return 674;// 8�ı����̵���
		case 40925:
			return 685;// ��ȭ�ǹ���
		case 21006:
			return 686;// ��ȥ�� �Ͱ��� ���
		case 21009:
			return 687;// ������ �Ͱ���
		case 21012:
			return 688;// ���� �Ͱ���
		case 21013:
			return 689;// ������ �Ͱ���
		case 21007:
			return 690;// ��ȥ�� �Ͱ��� �ٿ�
		case 21008:
			return 691;// �г��ǱͰ���
		case 21010:
			return 692;// ����ǱͰ���
		case 21011:
			return 693;// �һ��ǱͰ���
		case 21014:
			return 694;// ��ȥ�� �Ͱ��� ����
		case 21015:
			return 695;// �����ǱͰ���
		case 21016:
			return 696;// �����ǱͰ���
		case 21017:
			return 697;// �����ǱͰ���
		case 20435:
			return 698;// ���������ɹ���
		case 20439:
			return 699;// ���������ɹ���
		case 20443:
			return 700;// ���������ɹ���
		case 20447:
			return 701;// ���������ɹ���
		case 20436:
			return 702;// �����Ǽ��ɹ���
		case 20440:
			return 703;// �����Ǽ��ɹ���
		case 20444:
			return 704;// �����Ǽ��ɹ���
		case 20448:
			return 705;// �����Ǽ��ɹ���
		case 20438:
			return 706;// ������ǳ�ɹ���
		case 20442:
			return 707;// ������ǳ�ɹ���
		case 20446:
			return 708;// ������ǳ�ɹ���
		case 20450:
			return 709;// ������ǳ�ɹ���
		case 20437:
			return 710;// ������ȭ�ɹ���
		case 20441:
			return 711;// ������ȭ�ɹ���
		case 20445:
			return 712;// ������ȭ�ɹ���
		case 20449:
			return 713;// ������ȭ�ɹ���
		case 60423:
			return 741;// ���� ȣ�ڼ�
		case 60486:
			return 929;// ���뽺����
		case 60487:
			return 930;// ���뽺����
		case 60488:
			return 931;// ���뽺����
		case 60489:
			return 932;// ���뽺����
		case 1437009:
			return 1008;// �ູ���� ���
		case 7322:
			return 1009;// �巡���ǿ��ݼ����
		case 7251:
			return 1160;// ���������������Ե���

		case 40722:
			return 2183;// ��ȣ��
		case 435000:
			return 2184;// ȣ������
		case 21269:
			return 2185;// ȣ�� �����

		case 90085:
			return 15776;// ��հ�
		case 90086:
			return 15777;// ������
		case 90087:
			return 15778;// �Ѽհ�
		case 90088:
			return 15779;// Ȱ
		case 90089:
			return 15780;// �̵���
		case 90090:
			return 15781;// ü�μҵ�
		case 90091:
			return 15782;// Ű��ũ
		case 90092:
			return 15783;// ����
		default:
			return 0;
		}
	}

	public static int createItemByCode(int code) {
		switch (code) {

		case 5555:  return 8020;  //-- ȯ���� ����
		case 5608:  return 8022;  //-- ȯ���� ���� �ָӴ�
		case 5547:  return 40048;  //-- ��޴��̾�
		case 5548:  return 40051;  //-- ��޿��޶���
		case 5549:  return 40049;  //-- ��޷��
		case 5550:  return 40050;  //-- ��޻���
		case 5551:  return 40052;  //-- �ְ�޴��̾Ƹ��
		case 5552:  return 40055;  //-- ��޿��޶���
		case 5553:  return 40053;  //-- �ְ�޷��
		case 5554:  return 40054;  //-- �ְ�޻���
		case 5604:  return 6010;  //-- ����Ƽ�ƶ����
		case 5605:  return 6012;  //-- �����ǻ��޿���
		case 5606:  return 437010;  //-- �巡���Ǵ��̾Ƹ��
		case 5607:  return 437009;  //-- �巡���Ǻ�������

		case 2195:
			return 40033;
		case 2196:
			return 40034;
		case 2197:
			return 40035;
		case 2198:
			return 40036;
		case 2199:
			return 40037;
		case 2200:
			return 40038;

		case 934:
			return 500207;
		case 2183:
			return 40722;// ��ȣ��
		case 2184:
			return 435000;// ȣ������
		case 2185:
			return 21269;// ȣ�� �����

		case 15776:
			return 90085;// ��հ�
		case 15777:
			return 90086;// ������
		case 15778:
			return 90087;// �Ѽհ�
		case 15779:
			return 90088;// Ȱ
		case 15780:
			return 90089;// �̵���
		case 15781:
			return 90090;// ü�μҵ�
		case 15782:
			return 90091;// Ű��ũ
		case 15783:
			return 90092;// ����

		case 1719:
			return 40104;// 2���ֹ���
		case 1720:
			return 40105;// 3���ֹ���
		case 1721:
			return 40106;// 4���ֹ���
		case 1722:
			return 40107;// 5���ֹ���
		case 1723:
			return 40108;// 6���ֹ���
		case 1724:
			return 40109;// 7���ֹ���
		case 1725:
			return 40110;// 8���ֹ���
		case 1726:
			return 40111;// 9���ֹ���
		case 1727:
			return 40112;// 10���ֹ���
		case 1728:
			return 40113;// ���� �ֹ���

		/*
		 * 1729 ���� 1�� ���� 1730 2�� 1731 3 1732 4 1733 5 1734 6 1735 7 1736 8 1737
		 * 9 1738 10
		 */
		case 1729:
			return 5000100;// 1
		case 1730:
			return 5000101;// 2
		case 1731:
			return 5000102;// 3
		case 1732:
			return 5000103;// 4
		case 1733:
			return 5000104;// 5
		case 1734:
			return 5000105;// 6
		case 1735:
			return 5000106;// 7
		case 1736:
			return 5000107;// 8
		case 1737:
			return 5000108;// 9
		case 1738:
			return 5000109;// 10

		/*
		 * 1739 ȥ�� 1�� ���� 1740 2�� 1741 3 1742 4 1743 5 1744 6 1745 7 1746 8 1747
		 * 9 1748 10
		 */
		case 1739:
			return 5000110;// 1
		case 1740:
			return 5000111;// 2
		case 1741:
			return 5000112;// 3
		case 1742:
			return 5000113;// 4
		case 1743:
			return 5000114;// 5
		case 1744:
			return 5000115;// 6
		case 1745:
			return 5000116;// 7
		case 1746:
			return 5000117;// 8
		case 1747:
			return 5000118;// 9
		case 1748:
			return 5000119;// 10

		case 1716:
			return 130220;// �ݺ��� �尩
		case 1717:
			return 90084;// �������� ü�μҵ�
		case 1715:
			return 90083;// ��ȿ�� �̵���

		case 1470:
			return 20050;// ����
		case 1471:
			return 20049;// �ݳ�
		case 1472:
			return 20057;// ���
		case 1473:
			return 20109;// ����
		case 1474:
			return 20200;// ����
		case 1475:
			return 20178;// ����
		case 1476:
			return 20152;// Ÿ��
		case 1477:
			return 20076;// Ÿ��
		case 1478:
			return 20186;// Ÿ��
		case 1479:
			return 20216;// Ÿ��
		case 1480:
			return 21167;// ����
		case 1481:
			return 21168;// ����

		case 1482:
			return 20040;// ī�ĸ�
		case 1483:
			return 20018;// �޸���
		case 1484:
			return 20025;// ���͸�
		case 1485:
			return 20029;// ������

		case 1486:
			return 50749;// ��
		case 1487:
			return 50747;// ��
		case 1488:
			return 50748;// ǳ
		case 1489:
			return 50750;// ȭ

		case 1008:
			return 1437009;// ����
		case 1009:
			return 7322;// �巡���ǿ��ݼ����
		case 1158:
			return 7227;// ������ǵ���
		case 1159:
			return 7225;// �����ǵ���
		case 1210:
			return 7245;// ������ �尩
		case 1211:
			return 7246;// ������ �������尩

		case 1217:
			return 7252;
		case 1218:
			return 7253;
		case 1219:
			return 7254;
		case 1220:
			return 7255;
		case 1221:
			return 7256;

		case 471:
			return 20130;
		case 472:
			return 20153;
		case 473:
			return 20108;
		case 474:
			return 20119;
		case 47:
			return 12;
		case 430:
			return 22;
		case 431:
			return 42;
		case 458:
			return 57;
		case 46:
			return 61;
		case 36:
			return 63;
		case 482:
			return 74;
		case 476:
			return 75;
		case 479:
			return 81;
		case 58:
			return 84;
		case 37:
			return 85;
		case 48:
			return 86;
		case 424:
			return 88;
		case 499:
			return 119;
		case 454:
			return 121;
		case 501:
			return 123;
		case 500:
			return 124;
		case 49:
			return 134;
		case 481:
			return 157;
		case 475:
			return 158;
		case 478:
			return 162;
		case 57:
			return 164;
		case 38:
			return 165;
		case 477:
			return 168;
		case 480:
			return 177;
		case 428:
			return 180;
		case 429:
			return 181;
		case 35:
			return 185;
		case 59:
			return 189;
		case 467:
			return 190;
		case 405:
			return 196;
		case 406:
			return 197;
		case 407:
			return 198;
		case 408:
			return 199;
		case 409:
			return 200;
		case 410:
			return 201;
		case 411:
			return 202;
		case 412:
			return 203;
		case 9:
			return 210;
		case 7:
			return 218;
		case 65:
			return 6000;
		case 68:
			return 6001;
		case 450:
			return 6003;
		case 451:
			return 6004;
		case 452:
			return 6005;
		case 445:
			return 20003;
		case 404:
			return 20031;
		case 432:
			return 20033;
		case 456:
			return 20056;
		case 39:
			return 20062;
		case 457:
			return 20063;
		case 399:
			return 20069;
		case 435:
			return 20073;
		case 397:
			return 20083;
		case 455:
			return 20085;
		case 446:
			return 20091;
		case 465:
			return 20108;
		case 466:
			return 20119;
		case 460:
			return 20127;
		case 463:
			return 20130;
		case 398:
			return 20131;
		case 433:
			return 20137;
		case 434:
			return 20138;
		case 459:
			return 20146;
		case 464:
			return 20153;
		case 461:
			return 20156;
		case 462:
			return 20159;
		case 447:
			return 20163;
		case 400:
			return 20179;
		case 437:
			return 20187;
		case 436:
			return 20191;
		case 448:
			return 20194;
		case 401:
			return 20209;
		case 449:
			return 20220;
		case 60:
			return 20235;
		case 438:
			return 20236;
		case 403:
			return 20261;
		case 3:
			return 20265;
		case 402:
			return 20290;
		case 4:
			return 20297;
		case 2:
			return 20301;
		case 488:
			return 20309;
		case 490:
			return 20310;
		case 489:
			return 20311;
		case 53:
			return 20390;
		case 51:
			return 20395;
		case 8:
			return 20396;
		case 54:
			return 20402;
		case 52:
			return 20408;
		case 50:
			return 20410;
		case 100:
			return 21095;
		case 101:
			return 21096;
		case 102:
			return 21097;
		case 470:
			return 21166;
		case 491:
			return 40018;
		case 99:
			return 40031;
		case 443:
			return 40068;
		case 44:
			return 40084;
		case 43:
			return 40118;
		case 484:
			return 40162;
		case 486:
			return 40169;
		case 498:
			return 40208;
		case 494:
			return 40227;
		case 495:
			return 40229;
		case 496:
			return 40230;
		case 497:
			return 40231;
		case 26:
			return 40267;
		case 30:
			return 40268;
		case 96:
			return 40318;
		case 97:
			return 40319;
		case 98:
			return 40320;
		case 5:
			return 40391;
		case 511:
			return 40406;
		case 487:
			return 40409;
		case 485:
			return 40413;
		case 40:
			return 40416;
		case 34:
			return 40417;
		case 492:
			return 40428;
		case 78:
			return 40439;
		case 77:
			return 40440;
		case 71:
			return 40443;
		case 72:
			return 40445;
		case 73:
			return 40467;
		case 74:
			return 40469;
		case 76:
			return 40487;
		case 75:
			return 40488;
		case 442:
			return 40493;
		case 426:
			return 40494;
		case 421:
			return 40495;
		case 422:
			return 40497;
		case 419:
			return 40502;
		case 420:
			return 40504;
		case 444:
			return 40506;
		case 427:
			return 40508;
		case 423:
			return 40509;
		case 439:
			return 40520;
		case 425:
			return 40521;
		case 493:
			return 40525;
		case 69:
			return 40526;
		case 41:
			return 40615;
		case 42:
			return 40616;
		case 10:
			return 40623;
		case 14:
			return 40624;
		case 18:
			return 40625;
		case 22:
			return 40626;
		case 55:
			return 40677;
		case 440:
			return 40746;
		case 483:
			return 40747;
		case 441:
			return 40748;
		case 70:
			return 40779;
		case 6:
			return 40921;
		case 56:
			return 41027;
		case 110:
			return 41916;
		case 111:
			return 41917;
		case 112:
			return 41918;
		case 115:
			return 41919;
		case 113:
			return 41920;
		case 114:
			return 41921;
		case 1160:
			return 7251;// ���������������Ե���

		case 453:
			return 49015;
		case 392:
			return 49072;
		case 393:
			return 49073;
		case 394:
			return 49074;
		case 395:
			return 49075;
		case 396:
			return 49076;
		case 116:
			return 60041;
		case 117:
			return 60042;
		case 118:
			return 60043;
		case 119:
			return 60044;
		case 120:
			return 60045;
		case 121:
			return 60046;
		case 122:
			return 60047;
		case 123:
			return 60048;
		case 124:
			return 60049;
		case 125:
			return 60050;
		case 126:
			return 60051;
		case 127:
			return 60052;
		case 384:
			return 60053;
		case 385:
			return 60054;
		case 468:
			return 120187;
		case 469:
			return 120194;
		case 61:
			return 410000;
		case 62:
			return 410001;
		case 66:
			return 410003;
		case 67:
			return 410004;
		case 413:
			return 420000;
		case 414:
			return 420003;
		case 79:
			return 420100;
		case 91:
			return 420101;
		case 87:
			return 420102;
		case 83:
			return 420103;
		case 81:
			return 420104;
		case 93:
			return 420105;
		case 89:
			return 420106;
		case 85:
			return 420107;
		case 82:
			return 420108;
		case 94:
			return 420109;
		case 90:
			return 420110;
		case 86:
			return 420111;
		case 80:
			return 420112;
		case 92:
			return 420113;
		case 88:
			return 420114;
		case 84:
			return 420115;
		case 507:
			return 427003;
		case 510:
			return 427005;
		case 508:
			return 427006;
		case 509:
			return 427007;
		case 506:
			return 427101;
		case 504:
			return 427103;
		case 502:
			return 427104;
		case 505:
			return 427107;
		case 503:
			return 427109;
		case 104:
			return 430104;
		case 106:
			return 430105;
		case 103:
			return 430106;
		case 105:
			return 430107;
		case 107:
			return 430108;
		case 108:
			return 430109;
		case 109:
			return 430110;

		case 95:
			return 60386;
		case 415:
			return 60387;
		case 416:
			return 60388;
		case 417:
			return 60389;
		case 418:
			return 60390;

		case 640:
			return 40404;
		case 641:
			return 40402;
		case 642:
			return 40403;
		case 643:
			return 40401;
		case 644:
			return 20054;
		case 645:
			return 20059;
		case 646:
			return 20061;
		case 647:
			return 20071;
		case 648:
			return 550001;
		case 649:
			return 550002;
		case 650:
			return 550003;
		case 651:
			return 550004;
		case 652:
			return 550000;
		case 653:
			return 550005;
		case 654:
			return 550006;
		case 655:
			return 21169;
		case 656:
			return 21170;
		case 657:
			return 21171;
		case 658:
			return 21172;
		case 671:
			return 60126;
		case 672:
			return 60127;
		case 673:
			return 60128;
		case 674:
			return 60129;
		case 685:
			return 40925;
		case 686:
			return 21006;
		case 687:
			return 21009;
		case 688:
			return 21012;
		case 689:
			return 21013;
		case 690:
			return 21007;
		case 691:
			return 21008;
		case 692:
			return 21010;
		case 693:
			return 21011;
		case 694:
			return 21014;
		case 695:
			return 21015;
		case 696:
			return 21016;
		case 697:
			return 21017;
		case 698:
			return 20435;
		case 699:
			return 20439;
		case 700:
			return 20443;
		case 701:
			return 20447;
		case 702:
			return 20436;
		case 703:
			return 20440;
		case 704:
			return 20444;
		case 705:
			return 20448;
		case 706:
			return 20438;
		case 707:
			return 20442;
		case 708:
			return 20446;
		case 709:
			return 20450;
		case 710:
			return 20437;
		case 711:
			return 20441;
		case 712:
			return 20445;
		case 713:
			return 20449;
		case 741:
			return 60423;// ���� ȣ�ڼ�
		case 742:
			return 212551;// ���� ȣ�ڰ��� (ȣ�ڼ�)
		case 731:
			return 212552;// ���� ȣ�ڰ��� (8����)
		case 732:
			return 212553;// ���� ȣ�ڰ��� (8��)
		case 733:
			return 212554;// ���� ȣ�ڰ��� (9����)
		case 734:
			return 212555;// ���� ȣ�ڰ��� (9��)
		case 735:
			return 212556;// ���� ȣ�ڰ��� (10��)
		case 736:
			return 212557;// ���� ȣ�ڰ��� (10��)
		case 743:
			return 60443;// ũ�������� ��Ű
		case 744:
			return 60439;// ���� ����(���¼ַ�)
		case 745:
			return 60440;// ���� ����(�ַ���ǰ��)
		case 746:
			return 60441;// ���� ����(�����Ǽַκδ�)
		case 747:
			return 60442;// ���� ����(�ַ�õ��Ŀ������)
		case 759:
			return 60474;// ������ �������
		case 929:
			return 60486;// ���뽺����
		case 930:
			return 60487;// ���뽺����
		case 931:
			return 60488;// ���뽺����
		case 932:
			return 60489;// ���뽺����
		case 938:
			return 60502;// ǳ���� ����:3��
		case 939:
			return 60503;// ������ ����:35��
		case 940:
			return 60504;// ������ ����:120��
		case 941:
			return 60505;// ȭ���� ����:500��
		default:
			return 0;
		}
	}

	public S_NewCreateItem(L1PcInstance pc, int type, int chat_type, String chat_text, String target_name) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);

		switch (type) {
		case 3:
			writeC(0x03);
			break;
		case 4:
			writeC(0x04);
			break;
		}

		writeC(0x02);
		writeC(0x08);
		writeC(0x00);
		writeC(0x10);
		writeC(chat_type);

		writeC(0x1a);
		byte[] text_byte = chat_text.getBytes();
		writeC(text_byte.length);
		writeByte(text_byte);

		switch (type) {
		case 3:
			writeC(0x22);

			if (chat_type == 0) {
				writeC(0x00);
				writeC(0x28);
				writeC(0x00);
				writeC(0x30);
				writeC(0x18);
			} else if (chat_type == 1) {
				byte[] name_byte = target_name.getBytes();
				writeC(name_byte.length);
				writeByte(name_byte);
				writeC(0x30);
				writeC(0x00);
			}
			break;
		case 4:
			writeC(0x2a);
			try {
				if (pc.isGm() && chat_type == 3) {
					byte[] name = "******".getBytes("MS949");
					writeC(name.length);
					writeByte(name);
				} else if (pc.getAge() != 0 && chat_type == 4) {// ���� �ǰ� ���մµ�...
					String names = pc.getName() + "(" + pc.getAge() + ")";
					byte[] name = names.getBytes("MS949");
					writeC(name.length);
					writeByte(name);
				} else {
					byte[] name = pc.getName().getBytes("MS949");
					writeC(name.length);
					writeByte(name);
				}
			} catch (UnsupportedEncodingException e) {
			}
			if (chat_type == 0) {
				writeC(0x38);
				byteWrite(pc.getId());
				writeC(0x40);
				byteWrite(pc.getX());
				writeC(0x48);
				byteWrite(pc.getY());
			}
			break;
		}
		L1UserRanking rank = UserRankingController.getInstance().getTotalRank(pc.getName());

		if (rank != null) {
			int uRank = rank.getCurRank();
			int number = 0;
			if (uRank >= 1 && uRank <= 10) {
				number = 4;
			} else if (uRank >= 11 && uRank <= 30) {
				number = 3;
			} else if (uRank >= 31 && uRank <= 60) {
				number = 2;
			} else if (uRank >= 61 && uRank <= 100) {
				number = 1;
			}
			writeC(0x50);
			writeC(number);
		}

		writeH(0x00);
	}

	public S_NewCreateItem(L1PcInstance pc, int chat_types, String s) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF); // 7���ڴ� 36
		writeC(0x04);
		writeC(0x02);
		writeC(0x08);
		writeC(0x39);
		writeC(0x10);
		writeC(chat_types);
		writeC(0x1A);
		byte[] chat_byte = s.getBytes();
		writeC(chat_byte.length);
		writeByte(chat_byte);
		writeC(0x2A);
		byte[] name = pc.getName().getBytes();
		writeC(name.length);
		writeByte(name);
		writeC(0x28);
		writeC(0x22);
		writeC(0x50);
		L1UserRanking rank = UserRankingController.getInstance().getTotalRank(pc.getName());
		if (rank != null) {
			int uRank = rank.getCurRank();
			if (uRank >= 1 && uRank <= 10)
				writeC(0x04);
			else if (uRank >= 11 && uRank <= 30)
				writeC(0x03);
			else if (uRank >= 31 && uRank <= 60)
				writeC(0x02);
			else if (uRank >= 61 && uRank <= 100)
				writeC(0x01);
		} else {
			writeC(0x00);
		}

		if (chat_types == 0) {
			writeC(0x38);// ������??
			writeK(pc.getId());
			writeC(0x40);
			writeK(pc.getX());
			writeC(0x48);
			writeK(pc.getY());
		}
		writeH(0x00);
	}

	public S_NewCreateItem(int type, byte[] chat, int count, String targetNam1, L1PcInstance pc) {

		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0x03);
		writeC(0x02);
		writeC(0x08);
		writeK(count); // ī��Ʈ

		writeC(0x10);
		writeC(type); // Ÿ��?

		writeC(0x1a);
		writeC(chat.length);
		writeByte(chat);

		writeC(0x22);
		if (type == 1) {
			try {
				byte[] name = targetNam1.getBytes("EUC-KR");
				writeC(name.length);
				writeByte(name);
			} catch (UnsupportedEncodingException e) {
			}
		} else {
			writeC(0);
		}
		writeC(0x28);
		writeC(0x00);
		writeC(0x30);
		writeC(0x00);
		writeC(0x50);
		L1UserRanking rank = UserRankingController.getInstance().getTotalRank(pc.getName());
		if (rank != null) {
			int uRank = rank.getCurRank();
			if (uRank >= 1 && uRank <= 10)
				writeC(0x04);
			else if (uRank >= 11 && uRank <= 30)
				writeC(0x03);
			else if (uRank >= 31 && uRank <= 60)
				writeC(0x02);
			else if (uRank >= 61 && uRank <= 100)
				writeC(0x01);
		} else {
			writeC(0x00);
		}

		writeH(0);

	}

	public S_NewCreateItem(int type, byte[] chat, int count, String targetName) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0x03);
		writeC(0x02);
		writeC(0x08);
		writeK(count); // ī��Ʈ

		writeC(0x10);
		writeC(type); // Ÿ��?

		writeC(0x1a);
		writeC(chat.length);
		writeByte(chat);

		writeC(0x22);
		if (type == 1) {
			try {
				byte[] name = targetName.getBytes("EUC-KR");
				writeC(name.length);
				writeByte(name);
			} catch (UnsupportedEncodingException e) {
			}
		} else {
			writeC(0);
		}

		writeC(0x28);
		writeC(0x00);
		writeC(0x30);
		writeC(0x00);
		writeC(0x50);
		L1UserRanking rank = UserRankingController.getInstance().getTotalRank(targetName);
		if (rank != null) {
			int uRank = rank.getCurRank();
			if (uRank >= 1 && uRank <= 10)
				writeC(0x04);
			else if (uRank >= 11 && uRank <= 30)
				writeC(0x03);
			else if (uRank >= 31 && uRank <= 60)
				writeC(0x02);
			else if (uRank >= 61 && uRank <= 100)
				writeC(0x01);
		} else {
			writeC(0x00);
		}

		writeH(0);

	}

	public S_NewCreateItem(int type, byte[] chat, int count, L1PcInstance pc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(0x04);
		writeC(0x02);
		writeC(0x08);
		writeK(count); // ī��Ʈ
		writeC(0x10);
		writeC(type); // Ÿ��?
		writeC(0x1a);
		writeC(chat.length);
		writeByte(chat);
		writeC(0x2a);
		try {
			if (pc.isGm() && type == 3) {
				byte[] name = "******".getBytes("EUC-KR");
				writeC(name.length);
				writeByte(name);
			} else if (pc.getAge() != 0 && type == 4) {
				String names = pc.getName() + "(" + pc.getAge() + ")";
				byte[] name = names.getBytes("EUC-KR");
				writeC(name.length);
				writeByte(name);
			} else {
				byte[] name = pc.getName().getBytes("EUC-KR");
				writeC(name.length);
				writeByte(name);
			}
		} catch (UnsupportedEncodingException e) {
		}
		writeC(0x28);
		writeC(0x22);
		writeC(0x50);
		L1UserRanking rank = UserRankingController.getInstance().getTotalRank(pc.getName());
		if (rank != null) {
			int uRank = rank.getCurRank();
			if (uRank >= 1 && uRank <= 10)
				writeC(0x04);
			else if (uRank >= 11 && uRank <= 30)
				writeC(0x03);
			else if (uRank >= 31 && uRank <= 60)
				writeC(0x02);
			else if (uRank >= 61 && uRank <= 100)
				writeC(0x01);
		} else {
			writeC(0x00);
		}

		if (type == 0) {
			writeC(0x38);// ������??
			writeK(pc.getId());
			writeC(0x40);
			writeK(pc.getX());
			writeC(0x48);
			writeK(pc.getY());
		}

		writeH(0);

	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return S_NEWCREATEITEM;
	}
}
