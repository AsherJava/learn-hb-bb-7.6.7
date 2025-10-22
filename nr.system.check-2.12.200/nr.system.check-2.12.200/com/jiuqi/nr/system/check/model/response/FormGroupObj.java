/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 */
package com.jiuqi.nr.system.check.model.response;

import com.jiuqi.nr.definition.facade.FormGroupDefine;

public class FormGroupObj {
    private String key;
    private String title;
    private String code;

    public FormGroupObj() {
    }

    public FormGroupObj(FormGroupDefine formGroupDefine) {
        if (formGroupDefine != null) {
            this.key = formGroupDefine.getKey();
            this.title = formGroupDefine.getTitle();
            this.code = formGroupDefine.getCode();
        }
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
}

