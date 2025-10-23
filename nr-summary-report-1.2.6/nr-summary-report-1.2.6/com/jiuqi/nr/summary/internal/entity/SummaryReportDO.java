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
import com.jiuqi.nr.summary.api.SummaryReport;
import com.jiuqi.nr.summary.model.report.SequenceType;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_SUMMARY_REPORT")
public class SummaryReportDO
implements SummaryReport {
    @DBAnno.DBField(dbField="SR_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="SR_NAME")
    protected String name;
    @DBAnno.DBField(dbField="SR_TITLE")
    protected String title;
    @DBAnno.DBField(dbField="SS_KEY")
    protected String summarySolutionKey;
    @DBAnno.DBField(dbField="SR_SEQUENCE_TYPE", tranWith="transSequenceType", dbType=Integer.class, appType=SequenceType.class)
    protected SequenceType sequenceType;
    @DBAnno.DBField(dbField="SR_FILTER")
    protected String filter;
    @DBAnno.DBField(dbField="SR_CONFIG", tranWith="transClob", dbType=Clob.class, appType=String.class)
    protected String config;
    @DBAnno.DBField(dbField="SR_GRID", tranWith="transClob", dbType=Clob.class, appType=String.class)
    protected String gridData;
    @DBAnno.DBField(dbField="SR_PAGE_CONFIG", tranWith="transClob", dbType=Clob.class, appType=String.class)
    protected String pageConfig;
    @DBAnno.DBField(dbField="SR_ORDER", isOrder=true)
    protected String order;
    @DBAnno.DBField(dbField="SR_MODIFY_TIME", tranWith="transInstant", dbType=Timestamp.class, appType=Instant.class)
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
    public SequenceType getSequenceType() {
        return this.sequenceType;
    }

    @Override
    public String getSummarySolutionKey() {
        return this.summarySolutionKey;
    }

    @Override
    public String getFilter() {
        return this.filter;
    }

    @Override
    public String getConfig() {
        return this.config;
    }

    @Override
    public String getGridData() {
        return this.gridData;
    }

    @Override
    public String getPageConfig() {
        return this.pageConfig;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummarySolutionKey(String summarySolutionKey) {
        this.summarySolutionKey = summarySolutionKey;
    }

    public void setSequenceType(SequenceType sequenceType) {
        this.sequenceType = sequenceType;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public void setGridData(String gridData) {
        this.gridData = gridData;
    }

    public void setPageConfig(String pageConfig) {
        this.pageConfig = pageConfig;
    }

    public void setOrder(String order) {
        this.order = order;
    }

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

