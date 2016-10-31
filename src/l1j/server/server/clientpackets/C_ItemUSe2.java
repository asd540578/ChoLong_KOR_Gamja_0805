package l1j.server.server.clientpackets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_DelSkill;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_ItemName;
import l1j.server.server.serverpackets.S_NPCTalkReturn;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.SQLUtil;
import server.LineageClient;

public class C_ItemUSe2 extends ClientBasePacket {

	private static final String C_ITEM_USE2 = "[C] C_ItemUSe2";
	private static Logger _log = Logger.getLogger(C_ItemUSe2.class.getName());
	Calendar currentDate = Calendar.getInstance();
	
	
	public C_ItemUSe2(byte abyte0[], LineageClient client) throws Exception {
		super(abyte0);
		int itemObjid = readD();

		L1PcInstance pc = client.getActiveChar();
		
		L1ItemInstance l1iteminstance = pc.getInventory().getItem(itemObjid);
		
		int itemId;
		
		try {
			itemId = l1iteminstance.getItem().getItemId();
		} catch (Exception e) {
			return;
		}
		switch(itemId){
		case 5557:
			if (pc.getInventory().checkItem(41159, 100)){
				pc.getInventory().consumeItem(41159, 100);
				pc.getInventory().storeItem(5556, 1);
			} else {
				pc.sendPackets(new S_SystemMessage("��ǥ������ �ʿ��� ������ �����մϴ�."));
			}
			break;
		case 40969: // ��ũ���� ��ȥ�� ����ü
			if (pc.getMapId() >= 451 && pc.getMapId() <= 536
			&& pc.getMapId() != 480 && pc.getMapId() != 481
			&& pc.getMapId() != 482 && pc.getMapId() != 483
			&& pc.getMapId() != 484 && pc.getMapId() != 521
			&& pc.getMapId() != 522 && pc.getMapId() != 523
			&& pc.getMapId() != 524) {// ���
				L1Location loc = new L1Location();
				loc.set(32741, 32768, 479);
				loc = loc.randomLocation(3, false);
				L1Teleport.teleport(pc, loc.getX(), loc.getY(),	(short) 479, pc.getMoveState().getHeading(), true, true);
				pc.getInventory().removeItem(l1iteminstance, 1);
			} else {
				pc.sendPackets(new S_SystemMessage("��Ÿ�ٵ� ���� ������ ����� �����մϴ�."), true);
			}
			break;
		
		case 51093:case 51094:case 51095:case 51096:case 51097:case 51098:case 51099:case 51100:
			//Ŭ���� ���湰�� 
			if (pc.getClanid() != 0) {
				pc.sendPackets(new S_ChatPacket(pc,"������ ���� Ż���Ͽ� �ֽñ� �ٶ��ϴ�."));
				return;
			} else if (itemId == 51093 && pc.getType() == 0) { // �ڳ� ����?
				pc.sendPackets(new S_ChatPacket(pc,"����� �̹� ���� Ŭ���� �Դϴ�."));
				return;
			} else if (itemId == 51094 && pc.getType() == 1) { // �ڳ� ���?
				pc.sendPackets(new S_ChatPacket(pc,"����� �̹� ��� Ŭ���� �Դϴ�."));
				return;
			} else if (itemId == 51095 && pc.getType() == 2) { // �ڳ� ����?
				pc.sendPackets(new S_ChatPacket(pc,"����� �̹� ���� Ŭ���� �Դϴ�."));
				return;
			} else if (itemId == 51096 && pc.getType() == 3) { // �ڳ� ������?
				pc.sendPackets(new S_ChatPacket(pc,"����� �̹� ������ Ŭ���� �Դϴ�."));
				return;
			} else if (itemId == 51097 && pc.getType() == 4) { // �ڳ� ��ũ����?
				pc.sendPackets(new S_ChatPacket(pc,"����� �̹� ��ũ���� Ŭ���� �Դϴ�."));
				return;
			} else if (itemId == 51098 && pc.getType() == 5) { // �ڳ� ����?
				pc.sendPackets(new S_ChatPacket(pc,"����� �̹� ���� Ŭ���� �Դϴ�."));
				return;
			} else if (itemId == 51099 && pc.getType() == 6) { // �ڳ� ȯ����?
				pc.sendPackets(new S_ChatPacket(pc,"����� �̹� ȯ���� Ŭ���� �Դϴ�."));
				return;
			} else if (itemId == 51100 && pc.getType() == 7) { // �ڳ� ȯ����?
				pc.sendPackets(new S_ChatPacket(pc,"����� �̹� ���� Ŭ���� �Դϴ�."));
				return;
			}
			int[] Mclass = new int[] { 0, 61, 138, 734, 2786, 6658, 6671, 12490 };
			int[] Wclass = new int[] { 1, 48, 37, 1186, 2796, 6661, 6650, 12494 };
			if (itemId == 51093 && pc.getType() != 0 && pc.get_sex() == 0) {
				pc.setType(0);
				pc.setClassId(Mclass[pc.getType()]);
			} else if (itemId == 51093 && pc.getType() != 0 && pc.get_sex() == 1) {//����
				pc.setType(0);
				pc.setClassId(Wclass[pc.getType()]);
			} else if (itemId == 51094 && pc.getType() != 1 && pc.get_sex() == 0) { // ����: ���
				pc.setType(1);
				pc.setClassId(Mclass[pc.getType()]);
			} else if (itemId == 51094 && pc.getType() != 1 && pc.get_sex() == 1) {
				pc.setType(1);
				pc.setClassId(Wclass[pc.getType()]);
			} else if (itemId == 51095 && pc.getType() != 2 && pc.get_sex() == 0) { // ����: ����
				pc.setType(2);
				pc.setClassId(Mclass[pc.getType()]);
			} else if (itemId == 51095 && pc.getType() != 2 && pc.get_sex() == 1) {
				pc.setType(2);
				pc.setClassId(Wclass[pc.getType()]);
			} else if (itemId == 51096 && pc.getType() != 3 && pc.get_sex() == 0) { // ����: ������
				pc.setType(3);
				pc.setClassId(Mclass[pc.getType()]);
			} else if (itemId == 51096 && pc.getType() != 3 && pc.get_sex() == 1) {
				pc.setType(3);
				pc.setClassId(Wclass[pc.getType()]);
			} else if (itemId == 51097 && pc.getType() != 4 && pc.get_sex() == 0) { // ����: ��ũ����
				pc.setType(4);
				pc.setClassId(Mclass[pc.getType()]);
			} else if (itemId == 51097 && pc.getType() != 4 && pc.get_sex() == 1) {
				pc.setType(4);
				pc.setClassId(Wclass[pc.getType()]);
			} else if (itemId == 51098 && pc.getType() != 5 && pc.get_sex() == 0) { // ����: ����
				pc.setType(5);
				pc.setClassId(Mclass[pc.getType()]);
			} else if (itemId == 51098 && pc.getType() != 5 && pc.get_sex() == 1) {
				pc.setType(5);
				pc.setClassId(Wclass[pc.getType()]);
			} else if (itemId == 51099 && pc.getType() != 6 && pc.get_sex() == 0) { // ����: ȯ����
				pc.setType(6);
				pc.setClassId(Mclass[pc.getType()]);
			} else if (itemId == 51099 && pc.getType() != 6 && pc.get_sex() == 1) {
				pc.setType(6);
				pc.setClassId(Wclass[pc.getType()]);
			} else if (itemId == 51100 && pc.getType() != 7 && pc.get_sex() == 0) { // ����: ����
				pc.setType(7);
				pc.setClassId(Mclass[pc.getType()]);
			} else if (itemId == 51100 && pc.getType() != 7 && pc.get_sex() == 1) {
				pc.setType(7);
				pc.setClassId(Wclass[pc.getType()]);
			}
			if (pc.getWeapon() != null)
				pc.getInventory().setEquipped(pc.getWeapon(), false, false, false, false);
			pc.getInventory().takeoffEquip(945);
			pc.sendPackets(new S_CharVisualUpdate(pc));
			for (L1ItemInstance armor : pc.getInventory().getItems()) {
				for (int type = 0; type <= 12; type++) {
					if (armor != null) {
						pc.getInventory().setEquipped(armor, false, false, false, false);
					}
				}
			}
			pc.sendPackets(new S_DelSkill(255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
					255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255));
			deleteSpell(pc);
			pc.setTempCharGfx(pc.getClassId());
			pc.sendPackets(new S_ChangeShape(pc.getId(), pc.getClassId()));
			Broadcaster.broadcastPacket(pc, new S_ChangeShape(pc.getId(), pc.getClassId()));
			pc.getInventory().removeItem(l1iteminstance, 1);
			pc.save();
			Thread.sleep(500);
			pc.sendPackets(new S_Disconnect());
			break;
		

		case 60217:
			L1SkillUse su = new L1SkillUse();
			su.handleCommands(pc, L1SkillId.STATUS_COMA_5, pc.getId(), pc.getX(), pc.getY(), null, 0, L1SkillUse.TYPE_GMBUFF);
			su = null;
			pc.getInventory().removeItem(l1iteminstance, 1);
			break;
		case 40003:
			for (L1ItemInstance lightItem : pc.getInventory()
					.getItems()) {
				if (lightItem.getItem().getItemId() == 40002) {
					lightItem.setRemainingTime(l1iteminstance.getItem()
							.getLightFuel());
					pc.sendPackets(new S_ItemName(lightItem), true);

					pc.sendPackets(new S_ServerMessage(230), true);
					break;
				}
			}
			pc.getInventory().removeItem(l1iteminstance, 1);
			break;
		case 600258:
			�����ǰ�ȣ(pc, l1iteminstance);
			break;
		
		case 500022:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s10"), true);
				break;
		case 500023:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s00"), true);
				break;
		case 500024:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s01"), true);
				break;
		case 500025:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s02"), true);
				break;
		case 500026:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s03"), true);
				break;
		case 500027:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s04"), true);
				break;
		case 500028:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s05"), true);
				break;
		case 500029:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s06"), true);
				break;
		case 500030:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s07"), true);
				break;
		case 500031:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s08"), true);
				break;
		case 500032:
				pc.sendPackets(new S_NPCTalkReturn(pc.getId(),
						"j_ep0s09"), true);
				break;
		
		default:
			break;
		}
	}
	
