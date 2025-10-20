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
package com.jiuqi.gcreport.inputdata.inputdata.entity;

import com.jiuqi.gcreport.definition.impl.anno.DBColumn;
import com.jiuqi.gcreport.definition.impl.anno.DBIndex;
import com.jiuqi.gcreport.definition.impl.anno.DBTable;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;

@DBTable(name="GC_RTOFFSETLOG_DETAIL", title="\u5b9e\u65f6\u62b5\u9500\u65e5\u5fd7\u8be6\u60c5", indexs={@DBIndex(name="IDX_GC_RTOLOG_DETAIL_RELID", columnsFields={"RELLOGID"}), @DBIndex(name="IDX_GC_RTOLOG_DETAIL_RULE", columnsFields={"UNIONRULEID"}), @DBIndex(name="IDX_GC_RTOLOG_DETAIL_LOGID", columnsFields={"LOGID"})})
public class RealTimeOffsetDetailLogEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_RTOFFSETLOG_DETAIL";
    @DBColumn(title="\u5b9e\u65f6\u62b5\u9500\u5173\u8054\u65b9\u65e5\u5fd7ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String relLogId;
    @DBColumn(title="\u5408\u5e76\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String unionRuleId;
    @DBColumn(title="\u5b9e\u65f6\u62b5\u9500\u65e5\u5fd7ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String logId;
    @DBColumn(title="\u501f\u65b9\u6570\u91cf", dbType=DBColumn.DBType.Int, precision=10)
    private int debitNum;
    @DBColumn(title="\u8d37\u65b9\u6570\u91cf", dbType=DBColumn.DBType.Int, precision=10)
    private int creditNum;
    @DBColumn(title="\u6709\u5e8f\u62b5\u9500", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer sortOffset;
    @DBColumn(title="\u5339\u914d\u6b21\u6570", dbType=DBColumn.DBType.Int, precision=10)
    private int matchedNum;
    @DBColumn(title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private long beginTime;
    @DBColumn(title="\u7ed3\u675f\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private long endTime;
    @DBColumn(title="\u8017\u65f6", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private long elapsedTime;

    public String getRelLogId() {
        return this.relLogId;
    }

    public void setRelLogId(String relLogId) {
        this.relLogId = relLogId;
    }

    public String getUnionRuleId() {
        return this.unionRuleId;
    }

    public void setUnionRuleId(String unionRuleId) {
        this.unionRuleId = unionRuleId;
    }

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public int getDebitNum() {
        return this.debitNum;
    }

    public void setDebitNum(int debitNum) {
        this.debitNum = debitNum;
    }

    public int getCreditNum() {
        return this.creditNum;
    }

    public void setCreditNum(int creditNum) {
        this.creditNum = creditNum;
    }

    public Integer isSortOffset() {
        return this.sortOffset;
    }

    public void setSortOffset(Integer sortOffset) {
        this.sortOffset = sortOffset;
    }

    public int getMatchedNum() {
        return this.matchedNum;
    }

    public void setMatchedNum(int matchedNum) {
        this.matchedNum = matchedNum;
    }

    public long getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String toString() {
        return "RealTimeOffsetDetailLogEO{relLogId='" + this.relLogId + '\'' + ", unionRuleId='" + this.unionRuleId + '\'' + ", logId='" + this.logId + '\'' + ", debitNum=" + this.debitNum + ", creditNum=" + this.creditNum + ", sortOffset=" + this.sortOffset + ", matchedNum=" + this.matchedNum + ", beginTime=" + this.beginTime + ", endTime=" + this.endTime + ", elapsedTime=" + this.elapsedTime + '}';
    }
}

