package com.googlecode.bootstrapx.web.controllers;

import java.util.Date;

import com.googlecode.bootstrapx.annotation.LoginRequired;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("admin")
@LoginRequired
public class AdminHelpController {
	@Get("help.html")
    public String help(Invocation inv) {
		inv.addModel("contextPath", inv.getRequest().getContextPath());
        inv.addModel("now", new Date());
        return "admin/help";
    }
}
