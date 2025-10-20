/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.conversion.conversionsystem.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemItemEO;
import java.util.List;
import java.util.Set;

public interface ConversionSystemItemDao
extends IDbSqlGenericDAO<ConversionSystemItemEO, String> {
    public List<ConversionSystemItemEO> getSystemItemByTaskSchemeId(String var1);

    public ConversionSystemItemEO getSystemItemByFormIdAndIndexId(String var1, String var2);

    public List<ConversionSystemItemEO> getSystemItemByFormId(String var1);

    public List<ConversionSystemItemEO> getSystemItemBySchemeTaskIdsAndFormIds(Set<String> var1);

    public List<ConversionSystemItemEO> batchGetSystemItemsByFormIdAndIndexIds(String var1, Set<String> var2);

    public List<ConversionSystemItemEO> getSystemItemsByIndexIds(Set<String> var1);

    public void deleteBySchemeTaskIdAndIndexId(String var1, String var2);

    public void deleteById(String var1);

    public void deleteByFormIdAndIndexId(String var1, String var2);
}

