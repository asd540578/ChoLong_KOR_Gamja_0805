package l1j.server.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import javolution.util.FastTable;
import l1j.server.L1DatabaseFactory;
import l1j.server.server.GMCommands;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.templates.L1ShopItem;
import l1j.server.server.utils.SQLUtil;

public class LogTable {

	private static LogTable _instance;

	public static LogTable getInstance() {
		if (_instance == null)
			_instance = new LogTable();
		return _instance;
	}

	public LogTable() {
	}

	private static Timestamp start���T;
	private static Timestamp start����T;
	public static boolean ��ɾƵ� = false;
	public static boolean �����Ƶ� = false;
	public static FastTable<adenLog> ��ɾƵ�����Ʈ = new FastTable<adenLog>();
	public static FastTable<adenLog> �����Ƶ�����Ʈ = new FastTable<adenLog>();

	public static void ��ɾƵ�����() {
		��ɾƵ�����Ʈ.clear();
		��ɾƵ� = true;
		start���T = new Timestamp(System.currentTimeMillis());
	}

	public static void ��ɾƵ�(L1PcInstance pc, int count) {
		if (��ɾƵ�) {
			if (pc.getNetConnection() == null)
				return;
			adenLog log = null;
			for (adenLog aL : ��ɾƵ�����Ʈ) {
				if (aL.name.equalsIgnoreCase(pc.getName())) {
					log = aL;
					break;
				}
			}
			if (log != null) {
				log.count += count;
			} else {
				log = new adenLog();
				log.accounts = pc.getAccountName();
				log.name = pc.getName();
				log.count += count;
				��ɾƵ�����Ʈ.add(log);
			}
		}
	}

	public static void ��ɾƵ�����() {
		��ɾƵ� = false;
		GeneralThreadPool.getInstance().schedule(new saveThread(��ɾƵ�����Ʈ, start���T, false), 1);
	}

	public static void �����Ƶ�����() {
		�����Ƶ�����Ʈ.clear();
		�����Ƶ� = true;
		start����T = new Timestamp(System.currentTimeMillis());
	}

	public static void �����Ƶ�(L1PcInstance pc, int count) {
		if (�����Ƶ�) {
			if (pc.getNetConnection() == null)
				return;
			adenLog log = null;
			for (adenLog aL : �����Ƶ�����Ʈ) {
				if (aL.name.equalsIgnoreCase(pc.getName())) {
					log = aL;
					break;
				}
			}
			if (log != null) {
				log.count += count;
			} else {
				log = new adenLog();
				log.accounts = pc.getAccountName();
				log.name = pc.getName();
				log.count += count;
				�����Ƶ�����Ʈ.add(log);
			}
		}
	}

	public static void �����Ƶ�����() {
		�����Ƶ� = false;
		GeneralThreadPool.getInstance().schedule(new saveThread(�����Ƶ�����Ʈ, start����T, true), 1);
	}

	static class adenLog {
		public String accounts = "";
		public String name = "";
		public int count = 0;
	}

	static class saveThread implements Runnable {
		private FastTable<adenLog> list = new FastTable<adenLog>();
		private Timestamp start;
		private Timestamp end;
		private boolean shop = false;

		public saveThread(FastTable<adenLog> _list, Timestamp _startTime, boolean ck) {
			list.addAll(_list);
			start = new Timestamp(_startTime.getTime());
			end = new Timestamp(System.currentTimeMillis());
			shop = ck;
		}

		@Override
		public void run() {
			// TODO �ڵ� ������ �޼ҵ� ����
			try {
				if (list.size() <= 0)
					return;
				adenLog aL = list.remove(0);
				if (aL == null)
					return;
				if (aL.count <= 0) {
					GeneralThreadPool.getInstance().schedule(this, 100);
					return;
				}
				Connection c = null;
				PreparedStatement p = null;
				try {
					c = L1DatabaseFactory.getInstance().getConnection();
					if (shop) {
						p = c.prepareStatement(
								"INSERT INTO log_adena_shop SET startTime=?, endTime=?, accounts=?, name=?, count=?");
					} else {
						p = c.prepareStatement(
								"INSERT INTO log_adena_monster SET startTime=?, endTime=?, accounts=?, name=?, count=?");
					}
					p.setTimestamp(1, start);
					p.setTimestamp(2, end);
					p.setString(3, aL.accounts);
					p.setString(4, aL.name);
					p.setInt(5, aL.count);
					p.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					SQLUtil.close(p);
					SQLUtil.close(c);
				}
				GeneralThreadPool.getInstance().schedule(this, 100);
			} catch (Exception e) {

			}
		}

	}

