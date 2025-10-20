/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessNodeDO
 */
package com.jiuqi.va.workflow.domain;

import com.jiuqi.va.domain.workflow.ProcessNodeDO;
import java.util.List;

public class ProcessNodeDOS
extends ProcessNodeDO {
    private static final long serialVersionUID = 1L;
    private List<List<ProcessNodeDOS>> children;

    public List<List<ProcessNodeDOS>> getChildren() {
        return this.children;
    }

    public void setChildren(List<List<ProcessNodeDOS>> children) {
        this.children = children;
    }
}

