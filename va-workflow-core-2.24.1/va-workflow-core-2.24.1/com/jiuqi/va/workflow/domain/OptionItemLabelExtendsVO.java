/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.option.OptionItemLabel
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.domain.option.OptionItemLabel;

public class OptionItemLabelExtendsVO
extends OptionItemLabel {
    private Boolean display = Boolean.FALSE;
    private Boolean disabled = Boolean.FALSE;
    private Boolean tips = Boolean.FALSE;

    public OptionItemLabelExtendsVO(String title, String val) {
        super(title, val);
    }

    public OptionItemLabelExtendsVO(String title, String val, Boolean display) {
        super(title, val);
        this.display = display;
        this.tips = display;
    }

    public OptionItemLabelExtendsVO(String title, String val, Boolean display, Boolean disabled) {
        super(title, val);
        this.display = display;
        this.tips = display;
        this.disabled = disabled;
    }

    public Boolean getDisplay() {
        return this.display;
    }

    public void setDisplay(Boolean flag) {
        this.display = flag;
    }

    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getTips() {
        return this.tips;
    }

    public void setTips(Boolean tips) {
        this.tips = tips;
    }

    public String toString() {
        return super.toString() + "OptionItemLabelExtendsVO{display=" + this.display + ", disabled=" + this.disabled + ", tips=" + this.tips + '}';
    }
}

