/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.config;

import java.util.List;

public class NrSchemesVO {
    private String value;
    private String label;
    private int type;
    private String startDate;
    private String endDate;
    private String orgType;
    private int offset;
    private List<NrSchemesVO> children;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<NrSchemesVO> getChildren() {
        return this.children;
    }

    public void setChildren(List<NrSchemesVO> children) {
        this.children = children;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}

