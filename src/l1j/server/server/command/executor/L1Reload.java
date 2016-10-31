/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.   See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package l1j.server.server.command.executor;

import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.GameSystem.CrockSystem;
import l1j.server.GameSystem.NpcBuyShop.NpcBuyShop;
import l1j.server.GameSystem.NpcBuyShop.NpcBuyShopSpawn;
import l1j.server.GameSystem.NpcTradeShop.NpcTradeShop;
import l1j.server.server.datatables.AdenShopTable;
import l1j.server.server.datatables.AutoLoot;
import l1j.server.server.datatables.DragonRaidItemTable;
import l1j.server.server.datatables.DropItemTable;
import l1j.server.server.datatables.DropTable;
import l1j.server.server.datatables.IpCheckTable;
import l1j.server.server.datatables.ItemEnchantList;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.MapFixKeyTable;
import l1j.server.server.datatables.MobSkillTable;
import l1j.server.server.datatables.NPCTalkDataTable;
import l1j.server.server.datatables.NoticeTable;
import l1j.server.server.datatables.NpcActionTable;
import l1j.server.server.datatables.NpcShopSpawnTable;
import l1j.server.server.datatables.NpcShopTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.datatables.ResolventTable;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.datatables.SpawnTable;
import l1j.server.server.datatables.WeaponAddDamage;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1TreasureBox;
import l1j.server.server.serverpackets.S_SystemMessage;

public class L1Reload implements L1CommandExecutor {
	@SuppressWarnings("unused")
	private static Logger _log = Logger.getLogger(L1Reload.class.getName());

	private L1Reload() {
	}

	public static L1CommandExecutor getInstance() {
		return new L1Reload();
	}

	@Override
	public void execute(L1PcInstance gm, String cmdName, String arg) {
		if (arg.equalsIgnoreCase("���")) {
			DropTable.reload();
			gm.sendPackets(new S_SystemMessage("DropTable Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("���������")) {
			DropItemTable.reload();
			gm.sendPackets(new S_SystemMessage("DropItemTable Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("����")) {
			PolyTable.reload();
			gm.sendPackets(new S_SystemMessage("PolyTable Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("������")) {
			ResolventTable.reload();
			gm.sendPackets(new S_SystemMessage("ResolventTable Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("�ڽ�")) {
			L1TreasureBox.load();
			gm.sendPackets(new S_SystemMessage("TreasureBox Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("��ų")) {
			SkillsTable.reload();
			gm.sendPackets(new S_SystemMessage("Skills Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("����ų")) {
			MobSkillTable.reload();
			gm.sendPackets(new S_SystemMessage("Skills Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("��")) {
			MapFixKeyTable.reload();
			gm.sendPackets(new S_SystemMessage("Map Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("������")) {
			ItemTable.reload();
			gm.sendPackets(new S_SystemMessage("ItemTable Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("����")) {
			ShopTable.reload();
			gm.sendPackets(new S_SystemMessage("Shop Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("��������")) {
			NoticeTable.reload();
			gm.sendPackets(new S_SystemMessage("NoticeTable Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("���Ǿ�")) {
			NpcTable.reload();
			gm.sendPackets(new S_SystemMessage("NpcTable Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("���Ǳ�")) {
			Config.load();
			gm.sendPackets(new S_SystemMessage("Config Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("������")) {
			try {
				SpawnTable.reload();
				gm.sendPackets(new S_SystemMessage("Spawn Delay Update Complete..."), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (arg.equalsIgnoreCase("�������")){
			AutoLoot.getInstance().reload();
			gm.sendPackets(new S_SystemMessage("AutoLoot Chat Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("���Ի���ä��")) {
			NpcBuyShopSpawn.getInstance().ShopNameReload();
			gm.sendPackets(new S_SystemMessage("NpcBuyShopSpawn Chat Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("���Ի���")) {
			NpcBuyShopSpawn.getInstance().ShopNameReload();
			NpcBuyShop.getInstance().reload();
			NpcBuyShop.getInstance().relodingac();
			gm.sendPackets(new S_SystemMessage("NpcBuyShop Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("���ڻ���")) {
			NpcShopTable.reloding();
			NpcShopSpawnTable.getInstance().shopNameReload();
			NpcShopTable.getInstance().relodingac();
			gm.sendPackets(new S_SystemMessage("NpcShopTable Update Complete..."), true);
		} else if (arg.equalsIgnoreCase("�տ�")) {
			CrockSystem.getInstance().reload();
			gm.sendPackets(new S_SystemMessage("CrockSystem Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("�ǸŻ���")) {
			NpcTradeShop.getInstance().reload();
			gm.sendPackets(new S_SystemMessage("NpcTradeShop Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("���̵������")) {
			DragonRaidItemTable.reload();
			gm.sendPackets(new S_SystemMessage("DragonRaidItemTable Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("���Ǿ��׼�")) {
			NPCTalkDataTable.reload();
			gm.sendPackets(new S_SystemMessage("NPCTalkDataTable Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("��������")){
			WeaponAddDamage.reload();
			gm.sendPackets(new S_SystemMessage("���� ������� �ֽ�ȭ �Ǿ����ϴ�."));
		} else if (arg.equalsIgnoreCase("�Ƶ�����")) {
			AdenShopTable.reload();
			gm.sendPackets(new S_SystemMessage("AdenShopTable Reload Complete..."), true);
		} else if (arg.equalsIgnoreCase("������üũ")) {
			IpCheckTable.reload();
			gm.sendPackets(new S_SystemMessage("IpCheckTable Reload Complete..."), true);
		} 		 else if (arg.equalsIgnoreCase("��æƮ")) {
			ItemEnchantList.reload();
			gm.sendPackets(new S_SystemMessage("ItemEnchantList Update Complete..."));
		}
			
			else
			
			
			
			if (arg.equalsIgnoreCase("�׼�����")) {
			NpcActionTable.reload();
			gm.sendPackets(new S_SystemMessage("NpcActionTable Reload Complete..."), true);
		} else 
		{
			gm.sendPackets(new S_SystemMessage("[���,���������,����,������,�ڽ�,��ų]"));
			gm.sendPackets(new S_SystemMessage("[����ų,��,������,����,��������,���Ǿ�]"));
			gm.sendPackets(new S_SystemMessage("[���Ǳ�,�������,���Ի���ä��,�׼�����]"));
			gm.sendPackets(new S_SystemMessage("[���Ի���,���ڻ���,�տ�,�ǸŻ���,������]"));
			gm.sendPackets(new S_SystemMessage("[���̵������,���Ǿ��׼�,��������]"));
			gm.sendPackets(new S_SystemMessage("[�Ƶ�����,������üũ,��æƮ(��������æ����Ʈ)]"));
		}
	}
}
