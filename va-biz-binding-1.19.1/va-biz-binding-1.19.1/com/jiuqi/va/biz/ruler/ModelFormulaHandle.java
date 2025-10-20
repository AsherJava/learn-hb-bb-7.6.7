/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.va.formula.provider.ModelFunctionProvider
 */
package com.jiuqi.va.biz.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.ruler.ModelNodeProvider;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.formula.provider.ModelFunctionProvider;
import java.util.Map;

public class ModelFormulaHandle {
    private static final ReportFormulaParser parser = new ReportFormulaParser();
    private static final ModelFormulaHandle HANDLE;

    private ModelFormulaHandle() {
    }

    public static final ModelFormulaHandle getInstance() {
        return HANDLE;
    }

    private void customParse(ModelDataContext context, IExpression expression) throws SyntaxException {
        for (IASTNode node : expression) {
            if (!(node instanceof Equal)) continue;
            int leftType = node.getChild(0).getType((IContext)context);
            int rightType = node.getChild(1).getType((IContext)context);
            switch (leftType) {
                case 1: {
                    switch (rightType) {
                        case 0: 
                        case 1: 
                        case 10: {
                            return;
                        }
                    }
                    throw new SyntaxException(node.getToken(), "\u64cd\u4f5c\u6570\u7c7b\u578b\u4e0d\u5339\u914d\uff01");
                }
            }
            return;
        }
    }

    public IExpression parse(ModelDataContext context, String expresion, FormulaType formulaType) throws ParseException {
        IExpression iExpression = null;
        iExpression = FormulaType.EXECUTE.equals((Object)formulaType) ? parser.parseAssign(expresion, (IContext)context) : parser.parseEval(expresion, (IContext)context);
        try {
            this.customParse(context, iExpression);
        }
        catch (SyntaxException e) {
            throw new ParseException(e.getMessage());
        }
        return iExpression;
    }

    static {
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new ModelNodeProvider());
        for (Map.Entry functionProviderMap : ModelFunctionProvider.functionProviderMap.entrySet()) {
            parser.registerFunctionProvider((IFunctionProvider)functionProviderMap.getValue());
        }
        HANDLE = new ModelFormulaHandle();
    }
}

