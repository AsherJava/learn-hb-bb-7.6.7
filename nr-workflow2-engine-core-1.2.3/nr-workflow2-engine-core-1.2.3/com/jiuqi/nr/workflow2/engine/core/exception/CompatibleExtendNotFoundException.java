/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

public class CompatibleExtendNotFoundException
extends RuntimeException {
    private static final long serialVersionUID = -5371457382930010970L;

    public CompatibleExtendNotFoundException(String workflowEngine) {
        super("\u672a\u627e\u5230\u4e0e\u6d41\u7a0b\u5f15\u64ceId:" + workflowEngine + " \u76f8\u5339\u914d\u7684\u9002\u914d\u5c42\u53c2\u6570\u517c\u5bb9\u6269\u5c55\u5b9e\u73b0");
    }
}

