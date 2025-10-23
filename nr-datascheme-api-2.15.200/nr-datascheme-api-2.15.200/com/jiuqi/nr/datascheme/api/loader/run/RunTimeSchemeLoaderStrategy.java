/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.loader.run;

import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;
import java.util.List;

public interface RunTimeSchemeLoaderStrategy {
    public boolean matching(int var1);

    public <E> void visitRoot(SchemeNode<E> var1, RuntimeSchemeVisitor<E> var2);

    public <E> List<SchemeNode<E>> visitNode(SchemeNode<E> var1, RuntimeSchemeVisitor<E> var2, Integer var3);

    public <E> SchemeNode<E> visitNode(SchemeNode<E> var1, RuntimeReverseSchemeVisitor<E> var2, Integer var3);
}

