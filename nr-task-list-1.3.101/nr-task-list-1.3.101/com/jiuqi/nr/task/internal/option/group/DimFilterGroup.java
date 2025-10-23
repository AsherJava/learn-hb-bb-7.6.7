/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.spi.TaskOptionGroup
 */
package com.jiuqi.nr.task.internal.option.group;

import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;

public class DimFilterGroup
implements TaskOptionGroup {
    public String getPageTitle() {
        return "\u7ef4\u5ea6";
    }

    public String getTitle() {
        return "\u60c5\u666f\u8fc7\u6ee4\u6761\u4ef6";
    }

    public Double getOrder() {
        return 200.0;
    }

    public boolean isCustom() {
        return true;
    }

    public String getNewPluginName() {
        return "task-dw-option";
    }

    public String getNewExpose() {
        return "dimensionFilter";
    }
}

