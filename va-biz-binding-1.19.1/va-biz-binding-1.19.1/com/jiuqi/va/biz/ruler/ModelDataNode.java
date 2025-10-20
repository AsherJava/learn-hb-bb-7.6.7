/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.biz.ruler;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.ruler.ModelNode;

public abstract class ModelDataNode
extends ASTNode
implements IASTNode {
    private static final long serialVersionUID = -6319581285048742658L;
    private ModelNode modelNode;

    public ModelDataNode(Token token, ModelNode modelNode) {
        super(token);
        this.modelNode = modelNode;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DATA;
    }

    public int getType(IContext context) throws SyntaxException {
        return ((ModelDataContext)context).getType(this.modelNode.fieldDefine.getValueType());
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public ModelNode getModelNode() {
        return this.modelNode;
    }
}

