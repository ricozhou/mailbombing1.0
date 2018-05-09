package maiGUI;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MailUtils {
	// 文件大小50m
	public static final int FILE_SIZE = 52428800;

	// 验证邮箱格式
	public boolean checkMailFormat(String[] accUsers) {
		if (accUsers == null) {
			return false;
		}
		String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		// 遍历数组
		for (int i = 0; i < accUsers.length; i++) {
			if (!regex.matcher(accUsers[i]).matches()) {
				return false;
			}
		}
		return true;
	}

	// 检查文件大小
	public boolean checkFileSize(File[] fs) {
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].exists() && fs[i].isFile()) {
				if (FILE_SIZE < fs[i].length()) {
					return false;
				}
			}
		}
		return true;
	}

	// 获取路径和文件名
	public Map<String, String> getFilePathName(File[] fs) {
		Map<String, String> filePathName = new HashMap<String, String>();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].exists() && fs[i].isFile()) {
				filePathName.put(fs[i].getAbsolutePath(), fs[i].getName());
			}
		}
		return filePathName;
	}

	// 格式化所有name
	public String getFileNameFormat(Map<String, String> filePathName) {
		StringBuilder sb = new StringBuilder();
		for (String fileName : filePathName.values()) {
			sb.append(fileName + ";");
		}
		return sb.toString();
	}

	public void getFilePathName(List<String> filePathList, File[] fs) {
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].exists() && fs[i].isFile()) {
				filePathList.add(fs[i].getAbsolutePath());
			}
		}
	}

	// formatdatetostring
	public String formatDate(Date date, String stringFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(stringFormat);
		String dateString = sdf.format(date);
		return dateString;
	}

	// string to date
	public Date formatStringToDate(String dateString, String stringFormat) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(stringFormat);
		Date date = sdf.parse(dateString);
		return date;
	}

}
