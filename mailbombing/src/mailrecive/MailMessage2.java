package mailrecive;

public class MailMessage2 {
	// 发件人
	public String sendUser;
	// 密码
	public String sendUserPwd;
	// 是否全部
	public boolean isAllRece;
	// 第几封
	public String fromNum;
	// 到第几封
	public String lastNum;

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

	public boolean isAllRece() {
		return isAllRece;
	}

	public void setAllRece(boolean isAllRece) {
		this.isAllRece = isAllRece;
	}

	public String getFromNum() {
		return fromNum;
	}

	public void setFromNum(String fromNum) {
		this.fromNum = fromNum;
	}

	public String getLastNum() {
		return lastNum;
	}

	public void setLastNum(String lastNum) {
		this.lastNum = lastNum;
	}

	@Override
	public String toString() {
		return "MailMessage2 [sendUser=" + sendUser + ", sendUserPwd=" + sendUserPwd + ", isAllRece=" + isAllRece
				+ ", fromNum=" + fromNum + ", lastNum=" + lastNum + "]";
	}

}
