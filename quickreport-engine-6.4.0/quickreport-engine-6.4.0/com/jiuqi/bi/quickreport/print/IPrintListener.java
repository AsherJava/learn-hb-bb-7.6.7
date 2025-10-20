/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.print;

import com.jiuqi.bi.quickreport.print.PrintException;

public interface IPrintListener {
    public void onBeforePrint(Object var1) throws PrintException;

    public void onAfterPrint(Object var1) throws PrintException;
}

