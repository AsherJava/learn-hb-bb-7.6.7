/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.storage.dao;

import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationSchemeBase;
import java.util.List;

public interface IEstimationSchemeTemplateDao {
    public List<EstimationSchemeBase> findAllTemplate(String var1);

    public EstimationSchemeBase findTemplate(String var1, String var2);

    public EstimationSchemeBase findEstimationScheme(String var1);

    public List<EstimationSchemeBase> findEstimationSchemes(String var1, String var2);

    public int insertEstimationScheme(EstimationSchemeBase var1);

    public int updateEstimationScheme(EstimationSchemeBase var1);

    public int deleteEstimationScheme(String var1);
}

