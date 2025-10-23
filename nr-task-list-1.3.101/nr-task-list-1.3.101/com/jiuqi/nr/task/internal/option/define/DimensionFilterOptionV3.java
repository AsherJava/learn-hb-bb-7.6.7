/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.core.VisibleType
 *  com.jiuqi.nr.definition.option.internal.DimensionFilterOption
 */
package com.jiuqi.nr.task.internal.option.define;

import com.jiuqi.nr.definition.option.core.VisibleType;
import com.jiuqi.nr.definition.option.internal.DimensionFilterOption;
import com.jiuqi.nr.task.internal.option.define.DimensionGroupOptionV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DimensionFilterOptionV3
extends DimensionFilterOption {
    @Autowired
    private DimensionGroupOptionV3 dimensionGroupOptionV3;

    public String getPageTitle() {
        return "\u7ef4\u5ea6";
    }

    public VisibleType getVisibleType(String taskKey) {
        return this.dimensionGroupOptionV3.getVisibleType(taskKey);
    }
}

