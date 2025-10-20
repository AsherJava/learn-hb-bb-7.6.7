/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.reportdatasync.enums;

import java.util.Arrays;
import java.util.List;

public enum ReportFileFormat {
    NRD("nrd", "\u6807\u51c6"),
    JIO("jio", "jio"),
    YG("yg", "\u5f02\u6784\u7cfb\u7edf");

    private String code;
    private String name;

    private ReportFileFormat(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<ReportFileFormat> listAllReportFileFormat() {
        return Arrays.asList(ReportFileFormat.values());
    }
}

