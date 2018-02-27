package com.jy.common;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportUtils {
	/**
	 * 设置sheet表头信息
	 * 
	 * @param headers
	 * @param title
	 */
	public static HSSFSheet sheet;

	public static void outputHeaders(String[] headers, HSSFWorkbook workbook,
			String title) {
		sheet = workbook.createSheet(title);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 在第一行创建表头
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			// 设置列宽
			sheet.setColumnWidth(i, 4000);
			// 对第一行创建列，对每一列进行赋值
			row.createCell(i).setCellValue(headers[i]);
		}
	}

	/**
	 * 设置每行数据
	 * 
	 * @param headers
	 * @param colums
	 * @param sheet
	 * @param rowIndex
	 */
	public static void outputColums(String[] headers, List<?> colums,
			HSSFWorkbook workbook, int rowIndex, String title) {

		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();

		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		HSSFRow row;
		int headerSize = headers.length;
		int columnSize = colums.size();
		// 循环多少行
		for (int i = 0; i < colums.size(); i++) {
			// 从rowIndex行开始创建行
			row = sheet.createRow(rowIndex + i);
			Object obj = colums.get(i);
			// 循环多少列
			for (int j = 0; j < headers.length; j++) {
				Object value = getFieldValueByName(headers[j], obj);
				Boolean isNum = false;// data鏄惁涓烘暟鍊煎瀷
				Boolean isInteger = false;// data鏄惁涓烘暣鏁�
				Boolean isPercent = false;// data鏄惁涓虹櫨鍒嗘暟
				if (value != null || "".equals(value)) {
					// 匹配是否是数字
					isNum = value.toString().matches("^(-?\\d+)(\\.\\d+)?$");
					// 是否是整数
					isInteger = value.toString().matches("^[-\\+]?[\\d]*$");
					// 是否有百分号
					isPercent = value.toString().contains("%");
					if (isNum && !isPercent) {
						HSSFDataFormat df = workbook.createDataFormat();
						if (isInteger) {
							style2.setDataFormat(df.getFormat("#,#0.00"));
						} else {
							style2.setDataFormat(df.getFormat("#,##0.0000"));
						}

						row.createCell(j).setCellStyle(style2);

						row.createCell(j).setCellValue(
								Double.parseDouble(value.toString()));

					} else {

						row.createCell(j).setCellStyle(style2);

						row.createCell(j).setCellValue(value.toString());
					}
				} else if (value == null || "".equals(value)) {
					row.createCell(j).setCellValue("");
				}

			}
		}
	}

	/**
	 * 根据对象的属性获取值
	 * 
	 * @param string
	 * @param obj
	 * @return
	 */
	private static Object getFieldValueByName(String fieldName, Object obj) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getter = "get" + firstLetter + fieldName.substring(1);
		try {
			Method method = obj.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(obj, new Object[] {});
			return value;
		} catch (Exception e) {
			System.out.println("属性不存在");
			return null;
		}

	}

	/**
	 * 读取配置文件获取标题和内容
	 * 
	 * @param in
	 * @return
	 */
	public static Map<String, List<String>> getHeadTitle(InputStream in) {
		OrderedProperties prop = new OrderedProperties();
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> headTitle = new ArrayList<String>();
		List<String> fieldName = new ArrayList<String>();

		try {
			prop.load(in);
			Iterator<String> it = prop.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				headTitle.add(key);
				fieldName.add(prop.getProperty(key));
			}
			in.close();
			map.put("headTitle", headTitle);
			map.put("fieldName", fieldName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} // /加载属性列表
		return map;

	}

}
