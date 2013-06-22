package com.googlecode.bootstrapx.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.bootstrapx.dao.UserDAO;
import com.googlecode.bootstrapx.model.OrderBy;
import com.googlecode.bootstrapx.model.User;

@Service
public class UserService extends AbstraceService {
    @Autowired
	private UserDAO userDAO;

	public List<User> getList(int page, int size, int status, String orderBy) {
		User._Fields field = User._Fields.UPDATE_TIME;
		OrderBy order = OrderBy.ASC;
    	if(StringUtils.isNotBlank(orderBy)){
    		String[] fields = orderBy.split(" ");
    		if(fields.length>=1){
		    	try {
		    		field = User._Fields.findByName(fields[0]);
		    	}catch(Exception e){
		    		LOGGER.warn("parms error or sql inject: "+ orderBy, e);
		    	}
    		}
    		if(fields.length>=2){
    			try {
    				order = OrderBy.valueOf(StringUtils.upperCase(fields[1]));
    			}catch(Exception e){
    				LOGGER.warn("parms error or sql inject: "+ orderBy, e);
    			}
    		}
    	}
		if(page<=0)
			page = 1;
		int offset = (page-1)*size;
		return userDAO.getList(offset, size,status, field.getFieldName()+ " " + String.valueOf(order));
	}

	public int getCount(int status) {
		return userDAO.getCount(status);
	}

	public int update(User user) {
		return userDAO.update(user);
	}

	public int removeById(int userId) {
		return userDAO.removeById(userId);
	}

	public int add(User user) {
		return userDAO.addForRows(user);
	}

	public User get(int userId) {
		return userDAO.get(userId);
	}

	public User get(String name) {
		return userDAO.get(name);
	}

	private Map<String, User> userMap = null;
	
	public User getUserByName(String name){
		return userDAO.get(name);
	}
	
	public Map<String, User> getUserMap(){
		if(userMap == null){
			userMap = new HashMap<String, User> ();
			
		}
		
		return userMap;
	}
}
