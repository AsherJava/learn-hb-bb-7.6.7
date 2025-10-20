/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import java.text.Format;
import java.util.List;

public interface IReportExpression
extends Iterable<IASTNode>,
Cloneable {
    public int getDataType(IContext var1) throws ReportExpressionException;

    public String getErrorMessage();

    public IASTNode getRootNode();

    public Object evaluate(IContext var1) throws ReportExpressionException;

    public boolean judge(IContext var1) throws ReportExpressionException;

    public Format getFormat(IContext var1) throws ReportExpressionException;

    public String toFormula(IContext var1) throws ReportExpressionException;

    public String toDSFormula(IContext var1, String var2) throws ReportExpressionException;

    public String toExcel(IContext var1) throws ReportExpressionException;

    public String toExplain(IContext var1) throws ReportExpressionException;

    public IReportExpression offset(IContext var1, int var2, int var3) throws ReportExpressionException;

    public boolean renameSheet(IContext var1, String var2, String var3) throws ReportExpressionException;

    public boolean rowsChanged(IContext var1, String var2, int var3, int var4) throws ReportExpressionException;

    public boolean colsChanged(IContext var1, String var2, int var3, int var4) throws ReportExpressionException;

    public List<IReportExpression> getANDList(IContext var1);

    public List<IReportExpression> getORList(IContext var1);

    public IReportExpression optimize(IContext var1) throws ReportExpressionException;

    public boolean isStatic(IContext var1);

    public boolean hasCellRef();

    public boolean hasMeasureRef();

    public boolean isFieldRef();

    public Object clone();
}

