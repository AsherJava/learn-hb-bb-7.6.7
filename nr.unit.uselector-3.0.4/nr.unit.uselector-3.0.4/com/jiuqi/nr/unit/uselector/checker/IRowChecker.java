/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 */
package com.jiuqi.nr.unit.uselector.checker;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.DisplayType;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;

public interface IRowChecker {
    public String getKeyword();

    public String getShowText();

    public CheckerGroup[] getGroup();

    default public DisplayType getDisplayType() {
        return DisplayType.NONE;
    }

    public boolean isChecked();

    default public int getOrder() {
        return -1;
    }

    default public boolean isDisplay(IUnitTreeContext ctx) {
        return true;
    }

    public IRowCheckExecutor getExecutor(IUnitTreeContext var1);
}

