/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.condition;

import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;

public interface CustomConditionRow
extends CustomCalibreRow {
    public String[] getPath();

    public int getMaxDepth();

    public int getChildCount();

    public int getAllChildCount();
}

