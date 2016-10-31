package l1j.server.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1GroundInventory;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Quest;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
//Referenced classes of package l1j.server.server.templates:
//L1Npc, L1Item, ItemTable
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Drop;
import l1j.server.server.templates.L1Item;
import l1j.server.server.utils.SQLUtil;

public class DropTable {

	private static Logger _log = Logger.getLogger(DropTable.class.getName());

	private static final Random _random = new Random(); // �̰͵� �ִٸ� ������������

	private static DropTable _instance;

	private final HashMap<Integer, ArrayList<L1Drop>> _droplists;

	public static DropTable getInstance() {
		if (_instance == null) {
			_instance = new DropTable();
		}
		return _instance;
	}

	private DropTable() {
		_droplists = allDropList();
	}

	public static void reload() {
		synchronized (_instance) {
			DropTable oldInstance = _instance;
			_instance = new DropTable();
			oldInstance._droplists.clear();
		}
	}

	public static final int CLASSID_PRINCE = 0;
	public static final int CLASSID_PRINCESS = 1;
	public static final int CLASSID_KNIGHT_MALE = 61;
	public static final int CLASSID_KNIGHT_FEMALE = 48;
	public static final int CLASSID_ELF_MALE = 138;
	public static final int CLASSID_ELF_FEMALE = 37;
	public static final int CLASSID_WIZARD_MALE = 734;
	public static final int CLASSID_WIZARD_FEMALE = 1186;
	public static final int CLASSID_DARKELF_MALE = 2786;
	public static final int CLASSID_DARKELF_FEMALE = 2796;
	public static final int CLASSID_DRAGONKNIGHT_MALE = 6658;
	public static final int CLASSID_DRAGONKNIGHT_FEMALE = 6661;
	public static final int CLASSID_ILLUSIONIST_MALE = 6671;
	public static final int CLASSID_ILLUSIONIST_FEMALE = 6650;
	public static final int CLASSID_WARRIOR_MALE = 12490;
	public static final int CLASSID_WARRIOR_FEMALE = 12494;

