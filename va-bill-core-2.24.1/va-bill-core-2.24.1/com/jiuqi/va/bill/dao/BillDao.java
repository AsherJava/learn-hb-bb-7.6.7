/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.CustomOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.bill.dao;

import com.jiuqi.va.bill.domain.BillDTO;
import com.jiuqi.va.mapper.common.CustomOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface BillDao
extends CustomOptMapper {
    @SelectProvider(type=BillDaoProvider.class, method="selectBillCode")
    public List<String> selectBillCode(BillDTO var1);

    @SelectProvider(type=BillDaoProvider.class, method="count")
    public int count(BillDTO var1);

    public static class BillDaoProvider {
        public String selectBillCode(BillDTO billDTO) {
            SQL sql = new SQL();
            sql.SELECT("BILLCODE");
            sql.FROM(billDTO.getTableName());
            sql.WHERE("DEFINECODE = #{defineCode}");
            sql.WHERE("CREATEUSER = #{userCode}");
            sql.ORDER_BY(billDTO.getSort() + " " + billDTO.getOrder());
            return sql.toString();
        }

        public String count(BillDTO billDTO) {
            SQL sql = new SQL();
            sql.SELECT("count(0)");
            sql.FROM(billDTO.getTableName());
            sql.WHERE("DEFINECODE = #{defineCode}");
            sql.WHERE("CREATEUSER = #{userCode}");
            return sql.toString();
        }
    }
}

