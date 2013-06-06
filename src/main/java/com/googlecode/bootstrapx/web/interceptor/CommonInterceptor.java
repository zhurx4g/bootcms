package com.googlecode.bootstrapx.web.interceptor;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonInterceptor extends ControllerInterceptorAdapter {

	static final Logger LOG = LoggerFactory.getLogger(CommonInterceptor.class);

	@Override
	public Object before(Invocation inv) throws Exception {
		inv.addModel("contextPath", inv.getRequest().getContextPath());

		return true;
	}
}
