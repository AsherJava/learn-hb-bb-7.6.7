/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.common.QueryField;
import java.util.UUID;

public final class OrderByItem {
    public QueryField field;
    public UUID lookupUID;
    public IASTNode expr;
    public boolean descending;
    public boolean specified = false;

    public OrderByItem clone() {
        OrderByItem varCopy = new OrderByItem();
        varCopy.field = this.field;
        varCopy.lookupUID = this.lookupUID;
        varCopy.expr = this.expr;
        varCopy.descending = this.descending;
        return varCopy;
    }
}

