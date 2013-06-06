package com.googlecode.bootstrapx.web.controllers;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("")
public class IndexController {

	@Get("")
	public String root(Invocation inv) {
		return "r:index.html";
	}

	@Get("index.html")
	public String index(Invocation inv){
		return "index";
	}
}
