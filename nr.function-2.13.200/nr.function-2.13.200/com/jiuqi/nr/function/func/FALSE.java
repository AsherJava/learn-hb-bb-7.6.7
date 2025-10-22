/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import java.util.List;

public final class FALSE
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 425370342614348893L;

    public String category() {
        return "\u903b\u8f91\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return Boolean.FALSE;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) {
        return 1;
    }

    public String name() {
        return "FALSE";
    }

    public String title() {
        return "\u8fd4\u56de\u903b\u8f91\u503cFalse";
    }

    public boolean support(Language lang) {
        return lang != Language.JavaScript;
    }

    protected void toSQL(IContext context, List<IASTNode> children, StringBuilder buffer, ISQLInfo info) throws InterpretException {
    }
}

