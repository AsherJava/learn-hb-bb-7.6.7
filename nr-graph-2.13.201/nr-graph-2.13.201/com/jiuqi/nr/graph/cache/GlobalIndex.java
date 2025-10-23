/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GlobalIndex
extends HashMap<String, String> {
    private static final long serialVersionUID = -4524394766427839068L;
    private static final GlobalIndex EMPTY_GLOBAL_INDEX = new GlobalIndex(Collections.emptyMap());

    GlobalIndex() {
    }

    GlobalIndex(Map<String, String> map) {
        super(map);
    }

    public static GlobalIndex emptyGlobalIndex() {
        return EMPTY_GLOBAL_INDEX;
    }
}

