package l1j.server.server.clientpackets;

import java.sql.Timestamp;

import l1j.server.Config;
import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.MonsterBookTeleportTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_DeleteCharOK;
import server.LineageClient;
import server.system.autoshop.AutoShop;
import server.system.autoshop.AutoShopManager;

public class C_DeleteChar extends ClientBasePacket {

	private static final String C_DELETE_CHAR = "[C] RequestDeleteChar";

	public C_DeleteChar(byte decrypt[], LineageClient client) throws Exception {
		super(decrypt);
		try {
			String name = readS();

			AutoShopManager shopManager = AutoShopManager.getInstance();
			AutoShop shopPlayer = shopManager.getShopPlayer(name);
			if (shopPlayer != null) {
				shopPlayer.logout();
				shopManager.remove(shopPlayer);
				shopPlayer = null;
			}

			/*
			 * if(DisconnectCharacter(name)){ client.kick(); client.close();
			 * return; }
			 */

			L1PcInstance pc = CharacterTable.getInstance().restoreCharacter(
					name);
			if (pc == null)
				return;
			if (!client.getAccountName().equalsIgnoreCase(pc.getAccountName())) {
				return;
			}

			if (pc != null && pc.getLevel() >= 51
					&& Config.DELETE_CHARACTER_AFTER_7DAYS) {
				over30lv(pc);
				client.sendPacket(new S_DeleteCharOK(
						S_DeleteCharOK.DELETE_CHAR_AFTER_7DAYS), true);
				return;
			}

			if (pc != null) {
				if (pc.getClanname() != null
						&& !pc.getClanname().equalsIgnoreCase("")) {
					L1Clan clan = L1World.getInstance().getClan(
							pc.getClanname());
					if (clan != null) {
						clan.removeClanMember(name);
					}
				}
			}

			CharacterTable.getInstance().deleteCharacter(
					client.getAccountName(), name);

			client.sendPacket(
					new S_DeleteCharOK(S_DeleteCharOK.DELETE_CHAR_NOW), true);
		} catch (Exception e) {
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
			// client.close();
			return;
		} finally {
			clear();
		}
	}

	public void over30lv(L1PcInstance pc) throws Exception {
		Timestamp deleteTime = null;
		if (pc.getType() < 32) {
			if (pc.isCrown())
				pc.setType(32);
			else if (pc.isKnight())
				pc.setType(33);
			else if (pc.isElf())
				pc.setType(34);
			else if (pc.isWizard())
				pc.setType(35);
			else if (pc.isDarkelf())
				pc.setType(36);
			else if (pc.isDragonknight())
				pc.setType(37);
			else if (pc.isIllusionist())
				pc.setType(38);
			else if (pc.isWarrior())
				pc.setType(39);

			// deleteTime = new Timestamp(System.currentTimeMillis() +
			// 604800000);
			deleteTime = new Timestamp(System.currentTimeMillis() + 86400000);// го╥Г
		} else {
			if (pc.isCrown())
				pc.setType(0);
			else if (pc.isKnight())
				pc.setType(1);
			else if (pc.isElf())
				pc.setType(2);
			else if (pc.isWizard())
				pc.setType(3);
			else if (pc.isDarkelf())
				pc.setType(4);
			else if (pc.isDragonknight())
				pc.setType(5);
			else if (pc.isIllusionist())
				pc.setType(6);
			else if (pc.isWarrior())
				pc.setType(7);
		}
		pc.setDeleteTime(deleteTime);
		pc.save();
	}

	@Override
	public String getType() {
		return C_DELETE_CHAR;
	}
}
