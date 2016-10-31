package l1j.server.server.serverpackets;

import java.util.List;

import l1j.server.server.Opcodes;
import l1j.server.server.datatables.ExpTable;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;

public class S_Chainfo extends ServerBasePacket {

	private static final String S_Chainfo = "[S] S_Chainfo";
	private byte[] _byte = null;
	private int lvl, exp, hp, mp, ac, str, con, dex, cha, Int, wis, aden = 0;
	private String PcName, Class = null;

	public S_Chainfo(L1PcInstance pc) {
		buildPacket(pc);
	}

	private void buildPacket(L1PcInstance pc) {
		StringBuilder sb = new StringBuilder();

		PcName = pc.getName();
		lvl = pc.getLevel();
		exp = (int) ExpTable.getExpPercentage(lvl, pc.getExp());
		hp = pc.getMaxHp();
		mp = pc.getMaxMp();
		ac = pc.getAC().getAc();
		str = pc.getAbility().getTotalStr();
		dex = pc.getAbility().getTotalDex();
		con = pc.getAbility().getTotalCon();
		Int = pc.getAbility().getTotalInt();
		cha = pc.getAbility().getTotalCha();
		wis = pc.getAbility().getTotalWis();

		if (pc.getClassId() == 0) {
			Class = "����";
		} else if (pc.getClassId() == 1) {
			Class = "����";
		} else if (pc.getClassId() == 61) {
			Class = "�����";
		} else if (pc.getClassId() == 48) {
			Class = "���ä�";
		} else if (pc.getClassId() == 734) {
			Class = "������";
		} else if (pc.getClassId() == 1186) {
			Class = "������";
		} else if (pc.getClassId() == 138) {
			Class = "������";
		} else if (pc.getClassId() == 37) {
			Class = "������";
		} else if (pc.getClassId() == 2786) {
			Class = "���ٿ�";
		} else if (pc.getClassId() == 2796) {
			Class = "���ٿ�";
		} else if (pc.getClassId() == 6658) {
			Class = "������";
		} else if (pc.getClassId() == 6661) {
			Class = "������";
		} else if (pc.getClassId() == 6671) {
			Class = "��ȯ����";
		} else if (pc.getClassId() == 6671) {
			Class = "��ȯ����";
		}

		if (pc.getInventory().checkItem(40308)) {
			aden = pc.getInventory().countItems(40308);
		}
		sb.append(PcName);
		sb.append(" ");
		sb.append(Class);
		sb.append(" ����\n ����:");
		sb.append(lvl);
		sb.append(" ");
		sb.append(exp);
		sb.append("%\n HP:");
		sb.append(hp);
		sb.append(" MP:");
		sb.append(mp);
		sb.append(" AC:");
		sb.append(ac);
		sb.append("\n STR:");
		sb.append(str);
		sb.append(" DEX:");
		sb.append(dex);
		sb.append(" CON:");
		sb.append(con);
		sb.append("\n WIS:");
		sb.append(wis);
		sb.append(" CHA:");
		sb.append(cha);
		sb.append(" INT:");
		sb.append(Int);
		sb.append("\n");
		sb.append(" �Ƶ� ");
		sb.append(aden);
		sb.append(" ��");
		sb.append("\n");
		sb.append("<���� ������ ���>\n");
		List<L1ItemInstance> items = pc.getInventory().getItems();
		for (L1ItemInstance item : items) {
			if (item.isEquipped()) {
				sb.append(ItemLogName(item, item.getCount()));
				sb.append("\n");
			}
		}
		writeC(Opcodes.S_BOARD_READ);
		writeD(9999);// �ѹ�
		writeS("ĳ���� ����");// �۾���?
		writeS("");// ��¥?
		writeS("");// ����?
		writeS(sb.toString());
	}

	private String ItemLogName(L1ItemInstance item, int count) {
		int attr = item.getAttrEnchantLevel();
		int elv = item.getEnchantLevel();
		boolean iden = item.isIdentified();

		StringBuilder SB = new StringBuilder();
		// 0:�ູ 1:��� 2:���� 3:�̰���
		if (iden) {
			if (item.getBless() == 0) {
				SB.append(" �� ");
			} else if (item.getBless() == 1) {
				SB.append(" �� ");
			} else if (item.getBless() == 2) {
				SB.append(" �� ");
			}
		} else {
			SB.append(" �� ");
		}
		if (attr > 0) {
			switch (attr) {
			case 1:
				SB.append("ȭ��:1�� ");
				break;
			case 2:
				SB.append("ȭ��:2�� ");
				break;
			case 3:
				SB.append("ȭ��:3�� ");
				break;
			case 4:
				SB.append("����:1�� ");
				break;
			case 5:
				SB.append("����:2�� ");
				break;
			case 6:
				SB.append("����:3�� ");
				break;
			case 7:
				SB.append("ǳ��:1�� ");
				break;
			case 8:
				SB.append("ǳ��:2�� ");
				break;
			case 9:
				SB.append("ǳ��:3�� ");
				break;
			case 10:
				SB.append("����:1�� ");
				break;
			case 11:
				SB.append("����:2�� ");
				break;
			case 12:
				SB.append("����:3�� ");
				break;
			case 33:
				SB.append("ȭ��:4�� ");
				break;
			case 34:
				SB.append("ȭ��:5�� ");
				break;
			case 35:
				SB.append("����:4�� ");
				break;
			case 36:
				SB.append("����:5�� ");
				break;
			case 37:
				SB.append("ǳ��:4�� ");
				break;
			case 38:
				SB.append("ǳ��:5�� ");
				break;
			case 39:
				SB.append("����:4�� ");
				break;
			case 40:
				SB.append("����:5�� ");
				break;
			}
		}

		if (elv > 0) {
			SB.append("+");
			SB.append(elv);
			SB.append(" ");
		}
		SB.append(item.getName());
		SB.append(" ");
		if (count > 1) {
			SB.append("(");
			SB.append(count);
			SB.append(")");
		}
		return SB.toString();
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_Chainfo;
	}
}
