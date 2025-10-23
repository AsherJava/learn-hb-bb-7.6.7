/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.loader.des;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import java.util.List;
import org.springframework.lang.Nullable;

public interface ReverseSchemeNodeVisitor<E> {
    @Nullable
    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> E visitGoTaNode(SchemeNode<E> var1, List<DG> var2, List<DT> var3, List<DM> var4);

    @Nullable
    public <DG extends DesignDataGroup, DS extends DesignDataScheme> E visitGoScNode(SchemeNode<E> var1, List<DG> var2, List<DS> var3);

    public E visitRootNode(SchemeNode<E> var1);

    public VisitorResult preVisitNode(SchemeNode<E> var1);

    public <DF extends DesignDataField> E visitFieldNode(SchemeNode<E> var1, List<DF> var2);
}

