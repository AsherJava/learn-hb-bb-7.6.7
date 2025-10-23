/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.loader.run;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import java.util.List;
import org.springframework.lang.Nullable;

public interface RuntimeReverseSchemeVisitor<E> {
    @Nullable
    public <DG extends DataGroup, DT extends DataTable, DM extends DataDimension> E visitGoTaNode(SchemeNode<E> var1, List<DG> var2, List<DT> var3, List<DM> var4);

    @Nullable
    public <DG extends DataGroup, DS extends DataScheme> E visitGoScNode(SchemeNode<E> var1, List<DG> var2, List<DS> var3);

    public E visitRootNode(SchemeNode<E> var1);

    public VisitorResult preVisitNode(SchemeNode<E> var1);

    public <DF extends DataField> E visitFieldNode(SchemeNode<E> var1, List<DF> var2);
}

