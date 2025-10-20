/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.gcreport.common.elementtable.impl;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.gcreport.common.util.MapToPropertyJsonSerializer;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ElementTableDataVO {
    private String id;
    @JsonSerialize(using=MapToPropertyJsonSerializer.class)
    private Map<String, BigDecimal> zbvalue = new HashMap<String, BigDecimal>();

    public Map<String, BigDecimal> getZbvalue() {
        return this.zbvalue;
    }

    public void setZbvalue(Map<String, BigDecimal> zbvalue) {
        this.zbvalue = zbvalue;
    }

    public void addZbValue(String key, BigDecimal value) {
        BigDecimal v = this.getZbvalue().get(key);
        if (v == null) {
            v = BigDecimal.ZERO;
        }
        this.getZbvalue().put(key, v.add(value));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

