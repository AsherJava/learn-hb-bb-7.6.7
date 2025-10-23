/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.option.impl.group.AuditSchemeGroup
 */
package com.jiuqi.nr.task.internal.option.group;

import com.jiuqi.nr.definition.option.impl.group.AuditSchemeGroup;
import org.springframework.stereotype.Component;

@Component
public class AuditSchemeGroupV3
extends AuditSchemeGroup {
    public String getNewPluginName() {
        return "task-dw-option";
    }

    public String getNewExpose() {
        return "auditSchemeCustom";
    }
}

