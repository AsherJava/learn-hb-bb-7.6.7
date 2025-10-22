/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.forcecontrol;

import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckResult;

public interface IForceControlService {
    public StepByStepCheckResult reject(StepByOptParam var1);

    public BatchStepByStepResult batchReject(BatchStepByStepParam var1);
}

