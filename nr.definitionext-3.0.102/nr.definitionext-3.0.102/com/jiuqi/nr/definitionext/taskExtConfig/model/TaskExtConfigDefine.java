/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definitionext.taskExtConfig.model;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import java.sql.Clob;

@DBAnno.DBTable(dbTable="SYS_FORMSCHEMA_EXTPARA")
public class TaskExtConfigDefine {
    @DBAnno.DBField(dbField="EXTKEY", dbType=String.class, isPk=true)
    private String extKey;
    @DBAnno.DBField(dbField="TASKKEY", dbType=String.class, isPk=false)
    private String taskKey;
    @DBAnno.DBField(dbField="SCHEMAKEY", dbType=String.class, isPk=false)
    private String schemaKey;
    @DBAnno.DBField(dbField="EXTDATA", dbType=Clob.class)
    private String extData;
    @DBAnno.DBField(dbField="EXTTYPE", dbType=String.class, isPk=false)
    private String extType;
    @DBAnno.DBField(dbField="EXTCODE", dbType=String.class, isPk=false)
    private String extCode;

    public String getExtKey() {
        return this.extKey;
    }

    public void setExtKey(String extKey) {
        this.extKey = extKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getSchemaKey() {
        return this.schemaKey;
    }

    public void setSchemaKey(String schemaKey) {
        this.schemaKey = schemaKey;
    }

    public String getExtData() {
        return this.extData;
    }

    public void setExtData(String extData) {
        this.extData = extData;
    }

    public String getExtType() {
        return this.extType;
    }

    public void setExtType(String extType) {
        this.extType = extType;
    }

    public String getExtCode() {
        return this.extCode;
    }

    public void setExtCode(String extCode) {
        this.extCode = extCode;
    }
}

