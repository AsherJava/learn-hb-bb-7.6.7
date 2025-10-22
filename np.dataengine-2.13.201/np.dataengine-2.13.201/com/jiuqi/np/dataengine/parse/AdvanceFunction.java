/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.Function
 */
package com.jiuqi.np.dataengine.parse;

import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.parse.IReportFunction;

public abstract class AdvanceFunction
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -5930111331412469197L;

    @Override
    public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.CUSTOM;
    }
}

