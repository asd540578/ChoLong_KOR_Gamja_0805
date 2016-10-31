package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.Account;
import l1j.server.server.AccountAlreadyLoginException;
import l1j.server.server.GameServerFullException;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.clientpackets.C_AuthLogin;
import l1j.server.server.clientpackets.C_NoticeClick;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_CharPass;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_LoginResult;
import l1j.server.server.serverpackets.S_Notice;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;

public class Authorization {
	private static Authorization uniqueInstance = null;
	private static Logger _log = Logger.getLogger(C_AuthLogin.class.getName());

	public static Authorization getInstance() {
		if (uniqueInstance == null) {
			synchronized (Authorization.class) {
				if (uniqueInstance == null)
					uniqueInstance = new Authorization();
			}
		}

		return uniqueInstance;
	}

	private Authorization() {
	}

	LineageClient _cl;

	class ���ὺ���� implements Runnable {
		public ���ὺ����(LineageClient CL) {
			_cl = CL;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(100);
				disconnect(_cl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void auth(LineageClient client, String accountName, String password, String ip, String host)
			throws IOException {

		/** ������ ����, ���ڷ� �Ǿ��ִ��� üũ **/
		int length = accountName.length();
		char chr;
		for (int i = 0; i < length; i++) {
			chr = accountName.charAt(i);
			if (Character.UnicodeBlock.of(chr) != Character.UnicodeBlock.BASIC_LATIN) {
				if (client.getActiveChar() == null) {
					client.sendPacket(new S_LoginResult(client.getAccount(), 26), true);
				}
				return;
			} /*
				 * else{ System.out.print(chr); }
				 */
		}

		int accountLength = accountName.getBytes().length;
		int passwordLength = password.getBytes().length;
		if (accountLength > 12 || accountLength < 4) {
			_log.info("������ ���� [���� ���� " + accountName + "] ");

			client.sendPacket(new S_LoginResult(client.getAccount(), S_LoginResult.REASON_WRONG_ACCOUNT));

			return;
		}
		// �н����� ���� (eva �� �Ŵ��� Ŭ������)
		else if (passwordLength > 12 || passwordLength < 4) {
			_log.info("�н����� ���� ���� [�н����� ���� " + passwordLength + "] ");

			client.sendPacket(new S_LoginResult(client.getAccount(), S_LoginResult.REASON_WRONG_PASSWORD));
			return;
		}

		Account account = Account.load(accountName);
		if (account == null) {
			if (Config.AUTO_CREATE_ACCOUNTS) {
				account = Account.create(accountName, password, ip, host);
				account = Account.load(accountName);
				// }
			} else {
				_log.warning("account missing for user " + accountName);
			}
		}

		if (account == null || !account.validatePassword(accountName, password)) {
			int lfc = client.getLoginFailedCount();
			client.setLoginFailedCount(lfc + 1);
			if (lfc > 2)
				disconnect(client);
			else {
				client.sendPacket(new S_LoginResult(client.getAccount(), 26), true);
				// client.sendPacket(new
				// S_Notice("�������� �����Ͻ� �����Դϴ�. ��ڿ��� ���� �Ͻñ� �ٶ��ϴ�"), true);
			}
			return;
		}
		// =========== IP Check[#C_AuthLogin] ===========
		// if(Config.AUTH_CONNECT) {
		// LoginAuth authIP = new LoginAuth();
		// if (authIP.ConnectCheck(ip)) {
		// client.sendPacket(new S_LoginResult(0x01)); // 0x01
		// return;
		// }
		// }
		// =========== IP Check[#C_AuthLogin] ===========
		if (account.isBanned()) {
			_log.info("BAN ������ �α����� �ź��߽��ϴ�. account=" + accountName + " host=" + host);
			client.sendPacket(new S_Notice("�������� �����Ͻ� �����Դϴ�. ��ڿ��� ���� �Ͻñ� �ٶ��ϴ�"), true);
			// disconnect(client);
			GeneralThreadPool.getInstance().execute(new ���ὺ����(client));
			return;
		}

		try {
			LoginController.getInstance().login(client, account);
			Account.updateLastActive(account, ip); // ���� �α������� �����Ѵ�
			client.setAccount(account);

			client.sendPacket(new S_LoginResult());
			// accountTimeCheck(client);
			client.sendPacket(new S_CharPass(), true);
			// 123
			// �α� ����Ʈ
			// client.sendPacket(new
			// S_LoginResult(S_LoginResult.REASON_LOGIN_OK), true);
			// client.sendPacket(new S_LoginResult(0), true);
			// client.sendPacket(new S_LoginResult(), true);
			//
			/*
			 * client.sendPacket(new S_LoginResult()); client.sendPacket(new
			 * S_CharPass(), true);
			 */
			// sendNotice(client);
		} catch (GameServerFullException e) {
			client.sendPacket(new S_Notice("���� �����ο��� ���� ������ �����ǰ��ֽ��ϴ�.\n \n ����Ŀ� �ٽ� ������ �õ����ֽñ�ٶ��ϴ�."), true);
			disconnect(client);
			_log.info("���� �ο����� �ʰ��Ͽ����ϴ�. (" + client.getHostname() + ")�� ���� �õ��� ���� �����߽��ϴ�.");
			return;
		} catch (AccountAlreadyLoginException e) { // by ���������
			client.sendPacket(new S_LoginResult(client.getAccount(), S_LoginResult.REASON_ACCOUNT_IN_USE), true);
			disconnect(client);
			String name = Alreadychr(accountName);
			L1PcInstance target = L1World.getInstance().getPlayer(name);
			if (target instanceof L1RobotInstance) {
				return;
			}
			if (target == null)
				return;

			target.sendPackets(new S_SystemMessage("�ٸ� ����� ���� �������� �α����߽��ϴ�."), true);
			target.sendPackets(new S_Disconnect(), true);
			if (target.getNetConnection() != null) {
				target.getNetConnection().kick();
				target.getNetConnection().close();
			}
			return;
		}
	}

	private static String _chr;

	public static String Alreadychr(String name) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			String sqlstr = "SELECT * FROM characters WHERE account_name='" + name + "' and onlineStatus = 1";
			pstm = con.prepareStatement(sqlstr);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				return null;
			}
			_chr = rs.getString("char_name");

		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs, pstm, con);
		}
		return _chr;

	}

	public void sendNotice(LineageClient client) {
		String accountName = client.getAccountName();

		// �о���� ������ �ִ��� üũ
		if (S_Notice.NoticeCount(accountName) > 0) {
			client.sendPacket(new S_Notice(accountName, client), true);
		} else {
			client.setloginStatus(1);
			new C_NoticeClick(client);
		}
	}

	private void disconnect(LineageClient client) throws IOException {
		client.kick();
		client.close();
	}

	@SuppressWarnings("unused")
	private Account loadAccountInfoFromDB(String accountName) {
		return Account.load(accountName);
	}
}
