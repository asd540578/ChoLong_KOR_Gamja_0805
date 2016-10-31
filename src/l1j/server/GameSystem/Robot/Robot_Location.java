package l1j.server.GameSystem.Robot;

import java.util.ArrayList;
import java.util.Random;

public class Robot_Location {

	private static Random _random = new Random(System.currentTimeMillis());

	private static ArrayList<Robot_Location_bean> 기란셋팅 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 기란셋팅2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 기란셋팅3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 기란셋팅4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 기란셋팅5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 오렌셋팅 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 글말셋팅 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 웰던셋팅 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 텔녀 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용던입구 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 본던입구 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 기감입구 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 상아탑4층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 상아탑5층 = new ArrayList<Robot_Location_bean>();
	/*private static ArrayList<Robot_Location_bean> 용계1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용계2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용계3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용계4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 자이언트밭1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 자이언트밭2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 화둥1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 화둥2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 화둥3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 화둥4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 화둥5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 지하침공로1층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 지하침공로1층2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 지하침공로2층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 지하침공로2층2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 지하침공로3층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 지하침공로3층2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 에바1층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 에바1층2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 에바2층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 에바2층2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 에바3층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 에바3층2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 에바4층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 에바4층2 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 하이네잡밭 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 하이네잡밭2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 하이네잡밭3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 하이네잡밭4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 하이네잡밭5 = new ArrayList<Robot_Location_bean>();*/

	private static ArrayList<Robot_Location_bean> 지저 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용던1층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용던2층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용던3층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용던4층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용던5층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용던6층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 용던7층 = new ArrayList<Robot_Location_bean>();

	
	private static ArrayList<Robot_Location_bean> 리뉴얼오만1층 = new ArrayList<Robot_Location_bean>(); //160316
	private static ArrayList<Robot_Location_bean> 리뉴얼오만2층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 리뉴얼오만3층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 리뉴얼오만4층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 리뉴얼오만5층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 리뉴얼오만6층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 리뉴얼오만7층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 리뉴얼오만8층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 리뉴얼오만9층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 리뉴얼오만10층 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 본던1층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 본던2층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 본던3층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 본던4층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 본던5층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 본던6층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 본던7층 = new ArrayList<Robot_Location_bean>();


	private static ArrayList<Robot_Location_bean> 선박심해 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 선박심해2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 선박심해3 = new ArrayList<Robot_Location_bean>();
	
	private static ArrayList<Robot_Location_bean> 잊섬 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 잊섬2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 잊섬3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 잊섬4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 잊섬5 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 개미굴1 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 개미굴2 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 개미굴3 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 개미굴4 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 개미굴5 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 개미굴6 = new ArrayList<Robot_Location_bean>();

	private static ArrayList<Robot_Location_bean> 기감1층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 기감2층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 기감3층 = new ArrayList<Robot_Location_bean>();
	private static ArrayList<Robot_Location_bean> 기감4층 = new ArrayList<Robot_Location_bean>();




	public static void 로케이션등록(int x, int y, int m) {

	}

	public static ArrayList<Robot_Location_bean> 로케이션(L1RobotInstance bot) {
		_random.setSeed(System.currentTimeMillis());
		if (bot.사냥봇_타입 == L1RobotInstance.SETTING) {
			if (bot.사냥봇_위치.equalsIgnoreCase("용던1층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던2층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던3층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던4층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던5층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던6층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던7층")) {
				return _random.nextInt(1000) > 500 ? 기란셋팅 : 기란셋팅2;
			}
			int rr = _random.nextInt(16);
			if (rr == 15) {
				switch (_random.nextInt(2)) {
				case 0:
					rr = 0;
					break;
				case 1:
					rr = 7;
					break;
				}
			}
			switch (rr) {
			case 7:
			case 11:
			case 0:
				return _random.nextInt(1000) >= 500 ? 기란셋팅2 : 기란셋팅5;
			case 8:
			case 4:
			case 1:
				return 오렌셋팅;
			case 13:
			case 12:
				return 기란셋팅4;
			case 10:
			case 9:
			case 5:
			case 2:
				return _random.nextInt(1000) >= 500 ? 기란셋팅 : 기란셋팅3;
				//return 글말셋팅; //원래 글말셋팅
			case 14:
			case 6:
			case 3:
				return 글말셋팅;
				//return 웰던셋팅;
			default:
				break;
			}
			return 기란셋팅;
		} else if (bot.사냥봇_타입 == L1RobotInstance.TEL_NPC_MOVE) {
			if (bot.사냥봇_위치.equalsIgnoreCase("용던1층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던2층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던3층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던4층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던5층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던6층")
					|| bot.사냥봇_위치.equalsIgnoreCase("용던7층")) {
				return 용던입구;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("본던1층")
					|| bot.사냥봇_위치.equalsIgnoreCase("본던2층")
					|| bot.사냥봇_위치.equalsIgnoreCase("본던3층")
					|| bot.사냥봇_위치.equalsIgnoreCase("본던4층")
					|| bot.사냥봇_위치.equalsIgnoreCase("본던5층")
					|| bot.사냥봇_위치.equalsIgnoreCase("본던6층")
					|| bot.사냥봇_위치.equalsIgnoreCase("본던7층")
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만1층")//160316
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만2층")
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만3층")
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만4층")
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만5층")
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만6층")
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만7층")
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만8층")
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만9층")
					|| bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만10층")	
					|| bot.사냥봇_위치.startsWith("잊섬")) { //잊섬
				    return 텔녀; // 원래null
			} else if (bot.사냥봇_위치.startsWith("오만")
					|| bot.사냥봇_위치.startsWith("개미굴")) {
				    return null;
	         } else if (bot.사냥봇_위치.equalsIgnoreCase("기감1층")
	        		 || bot.사냥봇_위치.equalsIgnoreCase("기감2층")
	        		 || bot.사냥봇_위치.equalsIgnoreCase("기감3층")
	        		 || bot.사냥봇_위치.equalsIgnoreCase("기감4층"))
			   return 기감입구;
			if (bot.getX() >= 33410 && bot.getX() <= 33461
					&& bot.getY() >= 32788 && bot.getY() <= 32838
					&& bot.getMapId() == 4)// 기란
				return 텔녀;
		  } else if (bot.사냥봇_타입 == L1RobotInstance.HUNT_MOVE) {
			bot.텔사냥 = false;
			/*if (bot.사냥봇_위치.equalsIgnoreCase("용계")) {
				switch (_random.nextInt(4)) {
				case 0:
					return 용계1;
				case 1:
					return 용계2;
				case 2:
					return 용계3;
				case 3:
					return 용계4;
				default:
					break;
				}
			} else if (bot.사냥봇_위치.equalsIgnoreCase("자이언트밭")) {
				if (_random.nextInt(100) < 50)
					return 자이언트밭1;
				else
					return 자이언트밭2;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("화둥")) {
				switch (_random.nextInt(5)) {
				case 0:
					return 화둥1;
				case 1:
					return 화둥2;
				case 2:
					return 화둥3;
				case 3:
					return 화둥4;
				case 4:
					return 화둥5;
				default:
					break;
				}
			} else if (bot.사냥봇_위치.equalsIgnoreCase("지하침공로1층")) {
				bot.텔사냥 = true;
				if (_random.nextInt(100) >= 50)
					return 지하침공로1층;
				else
					return 지하침공로1층2;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("지하침공로2층")) {
				bot.텔사냥 = true;
				if (_random.nextInt(100) >= 50)
					return 지하침공로2층;
				else
					return 지하침공로2층2;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("지하침공로3층")) {
				bot.텔사냥 = true;
				if (_random.nextInt(100) >= 50)
					return 지하침공로3층;
				else
					return 지하침공로3층2;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("에바1층")) {
				bot.텔사냥 = true;
				if (_random.nextInt(100) >= 50)
					return 에바1층;
				else
					return 에바1층2;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("에바2층")) {
				bot.텔사냥 = true;
				if (_random.nextInt(100) >= 50)
					return 에바2층;
				else
					return 에바2층2;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("에바3층")) {
				bot.텔사냥 = true;
				if (_random.nextInt(100) >= 50)
					return 에바3층;
				else
					return 에바3층2;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("에바4층")) {
				bot.텔사냥 = true;
				if (_random.nextInt(100) >= 50)
					return 에바4층;
				else
					return 에바4층2;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("하이네잡밭")) {
				switch (_random.nextInt(9)) {
				case 5:
				case 0:
					return 하이네잡밭;
				case 6:
				case 1:
					return 하이네잡밭2;
				case 7:
				case 2:
					return 하이네잡밭3;
				case 8:
				case 3:
					return 하이네잡밭4;
				case 4:
					return 하이네잡밭5;
				default:
					break;
				}
			} else*/ if (bot.사냥봇_위치.equalsIgnoreCase("선박심해")) {
				bot.텔사냥 = true;
				switch (_random.nextInt(3)) {
				case 0:
					return 선박심해;
				case 1:
					return 선박심해2;
				case 2:
					return 선박심해3;
				default:
					break;
				}
			} else if (bot.사냥봇_위치.equalsIgnoreCase("잊섬")) {
				//bot.텔사냥 = false;
				switch (_random.nextInt(5)) {
				case 0:
					return 잊섬;
				case 1:
					return 잊섬2;
				case 2:
					return 잊섬3;
				case 3:
					return 잊섬4;
				case 4:
					return 잊섬5;
				default:
					break;
				}
			} else if (bot.사냥봇_위치.equalsIgnoreCase("개미굴1")) {
				bot.텔사냥 = true;
				return 개미굴1;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("개미굴2")) {
				bot.텔사냥 = true;
				return 개미굴2;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("개미굴3")) {
				bot.텔사냥 = true;
				return 개미굴3;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("개미굴4")) {
				bot.텔사냥 = true;
				return 개미굴4;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("개미굴5")) {
				bot.텔사냥 = true;
				return 개미굴5;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("개미굴6")) {
				bot.텔사냥 = true;
				return 개미굴6;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("상아탑4층")) {
				bot.텔사냥 = true;
				return 상아탑4층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("상아탑5층")) {
				bot.텔사냥 = true;
				return 상아탑5층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("지저")) {
				bot.텔사냥 = true;
				return 지저;
//160316
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만1층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만1층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만2층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만2층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만3층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만3층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만4층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만4층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만5층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만5층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만6층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만6층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만7층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만7층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만8층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만8층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만9층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만9층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("리뉴얼오만10층")) {
				bot.텔사냥 = true;
				return 리뉴얼오만10층;
//160316
			} else if (bot.사냥봇_위치.equalsIgnoreCase("용던1층")) {
				bot.텔사냥 = true;
				return 용던1층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("용던2층")) {
				bot.텔사냥 = true;
				return 용던2층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("용던3층")) {
				bot.텔사냥 = true;
				return 용던3층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("용던4층")) {
				bot.텔사냥 = true;
				return 용던4층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("용던5층")) {
				bot.텔사냥 = true;
				return 용던5층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("용던6층")) {
				bot.텔사냥 = true;
				return 용던6층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("용던7층")) {
				bot.텔사냥 = true;
				return 용던7층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("본던1층")) {
				bot.텔사냥 = true;
				return 본던1층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("본던2층")) {
				bot.텔사냥 = true;
				return 본던2층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("본던3층")) {
				bot.텔사냥 = true;
				return 본던3층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("본던4층")) {
				bot.텔사냥 = true;
				return 본던4층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("본던5층")) {
				bot.텔사냥 = true;
				return 본던5층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("본던6층")) {
				bot.텔사냥 = true;
				return 본던6층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("본던7층")) {
				bot.텔사냥 = true;
				return 본던7층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("기감1층")) {
				bot.텔사냥 = true;
				return 기감1층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("기감2층")) {
				bot.텔사냥 = true;
				return 기감2층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("기감3층")) {
				bot.텔사냥 = true;
				return 기감3층;
			} else if (bot.사냥봇_위치.equalsIgnoreCase("기감4층")) {
				bot.텔사냥 = true;
				return 기감4층;

			}
		}
		return null;
	}

