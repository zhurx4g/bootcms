package com.googlecode.bootstrapx.web.interceptor;

import java.lang.annotation.Annotation;

import javax.servlet.http.Cookie;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.bootstrapx.Constant;
import com.googlecode.bootstrapx.annotation.LoginRequired;
import com.googlecode.bootstrapx.model.User;
import com.googlecode.bootstrapx.service.UserService;
import com.googlecode.bootstrapx.util.CookieUtils;
import com.googlecode.bootstrapx.util.CryptUtils;

@Component
public class LoginRequiredInterceptor extends ControllerInterceptorAdapter {

	protected Log LOG = LogFactory.getLog(getClass());

	@Autowired
	private UserService userService;
	
	public LoginRequiredInterceptor(){
		this.setPriority(10);
	}
	// 覆盖这个方法，表示只有标注@LoginRequired的控制器或方法才会被此拦截器拦截
	@Override
	public Class<? extends Annotation> getRequiredAnnotationClass() {
		return LoginRequired.class;
	}

	@Override
	public Object before(Invocation inv) throws Exception {
		inv.addModel("contextPath", inv.getRequest().getContextPath());
		StringBuffer loginInfo = new StringBuffer();
		try {
			
			String loginIp = inv.getRequest().getRemoteHost();//AccessTrackUtil.getClientIP(inv.getRequest());
			String token = inv.getRequestPath().getRosePath();
			loginInfo.append(inv.getRequestPath().toString() + ",访问路径=[")
					.append(token).append("],登录IP=[").append(loginIp)
					.append("]");
			Cookie cookie = CookieUtils.getCookieByName(inv.getRequest(),Constant.ADMIN_TOKEN);
			if (cookie == null || StringUtils.isBlank(cookie.getValue())) {
				loginInfo.append("，鉴权失败，cookie sid 为空!");
				LOG.debug(loginInfo.toString() +" maybe is a error,try login again!");
				return r("admin/login.html", inv);
			}
			long key = Long.parseLong(cookie.getValue());
			cookie = CookieUtils.getCookieByName(inv.getRequest(),Constant.COOKIE_USR);
			if (cookie == null || StringUtils.isBlank(cookie.getValue())) {
				loginInfo.append("，鉴权失败，cookie usr 为空!");
				LOG.debug(loginInfo.toString() + "maybe is a error,try login again!");
				return r("admin/login.html", inv);
			}
			String usr = cookie.getValue();
			User user = userService.getUserByName(usr);
			String pwd = (user==null||user.getStatus()!=1)?"":user.getPassword();
			if(StringUtils.isNotBlank(pwd) ){
				StringBuilder strB = new StringBuilder();
				strB.append(usr);
				strB.append('|');
				strB.append(pwd);
				long sign = CryptUtils.sign2Long(strB.toString());
				if(key == sign){
					loginInfo.append(",uname=[ ");
					loginInfo.append(usr);
					loginInfo.append(" ]");
					LOG.debug(loginInfo.toString() + " check session success!!");
					inv.addModel("loginUser", user);
					return true;
				}
			}
			loginInfo.append("cookie鉴权失败");
			LOG.warn(loginInfo.toString() + " 鉴权失败 ");
			return r("admin/login.html", inv); // 某个特殊的错误码 add by
		} catch (Exception e) {
			loginInfo.append(",登录鉴权拦截器异常");
			LOG.error(loginInfo.toString());
			LOG.error(e.getMessage());
			return r("admin/login.html", inv);// 登录鉴权异常
		}
	}
	
	public String r(String url, Invocation inv){
		inv.addModel("redirectUrl", url);
		return "redirect";
	}
}
