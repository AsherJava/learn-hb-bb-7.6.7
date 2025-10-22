/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.provider;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface IEntityAuthProvider {
    public boolean canAuthorityEntityKey(String var1, String var2, String var3, Date var4);

    public Map<String, Boolean> canAuthorityEntityKey(String var1, String var2, Set<String> var3, Date var4);

    public boolean canAuthorityEntityKey(String var1, String var2, String var3, String var4, Date var5);

    public Set<String> getCanOperateEntityKeys(String var1, String var2, Date var3);

    public Set<String> getCanOperateIdentities(String var1, String var2, String var3, Date var4);
}

