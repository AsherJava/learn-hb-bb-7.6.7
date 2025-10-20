/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.dao;

import java.util.List;
import java.util.Map;

public interface OdsMdOrgDao {
    public Map<String, Map<String, Object>> selectOdsMdOrg(String var1);

    public void insertOdsMdOrg(List<Map<String, Object>> var1);

    public void updateOdsMdOrg(List<Map<String, Object>> var1);
}

