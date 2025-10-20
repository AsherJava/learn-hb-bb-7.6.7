/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark;

import java.util.List;
import java.util.Map;

public interface IResourceTagProvider {
    public String getType();

    public Map<String, String> getValue(String var1, List<String> var2);
}

