package com.googlecode.bootstrapx.web.controllers;

import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.HttpFeatures;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.json.JSONObject;

import com.googlecode.bootstrapx.model.Navigate;
import com.googlecode.bootstrapx.model.OperateType;

@Path("admin/navigate")
public class AdminNavigateController extends AdminCommonController {

	@Get("list.html")
	public String list(@Param("page") int page, Invocation inv){
		List<Navigate> navigateList = navigateService.select(page, SIZE, 1, "updateTime desc");
		
		inv.addModel("navigateList", navigateList);
		return "admin/navigate";
	}

	@Post("add.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject add(Navigate navigate){
		JSONObject resultJson = new JSONObject();
		try{
			int rows = navigateService.addForRows(navigate);
			resultJson = parseJSON(OperateType.ADD, rows);
		}catch(Exception e){
			LOGGER.error("", e);
			P(resultJson, RESULT, NG);
			P(resultJson, MSG, "添加失败");
		}
		return resultJson;
	}
	@Post("edit.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject edit(Navigate navigate){
		JSONObject resultJson = new JSONObject();
		
		try{
			int rows = navigateService.update(navigate);
			resultJson = parseJSON(OperateType.UPDATE, rows);
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
			int rows = navigateService.remove(key);
			resultJson = parseJSON(OperateType.REMOVE, rows);
		}catch(Exception e){
			LOGGER.error("", e);
			P(resultJson, RESULT, NG);
			P(resultJson, MSG, "更新失败");
		}
		return resultJson;
	}

	@Get("get.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject get(@Param("id") int id){
		JSONObject resultJson = new JSONObject();
		try{
			Navigate navigate = navigateService.get(id);
			resultJson = json(navigate);
		}catch(Exception e){
			LOGGER.error("", e);
			P(resultJson, "error", e.getMessage());
		}
		return resultJson;
	}
}
