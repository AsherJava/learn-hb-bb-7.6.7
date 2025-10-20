/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IAssignable
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IAssignable;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.formula.common.exception.FormulaException;
import com.jiuqi.va.formula.common.exception.ToFilterException;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.FieldNode;
import com.jiuqi.va.formula.intf.IFormulaContext;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.intf.TableFieldNode;
import com.jiuqi.va.formula.provider.FilterNodeProvider;
import com.jiuqi.va.formula.tofilter.FilterHandle;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GetRefTableDataConditionsFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public GetRefTableDataConditionsFunction() {
        this.parameters().add(new Parameter("mdValue", 0, "\u83b7\u53d6\u7684\u5f15\u7528\u6570\u636e\u5b57\u6bb5", false));
        this.parameters().add(new Parameter("condition", 1, "\u8fc7\u6ee4\u6761\u4ef6", false));
    }

    @Override
    public String addDescribe() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\u591a\u6761\u4ef6\u83b7\u53d6\u5f15\u7528\u6570\u636e\u3002\u7b2c\u4e00\u4e2a\u53c2\u6570\u8868\u793a\u9700\u8981\u8fdb\u884c\u8fc7\u6ee4\u7684\u5f15\u7528\u6570\u636e\u5b57\u6bb5\uff1b\u7b2c\u4e8c\u4e2a\u53c2\u6570\u8868\u793a\u8fc7\u6ee4\u6761\u4ef6\u96c6\u5408\uff0c\u591a\u4e2a\u8fc7\u6ee4\u6761\u4ef6\u7528\u2018&&\u2019\u53f7\u5206\u9694\u3002");
        return buffer.toString();
    }

    public String name() {
        return "GetRefTableDataConditions";
    }

    public String title() {
        return "\u591a\u6761\u4ef6\u83b7\u53d6\u5f15\u7528\u6570\u636e";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IFormulaContext cxt = (IFormulaContext)context;
        TableFieldNode node = (TableFieldNode)parameters.get(0);
        String tableName = node.getTableName();
        String fieldName = node.getFieldName().toLowerCase();
        int nodeDataType = parameters.get(0).getType(context);
        int refTableType = cxt.getRefTableType(tableName);
        try {
            if (refTableType == 1) {
                ArrayList results = new ArrayList();
                cxt.getRefDataByExpression(refTableType, tableName, this.transforFormula(parameters.get(1), cxt)).forEach(o -> {
                    Object stopflag = o.get("stopflag");
                    if (stopflag != null && (stopflag instanceof BigDecimal ? ((BigDecimal)stopflag).intValue() == 1 : (Integer)stopflag == 1)) {
                        return;
                    }
                    results.add(cxt.valueOf(o.get(fieldName), nodeDataType));
                });
                if (results.size() == 0) {
                    return cxt.valueOf(null, nodeDataType);
                }
                if (results.size() == 1) {
                    return cxt.valueOf(results.get(0), nodeDataType);
                }
                return cxt.valueOf(results, nodeDataType);
            }
        }
        catch (ToFilterException e) {
            throw new FormulaException(e);
        }
        Map<String, Map<String, Object>> allRefData = cxt.getAll(refTableType, tableName);
        Object result = null;
        try {
            result = this.filterResult(cxt, parameters, tableName, fieldName, allRefData, nodeDataType);
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
        return result;
    }

    private String transforFormula(IASTNode conditionNode, IFormulaContext context) throws ToFilterException {
        Expression currentExpression = new Expression(conditionNode.toString());
        currentExpression.setChild(0, conditionNode);
        Expression transforExpression = new Expression(conditionNode.toString());
        transforExpression.setChild(0, (IASTNode)conditionNode.clone());
        this.transforFormulaNode(conditionNode, transforExpression.getChild(0), (IASTNode)currentExpression, (IASTNode)transforExpression, context);
        return FilterHandle.toFilter(context, (IASTNode)transforExpression, new HashMap<String, Object>(){
            private static final long serialVersionUID = 1L;
            {
                this.put("transforModelNode", false);
            }
        });
    }

    private void transforFormulaNode(IASTNode currentNode, IASTNode transferNode, IASTNode pCurrentNode, IASTNode pTransferNode, IFormulaContext context) {
        if (currentNode instanceof TableFieldNode && currentNode instanceof IAssignable) {
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

    private Object filterResult(IFormulaContext context, List<IASTNode> parameters, String tableName, String fieldName, Map<String, Map<String, Object>> allRefData, int nodeDataType) throws SyntaxException, Exception {
        HashMap dataModelMap = new HashMap();
        ArrayList<Object> list = new ArrayList<Object>();
        for (Map.Entry<String, Map<String, Object>> entry : allRefData.entrySet()) {
            parameters.get(1).forEach(node -> {
                if (node instanceof FieldNode) {
                    FieldNode modelParamNode = (FieldNode)node;
                    try {
                        modelParamNode.setDataType(node.getType((IContext)context));
                    }
                    catch (SyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    String param = modelParamNode.getName();
                    DataModelDO dataModelDO = null;
                    if (dataModelMap.get(param) != null) {
                        dataModelDO = (DataModelDO)dataModelMap.get(param);
                    } else {
                        dataModelDO = context.findBaseDataDefine(tableName);
                        dataModelMap.put(param, dataModelDO);
                    }
                    context.put(param, ((Map)entry.getValue()).get(param.toLowerCase()));
                }
            });
            context.put(tableName, entry.getValue());
            if (!parameters.get(1).judge((IContext)context)) continue;
            list.add(context.valueOf(entry.getValue().get(fieldName), nodeDataType));
        }
        if (list.size() > 1) {
            return context.valueOf(list, nodeDataType);
        }
        if (list.size() == 1) {
            return context.valueOf(list.get(0), nodeDataType);
        }
        return context.valueOf(null, nodeDataType);
    }

    @Override
    public void toFilter(IContext context, List<IASTNode> params, StringBuilder buffer, String functionName, Object info) throws ToFilterException {
        buffer.append(this.name()).append('(');
        boolean flag = false;
        for (int i = 0; i < params.size(); ++i) {
            IASTNode p = params.get(i);
            if (flag) {
                buffer.append(',');
            } else {
                flag = true;
            }
            if (i == 0) {
                p.toString(buffer);
                continue;
            }
            FilterNodeProvider.get(p.getNodeType()).toFilter(context, p, buffer, info);
        }
        buffer.append(')');
    }

    @Override
    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("mdValue").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u83b7\u53d6\u7684\u5f15\u7528\u6570\u636e\u5b57\u6bb5").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("condition").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u8fc7\u6ee4\u6761\u4ef6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5f15\u7528\u6570\u636e\u8868MD_TABLE\u7684MEMO\u5b57\u6bb5(MEMO\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b\u5b57\u6bb5)\uff0c\u9700\u8981\u6ee1\u8db3MD_Table\u4e2d\u7684StdCode\u5728\"01\uff0c02\"\u8303\u56f4\u5185\u5e76\u4e14NAME\u4e0d\u7b49\u4e8e\u804c\u5458").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetRefTableDataConditions(MD_TABLE[MEMO],[STDCODE] IN ToArray(\"01\",\"02\") && [NAME] !=\"\u571f\u5730\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6));
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("mdValue");
        parameterDescription.setType(DataType.toString((int)0));
        parameterDescription.setDescription("\u83b7\u53d6\u7684\u5f15\u7528\u6570\u636e\u5b57\u6bb5");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("condition");
        parameterDescription1.setType(DataType.toString((int)6));
        parameterDescription1.setDescription("\u8fc7\u6ee4\u6761\u4ef6");
        parameterDescription1.setRequired(true);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u5f15\u7528\u6570\u636e\u8868MD_TABLE\u7684MEMO\u5b57\u6bb5(MEMO\u4e3a\u5b57\u7b26\u4e32\u7c7b\u578b\u5b57\u6bb5)\uff0c\u9700\u8981\u6ee1\u8db3MD_Table\u4e2d\u7684StdCode\u5728\"01\uff0c02\"\u8303\u56f4\u5185\u5e76\u4e14NAME\u4e0d\u7b49\u4e8e\u804c\u5458");
        formulaExample.setFormula("GetRefTableDataConditions(MD_TABLE[MEMO],[STDCODE] IN ToArray(\"01\",\"02\") && [NAME] !=\"\u571f\u5730\")");
        formulaExample.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6));
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    @Override
    protected void printParamDeclaration(StringBuilder buffer) {
        boolean flag = false;
        for (IParameter p : this.parameters()) {
            if (flag) {
                buffer.append(", ");
            } else {
                flag = true;
            }
            buffer.append(DataType.toExpression((int)p.dataType())).append(' ').append(p.name());
        }
        if (this.isInfiniteParameter() && !this.parameters().isEmpty()) {
            buffer.append(", ...");
        }
    }

    @Override
    public boolean autoOptimize() {
        return true;
    }
}

