package l1j.server.server.model.item.function;

import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;

@SuppressWarnings("serial")
public class TelBook extends L1ItemInstance {
	public TelBook(L1Item item) {
		super(item);
	}
	

	private static final int[][] TownAddBook = { { 34060, 32281, 4 }, // ����
			{ 33079, 33390, 4 }, // �����
			{ 32750, 32439, 4 }, // ��ũ��
			{ 32612, 33188, 4 }, // ���ٿ��
			{ 33720, 32492, 4 }, // ����
			{ 32872, 32912, 304 }, // ħ���� ����
			{ 32612, 32781, 4 }, // �۷���
			{ 33067, 32803, 4 }, // ��Ʈ
			{ 33933, 33358, 4 }, // �Ƶ�
			{ 33601, 33232, 4 }, // ���̳�
			{ 32574, 32942, 0 }, // ���ϴ� ��
			{ 33430, 32815, 4 } }; // ���

	private static final int[][] DungeonAddBook = { { 32797, 32799, 101 }, // ����1
			// { 32809, 32723, 7 }, // ����1
			{ 32764, 32842, 77 }, // ����
			{ 32745, 32852, 59 }, // ���ٿձ�1��
			{ 32776, 32732, 45 }, // ���̱�
			{ 32681, 32877, 450 }, // ��� ������ ����

			// { 32577, 32674, 400 }, // ����� ���ױ�
			// { 32920, 32798, 430 }, // ����
			{ 32929, 32994, 410 }, // ����
			{ 34266, 32190, 4 }, // �׽�
			{ 32758, 33459, 4 }, // ���
			{ 32680, 32803, 450 }, // ����߾ӱ���
			{ 32843, 32694, 550 } }; // ������ ���� ����
	/*
	 * { 32743, 32833, 72 }, // �������� { 33444, 32828, 4 }, // ���1 { 32763, 32843,
	 * 77 }, // ����3 { 32711, 32793, 59 }, // ����1 { 32637, 33327, 4 }, // ���̱� {
	 * 32538, 32803, 400 }, // ����� ���ױ� { 32920, 32800, 430 }, // ���� { 32927,
	 * 32995, 410 }, // ���� { 34267, 32189, 4 }, // �׽� { 32760, 33461, 4 }}; //
	 * ���
	 */

	private static final int[][] OmanTowerAddBook = { { 32725, 32794, 101 }, // ����1
			{ 32730, 32802, 102 }, // ����2
			{ 32726, 32802, 103 }, // ����3
			{ 32620, 32858, 104 }, // ����4
			{ 32602, 32866, 105 }, // ����5
			{ 32611, 32862, 106 }, // ����6
			{ 32618, 32865, 107 }, // ����7
			{ 32602, 32866, 108 }, // ����8
			{ 32613, 32866, 109 }, // ����9
			{ 32730, 32802, 110 }, // ����10
			{ 32638, 32805, 111 }, // ���� ����1
			{ 32799, 32963, 111 } }; // ���� ����2

	private static final int[][] OmanTower2AddBook = { { 32725, 32794, 101 }, // ����1
			{ 32730, 32802, 102 }, // ����2
			{ 32726, 32802, 103 }, // ����3
			{ 32620, 32858, 104 }, // ����4
			{ 32602, 32866, 105 }, // ����5
			{ 32611, 32862, 106 }, // ����6
			{ 32618, 32865, 107 }, // ����7
			{ 32602, 32866, 108 }, // ����8
			{ 32613, 32866, 109 }, // ����9
			{ 32730, 32802, 110 }, // ����10
			{ 32638, 32805, 111 }, // ���� ����1
			{ 32799, 32963, 111 } }; // ���� ����2

	private static final int[][] JouAddBook = { 
		    { 34266, 32190, 4 }, // �׽��Ա�
		
		    
		    { 32507, 32924, 0 }, // ���� ���� ��
			{ 32476, 32857, 0 }, // ���� ���� �Ա�
			{ 32409, 32938, 0 }, // ���� ��ũ ���� ����
			{ 32883, 32647, 4 }, // ���� ������ ����
			{ 32875, 32927, 4 }, // ���� ������ ����
			{ 32811, 32726, 807 }, // �۷��� ���� 1��
		    { 32763, 32841, 77 }, // ���ž 4�� �Ա�
			{ 32708, 33150, 0 }, // ���� ���� ���� ����
			{ 32599, 32289, 4 }, // ���� ��ũ �ζ�
			{ 32908, 33222, 4 }, // ���� �縷(�����ں�)
			{ 32716, 33136, 4 }, // ���� �縷(�����)
			{ 32806, 32726, 19 }, // ���� �� ���� 1��
			{ 32798, 32755, 809 }, // �۷��� ���� 3��
			{ 33429, 32826, 4 }, // ��� ���� �Ա�
			{ 32809, 32729, 25 }, // ���� ���� 1��
			{ 32745, 32427, 4 }, // 
			{ 33764, 33314, 4 }, // ���� �ſ��� ��
			{ 33804, 32966, 4 }, // ���� �и� ����
			{ 32710, 32790, 59 }, // ���ٿձ� 1��
			{ 34251, 33453, 4 }, // ������ ž �Ա�
			{ 32811, 32909, 4 }, // ���� ���� ��� ����
			{ 32766, 32798, 20 }, // ���� �� ���� 2��
			{ 32726, 32808, 61 }, // ���� �ձ� 3��
			{ 32809, 32810, 30 }, // ���� ���� 1��
			{ 32807, 32766, 27 }, // ���� ���� 3��
			{ 34266, 32190, 4 }, // �׸��� ���� �Ա�
			{ 32758, 33459, 4 }, // ����� ���� �Ա�
			{ 32801, 32928, 800 }, // 
			{ 32706, 32820, 32 }, // ���� ���� 3��
			{ 33442, 33473, 4 }, // ���̳� ������ �� ��ǥ��
			{ 33182, 33006, 4 }, // ���� ������� ����
			{ 34255, 32296, 4 }, // ���� ���� ����
			{ 34051, 32477, 4 }, // ���� ����� ������
			{ 33233, 32451, 4 }, // ���� ���� ��� �Ա�
			{ 33383, 32352, 4 }, // ���� ���� ��� ����
			{ 33660, 32449, 4 }, // ���� ȭ���� ���� �Ա�
			{ 33723, 32259, 4 }, // ���� ȭ���� ���� �ֻ��
			{ 34101, 32939, 4 }, // ���� ǳ���� ���� �Ա�
			{ 34261, 32797, 4 }, // ���� ǳ���� ���� �ֻ��

	}; // ����

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			L1ItemInstance useItem = pc.getInventory().getItem(this.getId());
			int itemId = useItem.getItemId();
			if (pc.get_DuelLine() != 0) {
				pc.sendPackets(new S_SystemMessage("��Ʋ������ ����� �� �����ϴ�."));
				return;
			}
			
