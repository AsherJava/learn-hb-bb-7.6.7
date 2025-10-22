/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.service;

import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResultGroup;

public interface ICheckResultService {
    public CheckResult queryAllCheckResult(CheckResultQueryParam var1, String var2);

    public CheckResult queryBatchCheckResult(CheckResultQueryParam var1);

    public CheckResultGroup queryBatchCheckResultGroup(CheckResultQueryParam var1);

    public boolean existError(CheckResultQueryParam var1);
}

