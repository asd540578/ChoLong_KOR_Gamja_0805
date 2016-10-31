package l1j.server.GameSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.Warehouse.ClanWarehouse;
import l1j.server.Warehouse.PrivateWarehouse;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.clientpackets.C_ItemUSe;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1MobGroupSpawn;
import l1j.server.server.model.L1NpcDeleteTimer;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PcInventory;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1TeleporterInstance;
import l1j.server.server.model.gametime.GameTimeClock;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.utils.L1SpawnUtil;
import l1j.server.server.utils.SQLUtil;

public class BossTimer implements Runnable {
	private static BossTimer _instance;

	public static BossTimer getInstance() {
		if (_instance == null) {
			_instance = new BossTimer();
		}
		return _instance;
	}

	public boolean ����� = false;

	public boolean ������� = false;

	private Date day = new Date(System.currentTimeMillis());

	public BossTimer() {
		// super("l1j.server.GameSystem.BossTimer");
		GeneralThreadPool.getInstance().execute(this);
	}

	@Override
	public void run() {
		try {
			// while (true) {
			day.setTime(System.currentTimeMillis());
			boss();
			fairlyQueen();
			MerchantOneDayBuyReset();
			// Thread.sleep(1000L);
			// }
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

	private void MerchantOneDayBuyReset() {
		// TODO �ڵ� ������ �޼ҵ� ����
		if (day.getMinutes() == 0 && day.getSeconds() == 0 && day.getHours() == 0) {
			L1MerchantInstance.resetOneDayBuy();
			C_ItemUSe.reset�ð����׾Ƹ�Ƚ��();
			C_ItemUSe.reset�����ָӴ�_����Ƚ��();
			// C_Shop.reset������������Ƚ��();
		}
	}

	private boolean QueenAMspawn = false;
	private boolean QueenPMspawn = false;

	private void fairlyQueen() {
		long time = GameTimeClock.getInstance().getGameTime().getSeconds() % 86400;
		// if(time < 0){
		// time=time-time-time;
		// }
		if ((time > 60 * 60 * 9 && time < 60 * 60 * 10) || (time < -60 * 60 * 9 && time > -60 * 60 * 10)) {
			if (!QueenAMspawn) {
				QueenAMspawn = true;
				// 9~12
				fairlyQueenSpawn();
			}
		} else {
			QueenAMspawn = false;
		}

		if ((time > 60 * 60 * 19 && time < 60 * 60 * 20) || (time < -60 * 60 * 19 && time > -60 * 60 * 20)) {
			if (!QueenPMspawn) {
				QueenPMspawn = true;
				// 19~24
				fairlyQueenSpawn();
			}
		} else {
			QueenPMspawn = false;
		}
	}

	private void fairlyQueenSpawn() {
		Random _rnd = new Random(System.nanoTime());
		int delay = _rnd.nextInt(600000 * 3) + 1;
		GeneralThreadPool.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				Random _rnd = new Random(System.nanoTime());
				int deletetime = (_rnd.nextInt(11) + 10) * 60000;
				L1NpcInstance n = L1SpawnUtil.spawn2(33164, 32284, (short) 4, 70852, 0, deletetime, 0);
				L1MobGroupSpawn.getInstance().doSpawn(n, 107, true, false);
				for (L1NpcInstance npc : n.getMobGroupInfo().getMember()) {
					L1NpcDeleteTimer timer = new L1NpcDeleteTimer(npc, deletetime);
					timer.begin();
				}
			}

		}, delay);
	}

	private static int �ҷ�������Ʈ[] = { 90085, 90086, 90087, 90088, 90089, 90090, 90091, 90092, 160423, 435000, 160510,
			160511, 21123, 21269 };
	private static int �ҷ�������Ʈ2[] = { 90085, 90086, 90087, 90088, 90089, 90090, 90091, 90092, 160423, 435000, 160510,
			160511, 21123 };

	public synchronized static void EventItemDelete() {
		try {
			for (L1PcInstance tempPc : L1World.getInstance().getAllPlayers()) {
				if (tempPc == null)
					continue;
				for (int i = 0; i < �ҷ�������Ʈ.length; i++) {
					L1ItemInstance[] item = tempPc.getInventory().findItemsId(�ҷ�������Ʈ[i]);

					if (item != null && item.length > 0) {
						for (int o = 0; o < item.length; o++) {
							if (item[o].getItemId() == 21269) {
								if (item[o].getEnchantLevel() >= 6) {
									if (item[o].getBless() >= 128)
										item[o].setBless(128);
									else
										item[o].setBless(0);
									tempPc.getInventory().updateItem(item[o], L1PcInventory.COL_BLESS);
									tempPc.getInventory().saveItem(item[o], L1PcInventory.COL_BLESS);
									continue;
								}
							}
							if (item[o].isEquipped()) {
								tempPc.getInventory().setEquipped(item[o], false); // �߰����ּ���.
							}
							tempPc.getInventory().removeItem(item[o]);
						}
					}
					try {
						PrivateWarehouse pw = WarehouseManager.getInstance()
								.getPrivateWarehouse(tempPc.getAccountName());
						L1ItemInstance[] item2 = pw.findItemsId(�ҷ�������Ʈ[i]);
						if (item2 != null && item2.length > 0) {
							for (int o = 0; o < item2.length; o++) {
								if (item[o].getItemId() == 21269) {
									if (item[o].getEnchantLevel() >= 6) {
										if (item[o].getBless() >= 128)
											item[o].setBless(128);
										else
											item[o].setBless(0);
										tempPc.getInventory().updateItem(item[o], L1PcInventory.COL_BLESS);
										tempPc.getInventory().saveItem(item[o], L1PcInventory.COL_BLESS);
										continue;
									}
								}
								pw.removeItem(item2[o]);
							}
						}
					} catch (Exception e) {
					}
					try {
						if (tempPc.getClanid() > 0) {
							ClanWarehouse cw = WarehouseManager.getInstance().getClanWarehouse(tempPc.getClanname());
							L1ItemInstance[] item3 = cw.findItemsId(�ҷ�������Ʈ[i]);
							if (item3 != null && item3.length > 0) {
								for (int o = 0; o < item3.length; o++) {
									if (item3[o].getItemId() == 21269) {
										if (item3[o].getEnchantLevel() >= 6) {
											if (item3[o].getBless() >= 128)
												item3[o].setBless(128);
											else
												item3[o].setBless(0);
											tempPc.getInventory().updateItem(item3[o], L1PcInventory.COL_BLESS);
											tempPc.getInventory().saveItem(item3[o], L1PcInventory.COL_BLESS);
											continue;
										}
									}
									cw.removeItem(item3[o]);
								}
							}
						}
					} catch (Exception e) {
					}
					try {
						Collection<L1NpcInstance> pList = tempPc.getPetList();
						if (pList.size() > 0) {
							for (L1NpcInstance npc : pList) {
								L1ItemInstance[] pitem = npc.getInventory().findItemsId(�ҷ�������Ʈ[i]);
								if (pitem != null && pitem.length > 0) {
									for (int o = 0; o < pitem.length; o++) {
										if (pitem[o].getItemId() == 21269) {
											if (pitem[o].getEnchantLevel() >= 6) {
												if (pitem[o].getBless() >= 128)
													pitem[o].setBless(128);
												else
													pitem[o].setBless(0);
												tempPc.getInventory().updateItem(pitem[o], L1PcInventory.COL_BLESS);
												tempPc.getInventory().saveItem(pitem[o], L1PcInventory.COL_BLESS);
												continue;
											}
										}
										npc.getInventory().removeItem(pitem[o]);
									}
								}
							}
						}
					} catch (Exception e) {
					}
				}
			}
			try {
				for (L1Object obj : L1World.getInstance().getAllItem()) {
					if (!(obj instanceof L1ItemInstance))
						continue;
					L1ItemInstance temp_item = (L1ItemInstance) obj;
					if (temp_item.getItemOwner() == null || !(temp_item.getItemOwner() instanceof L1RobotInstance)) {
						if (temp_item.getX() == 0 && temp_item.getY() == 0)
							continue;
					}
					for (int ii = 0; ii < �ҷ�������Ʈ.length; ii++) {
						if (�ҷ�������Ʈ[ii] == temp_item.getItemId()) {
							if (temp_item.getItemId() == 21269) {
								if (temp_item.getEnchantLevel() >= 6) {
									if (temp_item.getBless() >= 128)
										temp_item.setBless(128);
									else
										temp_item.setBless(0);
									continue;
								}
							}
							L1Inventory groundInventory = L1World.getInstance().getInventory(temp_item.getX(),
									temp_item.getY(), temp_item.getMapId());
							groundInventory.removeItem(temp_item);
							break;
						}
					}

				}
			} catch (Exception e) {
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < �ҷ�������Ʈ2.length; i++) {
				sb.append(+�ҷ�������Ʈ2[i]);
				if (i < �ҷ�������Ʈ2.length - 1) {
					sb.append(",");
				}
			}
			Delete(sb.toString());
			Delete21269();
			BlessUpdate(21269);
			BlessUpdatewearehose(21269);
			BlessUpdateclanwarehouse(21269);
			BlessUpdateelfwarehouse(21269);
		} catch (Exception e) {
		}
	}

	private static void Delete(String id_name) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("delete FROM character_items WHERE item_id IN (" + id_name + ")");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM character_warehouse WHERE item_id in (" + id_name + ")");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM clan_warehouse WHERE item_id in (" + id_name + ")");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM character_elf_warehouse WHERE item_id in (" + id_name + ")");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static void Delete21269() {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("delete FROM character_items WHERE item_id=21269 AND enchantlvl < 6");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM character_warehouse WHERE item_id=21269 AND enchantlvl < 6");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM clan_warehouse WHERE item_id=21269 AND enchantlvl < 6");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
		}
		try {
			pstm = con.prepareStatement("delete FROM character_elf_warehouse WHERE item_id=21269 AND enchantlvl < 6");
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void BlessUpdate(int itemid) {
		Connection con = null;
		Connection con2 = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT id, bless, enchantlvl FROM character_items WHERE item_id=?");
			// pstm =
			// con.prepareStatement("SELECT * FROM character_items WHERE
			// item_id=?");
			pstm.setInt(1, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int bless = rs.getInt("bless");
				int ent = rs.getInt("enchantlvl");
				if (ent < 6)
					continue;
				try {
					con2 = L1DatabaseFactory.getInstance().getConnection();
					pstm2 = con2.prepareStatement("UPDATE character_items SET bless =? WHERE id=?");
					pstm2.setInt(1, bless > 128 ? 128 : 0);
					pstm2.setInt(2, id);
					pstm2.executeUpdate();
				} finally {
					SQLUtil.close(pstm2);
					SQLUtil.close(con2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void BlessUpdatewearehose(int itemid) {
		Connection con = null;
		Connection con2 = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT id, bless, enchantlvl FROM character_warehouse WHERE item_id=?");
			// pstm =
			// con.prepareStatement("SELECT * FROM character_items WHERE
			// item_id=?");
			pstm.setInt(1, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int bless = rs.getInt("bless");
				int ent = rs.getInt("enchantlvl");
				if (ent < 6)
					continue;
				try {
					con2 = L1DatabaseFactory.getInstance().getConnection();
					pstm2 = con2.prepareStatement("UPDATE character_warehouse SET bless =? WHERE id=?");
					pstm2.setInt(1, bless > 128 ? 128 : 0);
					pstm2.setInt(2, id);
					pstm2.executeUpdate();
				} finally {
					SQLUtil.close(pstm2);
					SQLUtil.close(con2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void BlessUpdateclanwarehouse(int itemid) {
		Connection con = null;
		Connection con2 = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT id, bless, enchantlvl FROM clan_warehouse WHERE item_id=?");
			// pstm =
			// con.prepareStatement("SELECT * FROM character_items WHERE
			// item_id=?");
			pstm.setInt(1, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int bless = rs.getInt("bless");
				int ent = rs.getInt("enchantlvl");
				if (ent < 6)
					continue;
				try {
					con2 = L1DatabaseFactory.getInstance().getConnection();
					pstm2 = con2.prepareStatement("UPDATE clan_warehouse SET bless =? WHERE id=?");
					pstm2.setInt(1, bless > 128 ? 128 : 0);
					pstm2.setInt(2, id);
					pstm2.executeUpdate();
				} finally {
					SQLUtil.close(pstm2);
					SQLUtil.close(con2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public static void BlessUpdateelfwarehouse(int itemid) {
		Connection con = null;
		Connection con2 = null;
		PreparedStatement pstm = null;
		PreparedStatement pstm2 = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT id, bless, enchantlvl FROM character_elf_warehouse WHERE item_id=?");
			// pstm =
			// con.prepareStatement("SELECT * FROM character_items WHERE
			// item_id=?");
			pstm.setInt(1, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				int bless = rs.getInt("bless");
				int ent = rs.getInt("enchantlvl");
				if (ent < 6)
					continue;
				try {
					con2 = L1DatabaseFactory.getInstance().getConnection();
					pstm2 = con2.prepareStatement("UPDATE character_elf_warehouse SET bless =? WHERE id=?");
					pstm2.setInt(1, bless > 128 ? 128 : 0);
					pstm2.setInt(2, id);
					pstm2.executeUpdate();
				} finally {
					SQLUtil.close(pstm2);
					SQLUtil.close(con2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	private static final int[][] ��������loc = { // ����ŷ,���ɰ�����
			{ 32928, 32809, 430 }, { 32920, 32864, 430 }, { 32909, 32918, 430 }, { 32875, 32942, 430 },
			{ 32836, 32970, 430 }, { 32775, 32924, 430 }, { 32737, 32919, 430 }, { 32741, 32876, 430 },
			{ 32767, 32848, 430 }, { 32810, 32817, 430 }, { 32849, 32814, 430 }, { 32862, 32833, 430 },
			{ 32819, 32857, 430 } };
	private static final int[][] ���loc = { // ���θ�׽�Ÿ
			{ 34231, 33453, 4 }, { 34244, 33408, 4 }, { 34271, 33383, 4 }, { 34255, 33365, 4 }, { 34254, 33326, 4 },
			{ 34242, 33285, 4 }, { 34225, 33321, 4 }, { 34265, 33240, 4 }, { 34275, 33216, 4 }, { 34280, 33181, 4 },
			{ 34278, 33139, 4 }, { 34266, 33118, 4 }, { 34253, 33140, 4 } };
	private static final int[][] ���ž�̹�loc = { // ���ž�̹�
			{ 32903, 32798, 280 }, { 32873, 32758, 280 }, { 32905, 32736, 280 }, { 32936, 32767, 280 },
			{ 32807, 32828, 281 }, { 32769, 32795, 281 }, { 32773, 32821, 281 }, { 32775, 32843, 281 },
			{ 32739, 32826, 281 }, { 32752, 32793, 282 }, { 32761, 32806, 282 }, { 32755, 32860, 282 },
			{ 32746, 32818, 282 }, { 32803, 32858, 282 }, { 32798, 32871, 282 }, { 32808, 32848, 283 },
			{ 32806, 32813, 283 }, { 32788, 32805, 283 }, { 32759, 32796, 283 }, { 32738, 32808, 283 },
			{ 32749, 32835, 283 }, { 32757, 32859, 283 }, { 32693, 32799, 284 }, { 32728, 32791, 284 },
			{ 32742, 32818, 284 }, { 32744, 32855, 284 }, { 32707, 32862, 284 }, { 32677, 32855, 284 },
			{ 32703, 32820, 284 }, { 32717, 32836, 284 } };
	private static final int[][] �ϵ�н�loc = { // �ϵ�н�
			{ 32752, 32793, 282 }, { 32761, 32806, 282 }, { 32755, 32860, 282 }, { 32746, 32818, 282 },
			{ 32803, 32858, 282 }, { 32798, 32871, 282 }, { 32808, 32848, 283 }, { 32806, 32813, 283 },
			{ 32788, 32805, 283 }, { 32759, 32796, 283 }, { 32738, 32808, 283 }, { 32749, 32835, 283 },
			{ 32757, 32859, 283 }, { 32693, 32799, 284 }, { 32728, 32791, 284 }, { 32742, 32818, 284 },
			{ 32744, 32855, 284 }, { 32707, 32862, 284 }, { 32677, 32855, 284 }, { 32703, 32820, 284 },
			{ 32717, 32836, 284 } };
	private static final int[][] �渶����loc = { // �渶����
			{ 32807, 32828, 281 }, { 32769, 32795, 281 }, { 32773, 32821, 281 }, { 32775, 32843, 281 },
			{ 32739, 32826, 281 }, { 32752, 32793, 282 }, { 32761, 32806, 282 }, { 32755, 32860, 282 },
			{ 32746, 32818, 282 }, { 32803, 32858, 282 }, { 32798, 32871, 282 } };

	private static final int[][] �巹��ũloc = { { 33405, 32411, 4 }, { 33361, 32382, 4 }, { 33402, 32342, 4 },
			{ 33317, 32319, 4 }, { 33356, 32355, 4 } };
	private int _12�ð��ֱ� = (60000 * 60 * 8) + (60000 * 40);
	private int _6�ð��ֱ� = (60000 * 60 * 5) + (60000 * 40);
	private int _4�ð��ֱ� = (60000 * 60 * 3) + (60000 * 40);
	private int _2�ð��ֱ� = (60000 * 60) + (60000 * 40);

	Random _random = new Random(System.nanoTime());

	public void boss() {
		try {
			if (����� == true) {
				return;
			}

			/*
			 * if(day.getSeconds()==0 && day.getMinutes() == 30 &&
			 * day.getHours() == 20){ if(�⸣ == null){ Random _rnd = new
			 * Random(System.nanoTime()); �⸣ = L1SpawnUtil.spawn2(32868, 32862,
			 * (short) 537, �⸣npc[_rnd.nextInt(3)], 0, 0, 0); } }
			 */

			// Date day = new Date(System.currentTimeMillis());

			if ((day.getDay() == 5 || day.getDay() == 6) && day.getHours() == 21 && day.getSeconds() == 0
					&& day.getMinutes() == 0) {
				����� = true;
				BossTimerCheck check = new BossTimerCheck(this);
				check.begin();

				L1SpawnUtil.bossspawn(100338, 32908, 33222, (short) 4, 1000 * 60 * 60 * 5, 99);
				L1SpawnUtil.bossspawn(100420, 32784, 33157, (short) 4, 1000 * 60 * 60 * 5, 99);
				L1World.getInstance().broadcastPacketToAll(
						new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "���ٿ�� �縷�� �����, �����ں� �� �����Ͽ����ϴ�."), true);
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (pc.getMapId() == 4) {
						if (pc.getX() >= 32512 && pc.getX() <= 32960 && pc.getY() >= 33023 && pc.getY() <= 33535) {
							pc.sendPackets(new S_PacketBox(83, 2), true);
						}
					}
				}
				GeneralThreadPool.getInstance().schedule(new Runnable() {
					@Override
					public void run() {
						// TODO �ڵ� ������ �޼ҵ� ����
						try {
							for (L1TeleporterInstance tel_npc : L1World.getInstance().getAllTeleporter()) {
								if (tel_npc.getNpcId() == 50036 || tel_npc.getNpcId() == 50020
										|| tel_npc.getNpcId() == 50056 || tel_npc.getNpcId() == 50054
										|| tel_npc.getNpcId() == 50024) {
									Broadcaster.broadcastPacket(tel_npc,
											new S_NpcChatPacket(tel_npc, "�� ������..!! �縷�� ���� ���� ��Ÿ��������!", 0), true);
								}
							}
							Thread.sleep(2000);
							for (L1TeleporterInstance tel_npc : L1World.getInstance().getAllTeleporter()) {
								if (tel_npc.getNpcId() == 50036 || tel_npc.getNpcId() == 50020
										|| tel_npc.getNpcId() == 50056 || tel_npc.getNpcId() == 50054
										|| tel_npc.getNpcId() == 50024) {
									Broadcaster.broadcastPacket(tel_npc,
											new S_NpcChatPacket(tel_npc, "������! � ���ٿ�� �縷�������� ���� ���� ���� �����ּ���!", 0),
											true);
								}
							}
						} catch (Exception e) {
						}
					}
				}, 1);
			}

			/*
			 * if(NewTimeController.get().SandWorm_On){
			 * NewTimeController.get().SandWorm_On = false;
			 * L1SpawnUtil.bossspawn(100338, 32908, 33222, (short)4,
			 * 1000*60*60*5, 99); L1SpawnUtil.bossspawn(100420, 32784, 33157,
			 * (short)4, 1000*60*60*5, 99);
			 * L1World.getInstance().broadcastPacketToAll(new
			 * S_PacketBox(S_PacketBox.GREEN_MESSAGE,
			 * "���ٿ�� �縷�� �����, �����ں� �� �����Ͽ����ϴ�."), true); for(L1PcInstance pc :
			 * L1World.getInstance().getAllPlayers()){ if(pc.getMapId() == 4){
			 * if(pc.getX() >= 32512 && pc.getX() <= 32960 && pc.getY() >= 33023
			 * && pc.getY() <= 33535){ pc.sendPackets(new S_PacketBox(83, 2),
			 * true); } } } GeneralThreadPool.getInstance().schedule(new
			 * Runnable(){
			 * 
			 * @Override public void run() { // TODO �ڵ� ������ �޼ҵ� ���� try{
			 * for(L1TeleporterInstance tel_npc :
			 * L1World.getInstance().getAllTeleporter()){ if(tel_npc.getNpcId()
			 * == 50036 || tel_npc.getNpcId() == 50020 || tel_npc.getNpcId() ==
			 * 50056 || tel_npc.getNpcId() == 50054 || tel_npc.getNpcId() ==
			 * 50024){ Broadcaster.broadcastPacket(tel_npc, new
			 * S_NpcChatPacket(tel_npc, "�� ������..!! �縷�� ���� ���� ��Ÿ��������!", 0),
			 * true); } } Thread.sleep(2000); for(L1TeleporterInstance tel_npc :
			 * L1World.getInstance().getAllTeleporter()){ if(tel_npc.getNpcId()
			 * == 50036 || tel_npc.getNpcId() == 50020 || tel_npc.getNpcId() ==
			 * 50056 || tel_npc.getNpcId() == 50054 || tel_npc.getNpcId() ==
			 * 50024){ Broadcaster.broadcastPacket(tel_npc, new
			 * S_NpcChatPacket(tel_npc, "������! � ���ٿ�� �縷�������� ���� ���� ���� �����ּ���!",
			 * 0), true); } } }catch(Exception e){} } }, 1); //L1Teleporter
			 * 50036 50020 50056 50054 50024 }
			 */

			/*
			 * if(day.getSeconds()==0 && (day.getMinutes() ==
			 * 0||day.getMinutes() == 30)){//�Žð� ���� ����� =true; BossTimerCheck
			 * check = new BossTimerCheck(this); check.begin(); int �̺�Ʈ�߶� =
			 * _random.nextInt(�̺�Ʈ�߶�loc.length); L1SpawnUtil.bossspawn(45684,
			 * �̺�Ʈ�߶�loc[�̺�Ʈ�߶�][0] , �̺�Ʈ�߶�loc[�̺�Ʈ�߶�][1] ,
			 * (short)�̺�Ʈ�߶�loc[�̺�Ʈ�߶�][2], _30���ֱ�, 7626);//�ɿ��� ���� }
			 */

			if (day.getSeconds() == 0 && day.getMinutes() == 0) {
				int rh = day.getHours();
				����� = true;
				BossTimerCheck check = new BossTimerCheck(this);
				check.begin();

				// System.out.println("���� Ÿ���� �˸� ���� �ð� : " + rh + "��");

				if (rh == 6 || rh == 18) {// 6~12���� 18~24 ����
					int �����ð� = _6�ð��ֱ�;
					int type = 6;

					int �������� = _random.nextInt(��������loc.length);
					int �����ɿ� = _random.nextInt(��������loc.length);
					int ��� = _random.nextInt(���loc.length);
					int �̹� = _random.nextInt(���ž�̹�loc.length);
					int �ϵ�н� = _random.nextInt(�ϵ�н�loc.length);
					int �渶���� = _random.nextInt(�渶����loc.length);
					int �巹��ũ = _random.nextInt(�巹��ũloc.length);

					L1SpawnUtil.bossspawn(45646, ��������loc[�����ɿ�][0], ��������loc[�����ɿ�][1], (short) ��������loc[�����ɿ�][2], �����ð�,
							type);// �ɿ��� ����
					L1SpawnUtil.bossspawn(45534, ��������loc[��������][0], ��������loc[��������][1], (short) ��������loc[��������][2], �����ð�,
							type);// ����
					L1SpawnUtil.bossspawn(100339, 34240, 32216, (short) 4, �����ð�, type);// ū����
																						// ����
					L1SpawnUtil.bossspawn(7300168, ���ž�̹�loc[�̹�][0], ���ž�̹�loc[�̹�][1], (short) ���ž�̹�loc[�̹�][2], �����ð�,
							type); // ���ž �̹�
					L1SpawnUtil.bossspawn(7200185, �ϵ�н�loc[�ϵ�н�][0], �ϵ�н�loc[�ϵ�н�][1], (short) �ϵ�н�loc[�ϵ�н�][2], �����ð�,
							type); // �ϵ��� �н�
					L1SpawnUtil.bossspawn(45610, ���loc[���][0], ���loc[���][1], (short) ���loc[���][2], �����ð�, type);// ��׽�Ÿ
					L1SpawnUtil.bossspawn(45492, 32767, 32899, (short) 480, �����ð�, type);// ��
					L1SpawnUtil.bossspawn(100003, 32786, 32985, (short) 420, �����ð�, type);// ����
					L1SpawnUtil.bossspawn(100814, 0, 0, (short) 814, �����ð�, type);// ����
					L1SpawnUtil.bossspawn(45734, 32899, 33116, (short) 558, �����ð�, type);// ��տ�¡��
					L1SpawnUtil.bossspawn(7280193, 32683, 32862, (short) 284, �����ð�, type);// ����
					L1SpawnUtil.bossspawn(4037000, 32765, 32769, (short) 56, �����ð�, type);// �����θ�
					L1SpawnUtil.bossspawn(100091, 32776, 32846, (short) 2, �����ð�, type);// �νĵ�
																						// �ذ���
					L1SpawnUtil.bossspawn(45943, 32735, 32799, (short) 63, �����ð�, type);// īǪ
					L1SpawnUtil.bossspawn(81175, 32698, 32827, (short) 1, �����ð�, type);// ����
					L1SpawnUtil.bossspawn(45548, 32742, 32861, (short) 484, �����ð�, type);// ȣ��
					L1SpawnUtil.bossspawn(45735, 32899, 33116, (short) 558, �����ð�, type);// �ݾ���
					L1SpawnUtil.bossspawn(45802, 32804, 32870, (short) 256, �����ð�, type);// ���̳����
					L1SpawnUtil.bossspawn(45801, 32778, 32797, (short) 255, �����ð�, type);// ���̳���հ�

					L1SpawnUtil.bossspawn(450803, 34265, 32794, (short) 4, �����ð�, type);// ������
																						// ��������

					L1SpawnUtil.bossspawn(45944, 32735, 32799, (short) 63, �����ð�, type);// ���̾�Ʈ��
					L1SpawnUtil.bossspawn(45683, 0, 0, (short) 814, �����ð�, type);// �Ŀ���
					L1SpawnUtil.bossspawn(46025, 32809, 32796, (short) 251, �����ð�, type);// Ÿ�ν�����
					L1SpawnUtil.bossspawn(45772, 32752, 32809, (short) 244, �����ð�, type);// ������ũ����
					L1SpawnUtil.bossspawn(45671, 32681, 32947, (short) 243, �����ð�, type);// �Ƹ���ũ
					L1SpawnUtil.bossspawn(45795, 32859, 32933, (short) 244, �����ð�, type);// ���Ǹ���
					L1SpawnUtil.bossspawn(100281, 32697, 32823, (short) 37, �����ð�, type);// ����
					L1SpawnUtil.bossspawn(100090, 32396, 32905, (short) 0, �����ð�, type);// ��ũ�ν�
					L1SpawnUtil.bossspawn(46026, 32797, 32790, (short) 251, �����ð�, type);// ����
					L1SpawnUtil.bossspawn(46037, 32796, 33072, (short) 258, �����ð�, type);// ����

					L1SpawnUtil.bossspawn(45546, 33709, 33307, (short) 4, �����ð�, type);// ����
					L1SpawnUtil.bossspawn(45583, 32768, 32768, (short) 24, �����ð�, type);// ������
					L1SpawnUtil.bossspawn(45829, 32675, 32860, (short) 254, �����ð�, type);// �߹ٵ���
					L1SpawnUtil.bossspawn(100089, 32611, 33029, (short) 0, �����ð�, type);// ���ȷ�
					L1SpawnUtil.bossspawn(100066, 32518, 33017, (short) 0, �����ð�, type);// �Ƹ��Ǿ�
					L1SpawnUtil.bossspawn(100088, 32530, 32820, (short) 0, �����ð�, type);// ������
					L1SpawnUtil.bossspawn(100065, 32668, 32853, (short) 0, �����ð�, type);// ��ĭƮ
					L1SpawnUtil.bossspawn(45625, 32734, 32895, (short) 522, �����ð�, type);// ȥ��
					L1SpawnUtil.bossspawn(45844, 32833, 32758, (short) 453, �����ð�, type);// �ٶ�ī
					L1SpawnUtil.bossspawn(45863, 32802, 32839, (short) 462, �����ð�, type);// ���̾�

					L1SpawnUtil.bossspawn(45676, 32762, 32840, (short) 475, �����ð�, type);// �����
					L1SpawnUtil.bossspawn(45615, 32768, 32827, (short) 475, �����ð�, type);// ũ���۽�
					L1SpawnUtil.bossspawn(45602, 32765, 32838, (short) 462, �����ð�, type);// ī���̿�
					L1SpawnUtil.bossspawn(45607, 32784, 32754, (short) 453, �����ð�, type);// ī�̹ٸ�
					L1SpawnUtil.bossspawn(45585, 32825, 32837, (short) 492, �����ð�, type);// ������
					L1SpawnUtil.bossspawn(45574, 32822, 32813, (short) 492, �����ð�, type);// ī��Ʈ
					L1SpawnUtil.bossspawn(45608, 32730, 32845, (short) 475, �����ð�, type);// �����̽���
					L1SpawnUtil.bossspawn(45577, 32770, 32855, (short) 453, �����ð�, type);// ��ũ�漭
					L1SpawnUtil.bossspawn(45648, 32833, 32827, (short) 492, �����ð�, type);// �����̺�
					L1SpawnUtil.bossspawn(45612, 32744, 32838, (short) 462, �����ð�, type);// �ٿ�Ƽ

					L1SpawnUtil.bossspawn(7140179, �渶����loc[�渶����][0], �渶����loc[�渶����][1], (short) �渶����loc[�渶����][2], �����ð�,
							type);// �渶����

					L1SpawnUtil.bossspawn(100717, �巹��ũloc[�巹��ũ][0], �巹��ũloc[�巹��ũ][1], (short) �巹��ũloc[�巹��ũ][2], �����ð�,
							type);// �渶����

				}

				if (rh == 9 || rh == 21) {
					/*
					 * L1SpawnUtil.bossspawn(45601, 32799, 32804, (short) 811,
					 * _12�ð��ֱ�, 0);// ��������Ʈ
					 */
					L1SpawnUtil.bossspawn(45752, 32726, 32832, (short) 603, _12�ð��ֱ�, 0);// �߷�
					L1SpawnUtil.bossspawn(45675, 32733, 32894, (short) 524, _12�ð��ֱ�, 0);// ����
					L1SpawnUtil.bossspawn(45674, 32746, 32897, (short) 523, _12�ð��ֱ�, 0);// ����
					L1SpawnUtil.bossspawn(100570, 32904, 32801, (short) 410, _12�ð��ֱ�, 0);// Ÿ��
				}

				/*
				 * if (rh == 22) {// 10�� �׸����� if (day.getDay() == 2 ||
				 * day.getDay() == 4 || day.getDay() == 6) {// ȭ������� //
				 * L1Location loc = mapRandomXY(111);
				 * L1SpawnUtil.bossspawn(81047, 32693, 32902, (short) 111,
				 * _5�ð��ֱ�, 99); }// 32693 32902 111 }
				 */

				// 0
				// 1
				// 2 ȭ
				// 3
				// 4 ��
				// 5
				// 6 ��

			} else if (day.getSeconds() == 0 && day.getMinutes() == 40) {
				int rh = day.getHours();
				����� = true;
				BossTimerCheck check = new BossTimerCheck(this);
				check.begin();
				if (day.getDay() == 6) {// �����
					if (rh == 18) {// ����6��
						L1Location loc = new L1Location();
						loc.set(32816, 32763, 479);
						loc = L1Location.randomRangeLocation(loc, 39, 19, false);
						L1SpawnUtil.bossspawn(45956, loc.getX(), loc.getY(), (short) 479, 60000 * 60 * 5, 7);
						loc.set(32816, 32763, 479);
						loc = L1Location.randomRangeLocation(loc, 39, 19, false);
						L1SpawnUtil.bossspawn(45959, loc.getX(), loc.getY(), (short) 479, 60000 * 60 * 5, 7);
						loc.set(32816, 32763, 479);
						loc = L1Location.randomRangeLocation(loc, 39, 19, false);
						L1SpawnUtil.bossspawn(45960, loc.getX(), loc.getY(), (short) 479, 60000 * 60 * 5, 7);
						loc.set(32816, 32763, 479);
						loc = L1Location.randomRangeLocation(loc, 39, 19, false);
						L1SpawnUtil.bossspawn(45961, loc.getX(), loc.getY(), (short) 479, 60000 * 60 * 5, 7);
						loc.set(32816, 32763, 479);
						loc = L1Location.randomRangeLocation(loc, 39, 19, false);
						L1SpawnUtil.bossspawn(45962, loc.getX(), loc.getY(), (short) 479, 60000 * 60 * 5, 7);
						loc.set(32816, 32763, 479);
						loc = L1Location.randomRangeLocation(loc, 39, 19, false);
						L1SpawnUtil.bossspawn(45955, loc.getX(), loc.getY(), (short) 479, 60000 * 60 * 5, 7);
						loc.set(32816, 32763, 479);
						loc = L1Location.randomRangeLocation(loc, 39, 19, false);
						L1SpawnUtil.bossspawn(45958, loc.getX(), loc.getY(), (short) 479, 60000 * 60 * 5, 7);
						loc.set(32816, 32763, 479);
						loc = L1Location.randomRangeLocation(loc, 39, 19, false);
						L1SpawnUtil.bossspawn(45957, loc.getX(), loc.getY(), (short) 479, 60000 * 60 * 5, 7);
					}
				}

				/*
				 * rh = day.getHours() + 1; if (rh >= 24) rh = 0;
				 * 
				 * if (rh == 6 || rh == 12 || rh == 18 || rh == 0) { L1Location
				 * loc = mapRandomXY(101); L1SpawnUtil.bossspawn(45513,
				 * loc.getX(), loc.getY(), (short) 101, _5�ð��ֱ�, 10);// 10 loc =
				 * mapRandomXY(102); L1SpawnUtil.bossspawn(45547, loc.getX(),
				 * loc.getY(), (short) 102, _5�ð��ֱ�, 10);// 20 loc =
				 * mapRandomXY(103); L1SpawnUtil.bossspawn(45606, loc.getX(),
				 * loc.getY(), (short) 103, _5�ð��ֱ�, 10);// 30 loc =
				 * mapRandomXY(104); L1SpawnUtil.bossspawn(45650, loc.getX(),
				 * loc.getY(), (short) 104, _5�ð��ֱ�, 10);// 40 loc =
				 * mapRandomXY(105); L1SpawnUtil.bossspawn(45652, loc.getX(),
				 * loc.getY(), (short) 105, _5�ð��ֱ�, 10);// 50 loc =
				 * mapRandomXY(106); L1SpawnUtil.bossspawn(45653, loc.getX(),
				 * loc.getY(), (short) 106, _5�ð��ֱ�, 10);// 60 loc =
				 * mapRandomXY(107); L1SpawnUtil.bossspawn(45654, loc.getX(),
				 * loc.getY(), (short) 107, _5�ð��ֱ�, 10);// 70 loc =
				 * mapRandomXY(108); L1SpawnUtil.bossspawn(45618, loc.getX(),
				 * loc.getY(), (short) 108, _5�ð��ֱ�, 10);// 80 loc =
				 * mapRandomXY(109); L1SpawnUtil.bossspawn(45672, loc.getX(),
				 * loc.getY(), (short) 109, _5�ð��ֱ�, 10);// 90 loc =
				 * mapRandomXY(110); L1SpawnUtil.bossspawn(100002, loc.getX(),
				 * loc.getY(), (short) 110, _5�ð��ֱ�, 10);// 90 }
				 */
				/*
				 * }else if(day.getSeconds() == 0 && day.getMinutes() == 30){
				 * int rh = day.getHours(); ����� =true; BossTimerCheck check =
				 * new BossTimerCheck(this); check.begin();
				 * 
				 * 
				 * 
				 * /////////////////////////////////////////////////�������� �����ؾ���
				 * ��ġ�� �̻�;;;/////////////////////////////////
				 */
				/*
				 * }else if(day.getSeconds()==0 && day.getMinutes()==40){ int rh
				 * = day.getHours() + 1; if(rh >= 24) rh = 0; ����� =true;
				 * BossTimerCheck check = new BossTimerCheck(this);
				 * check.begin();
				 *//** ���� 1��~100������ ������ **/
				/*
				 * // if(rh==1||rh==7||rh==13||rh==19){
				 */
			} else if (day.getSeconds() == 0 && day.getMinutes() == 50) {
				int rh = day.getHours() + 1;
				if (rh >= 24)
					rh = 0;
				����� = true;
				BossTimerCheck check = new BossTimerCheck(this);
				check.begin();
				if (rh == 0 || rh == 4 || rh == 8 || rh == 12 || rh == 16 || rh == 20) {
					L1SpawnUtil.bossspawn(45600, 32632, 32964, (short) 111, _2�ð��ֱ�, 0);// Ŀ��
					L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
							"5~10�� �� ������ ž ���� ���� ���� Ŀ���� �����մϴ�. Spawn Time : 4"), true);

				}
				if (rh == 1 || rh == 5 || rh == 9 || rh == 13 || rh == 17 || rh == 21) {
					L1SpawnUtil.bossspawn(45601, 32767, 32961, (short) 111, _2�ð��ֱ�, 0);// ��������Ʈ
					L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
							"5~10�� �� ������ ž ���� ��������Ʈ�� �����մϴ�. Spawn Time : 4"), true);

				}
				if (rh == 2 || rh == 6 || rh == 10 || rh == 14 || rh == 18 || rh == 22) {
					L1SpawnUtil.bossspawn(55601, 32774, 32833, (short) 111, _2�ð��ֱ�, 0);// ��
																						// ��������Ʈ
					L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
							"5~10�� �� ������ ž ���� �� ��������Ʈ�� �����մϴ�. Spawn Time : 4"), true);

				}
				if (rh == 3 || rh == 9 || rh == 15 || rh == 21) {
					L1SpawnUtil.bossspawn(81047, 32692, 32903, (short) 111, _4�ð��ֱ�, 0);// �׸�
																						// ����
					L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
							"5~10�� �� ������ ž ���� �׸� ���۰� �����մϴ�. Spawn Time : 6"), true);

				}
				if (rh == 4 || rh == 10 || rh == 16 || rh == 22) {
					L1SpawnUtil.bossspawn(707026, 32909, 32783, (short) 1700, _4�ð��ֱ�, 0);// ���̼�Ʈ
																							// �����
					L1World.getInstance().broadcastPacketToAll(
							new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "������ ���� ���� ����� ���� �����Ǿ����ϴ�. Spawn Time : 6"),
							true);
				}
				if (rh == 5 || rh == 17) {
					L1SpawnUtil.bossspawn(100589, 32867, 32861, (short) 537, _6�ð��ֱ�, 0);// �⸣Ÿ��
					L1World.getInstance().broadcastPacketToAll(
							new S_PacketBox(S_PacketBox.GREEN_MESSAGE, "ī�ʽ� : ���ʱ������� �⸣Ÿ���� ������ ����� ��������! �� �װ� ��Ȱ�Ұɼ�!"),
							true);
				}
				/*
				 * L1SpawnUtil.bossspawn(100824, ��Ű����loc[��Ű][0], ��Ű����loc[��Ű][1],
				 * (short) ��Ű����loc[��Ű][2], �����ð�, type);
				 */
				// ////////////////////////////////////1�ð��ֱ��/////////////////////////////////
				/*
				 * L1SpawnUtil.bossspawn(1000651, 32801, 32767, (short) 53,
				 * _1�ð��ֱ�, 4);// �Ⱘ ȣ������
				 * L1World.getInstance().broadcastPacketToAll( new
				 * S_PacketBox(S_PacketBox.GREEN_MESSAGE,
				 * "��� �ı������ 1���� ȣ�����簡 �����մϴ�."), true);
				 * L1SpawnUtil.bossspawn(707026, 32909, 32783, (short) 1700,
				 * _1�ð��ֱ�, 4);// ���̼�Ʈ ����� L1SpawnUtil.bossspawn(707024, 32794,
				 * 32948, (short) 1700, _1�ð��ֱ�, 4);// ��ī ŷ
				 * L1SpawnUtil.bossspawn(707023, 32838, 32718, (short) 1700,
				 * _1�ð��ֱ�, 4);// ���� ŷ L1SpawnUtil.bossspawn(707022, 32673,
				 * 32736, (short) 1700, _1�ð��ֱ�, 4);// �׷���Ʈ �̳��츣��
				 * L1SpawnUtil.bossspawn(707013, 32817, 33026, (short) 1700,
				 * _1�ð��ֱ�, 4);// ���̾�Ʈũ��ũ���� L1SpawnUtil.bossspawn(707020, 32694,
				 * 32955, (short) 1700, _1�ð��ֱ�, 4);// ����� Ŭ�η���
				 * L1SpawnUtil.bossspawn(707025, 32752, 32973, (short) 1700,
				 * _1�ð��ֱ�, 4);// ����� ŷ L1SpawnUtil.bossspawn(707017, 32928,
				 * 32803, (short) 1700, _1�ð��ֱ�, 4);// �巹��ũ ŷ
				 * L1SpawnUtil.bossspawn(45942, 32700, 32830, (short) 61,
				 * _1�ð��ֱ�, 4);// ���ֹ������� L1SpawnUtil.bossspawn(45941, 32735,
				 * 32799, (short) 63, _1�ð��ֱ�, 4);// ���ֹ�������翤
				 * L1SpawnUtil.bossspawn(45931, 32735, 32799, (short) 63,
				 * _1�ð��ֱ�, 4);// ���� ���� L1SpawnUtil.bossspawn(46024, 32737,
				 * 32827, (short) 250, _1�ð��ֱ�, 4);// ����ģ������
				 */

				// L1SpawnUtil.bossspawn(200281, 33332, 32451, (short)4, _1�ð��ֱ�,
				// 4);//��� ���� �̺�Ʈ��
				//

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * ==================== 1 �� �� �� �� �� ��
		 * =================================== ũ��Ŀ����.�巹��ũ����.�����䳢. ������Ʈ �巹��ũ �����䳢
		 * ����� ���簻�� �׷���Ʈ �̳�Ÿ�츣��,Ŀ��,���̼�Ʈ ���̾�Ʈ,�Ǵн�,��տ�¡��,��θӸ� �ݾ���,
		 * 
		 * ������ ��ũ����,���Ǹ���,��.ī���� �йи�.������,����������,���ֹ��� ���� ������ ,���ֹ��� ���� �翤,���� ����,�ɿ���
		 * ����,���� ���� �ٶ�ī,īǪ,���̾�Ʈ ��,�� ��ũ�θǼ�
		 * ======================================
		 * =================================
		 **/
	}

	public class BossTimerCheck implements Runnable {
		private BossTimer ��üũ = null;

		public BossTimerCheck(BossTimer bt) {
			��üũ = bt;
		}

		@Override
		public void run() {
			try {
				��üũ.����� = false;
				��üũ = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void begin() {
			GeneralThreadPool.getInstance().schedule(this, 3000);
		}
	}
}