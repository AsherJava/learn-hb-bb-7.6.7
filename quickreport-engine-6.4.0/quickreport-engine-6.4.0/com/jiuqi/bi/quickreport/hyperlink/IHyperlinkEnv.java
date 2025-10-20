/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.hyperlink;

import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkContext;
import com.jiuqi.bi.quickreport.hyperlink.ReportHyperlinkException;
import com.jiuqi.bi.quickreport.model.HyperlinkWindowInfo;

@Deprecated
public interface IHyperlinkEnv {
    public static final String HYPERLINK_FUNCNAME = "ql";
    public static final String MESSAGE_FUNCNAME = "qm";

    public IHyperlinkContext createContext(int var1, int var2) throws ReportHyperlinkException;

    public void linkTo(Object var1, Object var2, int var3, int var4) throws ReportHyperlinkException;

    public boolean resExisted(Object var1, Object var2, int var3, int var4) throws ReportHyperlinkException;

    public HyperlinkWindowInfo resWindowInfo(Object var1, Object var2, int var3, int var4) throws ReportHyperlinkException;

    public String resTitle(Object var1, Object var2, int var3, int var4) throws ReportHyperlinkException;
}

