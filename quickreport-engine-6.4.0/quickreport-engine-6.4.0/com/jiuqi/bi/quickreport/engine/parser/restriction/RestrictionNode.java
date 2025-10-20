/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser.restriction;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;

public final class RestrictionNode
extends DynamicNode {
    private static final long serialVersionUID = -3670696710573156640L;
    private final String tag;

    public RestrictionNode(Token token, String tag) {
        super(token);
        this.tag = tag;
    }

    public int getType(IContext context) throws SyntaxException {
        return 0;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        throw new SyntaxException(this.getToken(), "\u65e0\u6cd5\u8ba1\u7b97\u7684\u8bed\u6cd5\u8282\u70b9\u3002");
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.tag);
    }

    public boolean support(Language lang) {
        return lang == Language.FORMULA || lang == Language.EXPLAIN;
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.tag);
    }

    public String getTag() {
        return this.tag;
    }
}

