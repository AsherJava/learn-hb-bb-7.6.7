/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTIterator
 *  com.jiuqi.bi.syntax.ast.AdjustException
 *  com.jiuqi.bi.syntax.ast.AdjustorFactory
 *  com.jiuqi.bi.syntax.ast.Expression
 *  com.jiuqi.bi.syntax.ast.IASTAdjustor
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.cell.ICellNode
 *  com.jiuqi.bi.syntax.cell.IRegionNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.util.ASTHelper
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.quickreport.engine.parser.IReportExpression;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFieldNode;
import com.jiuqi.bi.quickreport.engine.parser.dataset.DSFormulaInfo;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionNode;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTIterator;
import com.jiuqi.bi.syntax.ast.AdjustException;
import com.jiuqi.bi.syntax.ast.AdjustorFactory;
import com.jiuqi.bi.syntax.ast.Expression;
import com.jiuqi.bi.syntax.ast.IASTAdjustor;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.cell.ICellNode;
import com.jiuqi.bi.syntax.cell.IRegionNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.util.ASTHelper;
import java.text.Format;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ReportExpression
implements IReportExpression {
    private final IExpression expression;

    public ReportExpression(IExpression expression) {
        this.expression = expression;
    }

    public ReportExpression(IASTNode expression) {
        this((IExpression)new Expression(null, expression));
    }

    @Override
    public int getDataType(IContext context) throws ReportExpressionException {
        try {
            return this.expression.getType(context);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public IASTNode getRootNode() {
        return this.expression.getChild(0);
    }

    @Override
    public Object evaluate(IContext context) throws ReportExpressionException {
        try {
            return this.expression.evaluate(context);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
    }

    @Override
    public boolean judge(IContext context) throws ReportExpressionException {
        try {
            return this.expression.judge(context);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
    }

    @Override
    public Format getFormat(IContext context) throws ReportExpressionException {
        try {
            IDataFormator formator = this.expression.getDataFormator(context);
            return formator == null ? null : formator.getFormator(context);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
    }

    @Override
    public String toFormula(IContext context) throws ReportExpressionException {
        try {
            return this.expression.interpret(context, Language.FORMULA, null);
        }
        catch (InterpretException e) {
            throw new ReportExpressionException(e);
        }
    }

    @Override
    public String toDSFormula(IContext context, String curDataSet) throws ReportExpressionException {
        try {
            return this.expression.interpret(context, Language.FORMULA, (Object)new DSFormulaInfo(curDataSet));
        }
        catch (InterpretException e) {
            throw new ReportExpressionException(e);
        }
    }

    @Override
    public String toExcel(IContext context) throws ReportExpressionException {
        try {
            return this.expression.interpret(context, Language.EXCEL, null);
        }
        catch (InterpretException e) {
            throw new ReportExpressionException(e);
        }
    }

    @Override
    public String toExplain(IContext context) throws ReportExpressionException {
        try {
            return this.expression.interpret(context, Language.EXPLAIN, null);
        }
        catch (InterpretException e) {
            throw new ReportExpressionException(e);
        }
    }

    @Override
    public IReportExpression offset(IContext context, int colDelta, int rowDelta) throws ReportExpressionException {
        IExpression newExpr;
        try {
            newExpr = (IExpression)this.expression.offset(colDelta, rowDelta);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
        return new ReportExpression(newExpr);
    }

    @Override
    public boolean renameSheet(IContext context, String oldName, String newName) throws ReportExpressionException {
        IASTAdjustor rename = AdjustorFactory.renameSheet((String)oldName, (String)newName);
        try {
            return this.expression.adjust(rename) > 0;
        }
        catch (AdjustException e) {
            throw new ReportExpressionException(e);
        }
    }

    @Override
    public boolean rowsChanged(IContext context, String sheetName, int start, int count) throws ReportExpressionException {
        int changed;
        IASTAdjustor rowChanged;
        if (count > 0) {
            rowChanged = AdjustorFactory.insertRows((String)sheetName, (int)start, (int)count);
        } else if (count < 0) {
            rowChanged = AdjustorFactory.deleteRows((String)sheetName, (int)start, (int)(-count));
        } else {
            return false;
        }
        try {
            changed = this.expression.adjust(rowChanged);
        }
        catch (AdjustException e) {
            throw new ReportExpressionException(e);
        }
        return changed > 0;
    }

    @Override
    public boolean colsChanged(IContext context, String sheetName, int start, int count) throws ReportExpressionException {
        int changed;
        IASTAdjustor colChanged;
        if (count > 0) {
            colChanged = AdjustorFactory.insertCols((String)sheetName, (int)start, (int)count);
        } else if (count < 0) {
            colChanged = AdjustorFactory.deleteCols((String)sheetName, (int)start, (int)(-count));
        } else {
            return false;
        }
        try {
            changed = this.expression.adjust(colChanged);
        }
        catch (AdjustException e) {
            throw new ReportExpressionException(e);
        }
        return changed > 0;
    }

    @Override
    public List<IReportExpression> getANDList(IContext context) {
        List nodes = ASTHelper.getANDList((IASTNode)this.expression.getChild(0));
        return this.toExpressions(nodes);
    }

    @Override
    public List<IReportExpression> getORList(IContext context) {
        List nodes = ASTHelper.getORList((IASTNode)this.expression.getChild(0));
        return this.toExpressions(nodes);
    }

    private List<IReportExpression> toExpressions(List<IASTNode> nodes) {
        ArrayList<IReportExpression> expressions = new ArrayList<IReportExpression>();
        if (nodes.size() == 1) {
            expressions.add(this);
        } else {
            for (IASTNode node : nodes) {
                expressions.add(new ReportExpression(node));
            }
        }
        return expressions;
    }

    @Override
    public IReportExpression optimize(IContext context) throws ReportExpressionException {
        IExpression newExpr;
        try {
            newExpr = (IExpression)this.expression.optimize(context, 3);
        }
        catch (SyntaxException e) {
            throw new ReportExpressionException(e);
        }
        return newExpr == this.expression ? this : new ReportExpression(newExpr);
    }

    @Override
    public boolean isStatic(IContext context) {
        return this.expression.isStatic(context);
    }

    @Override
    public Iterator<IASTNode> iterator() {
        return new ASTIterator(this.expression){

            protected boolean needChildren(IASTNode node) {
                if (node instanceof DataSetFunctionNode) {
                    return false;
                }
                return super.needChildren(node);
            }
        };
    }

    @Override
    public boolean hasCellRef() {
        for (IASTNode node : this.expression) {
            if (!(node instanceof ICellNode) && !(node instanceof IRegionNode)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasMeasureRef() {
        for (IASTNode node : this.expression) {
            DSFieldNode fieldNode;
            if (!(node instanceof DSFieldNode) || (fieldNode = (DSFieldNode)node).getField().isDimention()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isFieldRef() {
        IASTNode root = this.expression.getChild(0);
        return root instanceof DSFieldNode && ((DSFieldNode)root).getRestrictions().isEmpty();
    }

    public String toString() {
        return this.expression.toString();
    }

    @Override
    public Object clone() {
        return new ReportExpression((IExpression)this.expression.clone());
    }
}

