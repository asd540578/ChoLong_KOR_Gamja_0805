package l1j.server.GameSystem.Astar;

import java.util.ArrayList;
import java.util.List;

import javolution.util.FastTable;
import l1j.server.GameSystem.Robot.L1RobotInstance;
import l1j.server.server.model.L1Inventory;
import l1j.server.server.model.L1Location;
import l1j.server.server.model.L1Object;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1DollInstance;
import l1j.server.server.model.Instance.L1NpcInstance;
import l1j.server.server.model.Instance.L1TrapInstance;

public class AStar {

	// ���� ���, ���� ��� ����Ʈ
	Node OpenNode, ClosedNode;
	private L1NpcInstance _npc = null;

	public void setnpc(L1NpcInstance npc) {
		_npc = npc;
	}

	// �ִ� ���� ȸ��
	static final int LIMIT_LOOP = 200;
	// private List<Node> pool;
	// private List<Node> sabu;
	private FastTable<Node> pool;
	private FastTable<Node> sabu;

	private Node getPool() {
		Node node;
		if (pool.size() > 0) {
			node = pool.get(0);
			pool.remove(0);
		} else {
			node = new Node();
		}
		return node;
	}

	private void setPool(Node node) {
		if (node != null) {
			node.close();
			if (isPoolAppend(pool, node))
				pool.add(node);
		}
	}

	// *************************************************************************
	// Name : AStar()
	// Desc : ������
	// *************************************************************************
	public AStar() {
		// sabu = new ArrayList<Node>();
		sabu = new FastTable<Node>();
		OpenNode = null;
		ClosedNode = null;
		// pool = new ArrayList<Node>();
		pool = new FastTable<Node>();
	}

	public void clear() {
		for (Node s : sabu) {
			try {
				s.close();
			} catch (Exception e) {
			}
			s.clear();
		}
		for (Node s2 : pool) {
			try {
				s2.close();
			} catch (Exception e) {
			}
			s2.clear();
		}
		OpenNode = null;
		ClosedNode = null;
		sabu.clear();
		pool.clear();
		sabu = null;
		pool = null;
	}

