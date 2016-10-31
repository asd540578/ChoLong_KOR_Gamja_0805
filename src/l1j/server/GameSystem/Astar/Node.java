package l1j.server.GameSystem.Astar;

//******************************************************************************
// File Name	: Node.java
// Description	: ��� Ŭ����
// Create		: 2003/04/01 JongHa Woo
// Update		:
//******************************************************************************

public class Node {
	public int f; // f = g+h
	public int h; // �޸���ƽ ��
	public int g; // ��������� �Ÿ�
	public int x, y; // ����� ��ġ
	public Node prev; // ���� ���
	public Node direct[]; // ������ ���
	public Node next; // ���� ���

	// *************************************************************************
	// Name : Node()
	// Desc : ������
	// *************************************************************************
	Node() {
		direct = new Node[8];

		for (int i = 0; i < 8; i++) {
			direct[i] = null;
		}
	}

	public void close() {
		for (int i = 0; i < 8; i++) {
			direct[i] = null;
		}
		prev = next = null;
	}

	public void clear() {
		f = 0;
		h = 0;
		g = 0;
		x = 0;
		y = 0;
		prev = null;
		direct = null;
		next = null;
	}
}
