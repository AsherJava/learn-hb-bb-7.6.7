/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.response;

import com.jiuqi.nr.tag.management.bean.TagFacadeImpl;

public class TagManagerShowData
extends TagFacadeImpl {
    private boolean shared;
    private boolean rangeModify;
    private String showText;

    public boolean isShared() {
        return this.shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean isRangeModify() {
        return this.rangeModify;
    }

    public void setRangeModify(boolean rangeModify) {
        this.rangeModify = rangeModify;
    }

    public String getShowText() {
        return this.showText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }
}

