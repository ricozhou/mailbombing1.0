package maiGUI;

public class SendSet {
	// 是否定时
	public boolean isTiming;
	// 时间
	public String sendDate;
	// 发送条数
	public String sendTimes;
	// 是否固定频次
	public boolean isFixed;
	// 频次
	public String sendFrequency;

	public SendSet(boolean isTiming, String sendDate, String sendTimes, boolean isFixed, String sendFrequency) {
		this.isTiming = isTiming;
		this.sendDate = sendDate;
		this.sendTimes = sendTimes;
		this.isFixed = isFixed;
		this.sendFrequency = sendFrequency;
	}

	public SendSet() {

	}

	public boolean isTiming() {
		return isTiming;
	}

	public void setTiming(boolean isTiming) {
		this.isTiming = isTiming;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendTimes() {
		return sendTimes;
	}

	public void setSendTimes(String sendTimes) {
		this.sendTimes = sendTimes;
	}

	public boolean isFixed() {
		return isFixed;
	}

	public void setFixed(boolean isFixed) {
		this.isFixed = isFixed;
	}

	public String getSendFrequency() {
		return sendFrequency;
	}

	public void setSendFrequency(String sendFrequency) {
		this.sendFrequency = sendFrequency;
	}

	@Override
	public String toString() {
		return "SendSet [isTiming=" + isTiming + ", sendDate=" + sendDate + ", sendTimes=" + sendTimes + ", isFixed="
				+ isFixed + ", sendFrequency=" + sendFrequency + "]";
	}

}
