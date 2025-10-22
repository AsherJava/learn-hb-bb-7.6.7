/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.invest.investworkpaper.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_INVESTWORKPAPERSETTING", title="\u6295\u8d44\u5de5\u4f5c\u5e95\u7a3f\u8bbe\u7f6e")
public class InvestWorkPaperSettingEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_INVESTWORKPAPERSETTING";
    @DBColumn(nameInDB="USERID", dbType=DBColumn.DBType.Varchar, length=36)
    private String userId;
    @DBColumn(nameInDB="TASKID", dbType=DBColumn.DBType.Varchar, length=36)
    private String taskId;
    @DBColumn(nameInDB="SYSTEMID", dbType=DBColumn.DBType.Varchar, length=36)
    private String systemId;
    @DBColumn(nameInDB="ORGTYPE", dbType=DBColumn.DBType.NVarchar)
    private String orgType;
    @DBColumn(nameInDB="SETTINGDATASTR", dbType=DBColumn.DBType.Text)
    private String settingDataStr;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSettingDataStr() {
        return this.settingDataStr;
    }

    public void setSettingDataStr(String settingDataStr) {
        this.settingDataStr = settingDataStr;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

