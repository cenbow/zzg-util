package com.zzg.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author zhang_zg
 * @version 1.0
 * @created 2016年3月10日 下午1:32:36
 */
public class TestExportLinkedMap {
	public static void main(String[] args) throws IOException {

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("name", "王刚");
		map.put("age", 24);
		map.put("sex", "男");
		Map<String, Object> map2 = new LinkedHashMap<String, Object>();
		map2.put("name", "sargeras");
		map2.put("age", 100);
		map2.put("sex", "女");
		list.add(map);
		list.add(map2);

		File f = new File("/Users/zhangzhiguang/Desktop/test.xls");
		OutputStream out = new FileOutputStream(f);
		ExcelUtil.exportExcel(new String[] { "姓名", "年龄", "性别" }, list, out);
		out.close();
	}
}
