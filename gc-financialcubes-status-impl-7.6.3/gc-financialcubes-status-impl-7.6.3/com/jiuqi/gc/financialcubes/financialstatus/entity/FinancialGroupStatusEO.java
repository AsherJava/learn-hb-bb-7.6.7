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
package com.jiuqi.gc.financialcubes.financialstatus.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_FINANCIAL_GROUP_STATUS", title="\u96c6\u56e2\u5f00\u5173\u8d26\u72b6\u6001\u8868")
@DBIndex(name="IDX_FINGROUPSTATUS_D_P", columnsFields={"PERIODTYPE"})
public class FinancialGroupStatusEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_FINANCIAL_GROUP_STATUS";
    @DBColumn(nameInDB="DATATIME", title="\u65f6\u671f", dbType=DBColumn.DBType.Varchar, length=9, isRequired=true)
    private String dataTime;
    @DBColumn(nameInDB="PERIODTYPE", title="\u65f6\u671f\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, length=4, isRequired=true)
    private String periodType;
    @DBColumn(nameInDB="CREATOR", title="\u64cd\u4f5c\u4eba", dbType=DBColumn.DBType.Varchar, length=60, isRequired=true)
    private String createUser;
    @DBColumn(nameInDB="STATUS", title="\u72b6\u6001", dbType=DBColumn.DBType.Varchar, length=4, isRequired=true)
    private String status;
    @DBColumn(nameInDB="UPDATETIME", title="\u66f4\u65b0\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, isRequired=true)
    private Date updateTime;
    @DBColumn(nameInDB="MODULECODE", title="\u6a21\u5757\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=60)
    private String moduleCode;

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
}

