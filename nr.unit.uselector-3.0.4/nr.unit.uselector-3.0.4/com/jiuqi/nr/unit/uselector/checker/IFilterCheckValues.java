/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.checker;

import java.util.List;
import java.util.Map;

public interface IFilterCheckValues {
    public static final String TEXT = "text";
    public static final String VALUE = "value";
    public static final String K_DO_IN_RESULT = "isInResult";
    public static final String V_TRUE = "T";
    public static final String V_FALSE = "F";
    public static final String K_FORM_SCHEME = "formSchemeKey";
    public static final String K_RUNTIME_PARA = "runtimePara";

    public Map<String, String> getRuntimePara();

    public List<Map<String, String>> getValues();
}

