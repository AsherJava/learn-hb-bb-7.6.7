/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.FormulaSearchItem;
import java.util.List;

public class FormulaSearchParam {
    public static final int PAGE_NO = 1;
    public static final int PAGE_SIZE = 15;
    private Integer pageNo = 1;
    private Integer pageSize = 15;
    private String keywords;
    private List<FormulaSearchItem> list;
    private String formulaScheme;

    public Integer getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public List<FormulaSearchItem> getList() {
        return this.list;
    }

    public void setList(List<FormulaSearchItem> list) {
        this.list = list;
    }

    public String getFormulaScheme() {
        return this.formulaScheme;
    }

    public void setFormulaScheme(String formulaScheme) {
        this.formulaScheme = formulaScheme;
    }
}

