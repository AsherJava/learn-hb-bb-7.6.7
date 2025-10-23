/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.loader.des;

import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import java.util.List;

public interface DataSchemeLoaderStrategy {
    public boolean matching(int var1);

    public <E> void visitRoot(SchemeNode<E> var1, SchemeNodeVisitor<E> var2);

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> var1, SchemeNodeVisitor<E> var2, Integer var3);

    public <E> SchemeNode<E> visitNode(SchemeNode<E> var1, ReverseSchemeNodeVisitor<E> var2, Integer var3);
}

