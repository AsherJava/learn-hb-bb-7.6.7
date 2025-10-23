/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.service;

import com.jiuqi.nr.mapping.bean.ZBMapping;
import java.util.List;

public interface ZBMappingService {
    public List<ZBMapping> findByMS(String var1);

    public List<ZBMapping> findByMSAndForm(String var1, String var2);

    public void save(String var1, String var2, List<ZBMapping> var3);

    public void clearByMs(String var1);

    public void clearByMSAndForm(String var1, String var2);
}

