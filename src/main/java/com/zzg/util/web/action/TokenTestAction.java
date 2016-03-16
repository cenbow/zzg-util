package com.zzg.util.web.action;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.zzg.util.token.Token;

@At("/token_test")
@IocBean
public class TokenTestAction {
	
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
}
