/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.service;

import com.jiuqi.nr.mapping2.bean.ZBMapping;
import java.util.List;

public interface ZBMappingService {
    public List<ZBMapping> findByMS(String var1);

    public List<ZBMapping> findByMSAndForm(String var1, String var2);

    public List<ZBMapping> findByMSAndMapping(String var1, String var2);

    public List<ZBMapping> findByMSAndMapping(String var1, List<String> var2);

    public void save(String var1, String var2, List<ZBMapping> var3);

    public void batchAdd(List<ZBMapping> var1);

    public void clearByMs(String var1);

    public void deleteByMS(String var1);

    public void clearByMSAndForm(String var1, String var2);

    public void deleteByKeys(List<String> var1);
}

