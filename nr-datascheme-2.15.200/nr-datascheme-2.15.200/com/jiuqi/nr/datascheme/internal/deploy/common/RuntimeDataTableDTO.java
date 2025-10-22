/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataColumnModelDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableModelDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RuntimeDataTableDTO {
    private final DataTable dataTable;
    private final List<DataTableModelDTO> dataTableModels;

    public RuntimeDataTableDTO(DataTable dataTable, List<DataTableModelDTO> dataTableModels) {
        this.dataTable = dataTable;
        this.dataTableModels = dataTableModels;
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public List<DataField> getDataFields() {
        return this.getDataFieldIdMap().values().stream().sorted().collect(Collectors.toList());
    }

    public Map<String, DataField> getDataFieldIdMap() {
        ArrayList<DataField> dataFields = new ArrayList<DataField>();
        for (DataTableModelDTO dataTableModel : this.dataTableModels) {
            for (DataColumnModelDTO dataColumnModel : dataTableModel.getDataColumnModels()) {
                if (null == dataColumnModel.getDataField()) continue;
                dataFields.add(dataColumnModel.getDataField());
            }
        }
        HashMap<String, DataField> map = new HashMap<String, DataField>();
        for (DataField dataField : dataFields) {
            map.put(dataField.getKey(), dataField);
        }
        return map;
    }

    public List<DataTableModelDTO> getDataTableModels() {
        return null == this.dataTableModels ? Collections.emptyList() : this.dataTableModels;
    }
}

