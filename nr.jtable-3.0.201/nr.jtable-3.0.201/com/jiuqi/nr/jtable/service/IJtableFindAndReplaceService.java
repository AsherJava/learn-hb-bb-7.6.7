/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.jtable.params.input.FindPageQueryInfo;
import java.util.Map;

public interface IJtableFindAndReplaceService {
    public Map<String, Object> findPage(FindPageQueryInfo var1);

    public Map<String, Object> replaceAll(FindPageQueryInfo var1);
}

