/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.FieldItem;
import java.util.List;

public class SqlItem {
    private String executorSql;
    private List<Object> paramValues;
    private List<FieldItem> innerfieldItems;

    public String getExecutorSql() {
        return this.executorSql;
    }

    public void setExecutorSql(String executorSql) {
        this.executorSql = executorSql;
    }

    public List<Object> getParamValues() {
        return this.paramValues;
    }

    public void setParamValues(List<Object> paramValues) {
        this.paramValues = paramValues;
    }

    public List<FieldItem> getInnerfieldItems() {
        return this.innerfieldItems;
    }

    public void setInnerfieldItems(List<FieldItem> innerfieldItems) {
        this.innerfieldItems = innerfieldItems;
    }
}

