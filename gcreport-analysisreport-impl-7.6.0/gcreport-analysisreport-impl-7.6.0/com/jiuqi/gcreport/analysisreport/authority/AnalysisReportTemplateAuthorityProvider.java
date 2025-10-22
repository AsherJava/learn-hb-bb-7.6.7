/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.analysisreport.authority;

public interface AnalysisReportTemplateAuthorityProvider {
    public boolean canRead(String var1);

    public void grantAllPrivileges(String var1);
}

