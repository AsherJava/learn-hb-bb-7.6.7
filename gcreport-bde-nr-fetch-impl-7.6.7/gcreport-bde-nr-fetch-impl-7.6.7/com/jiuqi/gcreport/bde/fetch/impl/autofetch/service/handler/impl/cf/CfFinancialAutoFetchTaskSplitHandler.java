/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.service.handler.impl.cf;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FetchRangCondition;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.mq.AbstractFinancialAutoFetchTaskSplitHandler;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.utils.FinancialAutoFetchUtil;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class CfFinancialAutoFetchTaskSplitHandler
extends AbstractFinancialAutoFetchTaskSplitHandler {
    @Override
    public String getFinancialTableName() {
        return "GC_FINCUBES_CF";
    }

    @Override
    public List<String> getPreTaskNames() {
        return CollectionUtils.newArrayList((Object[])new String[]{"FinancialCubesCfCalcHandler", "FinancialCubesAdjustCfCalcHandler", "FinancialCubesOffsetCfCalcHandler", "FinancialCubesOffsetDimCalcHandler", "FinancialCubesMergeSummaryCfCalcHandler"});
    }

    @Override
    public Set<String> getComputationSet() {
        return FinancialAutoFetchUtil.CF_COMPUTATIONMODEL_LIST;
    }

    @Override
    public List<String> getMainCodes(FetchRangCondition financialFetchCondition) {
        return this.financialAutoFetchService.getCashCodesByCf(financialFetchCondition);
    }
}

