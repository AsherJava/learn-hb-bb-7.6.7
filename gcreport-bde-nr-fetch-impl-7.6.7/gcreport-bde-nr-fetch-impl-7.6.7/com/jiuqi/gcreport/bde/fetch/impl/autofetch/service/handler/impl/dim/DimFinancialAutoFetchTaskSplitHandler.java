/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gcreport.bde.fetch.impl.autofetch.service.handler.impl.dim;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.dto.FetchRangCondition;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.mq.AbstractFinancialAutoFetchTaskSplitHandler;
import com.jiuqi.gcreport.bde.fetch.impl.autofetch.utils.FinancialAutoFetchUtil;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class DimFinancialAutoFetchTaskSplitHandler
extends AbstractFinancialAutoFetchTaskSplitHandler {
    @Override
    public String getFinancialTableName() {
        return "GC_FINCUBES_DIM";
    }

    @Override
    public List<String> getPreTaskNames() {
        return CollectionUtils.newArrayList((Object[])new String[]{"FinancialCubesDimCalcHandler", "FinancialCubesAdjustDimCalcHandler", "FinancialCubesMergeSummaryDimCalcHandler"});
    }

    @Override
    public Set<String> getComputationSet() {
        return FinancialAutoFetchUtil.DIM_COMPUTATIONMODEL_LIST;
    }

    @Override
    public List<String> getMainCodes(FetchRangCondition financialFetchCondition) {
        return this.financialAutoFetchService.getSubjectCodesByDim(financialFetchCondition);
    }
}

