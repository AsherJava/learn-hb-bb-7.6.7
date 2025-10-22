/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.gather.gzw.service.dao;

import com.jiuqi.nr.batch.gather.gzw.service.entity.GatherEntityCodeMapping;
import java.util.List;

public interface IGatherEntityCodeMappingDao {
    public List<GatherEntityCodeMapping> queryCodeMapping(String var1, String var2, String var3, String var4);

    public List<GatherEntityCodeMapping> queryCodeMappingByDw(String var1, String var2, String var3, String var4);

    public List<GatherEntityCodeMapping> queryCodeMapping(String var1);

    public int[] insertCodeMappings(List<GatherEntityCodeMapping> var1);

    public int deleteCodeMappings(String var1, String var2);
}

