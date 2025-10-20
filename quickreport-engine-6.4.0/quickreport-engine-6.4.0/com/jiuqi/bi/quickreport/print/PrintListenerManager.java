/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.print;

import com.jiuqi.bi.quickreport.print.IPrintListener;
import com.jiuqi.bi.quickreport.print.PrintException;

public class PrintListenerManager {
    private IPrintListener listener;
    private static final PrintListenerManager instance = new PrintListenerManager();

    public static PrintListenerManager getInstance() {
        return instance;
    }

    public void setListener(IPrintListener listener) {
        this.listener = listener;
    }

    public IPrintListener getListener() {
        return this.listener;
    }

    public void onBeforePrint(Object obj) throws PrintException {
        if (this.listener == null) {
            return;
        }
        this.listener.onBeforePrint(obj);
    }

    public void onAfterPrint(Object obj) throws PrintException {
        if (this.listener == null) {
            return;
        }
        this.listener.onAfterPrint(obj);
    }
}

