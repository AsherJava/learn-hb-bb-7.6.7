/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.summary.internal.dto;

import com.jiuqi.nr.summary.api.Ordered;
import com.jiuqi.nr.summary.api.SummaryReport;
import com.jiuqi.nr.summary.model.report.SequenceType;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public class SummaryReportDTO
implements SummaryReport {
    protected String key;
    protected String name;
    protected String title;
    protected String summarySolutionKey;
    protected SequenceType sequenceType;
    protected String filter;
    protected String config;
    protected String gridData;
    protected String pageConfig;
    protected String order;
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
    public String getSummarySolutionKey() {
        return this.summarySolutionKey;
    }

    @Override
    public SequenceType getSequenceType() {
        return this.sequenceType;
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

    public void setDesc(String desc) {
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
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

    @Override
    public int compareTo(@NotNull Ordered o) {
        return 0;
    }
}

