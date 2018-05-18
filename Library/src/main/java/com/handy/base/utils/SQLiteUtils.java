package com.handy.base.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.blankj.utilcode.util.ObjectUtils;
import com.handy.base.utils.bean.SQLTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库公共类，提供基本数据库操作
 * <p>
 * Created by LiuJie on 2016/10/25.
 */
public class SQLiteUtils {
    private static SQLiteUtils sqLiteUtils = null;

    private Context context;
    /**
     * 查询游标对象
     */
    private Cursor cursor;
    /**
     * 数据库版本
     */
    private int DB_VERSION = 1;
    /**
     * 默认数据库
     */
    private String DB_NAME = "HandyBase.db";
    /**
     * 由SQLiteOpenHelper继承过来
     */
    private DatabaseHelper databaseHelper = null;
    /**
     * 执行open()打开数据库时，保存返回的数据库对象
     */
    private SQLiteDatabase sqLiteDatabase = null;
    /**
     * 数据库表对象数据集合
     */
    private List<SQLTable> sqLiteTables = new ArrayList<>();

    private SQLiteUtils(Context context) {
        this.context = context;
    }

    private SQLiteUtils(Context context, int DB_VERSION, String DB_NAME) {
        this.context = context;
        this.DB_VERSION = DB_VERSION;
        this.DB_NAME = DB_NAME;
    }

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public static SQLiteUtils getInstance(Context context) {
        if (sqLiteUtils == null) {
            sqLiteUtils = new SQLiteUtils(context);
        }
        return sqLiteUtils;
    }

    /**
     * 构造函数
     *
     * @param context    上下文
     * @param DB_NAME    数据库名称
     * @param DB_VERSION 数据库版本号
     */
    public static SQLiteUtils getInstance(Context context, int DB_VERSION, String DB_NAME) {
        return new SQLiteUtils(context, DB_VERSION, DB_NAME);
    }

    /**
     * 添加或更新数据库中的表
     *
     * @param sqlTable 数据库表
     */
    public void insertSQLTable(SQLTable sqlTable) {
        if (sqLiteTables.size() == 0) {
            sqLiteTables.add(sqlTable);
        } else if (sqLiteTables.size() > 0) {
            if (sqLiteTables.contains(sqlTable)) {
                sqLiteTables.remove(sqlTable);
                sqLiteTables.add(sqlTable);
            }
        }
    }

    /**
     * 打开数据库
     */
    public void open() {
        if (databaseHelper == null || sqLiteDatabase == null) {
            this.databaseHelper = new DatabaseHelper(context);
            sqLiteDatabase = databaseHelper.getWritableDatabase();
        }
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (null != databaseHelper) {
            databaseHelper.close();
            databaseHelper = null;
            sqLiteDatabase = null;
        }
        if (null != cursor) {
            cursor.close();
            cursor = null;
        }
    }

