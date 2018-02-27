package com.jy.thread;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jy.common.UUIDUtils;
import com.jy.model.Driver;
import com.jy.model.DriverToOrder;
import com.jy.model.ShippingOrder;
import com.jy.service.impl.OrderInfoServiceImpl;

public class InsertShipingOrderThread implements Runnable{
	private ShippingOrder shipp;
	private DriverToOrder dto;
    private OrderInfoServiceImpl OrderInfoServiceImpl;

    public InsertShipingOrderThread() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InsertShipingOrderThread(ShippingOrder ship,OrderInfoServiceImpl o) {
		this.shipp=ship;
		this.OrderInfoServiceImpl=o;
		
	}
    public void run() {
		
		
		//分配给司机订单
		List<DriverToOrder> dtolist=new ArrayList<DriverToOrder>();
		//获取司机信息
		List<Driver> driverlist=OrderInfoServiceImpl.getDriverInfo();
		System.out.println("start==分配订单给司机中。。。。。。");
		
		//new Thread(new ShipingUpdateAging(tlist)).start();
		
		System.out.println("-------------------标准时效 ,,,,,,,日志表--------------------------");
		for (Driver driver : driverlist) {
			//for (ShippingOrder shippingOrder : tlist) {
				if((driver.getDriver_address()).equals(shipp.getEnd_address())){
				
					dto=new DriverToOrder();
					dto.setDriver_id(driver.getDriver_id());
					dto.setId(UUIDUtils.getUUID());
					dto.setOrder_id(shipp.getShiping_order_id());
					dtolist.add(dto);
				}
			}
		//}
		try{
			if(dtolist.size()>0){
				System.out.println(dtolist.size()+"1");
				int insertnum=OrderInfoServiceImpl.saveDriverToOrder(dtolist);
				OrderInfoServiceImpl.upfenpeiOrder(dtolist);
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("订单司机关联添加异常："+e.getMessage());
		}
		System.err.println("end==分配订单结束");
		
	}

}
