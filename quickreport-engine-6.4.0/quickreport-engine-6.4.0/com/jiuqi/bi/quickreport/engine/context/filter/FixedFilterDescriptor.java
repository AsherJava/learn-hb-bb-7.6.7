/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.FilterItem
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.context.filter;

import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.quickreport.engine.context.filter.FilterDescriptor;
import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.parser.IContext;

public final class FixedFilterDescriptor
extends FilterDescriptor {
    private final boolean value;

    public FixedFilterDescriptor(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public FilterItem toFilter() {
        FilterItem filter = new FilterItem();
        filter.setExpr(this.toFilterString());
        return filter;
    }

    @Override
    public IASTNode toASTFilter(IContext context) {
        return DataNode.valueOf((boolean)this.value);
    }

    @Override
    protected String toFilterString() {
        return this.value ? "TRUE" : "FALSE";
    }

    @Override
    public int hashCode() {
        return this.value ? 1 : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FixedFilterDescriptor)) {
            return false;
        }
        return ((FixedFilterDescriptor)obj).value == this.value;
    }

    @Override
    public IFilterDescriptor mapTo(String dsName, DSField field) {
        return this;
    }
}

