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
package l1j.server.server.model.Instance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import l1j.server.server.GeneralThreadPool;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.DoorSpawnTable;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1WarSpawn;
import l1j.server.server.model.L1World;
import l1j.server.server.serverpackets.S_CastleMaster;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.templates.L1Npc;

public class L1CrownInstance extends L1NpcInstance {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public L1CrownInstance(L1Npc template) {
		super(template);
	}

	@Override
	public void onAction(L1PcInstance player) {
		boolean in_war = false;
		if (player.getClanid() == 0) { // ũ���̼Ҽ�
			return;
		}
		String playerClanName = player.getClanname();
		L1Clan clan = L1World.getInstance().getClan(playerClanName);
		if (clan == null) {
			return;
		}

		if (!player.isCrown()) { // ���� �̿�
			return;
		}
		if (player.getGfxId().getTempCharGfx() != 0 && // ������
				player.getGfxId().getTempCharGfx() != 1) {
			return;
		}
		if (player.getId() != clan.getLeaderId()) // ������ �̿�
			return;

		if (!checkRange(player)) // ũ����� 1 �� �̳�
			return;

		if (clan.getCastleId() != 0) {// ���� ũ��
			player.sendPackets(new S_ServerMessage(474), true);// ����� ���� ���� �����ϰ�
																// �����Ƿ�, �ٸ� �÷θ�
																// ���� �� �����ϴ�.
			return;
		}

		// ũ����� ��ǥ�κ��� castle_id�� ���
		int castle_id = L1CastleLocation
				.getCastleId(getX(), getY(), getMapId());

		// �����ϰ� ������ üũ.��, ���ְ� ���� ���� ���� �ҿ�
		boolean existDefenseClan = false;
		L1Clan defence_clan = null;
		for (L1Clan defClan : L1World.getInstance().getAllClans()) {
			if (castle_id == defClan.getCastleId()) {
				// ���� ���� ũ��
				defence_clan = L1World.getInstance().getClan(
						defClan.getClanName());
				existDefenseClan = true;
				break;
			}
		}
		List<L1War> wars = L1World.getInstance().getWarList(); // ������ ����Ʈ�� ���
		for (L1War war : wars) {
			if (castle_id == war.GetCastleId()) { // �̸��̼��� ����
				in_war = war.CheckClanInWar(playerClanName);
				break;
			}
		}
		if (existDefenseClan && in_war == false) { // ���ְ� �־�, �����ϰ� ���� �ʴ� ���
			return;
		}

		if (player.isDead())
			return;

		// clan_data�� hascastle�� ������, ĳ���Ϳ� ũ����� ���δ�
		if (existDefenseClan && defence_clan != null) { // ���� ���� ũ���� �ִ�
			defence_clan.setCastleId(0);
			defence_clan.setCastleHasDate(null);
			ClanTable.getInstance().updateClan(defence_clan);
			L1PcInstance defence_clan_member[] = defence_clan
					.getOnlineClanMember();
			for (int m = 0; m < defence_clan_member.length; m++) {
				if (defence_clan_member[m].getId() == defence_clan
						.getLeaderId()) { // ���� ���� ũ���� ����
					defence_clan_member[m].sendPackets(new S_CastleMaster(0,
							defence_clan_member[m].getId()), true);
					// Broadcaster.broadcastPacket(defence_clan_member[m], new
					// S_CastleMaster(0, defence_clan_member[m].getId()));
					L1World.getInstance().broadcastPacketToAll(
							new S_CastleMaster(0,
									defence_clan_member[m].getId()), true);
					break;
				}
			}
		}

		SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		clan.setCastleHasDate(s.format(Calendar.getInstance().getTime()));
		clan.addWarpoint(1);
		clan.setCastleId(castle_id);
		ClanTable.getInstance().updateClan(clan);
		player.sendPackets(new S_CastleMaster(castle_id, player.getId()), true);
		// Broadcaster.broadcastPacket(player, new S_CastleMaster(castle_id,
		// player.getId()));
		L1World.getInstance().broadcastPacketToAll(
				new S_CastleMaster(castle_id, player.getId()), true);

		// ũ���� �ܸ̿� �Ÿ��� ���� �ڷ���Ʈ
		GeneralThreadPool.getInstance().execute(new tel(player, castle_id));

		// �޼��� ǥ��
		for (L1War war : wars) {
			// System.out.println(defence_clan.getClanName() + " > "+
			// war.GetDefenceClanName());
			if (defence_clan.getClanName().equalsIgnoreCase(
					war.GetDefenceClanName())
					&& war.CheckClanInWar(playerClanName) && existDefenseClan) {
				// ��ũ���� �����߿���, ���ְ� ����
				// System.out.println(war.GetCastleId() + " > ��");
				war.WinCastleWar(playerClanName);
				break;
			}
		}

		if (clan.getOnlineClanMember().length > 0) {
			// ���� �����߽��ϴ�.
			S_ServerMessage s_serverMessage = new S_ServerMessage(643);
			for (L1PcInstance pc : clan.getOnlineClanMember()) {
				pc.setCurrentHp(pc.getCurrentHp() + 3000); // �հ�Ŭ���� ���ָ���ä���
				pc.sendPackets(s_serverMessage);
			}
		}
		deleteMe();
		L1TowerInstance lt = null;
		for (L1Object l1object : L1World.getInstance().getObject()) {
			if (l1object instanceof L1TowerInstance) {
				lt = (L1TowerInstance) l1object;
				if (L1CastleLocation.checkInWarArea(castle_id, lt)) {
					lt.deleteMe();
				}
			}

		}
		// Ÿ���� spawn �Ѵ�
		L1WarSpawn warspawn = new L1WarSpawn();
		warspawn.SpawnTower(castle_id);

		for (L1DoorInstance door : DoorSpawnTable.getInstance().getDoorList()) {
			if (L1CastleLocation.checkInWarArea(castle_id, door)) {
				door.repairGate();
			}
		}

		// ������� ������� ������ �����ؾ� �ٽ� �����Ҽ�����
		L1War[] wr = L1World.getInstance().get_wars();
		for (int i = 0; i < wr.length; i++) {
			if (castle_id == wr[i].GetCastleId()) {
				L1World.getInstance().removeWar(wr[i]);
				continue;
			}
			if (wr[i].CheckClanInWar(playerClanName)) {
				wr[i].CeaseWar(playerClanName, wr[i].GetDefenceClanName());
			}
		}
		wr = null;

		WarTimeController.getInstance().AttackClanSetting(castle_id,
				playerClanName);

		L1PcInstance defence_clan_member[] = clan.getOnlineClanMember();
		for (L1PcInstance pp : defence_clan_member) {
			int castleid = L1CastleLocation.getCastleIdByArea(pp);
			if (castleid == castle_id) {
				WarTimeController.getInstance()
						.WarTime_SendPacket(castleid, pp);
			}
		}
	}

