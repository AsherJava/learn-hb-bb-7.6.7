/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 */
package com.jiuqi.nr.workflow2.todo.event;

import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.todo.entity.TodoInfo;

public class TodoItem {
    private final IBusinessObject businessObject;
    private final TodoInfo todoInfo;

    public TodoItem(IBusinessObject businessObject, TodoInfo todoInfo) {
        this.businessObject = businessObject;
        this.todoInfo = todoInfo;
    }

    public IBusinessObject getBusinessObject() {
        return this.businessObject;
    }

    public TodoInfo getTodoInfo() {
        return this.todoInfo;
    }

    public String getPeriod() {
        FixedDimensionValue dimension = this.businessObject.getDimensions().getPeriodDimensionValue();
        if (dimension != null) {
            return (String)dimension.getValue();
        }
        return null;
    }

    public String getMdCode() {
        FixedDimensionValue dimension = this.businessObject.getDimensions().getDWDimensionValue();
        if (dimension != null) {
            return (String)dimension.getValue();
        }
        return null;
    }
}

