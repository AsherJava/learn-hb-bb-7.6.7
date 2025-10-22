/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 */
package com.jiuqi.nr.unit.uselector.checker;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import java.util.List;

public interface IRowCheckerHelper {
    public IRowChecker getChecker(String var1);

    public List<IRowChecker> getCheckersByGroup(CheckerGroup var1);

    public List<IRowChecker> getFilterSchemeCheckers(IUnitTreeContext var1);
}

