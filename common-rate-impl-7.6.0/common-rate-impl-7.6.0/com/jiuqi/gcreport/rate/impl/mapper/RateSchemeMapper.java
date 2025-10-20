/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SqlBuildUtil
 *  com.jiuqi.va.mapper.common.BaseOptMapper
 *  org.apache.ibatis.annotations.Mapper
 *  org.apache.ibatis.annotations.SelectProvider
 */
package com.jiuqi.gcreport.rate.impl.mapper;

import com.jiuqi.common.base.util.SqlBuildUtil;
import com.jiuqi.gcreport.rate.impl.domain.ConvertRateSchemeDO;
import com.jiuqi.gcreport.rate.impl.dto.ConvRateSchemeQueryDTO;
import com.jiuqi.va.mapper.common.BaseOptMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface RateSchemeMapper
extends BaseOptMapper<ConvertRateSchemeDO> {
    @SelectProvider(type=SqlProvider.class, method="getSchemeBySubjectCode")
    public List<ConvertRateSchemeDO> getSchemeBySubjectCode(ConvRateSchemeQueryDTO var1);

    public static class SqlProvider {
        public String getSchemeBySubjectCode(ConvRateSchemeQueryDTO param) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT T.* \n");
            sql.append("  FROM ").append("DC_SCHEME_CONVRATE").append(" T \n");
            sql.append(" WHERE ").append(SqlBuildUtil.getStrInCondi((String)" T.SUBJECTCODE", param.getSubjectCodes()));
            sql.append("  ORDER BY T.SUBJECTCODE \n");
            return sql.toString();
        }
    }
}

