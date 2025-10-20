/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tree;

import com.jiuqi.bi.util.tree.FastTreeBuilder;
import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.TreeCodeStruct;
import com.jiuqi.bi.util.tree.TreeException;

public final class StructTreeBuilder
extends FastTreeBuilder {
    private TreeCodeStruct codeStruct;
    private boolean shortCodeMode;

    public StructTreeBuilder(ObjectVistor visitor) {
        super(visitor);
        this.shortCodeMode = false;
    }

    public StructTreeBuilder(ObjectVistor visitor, boolean shortCodeMode) {
        super(visitor);
        this.shortCodeMode = shortCodeMode;
    }

    public void setStruct(String structStr) throws TreeException {
        this.codeStruct = new TreeCodeStruct(structStr, this.shortCodeMode);
    }

    @Override
    protected String getObjectCode(Object item) {
        String code = this.visitor.getCode(item);
        return this.shortCodeMode ? this.codeStruct.longCode(code) : code;
    }

    @Override
    protected String getObjectParent(Object item) {
        String parentCode = this.codeStruct.parentCode(this.visitor.getCode(item));
        return this.shortCodeMode ? this.codeStruct.longCode(parentCode) : parentCode;
    }
}

