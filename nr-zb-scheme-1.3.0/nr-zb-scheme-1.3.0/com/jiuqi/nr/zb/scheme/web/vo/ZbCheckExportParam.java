/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotBlank
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import java.util.List;
import javax.validation.constraints.NotBlank;

public class ZbCheckExportParam {
    @NotBlank(message="checkKey is blank")
    private @NotBlank(message="checkKey is blank") String checkKey;
    @NotBlank(message="formSchemeKey is blank")
    private @NotBlank(message="formSchemeKey is blank") String formSchemeKey;
    private String formGroupKey;
    private String formKey;
    private List<Integer> diffTypes;
    private int operType;
    private String keywords;

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

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<Integer> getDiffTypes() {
        return this.diffTypes;
    }

    public void setDiffTypes(List<Integer> diffTypes) {
        this.diffTypes = diffTypes;
    }

    public int getOperType() {
        return this.operType;
    }

    public void setOperType(int operType) {
        this.operType = operType;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeyword(String keywords) {
        this.keywords = keywords;
    }
}

