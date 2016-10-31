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
package l1j.server.GameSystem;

import java.util.ArrayList;

import l1j.server.server.ActionCodes;
import l1j.server.server.datatables.DoorSpawnTable;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_ServerMessage;

public class GhostHouse implements Runnable {

	enum Message {
		ENTER, WAIT_START, NOT_ENOUGH_STARTMEMBERS, GAMEEND
	};

	enum Status {
		ENTER, READY, PLAY, END, REST
	};

	private static final int GHOSTHOUSE_MAPID = 5140;
	private static final int LIMIT_MIN_ENTER_PLAYER_COUNT = 2; // 게임 입장에 필요한 인원
																// (본섭 : 5명)

	private L1PcInstance[] rankList;
	private L1PcInstance[] finishMember;
	private L1DoorInstance openDoorId;

	private int winnerCount = 0;
	private int finishMemberCount;

	public static Status GhostHouseStatus;
	private static GhostHouse instance;

	private final ArrayList<L1PcInstance> entermember = new ArrayList<L1PcInstance>();
	private final ArrayList<L1PcInstance> playmember = new ArrayList<L1PcInstance>();

	public static GhostHouse getInstance() {
		if (instance == null) {
			instance = new GhostHouse();
		}
		return instance;
	}

	@Override
	public void run() {
		try {
			setStatus(Status.PLAY);
			/*
			 * setStatus(Status.REST); while(true){ switch (GhostHouseStatus) {
			 * case ENTER: npcChat("유령의집 경기 입장 2분 전입니다."); Thread.sleep(60000L);
			 * npcChat("유령의집 경기 입장 1분 전입니다."); Thread.sleep(30000L); // 2분정도
			 * 입장유저받을겸 기다린다 120000L npcChat("유령의집 경기 입장 30초 전입니다.");
			 * enterMsg("유령의집 경기 입장 30초 전입니다."); Thread.sleep(10000L);
			 * npcChat("유령의집 경기 입장 20초 전입니다.");
			 * enterMsg("유령의집 경기 입장 20초 전입니다."); Thread.sleep(10000L);
			 * npcChat("유령의집 경기 입장 10초 전입니다.");
			 * enterMsg("유령의집 경기 입장 10초 전입니다."); Thread.sleep(5000L);
			 * npcChat("유령의집 경기 입장 5초 전입니다."); enterMsg("유령의집 경기 입장 5초 전입니다.");
			 * Thread.sleep(1000L); npcChat("유령의집 경기 입장 4초 전입니다.");
			 * enterMsg("유령의집 경기 입장 4초 전입니다."); Thread.sleep(1000L);
			 * npcChat("유령의집 경기 입장 3초 전입니다."); enterMsg("유령의집 경기 입장 3초 전입니다.");
			 * Thread.sleep(1000L); npcChat("유령의집 경기 입장 2초 전입니다.");
			 * enterMsg("유령의집 경기 입장 2초 전입니다."); Thread.sleep(1000L);
			 * npcChat("유령의집 경기 입장 1초 전입니다."); enterMsg("유령의집 경기 입장 1초 전입니다.");
			 * Thread.sleep(1000L); sendMessage(Message.ENTER);
			 * setStatus(Status.READY); break; case READY:
			 * npcChat("유령의집 경기 시작 대기 중입니다."); Thread.sleep(10000L);
			 * npcChat("유령의집 경기 시작 대기 중입니다."); Thread.sleep(5000L);
			 * finalPlayMemberCheck(); if(isGotEnoughStartMembers()){
			 * sendMessage(Message.WAIT_START); setStatus(Status.PLAY); } else {
			 * sendMessage(Message.NOT_ENOUGH_STARTMEMBERS); getOutGhostHouse();
			 * setStatus(Status.REST); npcChat("인원 부족으로 유령의집 경기가 종료 되었습니다."); }
			 * break; case PLAY: isTimeOver = false; Thread.sleep(3000L);
			 * clearEnterMember(); // 입장대기 삭제 doPolyPlayGameMember(); // 게임인원 변신
			 * Thread.sleep(5000L); countDownStartGame(); // 5,4,3,2,1 카운트 다운
			 * Thread.sleep(5000L); npcChat("유령의집 경기가 시작 되었습니다.");
			 * checkWinnerCount(); // 승자 체크 startPlayGameMemberGameTime(); // 게임
			 * 참가자들 00:00 시간 시작 GhostHouseStartDoorOpen(); // 5분 체크 시작 int j =
			 * 0; while (j <= 300){ if(getStatus() == Status.END){ break; }
			 * Thread.sleep(1000L); playmember_check(); sortRankList();
			 * refreshRankList(); ++j; } // 5분 체크 종료 if (notWinnerGame())
			 * isTimeOver = true; setStatus(Status.END); break; case END:
			 * sendMessage(Message.GAMEEND); Thread.sleep(10000L);
			 * playGameMembersDisplayPacketClear(); getOutGhostHouse(); if
			 * (finalCheckFinishMember()){ giveItemToWinnerMember(); }
			 * GhostHouseDoorClose(); allClear(); clearFinishMember();
			 * npcChat("유령의집 경기가 종료 되었습니다."); break; case REST: if(msgCount-- <
			 * 1){ npcChat("유령 한번 보구 가세요~"); msgCount = 10; }
			 * Thread.sleep(1000L); break; default: Thread.sleep(1000L); break;
			 * } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final S_Message_YN myn1256 = new S_Message_YN(1256, "");

	public void finalPlayMemberCheck() {
		L1PcInstance[] list = getPlayMemberArray();
		for (L1PcInstance pc : list) {
			if (pc != null) {
				if (pc.getMapId() != GHOSTHOUSE_MAPID) {
					removePlayMember(pc);
					pc.setHaunted(false);
				}
			}
		}
		list = null;
	}


	public void pushOpenDoorTrap(int doorid) {
		openDoorId = DoorSpawnTable.getInstance().getDoor(doorid);
		if (openDoorId != null) {
			if (openDoorId.getOpenStatus() == ActionCodes.ACTION_Close)
				openDoorId.open();
		}
	}

	public void pushFinishLineTrap(L1PcInstance pc) {
		if (isPlayingNow())
			setStatus(Status.END);
		if (finishMemberCount >= winnerCount)
			return;
		if (finishMember[finishMemberCount] != pc)
			finishMember[finishMemberCount++] = pc;
	}

	public void addEnterMember(L1PcInstance pc) {
		if (isReadyNow()) {
			pc.sendPackets(myn1256);
			return;
		}
		if (!isEnterMember(pc)) {
			entermember.add(pc);
			S_ServerMessage sm1253 = new S_ServerMessage(1253,
					Integer.toString(getEnterMemberCount()));
			pc.sendPackets(sm1253);
			sm1253 = null;
			if (getStatus() == Status.REST
					&& getEnterMemberCount() >= LIMIT_MIN_ENTER_PLAYER_COUNT) {
				setStatus(Status.ENTER);
			}
		}
	}

	public boolean isReadyNow() {
		return getStatus() == Status.READY ? true : false;
	}

	public boolean isPlayingNow() {
		return getStatus() == Status.PLAY ? true : false;
	}

	public void addPlayMember(L1PcInstance pc) {
		playmember.add(pc);
	}

	public int getPlayMembersCount() {
		return playmember.size();
	}

	public void removePlayMember(L1PcInstance pc) {
		playmember.remove(pc);
	}

	public void clearPlayMember() {
		playmember.clear();
	}

	public boolean isPlayMember(L1PcInstance pc) {
		return playmember.contains(pc);
	}

	public L1PcInstance[] getPlayMemberArray() {
		return playmember.toArray(new L1PcInstance[getPlayMembersCount()]);
	}

	public int getEnterMemberCount() {
		return entermember.size();
	}

	public void removeEnterMember(L1PcInstance pc) {
		entermember.remove(pc);
	}

	public void clearEnterMember() {
		entermember.clear();
	}

	public boolean isEnterMember(L1PcInstance pc) {
		return entermember.contains(pc);
	}

	public L1PcInstance[] getEnterMemberArray() {
		return entermember.toArray(new L1PcInstance[getEnterMemberCount()]);
	}

	public L1PcInstance[] getRank() {
		return rankList;
	}

	private void setStatus(Status i) {
		GhostHouseStatus = i;
	}

	private Status getStatus() {
		return GhostHouseStatus;
	}
}