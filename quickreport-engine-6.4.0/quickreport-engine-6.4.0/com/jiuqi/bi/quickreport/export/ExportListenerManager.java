/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.export;

import com.jiuqi.bi.quickreport.export.ExportException;
import com.jiuqi.bi.quickreport.export.IExportListener;

public class ExportListenerManager {
    private IExportListener listener;
    private static final ExportListenerManager instance = new ExportListenerManager();

    public static ExportListenerManager getInstance() {
        return instance;
    }

    public void setListener(IExportListener listener) {
        this.listener = listener;
    }

    public IExportListener getListener() {
        return this.listener;
    }

    public void onBeforeExport(Object obj) throws ExportException {
        if (this.listener == null) {
            return;
        }
        this.listener.onBeforeExport(obj);
    }

    public void onAfterExport(Object obj) throws ExportException {
        if (this.listener == null) {
            return;
        }
        this.listener.onAfterExport(obj);
    }
}

