package com.googlecode.bootstrapx.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.bootstrapx.dao.PrivilegeDAO;
import com.googlecode.bootstrapx.model.OrderBy;
import com.googlecode.bootstrapx.model.Privilege;

@Service
public class PrivilegeService {
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
	private PrivilegeDAO privilegeDAO;

	public List<Privilege> getList(int page, int size, int status, String orderBy) {
		Privilege._Fields field = Privilege._Fields.UPDATE_TIME;
		OrderBy order = OrderBy.ASC;
    	if(StringUtils.isNotBlank(orderBy)){
    		String[] fields = orderBy.split(" ");
    		if(fields.length>=1){
		    	try {
		    		field = Privilege._Fields.findByName(fields[0]);
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
		return privilegeDAO.getList(offset, size,status, field.getFieldName()+ " " + String.valueOf(order));
	}

	public List<Privilege> getListByParentId(int parentId, int status, String orderBy) {
		Privilege._Fields field = Privilege._Fields.UPDATE_TIME;
		OrderBy order = OrderBy.ASC;
		if(StringUtils.isNotBlank(orderBy)){
			String[] fields = orderBy.split(" ");
			if(fields.length>=1){
				try {
					field = Privilege._Fields.findByName(fields[0]);
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

		return privilegeDAO.getListByParentId(parentId, status, field.getFieldName()+ " " + String.valueOf(order));
	}

	public int getCount(int status) {
		return privilegeDAO.getCount(status);
	}

	public int update(Privilege priv) {
		return privilegeDAO.update(priv);
	}

	public int removeById(int privId) {
		return privilegeDAO.removeById(privId);
	}

	public int add(Privilege priv) {
		return privilegeDAO.add(priv);
	}

	public Privilege get(int privId) {
		return privilegeDAO.get(privId);
	}

	public List<Privilege> getPrivListByUserId(int userId, int status) {
		return privilegeDAO.getPrivListByUserId(userId, status);
	}
}
