/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesignDataTableDTO {
    private final DesignDataTable dataTable;
    private final List<DesignDataField> dataFields;

    public DesignDataTableDTO(DesignDataTable dataTable, List<DesignDataField> dataFields) {
        this.dataTable = dataTable;
        this.dataFields = dataFields;
    }

    public DesignDataTable getDataTable() {
        return this.dataTable;
    }

    public List<DesignDataField> getDataFields() {
        return null == this.dataFields ? Collections.emptyList() : this.dataFields;
    }

    public Map<String, DesignDataField> getDataFieldIdMap() {
        HashMap<String, DesignDataField> map = new HashMap<String, DesignDataField>();
        for (DesignDataField dataField : this.getDataFields()) {
            map.put(dataField.getKey(), dataField);
        }
        return map;
    }
}

