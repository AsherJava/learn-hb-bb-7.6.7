/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.nr.unit.uselector.filter.listselect.FTableConditionRow;
import java.util.List;

public interface USelectorListSelectExecutor {
    public int getExactCount();

    public int getFuzzyCount();

    public int getUnMatch();

    public List<FTableConditionRow> executeListSelect();
}

