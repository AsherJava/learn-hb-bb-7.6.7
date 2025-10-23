/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.web.facade.DataFieldVO;

public class DataFieldParamVO
extends DataFieldVO {
    private DataTableType dataTableType;

    public DataTableType getDataTableType() {
        return this.dataTableType;
    }

    public void setDataTableType(DataTableType dataTableType) {
        this.dataTableType = dataTableType;
    }
}

