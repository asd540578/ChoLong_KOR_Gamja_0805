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
package l1j.server.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.ObjectIdFactory;
import l1j.server.server.datatables.DoorSpawnTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.RaceRecordTable;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1LittleBugInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1RaceTicket;
import l1j.server.server.utils.SQLUtil;

public class L1BugBearRace_ruphy {

	public final ArrayList<L1LittleBugInstance> _littleBug = new ArrayList<L1LittleBugInstance>();

	private final ArrayList<L1NpcInstance> _merchant = new ArrayList<L1NpcInstance>();

	// private final HashMap<Integer, L1RaceTicket> _ticketPrice = new
	// HashMap<Integer, L1RaceTicket>();
	private final ArrayList<L1RaceTicket> _ticketPrice = new ArrayList<L1RaceTicket>();
	public static final int STATUS_NONE = 0;
	public static final int STATUS_READY = 1;
	public static final int STATUS_PLAYING = 2;
	public boolean buyTickets = true;

	private static final int[] startX = { 33522, 33520, 33518, 33516, 33514 };
	private static final int[] startY = { 32861, 32863, 32865, 32867, 32869 };

	private static final int[][] movingCount = { { 45, 4, 5, 6, 50 },
			{ 42, 6, 5, 7, 50 }, { 39, 8, 5, 8, 50 }, { 36, 10, 5, 9, 50 },
			{ 33, 12, 5, 10, 50 } };

	private static final int[] heading = { 6, 7, 0, 1, 2 };

	private static final Random _random = new Random();

	private int[] _betting = new int[5];

	private int _round;

	private int _roundId;

	private int _bugRaceStatus;

	public boolean _goal;

	private static L1BugBearRace_ruphy _instance;

	public static L1BugBearRace_ruphy getInstance() {
		if (_instance == null) {
			_instance = new L1BugBearRace_ruphy();
		}
		return _instance;
	}

	private L1BugBearRace_ruphy() {
		for (L1Object obj : L1World.getInstance().getObject()) {
			if (obj instanceof L1NpcInstance) {
				L1NpcInstance npc = (L1NpcInstance) obj;
				if (npc.getNpcTemplate().get_npcId() == 70041
						|| npc.getNpcTemplate().get_npcId() == 70035
						|| npc.getNpcTemplate().get_npcId() == 70042) {
					_merchant.add(npc);
				}
			}
		}
		race_loading();
		loadingGame();
	}

	public void loadingGame() {
		clearBug();
		setRoundId(ObjectIdFactory.getInstance().nextId());
		setRound(getRound() + 1);
		clearBetting();
		_goal = false;
		storeBug();
		closeDoor();
		setBugRaceStatus(STATUS_READY);
		broadCastTime("$376 " + 3 + " $377");
		L1ReadyThread rt = new L1ReadyThread();
		GeneralThreadPool.getInstance().execute(rt);
	}

	private void storeBug() {
		int arr[] = new int[5];
		for (int i = 0; i < 5; i++) {
			arr[i] = _random.nextInt(20);
			for (int j = 0; j < i; j++) {
				if (arr[i] == arr[j]) {
					arr[i] = _random.nextInt(20);
					i = i - 1;
					break;
				}
			}
		}

		L1Npc npcTemp = NpcTable.getInstance().getTemplate(100000);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(1);
		for (int i = 0; i < 5; i++) {
			L1LittleBugInstance bug = new L1LittleBugInstance(npcTemp, arr[i],
					startX[i], startY[i]);
			RaceRecordTable.getInstance().getRaceRecord(arr[i], bug);
			float winpoint = 0;
			float record = bug.getWin() + bug.getLose();
			if (record == 0) {
				winpoint = 0;
			} else {
				winpoint = bug.getWin() / record * 100;
			}
			bug.setWinPoint(nf.format(winpoint));
			_littleBug.add(bug);
		}
	}

