package com.googlecode.bootstrapx.web.controllers;

import java.util.List;

import com.googlecode.bootstrapx.model.Navigate;
import com.googlecode.bootstrapx.model.Status;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("course")
public class CourseController extends CommonController {

	@Get("info.html")
	public String info(Invocation inv){
		List<Navigate> navigateList = navigateService.select(0, 0, Integer.MAX_VALUE, Status.NORMAL, "sequence asc");
		inv.addModel("navigateList", navigateList);
		return "course";
	}
}
