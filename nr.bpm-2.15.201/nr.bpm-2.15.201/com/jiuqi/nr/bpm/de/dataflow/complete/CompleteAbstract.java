/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.complete;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;

public interface CompleteAbstract {
    public boolean IsMatch(String var1, String var2);

    public CompleteMsg executeTask(ExecuteParam var1);

    public CompleteMsg batchExecuteTask(BatchExecuteParam var1);

    default public CompleteMsg executeRevert(BusinessKey businessKey) {
        return null;
    }

    default public CompleteMsg executeRevert(BusinessKey businessKey, TaskContext context) {
        return null;
    }

    default public CompleteMsg batchApplyReturnExecute(BatchExecuteParam executeParam) {
        return null;
    }
}

