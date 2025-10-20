/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.parameter.engine.IParameterEnv;

public final class DSContextFactory {
    @Deprecated
    public static DSContext create(DSModel dsModel, String userGuid, IParameterEnv parameterEnv) {
        return new DSContext(dsModel, userGuid, parameterEnv);
    }

    public static DSContext create(DSModel dsModel, String userGuid, com.jiuqi.nvwa.framework.parameter.IParameterEnv parameterEnv) {
        return new DSContext(dsModel, userGuid, parameterEnv);
    }
}

