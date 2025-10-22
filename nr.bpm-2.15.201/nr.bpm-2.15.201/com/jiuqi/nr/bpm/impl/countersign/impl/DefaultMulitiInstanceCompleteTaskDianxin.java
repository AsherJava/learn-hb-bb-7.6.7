/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.delegate.DelegateExecution
 */
package com.jiuqi.nr.bpm.impl.countersign.impl;

import com.jiuqi.nr.bpm.impl.countersign.CounterSignParam;
import org.activiti.engine.delegate.DelegateExecution;

public class DefaultMulitiInstanceCompleteTaskDianxin {
    private static final String COMPLETE_INSTANCE = "nrOfCompletedInstances";
    private static final String INSTANCES = "nrOfInstances";

    public boolean completeTask(DelegateExecution execution, CounterSignParam counterSignParam) {
        boolean signStartMode = counterSignParam.isSignStartMode();
        if (signStartMode) {
            Integer completeInstance = (Integer)execution.getVariable(COMPLETE_INSTANCE);
            Integer instance = (Integer)execution.getVariable(INSTANCES);
            if (completeInstance >= instance) {
                return true;
            }
        }
        return false;
    }
}

