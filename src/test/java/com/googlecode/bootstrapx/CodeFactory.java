package com.googlecode.bootstrapx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.googlecode.bootstrapx.model.Navigate;

public class CodeFactory {

	public static Set<String> ignoreSet = new HashSet<String>();
	static {
		ignoreSet.add("updateTime");
		ignoreSet.add("createTime");
		ignoreSet.add("status");
		ignoreSet.add("creatorId");
		ignoreSet.add("updaterId");
	}
	public static void generate(Class<?> controller, Class<?> bean, String prefix, String alias){
		VelocityEngine engine = new VelocityEngine("src/test/resources/velocity.properties");
		
		List<String> fieldList = new ArrayList<String>();
		Map<String,Boolean> fieldTypeMap = new HashMap<String,Boolean>();
		Field[] fields = bean.getDeclaredFields();
		for(Field f:fields){
			String fieldName = f.getName();
			if(Character.isUpperCase(fieldName.charAt(0))||fieldName.charAt(0)=='_'||"metaDataMap".equals(fieldName)){
				continue;	//首字符大写, 下划线开头,metaDataMap的字段忽略			
			}
			if(StringUtils.startsWith(fieldName, "$")||ignoreSet.contains(fieldName)){
				continue;
			}
			if(Boolean.class.equals(f.getType())||boolean.class.equals(f.getType())){
				fieldTypeMap.put(fieldName, true);
			}
			fieldList.add(fieldName);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("fieldList", fieldList);
		model.put("fieldTypeMap", fieldTypeMap);
		model.put("name",alias==null?StringUtils.lowerCase(bean.getSimpleName()):alias);
		model.put("prefix", prefix);
		System.out.println(VelocityEngineUtils.mergeTemplateIntoString(engine, "common/template.vm", "utf-8", model));
	}
	public static void main(String[] args) {
		generate(Navigate.class, Navigate.class, "admin",null);
	}
}
