package com.clys.codeGenerator.helper;

import com.clys.codeGenerator.entity.ConfigContext;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author lujianing01@58.com
 * @Description:
 * @date 2015/12/18
 */
public class DBHelper {

    private DataSource dataSource;
    private QueryRunner queryRunner;
    private ConfigContext context;

    private DataSource initDataSource(ConfigContext context){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(context.getJdbc().getDriver());
        dataSource.setUrl(context.getJdbc().getUrl());
        dataSource.setUsername(context.getJdbc().getUserName());
        dataSource.setPassword(context.getJdbc().getPassword());
        return dataSource;
    }

    public DBHelper(ConfigContext context){
        this.context = context;
        dataSource = initDataSource(this.context);
        queryRunner = new QueryRunner(dataSource);
    }

    public List<Map<String, Object>> descTable(String table){

        String DESC_TABLE = String.format("desc %s",table);

        return queryMapList(DESC_TABLE,null);
    }

    public List<Map<String, Object>> queryMapList(String sql, Object... params) {
        List<Map<String, Object>> fieldMapList;
        try {
            fieldMapList = queryRunner.query(sql, new MapListHandler(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return fieldMapList;
    }

}
