/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;

public class BusinessKeyCollection
implements IBusinessKeyCollection {
    private final String taskKey;
    private final IBusinessObjectCollection bizObjectCollection;

    public BusinessKeyCollection(String taskKey, IBusinessObjectCollection bizObjectCollection) {
        if (taskKey == null || taskKey == "") {
            throw new IllegalArgumentException("'taskKey' must not be null.");
        }
        if (bizObjectCollection == null) {
            throw new IllegalArgumentException("'bizObjectCollection' must not be null.");
        }
        this.taskKey = taskKey;
        this.bizObjectCollection = bizObjectCollection;
    }

    @Override
    public String getTask() {
        return this.taskKey;
    }

    @Override
    public IBusinessObjectCollection getBusinessObjects() {
        return this.bizObjectCollection;
    }
}

