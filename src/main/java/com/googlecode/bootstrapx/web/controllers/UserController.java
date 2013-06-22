package com.googlecode.bootstrapx.web.controllers;

import java.util.Date;
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
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import com.googlecode.bootstrapx.Constant;
import com.googlecode.bootstrapx.annotation.LoginRequired;
import com.googlecode.bootstrapx.model.Privilege;
import com.googlecode.bootstrapx.model.User;
import com.googlecode.bootstrapx.model.UserPrivilege;
import com.googlecode.bootstrapx.service.PrivilegeService;
import com.googlecode.bootstrapx.service.UserPrivilegeService;
import com.googlecode.bootstrapx.service.UserService;
import com.googlecode.bootstrapx.util.PageInfo;
import com.googlecode.bootstrapx.util.PageInfoBuilder;
import com.googlecode.bootstrapx.util.SecurityUtils;

@Path("admin/user")
@LoginRequired
public class UserController extends AdminCommonController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserPrivilegeService userPrivilegeService;
	@Autowired
	private PrivilegeService privilegeService;

	@Get("list.html")
    public String list(Invocation inv) throws Exception {
		inv.addModel("contextPath", inv.getRequest().getContextPath());
        inv.addModel("now", new Date());
        
        int page = ServletRequestUtils.getIntParameter(inv.getRequest(), "page", 1);
        int count = 0;
        
        try{
	        count = userService.getCount(1);
	        List<User> userList = userService.getList(page, Constant.PAGE_SIZE, 1, "updateTime desc");
	        inv.addModel("userList", userList);
	        inv.addModel("userCount", count);
	        
	        List<Privilege> privilegeList = privilegeService.getList(page, Integer.MAX_VALUE, 1, "updateTime desc");
	        inv.addModel("privilegeList", privilegeList);
        }catch(Exception e){
        	LOGGER.error("get package error", e);
        }
        PageInfo pageInfo = PageInfoBuilder.build("common/user/list.html", count, page, Constant.PAGE_SIZE);
        inv.addModel("pageInfo", pageInfo);

        return "admin/user";
	}

	@Post("add.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject add(User user){
		JSONObject resultJson = new JSONObject();
		
		int result = -1;
		try{
			String password = user.getPassword();
			user.setPassword(SecurityUtils.password(password));
			User tmpUser = userService.getUserByName(StringUtils.trim(user.getName()));
			if(tmpUser==null||StringUtils.isBlank(tmpUser.getName())){
				result = userService.add(user);
			}else{
				if(tmpUser.getStatus()==0){
					result = userService.add(user);
				}else{
					P(resultJson, RESULT, NG);
					P(resultJson, MSG, "改用户已经存在.");
					return resultJson;
				}
			}
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
			User user = userService.get(id);
			user.setPassword("");
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
	public JSONObject edit(User user){
		JSONObject resultJson = new JSONObject();
		
		try {
			String password = user.getPassword();
			user.setPassword(SecurityUtils.password(password));
			int rows = userService.update(user);
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
			int rows = userService.removeById(id);
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

	@Get("getPriv.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONArray getPriv(@Param("userId") int userId){
		JSONArray resultJson = new JSONArray();
		
		try {
			List<UserPrivilege> userPrivList = userPrivilegeService.getListByUserId(userId, 1);
			
			for(UserPrivilege userPriv:userPrivList){
				resultJson.put(json(userPriv));
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return resultJson;
	}

	@Post("editPriv.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject editPriv(@Param("privId") int[] privId,@Param("userId") int userId){
		JSONObject resultJson = new JSONObject();
		if(privId == null){
			P(resultJson, RESULT, NG);
			P(resultJson, MSG, "参数错误");
		}else{
			for(int id:privId){
				UserPrivilege userPriv = new UserPrivilege();
				userPriv.setPrivilegeId(Math.abs(id));
				userPriv.setUserId(userId);
				
				try {
					if(id<0){
						userPrivilegeService.remove(userPriv);
					}else{
						userPrivilegeService.add(userPriv);
					}
				} catch (Exception e) {
					LOGGER.error("", e);
				}
			}
			P(resultJson, RESULT, OK);
			P(resultJson, MSG, "添加成功");
		}
		return resultJson;
	}
}
