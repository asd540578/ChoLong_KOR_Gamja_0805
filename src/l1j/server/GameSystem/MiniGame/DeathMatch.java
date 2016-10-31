package l1j.server.GameSystem.MiniGame;

import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;

public class DeathMatch extends MiniGame implements Runnable {
	private static DeathMatch instance;

	public static L1NpcInstance kusan = null;
	public static L1NpcInstance datoo = null;

	public static int DEATH_MATCH_PLAY_LEVEL = 1;

	private int playerCount;

	public static DeathMatch getInstance() {
		if (instance == null) {
			instance = new DeathMatch();
		}
		return instance;
	}

	public DeathMatch() {
		setMiniGameStatus(Status.REST);
	}

	public void run() {
		try {
			setMiniGameStatus(Status.PLAY);
			/*
			 * while(true){ switch(getMiniGameStatus()){ case REST:
			 * npcChat("������ġ ��� ���� 2�� ���Դϴ�."); Thread.sleep(60000L);
			 * npcChat("������ġ ��� ���� 1�� ���Դϴ�."); Thread.sleep(30000L);
			 * npcChat("������ġ ��� ���� 30�� ���Դϴ�.");
			 * enterMsg("������ġ ��� ���� 30�� ���Դϴ�."); Thread.sleep(10000L);
			 * npcChat("������ġ ��� ���� 20�� ���Դϴ�.");
			 * enterMsg("������ġ ��� ���� 20�� ���Դϴ�."); Thread.sleep(10000L);
			 * npcChat("������ġ ��� ���� 10�� ���Դϴ�.");
			 * enterMsg("������ġ ��� ���� 10�� ���Դϴ�."); Thread.sleep(5000L);
			 * npcChat("������ġ ��� ���� 5�� ���Դϴ�."); enterMsg("������ġ ��� ���� 5�� ���Դϴ�.");
			 * Thread.sleep(1000L); npcChat("������ġ ��� ���� 4�� ���Դϴ�.");
			 * enterMsg("������ġ ��� ���� 4�� ���Դϴ�."); Thread.sleep(1000L);
			 * npcChat("������ġ ��� ���� 3�� ���Դϴ�."); enterMsg("������ġ ��� ���� 3�� ���Դϴ�.");
			 * Thread.sleep(1000L); npcChat("������ġ ��� ���� 2�� ���Դϴ�.");
			 * enterMsg("������ġ ��� ���� 2�� ���Դϴ�."); Thread.sleep(1000L);
			 * npcChat("������ġ ��� ���� 1�� ���Դϴ�."); enterMsg("������ġ ��� ���� 1�� ���Դϴ�.");
			 * Thread.sleep(1000L); setMiniGameStatus(Status.ENTERREADY); break;
			 * case ENTERREADY: /*if(DEATH_MATCH_PLAY_LEVEL == 1)
			 * Broadcaster.broadcastPacket(kusan, new S_NpcChatPacket(datoo,
			 * "30�̻� 51���� ������ġ ���� ������Դϴ� �������� �ٶ��ϴ�", 0)); else
			 * Broadcaster.broadcastPacket(datoo, new S_NpcChatPacket(datoo,
			 * "52�̻� ������ġ ���� ������Դϴ� �������� �ٶ��ϴ�", 0));
			 * 
			 * Thread.sleep(5000L);
			 */
			/*
			 * if(getEnterMemberCount() < LIMIT_MIN_PLAYER_COUNT){
			 * ClearMiniGame(); }else{ sendMessage(); clearEnterMember();
			 * setMiniGameStatus(Status.READY); } break; case READY:
			 * Thread.sleep(60000L); if(getPlayerMemberCount() <
			 * LIMIT_MIN_PLAYER_COUNT){ NoReadyMiniGame(); ClearMiniGame();
			 * }else{ ReadyMiniGame(); Thread.sleep(5000L);
			 * enterMsg("������ġ ��� ���� 5�� ���Դϴ�."); Thread.sleep(1000);
			 * enterMsg("������ġ ��� ���� 4�� ���Դϴ�."); Thread.sleep(1000);
			 * enterMsg("������ġ ��� ���� 3�� ���Դϴ�."); Thread.sleep(1000);
			 * enterMsg("������ġ ��� ���� 2�� ���Դϴ�."); Thread.sleep(1000);
			 * enterMsg("������ġ ��� ���� 1�� ���Դϴ�."); Thread.sleep(1000);
			 * StartMiniGame(); setMiniGameStatus(Status.PLAY); } break; case
			 * PLAY: playerCount = 0; loof:for(int i = 0; i < 30; i++){ for(int
			 * j = 0; j < 20; j++){ Thread.sleep(3000L); decreaseHp(i*10);
			 * 
			 * playmember_check();
			 * 
			 * if(j % 5 == 0){ playerCount = getAlivingPlayerCount();
			 * sendMessage(); }
			 * 
			 * 
			 * if(playerCount == 1) break loof; } }
			 * setMiniGameStatus(Status.END); break; case END: if(playerCount ==
			 * 1) remainOnlyWinner(); else EndMiniGame(); ClearMiniGame();
			 * Thread.sleep(3000L); break; default: Thread.sleep(1000); break; }
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playmember_check() {
		L1PcInstance[] pcl = playmembers.toArray(new L1PcInstance[playmembers
				.size()]);
		S_PacketBox pb = new S_PacketBox(S_PacketBox.MINIGAME_TIME_CLEAR);
		for (L1PcInstance pc : pcl) {
			if (pc == null)
				continue;
			if (pc.getMapId() != 5153) {
				pc.setDeathMatch(false);
				pc.sendPackets(pb);
				removePlayerMember(pc);
			}
		}
		pb = null;
		pcl = null;
	}

	public void EndMiniGame() {
		L1PcInstance pc;
		S_PacketBox pb = new S_PacketBox(S_PacketBox.MINIGAME_TIME_CLEAR);
		S_ServerMessage sm = new S_ServerMessage(1275, null);
		for (int i = 0; i < getPlayerMemberCount(); i++) {
			pc = playmembers.get(i);
			if (pc == null)
				continue;
			if (pc.isGhost()) {
				pc.DeathMatchEndGhost();
			} else {
				L1Teleport.teleport(pc, 32624, 32813, (short) 4, 5, true);
			}
			pc.setDeathMatch(false);
			pc.getInventory().storeItem(L1ItemId.DEATHMATCH_SOUVENIR_BOX, 1);
			pc.sendPackets(pb);
			pc.sendPackets(sm);
		}
		pb = null;
		sm = null;
	}

	public void ClearMiniGame() {
		clearEnterMember();
		clearPlayerMember();
		setMiniGameStatus(Status.REST);

		DEATH_MATCH_PLAY_LEVEL *= -1;
	}

	public void StartMiniGame() {
		L1Map map = L1WorldMap.getInstance().getMap((short) DEATHMATCH_MAPID);
		L1PcInstance pc;
		S_PacketBox pb = new S_PacketBox(S_PacketBox.MINIGAME_TIME, 1800);
		for (int i = 0; i < getPlayerMemberCount(); i++) {
			pc = playmembers.get(i);
			pc.setDeathMatch(true);
			int sx = 32625 + _random.nextInt(27);
			int sy = 32885 + _random.nextInt(27);

			if (map.isInMap(sx, sy) && map.isPassable(sx, sy)) {
				L1Teleport.teleport(pc, sx, sy, (short) DEATHMATCH_MAPID, 5,
						true);
			} else {
				L1Teleport.teleport(pc, 32639, 32897, (short) DEATHMATCH_MAPID,
						5, true);
			}
			pc.sendPackets(pb);
		}
		pb = null;
	}

	public void sendMessage() {
		L1PcInstance pc;
		switch (getMiniGameStatus()) {
		case ENTERREADY:
			S_Message_YN yn = new S_Message_YN(1268, "");
			for (int i = 0; i < getEnterMemberCount(); i++) {
				pc = entermembers.get(i);
				// ������ġ�� �����Ͻðڽ��ϱ�? (Y/N)
				pc.sendPackets(yn);
			}
			yn = null;
			break;
		case PLAY:
			S_ServerMessage sm = new S_ServerMessage(1274,
					String.valueOf(playerCount));
			for (int i = 0; i < getPlayerMemberCount(); i++) {
				pc = playmembers.get(i);
				pc.sendPackets(sm);
			}
			sm = null;
			break;
		case END:
			break;
		case READY:
			break;
		case REST:
			break;
		default:
			break;
		}
	}

	public void remainOnlyWinner() {
		L1PcInstance pc;
		S_PacketBox pb = new S_PacketBox(S_PacketBox.MINIGAME_TIME_CLEAR);
		S_ServerMessage sm = new S_ServerMessage(1275, null);
		for (int i = 0; i < getPlayerMemberCount(); i++) {
			pc = playmembers.get(i);
			if (pc == null)
				continue;
			if (pc.isGhost()) {
				pc.DeathMatchEndGhost();
			} else {
				S_ServerMessage sm2 = new S_ServerMessage(1272, pc.getName());
				pc.sendPackets(sm2);
				sm2 = null;
				pc.getInventory().storeItem(41402, _random.nextInt(6) + 1);
				if (_random.nextInt(10) <= 2) {
					pc.getInventory().storeItem(
							L1ItemId.DEATHMATCH_WINNER_PIECE, 1);
				}
				L1Teleport.teleport(pc, 32624, 32813, (short) 4, 5, true);
			}
			pc.setDeathMatch(false);
			pc.getInventory().storeItem(L1ItemId.DEATHMATCH_SOUVENIR_BOX, 1);
			pc.sendPackets(pb);
			pc.sendPackets(sm);
		}
		sm = null;
		pb = null;
	}

	public void NoReadyMiniGame() {
		L1PcInstance pc;
		S_ServerMessage sm = new S_ServerMessage(1270);
		for (int i = 0; i < getPlayerMemberCount(); i++) {
			pc = playmembers.get(i);
			// ��� �ּ� �ο��� 5���� �������� �ʾ� ��⸦ ���� ���� �մϴ�. 1000 �Ƶ����� ���� ��Ƚ��ϴ�.
			pc.sendPackets(sm);
			pc.getInventory().storeItem(40308, 1000); // 1000 �Ƶ��� ����
			L1Teleport.teleport(pc, 32624, 32813, (short) 4, 5, true);
		}
		sm = null;
	}

	public void ReadyMiniGame() {
		L1PcInstance pc;
		S_ServerMessage sm = new S_ServerMessage(1269);
		for (int i = 0; i < getPlayerMemberCount(); i++) {
			pc = playmembers.get(i);
			// ������ġ �������� - ����
			pc.getInventory().storeItem(L1ItemId.DEATHMATCH_POTION_BOX, 1);
			pc.sendPackets(sm);
		}
		sm = null;
	}

	public void addWaitListMember(L1PcInstance pc) {
		if (getMiniGameStatus() == Status.READY) {
			pc.sendPackets(new S_Message_YN(1268, ""));
			return;
		}
		if (!isEnterMember(pc)) {
			String count = Integer.toString(getEnterMemberCount());
			S_SystemMessage sm = new S_SystemMessage(count
					+ "��° �������� ���� ����Ǿ����ϴ�.");
			// %d��° �������� ���� ����Ǿ����ϴ�.
			addEnterMember(pc);
			pc.sendPackets(sm);
			sm = null;
		} else {
			// �̹� ������ġ ���� ����Ǿ��ֽ��ϴ�.

			S_SystemMessage sm = new S_SystemMessage("�̹� ������ġ ���� ����Ǿ��ֽ��ϴ�.");
			pc.sendPackets(sm);
			sm = null;
		}
	}

	public void addPlayMember(L1PcInstance pc) {
		if (pc.isInParty()) { // ��Ƽ��
			pc.getParty().leaveMember(pc);
		}

		addPlayerMember(pc);

		L1SkillUse l1skilluse = new L1SkillUse();
		l1skilluse.handleCommands(pc, L1SkillId.CANCELLATION, pc.getId(),
				pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_LOGIN);
		l1skilluse = null;
		L1Teleport
				.teleport(pc, 32658, 32899, (short) DEATHMATCH_MAPID, 2, true); // ��
	}

	public void giveBackAdena(L1PcInstance pc) {
		// pc.getInventory().storeItem(40308, 1000); // 1000 �Ƶ��� ����
	}
}
