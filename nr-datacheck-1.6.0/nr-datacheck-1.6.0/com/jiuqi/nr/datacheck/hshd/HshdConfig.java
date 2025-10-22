/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.datacheck.hshd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datacheck.hshd.PeriodConfig;

@JsonIgnoreProperties(ignoreUnknown=true)
public class HshdConfig {
    private String assTask;
    private String assFormScheme;
    private String assPeriod;
    private PeriodConfig periodConfig;
    private String assEntity;
    private boolean multiEntity;

    public String getAssTask() {
        return this.assTask;
    }

    public void setAssTask(String assTask) {
        this.assTask = assTask;
    }

    public String getAssFormScheme() {
        return this.assFormScheme;
    }

    public void setAssFormScheme(String assFormScheme) {
        this.assFormScheme = assFormScheme;
    }

    public PeriodConfig getPeriodConfig() {
        return this.periodConfig;
    }

    public void setPeriodConfig(PeriodConfig periodConfig) {
        this.periodConfig = periodConfig;
    }

    public String getAssPeriod() {
        return this.assPeriod;
    }

    public void setAssPeriod(String assPeriod) {
        this.assPeriod = assPeriod;
    }

    public String getAssEntity() {
        return this.assEntity;
    }

    public void setAssEntity(String assEntity) {
        this.assEntity = assEntity;
    }

    public boolean isMultiEntity() {
        return this.multiEntity;
    }

    public void setMultiEntity(boolean multiEntity) {
        this.multiEntity = multiEntity;
    }
}