			if (pc.getMapId() == 99 || pc.getMapId() == 6202) {
				pc.sendPackets(new S_SystemMessage(
						"������ ���¿����� �����̵��� ����� �� �����ϴ�."), true);
				pc.sendPackets(new S_Paralysis(
						S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
				return;
			}
			int BookTel = packet.readC();
			switch (itemId) {
			case 560025:
			case 560026:

				int[] TownAddBookList = TownAddBook[BookTel];
				if (TownAddBookList != null) {
					S_SkillSound packe = null;
					packe = new S_SkillSound(pc.getId(), 169);
					pc.sendPackets(packe);
					Broadcaster.broadcastPacket(pc, packe, true);

					L1Teleport.teleport(pc, TownAddBookList[0],
							TownAddBookList[1], (short) TownAddBookList[2], 3,
							false, 0);
					pc.getInventory().removeItem(useItem, 1);
				}
				break;
			case 560027:
				int[] DungeonAddBookList = DungeonAddBook[BookTel];
				/*
				 * if(BookTel == 2){ if(pc.isKnight()){ DungeonAddBookList[0] =
				 * 32737; DungeonAddBookList[1] = 32811; DungeonAddBookList[2] =
				 * 276; }else if(pc.isCrown()){ DungeonAddBookList[0] = 32734;
				 * DungeonAddBookList[1] = 32852; DungeonAddBookList[2] = 277;
				 * }else if(pc.isElf()){ DungeonAddBookList[0] = 32735;
				 * DungeonAddBookList[1] = 32867; DungeonAddBookList[2] = 275;
				 * }else if(pc.isDarkelf()){ DungeonAddBookList[0] = 32736;
				 * DungeonAddBookList[1] = 32809; DungeonAddBookList[2] = 273;
				 * }else if(pc.isDragonknight()){ DungeonAddBookList[0] = 32734;
				 * DungeonAddBookList[1] = 32852; DungeonAddBookList[2] = 274;
				 * }else if(pc.isIllusionist()){ DungeonAddBookList[0] = 32809;
				 * DungeonAddBookList[1] = 32830; DungeonAddBookList[2] = 272;
				 * }else if(pc.isWizard()){ DungeonAddBookList[0] = 32739;
				 * DungeonAddBookList[1] = 32856; DungeonAddBookList[2] = 271; }
				 * }
				 */
				if (DungeonAddBookList != null) {
					S_SkillSound packe = null;
					packe = new S_SkillSound(pc.getId(), 169);
					pc.sendPackets(packe);
					Broadcaster.broadcastPacket(pc, packe, true);

					L1Teleport.teleport(pc, DungeonAddBookList[0],
							DungeonAddBookList[1],
							(short) DungeonAddBookList[2], 3, false, 0);
					pc.getInventory().removeItem(useItem, 1);
				}
				break;
			case 560028:

				int[] OmanTowerAddBookList = OmanTowerAddBook[BookTel];
				if (OmanTowerAddBookList != null) {
					S_SkillSound packe = null;
					packe = new S_SkillSound(pc.getId(), 169);
					pc.sendPackets(packe);
					Broadcaster.broadcastPacket(pc, packe, true);

					L1Teleport.teleport(pc, OmanTowerAddBookList[0],
							OmanTowerAddBookList[1],
							(short) OmanTowerAddBookList[2], 3, false, 0);
					pc.getInventory().removeItem(useItem, 1);
				}
				break;
			case 60203:
				int[] OmanAddBookList = OmanTower2AddBook[BookTel];
				if (OmanAddBookList != null) {
					S_SkillSound packe = null;
					packe = new S_SkillSound(pc.getId(), 169);
					pc.sendPackets(packe);
					Broadcaster.broadcastPacket(pc, packe, true);

					L1Teleport.teleport(pc, OmanAddBookList[0],
							OmanAddBookList[1], (short) OmanAddBookList[2], 3,
							false, 0);
				}
				break;
			case 60360:// ������ �̵� ���å
				int[] jouAddBookList = JouAddBook[BookTel];
				if (jouAddBookList != null) {
					S_SkillSound packe = new S_SkillSound(pc.getId(), 169);
					pc.sendPackets(packe);
					Broadcaster.broadcastPacket(pc, packe, true);

					L1Teleport.teleport(pc, jouAddBookList[0],
							jouAddBookList[1], (short) jouAddBookList[2], 3,
							false, 0);
					pc.getInventory().removeItem(useItem, 1);
				}
				break;
			}
		}
	}
}
