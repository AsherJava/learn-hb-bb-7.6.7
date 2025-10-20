/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.formula.common.utils.FunctionUtils
 *  com.jiuqi.va.formula.domain.FormulaDescription
 *  com.jiuqi.va.formula.domain.FormulaExample
 *  com.jiuqi.va.formula.domain.ParameterDescription
 *  com.jiuqi.va.formula.intf.ModelFunction
 */
package com.jiuqi.va.bill.function;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class GetBillContextValFunction
extends ModelFunction {
    public String addDescribe() {
        return "\u83b7\u53d6\u5355\u636e\u4e0a\u4e0b\u6587\u6307\u5b9akey\u7684\u503c";
    }

    public String name() {
        return "GetBillContextVal";
    }

    public String title() {
        return "\u83b7\u53d6\u5355\u636e\u4e0a\u4e0b\u6587\u6307\u5b9akey\u7684\u503c";
    }

    public GetBillContextValFunction() {
        this.parameters().add(new Parameter("key", 6, "\u4e0a\u4e0b\u6587key", false));
    }

    public int getResultType(IContext iContext, List<IASTNode> list) {
        return 0;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node0 = parameters.get(0);
        Object node0Data = node0.evaluate(context);
        if (ObjectUtils.isEmpty(node0Data)) {
            return null;
        }
        BillContextImpl billContext = (BillContextImpl)((ModelDataContext)context).model.getContext();
        Object contextValue = billContext.getContextValue("X--" + node0Data);
        if (contextValue == null) {
            contextValue = billContext.getContextValue(node0Data.toString());
        }
        return contextValue;
    }

    public String toDescription() {
        StringBuilder buffer = new StringBuilder(64);
        buffer.append("\u51fd\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ");
        this.toDeclaration(buffer);
        buffer.append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u63cf\u8ff0\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(this.addDescribe()).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u53c2\u6570\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("key").append("\uff1a").append(DataType.toString((int)3)).append("\uff1b\u4e0a\u4e0b\u6587\u4e2d\u7684key").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)0)).append("\uff1a").append(DataType.toString((int)0)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\u5355\u636e\u4e0a\u4e0b\u6587\u6307\u5b9akey\u7684\u503c\u3002").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u5f53\u524d\u5355\u636e\u4e0a\u4e0b\u6587\u4e2d\u6b63\u5728\u6267\u884c\u52a8\u4f5c\u7684id\u3002 ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetBillContextVal(\"actionId\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append(DataType.toString((int)0));
        return buffer.toString();
    }

    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)0) + "\uff1a" + DataType.toString((int)0));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("key", DataType.toString((int)3), "\u4e0a\u4e0b\u6587\u4e2d\u7684key", Boolean.valueOf(true));
        parameterDescriptions.add(parameterDescription);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u83b7\u53d6\u5f53\u524d\u5355\u636e\u4e0a\u4e0b\u6587\u4e2d\u6b63\u5728\u6267\u884c\u52a8\u4f5c\u7684id\u3002", "GetBillContextVal(\"actionId\")", DataType.toString((int)0));
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

