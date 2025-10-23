/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.service.impl.FieldContext;

public class FieldNode
extends ASTNode {
    public FieldNode(Token token) {
        super(token);
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext iContext) {
        DataField dataField;
        DataFieldType dataFieldType;
        if (iContext instanceof FieldContext && (dataFieldType = (dataField = ((FieldContext)iContext).getDataField()).getDataFieldType()) != null) {
            return dataFieldType.getValue();
        }
        return 6;
    }

    public Object evaluate(IContext iContext) {
        if (iContext instanceof FieldContext) {
            return ((FieldContext)iContext).getDataField();
        }
        return null;
    }

    public boolean isStatic(IContext iContext) {
        return false;
    }

    public void toString(StringBuilder stringBuilder) {
    }
}

