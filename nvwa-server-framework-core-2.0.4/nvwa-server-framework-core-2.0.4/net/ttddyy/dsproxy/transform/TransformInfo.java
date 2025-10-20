/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.transform;

import java.sql.Statement;

public class TransformInfo {
    private Class<? extends Statement> clazz;
    private String dataSourceName;
    private String query;
    private boolean isBatch;
    private int count;

    public TransformInfo() {
    }

    public TransformInfo(Class<? extends Statement> clazz, String dataSourceName, String query, boolean batch, int count) {
        this.clazz = clazz;
        this.dataSourceName = dataSourceName;
        this.query = query;
        this.isBatch = batch;
        this.count = count;
    }

    public Class<? extends Statement> getClazz() {
        return this.clazz;
    }

    public void setClazz(Class<? extends Statement> clazz) {
        this.clazz = clazz;
    }

    public String getDataSourceName() {
        return this.dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isBatch() {
        return this.isBatch;
    }

    public void setBatch(boolean batch) {
        this.isBatch = batch;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

