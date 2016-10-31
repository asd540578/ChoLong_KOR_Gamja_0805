package l1j.server.server.clientpackets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import l1j.server.server.datatables.CharacterTable;
import l1j.server.server.datatables.ExcludeLetterTable;
import l1j.server.server.datatables.LetterTable;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Clan.ClanMember;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.serverpackets.S_Letter;
import l1j.server.server.serverpackets.S_LetterList;
import l1j.server.server.serverpackets.S_ReadLetter;
import l1j.server.server.serverpackets.S_RenewLetter;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillSound;
import server.LineageClient;

public class C_MailBox extends ClientBasePacket {
	private static final int TYPE_PRIVATE_MAIL = 0; // ���� ����
	private static final int TYPE_BLOODPLEDGE_MAIL = 1; // ���� ����
	private static final int TYPE_KEPT_MAIL = 2; // ���� ����

	private static final int READ_PRIVATE_MAIL = 16; // ���� �����б�
	private static final int READ_BLOODPLEDGE_MAIL = 17; // ���� �����б�
	private static final int READ_KEPT_MAIL_ = 18; // ������ �����б�

	private static final int WRITE_PRIVATE_MAIL = 32; // ���� ��������
	private static final int WRITE_BLOODPLEDGE_MAIL = 33; // ���� ��������

	private static final int DEL_PRIVATE_MAIL = 48; // ���� ��������
	private static final int DEL_BLOODPLEDGE_MAIL = 49; // ���� ��������
	private static final int DEL_KEPT_MAIL = 50; // ������ ��������

	private static final int TO_KEEP_MAIL = 64; // ���� �����ϱ�

	private static final int PRICE_PRIVATEMAIL = 50; // ���� ���� ����
	private static final int PRICE_BLOODPLEDGEMAIL = 1000; // ���� ���� ����

	private static final int SIZE_PRIVATE_MAILBOX = 40; // ���� ������ ũ��
	private static final int SIZE_BLOODPLEDGE_MAILBOX = 80; // ���� ������ ũ��
	private static final int SIZE_KEPTMAIL_MAILBOX = 10; // ���������� ũ��

	private static final int LIST_DEL_PRIVATE_MAIL = 0x60;
	private static final int LIST_DEL_BLOODPLEDGE_MAIL = 0x61;

	private static final String C_MailBox = "[C] C_MailBox";

