package l1j.server.GameSystem.EventShop;

import java.sql.Connection;
import java.sql.PreparedStatement;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.Warehouse.ClanWarehouse;
import l1j.server.Warehouse.PrivateWarehouse;
import l1j.server.Warehouse.WarehouseManager;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.utils.L1SpawnUtil;
import l1j.server.server.utils.SQLUtil;

public class ���ƿ¾Ƴ���̺�Ʈ extends Thread {
 
private static ���ƿ¾Ƴ���̺�Ʈ _instance;

private boolean _���ƿ¾Ƴ���̺�Ʈ;
  
public boolean get���ƿ¾Ƴ���̺�Ʈ() {
	return _���ƿ¾Ƴ���̺�Ʈ;
	}
  
public void set���ƿ¾Ƴ���̺�Ʈ(boolean ���ƿ¾Ƴ���̺�Ʈ) {
	_���ƿ¾Ƴ���̺�Ʈ = ���ƿ¾Ƴ���̺�Ʈ;
	}
  
  public boolean isGmOpen4 = false;
  
  public static ���ƿ¾Ƴ���̺�Ʈ getInstance() {
	  if(_instance == null) {
		  _instance = new ���ƿ¾Ƴ���̺�Ʈ();
	  }
	  return _instance;
  }
  
