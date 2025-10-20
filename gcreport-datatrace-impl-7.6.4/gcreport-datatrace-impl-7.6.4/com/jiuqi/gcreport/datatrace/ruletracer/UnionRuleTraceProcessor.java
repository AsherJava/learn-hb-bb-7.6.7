/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.datatrace.vo.FetchItemDTO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO
 *  com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 */
package com.jiuqi.gcreport.datatrace.ruletracer;

import com.jiuqi.gcreport.datatrace.vo.FetchItemDTO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO;
import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceResultVO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import java.util.List;

public interface UnionRuleTraceProcessor {
    public List<FetchItemDTO> getFetchItem();

    public GcOffSetVchrItemAdjustEO getOffSetItem();

    public ExecutorContext getExecutorContext();

    public AbstractData formulaEval(String var1);

    default public void initFetchItem(FetchItemDTO item) {
    }

    default public boolean takeOverTrace(FetchItemDTO item) {
        return false;
    }

    default public OffsetAmtTraceResultVO traceByTakeOver() {
        return null;
    }

    default public AbstractData formulaEval(OffsetAmtTraceItemVO offsetAmtTraceItemVO) {
        return this.formulaEval(offsetAmtTraceItemVO.getExpression());
    }
}

