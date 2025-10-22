/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.query;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UnionModel {
    private final Map<String, List<String>> dimFilters = new LinkedHashMap<String, List<String>>();
    private Map<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();

    public Map<String, List<String>> getDimFilters() {
        return this.dimFilters;
    }

    public Map<Integer, Boolean> getCheckTypes() {
        return this.checkTypes;
    }

    public void setCheckTypes(Map<Integer, Boolean> checkTypes) {
        this.checkTypes = checkTypes;
    }
}

