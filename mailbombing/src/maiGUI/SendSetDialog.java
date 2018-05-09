package maiGUI;

import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

//自定义弹窗
public class SendSetDialog extends JDialog implements ActionListener {
	public JTextField jtf1, jtf2, jtf3;
	public JButton button1, button2;
	public JLabel jlp1, jlp2, jlp3, jlp4, jlp5, jlp6, jlp7, jlp8, jlp9;
	public JRadioButton jrb1, jrb2, jrb3, jrb4;
	public ButtonGroup bg1, bg2;
	public MailUtils mu = new MailUtils();
	public SendSet ss;

	public SendSetDialog(SendSet ss) {
		this.ss = ss;
		init();
	}

	private void init() {
		setLayout(null);
		jlp1 = new JLabel("是否定时发送：");
		jlp1.setBounds(20, 10, 100, 25);

		jlp4 = new JLabel("输入时间：");
		jlp4.setBounds(110, 40, 100, 25);

		jtf1 = new JTextField();
		jtf1.setBounds(180, 40, 180, 25);
		jtf1.setText(ss.getSendDate() != null ? ss.getSendDate() : mu.formatDate(new Date(), "yyyyMMddHHmmss"));
		if (ss.isTiming()) {
			jlp4.setVisible(true);
			jtf1.setVisible(true);
		} else {
			jlp4.setVisible(false);
			jtf1.setVisible(false);
		}

		jrb1 = new JRadioButton("立即发送");
		jrb2 = new JRadioButton("定时发送");
		jrb1.setSelected(!ss.isTiming());
		jrb2.setSelected(ss.isTiming());

		jrb1.setBounds(120, 10, 100, 25);
		jrb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jrb1.isSelected()) {
					jlp4.setVisible(false);
					jtf1.setVisible(false);
				} else {
					jlp4.setVisible(true);
					jtf1.setVisible(true);
				}
			}
		});

		jrb2.setBounds(250, 10, 100, 25);
		jrb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jrb2.isSelected()) {
					jlp4.setVisible(true);
					jtf1.setVisible(true);
				} else {
					jlp4.setVisible(false);
					jtf1.setVisible(false);
				}
			}
		});
		bg1 = new ButtonGroup();
		bg1.add(jrb1);
		bg1.add(jrb2);

		jlp2 = new JLabel("是否固定频次：");
		jlp2.setBounds(20, 70, 100, 25);

		jlp5 = new JLabel("输入频次：");
		jlp5.setBounds(130, 100, 100, 25);
		jlp5.setVisible(true);

		jtf2 = new JTextField();
		jtf2.setBounds(200, 100, 80, 25);
		jtf2.setText(ss.getSendFrequency());
		jtf2.setVisible(true);

		jlp6 = new JLabel("秒/次");
		jlp6.setBounds(290, 100, 100, 25);
		jlp6.setVisible(true);

		jlp7 = new JLabel("1~180（秒/次）随机发送");
		jlp7.setBounds(160, 100, 250, 25);
		jlp7.setVisible(false);

		jrb3 = new JRadioButton("固定频次");
		jrb4 = new JRadioButton("不固定频次");
		jrb3.setSelected(ss.isFixed);
		jrb4.setSelected(!ss.isFixed);

		jrb3.setBounds(120, 70, 100, 25);
		jrb3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jrb3.isSelected()) {
					jlp5.setVisible(true);
					jtf2.setVisible(true);
					jlp6.setVisible(true);
					jlp7.setVisible(false);
				} else {
					jlp5.setVisible(false);
					jtf2.setVisible(false);
					jlp6.setVisible(false);
					jlp7.setVisible(true);
				}
			}
		});

		jrb4.setBounds(250, 70, 120, 25);
		jrb4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jrb4.isSelected()) {
					jlp5.setVisible(false);
					jtf2.setVisible(false);
					jlp6.setVisible(false);
					jlp7.setVisible(true);
				} else {
					jlp5.setVisible(true);
					jtf2.setVisible(true);
					jlp6.setVisible(true);
					jlp7.setVisible(false);
				}
			}
		});
		bg2 = new ButtonGroup();
		bg2.add(jrb3);
		bg2.add(jrb4);

		jlp3 = new JLabel("发  送  条  数：");
		jlp3.setBounds(20, 130, 100, 25);

		jtf3 = new JTextField();
		jtf3.setBounds(125, 130, 100, 25);
		jtf3.setText(ss.getSendTimes());
		jtf3.setVisible(true);

		jlp8 = new JLabel("条");
		jlp8.setBounds(240, 130, 100, 25);

		button1 = new JButton("保存");
		button1.setBounds(100, 350, 80, 25);
		button2 = new JButton("取消");
		button2.setBounds(220, 350, 80, 25);
		button1.addActionListener(this);
		button2.addActionListener(this);

		this.add(jlp1);
		this.add(jlp2);
		this.add(jlp3);
		this.add(jlp4);
		this.add(jlp5);
		this.add(jlp6);
		this.add(jlp7);
		this.add(jlp8);
		this.add(button1);
		this.add(button2);
		this.add(jrb1);
		this.add(jrb2);
		this.add(jrb3);
		this.add(jrb4);
		this.add(jtf1);
		this.add(jtf2);
		this.add(jtf3);
		ImageIcon i = new ImageIcon(getClass().getResource("setup.png"));
		this.setIconImage(i.getImage());
		// 原窗口不能动(模态窗口)
		this.setModal(true);
		this.setTitle("发送设置");
		this.setSize(400, 450);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 监听保存按钮
		if (e.getSource().equals(button1)) {
			// 先获取参数值
			SendSet sendSet = getSendSet();
			// 校验参数
			int f = checkParams(sendSet);
			if (f == 0) {
				// 校验成功
				MailMainGui.ss = sendSet;
				dispose();
			} else if (f == 1) {
				// 检验失败
				JOptionPane.showMessageDialog(null, "参数不能为空！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 2) {
				// 检验失败
				JOptionPane.showMessageDialog(null, "参数格式不正确！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 3) {
				// 检验失败
				JOptionPane.showMessageDialog(null, "一次发送条数应小于100条！", "提示消息", JOptionPane.WARNING_MESSAGE);
			} else if (f == 4) {
				// 检验失败
				JOptionPane.showMessageDialog(null, "一次至少发送一条！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
		}

		// 监听取消按钮
		if (e.getSource().equals(button2)) {
			dispose();
		}
	}

	// getset
	private SendSet getSendSet() {
		SendSet ss = new SendSet();
		ss.setTiming(!jrb1.isSelected());
		ss.setSendDate(jtf1.getText().trim());
		ss.setFixed(jrb3.isSelected());
		ss.setSendFrequency(jtf2.getText().trim());
		ss.setSendTimes(jtf3.getText().trim());
		return ss;
	}

	// checkparams
	private int checkParams(SendSet sendSet) {
		if (!sendSet.isTiming()) {
			if ("".equals(sendSet.getSendDate())) {
				return 1;
			} else {

			}
			if (sendSet.getSendDate().length() != 14 || !sendSet.getSendDate().matches("^[0-9]*$")) {
				return 2;
			}
		}

		if (sendSet.isFixed()) {
			if ("".equals(sendSet.getSendFrequency())) {
				return 1;
			}
			if (!sendSet.getSendFrequency().matches("^[0-9]*$") || sendSet.getSendFrequency().length() > 9) {
				return 2;
			}
		}
		if ("".equals(sendSet.getSendTimes())) {
			return 1;
		}
		if (!sendSet.getSendTimes().matches("^[0-9]*$") || sendSet.getSendTimes().length() > 9) {
			return 2;
		}

		if (sendSet.getSendTimes().length() > 2) {
			return 3;
		}

		if (Integer.valueOf(sendSet.getSendTimes()) < 1) {
			return 4;
		}

		return 0;
	}
}
