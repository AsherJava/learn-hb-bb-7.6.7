/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;

public class BusinessKey
implements IBusinessKey {
    private final String taskKey;
    private final IBusinessObject businessObject;

    public BusinessKey(String taskKey, IBusinessObject businessObject) {
        this.taskKey = taskKey;
        this.businessObject = businessObject;
    }

    @Override
    public String getTask() {
        return this.taskKey;
    }

    @Override
    public IBusinessObject getBusinessObject() {
        return this.businessObject;
    }

    public int hashCode() {
        return this.businessObject.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof IBusinessKey)) {
            return false;
        }
        IBusinessKey another = (IBusinessKey)obj;
        return this.taskKey.equals(another.getTask()) && this.businessObject.equals(another.getBusinessObject());
    }
}

