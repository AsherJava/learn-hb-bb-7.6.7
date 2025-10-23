/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.mapping2.bean.ZBMapping
 */
package com.jiuqi.nr.sbdata.carry.bean;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.mapping2.bean.ZBMapping;
import com.jiuqi.nr.sbdata.carry.bean.TzCarryDownBaseParam;
import java.util.List;

public class TzCarryDownDTO
extends TzCarryDownBaseParam {
    private String formKey;
    private DimensionCollection sourceMasterKey;
    private DimensionCollection destMasterKey;
    private DataTable dataTable;
    private List<ZBMapping> mappings;

    public TzCarryDownDTO() {
    }

    public TzCarryDownDTO(TzCarryDownBaseParam param) {
        this.setSourceTaskKey(param.getSourceTaskKey());
        this.setSourceFormSchemeKey(param.getSourceFormSchemeKey());
        this.setDestTaskKey(param.getDestTaskKey());
        this.setDestFormSchemeKey(param.getDestFormSchemeKey());
        this.setSourceDimensionSet(param.getSourceDimensionSet());
        this.setDestPeriod(param.getDestPeriod());
        this.setMappingKey(param.getMappingKey());
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public DimensionCollection getSourceMasterKey() {
        return this.sourceMasterKey;
    }

    public void setSourceMasterKey(DimensionCollection sourceMasterKey) {
        this.sourceMasterKey = sourceMasterKey;
    }

    public DimensionCollection getDestMasterKey() {
        return this.destMasterKey;
    }

    public void setDestMasterKey(DimensionCollection destMasterKey) {
        this.destMasterKey = destMasterKey;
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public List<ZBMapping> getMappings() {
        return this.mappings;
    }

    public void setMappings(List<ZBMapping> mappings) {
        this.mappings = mappings;
    }
}

