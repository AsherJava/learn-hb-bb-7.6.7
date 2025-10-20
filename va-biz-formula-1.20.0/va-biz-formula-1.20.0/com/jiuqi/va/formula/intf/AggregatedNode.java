/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.va.formula.intf;

import com.jiuqi.bi.syntax.ast.IASTNode;
import java.util.List;

public interface AggregatedNode {
    default public int getAggregatedIndex() {
        return 0;
    }

    default public int[] getAggregatedIndexes(List<IASTNode> parameters) {
        return null;
    }
}

