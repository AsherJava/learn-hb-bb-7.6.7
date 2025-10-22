/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataColumnModelDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.DesignTableDTO;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTableModelDTO {
    private final DataTable dataTable;
    private final DesignTableModelDefine tableModel;
    private List<DataColumnModelDTO> dataColumnModels;
    private List<DesignIndexModelDefine> indexModels;
    private Map<String, DesignTableDTO> extendTableModels;

    public DataTableModelDTO(DataTable dataTable, DesignTableModelDefine tableModel) {
        this.dataTable = dataTable;
        this.tableModel = tableModel;
    }

    public void addDataColumnModel(DataField dataField, DesignColumnModelDefine columnModel) {
        if (null == this.dataColumnModels) {
            this.dataColumnModels = new ArrayList<DataColumnModelDTO>();
        }
        this.dataColumnModels.add(new DataColumnModelDTO(dataField, columnModel));
    }

    public void addIndexModels(List<DesignIndexModelDefine> indexes) {
        this.indexModels = indexes;
    }

    public void addExtendTableModel(DesignTableModelDefine table, List<DesignColumnModelDefine> columns, List<DesignIndexModelDefine> indexes) {
        if (null == this.extendTableModels) {
            this.extendTableModels = new HashMap<String, DesignTableDTO>();
        }
        this.extendTableModels.put(table.getCode(), new DesignTableDTO(table, columns, indexes));
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public DesignTableModelDefine getTableModel() {
        return this.tableModel;
    }

    public List<DataColumnModelDTO> getDataColumnModels() {
        return null == this.dataColumnModels ? Collections.emptyList() : this.dataColumnModels;
    }

    public List<DesignIndexModelDefine> getIndexModels() {
        return null == this.indexModels ? Collections.emptyList() : this.indexModels;
    }

    public Map<String, DesignTableDTO> getExtendTableModels() {
        return null == this.extendTableModels ? Collections.emptyMap() : this.extendTableModels;
    }

    public DesignTableDTO getExtendTableByCode(String code) {
        return this.getExtendTableModels().get(code);
    }

    public Map<String, DataColumnModelDTO> getColumnIdMap() {
        HashMap<String, DataColumnModelDTO> map = new HashMap<String, DataColumnModelDTO>();
        for (DataColumnModelDTO dataColumnModel : this.getDataColumnModels()) {
            if (null == dataColumnModel.getColumnModel()) continue;
            map.put(dataColumnModel.getColumnModel().getID(), dataColumnModel);
        }
        return map;
    }

    public Map<String, DataColumnModelDTO> getColumnCodeMap() {
        HashMap<String, DataColumnModelDTO> map = new HashMap<String, DataColumnModelDTO>();
        for (DataColumnModelDTO dataColumnModel : this.getDataColumnModels()) {
            if (null == dataColumnModel.getColumnModel()) continue;
            map.put(dataColumnModel.getColumnModel().getCode(), dataColumnModel);
        }
        return map;
    }

    public Map<String, DataColumnModelDTO> getFieldKeyMap() {
        HashMap<String, DataColumnModelDTO> map = new HashMap<String, DataColumnModelDTO>();
        for (DataColumnModelDTO dataColumnModel : this.getDataColumnModels()) {
            if (null == dataColumnModel.getDataField()) continue;
            map.put(dataColumnModel.getDataField().getKey(), dataColumnModel);
        }
        return map;
    }

    public Map<String, DesignIndexModelDefine> getIndexIdMap() {
        HashMap<String, DesignIndexModelDefine> map = new HashMap<String, DesignIndexModelDefine>();
        for (DesignIndexModelDefine indexModel : this.getIndexModels()) {
            map.put(indexModel.getID(), indexModel);
        }
        return map;
    }
}

