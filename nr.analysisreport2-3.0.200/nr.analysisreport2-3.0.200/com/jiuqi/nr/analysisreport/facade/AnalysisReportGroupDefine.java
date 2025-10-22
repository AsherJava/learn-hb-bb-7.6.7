/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.facade;

import com.jiuqi.nr.analysisreport.facade.Analysis;

public interface AnalysisReportGroupDefine
extends Analysis {
    public void setKey(String var1);

    public String getKey();

    public void setTitle(String var1);

    public String getTitle();

    public void setDescription(String var1);

    @Override
    public String getDescription();

    public void setOrder(String var1);

    public String getOrder();

    public void setParentgroup(String var1);

    public String getParentgroup();
}

