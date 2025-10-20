/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bill.bd.core.dao;

import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface BillChangeRecordDao
extends BaseOptMapper<BillChangeRecordDO> {
    @SelectProvider(type=SqlProvider.class, method="listByBillCode")
    public List<BillChangeRecordDO> listByBillCode(BillChangeRecordDO var1);

    public static class SqlProvider {
        public String listByBillCode(BillChangeRecordDO recordDO) {
            SQL sql = new SQL();
            sql.SELECT("changefiledname, changefiledtitle, createtime, changebefore, changeafter, srcbillcode, srcbilldefine");
            sql.FROM("BILLCHANGE_RECORD record");
            sql.WHERE("billcode = #{billcode,jdbcType=VARCHAR} and changetype in (1, 2)");
            sql.ORDER_BY("createtime desc");
            return sql.toString();
        }
    }
}

