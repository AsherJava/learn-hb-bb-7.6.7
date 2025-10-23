/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;

public class ResourceSearchVO {
    private String key;
    private String title;
    private String code;
    private String icon;
    private String parentKey;

    public ResourceSearchVO() {
    }

    public ResourceSearchVO(DesignTaskGroupDefine taskGroupDefine) {
        this.key = taskGroupDefine.getKey();
        this.title = taskGroupDefine.getTitle();
        this.code = taskGroupDefine.getCode();
        this.parentKey = taskGroupDefine.getParentKey();
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }
}

