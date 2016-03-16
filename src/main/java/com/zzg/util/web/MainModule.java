package com.zzg.util.web;

import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.zzg.util" })
@SetupBy(MvcSetup.class)
@Encoding(input = "UTF-8", output = "UTF-8")
public class MainModule {
}
