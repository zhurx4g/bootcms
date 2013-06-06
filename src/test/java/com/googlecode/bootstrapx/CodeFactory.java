package com.googlecode.bootstrapx;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.xiaomi.miui.admin.common.model.Privilege;
import com.xiaomi.miui.spbook.model.SpbYellowPage;

public class CodeFactory {

	public static void generate(Class<?> controller, Class<?> bean, String prefix){
		VelocityEngine engine = new VelocityEngine("src/test/resources/velocity.properties");
		
		List<String> fieldList = new ArrayList<String>();
		Map<String,Boolean> fieldTypeMap = new HashMap<String,Boolean>();
		Field[] fields = bean.getDeclaredFields();
		for(Field f:fields){
			String fieldName = f.getName();
			if(Character.isUpperCase(fieldName.charAt(0))||fieldName.charAt(0)=='_'||"metaDataMap".equals(fieldName)){
				continue;	//首字符大写, 下划线开头,metaDataMap的字段忽略			
			}
			if(StringUtils.startsWith(fieldName, "$")){
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
		model.put("name", StringUtils.lowerCase(bean.getSimpleName()));
		model.put("prefix", prefix);
		System.out.println(VelocityEngineUtils.mergeTemplateIntoString(engine, "common/template.vm", "utf-8", model));
	}
	public static void main(String[] args) {
		generate(SpbYellowPage.class, SpbYellowPage.class, "common");
	}
}
