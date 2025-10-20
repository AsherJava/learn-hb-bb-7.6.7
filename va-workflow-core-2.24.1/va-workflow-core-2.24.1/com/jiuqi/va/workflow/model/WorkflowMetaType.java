/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.meta.MetaType
 */
package com.jiuqi.va.workflow.model;

import com.jiuqi.va.biz.intf.meta.MetaType;
import org.springframework.stereotype.Component;

@Component
public class WorkflowMetaType
implements MetaType {
    public static final String NAME = "workflow";
    public static final String TITLE = "\u5de5\u4f5c\u6d41";

    public String getName() {
        return NAME;
    }

    public String getTitle() {
        return TITLE;
    }
}

