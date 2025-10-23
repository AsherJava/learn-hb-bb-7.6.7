/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.summary.internal.dto;

import com.jiuqi.nr.summary.api.Ordered;
import com.jiuqi.nr.summary.api.SummaryFormula;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public class SummaryFormulaDTO
implements SummaryFormula {
    protected String key;
    protected String name;
    protected String summarySolutionKey;
    protected String summaryReportKey;
    protected boolean useCalculate;
    protected boolean useCheck;
    protected int checkType;
    protected String expression;
    protected String desc;
    protected Instant modifyTime;
    protected String order;

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
        return null;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSummarySolutionKey() {
        return this.summarySolutionKey;
    }

    public void setSummarySolutionKey(String summarySolutionKey) {
        this.summarySolutionKey = summarySolutionKey;
    }

    @Override
    public String getSummaryReportKey() {
        return this.summaryReportKey;
    }

    @Override
    public boolean useCalculate() {
        return this.useCalculate;
    }

    @Override
    public boolean useCheck() {
        return this.useCheck;
    }

    public void setSummaryReportKey(String summaryReportKey) {
        this.summaryReportKey = summaryReportKey;
    }

    public boolean isUseCalculate() {
        return this.useCalculate;
    }

    public void setUseCalculate(boolean useCalculate) {
        this.useCalculate = useCalculate;
    }

    public boolean isUseCheck() {
        return this.useCheck;
    }

    public void setUseCheck(boolean useCheck) {
        this.useCheck = useCheck;
    }

    @Override
    public int getCheckType() {
        return this.checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public Instant getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int compareTo(@NotNull Ordered o) {
        return 0;
    }
}

