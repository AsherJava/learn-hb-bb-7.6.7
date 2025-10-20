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
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.feign.client.AuthRoleClient
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.SyntaxRuntimeException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IParameter;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.feign.client.AuthRoleClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import com.jiuqi.va.formula.utils.BizFormualI18nUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GetStaffRelatedUserRoleFunction
extends ModelFunction {
    private static final long serialVersionUID = 1L;
    @Autowired
    private AuthRoleClient authRoleClient;
    @Autowired
    private BaseDataClient baseDataClient;

    public GetStaffRelatedUserRoleFunction() {
        this.parameters().add(new Parameter("UnitCode", 0, "\u673a\u6784\u4ee3\u7801", false));
        this.parameters().add(new Parameter("StaffCode", 0, "\u804c\u5458\u4ee3\u7801", false));
        this.parameters().add(new Parameter("BizUnitCode", 0, "\u4e1a\u52a1\u7ec4\u7ec7", true));
    }

    @Override
    public String addDescribe() {
        return "\u83b7\u53d6\u804c\u5458\u5bf9\u5e94\u7528\u6237\u7684\u89d2\u8272\u6807\u8bc6";
    }

    public String name() {
        return "GetStaffRelatedUserRole";
    }

    public String title() {
        return "\u83b7\u53d6\u804c\u5458\u5bf9\u5e94\u7528\u6237\u7684\u89d2\u8272";
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
        buffer.append("    ").append("UnitCode").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u804c\u5458\u6240\u5c5e\u673a\u6784\u4ee3\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("StaffCode").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u804c\u5458\u4ee3\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("BizUnitCode").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u4e1a\u52a1\u7ec4\u7ec7\u4ee3\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b").append("\u8fd4\u56de\u89d2\u8272\u6807\u8bc6\uff0c\u591a\u4e2a\u89d2\u8272\u4ee5\u9017\u53f7\u5206\u9694").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784[A0001]\uff0c\u804c\u5458[jiuqi]\u5bf9\u5e94\u7528\u6237\u7684\u89d2\u8272\u6807\u8bc6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetStaffRelatedUserRole(\"A0001\",\"JIUQI\")").append(FunctionUtils.LINE_SEPARATOR);
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
        parameterDescription.setName("UnitCode");
        parameterDescription.setType(DataType.toString((int)0));
        parameterDescription.setDescription("\u804c\u5458\u6240\u5c5e\u673a\u6784\u4ee3\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("StaffCode");
        parameterDescription1.setType(DataType.toString((int)0));
        parameterDescription1.setDescription("\u804c\u5458\u4ee3\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570");
        parameterDescription1.setRequired(true);
        ParameterDescription parameterDescription2 = new ParameterDescription();
        parameterDescription2.setName("BizUnitCode");
        parameterDescription2.setType(DataType.toString((int)0));
        parameterDescription2.setDescription("\u4e1a\u52a1\u7ec4\u7ec7\u4ee3\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570");
        parameterDescription2.setRequired(false);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        parameterDescriptions.add(parameterDescription2);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784[A0001]\uff0c\u804c\u5458[jiuqi]\u5bf9\u5e94\u7528\u6237\u7684\u89d2\u8272\u6807\u8bc6");
        formulaExample.setFormula("GetStaffRelatedUserRole(\"A0001\",\"JIUQI\")");
        formulaExample.setReturnValue("R1_1,R1_2");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        List roles;
        IASTNode node0 = parameters.get(0);
        IASTNode node1 = parameters.get(1);
        Object node0Data = node0.evaluate(context);
        Object node1Data = node1.evaluate(context);
        String unitCode = (String)node0Data;
        String staffCode = (String)node1Data;
        if (!StringUtils.hasText(staffCode)) {
            return null;
        }
        BaseDataDO staffDO = null;
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName("MD_STAFF");
        baseDataDTO.setCode(staffCode);
        if (!StringUtils.isEmpty(unitCode)) {
            baseDataDTO.setUnitcode(unitCode);
        }
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO result = this.baseDataClient.list(baseDataDTO);
        if (result != null && result.getTotal() > 0) {
            if (!StringUtils.hasText(unitCode) && result.getTotal() > 1) {
                throw new RuntimeException(BizFormualI18nUtil.getMessage("va.bizformula.staff.code.more.than.one", new Object[]{staffCode}));
            }
            staffDO = (BaseDataDO)result.getRows().get(0);
        }
        if (staffDO == null) {
            return null;
        }
        String linkuser = (String)staffDO.get((Object)"linkuser");
        if (!StringUtils.hasText(linkuser)) {
            return null;
        }
        String bizUintCode = null;
        if (parameters.size() > 2) {
            bizUintCode = String.valueOf(parameters.get(2).evaluate(context));
        }
        UserDO user = new UserDO();
        user.setId(linkuser);
        if (StringUtils.hasText(bizUintCode)) {
            user.addExtInfo("contextOrg", (Object)bizUintCode);
        }
        if ((roles = this.authRoleClient.listByUser(user)) == null || roles.size() == 0) {
            return null;
        }
        return StringUtils.collectionToCommaDelimitedString(roles.stream().map(role -> role.getName()).collect(Collectors.toList()));
    }
}

