/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.auth;

import com.jiuqi.nr.query.querymodal.QueryModelType;

public interface QueryModelAuthorityProvider {
    public boolean canReadModal(String var1, String var2);

    public boolean canWriteModal(String var1, String var2);

    @Deprecated
    public boolean canDeleteModal(String var1, String var2);

    public boolean canOperateQueryModalCategoryResource(String var1, String var2, String var3);

    public void grantAllPrivileges(String var1, String var2, QueryModelType var3);

    public boolean canDelegateQueryModalResource(String var1, String var2);
}

