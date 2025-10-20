/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.operator.OperatorNode
 */
package com.jiuqi.va.formula.tojs;

import com.jiuqi.bi.syntax.operator.OperatorNode;
import com.jiuqi.va.formula.tojs.BinaryOperatorToJS;

public class EqualNodeToJS
extends BinaryOperatorToJS {
    @Override
    public String getSign(OperatorNode operatorNode) {
        return "==";
    }
}

