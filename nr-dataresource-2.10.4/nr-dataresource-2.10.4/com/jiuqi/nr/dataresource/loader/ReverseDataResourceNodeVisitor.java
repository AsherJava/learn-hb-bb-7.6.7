/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 */
package com.jiuqi.nr.dataresource.loader;

import com.jiuqi.nr.dataresource.DataResource;
import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceDefineGroup;
import com.jiuqi.nr.dataresource.DimAttribute;
import com.jiuqi.nr.dataresource.ResourceNode;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import java.util.List;
import org.springframework.lang.Nullable;

public interface ReverseDataResourceNodeVisitor<E> {
    public VisitorResult preVisitNode(ResourceNode<E> var1);

    public E visitRootNode(ResourceNode<E> var1);

    @Nullable
    public <DR extends DataResource, DA extends DimAttribute, DF extends DataField> E visitGroupNode(ResourceNode<E> var1, List<DR> var2, List<DA> var3, List<DF> var4);

    @Nullable
    public <DD extends DataResourceDefine, DG extends DataResourceDefineGroup> E visitGoDeNode(ResourceNode<E> var1, List<DG> var2, List<DD> var3);
}

