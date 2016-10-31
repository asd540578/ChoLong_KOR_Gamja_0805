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

package l1j.server.server.model.skill;

import static l1j.server.server.model.skill.L1SkillId.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import l1j.server.Config;
import l1j.server.GameSystem.Antaras.AntarasRaid;
import l1j.server.GameSystem.Antaras.AntarasRaidSystem;
import l1j.server.GameSystem.Antaras.AntarasRaidTimer;
import l1j.server.GameSystem.Gamble.GambleInstance;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.ActionCodes;
import l1j.server.server.GeneralThreadPool;
import l1j.server.server.Opcodes;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.datatables.SprTable;
import l1j.server.server.model.Broadcaster;
import l1j.server.server.model.CharPosUtil;
import l1j.server.server.model.L1Attack;
import l1j.server.server.model.L1CastleLocation;
import l1j.server.server.model.L1Character;
import l1j.server.server.model.L1Cooking;
import l1j.server.server.model.L1Cube;
import l1j.server.server.model.L1CurseParalysis;
import l1j.server.server.model.L1EffectSpawn;
import l1j.server.server.model.L1HouseLocation;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Magic;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1PinkName;
import l1j.server.server.model.L1PolyMorph;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.L1War;
import l1j.server.server.model.L1World;
import l1j.server.server.model.L1토마호크;
import l1j.server.server.model.Instance.L1ArrowInstance;
import l1j.server.server.model.Instance.L1AuctionBoardInstance;
import l1j.server.server.model.Instance.L1BoardInstance;
import l1j.server.server.model.Instance.L1CrownInstance;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1DoorInstance;
import l1j.server.server.model.Instance.L1DwarfInstance;
import l1j.server.server.model.Instance.L1EffectInstance;
import l1j.server.server.model.Instance.L1FieldObjectInstance;
import l1j.server.server.model.Instance.L1FurnitureInstance;
import l1j.server.server.model.Instance.L1GuardInstance;
import l1j.server.server.model.Instance.L1HousekeeperInstance;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1LittleBugInstance;
import l1j.server.server.model.Instance.L1MerchantInstance;
import l1j.server.server.model.Instance.L1MonsterInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1NpcShopInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.Instance.L1PeopleInstance;
import l1j.server.server.model.Instance.L1PetInstance;
import l1j.server.server.model.Instance.L1ScarecrowInstance;
import l1j.server.server.model.Instance.L1SummonInstance;
import l1j.server.server.model.Instance.L1TeleporterInstance;
import l1j.server.server.model.Instance.L1TowerInstance;
import l1j.server.server.model.map.L1Map;
import l1j.server.server.model.map.L1WorldMap;
import l1j.server.server.model.poison.L1DamagePoison;
import l1j.server.server.model.trap.L1WorldTraps;
import l1j.server.server.serverpackets.S_ACTION_UI2;
import l1j.server.server.serverpackets.S_ChangeHeading;
import l1j.server.server.serverpackets.S_ChangeName;
import l1j.server.server.serverpackets.S_ChangeShape;
import l1j.server.server.serverpackets.S_CharVisualUpdate;
import l1j.server.server.serverpackets.S_ChatPacket;
import l1j.server.server.serverpackets.S_CloseList;
import l1j.server.server.serverpackets.S_CreateItem;
import l1j.server.server.serverpackets.S_CurseBlind;
import l1j.server.server.serverpackets.S_Dexup;
import l1j.server.server.serverpackets.S_Disconnect;
import l1j.server.server.serverpackets.S_DoActionGFX;
import l1j.server.server.serverpackets.S_DoActionShop;
import l1j.server.server.serverpackets.S_EffectLocation;
import l1j.server.server.serverpackets.S_HPUpdate;
import l1j.server.server.serverpackets.S_Invis;
import l1j.server.server.serverpackets.S_MPUpdate;
import l1j.server.server.serverpackets.S_Message_YN;
import l1j.server.server.serverpackets.S_NewCreateItem;
import l1j.server.server.serverpackets.S_NpcChatPacket;
import l1j.server.server.serverpackets.S_OtherCharPacks;
import l1j.server.server.serverpackets.S_OwnCharAttrDef;
import l1j.server.server.serverpackets.S_OwnCharStatus;
import l1j.server.server.serverpackets.S_OwnCharStatus2;
import l1j.server.server.serverpackets.S_PacketBox;
import l1j.server.server.serverpackets.S_Paralysis;
import l1j.server.server.serverpackets.S_Poison;
import l1j.server.server.serverpackets.S_PolyHtml;
import l1j.server.server.serverpackets.S_RangeSkill;
import l1j.server.server.serverpackets.S_RemoveObject;
import l1j.server.server.serverpackets.S_SPMR;
import l1j.server.server.serverpackets.S_SabuTell;
import l1j.server.server.serverpackets.S_ServerMessage;
import l1j.server.server.serverpackets.S_SkillBrave;
import l1j.server.server.serverpackets.S_SkillHaste;
import l1j.server.server.serverpackets.S_SkillIconAura;
import l1j.server.server.serverpackets.S_SkillIconBlessOfEva;
import l1j.server.server.serverpackets.S_SkillIconGFX;
import l1j.server.server.serverpackets.S_SkillIconShield;
import l1j.server.server.serverpackets.S_SkillIconWindShackle;
import l1j.server.server.serverpackets.S_SkillSound;
import l1j.server.server.serverpackets.S_Sound;
import l1j.server.server.serverpackets.S_Strup;
import l1j.server.server.serverpackets.S_SystemMessage;
import l1j.server.server.serverpackets.S_TrueTargetNew;
import l1j.server.server.serverpackets.S_UseAttackSkill;
import l1j.server.server.templates.L1BookMark;
import l1j.server.server.templates.L1Npc;
import l1j.server.server.templates.L1Skills;
import l1j.server.server.utils.CalcExp;
import l1j.server.server.utils.CalcStat;
import l1j.server.server.utils.L1SpawnUtil;

public class L1SkillUse {
	public static final int TYPE_NORMAL = 0;
	public static final int TYPE_LOGIN = 1;
	public static final int TYPE_SPELLSC = 2;
	public static final int TYPE_NPCBUFF = 3;
	public static final int TYPE_GMBUFF = 4;

	private L1Skills _skill;
	private int _skillId;
	private int _getBuffDuration;
	private int _shockStunDuration;
	private int _getBuffIconDuration;
	private int _targetID;
	private int _mpConsume = 0;
	private int _hpConsume = 0;
	private int _targetX = 0;
	private int _targetY = 0;
	private int _skillTime = 0;
	private int _type = 0;
	private boolean _isPK = false;
	private int _bookmarkMapId = 0;
	private int _bookmarkX = 0;
	private int _bookmarkY = 0;
	private int _itemobjid = 0;
	private boolean _checkedUseSkill = false;
	private int _leverage = 10;
	private boolean _isFreeze = false;
	private boolean _isCounterMagic = true;

	private L1Character _user = null;
	private L1Character _target = null;

	private L1PcInstance _player = null;
	private L1NpcInstance _npc = null;
	private L1NpcInstance _targetNpc = null;

	private int _calcType;
	private static final int PC_PC = 1;
	private static final int PC_NPC = 2;
	private static final int NPC_PC = 3;
	private static final int NPC_NPC = 4;
	private Random random = new Random(System.nanoTime());
	private ArrayList<TargetStatus> _targetList;
	private boolean _isGlanceCheckFail = false;
	private boolean USE_SPELLSC = false;

	private static Logger _log = Logger.getLogger(L1SkillUse.class.getName());

	public static Map<Integer, L1Object> _truetarget_list = new HashMap<Integer, L1Object>();

	private static final int[] CAST_WITH_INVIS = { 1, 2, 3, 5, 8, 9, 12, 13, 14, 19, 21, 26, 31, 32, 35, 37, 42, 43, 44,
			48, 49, 52, 54, 55, 57, 60, 61, 63, 67, 68, 69, 72, 73, 75, 78, 79, REDUCTION_ARMOR, BOUNCE_ATTACK,
			SOLID_CARRIAGE, COUNTER_BARRIER, 97, 98, 99, 100, 101, 102, 104, 105, 106, 107, 109, 110, 111, 113, 114,
			115, 116, 117, 118, 129, 130, 131, 133, 134, 137, 138, 146, 147, 148, 149, 150, 151, 155, 156, 158, 159,
			163, 164, 165, 166, 168, 169, 170, 171, 181, SOUL_OF_FLAME, ADDITIONAL_FIRE, IllUSION_OGRE, PATIENCE,
			IllUSION_DIAMONDGOLEM, IllUSION_LICH, IllUSION_AVATAR };

	// 카운터 매직으로 방어할수 없는 마법
	private static final int[] EXCEPT_COUNTER_MAGIC = { 1, 2, 3, 5, 8, 9, 12, 13, 14, 19, 21, 26, 31, 32, 35, 37, 42,
			43, 44, 48, 49, 52, 54, 55, 57, 60, 61, 63, 67, 68, 69, 72, 73, 75, 78, 79, SHOCK_STUN, 파워그립, 데스페라도,
			REDUCTION_ARMOR, BOUNCE_ATTACK, SOLID_CARRIAGE, COUNTER_BARRIER, 97, 98, 99, 100, 101, 102, 104, 105, 106,
			107, 109, 110, 111, 113, 114, 115, 116, 117, 118, 129, 130, 131, 132, 134, 137, 138, 146, 147, 148, 149,
			150, 151, 155, 156, 158, 159, 161, 163, 164, 165, 166, 168, 169, 170, 171, 181, SOUL_OF_FLAME,
			ADDITIONAL_FIRE, DRAGON_SKIN, FOU_SLAYER, SCALES_EARTH_DRAGON, SCALES_FIRE_DRAGON, SCALES_WATER_DRAGON,
			MIRROR_IMAGE, IllUSION_OGRE, PATIENCE, IllUSION_DIAMONDGOLEM, IllUSION_LICH, IllUSION_AVATAR, INSIGHT,
			10026, 10027, 10028, 10029, 30060, 30000, 30078, 30079, 30011, 30081, 30082, 30083, 30080, 31080, 30084,
			30010, 9278, 9279, 30002, 30086, 222204, 222205, 222206, 222207, 222208 };

	public L1SkillUse() {
	}

	private static class TargetStatus {
		private L1Character _target = null;
		// private boolean _isAction = false;
		// private boolean _isSendStatus = false;
		private boolean _isCalc = true;

		public TargetStatus(L1Character _cha) {
			_target = _cha;
		}

		public L1Character getTarget() {
			return _target;
		}

		public TargetStatus(L1Character _cha, boolean _flg) {
			_isCalc = _flg;
		}

		public boolean isCalc() {
			return _isCalc;
		}
	}

	public void setLeverage(int i) {
		_leverage = i;
	}

	public int getLeverage() {
		return _leverage;
	}

	private boolean isCheckedUseSkill() {
		return _checkedUseSkill;
	}

	private void setCheckedUseSkill(boolean flg) {
		_checkedUseSkill = flg;
	}

	public boolean checkUseSkill(L1PcInstance player, int skillid, int target_id, int x, int y, String message,
			int time, int type, L1Character attacker) {

		// 존재버그 관련 추가
		if (player instanceof L1PcInstance) {
			L1PcInstance jonje = L1World.getInstance().getPlayer(player.getName());
			if (jonje == null && player.getAccessLevel() != Config.GMCODE) {

				player.sendPackets(new S_SystemMessage("존재버그 강제종료! 재접속하세요"), true);

				player.sendPackets(new S_Disconnect(), true);

				return false;
			}
		}
		if (skillid == L1SkillId.FIRE_BLESS) {// 댄싱블레이즈 검착용 체
			if (player.getWeapon() == null) {
				player.sendPackets(new S_SystemMessage("마법 사용: 실패(성공하지 못함), 검 무기 착용 필요"));
				return false;
			} else {
				L1ItemInstance weapon = player.getWeapon();
				if (weapon.getItem().getType1() == 24) {
					player.sendPackets(new S_SystemMessage("마법 사용: 실패(성공하지 못함), 검 무기 착용 필요"));
					return false;
				}
			}
		}
		if (skillid == L1SkillId.COUNTER_BARRIER) {// 댄싱블레이즈 검착용 체
			if (player.getWeapon() == null) {
				player.sendPackets(new S_SystemMessage("기술 사용: 실패(성공하지 못함), 양손검 무기 착용 필요"));
				return false;
			} else {
				L1ItemInstance weapon = player.getWeapon();
				if (weapon.getItem().getType1() != 50) {
					player.sendPackets(new S_SystemMessage("기술 사용: 실패(성공하지 못함), 양손검 무기 착용 필요"));
					return false;
				}
			}
		}
		setCheckedUseSkill(true);
		_targetList = new ArrayList<TargetStatus>();

		_skill = SkillsTable.getInstance().getTemplate(skillid);
		if (_skill == null)
			return false;
		_skillId = skillid;
		_targetX = x;
		_targetY = y;
		_skillTime = time;
		_type = type;
		boolean checkedResult = true;

		if (attacker == null) {
			// pc
			_player = player;
			_user = _player;
		} else {
			// npc
			_npc = (L1NpcInstance) attacker;
			_user = _npc;
		}

		if (_skill.getTarget().equals("none") && skillid != SUMMON_MONSTER) {
			_targetID = _user.getId();
			if ((skillid == TELEPORT || skillid == MASS_TELEPORT) && _targetX != 0 && _targetY != 0) {

			} else {
				_targetX = _user.getX();
				_targetY = _user.getY();
			}
		} else {
			_targetID = target_id;
		}

		if (type == TYPE_NORMAL) {
			checkedResult = isNormalSkillUsable();
		} else if (type == TYPE_SPELLSC) {
			checkedResult = isSpellScrollUsable();
		} else if (type == TYPE_NPCBUFF) {
			checkedResult = true;
		}

		if (!checkedResult) {
			return false;
		}
		if (_skillId == SUMMON_MONSTER) {
			_target = _user;
			_calcType = PC_PC;
			makeTargetList();
			return true;
		}
		if (_skillId == FIRE_WALL || _skillId == LIFE_STREAM || _skillId == CUBE_IGNITION || _skillId == CUBE_QUAKE
				|| _skillId == CUBE_SHOCK || _skillId == CUBE_BALANCE) {
			return true;
		}

		L1Object l1object = L1World.getInstance().findObject(_targetID);

		if (l1object instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) l1object;
			if (pc.isTeleport() || pc.텔대기()) {
				return false;
			}
		}

		if (l1object == null && _skillId == TRUE_TARGET) {
			return true;
		}
		if (l1object instanceof L1LittleBugInstance) {
			return false;
		}
		if (l1object instanceof L1ItemInstance) {
			_log.fine("skill target item name: " + ((L1ItemInstance) l1object).getViewName());
			return false;
		}
		if (_user instanceof L1PcInstance) {
			if (l1object instanceof L1PcInstance) {
				_calcType = PC_PC;
			} else {
				_calcType = PC_NPC;
				_targetNpc = (L1NpcInstance) l1object;
			}
		} else if (_user instanceof L1NpcInstance) {
			if (l1object instanceof L1PcInstance) {
				_calcType = NPC_PC;
			} else if (_skill.getTarget().equals("none")) {
				_calcType = NPC_PC;
			} else {
				_calcType = NPC_NPC;
				_targetNpc = (L1NpcInstance) l1object;
			}
		}

		if (_skillId == TELEPORT || _skillId == MASS_TELEPORT || _skillId == TRUE_TARGET) {
			_bookmarkMapId = target_id;
			_bookmarkX = x;
			_bookmarkY = y;
		}

		if (_skillId == BRING_STONE || _skillId == BLESSED_ARMOR || _skillId == ENCHANT_WEAPON
				|| _skillId == SHADOW_FANG) {
			_itemobjid = target_id;
		}
		_target = (L1Character) l1object;

		if (!(_target instanceof L1MonsterInstance) && _skill.getTarget().equals("attack")
				&& _user.getId() != target_id) {
			_isPK = true;
		}

		if (!(l1object instanceof L1Character)) {
			checkedResult = false;
		}

		makeTargetList();

		if (_targetList.size() == 0 && (_user instanceof L1NpcInstance)) {
			checkedResult = false;
		}

