/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckResultGroupInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormulaCheckGroupReturnInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import java.util.List;
import java.util.Map;

@Deprecated
public interface IBatchCheckResultService {
    @Deprecated
    public FormulaCheckReturnInfo batchCheckResult(BatchCheckInfo var1);

    @Deprecated
    public FormulaCheckGroupReturnInfo batchCheckResultGroup(BatchCheckResultGroupInfo var1);

    public CheckResultQueryParam buildFilterCondiotion(CheckResultQueryParam var1, String var2, String var3, Map<String, List<String>> var4);
}

