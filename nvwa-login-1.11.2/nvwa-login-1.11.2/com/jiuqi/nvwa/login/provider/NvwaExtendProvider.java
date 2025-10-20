/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextIdentity
 *  com.jiuqi.np.core.context.ContextUser
 */
package com.jiuqi.nvwa.login.provider;

import com.jiuqi.np.core.context.ContextIdentity;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.nvwa.login.domain.NvwaContextIdentity;
import java.util.Map;

public interface NvwaExtendProvider {
    public NvwaContextIdentity getNvwaContextIdentity(ContextUser var1, Map<String, Object> var2);

    public String getOrgTitle(String var1);

    public boolean existOrgAuth(String var1, ContextUser var2, ContextIdentity var3);
}

