package com.integrate.admin.module.city.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.integrate.admin.common.AppDbService;
import com.integrate.admin.module.city.model.AreaInfo;
import com.integrate.admin.module.city.model.CityInfo;
import com.integrate.admin.module.city.model.ProvinceInfo;

/**
 * 
 * @author summerao
 *
 */
@Component
public class CityDao {
	@Autowired
	private AppDbService jdbc;
	/**
	 * 获取城市信息
	 * @param cityVersion
	 */
	public List<Map<String, Object>> getCityList(int cityVersion){
		String sql = "select * from t_province_city_area where data_version = ? order by city_pinyin ";
		List<Map<String, Object>> list = jdbc.queryList(sql, cityVersion);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}
	
	/**
	 * 根据code获取城市信息
	 * @param cityVersion
	 */
	public List<Map<String, Object>> getCityInfoByCode(int cityCode,int cityVersion){
		String sql = "select * from t_province_city_area where data_version = ? and city_code = ? ";
		List<Map<String, Object>> list = jdbc.queryList(sql, cityVersion,cityCode);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}
	
	/**
	 * 获取省份信息
	 * @param cityVersion
	 */
	public List<Map<String, Object>> getProvinceList(int cityVersion){
		String sql = "select distinct province_code,province_name,province_pinyin from t_province_city_area where data_version = ? order by province_pinyin";
		List<Map<String, Object>> list = jdbc.queryList(sql, cityVersion);
		if(list==null){
			list = Lists.newArrayList();
		}
		return list;
	}

	/**
	 * 获取城市对应的区域信息
	 * @param cityName
	 */
	public List<AreaInfo> getAreaList(String cityName){
		String sql = "select distinct area_code, area_name from t_province_city_area where city_name=? order by area_code";
		List<Map<String, Object>> list = jdbc.queryList(sql, cityName);
		if(CollectionUtils.isEmpty(list)){
			return Collections.EMPTY_LIST;
		}

		List<AreaInfo> result = Lists.newArrayList();
		if(list !=null && list.size()>0){
			for(Map<String,Object> map :list){
				AreaInfo areaInfo = new AreaInfo((int) map.get("area_code"), (String)map.get("area_name"));
				result.add(areaInfo);
			}
		}

		return result;
	}

	public List<AreaInfo> getAreaList(int cityCode){
		String sql = "select distinct area_code, area_name from t_province_city_area where city_code=? order by area_code";
		List<Map<String, Object>> list = jdbc.queryList(sql, cityCode);
		if(CollectionUtils.isEmpty(list)){
			return Collections.EMPTY_LIST;
		}

		List<AreaInfo> result = Lists.newArrayList();
		if(list !=null && list.size()>0){
			for(Map<String,Object> map :list){
				AreaInfo areaInfo = new AreaInfo((int) map.get("area_code"), (String)map.get("area_name"));
				result.add(areaInfo);
			}
		}

		return result;
	}

	public int getCityCodeByCityName(String cityName) {
		if (StringUtils.isBlank(cityName)) {
			return 0;
		}
		String sql = "SELECT city_code FROM t_province_city_area WHERE city_name=? LIMIT 1";
		return jdbc.queryInt(sql, cityName);
	}

	public String getCityNameByCityCode(int cityCode) {
		if (cityCode < 1) {
			return "";
		}
		String sql = "SELECT city_name FROM t_province_city_area WHERE city_code=? LIMIT 1";
		return jdbc.queryForMap(sql, cityCode).get("city_name")+"";
	}

	public String getAreaNameByAreaCode(int areaCode) {
		if (areaCode < 1) {
			return "";
		}
		String sql = "SELECT area_name FROM t_province_city_area WHERE area_code=? LIMIT 1";
		return jdbc.queryForMap(sql, areaCode).get("area_name")+"";
	}

	public int getCityCodeByAreaCode(int areaCode) {
		if (areaCode < 1) {
			return 0;
		}
		String sql = "SELECT city_code FROM t_province_city_area WHERE area_code=? LIMIT 1";
		return jdbc.queryInt(sql, areaCode);
	}

