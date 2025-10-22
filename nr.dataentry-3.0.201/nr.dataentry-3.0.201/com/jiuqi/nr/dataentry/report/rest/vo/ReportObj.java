/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.report.rest.vo;

import java.io.Serializable;

public class ReportObj
implements Serializable {
    private static final long serialVersionUID = -18542935855317565L;
    private String key;
    private String fileKey;
    private String fileNameExp;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getFileNameExp() {
        return this.fileNameExp;
    }

    public void setFileNameExp(String fileNameExp) {
        this.fileNameExp = fileNameExp;
    }
}

