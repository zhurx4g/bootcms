package com.googlecode.bootstrapx.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.bootstrapx.dao.NavigateDAO;
import com.googlecode.bootstrapx.model.Navigate;
import com.googlecode.bootstrapx.model.OrderBy;
import com.googlecode.bootstrapx.model.Status;

@Service
public class NavigateService extends AbstraceService {

	@Autowired
	private NavigateDAO navigateDAO;
	
	public int getCount(Status status){
		return getCount(0, status);
	}
	public int getCount(int parentId, Status status){
		if(status==null)
			status = Status.NORMAL;
		return navigateDAO.getCount(parentId, status.getValue());
	}

	public List<Navigate> select(int page, int size, Status status, String orderBy) {
		return select(-1,page,size,status,orderBy);
	}
	public List<Navigate> select(int parentId, int page, int size, Status status, String orderBy) {
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

		if(status==null)
			status = Status.NORMAL;

		return navigateDAO.select(parentId, offset, size,status.getValue(), field.getFieldName()+ " " + String.valueOf(order));
	}

	public int addForRows(Navigate navigate) {
		return navigateDAO.addForRows(navigate);
	}

	public int add(Navigate navigate) {
		return navigateDAO.add(navigate);
	}

	public int remove(int id) {
		return navigateDAO.remove(id);
	}

	public Navigate get(int id) {
		return navigateDAO.get(id);
	}

	public int update(Navigate navigate) {
		return 0;
	}
}
