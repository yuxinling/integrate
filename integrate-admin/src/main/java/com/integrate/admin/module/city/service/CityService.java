package com.integrate.admin.module.city.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.integrate.admin.module.city.dao.CityDao;
import com.integrate.admin.module.city.model.AreaInfo;
import com.integrate.admin.module.city.model.CityInfo;
import com.integrate.admin.module.city.model.ProvinceCityInfo;
import com.integrate.admin.module.city.model.ProvinceInfo;
import com.integrate.common.Constants;
/**
 * 省市服务类.
 * @author summerao
 *
 */
@Component
public class CityService {
	private Logger logger = LoggerFactory.getLogger(CityService.class);
	@Autowired
	private CityDao cityDao;
	
	LoadingCache<Integer, List<ProvinceCityInfo>> cityCache = 
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
			new CacheLoader<Integer,  List<ProvinceCityInfo>>(){
				@Override
				public  List<ProvinceCityInfo> load(Integer version) {
					return getProvinceCityInfoList() ;
				}
			});

	LoadingCache<String, List<AreaInfo>> areaCacheByCityName =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<String, List<AreaInfo>>(){
						@Override
						public  List<AreaInfo> load(String cityName) {
							return cityDao.getAreaList(cityName);
						}
					});

	LoadingCache<Integer, List<AreaInfo>> areaCacheByCityCode =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<Integer, List<AreaInfo>>(){
						@Override
						public  List<AreaInfo> load(Integer cityCode) {
							return cityDao.getAreaList(cityCode);
						}
					});

	LoadingCache<Integer, Map<Integer, String>> cityCodeAndNameCache =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<Integer, Map<Integer, String>>(){
						@Override
						public  Map<Integer, String> load(Integer version) {
							return cityDao.getCityCodeAndNameMap() ;
						}
					});

	LoadingCache<Integer, Map<Integer, String>> provinceCodeAndNameCache =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<Integer, Map<Integer, String>>(){
						@Override
						public  Map<Integer, String> load(Integer version) {
							return cityDao.getProvinceCodeAndNameMap() ;
						}
					});

	LoadingCache<Integer, ProvinceInfo> provinceInfoByCityCodeCache =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<Integer, ProvinceInfo>(){
						@Override
						public ProvinceInfo load(Integer cityCode) {
							return cityDao.getProvinceInfoByCityCode(cityCode) ;
						}
					});

	LoadingCache<Integer, List<CityInfo>> cityListByProvinceCodeCache =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<Integer, List<CityInfo>>(){
						@Override
						public List<CityInfo> load(Integer provinceCode) {
							if (provinceCode == 0){
								return Collections.EMPTY_LIST;
							}
							return cityDao.getCityListByProvinceCode(provinceCode) ;
						}
					});

	LoadingCache<String, Integer> cityName2CityCodeCache =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<String, Integer>(){
						@Override
						public Integer load(String cityName) {
							if (StringUtils.isBlank(cityName)) {
								return 0;
							}
							return cityDao.getCityCodeByCityName(cityName) ;
						}
					});

	LoadingCache<Integer, String> cityCode2CityNameCache =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<Integer, String>(){
						@Override
						public String load(Integer cityCode) {
							if (cityCode < 1) {
								return "";
							}
							return cityDao.getCityNameByCityCode(cityCode) ;
						}
					});

	LoadingCache<Integer, String> areaCode2areaNameCache =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<Integer, String>(){
						@Override
						public String load(Integer areaCode) {
							return StringUtils.defaultString(cityDao.getAreaNameByAreaCode(areaCode)) ;
						}
					});

	LoadingCache<Integer, Integer> areaCode2cityCodeCache =
			CacheBuilder.newBuilder().expireAfterWrite(2, TimeUnit.DAYS).build(
					new CacheLoader<Integer, Integer>(){
						@Override
						public Integer load(Integer areaCode) {
							return cityDao.getCityCodeByAreaCode(areaCode) ;
						}
					});

	public int getCityCodeByAreaCode(int areaCode) {
		return areaCode2cityCodeCache.getUnchecked(areaCode);
	}

	public int getCityCodeByCityName(String cityName) {
		return cityName2CityCodeCache.getUnchecked(cityName);
	}

	public String getCityNameByCityCode(int cityCode) {
		return cityCode2CityNameCache.getUnchecked(cityCode);
	}

	public String getAreaNameByAreaCode(int areaCode) {
		return areaCode2areaNameCache.getUnchecked(areaCode);
	}
	
	/**
	 * 获取城市列表
	 * @return
	 */
	public List<ProvinceCityInfo> getCityList(){
		try {
			List<ProvinceCityInfo> list = null;
			list = cityCache.get(Constants.CITY_VERSION);
			if(list!= null &&  list.size()>0){
				return list;
			}
		} catch (ExecutionException e) {
			logger.warn("get city cache error.e:{}",e);
		}
		return Lists.newArrayList();
	}

	/**
	 * 获取区域列表
	 * @return
	 */
	public List<AreaInfo> getAreaInfoList(String cityName){
		try {
			List<AreaInfo> list = null;
			list = areaCacheByCityName.get(cityName);
			if(list!= null &&  list.size()>0){
				return list;
			}
		} catch (ExecutionException e) {
			logger.warn("get getAreaInfoList cache error.e:{}",e);
		}
		return Lists.newArrayList();
	}

	public List<AreaInfo> getAreaInfoList(int cityCode){
		try {
			List<AreaInfo> list = null;
			list = areaCacheByCityCode.get(cityCode);
			if(list!= null &&  list.size()>0){
				return list;
			}
		} catch (ExecutionException e) {
			logger.warn("get getAreaInfoList cache error.e:{}",e);
		}
		return Lists.newArrayList();
	}

	/**
	 * 省市组装
	 * @return
	 */
	private List<ProvinceCityInfo> getProvinceCityInfoList(){
		List<ProvinceCityInfo> list = Lists.newArrayList();
		List<Map<String, Object>> cityList = cityDao.getCityList(Constants.CITY_VERSION);
		List<Map<String, Object>> provinceList = cityDao.getProvinceList(Constants.CITY_VERSION);
		if(provinceList !=null && provinceList.size()>0){
			for(Map<String,Object> map :provinceList){
				ProvinceCityInfo  provinceCityInfo = new ProvinceCityInfo();
				int provinceCode = (int) map.get("province_code");
				provinceCityInfo.setProvinceCode((int)map.get("province_code"));
				provinceCityInfo.setProvinceName((String)map.get("province_name"));
				List<CityInfo> cityInfoList = Lists.newArrayListWithCapacity(cityList.size());
				for(Map<String,Object> cityMap :cityList){
					int cityProvinceCode =(int) cityMap.get("province_code");
					if(provinceCode ==cityProvinceCode ){
						CityInfo cityInfo = new CityInfo();
						cityInfo.setCityCode((int)cityMap.get("city_code"));
						cityInfo.setViewCityName((String)cityMap.get("city_name"));
						cityInfoList.add(cityInfo);
					}
				}
				provinceCityInfo.setCityList(cityInfoList);
				list.add(provinceCityInfo);
			}
		}
		return list;
	}

	public Map<Integer, String> getCityCodeAndNameMap() {
		return cityCodeAndNameCache.getUnchecked(1);
	}

	public Map<Integer, String> getProvinceCodeAndNameMap() {
		return provinceCodeAndNameCache.getUnchecked(1);
	}

	public ProvinceInfo getProvinceInfoByCityCode(int cityCode) {
		return provinceInfoByCityCodeCache.getUnchecked(cityCode);
	}

	public List<CityInfo> getCityListByProvinceCode(int provinceCode) {
		return cityListByProvinceCodeCache.getUnchecked(provinceCode);
	}
}
