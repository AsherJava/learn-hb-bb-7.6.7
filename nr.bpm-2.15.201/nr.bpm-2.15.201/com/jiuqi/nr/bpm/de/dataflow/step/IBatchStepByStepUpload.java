/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.step;

import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;

public interface IBatchStepByStepUpload {
    public boolean isUpload(StepByOptParam var1);

    public boolean isBatchUpload(BatchStepByStepParam var1);

    default public boolean isUploadAll(StepByOptParam stepByOptParam) {
        return false;
    }

    default public boolean isBatchUploadAll(BatchStepByStepParam batchStepByStepParam) {
        return false;
    }
}

