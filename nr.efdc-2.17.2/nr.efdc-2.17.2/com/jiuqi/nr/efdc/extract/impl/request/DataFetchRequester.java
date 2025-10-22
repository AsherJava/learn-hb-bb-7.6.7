/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.efdc.extract.impl.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.efdc.extract.impl.request.DataFetchEnv;
import com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing;
import com.jiuqi.nr.efdc.extract.impl.request.ReportSoftInfo;

public class DataFetchRequester {
    private DataFetchEnv fetchEnv;
    private ReportSoftInfo reportSoft;
    private ExpressionListing expListing;
    private boolean isReturnSql = false;

    public DataFetchEnv getFetchEnv() {
        return this.fetchEnv;
    }

    public void setFetchEnv(DataFetchEnv fetchEnv) {
        this.fetchEnv = fetchEnv;
    }

    public ReportSoftInfo getReportSoft() {
        return this.reportSoft;
    }

    public void setReportSoft(ReportSoftInfo reportSoft) {
        this.reportSoft = reportSoft;
    }

    public ExpressionListing getExpListing() {
        return this.expListing;
    }

    public void setExpListing(ExpressionListing expListing) {
        this.expListing = expListing;
    }

    @JsonProperty(value="isReturnSql")
    public boolean isReturnSql() {
        return this.isReturnSql;
    }

    public void setReturnSql(boolean isReturnSql) {
        this.isReturnSql = isReturnSql;
    }
}

