/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.impl.group.UnitTreeGroup
 */
package com.jiuqi.nr.task.internal.option.group;

import com.jiuqi.nr.definition.option.impl.group.UnitTreeGroup;
import org.springframework.stereotype.Component;

@Component
public class UnitTreeGroupV3
extends UnitTreeGroup {
    public String getNewPluginName() {
        return "task-dw-option";
    }

    public String getNewExpose() {
        return "unitTreeGroup";
    }

    public String getPageTitle() {
        return "\u7ef4\u5ea6";
    }

    public Double getOrder() {
        return 0.0;
    }
}

