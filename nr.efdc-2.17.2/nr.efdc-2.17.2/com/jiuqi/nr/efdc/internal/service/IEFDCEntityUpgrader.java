/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 */
package com.jiuqi.nr.efdc.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.efdc.pojo.EfdcInfo;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import java.util.List;
import java.util.Map;

public interface IEFDCEntityUpgrader
extends IEntityUpgrader {
    public String getTargetKey(EntityViewData var1, DimensionValueSet var2);

    public String getPeriodCode(String var1, EntityViewData var2, DimensionValueSet var3);

    public Map<String, String> getDimMap(EntityViewData var1, DimensionValueSet var2, EfdcInfo var3);

    public IEntityTable getEntityTable(String var1, Map<String, String> var2);

    public List<IEntityRow> queryRowsByBizKeys(String var1, DimensionValueSet var2);

    public List<IEntityRow> queryAllRows(String var1);
}

