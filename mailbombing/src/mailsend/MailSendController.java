package mailsend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import maiGUI.MailMainGui;
import maiGUI.MailUtils;

public class MailSendController {
	public MailUtils mu = new MailUtils();
	// public CountThread thread;
	// public Transport ts = null;
	public static int sendTimes = 0;
	public static int taskNum = 0;
	public static List<CountThread> tl = new ArrayList<CountThread>();

	// 发送
	public String mailSend(MailModel mmodel, int fb, String path) {

		try {
			// 0表示发送
			CountThread thread = new CountThread(mmodel, fb, path);
			thread.start();
			// 把所有的线程加入集合，等会中止全部
			tl.add(thread);

			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
	}

	// create a mail
	private Message createMail(Session session, MailModel mmodel) throws IOException {
		try {
			Message mailMessage = new MimeMessage(session);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mmodel.getMm().getSendUser());
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address[] tos = new InternetAddress[mmodel.getMm().getReciUsers().size()];
			for (int i = 0; i < mmodel.getMm().getReciUsers().size(); i++) {
				tos[i] = new InternetAddress(mmodel.getMm().getReciUsers().get(i));
			}

			// 将所有接收者地址都添加到邮件接收者属性中
			mailMessage.setRecipients(Message.RecipientType.TO, tos);

			// 抄送
			if (mmodel.getMm().getCopyToUsers() != null && mmodel.getMm().getCopyToUsers().size() > 0) {
				Address[] ccs = new InternetAddress[mmodel.getMm().getCopyToUsers().size()];
				for (int i = 0; i < mmodel.getMm().getCopyToUsers().size(); i++) {
					ccs[i] = new InternetAddress(mmodel.getMm().getCopyToUsers().get(i));
				}
				// 将所有接收者地址都添加到邮件接收者属性中
				mailMessage.setRecipients(Message.RecipientType.CC, ccs);
			}

			// 暗抄送
			if (mmodel.getMm().getDarkCopyToUsers() != null && mmodel.getMm().getDarkCopyToUsers().size() > 0) {
				Address[] bccs = new InternetAddress[mmodel.getMm().getDarkCopyToUsers().size()];
				for (int i = 0; i < mmodel.getMm().getDarkCopyToUsers().size(); i++) {
					bccs[i] = new InternetAddress(mmodel.getMm().getDarkCopyToUsers().get(i));
				}
				// 将所有接收者地址都添加到邮件接收者属性中
				mailMessage.setRecipients(Message.RecipientType.BCC, bccs);
			}

			mailMessage.setSubject(mmodel.getMm().getMailTheme());
			// mailMessage.setSentDate(new Date());
			// 设置邮件内容
			Multipart mainPart = new MimeMultipart("mixed");
			BodyPart html = new MimeBodyPart();
			MimeBodyPart attch = new MimeBodyPart();
			html.setContent(mmodel.getMm().getMainText(), "text/html; charset=UTF-8");
			mainPart.addBodyPart(html);
			// 设置附件
			if (mmodel.getMm().getAttachs() != null && mmodel.getMm().getAttachs().size() > 0) {
				for (int i = 0; i < mmodel.getMm().getAttachs().size(); i++) {
					attch = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(new File(mmodel.getMm().getAttachs().get(i)));
					attch.setDataHandler(new DataHandler(fds));
					attch.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
					mainPart.addBodyPart(attch);
				}
			}

			mailMessage.setContent(mainPart);
			mailMessage.saveChanges();
			// mailMessage.writeTo(new FileOutputStream("E:\\ImageMail.eml"));
			// 返回创建好的邮件
			return mailMessage;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	// 计时线程
	private class CountThread extends Thread {
		public MailModel mmodel;
		public int fb;
		public String path;

		private CountThread(MailModel mmodel, int fb, String path) {
			// false是用户线程，true是守护线程，守护线程主线程停止就全部停止。
			setDaemon(false);
			this.mmodel = mmodel;
			this.fb = fb;
			this.path = path;
		}

		@Override
		public void run() {
			taskNum++;
			MailMainGui.jlb18.setVisible(true);
			MailMainGui.jlb18.setText("任务数：  " + MailSendController.taskNum);
			sendTimes = 0;
			// System.out.println("b:" + mmodel.getMm().getMailTheme());
			// 计算时间差
			long dates = 0;
			if (mmodel.getSs().isTiming) {
				try {
					dates = dateToS(new Date(), mu.formatStringToDate(mmodel.getSs().getSendDate(), "yyyyMMddHHmmss"));
					Thread.sleep(dates);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// System.out.println("a:" + mmodel.getMm().getMailTheme());
			Transport ts = null;
			Message message = null;
			try {
				// 先创建再阻塞
				// 邮件服务器相关设置
				Properties prop = new Properties();
				prop.setProperty("mail.host", mmodel.getMc().getSmtpUrl());
				prop.setProperty("mail.transport.protocol", "smtp");
				prop.setProperty("mail.smtp.auth", "true");
				prop.setProperty("mail.smtp.port", mmodel.getMc().getSmtpPort());
				prop.setProperty("username", mmodel.getMm().getSendUser());
				prop.setProperty("password", mmodel.getMm().getSendUserPwd());

				// 固定频次
				int waitTime = Integer.valueOf(mmodel.getSs().getSendFrequency()) * 1000;
				// 使用JavaMail发送邮件的5个步骤
				// 1、创建session
				Session session = Session.getInstance(prop);
				// 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
				session.setDebug(false);
				// 2、通过session得到transport对象
				ts = session.getTransport();
				// 3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
				ts.connect(mmodel.getMm().getSendUser(), mmodel.getMm().getSendUserPwd());
				// 4、创建邮件
				message = createMail(session, mmodel);

				// 判断时间，到了启动线程
				int times = Integer.valueOf(mmodel.getSs().getSendTimes());
				if (fb == 0) {
					// 设置发送显示时间
					for (int i = 0; i < times; i++) {
//						if (!MailMainGui.isRunning) {
//							// 中止
//							return;
//						}
						message.setSentDate(new Date());
						ts.sendMessage(message, message.getAllRecipients());
						sendTimes++;
						// 最后一次不再等待
						if (i != times - 1) {
							if (mmodel.getSs().isFixed) {
								Thread.sleep(waitTime);
							} else {
								// 1-1000s
								Thread.sleep((1 + (int) (Math.random() * 180)) * 1000);
							}
						}

					}
				} else if (fb == 1) {
					if (path != null) {
						File f = new File(path);
						if (f.exists()) {
							message.writeTo(new FileOutputStream(
									path + "\\" + mu.formatDate(new Date(), "yyyyMMddHHmmss") + ".mel"));
						} else {
							return;
						}
					} else {
						return;
					}
				}
				taskNum--;
				MailMainGui.jlb18.setVisible(true);
				MailMainGui.jlb18.setText("任务数：  " + MailSendController.taskNum);
				if(taskNum==0){
					MailMainGui.isRunning=false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (ts != null) {
						ts.close();
					}
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public long dateToS(Date date1, Date date2) {
		long dates = 0;
		if (date1 != null && date2 != null) {
			if (date2.before(date1)) {
				dates = 0;
			} else {
				dates = date2.getTime() - date1.getTime();
			}
		} else {
			dates = 0;
		}
		return dates;
	}

	public boolean stopThread() {
		if (!MailMainGui.isRunning) {
			// 中止所有
			Iterator<CountThread> it = tl.iterator();
			while (it.hasNext()) {
				CountThread thread = it.next();
				if (thread != null) {
					thread.stop();
				}
				if (!thread.isAlive()) {
					it.remove();
				}
			}
			MailSendController.taskNum = tl.size();
			MailMainGui.jlb18.setText("任务数：  " + MailSendController.taskNum);
			return true;
		}
		return false;
	}

}
