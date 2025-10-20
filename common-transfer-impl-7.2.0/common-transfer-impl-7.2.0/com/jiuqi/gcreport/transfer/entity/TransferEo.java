/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBIndex
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.transfer.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.lang.reflect.Field;
import java.util.Date;

@DBTable(name="GC_TRANSFERINFO", title="\u5217\u9009\u4fe1\u606f\u5b58\u50a8\u8868", inStorage=true)
@DBIndex(columnsFields={"USERID", "URLPATH"})
public class TransferEo
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_TRANSFERINFO";
    @DBColumn(nameInDB="USERID", title="\u884c\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=36)
    public String userId;
    @DBColumn(nameInDB="URLPATH", title="\u8def\u5f84", dbType=DBColumn.DBType.NVarchar, length=300)
    public String path;
    @DBColumn(nameInDB="TBCOLUMNS", title="\u9009\u4e2d\u7684\u5217", dbType=DBColumn.DBType.NVarchar, length=1000)
    private String columns;
    @DBColumn(nameInDB="CREATOR", title="\u521b\u5efa\u4eba", dbType=DBColumn.DBType.NVarchar, length=60)
    private String creator;
    @DBColumn(nameInDB="CREATETIME", title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date createTime;
    @DBColumn(nameInDB="UPDATOR", title="\u4fee\u6539\u4eba", dbType=DBColumn.DBType.NVarchar, length=60)
    private String updator;
    @DBColumn(nameInDB="UPDATETIME", title="\u4fee\u6539\u65f6\u95f4", dbType=DBColumn.DBType.DateTime)
    private Date updateTime;

    public static String getAllFieldSql(String tableAlias) {
        String prefix = "";
        String asStr = "";
        if (tableAlias != null && tableAlias.trim().length() > 0) {
            prefix = tableAlias + ".";
            asStr = "as";
        }
        String fieldSql = " " + prefix + "ID " + asStr + " id," + prefix + "RECVER " + asStr + " recver, ";
        Class<TransferEo> clazz = TransferEo.class;
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; ++i) {
            if ("TABLENAME".equals(fields[i].getName()) || fields[i].getAnnotation(DBColumn.class) == null) continue;
            fieldSql = fieldSql + prefix + fields[i].getAnnotation(DBColumn.class).nameInDB().toLowerCase() + " " + asStr + " " + fields[i].getName() + ",";
        }
        return String.format(fieldSql.substring(0, fieldSql.length() - 1), new Object[0]);
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String uuid) {
        this.userId = uuid;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getColumns() {
        return this.columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdator() {
        return this.updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static String getTablename() {
        return TABLENAME;
    }
}