	private HashMap<Integer, ArrayList<L1Drop>> allDropList() {
		HashMap<Integer, ArrayList<L1Drop>> droplistMap = new HashMap<Integer, ArrayList<L1Drop>>();

		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("select * from droplist");
			rs = pstm.executeQuery();
			L1Drop drop = null;
			while (rs.next()) {
				int mobId = rs.getInt("mobId");
				int itemId = rs.getInt("itemId");
				int min = rs.getInt("min");
				int max = rs.getInt("max");
				int chance = rs.getInt("chance");
				int enchant = rs.getInt("Enchant");
				int Rate = rs.getInt("Rate");

				drop = new L1Drop(mobId, itemId, min, max, chance, enchant, Rate);

				ArrayList<L1Drop> dropList = droplistMap.get(drop.getMobid());
				if (dropList == null) {
					dropList = new ArrayList<L1Drop>();
					droplistMap.put(new Integer(drop.getMobid()), dropList);
				}
				dropList.add(drop);
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return droplistMap;
	}

	@SuppressWarnings("resource")
	public static boolean SabuDrop(int npcid, int itemid, int max, int rate, String mobname, String itemname,
			String note) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("select * from droplist where mobId = ? and itemId = ?");
			pstm.setInt(1, npcid);
			pstm.setInt(2, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				SQLUtil.close(rs);
				SQLUtil.close(pstm);
				SQLUtil.close(con);
				return false;
			}
			pstm = con.prepareStatement(
					"INSERT INTO droplist SET mobId=?,itemId=?,min=?,max=?,chance=?,mobname=?,itemname=?,mobnote=?");
			pstm.setInt(1, npcid);
			pstm.setInt(2, itemid);
			pstm.setInt(3, 1);
			pstm.setInt(4, max);
			pstm.setInt(5, rate);
			pstm.setString(6, mobname);
			pstm.setString(7, itemname);
			pstm.setString(8, note);
			pstm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);

		}
		return true;
	}

	// �κ�Ʈ���� ����� ����
	public void setDrop(L1NpcInstance npc, L1Inventory inventory) {
		/** ���� ���� ��� */
		if (Config.STANDBY_SERVER) {
			return;
		}
		// ��� ����Ʈ�� ���
		int mobId = npc.getNpcTemplate().get_npcId();
		ArrayList<L1Drop> dropList = _droplists.get(mobId);
		if (dropList == null) {
			return;
		}

		// ����Ʈ ���
		double droprate = Config.RATE_DROP_ITEMS;
		if (droprate <= 0) {
			droprate = 0;
		}
		double adenarate = Config.RATE_DROP_ADENA;
		if (adenarate <= 0) {
			adenarate = 0;
		}
		if (droprate <= 0 && adenarate <= 0) {
			return;
		}
		if (inventory.getSize() >= 1) {
			System.out.println("[�������2] npcid : " + npc.getNpcId() + " / x : " + npc.getX() + " y : " + npc.getY()
					+ " m : " + npc.getMapId());
		}
		int itemId;
		int itemCount;
		int addCount;
		int randomChance;
		L1ItemInstance item;

		/** ȯ�� �̺�Ʈ **/
		L1ItemInstance Fitem;
		L1ItemInstance Citem;

		Random random = new Random(System.nanoTime());

		for (L1Drop drop : dropList) {
			// ��� �������� ���
			itemId = drop.getItemid();
			if (adenarate == 0 && itemId == L1ItemId.ADENA) {
				continue; // �Ƶ�������Ʈ 0���� ����� �Ƶ����� ���� ����
			}

			// ��� ���� ����
			randomChance = random.nextInt(0xf4240) + 1;
			double rateOfMapId = MapsTable.getInstance().getDropRate(npc.getMapId());
			double rateOfItem = DropItemTable.getInstance().getDropRate(itemId);
			double resultDroprate = drop.getChance() * droprate * rateOfMapId;

			resultDroprate = (int) (resultDroprate * rateOfItem);

			if (droprate == 0 || resultDroprate < randomChance) {
				continue;
			}
			// ��� ������ ����
			double amount = DropItemTable.getInstance().getDropAmount(itemId);
			int min = drop.getMin();
			int max = drop.getMax();
			int rate = drop.getrate();
			min = (int) (min * amount);
			max = (int) (max * amount);

			// System.out.println("�ּ� : "+min);
			// System.out.println("�ִ� : "+max);
			itemCount = min;
			addCount = max - min + 1;
			if (addCount > 1) {
				itemCount += random.nextInt(addCount);
			}
			// /System.out.println("���� : "+addCount);
			// System.out.println("�ּ�+���� : "+itemCount);
			try {
				if (rate != 0) {
					int average = (min + max) / 2;
					int rrr = random.nextInt(100) + 1;
					if (rrr < rate) {
						itemCount = random.nextInt(max - average) + average;
					} else {
						itemCount = random.nextInt(average - min) + min;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (itemId == L1ItemId.ADENA) { // ����� �Ƶ����� ���� �Ƶ�������Ʈ�� �Ǵ�
				// if(npc.getMapId() == 410){
				// itemCount = 0;
				// }else{
				itemCount *= adenarate;
				// System.out.println("���� : "+itemCount);
				// }
			} else {
				if (itemCount > max) {
					itemCount = max;
					// System.out.println("[�������3] npcid : "+npc.getNpcId()+" /
					// x : "+npc.getX()+" y : "+npc.getY()+" m :
					// "+npc.getMapId());
				}
			}

			// ȭ�� ���� �Ƶ��� �ȳ�����
			/*
			 * if(itemId == L1ItemId.ADENA && npc.getMapId() == 4 && npc.getX()
			 * >= 33472 && npc.getX() <= 33856 && npc.getY() >= 32191 &&
			 * npc.getY() <= 32511 && npc.getMaxHp() < 10000){ continue; }
			 */

			if (itemCount < 0) {
				itemCount = 0;
			}
			if (itemCount > 2000000000) {
				itemCount = 2000000000;
			}

			L1Item Litem = ItemTable.getInstance().getTemplate(itemId);
			// �������� ����
			if (Litem != null) {

				if (Litem.isStackable()) {
					// �������� ����
					item = ItemTable.getInstance().createItem(itemId);
					if (item == null)
						continue;
					item.setCount(itemCount);
					if (drop.getEnchant() != 0) {
						System.out.println("[����] droplist : �������� �����ۿ� ��þ��.(" + item.getItemId() + ")");
						item.setEnchantLevel(drop.getEnchant());
					}
					// ������ �ݳ�
					inventory.storeItem(item);
				} else {
					for (int i = 0; i < itemCount; ++i) {
						// �������� ����
						item = ItemTable.getInstance().createItem(itemId);

						/*
						 * Timestamp deleteTime = null; if((itemId >= 21099 &&
						 * itemId <= 21112) || itemId == 21254 || itemId ==
						 * 20082
						 * 
						 * ||itemId == 7 || itemId == 35 || itemId == 48 ||
						 * itemId == 73 || itemId == 105 || itemId == 120 ||
						 * itemId == 147 || itemId == 7232 || itemId == 156 ||
						 * itemId == 174 || itemId == 175 || itemId == 224){
						 * deleteTime = new Timestamp(System.currentTimeMillis()
						 * + 3600000*24*7);// 3�� item.setEndTime(deleteTime); }
						 */

						item.setCount(1);
						if (drop.getEnchant() != 0) {
							item.setEnchantLevel(drop.getEnchant());
						}
						// ������ �ݳ�
						inventory.storeItem(item);
					}
				}
			}
		}
		/** ȯ�� �̺�Ʈ **/
		if (Config.ALT_FANTASYEVENT == true) {
			int itemRandom = random.nextInt(100) + 1;
			int countRandom = random.nextInt(100) + 1;
			int item1Random = random.nextInt(100 + 1);
			int Fcount = 0;
			int Itemnum = 0;
			if (item1Random <= 50) {
				Itemnum = 40127;
			} else {
				Itemnum = 40128;
			}
			if (countRandom <= 90) {
				Fcount = 1;
			} else if (countRandom >= 91) {
				Fcount = 2;
			}
			if (itemRandom <= 40) {
			} else if (itemRandom >= 46 || itemRandom <= 70) {
				Fitem = ItemTable.getInstance().createItem(Itemnum);
				Fitem.setCount(Fcount);
				inventory.storeItem(Fitem);
			} else if (itemRandom >= 96) {
				Fitem = ItemTable.getInstance().createItem(Itemnum);
				Fitem.setCount(Fcount);
				inventory.storeItem(Fitem);
			}
		}
		/** �߼� �̺�Ʈ **/
		if (Config.ALT_CHUSEOKEVENT == true) {
			int itemRandom = random.nextInt(100) + 1;
			if (itemRandom <= 3) {
				Citem = ItemTable.getInstance().createItem(435014);
				inventory.storeItem(Citem);
			}
		}
		/** ���� �̺�Ʈ **/
		if (Config.ALT_FEATURE == true) {
			short mapid = npc.getMapId();
			if ((mapid >= 450 && mapid <= 478) || (mapid >= 490 && mapid <= 496) || (mapid >= 530 && mapid <= 536)) {
				return;
			}
			int itemRandom = random.nextInt(300) + 1;
			if (itemRandom <= 3) {
				Citem = ItemTable.getInstance().createItem(41159);
				Citem.setCount(random.nextInt(5) + 1);
				inventory.storeItem(Citem);
			}
		}

		/** �׽�Ʈ ���� **/
		if (Config.GAME_SERVER_TYPE == 1) {
			short mapid = npc.getMapId();
			if ((mapid >= 450 && mapid <= 478) || (mapid >= 490 && mapid <= 496) || (mapid >= 530 && mapid <= 536)) {
				return;
			}
			int lvl = npc.getLevel();
			int itemRandom = 0;
			if (lvl >= 20) {
				itemRandom = random.nextInt(lvl * 5 + 1) + lvl;
				Citem = ItemTable.getInstance().createItem(L1ItemId.TEST_MARK);
				Citem.setCount(itemRandom);
				inventory.storeItem(Citem);
			}
		}

		if (npc.getMapId() >= 1 && npc.getMapId() <= 2) {
			int itemRandom = random.nextInt(10000);
			if (itemRandom <= 2) {// 0.02%
				Citem = ItemTable.getInstance().createItem(40163); // ������Ʈ ���� ����
				Citem.setCount(1);
				inventory.storeItem(Citem);
			}
		}
		/*
		 * if (npc.getNpcId() == 45720 || npc.getNpcId() == 45725) { int
		 * itemRandom = random.nextInt(1000); int count =
		 * random.nextInt(10000000)+10000000; if (itemRandom <= 777) { Citem =
		 * ItemTable.getInstance().createItem(40308); Citem.setCount(count);
		 * inventory.storeItem(Citem); }
		 * 
		 * }
		 */

		/** ���� ���� ���� ��� */
		/*
		 * if (npc.getMapId() >= 101 && npc.getMapId() <= 200) { int itemRandom
		 * = random.nextInt(100); if (itemRandom <= 70) { Citem =
		 * ItemTable.getInstance().createItem(41159);
		 * Citem.setCount(CommonUtil.random(7, 15)); inventory.storeItem(Citem);
		 * } }
		 */
		/** �Ǹ����� ���� */
		/*
		 * if (npc.getMapId() == 5167 || (npc.getMapId() >= 53 && npc.getMapId()
		 * <= 56)) { int itemRandom = random.nextInt(100); if (itemRandom <= 70)
		 * { Citem = ItemTable.getInstance().createItem(41159);
		 * Citem.setCount(CommonUtil.random(5, 10)); inventory.storeItem(Citem);
		 * } }
		 */

		/** ���� ���� */
		/*
		 * if ((npc.getMapId() >= 23 && npc.getMapId() <= 24) || // ���ٿ��
		 * (npc.getMapId() >= 240 && npc.getMapId() <= 243) || // ��Ʈ��
		 * (npc.getMapId() >= 248 && npc.getMapId() <= 251) || // �����
		 * (npc.getMapId() >= 252 && npc.getMapId() <= 253) || // ���̳״���
		 * (npc.getMapId() >= 257 && npc.getMapId() <= 259)) { // �Ƶ������� int
		 * itemRandom = random.nextInt(100); if (itemRandom <= 70) { Citem =
		 * ItemTable.getInstance().createItem(41159);
		 * Citem.setCount(CommonUtil.random(10, 30));
		 * inventory.storeItem(Citem); } }
		 */

		/**
		 * �����, ������, Ÿ��, ȥ��, �Ŵ뿩�հ���, �߷�, �Ǵн�, Ÿ�ν�, ��������Ʈ, �׸�����, 4���, �⸣Ÿ��, �Ǹ���
		 */
		/*
		 * if (npc.getNpcTemplate().get_npcId() == 45545 || //�����
		 * npc.getNpcTemplate().get_npcId() == 45583 || //������
		 * npc.getNpcTemplate().get_npcId() == 45685 || //Ÿ��
		 * npc.getNpcTemplate().get_npcId() == 45625 || //ȥ��
		 * npc.getNpcTemplate().get_npcId() == 45614 || //�Ŵ뿩�հ���
		 * npc.getNpcTemplate().get_npcId() == 45753 || //�߷�
		 * npc.getNpcTemplate().get_npcId() == 45617 || //�Ǵн�
		 * npc.getNpcTemplate().get_npcId() == 46025 || //Ÿ�ν�
		 * npc.getNpcTemplate().get_npcId() == 45601 || //��������Ʈ
		 * npc.getNpcTemplate().get_npcId() == 45673 || //�׸�����
		 * npc.getNpcTemplate().get_npcId() == 45682 || //��Ÿ��
		 * npc.getNpcTemplate().get_npcId() == 45683 || //��Ǫ����
		 * npc.getNpcTemplate().get_npcId() == 45684 || //�߶�ī��
		 * npc.getNpcTemplate().get_npcId() == 45681 || //��������
		 * npc.getNpcTemplate().get_npcId() == 81163 || //�⸣Ÿ��
		 * npc.getNpcTemplate().get_npcId() == 7000062) { //�Ǹ��� int itemRandom =
		 * random.nextInt(100); if (itemRandom <= 70) { Citem =
		 * ItemTable.getInstance().createItem(41159);
		 * Citem.setCount(CommonUtil.random(500, 1500));
		 * inventory.storeItem(Citem); } }
		 */
		random = null;
	}

	private static int ladunMonList[] = { 45254, 45256, 45287, 45398, 45412, 45448, 45459, 45460, 45461, 45462, 45465,
			45466, 45467, 45474, 45475, 45483, 45484, 45486, 45489, 45495, 45512, 45517, 45518, 45521, 45523, 45526,
			45527, 45533, 45669, 45830, 45836, 45837, 45841, 45846, 45850, 45851, 45852, 45854, 45855, 45856, 45857,
			45858, 45864, 45865, 45868, 45869, 45872, 45976, 45977, 45978, 45979, 45980, 45981, 45982, 45983, 45984,
			45985, 45986, 45987, 45988, 45989, 45991, 45992, 45994, 45995, 45997, 46004, 46047 };

	public boolean autook(L1PcInstance pc) {
		L1PcInstance ckpc = L1World.getInstance().getPlayer(pc.getName());
		if (ckpc == null) {
			return false;
		}
		if (ckpc.getNetConnection() == null) {
			return false;
		}
		if (ckpc.getNetConnection().isClosed()) {
			return false;
		}
		if (ckpc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_AUTOROOT)) {
			return false;
		}
		return true;
	}

	// ����� �й�
	public void dropShare(L1NpcInstance npc, ArrayList<?> acquisitorList, ArrayList<?> hateList, L1PcInstance pc) {
		/** ���� ���� ��� */
		if (Config.STANDBY_SERVER) {
			return;
		}
		L1Inventory inventory = npc.getInventory();
		int mobId = npc.getNpcTemplate().get_npcId();
		if (inventory.getSize() == 0) {
			return;
		}
		if (acquisitorList.size() != hateList.size()) {
			return;
		}
		if (pc instanceof L1RobotInstance) {
			return;
		}

		if (pc.getLevel() >= 45) {
			if ((mobId >= 100225 && mobId <= 100231) || (mobId >= 100236 && mobId <= 100241)) {
				return;
			}
		}

		/*
		 * ArrayList<L1Character> acquisitorList = list.toTargetArrayList();
		 * ArrayList<Integer> hateList = list.toHateArrayList();
		 * 
		 * if (acquisitorList.size() != hateList.size()) { return; }
		 */

		/** ����Ʈ ���� **/
		if (!npc.isResurrect()) {
	
			if (pc.getQuest().get_step(L1Quest.QUEST_SILVER_FIRST) == 2) {
				if (npc.getNpcId() >= 100225 && npc.getNpcId() <= 100234) {
					npc.getInventory().storeItem(60143, 1);
				}
			} else if (pc.getQuest().get_step(L1Quest.QUEST_SILVER_FIRST) == 4) {
				if ((npc.getNpcId() >= 100235 && npc.getNpcId() <= 100241) || npc.getNpcId() == 100231) {
					npc.getInventory().storeItem(60144, 1);
				}
			} else if (pc.getQuest().get_step(L1Quest.QUEST_SILVER_FIRST) == 6) {
				if (npc.getMapId() == 25) {
					npc.getInventory().storeItem(60145, 1);
				}
			} else if (pc.getQuest().get_step(L1Quest.QUEST_SILVER_FIRST) == 8) {
				if (npc.getMapId() == 26) {
					npc.getInventory().storeItem(60146, 1);
				}
			} else if (pc.getQuest().get_step(L1Quest.QUEST_SILVER_FIRST) == 10) {
				if (npc.getMapId() == 27 || npc.getMapId() == 28) {
					npc.getInventory().storeItem(60147, 1);
				}
			}
			if (pc.getQuest().get_step(L1Quest.QUEST_SILVER_HUNT) != 0
					&& pc.getQuest().get_step(L1Quest.QUEST_SILVER_HUNT) % 2 != 0
					&& pc.getQuest().get_step(L1Quest.QUEST_SILVER_HUNT) != 255) {
				if ((npc.getNpcId() >= 100225 && npc.getNpcId() <= 100241)
						|| (npc.getNpcId() >= 100249 && npc.getNpcId() <= 100263))
					npc.getInventory().storeItem(60158, 1);
			}
			if (pc.getMapId() == 2010 && pc.getQuest().get_step(L1Quest.QUEST_SILVER_DRAGON_TEARS) != 0
					&& pc.getQuest().get_step(L1Quest.QUEST_SILVER_DRAGON_TEARS) != 255
					&& (pc.getQuest().get_step(L1Quest.QUEST_SILVER_DRAGON_TEARS) % 2 != 0
							|| pc.getInventory().checkItem(60160))) {
				if (npc.getNpcId() >= 100242 && npc.getNpcId() <= 100248)
					npc.getInventory().storeItem(60161, 1);
			}
		}

		if (!npc.isResurrect()) {
			if ((mobId == 100330 || mobId == 100332 || mobId == 100333 || mobId == 100334 || mobId == 100335)
					&& pc.getInventory().checkItem(60177)) {
				if (npc.getMapId() == 785 || npc.getMapId() == 788 || npc.getMapId() == 789) {
					Random rnd = new Random(System.nanoTime());
					inventory.storeItem(60179, rnd.nextInt(9) + 1);
				}
			} else if (mobId >= 100596 && mobId <= 100603) {
				Random rnd = new Random(System.nanoTime());
				if (rnd.nextInt(0xf4240) + 1 < 15000 * Config.RATE_DROP_ITEMS) {
					inventory.storeItem(60321, 1);
				}
			} else if (mobId >= 100699 && mobId <= 100706 && npc.getMapId() == 820) {
				Random rnd = new Random(System.nanoTime());
				if (rnd.nextInt(0xf4240) + 1 < 20000 * Config.RATE_DROP_ITEMS) {
					inventory.storeItem(60443, 1);
				}
			} else {
				// ��� ���� ������� 10 ���� ����ʿ��� �μ��� ���� ���� �ø��� ���
				if (pc.getMapId() >= 451 && pc.getMapId() <= 536 && pc.getMapId() != 480 && pc.getMapId() != 481
						&& pc.getMapId() != 482 && pc.getMapId() != 483 && pc.getMapId() != 484 && pc.getMapId() != 521
						&& pc.getMapId() != 522 && pc.getMapId() != 523 && pc.getMapId() != 524) {
					for (int i : ladunMonList) {
						if (mobId == i) {
							Random rnd = new Random(System.nanoTime());
							if (rnd.nextInt(0xf4240) + 1 < 10 * Config.RATE_DROP_ITEMS) {
								inventory.storeItem(60213 + rnd.nextInt(4), 1);
								break;
							}
						}
					}
				}
			}
		}

		// inventory.shuffle();

		// ����Ʈ�� �հ踦 ���
		int totalHate = 0;
		L1Character acquisitor;
		for (int i = hateList.size() - 1; i >= 0; i--) {
			acquisitor = (L1Character) acquisitorList.get(i);
			if ((Config.AUTO_LOOT == 2) // ���� ���� 2�� ���� ��� �� �ֿϵ����� �����Ѵ�
					&& (acquisitor instanceof L1SummonInstance || acquisitor instanceof L1PetInstance)) {
				acquisitorList.remove(i);
				hateList.remove(i);
			} else if (acquisitor != null && acquisitor.isDead()
					&& (npc.getNpcId() == 4200011 || npc.getNpcId() == 4039007 || npc.getNpcId() == 100014
							|| npc.getNpcId() == 400016 || npc.getNpcId() == 400017 || npc.getNpcId() == 4036016
							|| npc.getNpcId() == 4036017)) {
				acquisitorList.remove(i);
				hateList.remove(i);
			} else if (acquisitor != null && acquisitor.getMapId() == npc.getMapId()
					&& (acquisitor.getLocation().getTileLineDistance(npc.getLocation()) <= Config.LOOTING_RANGE
							|| (npc.getNpcId() == 4200011 || npc.getNpcId() == 4039007 || npc.getNpcId() == 100014
									|| npc.getNpcId() == 400016 || npc.getNpcId() == 400017 || npc.getNpcId() == 4036016
									|| npc.getNpcId() == 4036017))) {
				totalHate += (Integer) hateList.get(i);
			} else { // null���ų� �ױ⵵ �ϰ� �־����� ����
				acquisitorList.remove(i);
				hateList.remove(i);
			}
		}
		// ����� �й�
		L1ItemInstance item;
		L1Inventory targetInventory = null;
		L1PcInstance player;
		Random random = new Random();
		int randomInt;
		int chanceHate;
		int itemId;

		for (int i = inventory.getSize(); i > 0; i--) {
			item = null;
			// try {
			item = inventory.getItems().get(0);
			if (item == null) {
				continue;
			}

			itemId = item.getItem().getItemId();

			if (pc != null) {
				if (itemId == 60135) {// ����
					if (!pc.isElf()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
				if (itemId == 60137) {// ����
					if (!pc.isElf()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
				if (itemId == 60138) {// ���� ����,����
					if (!pc.isElf() && !pc.isWizard()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
				if (itemId == 240016) {// ����
					if (!pc.isWizard()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
				if (itemId == 60133) {// ����
					if (!pc.isCrown()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
				if (itemId == 60134) {// ���
					if (!pc.isKnight() && !pc.isWarrior()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
				if (itemId == 60136) {// ���׵�󿭸�
					if (!pc.isIllusionist()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
				if (itemId == 60139) {// ��伮
					if (!pc.isDarkelf()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
				if (itemId == 60140) {// ������
					if (!pc.isDragonknight()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
				if (itemId == 60157) {// �Ӽ���
					if (!pc.isIllusionist()) {
						inventory.removeItem(item, item.getCount());
						continue;
					}
				}
			}

			boolean isGround = false;
			if (item.getItem().getType2() == 0 && item.getItem().getType() == 2) { // light��
																					// ������
				item.setNowLighting(false);
			}

			if ((Config.AUTO_LOOT != 0 || AutoLoot.getInstance().isAutoLoot(itemId)) && totalHate > 0) {
				randomInt = random.nextInt(totalHate);
				chanceHate = 0;
				for (int j = hateList.size() - 1; j >= 0; j--) {
					chanceHate += (Integer) hateList.get(j);
					if (chanceHate > randomInt) {
						acquisitor = (L1Character) acquisitorList.get(j);

						if (acquisitor.getInventory().checkAddItem(item, item.getCount()) == L1Inventory.OK) {
							targetInventory = acquisitor.getInventory();
							if (acquisitor instanceof L1PcInstance) {
								player = (L1PcInstance) acquisitor;
								L1ItemInstance l1iteminstance = player.getInventory().findItemId(L1ItemId.ADENA);
								// ���� �Ƶ����� üũ
								if (l1iteminstance != null && l1iteminstance.getCount() > 2000000000) {
									targetInventory = L1World.getInstance().getInventory(acquisitor.getX(),
											acquisitor.getY(), acquisitor.getMapId());
									// ���� �� ���� ������ �߹ؿ� ����߸���
									player.sendPackets(
											new S_ServerMessage(166, "�����ϰ� �ִ� �Ƶ���", "2,000,000,000�� �ʰ��ϰ� �ֽ��ϴ�."));
								} else {
									if (npc.getNpcId() == 4200011 // ������ ����޼���..
											|| npc.getNpcId() == 4039007 || npc.getNpcId() == 100014
											|| npc.getNpcId() == 400016 || npc.getNpcId() == 400017
											|| npc.getNpcId() == 4036016 || npc.getNpcId() == 4036017
											|| npc.getNpcId() == 45601 || npc.getNpcId() == 45672
											|| npc.getNpcId() == 100002 || npc.getNpcId() == 707022
											|| npc.getNpcId() == 707025 || npc.getNpcId() == 707024
											|| npc.getNpcId() == 707023 || npc.getNpcId() == 707017
											|| npc.getNpcId() == 707026 || npc.getNpcId() == 45673
											|| npc.getNpcId() == 45600 || npc.getNpcId() == 100719
											|| npc.getNpcId() == 46025 || npc.getNpcId() == 55601
											|| npc.getNpcId() == 100589 || npc.getNpcId() == 45513
											|| npc.getNpcId() == 45547 || npc.getNpcId() == 45606
											|| npc.getNpcId() == 45650 || npc.getNpcId() == 45652
											|| npc.getNpcId() == 45653 || npc.getNpcId() == 45654
											|| npc.getNpcId() == 45583 || npc.getNpcId() == 81175
											|| npc.getNpcId() == 100063 || npc.getNpcId() == 100065
											|| npc.getNpcId() == 100088 || npc.getNpcId() == 45618) {
										Collection<L1Object> DragonDropTargetList = L1World.getInstance()
												.getVisibleObjects(npc.getMapId()).values();
										for (L1Object DUser : DragonDropTargetList) {
											if (DUser instanceof L1PcInstance && player != DUser)
												((L1PcInstance) DUser).sendPackets(new S_ServerMessage(813,
														npc.getName(), item.getLogName(), player.getName()));
										}
										player.sendPackets(new S_ServerMessage(813, npc.getName(), item.getLogName(),
												player.getName()));
									} else if (player.isInParty()) { // ��Ƽ�� ���
										L1PcInstance[] partyMember = player.getParty().getMembers();
										int Who = _random.nextInt(partyMember.length);
										L1PcInstance pc1 = partyMember[Who];

										if (player.getLocation().getTileLineDistance(pc1.getLocation()) < 14) {
											if (item != null && item.getItemId() != L1ItemId.ADENA
													&& item.getItemId() != L1ItemId.HALPAS && Config.AUTO_LOOT == 0) { // ������
												String �̸� = pc1.getName();
												String �������̸� = item.getName();
												int �����۰��� = item.getCount();
												targetInventory = pc1.getInventory();
												for (int p = 0; p < partyMember.length; p++) {
													partyMember[p].sendPackets(new S_SystemMessage("��Ƽ:" + �̸� + "�Բ���["
															+ �������̸� + "]��[" + �����۰��� + "]�� ȹ���ϼ̽��ϴ�."));
												}
												if (!player.getSkillEffectTimerSet()
														.hasSkillEffect(L1SkillId.STATUS_MENT)) {
													player.sendPackets(
															new S_ServerMessage(143, npc.getName(), item.getLogName()));
												}
											} else

											if (item != null && item.getItemId() == L1ItemId.ADENA) {
												int size = 0;
												for (L1PcInstance m : partyMember) {
													if (player.getLocation().getTileLineDistance(m.getLocation()) < 14)
														size++;
												}
												int �Ƶ��� = item.getCount() / size;
												if (size > 1) {
													for (L1PcInstance m : partyMember) {
														if (player.getLocation()
																.getTileLineDistance(m.getLocation()) < 14) {
															targetInventory = m.getInventory();
															inventory.tradeItem(item, �Ƶ���, targetInventory);
															m.sendPackets(new S_SystemMessage(
																	"��Ƽ:�Ƶ�����[" + �Ƶ��� + "]��ŭ �й��մϴ�."));
														}
													}
												} else {
													targetInventory = player.getInventory();
													inventory.tradeItem(item, �Ƶ���, targetInventory);
												}

												if (!player.getSkillEffectTimerSet()
														.hasSkillEffect(L1SkillId.STATUS_MENT)) { // ?
													player.sendPackets(
															new S_ServerMessage(143, npc.getName(), item.getLogName()));
												}

											} else

											if (item != null && item.getItemId() == L1ItemId.HALPAS) {
												int size = 0;
												for (L1PcInstance m : partyMember) {
													if (player.getLocation().getTileLineDistance(m.getLocation()) < 14)
														size++;
												}
												int ���Ľ� = item.getCount() / size;
												if (size > 1) {
													for (L1PcInstance m : partyMember) {
														if (player.getLocation()
																.getTileLineDistance(m.getLocation()) < 14) {
															targetInventory = m.getInventory();
															inventory.tradeItem(item, ���Ľ�, targetInventory);
															m.sendPackets(new S_SystemMessage(
																	"��Ƽ:�Ƶ�����[" + ���Ľ� + "]��ŭ �й��մϴ�."));
														}
													}
												} else {
													targetInventory = player.getInventory();
													inventory.tradeItem(item, ���Ľ�, targetInventory);
												}

												if (!player.getSkillEffectTimerSet()
														.hasSkillEffect(L1SkillId.STATUS_MENT)) { // ?
													player.sendPackets(
															new S_ServerMessage(143, npc.getName(), item.getLogName()));
												}

											} else

												break;
										}
										return;
									} else if (player.RootMent) { // �ַ��� ���
										player.sendPackets(new S_ServerMessage(143, npc.getName(), item.getLogName()));
									}
								}
							}
						} else {
							targetInventory = L1World.getInstance().getInventory(acquisitor.getX(), acquisitor.getY(),
									acquisitor.getMapId());
							// ���� �� ���� �������߹ؿ�����߸���
						}
						break;
					}
				}
			} else { // Non ���� ����

				int maxHatePc = -1;
				int maxHate = -1;

				for (int j = hateList.size() - 1; j >= 0; j--) {
					if (maxHate < (Integer) hateList.get(j)) {
						maxHatePc = j;
						maxHate = (Integer) hateList.get(j);
					}
				}

				if (maxHatePc != -1 && acquisitorList.get(maxHatePc) instanceof L1PcInstance) {
					item.startItemOwnerTimer((L1PcInstance) acquisitorList.get(maxHatePc));
				} else {
					item.startItemOwnerTimer(pc);
				}

				List<Integer> dirList = new ArrayList<Integer>();
				for (int j = 0; j < 8; j++) {
					dirList.add(j);
				}
				int x = 0;
				int y = 0;
				int dir = 0;
				do {
					if (dirList.size() == 0) {
						x = 0;
						y = 0;
						break;
					}
					randomInt = random.nextInt(dirList.size());
					dir = dirList.get(randomInt);
					dirList.remove(randomInt);
					switch (dir) {
					case 0:
						x = 0;
						y = -1;
						break;
					case 1:
						x = 1;
						y = -1;
						break;
					case 2:
						x = 1;
						y = 0;
						break;
					case 3:
						x = 1;
						y = 1;
						break;
					case 4:
						x = 0;
						y = 1;
						break;
					case 5:
						x = -1;
						y = 1;
						break;
					case 6:
						x = -1;
						y = 0;
						break;
					case 7:
						x = -1;
						y = -1;
						break;
					}
				} while (!npc.getMap().isPassable(npc.getX(), npc.getY(), dir));

				targetInventory = L1World.getInstance().getInventory(npc.getX() + x, npc.getY() + y, npc.getMapId());
				isGround = true;
				dirList = null;
			}
			if (itemId >= 40131 && itemId <= 40135) {
				if (isGround || targetInventory == null) {
					inventory.removeItem(item, item.getCount());
					continue;
				}
			}
			L1ItemInstance resultItem = inventory.tradeItem(item, item.getCount(), targetInventory);

			if (resultItem != null && targetInventory instanceof L1GroundInventory) {
				resultItem.setDropMobId(mobId);
			}
		}
		npc.getLight().turnOnOffLight();
		random = null;
	}

	public void setPainwandDrop(L1NpcInstance npc, L1Inventory inventory) {
		/** ���� ���� ��� */
		if (Config.STANDBY_SERVER) {
			return;
		}

		// ��� ����Ʈ�� ���
		int mobId = npc.getNpcTemplate().get_npcId();
		ArrayList<L1Drop> dropList = _droplists.get(mobId);
		if (dropList == null) {
			return;
		}

		// ����Ʈ ���
		double droprate = Config.RATE_DROP_ITEMS;
		if (droprate <= 0) {
			droprate = 0;
		}
		double adenarate = Config.RATE_DROP_ADENA;
		if (adenarate <= 0) {
			adenarate = 0;
		}
		if (droprate <= 0 && adenarate <= 0) {
			return;
		}

		int itemId;
		int itemCount;
		int addCount;
		int randomChance;
		L1ItemInstance item;
		Random random = new Random(System.nanoTime());

		for (L1Drop drop : dropList) {
			// ��� �������� ���
			itemId = drop.getItemid();
			if (adenarate == 0 && itemId == L1ItemId.ADENA) {
				continue; // �Ƶ�������Ʈ 0���� ����� �Ƶ����� ���� ����
			}
			if (itemId != L1ItemId.ADENA) {
				continue;
			}

			// ��� ���� ����
			randomChance = random.nextInt(0xf4240) + 1;
			double rateOfMapId = MapsTable.getInstance().getDropRate(npc.getMapId());
			double rateOfItem = DropItemTable.getInstance().getDropRate(itemId);
			if (droprate == 0 || drop.getChance() * droprate * rateOfMapId * rateOfItem < randomChance) {
				continue;
			}

			// ��� ������ ����
			double amount = DropItemTable.getInstance().getDropAmount(itemId);
			int min = drop.getMin();
			int max = drop.getMax();
			if (amount < 0) {
				min = (int) (min / amount);
				max = (int) (max / amount);
			} else {
				min = (int) (min * amount);
				max = (int) (max * amount);
			}

			itemCount = min;
			addCount = max - min + 1;

			if (addCount > 1) {
				itemCount += random.nextInt(addCount);
			}
			if (itemId == L1ItemId.ADENA) { // ����� �Ƶ����� ���� �Ƶ�������Ʈ�� �Ǵ�
				if (npc.getMapId() == 410) {
					itemCount = 0;
				} else {
					itemCount *= adenarate;
				}
			}
			if (itemCount < 0) {
				itemCount = 0;
			}
			if (itemCount > 2000000000) {
				itemCount = 2000000000;
			}

			// �������� ����
			item = ItemTable.getInstance().createItem(itemId);
			item.setCount(itemCount);
			// ������ �ݳ�
			inventory.storeItem(item);
		}
	}
}
