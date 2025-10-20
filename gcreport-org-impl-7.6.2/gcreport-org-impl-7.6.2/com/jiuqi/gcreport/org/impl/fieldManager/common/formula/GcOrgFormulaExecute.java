/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DefaultDatabase
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.FunctionNode
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.va.filter.bill.formula.OrgFieldNode
 *  com.jiuqi.va.filter.bill.formula.OrgFormulaContext
 *  com.jiuqi.va.formula.common.exception.ToSqlException
 *  com.jiuqi.va.formula.intf.FieldNode
 *  com.jiuqi.va.formula.provider.ModelFunctionProvider
 *  com.jiuqi.va.formula.tosql.ToSqlHandle
 */
package com.jiuqi.gcreport.org.impl.fieldManager.common.formula;

import com.jiuqi.bi.database.DefaultDatabase;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.FunctionNode;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.gcreport.org.impl.fieldManager.common.formula.GcOrgFormulaNodeProvider;
import com.jiuqi.va.filter.bill.formula.OrgFieldNode;
import com.jiuqi.va.filter.bill.formula.OrgFormulaContext;
import com.jiuqi.va.formula.common.exception.ToSqlException;
import com.jiuqi.va.formula.intf.FieldNode;
import com.jiuqi.va.formula.provider.ModelFunctionProvider;
import com.jiuqi.va.formula.tosql.ToSqlHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.springframework.util.StringUtils;

public class GcOrgFormulaExecute {
    private static final List<String> EXCLUDE_FUNCTIONS = Arrays.asList("TFV", "GetRefTableDataConditions", "GetRefTableDataField", "GetStaffRelatedUserRole", "GetUserLinkedStaff", "GetUserRoles");
    private static final GcOrgFormulaExecute EXECUTE = new GcOrgFormulaExecute();

    public static final GcOrgFormulaExecute getInstance() {
        return EXECUTE;
    }

    private GcOrgFormulaExecute() {
    }

    public IExpression transforFormula(String expression, OrgFormulaContext context) {
        if (!StringUtils.hasText(expression)) {
            return null;
        }
        Expression transforExpressionObj = new Expression("");
        try {
            IExpression expressionObj = EXECUTE.getExpression(context, expression);
            this.checkFormual((IASTNode)expressionObj, new Stack<FunctionNode>());
            this.transforFormulaNode((IASTNode)expressionObj, (IASTNode)transforExpressionObj, null, null, context);
            return (IExpression)transforExpressionObj.optimize((IContext)context, 2);
        }
        catch (Exception e) {
            throw new RuntimeException(String.format("\u6267\u884c\u516c\u5f0f%s\u51fa\u73b0\u5f02\u5e38:%s", expression, e.getMessage()), e);
        }
    }

