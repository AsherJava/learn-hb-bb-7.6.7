/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.cache.graph.TableNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.bql.interpret.var;

import com.jiuqi.bi.adhoc.cache.graph.TableNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.bql.interpret.var.BiAdaptVariable;

public class VarSYS_UNITKEY
extends BiAdaptVariable {
    private static final long serialVersionUID = -8528737346660165715L;

    public VarSYS_UNITKEY() {
        super("SYS_UNITKEY", "\u5f53\u524d\u5355\u4f4d\u4e1a\u52a1\u4e3b\u952e", 6);
    }

    @Override
    public void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        TableNode unitTable = this.getUnitTableNode(context);
        String tableName = unitTable.getTableName();
        String fieldName = tableName.equals("MD_ORG") ? "CODE" : "OBJECTCODE";
        buffer.append(tableName).append(".").append(fieldName);
    }
}