		return checkedResult;
	}

	/**
	 * 통상의 스킬 사용시에 사용자 상태로부터 스킬이 사용 가능한가 판단한다
	 * 
	 * @return false 스킬이 사용 불가능한 상태인 경우
	 */

	private boolean isNormalSkillUsable() {
		if (_user instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) _user;
			if (pc.isParalyzed())
				return false;
			if ((pc.isInvisble() || pc.isInvisDelay()) && !isInvisUsableSkill(pc)) {
				if (_skillId != L1SkillId.AREA_OF_SILENCE && _skillId != L1SkillId.AQUA_PROTECTER) {
					return false;
				} else if (_skillId == L1SkillId.AREA_OF_SILENCE) {
					if (pc.isInvisble()) {
						pc.delInvis();
					}
				}
			}
			if (pc.getInventory().calcWeightpercent() >= 83
					&& !pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.RANKING_BUFF_4)) { // 중량
																								// 오버이면
																								// 스킬을
				// 사용할 수 없다
				pc.sendPackets(new S_ServerMessage(316), true);
				return false;
			}
			int polyId = pc.getGfxId().getTempCharGfx();
			L1PolyMorph poly = PolyTable.getInstance().getTemplate(polyId);
			if (poly != null && !poly.canUseSkill()) {
				pc.sendPackets(new S_ServerMessage(285), true);
				return false;
			}
			/** 2011.04.07 고정수 배틀존 */

			// /// 공성존에서 특정 마법 사용 못하게 -----------------------
			int castle_id = L1CastleLocation.getCastleIdByArea(pc);
			if (castle_id != 0) {
				if (_skillId == 69 || _skillId == 157) {
					pc.sendPackets(new S_SystemMessage("\\fU공성존에서는 해당마법을 사용 할 수 없습니다."), true);
					return false;
				}
			}

			if (_skillId == 69 || _skillId == 157) {
				L1Object l1object = L1World.getInstance().findObject(_targetID);
				if (l1object != null && l1object instanceof L1PcInstance) {
					int castle_id2 = L1CastleLocation.getCastleIdByArea((L1PcInstance) l1object);
					if (castle_id2 != 0) {
						pc.sendPackets(new S_SystemMessage("\\fU공성존에 있는 대상에게 해당마법을 사용 할 수 없습니다."), true);
						return false;
					}
				}
			}
			// /// 공성존에서 특정 마법 사용 못하게 -----------------------

			if (pc.getMapId() == 5302 || pc.getMapId() == 5153 || pc.getMapId() == 5490) {// 배틀존
				if (_skillId == 69 || _skillId == 50 || _skillId == 78 || _skillId == 71 || _skillId == 39
						|| _skillId == 20 || _skillId == 116 || _skillId == 33 || _skillId == 67 || _skillId == 61
						|| _skillId == 75 || _skillId == 118 || _skillId == 157 || _skillId == 44 || _skillId == 60) {
					// pc.sendPackets(new S_SystemMessage("배틀존에서는 시전이
					// 불가능합니다."));
					S_ChatPacket s_chatpacket = new S_ChatPacket(pc, "배틀존에서는 시전이 불가능합니다.", Opcodes.S_MESSAGE, 20);
					pc.sendPackets(s_chatpacket);
					return false;
				}
			}
			if (pc.getMapId() == 5001) {// 배틀존 대기실
				if (_skillId == 60 || _skillId == 67) {
					S_ChatPacket s_chatpacket = new S_ChatPacket(pc, "대기실에서는 시전이 불가능합니다.", Opcodes.S_MESSAGE, 20);
					pc.sendPackets(s_chatpacket);
					return false;
				}
			}
			if (pc.getMap().isSafetyZone(pc.getLocation())) {
				if (_skillId == 220 || _skillId == 215 || _skillId == 205 || _skillId == 11 || _skillId == 69) {
					pc.sendPackets(new S_SystemMessage("\\fU마을안에서는 사용할 수 없는 마법입니다."), true);
					return false;
				}
			}
			if (pc.getMapId() == 622) {// 깃털마을
				if (_skillId == 69) {
					pc.sendPackets(new S_SystemMessage("\\fU깃털 마을안에서는 사용할 수 없는 마법입니다."), true);
					return false;
				}
			}
			if (!isAttrAgrees()) {
				return false;
			}
			if (_skillId == ELEMENTAL_PROTECTION && pc.getElfAttr() == 0) {
				pc.sendPackets(new S_ServerMessage(280), true);
				return false;
			}

			if (pc.isSkillDelay()) {
				// System.out.println("딜레이");
				return false;
			}

			if (pc.getSkillEffectTimerSet().hasSkillEffect(SILENCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(AREA_OF_SILENCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_POISON_SILENCE)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(CONFUSION)) {
				if (!(_skillId >= SHOCK_STUN && _skillId <= COUNTER_BARRIER) && !(_skillId >= 226 && _skillId <= 230)) {
					pc.sendPackets(new S_ServerMessage(285), true);
					return false;
				}
			}

			if (isItemConsume() == false && !_player.isGm()) {
				_player.sendPackets(new S_ServerMessage(299), true);
				return false;
			}
		} else if (_user instanceof L1NpcInstance) {

			if (_user.getSkillEffectTimerSet().hasSkillEffect(CONFUSION))
				return false;

			if (_user.getSkillEffectTimerSet().hasSkillEffect(SILENCE)) {
				_user.getSkillEffectTimerSet().killSkillEffectTimer(SILENCE);
				return false;
			}
		}

		if (!isHPMPConsume()) {
			return false;
		}
		return true;
	}

	private boolean isSpellScrollUsable() {
		L1PcInstance pc = (L1PcInstance) _user;
		if (pc.isParalyzed())
			return false;
		if ((pc.isInvisble() || pc.isInvisDelay()) && !isInvisUsableSkill(pc)) {
			return false;
		}

		return true;
	}

	private boolean isInvisUsableSkill(L1PcInstance pc) {
		for (int skillId : CAST_WITH_INVIS) {
			if (skillId == _skillId) {
				return true;
			}
		}
		if (pc.getSkillEffectTimerSet().hasSkillEffect(BLIND_HIDING)) {
			if (_skillId == 어쌔신) {
				return true;
			}
		}
		return false;
	}

	public void handleCommands(L1PcInstance player, int skillId, int targetId, int x, int y, String message,
			int timeSecs, int type) {
		L1Character attacker = null;
		handleCommands(player, skillId, targetId, x, y, message, timeSecs, type, attacker);
	}

	private boolean useok = true;

	public void handleCommands(L1PcInstance player, int skillId, int targetId, int x, int y, String message,
			int timeSecs, int type, L1Character attacker) {

		try {
			USE_SPELLSC = false;
			boolean isUseSkill = true;
			if (!isCheckedUseSkill()) {
				isUseSkill = checkUseSkill(player, skillId, targetId, x, y, message, timeSecs, type, attacker);
				if (!isUseSkill) {
					failSkill();
					return;
				}
			}
			switch (type) {
			case TYPE_NORMAL:
				boolean imm = false;
				if (_target != null && isUseSkill && _user instanceof L1PcInstance) {
					if ((CharPosUtil.isAreaAttack(_player, _target.getX(), _target.getY(), _target.getMapId()) == false
							|| CharPosUtil.isAreaAttack(_target, _player.getX(), _player.getY(),
									_player.getMapId()) == false)) {
						if (_skillId == L1SkillId.IMMUNE_TO_HARM) {
							imm = true;
						}
					}
				}
				if ((!_isGlanceCheckFail && !imm) || _skill.getArea() > 0 || _skill.getTarget().equals("none")) {
					runSkill();
					useConsume();
					sendGrfx(true);
					sendFailMessageHandle();
					setDelay();
					pinkname();
				} else {
					if (isUseSkill && _user instanceof L1PcInstance) {
						int actionId = _skill.getActionId();
						int castgfx = _skill.getCastGfx();
						int targetid = _target.getId();
						if (castgfx > 0) {
							if (_skill.getTarget().equals("buff") && _skill.getType() != L1Skills.TYPE_HEAL) {
								S_DoActionGFX gfx = new S_DoActionGFX(_player.getId(), _skill.getActionId());
								_player.sendPackets(gfx);
								Broadcaster.broadcastPacket(_player, gfx, true);
							} else if (_skill.getTarget().equals("attack") && _skillId != 18) {
								boolean ck = false;
								if (isPcSummonPet(_target)) {
									if (CharPosUtil.getZoneType(_player) == 1 || CharPosUtil.getZoneType(_target) == 1
											|| _player.checkNonPvP(_player, _target)) {
										if (_skillId == 229) {
											_player.sendPackets(new S_UseAttackSkill(_player, _target), true);
											Broadcaster.broadcastPacket(_player, new S_UseAttackSkill(_player, _target),
													true);
										} else {
											_player.sendPackets(new S_UseAttackSkill(_player, 0, castgfx, _targetX,
													_targetY, actionId, true), true);
											Broadcaster.broadcastPacket(_player, new S_UseAttackSkill(_player, 0,
													castgfx, _targetX, _targetY, actionId, true), true);
										}
										ck = true;
									}
								}
								if (!ck) {
									if (_skillId == 229) {
										_player.sendPackets(new S_UseAttackSkill(_player, _target), true);
										Broadcaster.broadcastPacket(_player, new S_UseAttackSkill(_player, _target),
												true);
									} else {
										_player.sendPackets(new S_UseAttackSkill(_player, targetid, castgfx, _targetX,
												_targetY, actionId, true), true);
										Broadcaster.broadcastPacket(_player, new S_UseAttackSkill(_player, targetid,
												castgfx, _targetX, _targetY, actionId, true), true);
									}
								}
							} else {
								if (_skillId != 5 && _skillId != 69 && _skillId != 131) {
									if (actionId > 0) {
										S_DoActionGFX gfx = new S_DoActionGFX(_player.getId(), _skill.getActionId());
										_player.sendPackets(gfx);
										Broadcaster.broadcastPacket(_player, gfx, true);
									}
									if (_skillId == COUNTER_MIRROR
											// || _skillId == TRUE_TARGET
											|| _skillId == INVISIBILITY) {
									} else if (_skillId == TURN_UNDEAD)
										_player.sendPackets(new S_ServerMessage(280), true);
									else {
										S_SkillSound ss = new S_SkillSound(targetid, castgfx);
										_player.sendPackets(ss);
										Broadcaster.broadcastPacket(_player, ss, true);
									}
								}
							}
						}
					}
				}
				if (_user instanceof L1PcInstance) {
					if (!((L1PcInstance) _user).AttackCheckUseSKill) {
						((L1PcInstance) _user).AttackCheckUseSKill = true;
						((L1PcInstance) _user).AttackCheckUseSKillDelay = getRightInterval(((L1PcInstance) _user),
								SkillsTable.getInstance().getTemplate(skillId).getActionId());
						GeneralThreadPool.getInstance().schedule(new Runnable() {
							@Override
							public void run() {
								// TODO 자동 생성된 메소드 스텁
								((L1PcInstance) _user).AttackCheckUseSKill = false;
							}
						}, ((L1PcInstance) _user).AttackCheckUseSKillDelay / 4);
					}
				}
				break;
			case TYPE_LOGIN:
				runSkill();
				break;
			case TYPE_SPELLSC:
				USE_SPELLSC = true;
				runSkill();
				setDelay();
				sendFailMessageHandle();
				sendGrfx(true);
				if (_user instanceof L1PcInstance) {
					if (!((L1PcInstance) _user).AttackCheckUseSKill) {
						((L1PcInstance) _user).AttackCheckUseSKill = true;
						((L1PcInstance) _user).AttackCheckUseSKillDelay = getRightInterval(((L1PcInstance) _user),
								SkillsTable.getInstance().getTemplate(skillId).getActionId());
						GeneralThreadPool.getInstance().schedule(new Runnable() {
							@Override
							public void run() {
								// TODO 자동 생성된 메소드 스텁
								((L1PcInstance) _user).AttackCheckUseSKill = false;
							}
						}, ((L1PcInstance) _user).AttackCheckUseSKillDelay / 4);
					}
				}
				break;
			case TYPE_GMBUFF:
				runSkill();
				sendGrfx(false);
				break;
			case TYPE_NPCBUFF:
				runSkill();
				sendGrfx(true);
				break;
			default:
				break;
			}
			setCheckedUseSkill(false);
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("skillId : "+skillId+" / attacker :
			// "+attacker.getName());
			// _log.log(Level.SEVERE, "", e);
		}
	}

	private static Random _random = new Random(System.nanoTime());

	private void pinkname() {
		if ((_skill.getTarget().equals("buff") && _calcType == PC_PC) && CharPosUtil.getZoneType(_user) == 0
				&& CharPosUtil.getZoneType(_target) != 1) {
			if (_skill.getType() == L1Skills.TYPE_PROBABILITY || _skill.getType() == L1Skills.TYPE_CURSE) {
				if (_target instanceof L1PcInstance) {
					L1PcInstance target = (L1PcInstance) _target;
					L1PinkName.onAction(target, _user);
				}
			} else if (_skill.getType() == L1Skills.TYPE_HEAL || _skill.getType() == L1Skills.TYPE_CHANGE) {
				if (_target instanceof L1PcInstance) {
					L1PcInstance target = (L1PcInstance) _target;
					if (target.isPinkName()) {
						L1PinkName.onAction(target, _user);
					}
				}
			}
		}
	}

	private void failSkill() {
		setCheckedUseSkill(false);
		if (_skillId == TELEPORT || _skillId == MASS_TELEPORT || _skillId == TELEPORT_TO_MOTHER) {
			_player.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
		}
	}

	private boolean isTarget(L1Character cha) throws Exception {
		boolean _flg = false;

		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			if (pc.isGhost() || pc.isGmInvis()) {
				return false;
			}
		}

		if (_calcType == NPC_PC
				&& (cha instanceof L1PcInstance || cha instanceof L1PetInstance || cha instanceof L1SummonInstance)) {
			_flg = true;
		}

		if (cha instanceof L1DoorInstance) {
			if (cha.getMaxHp() == 0 || cha.getMaxHp() == 1) {
				return false;
			}
		}

		if ((_skill.getTarget().equals("attack") || _skill.getType() == L1Skills.TYPE_ATTACK) && _calcType == NPC_PC
				&& cha instanceof L1PcInstance && _user instanceof L1SummonInstance) {
			L1SummonInstance summon = (L1SummonInstance) _user;

			if (cha.getId() == summon.getMaster().getId()) {
				return false;
			}
			if (CharPosUtil.getZoneType(cha) == 1) {
				return false;
			}
		}

		if ((_skill.getTarget().equals("attack") || _skill.getType() == L1Skills.TYPE_ATTACK) && _calcType == NPC_PC
				&& cha instanceof L1PcInstance && _user instanceof L1PetInstance) {
			L1PetInstance pet = (L1PetInstance) _user;
			if (cha.getId() == pet.getMaster().getId()) {
				return false;
			}
			if (CharPosUtil.getZoneType(cha) == 1) {
				return false;
			}
		}

		if (cha instanceof L1DollInstance && _skillId != HASTE) {
			return false;
		}

		if (_skill.getSkillId() == RETURN_TO_NATURE && _calcType == PC_NPC && (CharPosUtil.getZoneType(_player) == 1
				|| CharPosUtil.getZoneType(_target) == 1 || _player.checkNonPvP(_player, _target))) {
			if (!_player.isGm())
				return false;
		}

		if (_calcType == PC_NPC && _target instanceof L1NpcInstance && !(_target instanceof L1PetInstance)
				&& !(_target instanceof L1SummonInstance)
				&& (cha instanceof L1PetInstance || cha instanceof L1SummonInstance || cha instanceof L1PcInstance)) {
			return false;
		}
		if (_calcType == PC_NPC && _target instanceof L1NpcInstance && !(_target instanceof L1GuardInstance)
				&& cha instanceof L1GuardInstance) {
			return false;
		}
		if ((_skill.getTarget().equals("attack") || _skill.getType() == L1Skills.TYPE_ATTACK) && _calcType == NPC_PC
				&& !(cha instanceof L1PetInstance) && !(cha instanceof L1SummonInstance)
				&& !(cha instanceof L1PcInstance)) {
			return false;
		}

		if ((_skill.getTarget().equals("attack") || _skill.getType() == L1Skills.TYPE_ATTACK) && _calcType == NPC_NPC
				&& _user instanceof L1MonsterInstance && cha instanceof L1MonsterInstance) {
			return false;
		}

		if (_skill.getTarget().equals("none") && _skill.getType() == L1Skills.TYPE_ATTACK
				&& (cha instanceof L1AuctionBoardInstance || cha instanceof L1BoardInstance
						|| cha instanceof L1CrownInstance || cha instanceof L1DwarfInstance
						|| cha instanceof L1EffectInstance || cha instanceof L1FieldObjectInstance
						|| cha instanceof L1FurnitureInstance || cha instanceof L1HousekeeperInstance
						|| cha instanceof L1MerchantInstance || cha instanceof L1TeleporterInstance)) {
			return false;
		}

		if (_skill.getType() == L1Skills.TYPE_ATTACK && cha.getId() == _user.getId()) {
			return false;
		}

		if (cha.getId() == _user.getId() && _skillId == HEAL_ALL) {
			return false;
		}

		if (((_skill.getTargetTo() & L1Skills.TARGET_TO_PC) == L1Skills.TARGET_TO_PC
				|| (_skill.getTargetTo() & L1Skills.TARGET_TO_CLAN) == L1Skills.TARGET_TO_CLAN
				|| (_skill.getTargetTo() & L1Skills.TARGET_TO_PARTY) == L1Skills.TARGET_TO_PARTY)
				&& cha.getId() == _user.getId() && _skillId != HEAL_ALL) {
			return true;
		}

		if (_user instanceof L1PcInstance
				&& (_skill.getTarget().equals("attack") || _skill.getType() == L1Skills.TYPE_ATTACK)
				&& _isPK == false) {
			if (cha instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) cha;
				if (_player.getId() == summon.getMaster().getId()) {
					return false;
				}
			} else if (cha instanceof L1PetInstance) {
				L1PetInstance pet = (L1PetInstance) cha;
				if (_player.getId() == pet.getMaster().getId()) {
					return false;
				}
			}
		}

		if ((_skill.getTarget().equals("attack") || _skill.getType() == L1Skills.TYPE_ATTACK)
				&& !(cha instanceof L1MonsterInstance) && !(cha instanceof L1PeopleInstance) && _isPK == false
				&& _target instanceof L1PcInstance) {
			L1PcInstance enemy = (L1PcInstance) cha;
			if (_skillId == COUNTER_DETECTION && CharPosUtil.getZoneType(enemy) != 1
					&& (cha.getSkillEffectTimerSet().hasSkillEffect(INVISIBILITY)
							|| cha.getSkillEffectTimerSet().hasSkillEffect(BLIND_HIDING))) {
				return true;
			}
			if (_player.getClanid() != 0 && enemy.getClanid() != 0) {
				for (L1War war : L1World.getInstance().getWarList()) {
					if (war.CheckClanInWar(_player.getClanname())) {
						if (war.CheckClanInSameWar(_player.getClanname(), enemy.getClanname())) {
							if (L1CastleLocation.checkInAllWarArea(enemy.getX(), enemy.getY(), enemy.getMapId())) {
								return true;
							}
						}
					}
				}
			}
			return false;
		}

		if ((CharPosUtil.isAreaAttack(_user, cha.getX(), cha.getY(), cha.getMapId()) == false
				|| CharPosUtil.isAreaAttack(cha, _user.getX(), _user.getY(), _user.getMapId()) == false)
				&& _skill.isThrough() == false) {
			if (!(_skill.getType() == L1Skills.TYPE_CHANGE || _skill.getType() == L1Skills.TYPE_RESTORE)) {
				_isGlanceCheckFail = true;
				return false;
			}
		}

		if ((cha.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE))
				&& (_skillId == ICE_LANCE || _skillId == SHOCK_STUN || _skillId == 데스페라도 || _skillId == 파워그립)) {
			return false;
		}

		if ((cha.getSkillEffectTimerSet().hasSkillEffect(MOB_BASILL) && _skillId == MOB_BASILL)
				|| (cha.getSkillEffectTimerSet().hasSkillEffect(MOB_COCA) && _skillId == MOB_COCA)) {
			return false;
		}

		if (cha.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
			if (_skillId != WEAPON_BREAK && _skillId != CANCELLATION // 확률계
					&& _skillId != 208 && _skillId != 217// 본브레이크, 패닉
					/* && _skill.getType() != L1Skills.TYPE_HEAL */// 힐 계
					&& _skill.getType() != L1Skills.TYPE_CHANGE) { // 버프 계
				return false;
			}
		}

		if (!(cha instanceof L1MonsterInstance) && (_skillId == TAMING_MONSTER || _skillId == CREATE_ZOMBIE)) {
			return false;
		}
		if (cha.isDead() && (_skillId != CREATE_ZOMBIE && _skillId != RESURRECTION && _skillId != GREATER_RESURRECTION
				&& _skillId != CALL_OF_NATURE)) {
			return false;
		}

		if (!(cha instanceof L1TowerInstance || cha instanceof L1DoorInstance) && cha.isDead() == false
				&& (_skillId == CREATE_ZOMBIE || _skillId == RESURRECTION || _skillId == GREATER_RESURRECTION
						|| _skillId == CALL_OF_NATURE)) {
			return false;
		}

		if (cha instanceof L1PcInstance) {
			L1PcInstance pc = (L1PcInstance) cha;
			if (pc.getSkillEffectTimerSet().hasSkillEffect(ABSOLUTE_BARRIER)
					|| pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_안전모드)) {
				if (_skillId == CURSE_BLIND || _skillId == WEAPON_BREAK || _skillId == DARKNESS || _skillId == WEAKNESS
						|| _skillId == DISEASE || _skillId == FOG_OF_SLEEPING || _skillId == MASS_SLOW
						|| _skillId == SLOW || _skillId == CANCELLATION || _skillId == SILENCE
						|| _skillId == DECAY_POTION || _skillId == MASS_TELEPORT || _skillId == DETECTION
						|| _skillId == HORROR_OF_DEATH || _skillId == COUNTER_DETECTION || _skillId == GUARD_BREAK
						|| _skillId == ERASE_MAGIC || _skillId == FREEZING_BREATH || _skillId == ENTANGLE
						|| _skillId == FEAR || _skillId == PHYSICAL_ENCHANT_DEX || _skillId == PHYSICAL_ENCHANT_STR
						|| _skillId == BLESS_WEAPON || _skillId == EARTH_SKIN || _skillId == IMMUNE_TO_HARM
						|| _skillId == REMOVE_CURSE || _skillId == CONFUSION || _skillId == MOB_SLOW_1
						|| _skillId == MOB_SLOW_18 || _skillId == MOB_WEAKNESS_1 || _skillId == MOB_DISEASE_1
						|| _skillId == MOB_BASILL || _skillId == MOB_SHOCKSTUN_30 || _skillId == MOB_RANGESTUN_19
						|| _skillId == MOB_RANGESTUN_18 || _skillId == MOB_DISEASE_30 || _skillId == MOB_WINDSHACKLE_1
						|| _skillId == MOB_COCA || _skillId == MOB_CURSEPARALYZ_19 || _skillId == MOB_CURSEPARALYZ_18
						|| _skillId == ANTA_SKILL_1 || _skillId == ANTA_SKILL_2 || _skillId == ANTA_SKILL_3 // 안타라스
																											// 용언
						|| _skillId == ANTA_SKILL_4 || _skillId == ANTA_SKILL_5 || _skillId == ANTA_SKILL_6
						|| _skillId == ANTA_SKILL_7 || _skillId == ANTA_SKILL_8 || _skillId == ANTA_SKILL_9
						|| _skillId == ANTA_SKILL_10 || _skillId == ANTA_SKILL_11 || _skillId == ANTA_SKILL_12
						|| _skillId == ANTA_SKILL_13 || _skillId == ANTA_SKILL_14 || _skillId == 40027
						|| _skillId == 데스힐) {
					return true;
				} else {
					return false;
				}
			}
		}

		if (cha instanceof L1NpcInstance) {
			int hiddenStatus = ((L1NpcInstance) cha).getHiddenStatus();
			if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK) {
				if (_skillId == DETECTION || _skillId == COUNTER_DETECTION || _skillId == FREEZING_BREATH) {
					return true;
				} else {
					return false;
				}
			} else if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_FLY) {
				return false;
			}
		}

		if ((_skill.getTargetTo() & L1Skills.TARGET_TO_PC) == L1Skills.TARGET_TO_PC && cha instanceof L1PcInstance) {
			_flg = true;
		} else if ((_skill.getTargetTo() & L1Skills.TARGET_TO_NPC) == L1Skills.TARGET_TO_NPC
				&& cha instanceof L1NpcInstance
				&& !(cha instanceof L1BoardInstance || cha instanceof L1ArrowInstance)) {
			_flg = true;
		} else if ((_skill.getTargetTo() & L1Skills.TARGET_TO_PET) == L1Skills.TARGET_TO_PET
				&& _user instanceof L1PcInstance) {
			if (cha instanceof L1SummonInstance) {
				if (_skill.getSkillId() == RETURN_TO_NATURE)
					_flg = true;
				L1SummonInstance summon = (L1SummonInstance) cha;
				if (summon.getMaster() != null) {
					if (_player.getId() == summon.getMaster().getId()) {
						_flg = true;
					}
				}
			}
			if (cha instanceof L1PetInstance) {
				L1PetInstance pet = (L1PetInstance) cha;
				if (pet.getMaster() != null) {
					if (_player.getId() == pet.getMaster().getId()) {
						_flg = true;
					}
				}
			}
		}

		if (_calcType == PC_PC) {
			if (CharPosUtil.getZoneType(_player) == 1 || CharPosUtil.getZoneType(cha) == 1) {
				if (_skillId == L1SkillId.CURE_POISON || _skillId == L1SkillId.MIND_BREAK || _skillId == L1SkillId.PANIC
						|| _skillId == L1SkillId.PHANTASM || _skillId == L1SkillId.CURSE_BLIND
						|| _skillId == L1SkillId.WEAPON_BREAK || _skillId == L1SkillId.CURSE_PARALYZE
						|| _skillId == L1SkillId.MANA_DRAIN || _skillId == L1SkillId.DARKNESS
						|| _skillId == L1SkillId.DISEASE || _skillId == L1SkillId.WEAKNESS
						|| _skillId == L1SkillId.SILENCE || _skillId == L1SkillId.FOG_OF_SLEEPING
						|| _skillId == L1SkillId.DECAY_POTION || _skillId == L1SkillId.MASS_SLOW
						|| _skillId == L1SkillId.EARTH_BIND || _skillId == L1SkillId.WIND_SHACKLE)
					_flg = false;
			}
		}

		if (_calcType == PC_PC && cha instanceof L1PcInstance) {
			if ((_skill.getTargetTo() & L1Skills.TARGET_TO_CLAN) == L1Skills.TARGET_TO_CLAN
					&& ((_player.getClanid() != 0 && _player.getClanid() == ((L1PcInstance) cha).getClanid())
							|| _player.isGm() || (_player.getMapId() >= 1005 && _player.getMapId() <= 1010)
							|| (_player.getMapId() >= 1011 && _player.getMapId() <= 1016)
							|| (_player.getMapId() >= 10000 && _player.getMapId() <= 10005))) {
				return true;
			}
			if ((_skill.getTargetTo() & L1Skills.TARGET_TO_PARTY) == L1Skills.TARGET_TO_PARTY
					&& (_player.getParty().isMember((L1PcInstance) cha) || _player.isGm())) {
				return true;
			}
		}
		return _flg;
	}

	private void makeTargetList() {
		try {
			if (_type == TYPE_LOGIN) {
				_targetList.add(new TargetStatus(_user));
				return;
			}

			if (_skill.getTargetTo() == L1Skills.TARGET_TO_ME
					&& (_skill.getType() & L1Skills.TYPE_ATTACK) != L1Skills.TYPE_ATTACK) {
				_targetList.add(new TargetStatus(_user));
				return;
			}

			if (_skill.getRanged() != -1) {
				if (_user.getLocation().getTileLineDistance(_target.getLocation()) > _skill.getRanged()) {
					return;
				}
			} else {
				if (!_user.getLocation().isInScreen(_target.getLocation())) {
					return;
				}
			}

			if (isTarget(_target) == false && !(_skill.getTarget().equals("none"))) {
				return;
			}

			if (_skillId == LIGHTNING) {
				for (L1Object tgobj : L1World.getInstance().getVisibleLineObjects(_user, _target)) {
					if (tgobj == null) {
						continue;
					}
					if (!(tgobj instanceof L1Character)) {
						continue;
					}
					L1Character cha = (L1Character) tgobj;
					if (isTarget(cha) == false) {
						continue;
					}
					_targetList.add(new TargetStatus(cha));
				}
				return;
			}

			if (_skill.getArea() == 0) {
				if (!CharPosUtil.isAreaAttack(_user, _target.getX(), _target.getY(), _target.getMapId())
						|| !CharPosUtil.isAreaAttack(_target, _user.getX(), _user.getY(), _user.getMapId())) {
					if ((_skill.getType() & L1Skills.TYPE_ATTACK) == L1Skills.TYPE_ATTACK) {
						_targetList.add(new TargetStatus(_target, false));
						return;
					}
				}
				_targetList.add(new TargetStatus(_target));
			} else {

				if (!_skill.getTarget().equals("none")) {
					_targetList.add(new TargetStatus(_target));
				}

				if (_skillId != 49
						&& !(_skill.getTarget().equals("attack") || _skill.getType() == L1Skills.TYPE_ATTACK)) {
					_targetList.add(new TargetStatus(_user));
				}

				List<L1Object> objects;
				if (_skill.getArea() == -1) {
					if (_user instanceof L1PcInstance)
						objects = _user.getNearObjects().getKnownObjects();
					else
						objects = L1World.getInstance().getVisibleObjects(_user);
				} else {
					if (_target instanceof L1PcInstance)
						objects = _target.getNearObjects().getVisibleObjects(_target, _skill.getArea());
					else
						objects = L1World.getInstance().getVisibleObjects(_target, _skill.getArea());
				}

				for (L1Object tgobj : objects) {
					try {
						if (tgobj == null) {
							continue;
						}
						if (!(tgobj instanceof L1Character)) {
							continue;
						}
						L1Character cha = (L1Character) tgobj;

						/* 범위 공격 마법 PC대상시 공성존 체크 */
						if (_skill.getTarget().equals("attack")) {
							if (_target instanceof L1PcInstance && cha instanceof L1PcInstance) {
								int castleId = L1CastleLocation.getCastleIdByArea(_target);
								if (castleId == 0)
									continue;
							}
						}
						if (_skill.getArea() == -1) {
							if (!CharPosUtil.isAreaAttack(_user, cha.getX(), cha.getY(), cha.getMapId())
									|| !CharPosUtil.isAreaAttack(cha, _user.getX(), _user.getY(), _user.getMapId())) {
								continue;
							}
						} else {
							if (!CharPosUtil.isAreaAttack(_target, cha.getX(), cha.getY(), cha.getMapId())
									|| !CharPosUtil.isAreaAttack(cha, _target.getX(), _target.getY(),
											_target.getMapId())) {
								continue;
							}
						}
						if (!isTarget(cha)) {
							continue;
						}
						_targetList.add(new TargetStatus(cha));
					} catch (Exception e) {
						// TODO 자동 생성된 catch 블록
						// e.printStackTrace();
					}
				}
				return;
			}

		} catch (Exception e) {
			_log.finest("exception in L1Skilluse makeTargetList" + e);
		}
	}

	private void sendHappenMessage(L1PcInstance pc) {
		int msgID = _skill.getSysmsgIdHappen();
		if (msgID > 0) {
			pc.sendPackets(new S_ServerMessage(msgID), true);
		}
	}

	private void sendFailMessageHandle() {
		if (_skill.getType() != L1Skills.TYPE_ATTACK && !_skill.getTarget().equals("none") && _targetList.size() == 0) {
			sendFailMessage();
		}
	}

	private void sendFailMessage() {
		int msgID = _skill.getSysmsgIdFail();
		if (msgID > 0 && (_user instanceof L1PcInstance)) {
			_player.sendPackets(new S_ServerMessage(msgID), true);
		}
	}

	private boolean isAttrAgrees() {
		int magicattr = _skill.getAttr();
		if (_user instanceof L1NpcInstance || _user instanceof L1RobotInstance) {
			return true;
		}

		if ((_skill.getSkillLevel() >= 17 && _skill.getSkillLevel() <= 22 && magicattr != 0)
				&& (magicattr != _player.getElfAttr() && !_player.isGm())) {
			return false;
		}
		return true;
	}

	private boolean isHPMPConsume() {
		int minusMP = 0;
		_mpConsume = _skill.getMpConsume();
		_hpConsume = _skill.getHpConsume();
		int currentMp = 0;
		int currentHp = 0;
		if (_user instanceof L1NpcInstance) {
			currentMp = _npc.getCurrentMp();
			currentHp = _npc.getCurrentHp();
		} else {
			currentMp = _player.getCurrentMp();
			currentHp = _player.getCurrentHp();
			if (!(_skillId >= SHOCK_STUN && _skillId <= COUNTER_BARRIER) && !(_skillId >= 225 && _skillId <= 230)) {

				int maxConsume = _skill.getMpConsume();

				double balance = CalcStat.엠소모감소(_player.getAbility().getTotalInt()) * 0.01;
				minusMP = (int) (maxConsume * balance);

				if (minusMP > 0)
					_mpConsume -= minusMP;
			}

			if ((_skillId == PHYSICAL_ENCHANT_DEX || _skillId == HASTE)
					&& (_player.getInventory().checkEquipped(20013) || _player.getInventory().checkEquipped(120013))) {
				_mpConsume /= 2;
			}
			if ((_skillId == HEAL || _skillId == EXTRA_HEAL)
					&& (_player.getInventory().checkEquipped(20014) || _player.getInventory().checkEquipped(120014))) {
				_mpConsume /= 2;
			}
			if ((_skillId == ENCHANT_WEAPON || _skillId == DETECTION || _skillId == PHYSICAL_ENCHANT_STR)
					&& (_player.getInventory().checkEquipped(20015) || _player.getInventory().checkEquipped(120015))) {
				_mpConsume /= 2;
			}
			if (_skillId == HASTE && _player.getInventory().checkEquipped(20008)) {
				_mpConsume /= 2;
			}
			if (_skillId == GREATER_HASTE && _player.getInventory().checkEquipped(20023)) {
				_mpConsume /= 2;
			}

			if (0 < _skill.getMpConsume()) {
				_mpConsume = Math.max(_mpConsume, 1);
			}
		}

		if (currentHp < _hpConsume + 1) {
			if (_user instanceof L1PcInstance) {
				_player.sendPackets(new S_ServerMessage(279), true);
			}
			return false;
		} else if (currentMp < _mpConsume) {
			if (_user instanceof L1PcInstance) {
				_player.sendPackets(new S_ServerMessage(278), true);
			}
			return false;
		}

		return true;
	}

	private boolean isItemConsume() {
		int itemConsume = _skill.getItemConsumeId();
		int itemConsumeCount = _skill.getItemConsumeCount();

		if (itemConsume == 0) {
			return true;
		}
		if (_player.getLevel() < 52) {
			int itemConsume2 = 0;
			if (itemConsume == 40318) {// 마돌
				itemConsume2 = 60138;
			} else if (itemConsume == 40319) { // 정령옥
				itemConsume2 = 60137;
			} else if (itemConsume == 40321) { // 흑요석
				itemConsume2 = 60139;
			} else if (itemConsume == 430007) { // 각인의 뼈조각
				itemConsume2 = 60140;
			} else if (itemConsume == 430006) { // 유그드라
				itemConsume2 = 60136;
			} else if (itemConsume == 430008) { // 속성석
				itemConsume2 = 60157;
			}
			if (itemConsume2 != 0) {
				if (_player.getInventory().checkItem(itemConsume2, itemConsumeCount)) {
					return true;
				}
			}
		}

		if (!_player.getInventory().checkItem(itemConsume, itemConsumeCount)) {
			return false;
		}

		return true;
	}

	private void useConsume() {
		if (_user instanceof L1NpcInstance) {
			int current_hp = _npc.getCurrentHp() - _hpConsume;
			_npc.setCurrentHp(current_hp);

			int current_mp = _npc.getCurrentMp() - _mpConsume;
			_npc.setCurrentMp(current_mp);
			return;
		}

		if (isHPMPConsume()) {
			if (_skillId == FINAL_BURN) {
				if (_player.getCurrentHp() >= 100)
					_player.setCurrentHp(100);
				else
					_player.setCurrentHp(1);
				_player.setCurrentMp(1);
			} else {
				int current_hp = _player.getCurrentHp() - _hpConsume;
				_player.setCurrentHp(current_hp);

				int current_mp = _player.getCurrentMp() - _mpConsume;
				_player.setCurrentMp(current_mp);
			}
		}

		int lawful = _player.getLawful() + _skill.getLawful();
		if (lawful > 32767) {
			lawful = 32767;
		}
		if (lawful < -32767) {
			lawful = -32767;
		}
		_player.setLawful(lawful);

		int itemConsume = _skill.getItemConsumeId();
		int itemConsumeCount = _skill.getItemConsumeCount();

		if (itemConsume == 0) {
			return;
		}

		if (_player.getLevel() <= 51) {
			int itemConsume2 = 0;
			if (itemConsume == 40318) {// 마돌
				itemConsume2 = 60138;
			} else if (itemConsume == 40319) { // 정령옥
				itemConsume2 = 60137;
			} else if (itemConsume == 40321) { // 흑요석
				itemConsume2 = 60139;
			} else if (itemConsume == 430007) { // 각인의 뼈조각
				itemConsume2 = 60140;
			} else if (itemConsume == 430006) { // 유그드라
				itemConsume2 = 60136;
			} else if (itemConsume == 430008) { // 속성석
				itemConsume2 = 60157;
			}
			if (itemConsume2 != 0) {
				if (_player.getInventory().checkItem(itemConsume2, itemConsumeCount)) {
					_player.getInventory().consumeItem(itemConsume2, itemConsumeCount);
					return;
				}
			}
		}
		_player.getInventory().consumeItem(itemConsume, itemConsumeCount);
	}

	private void addMagicList(L1Character cha, boolean repetition) {
		if (_skillTime == 0) {
			_getBuffDuration = _skill.getBuffDuration() * 1000;
			if (_skill.getBuffDuration() == 0) {
				if (_skillId == INVISIBILITY) {
					cha.getSkillEffectTimerSet().setSkillEffect(INVISIBILITY, 0);
				}
				return;
			}
		} else {
			_getBuffDuration = _skillTime * 1000;
		}

		if (_skillId == 데스힐 || _skillId == SHOCK_STUN || _skillId == 데스페라도 || _skillId == 파워그립) {
			_getBuffDuration = _shockStunDuration;
		}

		if (_skillId == CURSE_POISON || _skillId == CURSE_PARALYZE || _skillId == CURSE_PARALYZE2
				|| _skillId == SHAPE_CHANGE || _skillId == BLESSED_ARMOR || _skillId == HOLY_WEAPON
				|| _skillId == ENCHANT_WEAPON || _skillId == BLESS_WEAPON || _skillId == SHADOW_FANG) {
			return;
		}

		if ((_skillId == ICE_LANCE || _skillId == FREEZING_BREATH) && !_isFreeze) {
			return;
		}

		/*
		 * if(cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.토마호크)){
		 * System.out.println("333333333333"); }
		 */

		cha.getSkillEffectTimerSet().setSkillEffect(_skillId, _getBuffDuration);

		if (cha instanceof L1PcInstance && repetition) {
			L1PcInstance pc = (L1PcInstance) cha;
			sendIcon(pc);
		}
	}

	private void sendIcon(L1PcInstance pc) {
		if (_skillTime == 0) {
			_getBuffIconDuration = _skill.getBuffDuration();
		} else {
			_getBuffIconDuration = _skillTime;
		}
		switch (_skillId) {

		case SHIELD:
			pc.sendPackets(new S_SkillIconShield(2, _getBuffIconDuration), true);
			break;
		// case SHADOW_ARMOR: pc.sendPackets(new S_SkillIconShield(3,
		// _getBuffIconDuration), true); break;
		case DRESS_DEXTERITY:
			pc.sendPackets(new S_Dexup(pc, 2, _getBuffIconDuration), true);
			break;
		case DRESS_MIGHTY:
			pc.sendPackets(new S_Strup(pc, 2, _getBuffIconDuration), true);
			break;
		case GLOWING_AURA:
			pc.sendPackets(new S_SkillIconAura(113, _getBuffIconDuration), true);
			break;
		case SHINING_AURA:
			pc.sendPackets(new S_SkillIconAura(114, _getBuffIconDuration), true);
			break;
		case BRAVE_AURA:
			pc.sendPackets(new S_SkillIconAura(116, _getBuffIconDuration), true);
			break;
		case FIRE_WEAPON:
			pc.sendPackets(new S_SkillIconAura(147, _getBuffIconDuration), true);
			break;
		case WIND_SHOT:
			pc.sendPackets(new S_SkillIconAura(148, _getBuffIconDuration), true);
			break;
		case STORM_EYE:
			pc.sendPackets(new S_SkillIconAura(155, _getBuffIconDuration), true);
			break;
		// case EARTH_BLESS: pc.sendPackets(new S_SkillIconShield(7,
		// _getBuffIconDuration), true); break;
		case BURNING_WEAPON:
			pc.sendPackets(new S_SkillIconAura(162, _getBuffIconDuration), true);
			break;
		case STORM_SHOT:
			pc.sendPackets(new S_SkillIconAura(165, _getBuffIconDuration), true);
			break;
		case IRON_SKIN:
			pc.sendPackets(new S_SkillIconShield(10, _getBuffIconDuration), true);
			break;
		case EARTH_SKIN:
			pc.sendPackets(new S_SkillIconShield(6, _getBuffIconDuration), true);
			break;
		case PHYSICAL_ENCHANT_STR:
			pc.sendPackets(new S_Strup(pc, 5, _getBuffIconDuration), true);
			break;
		case PHYSICAL_ENCHANT_DEX:
			pc.sendPackets(new S_Dexup(pc, 5, _getBuffIconDuration), true);
			break;
		// case IMMUNE_TO_HARM: pc.sendPackets(new S_SkillIconGFX(40,
		// _getBuffIconDuration), true); break;
		case HASTE:
		case GREATER_HASTE:
			pc.sendPackets(new S_SkillHaste(pc.getId(), 1, _getBuffIconDuration), true);
			Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 1, 0), true);
			break;
		case HOLY_WALK:
		case MOVING_ACCELERATION:
		case WIND_WALK:
			pc.sendPackets(new S_SkillBrave(pc.getId(), 4, _getBuffIconDuration), true);
			Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 4, 0), true);
			break;
		case BLOOD_LUST:
			pc.sendPackets(new S_SkillBrave(pc.getId(), 1, _getBuffIconDuration), true);
			Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 1, 0), true);
			break;
		case SLOW:
		case MOB_SLOW_1:
		case MOB_SLOW_18:
		case MASS_SLOW:
		case ENTANGLE:
			pc.sendPackets(new S_SkillHaste(pc.getId(), 2, _getBuffIconDuration), true);
			Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 2, 0), true);
			break;
		default:
			break;
		}
		pc.sendPackets(new S_OwnCharStatus(pc));
		pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
	}

	private void sendGrfx(boolean isSkillAction) {
		int actionId = _skill.getActionId();
		int castgfx = _skill.getCastGfx();
		if (!useok) {
			return;
		}
		if (_user instanceof L1PcInstance) {
			if (castgfx == 0 && _skillId != INVISIBILITY) {
				return;
			}
			if (((L1PcInstance) _user).skillCritical) {
				((L1PcInstance) _user).skillCritical = false;
				if (_skillId == L1SkillId.DISINTEGRATE || _skillId == L1SkillId.SUNBURST)
					castgfx += 2;
				else if (_skillId == L1SkillId.ERUPTION || _skillId == L1SkillId.CONE_OF_COLD)
					castgfx += 4;
				else if (_skillId == L1SkillId.CALL_LIGHTNING)
					castgfx += 1;
			}
			if (_skillId == L1SkillId.UNCANNY_DODGE) {
				if (((L1PcInstance) _user).getAC().getAc() <= -100) {
					castgfx = 11766;
				}
			}
			if (_skillId == FIRE_WALL || _skillId == LIFE_STREAM || _skillId == CUBE_IGNITION || _skillId == CUBE_QUAKE
					|| _skillId == CUBE_SHOCK || _skillId == CUBE_BALANCE) {
				L1PcInstance pc = (L1PcInstance) _user;
				if (_skillId == FIRE_WALL) {
					pc.getMoveState().setHeading(CharPosUtil.targetDirection(pc, _targetX, _targetY));
					Broadcaster.broadcastPacket(pc, new S_ChangeHeading(pc), true);
				}
				S_DoActionGFX gfx = new S_DoActionGFX(pc.getId(), actionId);
				pc.sendPackets(gfx);
				Broadcaster.broadcastPacket(pc, gfx, true);
				return;
			}

			int targetid = _target.getId();

			if (_skillId == THUNDER_GRAB) {
				int ran2 = random.nextInt(60) + 10;
				if (_target instanceof L1PcInstance) {
					L1PcInstance target = (L1PcInstance) _target;
					// target.sendPackets(new S_SkillSound(target.getId(),
					// 6512));
					// Broadcaster.broadcastPacket(target, new
					// S_SkillSound(target.getId(), 6512));
					target.sendPackets(new S_SkillSound(target.getId(), 4184), true);
					Broadcaster.broadcastPacket(target, new S_SkillSound(target.getId(), 4184), true);
					if (isTargetCalc(target)) {
						if (!target.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
								&& !target.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
								&& !target.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)) {
							target.receiveDamage(_user, ran2, false);
						}
					}
				} else if (_target instanceof L1NpcInstance) {
					L1NpcInstance npc = (L1NpcInstance) _target;
					// Broadcaster.broadcastPacket(_target, new
					// S_SkillSound(_target.getId(), 6512));
					Broadcaster.broadcastPacket(_target, new S_SkillSound(_target.getId(), 4184), true);
					npc.receiveDamage(_user, ran2);
				}
			}
			/*
			 * case DESPERADO: // 공포 내성 if (_calcType == PC_PC || _calcType ==
			 * NPC_PC) { probability -= _targetPc.getResistance
			 * 
			 * ().gethorror(); } break;
			 */

			if (_skillId == 데스페라도) { // 데스페라도
				if (_target instanceof L1PcInstance) { // Gn.89
					L1PcInstance pc = (L1PcInstance) _target;
					pc.sendPackets(new S_SkillSound(pc.getId(), 12758), true);
					Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 12758), true);
				} else if (_target instanceof L1NpcInstance/*
															 * || _target
															 * instanceof
															 * L1SummonInstance
															 * || _target
															 * instanceof
															 * L1PetInstance ||
															 * _target
															 * instanceof
															 * L1DwarfInstance
															 * || _target
															 * instanceof
															 * L1TeleporterInstance
															 * || _target
															 * instanceof
															 * L1MerchantInstance
															 * || _target
															 * instanceof
															 * L1ScarecrowInstance
															 * || _target
															 * instanceof
															 * L1PeopleInstance
															 */) {
					Broadcaster.broadcastPacket(_target, new S_SkillSound(_target.getId(), 12758), true);
				}
			}
			if (_skillId == SHOCK_STUN || _skillId == MOB_SHOCKSTUN_30 || _skillId == MOB_RANGESTUN_19
					|| _skillId == MOB_RANGESTUN_18) {
				if (_targetList.size() == 0) {
					if (_target instanceof L1PcInstance) { // Gn.89
						L1PcInstance pc = (L1PcInstance) _target;
						pc.sendPackets(new S_SkillSound(pc.getId(), 4434), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 4434), true);
						// pc.sendPackets(new S_ServerMessage(280), true);
					} else if (_target instanceof L1NpcInstance) {
						Broadcaster.broadcastPacket(_target, new S_SkillSound(_target.getId(), 4434), true);
					}
					return;
				} else {
					if (_target instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) _target;
						pc.sendPackets(new S_SkillSound(pc.getId(), 4434), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 4434), true);
					} else if (_target instanceof L1NpcInstance) {
						Broadcaster.broadcastPacket(_target, new S_SkillSound(_target.getId(), 4434), true);
					}
					return;
				}
			}

			/*
			 * if (_skillId == AM_BREAK) { if (_targetList.size() == 0) {
			 * return; } else { if (_target instanceof L1PcInstance) {
			 * L1PcInstance pc = (L1PcInstance) _target; pc.sendPackets(new
			 * S_SkillSound(pc.getId(), 6551), true);
			 * Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(),
			 * 6551), true); } else if (_target instanceof L1NpcInstance) {
			 * Broadcaster.broadcastPacket(_target, new
			 * S_SkillSound(_target.getId(), 6551), true); } if (isSkillAction
			 * && actionId > 0) { S_DoActionGFX gfx = new
			 * S_DoActionGFX(_player.getId(), _skill.getActionId());
			 * _player.sendPackets(gfx); Broadcaster.broadcastPacket(_player,
			 * gfx, true); } return; } }
			 */

			/*
			 * if (_skillId == SMASH) { if (_targetList.size() == 0) { return; }
			 * else { if (_target instanceof L1PcInstance) { L1PcInstance pc =
			 * (L1PcInstance) _target; pc.sendPackets(new
			 * S_SkillSound(pc.getId(), 6972), true);
			 * Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(),
			 * 6972), true); } else if (_target instanceof L1NpcInstance) {
			 * Broadcaster.broadcastPacket(_target, new
			 * S_SkillSound(_target.getId(), 6972), true); } if (isSkillAction
			 * && actionId > 0) { S_DoActionGFX gfx = new
			 * S_DoActionGFX(_player.getId(), _skill.getActionId());
			 * _player.sendPackets(gfx); Broadcaster.broadcastPacket(_player,
			 * gfx, true); } return; } }
			 */

			if (_skillId == LIGHT) {
				L1PcInstance pc = (L1PcInstance) _target;
				pc.sendPackets(new S_Sound(145), true);
			}
			if (_targetList.size() == 0 && !(_skill.getTarget().equals("none"))) {
				int tempchargfx = _player.getGfxId().getTempCharGfx();
				if (tempchargfx == 5727 || tempchargfx == 5730) {
					actionId = ActionCodes.ACTION_SkillBuff;
				} else if (tempchargfx == 5733 || tempchargfx == 5736) {
					actionId = ActionCodes.ACTION_Attack;
				}
				if (isSkillAction && actionId > 0) {
					S_DoActionGFX gfx = new S_DoActionGFX(_player.getId(), actionId);
					_player.sendPackets(gfx);
					Broadcaster.broadcastPacket(_player, gfx, true);
				}
				return;
			}

			if (_skill.getTarget().equals("attack") && _skillId != 18) {
				if (isPcSummonPet(_target)) {
					if (CharPosUtil.getZoneType(_player) == 1 || CharPosUtil.getZoneType(_target) == 1
							|| _player.skillismiss == true || _player.checkNonPvP(_player, _target)) {
						_player.skillismiss = false;
						_player.sendPackets(new S_UseAttackSkill(_player, 0, castgfx, _targetX, _targetY, actionId),
								true);
						Broadcaster.broadcastPacket(_player,
								new S_UseAttackSkill(_player, 0, castgfx, _targetX, _targetY, actionId), true);
						return;
					}
				}
				if (_skill.getArea() == 0) {
					_player.sendPackets(new S_UseAttackSkill(_player, targetid, castgfx, _targetX, _targetY, actionId),
							true);
					Broadcaster.broadcastPacket(_player,
							new S_UseAttackSkill(_player, targetid, castgfx, _targetX, _targetY, actionId), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(targetid, ActionCodes.ACTION_Damage), _player, true);
				} else {
					L1Character[] cha = new L1Character[_targetList.size()];
					int i = 0;
					for (TargetStatus ts : _targetList) {
						cha[i] = ts.getTarget();
						i++;
					}
					_player.sendPackets(new S_RangeSkill(_player, cha, castgfx, actionId, S_RangeSkill.TYPE_DIR), true);
					Broadcaster.broadcastPacket(_player,
							new S_RangeSkill(_player, cha, castgfx, actionId, S_RangeSkill.TYPE_DIR), true);
				}
			} else if (_skill.getTarget().equals("none") && _skill.getType() == L1Skills.TYPE_ATTACK) {
				L1Character[] cha = new L1Character[_targetList.size()];
				int i = 0;
				for (TargetStatus ts : _targetList) {
					cha[i] = ts.getTarget();
					Broadcaster.broadcastPacketExceptTargetSight(cha[i],
							new S_DoActionGFX(cha[i].getId(), ActionCodes.ACTION_Damage), _player, true);
					i++;
				}
				_player.sendPackets(new S_RangeSkill(_player, cha, castgfx, actionId, S_RangeSkill.TYPE_NODIR), true);
				Broadcaster.broadcastPacket(_player,
						new S_RangeSkill(_player, cha, castgfx, actionId, S_RangeSkill.TYPE_NODIR), true);
			} else {
				if (_skillId != 5 && _skillId != 69 && _skillId != 131) {
					if (isSkillAction && actionId > 0) {
						S_DoActionGFX gfx = new S_DoActionGFX(_player.getId(), _skill.getActionId());
						_player.sendPackets(gfx);
						Broadcaster.broadcastPacket(_player, gfx, true);
					}
					if (_skillId == COUNTER_MIRROR) {
						S_SkillSound ss = new S_SkillSound(targetid, castgfx);
						_player.sendPackets(ss);
						Broadcaster.broadcastPacket(_player, ss, true);
					} else if (_skillId == INVISIBILITY) {
						S_Sound ss = new S_Sound(147);
						_player.sendPackets(ss);
						Broadcaster.broadcastPacket(_player, ss, true);
					} else if (_skillId == TRUE_TARGET) {
						return;
					} else if (_skillId == 어쌔신) {
						_player.sendPackets(new S_SkillSound(_player.getId(), castgfx));
						return;
					} else {
						/*
						 * if (_skillId == L1SkillId.IllUSION_LICH ) {
						 * System.out.println(_player.getName() +
						 * " > 리치 스킬 GFX 다른걸로 전송됨 > " + castgfx); castgfx =
						 * 10438; } else
						 * 
						 * if (_skillId == L1SkillId.PATIENCE && castgfx !=
						 * 6507) { System.out.println(_player.getName() +
						 * " > 페이션스 스킬 GFX 다른걸로 전송됨 > " + castgfx); castgfx =
						 * 6507; } else if (_skillId == L1SkillId.IllUSION_OGRE
						 * && castgfx != 10437) {
						 * System.out.println(_player.getName() +
						 * " > 오우거 스킬 GFX 다른걸로 전송됨 > " + castgfx); castgfx =
						 * 10437; } else if (_skillId == L1SkillId.CONCENTRATION
						 * && castgfx != 6527) {
						 * System.out.println(_player.getName() +
						 * " > 컨센트레이션 스킬 GFX 다른걸로 전송됨 > " + castgfx); castgfx =
						 * 6527; } else if (_skillId ==
						 * L1SkillId.IllUSION_DIAMONDGOLEM && castgfx != 10439)
						 * { System.out.println(_player.getName() +
						 * " > 다이아골렘 스킬 GFX 다른걸로 전송됨 > " + castgfx); castgfx =
						 * 10439; } else if (_skillId == L1SkillId.INSIGHT &&
						 * castgfx != 6505) {
						 * System.out.println(_player.getName() +
						 * " > 인사이트 스킬 GFX 다른걸로 전송됨 > " + castgfx); castgfx =
						 * 6505; } else if (_skillId ==
						 * L1SkillId.IllUSION_AVATAR && castgfx != 6575) {
						 * System.out.println(_player.getName() +
						 * " > 아바타 스킬 GFX 다른걸로 전송됨 > " + castgfx); castgfx =
						 * 6575; }
						 */
						_player.sendPackets(new S_SkillSound(targetid, castgfx), true);
						if (_skillId != ARMOR_BREAK)
							Broadcaster.broadcastPacket(_player, new S_SkillSound(targetid, castgfx), true);

						if (_skillId == GLOWING_AURA)
							_player.sendPackets(new S_SkillIconAura(113, _getBuffIconDuration), true);
						else if (_skillId == SHINING_AURA)
							_player.sendPackets(new S_SkillIconAura(114, _getBuffIconDuration), true);
						else if (_skillId == BRAVE_AURA)
							_player.sendPackets(new S_SkillIconAura(116, _getBuffIconDuration), true);
						else if (_skillId == CANCELLATION) {
							L1Object tempObj = L1World.getInstance().findObject(targetid);
							if (tempObj != null && tempObj instanceof L1PcInstance) {
								L1PcInstance tempPlayer = (L1PcInstance) tempObj;
								for (int skillNum = COOKING_BEGIN; skillNum <= COOKING_END; skillNum++) {
									if (tempPlayer.getSkillEffectTimerSet().hasSkillEffect(skillNum)) {
										int cookingType = 0;
										boolean ck = false;
										switch (skillNum) {
										case COOKING_1_0_N:// 괴물눈 스테이크
										case COOKING_1_0_S:
											cookingType = 0;
											break;
										case COOKING_1_1_N:// 곰고기 구이
										case COOKING_1_1_S:
											cookingType = 1;
											break;
										case COOKING_1_2_N:// 씨호떡
										case COOKING_1_2_S:
											cookingType = 2;
											break;
										case COOKING_1_3_N:// 개미다리치즈구이
										case COOKING_1_3_S:
											cookingType = 3;
											break;
										case COOKING_1_4_N:// 과일샐러드
										case COOKING_1_4_S:
											cookingType = 4;
											break;
										case COOKING_1_5_N:// 과일탕수육
										case COOKING_1_5_S:
											cookingType = 5;
											break;
										case COOKING_1_6_N:// 멧돼지 꼬치 구이
										case COOKING_1_6_S:
											cookingType = 6;
											break;
										case COOKING_1_7_N:// 버섯스프
										case COOKING_1_7_S:
											cookingType = 7;
											break;
										/** 1차요리 효과끝 */
										case COOKING_1_8_N:// 캐비어카나페
										case COOKING_1_8_S:
											cookingType = 16;
											break;
										case COOKING_1_9_N:// 악어스테이크
										case COOKING_1_9_S:
											cookingType = 17;
											break;
										case COOKING_1_10_N:// 터틀드래곤과자
										case COOKING_1_10_S:
											cookingType = 18;
											break;
										case COOKING_1_11_N:// 키위패롯구이
										case COOKING_1_11_S:
											cookingType = 19;
											break;
										case COOKING_1_12_N:// 스콜피온구이
										case COOKING_1_12_S:
											cookingType = 20;
											break;
										case COOKING_1_13_N:// 일렉카둠스튜
										case COOKING_1_13_S:
											cookingType = 21;
											break;
										case COOKING_1_14_N:// 거미다리꼬치구이
										case COOKING_1_14_S:
											cookingType = 22;
											break;
										case COOKING_1_15_N:// 크랩살스프
										case COOKING_1_15_S:
											cookingType = 23;
											break;
										/** 2차요리 효과끝 */
										case COOKING_1_16_N:// 시안집게발구이
										case COOKING_1_16_S:
											cookingType = 45;
											break;
										case COOKING_1_17_N:// 그리폰구이
										case COOKING_1_17_S:
											cookingType = 46;
											break;
										case COOKING_1_18_N:// 코카스테이크
										case COOKING_1_18_S:
											cookingType = 47;
											break;
										case COOKING_1_19_N:// 대왕거북구이
										case COOKING_1_19_S:
											cookingType = 48;
											break;
										case COOKING_1_20_N:// 레서날개꼬치
										case COOKING_1_20_S:
											cookingType = 49;
											break;
										case COOKING_1_21_N:// 드레이크구이
										case COOKING_1_21_S:
											cookingType = 50;
											break;
										case COOKING_1_22_N:// 심해어스튜
										case COOKING_1_22_S:
											cookingType = 51;
											break;
										case COOKING_1_23_N:// 바실스프
										case COOKING_1_23_S:
											cookingType = 52;
											break;
										default:
											ck = true;
											break;
										}
										if (!ck) {
											int time = tempPlayer.getSkillEffectTimerSet()
													.getSkillEffectTimeSec(skillNum);
											// System.out.println(skillNum+" >
											// "+cookingType+" > "+time);
											tempPlayer.sendPackets(new S_PacketBox(53, cookingType, time));
										}
									}
								}
								for (int skillNum = 3077; skillNum <= 3080; skillNum++) {
									if (tempPlayer.getSkillEffectTimerSet().hasSkillEffect(skillNum)) {
										int time = tempPlayer.getSkillEffectTimerSet().getSkillEffectTimeSec(skillNum);
										tempPlayer.getSkillEffectTimerSet().removeSkillEffect(skillNum);
										L1Cooking.newEatCooking(tempPlayer, skillNum, time);
									}
								}
								// if(tempPlayer.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_BARLOG)){
								// int time =
								// tempPlayer.getSkillEffectTimerSet().getSkillEffectTimeSec(STATUS_CURSE_BARLOG);
								// tempPlayer.sendPackets(new
								// S_PacketBox(S_PacketBox.ICON_AURA, 1, time));
								// }
								// if(tempPlayer.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_YAHEE)){
								// int time =
								// tempPlayer.getSkillEffectTimerSet().getSkillEffectTimeSec(STATUS_CURSE_YAHEE);
								// tempPlayer.sendPackets(new
								// S_PacketBox(S_PacketBox.ICON_AURA, 2, time));
								// }
								for (L1ItemInstance item : tempPlayer.getInventory().getItems()) {
									if (item != null && item.isEquipped() && item.getItem().isHasteItem()) {
										if (tempPlayer.getMoveState().getMoveSpeed() != 1) {
											tempPlayer.getMoveState().setMoveSpeed(1);
											tempPlayer.sendPackets(new S_SkillHaste(tempPlayer.getId(), 1, -1));
											Broadcaster.broadcastPacket(tempPlayer,
													new S_SkillHaste(tempPlayer.getId(), 1, 0));
										}
										break;
									}
								}
							}
						}
					}
				}

				for (TargetStatus ts : _targetList) {
					L1Character cha = ts.getTarget();
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						S_OwnCharStatus so = new S_OwnCharStatus(pc);
						pc.sendPackets(so, true);
					}
				}
			}
			if (_skillId == JOY_OF_PAIN || _skillId == DECREASE_WEIGHT) {
				_player.sendPackets(new S_NewCreateItem("무게", _player));
			}
		} else if (_user instanceof L1NpcInstance) {
			int targetid = _target.getId();

			if (_user instanceof L1MerchantInstance) {
				Broadcaster.broadcastPacket(_user, new S_SkillSound(targetid, castgfx), true);
				return;
			}

			if (_targetList.size() == 0 && !(_skill.getTarget().equals("none"))) {
				S_DoActionGFX gfx = new S_DoActionGFX(_user.getId(), _skill.getActionId());
				Broadcaster.broadcastPacket(_user, gfx, true);
				return;
			}

			if (_skill.getTarget().equals("attack") && _skillId == 40046) {
				GeneralThreadPool.getInstance().execute(new TargetLineSkillEffect(_user, _target, _skill.getCastGfx()));
				S_DoActionGFX gfx = new S_DoActionGFX(_user.getId(), _skill.getActionId());
				Broadcaster.broadcastPacket(_user, gfx, true);
				return;
			}

			if (_skill.getTarget().equals("attack") && _skillId != 18) {
				if (_skill.getArea() == 0) {
					if (castgfx == 0)
						Broadcaster.broadcastPacket(_user, new S_DoActionGFX(_user.getId(), actionId), true);
					else
						Broadcaster.broadcastPacket(_user,
								new S_UseAttackSkill(_user, targetid, castgfx, _targetX, _targetY, actionId), true);
					Broadcaster.broadcastPacketExceptTargetSight(_target,
							new S_DoActionGFX(targetid, ActionCodes.ACTION_Damage), _user, true);
				} else {
					L1Character[] cha = new L1Character[_targetList.size()];
					int i = 0;
					for (TargetStatus ts : _targetList) {
						cha[i] = ts.getTarget();
						Broadcaster.broadcastPacketExceptTargetSight(cha[i],
								new S_DoActionGFX(cha[i].getId(), ActionCodes.ACTION_Damage), _user, true);
						i++;
					}
					if (castgfx == 0) {
						Broadcaster.broadcastPacket(_user, new S_DoActionGFX(_user.getId(), actionId), true);
						for (L1Character cc : cha) {
							if (cc instanceof L1PcInstance) {
								L1PcInstance pp = (L1PcInstance) cc;
								pp.sendPackets(new S_DoActionGFX(pp.getId(), ActionCodes.ACTION_Damage), true);
							}
						}
					} else {
						/*
						 * if (_skillId == 10061) { ArrayList<L1PcInstance> pcs
						 * = new ArrayList<>(); for (L1PcInstance pc:
						 * L1World.getInstance().getVisiblePlayer(_user, 2)) {
						 * pcs.add(pc); }
						 * 
						 * cha = new L1Character[pcs.size()];
						 * 
						 * cha = (L1Character[]) pcs.toArray();
						 * 
						 * Broadcaster.broadcastPacket(_user, new
						 * S_RangeSkill(_user, cha, castgfx, actionId,
						 * S_RangeSkill.TYPE_NODIR), true);
						 */
						// }else{
						Broadcaster.broadcastPacket(_user,
								new S_RangeSkill(_user, cha, castgfx, actionId, S_RangeSkill.TYPE_DIR), true);
						// }

					}
				}
			} else if (_skill.getTarget().equals("none") && _skill.getType() == L1Skills.TYPE_ATTACK) {
				L1Character[] cha = new L1Character[_targetList.size()];
				int i = 0;
				for (TargetStatus ts : _targetList) {
					cha[i] = ts.getTarget();
					i++;
				}

				if (castgfx == 7987) {
					int x = _user.getX();
					int y = _user.getY();
					if (_user.getX() == 32862 && _user.getY() == 32861) {
						castgfx = 8050;
						x = _user.getX() - 5;
						y = _user.getY() + 5;
					} else if (_user.getX() == 32868 && _user.getY() == 32875) {
						castgfx = 8051;
						x = _user.getX() - 5;
						y = _user.getY();
					} else {
						x = _user.getX();
						y = _user.getY() + 5;
					}
					Broadcaster.broadcastPacket(_user,
							new S_RangeSkill(_user, x, y, cha, castgfx, actionId, S_RangeSkill.TYPE_NODIR), true);
				} else if (castgfx == 4326 && ((L1NpcInstance) _user).getNpcTemplate().get_npcId() == 45516) {
					int oriheading = _user.getMoveState().getHeading();
					_user.getMoveState().setHeading(1);
					Broadcaster.broadcastPacket(_user,
							new S_RangeSkill(_user, cha, castgfx, actionId, S_RangeSkill.TYPE_NODIR), true);
					_user.getMoveState().setHeading(oriheading);
					Broadcaster.broadcastPacket(_user, new S_ChangeHeading(_user), true);
				} else {
					if (castgfx == 0) {
						Broadcaster.broadcastPacket(_user, new S_DoActionGFX(_user.getId(), actionId), true);
						for (L1Character cc : cha) {
							if (cc instanceof L1PcInstance) {
								L1PcInstance pp = (L1PcInstance) cc;
								pp.sendPackets(new S_DoActionGFX(pp.getId(), ActionCodes.ACTION_Damage), true);
								Broadcaster.broadcastPacketExceptTargetSight(pp,
										new S_DoActionGFX(pp.getId(), ActionCodes.ACTION_Damage), _user, true);
							}
						}
					} else
						Broadcaster.broadcastPacket(_user,
								new S_RangeSkill(_user, cha, castgfx, actionId, S_RangeSkill.TYPE_NODIR), true);
				}
			} else {
				if (_skillId != 5 && _skillId != 69 && _skillId != 131) {
					S_DoActionGFX gfx = new S_DoActionGFX(_user.getId(), _skill.getActionId());
					Broadcaster.broadcastPacket(_user, gfx, true);
					if (castgfx != 0)
						Broadcaster.broadcastPacket(_user, new S_SkillSound(targetid, castgfx), true);
				}
			}
		}
	}

	private static final int[][] repeatedSkills = {
			// { HOLY_WEAPON, ENCHANT_WEAPON, BLESS_WEAPON, SHADOW_FANG },
			{ FIRE_WEAPON, WIND_SHOT, STORM_EYE, BURNING_WEAPON, STORM_SHOT },
			{ SHIELD, /* SHADOW_ARMOR, */EARTH_SKIN, /* EARTH_BLESS, */IRON_SKIN },
			{ HOLY_WALK, BLOOD_LUST, MOVING_ACCELERATION, WIND_WALK, STATUS_FRUIT, STATUS_BRAVE, STATUS_ELFBRAVE,
					FIRE_BLESS },
			{ HASTE, GREATER_HASTE, STATUS_HASTE }, { 기간틱, ADVANCE_SPIRIT }, { PHYSICAL_ENCHANT_DEX, DRESS_DEXTERITY },
			{ PHYSICAL_ENCHANT_STR, DRESS_MIGHTY },
			// { GLOWING_AURA, SHINING_AURA },
			{ SCALES_EARTH_DRAGON, SCALES_WATER_DRAGON, SCALES_FIRE_DRAGON },
			{ FEATHER_BUFF_A, FEATHER_BUFF_B, FEATHER_BUFF_C, FEATHER_BUFF_D },
			{ FAFU_MAAN, ANTA_MAAN, LIND_MAAN, VALA_MAAN, LIFE_MAAN, BIRTH_MAAN, SHAPE_MAAN } };

	private void deleteRepeatedSkills(L1Character cha) {
		for (int[] skills : repeatedSkills) {
			for (int id : skills) {
				if (id == _skillId) {
					stopSkillList(cha, skills);
				}
			}
		}
	}

	private void stopSkillList(L1Character cha, int[] repeat_skill) {
		for (int skillId : repeat_skill) {
			if (skillId != _skillId) {
				cha.getSkillEffectTimerSet().removeSkillEffect(skillId);
			}
		}
	}

	private void setDelay() {
		if (_skill.getReuseDelay() > 0) {
			// L1SkillDelay.onSkillUse(_user, _skill.getReuseDelay());
			_user.setSkillDelay(true);
			GeneralThreadPool.getInstance().schedule(new L1SkillDelay(_user, _skill.getReuseDelay()),
					_skill.getReuseDelay());
		}
	}

	private static final int[] BowGFX = { 138, 37, 3860, 3126, 3420, 2284,
			3105, 3145, 3148, 3151, 3871, 4125, 2323, 3892, 3895, 3898, 3901,
			4917, 4918, 4919, 4950, 10870, 10869, 10871, 6140, 6145, 6150,
			6155, 6269, 6272, 6275, 6278, 6826, 6827, 6836, 6837, 6846, 6847,
			6856, 6857, 6866, 6867, 6876, 6877, 6886, 6887, 6400, 5645, 6399,
			7039, 7040, 7041, 7140, 7144, 7148, 6160, 7152, 7156, 7160, 7164,
			7139, 7143, 7147, 7151, 7155, 7159, 7163, 7959, 7967, 7969, 7970,
			9214, 8900, 8913, 9225, 9226, 8804, 8808, 8792, 8798, 8786, 8860,
			8561, 8562, 8719, 8112, 11498, 13140, 13388, 13380, 11122, 11126,
			11331, 11342, 11352, 11353, 11362, 11363, 11369, 11378, 11382,
			11386, 11390, 11394, 11402, 11406, 11412, 11413, 11398, 10874,
			7967, 7846, 7848, 8719, 13631, 13635, 13723, 13725, 13346, 9615, 14927, 12314,
			15848, 15814, 15528, 15830, 15831 };
	
	private static final int[] FouGFX = { 138, 37, 3860, 3126, 3420, 2284, 3105, 3145, 3148, 3151, 3871, 4125, 2323,
			3892, 3895, 3898, 3901, 4917, 4918, 4919, 4950, 6140, 6145, 6150, 6155, 6269, 6272, 6275, 6278, 6826, 6827,
			6836, 6837, 6846, 6847, 6856, 6857, 6866, 6867, 6876, 6877, 6886, 6887, 6400, 5645, 6399, 7039, 7040, 7041,
			7140, 7144, 7148, 6160, 13140, 13389, 7959, 7967, 7969, 7970, 9214, 8900, 8913, 9225, 9226, 8804, 8808,
			13380, 8792, 8798, 8786, 8860, 9362, 9363, 9364, 9365, 4769 };

	private void runSkill() {
		if (_skillId == CUBE_IGNITION) {
			L1EffectInstance effect = L1EffectSpawn.getInstance().spawnEffect(4500501, _skill.getBuffDuration() * 1000,
					_user.getX(), _user.getY(), _user.getMapId());
			_player.getSkillEffectTimerSet().setSkillEffect(CUBE_IGNITION, _skill.getBuffDuration() * 1000);
			effect.setCubeTime(4);
			effect.setCubePc(_player);
			L1Cube.getInstance().add(0, effect);
			return;
		}

		if (_skillId == CUBE_QUAKE) {
			L1EffectInstance effect = L1EffectSpawn.getInstance().spawnEffect(4500502, _skill.getBuffDuration() * 1000,
					_user.getX(), _user.getY(), _user.getMapId());
			_player.getSkillEffectTimerSet().setSkillEffect(CUBE_QUAKE, _skill.getBuffDuration() * 1000);
			effect.setCubeTime(4);
			effect.setCubePc(_player);
			L1Cube.getInstance().add(1, effect);
			return;
		}

		if (_skillId == CUBE_SHOCK) {
			L1EffectInstance effect = L1EffectSpawn.getInstance().spawnEffect(4500503, _skill.getBuffDuration() * 1000,
					_user.getX(), _user.getY(), _user.getMapId());
			_player.getSkillEffectTimerSet().setSkillEffect(CUBE_SHOCK, _skill.getBuffDuration() * 1000);
			effect.setCubeTime(4);
			effect.setCubePc(_player);
			L1Cube.getInstance().add(2, effect);
			return;
		}

		if (_skillId == CUBE_BALANCE) {
			L1EffectInstance effect = L1EffectSpawn.getInstance().spawnEffect(4500504, _skill.getBuffDuration() * 1000,
					_user.getX(), _user.getY(), _user.getMapId());
			_player.getSkillEffectTimerSet().setSkillEffect(CUBE_BALANCE, _skill.getBuffDuration() * 1000);
			effect.setCubeTime(5);
			effect.setCubePc(_player);
			L1Cube.getInstance().add(3, effect);
			return;
		}

		if (_skillId == LIFE_STREAM) {
			L1EffectSpawn.getInstance().spawnEffect(81169, _skill.getBuffDuration() * 1000, _targetX, _targetY,
					_user.getMapId());
			return;
		}

		if (_skillId == FIRE_WALL) {
			L1EffectSpawn.getInstance().doSpawnFireWall(_user, _targetX, _targetY);
			return;
		}

		if (_skillId == 40048) { // 독구름
			L1EffectSpawn.getInstance().doSpawnPoisonClude(_user, _targetX, _targetY);
			return;
		} else if (_skillId == 40052) { // 에르자베 약한 독구름
			L1EffectSpawn.getInstance().doSpawnPoisonClude(_user, _targetX, _targetY, true);
			return;
		} else if (_skillId == 40055) { // 안타 전방 독구름
			L1EffectSpawn.getInstance().doSpawnAntaBreathPoisonClude(_user, _targetX, _targetY);
			return;
		}

		for (int skillId : EXCEPT_COUNTER_MAGIC) {
			if (_skillId == skillId) {
				_isCounterMagic = false;
				break;
			}
		}
		if (/* _skillId == 데스페라도|| */_skillId == SHOCK_STUN /*
															 * || _skillId ==
															 * BONE_BREAK ||
															 * _skillId ==
															 * AM_BREAK ||
															 * _skillId == SMASH
															 */
				&& _user instanceof L1PcInstance) {
			if (_target != _user)
				_target.onAction(_player);
		}

		if (!isTargetCalc(_target)) {
			return;
		}

		try {
			TargetStatus ts = null;
			L1Character cha = null;
			int dmg = 0;
			int drainMana = 0;
			int heal = 0;
			boolean isSuccess = false;
			int undeadType = 0;

			for (Iterator<TargetStatus> iter = _targetList.iterator(); iter.hasNext();) {
				ts = null;
				cha = null;
				dmg = 0;
				heal = 0;
				isSuccess = false;
				undeadType = 0;

				ts = iter.next();
				cha = ts.getTarget();
				if (!ts.isCalc() || !isTargetCalc(cha)) {
					continue;
				}
				L1Magic _magic = new L1Magic(_user, cha);
				_magic.setLeverage(getLeverage());

				if (cha instanceof L1MonsterInstance) {
					undeadType = ((L1MonsterInstance) cha).getNpcTemplate().get_undead();
				}

				if ((_skill.getType() == L1Skills.TYPE_CURSE || _skill.getType() == L1Skills.TYPE_PROBABILITY)
						&& isTargetFailure(cha)) {
					iter.remove();
					continue;
				}

				if (cha instanceof L1PcInstance) {
					if (_skillTime == 0) {
						_getBuffIconDuration = _skill.getBuffDuration();
					} else {
						_getBuffIconDuration = _skillTime;
					}
				}

				deleteRepeatedSkills(cha);

				if (_skill.getType() == L1Skills.TYPE_ATTACK && _user.getId() != cha.getId()) {
					if (isUseCounterMagic(cha)) {
						iter.remove();
						continue;
					}
					dmg = _magic.calcMagicDamage(_skillId);
					if (cha instanceof L1ScarecrowInstance) {
						if (_player.getLevel() < 5) {
							ArrayList<L1PcInstance> targetList = new ArrayList<L1PcInstance>();
							targetList.add(_player);
							ArrayList<Integer> hateList = new ArrayList<Integer>();
							hateList.add(1);
							CalcExp.calcExp(_player, cha.getId(), targetList, hateList, cha.getExp());
						}
						int heading = cha.getMoveState().getHeading();
						if (heading < 7)
							heading++;
						else
							heading = 0;
						cha.getMoveState().setHeading(heading);
						S_ChangeHeading ch = new S_ChangeHeading(cha);
						Broadcaster.broadcastPacket(cha, ch, true);
					}
					// _player.sendPackets(new
					// S_SystemMessage("마법 데미지 : "+dmg));
					// 공격 스킬일때!! 이레이즈 여부 판멸후 제거
					if (_skill.getSkillId() != L1SkillId.TRIPLE_ARROW && _skill.getSkillId() != L1SkillId.FOU_SLAYER) {
						if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC)) {
							cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
						}
					}
				} else if (_skill.getType() == L1Skills.TYPE_CURSE || _skill.getType() == L1Skills.TYPE_PROBABILITY) {
					isSuccess = _magic.calcProbabilityMagic(_skillId);
					// 이레 마법이 아니고 현제 이레중이라면!!!
					if (_skillId != ERASE_MAGIC && _skillId != EARTH_BIND) {
						if (cha.getSkillEffectTimerSet().hasSkillEffect(ERASE_MAGIC)) {
							cha.getSkillEffectTimerSet().removeSkillEffect(ERASE_MAGIC);
						}
					}
					if (_skillId != FOG_OF_SLEEPING) {
						cha.getSkillEffectTimerSet().removeSkillEffect(FOG_OF_SLEEPING);
					}
					if (_skillId != PHANTASM) {
						cha.getSkillEffectTimerSet().removeSkillEffect(PHANTASM);
					}
					if (isSuccess) {
						if (isUseCounterMagic(cha)) {
							iter.remove();
							continue;
						}
					} else {
						if (_skillId == FOG_OF_SLEEPING && cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_ServerMessage(297));
						}
						iter.remove();
						continue;
					}
				} else if (_skill.getType() == L1Skills.TYPE_HEAL) {
					if (cha.getSkillEffectTimerSet().hasSkillEffect(10518)
							|| cha.getSkillEffectTimerSet().hasSkillEffect(데스힐)) {
						dmg = _magic.calcHealing(_skillId);
					} else {
						dmg = -1 * _magic.calcHealing(_skillId);
						if (USE_SPELLSC) {
							if (_user instanceof L1PcInstance) {
								L1PcInstance up = (L1PcInstance) _user;
								if (!up.isWizard()) {
									if (up.isElf())
										dmg *= 0.8;
									else if (up.isIllusionist())
										dmg *= 0.7;
									else
										dmg *= 0.4;
								}
							}
						}
					}

					if (cha.getSkillEffectTimerSet().hasSkillEffect(WATER_LIFE)) {
						dmg *= 2;
					}

					if (cha.getSkillEffectTimerSet().hasSkillEffect(POLLUTE_WATER) || // /파푸
																						// 리듀스힐
							cha.getSkillEffectTimerSet().hasSkillEffect(10517)) {
						dmg /= 2;
					}
				}
				if (cha.getSkillEffectTimerSet().hasSkillEffect(_skillId) && _skillId != 토마호크 && _skillId != BONE_BREAK
						&& _skillId != 파워그립 && _skillId != 데스페라도 && _skillId != SHOCK_STUN && _skillId != THUNDER_GRAB
						&& _skillId != 데스힐 && _skillId != 앱솔루트블레이드 && _skillId != 디스트로이 && _skillId != 소울배리어
						&& _skillId != 타이탄라이징 && _skillId != 어쌔신 && _skillId != 임팩트) {
					addMagicList(cha, true);
					if (_skillId != SHAPE_CHANGE) {
						continue;
					}
				}

				// ●●●● PC, NPC 양쪽 모두 효과가 있는 스킬 ●●●●
				// GFX Check (Made by HuntBoy)
				switch (_skillId) {
				case MIRROR_IMAGE:
				case UNCANNY_DODGE:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addDg(-5);
					}
					break;
				case AREA_OF_SILENCE:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						int attackLevel = _user.getLevel();
						int defenseLevel = cha.getLevel();
						int probability = 0;
						if (attackLevel >= defenseLevel) {
							probability = (int) ((attackLevel - defenseLevel) * 5) + 50;
						} else if (attackLevel < defenseLevel) {
							probability = (int) ((attackLevel - defenseLevel) * 6) + 50;
						}
						if (probability > 90) {
							probability = 90;
						}
						if (random.nextInt(100) < probability) {
							if (_user != cha) {
								pc.sendPackets(new S_SkillSound(cha.getId(), 10708), true);
								Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), 10708), true);
							}
						} else {
							continue;
						}
					}
					break;
				case DRAGONBLOOD_A: {
					L1PcInstance pc = (L1PcInstance) cha;
					if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONBLOOD_A))
						pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_A);
					pc.getResistance().addWater(50);
					pc.getAC().addAc(-2);
					pc.sendPackets(new S_SPMR(pc), true);
					pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 82, _getBuffIconDuration / 60), true);
					pc.sendPackets(new S_SystemMessage("안타라스의 혈흔에 의해 강해진 느낌이 듭니다. 방어력 +2만큼 상승 효과가 있습니다."), true);
				}
					break;

				case DRAGONBLOOD_P: {

					L1PcInstance pc = (L1PcInstance) cha;
					if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONBLOOD_P))
						pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_P);
					pc.getResistance().addWind(50);
					pc.addHpr(3);
					pc.addMpr(1);
					pc.sendPackets(new S_SPMR(pc), true);
					pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 85, _getBuffIconDuration / 60), true);
					pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					pc.sendPackets(new S_SystemMessage("파푸리온의 혈흔에 의해 강해진 느낌이 듭니다. HP 회복율 +3, MP 회복율 +1만큼 상승 효과가 있습니다."),
							true);
				}
					break;// 시발
				case DRAGONBLOOD_L: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.DRAGONBLOOD_L))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.DRAGONBLOOD_L);
						pc.getAbility().addSp(1);
						pc.sendPackets(new S_SPMR(pc), true);
						pc.sendPackets(new S_PacketBox(S_PacketBox.DRAGONBLOOD, 88, _getBuffIconDuration / 60), true);
						pc.sendPackets(
								new S_SystemMessage("린드비오르의 혈흔에 의해 강해진 느낌이 듭니다. 주술력 +1, 마법치명타 +1%만큼 상승 효과가 있습니다."),
								true);
					}
				}
					break;

				case HEUKSA: {
					L1PcInstance pc = (L1PcInstance) cha;
					if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.HEUKSA))
						pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.HEUKSA);
					pc.addMaxHp(20);
					pc.addMaxMp(13);
					pc.getAC().addAc(-2);
					pc.getResistance().addBlind(10);
				}
					break;

				case HASTE:
				case 40028: {
					if (cha.getMoveState().getMoveSpeed() != 2) {
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							if (pc.getHasteItemEquipped() > 0) {
								continue;
							}
							if (pc.getSkillEffectTimerSet().hasSkillEffect(HASTE)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(HASTE);
								pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0), true);
								Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 0, 0), true);
								pc.getMoveState().setMoveSpeed(0);
							} else if (pc.getSkillEffectTimerSet().hasSkillEffect(GREATER_HASTE)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(GREATER_HASTE);
								pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0), true);
								Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 0, 0), true);
								pc.getMoveState().setMoveSpeed(0);
							} else if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HASTE)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_HASTE);
								pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0), true);
								Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 0, 0), true);
								pc.getMoveState().setMoveSpeed(0);
							}
							pc.setDrink(false);
							pc.sendPackets(new S_SkillHaste(pc.getId(), 1, _getBuffIconDuration), true);
						}
						Broadcaster.broadcastPacket(cha, new S_SkillHaste(cha.getId(), 1, 0), true);
						cha.getMoveState().setMoveSpeed(1);
					} else {
						int skillNum = 0;
						if (cha.getSkillEffectTimerSet().hasSkillEffect(SLOW)) {
							skillNum = SLOW;
						} else if (cha.getSkillEffectTimerSet().hasSkillEffect(MASS_SLOW)) {
							skillNum = MASS_SLOW;
						} else if (cha.getSkillEffectTimerSet().hasSkillEffect(ENTANGLE)) {
							skillNum = ENTANGLE;
						} else if (cha.getSkillEffectTimerSet().hasSkillEffect(MOB_SLOW_1)) {
							skillNum = MOB_SLOW_1;
						} else if (cha.getSkillEffectTimerSet().hasSkillEffect(MOB_SLOW_18)) {
							skillNum = MOB_SLOW_18;
						}
						if (skillNum != 0) {
							cha.getSkillEffectTimerSet().removeSkillEffect(skillNum);
							cha.getSkillEffectTimerSet().removeSkillEffect(_skillId);// HASTE
							cha.getMoveState().setMoveSpeed(0);
							continue;
						}
					}
				}
					break;
				case CURE_POISON: {
					cha.curePoison();
				}
					break;
				// ///// 크레이버프 추가 //////////
				case 사엘: {
					L1PcInstance pc = (L1PcInstance) cha;
					if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.크레이)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.크레이);
					}
					pc.addMaxHp(100);
					pc.addMaxMp(50);
					pc.addHpr(3);
					pc.addMpr(3);
					pc.getResistance().addWater(30);
					pc.addDmgup(1);
					pc.addBowDmgup(1);
					pc.addHitup(5);
					pc.addBowHitup(5);
					pc.addWeightReduction(40);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
					pc.sendPackets(new S_OwnCharStatus(pc), true);
				}
					break;
				case 크레이: {// 크레이 버프
					L1PcInstance pc = (L1PcInstance) cha;
					if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.사엘)) {
						pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.사엘);
					}
					pc.getAC().addAc(-8);
					pc.getResistance().addMr(20);
					pc.addMaxHp(200);
					pc.addMaxMp(100);
					pc.addHpr(3);
					pc.addMpr(3);
					pc.getResistance().addEarth(30);
					pc.addDmgup(3);
					pc.addBowDmgup(3);
					pc.addHitup(10);
					pc.addBowHitup(10);
					pc.addWeightReduction(40);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()));
					pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()));
					pc.sendPackets(new S_OwnCharStatus(pc));
				}
					break;
				// ////////////// 크레이버프 추가 //////////////
				case REMOVE_CURSE: {
					cha.curePoison();
					if (cha.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_PARALYZING)
							|| cha.getSkillEffectTimerSet().hasSkillEffect(STATUS_CURSE_PARALYZED)) {
						cha.cureParalaysis();
					}
					if (cha.getSkillEffectTimerSet().hasSkillEffect(CURSE_BLIND)
							|| cha.getSkillEffectTimerSet().hasSkillEffect(DARKNESS)) {
						if (cha.getSkillEffectTimerSet().hasSkillEffect(CURSE_BLIND)) {
							cha.getSkillEffectTimerSet().removeSkillEffect(CURSE_BLIND);
						} else if (cha.getSkillEffectTimerSet().hasSkillEffect(DARKNESS)) {
							cha.getSkillEffectTimerSet().removeSkillEffect(DARKNESS);
						}
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_CurseBlind(0), true);
						}
					}
				}
					break;
				case RESURRECTION:
				case GREATER_RESURRECTION: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (_player.getId() != pc.getId()) {
							if (L1World.getInstance().getVisiblePlayer(pc, 0).size() > 0) {
								for (L1PcInstance visiblePc : L1World.getInstance().getVisiblePlayer(pc, 0)) {
									if (!visiblePc.isDead()) {
										_player.sendPackets(new S_ServerMessage(592), true);
										return;
									}
								}
							}
							if (pc.getCurrentHp() == 0 && pc.isDead()) {
								if (pc.getMap().isUseResurrection()) {
									if (_skillId == RESURRECTION) {
										pc.setGres(false);
									} else if (_skillId == GREATER_RESURRECTION) {
										pc.setGres(true);
									}
									pc.setTempID(_player.getId());
									pc.sendPackets(new S_Message_YN(322, ""), true);
								}
							}
						}
					}
					if (cha instanceof L1NpcInstance) {
						if (!(cha instanceof L1TowerInstance)) {
							L1NpcInstance npc = (L1NpcInstance) cha;
							if (npc.getNpcTemplate().isCantResurrect() && npc.getMaster() == null) {
								return;
							} else if (npc.getNpcId() >= 100032 && npc.getNpcId() <= 100044) {
								return;
							}
							if (npc instanceof L1PetInstance
									&& L1World.getInstance().getVisiblePlayer(npc, 0).size() > 0) {
								for (L1PcInstance visiblePc : L1World.getInstance().getVisiblePlayer(npc, 0)) {
									if (!visiblePc.isDead()) {
										_player.sendPackets(new S_ServerMessage(592), true);
										return;
									}
								}
							}
							if (npc.getCurrentHp() == 0 && npc.isDead()) {
								npc.resurrect(npc.getMaxHp() / 4);
								npc.setResurrect(true);
							}
						}
					}
				}
					break;
				case CALL_OF_NATURE: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (_player.getId() != pc.getId()) {
							if (L1World.getInstance().getVisiblePlayer(pc, 0).size() > 0) {
								for (L1PcInstance visiblePc : L1World.getInstance().getVisiblePlayer(pc, 0)) {
									if (!visiblePc.isDead()) {
										_player.sendPackets(new S_ServerMessage(592), true);
										return;
									}
								}
							}
							if (pc.getCurrentHp() == 0 && pc.isDead()) {
								pc.setTempID(_player.getId());
								pc.sendPackets(new S_Message_YN(322, ""), true);
							}
						}
					}
					if (cha instanceof L1NpcInstance) {
						if (!(cha instanceof L1TowerInstance)) {
							L1NpcInstance npc = (L1NpcInstance) cha;
							if (npc instanceof L1PetInstance
									&& L1World.getInstance().getVisiblePlayer(npc, 0).size() > 0) {
								for (L1PcInstance visiblePc : L1World.getInstance().getVisiblePlayer(npc, 0)) {
									if (!visiblePc.isDead()) {
										_player.sendPackets(new S_ServerMessage(592), true);
										return;
									}
								}
							}
							if (npc.getCurrentHp() == 0 && npc.isDead()) {
								npc.resurrect(cha.getMaxHp());
								npc.resurrect(cha.getMaxMp() / 100);
								npc.setResurrect(true);
							}
						}
					}
				}
					break;
				case DETECTION:
				case FREEZING_BREATH: {
					if (cha instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						int hiddenStatus = npc.getHiddenStatus();
						if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK) {
							if (npc.getNpcId() != 45682) {
								npc.appearOnGround(_user);
							}
						}
					}
				}
					break;
				case COUNTER_DETECTION: {
					if (cha instanceof L1PcInstance) {
						dmg = _magic.calcMagicDamage(_skillId);
					} else if (cha instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						int hiddenStatus = npc.getHiddenStatus();
						if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK) {
							if (npc.getNpcId() != 45682)
								npc.appearOnGround(_player);
						} else {
							dmg = 0;
						}
					} else {
						dmg = 0;
					}
				}
					break;
				/*
				 * case JOY_OF_PAIN:{ int selldmg = _player.getMaxHp() -
				 * _player.getCurrentHp(); dmg = selldmg/5; } break;
				 */
				case MIND_BREAK: {
					if (_target.getCurrentMp() >= 5) {
						_target.setCurrentMp(_target.getCurrentMp() - 5);
						dmg = 25;
					} else {
						return;
					}
				}
					break;
				case TRUE_TARGET: {
					if (_user instanceof L1PcInstance) {
						L1PcInstance pri = (L1PcInstance) _user;
						pri.sendPackets(new S_TrueTargetNew(_targetID, true));
						if (_target instanceof L1PcInstance) {
							int step = pri.getLevel() / 15;
							L1PcInstance target = (L1PcInstance) _target;
							if (step > 0) {
								target.set트루타켓(step);
							}
						} else if (_target instanceof L1PcInstance) {
							int step = pri.getLevel() / 15;
							L1NpcInstance target = (L1NpcInstance) _target;
							if (step > 0) {
								target.set트루타켓(step);
							}
						}
						for (L1PcInstance pc : L1World.getInstance().getRecognizePlayer(_target)) {
							if (pri.getClanid() == pc.getClanid()) {
								pc.sendPackets(new S_TrueTargetNew(_targetID, true));
							}
						}
						// 이전에 시전한 트루타겟 찾아서 강제 종료 시키기.
						synchronized (_truetarget_list) {
							L1Object temp = _truetarget_list.remove(_user.getId());
							if (temp != null && temp instanceof L1Character) {
								L1Character temp2 = (L1Character) temp;
								temp2.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.TRUE_TARGET);
							}
						}
						// 트루타겟 활성화.
						_target.getSkillEffectTimerSet().setSkillEffect(L1SkillId.TRUE_TARGET, 16 * 1000);
						synchronized (_truetarget_list) {
							_truetarget_list.put(_user.getId(), _target);
						}
					}
				}
					break;
				case ELEMENTAL_FALL_DOWN: {
					if (_user instanceof L1PcInstance) {
						int playerAttr = _player.getElfAttr();
						int i = -50;
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							switch (playerAttr) {
							case 0:
								_player.sendPackets(new S_ServerMessage(79), true);
								break;
							case 1:
								pc.getResistance().addEarth(i);
								pc.setAddAttrKind(1);
								break;
							case 2:
								pc.getResistance().addFire(i);
								pc.setAddAttrKind(2);
								break;
							case 4:
								pc.getResistance().addWater(i);
								pc.setAddAttrKind(4);
								break;
							case 8:
								pc.getResistance().addWind(i);
								pc.setAddAttrKind(8);
								break;
							default:
								break;
							}
						} else if (cha instanceof L1MonsterInstance) {
							L1MonsterInstance mob = (L1MonsterInstance) cha;
							switch (playerAttr) {
							case 0:
								_player.sendPackets(new S_ServerMessage(79), true);
								break;
							case 1:
								mob.getResistance().addEarth(i);
								mob.setAddAttrKind(1);
								break;
							case 2:
								mob.getResistance().addFire(i);
								mob.setAddAttrKind(2);
								break;
							case 4:
								mob.getResistance().addWater(i);
								mob.setAddAttrKind(4);
								break;
							case 8:
								mob.getResistance().addWind(i);
								mob.setAddAttrKind(8);
								break;
							default:
								break;
							}
						}
					}
				}
					break;
				case IMMUNE_TO_HARM: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.isWizard() || pc.isElf()) {
							pc._이뮨어그로 = _player;
						}
					}
				}
					break;
				case HEAL:
				case EXTRA_HEAL:
				case GREATER_HEAL:
				case FULL_HEAL:
				case HEAL_ALL:
				case NATURES_TOUCH:
				case NATURES_BLESSING: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.isWizard() || pc.isElf()) {
							pc._healagro = _player;
						}
					}

					if (cha.getSkillEffectTimerSet().hasSkillEffect(WATER_LIFE)) {
						cha.getSkillEffectTimerSet().killSkillEffectTimer(WATER_LIFE);
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_PacketBox(S_PacketBox.DEL_ICON), true);
						}
					}

				}
					break;
				case CHILL_TOUCH:
				case VAMPIRIC_TOUCH: {
					heal = dmg;
				}
					break;
				case TRIPLE_ARROW: {
					boolean gfxcheck = false;

					int playerGFX = _player.getGfxId().getTempCharGfx();
					for (int gfx : BowGFX) {
						if (playerGFX == gfx) {
							gfxcheck = true;
							break;
						}
					}
					if (!gfxcheck) {
						return;
					}

					for (int i = 3; i > 0; i--) {
						_target.onAction(_player);
					}
					_player.sendPackets(new S_SkillSound(_player.getId(), 15103), true);
					Broadcaster.broadcastPacket(_player, new S_SkillSound(_player.getId(), 15103), true);
				}
					break;

				case FOU_SLAYER: {
					boolean gfxcheck = false;

					int playerGFX = _player.getGfxId().getTempCharGfx();

					for (int gfx : FouGFX) {
						if (playerGFX != gfx) {
							gfxcheck = true;
							break;
						}
					}

					if (!gfxcheck) {
						return;
					}

					// 약점 노출 리셋
					_player.ChainSwordObjid = 0;

					if (_player.getSkillEffectTimerSet().hasSkillEffect(STATUS_SPOT3)) {
						_player.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_SPOT3);
						S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 0);
						_player.sendPackets(pb, true);
						dmg += 60;
					} else if (_player.getSkillEffectTimerSet().hasSkillEffect(STATUS_SPOT2)) {
						_player.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_SPOT2);
						S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 0);
						_player.sendPackets(pb, true);
						dmg += 40;
					} else if (_player.getSkillEffectTimerSet().hasSkillEffect(STATUS_SPOT1)) {
						_player.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_SPOT1);
						S_PacketBox pb = new S_PacketBox(S_PacketBox.SPOT, 0);
						_player.sendPackets(pb, true);
						dmg += 20;
						for (L1DollInstance doll : _player.getDollList()) {
							dmg += doll.fou_DamageUp();
						}
					}
					try {

						for (int i = 3; i > 0; i--) {
							_target.onAction(_player, dmg);
						}

						dmg = 0;

					} catch (Exception e) {
						e.printStackTrace();
					}

					_player.sendPackets(new S_SkillSound(_player.getId(), 7020), true);
					_player.sendPackets(new S_SkillSound(_targetID, 6509), true);
					Broadcaster.broadcastPacket(_player, new S_SkillSound(_player.getId(), 7020), true);
					Broadcaster.broadcastPacket(_player, new S_SkillSound(_targetID, 6509), true);
				}
					break;

				/** 혈맹버프 **/
				case CLAN_BUFF1: {// 일반 공격 태세
					L1PcInstance pc = (L1PcInstance) cha;
					pc.addDmgupByArmor(2);
					pc.addBowDmgupByArmor(2);
					pc.sendPackets(new S_ACTION_UI2(2724, pc.getClan().getBuffTime()[pc.getClan().getBless() - 1], 7,
							7233, 4650));
					pc.sendPackets(new S_ServerMessage(4618, "$22503"));
					pc.sendPackets(new S_SkillSound(pc.getId(), 14482));
					// System.out.println("케릭터 수치값은?? : " +
					// pc.getDmgupByArmor());
				}
					break;
				case CLAN_BUFF2: {// 일반 방어 태세
					L1PcInstance pc = (L1PcInstance) cha;
					pc.getAC().addAc(-3);
					pc.sendPackets(new S_OwnCharAttrDef(pc));
					pc.sendPackets(new S_ACTION_UI2(2725, pc.getClan().getBuffTime()[pc.getClan().getBless() - 1], 7,
							7235, 4651));
					pc.sendPackets(new S_ServerMessage(4618, "$22504"));
					pc.sendPackets(new S_SkillSound(pc.getId(), 14482));
				}
					break;
				case CLAN_BUFF3: {// 전투 공격 태세
					L1PcInstance pc = (L1PcInstance) cha;// 따로 없으면 그냥 이렇게 하셔도
															// 되요.
					// pc.addPvPDmgup(1); //pvp 추가데미지
					pc.sendPackets(new S_ACTION_UI2(2726, pc.getClan().getBuffTime()[pc.getClan().getBless() - 1], 7,
							7237, 4652));
					pc.sendPackets(new S_ServerMessage(4618, "$22505"));
					pc.sendPackets(new S_SkillSound(pc.getId(), 14482));
				}
					break;
				case CLAN_BUFF4: {// 전투 방어 태세
					L1PcInstance pc = (L1PcInstance) cha;
					// pc.addDmgReducPvp(1); //pvp 리덕
					pc.sendPackets(new S_ACTION_UI2(2727, pc.getClan().getBuffTime()[pc.getClan().getBless() - 1], 7,
							7239, 4653));
					pc.sendPackets(new S_ServerMessage(4618, "$22506"));
					pc.sendPackets(new S_SkillSound(pc.getId(), 14482));
				}
					break;
				case 10026:
				case 10027:
				case 10028:
				case 10029: {
					if (_user instanceof L1NpcInstance) {
						Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "$3717", 0), true);
					} else {
						Broadcaster.broadcastPacket(_player, new S_ChatPacket(_player, "$3717", 0, 0), true);
					}
				}
					break;
				case 10057: {
					L1Teleport.teleportToTargetFront(cha, _user, 1);
				}
					break;

				case ANTA_SKILL_1: { // 안타라스(리뉴얼) - 용언 스킬1
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "$7914", 0), true);
							// 오브 모크! 세이 리라프[무기손상-웨폰브레이크 + 굳기]
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 10, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;
				case ANTA_SKILL_2: { // 용언스킬 2 티 세토르
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "$7948", 0), true);
							// 오브 모크! 티 세토르[유저들 위치 변경 유니드래곤스폰]
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 11, 2000);// 2초
							antaendtime.begin();
						}
					}
				}

					break;
				case ANTA_SKILL_3: { // 용언스킬 3 뮤즈삼
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "$7903", 0), true);
							// 오브 모크! 뮤즈삼 점프+스턴;
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 12, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;
				case ANTA_SKILL_4: { // 용언스킬 3 너츠삼
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "$7909", 0), true);
							// 오브 모크! 너츠삼 운석+스턴;
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 13, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;

				case ANTA_SKILL_5: { // 용언스킬 3 너츠삼
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "$7915", 0), true);
							// 오브 모크! 티프삼 점프+스턴+운석;
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 14, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;

				case ANTA_SKILL_6: { // 용언스킬 리라프
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "오브 모크! 리라프..", 0), true);
							// 오브 모크! 리라프 웨폰;
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 15, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;
				case ANTA_SKILL_7: { // 용언스킬 켄 로우
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "오브 모크! 켄 로우..", 0), true);
							// 오브 모크! 켄 로우 웨폰;
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 16, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;
				case ANTA_SKILL_8: { // 안타라스(리뉴얼) - 용언 스킬8
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "오브 모크! 티기르..", 0), true);
							// 오브 모크! 티기르 원투+고함;
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 17, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;
				case ANTA_SKILL_9: { // 안타라스(리뉴얼) - 용언 스킬9
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "오브 모크! 켄 티기르..", 0), true);
							// 오브 모크! 켄 티기르 원투+브레스;
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 18, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;

				case ANTA_SKILL_10: { // 안타라스(리뉴얼) - 용언 스킬9
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "오브 모크! 루오 타..", 0), true);
							// 오브 모크! 루오 타 독구름+고함;
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 19, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;

				case ANTA_SKILL_11: { // 안타라스(리뉴얼) - 용언 스킬9
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "오브 모크! 케네시..", 0), true);
							// 오브 모크! 케네시 캔슬+마비
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 20, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;
				case ANTA_SKILL_12: { // 안타라스(리뉴얼) - 용언 스킬9
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "오브 모크! 뮤즈 심..", 0), true);
							// 오브 모크! 뮤즈심 뮤즈 전단계
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 21, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;
				case ANTA_SKILL_13: { // 안타라스(리뉴얼) - 용언 스킬9
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "오브 모크! 너츠 심..", 0), true);
							// 오브 모크! 너츠심
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 22, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;
				case 앱솔루트블레이드: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_CreateItem(앱솔루트블레이드, 9));
					}
				}
					break;
				case 임팩트: {
					// System.out.println(2222222);
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_CreateItem(임팩트, 15));
						int c = 5;// 기본확률
						if (pc.getLevel() > 80)
							c += (pc.getLevel() - 80);
						if (c > 10)// 최대확률
							c = 10;
						pc.임팩트 = c;
					}
				}
					break;
				case 그레이스아바타: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						int c = 10;// 효과
						if (pc.getLevel() > 80)
							c += (pc.getLevel() - 80);
						if (c > 15)// 최대효과
							c = 15;
						if (pc.isInParty()) { // 파티 인경우
							for (L1PcInstance partymember : pc.getParty().getMembers()) {
								if (isDistance(pc.getX(), pc.getY(), pc.getMapId(), partymember.getX(),
										partymember.getY(), partymember.getMapId(), 18)) {
									if (!partymember.getSkillEffectTimerSet().hasSkillEffect(그레이스아바타)) {
										partymember.getResistance().addhorror(c);
										partymember.getResistance().addStun(c);
										partymember.getResistance().addHold(c);
									}
									partymember.sendPackets(new S_CreateItem(그레이스아바타, 15));
									partymember.sendPackets(new S_SkillSound(partymember.getId(), 4434));
									partymember.broadcastPacket(new S_SkillSound(partymember.getId(), 4434));
									partymember.그레이스아바타 = c;
								}
							}
						} else {
							if (!pc.getSkillEffectTimerSet().hasSkillEffect(그레이스아바타)) {
								// pc.getResistance().addFear(c);
								pc.getResistance().addStun(c);
								pc.getResistance().addHold(c);
							}
							pc.sendPackets(new S_CreateItem(그레이스아바타, 15));
							pc.그레이스아바타 = c;
						}
					}
				}
					break;
				case 데스힐: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						// int healtime = 5;
						// if(pc.getLevel() > 80)
						// healtime = healtime +( pc.getLevel()-80);
						// if(healtime > 10)
						// healtime = 10;
						// healtime = CommonUtil.random(5, healtime);
						_shockStunDuration = 8 * 1000;// 데스힐도 일단 저변수를쓰자.
						pc.sendPackets(new S_CreateItem(데스힐, 8));
					}
				}
					break;
				case 디스트로이: {
					if (_calcType == PC_PC) {
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_CreateItem(디스트로이, 30));
						}
					}
				}
					break;
				case 타이탄라이징: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_CreateItem(타이탄라이징, 2400));
					}
				}
					break;
				case 어쌔신: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_CreateItem(어쌔신, 15));
					}
				}
					break;
				case 소울배리어: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_CreateItem(소울배리어, 600));
					}
				}
					break;
				case ANTA_SKILL_14: { // 안타라스(리뉴얼) - 용언 스킬9
					if (_user instanceof L1NpcInstance) {
						if (_npc.skilluse == false) {
							_npc.skilluse = true;
							Broadcaster.broadcastPacket(_user, new S_NpcChatPacket(_npc, "오브 모크! 티프 심..", 0), true);
							// 오브 모크! 티프심
							AntarasRaid ar = AntarasRaidSystem.getInstance().getAR(_user.getMapId());
							AntarasRaidTimer antaendtime = new AntarasRaidTimer(_npc, ar, 23, 2000);// 2초
							antaendtime.begin();
						}
					}
				}
					break;
				case SLOW:
				case MASS_SLOW:
				case ENTANGLE:
				case MOB_SLOW_1:
				case MOB_SLOW_18: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.is에틴인형()) {
							continue;
						}
						if (pc.getHasteItemEquipped() > 0) {
							continue;
						}
					}
					if (cha.getMoveState().getMoveSpeed() == 0) {

						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_SkillHaste(pc.getId(), 2, _getBuffIconDuration), true);
						}

						Broadcaster.broadcastPacket(cha, new S_SkillHaste(cha.getId(), 2, _getBuffIconDuration), true);
						cha.getMoveState().setMoveSpeed(2);
					} else if (cha.getMoveState().getMoveSpeed() == 1) {
						int skillNum = 0;
						if (cha.getSkillEffectTimerSet().hasSkillEffect(HASTE)) {
							skillNum = HASTE;
						} else if (cha.getSkillEffectTimerSet().hasSkillEffect(GREATER_HASTE)) {
							skillNum = GREATER_HASTE;
						} else if (cha.getSkillEffectTimerSet().hasSkillEffect(STATUS_HASTE)) {
							skillNum = STATUS_HASTE;
						}
						if (skillNum != 0) {
							cha.getSkillEffectTimerSet().removeSkillEffect(skillNum);
							cha.getSkillEffectTimerSet().removeSkillEffect(_skillId);
							cha.getMoveState().setMoveSpeed(0);
							continue;
						}
					}
				}
					break;
				case SHAPE_CHANGE:
					if (_player != null && _player.isInvisble()) {
						_player.delInvis();
					}
					if (cha instanceof L1PcInstance) {
						int probability = 0;
						int attackInt = _user.getAbility().getTotalInt();
						int defenseMr = cha.getResistance().getEffectedMrBySkill();

						// if (attackInt > 25) attackInt = 25;
						probability = (int) ((attackInt - (defenseMr / 4)) * 2);

						if (_calcType == PC_NPC) {
							if (_targetNpc.getLevel() >= 70) {
								probability = 0;
							}
						}
						if (_calcType == PC_PC) {
							if (_user.getId() == cha.getId()) {
								probability = 100;
							}
						}

						if (probability > _random.nextInt(100)) {
							L1PcInstance pc = (L1PcInstance) cha;
							L1PinkName.onAction(pc, _user);
							if (pc.getInventory().checkEquipped(20281) || pc.getInventory().checkEquipped(120281)) {
								pc.sendPackets(new S_PolyHtml());
								pc.sendPackets(new S_ServerMessage(241, _user.getName()));
							} else {
								int randomValue = 11;
								if (pc.getLevel() >= 15)
									randomValue += 9;
								if (pc.getLevel() >= 30)
									randomValue += 18;
								int polyId = 11328 + _random.nextInt(randomValue);
								if (polyId >= 11358 && polyId <= 11361) {
									polyId = 11371 + _random.nextInt(4);
								} else if (polyId >= 11362 && polyId <= 11365) {
									polyId = 11396 + _random.nextInt(4);
								}
								L1PolyMorph.doPoly(pc, polyId, 7200, L1PolyMorph.MORPH_BY_ITEMMAGIC);
								if (_user.getId() == cha.getId()) {
								} else {
									pc.sendPackets(new S_ServerMessage(241, _user.getName()));
								}
							}
						} else {
							_player.sendPackets(new S_ServerMessage(280));
						}

					} else/* if(cha instanceof L1MonsterInstance) */ {

						/*
						 * int probability = 0; if(_targetNpc.getLevel()>=70){
						 * probability = 0; } if (_calcType == PC_PC ){
						 * if(_user.getId() == cha.getId()){ probability = 100;
						 * } }
						 * 
						 * if(probability > _random.nextInt(100)){
						 * 
						 * 
						 * L1PcInstance pc = (L1PcInstance) cha;
						 * L1PinkName.onAction(pc, _user);
						 * if(pc.getInventory().checkEquipped(20281)){
						 * pc.sendPackets(new S_PolyHtml()); pc.sendPackets(new
						 * S_ServerMessage(241, _user.getName())); }else{ int
						 * randomValue = 11; if(pc.getLevel() >= 15) randomValue
						 * += 9; if(pc.getLevel() >= 30) randomValue += 18; int
						 * polyId = 11328+_random.nextInt(randomValue);
						 * if(polyId >= 11358 && polyId <= 11361){ polyId =
						 * 11371+_random.nextInt(4); }else if(polyId >= 11362 &&
						 * polyId <= 11365){ polyId = 11396+_random.nextInt(4);
						 * } L1PolyMorph.doPoly(pc, polyId, 7200,
						 * L1PolyMorph.MORPH_BY_ITEMMAGIC); if(_user.getId() ==
						 * cha.getId()){ }else{ pc.sendPackets(new
						 * S_ServerMessage(241, _user.getName())); } }
						 * 
						 * 
						 * }else{
						 */
						_player.sendPackets(new S_ServerMessage(280));
						// }
					}
					break;
				case CURSE_BLIND:
				case DARKNESS: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_FLOATING_EYE)) {
							pc.sendPackets(new S_CurseBlind(2), true);
						} else {
							pc.sendPackets(new S_CurseBlind(1), true);
						}
					}
				}
					break;
				case CURSE_POISON: {
					L1DamagePoison.doInfection(_user, cha, 3000, 5);
				}
					break;
				case CURSE_PARALYZE:
					if (!cha.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(CURSE_PARALYZE)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(CURSE_PARALYZE2)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(MOB_CURSEPARALYZ_18)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(MOB_CURSEPARALYZ_19)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(40013)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CURSE_PARALYZING)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)) {
						if (cha instanceof L1PcInstance) {
							int rrr = _random.nextInt(5) + 15;
							L1CurseParalysis.curse(cha, 8000, rrr * 1000);
						} else if (cha instanceof L1MonsterInstance) {
							L1CurseParalysis.curse(cha, 0, 4000);
						}
					}
					break;
				case CURSE_PARALYZE2:
				case MOB_CURSEPARALYZ_18:
				case MOB_CURSEPARALYZ_19:
				case 40013: {
					if (!cha.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(CURSE_PARALYZE)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(CURSE_PARALYZE2)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(MOB_CURSEPARALYZ_18)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(MOB_CURSEPARALYZ_19)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(40013)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_CURSE_PARALYZING)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(ICE_LANCE)
							&& !cha.getSkillEffectTimerSet().hasSkillEffect(FREEZING_BREATH)) {
						if (cha instanceof L1PcInstance) {
							((L1PcInstance) cha).sendPackets(new S_SkillSound(cha.getId(), 746));
							Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), 746));
							L1CurseParalysis.curse(cha, 5000, 3000);
						} else if (cha instanceof L1MonsterInstance) {
							Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), 746));
							L1CurseParalysis.curse(cha, 0, 4000);
						}
					}
				}
					break;
				case WEAKNESS:
				case MOB_WEAKNESS_1: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(-5);
						pc.addHitup(-1);
					}
				}
					break;
				case DISEASE:
				case MOB_DISEASE_1:
				case MOB_DISEASE_30: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(-6);
						pc.getAC().addAc(12);
					}
				}
					break;
				case GUARD_BREAK: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAC().addAc(10);
					}
				}
					break;
				case HORROR_OF_DEATH: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAbility().addAddedStr((byte) -3);
						pc.getAbility().addAddedInt((byte) -3);
					}
				}
					break;
				case PANIC: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAbility().addAddedStr((byte) -1);
						pc.getAbility().addAddedDex((byte) -1);
						pc.getAbility().addAddedCon((byte) -1);
						pc.getAbility().addAddedInt((byte) -1);
						pc.getAbility().addAddedWis((byte) -1);
						pc.resetBaseMr();
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
					}
				}
					break;
				/*
				 * case MAGMA_BREATH: case SHOCK_SKIN: case FREEZING_BREATH:{
				 * _player.sendPackets(new S_SystemMessage(
				 * "각성 업데이트로 당분간 사용할 수 없습니다."), true);
				 * 
				 * } break;
				 */
				case 38:
					if (_npc != null) {
						if (_npc.getNpcId() == 90026) {
							int ran = random.nextInt(100) + 1;
							if (ran < 15) {
								Broadcaster.broadcastPacket(_npc,
										new S_NpcChatPacket(_npc, "인간주제에 혹한의 힘을 견딜 수 있겠느냐!", 0), true);
							} else if (ran < 30) {
								Broadcaster.broadcastPacket(_npc, new S_NpcChatPacket(_npc, "여왕님의 혼란을 방해할 수는 없다!", 0),
										true);
							}
						}
					}
					break;
				case 55004:
					if (_npc != null) {
						if (_npc.getNpcId() == 46141 || _npc.getNpcId() == 46142) {
							int ran = random.nextInt(100) + 1;
							if (ran < 20) {
								L1SpawnUtil.spawn2(_npc.getX(), _npc.getY(), _npc.getMapId(), 90026, 5, 0, 0);
								L1SpawnUtil.spawn2(_npc.getX(), _npc.getY(), _npc.getMapId(), 90026, 5, 0, 0);
								L1SpawnUtil.spawn2(_npc.getX(), _npc.getY(), _npc.getMapId(), 90026, 5, 0, 0);
								L1SpawnUtil.spawn2(_npc.getX(), _npc.getY(), _npc.getMapId(), 90026, 5, 0, 0);
								L1SpawnUtil.spawn2(_npc.getX(), _npc.getY(), _npc.getMapId(), 90026, 5, 0, 0);
								Broadcaster.broadcastPacket(_npc, new S_SkillSound(_npc.getId(), 761), true);
								Broadcaster.broadcastPacket(_npc,
										new S_NpcChatPacket(_npc, "시녀들은 뭐하는 것이냐? 어서 나를 받들라!", 0), true);
							}
						}
					}
					break;

				case DISINTEGRATE:
					if (_npc != null) {
						if (_npc.getNpcId() == 46141 || _npc.getNpcId() == 46142) {
							int ran = random.nextInt(100) + 1;
							if (ran < 3) {
								Broadcaster.broadcastPacket(_npc,
										new S_NpcChatPacket(_npc, "혹한의 바람이여 이들의 숨결조차 얼어붙게 하라!", 0), true);
							}
							dmg /= 3;
						}
					}
					break;

				case 10035:
					if (_npc != null) {
						if (_npc.getNpcId() == 46141 || _npc.getNpcId() == 46142) {
							int ran = random.nextInt(100) + 1;
							if (ran < 3) {
								Broadcaster.broadcastPacket(_npc,
										new S_NpcChatPacket(_npc, "얼음 칼날들이여 저들을 모두 베어버려라!", 0), true);
							}
						}
					}
					break;
				case ICE_LANCE:
				/* case FREEZING_BLIZZARD: */ {
					_isFreeze = _magic.calcProbabilityMagic(_skillId);
					if (_npc != null) {
						if (_npc.getNpcId() == 46141 || _npc.getNpcId() == 46142) {

							Broadcaster.broadcastPacket(_npc, new S_NpcChatPacket(_npc, "네게 명한다. 이 자리에서 얼어 죽어라", 0),
									true);

							// int ran = random.nextInt(100)+1;
							// if(ran < 50){
							// _isFreeze = true;
							// }
						}
					}
					if (_isFreeze) {
						int time = _skill.getBuffDuration() * 1000;
						L1EffectSpawn.getInstance().spawnEffect(81168, time, cha.getX(), cha.getY(), cha.getMapId());
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_Poison(pc.getId(), 2), true);
							Broadcaster.broadcastPacket(pc, new S_Poison(pc.getId(), 2), true);
							pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, true), true);
						} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
								|| cha instanceof L1PetInstance) {
							L1NpcInstance npc = (L1NpcInstance) cha;
							Broadcaster.broadcastPacket(npc, new S_Poison(npc.getId(), 2), true);
							npc.setParalyzed(true);
							npc.setParalysisTime(time);
						}
					}
				}
					break;
				case EARTH_BIND:
				case MOB_BASILL:
				case MOB_COCA: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (_skillId == EARTH_BIND)
							_skillTime = random.nextInt(_skill.getBuffDuration()) + 1;

						pc.sendPackets(new S_Poison(pc.getId(), 2), true);
						Broadcaster.broadcastPacket(pc, new S_Poison(pc.getId(), 2), true);
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, true), true);
						if (_skillId == MOB_BASILL) {
							pc.sendPackets(new S_SkillSound(pc.getId(), 1043), true);
							Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 1043), true);
						}

					} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
							|| cha instanceof L1PetInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						if (_skillId == MOB_BASILL)
							Broadcaster.broadcastPacket(npc, new S_SkillSound(npc.getId(), 1043), true);
						Broadcaster.broadcastPacket(npc, new S_Poison(npc.getId(), 2), true);
						npc.setParalyzed(true);
						if (_skillId == EARTH_BIND) {
							_skillTime = random.nextInt(_skill.getBuffDuration()) + 1;
							npc.setParalysisTime(_skillTime * 1000);
						} else
							npc.setParalysisTime(_skill.getBuffDuration() * 1000);
					}
				}
					break;
				case DARK_BLIND:
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_SLEEP, true), true);
					}
					cha.setSleeped(true);
					break;
				case 기간틱: {
					L1PcInstance pc = (L1PcInstance) cha;
					double per = (pc.getLevel() / 2.000) / 100.000;
					int addhp = (int) ((double) pc.getBaseMaxHp() * per);
					pc.setggHp(addhp);
					pc.addMaxHp(addhp);
					pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
					if (pc.isInParty()) {
						pc.getParty().updateMiniHP(pc);
					}
				}
					break;
				case 토마호크: {// 레벨 * 2 / 6
					if (cha instanceof L1PcInstance) {
						L1PcInstance target = (L1PcInstance) cha;
						if (target.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.토마호크)) {
							if (target.토마호크th != null) {
								target.토마호크th.stop();
							}
						}
					} else if (cha instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						if (npc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.토마호크)) {
							if (npc.토마호크th != null) {
								npc.토마호크th.stop();
							}
						}
					}
					L1토마호크 토마 = new L1토마호크(_player, cha);
					토마.begin();

					if (cha instanceof L1PcInstance) {
						L1PcInstance target = (L1PcInstance) cha;
						target.토마호크th = 토마;
						target.sendPackets(new S_NewCreateItem(S_NewCreateItem.버프창, true), true);
					} else if (cha instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						npc.토마호크th = 토마;
					}
				}
					break;
				case THUNDER_GRAB: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance target = (L1PcInstance) cha;
						int attackLevel = _user.getLevel();
						int defenseLevel = target.getLevel();
						int probability = 0;
						int _ranpro = random.nextInt(100) + 1;
						if (attackLevel >= defenseLevel) {
							probability = (int) ((attackLevel - defenseLevel) * 5) + 45;
						} else if (attackLevel < defenseLevel) {
							probability = (int) ((attackLevel - defenseLevel) * 6) + 45;
						}
						if (probability > 90) {
							probability = 90;
						}
						int stunregi = target.getResistance().getHold();
						probability -= stunregi;

						if (probability < 0) {
							probability = 0;
						}
						if (probability < _ranpro) {
							return;
						}
					} else {
						int probability = 50;
						int _ranpro = random.nextInt(100) + 1;
						if (probability < _ranpro) {
							return;
						}
					}

					int ran = random.nextInt(3) + 1;

					int time = ran * 1000;
					L1EffectSpawn.getInstance().spawnEffect(81182, time, cha.getX(), cha.getY(), cha.getMapId());

					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;

						// pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.EARTH_BIND,
						// time);

						// pc.sendPackets(new S_Poison(pc.getId(), 2), true);
						// Broadcaster.broadcastPacket(pc, new
						// S_Poison(pc.getId(), 2), true);

						// pc.sendPackets(new
						// S_Paralysis(S_Paralysis.TYPE_FREEZE, true), true);
						pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.THUNDER_GRAB, time);
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_BIND, true), true);
					} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
							|| cha instanceof L1PetInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;

						// npc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.EARTH_BIND,
						// time);

						// Broadcaster.broadcastPacket(npc, new
						// S_Poison(npc.getId(), 2), true);
						npc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.THUNDER_GRAB, time);
						// npc.setParalyzed(true);
						// npc.setParalysisTime(time);
					}
					return;
				}

				case 40037: // 라미아 발 묶기
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						// pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.THUNDER_GRAB,
						// _skill.getBuffDuration()*1000);
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_BIND, true), true);
					} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
							|| cha instanceof L1PetInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						// npc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.THUNDER_GRAB,
						// _skill.getBuffDuration()*1000);
						npc.setParalyzed(true);
						npc.setParalysisTime(_skill.getBuffDuration() * 1000);
					}
					break;

				case DRESS_EVASION:// 12
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.Add_Er(18);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
					}
					break;
				case AQUA_PROTECTER:// 5
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.Add_Er(5);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
					}
					break;
				case SOLID_CARRIAGE:// 15
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.Add_Er(15);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
					}
					break;
				case STRIKER_GALE:// -99
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_OwnCharStatus2(pc));
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
						pc.sendPackets(new S_CharVisualUpdate(pc));

					}
					break;
				case 파워그립: {
					_shockStunDuration = random.nextInt(4000) + 2000;
					L1EffectSpawn.getInstance().spawnEffect(91163, _shockStunDuration, cha.getX(), cha.getY(),
							cha.getMapId());
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_POWER_GRIP, true));
					} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
							|| cha instanceof L1PetInstance || cha instanceof L1DwarfInstance
							|| cha instanceof L1TeleporterInstance || cha instanceof L1MerchantInstance
							|| cha instanceof L1ScarecrowInstance || cha instanceof L1PeopleInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						npc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.파워그립, _shockStunDuration);
					}
				}
					break;
				case 데스페라도: {
					int targetLevel = 0;
					int diffLevel = 0;
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						targetLevel = pc.getLevel();
					} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
							|| cha instanceof L1PetInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						targetLevel = npc.getLevel();
						if (npc.getMaxHp() > 5000) {
							return;
						}
					}

					// diffLevel = _user.getLevel() - targetLevel;
					/*
					 * int rnd = random.nextInt(100); if(rnd < 20){
					 * _shockStunDuration = random.nextInt(1000)+1000;//20%
					 * }else{ _shockStunDuration =
					 * random.nextInt(4000)+2000;//80% }
					 */
					diffLevel = _user.getLevel() - targetLevel;

					if (diffLevel <= -10) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel <= -8) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel <= -6) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel <= -4) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel <= -2) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel == 0) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel <= 2) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel <= 4) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel <= 6) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel <= 8) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel <= 10) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					} else if (diffLevel > 10) {
						_shockStunDuration = stunTimehorror[random.nextInt(stunTimehorror.length)];
					}

					L1EffectSpawn.getInstance().spawnEffect(91162, _shockStunDuration, cha.getX(), cha.getY(),
							cha.getMapId());
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_horror, true));
						pc.데스페라도공격자레벨 = _user.getLevel();

						// pc.sendPackets(new S_SkillSound(pc.getId(), 12758),
						// true);
						// Broadcaster.broadcastPacket(pc, new
						// S_SkillSound(pc.getId(), 12758), true);
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_DEATH_PERADO, true));

					} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
							|| cha instanceof L1PetInstance || cha instanceof L1DwarfInstance
							|| cha instanceof L1TeleporterInstance || cha instanceof L1MerchantInstance
							|| cha instanceof L1ScarecrowInstance || cha instanceof L1PeopleInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						// Broadcaster.broadcastPacket(npc, new
						// S_SkillSound(npc.getId(), 12758), true);
						npc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.데스페라도, _shockStunDuration);
						// npc.setParalyzed(true);123
						// npc.setParalysisTime(_shockStunDuration);
					}
				}
					break;
				case SHOCK_STUN: {
					try {
						int targetLevel = 0;
						int diffLevel = 0;
						// _user 가 pc 라면.
						if (_user != cha && _user instanceof L1PcInstance) {
							L1PcInstance c = (L1PcInstance) _user;
							// 양손 여부 체크
							if (c == null)
								return; // 시전자가 없다면 리턴
							if (c.getWeapon() == null)
								return; // 검이 없다면 리턴
							if (!c.getWeapon().getItem().isTwohandedWeapon())
								return; // 현재 검이 양손검이 아니라면 리턴.
						}

						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							targetLevel = pc.getLevel();
							pc.receiveDamage(_user, _random.nextInt(70) + 80, 0);
						} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
								|| cha instanceof L1PetInstance) {
							L1NpcInstance npc = (L1NpcInstance) cha;
							targetLevel = npc.getLevel();
							npc.receiveDamage(_user, _random.nextInt(70) + 80);
							if (npc.getMaxHp() > 5000) {
								return;
							}
						}

						diffLevel = _user.getLevel() - targetLevel;

						{
							if (diffLevel <= -10) {
								_shockStunDuration = stunTimeArray1[random.nextInt(stunTimeArray1.length)];
							} else if (diffLevel <= -8) {
								_shockStunDuration = stunTimeArray2[random.nextInt(stunTimeArray2.length)];
							} else if (diffLevel <= -6) {
								_shockStunDuration = stunTimeArray3[random.nextInt(stunTimeArray3.length)];
							} else if (diffLevel <= -4) {
								_shockStunDuration = stunTimeArray4[random.nextInt(stunTimeArray4.length)];
							} else if (diffLevel <= -2) {
								_shockStunDuration = stunTimeArray5[random.nextInt(stunTimeArray5.length)];
							} else if (diffLevel == 0) {
								_shockStunDuration = stunTimeArray6[random.nextInt(stunTimeArray6.length)];
							} else if (diffLevel <= 2) {
								_shockStunDuration = stunTimeArray7[random.nextInt(stunTimeArray7.length)];
							} else if (diffLevel <= 4) {
								_shockStunDuration = stunTimeArray8[random.nextInt(stunTimeArray8.length)];
							} else if (diffLevel <= 6) {
								_shockStunDuration = stunTimeArray9[random.nextInt(stunTimeArray9.length)];
							} else if (diffLevel <= 8) {
								_shockStunDuration = stunTimeArray10[random.nextInt(stunTimeArray10.length)];
							} else if (diffLevel <= 10) {
								_shockStunDuration = stunTimeArray11[random.nextInt(stunTimeArray11.length)];
							} else if (diffLevel > 10) {
								_shockStunDuration = stunTimeArray12[random.nextInt(stunTimeArray12.length)];
							}

						}

						L1EffectSpawn.getInstance().spawnEffect(81162, _shockStunDuration, cha.getX(), cha.getY(),
								cha.getMapId());
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_STUN, true), true);
						} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
								|| cha instanceof L1PetInstance) {
							L1NpcInstance npc = (L1NpcInstance) cha;
							npc.setParalyzed(true);
							npc.setParalysisTime(_shockStunDuration);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

					break;

				case MOB_RANGESTUN_18:
				case MOB_RANGESTUN_19:

					break;

				case MOB_SHOCKSTUN_30: {
					_shockStunDuration = mobstun30TimeArray[random.nextInt(mobstun30TimeArray.length)];
					L1EffectSpawn.getInstance().spawnEffect(81162, _shockStunDuration, cha.getX(), cha.getY(),
							cha.getMapId());
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_STUN, true), true);
					} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
							|| cha instanceof L1PetInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						npc.setParalyzed(true);
						npc.setParalysisTime(_shockStunDuration);
					}
				}
					break;
				case 40003: // 불멸의리치, 냉혹의 아이리스 넉백스킬
					// byte HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
					// byte HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };
					for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(_user)) {
						if (pc == null)
							continue;
						L1Location newLocation = pc.getLocation().randomLocation(10, true);
						int newX = newLocation.getX();
						int newY = newLocation.getY();
						short mapId = (short) newLocation.getMapId();
						if (pc instanceof L1RobotInstance) {
							L1RobotInstance rob = (L1RobotInstance) pc;
							L1Teleport.로봇텔(rob, newX, newY, (short) mapId, true);
						} else {
							pc.dx = newX;
							pc.dy = newY;
							pc.dm = mapId;
							pc.dh = pc.getMoveState().getHeading();
							pc.setTelType(9);
							pc.sendPackets(new S_SabuTell(pc), true);
						}
						/*
						 * int heading = CharPosUtil.targetDirection(_user,
						 * pc.getX(), pc.getY()); heading =
						 * checkObject(pc.getX(), pc.getY(), pc.getMapId(),
						 * heading);
						 * 
						 * if(heading == -1)continue; int tempx =
						 * HEADING_TABLE_X[heading]; int tempy =
						 * HEADING_TABLE_Y[heading];
						 * 
						 * if(Math.max(Math.abs(_user.getX() - (pc.getX() +
						 * tempx)), Math.abs(_user.getY() - (pc.getY() +
						 * tempy))) <= 1) continue; pc.dx= pc.getX() + tempx;
						 * pc.dy= pc.getY() + tempy; pc.dm=
						 * (short)pc.getLocation().getMapId();
						 * pc.dh=pc.getMoveState().getHeading();
						 * pc.setTelType(9); pc.sendPackets(new S_SabuTell(pc));
						 */
					}
					break;
				case 40040: // 옛셸로브 끌어오기
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						L1Location newLocation = _user.getLocation().randomLocation(2, true);
						int newX = newLocation.getX();
						int newY = newLocation.getY();
						short mapId = (short) newLocation.getMapId();
						if (pc instanceof L1RobotInstance) {
							L1RobotInstance rob = (L1RobotInstance) pc;
							L1Teleport.로봇텔(rob, newX, newY, (short) mapId, true);
						} else {
							pc.dx = newX;
							pc.dy = newY;
							pc.dm = mapId;
							pc.dh = pc.getMoveState().getHeading();
							pc.setTelType(9);
							pc.sendPackets(new S_SabuTell(pc), true);
						}
					}
					break;
				case 40029: // 옛늑인 끌어오기
					for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(_user)) {
						if (pc == null)
							continue;
						L1Location newLocation = _user.getLocation().randomLocation(2, true);
						int newX = newLocation.getX();
						int newY = newLocation.getY();
						short mapId = (short) newLocation.getMapId();
						/*
						 * int newX = _user.getX() + random.nextInt(2)+1; int
						 * newY = _user.getY() + random.nextInt(2)+1; short
						 * mapId = (short) _user.getMapId();
						 */
						if (pc instanceof L1RobotInstance) {
							L1RobotInstance rob = (L1RobotInstance) pc;
							L1Teleport.로봇텔(rob, newX, newY, (short) mapId, true);
						} else {
							pc.dx = newX;
							pc.dy = newY;
							pc.dm = mapId;
							pc.dh = pc.getMoveState().getHeading();
							pc.setTelType(9);
							pc.sendPackets(new S_SabuTell(pc), true);
						}
					}
					break;
				case 40008: // 아이리스 아이스 미티어
					if (_user instanceof L1NpcInstance) {
						for (L1PcInstance pc : L1World.getInstance().getVisiblePlayer(_user, 10)) {
							if (pc == null)
								continue;
							int ran = random.nextInt(100) + 1;
							if (ran < 30) {
								_isFreeze = true;
							}
							if (_isFreeze) {
								int time = 3 * 1000;
								pc.getSkillEffectTimerSet().setSkillEffect(L1SkillId.ICE_LANCE, time);
								L1EffectSpawn.getInstance().spawnEffect(81168, time, cha.getX(), cha.getY(),
										cha.getMapId());
								pc.sendPackets(new S_Poison(pc.getId(), 2), true);
								Broadcaster.broadcastPacket(pc, new S_Poison(pc.getId(), 2), true);
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_FREEZE, true), true);
							}
						}
					}
					break;
				case BONE_BREAK: {
					int bonetime = random.nextInt(1000) + 1000;

					L1Attack attack = new L1Attack(_user, cha);
					// 데미지
					if (random.nextInt(100) > 20 && !cha.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
						if (attack.calcHit())
							attack.calcDamage();
						attack.action();
						attack.commit();
						dmg = 10;
						L1EffectSpawn.getInstance().spawnEffect(4500500, bonetime, cha.getX(), cha.getY(),
								cha.getMapId());
					} else {
						attack.action();
					}
					attack = null;
					if (!cha.getSkillEffectTimerSet().hasSkillEffect(EARTH_BIND)) {
						// 스턴
						int attackLevel = _user.getLevel();
						int defenseLevel = cha.getLevel();
						int probability = 0;
						if (attackLevel >= defenseLevel) {
							probability = (int) ((attackLevel - defenseLevel) * 5) + 30;
						} else if (attackLevel < defenseLevel) {
							probability = (int) ((attackLevel - defenseLevel) * 5) + 30;
						}
						if (probability > 90)
							probability = 90;

						else if (probability < 30)
							probability = 30;
						/*
						 * if (random.nextInt(100) < probability) { //
						 * random.nextInt(21)+30 if (!effect)
						 */
						L1EffectSpawn.getInstance().spawnEffect(4500500, bonetime, cha.getX(), cha.getY(),
								cha.getMapId());

						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_STUN, true));
						} else if (cha instanceof L1MonsterInstance || cha instanceof L1SummonInstance
								|| cha instanceof L1PetInstance) {
							L1NpcInstance npc = (L1NpcInstance) cha;
							npc.setParalyzed(true);
							npc.setParalysisTime(bonetime);
						}
					} else {
						return;
					}
				}
					// }
					break;
				case AM_BREAK: {
					/*
					 * if (cha instanceof L1PcInstance) { L1PcInstance pc =
					 * (L1PcInstance) cha; pc.addDmgup(-2); }
					 */
					for (L1Object obj : cha.getNearObjects().getVisibleObjects(cha, 15)) {
						// for(L1Object obj :
						// L1World.getInstance().getVisibleObjects(cha, 15)){
						if (obj != null && obj instanceof L1Character) {
							L1Character chara = (L1Character) obj;
							if (chara instanceof L1PcInstance && !((L1PcInstance) chara).isGm()) {
								detection((L1PcInstance) chara);
							} else if (chara instanceof L1NpcInstance) {
								L1NpcInstance npc = (L1NpcInstance) chara;
								int hiddenStatus = npc.getHiddenStatus();
								if (hiddenStatus == L1NpcInstance.HIDDEN_STATUS_SINK) {
									if (npc.getNpcId() != 45682) {
										npc.appearOnGround(_user);
									}
								}
							}
						}
					}
				}
					break;
				case PHANTASM: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_SLEEP, true), true);
					}
					cha.setSleeped(true);
				}
					break;
				case WIND_SHACKLE:
					break;
				case MOB_WINDSHACKLE_1: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SkillIconWindShackle(pc.getId(), _getBuffIconDuration), true);
					}
				}
					break;
				case 40016:
				case CANCELLATION: {
					if (_player != null && _player.isInvisble()) {
						_player.delInvis();
					}
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.isInvisble()) {
							pc.delInvis();
						}
					}

					if (cha instanceof L1NpcInstance) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						int npcId = npc.getNpcTemplate().get_npcId();
						if (npcId == 71092) {
							if (npc.getGfxId().getGfxId() == npc.getGfxId().getTempCharGfx()) {
								npc.getGfxId().setTempCharGfx(1314);
								Broadcaster.broadcastPacket(npc, new S_ChangeShape(npc.getId(), 1314), true);
								return;
							} else {
								return;
							}
						} else if (npcId == 45640) {
							if (npc.getGfxId().getGfxId() == npc.getGfxId().getTempCharGfx()) {
								npc.setCurrentHp(npc.getMaxHp());
								npc.getGfxId().setTempCharGfx(2332);
								Broadcaster.broadcastPacket(npc, new S_ChangeShape(npc.getId(), 2332), true);
								npc.setName("$2103");
								npc.setNameId("$2103");
								Broadcaster.broadcastPacket(npc, new S_ChangeName(npc.getId(), "$2103"), true);
							} else if (npc.getGfxId().getTempCharGfx() == 2332) {
								npc.setCurrentHp(npc.getMaxHp());
								npc.getGfxId().setTempCharGfx(2755);
								Broadcaster.broadcastPacket(npc, new S_ChangeShape(npc.getId(), 2755), true);
								npc.setName("$2488");
								npc.setNameId("$2488");
								Broadcaster.broadcastPacket(npc, new S_ChangeName(npc.getId(), "$2488"), true);
							}
						} else if (npcId == 81209) {
							if (npc.getGfxId().getGfxId() == npc.getGfxId().getTempCharGfx()) {
								npc.getGfxId().setTempCharGfx(4310);
								Broadcaster.broadcastPacket(npc, new S_ChangeShape(npc.getId(), 4310), true);
								return;
							} else {
								return;
							}
						}
					}

					if (!(cha instanceof L1PcInstance)) {
						L1NpcInstance npc = (L1NpcInstance) cha;
						npc.getMoveState().setMoveSpeed(0);
						npc.getMoveState().setBraveSpeed(0);
						Broadcaster.broadcastPacket(npc, new S_SkillHaste(cha.getId(), 0, 0), true);
						Broadcaster.broadcastPacket(npc, new S_SkillBrave(cha.getId(), 0, 0), true);
						npc.setWeaponBreaked(false);
						npc.setParalyzed(false);
						npc.setParalysisTime(0);
					}

					for (int skillNum = SKILLS_BEGIN; skillNum <= SKILLS_END; skillNum++) {
						if (isNotCancelable(skillNum) && !cha.isDead()) {
							continue;
						}
						if (skillNum == SHAPE_CHANGE) { //
							if (cha instanceof L1PcInstance) {
								L1PcInstance pc = (L1PcInstance) cha;
								if (pc.getGfxId().getTempCharGfx() >= 13715
										&& pc.getGfxId().getTempCharGfx() <= 13745) {
									continue;
								}

							}
						}

						// TotalRank
						cha.getSkillEffectTimerSet().removeSkillEffect(skillNum);
					}
					for (int i = 0; i < 캔슬강제삭제.length; i++) {
						if (cha.getSkillEffectTimerSet().hasSkillEffect(캔슬강제삭제[i]))
							cha.getSkillEffectTimerSet().removeSkillEffect(캔슬강제삭제[i]);
					}
					for (int skillNum = STATUS_BEGIN; skillNum <= STATUS_CANCLEEND; skillNum++) {
						// System.out.println("캔슬 삭제 버프 번호 : " + skillNum);
						if (isNotCancelable(skillNum) && !cha.isDead()) {
							continue;
						}
						cha.getSkillEffectTimerSet().removeSkillEffect(skillNum);
					}

					cha.curePoison();
					cha.cureParalaysis();

					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.SHAPE_CHANGE)) {
							if (!(pc.getGfxId().getTempCharGfx() >= 13715 && pc.getGfxId().getTempCharGfx() <= 13745))
								L1PolyMorph.undoPoly(pc);
						}
						pc.sendPackets(new S_CharVisualUpdate(pc), true);
						Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc), true);

						if (pc.getHasteItemEquipped() > 0) {
							pc.getMoveState().setMoveSpeed(0);
							pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0), true);
							Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 0, 0), true);
						}
						if (pc != null && pc.isInvisble()) {
							if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.INVISIBILITY)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.INVISIBILITY);
								pc.sendPackets(new S_Invis(pc.getId(), 0), true);
								Broadcaster.broadcastPacket(pc, new S_Invis(pc.getId(), 0), true);
								pc.sendPackets(new S_Sound(147), true);
							}
							if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.BLIND_HIDING)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.BLIND_HIDING);
								pc.sendPackets(new S_Invis(pc.getId(), 0), true);
								Broadcaster.broadcastPacket(pc, new S_Invis(pc.getId(), 0), true);
								S_RemoveObject iv2 = new S_RemoveObject(pc.getId());
								pc.sendPackets(iv2);
								Broadcaster.broadcastPacket(pc, iv2);
							}
						}

						if (pc.is에틴인형()) {
							pc.getMoveState().setMoveSpeed(1);
							pc.sendPackets(new S_SkillHaste(pc.getId(), 1, -1), true);
							Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 1, 0), true);
						}

						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_UNDERWATER_BREATH)) {
							int timeSec = pc.getSkillEffectTimerSet().getSkillEffectTimeSec(STATUS_UNDERWATER_BREATH);
							pc.sendPackets(new S_SkillIconBlessOfEva(pc.getId(), timeSec), true);
						}

					}

					cha.getSkillEffectTimerSet().removeSkillEffect(STATUS_FREEZE);
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_CharVisualUpdate(pc), true);
						Broadcaster.broadcastPacket(pc, new S_CharVisualUpdate(pc), true);
						if (pc.isPrivateShop()) {
							pc.sendPackets(new S_DoActionShop(pc.getId(), ActionCodes.ACTION_Shop, pc.getShopChat()),
									true);
							Broadcaster.broadcastPacket(pc,
									new S_DoActionShop(pc.getId(), ActionCodes.ACTION_Shop, pc.getShopChat()), true);
						}
					}
				}
					break;
				case TURN_UNDEAD: {
					if (undeadType == 1 || undeadType == 3) {
						dmg = cha.getCurrentHp();
					}
				}
					break;
				case MANA_DRAIN: {
					if (!cha.isManaDrain) {
						int chance = random.nextInt(10) + 10;
						drainMana = chance + (_user.getAbility().getTotalInt() / 2);
						if (cha.getResistance().getMr() >= 101) {
							drainMana -= drainMana * 0.30;
						}
						/*
						 * if(cha.getResistance().getMr()<=50){ drainMana -=
						 * drainMana*0.1; }else
						 * if(cha.getResistance().getMr()<=100){ drainMana -=
						 * drainMana*0.2; }else{ drainMana -= drainMana*0.3; }
						 */
						if (cha.getCurrentMp() < drainMana) {
							drainMana = cha.getCurrentMp();
						}
						if (cha instanceof L1NpcInstance) {
							cha.isManaDrain = true;
						}
					}
					if (_user instanceof L1PcInstance) {
						_player.sendPackets(new S_SkillSound(_player.getId(), 2171), true);
						Broadcaster.broadcastPacket(_player, new S_SkillSound(_player.getId(), 2171), true);
					} else {
						Broadcaster.broadcastPacket(_user, new S_SkillSound(_user.getId(), 2171), true);
					}
				}
					break;
				case 40015:
				case WEAPON_BREAK: {
					if (_calcType == PC_PC || _calcType == NPC_PC) {
						if (cha instanceof L1PcInstance) {
							L1PcInstance pc = (L1PcInstance) cha;
							L1ItemInstance weapon = pc.getWeapon();
							if (weapon != null) {
								int weaponDamage = random.nextInt(_user.getAbility().getTotalInt() / 3) + 1;
								pc.sendPackets(new S_ServerMessage(268, weapon.getLogName()), true);
								pc.getInventory().receiveDamage(weapon, weaponDamage);
							}
						}
					} else {
						((L1NpcInstance) cha).setWeaponBreaked(true);
					}
				}
					break;
				case FOG_OF_SLEEPING: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SkillSound(pc.getId(), 9218), true);
						Broadcaster.broadcastPacket(pc, new S_SkillSound(pc.getId(), 9218), true);
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_SLEEP, true), true);
					}
					cha.setSleeped(true);
				}
					break;
				case STATUS_FREEZE: {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_BIND, true), true);
					}
				}
					break;
				default:
					break;
				}
				if (_calcType == PC_PC || _calcType == NPC_PC) { // 여기부터
					switch (_skillId) {
					case TELEPORT:
					case MASS_TELEPORT: {
						L1PcInstance pc = (L1PcInstance) cha;
						Random random = new Random();
						// L1BookMark bookm = pc.getBookMark(_bookmarkId);
						L1BookMark bookm = pc.getBookMark(_bookmarkX, _bookmarkY, _bookmarkMapId);
						if (bookm != null) {
							if (pc.getMap().isEscapable() || pc.isGm()) {
								L1Location newLocation = L1Location.BookmarkLoc(pc, bookm);
								int newX = newLocation.getX();
								int newY = newLocation.getY();
								short mapId = bookm.getMapId();
								L1Map map = L1WorldMap.getInstance().getMap(mapId);

								if (mapId == 4 && ((newX >= 33331 && newX <= 33341 && newY >= 32430 && newY <= 32441)
										|| (newX >= 33258 && newX <= 33267 && newY >= 32396 && newY <= 32407)
										|| (newX >= 33388 && newX <= 33397 && newY >= 32339 && newY <= 32350)
										|| (newX >= 33443 && newX <= 33483 && newY >= 32315 && newY <= 32357))) {
									newX = pc.getX();
									newY = pc.getY();
									mapId = pc.getMapId();
								}

								if (_skillId == MASS_TELEPORT) {
									byte count = 0;
									for (L1PcInstance member : L1World.getInstance().getVisiblePlayer(pc, 3)) {
										if (pc.getClanid() != 0 && member.getClanid() == pc.getClanid()
												&& member.getId() != pc.getId() && !member.isPrivateShop()) {
											count++;
											int newX2 = newX + random.nextInt(3) + 1;
											int newY2 = newY + random.nextInt(3) + 1;
											boolean ck = false;
											if (L1CastleLocation.checkInAllWarArea(newX2, newY2, mapId))
												ck = true;
											else if (L1HouseLocation.isInHouse(newX2, newY2, mapId))
												ck = true;
											else if (newX2 >= 32704 && newX2 <= 32835 && newY2 >= 33110
													&& newY2 <= 33234 && mapId == 4) // 샌드웜지역
												ck = true;
											else if ((newX2 >= 33472 && newX2 <= 33536)
													&& (newY2 >= 32838 && newY2 <= 32876) && mapId == 4) // 버경장
												ck = true;
											else if (mapId == 4 && ((newX2 >= 33331 && newX2 <= 33341 && newY2 >= 32430
													&& newY2 <= 32441)
													|| (newX2 >= 33258 && newX2 <= 33267 && newY2 >= 32396
															&& newY2 <= 32407)
													||

													(newX2 >= 34197 && newX2 <= 34302 && newY2 >= 33327
															&& newY2 <= 33533)
													|| // 황혼의산맥
													(newX2 >= 33453 && newX2 <= 33468 && newY2 >= 32331
															&& newY2 <= 32341)
													|| // 아덴의한국민

													(newX2 >= 33388 && newX2 <= 33397 && newY2 >= 32339
															&& newY2 <= 32350)
													|| (newX2 >= 33464 && newX2 <= 33531 && newY2 >= 33168
															&& newY2 <= 33248) // ||
											/*
											 * (newX2 >= 33443 && newX2 <= 33483
											 * && newY2 >= 32315 && newY2 <=
											 * 32357)
											 */) /* && !pc.isGm() */) {
												ck = true;
											}

											if (map.isInMap(newX2, newY2) && map.isPassable(newX2, newY2) && !ck) {
												L1Teleport.teleport(member, newX2, newY2, mapId,
														member.getMoveState().getHeading(), true, true);
											} else {
												L1Teleport.teleport(member, newX, newY, mapId,
														member.getMoveState().getHeading(), true, true);
											}
										}
									}
									if (count > 0) {

										for (L1PcInstance member : L1World.getInstance().getVisiblePlayer(pc, 3)) {
											if (pc.getClanid() != 0 && member.getClanid() == pc.getClanid()
													&& member.getId() != pc.getId() && !member.isPrivateShop()) {
												member.sendPackets(new S_ServerMessage(3655, pc.getName(), "" + count),
														true);
											}
										}
										pc.sendPackets(new S_ServerMessage(3655, pc.getName(), "" + count), true);
									}
								}
								if (pc.getInventory().checkItem(20288)) {
									L1Teleport.teleport(pc, newX, newY, mapId, pc.getMoveState().getHeading(), true,
											true);
								} else {
									int newX2 = (newX - 6) + random.nextInt(12);
									int newY2 = (newY - 6) + random.nextInt(12);

									int aaa = _random.nextInt(100);

									boolean ck = false;
									if (L1CastleLocation.checkInAllWarArea(newX2, newY2, mapId))
										ck = true;
									else if (L1HouseLocation.isInHouse(newX2, newY2, mapId))
										ck = true;
									else if (newX2 >= 32704 && newX2 <= 32835 && newY2 >= 33110 && newY2 <= 33234
											&& mapId == 4) // 샌드웜지역
										ck = true;
									else if ((newX2 >= 33472 && newX2 <= 33536) && (newY2 >= 32838 && newY2 <= 32876)
											&& mapId == 4) // 버경장
										ck = true;
									else if (mapId == 4 && ((newX2 >= 33331 && newX2 <= 33341 && newY2 >= 32430
											&& newY2 <= 32441)
											|| (newX2 >= 33258 && newX2 <= 33267 && newY2 >= 32396 && newY2 <= 32407) ||

											(newX2 >= 34197 && newX2 <= 34302 && newY2 >= 33327 && newY2 <= 33533) || // 황혼의산맥
											(newX2 >= 33453 && newX2 <= 33468 && newY2 >= 32331 && newY2 <= 32341) || // 아덴의한국민

											(newX2 >= 33388 && newX2 <= 33397 && newY2 >= 32339 && newY2 <= 32350)
											|| (newX2 >= 33464 && newX2 <= 33531 && newY2 >= 33168 && newY2 <= 33248) // ||
									/*
									 * (newX2 >= 33443 && newX2 <= 33483 &&
									 * newY2 >= 32315 && newY2 <= 32357)
									 */) /* && !pc.isGm() */) {
										ck = true;
									}

									if (aaa < 50) {// 요정 바보텔 확률
										ck = true;
										newX = pc.getX();
										newY = pc.getY();
									}

									if (map.isInMap(newX2, newY2) && map.isPassable(newX2, newY2) && !ck) {
										L1Teleport.teleport(pc, newX2, newY2, mapId, pc.getMoveState().getHeading(),
												true, true);
									} else {
										L1Teleport.teleport(pc, pc.getX(), pc.getY(), mapId,
												pc.getMoveState().getHeading(), true, true);
									}
								}
							} else {
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
								pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
							}
						} else {
							if ((pc.getMap().isTeleportable(pc.getX(), pc.getY()) && pc.getMap().isTeleportable())
									|| pc.isGm() || ((pc.getMapId() >= 101 && pc.getMapId() <= 110) && pc.is오만텔())) {
								L1Location newLocation = pc.getLocation().randomLocation(200, true);
								int newX = newLocation.getX();
								int newY = newLocation.getY();
								short mapId = (short) newLocation.getMapId();
								L1Map map = L1WorldMap.getInstance().getMap(mapId);

								if (_skillId == MASS_TELEPORT) {
									byte count = 0;
									for (L1PcInstance member : L1World.getInstance().getVisiblePlayer(pc, 3)) {
										if (pc.getClanid() != 0 && member.getClanid() == pc.getClanid()
												&& member.getId() != pc.getId() && !member.isPrivateShop()) {
											count++;
											int newX2 = newX + random.nextInt(3) + 1;
											int newY2 = newY + random.nextInt(3) + 1;
											boolean ck = false;
											if (L1CastleLocation.checkInAllWarArea(newX2, newY2, mapId))
												ck = true;
											else if (L1HouseLocation.isInHouse(newX2, newY2, mapId))
												ck = true;
											else if (newX2 >= 32704 && newX2 <= 32835 && newY2 >= 33110
													&& newY2 <= 33234 && mapId == 4) // 샌드웜지역
												ck = true;
											else if ((newX2 >= 33472 && newX2 <= 33536)
													&& (newY2 >= 32838 && newY2 <= 32876) && mapId == 4) // 버경장
												ck = true;
											else if (mapId == 4 && ((newX2 >= 33331 && newX2 <= 33341 && newY2 >= 32430
													&& newY2 <= 32441)
													|| (newX2 >= 33258 && newX2 <= 33267 && newY2 >= 32396
															&& newY2 <= 32407)
													||

													(newX2 >= 34197 && newX2 <= 34302 && newY2 >= 33327
															&& newY2 <= 33533)
													|| // 황혼의산맥
													(newX2 >= 33453 && newX2 <= 33468 && newY2 >= 32331
															&& newY2 <= 32341)
													|| // 아덴의한국민

													(newX2 >= 33388 && newX2 <= 33397 && newY2 >= 32339
															&& newY2 <= 32350)
													|| (newX2 >= 33464 && newX2 <= 33531 && newY2 >= 33168
															&& newY2 <= 33248)
											/*
											 * (newX2 >= 33443 && newX2 <= 33483
											 * && newY2 >= 32315 && newY2 <=
											 * 32357)
											 */) /* && !pc.isGm() */) {
												ck = true;
											}

											if (map.isInMap(newX2, newY2) && map.isPassable(newX2, newY2) && !ck) {
												L1Teleport.teleport(member, newX2, newY2, mapId,
														member.getMoveState().getHeading(), true, true);
											} else {
												L1Teleport.teleport(member, newX, newY, mapId,
														member.getMoveState().getHeading(), true, true);
											}
										}
									}
									if (count > 0) {
										for (L1PcInstance member : L1World.getInstance().getVisiblePlayer(pc, 3)) {
											if (pc.getClanid() != 0 && member.getClanid() == pc.getClanid()
													&& member.getId() != pc.getId() && !member.isPrivateShop()) {
												pc.sendPackets(new S_ServerMessage(3655, pc.getName(), "" + count),
														true);
											}
										}
										pc.sendPackets(new S_ServerMessage(3655, pc.getName(), "" + count), true);
									}
								}
								L1Teleport.teleport(pc, newX, newY, mapId, pc.getMoveState().getHeading(), true, true);
							} else {
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
								pc.sendPackets(new S_ServerMessage(276), true);
							}
						}
					}
						break;
					case TELEPORT_TO_MOTHER: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getMap().isEscapable() || pc.isGm()) {
							L1Teleport.teleport(pc, 33051, 32337, (short) 4, 5, true, true);
						} else {
							pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
							pc.sendPackets(new S_ServerMessage(647), true);
						}
					}
						break;
					case CALL_CLAN: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getMapId() == 4 && ((pc.getX() >= 33332 && pc.getX() <= 33338 && pc.getY() >= 32433
								&& pc.getY() <= 32439)
								|| (pc.getX() >= 33259 && pc.getX() <= 33265 && pc.getY() >= 32399
										&& pc.getY() <= 32405)
								|| (pc.getX() >= 33389 && pc.getX() <= 33395 && pc.getY() >= 32341
										&& pc.getY() <= 32347))) {
							pc.sendPackets(new S_ServerMessage(647), true);
							return;
						}

						if (pc.getMapId() == 4 && ((pc.getX() >= 33328 && pc.getX() <= 33344 && pc.getY() >= 32427
								&& pc.getY() <= 32444)
								|| (pc.getX() >= 33255 && pc.getX() <= 33272 && pc.getY() >= 32393
										&& pc.getY() <= 32412)
								||

								(pc.getX() >= 34194 && pc.getX() <= 34305 && pc.getY() >= 33324 && pc.getY() <= 33535)
								|| // 황혼의산맥
								(pc.getX() >= 33450 && pc.getX() <= 33470 && pc.getY() >= 32328 && pc.getY() <= 32344)
								|| // 아덴의한국민

								(pc.getX() >= 33385 && pc.getX() <= 33400 && pc.getY() >= 32336 && pc.getY() <= 32353)
								|| (pc.getX() >= 33461 && pc.getX() <= 33534 && pc.getY() >= 33165
										&& pc.getY() <= 33253))) {
							pc.sendPackets(new S_ServerMessage(647), true);
						}
						L1PcInstance clanPc = (L1PcInstance) L1World.getInstance().findObject(_targetID);
						if (clanPc != null) {
							clanPc.setTempID(pc.getId());
							clanPc.sendPackets(new S_Message_YN(729, ""), true);
						}
					}
						break;
					case RUN_CLAN: {
						L1PcInstance pc = (L1PcInstance) cha;
						L1PcInstance clanPc = (L1PcInstance) L1World.getInstance().findObject(_targetID);
						if (clanPc != null) {
							if (pc.getMap().isEscapable() || pc.isGm()) {
								if (pc.getMapId() == 4 && ((pc.getX() >= 33332 && pc.getX() <= 33338
										&& pc.getY() >= 32433 && pc.getY() <= 32439)
										|| (clanPc.getX() >= 33259 && clanPc.getX() <= 33265 && clanPc.getY() >= 32399
												&& clanPc.getY() <= 32405)
										|| (clanPc.getX() >= 33389 && clanPc.getX() <= 33395 && clanPc.getY() >= 32341
												&& clanPc.getY() <= 32347))) {
									pc.sendPackets(new S_ServerMessage(547), true);
									return;
								}
								boolean castle_area = L1CastleLocation.checkInAllWarArea(clanPc.getX(), clanPc.getY(),
										clanPc.getMapId());
								if ((clanPc.getMapId() == 0 || clanPc.getMapId() == 4 || clanPc.getMapId() == 304)
										&& castle_area == false) {
									L1Teleport.teleport(pc, clanPc.getX(), clanPc.getY(), clanPc.getMapId(), 5, true,
											true);
								} else {
									pc.sendPackets(new S_ServerMessage(547), true);
								}
							} else {
								pc.sendPackets(new S_Paralysis(S_Paralysis.TYPE_TELEPORT_UNLOCK, false), true);
								pc.sendPackets(new S_ServerMessage(647), true);
							}
						}
					}
						break;
					// case CREATE_MAGICAL_WEAPON: {
					// L1PcInstance pc = (L1PcInstance) cha;
					// L1ItemInstance item = pc.getInventory().getItem(
					// _itemobjid);
					// if (item != null && item.getItem().getType2() == 1) {
					// int item_type = item.getItem().getType2();
					// int safe_enchant = item.getItem().get_safeenchant();
					// int enchant_level = item.getEnchantLevel();
					// String item_name = item.getName();
					// if (safe_enchant < 0) {
					// pc.sendPackets(new S_SystemMessage(
					// "아무일도 일어나지 않았습니다."), true);
					// } else if (safe_enchant == 0) {
					// pc.sendPackets(new S_SystemMessage(
					// "아무일도 일어나지 않았습니다."), true);
					// } else if (item_type == 1 && enchant_level == 0) {
					// if (!item.isIdentified()) {
					// pc.sendPackets(new S_ServerMessage(161,
					// item_name, "$245", "$247"), true);
					// } else {
					// item_name = "+0 " + item_name;
					// pc.sendPackets(new S_ServerMessage(161,
					// "+0 " + item_name, "$245", "$247"),
					// true);
					// }
					// item.setEnchantLevel(1);
					// pc.getInventory().updateItem(item,
					// L1PcInventory.COL_ENCHANTLVL);
					// } else {
					// pc.sendPackets(new S_SystemMessage(
					// "아무일도 일어나지 않았습니다."), true);
					// }
					// } else {
					// pc.sendPackets(new S_SystemMessage(
					// "아무일도 일어나지 않았습니다."), true);
					// }
					// }
					// break;
					case BRING_STONE: {
						L1PcInstance pc = (L1PcInstance) cha;
						Random random = new Random();
						L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
						if (item != null) {
							int dark = (int) (10 + (pc.getLevel() * 0.8) + (pc.getAbility().getTotalWis() - 6) * 1.2);
							int brave = (int) (dark / 2.1);
							int wise = (int) (brave / 2.0);
							int kayser = (int) (wise / 1.9);
							int chance = random.nextInt(100) + 1;
							if (item.getItem().getItemId() == 40320) {
								pc.getInventory().removeItem(item, 1);
								if (dark >= chance) {
									pc.getInventory().storeItem(40321, 1);
									pc.sendPackets(new S_ServerMessage(403, "$2475"), true);
								} else {
									pc.sendPackets(new S_ServerMessage(280), true);
								}
							} else if (item.getItem().getItemId() == 40321) {
								pc.getInventory().removeItem(item, 1);
								if (brave >= chance) {
									pc.getInventory().storeItem(40322, 1);
									pc.sendPackets(new S_ServerMessage(403, "$2476"), true);
								} else {
									pc.sendPackets(new S_ServerMessage(280), true);
								}
							} else if (item.getItem().getItemId() == 40322) {
								pc.getInventory().removeItem(item, 1);
								if (wise >= chance) {
									pc.getInventory().storeItem(40323, 1);
									pc.sendPackets(new S_ServerMessage(403, "$2477"), true);
								} else {
									pc.sendPackets(new S_ServerMessage(280), true);
								}
							} else if (item.getItem().getItemId() == 40323) {
								pc.getInventory().removeItem(item, 1);
								if (kayser >= chance) {
									pc.getInventory().storeItem(40324, 1);
									pc.sendPackets(new S_ServerMessage(403, "$2478"), true);
								} else {
									pc.sendPackets(new S_ServerMessage(280), true);
								}
							}
						}
					}
						break;
					case SUMMON_MONSTER: {
						L1PcInstance pc = (L1PcInstance) cha;
						int level = pc.getLevel();
						if (pc.getMap().isRecallPets() || pc.isGm()) {
							// System.out.println(_targetID);
							if ((pc.getInventory().checkEquipped(20284) || pc.getInventory().checkEquipped(120284))
									&& _targetID != 0) {
								// System.out.println(_targetID);
								summonMonster(pc, _targetID);
								/*
								 * pc.sendPackets(new
								 * S_ShowSummonList(pc.getId()), true); if
								 * (!pc.isSummonMonster()) {
								 * pc.setSummonMonster(true); }
								 */
							} else {
								int summonid = 0;
								int summoncost = 6;
								int levelRange = 32;
								for (int i = 0; i < summons.length; i++) {
									if (level < levelRange || i == summons.length - 1) {
										summonid = summons[i];
										break;
									}
									levelRange += 4;
								}
								int petcost = 0;
								for (Object pet : pc.getPetList()) {
									petcost += ((L1NpcInstance) pet).getPetcost();
								}
								int charisma = pc.getAbility().getTotalCha() + 6 - petcost;
								int summoncount = charisma / summoncost;
								L1Npc npcTemp = NpcTable.getInstance().getTemplate(summonid);
								for (int i = 0; i < summoncount; i++) {
									L1SummonInstance summon = new L1SummonInstance(npcTemp, pc);
									summon.setPetcost(summoncost);
								}
							}
						} else {
							pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
						}
					}
						break;
					case LESSER_ELEMENTAL:
					case GREATER_ELEMENTAL: {
						L1PcInstance pc = (L1PcInstance) cha;
						int attr = pc.getElfAttr();
						if (attr != 0) {
							if (pc.getMap().isRecallPets() || pc.isGm()) {
								int petcost = 0;
								for (Object pet : pc.getPetList()) {
									petcost += ((L1NpcInstance) pet).getPetcost();
								}

								if (petcost == 0) {
									int summonid = 0;
									int summons[];
									if (_skillId == LESSER_ELEMENTAL) {
										summons = new int[] { 45306, 45303, 45304, 45305 };
									} else {
										summons = new int[] { 81053, 81050, 81051, 81052 };
									}
									int npcattr = 1;
									for (int i = 0; i < summons.length; i++) {
										if (npcattr == attr) {
											summonid = summons[i];
											i = summons.length;
										}
										npcattr *= 2;
									}
									if (summonid == 0) {
										Random random = new Random();
										int k3 = random.nextInt(4);
										summonid = summons[k3];
									}

									L1Npc npcTemp = NpcTable.getInstance().getTemplate(summonid);
									L1SummonInstance summon = new L1SummonInstance(npcTemp, pc);
									summon.setPetcost(pc.getAbility().getTotalCha() + 7);
									summons = null;
								}
							} else {
								pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
							}
						}
					}
						break;
					// 이부분은 전체적으로 처리를 하기때문에 제외함

					case ABSOLUTE_BARRIER: {
						L1PcInstance pc = (L1PcInstance) cha;
						// pc.stopHpRegeneration();
						// pc.stopMpRegeneration();
						pc.stopMpRegenerationByDoll();
						pc.stopHpRegenerationByDoll();
					}
						break;

					case LIGHT:
						break;
					case GLOWING_AURA: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addHitup(5);
						pc.addDmgup(5);
						pc.sendPackets(new S_SPMR(pc), true);
						pc.sendPackets(new S_SkillIconAura(113, _getBuffIconDuration), true);
					}
						break;
					case SHINING_AURA: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAC().addAc(-8);
						pc.sendPackets(new S_SkillIconAura(114, _getBuffIconDuration), true);
					}
						break;
					case BRAVE_AURA: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.sendPackets(new S_SkillIconAura(116, _getBuffIconDuration), true);
					}
						break;
					case SHIELD: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAC().addAc(-2);
						pc.sendPackets(new S_SkillIconShield(2, _getBuffIconDuration), true);
					}
						break;
					case SHADOW_ARMOR: {
						L1PcInstance pc = (L1PcInstance) cha;
						// pc.getAC().addAc(-3);
						pc.getResistance().addMr(5);
						pc.sendPackets(new S_SPMR(pc), true);
						// pc.sendPackets(new S_SkillIconShield(3,
						// _getBuffIconDuration), true);
					}
						break;
					case DRESS_DEXTERITY: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_덱업6))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_덱업6);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_덱업7))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_덱업7);
						pc.getAbility().addAddedDex((byte) 3);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
						pc.sendPackets(new S_Dexup(pc, 2, _getBuffIconDuration), true);
					}
						break;

					case DRESS_MIGHTY: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_힘업6))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_힘업6);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_힘업7))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_힘업7);
						pc.getAbility().addAddedStr((byte) 3);
						pc.sendPackets(new S_Strup(pc, 2, _getBuffIconDuration), true);
					}
						break;

					case SHADOW_FANG: {
						L1PcInstance pc = (L1PcInstance) cha;
						L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
						if (item != null && item.getItem().getType2() == 1) {
							item.setSkillWeaponEnchant(pc, _skillId, _skill.getBuffDuration() * 1000);
						} else {
							pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
						}
					}
						break;
					case ENCHANT_WEAPON: {
						L1PcInstance pc = (L1PcInstance) cha;
						L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
						if (item != null && item.getItem().getType2() == 1) {
							pc.sendPackets(
									new S_ServerMessage(161, String.valueOf(item.getLogName()).trim(), "$245", "$247"),
									true);
							item.setSkillWeaponEnchant(pc, _skillId, _skill.getBuffDuration() * 1000);
						} else {
							pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
						}
					}
						break;
					case HOLY_WEAPON:
					case BLESS_WEAPON: {
						if (!(cha instanceof L1PcInstance)) {
							return;
						}
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getWeapon() == null) {
							useok = false;
							pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
							return;
						}
						for (L1ItemInstance item : pc.getInventory().getItems()) {
							if (pc.getWeapon() != null) {
								if (pc.getWeapon().equals(item)) {
									pc.sendPackets(new S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON, 2176,
											_skill.getBuffDuration(), false, false));
									item.setSkillWeaponEnchant(pc, _skillId, _skill.getBuffDuration() * 1000);
								}
							}
							// 쌍수 블레스 웨폰까지 적용되는부분 수정
							/*
							 * if (pc.getSecondWeapon() != null) { if
							 * (pc.getSecondWeapon().equals(item)) {
							 * pc.sendPackets(new
							 * S_PacketBox(S_PacketBox.SKILL_WEAPON_ICON,2176,
							 * _skill.getBuffDuration(),true, false));
							 * item.setSkillWeaponEnchant(pc,
							 * _skillId,_skill.getBuffDuration() * 1000); } }
							 */
						}
					}
						break;
					case BLESSED_ARMOR: {
						L1PcInstance pc = (L1PcInstance) cha;
						L1ItemInstance item = pc.getInventory().getItem(_itemobjid);
						if (item != null && item.getItem().getType2() == 2 && item.getItem().getType() == 2) {
							pc.sendPackets(new S_ServerMessage(161, item.getLogName(), "$245", "$247"), true);
							item.setSkillArmorEnchant(pc, _skillId, _skill.getBuffDuration() * 1000);
						} else {
							pc.sendPackets(new S_SystemMessage("아무일도 일어나지 않았습니다."), true);
						}
					}
						break;
					case EARTH_BLESS:
						break;
					case RESIST_MAGIC: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getResistance().addMr(10);
						pc.sendPackets(new S_SPMR(pc), true);
					}
						break;
					case CLEAR_MIND: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAbility().addAddedWis((byte) 3);
						pc.resetBaseMr();
						pc.sendPackets(new S_SPMR(pc), true);
					}
						break;
					case RESIST_ELEMENTAL: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getResistance().addAllNaturalResistance(10);
						pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					}
						break;

					case BODY_TO_MIND: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.setCurrentMp(pc.getCurrentMp() + 2);
					}
						break;
					case BLOODY_SOUL: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.setCurrentMp(pc.getCurrentMp() + 19);// 20
					}
						break;
					case ELEMENTAL_PROTECTION: {
						L1PcInstance pc = (L1PcInstance) cha;
						int attr = pc.getElfAttr();
						if (attr == 1) {
							pc.getResistance().addEarth(50);
						} else if (attr == 2) {
							pc.getResistance().addFire(50);
						} else if (attr == 4) {
							pc.getResistance().addWater(50);
						} else if (attr == 8) {
							pc.getResistance().addWind(50);
						}
					}
						break;
					/*
					 * case INVISIBILITY: case BLIND_HIDING:{ L1PcInstance pc =
					 * (L1PcInstance) cha;
					 * 
					 * for (L1DollInstance doll : pc.getDollList()) {
					 * doll.deleteDoll(); pc.sendPackets(new S_SkillIconGFX(56,
					 * 0), true); pc.sendPackets(new S_OwnCharStatus(pc), true);
					 * }
					 * 
					 * 
					 * pc.sendPackets(new S_Invis(pc.getId(), 1), true);
					 * Broadcaster.broadcastPacket(pc, new S_Invis(pc.getId(),
					 * 1), true); Broadcaster.broadcastPacket(pc, new
					 * S_RemoveObject(pc.getId()), true); } break;
					 */

					case INVISIBILITY: {
						L1PcInstance pc = (L1PcInstance) cha;

						for (L1DollInstance doll : pc.getDollList()) {
							doll.deleteDoll();
							pc.sendPackets(new S_SkillIconGFX(56, 0));
							pc.sendPackets(new S_OwnCharStatus(pc));
						}
						pc.sendPackets(new S_Invis(pc.getId(), 1));
						Broadcaster.broadcastPacket(pc, new S_Invis(pc.getId(), 1));

						/*
						 * if (pc.isInParty()) { for (L1PcInstance tar :
						 * L1World.getInstance().getVisiblePlayer(pc,-1)) {
						 * if(pc.getParty().isMember(tar)){ tar.sendPackets(new
						 * S_OtherCharPacks(pc, tar, true)); } } }
						 */

						// S_RemoveObject sremove = new
						// S_RemoveObject(pc.getId());
						for (L1PcInstance pc2 : L1World.getInstance().getVisiblePlayer(pc)) {
							// pc2.sendPackets(sremove);
							if (pc2.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_FLOATING_EYE)
									&& pc2.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.CURSE_BLIND)) {
								pc2.sendPackets(new S_OtherCharPacks(pc, pc2, true));
							}
						}

					}
						break;
					case BLIND_HIDING: {
						L1PcInstance pc = (L1PcInstance) cha;

						for (L1DollInstance doll : pc.getDollList()) {
							doll.deleteDoll();
							pc.sendPackets(new S_SkillIconGFX(56, 0));
							pc.sendPackets(new S_OwnCharStatus(pc));
						}

						pc.sendPackets(new S_Invis(pc.getId(), 2));
						Broadcaster.broadcastPacket(pc, new S_Invis(pc.getId(), 2));

						if (pc.isInParty()) {
							for (L1PcInstance tar : L1World.getInstance().getVisiblePlayer(pc)) {
								if (pc.getParty().isMember(tar)) {
									tar.sendPackets(new S_OtherCharPacks(pc, tar, true));
								}
							}
						}

						// S_RemoveObject sremove = new
						// S_RemoveObject(pc.getId());

						for (L1PcInstance pc2 : L1World.getInstance().getVisiblePlayer(pc)) {
							// pc2.sendPackets(sremove);
							if (pc2.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_FLOATING_EYE)
									&& pc2.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.CURSE_BLIND)) {
								pc2.sendPackets(new S_OtherCharPacks(pc, pc2, true));
							}
						}
					}
						break;

					case IRON_SKIN: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAC().addAc(-10);
						pc.sendPackets(new S_SkillIconShield(10, _getBuffIconDuration), true);
					}
						break;
					case EARTH_SKIN: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAC().addAc(-6);
						pc.sendPackets(new S_SkillIconShield(6, _getBuffIconDuration), true);
					}
						break;
					case PHYSICAL_ENCHANT_STR: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_힘업6))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_힘업6);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_힘업7))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_힘업7);
						pc.getAbility().addAddedStr((byte) 5);
						pc.sendPackets(new S_Strup(pc, 5, _getBuffIconDuration), true);
					}
						break;
					case PHYSICAL_ENCHANT_DEX: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_덱업6))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_덱업6);
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_덱업7))
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_덱업7);
						pc.getAbility().addAddedDex((byte) 5);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
						pc.sendPackets(new S_Dexup(pc, 5, _getBuffIconDuration), true);
					}
						break;
					case FIRE_WEAPON: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(4);
						pc.sendPackets(new S_SkillIconAura(147, _getBuffIconDuration), true);
					}
						break;
					case FIRE_BLESS: {
						L1PcInstance pc = (L1PcInstance) cha;

						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.WIND_WALK)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.WIND_WALK);
						}
						if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_ELFBRAVE)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(STATUS_ELFBRAVE);
						}
						pc.getMoveState().setBraveSpeed(1);
						pc.sendPackets(new S_SkillBrave(pc.getId(), 1, _getBuffIconDuration), true);
						Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 1, 0), true);

						// pc.addDmgup(4);
						// pc.sendPackets(new S_SkillIconAura(154,
						// _getBuffIconDuration), true);
					}
						break;
					case BURNING_WEAPON: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(6);
						pc.addHitup(6);
						pc.sendPackets(new S_SkillIconAura(162, _getBuffIconDuration), true);
					}
						break;
					case WIND_SHOT: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addBowHitup(6);
						pc.sendPackets(new S_SkillIconAura(148, _getBuffIconDuration), true);
					}
						break;
					case STORM_EYE: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addBowHitup(2);
						pc.addBowDmgup(3);
						pc.sendPackets(new S_SkillIconAura(155, _getBuffIconDuration), true);
					}
						break;
					case STORM_SHOT: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addBowDmgup(6);
						pc.addBowHitup(3);
						pc.sendPackets(new S_SkillIconAura(165, _getBuffIconDuration), true);
					}
						break;
					case BERSERKERS: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAC().addAc(10);
						pc.addDmgup(5);
						pc.addHitup(2);
					}
						break;
					case SCALES_EARTH_DRAGON: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getResistance().addHold(10);
						pc.getAC().addAc(-3);
						pc.sendPackets(new S_OwnCharAttrDef(pc), true);
						/*
						 * L1PcInstance pc = (L1PcInstance) cha;
						 * if(pc.getMapId() >= 9000 && pc.getMapId() <= 9099){
						 * pc.sendPackets(new S_ServerMessage(1170), true); //
						 * 이곳에서 변신할수 없습니다. return; } pc.addMaxHp(35);
						 * pc.getAC().addAc(-8); pc.sendPackets(new
						 * S_OwnCharAttrDef(pc), true); pc.sendPackets(new
						 * S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
						 * if (pc.isInParty()) { pc.getParty().updateMiniHP(pc);
						 * } pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(),
						 * pc.getMaxMp()), true);
						 * pc.getGfxId().setTempCharGfx(9362);
						 * pc.sendPackets(new S_ChangeShape(pc.getId(), 9362),
						 * true); if (!pc.isGmInvis() && !pc.isInvisble()) {
						 * Broadcaster.broadcastPacket(pc, new
						 * S_ChangeShape(pc.getId(), 9362), true); }
						 * pc.startMpDecreaseByScales(); L1ItemInstance item =
						 * pc.getWeapon(); if(item != null){
						 * pc.getInventory().setEquipped(item, false);
						 * pc.getInventory().setEquipped(item, true); }
						 * pc.sendPackets(new S_SystemMessage(
						 * "안타라스 각성 : 양손검 공격속도 특화"), true); pc.sendPackets(new
						 * S_SystemMessage(
						 * "각성 업데이트로 당분간 각성마법(브레스등등)은 사용할 수 없습니다." ), true);
						 */
					}
						break;
					case SCALES_WATER_DRAGON: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getResistance().addFreeze(10);
						/*
						 * if(pc.getMapId() >= 9000 && pc.getMapId() <= 9099){
						 * pc.sendPackets(new S_ServerMessage(1170), true); //
						 * 이곳에서 변신할수 없습니다. return; }
						 * pc.getResistance().addMr(15);
						 * pc.getResistance().addAllNaturalResistance(15);
						 * pc.sendPackets(new S_SPMR(pc), true);
						 * pc.sendPackets(new S_OwnCharAttrDef(pc), true);
						 * pc.getGfxId().setTempCharGfx(9365);
						 * pc.sendPackets(new S_ChangeShape(pc.getId(), 9365),
						 * true); if (!pc.isGmInvis() && !pc.isInvisble()) {
						 * Broadcaster.broadcastPacket(pc, new
						 * S_ChangeShape(pc.getId(), 9365), true); }
						 * L1ItemInstance item = pc.getWeapon(); if(item !=
						 * null){ pc.getInventory().setEquipped(item, false);
						 * pc.getInventory().setEquipped(item, true); }
						 * pc.startMpDecreaseByScales(); pc.sendPackets(new
						 * S_SystemMessage("파푸리온 각성 : 이동속도 특화"), true);
						 * pc.sendPackets(new S_SystemMessage(
						 * "각성 업데이트로 당분간 각성마법(브레스등등)은 사용할 수 없습니다." ), true);
						 */
					}
						break;
					case SCALES_FIRE_DRAGON: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getResistance().addStun(10);
						pc.addHitup(5);
						/*
						 * if(pc.getMapId() >= 9000 && pc.getMapId() <= 9099){
						 * pc.sendPackets(new S_ServerMessage(1170)); // 이곳에서
						 * 변신할수 없습니다. return; }
						 * pc.getAbility().addAddedStr((byte) 3);
						 * pc.getAbility().addAddedDex((byte) 3);
						 * pc.getAbility().addAddedCon((byte) 3);
						 * pc.getAbility().addAddedInt((byte) 3);
						 * pc.getAbility().addAddedWis((byte) 3);
						 * pc.getGfxId().setTempCharGfx(9363);
						 * pc.sendPackets(new S_ChangeShape(pc.getId(), 9363),
						 * true); if (!pc.isGmInvis() && !pc.isInvisble()) {
						 * Broadcaster.broadcastPacket(pc, new
						 * S_ChangeShape(pc.getId(), 9363), true); }
						 * L1ItemInstance item = pc.getWeapon(); if(item !=
						 * null){ pc.getInventory().setEquipped(item, false);
						 * pc.getInventory().setEquipped(item, true); }
						 * pc.startMpDecreaseByScales(); pc.sendPackets(new
						 * S_SystemMessage("발라카스 각성 : 도끼 공격속도 특화"), true);
						 * pc.sendPackets(new S_SystemMessage(
						 * "각성 업데이트로 당분간 각성마법(브레스등등)은 사용할 수 없습니다." ), true);
						 */
					}
						break;
					case IllUSION_OGRE: { // 일루젼 오거
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(4);
						pc.addHitup(4);
					}
						break;
					case IllUSION_LICH: { // 일루젼 리치
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAbility().addSp(2);
						pc.sendPackets(new S_SPMR(pc), true);
					}
						break;
					case IllUSION_DIAMONDGOLEM: { // 일루젼 다이아골렘
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAC().addAc(-8);
						pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					}
						break;
					case IllUSION_AVATAR: { // 일루젼 아바타
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(10);
					}
						break;
					case INSIGHT: { // 인사이트
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAbility().addAddedStr((byte) 1);
						pc.getAbility().addAddedDex((byte) 1);
						pc.getAbility().addAddedCon((byte) 1);
						pc.getAbility().addAddedInt((byte) 1);
						pc.getAbility().addAddedWis((byte) 1);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
						pc.resetBaseMr();
					}
						break;

					case ADVANCE_SPIRIT: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.setAdvenHp(pc.getBaseMaxHp() / 5);
						pc.setAdvenMp(pc.getBaseMaxMp() / 5);
						pc.addMaxHp(pc.getAdvenHp());
						pc.addMaxMp(pc.getAdvenMp());
						pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
						if (pc.isInParty()) {
							pc.getParty().updateMiniHP(pc);
						}
					}
						break;
					case GREATER_HASTE: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getHasteItemEquipped() > 0) {
							continue;
						}
						if (pc.getMoveState().getMoveSpeed() != 2) {
							if (pc.getSkillEffectTimerSet().hasSkillEffect(HASTE)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(HASTE);
								pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0), true);
								Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 0, 0), true);
								pc.getMoveState().setMoveSpeed(0);
							} else if (pc.getSkillEffectTimerSet().hasSkillEffect(GREATER_HASTE)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(GREATER_HASTE);
								pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0), true);
								Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 0, 0), true);
								pc.getMoveState().setMoveSpeed(0);
							} else if (pc.getSkillEffectTimerSet().hasSkillEffect(STATUS_HASTE)) {
								pc.getSkillEffectTimerSet().killSkillEffectTimer(STATUS_HASTE);
								pc.sendPackets(new S_SkillHaste(pc.getId(), 0, 0), true);
								Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 0, 0), true);
								pc.getMoveState().setMoveSpeed(0);
							}
							pc.setDrink(false);
							pc.getMoveState().setMoveSpeed(1);
							pc.sendPackets(new S_SkillHaste(pc.getId(), 1, _getBuffIconDuration), true);
							Broadcaster.broadcastPacket(pc, new S_SkillHaste(pc.getId(), 1, 0), true);
						} else {
							int skillNum = 0;
							if (pc.getSkillEffectTimerSet().hasSkillEffect(SLOW)) {
								skillNum = SLOW;
							} else if (pc.getSkillEffectTimerSet().hasSkillEffect(MASS_SLOW)) {
								skillNum = MASS_SLOW;
							} else if (pc.getSkillEffectTimerSet().hasSkillEffect(ENTANGLE)) {
								skillNum = ENTANGLE;
							} else if (pc.getSkillEffectTimerSet().hasSkillEffect(MOB_SLOW_1)) {
								skillNum = MOB_SLOW_1;
							} else if (pc.getSkillEffectTimerSet().hasSkillEffect(MOB_SLOW_18)) {
								skillNum = MOB_SLOW_18;
							}
							if (skillNum != 0) {
								pc.getSkillEffectTimerSet().removeSkillEffect(skillNum);
								pc.getSkillEffectTimerSet().removeSkillEffect(GREATER_HASTE);
								pc.getMoveState().setMoveSpeed(0);
								continue;
							}
						}
					}
						break;
					// case BUFF_SAEL: {
					// if(cha instanceof L1PcInstance) {
					// L1PcInstance pc = (L1PcInstance) cha;
					// pc.addHitup(5);
					// pc.addDmgup(1);
					// pc.addBowHitup(5);
					// pc.addBowDmgup(1);
					// pc.addExp(30);
					// pc.addMaxHp(100);
					// pc.addMaxMp(50);
					// pc.addHpr(3);
					// pc.addMpr(3);
					// pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(),
					// pc.getMaxHp()));
					// pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(),
					// pc.getMaxMp()));
					// pc.sendPackets(new S_SPMR(pc));
					// }
					// }
					// break;
					case HOLY_WALK:
					case MOVING_ACCELERATION:
					case WIND_WALK: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getMoveState().setBraveSpeed(4);
						pc.sendPackets(new S_SkillBrave(pc.getId(), 4, _getBuffIconDuration), true);
						Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 4, 0), true);
					}
						break;
					case BLOOD_LUST: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_FRUIT)) {
							pc.getSkillEffectTimerSet().killSkillEffectTimer(L1SkillId.STATUS_FRUIT);
							pc.sendPackets(new S_SkillBrave(pc.getId(), 0, 0), true);
							pc.getMoveState().setBraveSpeed(0);
						}
						pc.getMoveState().setBraveSpeed(1);
						pc.sendPackets(new S_SkillBrave(pc.getId(), 1, _getBuffIconDuration), true);
						Broadcaster.broadcastPacket(pc, new S_SkillBrave(pc.getId(), 1, 0), true);
					}
						break;
					case STATUS_TIKAL_BOSSJOIN: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addHitup(5);
						pc.addDmgup(10);
						pc.addBowHitup(5);
						pc.addBowDmgup(10);
						pc.getAbility().addAddedStr((byte) 3);
						pc.getAbility().addAddedDex((byte) 3);
						pc.getAbility().addAddedCon((byte) 3);
						pc.getAbility().addAddedInt((byte) 3);
						pc.getAbility().addAddedWis((byte) 3);
						pc.getAbility().addSp(3);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
					}
						break;
					case STATUS_TIKAL_BOSSDIE: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addHitup(5);
						pc.addDmgup(5);
						pc.addBowHitup(5);
						pc.addBowDmgup(5);
						pc.getAbility().addAddedStr((byte) 2);
						pc.getAbility().addAddedDex((byte) 2);
						pc.getAbility().addAddedCon((byte) 2);
						pc.getAbility().addAddedInt((byte) 2);
						pc.getAbility().addAddedWis((byte) 2);
						pc.getAbility().addSp(1);
					}
						break;
					case LIND_MAAN: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(LIND_MAAN)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(LIND_MAAN);
						}
						pc.getResistance().addSleep(15); // 수면내성3
					}
						break;

					case FAFU_MAAN: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(FAFU_MAAN)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(FAFU_MAAN);
						}
						pc.getResistance().addFreeze(15); // 동빙내성3
					}
						break;
					case ANTA_MAAN: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(ANTA_MAAN)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(ANTA_MAAN);
						}
						pc.getResistance().addHold(15);

						// pc.addDamageReductionByArmor(3);
						// pc.getResistance().addPetrifaction(3); //석화내성3
					}
						break;
					case VALA_MAAN: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(VALA_MAAN)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(VALA_MAAN);
						}
						// pc.addDamageReductionByArmor(3);
						pc.getResistance().addStun(15); // 스턴내성3
					}
						break;
					case BIRTH_MAAN: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(BIRTH_MAAN)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(BIRTH_MAAN);
						}
						// pc.addDamageReductionByArmor(5);
						pc.getResistance().addHold(15); // 홀드
						pc.getResistance().addFreeze(15); // 동빙내성3
					}
						break;
					case SHAPE_MAAN: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(SHAPE_MAAN)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(SHAPE_MAAN);
						}
						pc.getResistance().addHold(15); // 홀드
						pc.getResistance().addFreeze(15); // 동빙내성3
						pc.getResistance().addSleep(15); // 수면내성3
					}
						break;
					case LIFE_MAAN: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(LIFE_MAAN)) {
							pc.getSkillEffectTimerSet().removeSkillEffect(LIFE_MAAN);
						}
						pc.getResistance().addHold(15); // 홀드
						pc.getResistance().addFreeze(15); // 동빙내성3
						pc.getResistance().addSleep(15); // 수면내성3
						pc.getResistance().addStun(15); // 스턴내성3
					}
						break;
					case STATUS_COMA_3: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_COMA_5))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_COMA_5);
						pc.getAC().addAc(-3);
						pc.addHitup(3);
						pc.getAbility().addAddedStr((byte) 5);
						pc.getAbility().addAddedDex((byte) 5);
						pc.getAbility().addAddedCon((byte) 1);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
					}
						break;
					case STATUS_COMA_5: {
						L1PcInstance pc = (L1PcInstance) cha;
						if (pc.getSkillEffectTimerSet().hasSkillEffect(L1SkillId.STATUS_COMA_3))
							pc.getSkillEffectTimerSet().removeSkillEffect(L1SkillId.STATUS_COMA_3);
						pc.getAC().addAc(-8);
						pc.addHitup(5);
						pc.getAbility().addAddedStr((byte) 5);
						pc.getAbility().addAddedDex((byte) 5);
						pc.getAbility().addAddedCon((byte) 1);
						pc.getAbility().addSp(1);
						pc.sendPackets(new S_SPMR(pc), true);
						pc.sendPackets(new S_PacketBox(S_PacketBox.char_ER, pc.get_PlusEr()), true);
					}
						break;
					case FEATHER_BUFF_A: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addDmgup(2);
						pc.addHitup(2);
						pc.getAbility().addSp(2);
						pc.sendPackets(new S_SPMR(pc), true);
						pc.addMaxHp(50);
						pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
						if (pc.isInParty())
							pc.getParty().updateMiniHP(pc);
						pc.addHpr(3);
						pc.addMaxMp(30);
						pc.addMpr(3);
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
					}
						break;
					case FEATHER_BUFF_B: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addHitup(2);
						pc.getAbility().addSp(1);
						pc.sendPackets(new S_SPMR(pc), true);
						pc.addMaxHp(50);
						pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
						if (pc.isInParty())
							pc.getParty().updateMiniHP(pc);
						pc.addMaxMp(30);
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
					}
						break;
					case FEATHER_BUFF_C: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.addMaxHp(50);
						pc.sendPackets(new S_HPUpdate(pc.getCurrentHp(), pc.getMaxHp()), true);
						if (pc.isInParty())
							pc.getParty().updateMiniHP(pc);
						pc.addMaxMp(30);
						pc.sendPackets(new S_MPUpdate(pc.getCurrentMp(), pc.getMaxMp()), true);
						pc.getAC().addAc(-2);
						pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					}
						break;
					case FEATHER_BUFF_D: {
						L1PcInstance pc = (L1PcInstance) cha;
						pc.getAC().addAc(-1);
						pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					}
						break;
					default:
						break;
					}
				}

				if (_calcType == PC_NPC || _calcType == NPC_NPC) {
					if (_skillId == TAMING_MONSTER && ((L1MonsterInstance) cha).getNpcTemplate().isTamable()) {
						int petcost = 0;
						for (Object pet : ((L1PcInstance) _user).getPetList()) {
							petcost += ((L1NpcInstance) pet).getPetcost();
						}
						int charisma = _user.getAbility().getTotalCha();
						if (_player.isElf()) {
							charisma += 12;
						} else if (_player.isWizard()) {
							charisma += 6;
						}
						charisma -= petcost;
						if (charisma >= 6) {
							L1SummonInstance summon = new L1SummonInstance(_targetNpc, (L1PcInstance) _user, false);
							_target = summon;
						} else {
							_player.sendPackets(new S_ServerMessage(319), true);
						}
					} else if (_skillId == CREATE_ZOMBIE) {
						int petcost = 0;
						for (Object pet : ((L1PcInstance) _user).getPetList()) {
							petcost += ((L1NpcInstance) pet).getPetcost();
						}
						int charisma = _user.getAbility().getTotalCha();
						if (_player.isElf()) {
							charisma += 12;
						} else if (_player.isWizard()) {
							charisma += 6;
						}
						charisma -= petcost;
						if (charisma >= 6) {
							L1SummonInstance summon = new L1SummonInstance(_targetNpc, (L1PcInstance) _user, true);
							_target = summon;
						} else {
							_player.sendPackets(new S_ServerMessage(319), true);
						}
					} else if (_skillId == WEAK_ELEMENTAL) {
						if (cha instanceof L1MonsterInstance) {
							L1Npc npcTemp = ((L1MonsterInstance) cha).getNpcTemplate();
							int weakAttr = npcTemp.get_weakAttr();
							if ((weakAttr & 1) == 1) {
								Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), 2169), true);
							}
							if ((weakAttr & 2) == 2) {
								Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), 2167), true);
							}
							if ((weakAttr & 4) == 4) {
								Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), 2166), true);
							}
							if ((weakAttr & 8) == 8) {
								Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), 2168), true);
							}
						}
					} else if (_skillId == RETURN_TO_NATURE) {
						if (Config.RETURN_TO_NATURE && cha instanceof L1SummonInstance) {
							L1SummonInstance summon = (L1SummonInstance) cha;
							Broadcaster.broadcastPacket(summon, new S_SkillSound(summon.getId(), 2245), true);
							summon.returnToNature();
						} else {
							if (_user instanceof L1PcInstance) {
								_player.sendPackets(new S_ServerMessage(79), true);
							}
						}
					}
				}

				/*
				 * if(cha instanceof L1PcInstance){ L1PcInstance pc =
				 * (L1PcInstance) cha; if (_skill.getType() ==
				 * L1Skills.TYPE_HEAL){
				 * if(pc.getSkillEffectTimerSet().hasSkillEffect(10518)){ if(dmg
				 * > 0){ dmg *= -1; } } } }
				 */

				if (_skillId == 하울) {
					dmg = 40;// 하울데미지
				}

				if (_calcType == PC_NPC) {
					if (_skill.getType() == L1Skills.TYPE_ATTACK) {
						if (dmg < 1) {
							dmg = 1;
						}
					}
				}

				if (_skill.getType() == L1Skills.TYPE_HEAL && _calcType == PC_NPC && undeadType == 1) {
					dmg *= -1;

					dmg *= 0.5;// 힐 언데드 데미지 하향
				}

				if (_skill.getType() == L1Skills.TYPE_HEAL && _calcType == PC_NPC && undeadType == 3) {
					dmg = 0;
				}

				if ((cha instanceof L1TowerInstance || cha instanceof L1DoorInstance) && dmg < 0) {
					dmg = 0;
				}

				// System.out.println("1111111111 : " + dmg);
				if (dmg != 0 || drainMana != 0) {
					if (cha instanceof L1PcInstance) {
						L1PcInstance pc = (L1PcInstance) cha;
						boolean success = true;
						if (_skillId == L1SkillId.ERUPTION) {
							long time = System.currentTimeMillis();
							if (pc.ANTI_ERUPTION >= time)
								success = false;
							else
								pc.ANTI_ERUPTION = time + 500;
						} else if (_skillId == L1SkillId.METEOR_STRIKE) {
							long time = System.currentTimeMillis();
							if (pc.ANTI_METEOR_STRIKE >= time)
								success = false;
							else
								pc.ANTI_METEOR_STRIKE = time + 1000;
						} else if (_skillId == L1SkillId.SUNBURST) {
							long time = System.currentTimeMillis();
							if (pc.ANTI_SUNBURST >= time)
								success = false;
							else
								pc.ANTI_SUNBURST = time + 500;
						} else if (_skillId == L1SkillId.CALL_LIGHTNING) {
							long time = System.currentTimeMillis();
							if (pc.ANTI_CALL_LIGHTNING >= time)
								success = false;
							else
								pc.ANTI_CALL_LIGHTNING = time + 500;
						} else if (_skillId == L1SkillId.DISINTEGRATE) {
							long time = System.currentTimeMillis();
							if (pc.ANTI_DISINTEGRATE >= time)
								success = false;
							else
								pc.ANTI_DISINTEGRATE = time + 1000;
						} else if (_skillId == L1SkillId.BONE_BREAK) {
							long time = System.currentTimeMillis();
							if (pc.ANTI_BONE_BREAK >= time)
								success = false;
							else
								pc.ANTI_BONE_BREAK = time + 500;
						} else if (_skillId == L1SkillId.FINAL_BURN) {
							long time = System.currentTimeMillis();
							if (pc.ANTI_FINAL_BURN >= time)
								success = false;
							else
								pc.ANTI_FINAL_BURN = time + 500;
						} else if (_skillId == L1SkillId.ICE_SPIKE) {
							long time = System.currentTimeMillis();
							if (pc.ANTI_ICE_SPIKE >= time)
								success = false;
							else
								pc.ANTI_ICE_SPIKE = time + 500;
						}
						if (success)
							_magic.commit(dmg, drainMana, _skillId);

					} else {
						_magic.commit(dmg, drainMana, _skillId);
					}

				}

				if (heal > 0) {
					if ((heal + _user.getCurrentHp()) > _user.getMaxHp()) {
						_user.setCurrentHp(_user.getMaxHp());
					} else {
						_user.setCurrentHp(heal + _user.getCurrentHp());
					}
				}

				if (cha instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) cha;
					pc.getLight().turnOnOffLight();
					pc.sendPackets(new S_OwnCharAttrDef(pc), true);
					pc.sendPackets(new S_OwnCharStatus(pc), true);
					sendHappenMessage(pc);
				}

				addMagicList(cha, false);
				if (cha instanceof L1PcInstance) {
					L1PcInstance pc = (L1PcInstance) cha;
					pc.getLight().turnOnOffLight();
				}
			}

			if (_skillId == DETECTION || _skillId == COUNTER_DETECTION || _skillId == FREEZING_BREATH) {
				detection(_player);
			}
		} catch (Exception e) {
			// 스킬 오류 발생 부분에 케릭터명, 몹명, 타켓명순으로 출력
			e.printStackTrace();
			// System.out.println("오류 발생 : " + _player.getAccountName() + " | "
			// + _npc.getName() + " | " + _target.getName());
			// _log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	private boolean isNotCancelable(int skillNum) {
		return skillNum == ENCHANT_WEAPON || skillNum == BLESSED_ARMOR || skillNum == ABSOLUTE_BARRIER
				|| skillNum == ADVANCE_SPIRIT || skillNum == SHOCK_STUN || skillNum == 기간틱 || skillNum == 파워그립
				|| skillNum == 데스페라도 || skillNum == STATUS_UNDERWATER_BREATH || skillNum == SHADOW_FANG
				|| skillNum == REDUCTION_ARMOR || skillNum == SOLID_CARRIAGE || skillNum == COUNTER_BARRIER
				|| skillNum == SHADOW_ARMOR || skillNum == ARMOR_BREAK || skillNum == DRESS_EVASION
				|| skillNum == UNCANNY_DODGE || skillNum == SCALES_EARTH_DRAGON || skillNum == SCALES_WATER_DRAGON
				|| skillNum == SCALES_FIRE_DRAGON || skillNum == BOUNCE_ATTACK || skillNum == IllUSION_OGRE
				|| skillNum == IllUSION_LICH || skillNum == IllUSION_DIAMONDGOLEM || skillNum == IllUSION_AVATAR
				|| skillNum == STATUS_DRAGONPERL;

	}

	private void detection(L1PcInstance pc) {
		if (pc != null) {
			if (pc != null && !pc.isGmInvis() && pc.isInvisble()) {
				pc.delInvis();
				pc.beginInvisTimer();
			}
			for (L1PcInstance tgt : L1World.getInstance().getVisiblePlayer(pc)) {
				if (!tgt.isGmInvis() && tgt.isInvisble()) {
					tgt.delInvis();
					tgt.beginInvisTimer();
				}
			}
			L1WorldTraps.getInstance().onDetection(pc);
		} else if (_user != null && _user instanceof GambleInstance) {
			for (L1PcInstance tgt : L1World.getInstance().getVisiblePlayer(_user)) {
				if (!tgt.isGmInvis() && tgt.isInvisble()) {
					tgt.delInvis();
					tgt.beginInvisTimer();
				}
			}
		}
	}

	private boolean isTargetCalc(L1Character cha) {
		if (_skill.getTarget().equals("attack") && _skillId != 18) {
			if (isPcSummonPet(cha)) {
				if (CharPosUtil.getZoneType(_player) == 1 || CharPosUtil.getZoneType(cha) == 1
						|| _player.checkNonPvP(_player, cha)) {
					return false;
				}
			}
		}

		if (_skillId == FOG_OF_SLEEPING && _user.getId() == cha.getId()) {
			return false;
		}

		if (_skillId == MASS_SLOW) {
			if (_user.getId() == cha.getId()) {
				return false;
			}
			if (cha instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) cha;
				if (_user.getId() == summon.getMaster().getId()) {
					return false;
				}
			} else if (cha instanceof L1PetInstance) {
				L1PetInstance pet = (L1PetInstance) cha;
				if (_user.getId() == pet.getMaster().getId()) {
					return false;
				}
			}
		}

		if (_skillId == MASS_TELEPORT) {
			if (_user.getId() != cha.getId()) {
				return false;
			}
		}

		return true;
	}

	private boolean isPcSummonPet(L1Character cha) {
		if (_calcType == PC_PC) {
			return true;
		}

		if (_calcType == PC_NPC) {
			if (cha instanceof L1SummonInstance) {
				L1SummonInstance summon = (L1SummonInstance) cha;
				if (summon.isExsistMaster()) {
					return true;
				}
			}
			if (cha instanceof L1PetInstance || cha instanceof GambleInstance || cha instanceof L1NpcShopInstance) {
				return true;
			}
		}
		return false;
	}

	private boolean isUseCounterMagic(L1Character cha) {
		if (_isCounterMagic && cha.getSkillEffectTimerSet().hasSkillEffect(COUNTER_MAGIC)) {
			cha.getSkillEffectTimerSet().removeSkillEffect(COUNTER_MAGIC);
			// int castgfx =
			// SkillsTable.getInstance().getTemplate(COUNTER_MAGIC).getCastGfx();
			Broadcaster.broadcastPacket(cha, new S_SkillSound(cha.getId(), 10702), true);
			if (cha instanceof L1PcInstance) {
				L1PcInstance pc = (L1PcInstance) cha;
				pc.sendPackets(new S_SkillSound(pc.getId(), 10702), true);
			}
			return true;
		}
		return false;
	}

	private boolean isTargetFailure(L1Character cha) {
		boolean isTU = false;
		boolean isErase = false;
		boolean isManaDrain = false;
		int undeadType = 0;

		if (cha instanceof L1TowerInstance || cha instanceof L1DoorInstance) {
			return true;
		}

		if (cha instanceof L1PcInstance) {
			if (_calcType == PC_PC && _player.checkNonPvP(_player, cha)) {
				L1PcInstance pc = (L1PcInstance) cha;
				if (_player.getId() == pc.getId() || (pc.getClanid() != 0 && _player.getClanid() == pc.getClanid())) {
					return false;
				}
				return true;
			}
			return false;
		}

		if (cha instanceof L1MonsterInstance) {
			isTU = ((L1MonsterInstance) cha).getNpcTemplate().get_IsTU();
			isErase = ((L1MonsterInstance) cha).getNpcTemplate().get_IsErase();
			undeadType = ((L1MonsterInstance) cha).getNpcTemplate().get_undead();
			isManaDrain = true;
		}
		if ((_skillId == TURN_UNDEAD && (undeadType == 0 || undeadType == 2))
				|| (_skillId == TURN_UNDEAD && isTU == false)
				|| ((_skillId == ERASE_MAGIC || _skillId == SLOW || _skillId == MOB_SLOW_1 || _skillId == MOB_SLOW_18
						|| _skillId == MANA_DRAIN || _skillId == MASS_SLOW || _skillId == ENTANGLE
						|| _skillId == WIND_SHACKLE) && isErase == false)
				|| (_skillId == MANA_DRAIN && isManaDrain == false)) {
			return true;
		}
		return false;
	}

	public static int checkObject(int x, int y, short m, int d) {
		L1Map map = L1WorldMap.getInstance().getMap(m);
		switch (d) {
		case 1:
			if (map.isPassable(x, y, 1)) {
				return 1;
			} else if (map.isPassable(x, y, 0)) {
				return 0;
			} else if (map.isPassable(x, y, 2)) {
				return 2;
			}
			break;
		case 2:
			if (map.isPassable(x, y, 2)) {
				return 2;
			} else if (map.isPassable(x, y, 1)) {
				return 1;
			} else if (map.isPassable(x, y, 3)) {
				return 3;
			}
			break;
		case 3:
			if (map.isPassable(x, y, 3)) {
				return 3;
			} else if (map.isPassable(x, y, 2)) {
				return 2;
			} else if (map.isPassable(x, y, 4)) {
				return 4;
			}
			break;
		case 4:
			if (map.isPassable(x, y, 4)) {
				return 4;
			} else if (map.isPassable(x, y, 3)) {
				return 3;
			} else if (map.isPassable(x, y, 5)) {
				return 5;
			}
			break;
		case 5:
			if (map.isPassable(x, y, 5)) {
				return 5;
			} else if (map.isPassable(x, y, 4)) {
				return 4;
			} else if (map.isPassable(x, y, 6)) {
				return 6;
			}
			break;
		case 6:
			if (map.isPassable(x, y, 6)) {
				return 6;
			} else if (map.isPassable(x, y, 5)) {
				return 5;
			} else if (map.isPassable(x, y, 7)) {
				return 7;
			}
			break;
		case 7:
			if (map.isPassable(x, y, 7)) {
				return 7;
			} else if (map.isPassable(x, y, 6)) {
				return 6;
			} else if (map.isPassable(x, y, 0)) {
				return 0;
			}
			break;
		case 0:
			if (map.isPassable(x, y, 0)) {
				return 0;
			} else if (map.isPassable(x, y, 7)) {
				return 7;
			} else if (map.isPassable(x, y, 1)) {
				return 1;
			}
			break;
		default:
			break;
		}
		return -1;
	}

	class TargetLineSkillEffect implements Runnable {
		private byte HEADING_TABLE_X[] = { 0, 1, 1, 1, 0, -1, -1, -1 };
		private byte HEADING_TABLE_Y[] = { -1, -1, 0, 1, 1, 1, 0, -1 };

		int effect = 0;
		L1Character caster = null;
		L1Character target = null;

		public TargetLineSkillEffect(L1Character _c, L1Character _t, int _e) {
			caster = _c;
			target = _t;
			effect = _e;
		}

		public void run() {
			try {
				if (caster == null || target == null)
					return;
				int xx = caster.getX();
				int yy = caster.getY();
				int ia = caster.getLocation().getTileLineDistance(target.getLocation());
				for (int i = 0; i < ia; i++) {
					int a = CharPosUtil.calcheading(xx, yy, target.getX(), target.getY());
					int x = xx;
					int y = yy;
					x += HEADING_TABLE_X[a];
					y += HEADING_TABLE_Y[a];

					Broadcaster.broadcastPacket(caster, new S_EffectLocation(x, y, (short) effect), true);
					if (target.getX() == x && target.getY() == y)
						break;
					xx = x;
					yy = y;
					Thread.sleep(100);
				}
				if (target instanceof L1PcInstance)
					((L1PcInstance) target).sendPackets(new S_DoActionGFX(target.getId(), ActionCodes.ACTION_Damage),
							true);
				Broadcaster.broadcastPacket(target, new S_DoActionGFX(target.getId(), ActionCodes.ACTION_Damage), true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	int 캔슬강제삭제[] = { STATUS_CURSE_BARLOG,
			STATUS_CURSE_YAHEE/* , STATUS_DRAGONPERL */ };

	private static final int[] summonstr_list = new int[] { 7, 263, 519, 8, 264, 520, 9, 265, 521, 10, 266, 522, 11,
			267, 523, 12, 268, 524, 13, 269, 525, 14, 270, 526, 15, 271, 527, 16, 17, 18, 274 };
	private static final int[] summonid_list = new int[] { 81210, 81211, 81212, 81213, 81214, 81215, 81216, 81217,
			81218, 81219, 81220, 81221, 81222, 81223, 81224, 81225, 81226, 81227, 81228, 81229, 81230, 81231, 81232,
			81233, 81234, 81235, 81236, 81237, 81238, 81239, 81240 };
	private static final int[] summonlvl_list = new int[] { 28, 28, 28, 32, 32, 32, 36, 36, 36, 40, 40, 40, 44, 44, 44,
			48, 48, 48, 52, 52, 52, 56, 56, 56, 60, 60, 60, 64, 68, 72, 72 };
	private static final int[] summoncha_list = new int[] { 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8,
			8, 8, 8, 8, 8, 8, 8, 14, 42, 42, 50 };

	private void summonMonster(L1PcInstance pc, int s) {
		int summonid = 0;
		int levelrange = 0;
		int summoncost = 0;

		for (int loop = 0; loop < summonstr_list.length; loop++) {
			if (s == summonstr_list[loop]) {
				summonid = summonid_list[loop];
				levelrange = summonlvl_list[loop];
				summoncost = summoncha_list[loop];
				break;
			}
		}
		if (pc.getLevel() < levelrange) {
			pc.sendPackets(new S_ServerMessage(743));
			return;
		}

		int petcost = 0;
		for (Object pet : pc.getPetList()) {
			petcost += ((L1NpcInstance) pet).getPetcost();
		}
		if ((summonid == 81238 || summonid == 81239 || summonid == 81240) && petcost != 0) {
			pc.sendPackets(new S_CloseList(pc.getId()));
			return;
		}
		int charisma = pc.getAbility().getTotalCha() + 6 - petcost;
		int summoncount = 0;
		if (levelrange <= 52) {
			summoncount = charisma / summoncost;
		} else if (levelrange == 56) {
			summoncount = charisma / (summoncost + 2);
		} else if (levelrange == 60) {
			summoncount = charisma / (summoncost + 4);
		} else if (levelrange == 64) {
			summoncount = charisma / (summoncost + 6);
		} else {
			summoncount = charisma / summoncost;
		}

		if (levelrange <= 52 && summoncount > 5) {
			summoncount = 5;
		} else if (levelrange == 56 && summoncount > 4) {
			summoncount = 4;
		} else if (levelrange == 60 && summoncount > 3) {
			summoncount = 3;
		} else if (levelrange == 64 && summoncount > 2) {
			summoncount = 2;
		}

		L1Npc npcTemp = NpcTable.getInstance().getTemplate(summonid);
		L1SummonInstance summon = null;
		for (int cnt = 0; cnt < summoncount; cnt++) {
			summon = new L1SummonInstance(npcTemp, pc);
			if (summonid == 81238 || summonid == 81239 || summonid == 81240) {
				summon.setPetcost(pc.getAbility().getTotalCha() + 7);
			} else {
				if (levelrange <= 52)
					summon.setPetcost(summoncost);
				else if (levelrange == 56)
					summon.setPetcost(summoncost + 2);
				else if (levelrange == 60)
					summon.setPetcost(summoncost + 4);
				else if (levelrange == 64)
					summon.setPetcost(summoncost + 6);
				else
					summoncount = charisma / summoncost;
			}
		}
		pc.sendPackets(new S_CloseList(pc.getId()));
	}

	private static final double HASTE_RATE = 0.745;
	private static final double WAFFLE_RATE = 0.874;
	private static final double THIRDSPEED_RATE = 0.874;// by사부

	private int getRightInterval(L1PcInstance _pc, int actid) {
		int gfxid = _pc.getGfxId().getTempCharGfx();
		int interval = 0;
		if (actid == 18)
			interval = SprTable.getInstance().getDirSpellSpeed(gfxid);
		else
			interval = SprTable.getInstance().getNodirSpellSpeed(gfxid);
		if (interval == 0) {
			// System.out.println(_pc.getName() + "의 스킬 사용 spr오류 확인 요망 변신 : "
			// + gfxid);
			interval = 640;
		}
		if (_pc.isHaste()) {
			interval *= HASTE_RATE;
		}
		if (_pc.isBloodLust()) { // 블러드러스트
			interval *= HASTE_RATE;
		}
		if (_pc.isBrave()) {
			interval *= HASTE_RATE;
		}
		if (_pc.isElfBrave()) {
			interval *= WAFFLE_RATE;
		}
		if (_pc.isThirdSpeed()) {
			interval *= THIRDSPEED_RATE;
		}
		if (_pc.getMapId() == 5143) {
			interval *= (HASTE_RATE / 2);
		}
		return interval;
	}

	private boolean isDistance(int x, int y, int m, int tx, int ty, int tm, int loc) {
		int distance = getDistance(x, y, tx, ty);
		if (loc < distance)
			return false;
		if (m != tm)
			return false;
		return true;
	}

	private int getDistance(int x, int y, int tx, int ty) {
		long dx = tx - x;
		long dy = ty - y;
		return (int) Math.sqrt(dx * dx + dy * dy);
	}

	private static final int[] stunTimehorror = { 1500, 1500, 2000, 2000, 3000, // 5
			3000, 3500, 3500, 4000, // 4
			4000, 4000, // 2
	};

	private static final int[] stunTimeArray1 = { 500, 500, 500, 500, 500, 500, // 6
			1000, 1000, 1000, 1000, 1000, // 5
			1500, 1500, 1500, 1500, // 4
			2000, 2000, 2000, // 3
			2500, 2500, // 2
			3000, 3000, 3500 };
	private static final int[] stunTimeArray2 = { 500, 500, 500, 500, 500, 1000, 1000, 1000, 1000, 1500, 1500, 1500,
			2000, 2000, 2500, 2500, 3000, 3500 };
	private static final int[] stunTimeArray3 = { 500, 500, 500, 500, 1000, 1000, 1000, 1500, 1500, 2000, 2000, 2500,
			3000, 3500 };
	private static final int[] stunTimeArray4 = { 500, 500, 500, 1000, 1000, 1500, 1500, 2000, 2500, 3000, 3500 };
	private static final int[] stunTimeArray5 = { 500, 500, 1000, 1000, 1500, 2000, 2500, 3000, 3500 };
	private static final int[] stunTimeArray6 = { 500, 500, 1000, 1500, 2000, 2500, 3000, 3500 };

	private static final int[] stunTimeArray7 = { 500, 1000, 1500, 2000, 2500, 2500, 3000, 3000, 3500 };

	private static final int[] stunTimeArray8 = { 500, 1000, 1500, 2000, 2000, 2500, 2500, 3000, 3000, 3500, 4000 };

	private static final int[] stunTimeArray9 = { 500, 1000, 1000, 1500, 1500, 2000, 2000, 2500, 2500, 3000, 3000, 3500,
			3500, 4000 };

	private static final int[] stunTimeArray10 = { 500, 1000, 1000, 1500, 1500, 2000, 2000, 2500, 2500, 3000, 3000,
			3000, 3500, 3500, 3500, 4000, 4500 };

	private static final int[] stunTimeArray11 = { 500, 1000, 1500, 2000, 2500, 3000, 3000, 3000, 3500,

			3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500 };
	private static final int[] stunTimeArray12 = { 500, 1000, 1500, 2000, 2500, 3000, 3000, 3000, 3500,

			3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 3500, 4000, 4500 };

	private static final int[] mobstun30TimeArray = { 1000, 1200, 1300, 1400, 1500, 2000, 2500, 3000, 3500 };

	private static final int[] summons = new int[] { 81083, 81084, 81085, 81086, 81087, 81088, 81089 };
}
