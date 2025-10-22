/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.query.result.ColumnInfo
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.data.logic.internal.cache;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.query.result.ColumnInfo;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.data.logic.internal.cache.MemoryDataContext;

public class MemoryDataNode
extends ASTNode {
    private static final long serialVersionUID = 9012002035913354226L;
    private Column<ColumnInfo> column;

    public MemoryDataNode(Column<ColumnInfo> column) {
        super(null);
        this.column = column;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DATA;
    }

    public int getType(IContext context) throws SyntaxException {
        int dataType = this.column.getDataType();
        if (dataType == 5) {
            dataType = 3;
        }
        return dataType;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        MemoryDataContext memoryDataContext = (MemoryDataContext)context;
        return memoryDataContext.getValue(this.column.getName());
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder stringBuilder) {
    }
}

