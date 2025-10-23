/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto;

import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.BasicCacheDTO;
import com.jiuqi.nr.datascheme.internal.service.impl.cache2.dto.DataFieldCacheDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTableCacheDTO
implements BasicCacheDTO {
    private final DataTableDTO dataTable;
    private final List<DataFieldCacheDTO> dataFields;
    private final List<DataTableRel> dataTableRels;
    private volatile Map<String, DataFieldCacheDTO> codeMap;

    public DataTableCacheDTO(DataTableDTO dataTable) {
        this.dataTable = dataTable;
        this.dataFields = new ArrayList<DataFieldCacheDTO>();
        this.dataTableRels = new ArrayList<DataTableRel>();
    }

    public DataTableDTO getDataTable() {
        return this.dataTable;
    }

    public List<DataFieldCacheDTO> getDataFields() {
        return this.dataFields;
    }

    public List<DataTableRel> getDataTableRels() {
        return this.dataTableRels;
    }

    @Override
    public String getKey() {
        return this.dataTable.getKey();
    }

    @Override
    public String getCode() {
        return this.dataTable.getCode();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public DataFieldCacheDTO getDataFieldByCode(String code) {
        if (null == this.codeMap) {
            DataTableCacheDTO dataTableCacheDTO = this;
            synchronized (dataTableCacheDTO) {
                if (null == this.codeMap) {
                    this.codeMap = new HashMap<String, DataFieldCacheDTO>();
                    for (DataFieldCacheDTO dataField : this.dataFields) {
                        this.codeMap.put(dataField.getCode(), dataField);
                    }
                }
            }
        }
        return this.codeMap.get(code);
    }
}

