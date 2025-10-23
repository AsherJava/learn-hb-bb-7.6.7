/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.jqrmapping;

import com.jiuqi.nr.migration.transferdata.jqrmapping.JQRResourceMapping2DO;
import java.util.List;

public interface JQRResourceMapping2Service {
    public void batchInsertOrUpdateJqrCustomMappings(List<JQRResourceMapping2DO> var1, String var2);

    public List<JQRResourceMapping2DO> findByMSJqrCustom(String var1);

    public String getTableNameMapping(String var1);

    public String getSolutionNameMapping(String var1);

    public String getVersionMapping(String var1);

    public void deleteMappingByMSKey(String var1);
}

