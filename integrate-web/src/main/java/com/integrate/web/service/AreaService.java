package com.integrate.web.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.integrate.common.Constants;
import com.integrate.web.dao.AreaDao;
import com.integrate.web.model.AreaInfo;
import com.integrate.web.model.CityInfo;
import com.integrate.web.model.ProvinceCityAreaInfo;
@Service
public class AreaService {
	private Logger logger = LoggerFactory.getLogger(AreaService.class);
	@Autowired
	private AreaDao areaDao;
	LoadingCache<Integer, List<ProvinceCityAreaInfo>> cityCache = 
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
			new CacheLoader<Integer,  List<ProvinceCityAreaInfo>>(){
				@Override
				public  List<ProvinceCityAreaInfo> load(Integer version) {
					return getProvinceCityAreaInfoList() ;
				}
			});
	
	/**
	 * 获取城市列表
	 * @return
	 */
	public List<ProvinceCityAreaInfo> getAreaList(){
		try {
			List<ProvinceCityAreaInfo> list = null;
			list = cityCache.get(Constants.CITY_VERSION);
			if(list!= null &&  list.size()>0){
				return list;
			}
		} catch (ExecutionException e) {
			logger.warn("get city cache error.e:{}",e);
		}
		return Lists.newArrayList();
	}
	
	
	private List<ProvinceCityAreaInfo> getProvinceCityAreaInfoList(){
		List<ProvinceCityAreaInfo> list = Lists.newArrayList();
		List<Map<String, Object>> cityList = areaDao.getCityList(Constants.CITY_VERSION);
		List<Map<String, Object>> provinceList = areaDao.getProvinceList(Constants.CITY_VERSION);
		List<Map<String, Object>> areaList = areaDao.getAreaList(Constants.CITY_VERSION);
		if(provinceList !=null && provinceList.size()>0){
			for(Map<String,Object> map :provinceList){
				ProvinceCityAreaInfo provinceCityAreaInfo = new ProvinceCityAreaInfo();
				int provinceCode = (int) map.get("province_code");
				provinceCityAreaInfo.setProvinceCode(provinceCode);
				provinceCityAreaInfo.setProvinceName((String)map.get("province_name"));
				List<CityInfo> cityInfoList = Lists.newArrayListWithCapacity(cityList.size());
				for(Map<String,Object> cityMap :cityList){
					//拼城市.
					int cityProvinceCode =(int) cityMap.get("province_code");
					if(provinceCode ==cityProvinceCode ){
						CityInfo cityInfo = new CityInfo();
						int cityCode = (int)cityMap.get("city_code");
						cityInfo.setCityCode(cityCode);
						cityInfo.setViewCityName((String)cityMap.get("view_cityname"));
						//拼区域
						List<AreaInfo> areaInfoList = Lists.newArrayListWithCapacity(areaList.size());
						for(Map<String,Object> areaMap :areaList){
							int areaCityCode = (int) areaMap.get("city_code");
							if(cityCode == areaCityCode){
								AreaInfo areaInfo = new AreaInfo();
								areaInfo.setAreaCode((int)areaMap.get("area_code"));
								areaInfo.setAreaName((String)areaMap.get("area_name"));
								areaInfoList.add(areaInfo);
							}
						}
						cityInfo.setAreaList(areaInfoList);
						cityInfoList.add(cityInfo);
					}
				}
				provinceCityAreaInfo.setCityList(cityInfoList);
				list.add(provinceCityAreaInfo);
			}
		}
		return list;
		
	}
	

}
