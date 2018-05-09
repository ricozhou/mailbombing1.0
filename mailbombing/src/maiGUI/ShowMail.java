package maiGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileSystemView;

import maiGUI.MailMainGui;
import mailrecive.MailReciveMsgUtits;

public class ShowMail extends JFrame implements ActionListener {
	public JPanel jp0, jp1, jp2, jp11, jp12, jp13, jp14, jp15, jp16, jp17;
	public JLabel jlb1, jlb2, jlb3, jlb4, jlb5, jlb6, jlb7, jlb8, jlb9, jlb10;
	public JButton button1, button2, button3, button4, button5, button6, button7;
	public JTextArea jta1, jta2;
	public JTextField tt1, tt2, tt3, tt4;
	public JFileChooser jfc1, jfc2, jfc3, jfc4;
	public JRadioButton jrb1, jrb2, jrb3;
	public Message message;
	public MailReciveMsgUtits mrmu;
	public JScrollPane jsp1, jsp2;
	public JEditorPane jep1;
	public JInternalFrame jif1;
	// 附件名字
	public String aName;
	public SwingWorker<String, String> sw1;
	public JFrame jf1;
	public String path;
	public JProgressBar jpbProcessLoading1;

	// 构造方法
	public ShowMail(Message message) throws Exception {
		this.jf1 = this;
		this.message = message;
		mrmu = new MailReciveMsgUtits((MimeMessage) message);
		init();
	}

