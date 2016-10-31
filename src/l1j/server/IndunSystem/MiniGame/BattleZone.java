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
package l1j.server.IndunSystem.MiniGame;

import java.util.ArrayList;
import java.util.Random;

import l1j.server.Config;
import l1j.server.server.Announcements;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.gametime.RealTimeClock;
import l1j.server.server.serverpackets.S_HPMeter;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_SystemMessage;

public class BattleZone implements Runnable {
	protected final Random _random = new Random();

	private static BattleZone _instance;
	
	int ����2 = 3*3600;
	int ����5 = 6*3600;
	int ����8 = 9*3600;
	int ����11 = 12*3600;
	int ����14 = 15*3600;
	int ����17 = 18*3600;
	int ����20 = 21*3600;
	int ����23 = 24*3600;

	//��� ���ۿ���
	private boolean _DuelStart;

	public boolean getDuelStart() {
		return _DuelStart;
	}

	public void setDuelStart(boolean duel) {
		_DuelStart = duel;
	}

	//��� ���忩��
	private boolean _DuelOpen;

	public boolean getDuelOpen() {
		return _DuelOpen;
	}

	public void setDuelOpen(boolean duel) {
		_DuelOpen = duel;
	}
	//��� ���ۿ���
	private boolean _����;

	public boolean ��Ʋ������() {
		return _����;
	}

	public void set��Ʋ������(boolean flag) {
		_���� = flag;
	}


	private boolean _����;

	public boolean ��Ʋ������() {
		return _����;
	}

	public void set��Ʋ������(boolean flag) {
		_���� = flag;
	}
	//public int DuelCount;

	private int enddueltime;

	private boolean Close;

	protected ArrayList<L1PcInstance> ��Ʋ������ = new ArrayList<L1PcInstance>();
	public void add��Ʋ������(L1PcInstance pc) 	{
		��Ʋ������.add(pc);
	}
	public void remove��Ʋ������(L1PcInstance pc) 	{
		��Ʋ������.remove(pc); 
	}
	public void clear��Ʋ������() 					{ 
		��Ʋ������.clear();	  
	}
	public boolean is��Ʋ������(L1PcInstance pc) 	{ 
		return ��Ʋ������.contains(pc); 	
	} 
	public int get��Ʋ������Count(){ 
		return ��Ʋ������.size();	
	}
	
	private boolean GmStart = false;
	public void setGmStart(boolean ck){	GmStart = ck; }
	public boolean getGmStart(){	return GmStart;	}
	

	public L1PcInstance[] toArray��Ʋ������() {
		return ��Ʋ������.toArray(new L1PcInstance[��Ʋ������.size()]);
	}
	public static BattleZone getInstance() {
		if (_instance == null) {
			_instance = new BattleZone();
		}
		return _instance;
	}


	@Override
	public void run() {
		try {
			while (true) {
				try{
					if(��Ʋ������()== true){
						Thread.sleep(1000*60*60*2); //2�ð� ���ð�
						set��Ʋ������(false);
					}else{
						checkDuelTime(); // ��� ���ɽð��� üũ
						if (��Ʋ������() == true)	{
							����üũ();
						}
						Thread.sleep(1000);
					}
				}catch (Exception e) {}
			}
		} catch (Exception e1) {
		}
	}

	private void ����üũ() {
		L1PcInstance[] pc = toArray��Ʋ������();
		for (int i = 0; i < pc.length; i++) {
			if (pc[i] == null)
				continue;

			if (pc[i].getMapId() == 5001 || pc[i].getMapId() == 5153) {
				continue;
			} else {
				if (is��Ʋ������(pc[i])) {
					remove��Ʋ������(pc[i]);
				}
				pc[i].set_DuelLine(0);
			}
		}
	}

