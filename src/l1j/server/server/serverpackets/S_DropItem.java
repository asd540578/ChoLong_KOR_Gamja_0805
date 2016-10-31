package l1j.server.server.serverpackets;

import java.text.SimpleDateFormat;
import java.util.Locale;

import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1ItemInstance;

public class S_DropItem extends ServerBasePacket {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.KOREA);
	private static final String _S__OB_DropItem = "[S] S_DropItem";

	private byte[] _byte = null;

	public S_DropItem(L1ItemInstance item) {
		buildPacket(item);
	}

	private void buildPacket(L1ItemInstance item) {
		writeC(Opcodes.S_PUT_OBJECT);
		writeH(item.getX());
		writeH(item.getY());
		writeD(item.getId());
		writeH(item.getItem().getGroundGfxId());
		writeC(0);
		writeC(0);
		if (item.isNowLighting()) {
			writeC(item.getItem().getLightRange());
		} else {
			writeC(0);
		}
		writeC(0);
		writeD(item.getCount());
		writeH(0);

		/**
		 * �����۵���� ��þ/�Ӽ� ǥ�� by���
		 */
		/***********************************************************************
		 ***********************************************************************/
		StringBuffer sb = null;
		sb = new StringBuffer();
		if (item.isIdentified()) {
			if (item.getItem().getType2() == 1 || item.getItem().getType2() == 2) {
				switch (item.getAttrEnchantLevel()) {// by���
				case 1:
					sb.append("$6115");
					break;
				case 2:
					sb.append("$6116");
					break;
				case 3:
					sb.append("$6117");
					break;
				case 4:
					sb.append("$6118");
					break;
				case 5:
					sb.append("$6119");
					break;
				case 6:
					sb.append("$6120");
					break;
				case 7:
					sb.append("$6121");
					break;
				case 8:
					sb.append("$6122");
					break;
				case 9:
					sb.append("$6123");
					break;
				case 10:
					sb.append("$6124");
					break;
				case 11:
					sb.append("$6125");
					break;
				case 12:
					sb.append("$6126");
					break;
				case 33:
					sb.append("ȭ��:4�� ");
					break;
				case 34:
					sb.append("ȭ��:5�� ");
					break;
				case 35:
					sb.append("����:4�� ");
					break;
				case 36:
					sb.append("����:5�� ");
					break;
				case 37:
					sb.append("ǳ��:4�� ");
					break;
				case 38:
					sb.append("ǳ��:5�� ");
					break;
				case 39:
					sb.append("����:4�� ");
					break;
				case 40:
					sb.append("����:5�� ");
					break;
				default:
					sb.append(" ");
					break;
				// �ּ����Ұ�� ALT���������� ���콺�÷��������� ��þǥ��
				// �ּ��Ұ�� ALT���������� ��þǥ�� (����)
				}
				// ��þ +0 �϶��� ǥ��ǰ� �ϽǺ��� �ؿ� if (item.getEnchantLevel() >= 0) {�� ��ü
				// by���
				if (item.getEnchantLevel() > 0) {
					sb.append("+" + item.getEnchantLevel() + " ");
				} else if (item.getEnchantLevel() < 0) {
					sb.append(String.valueOf(item.getEnchantLevel()) + " ");
				}
			}
		}
		sb.append(item.getItem().getNameId());
		int itemId = item.getItem().getItemId();
		if (item.getCount() > 1) {
			sb.append(" (" + item.getCount() + ")");
		} else {
			if (item.getItem().getLightRange() != 0 && item.isNowLighting()) {
				sb.append(" ($10)");
			}
		}

		if (itemId == 40312 || itemId == 49312) {
			if (item.getEndTime() != null) {
				sb.append(" [" + sdf.format(item.getEndTime().getTime()) + "] �ĺ���ȣ : " + item.getKey());
			}
		}

		if (itemId == 60285) {// �Ʒü� ����
			if (item.getEndTime() != null) {
				int mapid = item.getKey() - 1399;
				sb.append(" (" + (mapid > 9 ? mapid : "0" + mapid) + ") [" + sdf.format(item.getEndTime().getTime())
						+ "]");
			}
		}
		writeS(sb.toString());
		writeC(0);
		writeD(0);
		writeD(0);
		writeC(0xFF);
		writeC(0);
		writeC(0);
		writeH(0xFFFF);
		writeC(0);
		writeC(0x08);
		writeC(0x12);
		writeC(0x00);
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = _bao.toByteArray();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return _S__OB_DropItem;
	}

}
