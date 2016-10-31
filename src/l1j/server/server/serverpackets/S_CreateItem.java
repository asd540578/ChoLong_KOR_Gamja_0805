package l1j.server.server.serverpackets;



import l1j.server.server.Opcodes;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.skill.L1SkillId;

public class S_CreateItem extends ServerBasePacket {

	private static final String S_CreateItem = "[S] S_CreateItem";
	private byte[] _byte = null;
	public static final int N_SHOP_BUFF = 0x6e;
	public static final int NEW_SKILL = 0x92;
	public static final int NEW_SKILLP = 0x91;
	public static final int CRAFT_ITEMLIST = 0x39;

	public S_CreateItem(int type,int id, int d) {
		buildPacket(type, id, d);
	}
	
	public S_CreateItem(int skillId, long time) {
		buildPacket(skillId, time);
	}
	
	public S_CreateItem(L1NpcInstance npc) {
		buildPacket(npc);
	}
	
	private void buildPacket(L1NpcInstance npc) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(CRAFT_ITEMLIST);
		writeH(0x08);
		int craftlist[] = null;	
		try{
			switch (npc.getNpcId()) { 
			case 301028:
				craftlist = new int[] { 2175, 2174};
				break;
	
			case 11887: 
				//·¹¿Ë
				craftlist = new int[] {1763,1764,1765,1766,1767,1768,1769,1770}; 
				
				break;
			case 7310086: //·°Å°
				craftlist = new int[] { 1771, 1772, 1773, 1774, 1775, 1776, 1777, 1778, 1779 }; 
				break;
			case 70662: //
				craftlist = new int[] { 2876,2877,2858,203,204,205,206,207,208,209,210 }; 
				break;
			case 70027:	
				craftlist = new int[] { 2739, 2792, 2731, 2732, 2733, 2734, 2735, 2736, 2737, 2738, 2788, 2789, 2790, 2791 };
				break;
			case 7210071:	
				craftlist = new int[] { 2528,1861,95,96,97,98,99,1960,1961 };
				break;
			}			
		int num;
		for (int i = 0; i < craftlist.length; i++) {
				writeC(0x12);
				num = craftlist[i];
				if (num > 127) {
					writeC(0x07);
				} else {
					writeC(0x06);
				}
				writeC(0x08);
				write4bit(num);
				writeH(0x10);
				writeH(0x18);
			}
			writeH(0x00);

		} catch (Exception e) {			
		}
	}
	private void buildPacket(int type,int id, int d) {
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeC(type);
		switch (type) {
			case NEW_SKILL:
				writeC(0x01);
				writeC(0x08);
				writeC(id);
				if (id == 5) {
					writeC(0x10);
					writeC(0x0a);
				}
				writeH(0x00);
				break;
			case NEW_SKILLP:
				writeC(0x01);
				writeC(0x0a);
				writeC(id != 5 ? 0x02 : 0x04);
				writeC(0x08);
				writeC(id);
				if (id == 5) {
					writeC(0x10);
					writeC(0x0a);
				}
				writeH(0xf18d);
			}
	}
	
	private void buildPacket(int skillId, long time) {
		int[] p = new int [3];
		switch (skillId) {
		case L1SkillId.¾Û¼Ö·çÆ®ºí·¹ÀÌµå:
			//92 08 14984 9471
			p[0] = 92; p[1] = 14984; p[2] = 9471;
			break;
		case L1SkillId.¼Ò¿ï¹è¸®¾î:
			//391 240 14989 9600
			p[0] = 391; p[1] = 14987; p[2] = 9600;
			break;
		case L1SkillId.Å¸ÀÌÅº¶óÀÌÂ¡:
			//487 4832 15012 9604
			p[0] = 487; p[1] = 15012; p[2] = 9604;
			break;
		case L1SkillId.µ¥½ºÈú:
			//73 8 14991 9601
			p[0] = 73; p[1] = 14991; p[2] = 9601;
			break;
		case L1SkillId.µð½ºÆ®·ÎÀÌ:
			//452 30 15002 9603
			p[0] = 452; p[1] = 15002; p[2] = 9603;
			break;
			
		case L1SkillId.±×·¹ÀÌ½º¾Æ¹ÙÅ¸:
			//122 15 14979 9470
			p[0] = 122;  p[1] = 14979 ; p[2] = 9470;
			break;
		case L1SkillId.¾î½Ø½Å:
			//489 15 14996 9602
			p[0] = 489;  p[1] = 14996 ; p[2] = 9602;
			break;
		case L1SkillId.ºí·¹ÀÌÂ¡½ºÇÇ¸´Ã÷:
			//692 03 14999 9614
			p[0] = 692;  p[1] = 14999 ; p[2] = 9614;
			break;
		case L1SkillId.ÀÓÆÑÆ®:
			//478 15 15008 9625
			p[0] = 478;  p[1] = 15008 ; p[2] = 9625;
			break;
			
		}
		writeC(Opcodes.S_EXTENDED_PROTOBUF);
		writeH(N_SHOP_BUFF);
		writeC(0x08);
		writeC(0x02);

		writeC(0x10);
		if(p[0] <=255)
			writeBit(p[0]);
		else
			writeH(p[0]);

		writeC(0x18);
		writeBit(time);

		writeC(0x20);
		writeC(0x09);

		writeC(0x28);
		writeH(p[1]);

		writeC(0x30);
		writeC(0x00);
//		if(skillId == L1SkillId.ºí·¹ÀÌÂ¡½ºÇÇ¸´){
//			writeC(0x38);
//			writeC(0x00);
//		}

		writeC(0x40);
		writeH(p[2]);

		writeC(0x48);
		writeC(0x00);
		writeC(0x50);
		writeC(0x00);
		writeC(0x58);
		writeC(0x01);
		writeC(0x00);
		writeC(0x00);
	}


	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	public String getType() {
		return S_CreateItem;
	}
}

/*
13 
39 00 
08 00 
12 07 
	08 e3 0d 10 00 18 00 
12 07
	08 e4 0d 10 00 18 00 
12 07 
	08 e5 0d 10 00 18 00
12 07 
	08 e6 0d 10 00 18 00 
12 07 
	08 e7 0d 10 00 18 00 
12 07 
	08 e8 0d 10 00 18 00 
12 07 
	08 e9 0d 10 00 18 00 
12 07 
	08 ea 0d 10 00 18 00 
00 00


*/