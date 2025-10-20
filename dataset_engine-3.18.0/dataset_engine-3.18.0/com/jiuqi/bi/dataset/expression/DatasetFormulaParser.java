/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.FormulaParser
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.expression.DSExpression;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.restrict.FilterOptimizer;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.dynamic.IDynamicNodeProvider;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.FormulaParser;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import java.util.Set;

public class DatasetFormulaParser {
    private FormulaParser parser = FormulaParser.getInstance();

    public int check(String formula, DSFormulaContext context) throws SyntaxException {
        if (formula == null || formula.length() == 0) {
            return -1;
        }
        IExpression expr = this.parser.parseEval(formula, (IContext)context);
        return expr.getType((IContext)context);
    }

    public DSExpression parseEval(String formula, DSFormulaContext context) throws ParseException {
        if (formula == null || formula.length() == 0) {
            return new DSExpression(formula, (IASTNode)DataNode.STRING_NULL);
        }
        IExpression expr = this.parser.parseEval(formula, (IContext)context);
        DSExpression dsExpr = new DSExpression(formula, expr.getChild(0));
        FilterOptimizer optimizer = new FilterOptimizer();
        try {
            optimizer.analysis(dsExpr, context);
        }
        catch (BIDataSetException e) {
            throw new ParseException(e.getMessage(), (Throwable)e);
        }
        dsExpr.setOptimizer(optimizer);
        return dsExpr;
    }

    public DSExpression parseCond(String formula, DSFormulaContext context) throws ParseException {
        DSExpression cond = this.parseEval(formula, context);
        try {
            if (cond.getType(context) != 1) {
                throw new ParseException("\u89e3\u6790\u6761\u4ef6\u516c\u5f0f\u5931\u8d25\uff0c\u8868\u8fbe\u5f0f\u4e0d\u662f\u6709\u6548\u7684\u903b\u8f91\u5224\u65ad\u8868\u8fbe\u5f0f\uff01");
            }
        }
        catch (SyntaxException e) {
            throw new ParseException((Throwable)e);
        }
        return cond;
    }

    public IExpression _parseEvalWithoutOptimize(String formula, DSFormulaContext context) throws ParseException {
        if (formula == null || formula.length() == 0) {
            return new DSExpression(formula, (IASTNode)DataNode.STRING_NULL);
        }
        return this.parser.parseEval(formula, (IContext)context);
    }

    public void registerFunctionProvider(IFunctionProvider provider) {
        this.parser.registerFunctionProvider(provider);
    }

    public void registerDynamicNodeProvider(IDynamicNodeProvider provider) {
        this.parser.registerDynamicNodeProvider(provider);
    }

    public Set<IFunction> allFunctions() {
        return this.parser.allFunctions();
    }
}

