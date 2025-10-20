/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.annotations.Mapper
 *  tk.mybatis.mapper.common.BaseMapper
 */
package com.jiuqi.va.bill.dao;

import com.jiuqi.va.bill.domain.BillLockDO;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface BillLockDao
extends BaseMapper<BillLockDO> {
}

