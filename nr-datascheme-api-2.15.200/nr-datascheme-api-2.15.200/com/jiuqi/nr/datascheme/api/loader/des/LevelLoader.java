/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.loader.des;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.loader.des.ReverseSchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;

public interface LevelLoader {
    public <E> void walkDataSchemeTree(SchemeNode<E> var1, SchemeNodeVisitor<E> var2);

    public <E> void walkDataSchemeTree(SchemeNode<E> var1, SchemeNodeVisitor<E> var2, Integer var3);

    public <E> void walkDataSchemeTree(DesignDataScheme var1, SchemeNodeVisitor<E> var2);

    public <E> void walkDataSchemeTree(DesignDataGroup var1, SchemeNodeVisitor<E> var2);

    public <E> E reverseDataSchemeTree(SchemeNode<E> var1, ReverseSchemeNodeVisitor<E> var2);

    public <E> E reverseDataSchemeTree(SchemeNode<E> var1, ReverseSchemeNodeVisitor<E> var2, Integer var3);
}

