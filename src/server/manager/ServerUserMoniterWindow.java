package server.manager;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import l1j.server.L1DatabaseFactory;
import l1j.server.server.utils.CommonUtil;
import l1j.server.server.utils.SQLUtil;


@SuppressWarnings("serial")
public class ServerUserMoniterWindow extends JInternalFrame {
	private JLabel jJLabel1 = null;
	private JLabel jJLabel2 = null;
	private JLabel jJLabel3 = null;
	private JLabel jJLabel4 = null;
	private JLabel jJLabel5 = null;
	private JLabel jJLabel6 = null;
	private JLabel jJLabel7 = null;
	
	
	private JTextField txt_Adena = null;
	private JTextField txt_Feather = null;
	private JTextField txt_WeaponScroll = null;
	private JTextField txt_ArmorScroll = null;
	private JTextField txt_Supyo = null;
	private JTextField txt_GD = null;
	private JTextField txt_GZ = null;
	
	private JButton btn_Search = null;
	private JButton btn_WareSearch = null;
	private JButton btn_ClanSearch = null;
	
	private JTable jJTable = null;
	private JScrollPane pScroll = null;
	
	private DefaultTableModel model = null;
	

	public ServerUserMoniterWindow(String windowName, int x, int y, int width, int height, boolean resizable, boolean closable) {
		super();
		initialize(windowName, x, y, width, height, resizable, closable);
	}
	
