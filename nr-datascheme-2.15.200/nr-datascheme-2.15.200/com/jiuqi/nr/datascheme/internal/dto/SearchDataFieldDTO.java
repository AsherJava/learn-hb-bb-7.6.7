/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.datascheme.internal.dto;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;

public class SearchDataFieldDTO {
    private DesignDataField field;
    protected DesignDataTable designDataTable;

    public DesignDataTable getDesignDataTable() {
        return this.designDataTable;
    }

    public void setDesignDataTable(DesignDataTable designDataTable) {
        this.designDataTable = designDataTable;
    }

    public SearchDataFieldDTO() {
    }

    public SearchDataFieldDTO(DesignDataField dto) {
        this.field = dto;
    }

    public DesignDataField getField() {
        return this.field;
    }

    public void setField(DesignDataField field) {
        this.field = field;
    }
}

