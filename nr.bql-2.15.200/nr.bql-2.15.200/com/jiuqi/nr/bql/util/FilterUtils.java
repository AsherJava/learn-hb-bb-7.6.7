/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.text.Pos
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.function.Position
 */
package com.jiuqi.nr.bql.util;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.text.Pos;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.function.Position;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.parse.ParseInfo;
import java.util.Collections;
import java.util.List;

public class FilterUtils {
    public static String transFormula(QueryContext context, String formula, ParseInfo info) throws ParseException, InterpretException {
        context.getLogger().debug("\u8fc7\u6ee4\u516c\u5f0f\uff1a" + formula);
        IExpression expression = FilterUtils.parseFilterFormula(formula, context);
        for (IASTNode node : expression) {
            for (int i = 0; i < node.childrenSize(); ++i) {
                FunctionNode functionNode;
                IASTNode child = node.getChild(i);
                if (!(child instanceof FunctionNode) || !((functionNode = (FunctionNode)child).getDefine() instanceof Pos)) continue;
                List parameters = functionNode.getParameters();
                Collections.reverse(parameters);
                FunctionNode positionNode = new FunctionNode(null, (IFunction)new Position(), parameters);
                node.setChild(i, (IASTNode)positionNode);
            }
        }
        String newFormula = expression.interpret((IContext)context, Language.FORMULA, (Object)info);
        context.getLogger().debug("\u7ffb\u8bd1\u6210\u62a5\u8868\u516c\u5f0f\uff1a" + newFormula);
        return newFormula;
    }

    public static IExpression parseFilterFormula(String formula, QueryContext context) throws ParseException {
        IExpression expression = context.getParser().parseCond(formula, (IContext)context);
        return expression;
    }

    public static void appendFilter(StringBuilder rowFilter, String filter) {
        if (rowFilter.length() > 0) {
            rowFilter.append(" and ");
        }
        rowFilter.append("(").append(filter).append(")");
    }

    public static void printValueList(StringBuilder buff, List<String> values) {
        buff.append("{");
        if (values.size() > 0) {
            for (int i = 0; i < values.size(); ++i) {
                if (i >= 10) {
                    buff.append("...,");
                    break;
                }
                buff.append(values.get(i)).append(",");
            }
            buff.setLength(buff.length() - 1);
        }
        buff.append("}");
    }
}

