/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.formulaeditor.vo;

import com.jiuqi.nr.entity.model.IEntityDefine;

public class EntityData {
    private String code;
    private String key;
    private String title;

    public EntityData(IEntityDefine iEntityDefine) {
        if (null != iEntityDefine) {
            this.key = iEntityDefine.getId();
            this.code = iEntityDefine.getCode();
            this.title = iEntityDefine.getTitle();
        }
    }

    public EntityData() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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
}

