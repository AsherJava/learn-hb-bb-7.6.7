/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.step.inter;

import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepTree;
import com.jiuqi.nr.bpm.de.dataflow.step.provide.StepQueryState;
import java.util.List;

public interface IResultData {
    public int getType();

    public void resultData(BatchStepByStepParam var1, List<StepTree> var2, String var3, StepQueryState var4, BatchStepByStepResult var5, boolean var6, boolean var7);
}

