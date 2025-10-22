/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.samecontrol.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.Date;

@DBTable(name="GC_SAMECTRL_SCHEME_MAPPING", title="\u540c\u63a7\u53d8\u52a8-\u671f\u521d\u62b5\u9500\u5206\u5f55\u4e0e\u4e0a\u5e74\u62a5\u8868\u65b9\u6848\u6620\u5c04\u8bbe\u7f6e")
public class SameCtrlChgSettingSchemeMappingEO
extends DefaultTableEntity {
    public static final String TABLENAME = "GC_SAMECTRL_SCHEME_MAPPING";
    @DBColumn(title="\u4efb\u52a1", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String currTaskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String currSchemeId;
    @DBColumn(title="\u6570\u636e\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String dataType;
    @DBColumn(title="\u4e0a\u5e74\u62a5\u8868\u4efb\u52a1", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String lastYearTaskId;
    @DBColumn(title="\u4e0a\u5e74\u62a5\u8868\u65b9\u6848", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String lastYearSchemeId;
    @DBColumn(title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date fromDate;
    @DBColumn(title="\u7ed3\u675f\u65f6\u95f4", dbType=DBColumn.DBType.Date)
    private Date toDate;

    public String getCurrTaskId() {
        return this.currTaskId;
    }

    public void setCurrTaskId(String currTaskId) {
        this.currTaskId = currTaskId;
    }

    public String getCurrSchemeId() {
        return this.currSchemeId;
    }

    public void setCurrSchemeId(String currSchemeId) {
        this.currSchemeId = currSchemeId;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getLastYearTaskId() {
        return this.lastYearTaskId;
    }

    public void setLastYearTaskId(String lastYearTaskId) {
        this.lastYearTaskId = lastYearTaskId;
    }

    public String getLastYearSchemeId() {
        return this.lastYearSchemeId;
    }

    public void setLastYearSchemeId(String lastYearSchemeId) {
        this.lastYearSchemeId = lastYearSchemeId;
    }

    public Date getFromDate() {
        return this.fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.addFieldValue("FROMDATE", fromDate);
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return this.toDate;
    }

    public void setToDate(Date toDate) {
        this.addFieldValue("TODATE", toDate);
        this.toDate = toDate;
    }
}

