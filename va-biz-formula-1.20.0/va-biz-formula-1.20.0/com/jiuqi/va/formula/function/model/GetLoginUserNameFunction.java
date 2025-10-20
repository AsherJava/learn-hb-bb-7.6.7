/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetLoginUserNameFunction
extends ModelFunction {
    private static final long serialVersionUID = 37871443915158193L;

    public String name() {
        return "GetLoginUserName";
    }

    public String title() {
        return "\u83b7\u53d6\u5f53\u524d\u767b\u5f55\u7528\u6237\u540d";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        UserLoginDTO user;
        try {
            user = ShiroUtil.getUser();
        }
        catch (Exception e) {
            user = null;
        }
        if (user != null) {
            return user.getUsername();
        }
        return null;
    }

    @Override
    public String addDescribe() {
        return "\u83b7\u53d6\u5f53\u524d\u767b\u5f55\u7528\u6237\u540d";
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
        buffer.append("    ").append("\u65e0").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5728\u7528\u6237\"jiuqi\"\u767b\u5f55\u7684\u60c5\u51b5\u4e0b\uff0c\u5728\u5355\u636e\u4e2d\u60f3\u8981\u83b7\u53d6\u5f53\u524d\u767b\u5f55\u7528\u6237\u7684\u540d\u5b57").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetLoginUserName()").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("jiuqi");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u5728\u7528\u6237\"jiuqi\"\u767b\u5f55\u7684\u60c5\u51b5\u4e0b\uff0c\u5728\u5355\u636e\u4e2d\u60f3\u8981\u83b7\u53d6\u5f53\u524d\u767b\u5f55\u7528\u6237\u7684\u540d\u5b57");
        formulaExample.setFormula("GetLoginUserName()");
        formulaExample.setReturnValue("jiuqi");
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

