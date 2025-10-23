/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.multcheck2.dynamic;

import com.jiuqi.nr.multcheck2.bean.MultcheckResItem;
import java.util.List;
import java.util.Map;

public interface IMCResultService {
    public List<MultcheckResItem> getRecordByOrg(String var1, String var2, String var3, String var4, String var5) throws Exception;

    public Map<String, List<MultcheckResItem>> getRecordByOrgList(String var1, String var2, List<String> var3, String var4, String var5) throws Exception;
}

