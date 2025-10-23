/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.expression.filter.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.nr.expression.filter.exception.EntityFilterException;
import com.jiuqi.nr.expression.filter.parse.EntityNodeProvider;
import com.jiuqi.nr.expression.filter.parse.FilterExecuteContext;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityFormulaParser {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private EntityNodeProvider entityNodeProvider;
    private final Map<String, ExpCache> expressionCache = new HashMap<String, ExpCache>();

    public IExpression parseEntityFormula(FilterExecuteContext context, String expression, String categoryName) {
        ExpCache expCache = this.expressionCache.get(this.makeKey(expression, categoryName));
        if (expCache != null) {
            return expCache.expression;
        }
        IExpression exp = this.hardParse(context, expression, categoryName);
        expCache = new ExpCache(exp, true);
        this.expressionCache.put(this.makeKey(expression, categoryName), expCache);
        return exp;
    }

    public IExpression hardParse(FilterExecuteContext context, String expression, String categoryName) {
        IExpression parsedExpression = this.parseExpression(context, expression, categoryName);
        IExpression clonedExpression = (IExpression)parsedExpression.clone();
        return this.processNodes(clonedExpression, categoryName) ? clonedExpression : null;
    }

    private IExpression parseExpression(FilterExecuteContext context, String expression, String categoryName) {
        ReportFormulaParser formulaParser;
        ExecutorContext executorContext = context.getExecutorContext();
        try {
            formulaParser = executorContext.getCache().getFormulaParser(true);
        }
        catch (ParseException e) {
            throw new EntityFilterException("\u83b7\u53d6\u516c\u5f0f\u89e3\u6790\u5668\u9519\u8bef", e);
        }
        try {
            return formulaParser.parseCond(expression, (IContext)context);
        }
        catch (ParseException e) {
            throw new EntityFilterException("\u89e3\u6790\u516c\u5f0f\u9519\u8bef\uff1a" + categoryName + ", \u516c\u5f0f: " + expression, e);
        }
    }

    private boolean processNodes(IExpression expression, String categoryName) {
        HashMap<String, TableModelDefine> cacheTable = new HashMap<String, TableModelDefine>();
        boolean allEntityNodes = true;
        for (IASTNode node : expression) {
            if (this.processChildNodes(node, cacheTable, categoryName)) continue;
            allEntityNodes = false;
            break;
        }
        return allEntityNodes;
    }

    private boolean processChildNodes(IASTNode node, Map<String, TableModelDefine> cacheTable, String categoryName) {
        int childrenSize = node.childrenSize();
        for (int i = 0; i < childrenSize; ++i) {
            IASTNode child = node.getChild(i);
            if (!(child instanceof DynamicDataNode ? !this.handleDynamicDataNode(node, i, (DynamicDataNode)child, cacheTable, categoryName) : child instanceof VariableDataNode)) continue;
            return false;
        }
        return true;
    }

    private boolean handleDynamicDataNode(IASTNode node, int index, DynamicDataNode dataNode, Map<String, TableModelDefine> cacheTable, String categoryName) {
        QueryField queryField = dataNode.getQueryField();
        String tableName = queryField.getTableName();
        String fieldName = queryField.getFieldName();
        if (!tableName.equals(categoryName)) {
            return false;
        }
        TableModelDefine tableModelDefine = cacheTable.computeIfAbsent(tableName, k -> this.dataModelService.getTableModelDefineByCode(tableName));
        IASTNode newDataNode = this.entityNodeProvider.find(dataNode.getToken(), fieldName, tableModelDefine);
        node.setChild(index, newDataNode);
        return true;
    }

    private String makeKey(String expression, String category) {
        return category + "_" + expression;
    }

    class ExpCache {
        IExpression expression;
        boolean exist;

        public ExpCache(IExpression expression, boolean exist) {
            this.expression = expression;
            this.exist = exist;
        }
    }
}