	public static void setRLOC() {
		// 물약, 창고, 버프
		기란셋팅.add(new Robot_Location_bean(33457, 32819, 4));
		기란셋팅.add(new Robot_Location_bean(33431, 32816, 4));
		기란셋팅.add(new Robot_Location_bean(33437, 32804, 4));
		기란셋팅2.add(new Robot_Location_bean(33432, 32815, 4));
		기란셋팅2.add(new Robot_Location_bean(33457, 32820, 4));
		기란셋팅2.add(new Robot_Location_bean(33437, 32804, 4));
		기란셋팅3.add(new Robot_Location_bean(33428, 32806, 4));
		기란셋팅3.add(new Robot_Location_bean(33422, 32813, 4));
		기란셋팅3.add(new Robot_Location_bean(33437, 32803, 4));
		기란셋팅4.add(new Robot_Location_bean(33437, 32803, 4));
		기란셋팅5.add(new Robot_Location_bean(33428, 32806, 4));
		기란셋팅5.add(new Robot_Location_bean(33440, 32801, 4));

		오렌셋팅.add(new Robot_Location_bean(34065, 32287, 4));
		오렌셋팅.add(new Robot_Location_bean(34053, 32287, 4));
		오렌셋팅.add(new Robot_Location_bean(34064, 32279, 4));
		글말셋팅.add(new Robot_Location_bean(32596, 32741, 4));
		글말셋팅.add(new Robot_Location_bean(32609, 32735, 4));
		웰던셋팅.add(new Robot_Location_bean(33738, 32494, 4));
		웰던셋팅.add(new Robot_Location_bean(33723, 32488, 4));
		웰던셋팅.add(new Robot_Location_bean(33714, 32498, 4));
		// 기란 텔녀 이동
		텔녀.add(new Robot_Location_bean(33437, 32795, 4));
		// 용던 입구
		용던입구.add(new Robot_Location_bean(33446, 32828, 4));
		// 기감 입구
		기감입구.add(new Robot_Location_bean(33428, 32820, 4));
		// 본던 입구
		본던입구.add(new Robot_Location_bean(32727, 32929, 4));
		// 용계
		/*용계1.add(new Robot_Location_bean(33334, 32440, 4));
		용계1.add(new Robot_Location_bean(33323, 32432, 4));
		용계1.add(new Robot_Location_bean(33315, 32432, 4));
		용계1.add(new Robot_Location_bean(33304, 32429, 4));
		용계1.add(new Robot_Location_bean(33292, 32431, 4));
		용계1.add(new Robot_Location_bean(33276, 32429, 4));
		용계1.add(new Robot_Location_bean(33266, 32424, 4));
		용계1.add(new Robot_Location_bean(33262, 32412, 4));
		용계1.add(new Robot_Location_bean(33275, 32400, 4));
		용계1.add(new Robot_Location_bean(33287, 32400, 4));
		용계1.add(new Robot_Location_bean(33306, 32394, 4));
		용계1.add(new Robot_Location_bean(33318, 32391, 4));
		용계1.add(new Robot_Location_bean(33318, 32383, 4));
		용계1.add(new Robot_Location_bean(33308, 32379, 4));
		용계1.add(new Robot_Location_bean(33300, 32378, 4));
		용계1.add(new Robot_Location_bean(33298, 32366, 4));
		용계1.add(new Robot_Location_bean(33306, 32360, 4));
		용계1.add(new Robot_Location_bean(33322, 32352, 4));
		용계1.add(new Robot_Location_bean(33337, 32350, 4));
		용계1.add(new Robot_Location_bean(33352, 32353, 4));
		용계1.add(new Robot_Location_bean(33368, 32351, 4));
		용계1.add(new Robot_Location_bean(33381, 32353, 4));
		용계1.add(new Robot_Location_bean(33389, 32336, 4));
		용계1.add(new Robot_Location_bean(33384, 32324, 4));
		용계1.add(new Robot_Location_bean(33381, 32315, 4));
		용계1.add(new Robot_Location_bean(33373, 32305, 4));
		용계1.add(new Robot_Location_bean(33361, 32309, 4));
		용계1.add(new Robot_Location_bean(33344, 32320, 4));
		용계1.add(new Robot_Location_bean(33326, 32303, 4));
		용계1.add(new Robot_Location_bean(33345, 32283, 4));
		용계1.add(new Robot_Location_bean(33358, 32278, 4));
		용계1.add(new Robot_Location_bean(33359, 32278, 4));
		용계1.add(new Robot_Location_bean(33374, 32276, 4));
		용계1.add(new Robot_Location_bean(33383, 32276, 4));
		용계1.add(new Robot_Location_bean(33393, 32279, 4));
		용계1.add(new Robot_Location_bean(33413, 32296, 4));
		용계1.add(new Robot_Location_bean(33427, 32305, 4));
		용계1.add(new Robot_Location_bean(33428, 32316, 4));
		용계1.add(new Robot_Location_bean(33419, 32326, 4));
		용계1.add(new Robot_Location_bean(33422, 32341, 4));
		용계1.add(new Robot_Location_bean(33413, 32356, 4));
		용계1.add(new Robot_Location_bean(33411, 32378, 4));
		용계1.add(new Robot_Location_bean(33400, 32389, 4));
		용계1.add(new Robot_Location_bean(33404, 32404, 4));
		용계1.add(new Robot_Location_bean(33398, 32414, 4));
		용계1.add(new Robot_Location_bean(33385, 32423, 4));
		용계1.add(new Robot_Location_bean(33382, 32440, 4));
		용계1.add(new Robot_Location_bean(33373, 32446, 4));
		용계1.add(new Robot_Location_bean(33357, 32439, 4));
		용계1.add(new Robot_Location_bean(33342, 32438, 4));
		용계2.add(new Robot_Location_bean(33334, 32440, 4));
		용계2.add(new Robot_Location_bean(33323, 32432, 4));
		용계2.add(new Robot_Location_bean(33315, 32432, 4));
		용계2.add(new Robot_Location_bean(33304, 32429, 4));
		용계2.add(new Robot_Location_bean(33292, 32431, 4));
		용계2.add(new Robot_Location_bean(33276, 32429, 4));
		용계2.add(new Robot_Location_bean(33266, 32424, 4));
		용계2.add(new Robot_Location_bean(33262, 32412, 4));
		용계2.add(new Robot_Location_bean(33275, 32400, 4));
		용계2.add(new Robot_Location_bean(33287, 32400, 4));
		용계2.add(new Robot_Location_bean(33306, 32394, 4));
		용계2.add(new Robot_Location_bean(33318, 32391, 4));
		용계2.add(new Robot_Location_bean(33328, 32386, 4));
		용계2.add(new Robot_Location_bean(33342, 32387, 4));
		용계2.add(new Robot_Location_bean(33356, 32402, 4));
		용계2.add(new Robot_Location_bean(33362, 32384, 4));
		용계2.add(new Robot_Location_bean(33359, 32373, 4));
		용계2.add(new Robot_Location_bean(33367, 32367, 4));
		용계2.add(new Robot_Location_bean(33388, 32373, 4));
		용계2.add(new Robot_Location_bean(33395, 32358, 4));
		용계2.add(new Robot_Location_bean(33385, 32350, 4));
		용계2.add(new Robot_Location_bean(33388, 32335, 4));
		용계2.add(new Robot_Location_bean(33382, 32315, 4));
		용계2.add(new Robot_Location_bean(33370, 32302, 4));
		용계2.add(new Robot_Location_bean(33358, 32296, 4));
		용계2.add(new Robot_Location_bean(33347, 32298, 4));
		용계2.add(new Robot_Location_bean(33339, 32288, 4));
		용계2.add(new Robot_Location_bean(33345, 32283, 4));
		용계2.add(new Robot_Location_bean(33358, 32278, 4));
		용계2.add(new Robot_Location_bean(33359, 32278, 4));
		용계2.add(new Robot_Location_bean(33374, 32276, 4));
		용계2.add(new Robot_Location_bean(33383, 32276, 4));
		용계2.add(new Robot_Location_bean(33393, 32279, 4));
		용계2.add(new Robot_Location_bean(33413, 32296, 4));
		용계2.add(new Robot_Location_bean(33427, 32305, 4));
		용계2.add(new Robot_Location_bean(33428, 32316, 4));
		용계2.add(new Robot_Location_bean(33419, 32326, 4));
		용계2.add(new Robot_Location_bean(33422, 32341, 4));
		용계2.add(new Robot_Location_bean(33413, 32356, 4));
		용계2.add(new Robot_Location_bean(33411, 32378, 4));
		용계2.add(new Robot_Location_bean(33400, 32389, 4));
		용계2.add(new Robot_Location_bean(33404, 32404, 4));
		용계2.add(new Robot_Location_bean(33398, 32414, 4));
		용계2.add(new Robot_Location_bean(33385, 32423, 4));
		용계2.add(new Robot_Location_bean(33382, 32440, 4));
		용계2.add(new Robot_Location_bean(33373, 32446, 4));
		용계2.add(new Robot_Location_bean(33357, 32439, 4));
		용계2.add(new Robot_Location_bean(33342, 32438, 4));
		용계3.add(new Robot_Location_bean(33334, 32440, 4));
		용계3.add(new Robot_Location_bean(33352, 32438, 4));
		용계3.add(new Robot_Location_bean(33370, 32444, 4));
		용계3.add(new Robot_Location_bean(33379, 32445, 4));
		용계3.add(new Robot_Location_bean(33381, 32432, 4));
		용계3.add(new Robot_Location_bean(33386, 32419, 4));
		용계3.add(new Robot_Location_bean(33405, 32411, 4));
		용계3.add(new Robot_Location_bean(33402, 32392, 4));
		용계3.add(new Robot_Location_bean(33386, 32391, 4));
		용계3.add(new Robot_Location_bean(33400, 32387, 4));
		용계3.add(new Robot_Location_bean(33409, 32378, 4));
		용계3.add(new Robot_Location_bean(33411, 32357, 4));
		용계3.add(new Robot_Location_bean(33422, 32341, 4));
		용계3.add(new Robot_Location_bean(33420, 32323, 4));
		용계3.add(new Robot_Location_bean(33429, 32314, 4));
		용계3.add(new Robot_Location_bean(33427, 32305, 4));
		용계3.add(new Robot_Location_bean(33414, 32296, 4));
		용계3.add(new Robot_Location_bean(33400, 32284, 4));
		용계3.add(new Robot_Location_bean(33391, 32275, 4));
		용계3.add(new Robot_Location_bean(33379, 32275, 4));
		용계3.add(new Robot_Location_bean(33356, 32277, 4));
		용계3.add(new Robot_Location_bean(33349, 32280, 4));
		용계3.add(new Robot_Location_bean(33338, 32290, 4));
		용계3.add(new Robot_Location_bean(33323, 32303, 4));
		용계3.add(new Robot_Location_bean(33317, 32320, 4));
		용계3.add(new Robot_Location_bean(33308, 32333, 4));
		용계3.add(new Robot_Location_bean(33286, 32334, 4));
		용계3.add(new Robot_Location_bean(33277, 32348, 4));
		용계3.add(new Robot_Location_bean(33275, 32366, 4));
		용계3.add(new Robot_Location_bean(33269, 32381, 4));
		용계3.add(new Robot_Location_bean(33259, 32396, 4));
		용계3.add(new Robot_Location_bean(33259, 32407, 4));
		용계3.add(new Robot_Location_bean(33266, 32424, 4));
		용계3.add(new Robot_Location_bean(33288, 32430, 4));
		용계3.add(new Robot_Location_bean(33305, 32431, 4));
		용계3.add(new Robot_Location_bean(33325, 32431, 4));
		용계4.add(new Robot_Location_bean(33334, 32440, 4));
		용계4.add(new Robot_Location_bean(33352, 32438, 4));
		용계4.add(new Robot_Location_bean(33370, 32444, 4));
		용계4.add(new Robot_Location_bean(33379, 32445, 4));
		용계4.add(new Robot_Location_bean(33381, 32432, 4));
		용계4.add(new Robot_Location_bean(33386, 32419, 4));
		용계4.add(new Robot_Location_bean(33405, 32411, 4));
		용계4.add(new Robot_Location_bean(33402, 32392, 4));
		용계4.add(new Robot_Location_bean(33386, 32391, 4));
		용계4.add(new Robot_Location_bean(33409, 32378, 4));
		용계4.add(new Robot_Location_bean(33411, 32357, 4));
		용계4.add(new Robot_Location_bean(33422, 32341, 4));
		용계4.add(new Robot_Location_bean(33420, 32323, 4));
		용계4.add(new Robot_Location_bean(33429, 32314, 4));
		용계4.add(new Robot_Location_bean(33427, 32305, 4));
		용계4.add(new Robot_Location_bean(33414, 32296, 4));
		용계4.add(new Robot_Location_bean(33400, 32284, 4));
		용계4.add(new Robot_Location_bean(33379, 32275, 4));
		용계4.add(new Robot_Location_bean(33356, 32277, 4));
		용계4.add(new Robot_Location_bean(33338, 32290, 4));
		용계4.add(new Robot_Location_bean(33324, 32304, 4));
		용계4.add(new Robot_Location_bean(33340, 32314, 4));
		용계4.add(new Robot_Location_bean(33356, 32315, 4));
		용계4.add(new Robot_Location_bean(33372, 32305, 4));
		용계4.add(new Robot_Location_bean(33380, 32313, 4));
		용계4.add(new Robot_Location_bean(33385, 32324, 4));
		용계4.add(new Robot_Location_bean(33388, 32336, 4));
		용계4.add(new Robot_Location_bean(33384, 32352, 4));
		용계4.add(new Robot_Location_bean(33395, 32359, 4));
		용계4.add(new Robot_Location_bean(33389, 32370, 4));
		용계4.add(new Robot_Location_bean(33376, 32371, 4));
		용계4.add(new Robot_Location_bean(33364, 32368, 4));
		용계4.add(new Robot_Location_bean(33358, 32376, 4));
		용계4.add(new Robot_Location_bean(33361, 32389, 4));
		용계4.add(new Robot_Location_bean(33355, 32402, 4));
		용계4.add(new Robot_Location_bean(33343, 32388, 4));
		용계4.add(new Robot_Location_bean(33324, 32386, 4));
		용계4.add(new Robot_Location_bean(33312, 32395, 4));
		용계4.add(new Robot_Location_bean(33295, 32396, 4));
		용계4.add(new Robot_Location_bean(33282, 32401, 4));
		용계4.add(new Robot_Location_bean(33269, 32406, 4));
		용계4.add(new Robot_Location_bean(33260, 32418, 4));
		용계4.add(new Robot_Location_bean(33274, 32426, 4));
		용계4.add(new Robot_Location_bean(33292, 32430, 4));
		용계4.add(new Robot_Location_bean(33306, 32432, 4));
		용계4.add(new Robot_Location_bean(33320, 32432, 4));
		용계4.add(new Robot_Location_bean(33336, 32433, 4));
		자이언트밭1.add(new Robot_Location_bean(34278, 33214, 4));
		자이언트밭1.add(new Robot_Location_bean(34279, 33232, 4));
		자이언트밭1.add(new Robot_Location_bean(34277, 33248, 4));
		자이언트밭1.add(new Robot_Location_bean(34276, 33262, 4));
		자이언트밭1.add(new Robot_Location_bean(34278, 33275, 4));
		자이언트밭1.add(new Robot_Location_bean(34276, 33299, 4));
		자이언트밭1.add(new Robot_Location_bean(34274, 33325, 4));
		자이언트밭1.add(new Robot_Location_bean(34272, 33342, 4));
		자이언트밭1.add(new Robot_Location_bean(34277, 33355, 4));
		자이언트밭1.add(new Robot_Location_bean(34278, 33370, 4));
		자이언트밭1.add(new Robot_Location_bean(34272, 33384, 4));
		자이언트밭1.add(new Robot_Location_bean(34255, 33387, 4));
		자이언트밭1.add(new Robot_Location_bean(34247, 33400, 4));
		자이언트밭1.add(new Robot_Location_bean(34238, 33412, 4));
		자이언트밭1.add(new Robot_Location_bean(34226, 33431, 4));
		자이언트밭1.add(new Robot_Location_bean(34227, 33455, 4));
		자이언트밭1.add(new Robot_Location_bean(34230, 33430, 4));
		자이언트밭1.add(new Robot_Location_bean(34227, 33422, 4));
		자이언트밭1.add(new Robot_Location_bean(34240, 33410, 4));
		자이언트밭1.add(new Robot_Location_bean(34252, 33397, 4));
		자이언트밭1.add(new Robot_Location_bean(34266, 33387, 4));
		자이언트밭1.add(new Robot_Location_bean(34276, 33377, 4));
		자이언트밭1.add(new Robot_Location_bean(34275, 33364, 4));
		자이언트밭1.add(new Robot_Location_bean(34263, 33364, 4));
		자이언트밭1.add(new Robot_Location_bean(34250, 33363, 4));
		자이언트밭1.add(new Robot_Location_bean(34235, 33358, 4));
		자이언트밭1.add(new Robot_Location_bean(34238, 33340, 4));
		자이언트밭1.add(new Robot_Location_bean(34225, 33329, 4));
		자이언트밭1.add(new Robot_Location_bean(34224, 33315, 4));
		자이언트밭1.add(new Robot_Location_bean(34227, 33300, 4));
		자이언트밭1.add(new Robot_Location_bean(34240, 33288, 4));
		자이언트밭1.add(new Robot_Location_bean(34248, 33279, 4));
		자이언트밭1.add(new Robot_Location_bean(34256, 33258, 4));
		자이언트밭1.add(new Robot_Location_bean(34262, 33247, 4));
		자이언트밭1.add(new Robot_Location_bean(34263, 33238, 4));
		자이언트밭1.add(new Robot_Location_bean(34262, 33228, 4));
		자이언트밭1.add(new Robot_Location_bean(34260, 33219, 4));
		자이언트밭1.add(new Robot_Location_bean(34269, 33218, 4));
		자이언트밭2.add(new Robot_Location_bean(34278, 33214, 4));
		자이언트밭2.add(new Robot_Location_bean(34265, 33220, 4));
		자이언트밭2.add(new Robot_Location_bean(34262, 33232, 4));
		자이언트밭2.add(new Robot_Location_bean(34263, 33245, 4));
		자이언트밭2.add(new Robot_Location_bean(34257, 33258, 4));
		자이언트밭2.add(new Robot_Location_bean(34249, 33273, 4));
		자이언트밭2.add(new Robot_Location_bean(34246, 33283, 4));
		자이언트밭2.add(new Robot_Location_bean(34232, 33291, 4));
		자이언트밭2.add(new Robot_Location_bean(34226, 33304, 4));
		자이언트밭2.add(new Robot_Location_bean(34225, 33315, 4));
		자이언트밭2.add(new Robot_Location_bean(34223, 33327, 4));
		자이언트밭2.add(new Robot_Location_bean(34236, 33337, 4));
		자이언트밭2.add(new Robot_Location_bean(34237, 33354, 4));
		자이언트밭2.add(new Robot_Location_bean(34245, 33363, 4));
		자이언트밭2.add(new Robot_Location_bean(34255, 33357, 4));
		자이언트밭2.add(new Robot_Location_bean(34254, 33342, 4));
		자이언트밭2.add(new Robot_Location_bean(34255, 33329, 4));
		자이언트밭2.add(new Robot_Location_bean(34245, 33316, 4));
		자이언트밭2.add(new Robot_Location_bean(34242, 33300, 4));
		자이언트밭2.add(new Robot_Location_bean(34245, 33284, 4));
		자이언트밭2.add(new Robot_Location_bean(34261, 33276, 4));
		자이언트밭2.add(new Robot_Location_bean(34273, 33280, 4));
		자이언트밭2.add(new Robot_Location_bean(34279, 33271, 4));
		자이언트밭2.add(new Robot_Location_bean(34276, 33261, 4));
		자이언트밭2.add(new Robot_Location_bean(34276, 33248, 4));
		자이언트밭2.add(new Robot_Location_bean(34279, 33236, 4));
		자이언트밭2.add(new Robot_Location_bean(34278, 33222, 4));
		자이언트밭2.add(new Robot_Location_bean(34265, 33218, 4));
		자이언트밭2.add(new Robot_Location_bean(34258, 33209, 4));
		자이언트밭2.add(new Robot_Location_bean(34258, 33195, 4));
		자이언트밭2.add(new Robot_Location_bean(34265, 33177, 4));
		자이언트밭2.add(new Robot_Location_bean(34279, 33166, 4));
		자이언트밭2.add(new Robot_Location_bean(34272, 33152, 4));
		자이언트밭2.add(new Robot_Location_bean(34279, 33139, 4));
		자이언트밭2.add(new Robot_Location_bean(34278, 33128, 4));
		자이언트밭2.add(new Robot_Location_bean(34265, 33118, 4));
		자이언트밭2.add(new Robot_Location_bean(34256, 33131, 4));
		자이언트밭2.add(new Robot_Location_bean(34254, 33149, 4));
		자이언트밭2.add(new Robot_Location_bean(34259, 33165, 4));
		자이언트밭2.add(new Robot_Location_bean(34263, 33179, 4));
		자이언트밭2.add(new Robot_Location_bean(34259, 33191, 4));
		자이언트밭2.add(new Robot_Location_bean(34258, 33206, 4));
		자이언트밭2.add(new Robot_Location_bean(34261, 33217, 4));
		자이언트밭2.add(new Robot_Location_bean(34270, 33218, 4));

		/*화둥1.add(new Robot_Location_bean(33738, 32396, 4));
		화둥1.add(new Robot_Location_bean(33735, 32403, 4));
		화둥1.add(new Robot_Location_bean(33724, 32413, 4));
		화둥1.add(new Robot_Location_bean(33714, 32419, 4));
		화둥1.add(new Robot_Location_bean(33701, 32414, 4));
		화둥1.add(new Robot_Location_bean(33695, 32404, 4));
		화둥1.add(new Robot_Location_bean(33689, 32396, 4));
		화둥1.add(new Robot_Location_bean(33698, 32387, 4));
		화둥1.add(new Robot_Location_bean(33710, 32393, 4));
		화둥1.add(new Robot_Location_bean(33722, 32387, 4));
		화둥1.add(new Robot_Location_bean(33724, 32374, 4));
		화둥1.add(new Robot_Location_bean(33725, 32360, 4));
		화둥1.add(new Robot_Location_bean(33727, 32347, 4));
		화둥1.add(new Robot_Location_bean(33727, 32328, 4));
		화둥1.add(new Robot_Location_bean(33729, 32313, 4));
		화둥1.add(new Robot_Location_bean(33736, 32294, 4));
		화둥1.add(new Robot_Location_bean(33744, 32283, 4));
		화둥1.add(new Robot_Location_bean(33748, 32290, 4));
		화둥1.add(new Robot_Location_bean(33763, 32297, 4));
		화둥1.add(new Robot_Location_bean(33762, 32310, 4));
		화둥1.add(new Robot_Location_bean(33768, 32321, 4));
		화둥1.add(new Robot_Location_bean(33767, 32334, 4));
		화둥1.add(new Robot_Location_bean(33762, 32350, 4));
		화둥1.add(new Robot_Location_bean(33756, 32366, 4));
		화둥1.add(new Robot_Location_bean(33754, 32375, 4));
		화둥1.add(new Robot_Location_bean(33757, 32389, 4));
		화둥1.add(new Robot_Location_bean(33749, 32400, 4));

		화둥2.add(new Robot_Location_bean(33738, 32396, 4));
		화둥2.add(new Robot_Location_bean(33748, 32401, 4));
		화둥2.add(new Robot_Location_bean(33760, 32387, 4));
		화둥2.add(new Robot_Location_bean(33767, 32378, 4));
		화둥2.add(new Robot_Location_bean(33770, 32365, 4));
		화둥2.add(new Robot_Location_bean(33762, 32351, 4));
		화둥2.add(new Robot_Location_bean(33767, 32332, 4));
		화둥2.add(new Robot_Location_bean(33754, 32327, 4));
		화둥2.add(new Robot_Location_bean(33744, 32313, 4));
		화둥2.add(new Robot_Location_bean(33734, 32305, 4));
		화둥2.add(new Robot_Location_bean(33743, 32290, 4));
		화둥2.add(new Robot_Location_bean(33730, 32307, 4));
		화둥2.add(new Robot_Location_bean(33726, 32322, 4));
		화둥2.add(new Robot_Location_bean(33728, 32344, 4));
		화둥2.add(new Robot_Location_bean(33724, 32361, 4));
		화둥2.add(new Robot_Location_bean(33723, 32376, 4));
		화둥2.add(new Robot_Location_bean(33714, 32394, 4));
		화둥2.add(new Robot_Location_bean(33701, 32389, 4));
		화둥2.add(new Robot_Location_bean(33690, 32392, 4));
		화둥2.add(new Robot_Location_bean(33691, 32402, 4));
		화둥2.add(new Robot_Location_bean(33697, 32412, 4));
		화둥2.add(new Robot_Location_bean(33715, 32416, 4));
		화둥2.add(new Robot_Location_bean(33727, 32415, 4));
		화둥2.add(new Robot_Location_bean(33738, 32404, 4));

		화둥3.add(new Robot_Location_bean(33662, 32340, 4));
		화둥3.add(new Robot_Location_bean(33672, 32333, 4));
		화둥3.add(new Robot_Location_bean(33684, 32324, 4));
		화둥3.add(new Robot_Location_bean(33695, 32331, 4));
		화둥3.add(new Robot_Location_bean(33705, 32326, 4));
		화둥3.add(new Robot_Location_bean(33709, 32309, 4));
		화둥3.add(new Robot_Location_bean(33714, 32300, 4));
		화둥3.add(new Robot_Location_bean(33716, 32286, 4));
		화둥3.add(new Robot_Location_bean(33730, 32281, 4));
		화둥3.add(new Robot_Location_bean(33731, 32266, 4));
		화둥3.add(new Robot_Location_bean(33726, 32248, 4));
		화둥3.add(new Robot_Location_bean(33729, 32263, 4));
		화둥3.add(new Robot_Location_bean(33729, 32280, 4));
		화둥3.add(new Robot_Location_bean(33719, 32281, 4));
		화둥3.add(new Robot_Location_bean(33715, 32296, 4));
		화둥3.add(new Robot_Location_bean(33703, 32300, 4));
		화둥3.add(new Robot_Location_bean(33698, 32288, 4));
		화둥3.add(new Robot_Location_bean(33690, 32284, 4));
		화둥3.add(new Robot_Location_bean(33686, 32274, 4));
		화둥3.add(new Robot_Location_bean(33684, 32262, 4));
		화둥3.add(new Robot_Location_bean(33684, 32247, 4));
		화둥3.add(new Robot_Location_bean(33679, 32239, 4));
		화둥3.add(new Robot_Location_bean(33671, 32228, 4));
		화둥3.add(new Robot_Location_bean(33652, 32223, 4));
		화둥3.add(new Robot_Location_bean(33637, 32235, 4));
		화둥3.add(new Robot_Location_bean(33625, 32226, 4));
		화둥3.add(new Robot_Location_bean(33616, 32235, 4));
		화둥3.add(new Robot_Location_bean(33611, 32248, 4));
		화둥3.add(new Robot_Location_bean(33618, 32261, 4));
		화둥3.add(new Robot_Location_bean(33619, 32275, 4));
		화둥3.add(new Robot_Location_bean(33611, 32284, 4));
		화둥3.add(new Robot_Location_bean(33597, 32276, 4));
		화둥3.add(new Robot_Location_bean(33584, 32269, 4));
		화둥3.add(new Robot_Location_bean(33578, 32259, 4));
		화둥3.add(new Robot_Location_bean(33568, 32255, 4));
		화둥3.add(new Robot_Location_bean(33560, 32264, 4));
		화둥3.add(new Robot_Location_bean(33562, 32277, 4));
		화둥3.add(new Robot_Location_bean(33559, 32287, 4));
		화둥3.add(new Robot_Location_bean(33562, 32302, 4));
		화둥3.add(new Robot_Location_bean(33573, 32315, 4));
		화둥3.add(new Robot_Location_bean(33580, 32320, 4));
		화둥3.add(new Robot_Location_bean(33580, 32334, 4));
		화둥3.add(new Robot_Location_bean(33585, 32349, 4));
		화둥3.add(new Robot_Location_bean(33587, 32367, 4));
		화둥3.add(new Robot_Location_bean(33576, 32389, 4));
		화둥3.add(new Robot_Location_bean(33588, 32401, 4));
		화둥3.add(new Robot_Location_bean(33601, 32407, 4));
		화둥3.add(new Robot_Location_bean(33613, 32402, 4));
		화둥3.add(new Robot_Location_bean(33616, 32395, 4));
		화둥3.add(new Robot_Location_bean(33615, 32381, 4));
		화둥3.add(new Robot_Location_bean(33628, 32378, 4));
		화둥3.add(new Robot_Location_bean(33640, 32382, 4));
		화둥3.add(new Robot_Location_bean(33650, 32381, 4));
		화둥3.add(new Robot_Location_bean(33661, 32377, 4));
		화둥3.add(new Robot_Location_bean(33659, 32364, 4));
		화둥3.add(new Robot_Location_bean(33647, 32363, 4));
		화둥3.add(new Robot_Location_bean(33641, 32352, 4));
		화둥3.add(new Robot_Location_bean(33653, 32345, 4));

		화둥4.add(new Robot_Location_bean(33662, 32340, 4));
		화둥4.add(new Robot_Location_bean(33650, 32346, 4));
		화둥4.add(new Robot_Location_bean(33641, 32351, 4));
		화둥4.add(new Robot_Location_bean(33651, 32365, 4));
		화둥4.add(new Robot_Location_bean(33660, 32369, 4));
		화둥4.add(new Robot_Location_bean(33659, 32380, 4));
		화둥4.add(new Robot_Location_bean(33645, 32383, 4));
		화둥4.add(new Robot_Location_bean(33636, 32382, 4));
		화둥4.add(new Robot_Location_bean(33625, 32379, 4));
		화둥4.add(new Robot_Location_bean(33614, 32385, 4));
		화둥4.add(new Robot_Location_bean(33612, 32398, 4));
		화둥4.add(new Robot_Location_bean(33598, 32407, 4));
		화둥4.add(new Robot_Location_bean(33587, 32400, 4));
		화둥4.add(new Robot_Location_bean(33577, 32393, 4));
		화둥4.add(new Robot_Location_bean(33581, 32376, 4));
		화둥4.add(new Robot_Location_bean(33587, 32358, 4));
		화둥4.add(new Robot_Location_bean(33582, 32347, 4));
		화둥4.add(new Robot_Location_bean(33574, 32341, 4));
		화둥4.add(new Robot_Location_bean(33562, 32331, 4));
		화둥4.add(new Robot_Location_bean(33554, 32322, 4));
		화둥4.add(new Robot_Location_bean(33549, 32312, 4));
		화둥4.add(new Robot_Location_bean(33561, 32300, 4));
		화둥4.add(new Robot_Location_bean(33560, 32286, 4));
		화둥4.add(new Robot_Location_bean(33562, 32273, 4));
		화둥4.add(new Robot_Location_bean(33560, 32261, 4));
		화둥4.add(new Robot_Location_bean(33575, 32255, 4));
		화둥4.add(new Robot_Location_bean(33585, 32270, 4));
		화둥4.add(new Robot_Location_bean(33596, 32274, 4));
		화둥4.add(new Robot_Location_bean(33607, 32278, 4));
		화둥4.add(new Robot_Location_bean(33616, 32280, 4));
		화둥4.add(new Robot_Location_bean(33621, 32267, 4));
		화둥4.add(new Robot_Location_bean(33618, 32256, 4));
		화둥4.add(new Robot_Location_bean(33611, 32248, 4));
		화둥4.add(new Robot_Location_bean(33615, 32236, 4));
		화둥4.add(new Robot_Location_bean(33625, 32226, 4));
		화둥4.add(new Robot_Location_bean(33635, 32231, 4));
		화둥4.add(new Robot_Location_bean(33638, 32242, 4));
		화둥4.add(new Robot_Location_bean(33638, 32254, 4));
		화둥4.add(new Robot_Location_bean(33646, 32266, 4));
		화둥4.add(new Robot_Location_bean(33659, 32265, 4));
		화둥4.add(new Robot_Location_bean(33652, 32243, 4));
		화둥4.add(new Robot_Location_bean(33656, 32235, 4));
		화둥4.add(new Robot_Location_bean(33668, 32229, 4));
		화둥4.add(new Robot_Location_bean(33675, 32237, 4));
		화둥4.add(new Robot_Location_bean(33683, 32247, 4));
		화둥4.add(new Robot_Location_bean(33684, 32263, 4));
		화둥4.add(new Robot_Location_bean(33687, 32272, 4));
		화둥4.add(new Robot_Location_bean(33687, 32282, 4));
		화둥4.add(new Robot_Location_bean(33695, 32287, 4));
		화둥4.add(new Robot_Location_bean(33704, 32295, 4));
		화둥4.add(new Robot_Location_bean(33705, 32301, 4));
		화둥4.add(new Robot_Location_bean(33715, 32299, 4));
		화둥4.add(new Robot_Location_bean(33718, 32281, 4));
		화둥4.add(new Robot_Location_bean(33715, 32302, 4));
		화둥4.add(new Robot_Location_bean(33708, 32313, 4));
		화둥4.add(new Robot_Location_bean(33703, 32330, 4));
		화둥4.add(new Robot_Location_bean(33694, 32331, 4));
		화둥4.add(new Robot_Location_bean(33684, 32322, 4));
		화둥4.add(new Robot_Location_bean(33674, 32333, 4));
		화둥4.add(new Robot_Location_bean(33664, 32336, 4));

		화둥5.add(new Robot_Location_bean(33738, 32396, 4));
		화둥5.add(new Robot_Location_bean(33731, 32406, 4));
		화둥5.add(new Robot_Location_bean(33722, 32416, 4));
		화둥5.add(new Robot_Location_bean(33710, 32418, 4));
		화둥5.add(new Robot_Location_bean(33700, 32415, 4));
		화둥5.add(new Robot_Location_bean(33686, 32423, 4));
		화둥5.add(new Robot_Location_bean(33676, 32423, 4));
		화둥5.add(new Robot_Location_bean(33663, 32418, 4));
		화둥5.add(new Robot_Location_bean(33651, 32414, 4));
		화둥5.add(new Robot_Location_bean(33637, 32414, 4));
		화둥5.add(new Robot_Location_bean(33627, 32406, 4));
		화둥5.add(new Robot_Location_bean(33619, 32397, 4));
		화둥5.add(new Robot_Location_bean(33613, 32387, 4));
		화둥5.add(new Robot_Location_bean(33614, 32380, 4));
		화둥5.add(new Robot_Location_bean(33625, 32379, 4));
		화둥5.add(new Robot_Location_bean(33634, 32380, 4));
		화둥5.add(new Robot_Location_bean(33642, 32381, 4));
		화둥5.add(new Robot_Location_bean(33654, 32382, 4));
		화둥5.add(new Robot_Location_bean(33661, 32373, 4));
		화둥5.add(new Robot_Location_bean(33655, 32364, 4));
		화둥5.add(new Robot_Location_bean(33644, 32360, 4));
		화둥5.add(new Robot_Location_bean(33641, 32352, 4));
		화둥5.add(new Robot_Location_bean(33652, 32345, 4));
		화둥5.add(new Robot_Location_bean(33664, 32335, 4));
		화둥5.add(new Robot_Location_bean(33661, 32328, 4));
		화둥5.add(new Robot_Location_bean(33659, 32321, 4));
		화둥5.add(new Robot_Location_bean(33655, 32311, 4));
		화둥5.add(new Robot_Location_bean(33646, 32303, 4));
		화둥5.add(new Robot_Location_bean(33656, 32296, 4));
		화둥5.add(new Robot_Location_bean(33666, 32299, 4));
		화둥5.add(new Robot_Location_bean(33672, 32303, 4));
		화둥5.add(new Robot_Location_bean(33678, 32309, 4));
		화둥5.add(new Robot_Location_bean(33682, 32317, 4));
		화둥5.add(new Robot_Location_bean(33689, 32319, 4));
		화둥5.add(new Robot_Location_bean(33692, 32329, 4));
		화둥5.add(new Robot_Location_bean(33705, 32329, 4));
		화둥5.add(new Robot_Location_bean(33706, 32316, 4));
		화둥5.add(new Robot_Location_bean(33712, 32305, 4));
		화둥5.add(new Robot_Location_bean(33716, 32290, 4));
		화둥5.add(new Robot_Location_bean(33720, 32282, 4));
		화둥5.add(new Robot_Location_bean(33732, 32281, 4));
		화둥5.add(new Robot_Location_bean(33743, 32286, 4));
		화둥5.add(new Robot_Location_bean(33750, 32292, 4));
		화둥5.add(new Robot_Location_bean(33760, 32292, 4));
		화둥5.add(new Robot_Location_bean(33763, 32302, 4));
		화둥5.add(new Robot_Location_bean(33765, 32313, 4));
		화둥5.add(new Robot_Location_bean(33768, 32322, 4));
		화둥5.add(new Robot_Location_bean(33766, 32335, 4));
		화둥5.add(new Robot_Location_bean(33763, 32350, 4));
		화둥5.add(new Robot_Location_bean(33760, 32361, 4));
		화둥5.add(new Robot_Location_bean(33755, 32374, 4));
		화둥5.add(new Robot_Location_bean(33758, 32387, 4));
		화둥5.add(new Robot_Location_bean(33752, 32396, 4));
		화둥5.add(new Robot_Location_bean(33742, 32401, 4));

		지하침공로1층.add(new Robot_Location_bean(32761, 32825, 307));
		지하침공로1층.add(new Robot_Location_bean(32765, 32833, 307));
		지하침공로1층.add(new Robot_Location_bean(32766, 32845, 307));
		지하침공로1층.add(new Robot_Location_bean(32771, 32856, 307));
		지하침공로1층.add(new Robot_Location_bean(32785, 32854, 307));
		지하침공로1층.add(new Robot_Location_bean(32799, 32844, 307));
		지하침공로1층.add(new Robot_Location_bean(32814, 32839, 307));
		지하침공로1층.add(new Robot_Location_bean(32821, 32824, 307));
		지하침공로1층.add(new Robot_Location_bean(32835, 32815, 307));
		지하침공로1층.add(new Robot_Location_bean(32850, 32813, 307));
		지하침공로1층.add(new Robot_Location_bean(32862, 32803, 307));
		지하침공로1층.add(new Robot_Location_bean(32877, 32805, 307));
		지하침공로1층.add(new Robot_Location_bean(32893, 32805, 307));
		지하침공로1층.add(new Robot_Location_bean(32907, 32811, 307));
		지하침공로1층.add(new Robot_Location_bean(32918, 32824, 307));
		지하침공로1층.add(new Robot_Location_bean(32925, 32840, 307));
		지하침공로1층.add(new Robot_Location_bean(32925, 32856, 307));
		지하침공로1층.add(new Robot_Location_bean(32922, 32872, 307));
		지하침공로1층.add(new Robot_Location_bean(32921, 32888, 307));
		지하침공로1층.add(new Robot_Location_bean(32922, 32904, 307));
		지하침공로1층.add(new Robot_Location_bean(32913, 32913, 307));
		지하침공로1층.add(new Robot_Location_bean(32900, 32916, 307));
		지하침공로1층.add(new Robot_Location_bean(32891, 32916, 307));
		지하침공로1층.add(new Robot_Location_bean(32877, 32918, 307));
		지하침공로1층.add(new Robot_Location_bean(32868, 32915, 307));
		지하침공로1층.add(new Robot_Location_bean(32868, 32905, 307));
		지하침공로1층.add(new Robot_Location_bean(32859, 32899, 307));
		지하침공로1층.add(new Robot_Location_bean(32852, 32894, 307));
		지하침공로1층.add(new Robot_Location_bean(32843, 32893, 307));
		지하침공로1층.add(new Robot_Location_bean(32834, 32891, 307));
		지하침공로1층.add(new Robot_Location_bean(32825, 32892, 307));
		지하침공로1층.add(new Robot_Location_bean(32817, 32891, 307));
		지하침공로1층.add(new Robot_Location_bean(32810, 32891, 307));
		지하침공로1층.add(new Robot_Location_bean(32800, 32883, 307));
		지하침공로1층.add(new Robot_Location_bean(32798, 32875, 307));
		지하침공로1층.add(new Robot_Location_bean(32796, 32868, 307));
		지하침공로1층.add(new Robot_Location_bean(32786, 32869, 307));
		지하침공로1층.add(new Robot_Location_bean(32776, 32872, 307));
		지하침공로1층.add(new Robot_Location_bean(32766, 32867, 307));
		지하침공로1층.add(new Robot_Location_bean(32758, 32860, 307));
		지하침공로1층.add(new Robot_Location_bean(32752, 32858, 307));
		지하침공로1층.add(new Robot_Location_bean(32743, 32858, 307));
		지하침공로1층.add(new Robot_Location_bean(32748, 32847, 307));
		지하침공로1층.add(new Robot_Location_bean(32756, 32839, 307));
		지하침공로1층.add(new Robot_Location_bean(32759, 32831, 307));

		지하침공로1층2.add(new Robot_Location_bean(32761, 32825, 307));
		지하침공로1층2.add(new Robot_Location_bean(32761, 32835, 307));
		지하침공로1층2.add(new Robot_Location_bean(32757, 32845, 307));
		지하침공로1층2.add(new Robot_Location_bean(32762, 32854, 307));
		지하침공로1층2.add(new Robot_Location_bean(32767, 32861, 307));
		지하침공로1층2.add(new Robot_Location_bean(32769, 32872, 307));
		지하침공로1층2.add(new Robot_Location_bean(32779, 32872, 307));
		지하침공로1층2.add(new Robot_Location_bean(32794, 32868, 307));
		지하침공로1층2.add(new Robot_Location_bean(32800, 32869, 307));
		지하침공로1층2.add(new Robot_Location_bean(32815, 32865, 307));
		지하침공로1층2.add(new Robot_Location_bean(32827, 32867, 307));
		지하침공로1층2.add(new Robot_Location_bean(32837, 32868, 307));
		지하침공로1층2.add(new Robot_Location_bean(32848, 32862, 307));
		지하침공로1층2.add(new Robot_Location_bean(32854, 32862, 307));
		지하침공로1층2.add(new Robot_Location_bean(32862, 32861, 307));
		지하침공로1층2.add(new Robot_Location_bean(32867, 32863, 307));
		지하침공로1층2.add(new Robot_Location_bean(32880, 32862, 307));
		지하침공로1층2.add(new Robot_Location_bean(32890, 32858, 307));
		지하침공로1층2.add(new Robot_Location_bean(32900, 32851, 307));
		지하침공로1층2.add(new Robot_Location_bean(32911, 32847, 307));
		지하침공로1층2.add(new Robot_Location_bean(32914, 32857, 307));
		지하침공로1층2.add(new Robot_Location_bean(32921, 32860, 307));
		지하침공로1층2.add(new Robot_Location_bean(32932, 32856, 307));
		지하침공로1층2.add(new Robot_Location_bean(32925, 32844, 307));
		지하침공로1층2.add(new Robot_Location_bean(32925, 32834, 307));
		지하침공로1층2.add(new Robot_Location_bean(32925, 32826, 307));
		지하침공로1층2.add(new Robot_Location_bean(32917, 32822, 307));
		지하침공로1층2.add(new Robot_Location_bean(32906, 32819, 307));
		지하침공로1층2.add(new Robot_Location_bean(32908, 32812, 307));
		지하침공로1층2.add(new Robot_Location_bean(32904, 32804, 307));
		지하침공로1층2.add(new Robot_Location_bean(32896, 32804, 307));
		지하침공로1층2.add(new Robot_Location_bean(32889, 32806, 307));
		지하침공로1층2.add(new Robot_Location_bean(32881, 32806, 307));
		지하침공로1층2.add(new Robot_Location_bean(32870, 32802, 307));
		지하침공로1층2.add(new Robot_Location_bean(32857, 32805, 307));
		지하침공로1층2.add(new Robot_Location_bean(32847, 32806, 307));
		지하침공로1층2.add(new Robot_Location_bean(32837, 32811, 307));
		지하침공로1층2.add(new Robot_Location_bean(32833, 32818, 307));
		지하침공로1층2.add(new Robot_Location_bean(32822, 32824, 307));
		지하침공로1층2.add(new Robot_Location_bean(32816, 32833, 307));
		지하침공로1층2.add(new Robot_Location_bean(32809, 32841, 307));
		지하침공로1층2.add(new Robot_Location_bean(32797, 32846, 307));
		지하침공로1층2.add(new Robot_Location_bean(32787, 32852, 307));
		지하침공로1층2.add(new Robot_Location_bean(32778, 32858, 307));
		지하침공로1층2.add(new Robot_Location_bean(32771, 32856, 307));
		지하침공로1층2.add(new Robot_Location_bean(32765, 32846, 307));
		지하침공로1층2.add(new Robot_Location_bean(32763, 32835, 307));

		지하침공로2층.add(new Robot_Location_bean(32748, 32827, 308));
		지하침공로2층.add(new Robot_Location_bean(32748, 32833, 308));
		지하침공로2층.add(new Robot_Location_bean(32756, 32840, 308));
		지하침공로2층.add(new Robot_Location_bean(32752, 32851, 308));
		지하침공로2층.add(new Robot_Location_bean(32763, 32856, 308));
		지하침공로2층.add(new Robot_Location_bean(32778, 32854, 308));
		지하침공로2층.add(new Robot_Location_bean(32786, 32848, 308));
		지하침공로2층.add(new Robot_Location_bean(32790, 32836, 308));
		지하침공로2층.add(new Robot_Location_bean(32795, 32822, 308));
		지하침공로2층.add(new Robot_Location_bean(32806, 32815, 308));
		지하침공로2층.add(new Robot_Location_bean(32818, 32808, 308));
		지하침공로2층.add(new Robot_Location_bean(32825, 32810, 308));
		지하침공로2층.add(new Robot_Location_bean(32835, 32809, 308));
		지하침공로2층.add(new Robot_Location_bean(32845, 32806, 308));
		지하침공로2층.add(new Robot_Location_bean(32855, 32798, 308));
		지하침공로2층.add(new Robot_Location_bean(32864, 32803, 308));
		지하침공로2층.add(new Robot_Location_bean(32875, 32804, 308));
		지하침공로2층.add(new Robot_Location_bean(32876, 32813, 308));
		지하침공로2층.add(new Robot_Location_bean(32879, 32816, 308));
		지하침공로2층.add(new Robot_Location_bean(32891, 32813, 308));
		지하침공로2층.add(new Robot_Location_bean(32900, 32812, 308));
		지하침공로2층.add(new Robot_Location_bean(32911, 32809, 308));
		지하침공로2층.add(new Robot_Location_bean(32919, 32809, 308));
		지하침공로2층.add(new Robot_Location_bean(32925, 32809, 308));
		지하침공로2층.add(new Robot_Location_bean(32935, 32809, 308));
		지하침공로2층.add(new Robot_Location_bean(32942, 32807, 308));
		지하침공로2층.add(new Robot_Location_bean(32950, 32810, 308));
		지하침공로2층.add(new Robot_Location_bean(32961, 32806, 308));
		지하침공로2층.add(new Robot_Location_bean(32968, 32808, 308));
		지하침공로2층.add(new Robot_Location_bean(32973, 32815, 308));
		지하침공로2층.add(new Robot_Location_bean(32982, 32818, 308));
		지하침공로2층.add(new Robot_Location_bean(32987, 32825, 308));
		지하침공로2층.add(new Robot_Location_bean(32992, 32831, 308));
		지하침공로2층.add(new Robot_Location_bean(32986, 32842, 308));
		지하침공로2층.add(new Robot_Location_bean(32988, 32851, 308));
		지하침공로2층.add(new Robot_Location_bean(32988, 32859, 308));
		지하침공로2층.add(new Robot_Location_bean(32984, 32866, 308));
		지하침공로2층.add(new Robot_Location_bean(32990, 32873, 308));
		지하침공로2층.add(new Robot_Location_bean(32986, 32881, 308));
		지하침공로2층.add(new Robot_Location_bean(32987, 32889, 308));
		지하침공로2층.add(new Robot_Location_bean(32980, 32898, 308));
		지하침공로2층.add(new Robot_Location_bean(32975, 32907, 308));
		지하침공로2층.add(new Robot_Location_bean(32965, 32914, 308));
		지하침공로2층.add(new Robot_Location_bean(32957, 32918, 308));
		지하침공로2층.add(new Robot_Location_bean(32947, 32924, 308));
		지하침공로2층.add(new Robot_Location_bean(32938, 32926, 308));
		지하침공로2층.add(new Robot_Location_bean(32928, 32922, 308));
		지하침공로2층.add(new Robot_Location_bean(32922, 32912, 308));
		지하침공로2층.add(new Robot_Location_bean(32912, 32914, 308));
		지하침공로2층.add(new Robot_Location_bean(32902, 32919, 308));
		지하침공로2층.add(new Robot_Location_bean(32892, 32928, 308));
		지하침공로2층.add(new Robot_Location_bean(32880, 32933, 308));
		지하침공로2층.add(new Robot_Location_bean(32872, 32927, 308));
		지하침공로2층.add(new Robot_Location_bean(32859, 32928, 308));
		지하침공로2층.add(new Robot_Location_bean(32848, 32929, 308));
		지하침공로2층.add(new Robot_Location_bean(32838, 32922, 308));
		지하침공로2층.add(new Robot_Location_bean(32827, 32918, 308));
		지하침공로2층.add(new Robot_Location_bean(32814, 32920, 308));
		지하침공로2층.add(new Robot_Location_bean(32804, 32917, 308));
		지하침공로2층.add(new Robot_Location_bean(32796, 32911, 308));
		지하침공로2층.add(new Robot_Location_bean(32787, 32894, 308));
		지하침공로2층.add(new Robot_Location_bean(32779, 32886, 308));
		지하침공로2층.add(new Robot_Location_bean(32778, 32874, 308));
		지하침공로2층.add(new Robot_Location_bean(32781, 32863, 308));
		지하침공로2층.add(new Robot_Location_bean(32764, 32865, 308));
		지하침공로2층.add(new Robot_Location_bean(32757, 32870, 308));
		지하침공로2층.add(new Robot_Location_bean(32748, 32874, 308));
		지하침공로2층.add(new Robot_Location_bean(32739, 32869, 308));
		지하침공로2층.add(new Robot_Location_bean(32739, 32859, 308));
		지하침공로2층.add(new Robot_Location_bean(32738, 32852, 308));
		지하침공로2층.add(new Robot_Location_bean(32739, 32842, 308));
		지하침공로2층.add(new Robot_Location_bean(32746, 32835, 308));

		지하침공로2층2.add(new Robot_Location_bean(32748, 32827, 308));
		지하침공로2층2.add(new Robot_Location_bean(32748, 32838, 308));
		지하침공로2층2.add(new Robot_Location_bean(32739, 32842, 308));
		지하침공로2층2.add(new Robot_Location_bean(32734, 32851, 308));
		지하침공로2층2.add(new Robot_Location_bean(32739, 32859, 308));
		지하침공로2층2.add(new Robot_Location_bean(32741, 32869, 308));
		지하침공로2층2.add(new Robot_Location_bean(32752, 32867, 308));
		지하침공로2층2.add(new Robot_Location_bean(32761, 32866, 308));
		지하침공로2층2.add(new Robot_Location_bean(32776, 32863, 308));
		지하침공로2층2.add(new Robot_Location_bean(32782, 32863, 308));
		지하침공로2층2.add(new Robot_Location_bean(32792, 32853, 308));
		지하침공로2층2.add(new Robot_Location_bean(32807, 32848, 308));
		지하침공로2층2.add(new Robot_Location_bean(32817, 32846, 308));
		지하침공로2층2.add(new Robot_Location_bean(32827, 32851, 308));
		지하침공로2층2.add(new Robot_Location_bean(32838, 32851, 308));
		지하침공로2층2.add(new Robot_Location_bean(32846, 32851, 308));
		지하침공로2층2.add(new Robot_Location_bean(32854, 32851, 308));
		지하침공로2층2.add(new Robot_Location_bean(32866, 32849, 308));
		지하침공로2층2.add(new Robot_Location_bean(32871, 32850, 308));
		지하침공로2층2.add(new Robot_Location_bean(32870, 32860, 308));
		지하침공로2층2.add(new Robot_Location_bean(32872, 32866, 308));
		지하침공로2층2.add(new Robot_Location_bean(32874, 32875, 308));
		지하침공로2층2.add(new Robot_Location_bean(32871, 32884, 308));
		지하침공로2층2.add(new Robot_Location_bean(32868, 32896, 308));
		지하침공로2층2.add(new Robot_Location_bean(32878, 32898, 308));
		지하침공로2층2.add(new Robot_Location_bean(32888, 32890, 308));
		지하침공로2층2.add(new Robot_Location_bean(32895, 32879, 308));
		지하침공로2층2.add(new Robot_Location_bean(32904, 32869, 308));
		지하침공로2층2.add(new Robot_Location_bean(32916, 32868, 308));
		지하침공로2층2.add(new Robot_Location_bean(32924, 32859, 308));
		지하침공로2층2.add(new Robot_Location_bean(32919, 32848, 308));
		지하침공로2층2.add(new Robot_Location_bean(32919, 32835, 308));
		지하침공로2층2.add(new Robot_Location_bean(32930, 32832, 308));
		지하침공로2층2.add(new Robot_Location_bean(32939, 32832, 308));
		지하침공로2층2.add(new Robot_Location_bean(32946, 32838, 308));
		지하침공로2층2.add(new Robot_Location_bean(32957, 32844, 308));
		지하침공로2층2.add(new Robot_Location_bean(32964, 32850, 308));
		지하침공로2층2.add(new Robot_Location_bean(32967, 32840, 308));
		지하침공로2층2.add(new Robot_Location_bean(32974, 32831, 308));
		지하침공로2층2.add(new Robot_Location_bean(32986, 32826, 308));
		지하침공로2층2.add(new Robot_Location_bean(32992, 32833, 308));
		지하침공로2층2.add(new Robot_Location_bean(32990, 32839, 308));
		지하침공로2층2.add(new Robot_Location_bean(32992, 32846, 308));
		지하침공로2층2.add(new Robot_Location_bean(32990, 32856, 308));
		지하침공로2층2.add(new Robot_Location_bean(32981, 32859, 308));
		지하침공로2층2.add(new Robot_Location_bean(32972, 32866, 308));
		지하침공로2층2.add(new Robot_Location_bean(32971, 32881, 308));
		지하침공로2층2.add(new Robot_Location_bean(32966, 32868, 308));
		지하침공로2층2.add(new Robot_Location_bean(32957, 32860, 308));
		지하침공로2층2.add(new Robot_Location_bean(32948, 32863, 308));
		지하침공로2층2.add(new Robot_Location_bean(32943, 32871, 308));
		지하침공로2층2.add(new Robot_Location_bean(32943, 32881, 308));
		지하침공로2층2.add(new Robot_Location_bean(32939, 32893, 308));
		지하침공로2층2.add(new Robot_Location_bean(32934, 32901, 308));
		지하침공로2층2.add(new Robot_Location_bean(32931, 32890, 308));
		지하침공로2층2.add(new Robot_Location_bean(32925, 32884, 308));
		지하침공로2층2.add(new Robot_Location_bean(32922, 32874, 308));
		지하침공로2층2.add(new Robot_Location_bean(32914, 32867, 308));
		지하침공로2층2.add(new Robot_Location_bean(32908, 32867, 308));
		지하침공로2층2.add(new Robot_Location_bean(32900, 32874, 308));
		지하침공로2층2.add(new Robot_Location_bean(32892, 32882, 308));
		지하침공로2층2.add(new Robot_Location_bean(32886, 32891, 308));
		지하침공로2층2.add(new Robot_Location_bean(32875, 32897, 308));
		지하침공로2층2.add(new Robot_Location_bean(32868, 32899, 308));
		지하침공로2층2.add(new Robot_Location_bean(32869, 32890, 308));
		지하침공로2층2.add(new Robot_Location_bean(32872, 32880, 308));
		지하침공로2층2.add(new Robot_Location_bean(32875, 32871, 308));
		지하침공로2층2.add(new Robot_Location_bean(32872, 32862, 308));
		지하침공로2층2.add(new Robot_Location_bean(32866, 32857, 308));
		지하침공로2층2.add(new Robot_Location_bean(32857, 32858, 308));
		지하침공로2층2.add(new Robot_Location_bean(32850, 32859, 308));
		지하침공로2층2.add(new Robot_Location_bean(32841, 32859, 308));
		지하침공로2층2.add(new Robot_Location_bean(32829, 32859, 308));
		지하침공로2층2.add(new Robot_Location_bean(32819, 32866, 308));
		지하침공로2층2.add(new Robot_Location_bean(32811, 32875, 308));
		지하침공로2층2.add(new Robot_Location_bean(32807, 32888, 308));
		지하침공로2층2.add(new Robot_Location_bean(32797, 32896, 308));
		지하침공로2층2.add(new Robot_Location_bean(32785, 32892, 308));
		지하침공로2층2.add(new Robot_Location_bean(32777, 32884, 308));
		지하침공로2층2.add(new Robot_Location_bean(32780, 32869, 308));
		지하침공로2층2.add(new Robot_Location_bean(32782, 32856, 308));
		지하침공로2층2.add(new Robot_Location_bean(32774, 32855, 308));
		지하침공로2층2.add(new Robot_Location_bean(32764, 32857, 308));
		지하침공로2층2.add(new Robot_Location_bean(32756, 32851, 308));
		지하침공로2층2.add(new Robot_Location_bean(32757, 32838, 308));
		지하침공로2층2.add(new Robot_Location_bean(32749, 32835, 308));

		지하침공로3층.add(new Robot_Location_bean(32758, 32840, 309));
		지하침공로3층.add(new Robot_Location_bean(32767, 32846, 309));
		지하침공로3층.add(new Robot_Location_bean(32776, 32842, 309));
		지하침공로3층.add(new Robot_Location_bean(32783, 32845, 309));
		지하침공로3층.add(new Robot_Location_bean(32794, 32840, 309));
		지하침공로3층.add(new Robot_Location_bean(32797, 32827, 309));
		지하침공로3층.add(new Robot_Location_bean(32798, 32816, 309));
		지하침공로3층.add(new Robot_Location_bean(32796, 32807, 309));
		지하침공로3층.add(new Robot_Location_bean(32806, 32805, 309));
		지하침공로3층.add(new Robot_Location_bean(32812, 32809, 309));
		지하침공로3층.add(new Robot_Location_bean(32818, 32813, 309));
		지하침공로3층.add(new Robot_Location_bean(32827, 32818, 309));
		지하침공로3층.add(new Robot_Location_bean(32836, 32811, 309));
		지하침공로3층.add(new Robot_Location_bean(32844, 32806, 309));
		지하침공로3층.add(new Robot_Location_bean(32855, 32803, 309));
		지하침공로3층.add(new Robot_Location_bean(32855, 32816, 309));
		지하침공로3층.add(new Robot_Location_bean(32858, 32826, 309));
		지하침공로3층.add(new Robot_Location_bean(32868, 32831, 309));
		지하침공로3층.add(new Robot_Location_bean(32880, 32824, 309));
		지하침공로3층.add(new Robot_Location_bean(32890, 32820, 309));
		지하침공로3층.add(new Robot_Location_bean(32898, 32827, 309));
		지하침공로3층.add(new Robot_Location_bean(32907, 32825, 309));
		지하침공로3층.add(new Robot_Location_bean(32917, 32814, 309));
		지하침공로3층.add(new Robot_Location_bean(32923, 32806, 309));
		지하침공로3층.add(new Robot_Location_bean(32933, 32809, 309));
		지하침공로3층.add(new Robot_Location_bean(32944, 32807, 309));
		지하침공로3층.add(new Robot_Location_bean(32955, 32804, 309));
		지하침공로3층.add(new Robot_Location_bean(32969, 32804, 309));
		지하침공로3층.add(new Robot_Location_bean(32978, 32805, 309));
		지하침공로3층.add(new Robot_Location_bean(32988, 32802, 309));
		지하침공로3층.add(new Robot_Location_bean(33000, 32801, 309));
		지하침공로3층.add(new Robot_Location_bean(33007, 32805, 309));
		지하침공로3층.add(new Robot_Location_bean(33018, 32808, 309));
		지하침공로3층.add(new Robot_Location_bean(33031, 32804, 309));
		지하침공로3층.add(new Robot_Location_bean(33038, 32812, 309));
		지하침공로3층.add(new Robot_Location_bean(33043, 32820, 309));
		지하침공로3층.add(new Robot_Location_bean(33043, 32830, 309));
		지하침공로3층.add(new Robot_Location_bean(33043, 32842, 309));
		지하침공로3층.add(new Robot_Location_bean(33046, 32850, 309));
		지하침공로3층.add(new Robot_Location_bean(33049, 32859, 309));
		지하침공로3층.add(new Robot_Location_bean(33051, 32869, 309));
		지하침공로3층.add(new Robot_Location_bean(33048, 32881, 309));
		지하침공로3층.add(new Robot_Location_bean(33046, 32893, 309));
		지하침공로3층.add(new Robot_Location_bean(33035, 32899, 309));
		지하침공로3층.add(new Robot_Location_bean(33026, 32906, 309));
		지하침공로3층.add(new Robot_Location_bean(33015, 32908, 309));
		지하침공로3층.add(new Robot_Location_bean(33001, 32908, 309));
		지하침공로3층.add(new Robot_Location_bean(32991, 32902, 309));
		지하침공로3층.add(new Robot_Location_bean(32991, 32890, 309));
		지하침공로3층.add(new Robot_Location_bean(32986, 32882, 309));
		지하침공로3층.add(new Robot_Location_bean(32978, 32877, 309));
		지하침공로3층.add(new Robot_Location_bean(32968, 32884, 309));
		지하침공로3층.add(new Robot_Location_bean(32958, 32886, 309));
		지하침공로3층.add(new Robot_Location_bean(32949, 32883, 309));
		지하침공로3층.add(new Robot_Location_bean(32953, 32873, 309));
		지하침공로3층.add(new Robot_Location_bean(32957, 32864, 309));
		지하침공로3층.add(new Robot_Location_bean(32951, 32855, 309));
		지하침공로3층.add(new Robot_Location_bean(32953, 32842, 309));
		지하침공로3층.add(new Robot_Location_bean(32942, 32846, 309));
		지하침공로3층.add(new Robot_Location_bean(32934, 32857, 309));
		지하침공로3층.add(new Robot_Location_bean(32929, 32866, 309));
		지하침공로3층.add(new Robot_Location_bean(32920, 32860, 309));
		지하침공로3층.add(new Robot_Location_bean(32915, 32851, 309));
		지하침공로3층.add(new Robot_Location_bean(32902, 32850, 309));
		지하침공로3층.add(new Robot_Location_bean(32891, 32852, 309));
		지하침공로3층.add(new Robot_Location_bean(32881, 32849, 309));
		지하침공로3층.add(new Robot_Location_bean(32877, 32860, 309));
		지하침공로3층.add(new Robot_Location_bean(32869, 32870, 309));
		지하침공로3층.add(new Robot_Location_bean(32857, 32875, 309));
		지하침공로3층.add(new Robot_Location_bean(32846, 32872, 309));
		지하침공로3층.add(new Robot_Location_bean(32836, 32873, 309));
		지하침공로3층.add(new Robot_Location_bean(32829, 32879, 309));
		지하침공로3층.add(new Robot_Location_bean(32821, 32885, 309));
		지하침공로3층.add(new Robot_Location_bean(32815, 32889, 309));
		지하침공로3층.add(new Robot_Location_bean(32809, 32899, 309));
		지하침공로3층.add(new Robot_Location_bean(32799, 32907, 309));
		지하침공로3층.add(new Robot_Location_bean(32790, 32910, 309));
		지하침공로3층.add(new Robot_Location_bean(32786, 32903, 309));
		지하침공로3층.add(new Robot_Location_bean(32777, 32911, 309));
		지하침공로3층.add(new Robot_Location_bean(32770, 32915, 309));
		지하침공로3층.add(new Robot_Location_bean(32759, 32916, 309));
		지하침공로3층.add(new Robot_Location_bean(32752, 32911, 309));
		지하침공로3층.add(new Robot_Location_bean(32753, 32898, 309));
		지하침공로3층.add(new Robot_Location_bean(32752, 32885, 309));
		지하침공로3층.add(new Robot_Location_bean(32751, 32877, 309));
		지하침공로3층.add(new Robot_Location_bean(32741, 32871, 309));
		지하침공로3층.add(new Robot_Location_bean(32736, 32864, 309));
		지하침공로3층.add(new Robot_Location_bean(32741, 32852, 309));
		지하침공로3층.add(new Robot_Location_bean(32749, 32845, 309));

		지하침공로3층2.add(new Robot_Location_bean(32758, 32840, 309));
		지하침공로3층2.add(new Robot_Location_bean(32750, 32843, 309));
		지하침공로3층2.add(new Robot_Location_bean(32744, 32853, 309));
		지하침공로3층2.add(new Robot_Location_bean(32748, 32859, 309));
		지하침공로3층2.add(new Robot_Location_bean(32744, 32869, 309));
		지하침공로3층2.add(new Robot_Location_bean(32746, 32879, 309));
		지하침공로3층2.add(new Robot_Location_bean(32752, 32885, 309));
		지하침공로3층2.add(new Robot_Location_bean(32757, 32890, 309));
		지하침공로3층2.add(new Robot_Location_bean(32754, 32896, 309));
		지하침공로3층2.add(new Robot_Location_bean(32752, 32908, 309));
		지하침공로3층2.add(new Robot_Location_bean(32756, 32915, 309));
		지하침공로3층2.add(new Robot_Location_bean(32769, 32916, 309));
		지하침공로3층2.add(new Robot_Location_bean(32781, 32914, 309));
		지하침공로3층2.add(new Robot_Location_bean(32792, 32909, 309));
		지하침공로3층2.add(new Robot_Location_bean(32801, 32906, 309));
		지하침공로3층2.add(new Robot_Location_bean(32811, 32899, 309));
		지하침공로3층2.add(new Robot_Location_bean(32824, 32900, 309));
		지하침공로3층2.add(new Robot_Location_bean(32823, 32908, 309));
		지하침공로3층2.add(new Robot_Location_bean(32821, 32921, 309));
		지하침공로3층2.add(new Robot_Location_bean(32827, 32927, 309));
		지하침공로3층2.add(new Robot_Location_bean(32834, 32922, 309));
		지하침공로3층2.add(new Robot_Location_bean(32845, 32916, 309));
		지하침공로3층2.add(new Robot_Location_bean(32852, 32917, 309));
		지하침공로3층2.add(new Robot_Location_bean(32857, 32924, 309));
		지하침공로3층2.add(new Robot_Location_bean(32866, 32925, 309));
		지하침공로3층2.add(new Robot_Location_bean(32875, 32918, 309));
		지하침공로3층2.add(new Robot_Location_bean(32883, 32911, 309));
		지하침공로3층2.add(new Robot_Location_bean(32889, 32913, 309));
		지하침공로3층2.add(new Robot_Location_bean(32895, 32919, 309));
		지하침공로3층2.add(new Robot_Location_bean(32902, 32924, 309));
		지하침공로3층2.add(new Robot_Location_bean(32912, 32922, 309));
		지하침공로3층2.add(new Robot_Location_bean(32923, 32913, 309));
		지하침공로3층2.add(new Robot_Location_bean(32933, 32912, 309));
		지하침공로3층2.add(new Robot_Location_bean(32945, 32909, 309));
		지하침공로3층2.add(new Robot_Location_bean(32951, 32910, 309));
		지하침공로3층2.add(new Robot_Location_bean(32957, 32915, 309));
		지하침공로3층2.add(new Robot_Location_bean(32960, 32924, 309));
		지하침공로3층2.add(new Robot_Location_bean(32960, 32932, 309));
		지하침공로3층2.add(new Robot_Location_bean(32963, 32938, 309));
		지하침공로3층2.add(new Robot_Location_bean(32972, 32936, 309));
		지하침공로3층2.add(new Robot_Location_bean(32983, 32934, 309));
		지하침공로3층2.add(new Robot_Location_bean(32994, 32931, 309));
		지하침공로3층2.add(new Robot_Location_bean(33006, 32930, 309));
		지하침공로3층2.add(new Robot_Location_bean(33017, 32926, 309));
		지하침공로3층2.add(new Robot_Location_bean(33028, 32927, 309));
		지하침공로3층2.add(new Robot_Location_bean(33032, 32923, 309));
		지하침공로3층2.add(new Robot_Location_bean(33028, 32914, 309));
		지하침공로3층2.add(new Robot_Location_bean(33031, 32901, 309));
		지하침공로3층2.add(new Robot_Location_bean(33029, 32892, 309));
		지하침공로3층2.add(new Robot_Location_bean(33025, 32884, 309));
		지하침공로3층2.add(new Robot_Location_bean(33018, 32875, 309));
		지하침공로3층2.add(new Robot_Location_bean(33020, 32860, 309));
		지하침공로3층2.add(new Robot_Location_bean(33010, 32856, 309));
		지하침공로3층2.add(new Robot_Location_bean(33001, 32850, 309));
		지하침공로3층2.add(new Robot_Location_bean(32997, 32865, 309));
		지하침공로3층2.add(new Robot_Location_bean(32989, 32869, 309));
		지하침공로3층2.add(new Robot_Location_bean(32979, 32875, 309));
		지하침공로3층2.add(new Robot_Location_bean(32970, 32882, 309));
		지하침공로3층2.add(new Robot_Location_bean(32961, 32887, 309));
		지하침공로3층2.add(new Robot_Location_bean(32952, 32887, 309));
		지하침공로3층2.add(new Robot_Location_bean(32951, 32879, 309));
		지하침공로3층2.add(new Robot_Location_bean(32955, 32866, 309));
		지하침공로3층2.add(new Robot_Location_bean(32953, 32855, 309));
		지하침공로3층2.add(new Robot_Location_bean(32954, 32842, 309));
		지하침공로3층2.add(new Robot_Location_bean(32942, 32846, 309));
		지하침공로3층2.add(new Robot_Location_bean(32935, 32855, 309));
		지하침공로3층2.add(new Robot_Location_bean(32929, 32865, 309));
		지하침공로3층2.add(new Robot_Location_bean(32922, 32862, 309));
		지하침공로3층2.add(new Robot_Location_bean(32916, 32854, 309));
		지하침공로3층2.add(new Robot_Location_bean(32915, 32847, 309));
		지하침공로3층2.add(new Robot_Location_bean(32920, 32835, 309));
		지하침공로3층2.add(new Robot_Location_bean(32913, 32829, 309));
		지하침공로3층2.add(new Robot_Location_bean(32902, 32825, 309));
		지하침공로3층2.add(new Robot_Location_bean(32894, 32831, 309));
		지하침공로3층2.add(new Robot_Location_bean(32888, 32841, 309));
		지하침공로3층2.add(new Robot_Location_bean(32881, 32846, 309));
		지하침공로3층2.add(new Robot_Location_bean(32878, 32856, 309));
		지하침공로3층2.add(new Robot_Location_bean(32875, 32864, 309));
		지하침공로3층2.add(new Robot_Location_bean(32867, 32872, 309));
		지하침공로3층2.add(new Robot_Location_bean(32857, 32872, 309));
		지하침공로3층2.add(new Robot_Location_bean(32847, 32872, 309));
		지하침공로3층2.add(new Robot_Location_bean(32833, 32875, 309));
		지하침공로3층2.add(new Robot_Location_bean(32823, 32871, 309));
		지하침공로3층2.add(new Robot_Location_bean(32812, 32868, 309));
		지하침공로3층2.add(new Robot_Location_bean(32801, 32874, 309));
		지하침공로3층2.add(new Robot_Location_bean(32793, 32872, 309));
		지하침공로3층2.add(new Robot_Location_bean(32784, 32878, 309));
		지하침공로3층2.add(new Robot_Location_bean(32778, 32885, 309));
		지하침공로3층2.add(new Robot_Location_bean(32773, 32890, 309));
		지하침공로3층2.add(new Robot_Location_bean(32762, 32887, 309));
		지하침공로3층2.add(new Robot_Location_bean(32752, 32887, 309));
		지하침공로3층2.add(new Robot_Location_bean(32746, 32879, 309));
		지하침공로3층2.add(new Robot_Location_bean(32756, 32871, 309));
		지하침공로3층2.add(new Robot_Location_bean(32764, 32862, 309));
		지하침공로3층2.add(new Robot_Location_bean(32765, 32852, 309));
		지하침공로3층2.add(new Robot_Location_bean(32762, 32844, 309));

		에바1층.add(new Robot_Location_bean(32710, 32788, 59));
		에바1층.add(new Robot_Location_bean(32716, 32793, 59));
		에바1층.add(new Robot_Location_bean(32721, 32796, 59));
		에바1층.add(new Robot_Location_bean(32731, 32796, 59));
		에바1층.add(new Robot_Location_bean(32727, 32810, 59));
		에바1층.add(new Robot_Location_bean(32725, 32818, 59));
		에바1층.add(new Robot_Location_bean(32722, 32825, 59));
		에바1층.add(new Robot_Location_bean(32726, 32831, 59));
		에바1층.add(new Robot_Location_bean(32724, 32838, 59));
		에바1층.add(new Robot_Location_bean(32724, 32844, 59));
		에바1층.add(new Robot_Location_bean(32721, 32852, 59));
		에바1층.add(new Robot_Location_bean(32715, 32859, 59));
		에바1층.add(new Robot_Location_bean(32702, 32863, 59));
		에바1층.add(new Robot_Location_bean(32703, 32873, 59));
		에바1층.add(new Robot_Location_bean(32695, 32870, 59));
		에바1층.add(new Robot_Location_bean(32689, 32869, 59));
		에바1층.add(new Robot_Location_bean(32683, 32868, 59));
		에바1층.add(new Robot_Location_bean(32679, 32864, 59));
		에바1층.add(new Robot_Location_bean(32673, 32864, 59));
		에바1층.add(new Robot_Location_bean(32668, 32868, 59));
		에바1층.add(new Robot_Location_bean(32664, 32859, 59));
		에바1층.add(new Robot_Location_bean(32664, 32854, 59));
		에바1층.add(new Robot_Location_bean(32663, 32847, 59));
		에바1층.add(new Robot_Location_bean(32667, 32841, 59));
		에바1층.add(new Robot_Location_bean(32674, 32844, 59));
		에바1층.add(new Robot_Location_bean(32675, 32834, 59));
		에바1층.add(new Robot_Location_bean(32668, 32834, 59));
		에바1층.add(new Robot_Location_bean(32663, 32832, 59));
		에바1층.add(new Robot_Location_bean(32668, 32827, 59));
		에바1층.add(new Robot_Location_bean(32676, 32826, 59));
		에바1층.add(new Robot_Location_bean(32680, 32816, 59));
		에바1층.add(new Robot_Location_bean(32679, 32806, 59));
		에바1층.add(new Robot_Location_bean(32684, 32802, 59));
		에바1층.add(new Robot_Location_bean(32695, 32801, 59));
		에바1층.add(new Robot_Location_bean(32705, 32800, 59));
		에바1층.add(new Robot_Location_bean(32709, 32800, 59));
		에바1층.add(new Robot_Location_bean(32710, 32793, 59));

		에바1층2.add(new Robot_Location_bean(32710, 32788, 59));
		에바1층2.add(new Robot_Location_bean(32710, 32793, 59));
		에바1층2.add(new Robot_Location_bean(32709, 32800, 59));
		에바1층2.add(new Robot_Location_bean(32703, 32802, 59));
		에바1층2.add(new Robot_Location_bean(32695, 32802, 59));
		에바1층2.add(new Robot_Location_bean(32688, 32802, 59));
		에바1층2.add(new Robot_Location_bean(32679, 32803, 59));
		에바1층2.add(new Robot_Location_bean(32678, 32810, 59));
		에바1층2.add(new Robot_Location_bean(32679, 32817, 59));
		에바1층2.add(new Robot_Location_bean(32667, 32828, 59));
		에바1층2.add(new Robot_Location_bean(32664, 32832, 59));
		에바1층2.add(new Robot_Location_bean(32669, 32834, 59));
		에바1층2.add(new Robot_Location_bean(32675, 32835, 59));
		에바1층2.add(new Robot_Location_bean(32674, 32844, 59));
		에바1층2.add(new Robot_Location_bean(32669, 32850, 59));
		에바1층2.add(new Robot_Location_bean(32676, 32850, 59));
		에바1층2.add(new Robot_Location_bean(32677, 32859, 59));
		에바1층2.add(new Robot_Location_bean(32680, 32866, 59));
		에바1층2.add(new Robot_Location_bean(32684, 32868, 59));
		에바1층2.add(new Robot_Location_bean(32690, 32869, 59));
		에바1층2.add(new Robot_Location_bean(32696, 32870, 59));
		에바1층2.add(new Robot_Location_bean(32703, 32872, 59));
		에바1층2.add(new Robot_Location_bean(32705, 32863, 59));
		에바1층2.add(new Robot_Location_bean(32713, 32860, 59));
		에바1층2.add(new Robot_Location_bean(32721, 32851, 59));
		에바1층2.add(new Robot_Location_bean(32729, 32850, 59));
		에바1층2.add(new Robot_Location_bean(32735, 32851, 59));
		에바1층2.add(new Robot_Location_bean(32741, 32843, 59));
		에바1층2.add(new Robot_Location_bean(32747, 32839, 59));
		에바1층2.add(new Robot_Location_bean(32747, 32828, 59));
		에바1층2.add(new Robot_Location_bean(32745, 32818, 59));
		에바1층2.add(new Robot_Location_bean(32739, 32815, 59));
		에바1층2.add(new Robot_Location_bean(32733, 32818, 59));
		에바1층2.add(new Robot_Location_bean(32732, 32825, 59));
		에바1층2.add(new Robot_Location_bean(32726, 32825, 59));
		에바1층2.add(new Robot_Location_bean(32722, 32823, 59));
		에바1층2.add(new Robot_Location_bean(32724, 32811, 59));
		에바1층2.add(new Robot_Location_bean(32728, 32805, 59));
		에바1층2.add(new Robot_Location_bean(32731, 32797, 59));
		에바1층2.add(new Robot_Location_bean(32723, 32796, 59));
		에바1층2.add(new Robot_Location_bean(32719, 32792, 59));
		에바1층2.add(new Robot_Location_bean(32711, 32792, 59));

		에바2층.add(new Robot_Location_bean(32746, 32861, 60));
		에바2층.add(new Robot_Location_bean(32734, 32862, 60));
		에바2층.add(new Robot_Location_bean(32724, 32859, 60));
		에바2층.add(new Robot_Location_bean(32715, 32852, 60));
		에바2층.add(new Robot_Location_bean(32708, 32853, 60));
		에바2층.add(new Robot_Location_bean(32702, 32855, 60));
		에바2층.add(new Robot_Location_bean(32699, 32849, 60));
		에바2층.add(new Robot_Location_bean(32691, 32846, 60));
		에바2층.add(new Robot_Location_bean(32689, 32838, 60));
		에바2층.add(new Robot_Location_bean(32680, 32837, 60));
		에바2층.add(new Robot_Location_bean(32672, 32832, 60));
		에바2층.add(new Robot_Location_bean(32677, 32824, 60));
		에바2층.add(new Robot_Location_bean(32678, 32813, 60));
		에바2층.add(new Robot_Location_bean(32684, 32805, 60));
		에바2층.add(new Robot_Location_bean(32691, 32804, 60));
		에바2층.add(new Robot_Location_bean(32697, 32808, 60));
		에바2층.add(new Robot_Location_bean(32703, 32803, 60));
		에바2층.add(new Robot_Location_bean(32706, 32794, 60));
		에바2층.add(new Robot_Location_bean(32717, 32793, 60));
		에바2층.add(new Robot_Location_bean(32729, 32793, 60));
		에바2층.add(new Robot_Location_bean(32740, 32792, 60));
		에바2층.add(new Robot_Location_bean(32740, 32799, 60));
		에바2층.add(new Robot_Location_bean(32736, 32808, 60));
		에바2층.add(new Robot_Location_bean(32737, 32818, 60));
		에바2층.add(new Robot_Location_bean(32734, 32829, 60));
		에바2층.add(new Robot_Location_bean(32738, 32819, 60));
		에바2층.add(new Robot_Location_bean(32735, 32810, 60));
		에바2층.add(new Robot_Location_bean(32724, 32809, 60));
		에바2층.add(new Robot_Location_bean(32717, 32812, 60));
		에바2층.add(new Robot_Location_bean(32709, 32820, 60));
		에바2층.add(new Robot_Location_bean(32704, 32823, 60));
		에바2층.add(new Robot_Location_bean(32696, 32832, 60));
		에바2층.add(new Robot_Location_bean(32693, 32835, 60));
		에바2층.add(new Robot_Location_bean(32686, 32837, 60));
		에바2층.add(new Robot_Location_bean(32686, 32846, 60));
		에바2층.add(new Robot_Location_bean(32696, 32846, 60));
		에바2층.add(new Robot_Location_bean(32702, 32854, 60));
		에바2층.add(new Robot_Location_bean(32712, 32853, 60));
		에바2층.add(new Robot_Location_bean(32722, 32855, 60));
		에바2층.add(new Robot_Location_bean(32730, 32858, 60));
		에바2층.add(new Robot_Location_bean(32735, 32862, 60));
		에바2층.add(new Robot_Location_bean(32742, 32862, 60));

		에바2층2.add(new Robot_Location_bean(32746, 32861, 60));
		에바2층2.add(new Robot_Location_bean(32738, 32863, 60));
		에바2층2.add(new Robot_Location_bean(32729, 32862, 60));
		에바2층2.add(new Robot_Location_bean(32729, 32855, 60));
		에바2층2.add(new Robot_Location_bean(32723, 32856, 60));
		에바2층2.add(new Robot_Location_bean(32716, 32852, 60));
		에바2층2.add(new Robot_Location_bean(32701, 32850, 60));
		에바2층2.add(new Robot_Location_bean(32696, 32846, 60));
		에바2층2.add(new Robot_Location_bean(32691, 32838, 60));
		에바2층2.add(new Robot_Location_bean(32698, 32826, 60));
		에바2층2.add(new Robot_Location_bean(32708, 32821, 60));
		에바2층2.add(new Robot_Location_bean(32718, 32813, 60));
		에바2층2.add(new Robot_Location_bean(32716, 32805, 60));
		에바2층2.add(new Robot_Location_bean(32705, 32800, 60));
		에바2층2.add(new Robot_Location_bean(32698, 32809, 60));
		에바2층2.add(new Robot_Location_bean(32688, 32804, 60));
		에바2층2.add(new Robot_Location_bean(32680, 32808, 60));
		에바2층2.add(new Robot_Location_bean(32677, 32812, 60));
		에바2층2.add(new Robot_Location_bean(32677, 32824, 60));
		에바2층2.add(new Robot_Location_bean(32671, 32833, 60));
		에바2층2.add(new Robot_Location_bean(32674, 32842, 60));
		에바2층2.add(new Robot_Location_bean(32685, 32837, 60));
		에바2층2.add(new Robot_Location_bean(32693, 32835, 60));
		에바2층2.add(new Robot_Location_bean(32700, 32838, 60));
		에바2층2.add(new Robot_Location_bean(32701, 32843, 60));
		에바2층2.add(new Robot_Location_bean(32702, 32850, 60));
		에바2층2.add(new Robot_Location_bean(32710, 32853, 60));
		에바2층2.add(new Robot_Location_bean(32717, 32852, 60));
		에바2층2.add(new Robot_Location_bean(32724, 32857, 60));
		에바2층2.add(new Robot_Location_bean(32729, 32857, 60));
		에바2층2.add(new Robot_Location_bean(32735, 32862, 60));
		에바2층2.add(new Robot_Location_bean(32742, 32862, 60));

		에바3층.add(new Robot_Location_bean(32727, 32808, 61));
		에바3층.add(new Robot_Location_bean(32719, 32804, 61));
		에바3층.add(new Robot_Location_bean(32714, 32796, 61));
		에바3층.add(new Robot_Location_bean(32708, 32791, 61));
		에바3층.add(new Robot_Location_bean(32698, 32789, 61));
		에바3층.add(new Robot_Location_bean(32690, 32795, 61));
		에바3층.add(new Robot_Location_bean(32678, 32790, 61));
		에바3층.add(new Robot_Location_bean(32676, 32801, 61));
		에바3층.add(new Robot_Location_bean(32674, 32809, 61));
		에바3층.add(new Robot_Location_bean(32678, 32817, 61));
		에바3층.add(new Robot_Location_bean(32674, 32831, 61));
		에바3층.add(new Robot_Location_bean(32677, 32847, 61));
		에바3층.add(new Robot_Location_bean(32681, 32854, 61));
		에바3층.add(new Robot_Location_bean(32677, 32863, 61));
		에바3층.add(new Robot_Location_bean(32668, 32873, 61));
		에바3층.add(new Robot_Location_bean(32679, 32873, 61));
		에바3층.add(new Robot_Location_bean(32691, 32861, 61));
		에바3층.add(new Robot_Location_bean(32700, 32869, 61));
		에바3층.add(new Robot_Location_bean(32714, 32866, 61));
		에바3층.add(new Robot_Location_bean(32724, 32871, 61));
		에바3층.add(new Robot_Location_bean(32732, 32863, 61));
		에바3층.add(new Robot_Location_bean(32741, 32854, 61));
		에바3층.add(new Robot_Location_bean(32746, 32841, 61));
		에바3층.add(new Robot_Location_bean(32741, 32833, 61));
		에바3층.add(new Robot_Location_bean(32739, 32825, 61));
		에바3층.add(new Robot_Location_bean(32739, 32820, 61));
		에바3층.add(new Robot_Location_bean(32731, 32817, 61));

		에바3층2.add(new Robot_Location_bean(32727, 32808, 61));
		에바3층2.add(new Robot_Location_bean(32730, 32818, 61));
		에바3층2.add(new Robot_Location_bean(32739, 32822, 61));
		에바3층2.add(new Robot_Location_bean(32734, 32831, 61));
		에바3층2.add(new Robot_Location_bean(32729, 32842, 61));
		에바3층2.add(new Robot_Location_bean(32724, 32852, 61));
		에바3층2.add(new Robot_Location_bean(32728, 32863, 61));
		에바3층2.add(new Robot_Location_bean(32731, 32870, 61));
		에바3층2.add(new Robot_Location_bean(32722, 32872, 61));
		에바3층2.add(new Robot_Location_bean(32716, 32864, 61));
		에바3층2.add(new Robot_Location_bean(32709, 32852, 61));
		에바3층2.add(new Robot_Location_bean(32699, 32847, 61));
		에바3층2.add(new Robot_Location_bean(32696, 32837, 61));
		에바3층2.add(new Robot_Location_bean(32691, 32835, 61));
		에바3층2.add(new Robot_Location_bean(32690, 32828, 61));
		에바3층2.add(new Robot_Location_bean(32686, 32819, 61));
		에바3층2.add(new Robot_Location_bean(32685, 32806, 61));
		에바3층2.add(new Robot_Location_bean(32680, 32796, 61));
		에바3층2.add(new Robot_Location_bean(32676, 32790, 61));
		에바3층2.add(new Robot_Location_bean(32686, 32790, 61));
		에바3층2.add(new Robot_Location_bean(32695, 32795, 61));
		에바3층2.add(new Robot_Location_bean(32708, 32792, 61));
		에바3층2.add(new Robot_Location_bean(32717, 32798, 61));
		에바3층2.add(new Robot_Location_bean(32721, 32807, 61));

		에바4층.add(new Robot_Location_bean(32805, 32870, 63));
		에바4층.add(new Robot_Location_bean(32796, 32875, 63));
		에바4층.add(new Robot_Location_bean(32789, 32871, 63));
		에바4층.add(new Robot_Location_bean(32771, 32868, 63));
		에바4층.add(new Robot_Location_bean(32773, 32858, 63));
		에바4층.add(new Robot_Location_bean(32784, 32852, 63));
		에바4층.add(new Robot_Location_bean(32789, 32839, 63));
		에바4층.add(new Robot_Location_bean(32799, 32828, 63));
		에바4층.add(new Robot_Location_bean(32795, 32819, 63));
		에바4층.add(new Robot_Location_bean(32782, 32815, 63));
		에바4층.add(new Robot_Location_bean(32774, 32816, 63));
		에바4층.add(new Robot_Location_bean(32775, 32808, 63));
		에바4층.add(new Robot_Location_bean(32771, 32800, 63));
		에바4층.add(new Robot_Location_bean(32783, 32797, 63));
		에바4층.add(new Robot_Location_bean(32795, 32794, 63));
		에바4층.add(new Robot_Location_bean(32801, 32783, 63));
		에바4층.add(new Robot_Location_bean(32804, 32770, 63));
		에바4층.add(new Robot_Location_bean(32810, 32754, 63));
		에바4층.add(new Robot_Location_bean(32810, 32738, 63));
		에바4층.add(new Robot_Location_bean(32823, 32726, 63));
		에바4층.add(new Robot_Location_bean(32818, 32712, 63));
		에바4층.add(new Robot_Location_bean(32804, 32717, 63));
		에바4층.add(new Robot_Location_bean(32794, 32707, 63));
		에바4층.add(new Robot_Location_bean(32780, 32710, 63));
		에바4층.add(new Robot_Location_bean(32763, 32717, 63));
		에바4층.add(new Robot_Location_bean(32748, 32712, 63));
		에바4층.add(new Robot_Location_bean(32735, 32703, 63));
		에바4층.add(new Robot_Location_bean(32721, 32713, 63));
		에바4층.add(new Robot_Location_bean(32706, 32707, 63));
		에바4층.add(new Robot_Location_bean(32693, 32708, 63));
		에바4층.add(new Robot_Location_bean(32682, 32717, 63));
		에바4층.add(new Robot_Location_bean(32669, 32716, 63));
		에바4층.add(new Robot_Location_bean(32660, 32721, 63));
		에바4층.add(new Robot_Location_bean(32652, 32732, 63));
		에바4층.add(new Robot_Location_bean(32647, 32744, 63));
		에바4층.add(new Robot_Location_bean(32648, 32757, 63));
		에바4층.add(new Robot_Location_bean(32651, 32768, 63));
		에바4층.add(new Robot_Location_bean(32646, 32778, 63));
		에바4층.add(new Robot_Location_bean(32644, 32790, 63));
		에바4층.add(new Robot_Location_bean(32648, 32797, 63));
		에바4층.add(new Robot_Location_bean(32648, 32809, 63));
		에바4층.add(new Robot_Location_bean(32648, 32821, 63));
		에바4층.add(new Robot_Location_bean(32649, 32834, 63));
		에바4층.add(new Robot_Location_bean(32648, 32845, 63));
		에바4층.add(new Robot_Location_bean(32648, 32861, 63));
		에바4층.add(new Robot_Location_bean(32647, 32871, 63));
		에바4층.add(new Robot_Location_bean(32657, 32878, 63));
		에바4층.add(new Robot_Location_bean(32660, 32889, 63));
		에바4층.add(new Robot_Location_bean(32673, 32888, 63));
		에바4층.add(new Robot_Location_bean(32681, 32885, 63));
		에바4층.add(new Robot_Location_bean(32691, 32881, 63));
		에바4층.add(new Robot_Location_bean(32705, 32882, 63));
		에바4층.add(new Robot_Location_bean(32715, 32880, 63));
		에바4층.add(new Robot_Location_bean(32725, 32883, 63));
		에바4층.add(new Robot_Location_bean(32736, 32877, 63));
		에바4층.add(new Robot_Location_bean(32743, 32886, 63));
		에바4층.add(new Robot_Location_bean(32753, 32888, 63));
		에바4층.add(new Robot_Location_bean(32762, 32895, 63));
		에바4층.add(new Robot_Location_bean(32772, 32889, 63));
		에바4층.add(new Robot_Location_bean(32784, 32882, 63));
		에바4층.add(new Robot_Location_bean(32797, 32878, 63));

		에바4층2.add(new Robot_Location_bean(32805, 32870, 63));
		에바4층2.add(new Robot_Location_bean(32795, 32878, 63));
		에바4층2.add(new Robot_Location_bean(32783, 32881, 63));
		에바4층2.add(new Robot_Location_bean(32775, 32887, 63));
		에바4층2.add(new Robot_Location_bean(32759, 32894, 63));
		에바4층2.add(new Robot_Location_bean(32750, 32888, 63));
		에바4층2.add(new Robot_Location_bean(32742, 32885, 63));
		에바4층2.add(new Robot_Location_bean(32733, 32877, 63));
		에바4층2.add(new Robot_Location_bean(32732, 32864, 63));
		에바4층2.add(new Robot_Location_bean(32719, 32870, 63));
		에바4층2.add(new Robot_Location_bean(32713, 32860, 63));
		에바4층2.add(new Robot_Location_bean(32715, 32848, 63));
		에바4층2.add(new Robot_Location_bean(32709, 32841, 63));
		에바4층2.add(new Robot_Location_bean(32697, 32839, 63));
		에바4층2.add(new Robot_Location_bean(32695, 32828, 63));
		에바4층2.add(new Robot_Location_bean(32691, 32818, 63));
		에바4층2.add(new Robot_Location_bean(32688, 32808, 63));
		에바4층2.add(new Robot_Location_bean(32692, 32798, 63));
		에바4층2.add(new Robot_Location_bean(32694, 32787, 63));
		에바4층2.add(new Robot_Location_bean(32686, 32783, 63));
		에바4층2.add(new Robot_Location_bean(32687, 32775, 63));
		에바4층2.add(new Robot_Location_bean(32702, 32769, 63));
		에바4층2.add(new Robot_Location_bean(32714, 32757, 63));
		에바4층2.add(new Robot_Location_bean(32725, 32750, 63));
		에바4층2.add(new Robot_Location_bean(32737, 32746, 63));
		에바4층2.add(new Robot_Location_bean(32740, 32736, 63));
		에바4층2.add(new Robot_Location_bean(32739, 32728, 63));
		에바4층2.add(new Robot_Location_bean(32742, 32718, 63));
		에바4층2.add(new Robot_Location_bean(32741, 32708, 63));
		에바4층2.add(new Robot_Location_bean(32742, 32697, 63));
		에바4층2.add(new Robot_Location_bean(32754, 32705, 63));
		에바4층2.add(new Robot_Location_bean(32758, 32713, 63));
		에바4층2.add(new Robot_Location_bean(32770, 32713, 63));
		에바4층2.add(new Robot_Location_bean(32780, 32708, 63));
		에바4층2.add(new Robot_Location_bean(32790, 32700, 63));
		에바4층2.add(new Robot_Location_bean(32797, 32690, 63));
		에바4층2.add(new Robot_Location_bean(32803, 32701, 63));
		에바4층2.add(new Robot_Location_bean(32805, 32708, 63));
		에바4층2.add(new Robot_Location_bean(32808, 32718, 63));
		에바4층2.add(new Robot_Location_bean(32822, 32714, 63));
		에바4층2.add(new Robot_Location_bean(32833, 32710, 63));
		에바4층2.add(new Robot_Location_bean(32836, 32722, 63));
		에바4층2.add(new Robot_Location_bean(32835, 32737, 63));
		에바4층2.add(new Robot_Location_bean(32834, 32746, 63));
		에바4층2.add(new Robot_Location_bean(32838, 32757, 63));
		에바4층2.add(new Robot_Location_bean(32840, 32767, 63));
		에바4층2.add(new Robot_Location_bean(32845, 32779, 63));
		에바4층2.add(new Robot_Location_bean(32836, 32792, 63));
		에바4층2.add(new Robot_Location_bean(32820, 32795, 63));
		에바4층2.add(new Robot_Location_bean(32817, 32806, 63));
		에바4층2.add(new Robot_Location_bean(32810, 32817, 63));
		에바4층2.add(new Robot_Location_bean(32802, 32826, 63));
		에바4층2.add(new Robot_Location_bean(32800, 32836, 63));
		에바4층2.add(new Robot_Location_bean(32807, 32843, 63));
		에바4층2.add(new Robot_Location_bean(32825, 32845, 63));
		에바4층2.add(new Robot_Location_bean(32833, 32854, 63));
		에바4층2.add(new Robot_Location_bean(32834, 32867, 63));
		에바4층2.add(new Robot_Location_bean(32826, 32877, 63));
		에바4층2.add(new Robot_Location_bean(32828, 32886, 63));
		에바4층2.add(new Robot_Location_bean(32824, 32895, 63));
		에바4층2.add(new Robot_Location_bean(32817, 32896, 63));
		에바4층2.add(new Robot_Location_bean(32807, 32896, 63));
		에바4층2.add(new Robot_Location_bean(32801, 32889, 63));
		에바4층2.add(new Robot_Location_bean(32796, 32881, 63));

		/*하이네잡밭.add(new Robot_Location_bean(33403, 33415, 4));
		하이네잡밭.add(new Robot_Location_bean(33409, 33406, 4));
		하이네잡밭.add(new Robot_Location_bean(33414, 33394, 4));
		하이네잡밭.add(new Robot_Location_bean(33423, 33387, 4));
		하이네잡밭.add(new Robot_Location_bean(33428, 33380, 4));
		하이네잡밭.add(new Robot_Location_bean(33430, 33372, 4));
		하이네잡밭.add(new Robot_Location_bean(33431, 33362, 4));
		하이네잡밭.add(new Robot_Location_bean(33438, 33354, 4));
		하이네잡밭.add(new Robot_Location_bean(33442, 33344, 4));
		하이네잡밭.add(new Robot_Location_bean(33439, 33338, 4));
		하이네잡밭.add(new Robot_Location_bean(33436, 33329, 4));
		하이네잡밭.add(new Robot_Location_bean(33441, 33321, 4));
		하이네잡밭.add(new Robot_Location_bean(33444, 33313, 4));
		하이네잡밭.add(new Robot_Location_bean(33446, 33305, 4));
		하이네잡밭.add(new Robot_Location_bean(33452, 33297, 4));
		하이네잡밭.add(new Robot_Location_bean(33461, 33291, 4));
		하이네잡밭.add(new Robot_Location_bean(33467, 33283, 4));
		하이네잡밭.add(new Robot_Location_bean(33474, 33287, 4));
		하이네잡밭.add(new Robot_Location_bean(33477, 33290, 4));
		하이네잡밭.add(new Robot_Location_bean(33485, 33287, 4));
		하이네잡밭.add(new Robot_Location_bean(33492, 33284, 4));
		하이네잡밭.add(new Robot_Location_bean(33500, 33283, 4));
		하이네잡밭.add(new Robot_Location_bean(33509, 33283, 4));
		하이네잡밭.add(new Robot_Location_bean(33514, 33275, 4));
		하이네잡밭.add(new Robot_Location_bean(33517, 33265, 4));
		하이네잡밭.add(new Robot_Location_bean(33525, 33263, 4));
		하이네잡밭.add(new Robot_Location_bean(33532, 33260, 4));
		하이네잡밭.add(new Robot_Location_bean(33538, 33257, 4));
		하이네잡밭.add(new Robot_Location_bean(33544, 33254, 4));
		하이네잡밭.add(new Robot_Location_bean(33545, 33244, 4));
		하이네잡밭.add(new Robot_Location_bean(33546, 33236, 4));
		하이네잡밭.add(new Robot_Location_bean(33552, 33233, 4));
		하이네잡밭.add(new Robot_Location_bean(33562, 33231, 4));
		하이네잡밭.add(new Robot_Location_bean(33561, 33222, 4));
		하이네잡밭.add(new Robot_Location_bean(33559, 33214, 4));
		하이네잡밭.add(new Robot_Location_bean(33561, 33205, 4));
		하이네잡밭.add(new Robot_Location_bean(33567, 33198, 4));
		하이네잡밭.add(new Robot_Location_bean(33567, 33190, 4));
		하이네잡밭.add(new Robot_Location_bean(33562, 33185, 4));
		하이네잡밭.add(new Robot_Location_bean(33568, 33179, 4));
		하이네잡밭.add(new Robot_Location_bean(33572, 33171, 4));
		하이네잡밭.add(new Robot_Location_bean(33575, 33165, 4));
		하이네잡밭.add(new Robot_Location_bean(33579, 33159, 4));
		하이네잡밭.add(new Robot_Location_bean(33575, 33156, 4));
		하이네잡밭.add(new Robot_Location_bean(33572, 33151, 4));
		하이네잡밭.add(new Robot_Location_bean(33569, 33147, 4));
		하이네잡밭.add(new Robot_Location_bean(33564, 33138, 4));
		하이네잡밭.add(new Robot_Location_bean(33557, 33131, 4));
		하이네잡밭.add(new Robot_Location_bean(33556, 33121, 4));
		하이네잡밭.add(new Robot_Location_bean(33559, 33110, 4));
		하이네잡밭.add(new Robot_Location_bean(33553, 33104, 4));
		하이네잡밭.add(new Robot_Location_bean(33553, 33095, 4));
		하이네잡밭.add(new Robot_Location_bean(33549, 33089, 4));
		하이네잡밭.add(new Robot_Location_bean(33540, 33087, 4));
		하이네잡밭.add(new Robot_Location_bean(33531, 33092, 4));
		하이네잡밭.add(new Robot_Location_bean(33520, 33092, 4));
		하이네잡밭.add(new Robot_Location_bean(33513, 33101, 4));
		하이네잡밭.add(new Robot_Location_bean(33504, 33104, 4));
		하이네잡밭.add(new Robot_Location_bean(33495, 33111, 4));
		하이네잡밭.add(new Robot_Location_bean(33487, 33114, 4));
		하이네잡밭.add(new Robot_Location_bean(33479, 33109, 4));
		하이네잡밭.add(new Robot_Location_bean(33472, 33107, 4));
		하이네잡밭.add(new Robot_Location_bean(33468, 33101, 4));
		하이네잡밭.add(new Robot_Location_bean(33457, 33102, 4));
		하이네잡밭.add(new Robot_Location_bean(33453, 33110, 4));
		하이네잡밭.add(new Robot_Location_bean(33452, 33118, 4));
		하이네잡밭.add(new Robot_Location_bean(33452, 33130, 4));
		하이네잡밭.add(new Robot_Location_bean(33443, 33135, 4));
		하이네잡밭.add(new Robot_Location_bean(33442, 33141, 4));
		하이네잡밭.add(new Robot_Location_bean(33435, 33147, 4));
		하이네잡밭.add(new Robot_Location_bean(33426, 33146, 4));
		하이네잡밭.add(new Robot_Location_bean(33423, 33150, 4));
		하이네잡밭.add(new Robot_Location_bean(33421, 33160, 4));
		하이네잡밭.add(new Robot_Location_bean(33416, 33168, 4));
		하이네잡밭.add(new Robot_Location_bean(33411, 33177, 4));
		하이네잡밭.add(new Robot_Location_bean(33411, 33188, 4));
		하이네잡밭.add(new Robot_Location_bean(33411, 33196, 4));
		하이네잡밭.add(new Robot_Location_bean(33412, 33203, 4));
		하이네잡밭.add(new Robot_Location_bean(33414, 33213, 4));
		하이네잡밭.add(new Robot_Location_bean(33414, 33220, 4));
		하이네잡밭.add(new Robot_Location_bean(33406, 33223, 4));
		하이네잡밭.add(new Robot_Location_bean(33402, 33232, 4));
		하이네잡밭.add(new Robot_Location_bean(33395, 33239, 4));
		하이네잡밭.add(new Robot_Location_bean(33392, 33247, 4));
		하이네잡밭.add(new Robot_Location_bean(33388, 33254, 4));
		하이네잡밭.add(new Robot_Location_bean(33386, 33261, 4));
		하이네잡밭.add(new Robot_Location_bean(33387, 33271, 4));
		하이네잡밭.add(new Robot_Location_bean(33386, 33275, 4));
		하이네잡밭.add(new Robot_Location_bean(33384, 33282, 4));
		하이네잡밭.add(new Robot_Location_bean(33382, 33288, 4));
		하이네잡밭.add(new Robot_Location_bean(33386, 33291, 4));
		하이네잡밭.add(new Robot_Location_bean(33390, 33296, 4));
		하이네잡밭.add(new Robot_Location_bean(33392, 33300, 4));
		하이네잡밭.add(new Robot_Location_bean(33398, 33302, 4));
		하이네잡밭.add(new Robot_Location_bean(33398, 33307, 4));
		하이네잡밭.add(new Robot_Location_bean(33396, 33314, 4));
		하이네잡밭.add(new Robot_Location_bean(33397, 33323, 4));
		하이네잡밭.add(new Robot_Location_bean(33397, 33331, 4));
		하이네잡밭.add(new Robot_Location_bean(33401, 33337, 4));
		하이네잡밭.add(new Robot_Location_bean(33400, 33345, 4));
		하이네잡밭.add(new Robot_Location_bean(33396, 33352, 4));
		하이네잡밭.add(new Robot_Location_bean(33397, 33359, 4));
		하이네잡밭.add(new Robot_Location_bean(33401, 33364, 4));
		하이네잡밭.add(new Robot_Location_bean(33402, 33372, 4));
		하이네잡밭.add(new Robot_Location_bean(33401, 33379, 4));
		하이네잡밭.add(new Robot_Location_bean(33402, 33386, 4));
		하이네잡밭.add(new Robot_Location_bean(33398, 33394, 4));
		하이네잡밭.add(new Robot_Location_bean(33401, 33402, 4));
		하이네잡밭.add(new Robot_Location_bean(33402, 33407, 4));
		하이네잡밭.add(new Robot_Location_bean(33401, 33413, 4));

		하이네잡밭2.add(new Robot_Location_bean(33403, 33415, 4));
		하이네잡밭2.add(new Robot_Location_bean(33397, 33409, 4));
		하이네잡밭2.add(new Robot_Location_bean(33393, 33402, 4));
		하이네잡밭2.add(new Robot_Location_bean(33394, 33395, 4));
		하이네잡밭2.add(new Robot_Location_bean(33395, 33388, 4));
		하이네잡밭2.add(new Robot_Location_bean(33395, 33381, 4));
		하이네잡밭2.add(new Robot_Location_bean(33398, 33375, 4));
		하이네잡밭2.add(new Robot_Location_bean(33400, 33367, 4));
		하이네잡밭2.add(new Robot_Location_bean(33399, 33360, 4));
		하이네잡밭2.add(new Robot_Location_bean(33396, 33353, 4));
		하이네잡밭2.add(new Robot_Location_bean(33397, 33345, 4));
		하이네잡밭2.add(new Robot_Location_bean(33403, 33338, 4));
		하이네잡밭2.add(new Robot_Location_bean(33410, 33334, 4));
		하이네잡밭2.add(new Robot_Location_bean(33415, 33327, 4));
		하이네잡밭2.add(new Robot_Location_bean(33422, 33323, 4));
		하이네잡밭2.add(new Robot_Location_bean(33428, 33321, 4));
		하이네잡밭2.add(new Robot_Location_bean(33432, 33314, 4));
		하이네잡밭2.add(new Robot_Location_bean(33438, 33305, 4));
		하이네잡밭2.add(new Robot_Location_bean(33441, 33297, 4));
		하이네잡밭2.add(new Robot_Location_bean(33438, 33290, 4));
		하이네잡밭2.add(new Robot_Location_bean(33442, 33280, 4));
		하이네잡밭2.add(new Robot_Location_bean(33447, 33273, 4));
		하이네잡밭2.add(new Robot_Location_bean(33451, 33266, 4));
		하이네잡밭2.add(new Robot_Location_bean(33449, 33258, 4));
		하이네잡밭2.add(new Robot_Location_bean(33442, 33250, 4));
		하이네잡밭2.add(new Robot_Location_bean(33445, 33241, 4));
		하이네잡밭2.add(new Robot_Location_bean(33449, 33234, 4));
		하이네잡밭2.add(new Robot_Location_bean(33441, 33229, 4));
		하이네잡밭2.add(new Robot_Location_bean(33431, 33230, 4));
		하이네잡밭2.add(new Robot_Location_bean(33423, 33228, 4));
		하이네잡밭2.add(new Robot_Location_bean(33415, 33226, 4));
		하이네잡밭2.add(new Robot_Location_bean(33408, 33218, 4));
		하이네잡밭2.add(new Robot_Location_bean(33400, 33216, 4));
		하이네잡밭2.add(new Robot_Location_bean(33391, 33214, 4));
		하이네잡밭2.add(new Robot_Location_bean(33384, 33208, 4));
		하이네잡밭2.add(new Robot_Location_bean(33386, 33200, 4));
		하이네잡밭2.add(new Robot_Location_bean(33388, 33186, 4));
		하이네잡밭2.add(new Robot_Location_bean(33400, 33178, 4));
		하이네잡밭2.add(new Robot_Location_bean(33410, 33166, 4));
		하이네잡밭2.add(new Robot_Location_bean(33414, 33156, 4));
		하이네잡밭2.add(new Robot_Location_bean(33422, 33148, 4));
		하이네잡밭2.add(new Robot_Location_bean(33430, 33139, 4));
		하이네잡밭2.add(new Robot_Location_bean(33436, 33126, 4));
		하이네잡밭2.add(new Robot_Location_bean(33445, 33119, 4));
		하이네잡밭2.add(new Robot_Location_bean(33451, 33109, 4));
		하이네잡밭2.add(new Robot_Location_bean(33458, 33099, 4));
		하이네잡밭2.add(new Robot_Location_bean(33469, 33094, 4));
		하이네잡밭2.add(new Robot_Location_bean(33474, 33097, 4));
		하이네잡밭2.add(new Robot_Location_bean(33482, 33101, 4));
		하이네잡밭2.add(new Robot_Location_bean(33494, 33104, 4));
		하이네잡밭2.add(new Robot_Location_bean(33503, 33113, 4));
		하이네잡밭2.add(new Robot_Location_bean(33509, 33119, 4));
		하이네잡밭2.add(new Robot_Location_bean(33515, 33125, 4));
		하이네잡밭2.add(new Robot_Location_bean(33515, 33133, 4));
		하이네잡밭2.add(new Robot_Location_bean(33517, 33137, 4));
		하이네잡밭2.add(new Robot_Location_bean(33524, 33141, 4));
		하이네잡밭2.add(new Robot_Location_bean(33535, 33146, 4));
		하이네잡밭2.add(new Robot_Location_bean(33545, 33137, 4));
		하이네잡밭2.add(new Robot_Location_bean(33554, 33127, 4));
		하이네잡밭2.add(new Robot_Location_bean(33565, 33126, 4));
		하이네잡밭2.add(new Robot_Location_bean(33573, 33133, 4));
		하이네잡밭2.add(new Robot_Location_bean(33580, 33140, 4));
		하이네잡밭2.add(new Robot_Location_bean(33591, 33141, 4));
		하이네잡밭2.add(new Robot_Location_bean(33596, 33149, 4));
		하이네잡밭2.add(new Robot_Location_bean(33600, 33159, 4));
		하이네잡밭2.add(new Robot_Location_bean(33606, 33166, 4));
		하이네잡밭2.add(new Robot_Location_bean(33599, 33175, 4));
		하이네잡밭2.add(new Robot_Location_bean(33587, 33182, 4));
		하이네잡밭2.add(new Robot_Location_bean(33581, 33190, 4));
		하이네잡밭2.add(new Robot_Location_bean(33573, 33200, 4));
		하이네잡밭2.add(new Robot_Location_bean(33561, 33209, 4));
		하이네잡밭2.add(new Robot_Location_bean(33556, 33222, 4));
		하이네잡밭2.add(new Robot_Location_bean(33549, 33236, 4));
		하이네잡밭2.add(new Robot_Location_bean(33552, 33245, 4));
		하이네잡밭2.add(new Robot_Location_bean(33547, 33255, 4));
		하이네잡밭2.add(new Robot_Location_bean(33543, 33267, 4));
		하이네잡밭2.add(new Robot_Location_bean(33529, 33280, 4));
		하이네잡밭2.add(new Robot_Location_bean(33521, 33288, 4));
		하이네잡밭2.add(new Robot_Location_bean(33509, 33293, 4));
		하이네잡밭2.add(new Robot_Location_bean(33504, 33303, 4));
		하이네잡밭2.add(new Robot_Location_bean(33505, 33311, 4));
		하이네잡밭2.add(new Robot_Location_bean(33499, 33324, 4));
		하이네잡밭2.add(new Robot_Location_bean(33489, 33332, 4));
		하이네잡밭2.add(new Robot_Location_bean(33481, 33339, 4));
		하이네잡밭2.add(new Robot_Location_bean(33469, 33344, 4));
		하이네잡밭2.add(new Robot_Location_bean(33462, 33351, 4));
		하이네잡밭2.add(new Robot_Location_bean(33461, 33360, 4));
		하이네잡밭2.add(new Robot_Location_bean(33461, 33370, 4));
		하이네잡밭2.add(new Robot_Location_bean(33452, 33377, 4));
		하이네잡밭2.add(new Robot_Location_bean(33440, 33376, 4));
		하이네잡밭2.add(new Robot_Location_bean(33434, 33385, 4));
		하이네잡밭2.add(new Robot_Location_bean(33426, 33389, 4));
		하이네잡밭2.add(new Robot_Location_bean(33415, 33393, 4));
		하이네잡밭2.add(new Robot_Location_bean(33404, 33399, 4));
		하이네잡밭2.add(new Robot_Location_bean(33396, 33405, 4));
		하이네잡밭2.add(new Robot_Location_bean(33394, 33415, 4));

		하이네잡밭3.add(new Robot_Location_bean(33437, 33209, 4));
		하이네잡밭3.add(new Robot_Location_bean(33442, 33223, 4));
		하이네잡밭3.add(new Robot_Location_bean(33449, 33232, 4));
		하이네잡밭3.add(new Robot_Location_bean(33458, 33239, 4));
		하이네잡밭3.add(new Robot_Location_bean(33466, 33247, 4));
		하이네잡밭3.add(new Robot_Location_bean(33474, 33254, 4));
		하이네잡밭3.add(new Robot_Location_bean(33483, 33261, 4));
		하이네잡밭3.add(new Robot_Location_bean(33492, 33268, 4));
		하이네잡밭3.add(new Robot_Location_bean(33501, 33280, 4));
		하이네잡밭3.add(new Robot_Location_bean(33498, 33291, 4));
		하이네잡밭3.add(new Robot_Location_bean(33486, 33300, 4));
		하이네잡밭3.add(new Robot_Location_bean(33479, 33307, 4));
		하이네잡밭3.add(new Robot_Location_bean(33473, 33306, 4));
		하이네잡밭3.add(new Robot_Location_bean(33463, 33310, 4));
		하이네잡밭3.add(new Robot_Location_bean(33449, 33311, 4));
		하이네잡밭3.add(new Robot_Location_bean(33439, 33307, 4));
		하이네잡밭3.add(new Robot_Location_bean(33432, 33295, 4));
		하이네잡밭3.add(new Robot_Location_bean(33418, 33293, 4));
		하이네잡밭3.add(new Robot_Location_bean(33413, 33287, 4));
		하이네잡밭3.add(new Robot_Location_bean(33413, 33278, 4));
		하이네잡밭3.add(new Robot_Location_bean(33416, 33268, 4));
		하이네잡밭3.add(new Robot_Location_bean(33417, 33257, 4));
		하이네잡밭3.add(new Robot_Location_bean(33413, 33248, 4));
		하이네잡밭3.add(new Robot_Location_bean(33409, 33238, 4));
		하이네잡밭3.add(new Robot_Location_bean(33403, 33230, 4));
		하이네잡밭3.add(new Robot_Location_bean(33403, 33218, 4));
		하이네잡밭3.add(new Robot_Location_bean(33402, 33208, 4));
		하이네잡밭3.add(new Robot_Location_bean(33408, 33195, 4));
		하이네잡밭3.add(new Robot_Location_bean(33415, 33185, 4));
		하이네잡밭3.add(new Robot_Location_bean(33423, 33175, 4));
		하이네잡밭3.add(new Robot_Location_bean(33430, 33163, 4));
		하이네잡밭3.add(new Robot_Location_bean(33439, 33153, 4));
		하이네잡밭3.add(new Robot_Location_bean(33448, 33144, 4));
		하이네잡밭3.add(new Robot_Location_bean(33458, 33139, 4));
		하이네잡밭3.add(new Robot_Location_bean(33467, 33130, 4));
		하이네잡밭3.add(new Robot_Location_bean(33479, 33119, 4));
		하이네잡밭3.add(new Robot_Location_bean(33490, 33113, 4));
		하이네잡밭3.add(new Robot_Location_bean(33500, 33104, 4));
		하이네잡밭3.add(new Robot_Location_bean(33510, 33102, 4));
		하이네잡밭3.add(new Robot_Location_bean(33520, 33104, 4));
		하이네잡밭3.add(new Robot_Location_bean(33528, 33096, 4));
		하이네잡밭3.add(new Robot_Location_bean(33539, 33090, 4));
		하이네잡밭3.add(new Robot_Location_bean(33550, 33091, 4));
		하이네잡밭3.add(new Robot_Location_bean(33559, 33096, 4));
		하이네잡밭3.add(new Robot_Location_bean(33563, 33105, 4));
		하이네잡밭3.add(new Robot_Location_bean(33574, 33106, 4));
		하이네잡밭3.add(new Robot_Location_bean(33584, 33114, 4));
		하이네잡밭3.add(new Robot_Location_bean(33591, 33120, 4));
		하이네잡밭3.add(new Robot_Location_bean(33592, 33130, 4));
		하이네잡밭3.add(new Robot_Location_bean(33589, 33141, 4));
		하이네잡밭3.add(new Robot_Location_bean(33588, 33150, 4));
		하이네잡밭3.add(new Robot_Location_bean(33581, 33161, 4));
		하이네잡밭3.add(new Robot_Location_bean(33572, 33166, 4));
		하이네잡밭3.add(new Robot_Location_bean(33557, 33169, 4));
		하이네잡밭3.add(new Robot_Location_bean(33543, 33172, 4));
		하이네잡밭3.add(new Robot_Location_bean(33534, 33163, 4));
		하이네잡밭3.add(new Robot_Location_bean(33525, 33157, 4));
		하이네잡밭3.add(new Robot_Location_bean(33509, 33156, 4));
		하이네잡밭3.add(new Robot_Location_bean(33499, 33158, 4));
		하이네잡밭3.add(new Robot_Location_bean(33489, 33162, 4));
		하이네잡밭3.add(new Robot_Location_bean(33480, 33166, 4));
		하이네잡밭3.add(new Robot_Location_bean(33470, 33173, 4));
		하이네잡밭3.add(new Robot_Location_bean(33459, 33181, 4));
		하이네잡밭3.add(new Robot_Location_bean(33451, 33189, 4));
		하이네잡밭3.add(new Robot_Location_bean(33449, 33198, 4));
		하이네잡밭3.add(new Robot_Location_bean(33445, 33208, 4));

		하이네잡밭4.add(new Robot_Location_bean(33495, 33103, 4));
		하이네잡밭4.add(new Robot_Location_bean(33484, 33100, 4));
		하이네잡밭4.add(new Robot_Location_bean(33475, 33097, 4));
		하이네잡밭4.add(new Robot_Location_bean(33470, 33090, 4));
		하이네잡밭4.add(new Robot_Location_bean(33461, 33084, 4));
		하이네잡밭4.add(new Robot_Location_bean(33453, 33088, 4));
		하이네잡밭4.add(new Robot_Location_bean(33444, 33093, 4));
		하이네잡밭4.add(new Robot_Location_bean(33435, 33099, 4));
		하이네잡밭4.add(new Robot_Location_bean(33428, 33109, 4));
		하이네잡밭4.add(new Robot_Location_bean(33417, 33115, 4));
		하이네잡밭4.add(new Robot_Location_bean(33415, 33123, 4));
		하이네잡밭4.add(new Robot_Location_bean(33402, 33126, 4));
		하이네잡밭4.add(new Robot_Location_bean(33393, 33130, 4));
		하이네잡밭4.add(new Robot_Location_bean(33386, 33135, 4));
		하이네잡밭4.add(new Robot_Location_bean(33379, 33142, 4));
		하이네잡밭4.add(new Robot_Location_bean(33371, 33140, 4));
		하이네잡밭4.add(new Robot_Location_bean(33363, 33137, 4));
		하이네잡밭4.add(new Robot_Location_bean(33353, 33136, 4));
		하이네잡밭4.add(new Robot_Location_bean(33345, 33131, 4));
		하이네잡밭4.add(new Robot_Location_bean(33335, 33135, 4));
		하이네잡밭4.add(new Robot_Location_bean(33327, 33142, 4));
		하이네잡밭4.add(new Robot_Location_bean(33320, 33152, 4));
		하이네잡밭4.add(new Robot_Location_bean(33315, 33161, 4));
		하이네잡밭4.add(new Robot_Location_bean(33310, 33171, 4));
		하이네잡밭4.add(new Robot_Location_bean(33314, 33182, 4));
		하이네잡밭4.add(new Robot_Location_bean(33322, 33185, 4));
		하이네잡밭4.add(new Robot_Location_bean(33326, 33195, 4));
		하이네잡밭4.add(new Robot_Location_bean(33335, 33190, 4));
		하이네잡밭4.add(new Robot_Location_bean(33343, 33195, 4));
		하이네잡밭4.add(new Robot_Location_bean(33343, 33204, 4));
		하이네잡밭4.add(new Robot_Location_bean(33342, 33209, 4));
		하이네잡밭4.add(new Robot_Location_bean(33336, 33220, 4));
		하이네잡밭4.add(new Robot_Location_bean(33336, 33234, 4));
		하이네잡밭4.add(new Robot_Location_bean(33339, 33243, 4));
		하이네잡밭4.add(new Robot_Location_bean(33348, 33248, 4));
		하이네잡밭4.add(new Robot_Location_bean(33358, 33250, 4));
		하이네잡밭4.add(new Robot_Location_bean(33354, 33259, 4));
		하이네잡밭4.add(new Robot_Location_bean(33346, 33269, 4));
		하이네잡밭4.add(new Robot_Location_bean(33335, 33274, 4));
		하이네잡밭4.add(new Robot_Location_bean(33331, 33282, 4));
		하이네잡밭4.add(new Robot_Location_bean(33321, 33287, 4));
		하이네잡밭4.add(new Robot_Location_bean(33312, 33285, 4));
		하이네잡밭4.add(new Robot_Location_bean(33308, 33276, 4));
		하이네잡밭4.add(new Robot_Location_bean(33301, 33269, 4));
		하이네잡밭4.add(new Robot_Location_bean(33292, 33264, 4));
		하이네잡밭4.add(new Robot_Location_bean(33295, 33257, 4));
		하이네잡밭4.add(new Robot_Location_bean(33288, 33255, 4));
		하이네잡밭4.add(new Robot_Location_bean(33279, 33255, 4));
		하이네잡밭4.add(new Robot_Location_bean(33275, 33266, 4));
		하이네잡밭4.add(new Robot_Location_bean(33280, 33271, 4));
		하이네잡밭4.add(new Robot_Location_bean(33283, 33279, 4));
		하이네잡밭4.add(new Robot_Location_bean(33286, 33286, 4));
		하이네잡밭4.add(new Robot_Location_bean(33291, 33292, 4));
		하이네잡밭4.add(new Robot_Location_bean(33297, 33299, 4));
		하이네잡밭4.add(new Robot_Location_bean(33300, 33307, 4));
		하이네잡밭4.add(new Robot_Location_bean(33303, 33312, 4));
		하이네잡밭4.add(new Robot_Location_bean(33311, 33319, 4));
		하이네잡밭4.add(new Robot_Location_bean(33316, 33326, 4));
		하이네잡밭4.add(new Robot_Location_bean(33322, 33331, 4));
		하이네잡밭4.add(new Robot_Location_bean(33325, 33340, 4));
		하이네잡밭4.add(new Robot_Location_bean(33324, 33349, 4));
		하이네잡밭4.add(new Robot_Location_bean(33325, 33357, 4));
		하이네잡밭4.add(new Robot_Location_bean(33332, 33359, 4));
		하이네잡밭4.add(new Robot_Location_bean(33340, 33360, 4));
		하이네잡밭4.add(new Robot_Location_bean(33348, 33363, 4));
		하이네잡밭4.add(new Robot_Location_bean(33354, 33369, 4));
		하이네잡밭4.add(new Robot_Location_bean(33364, 33366, 4));
		하이네잡밭4.add(new Robot_Location_bean(33370, 33357, 4));
		하이네잡밭4.add(new Robot_Location_bean(33372, 33345, 4));
		하이네잡밭4.add(new Robot_Location_bean(33379, 33336, 4));
		하이네잡밭4.add(new Robot_Location_bean(33382, 33327, 4));
		하이네잡밭4.add(new Robot_Location_bean(33382, 33315, 4));
		하이네잡밭4.add(new Robot_Location_bean(33383, 33307, 4));
		하이네잡밭4.add(new Robot_Location_bean(33382, 33298, 4));
		하이네잡밭4.add(new Robot_Location_bean(33382, 33289, 4));
		하이네잡밭4.add(new Robot_Location_bean(33379, 33283, 4));
		하이네잡밭4.add(new Robot_Location_bean(33383, 33275, 4));
		하이네잡밭4.add(new Robot_Location_bean(33384, 33267, 4));
		하이네잡밭4.add(new Robot_Location_bean(33377, 33262, 4));
		하이네잡밭4.add(new Robot_Location_bean(33377, 33254, 4));
		하이네잡밭4.add(new Robot_Location_bean(33376, 33246, 4));
		하이네잡밭4.add(new Robot_Location_bean(33383, 33237, 4));
		하이네잡밭4.add(new Robot_Location_bean(33383, 33226, 4));
		하이네잡밭4.add(new Robot_Location_bean(33388, 33216, 4));
		하이네잡밭4.add(new Robot_Location_bean(33395, 33209, 4));
		하이네잡밭4.add(new Robot_Location_bean(33395, 33203, 4));
		하이네잡밭4.add(new Robot_Location_bean(33395, 33194, 4));
		하이네잡밭4.add(new Robot_Location_bean(33396, 33184, 4));
		하이네잡밭4.add(new Robot_Location_bean(33400, 33177, 4));
		하이네잡밭4.add(new Robot_Location_bean(33410, 33169, 4));
		하이네잡밭4.add(new Robot_Location_bean(33418, 33161, 4));
		하이네잡밭4.add(new Robot_Location_bean(33428, 33154, 4));
		하이네잡밭4.add(new Robot_Location_bean(33442, 33146, 4));
		하이네잡밭4.add(new Robot_Location_bean(33450, 33141, 4));
		하이네잡밭4.add(new Robot_Location_bean(33459, 33137, 4));
		하이네잡밭4.add(new Robot_Location_bean(33468, 33132, 4));
		하이네잡밭4.add(new Robot_Location_bean(33478, 33122, 4));
		하이네잡밭4.add(new Robot_Location_bean(33488, 33117, 4));
		하이네잡밭4.add(new Robot_Location_bean(33499, 33115, 4));
		하이네잡밭4.add(new Robot_Location_bean(33507, 33109, 4));
		하이네잡밭4.add(new Robot_Location_bean(33516, 33106, 4));
		하이네잡밭4.add(new Robot_Location_bean(33527, 33107, 4));
		하이네잡밭4.add(new Robot_Location_bean(33537, 33106, 4));
		하이네잡밭4.add(new Robot_Location_bean(33545, 33107, 4));
		하이네잡밭4.add(new Robot_Location_bean(33552, 33104, 4));
		하이네잡밭4.add(new Robot_Location_bean(33559, 33103, 4));
		하이네잡밭4.add(new Robot_Location_bean(33567, 33104, 4));
		하이네잡밭4.add(new Robot_Location_bean(33575, 33099, 4));
		하이네잡밭4.add(new Robot_Location_bean(33582, 33090, 4));
		하이네잡밭4.add(new Robot_Location_bean(33589, 33081, 4));
		하이네잡밭4.add(new Robot_Location_bean(33596, 33071, 4));
		하이네잡밭4.add(new Robot_Location_bean(33598, 33062, 4));
		하이네잡밭4.add(new Robot_Location_bean(33600, 33051, 4));
		하이네잡밭4.add(new Robot_Location_bean(33595, 33039, 4));
		하이네잡밭4.add(new Robot_Location_bean(33584, 33033, 4));
		하이네잡밭4.add(new Robot_Location_bean(33575, 33037, 4));
		하이네잡밭4.add(new Robot_Location_bean(33566, 33034, 4));
		하이네잡밭4.add(new Robot_Location_bean(33554, 33033, 4));
		하이네잡밭4.add(new Robot_Location_bean(33543, 33037, 4));
		하이네잡밭4.add(new Robot_Location_bean(33534, 33036, 4));
		하이네잡밭4.add(new Robot_Location_bean(33524, 33038, 4));
		하이네잡밭4.add(new Robot_Location_bean(33516, 33039, 4));
		하이네잡밭4.add(new Robot_Location_bean(33508, 33047, 4));
		하이네잡밭4.add(new Robot_Location_bean(33499, 33054, 4));
		하이네잡밭4.add(new Robot_Location_bean(33497, 33061, 4));
		하이네잡밭4.add(new Robot_Location_bean(33491, 33071, 4));
		하이네잡밭4.add(new Robot_Location_bean(33482, 33074, 4));
		하이네잡밭4.add(new Robot_Location_bean(33473, 33079, 4));
		하이네잡밭4.add(new Robot_Location_bean(33463, 33084, 4));
		하이네잡밭4.add(new Robot_Location_bean(33455, 33087, 4));
		하이네잡밭4.add(new Robot_Location_bean(33451, 33097, 4));
		하이네잡밭4.add(new Robot_Location_bean(33454, 33106, 4));
		하이네잡밭4.add(new Robot_Location_bean(33454, 33115, 4));
		하이네잡밭4.add(new Robot_Location_bean(33454, 33126, 4));
		하이네잡밭4.add(new Robot_Location_bean(33456, 33136, 4));
		하이네잡밭4.add(new Robot_Location_bean(33466, 33131, 4));
		하이네잡밭4.add(new Robot_Location_bean(33471, 33124, 4));
		하이네잡밭4.add(new Robot_Location_bean(33479, 33115, 4));
		하이네잡밭4.add(new Robot_Location_bean(33488, 33112, 4));
		하이네잡밭4.add(new Robot_Location_bean(33498, 33103, 4));

		하이네잡밭5.add(new Robot_Location_bean(33347, 33219, 4));
		하이네잡밭5.add(new Robot_Location_bean(33354, 33214, 4));
		하이네잡밭5.add(new Robot_Location_bean(33362, 33211, 4));
		하이네잡밭5.add(new Robot_Location_bean(33372, 33210, 4));
		하이네잡밭5.add(new Robot_Location_bean(33380, 33212, 4));
		하이네잡밭5.add(new Robot_Location_bean(33387, 33212, 4));
		하이네잡밭5.add(new Robot_Location_bean(33395, 33220, 4));
		하이네잡밭5.add(new Robot_Location_bean(33403, 33225, 4));
		하이네잡밭5.add(new Robot_Location_bean(33412, 33221, 4));
		하이네잡밭5.add(new Robot_Location_bean(33420, 33218, 4));
		하이네잡밭5.add(new Robot_Location_bean(33427, 33211, 4));
		하이네잡밭5.add(new Robot_Location_bean(33435, 33210, 4));
		하이네잡밭5.add(new Robot_Location_bean(33442, 33212, 4));
		하이네잡밭5.add(new Robot_Location_bean(33446, 33205, 4));
		하이네잡밭5.add(new Robot_Location_bean(33450, 33194, 4));
		하이네잡밭5.add(new Robot_Location_bean(33454, 33183, 4));
		하이네잡밭5.add(new Robot_Location_bean(33461, 33175, 4));
		하이네잡밭5.add(new Robot_Location_bean(33467, 33167, 4));
		하이네잡밭5.add(new Robot_Location_bean(33475, 33159, 4));
		하이네잡밭5.add(new Robot_Location_bean(33474, 33150, 4));
		하이네잡밭5.add(new Robot_Location_bean(33468, 33142, 4));
		하이네잡밭5.add(new Robot_Location_bean(33460, 33139, 4));
		하이네잡밭5.add(new Robot_Location_bean(33457, 33132, 4));
		하이네잡밭5.add(new Robot_Location_bean(33450, 33131, 4));
		하이네잡밭5.add(new Robot_Location_bean(33443, 33134, 4));
		하이네잡밭5.add(new Robot_Location_bean(33434, 33134, 4));
		하이네잡밭5.add(new Robot_Location_bean(33424, 33137, 4));
		하이네잡밭5.add(new Robot_Location_bean(33418, 33131, 4));
		하이네잡밭5.add(new Robot_Location_bean(33412, 33127, 4));
		하이네잡밭5.add(new Robot_Location_bean(33403, 33132, 4));
		하이네잡밭5.add(new Robot_Location_bean(33394, 33133, 4));
		하이네잡밭5.add(new Robot_Location_bean(33385, 33135, 4));
		하이네잡밭5.add(new Robot_Location_bean(33376, 33142, 4));
		하이네잡밭5.add(new Robot_Location_bean(33371, 33152, 4));
		하이네잡밭5.add(new Robot_Location_bean(33369, 33165, 4));
		하이네잡밭5.add(new Robot_Location_bean(33365, 33177, 4));
		하이네잡밭5.add(new Robot_Location_bean(33359, 33183, 4));
		하이네잡밭5.add(new Robot_Location_bean(33361, 33193, 4));
		하이네잡밭5.add(new Robot_Location_bean(33369, 33194, 4));
		하이네잡밭5.add(new Robot_Location_bean(33376, 33193, 4));
		하이네잡밭5.add(new Robot_Location_bean(33382, 33195, 4));
		하이네잡밭5.add(new Robot_Location_bean(33387, 33187, 4));
		하이네잡밭5.add(new Robot_Location_bean(33391, 33190, 4));
		하이네잡밭5.add(new Robot_Location_bean(33394, 33193, 4));
		하이네잡밭5.add(new Robot_Location_bean(33391, 33201, 4));
		하이네잡밭5.add(new Robot_Location_bean(33396, 33207, 4));
		하이네잡밭5.add(new Robot_Location_bean(33394, 33213, 4));
		하이네잡밭5.add(new Robot_Location_bean(33382, 33220, 4));
		하이네잡밭5.add(new Robot_Location_bean(33366, 33219, 4));
		하이네잡밭5.add(new Robot_Location_bean(33356, 33227, 4));
		하이네잡밭5.add(new Robot_Location_bean(33352, 33236, 4));
		하이네잡밭5.add(new Robot_Location_bean(33352, 33243, 4));
		하이네잡밭5.add(new Robot_Location_bean(33343, 33246, 4));
		하이네잡밭5.add(new Robot_Location_bean(33338, 33237, 4));
		하이네잡밭5.add(new Robot_Location_bean(33333, 33231, 4));
		하이네잡밭5.add(new Robot_Location_bean(33331, 33224, 4));
		하이네잡밭5.add(new Robot_Location_bean(33328, 33218, 4));
		하이네잡밭5.add(new Robot_Location_bean(33330, 33208, 4));
		하이네잡밭5.add(new Robot_Location_bean(33340, 33199, 4));
		하이네잡밭5.add(new Robot_Location_bean(33348, 33191, 4));
		하이네잡밭5.add(new Robot_Location_bean(33357, 33188, 4));
		하이네잡밭5.add(new Robot_Location_bean(33364, 33192, 4));
		하이네잡밭5.add(new Robot_Location_bean(33368, 33203, 4));
		하이네잡밭5.add(new Robot_Location_bean(33364, 33216, 4));
		하이네잡밭5.add(new Robot_Location_bean(33357, 33226, 4));
		하이네잡밭5.add(new Robot_Location_bean(33354, 33233, 4));
		하이네잡밭5.add(new Robot_Location_bean(33349, 33226, 4));
        */
		
		지저.add(new Robot_Location_bean(32800, 33051, 420));
		
		잊섬.add(new Robot_Location_bean(32645, 33009, 1700)); //입섬세프티존입구
		잊섬2.add(new Robot_Location_bean(32754, 32942, 1700)); //남쪽골렘연구소입구
		잊섬3.add(new Robot_Location_bean(32694, 32716, 1700)); //서쪽골렘입구
		잊섬4.add(new Robot_Location_bean(32926, 32890, 1700)); //동쪽골렘입구
		잊섬5.add(new Robot_Location_bean(32955, 32800, 1700)); //히든상점

		선박심해.add(new Robot_Location_bean(33011, 33011, 558));
		선박심해2.add(new Robot_Location_bean(33011, 33012, 558));
		선박심해3.add(new Robot_Location_bean(33011, 33013, 558));

		용던1층.add(new Robot_Location_bean(32799, 32742, 30));
		용던2층.add(new Robot_Location_bean(32761, 32788, 31));
		용던3층.add(new Robot_Location_bean(32703, 32833, 32));
		용던4층.add(new Robot_Location_bean(32677, 32860, 33));
		용던5층.add(new Robot_Location_bean(32742, 32794, 35));
		용던6층.add(new Robot_Location_bean(32666, 32862, 36));
		용던7층.add(new Robot_Location_bean(32664, 32838, 37));

//160316
		리뉴얼오만1층.add(new Robot_Location_bean(32725, 32794, 101));
		리뉴얼오만2층.add(new Robot_Location_bean(32730, 32802, 102));
		리뉴얼오만3층.add(new Robot_Location_bean(32726, 32802, 103));
		리뉴얼오만4층.add(new Robot_Location_bean(32620, 32858, 104));
		리뉴얼오만5층.add(new Robot_Location_bean(32602, 32866, 105));
		리뉴얼오만6층.add(new Robot_Location_bean(32611, 32862, 106));
		리뉴얼오만7층.add(new Robot_Location_bean(32618, 32865, 107));
		리뉴얼오만8층.add(new Robot_Location_bean(32602, 32866, 108));
		리뉴얼오만9층.add(new Robot_Location_bean(32613, 32866, 109));
		리뉴얼오만10층.add(new Robot_Location_bean(32730, 32802, 110));
//160316


		본던1층.add(new Robot_Location_bean(32812, 32726, 807));
		본던2층.add(new Robot_Location_bean(32750, 32798, 808));
		본던3층.add(new Robot_Location_bean(32801, 32754, 809));
		본던4층.add(new Robot_Location_bean(32763, 32773, 810));
		본던5층.add(new Robot_Location_bean(32728, 32723, 811));
		본던6층.add(new Robot_Location_bean(32804, 32725, 812));
		본던7층.add(new Robot_Location_bean(32727, 32725, 813));

		상아탑4층.add(new Robot_Location_bean(32901, 32765, 280));
		상아탑5층.add(new Robot_Location_bean(32810, 32865, 281));

		개미굴1.add(new Robot_Location_bean(32784, 32751, 43));
		개미굴2.add(new Robot_Location_bean(32798, 32754, 44));
		개미굴3.add(new Robot_Location_bean(32759, 32742, 45));
		개미굴4.add(new Robot_Location_bean(32750, 32764, 46));
		개미굴5.add(new Robot_Location_bean(32795, 32746, 47));
		개미굴6.add(new Robot_Location_bean(32768, 32805, 50));
		
		기감1층.add(new Robot_Location_bean(32805, 32738, 53));
		기감2층.add(new Robot_Location_bean(32808, 32796, 54));
		기감3층.add(new Robot_Location_bean(32736, 32729, 55));
		기감4층.add(new Robot_Location_bean(32768, 32820, 56));
		

	}

	

	

}