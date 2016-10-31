package l1j.server.server.model.item.function;

import l1j.server.server.ActionCodes;
import l1j.server.server.clientpackets.ClientBasePacket;
import l1j.server.server.command.L1Commands;
import l1j.server.server.command.executor.L1CommandExecutor;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_AttackPacket;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Command;
import l1j.server.server.templates.L1Item;

public class GMWand extends L1ItemInstance {
	private static final long serialVersionUID = 1L;

	public GMWand(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			int itemId = this.getItemId();
			int spellsc_objid = 0;
			int spellsc_x = 0;
			int spellsc_y = 0;
			spellsc_objid = packet.readD();
			spellsc_x = packet.readH();
			spellsc_y = packet.readH();

			int heding = CharPosUtil.targetDirection(pc, spellsc_x, spellsc_y);
			pc.getMoveState().setHeading(heding);
			pc.sendPackets(new S_AttackPacket(pc, 0, ActionCodes.ACTION_Wand));

			if (!pc.isGm()) {
				pc.sendPackets(new S_ServerMessage(79));
				return;
			}

			L1Object findObject = L1World.getInstance().findObject(spellsc_objid);

			if (itemId == 5000686 && findObject == null) {
				pc.setChatTarget("");
				pc.sendPackets(new S_SystemMessage("ä�� Ÿ���� �����Ǿ����ϴ�."));
				return;
			}

			// ĳ������ �˻縷��
			if (itemId == 5000683 && findObject != null && findObject instanceof L1PcInstance) {
				try {
					L1Command command = L1Commands.get("����");
					Class<?> cls = Class.forName(complementClassName(command.getExecutorClassName()));
					L1CommandExecutor exe = (L1CommandExecutor) cls.getMethod("getInstance").invoke(null);
					exe.execute(pc, "����", ((L1PcInstance) findObject).getName());
				} catch (Exception e) {
					e.getStackTrace();
				}
			} else if (itemId == 5000684 && findObject != null && findObject instanceof L1PcInstance) {
				try {
					L1Command command = L1Commands.get("�˻�");
					Class<?> cls = Class.forName(complementClassName(command.getExecutorClassName()));
					L1CommandExecutor exe = (L1CommandExecutor) cls.getMethod("getInstance").invoke(null);
					String param = ((L1PcInstance) findObject).getName() + " ���";
					exe.execute(pc, "�˻�", param);
				} catch (Exception e) {
					e.getStackTrace();
				}
			} else if (itemId == 5000685 && findObject != null && findObject instanceof L1PcInstance) {
				try {
					L1Command command = L1Commands.get("�˻�");
					Class<?> cls = Class.forName(complementClassName(command.getExecutorClassName()));
					L1CommandExecutor exe = (L1CommandExecutor) cls.getMethod("getInstance").invoke(null);
					String param = ((L1PcInstance) findObject).getName() + " ����";
					exe.execute(pc, "�˻�", param);
				} catch (Exception e) {
					e.getStackTrace();
				}
			} else if (itemId == 5000686 && findObject instanceof L1PcInstance) {
				try {
					L1PcInstance player = (L1PcInstance) findObject;

					if (player.noPlayerCK) {
						pc.setChatTarget(player.getName());
						pc.sendPackets(new S_SystemMessage("ä�� Ÿ���� " + pc.getChatTarget() + "���� �����Ǿ����ϴ�."));
					} else {
						pc.sendPackets(new S_SystemMessage("�����Ͻ� Ÿ�� " + player.getName() + " ĳ���� ����ĳ���� �ƴմϴ�."));
					}
				} catch (Exception e) {
					e.getStackTrace();
				}
			} else {
				pc.sendPackets(new S_ServerMessage(79));
			}
		}
	}

	private String complementClassName(String className) {
		if (className.contains(".")) {
			return className;
		}
		if (className.contains(",")) {
			return className;
		}
		return "l1j.server.server.command.executor." + className;
	}

}
