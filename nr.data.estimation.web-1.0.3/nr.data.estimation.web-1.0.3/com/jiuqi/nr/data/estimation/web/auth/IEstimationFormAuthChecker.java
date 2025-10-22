/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme
 *  com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate
 */
package com.jiuqi.nr.data.estimation.web.auth;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationScheme;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import java.util.List;

public interface IEstimationFormAuthChecker {
    public List<String> getCanWriteInputForms(IEstimationScheme var1);

    public List<String> getCanReadForms(IEstimationSchemeTemplate var1, DimensionValueSet var2);
}

