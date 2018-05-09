package mailsend;

public class MailConfig {
	// smtpurl
	public String smtpUrl;
	// smtpport
	public String smtpPort;

	public String getSmtpUrl() {
		return smtpUrl;
	}

	public void setSmtpUrl(String smtpUrl) {
		this.smtpUrl = smtpUrl;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	@Override
	public String toString() {
		return "MailConfig [smtpUrl=" + smtpUrl + ", smtpPort=" + smtpPort + "]";
	}

}
