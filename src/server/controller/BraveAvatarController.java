package server.controller;

import l1j.server.server.GeneralThreadPool;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SPMR;

public class BraveAvatarController implements Runnable {

	private static BraveAvatarController _instance;

	// private static Logger _log =
	// Logger.getLogger(SabuDGTime.class.getName());

	public static BraveAvatarController getInstance() {
		if (_instance == null) {
			_instance = new BraveAvatarController();
		}
		return _instance;
	}

	public BraveAvatarController() {
		// super("server.threads.pc.SabuDGTime");
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

	@Override
	public void run() {
		try {
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc == null || pc.getNetConnection() == null) {
					continue;
				}
				if (pc.getParty() != null
						&& pc.getParty().getLeader().isCrown()
						&& pc.getParty().getLeader().isSkillMastery(121)
						&& pc.getParty().getLeader().getLocation()
								.getTileLineDistance(pc.getLocation()) <= 8) {
					if (pc.getParty().getNumOfMembers() >= 7) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BRAVE_AVATAR))
							pc.getSkillEffectTimerSet().removeSkillEffect(
									L1SkillId.BRAVE_AVATAR);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BRAVE_AVATAR2))
							pc.getSkillEffectTimerSet().removeSkillEffect(
									L1SkillId.BRAVE_AVATAR2);
						if (!pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BRAVE_AVATAR3)) {
							pc.getAbility().addAddedStr((byte) 1);
							pc.getAbility().addAddedDex((byte) 1);
							pc.getAbility().addAddedInt((byte) 1);
							pc.getResistance().addMr(10);
							pc.getResistance().addStun(2);
							pc.getResistance().addHold(2);
							pc.sendPackets(new S_SPMR(pc), true);
							pc.sendPackets(new S_PacketBox(
									S_PacketBox.NONE_TIME_ICON, 1, 479), true);
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER,
									pc.get_PlusEr()), true);
						}
						pc.getSkillEffectTimerSet().setSkillEffect(
								L1SkillId.BRAVE_AVATAR3, 30 * 1000);
					} else if (pc.getParty().getNumOfMembers() >= 5) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BRAVE_AVATAR))
							pc.getSkillEffectTimerSet().removeSkillEffect(
									L1SkillId.BRAVE_AVATAR);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BRAVE_AVATAR3))
							pc.getSkillEffectTimerSet().removeSkillEffect(
									L1SkillId.BRAVE_AVATAR3);
						if (!pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BRAVE_AVATAR2)) {
							pc.getAbility().addAddedStr((byte) 1);
							pc.getAbility().addAddedDex((byte) 1);
							pc.getAbility().addAddedInt((byte) 1);
							pc.getResistance().addMr(9);
							pc.getResistance().addStun(2);
							pc.sendPackets(new S_SPMR(pc), true);
							pc.sendPackets(new S_PacketBox(
									S_PacketBox.NONE_TIME_ICON, 1, 478), true);
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER,
									pc.get_PlusEr()), true);
						}
						pc.getSkillEffectTimerSet().setSkillEffect(
								L1SkillId.BRAVE_AVATAR2, 30 * 1000);
					} else if (pc.getParty().getNumOfMembers() >= 2) {
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BRAVE_AVATAR3))
							pc.getSkillEffectTimerSet().removeSkillEffect(
									L1SkillId.BRAVE_AVATAR3);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BRAVE_AVATAR2))
							pc.getSkillEffectTimerSet().removeSkillEffect(
									L1SkillId.BRAVE_AVATAR2);
						if (!pc.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BRAVE_AVATAR)) {
							pc.getAbility().addAddedStr((byte) 1);
							pc.getAbility().addAddedDex((byte) 1);
							pc.getAbility().addAddedInt((byte) 1);
							pc.getResistance().addMr(8);
							pc.sendPackets(new S_SPMR(pc), true);
							pc.sendPackets(new S_PacketBox(
									S_PacketBox.NONE_TIME_ICON, 1, 477), true);
							pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER,
									pc.get_PlusEr()), true);
						}
						pc.getSkillEffectTimerSet().setSkillEffect(
								L1SkillId.BRAVE_AVATAR, 30 * 1000);
					}
				} else {
					if (pc.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.BRAVE_AVATAR))
						pc.getSkillEffectTimerSet().removeSkillEffect(
								L1SkillId.BRAVE_AVATAR);
					if (pc.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.BRAVE_AVATAR2))
						pc.getSkillEffectTimerSet().removeSkillEffect(
								L1SkillId.BRAVE_AVATAR2);
					if (pc.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.BRAVE_AVATAR3))
						pc.getSkillEffectTimerSet().removeSkillEffect(
								L1SkillId.BRAVE_AVATAR3);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GeneralThreadPool.getInstance().schedule(this, 1000);
	}

}
