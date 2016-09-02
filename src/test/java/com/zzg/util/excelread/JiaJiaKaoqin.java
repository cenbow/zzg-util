package com.zzg.util.excelread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zzg.util.date.DateUtil;
import com.zzg.util.excel.ExcelUtil;
import com.zzg.util.excel.Model;

public class JiaJiaKaoqin {
	private static final String KAOQIN_DIR = "/Users/zhangzhiguang/Documents/jiajia";

	private static final String START = "09:00"; // 上班时间
	private static final String END = "18:00"; // 下班时间

	private static Map<String, Date[]> kqMap = new LinkedHashMap<String, Date[]>();

	private void readExcel(String dateString) throws Exception {
		File fileDir = new File(KAOQIN_DIR + File.separator + dateString);
		File[] files = fileDir.listFiles();
		for (File file : files) {
			if (file.getName().contains("考勤")) {
				continue;
			}
			System.out.println("开始处理: " + file.getAbsolutePath());

			FileInputStream fin = new FileInputStream(file);
			HSSFWorkbook work = new HSSFWorkbook(fin);
			fin.close();

			HSSFSheet sheet = work.getSheetAt(0);
			int rowCount = sheet.getLastRowNum();

			for (int i = 1; i <= rowCount; i++) {
				HSSFRow row = sheet.getRow(i);
				try {
					this.parseRow(row);
				} catch (Exception ex) {
					System.out.println("第" + (i + 1) + "行解析出错！" + ex);
				}
			}
		}
	}

	/**
	 * 汇总考勤
	 * @author zhang_zg
	 * @throws ParseException 
	 */
	private void summaryKaoqin(String dateString) throws Exception {
		String[] headers = { "姓名", "打卡时间", "备注" };
		Collection<Object> dataset = new ArrayList<Object>();

		for (String staffName : kqMap.keySet()) {
			Date[] date = kqMap.get(staffName);
			String am = date[0] == null ? "" : DateUtil.format(date[0], "yyyy-MM-dd HH:mm:ss");
			String pm = date[1] == null ? "" : DateUtil.format(date[1], "HH:mm:ss");

			Date start = DateUtil.parse(dateString + START, "yyyyMMddHH:mm");
			Date end = DateUtil.parse(dateString + END, "yyyyMMddHH:mm");

			String remark = "";
			if (date[0] == null || date[0].compareTo(start) == 1) {
				remark += "迟到 ";
			}

			if (date[1] == null || date[1].compareTo(end) == -1) {
				remark += "早退 ";
			}

			System.out.println(staffName + "\t" + am + "~" + pm + "\t" + remark);

			dataset.add(new Model(staffName, am + "~" + pm, remark));
		}

		File f = new File(KAOQIN_DIR + File.separator + dateString + File.separator + dateString + "考勤.xls");
		OutputStream out = new FileOutputStream(f);
		ExcelUtil.exportExcel(headers, dataset, out);
		out.close();
	}

	/**
	 * 解析excel的每一行
	 * @author zhang_zg
	 * @param row
	 */
	private void parseRow(HSSFRow row) {
		if (row != null) {
			List<String> list = new ArrayList<String>();
			for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
				if (row.getCell(i) != null) {
					list.add(row.getCell(i).toString());
				}
			}

			if (list.size() >= 2) {
				this.recordKaoqin(list.get(0), getDate(list.get(1)));
			}

			if (list.size() >= 4) {
				this.recordKaoqin(list.get(2), getDate(list.get(3)));
			}
		}
	}

	/**
	 * 计算考勤
	 * @author zhang_zg
	 * @param staffName
	 * @param clockTime
	 */
	private void recordKaoqin(String staffName, Date clockTime) {
		if (StringUtils.isNotBlank(staffName)) {
			if (kqMap.containsKey(staffName)) {
				if (clockTime != null) {
					Date[] temp = kqMap.get(staffName);

					// 上班打卡时间取最小值
					if (temp[0] == null) {
						temp[0] = clockTime;
					}
					if (temp[0].compareTo(clockTime) == 1) {
						temp[0] = clockTime;
					}
					// 下班打卡时间取最大值

					if (temp[1] == null) {
						temp[1] = clockTime;
					}
					if (temp[1].compareTo(clockTime) == -1) {
						temp[1] = clockTime;
					}
				}
			} else {
				kqMap.put(staffName, new Date[] { clockTime, null });
			}
		}
	}

	private static Date getDate(String dateString) {
		if (StringUtils.isBlank(dateString)) {
			return null;
		}

		Date date = null;
		try {
			dateString = dateString.replace("上午", "AM").replace("下午", "PM");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d a hh:mm:ss", Locale.ENGLISH);
			date = sdf.parse(dateString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static void main(String[] args) throws Exception {
		JiaJiaKaoqin kaoqin = new JiaJiaKaoqin();
		kaoqin.readExcel("20160623");
		kaoqin.summaryKaoqin("20160623");
	}

}
