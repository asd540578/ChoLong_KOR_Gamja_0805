package l1j.server.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import javax.sql.DataSource;

import l1j.server.Config;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.KeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class DB {

	private static DataSource dataSource;
	private static GenericObjectPool<Object> connectionPool;
	private static String databaseName;
	private static int databaseMajorVersion;
	private static int databaseMinorVersion;

	public synchronized static void init() {

		connectionPool = new GenericObjectPool<Object>();

		connectionPool.setMinIdle(10);
		connectionPool.setMaxIdle(300);
		connectionPool.setMaxActive(300);

		connectionPool.setTestWhileIdle(true);
		connectionPool.setTimeBetweenEvictionRunsMillis(600000);
		
		try {
			dataSource = setupDataSource();
			Connection c = getConnection();
			DatabaseMetaData dmd = c.getMetaData();
			databaseName = dmd.getDatabaseProductName();
			databaseMajorVersion = dmd.getDatabaseMajorVersion();
			databaseMinorVersion = dmd.getDatabaseMinorVersion();
			c.close();
		} catch (Exception e) {
			System.out.println("���� ����: " + Config.DB_URL);
			e.printStackTrace();
			throw new Error("DatabaseFactory�� �ʱ�ȭ �� �� �����ϴ�!");
		}

		// System.out.println("�����ͺ��̽��� ���������� ����ƽ��ϴ�.");
	}

	private static DataSource setupDataSource() throws Exception {
		ConnectionFactory conFactory = new DriverManagerConnectionFactory(Config.DB_URL, Config.DB_LOGIN, Config.DB_PASSWORD);
		KeyedObjectPoolFactory<Object, Object> kopf = new GenericKeyedObjectPoolFactory<Object, Object>(null, 300);
		
		new PoolableConnectionFactoryAK(conFactory, connectionPool, kopf, 1, false, true);// 13_07_07 ����

		return new PoolingDataSource(connectionPool);
	}

	public static int active_count_check = 1;

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static int getActiveConnections() {
		return connectionPool.getNumActive();
	}

	public static int getIdleConnections() {
		return connectionPool.getNumIdle();
	}

	public static synchronized void shutdown() {
		try {
			connectionPool.close();
		} catch (Exception e) {
			System.out.println("DatabaseFactory ���� ����");
			e.printStackTrace();
		}
		// set datasource to null so we can call init() once more...
		dataSource = null;
	}

	public static void close(Connection con) {
		if (con == null)
			return;
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("DatabaseFactory: �����ͺ��̽� ���� �ݱ� ����!");
			e.printStackTrace();
		}
	}

	public static String getDatabaseName() {
		return databaseName;
	}

	public static int getDatabaseMajorVersion() {
		return databaseMajorVersion;
	}

	public static int getDatabaseMinorVersion() {
		return databaseMinorVersion;
	}
}
