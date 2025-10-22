/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.consolidatedsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_CONSSYSTEM", title="\u5408\u5e76\u4f53\u7cfb", inStorage=true)
public class ConsolidatedSystemEO
extends DefaultTableEntity {
    public static final String ENTITYNAME = "consSystem";
    public static final String TABLENAME = "GC_CONSSYSTEM";
    @DBColumn(nameInDB="SYSTEMNAME", dbType=DBColumn.DBType.NVarchar, length=60)
    private String systemName;
    @DBColumn(nameInDB="DATASCHEMEKEY", title="\u6570\u636e\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36)
    private String dataSchemeKey;
    @DBColumn(nameInDB="DESCRIPTION", dbType=DBColumn.DBType.NVarchar, length=200)
    private String description;
    @DBColumn(nameInDB="DATASORT", dbType=DBColumn.DBType.NVarchar)
    private String dataSort;
    @DBColumn(nameInDB="CREATETIME", dbType=DBColumn.DBType.Date)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
    @DBColumn(nameInDB="UPDATETIME", dbType=DBColumn.DBType.Date)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;
    @DBColumn(nameInDB="CREATEUSER", dbType=DBColumn.DBType.NVarchar, length=36)
    private String createUser;

    public String getSystemName() {
        return this.systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataSort() {
        return this.dataSort;
    }

    public void setDataSort(String dataSort) {
        this.dataSort = dataSort;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }
}

