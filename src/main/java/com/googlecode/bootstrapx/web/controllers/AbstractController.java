package com.googlecode.bootstrapx.web.controllers;

import java.util.Map;
import java.util.Map.Entry;

import net.paoding.rose.web.Invocation;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class AbstractController {
	protected Log LOGGER = LogFactory.getLog(getClass());
	
	public static final String RESULT ="result";
	public static final String MSG ="message";
	public static final String OK ="OK";
	public static final String NG ="NG";
	public static final String WARN ="WARN";

	public String r(String url, Invocation inv){
		inv.addModel("redirectUrl", url);
		return "redirect";
	}
	
	
	@SuppressWarnings("unchecked")
	public JSONObject json(Object obj) {
		JSONObject resultJson = new JSONObject();
		Map<String, Object> dataMap;
		try {
			dataMap = BeanUtils.describe(obj);
			for(Entry<String,Object> entry:dataMap.entrySet()){
				P(resultJson, entry.getKey(), entry.getValue());
			}
		} catch (Exception e) {
			LOGGER.warn("",e);
		}
		return resultJson;
	}
	
	public void P(JSONObject object, String key, Object value){
		try {
			object.put(key, value);
		} catch (JSONException e) {
			LOGGER.warn("",e);
		}
	}
}
