/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Update
 */
package com.jiuqi.va.billcode.dao;

import com.jiuqi.va.billcode.domain.BillCodeFlowDO;
import com.jiuqi.va.billcode.domain.BillCodeFlowDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IBillCodeFlowDao
extends BaseOptMapper<BillCodeFlowDO> {
    @Update(value={"update MD_BILLCODE_FLOW set FLOWNUMBER = FLOWNUMBER + #{flowNumber} where DIMENSIONS = #{dimensions}"})
    public int updateFlow(BillCodeFlowDTO var1);
}

