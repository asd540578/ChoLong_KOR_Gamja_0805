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
/*
 * $Header: /cvsroot/l2j/L2_Gameserver/java/net/sf/l2j/Server.java,v 1.5 2004/11/19 08:54:43 l2chef Exp $
 *
 * $Author: l2chef $
 * $Date: 2004/11/19 08:54:43 $
 * $Revision: 1.5 $
 * $Log: Server.java,v $
 * Revision 1.5  2004/11/19 08:54:43  l2chef
 * database is now used
 *
 * Revision 1.4  2004/07/08 22:42:28  l2chef
 * logfolder is created automatically
 *
 * Revision 1.3  2004/06/30 21:51:33  l2chef
 * using jdk logger instead of println
 *
 * Revision 1.2  2004/06/27 08:12:59  jeichhorn
 * Added copyright notice
 */
package server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.LogManager;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.Database.DB;
//import server.monitor.MonitorManager;
import l1j.server.server.utils.SQLUtil;
//import l1j.server.telnet.TelnetServer;
import l1j.server.server.utils.SystemUtil;
import server.mina.LineageCodecFactory;

public class Server {
	private volatile static Server uniqueInstance;
	private static final String LOG_PROP = "./config/log.properties";// �α� ���� ����

	private Server() {
	}

	public static Server createServer() {
		if (uniqueInstance == null) {
			synchronized (Server.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new Server();
				}
			}
		}
		return uniqueInstance;
	}

	public void start() {
		initLogManager();
		initDBFactory();
		try {
			clearDB();
		} catch (SQLException e) {
		} finally {
			
		}
		startGameServer();
		if (!Config.���ο���Ŷ����) {
			startLoginServer();
		}
	}

	private void addLogger(DefaultIoFilterChainBuilder chain) throws Exception {
		chain.addLast("logger", new LoggingFilter());

	}

	public void shutdown() {
		GameServer.getInstance().shutdown();
	}

	private void initLogManager() {
		File logFolder = new File("log");
		logFolder.mkdir();

		try {
			InputStream is = new BufferedInputStream(new FileInputStream(LOG_PROP));
			LogManager.getLogManager().readConfiguration(is);
			is.close();
		} catch (IOException e) {
			e.getStackTrace();
			System.exit(0);
		}
		try {
			Config.load();
		} catch (Exception e) {
			e.getStackTrace();
			System.exit(0);
		}
	}

	private void initDBFactory() {// L1DatabaseFactory �ʱ⼳��
		DB.init();
	}

	private void startGameServer() {
		try {
			GameServer.getInstance().initialize();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}

	private static NioSocketAcceptor acceptor;

	private void startLoginServer() {
		try {
			LoginController.getInstance().setMaxAllowedOnlinePlayers(Config.MAX_ONLINE_USERS);
			acceptor = new NioSocketAcceptor();

			DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

			// ��ȣȭ Ŭ���� ���
			chain.addLast("codec", new ProtocolCodecFilter(new LineageCodecFactory()));

			// �ΰŷ� �⺻ ����
			if (Config.LOGGER) {
				addLogger(chain);
			}

			acceptor.setReuseAddress(true);
			acceptor.getSessionConfig().setReceiveBufferSize(2048);// 1024*32
			// Bind
			acceptor.setHandler(new LineageProtocolHandler());
			acceptor.setCloseOnDeactivation(false);
			acceptor.bind(new InetSocketAddress(Config.GAME_SERVER_PORT));

			System.out.println(":: Server�� " + Config.GAME_SERVER_PORT + "�� ��Ʈ�� �̿��ؼ� ���� �Ǿ����ϴ�.  : Memory : "
					+ SystemUtil.getUsedMemoryMB() + " MB");
			System.out.println("���� ������ ����ȭ ���·� �����մϴ�");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean ChangePort(int port) {
		try {
			acceptor.unbind();
			acceptor.bind(new InetSocketAddress(port));
			System.out.println(":: Game ������ " + port + "�� ��Ʈ�� �̿��ؼ� �簡�� �Ǿ����ϴ�.  : Memory : "
					+ SystemUtil.getUsedMemoryMB() + " MB");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void clearDB() throws SQLException {
		Connection c = null;
		PreparedStatement p = null;
		try {
			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement("call deleteData(?)");
			p.setInt(1, Config.DELETE_DB_DAYS);
			p.executeUpdate();
		} catch (Exception e) {
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
	}

	ArrayList<Integer> del = new ArrayList<Integer>();
	ArrayList<Integer> temp = new ArrayList<Integer>();
	ArrayList<Integer> org = new ArrayList<Integer>();

	public void clearDB2() throws SQLException {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("select objid FROM characters");
			rs = pstm.executeQuery();
			while (rs.next()) {
				del.add(rs.getInt(1));
			}
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void clearDB3() throws SQLException {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();

			pstm = con.prepareStatement("select char_id FROM character_items");
			rs = pstm.executeQuery();
			while (rs.next()) {
				temp.add(rs.getInt(1));
			}
			pstm.executeUpdate();
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void clearDB4() throws SQLException {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			for (int i = 0; i < org.size(); i++) {
				pstm = con.prepareStatement("delete FROM character_items WHERE char_id=? ");
				pstm.setInt(1, org.get(i));
				rs = pstm.executeQuery();
				pstm.executeUpdate();
			}
		} catch (SQLException e) {
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void gogo() {
		for (int j = 0; j < del.size(); j++) {
			if (!temp.contains(del.get(j))) {
				org.add(del.get(j));
			}
		}

	}
}
