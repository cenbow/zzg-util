package com.zzg.util.string;

import org.apache.commons.lang3.StringEscapeUtils;

public class XssDefense {

	public static void main(String[] args) {

		/**
		 * html 实体编码 与 解码
		 * 可用于预防XSS跨站点脚本攻击
		 */
		String html = "中国<img src='http://www.test'><script>alert(document.cookie;);</script>";
		html = StringEscapeUtils.escapeHtml4(html);

		//打印结果：&#20013;&#22269;&lt;img src='http://www.test'&gt;&lt;script&gt;alert(document.cookie;);&lt;/script&gt;
		System.out.println(html);
		html = StringEscapeUtils.unescapeHtml4(html);

		//打印结果：中国<img src='http://www.test'><script>alert(document.cookie;);</script>
		System.out.println(html);

		/**
		 *  sql 特殊字符转义(主要是将一个单引号替换成两个单引号)
		 *  可用于预防sql注入
		 */
		String sql = "1' or '1'='1";
		sql = StringEscapeUtils.escapeEcmaScript(sql);

		//打印结果：1'' or ''1''=''1
		System.out.println(sql);

		/**
		 * xml 特殊字符过滤
		 */
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		xml = StringEscapeUtils.escapeXml(xml);

		//打印结果：&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot; ?&gt;
		System.out.println(xml);
		xml = StringEscapeUtils.unescapeXml(xml);

		//打印结果：<?xml version="1.0" encoding="UTF-8" ?>
		System.out.println(xml);
	}
}
