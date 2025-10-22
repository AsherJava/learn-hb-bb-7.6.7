/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.authority;

public interface AnalysisReportAuthorityProvider {
    public boolean canReadModal(String var1, String var2);

    public boolean canWriteModal(String var1, String var2);

    public boolean canDeleteModal(String var1, String var2);

    public boolean canCreateModal(String var1, String var2);

    public boolean canOperateQueryModalCategoryResource(String var1, String var2, String var3);

    public void grantAllPrivileges(String var1);
}