	//���ð�üũ
	public void checkDuelTime() {
		//���ӽð��� �޾ƿ´�.
		try{
			int servertime = RealTimeClock.getInstance().getRealTime().getSeconds();
			//����ð�
			int nowdueltime = servertime % 86400;
			int count1 = 0;
			int count2 = 0;
			int winLine = 4;
			if (getDuelStart() == false){
				if (  	nowdueltime >= ����2-31 && nowdueltime <= ����2+31 // /2��
						|| nowdueltime >= ����5-31 && nowdueltime <= ����5+31 ///5��
						|| nowdueltime >= ����8-31 && nowdueltime <= ����8+31 ////8��
						|| nowdueltime >= ����11-31 && nowdueltime <= ����11+31 //11��
						|| nowdueltime >= ����14-31 && nowdueltime <= ����14+31//14��
						|| nowdueltime >= ����17-31 && nowdueltime <= ����17+31//17��
						|| nowdueltime >= ����20-31 && nowdueltime <= ����20+31//20��
						|| nowdueltime >= ����23-31 && nowdueltime <= ����23+31//23��
						|| getGmStart())
				{
					setDuelOpen(true);
					setDuelStart(true);
					����3�д��();
				}
				if (��Ʋ������() == true)	{
					L1PcInstance[] c = toArray��Ʋ������();
					for (int i = 0; i < c.length; i++) {
						if(c[i].getMapId() == 5001){
							if(!c[i].isDead()){
								��Ʋ������(c[i]);
							}
						}
					}
					setDuelStart(true);
					//������ �ð�����
					enddueltime = nowdueltime + 600; //10������������ð� ���ϴ°�

				}
			}else{
				//����ð��̰ų� ����������
				if(nowdueltime >= enddueltime || Close == true){
					L1PcInstance[] c1 = toArray��Ʋ������();
					for (int i = 0; i < c1.length; i++) {
						if(c1[i].getMapId() == 5153){
							if(!c1[i].isDead()){
								if(c1[i].get_DuelLine() == 1){
									count1 += 1;
								}else{
									count2 += 1;
								}
							}
						}
					}
					//���üũ
					String ment = null;
					if(count1 > count2){
						//1������ ���
						winLine = 1;
						ment = "�����̾� ��Ʋ�� '��ũ' ������ �¸��Դϴ�.";
						L1World.getInstance().broadcastServerMessage("\\fW* ��Ʋ�� ����! '��ũ' ������ �¸��Դϴ� *");
					}else if(count1 < count2){
						//2������ ���
						winLine = 2;
						ment = "�����̾� ��Ʋ�� '�ǹ�' ������ �¸��Դϴ�.";
						L1World.getInstance().broadcastServerMessage("\\fW* ��Ʋ�� ����! '�ǹ�' ������ �¸��Դϴ� *");
					}else{
						winLine = 3;
						ment = "�����̾� ��Ʋ�� '��ũ' ���ΰ� '�ǹ�' ������ �����ϴ�.";
						L1World.getInstance().broadcastServerMessage("\\fW* ��Ʋ�� ����! '��ũ' ���ΰ� '�ǹ�'������ �����Դϴ� *");
					}

					L1PcInstance[] c2 = toArray��Ʋ������();
					for (int i = 0; i < c2.length; i++) {  
						if(c2[i] == null) continue;
						if(c2[i].get_DuelLine() != 0){
							c2[i].sendPackets(new S_SystemMessage(ment));//��Ʈ����
							//�̱� ���ο��� ����������
							 if(c2[i].get_DuelLine() == winLine){
						    	 String[] itemIds = null;
							 		try{
							 			int idx = Config.��Ʋ��������.indexOf(",");
							 			// ,�� �������
							 			if(idx > -1){
							 				itemIds = Config.��Ʋ��������.split(",");
							 			}else{
							 				itemIds = new String[1];
							 				itemIds[0] = Config.��Ʋ��������;
							 			}
							 		}catch(Exception e){}
							 		// ������ ������ ����
							 		String[] counts = null;
							 		try{
							 			int idx = Config.��Ʋ�������۰���.indexOf(",");
							 			// ,�� �������
							 			if(idx > -1){
							 				counts = Config.��Ʋ�������۰���.split(",");
							 			}else{
							 				counts = new String[1];
							 				counts[0] = Config.��Ʋ�������۰���;
							 			}
							 		}catch(Exception e){}
							 		// ������ ���̵� ī��Ʈ�� �������
							 		if (itemIds == null || counts == null)
							 			return;
							 		for (int j = 0; j < itemIds.length; j++) {
							 			int itemId = 0;
							 			int count = 0;
							 			itemId = Integer.parseInt(itemIds[j]);
							 			count = Integer.parseInt(counts[j]);
							 			if (itemId <= 0 || count <= 0)
							 				continue;
							 			L1ItemInstance item = c2[i].getInventory().storeItem(itemId, count);
							 			if (item != null)
							 				c2[i].sendPackets(new S_SystemMessage(item.getName() + " (" + count + ")�� ������ϴ�."));
							 		}
							      c2[i].sendPackets(new S_SystemMessage("\\fU* �¸������� �������� ���޵Ǿ����ϴ� *"));
							     }
							
							

							deleteMiniHp(c2[i]);
							c2[i].set_DuelLine(0);
							//��Ʋ���̶��
							if(c2[i].getMapId() == 5153 || c2[i].getMapId() == 5001){
								if(!c2[i].isDead()){
									L1Teleport.teleport(c2[i], 33090, 33402, (short) 4, 0, true);// 
								}
							}
						}
					}
					ment = null;
					Announcements.getInstance().announceToAll("\\fW* �����̾� ��Ʋ���� ����Ǿ����ϴ� *");
					Announcements.getInstance().announceToAll("\\fW* ��Ʋ���� 3�ð� �������� �����ϴ� *");
					set��Ʋ������(true);
					set��Ʋ������(false);
					setDuelStart(false);
					//	DuelCount = 0;
					Close = false;
					��Ʋ������.clear();
					setGmStart(false);
				}else{
					//������ �����Ǿ��ٸ�
					if(!getDuelOpen()){
						int count3 = 0;
						int count4 = 0;
						L1PcInstance[] c3 = toArray��Ʋ������();
						for (int i = 0; i < c3.length; i++) {
							if(c3[i] == null) continue;
							//��Ʋ���̶��
							if(c3[i].getMapId() == 5153){
								if(!c3[i].isDead()){//�������� ���� üũ
									if(c3[i].get_DuelLine() == 1){
										count3 += 1;
									}else if(c3[i].get_DuelLine() == 2){
										count4 += 1;
									}else{
										remove��Ʋ������(c3[i]);
									}
								}
							}
						}

						//���������� 0���϶� �����������<<
						if(count3 == 0 || count4 == 0){
							Close = true;
						}
					}

				}

			}
		}catch(Exception e){}
	}

