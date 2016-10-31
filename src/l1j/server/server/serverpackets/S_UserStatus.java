package l1j.server.server.serverpackets;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_UserStatus extends ServerBasePacket {

	private static final String S_UserCommands3 = "[C] S_UserCommands3";

	private byte[] _byte = null;

	public S_UserStatus(L1PcInstance pc, int number) {
		buildPacket(pc, number);
	}

	@SuppressWarnings("deprecation")
	private void buildPacket(L1PcInstance pc, int number) {

		int calcaden = 0;
		int calclevel = pc.getLevel() - pc._PlayLevel;
		int calcexp = pc.getExp() - pc._PlayEXP;

		Date _lastlogout = new Date();
		String lastLogout = null;

		if (pc.getLogOutTime() != null) {
			StringBuilder sb = null;
			sb = new StringBuilder();
			_lastlogout.setTime(pc.getLogOutTime().getTime());

			Calendar cal = Calendar.getInstance(Locale.KOREA);
			cal.setTime(_lastlogout);

			sb.append(cal.get(Calendar.YEAR) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.DATE) + ". "
					+ cal.get(Calendar.HOUR_OF_DAY) + ":"
					+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND));
			lastLogout = sb.toString();
			sb = null;
		} else {
			lastLogout = "���� ���� �ɸ� �Դϴ�.";
		}

		if (pc.getInventory().countItems(40308) > 0) {
			calcaden = pc.getInventory().countItems(40308) - pc._PlayAden;
		} else {
			calcaden = -pc._PlayAden;
		}

		long nowtime = System.currentTimeMillis();
		Timestamp nowstamp = new Timestamp(nowtime);

		int girantime = 0;
		int girantemp = 60 * 60 * 3;
		int giranh = 0;
		int girans = 0;
		int giranm = 0;

		int ����time = 0;
		int ����temp = 60 * 60 * 2;
		int ����h = 0;
		int ����s = 0;
		int ����m = 0;

		int ravatime = 0;
		int ravatemp = 18000;
		int ravah = 0;
		int ravas = 0;
		int ravam = 0;

		int ivorytime = 0;
		int ivorytemp = 3600;
		int ivoryh = 0;
		int ivorys = 0;
		int ivorym = 0;

		if (pc.getgiranday() != null
				&& pc.getgiranday().getDate() == nowstamp.getDate()
				&& pc.getgirantime() != 0) {
			girantime = pc.getgirantime();
			girans = (girantemp - girantime) % 60;
			giranm = (girantemp - girantime) / 60 % 60;
			giranh = (girantemp - girantime) / 60 / 60;// �ð�
		} else {
			giranh = 3;
			giranm = 0;
			girans = 0;
		}

		if (pc.get�����Ѱ���day() != null
				&& pc.get�����Ѱ���day().getDate() == nowstamp.getDate()
				&& pc.get�����Ѱ���time() != 0) {
			����time = pc.get�����Ѱ���time();
			����s = (����temp - ����time) % 60;
			����m = (����temp - ����time) / 60 % 60;
			����h = (����temp - ����time) / 60 / 60;// �ð�
		} else {
			����h = 2;
			����m = 0;
			����s = 0;
		}

		if (pc.getravaday() != null
		// && pc.getravaday().getDate() == nowstamp.getDate()
				&& pc.getravatime() != 0) {
			ravatime = pc.getravatime();
			ravas = (ravatemp - ravatime) % 60;
			ravam = (ravatemp - ravatime) / 60 % 60;
			ravah = (ravatemp - ravatime) / 60 / 60;// �ð�
		} else {
			ravah = 2;
			ravam = 0;
			ravas = 0;
		}

		if (pc.getivoryday() != null
				&& pc.getivoryday().getDate() == nowstamp.getDate()
				&& pc.getivorytime() != 0) {
			ivorytime = pc.getivorytime();
			ivorys = (ivorytemp - ivorytime) % 60;
			ivorym = (ivorytemp - ivorytime) / 60 % 60;
			ivoryh = (ivorytemp - ivorytime) / 60 / 60;// �ð�
		} else {
			ivoryh = 1;
			ivorym = 0;
			ivorys = 0;
		}

		// String ���� = "#,##0.0000";
		// DecimalFormat �Ҽ��� = new DecimalFormat(����);

		writeC(Opcodes.S_BOARD_READ);
		writeD(number);// �ѹ�
		writeS(pc.getName());// �۾���?
		writeS(pc.getName() + " �� ����");// ����?
		writeS("  ");// ��¥?
		// System.out.println(����ġ);

		writeS("Logout : " + lastLogout + "\nLogin : " + pc._PlayTime
				+ "\n=======[ȹ�� ����ġ]=======\n" + "[������ : " + calclevel
				+ " EXP : " + calcexp + "]\n" + "\n=======[ȹ�� �Ƶ���]=======\n"
				+ "[" + calcaden + "��]\n" + "\n======[���� ���� ��]=====\n" + "["
				+ pc._PlayMonKill + "����]\n" + "\n=======[���� ���� ��]======\n"
				+ "[" + pc._PlayPcKill + "��]\n" + "\n=======[����   �ð�]=======\n"
				+ "[�������  : " + giranh + "�ð� " + giranm + "�� " + girans
				+ "��]\n" + "[�����Ѱ���: " + ����h + "�ð� " + ����m + "�� " + ����s + "��]\n"
				+ "[���      : " + ravah + "�ð� " + ravam + "�� " + ravas + "��]\n"
				+ "[���ž    : " + ivoryh + "�ð� " + ivorym + "�� " + ivorys
				+ "��]\n" + "=============================");
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_UserCommands3;
	}
}
