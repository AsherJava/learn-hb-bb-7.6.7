/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.param.transfer.period;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.param.transfer.period.PeriodEntityDTO;
import com.jiuqi.nr.param.transfer.period.PeriodRowDTO;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PeriodTransferDTO {
    private PeriodEntityDTO periodEntity;
    private List<PeriodRowDTO> periodRows;

    public PeriodEntityDTO getPeriodEntity() {
        return this.periodEntity;
    }

    public void setPeriodEntity(PeriodEntityDTO periodEntity) {
        this.periodEntity = periodEntity;
    }

    public List<PeriodRowDTO> getPeriodRows() {
        return this.periodRows;
    }

    public void setPeriodRows(List<PeriodRowDTO> periodRows) {
        this.periodRows = periodRows;
    }
}

