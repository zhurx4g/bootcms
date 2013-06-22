package com.googlecode.bootstrapx.web.interceptor;

import java.util.Map;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.googlecode.bootstrapx.model.Config;
import com.googlecode.bootstrapx.service.ConfigService;

@Component
public class CommonInterceptor extends ControllerInterceptorAdapter {

	static final Logger LOG = LoggerFactory.getLogger(CommonInterceptor.class);

	@Autowired
	protected ConfigService configService;

	@Override
	public Object before(Invocation inv) throws Exception {
		inv.addModel("contextPath", inv.getRequest().getContextPath());
		Map<String,Config> configMap = configService.getConfigMap();
		inv.addModel("C", configMap);
		return true;
	}
}
