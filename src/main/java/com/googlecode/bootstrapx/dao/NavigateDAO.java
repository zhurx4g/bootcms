package com.googlecode.bootstrapx.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

import com.googlecode.bootstrapx.model.Navigate;

@DAO
public interface NavigateDAO {
	public static final String TABLE = "bootstrapx_navigate";
	@SQL("select * from " + TABLE + " where status=:status #if (:orderBy != null) { order by ##(:orderBy)} limit :offset,:size")
	public List<Navigate> select(@SQLParam("offset") int offset, @SQLParam("size") int size, @SQLParam("status") int status, @SQLParam("orderBy") String orderBy);
}
