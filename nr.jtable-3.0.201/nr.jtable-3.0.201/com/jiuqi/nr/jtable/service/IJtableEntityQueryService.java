/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

public interface IJtableEntityQueryService
extends IEntityUpgrader {
    public IEntityTable queryEntity(EntityViewData var1, List<ReferRelation> var2, JtableContext var3, boolean var4, boolean var5, boolean var6, boolean var7);

    public IEntityTable queryEntityWithDimVal(EntityViewData var1, List<ReferRelation> var2, JtableContext var3, boolean var4, boolean var5, DimensionValueSet var6);

    public IEntityTable queryEntity(EntityViewData var1, List<ReferRelation> var2, JtableContext var3, boolean var4, boolean var5, boolean var6, boolean var7, boolean var8, boolean var9);

    public IEntityTable queryDimEntity(EntityViewData var1, List<ReferRelation> var2, JtableContext var3, boolean var4, boolean var5, boolean var6);

    public IEntityTable querySimplEntityTable(EntityViewData var1);
}

