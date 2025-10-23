/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.datascheme.internal.tree;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.internal.tree.DataSchemeNodeDTO;

public class DataTableNodeDTO
extends DataSchemeNodeDTO {
    public DataTableNodeDTO(DesignDataTable table) {
        super(table);
    }

    public int getTableType() {
        return ((DataTable)this.getData()).getDataTableType().getValue();
    }
}

