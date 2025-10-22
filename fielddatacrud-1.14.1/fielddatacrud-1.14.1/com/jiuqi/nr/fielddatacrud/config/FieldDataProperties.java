/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nr.fieldcrud")
public class FieldDataProperties {
    private int batchSize = 1000;
    private int rowMaxSize = 200000;

    public int getBatchSize() {
        return this.batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getRowMaxSize() {
        return this.rowMaxSize;
    }

    public void setRowMaxSize(int rowMaxSize) {
        this.rowMaxSize = rowMaxSize;
    }
}

