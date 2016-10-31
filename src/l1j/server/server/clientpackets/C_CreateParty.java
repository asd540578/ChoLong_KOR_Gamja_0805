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

package l1j.server.server.clientpackets;

import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Party;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import server.LineageClient;

// Referenced classes of package l1j.server.server.clientpackets:
// ClientBasePacket

public class C_CreateParty extends ClientBasePacket {

	private static final String C_CREATE_PARTY = "[C] C_CreateParty";

	public C_CreateParty(byte decrypt[], LineageClient client) throws Exception {
		super(decrypt);

		try {
			L1PcInstance pc = client.getActiveChar();

			int type = readC();
			if (type == 0 || type == 1 || type == 4 || type == 5) {// 0.�Ϲ� 1.�й�
				int targetId = 0;
				L1Object temp = null;
				if (type == 4 || type == 5) {
					String name = readS();
					L1PcInstance tar = L1World.getInstance().getPlayer(name);
					temp = tar;
					targetId = tar.getId();
				} else {
					targetId = readD();
					temp = L1World.getInstance().findObject(targetId);
				}

				if (temp instanceof L1PcInstance) {
					L1PcInstance targetPc = (L1PcInstance) temp;

					if (pc.getId() == targetPc.getId())
						return;

					if (targetPc.isInParty()) {
						// ���� �ٸ� ��Ƽ�� �Ҽ��� �ֱ� (����)������ �ʴ��� �� �����ϴ�
						S_ServerMessage sm = new S_ServerMessage(415);
						pc.sendPackets(sm, true);
						return;
					}

					if (pc.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.��Ƽ�ʴ������)) {
						int time = pc.getSkillEffectTimerSet()
								.getSkillEffectTimeSec(L1SkillId.��Ƽ�ʴ������);
						pc.sendPackets(new S_SystemMessage("(" + time
								+ ") ���� �ٽ� �̿��� �ּ���."));
						return;
					}

					pc.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.��Ƽ�ʴ������, 2000);

					if (pc.isInParty()) {
						if (pc.getParty().isLeader(pc)) {
							targetPc.setPartyID(pc.getId());
							// \f2%0\f>%s�κ��� \fU��Ƽ \f> �� �ʴ�Ǿ����ϴ�. ���մϱ�? (Y/N)
							S_Message_YN myn = new S_Message_YN(953,
									pc.getName());
							targetPc.sendPackets(myn, true);
						} else {
							// ��Ƽ�� �������� �ʴ��� �� �ֽ��ϴ�.
							S_ServerMessage sm = new S_ServerMessage(416);
							pc.sendPackets(sm, true);
						}
					} else {

						targetPc.setPartyID(pc.getId());
						switch (type) {
						case 4:
						case 0:
							pc.setPartyType(0);
							// \f2%0\f>%s�κ��� \fU��Ƽ \f> �� �ʴ�Ǿ����ϴ�. ���մϱ�? (Y/N)
							S_Message_YN myn = new S_Message_YN(953,
									pc.getName());
							targetPc.sendPackets(myn, true);
							break;
						case 5:
						case 1:
							pc.setPartyType(1);
							// \f2%0\f>%s \fU�ڵ��й���Ƽ\f> �ʴ��Ͽ����ϴ�. ����Ͻðڽ��ϱ�? (Y/N)
							S_Message_YN myn2 = new S_Message_YN(954,
									pc.getName());
							targetPc.sendPackets(myn2, true);

							if (targetPc.�����) {
								if (pc.isInParty()) { // �ʴ��ְ� ��Ƽ��
									if (pc.getParty().isVacancy() || pc.isGm()) { // ��Ƽ��
																					// ��
																					// ����
																					// �ִ�
										pc.getParty().addMember(targetPc);
									} else { // ��Ƽ�� �� ���� ����
										S_ServerMessage sm = new S_ServerMessage(
												417);
										pc.sendPackets(sm, true);
									}
								} else {
									// �ʴ��ְ� ��Ƽ���� �ƴϴ�
									L1Party party = new L1Party();
									party.addMember(pc);
									party.addMember(targetPc);
									S_ServerMessage sm = new S_ServerMessage(
											424, targetPc.getName());
									pc.sendPackets(sm, true);
								}
							}
							break;
						}
					}
				}
			} else if (type == 2) { // ä�� ��Ƽ

				String name = readS();
				if (pc.getSkillEffectTimerSet()
						.hasSkillEffect(L1SkillId.ä����Ƽ����)) {
					int time = pc.getSkillEffectTimerSet()
							.getSkillEffectTimeSec(L1SkillId.ä����Ƽ����);
					pc.sendPackets(new S_SystemMessage("(" + time
							+ ") ���� �ٽ� �̿��� �ּ���."));
					return;
				}

				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.ä����Ƽ����,
						60000 * 5);

				L1PcInstance targetPc = L1World.getInstance().getPlayer(name);
				if (targetPc == null) {
					if (L1World.getInstance().getNpcShop(name) == null) {
						// %0��� �̸��� ����� �����ϴ�.
						S_ServerMessage sm = new S_ServerMessage(109);
						pc.sendPackets(sm, true);
					}
					return;
				}
				if (pc.getId() == targetPc.getId())
					return;

				if (targetPc.isInChatParty()) {
					// ���� �ٸ� ��Ƽ�� �Ҽ��� �ֱ� (����)������ �ʴ��� �� �����ϴ�
					S_ServerMessage sm = new S_ServerMessage(415);
					pc.sendPackets(sm, true);
					return;
				}

				if (pc.isInChatParty()) {
					if (pc.getChatParty().isLeader(pc)) {
						targetPc.setPartyID(pc.getId());
						// \f2%0\f>%s�κ���\fUä�� ��Ƽ \f>�� �ʴ�Ǿ����ϴ�. ���մϱ�? (Y/N)
						S_Message_YN myn = new S_Message_YN(951, pc.getName());
						targetPc.sendPackets(myn, true);
					} else {
						// ��Ƽ�� �������� �ʴ��� �� �ֽ��ϴ�.
						S_ServerMessage sm = new S_ServerMessage(416);
						pc.sendPackets(sm, true);
					}
				} else {
					targetPc.setPartyID(pc.getId());
					// \f2%0\f>%s�κ���\fUä�� ��Ƽ \f>�� �ʴ�Ǿ����ϴ�. ���մϱ�? (Y/N)
					S_Message_YN smyn = new S_Message_YN(951, pc.getName());
					targetPc.sendPackets(smyn, true);
				}
			} else if (type == 3) {
				int target = readD();
				L1PcInstance nl = (L1PcInstance) L1World.getInstance()
						.findObject(target);
				if (pc.getId() != pc.getParty().getLeader().getId())
					return;
				nl.setPartyType(pc.getPartyType());
				pc.getParty().passLeader(nl);
			}

		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_CREATE_PARTY;
	}

}
