/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 */
package com.jiuqi.gcreport.samecontrol.service;

import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgOrgEO;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import java.util.List;

public interface SameCtrlCalcInvestRuleService {
    public List<GcOffSetVchrItemAdjustEO> generateGcOffSetVchrItemAdjust(SameCtrlOffsetCond var1, SameCtrlChgOrgEO var2);
}

