package com.googlecode.bootstrapx.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.bootstrapx.dao.FriendLinkDAO;
import com.googlecode.bootstrapx.model.FriendLink;
import com.googlecode.bootstrapx.model.OrderBy;
import com.googlecode.bootstrapx.model.Status;

@Service
public class FriendLinkService extends AbstraceService {

	@Autowired
	private FriendLinkDAO friendLinkDAO;
	
	public int getCount(Status status){
		if(status==null)
			status = Status.NORMAL;
		return friendLinkDAO.getCount(status.getValue());
	}

	public List<FriendLink> select(int page, int size, Status status, String orderBy) {
		FriendLink._Fields field = FriendLink._Fields.UPDATE_TIME;
		OrderBy order = OrderBy.ASC;
    	if(StringUtils.isNotBlank(orderBy)){
    		String[] fields = orderBy.split(" ");
    		if(fields.length>=1){
		    	try {
		    		field = FriendLink._Fields.findByName(fields[0]);
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

		return friendLinkDAO.select(offset, size,status.getValue(), field.getFieldName()+ " " + String.valueOf(order));
	}

	public int addForRows(FriendLink friendLink) {
		return friendLinkDAO.addForRows(friendLink);
	}

	public int add(FriendLink friendLink) {
		return friendLinkDAO.add(friendLink);
	}

	public int remove(int id) {
		return friendLinkDAO.remove(id);
	}

	public FriendLink get(int id) {
		return friendLinkDAO.get(id);
	}

	public int update(FriendLink friendLink) {
		return 0;
	}
}
