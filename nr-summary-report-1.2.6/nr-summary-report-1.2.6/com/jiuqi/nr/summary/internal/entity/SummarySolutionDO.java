/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.summary.internal.entity;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.summary.api.Ordered;
import com.jiuqi.nr.summary.api.SummarySolution;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_SUMMARY_SOLUTION")
public class SummarySolutionDO
implements SummarySolution {
    @DBAnno.DBField(dbField="SS_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="SS_NAME")
    protected String name;
    @DBAnno.DBField(dbField="SS_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="SG_KEY")
    protected String group;
    @DBAnno.DBField(dbField="SS_MAINTASK")
    protected String mainTask;
    @DBAnno.DBField(dbField="SS_DATA", tranWith="transClob", dbType=Clob.class, appType=String.class)
    protected String taskConfigData;
    @DBAnno.DBField(dbField="SS_TARGETDIMENSION")
    protected String targetDimension;
    @DBAnno.DBField(dbField="SS_ORDER", notUpdate=true, isOrder=true)
    protected String order;
    @DBAnno.DBField(dbField="SS_MODIFY_TIME", tranWith="transInstant", dbType=Timestamp.class, appType=Instant.class)
    protected Instant modifyTime;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDesc() {
        return null;
    }

    @Override
    public Instant getModifyTime() {
        return this.modifyTime;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public String getMainTask() {
        return this.mainTask;
    }

    @Override
    public String getTaskConfigData() {
        return this.taskConfigData;
    }

    @Override
    public String getTargetDimension() {
        return this.targetDimension;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDesc(String desc) {
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setMainTask(String mainTask) {
        this.mainTask = mainTask;
    }

    public void setTaskConfigData(String taskConfigData) {
        this.taskConfigData = taskConfigData;
    }

    public void setTargetDimension(String targetDimension) {
        this.targetDimension = targetDimension;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return 1;
        }
        if (this.order == null) {
            return -1;
        }
        return this.order.compareTo(o.getOrder());
    }
}

