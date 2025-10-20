/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.definition.internal.env;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

@Deprecated
public class EntityNode
extends ASTNode {
    private static final long serialVersionUID = 8509770242089658427L;
    private String taskCode;
    private String entityCode;

    public EntityNode(Token token, String taskCode, String entityCode) {
        super(token);
        this.taskCode = taskCode;
        this.entityCode = entityCode;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext context) throws SyntaxException {
        return 6;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return this.taskCode + "_" + this.entityCode;
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        this.toString(buffer);
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.entityCode);
    }
}

