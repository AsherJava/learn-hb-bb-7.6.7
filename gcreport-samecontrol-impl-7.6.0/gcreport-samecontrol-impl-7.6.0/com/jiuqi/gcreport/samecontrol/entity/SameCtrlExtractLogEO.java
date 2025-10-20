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
package com.jiuqi.gcreport.samecontrol.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_SAMETRLEXTRACT_LOG", title="\u540c\u63a7\u63d0\u53d6\u65e5\u5fd7\u8868", indexs={@DBIndex(name="GC_SAMETRLEXTRACT_LOGCOM", columnsFields={"changedCode", "schemeId", "periodStr", "orgType"})})
public class SameCtrlExtractLogEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_SAMETRLEXTRACT_LOG";
    @DBColumn(title="\u64cd\u4f5c\u6807\u8bc6", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String operate;
    @DBColumn(title="\u4efb\u52a1ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String taskId;
    @DBColumn(title="\u62a5\u8868\u65b9\u6848ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String schemeId;
    @DBColumn(title="\u65f6\u671f\u7c7b\u578b9\u4f4d\u5b57\u7b26\u4e32 yyyyTmmmm", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String periodStr;
    @DBColumn(title="\u5355\u4f4d\u7c7b\u578b", dbType=DBColumn.DBType.NVarchar, isRequired=true)
    private String orgType;
    @DBColumn(title="\u53d8\u52a8\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String changedCode;
    @DBColumn(title="\u5904\u7f6e\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=60)
    private String virtualParentCode;
    @DBColumn(title="\u6536\u8d2d\u5355\u4f4d", dbType=DBColumn.DBType.Varchar, length=60)
    private String changedParentCode;
    @DBColumn(title="\u6267\u884c\u5f00\u59cb\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Long)
    private Long beginTime;
    @DBColumn(title="\u6267\u884c\u7ed3\u675f\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Long)
    private Long endTime;
    @DBColumn(title="\u6267\u884c\u72b6\u6001", dbType=DBColumn.DBType.Varchar)
    private String taskState;
    @DBColumn(title="\u6267\u884c\u4eba", dbType=DBColumn.DBType.NVarchar)
    private String userName;
    @DBColumn(title="\u662f\u5426\u6700\u65b0\u8bb0\u5f55", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer latestFlag;
    @DBColumn(title="\u6267\u884c\u65e5\u5fd7", dbType=DBColumn.DBType.Text)
    private String info;

    public String getOperate() {
        return this.operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getChangedCode() {
        return this.changedCode;
    }

    public void setChangedCode(String changedCode) {
        this.changedCode = changedCode;
    }

    public String getVirtualParentCode() {
        return this.virtualParentCode;
    }

    public void setVirtualParentCode(String virtualParentCode) {
        this.virtualParentCode = virtualParentCode;
    }

    public String getChangedParentCode() {
        return this.changedParentCode;
    }

    public void setChangedParentCode(String changedParentCode) {
        this.changedParentCode = changedParentCode;
    }

    public Long getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getTaskState() {
        return this.taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLatestFlag() {
        return this.latestFlag;
    }

    public void setLatestFlag(Integer latestFlag) {
        this.latestFlag = latestFlag;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

