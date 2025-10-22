/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.bql.interpret;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

public class BiAdaptDimNode
extends ASTNode {
    private static final long serialVersionUID = 8428675663561568518L;
    private String tableCode;
    private String fieldCode;
    private int fieldDataType;

    public BiAdaptDimNode(String tableCode, String fieldCode, int dataType) {
        super(null);
        this.tableCode = tableCode;
        this.fieldCode = fieldCode;
        this.fieldDataType = dataType;
        if (this.fieldDataType == 5) {
            this.fieldDataType = 2;
        } else if (this.fieldDataType == 4 || this.fieldDataType == 3) {
            this.fieldDataType = 3;
        }
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return null;
    }

    public ASTNodeType getNodeType() {
        return ASTNodeType.DYNAMICDATA;
    }

    public int getType(IContext context) throws SyntaxException {
        return this.fieldDataType;
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.tableCode).append(".").append(this.fieldCode);
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void toString(StringBuilder buffer) {
        buffer.append(this.tableCode).append(".").append(this.fieldCode);
    }
}

