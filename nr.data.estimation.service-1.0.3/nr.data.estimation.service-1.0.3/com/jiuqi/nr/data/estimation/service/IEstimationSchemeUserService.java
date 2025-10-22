/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 */
package com.jiuqi.nr.data.estimation.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import java.util.List;
import java.util.Map;

public interface IEstimationSchemeUserService {
    public IEstimationScheme findEstimationScheme(String var1, DimensionValueSet var2);

    public List<IEstimationScheme> findEstimationSchemes(String var1, DimensionValueSet var2);

    public IEstimationScheme newEstimationScheme(String var1, List<String> var2, Map<String, DimensionValue> var3);

    public IEstimationScheme oldEstimationScheme(String var1, Map<String, DimensionValue> var2);
}

