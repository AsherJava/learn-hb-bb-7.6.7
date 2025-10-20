/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.dao;

import com.jiuqi.dc.mappingscheme.impl.domain.DataMappingDefineDO;
import java.util.List;

public interface DataMappingDefineDao {
    public int insert(DataMappingDefineDO var1);

    public int update(DataMappingDefineDO var1);

    public int delete(DataMappingDefineDO var1);

    public List<DataMappingDefineDO> selectAll();

    public List<DataMappingDefineDO> selectByModelType(String var1);
}

