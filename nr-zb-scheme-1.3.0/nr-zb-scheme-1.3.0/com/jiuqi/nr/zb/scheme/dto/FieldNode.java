/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.zb.scheme.dto;

import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.zb.scheme.common.ZbDataType;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.dto.FieldContext;

public class FieldNode
extends ASTNode {
    public FieldNode(Token token) {
        super(token);
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext iContext) {
        ZbInfo zbInfo;
        ZbDataType dataFieldType;
        if (iContext instanceof FieldContext && (dataFieldType = (zbInfo = ((FieldContext)iContext).getDataField()).getDataType()) != null) {
            return dataFieldType.getValue();
        }
        return ZbDataType.STRING.getValue();
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

