/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.FormulaParsedExp
 */
package com.jiuqi.nr.data.logic.internal.service;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.FormulaParsedExp;

public interface IFmlGraphAccessVerifier {
    public boolean access(FormulaParsedExp var1, DimensionCombination var2);
}