	private void deleteSpell(L1PcInstance pc) {
		int player = pc.getId();
		Connection con = null;
		PreparedStatement pstm = null;
		try {

			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("DELETE FROM character_skills WHERE char_obj_id=?");
			pstm.setInt(1, player);
			pstm.execute();
		} catch (Exception e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	private void �����ǰ�ȣ(L1PcInstance pc, L1ItemInstance useItem) {
		
		Timestamp lastUsed = useItem.getLastUsed();
		if (lastUsed == null || currentDate.getTimeInMillis() > lastUsed.getTime()
						+ (1000 * 60 * 60 * 1)) {

			pc.sendPackets(new S_SkillSound(pc.getId(), 12536));
			pc.broadcastPacket(new S_SkillSound(pc.getId(), 12536));
			
			if(pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.�����ǰ�ȣ))
			{
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.�����ǰ�ȣ, 600 * 1000);
			}
			else
			{
				pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.�����ǰ�ȣ, 600 * 1000);
			}
			
			useItem.setLastUsed(new Timestamp(currentDate.getTimeInMillis()));
		} else {
			long i = (lastUsed.getTime() + (1000 * 60 * 60 * 1))
					- currentDate.getTimeInMillis();
			Calendar cal = (Calendar) currentDate.clone();
			cal.setTimeInMillis(cal.getTimeInMillis() + i);
			pc.sendPackets(new S_SystemMessage(i / 60000 + "�� ����("
					+ cal.getTime().getHours() + ":"
					+ cal.getTime().getMinutes() + " ����)�� ����� �� �����ϴ�."), true);
		}
	}
	
	@Override
	public String getType() {
		return C_ITEM_USE2;
	}
}