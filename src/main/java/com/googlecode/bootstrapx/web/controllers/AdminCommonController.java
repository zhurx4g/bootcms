package com.googlecode.bootstrapx.web.controllers;

import org.json.JSONObject;

import com.googlecode.bootstrapx.annotation.LoginRequired;
import com.googlecode.bootstrapx.model.OperateType;


@LoginRequired
public class AdminCommonController extends CommonController {

	protected JSONObject parseJSON(OperateType type, int rows){
		JSONObject resultJson = new JSONObject();
		return parseJSON(resultJson, type, rows);
	}
	protected JSONObject parseJSON(JSONObject resultJson, OperateType type, int rows){
		if(OperateType.ADD.equals(type)){
			if(rows==1){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "添加成功.");
			}else if(rows==2){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "替换成功.");
			}else{
				P(resultJson, RESULT, NG);
				P(resultJson, MSG, "添加失败.");
			}
		}else if(OperateType.UPDATE.equals(type)){
			if(rows==1){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "更新成功.");
			}else if(rows>1){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "成功更新"+rows+"条数据.");
			}else{
				P(resultJson, RESULT, NG);
				P(resultJson, MSG, "更新失败.");
			}
		}else if(OperateType.REMOVE.equals(type)){
			if(rows==1){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "删除成功");
			}else if(rows>1){
				P(resultJson, RESULT, OK);
				P(resultJson, MSG, "成功删除"+rows+"条数据.");
			}else{
				P(resultJson, RESULT, NG);
				P(resultJson, MSG, "删除失败");
			}
		}else{
			P(resultJson, RESULT, NG);
			P(resultJson, MSG, "类型来自火星, 不能识别.");
		}
		return resultJson;
	}
}