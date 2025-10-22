/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.loader;

import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.dataresource.loader.ResourceNodeVisitor;
import com.jiuqi.nr.dataresource.loader.ReverseDataResourceNodeVisitor;
import java.util.List;

public interface DataResourceLoaderStrategy {
    public boolean matching(int var1);

    public <E> void visitRoot(ResourceNode<E> var1, ResourceNodeVisitor<E> var2);

    public <E> List<ResourceNode<E>> visitNode(ResourceNode<E> var1, ResourceNodeVisitor<E> var2);

    public <E> ResourceNode<E> visitNode(ResourceNode<E> var1, ReverseDataResourceNodeVisitor<E> var2);
}

