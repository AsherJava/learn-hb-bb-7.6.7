/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.logic.internal.service;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.data.logic.internal.service.IFmlGraphAccessVerifier;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public interface IFmlGraphDataCollector {
    public List<IParsedExpression> collect(String var1, DimensionCombination var2, List<String> var3);

    public List<IParsedExpression> collect(String var1, DimensionCombination var2, List<String> var3, IFmlGraphAccessVerifier ... var4);
}

