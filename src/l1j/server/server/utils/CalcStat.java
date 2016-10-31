/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */

package l1j.server.server.utils;

import java.util.Random;

import l1j.server.Config;

public class CalcStat {

	private static Random rnd = new Random(System.nanoTime());

	private CalcStat() {
	}

	/*
	 * private static final int[] maxWeight={ // Str+Con 1500, 1650, 1800, 1800,
	 * 1950, 2100, 2100, 2250, 2250, 2400, // 20~30 2550, 2550, 2700, 2700,
	 * 2850, 3000, 3000, 3150, 3150, 3300, // 30~40 3450, 3450, 3600, 3600,
	 * 3750, 3900, 3900, 4050, 4050, 4200, // 40~50 4350, 4350, 4500, 4500,
	 * 4650, 4800, 4800, 4950, 4950, 5100, // 50~60 5250, 5250, 5400, 5400,
	 * 5550, 5700, 5700, 5850, 5850, 6000, // 60~70 6150, 6150, 6300, 6300,
	 * 6450, 6600, 6600, 6750, 6750, 6900, // 70~80 7050, 7050, 7200, 7200,
	 * 7350, 7500, 7500, 7650, 7650, 7650, // 80~90 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, //200 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650, 7650,
	 * 7650, 7650, 7650, 7650, 7650 };
	 */

