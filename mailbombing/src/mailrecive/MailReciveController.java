package mailrecive;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import maiGUI.MailMainGui;
import maiGUI.MailUtils;
import mailsend.MailModel;

public class MailReciveController {
	public MailUtils mu = new MailUtils();
	public MailReciveMsgUtits mrmu = null;
	public static Message[] message = null;
	public static int receTimes = 0;
	public static int receMailNum = 1;

	// 接收邮件
	public String receiveMail(MailModel mmodel2) {
		// 开始导入配置信息
		Properties prop = getProp(mmodel2);
		// 获取连接
		Session session = Session.getInstance(prop);
		Store store;
		try {
			store = session.getStore("pop3");
			store.connect(mmodel2.getMc().getSmtpUrl(), mmodel2.getMm2().getSendUser(),
					mmodel2.getMm2().getSendUserPwd());
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			// 总邮件数量
			int messageCount = folder.getMessageCount();
			int start = messageCount + 1 - Integer.valueOf(mmodel2.getMm2().getLastNum());
			if (start < 1) {
				start = 1;
			}
			int end = messageCount + 1 - Integer.valueOf(mmodel2.getMm2().getFromNum());
			message = folder.getMessages(start, end);
			receMailNum = message.length;
			System.out.println("邮件数量:　" + message.length);
			// 获取邮件显示信息向前台显示
			if (showMailMsg(message)) {
				if (!MailMainGui.isRunning2) {
					// 中止
					return "3";
				}
			} else {
				return "2";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}

		return "0";
	}

	// 前台显示信息拼接
	private boolean showMailMsg(Message[] message) {
		try {
			MailReciveController.receTimes = 0;
			if (message != null) {
				MailMainGui.mailData.clear();
				for (int i = message.length; i > 0; i--) {
					// 线程标志
					if (!MailMainGui.isRunning2) {
						return true;
					}
					mrmu = new MailReciveMsgUtits((MimeMessage) message[i - 1]);
					MailMainGui.mailData.addElement(mrmu.getMailSubject() + "<" + mrmu.getMailFrom() + ">" + "("
							+ mrmu.getMailSentDate() + ")");
					MailReciveController.receTimes++;
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// getproperties
	private Properties getProp(MailModel mmodel2) {
		Properties prop = new Properties();
		prop.setProperty("mail.pop3.host", mmodel2.getMc().getSmtpUrl());
		prop.setProperty("mail.pop3.port", mmodel2.getMc().getSmtpPort());
		// SSL安全连接参数
		// if (!mmodel2.getMc().getSmtpUrl().contains("qq")) {
		prop.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.setProperty("mail.pop3.socketFactory.fallback", "true");
		prop.setProperty("mail.pop3.socketFactory.port", "995");
		// }
		return prop;
	}

}
