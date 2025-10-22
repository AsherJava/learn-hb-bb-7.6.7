/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.time.setting.bean;

import java.util.ArrayList;
import java.util.List;

public class DropTreeResult {
    private String key;
    private String code;
    private String title;
    private boolean expend;
    private boolean selected;
    private List<DropTreeResult> children = new ArrayList<DropTreeResult>();

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpend() {
        return this.expend;
    }

    public void setExpend(boolean expend) {
        this.expend = expend;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<DropTreeResult> getChildren() {
        return this.children;
    }

    public void setChildren(List<DropTreeResult> children) {
        this.children = children;
    }
}

