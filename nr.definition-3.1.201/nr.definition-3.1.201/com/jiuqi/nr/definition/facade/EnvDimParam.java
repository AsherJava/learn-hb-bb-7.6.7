/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.dto.DataDimDTO
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import java.util.List;

public class EnvDimParam {
    private DataDimDTO dwDataDimDTO;
    private List<String> dws;
    private DataDimDTO periodDimDTO;
    private String period;
    private DataDimDTO dataDimDTO;

    public DataDimDTO getDataDimDTO() {
        return this.dataDimDTO;
    }

    public void setDataDimDTO(DataDimDTO dataDimDTO) {
        this.dataDimDTO = dataDimDTO;
    }

    public List<String> getDws() {
        return this.dws;
    }

    public void setDw(List<String> dws) {
        this.dws = dws;
    }

    public DataDimDTO getDwDataDimDTO() {
        return this.dwDataDimDTO;
    }

    public void setDwDataDimDTO(DataDimDTO dwDataDimDTO) {
        this.dwDataDimDTO = dwDataDimDTO;
    }

    public DataDimDTO getPeriodDimDTO() {
        return this.periodDimDTO;
    }

    public void setPeriodDimDTO(DataDimDTO periodDimDTO) {
        this.periodDimDTO = periodDimDTO;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}

