/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 */
package com.jiuqi.nr.datascheme.internal.tree.des;

import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.internal.tree.des.InterestSpecifiedTree;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;

public class DesInterestSpecifiedTree
extends InterestSpecifiedTree {
    public DesInterestSpecifiedTree(IEntityMetaService entityMetaService, PeriodEngineService periodEngineService, int interestType, String specifiedKey) {
        super(entityMetaService, periodEngineService, interestType, specifiedKey);
    }

    @Override
    public VisitorResult preVisitNode(SchemeNode<Void> ele) {
        String key = ele.getKey();
        if (this.specifiedKey.equals(key)) {
            return null;
        }
        if (this.nodeFilter != null && !this.nodeFilter.test(ele)) {
            return VisitorResult.TERMINATE;
        }
        if ((this.interestType & ele.getType()) == 0) {
            return VisitorResult.TERMINATE;
        }
        return null;
    }
}

