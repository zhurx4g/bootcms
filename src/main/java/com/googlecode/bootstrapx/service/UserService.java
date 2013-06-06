package com.googlecode.bootstrapx.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.googlecode.bootstrapx.model.User;

@Service
public class UserService {
	static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
	
	private Map<String, User> userMap = null;
	
	public User getUserByName(String name){
		return getUserMap().get(name);
	}
	
	public Map<String, User> getUserMap(){
		if(userMap == null){
			userMap = new HashMap<String, User>();
			
			try {
				List<?> configLines = IOUtils.readLines(UserService.class.getResourceAsStream("/user.properties"));
				for(Object d: configLines){
					String line = (String)d;
					if(StringUtils.isBlank(line)){
						continue;
					}
					if(StringUtils.startsWith(line,"#")){
						continue;
					}
					String[] fields = line.split("=");
					if(fields.length!=2){
						LOGGER.warn("Format error" + line);
						continue;
					}
					userMap.put(fields[0], new User(fields[0], fields[1]));
				}
			} catch (IOException e) {
				LOGGER.error("parse user.properties error", e);
			}
		}
		
		return userMap;
	}
}
