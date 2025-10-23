/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.Min
 *  javax.validation.constraints.NotBlank
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ZbCheckQueryParam {
    @NotBlank(message="checkKey is blank")
    private @NotBlank(message="checkKey is blank") String checkKey;
    private String formGroupKey;
    private String formKey;
    private List<Integer> diffTypes;
    private int operType;
    private String keywords;
    @Min(value=0L)
    private @Min(value=0L) int pageSize;
    @Min(value=1L)
    private @Min(value=1L) int currentPage;

    public ZbCheckQueryParam() {
    }

    public ZbCheckQueryParam(String checkKey, String formKey) {
        this.checkKey = checkKey;
        this.formKey = formKey;
    }

    public ZbCheckQueryParam(List<Integer> diffTypes, int operType) {
        this.diffTypes = diffTypes;
        this.operType = operType;
    }

    public String getCheckKey() {
        return this.checkKey;
    }

    public void setCheckKey(String checkKey) {
        this.checkKey = checkKey;
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

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}

