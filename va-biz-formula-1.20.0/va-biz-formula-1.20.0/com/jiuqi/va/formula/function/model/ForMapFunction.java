/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.FormulaException;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.IFormulaContext;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ForMapFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public ForMapFunction() {
        this.parameters().add(new Parameter("expression", 0, "\u8868\u8fbe\u5f0f", false));
        this.parameters().add(new Parameter("dataSource", 0, "\u6570\u636e\u6e90", false));
    }

    @Override
    public String addDescribe() {
        return "\u904d\u5386\u6570\u7ec4\u5185\u5bb9\uff0c\u6839\u636e\u6bcf\u4e00\u9879\u5185\u5bb9\u8ba1\u7b97\u5f97\u5230\u4e00\u4e2a\u65b0\u7684\u6570\u7ec4\uff1b\u8ba1\u7b97\u8868\u8fbe\u5f0f\u4e2d\u8981\u4f7f\u7528\u6570\u636e\u6e90\u53c2\u6570\u7684\u6570\u636e\u65f6\uff0c\u4f7f\u7528[ARRAY[n]]\u683c\u5f0f\u83b7\u53d6\uff0cARRAY\u4e3a\u56fa\u5b9a\u524d\u7f00\uff0c\u6570\u5b57n\u4ee3\u8868\u7b2cn\u4e2a\u6570\u636e\u6e90\u53c2\u6570\uff0cn>=1\uff0c\u4f8b\u5982[ARRAY1]\u3001[ARRAY2]\u3001[ARRAY3]...";
    }

    public String name() {
        return "ForMap";
    }

    public String title() {
        return "\u6570\u7ec4\u904d\u5386\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 11;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IFormulaContext cxt = (IFormulaContext)context;
        IASTNode expression = parameters.get(0);
        IASTNode dataNode = parameters.get(1);
        int dataType = dataNode.getType(context);
        Object dataSource = dataNode.evaluate(context);
        ArrayList<Object> dataSources = new ArrayList<Object>();
        for (int i = 1; i < parameters.size(); ++i) {
            dataSources.add(parameters.get(i).evaluate(context));
        }
        HashSet<Object> resultDatas = new HashSet<Object>();
        if (dataSource instanceof ArrayData) {
            ArrayData datas = (ArrayData)dataSource;
            for (int index = 0; index < datas.size(); ++index) {
                this.executeItem(cxt, dataSources, expression, resultDatas, index);
            }
        } else {
            this.executeItem(cxt, dataSources, expression, resultDatas, -1);
        }
        return cxt.valueOf(Arrays.asList(resultDatas.toArray()), dataType);
    }

    private void executeItem(IFormulaContext cxt, List<Object> dataSources, IASTNode expression, Set<Object> resultDatas, int index) {
        for (int i = 0; i < dataSources.size(); ++i) {
            Object dataSource = dataSources.get(i);
            if (index != -1) {
                dataSource = ((ArrayData)dataSources.get(i)).get(index);
            }
            cxt.put("ARRAY" + (i + 1), dataSource);
        }
        try {
            Object evalData = expression.evaluate((IContext)cxt);
            if (evalData instanceof ArrayData) {
                ((ArrayData)evalData).forEach(data -> resultDatas.add(data));
            } else {
                resultDatas.add(evalData);
            }
        }
        catch (SyntaxException e) {
            throw new FormulaException(BizFormualI18nUtil.getMessage("va.bizformula.execute.formap.exception") + e.getMessage(), e);
        }
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
        buffer.append("    ").append("expression\uff1a").append(DataType.toString((int)0)).append("\uff1b \u8868\u8fbe\u5f0f").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("dataSource\uff1a").append(DataType.toString((int)0)).append("\uff1b \u6570\u636e\u6e90").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)11)).append("\uff1a").append(DataType.toString((int)11)).append(";\u8fd4\u56de\u751f\u6210\u7684\u6570\u7ec4\uff08\u6570\u7ec4\u4f1a\u53bb\u91cd\uff09").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u5b57\u7b26\u4e32\u6570\u7ec4\u4e2d\u6bcf\u4e2a\u5b57\u7b26\u4e32\u7684\u7b2c\u4e00\u4e2a\u5b57\u7b26\uff0c\u7ec4\u6210\u65b0\u6570\u7ec4\u8fd4\u56de").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("ForMap(Left([ARRAY1],1), ToArray('sss','yyy','zzz'))").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("['s','y','z']");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)11) + "\uff1a" + DataType.toString((int)11) + "\uff1b\u8fd4\u56de\u751f\u6210\u7684\u6570\u7ec4\uff08\u6570\u7ec4\u4f1a\u53bb\u91cd\uff09");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("expression");
        parameterDescription.setType(DataType.toString((int)0));
        parameterDescription.setDescription("\u8868\u8fbe\u5f0f");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("dataSource");
        parameterDescription1.setType(DataType.toString((int)6));
        parameterDescription1.setDescription("\u6570\u636e\u6e90");
        parameterDescription1.setRequired(true);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u53d6\u5b57\u7b26\u4e32\u6570\u7ec4\u4e2d\u6bcf\u4e2a\u5b57\u7b26\u4e32\u7684\u7b2c\u4e00\u4e2a\u5b57\u7b26\uff0c\u7ec4\u6210\u65b0\u6570\u7ec4\u8fd4\u56de");
        formulaExample.setFormula("ForMap(Left([ARRAY1],1), ToArray('sss','yyy','zzz'))");
        formulaExample.setReturnValue("['s','y','z']");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public boolean isInfiniteParameter() {
        return true;
    }
}

