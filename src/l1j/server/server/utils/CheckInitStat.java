/** 
 * From. LinFreedom
 * ĳ���� ��������  �˻� 
 * 
 * 
 * 
 * */
package l1j.server.server.utils;

import l1j.server.server.model.Instance.L1PcInstance;

public class CheckInitStat {

	// KKK �ɸ����� �ּҽ����� �˻�
	private CheckInitStat() {
	}

	/**
	 * ĳ������ �ּҽ����� �˻�
	 * 
	 * @param pc
	 * @return true : ���� or ���, false : ������
	 */
	public static boolean CheckPcStat(L1PcInstance pc) {
		if (pc == null) { // ���� pc�� ���ٸ�
			return false;
		}
		if (pc.isGm()) { // pc�� ��ڶ��
			return true;
		}

		int str = pc.getAbility().getBaseStr();
		int dex = pc.getAbility().getBaseDex();
		int cha = pc.getAbility().getBaseCha();
		int con = pc.getAbility().getBaseCon();
		int intel = pc.getAbility().getBaseInt();
		int wis = pc.getAbility().getBaseWis();
		int basestr = 0;
		int basedex = 0;
		int basecon = 0;
		int baseint = 0;
		int basewis = 0;
		int basecha = 0;
		switch (pc.getType()) {
		case 0: // ����
			basestr = 13;
			basedex = 10;
			basecon = 10;
			basewis = 11;
			basecha = 13;
			baseint = 10;
			break;
		case 1: // ���
			basestr = 16;
			basedex = 12;
			basecon = 14;
			basewis = 9;
			basecha = 12;
			baseint = 8;
			break;
		case 2: // ����
			basestr = 11;
			basedex = 12;
			basecon = 12;
			basewis = 12;
			basecha = 9;
			baseint = 12;
			break;
		case 3: // ����
			basestr = 8;
			basedex = 7;
			basecon = 12;
			basewis = 12;
			basecha = 8;
			baseint = 12;
			break;
		case 4: // ��ũ����
			basestr = 12;
			basedex = 15;
			basecon = 8;
			basewis = 10;
			basecha = 9;
			baseint = 11;
			break;
		case 5: // ����
			basestr = 13;
			basedex = 11;
			basecon = 14;
			basewis = 12;
			basecha = 8;
			baseint = 11;
			break;
		case 6: // ȯ����
			basestr = 11;
			basedex = 10;
			basecon = 12;
			basewis = 12;
			basecha = 8;
			baseint = 12;
			break;
		}

		if (str < basestr || dex < basedex || con < basecon || cha < basecha
				|| intel < baseint || wis < basewis) { // �ʱ⽺�Ⱥ��� �۴ٸ�
			return false;
		}
		return true;
	}

}
