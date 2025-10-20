/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 */
package com.jiuqi.va.formula.tofilter;

import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.va.formula.tofilter.BinaryOperatorToFilter;

public class EqualNodeToFilter
extends BinaryOperatorToFilter {
    @Override
    public String getSign(OperatorNode operatorNode) {
        return "==";
    }
}

