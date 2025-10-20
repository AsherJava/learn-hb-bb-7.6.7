/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 */
package com.jiuqi.bi.quickreport.engine.context;

import com.jiuqi.bi.quickreport.engine.context.filter.IFilterDescriptor;
import com.jiuqi.bi.quickreport.engine.result.TraceInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import java.util.List;

@FunctionalInterface
public interface IEvalTracer {
    public TraceInfo evalNode(IASTNode var1, List<IFilterDescriptor> var2, Object var3) throws SyntaxException;
}

