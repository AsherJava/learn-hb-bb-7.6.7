/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.dao;

import com.jiuqi.dc.mappingscheme.impl.domain.BaseDataMappingDefineDO;
import java.util.List;

public interface BaseDataMappingDefineDao {
    public int insert(BaseDataMappingDefineDO var1);

    public int update(BaseDataMappingDefineDO var1);

    public int delete(BaseDataMappingDefineDO var1);

    public List<BaseDataMappingDefineDO> selectAll();

    public List<BaseDataMappingDefineDO> selectByModelType(String var1);

    public List<String> getTableColumn(String var1);
}

