/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.jiuqi.nr.zb.scheme.common.PropDataType;

public class PropLinkVO {
    private String key;
    private Boolean selected;
    private String title;
    private PropDataType dataType;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean isSelected() {
        return this.selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PropDataType getDataType() {
        return this.dataType;
    }

    public void setDataType(PropDataType dataType) {
        this.dataType = dataType;
    }
}

