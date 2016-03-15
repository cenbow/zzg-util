package com.zzg.util.cmd;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

/**
 * 执行shell命令
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年3月15日 下午5:57:23
 */
public class LinuxCmd {
	public static String process(String command) {
		Process p = null;
		StringBuilder sb = new StringBuilder();
		try {
			String[] comands = new String[] { "/bin/sh", "-c", command };
			p = Runtime.getRuntime().exec(comands);
			String error = read(p.getErrorStream());
			String outInfo = read(p.getInputStream());
			String resultCode = "0";// 脚本中输出0表示命令执行成功
			if (error.length() != 0) { // 如果错误流中有内容，表明脚本执行有问题
				resultCode = "1";
			}
			sb.append(resultCode);
			sb.append(error);
			sb.append(outInfo);
			p.waitFor();
		} catch (Exception e) {
		} finally {
			try {
				p.getErrorStream().close();
			} catch (Exception e) {
			}
			try {
				p.getInputStream().close();
			} catch (Exception e) {
			}
			try {
				p.getOutputStream().close();
			} catch (Exception e) {
			}
		}
		return sb.toString();
	}

	/**
	 * 带返回结果的Linux命令处理
	 * @author zhang_zg
	 * @param command
	 * @return String 数组 result[0] 执行状态 0-成功;1-失败 result[1] 错误信息 result[2] 响应内容
	 */
	public static String[] process2(String command) {
		Process p = null;
		String[] result = new String[3];
		try {
			String[] comands = new String[] { "/bin/sh", "-c", command };
			p = Runtime.getRuntime().exec(comands);
			String error = read(p.getErrorStream());
			String outInfo = read(p.getInputStream());
			String resultCode = "0";// 脚本中输出0表示命令执行成功
			if (error.length() != 0) { // 如果错误流中有内容，表明脚本执行有问题
				resultCode = "1";
			}
			result[0] = StringUtils.trimToEmpty(resultCode);
			result[1] = StringUtils.trimToEmpty(error);
			result[2] = StringUtils.trimToEmpty(outInfo);
			p.waitFor();
		} catch (Exception e) {
		} finally {
			try {
				p.getErrorStream().close();
			} catch (Exception e) {
			}
			try {
				p.getInputStream().close();
			} catch (Exception e) {
			}
			try {
				p.getOutputStream().close();
			} catch (Exception e) {
			}
		}
		return result;
	}

	public static String read(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		int ch;
		while (-1 != (ch = in.read()))
			sb.append((char) ch);
		return sb.toString();
	}

}
