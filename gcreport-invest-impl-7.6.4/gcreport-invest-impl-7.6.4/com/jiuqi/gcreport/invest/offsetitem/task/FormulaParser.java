/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil
 */
package com.jiuqi.gcreport.invest.offsetitem.task;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.invest.offsetitem.task.FormulaParserResult;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.nr.data.engine.util.ReportFormulaParseUtil;
import java.util.LinkedHashSet;
import java.util.Set;

public class FormulaParser {
    public static Set<FormulaParserResult> parseFormula(ExecutorContext context, String formula) throws Exception {
        ReportFormulaParseUtil reportFormulaParseUtil = (ReportFormulaParseUtil)SpringContextUtils.getBean(ReportFormulaParseUtil.class);
        IExpression iExpression = reportFormulaParseUtil.parseFormula(context, formula);
        LinkedHashSet<FormulaParserResult> result = new LinkedHashSet<FormulaParserResult>();
        FormulaParser.parseFormula((IASTNode)iExpression, result);
        return result;
    }

    private static String parseFormula(IASTNode node, Set<FormulaParserResult> result) {
        switch (node.getNodeType()) {
            case FUNCTION: {
                StringBuilder functionText = new StringBuilder(128);
                functionText.append(node.getToken().text()).append("(");
                for (int i = 0; i < node.childrenSize(); ++i) {
                    IASTNode subNode = node.getChild(i);
                    functionText.append(FormulaParser.parseFormula(subNode, result)).append(",");
                }
                if (node.childrenSize() > 0) {
                    functionText.setLength(functionText.length() - 1);
                }
                functionText.append(")");
                result.add(new FormulaParserResult(node, functionText.toString()));
                return "";
            }
            case DYNAMICDATA: {
                result.add(new FormulaParserResult(node, node.toString()));
                break;
            }
            case DATA: {
                return node.toString();
            }
        }
        for (int i = 0; i < node.childrenSize(); ++i) {
            IASTNode subNode = node.getChild(i);
            FormulaParser.parseFormula(subNode, result);
        }
        return "";
    }
}

