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

package l1j.server.server.model.item.function;

import l1j.server.Config;
import l1j.server.server.clientpackets.ClientBasePacket;
//import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_ItemName;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.templates.L1Item;
import l1j.server.server.templates.L1Npc;

@SuppressWarnings("serial")
public class MagicDoll extends L1ItemInstance {

	public MagicDoll(L1Item item) {
		super(item);
	}

	@Override
	public void clickItem(L1Character cha, ClientBasePacket packet) {
		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			int itemId = this.getItemId();
			useMagicDoll(pc, itemId, this.getId());
		}
	}

	private void useMagicDoll(L1PcInstance pc, int itemId, int itemObjectId) {
		if (pc.isInvisble()) {
			return;
		}
		boolean isAppear = true;

		L1DollInstance doll = null;
		for (Object dollObject : pc.getDollList()) {
			doll = (L1DollInstance) dollObject;
			if (doll.getItemObjId() == itemObjectId) { // �̹� ������ �ִ� ���� �Ǿ� ����
				isAppear = false;
				break;
			}
		}

		if (isAppear) {

			int npcId = 0;
			int dollType = 0;
			int consumecount = 0;
			int dollTime = 0;

			/*
			 * int castle_id = L1CastleLocation.getCastleIdByArea(pc);//�߰� if
			 * (castle_id != 0){ // ���� ���������� ��� �Ұ� if(itemId == 41248 || itemId
			 * == 41249 || itemId == 41250 || itemId == 430000 || itemId ==
			 * 430001 || itemId == 430002 || itemId == 430003 || itemId ==
			 * 430004 || itemId == 430500 || itemId == 430505 || itemId ==
			 * 430506 || itemId == 430501 || itemId == 430502 || itemId ==
			 * 430503 || itemId == 41915 || itemId == 500144 || itemId == 500145
			 * || itemId == 500146 || itemId == 5000034 || itemId == 5000035 ||
			 * itemId == 5000036 || itemId == 430504){ pc.sendPackets(new
			 * S_SystemMessage("\\fY�������������� ��� �� �� �����ϴ�.")); return; } }
			 */

			switch (itemId) {
			case L1ItemId.DOLL_BUGBEAR:
				npcId = 80106;
				dollType = L1DollInstance.DOLLTYPE_BUGBEAR;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_SUCCUBUS:
				npcId = 80107;
				dollType = L1DollInstance.DOLLTYPE_SUCCUBUS;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_WAREWOLF:
				npcId = 80108;
				dollType = L1DollInstance.DOLLTYPE_WAREWOLF;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_STONEGOLEM:
				npcId = 4500150;
				dollType = L1DollInstance.DOLLTYPE_STONEGOLEM;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_ELDER:
				npcId = 4500151;
				dollType = L1DollInstance.DOLLTYPE_ELDER;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_CRUSTACEA:
				npcId = 4500152;
				dollType = L1DollInstance.DOLLTYPE_CRUSTACEA;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_SEADANCER:
				npcId = 4500153;
				dollType = L1DollInstance.DOLLTYPE_SEADANCER;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_SNOWMAN:
				npcId = 4500154;
				dollType = L1DollInstance.DOLLTYPE_SNOWMAN;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_COCATRIS:
				npcId = 4500155;
				dollType = L1DollInstance.DOLLTYPE_COCATRIS;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_DRAGON_M:
				npcId = 4500156;
				dollType = L1DollInstance.DOLLTYPE_DRAGON_M;
				consumecount = 50;
				dollTime = 18000;
				break;
			case L1ItemId.DOLL_DRAGON_W:
				npcId = 4500157;
				dollType = L1DollInstance.DOLLTYPE_DRAGON_W;
				consumecount = 50;
				dollTime = 18000;
				break;
			case L1ItemId.DOLL_HIGH_DRAGON_M:
				npcId = 4500158;
				dollType = L1DollInstance.DOLLTYPE_HIGH_DRAGON_M;
				consumecount = 50;
				dollTime = 18000;
				break;
			case L1ItemId.DOLL_HIGH_DRAGON_W:
				npcId = 4500159;
				dollType = L1DollInstance.DOLLTYPE_HIGH_DRAGON_W;
				consumecount = 50;
				dollTime = 18000;
				break;
			case L1ItemId.DOLL_LAMIA:
				npcId = 4500160;
				dollType = L1DollInstance.DOLLTYPE_LAMIA;
				consumecount = 50;
				dollTime = 1800;
				break;
			case L1ItemId.DOLL_SPATOI:
				npcId = 4500161;
				dollType = L1DollInstance.DOLLTYPE_SPATOI;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 500202: // 1,800�� ���� �ٰŸ� �����+2,���� ����+12,�ٰŸ� ����+2
				npcId = 1500202;
				dollType = L1DollInstance.DOLLTYPE_����Ŭ�ӽ�;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 500203: // 1,800�� ���� ����ġ ���ʽ�+10%, ����� ������+1
				npcId = 1500203;
				dollType = L1DollInstance.DOLLTYPE_���̾�Ʈ;
				consumecount = 50;
				dollTime = 1800;
				break;

			case 500204: // 1,800�� ���� 64�ʸ��� MP ȸ�� +15, ���� �� ���� Ȯ���� �� ����Ʈ�� �ߵ�
				npcId = 1500204;
				dollType = L1DollInstance.DOLLTYPE_�����;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 500205: // 1,800�� ���� 64�ʸ��� MP 15 ȸ��, SP+1
				npcId = 1500205;
				dollType = L1DollInstance.DOLLTYPE_��ť��;
				consumecount = 50;
				dollTime = 1800;
				break;

			case 141919:// ��̾�
				npcId = 4500160;
				dollType = L1DollInstance.DOLLTYPE_LAMIA;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 141920:// ��������
				npcId = 4500161;
				dollType = L1DollInstance.DOLLTYPE_SPATOI;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 141922:// ��ƾ
				npcId = 45000161;
				dollType = L1DollInstance.DOLLTYPE_��ƾ;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 141921:// ����ƺ�
				npcId = 41915;
				dollType = L1DollInstance.DOLLTYPE_HUSUABI;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 141918:// �ô�
				npcId = 4500153;
				dollType = L1DollInstance.DOLLTYPE_SEADANCER;
				consumecount = 50;
				dollTime = 1800;
				break;

			case 500108:// �ξ�
				npcId = 1500108;
				dollType = L1DollInstance.DOLLTYPE_�ξ�;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 500109:// �����
				npcId = 1500110;
				dollType = L1DollInstance.DOLLTYPE_�����;
				consumecount = 50;
				dollTime = 1800;
				break;

			case 500110:// ŷ����
				npcId = 1500109;
				dollType = L1DollInstance.DOLLTYPE_ŷ_���׺���;
				consumecount = 50;
				dollTime = 1800;
				break;

			case 600234:// ŷ����
				npcId = 1600234;
				dollType = L1DollInstance.DOLLTYPE_�̺�Ʈ����;
				consumecount = 50;
				dollTime = 1800;
				break;

			case 600241:// ��
				npcId = 1600241;
				dollType = L1DollInstance.DOLLTYPE_��;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 600242:// ���
				npcId = 1600242;
				dollType = L1DollInstance.DOLLTYPE_��ٰ�;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 600243:// �ٰ�
				npcId = 1600243;
				dollType = L1DollInstance.DOLLTYPE_���̾Ƹ���;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 600244:// �þ�
				npcId = 1600244;
				dollType = L1DollInstance.DOLLTYPE_�þ�;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 600245:// ����
				npcId = 1600245;
				dollType = L1DollInstance.DOLLTYPE_����Ʈ�ߵ�;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 600246:// ����
				npcId = 1600246;
				dollType = L1DollInstance.DOLLTYPE_����;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("����ġ +10%, ���� ����+10, ���� ����+12, ü��+300, �ٰŸ� ����� +4"));
				break;
			case 600247:// ����
				npcId = 1600247;
				dollType = L1DollInstance.DOLLTYPE_��������Ʈ;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("����ġ +20%, ����+5,�ߵ� : �����̾�, ����+100, ���Ÿ� �����,���� +3"));
				break;

			case L1ItemId.DOLL_GremRin:
				npcId = 100882;
				dollType = L1DollInstance.DOLLTYPE_�׷���;
				consumecount = 50;
				dollTime = 1800;
				break;

			case L1ItemId.DOLL_ETIN:
				npcId = 45000161;
				dollType = L1DollInstance.DOLLTYPE_��ƾ;
				consumecount = 50;
				dollTime = 1800;
				break;

			case L1ItemId.DOLL_RICH:
				npcId = 45000162;
				dollType = L1DollInstance.DOLLTYPE_RICH;
				consumecount = 50;
				dollTime = 1800;
				break;

			case L1ItemId.DOLL_PHENIX:
				npcId = 45000163;
				dollType = L1DollInstance.DOLLTYPE_ETIN;
				consumecount = 50;
				dollTime = 1800;
				break;

			case 500144: // �����(A)
				npcId = 700196;
				dollType = L1DollInstance.DOLLTYPE_SNOWMAN_A;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 500145: // �����(B)
				npcId = 700197;
				dollType = L1DollInstance.DOLLTYPE_SNOWMAN_B;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 500146: // �����(C)
				npcId = 700198;
				dollType = L1DollInstance.DOLLTYPE_SNOWMAN_C;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 41915:
				npcId = 41915;
				dollType = L1DollInstance.DOLLTYPE_HUSUABI;
				consumecount = 50;
				dollTime = 1800;
				break;

			case 141915:
				npcId = 141915;
				dollType = L1DollInstance.DOLLTYPE_HW_HUSUABI;
				consumecount = 50;
				dollTime = 1800;
				break;

			case 141916:
				npcId = 101033;
				dollType = L1DollInstance.DOLLTYPE_ưư�ѱ��;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 1419161:
				npcId = 1010331;
				dollType = L1DollInstance.DOLLTYPE_����Ǳ��;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("\\fS�����۵���� 2���� / ����ġ���ʽ� 30% ����"));
				break;
				
			case 142920:
				npcId = 101134;
				dollType = L1DollInstance.DOLLTYPE_���̸���;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("���� �����̾� �ܰ躰 �����+10, ����� ������+3"));
				break;
			case 142921:
				npcId = 101135;
				dollType = L1DollInstance.DOLLTYPE_�����̾�;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("Ÿ��ź �迭 ��� �ߵ� HP ���� 5% ����, �ٰŸ� �����,����+2"));
				break;
			case 142922:
				npcId = 101136;
				dollType = L1DollInstance.DOLLTYPE_�ٶ�ī;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("����ġ +10%, �ı�����+5, ���� ����+12, ü��+100, �ٰŸ� �����, ���� +5"));
				break;
			case 751:
				npcId = 101138;
				dollType = L1DollInstance.DOLLTYPE_�ӹ̷ε�;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("����ġ +10%, ����� ����+2, 64�ʸ��� MP 15ȸ��"));
				break;
			case 752:
				npcId = 101137;
				dollType = L1DollInstance.DOLLTYPE_Ÿ��;
				consumecount = 50;
				dollTime = 1800;
				pc.sendPackets(new S_SystemMessage("���� ����+10, SP+3, ���� ����+5"));
				break;
			case 437018:
				npcId = 4000009;
				dollType = L1DollInstance.DOLLTYPE_HELPER;
				consumecount = 50;
				dollTime = 300;
				break;
			case 60173:
				npcId = 100320;
				dollType = L1DollInstance.DOLLTYPE_����;
				consumecount = 10;
				dollTime = 1800;
				break;
			case 60174:
				npcId = 100321;
				dollType = L1DollInstance.DOLLTYPE_������;
				consumecount = 10;
				dollTime = 1800;
				break;
			case 60175:
				npcId = 100322;
				dollType = L1DollInstance.DOLLTYPE_������;
				consumecount = 10;
				dollTime = 1800;
				break;
			case 60176:
				npcId = 100323;
				dollType = L1DollInstance.DOLLTYPE_�׷���;
				consumecount = 10;
				dollTime = 1800;
				break;
			case 60261:
				npcId = 100431;
				dollType = L1DollInstance.DOLLTYPE_����;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60262:
				npcId = 100432;
				dollType = L1DollInstance.DOLLTYPE_����;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60263:
				npcId = 100433;
				dollType = L1DollInstance.DOLLTYPE_����;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60309:
				npcId = 100579;
				dollType = L1DollInstance.DOLLTYPE_�ܵ�;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60310:
				npcId = 100580;
				dollType = L1DollInstance.DOLLTYPE_�긮;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60324:
				npcId = 100604;
				dollType = L1DollInstance.DOLLTYPE_�巹��ũ;
				consumecount = 50;
				dollTime = 1800;
				break;
			case 60447:
				npcId = 100677;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60448:
				npcId = 100678;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60449:
				npcId = 100679;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60450:
				npcId = 100680;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60451:
				npcId = 100681;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60452:
				npcId = 100682;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60453:
				npcId = 100683;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60454:
				npcId = 100684;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60455:
				npcId = 100685;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60456:
				npcId = 100686;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60457:
				npcId = 100687;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60458:
				npcId = 100688;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60459:
				npcId = 100689;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			case 60460:
				npcId = 100690;
				dollType = L1DollInstance.DOLLTYPE_����_����;
				consumecount = 500;
				dollTime = 18000;
				break;
			}
			if (itemId >= 60173 && itemId <= 60176) {
				if (!pc.getInventory().checkItem(41159, consumecount)) {
					pc.sendPackets(new S_ServerMessage(337, "$5116"), true);
					return;
				}
			} else {
				if (!pc.getInventory().checkItem(41246, consumecount)) {
					pc.sendPackets(new S_ServerMessage(337, "$5240"), true);
					return;
				}
			}
			if (pc.getDollListSize() >= Config.MAX_DOLL_COUNT) {
			//	pc.sendPackets(new S_ServerMessage(319), true);
			//	return;
				
				//���� ���
				doll.deleteDoll();
				pc.sendPackets(new S_SkillIconGFX(56, 0), true);
				pc.sendPackets(new S_OwnCharStatus(pc), true);
				pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
				
				L1Npc template = NpcTable.getInstance().getTemplate(npcId);
				doll = new L1DollInstance(template, pc, dollType, itemObjectId,	dollTime * 1000);
				pc.sendPackets(new S_SkillSound(doll.getId(), 5935), true);
				Broadcaster.broadcastPacket(pc,new S_SkillSound(doll.getId(), 5935), true);
				pc.sendPackets(new S_SkillIconGFX(56, dollTime), true);
				pc.sendPackets(new S_OwnCharStatus(pc), true);
				pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
				pc.sendPackets(new S_ServerMessage(1143), true);
				pc.getInventory().consumeItem(41246, consumecount);
				return;
			}
			if (itemId == 437018 && pc.getLevel() > 70) {
				pc.sendPackets(new S_SystemMessage(
						"���� ���� ������ Lv70 ���� ����� �� �ֽ��ϴ�."), true);
				return;
			}
			// DollMent(pc,itemId);
			L1Npc template = NpcTable.getInstance().getTemplate(npcId);
			doll = new L1DollInstance(template, pc, dollType, itemObjectId,
					dollTime * 1000);
			pc.sendPackets(new S_SkillSound(doll.getId(), 5935), true);
			Broadcaster.broadcastPacket(pc,
					new S_SkillSound(doll.getId(), 5935), true);
			pc.sendPackets(new S_SkillIconGFX(56, dollTime), true);
			pc.sendPackets(new S_OwnCharStatus(pc), true);
			pc.sendPackets(
					new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
			pc.sendPackets(new S_ServerMessage(1143), true);
			if (itemId >= 60173 && itemId <= 60176)
				pc.getInventory().consumeItem(41159, consumecount);
			else
				pc.getInventory().consumeItem(41246, consumecount);
		} else {
			doll.deleteDoll();
			pc.sendPackets(new S_SkillIconGFX(56, 0), true);
			pc.sendPackets(new S_OwnCharStatus(pc), true);
			pc.sendPackets(
					new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
		}
		pc.sendPackets(new S_ItemName(this), true);
	}

	
	  /*private void DollMent(L1PcInstance pc, int itemObjectId){ switch
	  (itemObjectId) { case L1ItemId.DOLL_BUGBEAR: pc.sendPackets(new
	  S_SystemMessage("\\fS���������� ���� �������� 10% �÷��ݴϴ�."), true); break; 
	  case
	  L1ItemId.DOLL_SUCCUBUS: pc.sendPackets(new
	  S_SystemMessage("\\fS���������� ���� 1�п� MP�� 15�� ȸ���˴ϴ�."), true); break;
	  case
	  L1ItemId.DOLL_ELDER: pc.sendPackets(new
	  S_SystemMessage("\\fS���������� ���� Ȯ��������Ÿ 1�п� MP�� 18�� ȸ���˴ϴ�."), true); break;
	  case L1ItemId.DOLL_WAREWOLF: pc.sendPackets(new
	  S_SystemMessage("\\fS7%Ȯ���� �����߰�Ÿ�� +15 ȿ���� �ߵ��˴ϴ�."), true); break; 
	  case
	  L1ItemId.DOLL_CRUSTACEA: pc.sendPackets(new
	  S_SystemMessage("\\fS7%Ȯ���� �����߰�Ÿ�� +15 ȿ���� �ߵ��˴ϴ�."), true); break; 
	  case
	  L1ItemId.DOLL_����Ǳ��: pc.sendPackets(new
	  S_SystemMessage("\\fS�����۵���� 2���� / ����ġ���ʽ� 30% ����"), true); break; 
	  case
	  L1ItemId.DOLL_STONEGOLEM: pc.sendPackets(new
	  S_SystemMessage("\\fS10%Ȯ���� 15�� �������� �氨�˴ϴ�."), true); break; case
	  L1ItemId.DOLL_SEADANCER: pc.sendPackets(new
	  S_SystemMessage("\\fS1�п� HP�� 70�� ȸ���˴ϴ�."), true); break; case
	  L1ItemId.DOLL_SNOWMAN: pc.sendPackets(new
	  S_SystemMessage("\\fSAC -3, �������� +7 ȿ���� �����˴ϴ�."), true); break; case
	  L1ItemId.DOLL_COCATRIS: pc.sendPackets(new
	  S_SystemMessage("\\fSȰ ���� +1, Ȱ �߰�Ÿ�� +1 ȿ���� �ߵ��˴ϴ�."), true); break; case
	  L1ItemId.DOLL_DRAGON_M: case L1ItemId.DOLL_DRAGON_W: pc.sendPackets(new
	  S_SystemMessage("\\fS��ƽ +4 ���� ȿ����  �ߵ��˴ϴ�."), true); break; case
	  L1ItemId.DOLL_HIGH_DRAGON_M: case L1ItemId.DOLL_HIGH_DRAGON_W:
	  pc.sendPackets(new S_SystemMessage("\\fS��ƽ 4���� ���� ������ 5%���� ȿ����  �ߵ��˴ϴ�."),
	  true); break; case L1ItemId.DOLL_LAMIA: pc.sendPackets(new
	  S_SystemMessage("\\fS��ƽ+4, ������ �� ȿ����  �ߵ��˴ϴ�."), true); break; case
	  L1ItemId.DOLL_SPATOI: pc.sendPackets(new
	  S_SystemMessage("\\fS�ٰŸ� �߰�Ÿ�� +2, ���ϳ��� +10ȿ����  �ߵ��˴ϴ�."), true); break;
	  case L1ItemId.DOLL_ETIN: pc.sendPackets(new
	  S_SystemMessage("\\fS���� ���̽�Ʈ(�ӵ����� ����� ����) Ac-2, Ȧ�峻�� +10 ȿ����  �ߵ��˴ϴ�."),
	  true); break; case 41915: pc.sendPackets(new
	  S_SystemMessage("\\fSȰ����+2 ����+2 HP+50 MP+30 ȿ���� �ߵ��˴ϴ�."), true); break;
	  case 500144: pc.sendPackets(new
	  S_SystemMessage("\\fS����ġ10%����, Ȱ����+5 ȿ���� �ߵ��˴ϴ�."), true); break; case
	  500145: pc.sendPackets(new
	  S_SystemMessage("\\fS����ġ10%����, 1�д� MP 20ȸ�� ȿ����  �ߵ��˴ϴ�."), true); break;
	  case 500146: pc.sendPackets(new
	  S_SystemMessage("\\fS����ġ10%����, 32�ʴ� Hp200ȸ�� ȿ����  �ߵ��˴ϴ�."), true); break;
	  case L1ItemId.DOLL_RICH: pc.sendPackets(new
	  S_SystemMessage("\\fS�߰�Ÿ��+2, ���ݼ���+1, Ȱ��Ÿ+2, Ȱ����+1, SP+3"), true);
	  pc.sendPackets(new
	  S_SystemMessage("\\fS����������+3, ���ϳ���+10, Ȧ�峻��+10, ����+10,"), true);
	  pc.sendPackets(new
	  S_SystemMessage("\\fSHP+30, MP+40, ��ƽ+20 ȿ���� �ߵ��˴ϴ�.")); break; case
	  L1ItemId.DOLL_PHENIX: pc.sendPackets(new
	  S_SystemMessage("\\fS�����Ŀ�+3, ��ƽ+20, ����������+3, "), true);
	  pc.sendPackets(new S_SystemMessage("\\fS���ϳ���+10, Ȧ�峻��+10, ����+10, "),
	  true); pc.sendPackets(new S_SystemMessage("\\fSHP+30, MP+40 ȿ���� �ߵ��˴ϴ�."),
	  true); break; } }*/
	
}
