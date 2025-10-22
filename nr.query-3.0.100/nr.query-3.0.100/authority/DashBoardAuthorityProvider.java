/*
 * Decompiled with CFR 0.152.
 */
package authority;

public interface DashBoardAuthorityProvider {
    public boolean canReadModal(String var1, String var2);

    public boolean canWriteModal(String var1, String var2);

    public boolean canDeleteModal(String var1, String var2);

    public boolean canOperateDashBoardCategoryResource(String var1, String var2, String var3);

    public void grantAllPrivileges(String var1, String var2);
}

