/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.FunctionProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionNodeProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.quickreport.engine.parser.function.DS_AggregateByTree;
import com.jiuqi.bi.quickreport.engine.parser.function.DS_Count;
import com.jiuqi.bi.quickreport.engine.parser.function.DS_LookUp;
import com.jiuqi.bi.quickreport.engine.parser.function.DS_RemoveParentTree;
import com.jiuqi.bi.quickreport.engine.parser.function.DS_Sum;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionNode;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetProviderFunctionNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.FunctionProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionNodeProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.List;

public class DataSetFunctionProvider
extends FunctionProvider
implements IFunctionNodeProvider {
    public static final DataSetFunctionProvider DATASET_PROVIDER = new DataSetFunctionProvider();

    public IASTNode createNode(IContext context, Token token, IFunction function, List<IASTNode> parameters) throws FunctionException {
        if (function instanceof DS_RemoveParentTree || function instanceof DS_AggregateByTree) {
            return new DataSetProviderFunctionNode(token, function, parameters);
        }
        return new DataSetFunctionNode(token, function, parameters);
    }

    static {
        DATASET_PROVIDER.add((IFunction)new DS_LookUp());
        DATASET_PROVIDER.add((IFunction)new DS_Sum());
        DATASET_PROVIDER.add((IFunction)new DS_Count());
        DATASET_PROVIDER.add((IFunction)new DS_RemoveParentTree());
        DATASET_PROVIDER.add((IFunction)new DS_AggregateByTree());
    }
}

