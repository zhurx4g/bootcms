package com.googlecode.bootstrapx.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.googlecode.bootstrapx.model.Category;

@DAO
public interface CategoryDAO {
	public static final String TABLE = "bootstrapx_category";
	public static final String FIELDS = " id,parentId,name,sequence,weight,icon,image,description,creatorId,updaterId,createTime,updateTime,link,status ";
	public static final String FIELDS_REPLACE = " (" + FIELDS + ") ";


	@SQL("select " + FIELDS + " from " + TABLE + " where status=:status #if (:orderBy != null) { order by ##(:orderBy)} limit :offset,:size")
	public List<Category> select(@SQLParam("offset") int offset, @SQLParam("size") int size, @SQLParam("status") int status, @SQLParam("orderBy") String orderBy);

	@SQL("replace into " + TABLE + FIELDS_REPLACE + " values(:category.id,:category.parentId,:category.name,:category.sequence,:category.weight,:category.icon,:category.image,:category.description,:category.creatorId,:category.updaterId,:category.createTime,:category.updateTime,:category.link,:category.status)")
	public int addForRows(@SQLParam("friendLink") Category category);

	@ReturnGeneratedKeys
	@SQL("insert into " + TABLE + FIELDS + " values(null,:category.parentId,:category.name,:category.sequence,:category.weight,:category.icon,:category.image,:category.description,:category.creatorId,:category.updaterId,:category.createTime,:category.updateTime,:category.link,:category.status)")
	public int add(@SQLParam("category") Category category);
	
	@SQL("update " + TABLE + " set status=0 where id=:id and status=1")
	public int remove(@SQLParam("id") int id);

	@SQL("select " + FIELDS +" from " + TABLE + " where status=1 and id=:id")
	public Category get(@SQLParam("id") int id);

	@SQL("select count(*) from " + TABLE + " where status=:status")
	public int getCount(@SQLParam("status") int status);
}
