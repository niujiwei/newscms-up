package com.jy.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.City_info;

public interface CityService {
	/**
	 * 城市管理 查询总数
	 * 
	 * @param cityname
	 * @param cityzm
	 * @return
	 */
	int getCity(String cityname, String cityzm);

	/**
	 * 城市管理 查询信息
	 * 
	 * @param rows
	 * @param page
	 * @param cityname
	 * @param cityzm
	 * @return
	 */
	List<City_info> getCityInfo(int rows, int page, String cityname,
			String cityzm);

	/**
	 * 城市管理 删除城市信息
	 * 
	 * @param del
	 * @return
	 */
	public int deleteCity(String[] del);

	/**
	 * 城市管理 城市信息添加
	 * 
	 * @param d
	 * @return
	 */
	public int saveCity(City_info d);

	/**
	 * 城市管理 修改查询
	 * 
	 * @param citycode
	 * @return
	 */
	public City_info getUpdateCity(int citycode);

	/**
	 * 城市管理 城市修改
	 * 
	 * @param d
	 * @return
	 */
	public int updateCity(City_info d);

	/**
	 * 获取省份
	 * 
	 * @return
	 */
	String getcitytype1();

	/**
	 * 获取城市
	 * 
	 * @param id
	 * @return
	 */
	String getcitytype2(String id);

	/**
	 * 获取区县
	 * 
	 * @param id
	 * @return
	 */
	List<City_info> getcitytype3(String id);

	/**
	 * 添加
	 * 
	 * @param city_info
	 * @return
	 */
	boolean addcity(City_info city_info);

	/**
	 * 更新
	 * 
	 * @param city_info
	 * @return
	 */
	boolean updatecity(City_info city_info);

	/**
	 * 删除
	 * 
	 * @param city_info
	 * @return
	 */
	boolean deletecity(City_info city_info);

	/**
	 * 删除区县
	 * 
	 * @param del
	 * @return
	 */
	boolean deleteqx(String[] del);

	/**
	 * 获取要删除的节点下边是否有子节点
	 * 
	 * @param citycode
	 * @return
	 */
	int getdeletecount(int citycode);
}
