/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.va.formula.function.model;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.va.formula.common.utils.FunctionUtils;
import com.jiuqi.va.formula.domain.FormulaDescription;
import com.jiuqi.va.formula.intf.ModelFunction;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class CheckSocialCreditCodeLegitimateFunction
extends ModelFunction {
    private static final int[] WEIGHT = new int[]{1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};
    private static final String BASE_CODE_STRING = "0123456789ABCDEFGHJKLMNPQRTUWXY";
    private static final String REGEX = "[0123456789ABCDEFGHJKLMNPQRTUWXY]{18}";
    private static final char[] BASE_CODE_ARRAY = "0123456789ABCDEFGHJKLMNPQRTUWXY".toCharArray();
    private static final List<Character> BASE_CODES = new ArrayList<Character>();

    public CheckSocialCreditCodeLegitimateFunction() {
        this.parameters().add(new Parameter("socialCreditCode", 6, "\u793e\u4f1a\u4fe1\u7528\u4ee3\u7801"));
    }

    @Override
    public String addDescribe() {
        return "\u6821\u9a8c\u5f53\u524d\u793e\u4f1a\u4fe1\u7528\u4ee3\u7801\u662f\u5426\u5408\u6cd5";
    }

    public String name() {
        return "CheckSocialCreditCodeLegitimate";
    }

    public String title() {
        return "\u793e\u4f1a\u4fe1\u7528\u4ee3\u7801\u6821\u9a8c\u51fd\u6570";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u6a21\u578b\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        String value = (String)list.get(0).evaluate(iContext);
        if (StringUtils.isEmpty((String)value)) {
            return true;
        }
        if (value.length() != 18 || !Pattern.matches(REGEX, value)) {
            return false;
        }
        char[] businessCodeArray = value.toCharArray();
        char check = businessCodeArray[17];
        int sum = 0;
        for (int i = 0; i < 17; ++i) {
            char key = businessCodeArray[i];
            sum += BASE_CODES.indexOf(Character.valueOf(key)) * WEIGHT[i];
        }
        int checkCode = 31 - sum % 31;
        return check == BASE_CODE_ARRAY[checkCode % 31];
    }

    @Override
    public FormulaDescription toFormulaDescription() {
        FormulaDescription formulaDescription = FunctionUtils.handleFormula((IFunction)this);
        formulaDescription.setDescription(this.addDescribe());
        return formulaDescription;
    }

    static {
        for (char c : BASE_CODE_ARRAY) {
            BASE_CODES.add(Character.valueOf(c));
        }
    }
}

