/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.system.check.model.response;

import com.jiuqi.nr.definition.facade.FormDefine;

public class FormObj {
    private String key;
    private String title;
    private String code;

    public FormObj() {
    }

    public FormObj(FormDefine formDefine) {
        if (formDefine != null) {
            this.key = formDefine.getKey();
            this.title = formDefine.getTitle();
            this.code = formDefine.getFormCode();
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

