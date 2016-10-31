package l1j.server.server.datatables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.utils.SQLUtil;

public class MonsterBookTeleportTable {
	public class BooksTeleportLoc {
		public int _itemId;
		public int _getX;
		public int _getY;
		public int _getMapId;
		public int _random;
	}
	private static Logger _log = Logger.getLogger(MonsterBookTeleportTable.class.getName());

	private static MonsterBookTeleportTable _instance;

	private Map<Integer, BooksTeleportLoc> _booksTeleport = new HashMap<Integer, BooksTeleportLoc>();
	public static MonsterBookTeleportTable getInstance() {
		if(_instance == null) {
			_instance = new MonsterBookTeleportTable();
		}
		return _instance;
	}

	public static void reload() {
		MonsterBookTeleportTable oldInstance = _instance;
		_instance = new MonsterBookTeleportTable();
		if (oldInstance != null) {
			oldInstance._booksTeleport.clear();
		}
	}

	private MonsterBookTeleportTable() {
		selectBooksTeleport();
	}
	private void selectBooksTeleport() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement("SELECT * FROM teleport_monster_books");
			rs = pstm.executeQuery();
			BooksTeleportLoc bt = null;
			while(rs.next()) {
				bt = new BooksTeleportLoc();
				int bookId = rs.getInt("bookId");
				bt._itemId = rs.getInt("itemId");
				bt._getX = rs.getInt("locX");
				bt._getY = rs.getInt("locY");
				bt._getMapId = rs.getInt("locMap");
				bt._random = rs.getInt("random");
				_booksTeleport.put(bookId, bt);
			}
		}catch(SQLException e) {
			_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}finally{
			SQLUtil.close(rs, pstm, con);
		}
	}

	public BooksTeleportLoc getTeleportLoc(int bookId) {
		BooksTeleportLoc btl = _booksTeleport.get(bookId);
		if(btl == null) return null;
		return btl;
	}
}