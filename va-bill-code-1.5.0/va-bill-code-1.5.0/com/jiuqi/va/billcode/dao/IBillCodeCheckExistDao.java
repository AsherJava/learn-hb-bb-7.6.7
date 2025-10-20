/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.Select
 */
package com.jiuqi.va.billcode.dao;

import com.jiuqi.va.billcode.domain.BillCodeCheckExistDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IBillCodeCheckExistDao {
    @Select(value={"select count(1) from ${tableName} where BILLCODE is not null and DEFINECODE = #{defineCode,jdbcType=VARCHAR}"})
    public int selectExistCount(BillCodeCheckExistDO var1);
}

