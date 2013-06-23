package com.googlecode.bootstrapx.web.controllers;

import java.util.List;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.HttpFeatures;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

import org.json.JSONObject;

import com.googlecode.bootstrapx.Constant;
import com.googlecode.bootstrapx.model.FriendLink;
import com.googlecode.bootstrapx.model.OperateType;
import com.googlecode.bootstrapx.model.Status;
import com.googlecode.bootstrapx.util.PageInfo;
import com.googlecode.bootstrapx.util.PageInfoBuilder;

@Path("admin/friendlink")
public class AdminFriendLinkController extends AdminCommonController {

	@Get("list.html")
	public String list(@Param("page") int page, Invocation inv){
		List<FriendLink> friendlinkList = friendLinkService.select(page, Constant.PAGE_SIZE, Status.NORMAL, "updateTime desc");
		inv.addModel("friendlinkList", friendlinkList);

        PageInfo pageInfo = PageInfoBuilder.build("admin/friendlink/list.html", friendLinkService.getCount(Status.NORMAL), page, Constant.PAGE_SIZE);
        inv.addModel("pageInfo", pageInfo);
		return "admin/friendlink";
	}

	@Post("add.do")
	@HttpFeatures(contentType = "application/json", charset = "UTF-8")
	public JSONObject add(FriendLink friendLink){
		JSONObject resultJson = new JSONObject();
		try{
			int rows = friendLinkService.addForRows(friendLink);
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
	public JSONObject edit(FriendLink friendLink){
		JSONObject resultJson = new JSONObject();
		
		try{
			int rows = friendLinkService.update(friendLink);
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
			int rows = friendLinkService.remove(id);
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
			FriendLink friendLink = friendLinkService.get(id);
			resultJson = json(friendLink);
		}catch(Exception e){
			LOGGER.error("", e);
			P(resultJson, "error", e.getMessage());
		}
		return resultJson;
	}
}
