/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckcore.sqlutils.dao;

import com.jiuqi.gcreport.financialcheckcore.sqlutils.entity.ReltxIdTemporary;
import java.util.Collection;
import java.util.List;

public interface ReltxIdTemporaryDao {
    public String saveAll(Collection<String> var1);

    public void deleteByGroupId(String var1);

    public void deleteByGroupIds(Collection<String> var1);

    public List<ReltxIdTemporary> listIdTemporaryByGroupId(String var1);
}

