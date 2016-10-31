package l1j.server.server.serverpackets;

import l1j.server.server.Opcodes;
import l1j.server.server.clientpackets.C_SabuTeleport;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;

public class S_SabuTell extends ServerBasePacket {

	private static final String S_SabuTell = "[S] S_SabuTell";
	private byte[] _byte = null;

	public S_SabuTell(L1PcInstance pc) {
		if (pc.�ڴ��() || pc.isTeleport() || pc.isDead()
				|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�������)) {
			return;
		}
		if (pc.getTelType() != 4) { // ������
			pc.getSkillEffectTimerSet().setSkillEffect(
					L1SkillId.ABSOLUTE_BARRIER, 500);
			pc.setTeleport(true);
		}
		pc.�ڴ��(true);
		// if(pc.getTelType() == 1 || pc.getTelType() == 4 || pc.getTelType() ==
		// 10){
		if (pc.getTelType() == 1) {
			writeC(Opcodes.S_REQUEST_SUMMON); //�̰��� ����
			writeH(0x00);
			writeD(0x00);
		} else {
			writeC(Opcodes.S_REQUEST_SUMMON);
			writeH(0x00);
			pc.setTeleLockCheck();
		}
	}

	public S_SabuTell(L1PcInstance pc, int time) {
		if (pc.�ڴ��() || pc.isTeleport() || pc.isDead()) {
			return;
		}
		if (pc.getTelType() != 4) { // ������
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_�������,
					time);
			pc.setTeleport(true);
		}
		pc.�ڴ��(true);
		// if(pc.getTelType() == 1 || pc.getTelType() == 4 || pc.getTelType() ==
		// 10){
		if (pc.getTelType() == 1) {
			writeC(Opcodes.S_REQUEST_SUMMON);
			writeH(0x00);
			writeD(0x00);
		} else {
			writeC(Opcodes.S_REQUEST_SUMMON);
			writeH(0x00);
			pc.setTeleLockCheck();
		}
	}

	// ���� ���� ��
	public S_SabuTell(L1PcInstance pc, boolean ck) {
		if (pc.�ڴ��() || pc.isTeleport() || pc.isDead()) {
			return;
		}
		if (pc.getTelType() != 4) { // ������
			pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.STATUS_�������,
					500);
			pc.setTeleport(true);
		}
		pc.�ڴ��(true);
		if (pc.getTelType() == 1) {
			writeC(Opcodes.S_REQUEST_SUMMON);
			writeH(0x00);
			writeD(0x00);
		} else {
			new C_SabuTeleport(new byte[1], pc.getNetConnection());
		}
	}

	@Override
	public byte[] getContent() {
		if (_byte == null) {
			_byte = getBytes();
		}
		return _byte;
	}

	@Override
	public String getType() {
		return S_SabuTell;
	}
}
