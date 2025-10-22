/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.service;

import java.util.Map;

public interface IActionAlias {
    public Map<String, String> actionCodeAndStateName(String var1);

    public Map<String, String> actionStateCodeAndStateName(String var1);

    public Map<String, String> actionCodeAndActionName(String var1);

    public Map<String, String> nodeAndNodeName(String var1);
}

