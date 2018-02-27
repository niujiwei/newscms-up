package com.jy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jy.model.City_info;

public interface CityDao {

	// 城市管理 查询总数
	int getCity(String cityname, String cityzm);

	// 城市管理 查询信息
	List<City_info> getCityInfo(int rows, int page, String cityname,
			String cityzm);

	// 城市管理 删除城市信息
	public int deleteCity(@Param("array") String[] del);

	// 城市管理 城市信息添加
	public int saveCity(City_info d);

	// 城市管理 修改查询
	public City_info getUpdateCity(int citycode);

	// 城市管理 城市修改
	public int updateCity(City_info d);

	// 获取省份
	List<City_info> getcitytype1();

	// 获取城市
	List<City_info> getcitytype2(String id);

	// 获取区县
	List<City_info> getcitytype3(String id);

	// 添加城市
	int addcity(City_info city_info);

	// 更新
	int updatecity(City_info city_info);

	// 删除
	int deletecity(City_info city_info);

	// 删除区县
	int deleteqx(String[] del);

	// 返回要删除的节点下边是否有子节点
	int getdeletecount(int citycode);
}
