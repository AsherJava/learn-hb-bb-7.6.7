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

@DBTable(name="GC_RTOFFSETLOG", title="\u5b9e\u65f6\u62b5\u9500\u65e5\u5fd7", indexs={@DBIndex(name="IDX_GC_RTOFFSETLOG_RULE", columnsFields={"UNIONRULEID"})})
public class RealTimeOffsetLogEO
extends DefaultTableEntity {
    private static final long serialVersionUID = 1L;
    public static final String TABLENAME = "GC_RTOFFSETLOG";
    @DBColumn(title="\u5408\u5e76\u89c4\u5219ID", dbType=DBColumn.DBType.Varchar, length=36, isRequired=true)
    private String unionRuleId;
    @DBColumn(title="\u5185\u90e8\u8868\u6570\u636e\u53d8\u66f4\u6570\u91cf", dbType=DBColumn.DBType.Int, precision=10)
    private int changedNum;
    @DBColumn(title="\u53d8\u66f4\u6570\u636e\u5173\u8054\u65b9\u5206\u7ec4\u6570\u91cf", dbType=DBColumn.DBType.Int, precision=10)
    private int relGroupNum;
    @DBColumn(title="\u5f00\u59cb\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private long beginTime;
    @DBColumn(title="\u7ed3\u675f\u65f6\u95f4", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private long endTime;
    @DBColumn(title="\u8017\u65f6", dbType=DBColumn.DBType.Numeric, precision=19, scale=0)
    private long elapsedTime;

    public String getUnionRuleId() {
        return this.unionRuleId;
    }

    public void setUnionRuleId(String unionRuleId) {
        this.unionRuleId = unionRuleId;
    }

    public int getChangedNum() {
        return this.changedNum;
    }

    public void setChangedNum(int changedNum) {
        this.changedNum = changedNum;
    }

    public int getRelGroupNum() {
        return this.relGroupNum;
    }

    public void setRelGroupNum(int relGroupNum) {
        this.relGroupNum = relGroupNum;
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
        return "RealTimeOffsetLogEO{unionRuleId='" + this.unionRuleId + '\'' + ", changedNum=" + this.changedNum + ", relGroupNum=" + this.relGroupNum + ", beginTime=" + this.beginTime + ", endTime=" + this.endTime + ", elapsedTime=" + this.elapsedTime + '}';
    }
}

