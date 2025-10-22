/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.ICellProvider
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.nr.dafafill.formula;

import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.ICellProvider;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.dafafill.formula.DatafillFormulaContext;

public class DatafillCellProvider
implements ICellProvider {
    public IWorksheet activeWorksheet(IContext var1) throws CellExcpetion {
        DatafillFormulaContext context = (DatafillFormulaContext)var1;
        return context.getCurWorksheet();
    }

    public IWorksheet find(IContext var1, String sheetName) throws CellExcpetion {
        throw new CellExcpetion("\u4e0d\u652f\u6301\u8de8\u8868\u6a21\u5f0f\u3002");
    }
}

