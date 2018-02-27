package com.jy.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jy.model.Aging;
import com.jy.model.DeliveryCustomer;
import com.jy.service.impl.OrderInfoServiceImpl;
/**
 * 配送时效
 * */
public class AgingExcelInfo {
	private OrderInfoServiceImpl OrderInfoServiceImpl;
	StringBuffer order_num = new StringBuffer();
	private static ApplicationContext ac=new ClassPathXmlApplicationContext(new String[] {"classpath:mybatis-config.xml","classpath:spring.xml"});
	public String impExcel(String execelFile, String usersname, String pid)
			throws Exception {
		
		OrderInfoServiceImpl = (OrderInfoServiceImpl) ac.getBean("OrderInfoService");
		Aging tli;
		// OrderCustom oc=OrderInfoServiceImpl.getUpdateOrderCustom(pid);
		List<Aging> tlist = new ArrayList<Aging>();
		File file = new File(execelFile);
		String[][] result = getData(file, 1);
		int rowLength = result.length;
		for (int i = 0; i < rowLength; i++) {
			tli = new Aging();
			// ----------------------循环插入
			for (int j = 0; j < result[i].length; j++) {
				// 货物编号
				if (j == 0) {
					tli.setAging_id(UUIDUtils.getUUID());
				} else if (j == 1) {
					// 发货客户名称
					tli.setAging_countyname(result[i][j]);

				}else if(j==2){
					//省
					tli.setAging_provincename(result[i][j]);
				} 				
				else if (j == 3) {
					// 市
					tli.setAging_cityname(result[i][j]);
				} else if (j == 4) {
					// 县
					tli.setAging_countyname(result[i][j]);
				} else if (j == 5) {
					// 标准时效
					tli.setAging_time(result[i][j]);
				} else if (j == 6) {
					// 操作人
					tli.setAging_operator(result[i][j]);
				} else if (j == 7) {
					// 操作日期
					tli.setAging_operator_date(result[i][j]);
				} else if (j == 8) {
					// 配送时效
					tli.setAging_day(result[i][j]);
				} 
					
				} 
			
			/*if (tli.getShiping_order_num() != "") {
				tli.setShipping_order(usersname);
				tli.setCreat_type(1);
				tli.setShipping_order_state(0);
				tli.setShiping_order_id(UUIDUtils.getUUID());
				tli.setUpdatetime(DateFormat.getDateTimeInstance().format(
						new Date()));
				tli.setOrder_time(DateFormat.getDateTimeInstance().format(
						new Date()));// 制单时间
				
				//System.out.println(tli.getShiping_yueid()+"???????????????????????");
				
			}*/
		tlist.add(tli);
		}
		//筛选订单
		tlist=removeDuplicate(tlist);
		//List<ShippingOrder> listdata=OrderInfoServiceImpl.getAorder();
		//tlist=removeDataDuplicate(tlist,listdata);
		int js = tlist.size();// 插入成功条数
		int zs = rowLength;// 总数

		String rb = zs + "#" + js + "#" + order_num.toString();
		/*for (int i = 0; i < tlist.size(); i++) {
			System.out.println("订单号：" + tlist.get(i).getShiping_order_num());
		}*/
		int num = 0;
		/*for (ShippingOrder shippingOrder : tlist) {
			System.out.println(shippingOrder.getShiping_order_id());
		}*/
		if (tlist.size() > 0) {
		//	num = OrderInfoServiceImpl.saveimp(tlist);
		}
	
			return rb;
	
	}
	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String[][] getData(File file, int ignoreRows)
			throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				file));
		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st = wb.getSheetAt(sheetIndex);
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
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm:ss").format(date);
								} else {
									value = "";
								}
							} else {
								/*
								 * value = new DecimalFormat("0.00").format(cell
								 * .getNumericCellValue());
								 */
								cell.setCellType(1);// 设置为String
								value = cell.getStringCellValue().trim();
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
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
					} else {

					}
					if (columnIndex == 1
							&& ("".equals(value.trim()) || value==null)) {
						hasValue = false;
						break;
					} else {
						values[columnIndex] = rightTrim(value);
						hasValue = true;
					}
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;
	}

	/**
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 处理后的字符串
	 */

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

	
	public  List<Aging> removeDuplicate(List<Aging> list) {
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = list.size() - 1; j > i; j--) {
			/*	if (list.get(j).getShiping_order_num().equals(list.get(i).getShiping_order_num())) {
					if(order_num.length()>0){
						order_num.append("," + list.get(j).getShiping_order_num());
					}else{
						order_num.append(list.get(j).getShiping_order_num());
					}
					list.remove(j);
				}*/
			}
		}
		return list;
	}
	public  List<Aging> removeDataDuplicate(List<Aging> list,List<Aging> listdata) {
		for (int i = 0; i < listdata.size(); i++) {
/*			System.out.println("循环次数："+i+"外层顶大号："+listdata.get(i).getShiping_order_num());
*/			for (int j=0;j<list.size();j++) {
/*				System.out.println("内层订单号："+list.get(j).getShiping_order_num());
*/				/*if (list.get(j).getShiping_order_num().equals(listdata.get(i).getShiping_order_num())) {
					System.out.println("相同订单号："+list.get(j).getShiping_order_num());
					if(order_num.length()>0){
						order_num.append("," + list.get(j).getShiping_order_num());
					}else{
						order_num.append(list.get(j).getShiping_order_num());
					}
					list.remove(j);
				}*/
			}
		}
		return list;
	}
}
