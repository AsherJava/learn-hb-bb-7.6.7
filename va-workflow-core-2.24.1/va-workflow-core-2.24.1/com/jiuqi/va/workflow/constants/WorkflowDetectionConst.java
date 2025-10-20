/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkflowDetectionConst {
    public static final String DETECT_NO_REACHABLE_NODE = "\u6ca1\u6709\u540e\u7eed\u53ef\u8fbe\u8282\u70b9";
    public static final String DETECT_MULTIPLE_REACHABLE_NODE = "\u6709\u591a\u4e2a\u540e\u7eed\u53ef\u8fbe\u8282\u70b9";
    public static final String DETECT_CONVERGE = "\u6c47\u805a";
    public static final String DETECT_DIVERGENT = "\u53d1\u6563";
    public static final String DETECT_SUBPROCESS = "\u5b50\u6d41\u7a0b";
    public static final String DETECT_AUTO = "\u81ea\u52a8";
    public static final String DETECT_END = "\u7ed3\u675f";
    public static final String DETECT_SKIP_EMPTY_USER = "\u53c2\u4e0e\u8005\u4e3a\u7a7a\u8df3\u8fc7";
    public static final String DETECT_SKIP_SAME_USER = "\u76f8\u540c\u53c2\u4e0e\u8005\u8df3\u8fc7";
    public static final String DETECT_NOT_CONFIG_USER = "\u672a\u914d\u7f6e\u53c2\u4e0e\u8005";
    public static final String DETECT_INTERRUPT = "\u4e2d\u65ad";
    public static final String DETECT_SUB_FAILURE = "\u5b50\u6d41\u7a0b\u68c0\u6d4b\u5931\u8d25";
    public static final List<String> TYPE_STRING = new ArrayList<String>(Arrays.asList("UUID", "NVARCHAR", "CLOB", "IDENTIFY", "STRING"));
    public static final List<String> TYPE_DATE = new ArrayList<String>(Arrays.asList("DATE", "TIMESTAMP", "DATETIME"));
    public static final String TYPE_INTEGER = "INTEGER";
    public static final String TYPE_NUMERIC = "NUMERIC";
    public static final String TYPE_LONG = "LONG";
    public static final String TYPE_DECIMAL = "DECIMAL";
    public static final String TYPE_BOOLEAN = "BOOLEAN";
}