	@Override
	public void deleteMe() {
		_destroyed = true;
		if (getInventory() != null) {
			getInventory().clearItems();
		}
		allTargetClear();
		_master = null;
		L1World.getInstance().removeVisibleObject(this);
		L1World.getInstance().removeObject(this);
		for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(this)) {
			pc.getNearObjects().removeKnownObject(this);
			pc.sendPackets(new S_RemoveObject(this), true);
		}
		getNearObjects().removeAllKnownObjects();
	}

	private boolean checkRange(L1PcInstance pc) {
		return (getX() - 1 <= pc.getX() && pc.getX() <= getX() + 1
				&& getY() - 1 <= pc.getY() && pc.getY() <= getY() + 1);
	}

	private class tel implements Runnable {
		L1PcInstance player;
		int clanid;

		public tel(L1PcInstance pc, int _clanid) {
			player = pc;
			clanid = _clanid;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(10);
				int[] loc = new int[3];
				for (L1PcInstance pc : L1World.getInstance().getAllPlayers()) {
					if (pc.getClanid() != player.getClanid() && !pc.isGm()) {
						if (L1CastleLocation.checkInWarArea(clanid, pc)) {
							loc = L1CastleLocation.getGetBackLoc(clanid);

							L1Location locc = new L1Location(loc[0], loc[1],
									loc[2]);
							L1Location newLocation = locc.randomLocation(5,
									true);
							L1Teleport.teleport(pc, newLocation.getX(),
									newLocation.getY(), (short) newLocation
											.getMapId(), pc.getMoveState()
											.getHeading(), true);
							Thread.sleep(5);
						}
					} else {
						if (pc.war_zone) {
							pc.sendPackets(new S_NewCreateItem(1, 0, ""), true);
							pc.war_zone = false;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
