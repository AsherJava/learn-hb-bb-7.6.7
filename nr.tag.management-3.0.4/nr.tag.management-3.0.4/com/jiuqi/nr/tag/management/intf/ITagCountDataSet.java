/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.intf;

import java.util.List;
import java.util.Map;

public interface ITagCountDataSet {
    public boolean isEmpty();

    public boolean containsKey(String var1);

    public List<String> getResultSet(String var1);

    public Map<String, List<String>> getDataSet();
}

