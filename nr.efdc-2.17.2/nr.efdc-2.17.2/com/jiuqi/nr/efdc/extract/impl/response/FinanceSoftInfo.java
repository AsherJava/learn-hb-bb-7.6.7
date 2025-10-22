/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.extract.impl.response;

public class FinanceSoftInfo {
    private final String softWare = "\u4f01\u4e1a\u8d22\u52a1\u6570\u636e\u4e2d\u5fc3\uff08EFDC\uff09";
    private String softVersion;
    private String startTime;
    private String endTime;
    private String createTime;

    public String getSoftWare() {
        return "\u4f01\u4e1a\u8d22\u52a1\u6570\u636e\u4e2d\u5fc3\uff08EFDC\uff09";
    }

    public String getSoftVersion() {
        return this.softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

