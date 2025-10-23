/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 */
package com.jiuqi.nr.param.transfer.datascheme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDTO;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TransferSchemeDTO {
    private DataScheme dataScheme;
    private List<DesignDataDimension> dims;
    private List<DesignAdjustPeriodDTO> adjusts;
    private Map<String, byte[]> params = null;

    public DataScheme getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(DataScheme dataScheme) {
        this.dataScheme = dataScheme;
    }

    public List<DesignDataDimension> getDims() {
        return this.dims;
    }

    public void setDims(List<DesignDataDimension> dims) {
        this.dims = dims;
    }

    public List<DesignAdjustPeriodDTO> getAdjusts() {
        return this.adjusts;
    }

    public void setAdjusts(List<DesignAdjustPeriodDTO> adjusts) {
        this.adjusts = adjusts;
    }

    public Map<String, byte[]> getParams() {
        return this.params;
    }

    public void setParams(Map<String, byte[]> params) {
        this.params = params;
    }
}

