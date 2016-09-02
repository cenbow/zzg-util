package com.zzg.util.web.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.zzg.util.excel.ExcelUtil;
import com.zzg.util.ftl.FTL;
import com.zzg.util.token.Token;

@At("/token_test")
@IocBean
public class TokenTestAction {
	
	@At("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] headers = { "a", "b", "c" };
		Collection<Object> dataset = new ArrayList<Object>();

		//设置response头信息  
		response.reset();
		response.setContentType("application/msexcel");// 定义输出类型 
		response.setHeader("Content-disposition", "attachment; filename=" + "excel" + ".xls");
		OutputStream out = response.getOutputStream();
		ExcelUtil.exportExcel(headers, dataset, out);
		out.flush();
		out.close();
	}

	@At("/to_submit")
	@Ok("jsp:/WEB-INF/jsp/token/token.jsp")
	public void toSubmit(String id) {

	}

	@At("/do_submit")
	@Ok("jsp:/WEB-INF/jsp/token/token.jsp")
	public void doSubmit(String id) {

		Lang.sleep(1000l);

		if (!Token.validToken()) {
			System.out.println("重复提交了！！！");
			Mvcs.getReq().setAttribute("message", "重复提交了!!!");
		}

	}

	@At("/get_json")
	@Ok("raw")
	public String getJson() {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("user", "Big Joe");
		Record latest = new Record();
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");
		root.put("latestProduct", latest);

		return FTL.format("test/a.ftl", root);
	}
}
