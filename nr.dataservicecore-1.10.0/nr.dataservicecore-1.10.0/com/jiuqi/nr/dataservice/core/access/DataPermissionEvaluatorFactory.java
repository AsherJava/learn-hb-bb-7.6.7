/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.access;

import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.Collection;

public interface DataPermissionEvaluatorFactory {
    public DataPermissionEvaluator createEvaluator(EvaluatorParam var1);

    public DataPermissionEvaluator createEvaluator(EvaluatorParam var1, DimensionCombination var2, Collection<String> var3);

    public DataPermissionEvaluator createEvaluator(EvaluatorParam var1, DimensionCollection var2, Collection<String> var3);
}

