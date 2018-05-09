package mailsend;

import maiGUI.SendSet;
import mailrecive.MailMessage2;

public class MailModel {
	// 发送服务器设置
	public MailConfig mc;
	// 发送基本设置
	public SendSet ss;
	// 邮件主体信息
	public MailMessage mm;
	public MailMessage2 mm2;

	public MailMessage2 getMm2() {
		return mm2;
	}

	public void setMm2(MailMessage2 mm2) {
		this.mm2 = mm2;
	}

	public MailConfig getMc() {
		return mc;
	}

	public void setMc(MailConfig mc) {
		this.mc = mc;
	}

	public SendSet getSs() {
		return ss;
	}

	public void setSs(SendSet ss) {
		this.ss = ss;
	}

	public MailMessage getMm() {
		return mm;
	}

	public void setMm(MailMessage mm) {
		this.mm = mm;
	}

	@Override
	public String toString() {
		return "MailModel [mc=" + mc + ", ss=" + ss + ", mm=" + mm + ", mm2=" + mm2 + "]";
	}

}
