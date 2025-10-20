/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.DeleteProvider
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.UpdateProvider
 *  org.apache.ibatis.jdbc.SQL
 */
package com.jiuqi.va.biz.dao;

import com.jiuqi.va.biz.domain.VaMonitorBillRuleDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;

@Mapper
public interface VaMonitorBillRuleDao
extends BaseOptMapper<VaMonitorBillRuleDO> {
    @DeleteProvider(type=VaMonitorBillRuleDaoProvider.class, method="deleteByUpdateTime")
    public int deleteByUpdateTime(VaMonitorBillRuleDO var1);

    @UpdateProvider(type=VaMonitorBillRuleDaoProvider.class, method="updateByCondition")
    public int updateByCondition(VaMonitorBillRuleDO var1);

    public static class VaMonitorBillRuleDaoProvider {
        public String deleteByUpdateTime(VaMonitorBillRuleDO vaMonitorBillRuleDO) {
            SQL sql = new SQL();
            sql.DELETE_FROM("VA_MONITOR_BILL_RULE");
            sql.WHERE("UPDATETIME < #{updatetime}");
            return sql.toString();
        }

        public String updateByCondition(VaMonitorBillRuleDO vaMonitorBillRuleDO) {
            SQL sql = new SQL();
            sql.UPDATE("VA_MONITOR_BILL_RULE");
            sql.SET("EXECUTECOUNT = #{executecount}");
            sql.SET("TOTALTIME = #{totaltime}");
            sql.SET("MAXTIME = #{maxtime}");
            sql.SET("MINTIME = #{mintime}");
            sql.SET("AVGTIME = #{avgtime}");
            sql.SET("UPDATETIME = #{updatetime}");
            sql.WHERE("HOSTNAME = #{hostname}");
            sql.WHERE("DEFINECODE = #{definecode}");
            sql.WHERE("RULENAME = #{rulename}");
            sql.WHERE("MPERIOD = #{mperiod}");
            return sql.toString();
        }
    }
}

