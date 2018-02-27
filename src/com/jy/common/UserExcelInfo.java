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

import com.jy.model.User;

public class UserExcelInfo {
	private ApplicationContext ac;
	//private OrderInfoServiceImpl OrderInfoServiceImpl;
	StringBuffer order_num = new StringBuffer();

	public String impExcel(String execelFile, String usersname, String pid)
			throws Exception {
		/*ac = new ClassPathXmlApplicationContext(new String[] {
				"classpath:mybatis-config.xml", "classpath:spring.xml" });*/
		//OrderInfoServiceImpl = (OrderInfoServiceImpl) ac.getBean("OrderInfoService");
		User tli;
		// OrderCustom oc=OrderInfoServiceImpl.getUpdateOrderCustom(pid);
		List<User> tlist = new ArrayList<User>();
		File file = new File(execelFile);
		String[][] result = getData(file, 1);
		int rowLength = result.length;
		for (int i = 0; i < rowLength; i++) {
			tli = new User();
			// ----------------------循环插入
			for (int j = 0; j < result[i].length; j++) {
				// id
				if (j == 0) {
					tli.setId(UUIDUtils.getUUID());
				} else if (j == 1) {
					// 用户名
					tli.setUsername(result[i][j]);

				}else if(j==2){
					//用于密码
					tli.setPassword(result[i][j]);
				} 				
				else if (j == 3) {
					// 真实姓名
					tli.setRealName(result[i][j]);
				} else if (j == 4) {
					// 供应商姓名
					tli.setSuppliers_name(result[i][j]);
				} else if (j == 5) {
					// 部门名称
					tli.setDepartment_name(result[i][j]);
				} else if (j == 6) {
					// 最后登入时间
					tli.setLastTime(result[i][j]);
				} else if (j == 7) {
					// 有效用户
					tli.setState(result[i][j]);
				} else if (j == 8) {
					// 1是司机0是其他
					tli.setFlag(result[i][j]);
				} else if (j == 9) {
					// 创建时间
					tli.setCreatetime(result[i][j]);
				} else if (j == 10) {
					//api id 
					tli.setChannelId(result[i][j]);
				} else if (j == 11) {
					//类型
					tli.setDevice_type(result[i][j]);
				}else if (j == 12) {
					//司机姓名
					tli.setSuppliers_name(result[i][j]);
				}else if (j == 13) {
					//省
					tli.setUser_province(Integer.parseInt(result[i][j]));
				}else if (j == 14) {
					//市
					tli.setUser_city(Integer.parseInt(result[i][j]));
				}else if (j == 15) {
					//县
					tli.setUser_county(Integer.parseInt(result[i][j]));
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

	
	public  List<User> removeDuplicate(List<User> list) {
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
	public  List<User> removeDataDuplicate(List<User> list,List<User> listdata) {
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