	/**
	 * AC���ʽ��� �����ش�
	 * 
	 * @param level
	 * @param dex
	 * @return acBonus
	 * 
	 */
	/*
	 * public static int calcAc(int level, int dex) { int acBonus = 10;
	 * switch(dex){ case 6: case 7: case 8: case 9: acBonus -= level / 8; break;
	 * case 10: case 11: case 12: acBonus -= level / 7; break; case 13: case 14:
	 * case 15: acBonus -= level / 6; break; case 16: case 17: acBonus -= level
	 * / 5; break; default: acBonus -= level / 4; break; } return acBonus; }
	 */
	/**
	 * ���̽����ݿ����� �ٰŸ� ����
	 * 
	 * @param pc
	 * @param str
	 * @return Hitup
	 * 
	 */
	/*
	 * public static int calcBaseHitup(int chartype, int str) { int Hitup = 0;
	 * switch(chartype){ case 0: if(str >= 16){ Hitup += 1; } if(str >= 19){
	 * Hitup += 2; } break; case 1: if(str >= 17){ Hitup += 2; } if(str >= 19){
	 * Hitup += 3; } break; case 2: if(str >= 13){ Hitup +=1; } if(str >= 15){
	 * Hitup +=2; } break; case 3: if(str >= 11){ Hitup += 1; } if(str >= 13){
	 * Hitup += 2; } break; case 4: if(str >= 15){ Hitup += 1; } if(str >= 17){
	 * Hitup += 2; } break; case 5: if(str >= 14){ Hitup += 1; } if(str >= 17){
	 * Hitup += 3; } break; case 6: if(str >= 12){ Hitup += 1; } if(str >= 14){
	 * Hitup += 1; } if(str >= 16){ Hitup += 1; } if(str >= 17){ Hitup += 2; }
	 * break; case 7: if(str >= 17){ Hitup += 2; } if(str >= 19){ Hitup += 2; }
	 * break; default: break; } return Hitup; }
	 */
	/**
	 * ���̽� ���ݿ� ���� �ٰŸ� ������
	 * 
	 * @param pc
	 * @param str
	 * @return Dmgup
	 * 
	 */
	/*
	 * public static int calcBaseDmgup(int chartype, int str) { int Dmgup = 0;
	 * switch(chartype){ case 0: if(str >= 15){ Dmgup += 1; } if(str >= 18){
	 * Dmgup += 2; } break; case 1: if(str >= 18){ Dmgup += 2; } if(str >= 20){
	 * Dmgup += 3; } break; case 2: if(str >= 12){ Dmgup +=1; } if(str >= 14){
	 * Dmgup +=2; } break; case 3: if(str >= 10){ Dmgup += 1; } if(str >= 12){
	 * Dmgup += 2; } break; case 4: if(str >= 14){ Dmgup += 1; } if(str >= 18){
	 * Dmgup += 2; } break; case 5: if(str >= 15){ Dmgup += 1; } if(str >= 18){
	 * Dmgup += 2; } break; case 6: if(str >= 13){ Dmgup += 1; } if(str >= 15){
	 * Dmgup += 2; } break; case 7: if(str >= 18){ Dmgup += 2; } if(str >= 20){
	 * Dmgup += 2; } break; default: break; } return Dmgup; }
	 */
	/**
	 * ���̽� ���ݿ� ���� ���Ÿ� ���߷�
	 * 
	 * @param pc
	 * @param dex
	 * @return BowHitup
	 * 
	 */
	/*
	 * public static int calcBaseBowHitup(int chartype, int dex) { int BowHitup
	 * = 0; switch(chartype){ case 0: BowHitup = 0; break; case 1: BowHitup = 0;
	 * break; case 2: if(dex >= 13){ BowHitup +=2; } if(dex >= 16){ BowHitup
	 * +=2; } break; case 3: BowHitup = 0; break; case 4: if(dex >= 17){
	 * BowHitup += 1; } if(dex >= 18){ BowHitup += 2; } break; case 5: BowHitup
	 * = 0; break; case 6: BowHitup = 0; break; case 7: BowHitup = 0; break;
	 * default: break; } return BowHitup; }
	 */
	/**
	 * ���̽� ���ݿ� ���� ���Ÿ� ������
	 * 
	 * @param pc
	 * @param dex
	 * @return BowDmgup
	 * 
	 */
	/*
	 * public static int calcBaseBowDmgup(int chartype, int dex) { int BowDmgup
	 * = 0; switch(chartype){ case 0: if(dex >= 13){ BowDmgup += 2; } break;
	 * case 1: BowDmgup = 0; break; case 2: if(dex >= 14){ BowDmgup +=2; }
	 * if(dex >= 17){ BowDmgup +=2; } break; case 3: BowDmgup = 0; break; case
	 * 4: if(dex >= 18){ BowDmgup += 3; } break; case 5: BowDmgup = 0; break;
	 * case 6: BowDmgup = 0; break; case 7: BowDmgup = 0; break; default: break;
	 * } return BowDmgup; }
	 */
	/**
	 * 
	 * ���̽� ���ȿ� ���� ���� ����
	 * 
	 * @param chartype
	 * @param baseint
	 * @return magicHit
	 */
	/*
	 * public static int calcBaseMagicHitUp(int chartype, int baseint){ int
	 * magicHit = 0; switch(chartype){ case 0: if (baseint >= 12){ magicHit +=
	 * 1; } if (baseint >= 14){ magicHit += 1; } break; case 1: if (baseint >=
	 * 10){ magicHit += 1; } if (baseint >= 12){ magicHit += 1; } break; case 2:
	 * if (baseint >= 13){ magicHit += 1; } if (baseint >=15){ magicHit += 1; }
	 * break; case 3: if (baseint >= 14){ magicHit += 1; } break; case 4: if
	 * (baseint >= 12){ magicHit += 1; } if (baseint >= 14){ magicHit += 1; }
	 * break; case 5: if (baseint >= 12){ magicHit += 1; } if (baseint >= 14){
	 * magicHit += 1; } break; case 6: if (baseint >= 13){ magicHit += 1; }
	 * break; case 7: if (baseint >= 12){ magicHit += 1; } if (baseint >= 14){
	 * magicHit += 1; } break; default: break; } return magicHit; }
	 */
	/**
	 * 
	 * ���̽� ���ݿ� ���� ���� ġ��Ÿ (%)
	 * 
	 * @param chartype
	 * @param baseint
	 * @return mc
	 */
	/*
	 * public static int calcBaseMagicCritical(int chartype, int baseint){ int
	 * mc = 0; switch(chartype){ case 0: case 1: mc = 0; break; case 2: if
	 * (baseint >= 14){ mc += 2; } if (baseint >= 16){ mc += 2; } break; case 3:
	 * if (baseint >= 15){ mc += 2; } if (baseint >= 16){ mc += 2; } if (baseint
	 * >= 17){ mc += 2; } if (baseint >= 18){ mc += 2; } if (baseint >= 19){ mc
	 * += 2; } if (baseint >= 20){ mc += 1; } if (baseint >= 21){ mc += 1; } if
	 * (baseint >= 22){ mc += 1; } if (baseint >= 23){ mc += 1; } if (baseint >=
	 * 24){ mc += 1; } if (baseint >= 25){ mc += 1; } break; case 4: case 5:
	 * case 6: case 7: mc = 0; break; default:break; } return mc; }
	 */
	/**
	 * 
	 * ���̽� ���ݿ� ���� ���� ������
	 * 
	 * @param chartype
	 * @param baseint
	 * @return md
	 */
	/*
	 * public static int calcBaseMagicDmg(int chartype, int baseint){ int md =
	 * 0; switch(chartype){ case 0: case 1: case 2: case 7: md = 0; break; case
	 * 3: if (baseint >= 13){ md+=1; } break; case 4: md = 0; break; case 5: if
	 * (baseint >= 13){ md += 1; } if (baseint >= 15){ md += 1; } if (baseint >=
	 * 17){ md += 1; } break; case 6: if (baseint >= 16){ md += 1; } if (baseint
	 * >= 17){ md += 1; } break; default: break; } return md; }
	 */
	/**
	 * 
	 * ���̽� ���ݿ� ���� MP ���ҷ�
	 * 
	 * @param chartype
	 * @param baseint
	 * @return dmp
	 */
	/*
	 * public static int calcBaseDecreaseMp(int chartype, int baseint){ int dmp
	 * = 0; switch(chartype){ case 0: if (baseint >= 11){ dmp += 1; } if
	 * (baseint >= 13){ dmp += 1; } break; case 1: if (baseint >= 9){ dmp += 1;
	 * } if (baseint >= 11){ dmp += 1; } break; case 2: dmp = 0; break; case 3:
	 * dmp = 0; break; case 4: if (baseint >= 13){ dmp += 1; } if (baseint >=
	 * 15){ dmp += 1; } break; case 5: dmp = 0; break; case 6: if (baseint >=
	 * 14){ dmp += 1; } if (baseint >= 15){ dmp += 1; } break; case 7: if
	 * (baseint >= 11){ dmp += 1; } if (baseint >= 13){ dmp += 1; } break;
	 * default: break; } return dmp; }
	 */
	/**
	 * ���̽� ���ݿ� ���� ER(���Ÿ� ȸ����)
	 * 
	 * @param pc
	 * @param dex
	 * @return Er
	 * 
	 */
	/*
	 * public static int calcBaseEr(int chartype, int dex) { int Er = 0;
	 * switch(chartype){ case 0: if(dex >= 14){ Er += 1; } if(dex >= 16){ Er +=
	 * 1; } if(dex >= 18){ Er += 1; } break; case 1: if(dex >=14){ Er += 1; }
	 * if(dex >=16){ Er += 2; } break; case 2: Er = 0; break; case 3: if(dex >=
	 * 9){ Er += 1; } if(dex >= 11){ Er += 1; } break; case 4: if(dex >= 16){ Er
	 * += 2; } break; case 5: if(dex >= 13){ Er += 1; } if(dex >= 15){ Er += 1;
	 * } break; case 6: if(dex >= 12){ Er += 1; } if(dex >= 14){ Er += 1; }
	 * break; case 7: if(dex >=15){ Er += 1; } if(dex >=17){ Er += 2; } break;
	 * default: break; } return Er; }
	 */
	/**
	 * ���̽� ���ݿ� ���� MR(���� ����)
	 * 
	 * @param pc
	 * @param wis
	 * @return MR
	 * 
	 */
	/*
	 * public static int calcBaseMr(int chartype, int wis) { int MR = 0;
	 * switch(chartype){ case 0: if(wis >= 12) MR += 1; if(wis >= 14) MR += 1;
	 * break; case 1: if(wis >= 10) MR += 1; if(wis >= 12) MR += 2; break; case
	 * 2: if(wis >= 13) MR += 1; if(wis >= 16) MR += 1; break; case 3: if(wis >=
	 * 15) MR += 1; break; case 4: if(wis >= 11) MR += 1; if(wis >= 14) MR += 1;
	 * if(wis >= 15) MR += 1; if(wis >= 16) MR += 1; break; case 5: if(wis >=
	 * 14) MR += 2; break; case 6: if(wis >= 15) MR += 2; if(wis >= 18) MR += 2;
	 * break; case 7: if(wis >= 8) MR += 1; if(wis >= 10) MR += 2; break;
	 * default: break; } return MR; }
	 */
	/**
	 * ���̽����ݿ����� AC���ʽ��� �����ش�.
	 * 
	 * @param pc
	 * @param dex
	 * @return acBonus
	 * 
	 */
	/*
	 * public static int calcBaseAc(int chartype, int dex) { int acBonus = 0;
	 * switch(chartype){ case 0: if(dex >= 12) { acBonus -= 1; } if(dex >= 15){
	 * acBonus -= 1; } if(dex >= 17){ acBonus -= 1; } break; case 1: if(dex >=
	 * 13) { acBonus -= 1; } if(dex >= 15){ acBonus -= 2; } break; case 2:
	 * if(dex >= 15) { acBonus -= 1; } if(dex >= 18){ acBonus -= 1; } break;
	 * case 3: if(dex >= 8) { acBonus -= 1; } if(dex >= 10){ acBonus -= 1; }
	 * break; case 4: if(dex >= 17) { acBonus -= 1; } break; case 5: if(dex >=
	 * 12) { acBonus -= 1; } if(dex >= 14){ acBonus -= 1; } break; case 6:
	 * if(dex >= 11) { acBonus -= 1; } if(dex >= 13){ acBonus -= 1; } break;
	 * case 7: if(dex >= 14) { acBonus -= 1; } if(dex >= 16){ acBonus -= 2; }
	 * break; default: break; } return acBonus; }
	 */
	/**
	 * ���̽����ݿ����� HP ȸ�� ���ʽ��� �����ش�.
	 * 
	 * @param chartype
	 *            (ĳ����Ÿ��)
	 * @param con
	 * @return BonusHpr
	 * 
	 */
	/*
	 * public static int calcBaseHpr(int chartype, int con) { int BonusHpr = 0;
	 * switch(chartype){ case 0: if(con >= 13) { BonusHpr += 1; } if(con >= 15){
	 * BonusHpr += 1; } if(con >= 17){ BonusHpr += 1; } if(con >= 18){ BonusHpr
	 * += 1; } break; case 1: if(con >= 16) { BonusHpr += 2; } if(con >= 18){
	 * BonusHpr += 2; } break; case 2: if(con >= 14) { BonusHpr += 1; } if(con
	 * >= 16){ BonusHpr += 1; } if(con >= 17){ BonusHpr += 1; } break; case 3:
	 * if(con >= 17) { BonusHpr += 1; } if(con >= 18){ BonusHpr += 1; } break;
	 * case 4: if(con >= 11) { BonusHpr += 1; } if(con >= 13) { BonusHpr += 1; }
	 * break; case 5: if(con >= 16) { BonusHpr += 1; } if(con >= 18){ BonusHpr
	 * += 2; } break; case 6: if(con >= 14) { BonusHpr += 1; } if(con >= 16){
	 * BonusHpr += 1; } break; case 7: if(con >= 18) { BonusHpr += 2; } if(con
	 * >= 20){ BonusHpr += 2; } break; default: break; } return BonusHpr; }
	 */
	/**
	 * ���̽����ݿ����� MP ȸ�� ���ʽ��� �����ش�.
	 * 
	 * @param chartype
	 *            (ĳ����Ÿ��)
	 * @param con
	 * @return BonusMpr
	 * 
	 */
	/*
	 * public static int calcBaseMpr(int chartype, int wis) { int BonusMpr = 0;
	 * switch(chartype){ case 0: if(wis >= 13) { BonusMpr += 1; } if(wis >= 15){
	 * BonusMpr += 1; } break; case 1: if(wis >= 10) { BonusMpr += 1; } if(wis
	 * >= 12){ BonusMpr += 2; } break; case 2: if(wis >= 15) { BonusMpr += 1; }
	 * if(wis >= 18){ BonusMpr += 1; } break; case 3: if(wis >= 14) { BonusMpr
	 * += 1; } if(wis >= 16){ BonusMpr += 1; } if(wis >= 18){ BonusMpr += 1; }
	 * break; case 4: if(wis >= 13) { BonusMpr += 1; } break; case 5: if(wis >=
	 * 15) { BonusMpr += 1; } if(wis >= 17){ BonusMpr += 1; } break; case 6:
	 * if(wis >= 14) { BonusMpr += 1; } if(wis >= 17){ BonusMpr += 1; } break;
	 * case 7: if(wis >= 9) { BonusMpr += 1; } if(wis >= 11){ BonusMpr += 1; }
	 * break; default: break; } return BonusMpr; }
	 */
	/**
	 * �μ��� WIS�� �����ϴ� MR���ʽ��� �����ش�
	 * 
	 * @param wis
	 * @return mrBonus
	 */
	/*
	 * public static int calcStatMr(int wis) { int mrBonus = 0; if (wis <= 14) {
	 * mrBonus = 0; } else if (wis >= 15 && wis <= 16) { mrBonus = 3; } else if
	 * (wis == 17) { mrBonus = 6; } else if (wis == 18) { mrBonus = 10; } else
	 * if (wis == 19) { mrBonus = 15; } else if (wis == 20) { mrBonus = 21; }
	 * else if (wis == 21) { mrBonus = 28; } else if (wis == 22) { mrBonus = 37;
	 * } else if (wis == 23) { mrBonus = 47; } else if (wis >= 24){ mrBonus =
	 * 50; } else if (wis >= 30){ mrBonus = 52; } else if (wis >= 35){ mrBonus =
	 * 55; } else if (wis >= 40){ mrBonus = 59; } else if (wis >= 45){ mrBonus =
	 * 62; } return mrBonus; }
	 */
	/*
	 * public static int calcDiffMr(int wis, int diff) { return calcStatMr(wis +
	 * diff) - calcStatMr(wis); }
	 */

