package com.googlecode.bootstrapx.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.HttpFeatures;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import com.googlecode.bootstrapx.Constant;
import com.googlecode.bootstrapx.model.Privilege;
import com.googlecode.bootstrapx.service.PrivilegeService;
import com.googlecode.bootstrapx.util.PageInfo;
import com.googlecode.bootstrapx.util.PageInfoBuilder;

@Path("admin/privilege")
public class AdminPrivilegeController extends AdminCommonController {

	@Autowired
	private PrivilegeService privilegeService;

	@Get("list.html")
    public String list(Invocation inv) throws Exception {
        int page = ServletRequestUtils.getIntParameter(inv.getRequest(), "page", 1);
        int count = 0;
        
        try{
        	List<Privilege> privilegeRootList = privilegeService.getListByParentId(0, 1, "updateTime desc");
        	inv.addModel("privilegeRootList", privilegeRootList);

	        count = privilegeService.getCount(1);
	        List<Privilege> privilegeList = privilegeService.getList(page, Constant.PAGE_SIZE, 1, "updateTime desc");
	        
	        inv.addModel("privilegeList", sort(privilegeRootList, privilegeList));
	        inv.addModel("privilegeCount", count);
        }catch(Exception e){
        	LOGGER.error("get privilege error", e);
        }
        PageInfo pageInfo = PageInfoBuilder.build("common/privilege/list.html", count, page, Constant.PAGE_SIZE);
        inv.addModel("pageInfo", pageInfo);

        return "admin/privilege";
	}

	private List<Privilege> sort(List<Privilege> privilegeRootList, List<Privilege> privilegeList){
		Map<Integer, List<Privilege>> privMap = new HashMap<Integer, List<Privilege>>();
		for(Privilege priv:privilegeRootList){
			privMap.put(priv.getId(), new ArrayList<Privilege>());
		}
		
		for(Privilege priv:privilegeList){
			if(priv.getParentId()==0)
				continue;
			List<Privilege> privList = privMap.get(priv.getParentId());
			privList.add(priv);
		}
		
		List<Privilege> resultLIst = new LinkedList<Privilege>();
		for(Privilege priv:privilegeRootList){
			resultLIst.add(priv);
			resultLIst.addAll(privMap.get(priv.getId()));
		}
		return resultLIst;
	}
	@Post("add.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject add(Privilege privilege){
		JSONObject resultJson = new JSONObject();
		
		int result = -1;
		LOGGER.debug("privilege:" + privilege);
		try{
			result = privilegeService.add(privilege);
		}catch(Exception e){
			LOGGER.error("add package error", e);
			result = -1;
		}
		
		if(result<0){
			P(resultJson, RESULT, NG);
			P(resultJson, MSG, "添加失败");
		}else{
			P(resultJson, RESULT, OK);
			P(resultJson, MSG, "添加成功");
		}
		return resultJson;
	}
	
	@Get("get.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject get(@Param("id") int id){
		JSONObject resultJson = new JSONObject();
		
		try {
			Privilege user = privilegeService.get(id);
			@SuppressWarnings("unchecked")
			Map<String,Object> dataMap = BeanUtils.describe(user);
			for(Entry<String,Object> entry:dataMap.entrySet()){
				P(resultJson, entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return resultJson;
	}

	@Post("edit.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject edit(Privilege privilege){
		JSONObject resultJson = new JSONObject();
		
		try {
			int rows = privilegeService.update(privilege);
			if(rows==1){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "添加成功");
			}else {
				P(resultJson, RESULT, NG);
				P(resultJson, MSG, "添加失败");
			}
		} catch (Exception e) {
			LOGGER.error("", e);
			P(resultJson, RESULT, NG);
			P(resultJson, MSG, "添加失败");
		}
		return resultJson;
	}

	@Post("remove.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject remove(@Param("id") int id){
		JSONObject resultJson = new JSONObject();
		
		try {
			int rows = privilegeService.removeById(id);
			if(rows==1){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "添加成功");
			}else {
				P(resultJson, RESULT, NG);
				P(resultJson, MSG, "添加失败");
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return resultJson;
	}
}
