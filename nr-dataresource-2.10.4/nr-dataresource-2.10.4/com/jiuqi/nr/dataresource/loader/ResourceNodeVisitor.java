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
import java.util.Map;
import org.springframework.lang.Nullable;

public interface ResourceNodeVisitor<E> {
    public VisitorResult preVisitNode(ResourceNode<E> var1);

    @Nullable
    public E visitRootIsDefineNode(DataResourceDefine var1);

    @Nullable
    public E visitRootIsGroupNode(DataResourceDefineGroup var1);

    @Nullable
    public <DG extends DataResource> Map<String, E> visitResourceDefineNode(ResourceNode<E> var1, List<DG> var2);

    @Nullable
    public <DG extends DataResource, DA extends DimAttribute, DF extends DataField> Map<String, E> visitResourceNode(ResourceNode<E> var1, List<DG> var2, List<DA> var3, List<DF> var4);

    public <DG extends DataResourceDefineGroup, DR extends DataResourceDefine> Map<String, E> visitGroupNode(ResourceNode<E> var1, List<DG> var2, List<DR> var3);
}

