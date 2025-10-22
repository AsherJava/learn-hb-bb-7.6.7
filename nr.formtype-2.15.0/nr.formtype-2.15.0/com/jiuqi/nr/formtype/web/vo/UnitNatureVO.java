/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.web.vo;

import com.jiuqi.nr.formtype.common.UnitNature;

public class UnitNatureVO {
    private int value;
    private String title;
    private String icon;
    private UnitNature.UnitNatureRule rule;

    public UnitNatureVO() {
    }

    public UnitNatureVO(UnitNature iUnitNature, String icon) {
        this.value = iUnitNature.getValue();
        this.title = iUnitNature.getTitle();
        this.icon = icon;
        this.rule = iUnitNature.getRule();
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public UnitNature.UnitNatureRule getRule() {
        return this.rule;
    }

    public void setRule(UnitNature.UnitNatureRule rule) {
        this.rule = rule;
    }
}

