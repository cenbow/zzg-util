package com.zzg.util.token;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.nutz.mvc.Mvcs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 基于Session的防止用户重复提交
 * 
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年3月16日 上午11:45:30
 */
public class Token {
	
	private static Logger LOG = LoggerFactory.getLogger(Token.class);

	private static Token currentToken = new Token();

	public static Token getToken() {
		return currentToken;
	}

	public final static String TOKEN_KEY = "_token_";

	private Token() {
	}

	public static String createToken() {
		// 唯一标志
		String token = UUID.randomUUID().toString();
		// 存入Session
		Mvcs.getReq().getSession().setAttribute(TOKEN_KEY, token);

		return token;
	}

	public static boolean validToken(){
		String token = Mvcs.getReq().getParameter(TOKEN_KEY);
		String sessionToken = Mvcs.getReq().getSession().getAttribute(TOKEN_KEY).toString();

		// 清除token
		Mvcs.getReq().getSession().removeAttribute(TOKEN_KEY);

		if (StringUtils.isBlank(token)) {
			LOG.warn("can not find '{}' in request parameter map.", TOKEN_KEY);
			return false;
		}
		
		return token.equals(sessionToken);
	}
}
