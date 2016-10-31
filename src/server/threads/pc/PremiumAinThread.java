package server.threads.pc;

import static l1j.server.server.model.skill.L1SkillId.ADVANCE_SPIRIT;
import static l1j.server.server.model.skill.L1SkillId.BLESS_WEAPON;
import static l1j.server.server.model.skill.L1SkillId.EARTH_SKIN;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_DEX;
import static l1j.server.server.model.skill.L1SkillId.PHYSICAL_ENCHANT_STR;

import java.io.File; // ��ƹ� ȫ��
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.L1DatabaseFactory;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.GameSystem.UserRanking.UserRankingController;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.TimeController.WarTimeController;
import l1j.server.server.datatables.ClanTable;
import l1j.server.server.datatables.RobotTable;
import l1j.server.server.datatables.RobotTable.RobotTeleport;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Clan;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.skill.L1SkillId;
import l1j.server.server.model.skill.L1SkillUse;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_Restart;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.utils.CommonUtil;
import l1j.server.server.utils.SQLUtil;
import server.manager.eva;

public class PremiumAinThread extends Thread {
	private static final int[] loc = { -2, -1, 0, 1, 2 };
	private static PremiumAinThread _instance;
	private static Logger _log = Logger.getLogger(PremiumAinThread.class
			.getName());

	public static PremiumAinThread getInstance() {
		if (_instance == null) {
			_instance = new PremiumAinThread();
			_instance.start();
		}
		return _instance;
	}

	public PremiumAinThread() {
		super("server.threads.pc.PremiumAinThread");
	}
	

