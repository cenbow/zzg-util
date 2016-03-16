package com.zzg.util.web;

import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

public class MvcSetup implements Setup {

    public void init(NutConfig config) {
		System.out.println("这里是初始化...");
    }
    
    @Override
    public void destroy(NutConfig config) {
    }
}
