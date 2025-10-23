/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellNode
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.task.form.formio.formula;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellNode;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.IContext;

public class ReportCellNode
extends CellNode {
    private final ReportCellData cellData;

    public ReportCellNode(Token token, IWorksheet workSheet, Position position, int options, ReportCellData cellData) {
        super(token, workSheet, position, options);
        this.cellData = cellData;
    }

    public int getType(IContext context) throws SyntaxException {
        return this.cellData.getType();
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return null;
    }

    public void toString(StringBuilder buffer) {
        super.toString(buffer);
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append(this.cellData.getFormCode());
        buffer.append("[");
        buffer.append(this.cellData.getPos());
        buffer.append("]");
    }

    public static class ReportCellData {
        private final String formCode;
        private final String pos;
        private final int type;

        public ReportCellData(String formCode, String pos, int type) {
            this.formCode = formCode;
            this.pos = pos;
            this.type = type;
        }

        public String getPos() {
            return this.pos;
        }

        public int getType() {
            return this.type;
        }

        public String getFormCode() {
            return this.formCode;
        }
    }
}

