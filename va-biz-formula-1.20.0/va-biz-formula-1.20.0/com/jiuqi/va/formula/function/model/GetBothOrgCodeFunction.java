/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.domain.user.UserOrgDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.domain.user.UserOrgDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetBothOrgCodeFunction
extends ModelFunction {
    private static final long serialVersionUID = -2442142080922729603L;
    private static final String GET_BOTH_ORG_CODE = "\u83b7\u53d6\u5f53\u524d\u7528\u6237\u6240\u6709\u76d1\u7ba1\u7ec4\u7ec7";
    @Autowired
    private AuthUserClient authUserClient;

    @Override
    public String addDescribe() {
        return GET_BOTH_ORG_CODE;
    }

    public String name() {
        return "GetBothOrgCode";
    }

    public String title() {
        return GET_BOTH_ORG_CODE;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 11;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        UserLoginDTO user = ShiroUtil.getUser();
        if (user == null) {
            return new ArrayData(6, new ArrayList());
        }
        UserOrgDTO userDTO = new UserOrgDTO();
        userDTO.setUsername(user.getUsername());
        UserOrgDTO userOrgDTO = this.authUserClient.listBothOrg(userDTO);
        List orgCodes = userOrgDTO.getOrgCodes();
        if (orgCodes == null) {
            return new ArrayData(6, new ArrayList());
        }
        return new ArrayData(6, (Collection)orgCodes);
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)11) + "\uff1a" + DataType.toString((int)11) + "\uff1b\u8fd4\u56de\u5f53\u524d\u7528\u6237\u6240\u6709\u76d1\u7ba1\u7ec4\u7ec7");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario(GET_BOTH_ORG_CODE);
        formulaExample.setFormula("GetBothOrgCode()");
        formulaExample.setReturnValue("['TEST2','TEST3']");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

