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
import com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo;

public class TodoConsumeItem {
    private final IBusinessObject businessObject;
    private final TodoConsumeInfo todoConsumeInfo;

    public TodoConsumeItem(IBusinessObject businessObject, TodoConsumeInfo todoConsumeInfo) {
        this.businessObject = businessObject;
        this.todoConsumeInfo = todoConsumeInfo;
    }

    public IBusinessObject getBusinessObject() {
        return this.businessObject;
    }

    public TodoConsumeInfo getTodoConsumeInfo() {
        return this.todoConsumeInfo;
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

