/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.unit.treeimpl.dataio;

import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;

public interface DataExportWorker {
    public Workbook writeExcel() throws Exception;

    public void writeExcel(OutputStream var1) throws Exception;

    public void writeExcel(HttpServletResponse var1, String var2) throws Exception;
}

