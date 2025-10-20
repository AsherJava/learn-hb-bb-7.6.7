/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.loader;

import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.sql.loader.Record;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ITableWriter {
    public void setTableName(String var1);

    public List<LogicField> getFields();

    public void addField(String var1, String var2, int var3);

    public void open() throws SQLException;

    public void insert(Record var1) throws SQLException;

    public void close() throws SQLException;

    public void setOption(String var1, String var2);

    public String getOption(String var1);

    public int insertResultSet(ResultSet var1) throws SQLException;

    public int insertResultSet(ResultSet var1, Map<String, String> var2) throws SQLException;
}

