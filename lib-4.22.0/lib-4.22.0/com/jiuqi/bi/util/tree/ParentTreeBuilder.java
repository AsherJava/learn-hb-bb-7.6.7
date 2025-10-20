/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tree;

import com.jiuqi.bi.util.tree.FastTreeBuilder;
import com.jiuqi.bi.util.tree.ObjectVistor;

public final class ParentTreeBuilder
extends FastTreeBuilder {
    public ParentTreeBuilder(ObjectVistor visitor) {
        super(visitor);
    }

    @Override
    protected String getObjectCode(Object item) {
        return this.visitor.getCode(item);
    }

    @Override
    protected String getObjectParent(Object item) {
        return this.visitor.getParentCode(item);
    }
}

