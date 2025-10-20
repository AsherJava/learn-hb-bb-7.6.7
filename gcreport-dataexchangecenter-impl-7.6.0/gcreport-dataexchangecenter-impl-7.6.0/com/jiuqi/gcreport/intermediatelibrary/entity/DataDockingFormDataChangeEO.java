/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.intermediatelibrary.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_FORMDATACHANGE", title="\u62a5\u8868\u8868\u5355\u6570\u636e\u53d8\u52a8\u8868")
public class DataDockingFormDataChangeEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_FORMDATACHANGE";
    @DBColumn(title="\u5355\u4f4d\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, length=200)
    private String unitCode;
    @DBColumn(title="\u4efb\u52a1\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=50)
    private String taskCode;
    @DBColumn(title="\u65f6\u671f", dbType=DBColumn.DBType.Varchar, length=50)
    private String dataTime;
    @DBColumn(title="\u8868\u5355\u6807\u8bc6", dbType=DBColumn.DBType.Varchar, length=50)
    private String formCode;
    @DBColumn(title="\u7269\u7406\u8868\u540d", dbType=DBColumn.DBType.Varchar, length=50)
    private String tableCode;
    @DBColumn(title="\u7ef4\u5ea6\u4fe1\u606f", dbType=DBColumn.DBType.Varchar, length=1000)
    private String dimStr;
    @DBColumn(title="\u521b\u5efa\u65f6\u95f4", dbType=DBColumn.DBType.DateTime, length=60)
    private Date createTime;

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getDimStr() {
        return this.dimStr;
    }

    public void setDimStr(String dimStr) {
        this.dimStr = dimStr;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }
}

