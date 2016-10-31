package l1j.server.server.serverpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.utils.SQLUtil;

public class S_LetterList extends ServerBasePacket {
	private static Logger _log = Logger.getLogger(S_Letter.class.getName());
	private static final String S_LETTERLIST = "[S] S_LetterList";
	private byte[] _byte = null;

	public S_LetterList(L1PcInstance pc, int type, int count) {
		buildPacket(pc, type, count);
	}

	private void buildPacket(L1PcInstance pc, int type, int count) {
		Connection con = null;
		Connection con2 = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;

		ResultSet rs = null;
		ResultSet rs1 = null;
		int cnt = 0;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT * FROM letter WHERE receiver=? AND template_id = ? order by date limit ?  ");

			pstm.setString(1, pc.getName());
			pstm.setInt(2, type);
			pstm.setInt(3, count);
			rs = pstm.executeQuery();

			con2 = L1DatabaseFactory.getInstance().getConnection();
			pstm1 = con2
					.prepareStatement(" SELECT count(*) as cnt FROM letter WHERE receiver=? AND template_id = ? order by date limit ?  ");
			pstm1.setString(1, pc.getName());
			pstm1.setInt(2, type);
			pstm1.setInt(3, count);
			rs1 = pstm1.executeQuery();
			if (rs1.next()) {
				cnt = rs1.getInt(1);
			}
			writeC(Opcodes.S_MAIL_INFO);
			writeC(type); // 0:������ 1:���͸����� 2:������
			if (cnt > count)
				cnt = count;

			writeH(cnt);
			// writeH(count); //ǥ���� �� �Լ� (������ 10 �Ϲ� ���� 20 ������ 50��)
			// System.out.println(cnt);
			String[] str;
			int i = 1;
			while (rs.next()) {
				writeD(rs.getInt(1));

				if (pc.getName().equals(rs.getString(3))) {
					writeC(1); // Ȯ�ο��� 0:��Ȯ�� 1:Ȯ��
				} else {
					writeC(rs.getInt(9)); // Ȯ�ο��� 0:��Ȯ�� 1:Ȯ��
				}

				str = null;
				str = rs.getString(5).split("/");
				Calendar s = (Calendar) Calendar.getInstance().clone();
				s.set(Integer.parseInt(20 + str[0]),
						Integer.parseInt(str[1]) - 1, Integer.parseInt(str[2]));
				// writeC(Integer.parseInt(str[0]));
				// writeC(Integer.parseInt(str[1]));
				// writeC(Integer.parseInt(str[2]));
				writeD((int) (s.getTimeInMillis() / 1000));
				writeC(0x00);
				writeS(rs.getString(3));
				writeSS(rs.getString(7));
				i++;
				if (i > cnt)
					break;
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(rs1);
			SQLUtil.close(pstm);
			SQLUtil.close(pstm1);
			SQLUtil.close(con2);
			SQLUtil.close(con);
		}
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_LETTERLIST;
	}
}
