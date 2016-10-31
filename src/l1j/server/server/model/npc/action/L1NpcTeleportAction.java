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
package l1j.server.server.model.npc.action;

import java.util.Random;

import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1Teleport;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1ItemId;
import l1j.server.server.model.npc.L1NpcHtml;
import l1j.server.server.serverpackets.S_ServerMessage;

import org.w3c.dom.Element;

public class L1NpcTeleportAction extends L1NpcXmlAction {
	private final L1Location _loc;
	private final int _heading;
	private final int _price;
	private final boolean _effect;

	public L1NpcTeleportAction(Element element) {
		super(element);

		int x = L1NpcXmlParser.getIntAttribute(element, "X", -1);
		int y = L1NpcXmlParser.getIntAttribute(element, "Y", -1);
		int mapId = L1NpcXmlParser.getIntAttribute(element, "Map", -1);
		_loc = new L1Location(x, y, mapId);

		_heading = L1NpcXmlParser.getIntAttribute(element, "Heading", 5);

		_price = L1NpcXmlParser.getIntAttribute(element, "Price", 0);
		_effect = L1NpcXmlParser.getBoolAttribute(element, "Effect", true);
	}

	@Override
	public L1NpcHtml execute(String actionName, L1PcInstance pc, L1Object obj,
			byte[] args) {
		if ((_loc.getMapId() == 68 || _loc.getMapId() == 69)
				&& pc.getLevel() >= 13) {
			// �뼶, ����
			return L1NpcHtml.HTML_CLOSE;
		}
		if (_loc.getMapId() == 350 || _loc.getMapId() == 340
				|| _loc.getMapId() == 360 || _loc.getMapId() == 370) {
			Random rnd = new Random(System.nanoTime());
			/*
			 * int chance = rnd.nextInt(3); if(_loc.getMapId() == 350){
			 * if(chance == 0) _loc.set(32705, 32810); else if(chance == 1)
			 * _loc.set(32719, 32844); else _loc.set(32676, 32850); }else
			 * if(_loc.getMapId() == 340){ if(chance == 0) _loc.set(32806,
			 * 32823); else if(chance == 1) _loc.set(32767, 32796); else
			 * _loc.set(32760, 32866); }else if(_loc.getMapId() == 360){
			 * if(chance == 1) _loc.set(32734, 32789); else _loc.set(32735,
			 * 32812); }else if(_loc.getMapId() == 370){ if(chance == 1)
			 * _loc.set(32736, 32788); else _loc.set(32735, 32809); }
			 */
			_loc.set(32795 + rnd.nextInt(12), 32921 + rnd.nextInt(12), 800);
		}
		/*
		 * if ((pc.getLevel() <45 && pc.getLevel() > 70) && (_loc.getMapId() ==
		 * 777 || _loc.getMapId() == 778 || _loc.getMapId() == 779)) { // ����
		 * �̵��κ� return L1NpcHtml.HTML_CLOSE; }
		 */
		if (!pc.getInventory().checkItem(L1ItemId.ADENA, _price)) {
			pc.sendPackets(new S_ServerMessage(337, "$4"), true);
			return L1NpcHtml.HTML_CLOSE;
		}
		pc.getInventory().consumeItem(L1ItemId.ADENA, _price);
		L1Teleport.teleport(pc, _loc.getX(), _loc.getY(),
				(short) _loc.getMapId(), _heading, _effect, true, 5000);
		return null;
	}

}