	// *************************************************************************
	// Name : ResetPath()
	// Desc : ������ ������ ��θ� ����
	// *************************************************************************
	public void cleanTail() {
		Node tmp;
		int cnt = 0;
		while (OpenNode != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return;
				} else if (cnt > 10000) {
					return;
				}
			}
			// cnt++;
			tmp = OpenNode.next;
			setPool(OpenNode);
			OpenNode = tmp;
		}
		cnt = 0;
		while (ClosedNode != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					ClosedNode = null;
					return;
				} else if (cnt > 10000) {
					return;
				}
			}
			// cnt++;
			tmp = ClosedNode.next;
			setPool(ClosedNode);
			ClosedNode = tmp;
		}

		/*
		 * if(cnt > 5000){
		 * System.out.println("�μ�Ʈ �̸� "+_npc.getName()+" x:"+_npc
		 * .getX()+" y:"+_npc.getY()+" m:"+_npc.getMapId());
		 * System.out.println(_npc.isDead()); L1PcInstance[] gm =
		 * Config.toArray����ä�ø����(); gm[0].dx= _npc.getX(); gm[0].dy=
		 * _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
	}

	// *************************************************************************
	// Name : FindPath()
	// Desc : ������ġ�� ��ǥ��ġ�� �Է� �޾� ��γ�� ����Ʈ�� ��ȯ
	// *************************************************************************
	// ������ǥ sx, xy
	// �̵�����ǥ tx, ty
	public Node searchTail(L1Object o, int tx, int ty, int m, boolean obj) {
		int calcx = o.getX() - tx;
		int calcy = o.getY() - ty;
		if (o instanceof L1RobotInstance) {
			if (o.getMapId() != m || Math.abs(calcx) > 40
					|| Math.abs(calcy) > 40) {
				return null;
			}
		} else if (o.getMapId() != m || Math.abs(calcx) > 30
				|| Math.abs(calcy) > 30) {
			/*
			 * if(o instanceof L1NpcInstance){ L1NpcInstance npp =
			 * (L1NpcInstance)o; if(npp.getNpcId() >=100750 && npp.getNpcId() <=
			 * 100757){
			 * 
			 * }else{ return null; } }
			 */

		}
		Node src, best = null;
		int count = 0;
		int sx = o.getX();
		int sy = o.getY();

		// ó�� ���۳�� ����
		src = getPool();
		src.g = 0;
		src.h = (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy);
		src.f = src.h;
		src.x = sx;
		src.y = sy;

		// ���۳�带 ������� ����Ʈ�� �߰�
		OpenNode = src;

		// ��ã�� ���� ����
		// �ִ� �ݺ� ȸ���� ������ ��ã�� ����
		while (count < LIMIT_LOOP) {
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				}
			}
			// ������尡 ���ٸ� ��� ��带 �˻������Ƿ� ��ã�� ����
			if (OpenNode == null) {
				// System.out.println("�������̾���");
				return null;
			}

			// ��������� ù��° ��带 �������� ������忡�� ����
			best = OpenNode;
			OpenNode = best.next;

			// ������ ��带 ������忡 �߰�
			best.next = ClosedNode;
			ClosedNode = best;

			// ���� ������ ��尡 ��ǥ����� ��ã�� ����
			if (best.x == tx && best.y == ty) {
				return best;
			}

			// ���� ���� ������ ����� Ȯ���Ͽ� �������� �߰�
			if (MakeChild(o, best, tx, ty, obj) == 0 && count == 0) {
				// System.out.println("�����־�..");
				return null;
			}

			count++;
		}

		return null;
	}

	// *************************************************************************
	// Name : MakeChild()
	// Desc : �Է¹��� ����� ������ ����� Ȯ��
	// *************************************************************************
	// ������ ȯ�濡 �°� ����� by sabu
	private char ����ũ���ϵ�(L1Object o, Node node, int tx, int ty, boolean obj) {
		int x, y;
		char flag = 0;

		x = node.x;
		y = node.y;
		boolean ckckck = false;
		/*
		 * if(o instanceof L1NpcInstance){ L1NpcInstance npp = (L1NpcInstance)o;
		 * if(npp.getNpcId() >=100750 && npp.getNpcId() <= 100757){ ckckck =
		 * true; } }
		 */
		// ������ ���� �̵��������� �˻�
		for (int i = 0; i < 8; ++i) {
			if (ckckck || World.isThroughObject(x, y, o.getMapId(), i)) {
				int nx = x + getXY(i, true);
				int ny = y + getXY(i, false);
				boolean ck = true;
				// ���������� ��ǥ�� �˻����ʿ� ����.
				if (tx != nx || ty != ny) {
					if (obj) {
						if (o instanceof L1DollInstance) {
							ck = true;
						} else if (World.���̵�(x, y, o.getMapId(), i) == true) {
							ck = false;
							/*
							 * if(o instanceof L1NpcInstance){ L1NpcInstance np
							 * = (L1NpcInstance)o; if(np.getNpcId() >=100750 &&
							 * np.getNpcId() <= 100757){ ck = true; } }
							 */
						} else {
							ck = World.isMapdynamic(nx, ny, o.getMapId()) == false;
						}
						if (ck && o instanceof L1RobotInstance) {
							// ck = L1World.getInstance().getVisiblePoint(new
							// L1Location(nx, ny, o.getMapId()), 0).size() == 0;
							try {
								ArrayList<L1Object> list = L1World
										.getInstance().getVisiblePoint(
												new L1Location(nx, ny,
														o.getMapId()), 0);
								if (list.size() > 0) {
									for (L1Object temp_obj : list) {
										if (temp_obj instanceof L1DollInstance
												|| temp_obj instanceof L1Inventory
												|| temp_obj instanceof L1TrapInstance) {
										} else {
											ck = false;
											break;
										}
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				if (ck) {
					MakeChildSub(node, nx, ny, o.getMapId(), tx, ty);
					flag = 1;
				} else if (tx != nx || ty != ny) {
					sabu.add(node);
				}
			} else {

			}
		}
		return flag;
	}

	// *************************************************************************
	// Name : FindPath()
	// Desc : ������ ��ġ ã��.. ���� �ɷ���
	// *************************************************************************
	// ������ǥ sx, xy
	// �̵�����ǥ tx, ty
	public Node ������ġŸ��(L1Object o, int tx, int ty, int m, boolean obj) {
		int calcx = o.getX() - tx;
		int calcy = o.getY() - ty;
		if (o instanceof L1RobotInstance) {
			if (o.getMapId() != m || Math.abs(calcx) > 40
					|| Math.abs(calcy) > 40) {
				return null;
			}
		} else if (o.getMapId() != m || Math.abs(calcx) > 30
				|| Math.abs(calcy) > 30) {
			/*
			 * if(o instanceof L1NpcInstance){ L1NpcInstance npp =
			 * (L1NpcInstance)o; if(npp.getNpcId() >=100750 && npp.getNpcId() <=
			 * 100757){ //Broadcaster.broadcastPacket(npp, new
			 * S_NpcChatPacket(npp, "1111111111111111111", 0)); }else{ return
			 * null; } }
			 */

		}

		Node src, best = null;
		int count = 0;
		int sx = o.getX();
		int sy = o.getY();

		// ó�� ���۳�� ����
		src = getPool();
		src.g = 0;
		src.h = (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy);
		src.f = src.h;
		src.x = sx;
		src.y = sy;

		// ���۳�带 ������� ����Ʈ�� �߰�
		OpenNode = src;

		// ��ã�� ���� ����
		// �ִ� �ݺ� ȸ���� ������ ��ã�� ����
		while (count < LIMIT_LOOP) {
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				}
			}
			// ������尡 ���ٸ� ��� ��带 �˻������Ƿ� ��ã�� ����
			if (OpenNode == null) {
				// System.out.println("�������̾���");
				return null;
			}

			// ��������� ù��° ��带 �������� ������忡�� ����
			best = OpenNode;
			OpenNode = best.next;

			// ������ ��带 ������忡 �߰�
			best.next = ClosedNode;
			ClosedNode = best;

			// ���� ������ ��尡 ��ǥ����� ��ã�� ����
			if (best.x == tx && best.y == ty) {
				return best;
			}

			// ���� ���� ������ ����� Ȯ���Ͽ� �������� �߰�
			if (����ũ���ϵ�(o, best, tx, ty, obj) == 0 && count == 0) {
				// System.out.println("�����־�..");
				return null;
			}

			count++;
		}
		int tmpdis = 0;
		for (Node saNode : sabu) {
			int x = saNode.x;
			int y = saNode.y;
			saNode.h = (tx - x) * (tx - x) + (ty - y) * (ty - y);
			if (tmpdis == 0) {
				best = saNode;
				tmpdis = saNode.h;
			}
			if (tmpdis > saNode.h) {
				best = saNode;
				tmpdis = saNode.h;
			}
		}

		if (best == null
				|| best.h >= (tx - sx) * (tx - sx) + (ty - sy) * (ty - sy)) {
			return null;
		}
		if (sabu.size() > 0)
			sabu.clear();
		return best;
	}

	// *************************************************************************
	// Name : MakeChild()
	// Desc : �Է¹��� ����� ������ ����� Ȯ��
	// *************************************************************************
	// ������ ȯ�濡 �°� ����� by sabu

	private char MakeChild(L1Object o, Node node, int tx, int ty, boolean obj) {
		int x, y;
		char flag = 0;

		x = node.x;
		y = node.y;
		boolean ckckck = false;
		/*
		 * if(o instanceof L1NpcInstance){ L1NpcInstance npp = (L1NpcInstance)o;
		 * if(npp.getNpcId() >=100750 && npp.getNpcId() <= 100757){ ckckck =
		 * true; //Broadcaster.broadcastPacket(npp, new S_NpcChatPacket(npp,
		 * "33333333333", 0)); } }
		 */
		// ������ ���� �̵��������� �˻�

		for (int i = 0; i < 8; ++i) {
			if (ckckck || World.isThroughObject(x, y, o.getMapId(), i)) {
				int nx = x + getXY(i, true);
				int ny = y + getXY(i, false);
				boolean ck = true;
				// ���������� ��ǥ�� �˻����ʿ� ����.
				if (tx != nx || ty != ny) {
					if (obj) {
						if (o instanceof L1DollInstance) {
							ck = true;
						} else if (World.���̵�(x, y, o.getMapId(), i) == true) {
							ck = false;
							/*
							 * if(o instanceof L1NpcInstance){ L1NpcInstance npp
							 * = (L1NpcInstance)o; if(npp.getNpcId() >=100750 &&
							 * npp.getNpcId() <= 100757){ ck = true; } }
							 */

						} else {
							ck = World.isMapdynamic(nx, ny, o.getMapId()) == false;
						}
						if (ck && o instanceof L1RobotInstance) {
							if (o.getMap().isCombatZone(nx, ny))
								continue;
							// ck = L1World.getInstance().getVisiblePoint(new
							// L1Location(nx, ny, o.getMapId()), 0).size() == 0;
							ArrayList<L1Object> list = L1World
									.getInstance()
									.getVisiblePoint(
											new L1Location(nx, ny, o.getMapId()),
											0);
							if (list.size() > 0) {
								for (L1Object temp_obj : list) {
									if (temp_obj instanceof L1DollInstance
											|| temp_obj instanceof L1Inventory
											|| temp_obj instanceof L1TrapInstance) {
									} else {
										ck = false;
										break;
									}
								}
							}
						}
					}
				}
				if (ck) {
					MakeChildSub(node, nx, ny, o.getMapId(), tx, ty);
					flag = 1;
				}
			} else {

			}
		}

		return flag;
	}

	// *************************************************************************
	// Name : MakeChildSub()
	// Desc : ��带 ����. ������峪 ������忡 �̹� �ִ� �����
	// �������� ���Ͽ� f�� �� ������ ���� ����
	// ������忡 �ִٸ� �׿� ����� ��� ������ ������ ���� ����
	// *************************************************************************
	void MakeChildSub(Node node, int x, int y, int m, int tx, int ty) {
		Node old = null, child = null;
		int g = node.g + 1;
		// �����尡 ���� ��忡 �ְ� f�� �� ������ ���� ����
		if ((old = IsOpen(x, y, m)) != null) {
			if (g < old.g) {
				old.prev = node;
				old.g = g;
				old.f = old.h + old.g;
			}

			// �����尡 ���� ��忡 �ְ� f�� �� ������ ���� ����
		} else if ((old = IsClosed(x, y, m)) != null) {
			if (g < old.g) {
				old.prev = node;
				old.g = g;
				old.f = old.h + old.g;
			}
			// ���ο� ����� ������� �����ϰ� ������忡 �߰�
		} else {
			try {
				// ���ο� ��� ����
				child = getPool();

				child.prev = node;
				child.g = g;
				child.h = (x - tx) * (x - tx) + (y - ty) * (y - ty);
				child.f = child.h + child.g;
				child.x = x;
				child.y = y;

				// ���ο� ��带 ������忡 �߰�
				InsertNode(child);
			} catch (Exception e) {
			}
		}
	}

	// *************************************************************************
	// Name : IsOpen()
	// Desc : �Էµ� ��尡 ����������� �˻�
	// *************************************************************************
	private Node IsOpen(int x, int y, int mapid) {
		Node tmp = OpenNode;
		int cnt = 0;
		while (tmp != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				} else if (cnt > 10000) {
					return null;
				}
			}
			// cnt++;
			if (tmp.x == x && tmp.y == y) {
				return tmp;
			}
			tmp = tmp.next;
		}

		/*
		 * if(cnt > 5000){
		 * System.out.println(cnt+" ���� x :"+x+" y :"+y+" m :"+mapid);
		 * System.out.
		 * println(" �̸�"+_npc.getName()+" x:"+_npc.getX()+" y:"+_npc.getY
		 * ()+" m:"+_npc.getMapId()); System.out.println(_npc.isDead());
		 * L1PcInstance[] gm = Config.toArray����ä�ø����(); gm[0].dx= _npc.getX();
		 * gm[0].dy= _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
		return null;
	}

	// *************************************************************************
	// Name : IsClosed()
	// Desc : �Էµ� ��尡 ����������� �˻�
	// *************************************************************************
	private Node IsClosed(int x, int y, int mapid) {
		Node tmp = ClosedNode;
		int cnt = 0;
		while (tmp != null) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return null;
				} else if (cnt > 10000) {
					return null;
				}
			}
			// cnt ++;
			if (tmp.x == x && tmp.y == y) {
				return tmp;
			}
			tmp = tmp.next;
		}
		/*
		 * if(cnt > 5000){
		 * System.out.println(cnt+" Ŭ���� x :"+x+" y :"+y+" m :"+mapid);
		 * System.out
		 * .println(" �̸�"+_npc.getName()+" x:"+_npc.getX()+" y:"+_npc.getY
		 * ()+" m:"+_npc.getMapId()); System.out.println(_npc.isDead());
		 * L1PcInstance[] gm = Config.toArray����ä�ø����(); gm[0].dx= _npc.getX();
		 * gm[0].dy= _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
		return null;
	}

	// *************************************************************************
	// Name : InsertNode()
	// Desc : �Էµ� ��带 ������忡 f���� ���� �����Ͽ� �߰�
	// f���� �������� ���� ���� ������ -> ������ ���
	// *************************************************************************
	private void InsertNode(Node src) {
		Node old = null, tmp = null;
		int cnt = 0;
		if (OpenNode == null) {
			OpenNode = src;
			return;
		}
		tmp = OpenNode;
		while (tmp != null && (tmp.f < src.f)) {
			cnt++;
			if (_npc != null) {
				if (_npc.isDead()) {
					return;
				} else if (cnt > 10000) {
					return;
				}
			}
			// cnt++;
			old = tmp;
			tmp = tmp.next;
		}
		if (old != null) {
			src.next = tmp;
			old.next = src;
		} else {
			src.next = tmp;
			OpenNode = src;
		}
		/*
		 * if(cnt > 100000){
		 * System.out.println("�μ�Ʈ �̸� "+_npc.getName()+" x:"+_npc
		 * .getX()+" y:"+_npc.getY()+" m:"+_npc.getMapId());
		 * System.out.println(_npc.isDead()); L1PcInstance[] gm =
		 * Config.toArray����ä�ø����(); gm[0].dx= _npc.getX(); gm[0].dy=
		 * _npc.getY(); gm[0].dm= _npc.getMapId();
		 * gm[0].dh=gm[0].getMoveState().getHeading(); gm[0].setTelType(7);
		 * gm[0].sendPackets(new S_SabuTell(gm[0])); }
		 */
	}

	/**
	 * Ǯ���� �߰��ص��Ǵ��� Ȯ�����ִ� �Լ�. : �ʹ� ���� ��ϵǸ� ������ �Ǳ�빮�� ���������� ī��.. :
	 * java.lang.OutOfMemoryError: Java heap space
	 * 
	 * @param c
	 * @return
	 */
	private boolean isPoolAppend(List<?> pool, Object c) {
		// ��ü ������ üũ.
		return pool.size() < 200;
	}

	/**
	 * ����� Ÿ�Կ����� �����ϰ� ��ǥ������ ����
	 * 
	 * @param h
	 *            : ����
	 * @param type
	 *            : true ? x : y
	 * @return
	 */
	public int getXY(final int h, final boolean type) {
		int loc = 0;
		switch (h) {
		case 0:
			if (!type)
				loc -= 1;
			break;
		case 1:
			if (type)
				loc += 1;
			else
				loc -= 1;
			break;
		case 2:
			if (type)
				loc += 1;
			break;
		case 3:
			loc += 1;
			break;
		case 4:
			if (!type)
				loc += 1;
			break;
		case 5:
			if (type)
				loc -= 1;
			else
				loc += 1;
			break;
		case 6:
			if (type)
				loc -= 1;
			break;
		case 7:
			loc -= 1;
			break;
		}
		return loc;
	}

	public int calcheading(int myx, int myy, int tx, int ty) {
		if (tx > myx && ty > myy) {
			return 3;
		} else if (tx < myx && ty < myy) {
			return 7;
		} else if (tx > myx && ty == myy) {
			return 2;
		} else if (tx < myx && ty == myy) {
			return 6;
		} else if (tx == myx && ty < myy) {
			return 0;
		} else if (tx == myx && ty > myy) {
			return 4;
		} else if (tx < myx && ty > myy) {
			return 5;
		} else {
			return 1;
		}
	}

}