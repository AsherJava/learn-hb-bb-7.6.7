/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain;

import java.util.Arrays;
import java.util.List;

public final class WorkflowConst {
    public static final int WORKFLOW_BUSINESS_FLAG_DISABLE = 1;
    public static final int WORKFLOW_BUSINESS_FLAG_AVAILABLE = 0;
    public static final String SYMBOL_ONE = "#";
    public static final String SYMBOL_TWO = "/";
    public static final String SYMBOL_$ = "$";
    public static final String KEY_LIMIT = "limit";
    public static final String KEY_OFFSET = "offset";
    public static final int CURING_LIMIT = 16;
    public static final int CURING_OFFSET = 0;
    public static final String COMPLETE_USER_TYPE = "completeusertype";
    public static final List<String> ELEMENTTYPELIST = Arrays.asList("UserTask", "ParallelGateway", "SequenceFlow", "StartNoneEvent", "EndNoneEvent", "JoinParallelGateway");

    private WorkflowConst() {
    }
}

