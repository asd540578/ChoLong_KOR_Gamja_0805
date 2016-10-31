package l1j.server.server.model;

import l1j.server.server.datatables.ItemTable;

public class ItemClientCode {

	public static int code(int itemid) {
		int id = 0x0539;
		switch (itemid) {
		case 60134: // 수련자용기
		case 40030: // 수련자속도
		case 60135: // 수련자와퍼
		case 40096: // 수련자변신
		case 240016: // 수련자지혜
		case 40018: // 강촐
			id = 0;
			break;

		case 40747: // 블랙미스릴화살
			id = 1484;
			break;

		case 7322: // 드래곤의 연금술 용액
			id = 15211;
			break;
		case 40318: // 마돌
			id = 0x00A6;
			break;
		case 60138:// 상아탑마돌
			id = 0x3686;
			break;
		case 430007:
		case 60140:// 각인의 뼈조각
			id = 0x0E15;
			break;
		case 40319:
		case 60137:// 정령옥
			id = 0x0239;
			break;// 569 1822 1253
		case 40320:// 흑마석
			id = 0x0344;
			break;// 836 2474
		case 40321:
		case 60139:// 흑요석
			id = 0x0345;
			break;
		case 430008:
		case 60157:// 속성석
			id = 0x0E16;
			break;
		case 430006:
		case 60136:// 유그드라
			id = 0x0E5A;
			break;
		case 40008:
			id = 0x0014;
			break;
		case 60224:
		case 60232: // 판도라 문양
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
		case 21136: // 판도라 향수티
			id = 0x315F;
			break;
		case 60247:// 천연비누 판도라 향기티
			id = 0x3183;
			break;
		case 430040:
		case 5000500:
		case 5000550:
		case 530040:// 신기한반지,룸티스 강화주문서
			id = 0x2EC1;
			break;
		/*
		 * case 40280://봉인된 오만의 탑 11층 이동 부적 ~ 봉인된 오만의 탑 91층 이동 부적 case 40281:
		 * case 40283: case 40284: case 40285: case 40286: case 40287: case
		 * 40288: case 5000100://변이된오만의탑1층이동부적 ~ 혼돈의오만의탑91층이동부적 case 5000101:
		 * case 5000102: case 5000103: case 5000104: show me the money show me
		 * the money case 5000105: case 5000106: case 5000107: case 5000108:
		 * case 5000119: case 60201: id = 945; break;
		 */
		case 40100: // 순간이동 주문서
			id = 0x0017;
			break;
		case 60359: // 조우의 변신 주문서
			id = 0x36DE;
			break;
		case 40308: // 아데나
			id = 0x0007;
			break;
		case 40524: // 검은 혈흔
			id = 927;
			break;
		case 41246: // 결정체
			id = 2257;
			break;
		case 60123: // 오림의일기장
			id = 11312;
			break;
		case 20265: // 조화의 인장
			id = 1244;
			break;
		case 20297: // 정신력의 인장
			id = 1245;
			break;
		case 20301: // 체력의 인장
			id = 1246;
			break;
		case 40341:// 안타라스의 비늘~ 린드비오르의 이빨
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
		case 40372:// 용 재료들
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
		case 40981: // 시버인의 꼬리 : 공기
			id = 2168;
			break;
		case 40979: // 시버인의 꼬리 : 물
			id = 2169;
			break;
		case 40980: // 시버인의 꼬리 : 불
			id = 2170;
			break;
		case 40978: // 시버인의 꼬리 : 흙
			id = 2171;
			break;
		case 40986: // 시버인의 앞니
			id = 2174;
			break;
		case 40983: // 시버인의 가죽 : 물
			id = 2175;
			break;
		case 40984: // 시버인의 가죽 : 불
			id = 2175;
			break;
		case 40982: // 시버인의 가죽 : 흙
			id = 2175;
			break;
		case 40976: // 모래
			id = 2179;
			break;
		case 40977: // 피묻은 모래
			id = 2180;
			break;
		case 40973: // 파사즈의 날개
			id = 2181;
			break;
		case 40972: // 파사즈의 숨결
			id = 2182;
			break;
		case 40970: // 앙가스의 꼬리
			id = 2183;
			break;
		case 40971: // 앙가스의 이빨
			id = 2184;
			break;
		case 40974: // 디고의 피
			id = 2186;
			break;
		case 40975: // 디고의 지느러미
			id = 2187;
			break;
		case 40779: // 강철괴
			id = 380;
			break;
		case 40526: // 얇은 판금
			id = 1271;
			break;
		case 40918: // 물의 지배자
			id = 2401;
			break;
		case 40919: // 불의 지배자
			id = 2402;
			break;
		case 40920: // 공기의 지배자
			id = 2403;
			break;
		case 40917: // 흙의 지배자
			id = 2404;
			break;
		case 40475: // 타락의 머리카락
			id = 1378;
			break;
		case 40473: // 타락의 낫
			id = 1383;
			break;
		case 40477: // 타락의 악마서 1권
			id = 1385;
			break;
		case 40478: // 타락의 악마서 2권
			id = 1387;
			break;
		case 40479: // 타락의 악마서 3권
			id = 1389;
			break;
		case 40480: // 타락의 악마서 4권
			id = 1391;
			break;
		case 40471: // 정령의 파편
			id = 1485;
			break;
		case 40482: // 타락의 외침
			id = 1386;
			break;
		case 40474: // 타락의 독
			id = 1388;
			break;
		case 40481: // 타락의 이빨
			id = 1390;
			break;
		case 40476: // 타락의 손길
			id = 1392;
			break;
		case 40429: // 라스타바드 주문서 조각
			id = 1512;
			break;
		case 40678: // 영혼석 파편
			id = 1747;
			break;
		case 40438: // 박쥐 송곳니
			id = 1500;
			break;
		case 40424: // 늑대 가죽
			id = 1501;
			break;
		case 40437: // 딥플라워 줄기
			id = 1502;
			break;
		case 40427: // 다크엘프 주머니
			id = 1504;
			break;
		case 40451: // 블랙 티거의 심장
			id = 1503;
			break;
		case 40459: // 스콜피온 껍질
			id = 1505;
			break;
		case 40432: // 디아드 주문서 조각
			id = 1519;
			break;
		case 62: // 무관의 양손검
			id = 1755;
			break;
		case 49: // 무관의 장검
			id = 1756;
			break;
		case 132: // 신관의 지팡이
			id = 1763;
			break;
		case 84: // 흑왕도
			id = 854;
			break;
		case 40969: // 다크엘프 영혼의 결정체
			id = 2071;
			break;
		case 40967: // 성지의 유물
			id = 2085;
			break;
		case 40964: // 흑마법 가루
			id = 2362;
			break;
		case 40965: // 라스타바드 무기제작 비법서
			id = 2380;
			break;
		case 20168: // 무관의 장갑
			id = 1760;
			break;
		case 40966: // 진명황의 방어구 비법서
			id = 2388;
			break;
		case 40968: // 수행자의 경전
			id = 2087;
			break;
		case 20113: // 무관의 갑옷
			id = 1757;
			break;
		case 40052: // 최고급 다이아
			id = 170;
			break;
		case 20201: // 무관의 부츠
			id = 1761;
			break;
		case 20020: // 무관의 투구
			id = 1758;
			break;
		case 40652: // 불타는 가죽
			id = 1789;
			break;
		case 40675:// 어둠의 광석
			id = 1792;
			break;
		case 41019:// 라스타바드의 역사서 1장 ~ 8장
		case 41020:
		case 41021:
		case 41022:
		case 41023:
		case 41024:
		case 41025:
		case 41026:
			id = itemid - 38648;
			break;
		// id == 3524 탈리온의 무기 재료리스트
		// id == 3590 빛나는 은괴
		case 410002: // 용기사의 양손검
			id = 3219;
			break;
		case 40055: // 최고급 에메랄드
			id = 173;
			break;
		case 40466: // 용의 심장
			id = 733;
			break;
		case 162: // 흑크
			id = 852;
			break;
		case 81: // 흑이
			id = 851;
			break;
		case 177: // 흑활
			id = 853;
			break;
		case 40413: // 얼음여왕의 숨결
			id = 1153;
			break;
		case 40053: // 최고급 루비
			id = 176;
			break;
		case 40054: // 최고급 사파이어
			id = 179;
			break;
		case 40395: // 수룡 비늘
			id = 407;
			break;
		case 40041: // 인어의 비늘
			id = 418;
			break;
		case 40046:// 사파이어
			id = 177;
			break;
		case 40045: // 루비
			id = 174;
			break;
		case 40044: // 다이아몬드
			id = 168;
			break;
		case 40047: // 에메랄드
			id = 171;
			break;
		case 40304: // 마프르유산
		case 40305: // 파아유산
		case 40306: // 에바유산
		case 40307: // 사이하의 유산
			id = itemid - 39149;
			break;
		case 52: // 양손검
			id = 6;
			break;
		case 149027: // 마물의기운
			id = 11713;
			break;
		case 40503: // 아라크네의거미줄
			id = 254;
			break;
		case 40505: // 엔트의껍질
			id = 255;
			break;
		case 40495: // 미스릴 실
			id = 257;
			break;
		case 40504: // 아라크네 허물
			id = 259;
			break;
		case 40521: // 페어리의 날개
			id = 261;
			break;
		case 20140: // 잊혀진 가죽갑옷
			id = 655;
			break;
		case 40408: // 철괴
			id = 333;
			break;
		case 40899: // 강철 원석
			id = 382;
			break;
		// id = 654 잊혀진 판금 갑옷
		case 40468: // 은원석조각
			id = 1140;
			break;
		// id = 3202 특별한 원석 (사키재료:
		// id = 3286 실레인의 추천서 (사키재료:
		case 6023:// 눈의 결정
			id = 12533;
			break;
		case 6008: // 봉인된 극한의 체인소드
			id = 12534;
			break;
		case 6009: // 봉인된 냉한의 키링크
			id = 12535;
			break;
		case 410005: // 환술사의 전투봉
			id = 3216;
			break;
		case 40442: // 브롭의 위액
			id = 1203;
			break;
		case 40486: // 화산재
			id = 1204;
			break;
		case 40490: // 흑정령석
			id = 1205;
			break;
		case 40497: // 미스릴 판금
			id = 264;
			break;
		case 40509: // 오리하루콘 판금
			id = 265;
			break;
		case 40487: // 황금 판금
			id = 1135;
			break;
		case 40439: // 백금 판금
			id = 1138;
			break;
		case 40469: // 은 판금
			id = 1141;
			break;
		case 40489: // 황금원석조각
			id = 1134;
			break;
		case 40441: // 백금원석조각
			id = 1137;
			break;
		case 40467: // 은괴
			id = 1139;
			break;
		case 40440: // 백금괴
			id = 1136;
			break;
		case 40488: // 황금괴
			id = 1133;
			break;
		case 500022: // 어두운 하딘의 일기장
			id = 10028;
			break;
		case 40515: // 정령의 돌
			id = 247;
			break;
		case 40048:// 고급다이아
			id = 169;
			break;
		case 40051:// 고급에메
			id = 172;
			break;
		case 100: // 오리하루콘 도금 뿔
			id = 277;
			break;
		case 89: // 미스릴 도금 뿔
			id = 276;
			break;
		case 3: // 단검신
			id = 270;
			break;
		case 40050: // 고급 사파이어
			id = 178;
			break;
		case 40520: // 페어리 더스트
			id = 253;
			break;
		case 49077: // 데몬의 영혼
			id = 3010;
			break;
		case 49078: // 드레이크의 영혼
			id = 3011;
			break;
		case 49079: // 쿠거의 영혼
			id = 3012;
			break;
		case 49080: // 아이리스의 영혼
			id = 3013;
			break;
		case 49081: // 그레이트 미노타우르스의 영혼
			id = 3014;
			break;
		case 20158: // 혼돈의 문장
			id = 1707;
			break;
		// case 123123 id = 1742; break; 야히의겉옷
		case 40672: // 야히의 의지
			id = 1706;
			break;
		case 40671: // 야히의 날개
			id = 1705;
			break;
		case 40674: // 야히의 손톱
			id = 1703;
			break;
		case 40670: // 야히의 꼬리
			id = 1704;
			break;
		case 40673: // 야히의 가면
			id = 1701;
			break;
		case 40718: // 혈석 파편
			id = 1825;
			break;
		case 40995: // 발록의손톱
			id = 2153;
			break;
		case 40991: // 발록의 양손검
			id = 2158;
			break;
		case 196:// 앨리스 1단계
			id = 2159;
			break;
		case 197:// 앨리스 2단계
			id = 2160;
			break;
		case 198:// 앨리스 3단계
			id = 2161;
			break;
		case 199:// 앨리스 4단계
			id = 2162;
			break;
		case 200:// 앨리스 5단계
			id = 2163;
			break;
		case 201:// 앨리스 6단계
			id = 2163;
			break;
		case 202:// 앨리스 7단계
			id = 2164;
			break;
		case 203: // 앨리스 8단계
			id = 2165;
			break;
		case 40997: // 발록의 이빨
			id = 2157;
			break;
		case 40990: // 발록의 날개
			id = 2152;
			break;
		case 40993: // 발록의 뿔
			id = 2167;
			break;
		case 40998: // 발록의 의지
			id = 2156;
			break;
		case 40996: // 발록의 전율
			id = 2155;
			break;
		case 40992: // 발록의 가면
			id = 2151;
			break;
		case 20129: // 신관의 로브
			id = 1764;
			break;
		case 40493: // 마법의 플룻
			id = 262;
			break;
		case 40494: // 미스릴
			id = 252;
			break;
		case 40496: // 미스릴 원석
			id = 246;
			break;
		case 20: // 오리하루콘 검신
			id = 272;
			break;
		case 40049: // 고급 루비
			id = 175;
			break;
		case 20006: // 기사의 면갑
			id = 140;
			break;
		case 20154: // 판금 갑옷
			id = 91;
			break;
		case 20182: // 장갑
			id = 129;
			break;
		case 20205: // 부츠
			id = 128;
			break;
		case 20231: // 사각 방패
			id = 155;
			break;

		case 6010: // 극한의 티아라 원석
			id = 12568;
			break;
		case 6011: // 극한의 드레스 옷감
			id = 12567;
			break;
		case 6012: // 극한의 샌달 원석
			id = 12566;
			break;
		case 40397: // 키메라의 가죽 (용)
			id = 728;
			break;
		case 40398: // 키메라의 가죽 (산양)
			id = 729;
			break;
		case 40399: // 키메라의 가죽 (사자)
			id = 730;
			break;
		case 40400: // 키메라의 가죽 (뱀)
			id = 731;
			break;
		case 40091: // 빈 주문서 (레벨 2)
			id = 620;
			break;
		case 40092: // 빈 주문서 (레벨 3)
			id = 621;
			break;
		case 40093: // 빈 주문서 (레벨 4)
			id = 622;
			break;
		case 40094: // 빈 주문서 (레벨 5)
			id = 623;
			break;
		case 111:// 마력을 잃은 얼음 여왕의지팡이
			id = 988;
			break;
		case 40455: // 파란 옷감
			id = 376;
			break;
		case 40456: // 붉은 옷감
			id = 378;
			break;
		case 40457: // 하얀 옷감
			id = 379;
			break;
		case 40460: // 아시타지오의 재
			id = 429;
			break;
		case 40396: // 지룡 비늘
			id = 408;
			break;
		case 40394: // 풍룡 비늘
			id = 410;
			break;
		case 40393: // 화룡 비늘
			id = 409;
			break;
		case 40162: // 골렘의숨결
			id = 1151;
			break;
		case 40169: // 드레이크의숨결
			id = 1154;
			break;
		case 40409: // 불새의숨결
			id = 1152;
			break;
		case 181: // 장궁
			id = 81;
			break;
		case 40491: // 그리폰의 깃털
			id = 618;
			break;
		case 40498: // 바람의 눈물
			id = 619;
			break;
		case 20165: // 데몬의 장갑
			id = 552;
			break;
		case 20197: // 데몬의 부츠
			id = 554;
			break;
		case 40406: // 고급피혁
			id = 335;
			break;
		case 20306: // 낡은 신체의 벨트
			id = 502;
			break;
		case 20308: // 낡은 정신의 벨트
			id = 503;
			break;
		case 20307: // 낡은 영혼의 벨트
			id = 504;
			break;
		case 427102: // 투견의 이빨
			id = 1067;
			break;
		case 427108: // 강철의 이빨
			id = 1071;
			break;
		case 427103: // 황금의 이빨
			id = 1070;
			break;
		case 427104: // 사냥개의 이빨
			id = 1064;
			break;
		case 40405: // 동물가죽
			id = 332;
			break;
		case 40407: // 뼈조각
			id = 336;
			break;
		case 427002:// 레더 펫아머
			id = 1058;
			break;
		case 427004:// 강철 펫아머
			id = 1062;
			break;
		case 427006:// 크로스 펫아머
			id = 1057;
			break;
		case 410000:// 소체
			id = 3223;
			break;
		case 410003:// 사키
			id = 3217;
			break;
		case 410004:// 흑키
			id = 3218;
			break;
		case 41:// 일본도
			return 2;
		case 5:// 요단
			return 39;
		case 104:// 포챠드
			return 44;
		case 99:// 요정족창
			return 70;
		case 148:// 대도
			return 138;
		case 32:// 그라디우스
			return 141;
		case 145:// 광도
			return 144;
		case 180:// 크로
			return 269;
		case 42:// 레이
			return 274;
		case 64:// 대검
			return 337;
		case 40519:// 판의갈기털
			return 245;
		case 40502:// 실
			return 251;
		case 430106:// 지룡마안
			return 4012;
		case 430104:// 수룡마안
			return 4013;
		case 430105:// 풍룡마안
			return 4015;
		case 430107:// 화룡마안
			return 4014;
			
	
			
	
		case 3000065:// 지룡의표식
			return 106671;
		case 3000064:// 지룡의표식
			return 106672;
		case 5000066:// 수룡의표식
			return 10665;
		case 5000065:// 풍룡의표식
			return 10666;
		case 5000063:// 화룡의표식
			return 10667;
		case 41159:// 픽시의날개깃털
			return 0x0A6F;
		case 60327: // 영양미끼
			return 0x3883;
		case 60334: // 릴 장착 낚시대
			return 0x387F;
		case 60326: // 고탄력 낚시대
			return 0x387E;
		case 41245: // 용해제
			return 1906;
		case 21124: // 단단한호박갑옷
			return 14024;
		case 427306: // 호박장갑
			return 14025;
		case 256: // 호박검
			return 14026;
		case 4500027: // 호박양손검
			return 14027;
		case 263: // 지식의호박지팡이
			return 14028;
		case 4500026: // 호박 각궁
			return 14029;
		case 265: // 호박 체인소드
			return 14030;
		case 264: // 호박 키링크
			return 14031;
		case 60423: // 용의 호박석
			return 14820;
		case 60427: // 자음 블록(ㄱ)
			return 12916;
		case 60428: // 자음 블록(ㄷ)
			return 12917;
		case 60429: // 자음 블록(ㄹ)
			return 12918;
		case 60430: // 자음 블록(ㅁ)
			return 12919;
		case 60431: // 자음 블록(ㅂ)
			return 12920;
		case 60432: // 자음 블록(ㅅ)
			return 12921;
		case 60433: // 자음 블록(ㅇ)
			return 12922;
		case 60434: // 자음 블록(ㅈ)
			return 12923;
		case 60435: // 자음 블록(ㅊ)
			return 12924;
		case 60436: // 자음 블록(ㅋ)
			return 12925;
		case 60437: // 자음 블록(ㅌ)
			return 12926;
		case 60438: // 자음 블록(ㅍ)
			return 12927;
		case 60473: // 환상의 무기마법 주문서
			return 14932;
		case 284: // 아툰무기
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
