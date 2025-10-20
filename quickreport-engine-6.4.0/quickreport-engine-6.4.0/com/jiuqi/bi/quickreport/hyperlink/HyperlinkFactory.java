/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.hyperlink;

import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkExecutor;
import com.jiuqi.bi.quickreport.hyperlink.ReportHyperlinkException;

@Deprecated
public abstract class HyperlinkFactory {
    public abstract IHyperlinkExecutor createExecutor() throws ReportHyperlinkException;

    public String getHyperlinkFunction(String contextPath, String cacheID) throws ReportHyperlinkException {
        return null;
    }
}

