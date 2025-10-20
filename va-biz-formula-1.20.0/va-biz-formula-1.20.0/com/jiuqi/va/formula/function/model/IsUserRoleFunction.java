/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.SyntaxRuntimeException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.role.RoleDO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.feign.client.AuthRoleClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.SyntaxRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.role.RoleDO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class IsUserRoleFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;

    public IsUserRoleFunction() {
        this.parameters().add(new Parameter("UserName", 0, "\u7528\u6237\u540d", false));
        this.parameters().add(new Parameter("RoleCode", 0, "\u89d2\u8272\u6807\u8bc6", false));
    }

    @Override
    public String addDescribe() {
        return "\u68c0\u67e5\u6307\u5b9a\u7528\u6237\u662f\u5426\u5305\u542b\u6307\u5b9a\u89d2\u8272";
    }

    public String name() {
        return "IsUserRole";
    }

    public String title() {
        return "\u68c0\u67e5\u6307\u5b9a\u7528\u6237\u662f\u5426\u5305\u542b\u6307\u5b9a\u89d2\u8272";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
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
        buffer.append("    ").append("RoleCode").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u89d2\u8272\u6807\u8bc6\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)1)).append("\uff1a").append(DataType.toString((int)1)).append("\uff1b").append("\u5305\u542b\u89d2\u8272\u8fd4\u56detrue\uff0c\u5426\u5219\u8fd4\u56defalse").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u5224\u65ad\u53d6\u7528\u6237\u3010jiuqi\u3011\u662f\u5426\u5305\u6362\u89d2\u8272\u3010R1_1\u3011").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("IsUserRole(\"jiuqi\",\"R1_1\")").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("true");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)1) + "\uff1a" + DataType.toString((int)1));
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription("UserName", DataType.toString((int)0), "\u7528\u6237\u540d\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570", true);
        ParameterDescription parameterDescription1 = new ParameterDescription("RoleCode", DataType.toString((int)0), "\u89d2\u8272\u6807\u8bc6\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570", true);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample("\u5224\u65ad\u53d6\u7528\u6237\u3010jiuqi\u3011\u662f\u5426\u5305\u6362\u89d2\u8272\u3010R1_1\u3011", "IsUserRole(\"jiuqi\",\"R1_1\")", "true");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node0 = parameters.get(0);
        IASTNode node1 = parameters.get(1);
        Object node0Data = node0.evaluate(context);
        Object node1Data = node1.evaluate(context);
        String userName = (String)node0Data;
        String roleCode = (String)node1Data;
        UserDO user = new UserDO();
        user.setUsername(userName);
        List roles = ((AuthRoleClient)ApplicationContextRegister.getBean(AuthRoleClient.class)).listByUser(user);
        if (roles == null || roles.size() == 0) {
            return false;
        }
        Optional<RoleDO> option = roles.stream().filter(o -> o.getName().equals(roleCode)).findFirst();
        return option.isPresent();
    }

    @Override
    protected void toSQL(IContext context, List<IASTNode> parameters, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        try {
            buffer.append(this.evalute(context, parameters));
        }
        catch (SyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}

