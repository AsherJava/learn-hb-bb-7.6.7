/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public class IsMobilePhone
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 5999307196627978591L;

    public IsMobilePhone() {
        this.parameters().add(new Parameter("mobilenum", 6, "\u624b\u673a\u53f7\u7801"));
    }

    public static boolean matchRegex(String s, String regexp) {
        boolean match = false;
        if (s == null) {
            return false;
        }
        match = s.matches(regexp);
        return match;
    }

    public String name() {
        return "IsMobilePhone";
    }

    public String title() {
        return "\u624b\u673a\u53f7\u7801\u6821\u9a8c";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u4fe1\u606f\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        String p1 = "^((\\+86)?(1(3[0-9]|4[5-9]|5[0,1,2,3,5,6,7,8,9]|6[2,5,6,7]|7[0-8]|8[0-9]|9[1,3,5,8,9]))\\d{8})$";
        Object p0 = list.get(0).evaluate(iContext);
        return IsMobilePhone.matchRegex((String)p0, p1);
    }
}

