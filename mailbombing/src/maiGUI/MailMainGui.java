package maiGUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import mailrecive.MailMessage2;
import mailrecive.MailReciveController;
import mailsend.MailConfig;
import mailsend.MailMessage;
import mailsend.MailModel;
import mailsend.MailSendController;

public class MailMainGui extends JFrame implements ActionListener {
	public JPanel jp1, jp2;
	public JLabel jlb1, jlb2, jlb3, jlb4, jlb5, jlb6, jlb7, jlb8, jlb9, jlb10, jlb11, jlb12, jlb13, jlb14, jlb15, jlb16,
			jlb17;
	public static JLabel jlb18;
	public JButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11,
			button12, button13, button14, button15;
	public JTextArea jta1, jta2;
	public JScrollPane jsp1, jsp2;
	public JRadioButton jrb1, jrb2, jrb3, jrb4;
	public ButtonGroup bg1, bg2;
	// 列表
	public JList jl1, jl2;
	// 数据模型
	public static DefaultListModel mailData = new DefaultListModel();;
	public JTextField tt1, tt2, tt3, tt4, tt5, tt6, tt7, tt8, tt9, tt10, tt11, tt12, tt13, tt14;
	public JPasswordField jpf1, jpf2;
	public JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);
	public JProgressBar jpbar1, jpbar2, jpbar3;
	public JComboBox jbb1, jbb2, jbb3;
	public JFileChooser chooseFile1;
	public File[] fs;
	public static MailUtils mu = new MailUtils();
	// 收件人
	public String accUser = "";
	public String[] accUsers;
	public boolean accUserIsOk = false;
	// 发件人
	public String sendUser = "";
	public String sendUser2 = "";
	public boolean sendUserIsOk = true;
	public boolean sendUserIsOk2 = true;
	// 抄送
	public String copyToUser = "";
	public String[] copyToUsers;
	public boolean copyToUserIsOk = false;
	// 暗抄送
	public String darkCopyToUser = "";
	public String[] darkCopyToUsers;
	public boolean darkCopyToUserIsOk = false;
	// 附件路径和名字
	public static Map<String, String> filePathName;
	public static List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
	public static List<String> filePathList = new ArrayList<String>();
	// 默认设置
	public static SendSet ss;
	public MailMessage mm;
	public static MailMessage2 mm2;
	public MailConfig mc;
	public MailConfig mc2;
	public MailModel mmodelp = new MailModel();
	public MailModel mmodelp2 = new MailModel();
	public SwingWorker<String, String> sw1;
	public SwingWorker<String, String> sw2;
	public MailSendController msc = new MailSendController();
	public MailReciveController mrc = new MailReciveController();
	public String path = null;
	public static boolean isRunning = false;
	public static boolean isRunning2 = false;
	public CountThread thread;
	public CountThread2 thread2;
	public boolean fileExit = false;
	public MailSaveModelUtils msmu = new MailSaveModelUtils();

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				// 国人牛逼主题，值得学习
				// 初始化字体
				InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 13));
				// 设置本属性将改变窗口边框样式定义
				// 系统默认样式 osLookAndFeelDecorated
				// 强立体半透明 translucencyAppleLike
				// 弱立体感半透明 translucencySmallShadow
				// 普通不透明 generalNoTranslucencyShadow
				BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
				// 设置主题为BeautyEye
				try {
					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 隐藏“设置”按钮
				UIManager.put("RootPane.setupButtonVisible", false);
				// 开启/关闭窗口在不活动时的半透明效果
				// 设置此开关量为false即表示关闭之，BeautyEye LNF中默认是true
				BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
				// 设置BeantuEye外观下JTabbedPane的左缩进
				// 改变InsetsUIResource参数的值即可实现
				UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(3, 20, 2, 20));
				// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new MailMainGui();
			}
		});

	}

	// font
	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}

	public MailMainGui() {
		// 初始化模板
		initModel();
		// 初始化界面
		init();
	}

	// 初始化模板
	private void initModel() {
		// url设置
		MailConfig mc = new MailConfig();
		// 发送基本设置
		// SendSet ss = new SendSet();
		// 发送msg
		MailMessage mm = new MailMessage();
		// 接收msg
		MailMessage2 mm2 = new MailMessage2();
		mmodelp.setMc(mc);
		mmodelp.setMm(mm);
		mmodelp.setMm2(mm2);
		mmodelp2.setMc(mc);
		mmodelp2.setMm(mm);
		mmodelp2.setMm2(mm2);
		// 原理：首先判断exe所在目录是否存在隐藏的配置文件kafkamodel.properties，
		// 如果存在，则读取此配置文件，如果不存在则生成一个默认的隐藏的配置文件

		File f = new File("sendmailmodel.properties");
		fileExit = msmu.isFileExit(f);
		if (fileExit) {
			// 获取默认配置信息
			try {
				mmodelp = msmu.getDefaultMsgSet(f, 0);
				// 初始化设置
				String s;
				if (!mmodelp.getSs().isTiming()) {
					// 如果不是定时发送
					s = mu.formatDate(new Date(), "yyyyMMddHHmmss");
				} else {
					s = mmodelp.getSs().getSendDate();
				}
				// 频次处理
				String s2;
				if (mmodelp.getSs().isFixed) {
					s2 = mmodelp.getSs().getSendFrequency();
				} else {
					s2 = "1";
				}
				ss = new SendSet(mmodelp.getSs().isTiming(), s, mmodelp.getSs().getSendTimes(), mmodelp.getSs().isFixed,
						s2);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			// 如果没有文件
			ss = new SendSet(false, mu.formatDate(new Date(), "yyyyMMddHHmmss"), "1", true, "1");
		}

		// 发送邮件的内容单独存入一个文件
		File f2 = new File("sendmailmessage.txt");
		String msg = "";
		if (msmu.isFileExit(f2)) {
			// 获取默认配置信息
			try {
				msg = msmu.getDefaultMailMsg(f2);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			// 如果没有文件
			msg = "";
		}
		mmodelp.getMm().setMainText(msg);

		File ff = new File("receivemailmodel.properties");
		if (msmu.isFileExit(ff)) {
			// 获取默认配置信息
			try {
				mmodelp2 = msmu.getDefaultMsgSet(ff, 1);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {

		}
	}

	private void init() {
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp1.setLayout(null);
		jp2.setLayout(null);

		jlb1 = new JLabel("SMTP服务：");
		jlb1.setBounds(20, 10, 100, 25);
		jp1.add(jlb1);

		tt1 = new JTextField();
		tt1.setBounds(110, 10, 180, 25);
		jp1.add(tt1);
		// 默认163
		tt1.setText(mmodelp.getMc().getSmtpUrl() != null ? mmodelp.getMc().getSmtpUrl() : "");

		jlb2 = new JLabel("端口：");
		jlb2.setBounds(300, 10, 100, 25);
		jp1.add(jlb2);

		tt2 = new JTextField();
		tt2.setBounds(350, 10, 105, 25);
		jp1.add(tt2);
		// 默认25
		tt2.setText(mmodelp.getMc().getSmtpPort() != null ? mmodelp.getMc().getSmtpPort() : "");

		jlb3 = new JLabel("发   件   人：");
		jlb3.setBounds(20, 50, 100, 25);
		jp1.add(jlb3);

		tt3 = new JTextField();
		tt3.setBounds(110, 50, 180, 25);
		jp1.add(tt3);
		// 默认156
		tt3.setText(mmodelp.getMm().getSendUser() != null ? mmodelp.getMm().getSendUser() : "");
		// 初始值先校验下
		checkMail(0);
		tt3.addFocusListener(new FocusListener() {
			// 光标不在文本域
			@Override
			public void focusLost(FocusEvent e) {
				checkMail(0);
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});

		jlb4 = new JLabel("密码：");
		jlb4.setBounds(300, 50, 100, 25);
		jp1.add(jlb4);

		jpf1 = new JPasswordField();
		jpf1.setBounds(350, 50, 105, 25);
		jp1.add(jpf1);
		// 默认密码
		jpf1.setText(mmodelp.getMm().getSendUserPwd() != null ? mmodelp.getMm().getSendUserPwd() : "");
		jlb5 = new JLabel("收   件   人：");
		jlb5.setBounds(20, 80, 100, 25);
		jp1.add(jlb5);

		tt4 = new JTextField();
		tt4.setBounds(110, 80, 345, 25);
		tt4.setText(mmodelp.getMm().getReciUsersString() != null ? mmodelp.getMm().getReciUsersString() : "");
		jp1.add(tt4);
		// 初始值先校验下
		checkMail(1);
		// 监听光标
		tt4.addFocusListener(new FocusListener() {
			// 光标不在文本域
			@Override
			public void focusLost(FocusEvent e) {
				checkMail(1);
			}

			// 光标在文本域
			@Override
			public void focusGained(FocusEvent e) {
			}
		});

		jlb6 = new JLabel("抄          送：");
		jlb6.setBounds(20, 110, 100, 25);
		jp1.add(jlb6);

		tt5 = new JTextField();
		tt5.setBounds(110, 110, 345, 25);
		tt5.setText(mmodelp.getMm().getCopyToUsersString() != null ? mmodelp.getMm().getCopyToUsersString() : "");
		jp1.add(tt5);
		// 初始值先校验下
		checkMail(2);
		// 监听光标
		tt5.addFocusListener(new FocusListener() {
			// 光标不在文本域
			@Override
			public void focusLost(FocusEvent e) {
				checkMail(2);
			}

			// 光标在文本域
			@Override
			public void focusGained(FocusEvent e) {
			}
		});

		jlb7 = new JLabel("暗   抄   送：");
		jlb7.setBounds(20, 140, 100, 25);
		jp1.add(jlb7);

		tt6 = new JTextField();
		tt6.setBounds(110, 140, 345, 25);
		jp1.add(tt6);
		tt6.setText(
				mmodelp.getMm().getDarkCopyToUsersString() != null ? mmodelp.getMm().getDarkCopyToUsersString() : "");
		// 初始值先校验下
		checkMail(3);
		// 监听光标
		tt6.addFocusListener(new FocusListener() {
			// 光标不在文本域
			@Override
			public void focusLost(FocusEvent e) {
				checkMail(3);
			}

			// 光标在文本域
			@Override
			public void focusGained(FocusEvent e) {
			}
		});

		jlb8 = new JLabel("主          题：");
		jlb8.setBounds(20, 170, 100, 25);
		jp1.add(jlb8);

		tt7 = new JTextField();
		tt7.setBounds(110, 170, 345, 25);
		tt7.setText(mmodelp.getMm().getMailTheme() != null ? mmodelp.getMm().getMailTheme() : "");
		jp1.add(tt7);

		button1 = new JButton("附件");
		button1.setBounds(20, 200, 75, 25);
		button1.addActionListener(this);
		jp1.add(button1);

		button7 = new JButton("清除");
		button7.setBounds(20, 230, 75, 25);
		button7.addActionListener(this);
		jp1.add(button7);
		button7.setEnabled(false);

		tt8 = new JTextField();
		tt8.setBounds(110, 200, 345, 25);
		jp1.add(tt8);
		tt8.setEditable(false);
		// 监听文本框变化
		tt8.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if ("".equals(tt8.getText().trim())) {
					button7.setEnabled(false);
				} else {
					button7.setEnabled(true);
				}
			}

			// 改变是时候
			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});

		jpbar1 = new JProgressBar();
		jpbar1.setBounds(130, 230, 305, 20);
		jpbar1.setStringPainted(true);
		jpbar1.setVisible(false);
		jp1.add(jpbar1);

		jlb9 = new JLabel("正          文：");
		jlb9.setBounds(20, 260, 100, 25);
		jp1.add(jlb9);

		button6 = new JButton("清空");
		button6.setBounds(20, 290, 75, 25);
		button6.addActionListener(this);
		jp1.add(button6);
		button6.setEnabled(false);

		String[] font = { "楷体", "宋体", "仿宋", "黑体" };
		jbb1 = new JComboBox(font);
		jbb1.setBounds(20, 320, 75, 25);
		jbb1.addActionListener(this);
		jp1.add(jbb1);

		String[] textSize = { "11", "5", "10", "15", "20", "25", "30", "35", "40" };
		jbb2 = new JComboBox(textSize);
		jbb2.setBounds(20, 350, 75, 25);
		jbb2.addActionListener(this);
		jp1.add(jbb2);

		jta1 = new JTextArea();
		jta1.setText(mmodelp.getMm().getMainText() != null ? mmodelp.getMm().getMainText() : "");
		jsp1 = new JScrollPane(jta1);
		jsp1.setBounds(110, 260, 345, 270);
		jp1.add(jsp1);

		// 监听文本变化
		jta1.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if ("".equals(jta1.getText().trim())) {
					button6.setEnabled(false);
				} else {
					button6.setEnabled(true);
				}
			}

			// 改变是时候
			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});

		jpbar2 = new JProgressBar();
		jpbar2.setStringPainted(true);
		jpbar2.setBounds(100, 540, 300, 20);
		jpbar2.setMinimum(1);
		jpbar2.setMaximum(100);
		jpbar2.setVisible(false);
		jp1.add(jpbar2);

		jlb10 = new JLabel();
		jlb10.setBounds(410, 538, 20, 25);
		jlb10.setVisible(false);
		jp1.add(jlb10);

		button8 = new JButton("设置");
		button8.setBounds(20, 380, 75, 25);
		button8.addActionListener(this);
		jp1.add(button8);

		button2 = new JButton("发送");
		button2.setBounds(65, 570, 70, 25);
		button2.addActionListener(this);
		jp1.add(button2);

		button3 = new JButton("重置");
		button3.setBounds(165, 570, 70, 25);
		button3.addActionListener(this);
		jp1.add(button3);

		button4 = new JButton("存草稿");
		button4.setBounds(20, 410, 75, 25);
		button4.addActionListener(this);
		jp1.add(button4);

		button14 = new JButton("存模板");
		button14.setBounds(20, 440, 75, 25);
		button14.addActionListener(this);
		jp1.add(button14);

		jlb18 = new JLabel();
		jlb18.setBounds(23, 470, 100, 25);
		jp1.add(jlb18);
		jlb18.setForeground(Color.red);
		jlb18.setVisible(true);

		button13 = new JButton("中止所有");
		button13.setBounds(260, 570, 80, 25);
		button13.addActionListener(this);
		jp1.add(button13);
		button13.setEnabled(false);

		button5 = new JButton("关闭");
		button5.setBounds(365, 570, 70, 25);
		button5.addActionListener(this);
		jp1.add(button5);

		// jp2
		jlb11 = new JLabel("POP服务：");
		jlb11.setBounds(20, 10, 100, 25);
		jp2.add(jlb11);

		tt10 = new JTextField();
		tt10.setBounds(100, 10, 190, 25);
		jp2.add(tt10);
		// 默认qq
		tt10.setText(mmodelp2.getMc().getSmtpUrl() != null ? mmodelp2.getMc().getSmtpUrl() : "");

		jlb12 = new JLabel("端口：");
		jlb12.setBounds(300, 10, 100, 25);
		jp2.add(jlb12);

		tt11 = new JTextField();
		tt11.setBounds(350, 10, 105, 25);
		jp2.add(tt11);
		// 默认995
		tt11.setText(mmodelp2.getMc().getSmtpPort() != null ? mmodelp2.getMc().getSmtpPort() : "");

		jlb13 = new JLabel("收  件  人：");
		jlb13.setBounds(20, 50, 100, 25);
		jp2.add(jlb13);

		tt12 = new JTextField();
		tt12.setBounds(100, 50, 190, 25);
		tt12.setText(mmodelp2.getMm2().getSendUser() != null ? mmodelp2.getMm2().getSendUser() : "");
		jp2.add(tt12);
		// 初始值先校验下
		checkMail(4);
		tt12.addFocusListener(new FocusListener() {
			// 光标不在文本域
			@Override
			public void focusLost(FocusEvent e) {
				checkMail(4);
			}

			@Override
			public void focusGained(FocusEvent e) {

			}
		});

		jlb14 = new JLabel("密码：");
		jlb14.setBounds(300, 50, 100, 25);
		jp2.add(jlb14);

		jpf2 = new JPasswordField();
		jpf2.setBounds(350, 50, 105, 25);
		jp2.add(jpf2);
		// 默认密码
		jpf2.setText(mmodelp2.getMm2().getSendUserPwd() != null ? mmodelp2.getMm2().getSendUserPwd() : "");

		// 选择显示数量
		jlb15 = new JLabel("接收范围：");
		jlb15.setBounds(20, 90, 100, 25);
		jp2.add(jlb15);

		jrb1 = new JRadioButton("全部");
		jrb2 = new JRadioButton("部分");
		jrb1.setSelected(mmodelp2.getMm2().isAllRece());
		jrb2.setSelected(!mmodelp2.getMm2().isAllRece());

		jrb1.setBounds(15, 130, 70, 25);
		jrb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jrb1.isSelected()) {
					tt13.setEnabled(false);
					tt14.setEnabled(false);
					tt13.setText("");
					tt14.setText("");
				} else {
					tt13.setEnabled(true);
					tt14.setEnabled(true);
				}
			}
		});

		jrb2.setBounds(15, 170, 70, 25);
		jrb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jrb2.isSelected()) {
					tt13.setEnabled(true);
					tt14.setEnabled(true);
				} else {
					tt13.setEnabled(false);
					tt14.setEnabled(false);
					tt13.setText("");
					tt14.setText("");
				}
			}
		});
		bg1 = new ButtonGroup();
		bg1.add(jrb1);
		bg1.add(jrb2);

		jp2.add(jrb1);
		jp2.add(jrb2);

		// 选择第几封到第几封
		tt13 = new JTextField();
		tt13.setBounds(20, 210, 30, 25);
		tt13.setText(mmodelp2.getMm2().getFromNum() != null ? mmodelp2.getMm2().getFromNum() : "");
		jp2.add(tt13);

		// -
		jlb16 = new JLabel("-");
		jlb16.setBounds(52, 210, 8, 25);
		jp2.add(jlb16);

		tt14 = new JTextField();
		tt14.setBounds(60, 210, 30, 25);
		jp2.add(tt14);
		tt14.setText(mmodelp2.getMm2().getLastNum() != null ? mmodelp2.getMm2().getLastNum() : "");
		if (mmodelp2.getMm2().isAllRece()) {
			tt13.setEnabled(false);
			tt14.setEnabled(false);
			tt13.setText("");
			tt14.setText("");
		} else {
			tt13.setEnabled(true);
			tt14.setEnabled(true);
		}

		button15 = new JButton("存模板");
		button15.setBounds(20, 250, 70, 25);
		button15.addActionListener(this);
		jp2.add(button15);

		// 邮件列表
		jl1 = new JList(mailData);
		this.jl1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 双击显示邮件
				if (e.getClickCount() == 2) {
					// 获取下标
					int num = jl1.getSelectedIndex();
					if (MailReciveController.message != null && MailReciveController.message.length > 0) {
						int length = MailReciveController.message.length;
						// 显示面板
						try {
							ShowMail sm = new ShowMail(MailReciveController.message[length - 1 - num]);
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "打开邮件失败！", "提示消息", JOptionPane.WARNING_MESSAGE);
						}
					}

				}
			}
		});

		// mailData.addElement("zhongzh");
		// 默认选中
		jl1.setSelectedIndex(0);
		jsp2 = new JScrollPane(jl1);
		jsp2.setBorder(new TitledBorder("Email列表"));
		jsp2.setBounds(100, 90, 355, 440);
		jp2.add(jsp2);

		jpbar3 = new JProgressBar();
		jpbar3.setStringPainted(true);
		jpbar3.setBounds(100, 540, 300, 20);
		jpbar3.setMinimum(1);
		jpbar3.setMaximum(100);
		jpbar3.setVisible(false);
		jp2.add(jpbar3);

		jlb17 = new JLabel();
		jlb17.setBounds(405, 538, 40, 25);
		jp2.add(jlb17);
		jlb17.setVisible(false);

		button9 = new JButton("接收");
		button9.setBounds(65, 570, 70, 25);
		button9.addActionListener(this);
		jp2.add(button9);

		button10 = new JButton("重置");
		button10.setBounds(165, 570, 70, 25);
		button10.addActionListener(this);
		jp2.add(button10);

		button11 = new JButton("中止");
		button11.setBounds(265, 570, 70, 25);
		button11.addActionListener(this);
		button11.setEnabled(false);
		jp2.add(button11);

		button12 = new JButton("关闭");
		button12.setBounds(365, 570, 70, 25);
		button12.addActionListener(this);
		jp2.add(button12);

		this.add(jtp);
		jtp.add("发送邮件", jp1);
		jtp.add("接收邮件", jp2);

		this.setTitle("邮箱");
		this.setSize(500, 700);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon i = new ImageIcon(getClass().getResource("mail.png"));
		this.setIconImage(i.getImage());
	}

	protected void checkMail(int i) {
		if (i == 0) {
			sendUser = tt3.getText().trim();
			if (!"".equals(sendUser)) {
				// 校验邮箱
				// 发件人邮箱
				String[] s = new String[1];
				s[0] = sendUser;
				if (!mu.checkMailFormat(s)) {
					sendUserIsOk = false;
					JOptionPane.showMessageDialog(null, "发件人邮箱格式不正确！请检查！", "提示消息", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					sendUserIsOk = true;
				}
			}

		} else if (i == 1) {
			// 收件人
			accUser = tt4.getText();
			if (!"".equals(accUser) && !accUser.endsWith(";")) {
				accUser = accUser + ";";
			} else {

			}

			// 验证邮箱格式
			if (!"".equals(accUser)) {
				accUsers = accUser.split(";");
				if (!mu.checkMailFormat(accUsers)) {
					accUserIsOk = false;
					JOptionPane.showMessageDialog(null, "收件人邮箱格式不正确！请检查！", "提示消息", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					accUserIsOk = true;
				}
			} else {

			}
			tt4.setText(accUser);

		} else if (i == 2) {
			// 抄送
			copyToUser = tt5.getText();
			if (!"".equals(copyToUser) && !copyToUser.endsWith(";")) {
				copyToUser = copyToUser + ";";
			} else {

			}

			// 验证邮箱格式
			if (!"".equals(copyToUser)) {
				copyToUsers = copyToUser.split(";");
				if (!mu.checkMailFormat(copyToUsers)) {
					copyToUserIsOk = false;
					JOptionPane.showMessageDialog(null, "抄送邮箱格式不正确！请检查！", "提示消息", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					copyToUserIsOk = true;
				}
			} else {

			}
			tt5.setText(copyToUser);

		} else if (i == 3) {
			// 暗抄送
			darkCopyToUser = tt6.getText();
			if (!"".equals(darkCopyToUser) && !darkCopyToUser.endsWith(";")) {
				darkCopyToUser = darkCopyToUser + ";";
			} else {

			}

			// 验证邮箱格式
			if (!"".equals(darkCopyToUser)) {
				darkCopyToUsers = darkCopyToUser.split(";");
				if (!mu.checkMailFormat(darkCopyToUsers)) {
					darkCopyToUserIsOk = false;
					JOptionPane.showMessageDialog(null, "暗抄送邮箱格式不正确！请检查！", "提示消息", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					darkCopyToUserIsOk = true;
				}
			} else {

			}
			tt6.setText(darkCopyToUser);

		} else if (i == 4) {
			// 收件人
			sendUser2 = tt12.getText().trim();
			if (!"".equals(sendUser2)) {
				String[] s = new String[1];
				s[0] = sendUser2;
				if (!mu.checkMailFormat(s)) {
					sendUserIsOk2 = false;
					JOptionPane.showMessageDialog(null, "收件人邮箱格式不正确！请检查！", "提示消息", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					sendUserIsOk2 = true;
				}
			}
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object key = e.getSource();
		// 监听添加附件
		if (key.equals(button1)) {
			// 打开文件选择器
			chooseFile1 = new JFileChooser();
			// 多选
			chooseFile1.setMultiSelectionEnabled(true);
			// 只能选择文件
			chooseFile1.setFileSelectionMode(0);
			int returnVal = chooseFile1.showOpenDialog(this);
			if (returnVal == chooseFile1.APPROVE_OPTION) {
				// 获取所有选中的文件
				fs = chooseFile1.getSelectedFiles();
				// 检查文件的大小，不能超过50兆
				if (!mu.checkFileSize(fs)) {
					JOptionPane.showMessageDialog(null, "单个文件不能超过50M！请检查！", "提示消息", JOptionPane.WARNING_MESSAGE);
					return;
				}
				// 获取文件的所有名字,及地址
				filePathName = mu.getFilePathName(fs);
				// 加入list(包含所有添加的文件)
				fileList.add(filePathName);
				// 加入path
				mu.getFilePathName(filePathList, fs);
				// 界面显示以分号相间
				String ShowFileName = mu.getFileNameFormat(filePathName);
				tt8.setText(tt8.getText() + ShowFileName);
			}
		}
		// 监听删除附件
		if (key.equals(button7)) {
			// 清除list集合，显示清零
			fileList.clear();
			filePathList.clear();
			tt8.setText("");
			button7.setEnabled(false);
		}

		// 监听发送或保存
		if (key.equals(button2) || key.equals(button4)) {

			// 0是发送，1是保存
			// 获取url参数
			mc = getMC();
			// 获取主体信息
			mm = getMM();
			// 校验参数
			int f = checkParams(mc, mm);
			if (f == 0) {
				// 如果是保存则打开弹窗以便于获取保存路径
				// 设置进度条为不确定模式,默认为确定模式

				if (key.equals(button4)) {
					JFileChooser jfc = new JFileChooser();
					jfc.setDialogTitle("保存");
					jfc.setFileSelectionMode(1);
					int flag = jfc.showSaveDialog(this);
					if (flag == JFileChooser.APPROVE_OPTION) {
						File file = jfc.getSelectedFile();
						path = file.getPath();
						// 写入文件
						// dispose();
					} else {
						return;
					}
				} else {
					button13.setEnabled(true);
					jpbar2.setVisible(true);
					jpbar2.setValue(0);
					jpbar2.setString("正在发送，请耐心等待...");
					// start times
					MailSendController.sendTimes = 0;
					MailMainGui.isRunning = true;
					thread = new CountThread();
					thread.start();
				}

				// 参数合格，把邮箱字符串转换为list集合
				mm = arrayToList(mm);
				// 一定要新建对象
				MailModel mmodel = new MailModel();
				// 导入所有参数
				mmodel.setMc(mc);
				mmodel.setMm(mm);
				mmodel.setSs(ss);
				sw1 = new SwingWorker<String, String>() {
					int fb = 0;

					// 此方法是耗时任务
					@Override
					protected String doInBackground() throws Exception {
						String f = null;
						if (key.equals(button2)) {
							fb = 0;
							f = msc.mailSend(mmodel, fb, null);

						} else if (key.equals(button4)) {
							fb = 1;
							f = msc.mailSend(mmodel, fb, path);
						}
						return f;
					}

					// done
					protected void done() {
						// MailMainGui.isRunning = false;
						String f = null;
						try {
							f = get();
						} catch (Exception e) {
							e.printStackTrace();
						}
						String msg = "创建任务";
						if (fb == 0) {
							msg = "创建任务";
							// thread.stop();
						} else if (fb == 1) {
							msg = "保存";
						}
						// button13.setEnabled(false);
						if (f == null || "1".equals(f)) {
							if (fb == 0) {
								jpbar2.setString("创建任务失败！");
							}
							JOptionPane.showMessageDialog(null, msg + "失败！", "提示消息", JOptionPane.WARNING_MESSAGE);
						} else if ("0".equals(f)) {
							JOptionPane.showMessageDialog(null, msg + "成功！", "提示消息", JOptionPane.WARNING_MESSAGE);

						}
					}
				};
				sw1.execute();

			} else if (f == 1) {
				JOptionPane.showMessageDialog(null, "参数不能为空！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 2) {
				JOptionPane.showMessageDialog(null, "端口号格式不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 3) {
				JOptionPane.showMessageDialog(null, "邮箱格式不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}

		}

		// 监听接收
		if (key.equals(button9)) {
			mc2 = getMC2();
			// 获取主体信息
			mm2 = getMM2();
			// 校验参数
			int f = checkParams2(mc2, mm2);
			if (f == 0) {
				MailModel mmodel2 = new MailModel();
				// 导入所有参数
				mmodel2.setMc(mc2);
				mmodel2.setMm2(mm2);
				MailMainGui.isRunning2 = true;
				button11.setEnabled(true);
				thread2 = new CountThread2();
				thread2.start();
				jpbar3.setVisible(true);
				jpbar3.setValue(0);
				jpbar3.setString("正在接收邮件，请耐心等待...");
				sw2 = new SwingWorker<String, String>() {
					// 此方法是耗时任务
					@Override
					protected String doInBackground() throws Exception {
						String f = null;
						f = mrc.receiveMail(mmodel2);
						return f;
					}

					// done
					protected void done() {
						MailMainGui.isRunning2 = false;
						String f = null;
						try {
							f = get();
						} catch (Exception e) {
							e.printStackTrace();
						}
						thread2.stop();
						button11.setEnabled(false);
						if (f == null || "1".equals(f)) {
							jpbar3.setString("接收失败！");
							JOptionPane.showMessageDialog(null, "接收失败！", "提示消息", JOptionPane.WARNING_MESSAGE);
						} else if ("0".equals(f)) {
							jpbar3.setString("接收成功！");
							JOptionPane.showMessageDialog(null, "接收成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
						} else if ("2".equals(f)) {
							jpbar3.setString("无邮件！");
							JOptionPane.showMessageDialog(null, "无邮件！", "提示消息", JOptionPane.WARNING_MESSAGE);
						} else if ("3".equals(f)) {
							jpbar3.setString("接收中止！");
							JOptionPane.showMessageDialog(null, "接收中止！", "提示消息", JOptionPane.WARNING_MESSAGE);
						}
					}
				};
				sw2.execute();

			} else if (f == 1) {
				JOptionPane.showMessageDialog(null, "参数不能为空！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 2) {
				JOptionPane.showMessageDialog(null, "端口号格式不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 3) {
				JOptionPane.showMessageDialog(null, "邮箱格式不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 4) {
				JOptionPane.showMessageDialog(null, "范围参数不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}

		}

		// 监听中止接收
		if (key.equals(button11)) {
			MailMainGui.isRunning2 = false;
			// thread2.stop();
		}

		// 监听中止发送
		if (key.equals(button13)) {
			MailMainGui.isRunning = false;
			// 由于后台存在延时发送，所以先禁用按钮
			// 调用后台方法
			if (msc.stopThread()) {
				button13.setEnabled(false);
				jpbar2.setString("所有发送中止！");
				JOptionPane.showMessageDialog(null, "所有发送中止！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else {
				jpbar2.setString("中止失败！");
				JOptionPane.showMessageDialog(null, "中止失败！请直接退出程序！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
			// thread.stop();
		}

		// 监听重置
		if (key.equals(button3)) {
			tt1.setText("");
			tt2.setText("");
			tt3.setText("");
			tt4.setText("");
			tt5.setText("");
			tt6.setText("");
			tt7.setText("");
			tt8.setText("");
			jpf1.setText("");
			jta1.setText("");
			jlb10.setText("");
			jlb18.setText("");
			jpbar2.setVisible(false);
			jlb10.setVisible(false);
			button6.setEnabled(false);
			button7.setEnabled(false);
			button13.setEnabled(false);
			ss = new SendSet(false, mu.formatDate(new Date(), "yyyyMMddHHmmss"), "1", true, "1");
		}
		// 监听重置2
		if (key.equals(button10)) {
			tt10.setText("");
			tt11.setText("");
			tt12.setText("");
			tt13.setText("");
			tt14.setText("");
			jpf2.setText("");
			jpbar3.setVisible(false);
			jrb1.setSelected(true);
			button11.setEnabled(false);
			mailData.clear();
		}

		// 监听关闭
		if (key.equals(button5)) {
			if (MailMainGui.isRunning) {
				// 自定义弹窗,返回按钮的下标
				String[] option = { "确定", "取消" };
				int index = JOptionPane.showOptionDialog(this, "邮件正在发送，是否退出系统？", "提示消息", JOptionPane.YES_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
				if (index == 0) {
					System.exit(0);
				}
			} else {
				System.exit(0);
			}
		}

		// 监听关闭接收
		if (key.equals(button12)) {
			if (MailMainGui.isRunning2) {
				// 自定义弹窗,返回按钮的下标
				String[] option = { "确定", "取消" };
				int index = JOptionPane.showOptionDialog(this, "正在接收邮件，是否退出系统？", "提示消息", JOptionPane.YES_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, option, option[0]);
				if (index == 0) {
					System.exit(0);
				}
			} else {
				System.exit(0);
			}
		}

		// 监听发送设置
		if (key.equals(button8)) {
			SendSetDialog ssd = new SendSetDialog(ss);
		}

		// 清空正文
		if (key.equals(button6)) {
			jta1.setText("");
			button6.setEnabled(false);
		}

		// 监听保存发送设置模板
		if (key.equals(button14)) {
			// 获取url参数
			MailConfig mcc = getMC();
			// 获取主体信息
			MailMessage mmm = getMM();
			// 校验参数
			int f = checkParams(mcc, mmm);
			if (f == 0) {
				// 参数合格，把邮箱字符串转换为list集合
				// mmm = arrayToList(mmm);
				MailModel mmdo = new MailModel();
				// 导入所有参数
				mmdo.setMc(mcc);
				mmdo.setMm(mmm);
				mmdo.setSs(ss);
				// 存入配置文件
				try {
					if (msmu.BaseMsgToPro(mmdo, 0)) {
						JOptionPane.showMessageDialog(null, "保存模板成功！重启生效！", "提示消息", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "保存模板失败！请重试！", "提示消息", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "保存模板失败！请重试！", "提示消息", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}
			} else if (f == 1) {
				JOptionPane.showMessageDialog(null, "参数不能为空！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 2) {
				JOptionPane.showMessageDialog(null, "端口号格式不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 3) {
				JOptionPane.showMessageDialog(null, "邮箱格式不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
		}

		// 监听保存接收设置模板
		if (key.equals(button15)) {
			MailConfig mcc2 = getMC2();
			// 获取主体信息
			MailMessage2 mmm2 = getMM2();
			// 校验参数
			int f = checkParams2(mcc2, mmm2);
			if (f == 0) {
				MailModel mmdo2 = new MailModel();
				// 导入所有参数
				mmdo2.setMc(mcc2);
				mmdo2.setMm2(mmm2);
				// 存入配置文件
				try {
					if (msmu.BaseMsgToPro(mmdo2, 1)) {
						JOptionPane.showMessageDialog(null, "保存模板成功！重启生效！", "提示消息", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "保存模板失败！请重试！", "提示消息", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "保存模板失败！请重试！", "提示消息", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}

			} else if (f == 1) {
				JOptionPane.showMessageDialog(null, "参数不能为空！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 2) {
				JOptionPane.showMessageDialog(null, "端口号格式不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 3) {
				JOptionPane.showMessageDialog(null, "邮箱格式不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 4) {
				JOptionPane.showMessageDialog(null, "范围参数不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
		}

	}

	// arraytolist
	private MailMessage arrayToList(MailMessage mm2) {
		mm2.setReciUsers(Arrays.asList(mm2.getReciUsersString().split(";")));
		if (!"".equals(mm2.getCopyToUsersString())) {
			mm2.setCopyToUsers(Arrays.asList(mm2.getCopyToUsersString().split(";")));
		}
		if (!"".equals(mm2.getDarkCopyToUsersString())) {
			mm2.setDarkCopyToUsers(Arrays.asList(mm2.getDarkCopyToUsersString().split(";")));
		}
		return mm2;
	}

	private int checkParams(MailConfig mc2, MailMessage mm2) {
		// 相关不能为空
		if ("".equals(mc2.getSmtpUrl()) || "".equals(mc2.getSmtpPort()) || "".equals(mm2.getSendUser())
				|| "".equals(mm2.getSendUserPwd()) || "".equals(mm2.getReciUsersString())) {
			return 1;
		}

		// 校验端口号
		if (!mc2.getSmtpPort().matches("^[0-9]*$")) {
			return 2;
		}

		// 校验邮箱
		if (!sendUserIsOk || !accUserIsOk) {
			return 3;
		}
		if (!"".equals(mm2.getCopyToUsersString())) {
			if (!copyToUserIsOk) {
				return 3;
			}
		}
		if (!"".equals(mm2.getDarkCopyToUsersString())) {
			if (!darkCopyToUserIsOk) {
				return 3;
			}
		}
		return 0;
	}

	private int checkParams2(MailConfig mc2, MailMessage2 mm2) {
		// 相关不能为空
		if ("".equals(mc2.getSmtpUrl()) || "".equals(mc2.getSmtpPort()) || "".equals(mm2.getSendUser())
				|| "".equals(mm2.getSendUserPwd())) {
			return 1;
		}

		// 校验端口号
		if (!mc2.getSmtpPort().matches("^[0-9]*$")) {
			return 2;
		}

		// 校验邮箱
		if (!sendUserIsOk2) {
			return 3;
		}

		if (!mm2.isAllRece) {
			// 填写一个即可
			if ("".equals(mm2.getFromNum()) && "".equals(mm2.getLastNum())) {
				return 1;
			}
			// 如果都填了
			if (!"".equals(mm2.getFromNum()) && !"".equals(mm2.getLastNum())) {
				// 不是数字
				if (!checkNum(mm2.getFromNum()) || !checkNum(mm2.getLastNum())) {
					return 4;
				} else {
					// 大小不对
					if (Integer.valueOf(mm2.getFromNum()) > Integer.valueOf(mm2.getLastNum())) {
						return 4;
					}
					if (Integer.valueOf(mm2.getFromNum()) == 0) {
						mm2.setFromNum("1");
					}
					if (Integer.valueOf(mm2.getLastNum()) == 0) {
						return 4;
					}
				}
			}

			// 如果只填起始值
			if (!"".equals(mm2.getFromNum()) && "".equals(mm2.getLastNum())) {
				// 不是数字
				if (!checkNum(mm2.getFromNum())) {
					return 4;
				} else {
					mm2.setLastNum("9999999");
				}
				if (Integer.valueOf(mm2.getFromNum()) == 0) {
					mm2.setFromNum("1");
				}
			}

			// 只填结束值
			if ("".equals(mm2.getFromNum()) && !"".equals(mm2.getLastNum())) {
				// 不是数字
				if (!checkNum(mm2.getLastNum())) {
					return 4;
				} else {
					mm2.setFromNum("1");
				}
			}
		} else {
			mm2.setFromNum("1");
			mm2.setLastNum("9999999");
		}
		return 0;
	}

	public boolean checkNum(String num) {
		if (num != null && !"".equals(num)) {
			if (num.matches("^[0-9]*$") && num.length() < 8) {
				return true;
			}
		}
		return false;
	}

	// 获取主体信息
	private MailMessage getMM() {
		MailMessage mm = new MailMessage();
		mm.setSendUser(tt3.getText().trim());
		mm.setSendUserPwd(jpf1.getText().trim());
		mm.setReciUsersString(tt4.getText().trim());
		mm.setCopyToUsersString(tt5.getText().trim());
		mm.setDarkCopyToUsersString(tt6.getText().trim());
		mm.setMailTheme(tt7.getText().trim());
		mm.setMainText(jta1.getText().trim());
		mm.setAttachs(filePathList);
		return mm;
	}

	// 获取主体信息
	private MailMessage2 getMM2() {
		MailMessage2 mm = new MailMessage2();
		mm.setSendUser(tt12.getText().trim());
		mm.setSendUserPwd(jpf2.getText().trim());
		// 是否全部显示
		mm.setAllRece(jrb1.isSelected());
		mm.setFromNum(tt13.getText().trim());
		mm.setLastNum(tt14.getText().trim());
		return mm;
	}

	// 获取url
	private MailConfig getMC() {
		MailConfig mc = new MailConfig();
		mc.setSmtpUrl(tt1.getText().trim());
		mc.setSmtpPort(tt2.getText().trim());
		return mc;
	}

	// 获取url
	private MailConfig getMC2() {
		MailConfig mc = new MailConfig();
		mc.setSmtpUrl(tt10.getText().trim());
		mc.setSmtpPort(tt11.getText().trim());
		return mc;
	}

	// 计时线程
	private class CountThread extends Thread {
		private CountThread() {
			setDaemon(true);
		}

		@Override
		public void run() {
			jlb10.setVisible(true);
			while (MailMainGui.isRunning) {
				jlb10.setText(String.valueOf(MailSendController.sendTimes));
				jpbar2.setValue(100 * MailSendController.sendTimes / Integer.valueOf(ss.getSendTimes()));
				if (jpbar2.getValue() == 100) {
					jpbar2.setString("发送成功！");
					return;
				}
				if (MailSendController.tl.size() == 0) {
					button13.setEnabled(false);
				}
			}
		}
	}

	// 计时线程
	private class CountThread2 extends Thread {
		private CountThread2() {
			setDaemon(true);
		}

		@Override
		public void run() {
			jlb17.setVisible(true);
			while (MailMainGui.isRunning2) {
				jlb17.setText(String.valueOf(MailReciveController.receTimes));
				jpbar3.setValue(100 * MailReciveController.receTimes / MailReciveController.receMailNum);
			}
		}

	}
}
