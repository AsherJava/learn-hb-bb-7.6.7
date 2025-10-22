/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.dataentry.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.List;

public class GetUserSecretLevel
extends Function {
    public String name() {
        return "GetUserSecretLevel";
    }

    public String title() {
        return "\u83b7\u53d6\u7528\u6237\u5bc6\u7ea7\u7ea7\u522b";
    }

    public int getResultType(IContext iContext, List<IASTNode> list) throws SyntaxException {
        return 6;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext iContext, List<IASTNode> list) throws SyntaxException {
        String userSecurityLevel = NpContextHolder.getContext().getUser().getSecuritylevel();
        return userSecurityLevel;
    }
}

