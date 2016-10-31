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
package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.IND_Q;
import l1j.server.INN_Q;
import l1j.server.L1DatabaseFactory;
import l1j.server.quit_Q;
import l1j.server.GameSystem.BossTimer;
import l1j.server.GameSystem.CrockSystem;
import l1j.server.GameSystem.DesertTornadoController;
import l1j.server.GameSystem.GhostHouse;
import l1j.server.GameSystem.NewTimeController;
import l1j.server.GameSystem.NoticeSystem;
import l1j.server.GameSystem.NpcShopSystem;
import l1j.server.GameSystem.PetRacing;
import l1j.server.GameSystem.Delivery.DeliverySystem;
import l1j.server.GameSystem.Gamble.Gamble;
import l1j.server.GameSystem.Hadin.Hadin;
import l1j.server.GameSystem.Hadin.HadinThread;
import l1j.server.GameSystem.Lind.LindRaid;
import l1j.server.GameSystem.MiniGame.DeathMatch;
import l1j.server.GameSystem.NpcBuyShop.NpcBuyShop;
import l1j.server.GameSystem.NpcBuyShop.NpcBuyShopSell;
import l1j.server.GameSystem.NpcTradeShop.NpcTradeShop;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.Robot.Robot_Bugbear;
import l1j.server.GameSystem.Robot.Robot_ConnectAndRestart;
import l1j.server.GameSystem.Robot.Robot_Fish;
import l1j.server.GameSystem.Robot.Robot_Hunt;
import l1j.server.GameSystem.Robot.Robot_Location;
import l1j.server.GameSystem.TraningCenter.TraningCenter;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.IndunSystem.MiniGame.BattleZone;
import l1j.server.Warehouse.SupplementaryService;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.GMCommandsConfig;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.Shutdown;
import l1j.server.server.TimeController.AuctionTimeController;
import l1j.server.server.TimeController.DevilController;
import l1j.server.server.TimeController.FishingTimeController;
import l1j.server.server.TimeController.HouseTaxTimeController;
import l1j.server.server.TimeController.LightTimeController;
import l1j.server.server.TimeController.NoticeTimeController;
import l1j.server.server.TimeController.NpcChatTimeController;
import l1j.server.server.TimeController.UbTimeController;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.TimeController.�ؼ�Controller;
import l1j.server.server.datatables.AdenShopTable;
import l1j.server.server.datatables.CastleTable;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.ClanHistoryTable;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.DoorSpawnTable;
import l1j.server.server.datatables.DragonRaidItemTable;
import l1j.server.server.datatables.DropItemTable;
import l1j.server.server.datatables.DropTable;
import l1j.server.server.datatables.EvaSystemTable;
import l1j.server.server.datatables.FurnitureSpawnTable;
import l1j.server.server.datatables.GetBackRestartTable;
import l1j.server.server.datatables.IpTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.LightSpawnTable;
import l1j.server.server.datatables.MapsTable;
import l1j.server.server.datatables.MobGroupTable;
import l1j.server.server.datatables.ModelSpawnTable;
import l1j.server.server.datatables.MonsterBookTeleportTable;
import l1j.server.server.datatables.NPCTalkDataTable;
import l1j.server.server.datatables.NpcActionTable;
import l1j.server.server.datatables.NpcChatTable;
import l1j.server.server.datatables.NpcShopTable;
import l1j.server.server.datatables.NpcSpawnTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PetTable;
import l1j.server.server.datatables.PetTypeTable;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.datatables.RaceTable;
import l1j.server.server.datatables.ResolventTable;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.datatables.SoldierTable;
import l1j.server.server.datatables.SpawnTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.datatables.UBSpawnTable;
import l1j.server.server.datatables.WeaponAddDamage;
import l1j.server.server.model.Dungeon;
import l1j.server.server.model.ElementalStoneGenerator;
import l1j.server.server.model.Getback;
import l1j.server.server.model.L1BugBearRace;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Cube;
import l1j.server.server.model.L1DeleteItemOnGround;
import l1j.server.server.model.L1NpcRegenerationTimer;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1LittleBugInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1MonsterInstance.�����ڸ��۽ð�üũ;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.model.gametime.RealTimeClock;
import l1j.server.server.model.item.L1TreasureBox;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.trap.L1WorldTraps;
import l1j.server.server.templates.L1EvaSystem;
import l1j.server.server.utils.SQLUtil;
import l1j.server.server.utils.SystemUtil;
import server.controller.BraveAvatarController;
import server.controller.ExpMonitorController;
import server.controller.InvSwapController;
import server.manager.eva;
import server.threads.pc.ArrowTrapThread;
import server.threads.pc.AutoSaveThread;
import server.threads.pc.CharacterQuickCheckThread;
import server.threads.pc.ClanBuffThread;
import server.threads.pc.DollObserverThread;
import server.threads.pc.HpMpRegenThread;
import server.threads.pc.ItemEndTimeThread;
import server.threads.pc.PremiumAinThread;
import server.threads.pc.SabuDGTime;
import server.threads.pc.SkillReiterationThread;
import server.threads.pc.SpeedHackThread;
import xnetwork.Acceptor;
import xnetwork.AcceptorHandler;
import xnetwork.SelectorThread;

