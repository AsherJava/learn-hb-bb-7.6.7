/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.datacrud.impl.service;

import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.spi.IEnumFillNode;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.List;

public interface EntityDataService {
    public IEntityQuery getEntityQuery();

    public ExecutorContext getExecutorContext();

    public IEntityTable getEntityTable(String var1, DimensionCombination var2, String var3);

    public List<List<String>> fillEnum(RegionRelation var1, DimensionCombination var2);

    public List<List<String>> fillEnum(RegionRelation var1, DimensionCombination var2, List<IEnumFillNode> var3);
}

