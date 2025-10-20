/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.dc.base.impl.onlinePeriod.mapper;

import com.jiuqi.dc.base.client.onlinePeriod.vo.OnlinePeriodDefineVO;
import com.jiuqi.dc.base.impl.onlinePeriod.domain.OnlinePeriodDefineDO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface OnlinePeriodDefineMapper
extends BaseOptMapper<OnlinePeriodDefineDO> {
    @SelectProvider(type=SqlProvider.class, method="getAllTableData")
    public List<OnlinePeriodDefineVO> getAllTableData(OnlinePeriodDefineDO var1);

    @SelectProvider(type=SqlProvider.class, method="getMinPeriod")
    public String getMinPeriod(OnlinePeriodDefineDO var1);

    public static class SqlProvider {
        public String getAllTableData(OnlinePeriodDefineDO onlinePeriodDefineDO) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT * \n");
            sql.append("  FROM ").append("DC_DEFINE_ONLINEPERIOD").append(" \n");
            sql.append(" WHERE 1 = 1");
            if (!onlinePeriodDefineDO.getModuleCode().equals("root")) {
                sql.append("   AND MODULECODE = #{moduleCode} \n");
            }
            return sql.toString();
        }

        public String getMinPeriod(OnlinePeriodDefineDO onlinePeriodDefineDO) {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT MIN(ONLINEPERIOD) AS MINPERIOD FROM ").append("DC_DEFINE_ONLINEPERIOD");
            return sql.toString();
        }
    }
}

