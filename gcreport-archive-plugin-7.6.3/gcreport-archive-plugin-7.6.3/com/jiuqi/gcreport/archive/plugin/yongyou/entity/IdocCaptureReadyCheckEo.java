/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn
 *  com.jiuqi.gcreport.definition.impl.anno.DBColumn$DBType
 *  com.jiuqi.gcreport.definition.impl.anno.DBTable
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.archive.plugin.yongyou.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="IDOC_CAPTURE_READY_CHECK", title="\u6570\u636e\u51c6\u5907\u68c0\u67e5\u8868", inStorage=true, indexs={})
public class IdocCaptureReadyCheckEo
extends DefaultTableEntity {
    public static final String TABLENAME = "IDOC_CAPTURE_READY_CHECK";
    @DBColumn(nameInDB="ORGCODE", title="\u4f01\u4e1a\u4ee3\u7801", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String orgCode;
    @DBColumn(nameInDB="BBORGCODE", title="\u62a5\u8868\u5355\u4f4d\u7f16\u7801", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String bbOrgCode;
    @DBColumn(nameInDB="ORGNAME", title="\u5355\u4f4d\u540d\u79f0", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String orgName;
    @DBColumn(nameInDB="ACCOUNTYEAR", title="\u4f1a\u8ba1\u5e74", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String accountYear;
    @DBColumn(nameInDB="ACCOUNTMONTH", title="\u4f1a\u8ba1\u6708", dbType=DBColumn.DBType.Varchar, isRequired=false)
    private String accountMonth;
    @DBColumn(nameInDB="PERIOD", title="\u91c7\u96c6\u7684\u9636\u6bb5", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String period;
    @DBColumn(nameInDB="PERIODINDEX", title="\u65f6\u95f4\u7d22\u5f15", dbType=DBColumn.DBType.Int, isRequired=true)
    private Integer periodIndex;
    @DBColumn(nameInDB="ARCHIVETYPE", title="\u5f52\u6863\u7c7b\u578b", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String archiveType;
    @DBColumn(nameInDB="READYSTATUS", title="\u6570\u636e\u51c6\u5907\u72b6\u6001", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String readyStatus;
    @DBColumn(nameInDB="ERRORMSG", title="\u9519\u8bef\u4fe1\u606f", dbType=DBColumn.DBType.Varchar, length=1024, isRequired=false)
    private String errorMsg;
    @DBColumn(nameInDB="TS", title="\u65f6\u95f4\u6233", dbType=DBColumn.DBType.Varchar, isRequired=true)
    private String ts;

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getAccountYear() {
        return this.accountYear;
    }

    public void setAccountYear(String accountYear) {
        this.accountYear = accountYear;
    }

    public String getAccountMonth() {
        return this.accountMonth;
    }

    public void setAccountMonth(String accountMonth) {
        this.accountMonth = accountMonth;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getPeriodIndex() {
        return this.periodIndex;
    }

    public void setPeriodIndex(Integer periodIndex) {
        this.periodIndex = periodIndex;
    }

    public String getArchiveType() {
        return this.archiveType;
    }

    public void setArchiveType(String archiveType) {
        this.archiveType = archiveType;
    }

    public String getReadyStatus() {
        return this.readyStatus;
    }

    public void setReadyStatus(String readyStatus) {
        this.readyStatus = readyStatus;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTs() {
        return this.ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getBbOrgCode() {
        return this.bbOrgCode;
    }

    public void setBbOrgCode(String bbOrgCode) {
        this.bbOrgCode = bbOrgCode;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}

