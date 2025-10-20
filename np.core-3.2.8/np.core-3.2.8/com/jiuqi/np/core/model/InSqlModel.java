/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.model;

import java.util.List;

public class InSqlModel {
    private String prefix;
    private String alias;
    private List<String> values;
    private String column;
    private String arg;
    private boolean addWhere;
    private boolean addAnd;
    private String orderBySql;

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<String> getValues() {
        return this.values;
    }

    public String getColumn() {
        return this.column;
    }

    public boolean isAddWhere() {
        return this.addWhere;
    }

    public boolean isAddAnd() {
        return this.addAnd;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setAddWhere(boolean addWhere) {
        this.addWhere = addWhere;
    }

    public void setAddAnd(boolean addAnd) {
        this.addAnd = addAnd;
    }

    public String getArg() {
        return this.arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOrderBySql() {
        return this.orderBySql;
    }

    public void setOrderBySql(String orderBySql) {
        this.orderBySql = orderBySql;
    }
}

