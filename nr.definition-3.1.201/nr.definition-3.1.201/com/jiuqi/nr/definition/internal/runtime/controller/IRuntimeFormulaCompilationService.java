/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.nr.definition.facade.ICmpFmlProvider;
import java.util.List;

public interface IRuntimeFormulaCompilationService {
    public List<IParsedExpression> cmpFmlOnceAllPar(ICmpFmlProvider var1);

    public List<IParsedExpression> cmpFmlOnceFormPar(ICmpFmlProvider var1);
}

