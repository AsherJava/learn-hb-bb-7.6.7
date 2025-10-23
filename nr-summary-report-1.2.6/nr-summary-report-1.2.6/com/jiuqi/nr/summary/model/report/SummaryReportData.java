/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.nvwa.framework.automation.json.GridDataJsonComponent$GridDataDeserializer
 *  com.jiuqi.nvwa.framework.automation.json.GridDataJsonComponent$GridDataSerializer
 */
package com.jiuqi.nr.summary.model.report;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.nvwa.framework.automation.json.GridDataJsonComponent;
import java.io.Serializable;

public class SummaryReportData
implements Serializable {
    @JsonSerialize(using=GridDataJsonComponent.GridDataSerializer.class)
    @JsonDeserialize(using=GridDataJsonComponent.GridDataDeserializer.class)
    private GridData gridData;

    public SummaryReportData() {
    }

    public SummaryReportData(GridData gridData) {
        this.gridData = gridData;
    }

    public GridData getGridData() {
        return this.gridData;
    }

    public void setGridData(GridData gridData) {
        this.gridData = gridData;
    }
}

