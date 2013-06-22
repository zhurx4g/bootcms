package com.googlecode.bootstrapx.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.bootstrapx.dao.NavigateDAO;
import com.googlecode.bootstrapx.model.Navigate;
import com.googlecode.bootstrapx.model.OrderBy;

@Service
public class NavigateService extends AbstraceService {

	@Autowired
	private NavigateDAO navigateDAO;
	
	public List<Navigate> select(int page, int size, int status, String orderBy) {
		Navigate._Fields field = Navigate._Fields.UPDATE_TIME;
		OrderBy order = OrderBy.ASC;
    	if(StringUtils.isNotBlank(orderBy)){
    		String[] fields = orderBy.split(" ");
    		if(fields.length>=1){
		    	try {
		    		field = Navigate._Fields.findByName(fields[0]);
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
		return navigateDAO.select(offset, size,status, field.getFieldName()+ " " + String.valueOf(order));
	}

	public int add(Navigate navigate) {
		return 0;
	}

	public int addForRows(Navigate navigate) {
		return 0;
	}

	public int remove(String key) {
		return 0;
	}

	public Navigate get(int id) {
		return null;
	}

	public int update(Navigate navigate) {
		return 0;
	}
}
