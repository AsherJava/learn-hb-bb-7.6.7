/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.hyperlink;

import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkContext;
import com.jiuqi.bi.quickreport.hyperlink.ReportHyperlinkException;

@Deprecated
public interface IHyperlinkExecutor {
    public void doLink(Object var1, Object var2, IHyperlinkContext var3) throws ReportHyperlinkException;

    public boolean resExisted(Object var1, Object var2, IHyperlinkContext var3) throws ReportHyperlinkException;
}

