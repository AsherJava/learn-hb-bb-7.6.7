/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import com.zaxxer.sparsebits.SparseBitSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Grouping {
    private final Map<Object, SparseBitSet> groups = new LinkedHashMap<Object, SparseBitSet>();
    private final Set<Integer> columns = new LinkedHashSet<Integer>();
}

