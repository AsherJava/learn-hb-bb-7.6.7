/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.RegionTypeEnum
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import com.jiuqi.bde.common.constant.RegionTypeEnum;

public class ExcelRegionInfo {
    private RegionTypeEnum regionTypeEnum;
    private Integer startIndex;
    private Integer endIndex;

    public ExcelRegionInfo(RegionTypeEnum regionTypeEnum) {
        this.regionTypeEnum = regionTypeEnum;
    }

    public ExcelRegionInfo(RegionTypeEnum regionTypeEnum, Integer startIndex) {
        this.regionTypeEnum = regionTypeEnum;
        this.startIndex = startIndex;
    }

    public ExcelRegionInfo(RegionTypeEnum regionTypeEnum, Integer startIndex, Integer endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.regionTypeEnum = regionTypeEnum;
    }

    public Integer getStartIndex() {
        return this.startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return this.endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public RegionTypeEnum getRegionTypeEnum() {
        return this.regionTypeEnum;
    }

    public void setRegionTypeEnum(RegionTypeEnum regionTypeEnum) {
        this.regionTypeEnum = regionTypeEnum;
    }

    public String getPositionStr() {
        return String.format("\u7b2c%1$d-%2$d\u884c%3$s", this.getStartIndex() + 1, this.getEndIndex(), this.getRegionTypeEnum().getName());
    }
}

