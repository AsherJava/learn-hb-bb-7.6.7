/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tree;

import com.jiuqi.bi.util.tree.ObjectVistor;
import com.jiuqi.bi.util.tree.ParentTreeBuilder;
import com.jiuqi.bi.util.tree.StructTreeBuilder;
import com.jiuqi.bi.util.tree.TreeBuilder;
import com.jiuqi.bi.util.tree.TreeException;

public class TreeBuilderFactory {
    private TreeBuilderFactory() {
    }

    public static TreeBuilder createParentTreeBuilder(ObjectVistor visitor) throws TreeException {
        if (visitor == null) {
            throw new TreeException("\u6ca1\u6709\u6307\u5b9a\u5bf9\u8c61\u8bbf\u95ee\u5668\uff0c\u65e0\u6cd5\u8fdb\u884c\u5bf9\u8c61\u64cd\u4f5c\uff01");
        }
        return new ParentTreeBuilder(visitor);
    }

    public static TreeBuilder createStructTreeBuilder(ObjectVistor visitor, String structStr, boolean shortCodeMode) throws TreeException {
        if (visitor == null) {
            throw new TreeException("\u6ca1\u6709\u6307\u5b9a\u5bf9\u8c61\u8bbf\u95ee\u5668\uff0c\u65e0\u6cd5\u8fdb\u884c\u5bf9\u8c61\u64cd\u4f5c\uff01");
        }
        StructTreeBuilder builder = new StructTreeBuilder(visitor, shortCodeMode);
        builder.setStruct(structStr);
        return builder;
    }

    public static TreeBuilder createStructTreeBuilder(ObjectVistor visitor, String structStr) throws TreeException {
        return TreeBuilderFactory.createStructTreeBuilder(visitor, structStr, false);
    }
}

