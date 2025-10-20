/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.dataset.AggrMeasureItem;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.AggrFunction;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.dataset.restrict.CondKey;
import com.jiuqi.bi.dataset.restrict.FilterOptimizer;
import com.jiuqi.bi.dataset.stat.StatConfig;
import com.jiuqi.bi.dataset.stat.StatProcessor;
import com.jiuqi.bi.dataset.stat.info.StatInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DSFunctionNode
extends FunctionNode {
    private static final long serialVersionUID = 1L;
    private FilterOptimizer optimizer;
    private Map<CondKey, Object> cache = new HashMap<CondKey, Object>();
    private StatInfo statInfo;

    public DSFunctionNode(Token token, IFunction function, List<IASTNode> params) {
        super(token, function, params);
    }

    public List<IASTNode> getParams() {
        ArrayList<IASTNode> params = new ArrayList<IASTNode>(this.childrenSize());
        for (int i = 0; i < this.childrenSize(); ++i) {
            params.add(this.getChild(i));
        }
        return params;
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.function.name()).append("(");
        for (int i = 0; i < this.parameters.size(); ++i) {
            if (i != 0) {
                buffer.append(",");
            }
            ((IASTNode)this.parameters.get(i)).interpret(context, buffer, Language.FORMULA, info);
        }
        buffer.append(")");
    }

    public void setOptimizer(FilterOptimizer optimizer) {
        this.optimizer = optimizer;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        DSFormulaContext dsCxt = (DSFormulaContext)context;
        DSFunction func = (DSFunction)this.getDefine();
        if (func.isNeedOptimize() && this.optimizer != null) {
            try {
                CondKey key = this.optimizer.calcKey((IASTNode)this, dsCxt);
                Object result = this.cache.get(key);
                if (result == null) {
                    BIDataSetImpl filterDs = this.optimizer.evalFilter((IASTNode)this, dsCxt);
                    if (func instanceof AggrFunction) {
                        AggrFunction aggrFunc = (AggrFunction)func;
                        if (this.statInfo == null) {
                            List<AggrMeasureItem> msItems = aggrFunc.getAggrMeasureItem(dsCxt, this.parameters);
                            List<Integer> dimList = this.optimizer.getRestrictFields((IASTNode)this);
                            StatProcessor statProcessor = new StatProcessor(filterDs, new StatConfig(false, true));
                            this.statInfo = statProcessor.createStatInfo(dimList, msItems);
                        }
                        result = aggrFunc.evalute(dsCxt, filterDs, this.statInfo, this.parameters);
                        this.cache.put(key, result);
                    } else {
                        result = func.evalute(context, this.parameters, filterDs);
                    }
                }
                return result;
            }
            catch (BIDataSetException e) {
                throw new SyntaxException(e.getMessage(), (Throwable)e);
            }
        }
        return func.evalute(context, this.parameters);
    }
}

