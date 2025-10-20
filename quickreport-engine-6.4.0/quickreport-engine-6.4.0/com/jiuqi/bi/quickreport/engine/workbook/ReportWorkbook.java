/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.ICellProvider
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.quickreport.engine.workbook;

import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.workbook.ReportWorksheet;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.ICellProvider;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.parser.IContext;

public abstract class ReportWorkbook
implements ICellProvider {
    public abstract IWorksheet activeWorksheet(IContext var1) throws CellExcpetion;

    public abstract IWorksheet find(IContext var1, String var2) throws CellExcpetion;

    public abstract void setActiveWorksheet(String var1) throws ReportExpressionException;

    public abstract int sheetSize();

    public abstract ReportWorksheet getSheet(int var1);
}

