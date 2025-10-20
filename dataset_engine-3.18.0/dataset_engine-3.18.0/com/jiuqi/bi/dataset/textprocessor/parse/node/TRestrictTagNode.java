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
package com.jiuqi.bi.dataset.textprocessor.parse.node;

import com.jiuqi.bi.dataset.expression.RestrictTagNode;
import com.jiuqi.bi.dataset.textprocessor.parse.node.IAdjustable;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

public class TRestrictTagNode
extends ASTNode
implements IASTNode,
IAdjustable {
    private String tag;

    public TRestrictTagNode(Token token, String tag) {
        super(token);
        this.tag = tag;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.KEYWORD;
    }

    @Override
    public boolean isAdjustable(String dsName) {
        return true;
    }

    @Override
    public IASTNode adjust() {
        return new RestrictTagNode(this.token, this.getTag());
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

    public String getTag() {
        return this.tag;
    }
}

