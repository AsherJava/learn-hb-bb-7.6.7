/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext
 *  com.jiuqi.nr.itreebase.collection.IFilterStringList
 */
package com.jiuqi.nr.unit.uselector.filter.scheme;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuqi.nr.itreebase.collection.IFilterStringList;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;

public abstract class AbstractConditionChain {
    protected IUnitTreeContext context;
    protected AbstractConditionChain nextCondition;
    protected IUSelectorEntityRowProvider entityRowsProvider;

    protected AbstractConditionChain(IUnitTreeContext context, IUSelectorEntityRowProvider entityRowsProvider) {
        this.context = context;
        this.entityRowsProvider = entityRowsProvider;
    }

    public void setNextCondition(AbstractConditionChain nextCondition) {
        this.nextCondition = nextCondition;
    }

    public abstract void handle(LogicCondition var1, IFilterStringList var2);

    public static enum LogicCondition {
        AND,
        OR;

    }
}

