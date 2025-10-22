/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.input.MeasureQueryInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeyReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.params.output.MeasureData;
import com.jiuqi.nr.jtable.util.EntityDataLoader;
import java.util.List;
import java.util.Map;

public interface IJtableEntityService {
    public EntityReturnInfo queryEntityData(EntityQueryByViewInfo var1);

    public EntityReturnInfo queryDimEntityData(EntityQueryByViewInfo var1);

    public List<List<String>> queryRelEntityDatas(List<String> var1, JtableContext var2);

    public EntityByKeyReturnInfo queryEntityDataByKey(EntityQueryByKeyInfo var1);

    public EntityByKeyReturnInfo queryEntityDataByKey(EntityQueryByKeyInfo var1, DimensionValueSet var2);

    public EntityByKeysReturnInfo queryEntityDataByKeys(EntityQueryByKeysInfo var1);

    public EntityByKeyReturnInfo queryEntityDataByKey(String var1, EntityDataLoader var2);

    public EntityDataLoader getEntityDataLoader(EntityQueryByKeyInfo var1);

    public EntityDataLoader getEntityDataLoader(EntityQueryByKeyInfo var1, boolean var2);

    public List<String> getAllEntityKey(String var1, Map<String, DimensionValue> var2, String var3);

    public List<String> getAllEntityKey(String var1, JtableContext var2, EntityViewDefine var3);

    public List<String> getAllDimEntityKey(String var1, Map<String, DimensionValue> var2, String var3);

    public int getMaxDepth(String var1, JtableContext var2);

    public EntityReturnInfo queryCustomPeriodData(EntityQueryByViewInfo var1);

    public List<MeasureData> queryMeasureData(MeasureQueryInfo var1);

    public MeasureData queryMeasureDataByCode(MeasureQueryInfo var1);
}

