/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.export;

import com.jiuqi.bi.quickreport.export.ExportException;

public interface IExportListener {
    public void onBeforeExport(Object var1) throws ExportException;

    public void onAfterExport(Object var1) throws ExportException;
}

