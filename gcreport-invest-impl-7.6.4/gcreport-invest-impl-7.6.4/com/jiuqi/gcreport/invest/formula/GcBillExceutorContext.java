/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.gcreport.invest.formula;

import com.jiuqi.gcreport.invest.investbill.dto.GcInvestBillGroupDTO;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;

public class GcBillExceutorContext
extends ExecutorContext {
    private GcInvestBillGroupDTO investBillGroupDTO;
    private String taskId;
    private String schemeId;

    public GcBillExceutorContext(IDataDefinitionRuntimeController runtimeController) {
        super(runtimeController);
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public GcInvestBillGroupDTO getInvestBillGroupDTO() {
        return this.investBillGroupDTO;
    }

    public void setInvestBillGroupDTO(GcInvestBillGroupDTO investBillGroupDTO) {
        this.investBillGroupDTO = investBillGroupDTO;
    }
}

