/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotBlank
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import javax.validation.constraints.NotBlank;

public class GenerateZbParam {
    @NotBlank(message="checkKey is null")
    private @NotBlank(message="checkKey is null") String checkKey;
    @NotBlank(message="formSchemeKey is null")
    private @NotBlank(message="formSchemeKey is null") String formSchemeKey;
    @NotBlank(message="zbSchemeKey is null")
    private @NotBlank(message="zbSchemeKey is null") String zbSchemeKey;
    @NotBlank(message="zbSchemeVersionKey is null")
    private @NotBlank(message="zbSchemeVersionKey is null") String zbSchemeVersionKey;

    public String getCheckKey() {
        return this.checkKey;
    }

    public void setCheckKey(String checkKey) {
        this.checkKey = checkKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getZbSchemeKey() {
        return this.zbSchemeKey;
    }

    public void setZbSchemeKey(String zbSchemeKey) {
        this.zbSchemeKey = zbSchemeKey;
    }

    public String getZbSchemeVersionKey() {
        return this.zbSchemeVersionKey;
    }

    public void setZbSchemeVersionKey(String zbSchemeVersionKey) {
        this.zbSchemeVersionKey = zbSchemeVersionKey;
    }
}

