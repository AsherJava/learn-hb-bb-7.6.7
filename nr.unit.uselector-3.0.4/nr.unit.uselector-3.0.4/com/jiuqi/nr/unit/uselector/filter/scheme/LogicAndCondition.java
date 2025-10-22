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
import com.jiuqi.nr.unit.uselector.filter.scheme.AbstractConditionChain;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;

public class LogicAndCondition
extends AbstractConditionChain {
    public static final String KEYWORD = "logic_and_condition";

    public LogicAndCondition(IUnitTreeContext context, IUSelectorEntityRowProvider entityRowsProvider) {
        super(context, entityRowsProvider);
    }

    @Override
    public void handle(AbstractConditionChain.LogicCondition logical, IFilterStringList operateSet) {
        if (null != this.nextCondition) {
            this.nextCondition.handle(AbstractConditionChain.LogicCondition.AND, operateSet);
        }
    }
}