    /**
     * 执行sql语句，包括创建表、删除、插入
     *
     * @param sql
     */
    public void executeSql(String sql) {
        try {
            open();
            sqLiteDatabase.execSQL(sql);
            close();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 插入数据
     *
     * @param tableName     表名
     * @param nullColumn    null
     * @param contentValues 名值对
     * @return 新插入数据的ID，错误返回-1
     * @throws Exception
     */
    public long insert(String tableName, String nullColumn, ContentValues contentValues) {
        try {
            open();
            return sqLiteDatabase.insert(tableName, nullColumn, contentValues);
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    /**
     * 通过主键ID删除数据
     *
     * @param tableName 表名
     * @param key       主键名
     * @param id        主键值
     * @return 受影响的记录数
     * @throws Exception
     */
    public long delete(String tableName, String key, int id) {
        try {
            open();
            return sqLiteDatabase.delete(tableName, key + " = " + id, null);
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    /**
     * 查找表的所有数据
     *
     * @param sql 查询SQL语句
     * @return
     * @throws Exception
     */
    public Cursor findBySQL(String sql, String[] selectionArgs) {
        try {
            open();
            cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
            return cursor;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 查找表的所有数据
     *
     * @param tableName 表名
     * @param columns   要查询的列名，可以是多个，可以为null
     * @return
     * @throws Exception
     */
    public Cursor findAll(String tableName, String[] columns) {
        try {
            open();
            cursor = sqLiteDatabase.query(tableName, columns, null, null, null, null, null);
            return cursor;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据主键查找数据
     *
     * @param tableName 表名
     * @param key       主键名
     * @param id        主键值
     * @param columns   要查询的列名，可以是多个，可以为null
     * @return Cursor游标
     * @throws Exception
     */
    public Cursor findById(String tableName, String key, int id, String[] columns) {
        try {
            open();
            cursor = sqLiteDatabase.query(tableName, columns, key + " = " + id, null, null, null, null);
            return cursor;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据主键查找数据
     *
     * @param tableName 表名
     * @param condition 查询条件 " id = 1 and name = A and ..."
     * @param columns   要查询的列名，可以是多个，可以为null
     * @return Cursor游标
     * @throws Exception
     */
    public Cursor findByCondition(String tableName, String condition, String[] columns) {
        try {
            open();
            cursor = sqLiteDatabase.query(tableName, columns, condition, null, null, null, null);
            return cursor;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据条件查询数据
     *
     * @param tableName   表名
     * @param names       查询条件
     * @param condition   查询符号
     * @param values      查询条件值
     * @param columns     要查询的列名，可以是多个，可以为null
     * @param orderColumn 排序的列，可以为null
     * @param limit       限制返回数
     * @return Cursor游标
     * @throws Exception
     */
    public Cursor findLinkAnd(String tableName, String[] names, String[] condition, String[] values, String[] columns, String orderColumn, String limit) {
        try {
            open();
            StringBuffer selection = new StringBuffer();
            for (int i = 0; i < names.length; i++) {
                selection.append(names[i]);
                selection.append(condition[i] + " ?");
                if (i != names.length - 1) {
                    selection.append(" and ");
                }
            }
            cursor = sqLiteDatabase.query(true, tableName, columns, selection.toString(), values, null, null, orderColumn, limit);
            return cursor;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 根据条件查询数据
     *
     * @param tableName   表名
     * @param names       查询条件
     * @param condition   查询符号
     * @param values      查询条件值
     * @param columns     要查询的列名，可以是多个，可以为null
     * @param orderColumn 排序的列，可以为null
     * @param limit       限制返回数
     * @return Cursor游标
     * @throws Exception
     */
    public Cursor findLinkOr(String tableName, String[] names, String[] condition, String[] values, String[] columns, String orderColumn, String limit) {
        try {
            open();
            StringBuffer selection = new StringBuffer();
            for (int i = 0; i < names.length; i++) {
                selection.append(names[i]);
                selection.append(condition[i] + " ?");
                if (i != names.length - 1) {
                    selection.append(" or ");
                }
            }
            cursor = sqLiteDatabase.query(true, tableName, columns, selection.toString(), values, null, null, orderColumn, limit);
            return cursor;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param tableName 表名
     * @param names     查询条件
     * @param values    查询条件值
     * @param args      更新列-值对
     * @return true或false
     * @throws Exception
     */
    public boolean udpate(String tableName, String[] names, String[] values, ContentValues args) {
        try {
            open();
            StringBuffer selection = new StringBuffer();
            for (int i = 0; i < names.length; i++) {
                selection.append(names[i]);
                selection.append(" = ?");
                if (i != names.length - 1) {
                    selection.append("and");
                }
            }
            return sqLiteDatabase.update(tableName, args, selection.toString(), values) > 0;
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public List<SQLTable> getSqLiteTables() {
        return sqLiteTables;
    }

    public void setSqLiteTables(List<SQLTable> sqLiteTables) {
        this.sqLiteTables = sqLiteTables;
    }

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    /**
     * SQLiteOpenHelper内部类
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            //当调用getWritableDatabase()或 getReadableDatabase()方法时,创建一个数据库
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            for (SQLTable sqlTable : sqLiteTables) {
                if (ObjectUtils.isNotEmpty(sqlTable.getTableName()) && ObjectUtils.isNotEmpty(sqlTable.getCreateSQL())) {
                    db.execSQL(sqlTable.getCreateSQL());
                }
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            for (SQLTable sqlTable : sqLiteTables) {
                if (ObjectUtils.isNotEmpty(sqlTable.getTableName()) && ObjectUtils.isNotEmpty(sqlTable.getCreateSQL())) {
                    db.execSQL("DROP TABLE IF EXISTS " + sqlTable.getTableName());
                }
            }
            onCreate(db);
        }
    }
}