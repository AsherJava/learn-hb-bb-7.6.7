/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud.spi;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.spi.entity.IEntityTableWrapper;
import com.jiuqi.nr.datacrud.spi.entity.QueryMode;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public interface IEntityTableFactory {
    public IEntityTableWrapper getEntityTable(ParamRelation var1, IMetaData var2);

    public void reBuildEntityTable(ParamRelation var1, IMetaData var2, IEntityTableWrapper var3);

    public IEntityTableWrapper getEntityTable(ParamRelation var1, DimensionCombination var2, IMetaData var3);

    public void reBuildEntityTable(ParamRelation var1, DimensionCombination var2, IMetaData var3, IEntityTableWrapper var4);

    public IEntityTableWrapper getEntityTable(ParamRelation var1, DimensionCombination var2, IMetaData var3, int var4);

    public void reBuildEntityTable(ParamRelation var1, DimensionCombination var2, IMetaData var3, IEntityTableWrapper var4, int var5);

    public IEntityTableWrapper getEntityTable(ParamRelation var1, DimensionCombination var2, IMetaData var3, QueryMode var4);

    public void reBuildEntityTable(ParamRelation var1, DimensionCombination var2, IMetaData var3, IEntityTableWrapper var4, QueryMode var5);
}

