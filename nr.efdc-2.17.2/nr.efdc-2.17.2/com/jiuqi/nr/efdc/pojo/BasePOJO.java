/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.efdc.pojo;

import java.util.Map;

public interface BasePOJO {
    public String getKey();

    public String getTitle();

    public String getCode();

    public Map<String, Object> getValueMap();

    public Object getFieldValue(String var1);
}

