/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotBlank
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import javax.validation.constraints.NotBlank;

public class FormSchemeTreeSearchParam {
    @NotBlank(message="formSchemeKey is blank")
    private @NotBlank(message="formSchemeKey is blank") String formSchemeKey;
    private String keyword;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

