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

import l1j.server.GameSystem.Gamble.GambleInstance;
import l1j.server.server.Opcodes;
import l1j.server.server.datatables.NPCTalkDataTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1NpcTalkData;
import l1j.server.server.model.Instance.L1BoardInstance;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;

//Referenced classes of package l1j.server.server.serverpackets:
//ServerBasePacket

public class S_NPCPack extends ServerBasePacket {

	private static final String S_NPC_PACK = "[S] S_NPCPack";

	private static final int STATUS_POISON = 1;
	private static final int STATUS_PC = 4;
	private static final int STATUS_FREEZE = 8;
	private static final int STATUS_BRAVE = 16;

	private byte[] _byte = null;

	public S_NPCPack(L1NpcInstance npc) {

		/**
		 * ���� - 0:mob,item(atk pointer), 1:poisoned(), 2:invisable(), 4:pc,
		 * 8:cursed(), 16:brave(), 32:??, 64:??(??), 128:invisable but name
		 */
		int status = 0;

		if (npc.getPoison() != null) {
			if (npc.getPoison().getEffectId() == 1) {
				status |= STATUS_POISON;
			}
		}

		if (npc.getMoveState().getBraveSpeed() == 1)
			status |= STATUS_BRAVE;

		if (npc.getNpcTemplate().is_doppel()) {
			// PC�Ӽ��̶�� ������ �ູ�� �ǳ��� �� ���� ������ WIZ ����Ʈ�� ������ ����
			if (npc.getNpcTemplate().get_npcId() != 81069) {
				status |= STATUS_PC;
			}
		}

		if (npc instanceof GambleInstance || npc instanceof L1NpcShopInstance)
			status |= STATUS_PC;
		if (npc.isParalyzed()) {
			status |= STATUS_FREEZE;
		}

		writeC(Opcodes.S_PUT_OBJECT);
		writeH(npc.getX());
		writeH(npc.getY());
		writeD(npc.getId());

		if (npc instanceof L1BoardInstance && npc.getX() == 34063
				&& npc.getY() == 32275 && npc.getMapId() == 4)
			npc.getGfxId().setTempCharGfx(2207);

		if (npc.getGfxId().getTempCharGfx() == 0
				&& !(npc instanceof GambleInstance)) {
			writeH(npc.getGfxId().getGfxId());
		} else {
			writeH(npc.getGfxId().getTempCharGfx());
		}
		if (npc instanceof L1NpcShopInstance
				&& ((L1NpcShopInstance) npc).getState() == 1) {
			writeC(0x46);
		} else if ((npc.getNpcTemplate().is_doppel() && npc.getGfxId()
				.getGfxId() != 31) // �������� ����� �ϰ� ���� ������ ����
				|| npc.getGfxId().getGfxId() == 6632
				|| npc.getGfxId().getGfxId() == 6634 // ��������
				|| npc.getGfxId().getGfxId() == 6636
				|| npc.getGfxId().getGfxId() == 6638) { // ��������
			if (npc.getGfxId().getGfxId() == 12490
					|| npc.getGfxId().getGfxId() == 12494) {
				writeC(11);
			} else {
				writeC(4);
			}
			// ���
		} else if (npc.getGfxId().getGfxId() == 51 || npc.getNpcId() == 60519) { // â
																					// ���
																					// ,
																					// û����
			writeC(24);
		} else if (npc.getGfxId().getGfxId() == 816) { // ���� ��ũ��ī��Ʈ
			writeC(20);
		} else if (npc.getNpcId() == 70802) { // ����� �������� �Ƴ�
			writeC(4);
		} else {
			writeC(npc.getActionStatus());
		}

		writeC(npc.getMoveState().getHeading());
		writeC(npc.getLight().getChaLightSize());
		writeC(npc.getMoveState().getMoveSpeed());
		writeD(1);// npc.getExp ���� ���� ������ �ʴ´�.
		writeH(npc.getTempLawful());
		writeS(npc.getNameId());
		if (npc instanceof L1FieldObjectInstance) { // SIC�� ����, ���� ��
			L1NpcTalkData talkdata = NPCTalkDataTable.getInstance()
					.getTemplate(npc.getNpcTemplate().get_npcId());
			if (talkdata != null) {
				writeS(talkdata.getNormalAction()); // Ÿ��Ʋ�� HTML�����μ� �ؼ��ȴ�
			} else {
				writeS(null);
			}
		} else {
			writeS(npc.getTitle());
		}

		writeC(status);
		if (npc instanceof GambleInstance
				&& ((GambleInstance) npc).getClanid() > 0) {
			writeD(((GambleInstance) npc).getClanid()); // 0�̿ܿ� �ϸ�(��) C_27�� ����
			writeS(((GambleInstance) npc).getClanname());
		} else if (npc instanceof L1MerchantInstance
				&& ((L1MerchantInstance) npc).getClanid() > 0) {
			writeD(((L1MerchantInstance) npc).getClanid()); // 0�̿ܿ� �ϸ�(��) C_27��
															// ����
			writeS(((L1MerchantInstance) npc).getClanname());
		} else {
			writeD(0); // 0�̿ܿ� �ϸ�(��) C_27�� ����
			writeS(null);
		}
		writeS(null); // �����͸�?
		if (npc instanceof GambleInstance
				&& ((GambleInstance) npc).getClanid() > 0)
			writeC(L1Clan.CLAN_RANK_PUBLIC * 0x10); // ??
		else
			writeC(0xB0);
		writeC(0xFF); // HP
		writeC(0);
		if (npc instanceof GambleInstance)
			writeC(0);
		else
			writeC(npc.getLevel());

		if (npc instanceof L1NpcShopInstance
				&& ((L1NpcShopInstance) npc).getState() == 1)
			writeByte(((L1NpcShopInstance) npc).getShopName().getBytes());
		writeC(0);
		writeC(0xFF);
		writeC(0xFF);
		writeC(0x00);
		writeC(0xFF);
		writeH(0x00);
		// writeD(0x00);
		// writeH(0x00);

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
		return S_NPC_PACK;
	}

}
