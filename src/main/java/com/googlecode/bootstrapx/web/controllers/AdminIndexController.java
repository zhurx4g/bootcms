package com.googlecode.bootstrapx.web.controllers;

import java.util.Date;
import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

import com.googlecode.bootstrapx.model.SubSystem;

@Path("admin")
public class AdminIndexController extends AdminCommonController {
	
	@Get("")
	public String root(Invocation inv) {
		return r("index.html", inv);
	}

	@Get("index.html")
	public String index(Invocation inv){
		List<SubSystem> subSystemList = subSystemService.getSubSystemList();
		inv.addModel("subSystemList", subSystemList);
		return "admin/index";
	}
	
	@Get("welcome.html")
	public String welcome(Invocation inv) {
		inv.addModel("contextPath", inv.getRequest().getContextPath());
		inv.addModel("now", new Date());
		return "admin/welcome";
	}
}
