package server.manager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import l1j.server.SpecialEventHandler;
import l1j.server.GameSystem.Boss.L1BossCycle;
import l1j.server.GameSystem.Robot.Robot_Hunt;
import l1j.server.server.GMCommands;
import l1j.server.server.datatables.DropItemTable;
import l1j.server.server.datatables.DropTable;
//import l1j.server.server.datatables.GiNi;
import l1j.server.server.datatables.IpTable;
import l1j.server.server.datatables.ItemTable;
import l1j.server.server.datatables.MobSkillTable;
import l1j.server.server.datatables.NpcTable;
import l1j.server.server.datatables.PolyTable;
import l1j.server.server.datatables.ResolventTable;
import l1j.server.server.datatables.ShopTable;
import l1j.server.server.datatables.SkillsTable;
import l1j.server.server.model.L1World;
import l1j.server.server.model.Instance.L1ItemInstance;
import l1j.server.server.model.Instance.L1PcInstance;
import l1j.server.server.model.item.L1TreasureBox;
import server.GameServer;
import server.Server;
import l1j.server.Config;
import server.system.autoshop.AutoShopManager;
//import server.threads.pc.RobotThread;

/**
 * 
 * @author code
 */
public class eva {
	public static int saveCount = 0;
	
	public static int width = 0;
	public static int height = 0;

	
	public static final Object lock = new Object();
	public static String date = "";
	public static String time = "";
	
	public static boolean isServerStarted;
	public static int userCount;
	
	public static JFrame jJFrame = null;
	private JMenuBar jJMenuBar = null;
	private Container jContainer = null;
	private BorderLayout jBorderLayout = new BorderLayout();
	public static JDesktopPane jJDesktopPane = new JDesktopPane();
	// ��������
	public static ServerSettingWindow jServerSettingWindow = null;
	
	// �����α�
	public static ServerLogWindow jSystemLogWindow = null;      // �ý���
	public static ServerLogWindow jWorldChatLogWindow = null;   // ����ä��
	public static ServerLogWindow jNomalChatLogWindow = null;   // �Ϲ�ä��
	public static ServerWhisperLogWindow jWhisperChatLogWindow = null; // �ӼӸ�ä��
	public static ServerClanLogWindow jClanChatLogWindow = null;    // ����ä��
	public static ServerLogWindow jPartyChatLogWindow = null;   // ��Ƽä��
	public static ServerLogWindow jTradeChatLogWindow = null;   // ���ä��
	
	public static ServerWareLogWindow jWareHouseLogWindow = null;   // â��
	public static ServerTradeLogWindow jTradeLogWindow = null;       // �ŷ�
	public static ServerEnchantLogWindow jEnchantLogWindow = null;     // ��æ
	public static ServerObserveLogWindow jObserveLogWindow = null;     // ����
	public static ServerEventLogWindow jEventLogWindow = null;         //�̺�Ʈ
	public static ServerGMLogWindow jCommandLogWindow = null;     // ���
	public static ServerChatLogWindow jServerChatLogWindow = null;     // ����ä�� �����
	public static ServerLatterLogWindow jServerLatterLogWindow = null; // ���� �����
	public static ServerUserMoniterWindow jServerUserMoniterWindow = null; // ������ �����
	public static ServerBossLogWindow jBossLogWindow = null;     // ����
	public static ServerAccountLogWindow jAccountLogWindow = null;     //��������
	public static ServerRstatWindow jRStatLogWindow = null;     //��������
	public static ServerMemoryLogWindow jMemoryLogWindow = null;     //�޸� ����͸�
	public static ServerThreadLogWindow jThreadLogWindow = null;     //������ ����͸�

	// ��Ƽä��
	public static ServerMultiChatLogWindow jServerMultiChatLogWindow = null;
	
	// ��������
	public static ServerUserInfoWindow jServerUserInfoWindow = null;
	
	// �޼���
	public static final String NoServerStartMSG = "������ ������� �ʾҽ��ϴ�.";
	public static final String realExitServer = "������ �����Ͻðڽ��ϱ�?";
	public static final String blankSetUser = "������ �������� �ʾҽ��ϴ�.";
	public static final String characterSaveFail = "ĳ���� ���� ���� ����";
	public static final String characterSaveSuccess = "ĳ���� ���� ���� ����";
	public static final String NoConnectUser = " ĳ���ʹ� ������ ���� �ʽ��ϴ�.";
	public static final String UserDelete = "�����ϵ��� �������� ���� ĳ���͸� �����Ͻðڽ��ϱ�?";
	public static final String ReloadMSG = "���ε� �Ͻðڽ��ϱ�?";
	public static final String AllBuffMSG = "��ü������ �����Ͻðڽ��ϱ�?";
	public static final String ComaBuffMSG = "�ڸ� ������ �����Ͻðڽ��ϱ�?";
	public static final String SangABuffMSG = "���ž ������ �����Ͻðڽ��ϱ�?";
	public static final String FortuneBuffMSG = "���������� �����Ͻðڽ��ϱ�?";
	public static final String AinBuffMSG = "�����ϻ�带 �����Ͻðڽ��ϱ�?";
	public static final String AmetyhstBuffMSG = "�ڼ��������� �����Ͻðڽ��ϱ�?";
	public static final String HuksaBuffMSG = "��� ������ �����Ͻðڽ��ϱ�?";
	public static final String OpenStandByOn = "���´�� ��带 �ѽðڽ��ϱ�?";
	public static final String OpenStandByOff = "���´�� ��带 ���ðڽ��ϱ�?";
	public static final String SearchNameMSG = "�˻�� �Է��ϼ���.";
	
