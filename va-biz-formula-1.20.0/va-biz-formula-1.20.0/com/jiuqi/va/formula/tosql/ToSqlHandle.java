/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.formula.tosql;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.formula.common.exception.ToSqlException;

public class ToSqlHandle {
    public static final String toSQL(IContext context, IASTNode node, Object info) throws ToSqlException {
        try {
            StringBuilder builder = new StringBuilder(64);
            node.interpret(context, builder, Language.SQL, info);
            return builder.toString();
        }
        catch (InterpretException e) {
            throw new ToSqlException(String.format("\u516c\u5f0f\u8f6cSQL\u51fa\u73b0\u5f02\u5e38:%s", node.toString()), e);
        }
    }
}

