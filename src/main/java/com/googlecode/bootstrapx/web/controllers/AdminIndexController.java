package com.googlecode.bootstrapx.web.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("admin")
public class AdminIndexController {

	@Get("index.html")
	public String index(Invocation inv){
		return "admin/index";
	}
}
