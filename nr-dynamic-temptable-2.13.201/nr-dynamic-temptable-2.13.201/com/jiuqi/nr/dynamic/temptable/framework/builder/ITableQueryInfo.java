/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dynamic.temptable.framework.builder;

import com.jiuqi.nr.dynamic.temptable.common.DynamicTempTableStatusEnum;
import java.util.Iterator;
import java.util.Map;

public interface ITableQueryInfo {
    public Iterator<Integer> ColumnCountIterator();

    public DynamicTempTableStatusEnum getQueryStatus();

    public Iterator<Map.Entry<String, String>> orderColumnIterator();
}