	public void initialize(String windowName, int x, int y, int width, int height, boolean resizable, boolean closable) {
		this.title = windowName;
		this.closable = closable;      
		this.isMaximum = false;	   
		this.maximizable = true;
		this.resizable = resizable;
		this.iconable = true;   
		this.isIcon = false;			  
	    setSize(width, height);
		setBounds(x, y, width, height);
		setVisible(true);
		frameIcon = new ImageIcon("");
		setRootPaneCheckingEnabled(true);
		
	    updateUI();
	    
	    jJLabel1 = new JLabel("�Ƶ���");
	    jJLabel2 = new JLabel("�Ƚ��� ����");
	    jJLabel3 = new JLabel("����");
	    jJLabel4 = new JLabel("��");
	    jJLabel5 = new JLabel("��ǥ");
	    jJLabel6 = new JLabel("�൥��");
	    jJLabel7 = new JLabel("����");
	    
	    txt_Adena = new JTextField();
	    txt_Adena.setText("200000000");
	    txt_Feather = new JTextField();
	    txt_Feather.setText("50000");
	    txt_WeaponScroll = new JTextField();
	    txt_WeaponScroll.setText("500");
	    txt_ArmorScroll = new JTextField();
	    txt_ArmorScroll.setText("500");
	    txt_Supyo = new JTextField();
	    txt_Supyo.setText("5");
	    txt_GD = new JTextField();
	    txt_GD.setText("100");
	    txt_GZ = new JTextField();
	    txt_GZ.setText("100");
	    
	    btn_Search = new JButton("�κ��丮 ��ȸ");
	    btn_Search.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				if (eva.isServerStarted) {
					dataSearch();
				} else {
					eva.errorMsg(eva.NoServerStartMSG);
				}
			}
		});
	    
	    btn_WareSearch = new JButton("����â�� ��ȸ");
	    btn_WareSearch.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				if (eva.isServerStarted) {
					waredataSearch();
				} else {
					eva.errorMsg(eva.NoServerStartMSG);
				}
			}
		});
	    btn_ClanSearch = new JButton("����â�� ��ȸ");
	    btn_ClanSearch.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				if (eva.isServerStarted) {
					clandataSearch();
				} else {
					eva.errorMsg(eva.NoServerStartMSG);
				}
			}
		});
	    String[] modelColName = { "������", "ĳ����", "�¶���", "�Ƶ���", "����", "����", "��", "��ǥ", "�൥��", "����" };
		
		model = new DefaultTableModel(modelColName, 0);
		    
		jJTable = new JTable(model);
		//jJTable.setAutoCreateRowSorter(true);
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		jJTable.setRowSorter(sorter);

		
		jJTable.getColumnModel().getColumn(0).setPreferredWidth(70);
		jJTable.getColumnModel().getColumn(1).setPreferredWidth(70);
		jJTable.getColumnModel().getColumn(2).setPreferredWidth(60);
		jJTable.getColumnModel().getColumn(3).setPreferredWidth(70);
		jJTable.getColumnModel().getColumn(4).setPreferredWidth(70);
		jJTable.getColumnModel().getColumn(5).setPreferredWidth(70);
		jJTable.getColumnModel().getColumn(6).setPreferredWidth(70);
		jJTable.getColumnModel().getColumn(7).setPreferredWidth(70);
		
		jJTable.getColumn("�¶���").setCellRenderer(new LabelRowCellEdior("�¶���"));
		jJTable.getColumn("�Ƶ���").setCellRenderer(new LabelRowCellEdior("�Ƶ���"));
		jJTable.getColumn("����").setCellRenderer(new LabelRowCellEdior("����"));
		jJTable.getColumn("����").setCellRenderer(new LabelRowCellEdior("����"));
		jJTable.getColumn("��").setCellRenderer(new LabelRowCellEdior("��"));
		jJTable.getColumn("��ǥ").setCellRenderer(new LabelRowCellEdior("��ǥ"));
		jJTable.getColumn("�൥��").setCellRenderer(new LabelRowCellEdior("�൥��"));
		jJTable.getColumn("����").setCellRenderer(new LabelRowCellEdior("����"));
		
		jJTable.addMouseListener(new MouseListenner());
				
	    pScroll = new JScrollPane(jJTable);
	    
		pScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pScroll.setAutoscrolls(true);
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		GroupLayout.SequentialGroup main_horizontal_grp = layout.createSequentialGroup();
		
		GroupLayout.SequentialGroup horizontal_grp = layout.createSequentialGroup();
		GroupLayout.SequentialGroup vertical_grp   = layout.createSequentialGroup();
		
		GroupLayout.ParallelGroup main = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup col1 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup col2 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup col3 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup col4 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup col5 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup col6 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup col7 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup col8 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		GroupLayout.ParallelGroup col9 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
		
		main.addGroup(horizontal_grp);
		main_horizontal_grp.addGroup(main);
		
		layout.setHorizontalGroup(main_horizontal_grp);
		layout.setVerticalGroup(vertical_grp);
		
		col1.addComponent(jJLabel1, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
		    .addComponent(jJLabel3, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE);
		
		col2.addComponent(txt_Adena, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
		    .addComponent(txt_WeaponScroll, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE);
		
		col3.addComponent(jJLabel2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
		    .addComponent(jJLabel4, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE);
		
		col4.addComponent(txt_Feather, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
            .addComponent(txt_ArmorScroll, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE);
		
		col5.addComponent(jJLabel6, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
			.addComponent(jJLabel7, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE);
			
		col6.addComponent(txt_GD, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
			.addComponent(txt_GZ, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE);
		
		col7.addComponent(jJLabel5, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE);
			
		
		col8.addComponent(txt_Supyo, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
			.addComponent(btn_WareSearch, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE);
		
		col9.addComponent(btn_Search, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
			.addComponent(btn_ClanSearch, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE);

		
		horizontal_grp.addContainerGap().addGap(5).addGroup(col1).addContainerGap();
		horizontal_grp.addContainerGap().addGap(5).addGroup(col2).addContainerGap();
		horizontal_grp.addContainerGap().addGap(5).addGroup(col3).addContainerGap();
		horizontal_grp.addContainerGap().addGap(5).addGroup(col4).addContainerGap();
		horizontal_grp.addContainerGap().addGap(5).addGroup(col5).addContainerGap();
		horizontal_grp.addContainerGap().addGap(5).addGroup(col6).addContainerGap();
		horizontal_grp.addContainerGap().addGap(5).addGroup(col7).addContainerGap();
		horizontal_grp.addContainerGap().addGap(5).addGroup(col8).addContainerGap();
		horizontal_grp.addContainerGap().addGap(5).addGroup(col9).addContainerGap();
		
		main.addGroup(layout.createSequentialGroup().addComponent(pScroll));
		
		vertical_grp.addGap(5).addContainerGap().addGroup(layout.createBaselineGroup(false, false).addComponent(jJLabel1)
				                                                                                  .addComponent(txt_Adena)
				                                                                                  .addComponent(jJLabel2)
				                                                                                  .addComponent(txt_Feather)
				                                                                                  .addComponent(jJLabel5)
				                                                                                  .addComponent(txt_Supyo)
				                                                                                  .addComponent(jJLabel6)
				                                                                                  .addComponent(txt_GD)
																								  .addComponent(btn_Search));
		vertical_grp.addGap(5).addContainerGap().addGroup(layout.createBaselineGroup(false, false).addComponent(jJLabel3)
				                                                                                  .addComponent(txt_WeaponScroll)
				                                                                                  .addComponent(jJLabel4)
				                                                                                  .addComponent(txt_ArmorScroll)
				                                                                                  .addComponent(jJLabel7)
				                                                                                  .addComponent(txt_GZ)
                																				  .addComponent(btn_WareSearch)
                																				  .addComponent(btn_ClanSearch));
		
		vertical_grp.addGap(5).addContainerGap().addGroup(layout.createBaselineGroup(true, true).addComponent(pScroll));
		
	}
	
	private class MouseListenner extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == 1) {
				@SuppressWarnings("unused")
				int column = ((JTable)e.getSource()).getSelectedColumn();
				@SuppressWarnings("unused")
				int row = ((JTable)e.getSource()).getSelectedRow();								
			}
		}
	}	
	private void clandataSearch() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT a.alliance,         									     ");
			sb.append("        b.item_id,               								 ");
			sb.append("        b.item_name,            			 						 ");
			sb.append("        b.count,                                                  ");
			sb.append("        b.clan_name                                               ");
			sb.append("   FROM (SELECT alliance                                      	 ");
			sb.append("           FROM clan_data) a,									 ");
			sb.append("   		(SELECT clan_name,                                       ");    		
			sb.append("                item_id,						                     ");
			sb.append("                item_name,					                     ");
			sb.append("                count						                     ");
			sb.append("            FROM clan_warehouse					                 ");
			sb.append(" 		WHERE item_id in ('40308', '41159', '40087', '40074', '400075', '140087', '140074')) b ");
			sb.append("ORDER BY b.clan_name, b.item_id									");
			
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement(sb.toString());
			rs = pstm.executeQuery();
			
			DefaultTableModel tModel = (DefaultTableModel)jJTable.getModel();
			Object[] moniter = new Object[10];
			String account = "";
			String charName = "";
			
			for (int i = tModel.getRowCount() - 1; i >= 0; i--) {
				tModel.removeRow(i);					
			}
			
			for (int i = 0; i < moniter.length; i++) {
				moniter[i] = 0;
			}
			int count = 0;
			String clanware = "����â����ȸ";
			while (rs.next()) {	
					if (account != rs.getString("clan_name")) {
						account = rs.getString("clan_name");					
						moniter[0] = clanware;
					}
				if (charName.equals(rs.getString("clan_name"))) {
					if (rs.getInt("item_id") == 40308) {
						moniter[3] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 41159) {
						moniter[4] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40087) {
						moniter[5] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40074) {
						moniter[6] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 400075) {
						moniter[7] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140087) {
						moniter[8] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140074) {
						moniter[9] = CommonUtil.numberFormat(rs.getInt("count"));
					}
				} else {
					
					if (count != 0) {
						tModel.addRow(moniter);
						
						for (int i = 0; i < moniter.length; i++) {
							moniter[i] = 0;
						}
					}
					charName = rs.getString("clan_name");
					moniter[1] = rs.getString("clan_name");
					moniter[2] = rs.getInt("alliance") == 0 ? "X" : "O";
					
					if (rs.getInt("item_id") == 40308) {
						moniter[3] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 41159) {
						moniter[4] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40087) {
						moniter[5] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40074) {
						moniter[6] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 400075) {
						moniter[7] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140087) {
						moniter[8] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140074) {
						moniter[9] = CommonUtil.numberFormat(rs.getInt("count"));
					}
				}
				
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);			
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	private void waredataSearch() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT a.banned,         									   ");
			sb.append("        b.item_id,               								   ");
			sb.append("        b.item_name,            			 						   ");
			sb.append("        b.count,                                                     ");
			sb.append("        b.account_name                                                     ");
			sb.append("   FROM (SELECT banned                                      			");
			sb.append("           FROM accounts) a,											");
			sb.append("   		(SELECT account_name,                                       ");    		
			sb.append("                item_id,						                       ");
			sb.append("                item_name,					                       ");
			sb.append("                count						                       ");
			sb.append("            FROM character_warehouse					               ");
			sb.append(" 		WHERE item_id in ('40308', '41159', '40087', '40074', '400075', '140087', '140074')) b ");
			sb.append("ORDER BY b.account_name, b.item_id									");
			
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement(sb.toString());
			rs = pstm.executeQuery();
			
			DefaultTableModel tModel = (DefaultTableModel)jJTable.getModel();
			Object[] moniter = new Object[10];
			String account = "";
			String charName = "";
			
			for (int i = tModel.getRowCount() - 1; i >= 0; i--) {
				tModel.removeRow(i);					
			}
			
			for (int i = 0; i < moniter.length; i++) {
				moniter[i] = 0;
			}
			int count = 0;
			String ware = "â����ȸ";
			while (rs.next()) {	
					if (account != rs.getString("account_name")) {
						account = rs.getString("account_name");					
						moniter[0] = ware;
					}
				if (charName.equals(rs.getString("account_name"))) {
					if (rs.getInt("item_id") == 40308) {
						moniter[3] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 41159) {
						moniter[4] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40087) {
						moniter[5] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40074) {
						moniter[6] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 400075) {
						moniter[7] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140087) {
						moniter[8] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140074) {
						moniter[9] = CommonUtil.numberFormat(rs.getInt("count"));
					}
				} else {
					
					if (count != 0) {
						tModel.addRow(moniter);
						
						for (int i = 0; i < moniter.length; i++) {
							moniter[i] = 0;
						}
					}
					charName = rs.getString("account_name");
					moniter[1] = rs.getString("account_name");
					moniter[2] = rs.getInt("banned") == 0 ? "X" : "O";
					
					if (rs.getInt("item_id") == 40308) {
						moniter[3] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 41159) {
						moniter[4] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40087) {
						moniter[5] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40074) {
						moniter[6] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 400075) {
						moniter[7] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140087) {
						moniter[8] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140074) {
						moniter[9] = CommonUtil.numberFormat(rs.getInt("count"));
					}
				}
				
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);			
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	
	private void dataSearch() {
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT a.objid,                 								   ");
			sb.append("        a.char_name,             								   ");
			sb.append("        a.account_name,         									   ");
			sb.append("        a.level,                								       ");
			sb.append("        a.onlinestatus,         								       ");
			sb.append("       (SELECT locationname      								   ");
			sb.append("          FROM mapids     								           ");
			sb.append("         WHERE mapid = a.mapid) AS location,                        ");
			sb.append("        b.item_id,               								   ");
			sb.append("        b.item_name,            			 						   ");
			sb.append("        b.count                                                     ");
			sb.append("   FROM (SELECT account_name,                                       ");
			sb.append("                objid,                                              ");
			sb.append("                char_name,                                          ");      
			sb.append("                level,						                       ");
			sb.append("                locx,						                       ");
			sb.append("                locy,						                       ");
			sb.append("                mapid,						                       ");
			sb.append("                onlinestatus					                       ");
			sb.append("           FROM characters) a,					                   ");			
			sb.append("         (SELECT char_id,						                   ");
			sb.append("                 item_id,						                   ");
			sb.append("                 item_name,					                       ");
			sb.append("                 count						                       ");
			sb.append("            FROM character_items					                   ");
			sb.append(" 		WHERE item_id in ('40308', '41159', '40087', '40074', '400075', '140087', '140074')) b ");
			sb.append("  WHERE a.objid = b.char_id					                       ");
			sb.append("ORDER BY a.account_name, a.level, a.char_name, b.item_id			   ");
			
			
			con = L1DatabaseFactory.getInstance().getConnection();
			pstm = con.prepareStatement(sb.toString());
			rs = pstm.executeQuery();
			
			DefaultTableModel tModel = (DefaultTableModel)jJTable.getModel();
			Object[] moniter = new Object[10];
			String account = "";
			String charName = "";
			
			for (int i = tModel.getRowCount() - 1; i >= 0; i--) {
				tModel.removeRow(i);					
			}
			
			for (int i = 0; i < moniter.length; i++) {
				moniter[i] = 0;
			}
			int count = 0;
			while (rs.next()) {				
				if (account != rs.getString("account_name")) {
					account = rs.getString("account_name");					
					moniter[0] = rs.getString("account_name");
				}
				if (charName.equalsIgnoreCase(rs.getString("char_name"))) {
					
					if (rs.getInt("item_id") == 40308) {
						moniter[3] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 41159) {
						moniter[4] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40087) {
						moniter[5] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40074) {
						moniter[6] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 400075) {
						moniter[7] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140087) {
						moniter[8] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140074) {
						moniter[9] = CommonUtil.numberFormat(rs.getInt("count"));
					}
				} else {
					
					if (count != 0) {
						tModel.addRow(moniter);
						
						for (int i = 0; i < moniter.length; i++) {
							moniter[i] = 0;
						}
					}
					
					charName = rs.getString("char_name");					
					moniter[1] = rs.getString("char_name");
					moniter[2] = rs.getInt("onlinestatus") == 0 ? "X" : "O";
					
					if (rs.getInt("item_id") == 40308) {
						moniter[3] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 41159) {
						moniter[4] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40087) {
						moniter[5] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 40074) {
						moniter[6] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 400075) {
						moniter[7] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140087) {
						moniter[8] = CommonUtil.numberFormat(rs.getInt("count"));
					}
					if (rs.getInt("item_id") == 140074) {
						moniter[9] = CommonUtil.numberFormat(rs.getInt("count"));
					}
				}
				
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLUtil.close(rs);			
			SQLUtil.close(pstm);
			SQLUtil.close(con);
		}
	}
	
	private class LabelRowCellEdior extends JLabel implements TableCellRenderer {
		private String columnName;
		public LabelRowCellEdior(String column) {
			this.columnName = column;
			setOpaque(true);
		}
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
			@SuppressWarnings("unused")
			Object columnValue = table.getValueAt(rowIndex, table.getColumnModel().getColumnIndex(columnName));
			if (isSelected) {
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
			} else {
				setBackground(table.getBackground());
				setForeground(table.getForeground());
				if (columnName.equals("�¶���")) {			
					if (String.valueOf(value).equalsIgnoreCase("O")) {
						setBackground(Color.green);
					} 					
					setHorizontalAlignment(JLabel.CENTER);
				} else if (columnName.equals("�Ƶ���") && Integer.parseInt(String.valueOf(value).replaceAll(",", "")) > Integer.parseInt(txt_Adena.getText())) {					
					setBackground(Color.red);
					setHorizontalAlignment(JLabel.RIGHT);
				} else if (columnName.equals("����") && Integer.parseInt(String.valueOf(value).replaceAll(",", "")) > Integer.parseInt(txt_Feather.getText())) {
					setBackground(Color.cyan);
					setHorizontalAlignment(JLabel.RIGHT);
				} else if (columnName.equals("����") && Integer.parseInt(String.valueOf(value).replaceAll(",", "")) > Integer.parseInt(txt_WeaponScroll.getText())) {
					setBackground(Color.pink);
					setHorizontalAlignment(JLabel.RIGHT);
				} else if (columnName.equals("��") && Integer.parseInt(String.valueOf(value).replaceAll(",", "")) > Integer.parseInt(txt_ArmorScroll.getText())) {
					setBackground(Color.pink);	
					setHorizontalAlignment(JLabel.RIGHT);
				} else if (columnName.equals("��ǥ") && Integer.parseInt(String.valueOf(value).replaceAll(",", "")) > Integer.parseInt(txt_Supyo.getText())) {
					setBackground(Color.blue);
					setHorizontalAlignment(JLabel.RIGHT);
				} else if (columnName.equals("�൥��") && Integer.parseInt(String.valueOf(value).replaceAll(",", "")) > Integer.parseInt(txt_GD.getText())) {
					setBackground(Color.yellow);	
					setHorizontalAlignment(JLabel.RIGHT);
				} else if (columnName.equals("����") && Integer.parseInt(String.valueOf(value).replaceAll(",", "")) > Integer.parseInt(txt_GZ.getText())) {
					setBackground(Color.green);
					setHorizontalAlignment(JLabel.RIGHT);
				} else {
					setHorizontalAlignment(JLabel.RIGHT);
				}
			}			
			setText(String.valueOf(value));
			
			return this;
		}
	}
}
