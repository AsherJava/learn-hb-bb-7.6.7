/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MuiltDataToArrayFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public MuiltDataToArrayFunction() {
        this.parameters().add(new Parameter("FieldName", 0, "\u591a\u9009\u6570\u636e\u5b57\u6bb5", true));
    }

    @Override
    public String addDescribe() {
        return "\u5c06\u591a\u9009\u7684\u6570\u636e\u8f6c\u6362\u4e3a\u6570\u7ec4";
    }

    public String name() {
        return "MuiltDataToArray";
    }

    public String title() {
        return "\u591a\u9009\u6570\u636e\u8f6c\u6570\u7ec4\u51fd\u6570";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 11;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        int baseType = parameters.get(0).getType(context);
        ArrayList list = new ArrayList();
        ArrayData result = null;
        try {
            if (parameters.get(0).evaluate(context) instanceof ArrayData) {
                result = (ArrayData)parameters.get(0).evaluate(context);
            }
        }
        catch (SyntaxException e) {
            throw new RuntimeException(BizFormualI18nUtil.getMessage("va.bizformula.muiltdatatoarray.exception"), e);
        }
        if (result != null) {
            result.forEach(value -> list.add(value));
        }
        return new ArrayData(baseType, list);
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
        buffer.append("    ").append("FieldName\uff1a").append(DataType.toString((int)0)).append("\uff1b \u5f85\u8f6c\u591a\u9009\u6570\u636e\u5b57\u6bb5\u540d").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)11)).append("\uff1a").append(DataType.toString((int)11)).append("\uff1b\u8fd4\u56de\u8f6c\u6362\u7684\u6570\u7ec4").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("MuiltDataToArray(deptcode)").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("[\"01\",\"02\",\"03\"]");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)11) + "\uff1a" + DataType.toString((int)11) + "\uff1b\u8fd4\u56de\u8f6c\u6362\u7684\u6570\u7ec4");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("FieldName", DataType.toString((int)0), "\u5f85\u8f6c\u591a\u9009\u6570\u636e\u5b57\u6bb5\u540d", false);
        parameterDescriptions.add(parameterDescription);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("", "MuiltDataToArray(deptcode)", "[\"01\",\"02\",\"03\"]");
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
}

