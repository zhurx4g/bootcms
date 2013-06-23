package com.googlecode.bootstrapx.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.HttpFeatures;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.json.JSONObject;

import com.googlecode.bootstrapx.Constant;
import com.googlecode.bootstrapx.model.Navigate;
import com.googlecode.bootstrapx.model.OperateType;
import com.googlecode.bootstrapx.util.PageInfo;
import com.googlecode.bootstrapx.util.PageInfoBuilder;
import com.googlecode.bootstrapx.model.Status;

@Path("admin/navigate")
public class AdminNavigateController extends AdminCommonController {

	@Get("list.html")
	public String list(@Param("page") int page, Invocation inv, @Param("parentId") int parentId){
		List<Navigate> navigateList = navigateService.select(parentId, page, Constant.PAGE_SIZE, Status.NORMAL, "updateTime desc");
		inv.addModel("navigateList", navigateList);
		
		List<Navigate> parentList = navigateService.select(0, 0, Integer.MAX_VALUE, Status.NORMAL, "updateTime desc");
		inv.addModel("parentList", parentList);

		Map<Integer,Navigate> parentMap = new HashMap<Integer,Navigate>();
		for(Navigate nav:parentList){
			parentMap.put(nav.getId(), nav);
		}
		inv.addModel("parentMap", parentMap);

        PageInfo pageInfo = PageInfoBuilder.build("admin/navigate/list.html", navigateService.getCount(parentId, Status.NORMAL), page, Constant.PAGE_SIZE);
        inv.addModel("pageInfo", pageInfo);
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
	public JSONObject remove(@Param("id") int id){
		JSONObject resultJson = new JSONObject();
		
		try{
			int total = navigateService.getCount(id, Status.NORMAL);
			if(total>0){
				P(resultJson, RESULT, NG);
				P(resultJson, MSG, "更新成功.");
				return resultJson;
			}
			int rows = navigateService.remove(id);
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
