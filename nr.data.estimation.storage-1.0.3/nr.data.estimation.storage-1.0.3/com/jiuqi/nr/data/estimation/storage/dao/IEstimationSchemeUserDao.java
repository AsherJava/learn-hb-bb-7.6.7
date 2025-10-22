/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.estimation.storage.dao;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationScheme;
import java.util.List;

public interface IEstimationSchemeUserDao {
    public int insertEstimationScheme(EstimationScheme var1);

    public int updateEstimationScheme(EstimationScheme var1);

    public int removeEstimationScheme(String var1);

    public EstimationScheme findEstimationScheme(String var1, DimensionValueSet var2);

    public List<EstimationScheme> findEstimationSchemes(String var1, String var2, DimensionValueSet var3);
}

