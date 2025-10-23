/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.i18n.bean;

import com.jiuqi.nr.task.i18n.bean.I18nBaseObj;

public class I18nColsObj
extends I18nBaseObj {
    private boolean disabled;
    private boolean operate;
    private Integer colWidth;

    public I18nColsObj(String key, String title, boolean disabled, boolean operate, Integer colWidth) {
        super(key, title);
        this.disabled = disabled;
        this.operate = operate;
        this.colWidth = colWidth;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isOperate() {
        return this.operate;
    }

    public void setOperate(boolean operate) {
        this.operate = operate;
    }

    public Integer getColWidth() {
        return this.colWidth;
    }

    public void setColWidth(Integer colWidth) {
        this.colWidth = colWidth;
    }
}