	public C_MailBox(byte abyte0[], LineageClient client) {
		super(abyte0);
		try {
			int type = readC();

			L1PcInstance pc = client.getActiveChar();
			synchronized (LetterTable.getInstance()) {
				switch (type) {
				case TYPE_PRIVATE_MAIL:
					if (pc.isGm()) {
						LetterList(pc, TYPE_PRIVATE_MAIL, 40);
						break;
					} else {
						LetterList(pc, TYPE_PRIVATE_MAIL, SIZE_PRIVATE_MAILBOX);
						break;
					}
				case TYPE_BLOODPLEDGE_MAIL:
					LetterList(pc, TYPE_BLOODPLEDGE_MAIL,
							SIZE_BLOODPLEDGE_MAILBOX);
					break;
				case TYPE_KEPT_MAIL:
					LetterList(pc, TYPE_KEPT_MAIL, SIZE_KEPTMAIL_MAILBOX);
					break;
				case READ_PRIVATE_MAIL:
					ReadLetter(pc, READ_PRIVATE_MAIL, TYPE_PRIVATE_MAIL);
					break;
				case READ_BLOODPLEDGE_MAIL:
					ReadLetter(pc, READ_BLOODPLEDGE_MAIL, TYPE_BLOODPLEDGE_MAIL);
					break;
				case READ_KEPT_MAIL_:
					ReadLetter(pc, READ_KEPT_MAIL_, TYPE_KEPT_MAIL);
					break;
				case WRITE_PRIVATE_MAIL:
					WritePrivateMail(pc);
					break;
				case WRITE_BLOODPLEDGE_MAIL:
					WriteBloodPledgeMail(pc);
					break;
				case DEL_PRIVATE_MAIL:
					DeleteLetter(pc, DEL_PRIVATE_MAIL, TYPE_PRIVATE_MAIL);
					break;
				case DEL_BLOODPLEDGE_MAIL:
					DeleteLetter(pc, DEL_BLOODPLEDGE_MAIL,
							TYPE_BLOODPLEDGE_MAIL);
					break;
				case DEL_KEPT_MAIL:
					DeleteLetter(pc, DEL_KEPT_MAIL, TYPE_KEPT_MAIL);
					break;
				case TO_KEEP_MAIL:
					SaveLetter(pc, TO_KEEP_MAIL, TYPE_KEPT_MAIL);
					break;
				case LIST_DEL_PRIVATE_MAIL:
					DeletePriListLetter(pc, DEL_PRIVATE_MAIL, TYPE_PRIVATE_MAIL);
					break;
				case LIST_DEL_BLOODPLEDGE_MAIL:
					DeleteBloodListLetter(pc, DEL_BLOODPLEDGE_MAIL,
							TYPE_PRIVATE_MAIL);
					break;
				default:
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clear();
		}
	}

	private void DeleteBloodListLetter(L1PcInstance pc, int type,
			int typePrivateMail) {
		// TODO �ڵ� ������ �޼ҵ� ����
		int size = readD();
		while (size-- > 0) {
			int id = readD();
			LetterTable.getInstance().deleteLetter(id);
			S_RenewLetter rl = new S_RenewLetter(pc, type, id);
			pc.sendPackets(rl, true);
		}
	}

	private void DeletePriListLetter(L1PcInstance pc, int type,
			int typePrivateMail) {
		// TODO �ڵ� ������ �޼ҵ� ����
		int size = readD();
		while (size-- > 0) {
			int id = readD();
			LetterTable.getInstance().deleteLetter(id);
			S_RenewLetter rl = new S_RenewLetter(pc, type, id);
			pc.sendPackets(rl, true);
		}
	}

	private boolean payMailCost(L1PcInstance RECEIVER, int PRICE) {
		int AdenaCnt = RECEIVER.getInventory().countItems(L1ItemId.ADENA);
		if (AdenaCnt < PRICE) {
			S_ServerMessage sm = new S_ServerMessage(189, "");
			RECEIVER.sendPackets(sm, true);
			return false;
		}

		RECEIVER.getInventory().consumeItem(L1ItemId.ADENA, PRICE);
		return true;
	}

	private void WritePrivateMail(L1PcInstance sender) {
		if (!payMailCost(sender, PRICE_PRIVATEMAIL))
			return;

		int paper = readH(); // ������
		SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd",
				Locale.KOREA);
		Date date = new Date();
		try {
			String dTime = formatter.format(date);
			String receiverName = readS();
			String subject = readSS();
			String content = readSS();
			// if(!receiverName.equalsIgnoreCase("��Ƽ��") &&
			// !receiverName.equalsIgnoreCase("�̼��Ǿ�")){
			// if(!checkCountMail(sender, receiverName, TYPE_PRIVATE_MAIL,
			// SIZE_PRIVATE_MAILBOX)) return;
			// }
			L1PcInstance target = L1World.getInstance().getPlayer(receiverName);
			// ���ܵǰ� �ִ� ���
			if (target == null) {
				target = CharacterTable.getInstance().restoreCharacter(
						receiverName);
				if (target != null)
					ExcludeLetterTable.getInstance().load(target);
			}
			if (target != null
					&& target.getExcludingLetterList().contains(
							sender.getName())) {
				sender.sendPackets(new S_ServerMessage(117, target.getName()));
				return;
			}
			int objid = LetterTable.getInstance().writeLetter(paper, dTime,
					sender.getName(), receiverName, TYPE_PRIVATE_MAIL, subject,
					content);

			sendMessageToReceiver(target, sender, TYPE_PRIVATE_MAIL, objid,
					subject);

			S_ServerMessage sm2 = new S_ServerMessage(1239);
			sender.sendPackets(sm2, true);

			/*
			 * if(receiverName.equalsIgnoreCase("��Ƽ��") ||
			 * receiverName.equalsIgnoreCase("�̼��Ǿ�")){
			 * sendMessageToReceiver(target, sender, TYPE_PRIVATE_MAIL, 40);
			 * }else{ sendMessageToReceiver(target, sender, TYPE_PRIVATE_MAIL,
			 * SIZE_PRIVATE_MAILBOX); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			date = null;
			formatter = null;
		}
	}

	private void WriteBloodPledgeMail(L1PcInstance sender) {

		if (!payMailCost(sender, PRICE_BLOODPLEDGEMAIL))
			return;

		int paper = readH(); // ������
		SimpleDateFormat formatter = new SimpleDateFormat("yy/MM/dd",
				Locale.KOREA);
		Date date = new Date();
		String dTime = formatter.format(date);
		String receiverName = readS();
		String subject = readSS();
		String content = readSS();

		L1Clan targetClan = null;
		for (L1Clan clan : L1World.getInstance().getAllClans()) {
			if (clan.getClanName().toLowerCase()
					.equals(receiverName.toLowerCase())) {
				targetClan = clan;
				break;
			}
		}
		String name;
		L1PcInstance target = null;
		ArrayList<ClanMember> clanMemberList = targetClan.getClanMemberList();

		for (int i = 0, a = clanMemberList.size(); i < a; i++) {

			name = clanMemberList.get(i).name;

			target = L1World.getInstance().getPlayer(name);
			// ���ܵǰ� �ִ� ���
			if (target == null) {
				try {
					target = CharacterTable.getInstance()
							.restoreCharacter(name);
				} catch (Exception e) {
					// TODO �ڵ� ������ catch ���
					e.printStackTrace();
				}
				if (target != null)
					ExcludeLetterTable.getInstance().load(target);
			}
			if (target != null
					&& target.getExcludingLetterList().contains(
							sender.getName())) {
				sender.sendPackets(new S_ServerMessage(117, target.getName()));
				continue;
			}
			if (!checkCountMail(sender, name, TYPE_BLOODPLEDGE_MAIL,
					SIZE_BLOODPLEDGE_MAILBOX))
				continue;
			int objid = LetterTable.getInstance().writeLetter(paper, dTime,
					sender.getName(), name, TYPE_BLOODPLEDGE_MAIL, subject,
					content);
			BloodsendMessageToReceiver(target, sender, 1, objid, subject);
		}

		S_ServerMessage sm2 = new S_ServerMessage(1239);
		sender.sendPackets(sm2, true);

		date = null;
		formatter = null;
	}

	// ������ �����ϱ����� �޼ҵ�
	private void DeleteLetter(L1PcInstance pc, int type, int letterType) {
		int id = readD();
		LetterTable.getInstance().deleteLetter(id);
		S_RenewLetter rl = new S_RenewLetter(pc, type, id);
		pc.sendPackets(rl, true);
	}

	// ������ �б����� �޼ҵ�
	private void ReadLetter(L1PcInstance pc, int type, int letterType) {
		int id = readD();
		LetterTable.getInstance().CheckLetter(id);
		S_ReadLetter rl = new S_ReadLetter(pc, type, letterType, id);
		if (pc == null)
			return;
		pc.sendPackets(rl, true);
	}

	// ��������Ʈ ��������� �޼ҵ�
	private void LetterList(L1PcInstance pc, int type, int count) {
		if (pc != null)
			pc.sendPackets(new S_LetterList(pc, type, count), true);
	}

	// ��������Ʈ ��������� �޼ҵ�
	private void Letter(L1PcInstance pc, L1PcInstance target, int type,
			int iden, int objid, String ����) {
		if (pc != null)
			pc.sendPackets(new S_Letter(target, type, iden, objid, ����));
	}

	// ������ �����ϱ� ���� �޼ҵ�
	private void SaveLetter(L1PcInstance pc, int type, int letterType) {
		int id = readD();
		LetterTable.getInstance().SaveLetter(id, letterType);
		S_RenewLetter rl = new S_RenewLetter(pc, type, id);
		pc.sendPackets(rl, true);
	}

	private boolean checkCountMail(L1PcInstance from, String to, int type,
			int max) {
		int cntMailInMailBox = LetterTable.getInstance().getLetterCount(to,
				type);
		if (cntMailInMailBox >= max) { // ������ ����
			S_ServerMessage sm = new S_ServerMessage(1261);
			from.sendPackets(sm, true);
			return false;
		}
		return true;
	}

	private void sendMessageToReceiver(L1PcInstance receiver,
			L1PcInstance sender, final int type, int objid, String ����) {
		S_ServerMessage sm2 = new S_ServerMessage(1239);
		sender.sendPackets(sm2, true);
		if (receiver != null && receiver.getOnlineStatus() != 0) {
			// LetterList(receiver,type,MAILBOX_SIZE);
			if (sender != null && sender.getOnlineStatus() != 0) {
				Letter(receiver, sender, type, 0, objid, ����);
				if (receiver.getId() != sender.getId()) {
					receiver.sendPackets(new S_SkillSound(receiver.getId(),
							1091), true);
					receiver.sendPackets(new S_ServerMessage(428), true); // ������
																			// �����߽��ϴ�.
				}
			}
			// if(sender != null)
			// sender.sendPackets(new S_LetterList(sender,type,MAILBOX_SIZE),
			// true);
		}
	}

	private void BloodsendMessageToReceiver(L1PcInstance receiver,
			L1PcInstance sender, final int type, int objid, String ����) {
		if (receiver != null && receiver.getOnlineStatus() != 0) {
			if (sender != null && sender.getOnlineStatus() != 0) {
				Letter(receiver, sender, type, 0, objid, ����);
				if (receiver.getId() != sender.getId()) {
					// LetterList(receiver,type,MAILBOX_SIZE);
					receiver.sendPackets(new S_SkillSound(receiver.getId(),
							1091), true);
					receiver.sendPackets(new S_ServerMessage(428), true); // ������
																			// �����߽��ϴ�.
				}
			}
		}
	}

	@Override
	public String getType() {
		return C_MailBox;
	}
}
