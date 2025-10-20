/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Delete
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 */
package com.jiuqi.dc.base.impl.assistdim.mapper;

import com.jiuqi.dc.base.impl.assistdim.domain.AssistDimItemDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AssistDimItemMapper
extends BaseOptMapper<AssistDimItemDO> {
    @Select(value={"SELECT * FROM MD_ASSISTDIMITEM WHERE 1=1 AND CODE = #{code}"})
    public List<AssistDimItemDO> selectByCode(AssistDimItemDO var1);

    @Select(value={"SELECT * FROM MD_ASSISTDIMITEM WHERE 1=1 AND EFFECTTABLE = #{effectTable}"})
    public List<AssistDimItemDO> selectByEffectTable(AssistDimItemDO var1);

    @Delete(value={"DELETE FROM MD_ASSISTDIMITEM WHERE 1=1 AND CODE = #{code}"})
    public int deleteByCode(AssistDimItemDO var1);
}

