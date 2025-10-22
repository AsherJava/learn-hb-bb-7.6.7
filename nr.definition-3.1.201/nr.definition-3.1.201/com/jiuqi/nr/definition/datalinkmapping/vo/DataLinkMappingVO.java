/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.datalinkmapping.vo;

public class DataLinkMappingVO {
    private String firstLinkId;
    private String secondLinkId;

    public DataLinkMappingVO() {
    }

    public DataLinkMappingVO(String leftDataLinkKey, String rightDataLinkKey) {
        this.firstLinkId = leftDataLinkKey;
        this.secondLinkId = rightDataLinkKey;
    }

    public String getFirstLinkId() {
        return this.firstLinkId;
    }

    public void setFirstLinkId(String firstLinkId) {
        this.firstLinkId = firstLinkId;
    }

    public String getSecondLinkId() {
        return this.secondLinkId;
    }

    public void setSecondLinkId(String secondLinkId) {
        this.secondLinkId = secondLinkId;
    }
}

