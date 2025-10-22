/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.gather;

public class InfoViewItem {
    private String code;
    private String title;
    private String desc;
    private String param;
    private String nodeKey;
    private Boolean selected;

    public InfoViewItem() {
    }

    public InfoViewItem(String code, String title) {
        this.code = code;
        this.title = title;
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

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getNodeKey() {
        return this.nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public Boolean getSelected() {
        return this.selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}