	public static boolean log_lucky_darkelder(L1PcInstance pc) {
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;
		try {
			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement("INSERT INTO log_lucky_darkelder SET accounts=?, id=?, name=?, time=SYSDATE()");

			p.setString(1, pc.getAccountName());
			p.setInt(2, pc.getId());
			p.setString(3, pc.getName());
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}

	public static boolean log_fire_energy(L1PcInstance pc, int old_count, int new_count) {
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;

		try {

			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement(
					"INSERT INTO log_fire_energy SET accounts=?, id=?, name=?, old_count=?, new_count=?, time=SYSDATE()");

			p.setString(1, pc.getAccountName());
			p.setInt(2, pc.getId());
			p.setString(3, pc.getName());
			p.setInt(4, old_count);
			p.setInt(5, new_count);
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}

	public static boolean log_npc_buy_ok(L1PcInstance pc, L1ShopItem item, int count) {
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;
		try {
			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement(
					"INSERT INTO log_npc_buy SET accounts=?, id=?, name=?, itemId=?, itemName=?, itemEnchant=?, count=?, price=?, time=SYSDATE(), note=?");

			p.setString(1, pc.getAccountName());
			p.setInt(2, pc.getId());
			p.setString(3, pc.getName());
			p.setInt(4, item.getItemId());
			p.setString(5, item.getItem().getName());
			p.setInt(6, item.getEnchant());
			p.setInt(7, count);
			p.setInt(8, item.getBuyPrice() * count);
			p.setString(9, "����");
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}

	public static boolean log_npc_buy_cancel(L1PcInstance pc, L1ItemInstance item, int count) {
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;
		try {
			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement(
					"INSERT INTO log_npc_buy SET accounts=?, id=?, name=?, itemId=?, itemName=?, itemEnchant=?, count=?, price=?, time=SYSDATE(), note=?");

			p.setString(1, pc.getAccountName());
			p.setInt(2, pc.getId());
			p.setString(3, pc.getName());
			p.setInt(4, item.getItemId());
			p.setString(5, item.getName());
			p.setInt(6, item.getEnchantLevel());
			p.setInt(7, count);
			p.setInt(8, 0);
			p.setString(9, "���");
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}

	/**
	 * ���� â�� �α׸� ���
	 * 
	 * @param pc
	 *            Pc Instance
	 * @param item
	 *            item Instance
	 * @param type
	 *            Log type 0=���γֱ� 1=����ã�� 2=�����ֱ� 3= ����ã�� 4=��â��
	 * @return ���� true ���� false used : if(LogTable.getInstance().insert(pc,
	 *         item, LogTable.DROP_LOG)){ System.out.println("Log Write OK,"); }
	 */

	public static boolean logwarehouse(L1PcInstance pc, L1ItemInstance item, int count, int type) {
		String sTemp = "";
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;
		try {
			switch (type) {
			case 0:
				sTemp = "���γֱ�";
				break;
			case 1:
				sTemp = "����ã��";
				break;
			case 2:
				sTemp = "�����ֱ�";
				break;
			case 3:
				sTemp = "����ã��";
				break;
			case 4:
				sTemp = "��â��";
				break;
			}
			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement(
					"INSERT INTO log_warehouse SET type=?, account=?, char_id=?, char_name=?, item_id=?, item_name=?, item_enchantlvl=?, item_count=?, datetime=SYSDATE()");

			p.setString(1, sTemp);
			p.setString(2, pc.getAccountName());
			p.setInt(3, pc.getId());
			p.setString(4, pc.getName());
			p.setInt(5, item.getId());
			p.setString(6, item.getName());
			p.setInt(7, item.getEnchantLevel());
			p.setInt(8, count);
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}

	/**
	 * ���� Ŭ�� â�� �α׸� ���
	 * 
	 * @param pc
	 *            Pc Instance
	 * @param item
	 *            item Instance
	 * @param type
	 *            Log type 0=�ֱ� 1=ã��
	 * @return
	 * @return ���� true ���� false used : if(LogTable.getInstance().insert(pc,
	 *         item, LogTable.DROP_LOG)){ System.out.println("Log Write OK,"); }
	 */

	public static boolean logcwarehouse(L1PcInstance pc, L1ItemInstance item, int count, int type) {
		String sTemp = "";
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;
		try {
			switch (type) {
			case 0:
				sTemp = "�ֱ�";
				break;
			case 1:
				sTemp = "ã��";
				break;
			}
			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement(
					"INSERT INTO log_cwarehouse SET type=?, clan_id=?, clan_name=?, account=?, char_id=?, char_name=?, item_id=?, item_name=?, item_enchantlvl=?, item_count=?, datetime=SYSDATE()");

			p.setString(1, sTemp);
			p.setInt(2, pc.getClanid());
			p.setString(3, pc.getClanname());
			p.setString(4, pc.getAccountName());
			p.setInt(5, pc.getId());
			p.setString(6, pc.getName());
			p.setInt(7, item.getId());
			p.setString(8, item.getName());
			p.setInt(9, item.getEnchantLevel());
			p.setInt(10, count);
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}

	public static boolean logAdentrade(L1PcInstance player, L1PcInstance trading_partner, L1ItemInstance item) {
		// String sTemp = "";
		if (!GMCommands.�α�_��ȯ)
			return false;
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;
		try {
			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement(
					"INSERT INTO _log_Aden_trade_ok SET time=SYSDATE(), char_account=?,  char_name=?, char_level=?, account_char_count=?, Adena_count=?");

			p.setString(1, trading_partner.getAccountName());
			p.setString(2, trading_partner.getName());
			p.setInt(3, trading_partner.getLevel());
			p.setInt(4, trading_partner.getNetConnection().getAccount().countCharacters());
			p.setInt(5, item.getCount());
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}

	/**
	 * ���� ��ȯ �α׸� ���
	 * 
	 * @param pc
	 *            Pc Instance
	 * @param item
	 *            item Instance
	 * @return ���� true ���� false used : if(LogTable.getInstance().insert(pc,
	 *         item, LogTable.DROP_LOG)){ System.out.println("Log Write OK,"); }
	 */

	public static boolean logtrade(L1PcInstance player, L1PcInstance trading_partner, L1ItemInstance item) {
		// String sTemp = "";
		if (!GMCommands.�α�_��ȯ)
			return false;
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;
		try {

			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement(
					"INSERT INTO _log_trade_ok SET time=SYSDATE(), char_account=?, char_id=?, char_name=?, t_account=?, t_id=?, t_name=?, item_id=?, item_name=?, item_enchant=?, item_count=?");

			p.setString(1, player.getAccountName());
			p.setInt(2, player.getId());
			p.setString(3, player.getName());
			p.setString(4, trading_partner.getAccountName());
			p.setInt(5, trading_partner.getId());
			p.setString(6, trading_partner.getName());
			p.setInt(7, item.getId());
			p.setString(8, item.getName());
			p.setInt(9, item.getEnchantLevel());
			p.setInt(10, item.getCount());
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}

	/**
	 * ���� ���λ��� �α׸� ���
	 * 
	 * @param pc
	 *            Pc Instance
	 * @param item
	 *            item Instance
	 * @param type
	 *            0=���� 1=�Ǹ�
	 * @return ���� true ���� false used : if(LogTable.getInstance().insert(pc,
	 *         item, LogTable.DROP_LOG)){ System.out.println("Log Write OK,"); }
	 */

	public static boolean logshop(L1PcInstance pc, L1PcInstance targetPc, L1ItemInstance item, int price, int count,
			int type) {
		if (!GMCommands.�α�_���λ���)
			return false;

		String sTemp = "";
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;
		int tPrice = price * count;
		try {

			switch (type) {
			case 0:
				sTemp = "���λ��� - ����";
				break;
			case 1:
				sTemp = "���λ��� - �Ǹ�";
				break;
			}

			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement(
					"INSERT INTO log_private_shop SET time=SYSDATE(), type=?, shop_account=?, shop_id=?, shop_name=?, user_account=?, user_id=?, user_name=?, item_id=?, item_name=?, item_enchantlvl=?, price=?, item_count=?, total_price=?");

			p.setString(1, sTemp);
			p.setString(2, targetPc.getAccountName());// ���� ����
			p.setInt(3, targetPc.getId());// ���� ���̵�
			p.setString(4, targetPc.getName());// ���� ����
			p.setString(5, pc.getAccountName());// ��ü�� ����
			p.setInt(6, pc.getId());// ��ü�� ���̵�
			p.setString(7, pc.getName());// ��ü�� �̸�
			p.setInt(8, item.getId());
			p.setString(9, item.getName());
			p.setInt(10, item.getEnchantLevel());
			p.setInt(11, price);
			p.setInt(12, count);
			p.setInt(13, tPrice);
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}

	/**
	 * ���� ���� �α׸� ���
	 * 
	 * @param pc
	 *            Pc Instance
	 * @param item
	 *            item Instance
	 * @param type
	 *            0=���� 1=�Ǹ�
	 * @return ���� true ���� false used : if(LogTable.getInstance().insert(pc,
	 *         item, LogTable.DROP_LOG)){ System.out.println("Log Write OK,"); }
	 */

	public static boolean lognpcshop(L1PcInstance pc, int npcid, L1ItemInstance item, int price, int count, int type) {
		if (!GMCommands.�α�_����)
			return false;

		String sTemp = "";
		boolean bool = false;
		Connection c = null;
		PreparedStatement p = null;
		int tPrice = price * count;
		try {

			switch (type) {
			case 0:
				sTemp = "���� - ����";
				break;
			case 1:
				sTemp = "���� - �Ǹ�";
				break;
			}

			c = L1DatabaseFactory.getInstance().getConnection();
			p = c.prepareStatement(
					"INSERT INTO log_shop SET time=SYSDATE(), type=?, npc_id=?, user_account=?, user_id=?, user_name=?, item_id=?, item_name=?, item_enchantlvl=?, price=?, item_count=?, total_price=?");

			p.setString(1, sTemp);
			p.setInt(2, npcid);// ���� ����
			p.setString(3, pc.getAccountName());// ��ü�� ����
			p.setInt(4, pc.getId());// ��ü�� ���̵�
			p.setString(5, pc.getName());// ��ü�� �̸�
			p.setInt(6, item.getId());
			p.setString(7, item.getName());
			p.setInt(8, item.getEnchantLevel());
			p.setInt(9, price);
			p.setInt(10, count);
			p.setInt(11, tPrice);
			p.executeUpdate();
			bool = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(p);
			SQLUtil.close(c);
		}
		return bool;
	}
}