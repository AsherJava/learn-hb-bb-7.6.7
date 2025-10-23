/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class FormSchemeVO {
    private String key;
    private String title;

    public FormSchemeVO(FormSchemeDefine formSchemeDefine) {
        this.key = formSchemeDefine.getKey();
        this.title = formSchemeDefine.getTitle();
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

