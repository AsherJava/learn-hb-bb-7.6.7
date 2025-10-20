/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.biz.domain;

import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.Map;

public class TableAndFieldSearchDTO
extends TenantDO {
    private static final long serialVersionUID = -8508193974477856063L;
    private ModelDefine modelDefine;
    private String searchText;
    private List<Map<String, Object>> billData;

    public ModelDefine getModelDefine() {
        return this.modelDefine;
    }

    public void setModelDefine(ModelDefine modelDefine) {
        this.modelDefine = modelDefine;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public List<Map<String, Object>> getBillData() {
        return this.billData;
    }

    public void setBillData(List<Map<String, Object>> billData) {
        this.billData = billData;
    }
}