	private void createMiniHp(L1PcInstance pc) {
		// ��Ʋ��, ���� HP�� ǥ�ý�Ų��
		for (L1PcInstance member : BattleZone.getInstance().toArray��Ʋ������()) {
			// �������ο��� hpǥ��
			if (member != null) {
				if (pc.get_DuelLine() == member.get_DuelLine()) {
					member.sendPackets(new S_HPMeter(pc));
					pc.sendPackets(new S_HPMeter(member));
				}
			}
		}
	}

	////��Ʋ�� ����////////
	private void ��Ʋ������(L1PcInstance pc) {
		if (pc == null)
			return;
		int DuelLine = pc.get_DuelLine();
		int polyid = 0;
		int time = 1800;
		if (pc != null) {
			if (pc.isKnight() || pc.isCrown() || pc.isDarkelf() || pc.isDragonknight() || pc.isWarrior()) {
				// ��� ���� ��ũ���� ���� ����
				if (DuelLine == 1) {
					polyid = 13152;// <<1������ ���Ŵ�ũ>
				} else {
					polyid = 13153;// 2������ ��ũ����
				}
				L1PolyMorph.doPoly(pc, polyid, time, 2);
			}
			// ���� ȯ����
			if (pc.isWizard() || pc.isIllusionist()) {
				if (DuelLine == 1) {
					polyid = 13152;
				} else {
					polyid = 13153;
				}
				L1PolyMorph.doPoly(pc, polyid, time, 2);
			}
			// ����
			if (pc.isElf()) {
				if (DuelLine == 1) {
					polyid = 13635;
				} else {
					polyid = 13631;
				}
				L1PolyMorph.doPoly(pc, polyid, time, 2);
			}
		}
	}
	
	
	private void ��Ʋ������(L1PcInstance pc) {
		try {
			��Ʋ������(pc);
			createMiniHp(pc);
			if (pc.get_DuelLine() == 1) {
				int ranx = 32628 + _random.nextInt(4);
				int rany = 32896 + _random.nextInt(5);
				L1Teleport.teleport(pc, ranx, rany, (short) 5153, 1, true);
			} else {
				int ranx2 = 32650 - _random.nextInt(4);
				int rany2 = 32893 + _random.nextInt(5);
				L1Teleport.teleport(pc, ranx2, rany2, (short) 5153, 5, true);
			}
			
			set��Ʋ������(false);
		} catch (Exception e) {
		}
	}
	
	
	public void ����3�д��() {
		try {
			L1World.getInstance().broadcastPacketToAll(
					new S_PacketBox(S_PacketBox.GREEN_MESSAGE,
							"��Ʋ���� ���Ƚ��ϴ�. ������ð��� 3���Դϴ�."), true);
		
			Announcements.getInstance().announceToAll("3�� �� ��ü�� �����̾� ��Ʋ���� �����մϴ�.");
			Announcements.getInstance().announceToAll("������ ���������� ����������� �Ͻ� �� �ֽ��ϴ�.");
			Announcements.getInstance().announceToAll("���� ������ ������ �ϸ� ������ �Ұ����մϴ�.");
			try {
				Thread.sleep(1000 * 120);
			} catch (Exception e) {
			}
			Announcements.getInstance().announceToAll("1�� �� �����̾� ��Ʋ�� ������ �����մϴ�.");
			Announcements.getInstance().announceToAll("��� '�����̾���Ʋ��'�� ���� ������ �����մϴ�.");
			try {
				Thread.sleep(1000 * 50);
			} catch (Exception e) {
			}
			Announcements.getInstance().announceToAll("�����̾� ��Ʋ�� ���� ���� 10�� ���ҽ��ϴ�.");
			try {
				Thread.sleep(1000 * 10);
			} catch (Exception e) {
			}
			if (getDuelOpen()) {
				setDuelOpen(false);
			}
			Announcements.getInstance().announceToAll("�����̾� ��Ʋ�� ������ �����Ͽ����ϴ�.");
			try {
				Thread.sleep(1000 * 5);
			} catch (Exception e) {
			}
			set��Ʋ������(true);
			setGmStart(true);
		} catch (Exception e) {
		}
	}
	
	private void deleteMiniHp(L1PcInstance pc) {
		// ��Ʋ�����, HP�ٸ� �����Ѵ�.
		for (L1PcInstance member : pc.getKnownPlayers()){
			//�������ο��� hpǥ��
			if(member != null){
				if(pc.get_DuelLine() == member.get_DuelLine()){
					pc.sendPackets(new S_HPMeter(member.getId(), 0xff, 0xff));
					member.sendPackets(new S_HPMeter(pc.getId(), 0xff, 0xff));
				}
			}
		}
	}


}
