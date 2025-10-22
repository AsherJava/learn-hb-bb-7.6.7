/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.dataio;

import org.apache.poi.ss.usermodel.Workbook;

public interface IExcelWriteWorker {
    public String getFileName();

    public Workbook createWorkbook();
}

