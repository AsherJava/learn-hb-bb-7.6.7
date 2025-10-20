/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.SyntaxRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IParameter
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.feign.client.AuthRoleClient
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.SyntaxRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.IFormulaContext;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GetUserRolesFuntion
extends ModelFunction {
    @Autowired
    private AuthRoleClient authRoleClient;
    private static final long serialVersionUID = 1L;

    public GetUserRolesFuntion() {
        this.parameters().add(new Parameter("UserName", 0, "\u7528\u6237\u540d", false));
    }

    @Override
    public String addDescribe() {
        return "\u83b7\u53d6\u6307\u5b9a\u7528\u6237\u7684\u89d2\u8272\u6807\u8bc6";
    }

    public String name() {
        return "GetUserRoles";
    }

    public String title() {
        return "\u83b7\u53d6\u6307\u5b9a\u7528\u6237\u7684\u89d2\u8272";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
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

    public void toDeclaration(StringBuilder buffer) {
        try {
            int retType = this.getResultType(null, null);
            buffer.append(DataType.toExpression((int)retType)).append(' ').append(this.name()).append('(');
        }
        catch (SyntaxException e) {
            throw new SyntaxRuntimeException((Throwable)e);
        }
        this.printParamDeclaration(buffer);
        buffer.append(");");
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
        buffer.append("    ").append("UserName").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u7528\u6237\u540d\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b").append("\u8fd4\u56de\u89d2\u8272\u6807\u8bc6\uff0c\u591a\u4e2a\u89d2\u8272\u4ee5\u9017\u53f7\u5206\u9694").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u53d6\u7528\u6237\u3010jiuqi\u3011\u7684\u89d2\u8272").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetUserRoles(\"jiuqi\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("R1_1,R1_2");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6) + "\uff1b\u8fd4\u56de\u89d2\u8272\u6807\u8bc6\uff0c\u591a\u4e2a\u89d2\u8272\u4ee5\u9017\u53f7\u5206\u9694");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("UserName");
        parameterDescription.setType(DataType.toString((int)0));
        parameterDescription.setDescription("\u7528\u6237\u540d\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570");
        parameterDescription.setRequired(true);
        parameterDescriptions.add(parameterDescription);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u83b7\u53d6\u53d6\u7528\u6237\u3010jiuqi\u3011\u7684\u89d2\u8272");
        formulaExample.setFormula("GetUserRoles(\"jiuqi\")");
        formulaExample.setReturnValue("R1_1,R1_2");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node0 = parameters.get(0);
        Object node0Data = node0.evaluate(context);
        if (node0Data == null) {
            return null;
        }
        String userName = node0Data.toString();
        UserDO user = new UserDO();
        user.setUsername(userName);
        user.setTenantName(((IFormulaContext)context).getTenantName());
        List roles = this.authRoleClient.listByUser(user);
        if (roles == null || roles.size() == 0) {
            return null;
        }
        return StringUtils.collectionToCommaDelimitedString(roles.stream().map(role -> role.getName()).collect(Collectors.toList()));
    }
}

