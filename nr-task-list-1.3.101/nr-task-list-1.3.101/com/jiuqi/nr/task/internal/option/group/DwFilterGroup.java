/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.spi.TaskOptionGroup
 */
package com.jiuqi.nr.task.internal.option.group;

import com.jiuqi.nr.definition.option.spi.TaskOptionGroup;
import org.springframework.stereotype.Component;

@Component
public class DwFilterGroup
implements TaskOptionGroup {
    public static final String TITLE = "\u8fc7\u6ee4\u6761\u4ef6";

    public String getPageTitle() {
        return "\u7ef4\u5ea6";
    }

    public String getTitle() {
        return TITLE;
    }

    public Double getOrder() {
        return 100.0;
    }

    public boolean isCustom() {
        return true;
    }

    public String getNewPluginName() {
        return "task-dw-option";
    }

    public String getNewExpose() {
        return "dwOption";
    }
}

