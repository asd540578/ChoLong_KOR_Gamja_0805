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

import l1j.server.Config;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.Opcodes;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import server.LineageClient;
import server.manager.eva;

public class C_ChatWhisper extends ClientBasePacket {

	private static final String C_CHAT_WHISPER = "[C] C_ChatWhisper";

	public C_ChatWhisper(byte abyte0[], LineageClient client) throws Exception {
		super(abyte0);
		try {
			String targetName = readS();
			String text = readS();
			L1PcInstance whisperFrom = client.getActiveChar();
			// ä�� �������� ���
			if (whisperFrom.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_CHAT_PROHIBITED)) {
				S_ServerMessage sm = new S_ServerMessage(242);
				whisperFrom.sendPackets(sm, true);
				return;
			}
			if (whisperFrom.getLevel() < Config.WHISPER_CHAT_LEVEL) {
				S_ServerMessage sm = new S_ServerMessage(404,
						String.valueOf(Config.WHISPER_CHAT_LEVEL));
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (!whisperFrom.isGm() && (targetName.compareTo("��Ƽ��") == 0)) {
				S_SystemMessage sm = new S_SystemMessage(
						"��ڴԲ��� �ӼӸ��� �� �� �����ϴ�.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (targetName.equalsIgnoreCase("***")) {
				S_SystemMessage sm = new S_SystemMessage("-> (***) " + text);
				whisperFrom.sendPackets(sm, true);
				eva.LogChatWisperAppend("[�Ӹ�]", whisperFrom.getName(), "******", text, ">");
				return;
			}

			L1PcInstance whisperTo = L1World.getInstance()
					.getPlayer(targetName);

			// ���忡 ���� ���
			if (whisperTo == null) {
				L1NpcShopInstance npc = null;
				npc = L1World.getInstance().getNpcShop(targetName);
				if (npc != null) {
					S_ChatPacket scp = new S_ChatPacket(npc, text,
							Opcodes.S_MESSAGE, 9);
					whisperFrom.sendPackets(scp, true);
					// S_SystemMessage sm = new
					// S_SystemMessage("-> ("+targetName+") "+text);
					// whisperFrom.sendPackets(sm); sm.clear(); sm = null;
					return;
				}
				S_ServerMessage sm = new S_ServerMessage(73, targetName);
				whisperFrom.sendPackets(sm, true);
				return;
			}
			// �ڱ� �ڽſ� ���� wis�� ���
			if (whisperTo.equals(whisperFrom)) {
				return;
			}

			if (whisperTo.getSkillEffectTimerSet().hasSkillEffect(
					L1SkillId.STATUS_CHAT_PROHIBITED)) {
				S_SystemMessage sm = new S_SystemMessage(
						"ä�ñ������� �������� �ӼӸ��� �Ҽ� �����ϴ�.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (text.length() > 26) {
				S_SystemMessage sm = new S_SystemMessage(
						"�Ӹ��� ���� �� �ִ� ���ڼ��� �ʰ��Ͽ����ϴ�.");
				whisperFrom.sendPackets(sm, true);
				return;
			}

			// ���ܵǰ� �ִ� ���
			if (whisperTo.getExcludingList().contains(whisperFrom.getName())) {
				S_ServerMessage sm = new S_ServerMessage(117,
						whisperTo.getName());
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (!whisperTo.isCanWhisper()) {
				S_ServerMessage sm = new S_ServerMessage(205,
						whisperTo.getName());
				whisperFrom.sendPackets(sm, true);
				return;
			}

			if (whisperTo instanceof L1RobotInstance) {
				S_ChatPacket scp = new S_ChatPacket(whisperTo, text,
						Opcodes.S_MESSAGE, 9);
				whisperFrom.sendPackets(scp, true);
				return;
			}
			S_ChatPacket scp2 = new S_ChatPacket(whisperTo, text,
					Opcodes.S_MESSAGE, 9);
			whisperFrom.sendPackets(scp2, true);
			S_ChatPacket scp3 = new S_ChatPacket(whisperFrom, text,
					Opcodes.S_TELL, 16);
			whisperTo.sendPackets(scp3, true);
			eva.LogChatWisperAppend("[�Ӹ�]", whisperFrom.getName(), whisperTo.getName(), text, ">");
			if (Config.�Ӹ�ä�ø����() > 0) {
				S_SystemMessage sm = new S_SystemMessage(whisperFrom.getName()
						+ " -> (" + whisperTo.getName() + ") " + text);
				for (L1PcInstance gm : Config.toArray�Ӹ�ä�ø����()) {
					if (gm.getNetConnection() == null) {
						Config.remove�Ӹ�(gm);
						continue;
					}
					if (gm == whisperFrom || gm == whisperTo) {
						continue;
					}

					gm.sendPackets(sm);
				}
			}
			
		} catch (Exception e) {

		} finally {
			clear();
		}
	}

	@Override
	public String getType() {
		return C_CHAT_WHISPER;
	}
}
