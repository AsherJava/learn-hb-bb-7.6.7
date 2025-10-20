/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.expression;

import com.jiuqi.bi.dataset.restrict.RestrictionTag;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

public class RestrictTagNode
extends ASTNode
implements IASTNode {
    private static final long serialVersionUID = 1L;
    private String tag;

    public RestrictTagNode(Token token, String tag) {
        super(token);
        this.tag = tag;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.KEYWORD;
    }

    public boolean isAdjustable(String dsName) {
        return true;
    }

    public int getType(IContext context) throws SyntaxException {
        return 0;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        throw new SyntaxException("\u9650\u5b9a\u8bed\u6cd5\u8282\u70b9\u4e0d\u5177\u5907\u6267\u884c\u80fd\u529b");
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.tag);
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        this.toString(buffer);
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if (RestrictionTag.isMB(this.tag) || RestrictionTag.isALL(this.tag) || RestrictionTag.isPREV(this.tag) || RestrictionTag.isNEXT(this.tag) || RestrictionTag.isCURRENT(this.tag)) {
            // empty if block
        }
        if (RestrictionTag.isMB(this.tag)) {
            buffer.append("\u6210\u5458");
        } else if (RestrictionTag.isALL(this.tag)) {
            buffer.append("\u5168\u90e8");
        } else if (RestrictionTag.isPREV(this.tag)) {
            buffer.append("\u524d\u4e00\u4e2a");
        } else if (RestrictionTag.isNEXT(this.tag)) {
            buffer.append("\u4e0b\u4e00\u4e2a");
        } else if (RestrictionTag.isCURRENT(this.tag)) {
            buffer.append("\u5f53\u524d");
        }
    }

    public String getTag() {
        return this.tag;
    }
}