	public Map<Integer, String> getCityCodeAndNameMap() {
		Map<Integer, String> cityMap = Maps.newHashMap();
		String sql = "SELECT DISTINCT city_code, city_name FROM t_province_city_area ";
		List<Map<String, Object>> list = jdbc.queryList(sql);

		if (CollectionUtils.isNotEmpty(list)) {
			for(Map<String, Object> m : list) {
				cityMap.put((int) m.get("city_code"), String.valueOf(m.get("city_name")));
			}
		}

		return cityMap;
	}

	public Map<Integer, String> getProvinceCodeAndNameMap() {
		Map<Integer, String> provinceMap = Maps.newHashMap();
		String sql = "SELECT DISTINCT province_code, province_name FROM t_province_city_area ";
		List<Map<String, Object>> list = jdbc.queryList(sql);

		if (CollectionUtils.isNotEmpty(list)) {
			for(Map<String, Object> m : list) {
				provinceMap.put((int) m.get("province_code"), String.valueOf(m.get("province_name")));
			}
		}

		return provinceMap;
	}

	public ProvinceInfo getProvinceInfoByCityCode(int cityCode) {
		if (cityCode < 1) {
			return null;
		}
		String sql = "SELECT province_code, province_name FROM t_province_city_area WHERE city_code=? LIMIT 1";
		Map<String, Object> map = jdbc.queryForMap(sql, cityCode);

		return new ProvinceInfo((int) map.get("province_code"), String.valueOf(map.get("province_name")));
	}

	public List<CityInfo> getCityListByProvinceCode(int provinceCode) {
		if (provinceCode < 1) {
			return Collections.EMPTY_LIST;
		}
		String sql = "SELECT DISTINCT city_code, city_name FROM t_province_city_area WHERE province_code=? ";
		List<Map<String, Object>> list = jdbc.queryList(sql, provinceCode);

		if (CollectionUtils.isEmpty(list)) {
			return Collections.EMPTY_LIST;
		}

		List<CityInfo> cityList = Lists.newArrayListWithCapacity(list.size());
		for(Map<String, Object> m : list) {
			cityList.add(new CityInfo((int) m.get("city_code"), String.valueOf(m.get("city_name"))));
		}
		return cityList;
	}

//	//@PostConstruct
//	public void dealData(){
//		String sql = "select * from province_city ";
//		List<Map<String, Object>> list = secondDbService.queryList(sql);
//		String updateSql = " update  province_city set city_pinyin = ? , province_pinyin = ? where city_code = ?";
//		for(Map<String,Object> map:list){
//			String cityName = (String)map.get("city_name");
//			int cityCode= (int)map.get("city_code");
//			String provinceName = (String) map.get("province_name");
//			
//			Pinyin pinyin = Pinyin.getPinyinInstance(cityName.substring(0, 1));
//			String pinyin_name = Character.toString(pinyin.firstLetter);
//			
//			Pinyin pin = Pinyin.getPinyinInstance(provinceName.substring(0, 1));
//			String province_pinyin = Character.toString(pin.firstLetter);
//			secondDbService.update(updateSql, pinyin_name,province_pinyin, cityCode);
//			
//		}
//		
//	}
//	//@PostConstruct
//	public void dealData2(){
//		String sql = "SELECT * FROM city WHERE CODE LIKE '%00' AND NAME NOT LIKE '%省' ";
//		List<Map<String, Object>> list = secondDbService.queryList(sql);
//		String updateSql = " update  province_city set city_code = ? where city_name = ?  ";
//		for(Map<String,Object> map:list){
//			String name = (String)map.get("name");
//			int code = Integer.valueOf(map.get("code").toString());
//			//按name切割.
//			if(!name.contains("-")){
//				continue;
//			}
//			String[] arr = name.split("-");
//			String cityName = arr[1];
//			System.out.println(cityName);
//			secondDbService.update(updateSql, code,cityName);
//		}
//		
//	}
//		public void dealData3(){
//			String sql = "SELECT * FROM province_city WHERE city_name like '%市' ";
//			List<Map<String, Object>> list = secondDbService.queryList(sql);
//			String updateSql = " update  province_city set view_cityname = ? where city_code = ?  ";
//			for(Map<String,Object> map:list){
//				String name = (String)map.get("city_name");
//				int code = Integer.valueOf(map.get("city_code").toString());
//				if(name.endsWith("市")){
//					String viewSingerName = name.substring(0, name.length()-1);
//					secondDbService.update(updateSql, viewSingerName,code);
//				}
//			}
//		}
		
}
