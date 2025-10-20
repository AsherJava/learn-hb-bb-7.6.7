/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.login.provider;

import com.jiuqi.nvwa.login.domain.NvwaContext;
import java.util.Set;

public interface NvwaPermExtendProvider {
    public Set<String> getPermissions(NvwaContext var1);
}

