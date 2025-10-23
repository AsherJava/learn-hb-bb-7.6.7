/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.data.access.extend.param;

import com.jiuqi.nr.definition.facade.FormDefine;

public class FormData {
    private String key;
    private String code;
    private String title;
    private String formType;
    private Boolean Writeable;

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

    public String getFormType() {
        return this.formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public void init(FormDefine formDefine) {
        this.key = formDefine.getKey();
        this.code = formDefine.getFormCode();
    }

    public Boolean getWriteable() {
        return this.Writeable;
    }

    public void setWriteable(Boolean writeable) {
        this.Writeable = writeable;
    }
}

