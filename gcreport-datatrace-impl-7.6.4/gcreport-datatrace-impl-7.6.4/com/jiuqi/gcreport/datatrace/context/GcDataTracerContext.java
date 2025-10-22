/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 */
package com.jiuqi.gcreport.datatrace.context;

import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import java.util.HashMap;
import java.util.Map;

public class GcDataTracerContext {
    private GcOffSetVchrItemDTO gcOffSetVchrItemDTO;
    private GcTaskBaseArguments condition;
    private Map<String, Object> extendDataMap = new HashMap<String, Object>();

    public GcTaskBaseArguments getCondition() {
        return this.condition;
    }

    public void setCondition(GcTaskBaseArguments condition) {
        this.condition = condition;
    }

    public GcOffSetVchrItemDTO getGcOffSetVchrItemDTO() {
        return this.gcOffSetVchrItemDTO;
    }

    public void setGcOffSetVchrItemDTO(GcOffSetVchrItemDTO gcOffSetVchrItemDTO) {
        this.gcOffSetVchrItemDTO = gcOffSetVchrItemDTO;
    }

    public Map<String, Object> getExtendDataMap() {
        return this.extendDataMap;
    }
}

