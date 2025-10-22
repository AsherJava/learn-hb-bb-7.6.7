/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.dto;

import com.jiuqi.nr.form.analysis.dto.PeriodDimParamDTO;
import com.jiuqi.nr.form.analysis.dto.SrcEntityDimParamDTO;
import java.util.List;

public class SrcDimsParamDTO {
    private SrcEntityDimParamDTO org;
    private PeriodDimParamDTO period;
    private List<SrcEntityDimParamDTO> dims;

    public SrcEntityDimParamDTO getOrg() {
        return this.org;
    }

    public PeriodDimParamDTO getPeriod() {
        return this.period;
    }

    public List<SrcEntityDimParamDTO> getDims() {
        return this.dims;
    }

    public SrcDimsParamDTO(SrcEntityDimParamDTO org, PeriodDimParamDTO period, List<SrcEntityDimParamDTO> dims) {
        this.org = org;
        this.period = period;
        this.dims = dims;
    }
}

