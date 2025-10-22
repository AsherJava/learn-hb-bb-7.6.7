/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.entity;

public class AdjustPeriodSettingEO {
    public static final String TABLENAME = "BDE_ADJUSTPERIODSETTING";
    private String id;
    private String fetchSchemeId;
    private String adjustPeriod;
    private String startAdjustPeriod;
    private String endAdjustPeriod;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getAdjustPeriod() {
        return this.adjustPeriod;
    }

    public void setAdjustPeriod(String adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }

    public String getStartAdjustPeriod() {
        return this.startAdjustPeriod;
    }

    public void setStartAdjustPeriod(String startAdjustPeriod) {
        this.startAdjustPeriod = startAdjustPeriod;
    }

    public String getEndAdjustPeriod() {
        return this.endAdjustPeriod;
    }

    public void setEndAdjustPeriod(String endAdjustPeriod) {
        this.endAdjustPeriod = endAdjustPeriod;
    }
}

