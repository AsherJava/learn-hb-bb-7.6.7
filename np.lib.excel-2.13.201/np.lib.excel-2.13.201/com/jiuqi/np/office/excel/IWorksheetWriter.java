/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel;

import com.jiuqi.np.office.excel.ExcelWritingException;
import com.jiuqi.np.office.excel.ICellOperaterEx;

public interface IWorksheetWriter {
    public void setTitle(String var1);

    public void setAutoAdjust(boolean var1);

    public void setAddTitle(boolean var1);

    public void writeWorkSheet() throws ExcelWritingException;

    public void addCellOperater(ICellOperaterEx var1);
}

