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

package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;

// Referenced classes of package l1j.server.server.serverpackets:
// ServerBasePacket

public class S_UnityIcon extends ServerBasePacket {

	public S_UnityIcon(int DECREASE, int DECAY_POTION, int SILENCE,
			int VENOM_RESIST, int WEAKNESS, int DISEASE, int DRESS_EVASION,
			int BERSERKERS, int NATURES_TOUCH, int WIND_SHACKLE,
			int ERASE_MAGIC, int ADDITIONAL_FIRE, int ELEMENTAL_FALL_DOWN,
			int ELEMENTAL_FIRE, int STRIKER_GALE, int SOUL_OF_FLAME,
			int POLLUTE_WATER, int EXP_POTION, int SCROLL, int SCROLLTPYE,
			int TIKALBOSSDIE, int CONCENTRATION, int INSIGHT, int PANIC,
			int MORTAL_BODY, int HORROR_OF_DEATH, int FEAR, int PATIENCE,
			int GUARD_BREAK, int DRAGON_SKIN, int STATUS_FRUIT, int COMA,
			int COMA_TYPE, int FEATHER_BUFF, int FEATHER_TYPE, int MAAN_TIME,
			int MAAN, int �������, int wisdom) {

		writeC(Opcodes.S_EVENT);
		writeC(0x14);
		writeC(0xD1);

		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(DECREASE); // ��ũ���� ����Ʈ DECREASE
		writeC(DECAY_POTION); // ������ ����
		writeC(0x00);
		writeC(SILENCE); // ���Ϸ���
		// 10

		writeC(VENOM_RESIST); // ���� ������Ʈ
		writeC(WEAKNESS); // ��ũ�Ͻ�
		writeC(DISEASE); // ������
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		// 20

		writeC(DRESS_EVASION); // �巹���̺����� !
		writeC(BERSERKERS); // ����Ŀ�� !
		writeC(NATURES_TOUCH); // �����Ľ���ġ
		writeC(WIND_SHACKLE); // �����Ŭ
		writeC(ERASE_MAGIC); // �̷��������
		writeC(0x00); // ������������ε� ������ ī���͹̷�ȿ����� ������
		writeC(ADDITIONAL_FIRE); // ���ų� ���̾�
		writeC(ELEMENTAL_FALL_DOWN); // ������Ż���ٿ�
		writeC(0x00);
		writeC(ELEMENTAL_FIRE); // ������Ż ���̾�
		// 30

		writeC(0x00);
		writeC(0x00); // ��ô������ �������� ��ġä�����ϰ��մϴ�???�����ܵ��̻���
		writeC(0x00);// 9F?
		writeC(STRIKER_GALE); // ��Ʈ����Ŀ����
		writeC(SOUL_OF_FLAME); // �ҿ���� ������
		writeC(POLLUTE_WATER); // �÷�������
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00); // �Ӽ����׷� 10?
		// 40

		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(wisdom); // sp
		writeC(EXP_POTION); // exp
		writeC(SCROLL); // ������ȭ�ֹ��� 123 ������?
		// 50

		writeC(SCROLLTPYE); // 0-hp50hpr4, 1-mp40mpr4, 2-��Ÿ3����3sp3 4C-�ÿ��� ��������
		writeC(0x00);// 3f
		writeC(0x00);
		writeC(TIKALBOSSDIE);// writeC(0xa2); ���ž�� �ູ
		if (TIKALBOSSDIE != 0) {
			writeC(0x22);// writeC(0x22); ���ž�� �ູ
		} else {
			writeC(0x00);// writeC(0x22); ���ž�� �ູ
		}
		writeC(CONCENTRATION); // ����Ʈ���̼�
		writeC(INSIGHT); // �λ���Ʈ
		writeC(PANIC); // �д�
		writeC(MORTAL_BODY); // ��Ż�ٵ�
		writeC(HORROR_OF_DEATH); // ȣ����굥��
		writeC(FEAR); // �Ǿ�

		// 60
		writeC(PATIENCE); // ���̼ǽ�
		writeC(GUARD_BREAK); // ����극��ũ
		writeC(DRAGON_SKIN); // �巡�ｺŲ
		writeC(STATUS_FRUIT);// 14 //���׵��
		writeC(0x00);
		writeC(0x00);
		writeC(COMA);// �ð�
		writeC(COMA_TYPE);// Ÿ��
		writeC(0x00);
		writeC(0x00);

		// 70
		writeC(0x00); // 1a
		writeC(0x00); // 35
		writeC(0x00); // 0d
		writeC(0x00);// 38
		writeC(0x00);// bb // 38
		writeC(0x00);// d3 // 1f
		writeC(0x00);// 54 // 01
		writeC(0x00); // 52
		writeC(0x00);
		writeC(0x00);

		// 80
		writeC(MAAN_TIME);
		writeC(MAAN);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);

		// 90
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(FEATHER_BUFF);// ��� ���� ���� ����
		writeC(FEATHER_TYPE); // 0x46 �ſ����� 0x47 ���� 0x48 ���� 0x49 ���� c2
		writeC(0x00);
		writeC(0x00);

		// 100
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);

		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);

		writeC(EXP_POTION > 0 ? 0x01 : 0x00); // 2?
		writeC(0x00);

		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00);
		writeC(0x00); // 117

		writeD(0x00);
		writeD(0x00);

		writeH(0x00); // 127
		writeH(0x00);
		writeH(0x00);// 0x0835
		writeD(0x00);
		writeH(0x00); // 137
		writeD(0x00);
		writeD(0x00);
		writeH(0x00); // 147
		writeD(0x00);
		writeD(0x00);
		writeH(0x00); // 157
		writeC(0x00);// C1
		writeH(�������); // 162~3
		writeC(������� > 0 ? 0xC1 : 0x00); // 164
		writeD(0x00);
		writeH(0x00); // 170

		writeD(0x00);
		writeD(0x00);
		writeD(0x00);// 182

		writeD(0x00);
		writeD(0x00);
		writeD(0x00);
		writeD(0x00);// 198

		writeD(0x00);// 202
		writeD(0x00);// 206
		writeC(0x00);// 207
		writeH(0);// 208~209

		/*
		 * "A4 14 b0 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "a8 93 00 00 00 00 00 a8 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 9f 00 00 00 00 00 00 80 9f 80 a0 00 00 "+
		 * "00 00 00 1c 1c 3f 00 00 00 b6 00 2c 00 00 00 00 "+
		 * "b2 00 00 00 14 00 00 00 00 00 1a 35 0d 00 0d ea "+
		 * "71 51 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 c2 00 00 00 00 00 00 00 00 00 00 "+
		 * "01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00";
		 */

		/*
		 * String s = "0f 14 d1 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 3f 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 14 00 00 00 00 00 00 00 00 00 38 bb "+
		 * "d3 54 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "86 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 38 08 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 "+
		 * "00 00 00 00 00 08 07"; StringTokenizer token = new
		 * StringTokenizer(s); while (token.hasMoreTokens()) {
		 * writeC(Integer.parseInt(token.nextToken(), 16)); }
		 */
		// 00
	}

	@Override
	public byte[] getContent() {
		return getBytes();
	}
}
