/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.domain.user.UserDTO
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.AuthUserClient
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.domain.user.UserDTO;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.AuthUserClient;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.domain.FormulaExample;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetBelongOrgCodeFunction
extends ModelFunction {
    private static final long serialVersionUID = -2442142080922729603L;
    @Autowired
    private AuthUserClient authUserClient;

    @Override
    public String addDescribe() {
        return "\u83b7\u53d6\u5f53\u524d\u7528\u6237\u6240\u5c5e\u7ec4\u7ec7\u673a\u6784";
    }

    public String name() {
        return "GetBelongOrgCode";
    }

    public String title() {
        return "\u83b7\u53d6\u5f53\u524d\u7528\u6237\u6240\u5c5e\u7ec4\u7ec7\u673a\u6784";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        UserLoginDTO user = ShiroUtil.getUser();
        if (user == null) {
            return "";
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        UserDO userDO = this.authUserClient.get(userDTO);
        return userDO.getUnitcode();
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = new FormulaDescription();
        formulaDescription.setName(this.name());
        formulaDescription.setTitle(this.title());
        formulaDescription.setReturnValue(DataType.toExpression((int)6) + "\uff1a" + DataType.toString((int)6) + "\uff1b\u8fd4\u56de\u5f53\u524d\u7528\u6237\u6240\u5c5e\u7ec4\u7ec7");
        formulaDescription.setDescription(this.addDescribe());
        ArrayList<FormulaExample> formulaExamples = new ArrayList<FormulaExample>();
        FormulaExample formulaExample = new FormulaExample();
        formulaExample.setScenario("\u83b7\u53d6\u5f53\u524d\u7528\u6237\u6240\u5c5e\u7ec4\u7ec7");
        formulaExample.setFormula("GetBelongOrgCode()");
        formulaExample.setReturnValue("TEST");
        formulaExamples.add(formulaExample);
        formulaDescription.setExamples(formulaExamples);
        return formulaDescription;
    }
}