	public static int[] ����������������(int charType, int wis) {
		int �ּҿ��� = 0;
		int �ִ뿥�� = 0;
		switch (charType) {
		case 0:
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				�ּҿ��� = 3;
				�ִ뿥�� = 4;
				break;
			case 12:
			case 13:
			case 14:
				�ּҿ��� = 3;
				�ִ뿥�� = 5;
				break;
			case 15:
			case 16:
			case 17:
				�ּҿ��� = 4;
				�ִ뿥�� = 6;
				break;
			case 18:
			case 19:
				�ּҿ��� = 4;
				�ִ뿥�� = 7;
				break;
			case 20:
				�ּҿ��� = 5;
				�ִ뿥�� = 7;
				break;
			case 21:
			case 22:
			case 23:
				�ּҿ��� = 5;
				�ִ뿥�� = 8;
				break;
			case 24:
				�ּҿ��� = 5;
				�ִ뿥�� = 9;
				break;
			case 25:
			case 26:
				�ּҿ��� = 6;
				�ִ뿥�� = 9;
				break;
			case 27:
			case 28:
			case 29:
				�ּҿ��� = 6;
				�ִ뿥�� = 10;
				break;
			case 30:
			case 31:
			case 32:
				�ּҿ��� = 7;
				�ִ뿥�� = 11;
				break;
			case 33:
			case 34:
				�ּҿ��� = 7;
				�ִ뿥�� = 12;
				break;
			case 35:
				�ּҿ��� = 8;
				�ִ뿥�� = 12;
				break;
			case 36:
			case 37:
			case 38:
				�ּҿ��� = 8;
				�ִ뿥�� = 13;
				break;
			case 39:
				�ּҿ��� = 8;
				�ִ뿥�� = 14;
				break;
			case 40:
			case 41:
				�ּҿ��� = 9;
				�ִ뿥�� = 14;
				break;
			case 42:
			case 43:
			case 44:
				�ּҿ��� = 9;
				�ִ뿥�� = 15;
				break;
			case 45:
				�ּҿ��� = 10;
				�ִ뿥�� = 16;
				break;
			default:
				break;
			}
			;
			if (wis > 45) {
				�ּҿ��� = 10;
				�ִ뿥�� = 16;
			}
			break;// ����
		case 1:// ���
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				�ּҿ��� = 0;
				�ִ뿥�� = 2;
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				�ּҿ��� = 1;
				�ִ뿥�� = 2;
				break;
			case 15:
			case 16:
			case 17:
				�ּҿ��� = 2;
				�ִ뿥�� = 3;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				�ּҿ��� = 2;
				�ִ뿥�� = 4;
				break;
			case 24:
				�ּҿ��� = 2;
				�ִ뿥�� = 5;
				break;
			case 25:
			case 26:
				�ּҿ��� = 3;
				�ִ뿥�� = 5;
				break;
			case 27:
			case 28:
			case 29:
				�ּҿ��� = 3;
				�ִ뿥�� = 6;
				break;
			case 30:
			case 31:
			case 32:
				�ּҿ��� = 4;
				�ִ뿥�� = 6;
				break;
			case 33:
			case 34:
			case 35:
				�ּҿ��� = 4;
				�ִ뿥�� = 7;
				break;
			case 36:
			case 37:
			case 38:
			case 39:
				�ּҿ��� = 4;
				�ִ뿥�� = 8;
				break;
			case 40:
			case 41:
				�ּҿ��� = 5;
				�ִ뿥�� = 8;
				break;
			case 42:
			case 43:
			case 44:
				�ּҿ��� = 5;
				�ִ뿥�� = 9;
				break;
			case 45:
				�ּҿ��� = 6;
				�ִ뿥�� = 10;
				break;
			default:
				break;
			}
			;
			if (wis > 45) {
				�ּҿ��� = 6;
				�ִ뿥�� = 10;
			}
			break;

