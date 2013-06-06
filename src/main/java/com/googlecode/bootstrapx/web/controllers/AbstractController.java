package com.googlecode.bootstrapx.web.controllers;

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
	
	public static void P(JSONObject object, String key, Object value){
		try {
			object.put(key, value);
		} catch (JSONException e) {
			//LOGGER.warn("",e);
		}
	}
}