  @Override
  public void run() {
	  try {
		  while (true) {
			  Thread.sleep(1000); 
			  /** ���� **/
			  if(!isGmOpen4)
				  continue;
			  if(L1World.getInstance().getAllPlayers().size() <= 0)
				  continue;
			  
			  isGmOpen4 = false;
			  
			  Config.�Ƴ���̺�Ʈ = true;
			  L1SpawnUtil.spawn2(33433, 32798, (short) 4, 6, 0, (Config.�Ƴ���̺�Ʈ�ð� * 3600000), 0);//�ڷ����� 
			  L1SpawnUtil.spawn2(33431, 32798, (short) 4, 7, 0, (Config.�Ƴ���̺�Ʈ�ð� * 3600000), 0);//�ڷ����� 
			  L1SpawnUtil.spawn2(33429, 32798, (short) 4, 8, 0, (Config.�Ƴ���̺�Ʈ�ð� * 3600000), 0);//�ڷ����� 
			  L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,"��ڴԲ��� �Ƴ�� �̺�Ʈ�� �����մϴ�."));
			  L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,"��������� ���Ǿ��� �̿��Ͻñ� �ٶ��ϴ�."));
			  L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,"�Ƴ�� �̺�Ʈ�� " + Config.�Ƴ���̺�Ʈ�ð� + "�ð����� �����˴ϴ�."));
			  
			  set���ƿ¾Ƴ���̺�Ʈ(true);
			  
			  Thread.sleep(Config.�Ƴ���̺�Ʈ�ð�*3600000); //60������
			  /** ���� **/
			  End();
		  }
		  
	  } catch(Exception e){
		  e.printStackTrace();
	  }
  }
  
  private static void delenpc(int npcid) {
	  L1NpcInstance npc = null;
	  for (L1Object object : L1World.getInstance().getObject()) {
		  if (object instanceof L1NpcInstance) {
			  npc = (L1NpcInstance) object;
			  if (npc.getNpcTemplate().get_npcId() == npcid) {
				  npc.deleteMe();
				  npc = null;
			  }
		  }
	  }
  }
  
  private static int delItemlist[] = { 307, 308, 309, 310, 311, 312, 313, 314, 21095, 
		  30146, 30147, 30150};

  public synchronized static void �Ƴ���̺�Ʈ����() {
	  try {
		  if (delItemlist.length <= 0)
			  return;
				
		  for (L1PcInstance tempPc : L1World.getInstance().getAllPlayers()) {
			  if (tempPc == null)
				  continue;
			  for (int i = 0; i < delItemlist.length; i++) {
				  L1ItemInstance[] item = tempPc.getInventory().findItemsId(delItemlist[i]);
				  if (item != null && item.length > 0) {
					  for (int o = 0; o < item.length; o++) {
						  tempPc.getInventory().removeItem(item[o]);
					  }
				  }
				  try {
					  PrivateWarehouse pw = WarehouseManager.getInstance().getPrivateWarehouse(tempPc.getAccountName());
					  L1ItemInstance[] item2 = pw.findItemsId(delItemlist[i]);
					  if (item2 != null && item2.length > 0) {
						  for (int o = 0; o < item2.length; o++) {
							  pw.removeItem(item2[o]);
						  }
					  }
				  } catch (Exception e) {}
				  try {
					  if (tempPc.getClanid() > 0) {
						  ClanWarehouse cw = WarehouseManager.getInstance().getClanWarehouse(tempPc.getClanname());
						  L1ItemInstance[] item3 = cw.findItemsId(delItemlist[i]);
						  if (item3 != null && item3.length > 0) {
							  for (int o = 0; o < item3.length; o++) {
								  cw.removeItem(item3[o]);
							  }
						  }
					  }
				  } catch (Exception e) {}
				  try {
					  if (tempPc.getPetList().size() > 0) {
						  for (L1NpcInstance npc : tempPc.getPetList()) {
							  L1ItemInstance[] pitem = npc.getInventory().findItemsId(delItemlist[i]);
							  if (pitem != null && pitem.length > 0) {
								  for (int o = 0; o < pitem.length; o++) {
									  npc.getInventory().removeItem(pitem[o]);
								  }
							  }
						  }
					  }
				  } catch (Exception e) {}
			  }
		  }
		  try {
			  for (L1Object obj : L1World.getInstance().getAllItem()) {
				  if (!(obj instanceof L1ItemInstance))
					  continue;
				  L1ItemInstance temp_item = (L1ItemInstance) obj;
				  if (temp_item.getItemOwner() == null) {
					  if (temp_item.getX() == 0 && temp_item.getY() == 0)
						  continue;
				  }
				  for (int ii = 0; ii < delItemlist.length; ii++) {
					  if (delItemlist[ii] == temp_item.getItemId()) {
						  L1Inventory groundInventory = L1World.getInstance().getInventory(temp_item.getX(),temp_item.getY(),temp_item.getMapId());
						  groundInventory.removeItem(temp_item);
						  break;
					  }
				  }
			  }
		  } catch (Exception e) {}
		  StringBuilder sb = new StringBuilder();
		  for (int i = 0; i < delItemlist.length; i++) {
			  sb.append(+delItemlist[i]);
			  if (i < delItemlist.length - 1) {
				  sb.append(",");
			  }
		  }
		  Delete(sb.toString());
	  } catch (Exception e) {
	  }
  }
	
  private static void Delete(String id_name) {
	  Connection con = null;
	  PreparedStatement pstm = null;
	  try {
		  con = L1DatabaseFactory.getInstance().getConnection();
		  pstm = con.prepareStatement("delete FROM _cha_inv_items WHERE item_id IN (" + id_name + ")");
		  pstm.executeUpdate();
	  } catch (Exception e) {
		  e.printStackTrace();
	  } finally {
		  SQLUtil.close(pstm);
	  }
	  try {
		  pstm = con.prepareStatement("delete FROM character_warehouse WHERE item_id in (" + id_name + ")");
		  pstm.executeUpdate();
	  } catch (Exception e) {
		  e.printStackTrace();
	  } finally {
		  SQLUtil.close(pstm);
	  }
	  try {
		  pstm = con.prepareStatement("delete FROM clan_warehouse WHERE item_id in (" + id_name + ")");
		  pstm.executeUpdate();
	  } catch (Exception e) {
		  e.printStackTrace();
	  } finally {
		  SQLUtil.close(pstm);
		  SQLUtil.close(con);
	  }
  }
	
	/** ���� **/
  public void End() {
	  Config.�Ƴ���̺�Ʈ = false;
	  delenpc(6);
	  delenpc(7);
	  delenpc(8);
	  �Ƴ���̺�Ʈ����();
	  L1World.getInstance().broadcastServerMessage("\\fS�Ƴ�� �̺�Ʈ�� ����Ǿ����ϴ�.");
	  set���ƿ¾Ƴ���̺�Ʈ(false);
  }
  
}
	