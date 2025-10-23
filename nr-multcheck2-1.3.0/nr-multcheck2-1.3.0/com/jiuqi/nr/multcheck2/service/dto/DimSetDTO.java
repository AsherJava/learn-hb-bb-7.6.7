/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.service.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DimSetDTO
implements Serializable {
    private Map<String, Set<String>> dims = new HashMap<String, Set<String>>();

    public Map<String, Set<String>> getDims() {
        return this.dims;
    }

    public void setDims(Map<String, Set<String>> dims) {
        this.dims = dims;
    }
}