	private void setSpeed() {
		for (L1LittleBugInstance bug : _littleBug) {
			int pulsSpeed = 0;
			int condition = bug.getCondition();
			if (condition == L1LittleBugInstance.GOOD) {
				pulsSpeed = 30;
			} else if (condition == L1LittleBugInstance.NORMAL) {
				pulsSpeed = 60;
			} else if (condition == L1LittleBugInstance.BAD) {
				pulsSpeed = 90;
			}
			// /** ���׺��� �ӵ� �·� ������� �������� ���� ���� **/
			pulsSpeed = 60;
			bug.setPassispeed(bug.getPassispeed() + _random.nextInt(pulsSpeed));
		}
	}

	public void startRace() {
		setBugRaceStatus(STATUS_PLAYING);
		buyTickets = false;
		calcDividend();
		setSpeed();
		openDoor();
		int i = 0;
		for (L1LittleBugInstance bug : _littleBug) {
			L1BugBearRacing bbr = new L1BugBearRacing(bug, i++);
			GeneralThreadPool.getInstance().execute(bbr);
		}
	}

	private void clearBug() {
		for (L1LittleBugInstance bug : _littleBug) {
			bug.deleteMe();
		}
		_littleBug.clear();
	}

	private void openDoor() {
		for (L1DoorInstance door : DoorSpawnTable.getInstance().getDoorList()) {
			if (door.getGfxId().getGfxId() == 1487) {
				door.open();
			}
		}
	}

	private void closeDoor() {
		for (L1DoorInstance door : DoorSpawnTable.getInstance().getDoorList()) {
			if (door.getGfxId().getGfxId() == 1487) {
				door.close();
			}
		}
	}

	private void broadCastTime(String chat) {
		for (L1NpcInstance npc : _merchant) {
			Broadcaster.wideBroadcastPacket(npc, new S_NpcChatPacket(npc, chat,
					2));
		}
	}

	private void broadCastWinner(String winner) {
		String chat = "�� " + getRound() + "$366" + " '" + winner + "' "
				+ "$367";
		for (L1NpcInstance npc : _merchant) {
			if (npc.getNpcTemplate().get_npcId() == 70035) { // ����
				Broadcaster.wideBroadcastPacket(npc, new S_NpcChatPacket(npc,
						chat, 2));
			}
		}
	}

	private void calcDividend() {
		double[] dividend = new double[5];
		L1LittleBugInstance[] bugs = getBugsArray();
		double allBetting = 0;

		for (int b : _betting) {
			allBetting += b;
		}
		// System.out.println("������ ���Ե� ���� Ƽ�� : ("+allBetting+")��");
		allBetting = allBetting * 500;
		// System.out.println("���Ե� ���� ��Ż �ݾ� : ("+allBetting+")�Ƶ�");
		for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
			if (pc == null || pc.getNetConnection() == null || !pc.isGm())
				continue;
			pc.sendPackets(new S_SystemMessage("�� " + getRound()
					+ " ���� Ƽ�� ���� �� �ݾ�: " + (long) allBetting), true);
		}

		allBetting = allBetting * 0.4;
		// System.out.println("���� ������ �ݾ� : ("+allBetting+")�Ƶ�");

