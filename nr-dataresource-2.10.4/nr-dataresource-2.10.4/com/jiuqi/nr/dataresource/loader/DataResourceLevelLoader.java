/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.loader;

import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.loader.ResourceNodeVisitor;
import com.jiuqi.nr.dataresource.loader.ReverseDataResourceNodeVisitor;

public interface DataResourceLevelLoader {
    public <E> void walkDataResourceTree(ResourceNode<E> var1, ResourceNodeVisitor<E> var2);

    public <E> E reverseDataResourceTree(ResourceNode<E> var1, ReverseDataResourceNodeVisitor<E> var2);
}

