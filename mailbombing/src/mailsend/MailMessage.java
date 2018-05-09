package mailsend;

import java.util.Date;
import java.util.List;

public class MailMessage {

	// 发件人
	public String sendUser;
	// 密码
	public String sendUserPwd;
	// 收件人
	public List<String> reciUsers;
	public String reciUsersString;
	// 抄送地址
	public List<String> copyToUsers;
	public String copyToUsersString;
	// 暗抄送地址
	public List<String> darkCopyToUsers;
	public String darkCopyToUsersString;
	// 主题
	public String mailTheme;
	// 附件地址
	public List<String> attachs;
	// 正文
	public String mainText;
	// 发送时间
	public Date sendDate;

	public String getReciUsersString() {
		return reciUsersString;
	}

	@Override
	public String toString() {
		return "MailMessage [sendUser=" + sendUser + ", sendUserPwd=" + sendUserPwd + ", reciUsers=" + reciUsers
				+ ", reciUsersString=" + reciUsersString + ", copyToUsers=" + copyToUsers + ", copyToUsersString="
				+ copyToUsersString + ", darkCopyToUsers=" + darkCopyToUsers + ", darkCopyToUsersString="
				+ darkCopyToUsersString + ", mailTheme=" + mailTheme + ", attachs=" + attachs + ", mainText=" + mainText
				+ ", sendDate=" + sendDate + "]";
	}

	public void setReciUsersString(String reciUsersString) {
		this.reciUsersString = reciUsersString;
	}

	public String getCopyToUsersString() {
		return copyToUsersString;
	}

	public void setCopyToUsersString(String copyToUsersString) {
		this.copyToUsersString = copyToUsersString;
	}

	public String getDarkCopyToUsersString() {
		return darkCopyToUsersString;
	}

	public void setDarkCopyToUsersString(String darkCopyToUsersString) {
		this.darkCopyToUsersString = darkCopyToUsersString;
	}

	public String getSendUser() {
		return sendUser;
	}

	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}

	public String getSendUserPwd() {
		return sendUserPwd;
	}

	public void setSendUserPwd(String sendUserPwd) {
		this.sendUserPwd = sendUserPwd;
	}

	public List<String> getReciUsers() {
		return reciUsers;
	}

	public void setReciUsers(List<String> reciUsers) {
		this.reciUsers = reciUsers;
	}

	public List<String> getCopyToUsers() {
		return copyToUsers;
	}

	public void setCopyToUsers(List<String> copyToUsers) {
		this.copyToUsers = copyToUsers;
	}

	public List<String> getDarkCopyToUsers() {
		return darkCopyToUsers;
	}

	public void setDarkCopyToUsers(List<String> darkCopyToUsers) {
		this.darkCopyToUsers = darkCopyToUsers;
	}

	public String getMailTheme() {
		return mailTheme;
	}

	public void setMailTheme(String mailTheme) {
		this.mailTheme = mailTheme;
	}

	public List<String> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<String> attachs) {
		this.attachs = attachs;
	}

	public String getMainText() {
		return mainText;
	}

	public void setMainText(String mainText) {
		this.mainText = mainText;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

}