		for (int i = 0; i < 5; i++) {
			// System.out.println(i+"�� ���� Ƽ�� : ("+_betting[i]+")��");
			if (_betting[i] == 0) {
				dividend[i] = allBetting / 500;
			} else {
				dividend[i] = allBetting / (_betting[i] * 500);
			}
			// /** ���� ����� ���� **/
			bugs[i].setDividend((_random.nextDouble() * 2) + 1.6);
			// bugs[i].setDividend(dividend[i]);
		}
		for (int i = 0; i < 2; i++) {
			bugs[_random.nextInt(5)]
					.setDividend((_random.nextDouble() * 5.7) + 3.5);
		}
		if (_random.nextInt(30) == 1) {
			bugs[_random.nextInt(5)]
					.setDividend((_random.nextDouble() * 5.6) + 10);
		}

	}

	public String getTicketName(int i) {
		L1LittleBugInstance bug = _littleBug.get(i);
		return new StringBuilder().append(getRound()).append("-")
				.append(bug.getNumber() + 1).append(" ").append(bug.getName())
				.toString();
	}

	public int[] getTicketInfo(int i) {
		return new int[] { getRoundId(), getRound(),
				_littleBug.get(i).getNumber() };
	}

	public double getTicketPrice(L1ItemInstance item) {

		for (L1RaceTicket ticket : _ticketPrice) {
			if (ticket.getRoundId() == item.getSecondId()
					&& ticket.getWinner() == item.getTicketId()) {
				return ticket.getDividend();
			}
		}

		/*
		 * L1RaceTicket ticket = _ticketPrice.get(item.getSecondId()); if
		 * (ticket == null) { return 0; } if (ticket.getWinner() ==
		 * item.getTicketId()) { return ticket.getDividend(); }
		 */

		return 0;
	}

	public String[] makeStatusString() {
		ArrayList<String> status = new ArrayList<String>();
		for (L1LittleBugInstance bug : _littleBug) {
			status.add(bug.getName());
			if (bug.getCondition() == L1LittleBugInstance.GOOD) {
				status.add("$368");
			} else if (bug.getCondition() == L1LittleBugInstance.NORMAL) {
				status.add("$369");
			} else if (bug.getCondition() == L1LittleBugInstance.BAD) {
				status.add("$370");
			}
			status.add(bug.getWinPoint() + "%");
		}
		return status.toArray(new String[status.size()]);
	}

	private synchronized void finish(L1LittleBugInstance bug) {
		if (!_goal) {
			_goal = true;
			byte i = 0;
			for (L1LittleBugInstance b : _littleBug) {
				if (b == bug)
					break;
				i++;
			}
			int allBetting = 0;
			for (int b : _betting) {
				allBetting += b;
			}
			allBetting = allBetting * 500;
			for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
				if (pc == null || pc.getNetConnection() == null || !pc.isGm())
					continue;
				pc.sendPackets(
						new S_SystemMessage("�� " + getRound() + " ���� ���"), true);
				pc.sendPackets(
						new S_SystemMessage(
								" ��Ƽ��: "
										+ (long) allBetting
										+ " ���Ƽ��: "
										+ (long) (_betting[i]
												* bug.getDividend() * 500)
										+ " ����: "
										+ ((long) allBetting - (long) (_betting[i]
												* bug.getDividend() * 500))),
						true);
			}

			race_difference((int) allBetting,
					(int) (_betting[i] * bug.getDividend() * 500));

			L1RaceTicket ticket = new L1RaceTicket(getRoundId(),
					bug.getNumber(), bug.getDividend());
			_ticketPrice.add(ticket);
			race_divAdd(getRoundId(), bug.getNumber(), bug.getDividend());
			// _ticketPrice.put(getRoundId(), ticket);
			// ����, Ƽ�Ͼ��̵�, ��� ���̺� �Է�
			// ���ٽÿ� buyticket =true�� getRoundId() gub.getNember(), 1 �Է�
			broadCastWinner(bug.getNameId());
			setBugRaceStatus(STATUS_NONE);
			RaceRecordTable.getInstance().updateRaceRecord(bug.getNumber(),
					bug.getWin() + 1, bug.getLose());
			L1WaitingTimer wt = new L1WaitingTimer();
			wt.begin();
		} else {
			RaceRecordTable.getInstance().updateRaceRecord(bug.getNumber(),
					bug.getWin(), bug.getLose() + 1);
		}
	}

	private L1LittleBugInstance[] getBugsArray() {
		return _littleBug.toArray(new L1LittleBugInstance[_littleBug.size()]);
	}

	public synchronized void addBetting(int num, int count) {
		if (getBugRaceStatus() == STATUS_READY) {
			_betting[num] += count;
		}
	}

	private void clearBetting() {
		_betting = new int[5];
	}

	public void setRound(int i) {
		_round = i;
	}

	public int getRound() {
		return _round;
	}

	public void setRoundId(int i) {
		_roundId = i;
	}

	public int getRoundId() {
		return _roundId;
	}

	private void setBugRaceStatus(int i) {
		_bugRaceStatus = i;
	}

	public int getBugRaceStatus() {
		return _bugRaceStatus;
	}

	class L1WaitingTimer extends TimerTask {
		@Override
		public void run() {
			try {

				loadingGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void begin() {
			Timer _timer = new Timer();
			_timer.schedule(this, 50 * 1000);
		}
	}

	class L1ReadyThread implements Runnable {
		@Override
		public void run() {
			try {

				buyTickets = true;
				broadCastTime("���̽� ǥ �ǸŰ� ���۵Ǿ����ϴ�.");
				for (int time = 3; time > 0; time--) {
					if (time <= 3) {
						broadCastTime("$376 " + time + " $377");
					}
					try {
						Thread.sleep(60000);
					} catch (Exception e) {
					}
				}
				// int ran = 0;
				/*
				 * float winpoint = 0; float MinDis = 0; int i = 0;
				 * L1LittleBugInstance winerbug = null; for (L1LittleBugInstance
				 * bug : _littleBug) { float record = bug.getWin() +
				 * bug.getLose(); winpoint = bug.getWin() / record * 100;
				 * bug.setRainNum(i); if(Math.max(winpoint, MinDis) ==
				 * winpoint){ winerbug = bug; MinDis = winpoint; } i++; }
				 */

				// System.out.println("�·� ���� ���׺��� : "+winerbug.getNameId()+" �·� : "+winerbug.getWinPoint()+"%");

				buyTickets = false;
				broadCastTime("���̽�ǥ �ǸŰ� ����Ǿ����ϴ�.");
				L1BroadCastDividend bcd = new L1BroadCastDividend();
				GeneralThreadPool.getInstance().execute(bcd);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class L1BroadCastDividend implements Runnable {
		private L1NpcInstance _npc;

		public L1BroadCastDividend() {
			for (L1NpcInstance npc : _merchant) {
				if (npc.getNpcTemplate().get_npcId() == 70041) { // ��Ų
					_npc = npc;
				}
			}
		}

		@Override
		public void run() {
			try {

				Broadcaster.wideBroadcastPacket(_npc, new S_NpcChatPacket(_npc,
						"$363", 2), true);
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}
				Broadcaster.wideBroadcastPacket(_npc, new S_NpcChatPacket(_npc,
						"$364", 2), true);
				startRace();
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}
				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumFractionDigits(1);
				nf.setMinimumFractionDigits(1);
				for (L1LittleBugInstance bug : _littleBug) {
					String chat = bug.getName() + " $402 "
							+ nf.format(bug.getDividend());
					Broadcaster.wideBroadcastPacket(_npc, new S_NpcChatPacket(
							_npc, chat, 2), true);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	class L1BugBearRacing implements Runnable {
		private L1LittleBugInstance _bug;
		private int _num;

		public L1BugBearRacing(L1LittleBugInstance bug, int num) {
			_bug = bug;
			_num = num;
		}

		@Override
		public void run() {
			try {

				int i = 0;
				int count = movingCount[_num][i];
				while (true) {
					if (count == 0) {
						count = movingCount[_num][++i];
					}
					if (_random.nextInt(150) == 0) {
						Broadcaster.broadcastPacket(_bug, new S_DoActionGFX(
								_bug.getId(), 30));
						try {
							Thread.sleep((2360 + _random.nextInt(1000)) / 2);
						} catch (Exception e) {
						}
					} else {
						count--;
						_bug.setDirectionMove(heading[i]);
						try {
							Thread.sleep((int) (_bug.getPassispeed() / 2.2));
						} catch (Exception e) {
						}
					}
					if (_bug.getX() == 33527) {
						finish(_bug);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void race_difference(int b, int s) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO race_difference SET date=?, Round=?, buy=?, winner_sell=?, difference=?");
			pstm.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			pstm.setInt(2, getRound());
			pstm.setInt(3, b);
			pstm.setInt(4, s);
			pstm.setInt(5, b - s);
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void race_divAdd(int id, int b, double d) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("INSERT INTO race_div_record SET id=?, bug_number=?, dividend=?");
			pstm.setInt(1, id);
			pstm.setInt(2, b);
			pstm.setInt(3, (int) (d * 1000));
			pstm.executeUpdate();
		} catch (SQLException e) {
			// e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}

	public void race_loading() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM race_div_record");
			rs = pstm.executeQuery();
			while (rs.next()) {
				L1RaceTicket ticket = new L1RaceTicket(rs.getInt("id"),
						rs.getInt("bug_number"),
						(double) (rs.getInt("dividend")) / 1000);
				_ticketPrice.add(ticket);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
}