		case 2:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				�ּҿ��� = 4;
				�ִ뿥�� = 7;
				break;
			case 15:
			case 16:
			case 17:
				�ּҿ��� = 5;
				�ִ뿥�� = 8;
				break;
			case 18:
			case 19:
				�ּҿ��� = 5;
				�ִ뿥�� = 10;
				break;
			case 20:
				�ּҿ��� = 7;
				�ִ뿥�� = 10;
				break;
			case 21:
			case 22:
			case 23:
				�ּҿ��� = 7;
				�ִ뿥�� = 11;
				break;
			case 24:
				�ּҿ��� = 7;
				�ִ뿥�� = 13;
				break;
			case 25:
			case 26:
				�ּҿ��� = 8;
				�ִ뿥�� = 13;
				break;
			case 27:
			case 28:
			case 29:
				�ּҿ��� = 8;
				�ִ뿥�� = 14;
				break;
			case 30:
			case 31:
			case 32:
				�ּҿ��� = 10;
				�ִ뿥�� = 16;
				break;
			case 33:
			case 34:
				�ּҿ��� = 10;
				�ִ뿥�� = 17;
				break;
			case 35:
				�ּҿ��� = 11;
				�ִ뿥�� = 17;
				break;
			case 36:
			case 37:
			case 38:
				�ּҿ��� = 11;
				�ִ뿥�� = 19;
				break;
			case 39:
				�ּҿ��� = 11;
				�ִ뿥�� = 20;
				break;
			case 40:
			case 41:
				�ּҿ��� = 13;
				�ִ뿥�� = 20;
				break;
			case 42:
			case 43:
			case 44:
				�ּҿ��� = 13;
				�ִ뿥�� = 21;
				break;
			case 45:
				�ּҿ��� = 14;
				�ִ뿥�� = 23;
				break;
			default:
				break;
			}
			;
			if (wis > 45) {
				�ּҿ��� = 14;
				�ִ뿥�� = 23;
			}
			break;

		case 3:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				�ּҿ��� = 6;
				�ִ뿥�� = 10;
				break;
			case 15:
			case 16:
			case 17:
				�ּҿ��� = 8;
				�ִ뿥�� = 12;
				break;
			case 18:
			case 19:
				�ּҿ��� = 8;
				�ִ뿥�� = 14;
				break;
			case 20:
				�ּҿ��� = 10;
				�ִ뿥�� = 14;
				break;
			case 21:
			case 22:
			case 23:
				�ּҿ��� = 10;
				�ִ뿥�� = 16;
				break;
			case 24:
				�ּҿ��� = 10;
				�ִ뿥�� = 18;
				break;
			case 25:
			case 26:
				�ּҿ��� = 12;
				�ִ뿥�� = 18;
				break;
			case 27:
			case 28:
			case 29:
				�ּҿ��� = 12;
				�ִ뿥�� = 20;
				break;
			case 30:
			case 31:
			case 32:
				�ּҿ��� = 14;
				�ִ뿥�� = 22;
				break;
			case 33:
			case 34:
				�ּҿ��� = 14;
				�ִ뿥�� = 24;
				break;
			case 35:
				�ּҿ��� = 16;
				�ִ뿥�� = 24;
				break;
			case 36:
			case 37:
			case 38:
				�ּҿ��� = 16;
				�ִ뿥�� = 26;
				break;
			case 39:
				�ּҿ��� = 16;
				�ִ뿥�� = 28;
				break;
			case 40:
			case 41:
				�ּҿ��� = 18;
				�ִ뿥�� = 28;
				break;
			case 42:
			case 43:
			case 44:
				�ּҿ��� = 18;
				�ִ뿥�� = 30;
				break;
			case 45:
				�ּҿ��� = 20;
				�ִ뿥�� = 32;
				break;
			default:
				break;
			}
			;
			if (wis > 45) {
				�ּҿ��� = 20;
				�ִ뿥�� = 32;
			}
			break;

		case 4:// �ٿ�
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				�ּҿ��� = 4;
				�ִ뿥�� = 5;
				break;
			case 12:
			case 13:
			case 14:
				�ּҿ��� = 4;
				�ִ뿥�� = 7;
				break;
			case 15:
			case 16:
			case 17:
				�ּҿ��� = 5;
				�ִ뿥�� = 8;
				break;
			case 18:
			case 19:
				�ּҿ��� = 5;
				�ִ뿥�� = 10;
				break;
			case 20:
				�ּҿ��� = 7;
				�ִ뿥�� = 10;
				break;
			case 21:
			case 22:
			case 23:
				�ּҿ��� = 7;
				�ִ뿥�� = 11;
				break;
			case 24:
				�ּҿ��� = 7;
				�ִ뿥�� = 13;
				break;
			case 25:
			case 26:
				�ּҿ��� = 8;
				�ִ뿥�� = 13;
				break;
			case 27:
			case 28:
			case 29:
				�ּҿ��� = 8;
				�ִ뿥�� = 14;
				break;
			case 30:
			case 31:
			case 32:
				�ּҿ��� = 10;
				�ִ뿥�� = 16;
				break;
			case 33:
			case 34:
				�ּҿ��� = 10;
				�ִ뿥�� = 17;
				break;
			case 35:
				�ּҿ��� = 11;
				�ִ뿥�� = 17;
				break;
			case 36:
			case 37:
			case 38:
				�ּҿ��� = 11;
				�ִ뿥�� = 19;
				break;
			case 39:
				�ּҿ��� = 11;
				�ִ뿥�� = 20;
				break;
			case 40:
			case 41:
				�ּҿ��� = 13;
				�ִ뿥�� = 20;
				break;
			case 42:
			case 43:
			case 44:
				�ּҿ��� = 13;
				�ִ뿥�� = 22;
				break;
			case 45:
				�ּҿ��� = 14;
				�ִ뿥�� = 23;
				break;
			default:
				break;
			}
			;
			if (wis > 45) {
				�ּҿ��� = 14;
				�ִ뿥�� = 23;
			}
			break;

		case 5:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				�ּҿ��� = 2;
				�ִ뿥�� = 3;
				break;
			case 15:
			case 16:
			case 17:
				�ּҿ��� = 3;
				�ִ뿥�� = 4;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				�ּҿ��� = 3;
				�ִ뿥�� = 5;
				break;
			case 24:
				�ּҿ��� = 3;
				�ִ뿥�� = 6;
				break;
			case 25:
			case 26:
				�ּҿ��� = 4;
				�ִ뿥�� = 6;
				break;
			case 27:
			case 28:
			case 29:
				�ּҿ��� = 4;
				�ִ뿥�� = 7;
				break;
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
				�ּҿ��� = 5;
				�ִ뿥�� = 8;
				break;
			case 36:
			case 37:
			case 38:
				�ּҿ��� = 5;
				�ִ뿥�� = 9;
				break;
			case 39:
				�ּҿ��� = 5;
				�ִ뿥�� = 10;
				break;
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
				�ּҿ��� = 6;
				�ִ뿥�� = 10;
				break;
			case 45:
				�ּҿ��� = 7;
				�ִ뿥�� = 11;
				break;
			default:
				break;
			}
			;
			if (wis > 45) {
				�ּҿ��� = 7;
				�ִ뿥�� = 11;
			}
			break;
		case 6:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				�ּҿ��� = 4;
				�ִ뿥�� = 7;
				break;
			case 15:
			case 16:
			case 17:
				�ּҿ��� = 6;
				�ִ뿥�� = 9;
				break;
			case 18:
			case 19:
				�ּҿ��� = 6;
				�ִ뿥�� = 11;
				break;
			case 20:
				�ּҿ��� = 7;
				�ִ뿥�� = 11;
				break;
			case 21:
			case 22:
			case 23:
				�ּҿ��� = 7;
				�ִ뿥�� = 12;
				break;
			case 24:
				�ּҿ��� = 7;
				�ִ뿥�� = 14;
				break;
			case 25:
			case 26:
				�ּҿ��� = 9;
				�ִ뿥�� = 14;
				break;
			case 27:
			case 28:
			case 29:
				�ּҿ��� = 9;
				�ִ뿥�� = 16;
				break;
			case 30:
			case 31:
			case 32:
				�ּҿ��� = 11;
				�ִ뿥�� = 18;
				break;
			case 33:
			case 34:
				�ּҿ��� = 11;
				�ִ뿥�� = 19;
				break;
			case 35:
				�ּҿ��� = 12;
				�ִ뿥�� = 19;
				break;
			case 36:
			case 37:
			case 38:
				�ּҿ��� = 12;
				�ִ뿥�� = 21;
				break;
			case 39:
				�ּҿ��� = 12;
				�ִ뿥�� = 23;
				break;
			case 40:
			case 41:
				�ּҿ��� = 14;
				�ִ뿥�� = 23;
				break;
			case 42:
			case 43:
			case 44:
				�ּҿ��� = 14;
				�ִ뿥�� = 24;
				break;
			case 45:
				�ּҿ��� = 16;
				�ִ뿥�� = 26;
				break;
			default:
				break;
			}
			;
			if (wis > 45) {
				�ּҿ��� = 16;
				�ִ뿥�� = 26;
			}
			break;
		case 7:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				�ּҿ��� = 0;
				�ִ뿥�� = 1;
				break;
			case 9:
				�ּҿ��� = 0;
				�ִ뿥�� = 2;
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				�ּҿ��� = 1;
				�ִ뿥�� = 2;
				break;
			case 15:
			case 16:
			case 17:
				�ּҿ��� = 2;
				�ִ뿥�� = 3;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				�ּҿ��� = 2;
				�ִ뿥�� = 4;
				break;
			case 24:
				�ּҿ��� = 2;
				�ִ뿥�� = 5;
				break;
			case 25:
			case 26:
				�ּҿ��� = 3;
				�ִ뿥�� = 5;
				break;
			case 27:
			case 28:
			case 29:
				�ּҿ��� = 3;
				�ִ뿥�� = 6;
				break;
			case 30:
			case 31:
			case 32:
				�ּҿ��� = 4;
				�ִ뿥�� = 6;
				break;
			case 33:
			case 34:
			case 35:
				�ּҿ��� = 4;
				�ִ뿥�� = 7;
				break;
			case 36:
			case 37:
			case 38:
			case 39:
				�ּҿ��� = 4;
				�ִ뿥�� = 8;
				break;
			case 40:
			case 41:
				�ּҿ��� = 5;
				�ִ뿥�� = 8;
				break;
			case 42:
			case 43:
			case 44:
				�ּҿ��� = 5;
				�ִ뿥�� = 9;
				break;
			case 45:
				�ּҿ��� = 6;
				�ִ뿥�� = 10;
				break;
			default:
				break;

			}
			;
			if (wis > 45) {
				�ּҿ��� = 6;
				�ִ뿥�� = 10;
			}
			break;
		default:
			break;
		}

		if (wis <= 0) {
			�ּҿ��� = 0;
			�ִ뿥�� = 1;
		}

		int[] bbb = { �ּҿ���, �ִ뿥�� };

		return bbb;
	}

	public static int ����������(int charType, int baseMaxMp, int wis) {
		int addmp = 0;
		switch (charType) {
		case 0:
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				addmp = rnd.nextInt(2) + 3;
				break;
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(3) + 3;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(3) + 4;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 20:
				addmp = rnd.nextInt(3) + 5;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 24:
				addmp = rnd.nextInt(5) + 5;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(4) + 6;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(5) + 6;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(5) + 7;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(6) + 7;
				break;
			case 35:
				addmp = rnd.nextInt(5) + 8;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(6) + 8;
				break;
			case 39:
				addmp = rnd.nextInt(7) + 8;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(6) + 9;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(7) + 9;
				break;
			case 45:
				addmp = rnd.nextInt(7) + 10;
				break;
			default:
				break;
			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(7) + 10;
			break;// ����

		case 1:// ���
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				addmp = rnd.nextInt(3);
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(2) + 1;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(2) + 2;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(3) + 2;
				break;
			case 24:
				addmp = rnd.nextInt(4) + 2;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(3) + 3;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(4) + 3;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(3) + 4;
				break;
			case 33:
			case 34:
			case 35:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 36:
			case 37:
			case 38:
			case 39:
				addmp = rnd.nextInt(5) + 4;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(5) + 5;
				break;
			case 45:
				addmp = rnd.nextInt(5) + 6;
				break;
			default:
				break;
			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(5) + 6;
			break;

		case 2:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(6) + 5;
				break;
			case 20:
				addmp = rnd.nextInt(4) + 7;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(5) + 7;
				break;
			case 24:
				addmp = rnd.nextInt(7) + 7;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(6) + 8;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(7) + 8;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(7) + 10;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(8) + 10;
				break;
			case 35:
				addmp = rnd.nextInt(7) + 11;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(9) + 11;
				break;
			case 39:
				addmp = rnd.nextInt(10) + 11;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(8) + 13;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(10) + 13;
				break;
			case 45:
				addmp = rnd.nextInt(10) + 14;
				break;
			default:
				break;
			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(10) + 14;
			break;

		case 3:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(5) + 6;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(5) + 8;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(7) + 8;
				break;
			case 20:
				addmp = rnd.nextInt(5) + 10;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(7) + 10;
				break;
			case 24:
				addmp = rnd.nextInt(9) + 10;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(7) + 12;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(9) + 12;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(9) + 14;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(11) + 14;
				break;
			case 35:
				addmp = rnd.nextInt(9) + 16;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(11) + 16;
				break;
			case 39:
				addmp = rnd.nextInt(13) + 16;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(11) + 18;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(13) + 18;
				break;
			case 45:
				addmp = rnd.nextInt(13) + 20;
				break;
			default:
				break;
			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(13) + 20;
			break;

		case 4:// �ٿ�
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				addmp = rnd.nextInt(2) + 4;
				break;
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(6) + 5;
				break;
			case 20:
				addmp = rnd.nextInt(4) + 7;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(5) + 7;
				break;
			case 24:
				addmp = rnd.nextInt(7) + 7;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(6) + 8;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(7) + 8;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(7) + 10;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(8) + 10;
				break;
			case 35:
				addmp = rnd.nextInt(7) + 11;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(9) + 11;
				break;
			case 39:
				addmp = rnd.nextInt(10) + 11;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(8) + 13;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(10) + 13;
				break;
			case 45:
				addmp = rnd.nextInt(10) + 14;
				break;
			default:
				break;
			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(10) + 14;
			break;

		case 5:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(2) + 2;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(2) + 3;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(3) + 3;
				break;
			case 24:
				addmp = rnd.nextInt(4) + 3;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(3) + 4;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 30:
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(5) + 5;
				break;
			case 39:
				addmp = rnd.nextInt(6) + 5;
				break;
			case 40:
			case 41:
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(5) + 6;
				break;
			case 45:
				addmp = rnd.nextInt(5) + 7;
				break;
			default:
				break;
			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(5) + 7;
			break;
		case 6:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(4) + 6;
				break;
			case 18:
			case 19:
				addmp = rnd.nextInt(6) + 6;
				break;
			case 20:
				addmp = rnd.nextInt(5) + 7;
				break;
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(6) + 7;
				break;
			case 24:
				addmp = rnd.nextInt(8) + 7;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(6) + 9;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(8) + 9;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(8) + 11;
				break;
			case 33:
			case 34:
				addmp = rnd.nextInt(9) + 11;
				break;
			case 35:
				addmp = rnd.nextInt(8) + 12;
				break;
			case 36:
			case 37:
			case 38:
				addmp = rnd.nextInt(10) + 12;
				break;
			case 39:
				addmp = rnd.nextInt(12) + 12;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(10) + 14;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(11) + 14;
				break;
			case 45:
				addmp = rnd.nextInt(11) + 16;
				break;
			default:
				break;
			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(11) + 16;
			break;
		case 7:// ����
			switch (wis) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
				addmp = rnd.nextInt(2);
				break;
			case 9:
				addmp = rnd.nextInt(3);
				break;
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
				addmp = rnd.nextInt(2) + 1;
				break;
			case 15:
			case 16:
			case 17:
				addmp = rnd.nextInt(2) + 2;
				break;
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				addmp = rnd.nextInt(3) + 2;
				break;
			case 24:
				addmp = rnd.nextInt(4) + 2;
				break;
			case 25:
			case 26:
				addmp = rnd.nextInt(3) + 3;
				break;
			case 27:
			case 28:
			case 29:
				addmp = rnd.nextInt(4) + 3;
				break;
			case 30:
			case 31:
			case 32:
				addmp = rnd.nextInt(3) + 4;
				break;
			case 33:
			case 34:
			case 35:
				addmp = rnd.nextInt(4) + 4;
				break;
			case 36:
			case 37:
			case 38:
			case 39:
				addmp = rnd.nextInt(5) + 4;
				break;
			case 40:
			case 41:
				addmp = rnd.nextInt(4) + 5;
				break;
			case 42:
			case 43:
			case 44:
				addmp = rnd.nextInt(5) + 5;
				break;
			case 45:
				addmp = rnd.nextInt(5) + 6;
				break;
			default:
				break;

			}
			;
			if (wis > 45)
				addmp = rnd.nextInt(5) + 6;
			break;
		default:
			break;
		}

		if (wis <= 0) {
			addmp = rnd.nextInt(2);
		}

		switch (charType) {
		case 0:
			if (baseMaxMp + addmp > Config.PRINCE_MAX_MP) {
				addmp = (Config.PRINCE_MAX_MP - baseMaxMp);
			}
			break;// ����
		case 1:
			if (baseMaxMp + addmp > Config.KNIGHT_MAX_MP) {
				addmp = (Config.KNIGHT_MAX_MP - baseMaxMp);
			}
			break;// ���
		case 2:
			if (baseMaxMp + addmp > Config.ELF_MAX_MP) {
				addmp = (Config.ELF_MAX_MP - baseMaxMp);
			}
			break;// ����
		case 3:
			if (baseMaxMp + addmp > Config.WIZARD_MAX_MP) {
				addmp = (Config.WIZARD_MAX_MP - baseMaxMp);
			}
			break;// ����
		case 4:
			if (baseMaxMp + addmp > Config.DARKELF_MAX_MP) {
				addmp = (Config.DARKELF_MAX_MP - baseMaxMp);
			}
			break;// �ٿ�
		case 5:
			if (baseMaxMp + addmp > Config.DRAGONKNIGHT_MAX_MP) {
				addmp = (Config.DRAGONKNIGHT_MAX_MP - baseMaxMp);
			}
			break;// ����
		case 6:
			if (baseMaxMp + addmp > Config.BLACKWIZARD_MAX_MP) {
				addmp = (Config.BLACKWIZARD_MAX_MP - baseMaxMp);
			}
			break;// ȯ����
		case 7:
			if (baseMaxMp + addmp > Config.KNIGHT_MAX_MP) {
				addmp = (Config.KNIGHT_MAX_MP - baseMaxMp);
			}
			break;// ����
		default:
			break;
		}

		return addmp;
	}

	public static int ��������������(int charType, int totalCon) {
		int startcon = 0;
		int starthp = 0;
		int returnhp = 0;
		switch (charType) {
		case 0:
			startcon = 11;
			starthp = 12;
			break;// ����
		case 1:
			startcon = 16;
			starthp = 21;
			break;// ���
		case 2:
			startcon = 12;
			starthp = 10;
			break;// ����
		case 3:
			startcon = 12;
			starthp = 7;
			break;// ����
		case 4:
			startcon = 12;
			starthp = 11;
			break;// �ٿ�
		case 5:
			startcon = 14;
			starthp = 15;
			break;// ����
		case 6:
			startcon = 12;
			starthp = 9;
			break;// ȯ����
		case 7:
			startcon = 16;
			starthp = 21;
			break;// ����
		default:
			break;
		}
		int _25�̻� = 0;
		int _25���� = 0;
		if (totalCon > 25) {
			_25���� = 25 - startcon;
			_25�̻� = (totalCon - 25) / 2;
		} else {
			_25���� = totalCon - startcon;
		}

		if (_25���� != 0) {
			returnhp += (starthp + _25����);
		}

		if (_25�̻� != 0) {
			returnhp += _25�̻�;
		}

		return returnhp;
	}

	public static int ��������(int charType, int baseMaxHp, int totalCon) {
		int startcon = 0;
		int starthp = 0;
		int returnhp = 0;
		switch (charType) {
		case 0:
			startcon = 11;
			starthp = 12;
			break;// ����
		case 1:
			startcon = 16;
			starthp = 21;
			break;// ���
		case 2:
			startcon = 12;
			starthp = 10;
			break;// ����
		case 3:
			startcon = 12;
			starthp = 7;
			break;// ����
		case 4:
			startcon = 12;
			starthp = 11;
			break;// �ٿ�
		case 5:
			startcon = 14;
			starthp = 15;
			break;// ����
		case 6:
			startcon = 12;
			starthp = 9;
			break;// ȯ����
		case 7:
			startcon = 16;
			starthp = 21;
			break;// ����
		default:
			break;
		}

		int calcstat = totalCon - startcon;

		if (calcstat <= 0) {
			returnhp = starthp + rnd.nextInt(2);
		}
		returnhp = starthp + (calcstat + rnd.nextInt(2));

		switch (charType) {
		case 0:
			if (baseMaxHp + returnhp > Config.PRINCE_MAX_HP) {
				returnhp = (Config.PRINCE_MAX_HP - baseMaxHp);
			}
			break;// ����
		case 1:
			if (baseMaxHp + returnhp > Config.KNIGHT_MAX_HP) {
				returnhp = (Config.KNIGHT_MAX_HP - baseMaxHp);
			}
			break;// ���
		case 2:
			if (baseMaxHp + returnhp > Config.ELF_MAX_HP) {
				returnhp = (Config.ELF_MAX_HP - baseMaxHp);
			}
			break;// ����
		case 3:
			if (baseMaxHp + returnhp > Config.WIZARD_MAX_HP) {
				returnhp = (Config.WIZARD_MAX_HP - baseMaxHp);
			}
			break;// ����
		case 4:
			if (baseMaxHp + returnhp > Config.DARKELF_MAX_HP) {
				returnhp = (Config.DARKELF_MAX_HP - baseMaxHp);
			}
			break;// �ٿ�
		case 5:
			if (baseMaxHp + returnhp > Config.DRAGONKNIGHT_MAX_HP) {
				returnhp = (Config.DRAGONKNIGHT_MAX_HP - baseMaxHp);
			}
			break;// ����
		case 6:
			if (baseMaxHp + returnhp > Config.BLACKWIZARD_MAX_HP) {
				returnhp = (Config.BLACKWIZARD_MAX_HP - baseMaxHp);
			}
			break;// ȯ����
		case 7:
			if (baseMaxHp + returnhp > Config.KNIGHT_MAX_HP) {
				returnhp = (Config.KNIGHT_MAX_HP - baseMaxHp);
			}
			break;// ����
		default:
			break;
		}

		return returnhp;
	}

	/**
	 * �� Ŭ������ LVUP���� HP���ġ�� �����ش�
	 * 
	 * @param charType
	 * @param baseMaxHp
	 * @param baseCon
	 * @return HP���ġ
	 */
	/*
	 * public static short calcStatHp(int charType, int baseMaxHp, int totalCon)
	 * { short randomhp = 0; int addCon = 0; if (charType == 0) { // ���� int
	 * calCon = 10; switch(totalCon){ case 11 : case 12 : addCon = 1+
	 * rnd.nextInt(2); break; case 2 : case 3 : addCon = 2 + rnd.nextInt(2);
	 * break; case 4 : case 5 : addCon = 3 + rnd.nextInt(2); break; case 6 :
	 * addCon = 5 + rnd.nextInt(2); break; case 7 : addCon = 6 + rnd.nextInt(2);
	 * break; case 8 : addCon = 7 + rnd.nextInt(2); break; default : //addCon =
	 * (baseCon - calCon - 1) + (int)((baseCon - calCon)/5) + rnd.nextInt(2);
	 * if(baseCon >= 25) addCon = 14 + rnd.nextInt(2); else addCon =
	 * (baseCon-11) + rnd.nextInt(2); break; } randomhp += calCon + addCon;
	 * 
	 * if (baseMaxHp + randomhp > Config.PRINCE_MAX_HP) { randomhp = (short)
	 * (Config.PRINCE_MAX_HP - baseMaxHp); } } else if (charType == 2) { // ����
	 * int calCon = 12; switch(baseCon - calCon){ case 0 : addCon = 2 +
	 * rnd.nextInt(2); break; case 1 : case 2 : case 3 : addCon = 3 +
	 * rnd.nextInt(2); break; case 4 : addCon = 4 + rnd.nextInt(2); break; case
	 * 5 : addCon = 5 + rnd.nextInt(2); break; case 6 : addCon = 7 +
	 * rnd.nextInt(2); break; default : //addCon = 6 + rnd.nextInt(2) +
	 * (int)((baseCon - calCon)/3); if(baseCon >= 25) addCon = 16 +
	 * rnd.nextInt(2); else addCon = (baseCon-9) + rnd.nextInt(2); break; }
	 * randomhp += calCon - 4 + addCon;
	 * 
	 * if (baseMaxHp + randomhp > Config.ELF_MAX_HP) { randomhp = (short)
	 * (Config.ELF_MAX_HP - baseMaxHp); } } else if (charType == 3) { // ������ int
	 * calCon = 12; switch(baseCon - calCon){ case 0 : addCon = -1 +
	 * rnd.nextInt(2); break; case 1 : addCon = 0 + rnd.nextInt(2); break; case
	 * 2 : case 3 : addCon = 1 + rnd.nextInt(2); break; case 4 : case 5 : addCon
	 * = 2 + rnd.nextInt(2); break; case 6 : case 7 : addCon = 4 +
	 * rnd.nextInt(2); break; case 8 : case 9 : addCon = 5 + rnd.nextInt(2);
	 * break; case 10 : case 11 : addCon = 6 + rnd.nextInt(2); break; default:
	 * if(baseCon >= 24){ addCon = 7 + rnd.nextInt(2); } break; } randomhp +=
	 * calCon - 4 + addCon;
	 * 
	 * if (baseMaxHp + randomhp > Config.WIZARD_MAX_HP) { randomhp = (short)
	 * (Config.WIZARD_MAX_HP - baseMaxHp); } } else if (charType == 4) { // ��ũ����
	 * int calCon = 8; switch(baseCon - calCon){ case 0 : case 1 : addCon = -1 +
	 * rnd.nextInt(2); break; case 2 : case 3 : addCon = 0 + rnd.nextInt(2);
	 * break; case 4 : case 5 : addCon = 1 + rnd.nextInt(2); break; case 6 :
	 * case 7 : addCon = 2 + rnd.nextInt(2); break; case 8 : case 9 : addCon = 3
	 * + rnd.nextInt(2); break; case 10 : case 11 : addCon = 4 + rnd.nextInt(2);
	 * break; case 12 : case 13 : addCon = 5 + rnd.nextInt(2); break; case 14 :
	 * case 15 : addCon = 6 + rnd.nextInt(2); break; default : if(baseCon >= 24)
	 * addCon = 7 + rnd.nextInt(2); break; } randomhp += calCon + 3 + addCon;
	 * 
	 * if (baseMaxHp + randomhp > Config.DARKELF_MAX_HP) { randomhp = (short)
	 * (Config.DARKELF_MAX_HP - baseMaxHp); } } else if (charType == 5) { // ����
	 * int calCon = 14; switch(baseCon - calCon){ case 0 : addCon = 0 +
	 * rnd.nextInt(2); break; case 1 : addCon = 1 + rnd.nextInt(2); break; case
	 * 2 : addCon = 2 + rnd.nextInt(2); break; case 3 : addCon = 5 +
	 * rnd.nextInt(2); break; case 4 : addCon = 6 + rnd.nextInt(2); break;
	 * default : if(baseCon >= 25) addCon = 13 + rnd.nextInt(2); else addCon =
	 * baseCon - calCon + 2 + rnd.nextInt(2); break; } randomhp += calCon - 1 +
	 * addCon;
	 * 
	 * if (baseMaxHp + randomhp > Config.DRAGONKNIGHT_MAX_HP) { randomhp =
	 * (short) (Config.DRAGONKNIGHT_MAX_HP - baseMaxHp); } } else if (charType
	 * == 6) { // ȯ���� int calCon = 12; switch(baseCon - calCon){ case 0 : addCon
	 * = 1 + rnd.nextInt(2); break; case 1 : case 2 : addCon = 2 +
	 * rnd.nextInt(2); break; case 3 : addCon = 3 + rnd.nextInt(2); break; case
	 * 4 : addCon = 4 + rnd.nextInt(2); break; case 5 : addCon = 5 +
	 * rnd.nextInt(2); break; case 6 : addCon = 6 + rnd.nextInt(2); break;
	 * default : if(baseCon >= 25) addCon = 13 + rnd.nextInt(2); else addCon =
	 * baseCon - calCon + rnd.nextInt(2); break; } randomhp += calCon - 4 +
	 * addCon;
	 * 
	 * if (baseMaxHp + randomhp > Config.BLACKWIZARD_MAX_HP) { randomhp =
	 * (short) (Config.BLACKWIZARD_MAX_HP - baseMaxHp); } } else if (charType ==
	 * 1) { // ��� int calCon = 14; switch(baseCon - calCon){ case 0 : addCon = 3
	 * + rnd.nextInt(2); break; case 1 : addCon = 4 + rnd.nextInt(2); break;
	 * case 2 : addCon = 5 + rnd.nextInt(2); break; case 3 : addCon = 8 +
	 * rnd.nextInt(2); break; case 4 : addCon = 9 + rnd.nextInt(2); break;
	 * default : //addCon = 9 + rnd.nextInt(2) + (int)((baseCon - calCon)/3);
	 * if(baseCon >= 25) addCon = 16 + rnd.nextInt(2); else addCon = (baseCon-9)
	 * + rnd.nextInt(2); break; } randomhp += calCon + addCon; if (baseMaxHp +
	 * randomhp > Config.KNIGHT_MAX_HP) { randomhp = (short)
	 * (Config.KNIGHT_MAX_HP - baseMaxHp); } } else if (charType == 7) { // ����
	 * int calCon = 16; switch(baseCon - calCon){ case 0 : addCon = 2 +
	 * rnd.nextInt(2); break; case 1 : addCon = 4 + rnd.nextInt(2); break; case
	 * 2 : addCon = 5 + rnd.nextInt(2); break; case 3 : addCon = 8 +
	 * rnd.nextInt(2); break; case 4 : addCon = 9 + rnd.nextInt(2); break;
	 * default : //addCon = 9 + rnd.nextInt(2) + (int)((baseCon - calCon)/3);
	 * if(baseCon >= 25) addCon = 16 + rnd.nextInt(2); else addCon = (baseCon-9)
	 * + rnd.nextInt(2); break; } randomhp += calCon + addCon; if (baseMaxHp +
	 * randomhp > Config.KNIGHT_MAX_HP) { randomhp = (short)
	 * (Config.KNIGHT_MAX_HP - baseMaxHp); } } if (randomhp < 0) { randomhp = 0;
	 * }
	 * 
	 * return randomhp;
	 * 
	 * }
	 */

	/**
	 * �� Ŭ������ LVUP���� MP���ġ�� �����ش�
	 * 
	 * @param charType
	 * @param baseMaxMp
	 * @param baseWis
	 * @return MP���ġ
	 */
	/*
	 * public static short calcStatMp(int charType, int baseMaxMp, byte baseWis)
	 * { int randommp = 0;
	 * 
	 * if (charType == 0) { // ������ int addWis = 0; int calWis = 11;
	 * switch(baseWis - calWis){ case 0 : case 1 : addWis = 2 + rnd.nextInt(2);
	 * break; case 2 : case 3 : addWis = 3 + rnd.nextInt(2); break; case 4 :
	 * case 5 : addWis = 4 + rnd.nextInt(2); break; case 6 : case 7 : addWis = 5
	 * + rnd.nextInt(2); break; case 8 : case 9 : addWis = 6 + rnd.nextInt(2);
	 * break; case 10 : case 11 : addWis = 7 + rnd.nextInt(2); break; case 12 :
	 * case 13 : addWis = 8 + rnd.nextInt(2); break; default : if(baseWis >= 25)
	 * addWis = 9 + rnd.nextInt(2); break; } randommp += addWis;
	 * 
	 * if (baseMaxMp + randommp > Config.PRINCE_MAX_MP) { randommp =
	 * Config.PRINCE_MAX_MP - baseMaxMp; } } else if (charType == 1) { // ����Ʈ
	 * int addWis = 0; int calWis = 9; switch(baseWis - calWis){ case 0 :
	 * //addWis = rnd.nextInt(2); //addWis = rnd.nextInt(100)+1 > 80 ? 0 : 1;
	 * int rna = rnd.nextInt(100)+1; if(rna > 90) addWis = 0; else if(rna > 80)
	 * addWis = 2; else addWis = 1; break; case 1 : case 2 : case 3 : case 4 :
	 * addWis = 1 + rnd.nextInt(2); break; case 5 : case 6 : case 7 : case 8 :
	 * addWis = 2 + rnd.nextInt(2); break; case 9 : case 10 : case 11 : case 12
	 * : addWis = 3 + rnd.nextInt(2); break; default : if(baseWis >= 22) addWis
	 * = 4 + rnd.nextInt(2); break; } randommp += addWis;
	 * 
	 * if (baseMaxMp + randommp > Config.KNIGHT_MAX_MP) { randommp =
	 * Config.KNIGHT_MAX_MP - baseMaxMp; } } else if (charType == 2) { // ������
	 * int addWis = 0; int calWis = 12; switch(baseWis - calWis){ case 0 : case
	 * 1 : addWis = 4 + rnd.nextInt(2); break; case 2 : case 3 : addWis = 5 +
	 * rnd.nextInt(2); break; case 4 : addWis = 6 + rnd.nextInt(2); break; case
	 * 5 : addWis = 7 + rnd.nextInt(2); break; case 6 : case 7 : addWis = 8 +
	 * rnd.nextInt(2); break; case 8 : case 9 : addWis = 9 + rnd.nextInt(2);
	 * break; case 10 : case 11 : addWis = 10 + rnd.nextInt(2); break; default :
	 * if(baseWis >= 24) addWis = 12 + rnd.nextInt(2); break; } randommp +=
	 * addWis;
	 * 
	 * if (baseMaxMp + randommp > Config.ELF_MAX_MP) { randommp =
	 * Config.ELF_MAX_MP - baseMaxMp; } } else if (charType == 3) { // ������ int
	 * addWis = 0; int calWis = 12; switch(baseWis - calWis){ case 0 : addWis =
	 * 6 + rnd.nextInt(2); break; case 1 : addWis = 7 + rnd.nextInt(2); break;
	 * case 2 : addWis = 8 + rnd.nextInt(2); break; case 3 : case 4 : addWis = 9
	 * + rnd.nextInt(2); break; case 5 : addWis = 10 + rnd.nextInt(2); break;
	 * case 6 : case 7 : addWis = 11 + rnd.nextInt(2); break; case 8 : case 9 :
	 * addWis = 12 + rnd.nextInt(2); break; case 10 : case 11 : addWis = 13 +
	 * rnd.nextInt(2); break; default : if(baseWis >= 24) addWis = 14 +
	 * rnd.nextInt(2); break; } randommp += addWis;
	 * 
	 * if (baseMaxMp + randommp > Config.WIZARD_MAX_MP) { randommp =
	 * Config.WIZARD_MAX_MP - baseMaxMp; } } else if (charType == 4) { // ��ũ ������
	 * int addWis = 0; int calWis = 10; switch(baseWis - calWis) { case 0 : case
	 * 1 : addWis = 3 + rnd.nextInt(2); break; case 2 : case 3 : addWis = 4 +
	 * rnd.nextInt(2); break; case 4 : case 5 : addWis = 5 + rnd.nextInt(2);
	 * break; case 6 : case 7 : addWis = 6 + rnd.nextInt(2); break; case 8 :
	 * case 9 : addWis = 7 + rnd.nextInt(2); break; case 10 : case 11 : addWis =
	 * 8 + rnd.nextInt(2); break; case 12 : case 13 : addWis = 10 +
	 * rnd.nextInt(2); break; default : if(baseWis >= 24) addWis = 11 +
	 * rnd.nextInt(2); break; } randommp += addWis;
	 * 
	 * 
	 * if (baseMaxMp + randommp > Config.DARKELF_MAX_MP) { randommp =
	 * Config.DARKELF_MAX_MP - baseMaxMp; } } else if (charType == 5) { // ����
	 * int addWis = 0; int calWis = 12; switch(baseWis - calWis) { case 0 : case
	 * 1 : addWis = 1 + rnd.nextInt(2); break; case 2 : case 3 : addWis = 2 +
	 * rnd.nextInt(2); break; case 4 : addWis = 3 + rnd.nextInt(2); break; case
	 * 5 : addWis = 4 + rnd.nextInt(2); break; case 6 : case 7 : addWis = 5 +
	 * rnd.nextInt(2); break; case 8 : case 9 : addWis = 6 + rnd.nextInt(2);
	 * break; case 10 : case 11 : addWis = 7 + rnd.nextInt(2); break; default :
	 * if(baseWis >= 24) addWis = 8 + rnd.nextInt(2); break; } randommp +=
	 * addWis;
	 * 
	 * if (baseMaxMp + randommp > Config.DRAGONKNIGHT_MAX_MP) { randommp =
	 * Config.DRAGONKNIGHT_MAX_MP - baseMaxMp; } } else if (charType == 6) { //
	 * ȯ���� int addWis = 0; int calWis = 12; switch(baseWis - calWis){ case 0 :
	 * addWis = 4 + rnd.nextInt(2); break; case 1 : case 2 : addWis = 5 +
	 * rnd.nextInt(2); break; case 3 : addWis = 7 + rnd.nextInt(2); break; case
	 * 4 : case 5 : addWis = 8 + rnd.nextInt(2); break; case 6 : case 7 : addWis
	 * = 9 + rnd.nextInt(2); break; case 8 : case 9 : addWis = 10 +
	 * rnd.nextInt(2); break; case 10 : case 11 : addWis = 11 + rnd.nextInt(2);
	 * break; default : if(baseWis >= 24) addWis = 13 + rnd.nextInt(2); break; }
	 * randommp += addWis;
	 * 
	 * if (baseMaxMp + randommp > Config.BLACKWIZARD_MAX_MP) { randommp =
	 * Config.BLACKWIZARD_MAX_MP - baseMaxMp; } } else if (charType == 7) { //
	 * ���� int addWis = 0; int calWis = 7; switch(baseWis - calWis){ case 0 :
	 * case 1 : addWis = 0 + rnd.nextInt(2); break; case 2 : case 3 : case 4 :
	 * addWis = 1+ rnd.nextInt(2); break; case 5 : case 6 : case 7 : addWis = 2
	 * + rnd.nextInt(2); break; default : addWis = 3 + rnd.nextInt(2) +
	 * (int)((baseWis - calWis)/3); break; } randommp += addWis;
	 * 
	 * if (baseMaxMp + randommp > Config.KNIGHT_MAX_MP) { randommp =
	 * Config.KNIGHT_MAX_MP - baseMaxMp; } } if (randommp < 0) { randommp = 0; }
	 * return (short) randommp; }
	 */

	public static int getMaxWeight(int str, int con) {
		return �ִ��������(str, con);
	}

	public static int �ִ��������(int str, int con) {
		int maxWeight = 1000;
		try {
			int stat = (str + con) / 2;
			if (stat <= 0)
				return maxWeight;
			maxWeight += stat * 100;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxWeight;
	}

	public static int �ٰŸ������(int str) {
		try {

			if (str < 10)
				return 2;
			int temp = 2 + ((str - 8) / 2);
			if (str >= 25)
				temp++;
			if (str >= 35)
				temp++;
			if (str >= 45)
				temp += 3;

			return temp/* �ٰŸ�_�����[str] */;
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
	}

	public static int �ٰŸ�����(int str) {
		try {

			if (str < 9)
				return 5;
			int temp = 5 + (((str - 6) / 3) * 2);
			if (str >= 25)
				temp++;
			if (str >= 35)
				temp++;
			if (str >= 45)
				temp += 3;

			return temp/* �ٰŸ�_�����[str] */;
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
	}

	public static int �ٰŸ�ġ��Ÿ(int str) {
		try {
			int i = 0;
			if (str >= 40)
				i++;
			if (str >= 45)
				i++;
			if (str >= 50)
				i++;
			if (str >= 60)
				i++;
			if (str >= 70)
				i++;
			if (str >= 75)
				i++;
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * public static int ���������(int inte){ try { if(inte < 0)return 0; if(inte >
	 * 45)return 12; return ����_�����[inte]; } catch (Exception e) {
	 * e.printStackTrace(); return 0; } }
	 */
	public static int ���������(int inte) {
		try {
			if (inte < 15)
				return 0;
			int temp = 0 + ((inte - 10) / 5);
			if (inte >= 25)
				temp++;
			if (inte >= 35)
				temp++;
			if (inte >= 45)
				temp += 3;
			return temp/* �ٰŸ�_�����[str] */;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int ��������(int inte) {
		try {
			if (inte < 9)
				return -4;
			int temp = -4 + ((inte - 6) / 3);
			if (inte >= 25)
				temp++;
			if (inte >= 35)
				temp++;
			if (inte >= 45)
				temp += 3;
			return temp/* �ٰŸ�_�����[str] */;
		} catch (Exception e) {
			e.printStackTrace();
			return -4;
		}
	}

	public static int ����ġ��Ÿ(int inte) {
		try {
			int i = 0;
			if (inte >= 35)
				i++;
			if (inte >= 40)
				i++;
			if (inte >= 45)
				i += 2;
			if (inte >= 50)
				i++;
			if (inte >= 55)
				i++;
			if (inte >= 60)
				i++;
			if (inte >= 65)
				i++;
			if (inte >= 70)
				i++;
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * public static int �������ʽ�(int inte){ try { if(inte < 0)return 2; if(inte >
	 * 45)return 11; return ����_���ʽ�[inte]; } catch (Exception e) {
	 * e.printStackTrace(); return 2; } }
	 */
	public static int �������ʽ�(int inte) {
		try {

			if (inte < 12)
				return 2;
			int temp = 2 + ((inte - 8) / 4);
			return temp/* �ٰŸ�_�����[str] */;
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
	}

	public static int ���Ҹ𰨼�(int inte) {
		try {
			if (inte < 0)
				return 5;
			if (inte > 45)
				return 30;
			return ���Ҹ𰨼�[inte];
		} catch (Exception e) {
			e.printStackTrace();
			return 5;
		}
	}

	private static final int[] ���Ҹ𰨼� = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 6, 6,// 1~10
			7, 8, 8, 9, 10, 10, 11, 12, 12, 13,// 11~20
			14, 14, 15, 16, 16, 17, 18, 18, 19, 20,// 21~30
			20, 21, 22, 22, 23, 24, 24, 25, 26, 26,// 31~40
			27, 28, 28, 29, 30 };// 41~45

	/*
	 * public static int ���Ÿ������(int dex){ try { if(dex < 0)return 2; if(dex >
	 * 45)return 20; return ���Ÿ�_�����[dex]; } catch (Exception e) {
	 * e.printStackTrace(); return 2; } }
	 */

	public static int ���Ÿ������(int dex) {
		try {

			if (dex < 9)
				return 2;
			int temp = 2 + ((dex - 6) / 3);
			if (dex >= 25)
				temp++;
			if (dex >= 35)
				temp++;
			if (dex >= 45)
				temp += 3;

			return temp/* �ٰŸ�_�����[str] */;
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
	}

	public static int ���Ÿ�����(int dex) {
		try {
			if (dex <= 7)
				return -3;
			int temp = -3 + (dex - 7);
			if (dex >= 25)
				temp++;
			if (dex >= 35)
				temp++;
			if (dex >= 45)
				temp += 3;

			return temp/* �ٰŸ�_�����[str] */;
		} catch (Exception e) {
			e.printStackTrace();
			return -3;
		}
	}

	public static int ���Ÿ�ġ��Ÿ(int dex) {
		try {
			int i = 0;
			if (dex >= 40)
				i++;
			if (dex >= 45)
				i++;
			if (dex >= 50)
				i++;
			if (dex >= 60)
				i++;
			if (dex >= 70)
				i++;
			if (dex >= 75)
				i++;
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * public static int ��������(int dex){ try { if(dex < 0)return -2; if(dex >
	 * 45)return -15; return ����_����[dex]; } catch (Exception e) {
	 * e.printStackTrace(); return -2; } }
	 */

	public static int ��������(int dex) {
		try {
			/*�����ҽ� �ּ�
			if (dex < 9)
				return -2;
			int temp = -2 + (((dex - 6) / 3) * -1);
			return temp;
			*/
			int temp = 10;	//���� �⺻ ac10
			if (dex < 9) {
				temp -= 2;
			} else {
				temp += (-2 + (((dex - 6) / 3) * -1));
			}
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			//�����ҽ��ּ�	return -2;
			return 8;	//ac10���� -2�Ѱ�
		}
	}

	public static int ���Ÿ�ȸ��(int dex) {
		try {
			if (dex < 0)
				return 3;
			if (dex > 45)
				return 22;
			return ���Ÿ�_ȸ�Ƿ�[dex];
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}

	private static final int[] ���Ÿ�_ȸ�Ƿ� = { 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 5,// 1~10
			5, 6, 6, 7, 7, 8, 8, 9, 9, 10,// 11~20
			10, 11, 11, 12, 12, 13, 13, 14, 14, 15,// 21~30
			15, 16, 16, 17, 17, 18, 18, 19, 19, 20,// 31~40
			20, 21, 21, 22, 22 };// 41~45

	public static int ��ȸ��ƽ(int wis) {
		try {
			if (wis < 0)
				return 1;
			if (wis > 45)
				return 14;
			return ����_ȸ��[wis];
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	private static final int[] ����_ȸ�� = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,// 1~10
			2, 2, 2, 2, 3, 3, 3, 3, 3, 4,// 11~20
			4, 4, 4, 4, 6, 6, 6, 6, 6, 7,// 21~30
			7, 7, 7, 7, 9, 9, 9, 9, 9, 10,// 31~40
			10, 10, 10, 10, 14 };// 41~45

	public static int ���ǹ���ȸ��(int wis) {
		try {
			if (wis < 0)
				return 1;
			if (wis > 45)
				return 23;
			return ����_����_ȸ��[wis];
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	private static final int[] ����_����_ȸ�� = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,// 1~10
			1, 2, 2, 2, 3, 4, 4, 5, 5, 6,// 11~20
			6, 7, 7, 8, 9, 10, 10, 11, 11, 12,// 21~30
			12, 13, 13, 14, 15, 16, 16, 17, 17, 18,// 31~40
			18, 19, 19, 20, 23 };// 41~45

	public static int �������(int type, int wis) {
		try {
			int base = 0;
			switch (type) {
			case 0:
				base = 10;
				break;// ����
			case 1:
				base = 0;
				break;// ���
			case 2:
				base = 25;
				break;// ����
			case 3:
				base = 15;
				break;// ����
			case 4:
				base = 10;
				break;// �ٿ�
			case 5:
				base = 18;
				break;// ����
			case 6:
				base = 20;
				break;// ȯ����
			case 7:
				base = 0;
				break;// ����
			default:
				break;
			}

			if (wis <= 10)
				return base;

			int temp = wis - 10;

			base += temp * 4;

			return base;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int ��ȸ��ƽ(int con) {
		try {
			if (con < 0)
				return 5;
			if (con > 45)
				return 27;
			int result = ��_ȸ��_ƽ[con];
			if (con >= 25)
				result++;
			if (con >= 35)
				result++;
			if (con >= 45)
				result += 3;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 5;
		}
	}

	private static final int[] ��_ȸ��_ƽ = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,// 1~10
			5, 6, 6, 7, 7, 8, 8, 9, 9, 10,// 11~20
			10, 11, 11, 12, 13, 14, 14, 15, 15, 16,// 21~30
			16, 17, 17, 18, 19, 20, 20, 21, 21, 22,// 31~40
			22, 23, 23, 24, 27 };// 41~45

	public static int ����ȸ������(int con) {
		try {
			int i = 0;
			if (con >= 20)
				i++;
			if (con >= 30)
				i++;
			if (con >= 35)
				i++;
			if (con >= 40)
				i++;
			if (con >= 45)
				i += 2;
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/*
	 * public static int calcBaseWeight(int charType, int str, int con) { int
	 * weightReductionByBaseStatus = 0; switch(charType){ case 0: if(str >= 14){
	 * weightReductionByBaseStatus += 1; } if(str >= 17){
	 * weightReductionByBaseStatus += 1; } if(str >= 20){
	 * weightReductionByBaseStatus += 1; } if (con >= 11){
	 * weightReductionByBaseStatus += 1; } break; case 1: if(con >= 15){
	 * weightReductionByBaseStatus += 1; } break; case 2: if(str >= 16){
	 * weightReductionByBaseStatus += 2; } if(con >= 15){
	 * weightReductionByBaseStatus += 2; } break; case 3: if(str >= 9){
	 * weightReductionByBaseStatus += 1; } if(con >= 13){
	 * weightReductionByBaseStatus += 1; } if(con >= 15){
	 * weightReductionByBaseStatus += 1; } break; case 4: if(str >= 13){
	 * weightReductionByBaseStatus += 2; } if(str >= 16){
	 * weightReductionByBaseStatus += 1; } if(con >= 9){
	 * weightReductionByBaseStatus += 1; } break; case 5: if(str >= 16){
	 * weightReductionByBaseStatus += 1; } break; case 6: if(str >= 18){
	 * weightReductionByBaseStatus += 1; } if(con >= 17){
	 * weightReductionByBaseStatus += 1; } if(con >= 18){
	 * weightReductionByBaseStatus += 1; } break;
	 * 
	 * case 7: if(str >= 17){ weightReductionByBaseStatus += 1; } break;
	 * default: break; } return weightReductionByBaseStatus; }
	 */

}