    private void transforFormulaNode(IASTNode currentNode, IASTNode transferNode, IASTNode pCurrentNode, IASTNode pTransferNode, OrgFormulaContext context) {
        if (currentNode instanceof Expression) {
            IASTNode currentRoot = currentNode.getChild(0);
            IASTNode currentRootClone = (IASTNode)currentRoot.clone();
            Expression transferExpression = (Expression)transferNode;
            transferExpression.setChild(0, currentRootClone);
            this.transforFormulaNode(currentRoot, currentRootClone, currentNode, transferNode, context);
        } else if (this.canEvaluate(currentNode)) {
            Token token = currentNode.getToken();
            try {
                Object result = currentNode.evaluate((IContext)context);
                Token dataToken = new Token(String.valueOf(result), token.line(), token.column(), token.index());
                DataNode dataNode = new DataNode(dataToken, currentNode.getType((IContext)context), result);
                int childIndex = 0;
                for (int i = 0; i < pCurrentNode.childrenSize(); ++i) {
                    if (!pCurrentNode.getChild(i).equals(currentNode)) continue;
                    childIndex = i;
                }
                pTransferNode.setChild(childIndex, (IASTNode)dataNode);
            }
            catch (SyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            for (int i = 0; i < currentNode.childrenSize(); ++i) {
                IASTNode currentChildNode = currentNode.getChild(i);
                IASTNode currentChildNodeClone = (IASTNode)currentChildNode.clone();
                transferNode.setChild(i, currentChildNodeClone);
                this.transforFormulaNode(currentChildNode, currentChildNodeClone, currentNode, transferNode, context);
            }
        }
    }

    private boolean canEvaluate(IASTNode node) {
        if (node instanceof DataNode) {
            return false;
        }
        ArrayList fieldNodes = new ArrayList();
        try {
            node.forEach(action -> {
                if (action instanceof OrgFieldNode) {
                    fieldNodes.add(node);
                    return;
                }
            });
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return fieldNodes.isEmpty();
    }

    public boolean evaluate(OrgFormulaContext context, String expressionValue) {
        try {
            IExpression expression = EXECUTE.getExpression(context, expressionValue);
            this.checkFormual((IASTNode)expression, new Stack<FunctionNode>());
            return (Boolean)expression.evaluate((IContext)context);
        }
        catch (SyntaxException e) {
            throw new RuntimeException(String.format("\u6267\u884c\u516c\u5f0f%s\u51fa\u73b0\u5f02\u5e38:%s", expressionValue, e.getMessage()), e);
        }
    }

    private void checkFormual(IASTNode expression, Stack<FunctionNode> stackFunction) {
        try {
            for (IASTNode node : expression) {
                if (node instanceof FunctionNode) {
                    FunctionNode functionNode = (FunctionNode)node;
                    stackFunction.push(functionNode);
                    for (int i = 0; i < functionNode.childrenSize(); ++i) {
                        this.checkFormual(functionNode.getChild(i), stackFunction);
                    }
                    stackFunction.pop();
                }
                if (stackFunction.isEmpty() || !(node instanceof FieldNode) || !EXCLUDE_FUNCTIONS.contains(stackFunction.peek().getDefine().name())) continue;
                throw new RuntimeException(String.format("\u7ec4\u7ec7\u673a\u6784\u8fc7\u6ee4\u65f6\uff0c%s\u516c\u5f0f\u4e0d\u652f\u6301[FIELD]\u8bed\u6cd5", stackFunction.peek().getDefine().name()));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean judge(OrgFormulaContext context, IExpression expression) {
        try {
            return expression.judge((IContext)context);
        }
        catch (SyntaxException e) {
            throw new RuntimeException(String.format("\u6267\u884c\u516c\u5f0f%s\u51fa\u73b0\u5f02\u5e38:%s", expression.toString(), e.getMessage()), e);
        }
    }

    private IExpression getExpression(OrgFormulaContext context, String expression) throws ParseException {
        ReportFormulaParser parser = new ReportFormulaParser();
        parser.registerDynamicNodeProvider((IReportDynamicNodeProvider)new GcOrgFormulaNodeProvider());
        for (Map.Entry functionProviderMap : ModelFunctionProvider.functionProviderMap.entrySet()) {
            parser.registerFunctionProvider((IFunctionProvider)functionProviderMap.getValue());
        }
        return parser.parseCond(expression, (IContext)context);
    }

    public String toSQL(String expressionValue, OrgFormulaContext context) {
        StringBuilder sqlBuilder = new StringBuilder(32).append("(");
        try {
            IExpression expression = this.transforFormula(expressionValue, context);
            if (expression == null) {
                return "1=1";
            }
            sqlBuilder.append(ToSqlHandle.toSQL((IContext)context, (IASTNode)expression, (Object)new SQLInfoDescr((IDatabase)new DefaultDatabase(), true)));
            sqlBuilder.append(")");
            return sqlBuilder.toString();
        }
        catch (ToSqlException e) {
            throw new RuntimeException(e);
        }
    }
}