	public eva() {
		initialize();
		
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new eva();				
			}
		});
	}
	
	
	private void initialize() {
		try {
			Server.createServer().start();
			UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");			
			//UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");
			GMCommands.huntBot = true; 
			Robot_Hunt.getInstance().start_spawn();
			JFrame.setDefaultLookAndFeelDecorated(true);
			if (jJFrame == null) {
				jJFrame = new JFrame();
				jJFrame.setIconImage(new ImageIcon("img\\icon.gif").getImage());
				jJFrame.setSize(1920, 1050);
				jJFrame.setVisible(true);
				jJFrame.setTitle("::: ���� �Ŵ��� ���α׷� :::");
				jJFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				jContainer = jJFrame.getContentPane();
				jContainer.setLayout(jBorderLayout);
				jContainer.add("Center", jJDesktopPane);
				
				
			}	
			if (jJMenuBar == null) {
				jJMenuBar = new JMenuBar();
				jJFrame.setJMenuBar(jJMenuBar);
				
				JMenu jJMenu1 = new JMenu("����(F)");
				JMenu jJMenu2 = new JMenu("�����(M)");
				JMenu jJMenu3 = new JMenu("�����(H)");
				JMenu jJMenu4 = new JMenu("���ε�(R)");
				JMenu jJMenu5 = new JMenu("����(I)");
				
				jJMenu1.setMnemonic(KeyEvent.VK_F); // ����
				jJMenu2.setMnemonic(KeyEvent.VK_M); // �����
				jJMenu3.setMnemonic(KeyEvent.VK_H); // �����
				jJMenu4.setMnemonic(KeyEvent.VK_R); // ���ε�
				jJMenu5.setMnemonic(KeyEvent.VK_I); // ����
				
				// ����(F)
				
				JMenuItem StandByOn = new JMenuItem("���´�� ��");			
				StandByOn.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));			
				StandByOn.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (isServerStarted) {
							if (QMsg(OpenStandByOn) == 0) {
								Config.STANDBY_SERVER = true;
								jSystemLogWindow.append(getLogTime() + "��[���� ����]�����´�� ��." + "\n", "Blue");
							}	
						} else {
							errorMsg(NoServerStartMSG);
						}
					}
				});
				
				JMenuItem StandByOff = new JMenuItem("���´�� ��");			
				StandByOff.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));			
				StandByOff.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (isServerStarted) {
							if (QMsg(OpenStandByOff) == 0) {
								Config.STANDBY_SERVER = false;
								jSystemLogWindow.append(getLogTime() + "��[���� ����]�����´�� ��." + "\n", "Blue");
							}	
						} else {
							errorMsg(NoServerStartMSG);
						}
					}
				});
				
				JMenuItem serverSet = new JMenuItem("��������");			
				serverSet.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));			
				serverSet.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						jServerSettingWindow = new ServerSettingWindow();						
						jJDesktopPane.add(jServerSettingWindow, 0);	
						
						jServerSettingWindow.setLocation((jJFrame.getContentPane().getSize().width / 2) - (jServerSettingWindow.getContentPane().getSize().width / 2), (jJFrame.getContentPane().getSize().height / 2) - (jServerSettingWindow.getContentPane().getSize().height / 2));
					}
				});
				
				JMenuItem serverSave = new JMenuItem("��������");
				serverSave.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));			
				serverSave.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {	
						GameServer.getInstance().saveAllCharInfo();
						infoMsg(characterSaveSuccess);
						jSystemLogWindow.append(getLogTime() + "��[���� ����]����������" + "\n", "Red");
					}
				});			
				JMenuItem serverExit = new JMenuItem("��������");			
				serverExit.setAccelerator(KeyStroke.getKeyStroke(',', InputEvent.CTRL_DOWN_MASK));			
				serverExit.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg(realExitServer) == 0) {
							GameServer.getInstance().saveAllCharInfo();
							GameServer.getInstance().shutdownWithCountdown(10);
							jSystemLogWindow.append(getLogTime() + "��[���� ����]����������" + "\n", "Red");
						}
					}
				});
				JMenuItem serverNowExit = new JMenuItem("�ٷ�����");			
				serverNowExit.setAccelerator(KeyStroke.getKeyStroke('.', InputEvent.CTRL_DOWN_MASK));			
				serverNowExit.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg(realExitServer) == 0) {
							GameServer.getInstance().saveAllCharInfo();
							GameServer.getInstance().shutdownWithCountdown(0);
							jSystemLogWindow.append(getLogTime() + "��[���� ����]���ٷ�����" + "\n", "Red");
						}			
					}
				});
				
				jJMenu1.add(StandByOn);
				jJMenu1.add(StandByOff);
				jJMenu1.add(serverSet);
				jJMenu1.add(serverSave);
				jJMenu1.add(serverExit);
				jJMenu1.add(serverNowExit);
				
				// �����(M)
				
				JMenuItem worldSystemLogWindow = new JMenuItem("�α���");			
				worldSystemLogWindow.setAccelerator(KeyStroke.getKeyStroke('0', InputEvent.CTRL_DOWN_MASK));			
				worldSystemLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {						
						if (jSystemLogWindow != null && jSystemLogWindow.isClosed()) {
							jSystemLogWindow = null;
						}
						
						if (jSystemLogWindow == null) {
							jSystemLogWindow = new ServerLogWindow("�α���", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jSystemLogWindow, 0);
						}
					}
				});
				
				
				JMenuItem worldChatLogWindow = new JMenuItem("��üä��");			
				worldChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('1', InputEvent.CTRL_DOWN_MASK));			
				worldChatLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {						
						if (jWorldChatLogWindow != null && jWorldChatLogWindow.isClosed()) {
							jWorldChatLogWindow = null;
						}
						
						if (jWorldChatLogWindow == null) {
							jWorldChatLogWindow = new ServerLogWindow("��üä��", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jWorldChatLogWindow, 0);
						}
					}
				});	
				
				JMenuItem nomalChatLogWindow = new JMenuItem("�Ϲ�ä��");			
				nomalChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('2', InputEvent.CTRL_DOWN_MASK));			
				nomalChatLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {						
						if (jNomalChatLogWindow != null && jNomalChatLogWindow.isClosed()) {
							jNomalChatLogWindow = null;
						}
						
						if (jNomalChatLogWindow == null) {
							jNomalChatLogWindow = new ServerLogWindow("�Ϲ�ä��", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jNomalChatLogWindow, 0);
						}
					}
				});	
				
				JMenuItem whisperChatLogWindow = new JMenuItem("�ӼӸ�ä��");			
				whisperChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('3', InputEvent.CTRL_DOWN_MASK));			
				whisperChatLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (jWhisperChatLogWindow != null && jWhisperChatLogWindow.isClosed()) {
							jWhisperChatLogWindow = null;
						}
						
						if (jWhisperChatLogWindow == null) {
							jWhisperChatLogWindow = new ServerWhisperLogWindow("�ӼӸ�ä��", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jWhisperChatLogWindow, 0);
						}		
					}
				});	
				
				JMenuItem clanChatLogWindow = new JMenuItem("����ä��");			
				clanChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('4', InputEvent.CTRL_DOWN_MASK));			
				clanChatLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (jClanChatLogWindow != null && jClanChatLogWindow.isClosed()) {
							jClanChatLogWindow = null;
						}
						
						if (jClanChatLogWindow == null) {
							jClanChatLogWindow = new ServerClanLogWindow("����ä��", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jClanChatLogWindow, 0);
						}		
					}
				});	
				
				JMenuItem partyChatLogWindow = new JMenuItem("��Ƽä��");			
				partyChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('5', InputEvent.CTRL_DOWN_MASK));			
				partyChatLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (jPartyChatLogWindow != null && jPartyChatLogWindow.isClosed()) {
							jPartyChatLogWindow = null;
						}
						
						if (jPartyChatLogWindow == null) {
							jPartyChatLogWindow = new ServerLogWindow("��Ƽä��", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jPartyChatLogWindow, 0);
						}		
					}
				});	
				
				JMenuItem tradeChatLogWindow = new JMenuItem("���ä��");			
				tradeChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('6', InputEvent.CTRL_DOWN_MASK));			
				tradeChatLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (jTradeChatLogWindow != null && jTradeChatLogWindow.isClosed()) {
							jTradeChatLogWindow = null;
						}
						
						if (jTradeChatLogWindow == null) {
							jTradeChatLogWindow = new ServerLogWindow("���ä��", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jTradeChatLogWindow, 0);
						}		
					}
				});	
				
				JMenuItem wareHouseLogWindow = new JMenuItem("â��");			
				wareHouseLogWindow.setAccelerator(KeyStroke.getKeyStroke('7', InputEvent.CTRL_DOWN_MASK));			
				wareHouseLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (jWareHouseLogWindow != null && jWareHouseLogWindow.isClosed()) {
							jWareHouseLogWindow = null;
						}
						
						if (jWareHouseLogWindow == null) {
							jWareHouseLogWindow = new ServerWareLogWindow("â��", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jWareHouseLogWindow, 0);
						}		
					}
				});	
				
				JMenuItem tradeLogWindow = new JMenuItem("�ŷ�");			
				tradeLogWindow.setAccelerator(KeyStroke.getKeyStroke('8', InputEvent.CTRL_DOWN_MASK));			
				tradeLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (jTradeLogWindow != null && jTradeLogWindow.isClosed()) {
							jTradeLogWindow = null;
						}
						
						if (jTradeLogWindow == null) {
							jTradeLogWindow = new ServerTradeLogWindow("�ŷ�", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jTradeLogWindow, 0);
						}		
					}
				});
				
				JMenuItem enchatLogWindow = new JMenuItem("��æ");			
				enchatLogWindow.setAccelerator(KeyStroke.getKeyStroke('9', InputEvent.CTRL_DOWN_MASK));			
				enchatLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (jEnchantLogWindow != null && jEnchantLogWindow.isClosed()) {
							jEnchantLogWindow = null;
						}
						
						if (jEnchantLogWindow == null) {
							jEnchantLogWindow = new ServerEnchantLogWindow("��æ", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jEnchantLogWindow, 0);
						}		
					}
				});
				
				JMenuItem bossLogWindow = new JMenuItem("������ ���/����");			
				bossLogWindow.setAccelerator(KeyStroke.getKeyStroke(';', InputEvent.CTRL_DOWN_MASK));			
				bossLogWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (jBossLogWindow != null && jBossLogWindow.isClosed()) {
							jBossLogWindow = null;
						}
						
						if (jBossLogWindow == null) {
							jBossLogWindow = new ServerBossLogWindow("������ ���/����", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jBossLogWindow, 0);
						}		
					}
				});
				JMenuItem observeLogWindow = new JMenuItem("���׸����");
				observeLogWindow.setAccelerator(KeyStroke.getKeyStroke('0', InputEvent.CTRL_DOWN_MASK));			
				observeLogWindow.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (jObserveLogWindow != null && jObserveLogWindow.isClosed()) {
							jObserveLogWindow = null;
						}
						
						if (jObserveLogWindow == null) {
							jObserveLogWindow = new ServerObserveLogWindow("���׸����", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jObserveLogWindow, 0);
						}
					}
				});
				
				JMenuItem eventLogWindow = new JMenuItem("����/����Ÿ�̸�");		
				eventLogWindow.setAccelerator(KeyStroke.getKeyStroke('-', InputEvent.CTRL_DOWN_MASK));			
				eventLogWindow.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (jEventLogWindow != null && jEventLogWindow.isClosed()) {
							jEventLogWindow = null;
						}
						
						if (jEventLogWindow == null) {
							jEventLogWindow = new ServerEventLogWindow("����/����Ÿ�̸�", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jEventLogWindow, 0);
						}
					}
				});
				JMenuItem accountLogWindow = new JMenuItem("��������");			
				accountLogWindow.setAccelerator(KeyStroke.getKeyStroke('=', InputEvent.CTRL_DOWN_MASK));			
				accountLogWindow.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (jAccountLogWindow != null && jAccountLogWindow.isClosed()) {
							jAccountLogWindow = null;
						}
						
						if (jAccountLogWindow == null) {
							jAccountLogWindow = new ServerAccountLogWindow("��������", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jAccountLogWindow, 0);
						}
					}
				});
				
				JMenuItem commandLogWindow = new JMenuItem("GMĿ�ǵ�");			
				commandLogWindow.setAccelerator(KeyStroke.getKeyStroke('=', InputEvent.CTRL_DOWN_MASK));			
				commandLogWindow.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (jCommandLogWindow != null && jCommandLogWindow.isClosed()) {
							jCommandLogWindow = null;
						}
						
						if (jCommandLogWindow == null) {
							jCommandLogWindow = new ServerGMLogWindow("GMĿ�ǵ�", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jCommandLogWindow, 0);
						}
					}
				});
				JMenuItem rstatLogWindow = new JMenuItem("���� �����");			
				rstatLogWindow.setAccelerator(KeyStroke.getKeyStroke('?', InputEvent.CTRL_DOWN_MASK));			
				rstatLogWindow.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (jRStatLogWindow != null && jRStatLogWindow.isClosed()) {
							jRStatLogWindow = null;
						}
						
						if (jRStatLogWindow == null) {
							jRStatLogWindow = new ServerRstatWindow("���� �����", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jRStatLogWindow, 0);
						}
					}
				});
				JMenuItem serverChatLogWindow = new JMenuItem("��Ƽ ä�� �����");			
				serverChatLogWindow.setAccelerator(KeyStroke.getKeyStroke('\\', InputEvent.CTRL_DOWN_MASK));			
				serverChatLogWindow.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (jServerChatLogWindow != null && jServerChatLogWindow.isClosed()) {
							jServerChatLogWindow = null;
						}
						
						if (jServerChatLogWindow == null) {
							jServerChatLogWindow = new ServerChatLogWindow("��Ƽ ä�� �����", 20, 20, width, height, true, true);				
							jJDesktopPane.add(jServerChatLogWindow, 0);
						}
					}
				});
				
				JMenuItem serverUserMoniterWindow = new JMenuItem("������ ������ �����");			
				serverUserMoniterWindow.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.CTRL_DOWN_MASK));			
				serverUserMoniterWindow.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (jServerUserMoniterWindow != null && jServerUserMoniterWindow.isClosed()) {
							jServerUserMoniterWindow = null;
						}
						
						if (jServerUserMoniterWindow == null) {
							jServerUserMoniterWindow = new ServerUserMoniterWindow("������ ������ �����", 20, 20, width + 300, height + 300, true, true);				
							jJDesktopPane.add(jServerUserMoniterWindow, 0);
						}	
					}
				});
			
				
				jJMenu2.add(worldSystemLogWindow);
				jJMenu2.add(worldChatLogWindow);
				jJMenu2.add(nomalChatLogWindow);
				jJMenu2.add(whisperChatLogWindow);
				jJMenu2.add(clanChatLogWindow);
				jJMenu2.add(partyChatLogWindow);
				jJMenu2.add(tradeChatLogWindow);
				jJMenu2.add(wareHouseLogWindow);
				jJMenu2.add(tradeLogWindow);
				jJMenu2.add(enchatLogWindow);
				jJMenu2.add(bossLogWindow);
				jJMenu2.add(accountLogWindow);
				jJMenu2.add(observeLogWindow);
				jJMenu2.add(eventLogWindow);
				jJMenu2.add(commandLogWindow);
				jJMenu2.add(rstatLogWindow);
				jJMenu2.add(serverChatLogWindow);
				jJMenu2.add(serverUserMoniterWindow);
				jJMenu2.add(serverUserMoniterWindow);

				// �����(H)
				JMenuItem characterDelete = new JMenuItem("ĳ���ͻ���");			
				characterDelete.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.CTRL_DOWN_MASK));			
				characterDelete.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (isServerStarted) {
							if (QMsg(UserDelete) == 0) {
								try {
									Server.createServer().clearDB();
								} catch (SQLException ex) {
	
								}
								jSystemLogWindow.append(getLogTime() + "��[���� ����]��ĳ���ͻ��� �Ϸ�." + "\n", "Red");
							} 
						} else {
							errorMsg(NoServerStartMSG);
						}
					}
				});	
				
				JMenuItem allBuff = new JMenuItem("��ü����");			
				allBuff.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.CTRL_DOWN_MASK));			
				allBuff.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (isServerStarted) {
							if (QMsg(AllBuffMSG) == 0) {
								SpecialEventHandler.getInstance().doAllBuf();
								jSystemLogWindow.append(getLogTime() + "��[���� ����]����ü����." + "\n", "Blue");
							}	
						} else {
							errorMsg(NoServerStartMSG);
						}
					}
				});	
				JMenuItem autoShop = new JMenuItem("���λ���");			
				autoShop.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.CTRL_DOWN_MASK));			
				autoShop.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (isServerStarted) {
							if (AutoShopManager.getInstance().isAutoShop()) {
								if (QMsg("���λ����� ���� Ȱ��ȭ ���Դϴ�. ��Ȱ��ȭ �Ͻðڽ��ϱ�?") == 0) {
									AutoShopManager.getInstance().isAutoShop(false);
									jSystemLogWindow.append(getLogTime() + "��[���� ����]�����λ��� ��Ȱ��ȭ." + "\n", "Red");
								}		
							} else {
								if (QMsg("���λ����� ���� ��Ȱ��ȭ ���Դϴ�. Ȱ��ȭ �Ͻðڽ��ϱ�?") == 0) {
									AutoShopManager.getInstance().isAutoShop(true);
									jSystemLogWindow.append(getLogTime() + "��[���� ����]�����λ��� Ȱ��ȭ." + "\n", "Blue");
								}
							}
						} else {
							errorMsg(NoServerStartMSG);
						}
					}
				});	
				
				JMenuItem chat = new JMenuItem("����ä��");			
				chat.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.CTRL_DOWN_MASK));			
				chat.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (isServerStarted) {
							if (L1World.getInstance().isWorldChatElabled()) {
								if (QMsg("����ä���� ���� Ȱ��ȭ ���Դϴ�. ��Ȱ��ȭ �Ͻðڽ��ϱ�?") == 0) {
									SpecialEventHandler.getInstance().doNotChatEveryone();
									jSystemLogWindow.append(getLogTime() + "��[���� ����]������ä�� ��Ȱ��ȭ." + "\n", "Red");
								}		
							} else {
								if (QMsg("����ä���� ���� ��Ȱ��ȭ ���Դϴ�. Ȱ��ȭ �Ͻðڽ��ϱ�?") == 0) {
									SpecialEventHandler.getInstance().doChatEveryone();
									jSystemLogWindow.append(getLogTime() + "��[���� ����]������ä�� Ȱ��ȭ." + "\n", "Blue");
								}
							}
						} else {
							errorMsg(NoServerStartMSG);
						}
					}
				});	
				
				jJMenu3.add(characterDelete);
				jJMenu3.add(allBuff);
				jJMenu3.add(autoShop);
				jJMenu3.add(chat);
				
				JMenuItem dropReload = new JMenuItem("�������Ʈ");			
				dropReload.setAccelerator(KeyStroke.getKeyStroke('U', InputEvent.SHIFT_DOWN_MASK));			
				dropReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("Drop " + ReloadMSG) == 0) {
							DropTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] Drop Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem dropItemReload = new JMenuItem("������������");			
				dropItemReload.setAccelerator(KeyStroke.getKeyStroke('I', InputEvent.SHIFT_DOWN_MASK));			
				dropItemReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("DropItem " + ReloadMSG) == 0) {
							DropItemTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] DropItem Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem polyReload = new JMenuItem("����");			
				polyReload.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.SHIFT_DOWN_MASK));			
				polyReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("Poly " + ReloadMSG) == 0) {
							PolyTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] Poly Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem resolventReload = new JMenuItem("���ؾ�����");			
				resolventReload.setAccelerator(KeyStroke.getKeyStroke('P', InputEvent.SHIFT_DOWN_MASK));			
				resolventReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("Resolvent " + ReloadMSG) == 0) {
							ResolventTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] Resolvent Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem treasureBoxReload = new JMenuItem("���ھ�����");			
				treasureBoxReload.setAccelerator(KeyStroke.getKeyStroke('A', InputEvent.SHIFT_DOWN_MASK));			
				treasureBoxReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("TreasureBox " + ReloadMSG) == 0) {
							L1TreasureBox.load();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] TreasureBox Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem skillsReload = new JMenuItem("����");			
				skillsReload.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.SHIFT_DOWN_MASK));			
				skillsReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("Skills " + ReloadMSG) == 0) {
							SkillsTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] Skills Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem mobSkillReload = new JMenuItem("����ų");			
				mobSkillReload.setAccelerator(KeyStroke.getKeyStroke('D', InputEvent.SHIFT_DOWN_MASK));			
				mobSkillReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("MobSkill " + ReloadMSG) == 0) {
							MobSkillTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] MobSkill Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem mapFixKeyReload = new JMenuItem("���Ƚ�Ű");			
				mapFixKeyReload.setAccelerator(KeyStroke.getKeyStroke('F', InputEvent.SHIFT_DOWN_MASK));			
				mapFixKeyReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("MapFixKey " + ReloadMSG) == 0) {
							MobSkillTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] MapFixKey Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem itemReload = new JMenuItem("������");			
				itemReload.setAccelerator(KeyStroke.getKeyStroke('G', InputEvent.SHIFT_DOWN_MASK));			
				itemReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("Item " + ReloadMSG) == 0) {
							ItemTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] Item Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem shopReload = new JMenuItem("����");			
				shopReload.setAccelerator(KeyStroke.getKeyStroke('H', InputEvent.SHIFT_DOWN_MASK));			
				shopReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("Shop " + ReloadMSG) == 0) {
							ShopTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] Shop Update Complete..." + "\n", "Red");
						}			
					}
				});
				JMenuItem npcReload = new JMenuItem("���ǽ�");			
				npcReload.setAccelerator(KeyStroke.getKeyStroke('K', InputEvent.SHIFT_DOWN_MASK));			
				npcReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("Npc " + ReloadMSG) == 0) {
							NpcTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] Npc Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem bossCycleReload = new JMenuItem("��������Ŭ");			
				bossCycleReload.setAccelerator(KeyStroke.getKeyStroke('L', InputEvent.SHIFT_DOWN_MASK));			
				bossCycleReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("BossCycle " + ReloadMSG) == 0) {
							L1BossCycle.load();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] BossCycle Update Complete..." + "\n", "Red");
						}			
					}
				});
				JMenuItem banIpReload = new JMenuItem("�������");			
				banIpReload.setAccelerator(KeyStroke.getKeyStroke('B', InputEvent.SHIFT_DOWN_MASK));			
				banIpReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("BanIp " + ReloadMSG) == 0) {
							IpTable.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] BanIp Update Complete..." + "\n", "Red");
						}			
					}
				});
				/*JMenuItem robotReload = new JMenuItem("�κ�");			
				robotReload.setAccelerator(KeyStroke.getKeyStroke(',', InputEvent.SHIFT_DOWN_MASK));			
				robotReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("Robot " + ReloadMSG) == 0) {
							RobotThread.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] Robot Update Complete..." + "\n", "Red");
						}			
					}
				});*/
				
				JMenuItem configReload = new JMenuItem("���Ǳ�");			
				configReload.setAccelerator(KeyStroke.getKeyStroke('W', InputEvent.SHIFT_DOWN_MASK));			
				configReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("Config " + ReloadMSG) == 0) {
							Config.load();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] Config Update Complete..." + "\n", "Red");
						}			
					}
				});
				
				JMenuItem weaponAddDamageReload = new JMenuItem("�����߰�Ÿ��");			
				weaponAddDamageReload.setAccelerator(KeyStroke.getKeyStroke('E', InputEvent.SHIFT_DOWN_MASK));			
				weaponAddDamageReload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("WeaponAddDamage " + ReloadMSG) == 0) {
							Config.load();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] WeaponAddDamage Update Complete..." + "\n", "Red");
						}			
					}
				});
				
			/*	JMenuItem GiNireload = new JMenuItem("�����̺�Ʈ");			
				GiNireload.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.SHIFT_DOWN_MASK));			
				GiNireload.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						if (QMsg("GiNi " + ReloadMSG) == 0) {
							GiNi.reload();
							jSystemLogWindow.append(getLogTime() + "��[���� ����] GiNiTable Update Complete..." + "\n", "Red");
						}			
					}
				});*/
				
				jJMenu4.add(dropReload);
				jJMenu4.add(dropItemReload);
				jJMenu4.add(polyReload);
				jJMenu4.add(resolventReload);
				jJMenu4.add(treasureBoxReload);
				jJMenu4.add(skillsReload);
				jJMenu4.add(mobSkillReload);
				jJMenu4.add(mapFixKeyReload);
				jJMenu4.add(itemReload);
				jJMenu4.add(shopReload);
				jJMenu4.add(npcReload);
				jJMenu4.add(bossCycleReload);
				jJMenu4.add(banIpReload);
				//jJMenu4.add(robotReload);
				//jJMenu4.add(GiNireload);
				jJMenu4.add(configReload);
				jJMenu4.add(weaponAddDamageReload);
				
				
				JMenuItem developerInfo = new JMenuItem("����ó");						
				developerInfo.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) {
						JOptionPane.showMessageDialog(null, "�� �� ��", "������ ����ó", JOptionPane.INFORMATION_MESSAGE);		
						
					}
				});
				
				jJMenu5.add(developerInfo);
				
				jJMenuBar.add(jJMenu1);
				jJMenuBar.add(jJMenu2);
				jJMenuBar.add(jJMenu3);
				jJMenuBar.add(jJMenu4);
				jJMenuBar.add(jJMenu5);
				
				
				width = jJFrame.getContentPane().getSize().width / 3;
				height = (jJFrame.getContentPane().getSize().height - 50) / 3;
				
				/** ������ â��ġ **/
				if (jSystemLogWindow == null) { 
					jSystemLogWindow = new ServerLogWindow("�α���", 280, 0, 280, height, true, true);				
					jJDesktopPane.add(jSystemLogWindow);
				}
				
				if (jServerUserInfoWindow == null) {
					jServerUserInfoWindow = new ServerUserInfoWindow("��������", 560, 0, 570, height, true, true); // width +10 ����	
					jJDesktopPane.add(jServerUserInfoWindow);
				}
				if (jServerLatterLogWindow == null) {
					jServerLatterLogWindow = new ServerLatterLogWindow("������", 560, height, 570, height, true, true); // width +10 ����			
					jJDesktopPane.add(jServerLatterLogWindow);
				}
				if (jBossLogWindow == null) {
					jBossLogWindow = new ServerBossLogWindow("������ ���/����", 1120, 0, 280, height, true, true);				
					jJDesktopPane.add(jBossLogWindow, 0);
				}
				if (jEventLogWindow == null) {
					jEventLogWindow = new ServerEventLogWindow("����/���� Ÿ�̸�", 1400, 0, 280, height, true, true);				
					jJDesktopPane.add(jEventLogWindow, 0);
				}
				if (jAccountLogWindow == null) {
					jAccountLogWindow = new ServerAccountLogWindow("��������", 0, 0, 280, height, true, true);				
					jJDesktopPane.add(jAccountLogWindow, 0);
				}
				if (jRStatLogWindow == null) {
					jRStatLogWindow = new ServerRstatWindow("���� �����", 0, 322, 280, height, true, true);				
					jJDesktopPane.add(jRStatLogWindow, 0);
				}
				if (jCommandLogWindow == null) {
					jCommandLogWindow = new ServerGMLogWindow("GMĿ�ǵ�", 280, 322, 280, height, true, true);				
					jJDesktopPane.add(jCommandLogWindow);
				}
				if (jObserveLogWindow == null) {
					jObserveLogWindow = new ServerObserveLogWindow("���׸����", 1120, 322, 280, height, true, true);				
					jJDesktopPane.add(jObserveLogWindow);
				}
				if (jEnchantLogWindow == null) {
					jEnchantLogWindow = new ServerEnchantLogWindow("��æ�����", 1400, 322, 280, height, true, true);				
					jJDesktopPane.add(jEnchantLogWindow);
				}
				if (jClanChatLogWindow == null) {
					jClanChatLogWindow = new ServerClanLogWindow("����ä��", 0, 644, 280, height+30, true, true);				
					jJDesktopPane.add(jClanChatLogWindow);
				}
				if (jWhisperChatLogWindow == null) {
					jWhisperChatLogWindow = new ServerWhisperLogWindow("�ӼӸ�", 280, 644, 280, height+30, true, true);		
					jJDesktopPane.add(jWhisperChatLogWindow);
				}
				if (jServerMultiChatLogWindow == null) {
					jServerMultiChatLogWindow = new ServerMultiChatLogWindow("��Ƽä��", 560, 644, 570, height+30, true, true);				
					jJDesktopPane.add(jServerMultiChatLogWindow);
				}
				if (jWareHouseLogWindow == null) {
					jWareHouseLogWindow = new ServerWareLogWindow("â��", 1120, 644, 280, height+30, true, true);				
					jJDesktopPane.add(jWareHouseLogWindow);
				}
				if (jTradeLogWindow == null) {
					jTradeLogWindow = new ServerTradeLogWindow("�ŷ�", 1400, 644, 280, height+30, true, true);				
					jJDesktopPane.add(jTradeLogWindow);
				}
				if (jMemoryLogWindow == null) {
					//jMemoryLogWindow = new ServerMemoryLogWindow("�޸� ��뷮 �����", 1400, 935, 280, 60, true, true);
					jMemoryLogWindow = new ServerMemoryLogWindow("�޸� ��뷮 �����", 1680, 0, 240, height, true, true);
					jJDesktopPane.add(jMemoryLogWindow);
				}
				if (jThreadLogWindow == null) {
					jThreadLogWindow = new ServerThreadLogWindow("������ Ȱ��ȭ �����", 1680, 322, 240, height, true, true);				
					jJDesktopPane.add(jThreadLogWindow);
				}
			}
			isServerStarted = true;
			
		} catch (Exception e) {}		
	}
	
	/** *** �α� ���� �κ� ***** */
	public static int userCount(int i) {
		userCount += i;
		return userCount;
	}

	public static void refreshMemory() {
		//lblMemory.setText(" �޸� : " + SystemUtil.getUsedMemoryMB() + " MB");
	}
	public static void AccountAppend(String s) {
		// �����α�â : 2s
		//textServer.append("\n" + getLogTime() + "��" + s + "��" + s1);
		
		if (jAccountLogWindow != null && !jAccountLogWindow.isClosed()) {
			jAccountLogWindow.append(getLogTime() + "��" + s + "\n", "Yellow");
		} else {
			jAccountLogWindow = null;
		}
	}
	public static void MemoryLogAppend(String s) {
		if (jMemoryLogWindow != null && !jMemoryLogWindow.isClosed()) {
			jMemoryLogWindow.append(getLogTime() + " " + s + "\n", "Yellow");
		} else {
			jMemoryLogWindow = null;
		}
	}
	public static void ThreadLogAppend(String s) {
		if (jThreadLogWindow != null && !jThreadLogWindow.isClosed()) {
			jThreadLogWindow.append(getLogTime() + " " + s + "\n", "Pink");
		} else {
			jThreadLogWindow = null;
		}
	}
	/*public static void EventLogAppend(String s) { // �̺�Ʈ�α׿���
		if (jEventLogWindow != null && !jEventLogWindow.isClosed()) {
			jEventLogWindow.append(getLogTime() + " " + s + "\n", "Cyan");
		} else {
			jEventLogWindow = null;
		}
	}*/
	public static void CrockLogAppend(String s) {
		if (jEventLogWindow != null && !jEventLogWindow.isClosed()) {
			jEventLogWindow.append(getLogTime() + " " + s + "\n", "Pink");
		} else {
			jEventLogWindow = null;
		}
	}
	public static void HackLogAppend(String s) {
		if (jObserveLogWindow != null && !jObserveLogWindow.isClosed()) {
			jObserveLogWindow.append(getLogTime() + "" + s + "\n", "Yellow");
		} else {
			jObserveLogWindow = null;
		}
	}
	public static void DropLogAppend(String s) {
		if (jBossLogWindow != null && !jBossLogWindow.isClosed()) {
			jBossLogWindow.append("" + s + "\n", "White");
		} else {
			jBossLogWindow = null;
		}
	}
	public static void DelLogAppend(String s) {
		if (jBossLogWindow != null && !jBossLogWindow.isClosed()) {
			jBossLogWindow.append("" + s + "\n", "Purple");
		} else {
			jBossLogWindow = null;
		}
	}
	public static void PickupLogAppend(String s) {
		if (jBossLogWindow != null && !jBossLogWindow.isClosed()) {
			jBossLogWindow.append("" + s + "\n", "Green");
		} else {
			jBossLogWindow = null;
		}
	}
	public static void BugLogAppend(String s) {
		if (jObserveLogWindow != null && !jObserveLogWindow.isClosed()) {
			jObserveLogWindow.append(getLogTime() + "" + s + "\n", "Red");
		} else {
			jObserveLogWindow = null;
		}
	}
	public static void RStatLogAppend(String s) {
		if (jRStatLogWindow != null && !jRStatLogWindow.isClosed()) {
			jRStatLogWindow.append(getLogTime() + "" + s + "\n", "Red");
		} else {
			jRStatLogWindow = null;
		}
	}
	public static void LogGuardianAppend(String s, String pcname, String c, String text) {
		// �����α�â : 2s
		//textServer.append("\n" + getLogTime() + "��" + s + "��" + s1);
		
		if (jClanChatLogWindow != null && !jClanChatLogWindow.isClosed()) {
			jClanChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + "[" + c + "] : " + text + "\n", "Purple");
		} else {
			jClanChatLogWindow = null;
		}
	}
	public static void LogServerAppend(String s, String s1) {
		// �����α�â : 2s
		//textServer.append("\n" + getLogTime() + "��" + s + "��" + s1);
		
		if (jSystemLogWindow != null && !jSystemLogWindow.isClosed()) {
			jSystemLogWindow.append(getLogTime() + "��" + s + "��" + s1 + "\n", "White");
		} else {
			jSystemLogWindow = null;
		}
	}
	public static void LogFailServerAppend(String s) {
		// �����α�â : 2s
		//textServer.append("\n" + getLogTime() + "��" + s + "��" + s1);
		
		if (jSystemLogWindow != null && !jSystemLogWindow.isClosed()) {
			jSystemLogWindow.append(getLogTime() + "��" + s + "��", "Red");
		} else {
			jSystemLogWindow = null;
		}
	}
	public static void WorldChatAppend(String s, String pcname, String text) {
		// �����α�â : 2s
		//textServer.append("\n" + getLogTime() + "��" + s + "��" + s1);
		if (jWorldChatLogWindow != null && !jWorldChatLogWindow.isClosed()) {
			jWorldChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + "��:" + text + "\n", "White");
		} else {
			jWorldChatLogWindow = null;
		}	
		if (jServerMultiChatLogWindow != null) {
			jServerMultiChatLogWindow.append("worldChatText", getLogTime() + "��" + s + "��" + pcname + "��:" + text + "\n", "White");
		}
	}
	public static void PartyChatAppend(String s, String pcname, String text) {
		// �����α�â : 2s
		//textServer.append("\n" + getLogTime() + "��" + s + "��" + s1);
		if (jPartyChatLogWindow != null && !jPartyChatLogWindow.isClosed()) {
			jPartyChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + " : " + text + "\n", "Orange");
		} else {
			jPartyChatLogWindow = null;
		}	
		if (jServerChatLogWindow != null && !jServerChatLogWindow.isClosed()) {
			if (jServerChatLogWindow.chk_World.isSelected()) {
				jServerChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + "��:" + text + "\n", "Orange");
			}
		} else {
			jServerChatLogWindow = null;
		}
		if (jServerMultiChatLogWindow != null) {
			jServerMultiChatLogWindow.append("partyChatText", getLogTime() + "��" + s + "��" + pcname + " : " + text + "\n", "Orange");
		}
		
	}
	public static synchronized void LogServerAppend(String s, L1PcInstance pc, String ip, int i) {
		// �����α�â : s - ����,����
		//textServer.append("\n" + getLogTime() + "��" + s + "��" + pc.getName() + ":" + pc.getAccountName() + "��" + ip + "��U:" + userCount(i));
		
		if (jServerUserInfoWindow != null) {
			if (jServerUserInfoWindow.getTableModel() != null) {
				try {
					jServerUserInfoWindow.jJTable0.clearSelection();					
					
					if (s.equals("����")) {	
						jServerUserInfoWindow.jJTable0.clearSelection();
						String[] name = new String[1];
						name[0] = pc.getName();
						jServerUserInfoWindow.getTableModel().addRow(name);						
					} else {						
						jServerUserInfoWindow.jJTable0.clearSelection();
						for (int row = 0; row < jServerUserInfoWindow.getTableModel().getRowCount(); row++) {
							if (jServerUserInfoWindow.getTableModel().getValueAt(row, 0).equals(pc.getName())) {
								jServerUserInfoWindow.getTableModel().removeRow(row);
								break;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				jServerUserInfoWindow.getLbl_UserCount().setText("�����ڼ� : " + (jServerUserInfoWindow.getTableModel().getRowCount()));	
			}
		}
		if (jSystemLogWindow != null && !jSystemLogWindow.isClosed()) {
			jSystemLogWindow.append(getLogTime() + "��" + s + "  [" + pc.getName() + "]  [" + pc.getAccountName() + "]  [" + ip + "]�� [" + userCount(i) + "]\n", "Yellow");
		} else {
			jSystemLogWindow = null;
		}
	}

	public static void LogChatAppend(String s, String pcname, String text) {
		// ����ä��â s: �Ϲ�(-), ��Ƽ(#), �׷�(*), ��ü(&)
		//textChat.append("\n" + getLogTime() + "��" + s + "��" + pcname + "��:" + text);
		if (jServerChatLogWindow != null && !jServerChatLogWindow.isClosed()) {
			if (jServerChatLogWindow.chk_World.isSelected()) {
				jServerChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + "��:" + text + "\n", "White");
			}
		} else {
			jServerChatLogWindow = null;
		}
		
		if (jServerMultiChatLogWindow != null) {
			jServerMultiChatLogWindow.append("worldChatText", getLogTime() + "��" + s + "��" + pcname + "��:" + text + "\n", "White");
		}
	}
	

	public static void LogChatNormalAppend(String s, String pcname, String text) {
		// ����ä��â s: �Ϲ�(N)
		//textChatNormal.append("\n" + getLogTime() + "��" + s + "��" + pcname + " : " + text);
		
		if (jNomalChatLogWindow != null && !jNomalChatLogWindow.isClosed()) {
			jNomalChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + " : " + text + "\n", "White");
		} else {
			jNomalChatLogWindow = null;
		}
		
		if (jServerChatLogWindow != null && !jServerChatLogWindow.isClosed()) {
			if (jServerChatLogWindow.chk_Noaml.isSelected()) {
				jServerChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + " : " + text + "\n", "White");
			}
		} else {
			jServerChatLogWindow = null;
		}
		if (jServerMultiChatLogWindow != null) {
			jServerMultiChatLogWindow.append("nomalChatText", getLogTime() + "��" + s + "��" + pcname + " : " + text + "\n", "White");
		}
		/*if (jServerMultiChatLogWindow != null) {
			jServerMultiChatLogWindow.append("nomalChatText", getLogTime() + "��" + s + "��" + pcname + " : " + text + "\n", "White");
		}*/
	}
	public static void LogChatWisperAppend(String s, String pcname, String c, String text, String sel) {
		// ����ä��â s: �Ӹ�(>)
		//textChatWisper.append("\n" + getLogTime() + "��" + s + "��" + pcname + sel + c + " : " + text);
		
		if (jWhisperChatLogWindow != null && !jWhisperChatLogWindow.isClosed()) {
			jWhisperChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + sel + c + " : " + text + "\n", "Pink");
		} else {
			jWhisperChatLogWindow = null;
		}
		
		if (jServerChatLogWindow != null && !jServerChatLogWindow.isClosed()) {
			if (jServerChatLogWindow.chk_Whisper.isSelected()) {
				jServerChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + sel + c + " : " + text + "\n", "Pink");
			}
		} else {
			jServerChatLogWindow = null;
		}
	}

	public static void LogChatClanAppend(String s, String pcname, String c, String text) {
		// ����ä��â s: ����(@)
		//textChatClan.append("\n" + getLogTime() + "��" + s + "��" + pcname + "[" + c + "] : " + text);
		if (jClanChatLogWindow != null && !jClanChatLogWindow.isClosed()) {
			jClanChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + "[" + c + "] : " + text + "\n", "Green");
		} else {
			jClanChatLogWindow = null;
		}
		
		if (jServerChatLogWindow != null && !jServerChatLogWindow.isClosed()) {
			if (jServerChatLogWindow.chk_Clan.isSelected()) {
				jServerChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + "[" + c + "] : " + text + "\n", "Green");
			}
		} else {
			jServerChatLogWindow = null;
		}
	}

	public static void LogChatTradeAppend(String s, String pcname, String text) {
		// ����ä��â s: ���($)
		//textChatTrade.append("\n" + getLogTime() + "��" + s + "��" + pcname + " : " + text);
		
		if (jTradeChatLogWindow != null && !jTradeChatLogWindow.isClosed()) {
			jTradeChatLogWindow.append(getLogTime() + "��" + s + "��" + pcname + " : " + text + "\n", "White");
		} else {
			jTradeChatLogWindow = null;
		}
		
	}

	public static void LogWareHouseAppend(String s, String pcname, String clanname, L1ItemInstance item, int count, int obj) {
		// â��α� : �ð� s
		if (item.getItem().getType2() == 0 && item.getItem().getlogcheckitem() == 0)
			return;

		if (jWareHouseLogWindow != null && !jWareHouseLogWindow.isClosed()) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(getLogTime() + "��" + s + "��" + pcname);
						
			if (!clanname.equalsIgnoreCase("")) {
				sb.append("[" + clanname+"]");
			}
			sb.append("��");
			if (item.getEnchantLevel() > 0) {
				sb.append("+" + item.getEnchantLevel());
			} else if (item.getEnchantLevel() < 0) {
				sb.append("" + item.getEnchantLevel());
			}
				
			sb.append(item.getName() + "��B:" + item.getBless() + "��C:" + count + "��O:" + obj + "\n");
					
			jWareHouseLogWindow.append(sb.toString(), "Cyan");
		} else {
			jWareHouseLogWindow = null;
		}
	}

	public static void LogTradeAppend(String s, String pcname, String targetname, int enchant, String itemname, int bless, int count, int obj) {
		// ��ȯ �α�
		
		if (jTradeLogWindow != null && !jTradeLogWindow.isClosed()) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(getLogTime() + "��" + s + "��" + pcname + "��" + targetname + "��");		
			
			if (enchant > 0) {
				sb.append("+" + enchant);
			} else if (enchant < 0) {
				sb.append("-" + enchant);
			}
			
			sb.append(itemname + "��B:" + bless + "��C:" + count + "��O:" + obj + "\n");
			
			jTradeLogWindow.append(sb.toString(), "Pink");
		} else {
			jTradeLogWindow = null;
		}
	}

	public static void LogShopAppend(String s, String pcname, String targetname, int enchant, String itemname, int bless, int count) {
		// ���� �α� : �ð� ���� p t ������(�ŷ��Ƶ���)
		
		if (jTradeLogWindow != null && !jTradeLogWindow.isClosed()) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(getLogTime() + "��" + s + "��" + pcname + "��" + targetname + "��");
			if (enchant > 0) {
				sb.append("+" + enchant);
			} else if (enchant < 0) {
				sb.append("-" + enchant);
			}
			
			sb.append(itemname + "��B:" + bless + "��C:" + count + "\n");
			
			jTradeLogWindow.append(sb.toString(), "Yellow");
		} else {
			jTradeLogWindow = null;
		}
	}

	public static void LogEnchantAppend(String s, String pcname, String enchantlvl, String itemname, int obj) {
		// ��æ �α�
		//textEnchant.append("\n" + getLogTime() + "��" + s + "��" + pcname + "��" + enchantlvl + "��" + itemname + "��O:" + obj);
		
		if (jEnchantLogWindow != null && !jEnchantLogWindow.isClosed()) {	
			if (s.indexOf("����") > -1) {
				jEnchantLogWindow.append(getLogTime() + "��" + s + "��" + pcname + "��" + enchantlvl + "��" + itemname + "��O:" + obj + "\n", "Cyan");
			} else {
				jEnchantLogWindow.append(getLogTime() + "��" + s + "��" + pcname + "��" + enchantlvl + "��" + itemname + "��O:" + obj + "\n", "Red");
			}
		} else {
			jEnchantLogWindow = null;
		}

	}
	public static void LogBossAppend(String s) {	
		if (jBossLogWindow != null && !jBossLogWindow.isClosed()) {	
			StringBuilder sb = new StringBuilder();		
			sb.append(getLogTime() + "��" + s + "\n");
			sb.append("��");

		} else {
			jBossLogWindow = null;
		}
	}

	public static void LogObserverAppend(String s, String pcname, L1ItemInstance item, int count, int obj) {		
		if (jObserveLogWindow != null && !jObserveLogWindow.isClosed()) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(getLogTime() + "��" + s + "��" + pcname);
			sb.append("��");
			if (item.getEnchantLevel() > 0) {
				sb.append("+" + item.getEnchantLevel());
			} else if (item.getEnchantLevel() < 0) {
				sb.append("" + item.getEnchantLevel());
			}
			
			sb.append(item.getName() + "��C:" + count + "��O:" + obj + "\n");
			
			jObserveLogWindow.append(sb.toString(), "White");
		} else {
			jObserveLogWindow = null;
		}
	}
	
	public static void LogObserver1Append(String s, String pcname) {
		// ���÷α�
//		textObserver.append("\n" + getLogTime() + "��" + s + "��" + pcname);
//		textObserver.append("��");
//		if (item.getEnchantLevel() > 0) {
//			textObserver.append("+" + item.getEnchantLevel());
//		} else if (item.getEnchantLevel() < 0) {
//			textObserver.append("" + item.getEnchantLevel());
//		}
//		textObserver.append(item.getName() + "��C:" + count + "��O:" + obj);
		
		if (jObserveLogWindow != null && !jObserveLogWindow.isClosed()) {
			StringBuilder sb = new StringBuilder();
			sb.append(getLogTime() + "��" + s + "��" + pcname);
			sb.append("��");
			
			jObserveLogWindow.append(sb.toString(), "Blue");
		} else {
			jObserveLogWindow = null;
		}
		
		if (jServerMultiChatLogWindow != null) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(getLogTime() + "��" + s + "��" + pcname);
			sb.append("��");
			
			jServerMultiChatLogWindow.append("observeText", sb.toString(), "Blue");			
		}
	}


	public static void LogBugAppend(String s, L1PcInstance pc, int type) {
		if (jObserveLogWindow != null && !jObserveLogWindow.isClosed()) {	
			StringBuilder sb = new StringBuilder();
			sb.append(getLogTime() + "��" + s + "��" + pc.getName());
			switch (type) {
			case 1: // ����
				//sb.append("��P:" + pc.getGfxId().getTempCharGfx() + "��R:" + pc.getSpeedRightInterval() + "��>I:" + pc.getSpeedInterval() + "\n");
				
				jObserveLogWindow.append(sb.toString(), "Yellow");
				break;
			case 2: // �վ�
				sb.append("��x,y,map:" + pc.getLocation().getX() + "," + pc.getLocation().getY() + "," + pc.getLocation().getMapId() + "\n");
				
				jObserveLogWindow.append(sb.toString(), "Yellow");
				break;
			case 3:
				sb.append(pc.getName() + " " + s + "\n");
				
				jObserveLogWindow.append(sb.toString(), "Yellow");
				break;
			default:
				break;
			}
		} else {
			jObserveLogWindow = null;
		}
	}

	public static void LogCommandAppend(String pcname, String cmd, String arg) {
		// Ŀ�ǵ� �α�
		//textCommand.append("\n" + getLogTime() + "��" + pcname + "��C:" + cmd + " " + arg);
		
		if (jCommandLogWindow != null && !jCommandLogWindow.isClosed()) {	
			jCommandLogWindow.append(getLogTime() + "��" + pcname + "��C : " + cmd + " " + arg + "\n", "Orange");			
		} else {
			jCommandLogWindow = null;
		}
	}
	
	/** �޼��� ó�� �κ� */
	public static int QMsg(String s) { // Question Window
		int result = JOptionPane.showConfirmDialog(null, s, "Server Message", 2, JOptionPane.INFORMATION_MESSAGE);
		return result;
	}
	
	public static void infoMsg(String s) { // Message Window(INFOMATION)
		JOptionPane.showMessageDialog(null, s, "Server Message", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void errorMsg(String s) { // Message Window(ERROR)
		JOptionPane.showMessageDialog(null, s, "Server Message", JOptionPane.ERROR_MESSAGE);
	}
	
	/** ���� �ð� �������� */
	private static String getLogTime() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd HH:mm:ss");
		String time = dateFormat.format(currentDate.getTime());
		return time;
	}
	
	/** �α� ���� */
	public static void savelog() {
			File f = null;
			String sTemp = "";
			synchronized (eva.lock) {
				sTemp = eva.getDate();
				StringTokenizer s = new StringTokenizer(sTemp, " ");
				eva.date = s.nextToken();
				eva.time = s.nextToken();
				f = new File("ServerLog/" + eva.date);
				if (!f.exists()) {
					f.mkdir();
				}
		jSystemLogWindow.savelog();
		jTradeLogWindow.savelog();
		jWareHouseLogWindow.savelog();
		jEventLogWindow.savelog();
		jBossLogWindow.savelog();
		jEnchantLogWindow.savelog();
		jObserveLogWindow.savelog();
		jAccountLogWindow.savelog();
		jCommandLogWindow.savelog();
		jRStatLogWindow.savelog();
		jWhisperChatLogWindow.savelog();
		jClanChatLogWindow.savelog();
		jServerMultiChatLogWindow.savelog();
		sTemp = null;
		eva.date = null;
		eva.time = null;				
	}
	}
	
	public static void flush(JTextPane text, String FileName, String date) {
		try {
			RandomAccessFile rnd = new RandomAccessFile("ServerLog/" + date + "/" + FileName + ".txt", "rw");
			rnd.write(text.getText().getBytes());
			rnd.close();
		} catch (Exception e) {
		}
	}
	
	// ��¥����(yyyy-MM-dd) �ð�(hh-mm)
	public static String getDate() {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh-mm", Locale.KOREA);
		return s.format(Calendar.getInstance().getTime());
	}
}