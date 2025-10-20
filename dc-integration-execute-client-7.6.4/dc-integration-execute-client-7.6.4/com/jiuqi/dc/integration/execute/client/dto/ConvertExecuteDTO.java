/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 */
package com.jiuqi.dc.integration.execute.client.dto;

import com.jiuqi.dc.integration.execute.client.dto.ConvertRefDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import java.io.Serializable;
import java.util.List;

public class ConvertExecuteDTO
implements Serializable {
    private static final long serialVersionUID = -4341982391491202665L;
    private DataSchemeDTO dataScheme;
    private List<ConvertRefDefineDTO> bizDataRefs;
    private List<ConvertRefDefineDTO> baseDataRefs;

    public DataSchemeDTO getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DataSchemeDTO dataScheme) {
        this.dataScheme = dataScheme;
    }

    public List<ConvertRefDefineDTO> getBizDataRefs() {
        return this.bizDataRefs;
    }

    public void setBizDataRefs(List<ConvertRefDefineDTO> bizDataRefs) {
        this.bizDataRefs = bizDataRefs;
    }

    public List<ConvertRefDefineDTO> getBaseDataRefs() {
        return this.baseDataRefs;
    }

    public void setBaseDataRefs(List<ConvertRefDefineDTO> baseDataRefs) {
        this.baseDataRefs = baseDataRefs;
    }
}

