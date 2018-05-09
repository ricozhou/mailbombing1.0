package maiGUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import mailrecive.MailMessage2;
import mailsend.MailConfig;
import mailsend.MailMessage;
import mailsend.MailModel;

public class MailSaveModelUtils {

	public boolean isFileExit(File file) {
		if (file.exists()) {
			return true;
		}
		return false;
	}

	public MailModel getDefaultMsgSet(File f, int flag) throws Exception {
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(f);
		BufferedReader bf = new BufferedReader(new InputStreamReader(fis, "utf-8"));
		pro.load(bf);
		// 封装对象
		MailModel mmodel = new MailModel();
		// url设置
		MailConfig mc = new MailConfig();
		// 发送基本设置
		SendSet ss = new SendSet();
		// 发送msg
		MailMessage mm = new MailMessage();
		// 接收msg
		MailMessage2 mm2 = new MailMessage2();
		mc.setSmtpUrl(pro.getProperty("smtpurl"));
		mc.setSmtpPort(pro.getProperty("smtpport"));

		// 获取主体信息
		// 0表示发送
		if (flag == 0) {
			mm.setSendUser(pro.getProperty("senduser"));
			mm.setSendUserPwd(pro.getProperty("sendpwd"));
			mm.setReciUsersString(pro.getProperty("reciuser"));
			mm.setCopyToUsersString(pro.getProperty("copyuser"));
			mm.setDarkCopyToUsersString(pro.getProperty("darkcopyuser"));
			mm.setMailTheme(pro.getProperty("mailtheme"));
			// 不添加附件
			// 1是立即发送，0是定时发送
			ss.setTiming("0".equals(pro.getProperty("istiming")) ? true : false);
			// 定时时间
			ss.setSendDate(pro.getProperty("senddate"));
			// 是否固定频次
			// 0是固定1不固定
			ss.setFixed("0".equals(pro.getProperty("isfixed")) ? true : false);
			// 频次
			ss.setSendFrequency(pro.getProperty("sendfre"));
			// 发送条数
			ss.setSendTimes(pro.getProperty("sendtimes"));
		} else if (flag == 1) {
			// 1表示接收
			// 获取主体信息
			mm2.setSendUser(pro.getProperty("receiveuser"));
			mm2.setSendUserPwd(pro.getProperty("receivepwd"));
			mm2.setAllRece("0".equals(pro.getProperty("isallrece")) ? true : false);
			mm2.setFromNum(pro.getProperty("fromnum"));
			mm2.setLastNum(pro.getProperty("lastnum"));
		}
		mmodel.setSs(ss);
		mmodel.setMc(mc);
		mmodel.setMm(mm);
		mmodel.setMm2(mm2);
		fis.close();
		bf.close();
		return mmodel;
	}

	public boolean BaseMsgToPro(MailModel mmdo, int flag) {
		File f;
		String content = "";
		// 0是发送，1是接收
		if (flag == 0) {
			f = new File("sendmailmodel.properties");
			content = "smtpurl=" + mmdo.getMc().getSmtpUrl() + "\r\nsmtpport=" + mmdo.getMc().getSmtpPort()
					+ "\r\nsenduser=" + mmdo.getMm().getSendUser() + "\r\nsendpwd=" + mmdo.getMm().getSendUserPwd()
					+ "\r\nreciuser=" + mmdo.getMm().getReciUsersString() + "\r\ncopyuser="
					+ mmdo.getMm().getCopyToUsersString() + "\r\ndarkcopyuser="
					+ mmdo.getMm().getDarkCopyToUsersString() + "\r\nmailtheme=" + mmdo.getMm().getMailTheme()
					+ "\r\nistiming=" + (mmdo.getSs().isTiming() ? "0" : "1") + "\r\nsenddate="
					+ mmdo.getSs().getSendDate() + "\r\nisfixed=" + (mmdo.getSs().isFixed() ? "0" : "1")
					+ "\r\nsendfre=" + mmdo.getSs().getSendFrequency() + "\r\nsendtimes=" + mmdo.getSs().getSendTimes();
		} else {
			f = new File("receivemailmodel.properties");
			content = "smtpurl=" + mmdo.getMc().getSmtpUrl() + "\r\nsmtpport=" + mmdo.getMc().getSmtpPort()
					+ "\r\nreceiveuser=" + mmdo.getMm2().getSendUser() + "\r\nreceivepwd="
					+ mmdo.getMm2().getSendUserPwd() + "\r\nisallrece=" + (mmdo.getMm2().isAllRece() ? "0" : "1")
					+ "\r\nfromnum=" + mmdo.getMm2().getFromNum() + "\r\nlastnum=" + mmdo.getMm2().getLastNum();
		}
		// 先删除隐藏文件再重新创建，隐藏文件不支持修改
		if (f.exists()) {
			f.delete();
		}
		FileOutputStream fos = null;
		try {
			if (flag == 0) {
				// 存入主体msg
				saveMsgToFile(new File("sendmailmessage.txt"), mmdo.getMm().getMainText());
			}

			fos = new FileOutputStream(f);// 创建文件输出流对象
			// 设置文件的隐藏属性
			String set = "attrib +H " + f.getAbsolutePath();
			Runtime.getRuntime().exec(set);
			// 将字符串写入到文件中
			fos.write(content.getBytes());
			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	// 存入主体信息
	private void saveMsgToFile(File f, String msg) throws Exception {
		if (f.exists()) {
			f.delete();
		}
		// 设置文件的隐藏属性
		String set = "attrib +H " + f.getAbsolutePath();
		Runtime.getRuntime().exec(set);
		// 将字符串写入到文件中
		FileOutputStream writerStream = new FileOutputStream(f);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
		writer.write(msg);
		writer.close();
	}

	// 获取主体信息
	public String getDefaultMailMsg(File f2) {
		// 一次性读取
		String encoding = "UTF-8";
		Long filelength = f2.length();
		byte[] filecontent = new byte[filelength.intValue()];
		FileInputStream in = null;
		try {
			in = new FileInputStream(f2);
			in.read(filecontent);
			return new String(filecontent, encoding);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
