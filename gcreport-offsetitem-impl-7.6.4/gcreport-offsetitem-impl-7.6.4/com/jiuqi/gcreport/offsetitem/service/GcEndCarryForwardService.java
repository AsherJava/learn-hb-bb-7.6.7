/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO
 *  com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO
 *  com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.service;

import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.offsetitem.vo.EndCarryForwardResultVO;
import com.jiuqi.gcreport.offsetitem.vo.LossGainOffsetVO;
import com.jiuqi.gcreport.offsetitem.vo.MinorityRecoveryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.Map;

public interface GcEndCarryForwardService {
    public Pagination<Map<String, Object>> listMinRecoveryPentrateDatas(MinorityRecoveryParamsVO var1);

    public EndCarryForwardResultVO queryEndCarryForward(QueryParamsVO var1);

    public Boolean saveEndCarryForward(QueryParamsVO var1, LossGainOffsetVO var2);

    public Boolean doLossGain(QueryParamsVO var1);

    public boolean allowDeferredIncomeTaxByOption(ConsolidatedTaskVO var1);

    public boolean allowDeferredIncomeTax(QueryParamsVO var1);
}

