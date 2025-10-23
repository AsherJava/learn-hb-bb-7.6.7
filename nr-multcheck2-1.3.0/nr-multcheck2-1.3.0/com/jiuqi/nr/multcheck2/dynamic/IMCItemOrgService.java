/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.dynamic;

import com.jiuqi.nr.multcheck2.bean.MultcheckResOrg;
import java.util.List;
import java.util.Map;

public interface IMCItemOrgService {
    public void batchSave(List<MultcheckResOrg> var1, List<String> var2, String var3);

    @Deprecated
    public List<MultcheckResOrg> getByOrg(String var1, String var2, String var3, String var4, String var5, Map<String, String> var6);

    @Deprecated
    public List<MultcheckResOrg> getByItem(String var1, String var2, String var3, String var4, Map<String, String> var5);

    public List<MultcheckResOrg> getByOrg(String var1, String var2, String var3, String var4);

    public List<MultcheckResOrg> getByItem(String var1, String var2, String var3);
}

