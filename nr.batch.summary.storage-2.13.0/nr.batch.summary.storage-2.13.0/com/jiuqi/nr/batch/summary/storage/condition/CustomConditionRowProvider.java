/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.condition;

import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow;
import java.util.List;

public interface CustomConditionRowProvider {
    public List<CustomConditionRow> getAllRows();

    public List<CustomConditionRow> getRootRows();

    public CustomConditionRow findRow(String var1);

    public List<CustomConditionRow> getChildRows(String var1);

    public List<CustomConditionRow> getAllChildRows(String var1);

    public int getMaxDepth();

    public int getTotalCount();
}

