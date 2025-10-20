/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 */
package com.jiuqi.dc.mappingscheme.impl.common;

import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import java.util.List;

public class DataSchemeInit {
    private String customConfig;
    private List<BaseDataMappingDefineDTO> baseDataRefDefine;
    private List<DataMappingDefineDTO> bizDataRefDefine;

    public String getCustomConfig() {
        return this.customConfig;
    }

    public void setCustomConfig(String customConfig) {
        this.customConfig = customConfig;
    }

    public List<BaseDataMappingDefineDTO> getBaseDataRefDefine() {
        return this.baseDataRefDefine;
    }

    public void setBaseDataRefDefine(List<BaseDataMappingDefineDTO> baseDataRefDefine) {
        this.baseDataRefDefine = baseDataRefDefine;
    }

    public List<DataMappingDefineDTO> getBizDataRefDefine() {
        return this.bizDataRefDefine;
    }

    public void setBizDataRefDefine(List<DataMappingDefineDTO> bizDataRefDefine) {
        this.bizDataRefDefine = bizDataRefDefine;
    }
}

