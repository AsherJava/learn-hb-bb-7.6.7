/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.checker;

import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValuesImpl;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.List;

public interface IRowCheckExecutor {
    default public boolean clearSet() {
        return false;
    }

    default public IFilterCheckValues getValues() {
        return new IFilterCheckValuesImpl();
    }

    public List<String> executeCheck(IFilterCheckValues var1, IUSelectorEntityRowProvider var2);
}

