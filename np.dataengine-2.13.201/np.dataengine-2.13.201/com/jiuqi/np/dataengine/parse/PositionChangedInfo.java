/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.parse;

import java.util.HashMap;
import java.util.Map;

public class PositionChangedInfo {
    private Map<Integer, Integer> rowChangedMap = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> colChangedMap = new HashMap<Integer, Integer>();

    public Map<Integer, Integer> getRowChangedMap() {
        return this.rowChangedMap;
    }

    public Map<Integer, Integer> getColChangedMap() {
        return this.colChangedMap;
    }
}

