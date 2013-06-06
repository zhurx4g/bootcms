package com.googlecode.bootstrapx.dao;

import java.util.List;

import com.googlecode.bootstrapx.model.Config;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;
import net.paoding.rose.jade.annotation.SQLParam;

@DAO
public interface ConfigDAO {
	public static final String TABLE_NAME = "bootstrapx_config";
	public static final String COLUMNS = "`key`,value,name,description,`order`";
	
	@SQL("select " + COLUMNS + " from " + TABLE_NAME + " order by `order` limit :offset,:size")
	public List<Config> select(@SQLParam("offset") int offset, @SQLParam("size") int size);

	@SQL("replace into " + TABLE_NAME +" (" + COLUMNS + ") values(:config.key,:config.value,:config.name,:config.description,:config.order)")
	public int add(@SQLParam("config") Config config);

	@SQL("select "+ COLUMNS +" from " + TABLE_NAME + " where `key`=:key")
	public Config getConfigByKey(@SQLParam("key") String key);
}
