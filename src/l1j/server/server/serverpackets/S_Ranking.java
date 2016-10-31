package l1j.server.server.serverpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.utils.SQLUtil;

public class S_Ranking extends ServerBasePacket {

	private static final String S_Ranking = "[S] S_Ranking";

	private static Logger _log = Logger.getLogger(S_Ranking.class.getName());

	private byte[] _byte = null;
	private int j = 0;
	static String[] name;

	public S_Ranking(L1PcInstance pc, int number) {
		name = new String[10];
		buildPacket(pc, number);
	}

	public S_Ranking(L1NpcInstance board) {
		buildPacket(board);
	}

	public S_Ranking(String text) {
		buildPacket(text);
	}

	public S_Ranking(int map, int count, String text) {
		String date = time();
		writeC(Opcodes.S_BOARD_READ);
		writeD(0);
		writeS("����Ʈ");
		writeS("Map : " + map + " ������ : " + count);
		writeS(date);
		writeS(text);
	}

	private void buildPacket(L1NpcInstance board) {
		int count = 0;
		String[][] db = null;
		int[] id = null;
		db = new String[8][3];
		id = new int[8];
		while (count < 8) {
			id[count] = count + 1;
			db[count][0] = "";// Ranking
			db[count][1] = "";
			count++;
		}
		db[0][2] = "  == ��ü ��ŷ ==";
		db[1][2] = "  == ���� ��ŷ ==";
		db[2][2] = "  == ��� ��ŷ ==";
		db[3][2] = "  == ���� ��ŷ ==";
		db[4][2] = "  == ���� ��ŷ ==";
		db[5][2] = "  == �ٿ� ��ŷ ==";
		db[6][2] = "  == ���� ��ŷ ==";
		db[7][2] = "  == ȯ���� ��ŷ ==";
		// db[8][2] = "1. ��ü ��ŷ";

		writeC(Opcodes.S_BOARD_LIST);
		writeC(0);
		writeD(board.getId());
		writeC(0xFF); // ?
		writeC(0xFF); // ?
		writeC(0xFF); // ?
		writeC(0x7F); // ?
		writeH(8);
		writeH(300);
		for (int i = 0; i < 8; ++i) {
			writeD(id[i]);
			writeS(db[i][0]);
			writeS(db[i][1]);
			writeS(db[i][2]);
		}
		db = null;
		id = null;
	}

	private void buildPacket(L1PcInstance pc, int number) {
		String date = time();
		String type = null;
		String title = null;
		writeC(Opcodes.S_BOARD_READ);
		writeD(number);
		writeS("���");
		switch (number) {
		case 1:
			title = "��ü ��ŷ";
			break;
		case 2:
			title = "���� ��ŷ";
			break;
		case 3:
			title = "��� ��ŷ";
			break;
		case 4:
			title = "���� ��ŷ";
			break;
		case 5:
			title = "���� ��ŷ";
			break;
		case 6:
			title = "�ٿ� ��ŷ";
			break;
		case 7:
			title = "���� ��ŷ";
			break;
		case 8:
			title = "ȯ���� ��ŷ";
			break;
		case 9:
			title = "���� ��ŷ";
			break;
		}
		writeS(title);
		writeS(date);
		switch (pc.getType()) {
		case 0:
			type = "����";
			break;
		case 1:
			type = "���";
			break;
		case 2:
			type = "����";
			break;
		case 3:
			type = "������";
			break;
		case 4:
			type = "��ũ����";
			break;
		case 5:
			type = "����";
			break;
		case 6:
			type = "ȯ����";
			break;
		}
		int p = Rank(pc, number);
		if (number == 9) {
			writeS("\n\r\n\r\n\r" + pc.getName() + "���� ��ü��ŷ : " + p + "��" + "\n\r\n\r\n\r" + type + " Ŭ���� ��ŷ : " + j
					+ "��" + "\n\r\n\r\n\r" + "        ");

		} else {
			writeS("    1��  " + name[0] + "\n\r\n\r" + "   2��  " + name[1] + "\n\r\n\r" + "   3��  " + name[2]
					+ "\n\r\n\r" + "   4��  " + name[3] + "\n\r\n\r" + "   5��  " + name[4] + "\n\r\n\r" + "   6��  "
					+ name[5] + "\n\r\n\r" + "   7��  " + name[6] + "\n\r\n\r" + "   8��  " + name[7] + "\n\r\n\r"
					+ "   9��  " + name[8] + "\n\r\n\r" + "  10��  " + name[9] + "\n\r\n\r" + "             ");
		}
	}

