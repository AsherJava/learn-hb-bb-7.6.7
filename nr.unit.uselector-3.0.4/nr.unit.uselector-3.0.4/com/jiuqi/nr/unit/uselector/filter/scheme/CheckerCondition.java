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
import com.jiuqi.nr.unit.uselector.checker.IFilterCheckValues;
import com.jiuqi.nr.unit.uselector.checker.IRowCheckExecutor;
import com.jiuqi.nr.unit.uselector.checker.IRowChecker;
import com.jiuqi.nr.unit.uselector.filter.scheme.AbstractConditionChain;
import com.jiuqi.nr.unit.uselector.source.IUSelectorEntityRowProvider;
import java.util.List;

public class CheckerCondition
extends AbstractConditionChain {
    private IRowChecker checker;
    private IFilterCheckValues checkValues;

    public CheckerCondition(IUnitTreeContext context, IUSelectorEntityRowProvider entityRowsProvider) {
        super(context, entityRowsProvider);
    }

    public void setRowChecker(IRowChecker checker) {
        this.checker = checker;
    }

    public void setCheckValues(IFilterCheckValues checkValues) {
        this.checkValues = checkValues;
    }

    @Override
    public void handle(AbstractConditionChain.LogicCondition logical, IFilterStringList operateSet) {
        if (null != logical) {
            this.handNode(logical, operateSet);
        } else {
            this.handFirstNode(logical, operateSet);
        }
        if (null != this.nextCondition) {
            this.nextCondition.handle(logical, operateSet);
        }
    }

    private void handFirstNode(AbstractConditionChain.LogicCondition logical, IFilterStringList operateSet) {
        IRowCheckExecutor executer = this.checker.getExecutor(this.context);
        List<String> resultSet = executer.executeCheck(this.checkValues, this.entityRowsProvider);
        operateSet.unionAll(resultSet);
    }

    private void handNode(AbstractConditionChain.LogicCondition logical, IFilterStringList operateSet) {
        IRowCheckExecutor executer = this.checker.getExecutor(this.context);
        List<String> resultSet = executer.executeCheck(this.checkValues, this.entityRowsProvider);
        if (AbstractConditionChain.LogicCondition.AND.compareTo(logical) == 0) {
            operateSet.retainAll(resultSet);
        } else if (AbstractConditionChain.LogicCondition.OR.compareTo(logical) == 0) {
            operateSet.unionAll(resultSet);
        }
    }
}