	// 初始化窗口
	private void init() throws Exception {
		// 整体边界布局
		this.setLayout(new BorderLayout(1, 1));

		// this.setLayout(new BorderLayout(1, 1));
		jp0 = new JPanel();
		jp0.setLayout(new BorderLayout(2, 1));
		this.add(jp0, BorderLayout.CENTER);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp11 = new JPanel();
		jp12 = new JPanel();
		jp13 = new JPanel();
		jp14 = new JPanel();
		jp15 = new JPanel();
		jp16 = new JPanel();
		jp17 = new JPanel();
		jp1.setLayout(new GridLayout(7, 1, 1, 1));
		jsp1 = new JScrollPane(jp0);
		this.add(jsp1);
		jp0.add(jp1, BorderLayout.NORTH);
		jp0.add(jp2, BorderLayout.CENTER);

		jp1.add(jp11);
		jp1.add(jp12);
		jp1.add(jp13);
		jp1.add(jp14);
		jp1.add(jp15);
		jp1.add(jp16);
		jp1.add(jp17);

		jp11.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		jp12.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		jp13.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		jp14.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		jp15.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		jp16.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		// jp2.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));
		jp2.setLayout(new BorderLayout(1, 1));
		jp17.setLayout(new FlowLayout(FlowLayout.RIGHT, 1, 1));

		jlb1 = new JLabel(" 主题：" + mrmu.getMailSubject());
		jp11.add(jlb1);
		jlb1.setFont(new Font("楷体", Font.BOLD, 30));
		jlb1.setForeground(Color.RED);

		jlb3 = new JLabel("  发件人：" + mrmu.getMailFrom());
		jp12.add(jlb3);
		jlb3.setFont(new Font("楷体", Font.BOLD, 20));
		jlb3.setForeground(Color.BLACK);

		jlb4 = new JLabel("  收件人：" + mrmu.getMailAddress("TO"));
		jp13.add(jlb4);
		jlb4.setFont(new Font("楷体", Font.BOLD, 20));
		jlb4.setForeground(Color.BLUE);

		jlb5 = new JLabel("  抄  送：" + mrmu.getMailAddress("CC"));
		jp14.add(jlb5);
		jlb5.setFont(new Font("楷体", Font.BOLD, 20));
		jlb5.setForeground(Color.BLUE);

		jlb6 = new JLabel("  暗抄送：" + mrmu.getMailAddress("BCC"));
		jp15.add(jlb6);
		jlb6.setFont(new Font("楷体", Font.BOLD, 20));
		jlb6.setForeground(Color.BLUE);

		// 是否包含附件
		boolean iscluf = mrmu.isMailContainAttach((Part) message);
		aName = "";
		if (iscluf) {
			mrmu.saveMailAttachMent((Part) message, 0, null);
			aName = mrmu.getSbAname();
		}
		jlb7 = new JLabel("  附  件：" + aName);
		jp16.add(jlb7);
		jlb7.setFont(new Font("楷体", Font.BOLD, 20));
		jlb7.setForeground(Color.BLUE);

		if (iscluf) {
			// 出现下载按钮
			button1 = new JButton("下载");
			button1.addActionListener(this);
			jp16.add(button1);
			jpbProcessLoading1 = new JProgressBar();
			jpbProcessLoading1.setStringPainted(true); // 呈现字符串
			jpbProcessLoading1.setVisible(false);
			jp16.add(jpbProcessLoading1);
		}

		jlb8 = new JLabel(mrmu.getMailSentDate() + "  ");
		jp17.add(jlb8);

		jep1 = new JEditorPane();
		jp2.add(jep1, BorderLayout.CENTER);

		// 主体msg
		Map maps = mrmu.getMailContent((Part) message);
		// 遍历显示
		showMsg(maps, jep1);

		this.setTitle("Mail");
		this.setSize(850, 800);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("mail.png"));
		this.setIconImage(imageIcon.getImage());
	}

	// 遍历显示
	private void showMsg(Map maps, JEditorPane jep12) {
		// 此处处理的不好
		StringBuilder consb = new StringBuilder();
		// 迭代器
		Iterator it = maps.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entity = (Entry) it.next();
			if ("0".equals(entity.getKey())) {
				// // 如果是纯文本
				// consb.append(entity.getValue());
				jep12.setContentType("text/plain");
				jep12.setText(entity.getValue().toString());
			} else if ("1".equals(entity.getKey())) {
				consb.append(entity.getValue().toString());
			}
		}

		if ("".equals(jep12.getText())) {
			jep12.setContentType("text/html");
			jep12.setText(consb.toString());
		}

		if ("".equals(jep12.getText().replaceAll(" ", ""))
				|| "<html><head></head><body><pstyle=\"margin-top:0\"></p></body></html>"
						.equals(jep12.getText().replaceAll(" ", "").replaceAll("\r\n", ""))) {
			// 此处处理的不好
			StringBuilder consb2 = new StringBuilder();
			// 迭代器
			Iterator it2 = maps.entrySet().iterator();
			while (it2.hasNext()) {
				Map.Entry entity = (Entry) it2.next();
				consb2.append(entity.getValue().toString());
			}
			jep12.setContentType("text/plain");
			jep12.setText(consb2.toString());
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object key = e.getSource();
		// 监听下载
		if (key.equals(button1)) {
			// 先弹出选择文件框
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("下载");
			jfc.setFileSelectionMode(1);
			int flag = jfc.showSaveDialog(jf1);
			if (flag == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				path = file.getPath();
			} else {
				return;
			}

			jpbProcessLoading1.setVisible(true);
			jpbProcessLoading1.setIndeterminate(true); // 设置进度条为不确定模式,默认为确定模式
			jpbProcessLoading1.setString("正在下载，请耐心等待...");

			// 下载附件需要时间，需要线程，并弹出进度条
			sw1 = new SwingWorker<String, String>() {
				// 此方法是耗时任务
				@Override
				protected String doInBackground() throws Exception {
					String f = "0";
					// 下载附件
					try {
						mrmu.saveMailAttachMent((Part) message, 1, path);
					} catch (Exception e1) {
						e1.printStackTrace();
						f = "1";
					}
					return f;
				}

				protected void done() {
					String f = null;
					try {
						f = get();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (f == null || "1".equals(f)) {
						jpbProcessLoading1.setIndeterminate(false);
						jpbProcessLoading1.setString("下载失败！");
					} else if ("0".equals(f)) {
						jpbProcessLoading1.setIndeterminate(false);
						jpbProcessLoading1.setString("下载完成！");
						JOptionPane.showMessageDialog(null, "下载完成！", "提示消息", JOptionPane.WARNING_MESSAGE);
					}

				}
			};
			sw1.execute();

		}
	}

}
