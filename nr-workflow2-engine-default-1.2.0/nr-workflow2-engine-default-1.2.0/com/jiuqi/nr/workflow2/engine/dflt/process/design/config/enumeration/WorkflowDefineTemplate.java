/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration;

public enum WorkflowDefineTemplate {
    STANDARD_WORKFLOW("\u6807\u51c6\u6d41\u7a0b"),
    SUBMIT_WORKFLOW("\u9001\u5ba1\u6d41\u7a0b");

    public final String title;

    private WorkflowDefineTemplate(String title) {
        this.title = title;
    }
}

