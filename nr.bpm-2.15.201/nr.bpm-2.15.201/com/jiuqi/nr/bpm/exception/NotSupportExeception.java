/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.exception;

import com.jiuqi.nr.bpm.ProcessEngine;

public class NotSupportExeception
extends RuntimeException {
    private static final long serialVersionUID = 6450325451910557827L;

    public NotSupportExeception(Class<?> classes) {
        ProcessEngine.ProcessEngineType type = this.getProcessEngineType(classes);
        throw new RuntimeException("\u65b9\u6cd5\u8c03\u7528\u9519\u8bef\uff0c\u8be5\u5f15\u64ce\u6682\u672a\u652f\u6301\u6b64\u65b9\u6cd5\uff01\u5f15\u64ce\u7c7b\u578b\uff1a" + type.name());
    }

    private ProcessEngine.ProcessEngineType getProcessEngineType(Class<?> classes) {
        if (classes.getSimpleName().startsWith("Process")) {
            return ProcessEngine.ProcessEngineType.UPLOAD;
        }
        return ProcessEngine.ProcessEngineType.ACTIVITI;
    }
}

