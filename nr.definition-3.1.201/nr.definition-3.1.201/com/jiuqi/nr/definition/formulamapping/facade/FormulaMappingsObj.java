/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.formulamapping.facade;

import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj;
import java.util.List;

public class FormulaMappingsObj {
    private List<FormulaMappingObj> list;
    private Long total;
    private int page;

    public List<FormulaMappingObj> getList() {
        return this.list;
    }

    public void setList(List<FormulaMappingObj> list) {
        this.list = list;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

