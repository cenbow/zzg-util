package com.zzg.util.ftl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * FreeMarker模板处理工具类
 * 功能：模板加载到内存，并且可定期更新 
 * @author zhang_zg 
 * @version 1.0    
 * @created 2016年6月16日 下午6:20:36
 */
public class FTL {
	private static final Logger LOG = LoggerFactory.getLogger(FTL.class);

	private static final String TEMPLATE_DIR_NAME = "template";
	private static File ftlFiles;
	private static String templateDir;
	private static List<File> fileList = new ArrayList<File>();
	private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

	private static final Map<String, Template> TEMPLATES = new ConcurrentHashMap<String, Template>();

	/**
	 * 初始化模板入口
	 * @author zhang_zg
	 */
	public static void load() {
		String tplDir = FTL.class.getClassLoader().getResource("/").getPath() + TEMPLATE_DIR_NAME;
		templateDir = tplDir;
		ftlFiles = new File(templateDir);
		// 初始化Configuration
		try {
			cfg.setDirectoryForTemplateLoading(ftlFiles);
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			cfg.setLogTemplateExceptions(false);
		} catch (Exception ex) {
			LOG.error("init Configuration error, template directory is '{}'.", templateDir, ex);
		}

		doLoad();
	}

	/**
	 * 加载模板数据
	 * @author zhang_zg
	 */
	private static void doLoad() {
		fileList.clear();
		listFiles(ftlFiles.listFiles());

		for (File file : fileList) {
			String absPath = file.getAbsolutePath();
			if (absPath.endsWith("ftl") || absPath.endsWith("ftlh")) {
				try {
					String relPath = absPath.substring(templateDir.length());
					Template template = cfg.getTemplate(relPath);
					if (relPath.startsWith("/")) {
						relPath = relPath.substring(1);
					}
					TEMPLATES.put(relPath, template);
				} catch (Exception ex) {
					LOG.error("init template '{}' error.", absPath, ex);
				}
			}
		}
		LOG.info("{} of templates init sucesss.", TEMPLATES.size());
	}

	/**
	 * 递归获取子文件夹文件
	 * @author zhang_zg
	 * @param files
	 */
	private static void listFiles(File[] files) {
		for (File file : files) {
			if (file.isDirectory()) {
				listFiles(file.listFiles());
			} else {
				fileList.add(file);
			}
		}
	}

	/**
	 * 获取渲染后的模板文件
	 * @author zhang_zg
	 * @param tplPath 模板的相对路径 即：templateDir 后面的路径
	 * @param data 需要渲染的数据
	 */
	public static String format(String tplPath, Map<String, Object> data) {
		if (!TEMPLATES.containsKey(tplPath)) {
			throw new IllegalArgumentException(tplPath + " not found!");
		}

		String outString = null;
		Template template = TEMPLATES.get(tplPath);

		Writer out = new StringWriter();
		try {
			template.process(data, out);
			outString = out.toString();
		} catch (Exception ex) {
			LOG.error("template of '{}' render error!", tplPath, ex);
			throw new RuntimeException(ex);
		} finally {
			try {
				out.close();
			} catch (IOException ex) {
				LOG.error("StringWriter close error", ex);
				throw new RuntimeException(ex);
			}
		}

		return outString;
	}
}
