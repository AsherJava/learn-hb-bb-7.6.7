/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl.response;

import com.jiuqi.nr.efdc.extract.impl.response.FinanceSoftInfo;
import com.jiuqi.nr.efdc.extract.impl.response.ResultListing;

public class DataFetchResponser {
    private FinanceSoftInfo financeSoft;
    private ResultListing resultListing;
    private String fetchDataSql;

    public FinanceSoftInfo getFinanceSoft() {
        return this.financeSoft;
    }

    public void setFinanceSoft(FinanceSoftInfo financeSoft) {
        this.financeSoft = financeSoft;
    }

    public ResultListing getResultListing() {
        return this.resultListing;
    }

    public void setResultListing(ResultListing resultListing) {
        this.resultListing = resultListing;
    }

    public String getFetchDataSql() {
        return this.fetchDataSql;
    }

    public void setFetchDataSql(String fetchDataSql) {
        this.fetchDataSql = fetchDataSql;
    }
}

