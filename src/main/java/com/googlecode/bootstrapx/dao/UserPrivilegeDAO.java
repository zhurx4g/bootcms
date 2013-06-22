package com.googlecode.bootstrapx.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.googlecode.bootstrapx.model.UserPrivilege;

@DAO
public interface UserPrivilegeDAO {

	public static final String TABLE = "common_user_privilege";

	final String COLUMNS="userId,privilegeId,createTime,updateTime,status";
	
	@SQL("select " + COLUMNS + " from " + TABLE + " where userId=:userId #if(:status>-1){ and status=:status }")
	public List<UserPrivilege> getListByUserId(@SQLParam("userId") int userId, @SQLParam("status") int status);

	@SQL("update " + TABLE + " set status=0,updateTime=unix_timestamp() where userId=:userPriv.userId and privilegeId=:userPriv.privilegeId")
	public int remove(@SQLParam("userPriv") UserPrivilege userPriv);

	@SQL("replace into " + TABLE + " (" + COLUMNS + ") values(:userPriv.userId,:userPriv.privilegeId,unix_timestamp(),unix_timestamp(),1)")
	public int add(@SQLParam("userPriv") UserPrivilege userPriv);
}
