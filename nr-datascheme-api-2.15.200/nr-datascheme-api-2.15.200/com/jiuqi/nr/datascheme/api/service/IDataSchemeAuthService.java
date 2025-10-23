/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import java.util.List;

public interface IDataSchemeAuthService {
    public boolean canReadGroup(String var1);

    public boolean canWriteGroup(String var1);

    public boolean canReadScheme(String var1);

    public boolean canWriteScheme(String var1);

    public void grantAllPrivileges(DesignDataScheme var1);

    public void grantAllPrivileges(DesignDataGroup var1);

    public void revokeAll(DesignDataScheme var1);

    public void revokeAll(DesignDataGroup var1);

    public void revokeAllForSchemeGroup(List<String> var1);
}

