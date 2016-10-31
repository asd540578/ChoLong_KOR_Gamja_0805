package l1j.server.server.clientpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javolution.util.FastTable;
import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Antaras.AntarasRaidSystem;
import l1j.server.GameSystem.Lind.LindRaid;
import l1j.server.GameSystem.Papoo.PaPooRaidSystem;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1TownLocation;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_CharPass;
import l1j.server.server.serverpackets.S_ClanWindow;
import l1j.server.server.serverpackets.S_NewCharSelect;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1BookMark;
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_Report extends ClientBasePacket {

	private static final String C_REPORT = "[C] C_Report";
	private static Logger _log = Logger.getLogger(C_Report.class.getName());
	public static final int �ڵ��Ű� = 0x00;
	public static final int ������â��Ƽ�� = 0x0D;
	public static final int dragon_menu = 0x06;
	public static final int MapSend = 0x0B;
	public static final int bookmark_save = 0x22;
	public static final int bookmark_name_change = 0x27;
	public static final int bookmark_item_save = 0x28;
	public static final int newCharSelect = 0x2B;
	
	/** Ȩ������ ���� ������ **/
	public static final int HTTP = 0x13;
	public static final int BOOKMARK_SAVE = 0x22;
	public static final int BOOKMARK_COLOR = 0x27;
	public static final int BOOKMARK_LOADING_SAVE = 0x28;
	public static final int EMBLEM = 0x2e; //�����ֽ�
	public static final int TELPORT = 0x30; // �����ڷ���Ʈ
	public static final int �ɸ��ͻ��� = 43;
	//public static final int �� = 0x37;
	public static final int �Ŀ��ϰ˻� = 0x13;
	public static final int ����ã�� = 0x31;
	public static final int ��������Ƚ�� = 0x39;

	
	
	public static final int show_clanMark = 0x2e;
	public static final int ����ų�ʱ�ȭ = 0x2C;
	public static final int MapTeleport = 0x30;
	public static final int �� = 0x37;
	public static final int �ɸ��ͺ������ = 0x0e;
	public static final int �ɸ��ͺ������ = 0x10;
	public static final int �ɸ��ͺ������ = 0x11;
	byte[] str = { 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39 };

	private boolean createNewItem(L1PcInstance pc, int item_id, int count) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			item.setIdentified(true);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else { // ���� �� ���� ���� ���鿡 ����߸��� ó���� ĵ���� ���� �ʴ´�(���� ����)
				L1World.getInstance()
						.getInventory(pc.getX(), pc.getY(), pc.getMapId())
						.storeItem(item);
			}
			pc.sendPackets(new S_ServerMessage(403, item.getLogName()), true); 
			return true;
		} else {
			return false;
		}
	}

	public C_Report(byte abyte0[], LineageClient clientthread) throws Exception {
		super(abyte0);
		try {
			int type = readC();
			L1PcInstance pc = clientthread.getActiveChar();
			switch (type) {
			case ��: {
				byte[] data = null;
				int type2 = 0;
				try {
					data = pc.������;
					type2 = readH();
					if (data[type2] == 1) {
						return;
					}
				} catch (Exception e) {
					return;
				}
				int itemid = 0;
				int count = 1;
				int level = 0;
				switch (type2) {
				case 0x03: {
				}
					break;// �ֵ��ǻ���

				case 0x27: {
					itemid = 7325;
					level = 10;
				}
					break;// 10����
				case 0x28: {
					itemid = 7326;
					level = 15;
				}
					break;// 15����
				case 0x29: {
					itemid = 7327;
					level = 20;
				}
					break;// 20����
				case 0x2a: {
					itemid = 7328;
					level = 25;
				}
					break;// 25����
				case 0x2b: {
					itemid = 7329;
					level = 30;
				}
					break;// 30����
				case 0x2c: {
					itemid = 7330;
					level = 35;
				}
					break;// 35����
				case 0x2d: {
					itemid = 7331;
					level = 40;
				}
					break;// 40����
				case 0x2e: {
					itemid = 7332;
					level = 45;
				}
					break;// 45����
				case 0x2f: {
					itemid = 7333;
					level = 50;
				}
					break;// 50����
				case 0x30: {
					itemid = 7334;
					level = 52;
				}
					break;// 52����

				// -----------------52���ʹ� �����幰�ุ------------//

				case 0x31: {
					itemid = 7312;
					level = 53;
				}
					break;// 53����
				case 0x32: {
					itemid = 7312;
					level = 54;
				}
					break;// 54����
				case 0x33: {
					itemid = 7312;
					level = 55;
				}
					break;// 55����
				case 0x34: {
					itemid = 7312;
					level = 56;
				}
					break;// 56����
				case 0x35: {
					itemid = 7312;
					level = 57;
				}
					break;// 57����
				case 0x36: {
					itemid = 7312;
					level = 58;
				}
					break;// 58����
				case 0x37: {
					itemid = 7312;
					level = 59;
				}
					break;// 59����
				case 0x38: {
					itemid = 7312;
					level = 60;
				}
					break;// 60����

				case 0x3c: {
					itemid = 140100;
					count = 2;
					level = 50;
					L1BookMark.addBookmark(pc, 32609, 32771, 4,
							"\\d0\\fR 50����: ������");
				}
					break;// 50���� ����ͱ��
				case 0x3d: {
					itemid = 140100;
					count = 2;
					level = 51;
					L1BookMark.addBookmark(pc, 33443, 32829, 4,
							"\\d0\\fR 51����: ������ ����");
				}
					break;// 51���� ����ͱ��
				case 0x3e: {
					itemid = 140100;
					count = 2;
					level = 52;
					L1BookMark.addBookmark(pc, 34068, 32271, 4,
							"\\d0\\fR 52����: ���� ���� ����");
				}
					break;// 52���� ����ͱ��
				case 0x3f: {
					itemid = 140100;
					count = 2;
					level = 53;
					L1BookMark.addBookmark(pc, 32595, 33164, 4,
							"\\d0\\fU 53����: ��������(����)");
				}
					break;// 53���� ����ͱ��
				case 0x40: {
					itemid = 140100;
					count = 2;
					level = 54;
					L1BookMark.addBookmark(pc, 32578, 32924, 0,
							"\\d0\\fU 54����: ������(�δ�)");
				}
					break;// 54���� ����ͱ��
				case 0x41: {
					itemid = 140100;
					count = 2;
					level = 55;
					L1BookMark.addBookmark(pc, 32884, 32782, 4,
							"\\d0\\fU 55����: 55���� ����Ʈ");
					L1BookMark.addBookmark(pc, 33617, 33247, 4,
							"\\d0\\fY 55����: ������ ����");
				}
					break;// 55���� ����ͱ��
				case 0x42: {
					itemid = 140100;
					count = 2;
					level = 56;
					L1BookMark.addBookmark(pc, 33427, 32827, 4,
							"\\d0\\fY 56����: ��� ����");
				}
					break;// 56���� ����ͱ��
				case 0x43: {
					itemid = 140100;
					count = 2;
					level = 57;
					L1BookMark.addBookmark(pc, 34051, 32265, 4,
							"\\d0\\fY 57����: ���ž");
				}
					break;// 57���� ����ͱ��
				case 0x44: {
					itemid = 140100;
					count = 2;
					level = 58;
					L1BookMark.addBookmark(pc, 32719, 32929, 4,
							"\\d0\\fY 58����: �۷��� ����");
				}
					break;// 58���� ����ͱ��
				case 0x45: {
					itemid = 140100;
					count = 2;
					level = 59;
					L1BookMark.addBookmark(pc, 32476, 32857, 0,
							"\\d0\\fU 59����: ���ϴ� �� ����");
				}
					break;// 59���� ����ͱ��
				/*
				 * \d0\fY 60����: \d0\fY 60����: ���ϴ� �� ���� \d0\fY 60����: ȭ���� ����
				 */
				case 0x46: {
					level = 45;
				}
					break;// 45���� ���̵�
				case 0x47: {
					level = 45;
				}
					break;// 46���� ���̵�
				case 0x53: {
					level = 45;
				}
					break;// 47���� ���̵�
				case 0x48: {
					level = 45;
				}
					break;// 48���� ���̵�
				case 0x55: {
					level = 45;
				}
					break;// 49���� ���̵�
				case 0x49: {
					level = 45;
				}
					break;// 50���� ���̵�
				case 0x4a: {
					level = 45;
				}
					break;// 51���� ���̵�
				case 0x4b: {
					level = 45;
				}
					break;// 52���� ���̵�
				case 0x4c: {
					level = 45;
				}
					break;// 53���� ���̵�
				case 0x4d: {
					level = 45;
				}
					break;// 54���� ���̵�
				case 0x4e: {
					level = 45;
				}
					break;// 54���� ���̵�
				case 0x4f: {
					level = 45;
				}
					break;// 54���� ���̵�
				case 0x50: {
					level = 57;
				}
					break;// 57���� ���̵�
				case 0x51: {
					level = 58;
				}
					break;// 58���� ���̵�
				case 0x52: {
					level = 59;
				}
					break;// 59���� ���̵�
				case 0x54: {
					level = 60;
				}
					break;// 60���� ���̵�

				case 0x59: {
				}
					break;// �����Ҷ���?
				case 0x56: {
				}
					break;// �𸣴� ��ȣ1
				default:
					System.out.println("������� ���� �� ��ȣ :[" + type2 + "]");
					break;
				}

				if (pc.getInventory().calcWeightpercent() >= 90) {
					// pc.sendPackets(new S_ServerMessage(3561)); // ���� ��������
					// ����á���ϴ�.
					pc.sendPackets(new S_SystemMessage(
							"ȹ�� ���� : ���� ������ 90%�̻� ���� �������ֽ��ϴ�."));
					pc.sendPackets(new S_SystemMessage(
							"[���� �������� ���̽��� ����ŸƮ �Ͻø� �ٽ� ������ �ֽ��ϴ�.]"));
					return;
				}

				if (level != 0) {
					pc.����������(type2);
					pc.������ġ����(level);
					if (itemid != 0) {// �������ֱ�
						createNewItem(pc, itemid, count);
						// L1ItemInstance item =
						// pc.getInventory().storeItem(itemid, count);
						// pc.sendPackets(new S_ServerMessage(403,
						// item.getName()+"("+count+")"), true);
					}
				}
			}
				break;
			case �ɸ��ͺ������: {

				String password = readS();

				if (clientthread.getAccount().getCPW() != null) {
					return;
				} else {
					clientthread.getAccount().setCPW(password);
					clientthread.getAccount().UpdateCharPassword(password);
					clientthread.sendPacket(new S_CharPass(S_CharPass._��������Ϸ�â));
				}
			}
				break;
			case �ɸ��ͺ������: {
				
				String password = readS();
				String npassword = readS();

				if (clientthread.getAccount().getCPW() == null) {
					return;
				} else {
					if (clientthread.getAccount().getCPW().equals(password)) {
						clientthread.getAccount().setCPW(npassword);
						clientthread.getAccount().UpdateCharPassword(npassword);
						clientthread.sendPacket(new S_CharPass(
								S_CharPass._�������亯, true));
					} else {
						clientthread.sendPacket(new S_CharPass(
								S_CharPass._�������亯, false));
					}
				}

				break;
			}
			case �ɸ��ͺ������: {
				String password = readS();
				
				if (clientthread.getAccount().getCPW() == null) {

					return;
				} else {
					if (clientthread.getAccount().getCPW().equals(password)) {
						clientthread.getAccount().setcpwok(true);
						if (clientthread.getAccount().getwaitpacket() != null) {
							clientthread.packetwaitgo(clientthread.getAccount()
									.getwaitpacket());
						}
					} else {
						clientthread.sendPacket(new S_CharPass(
								S_CharPass._����Էº��Ʋ��));
					}
				}

			}
				break;
			case newCharSelect:
				if(Config.ĳ���ͺ����뿩�� == true){
					if (!clientthread.getAccount().iscpwok()) {
						if (clientthread.getAccount().getCPW() == null) {
							clientthread.sendPacket(new S_CharPass(S_CharPass._�������â));
						} else {
							clientthread.getAccount().setwaitpacket(abyte0);
							clientthread.sendPacket(new S_CharPass(S_CharPass._����Է�â));
						}
					} else {
						clientthread.sendPacket(new S_NewCharSelect());
					}
				} else {
					clientthread.sendPacket(new S_NewCharSelect());
				}
				break;
			case show_clanMark: {
				int on = readC();
				L1Clan clan = pc.getClan();
				if (clan == null) {
					return;
				}
				if (!clan.getLeaderName().equalsIgnoreCase(pc.getName()))
					return;
				clan.setmarkon(on);
				ClanTable.getInstance().updateClan(clan);
				for (L1PcInstance targetPc : clan.getOnlineClanMember()) { // �¶���
					targetPc.sendPackets(new S_ClanWindow(S_ClanWindow.����ũ����,
							on), true);
				}
			}
				break;
			case ����ų�ʱ�ȭ: {
				L1PcInstance pc1 = clientthread.getActiveChar();
				if (pc1 == null)
					return;
				pc1._PlayMonKill = 0;
				/*
				 * pc.sendPackets(new S_PacketBox(1, 0,
				 * "c9 19 00 14 38 f7 e0 1d 50 d1 44 a0 38 83 a6 91 "+
				 * "71 8c b0 76 81 ea c4 5c 6f a2 42 92 64 f4 18 70 "+
				 * "c1 83 ca"), true);
				 */
				/*
				 * byte[] data = readByte(); int size = data.length-2; byte[]
				 * buf1 = new byte[size];
				 * 
				 * System.arraycopy(data, 0, buf1, 0, size); pc.sendPackets(new
				 * S_ReturnedStat(S_ReturnedStat.POWER_BOOK, buf1), true);
				 * pc.sendPackets(new S_ReturnedStat(0x0F, null), true);
				 */
				// String val = readS();
				// S_SabuBox sn = new S_SabuBox(S_SabuBox.�Ŀ��ϰ˻�, val);
				// pc.sendPackets(sn);sn = null;
				// pc.sendPackets(new
				// S_SystemMessage("��Ʈ�� + F -> �ڽ� Ŭ�� -> Ȩ Ŭ�� �� �̿����ּ���."));
			}
				break;
			case �ڵ��Ű�:
				int targetid = readD();
				L1Object tar = L1World.getInstance().findObject(targetid);

				L1PcInstance targetpc = null;

				if (tar == null)
					return;
				if (!(tar instanceof L1PcInstance)) {
					return;
				} else {
					targetpc = (L1PcInstance) tar;
				}

				if (targetpc.isGm())
					return;
				if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�Ű������)) {
					int time = pc.getSkillEffectTimerSet()
							.getSkillEffectTimeSec(L1SkillId.�Ű������);
					pc.sendPackets(new S_SystemMessage("(" + time
							+ ") ���� �ٽ� �̿��� �ּ���."));
					return;
				}
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.�Ű������,
						60000);

				// String date = currentTime();
				Timestamp date = new Timestamp(System.currentTimeMillis());
				�Ű���(targetpc, date);

				pc.sendPackets(new S_ServerMessage(1019));
				break;
			case ������â��Ƽ��:
				int on = readC();
				if (pc == null)
					return;
				if (on == 0) {
					pc.window_active_time = System.currentTimeMillis()
							+ (1000 * 5);
				} else {
					pc.window_active_time = -1;
				}
				break;
			case dragon_menu:
				int itemid = readD();
				int dragonType = readC(); // ��Ÿ0, ��Ǫ1, ����2, �߶�3
				L1ItemInstance DragonKey = pc.getInventory().getItem(itemid);
				if (DragonKey == null)
					return;

				switch (dragonType) {
				case 0: // ��Ÿ
					AntarasRaidSystem.getInstance().startRaid(pc);
					L1World.getInstance()
							.broadcastServerMessage(
									"��ö ��� ������: ��...�巡���� ���¢���� ������� �鸮��. �ʽ� ������ �巡�� ��Ż�� �� ���� Ȯ���Ͽ�! �غ�� �巡�� �����̾�� ������ �ູ��!");
					break;
				case 1: // ��Ǫ
					PaPooRaidSystem.getInstance().startRaid(pc);
					L1World.getInstance()
							.broadcastServerMessage(
									"��ö ��� ������: ��...�巡���� ���¢���� ������� �鸮��. �ʽ� ������ �巡�� ��Ż�� �� ���� Ȯ���Ͽ�! �غ�� �巡�� �����̾�� ������ �ູ��!");
					break;
				case 2: // ����
					LindRaid.get().start(pc);
					break;
				case 3: // �߶�
					break;
				default:
					break;
				}
				break;
			case MapSend:
				String targetName = null;
				int mapid = 0,
				x = 0,
				y = 0,
				Mid = 0;
				try {
					targetName = readS();
					mapid = readH();
					x = readH();
					y = readH();
					Mid = readH();
				} catch (Exception e) {
					return;
				}
				L1PcInstance target = L1World.getInstance().getPlayer(
						targetName);
				if (target == null) {
					S_SystemMessage sm = new S_SystemMessage(
							"�ش� ĳ���͸� ã�� �� �����ϴ�.");
					pc.sendPackets(sm, true);
				} else if (pc == target) {
					S_SystemMessage sm = new S_SystemMessage("�ڽſ��� ���� �� �����ϴ�.");
					pc.sendPackets(sm, true);
				} else {
					S_PacketBox pb = new S_PacketBox(pc.getName(), mapid, x, y,
							Mid);
					target.sendPackets(pb, true);
					S_SystemMessage sm = new S_SystemMessage(target.getName()
							+ "�Կ��� ���� �Ǿ����ϴ�.");
					pc.sendPackets(sm, true);
				}
				break;
			case HTTP:
				break;
			
			case bookmark_save:
				int subType = readC();
				if (subType == 2) {
					L1BookMark.deleteCharBookmark(pc);
					L1BookMark[] list = pc.getBookMark();
					if (list.length > 0) {

						for (L1BookMark book : list) {
							book.set_fast(0);
						}

						FastTable<Integer> fs = new FastTable<Integer>();
						for (int i = 0; i < list.length; i++) {
							fs.add(readC());
						}

						for (int i = list.length; i < 128; i++) {
							int num = readC();
							if (num == 255)
								break;
							L1BookMark book = list[num];
							book.set_fast(1);
						}

						/** ���â ���� �̵� ���� **/
						for (int i = 0; i < list.length; i++) {
							int loc = fs.indexOf(i);
							L1BookMark book = list[loc];
							L1BookMark.insertResetIDBookmark(book);
						}
					}
				}
				break;
			case bookmark_name_change:
				int size = readD();
				for (int i = 0; i < size; i++) {
					int id = readD();
					String name = readS();
					L1BookMark book = pc.getBookMark(id);
					if (book != null)
						book.setName(name);
				}
				break;
			case bookmark_item_save: {
				int itemidd = readD(); // itemid
				int sizez = readH(); // size
				// �����ŭ readD(); �ؼ� �� id(���� �ϸ�ũid) �޾ƿ�
				if (sizez <= 0)
					return;
				L1ItemInstance item3 = pc.getInventory().getItem(itemidd);
				pc.getInventory().removeItem(item3, 1);
				if (item3.getItemId() != 60083)
					return;

				L1ItemInstance item4 = ItemTable.getInstance()
						.createItem(60084);
				item4.setCount(1);
				item4.setIdentified(true);
				item4.setCreaterName(pc.getName());
				pc.getInventory().storeItem(item4);
				L1BookMark.ItemaddBookmarkByTeleport(pc, item4.getId());
			}
				break;
			case MapTeleport: {
				int mapType = readH();
				int locType = readH();
				if (mapType == 1) {// �Ƶ�
					if (L1TownLocation.getTownIdByLoc(pc.getX(), pc.getY()) == L1TownLocation.TOWNID_ADEN) {
						if (locType == 0)
							L1Teleport.teleport(pc, 34086, 33142, (short) 4, 5,
									true);
						else if (locType == 1)
							L1Teleport.teleport(pc, 33980, 33249, (short) 4, 5,
									true);
						else if (locType == 2)
							L1Teleport.teleport(pc, 33929, 33358, (short) 4, 5,
									true);
					}
				} else if (mapType == 2) {// �۸�
					if (L1TownLocation.getTownIdByLoc(pc.getX(), pc.getY()) == L1TownLocation.TOWNID_GLUDIO) {
						if (locType == 0)
							L1Teleport.teleport(pc, 32625, 32721, (short) 4, 5,
									true);
						else if (locType == 1)
							L1Teleport.teleport(pc, 32628, 32795, (short) 4, 5,
									true);
					}
				} else if (mapType == 3) {// ���
					if (L1TownLocation.getTownIdByLoc(pc.getX(), pc.getY()) == L1TownLocation.TOWNID_GIRAN) {
						if (locType == 0)
							L1Teleport.teleport(pc, 33507, 32767, (short) 4, 5,
									true);
						else if (locType == 1)
							L1Teleport.teleport(pc, 33442, 32799, (short) 4, 5,
									true);
					}
				} else if (mapType == 4) {// ���ս���
					if (pc.getMapId() == 800) {
						Random rnd = new Random(System.nanoTime());
						switch (locType) {
						case 0:
							L1Teleport.teleport(pc, 32842 + rnd.nextInt(9),
									32877 + rnd.nextInt(9), (short) 800, 5,
									true);
							break;
						case 1:
							L1Teleport.teleport(pc, 32796 + rnd.nextInt(9),
									32877 + rnd.nextInt(9), (short) 800, 5,
									true);
							break;
						case 2:
							L1Teleport.teleport(pc, 32751 + rnd.nextInt(9),
									32877 + rnd.nextInt(9), (short) 800, 5,
									true);
							break;
						case 3:
							L1Teleport.teleport(pc, 32737 + rnd.nextInt(9),
									32922 + rnd.nextInt(9), (short) 800, 5,
									true);
							break;
						case 4:
							L1Teleport.teleport(pc, 32737 + rnd.nextInt(9),
									32968 + rnd.nextInt(9), (short) 800, 5,
									true);
							break;
						case 5:
							L1Teleport.teleport(pc, 32797 + rnd.nextInt(9),
									32969 + rnd.nextInt(9), (short) 800, 5,
									true);
							break;
						case 6:
							L1Teleport.teleport(pc, 32842 + rnd.nextInt(9),
									32968 + rnd.nextInt(9), (short) 800, 5,
									true);
							break;
						case 7:
							L1Teleport.teleport(pc, 32842 + rnd.nextInt(9),
									32922 + rnd.nextInt(9), (short) 800, 5,
									true);
							break;
						case 8:
							L1Teleport.teleport(pc, 32795 + rnd.nextInt(12),
									32921 + rnd.nextInt(12), (short) 800, 5,
									true);
							break;
						default:
							break;
						}
					}
				}
				pc.TownMapTeleporting = true;
			}
				break;
			case ����ã��: {
				if (pc.getMapId() == 800) {
					try {
						String name = readS();
						if (name == null)
							return;
						Random rnd = new Random(System.nanoTime());
						L1PcInstance pn = L1World.getInstance().getPlayer(name);
						if (pn != null && pn.getMapId() == 800
								&& pn.isPrivateShop()) {
							pc.dx = pn.getX() + rnd.nextInt(3) - 1;
							pc.dy = pn.getY() + rnd.nextInt(3) - 1;
							pc.dm = (short) pn.getMapId();
							pc.dh = calcheading(pc.dx, pc.dy, pn.getX(),
									pn.getY());
							pc.����ã��Objid = pn.getId();
							pc.setTelType(7);
							pc.sendPackets(new S_SabuTell(pc), true);
						} else {
							L1NpcShopInstance nn = L1World.getInstance()
									.getNpcShop(name);
							if (nn != null && nn.getMapId() == 800
									&& nn.getState() == 1) {
								pc.dx = nn.getX() + rnd.nextInt(3) - 1;
								pc.dy = nn.getY() + rnd.nextInt(3) - 1;
								pc.dm = (short) nn.getMapId();
								pc.dh = calcheading(pc.dx, pc.dy, nn.getX(),
										nn.getY());
								pc.����ã��Objid = nn.getId();
								pc.setTelType(7);
								pc.sendPackets(new S_SabuTell(pc), true);
							} else {
								pc.sendPackets(new S_SystemMessage(
										"����ã�� : ã���ô� ������ �����ϴ�."), true);
							}
						}
						rnd = null;
					} catch (Exception e) {
					}
				}
			}
				break;
			/*
			 * case 0x25:{ C_SelectCharacter cs = new C_SelectCharacter(new
			 * byte[1]); cs.clogin(clientthread.selectCharName, clientthread); }
			 * break;
			 */
			case ��������Ƚ��:
				if (pc.getNetConnection() == null
						|| pc.getNetConnection().getAccount() == null)
					return;
				pc.sendPackets(new S_PacketBox(S_PacketBox.��������Ƚ��, pc
						.getNetConnection().getAccount().Shop_open_count), true);
				break;
			default:
				// System.out.println("C_Report ���� Type : "+type);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clear();
		}
	}

	public void �Ű���(L1PcInstance pc, Timestamp date) {
		int cnt = 1;
		Connection con = null;
		PreparedStatement pstm1 = null;
		ResultSet rs = null;
		PreparedStatement pstm2 = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm1 = con
					.prepareStatement("SELECT * FROM _Report WHERE name = ?");
			pstm1.setString(1, pc.getName());
			rs = pstm1.executeQuery();
			if (rs.next()) {
				cnt = rs.getInt("count") + 1;
				pstm2 = con
						.prepareStatement("UPDATE _Report SET  count = ? , date = ? WHERE name = ?");
				pstm2.setInt(1, cnt);
				pstm2.setTimestamp(2, date);
				pstm2.setString(3, pc.getName());
				pstm2.executeUpdate();
			} else {
				pstm2 = con
						.prepareStatement("INSERT INTO  _Report SET name = ? , count = ? , date = ? ");
				pstm2.setString(1, pc.getName());
				pstm2.setInt(2, cnt);
				pstm2.setTimestamp(3, date);
				pstm2.executeUpdate();
			}
		} catch (SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm1);
			SQLUtil.close(pstm2);
			SQLUtil.close(con);
		}
	}

	public static String currentTime() {
		TimeZone tz = TimeZone.getTimeZone(Config.TIME_ZONE);
		Calendar cal = Calendar.getInstance(tz);
		int year = cal.get(Calendar.YEAR) - 2000;
		String year2;
		if (year < 10) {
			year2 = "0" + year;
		} else {
			year2 = Integer.toString(year);
		}
		int Month = cal.get(Calendar.MONTH) + 1;
		String Month2 = null;
		if (Month < 10) {
			Month2 = "0" + Month;
		} else {
			Month2 = Integer.toString(Month);
		}
		int date = cal.get(Calendar.DATE);
		String date2 = null;
		if (date < 10) {
			date2 = "0" + date;
		} else {
			date2 = Integer.toString(date);
		}
		return year2 + "/" + Month2 + "/" + date2;
	}

	private static String HexToDex(int data, int digits) {
		String number = Integer.toHexString(data);
		for (int i = number.length(); i < digits; i++)
			number = "0" + number;
		return number;
	}

	public static String DataToPacket(byte[] data, int len) {
		StringBuffer result = new StringBuffer();
		int counter = 0;
		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				result.append(HexToDex(i, 4) + ": ");
			}
			result.append(HexToDex(data[i] & 0xff, 2) + " ");
			counter++;
			if (counter == 16) {
				result.append("   ");
				int charpoint = i - 15;
				for (int a = 0; a < 16; a++) {
					int t1 = data[charpoint++];
					if (t1 > 0x1f && t1 < 0x80) {
						result.append((char) t1);
					} else {
						result.append('.');
					}
				}
				result.append("\n");
				counter = 0;
			}
		}
		int rest = data.length % 16;
		if (rest > 0) {
			for (int i = 0; i < 17 - rest; i++) {
				result.append("   ");
			}
			int charpoint = data.length - rest;
			for (int a = 0; a < rest; a++) {
				int t1 = data[charpoint++];
				if (t1 > 0x1f && t1 < 0x80) {
					result.append((char) t1);
				} else {
					result.append('.');
				}
			}
			result.append("\n");
		}
		return result.toString();
	}

	private int calcheading(int myx, int myy, int tx, int ty) {
		if (tx > myx && ty > myy) {
			return 3;
		} else if (tx < myx && ty < myy) {
			return 7;
		} else if (tx > myx && ty == myy) {
			return 2;
		} else if (tx < myx && ty == myy) {
			return 6;
		} else if (tx == myx && ty < myy) {
			return 0;
		} else if (tx == myx && ty > myy) {
			return 4;
		} else if (tx < myx && ty > myy) {
			return 5;
		} else {
			return 1;
		}
	}

	@Override
	public String getType() {
		return C_REPORT;
	}
}
