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

@DBTable(name="GC_RTOFFSETLOG_REL", title="\u5b9e\u65f6\u62b5\u9500\u5173\u8054\u65b9\u5206\u7ec4\u65e5\u5fd7", indexs={@DBIndex(name="IDX_GC_RTOFFSETLOG_REL_LOGID", columnsFields={"LOGID"}), @DBIndex(name="IDX_GC_RTOFFSETLOG_REL_RULE", columnsFields={"UNIONRULEID"})})
public class RealTimeOffsetRelLogEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_RTOFFSETLOG_REL";
    @DBColumn(title="\u5b9e\u65f6\u62b5\u9500\u65e5\u5fd7ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String logId;
    @DBColumn(title="\u5408\u5e76\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String unionRuleId;
    @DBColumn(title="\u5173\u8054\u65b9\u5206\u7ec4key", dbType=DBColumn.DBType.Varchar, length=32, isRequired=true)
    private String relGroupKey;
    @DBColumn(title="\u8df3\u8fc7", dbType=DBColumn.DBType.Numeric, precision=1, scale=0)
    private Integer skipOffset;
    @DBColumn(title="\u8df3\u8fc7\u539f\u56e0", dbType=DBColumn.DBType.Varchar, length=200)
    private String skipReason;
    @DBColumn(title="\u5173\u8054\u65b9\u6570\u636e\u6570\u91cf", dbType=DBColumn.DBType.Int, precision=10)
    private int relNum;
    @DBColumn(title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private long beginTime;
    @DBColumn(title="\u7ed3\u675f\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private long endTime;
    @DBColumn(title="\u8017\u65f6", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private long elapsedTime;

    public String getLogId() {
        return this.logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getUnionRuleId() {
        return this.unionRuleId;
    }

    public void setUnionRuleId(String unionRuleId) {
        this.unionRuleId = unionRuleId;
    }

    public String getRelGroupKey() {
        return this.relGroupKey;
    }

    public void setRelGroupKey(String relGroupKey) {
        this.relGroupKey = relGroupKey;
    }

    public Integer getSkipOffset() {
        return this.skipOffset;
    }

    public void setSkipOffset(Integer skipOffset) {
        this.skipOffset = skipOffset;
    }

    public String getSkipReason() {
        return this.skipReason;
    }

    public void setSkipReason(String skipReason) {
        this.skipReason = skipReason;
    }

    public int getRelNum() {
        return this.relNum;
    }

    public void setRelNum(int relNum) {
        this.relNum = relNum;
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
        return "RealTimeOffsetRelLogEO{logId='" + this.logId + '\'' + ", unionRuleId='" + this.unionRuleId + '\'' + ", relGroupKey='" + this.relGroupKey + '\'' + ", skipOffset=" + this.skipOffset + ", skipReason=" + this.skipReason + ", relNum=" + this.relNum + ", beginTime=" + this.beginTime + ", endTime=" + this.endTime + ", elapsedTime=" + this.elapsedTime + '}';
    }
}

