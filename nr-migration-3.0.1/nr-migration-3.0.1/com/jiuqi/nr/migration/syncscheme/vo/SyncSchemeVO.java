/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.migration.syncscheme.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.migration.syncscheme.bean.SyncScheme;

public class SyncSchemeVO {
    private String key;
    private String code;
    private String title;
    private String group;
    private String groupTitle;
    private String order;
    private boolean isFirst;
    private boolean isLast;

    public SyncSchemeVO(SyncScheme scheme, String groupTitle) {
        this.key = scheme.getKey();
        this.code = scheme.getCode();
        this.title = scheme.getTitle();
        this.group = scheme.getGroup();
        this.groupTitle = groupTitle;
        this.order = scheme.getOrder();
    }

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

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupTitle() {
        return this.groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isFirst() {
        return this.isFirst;
    }

    @JsonProperty(value="isFirst")
    public void setFirst(boolean first) {
        this.isFirst = first;
    }

    public boolean isLast() {
        return this.isLast;
    }

    @JsonProperty(value="isLast")
    public void setLast(boolean last) {
        this.isLast = last;
    }
}

