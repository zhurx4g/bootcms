package com.googlecode.bootstrapx.web.controllers;

import java.util.Date;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import com.googlecode.bootstrapx.Constant;
import com.googlecode.bootstrapx.model.User;
import com.googlecode.bootstrapx.service.UserService;
import com.googlecode.bootstrapx.util.CookieUtils;
import com.googlecode.bootstrapx.util.CryptUtils;

@Path("admin")
public class AdminLoginController {
	static final Logger LOGGER = LoggerFactory.getLogger(AdminLoginController.class);
	
	@Autowired
	private UserService userService;
	
	@Get("login.html")
    public String login(Invocation inv) {
		inv.addModel("contextPath", inv.getRequest().getContextPath());
        inv.addModel("now", new Date());
        return "admin/login";
    }

	@Post("login.html")
    public String doLogin(Invocation inv) throws Exception {
		String username = ServletRequestUtils.getStringParameter(inv.getRequest(), "username");
		String password = ServletRequestUtils.getStringParameter(inv.getRequest(), "password");
		
		inv.addModel("contextPath", inv.getRequest().getContextPath());
		User user = userService.getUserByName(username);
		if(user == null){
			inv.addModel("error", "用户名或密码错误");
		}else{
			LOGGER.debug("user name:" + user.getName());
			if(StringUtils.equals(password, user.getPassword())){
				addLoginCookie(username, password, inv);
				return "r:index.html";
			}else{
				inv.addModel("error", "用户名或密码错误");
			}
		}
        return "admin/login";
    }
	
	@Get("logout.html")
    public String logout(Invocation inv) {
		inv.addModel("contextPath", inv.getRequest().getContextPath());
		
    	CookieUtils.addCookie(inv.getResponse(), Constant.ADMIN_TOKEN, "", 0);
    	CookieUtils.addCookie(inv.getResponse(), Constant.COOKIE_USR, "", 0);
    	
        return "admin/login";
    }
	
    private void addLoginCookie(String name,String password, Invocation inv) {
    	StringBuilder strB = new StringBuilder();
    	strB.append(name);
    	strB.append('|');
    	strB.append(password);
    	long sign = CryptUtils.sign2Long(strB.toString());
    	CookieUtils.addCookie(inv.getResponse(), Constant.ADMIN_TOKEN, Long.toString(sign), Constant.SESSION_EXPIRE_TIME);
    	CookieUtils.addCookie(inv.getResponse(), Constant.COOKIE_USR, name, Constant.SESSION_EXPIRE_TIME);
    }
}
