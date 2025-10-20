/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.common;

import com.jiuqi.dc.mappingscheme.impl.common.DateTableEnum;
import java.util.List;

public class DataTableInfo {
    private String tableName;
    private DateTableEnum dateTableEnum;
    private String sql;
    private String primaryFields;
    private List<String> indexSqlList;

    public DataTableInfo(String tableName, DateTableEnum dateTableEnum) {
        this.tableName = tableName;
        this.dateTableEnum = dateTableEnum;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DateTableEnum getDateTableEnum() {
        return this.dateTableEnum;
    }

    public void setDateTableEnum(DateTableEnum dateTableEnum) {
        this.dateTableEnum = dateTableEnum;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getPrimaryFields() {
        return this.primaryFields;
    }

    public void setPrimaryFields(String primaryFields) {
        this.primaryFields = primaryFields;
    }

    public List<String> getIndexSqlList() {
        return this.indexSqlList;
    }

    public void setIndexSqlList(List<String> indexSqlList) {
        this.indexSqlList = indexSqlList;
    }

    public String toString() {
        return "DataTableInfo{tableName='" + this.tableName + '\'' + ", dateTableEnum=" + (Object)((Object)this.dateTableEnum) + ", sql='" + this.sql + '\'' + ", primaryFields='" + this.primaryFields + '\'' + ", indexSql=" + this.indexSqlList + '}';
    }
}

