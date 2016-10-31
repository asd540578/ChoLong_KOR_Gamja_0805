package l1j.server.server.model.item.function;

import java.util.Random;

import l1j.server.Config;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

public class LeafItem {

	private static Random _random = new Random(System.nanoTime());

	public static void clickItem(L1PcInstance pc, int itemId, L1ItemInstance l1iteminstance, L1ItemInstance l1iteminstance1) {


		switch (itemId) {
		case 31086:{//�����ǳ�����
			int targetItem = l1iteminstance1.getItemId();
			int[] item = new int[] { 31086 };//�ʿ������
			int[] �ݺ����� = new int[] { 20049 };		//�Ŵ� ���� �ݺ� ����
			int[] �������� = new int[] { 20050 };		//�Ŵ� ���� ���� ����
			int[] ����ü�μҵ� = new int[] { 1119 };	//������ ü�μҵ�
			int[] ������ = new int[] { 22009 };		//����� ����
			int[] ī�� = new int[] { 41148 };			//�����(ī���� �踮��)
			int[] ���� = new int[] { 1121 };			//����Ʈ�ߵ��� ��հ�
			int[] ��Ű = new int[] { 1120 };			//������ Ű��ũ
			int[] ���� = new int[] { 119 };			//������ ������
			int[] �������� = new int[] { 20100 };		//��������Ʈ�� ����
			int[] �������� = new int[] { 20198 };		//��������Ʈ�� ����
			int[] ���� = new int[] { 58 };			//��������Ʈ�� �Ұ�
			int[] �����尩 = new int[] { 20166 };		//��������Ʈ�� �尩
			int[] �������� = new int[] { 20010 };		//��������Ʈ�� ����
			int[] ����� = new int[] { 20250 };		//����۾� ������ �����
			int[] ������� = new int[] { 20277 };		//����۾� ������ ������ ����
			int[] ������� = new int[] { 20278 };		//����۾� ������ ���� ����
			int[] ���� = new int[] { 22261 };			//�� �尩
			int[] ���̾ƹ��� = new int[] { 20279 };		//���̾��� ����
			int[] �е��̵��� = new int[] { 76 };		//�е��� �̵���
			int[] ��ġ�κ� = new int[] { 20107 };		//��ġ �κ�
			int[] �������� = new int[] { 126 };		//������ ������
			int[] �������� = new int[] { 222328 };		//���� ��� ����
			int[] �� = new int[] { 40222 };			//������(����Ƽ�׷���Ʈ)
			int[] ��Ƽ�� = new int[] { 40219 };		//������(��Ƽ�� ��Ʈ����ũ)
			int[] ���չ��� = new int[] { 22008 };		//������ ����
			int[] �ӹ̿հ� = new int[] { 20017 };		//�ӹ̷ε��� �հ�
			int[] �޸����� = new int[] { 20018 };		//�޸�Ű������ ����
			int[] ��ø���� = new int[] { 22364 };		//��ø�� ����
			int[] ���� = new int[] { 124 };			//������Ʈ�� ������
			int[] �ݿ��� = new int[] { 22263 };		//�ݿ����� ����
			int[] ���͸��� = new int[] { 20025 };		//�����ڸ��� ����
			int[] ��� = new int[] { 20079 };			//�����̾��� ����
			int[] ����10 = new int[] { 830041};		//���ε� ������ ž 10�� �̵� ����
			int[] ����1 = new int[] { 830032 };		//���ε� ������ ž 1�� �̵� ����
			int[] ����2 = new int[] { 830033 };		//���ε� ������ ž 2�� �̵� ����
			int[] ����3 = new int[] { 830034 };		//���ε� ������ ž 3�� �̵� ����
			int[] ����4 = new int[] { 830035 };		//���ε� ������ ž 4�� �̵� ����
			int[] ����5 = new int[] { 830036 };		//���ε� ������ ž 5�� �̵� ����
			int[] ����6 = new int[] { 830037 };		//���ε� ������ ž 6�� �̵� ����
			int[] ����7 = new int[] { 830038 };		//���ε� ������ ž 7�� �̵� ����
			int[] ����8 = new int[] { 830039 };		//���ε� ������ ž 8�� �̵� ����
			int[] ����9 = new int[] { 830040 };		//���ε� ������ ž 9�� �̵� ����
			int[] �� = new int[] { 1123 };			//����� ���� ���弭Ŀ
			int[] ���� = new int[] { 20271 };			//������ �������� �����
			int[] ���� = new int[] { 20272 };			//������ �������� ����
			int[] ���� = new int[] { 20029 };			//������ ����
			int[] ���� = new int[] { 222311};			//������ ����
			int[] �ɾ� = new int[] { 22214 };			//�þ��� �ɾ�
			int[] ��Ƽ = new int[] { 900019 };		//������ Ƽ����
			int[] ���� = new int[] { 121 };			//���� ������ ������
			int[] ���� = new int[] { 20314 };			//���̼�Ʈ ���̾�Ʈ�� ����
			int[] ���� = new int[] { 9 };				//�����Ϸ��� �ܰ�
			int[] ������ = new int[] { 222310 };		//������ �����
			int[] �Ϸ¹� = new int[] { 22363 };		//�Ϸ��� ����
			int[] ��� = new int[] { 40466 };			//���� ����
			int[] ���� = new int[] { 20074 };			//������ ����
			int[] ���� = new int[] { 210125 };		//������ ����(�������)
			int[] Ÿ��ź�� = new int[] { 210130 };		//������ ����(Ÿ��ź ��)
			int[] Ÿ��ź���� = new int[] { 210132 };	//������ ����(Ÿ��ź ����)
			int[] Ÿ��ź�� = new int[] { 210131 };	//������ ����(Ÿ��ź ��)
			int[] ���� = new int[] { 41149 };			//������ ����(�ҿ� ���� ������)
			int[] ���� = new int[] { 41153 };			//������ ����(��Ʈ����ũ ����)
			int[] ��� = new int[] { 40249 };			//������ ����(� ���ε�)
			int[] ���� = new int[] { 41152 };			//������ ����(����Ʈ ����)
			int[] ���Ͻ��� = new int[] { 20298 };		//���Ͻ��� ����
			int[] ���Ĺ� = new int[] { 22358 };		//������ ����
			int[] ���ְ� = new int[] { 22360 };		//���ְ��� ����
			int[] ī���ĸ� = new int[] { 20040 };		//ī������ ����
			int[] Ŀ���� = new int[] { 20150 };		//Ŀ���� ����
			int[] Ŀ�� = new int[] { 54 };			//Ŀ���� ��
			int[] Ŀ���� = new int[] { 20214 };		//Ŀ���� ����
			int[] Ŀ���� = new int[] { 20184 };		//Ŀ���� �尩
			int[] Ŀ���� = new int[] { 20041 };		//Ŀ���� ����
			int[] ũ�κ� = new int[] { 900007 };		//ũ�γ뽺�� ��Ʈ
			int[] Ÿ���� = new int[] { 20216 };		//Ÿ���� ����
			int[] Ÿ���� = new int[] { 20186 };		//Ÿ���� �尩
			int[] Ÿ�� = new int[] { 20320 };			//Ÿ��ź�� ��Ʈ
			int[] ���� = new int[] { 20077 };			//���� ����
			int[] ����� = new int[] { 222304 };		//������ �����
			int[] ���� = new int[] { 1122 };			//�ı��� ���
			int[] ���� = new int[] { 222306 };		//������ �����
			int[] ȥ�� = new int[] { 20048 };			//ȥ���� ����
			int[] ����κ� = new int[] { 20160 };		//������� �κ�
			int[] ������� = new int[] { 20218 };		//������� ����
			int[] �ƸӺ� = new int[] { 5559 };		//�������� ����(�Ƹ� �극��ũ)
			int[] ���� = new int[] { 131 };			//���� ������
			int[] temp = null;

			switch(targetItem) {
			case 31000:		// ����� ���� �Ŵ� ���� �ݺ� ����
				temp = �ݺ�����;	break;
			case 31001:		// ����� ���� �Ŵ� ���� ���� ����
				temp = ��������;	break;
			case 31002:		// ����� ���� ������ ü�μҵ�
				temp = ����ü�μҵ�;break;
			case 31003:		// ����� ���� ����� ����
				temp = ������;	break;
			case 31004:		// ����� ���� �����(ī���� �踮��)
				temp = ī��;		break;
			case 31005:	// ����� ���� ����Ʈ�ߵ��� ��հ�
				temp = ����;break;
			case 31006:	// ����� ���� ������ Ű��ũ
				temp = ��Ű;break;
			case 31007:	// ����� ���� ������ ������
				temp = ����;break;
			case 31008:	// ����� ���� ��������Ʈ�� ����
				temp = ��������;break;
			case 31009:	// ����� ���� ��������Ʈ�� ����
				temp = ��������;break;
			case 31010:	// ����� ���� ��������Ʈ�� �Ұ�
				temp = ����;break;
			case 31011:	// ����� ���� ��������Ʈ�� �尩
				temp = �����尩;break;
			case 31012:	// ����� ���� ��������Ʈ�� ����
				temp = ��������;break;
			case 31013:	// ����� ���� ����۾� ������ �����
				temp = �����;break;
			case 31014:	// ����� ���� ����۾� ������ ������ ����
				temp = �������;break;
			case 31015:	// ����� ���� ����۾� ������ ���� ����
				temp = �������;break;
			case 31016:	// ����� ���� �� �尩
				temp = ����;break;
			case 31017:	// ����� ���� ���̾��� ����
				temp = ���̾ƹ���;break;
			case 31018:	// ����� ���� �е��� �̵���
				temp = �е��̵���;break;
			case 31019:	// ����� ���� ��ġ �κ�
				temp = ��ġ�κ�;break;
			case 31020:	// ����� ���� ������ ������
				temp = ��������;break;
			case 31021:	// ����� ���� ���� ��� ����
				temp = ��������;break;
			case 31022:	// ����� ���� ������(����Ƽ�׷���Ʈ)
				temp = ��;break;
			case 31023:	// ����� ���� ������(��Ƽ�� ��Ʈ����ũ)
				temp = ��Ƽ��;break;
			case 31024:	// ����� ���� ������ ����
				temp = ���չ���;break;
			case 31025:	// ����� ���� �ӹ̷ε��� �հ�
				temp = �ӹ̿հ�;break;
			case 31026:	// ����� ���� �޸�Ű������ ����
				temp = �޸�����;break;
			case 31027:	// ����� ���� ��ø�� ����
				temp = ��ø����;break;
			case 31028:	// ����� ���� ������Ʈ�� ������
				temp = ����;break;
			case 31029:	// ����� ���� �ݿ����� ����
				temp = �ݿ���;break;
			case 31030:	// ����� ���� �����ڸ��� ����
				temp = ���͸���;break;
			case 31031:	// ����� ���� �����̾��� ����
				temp = ���;break;
			case 31032:	// ����� ���� ���ε� ������ ž 10�� �̵� ����
				temp = ����10;break;
			case 31033:	// ����� ���� ���ε� ������ ž 1�� �̵� ����
				temp = ����1;break;
			case 31034:	// ����� ���� ���ε� ������ ž 2�� �̵� ����
				temp = ����2;break;
			case 31035:	// ����� ���� ���ε� ������ ž 3�� �̵� ����
				temp = ����3;break;
			case 31036:	// ����� ���� ���ε� ������ ž 4�� �̵� ����
				temp = ����4;break;
			case 31037:	// ����� ���� ���ε� ������ ž 5�� �̵� ����
				temp = ����5;break;
			case 31038:	// ����� ���� ���ε� ������ ž 6�� �̵� ����
				temp = ����6;break;
			case 31039:	// ����� ���� ���ε� ������ ž 7�� �̵� ����
				temp = ����7;break;
			case 31040:	// ����� ���� ���ε� ������ ž 8�� �̵� ����
				temp = ����8;break;
			case 31041:	// ����� ���� ���ε� ������ ž 9�� �̵� ����
				temp = ����9;break;
			case 31042:	// ����� ���� ���弭Ŀ
				temp = ��;break;
			case 31043:	// ����� ���� ������ �������� �����
				temp = ����;break;
			case 31044:	// ����� ���� ������ �������� ����
				temp = ����;break;
			case 31045:	// ����� ���� ������ ����
				temp = ����;break;
			case 31046:	// ����� ���� ������ ����
				temp = ����;break;
			case 31047:	// ����� ���� �þ��� �ɾ�
				temp = �ɾ�;break;
			case 31048:	// ����� ���� ������ Ƽ����
				temp = ��Ƽ;break;
			case 31049:	// ����� ���� ���� ������ ������
				temp = ����;break;
			case 31050:	// ����� ���� ���̼�Ʈ ���̾�Ʈ�� ����
				temp = ����;break;
			case 31051:	// ����� ���� �����Ϸ��� �ܰ�
				temp = ����;break;
			case 31052:	// ����� ���� ������ �����
				temp = ������;break;
			case 31053:	// ����� ���� �Ϸ��� ����
				temp = �Ϸ¹�;break;
			case 31054:	// ����� ���� ���� ����
				temp = ���;break;
			case 31055:	// ����� ���� ������ ����
				temp = ����;break;
			case 31056:	// ����� ���� ������ ����(�������)
				temp = ����;break;
			case 31057:	// ����� ���� ������ ����(Ÿ��ź ��)
				temp = Ÿ��ź��;break;
			case 31058:	// ����� ���� ������ ����(Ÿ��ź ����)
				temp = Ÿ��ź����;break;
			case 31059:	// ����� ���� ������ ����(Ÿ��ź ��)
				temp = Ÿ��ź��;break;
			case 31060:	// ����� ���� ������ ����(�ҿ� ���� ������)
				temp = ����;break;
			case 31061:	// ����� ���� ������ ����(��Ʈ����ũ ����)
				temp = ����;break;
			case 31062:	// ����� ���� ������ ����(� ���ε�)
				temp = ���;break;
			case 31063:	// ����� ���� ������ ����(����Ʈ ����)
				temp = ����;break;
			case 31064:	// ����� ���� ���Ͻ��� ����
				temp = ���Ͻ���;break;
			case 31065:	// ����� ���� ������ ����
				temp = ���Ĺ�;break;
			case 31066:	// ����� ���� ���ְ��� ����
				temp = ���ְ�;break;
			case 31067:	// ����� ���� ī������ ����
				temp = ī���ĸ�;break;
			case 31068:	// ����� ���� Ŀ���� ����
				temp = Ŀ����;break;
			case 31069:	// ����� ���� Ŀ���� ��
				temp = Ŀ��;break;
			case 31070:	// ����� ���� Ŀ���� ����
				temp = Ŀ����;break;
			case 31071:	// ����� ���� Ŀ���� �尩
				temp = Ŀ����;break;
			case 31072:	// ����� ���� Ŀ���� ����
				temp = Ŀ����;break;
			case 31073:	// ����� ���� ũ�γ뽺�� ��Ʈ
				temp = ũ�κ�;break;
			case 31074:	// ����� ���� Ÿ���� ����
				temp = Ÿ����;break;
			case 31075:	// ����� ���� Ÿ���� �尩
				temp = Ÿ����;break;
			case 31076:	// ����� ���� Ÿ��ź�� ��Ʈ
				temp = Ÿ��;break;
			case 31077:	// ����� ���� ���� ����
				temp = ����;break;
			case 31078:	// ����� ���� ������ �����
				temp = �����;break;
			case 31079:	// ����� ���� �ı��� ���
				temp = ����;break;
			case 31080:	// ����� ���� ������ �����
				temp = ����;break;
			case 31081:	// ����� ���� ȥ���� ����
				temp = ȥ��;break;
			case 31082:	// ����� ���� ������� �κ�
				temp = ����κ�;break;
			case 31083:	// ����� ���� ������� ����
				temp = �������;break;
			case 31084:	// ����� ���� �������� ����(�Ƹ� �극��ũ)
				temp = �ƸӺ�;break;
			case 31085:	// ����� ���� ���� ������
				temp = ����;break;
			default:
				pc.sendPackets(new S_SystemMessage("\\aA�˸�: ����� ���� �����۸� �����մϴ�."));
				break;
			}
			if(temp != null) {
				boolean chance = false;
				for (int i = 0 ; i < item.length; i++){
					if (l1iteminstance.getItemId() == item[i]) {
						if(_random.nextInt(99) + 1 <= Config.�����ǳ�����) {
							chance = true;
							// ���� ó��.
							createNewItem2(pc, temp[i], 1, l1iteminstance1.getEnchantLevel());
							pc.sendPackets(new S_SystemMessage(""+l1iteminstance1.getName()+"��(��) �� ������ �ο� �Ǿ����ϴ�."));
							break;
						}
						if (pc.isGm()){
							pc.sendPackets(new S_SystemMessage("������Ȯ�� >> " + Config.�����ǳ�����));
						}
					}
				}
				// Ȯ�� ���������� �޼��� ó��.
				if(chance == false) {
					pc.sendPackets(new S_SystemMessage(""+l1iteminstance1.getName()+"��(��) ����� ������� ���ϰ� �Ҹ��Ͽ����ϴ�."));
				}
				// ��� ���� ó��.
				pc.getInventory().DeleteEnchant(l1iteminstance1.getItemId(), l1iteminstance1.getEnchantLevel());
				pc.getInventory().removeItem(l1iteminstance, 1);
			}
		}
		break;

		}
	}

	private static boolean createNewItem2(L1PcInstance pc, int item_id, int count, int EnchantLevel) {
		L1ItemInstance item = ItemTable.getInstance().createItem(item_id);
		if (item != null) {
			item.setCount(count);
			item.setEnchantLevel(EnchantLevel);
			item.setIdentified(true);
			if (pc.getInventory().checkAddItem(item, count) == L1Inventory.OK) {
				pc.getInventory().storeItem(item);
			} else {
				pc.sendPackets(new S_ServerMessage(82));
				// ���� �������� �����ϰų� �κ��丮�� ������ �� �� �� �����ϴ�.
				return false;
			}
			//pc.sendPackets(new S_ServerMessage(403, item.getLogName())); // %0�� �տ� �־����ϴ�.
			return true;
		} else {
			return false;
		}
	}


}