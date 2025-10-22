/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.tree;

import com.jiuqi.nr.period.common.utils.Period13Info;
import com.jiuqi.nr.period.common.utils.PeriodPropertyGroup;

public class Data {
    private String title;
    private String code;
    private String parentKey;
    private String pression;
    private String key;
    private String ordinal;
    private String nodeType = "";
    private String type = "ALL";
    private PeriodPropertyGroup periodPropertyGroup;
    private Period13Info period13Info;

    public Period13Info getPeriod13Info() {
        return this.period13Info;
    }

    public void setPeriod13Info(Period13Info period13Info) {
        this.period13Info = period13Info;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getPression() {
        return this.pression;
    }

    public void setPression(String pression) {
        this.pression = pression;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public String getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PeriodPropertyGroup getPeriodPropertyGroup() {
        return this.periodPropertyGroup;
    }

    public void setPeriodPropertyGroup(PeriodPropertyGroup periodPropertyGroup) {
        this.periodPropertyGroup = periodPropertyGroup;
    }
}

