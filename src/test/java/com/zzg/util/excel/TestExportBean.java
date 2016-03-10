package com.zzg.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestExportBean {
    public static void main(String[] args) throws IOException {
        
        String[] headers = {"a","b","c"};
        Collection<Object> dataset=new ArrayList<Object>();
        dataset.add(new Model("a1", "b1", "c1"));
        dataset.add(new Model("a2", "b2", "c2"));
        dataset.add(new Model("a3", "b3", "c3"));
		File f = new File("/Users/zhangzhiguang/Desktop/test.xls");
        OutputStream out =new FileOutputStream(f);
        
        ExcelUtil.exportExcel(headers, dataset, out);
        out.close();

		List<ExcelSheet<Object>> sheets = new ArrayList<ExcelSheet<Object>>();

		ExcelSheet<Object> excelSheet1 = new ExcelSheet<Object>();
		excelSheet1.setSheetName("湖南卫视");
		excelSheet1.setHeaders(headers);
		excelSheet1.setDataset(dataset);
		sheets.add(excelSheet1);

		ExcelSheet<Object> excelSheet2 = new ExcelSheet<Object>();
		excelSheet2.setSheetName("江苏卫视");
		excelSheet2.setHeaders(headers);
		excelSheet2.setDataset(dataset);
		sheets.add(excelSheet2);

		File f2 = new File("/Users/zhangzhiguang/Desktop/test2.xls");
		out = new FileOutputStream(f2);
		ExcelUtil.exportExcel(sheets, out);
    }
}
