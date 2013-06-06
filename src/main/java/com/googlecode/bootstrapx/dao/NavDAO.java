package com.googlecode.bootstrapx.dao;

import java.util.List;

import com.googlecode.bootstrapx.model.Nav;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO
public interface NavDAO {
	@SQL("select * from bootstrapx_nav where status=1 ##(orderBy) limit :offset,size")
	public List<Nav> select(@SQLParam("offset") int offset, @SQLParam("size") int size, String orderBy);
}
