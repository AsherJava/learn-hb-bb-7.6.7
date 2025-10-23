/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.nr.formula.dto.FormulaSearchItem;
import java.util.List;

public class FormulaSearchParam {
    private List<FormulaSearchItem> data;
    private String formSchemeKey;
    private String formulaSchemeKey;
    private String keywords;
    private Long pageNo;
    private Long pageSize;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public List<FormulaSearchItem> getData() {
        return this.data;
    }

    public void setData(List<FormulaSearchItem> data) {
        this.data = data;
    }
}

