/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.common;

public enum ArchiveResAppEnum {
    CORPORATE_RES_APP("YT0020"),
    MANAGEMENT_RES_APP("YT0030");

    private String resAppCode;

    private ArchiveResAppEnum(String resAppCode) {
        this.resAppCode = resAppCode;
    }

    public String getResAppCode() {
        return this.resAppCode;
    }
}

