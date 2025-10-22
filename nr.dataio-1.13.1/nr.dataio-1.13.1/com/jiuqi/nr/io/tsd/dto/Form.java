/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.FormDefine
 */
package com.jiuqi.nr.io.tsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.FormDefine;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Form {
    private String key;
    private String code;
    private String title;

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

    public Form() {
    }

    public Form(FormDefine formDefine) {
        this.key = formDefine.getKey();
        this.code = formDefine.getFormCode();
        this.title = formDefine.getTitle();
    }
}

