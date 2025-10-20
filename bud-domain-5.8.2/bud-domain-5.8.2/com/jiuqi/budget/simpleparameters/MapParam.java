/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.DefaultTenantDTO
 */
package com.jiuqi.budget.simpleparameters;

import com.jiuqi.budget.common.domain.DefaultTenantDTO;
import java.util.Map;

public class MapParam
implements DefaultTenantDTO {
    private Map<String, Object> valMap;

    public MapParam(Map valMap) {
        this.valMap = valMap;
    }

    public Map<String, Object> getValMap() {
        return this.valMap;
    }

    public void setValMap(Map<String, Object> valMap) {
        this.valMap = valMap;
    }
}

