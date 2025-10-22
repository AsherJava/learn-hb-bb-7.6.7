/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.operator.IfThenElse
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.CellDataNode
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.util;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.operator.IfThenElse;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.util.ReportExpressionEvalUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportFormulaParseUtil {
    @Autowired
    private ReportExpressionEvalUtil evalUtil;

    public IExpression parseFormula(ExecutorContext context, String formula) throws Exception {
        QueryContext qContext = new QueryContext(context, null);
        return context.getCache().getFormulaParser(context).parseEval(formula, (IContext)qContext);
    }

    public List<IASTNode> parseFormulaNodes(ExecutorContext context, String formula, DimensionValueSet masterKeys) throws Exception {
        IExpression exp = this.parseFormula(context, formula);
        ArrayList<IASTNode> nodes = new ArrayList<IASTNode>();
        IASTNode root = exp.getChild(0);
        if (root instanceof IfThenElse && masterKeys != null) {
            IASTNode ifNode = root.getChild(0);
            boolean result = this.evalUtil.judge(context, ifNode, masterKeys);
            if (result) {
                this.parseFormulaNodes(context, root.getChild(1), nodes);
            } else if (root.childrenSize() == 3) {
                this.parseFormulaNodes(context, root.getChild(2), nodes);
            }
        } else if (root instanceof FunctionNode) {
            for (int index = 0; index < root.childrenSize(); ++index) {
                IASTNode child = root.getChild(index);
                this.parseFormulaNodes(context, child, nodes);
            }
        } else {
            this.parseFormulaNodes(context, root, nodes);
        }
        return nodes;
    }

    public void parseFormulaNodes(ExecutorContext context, IASTNode exp, List<IASTNode> nodes) throws Exception {
        if (exp instanceof FunctionNode) {
            nodes.add(exp);
        } else if (exp instanceof CellDataNode || exp instanceof DynamicDataNode) {
            nodes.add(exp);
        } else if (exp.childrenSize() > 0) {
            for (int index = 0; index < exp.childrenSize(); ++index) {
                IASTNode child = exp.getChild(index);
                this.parseFormulaNodes(context, child, nodes);
            }
        }
    }
}