//

public class GameServer implements AcceptorHandler {
	private static Logger _log = Logger.getLogger(GameServer.class.getName());
	private static GameServer _instance;
	private int SELECT_THREAD_COUNT;
	private int _ioThreadIndex;
	private SelectorThread[] _st;

	private GameServer() {

	}

	public static GameServer getInstance() {

		if (_instance == null) {
			synchronized (GameServer.class) {
				if (_instance == null)
					_instance = new GameServer();
			}
		}
		return _instance;

	}

	public static boolean �ű�����_����ġ���޴� = false;

	public void initialize() throws Exception {

		Config._IND_Q = new IND_Q();
		Config._INN_Q = new INN_Q();
		Config._quit_Q = new quit_Q();

		ObjectIdFactory.createInstance();
		L1WorldMap.createInstance();
		L1World.getInstance();
		L1WorldTraps.getInstance();
		GeneralThreadPool.getInstance();

		���Ǿ������̺��ʱ�ȭ();

		initTime();

		CharacterTable.getInstance().loadAllCharName();
		CharacterTable.getInstance().loadAllBotName();

		CharacterTable.clearOnlineStatus();

		// UBŸ�� ��Ʈ�ѷ�
		GeneralThreadPool.getInstance().execute(UbTimeController.getInstance());

		// ������ �� Ÿ�� ��Ʈ�ѷ�
		if (Config.ELEMENTAL_STONE_AMOUNT > 0) {
			GeneralThreadPool.getInstance().execute(ElementalStoneGenerator.getInstance());
		}

		NpcShopTable.getInstance();
		NpcShopSystem.getInstance();
		// ��Ʋ��
		if (Config.��Ʋ���۵�����) {
			BattleZone battleZone = BattleZone.getInstance();
			GeneralThreadPool.getInstance().execute(battleZone);
		}

		NoticeTimeController.getInstance();

		DevilController.getInstance().start();
		�ؼ�Controller.getInstance().start();
		// ���� Ÿ�� ��Ʈ�ѷ�
		GeneralThreadPool.getInstance().schedule(FishingTimeController.getInstance(), 300);

		NpcTable.getInstance();

		GeneralThreadPool.getInstance().execute(NpcChatTimeController.getInstance());

		L1DeleteItemOnGround deleteitem = new L1DeleteItemOnGround();
		deleteitem.initialize();

		if (!NpcTable.getInstance().isInitialized()) {
			throw new Exception("[GameServer] Could not initialize the npc table");
		}
		try {
			MapsTable.getInstance();
			SpawnTable.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MobGroupTable.getInstance();
		SkillsTable.getInstance();
		PolyTable.getInstance();
		ItemTable.getInstance();
		DropTable.getInstance();
		DropItemTable.getInstance();
		ShopTable.getInstance();
		NPCTalkDataTable.getInstance();
		Dungeon.getInstance();
		NpcSpawnTable.getInstance();
		IpTable.getInstance();
		UBSpawnTable.getInstance();
		PetTable.getInstance();
		ClanTable.getInstance();
		CastleTable.getInstance();
		L1CastleLocation.setCastleTaxRate(); // CastleTable �ʱ�ȭ ���� �ƴϸ� �� �ȴ�
		GetBackRestartTable.getInstance();
		DoorSpawnTable.getInstance();
		L1NpcRegenerationTimer.getInstance();
		NpcActionTable.load();
		GMCommandsConfig.load();
		Getback.loadGetBack();
		PetTypeTable.load();
		L1TreasureBox.load();
		SprTable.getInstance();
		RaceTable.getInstance();
		ResolventTable.getInstance();
		FurnitureSpawnTable.getInstance();
		NpcChatTable.getInstance();
		L1Cube.getInstance();
		SoldierTable.getInstance();
		L1BugBearRace.getInstance();
		WeaponAddDamage.getInstance(); // ���ⵥ����
		MonsterBookTeleportTable.getInstance();

		// ���� Ÿ�� ��Ʈ�ѷ�
		WarTimeController warTimeController = WarTimeController.getInstance();
		GeneralThreadPool.getInstance().execute(warTimeController);

		// ����Ʈ ��� Ÿ�� ��Ʈ�ѷ�
		GeneralThreadPool.getInstance().execute(AuctionTimeController.getInstance());
		// ����Ʈ ���� Ÿ�� ��Ʈ�ѷ�
		GeneralThreadPool.getInstance().execute(HouseTaxTimeController.getInstance());

		// ��������, ������ġ
		GeneralThreadPool.getInstance().execute(DeathMatch.getInstance());
		GeneralThreadPool.getInstance().execute(GhostHouse.getInstance());
		GeneralThreadPool.getInstance().execute(PetRacing.getInstance());

		// ȶ��
		LightSpawnTable.getInstance();
		LightTimeController.start();

		// ���峻�� ���� �ֱ�(������ Ƚ�� ���)
		ModelSpawnTable.getInstance().ModelInsertWorld();

		// ���� ����
		NoticeSystem.start();

		Gamble.get().Load();
		LindRaid.get();
		Hadin.get();
		HadinThread.get();

		TraningCenter.get();
		NpcBuyShop.getInstance();
		NpcBuyShopSell.getInstance().load();
		DeliverySystem.getInstance().Load();
		NpcTradeShop.getInstance();

		UserRankingController.getInstance();

		// �ð��� �տ�
		CrockSystem.getInstance();
		EvaSystemTable.getInstance();

		// ����ǥ ����
		RaceTicket();
		AutoSaveThread.getInstance();

		DollObserverThread.getInstance();
		HpMpRegenThread.getInstance();
		SabuDGTime.getInstance();

		SpeedHackThread.getInstance();
		PremiumAinThread.getInstance();
		CharacterQuickCheckThread.getInstance();

		BossTimer.getInstance();

		ExpMonitorController.getInstance();

		AdenShopTable.getInstance();

		ClanBuffThread.getInstance();
		SkillReiterationThread.getInstance();
		ArrowTrapThread.getInstance();
		NewTimeController.get();
		ItemEndTimeThread.get();
		try {
			DragonRaidItemTable.get();
			GeneralThreadPool.getInstance().execute(new DesertTornadoController());
			ClanHistoryTable.getInstance().dateCheckDelete();
			INNKeyDelete();
			Robot_ConnectAndRestart.getInstance();
			Robot_Hunt.getInstance();
			Robot_Bugbear.getInstance();
			Robot_Fish.getInstance();
			Robot_Location.setRLOC();

			BraveAvatarController.getInstance();

			L1MonsterInstance.���۽ð�üũ = new �����ڸ��۽ð�üũ();
			GeneralThreadPool.getInstance().schedule(L1MonsterInstance.���۽ð�üũ, 1000);

			�ɸ������̺��ʱ�ȭ();
			InvSwapController.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("[GameServer] Loading Complete!");
		System.out.println("=================================================");
		System.out.println("=================================================");

		Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());

		if (Config.���ο���Ŷ����) {
			System.gc();

			SELECT_THREAD_COUNT = 1;

			_st = new SelectorThread[SELECT_THREAD_COUNT];

			_st[0] = new SelectorThread();

			Acceptor ac = new Acceptor(Config.GAME_SERVER_PORT, _st[0], this);

			ac.startAccept();

			System.out.println(":: NEW Socket System");

			System.out.println("������� �޸� : " + SystemUtil.getUsedMemoryMB() + "MB");

			System.out.println(":: ���� ������ " + Config.GAME_SERVER_PORT + "�� ��Ʈ�� �̿��ؼ� ���� �Ǿ����ϴ�.  : Memory : "
					+ SystemUtil.getUsedMemoryMB() + " MB");

		}
	}

	@Override
	public void onAccept(Acceptor acceptor, final SocketChannel sc) {
		try {
			Socket connection = sc.socket();
			connection.setTcpNoDelay(true);
			String ClientIp = connection.getInetAddress().getHostAddress();

			connection.setSendBufferSize(2048);
			connection.setReceiveBufferSize(2048);
			if (!IpTable.getInstance().isBannedIp(ClientIp)) {
				connection.getInetAddress().toString();
				System.out.println(" ---  ���� �õ� �� ---  IP : " + ClientIp + "  Thread.C : " + Thread.activeCount()
						+ " UseMemory : " + SystemUtil.getUsedMemoryMB() + " MB");
				++_ioThreadIndex;
				final SelectorThread st = _st[_ioThreadIndex % SELECT_THREAD_COUNT];
				st.invokeLater(new Runnable() {
					@Override
					public void run() {
						try {
							new LineageClient(sc, st);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			} else {
				System.out.println("Banned Ip ���ӽõ� :" + ClientIp);
			}
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	@Override
	public void onError(Acceptor acceptor, Exception ex) {
		_log.log(Level.SEVERE, ex.getLocalizedMessage(), ex);
		// do nothing
	}

	private void ���Ǿ������̺��ʱ�ȭ() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("TRUNCATE shop_npc");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

	}

	private void �ɸ������̺��ʱ�ȭ() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("TRUNCATE character_shop");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

	}

	class iqlog {
		public String name = "";
		public int count = 0;
		public String account = "";
		public String ip = "";
		public String host = "";
	}

	private void initTime() {
		GameTimeClock.init(); // ���� �ð� �ð�
		RealTimeClock.init(); // ���� �ð� �ð�
	}

	public void �������κ��Ƶ�üũ() {
		int level_cut = 70; // ���� ���ƾߵ�
		int level_cut2 = 1; // ���� ���ƾߵ�
		int adena_cut = 1000000; // ���� Ŀ�ߵ�
		Connection con2 = null;
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		ArrayList<String> ll = new ArrayList<String>();
		try {
			con2 = L1DatabaseFactory.getInstance().getConnection();
			pstm2 = con2.prepareStatement("SELECT * FROM accounts WHERE banned=0");
			rs2 = pstm2.executeQuery();
			while (rs2.next()) {
				String account = rs2.getString("login");
				Connection con = L1DatabaseFactory.getInstance().getConnection();
				PreparedStatement pstm1 = con.prepareStatement("SELECT * FROM characters WHERE account_name=?");
				pstm1.setString(1, account);
				ResultSet rs = pstm1.executeQuery();
				boolean ck = false;
				while (rs.next()) {
					int level = rs.getInt("level");
					if (level > level_cut || level_cut2 > level)
						ck = true;
				}
				SQLUtil.close(rs);
				SQLUtil.close(pstm1);
				SQLUtil.close(con);
				if (!ck)
					ll.add(account);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
		}
		StringBuilder sbb = new StringBuilder();
		for (String accountName : ll) {
			try {
				con2 = L1DatabaseFactory.getInstance().getConnection();
				pstm2 = con2.prepareStatement("SELECT * FROM characters WHERE account_name=?");
				pstm2.setString(1, accountName);
				rs2 = pstm2.executeQuery();
				StringBuilder sb = new StringBuilder();
				sb.append("*** ���� : " + accountName + "\n");
				int total = 0;
				boolean ck2 = false;
				while (rs2.next()) {
					String name = rs2.getString("char_name");
					int objid = rs2.getInt("objid");
					int level = rs2.getInt("level");
					sb.append(" - �ɸ��� : " + name + "  :: ���� : " + level + " :: �ݾ� : ");
					Connection con = L1DatabaseFactory.getInstance().getConnection();
					PreparedStatement pstm1 = con
							.prepareStatement("SELECT * FROM character_items WHERE char_id=? AND item_id=?");
					pstm1.setInt(1, objid);
					pstm1.setInt(2, 40308);
					ResultSet rs = pstm1.executeQuery();
					int count = 0;
					while (rs.next()) {
						count = rs.getInt("count");
						total += count;
						if (count > adena_cut)
							ck2 = true;
					}
					sb.append(count + "\n");
					SQLUtil.close(rs);
					SQLUtil.close(pstm1);
					SQLUtil.close(con);
				}
				Connection con = L1DatabaseFactory.getInstance().getConnection();
				PreparedStatement pstm1 = con
						.prepareStatement("SELECT * FROM character_warehouse WHERE account_name=? AND item_id=?");
				pstm1.setString(1, accountName);
				pstm1.setInt(2, 40308);
				ResultSet rs = pstm1.executeQuery();
				int count = 0;
				while (rs.next()) {
					count = rs.getInt("count");
					total += count;
					if (count > adena_cut)
						ck2 = true;
				}
				sb.append(" - â�� : " + count + "\n");
				SQLUtil.close(rs);
				SQLUtil.close(pstm1);
				SQLUtil.close(con);

				sb.append("   �� �ݾ� : " + total + "\n\n");
				if (ck2 && total > adena_cut) {
					sbb.append(sb.toString());

				}
			} catch (SQLException e) {
				_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			} finally {
				SQLUtil.close(rs2);
				SQLUtil.close(pstm2);
				SQLUtil.close(con2);
			}
		}
		�α�����(sbb);
	}

	// private BufferedWriter writer;
	public void �α�����(StringBuilder sb) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"));
			writer.write(sb.toString());
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void ����üũ() {
		Connection con2 = null;
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		FastTable<String> f20 = new FastTable<String>();
		FastTable<String> f15 = new FastTable<String>();
		FastTable<String> f10 = new FastTable<String>();
		FastTable<String> f5 = new FastTable<String>();
		try {
			con2 = L1DatabaseFactory.getInstance().getConnection();

			String qu = "password";
			pstm2 = con2.prepareStatement("SELECT " + qu + " FROM accounts");
			rs2 = pstm2.executeQuery();

			FastTable<String> charname = new FastTable<String>();
			while (rs2.next()) {
				String password = rs2.getString(qu);

				boolean ck = false;
				for (String nn : charname) {
					if (nn.equalsIgnoreCase(password)) {
						ck = true;
						break;
					}
				}
				if (ck)
					continue;

				charname.add(password);

				Connection con = L1DatabaseFactory.getInstance().getConnection();
				PreparedStatement pstm1 = con.prepareStatement("SELECT login FROM accounts WHERE " + qu + "=?");
				pstm1.setString(1, password);
				ResultSet rs = pstm1.executeQuery();
				StringBuilder sb = new StringBuilder();
				sb.append("----------------------");
				short count = 0;
				while (rs.next()) {
					String acc = rs.getString("login");
					sb.append(acc + ",");
					count++;
				}
				// if(!ck){
				sb.append("----------------------");
				// if(count > 5)
				// System.out.println("Count: "+count+" > "+sb.toString());
				if (count >= 20)
					f20.add(password + " Count: " + count + " > " + sb.toString());
				else if (count >= 15)
					f15.add(password + " Count: " + count + " > " + sb.toString());
				else if (count >= 10)
					f10.add(password + " Count: " + count + " > " + sb.toString());
				else if (count >= 5)
					f5.add(password + " Count: " + count + " > " + sb.toString());
				// }
				SQLUtil.close(rs);
				SQLUtil.close(pstm1);
				SQLUtil.close(con);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
			for (String s : f20) {
				System.out.println(s);
			}
			System.out.println("20�̻� : " + f20.size() + "��\n");
			for (String s : f15) {
				System.out.println(s);
			}
			System.out.println("15�̻� : " + f15.size() + "��\n");
			for (String s : f10) {
				System.out.println(s);
			}
			System.out.println("10�̻� : " + f10.size() + "��\n");
			for (String s : f5) {
				System.out.println(s);
			}
			System.out.println("5�̻� : " + f5.size() + "��\n");
			System.out.println("�� : " + (f20.size() + f15.size() + f10.size() + f5.size()));
			System.out.println("��");
		}
	}

	public void �κ��Ƶ�üũ() {
		Connection con2 = null;
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		try {
			int count2 = 0;
			con2 = L1DatabaseFactory.getInstance().getConnection();
			pstm2 = con2.prepareStatement("SELECT * FROM character_items WHERE item_id=? order by count desc");
			pstm2.setInt(1, 40308);
			rs2 = pstm2.executeQuery();
			while (rs2.next()) {
				Connection con = L1DatabaseFactory.getInstance().getConnection();
				PreparedStatement pstm1 = con.prepareStatement("SELECT * FROM characters WHERE objid=?");
				pstm1.setInt(1, rs2.getInt("char_id"));
				ResultSet rs = pstm1.executeQuery();
				while (rs.next()) {
					String account = rs.getString("account_name");
					String name = rs.getString("char_name");
					int level = rs.getInt("level");
					if (level > 50)
						continue;
					int count = rs2.getInt("count");
					if (count > 5200000) {
						System.out.println("����:" + account + " �̸�:" + name + " �Ƶ���:" + count);
						count2++;
					}
				}
				SQLUtil.close(rs);
				SQLUtil.close(pstm1);
				SQLUtil.close(con);
			}
			System.out.println("���� �� : " + count2);
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
		}
	}

	public void ����üüũ() {
		Connection con2 = null;
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		try {
			con2 = L1DatabaseFactory.getInstance().getConnection();
			pstm2 = con2.prepareStatement("SELECT * FROM resolvent");
			rs2 = pstm2.executeQuery();
			while (rs2.next()) {
				int itemid = rs2.getInt("item_id");
				int count = rs2.getInt("crystal_count");

				Connection con = L1DatabaseFactory.getInstance().getConnection();
				PreparedStatement pstm1 = con.prepareStatement("SELECT * FROM shop WHERE item_id=?");
				pstm1.setInt(1, itemid);
				ResultSet rs = pstm1.executeQuery();
				while (rs.next()) {
					int price = rs.getInt("selling_price");
					int pack = rs.getInt("pack_count");
					if (price == -1 || price == 1000 || price == 1)
						continue;
					if (price < count * 5 * (pack > 0 ? pack : 1))
						System.out.println(itemid + " > " + price + " > " + (count * 5));
				}
				SQLUtil.close(rs);
				SQLUtil.close(pstm1);
				SQLUtil.close(con);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
		}
	}

	public void �����Ǹž�üũ() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM shop");
			rs = pstm.executeQuery();
			while (rs.next()) {
				int npcid = rs.getInt("npc_id");
				int itemid = rs.getInt("item_id");
				int price = rs.getInt("selling_price");
				int pack = rs.getInt("pack_count");
				// �׽�Ʈ NPC
				if (npcid == 4500162 || npcid == 4220019 || npcid == 4220700 || npcid == 8000055)
					continue;
				if (price == -1)
					continue;
				int totalprice = price / (pack > 0 ? pack : 1);

				Connection con1 = L1DatabaseFactory.getInstance().getConnection();
				PreparedStatement pstm1 = con.prepareStatement("SELECT * FROM shop WHERE item_id=?");
				pstm1.setInt(1, itemid);
				ResultSet rs1 = pstm1.executeQuery();
				while (rs1.next()) {
					int sell_price = rs1.getInt("purchasing_price");
					if (sell_price == -1)
						continue;
					if (totalprice < sell_price) {
						System.out.println(npcid + " > " + itemid + " > " + totalprice + " > " + sell_price);
					}
				}
				SQLUtil.close(rs1);
				SQLUtil.close(pstm1);
				SQLUtil.close(con1);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	/**
	 * �¶������� �÷��̾� ��ο� ���ؼ� kick, ĳ���� ������ ������ �Ѵ�.
	 */
	public void disconnectAllCharacters() {
		try {
			// ���� ǥ ���. (��� ���� -> ���� �� Ƽ�� �Ǹ� ����)
			try {
				if (!L1BugBearRace.getInstance()._goal) {
					for (L1LittleBugInstance b : L1BugBearRace.getInstance()._littleBug) {
						if (b == null)
							continue;
						L1BugBearRace.getInstance().race_divAdd(L1BugBearRace.getInstance().getRoundId(), b.getNumber(),
								1);
					}
				}
			} catch (Exception e) {
			}
			try {
				// ���ٽý��� ����
				L1EvaSystem es = EvaSystemTable.getInstance().getSystem(1);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				String fm = sdf.format(es.getEvaTime().getTime());
				if (es != null && fm.equalsIgnoreCase("2014")) {
					EvaSystemTable.getInstance().updateSystem(es);
				}
				sdf = null;
			} catch (Exception e) {
			}
			try {
				for (L1FieldObjectInstance foi : L1World.getInstance().getAllField()) {
					if (foi == null || foi._destroyed || foi.isDead() || foi.Potal_Open_pcid == 0)
						continue;
					String AccountName = null;
					L1Object obj = L1World.getInstance().findObject(foi.Potal_Open_pcid);
					if (obj != null && obj instanceof L1PcInstance) {
						AccountName = ((L1PcInstance) obj).getAccountName();
					} else {
						AccountName = LoadAccount(foi.Potal_Open_pcid);
					}
					if (AccountName == null)
						continue;
					SupplementaryService pwh = WarehouseManager.getInstance().getSupplementaryService(AccountName);
					L1ItemInstance item = ItemTable.getInstance().createItem(60422);
					item.setIdentified(true);
					item.setCount(1);
					pwh.storeTradeItem(item);
					foi.deleteMe();
				}
			} catch (Exception e) {
			}
			Collection<L1PcInstance> players = L1World.getInstance().getAllPlayers();
			// ��� ĳ���� ����
			for (L1PcInstance pc : players) {
				if (pc instanceof L1RobotInstance) {
					continue;
				}
				if (pc == null)
					continue; // pc �� ������.
				if (pc.getNetConnection() != null) {
					try {
						if (pc.getMapId() >= 9000 && pc.getMapId() <= 9099) { // ����
																				// �δ�
							pc.getInventory().storeItem(500017, 1);
						} else if (pc.getMapId() >= 2102 && pc.getMapId() <= 2151) { // ���
																						// �δ�
							pc.getInventory().storeItem(6022, 1);
						}
						pc.save();
						pc.saveInventory();
						pc.getNetConnection().setActiveChar(null);
						pc.getNetConnection().kick();
						pc.getNetConnection().quitGame(pc);
						L1World.getInstance().removeObject(pc);
					} catch (Exception e) {
						System.out.println("disconnectallcharacters error");
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String LoadAccount(int potal_Open_pcid) {

		String account = null;
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM characters WHERE objid=?");
			pstm.setInt(1, potal_Open_pcid);
			rs = pstm.executeQuery();
			if (!rs.next()) {
				return null;
			}
			account = rs.getString("account_name");
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			return null;
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return account;
	}

	public int saveAllCharInfo() {
		// exception �߻��ϸ� -1 ����, �ƴϸ� ������ �ο� �� ����
		int cnt = 0;
		try {
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc instanceof L1RobotInstance) {
					continue;
				}
				cnt++;
				pc.save();
				pc.saveInventory();
			}
		} catch (Exception e) {
			return -1;
		}

		return cnt;
	}

	/**
	 * �¶������� �÷��̾ ���ؼ� kick , ĳ���� ������ ������ �Ѵ�.
	 */

	public int �Ƶ������� = 50000000;

	public void disconnectChar(String name) {
		L1PcInstance pc = L1World.getInstance().getPlayer(name);
		L1PcInstance Player = pc;
		synchronized (pc) {
			pc.getNetConnection().kick();
			pc.getNetConnection().quitGame(Player);
			Player.logout();
		}
	}

	public String[] getiplist() {
		return iplist.toArray(new String[iplist.size()]);
	}

	private ArrayList<String> iplist = new ArrayList<String>();

	public boolean checkip(String cl) {
		if (iplist.contains(cl)) {
			return true;
		} else {
			return false;
		}

	}

	public void addipl(String cl) {
		iplist.add(cl);
	}

	public void removeip(String cl) {
		if (iplist.contains(cl)) {
			iplist.remove(cl);
		}
	}

	public String[] getaclist() {
		return aclist.toArray(new String[aclist.size()]);
	}

	private ArrayList<String> aclist = new ArrayList<String>();

	public boolean checkac(String cl) {
		if (aclist.contains(cl)) {
			return true;
		} else {
			return false;
		}

	}

	public void addac(String cl) {
		aclist.add(cl);
	}

	public void removeac(String cl) {
		if (aclist.contains(cl)) {
			aclist.remove(cl);
		}
	}

	public String[] getbuglist() {
		return buglist.toArray(new String[buglist.size()]);
	}

	private ArrayList<String> buglist = new ArrayList<String>();

	public boolean checkbug(String cl) {
		if (buglist.contains(cl)) {
			return true;
		} else {
			return false;
		}

	}

	public void addbug(String cl) {
		buglist.add(cl);
	}

	public void removebug(String cl) {
		if (buglist.contains(cl)) {
			buglist.remove(cl);
		}
	}

	private class ServerShutdownThread extends Thread {
		private final int _secondsCount;

		public ServerShutdownThread(int secondsCount) {
			_secondsCount = secondsCount;
		}

		@Override
		public void run() {
			L1World world = L1World.getInstance();
			try {
				int secondsCount = _secondsCount;
				System.out.println("[GameServer] ��� ��, ������ ���� �մϴ�.");
				System.out.println("[GameServer] ������ ��ҿ��� �α׾ƿ� �� �ּ���.");
				world.broadcastServerMessage("��� ��, ������ ���� �մϴ�.");
				world.broadcastServerMessage("������ ��ҿ��� �α׾ƿ� �� �ּ���.");
				while (0 < secondsCount) {
					if (secondsCount <= 30 || secondsCount == 40 || secondsCount == 50) {
						System.out.println("[GameServer] ������ " + secondsCount + "�� �Ŀ� ���� �˴ϴ�. ������ �ߴ��� �ּ���.");
						world.broadcastServerMessage("�������� " + secondsCount + "�� �� �ߴܵ˴ϴ�. ������ ������ �����Ͽ� �ֽñ� �ٶ��ϴ�.");
					} else {
						if (secondsCount % 60 == 0) {
							System.out.println("[GameServer] ������ " + secondsCount / 60 + "�� �Ŀ� ���� �˴ϴ�.");
							world.broadcastServerMessage(
									"�������� " + secondsCount / 60 + "�� �� �ߴܵ˴ϴ�. ������ ������ �����Ͽ� �ֽñ� �ٶ��ϴ�.");
						}
					}
					Thread.sleep(1000);
					secondsCount--;
				}
				shutdown();
			} catch (InterruptedException e) {
				System.out.println("[GameServer] ���� ���ᰡ �ߴܵǾ����ϴ�. ������ ���� �������Դϴ�.");
				world.broadcastServerMessage("���� ���ᰡ �ߴܵǾ����ϴ�. ������ ���� �������Դϴ�.");
				return;
			}
		}
	}

	private ServerShutdownThread _shutdownThread = null;

	public synchronized void shutdownWithCountdown(int secondsCount) {
		if (_shutdownThread != null) {
			// �̹� ���ٿ� �䱸�� �ϰ� �ִ�

			return;
		}
		_shutdownThread = new ServerShutdownThread(secondsCount);
		GeneralThreadPool.getInstance().execute(_shutdownThread);
	}

	public void shutdown() {
		if (_shutdownThread != null) {
			disconnectAllCharacters();
			InvSwapController.getInstance().initDB();
			eva.savelog();
			System.exit(0);
		}
	}

	public synchronized void abortShutdown() {
		if (_shutdownThread == null) {

			return;
		}

		_shutdownThread.interrupt();
		_shutdownThread = null;
	}

	public void Halloween() {
		Connection con = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm1 = null;
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement(
					"DELETE FROM character_items WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm1 = con.prepareStatement(
					"DELETE FROM character_elf_warehouse WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm2 = con.prepareStatement(
					"DELETE FROM clan_warehouse WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm3 = con.prepareStatement(
					"DELETE FROM character_warehouse WHERE item_id IN (20380, 21060, 256) AND enchantlvl < 8");
			pstm3.executeUpdate();
			pstm2.executeUpdate();
			pstm1.executeUpdate();
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(pstm1);
			SQLUtil.close(pstm2);
			SQLUtil.close(pstm3);
			SQLUtil.close(con);
		}
	}

	public void RaceTicket() {
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_items WHERE item_id >= 8000000");
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void INNKeyDelete() {
		Connection con = null;
		PreparedStatement pstm = null;

		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_items WHERE item_id = 49312 OR item_id = 40312");
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void DuplicationLoginCheck(String name, String msg) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("INSERT INTO duplication_login_check SET name=?, msg=?");
			pstm.setString(1, name);
			pstm.setString(2, msg);
			pstm.executeUpdate();
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void �����α��Ǹž�üũ() {
		Connection con2 = null;
		PreparedStatement pstm2 = null;
		ResultSet rs2 = null;
		FastTable<log_shop_total> list = new FastTable<log_shop_total>();
		try {
			con2 = L1DatabaseFactory.getInstance().getConnection();
			pstm2 = con2.prepareStatement("SELECT * FROM log_shop");
			rs2 = pstm2.executeQuery();
			while (rs2.next()) {
				String name = rs2.getString("user_name");
				int count = rs2.getInt("total_price");
				String itemname = rs2.getString("item_name");
				if (itemname.equalsIgnoreCase("���̽� ǥ"))
					continue;
				boolean ck = false;
				for (log_shop_total lst : list) {
					if (lst.name.equalsIgnoreCase(name)) {
						lst.total += count;
						ck = true;
						break;
					}
				}
				if (!ck) {
					log_shop_total lst = new log_shop_total();
					lst.name = name;
					lst.total = count;
					list.add(lst);
				}
			}

			StringBuilder sb = new StringBuilder();
			for (int i = 1; i <= 10; i++) {
				String temp_name = "";
				long temp_t = 0;
				for (log_shop_total lst : list) {
					if (temp_t < lst.total) {
						temp_t = lst.total;
						temp_name = lst.name;
					}
				}
				if (!temp_name.equalsIgnoreCase("")) {
					sb.append(i + ". �Ǹ���: " + temp_name + " �Ǹž׼�: " + temp_t + "\n");
					for (log_shop_total lst : list.toArray(new log_shop_total[list.size()])) {
						if (lst.name.equalsIgnoreCase(temp_name)) {
							list.remove(lst);
							break;
						}
					}
				}
			}
			System.out.println(sb.toString());
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs2);
			SQLUtil.close(pstm2);
			SQLUtil.close(con2);
		}
	}

	class log_shop_total {
		public String name = "";
		public long total = 0;
	}
}
