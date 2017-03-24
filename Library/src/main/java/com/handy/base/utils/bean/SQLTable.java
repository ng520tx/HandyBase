package com.handy.base.utils.bean;

/**
 * Created by LiuJie on 2017/1/12.
 */

public class SQLTable {
    private String tableName;
    private String createSQL;

    public SQLTable(String tableName, String createSQL) {
        this.tableName = tableName;
        this.createSQL = createSQL;
    }

    public String getTableName() {
        return tableName;
    }

    /**
     * 数据表表名，必须与创建语句中的表名一致。
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCreateSQL() {
        return createSQL;
    }

    /**
     * 表创建语句
     *
     * @param createSQL CREATE TABLE db_user(userId INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT);
     */
    public void setCreateSQL(String createSQL) {
        this.createSQL = createSQL;
    }
}
