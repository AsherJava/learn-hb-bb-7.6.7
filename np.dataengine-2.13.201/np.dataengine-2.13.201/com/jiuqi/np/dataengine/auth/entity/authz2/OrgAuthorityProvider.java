/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.auth.entity.authz2;

import com.jiuqi.np.dataengine.auth.entity.EntityOperation;
import java.util.Date;
import java.util.Set;

public interface OrgAuthorityProvider {
    public boolean isAllAllowed(EntityOperation var1);

    public boolean isAllAllowed(String var1, EntityOperation var2);

    public boolean canOperate(EntityOperation var1, String var2, Date var3, Date var4);

    public boolean canOperate(String var1, EntityOperation var2, String var3, Date var4, Date var5);

    public Set<String> getCanOperateOrg(EntityOperation var1, Date var2, Date var3);

    public Set<String> getCanOperateOrg(String var1, EntityOperation var2, Date var3, Date var4);

    public Set<String> getCanOperateIdentities(EntityOperation var1, String var2, Date var3, Date var4);

    public Set<String> getCanOperateIdentities(String var1, EntityOperation var2, String var3, Date var4, Date var5);
}

