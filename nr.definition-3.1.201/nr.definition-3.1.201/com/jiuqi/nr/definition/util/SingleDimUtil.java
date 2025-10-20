/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SingleDimUtil {
    @Value(value="${jiuqi.nr.cross-task.singledim:true}")
    private boolean crossTaskSingleDim;

    public boolean getCrossTaskSingleDim() {
        return this.crossTaskSingleDim;
    }
}

