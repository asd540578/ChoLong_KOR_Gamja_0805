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

public class 돌아온아놀드이벤트 extends Thread {
 
private static 돌아온아놀드이벤트 _instance;

private boolean _돌아온아놀드이벤트;
  
public boolean get돌아온아놀드이벤트() {
	return _돌아온아놀드이벤트;
	}
  
public void set돌아온아놀드이벤트(boolean 돌아온아놀드이벤트) {
	_돌아온아놀드이벤트 = 돌아온아놀드이벤트;
	}
  
  public boolean isGmOpen4 = false;
  
  public static 돌아온아놀드이벤트 getInstance() {
	  if(_instance == null) {
		  _instance = new 돌아온아놀드이벤트();
	  }
	  return _instance;
  }
  
  @Override
  public void run() {
	  try {
		  while (true) {
			  Thread.sleep(1000); 
			  /** 오픈 **/
			  if(!isGmOpen4)
				  continue;
			  if(L1World.getInstance().getAllPlayers().size() <= 0)
				  continue;
			  
			  isGmOpen4 = false;
			  
			  Config.아놀드이벤트 = true;
			  L1SpawnUtil.spawn2(33433, 32798, (short) 4, 6, 0, (Config.아놀드이벤트시간 * 3600000), 0);//텔레포터 
			  L1SpawnUtil.spawn2(33431, 32798, (short) 4, 7, 0, (Config.아놀드이벤트시간 * 3600000), 0);//텔레포터 
			  L1SpawnUtil.spawn2(33429, 32798, (short) 4, 8, 0, (Config.아놀드이벤트시간 * 3600000), 0);//텔레포터 
			  L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,"운영자님께서 아놀드 이벤트를 오픈합니다."));
			  L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,"기란여관앞 엔피씨를 이용하시기 바랍니다."));
			  L1World.getInstance().broadcastPacketToAll(new S_PacketBox(S_PacketBox.GREEN_MESSAGE,"아놀드 이벤트는 " + Config.아놀드이벤트시간 + "시간동안 유지됩니다."));
			  
			  set돌아온아놀드이벤트(true);
			  
			  Thread.sleep(Config.아놀드이벤트시간*3600000); //60분정도
			  /** 종료 **/
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

  public synchronized static void 아놀드이벤트삭제() {
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
	
	/** 종료 **/
  public void End() {
	  Config.아놀드이벤트 = false;
	  delenpc(6);
	  delenpc(7);
	  delenpc(8);
	  아놀드이벤트삭제();
	  L1World.getInstance().broadcastServerMessage("\\fS아놀드 이벤트가 종료되었습니다.");
	  set돌아온아놀드이벤트(false);
  }
  
}
	