	private void buildPacket(String text) {
		String date = time();
		// String type = null;
		String title = null;
		writeC(Opcodes.S_BOARD_READ);
		writeD(0);
		writeS("â�� ����");
		writeS(title);
		writeS(date);
		writeS(text);
	}

	private int Rank(L1PcInstance pc, int number) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		int objid = pc.getId();
		int i = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			switch (number) {
			case 1:
				pstm = con.prepareStatement("SELECT char_name FROM characters WHERE AccessLevel = 0 order by Exp desc limit 10");
				break;
			case 2:
				pstm = con.prepareStatement("SELECT char_name FROM characters WHERE Type = 0 And AccessLevel = 0 order by Exp desc limit 10");
				break;
			case 3:
				pstm = con.prepareStatement("SELECT char_name FROM characters WHERE Type = 1 And AccessLevel = 0 order by Exp desc limit 10");
				break;
			case 4:
				pstm = con.prepareStatement("SELECT char_name FROM characters WHERE Type = 2 And AccessLevel = 0 order by Exp desc limit 10");
				break;
			case 5:
				pstm = con.prepareStatement("SELECT char_name FROM characters WHERE Type = 3 And AccessLevel = 0 order by Exp desc limit 10");
				break;
			case 6:
				pstm = con.prepareStatement("SELECT char_name FROM characters WHERE Type = 4 And AccessLevel = 0 order by Exp desc limit 10");
				break;
			case 7:
				pstm = con.prepareStatement("SELECT char_name FROM characters WHERE Type = 5 And AccessLevel = 0 order by Exp desc limit 10");
				break;
			case 8:
				pstm = con.prepareStatement("SELECT char_name FROM characters WHERE Type = 6 And AccessLevel = 0 order by Exp desc limit 10");
				break;
			case 9:
				pstm = con.prepareStatement("SELECT objid FROM characters WHERE AccessLevel = 0 order by Exp desc");
				break;

			default:
				pstm = con.prepareStatement("SELECT char_name FROM characters WHERE AccessLevel = 0 order by Exp desc limit 10");
				break;
			}

			rs = pstm.executeQuery();
			if (number == 9) {
				while (rs.next()) {
					i++;
					if (objid == rs.getInt(1))
						break;
				}
				String sql = "SELECT objid FROM characters WHERE Type = ";
				sql = (new StringBuilder(String.valueOf(sql))).append(pc.getType()).toString();
				sql = (new StringBuilder(String.valueOf(sql))).append(" And AccessLevel = 0 order by Exp desc").toString();
				pstm2 = con.prepareStatement(sql);
				rs2 = pstm2.executeQuery();
				j = 0;
				while (rs2.next()) {
					j++;
					if (objid == rs2.getInt(1))
						break;
				}
			} else {
				while (rs.next()) {
					name[i] = rs.getString(1);
					i++;
				}

				// ���ڵ尡 ���ų� 5���� ������
				while (i < 10) {
					name[i] = "����.";
					i++;
				}
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con);
		}

		return i;
	}

	private static String time() {
		TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		Calendar cal = Calendar.getInstance(tz);
		int year = cal.get(Calendar.YEAR) - 2000;
		String year2;
		if (year < 10) {
			year2 = "0" + year;
		} else {
			year2 = Integer.toString(year);
		}
		int Month = cal.get(Calendar.MONTH) + 1;
		String Month2 = null;
		if (Month < 10) {
			Month2 = "0" + Month;
		} else {
			Month2 = Integer.toString(Month);
		}
		int date = cal.get(Calendar.DATE);
		String date2 = null;
		if (date < 10) {
			date2 = "0" + date;
		} else {
			date2 = Integer.toString(date);
		}
		return year2 + "/" + Month2 + "/" + date2;
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_Ranking;
	}

}
