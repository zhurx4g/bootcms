package com.googlecode.bootstrapx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.bootstrapx.dao.ConfigDAO;
import com.googlecode.bootstrapx.model.Config;

@Service
public class ConfigService {

	@Autowired
	private ConfigDAO configDao;
	
	public List<Config> getConfigList(int page,int size){
		if(page<=0){
			page = 1;
		}
		int offset = (page-1)*size;
		return configDao.select(offset, 20);
	}
	
	public int add(Config config){
		if(config==null)
			return -1;
		return configDao.add(config);
	}

	public Map<String, Config> getConfigMap(){
		Map<String, Config> hashMap = new HashMap<String, Config>();
		List<Config> configList = configDao.select(0, Integer.MAX_VALUE);
		for(Config cfg:configList){
			hashMap.put(cfg.getKey(), cfg);
		}
		return hashMap;
	}
	
	public Config getConfigByKey(String key){
		return configDao.getConfigByKey(key);
	}
	
	public int remove(String key){
		return configDao.remove(key);
	}
	
	public int update(Config config){
		return configDao.update(config);
	}
}
