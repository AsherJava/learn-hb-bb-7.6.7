/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.FunctionException
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionNodeProvider
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 */
package com.jiuqi.nr.data.engine.analysis.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.FunctionException;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionNodeProvider;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisFunctionNode;
import com.jiuqi.nr.data.engine.analysis.parse.func.MEDIANFunction;
import com.jiuqi.nr.data.engine.analysis.parse.func.MODEFunction;
import com.jiuqi.nr.data.engine.analysis.parse.func.Val;
import com.jiuqi.nr.data.engine.analysis.parse.func.VarianceFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.COUNTFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.IStatistic;
import com.jiuqi.nr.data.engine.summary.parse.func.MAXFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.MINFunction;
import com.jiuqi.nr.data.engine.summary.parse.func.SUMFunction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AnalysisFunctionProvider
implements IFunctionProvider,
IFunctionNodeProvider {
    private Map<String, IFunction> functions = new HashMap<String, IFunction>();

    public AnalysisFunctionProvider() {
        this.addfunction((IFunction)new COUNTFunction());
        this.addfunction((IFunction)new MAXFunction());
        this.addfunction((IFunction)new MINFunction());
        this.addfunction((IFunction)new SUMFunction());
        this.addfunction((IFunction)new MEDIANFunction());
        this.addfunction((IFunction)new MODEFunction());
        this.addfunction((IFunction)new VarianceFunction());
        this.addfunction((IFunction)new Val());
        for (IFunction func : ReportFunctionProvider.GLOBAL_PROVIDER) {
            this.addfunction(func);
        }
    }

    public Iterator<IFunction> iterator() {
        return this.functions.values().iterator();
    }

    public IASTNode createNode(IContext context, Token token, IFunction function, List<IASTNode> parameters) throws FunctionException {
        if (function instanceof IStatistic) {
            return new AnalysisFunctionNode(token, function, parameters);
        }
        return new FunctionNode(token, function, parameters);
    }

    public IFunction find(IContext context, String funcName) throws FunctionException {
        return this.functions.get(funcName.toUpperCase());
    }

    public void addfunction(IFunction func) {
        String funcName = func.name().toUpperCase();
        if (this.functions.containsKey(funcName)) {
            return;
        }
        this.functions.put(funcName, func);
        if (func.aliases() != null) {
            for (String alias : func.aliases()) {
                String aliasUpperCase = alias.toUpperCase();
                if (this.functions.containsKey(aliasUpperCase)) continue;
                this.functions.put(aliasUpperCase, func);
            }
        }
    }
}

