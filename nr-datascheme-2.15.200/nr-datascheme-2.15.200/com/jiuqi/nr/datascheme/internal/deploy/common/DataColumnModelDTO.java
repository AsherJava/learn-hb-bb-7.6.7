/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.internal.deploy.DataSchemeDeployHelper;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import org.jetbrains.annotations.NotNull;

public class DataColumnModelDTO
implements Comparable<DataColumnModelDTO> {
    private final DataField dataField;
    private final DesignColumnModelDefine columnModel;

    public DataColumnModelDTO(DataField dataField, DesignColumnModelDefine columnModel) {
        this.dataField = dataField;
        this.columnModel = columnModel;
    }

    public DataField getDataField() {
        return this.dataField;
    }

    public DesignColumnModelDefine getColumnModel() {
        return this.columnModel;
    }

    @Override
    public int compareTo(@NotNull DataColumnModelDTO o) {
        if (null != this.dataField && null != o.getDataField()) {
            return DataSchemeDeployHelper.DATAFIELD_COMPARATOR.compare(this.dataField, o.getDataField());
        }
        double o1 = null == this.columnModel ? 0.0 : this.columnModel.getOrder();
        double o2 = null == o.getColumnModel() ? 0.0 : o.getColumnModel().getOrder();
        return (int)(o1 - o2);
    }
}

