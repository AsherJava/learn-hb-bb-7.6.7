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
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.var.Variable
 */
package com.jiuqi.nr.bql.interpret;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.bql.interpret.var.BiAdaptVariable;

public class BiAdaptVariableNode
extends ASTNode {
    private static final long serialVersionUID = 1616788229271121740L;
    private BiAdaptVariable variable;

    public BiAdaptVariableNode(Token token, BiAdaptVariable variable) {
        super(token);
        this.variable = variable;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext context) throws SyntaxException {
        return this.variable.getDataType();
    }

    public Object evaluate(IContext context) throws SyntaxException {
        try {
            QueryContext qContext = (QueryContext)context;
            Object value = qContext.getVarValue(this.variable.getVarName());
            if (value == null) {
                value = this.variable.getVarValue(context);
            }
            return value;
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.variable.getVarName());
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        this.variable.toFormula(context, buffer, info);
    }

    public Variable getVariable() {
        return this.variable;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.variable == null ? 0 : this.variable.getVarName().hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((Object)((Object)this)).getClass() != obj.getClass()) {
            return false;
        }
        BiAdaptVariableNode other = (BiAdaptVariableNode)((Object)obj);
        return !(this.variable == null ? other.variable != null : !this.variable.getVarName().equals(other.variable.getVarName()));
    }
}

