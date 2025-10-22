/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.FunctionNode
 */
package com.jiuqi.np.dataengine;

import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.List;

public interface IPreProcessingHandler {
    public String funcName();

    public void preProcessing(QueryContext var1, List<FunctionNode> var2);
}

