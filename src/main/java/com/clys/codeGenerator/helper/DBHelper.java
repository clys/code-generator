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

    private DataSource initDataSource(ConfigContext context) {
        BasicDataSource dataSource = new BasicDataSource();
        String driver = context.getJdbc().getDriver(), url = context.getJdbc().getUrl();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(driver.contains("mysql") ? modifyDB(url, "information_schema") : url);
        dataSource.setUsername(context.getJdbc().getUserName());
        dataSource.setPassword(context.getJdbc().getPassword());
        return dataSource;
    }

    private static String modifyDB(String url, String db) {
        int l, r = url.length(), len = r, ll, rr;
        if ((l = url.indexOf("//")) == -1) {
            return url;
        }

        ll = url.indexOf("/", l + 2);

        rr = url.indexOf("?", l);

        if (rr > -1) {
            r = rr;
        }

        l = ll > -1 ? ll : r - 1;
        if (l > r) {
            ll = -1;
            l = r - 1;
        }

        return url.substring(0, l + 1) + (ll > -1 ? "" : "/") + db + url.substring(r, len);
    }

    private static String getDB(String url) {
        int l, r = url.length(), ll, rr;
        if ((l = url.indexOf("//")) == -1) {
            return "";
        }

        if ((ll = url.indexOf("/", l + 2)) == -1) {
            return "";
        }

        rr = url.indexOf("?", l);

        if (rr > -1) {
            r = rr;
        }

        if (ll > r) {
            return "";
        } else {
            l = ll;
        }

        return url.substring(l + 1, r);
    }

    public DBHelper(ConfigContext context) {
        this.context = context;
        dataSource = initDataSource(this.context);
        queryRunner = new QueryRunner(dataSource);
    }

    public List<Map<String, Object>> descTable(String table) {

        List<Map<String, Object>> result;

        if (context.getJdbc().getDriver().contains("mysql")) {
            result = queryMapList(
                    String.format("select * from columns where table_schema = '%s' and table_name='%s'", getDB(context.getJdbc().getUrl()), table)
                    , null);
            for (Map<String, Object> row : result) {
                row.put("FIELD", row.get("COLUMN_NAME"));
                row.put("KEY", row.get("COLUMN_KEY"));
                row.put("TYPE", row.get("DATA_TYPE"));

            }
        } else {
            result = queryMapList(
                    String.format("desc %s", table)
                    , null);
        }

        return result;
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
