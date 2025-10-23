/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.datascheme.api.loader.des;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;

public interface SchemeNodeVisitor<E> {
    public VisitorResult preVisitNode(SchemeNode<E> var1);

    @Nullable
    public E visitRootIsSchemeNode(DesignDataScheme var1);

    @Nullable
    public E visitRootIsGroupNode(DesignDataGroup var1);

    @Nullable
    public E visitRootIsTableNode(DesignDataTable var1);

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Map<String, E> visitSchemeGroupNode(SchemeNode<E> var1, List<DG> var2, List<DS> var3);

    @Nullable
    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> Map<String, E> visitSchemeNode(SchemeNode<E> var1, List<DG> var2, List<DT> var3, List<DM> var4);

    @Nullable
    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<E> var1, List<DA> var2);

    @Nullable
    public <DG extends DesignDataGroup, DT extends DesignDataTable> Map<String, E> visitGroupNode(SchemeNode<E> var1, List<DG> var2, List<DT> var3);

    @Nullable
    public <DF extends DesignDataField> void visitTableNode(SchemeNode<E> var1, List<DF> var2);
}

