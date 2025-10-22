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

public class IsTelephone
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 5602473202759110897L;

    public IsTelephone() {
        this.parameters().add(new Parameter("telnum", 6, "\u7535\u8bdd\u53f7\u7801"));
    }

    public String name() {
        return "IsTelephone";
    }

    public String title() {
        return "\u7535\u8bdd\u53f7\u7801\u6821\u9a8c";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u4fe1\u606f\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        String telephone = (String)list.get(0).evaluate(iContext);
        String isNumber = "[0-9]*";
        boolean isPhone = false;
        if (telephone == null || telephone.equals("")) {
            isPhone = false;
        } else {
            int indexOf = telephone.indexOf("-");
            if (indexOf == 0) {
                isPhone = false;
            } else if (indexOf > 0 && telephone.lastIndexOf("-") != telephone.length() - 1) {
                String newTelephone = telephone.replace("-", "");
                if (newTelephone.matches(isNumber) && newTelephone.length() >= 7) {
                    isPhone = true;
                }
            } else if (indexOf < 0 && telephone.matches(isNumber) && telephone.length() >= 7) {
                isPhone = true;
            }
        }
        return isPhone;
    }
}

