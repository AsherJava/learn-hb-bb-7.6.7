/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import java.util.ArrayList;
import org.springframework.stereotype.Component;

@Component
public class InitOfQueryCount
implements IRowChecker {
    public static final String KEYWORD = "#init-of-query-count";

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return null;
    }

    @Override
    public CheckerGroup[] getGroup() {
        return null;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public IRowCheckExecutor getExecutor(IUnitTreeContext ctx) {
        return (values, entityRowsProvider) -> new ArrayList();
    }
}

