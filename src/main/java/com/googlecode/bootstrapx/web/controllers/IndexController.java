package com.googlecode.bootstrapx.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.bootstrapx.model.Navigate;
import com.googlecode.bootstrapx.model.Status;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;

@Path("")
public class IndexController extends CommonController {

    @Get("")
    public String root(Invocation inv) {
        return "r:index.html";
    }

    @Get("index.html")
    public String index(Invocation inv){
        List<Navigate> navigateList = navigateService.select(0, 0, Integer.MAX_VALUE, Status.NORMAL, "sequence asc");
        inv.addModel("navigateList", navigateList);
        
        Map<Integer,List<Navigate>> navigateListMap = new HashMap<Integer, List<Navigate>>();
        for(Navigate nav:navigateList){
            if(nav.getCount()>0)
                navigateListMap.put(nav.getId(), navigateService.select(nav.getId(), 0, Integer.MAX_VALUE, Status.NORMAL, "sequence asc"));
        }
        inv.addModel("navigateListMap", navigateListMap);
        return "index";
    }
}
