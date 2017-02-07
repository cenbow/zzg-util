package com.zzg.util.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.nutz.lang.Streams;
import org.nutz.lang.random.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 为了与环信的Rest API集成，还得亲自写个HTTP工具类...
 * 	Nutz的Http不知怎地，不好使
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年11月1日 上午11:39:48
 */
public class HttpUtil {
	private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);

	private static final String SEPARATOR = "\r\n";
	private static final String TWO_HYPHENS = "--";
	private static final String CHARTSET = "UTF-8";

	/**
	 * GET请求
	 * @author zhang_zg
	 * @param url 请求的地址
	 * @param header 头文件配置
	 * @param timeout 超时时间
	 * @return
	 */
	public static String get(String url, Map<String, String> header, int timeout) {
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置超时时间
			if (timeout > 0) {
				conn.setConnectTimeout(timeout);
				conn.setReadTimeout(timeout);
			}
			// 设置通用的请求属性
			if (header != null) {
				for (String key : header.keySet()) {
					conn.setRequestProperty(key, header.get(key));
				}
			}
			// 建立实际的连接
			conn.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

				StringBuilder result = new StringBuilder();
				String line = null;
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
				return result.toString();
			} finally {
				Streams.safeClose(in);
			}

		} catch (Exception ex) {
			LOG.error("Get request send error", ex);
		}
		return null;
	}

	/**
	 * UPLOAD请求
	 * @author zhang_zg
	 * @param url 上传文件地址
	 * @param header 头文件配置
	 * @param map 文件集合，暂支持一个文件, key为提交表单文件的name
	 * @param timeout 超时时间
	 * @return
	 */
	public static String upload(String url, Map<String, String> header, Map<String, File> files, int timeout) {
		String boundary = "---------------------------" + R.UU32();
		try {
			String key = null;
			File file = null;
			for (String k : files.keySet()) {
				key = k;
				file = files.get(k);
				break;
			}

			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 设置超时时间
			if (timeout > 0) {
				conn.setConnectTimeout(timeout);
				conn.setReadTimeout(timeout);
			}
			// 设置通用的请求属性
			if (header != null) {
				for (String k : header.keySet()) {
					conn.setRequestProperty(k, header.get(k));
				}
			}

			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

			DataOutputStream outs = null;
			try {
				outs = new DataOutputStream(conn.getOutputStream());
				outs.writeBytes(TWO_HYPHENS + boundary + SEPARATOR);
				outs.writeBytes("Content-Disposition:form-data;" + "name=\"" + key + "\";" + "filename=\"" + file.getName().getBytes(CHARTSET) + "\""
						+ SEPARATOR);
				outs.writeBytes(SEPARATOR);

				InputStream is = null;
				try {
					is = Streams.fileIn(file);
					Streams.write(outs, is);
					outs.writeBytes(SEPARATOR);
					outs.writeBytes(TWO_HYPHENS + boundary + TWO_HYPHENS + SEPARATOR);
				} finally {
					Streams.safeClose(is);
				}
			} finally {
				Streams.safeFlush(outs);
			}
			// 建立实际的连接
			conn.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder result = new StringBuilder();
				String line = null;
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
				return result.toString();
			} finally {
				Streams.safeClose(in);
			}
		} catch (Exception ex) {
			LOG.error("Upload request send error", ex);
		}
		return null;
	}
}
