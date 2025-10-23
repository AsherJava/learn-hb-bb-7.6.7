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
import com.jiuqi.nr.summary.api.SummaryFormula;
import java.sql.Clob;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_SUMMARY_FORMULA")
public class SummaryFormulaDO
implements SummaryFormula {
    @DBAnno.DBField(dbField="SF_KEY", isPk=true)
    protected String key;
    @DBAnno.DBField(dbField="SF_CODE")
    protected String name;
    @DBAnno.DBField(dbField="SS_KEY")
    protected String summarySolutionKey;
    @DBAnno.DBField(dbField="SR_KEY")
    protected String summaryReportKey;
    @DBAnno.DBField(dbField="SF_USE_CALCULATE", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean useCalculate;
    @DBAnno.DBField(dbField="SF_USE_CHECK", tranWith="transBoolean", dbType=Integer.class, appType=Boolean.class)
    protected boolean useCheck;
    @DBAnno.DBField(dbField="SF_CHECK_TYPE")
    protected int checkType;
    @DBAnno.DBField(dbField="SF_EXPRESSION", tranWith="transClob", dbType=Clob.class, appType=String.class)
    protected String expression;
    @DBAnno.DBField(dbField="SF_DESC")
    protected String desc;
    @DBAnno.DBField(dbField="SF_MODIFY_TIME", tranWith="transInstant", dbType=Timestamp.class, appType=Instant.class)
    protected Instant modifyTime;
    @DBAnno.DBField(dbField="SF_ORDER", isOrder=true)
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

    @Override
    public String getDesc() {
        return this.desc;
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

    @Override
    public int getCheckType() {
        return this.checkType;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSummarySolutionKey(String summarySolutionKey) {
        this.summarySolutionKey = summarySolutionKey;
    }

    public void setSummaryReportKey(String summaryReportKey) {
        this.summaryReportKey = summaryReportKey;
    }

    public void setUseCalculate(boolean useCalculate) {
        this.useCalculate = useCalculate;
    }

    public void setUseCheck(boolean useCheck) {
        this.useCheck = useCheck;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setOrder(String order) {
        this.order = order;
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

