/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo;

import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;

public class AdjustingOffsetEntryVO
extends GcOffSetVchrItemDTO {
    private String gcBusinessType;
    private String unit;
    private String oppUnit;
    private String subject;
    private String elmModeText;
    private String rule;
    private int rowspan;

    public String getGcBusinessType() {
        return this.gcBusinessType;
    }

    public void setGcBusinessType(String gcBusinessType) {
        this.gcBusinessType = gcBusinessType;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOppUnit() {
        return this.oppUnit;
    }

    public void setOppUnit(String oppUnit) {
        this.oppUnit = oppUnit;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getElmModeText() {
        return this.elmModeText;
    }

    public void setElmModeText(String elmModeText) {
        this.elmModeText = elmModeText;
    }

    public String getRule() {
        return this.rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getRowspan() {
        return this.rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }
}

