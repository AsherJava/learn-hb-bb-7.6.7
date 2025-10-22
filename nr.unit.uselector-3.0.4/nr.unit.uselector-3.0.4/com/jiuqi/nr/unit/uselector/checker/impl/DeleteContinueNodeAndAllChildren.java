/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.unit.uselector.checker.impl;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.unit.uselector.checker.CheckerGroup;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class DeleteContinueNodeAndAllChildren
implements IRowChecker {
    public static final String KEYWORD = "#delete-continue-node-and-allChildren";
    @Resource(name="#check-continue-node-and-allChildren")
    private IRowChecker checker;

    @Override
    public String getKeyword() {
        return KEYWORD;
    }

    @Override
    public String getShowText() {
        return "\u5220\u9664\u540c\u7ea7\u8282\u70b9\uff08\u5305\u542b\u6240\u6709\u4e0b\u7ea7\uff09\u8303\u56f4";
    }

    @Override
    public CheckerGroup[] getGroup() {
        return new CheckerGroup[]{CheckerGroup.OTHERS};
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public IRowCheckExecutor getExecutor(IUnitTreeContext ctx) {
        return this.checker.getExecutor(ctx);
    }
}

