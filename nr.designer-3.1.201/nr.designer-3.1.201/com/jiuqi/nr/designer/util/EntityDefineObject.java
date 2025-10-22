/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.model.IEntityDefine
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.nr.entity.model.IEntityDefine;

public class EntityDefineObject {
    private String key;
    private String id;
    private String code;
    private String title;
    private String desc;

    public EntityDefineObject(IEntityDefine iEntityDefine) {
        if (null != iEntityDefine) {
            this.key = iEntityDefine.getId();
            this.id = iEntityDefine.getId();
            this.code = iEntityDefine.getCode();
            this.title = iEntityDefine.getTitle();
            this.desc = iEntityDefine.getDesc();
        }
    }

    public EntityDefineObject() {
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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
}

