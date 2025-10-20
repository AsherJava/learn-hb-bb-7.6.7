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
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
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
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.domain.ParameterDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserLinkedStaffFunction
extends ModelFunction {
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private AuthUserClient authUserClient;
    private static final long serialVersionUID = 1L;

    public GetUserLinkedStaffFunction() {
        this.parameters().add(new Parameter("UserName", 0, "\u7528\u6237\u540d", false));
        this.parameters().add(new Parameter("UnitCode", 0, "\u7ec4\u7ec7\u673a\u6784\u6807\u8bc6", false));
    }

    @Override
    public String addDescribe() {
        return "\u83b7\u53d6\u6307\u5b9a\u7528\u6237\u5728\u6307\u5b9a\u7ec4\u7ec7\u673a\u6784\u4e0b\u5173\u8054\u7684\u804c\u5458\u6807\u8bc6";
    }

    public String name() {
        return "GetUserLinkedStaff";
    }

    public String title() {
        return "\u83b7\u53d6\u7528\u6237\u5173\u8054\u7684\u804c\u5458";
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
        buffer.append("    ").append("UnitCode").append("\uff1a").append(DataType.toString((int)0)).append("\uff1b\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u8fd4\u56de\u503c\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append(DataType.toExpression((int)6)).append("\uff1a").append(DataType.toString((int)6)).append("\uff1b").append("\u8fd4\u56de\u804c\u5458\u7684\u6807\u8bc6").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("\u793a\u4f8b\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u573a\u666f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("\u53d6\u5f53\u524d\u62a5\u9500\u5355 \u5236\u5355\u4eba \u5173\u8054\u7684 \u804c\u5458 ").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u516c\u5f0f\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("GetUserLinkedStaff(FO_EXPENSEBILL[CREATEUSER],FO_EXPENSEBILL[UNITCODE])").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("\u8fd4\u56de\uff1a").append(FunctionUtils.LINE_SEPARATOR);
        buffer.append("    ").append("    ").append("sunfan");
        return buffer.toString();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6) + "\uff1b\u8fd4\u56de\u804c\u5458\u7684\u6807\u8bc6");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<ParameterDescription> parameterDescriptions = new ArrayList<ParameterDescription>();
        formulaDescription.setParameters(parameterDescriptions);
        ParameterDescription parameterDescription = new ParameterDescription();
        parameterDescription.setName("UserName");
        parameterDescription.setType(DataType.toString((int)0));
        parameterDescription.setDescription("\u7528\u6237\u540d\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570");
        parameterDescription.setRequired(true);
        ParameterDescription parameterDescription1 = new ParameterDescription();
        parameterDescription1.setName("UnitCode");
        parameterDescription1.setType(DataType.toString((int)0));
        parameterDescription1.setDescription("\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801\uff0c\u8be5\u53c2\u6570\u652f\u6301\u5185\u5d4c\u51fd\u6570");
        parameterDescription1.setRequired(true);
        parameterDescriptions.add(parameterDescription);
        parameterDescriptions.add(parameterDescription1);
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u53d6\u5f53\u524d\u62a5\u9500\u5355\u7684\u5236\u5355\u4eba\u5173\u8054\u7684\u804c\u5458\u3002");
        formulaExample.setFormula("GetUserLinkedStaff(FO_EXPENSEBILL[CREATEUSER],FO_EXPENSEBILL[UNITCODE]");
        formulaExample.setReturnValue("2000572||10001");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        IASTNode node0 = parameters.get(0);
        IASTNode node1 = parameters.get(1);
        Object node0Data = node0.evaluate(context);
        Object node1Data = node1.evaluate(context);
        if (node0Data == null || node1Data == null) {
            return null;
        }
        String userID = null;
        UserDTO user = new UserDTO();
        user.setUsername(node0Data.toString());
        UserDO userDO = this.authUserClient.get(user);
        if (userDO == null) {
            user.setUsername(null);
            user.setId(node0Data.toString());
            userDO = this.authUserClient.get(user);
            userID = userDO.getId();
        } else {
            userID = userDO.getId();
        }
        String unitCode = node1Data.toString();
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName("MD_STAFF");
        baseDataDTO.setUnitcode(unitCode);
        baseDataDTO.put("linkuser", (Object)userID);
        baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.SIMPLE);
        List result = null;
        PageVO pageVO = this.baseDataClient.list(baseDataDTO);
        if (pageVO != null && pageVO.getTotal() > 0) {
            result = pageVO.getRows();
        }
        if (result != null && result.size() > 0) {
            return ((BaseDataDO)result.get(0)).get((Object)"objectcode");
        }
        return null;
    }
}

