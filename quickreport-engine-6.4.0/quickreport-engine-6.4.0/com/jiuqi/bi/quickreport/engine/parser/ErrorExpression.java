/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import java.text.Format;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ErrorExpression
implements IReportExpression {
    private final String expression;
    private final String errorMessage;

    public ErrorExpression(String expression, String errorMessage) {
        this.expression = expression;
        this.errorMessage = StringUtils.isEmpty((String)errorMessage) ? "\u672a\u77e5\u7684\u8bed\u6cd5\u9519\u8bef\u3002" : errorMessage;
    }

    @Override
    public int getDataType(IContext context) throws ReportExpressionException {
        return -1;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public IASTNode getRootNode() {
        return null;
    }

    @Override
    public Object evaluate(IContext context) throws ReportExpressionException {
        throw new ReportExpressionException(this.errorMessage);
    }

    @Override
    public boolean judge(IContext context) throws ReportExpressionException {
        throw new ReportExpressionException(this.errorMessage);
    }

    @Override
    public Format getFormat(IContext context) throws ReportExpressionException {
        return null;
    }

    @Override
    public String toFormula(IContext context) throws ReportExpressionException {
        return this.expression;
    }

    @Override
    public String toDSFormula(IContext context, String curDataSet) throws ReportExpressionException {
        throw new ReportExpressionException(this.errorMessage);
    }

    @Override
    public String toExcel(IContext context) throws ReportExpressionException {
        throw new ReportExpressionException(this.errorMessage);
    }

    @Override
    public String toExplain(IContext context) throws ReportExpressionException {
        throw new ReportExpressionException(this.errorMessage);
    }

    @Override
    public IReportExpression offset(IContext context, int colDelta, int rowDelta) throws ReportExpressionException {
        return new ErrorExpression(this.expression, this.errorMessage);
    }

    @Override
    public boolean renameSheet(IContext context, String oldName, String newName) throws ReportExpressionException {
        return false;
    }

    @Override
    public boolean rowsChanged(IContext context, String sheetName, int start, int count) throws ReportExpressionException {
        return false;
    }

    @Override
    public boolean colsChanged(IContext context, String sheetName, int start, int count) throws ReportExpressionException {
        return false;
    }

    @Override
    public List<IReportExpression> getANDList(IContext context) {
        ArrayList<IReportExpression> ands = new ArrayList<IReportExpression>();
        ands.add(this);
        return ands;
    }

    @Override
    public List<IReportExpression> getORList(IContext context) {
        ArrayList<IReportExpression> ors = new ArrayList<IReportExpression>();
        ors.add(this);
        return ors;
    }

    @Override
    public IReportExpression optimize(IContext context) throws ReportExpressionException {
        return this;
    }

    @Override
    public boolean isStatic(IContext context) {
        return false;
    }

    @Override
    public Iterator<IASTNode> iterator() {
        return new Iterator<IASTNode>(){

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public IASTNode next() {
                return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public boolean hasCellRef() {
        return false;
    }

    @Override
    public boolean hasMeasureRef() {
        return false;
    }

    @Override
    public boolean isFieldRef() {
        return false;
    }

    public String toString() {
        return this.expression;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }
}

