/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.dao;

import com.jiuqi.dc.mappingscheme.impl.domain.DataSchemeOptionDO;
import java.util.List;

public interface DataSchemeOptionDao {
    public List<DataSchemeOptionDO> queryByDataSchemeCode(String var1);

    public int insert(DataSchemeOptionDO var1);

    public int update(DataSchemeOptionDO var1);

    public String queryOptionValue(String var1, String var2);

    public int deleteByDataSchemeCode(String var1);
}

