package mailrecive;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class MailReciveMsgUtits {

	private MimeMessage mailMimeMessage = null;
	// 附件地址
	private String saveMailAttachPath = "";
	// 邮件内容
	private StringBuffer mailBodyText = new StringBuffer();
	// 显示日期format
	private String dateFormat = "yyyy-MM-dd HH:mm:ss";
	// 附件名字
	public StringBuilder sbAname = new StringBuilder();
	// 存储内容的map
	// LinkedHashMap 有序
	public Map maps = new LinkedHashMap();

	// 构造方法
	public MailReciveMsgUtits() {
	}

	public String getSbAname() {
		return sbAname.toString().substring(0, sbAname.toString().length() - 1);
		// return sbAname.toString();
	}

	// 构造方法
	public MailReciveMsgUtits(MimeMessage mailMimeMessage) {
		this.mailMimeMessage = mailMimeMessage;
	}

	// 获得发件人地址和姓名
	public String getMailFrom() throws Exception {
		InternetAddress address[] = (InternetAddress[]) mailMimeMessage.getFrom();
		String from = address[0].getAddress();
		if (from == null) {
			from = "";
		}
		String personal = address[0].getPersonal();

		if (personal == null) {
			personal = "";
		}

		String fromAddr = null;
		if (personal != null || from != null) {
			fromAddr = personal + "<" + from + ">";
		}
		return fromAddr;
	}

	// 获得邮件的收件人，抄送，和密送的地址和姓名
	public String getMailAddress(String type) throws Exception {
		String mailAddr = "";
		String addType = type.toUpperCase();

		InternetAddress[] address = null;
		if (addType.equals("TO") || addType.equals("CC") || addType.equals("BCC")) {

			if (addType.equals("TO")) {
				address = (InternetAddress[]) mailMimeMessage.getRecipients(Message.RecipientType.TO);
			} else if (addType.equals("CC")) {
				address = (InternetAddress[]) mailMimeMessage.getRecipients(Message.RecipientType.CC);
			} else {
				address = (InternetAddress[]) mailMimeMessage.getRecipients(Message.RecipientType.BCC);
			}

			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					String emailAddr = address[i].getAddress();
					if (emailAddr == null) {
						emailAddr = "";
					} else {
						emailAddr = MimeUtility.decodeText(emailAddr);
					}
					String personal = address[i].getPersonal();
					if (personal == null) {
						personal = "";
					} else {
						personal = MimeUtility.decodeText(personal);
					}
					String compositeto = personal + "<" + emailAddr + ">";
					mailAddr += "," + compositeto;
				}
				mailAddr = mailAddr.substring(1);
			}
		} else {
			return "";
		}
		return mailAddr;
	}

	// 获取邮件主题
	public String getMailSubject() throws MessagingException {
		String subject = "";
		subject = mailMimeMessage.getSubject();
		if (subject != null) {
			try {
				subject = MimeUtility.decodeText(subject);
				if (subject == null) {
					subject = "";
				}
			} catch (Exception exce) {
				exce.printStackTrace();
			}
		}
		return subject;
	}

	// 获取邮件发送日期
	public String getMailSentDate() throws Exception {
		Date sentDate = mailMimeMessage.getSentDate();
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		String strSentDate = format.format(sentDate);
		return strSentDate;
	}

	// 解析邮件
	public Map getMailContent(Part part) throws Exception {
		String contentType = part.getContentType();
		// 获取邮件的MimeType类型
		int nameIndex = contentType.indexOf("name");
		boolean conName = false;
		if (nameIndex != -1) {
			conName = true;
		}
		if (part.isMimeType("text/plain") && conName == false) {
			// text/plain 类型,纯文本
			// mailBodyText.append((String) part.getContent());
			maps.put("0", (String) part.getContent());
			System.out.println(1);
		} else if (part.isMimeType("text/html") && conName == false) {
			// text/html 类型，html
			// mailBodyText.append((String) part.getContent());
			maps.put("1", (String) part.getContent());
			System.out.println(2);
		} else if (part.isMimeType("multipart/*")) {
			// multipart/*
			Multipart multipart = (Multipart) part.getContent();
			int counts = multipart.getCount();
			for (int i = 0; i < counts; i++) {
				getMailContent(multipart.getBodyPart(i));
			}
		} else if (part.isMimeType("message/rfc822")) {
			// message/rfc822
			getMailContent((Part) part.getContent());
		} else {

		}
		// System.out.println(mailBodyText.toString());
		return maps;
	}

	// 判断邮件是否需要回执
	public boolean getReplySign() throws MessagingException {

		boolean replySign = false;

		String needReply[] = mailMimeMessage.getHeader("Disposition-Notification-To");

		if (needReply != null) {
			replySign = true;
		}
		return replySign;
	}

	// 获取此邮件的Message-ID
	public String getMailMessageId() throws MessagingException {
		String messageID = mailMimeMessage.getMessageID();
		return messageID;
	}

	// 是否已读
	public boolean isNew() throws MessagingException {
		boolean isNew = false;
		Flags flags = ((Message) mailMimeMessage).getFlags();
		Flags.Flag[] flag = flags.getSystemFlags();
		for (int i = 0; i < flag.length; i++) {
			if (flag[i] == Flags.Flag.SEEN) {
				isNew = true;
			}
		}
		return isNew;
	}

	// 是否包含附件
	public boolean isMailContainAttach(Part part) throws Exception {
		boolean attachFlag = false;
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mPart = mp.getBodyPart(i);
				String disposition = mPart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
					attachFlag = true;
				else if (mPart.isMimeType("multipart/*")) {
					attachFlag = isMailContainAttach((Part) mPart);
				} else {
					String conType = mPart.getContentType();

					if (conType.toLowerCase().indexOf("application") != -1)
						attachFlag = true;
					if (conType.toLowerCase().indexOf("name") != -1)
						attachFlag = true;
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			attachFlag = isMailContainAttach((Part) part.getContent());
		}
		return attachFlag;
	}

	// 保存附件
	public void saveMailAttachMent(Part part, int f, String path) throws Exception {
		String fileName = "";
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mPart = mp.getBodyPart(i);
				String disposition = mPart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					fileName = mPart.getFileName();
					if (fileName.toLowerCase().indexOf("gb2312") != -1) {
						fileName = MimeUtility.decodeText(fileName);
					}
					if (f == 0) {
						// 0是获取name
						sbAname.append(fileName + ",");
					} else if (f == 1) {
						// 1是下载
						// 保存
						saveFile(fileName, mPart.getInputStream(), path);
					}

				} else if (mPart.isMimeType("multipart/*")) {
					saveMailAttachMent(mPart, f, path);
				} else {
					fileName = mPart.getFileName();
					if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
						fileName = MimeUtility.decodeText(fileName);
						if (f == 0) {
							// 0是获取name
							sbAname.append(fileName + ",");
						} else if (f == 1) {
							// 1是下载
							// 保存
							saveFile(fileName, mPart.getInputStream(), path);
						}
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveMailAttachMent((Part) part.getContent(), f, path);
		}
	}

	// 下载附件
	private void saveFile(String fileName, InputStream in, String path) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			// 不存在则创建
			try {
				file.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
				// 创建失败则指定
				path = "c://";
			}
		}
		File storeFile = new File(path + File.separator + fileName);
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(storeFile));
			bis = new BufferedInputStream(in);
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
				bos.flush();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new Exception("文件保存失败!");
		} finally {
			bos.close();
			bis.close();
		}
	}
}
