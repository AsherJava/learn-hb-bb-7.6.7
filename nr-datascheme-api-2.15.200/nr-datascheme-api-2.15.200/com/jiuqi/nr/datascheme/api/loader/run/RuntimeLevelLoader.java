/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.loader.run;

import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeReverseSchemeVisitor;
import com.jiuqi.nr.datascheme.api.loader.run.RuntimeSchemeVisitor;

public interface RuntimeLevelLoader {
    public <E> void walkDataSchemeTree(SchemeNode<E> var1, RuntimeSchemeVisitor<E> var2);

    public <E> void walkDataSchemeTree(SchemeNode<E> var1, RuntimeSchemeVisitor<E> var2, Integer var3);

    public <E> void walkDataSchemeTree(DataScheme var1, RuntimeSchemeVisitor<E> var2);

    public <E> void walkDataSchemeTree(DataGroup var1, RuntimeSchemeVisitor<E> var2);

    public <E> E reverseDataSchemeTree(SchemeNode<E> var1, RuntimeReverseSchemeVisitor<E> var2);

    public <E> E reverseDataSchemeTree(SchemeNode<E> var1, RuntimeReverseSchemeVisitor<E> var2, Integer var3);
}

