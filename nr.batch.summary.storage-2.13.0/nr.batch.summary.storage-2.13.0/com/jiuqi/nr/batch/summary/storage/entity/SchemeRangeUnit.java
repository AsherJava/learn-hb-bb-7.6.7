/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity;

import com.jiuqi.nr.batch.summary.storage.enumeration.RangeUnitType;
import java.util.List;

public interface SchemeRangeUnit {
    public RangeUnitType getRangeUnitType();

    public List<String> getCheckList();

    public String getExpression();

    public String valueToClob();
}

