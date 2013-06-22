package com.googlecode.bootstrapx.web.interceptor;

import java.lang.annotation.Annotation;
import java.util.List;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.bootstrapx.annotation.LoginRequired;
import com.googlecode.bootstrapx.model.Privilege;
import com.googlecode.bootstrapx.model.User;
import com.googlecode.bootstrapx.service.PrivilegeService;

@Component
public class PrivilegeInterceptor extends ControllerInterceptorAdapter {

	static final Logger LOGGER = LoggerFactory.getLogger(PrivilegeInterceptor.class);

	@Autowired
	private PrivilegeService privilegeService;

	public PrivilegeInterceptor(){
		this.setPriority(0);
	}
	// 覆盖这个方法，表示只有标注@LoginRequired的控制器或方法才会被此拦截器拦截
	@Override
	public Class<? extends Annotation> getRequiredAnnotationClass() {
		return LoginRequired.class;
	}

	@Override
	public Object before(Invocation inv) throws Exception {
		User loginUser = (User) inv.getModel("loginUser");
		if(loginUser==null){
			LOGGER.error("login User is null, and request privilege verify.");
			return r("login.html", inv);
		}

		
		String uri = inv.getRequestPath().getUri();
		String queryString = inv.getRequest().getQueryString();
		
		String url = uri + (StringUtils.isBlank(queryString)?StringUtils.EMPTY:("?"+queryString));

		if(StringUtils.equals(loginUser.getName(), "rdadmin")){
			LOGGER.debug("privilege accept, super user:" + loginUser.getName() + " access url:" +url);
			return true;
		}
		List<Privilege> privList = privilegeService.getPrivListByUserId(loginUser.getId(), 1);

		if(privList == null){
			LOGGER.error("privilege list is null, userId:" + loginUser.getId());
			return "error";
		}
		
		String contextPath = inv.getRequest().getContextPath();
		for(Privilege priv:privList){
			if(priv==null){
				continue;
			}
			
			String privUrl = contextPath + priv.getLink();
			if(StringUtils.startsWith(url, privUrl)){
				LOGGER.debug("privilege accept, privUrl:" + privUrl + " access url:" +url);
				return true;
			}
		}
		LOGGER.info("privilege denny user:" + loginUser.getName() + " access url:" +url);
		return false;
	}
	
	public String r(String url, Invocation inv){
		inv.addModel("redirectUrl", url);
		return "redirect";
	}
}
