package com.jy.common;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jy.csaction.ExeclRead;
import com.jy.model.ShippingOrder;
import com.jy.model.User;
import com.jy.service.OrderInfoService;
import com.jy.thread.ShipingUpdateAging;

/**
 * @author zzp
 */

public class OrderExcelForPOI {
    // private ApplicationContext ac;
    private OrderInfoService orderInfoService;
    private JSONObject jsonObject = new JSONObject();// 存放信息
    private JSONArray jsonArray = new JSONArray();// 存放list 集合
    private boolean b = true;
    private String business_type_name = "城际";
    /*
	 * private static ApplicationContext ac = new
	 * ClassPathXmlApplicationContext( new String[] {
	 * "classpath:mybatis-config.xml", "classpath:spring.xml" });
	 */

    public OrderExcelForPOI() {
        super();
    }

    public OrderExcelForPOI(OrderInfoService orderInfoService) {
        super();
        this.orderInfoService = orderInfoService;
    }

    public String impExcel(String execelFile, User user, String pid)
            throws Exception {
		/*
		 * OrderInfoServiceImpl = (OrderInfoServiceImpl) ac
		 * .getBean("OrderInfoService");
		 */
        ShippingOrder tli;
        JSONObject listJson;
        // OrderCustom oc=OrderInfoServiceImpl.getUpdateOrderCustom(pid);
        List<ShippingOrder> tlist = new ArrayList<ShippingOrder>();
        File file = new File(execelFile);
        String[][] result = ExeclRead.getData(file, 1);
        int rowLength = result.length;
        for (int i = 0; i < rowLength; i++) {
            tli = new ShippingOrder();
            listJson = new JSONObject();
            // ----------------------循环插入
            for (int j = 0; j < result[i].length; j++) {
                // 货物编号
                if (j == 0) {
                    tli.setShiping_order_num(result[i][j]);
					/*
					 * int der = OrderInfoServiceImpl.getAorder(result[i][j]);
					 * if (der > 0) { if (i > 0) { order_num.append("," +
					 * result[i][j]); } else { order_num.append(result[i][j]); }
					 * // tli.setShiping_order_num(""); break; } else {
					 * tli.setShiping_order_num(result[i][j]);
					 * 
					 * System.out.println(tli.getShiping_order_num()); }
					 */
                } else if (j == 1) {
                    // 受理机构
                    // tli.setSend_mechanism(result[i][j]);
                    if (user != null) {
                        if (user.getFlag().equals("3") || user.getFlag().equals("4")) {

                            tli.setSend_mechanism(user.getCustomer_name());
                        } else {
                            // 受理机构
                            tli.setSend_mechanism(result[i][j]);
                        }
                    }

                } else if (j == 2) {
                    // 出货订单号
                    tli.setShiping_order_goid(result[i][j]);
                } else if (j == 3) {
                    // 起始地
                    tli.setSend_station(result[i][j]);
                } else if (j == 4) {// 起运时间
                    b = ValidateUntil.isValidDate(result[i][j]);
                    if (b) tli.setSend_time(result[i][j]);
                    else {
                        validate(result[i][0], result[i][2], "起运时间格式不对");
                        break;
                    }

                } else if (j == 5) {
                    // 终到位置所在城市
                    tli.setEnd_address(result[i][j]);
                } else if (j == 6) {
                    // 终到机构 详细
                    tli.setEnd_mechanism(result[i][j]);
                } else if (j == 7) {
                    // 月结编号
                    tli.setShiping_yueid(result[i][j]);
                } else if (j == 8) {
                    // 发货客户名称
                    tli.setCustom_name(result[i][j]);
                } else if (j == 9) {
                    // 发货联系人
                    tli.setCustom_contact(result[i][j]);
                } else if (j == 10) {// 代收货款
                    // 发货联系人电话
                    tli.setCustom_tel(result[i][j]);
                } else if (j == 11) { //收货客户名称
                    tli.setReceipt(result[i][j]);
                } else if (j == 12) {
                    // 收货联系人
                    tli.setReceipt_name(result[i][j]);
                } else if (j == 13) {

                    // 收货联系人电话
                    tli.setReceipt_tel(result[i][j]);
                } else if (j == 14) {
                    // 物品名称
                    tli.setGoods_name(result[i][j]);
                } else if (j == 15) {
                    b = ValidateUntil.isNumber(result[i][j]);
                    if (b)// 回单份数
                        tli.setIs_recept(result[i][j]);
                    else {
                        validate(result[i][0], result[i][2], "回单份数不为数字");
                        break;
                    }
                } else if (j == 16) {//代收货款
                    if (result[i][j] == null || result[i][j].equals(""))
                        tli.setTrade_agency("0");
                    else {
                        b = ValidateUntil.isNumber(result[i][j]);
                        if (b) tli.setTrade_agency(result[i][j]);

                        else {
                            validate(result[i][0], result[i][2], "代收货款不为数字");
                            break;
                        }
                    }

                } else if (j == 17) {// 总件数
                    if (result[i][j] == null || result[i][j].equals(""))
                        tli.setGoods_num(0);
                    else {
                        b = ValidateUntil.isNumber(result[i][j]);
                        if (b)
                            tli.setGoods_num(Integer.parseInt(result[i][j]));
                        else {
                            validate(result[i][0], result[i][2], "总件数不为数字");
                            break;
                        }
                    }
                } else if (j == 18) {// 总重量
                    if (result[i][j] == null || result[i][j].equals(""))
                        tli.setGoods_weight("0");
                    else {
                        b = ValidateUntil.isNumber(result[i][j]);
                        if (b)
                            tli.setGoods_weight(result[i][j]);
                        else {
                            validate(result[i][0], result[i][2], "总重量不为数字");
                            break;
                        }
                    }

                } else if (j == 19) {// 总体积
                    if (result[i][j] == null || result[i][j].equals(""))
                        tli.setGoods_vol("0");
                    else {
                        b = ValidateUntil.isNumber(result[i][j]);
                        if (b)
                            tli.setGoods_vol(result[i][j]);
                        else {
                            validate(result[i][0], result[i][2], "总体积不为数字");
                            break;
                        }
                    }

                } else if (j == 20) {//配送费
                    if (result[i][j] == null || result[i][j].equals(""))
                        tli.setDeliver_fee("0");
                    else {
                        b = ValidateUntil.isNumber(result[i][j]);
                        if (b) tli.setDeliver_fee(result[i][j]);
                        else {
                            validate(result[i][0], result[i][2], "配送费不为数字");
                            break;
                        }
                    }
                } else if (j == 21) {// 上楼费
                    if (result[i][j] == null || result[i][j].equals(""))
                        tli.setUpstairs_fee("0");
                    else {
                        b = ValidateUntil.isNumber(result[i][j]);
                        if (b) tli.setUpstairs_fee(result[i][j]);
                        else {
                            validate(result[i][0], result[i][2], "上楼费不为数字");
                            break;
                        }
                    }
                } else if (j == 22) {// 附加费
                    if (result[i][j] != null || result[i][j].equals(""))
                        tli.setAdded_fee("0");
                    else {
                        b = ValidateUntil.isNumber(result[i][j]);
                        if (b) tli.setAdded_fee(result[i][j]);
                        else {
                            validate(result[i][0], result[i][2], "附加费不为数字");
                            break;
                        }
                    }
                } else if (j == 23) {// 附加费
                    tli.setRemarks(result[i][j]);
                } else if (j == 24) {
                    String business_type_ = result[i][j];
                    int business_type = (business_type_name.equals(business_type_)) ? 1 : 0;
                    tli.setBusiness_type(business_type);
                }
            }
            if (tli.getShiping_order_num() != "" && b == true) {
                tli.setShipping_order(user.getUsername());
                tli.setCreat_type(1);
                tli.setShipping_order_state(0);
                tli.setShiping_order_id(UUIDUtils.getUUID());
                tli.setUpdatetime(DateFormat.getDateTimeInstance().format(
                        new Date()));
                tli.setOrder_time(DateFormat.getDateTimeInstance().format(
                        new Date()));// 制单时间

                int bi = orderInfoService.bGetOrderCount(tli.getShiping_order_num(), tli.getShiping_order_goid());
                if (bi == 0) {
                    tlist.add(tli);
                } else {
                    validate(tli.getShiping_order_num(), tli.getShiping_order_goid(), "数据库中已存在");
                }


            }
            b = true;
        }
        // 筛选订单
        tlist = removeDuplicate(tlist);
        //List<ShippingOrder> listdata = orderInfoService.getAorder();
        //tlist = removeDataDuplicate(tlist, listdata);

        int js = tlist.size();// 插入成功条数
        int zs = rowLength;// 总数

        // String rb = zs + "#" + js + "#" + order_num.toString();
		/*
		 * for (int i = 0; i < tlist.size(); i++) { System.out.println("订单号：" +
		 * tlist.get(i).getShiping_order_num()); }
		 */
        int num = 0;
		/*
		 * for (ShippingOrder shippingOrder : tlist) {
		 * System.out.println(shippingOrder.getShiping_order_id()); }
		 */
        if (tlist.size() > 0) {
            num = orderInfoService.saveimp(tlist);
        }
        if (num != 0) {
            try {
                new Thread(new ShipingUpdateAging(tlist, orderInfoService))
                        .start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        jsonObject.put("js", js);
        jsonObject.put("zs", zs);
        jsonObject.put("num", num);
        jsonObject.put("list", jsonArray);
        return jsonObject.toString();
		/*
		 * DriverToOrder dto; //分配给司机订单 List<DriverToOrder> dtolist=new
		 * ArrayList<DriverToOrder>(); //获取司机信息 List<Driver>
		 * driverlist=OrderInfoServiceImpl.getDriverInfo();
		 * System.out.println("start==分配订单给司机中。。。。。。");
		 * 
		 * 
		 * System.out.println(
		 * "-------------------标准时效 ,,,,,,,日志表--------------------------"); for
		 * (Driver driver : driverlist) { for (ShippingOrder shippingOrder :
		 * tlist) { if((driver.getDriver_address()).equals(shippingOrder
		 * .getEnd_address())){ dto=new DriverToOrder();
		 * dto.setDriver_id(driver.getDriver_id());
		 * dto.setId(UUIDUtils.getUUID());
		 * dto.setOrder_id(shippingOrder.getShiping_order_id());
		 * dtolist.add(dto); } } } try{ if(dtolist.size()>0){
		 * 
		 * int insertnum=OrderInfoServiceImpl.saveDriverToOrder(dtolist);
		 * OrderInfoServiceImpl.upfenpeiOrder(dtolist); } }catch (Exception e) {
		 * // TODO: handle exception
		 * System.out.println("订单司机关联添加异常："+e.getMessage()); }
		 * System.err.println("end==分配订单结束");
		 * 
		 * return rb; }
		 */

    }

    public List<ShippingOrder> removeDuplicate(List<ShippingOrder> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getShiping_order_num()
                        .equals(list.get(i).getShiping_order_num())
                        && list.get(j).getShiping_order_goid()
                        .equals(list.get(i).getShiping_order_goid())) {

                    validate(list.get(j)
                            .getShiping_order_num(), list.get(j)
                            .getShiping_order_goid(), "Excel中订单号和出货单号相同");

					/*
					 * if (order_num.length() > 0) { order_num.append("," +
					 * list.get(j).getShiping_order_num()); } else {
					 * order_num.append(list.get(j).getShiping_order_num()); }
					 */
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public List<ShippingOrder> removeDataDuplicate(List<ShippingOrder> list,
                                                   List<ShippingOrder> listdata) {
        for (int i = 0; i < listdata.size(); i++) {
            // System.out.println("循环次数："+i+"外层顶大号："+listdata.get(i).getShiping_order_num());
            for (int j = 0; j < list.size(); j++) {
                // System.out.println("内层订单号："+list.get(j).getShiping_order_num());
                if (list.get(j).getShiping_order_num()
                        .equals(listdata.get(i).getShiping_order_num())
                        && list.get(j)
                        .getShiping_order_goid()
                        .equals(listdata.get(i).getShiping_order_goid())) {
                    // System.out.println("相同订单号："+list.get(j).getShiping_order_num());
                    validate(list.get(j)
                            .getShiping_order_num(), list.get(j)
                            .getShiping_order_goid(), "数据库中已存在");
					
					/*
					 * if (order_num.length() > 0) { order_num.append("," +
					 * list.get(j).getShiping_order_num()); } else {
					 * order_num.append(list.get(j).getShiping_order_num()); }
					 */
                    list.remove(j);
                }
            }
        }
        return list;
    }

    public void validate(String shiping_order_num, String shiping_order_goid, String message) {
        JSONObject json = new JSONObject();
        json.put("shiping_order_num", shiping_order_num);
        json.put("shiping_order_goid", shiping_order_goid);
        json.put("message", message);
        jsonArray.add(json);
    }
}
