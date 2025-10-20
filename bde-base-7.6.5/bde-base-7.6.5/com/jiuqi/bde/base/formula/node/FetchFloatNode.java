/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bde.base.formula.node;

import com.jiuqi.bde.base.formula.FetchFormulaContext;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

public class FetchFloatNode
extends DynamicNode {
    private static final long serialVersionUID = 4192482166340810926L;
    public static final String FN_FLOAT = "FLOAT";
    public final String queryTitle;

    public FetchFloatNode(IContext context, Token token, String queryTitle) {
        super(token);
        this.queryTitle = queryTitle;
    }

    public int getType(IContext context) throws SyntaxException {
        FetchFormulaContext formulaContext = (FetchFormulaContext)context;
        Object val = formulaContext.getFloatRowMap().get(this.queryTitle);
        if (val == null) {
            return 0;
        }
        if (val instanceof String) {
            return 6;
        }
        if (val instanceof Integer || val instanceof Double) {
            return 3;
        }
        return 6;
    }

    public Object evaluate(IContext context) {
        FetchFormulaContext formulaContext = (FetchFormulaContext)context;
        return formulaContext.getFloatRowMap().get(this.queryTitle);
    }

    public void toString(StringBuilder buffer) {
        buffer.append(String.format("%s[%s]", FN_FLOAT, this.queryTitle));
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        throw new InterpretException(String.format("\u672a\u652f\u6301SQL\u8f6c\u6362%s[%s]", FN_FLOAT, this.queryTitle));
    }
}

