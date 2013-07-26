package com.googlecode.bootstrapx.dao;

import java.util.List;

import com.googlecode.bootstrapx.model.SystemLog;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO
public interface SystemLogDAO {

    String query_columns = " id,featureId,recordId,userId,ip,action,description,createTime,updateTime,status ";
    String tab_name = " common_operation_log ";

    @ReturnGeneratedKeys
    @SQL("insert into " + tab_name + " (" + query_columns +" ) values(null,:systemLog.featureId,:systemLog.recordId,:systemLog.userId,:systemLog.ip,:systemLog.action,:systemLog.description,unix_timestamp(),unix_timestamp(),:systemLog.status)")
    public long add(@SQLParam("systemLog") SystemLog systemLog);
    
    @SQL("select " + query_columns + " from " + tab_name 
         +" where 1 " +
         " #if(:featureId>0){ and featureId=:featureId } " +
         " #if(:recordId>0){ and recordId=:recordId } " +
         "order by updateTime desc limit :offset,:size")
    public List<SystemLog> select(@SQLParam("featureId") int featureId, @SQLParam("recordId") long recordId, @SQLParam("offset") int offset, @SQLParam("size") int size);
    
    @SQL("select count(1) from " + tab_name 
            +" where 1 " +
            " #if(:featureId>0){ and featureId=:featureId } " +
            " #if(:recordId>0){ and recordId=:recordId } ")
    public int getCount(@SQLParam("featureId") int featureId, @SQLParam("recordId") long recordId);
}
