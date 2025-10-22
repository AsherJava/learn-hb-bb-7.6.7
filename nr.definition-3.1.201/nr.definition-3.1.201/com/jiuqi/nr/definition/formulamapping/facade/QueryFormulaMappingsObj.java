/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.formulamapping.facade;

public class QueryFormulaMappingsObj {
    String mappingSchemekey;
    String formGroupKey;
    String formKey;
    String keyword;
    int checkType = -1;
    int count = 15;
    int startPage = 1;
    int mappingType = -1;

    public int getMappingType() {
        return this.mappingType;
    }

    public void setMappingType(int mappingType) {
        this.mappingType = mappingType;
    }

    public int getCheckType() {
        return this.checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public String getMappingSchemekey() {
        return this.mappingSchemekey;
    }

    public void setMappingSchemekey(String mappingSchemekey) {
        this.mappingSchemekey = mappingSchemekey;
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

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStartPage() {
        return this.startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getStarRow() {
        return (this.startPage - 1) * this.count + 1;
    }

    public int getEndRow() {
        return this.startPage * this.count;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

