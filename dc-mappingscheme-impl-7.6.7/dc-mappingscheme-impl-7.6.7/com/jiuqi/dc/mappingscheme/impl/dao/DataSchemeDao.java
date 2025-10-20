/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.dao;

import com.jiuqi.dc.mappingscheme.impl.domain.DataSchemeDO;
import java.util.List;

public interface DataSchemeDao {
    public List<DataSchemeDO> selectAll();

    public int insert(DataSchemeDO var1);

    public int update(DataSchemeDO var1);

    public int delete(DataSchemeDO var1);

    public int stop(DataSchemeDO var1);

    public int existQuote(String var1);
}

