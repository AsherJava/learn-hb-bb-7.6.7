/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.data.estimation.sub.database.intf.impl;

import com.jiuqi.nr.data.estimation.sub.database.intf.IOriginalDesTableModelDefine;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;

public class OriginalDesTableModelDefine
implements IOriginalDesTableModelDefine {
    private DesignTableModelDefine desTableModelDefine;
    private DataTableType dataTableType;

    public OriginalDesTableModelDefine() {
    }

    public OriginalDesTableModelDefine(DesignTableModelDefine desTableModelDefine, DataTableType dataTableType) {
        this.desTableModelDefine = desTableModelDefine;
        this.dataTableType = dataTableType;
    }

    @Override
    public DesignTableModelDefine getDesTableModelDefine() {
        return this.desTableModelDefine;
    }

    public void setDesTableModelDefine(DesignTableModelDefine desTableModelDefine) {
        this.desTableModelDefine = desTableModelDefine;
    }

    @Override
    public DataTableType getDataTableType() {
        return this.dataTableType;
    }

    public void setDataTableType(DataTableType dataTableType) {
        this.dataTableType = dataTableType;
    }
}

