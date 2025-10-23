/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.datascheme.api.loader.run;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;

public interface RuntimeSchemeVisitor<E> {
    public VisitorResult preVisitNode(SchemeNode<E> var1);

    @Nullable
    public E visitRootIsSchemeNode(DataScheme var1);

    @Nullable
    public E visitRootIsGroupNode(DataGroup var1);

    @Nullable
    public E visitRootIsTableNode(DataTable var1);

    public <DG extends DataGroup, DS extends DataScheme> Map<String, E> visitSchemeGroupNode(SchemeNode<E> var1, List<DG> var2, List<DS> var3);

    @Nullable
    public <DG extends DataGroup, DT extends DataTable, DM extends DataDimension> Map<String, E> visitSchemeNode(SchemeNode<E> var1, List<DG> var2, List<DT> var3, List<DM> var4);

    @Nullable
    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<E> var1, List<DA> var2);

    @Nullable
    public <DG extends DataGroup, DT extends DataTable> Map<String, E> visitGroupNode(SchemeNode<E> var1, List<DG> var2, List<DT> var3);

    @Nullable
    public <DF extends DataField> void visitTableNode(SchemeNode<E> var1, List<DF> var2);
}

