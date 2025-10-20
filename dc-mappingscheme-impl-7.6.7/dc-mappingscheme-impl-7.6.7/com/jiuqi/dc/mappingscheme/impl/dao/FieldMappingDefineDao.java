/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.mappingscheme.impl.dao;

import com.jiuqi.dc.mappingscheme.impl.domain.FieldMappingDefineDO;
import java.util.List;

public interface FieldMappingDefineDao {
    public int insert(FieldMappingDefineDO var1);

    public void batchInsert(List<FieldMappingDefineDO> var1);

    public int delete(FieldMappingDefineDO var1);

    public int deleteByDataMappingId(String var1);

    public List<FieldMappingDefineDO> selectAll();

    public List<FieldMappingDefineDO> selectByModelType(String var1);
}

