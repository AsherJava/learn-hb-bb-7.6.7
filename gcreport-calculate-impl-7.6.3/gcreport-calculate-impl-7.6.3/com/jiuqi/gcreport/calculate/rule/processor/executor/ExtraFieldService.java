/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 */
package com.jiuqi.gcreport.calculate.rule.processor.executor;

import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import java.util.List;

public interface ExtraFieldService {
    public void doAreaBusiness(List<GcOffSetVchrItemDTO> var1);

    public List<GcOffSetVchrItemDTO> doBusinessSegments(List<GcOffSetVchrItemDTO> var1);

    public void doDimFinishCalc(List<GcOffSetVchrItemDTO> var1);
}

