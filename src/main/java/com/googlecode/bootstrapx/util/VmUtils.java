package com.googlecode.bootstrapx.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import com.googlecode.bootstrapx.model.Config;
import com.googlecode.bootstrapx.model.HH;

public class VmUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(VmUtils.class);

	public static String escape(String str) {
		return HtmlUtils.htmlEscape(str);
	}
	public static String limit(String str, int maxLength){
		if(StringUtils.isBlank(str)){
			return StringUtils.EMPTY;
		}
		
		if(StringUtils.length(str)<maxLength){
			return escape(str);
		}else{
			return escape(StringUtils.substring(str, 0, maxLength)) + "...";
		}
		
	}
	
	public static String format(long time){
		return format(time, "yyyy-MM-dd HH:mm:ss");
	}
	public static String format(String time){
		return format(time, "yyyy-MM-dd HH:mm:ss");
	}
	public static String format(String time, String format){
		return DateFormatUtils.format(new Date(NumberUtils.toLong(time)*1000), format);
	}
	public static String format(long time, String format){
		return DateFormatUtils.format(new Date(time*1000), format);
	}

	@SuppressWarnings("unchecked")
	public static String json(Object object){
		if(object==null){
			return StringUtils.EMPTY;
		}
		JSONObject result = new JSONObject();
		Map<String, Object> dataMap = null;
		try {
			dataMap = BeanUtils.describe(object);
		} catch (Exception e) {
			dataMap = new HashMap<String, Object>();
		}
		dataMap.remove("class");
		for(Entry<String, Object> entry:dataMap.entrySet()){
			try {
				result.put(entry.getKey(), entry.getValue());
			} catch (JSONException e) {
				e.printStackTrace();
				LOGGER.warn("", e);
			}
		}
		return result.toString();
	}
	public static void main(String[] args) {
		System.out.println(limit("dd", 3));
		System.out.println(limit("dddddddddddddddddd", 3));
		System.out.println(limit("dddddddddddddddddddddddddddd", 3));
		System.out.println(limit("dddddddddddddddddddddddddddddddddddddd", 3));
		
		System.out.println(format(1368561604,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(format(1368561604,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(format(1368561604,"yyyy-MM-dd HH:mm:ss"));
		System.out.println(format(1368561604,"yyyy-MM-dd HH:mm:ss"));
		Config cfg = new Config();
		System.out.println(json(cfg));
	}
}
