package com.googlecode.bootstrapx.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.googlecode.bootstrapx.model.FriendLink;

@DAO
public interface FriendLinkDAO {
	public static final String TABLE = "bootstrapx_friendlink";
	public static final String FIELDS = " id,name,sequence,weight,icon,image,description,creatorId,updaterId,createTime,updateTime,link,status ";
	public static final String FIELDS_REPLACE = " (" + FIELDS + ") ";


	@SQL("select " + FIELDS + " from " + TABLE + " where status=:status #if (:orderBy != null) { order by ##(:orderBy)} limit :offset,:size")
	public List<FriendLink> select(@SQLParam("offset") int offset, @SQLParam("size") int size, @SQLParam("status") int status, @SQLParam("orderBy") String orderBy);

	@SQL("replace into " + TABLE + FIELDS_REPLACE + " values(null,:friendLink.name,:friendLink.sequence,:friendLink.weight,:friendLink.icon,:friendLink.image,:friendLink.description,:friendLink.creatorId,:friendLink.updaterId,:friendLink.createTime,:friendLink.updateTime,:friendLink.link,:friendLink.status)")
	public int addForRows(@SQLParam("friendLink") FriendLink friendLink);

	@ReturnGeneratedKeys
	@SQL("insert into " + TABLE + FIELDS + " values(null,:navigate.name,:navigate.sequence,:navigate.weight,:navigate.icon,:navigate.image,:navigate.description,:navigate.createorId,:navigate.updaterId,now(),now(),:navigate.link,:navigate.status)")
	public int add(@SQLParam("friendLink") FriendLink friendLink);
	
	@SQL("update " + TABLE + " set status=0 where id=:id and status=1")
	public int remove(@SQLParam("id") int id);

	@SQL("select " + FIELDS +" from " + TABLE + " where status=1 and id=:id")
	public FriendLink get(@SQLParam("id") int id);

	@SQL("select count(*) from " + TABLE + " where status=:status")
	public int getCount(@SQLParam("status") int status);
}
