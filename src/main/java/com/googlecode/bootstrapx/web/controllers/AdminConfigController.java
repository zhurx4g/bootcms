package com.googlecode.bootstrapx.web.controllers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.HttpFeatures;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.apache.commons.beanutils.BeanUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.bootstrapx.model.Config;
import com.googlecode.bootstrapx.service.ConfigService;

@Path("admin/config")
public class AdminConfigController extends AdminCommonController {
	@Autowired
	private ConfigService configService;

	@Get("list.html")
	public String list(@Param("page") int page, Invocation inv){
		List<Config> configList = configService.getConfigList(page, 20);
		
		inv.addModel("configList", configList);
		return "admin/config";
	}

	@Post("add.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject add(Config config){
		JSONObject resultJson = new JSONObject();
		try{
			int rows = configService.add(config);
			if(rows>0){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "添加成功");
			}else{
				P(resultJson, RESULT, NG);
				P(resultJson, MSG, "添加失败");
			}
		}catch(Exception e){
			LOGGER.error("", e);
			P(resultJson, RESULT, NG);
			P(resultJson, MSG, "添加失败");
		}
		return resultJson;
	}
	@Post("edit.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject edit(Config config){
		JSONObject resultJson = new JSONObject();
		
		try{
			int rows = configService.update(config);
			
			if(rows>0){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "更新成功");
			}else{
				P(resultJson, RESULT, NG);
				P(resultJson, MSG, "更新失败");
			}
		}catch(Exception e){
			LOGGER.error("", e);
			P(resultJson, RESULT, NG);
			P(resultJson, MSG, "更新失败");
		}
		return resultJson;
	}
	@Post("remove.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject remove(@Param("key") String key){
		JSONObject resultJson = new JSONObject();
		
		try{
			int rows = configService.remove(key);
			
			if(rows>0){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "更新成功");
			}else{
				P(resultJson, RESULT, NG);
				P(resultJson, MSG, "更新失败");
			}
		}catch(Exception e){
			LOGGER.error("", e);
			P(resultJson, RESULT, NG);
			P(resultJson, MSG, "更新失败");
		}
		return resultJson;
	}

	@Get("get.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject get(@Param("key") String key){
		JSONObject resultJson = new JSONObject();
		try{
			Config cfg = configService.getConfigByKey(key);
			@SuppressWarnings("unchecked")
			Map<String,Object> dataMap = BeanUtils.describe(cfg);
			for(Entry<String,Object> entry:dataMap.entrySet()){
				P(resultJson, entry.getKey(), entry.getValue());
			}
		}catch(Exception e){
			LOGGER.error("", e);
			P(resultJson, "error", e.getMessage());
		}
		return resultJson;
	}
}