	public void run() {
		//System.out.println(PremiumAinThread.class.getName() + " ����");
		while (true) {
			try {

				for (L1Clan c : L1World.getInstance().getAllClans()) {
					ClanTable.getInstance().updateClan(c);
				}

				for (L1PcInstance _client : L1World.getInstance()
						.getAllPlayers()) {
					if (_client instanceof L1RobotInstance) {
						continue;
					}
					if (_client == null || _client.getNetConnection() == null) {
						if (_client.noPlayerCK) {
							// �ڷ���Ʈ
							if (_client.getTeleportTime() != 0
									&& _client.getCurrentTeleportCount() >= _client
											.getTeleportTime()) {
								RobotTeleport robotTeleport = RobotTable
										.getRobotTeleportList().get(
												CommonUtil.random(RobotTable
														.getRobotTeleportList()
														.size()));
								L1Teleport.teleport(
										_client,
										robotTeleport.x
												+ loc[CommonUtil.random(5)],
										robotTeleport.y
												+ loc[CommonUtil.random(5)],
										(short) robotTeleport.mapid,
										robotTeleport.heading);
								_client.setCurrentTeleportCount(0);
							}
							// ��ų���
							if (_client.getSkillTime() != 0
									&& _client.getCurrentSkillCount() >= _client
											.getSkillTime()) {
								BuffStart buff = new BuffStart();
								buff.player = _client;
								GeneralThreadPool.getInstance().execute(buff);
								_client.setCurrentSkillCount(0);
							}
							// �̵�
							if (_client.getMoveTime() != 0
									&& _client.getCurrentMoveCount() >= _client
											.getMoveTime()) {

							}
							_client.setCurrentTeleportCount(_client
									.getCurrentTeleportCount() + 1);
							_client.setCurrentSkillCount(_client
									.getCurrentSkillCount() + 1);

						}
						continue;
					} else {
						try {

							if (_client.���͹��� && _client.getLevel() < 60) {
								_client.sendPackets(new S_PacketBox(
										S_PacketBox.���͹���, 1), true);
							} else if (_client.���͹���) {
								_client.sendPackets(new S_PacketBox(
										S_PacketBox.���͹���, 0), true);
								_client.���͹��� = false;
							}
							
							int deadtime = _client.getDeadTimeCount();
							int tc = _client.getTimeCount();
							int ttc = _client.gettamtimecount();
							int FT = Config.FEATHER_TIME;
							int tamt = Config.Tam_Time;
							

							if (Config.Tam_Ok) {
								if (ttc >= tamt) {
									_client.settamtimecount(0);// 6(��)���� + 1����
																// �����ش�.
									int tamcount = _client.tamcount();
									if (tamcount > 0) {
										int addtam = Config.Tam_Count * tamcount;
										_client.getNetConnection().getAccount().tam_point += addtam;
										try {
											_client.getNetConnection().getAccount().updateTam();
										} catch (Exception e) {
										}

										_client.sendPackets(new S_SystemMessage("������ �� " + tamcount + "�ܰ� ���� : Tam����Ʈ ("+ addtam + ")�� ����!"));
										try {
											_client.sendPackets(new S_NewCreateItem(S_NewCreateItem.TAM_POINT, _client.getNetConnection()),	true);
										} catch (Exception e) {
										}
									}
								} else {
									_client.settamtimecount(ttc + 1);// 6(��)���� +1���������ش�.
								}
							}

							if (tc >= FT){ 
									giveFeather(_client);//�ź��ѳ��� ���� ���޽ð� (6��)
							}else {
									_client.setTimeCount(tc+1);//6(��)���� + 1���� �����ش�.
							}
							
							if (Config.Event_Box) {
								�����̺�Ʈ(_client);
							}

							if (_client.getSkillEffectTimerSet()
									.hasSkillEffect(L1SkillId.DRAGON_EME_2)) {
								DragonEME(_client);
							}
							if (_client.getSkillEffectTimerSet()
									.hasSkillEffect(L1SkillId.DRAGON_EME_1)) {
								DragonEME2(_client);
							}
							// if(_client.getLevel() >= 49){
							int sc = _client.getSafeCount();
							if (CharPosUtil.getZoneType(_client) == 1
									&& !_client.isPrivateShop()) {
								if (sc >= 14) {// 141
									if (_client.getAinHasad() <= 1999999) {
										_client.calAinHasad(10000);
										_client.sendPackets(new S_PacketBox(
												S_PacketBox.AINHASAD, _client),
												true);
									}
									_client.setSafeCount(0);
								} else {
									_client.setSafeCount(sc + 1);
								}
							} else {
								if (sc > 0)
									_client.setSafeCount(0);
							}
							// }

							int keycount = _client.getInventory().countItems(
									L1ItemId.DRAGON_KEY);
							if (keycount > 0)
								DragonkeyTimeCheck(_client, keycount);

							int castle_id = L1CastleLocation
									.getCastleIdByArea(_client);
							if (castle_id != 0) {
								if (WarTimeController.getInstance().isNowWar(
										castle_id)) {
									_client.setCastleZoneTime(_client
											.getCastleZoneTime() + 1);
								}
							}

							if (_client.getDollList().size() > 0) {
								for (L1DollInstance doll : _client
										.getDollList()) {
									if (doll.getDollType() == L1DollInstance.DOLLTYPE_HW_HUSUABI) {
										if (_client.��������_�ҷ��������_Count >= 60) {
											_client.getInventory().storeItem(
													140722, 1); // �ٷ��� ��������
											_client.sendPackets(new S_SystemMessage(
													"�ҷ��� ����ƺ� �������� ��ȯ ���� : �ٷ��� ���� ���� ȹ��."));
											// _client.sendPackets(new
											// S_ServerMessage(403,
											// "�׷����� ���� ����"), true);
											_client.��������_�ҷ��������_Count = 0;
										} else
											_client.��������_�ҷ��������_Count++;
									}

									if (doll.getDollType() == L1DollInstance.DOLLTYPE_�׷���) {
										if (_client.��������_�׷���_Count >= 30) {
											_client.getInventory().storeItem(
													9057, 1); // �ҷ��� ȣ�ھ� ����
											_client.sendPackets(new S_SystemMessage(
													"�׷��� �������� ��ȯ ���� : �׷����� ���� ���� ȹ��."));
											// _client.sendPackets(new
											// S_ServerMessage(403,
											// "�׷����� ���� ����"), true);
											_client.��������_�׷���_Count = 0;
										} else
											_client.��������_�׷���_Count++;
									}
								}
							}

							if (_client.isDead()) {
								if (deadtime >= 5) {
									_client.logout();
									_client.getNetConnection().kick();
								} else {
									_client.setDeadTimeCount(deadtime + 1);// 6(��)����
																			// +
																			// 1����
																			// �����ش�.
								}
							} else {
								_client.setDeadTimeCount(0);
							}

						} catch (Exception e) {
							_log.warning("Primeum give failure.");
							_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
							throw e;
						}
					}

					if (_client.PC��_����������) {
						_client.sendPackets(new S_PacketBox(
								S_PacketBox.GREEN_MESSAGE,
								"[PC�� ��ǰ ���� �ȳ�] PC�� �̿� �ð��� ����Ǿ� ���� ����ŸƮ�� ����˴ϴ�. "));
						_client.sendPackets(new S_SystemMessage(
								"[PC�� ��ǰ ���� �ȳ�] ����ŸƮ�� �������� �ʾƵ� ������ ������ �����ϴ�."));
						_client.sendPackets(new S_Restart(_client.getId(), 1),
								true);
					}

					long sysTime = System.currentTimeMillis();
					if (_client.PC��_����) {
						if (_client.getNetConnection().getAccount()
								.getBuff_PC��() != null) {
							if (sysTime <= _client.getNetConnection()
									.getAccount().getBuff_PC��().getTime()) {
								long �Ǿ�Ÿ�� = _client.getNetConnection()
										.getAccount().getBuff_PC��().getTime()
										- sysTime;
								TimeZone seoul = TimeZone.getTimeZone("UTC");
								Calendar calendar = Calendar.getInstance(seoul);
								calendar.setTimeInMillis(�Ǿ�Ÿ��);
								int d = calendar.get(Calendar.DATE) - 1;
								int h = calendar.get(Calendar.HOUR_OF_DAY);
								int m = calendar.get(Calendar.MINUTE);
								int sc = calendar.get(Calendar.SECOND);
								if (d == 0) {
									if (h > 0) {
										if (h == 1 && m == 0) {
											_client.sendPackets(new S_PacketBox(
													S_PacketBox.GREEN_MESSAGE,
													"[PC�� �̿� �ð�] " + h + "�ð� "
															+ m + "�� " + sc
															+ "�� ���ҽ��ϴ�."));
										}
									} else {
										if (m == 30) {
											_client.sendPackets(new S_PacketBox(
													S_PacketBox.GREEN_MESSAGE,
													"[PC�� �̿� �ð�] " + m + "�� "
															+ sc + "�� ���ҽ��ϴ�."));
											_client.sendPackets(new S_SystemMessage(
													"[PC�� ��ǰ ���� �ȳ�] �̿� �ð� ������ ���� ����ŸƮ�� ���� �˴ϴ�."));
										} else if (m == 20) {
											_client.sendPackets(new S_PacketBox(
													S_PacketBox.GREEN_MESSAGE,
													"[PC�� �̿� �ð�] " + m + "�� "
															+ sc + "�� ���ҽ��ϴ�."));
											_client.sendPackets(new S_SystemMessage(
													"[PC�� ��ǰ ���� �ȳ�] �̿� �ð� ������ ���� ����ŸƮ�� ���� �˴ϴ�."));
										} else if (m <= 10) {
											_client.sendPackets(new S_PacketBox(
													S_PacketBox.GREEN_MESSAGE,
													"[PC�� �̿� �ð�] " + m + "�� "
															+ sc + "�� ���ҽ��ϴ�."));
											_client.sendPackets(new S_SystemMessage(
													"[PC�� ��ǰ ���� �ȳ�] ������ ������ �����־ ������ ������ �����ϴ�. ����� �ڵ� ����ŸƮ�� ����˴ϴ�."));
										}

									}
								}
							} else {
								_client.PC��_���� = false;
								_client.PC��_���������� = true;
								String s = "08 00 e7 6d";// �Ǿ���..
								_client.sendPackets(new S_NewCreateItem(126, s));
								_client.sendPackets(
										new S_Restart(_client.getId(), 1), true);
							}
						}
					}
				}

				����üũ();
				// 1�ð� �ֱ�� ä�÷α� ����
			if (eva.saveCount > Config.LOGGING_TIME) {
				  eva.jServerMultiChatLogWindow.savelog();
				  eva.jSystemLogWindow.savelog(); eva.saveCount = 0; } else {
				 eva.saveCount++; }
				 
			} catch (Exception e) {
				e.printStackTrace();
				// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				// cancel();
			} finally {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private boolean ������(int npcid, int itemid) {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("SELECT COUNT(*) FROM npc_shop_sell WHERE npc_id = ? AND item_id = ?");
			pstm.setInt(1, npcid);
			pstm.setInt(2, itemid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	private boolean ��������(int npcid, int itemid) {
		Connection con = null;
		PreparedStatement pstm = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con
					.prepareStatement("DELETE FROM shop_npc WHERE npc_id = ? AND item_id = ?");
			pstm.setInt(1, npcid);
			pstm.setInt(2, itemid);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
		return false;
	}

	private void ����üũ() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean ok = false;
		int npcid, itemid;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM shop_npc");
			rs = pstm.executeQuery();
			while (rs.next()) {
				npcid = rs.getInt("npc_id");
				itemid = rs.getInt("item_id");
				if (npcid >= 8000001 && npcid <= 8010002)
					continue;
				ok = ������(npcid, itemid);
				if (!ok) {
					��������(npcid, itemid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}

	}
	

	private void �����̺�Ʈ(L1PcInstance pc) {
		
		if (pc instanceof L1RobotInstance)
			return;
		if (pc.���ڻ���Time >= 60) {
			pc.getInventory().storeItem(60517, 1); // ���ɻ��� ����
			pc.sendPackets(new S_ServerMessage(403, "1�ð�Ÿ�� ���� ����"), true);
			pc.���ڻ���Time = 0;
		} else
			pc.���ڻ���Time++;
	}

	private void DragonEME(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_2)) {
			if (pc.getDETime() != null) {
				if (System.currentTimeMillis() > pc.getDETime().getTime()) {
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME,
							0x02, 0), true);
					pc.getSkillEffectTimerSet().removeSkillEffect(
							L1SkillId.DRAGON_EME_2);
				} else {
					long DETIME = pc.getDETime().getTime()
							- System.currentTimeMillis();
					pc.getSkillEffectTimerSet().removeSkillEffect(
							L1SkillId.DRAGON_EME_2);
					pc.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.DRAGON_EME_2, (int) DETIME);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGON_EME,
							0x02, (int) DETIME / 1000), true);
					try {
						pc.save();
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void DragonEME2(L1PcInstance pc) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGON_EME_1)) {
			if (pc.getDETime2() != null) {
				if (System.currentTimeMillis() > pc.getDETime2().getTime()) {
					S_PacketBox pb1 = new S_PacketBox(S_PacketBox.DRAGON_EME,
							0x01, 0);
					pc.sendPackets(pb1, true);
					pc.getSkillEffectTimerSet().removeSkillEffect(
							L1SkillId.DRAGON_EME_1);
				} else {
					long DETIME = pc.getDETime2().getTime()
							- System.currentTimeMillis();
					pc.getSkillEffectTimerSet().removeSkillEffect(
							L1SkillId.DRAGON_EME_1);
					pc.getSkillEffectTimerSet().setSkillEffect(
							L1SkillId.DRAGON_EME_1, (int) DETIME);
					S_PacketBox pb2 = new S_PacketBox(S_PacketBox.DRAGON_EME,
							0x01, (int) DETIME / 1000);
					pc.sendPackets(pb2, true);
					try {
						pc.save();
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			}
		}
	}

	private Date day = new Date(System.currentTimeMillis());

	private void giveFeather(L1PcInstance pc) {

		 pc.setTimeCount(0);
		
		UserRankingController.isRenewal = true;
	//	UserRankingController.reload();
		
		if (pc.isPrivateShop()) { 
			return;
		} else {
			int realPremiumNumber = 1;   // �⺻������ �� ������ ���� ��ƹ� ȫ��
			
			String savedir = "c:\\uami\\"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"\\"+pc.getName();
			File dir=new File(savedir);
			if(dir.exists()){  // ȫ���� ������
				realPremiumNumber =realPremiumNumber*1;     // ȫ���⸦ ������ ���޵� ����
				pc.getInventory().storeItem(437010, realPremiumNumber); // �ź��� �������� ����  ���
				pc.sendPackets(new S_SystemMessage("ȫ���� �������� �巡���� ���̾Ƹ�� ("+realPremiumNumber+")�� ������ϴ�."));
			}else{             // ȫ���� �������� 
				pc.getInventory().storeItem(40308, realPremiumNumber); // �ź��� �������� ���� 
				pc.sendPackets(new S_SystemMessage("ȫ���⸦ �ѽø� �巡���� ���̾ư� ���� �˴ϴ�."));
			}
		} // ��ƹ� ȫ��
		if (pc instanceof L1RobotInstance) {
			return;
		}

		if (pc.getInventory().calcWeightpercent() >= 90) {
			pc.sendPackets(new S_ServerMessage(1414)); // ���� �������� ����á���ϴ�.
			return;
		}

		pc.setTimeCount(0);

		int FN = Config.FEATHER_NUMBER;
		int CLN = Config.CLAN_NUMBER;
		int CAN = Config.CASTLE_NUMBER;
		boolean eve = false;
		day.setTime(System.currentTimeMillis());
		// 18��~24�� ���� �ι� ����
		/*
		 * if(day.getHours() >= 18 && day.getHours() <= 23){ eve = true; //FN *=
		 * 2; //CLN *= 2; //CAN *= 2; }
		 */

		if (pc.isPrivateShop()) {
			// pc.getInventory().storeItem(41159, 1); // �ź��� �������� ����
			// pc.sendPackets(new S_ServerMessage(403, "$5116 (1)"));
		} else {
		
			int total = eve ? FN * 2 : FN;
			pc.sendPackets(new S_ServerMessage(403, "�Ƚ��� ���� ("
					+ (eve ? FN * 2 : FN) + ")"), true);
			// S_SystemMessage sm = new
			// S_SystemMessage("�Ƚ��� ���� ("+FN+")�� ������ϴ�.");
			// pc.sendPackets(sm); sm.clear(); sm = null;
			L1Clan clan = L1World.getInstance().getClan(pc.getClanname());
			if (clan != null) {
				if (clan.getCastleId() == 0 && pc.getClanid() != 0) { // ������ �ƴϰ�
																		// ����
																		// ������
					total += eve ? CLN * 2 : CLN;
					pc.sendPackets(new S_SystemMessage("\\fY���Ϳ� �߰� ����: ���� ("
							+ (eve ? CLN * 2 : CLN) + ") ȹ��"), true);
				}
				if (clan.getCastleId() != 0) { // �����Ͻ�
					total += eve ? CAN * 2 : CAN;
					pc.sendPackets(new S_SystemMessage("\\fY������ �߰� ����: ���� ("
							+ (eve ? CAN * 2 : CAN) + ") ȹ��"), true);
				}
			}
			pc.getInventory().storeItem(41159, total); // �ź��� �������� ����
		}
	}
	

	private void DragonkeyTimeCheck(L1PcInstance pc, int count) {
		if (pc instanceof L1RobotInstance) {
			return;
		}
		long nowtime = System.currentTimeMillis();
		if (count == 1) {
			L1ItemInstance item = pc.getInventory().findItemId(
					L1ItemId.DRAGON_KEY);
			if (nowtime > item.getEndTime().getTime())
				pc.getInventory().removeItem(item);
		} else {
			L1ItemInstance[] itemList = pc.getInventory().findItemsId(
					L1ItemId.DRAGON_KEY);
			for (int i = 0; i < itemList.length; i++) {
				if (nowtime > itemList[i].getEndTime().getTime())
					pc.getInventory().removeItem(itemList[i]);
			}
			itemList = null;
		}
	}

	private class BuffStart implements Runnable {
		L1PcInstance player;
		L1SkillUse skilluse = new L1SkillUse();

		private void buff(L1PcInstance pc) {
			if (pc.isDead())
				return;

			long curtime = System.currentTimeMillis() / 1000;

			int[] allBuffSkill = { PHYSICAL_ENCHANT_DEX, PHYSICAL_ENCHANT_STR,
					BLESS_WEAPON, ADVANCE_SPIRIT, EARTH_SKIN };
			if (pc.getLevel() <= 65) {
				try {
					for (int i = 0; i < allBuffSkill.length; i++) {
						skilluse.handleCommands(pc, allBuffSkill[i],
								pc.getId(), pc.getX(), pc.getY(), null, 0,
								L1SkillUse.TYPE_GMBUFF);
						pc.setQuizTime(curtime);
					}
				} catch (Exception e) {
				}
			}
		}

		public void run() {
			try {
				Thread.sleep(5000);
				if (player != null) {
					if (!player.getSkillEffectTimerSet().hasSkillEffect(
							L1SkillId.PHYSICAL_ENCHANT_STR)
							|| !player.getSkillEffectTimerSet().hasSkillEffect(
									L1SkillId.PHYSICAL_ENCHANT_DEX)) {
						buff(player);
						Thread.sleep(3000);
					}
					S_DoActionGFX da = new S_DoActionGFX(player.getId(),
							ActionCodes.ACTION_SkillBuff);
					if (player.isCrown()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.GLOWING_AURA)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.GLOWING_AURA, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.SHINING_AURA)
								&& player.getLevel() >= 55) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.SHINING_AURA, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					} else if (player.isKnight()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.REDUCTION_ARMOR)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.REDUCTION_ARMOR, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							player.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.REDUCTION_ARMOR, 100000);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BOUNCE_ATTACK)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.BOUNCE_ATTACK, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							player.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.BOUNCE_ATTACK, 60000);
							Thread.sleep(7000);
						}
					} else if (player.isDarkelf()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.MOVING_ACCELERATION)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.MOVING_ACCELERATION,
									player.getId(), player.getX(),
									player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.UNCANNY_DODGE)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.UNCANNY_DODGE, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BURNING_SPIRIT)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.BURNING_SPIRIT, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.DOUBLE_BRAKE)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.DOUBLE_BRAKE, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.SHADOW_FANG)) {
							player.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.SHADOW_FANG, 300000);
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.SHADOW_FANG, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					} else if (player.isElf()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BLOODY_SOUL)) {
							player.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.BLOODY_SOUL, 15000);
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.BLOODY_SOUL, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
							player.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.BLOODY_SOUL, 15000);
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.BLOODY_SOUL, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
							player.getSkillEffectTimerSet().setSkillEffect(
									L1SkillId.BLOODY_SOUL, 15000);
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.BLOODY_SOUL, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					} else if (player.isDragonknight()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.BLOOD_LUST)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.BLOOD_LUST, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.DRAGON_SKIN)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.DRAGON_SKIN, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.MORTAL_BODY)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.MORTAL_BODY, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					} else if (player.isIllusionist()) {
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.CONCENTRATION)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player,
									L1SkillId.CONCENTRATION, player.getId(),
									player.getX(), player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.PATIENCE)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.PATIENCE,
									player.getId(), player.getX(),
									player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
						if (!player.getSkillEffectTimerSet().hasSkillEffect(
								L1SkillId.INSIGHT)) {
							Broadcaster.broadcastPacket(player, da);
							skilluse.handleCommands(player, L1SkillId.INSIGHT,
									player.getId(), player.getX(),
									player.getY(), null, 0,
									L1SkillUse.TYPE_GMBUFF);
							Thread.sleep(7000);
						}
					}
					RobotTeleport robotTeleport = RobotTable
							.getRobotTeleportList().get(
									CommonUtil.random(RobotTable
											.getRobotTeleportList().size()));
					L1Teleport.teleport(player, robotTeleport.x
							+ loc[CommonUtil.random(5)], robotTeleport.y
							+ loc[CommonUtil.random(5)],
							(short) robotTeleport.mapid, robotTeleport.heading);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
