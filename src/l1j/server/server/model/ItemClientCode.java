package l1j.server.server.model;

import l1j.server.server.datatables.ItemTable;

public class ItemClientCode {

	public static int code(int itemid) {
		int id = 0x0539;
		switch (itemid) {
		case 60134: // �����ڿ��
		case 40030: // �����ڼӵ�
		case 60135: // �����ڿ���
		case 40096: // �����ں���
		case 240016: // ����������
		case 40018: // ����
			id = 0;
			break;

		case 40747: // ���̽���ȭ��
			id = 1484;
			break;

		case 7322: // �巡���� ���ݼ� ���
			id = 15211;
			break;
		case 40318: // ����
			id = 0x00A6;
			break;
		case 60138:// ���ž����
			id = 0x3686;
			break;
		case 430007:
		case 60140:// ������ ������
			id = 0x0E15;
			break;
		case 40319:
		case 60137:// ���ɿ�
			id = 0x0239;
			break;// 569 1822 1253
		case 40320:// �渶��
			id = 0x0344;
			break;// 836 2474
		case 40321:
		case 60139:// ��伮
			id = 0x0345;
			break;
		case 430008:
		case 60157:// �Ӽ���
			id = 0x0E16;
			break;
		case 430006:
		case 60136:// ���׵��
			id = 0x0E5A;
			break;
		case 40008:
			id = 0x0014;
			break;
		case 60224:
		case 60232: // �ǵ��� ����
			id = 0x315C;
			break;
		case 21125:
		case 21126:
		case 21127:
		case 21128:
		case 21129:
		case 21130:
		case 21131:
		case 21132:
		case 21133:
		case 21134:
		case 21135:
		case 21136: // �ǵ��� ���Ƽ
			id = 0x315F;
			break;
		case 60247:// õ���� �ǵ��� ���Ƽ
			id = 0x3183;
			break;
		case 430040:
		case 5000500:
		case 5000550:
		case 530040:// �ű��ѹ���,��Ƽ�� ��ȭ�ֹ���
			id = 0x2EC1;
			break;
		/*
		 * case 40280://���ε� ������ ž 11�� �̵� ���� ~ ���ε� ������ ž 91�� �̵� ���� case 40281:
		 * case 40283: case 40284: case 40285: case 40286: case 40287: case
		 * 40288: case 5000100://���̵ȿ�����ž1���̵����� ~ ȥ���ǿ�����ž91���̵����� case 5000101:
		 * case 5000102: case 5000103: case 5000104: show me the money show me
		 * the money case 5000105: case 5000106: case 5000107: case 5000108:
		 * case 5000119: case 60201: id = 945; break;
		 */
		case 40100: // �����̵� �ֹ���
			id = 0x0017;
			break;
		case 60359: // ������ ���� �ֹ���
			id = 0x36DE;
			break;
		case 40308: // �Ƶ���
			id = 0x0007;
			break;
		case 40524: // ���� ����
			id = 927;
			break;
		case 41246: // ����ü
			id = 2257;
			break;
		case 60123: // �������ϱ���
			id = 11312;
			break;
		case 20265: // ��ȭ�� ����
			id = 1244;
			break;
		case 20297: // ���ŷ��� ����
			id = 1245;
			break;
		case 20301: // ü���� ����
			id = 1246;
			break;
		case 40341:// ��Ÿ���� ���~ ���������� �̻�
		case 40342:
		case 40343:
		case 40344:
		case 40345:
		case 40346:
		case 40347:
		case 40348:
		case 40349:
		case 40350:
		case 40351:
		case 40352:
		case 40353:
		case 40354:
		case 40355:
		case 40356:
		case 40357:
		case 40358:
		case 40359:
		case 40360:
		case 40361:
		case 40362:
		case 40363:
		case 40364:
		case 40365:
		case 40367:
		case 40368:
		case 40369:
		case 40370:
		case 40371:
		case 40372:// �� ����
			id = itemid - 39329;
			break;
		case 60187:
			id = 1017;
			break;
		case 60188:
			id = 1033;
			break;
		case 60189:
			id = 1041;
			break;
		case 60186:
			id = 1137;
			break;
		case 40981: // �ù����� ���� : ����
			id = 2168;
			break;
		case 40979: // �ù����� ���� : ��
			id = 2169;
			break;
		case 40980: // �ù����� ���� : ��
			id = 2170;
			break;
		case 40978: // �ù����� ���� : ��
			id = 2171;
			break;
		case 40986: // �ù����� �մ�
			id = 2174;
			break;
		case 40983: // �ù����� ���� : ��
			id = 2175;
			break;
		case 40984: // �ù����� ���� : ��
			id = 2175;
			break;
		case 40982: // �ù����� ���� : ��
			id = 2175;
			break;
		case 40976: // ��
			id = 2179;
			break;
		case 40977: // �ǹ��� ��
			id = 2180;
			break;
		case 40973: // �Ļ����� ����
			id = 2181;
			break;
		case 40972: // �Ļ����� ����
			id = 2182;
			break;
		case 40970: // �Ӱ����� ����
			id = 2183;
			break;
		case 40971: // �Ӱ����� �̻�
			id = 2184;
			break;
		case 40974: // ����� ��
			id = 2186;
			break;
		case 40975: // ����� ��������
			id = 2187;
			break;
		case 40779: // ��ö��
			id = 380;
			break;
		case 40526: // ���� �Ǳ�
			id = 1271;
			break;
		case 40918: // ���� ������
			id = 2401;
			break;
		case 40919: // ���� ������
			id = 2402;
			break;
		case 40920: // ������ ������
			id = 2403;
			break;
		case 40917: // ���� ������
			id = 2404;
			break;
		case 40475: // Ÿ���� �Ӹ�ī��
			id = 1378;
			break;
		case 40473: // Ÿ���� ��
			id = 1383;
			break;
		case 40477: // Ÿ���� �Ǹ��� 1��
			id = 1385;
			break;
		case 40478: // Ÿ���� �Ǹ��� 2��
			id = 1387;
			break;
		case 40479: // Ÿ���� �Ǹ��� 3��
			id = 1389;
			break;
		case 40480: // Ÿ���� �Ǹ��� 4��
			id = 1391;
			break;
		case 40471: // ������ ����
			id = 1485;
			break;
		case 40482: // Ÿ���� ��ħ
			id = 1386;
			break;
		case 40474: // Ÿ���� ��
			id = 1388;
			break;
		case 40481: // Ÿ���� �̻�
			id = 1390;
			break;
		case 40476: // Ÿ���� �ձ�
			id = 1392;
			break;
		case 40429: // ��Ÿ�ٵ� �ֹ��� ����
			id = 1512;
			break;
		case 40678: // ��ȥ�� ����
			id = 1747;
			break;
		case 40438: // ���� �۰���
			id = 1500;
			break;
		case 40424: // ���� ����
			id = 1501;
			break;
		case 40437: // ���ö�� �ٱ�
			id = 1502;
			break;
		case 40427: // ��ũ���� �ָӴ�
			id = 1504;
			break;
		case 40451: // �� Ƽ���� ����
			id = 1503;
			break;
		case 40459: // �����ǿ� ����
			id = 1505;
			break;
		case 40432: // ��Ƶ� �ֹ��� ����
			id = 1519;
			break;
		case 62: // ������ ��հ�
			id = 1755;
			break;
		case 49: // ������ ���
			id = 1756;
			break;
		case 132: // �Ű��� ������
			id = 1763;
			break;
		case 84: // ��յ�
			id = 854;
			break;
		case 40969: // ��ũ���� ��ȥ�� ����ü
			id = 2071;
			break;
		case 40967: // ������ ����
			id = 2085;
			break;
		case 40964: // �渶�� ����
			id = 2362;
			break;
		case 40965: // ��Ÿ�ٵ� �������� �����
			id = 2380;
			break;
		case 20168: // ������ �尩
			id = 1760;
			break;
		case 40966: // ����Ȳ�� �� �����
			id = 2388;
			break;
		case 40968: // �������� ����
			id = 2087;
			break;
		case 20113: // ������ ����
			id = 1757;
			break;
		case 40052: // �ְ�� ���̾�
			id = 170;
			break;
		case 20201: // ������ ����
			id = 1761;
			break;
		case 20020: // ������ ����
			id = 1758;
			break;
		case 40652: // ��Ÿ�� ����
			id = 1789;
			break;
		case 40675:// ����� ����
			id = 1792;
			break;
		case 41019:// ��Ÿ�ٵ��� ���缭 1�� ~ 8��
		case 41020:
		case 41021:
		case 41022:
		case 41023:
		case 41024:
		case 41025:
		case 41026:
			id = itemid - 38648;
			break;
		// id == 3524 Ż������ ���� ��Ḯ��Ʈ
		// id == 3590 ������ ����
		case 410002: // ������ ��հ�
			id = 3219;
			break;
		case 40055: // �ְ�� ���޶���
			id = 173;
			break;
		case 40466: // ���� ����
			id = 733;
			break;
		case 162: // ��ũ
			id = 852;
			break;
		case 81: // ����
			id = 851;
			break;
		case 177: // ��Ȱ
			id = 853;
			break;
		case 40413: // ���������� ����
			id = 1153;
			break;
		case 40053: // �ְ�� ���
			id = 176;
			break;
		case 40054: // �ְ�� �����̾�
			id = 179;
			break;
		case 40395: // ���� ���
			id = 407;
			break;
		case 40041: // �ξ��� ���
			id = 418;
			break;
		case 40046:// �����̾�
			id = 177;
			break;
		case 40045: // ���
			id = 174;
			break;
		case 40044: // ���̾Ƹ��
			id = 168;
			break;
		case 40047: // ���޶���
			id = 171;
			break;
		case 40304: // ����������
		case 40305: // �ľ�����
		case 40306: // ��������
		case 40307: // �������� ����
			id = itemid - 39149;
			break;
		case 52: // ��հ�
			id = 6;
			break;
		case 149027: // �����Ǳ��
			id = 11713;
			break;
		case 40503: // �ƶ�ũ���ǰŹ���
			id = 254;
			break;
		case 40505: // ��Ʈ�ǲ���
			id = 255;
			break;
		case 40495: // �̽��� ��
			id = 257;
			break;
		case 40504: // �ƶ�ũ�� �㹰
			id = 259;
			break;
		case 40521: // ���� ����
			id = 261;
			break;
		case 20140: // ������ ���װ���
			id = 655;
			break;
		case 40408: // ö��
			id = 333;
			break;
		case 40899: // ��ö ����
			id = 382;
			break;
		// id = 654 ������ �Ǳ� ����
		case 40468: // ����������
			id = 1140;
			break;
		// id = 3202 Ư���� ���� (��Ű���:
		// id = 3286 �Ƿ����� ��õ�� (��Ű���:
		case 6023:// ���� ����
			id = 12533;
			break;
		case 6008: // ���ε� ������ ü�μҵ�
			id = 12534;
			break;
		case 6009: // ���ε� ������ Ű��ũ
			id = 12535;
			break;
		case 410005: // ȯ������ ������
			id = 3216;
			break;
		case 40442: // ����� ����
			id = 1203;
			break;
		case 40486: // ȭ����
			id = 1204;
			break;
		case 40490: // �����ɼ�
			id = 1205;
			break;
		case 40497: // �̽��� �Ǳ�
			id = 264;
			break;
		case 40509: // �����Ϸ��� �Ǳ�
			id = 265;
			break;
		case 40487: // Ȳ�� �Ǳ�
			id = 1135;
			break;
		case 40439: // ��� �Ǳ�
			id = 1138;
			break;
		case 40469: // �� �Ǳ�
			id = 1141;
			break;
		case 40489: // Ȳ�ݿ�������
			id = 1134;
			break;
		case 40441: // ��ݿ�������
			id = 1137;
			break;
		case 40467: // ����
			id = 1139;
			break;
		case 40440: // ��ݱ�
			id = 1136;
			break;
		case 40488: // Ȳ�ݱ�
			id = 1133;
			break;
		case 500022: // ��ο� �ϵ��� �ϱ���
			id = 10028;
			break;
		case 40515: // ������ ��
			id = 247;
			break;
		case 40048:// ��޴��̾�
			id = 169;
			break;
		case 40051:// ��޿���
			id = 172;
			break;
		case 100: // �����Ϸ��� ���� ��
			id = 277;
			break;
		case 89: // �̽��� ���� ��
			id = 276;
			break;
		case 3: // �ܰ˽�
			id = 270;
			break;
		case 40050: // ��� �����̾�
			id = 178;
			break;
		case 40520: // �� ����Ʈ
			id = 253;
			break;
		case 49077: // ������ ��ȥ
			id = 3010;
			break;
		case 49078: // �巹��ũ�� ��ȥ
			id = 3011;
			break;
		case 49079: // ����� ��ȥ
			id = 3012;
			break;
		case 49080: // ���̸����� ��ȥ
			id = 3013;
			break;
		case 49081: // �׷���Ʈ �̳�Ÿ�츣���� ��ȥ
			id = 3014;
			break;
		case 20158: // ȥ���� ����
			id = 1707;
			break;
		// case 123123 id = 1742; break; �����ǰѿ�
		case 40672: // ������ ����
			id = 1706;
			break;
		case 40671: // ������ ����
			id = 1705;
			break;
		case 40674: // ������ ����
			id = 1703;
			break;
		case 40670: // ������ ����
			id = 1704;
			break;
		case 40673: // ������ ����
			id = 1701;
			break;
		case 40718: // ���� ����
			id = 1825;
			break;
		case 40995: // �߷��Ǽ���
			id = 2153;
			break;
		case 40991: // �߷��� ��հ�
			id = 2158;
			break;
		case 196:// �ٸ��� 1�ܰ�
			id = 2159;
			break;
		case 197:// �ٸ��� 2�ܰ�
			id = 2160;
			break;
		case 198:// �ٸ��� 3�ܰ�
			id = 2161;
			break;
		case 199:// �ٸ��� 4�ܰ�
			id = 2162;
			break;
		case 200:// �ٸ��� 5�ܰ�
			id = 2163;
			break;
		case 201:// �ٸ��� 6�ܰ�
			id = 2163;
			break;
		case 202:// �ٸ��� 7�ܰ�
			id = 2164;
			break;
		case 203: // �ٸ��� 8�ܰ�
			id = 2165;
			break;
		case 40997: // �߷��� �̻�
			id = 2157;
			break;
		case 40990: // �߷��� ����
			id = 2152;
			break;
		case 40993: // �߷��� ��
			id = 2167;
			break;
		case 40998: // �߷��� ����
			id = 2156;
			break;
		case 40996: // �߷��� ����
			id = 2155;
			break;
		case 40992: // �߷��� ����
			id = 2151;
			break;
		case 20129: // �Ű��� �κ�
			id = 1764;
			break;
		case 40493: // ������ �÷�
			id = 262;
			break;
		case 40494: // �̽���
			id = 252;
			break;
		case 40496: // �̽��� ����
			id = 246;
			break;
		case 20: // �����Ϸ��� �˽�
			id = 272;
			break;
		case 40049: // ��� ���
			id = 175;
			break;
		case 20006: // ����� �鰩
			id = 140;
			break;
		case 20154: // �Ǳ� ����
			id = 91;
			break;
		case 20182: // �尩
			id = 129;
			break;
		case 20205: // ����
			id = 128;
			break;
		case 20231: // �簢 ����
			id = 155;
			break;

		case 6010: // ������ Ƽ�ƶ� ����
			id = 12568;
			break;
		case 6011: // ������ �巹�� �ʰ�
			id = 12567;
			break;
		case 6012: // ������ ���� ����
			id = 12566;
			break;
		case 40397: // Ű�޶��� ���� (��)
			id = 728;
			break;
		case 40398: // Ű�޶��� ���� (���)
			id = 729;
			break;
		case 40399: // Ű�޶��� ���� (����)
			id = 730;
			break;
		case 40400: // Ű�޶��� ���� (��)
			id = 731;
			break;
		case 40091: // �� �ֹ��� (���� 2)
			id = 620;
			break;
		case 40092: // �� �ֹ��� (���� 3)
			id = 621;
			break;
		case 40093: // �� �ֹ��� (���� 4)
			id = 622;
			break;
		case 40094: // �� �ֹ��� (���� 5)
			id = 623;
			break;
		case 111:// ������ ���� ���� ������������
			id = 988;
			break;
		case 40455: // �Ķ� �ʰ�
			id = 376;
			break;
		case 40456: // ���� �ʰ�
			id = 378;
			break;
		case 40457: // �Ͼ� �ʰ�
			id = 379;
			break;
		case 40460: // �ƽ�Ÿ������ ��
			id = 429;
			break;
		case 40396: // ���� ���
			id = 408;
			break;
		case 40394: // ǳ�� ���
			id = 410;
			break;
		case 40393: // ȭ�� ���
			id = 409;
			break;
		case 40162: // ���Ǽ���
			id = 1151;
			break;
		case 40169: // �巹��ũ�Ǽ���
			id = 1154;
			break;
		case 40409: // �һ��Ǽ���
			id = 1152;
			break;
		case 181: // ���
			id = 81;
			break;
		case 40491: // �׸����� ����
			id = 618;
			break;
		case 40498: // �ٶ��� ����
			id = 619;
			break;
		case 20165: // ������ �尩
			id = 552;
			break;
		case 20197: // ������ ����
			id = 554;
			break;
		case 40406: // �������
			id = 335;
			break;
		case 20306: // ���� ��ü�� ��Ʈ
			id = 502;
			break;
		case 20308: // ���� ������ ��Ʈ
			id = 503;
			break;
		case 20307: // ���� ��ȥ�� ��Ʈ
			id = 504;
			break;
		case 427102: // ������ �̻�
			id = 1067;
			break;
		case 427108: // ��ö�� �̻�
			id = 1071;
			break;
		case 427103: // Ȳ���� �̻�
			id = 1070;
			break;
		case 427104: // ��ɰ��� �̻�
			id = 1064;
			break;
		case 40405: // ��������
			id = 332;
			break;
		case 40407: // ������
			id = 336;
			break;
		case 427002:// ���� ��Ƹ�
			id = 1058;
			break;
		case 427004:// ��ö ��Ƹ�
			id = 1062;
			break;
		case 427006:// ũ�ν� ��Ƹ�
			id = 1057;
			break;
		case 410000:// ��ü
			id = 3223;
			break;
		case 410003:// ��Ű
			id = 3217;
			break;
		case 410004:// ��Ű
			id = 3218;
			break;
		case 41:// �Ϻ���
			return 2;
		case 5:// ���
			return 39;
		case 104:// ��í��
			return 44;
		case 99:// ������â
			return 70;
		case 148:// �뵵
			return 138;
		case 32:// �׶��콺
			return 141;
		case 145:// ����
			return 144;
		case 180:// ũ��
			return 269;
		case 42:// ����
			return 274;
		case 64:// ���
			return 337;
		case 40519:// ���ǰ�����
			return 245;
		case 40502:// ��
			return 251;
		case 430106:// ���渶��
			return 4012;
		case 430104:// ���渶��
			return 4013;
		case 430105:// ǳ�渶��
			return 4015;
		case 430107:// ȭ�渶��
			return 4014;
			
	
			
	
		case 3000065:// ������ǥ��
			return 106671;
		case 3000064:// ������ǥ��
			return 106672;
		case 5000066:// ������ǥ��
			return 10665;
		case 5000065:// ǳ����ǥ��
			return 10666;
		case 5000063:// ȭ����ǥ��
			return 10667;
		case 41159:// �Ƚ��ǳ�������
			return 0x0A6F;
		case 60327: // ����̳�
			return 0x3883;
		case 60334: // �� ���� ���ô�
			return 0x387F;
		case 60326: // ��ź�� ���ô�
			return 0x387E;
		case 41245: // ������
			return 1906;
		case 21124: // �ܴ���ȣ�ڰ���
			return 14024;
		case 427306: // ȣ���尩
			return 14025;
		case 256: // ȣ�ڰ�
			return 14026;
		case 4500027: // ȣ�ھ�հ�
			return 14027;
		case 263: // ������ȣ��������
			return 14028;
		case 4500026: // ȣ�� ����
			return 14029;
		case 265: // ȣ�� ü�μҵ�
			return 14030;
		case 264: // ȣ�� Ű��ũ
			return 14031;
		case 60423: // ���� ȣ�ڼ�
			return 14820;
		case 60427: // ���� ���(��)
			return 12916;
		case 60428: // ���� ���(��)
			return 12917;
		case 60429: // ���� ���(��)
			return 12918;
		case 60430: // ���� ���(��)
			return 12919;
		case 60431: // ���� ���(��)
			return 12920;
		case 60432: // ���� ���(��)
			return 12921;
		case 60433: // ���� ���(��)
			return 12922;
		case 60434: // ���� ���(��)
			return 12923;
		case 60435: // ���� ���(��)
			return 12924;
		case 60436: // ���� ���(��)
			return 12925;
		case 60437: // ���� ���(��)
			return 12926;
		case 60438: // ���� ���(��)
			return 12927;
		case 60473: // ȯ���� ���⸶�� �ֹ���
			return 14932;
		case 284: // ��������
			return 14925;
		case 285:
			return 14926;
		case 286:
			return 14927;
		case 287:
			return 14928;
		case 288:
			return 14929;
		case 289:
			return 14930;
		case 290:
			return 14931;
		default:
			id = ItemTable.getInstance().getTemplate(itemid).getItemDescId();
			id = id > 0 ? id : 0x0539;
			break;
		}
		return id;
	}

}
