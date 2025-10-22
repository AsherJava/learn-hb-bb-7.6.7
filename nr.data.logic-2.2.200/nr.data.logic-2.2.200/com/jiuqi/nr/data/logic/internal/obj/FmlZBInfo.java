/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.obj;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FmlZBInfo {
    private final Map<String, List<String>> fmlWriteZBKeyMap;
    private final Set<String> allWriteZBKeys;

    public FmlZBInfo(Set<String> allWriteZBKeys, Map<String, List<String>> fmlWriteZBKeyMap) {
        this.allWriteZBKeys = allWriteZBKeys;
        this.fmlWriteZBKeyMap = fmlWriteZBKeyMap;
    }

    public Set<String> getAllWriteZBKeys() {
        return this.allWriteZBKeys;
    }

    public Map<String, List<String>> getFmlWriteZBKeyMap() {
        return this.fmlWriteZBKeyMap;
    }
}

