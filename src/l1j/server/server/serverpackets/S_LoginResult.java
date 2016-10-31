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
package l1j.server.server.serverpackets;

import java.util.StringTokenizer;

import l1j.server.server.Account;
import l1j.server.server.Opcodes;

public class S_LoginResult extends ServerBasePacket {
	public static final String S_LOGIN_RESULT = "[S] S_LoginResult";

	public static final int REASON_LOGIN_OK_CONNECTOR = 0x01;
	public static final int REASON_CREAT_ACCOUNT = 0x02;
	public static final int REASON_ACCOUNT_CREAT_FAIL = 0x17;

	public static final int REASON_LOGIN_OK = 0x33;// 0x00

	public static final int REASON_ACCOUNT_IN_USE = 0x16;

	public static final int REASON_ACCOUNT_ALREADY_EXISTS = 0x07;

	public static final int REASON_ACCESS_FAILED = 0x08;

	public static final int REASON_USER_OR_PASS_WRONG = 0x08;

	public static final int REASON_BUG_WRONG = 0x26;
	public static final int REASON_WRONG_ACCOUNT = 0x09;
	public static final int REASON_WRONG_PASSWORD = 0x0A;

	// 06-���� ĳ���Ͱ� �̹� �ִ� 9-�̸��߸� 24-ip������ 26-���� ip��������
	// 28-��������ض� 29-������ 31-������ü 32-�ð������� ���� 34-���ɸ��� ����� ����
	// 35-���ӳ��������Ұ� 36-��ݹ��������� 37-����Ű� 38-���׻�� �� 39-���ŷ���

	// public static int REASON_SYSTEM_ERROR = 0x01;

	private byte[] _byte = null;

	public S_LoginResult(Account account, int reason) {
		buildPacket(account, reason);
	}

	public S_LoginResult() {
		writeC(Opcodes.S_LOGIN_CHECK);
		writeC(51);
		String s = "00 00 00 00 00 19 00 00 00 00 00 00 ff ff "
				+ "ff ff 12 00 00 00 00 00 00 00 00 00 00 64";

		/*
		 * writeC(25); String s = "00 00 00 00 00 1b 00 00 00 00 00 00 ff ff "+
		 * "ff ff 0e 00 00 00 00 00 00 00 00 00 45 ab";
		 */
		StringTokenizer st = new StringTokenizer(s);
		while (st.hasMoreTokens()) {
			writeC(Integer.parseInt(st.nextToken(), 16));
		}
	}

	private void buildPacket(Account account, int reason) {
		writeC(Opcodes.S_LOGIN_CHECK);
		writeC(reason);
		if (reason == REASON_LOGIN_OK) {
			String s = null;
			/*
			 * if(account.getCPW()==null){ s =
			 * "00 00 00 00 00 34 00 00 00 00 00 00 ff ff "+
			 * "ff ff 0e 00 00 00 00 00 00 00 00 00 7b 26"; }else{
			 */
			s = "00 00 00 00 00 1a 00 00 00 00 00 00 ff ff "
					+ "ff ff 12 00 00 00 00 00 00 00 00 00 00 2F";
			// }
			StringTokenizer st = new StringTokenizer(s);
			while (st.hasMoreTokens()) {
				writeC(Integer.parseInt(st.nextToken(), 16));
			}
		} else {
			writeD(0x00000000);
			writeD(0x00000000);
			writeD(0x00000000);
		}
	}

	/*
	 * public S_LoginResult() { writeC(Opcodes.S_OPCODE_LOGINRESULT);
	 * writeC(REASON_LOGIN_OK); String s =
	 * "00 00 00 00 00 19 00 00 00 00 00 00 ff ff "+
	 * "ff ff 12 00 00 00 00 00 00 00 00 00 00 64"; String s =
	 * "00 00 00 00 00 19 00 00 00 00 00 00 ff ff "+ "ff ff"; StringTokenizer st
	 * = new StringTokenizer(s); while(st.hasMoreTokens()){
	 * writeC(Integer.parseInt(st.nextToken(), 16)); } writeC(0xDB);
	 * writeC(0xF0); writeC(0xCE); writeC(0x09); writeD(0x00); writeH(0x00);
	 * writeC(0x00); writeC(0x64);
	 * 
	 * }
	 */

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return S_LOGIN_RESULT;
	}
}
