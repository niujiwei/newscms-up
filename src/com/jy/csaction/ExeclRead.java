package com.jy.csaction;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExeclRead {
	;
	public static void main(String[] args) throws Exception {

		File file = new File("D:\\新建文件夹\\11-23DMS导入(1).xls");
		FileInputStream is = new FileInputStream(file); // 文件流
		Workbook workbook = WorkbookFactory.create(is);
		int sheetCount = workbook.getNumberOfSheets();

		// File file = new File("C:\\test.xls");
		String[][] result = getData(file, 1);

		int rowLength = result.length;

		for (int i = 0; i < rowLength; i++) {

			for (int j = 0; j < result[i].length; j++) {

				System.out.print(result[i][j] + "\t\t");

			}

			System.out.println(i);

		}

	}

	/**
	 * 
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * 
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * 
	 * @return 读出的Excel中数据的内容
	 * 
	 * @throws FileNotFoundException
	 * 
	 * @throws IOException
	 * 
	 */
	public static String[][] getData(File file, int ignoreRows)
			throws FileNotFoundException, IOException {
		int rowSize = 0;
		// 打开HSSFWorkbook
		Workbook wb = null;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				file));
		List<String[]> result = new ArrayList<String[]>();
		/*
		 * // String outputFile = output2File(in);
		 * 
		 * try { in = new FileInputStream(file); wb = new XSSFWorkbook(in);
		 * result = getXSSFResult(wb, ignoreRows); } catch (Exception ex) { in =
		 * new FileInputStream(file); wb = new HSSFWorkbook(in); result =
		 * getHSSFResult(wb, ignoreRows); }
		 */
		// 哈哈，原来已经提供了一个很优雅的方法
		try {
			wb = WorkbookFactory.create(in);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(wb instanceof XSSFWorkbook);
		if (wb instanceof XSSFWorkbook) {
			// wb = new XSSFWorkbook(in);
			result = getXSSFResult(wb, ignoreRows);
			System.out.println("daorukasih1" + result.size());
		} else if (wb instanceof HSSFWorkbook) {

			// wb = new HSSFWorkbook(in);
			result = getHSSFResult(wb, ignoreRows);
			System.out.println("daorukasih1" + result.size());
		}

		in.close();

		String[][] returnArray = new String[result.size()][rowSize];

		for (int i = 0; i < returnArray.length; i++) {

			returnArray[i] = (String[]) result.get(i);

		}

		return returnArray;

	}

	private static List<String[]> getXSSFResult(Workbook wb, int ignoreRows) {
		List<String[]> result = new ArrayList<String[]>();
		XSSFCell cell = null;
		int rowSize = 0;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet st = (XSSFSheet) wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				XSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						// 注意：一定要设成这个，否则可能会出现乱码

						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;

						case XSSFCell.CELL_TYPE_FORMULA:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							try {
								value = String.valueOf(cell
										.getStringCellValue());
							} catch (IllegalStateException e) {
								value = String.valueOf(new DecimalFormat(
										"###.####").format(cell
										.getNumericCellValue()));
							}
							// 导入时如果为公式生成的数据则无值
							/*
							 * if (!cell.getStringCellValue().equals("")) {
							 * value = cell.getStringCellValue(); } else { value
							 * = cell.getNumericCellValue() + ""; }
							 */
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							if (DateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd")
											.format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("###.####")
										.format(cell.getNumericCellValue());
							}
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							break;
						case XSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y"
									: "N");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		return result;
	}

	private static List<String[]> getHSSFResult(Workbook wb, int ignoreRows) {
		List<String[]> result = new ArrayList<String[]>();
		HSSFCell cell = null;
		int rowSize = 0;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st = (HSSFSheet) wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						// 注意：一定要设成这个，否则可能会出现乱码

						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd")
											.format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("###.####")
										.format(cell.getNumericCellValue());
								// value=cell.getStringCellValue();
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							cell.setCellType(Cell.CELL_TYPE_STRING);
							try {
								value = String.valueOf(cell
										.getStringCellValue());
							} catch (IllegalStateException e) {
								value = String.valueOf(new DecimalFormat(
										"###.####").format(cell
										.getNumericCellValue()));
							}
							/*
							 * // 导入时如果为公式生成的数据则无值 if
							 * (!cell.getStringCellValue().equals("")) { value =
							 * cell.getStringCellValue(); } else { value =
							 * cell.getNumericCellValue() + ""; }
							 */
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y"
									: "N");
							break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
						break;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 处理后的字符串
	 * 
	 */

	public static String rightTrim(String str) {

		if (str == null) {

			return "";

		}
		str = str.trim();
		str = str.replaceAll(" ", "");
		str = str.replaceAll("\\s*", "");

		int length = str.length();

		for (int i = length - 1; i >= 0; i--) {

			if (str.charAt(i) != 0x20) {

				break;

			}

			length--;

		}

		return str.substring(0, length);
	}

	public static String[][] getImportFileData(String filePath,Integer ignoreRows) throws IOException {

		File file = new File(filePath);

		return ExeclRead.getData(file, ignoreRows);
	}
}
