/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nr.io")
public class NrIoProperties {
    public static final String PROP_PREFIX = "jiuqi.nr.io";
    private int pageSize = 20000;
    private int rowsize = 5000;
    private int impThreadSize = 1;
    private int orgSize = 1000;
    private boolean exportByReader = true;

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowsize() {
        return this.rowsize;
    }

    public void setRowsize(int rowsize) {
        this.rowsize = rowsize;
    }

    public int getImpThreadSize() {
        return this.impThreadSize;
    }

    public void setImpThreadSize(int impThreadSize) {
        this.impThreadSize = impThreadSize;
    }

    public int getOrgSize() {
        return this.orgSize;
    }

    public void setOrgSize(int orgSize) {
        this.orgSize = orgSize;
    }

    public boolean isExportByReader() {
        return this.exportByReader;
    }

    public void setExportByReader(boolean exportByReader) {
        this.exportByReader = exportByReader;
    }
}

